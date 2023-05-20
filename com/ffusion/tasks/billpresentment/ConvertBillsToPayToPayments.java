package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.beans.billpresentment.Biller;
import com.ffusion.beans.billpresentment.Billers;
import com.ffusion.beans.billpresentment.EBillAccount;
import com.ffusion.beans.billpresentment.EBillAccounts;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConvertBillsToPayToPayments
  extends Payments
  implements Task
{
  private EBillAccounts Hl;
  private Billers Hn;
  private Payees Hi;
  private int Hk;
  private String Hm = null;
  private String Ho;
  private String Hj;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    clear();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    int i = convert(localHttpSession);
    if (i == 0) {
      str = this.Hm;
    } else if (i == -1) {
      str = this.Ho;
    }
    return str;
  }
  
  public int convert(HttpSession paramHttpSession)
  {
    int i = 0;
    this.Hl = ((EBillAccounts)paramHttpSession.getAttribute("EBillAccounts"));
    this.Hn = ((Billers)paramHttpSession.getAttribute("Billers"));
    BillSummaries localBillSummaries = (BillSummaries)paramHttpSession.getAttribute("BillsToPay");
    this.Hi = ((Payees)paramHttpSession.getAttribute("Payees"));
    if (this.Hi == null)
    {
      this.Hk = 6515;
      i = -1;
      return i;
    }
    if (this.Hl == null)
    {
      this.Hk = 6510;
      i = -1;
      return i;
    }
    if (this.Hn == null)
    {
      this.Hk = 6503;
      i = -1;
      return i;
    }
    if (localBillSummaries != null)
    {
      Iterator localIterator = localBillSummaries.iterator();
      while (localIterator.hasNext())
      {
        BillSummary localBillSummary = (BillSummary)localIterator.next();
        if (convertToPayment(localBillSummary, paramHttpSession) != 0)
        {
          i = -1;
          return i;
        }
      }
      paramHttpSession.setAttribute("PaymentsToPay", this);
    }
    return i;
  }
  
  protected int convertToPayment(BillSummary paramBillSummary, HttpSession paramHttpSession)
  {
    Payment localPayment = (Payment)create();
    EBillAccount localEBillAccount = this.Hl.getByAccountID(paramBillSummary.getAccountIDValue());
    if (localEBillAccount == null)
    {
      this.Hk = 6702;
      return -1;
    }
    Biller localBiller = this.Hn.getByBillerID(localEBillAccount.getBillerIDValue());
    if (localBiller == null)
    {
      this.Hk = 6704;
      return -1;
    }
    if (paramBillSummary.getAmountPaidValue() == null)
    {
      this.Hk = 6604;
      return -1;
    }
    localPayment.setAmount(String.valueOf(paramBillSummary.getAmountPaidValue().doubleValue()));
    if (paramBillSummary.getPaymentDate() == null)
    {
      this.Hk = 6600;
      return -1;
    }
    localPayment.setPayDate(paramBillSummary.getPaymentDateValue());
    Payees localPayees = new Payees();
    Payee localPayee = localPayees.create();
    if (localEBillAccount.getPayeeID() != null) {
      localPayee = this.Hi.getByID(localEBillAccount.getPayeeID());
    } else if ((localPayee == null) || (localBiller.getBillerName() != null)) {
      localPayee = this.Hi.getByName(localBiller.getBillerName());
    }
    if (localPayee == null)
    {
      this.Hk = 6703;
      return -1;
    }
    localPayment.setPayee(localPayee);
    localPayment.setAccountID(paramBillSummary.getPaymentAccountID());
    if (localPayee.getName() != null) {
      localPayment.setPayeeName(localPayment.getPayee().getName());
    }
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
    if (localAccounts == null)
    {
      this.Hk = 6518;
      return -1;
    }
    Account localAccount = localAccounts.getByID(localPayment.getAccountID());
    localPayment.setAccount(localAccount);
    localPayment.set("BILLSUMMARYID", paramBillSummary.getBillSummaryID());
    return 0;
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.Hm = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.Ho = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Hj = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.Hk);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ConvertBillsToPayToPayments
 * JD-Core Version:    0.7.0.1
 */