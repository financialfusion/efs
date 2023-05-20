package com.ffusion.beans.reporting;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ReportDataItems
  extends FilteredList
  implements XMLable
{
  public static final String REPORT_DATA_ITEMS = "REPORT_DATA_ITEMS";
  
  public ReportDataItems(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ReportDataItem add()
  {
    ReportDataItem localReportDataItem = new ReportDataItem(this.locale);
    super.add(localReportDataItem);
    return localReportDataItem;
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
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_DATA_ITEMS");
    for (int i = 0; i < size(); i++)
    {
      Object localObject = get(i);
      if (localObject != null)
      {
        localStringBuffer.append(((ReportDataItem)localObject).getXML());
      }
      else
      {
        XMLHandler.appendBeginTag(localStringBuffer, "REPORT_DATA_ITEM");
        XMLHandler.appendEndTag(localStringBuffer, "REPORT_DATA_ITEM");
      }
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_DATA_ITEMS");
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
    int jdField_new = 0;
    
    public a(String[] paramArrayOfString)
    {
      this.jdField_int = paramArrayOfString;
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("REPORT_DATA_ITEM"))
      {
        ReportDataItem localReportDataItem = new ReportDataItem(ReportDataItems.this.locale);
        if (this.jdField_int == null)
        {
          localReportDataItem.continueXMLParsing(getHandler());
        }
        else
        {
          localReportDataItem.jdMethod_for(getHandler(), this.jdField_int[this.jdField_new]);
          this.jdField_new += 1;
        }
        ReportDataItems.this.add(localReportDataItem);
      }
      else
      {
        super.startElement(paramString);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      super.endElement(paramString);
      if (paramString.equals("REPORT_DATA_ITEMS")) {
        for (int i = 0; i < ReportDataItems.this.size(); i++)
        {
          ReportDataItem localReportDataItem = (ReportDataItem)ReportDataItems.this.get(i);
          if (localReportDataItem.getData() == null) {
            ReportDataItems.this.set(i, null);
          }
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportDataItems
 * JD-Core Version:    0.7.0.1
 */