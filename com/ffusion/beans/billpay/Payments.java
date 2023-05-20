package com.ffusion.beans.billpay;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;

public class Payments
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String PAYMENTS = "PAYMENTS";
  public static final String ALL = "ALL";
  public static final String PAID = "PAID";
  public static final String CANCELED = "CANCELED";
  public static final String PENDING = "PENDING";
  protected String dateformat = "SHORT";
  protected DateTime startDate;
  protected DateTime endDate;
  protected String accountID;
  protected String payeeID;
  protected String payeeName;
  protected boolean allDates = true;
  protected boolean allAccounts = true;
  protected boolean allPayees = true;
  protected int status = -1;
  protected boolean isIterator = false;
  
  public Payments() {}
  
  public Payments(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.dateformat = "SHORT";
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    if (this.startDate != null) {
      this.startDate.setFormat(paramString);
    }
    if (this.endDate != null) {
      this.endDate.setFormat(paramString);
    }
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      localPayment.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public void setBypassIterator(String paramString)
  {
    this.isIterator = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected void setIsIterator(boolean paramBoolean)
  {
    this.isIterator = paramBoolean;
  }
  
  public Iterator iterator()
  {
    if (this.isIterator) {
      return super.iterator();
    }
    Payments localPayments = new Payments(this.locale);
    localPayments.setIsIterator(true);
    for (int i = 0; i < size(); i++)
    {
      Payment localPayment = (Payment)get(i);
      if (localPayment != null)
      {
        DateTime localDateTime = localPayment.getPayDateValue();
        if (((this.allAccounts) || (localPayment.getAccount() == null) || (this.accountID.equals(localPayment.getAccount().getID()))) && ((this.allPayees) || (this.payeeID == null) || ((localPayment.getPayee() != null) && (this.payeeID.equals(localPayment.getPayee().getID())))) && ((this.allPayees) || (this.payeeName == null) || (this.payeeName.equals(localPayment.getPayeeName()))) && ((this.startDate == null) || (this.allDates) || (!localDateTime.before(this.startDate))) && ((this.endDate == null) || (this.allDates) || (!localDateTime.after(this.endDate))) && ((this.status == -1) || (localPayment.getStatus() == this.status))) {
          localPayments.add(localPayment);
        }
      }
    }
    localPayments.setFilter(getFilter());
    return localPayments.iterator();
  }
  
  public Object create()
  {
    Payment localPayment = new Payment(this.locale);
    add(localPayment);
    return localPayment;
  }
  
  public static Object createNoAdd(Locale paramLocale)
  {
    return new Payment(paramLocale);
  }
  
  public Object createNoAdd()
  {
    return new Payment(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    Payment localPayment = (Payment)paramObject;
    localPayment.setLocale(this.locale);
    return super.add(localPayment);
  }
  
  public Payment getByID(String paramString)
  {
    return (Payment)getFirstByFilter("ID=" + paramString);
  }
  
  public Payments getByRecPaymentID(String paramString)
  {
    Payments localPayments = new Payments();
    if (paramString != null)
    {
      ListIterator localListIterator = listIterator();
      while (localListIterator.hasNext())
      {
        Payment localPayment = (Payment)localListIterator.next();
        if (paramString.equals(localPayment.getRecPaymentID())) {
          localPayments.add(localPayment);
        }
      }
    }
    return localPayments;
  }
  
  public Payment getByTransactionID(String paramString)
  {
    return (Payment)getFirstByFilter("TransactionID=" + paramString);
  }
  
  public Payments getScheduledPaymentsToPayee(Payee paramPayee)
  {
    Payments localPayments = new Payments(this.locale);
    boolean bool = this.isIterator;
    this.isIterator = true;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      if ((localPayment.getStatus() == 2) && ((paramPayee == null) || ((localPayment.getPayee() != null) && (paramPayee.getID().equals(localPayment.getPayee().getID()))))) {
        localPayments.add(localPayment);
      }
    }
    this.isIterator = bool;
    return localPayments;
  }
  
  public void getHistoryRange(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      if (localObject1 == null)
      {
        localObject1 = localPayment.getPayDateValue();
        localObject2 = localPayment.getPayDateValue();
      }
      else
      {
        DateTime localDateTime = localPayment.getPayDateValue();
        if (((Calendar)localObject1).after(localDateTime)) {
          localObject1 = localDateTime;
        }
        if (((Calendar)localObject2).before(localDateTime)) {
          localObject2 = localDateTime;
        }
      }
    }
    if (localObject1 != null) {
      paramCalendar1.setTime(((Calendar)localObject1).getTime());
    }
    if (localObject2 != null) {
      paramCalendar2.setTime(((Calendar)localObject2).getTime());
    }
  }
  
  public ArrayList getPayees(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    int i = size();
    String str;
    for (int j = 0; j < i; j++)
    {
      Payment localPayment = (Payment)get(j);
      str = localPayment.getPayeeName();
      if ((localPayment.getStatus() == paramInt) && (str != null) && (!localArrayList.contains(str))) {
        localArrayList.add(str);
      }
    }
    i = localArrayList.size();
    for (j = 0; j < i; j++)
    {
      str = (String)localArrayList.get(j);
      for (int k = j; k < i; k++) {
        if (((String)localArrayList.get(k)).compareTo(str) < 0)
        {
          localArrayList.set(j, localArrayList.get(k));
          localArrayList.set(k, str);
          str = (String)localArrayList.get(j);
        }
      }
    }
    return localArrayList;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
    if (paramString.equalsIgnoreCase("ALL")) {
      this.allAccounts = true;
    } else {
      this.allAccounts = false;
    }
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeID = paramString;
    this.payeeName = null;
    if (paramString.equalsIgnoreCase("ALL")) {
      this.allPayees = true;
    } else {
      this.allPayees = false;
    }
  }
  
  public String getPayeeID()
  {
    return this.payeeID;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeID = null;
    this.payeeName = paramString;
    if (paramString.equalsIgnoreCase("ALL")) {
      this.allPayees = true;
    } else {
      this.allPayees = false;
    }
  }
  
  public String getPayeeName()
  {
    return this.payeeName;
  }
  
  public void setAllAccounts(String paramString)
  {
    this.allAccounts = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAllAccounts()
  {
    return String.valueOf(this.allAccounts);
  }
  
  public void setAllPayees(String paramString)
  {
    this.allPayees = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAllPayees()
  {
    return String.valueOf(this.allPayees);
  }
  
  public void setStartDate(String paramString)
  {
    try
    {
      if (this.startDate == null) {
        this.startDate = new DateTime(paramString, this.locale, this.dateformat);
      } else {
        this.startDate.fromString(paramString);
      }
      this.startDate.set(11, 0);
    }
    catch (Throwable localThrowable)
    {
      this.startDate = null;
    }
  }
  
  public String getStartDate()
  {
    if (this.startDate == null) {
      return "";
    }
    return this.startDate.toString();
  }
  
  public Calendar getStartDateValue()
  {
    return this.startDate;
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if (this.endDate == null) {
        this.endDate = new DateTime(paramString, this.locale, this.dateformat);
      } else {
        this.endDate.fromString(paramString);
      }
      this.endDate.set(11, 23);
    }
    catch (Throwable localThrowable)
    {
      this.endDate = null;
    }
  }
  
  public String getEndDate()
  {
    if (this.endDate == null) {
      return "";
    }
    return this.endDate.toString();
  }
  
  public Calendar getEndDateValue()
  {
    return this.endDate;
  }
  
  public void setStatus(String paramString)
  {
    this.status = -1;
    if (paramString.equalsIgnoreCase("PAID")) {
      this.status = 5;
    } else if (paramString.equalsIgnoreCase("CANCELED")) {
      this.status = 3;
    } else if (paramString.equalsIgnoreCase("PENDING")) {
      this.status = 2;
    }
  }
  
  public String getStatus()
  {
    switch (this.status)
    {
    case 5: 
      return "PAID";
    case 3: 
      return "CANCELED";
    case 2: 
      return "PENDING";
    }
    return "ALL";
  }
  
  public void setAllDates(String paramString)
  {
    this.allDates = Boolean.valueOf(paramString).booleanValue();
    if (this.allDates)
    {
      if (this.startDate == null)
      {
        this.startDate = new DateTime(this.locale);
        this.startDate.setFormat(this.dateformat);
      }
      if (this.endDate == null)
      {
        this.endDate = new DateTime(this.locale);
        this.endDate.setFormat(this.dateformat);
      }
      getHistoryRange(this.startDate, this.endDate);
    }
  }
  
  public String getAllDates()
  {
    return String.valueOf(this.allDates);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString, Accounts paramAccounts, Payees paramPayees)
  {
    setXML(paramString, paramAccounts, paramPayees);
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString, null, null);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PAYMENTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Payment)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PAYMENTS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString, Accounts paramAccounts, Payees paramPayees)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramAccounts, paramPayees), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void setXML(String paramString)
  {
    setXML(paramString, null, null);
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
    extends XMLHandler
  {
    Accounts jdField_new;
    Payees jdField_int;
    
    public a(Accounts paramAccounts, Payees paramPayees)
    {
      this.jdField_new = paramAccounts;
      this.jdField_int = paramPayees;
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("PAYMENT"))
      {
        Payment localPayment = (Payment)Payments.this.create();
        localPayment.continueXMLParsing(getHandler(), this.jdField_new, this.jdField_int);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.Payments
 * JD-Core Version:    0.7.0.1
 */