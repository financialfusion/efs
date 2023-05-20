package com.ffusion.beans.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

public class ReportDataItem
  extends ExtendABean
{
  static final String aWx = "REPORT_DATA_ITEM";
  public static final String DATA = "DATA";
  private Object aWw;
  private HashMap aWv;
  
  public ReportDataItem(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public Object getData()
  {
    return this.aWw;
  }
  
  public void setData(Object paramObject)
  {
    this.aWw = paramObject;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("data=").append(this.aWw);
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportDataItem localReportDataItem = (ReportDataItem)paramObject;
    int i = 1;
    if (paramString.equals("DATA"))
    {
      if (this.aWw.equals(localReportDataItem.getData())) {
        i = 0;
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
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_DATA_ITEM");
    XMLHandler.appendBeginTag(localStringBuffer, "DATA");
    if ((this.aWw instanceof DateTime)) {
      localStringBuffer.append(((DateTime)this.aWw).getXML());
    } else if ((this.aWw instanceof Currency)) {
      localStringBuffer.append(((Currency)this.aWw).getXML());
    } else {
      localStringBuffer.append(this.aWw.toString());
    }
    XMLHandler.appendEndTag(localStringBuffer, "DATA");
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_DATA_ITEM");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString1, String paramString2)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramString2), paramString1);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void setXML(String paramString)
  {
    setXML(paramString, "java.lang.String");
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    jdMethod_for(paramXMLHandler, "java.lang.String");
  }
  
  void jdMethod_for(XMLHandler paramXMLHandler, String paramString)
  {
    paramXMLHandler.continueWith(new a(paramString));
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    return set(paramString1, paramString2, "java.lang.String");
  }
  
  public boolean set(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = true;
    if (paramString1.equals("DATA"))
    {
      if (paramString3.equals("java.lang.Boolean")) {
        this.aWw = Boolean.valueOf(paramString2);
      } else if (paramString3.equals("java.lang.Character")) {
        this.aWw = new Character(paramString2.charAt(0));
      } else if (paramString3.equals("java.lang.Double")) {
        this.aWw = Double.valueOf(paramString2);
      } else if (paramString3.equals("java.lang.Float")) {
        this.aWw = Float.valueOf(paramString2);
      } else if (paramString3.equals("java.lang.Integer")) {
        this.aWw = Integer.valueOf(paramString2);
      } else if (paramString3.equals("java.lang.Long")) {
        this.aWw = Long.valueOf(paramString2);
      } else if (paramString3.equals("java.lang.Short")) {
        this.aWw = Short.valueOf(paramString2);
      } else if (paramString3.equals("java.math.BigDecimal")) {
        this.aWw = new BigDecimal(Long.valueOf(paramString2).longValue());
      } else {
        this.aWw = paramString2.toString();
      }
    }
    else {
      bool = super.set(paramString1, paramString2);
    }
    return true;
  }
  
  class a
    extends XMLHandler
  {
    private String jdField_int;
    
    public a(String paramString)
    {
      this.jdField_int = paramString;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ReportDataItem.this.set(getElement(), str, this.jdField_int);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("DATA"))
      {
        if (this.jdField_int.equals("com.ffusion.beans.Currency"))
        {
          if (ReportDataItem.this.aWw == null) {
            ReportDataItem.this.aWw = new Currency();
          }
          ((Currency)ReportDataItem.this.aWw).continueXMLParsing(getHandler());
        }
        else if (this.jdField_int.equals("com.ffusion.util.beans.DateTime"))
        {
          if (ReportDataItem.this.aWw == null) {
            ReportDataItem.this.aWw = new DateTime(ReportDataItem.this.locale);
          }
          ((DateTime)ReportDataItem.this.aWw).continueXMLParsing(getHandler());
        }
        else
        {
          super.startElement(paramString);
        }
      }
      else {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportDataItem
 * JD-Core Version:    0.7.0.1
 */