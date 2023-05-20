package com.ffusion.beans.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Reader;
import java.util.Iterator;

public class RegisterTransactions
  extends FilteredList
  implements XMLable
{
  public static final String REGISTER_TRANSACTIONS = "REGISTER_TRANSACTIONS";
  protected String datetype = "SHORT";
  private RegisterTransaction jdField_byte = null;
  
  public RegisterTransaction create()
  {
    RegisterTransaction localRegisterTransaction = new RegisterTransaction();
    add(localRegisterTransaction);
    return localRegisterTransaction;
  }
  
  public void setCurrent(String paramString)
  {
    this.jdField_byte = getByRegisterId(paramString);
  }
  
  public void setReconcileMatch(String paramString)
  {
    if (this.jdField_byte != null) {
      this.jdField_byte.setReconcileMatch(paramString);
    }
  }
  
  public String getReconcileMatch()
  {
    if (this.jdField_byte == null) {
      return null;
    }
    return this.jdField_byte.getReconcileMatch();
  }
  
  public RegisterTransaction getById(String paramString)
  {
    Object localObject = null;
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if ((localRegisterTransaction.getID() != null) && (localRegisterTransaction.getID().equals(paramString)))
      {
        localObject = localRegisterTransaction;
        break;
      }
    }
    return localObject;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      localRegisterTransaction.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public RegisterTransaction getByRegisterId(String paramString)
  {
    Object localObject = null;
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if ((localRegisterTransaction.getRegisterId() != null) && (localRegisterTransaction.getRegisterId().equals(paramString)))
      {
        localObject = localRegisterTransaction;
        break;
      }
    }
    return localObject;
  }
  
  public RegisterTransaction getByRegisterId(int paramInt)
  {
    return getByRegisterId(String.valueOf(paramInt));
  }
  
  public RegisterTransaction getByReferenceNumber(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if ((localRegisterTransaction.getReferenceNumber() != null) && (localRegisterTransaction.getReferenceNumber().equals(paramString)))
      {
        localObject = localRegisterTransaction;
        break;
      }
    }
    return localObject;
  }
  
  public void removeById(String paramString)
  {
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      String str = localRegisterTransaction.getID();
      if ((str != null) && (str.equalsIgnoreCase(paramString)))
      {
        localIterator.remove();
        break;
      }
    }
  }
  
  public void removeByRegisterId(String paramString)
  {
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if ((localRegisterTransaction.getRegisterId() != null) && (localRegisterTransaction.getRegisterId().equalsIgnoreCase(paramString)))
      {
        localIterator.remove();
        break;
      }
    }
  }
  
  public void removeByReferenceNumber(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localRegisterTransaction.getReferenceNumber().equalsIgnoreCase(paramString))
      {
        localIterator.remove();
        break;
      }
    }
  }
  
  public boolean hasUnreconciledTransactions()
  {
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localRegisterTransaction.getStatusValue() == 1) {
        return true;
      }
    }
    return false;
  }
  
  public boolean getHasUnreconciledTransactions()
  {
    return hasUnreconciledTransactions();
  }
  
  public String getUnreconciledTransactionsAmount()
  {
    Currency localCurrency = getUnreconciledTransactionsAmountValue();
    if (localCurrency == null) {
      localCurrency = new Currency("0", this.locale);
    }
    return localCurrency.toString();
  }
  
  public Currency getUnreconciledTransactionsAmountValue()
  {
    Currency localCurrency = null;
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localCurrency == null) {
        localCurrency = new Currency("0", localRegisterTransaction.getLocale());
      }
      if (localRegisterTransaction.getStatusValue() == 1)
      {
        localCurrency.addAmount(localRegisterTransaction.getAmountValue());
        localCurrency.setCurrencyCode(localRegisterTransaction.getAmountValue().getCurrencyCode());
      }
    }
    return localCurrency;
  }
  
  public String getUnreconciledTransactionsCount()
  {
    int i = 0;
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localRegisterTransaction.getStatusValue() == 1) {
        i++;
      }
    }
    return String.valueOf(i);
  }
  
  public String getCurrentOutstandingTransactionsAmount()
  {
    Currency localCurrency = getCurrentOutstandingTransactionsAmountValue();
    if (localCurrency == null) {
      return null;
    }
    return localCurrency.toString();
  }
  
  public Currency getCurrentOutstandingTransactionsAmountValue()
  {
    Currency localCurrency = null;
    DateTime localDateTime = new DateTime(this.locale);
    localDateTime.add(6, 1);
    localDateTime.set(11, 0);
    localDateTime.set(12, 0);
    localDateTime.set(13, 0);
    localDateTime.set(14, 0);
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localCurrency == null) {
        localCurrency = new Currency("0", localRegisterTransaction.getLocale());
      }
      if ((localRegisterTransaction.getStatusValue() == 0) && (localRegisterTransaction.getDateIssued() != null) && (localRegisterTransaction.getDateIssuedValue().before(localDateTime)))
      {
        localCurrency.addAmount(localRegisterTransaction.getAmountValue());
        localCurrency.setCurrencyCode(localRegisterTransaction.getAmountValue().getCurrencyCode());
      }
    }
    return localCurrency;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REGISTER_TRANSACTIONS");
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((RegisterTransaction)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REGISTER_TRANSACTIONS");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Exception localException) {}
  }
  
  public void startXMLParsing(Reader paramReader)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramReader);
    }
    catch (Exception localException) {}
  }
  
  private class a
    extends XMLHandler
  {
    private String jdField_int = "";
    private String jdField_new = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("REGISTER_TRANSACTION"))
      {
        RegisterTransaction localRegisterTransaction = new RegisterTransaction();
        localRegisterTransaction.continueXMLParsing(getHandler());
        RegisterTransactions.this.add(localRegisterTransaction);
      }
      this.jdField_int = "";
      this.jdField_new = "";
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.jdField_int = paramString1;
      this.jdField_new = paramString2;
    }
    
    a(RegisterTransactions.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.RegisterTransactions
 * JD-Core Version:    0.7.0.1
 */