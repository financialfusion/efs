package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class ReportDataDimensions
  extends ExtendABean
{
  public static final String REPORT_DATA_DIMENSIONS = "REPORT_DATA_DIMENSIONS";
  public static final String NUMROWS = "NUMROWS";
  public static final String NUMCOLUMNS = "NUMCOLUMNS";
  private int aV5 = 0;
  private int aV4 = 0;
  
  public ReportDataDimensions(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public int getNumRows()
  {
    return this.aV5;
  }
  
  public void setNumRows(int paramInt)
  {
    this.aV5 = paramInt;
  }
  
  public int getNumColumns()
  {
    return this.aV4;
  }
  
  public void setNumColumns(int paramInt)
  {
    this.aV4 = paramInt;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("numRows=").append(this.aV5);
    localStringBuffer.append("numColumns=").append(this.aV4);
    return localStringBuffer.toString();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    int i = compare(paramObject, "NUMROWS");
    if (i == 0) {
      i = compare(paramObject, "NUMCOLUMNS");
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportDataDimensions localReportDataDimensions = (ReportDataDimensions)paramObject;
    int i = 1;
    if (paramString.equals("NUMROWS")) {
      i = this.aV5 - localReportDataDimensions.getNumRows();
    } else if (paramString.equals("NUMCOLUMNS")) {
      i = this.aV4 - localReportDataDimensions.getNumColumns();
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_DATA_DIMENSIONS");
    XMLHandler.appendTag(localStringBuffer, "NUMROWS", this.aV5);
    XMLHandler.appendTag(localStringBuffer, "NUMCOLUMNS", this.aV4);
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_DATA_DIMENSIONS");
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
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("NUMROWS")) {
      this.aV5 = Integer.parseInt(paramString2);
    } else if (paramString1.equals("NUMCOLUMNS")) {
      this.aV4 = Integer.parseInt(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ReportDataDimensions.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportDataDimensions
 * JD-Core Version:    0.7.0.1
 */