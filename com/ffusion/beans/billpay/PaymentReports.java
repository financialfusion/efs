package com.ffusion.beans.billpay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class PaymentReports
  extends FilteredList
  implements PaymentReportConsts, IReportResult, ExportFormats, Serializable
{
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  protected Currency total;
  
  public Object create()
  {
    PaymentReport localPaymentReport = new PaymentReport(this.locale);
    add(localPaymentReport);
    return localPaymentReport;
  }
  
  public static Object createNoAdd(Locale paramLocale)
  {
    return new PaymentReport(paramLocale);
  }
  
  public void setTotal(Currency paramCurrency)
  {
    this.total = paramCurrency;
    if (this.total != null) {
      this.total.setLocale(this.locale);
    }
  }
  
  public String getTotal()
  {
    if (this.total != null) {
      return this.total.toString();
    }
    return "";
  }
  
  public Currency getAmountValue()
  {
    return this.total;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',', paramHashMap);
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t', paramHashMap);
    }
    return localStringBuffer;
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
    XMLHandler.appendBeginTag(localStringBuffer, "PAYMENTREPORTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((PaymentReport)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PAYMENTREPORTS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  protected StringBuffer getDelimitedReport(char paramChar, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = iterator();
    PaymentReport localPaymentReport = null;
    while (localIterator.hasNext())
    {
      localPaymentReport = (PaymentReport)localIterator.next();
      localStringBuffer.append(((StringBuffer)localPaymentReport.export("COMMA", paramHashMap)).toString());
      ExtendABean.appendString(localStringBuffer, localPaymentReport.getTotal());
      localStringBuffer.append(_lineSeparator);
    }
    return localStringBuffer;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.total != null) {
      this.total.setLocale(paramLocale);
    }
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PAYMENTREPORT"))
      {
        PaymentReport localPaymentReport = (PaymentReport)PaymentReports.this.create();
        localPaymentReport.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PaymentReports
 * JD-Core Version:    0.7.0.1
 */