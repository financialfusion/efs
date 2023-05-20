package com.ffusion.beans.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class Transactions
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String TRANSACTIONS = "TRANSACTIONS";
  protected String datetype = "SHORT";
  
  public Transactions() {}
  
  public Transactions(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public Transaction create()
  {
    Transaction localTransaction = new Transaction(this.locale);
    super.add(localTransaction);
    return localTransaction;
  }
  
  public Transaction createNoAdd()
  {
    Transaction localTransaction = new Transaction(this.locale);
    return localTransaction;
  }
  
  public boolean add(Object paramObject)
  {
    Transaction localTransaction = (Transaction)paramObject;
    localTransaction.setLocale(this.locale);
    return super.add(localTransaction);
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      localTransaction.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public Transactions getMonthlyItems(int paramInt1, int paramInt2)
  {
    Transactions localTransactions = new Transactions(this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      DateTime localDateTime = localTransaction.getDateValue();
      if ((localDateTime != null) && (localDateTime.get(1) == paramInt2) && (localDateTime.get(2) == paramInt1)) {
        localTransactions.add(localTransaction);
      }
    }
    return localTransactions;
  }
  
  public Transactions getDateItems(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Transactions localTransactions = new Transactions(this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      if ((localTransaction.getDateValue().after(paramCalendar1)) && (localTransaction.getDateValue().before(paramCalendar2))) {
        localTransactions.add(localTransaction);
      }
    }
    return localTransactions;
  }
  
  public Transactions getLastNItems(int paramInt)
  {
    Transactions localTransactions = new Transactions(this.locale);
    int i = size();
    int j = i - paramInt;
    if (j < 0) {}
    for (j = 0; j < i; j++) {
      localTransactions.add((Transaction)get(j));
    }
    return localTransactions;
  }
  
  public Transaction find(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      if (paramString.equals(localTransaction.getReferenceNumber()))
      {
        localObject = localTransaction;
        break;
      }
    }
    return localObject;
  }
  
  public Transaction getByID(String paramString)
  {
    return (Transaction)getFirstByFilter("ID=" + paramString);
  }
  
  public Transactions getCheckingItems()
  {
    Transactions localTransactions = new Transactions(this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      if (localTransaction.getTypeValue() == 3) {
        localTransactions.add(localTransaction);
      }
    }
    return localTransactions;
  }
  
  public void removeRange(int paramInt1, int paramInt2)
  {
    super.removeRange(paramInt1, paramInt2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Transaction)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "TRANSACTIONS");
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
      if (paramString.equals("TRANSACTION"))
      {
        Transaction localTransaction = Transactions.this.create();
        localTransaction.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.Transactions
 * JD-Core Version:    0.7.0.1
 */