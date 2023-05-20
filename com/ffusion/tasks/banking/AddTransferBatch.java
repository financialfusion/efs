package com.ffusion.tasks.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.TransferBatches;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddTransferBatch
  extends EditTransferBatch
{
  protected Boolean currentlyProcessing = Boolean.FALSE;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  protected static long timeoutValue = 120000L;
  protected boolean useTemplate = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      initProcess(localHttpSession);
    }
    else
    {
      str = addTransferBatch(localHttpSession);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("TransfersUpdated", "true");
    }
    return str;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime(localSecureUser.getLocale());
    Object localObject;
    try
    {
      BankIdentifier localBankIdentifier = new BankIdentifier(this.locale);
      localObject = (AffiliateBank)paramHttpSession.getAttribute("AffiliateBank");
      if (localObject != null)
      {
        localBankIdentifier.setBankDirectoryId(((AffiliateBank)localObject).getAffiliateRoutingNum());
      }
      else
      {
        localObject = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localSecureUser.getAffiliateIDValue(), null);
        localBankIdentifier.setBankDirectoryId(((AffiliateBank)localObject).getAffiliateRoutingNum());
      }
      boolean bool = SmartCalendar.getIgnoreForTransfers(localSecureUser, localBankIdentifier, new HashMap());
      if (!bool)
      {
        Calendar localCalendar1 = Calendar.getInstance(localSecureUser.getLocale());
        localCalendar1.set(11, 0);
        localCalendar1.set(12, 0);
        localCalendar1.set(13, 0);
        localCalendar1.set(14, 0);
        Date localDate1 = localCalendar1.getTime();
        Date localDate2 = SmartCalendar.getTransactionDay(localSecureUser, localBankIdentifier, localDate1, new HashMap());
        Calendar localCalendar2 = Calendar.getInstance(localSecureUser.getLocale());
        localCalendar2.setTime(localDate2);
        while (localDate2.before(localDate1))
        {
          localCalendar2.add(6, 1);
          localDate2 = SmartCalendar.getTransactionDay(localSecureUser, localBankIdentifier, localCalendar2.getTime(), new HashMap());
        }
        localDateTime.setTime(localDate2);
      }
    }
    catch (Exception localException) {}
    if ((this.useTemplate) && (paramHttpSession.getAttribute("TransferBatch") != null))
    {
      TransferBatch localTransferBatch = (TransferBatch)paramHttpSession.getAttribute("TransferBatch");
      set(localTransferBatch);
      setBatchType("BATCH");
      localObject = getTransfers().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Transfer localTransfer = (Transfer)((Iterator)localObject).next();
        localTransfer.setDateToPost((com.ffusion.beans.DateTime)null);
        localTransfer.setDate(localDateTime);
        localTransfer.setDateFormat(this.datetype);
        localTransfer.setTransferType("Current");
      }
    }
    if (getTransfers() == null) {
      setTransfers(new Transfers());
    }
    for (int i = getTransfers().size(); i < getMaxTransfersValue(); i++)
    {
      localObject = (Transfer)getTransfers().create();
      ((Transfer)localObject).setID(String.valueOf(i));
      ((Transfer)localObject).setDate(localDateTime);
      ((Transfer)localObject).setDateFormat(this.datetype);
      ((Transfer)localObject).setTransferType("Current");
    }
    setCurrentTransfer((Transfer)getTransfers().get(0));
  }
  
  protected String addTransferBatch(HttpSession paramHttpSession)
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
    String str = null;
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHttpSession.getAttribute(this.bankingServiceName);
    boolean bool = true;
    setSubmitDate(new com.ffusion.util.beans.DateTime(this.locale));
    bool = processAddTransferBatch(localBanking, paramHttpSession);
    if (bool)
    {
      str = this.successURL;
      this.haveProcessed = true;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean processAddTransferBatch(com.ffusion.services.Banking paramBanking, HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    if (paramBanking != null) {
      localHashMap.put("SERVICE", paramBanking);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(getAccountsName());
    if (localAccounts != null) {
      put("Accounts", localAccounts);
    }
    if (!getHasSingleUserAssignedAmountCurrency()) {
      setAmount(new Currency(new BigDecimal(0.0D), null, this.locale));
    }
    Object localObject1 = getTransfers().iterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Transfer)((Iterator)localObject1).next();
      if ((((Transfer)localObject2).getFromAccount() == null) || (((Transfer)localObject2).getToAccount() == null) || (((Transfer)localObject2).getAmountValue() == null)) {
        ((Iterator)localObject1).remove();
      } else if (!((Transfer)localObject2).getFromAccount().getCoreAccount().equals("1")) {
        ((Transfer)localObject2).setTransferDestination("ETOI");
      } else if (!((Transfer)localObject2).getToAccount().getCoreAccount().equals("1")) {
        ((Transfer)localObject2).setTransferDestination("ITOE");
      } else if ((((Transfer)localObject2).getTransferType().equals("TEMPLATE")) || (((Transfer)localObject2).getTransferType().equals("RECTEMPLATE"))) {
        ((Transfer)localObject2).setTransferDestination("ITOI");
      } else {
        ((Transfer)localObject2).setTransferDestination("INTERNAL");
      }
    }
    this.error = 0;
    try
    {
      if ("TEMPLATE".equals(getBatchType()))
      {
        localObject1 = new TransferBatch(this.locale);
        ((TransferBatch)localObject1).set(this);
        com.ffusion.csil.core.Banking.addTransferBatch(localSecureUser, (TransferBatch)localObject1, localHashMap);
      }
      else
      {
        com.ffusion.csil.core.Banking.addTransferBatch(localSecureUser, this, localHashMap);
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
      TransferBatch localTransferBatch = new TransferBatch(this.locale);
      localTransferBatch.set(this);
      paramHttpSession.setAttribute("TransferBatch", localTransferBatch);
      localObject2 = (TransferBatches)paramHttpSession.getAttribute("TransferBatches");
      if (localObject2 != null) {
        ((TransferBatches)localObject2).add(localTransferBatch);
      }
    }
    return this.error == 0;
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
 * Qualified Name:     com.ffusion.tasks.banking.AddTransferBatch
 * JD-Core Version:    0.7.0.1
 */