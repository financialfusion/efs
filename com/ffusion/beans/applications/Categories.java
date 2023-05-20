package com.ffusion.beans.applications;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Iterator;

public class Categories
  extends FilteredList
  implements Serializable
{
  public static final String CATEGORY_GROUP = "CATEGORY_GROUP";
  
  public Category add()
  {
    Category localCategory = new Category();
    add(localCategory);
    return localCategory;
  }
  
  public Category create()
  {
    Category localCategory = new Category();
    return localCategory;
  }
  
  public Category getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Category localCategory = (Category)localIterator.next();
      if (paramString.equalsIgnoreCase(localCategory.getID()))
      {
        localObject = localCategory;
        break;
      }
    }
    return localObject;
  }
  
  public Category getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Category localCategory = (Category)localIterator.next();
      if (localCategory.getName().equalsIgnoreCase(paramString))
      {
        localObject = localCategory;
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
    XMLHandler.appendBeginTag(localStringBuffer, "CATEGORY_GROUP");
    Category localCategory = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localCategory = (Category)localIterator.next();
      localStringBuffer.append(localCategory.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "CATEGORY_GROUP");
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
      if (paramString.equals("CATEGORY")) {
        Categories.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Categories
 * JD-Core Version:    0.7.0.1
 */