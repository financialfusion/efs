package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ProductAccesses
  extends ArrayList
  implements Serializable
{
  public static final String PRODUCT_ACCESS_GROUP = "PRODUCT_ACCESS_GROUP";
  
  public ProductAccess add()
  {
    ProductAccess localProductAccess = new ProductAccess();
    add(localProductAccess);
    return localProductAccess;
  }
  
  public ProductAccess create()
  {
    ProductAccess localProductAccess = new ProductAccess();
    return localProductAccess;
  }
  
  public ProductAccess getByProductID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ProductAccess localProductAccess = (ProductAccess)localIterator.next();
      if (paramString.equalsIgnoreCase(localProductAccess.getProductID()))
      {
        localObject = localProductAccess;
        break;
      }
    }
    return localObject;
  }
  
  public ProductAccess getByEmployeeID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ProductAccess localProductAccess = (ProductAccess)localIterator.next();
      if (paramString.equalsIgnoreCase(localProductAccess.getEmployeeID()))
      {
        localObject = localProductAccess;
        break;
      }
    }
    return localObject;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PRODUCT_ACCESS_GROUP");
    ProductAccess localProductAccess = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localProductAccess = (ProductAccess)localIterator.next();
      localStringBuffer.append(localProductAccess.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PRODUCT_ACCESS_GROUP");
    return localStringBuffer.toString();
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
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("PRODUCT_ACCESS")) {
        ProductAccesses.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.ProductAccesses
 * JD-Core Version:    0.7.0.1
 */