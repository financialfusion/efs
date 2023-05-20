package com.ffusion.beans.reporting;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ReportResults
  extends FilteredList
  implements XMLable
{
  public static final String REPORT_RESULTS = "REPORT_RESULTS";
  
  public ReportResults(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_RESULTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ReportResult)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_RESULTS");
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
      if (paramString.equals("REPORT_RESULT"))
      {
        ReportResult localReportResult = new ReportResult(ReportResults.this.locale);
        localReportResult.continueXMLParsing(getHandler());
        ReportResults.this.add(localReportResult);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportResults
 * JD-Core Version:    0.7.0.1
 */