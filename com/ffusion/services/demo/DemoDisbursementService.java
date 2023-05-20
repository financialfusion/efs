package com.ffusion.services.demo;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementSummary;
import com.ffusion.beans.disbursement.DisbursementTransaction;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.Disbursements;
import com.ffusion.services.disbursements.DisbException;
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

public class DemoDisbursementService
  implements Disbursements
{
  private static Locale jdField_if = ;
  private DisbursementAccounts jdField_for;
  private DisbursementSummaries a;
  private DisbursementTransactions jdField_new;
  private static final String jdField_do = "demoDisbursements.xml";
  final int jdField_int = 5;
  
  public void initialize()
    throws DisbException
  {
    InputStream localInputStream = ResourceUtil.getResourceAsStream(this, "demoDisbursements.xml");
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
      Iterator localIterator = null;
      int i = 0;
      int j = 1;
      localIterator = this.jdField_new.iterator();
      i = 0;
      while (localIterator.hasNext())
      {
        DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)localIterator.next();
        GregorianCalendar localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.roll(5, i);
        DateTime localDateTime = new DateTime(localGregorianCalendar, jdField_if);
        localDisbursementTransaction.setProcessingDate(localDateTime);
        localDisbursementTransaction.setCheckDate(localDateTime);
        localDisbursementTransaction.setValueDateTime(localDateTime);
        i--;
        j *= -1;
      }
    }
    catch (Throwable localThrowable) {}
  }
  
  public DisbursementAccounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws DisbException
  {
    if (this.jdField_for == null)
    {
      DebugLog.log("No Accounts to list: _disbAccounts == null");
      throw new DisbException(610);
    }
    return this.jdField_for;
  }
  
  public DisbursementSummaries getSummaries(DisbursementAccounts paramDisbursementAccounts, HashMap paramHashMap)
    throws DisbException
  {
    if (paramDisbursementAccounts == null) {
      return null;
    }
    DisbursementSummaries localDisbursementSummaries = null;
    try
    {
      localDisbursementSummaries = new DisbursementSummaries(jdField_if);
      DisbursementAccounts localDisbursementAccounts = getAccounts(null, null);
      Iterator localIterator1 = paramDisbursementAccounts.iterator();
      while (localIterator1.hasNext())
      {
        DisbursementAccount localDisbursementAccount = (DisbursementAccount)localIterator1.next();
        if (!localDisbursementAccounts.contains(localDisbursementAccount)) {
          throw new Exception();
        }
        DisbursementSummary localDisbursementSummary = localDisbursementSummaries.create();
        localDisbursementSummary.setAccount(localDisbursementAccount);
        localDisbursementSummary.setSummaryDate(new DateTime());
        localDisbursementSummary.setValueDateTime(new DateTime());
        localDisbursementSummary.setCurrentBalance(new Currency("0", jdField_if));
        localDisbursementSummary.setFedPresentmentEstimate(new Currency("0", jdField_if));
        localDisbursementSummary.setImmediateFundsNeeded(new Currency("0", jdField_if));
        localDisbursementSummary.setLateDebits(new Currency("0", jdField_if));
        localDisbursementSummary.setOneDayFundsNeeded(new Currency("0", jdField_if));
        localDisbursementSummary.setTotalChecksPaidEarlyAmount(new Currency("0", jdField_if));
        localDisbursementSummary.setTotalChecksPaidLastAmount(new Currency("0", jdField_if));
        localDisbursementSummary.setTotalChecksPaidLateAmount(new Currency("0", jdField_if));
        localDisbursementSummary.setTotalCredits(new Currency("0", jdField_if));
        localDisbursementSummary.setTotalDebits(new Currency("0", jdField_if));
        localDisbursementSummary.setTotalDTCCredits(new Currency("0", jdField_if));
        localDisbursementSummary.setTwoDayFundsNeeded(new Currency("0", jdField_if));
        int i = 0;
        int j = 0;
        DisbursementTransactions localDisbursementTransactions = getRecentTransactions(localDisbursementAccount, null);
        if (localDisbursementTransactions != null)
        {
          Iterator localIterator2 = localDisbursementTransactions.iterator();
          while (localIterator2.hasNext())
          {
            DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)localIterator2.next();
            if (localDisbursementTransaction.getProcessingDate() == null)
            {
              Currency localCurrency = localDisbursementTransaction.getCheckAmount();
              if (localCurrency.isNegative())
              {
                j++;
                localDisbursementSummary.getTotalDebits().addAmount(localCurrency);
              }
              else
              {
                i++;
                localDisbursementSummary.getTotalCredits().addAmount(localCurrency);
              }
              localDisbursementSummary.getCurrentBalance().addAmount(localCurrency);
            }
            localDisbursementSummary.getImmediateFundsNeeded().addAmount(localDisbursementTransaction.getImmediateFundsNeeded());
            localDisbursementSummary.getOneDayFundsNeeded().addAmount(localDisbursementTransaction.getOneDayFundsNeeded());
            localDisbursementSummary.getTwoDayFundsNeeded().addAmount(localDisbursementTransaction.getTwoDayFundsNeeded());
          }
          localDisbursementSummary.setNumItemsPending(i + j);
        }
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Could not get list of Summaries", localException);
      throw new DisbException(600, localException);
    }
    return localDisbursementSummaries;
  }
  
  public DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException
  {
    if (paramDisbursementAccount == null) {
      return null;
    }
    if (this.jdField_new == null)
    {
      DebugLog.log("No transactions to list: _disbTransactions == null");
      throw new DisbException(607);
    }
    DisbursementTransactions localDisbursementTransactions = new DisbursementTransactions(jdField_if);
    String str1 = paramDisbursementAccount.getAccountNumber();
    String str2 = paramDisbursementAccount.getBankID();
    Iterator localIterator = this.jdField_new.iterator();
    DateTime localDateTime1 = paramCalendar1 == null ? null : new DateTime(paramCalendar1, jdField_if);
    DateTime localDateTime2 = paramCalendar2 == null ? null : new DateTime(paramCalendar2, jdField_if);
    while (localIterator.hasNext())
    {
      DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)localIterator.next();
      if ((localDisbursementTransaction.getAccountNumber().equals(str1)) && (localDisbursementTransaction.getBankID().equals(str2)))
      {
        DateTime localDateTime3 = localDisbursementTransaction.getProcessingDate();
        if (((localDateTime1 == null) || (localDateTime3.compare(localDateTime1, null) >= 0)) && ((localDateTime2 == null) || (localDateTime3.compare(localDateTime2, null) <= 0))) {
          localDisbursementTransactions.add(localDisbursementTransaction);
        }
      }
    }
    return localDisbursementTransactions;
  }
  
  public DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException
  {
    DisbursementTransactions localDisbursementTransactions = getTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramHashMap);
    if ((localDisbursementTransactions != null) && (localDisbursementTransactions.size() > 5)) {
      while (localDisbursementTransactions.size() > 5) {
        localDisbursementTransactions.remove(localDisbursementTransactions.size() - 1);
      }
    }
    return localDisbursementTransactions;
  }
  
  public DisbursementTransactions getRecentTransactions(DisbursementAccount paramDisbursementAccount, HashMap paramHashMap)
    throws DisbException
  {
    if (paramDisbursementAccount == null) {
      return null;
    }
    if (this.jdField_new == null)
    {
      DebugLog.log("No transactions to list: _disbTransactions == null");
      throw new DisbException(607);
    }
    DisbursementTransactions localDisbursementTransactions = new DisbursementTransactions(jdField_if);
    String str1 = paramDisbursementAccount.getAccountNumber();
    String str2 = paramDisbursementAccount.getBankID();
    this.jdField_new.setSortedBy("TRANSACTIONINDEX,REVERSE");
    Iterator localIterator = this.jdField_new.iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)localIterator.next();
      if ((localDisbursementTransaction.getAccountNumber().equals(str1)) && (localDisbursementTransaction.getBankID().equals(str2)))
      {
        localDisbursementTransactions.add(localDisbursementTransaction);
        i++;
        if (i >= 5) {
          break;
        }
      }
    }
    return localDisbursementTransactions;
  }
  
  public DisbursementTransactions getNextTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws DisbException
  {
    if (paramDisbursementAccount == null) {
      return null;
    }
    if (this.jdField_new == null)
    {
      DebugLog.log("No transactions to list: _disbTransactions == null");
      throw new DisbException(607);
    }
    DisbursementTransactions localDisbursementTransactions = new DisbursementTransactions(jdField_if);
    String str1 = paramDisbursementAccount.getAccountNumber();
    String str2 = paramDisbursementAccount.getBankID();
    this.jdField_new.setSortedBy("TRANSACTIONINDEX,REVERSE");
    Iterator localIterator = this.jdField_new.iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)localIterator.next();
      if ((localDisbursementTransaction.getAccountNumber().equals(str1)) && (localDisbursementTransaction.getBankID().equals(str2)) && (localDisbursementTransaction.getTransactionIndex() < paramLong))
      {
        localDisbursementTransactions.add(localDisbursementTransaction);
        i++;
        if (i >= 5) {
          break;
        }
      }
    }
    return localDisbursementTransactions;
  }
  
  public DisbursementTransactions getPreviousTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws DisbException
  {
    if (paramDisbursementAccount == null) {
      return null;
    }
    if (this.jdField_new == null)
    {
      DebugLog.log("No transactions to list: _dbTransactions == null");
      throw new DisbException(607);
    }
    DisbursementTransactions localDisbursementTransactions = new DisbursementTransactions(jdField_if);
    String str1 = paramDisbursementAccount.getAccountNumber();
    String str2 = paramDisbursementAccount.getBankID();
    this.jdField_new.setSortedBy("TRANSACTIONINDEX");
    Iterator localIterator = this.jdField_new.iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)localIterator.next();
      if ((localDisbursementTransaction.getAccountNumber().equals(str1)) && (localDisbursementTransaction.getBankID().equals(str2)) && (localDisbursementTransaction.getTransactionIndex() > paramLong))
      {
        localDisbursementTransactions.add(localDisbursementTransaction);
        i++;
        if (i >= 5) {
          break;
        }
      }
    }
    return localDisbursementTransactions;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DisbException
  {
    if (paramReportCriteria == null) {
      return null;
    }
    Properties localProperties = paramReportCriteria.getReportOptions();
    if (localProperties == null)
    {
      DebugLog.log("Incoming ReportCriteria's reportOptions(Properties) object is null.");
      throw new DisbException(613);
    }
    IReportResult local1 = new IReportResult()
    {
      public Object export(String paramAnonymousString, HashMap paramAnonymousHashMap)
      {
        return new Object();
      }
      
      public Locale getLocale()
      {
        return DemoDisbursementService.if;
      }
      
      public void setLocale(Locale paramAnonymousLocale)
      {
        paramAnonymousLocale = paramAnonymousLocale;
      }
    };
    return local1;
  }
  
  protected class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("DISBURSEMENTACCOUNTS"))
      {
        if (DemoDisbursementService.this.jdField_for == null) {
          DemoDisbursementService.this.jdField_for = new DisbursementAccounts(DemoDisbursementService.if);
        }
        DemoDisbursementService.this.jdField_for.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("DISBURSEMENTSUMMARIES"))
      {
        if (DemoDisbursementService.this.a == null) {
          DemoDisbursementService.this.a = new DisbursementSummaries(DemoDisbursementService.if);
        }
        DemoDisbursementService.this.a.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("DISBURSEMENTTRANSACTIONS"))
      {
        if (DemoDisbursementService.this.jdField_new == null) {
          DemoDisbursementService.this.jdField_new = new DisbursementTransactions(DemoDisbursementService.if);
        }
        DemoDisbursementService.this.jdField_new.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.DemoDisbursementService
 * JD-Core Version:    0.7.0.1
 */