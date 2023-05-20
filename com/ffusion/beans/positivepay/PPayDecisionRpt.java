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

public class PPayDecisionRpt
  extends ExtendABean
  implements PPayRptConsts, IReportResult, ExportFormats, Serializable
{
  public static final String PPAYDECISIONRPT = "PPAYDECISIONRPT";
  public static final String DECISIONS = "DECISIONS";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private PPayDecisions SW = null;
  
  public PPayDecisionRpt(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.SW != null) {
      this.SW.setLocale(paramLocale);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof PPayDecisionRpt))) {
      return false;
    }
    PPayDecisionRpt localPPayDecisionRpt = (PPayDecisionRpt)paramObject;
    PPayDecisions localPPayDecisions = localPPayDecisionRpt.getDecisions();
    if (this.SW == null) {
      return localPPayDecisions == null;
    }
    return this.SW.equals(localPPayDecisions);
  }
  
  public void setDecisions(PPayDecisions paramPPayDecisions)
  {
    this.SW = paramPPayDecisions;
  }
  
  public PPayDecisions getDecisions()
  {
    return this.SW;
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
  
  public void set(PPayDecisionRpt paramPPayDecisionRpt)
  {
    super.set(paramPPayDecisionRpt);
    setDecisions(paramPPayDecisionRpt.getDecisions());
    setLocale(paramPPayDecisionRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYDECISIONRPT");
    if (this.SW != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "DECISIONS");
      localStringBuffer.append(this.SW.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "DECISIONS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PPAYDECISIONRPT");
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
    if (this.SW != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(221, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(205, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(206, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(207, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(210, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(213, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(216, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(220, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(224, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this.SW.iterator();
      while (localIterator.hasNext())
      {
        PPayDecision localPPayDecision = (PPayDecision)localIterator.next();
        PPayCheckRecord localPPayCheckRecord = localPPayDecision.getCheckRecord();
        appendString(localStringBuffer, localPPayDecision.getIssueDate().toString());
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
        appendString(localStringBuffer, localPPayDecision.getRejectReason());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localPPayDecision.getDecision());
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
      if (paramString.equals("DECISIONS"))
      {
        if (PPayDecisionRpt.this.SW == null) {
          PPayDecisionRpt.this.SW = new PPayDecisions(PPayDecisionRpt.this.locale);
        }
        PPayDecisionRpt.this.SW.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayDecisionRpt
 * JD-Core Version:    0.7.0.1
 */