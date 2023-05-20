package com.ffusion.beans.tw;

import com.ffusion.beans.XMLStrings;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class TWTransactions
  extends FilteredList
  implements XMLStrings, XMLable
{
  public TWTransactions(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public TWTransaction add()
  {
    TWTransaction localTWTransaction = new TWTransaction(this.locale);
    add(localTWTransaction);
    return localTWTransaction;
  }
  
  public void setDateFormat(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      localTransaction.setDateFormat(paramString);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof TWTransactions)) {
      return false;
    }
    TWTransactions localTWTransactions = (TWTransactions)paramObject;
    if (size() != localTWTransactions.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localTWTransactions.locale;
    if (localLocale1 != null)
    {
      if (localLocale2 != null)
      {
        if (!localLocale1.equals(localLocale2)) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else if (localLocale2 != null) {
      return false;
    }
    return containsAll(localTWTransactions);
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
    XMLHandler.appendBeginTag(localStringBuffer, "TW_TRANSACTIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((TWTransaction)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "TW_TRANSACTIONS");
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
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("TW_TRANSACTION"))
      {
        TWTransaction localTWTransaction = new TWTransaction(TWTransactions.this.locale);
        TWTransactions.this.add(localTWTransaction);
        localTWTransaction.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.tw.TWTransactions
 * JD-Core Version:    0.7.0.1
 */