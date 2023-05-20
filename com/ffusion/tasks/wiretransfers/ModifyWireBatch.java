package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.MapError;
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

public class ModifyWireBatch
  extends WireBatch
  implements WireTaskDefines
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String beanSessionName = "WireBatch";
  protected String collectionSessionName = "WiresBatches";
  protected String validate = null;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected WireTransfer currentWire;
  protected boolean dateChanged = false;
  protected boolean duplicateWire = false;
  protected boolean nonBusinessDay = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = this.successURL;
    if (this.initFlag == true) {
      str = initialize(localHttpSession);
    } else {
      str = modifyWireBatch(localHttpSession);
    }
    return str;
  }
  
  protected String modifyWireBatch(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    try
    {
      if (validateInput(paramHttpSession) == true)
      {
        if (this.processFlag == true)
        {
          this.processFlag = false;
          WireBatch localWireBatch1 = (WireBatch)paramHttpSession.getAttribute("OldWireBatch");
          if (localWireBatch1 == null)
          {
            this.error = 12033;
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
            this.wires.setFilter("All");
            Wire.modifyWireBatch(localSecureUser, this, localWireBatch1, localHashMap);
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
            WireBatch localWireBatch2 = new WireBatch(this.locale);
            localWireBatch2.set(this);
            paramHttpSession.setAttribute(this.beanSessionName, localWireBatch2);
            paramHttpSession.removeAttribute("OldWireBatch");
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
    WireBatch localWireBatch1 = (WireBatch)paramHttpSession.getAttribute(this.beanSessionName);
    if (localWireBatch1 == null)
    {
      this.error = 12032;
      return this.taskErrorURL;
    }
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    set(localWireBatch1);
    WireBatch localWireBatch2 = new WireBatch(this.locale);
    localWireBatch2.set(localWireBatch1);
    WireTransfers localWireTransfers = localWireBatch2.getWires();
    if ((localWireTransfers == null) || (localWireTransfers.size() == 0))
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        localWireTransfers = Wire.getWireTransfersByBatchId(localSecureUser, localWireBatch2.getID(), new HashMap());
      }
      catch (Exception localException)
      {
        DebugLog.log("WARNING: Failed to get Wires for Batch in ModifyWireBatch.Initialize");
        localException.printStackTrace();
      }
      localWireBatch2.setWires(localWireTransfers);
    }
    paramHttpSession.setAttribute("OldWireBatch", localWireBatch2);
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
    if (this.validate.indexOf("BATCH_NAME") != -1)
    {
      if (isNullOrEmpty(getBatchName()))
      {
        this.error = 12036;
        return false;
      }
      if ((getBatchName().trim().length() == 0) || (getBatchName().trim().length() > 128))
      {
        this.error = 12056;
        return false;
      }
    }
    if ((this.validate.indexOf("DATE_TO_POST") != -1) && (!validateDate(paramHttpSession, false))) {
      return false;
    }
    if (this.validate.indexOf("WIRE_TRANSFERS") != -1)
    {
      WireTransfers localWireTransfers = getWires();
      if ((localWireTransfers == null) || (localWireTransfers.size() == 0))
      {
        this.error = 12052;
        return false;
      }
      for (int i = 0; i < localWireTransfers.size(); i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.get(i);
        if ((localWireTransfer != null) && (!validateWire(paramHttpSession, localWireTransfer, this.validate))) {
          return false;
        }
      }
    }
    if (!getBatchType().equals("HOST")) {
      this.duplicateWire = Wire.isDuplicateWireInBatch(localSecureUser, this, false, new HashMap());
    }
    this.validate = null;
    return true;
  }
  
  protected boolean validateWire(HttpSession paramHttpSession, WireTransfer paramWireTransfer, String paramString)
    throws CSILException
  {
    if ((paramWireTransfer.getAction() != null) && (paramWireTransfer.getAction().equals("del") == true)) {
      return true;
    }
    if ((paramString.indexOf("AMOUNT") != -1) && (!validateOrigAmount(paramHttpSession, paramWireTransfer))) {
      return false;
    }
    if (getBatchType().equals("HOST"))
    {
      if (isNullOrEmpty(paramWireTransfer.getHostID()) == true)
      {
        this.error = 12037;
        return false;
      }
    }
    else
    {
      if ((paramString.indexOf("FROM_ACCOUNT_ID") != -1) && (isNullOrEmpty(paramWireTransfer.getFromAccountID()) == true))
      {
        this.error = 12007;
        return false;
      }
      if ((isNullOrEmpty(paramWireTransfer.getWirePayeeID()) == true) && (paramWireTransfer.getWirePayee() != null))
      {
        if ((paramString.indexOf("WIRE_PAYEE") != -1) && (!validatePayee(paramHttpSession, paramWireTransfer))) {
          return false;
        }
        if ((paramString.indexOf("WIRE_TRANSFER_BANK") != -1) && (!validateBank(paramHttpSession, paramWireTransfer))) {
          return false;
        }
      }
    }
    if ((paramString.indexOf("BY_ORDER_OF") != -1) && (!validateByOrderOf(paramWireTransfer)))
    {
      this.error = 12057;
      return false;
    }
    paramWireTransfer.setDateToPost(getDateToPostValue());
    return true;
  }
  
  public boolean validateOrigAmount(HttpSession paramHttpSession, WireTransfer paramWireTransfer)
  {
    String str = paramWireTransfer.getOrigAmount();
    if (paramWireTransfer.getAction().equals("add")) {
      str = paramWireTransfer.getAmountValue().getAmountValue().toString();
    }
    if (isNullOrEmpty(str) == true)
    {
      this.error = 12008;
      return false;
    }
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    Currency localCurrency = null;
    try
    {
      localCurrency = new Currency(str, localLocale);
    }
    catch (Exception localException)
    {
      this.error = 12008;
      return false;
    }
    if (localCurrency.doubleValue() <= 0.0D)
    {
      this.error = 12008;
      return false;
    }
    return true;
  }
  
  public boolean validatePayee(HttpSession paramHttpSession, WireTransfer paramWireTransfer)
    throws CSILException
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    WireTransferPayee localWireTransferPayee = paramWireTransfer.getWirePayee();
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
    if (isNullOrEmpty(localWireTransferPayee.getCity()) == true)
    {
      this.error = 12026;
      return false;
    }
    if ((localWireTransferPayee.getCountry() != null) && (localWireTransferPayee.getCountry().length() > 0))
    {
      HashMap localHashMap = new HashMap();
      String str1 = Util.getCodeForBankLookupCountryName(localSecureUser, localWireTransferPayee.getCountry(), localHashMap);
      if ((str1 != null) && (str1.length() > 0) && (Util.isRegisteredCountry(localSecureUser, str1, new HashMap())))
      {
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
  
  public boolean validateBank(HttpSession paramHttpSession, WireTransfer paramWireTransfer)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    WireTransferBank localWireTransferBank1 = paramWireTransfer.getWirePayee().getDestinationBank();
    WireTransferBanks localWireTransferBanks1 = paramWireTransfer.getWirePayee().getIntermediaryBanks();
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
        ((ProcessingWindow)localObject1).setPaymentSubType(getBatchDestination());
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
          DebugLog.log("WireBatch: The SettlementDate occurs before DateToPost, so changing SettlementDate to DateToPost.");
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
  
  public boolean validateByOrderOf(WireTransfer paramWireTransfer)
  {
    return ((isNullOrEmpty(paramWireTransfer.getByOrderOfName())) && (isNullOrEmpty(paramWireTransfer.getByOrderOfAddress1())) && (isNullOrEmpty(paramWireTransfer.getByOrderOfAddress2())) && (isNullOrEmpty(paramWireTransfer.getByOrderOfAddress3())) && (isNullOrEmpty(paramWireTransfer.getByOrderOfAccount()))) || ((!isNullOrEmpty(paramWireTransfer.getByOrderOfName())) && (!isNullOrEmpty(paramWireTransfer.getByOrderOfAddress1())) && (!isNullOrEmpty(paramWireTransfer.getByOrderOfAccount())));
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
  
  public void setCurrentWire(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.currentWire = ((WireTransfer)getWires().getByID(paramString));
    } else {
      this.currentWire = null;
    }
  }
  
  public void setCurrentWireIndex(String paramString)
  {
    this.currentWire = null;
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        int i = Integer.parseInt(paramString);
        this.currentWire = ((WireTransfer)this.wires.get(i));
      }
    }
    catch (Exception localException) {}
  }
  
  public WireTransfer getCurrentWire()
  {
    return this.currentWire;
  }
  
  public void setWireAmount(String paramString)
  {
    if (this.currentWire != null) {
      if ((this.currentWire.getAction() == null) || (this.currentWire.getAction().equals("add") == true)) {
        this.currentWire.setAmount(paramString);
      } else {
        this.currentWire.setOrigAmount(paramString);
      }
    }
  }
  
  public void setWireTemplateID(String paramString)
  {
    if (this.currentWire != null) {
      this.currentWire.setTemplateID(paramString);
    }
  }
  
  public void setWireAction(String paramString)
  {
    if (this.currentWire != null)
    {
      String str = this.currentWire.getAction();
      if (str.equalsIgnoreCase("add") == true)
      {
        if (paramString.equalsIgnoreCase("del"))
        {
          WireTransfers localWireTransfers = getWires();
          if (localWireTransfers != null) {
            localWireTransfers.remove(this.currentWire);
          }
        }
      }
      else if (!str.equalsIgnoreCase("del")) {
        this.currentWire.setAction(paramString);
      }
    }
  }
  
  public void setWireHostID(String paramString)
  {
    if (this.currentWire != null) {
      this.currentWire.setHostID(paramString);
    }
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
  
  public void setDeleteWireFromBatch(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
      if ("add".equals(localWireTransfer.getAction())) {
        this.wires.remove(localWireTransfer);
      } else {
        localWireTransfer.setAction("del");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
    }
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
  
  public static boolean isNullOrEmpty(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.ModifyWireBatch
 * JD-Core Version:    0.7.0.1
 */