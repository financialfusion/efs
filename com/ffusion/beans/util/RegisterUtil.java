package com.ffusion.beans.util;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.enums.UserAssignedAmount;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;

public class RegisterUtil
{
  public static final String ADDED_TO_REGISTER = "AddedToRegister";
  public static final String TRUE = "true";
  public static final String FALSE = "false";
  public static final String TRUE_CHAR = "T";
  public static final String FALSE_CHAR = "F";
  
  public static boolean isRegisterEnabled(com.ffusion.beans.accounts.Account paramAccount)
  {
    try
    {
      String str = (String)paramAccount.get("REG_ENABLED");
      return ("true".equalsIgnoreCase(str)) || ("T".equalsIgnoreCase(str));
    }
    catch (NullPointerException localNullPointerException) {}
    return false;
  }
  
  public static boolean isRegisterDefault(com.ffusion.beans.accounts.Account paramAccount)
  {
    String str = (String)paramAccount.get("REG_DEFAULT");
    return ("true".equalsIgnoreCase(str)) || ("T".equalsIgnoreCase(str));
  }
  
  public static boolean isDebitAccount(int paramInt)
  {
    switch (paramInt)
    {
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 13: 
      return false;
    }
    return true;
  }
  
  public static boolean isTransactionCreditType(int paramInt)
  {
    if (RegisterTransaction.usingNewTranTypes())
    {
      switch (paramInt)
      {
      case 1: 
      case 4: 
      case 6: 
      case 8: 
      case 11: 
      case 18: 
      case 19: 
      case 21: 
      case 27: 
      case 29: 
        return true;
      }
      return false;
    }
    return paramInt == 3;
  }
  
  public static RegisterTransaction buildRegisterTransaction(Transfer paramTransfer, boolean paramBoolean)
  {
    RegisterTransaction localRegisterTransaction = new RegisterTransaction();
    localRegisterTransaction.set("SERVER_TID", paramTransfer.getID());
    localRegisterTransaction.set("RECSRVRTID", paramTransfer.getRecTransferID());
    localRegisterTransaction.setReferenceNumber(paramTransfer.getReferenceNumber());
    localRegisterTransaction.setType(16);
    localRegisterTransaction.setStatus(0);
    localRegisterTransaction.setDateIssued(paramTransfer.getDate());
    localRegisterTransaction.setMemo(paramTransfer.getMemo());
    localRegisterTransaction.put("FROMACCOUNT", paramTransfer.getFromAccount());
    localRegisterTransaction.put("TOACCOUNT", paramTransfer.getToAccount());
    Currency localCurrency;
    if (paramBoolean)
    {
      localCurrency = (Currency)paramTransfer.getAmountValue().clone();
      localRegisterTransaction.setAmount(localCurrency);
      localRegisterTransaction.setAmountsNegative();
      if (paramTransfer.getUserAssignedAmountFlag() == UserAssignedAmount.TO) {
        localRegisterTransaction.put("ESTIMATED_AMOUNT", String.valueOf(paramTransfer.getEstimatedAmountFlag()));
      }
      localRegisterTransaction.set("ACCOUNT", paramTransfer.getFromAccountID());
      localRegisterTransaction.setPayeeName(getTransferPayeeName("TransferFromText", XMLUtil.XMLDecode(paramTransfer.getToAccount().getNickName()), localRegisterTransaction.getLocale()));
    }
    else
    {
      localCurrency = (Currency)paramTransfer.getToAmountValue().clone();
      if (localCurrency == null) {
        localCurrency = (Currency)paramTransfer.getAmountValue().clone();
      }
      localRegisterTransaction.setAmount(localCurrency);
      if (paramTransfer.getUserAssignedAmountFlag() == UserAssignedAmount.FROM) {
        localRegisterTransaction.put("ESTIMATED_AMOUNT", String.valueOf(paramTransfer.getEstimatedAmountFlag()));
      }
      localRegisterTransaction.set("ACCOUNT", paramTransfer.getToAccountID());
      localRegisterTransaction.setPayeeName(getTransferPayeeName("TransferToText", XMLUtil.XMLDecode(paramTransfer.getFromAccount().getNickName()), localRegisterTransaction.getLocale()));
    }
    return localRegisterTransaction;
  }
  
