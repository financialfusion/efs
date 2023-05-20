package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddTransfer
  extends EditTransfer
{
  protected Boolean currentlyProcessing = Boolean.FALSE;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  protected static long timeoutValue = 120000L;
  protected boolean useTemplate = false;
  private boolean e2 = false;
  
  public AddTransfer()
  {
    this.status = 1;
    this.datetype = "SHORT";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      str = initProcess(paramHttpServletRequest, localHttpSession);
    }
    else
    {
      str = addTransfer(localHttpSession);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("TransfersUpdated", "true");
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    this.date = new DateTime(this.locale);
    this.date.setFormat(this.datetype);
    this.transferDate = this.date.toString();
    this.currentlyProcessing = Boolean.FALSE;
    this.haveProcessed = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Object localObject2;
    Object localObject3;
    try
    {
      BankIdentifier localBankIdentifier = new BankIdentifier(this.locale);
      localObject2 = (AffiliateBank)paramHttpSession.getAttribute("AffiliateBank");
      if (localObject2 != null)
      {
        localBankIdentifier.setBankDirectoryId(((AffiliateBank)localObject2).getAffiliateRoutingNum());
      }
      else
      {
        localObject2 = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localSecureUser.getAffiliateIDValue(), null);
        localBankIdentifier.setBankDirectoryId(((AffiliateBank)localObject2).getAffiliateRoutingNum());
      }
      boolean bool = SmartCalendar.getIgnoreForTransfers(localSecureUser, localBankIdentifier, new HashMap());
      if (!bool)
      {
        localObject3 = SmartCalendar.getTransactionDay(localSecureUser, localBankIdentifier, this.date.getTime(), new HashMap());
        Calendar localCalendar1 = Calendar.getInstance(localSecureUser.getLocale());
        localCalendar1.setTime((Date)localObject3);
        Calendar localCalendar2 = Calendar.getInstance(localSecureUser.getLocale());
        localCalendar2.set(11, 0);
        localCalendar2.set(12, 0);
        localCalendar2.set(13, 0);
        localCalendar2.set(14, 0);
        Date localDate = localCalendar2.getTime();
        while (((Date)localObject3).before(localDate))
        {
          localCalendar1.add(6, 1);
          localObject3 = SmartCalendar.getTransactionDay(localSecureUser, localBankIdentifier, localCalendar1.getTime(), new HashMap());
        }
        this.date.setTime((Date)localObject3);
      }
    }
    catch (Exception localException) {}
    setCurrentTransfer(this);
    if (localSecureUser.getBusinessID() != 0) {
      this.e2 = true;
    }
    if ((this.useTemplate) && (paramHttpSession.getAttribute("Transfer") != null))
    {
      Object localObject1 = paramHttpSession.getAttribute("Transfer");
      localObject2 = (Transfer)paramHttpSession.getAttribute("Transfer");
      Transfer localTransfer = (Transfer)localObject1;
      if (((localObject1 instanceof RecTransfer)) && (this.e2))
      {
        localObject3 = (RecTransfer)localObject1;
        set((RecTransfer)localObject3);
      }
      else
      {
        set(localTransfer);
      }
      if (localTransfer.get("REGISTER_CATEGORY_ID") != null) {
        set("REGISTER_CATEGORY_ID", (String)((Transfer)localObject2).get("REGISTER_CATEGORY_ID"));
      }
    }
    return this.successURL;
  }
  
  protected String addTransfer(HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        int i = 0;
        synchronized (this)
        {
          if ((!this.currentlyProcessing.booleanValue()) && (!this.haveProcessed))
          {
            this.currentlyProcessing = Boolean.TRUE;
            i = 1;
          }
        }
        if (i != 0)
        {
          try
          {
            str = doProcess(paramHttpSession);
            this.nextURL = str;
          }
          catch (Exception localException1)
          {
            this.error = 1;
            str = this.serviceErrorURL;
            this.nextURL = str;
          }
          finally
          {
            this.currentlyProcessing = Boolean.FALSE;
          }
        }
        else
        {
          long l = System.currentTimeMillis();
          while ((!this.haveProcessed) && (this.currentlyProcessing.booleanValue() == true))
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
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str1 = null;
    Account localAccount1 = getFromAccount();
    Account localAccount2 = getToAccount();
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHttpSession.getAttribute(this.bankingServiceName);
    if (localAccount1 == null)
    {
      this.error = 1025;
      str1 = this.taskErrorURL;
    }
    else if (localAccount2 == null)
    {
      this.error = 1027;
      str1 = this.taskErrorURL;
    }
    else
    {
      boolean bool = true;
      Calendar localCalendar = (Calendar)getDateValue().clone();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      if (localSecureUser == null)
      {
        this.error = 1037;
        return this.taskErrorURL;
      }
      if (localSecureUser.getBusinessID() == 0) {
        setCustomerID(localSecureUser.getPrimaryUserID());
      } else {
        setCustomerID(localSecureUser.getBusinessID());
      }
      setBankID(localSecureUser.getBPWFIID());
      setSubmittedBy(localSecureUser.getProfileID());
      if (!getFromAccount().getCoreAccount().equals("1")) {
        setTransferDestination("ETOI");
      } else if (!getToAccount().getCoreAccount().equals("1")) {
        setTransferDestination("ITOE");
      } else if ((getTransferType().equals("TEMPLATE")) || (getTransferType().equals("RECTEMPLATE"))) {
        setTransferDestination("ITOI");
      } else {
        setTransferDestination("INTERNAL");
      }
      if ((this.numberTransfers > 1) && ((!"TEMPLATE".equals(this.transferType)) || (this.e2))) {
        bool = processAddRecTransfer(localAccount1, localAccount2, localBanking, paramHttpSession);
      } else {
        bool = processAddTransfer(localAccount1, localAccount2, localBanking, paramHttpSession);
      }
      if (bool)
      {
        str1 = this.successURL;
        if ((this.dateWasChangedURL != null) && (!"TEMPLATE".equals(this.transferType)))
        {
          DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
          String str2 = localDateFormat.format(localCalendar.getTime());
          String str3 = localDateFormat.format(getDateValue().getTime());
          if (!str2.equals(str3)) {
            str1 = this.dateWasChangedURL;
          }
        }
        this.haveProcessed = true;
      }
      else
      {
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  protected boolean processAddTransfer(Account paramAccount1, Account paramAccount2, com.ffusion.services.Banking paramBanking, HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    if (paramBanking != null) {
      localHashMap.put("SERVICE", paramBanking);
    }
    localHashMap.put("TOACCOUNT", paramAccount2);
    localHashMap.put("FROMACCOUNT", paramAccount1);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      setType(1);
      if ("TEMPLATE".equals(getTransferType()))
      {
        com.ffusion.csil.core.Banking.addTransferTemplate(localSecureUser, this, localHashMap);
      }
      else
      {
        setTransferType("Current");
        Transfer localTransfer1 = com.ffusion.csil.core.Banking.sendTransfer(localSecureUser, this, localHashMap);
        set(localTransfer1);
      }
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when adding transfer:");
      localException.printStackTrace();
    }
    if (this.error == 0)
    {
      Transfer localTransfer2 = new Transfer(this.locale);
      localTransfer2.set(this);
      paramHttpSession.setAttribute("Transfer", localTransfer2);
      Transfers localTransfers = (Transfers)paramHttpSession.getAttribute("Transfers");
      if (localTransfers != null) {
        localTransfers.add(localTransfer2);
      }
    }
    return this.error == 0;
  }
  
  protected boolean processAddRecTransfer(Account paramAccount1, Account paramAccount2, com.ffusion.services.Banking paramBanking, HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    if (paramBanking != null) {
      localHashMap.put("SERVICE", paramBanking);
    }
    localHashMap.put("FROMACCOUNT", paramAccount1);
    localHashMap.put("TOACCOUNT", paramAccount2);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      if ("RECTEMPLATE".equals(getTransferType()) == true)
      {
        com.ffusion.csil.core.Banking.addRecTransferTemplate(localSecureUser, this, localHashMap);
      }
      else
      {
        RecTransfer localRecTransfer1 = com.ffusion.csil.core.Banking.sendRecTransfer(localSecureUser, this, localHashMap);
        set(localRecTransfer1);
      }
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      RecTransfer localRecTransfer2 = new RecTransfer(this.locale);
      localRecTransfer2.set(this);
      paramHttpSession.setAttribute("Transfer", localRecTransfer2);
      RecTransfers localRecTransfers = (RecTransfers)paramHttpSession.getAttribute("RecTransfers");
      if (localRecTransfers != null) {
        localRecTransfers.add(localRecTransfer2);
      }
    }
    return this.error == 0;
  }
  
  public void setDate(String paramString)
  {
    this.transferDate = paramString;
    try
    {
      if (this.date == null) {
        this.date = new DateTime(this.transferDate, this.locale, this.datetype);
      } else {
        this.date.fromString(this.transferDate);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.date = null;
    }
  }
  
  public void setHaveProcessed(String paramString)
  {
    this.haveProcessed = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean isUseTemplate()
  {
    return this.useTemplate;
  }
  
  public void setUseTemplate(boolean paramBoolean)
  {
    this.useTemplate = paramBoolean;
  }
  
  public void setUseTemplate(String paramString)
  {
    this.useTemplate = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.AddTransfer
 * JD-Core Version:    0.7.0.1
 */