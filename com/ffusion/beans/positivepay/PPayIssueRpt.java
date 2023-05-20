package com.ffusion.beans.positivepay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class PPayIssueRpt
  extends ExtendABean
  implements PPayRptConsts, IReportResult, ExportFormats, Serializable
{
  public static final String PPAYISSUERPT = "PPAYISSUERPT";
  public static final String ISSUES = "ISSUES";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private PPayIssues SV = null;
  
  public PPayIssueRpt(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.SV != null) {
      this.SV.setLocale(paramLocale);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof PPayIssueRpt))) {
      return false;
    }
    PPayIssueRpt localPPayIssueRpt = (PPayIssueRpt)paramObject;
    PPayIssues localPPayIssues = localPPayIssueRpt.getIssues();
    if (this.SV == null) {
      return localPPayIssues == null;
    }
    return this.SV.equals(localPPayIssues);
  }
  
  public void setIssues(PPayIssues paramPPayIssues)
  {
    this.SV = paramPPayIssues;
  }
  
  public PPayIssues getIssues()
  {
    return this.SV;
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
  
  public void set(PPayIssueRpt paramPPayIssueRpt)
  {
    super.set(paramPPayIssueRpt);
    setIssues(paramPPayIssueRpt.getIssues());
    setLocale(paramPPayIssueRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYISSUERPT");
    if (this.SV != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ISSUES");
      localStringBuffer.append(this.SV.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ISSUES");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PPAYISSUERPT");
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
    if (this.SV != null)
    {
      Iterator localIterator = this.SV.iterator();
      localStringBuffer.append(ReportConsts.getColumnName(28, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(20, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(21, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(22, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(23, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(24, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(25, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(27, this.locale));
      localStringBuffer.append(_lineSeparator);
      PPayIssue localPPayIssue = null;
      PPayCheckRecord localPPayCheckRecord = null;
      while (localIterator.hasNext())
      {
        localPPayIssue = (PPayIssue)localIterator.next();
        localPPayCheckRecord = localPPayIssue.getCheckRecord();
        appendString(localStringBuffer, localPPayIssue.getIssueDate().toString());
        localStringBuffer.append(paramChar);
        if (localPPayCheckRecord == null)
        {
          for (int i = 1; i <= 6; i++) {
            localStringBuffer.append(paramChar);
          }
        }
        else
        {
          appendString(localStringBuffer, localPPayCheckRecord.getAccountID());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, localPPayCheckRecord.getBankID());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, localPPayCheckRecord.getCheckNumber());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, localPPayCheckRecord.getCheckDate().toString());
          localStringBuffer.append(paramChar);
          localStringBuffer.append(localPPayCheckRecord.getAmount().getCurrencyStringNoSymbol());
          localStringBuffer.append(paramChar);
          appendString(localStringBuffer, localPPayCheckRecord.getVoidCheck() ? "Void" : "");
          localStringBuffer.append(paramChar);
        }
        appendString(localStringBuffer, localPPayIssue.getRejectReason());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    public a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("ISSUES"))
      {
        if (PPayIssueRpt.this.SV == null) {
          PPayIssueRpt.this.SV = new PPayIssues(PPayIssueRpt.this.locale);
        }
        PPayIssueRpt.this.SV.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayIssueRpt
 * JD-Core Version:    0.7.0.1
 */