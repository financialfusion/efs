package com.ffusion.beans.applications;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Iterator;

public class Products
  extends FilteredList
  implements Serializable
{
  public static final String PRODUCT_GROUP = "PRODUCT_GROUP";
  
  public Product add()
  {
    Product localProduct = new Product();
    add(localProduct);
    return localProduct;
  }
  
  public Product create()
  {
    Product localProduct = new Product();
    return localProduct;
  }
  
  public Product getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Product localProduct = (Product)localIterator.next();
      if (paramString.equalsIgnoreCase(localProduct.getProductID()))
      {
        localObject = localProduct;
        break;
      }
    }
    return localObject;
  }
  
  public Product getByFormID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Product localProduct = (Product)localIterator.next();
      if (paramString.equalsIgnoreCase(localProduct.getFormID()))
      {
        localObject = localProduct;
        break;
      }
    }
    return localObject;
  }
  
  public Product getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Product localProduct = (Product)localIterator.next();
      if (localProduct.getTitle().equalsIgnoreCase(paramString))
      {
        localObject = localProduct;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Product localProduct = (Product)localIterator.next();
      if (localProduct.getProductID().equals(paramString))
      {
        remove(localProduct);
        break;
      }
    }
  }
  
  public void setBankID(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Product localProduct = (Product)localIterator.next();
      localProduct.setBankID(paramString);
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PRODUCT_GROUP");
    Product localProduct = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localProduct = (Product)localIterator.next();
      localStringBuffer.append(localProduct.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PRODUCT_GROUP");
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
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("PRODUCT")) {
        Products.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Products
 * JD-Core Version:    0.7.0.1
 */