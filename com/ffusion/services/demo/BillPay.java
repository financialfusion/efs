package com.ffusion.services.demo;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.BillPay6;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.PagingContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class BillPay
  extends Service
  implements BillPay6
{
  protected Accounts accountlist;
  protected Payees payees;
  protected Payees payeelist;
  protected Payments payments;
  protected RecPayments recpayments;
  protected int nextPayeeID = 0;
  
  public int initialize(String paramString)
  {
    return super.initialize("demo.xml");
  }
  
  public void setUserName(String paramString) {}
  
  public void setPassword(String paramString) {}
  
  public void setSecureUser(SecureUser paramSecureUser) {}
  
  public boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    return true;
  }
  
  public int getPayees(Payees paramPayees)
  {
    this.payees = paramPayees;
    jdMethod_do(false);
    int i = 0;
    Iterator localIterator = this.payees.iterator();
    while (localIterator.hasNext())
    {
      Payee localPayee = (Payee)localIterator.next();
      int j = Integer.parseInt(localPayee.getID());
      if (j > i) {
        i = j;
      }
    }
    this.nextPayeeID = i;
    this.payees = null;
    return 0;
  }
  
  public int addPayees(Payees paramPayees)
  {
    Iterator localIterator = paramPayees.iterator();
    while (localIterator.hasNext())
    {
      this.nextPayeeID += 1;
      Payee localPayee = (Payee)localIterator.next();
      localPayee.setID(String.valueOf(this.nextPayeeID));
      localPayee.setStatus(2);
    }
    return 0;
  }
  
  public int modifyPayees(Payees paramPayees)
  {
    return 0;
  }
  
  public int deletePayees(Payees paramPayees)
  {
    Iterator localIterator = paramPayees.iterator();
    while (localIterator.hasNext())
    {
      Payee localPayee = (Payee)localIterator.next();
      localPayee.setStatus(3);
    }
    return 0;
  }
  
  public Payees getGlobalPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    Payees localPayees = new Payees();
    this.payees = localPayees;
    jdMethod_do(false);
    this.payees = null;
    return localPayees;
  }
  
  public Payee getPayeeByID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
  {
    return null;
  }
  
  public RecPayment getRecPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
  {
    return null;
  }
  
  public Payment getPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
  {
    return null;
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees)
  {
    return sendPayments(paramAccount, paramPayments, paramPayees, null);
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees, HashMap paramHashMap)
  {
    Iterator localIterator = paramPayments.iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      localPayment.setID(Integer.toString((int)(Math.random() * 100000.0D)));
      Calendar localCalendar = (Calendar)localPayment.getPayDateValue().clone();
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      localCalendar.add(5, -4);
      localPayment.put("PayProcessDate", localSimpleDateFormat.format(localCalendar.getTime()));
      localPayment.setConfirmationNum(Integer.toString((int)(Math.random() * 100000.0D)));
      localPayment.setReferenceNumber(Integer.toString((int)(Math.random() * 100000.0D)));
      localPayment.setStatus(2);
    }
    return 0;
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment, HashMap paramHashMap)
  {
    return modifyPayment(paramAccount, paramPayment);
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment)
  {
    return 0;
  }
  
  public int cancelPayments(Payments paramPayments)
  {
    Iterator localIterator = paramPayments.iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      localPayment.setStatus(3);
    }
    return 0;
  }
  
  public int getPayments(Accounts paramAccounts, Payments paramPayments, RecPayments paramRecPayments, Payees paramPayees)
  {
    this.payments = paramPayments;
    this.recpayments = paramRecPayments;
    this.accountlist = paramAccounts;
    this.payeelist = paramPayees;
    jdMethod_do(false);
    filterPayments(paramAccounts, paramPayments);
    filterPayments(paramAccounts, paramRecPayments);
    this.payments = null;
    this.recpayments = null;
    GregorianCalendar localGregorianCalendar;
    int i;
    int j;
    Iterator localIterator;
    Payment localPayment;
    DateTime localDateTime;
    if (paramPayments.size() > 0)
    {
      localGregorianCalendar = new GregorianCalendar();
      i = localGregorianCalendar.get(1);
      j = localGregorianCalendar.get(2);
      localIterator = paramPayments.iterator();
      while (localIterator.hasNext())
      {
        localPayment = (Payment)localIterator.next();
        localDateTime = localPayment.getPayDateValue();
        localDateTime.set(1, i);
        localDateTime.set(2, j);
        if (localPayment.getStatus() == 5)
        {
          if (localDateTime.after(localGregorianCalendar)) {
            localDateTime.add(2, -1);
          }
        }
        else if ((localPayment.getStatus() == 2) && (localDateTime.before(localGregorianCalendar))) {
          localDateTime.add(2, 1);
        }
      }
      paramPayments.setSortedBy("PAYDATE");
    }
    if (paramRecPayments.size() > 0)
    {
      localGregorianCalendar = new GregorianCalendar();
      i = localGregorianCalendar.get(1);
      j = localGregorianCalendar.get(2);
      localIterator = paramRecPayments.iterator();
      while (localIterator.hasNext())
      {
        localPayment = (Payment)localIterator.next();
        localDateTime = localPayment.getPayDateValue();
        localDateTime.set(1, i);
        localDateTime.set(2, j);
        if (localPayment.getStatus() == 5)
        {
          if (localDateTime.after(localGregorianCalendar)) {
            localDateTime.add(2, -1);
          }
        }
        else if ((localPayment.getStatus() == 2) && (localDateTime.before(localGregorianCalendar))) {
          localDateTime.add(2, 1);
        }
      }
      paramRecPayments.setSortedBy("PAYDATE");
    }
    return 0;
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees)
  {
    return sendRecPayments(paramAccount, paramRecPayments, paramPayees, null);
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees, HashMap paramHashMap)
  {
    Iterator localIterator = paramRecPayments.iterator();
    while (localIterator.hasNext())
    {
      RecPayment localRecPayment = (RecPayment)localIterator.next();
      localRecPayment.setID(Integer.toString((int)(Math.random() * 100000.0D)));
      localRecPayment.setConfirmationNum(Integer.toString((int)(Math.random() * 100000.0D)));
      localRecPayment.setStatus(2);
    }
    return 0;
  }
  
  public int deleteRecPayment(RecPayment paramRecPayment)
  {
    if (paramRecPayment != null) {
      paramRecPayment.setStatus(3);
    }
    return 0;
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    paramRecPayment.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment, HashMap paramHashMap)
  {
    paramRecPayment.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int skipRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    return 0;
  }
  
  public Payments getPagedPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("FIRST");
    return a(paramAccounts, paramPayees, paramPagingContext, 2, paramHashMap);
  }
  
  public Payments getNextPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("NEXT");
    return a(paramAccounts, paramPayees, paramPagingContext, 2, paramHashMap);
  }
  
  public Payments getPreviousPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(paramAccounts, paramPayees, paramPagingContext, 2, paramHashMap);
  }
  
  public Payments getPagedCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("FIRST");
    return a(paramAccounts, paramPayees, paramPagingContext, 5, paramHashMap);
  }
  
  public Payments getNextCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("NEXT");
    return a(paramAccounts, paramPayees, paramPagingContext, 5, paramHashMap);
  }
  
  public Payments getPreviousCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(paramAccounts, paramPayees, paramPagingContext, 5, paramHashMap);
  }
  
  private Payments a(Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws Exception
  {
    long l = 0L;
    String str1 = "PAYDATE,REVERSE";
    if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
    {
      str1 = "PAYDATE";
      l = paramPagingContext.getFirstIndex();
    }
    else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
    {
      l = paramPagingContext.getLastIndex();
    }
    if (paramAccounts == null) {
      return null;
    }
    String str2 = String.valueOf(l);
    Payments localPayments1 = new Payments();
    RecPayments localRecPayments = new RecPayments();
    int i = getPayments(paramAccounts, localPayments1, localRecPayments, paramPayees);
    if ((i == 0) && (localPayments1 != null))
    {
      localPayments1.setSortedBy(str1);
      int j = 0;
      int k = jdMethod_for(paramHashMap);
      Payments localPayments2 = new Payments();
      int m = 0;
      if (l == 0L) {
        m = 1;
      }
      Object localObject;
      for (int n = 0; (n < localPayments1.size()) && (j < k); n++)
      {
        localObject = (Payment)localPayments1.get(n);
        if ((m != 0) && (((Payment)localObject).getStatus() == paramInt) && ((paramPagingContext.getStartDate() == null) || (((Payment)localObject).getPayDateValue().after(paramPagingContext.getStartDate()))) && ((paramPagingContext.getEndDate() == null) || (((Payment)localObject).getPayDateValue().before(paramPagingContext.getEndDate()))))
        {
          localPayments2.add(localObject);
          j++;
        }
        else if ((m == 0) && (((Payment)localObject).getID().equals(str2)))
        {
          m = 1;
        }
      }
      paramPagingContext.setFirstPage(false);
      paramPagingContext.setLastPage(false);
      if (localPayments2.size() < k) {
        paramPagingContext.setLastPage(true);
      }
      HashMap localHashMap;
      if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
      {
        paramPagingContext.setFirstPage(true);
        if (localPayments2.size() > 0)
        {
          localHashMap = new HashMap();
          localHashMap.put("FIRSTPAGEINDEX", ((Payment)localPayments2.get(0)).getID());
          paramPagingContext.setMap(localHashMap);
        }
      }
      else
      {
        localHashMap = paramPagingContext.getMap();
        if ((localHashMap != null) && (localPayments2.size() > 0))
        {
          localObject = (String)localHashMap.get("FIRSTPAGEINDEX");
          if (localPayments2.getByID((String)localObject) != null) {
            paramPagingContext.setFirstPage(true);
          }
        }
      }
      if (localPayments2.size() == 0)
      {
        paramPagingContext.setFirstIndex(0L);
        paramPagingContext.setLastIndex(0L);
      }
      else
      {
        if (str1.equals("PAYDATE")) {
          localPayments2.setSortedBy("PAYDATE,REVERSE");
        }
        paramPagingContext.setFirstIndex(Integer.parseInt(((Payment)localPayments2.get(0)).getID()));
        paramPagingContext.setLastIndex(Integer.parseInt(((Payment)localPayments2.get(localPayments2.size() - 1)).getID()));
      }
      localPayments2.setPagingContext(paramPagingContext);
      return localPayments2;
    }
    throw new Exception("Error code: " + i);
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  private int jdMethod_for(HashMap paramHashMap)
    throws Exception
  {
    int i = 10;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("PAGESIZE");
      if (str != null) {
        try
        {
          i = Integer.parseInt(str);
        }
        catch (Exception localException) {}
      }
    }
    return i;
  }
  
  protected void filterPayments(Accounts paramAccounts, Payments paramPayments)
  {
    ListIterator localListIterator = paramPayments.listIterator();
    while (localListIterator.hasNext())
    {
      Payment localPayment = (Payment)localListIterator.next();
      String str = localPayment.getAccountID();
      Iterator localIterator = paramAccounts.iterator();
      int i = 0;
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        if ((str != null) && (str.equals(localAccount.getID()))) {
          i = 1;
        }
      }
      if (i == 0) {
        localListIterator.remove();
      }
    }
  }
  
  private void jdMethod_do(boolean paramBoolean)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramBoolean), getXMLReader(this, this.m_URL));
    }
    catch (Throwable localThrowable) {}
  }
  
  public Payments getPagedPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    Accounts localAccounts = (Accounts)paramHashMap.get("PAYMENT_ACCOUNTS");
    Payees localPayees = (Payees)paramHashMap.get("PAYMENT_PAYEES");
    String str = (String)paramHashMap.get("PAYMENT_STATUS");
    int i = 2;
    if (str.equals("PAYMENT_STATUS_COMPLETED")) {
      i = 5;
    }
    return a(localAccounts, localPayees, paramPagingContext, i, paramHashMap);
  }
  
  protected class a
    extends Service.InternalXMLHandler
  {
    public a(boolean paramBoolean)
    {
      super();
      this.getTransactions = paramBoolean;
    }
    
    public void startElement(String paramString)
    {
      if ((paramString.equals("PAYEES")) && (BillPay.this.payees != null)) {
        BillPay.this.payees.continueXMLParsing(getHandler());
      } else if ((paramString.equals("PAYMENTS")) && (BillPay.this.payments != null)) {
        BillPay.this.payments.continueXMLParsing(getHandler(), BillPay.this.accountlist, BillPay.this.payeelist);
      } else if ((paramString.equals("RECPAYMENTS")) && (BillPay.this.recpayments != null)) {
        BillPay.this.recpayments.continueXMLParsing(getHandler(), BillPay.this.accountlist, BillPay.this.payeelist);
      } else {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.BillPay
 * JD-Core Version:    0.7.0.1
 */