package com.ffusion.beans.billpresentment;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class PaymentInfos
  extends FilteredList
{
  public static final String PAYMENTINFOS = "PAYMENTINFOS";
  private String jdField_byte = "SHORT";
  
  public PaymentInfos() {}
  
  public PaymentInfos(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setDateFormat(String paramString)
  {
    this.jdField_byte = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PaymentInfo localPaymentInfo = (PaymentInfo)localIterator.next();
      localPaymentInfo.setDateFormat(this.jdField_byte);
    }
  }
  
  public String getDateFormat()
  {
    return this.jdField_byte;
  }
  
  public PaymentInfo add()
  {
    PaymentInfo localPaymentInfo = new PaymentInfo(this.locale);
    add(localPaymentInfo);
    return localPaymentInfo;
  }
  
  public PaymentInfo create()
  {
    PaymentInfo localPaymentInfo = new PaymentInfo(this.locale);
    return localPaymentInfo;
  }
  
  public PaymentInfo getByPaymentInfoID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PaymentInfo localPaymentInfo = (PaymentInfo)localIterator.next();
      if (localPaymentInfo.getPaymentInfoID() == paramLong)
      {
        localObject = localPaymentInfo;
        break;
      }
    }
    return localObject;
  }
  
  public PaymentInfo getByBillSummaryID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PaymentInfo localPaymentInfo = (PaymentInfo)localIterator.next();
      if (localPaymentInfo.getBillSummaryID().equalsIgnoreCase(paramString))
      {
        localObject = localPaymentInfo;
        break;
      }
    }
    return localObject;
  }
  
  public PaymentInfo getByPaymentRefNum(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PaymentInfo localPaymentInfo = (PaymentInfo)localIterator.next();
      if (localPaymentInfo.getPaymentRefNum().equalsIgnoreCase(paramString))
      {
        localObject = localPaymentInfo;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByPaymentInfoID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PaymentInfo localPaymentInfo = (PaymentInfo)localIterator.next();
      if (localPaymentInfo.getPaymentInfoID() == paramLong)
      {
        localObject = localPaymentInfo;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PAYMENTINFOS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((PaymentInfo)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PAYMENTINFOS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("PAYMENTINFO"))
      {
        PaymentInfo localPaymentInfo = new PaymentInfo();
        PaymentInfos.this.add(localPaymentInfo);
        localPaymentInfo.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.PaymentInfos
 * JD-Core Version:    0.7.0.1
 */