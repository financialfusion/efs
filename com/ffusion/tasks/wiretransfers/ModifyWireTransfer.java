package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.MapError;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyWireTransfer
  extends WireTransfer
  implements WireTaskDefines
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String beanSessionName = null;
  protected String collectionSessionName = null;
  protected String payeeSessionName = "WireTransferPayee";
  protected String validate = null;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean dateChanged = false;
  protected boolean clearPayee = false;
  protected boolean saveToBatch = false;
  protected boolean duplicateWire = false;
  protected boolean nonBusinessDay = false;
  protected boolean _autoEntitleWireTransfer = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = this.successURL;
    if (this.clearPayee == true)
    {
      setWirePayeeID(null);
      setWirePayee(new WireTransferPayee());
      localHttpSession.setAttribute(this.payeeSessionName, getWirePayee());
      this.clearPayee = false;
    }
    else if (this.saveToBatch == true)
    {
      WireTransfer localWireTransfer = (WireTransfer)localHttpSession.getAttribute("OldWireTransfer");
      if (localWireTransfer != null) {
        localWireTransfer.set(this);
      }
      this.saveToBatch = false;
      localHttpSession.removeAttribute("OldWireTransfer");
    }
    else if (this.initFlag == true)
    {
      str = initialize(localHttpSession);
    }
    else
    {
      str = modifyWireTransfer(localHttpSession);
    }
    return str;
  }
  
  protected String modifyWireTransfer(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    try
    {
      if (validateInput(paramHttpSession) == true)
      {
        if (this.processFlag == true)
        {
          this.processFlag = false;
          WireTransfer localWireTransfer1 = (WireTransfer)paramHttpSession.getAttribute("OldWireTransfer");
          if (localWireTransfer1 == null)
          {
            this.error = 12006;
            return this.taskErrorURL;
          }
          SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          if (localSecureUser == null)
          {
            this.error = 12014;
            return this.taskErrorURL;
          }
          HashMap localHashMap = new HashMap();
          try
          {
            if ((getWireType() != null) && ((getWireType().equals("TEMPLATE")) || (getWireType().equals("RECTEMPLATE"))))
            {
              setAutoEntitleTransaction(this._autoEntitleWireTransfer);
              Wire.modifyWireTemplate(localSecureUser, this, localWireTransfer1, localHashMap);
            }
            else
            {
              Wire.modifyWireTransfer(localSecureUser, this, localWireTransfer1, localHashMap);
            }
          }
          catch (CSILException localCSILException2)
          {
            if ((localCSILException2.getCode() == 20003) || (localCSILException2.getServiceError() == 20003)) {
              paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
            }
            this.error = MapError.mapError(localCSILException2);
            str = this.serviceErrorURL;
          }
          if (this.error == 0)
          {
            WireTransfer localWireTransfer2 = new WireTransfer(this.locale);
            localWireTransfer2.set(this);
            paramHttpSession.setAttribute(this.beanSessionName, localWireTransfer2);
            paramHttpSession.removeAttribute("OldWireTransfer");
          }
        }
      }
      else {
        return this.taskErrorURL;
      }
    }
    catch (CSILException localCSILException1)
    {
      MapError.mapError(localCSILException1);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected String initialize(HttpSession paramHttpSession)
  {
    WireTransfer localWireTransfer1 = (WireTransfer)paramHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransfer1 == null)
    {
      this.error = 12005;
      return this.taskErrorURL;
    }
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    set(localWireTransfer1);
    WireTransfer localWireTransfer2 = new WireTransfer(this.locale);
    localWireTransfer2.set(localWireTransfer1);
    paramHttpSession.setAttribute("OldWireTransfer", localWireTransfer2);
    this.initFlag = false;
    return this.successURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    if (this.validate == null) {
      return true;
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    String str = getWireType();
    if (str == null)
    {
      this.error = 12044;
      return false;
    }
    if ((this.validate.indexOf("FROM_ACCOUNT_ID") != -1) && (isNullOrEmpty(getFromAccountID()) == true))
    {
      this.error = 12007;
      return false;
    }
    if ((this.validate.indexOf("AMOUNT") != -1) && (!validateOrigAmount(paramHttpSession))) {
      return false;
    }
    if ((!str.equals("RECTEMPLATE")) && (!str.equals("TEMPLATE")) && (!validateDate(paramHttpSession, false))) {
      return false;
    }
    if ((this.validate.indexOf("WIRE_PAYEE") != -1) && (isNullOrEmpty(getWirePayeeID()) == true) && (getWirePayee() != null) && (!validatePayee(paramHttpSession))) {
      return false;
    }
    if ((this.validate.indexOf("WIRE_TRANSFER_BANK") != -1) && (isNullOrEmpty(getWirePayeeID()) == true) && (getWirePayee() != null) && (!validateBank(paramHttpSession))) {
      return false;
    }
    if ((str.equals("RECTEMPLATE")) || (str.equals("TEMPLATE")))
    {
      if (isNullOrEmpty(getWireName()) == true)
      {
        this.error = 12053;
        return false;
      }
    }
    else {
      this.duplicateWire = Wire.isDuplicateWire(localSecureUser, this, false, new HashMap());
    }
    if ((this.validate.indexOf("BY_ORDER_OF") != -1) && ((!isNullOrEmpty(getByOrderOfName())) || (!isNullOrEmpty(getByOrderOfAddress1())) || (!isNullOrEmpty(getByOrderOfAddress2())) || (!isNullOrEmpty(getByOrderOfAddress3())) || (!isNullOrEmpty(getByOrderOfAccount()))) && ((isNullOrEmpty(getByOrderOfName())) || (isNullOrEmpty(getByOrderOfAddress1())) || (isNullOrEmpty(getByOrderOfAccount()))))
    {
      this.error = 12057;
      return false;
    }
    this.validate = null;
    return true;
  }
  
  public boolean validatePayee(HttpSession paramHttpSession)
    throws CSILException
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    WireTransferPayee localWireTransferPayee = getWirePayee();
    if (isNullOrEmpty(localWireTransferPayee.getPayeeName()) == true)
    {
      this.error = 12022;
      return false;
    }
    if (localWireTransferPayee.getDestinationBank() == null)
    {
      this.error = 12051;
      return false;
    }
    if (isNullOrEmpty(localWireTransferPayee.getAccountNum()) == true)
    {
      this.error = 12023;
      return false;
    }
    if (!Strings.isValidAccountNumber(localWireTransferPayee.getAccountNum(), localSecureUser.getLocale()))
    {
      this.error = 12058;
      return false;
    }
    if (isNullOrEmpty(localWireTransferPayee.getCity()) == true)
    {
      this.error = 12026;
      return false;
    }
    if ((localWireTransferPayee.getCountry() != null) && (localWireTransferPayee.getCountry().length() > 0))
    {
      HashMap localHashMap = new HashMap();
      String str1 = Util.getCodeForBankLookupCountryName(localSecureUser, localWireTransferPayee.getCountry(), localHashMap);
      if (isNullOrEmpty(localWireTransferPayee.getZipCode()) == true)
      {
        this.error = 12028;
        return false;
      }
      String str2 = Util.validateZipCodeFormat(localSecureUser, str1, localWireTransferPayee.getZipCode(), localHashMap);
      if (str2 == null)
      {
        this.error = 12054;
        return false;
      }
      if (!str2.equals("")) {
        localWireTransferPayee.getZipCodeValue().setFormat(str2);
      }
    }
    if (isNullOrEmpty(localWireTransferPayee.getStreet()) == true)
    {
      this.error = 12040;
      return false;
    }
    if (isNullOrEmpty(localWireTransferPayee.getCountry()) == true)
    {
      this.error = 12041;
      return false;
    }
    return true;
  }
  
  public boolean validateBank(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    WireTransferBank localWireTransferBank1 = getWirePayee().getDestinationBank();
    WireTransferBanks localWireTransferBanks1 = getWirePayee().getIntermediaryBanks();
    WireTransferBank localWireTransferBank2;
    WireTransferBanks localWireTransferBanks2;
    if (localWireTransferBank1.getRoutingNumber().length() == 0)
    {
      if ((localWireTransferBanks1 == null) || (localWireTransferBanks1.size() == 0))
      {
        this.error = 12030;
        return false;
      }
      int i = 1;
      for (int j = 0; j < localWireTransferBanks1.size(); j++)
      {
        localWireTransferBank2 = (WireTransferBank)localWireTransferBanks1.get(j);
        if (localWireTransferBank2.getRoutingNumber().length() > 0)
        {
          i = 0;
          break;
        }
      }
      if (i == 1)
      {
        this.error = 12030;
        return false;
      }
    }
    else
    {
      localWireTransferBanks2 = new WireTransferBanks();
      try
      {
        localWireTransferBanks2 = Wire.getWireTransferBanks(localSecureUser, localWireTransferBank1, 1, localHashMap);
        if (localWireTransferBanks2.size() == 0)
        {
          this.error = 12051;
          return false;
        }
      }
      catch (CSILException localCSILException1)
      {
        localCSILException1.printStackTrace(System.err);
        this.error = 12051;
        return false;
      }
      String str1 = localWireTransferBank1.getSrvrBankID();
      localWireTransferBank1.set((WireTransferBank)localWireTransferBanks2.get(0));
      localWireTransferBank1.setSrvrBankID(str1);
    }
    if (localWireTransferBanks1 != null)
    {
      if (localWireTransferBanks1.size() > 2)
      {
        this.error = 12055;
        return false;
      }
      localWireTransferBanks2 = new WireTransferBanks();
      for (int k = 0; k < localWireTransferBanks1.size(); k++)
      {
        localWireTransferBank2 = (WireTransferBank)localWireTransferBanks1.get(k);
        try
        {
          localWireTransferBanks2 = Wire.getWireTransferBanks(localSecureUser, localWireTransferBank2, 1, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          localCSILException2.printStackTrace(System.err);
          this.error = 12051;
          return false;
        }
        if (localWireTransferBanks2.size() == 0)
        {
          this.error = 12051;
          return false;
        }
        String str2 = localWireTransferBank2.getAction();
        if (str2 == null) {
          str2 = "";
        }
        String str3 = localWireTransferBank2.getSrvrBankID();
        localWireTransferBank2.set((WireTransferBank)localWireTransferBanks2.get(0));
        localWireTransferBank2.setAction(str2);
        localWireTransferBank2.setSrvrBankID(str3);
      }
    }
    return true;
  }
  
  public boolean validateDate(HttpSession paramHttpSession, boolean paramBoolean)
  {
    this.dateChanged = false;
    this.nonBusinessDay = false;
    if (isNullOrEmpty(getDueDate()) == true)
    {
      this.error = 12035;
      return false;
    }
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    DateTime localDateTime1 = new DateTime(localLocale);
    try
    {
      DateTime localDateTime2 = getDueDateValue();
      DateTime localDateTime3 = new DateTime(localLocale);
      localDateTime3.fromString(getDueDate());
      localDateTime3 = (DateTime)localDateTime2.clone();
      localDateTime3.set(1, localDateTime1.get(1));
      localDateTime3.set(2, localDateTime1.get(2));
      localDateTime3.set(5, localDateTime1.get(5));
      if ((paramBoolean == true) && (localDateTime2.before(localDateTime3) == true))
      {
        this.error = 12042;
        return false;
      }
      if ((localDateTime2.equals(localDateTime3)) || (localDateTime2.before(localDateTime3)))
      {
        localObject1 = new ProcessingWindow();
        ((ProcessingWindow)localObject1).setBankId(localSecureUser.getBPWFIID());
        ((ProcessingWindow)localObject1).setCustomerId(localSecureUser.getBusinessID());
        ((ProcessingWindow)localObject1).setPaymentType("WIRES");
        ((ProcessingWindow)localObject1).setPaymentSubType(getWireDestination());
        localObject2 = null;
        try
        {
          localObject2 = PaymentsAdmin.getProcessingWindows(localSecureUser, (ProcessingWindow)localObject1, null);
          if (((ProcessingWindows)localObject2).size() == 0)
          {
            ((ProcessingWindow)localObject1).setCustomerId(null);
            localObject2 = PaymentsAdmin.getProcessingWindows(localSecureUser, (ProcessingWindow)localObject1, null);
          }
          if (((ProcessingWindows)localObject2).size() > 0)
          {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
            String str = localSimpleDateFormat.format(new Date());
            int i = 0;
            for (int j = 0; j < ((ProcessingWindows)localObject2).size(); j++)
            {
              ProcessingWindow localProcessingWindow = (ProcessingWindow)((ProcessingWindows)localObject2).get(j);
              if (str.compareTo(localProcessingWindow.getCloseTime()) <= 0)
              {
                i = 1;
                break;
              }
            }
            if (i == 0)
            {
              localDateTime2.add(5, 1);
              this.dateChanged = true;
            }
          }
        }
        catch (CSILException localCSILException2)
        {
          DebugLog.log("WARNING: Unable to validate DueDate is within Processing Window");
        }
      }
      Object localObject1 = Wire.getSmartDate(localSecureUser, localDateTime2);
      Object localObject2 = new DateTime((Date)localObject1, localLocale);
      setDateToPost((DateTime)localObject2);
      localDateTime3 = (DateTime)((DateTime)localObject2).clone();
      localDateTime3.set(1, localDateTime2.get(1));
      localDateTime3.set(2, localDateTime2.get(2));
      localDateTime3.set(5, localDateTime2.get(5));
      if (((DateTime)localObject2).after(localDateTime3)) {
        this.nonBusinessDay = true;
      }
      if (!isNullOrEmpty(getSettlementDate()))
      {
        localDateTime3 = new DateTime(localLocale);
        localDateTime3.fromString(getSettlementDate());
        localDateTime3 = (DateTime)getSettlementDateValue().clone();
        localDateTime3.set(1, ((DateTime)localObject2).get(1));
        localDateTime3.set(2, ((DateTime)localObject2).get(2));
        localDateTime3.set(5, ((DateTime)localObject2).get(5));
        if (getSettlementDateValue().before(localDateTime3) == true)
        {
          DebugLog.log("WireTransfer: The SettlementDate occurs before DateToPost, so changing SettlementDate to DateToPost.");
          setSettlementDate((DateTime)localObject2);
        }
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.error = 12035;
      return false;
    }
    catch (CSILException localCSILException1) {}
    return true;
  }
  
  public boolean validateOrigAmount(HttpSession paramHttpSession)
  {
    String str1 = getWireType();
    String str2 = getOrigAmount();
    if ((getAction() != null) && (getAction().equals("add"))) {
      str2 = getAmountValue().getAmountValue().toString();
    }
    int i = 0;
    if ((str1.equals("RECTEMPLATE")) || (str1.equals("TEMPLATE")))
    {
      i = 1;
    }
    else if (isNullOrEmpty(str2) == true)
    {
      this.error = 12008;
      return false;
    }
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    Currency localCurrency = null;
    try
    {
      localCurrency = new Currency(str2, localLocale);
    }
    catch (Exception localException)
    {
      this.error = 12008;
      return false;
    }
    if (i == 1)
    {
      if (localCurrency.doubleValue() < 0.0D)
      {
        this.error = 12048;
        return false;
      }
    }
    else if (localCurrency.doubleValue() < 0.01D)
    {
      this.error = 12008;
      return false;
    }
    return true;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = paramString;
  }
  
  public void setBeanSessionName(String paramString)
  {
    this.beanSessionName = paramString;
  }
  
  public void setCollectionSessionName(String paramString)
  {
    this.collectionSessionName = paramString;
  }
  
  public void setClearPayee(String paramString)
  {
    this.clearPayee = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSaveWireToBatch(String paramString)
  {
    this.saveToBatch = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getDateChanged()
  {
    if (this.dateChanged == true) {
      return "true";
    }
    return "false";
  }
  
  public String getDuplicateWire()
  {
    if (this.duplicateWire == true) {
      return "true";
    }
    return "false";
  }
  
  public String getNonBusinessDay()
  {
    if (this.nonBusinessDay == true) {
      return "true";
    }
    return "false";
  }
  
  public void setPayeeSessionName(String paramString)
  {
    this.payeeSessionName = paramString;
  }
  
  public String getAutoEntitleWireTransfer()
  {
    return String.valueOf(this._autoEntitleWireTransfer);
  }
  
  public void setAutoEntitleWireTransfer(String paramString)
  {
    this._autoEntitleWireTransfer = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.ModifyWireTransfer
 * JD-Core Version:    0.7.0.1
 */