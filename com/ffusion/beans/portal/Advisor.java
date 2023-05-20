package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;

public class Advisor
  implements Serializable
{
  public static final String ADVISOR = "ADVISOR";
  public static final String CATEGORY = "CATEGORY";
  public static final String NAME = "NAME";
  public static final String URL = "URL";
  private String jdField_do = "";
  private String jdField_if = "";
  private String a = "";
  
  public void setCategory(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getCategory()
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
    XMLHandler.openTag(localStringBuffer, "ADVISOR");
    XMLHandler.appendAttribute(localStringBuffer, "CATEGORY", this.jdField_do);
    XMLHandler.closeTag(localStringBuffer, false);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.jdField_if);
    XMLHandler.appendTag(localStringBuffer, "URL", this.a);
    XMLHandler.appendEndTag(localStringBuffer, "ADVISOR");
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
        Advisor.this.jdField_if = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("URL")) {
        Advisor.this.a = this.jdField_int;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(Advisor.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.Advisor
 * JD-Core Version:    0.7.0.1
 */