package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddWireTransfersFromImport
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected String validate = null;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected static long timeoutValue = 120000L;
  protected boolean currentlyProcessing = false;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  protected String approvalCollectionSessionName = null;
  protected String importCollectionSessionName = null;
  protected String wiresAccountsCollectionSessionName = null;
  protected boolean dateChanged = false;
  protected boolean duplicateWire = false;
  protected boolean nonBusinessDay = false;
  
  public AddWireTransfersFromImport()
  {
    this.collectionSessionName = "WireTransfers";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    if (this.initFlag == true)
    {
      timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      str = initialize(paramHttpServletRequest, localHttpSession);
    }
    else
    {
      str = addWireTransfers(localHttpSession);
    }
    return str;
  }
  
  protected String initialize(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    setLocale(this.locale);
    this.currentlyProcessing = false;
    this.haveProcessed = false;
    return this.successURL;
  }
  
  protected String addWireTransfers(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    WireTransfers localWireTransfers = (WireTransfers)paramHttpSession.getAttribute(this.importCollectionSessionName);
    if ((localWireTransfers == null) || (localWireTransfers.size() == 0))
    {
      this.error = 12004;
      str = this.taskErrorURL;
    }
    else
    {
      if (this.validate != null)
      {
        WireTransfer localWireTransfer = null;
        for (int j = 0; j < localWireTransfers.size(); j++)
        {
          localWireTransfer = (WireTransfer)localWireTransfers.get(j);
          if (!validateInput(paramHttpSession, localWireTransfer))
          {
            str = this.taskErrorURL;
            break;
          }
        }
        this.validate = null;
      }
      if ((this.error == 0) && (this.processFlag == true))
      {
        this.processFlag = false;
        int i = 0;
        synchronized (this)
        {
          if ((!this.currentlyProcessing) && (!this.haveProcessed))
          {
            this.currentlyProcessing = true;
            i = 1;
          }
        }
        if (i == 1)
        {
          try
          {
            str = jdMethod_for(paramHttpSession, localWireTransfers);
            this.nextURL = str;
          }
          catch (Exception localException1)
          {
            this.error = 1;
            this.nextURL = this.serviceErrorURL;
          }
          finally
          {
            this.currentlyProcessing = false;
          }
        }
        else
        {
          long l = System.currentTimeMillis();
          while ((!this.haveProcessed) && (this.currentlyProcessing == true))
          {
            if (System.currentTimeMillis() - l > timeoutValue)
            {
              if (this.error != 0) {
                break;
              }
              this.error = 1;
              break;
            }
            try
            {
              Thread.sleep(2000L);
            }
            catch (Exception localException2) {}
          }
          str = this.nextURL;
        }
      }
    }
    return str;
  }
  
  private String jdMethod_for(HttpSession paramHttpSession, WireTransfers paramWireTransfers)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 12014;
      return this.taskErrorURL;
    }
    WireTransfer localWireTransfer1 = null;
    HashMap localHashMap = new HashMap();
    WireTransfer localWireTransfer2 = null;
    for (int i = 0; i < paramWireTransfers.size(); i++)
    {
      localWireTransfer2 = (WireTransfer)paramWireTransfers.get(i);
      localWireTransfer2.setBankID(localSecureUser.getBPWFIID());
      localWireTransfer2.setCustomerID(localSecureUser.getBusinessID());
      localWireTransfer2.setSubmittedBy(localSecureUser.getProfileID());
      localWireTransfer2.setUserID(localSecureUser.getProfileID());
      try
      {
        localWireTransfer1 = Wire.addWireTransfer(localSecureUser, localWireTransfer2, localHashMap);
        paramWireTransfers.set(i, localWireTransfer1);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
    }
    this.haveProcessed = true;
    return this.successURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession, WireTransfer paramWireTransfer)
  {
    if (this.validate == null) {
      return true;
    }
    String str = paramWireTransfer.getWireType();
    Object localObject3;
    if (this.validate.indexOf("FROM_ACCOUNT_ID") != -1)
    {
      if ((m(paramWireTransfer.getFromAccountID()) == true) && (m(paramWireTransfer.getFromAccountNum()) == true))
      {
        this.error = 12007;
        return false;
      }
      localObject1 = (Accounts)paramHttpSession.getAttribute(this.wiresAccountsCollectionSessionName);
      if (localObject1 == null)
      {
        this.error = 12002;
        return false;
      }
      localObject2 = (String)paramHttpSession.getAttribute("BankId");
      localObject3 = null;
      localObject3 = ((Accounts)localObject1).getByIDAndBankIDAndRoutingNum(paramWireTransfer.getFromAccountID(), (String)localObject2, paramWireTransfer.getFromAccountRoutingNum());
      if (localObject3 == null) {
        localObject3 = ((Accounts)localObject1).getByNumberAndBankID(paramWireTransfer.getFromAccountNum(), (String)localObject2);
      }
      if (localObject3 == null)
      {
        this.error = 12007;
        return false;
      }
      paramWireTransfer.setFromAccountRoutingNum(((Account)localObject3).getRoutingNum());
      paramWireTransfer.setFromAccountID(((Account)localObject3).getID());
      paramWireTransfer.setFromAccountNum(((Account)localObject3).getNumber());
      paramWireTransfer.setFromAccountType(((Account)localObject3).getTypeValue());
    }
    if (this.validate.indexOf("AMOUNT") != -1)
    {
      localObject1 = paramWireTransfer.getAmountValue();
      if (("RECTEMPLATE".equals(str)) || ("TEMPLATE".equals(str)))
      {
        if ((localObject1 != null) && (((Currency)localObject1).doubleValue() < 0.0D))
        {
          this.error = 12008;
          return false;
        }
      }
      else if ((localObject1 == null) || (((Currency)localObject1).doubleValue() <= 0.0D))
      {
        this.error = 12008;
        return false;
      }
    }
    if ((!str.equals("RECTEMPLATE")) && (!str.equals("TEMPLATE")) && (!validateDate(paramHttpSession, paramWireTransfer))) {
      return false;
    }
    if (this.validate.indexOf("WIRE_PAYEE") != -1)
    {
      localObject1 = paramWireTransfer.getWirePayee();
      if (localObject1 != null)
      {
        if (m(((WireTransferPayee)localObject1).getPayeeName()) == true)
        {
          this.error = 12022;
          return false;
        }
        ((WireTransferPayee)localObject1).setAccountType("CHECKING");
        if (((WireTransferPayee)localObject1).getDestinationBank() == null)
        {
          this.error = 12051;
          return false;
        }
        if (m(((WireTransferPayee)localObject1).getAccountNum()) == true)
        {
          this.error = 12023;
          return false;
        }
        if (m(((WireTransferPayee)localObject1).getZipCode()) == true)
        {
          this.error = 12028;
          return false;
        }
        if (((WireTransferPayee)localObject1).getCountry().equalsIgnoreCase("UNITED STATES"))
        {
          ((WireTransferPayee)localObject1).getZipCodeValue().setFormat("5OR9");
          if (!((WireTransferPayee)localObject1).getZipCodeValue().isValid())
          {
            this.error = 12054;
            return false;
          }
        }
      }
      else if (paramWireTransfer.getWirePayeeID() == null)
      {
        this.error = 12016;
        return false;
      }
    }
    Object localObject1 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Object localObject2 = new HashMap();
    if (this.validate.indexOf("WIRE_TRANSFER_BANK") != -1)
    {
      localObject3 = paramWireTransfer.getWirePayee().getDestinationBank();
      int j;
      WireTransferBank localWireTransferBank;
      if (((WireTransferBank)localObject3).getRoutingNumber().length() == 0)
      {
        localWireTransferBanks1 = paramWireTransfer.getWirePayee().getIntermediaryBanks();
        if ((localWireTransferBanks1 == null) || (localWireTransferBanks1.size() == 0))
        {
          this.error = 12030;
          return false;
        }
        int i = 1;
        for (j = 0; j < localWireTransferBanks1.size(); j++)
        {
          localWireTransferBank = (WireTransferBank)localWireTransferBanks1.get(j);
          if (localWireTransferBank.getRoutingNumber().length() > 0)
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
      WireTransferBanks localWireTransferBanks1 = null;
      try
      {
        localWireTransferBanks1 = Wire.getWireTransferBanks((SecureUser)localObject1, (WireTransferBank)localObject3, 1, (HashMap)localObject2);
      }
      catch (CSILException localCSILException1)
      {
        localCSILException1.printStackTrace(System.err);
        this.error = 12051;
        return false;
      }
      if (localWireTransferBanks1.size() == 0)
      {
        this.error = 12051;
        return false;
      }
      ((WireTransferBank)localObject3).set((WireTransferBank)localWireTransferBanks1.get(0));
      WireTransferBanks localWireTransferBanks2 = paramWireTransfer.getWirePayee().getIntermediaryBanks();
      if (localWireTransferBanks2 != null)
      {
        if (localWireTransferBanks2.size() > 2)
        {
          this.error = 12055;
          return false;
        }
        for (j = 0; j < localWireTransferBanks2.size(); j++)
        {
          localWireTransferBank = (WireTransferBank)localWireTransferBanks2.get(j);
          try
          {
            localWireTransferBanks1 = Wire.getWireTransferBanks((SecureUser)localObject1, localWireTransferBank, 1, (HashMap)localObject2);
          }
          catch (CSILException localCSILException2)
          {
            localCSILException2.printStackTrace(System.err);
            this.error = 12051;
            return false;
          }
          if (localWireTransferBanks1.size() == 0)
          {
            this.error = 12051;
            return false;
          }
          localWireTransferBank.set((WireTransferBank)localWireTransferBanks1.get(0));
          if (localWireTransferBank.isSame((WireTransferBank)localObject3) == true)
          {
            this.error = 12047;
            return false;
          }
        }
      }
    }
    if (("RECTEMPLATE".equals(str)) || ("TEMPLATE".equals(str)))
    {
      if (m(paramWireTransfer.getWireName()) == true)
      {
        this.error = 12053;
        return false;
      }
    }
    else
    {
      boolean bool = Wire.isDuplicateWire((SecureUser)localObject1, paramWireTransfer, true, new HashMap());
      this.duplicateWire = ((this.duplicateWire) || (bool));
    }
    if ((this.validate.indexOf("BY_ORDER_OF") != -1) && ((!m(paramWireTransfer.getByOrderOfName())) || (!m(paramWireTransfer.getByOrderOfAddress1())) || (!m(paramWireTransfer.getByOrderOfAddress2())) || (!m(paramWireTransfer.getByOrderOfAddress3())) || (!m(paramWireTransfer.getByOrderOfAccount()))) && ((m(paramWireTransfer.getByOrderOfName())) || (m(paramWireTransfer.getByOrderOfAddress1())) || (m(paramWireTransfer.getByOrderOfAccount()))))
    {
      this.error = 12057;
      return false;
    }
    return true;
  }
  
  public boolean validateDate(HttpSession paramHttpSession, WireTransfer paramWireTransfer)
  {
    if (m(paramWireTransfer.getDueDate()) == true)
    {
      this.error = 12035;
      return false;
    }
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    DateTime localDateTime1 = new DateTime(localLocale);
    try
    {
      DateTime localDateTime2 = paramWireTransfer.getDueDateValue();
      DateTime localDateTime3 = new DateTime(localLocale);
      localDateTime3.fromString(paramWireTransfer.getDueDate());
      localDateTime3 = (DateTime)localDateTime2.clone();
      localDateTime3.set(1, localDateTime1.get(1));
      localDateTime3.set(2, localDateTime1.get(2));
      localDateTime3.set(5, localDateTime1.get(5));
      if (localDateTime2.before(localDateTime3) == true)
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
        ((ProcessingWindow)localObject1).setPaymentSubType(paramWireTransfer.getWireDestination());
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
      paramWireTransfer.setDateToPost((DateTime)localObject2);
      localDateTime3 = (DateTime)((DateTime)localObject2).clone();
      localDateTime3.set(1, localDateTime2.get(1));
      localDateTime3.set(2, localDateTime2.get(2));
      localDateTime3.set(5, localDateTime2.get(5));
      if (((DateTime)localObject2).after(localDateTime3)) {
        this.nonBusinessDay = true;
      }
      if (!m(paramWireTransfer.getSettlementDate()))
      {
        localDateTime3 = new DateTime(localLocale);
        localDateTime3.fromString(paramWireTransfer.getSettlementDate());
        localDateTime3 = (DateTime)paramWireTransfer.getSettlementDateValue().clone();
        localDateTime3.set(1, ((DateTime)localObject2).get(1));
        localDateTime3.set(2, ((DateTime)localObject2).get(2));
        localDateTime3.set(5, ((DateTime)localObject2).get(5));
        if (paramWireTransfer.getSettlementDateValue().before(localDateTime3) == true)
        {
          DebugLog.log("WireTransfer: The SettlementDate occurs before DateToPost, so changing SettlementDate to DateToPost.");
          paramWireTransfer.setSettlementDate((DateTime)localObject2);
        }
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      localInvalidDateTimeException.printStackTrace();
      this.error = 12035;
      return false;
    }
    catch (CSILException localCSILException1) {}
    return true;
  }
  
  public void setTimeOutValue(String paramString)
  {
    try
    {
      timeoutValue = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setApprovalCollectionSessionName(String paramString)
  {
    this.approvalCollectionSessionName = paramString;
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
  
  private boolean m(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.AddWireTransfersFromImport
 * JD-Core Version:    0.7.0.1
 */