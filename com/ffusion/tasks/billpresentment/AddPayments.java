package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.util.FilteredList;
import java.util.Iterator;
import javax.servlet.http.HttpSession;

public class AddPayments
  extends com.ffusion.tasks.billpay.AddPayments
{
  protected int initPayments(HttpSession paramHttpSession)
  {
    int i = 0;
    this.initFlag = false;
    init(paramHttpSession);
    return i;
  }
  
  protected void init(HttpSession paramHttpSession)
  {
    Payments localPayments = (Payments)paramHttpSession.getAttribute("PaymentsToPay");
    Iterator localIterator = localPayments.iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      add(localPayment);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.AddPayments
 * JD-Core Version:    0.7.0.1
 */