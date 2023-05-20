package com.ffusion.beans.billpay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class PaymentReport
  extends ExtendABean
  implements PaymentReportConsts, IReportResult, ExportFormats, Sortable, Serializable
{
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  protected Payments _payments = null;
  protected Currency total;
  
  public PaymentReport(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this._payments != null) {
      this._payments.setLocale(paramLocale);
    }
    if (this.total != null) {
      this.total.setLocale(paramLocale);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof PaymentReport))) {
      return false;
    }
    PaymentReport localPaymentReport = (PaymentReport)paramObject;
    Payments localPayments = localPaymentReport.getPayments();
    if (this._payments == null) {
      return localPayments == null;
    }
    return this._payments.equals(localPayments);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PaymentReport localPaymentReport = (PaymentReport)paramObject;
    int i = 1;
    if ((paramString.equals("AMOUNT")) && (this.total != null) && (localPaymentReport.getAmountValue() != null)) {
      i = this.total.compareTo(localPaymentReport.getAmountValue());
    } else if ((paramString.equals("PAYEENAME")) && (localPaymentReport.getPayments().size() > 0) && (((Payment)localPaymentReport.getPayments().get(0)).getPayeeName() != null) && (getPayments().size() > 0) && (((Payment)getPayments().get(0)).getPayeeName() != null)) {
      i = ((Payment)getPayments().get(0)).getPayeeName().compareTo(((Payment)localPaymentReport.getPayments().get(0)).getPayeeName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void setPayments(Payments paramPayments)
  {
    this._payments = paramPayments;
  }
  
  public Payments getPayments()
  {
    return this._payments;
  }
  
  public void setTotal(Currency paramCurrency)
  {
    this.total = paramCurrency;
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
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    }
    return localStringBuffer;
  }
  
  public void set(PaymentReport paramPaymentReport)
  {
    super.set(paramPaymentReport);
    setPayments(paramPaymentReport.getPayments());
    setLocale(paramPaymentReport.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PAYMENTREPORT");
    if (this._payments != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "Payments");
      localStringBuffer.append(this._payments.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "Payments");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PAYMENTREPORT");
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
  
  public String getDateFormat()
  {
    return super.getDateFormat();
  }
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this._payments != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(180, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(181, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(182, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(183, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(184, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this._payments.iterator();
      Payment localPayment = null;
      while (localIterator.hasNext())
      {
        localPayment = (Payment)localIterator.next();
        appendString(localStringBuffer, localPayment.getPayDateValue().toString());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localPayment.getAccount().getNumber());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localPayment.getPayee().getName());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localPayment.getAmountValue().toString());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localPayment.getStatusName());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("Payments"))
      {
        if (PaymentReport.this._payments == null) {
          PaymentReport.this._payments = new Payments(PaymentReport.this.locale);
        }
        PaymentReport.this._payments.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PaymentReport
 * JD-Core Version:    0.7.0.1
 */