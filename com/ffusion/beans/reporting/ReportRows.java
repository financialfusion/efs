package com.ffusion.beans.reporting;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ReportRows
  extends FilteredList
  implements XMLable
{
  public static final String REPORT_ROWS = "REPORT_ROWS";
  
  public ReportRows(Locale paramLocale)
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
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_ROWS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ReportRow)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_ROWS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    jdMethod_for(paramXMLHandler, null);
  }
  
  void jdMethod_for(XMLHandler paramXMLHandler, String[] paramArrayOfString)
  {
    paramXMLHandler.continueWith(new a(paramArrayOfString));
  }
  
  class a
    extends XMLHandler
  {
    private String[] jdField_int;
    
    public a(String[] paramArrayOfString)
    {
      this.jdField_int = paramArrayOfString;
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("REPORT_ROW"))
      {
        ReportRow localReportRow = new ReportRow(ReportRows.this.locale);
        localReportRow.jdMethod_for(getHandler(), this.jdField_int);
        ReportRows.this.add(localReportRow);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportRows
 * JD-Core Version:    0.7.0.1
 */