package com.ffusion.beans.aggregation;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class AccountNVPairs
  extends FilteredList
{
  public static final String ACCOUNTNVPAIRLIST = "ACCOUNTNVPAIRLIST";
  
  protected AccountNVPairs() {}
  
  public AccountNVPairs(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public AccountNVPair add()
  {
    AccountNVPair localAccountNVPair = new AccountNVPair(this.locale);
    add(localAccountNVPair);
    return localAccountNVPair;
  }
  
  public AccountNVPair create()
  {
    AccountNVPair localAccountNVPair = new AccountNVPair(this.locale);
    return localAccountNVPair;
  }
  
  public AccountNVPair getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      AccountNVPair localAccountNVPair = (AccountNVPair)localIterator.next();
      if (localAccountNVPair.getName().equalsIgnoreCase(paramString))
      {
        localObject = localAccountNVPair;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByName(String paramString)
  {
    return getByName(paramString);
  }
  
  public void removeByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      AccountNVPair localAccountNVPair = (AccountNVPair)localIterator.next();
      if (localAccountNVPair.getName().equalsIgnoreCase(paramString))
      {
        localObject = localAccountNVPair;
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNTNVPAIRLIST");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((AccountNVPair)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNTNVPAIRLIST");
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
      if (paramString.equals("ACCOUNTNVPAIR"))
      {
        AccountNVPair localAccountNVPair = AccountNVPairs.this.add();
        localAccountNVPair.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.AccountNVPairs
 * JD-Core Version:    0.7.0.1
 */