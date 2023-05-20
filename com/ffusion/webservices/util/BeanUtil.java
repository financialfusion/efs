package com.ffusion.webservices.util;

import java.io.PrintStream;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BeanUtil
{
  private static boolean a = false;
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println("beansHaveTrackingID =" + a);
  }
  
  public static com.ffusion.webservices.beans.Account TransformAccount(com.ffusion.beans.accounts.Account paramAccount)
  {
    com.ffusion.webservices.beans.Account localAccount = new com.ffusion.webservices.beans.Account();
    localAccount.setId(paramAccount.getID());
    localAccount.setNumber(paramAccount.getNumber());
    localAccount.setBankID(paramAccount.getBankID());
    localAccount.setNickName(paramAccount.getNickName());
    localAccount.setType(paramAccount.getTypeValue());
    localAccount.setStatus(paramAccount.getStatus());
    localAccount.setCurrentBalance(TransformBalance(paramAccount.getCurrentBalance()));
    localAccount.setAvailableBalance(TransformBalance(paramAccount.getAvailableBalance()));
    localAccount.setClosingBalance(TransformBalance(paramAccount.getClosingBalance()));
    localAccount.setBankName(paramAccount.getBankName());
    localAccount.setCoreAccount(paramAccount.getCoreAccount());
    localAccount.setCurrencyCode(paramAccount.getCurrencyCode());
    localAccount.setRoutingNumber(paramAccount.getRoutingNum());
    return localAccount;
  }
  
  public static com.ffusion.webservices.beans.Balance TransformBalance(com.ffusion.beans.Balance paramBalance)
  {
    if (paramBalance != null)
    {
      com.ffusion.webservices.beans.Balance localBalance = new com.ffusion.webservices.beans.Balance();
      localBalance.setAmount(TransformCurrency(paramBalance.getAmountValue()));
      localBalance.setDate(paramBalance.getDateValue().getTime());
      return localBalance;
    }
    return null;
  }
  
  public static com.ffusion.webservices.beans.Currency TransformCurrency(com.ffusion.beans.Currency paramCurrency)
  {
    com.ffusion.webservices.beans.Currency localCurrency = new com.ffusion.webservices.beans.Currency();
    if (paramCurrency != null) {
      localCurrency.setAmount(paramCurrency.getAmountValue());
    }
    return localCurrency;
  }
  
  public static com.ffusion.webservices.beans.Transfer TransformTransfer(com.ffusion.beans.banking.Transfer paramTransfer)
  {
    com.ffusion.webservices.beans.Transfer localTransfer = new com.ffusion.webservices.beans.Transfer();
    TransformTransfer_(paramTransfer, localTransfer);
    return localTransfer;
  }
  
  public static void TransformTransfer_(com.ffusion.beans.banking.Transfer paramTransfer, com.ffusion.webservices.beans.Transfer paramTransfer1)
  {
    paramTransfer1.setId(paramTransfer.getID());
    paramTransfer1.setRecid(paramTransfer.getRecTransferID());
    paramTransfer1.setAmount(TransformCurrency(paramTransfer.getAmountValue()));
    paramTransfer1.setDate(paramTransfer.getDateValue().getTime());
    paramTransfer1.setFromAccountID(paramTransfer.getFromAccountID());
    paramTransfer1.setToAccountID(paramTransfer.getToAccountID());
    paramTransfer1.setReferenceNumber(paramTransfer.getReferenceNumber());
    paramTransfer1.setMemo(paramTransfer.getMemo());
    paramTransfer1.setStatus(paramTransfer.getStatus());
    paramTransfer1.setError(paramTransfer.getErrorValue());
    paramTransfer1.setTransactionID(paramTransfer.getTransactionID());
    if (a) {
      paramTransfer1.setTrackingID(paramTransfer.getTrackingID());
    }
  }
  
  public static com.ffusion.webservices.beans.RecTransfer TransformRecTransfer(com.ffusion.beans.banking.RecTransfer paramRecTransfer)
  {
    com.ffusion.webservices.beans.RecTransfer localRecTransfer = new com.ffusion.webservices.beans.RecTransfer();
    TransformTransfer_(paramRecTransfer, localRecTransfer);
    localRecTransfer.setNumberTransfers(paramRecTransfer.getNumberTransfersValue());
    localRecTransfer.setFrequency(paramRecTransfer.getFrequencyValue());
    return localRecTransfer;
  }
  
  public static com.ffusion.webservices.beans.Transaction TransformTransaction(com.ffusion.beans.banking.Transaction paramTransaction)
  {
    com.ffusion.webservices.beans.Transaction localTransaction = new com.ffusion.webservices.beans.Transaction();
    localTransaction.setId(paramTransaction.getID());
    localTransaction.setType(paramTransaction.getTypeValue());
    localTransaction.setCategory(paramTransaction.getCategory());
    localTransaction.setDescription(paramTransaction.getDescription());
    localTransaction.setReferenceNumber(paramTransaction.getReferenceNumber());
    localTransaction.setMemo(paramTransaction.getMemo());
    localTransaction.setDate(paramTransaction.getDateValue().getTime());
    localTransaction.setAmount(TransformCurrency(paramTransaction.getAmountValue()));
    localTransaction.setRunningBalance(TransformCurrency(paramTransaction.getRunningBalanceValue()));
    if (a) {
      localTransaction.setTrackingID(paramTransaction.getTrackingID());
    }
    return localTransaction;
  }
  
  public static void UpdateTransfer(com.ffusion.beans.banking.Transfer paramTransfer, com.ffusion.webservices.beans.Transfer paramTransfer1)
  {
    UpdateTransfer_(paramTransfer, paramTransfer1);
  }
  
  public static void UpdateTransfer_(com.ffusion.beans.banking.Transfer paramTransfer, com.ffusion.webservices.beans.Transfer paramTransfer1)
  {
    paramTransfer.setID(paramTransfer1.getId());
    paramTransfer.setRecTransferID(paramTransfer1.getRecid());
    paramTransfer.setAmount(new com.ffusion.beans.Currency(paramTransfer1.getAmount().getAmount(), Locale.US));
    paramTransfer.setDate(new com.ffusion.beans.DateTime(paramTransfer1.getDate(), Locale.US));
    paramTransfer.setFromAccountID(paramTransfer1.getFromAccountID());
    paramTransfer.setToAccountID(paramTransfer1.getToAccountID());
    paramTransfer.setReferenceNumber(paramTransfer1.getReferenceNumber());
    paramTransfer.setMemo(paramTransfer1.getMemo());
    paramTransfer.setStatus(paramTransfer1.getStatus());
    paramTransfer.setError(paramTransfer1.getError());
    paramTransfer.setTransactionID(paramTransfer1.getTransactionID());
    if (a) {
      paramTransfer.setTrackingID(paramTransfer1.getTrackingID());
    }
  }
  
  public static void UpdateRecTransfer(com.ffusion.beans.banking.RecTransfer paramRecTransfer, com.ffusion.webservices.beans.RecTransfer paramRecTransfer1)
  {
    UpdateTransfer_(paramRecTransfer, paramRecTransfer1);
    paramRecTransfer.setNumberTransfers(paramRecTransfer1.getNumberTransfers());
    paramRecTransfer.setFrequency(paramRecTransfer1.getFrequency());
  }
  
  public static com.ffusion.webservices.beans.Payee TransformPayee(com.ffusion.beans.billpay.Payee paramPayee)
  {
    com.ffusion.webservices.beans.Payee localPayee = new com.ffusion.webservices.beans.Payee();
    localPayee.setName(paramPayee.getName());
    localPayee.setNickName(paramPayee.getNickName());
    localPayee.setUserAccountNumber(paramPayee.getUserAccountNumber());
    localPayee.setId(paramPayee.getID());
    localPayee.setHostID(paramPayee.getHostID());
    localPayee.setTransactionID(paramPayee.getTransactionID());
    localPayee.setStatus(paramPayee.getStatus());
    localPayee.setDaysToPay(paramPayee.getDaysToPayValue());
    localPayee.setError(paramPayee.getErrorValue());
    if (a) {
      localPayee.setTrackingID(paramPayee.getTrackingID());
    }
    localPayee.setStreet(paramPayee.getStreet());
    localPayee.setStreet2(paramPayee.getStreet2());
    localPayee.setCity(paramPayee.getCity());
    localPayee.setState(paramPayee.getState());
    localPayee.setCountry(paramPayee.getCountry());
    localPayee.setEmail(paramPayee.getEmail());
    localPayee.setPhone(paramPayee.getPhone());
    localPayee.setPhone2(paramPayee.getPhone2());
    localPayee.setZipCode(paramPayee.getZipCode());
    return localPayee;
  }
  
  public static com.ffusion.webservices.beans.Payment TransformPayment(com.ffusion.beans.billpay.Payment paramPayment)
  {
    com.ffusion.webservices.beans.Payment localPayment = new com.ffusion.webservices.beans.Payment();
    TransformPayment_(paramPayment, localPayment);
    return localPayment;
  }
  
  public static com.ffusion.webservices.beans.RecPayment TransformRecPayment(com.ffusion.beans.billpay.RecPayment paramRecPayment)
  {
    com.ffusion.webservices.beans.RecPayment localRecPayment = new com.ffusion.webservices.beans.RecPayment();
    TransformPayment_(paramRecPayment, localRecPayment);
    localRecPayment.setNumberPayments(paramRecPayment.getNumberPaymentsValue());
    localRecPayment.setFrequency(paramRecPayment.getFrequencyValue());
    return localRecPayment;
  }
  
  public static void TransformPayment_(com.ffusion.beans.billpay.Payment paramPayment, com.ffusion.webservices.beans.Payment paramPayment1)
  {
    paramPayment1.setId(paramPayment.getID());
    paramPayment1.setRecid(paramPayment.getRecPaymentID());
    if (paramPayment.getPayee() != null) {
      paramPayment1.setPayeeID(paramPayment.getPayee().getID());
    }
    paramPayment1.setPayeeName(paramPayment.getPayeeName());
    paramPayment1.setAmount(TransformCurrency(paramPayment.getAmountValue()));
    paramPayment1.setPayDate(paramPayment.getPayDateValue().getTime());
    if (paramPayment.getDeliverByDateValue() != null) {
      paramPayment1.setDeliverByDate(paramPayment.getDeliverByDateValue().getTime());
    }
    paramPayment1.setAccountID(paramPayment.getAccountID());
    paramPayment1.setConfirmationNum(paramPayment.getConfirmationNum());
    paramPayment1.setReferenceNumber(paramPayment.getReferenceNumber());
    paramPayment1.setMemo(paramPayment.getMemo());
    paramPayment1.setStatus(paramPayment.getStatus());
    paramPayment1.setPaymentType(paramPayment.getPaymentType());
    paramPayment1.setFIID(paramPayment.getFIID());
    paramPayment1.setCustomerID(paramPayment.getCustomerID());
    paramPayment1.setError(paramPayment.getErrorValue());
    paramPayment1.setTransactionID(paramPayment.getTransactionID());
    if (a) {
      paramPayment1.setTrackingID(paramPayment.getTrackingID());
    }
  }
  
  public static void UpdatePayment(com.ffusion.beans.billpay.Payment paramPayment, com.ffusion.webservices.beans.Payment paramPayment1)
  {
    paramPayment.setID(paramPayment1.getId());
    paramPayment.setRecPaymentID(paramPayment1.getRecid());
    paramPayment.setPayeeName(paramPayment1.getPayeeName());
    paramPayment.setAmount(paramPayment1.getAmount().getAmount());
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    localGregorianCalendar.setTime(paramPayment1.getPayDate());
    paramPayment.setPayDate(localGregorianCalendar);
    if (paramPayment1.getDeliverByDate() != null)
    {
      localGregorianCalendar.setTime(paramPayment1.getDeliverByDate());
      paramPayment.setDeliverByDate(localGregorianCalendar);
    }
    paramPayment.setAccountID(paramPayment1.getAccountID());
    paramPayment.setConfirmationNum(paramPayment1.getConfirmationNum());
    paramPayment.setReferenceNumber(paramPayment1.getReferenceNumber());
    paramPayment.setMemo(paramPayment1.getMemo());
    paramPayment.setStatus(paramPayment1.getStatus());
    paramPayment.setPaymentType(paramPayment1.getPaymentType());
    paramPayment.setFIID(paramPayment1.getFIID());
    paramPayment.setCustomerID(paramPayment1.getCustomerID());
    paramPayment.setError(paramPayment1.getError());
    paramPayment.setTransactionID(paramPayment1.getTransactionID());
    if (a) {
      paramPayment.setTrackingID(paramPayment1.getTrackingID());
    }
  }
  
  public static void UpdateRecPayment(com.ffusion.beans.billpay.RecPayment paramRecPayment, com.ffusion.webservices.beans.RecPayment paramRecPayment1)
  {
    UpdatePayment(paramRecPayment, paramRecPayment1);
    paramRecPayment.setNumberPayments(paramRecPayment1.getNumberPayments());
    paramRecPayment.setFrequency(paramRecPayment1.getFrequency());
  }
  
  public static void UpdatePayee(com.ffusion.beans.billpay.Payee paramPayee, com.ffusion.webservices.beans.Payee paramPayee1)
  {
    paramPayee.setName(paramPayee1.getName());
    paramPayee.setNickName(paramPayee1.getNickName());
    paramPayee.setUserAccountNumber(paramPayee1.getUserAccountNumber());
    paramPayee.setID(paramPayee1.getId());
    paramPayee.setHostID(paramPayee1.getHostID());
    paramPayee.setTransactionID(paramPayee1.getTransactionID());
    paramPayee.setStatus(paramPayee1.getStatus());
    paramPayee.setDaysToPay(paramPayee1.getDaysToPay());
    paramPayee.setError(paramPayee1.getError());
    if (a) {
      paramPayee.setTrackingID(paramPayee1.getTrackingID());
    }
    paramPayee.setStreet(paramPayee1.getStreet());
    paramPayee.setStreet2(paramPayee1.getStreet2());
    paramPayee.setCity(paramPayee1.getCity());
    paramPayee.setState(paramPayee1.getState());
    paramPayee.setCountry(paramPayee1.getCountry());
    paramPayee.setEmail(paramPayee1.getEmail());
    paramPayee.setPhone(paramPayee1.getPhone());
    paramPayee.setPhone2(paramPayee1.getPhone2());
    paramPayee.setZipCode(paramPayee1.getZipCode());
  }
  
  static
  {
    try
    {
      if (com.ffusion.webservices.beans.Account.class.getMethod("getTrackingID", null) != null) {
        a = true;
      }
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (SecurityException localSecurityException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.util.BeanUtil
 * JD-Core Version:    0.7.0.1
 */