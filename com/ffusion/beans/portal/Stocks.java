package com.ffusion.beans.portal;

import com.ffusion.beans.ToXml;
import com.ffusion.util.LocaleableList;
import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.text.NumberFormat;
import java.util.Iterator;

public class Stocks
  extends LocaleableList
  implements ToXml
{
  public static final String STOCKS = "STOCKS";
  private String jdField_byte = "";
  private double jdField_long = 0.0D;
  private String jdField_char = "";
  private double jdField_else = 0.0D;
  private int jdField_goto = 0;
  private boolean jdField_case = false;
  
  public String getPosition()
  {
    calculateTotals();
    return this.jdField_byte;
  }
  
  public double getPositionDouble()
  {
    calculateTotals();
    return this.jdField_long;
  }
  
  public void setPosition(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public String getCurrentValue()
  {
    calculateTotals();
    return this.jdField_char;
  }
  
  public double getCurrentValueDouble()
  {
    calculateTotals();
    return this.jdField_else;
  }
  
  public void setCurrentValue(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public void calculateTotals()
  {
    double d1 = 0.0D;
    double d2 = 0.0D;
    NumberFormat localNumberFormat = NumberFormat.getCurrencyInstance(this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Stock localStock = (Stock)localIterator.next();
      d1 += localStock.getPositionDouble();
      d2 += localStock.getCurrentValueDouble();
    }
    this.jdField_byte = localNumberFormat.format(d1);
    this.jdField_long = d1;
    this.jdField_char = localNumberFormat.format(d2);
    this.jdField_else = d2;
  }
  
  public void setCurrentStock(String paramString)
  {
    try
    {
      this.jdField_goto = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setSymbol(String paramString)
  {
    Stock localStock = (Stock)get(this.jdField_goto);
    localStock.setSymbol(paramString);
  }
  
  public void setShares(String paramString)
  {
    Stock localStock = (Stock)get(this.jdField_goto);
    localStock.setShares(paramString);
  }
  
  public void setPurchasePrice(String paramString)
  {
    Stock localStock = (Stock)get(this.jdField_goto);
    localStock.setPurchasePrice(paramString);
  }
  
  public void setInStocks(boolean paramBoolean)
  {
    this.jdField_case = paramBoolean;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STOCKS");
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((Stock)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "STOCKS");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public Stock getStock(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Stock localStock = (Stock)get(j);
      if (localStock.getSymbol().equalsIgnoreCase(paramString)) {
        return localStock;
      }
    }
    return null;
  }
  
  public Stock getByCounterID(int paramInt)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Stock localStock = (Stock)get(j);
      if (localStock.getCounter() == paramInt) {
        return localStock;
      }
    }
    return null;
  }
  
  public Stock getBySymbol(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Stock localStock = (Stock)get(j);
      if (localStock.getSymbol().equals(paramString)) {
        return localStock;
      }
    }
    return null;
  }
  
  public Stock getEmptyStock(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Stock localStock = (Stock)get(j);
      if ((localStock.getSymbol().equals(paramString)) && (localStock.getLastSalePrice().equals(""))) {
        return localStock;
      }
    }
    return null;
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
  
  public String getSize()
  {
    return String.valueOf(size());
  }
  
  private class a
    extends XMLHandler
  {
    private boolean jdField_int = false;
    private String jdField_new = "";
    private String jdField_try = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("STOCKS"))
      {
        this.jdField_int = true;
      }
      else if (((Stocks.this.jdField_case) || (this.jdField_int)) && (paramString.equalsIgnoreCase("STOCK")))
      {
        Stock localStock = new Stock();
        localStock.setLocale(Stocks.this.locale);
        if (this.jdField_new.equals("NEWS")) {
          localStock.setNewsFlag(true);
        }
        localStock.continueXMLParsing(getHandler());
        Stocks.this.add(localStock);
      }
      this.jdField_new = "";
      this.jdField_try = "";
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("STOCKS"))
      {
        this.jdField_int = false;
        Stocks.this.jdField_case = false;
      }
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.jdField_new = paramString1;
      this.jdField_try = paramString2;
    }
    
    a(Stocks.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.Stocks
 * JD-Core Version:    0.7.0.1
 */