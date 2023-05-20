package com.ffusion.services.demo;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.beans.lockbox.LockboxCreditItem;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxDepositItemRpt;
import com.ffusion.beans.lockbox.LockboxDepositRpt;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.lockbox.LockboxSummaryRpt;
import com.ffusion.beans.lockbox.LockboxTransaction;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.Lockbox;
import com.ffusion.services.lockbox.LBoxException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class DemoLockboxService
  implements Lockbox
{
  private static Locale jdField_try = ;
  private static final String jdField_for = "demoLockbox.xml";
  private static final int jdField_do = 0;
  private static final int jdField_if = 1;
  private LockboxAccounts jdField_new;
  private LockboxCreditItems a;
  private LockboxTransactions jdField_int;
  public static final String MINIMUM_TRANSACTION_INDEX_FOR_RANGE = "MINIMUM_TRANSACTION_INDEX_FOR_RANGE";
  public static final String MAXIMUM_TRANSACTION_INDEX_FOR_RANGE = "MAXIMUM_TRANSACTION_INDEX_FOR_RANGE";
  
  public void initialize()
    throws LBoxException
  {
    InputStream localInputStream = ResourceUtil.getResourceAsStream(this, "demoLockbox.xml");
    String str = null;
    try
    {
      str = Strings.streamToString(localInputStream);
    }
    catch (IOException localIOException)
    {
      DebugLog.throwing("Could not convert input stream to string", localIOException);
    }
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), str);
      Iterator localIterator = this.a.iterator();
      Object localObject;
      GregorianCalendar localGregorianCalendar;
      DateTime localDateTime;
      for (int i = 0; localIterator.hasNext(); i--)
      {
        localObject = (LockboxCreditItem)localIterator.next();
        localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.roll(5, i);
        localDateTime = new DateTime(localGregorianCalendar, jdField_try);
        ((LockboxCreditItem)localObject).setProcessingDate(localDateTime);
      }
      localIterator = this.jdField_int.iterator();
      for (i = 0; localIterator.hasNext(); i--)
      {
        localObject = (LockboxTransaction)localIterator.next();
        localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.roll(5, i);
        localDateTime = new DateTime(localGregorianCalendar, jdField_try);
        ((LockboxTransaction)localObject).setProcessingDate(localDateTime);
      }
    }
    catch (Throwable localThrowable) {}
  }
  
  public LockboxAccounts getLockboxAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws LBoxException
  {
    if (this.jdField_new == null)
    {
      DebugLog.log("No Accounts to list: _lbAccts == null");
      throw new LBoxException(39500);
    }
    return this.jdField_new;
  }
  
  public LockboxSummaries getSummaries(LockboxAccounts paramLockboxAccounts, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccounts == null) {
      return null;
    }
    LockboxSummaries localLockboxSummaries = null;
    try
    {
      localLockboxSummaries = new LockboxSummaries(jdField_try);
      LockboxAccounts localLockboxAccounts = getLockboxAccounts(null, null);
      Iterator localIterator1 = paramLockboxAccounts.iterator();
      while (localIterator1.hasNext())
      {
        LockboxAccount localLockboxAccount = (LockboxAccount)localIterator1.next();
        if (!localLockboxAccounts.contains(localLockboxAccount)) {
          throw new Exception();
        }
        LockboxSummary localLockboxSummary = localLockboxSummaries.add();
        localLockboxSummary.setLockboxAccount(localLockboxAccount);
        localLockboxSummary.setSummaryDate(new DateTime());
        Currency localCurrency1 = new Currency();
        Currency localCurrency2 = new Currency();
        Currency localCurrency3 = new Currency();
        Currency localCurrency4 = new Currency();
        Currency localCurrency5 = new Currency();
        int i = 0;
        int j = 0;
        LockboxTransactions localLockboxTransactions = getRecentTransactions(localLockboxAccount, null);
        if (localLockboxTransactions != null)
        {
          Iterator localIterator2 = localLockboxTransactions.iterator();
          while (localIterator2.hasNext())
          {
            LockboxTransaction localLockboxTransaction = (LockboxTransaction)localIterator2.next();
            int k = localLockboxTransaction.getTransactionType();
            if (k == 0)
            {
              i++;
              localCurrency1.addAmount(localLockboxTransaction.getAmount());
            }
            else if (k == 1)
            {
              j++;
              localCurrency2.addAmount(localLockboxTransaction.getAmount());
            }
            localCurrency3.addAmount(localLockboxTransaction.getImmediateFloat());
            localCurrency4.addAmount(localLockboxTransaction.getOneDayFloat());
            localCurrency5.addAmount(localLockboxTransaction.getTwoDayFloat());
          }
          localLockboxSummary.setTotalLockboxCredits(localCurrency1);
          localLockboxSummary.setTotalNumLockboxCredits(i);
          localLockboxSummary.setTotalLockboxDebits(localCurrency2);
          localLockboxSummary.setTotalNumLockboxDebits(j);
          localLockboxSummary.setImmediateFloat(localCurrency3);
          localLockboxSummary.setOneDayFloat(localCurrency4);
          localLockboxSummary.setTwoDayFloat(localCurrency5);
        }
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Could not get list of Summaries", localException);
      throw new LBoxException(39501, localException);
    }
    return localLockboxSummaries;
  }
  
  public LockboxTransactions getTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.jdField_int == null)
    {
      DebugLog.log("No transactions to list: _lbTransactions == null");
      throw new LBoxException(39503);
    }
    LockboxTransactions localLockboxTransactions = new LockboxTransactions(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.jdField_int.iterator();
    DateTime localDateTime1 = paramCalendar1 == null ? null : new DateTime(paramCalendar1, jdField_try);
    DateTime localDateTime2 = paramCalendar2 == null ? null : new DateTime(paramCalendar2, jdField_try);
    while (localIterator.hasNext())
    {
      LockboxTransaction localLockboxTransaction = (LockboxTransaction)localIterator.next();
      if ((localLockboxTransaction.getAccountNumber().equals(str1)) && (localLockboxTransaction.getBankID().equals(str2)))
      {
        DateTime localDateTime3 = localLockboxTransaction.getProcessingDate();
        if (((localDateTime1 == null) || (localDateTime3.compare(localDateTime1, null) < 0)) && ((localDateTime2 == null) || (localDateTime3.compare(localDateTime2, null) > 0))) {
          localLockboxTransactions.add(localLockboxTransaction);
        }
      }
    }
    return localLockboxTransactions;
  }
  
  public LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.jdField_int == null)
    {
      DebugLog.log("No transactions to list: _lbTransactions == null");
      throw new LBoxException(39503);
    }
    LockboxTransactions localLockboxTransactions = new LockboxTransactions(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.jdField_int.iterator();
    DateTime localDateTime1 = paramCalendar1 == null ? null : new DateTime(paramCalendar1, jdField_try);
    DateTime localDateTime2 = paramCalendar2 == null ? null : new DateTime(paramCalendar2, jdField_try);
    long l1 = 0L;
    long l2 = 1L;
    int i = 1;
    while (localIterator.hasNext())
    {
      LockboxTransaction localLockboxTransaction = (LockboxTransaction)localIterator.next();
      if ((localLockboxTransaction.getAccountNumber().equals(str1)) && (localLockboxTransaction.getBankID().equals(str2)))
      {
        DateTime localDateTime3 = localLockboxTransaction.getProcessingDate();
        if (((localDateTime1 == null) || (localDateTime3.compare(localDateTime1, null) < 0)) && ((localDateTime2 == null) || (localDateTime3.compare(localDateTime2, null) > 0)))
        {
          if (i != 0)
          {
            i = 0;
            l1 = localLockboxTransaction.getTransactionIndex();
            l2 = localLockboxTransaction.getTransactionIndex();
          }
          else
          {
            if (localLockboxTransaction.getTransactionIndex() > l2) {
              l2 = localLockboxTransaction.getTransactionIndex();
            }
            if (localLockboxTransaction.getTransactionIndex() < l1) {
              l1 = localLockboxTransaction.getTransactionIndex();
            }
          }
          localLockboxTransactions.add(localLockboxTransaction);
        }
      }
    }
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l1));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l2));
    return localLockboxTransactions;
  }
  
  public LockboxTransactions getRecentTransactions(LockboxAccount paramLockboxAccount, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.jdField_int == null)
    {
      DebugLog.log("No transactions to list: _lbTransactions == null");
      throw new LBoxException(39503);
    }
    LockboxTransactions localLockboxTransactions = new LockboxTransactions(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.jdField_int.iterator();
    while (localIterator.hasNext())
    {
      LockboxTransaction localLockboxTransaction = (LockboxTransaction)localIterator.next();
      if ((localLockboxTransaction.getAccountNumber().equals(str1)) && (localLockboxTransaction.getBankID().equals(str2))) {
        localLockboxTransactions.add(localLockboxTransaction);
      }
    }
    return localLockboxTransactions;
  }
  
  public LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.jdField_int == null)
    {
      DebugLog.log("No transactions to list: _lbTransactions == null");
      throw new LBoxException(39503);
    }
    LockboxTransactions localLockboxTransactions = new LockboxTransactions(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.jdField_int.iterator();
    while (localIterator.hasNext())
    {
      LockboxTransaction localLockboxTransaction = (LockboxTransaction)localIterator.next();
      if ((localLockboxTransaction.getAccountNumber().equals(str1)) && (localLockboxTransaction.getBankID().equals(str2)) && (localLockboxTransaction.getTransactionIndex() >= paramLong)) {
        localLockboxTransactions.add(localLockboxTransaction);
      }
    }
    return localLockboxTransactions;
  }
  
  public LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.jdField_int == null)
    {
      DebugLog.log("No transactions to list: _lbTransactions == null");
      throw new LBoxException(39503);
    }
    LockboxTransactions localLockboxTransactions = new LockboxTransactions(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.jdField_int.iterator();
    while (localIterator.hasNext())
    {
      LockboxTransaction localLockboxTransaction = (LockboxTransaction)localIterator.next();
      if ((localLockboxTransaction.getAccountNumber().equals(str1)) && (localLockboxTransaction.getBankID().equals(str2)) && (localLockboxTransaction.getTransactionIndex() <= paramLong)) {
        localLockboxTransactions.add(localLockboxTransaction);
      }
    }
    return localLockboxTransactions;
  }
  
  public LockboxCreditItems getCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.a == null)
    {
      DebugLog.log("No credit items to list: _lbCreditItems == null");
      throw new LBoxException(39504);
    }
    LockboxCreditItems localLockboxCreditItems = new LockboxCreditItems(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.a.iterator();
    DateTime localDateTime1 = paramCalendar1 == null ? null : new DateTime(paramCalendar1, jdField_try);
    DateTime localDateTime2 = paramCalendar2 == null ? null : new DateTime(paramCalendar2, jdField_try);
    while (localIterator.hasNext())
    {
      LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)localIterator.next();
      if ((localLockboxCreditItem.getAccountNumber().equals(str1)) && (localLockboxCreditItem.getBankID().equals(str2)) && (localLockboxCreditItem.getLockboxNumber().equals(paramString)))
      {
        DateTime localDateTime3 = localLockboxCreditItem.getProcessingDate();
        if (((localDateTime1 == null) || (localDateTime3.compare(localDateTime1, null) < 0)) && ((localDateTime2 == null) || (localDateTime3.compare(localDateTime2, null) > 0))) {
          localLockboxCreditItems.add(localLockboxCreditItem);
        }
      }
    }
    return localLockboxCreditItems;
  }
  
  public LockboxCreditItems getPagedCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.a == null)
    {
      DebugLog.log("No credit items to list: _lbCreditItems == null");
      throw new LBoxException(39504);
    }
    LockboxCreditItems localLockboxCreditItems = new LockboxCreditItems(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.a.iterator();
    DateTime localDateTime1 = paramCalendar1 == null ? null : new DateTime(paramCalendar1, jdField_try);
    DateTime localDateTime2 = paramCalendar2 == null ? null : new DateTime(paramCalendar2, jdField_try);
    long l1 = 0L;
    long l2 = 1L;
    int i = 1;
    while (localIterator.hasNext())
    {
      LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)localIterator.next();
      if ((localLockboxCreditItem.getAccountNumber().equals(str1)) && (localLockboxCreditItem.getBankID().equals(str2)) && (localLockboxCreditItem.getLockboxNumber().equals(paramString)))
      {
        DateTime localDateTime3 = localLockboxCreditItem.getProcessingDate();
        if (((localDateTime1 == null) || (localDateTime3.compare(localDateTime1, null) < 0)) && ((localDateTime2 == null) || (localDateTime3.compare(localDateTime2, null) > 0)))
        {
          if (i != 0)
          {
            i = 0;
            l1 = localLockboxCreditItem.getItemIndex();
            l2 = localLockboxCreditItem.getItemIndex();
          }
          else
          {
            if (localLockboxCreditItem.getItemIndex() > l2) {
              l2 = localLockboxCreditItem.getItemIndex();
            }
            if (localLockboxCreditItem.getItemIndex() < l1) {
              l1 = localLockboxCreditItem.getItemIndex();
            }
          }
          localLockboxCreditItems.add(localLockboxCreditItem);
        }
      }
    }
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l1));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l2));
    return localLockboxCreditItems;
  }
  
  public LockboxCreditItems getRecentCreditItems(LockboxAccount paramLockboxAccount, String paramString, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.a == null)
    {
      DebugLog.log("No credit items to list: _lbCreditItems == null");
      throw new LBoxException(39504);
    }
    LockboxCreditItems localLockboxCreditItems = new LockboxCreditItems(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)localIterator.next();
      if ((localLockboxCreditItem.getAccountNumber().equals(str1)) && (localLockboxCreditItem.getBankID().equals(str2)) && (localLockboxCreditItem.getLockboxNumber().equals(paramString))) {
        localLockboxCreditItems.add(localLockboxCreditItem);
      }
    }
    return localLockboxCreditItems;
  }
  
  public LockboxCreditItems getNextCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.a == null)
    {
      DebugLog.log("No credit items to list: _lbCreditItems == null");
      throw new LBoxException(39504);
    }
    LockboxCreditItems localLockboxCreditItems = new LockboxCreditItems(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)localIterator.next();
      if ((localLockboxCreditItem.getAccountNumber().equals(str1)) && (localLockboxCreditItem.getBankID().equals(str2)) && (localLockboxCreditItem.getLockboxNumber().equals(paramString)) && (localLockboxCreditItem.getItemIndex() >= paramLong)) {
        localLockboxCreditItems.add(localLockboxCreditItem);
      }
    }
    return localLockboxCreditItems;
  }
  
  public LockboxCreditItems getPreviousCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramLockboxAccount == null) {
      return null;
    }
    if (this.a == null)
    {
      DebugLog.log("No credit items to list: _lbCreditItems == null");
      throw new LBoxException(39504);
    }
    LockboxCreditItems localLockboxCreditItems = new LockboxCreditItems(jdField_try);
    String str1 = paramLockboxAccount.getAccountNumber();
    String str2 = paramLockboxAccount.getBankID();
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)localIterator.next();
      if ((localLockboxCreditItem.getAccountNumber().equals(str1)) && (localLockboxCreditItem.getBankID().equals(str2)) && (localLockboxCreditItem.getLockboxNumber().equals(paramString)) && (localLockboxCreditItem.getItemIndex() <= paramLong)) {
        localLockboxCreditItems.add(localLockboxCreditItem);
      }
    }
    return localLockboxCreditItems;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws LBoxException
  {
    if (paramReportCriteria == null) {
      return null;
    }
    if (paramReportCriteria == null)
    {
      DebugLog.log("Incoming ReportCriteria object is null.");
      throw new LBoxException(39506);
    }
    Properties localProperties = paramReportCriteria.getReportOptions();
    if (localProperties == null)
    {
      DebugLog.log("Incoming ReportCriteria's reportOptions(Properties) object is null.");
      throw new LBoxException(39506);
    }
    String str = localProperties.getProperty("REPORTTYPE");
    Object localObject = null;
    if (str.equals("DepositItemSearch"))
    {
      localObject = new LockboxDepositItemRpt(paramSecureUser.getLocale());
      ((LockboxDepositItemRpt)localObject).setLockboxCreditItems(this.a);
    }
    else if (str.equals("LockboxDepositReport"))
    {
      localObject = new LockboxDepositRpt(paramSecureUser.getLocale());
      ((LockboxDepositRpt)localObject).setLockboxTransactions(this.jdField_int);
    }
    else if (str.equals("LockboxSummary"))
    {
      localObject = new LockboxSummaryRpt(paramSecureUser.getLocale());
      ((LockboxSummaryRpt)localObject).setLockboxSummaries(new LockboxSummaries(paramSecureUser.getLocale()));
    }
    else
    {
      DebugLog.log("This report type '" + str + "' is not supported.");
      throw new LBoxException(39505);
    }
    return localObject;
  }
  
  protected class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("LOCKBOX_ACCOUNTS"))
      {
        if (DemoLockboxService.this.jdField_new == null) {
          DemoLockboxService.this.jdField_new = new LockboxAccounts(DemoLockboxService.try);
        }
        DemoLockboxService.this.jdField_new.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("LOCKBOX_CREDIT_ITEMS"))
      {
        if (DemoLockboxService.this.a == null) {
          DemoLockboxService.this.a = new LockboxCreditItems(DemoLockboxService.try);
        }
        DemoLockboxService.this.a.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("LOCKBOX_TRANSACTIONS"))
      {
        if (DemoLockboxService.this.jdField_int == null) {
          DemoLockboxService.this.jdField_int = new LockboxTransactions(DemoLockboxService.try);
        }
        DemoLockboxService.this.jdField_int.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.DemoLockboxService
 * JD-Core Version:    0.7.0.1
 */