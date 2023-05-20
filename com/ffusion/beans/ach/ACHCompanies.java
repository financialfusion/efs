package com.ffusion.beans.ach;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class ACHCompanies
  extends FilteredList
  implements XMLStrings
{
  public ACHCompanies(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ACHCompany create()
  {
    ACHCompany localACHCompany = new ACHCompany(this.locale);
    super.add(localACHCompany);
    return localACHCompany;
  }
  
  public ACHCompany create(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    ACHCompany localACHCompany = new ACHCompany(paramString1, paramString2, paramString3, paramString4);
    super.add(localACHCompany);
    return localACHCompany;
  }
  
  public ACHCompany getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHCompany localACHCompany = (ACHCompany)localIterator.next();
      if (localACHCompany.getID().equals(paramString))
      {
        localObject = localACHCompany;
        break;
      }
    }
    return localObject;
  }
  
  public ACHCompany getByCompanyID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHCompany localACHCompany = (ACHCompany)localIterator.next();
      if (localACHCompany.getCompanyID().equals(paramString))
      {
        localObject = localACHCompany;
        break;
      }
    }
    return localObject;
  }
  
  public ACHCompany getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHCompany localACHCompany = (ACHCompany)localIterator.next();
      if (localACHCompany.getCompanyName().equals(paramString))
      {
        localObject = localACHCompany;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByCompanyID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHCompany localACHCompany = (ACHCompany)localIterator.next();
      if (localACHCompany.getCompanyID().equals(paramString))
      {
        localObject = localACHCompany;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACHCOMPANIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ACHCompany)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACHCOMPANIES");
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
      if (paramString.equals("ACHCOMPANY"))
      {
        ACHCompany localACHCompany = ACHCompanies.this.create();
        localACHCompany.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHCompanies
 * JD-Core Version:    0.7.0.1
 */