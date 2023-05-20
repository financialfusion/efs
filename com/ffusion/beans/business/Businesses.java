package com.ffusion.beans.business;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class Businesses
  extends FilteredList
  implements IdCollection
{
  public static final String BUSINESSES = "BUSINESSES";
  
  public Businesses() {}
  
  public Businesses(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public Business add()
  {
    Business localBusiness = new Business(this.locale);
    add(localBusiness);
    return localBusiness;
  }
  
  public Business create()
  {
    Business localBusiness = new Business(this.locale);
    return localBusiness;
  }
  
  public void set(Businesses paramBusinesses)
  {
    Iterator localIterator = paramBusinesses.iterator();
    while (localIterator.hasNext())
    {
      Business localBusiness = (Business)localIterator.next();
      add(localBusiness);
    }
  }
  
  public void setUniquely(Businesses paramBusinesses)
  {
    Iterator localIterator = paramBusinesses.iterator();
    while (localIterator.hasNext())
    {
      Business localBusiness = (Business)localIterator.next();
      if (((localBusiness == null) && (!contains(null))) || (getById(localBusiness.getIdValue()) == null)) {
        add(localBusiness);
      }
    }
  }
  
  public Business getById(int paramInt)
  {
    Object localObject = null;
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Business localBusiness = (Business)localIterator.next();
      if ((localBusiness != null) && (localBusiness.getIdValue() == paramInt))
      {
        localObject = localBusiness;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByID(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
    return getById(i);
  }
  
  public Business getByBusinessName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Business localBusiness = (Business)localIterator.next();
      if ((localBusiness != null) && (localBusiness.getBusinessName().equalsIgnoreCase(paramString)))
      {
        localObject = localBusiness;
        break;
      }
    }
    return localObject;
  }
  
  public void removeById(int paramInt)
  {
    Object localObject = null;
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Business localBusiness = (Business)localIterator.next();
      if ((localBusiness != null) && (localBusiness.getIdValue() == paramInt))
      {
        localObject = localBusiness;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public void removeByID(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
    removeById(i);
  }
  
  public void removeDeactivatedBusinesses()
  {
    Businesses localBusinesses = new Businesses();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Business localBusiness = (Business)localIterator.next();
      if ((localBusiness != null) && (localBusiness.getStatus().compareTo("2") == 0)) {
        localBusinesses.add(localBusiness);
      }
    }
    localIterator = localBusinesses.iterator();
    while (localIterator.hasNext()) {
      remove(localIterator.next());
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
    XMLHandler.appendBeginTag(localStringBuffer, "BUSINESSES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Business)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BUSINESSES");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
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
      if (paramString.equals(Business.BUSINESS))
      {
        Business localBusiness = Businesses.this.add();
        localBusiness.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.Businesses
 * JD-Core Version:    0.7.0.1
 */