package com.ffusion.util;

import java.util.Date;

public class XMLableDate
  extends Date
  implements XMLable
{
  public static final String DATE = "DATE";
  public static final String TIME_MILLIS = "TIME_MILLIS";
  
  public XMLableDate() {}
  
  public XMLableDate(long paramLong)
  {
    super(paramLong);
  }
  
  public XMLableDate(String paramString)
    throws NumberFormatException
  {
    super(Long.parseLong(paramString));
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    return toXML();
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DATE");
    XMLHandler.appendTag(localStringBuffer, "TIME_MILLIS", Long.toString(getTime()));
    XMLHandler.appendEndTag(localStringBuffer, "DATE");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public void fromString(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException();
    }
    if (paramString.length() <= 0)
    {
      setTime(System.currentTimeMillis());
      return;
    }
    long l = Long.parseLong(paramString);
    setTime(l);
  }
  
  public class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if ((str.length() > 0) && (getElement().equalsIgnoreCase("TIME_MILLIS"))) {
        XMLableDate.this.fromString(str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.XMLableDate
 * JD-Core Version:    0.7.0.1
 */