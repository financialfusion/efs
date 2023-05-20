package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.util.UniqueIDGenerator;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.Qsort;
import com.ffusion.util.XMLHandler;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class AcctTransactionRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String ACCT_RPT_TYPE = "ACCT_RPT_TYPE";
  public static final String ACCT_TRANSACTION_RPT = "ACCT_TRANSACTION_RPT";
  public static final String TRANSACTION_LIST = "TRANSACTION_LIST";
  public static final String TRANSACTION_DATA = "TRANSACTION_DATA";
  public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public static final String BANK_ID = "BANK_ID";
  public static final String ROUTING_NUMBER = "ROUTING_NUMBER";
  public static final String ACCOUNT_ID = "ACCOUNT_ID";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private Accounts Sj = null;
  private Currency Si = null;
  private ArrayList Sl = null;
  private ArrayList Sk = null;
  
  public AcctTransactionRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof AcctTransactionRpt))) {
      return false;
    }
    AcctTransactionRpt localAcctTransactionRpt = (AcctTransactionRpt)paramObject;
    Accounts localAccounts = localAcctTransactionRpt.getAccounts();
    if (this.Sj == null) {
      return localAccounts == null;
    }
    return this.Sj.equals(localAccounts);
  }
  
  public void setAccounts(Accounts paramAccounts)
  {
    this.Sj = paramAccounts;
  }
  
  public Accounts getAccounts()
  {
    return this.Sj;
  }
  
  public void setTransactionsList(ArrayList paramArrayList)
  {
    this.Sl = paramArrayList;
  }
  
  public ArrayList getTransactionsList()
  {
    return this.Sl;
  }
  
  public void setAccountsList(ArrayList paramArrayList)
  {
    this.Sk = paramArrayList;
  }
  
  public ArrayList getAccountsList()
  {
    return this.Sk;
  }
  
  public ArrayList getReportOutput()
  {
    ArrayList localArrayList = null;
    if (this.Sl != null) {
      for (int i = 0; i < this.Sl.size(); i++)
      {
        if (localArrayList == null) {
          localArrayList = new ArrayList();
        }
        localArrayList.add(this.Sl.get(i));
        localArrayList.add(this.Sk.get(i));
      }
    }
    return localArrayList;
  }
  
  private void E()
  {
    if (this.Sl == null) {
      return;
    }
    this.Si = new Currency();
    this.Si.setAmount(new BigDecimal(0.0D));
    Locale localLocale = null;
    for (int i = 0; i < this.Sl.size(); i++)
    {
      Transaction localTransaction = (Transaction)this.Sl.get(i);
      Currency localCurrency = localTransaction.getAmountValue();
      if (localCurrency != null)
      {
        if (localLocale == null) {
          localLocale = localCurrency.getLocale();
        }
        this.Si.addAmount(localCurrency);
      }
    }
    this.Si.setLocale(localLocale);
  }
  
  public Currency getAllTransactionsTotal()
  {
    if (this.Si == null) {
      E();
    }
    return this.Si;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',', paramHashMap);
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t', paramHashMap);
    } else if (paramString.equals("BAI2")) {
      localStringBuffer = jdMethod_for(paramHashMap);
    }
    return localStringBuffer;
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, int paramInt, Locale paramLocale, String paramString)
  {
    paramStringBuffer.append(ReportConsts.getColumnName(paramInt, paramLocale));
    paramStringBuffer.append(paramString);
  }
  
  protected StringBuffer getDelimitedReport(char paramChar, HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("REPORTTYPE");
    ReportCriteria localReportCriteria = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
    String str2 = null;
    if (localReportCriteria != null)
    {
      localObject1 = localReportCriteria.getSortCriteria();
      ((ReportSortCriteria)localObject1).setSortedBy("ORDINAL");
      if (((ReportSortCriteria)localObject1).size() != 0)
      {
        localObject2 = (ReportSortCriterion)((ReportSortCriteria)localObject1).get(0);
        str2 = ((ReportSortCriterion)localObject2).getName();
      }
    }
    if (str2 == null) {
      str2 = "ProcessingDate";
    }
    if (str1 == null) {
      str1 = "DepositDetail";
    }
    Object localObject1 = String.valueOf(paramChar);
    Object localObject2 = new StringBuffer();
    if (!str2.equals("AccountNumber"))
    {
      boolean bool = jdMethod_for((StringBuffer)localObject2, str1, str2, (String)localObject1);
      if (!bool) {
        return localObject2;
      }
    }
    if (this.Sj != null)
    {
      Iterator localIterator = this.Sj.iterator();
      Account localAccount = null;
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        if (str2.equals("AccountNumber"))
        {
          if (localAccount.getNickName() != null) {
            ((StringBuffer)localObject2).append(localAccount.getID() + " - " + localAccount.getNickName());
          } else {
            ((StringBuffer)localObject2).append(localAccount.getID());
          }
          ((StringBuffer)localObject2).append(_lineSeparator);
          jdMethod_for((StringBuffer)localObject2, str1, str2, (String)localObject1);
        }
        Transactions localTransactions = localAccount.getTransactions();
        if (str1.equals("TransactionDetail"))
        {
          jdMethod_try((StringBuffer)localObject2, localTransactions, localAccount, str2, (String)localObject1);
        }
        else if (str1.equals("CreditReport"))
        {
          jdMethod_for((StringBuffer)localObject2, localTransactions, localAccount, str2, (String)localObject1);
        }
        else if (str1.equals("DebitReport"))
        {
          jdMethod_int((StringBuffer)localObject2, localTransactions, localAccount, str2, (String)localObject1);
        }
        else if (str1.equals("AccountHistory"))
        {
          int i = Account.getAccountSystemTypeFromGroup(localAccount.getAccountGroup());
          if (i == 1) {
            jdMethod_for((StringBuffer)localObject2, localTransactions, localAccount, (String)localObject1);
          } else if (i == 3) {
            jdMethod_try((StringBuffer)localObject2, localTransactions, localAccount, (String)localObject1);
          } else if (i == 4) {
            jdMethod_int((StringBuffer)localObject2, localTransactions, localAccount, (String)localObject1);
          } else if (i == 2) {
            jdMethod_new((StringBuffer)localObject2, localTransactions, localAccount, (String)localObject1);
          }
        }
        else
        {
          jdMethod_new((StringBuffer)localObject2, localTransactions, localAccount, str2, (String)localObject1);
        }
      }
    }
    return localObject2;
  }
  
  private boolean jdMethod_for(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("TransactionDetail"))
    {
      jdMethod_char(paramStringBuffer, paramString2, paramString3);
    }
    else if (paramString1.equals("CreditReport"))
    {
      jdMethod_byte(paramStringBuffer, paramString2, paramString3);
    }
    else if (paramString1.equals("DebitReport"))
    {
      jdMethod_case(paramStringBuffer, paramString2, paramString3);
    }
    else if (paramString1.equals("AccountHistory"))
    {
      Iterator localIterator = this.Sj.iterator();
      for (Account localAccount = null; localIterator.hasNext(); localAccount = (Account)localIterator.next()) {}
      if (localAccount == null) {
        return false;
      }
      int i = Account.getAccountSystemTypeFromGroup(localAccount.getAccountGroup());
      if (i == 1) {
        jdMethod_for(paramStringBuffer, paramString3);
      } else if (i == 3) {
        jdMethod_new(paramStringBuffer, paramString3);
      } else if (i == 4) {
        jdMethod_try(paramStringBuffer, paramString3);
      } else if (i == 2) {
        jdMethod_int(paramStringBuffer, paramString3);
      }
    }
    else
    {
      jdMethod_goto(paramStringBuffer, paramString2, paramString3);
    }
    return true;
  }
  
  private void jdMethod_char(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    jdMethod_for(paramStringBuffer, 266, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 243, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 267, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 246, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 264, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 265, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 259, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 245, this.locale, paramString2);
    if (!paramString1.equals("AccountNumber")) {
      jdMethod_for(paramStringBuffer, 240, this.locale, paramString2);
    }
    jdMethod_for(paramStringBuffer, 249, this.locale, "");
    paramStringBuffer.append(_lineSeparator);
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, Transactions paramTransactions, Account paramAccount, String paramString1, String paramString2)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      jdMethod_try(paramStringBuffer, localTransaction.getProcessingDate(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getType(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getSubType(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getReferenceNumber(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getBankReferenceNumber(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getCustomerReferenceNumber(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getOrigUser(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getDescription(), paramString2);
      if (!paramString1.equals("AccountNumber")) {
        if (paramAccount.getNickName() != null) {
          jdMethod_else(paramStringBuffer, paramAccount.getID() + " - " + paramAccount.getNickName(), paramString2);
        } else {
          jdMethod_else(paramStringBuffer, paramAccount.getID(), paramString2);
        }
      }
      jdMethod_try(paramStringBuffer, localTransaction.getAmountValue(), paramString2);
      paramStringBuffer.append("Completed");
      paramStringBuffer.append(_lineSeparator);
    }
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, String paramString)
  {
    jdMethod_for(paramStringBuffer, 266, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 243, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 245, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 246, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 271, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 270, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 272, this.locale, "");
    paramStringBuffer.append(_lineSeparator);
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, Transactions paramTransactions, Account paramAccount, String paramString)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      jdMethod_try(paramStringBuffer, localTransaction.getProcessingDate(), paramString);
      jdMethod_else(paramStringBuffer, localTransaction.getType(), paramString);
      jdMethod_else(paramStringBuffer, localTransaction.getDescription(), paramString);
      jdMethod_else(paramStringBuffer, localTransaction.getReferenceNumber(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getDebitValueAbsolute(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getCreditValue(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getRunningBalanceValue(), "");
      paramStringBuffer.append(_lineSeparator);
    }
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, String paramString)
  {
    jdMethod_for(paramStringBuffer, 266, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 245, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 276, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 253, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 277, this.locale, "");
    paramStringBuffer.append(_lineSeparator);
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, Transactions paramTransactions, Account paramAccount, String paramString)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      jdMethod_try(paramStringBuffer, localTransaction.getProcessingDate(), paramString);
      jdMethod_else(paramStringBuffer, localTransaction.getDescription(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getAmountValue(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getDueDate(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getRunningBalanceValue(), "");
      paramStringBuffer.append(_lineSeparator);
    }
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, String paramString)
  {
    jdMethod_for(paramStringBuffer, 248, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 245, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 274, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 275, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 272, this.locale, "");
    paramStringBuffer.append(_lineSeparator);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, Transactions paramTransactions, Account paramAccount, String paramString)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      jdMethod_else(paramStringBuffer, localTransaction.getDate(), paramString);
      jdMethod_else(paramStringBuffer, localTransaction.getDescription(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getDebitValueAbsolute(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getCreditValue(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getRunningBalanceValue(), "");
      paramStringBuffer.append(_lineSeparator);
    }
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, String paramString)
  {
    jdMethod_for(paramStringBuffer, 266, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 243, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 245, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 270, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 271, this.locale, paramString);
    jdMethod_for(paramStringBuffer, 273, this.locale, "");
    paramStringBuffer.append(_lineSeparator);
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, Transactions paramTransactions, Account paramAccount, String paramString)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      jdMethod_try(paramStringBuffer, localTransaction.getProcessingDate(), paramString);
      jdMethod_else(paramStringBuffer, localTransaction.getType(), paramString);
      jdMethod_else(paramStringBuffer, localTransaction.getDescription(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getCreditValue(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getDebitValueAbsolute(), paramString);
      jdMethod_try(paramStringBuffer, localTransaction.getRunningBalanceValue(), "");
      paramStringBuffer.append(_lineSeparator);
    }
  }
  
  private void jdMethod_goto(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    jdMethod_for(paramStringBuffer, 266, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 245, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 258, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 259, this.locale, paramString2);
    if (!paramString1.equals("AccountNumber")) {
      jdMethod_for(paramStringBuffer, 172, this.locale, paramString2);
    }
    jdMethod_for(paramStringBuffer, 243, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 267, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 246, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 264, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 265, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 249, this.locale, "");
    paramStringBuffer.append(_lineSeparator);
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, Transactions paramTransactions, Account paramAccount, String paramString1, String paramString2)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      jdMethod_try(paramStringBuffer, localTransaction.getProcessingDate(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getDescription(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getPayorNum(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getOrigUser(), paramString2);
      if (!paramString1.equals("AccountNumber")) {
        if (paramAccount.getNickName() != null) {
          jdMethod_else(paramStringBuffer, paramAccount.getID() + " - " + paramAccount.getNickName(), paramString2);
        } else {
          jdMethod_else(paramStringBuffer, paramAccount.getID(), paramString2);
        }
      }
      jdMethod_else(paramStringBuffer, localTransaction.getType(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getSubType(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getReferenceNumber(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getBankReferenceNumber(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getCustomerReferenceNumber(), paramString2);
      jdMethod_try(paramStringBuffer, localTransaction.getAmountValue(), "");
      paramStringBuffer.append(_lineSeparator);
    }
  }
  
  private void jdMethod_byte(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    jdMethod_for(paramStringBuffer, 266, this.locale, paramString2);
    if (!paramString1.equals("AccountNumber")) {
      jdMethod_for(paramStringBuffer, 172, this.locale, paramString2);
    }
    jdMethod_for(paramStringBuffer, 279, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 245, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 260, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 243, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 267, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 264, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 265, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 249, this.locale, "");
    paramStringBuffer.append(_lineSeparator);
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, Transactions paramTransactions, Account paramAccount, String paramString1, String paramString2)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      jdMethod_try(paramStringBuffer, localTransaction.getProcessingDate(), paramString2);
      if (!paramString1.equals("AccountNumber")) {
        if (paramAccount.getNickName() != null) {
          jdMethod_else(paramStringBuffer, paramAccount.getID() + " - " + paramAccount.getNickName(), paramString2);
        } else {
          jdMethod_else(paramStringBuffer, paramAccount.getID(), paramString2);
        }
      }
      jdMethod_else(paramStringBuffer, localTransaction.getPayeePayor(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getDescription(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getPONum(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getType(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getSubType(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getBankReferenceNumber(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getCustomerReferenceNumber(), paramString2);
      jdMethod_try(paramStringBuffer, localTransaction.getAmountValue(), "");
      paramStringBuffer.append(_lineSeparator);
    }
  }
  
  private void jdMethod_case(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    jdMethod_for(paramStringBuffer, 266, this.locale, paramString2);
    if (!paramString1.equals("AccountNumber")) {
      jdMethod_for(paramStringBuffer, 268, this.locale, paramString2);
    }
    jdMethod_for(paramStringBuffer, 278, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 245, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 243, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 267, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 246, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 264, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 265, this.locale, paramString2);
    jdMethod_for(paramStringBuffer, 249, this.locale, "");
    paramStringBuffer.append(_lineSeparator);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, Transactions paramTransactions, Account paramAccount, String paramString1, String paramString2)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      jdMethod_try(paramStringBuffer, localTransaction.getProcessingDate(), paramString2);
      if (!paramString1.equals("AccountNumber")) {
        if (paramAccount.getNickName() != null) {
          jdMethod_else(paramStringBuffer, paramAccount.getID() + " - " + paramAccount.getNickName(), paramString2);
        } else {
          jdMethod_else(paramStringBuffer, paramAccount.getID(), paramString2);
        }
      }
      jdMethod_else(paramStringBuffer, localTransaction.getPayeePayor(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getDescription(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getType(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getSubType(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getReferenceNumber(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getBankReferenceNumber(), paramString2);
      jdMethod_else(paramStringBuffer, localTransaction.getCustomerReferenceNumber(), paramString2);
      jdMethod_try(paramStringBuffer, localTransaction.getAmountValue(), "");
      paramStringBuffer.append(_lineSeparator);
    }
  }
  
  private StringBuffer jdMethod_for(HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = (String)paramHashMap.get("AFFILIATE_BANK_ROUTING_NUMBER");
    String str2 = (String)paramHashMap.get("SENDER_ID_TYPE");
    if (str2 == null) {
      str2 = "1";
    }
    String str3 = null;
    if (str2.equals("1")) {
      str3 = str1;
    } else if (str2.equals("2")) {
      str3 = (String)paramHashMap.get("SENDER_ID_CUSTOM_DATA");
    }
    String str4 = (String)paramHashMap.get("RECEIVER_ID_TYPE");
    if (str4 == null) {
      str4 = "1";
    }
    String str5 = null;
    if (str4.equals("1")) {
      str5 = str1;
    } else if (str4.equals("2")) {
      str5 = (String)paramHashMap.get("RECEIVER_ID_CUSTOM_DATA");
    }
    String str6 = (String)paramHashMap.get("ULTIMATE_RECEIVER_ID_TYPE");
    if (str4 == null) {
      str4 = "1";
    }
    String str7 = null;
    if (str6.equals("1")) {
      str7 = str1;
    } else if (str6.equals("2")) {
      str7 = (String)paramHashMap.get("ULTIMATE_RECEIVER_ID_CUSTOM_DATA");
    }
    String str8 = (String)paramHashMap.get("ORIGINATOR_ID_TYPE");
    if (str8 == null) {
      str8 = "1";
    }
    String str9 = null;
    if (str8.equals("2")) {
      str9 = (String)paramHashMap.get("ORIGINATOR_ID_CUSTOM_DATA");
    }
    String str10 = (String)paramHashMap.get("CUSTOMER_ACCOUNT_OPTION");
    if (str10 == null) {
      str10 = "1";
    }
    int i = 1;
    if (str10.equals("1")) {
      i = 1;
    } else if (str10.equals("2")) {
      i = 0;
    }
    HashMap localHashMap1 = new HashMap();
    Object localObject4;
    HashMap localHashMap2;
    Object localObject5;
    for (int j = 0; j < this.Sj.size(); j++)
    {
      Account localAccount1 = (Account)this.Sj.get(j);
      if (str8.equals("1")) {
        str9 = localAccount1.getRoutingNum();
      }
      localObject1 = (HashMap)localHashMap1.get(str9);
      if (localObject1 == null)
      {
        localObject1 = new HashMap();
        localHashMap1.put(str9, localObject1);
      }
      localObject2 = localAccount1.getTransactions();
      for (int m = 0; m < ((Transactions)localObject2).size(); m++)
      {
        localObject3 = (Transaction)((Transactions)localObject2).get(m);
        localObject4 = new DateTime(((Transaction)localObject3).getProcessingDate(), ((Transaction)localObject3).getProcessingDate().getLocale());
        ((DateTime)localObject4).set(13, 0);
        ((DateTime)localObject4).set(14, 0);
        localHashMap2 = (HashMap)((HashMap)localObject1).get(localObject4);
        if (localHashMap2 == null)
        {
          localHashMap2 = new HashMap();
          ((HashMap)localObject1).put(localObject4, localHashMap2);
        }
        localObject5 = (ArrayList)localHashMap2.get(localAccount1);
        if (localObject5 == null)
        {
          localObject5 = new ArrayList();
          localHashMap2.put(localAccount1, localObject5);
        }
        ((ArrayList)localObject5).add(localObject3);
      }
    }
    j = 0;
    int k = 0;
    Object localObject1 = new Currency("0", this.locale);
    jdMethod_for(localStringBuffer, "01", false);
    jdMethod_for(localStringBuffer, str3, false);
    jdMethod_for(localStringBuffer, str5, false);
    Object localObject2 = Calendar.getInstance();
    jdMethod_for(localStringBuffer, jdMethod_int((Calendar)localObject2), false);
    jdMethod_for(localStringBuffer, jdMethod_for((Calendar)localObject2), false);
    jdMethod_for(localStringBuffer, UniqueIDGenerator.getNextID(), false);
    jdMethod_for(localStringBuffer, "", false);
    jdMethod_for(localStringBuffer, "", false);
    jdMethod_for(localStringBuffer, "2", true);
    j++;
    Set localSet1 = localHashMap1.keySet();
    Object localObject3 = localSet1.iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = (String)((Iterator)localObject3).next();
      localHashMap2 = (HashMap)localHashMap1.get(localObject4);
      localObject5 = localHashMap2.keySet();
      ArrayList localArrayList1 = new ArrayList((Collection)localObject5);
      Qsort.sortSortables(localArrayList1, "", 1);
      Iterator localIterator1 = localArrayList1.iterator();
      while (localIterator1.hasNext())
      {
        DateTime localDateTime = (DateTime)localIterator1.next();
        HashMap localHashMap3 = (HashMap)localHashMap2.get(localDateTime);
        k++;
        int n = 0;
        int i1 = 0;
        Currency localCurrency1 = new Currency("0", this.locale);
        jdMethod_for(localStringBuffer, "02", false);
        jdMethod_for(localStringBuffer, str7, false);
        jdMethod_for(localStringBuffer, (String)localObject4, false);
        jdMethod_for(localStringBuffer, "1", false);
        jdMethod_for(localStringBuffer, jdMethod_int(localDateTime), false);
        jdMethod_for(localStringBuffer, jdMethod_for(localDateTime), false);
        jdMethod_for(localStringBuffer, "USD", false);
        ReportCriteria localReportCriteria = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
        Properties localProperties = localReportCriteria.getSearchCriteria();
        String str11 = (String)localProperties.get("DataClassification");
        if (str11 == null) {
          str11 = "P";
        }
        if (str11.equals("P")) {
          jdMethod_for(localStringBuffer, "2", true);
        } else if (str11.equals("I")) {
          jdMethod_for(localStringBuffer, "3", true);
        } else {
          jdMethod_for(localStringBuffer, "2", true);
        }
        n++;
        Set localSet2 = localHashMap3.keySet();
        Iterator localIterator2 = localSet2.iterator();
        while (localIterator2.hasNext())
        {
          Account localAccount2 = (Account)localIterator2.next();
          String str12 = null;
          if (i != 0) {
            str12 = localAccount2.getNumber();
          } else {
            str12 = localAccount2.getRoutingNum() + ":" + localAccount2.getNumber();
          }
          i1++;
          ArrayList localArrayList2 = (ArrayList)localHashMap3.get(localAccount2);
          int i2 = 0;
          jdMethod_for(localStringBuffer, "03", false);
          jdMethod_for(localStringBuffer, str12, false);
          jdMethod_for(localStringBuffer, localAccount2.getCurrencyCode(), false);
          jdMethod_for(localStringBuffer, "", false);
          jdMethod_for(localStringBuffer, "", false);
          jdMethod_for(localStringBuffer, "", false);
          jdMethod_for(localStringBuffer, "", true);
          i2++;
          Currency localCurrency2 = new Currency("0", this.locale);
          for (int i3 = 0; i3 < localArrayList2.size(); i3++)
          {
            Transaction localTransaction = (Transaction)localArrayList2.get(i3);
            jdMethod_for(localStringBuffer, "16", false);
            String str13 = null;
            if (localTransaction.getSubTypeValue() == 0)
            {
              if (localTransaction.getAmount() != null) {
                if (localTransaction.getAmountValue().isNegative()) {
                  str13 = "409";
                } else {
                  str13 = "108";
                }
              }
            }
            else {
              str13 = Integer.toString(localTransaction.getSubTypeValue());
            }
            jdMethod_for(localStringBuffer, str13, false);
            if (localTransaction.getAmountValue() == null)
            {
              localObject6 = localTransaction.getHash();
              if (((HashMap)localObject6).size() == 0)
              {
                jdMethod_for(localStringBuffer, "", false);
              }
              else
              {
                localObject7 = ((HashMap)localObject6).values();
                localObject8 = ((Collection)localObject7).iterator();
                String str14 = null;
                if (((Iterator)localObject8).hasNext()) {
                  str14 = (String)((Iterator)localObject8).next();
                } else {
                  str14 = "";
                }
                jdMethod_for(localStringBuffer, str14, false);
              }
            }
            else
            {
              jdMethod_for(localStringBuffer, jdMethod_for(localTransaction.getAmountValue()), false);
              if (localTransaction.getAmountValue().isNegative()) {
                localCurrency2.addAmount(localTransaction.getAmountValue().getAmountValue().negate());
              } else {
                localCurrency2.addAmount(localTransaction.getAmountValue());
              }
            }
            jdMethod_for(localStringBuffer, "", false);
            Object localObject6 = null;
            if (localTransaction.getBankReferenceNumber() == null) {
              localObject6 = "";
            } else {
              localObject6 = localTransaction.getBankReferenceNumber();
            }
            jdMethod_for(localStringBuffer, (String)localObject6, false);
            Object localObject7 = null;
            if (localTransaction.getCustomerReferenceNumber() == null) {
              localObject7 = "";
            } else {
              localObject7 = localTransaction.getCustomerReferenceNumber();
            }
            jdMethod_for(localStringBuffer, (String)localObject7, false);
            Object localObject8 = "";
            if (localTransaction.getDescription() != null) {
              localObject8 = localTransaction.getDescription();
            }
            jdMethod_for(localStringBuffer, (String)localObject8, true);
            i2++;
          }
          jdMethod_for(localStringBuffer, "49", false);
          jdMethod_for(localStringBuffer, jdMethod_int(localCurrency2), false);
          jdMethod_for(localStringBuffer, Integer.toString(++i2), true);
          n += i2;
          localCurrency1.addAmount(localCurrency2);
        }
        jdMethod_for(localStringBuffer, "98", false);
        jdMethod_for(localStringBuffer, jdMethod_int(localCurrency1), false);
        jdMethod_for(localStringBuffer, Integer.toString(i1), false);
        jdMethod_for(localStringBuffer, Integer.toString(++n), true);
        j += n;
        ((Currency)localObject1).addAmount(localCurrency1);
      }
    }
    jdMethod_for(localStringBuffer, "99", false);
    jdMethod_for(localStringBuffer, jdMethod_int((Currency)localObject1), false);
    jdMethod_for(localStringBuffer, Integer.toString(k), false);
    jdMethod_for(localStringBuffer, Integer.toString(++j), true);
    return localStringBuffer;
  }
  
  private String jdMethod_for(Currency paramCurrency)
  {
    if (paramCurrency == null) {
      return "";
    }
    StringBuffer localStringBuffer = new StringBuffer(paramCurrency.toString());
    for (int i = 0; i < localStringBuffer.length(); i++) {
      if (!Character.isDigit(localStringBuffer.charAt(i)))
      {
        localStringBuffer.deleteCharAt(i);
        i--;
      }
    }
    return localStringBuffer.toString();
  }
  
  private String jdMethod_int(Currency paramCurrency)
  {
    if (paramCurrency == null) {
      return "";
    }
    StringBuffer localStringBuffer = new StringBuffer(paramCurrency.toString());
    for (int i = 0; i < localStringBuffer.length(); i++) {
      if (!Character.isDigit(localStringBuffer.charAt(i)))
      {
        localStringBuffer.deleteCharAt(i);
        i--;
      }
    }
    if (paramCurrency.isNegative()) {
      localStringBuffer.insert(0, "-");
    }
    return localStringBuffer.toString();
  }
  
  private String jdMethod_int(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return DateFormatUtil.getFormatter("yyMMdd").format(paramCalendar.getTime());
  }
  
  private String jdMethod_for(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return DateFormatUtil.getFormatter("HHmm").format(paramCalendar.getTime());
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
  {
    if (paramString != null) {
      paramStringBuffer.append(paramString);
    }
    if (paramBoolean)
    {
      paramStringBuffer.append('/');
      paramStringBuffer.append(System.getProperty("line.separator"));
    }
    else
    {
      paramStringBuffer.append(',');
    }
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, int paramInt, String paramString)
  {
    paramStringBuffer.append(paramInt);
    paramStringBuffer.append(paramString);
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, DateTime paramDateTime, String paramString)
  {
    if (paramDateTime != null) {
      paramStringBuffer.append(paramDateTime.toString());
    }
    paramStringBuffer.append(paramString);
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, Currency paramCurrency, String paramString)
  {
    if (paramCurrency != null)
    {
      paramCurrency.setFormat("DECIMAL");
      paramStringBuffer.append(paramCurrency.toString());
    }
    paramStringBuffer.append(paramString);
  }
  
  private void jdMethod_else(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      paramStringBuffer.append(paramString1);
    }
    paramStringBuffer.append(paramString2);
  }
  
  public void set(AcctTransactionRpt paramAcctTransactionRpt)
  {
    super.set(paramAcctTransactionRpt);
    setAccounts(paramAcctTransactionRpt.getAccounts());
    setLocale(paramAcctTransactionRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCT_TRANSACTION_RPT");
    if (this.Sj != null) {
      localStringBuffer.append(this.Sj.getXML());
    }
    if (this.Sl != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTION_LIST");
      for (int i = 0; i < this.Sl.size(); i++)
      {
        XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTION_DATA");
        Transaction localTransaction = (Transaction)this.Sl.get(i);
        Account localAccount = (Account)this.Sk.get(i);
        localStringBuffer.append(localTransaction.getXML());
        XMLHandler.appendTag(localStringBuffer, "ACCOUNT_NUMBER", localAccount.getNumber());
        XMLHandler.appendTag(localStringBuffer, "BANK_ID", localAccount.getBankID());
        XMLHandler.appendTag(localStringBuffer, "ROUTING_NUMBER", localAccount.getRoutingNum());
        XMLHandler.appendTag(localStringBuffer, "ACCOUNT_ID", localAccount.getID());
        XMLHandler.appendEndTag(localStringBuffer, "TRANSACTION_DATA");
      }
      XMLHandler.appendEndTag(localStringBuffer, "TRANSACTION_LIST");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCT_TRANSACTION_RPT");
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
    String jdField_try = null;
    String jdField_new = null;
    String jdField_byte = null;
    String jdField_case = null;
    Transactions jdField_int = null;
    
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      Object localObject;
      if (paramString.equals("ACCOUNT"))
      {
        System.out.println("go here? ACCOUNT");
        if (AcctTransactionRpt.this.Sj == null) {
          AcctTransactionRpt.this.Sj = new Accounts(AcctTransactionRpt.this.locale);
        }
        localObject = AcctTransactionRpt.this.Sj.create("", "", 0);
        ((Account)localObject).continueXMLParsing(getHandler());
        System.out.println("_accounts.size() = " + AcctTransactionRpt.this.Sj.size());
      }
      else if (paramString.equals("TRANSACTION"))
      {
        System.out.println("TRANSACTION");
        if (this.jdField_int == null) {
          this.jdField_int = new Transactions();
        }
        localObject = this.jdField_int.create();
        ((Transaction)localObject).continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("TRANSACTION_DATA"))
      {
        Account localAccount = null;
        if (this.jdField_byte.equals("")) {
          localAccount = AcctTransactionRpt.this.Sj.getByNumberAndBankID(this.jdField_try, this.jdField_new);
        } else {
          localAccount = AcctTransactionRpt.this.Sj.getByIDAndBankIDAndRoutingNum(this.jdField_case, this.jdField_new, this.jdField_byte);
        }
        if (AcctTransactionRpt.this.Sl == null) {
          AcctTransactionRpt.this.Sl = new ArrayList();
        }
        if (AcctTransactionRpt.this.Sk == null) {
          AcctTransactionRpt.this.Sk = new ArrayList();
        }
        Transaction localTransaction = (Transaction)this.jdField_int.get(0);
        this.jdField_int = null;
        this.jdField_try = null;
        this.jdField_new = null;
        this.jdField_case = null;
        this.jdField_byte = null;
        AcctTransactionRpt.this.Sl.add(localTransaction);
        AcctTransactionRpt.this.Sk.add(localAccount);
      }
      else
      {
        super.endElement(paramString);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (getElement().equals("ACCOUNT_NUMBER")) {
        this.jdField_try = str;
      } else if (getElement().equals("BANK_ID")) {
        this.jdField_new = str;
      } else if (getElement().equals("ROUTING_NUMBER")) {
        this.jdField_byte = str;
      } else if (getElement().equals("ACCOUNT_ID")) {
        this.jdField_case = str;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AcctTransactionRpt
 * JD-Core Version:    0.7.0.1
 */