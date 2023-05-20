package com.ffusion.beans.reporting;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;

public class ReportIdentifications
  extends FilteredList
{
  static final String jdField_byte = "REPORTIDENTIFICATIONS";
  
  public ReportIdentification create(int paramInt, String paramString1, String paramString2)
  {
    ReportIdentification localReportIdentification = new ReportIdentification(paramInt, paramString1, paramString2);
    super.add(localReportIdentification);
    return localReportIdentification;
  }
  
  public ReportIdentification getByID(int paramInt)
  {
    for (int i = 0; i < size(); i++)
    {
      ReportIdentification localReportIdentification = (ReportIdentification)get(i);
      if (localReportIdentification.getReportID() == paramInt) {
        return localReportIdentification;
      }
    }
    return null;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORTIDENTIFICATIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ReportIdentification)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORTIDENTIFICATIONS");
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
      if (paramString.equals("REPORTIDENTIFICATION"))
      {
        ReportIdentification localReportIdentification = new ReportIdentification();
        localReportIdentification.continueXMLParsing(getHandler());
        ReportIdentifications.this.add(localReportIdentification);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportIdentifications
 * JD-Core Version:    0.7.0.1
 */