package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.UniqueIDGenerator;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.Qsort;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class BalanceSummaryRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String BAL_SUM_RPT = "BAL_SUM_RPT";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private BalanceSummaryRecords SC = null;
  
  public BalanceSummaryRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof BalanceSummaryRpt))) {
      return false;
    }
    boolean bool = false;
    BalanceSummaryRpt localBalanceSummaryRpt = (BalanceSummaryRpt)paramObject;
    BalanceSummaryRecords localBalanceSummaryRecords = localBalanceSummaryRpt.getBalanceSummaryRecords();
    if (this.SC == null) {
      bool = localBalanceSummaryRecords == null;
    } else {
      bool = this.SC.equals(localBalanceSummaryRecords);
    }
    return bool;
  }
  
  public void setBalanceSummaryRecords(BalanceSummaryRecords paramBalanceSummaryRecords)
  {
    this.SC = paramBalanceSummaryRecords;
  }
  
  public BalanceSummaryRecords getBalanceSummaryRecords()
  {
    return this.SC;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    } else if (paramString.equals("BAI2")) {
      localStringBuffer = jdMethod_case(paramHashMap);
    }
    return localStringBuffer;
  }
  
  private void jdMethod_goto(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null) {
      paramStringBuffer.append(paramCurrency.removeCharFromString(paramCurrency.toString(), ','));
    }
  }
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(ReportConsts.getColumnName(760, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(761, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(762, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(763, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(764, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(765, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(766, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(767, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(768, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(769, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(770, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(771, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(772, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(773, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(774, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(775, this.locale));
    localStringBuffer.append(_lineSeparator);
    if (this.SC != null)
    {
      AccountHistory localAccountHistory = null;
      BalanceSummaryRecord localBalanceSummaryRecord = null;
      Iterator localIterator = this.SC.iterator();
      while (localIterator.hasNext())
      {
        localBalanceSummaryRecord = (BalanceSummaryRecord)localIterator.next();
        localAccountHistory = localBalanceSummaryRecord.getAccountHistory();
        localStringBuffer.append(localAccountHistory.getAccountID());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localAccountHistory.getHistoryDate());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getClosingAvail());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getOneDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getTwoOrMoreDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getOpeningLedger());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getAvgOpeningLedgerMTD());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getOpeningAvail());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getCurrentAvail());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getAvgOpenAvailMTD());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getClosingLedger());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localAccountHistory.getAvgClosingLedgerMTD());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localBalanceSummaryRecord.getNumCredits());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localBalanceSummaryRecord.getTotalCredits());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localBalanceSummaryRecord.getNumDebits());
        localStringBuffer.append(paramChar);
        jdMethod_goto(localStringBuffer, localBalanceSummaryRecord.getTotalDebits());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_case(HashMap paramHashMap)
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
    Currency localCurrency3 = new Currency("0", this.locale);
    i++;
    jdMethod_null(localStringBuffer, str3, str5);
    ReportCriteria localReportCriteria = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
    Properties localProperties = localReportCriteria.getSearchCriteria();
    String str11 = (String)localProperties.get("DataClassification");
    if (str11 == null) {
      str11 = "P";
    }
    if (this.SC != null)
    {
      HashMap localHashMap1 = jdMethod_byte(paramHashMap);
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
        while (localIterator2.hasNext())
        {
          localDateTime = (DateTime)localIterator2.next();
          localArrayList2 = (ArrayList)localHashMap2.get(localDateTime);
          i++;
          k++;
          jdMethod_int(localStringBuffer, jdMethod_new(((BalanceSummaryRecord)localArrayList2.get(0)).getAccountHistory().getHistoryDate()), jdMethod_try(((BalanceSummaryRecord)localArrayList2.get(0)).getAccountHistory().getHistoryDate()), str11, str7, str12);
          j++;
          for (int n = 0; n < localArrayList2.size(); n++)
          {
            m++;
            i++;
            k++;
            localCurrency3.addAmount(jdMethod_for(localStringBuffer, (BalanceSummaryRecord)localArrayList2.get(n), bool));
            localCurrency2.addAmount(localCurrency3);
            localCurrency1.addAmount(localCurrency3);
            i++;
            k++;
            jdMethod_else(localStringBuffer, localCurrency3);
            localCurrency3 = new Currency("0", this.locale);
          }
          i++;
          k++;
          jdMethod_new(localStringBuffer, localCurrency2, m, k);
          localCurrency2 = new Currency("0", this.locale);
          m = 0;
          k = 0;
        }
      }
    }
    i++;
    jdMethod_try(localStringBuffer, localCurrency1, j, i);
    return localStringBuffer;
  }
  
  private HashMap jdMethod_byte(HashMap paramHashMap)
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
    Iterator localIterator = this.SC.iterator();
    BalanceSummaryRecord localBalanceSummaryRecord = null;
    AccountHistory localAccountHistory = null;
    DateTime localDateTime = null;
    while (localIterator.hasNext())
    {
      localBalanceSummaryRecord = (BalanceSummaryRecord)localIterator.next();
      localAccountHistory = localBalanceSummaryRecord.getAccountHistory();
      Account localAccount = localBalanceSummaryRecord.getAccount();
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
        localArrayList.add(localBalanceSummaryRecord);
        localHashMap2.put(localDateTime, localArrayList);
      }
      else
      {
        ((ArrayList)localHashMap2.get(localDateTime)).add(localBalanceSummaryRecord);
      }
    }
    return localHashMap1;
  }
  
  private void jdMethod_null(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    jdMethod_try(paramStringBuffer, "01", false);
    jdMethod_try(paramStringBuffer, paramString1, false);
    jdMethod_try(paramStringBuffer, paramString2, false);
    jdMethod_try(paramStringBuffer, J(), false);
    jdMethod_try(paramStringBuffer, I(), false);
    jdMethod_try(paramStringBuffer, UniqueIDGenerator.getNextID(), false);
    jdMethod_try(paramStringBuffer, "", false);
    jdMethod_try(paramStringBuffer, "", false);
    jdMethod_try(paramStringBuffer, "2", true);
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, Currency paramCurrency, int paramInt1, int paramInt2)
  {
    jdMethod_try(paramStringBuffer, "99", false);
    jdMethod_try(paramStringBuffer, jdMethod_else(paramCurrency), false);
    jdMethod_int(paramStringBuffer, paramInt1, false);
    jdMethod_int(paramStringBuffer, paramInt2, true);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    jdMethod_try(paramStringBuffer, "02", false);
    jdMethod_try(paramStringBuffer, paramString4, false);
    jdMethod_try(paramStringBuffer, paramString5, false);
    jdMethod_try(paramStringBuffer, "1", false);
    jdMethod_try(paramStringBuffer, paramString1, false);
    jdMethod_try(paramStringBuffer, paramString2, false);
    jdMethod_try(paramStringBuffer, "USD", false);
    if (paramString3.equals("P")) {
      jdMethod_try(paramStringBuffer, "2", true);
    } else if (paramString3.equals("I")) {
      jdMethod_try(paramStringBuffer, "3", true);
    } else {
      jdMethod_try(paramStringBuffer, "2", true);
    }
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, Currency paramCurrency, int paramInt1, int paramInt2)
  {
    jdMethod_try(paramStringBuffer, "98", false);
    jdMethod_try(paramStringBuffer, jdMethod_else(paramCurrency), false);
    jdMethod_int(paramStringBuffer, paramInt1, false);
    jdMethod_int(paramStringBuffer, paramInt2, true);
  }
  
  private Currency jdMethod_for(StringBuffer paramStringBuffer, BalanceSummaryRecord paramBalanceSummaryRecord, boolean paramBoolean)
  {
    AccountHistory localAccountHistory = paramBalanceSummaryRecord.getAccountHistory();
    String str = null;
    if (paramBoolean) {
      str = paramBalanceSummaryRecord.getAccount().getID();
    } else {
      str = paramBalanceSummaryRecord.getAccount().getRoutingNum() + ":" + paramBalanceSummaryRecord.getAccount().getID();
    }
    jdMethod_try(paramStringBuffer, "03", false);
    jdMethod_try(paramStringBuffer, str, false);
    jdMethod_try(paramStringBuffer, paramBalanceSummaryRecord.getAccount().getCurrencyCode(), false);
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
    if (paramBalanceSummaryRecord.getTotalCredits() != null)
    {
      localArrayList2 = new ArrayList(3);
      localArrayList2.add("100");
      localArrayList2.add(paramBalanceSummaryRecord.getTotalCredits());
      localArrayList2.add(Integer.toString(paramBalanceSummaryRecord.getNumCredits()));
      localArrayList1.add(localArrayList2);
    }
    if (paramBalanceSummaryRecord.getTotalDebits() != null)
    {
      localArrayList2 = new ArrayList(3);
      localArrayList2.add("400");
      localArrayList2.add(paramBalanceSummaryRecord.getTotalDebits());
      localArrayList2.add(Integer.toString(paramBalanceSummaryRecord.getNumDebits()));
      localArrayList1.add(localArrayList2);
    }
    if (localArrayList1.size() == 0)
    {
      jdMethod_try(paramStringBuffer, "", false);
      jdMethod_try(paramStringBuffer, "", false);
      jdMethod_try(paramStringBuffer, "", false);
      jdMethod_try(paramStringBuffer, "", true);
    }
    else
    {
      for (int i = 0; i < localArrayList1.size(); i++)
      {
        localArrayList2 = (ArrayList)localArrayList1.get(i);
        jdMethod_try(paramStringBuffer, (String)localArrayList2.get(0), false);
        jdMethod_try(paramStringBuffer, jdMethod_else((Currency)localArrayList2.get(1)), false);
        if (localArrayList2.size() == 2) {
          jdMethod_try(paramStringBuffer, "", false);
        } else if (localArrayList2.size() == 3) {
          jdMethod_try(paramStringBuffer, (String)localArrayList2.get(2), false);
        }
        if (i == localArrayList1.size() - 1) {
          jdMethod_try(paramStringBuffer, "", true);
        } else {
          jdMethod_try(paramStringBuffer, "", false);
        }
        localCurrency.addAmount((Currency)localArrayList2.get(1));
      }
    }
    return localCurrency;
  }
  
  private void jdMethod_else(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    jdMethod_try(paramStringBuffer, "49", false);
    jdMethod_try(paramStringBuffer, jdMethod_else(paramCurrency), false);
    jdMethod_try(paramStringBuffer, "2", true);
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
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
  
  private void jdMethod_int(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    jdMethod_try(paramStringBuffer, Integer.toString(paramInt), paramBoolean);
  }
  
  private String jdMethod_char(Currency paramCurrency)
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
  
  private String jdMethod_else(Currency paramCurrency)
  {
    if ((paramCurrency != null) && (paramCurrency.isNegative())) {
      return "-" + jdMethod_char(paramCurrency);
    }
    return jdMethod_char(paramCurrency);
  }
  
  private String J()
  {
    return jdMethod_new(new DateTime());
  }
  
  private String jdMethod_new(DateTime paramDateTime)
  {
    return DateFormatUtil.getFormatter("yyMMdd").format(paramDateTime.getTime());
  }
  
  private String I()
  {
    return jdMethod_try(new DateTime());
  }
  
  private String jdMethod_try(DateTime paramDateTime)
  {
    return DateFormatUtil.getFormatter("HHmm").format(paramDateTime.getTime());
  }
  
  public void set(BalanceSummaryRpt paramBalanceSummaryRpt)
  {
    super.set(paramBalanceSummaryRpt);
    setBalanceSummaryRecords(paramBalanceSummaryRpt.getBalanceSummaryRecords());
    setLocale(paramBalanceSummaryRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "BAL_SUM_RPT");
    if (this.SC != null) {
      localStringBuffer.append(this.SC.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BAL_SUM_RPT");
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
      if (paramString.equals("BALANCESUMMARYRECORDS"))
      {
        if (BalanceSummaryRpt.this.SC == null) {
          BalanceSummaryRpt.this.SC = new BalanceSummaryRecords();
        }
        BalanceSummaryRpt.this.SC.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.BalanceSummaryRpt
 * JD-Core Version:    0.7.0.1
 */