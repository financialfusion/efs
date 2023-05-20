package com.ffusion.tasks.billpay;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.BankIdentifier;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddPayment
  extends EditPayment
{
  private int Dk = 14;
  private Boolean Dj = Boolean.FALSE;
  private boolean Dh = false;
  protected static long timeoutValue = 120000L;
  protected String nextURL;
  private boolean Di = false;
  
  public AddPayment()
  {
    this.status = 1;
    this.datetype = "SHORT";
    this.paymentSessionName = null;
    this.frequency = 0;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
    return super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser.getBusinessID() != 0) {
      this.Di = true;
    }
    if (this.templateID != null)
    {
      localObject1 = (FundsTransactions)paramHttpSession.getAttribute("PaymentTemplates");
      if (this.paymentType == null) {
        this.paymentType = "TEMPLATE";
      }
      localObject2 = ((FundsTransactions)localObject1).getFirstByFilter("ID=" + this.templateID + ",PaymentType=" + this.paymentType + ",AND");
      if ((localObject1 != null) && (localObject2 != null) && ((localObject2 instanceof Payment)))
      {
        if (((localObject2 instanceof RecPayment)) && (this.Di))
        {
          localObject3 = (RecPayment)localObject2;
          set((RecPayment)localObject3);
        }
        else
        {
          localObject3 = (Payment)localObject2;
          set((Payment)localObject3);
          setFrequency(0);
        }
        if (this.payee != null) {
          this.payeeID = this.payee.getID();
        }
        if (this.account != null) {
          this.accountID = this.account.getID();
        }
        setTrackingID(null);
        this.paymentType = "PAYMENT";
      }
    }
    if (this.paymentSessionName != null)
    {
      localObject1 = paramHttpSession.getAttribute(this.paymentSessionName);
      if ((localObject1 != null) && ((localObject1 instanceof Payment)))
      {
        if (((localObject1 instanceof RecPayment)) && ((!"TEMPLATE".equals(this.paymentType)) || (this.Di)))
        {
          localObject2 = (RecPayment)localObject1;
          set((RecPayment)localObject2);
        }
        else
        {
          localObject2 = (Payment)localObject1;
          set((Payment)localObject2);
        }
        if (this.payee != null) {
          this.payeeID = this.payee.getID();
        }
        if (this.account != null) {
          this.accountID = this.account.getID();
        }
        setTrackingID(null);
        this.paymentType = "PAYMENT";
      }
    }
    Object localObject1 = paramHttpSession.getAttribute("LoadPaymentTemplate");
    if ((localObject1 != null) && ((localObject1 instanceof Payment)))
    {
      if (((localObject1 instanceof RecPayment)) && ((!"TEMPLATE".equals(this.paymentType)) || (this.Di)))
      {
        localObject2 = (RecPayment)localObject1;
        set((RecPayment)localObject2);
      }
      else
      {
        localObject2 = (Payment)localObject1;
        set((Payment)localObject2);
      }
      if (this.payee != null) {
        this.payeeID = this.payee.getID();
      }
      if (this.account != null) {
        this.accountID = this.account.getID();
      }
      setTrackingID(null);
      this.paymentType = "PAYMENT";
    }
    Object localObject2 = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    Object localObject3 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    BankIdentifier localBankIdentifier = new BankIdentifier((Locale)localObject2);
    this.payDate = new DateTime((Locale)localObject2);
    AffiliateBank localAffiliateBank = (AffiliateBank)paramHttpSession.getAttribute("AffiliateBank");
    if (localAffiliateBank != null) {
      localBankIdentifier.setBankDirectoryId(localAffiliateBank.getAffiliateRoutingNum());
    } else {
      try
      {
        localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID((SecureUser)localObject3, ((SecureUser)localObject3).getAffiliateIDValue(), null);
        localBankIdentifier.setBankDirectoryId(localAffiliateBank.getAffiliateRoutingNum());
      }
      catch (Exception localException1)
      {
        Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
        localBankIdentifier.setBankDirectoryId(((Account)localAccounts.get(0)).getRoutingNum());
      }
    }
    try
    {
      Date localDate = SmartCalendar.getTransactionDay((SecureUser)localObject3, localBankIdentifier, this.payDate.getTime(), new HashMap());
      this.payDate = new DateTime(localDate, (Locale)localObject2, this.datetype);
    }
    catch (Exception localException2) {}
    this.payDate.setFormat(this.datetype);
    this.paymentDate = this.payDate.toString();
    paramHttpSession.removeAttribute("Payee");
    this.Dj = Boolean.FALSE;
    this.Dh = false;
    setCurrentPayment(this);
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    Account localAccount = getAccount();
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    this.nextURL = this.successURL;
    int i = 0;
    synchronized (this)
    {
      if ((!this.Dj.booleanValue()) && (!this.Dh))
      {
        this.Dj = Boolean.TRUE;
        i = 1;
      }
    }
    if (i != 0)
    {
      try
      {
        if ((this.numberPayments > 1) && ((!"TEMPLATE".equals(this.paymentType)) || (this.Di))) {
          processAddRecPayment(localAccount, localBillPay, paramHttpSession);
        } else {
          processAddPayment(localAccount, localBillPay, paramHttpSession);
        }
      }
      catch (Exception localException1)
      {
        this.error = 1;
        this.nextURL = this.serviceErrorURL;
      }
      finally
      {
        this.Dj = Boolean.FALSE;
      }
    }
    else
    {
      long l = System.currentTimeMillis();
      while ((!this.Dh) && (this.Dj.booleanValue() == true))
      {
        if (System.currentTimeMillis() - l > timeoutValue)
        {
          if (this.error != 0) {
            break;
          }
          this.error = 1;
          this.nextURL = this.serviceErrorURL;
          break;
        }
        try
        {
          Thread.sleep(2000L);
        }
        catch (Exception localException2) {}
      }
    }
    if ((this.error != 0) && (this.nextURL == this.successURL)) {
      this.nextURL = this.serviceErrorURL;
    }
    return this.nextURL;
  }
  
  protected void processAddPayment(Account paramAccount, BillPay paramBillPay, HttpSession paramHttpSession)
  {
    Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
    Payments localPayments = new Payments(this.locale);
    Payment localPayment = (Payment)localPayments.create();
    localPayment.set(this);
    int i = 0;
    int j = 0;
    while ((j == 0) && (i <= this.Dk))
    {
      HashMap localHashMap = new HashMap();
      if (paramBillPay != null) {
        localHashMap.put("SERVICE", paramBillPay);
      }
      localHashMap.put("ACCOUNT", getAccount());
      localHashMap.put("PAYEES", localPayees);
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        String str = null;
        if (getAccount() != null) {
          str = getAccount().getCurrencyCode();
        }
        localHashMap.put("Curdef", str);
        localPayment.setType(3);
        if (this.error == 0) {
          if (localPayment.getPaymentType().equals("TEMPLATE"))
          {
            Billpay.addPaymentTemplate(localSecureUser, localPayment, localHashMap);
          }
          else
          {
            localPayments = Billpay.sendPayments(localSecureUser, localPayments, localHashMap);
            localPayment.set((Payment)localPayments.get(0));
          }
        }
      }
      catch (CSILException localCSILException)
      {
        if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
          paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
        }
        this.error = MapError.mapError(localCSILException, paramHttpSession);
        this.nextURL = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        this.error = localPayment.getErrorValue();
        if (this.error == 20003) {
          paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
        }
        if (this.error != 0) {
          this.nextURL = this.serviceErrorURL;
        }
      }
      if ((this.error == 2004) || (localPayment.getStatus() == 8))
      {
        i++;
        DateTime localDateTime = localPayment.getPayDateValue();
        localDateTime.add(5, 1);
        int k = localDateTime.get(7);
        if (k == 7) {
          localDateTime.add(5, 2);
        } else if (k == 1) {
          localDateTime.add(5, 1);
        }
        localPayment.setPayDate(localDateTime);
      }
      else if (this.error == 0)
      {
        if (!"TEMPLATE".equals(getPaymentType()))
        {
          paramHttpSession.setAttribute("Payment", localPayment);
          localPayments = (Payments)paramHttpSession.getAttribute("Payments");
          if (localPayments != null) {
            localPayments.add(localPayment);
          }
        }
        j = 1;
        this.Dh = true;
      }
      else
      {
        j = 1;
      }
    }
  }
  
  protected void processAddRecPayment(Account paramAccount, BillPay paramBillPay, HttpSession paramHttpSession)
  {
    Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
    RecPayments localRecPayments = new RecPayments(this.locale);
    RecPayment localRecPayment = (RecPayment)localRecPayments.create();
    localRecPayment.set(this);
    HashMap localHashMap = new HashMap();
    if (paramBillPay != null) {
      localHashMap.put("SERVICE", paramBillPay);
    }
    localHashMap.put("ACCOUNT", getAccount());
    localHashMap.put("PAYEES", localPayees);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      String str = null;
      if (getAccount() != null) {
        str = getAccount().getCurrencyCode();
      }
      localHashMap.put("Curdef", str);
      if (localRecPayment.getPaymentType().equals("TEMPLATE"))
      {
        localRecPayment.setPaymentType("RECTEMPLATE");
        Billpay.addPaymentTemplate(localSecureUser, localRecPayment, localHashMap);
      }
      else
      {
        localRecPayments = Billpay.sendRecPayments(localSecureUser, localRecPayments, localHashMap);
        localRecPayment.set((RecPayment)localRecPayments.get(0));
      }
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      this.nextURL = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      this.error = localRecPayment.getErrorValue();
      if (this.error == 20003) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      if (this.error != 0) {
        this.nextURL = this.serviceErrorURL;
      }
    }
    if (this.error == 0)
    {
      if ((!"TEMPLATE".equals(getPaymentType())) && (!"RECTEMPLATE".equals(getPaymentType())))
      {
        localRecPayments = (RecPayments)paramHttpSession.getAttribute("RecPayments");
        if (localRecPayments != null) {
          localRecPayments.add(localRecPayment);
        }
        paramHttpSession.setAttribute("Payment", localRecPayment);
      }
      this.Dh = true;
    }
  }
  
  public void setPaymentRetry(String paramString)
  {
    this.Dk = Integer.valueOf(paramString).intValue();
  }
  
  public void setHaveProcessed(String paramString)
  {
    this.Dh = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.AddPayment
 * JD-Core Version:    0.7.0.1
 */