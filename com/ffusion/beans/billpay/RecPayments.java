package com.ffusion.beans.billpay;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class RecPayments
  extends Payments
{
  public static final String RECPAYMENTS = "RECPAYMENTS";
  
  public RecPayments() {}
  
  public RecPayments(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public Object create()
  {
    RecPayment localRecPayment = new RecPayment(this.locale);
    add(localRecPayment);
    return localRecPayment;
  }
  
  public Object createNoAdd()
  {
    RecPayment localRecPayment = new RecPayment(this.locale);
    return localRecPayment;
  }
  
  public boolean add(Object paramObject)
  {
    RecPayment localRecPayment = (RecPayment)paramObject;
    localRecPayment.setLocale(this.locale);
    return super.add(localRecPayment);
  }
  
  public RecPayment getByRecID(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        RecPayment localRecPayment = (RecPayment)localIterator.next();
        if (paramString.equals(localRecPayment.getRecPaymentID()))
        {
          localObject = localRecPayment;
          break;
        }
      }
    }
    return localObject;
  }
  
  public RecPayments getPaymentsToPayee(Payee paramPayee)
  {
    RecPayments localRecPayments = new RecPayments(this.locale);
    boolean bool = this.isIterator;
    this.isIterator = true;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RecPayment localRecPayment = (RecPayment)localIterator.next();
      if ((localRecPayment.getStatus() == 2) && ((paramPayee == null) || ((localRecPayment.getPayee() != null) && (paramPayee.getID().equals(localRecPayment.getPayee().getID()))))) {
        localRecPayments.add(localRecPayment);
      }
    }
    this.isIterator = bool;
    return localRecPayments;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString, Accounts paramAccounts, Payees paramPayees)
  {
    setXML(paramString, paramAccounts, paramPayees);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "RECPAYMENTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((RecPayment)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "RECPAYMENTS");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, Accounts paramAccounts, Payees paramPayees)
  {
    paramXMLHandler.continueWith(new a(paramAccounts, paramPayees));
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null, null));
  }
  
  class a
    extends Payments.a
  {
    public a(Accounts paramAccounts, Payees paramPayees)
    {
      super(paramAccounts, paramPayees);
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("RECPAYMENT"))
      {
        RecPayment localRecPayment = (RecPayment)RecPayments.this.create();
        localRecPayment.continueXMLParsing(getHandler(), this.jdField_new, this.jdField_int);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.RecPayments
 * JD-Core Version:    0.7.0.1
 */