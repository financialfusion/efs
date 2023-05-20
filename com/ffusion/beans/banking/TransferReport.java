package com.ffusion.beans.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class TransferReport
  extends ExtendABean
  implements BankingReportConsts, IReportResult, ExportFormats, Serializable
{
  public static final String TRANSFERRPT = "TRANSFERREPORT";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private Transfers SX = null;
  protected Currency total;
  
  public TransferReport(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.SX != null) {
      this.SX.setLocale(paramLocale);
    }
    if (this.total != null) {
      this.total.setLocale(paramLocale);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof TransferReport))) {
      return false;
    }
    TransferReport localTransferReport = (TransferReport)paramObject;
    Transfers localTransfers = localTransferReport.getTransfers();
    if (this.SX == null) {
      return localTransfers == null;
    }
    return this.SX.equals(localTransfers);
  }
  
  public void setTransfers(Transfers paramTransfers)
  {
    this.SX = paramTransfers;
  }
  
  public Transfers getTransfers()
  {
    return this.SX;
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
  
  public Currency getTotalValue()
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
  
  public void set(TransferReport paramTransferReport)
  {
    super.set(paramTransferReport);
    setTransfers(paramTransferReport.getTransfers());
    setLocale(paramTransferReport.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "TRANSFERREPORT");
    if (this.SX != null) {
      localStringBuffer.append(this.SX.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "TRANSFERREPORT");
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
    if (this.SX != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(170, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(171, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(172, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(173, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(174, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this.SX.iterator();
      Transfer localTransfer = null;
      while (localIterator.hasNext())
      {
        localTransfer = (Transfer)localIterator.next();
        appendString(localStringBuffer, localTransfer.getDateValue().toString());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localTransfer.getFromAccount().getNumber());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localTransfer.getToAccount().getNumber());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localTransfer.getAmountValue().toString());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localTransfer.getStatusName());
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
      if (paramString.equals("TRANSFERS"))
      {
        if (TransferReport.this.SX == null) {
          TransferReport.this.SX = new Transfers(TransferReport.this.locale);
        }
        TransferReport.this.SX.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.TransferReport
 * JD-Core Version:    0.7.0.1
 */