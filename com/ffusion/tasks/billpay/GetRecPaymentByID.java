package com.ffusion.tasks.billpay;

public class GetRecPaymentByID
  extends GetPaymentByID
  implements Task
{
  public GetRecPaymentByID()
  {
    this.beanSessionName = "Payment";
    this.isRecurring = true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetRecPaymentByID
 * JD-Core Version:    0.7.0.1
 */