package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class Report
  extends ExtendABean
{
  public static final String REPORT = "REPORT";
  private ReportIdentification aWa = new ReportIdentification();
  private ReportCriteria aWb = new ReportCriteria();
  private IReportResult aWc;
  
  public Report() {}
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aWa != null) {
      this.aWa.setLocale(paramLocale);
    }
    if (this.aWb != null) {
      this.aWb.setLocale(paramLocale);
    }
    if (this.aWc != null) {
      this.aWc.setLocale(paramLocale);
    }
  }
  
  public Report(ReportIdentification paramReportIdentification, ReportCriteria paramReportCriteria, IReportResult paramIReportResult)
  {
    this.aWa = paramReportIdentification;
    this.aWb = paramReportCriteria;
    this.aWc = paramIReportResult;
  }
  
  public void setReportID(ReportIdentification paramReportIdentification)
  {
    this.aWa = paramReportIdentification;
  }
  
  public ReportIdentification getReportID()
  {
    return this.aWa;
  }
  
  public void setReportCriteria(ReportCriteria paramReportCriteria)
  {
    this.aWb = paramReportCriteria;
  }
  
  public ReportCriteria getReportCriteria()
  {
    return this.aWb;
  }
  
  public void setReportResult(IReportResult paramIReportResult)
  {
    this.aWc = paramIReportResult;
  }
  
  public IReportResult getReportResult()
  {
    return this.aWc;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT");
    localStringBuffer.append(this.aWa.getXML());
    localStringBuffer.append(this.aWb.getXML());
    if ((this.aWc instanceof ReportResult)) {
      localStringBuffer.append(((ReportResult)this.aWc).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT");
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
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("REPORTIDENTIFICATION"))
      {
        Report.this.aWa.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("REPORTCRITERIA"))
      {
        Report.this.aWb.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("REPORT_RESULT"))
      {
        Report.this.aWc = new ReportResult(Report.this.locale);
        ((ReportResult)Report.this.aWc).continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.Report
 * JD-Core Version:    0.7.0.1
 */