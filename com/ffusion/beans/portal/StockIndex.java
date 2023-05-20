package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;

public class StockIndex
  implements Serializable
{
  public static final String STOCK_INDEX = "STOCK_INDEX";
  public static final String SYMBOL = "SYMBOL";
  public static final String NAME = "NAME";
  public static final String VALUE = "VALUE";
  public static final String CHANGE_PERCENT = "CHANGEPERCENT";
  public static final String CHANGE_PRICE = "CHANGEPRICE";
  public static final String VOLUME = "VOLUME";
  private String jdField_if = "";
  private String jdField_new = "";
  private String jdField_int = "";
  private String jdField_for = "";
  private String a = "";
  private String jdField_do = "";
  
  public String getSymbol()
  {
    return this.jdField_if;
  }
  
  public void setSymbol(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getIndexName()
  {
    return this.jdField_new;
  }
  
  public void setIndexName(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getValue()
  {
    return this.jdField_int;
  }
  
  public void setValue(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getChangePercent()
  {
    return this.jdField_for;
  }
  
  public void setChangePercent(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getChangePrice()
  {
    return this.a;
  }
  
  public void setChangePrice(String paramString)
  {
    this.a = paramString;
  }
  
  public String getVolume()
  {
    return this.jdField_do;
  }
  
  public void setVolume(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STOCK_INDEX");
    XMLHandler.appendTag(localStringBuffer, "SYMBOL", this.jdField_if);
    XMLHandler.appendTag(localStringBuffer, "VOLUME", this.jdField_do);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.jdField_new);
    XMLHandler.appendTag(localStringBuffer, "CHANGEPERCENT", this.jdField_for);
    XMLHandler.appendTag(localStringBuffer, "CHANGEPRICE", this.a);
    XMLHandler.appendTag(localStringBuffer, "VALUE", this.jdField_int);
    XMLHandler.appendEndTag(localStringBuffer, "STOCK_INDEX");
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
        StockIndex.this.jdField_if = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("NAME")) {
        StockIndex.this.jdField_new = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("VALUE")) {
        StockIndex.this.jdField_int = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("CHANGEPERCENT")) {
        StockIndex.this.jdField_for = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("CHANGEPRICE")) {
        StockIndex.this.a = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("VOLUME")) {
        StockIndex.this.jdField_do = this.jdField_int;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(StockIndex.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.StockIndex
 * JD-Core Version:    0.7.0.1
 */