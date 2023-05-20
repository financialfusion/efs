package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;

public class StockSymbol
  implements Serializable
{
  public static final String STOCK_SYMBOL = "STOCK_SYMBOL";
  public static final String SYMBOL = "SYMBOL";
  public static final String NAME = "NAME";
  private String jdField_if = "";
  private String a = "";
  
  public String getSymbol()
  {
    return this.jdField_if;
  }
  
  public void setSymbol(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getCompanyName()
  {
    return this.a;
  }
  
  public void setCompanyName(String paramString)
  {
    this.a = paramString;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STOCK_SYMBOL");
    XMLHandler.appendTag(localStringBuffer, "SYMBOL", this.jdField_if);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.a);
    XMLHandler.appendEndTag(localStringBuffer, "STOCK_SYMBOL");
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
      if (paramString.equalsIgnoreCase("SYMBOL")) {
        StockSymbol.this.jdField_if = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("NAME")) {
        StockSymbol.this.a = this.jdField_int;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(StockSymbol.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.StockSymbol
 * JD-Core Version:    0.7.0.1
 */