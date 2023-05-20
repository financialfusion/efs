package com.ffusion.tasks.billpresentment;

import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    paramHttpSession.removeAttribute("Biller");
    paramHttpSession.removeAttribute("Billers");
    paramHttpSession.removeAttribute("Publisher");
    paramHttpSession.removeAttribute("Publishers");
    paramHttpSession.removeAttribute("Consumer");
    paramHttpSession.removeAttribute("Consumers");
    paramHttpSession.removeAttribute("EBillAccount");
    paramHttpSession.removeAttribute("EBillAccounts");
    paramHttpSession.removeAttribute("BillSummary");
    paramHttpSession.removeAttribute("BillSummaries");
    paramHttpSession.removeAttribute("FilteredBillSummaries");
    paramHttpSession.removeAttribute("BillsToPay");
    paramHttpSession.removeAttribute("ConsumerStatus");
    paramHttpSession.removeAttribute("TCURL");
    paramHttpSession.removeAttribute("PaymentInfos");
    paramHttpSession.removeAttribute("BillPayErrors");
    paramHttpSession.removeAttribute("PaymentsToPay");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.SignOff
 * JD-Core Version:    0.7.0.1
 */