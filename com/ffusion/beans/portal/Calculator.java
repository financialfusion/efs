package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;

public class Calculator
  implements Serializable
{
  public static final String CALCULATOR = "CALCULATOR";
  public static final String ID = "ID";
  public static final String NAME = "NAME";
  public static final String URL = "URL";
  private String jdField_do = "";
  private String jdField_if = "";
  private String a = "";
  
  public void setID(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getID()
  {
    return this.jdField_do;
  }
  
  public void setName(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getName()
  {
    return this.jdField_if;
  }
  
  public void setURL(String paramString)
  {
    this.a = paramString;
  }
  
  public String getURL()
  {
    return this.a;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.openTag(localStringBuffer, "CALCULATOR");
    XMLHandler.appendAttribute(localStringBuffer, "ID", this.jdField_do);
    XMLHandler.closeTag(localStringBuffer, false);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.jdField_if);
    XMLHandler.appendTag(localStringBuffer, "URL", this.a);
    XMLHandler.appendEndTag(localStringBuffer, "CALCULATOR");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  private class a
    extends XMLHandler
  {
    String jdField_int = "";
    
    private a() {}
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("NAME")) {
        Calculator.this.jdField_if = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("URL")) {
        Calculator.this.a = this.jdField_int;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(Calculator.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.Calculator
 * JD-Core Version:    0.7.0.1
 */