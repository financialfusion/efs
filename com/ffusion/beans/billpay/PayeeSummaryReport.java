package com.ffusion.beans.billpay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class PayeeSummaryReport
  extends PaymentReport
{
  public PayeeSummaryReport(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Payments localPayments = getPayments();
    if (localPayments != null)
    {
      Iterator localIterator = localPayments.iterator();
      Payment localPayment = null;
      while (localIterator.hasNext())
      {
        localPayment = (Payment)localIterator.next();
        appendString(localStringBuffer, localPayment.getPayeeName());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localPayment.getAmount());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PayeeSummaryReport
 * JD-Core Version:    0.7.0.1
 */