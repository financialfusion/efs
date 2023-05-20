package com.ffusion.beans.billpay;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class PaymentBatches
  extends FilteredList
  implements Localeable
{
  public static final String PAYMENT_BATCHES = "PAYMENT_BATCHES";
  protected String dateformat;
  
  public PaymentBatches() {}
  
  public PaymentBatches(Locale paramLocale)
  {
    super(paramLocale);
    this.dateformat = "SHORT";
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PaymentBatch localPaymentBatch = (PaymentBatch)localIterator.next();
      localPaymentBatch.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public Object create()
  {
    PaymentBatch localPaymentBatch = new PaymentBatch(this.locale);
    add(localPaymentBatch);
    return localPaymentBatch;
  }
  
  public Object createNoAdd()
  {
    return new PaymentBatch(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    PaymentBatch localPaymentBatch = (PaymentBatch)paramObject;
    localPaymentBatch.setLocale(this.locale);
    return super.add(localPaymentBatch);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PAYMENT_BATCHES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((PaymentBatch)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PAYMENT_BATCHES");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PaymentBatches
 * JD-Core Version:    0.7.0.1
 */