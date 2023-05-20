package com.ffusion.tasks.billpay;

import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    paramHttpSession.removeAttribute("com.ffusion.services.BillPay");
    paramHttpSession.removeAttribute("BillPayAccounts");
    paramHttpSession.removeAttribute("BillPayAccount");
    paramHttpSession.removeAttribute("Payee");
    paramHttpSession.removeAttribute("Payees");
    paramHttpSession.removeAttribute("Payment");
    paramHttpSession.removeAttribute("Payments");
    paramHttpSession.removeAttribute("PayHistory");
    paramHttpSession.removeAttribute("RecPayments");
    paramHttpSession.removeAttribute("PayHistoryPayees");
    paramHttpSession.removeAttribute("PaymentReportData");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.SignOff
 * JD-Core Version:    0.7.0.1
 */