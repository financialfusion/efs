package com.ffusion.tasks.billpay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddPaymentBatch
  extends EditPaymentBatch
{
  protected Boolean currentlyProcessing = Boolean.FALSE;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  protected static long timeoutValue = 120000L;
  protected String sortBy = "NAME";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
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
        this.error = 0;
        this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
        if (localHttpSession.getAttribute("Payees") == null)
        {
          this.error = 2002;
          str = this.taskErrorURL;
        }
        else if (localHttpSession.getAttribute("BillPayAccounts") == null)
        {
          this.error = 2005;
          str = this.taskErrorURL;
        }
        else if (this.initFlag)
        {
          initProcess(localHttpSession);
        }
        else if (this.accountChanged)
        {
          this.error = accountChangedPayments(localHttpSession);
          if (this.error != 0) {
            str = this.taskErrorURL;
          }
        }
        else if (this.accountID == null)
        {
          this.error = 2007;
          str = this.taskErrorURL;
        }
        else
        {
          ??? = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
          this.account = ((Accounts)???).getByID(this.accountID);
          if (this.account == null)
          {
            this.error = 2007;
            str = this.taskErrorURL;
          }
          else
          {
            this.error = validateInput(localHttpSession);
          }
          if (this.error == 0)
          {
            if (getPayments().size() > 0)
            {
              if (this.processFlag)
              {
                this.processFlag = false;
                if (this.account != null)
                {
                  removePayments();
                  this.error = doProcess(localHttpSession);
                  if (this.error == 0) {
                    this.haveProcessed = true;
                  } else {
                    str = this.nextURL;
                  }
                }
                else
                {
                  this.error = 2007;
                  str = this.taskErrorURL;
                }
              }
            }
            else
            {
              this.error = 2003;
              str = this.taskErrorURL;
            }
          }
          else {
            str = this.taskErrorURL;
          }
        }
      }
      catch (Exception localException1)
      {
        this.error = 1;
        str = this.serviceErrorURL;
      }
      finally
      {
        this.currentlyProcessing = Boolean.FALSE;
      }
      this.nextURL = str;
    }
    else
    {
      long l1 = System.currentTimeMillis();
      long l2 = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      while ((!this.haveProcessed) && (this.currentlyProcessing.booleanValue() == true))
      {
        if (System.currentTimeMillis() - l1 > l2)
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
    return str;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    int i = 1;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    int j = 0;
    if (localSecureUser.getBusinessID() == 0) {
      j = localSecureUser.getPrimaryUserID();
    } else {
      j = localSecureUser.getBusinessID();
    }
    Object localObject2;
    Object localObject3;
    Object localObject4;
    Object localObject5;
    Payment localPayment;
    if (this.templateID != null)
    {
      localObject1 = (FundsTransactions)paramHttpSession.getAttribute("PaymentTemplates");
      localObject2 = ((FundsTransactions)localObject1).getByID(this.templateID);
      if ((localObject1 != null) && (localObject2 != null) && ((localObject2 instanceof PaymentBatch)))
      {
        this.account = null;
        this.accountID = null;
        localObject3 = getDateFormat();
        localObject4 = (PaymentBatch)localObject2;
        set((PaymentBatch)localObject4);
        setTrackingID(null);
        setDateFormat((String)localObject3);
        this.batchType = "PAYMENT";
        localObject5 = this.payments.listIterator();
        while (((Iterator)localObject5).hasNext())
        {
          localPayment = (Payment)((Iterator)localObject5).next();
          localPayment.setID(String.valueOf(i++));
          localPayment.setCustomerID(j);
          localPayment.setDateFormat(getDateFormat());
          localPayment.setStatus(1);
          if ((this.account == null) && (localPayment.getAccount() != null))
          {
            this.account = localPayment.getAccount();
            this.accountID = localPayment.getAccountID();
          }
        }
      }
    }
    Object localObject1 = paramHttpSession.getAttribute("LoadPaymentTemplate");
    if ((localObject1 != null) && ((localObject1 instanceof PaymentBatch)))
    {
      this.account = null;
      this.accountID = null;
      localObject2 = (PaymentBatch)localObject1;
      localObject3 = getDateFormat();
      set((PaymentBatch)localObject2);
      this.batchType = "PAYMENT";
      setDateFormat((String)localObject3);
      setTrackingID(null);
      localObject4 = this.payments.listIterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (Payment)((Iterator)localObject4).next();
        ((Payment)localObject5).setID(String.valueOf(i++));
        ((Payment)localObject5).setCustomerID(j);
        ((Payment)localObject5).setPaymentType("Current");
        ((Payment)localObject5).setDateFormat(getDateFormat());
        ((Payment)localObject5).setStatus(1);
        if ((this.account == null) && (((Payment)localObject5).getAccount() != null))
        {
          this.account = ((Payment)localObject5).getAccount();
          this.accountID = ((Payment)localObject5).getAccountID();
        }
      }
    }
    else
    {
      localObject2 = new Payments();
      setPayments((Payments)localObject2);
      localObject3 = (Payees)paramHttpSession.getAttribute("Payees");
      localObject4 = ((Payees)localObject3).iterator();
      ((Payees)localObject3).setSortedBy(this.sortBy);
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (Payee)((Iterator)localObject4).next();
        localPayment = (Payment)((Payments)localObject2).create();
        localPayment.setPaymentType("TEMPLATE");
        localPayment.setPayee((Payee)localObject5);
        localPayment.setPayeeName(((Payee)localObject5).getName());
        localPayment.setID(String.valueOf(i++));
        localPayment.setDateFormat(getDateFormat());
        localPayment.setCustomerID(j);
        localPayment.setStatus(1);
      }
    }
    setCustomerID(j);
    setFIID(localSecureUser.getBPWFIID());
    setSubmittedBy(localSecureUser.getProfileID());
    setSubmitDate(new DateTime(this.locale));
    if (this.payments.size() > 0) {
      setCurrentPayment((Payment)this.payments.get(0));
    }
    this.currentlyProcessing = Boolean.FALSE;
    this.haveProcessed = false;
  }
  
  protected int doProcess(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    localHashMap.put("PAYEES", paramHttpSession.getAttribute("Payees"));
    localHashMap.put("ACCOUNTS", paramHttpSession.getAttribute("BillPayAccounts"));
    Iterator localIterator = getPayments().iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      localPayment.setAccount(this.account);
      localPayment.setAction("add");
    }
    setAmount(new Currency("0.00", this.account.getCurrencyCode(), this.locale));
    setPaymentCount(this.payments.size());
    try
    {
      Billpay.addPaymentBatch(localSecureUser, this, localHashMap);
      paramHttpSession.setAttribute("PaymentBatch", this);
      this.haveProcessed = true;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      this.nextURL = this.successURL;
    } else {
      this.nextURL = this.serviceErrorURL;
    }
    return this.error;
  }
  
  public void setSortBy(String paramString)
  {
    this.sortBy = paramString;
  }
  
  public void setHaveProcessed(String paramString)
  {
    this.haveProcessed = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.AddPaymentBatch
 * JD-Core Version:    0.7.0.1
 */