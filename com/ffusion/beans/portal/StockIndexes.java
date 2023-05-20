package com.ffusion.beans.portal;

import com.ffusion.beans.ToXml;
import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class StockIndexes
  extends ArrayList
  implements ToXml
{
  public static final String STOCK_INDEXES = "STOCK_INDEXES";
  private boolean jdField_void = false;
  private String jdField_null = "";
  
  public StockIndex getStockIndex(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      StockIndex localStockIndex = (StockIndex)get(j);
      if (localStockIndex.getSymbol().equalsIgnoreCase(paramString)) {
        return localStockIndex;
      }
    }
    return null;
  }
  
  public void setChecked(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getChecked()
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      StockIndex localStockIndex = (StockIndex)get(j);
      if (localStockIndex.getSymbol().equals(this.jdField_null)) {
        return "true";
      }
    }
    return "false";
  }
  
  public void setCheck(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      StockIndex localStockIndex2 = (StockIndex)get(j);
      if (localStockIndex2.getSymbol().equals(paramString)) {
        return;
      }
    }
    StockIndex localStockIndex1 = new StockIndex();
    localStockIndex1.setSymbol(paramString);
    add(localStockIndex1);
  }
  
  public void setInStockIndexes(boolean paramBoolean)
  {
    this.jdField_void = paramBoolean;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STOCK_INDEXES");
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((StockIndex)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "STOCK_INDEXES");
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
    private boolean jdField_int = false;
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("STOCK_INDEXES"))
      {
        this.jdField_int = true;
      }
      else if (((StockIndexes.this.jdField_void) || (this.jdField_int)) && (paramString.equalsIgnoreCase("STOCK_INDEX")))
      {
        StockIndex localStockIndex = new StockIndex();
        localStockIndex.continueXMLParsing(getHandler());
        StockIndexes.this.add(localStockIndex);
      }
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("STOCK_INDEXES"))
      {
        this.jdField_int = false;
        StockIndexes.this.jdField_void = false;
      }
    }
    
    a(StockIndexes.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.StockIndexes
 * JD-Core Version:    0.7.0.1
 */