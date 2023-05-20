package com.ffusion.beans.bcreport;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;

public class ReportLogRecords
  extends FilteredList
  implements XMLable
{
  public static final String AUDIT_LOG_RECORDS = "AUDIT_LOG_RECORDS";
  
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
    XMLHandler.appendBeginTag(localStringBuffer, "AUDIT_LOG_RECORDS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ReportLogRecord)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "AUDIT_LOG_RECORDS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, boolean paramBoolean)
  {
    continueXMLParsing(paramXMLHandler);
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("AUDIT_LOG_RECORD"))
      {
        ReportLogRecord localReportLogRecord = new ReportLogRecord("", "", "", "", "", 0, 0, null, null, null, null, null, null, null, null, 0);
        localReportLogRecord.continueXMLParsing(getHandler());
        ReportLogRecords.this.add(localReportLogRecord);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.ReportLogRecords
 * JD-Core Version:    0.7.0.1
 */