  public static RegisterTransaction buildRegisterTransaction(Payment paramPayment)
  {
    RegisterTransaction localRegisterTransaction = new RegisterTransaction();
    Currency localCurrency = (Currency)paramPayment.getAmountValue().clone();
    localRegisterTransaction.set("SERVER_TID", paramPayment.getID());
    localRegisterTransaction.set("RECSRVRTID", paramPayment.getRecPaymentID());
    localRegisterTransaction.setReferenceNumber(paramPayment.getReferenceNumber());
    localRegisterTransaction.setType(25);
    localRegisterTransaction.setStatus(0);
    localRegisterTransaction.setDateIssued(paramPayment.getPayDate());
    localRegisterTransaction.setPayeeName(XMLUtil.XMLDecode(paramPayment.getPayeeNickName()));
    localRegisterTransaction.setPayeeName(trimLength(XMLUtil.XMLDecode(paramPayment.getPayeeNickName()), 40));
    localRegisterTransaction.setAmount(localCurrency);
    localRegisterTransaction.setMemo(paramPayment.getMemo());
    localRegisterTransaction.set("ACCOUNT", paramPayment.getAccountID());
    String str = (String)paramPayment.get("REGISTER_CATEGORY_ID");
    if (str != null) {
      localRegisterTransaction.setRegisterCategoryId(Integer.parseInt(str));
    }
    localRegisterTransaction.setAmountsNegative();
    return localRegisterTransaction;
  }
  
