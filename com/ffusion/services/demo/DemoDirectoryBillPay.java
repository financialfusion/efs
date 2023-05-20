package com.ffusion.services.demo;

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
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.BillPay3;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.PagingContext;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DemoDirectoryBillPay
  implements BillPay3
{
  private Payees o;
  
  public int initialize(String paramString)
  {
    int i = 0;
    try
    {
      String str = new String(Service.getXMLString(this, paramString));
      if (str.length() == 0)
      {
        i = 7;
      }
      else
      {
        XMLTag localXMLTag1 = new XMLTag();
        localXMLTag1.build(str);
        if (localXMLTag1 != null)
        {
          this.o = new Payees();
          XMLTag localXMLTag2;
          if ((localXMLTag2 = localXMLTag1.getContainedTag("PAYEE_GROUP")) != null) {
            a(localXMLTag2, this.o);
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("Error initializing file" + localThrowable);
      i = 8;
    }
    return i;
  }
  
  public int getPayees(Payees paramPayees)
  {
    Iterator localIterator = this.o.iterator();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        Payee localPayee = (Payee)localIterator.next();
        paramPayees.add(localPayee);
      }
    }
    return 0;
  }
  
  public int addPayees(Payees paramPayees)
  {
    Iterator localIterator = paramPayees.iterator();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        Payee localPayee1 = (Payee)localIterator.next();
        if (this.o != null)
        {
          localPayee1.setID(String.valueOf((int)(Math.random() * 100000.0D)));
          Payee localPayee2 = this.o.create();
          localPayee2.set(localPayee1);
        }
      }
    }
    return 0;
  }
  
  public int modifyPayees(Payees paramPayees)
  {
    Iterator localIterator = paramPayees.iterator();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        Payee localPayee1 = (Payee)localIterator.next();
        if (this.o != null)
        {
          Payee localPayee2 = this.o.getByID(localPayee1.getID());
          if (localPayee2 != null) {
            localPayee2.set(localPayee1);
          }
        }
      }
    }
    return 0;
  }
  
  public int deletePayees(Payees paramPayees)
  {
    Iterator localIterator = paramPayees.iterator();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        Payee localPayee1 = (Payee)localIterator.next();
        if (this.o != null)
        {
          Payee localPayee2 = this.o.getByID(localPayee1.getID());
          if (localPayee2 != null) {
            this.o.remove(localPayee2);
          }
        }
      }
    }
    return 0;
  }
  
  public void setInitURL(String paramString) {}
  
  public int signOn(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public void setUserName(String paramString) {}
  
  public void setPassword(String paramString) {}
  
  public void setSettings(String paramString) {}
  
  public String getSettings()
  {
    return null;
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    return 0;
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees)
  {
    return 0;
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees, HashMap paramHashMap)
  {
    return 0;
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment)
  {
    return 0;
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment, HashMap paramHashMap)
  {
    return 0;
  }
  
  public int cancelPayments(Payments paramPayments)
  {
    return 0;
  }
  
  public int getPayments(Accounts paramAccounts, Payments paramPayments, RecPayments paramRecPayments, Payees paramPayees)
  {
    return 0;
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees)
  {
    return 0;
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees, HashMap paramHashMap)
  {
    return 0;
  }
  
  public int deleteRecPayment(RecPayment paramRecPayment)
  {
    return 0;
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment, HashMap paramHashMap)
  {
    return modifyRecPayment(paramAccount, paramRecPayment);
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    return 0;
  }
  
  public int skipRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    return 0;
  }
  
  public Payments getPagedPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  public Payments getNextPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  public Payments getPreviousPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  public Payments getPagedCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  public Payments getNextCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  public Payments getPreviousCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  public Payees getGlobalPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  private void a(XMLTag paramXMLTag, Payees paramPayees)
  {
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        if (localXMLTag.getTagName().equalsIgnoreCase("PAYEE"))
        {
          Payee localPayee = paramPayees.createNoAdd();
          if (a(localXMLTag, localPayee)) {
            paramPayees.add(localPayee);
          }
        }
      }
    }
  }
  
  private boolean a(XMLTag paramXMLTag, Payee paramPayee)
  {
    ArrayList localArrayList = null;
    boolean bool = false;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    bool = true;
    while (localIterator.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("PAYEE_ID")) {
        paramPayee.setID(str2);
      } else if (str1.equals("NICK_NAME")) {
        paramPayee.setNickName(str2);
      } else {
        paramPayee.setHashValue(str1, str2);
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.DemoDirectoryBillPay
 * JD-Core Version:    0.7.0.1
 */