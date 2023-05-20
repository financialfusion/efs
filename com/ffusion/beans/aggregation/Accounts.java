package com.ffusion.beans.aggregation;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.Iterator;
import java.util.Locale;

public class Accounts
  extends FilteredList
{
  public static final String ACCOUNTS = "ACCOUNTS";
  protected String datetype = "SHORT";
  
  public Accounts() {}
  
  public Accounts(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public Account create(String paramString1, String paramString2, int paramInt)
  {
    Account localAccount = new Account(this.locale, paramString1, paramString2, paramInt);
    super.add(localAccount);
    return localAccount;
  }
  
  public Account create(String paramString, int paramInt)
  {
    Account localAccount = new Account(this.locale, paramString, paramInt);
    super.add(localAccount);
    return localAccount;
  }
  
  public Account create(String paramString)
  {
    Account localAccount = new Account(this.locale, paramString);
    super.add(localAccount);
    return localAccount;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      localAccount.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public boolean add(Object paramObject)
  {
    Account localAccount = (Account)paramObject;
    localAccount.setLocale(this.locale);
    return super.add(localAccount);
  }
  
  public void setFormat(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      localAccount.setFormat(paramString);
    }
  }
  
  public Account getByID(String paramString)
  {
    Object localObject = null;
    try
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        if (localAccount.getID().equals(paramString))
        {
          localObject = localAccount;
          break;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Exception", localThrowable);
    }
    return localObject;
  }
  
  public Account getByAccountNumberAndType(String paramString, int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if ((localAccount.getNumber().equals(paramString)) && (localAccount.getTypeValue() == paramInt))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Account)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNTS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(true), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, boolean paramBoolean)
  {
    paramXMLHandler.continueWith(new a(paramBoolean));
  }
  
  public void removeByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (localAccount.getID().equals(paramString))
      {
        localObject = localAccount;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  class a
    extends XMLHandler
  {
    boolean jdField_int;
    
    public a(boolean paramBoolean)
    {
      this.jdField_int = paramBoolean;
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("ACCOUNT"))
      {
        Account localAccount = Accounts.this.create("", "", 0);
        localAccount.continueXMLParsing(getHandler(), this.jdField_int);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.Accounts
 * JD-Core Version:    0.7.0.1
 */