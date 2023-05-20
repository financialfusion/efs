package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import java.util.HashMap;
import java.util.Locale;

public class ReportRow
  extends ExtendABean
{
  static final String aV3 = "REPORT_ROW";
  public static final String FORCED_PAGEBREAK = "FORCED_PAGEBREAK";
  private ReportDataItems aV2;
  private HashMap aV1;
  private boolean aV0 = false;
  
  public ReportRow(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public ReportDataItems getDataItems()
  {
    return this.aV2;
  }
  
  public void setDataItems(ReportDataItems paramReportDataItems)
  {
    this.aV2 = paramReportDataItems;
  }
  
  public void setForcedPageBreak(boolean paramBoolean)
  {
    this.aV0 = paramBoolean;
  }
  
  public boolean getForcedPageBreak()
  {
    return this.aV0;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("dataItems=").append(this.aV2.toString());
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportRow localReportRow = (ReportRow)paramObject;
    int i = 1;
    if (paramString.equals("REPORT_DATA_ITEMS"))
    {
      if (this.aV2.equals(localReportRow.getDataItems())) {
        i = 0;
      }
    }
    else if (paramString.equals("FORCED_PAGEBREAK"))
    {
      if (localReportRow.getForcedPageBreak() == this.aV0) {
        i = 0;
      } else if (localReportRow.getForcedPageBreak() == true) {
        i = -1;
      } else {
        i = 1;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_ROW");
    localStringBuffer.append(this.aV2.getXML());
    if (this.aV0) {
      XMLHandler.appendTag(localStringBuffer, "FORCED_PAGEBREAK", "TRUE");
    } else {
      XMLHandler.appendTag(localStringBuffer, "FORCED_PAGEBREAK", "FALSE");
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_ROW");
    return localStringBuffer.toString();
  }
  
  void jdMethod_for(String paramString, String[] paramArrayOfString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramArrayOfString), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void setXML(String paramString)
  {
    jdMethod_for(paramString, null);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    jdMethod_for(paramXMLHandler, null);
  }
  
  void jdMethod_for(XMLHandler paramXMLHandler, String[] paramArrayOfString)
  {
    paramXMLHandler.continueWith(new a(paramArrayOfString));
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("FORCED_PAGEBREAK"))
    {
      if (paramString2.equalsIgnoreCase("true")) {
        this.aV0 = true;
      }
    }
    else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  class a
    extends XMLHandler
  {
    private String[] jdField_int;
    
    public a(String[] paramArrayOfString)
    {
      this.jdField_int = paramArrayOfString;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ReportRow.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("REPORT_DATA_ITEMS"))
      {
        if (ReportRow.this.aV2 == null) {
          ReportRow.this.aV2 = new ReportDataItems(ReportRow.this.locale);
        }
        ReportRow.this.aV2.jdMethod_for(getHandler(), this.jdField_int);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportRow
 * JD-Core Version:    0.7.0.1
 */