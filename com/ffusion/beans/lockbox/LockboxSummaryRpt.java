package com.ffusion.beans.lockbox;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class LockboxSummaryRpt
  extends ExtendABean
  implements LockboxRptConsts, IReportResult, ExportFormats, Serializable
{
  public static final String LOCKBOXSUMMARYRPT = "LOCKBOXSUMMARYRPT";
  public static final String LOCKBOXSUMMARY = "LOCKBOXSUMMARY";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private LockboxSummaries Sf = null;
  
  public LockboxSummaryRpt(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxSummaryRpt))) {
      return false;
    }
    LockboxSummaryRpt localLockboxSummaryRpt = (LockboxSummaryRpt)paramObject;
    LockboxSummaries localLockboxSummaries = localLockboxSummaryRpt.getLockboxSummaries();
    if (this.Sf == null) {
      return localLockboxSummaries == null;
    }
    return this.Sf.equals(localLockboxSummaries);
  }
  
  public void setLockboxSummaries(LockboxSummaries paramLockboxSummaries)
  {
    this.Sf = paramLockboxSummaries;
  }
  
  public LockboxSummaries getLockboxSummaries()
  {
    return this.Sf;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(",");
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport("\t");
    }
    return localStringBuffer;
  }
  
  public void set(LockboxSummaryRpt paramLockboxSummaryRpt)
  {
    super.set(paramLockboxSummaryRpt);
    setLockboxSummaries(paramLockboxSummaryRpt.getLockboxSummaries());
    setLocale(paramLockboxSummaryRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOXSUMMARYRPT");
    if (this.Sf != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOXSUMMARY");
      localStringBuffer.append(this.Sf.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "LOCKBOXSUMMARY");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOXSUMMARYRPT");
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
  
  protected StringBuffer getDelimitedReport(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.Sf != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(194, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(190, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(192, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(195, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(197, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(199, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(200, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(201, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this.Sf.iterator();
      LockboxSummary localLockboxSummary = null;
      LockboxAccount localLockboxAccount = null;
      while (localIterator.hasNext())
      {
        localLockboxSummary = (LockboxSummary)localIterator.next();
        localLockboxAccount = localLockboxSummary.getLockboxAccount();
        jdMethod_for(localStringBuffer, localLockboxSummary.getSummaryDate(), paramString);
        jdMethod_int(localStringBuffer, localLockboxAccount.getAccountID(), paramString);
        jdMethod_int(localStringBuffer, localLockboxAccount.getNickname(), paramString);
        jdMethod_for(localStringBuffer, localLockboxSummary.getTotalLockboxCredits(), paramString);
        jdMethod_for(localStringBuffer, localLockboxSummary.getTotalNumLockboxCredits(), paramString);
        jdMethod_for(localStringBuffer, localLockboxSummary.getImmediateFloat(), paramString);
        jdMethod_for(localStringBuffer, localLockboxSummary.getOneDayFloat(), paramString);
        jdMethod_for(localStringBuffer, localLockboxSummary.getTwoDayFloat(), paramString);
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, Currency paramCurrency, String paramString)
  {
    if (paramCurrency != null)
    {
      paramCurrency.setFormat("DECIMAL");
      paramStringBuffer.append(paramCurrency.toString());
    }
    paramStringBuffer.append(paramString);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      paramStringBuffer.append(paramString1);
    }
    paramStringBuffer.append(paramString2);
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, int paramInt, String paramString)
  {
    paramStringBuffer.append(paramInt);
    paramStringBuffer.append(paramString);
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, DateTime paramDateTime, String paramString)
  {
    if (paramDateTime != null) {
      paramStringBuffer.append(paramDateTime.toString());
    }
    paramStringBuffer.append(paramString);
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("LOCKBOXSUMMARY"))
      {
        if (LockboxSummaryRpt.this.Sf == null) {
          LockboxSummaryRpt.this.Sf = new LockboxSummaries(LockboxSummaryRpt.this.locale);
        }
        LockboxSummaryRpt.this.Sf.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxSummaryRpt
 * JD-Core Version:    0.7.0.1
 */