package com.ffusion.beans.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class ACHReport
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  protected ACHBatches batches = null;
  protected Currency total;
  
  public ACHReport(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.batches != null) {
      this.batches.setLocale(paramLocale);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof ACHReport))) {
      return false;
    }
    ACHReport localACHReport = (ACHReport)paramObject;
    ACHBatches localACHBatches = localACHReport.getACHBatches();
    if (this.batches == null) {
      return localACHBatches == null;
    }
    return this.batches.equals(localACHBatches);
  }
  
  public void setACHBatches(ACHBatches paramACHBatches)
  {
    this.batches = paramACHBatches;
  }
  
  public ACHBatches getACHBatches()
  {
    return this.batches;
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
  
  public void set(ACHReport paramACHReport)
  {
    super.set(paramACHReport);
    setACHBatches(paramACHReport.getACHBatches());
    setLocale(paramACHReport.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACHREPORT");
    if (this.batches != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ACHBatch");
      localStringBuffer.append(this.batches.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ACHBatch");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACHREPORT");
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
    if ((this.batches != null) && (this.batches.size() > 0))
    {
      int i = 0;
      if (((ACHBatch)this.batches.get(0)).taxForm != null) {
        i = 1;
      }
      Iterator localIterator;
      Object localObject;
      if (i == 0)
      {
        localStringBuffer.append(ReportConsts.getColumnName(550, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(551, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(552, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(553, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(554, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(555, this.locale));
        localStringBuffer.append(_lineSeparator);
        localIterator = this.batches.iterator();
        localObject = null;
        ACHBatch localACHBatch = null;
        ACHEntry localACHEntry = null;
        while (localIterator.hasNext())
        {
          localACHBatch = (ACHBatch)localIterator.next();
          localObject = localACHBatch.entries.iterator();
          while (((Iterator)localObject).hasNext())
          {
            localACHEntry = (ACHEntry)((Iterator)localObject).next();
            appendString(localStringBuffer, localACHBatch.getName());
            localStringBuffer.append(paramChar);
            appendString(localStringBuffer, localACHBatch.getStandardEntryClassCode());
            localStringBuffer.append(paramChar);
            appendString(localStringBuffer, localACHBatch.getDate());
            localStringBuffer.append(paramChar);
            appendString(localStringBuffer, localACHEntry.getAchPayee().getName());
            localStringBuffer.append(paramChar);
            appendString(localStringBuffer, localACHEntry.getAmount());
            localStringBuffer.append(paramChar);
            appendString(localStringBuffer, localACHEntry.getCheckSerialNumber());
            localStringBuffer.append(_lineSeparator);
          }
        }
      }
      if (i == 1)
      {
        localStringBuffer.append(ReportConsts.getColumnName(650, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(651, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(652, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(653, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(654, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(655, this.locale));
        localStringBuffer.append(_lineSeparator);
        localIterator = this.batches.iterator();
        localObject = null;
        while (localIterator.hasNext())
        {
          localObject = (ACHBatch)localIterator.next();
          appendString(localStringBuffer, ((ACHBatch)localObject).getDate());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, ((ACHBatch)localObject).getTaxForm().getType());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, ((ACHBatch)localObject).getTaxForm().getBankAccountNumber());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, ((ACHBatch)localObject).getTaxForm().getTaxFormDescription());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, ((ACHBatch)localObject).getReferenceNumber());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, ((ACHBatch)localObject).getAmount());
          localStringBuffer.append(_lineSeparator);
        }
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
      if (paramString.equals("ACHBatches"))
      {
        if (ACHReport.this.batches == null) {
          ACHReport.this.batches = new ACHBatches(ACHReport.this.locale);
        }
        ACHReport.this.batches.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHReport
 * JD-Core Version:    0.7.0.1
 */