package com.ffusion.beans.exttransfers;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ExtTransferCompanies
  extends FilteredList
  implements IdCollection
{
  public static final String EXTTRANSFERCOMPANYLIST = "EXTTRANSFERCOMPANYLIST";
  
  public ExtTransferCompanies() {}
  
  public ExtTransferCompanies(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public ExtTransferCompany add()
  {
    ExtTransferCompany localExtTransferCompany = new ExtTransferCompany(this.locale);
    add(localExtTransferCompany);
    return localExtTransferCompany;
  }
  
  public ExtTransferCompany create()
  {
    ExtTransferCompany localExtTransferCompany = new ExtTransferCompany(this.locale);
    return localExtTransferCompany;
  }
  
  public ExtTransferCompany getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)localIterator.next();
      if (localExtTransferCompany.getBpwID().equals(paramString))
      {
        localObject = localExtTransferCompany;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByID(String paramString)
  {
    return getByID(paramString);
  }
  
  public ExtTransferCompany getByBpwID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)localIterator.next();
      if (localExtTransferCompany.getBpwID().equals(paramString))
      {
        localObject = localExtTransferCompany;
        break;
      }
    }
    return localObject;
  }
  
  public ExtTransferCompany getByCompanyName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)localIterator.next();
      if (localExtTransferCompany.getCompanyName().equalsIgnoreCase(paramString))
      {
        localObject = localExtTransferCompany;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    int i = 0;
    int j = -1;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)localIterator.next();
      if (localExtTransferCompany.getBpwID().equals(paramString))
      {
        j = i;
        break;
      }
      i++;
    }
    if (j != -1) {
      remove(j);
    }
  }
  
  public void setRemoveFromFilteredItems(String paramString)
  {
    StringTokenizer localStringTokenizer = null;
    if (this.filteredList != null)
    {
      Iterator localIterator = this.filteredList.iterator();
      while (localIterator.hasNext())
      {
        ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)localIterator.next();
        String str1 = localExtTransferCompany.getBpwID();
        int i = 0;
        localStringTokenizer = new StringTokenizer(paramString, ",");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str2 = localStringTokenizer.nextToken();
          if (str2.equals(str1))
          {
            i = 1;
            break;
          }
        }
        if (i != 0) {
          localIterator.remove();
        }
      }
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
    XMLHandler.appendBeginTag(localStringBuffer, "EXTTRANSFERCOMPANYINFO");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ExtTransferCompany)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "EXTTRANSFERCOMPANYINFO");
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
  
  public void set(ExtTransferCompanies paramExtTransferCompanies)
  {
    Iterator localIterator = paramExtTransferCompanies.iterator();
    while (localIterator.hasNext())
    {
      ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)localIterator.next();
      if (localExtTransferCompany != null) {
        add(localExtTransferCompany);
      }
    }
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("EXTTRANSFERCOMPANYINFO"))
      {
        ExtTransferCompany localExtTransferCompany = ExtTransferCompanies.this.add();
        localExtTransferCompany.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.exttransfers.ExtTransferCompanies
 * JD-Core Version:    0.7.0.1
 */