  public static RegisterTransactions buildRegisterTransactions(Payments paramPayments)
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    ListIterator localListIterator = paramPayments.listIterator();
    while (localListIterator.hasNext()) {
      localRegisterTransactions.add(buildRegisterTransaction((Payment)localListIterator.next()));
    }
    return localRegisterTransactions;
  }
  
  public static RegisterTransactions buildRegisterTransactions(com.ffusion.beans.banking.Transactions paramTransactions, String paramString)
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    Iterator localIterator = paramTransactions.iterator();
    while (localIterator.hasNext())
    {
      com.ffusion.beans.banking.Transaction localTransaction = (com.ffusion.beans.banking.Transaction)localIterator.next();
      RegisterTransaction localRegisterTransaction = new RegisterTransaction(localTransaction);
      localRegisterTransaction.setStatus(1);
      localRegisterTransaction.setDateIssued((DateTime)localTransaction.getDateValue().clone());
      localRegisterTransaction.setAmount(localTransaction.getAmountValue());
      localRegisterTransaction.setPayeeName(localTransaction.getDescription());
      localRegisterTransaction.setPayeeName(trimLength(localTransaction.getDescription(), 40));
      localRegisterTransaction.set("ACCOUNT", paramString);
      localRegisterTransaction.put("CURRENCY_CODE", localTransaction.getAmountValue().getCurrencyCode());
      localRegisterTransactions.add(localRegisterTransaction);
    }
    return localRegisterTransactions;
  }
  
  public static RegisterTransactions buildRegisterTransactions(com.ffusion.beans.aggregation.Transactions paramTransactions, com.ffusion.beans.aggregation.Account paramAccount)
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    Iterator localIterator = paramTransactions.iterator();
    while (localIterator.hasNext())
    {
      com.ffusion.beans.aggregation.Transaction localTransaction = (com.ffusion.beans.aggregation.Transaction)localIterator.next();
      RegisterTransaction localRegisterTransaction = new RegisterTransaction(localTransaction.getLocale());
      a(localTransaction, localRegisterTransaction);
      localRegisterTransaction.setStatus(1);
      localRegisterTransaction.setDateIssued(localTransaction.getDateValue());
      localRegisterTransaction.setAmount(localTransaction.getAmountValue());
      localRegisterTransaction.setPayeeName(localTransaction.getDescription());
      localRegisterTransaction.setPayeeName(trimLength(localTransaction.getDescription(), 40));
      localRegisterTransaction.set("ACCOUNT", paramAccount.getID());
      localRegisterTransactions.add(localRegisterTransaction);
    }
    return localRegisterTransactions;
  }
  
  public static String getTransferPayeeName(String paramString1, String paramString2, Locale paramLocale)
  {
    String str = ResourceUtil.getString(paramString1, "com.ffusion.beans.register.resources", paramLocale) + " " + paramString2;
    return trimLength(str, 40);
  }
  
  public static String trimLength(String paramString, int paramInt)
  {
    if (paramString == null) {
      return null;
    }
    if (paramString.length() > paramInt) {
      return paramString.substring(0, paramInt);
    }
    return paramString;
  }
  
  public static DateTime getLatestRetrievalDate(Accounts paramAccounts)
  {
    Object localObject = null;
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)localIterator.next();
      DateTime localDateTime = (DateTime)localAccount.get("REG_RETRIEVAL_DATE");
      if ((localDateTime != null) && ((localObject == null) || (localDateTime.after(localObject)))) {
        localObject = localDateTime;
      }
    }
    return localObject;
  }
  
  public static boolean hasValue(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    return (!(paramObject instanceof String)) || (!paramObject.equals(""));
  }
  
  public static int convertToInt(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public static String getBooleanString(String paramString)
  {
    if (paramString == null) {
      return "false";
    }
    if (paramString.equalsIgnoreCase("T")) {
      return "true";
    }
    return "false";
  }
  
  public static boolean getBoolean(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    return paramString.equalsIgnoreCase("T");
  }
  
  public static String getTFChar(String paramString)
  {
    if (paramString == null) {
      return "F";
    }
    if (paramString.equalsIgnoreCase("true")) {
      return "T";
    }
    return "F";
  }
  
  public static String getTFChar(boolean paramBoolean)
  {
    if (paramBoolean == true) {
      return "T";
    }
    return "F";
  }
  
  public static boolean sameSign(double paramDouble1, double paramDouble2)
  {
    return ((paramDouble1 > 0.0D) && (paramDouble2 > 0.0D)) || ((paramDouble1 < 0.0D) && (paramDouble2 < 0.0D));
  }
  
  public static boolean sameSign(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    BigDecimal localBigDecimal = new BigDecimal("0.0");
    return paramBigDecimal1.compareTo(localBigDecimal) == paramBigDecimal2.compareTo(localBigDecimal);
  }
  
  public static boolean isReconciled(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
    case 1: 
      return false;
    case 2: 
    case 3: 
    case 4: 
      return true;
    }
    return false;
  }
  
  private static void a(com.ffusion.beans.aggregation.Transaction paramTransaction, com.ffusion.beans.banking.Transaction paramTransaction1)
  {
    if ((paramTransaction1 == null) || (paramTransaction == null)) {
      return;
    }
    paramTransaction1.setID(paramTransaction.getID());
    paramTransaction1.setType(paramTransaction.getTypeValue());
    paramTransaction1.setCategory(paramTransaction.getCategory());
    paramTransaction1.setDescription(paramTransaction.getDescription());
    paramTransaction1.setReferenceNumber(paramTransaction.getReferenceNumber());
    paramTransaction1.setMemo(paramTransaction.getMemo());
    paramTransaction1.setDate(paramTransaction.getDateValue());
    paramTransaction1.setAmount(paramTransaction.getAmountValue());
    paramTransaction1.setTrackingID(paramTransaction.getTrackingID());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.RegisterUtil
 * JD-Core Version:    0.7.0.1
 */