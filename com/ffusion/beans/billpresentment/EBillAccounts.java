package com.ffusion.beans.billpresentment;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class EBillAccounts
  extends FilteredList
{
  public static final String EBILLACCOUNTS = "EBILLACCOUNTS";
  private String Io = "SHORT";
  
  public EBillAccounts() {}
  
  public EBillAccounts(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public EBillAccount add()
  {
    EBillAccount localEBillAccount = new EBillAccount(this.locale);
    add(localEBillAccount);
    return localEBillAccount;
  }
  
  public EBillAccount create()
  {
    return new EBillAccount(this.locale);
  }
  
  public void setDateFormat(String paramString)
  {
    this.Io = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EBillAccount localEBillAccount = (EBillAccount)localIterator.next();
      localEBillAccount.setDateFormat(this.Io);
    }
  }
  
  public String getDateFormat()
  {
    return this.Io;
  }
  
  public EBillAccount getByAccountID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EBillAccount localEBillAccount = (EBillAccount)localIterator.next();
      if (localEBillAccount.getAccountIDValue() == paramLong)
      {
        localObject = localEBillAccount;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByAccountID(long paramLong)
  {
    for (int i = 0; i < size(); i++)
    {
      EBillAccount localEBillAccount = (EBillAccount)get(i);
      if (localEBillAccount.getAccountIDValue() == paramLong)
      {
        remove(i);
        return;
      }
    }
  }
  
  public EBillAccount getByConsumerID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EBillAccount localEBillAccount = (EBillAccount)localIterator.next();
      if (localEBillAccount.getConsumerIDValue() == paramLong)
      {
        localObject = localEBillAccount;
        break;
      }
    }
    return localObject;
  }
  
  public EBillAccount getByBillerID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EBillAccount localEBillAccount = (EBillAccount)localIterator.next();
      if (localEBillAccount.getBillerIDValue() == paramLong)
      {
        localObject = localEBillAccount;
        break;
      }
    }
    return localObject;
  }
  
  public EBillAccount getByAccountNumber(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EBillAccount localEBillAccount = (EBillAccount)localIterator.next();
      if (localEBillAccount.getAccountNumber().equalsIgnoreCase(paramString))
      {
        localObject = localEBillAccount;
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
    XMLHandler.appendBeginTag(localStringBuffer, "EBILLACCOUNTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((EBillAccount)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "EBILLACCOUNTS");
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
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, boolean paramBoolean)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("EBILLACCOUNT"))
      {
        EBillAccount localEBillAccount = new EBillAccount();
        EBillAccounts.this.add(localEBillAccount);
        localEBillAccount.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.EBillAccounts
 * JD-Core Version:    0.7.0.1
 */