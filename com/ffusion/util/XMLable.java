package com.ffusion.util;

public abstract interface XMLable
{
  public abstract void setXML(String paramString);
  
  public abstract void fromXML(String paramString);
  
  public abstract String getXML();
  
  public abstract String toXML();
  
  public abstract void continueXMLParsing(XMLHandler paramXMLHandler);
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.XMLable
 * JD-Core Version:    0.7.0.1
 */