package com.ffusion.beans.exttransfers;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ExtTransferAccounts
  extends FilteredList
  implements IdCollection
{
  public static final String EXTTRANSFERACCOUNTINFO = "EXTTRANSFERACCOUNTINFO";
  
  public ExtTransferAccounts() {}
  
  public ExtTransferAccounts(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public ExtTransferAccount add()
  {
    ExtTransferAccount localExtTransferAccount = new ExtTransferAccount(this.locale);
    add(localExtTransferAccount);
    return localExtTransferAccount;
  }
  
  public ExtTransferAccount create()
  {
    ExtTransferAccount localExtTransferAccount = new ExtTransferAccount(this.locale);
    return localExtTransferAccount;
  }
  
  public ExtTransferAccount getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)localIterator.next();
      if (localExtTransferAccount.getBpwID().equals(paramString))
      {
        localObject = localExtTransferAccount;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByID(String paramString)
  {
    return getByID(paramString);
  }
  
  public ExtTransferAccount getByBpwID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)localIterator.next();
      if (localExtTransferAccount.getBpwID().equals(paramString))
      {
        localObject = localExtTransferAccount;
        break;
      }
    }
    return localObject;
  }
  
  public ExtTransferAccount getByNickname(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)localIterator.next();
      if (localExtTransferAccount.getNickname().equalsIgnoreCase(paramString))
      {
        localObject = localExtTransferAccount;
        break;
      }
    }
    return localObject;
  }
  
  public ExtTransferAccount getByAccountNumberAndTypeAndRTN(String paramString1, int paramInt, String paramString2)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)localIterator.next();
      if ((localExtTransferAccount.getNumber().equals(paramString1)) && (localExtTransferAccount.getTypeValue() == paramInt) && (localExtTransferAccount.getRoutingNumber().equals(paramString2)))
      {
        localObject = localExtTransferAccount;
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
      ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)localIterator.next();
      if (localExtTransferAccount.getBpwID().equals(paramString))
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
        ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)localIterator.next();
        String str1 = localExtTransferAccount.getBpwID();
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
    XMLHandler.appendBeginTag(localStringBuffer, "EXTTRANSFERACCOUNTINFO");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ExtTransferAccount)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "EXTTRANSFERACCOUNTINFO");
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
  
  public void set(ExtTransferAccounts paramExtTransferAccounts)
  {
    Iterator localIterator = paramExtTransferAccounts.iterator();
    while (localIterator.hasNext())
    {
      ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)localIterator.next();
      if (localExtTransferAccount != null) {
        add(localExtTransferAccount);
      }
    }
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("EXTTRANSFERACCOUNTINFO"))
      {
        ExtTransferAccount localExtTransferAccount = ExtTransferAccounts.this.add();
        localExtTransferAccount.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.exttransfers.ExtTransferAccounts
 * JD-Core Version:    0.7.0.1
 */