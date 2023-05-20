package com.ffusion.services;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import java.util.HashMap;

public abstract interface BillPay
  extends SignOn
{
  public static final int ERROR_INVALID_ACCOUNT = 2000;
  public static final int ERROR_INSUFFICIENT_FUNDS = 2001;
  public static final int ERROR_AMOUNT_TOO_LARGE = 2002;
  public static final int ERROR_AMOUNT_TOO_SMALL = 2003;
  public static final int ERROR_DATE_TOO_SOON = 2004;
  public static final int ERROR_DATE_TOO_DISTANT = 2005;
  public static final int ERROR_ALREADY_COMMITTED = 2006;
  public static final int ERROR_TOO_MANY_PAYMENTS = 2007;
  public static final int ERROR_INVALID_PAYEE = 2008;
  public static final int ERROR_ADDRESS_UNDELIVERABLE = 2009;
  public static final int ERROR_INVALID_FREQUENCY = 2010;
  
  public abstract void setUserName(String paramString);
  
  public abstract void setPassword(String paramString);
  
  public abstract void setSettings(String paramString);
  
  public abstract String getSettings();
  
  public abstract int getAccounts(Accounts paramAccounts);
  
  public abstract int getPayees(Payees paramPayees);
  
  public abstract int addPayees(Payees paramPayees);
  
  public abstract int modifyPayees(Payees paramPayees);
  
  public abstract int deletePayees(Payees paramPayees);
  
  public abstract int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees);
  
  public abstract int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees, HashMap paramHashMap);
  
  public abstract int modifyPayment(Account paramAccount, Payment paramPayment);
  
  public abstract int modifyPayment(Account paramAccount, Payment paramPayment, HashMap paramHashMap);
  
  public abstract int cancelPayments(Payments paramPayments);
  
  public abstract int getPayments(Accounts paramAccounts, Payments paramPayments, RecPayments paramRecPayments, Payees paramPayees);
  
  public abstract int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees);
  
  public abstract int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees, HashMap paramHashMap);
  
  public abstract int deleteRecPayment(RecPayment paramRecPayment);
  
  public abstract int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment);
  
  public abstract int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment, HashMap paramHashMap);
  
  public abstract int skipRecPayment(Account paramAccount, RecPayment paramRecPayment);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPay
 * JD-Core Version:    0.7.0.1
 */