package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.UniqueIDGenerator;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.Qsort;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class BalanceDetailRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String BAL_DETAIL_RPT = "BAL_DETAIL_RPT";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private BalanceDetailRecords Sx = null;
  
  public BalanceDetailRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof BalanceDetailRpt))) {
      return false;
    }
    boolean bool = false;
    BalanceDetailRpt localBalanceDetailRpt = (BalanceDetailRpt)paramObject;
    BalanceDetailRecords localBalanceDetailRecords = localBalanceDetailRpt.getBalanceDetailRecords();
    if (this.Sx == null) {
      bool = localBalanceDetailRecords == null;
    } else {
      bool = this.Sx.equals(localBalanceDetailRecords);
    }
    return bool;
  }
  
  public void setBalanceDetailRecords(BalanceDetailRecords paramBalanceDetailRecords)
  {
    this.Sx = paramBalanceDetailRecords;
  }
  
  public BalanceDetailRecords getBalanceDetailRecords()
  {
    return this.Sx;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    } else if (paramString.equals("BAI2")) {
      localStringBuffer = jdMethod_new(paramHashMap);
    }
    return localStringBuffer;
  }
  
  private void jdMethod_byte(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null) {
      paramStringBuffer.append(paramCurrency.removeCharFromString(paramCurrency.toString(), ','));
    }
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, char paramChar, Account paramAccount, Transactions paramTransactions)
  {
    if (paramTransactions != null)
    {
      paramStringBuffer.append(_lineSeparator);
      paramStringBuffer.append(ReportConsts.getColumnName(740, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(741, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(742, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(743, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(744, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(745, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(746, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(747, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(748, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(749, this.locale));
      paramStringBuffer.append(_lineSeparator);
      Transaction localTransaction = null;
      Iterator localIterator = paramTransactions.iterator();
      while (localIterator.hasNext())
      {
        localTransaction = (Transaction)localIterator.next();
        paramStringBuffer.append(paramChar);
        if (paramAccount.getID() != null) {
          paramStringBuffer.append(paramAccount.getID());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getPayeePayor() != null) {
          paramStringBuffer.append(localTransaction.getPayeePayor());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getMemo() != null) {
          paramStringBuffer.append(localTransaction.getMemo());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getType() != null) {
          paramStringBuffer.append(localTransaction.getType());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getSubType() != null) {
          paramStringBuffer.append(localTransaction.getSubType());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getReferenceNumber() != null) {
          paramStringBuffer.append(localTransaction.getReferenceNumber());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getBankReferenceNumber() != null) {
          paramStringBuffer.append(localTransaction.getBankReferenceNumber());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getCustomerReferenceNumber() != null) {
          paramStringBuffer.append(localTransaction.getCustomerReferenceNumber());
        }
        paramStringBuffer.append(paramChar);
        jdMethod_byte(paramStringBuffer, localTransaction.getAmountValue());
        paramStringBuffer.append(_lineSeparator);
      }
    }
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, char paramChar, Account paramAccount, Transactions paramTransactions)
  {
    if (paramTransactions != null)
    {
      paramStringBuffer.append(_lineSeparator);
      paramStringBuffer.append(ReportConsts.getColumnName(720, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(721, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(722, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(723, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(724, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(725, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(726, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(727, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(728, this.locale));
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(ReportConsts.getColumnName(729, this.locale));
      paramStringBuffer.append(_lineSeparator);
      Transaction localTransaction = null;
      Iterator localIterator = paramTransactions.iterator();
      while (localIterator.hasNext())
      {
        localTransaction = (Transaction)localIterator.next();
        paramStringBuffer.append(paramChar);
        if (paramAccount.getID() != null) {
          paramStringBuffer.append(paramAccount.getID());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getPayeePayor() != null) {
          paramStringBuffer.append(localTransaction.getPayeePayor());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getMemo() != null) {
          paramStringBuffer.append(localTransaction.getMemo());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getType() != null) {
          paramStringBuffer.append(localTransaction.getType());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getSubType() != null) {
          paramStringBuffer.append(localTransaction.getSubType());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getReferenceNumber() != null) {
          paramStringBuffer.append(localTransaction.getReferenceNumber());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getBankReferenceNumber() != null) {
          paramStringBuffer.append(localTransaction.getBankReferenceNumber());
        }
        paramStringBuffer.append(paramChar);
        if (localTransaction.getCustomerReferenceNumber() != null) {
          paramStringBuffer.append(localTransaction.getCustomerReferenceNumber());
        }
        paramStringBuffer.append(paramChar);
        jdMethod_byte(paramStringBuffer, localTransaction.getAmountValue());
        paramStringBuffer.append(_lineSeparator);
      }
    }
  }
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(ReportConsts.getColumnName(700, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(701, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(702, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(703, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(704, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(705, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(706, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(707, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(708, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(709, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(710, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(711, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(712, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(713, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(714, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(715, this.locale));
    localStringBuffer.append(_lineSeparator);
    if (this.Sx != null)
    {
      AccountHistory localAccountHistory = null;
      BalanceDetailRecord localBalanceDetailRecord = null;
      BalanceSummaryRecord localBalanceSummaryRecord = null;
      Iterator localIterator = this.Sx.iterator();
      while (localIterator.hasNext())
      {
        localBalanceDetailRecord = (BalanceDetailRecord)localIterator.next();
        localBalanceSummaryRecord = localBalanceDetailRecord.getBalanceSummaryRecord();
        localAccountHistory = localBalanceSummaryRecord.getAccountHistory();
        localStringBuffer.append(localAccountHistory.getAccountID());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localAccountHistory.getHistoryDate());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getClosingAvail());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getOneDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getTwoOrMoreDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getOpeningLedger());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getAvgOpeningLedgerMTD());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getOpeningAvail());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getCurrentAvail());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getAvgOpenAvailMTD());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getClosingLedger());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localAccountHistory.getAvgClosingLedgerMTD());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localBalanceSummaryRecord.getNumCredits());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localBalanceSummaryRecord.getTotalCredits());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localBalanceSummaryRecord.getNumDebits());
        localStringBuffer.append(paramChar);
        jdMethod_byte(localStringBuffer, localBalanceSummaryRecord.getTotalDebits());
        localStringBuffer.append(_lineSeparator);
        jdMethod_for(localStringBuffer, paramChar, localBalanceSummaryRecord.getAccount(), localBalanceDetailRecord.getCreditTransactions());
        jdMethod_int(localStringBuffer, paramChar, localBalanceSummaryRecord.getAccount(), localBalanceDetailRecord.getDebitTransactions());
      }
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_new(HashMap paramHashMap)
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
    boolean bool = true;
    if (str10.equals("1")) {
      bool = true;
    } else if (str10.equals("2")) {
      bool = false;
    }
    int i = 0;
    int j = 0;
    Currency localCurrency1 = new Currency("0", this.locale);
    int k = 0;
    int m = 0;
    Currency localCurrency2 = new Currency("0", this.locale);
    int n = 0;
    Currency localCurrency3 = new Currency("0", this.locale);
    i++;
    jdMethod_long(localStringBuffer, str3, str5);
    ReportCriteria localReportCriteria = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
    Properties localProperties = localReportCriteria.getSearchCriteria();
    String str11 = (String)localProperties.get("DataClassification");
    if (str11 == null) {
      str11 = "P";
    }
    if (this.Sx != null)
    {
      HashMap localHashMap1 = jdMethod_int(paramHashMap);
      Set localSet = localHashMap1.keySet();
      Iterator localIterator1 = localSet.iterator();
      while (localIterator1.hasNext())
      {
        String str12 = (String)localIterator1.next();
        HashMap localHashMap2 = (HashMap)localHashMap1.get(str12);
        ArrayList localArrayList1 = new ArrayList(localHashMap2.keySet());
        Qsort.sortSortables(localArrayList1, "", 1);
        Iterator localIterator2 = localArrayList1.iterator();
        ArrayList localArrayList2 = null;
        DateTime localDateTime = null;
        BalanceDetailRecord localBalanceDetailRecord = null;
        Object[] arrayOfObject = new Object[2];
        while (localIterator2.hasNext())
        {
          localDateTime = (DateTime)localIterator2.next();
          localArrayList2 = (ArrayList)localHashMap2.get(localDateTime);
          i++;
          k++;
          jdMethod_for(localStringBuffer, jdMethod_for(((BalanceDetailRecord)localArrayList2.get(0)).getBalanceSummaryRecord().getAccountHistory().getHistoryDate()), jdMethod_int(((BalanceDetailRecord)localArrayList2.get(0)).getBalanceSummaryRecord().getAccountHistory().getHistoryDate()), str11, str7, str12);
          j++;
          for (int i1 = 0; i1 < localArrayList2.size(); i1++)
          {
            m++;
            localBalanceDetailRecord = (BalanceDetailRecord)localArrayList2.get(i1);
            i++;
            k++;
            n++;
            localCurrency3.addAmount(jdMethod_for(localStringBuffer, localBalanceDetailRecord, bool));
            localCurrency2.addAmount(localCurrency3);
            localCurrency1.addAmount(localCurrency3);
            arrayOfObject = jdMethod_for(localStringBuffer, localBalanceDetailRecord);
            localCurrency3.addAmount((Currency)arrayOfObject[0]);
            localCurrency2.addAmount((Currency)arrayOfObject[0]);
            localCurrency1.addAmount((Currency)arrayOfObject[0]);
            n += ((Integer)arrayOfObject[1]).intValue();
            k += ((Integer)arrayOfObject[1]).intValue();
            i += ((Integer)arrayOfObject[1]).intValue();
            i++;
            k++;
            n++;
            jdMethod_for(localStringBuffer, localCurrency3, n);
            localCurrency3 = new Currency("0", this.locale);
            n = 0;
          }
          i++;
          k++;
          jdMethod_for(localStringBuffer, localCurrency2, m, k);
          localCurrency2 = new Currency("0", this.locale);
          m = 0;
          k = 0;
        }
      }
    }
    i++;
    jdMethod_int(localStringBuffer, localCurrency1, j, i);
    return localStringBuffer;
  }
  
  private HashMap jdMethod_int(HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("ORIGINATOR_ID_TYPE");
    if (str1 == null) {
      str1 = "1";
    }
    String str2 = null;
    if (str1.equals("2")) {
      str2 = (String)paramHashMap.get("ORIGINATOR_ID_CUSTOM_DATA");
    }
    HashMap localHashMap1 = new HashMap();
    Iterator localIterator = this.Sx.iterator();
    BalanceDetailRecord localBalanceDetailRecord = null;
    AccountHistory localAccountHistory = null;
    DateTime localDateTime = null;
    while (localIterator.hasNext())
    {
      localBalanceDetailRecord = (BalanceDetailRecord)localIterator.next();
      localAccountHistory = localBalanceDetailRecord.getBalanceSummaryRecord().getAccountHistory();
      Account localAccount = localBalanceDetailRecord.getBalanceSummaryRecord().getAccount();
      if (str1.equals("1")) {
        str2 = localAccount.getRoutingNum();
      }
      HashMap localHashMap2 = (HashMap)localHashMap1.get(str2);
      if (localHashMap2 == null)
      {
        localHashMap2 = new HashMap();
        localHashMap1.put(str2, localHashMap2);
      }
      localDateTime = new DateTime(localAccountHistory.getHistoryDate(), localAccountHistory.getHistoryDate().getLocale());
      localDateTime.set(13, 0);
      localDateTime.set(14, 0);
      if (!localHashMap2.containsKey(localDateTime))
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(localBalanceDetailRecord);
        localHashMap2.put(localDateTime, localArrayList);
      }
      else
      {
        ((ArrayList)localHashMap2.get(localDateTime)).add(localBalanceDetailRecord);
      }
    }
    return localHashMap1;
  }
  
  private void jdMethod_long(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    jdMethod_int(paramStringBuffer, "01", false);
    jdMethod_int(paramStringBuffer, paramString1, false);
    jdMethod_int(paramStringBuffer, paramString2, false);
    jdMethod_int(paramStringBuffer, H(), false);
    jdMethod_int(paramStringBuffer, G(), false);
    jdMethod_int(paramStringBuffer, UniqueIDGenerator.getNextID(), false);
    jdMethod_int(paramStringBuffer, "", false);
    jdMethod_int(paramStringBuffer, "", false);
    jdMethod_int(paramStringBuffer, "2", true);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, Currency paramCurrency, int paramInt1, int paramInt2)
  {
    jdMethod_int(paramStringBuffer, "99", false);
    jdMethod_int(paramStringBuffer, jdMethod_try(paramCurrency), false);
    jdMethod_for(paramStringBuffer, paramInt1, false);
    jdMethod_for(paramStringBuffer, paramInt2, true);
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    jdMethod_int(paramStringBuffer, "02", false);
    jdMethod_int(paramStringBuffer, paramString4, false);
    jdMethod_int(paramStringBuffer, paramString5, false);
    jdMethod_int(paramStringBuffer, "1", false);
    jdMethod_int(paramStringBuffer, paramString1, false);
    jdMethod_int(paramStringBuffer, paramString2, false);
    jdMethod_int(paramStringBuffer, "USD", false);
    if (paramString3.equals("P")) {
      jdMethod_int(paramStringBuffer, "2", true);
    } else if (paramString3.equals("I")) {
      jdMethod_int(paramStringBuffer, "3", true);
    } else {
      jdMethod_int(paramStringBuffer, "2", true);
    }
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, Currency paramCurrency, int paramInt1, int paramInt2)
  {
    jdMethod_int(paramStringBuffer, "98", false);
    jdMethod_int(paramStringBuffer, jdMethod_try(paramCurrency), false);
    jdMethod_for(paramStringBuffer, paramInt1, false);
    jdMethod_for(paramStringBuffer, paramInt2, true);
  }
  
  private Currency jdMethod_for(StringBuffer paramStringBuffer, BalanceDetailRecord paramBalanceDetailRecord, boolean paramBoolean)
  {
    BalanceSummaryRecord localBalanceSummaryRecord = paramBalanceDetailRecord.getBalanceSummaryRecord();
    AccountHistory localAccountHistory = localBalanceSummaryRecord.getAccountHistory();
    String str = null;
    if (paramBoolean) {
      str = localBalanceSummaryRecord.getAccount().getID();
    } else {
      str = localBalanceSummaryRecord.getAccount().getRoutingNum() + ":" + localBalanceSummaryRecord.getAccount().getID();
    }
    jdMethod_int(paramStringBuffer, "03", false);
    jdMethod_int(paramStringBuffer, str, false);
    jdMethod_int(paramStringBuffer, localBalanceSummaryRecord.getAccount().getCurrencyCode(), false);
    ArrayList localArrayList1 = new ArrayList(11);
    ArrayList localArrayList2 = null;
    Currency localCurrency = new Currency("0", this.locale);
    if (localAccountHistory.getClosingAvail() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("045");
      localArrayList2.add(localAccountHistory.getClosingAvail());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getOneDayFloat() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("072");
      localArrayList2.add(localAccountHistory.getOneDayFloat());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getTwoOrMoreDayFloat() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("074");
      localArrayList2.add(localAccountHistory.getTwoOrMoreDayFloat());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getOpeningLedger() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("010");
      localArrayList2.add(localAccountHistory.getOpeningLedger());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getAvgOpeningLedgerMTD() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("011");
      localArrayList2.add(localAccountHistory.getAvgOpeningLedgerMTD());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getOpeningAvail() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("040");
      localArrayList2.add(localAccountHistory.getOpeningAvail());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getCurrentAvail() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("060");
      localArrayList2.add(localAccountHistory.getCurrentAvail());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getAvgOpenAvailMTD() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("041");
      localArrayList2.add(localAccountHistory.getAvgOpenAvailMTD());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getClosingLedger() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("015");
      localArrayList2.add(localAccountHistory.getClosingLedger());
      localArrayList1.add(localArrayList2);
    }
    if (localAccountHistory.getAvgClosingLedgerMTD() != null)
    {
      localArrayList2 = new ArrayList(2);
      localArrayList2.add("020");
      localArrayList2.add(localAccountHistory.getAvgClosingLedgerMTD());
      localArrayList1.add(localArrayList2);
    }
    if (localBalanceSummaryRecord.getTotalCredits() != null)
    {
      localArrayList2 = new ArrayList(3);
      localArrayList2.add("100");
      localArrayList2.add(localBalanceSummaryRecord.getTotalCredits());
      localArrayList2.add(Integer.toString(localBalanceSummaryRecord.getNumCredits()));
      localArrayList1.add(localArrayList2);
    }
    if (localBalanceSummaryRecord.getTotalDebits() != null)
    {
      localArrayList2 = new ArrayList(3);
      localArrayList2.add("400");
      localArrayList2.add(localBalanceSummaryRecord.getTotalDebits());
      localArrayList2.add(Integer.toString(localBalanceSummaryRecord.getNumDebits()));
      localArrayList1.add(localArrayList2);
    }
    if (localArrayList1.size() == 0)
    {
      jdMethod_int(paramStringBuffer, "", false);
      jdMethod_int(paramStringBuffer, "", false);
      jdMethod_int(paramStringBuffer, "", false);
      jdMethod_int(paramStringBuffer, "", true);
    }
    else
    {
      for (int i = 0; i < localArrayList1.size(); i++)
      {
        localArrayList2 = (ArrayList)localArrayList1.get(i);
        jdMethod_int(paramStringBuffer, (String)localArrayList2.get(0), false);
        jdMethod_int(paramStringBuffer, jdMethod_try((Currency)localArrayList2.get(1)), false);
        if (localArrayList2.size() == 2) {
          jdMethod_int(paramStringBuffer, "", false);
        } else if (localArrayList2.size() == 3) {
          jdMethod_int(paramStringBuffer, (String)localArrayList2.get(2), false);
        }
        if (i == localArrayList1.size() - 1) {
          jdMethod_int(paramStringBuffer, "", true);
        } else {
          jdMethod_int(paramStringBuffer, "", false);
        }
        localCurrency.addAmount((Currency)localArrayList2.get(1));
      }
    }
    return localCurrency;
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, Currency paramCurrency, int paramInt)
  {
    jdMethod_int(paramStringBuffer, "49", false);
    jdMethod_int(paramStringBuffer, jdMethod_try(paramCurrency), false);
    jdMethod_for(paramStringBuffer, paramInt, true);
  }
  
  private Object[] jdMethod_for(StringBuffer paramStringBuffer, BalanceDetailRecord paramBalanceDetailRecord)
  {
    Currency localCurrency = new Currency("0", this.locale);
    int i = 0;
    Object[] arrayOfObject1 = jdMethod_for(paramStringBuffer, paramBalanceDetailRecord.getCreditTransactions());
    localCurrency.addAmount((Currency)arrayOfObject1[0]);
    i += ((Integer)arrayOfObject1[1]).intValue();
    arrayOfObject1 = jdMethod_for(paramStringBuffer, paramBalanceDetailRecord.getDebitTransactions());
    localCurrency.addAmount((Currency)arrayOfObject1[0]);
    i += ((Integer)arrayOfObject1[1]).intValue();
    Object[] arrayOfObject2 = { localCurrency, new Integer(i) };
    return arrayOfObject2;
  }
  
  private Object[] jdMethod_for(StringBuffer paramStringBuffer, Transactions paramTransactions)
  {
    Iterator localIterator = paramTransactions.iterator();
    Transaction localTransaction = null;
    int i = 0;
    Currency localCurrency1 = new Currency("0", this.locale);
    Currency localCurrency2 = null;
    while (localIterator.hasNext())
    {
      i++;
      localTransaction = (Transaction)localIterator.next();
      localCurrency2 = new Currency("0", this.locale);
      jdMethod_int(paramStringBuffer, "16", false);
      jdMethod_int(paramStringBuffer, localTransaction.getSubType(), false);
      localCurrency2 = localTransaction.getAmountValue();
      if (localCurrency2.isNegative()) {
        localCurrency2.setAmount(localCurrency2.getAmountValue().negate());
      }
      jdMethod_int(paramStringBuffer, jdMethod_new(localCurrency2), false);
      localCurrency1.addAmount(localCurrency2);
      jdMethod_int(paramStringBuffer, "", false);
      if (localTransaction.getBankReferenceNumber() != null) {
        jdMethod_int(paramStringBuffer, localTransaction.getBankReferenceNumber(), false);
      } else {
        jdMethod_int(paramStringBuffer, "", false);
      }
      if (localTransaction.getCustomerReferenceNumber() != null) {
        jdMethod_int(paramStringBuffer, localTransaction.getCustomerReferenceNumber(), false);
      } else {
        jdMethod_int(paramStringBuffer, "", false);
      }
      if (localTransaction.getDescription() != null) {
        jdMethod_int(paramStringBuffer, localTransaction.getDescription(), true);
      } else {
        jdMethod_int(paramStringBuffer, "", true);
      }
    }
    Object[] arrayOfObject = { localCurrency1, new Integer(i) };
    return arrayOfObject;
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
  {
    if (paramString != null) {
      paramStringBuffer.append(paramString);
    }
    if (!paramBoolean) {
      paramStringBuffer.append(',');
    } else {
      paramStringBuffer.append('/').append(_lineSeparator);
    }
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    jdMethod_int(paramStringBuffer, Integer.toString(paramInt), paramBoolean);
  }
  
  private String jdMethod_new(Currency paramCurrency)
  {
    if (paramCurrency != null)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramCurrency.toString());
      int i = 0;
      while (i < localStringBuffer.length()) {
        if (!Character.isDigit(localStringBuffer.charAt(i))) {
          localStringBuffer.deleteCharAt(i);
        } else {
          i++;
        }
      }
      return localStringBuffer.toString();
    }
    return "";
  }
  
  private String jdMethod_try(Currency paramCurrency)
  {
    if ((paramCurrency != null) && (paramCurrency.isNegative())) {
      return "-" + jdMethod_new(paramCurrency);
    }
    return jdMethod_new(paramCurrency);
  }
  
  private String H()
  {
    return jdMethod_for(new DateTime());
  }
  
  private String jdMethod_for(DateTime paramDateTime)
  {
    return DateFormatUtil.getFormatter("yyMMdd").format(paramDateTime.getTime());
  }
  
  private String G()
  {
    return jdMethod_int(new DateTime());
  }
  
  private String jdMethod_int(DateTime paramDateTime)
  {
    return DateFormatUtil.getFormatter("HHmm").format(paramDateTime.getTime());
  }
  
  public void set(BalanceDetailRpt paramBalanceDetailRpt)
  {
    super.set(paramBalanceDetailRpt);
    setBalanceDetailRecords(paramBalanceDetailRpt.getBalanceDetailRecords());
    setLocale(paramBalanceDetailRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "BAL_DETAIL_RPT");
    if (this.Sx != null) {
      localStringBuffer.append(this.Sx.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BAL_DETAIL_RPT");
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
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("BALANCEDETAILRECORDS"))
      {
        if (BalanceDetailRpt.this.Sx == null) {
          BalanceDetailRpt.this.Sx = new BalanceDetailRecords();
        }
        BalanceDetailRpt.this.Sx.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.BalanceDetailRpt
 * JD-Core Version:    0.7.0.1
 */