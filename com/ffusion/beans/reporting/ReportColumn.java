package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class ReportColumn
  extends ExtendABean
{
  static final String aVU = "REPORT_COLUMN";
  public static final String LABELS = "LABELS";
  public static final String LABEL = "LABEL";
  public static final String JUSTIFICATION = "JUSTIFICATION";
  public static final String DATATYPE = "DATATYPE";
  public static final String WIDTHASPERCENT = "WIDTHASPERCENT";
  private ArrayList aVZ;
  private String aVX;
  private int aVW;
  private String aVV = "LEFT";
  private HashMap aVY;
  
  public ReportColumn(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public ArrayList getLabels()
  {
    return this.aVZ;
  }
  
  public void setLabels(ArrayList paramArrayList)
  {
    this.aVZ = paramArrayList;
  }
  
  public String getJustification()
  {
    return this.aVV;
  }
  
  public void setJustification(String paramString)
  {
    this.aVV = paramString;
  }
  
  public String getDataType()
  {
    return this.aVX;
  }
  
  public void setDataType(String paramString)
  {
    this.aVX = paramString;
  }
  
  public int getWidthAsPercent()
  {
    return this.aVW;
  }
  
  public void setWidthAsPercent(int paramInt)
  {
    this.aVW = paramInt;
  }
  
  public void setReportColumnProperty(String paramString1, String paramString2)
  {
    if (this.aVY == null) {
      this.aVY = new HashMap();
    }
    this.aVY.put(paramString1, paramString2);
  }
  
  public String getReportColumnProperty(String paramString)
  {
    String str = null;
    if ((this.aVY != null) && (this.aVY.containsKey(paramString))) {
      str = (String)this.aVY.get(paramString);
    }
    return str;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("labels=").append(this.aVZ);
    localStringBuffer.append("dataType=").append(this.aVX);
    localStringBuffer.append("widthAsPercent=").append(this.aVW);
    localStringBuffer.append("justification=").append(this.aVV);
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportColumn localReportColumn = (ReportColumn)paramObject;
    int i = 1;
    if (paramString.equals("DATATYPE")) {
      i = this.aVX.compareToIgnoreCase(localReportColumn.getDataType());
    } else if (paramString.equals("JUSTIFICATION")) {
      i = this.aVV.compareToIgnoreCase(localReportColumn.getJustification());
    } else if (paramString.equals("LABELS"))
    {
      if (this.aVZ.equals(localReportColumn.getLabels())) {
        i = 0;
      }
    }
    else if (paramString.equals("WIDTHASPERCENT")) {
      i = this.aVW - localReportColumn.getWidthAsPercent();
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_COLUMN");
    if (this.aVZ != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "LABELS");
      for (int i = 0; i < this.aVZ.size(); i++) {
        XMLHandler.appendTag(localStringBuffer, "LABEL", (String)this.aVZ.get(i));
      }
      XMLHandler.appendEndTag(localStringBuffer, "LABELS");
    }
    if (this.aVV != null) {
      XMLHandler.appendTag(localStringBuffer, "JUSTIFICATION", this.aVV);
    }
    if (this.aVY != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "PROPERTIES");
      Iterator localIterator = this.aVY.keySet().iterator();
      while (localIterator.hasNext())
      {
        XMLHandler.appendBeginTag(localStringBuffer, "PROPERTY");
        String str1 = (String)localIterator.next();
        String str2 = (String)this.aVY.get(str1);
        XMLHandler.appendTag(localStringBuffer, "NAME", str1);
        XMLHandler.appendTag(localStringBuffer, "VALUE", str2);
        XMLHandler.appendEndTag(localStringBuffer, "PROPERTY");
      }
      XMLHandler.appendEndTag(localStringBuffer, "PROPERTIES");
    }
    XMLHandler.appendTag(localStringBuffer, "DATATYPE", this.aVX);
    XMLHandler.appendTag(localStringBuffer, "WIDTHASPERCENT", this.aVW);
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_COLUMN");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new d(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new d());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("DATATYPE"))
    {
      this.aVX = paramString2;
    }
    else if (paramString1.equals("JUSTIFICATION"))
    {
      this.aVV = paramString2;
    }
    else if (paramString1.equals("WIDTHASPERCENT"))
    {
      this.aVW = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("LABEL"))
    {
      if (this.aVZ == null) {
        this.aVZ = new ArrayList();
      }
      this.aVZ.add(paramString2);
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  class a
    extends XMLHandler
  {
    private String jdField_int = null;
    private String jdField_new = null;
    
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      String str2 = getElement();
      if (str2.equals("NAME")) {
        this.jdField_int = str1;
      } else if (str2.equals("VALUE")) {
        this.jdField_new = str1;
      } else {
        ReportColumn.this.set(str2, str1);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PROPERTY"))
      {
        if (this.jdField_int != null) {
          ReportColumn.this.aVY.put(this.jdField_int, this.jdField_new);
        }
      }
      else {
        super.endElement(paramString);
      }
    }
  }
  
  class c
    extends XMLHandler
  {
    public c() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PROPERTY")) {
        getHandler().continueWith(new ReportColumn.a(ReportColumn.this));
      } else {
        super.startElement(paramString);
      }
    }
  }
  
  class b
    extends XMLHandler
  {
    public b() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ReportColumn.this.set(getElement(), str);
    }
  }
  
  class d
    extends XMLHandler
  {
    public d() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ReportColumn.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("LABELS"))
      {
        getHandler().continueWith(new ReportColumn.b(ReportColumn.this));
      }
      else if (paramString.equals("PROPERTIES"))
      {
        if (ReportColumn.this.aVY == null) {
          ReportColumn.this.aVY = new HashMap();
        }
        getHandler().continueWith(new ReportColumn.c(ReportColumn.this));
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportColumn
 * JD-Core Version:    0.7.0.1
 */