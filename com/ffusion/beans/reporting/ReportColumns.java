package com.ffusion.beans.reporting;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ReportColumns
  extends FilteredList
  implements XMLable
{
  public static final String REPORT_COLUMNS = "REPORT_COLUMNS";
  
  public ReportColumns(Locale paramLocale)
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
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_COLUMNS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ReportColumn)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_COLUMNS");
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
      if (paramString.equals("REPORT_COLUMN"))
      {
        ReportColumn localReportColumn = new ReportColumn(ReportColumns.this.locale);
        localReportColumn.continueXMLParsing(getHandler());
        ReportColumns.this.add(localReportColumn);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportColumns
 * JD-Core Version:    0.7.0.1
 */