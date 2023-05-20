package com.ffusion.beans.reporting;

import com.ffusion.reporting.IReportResult;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;

public class Reports
  extends FilteredList
{
  public static final String REPORTS = "REPORTS";
  
  public Report create(ReportIdentification paramReportIdentification, ReportCriteria paramReportCriteria, IReportResult paramIReportResult)
  {
    Report localReport = new Report(paramReportIdentification, paramReportCriteria, paramIReportResult);
    super.add(localReport);
    return localReport;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Report)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORTS");
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
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("REPORT"))
      {
        Report localReport = new Report();
        localReport.continueXMLParsing(getHandler());
        Reports.this.add(localReport);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.Reports
 * JD-Core Version:    0.7.0.1
 */