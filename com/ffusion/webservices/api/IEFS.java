package com.ffusion.webservices.api;

import com.ffusion.webservices.beans.Account;
import com.ffusion.webservices.beans.Payee;
import com.ffusion.webservices.beans.Payment;
import com.ffusion.webservices.beans.RecPayment;
import com.ffusion.webservices.beans.RecTransfer;
import com.ffusion.webservices.beans.Transaction;
import com.ffusion.webservices.beans.Transfer;

public abstract interface IEFS
{
  public abstract String authenticate(String paramString1, String paramString2, String paramString3)
    throws WSException;
  
  public abstract void invalidate(String paramString)
    throws WSException;
  
  public abstract RecTransfer deleteRecTransfer(String paramString1, String paramString2)
    throws WSException;
  
  public abstract Transfer deleteTransfer(String paramString1, String paramString2)
    throws WSException;
  
  public abstract Account[] getBankingAccounts(String paramString)
    throws WSException;
  
  public abstract Transaction[] getTransactionsByDate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws WSException;
  
  public abstract Transaction[] getTransactions(String paramString1, String paramString2, String paramString3)
    throws WSException;
  
  public abstract RecTransfer modifyRecTransfer(String paramString, RecTransfer paramRecTransfer)
    throws WSException;
  
  public abstract Transfer modifyTransfer(String paramString, Transfer paramTransfer)
    throws WSException;
  
  public abstract Transfer[] getTransfers(String paramString1, String paramString2, String paramString3)
    throws WSException;
  
  public abstract RecTransfer[] getRecTransfers(String paramString1, String paramString2, String paramString3)
    throws WSException;
  
  public abstract Transfer addTransfer(String paramString, Transfer paramTransfer)
    throws WSException;
  
  public abstract RecTransfer addRecTransfer(String paramString, RecTransfer paramRecTransfer)
    throws WSException;
  
  public abstract Account[] getBillpayAccounts(String paramString)
    throws WSException;
  
  public abstract Payee[] getPayees(String paramString)
    throws WSException;
  
  public abstract Payee[] getGlobalPayees(String paramString)
    throws WSException;
  
  public abstract Payee addPayee(String paramString, Payee paramPayee)
    throws WSException;
  
  public abstract Payee modifyPayee(String paramString, Payee paramPayee)
    throws WSException;
  
  public abstract Payee deletePayee(String paramString1, String paramString2)
    throws WSException;
  
  public abstract Payment addPayment(String paramString, Payment paramPayment)
    throws WSException;
  
  public abstract RecPayment addRecPayment(String paramString, RecPayment paramRecPayment)
    throws WSException;
  
  public abstract Payment modifyPayment(String paramString, Payment paramPayment)
    throws WSException;
  
  public abstract RecPayment modifyRecPayment(String paramString, RecPayment paramRecPayment)
    throws WSException;
  
  public abstract Payment deletePayment(String paramString1, String paramString2)
    throws WSException;
  
  public abstract RecPayment deleteRecPayment(String paramString1, String paramString2)
    throws WSException;
  
  public abstract Payment[] getPayments(String paramString1, String paramString2, String paramString3)
    throws WSException;
  
  public abstract RecPayment[] getRecPayments(String paramString1, String paramString2, String paramString3)
    throws WSException;
  
  public abstract Account[] getConsumerTransferFromAccounts(String paramString)
    throws WSException;
  
  public abstract Account[] getConsumerTransferToAccounts(String paramString)
    throws WSException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.api.IEFS
 * JD-Core Version:    0.7.0.1
 */