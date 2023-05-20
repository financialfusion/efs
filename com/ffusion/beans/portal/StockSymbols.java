package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class StockSymbols
  extends ArrayList
{
  public static final String STOCK_SYMBOLS = "STOCK_SYMBOLS";
  public static final String NAME = "NAME";
  private String a = "";
  private boolean jdField_if = false;
  
  public String getSearchName()
  {
    return this.a;
  }
  
  public void setSearchName(String paramString)
  {
    this.a = paramString;
  }
  
  public void setInStockSymbols(boolean paramBoolean)
  {
    this.jdField_if = paramBoolean;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (!this.a.equals(""))
    {
      XMLHandler.openTag(localStringBuffer, "STOCK_SYMBOLS");
      XMLHandler.appendAttribute(localStringBuffer, "NAME", this.a);
      XMLHandler.closeTag(localStringBuffer, false);
    }
    else
    {
      XMLHandler.appendBeginTag(localStringBuffer, "STOCK_SYMBOLS");
    }
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((StockSymbol)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "STOCK_SYMBOLS");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public void startXMLParsing(Reader paramReader)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramReader);
    }
    catch (Exception localException) {}
  }
  
  public void startXMLParsing(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Exception localException) {}
  }
  
  private class a
    extends XMLHandler
  {
    private boolean jdField_try = false;
    private String jdField_int = "";
    private String jdField_new = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("STOCK_SYMBOLS"))
      {
        if (this.jdField_int.equalsIgnoreCase("NAME")) {
          StockSymbols.this.a = this.jdField_new;
        }
        this.jdField_try = true;
      }
      else if (((StockSymbols.this.jdField_if) || (this.jdField_try)) && (paramString.equalsIgnoreCase("STOCK_SYMBOL")))
      {
        StockSymbol localStockSymbol = new StockSymbol();
        localStockSymbol.continueXMLParsing(getHandler());
        StockSymbols.this.add(localStockSymbol);
      }
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("STOCK_SYMBOLS")) {
        this.jdField_try = false;
      }
      this.jdField_int = "";
      this.jdField_new = "";
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.jdField_int = paramString1;
      this.jdField_new = paramString2;
    }
    
    a(StockSymbols.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.StockSymbols
 * JD-Core Version:    0.7.0.1
 */