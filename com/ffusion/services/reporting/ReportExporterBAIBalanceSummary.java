package com.ffusion.services.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.UniqueIDGenerator;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ReportExporterBAIBalanceSummary
  extends ReportExporterBase
{
  private StringBuffer c = null;
  private HashMap jdField_long = null;
  private Locale b = Locale.getDefault();
  private String jdField_void;
  private ArrayList o;
  private HashMap p = new HashMap();
  private int k;
  private String h;
  private DateTime jdField_null;
  private int i = 0;
  private int n = 0;
  private Currency m;
  private int g = 0;
  private int e = 0;
  private Currency j;
  private int f = 0;
  private String jdField_goto;
  boolean l;
  String d;
  
  public ReportExporterBAIBalanceSummary(ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    super(paramReportCriteria);
    this.jdField_long = paramHashMap;
    this.c = new StringBuffer();
    this.m = new Currency("0", this.b);
    this.j = new Currency("0", this.b);
    this.jdField_void = getEOLString();
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    String str = this.c.toString();
    this.c.setLength(0);
    return str;
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {}
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {}
  
  protected void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException
  {}
  
  protected void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException
  {}
  
  protected void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    this.o.add(paramObject);
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    this.p.put(paramArrayList.get(0), new Integer(this.k++));
  }
  
  protected void doInitReportResultTable()
    throws RptException
  {
    String str1 = (String)this.jdField_long.get("AFFILIATE_BANK_ROUTING_NUMBER");
    String str2 = (String)this.jdField_long.get("SENDER_ID_TYPE");
    if (str2 == null) {
      str2 = "1";
    }
    String str3 = null;
    if (str2.equals("1")) {
      str3 = str1;
    } else if (str2.equals("2")) {
      str3 = (String)this.jdField_long.get("SENDER_ID_CUSTOM_DATA");
    }
    String str4 = (String)this.jdField_long.get("RECEIVER_ID_TYPE");
    if (str4 == null) {
      str4 = "1";
    }
    String str5 = null;
    if (str4.equals("1")) {
      str5 = str1;
    } else if (str4.equals("2")) {
      str5 = (String)this.jdField_long.get("RECEIVER_ID_CUSTOM_DATA");
    }
    this.i += 1;
    jdMethod_do(str3, str5);
    String str6 = (String)this.jdField_long.get("ULTIMATE_RECEIVER_ID_TYPE");
    if (str6 == null) {
      str6 = "1";
    }
    this.jdField_goto = null;
    if (str6.equals("1")) {
      this.jdField_goto = str1;
    } else if (str6.equals("2")) {
      this.jdField_goto = ((String)this.jdField_long.get("ULTIMATE_RECEIVER_ID_CUSTOM_DATA"));
    }
    this.l = (!"2".equals(this.jdField_long.get("CUSTOMER_ACCOUNT_OPTION")));
    if ("2".equals(this.jdField_long.get("ORIGINATOR_ID_TYPE"))) {
      this.d = ((String)this.jdField_long.get("ORIGINATOR_ID_CUSTOM_DATA"));
    }
  }
  
  protected void doFiniReportResultTable()
    throws RptException
  {
    jdMethod_try();
    this.i += 1;
    jdMethod_if(this.m, this.n, this.i);
  }
  
  protected void doInitReportResultRow()
    throws RptException
  {
    if (this.o == null) {
      this.k = 0;
    } else {
      this.o.clear();
    }
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    if (this.o == null) {
      this.o = new ArrayList(this.p.size());
    } else {
      try
      {
        jdMethod_char();
      }
      catch (ClassCastException localClassCastException)
      {
        throw new RptException(100, localClassCastException);
      }
    }
  }
  
  protected void doWriteBlankLine()
    throws RptException
  {}
  
  protected void doWriteParagraph(String paramString)
    throws RptException
  {}
  
  protected void doAddPageBreak()
    throws RptException
  {}
  
  private void jdMethod_try()
  {
    if (this.jdField_null != null)
    {
      this.i += 1;
      this.g += 1;
      a(this.j, this.e, this.g);
      this.j.setAmount("0");
      this.e = 0;
      this.g = 0;
    }
  }
  
  private void jdMethod_char()
    throws RptException
  {
    String str1;
    if (this.d != null) {
      str1 = this.d;
    } else {
      str1 = (String)jdMethod_int("RoutingNum");
    }
    DateTime localDateTime1 = (DateTime)jdMethod_int("Date");
    String str2 = (String)jdMethod_int("DataClassification");
    DateTime localDateTime2 = new DateTime(localDateTime1, localDateTime1.getLocale());
    localDateTime2.set(13, 0);
    localDateTime2.set(14, 0);
    if ((!str1.equals(this.h)) || (!localDateTime2.equals(this.jdField_null)))
    {
      jdMethod_try();
      this.h = str1;
      this.jdField_null = localDateTime2;
      this.i += 1;
      this.g += 1;
      a(a(localDateTime1), jdMethod_if(localDateTime1), str2, this.jdField_goto, str1);
      this.n += 1;
    }
    this.e += 1;
    this.i += 1;
    this.g += 1;
    this.f += 1;
    Currency localCurrency = jdMethod_case();
    this.j.addAmount(localCurrency);
    this.m.addAmount(localCurrency);
    this.i += 1;
    this.g += 1;
    this.f += 1;
    a(localCurrency, this.f);
  }
  
  private Object jdMethod_int(String paramString)
    throws RptException
  {
    Integer localInteger = (Integer)this.p.get(paramString);
    if ((localInteger == null) || (localInteger.intValue() < 0) || (localInteger.intValue() >= this.o.size())) {
      throw new RptException(100, "Could not find data for column '" + paramString + "'");
    }
    return this.o.get(localInteger.intValue());
  }
  
  private void jdMethod_do(String paramString1, String paramString2)
  {
    a("01", false);
    a(paramString1, false);
    a(paramString2, false);
    a(jdMethod_else(), false);
    a(jdMethod_byte(), false);
    a(UniqueIDGenerator.getNextID(), false);
    a("", false);
    a("", false);
    a("2", true);
  }
  
  private void jdMethod_if(Currency paramCurrency, int paramInt1, int paramInt2)
  {
    a("99", false);
    a(jdMethod_if(paramCurrency), false);
    a(paramInt1, false);
    a(paramInt2, true);
  }
  
  private void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    a("02", false);
    a(paramString4, false);
    a(paramString5, false);
    a("1", false);
    a(paramString1, false);
    a(paramString2, false);
    a("USD", false);
    if (paramString3.equals("P")) {
      a("2", true);
    } else if (paramString3.equals("I")) {
      a("3", true);
    } else {
      a("2", true);
    }
  }
  
  private void a(Currency paramCurrency, int paramInt1, int paramInt2)
  {
    a("98", false);
    a(jdMethod_if(paramCurrency), false);
    a(paramInt1, false);
    a(paramInt2, true);
  }
  
  private Currency jdMethod_case()
    throws RptException
  {
    String str1 = (String)jdMethod_int("AccountId");
    if (!this.l)
    {
      str2 = (String)jdMethod_int("RoutingNum");
      if (str2 != null)
      {
        StringBuffer localStringBuffer = new StringBuffer(str2.length() + 1 + str1.length());
        localStringBuffer.append(str2).append(':').append(str1);
        str1 = localStringBuffer.toString();
      }
    }
    String str2 = (String)jdMethod_int("CurrencyCode");
    int i1 = this.c.length();
    a("03", false);
    a(str1, false);
    a(str2, false);
    ArrayList localArrayList = new ArrayList(12);
    a("045", "ClosingAvail", localArrayList);
    a("072", "OneDayFloat", localArrayList);
    a("074", "TwoOrMoreDayFloat", localArrayList);
    a("010", "OpeningLedger", localArrayList);
    a("011", "AvgOpeningLedgerMTD", localArrayList);
    a("040", "OpeningAvail", localArrayList);
    a("060", "CurrentAvail", localArrayList);
    a("041", "AvgOpenAvailMTD", localArrayList);
    a("015", "ClosingLedger", localArrayList);
    a("020", "AvgClosingLedgerMTD", localArrayList);
    a("100", "TotalCredits", "NumCredits", localArrayList);
    a("400", "TotalDebits", "NumDebits", localArrayList);
    Currency localCurrency = new Currency("0", this.b);
    if (localArrayList.size() == 0)
    {
      a("", false);
      a("", false);
      a("", false);
      a("", true);
    }
    else
    {
      int i2 = this.c.length() - i1;
      int i3 = exportSummaryAndStatusFields(i2, localArrayList, this.c);
      this.i += i3;
      this.g += i3;
      this.f += i3;
      for (int i4 = 0; i4 < localArrayList.size(); i4++)
      {
        ReportExporterBAIBalanceDetail.SummaryValue localSummaryValue = (ReportExporterBAIBalanceDetail.SummaryValue)localArrayList.get(i4);
        if ((localSummaryValue.getSummaryValue() instanceof Currency)) {
          localCurrency.addAmount((Currency)localSummaryValue.getSummaryValue());
        }
      }
    }
    return localCurrency;
  }
  
  private void a(String paramString1, String paramString2, String paramString3, ArrayList paramArrayList)
    throws RptException
  {
    Currency localCurrency = (Currency)jdMethod_int(paramString2);
    Integer localInteger = (Integer)jdMethod_int(paramString3);
    if (localCurrency != null) {
      if (localInteger == null) {
        paramArrayList.add(new SummaryValue(Integer.parseInt(paramString1), localCurrency));
      } else {
        paramArrayList.add(new SummaryValue(Integer.parseInt(paramString1), localCurrency, localInteger.intValue()));
      }
    }
  }
  
  private void a(String paramString1, String paramString2, ArrayList paramArrayList)
    throws RptException
  {
    Currency localCurrency = (Currency)jdMethod_int(paramString2);
    if (localCurrency != null) {
      paramArrayList.add(new SummaryValue(Integer.parseInt(paramString1), localCurrency));
    }
  }
  
  private void a(Currency paramCurrency, int paramInt)
  {
    a("49", false);
    a(jdMethod_if(paramCurrency), false);
    a(paramInt, true);
    this.f = 0;
  }
  
  private void a(String paramString, boolean paramBoolean)
  {
    if (paramString != null) {
      this.c.append(paramString);
    }
    if (!paramBoolean) {
      this.c.append(',');
    } else {
      this.c.append('/').append(this.jdField_void);
    }
  }
  
  private void a(int paramInt, boolean paramBoolean)
  {
    a(Integer.toString(paramInt), paramBoolean);
  }
  
  private String a(Currency paramCurrency)
  {
    if (paramCurrency != null)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramCurrency.toString());
      int i1 = 0;
      while (i1 < localStringBuffer.length()) {
        if (!Character.isDigit(localStringBuffer.charAt(i1))) {
          localStringBuffer.deleteCharAt(i1);
        } else {
          i1++;
        }
      }
      return localStringBuffer.toString();
    }
    return "";
  }
  
  private String jdMethod_if(Currency paramCurrency)
  {
    if ((paramCurrency != null) && (paramCurrency.isNegative())) {
      return "-" + a(paramCurrency);
    }
    return a(paramCurrency);
  }
  
  private String jdMethod_else()
  {
    return a(new DateTime());
  }
  
  private String a(DateTime paramDateTime)
  {
    return DateFormatUtil.getFormatter("yyMMdd").format(paramDateTime.getTime());
  }
  
  private String jdMethod_byte()
  {
    return jdMethod_if(new DateTime());
  }
  
  private String jdMethod_if(DateTime paramDateTime)
  {
    return DateFormatUtil.getFormatter("HHmm").format(paramDateTime.getTime());
  }
  
  protected int exportSummaryAndStatusFields(int paramInt, ArrayList paramArrayList, StringBuffer paramStringBuffer)
  {
    int i1 = 0;
    for (int i2 = 0; i2 < paramArrayList.size(); i2++)
    {
      SummaryValue localSummaryValue = (SummaryValue)paramArrayList.get(i2);
      a(localSummaryValue.getBAICode(), false);
      if ((localSummaryValue.getSummaryValue() instanceof Currency)) {
        a(jdMethod_if((Currency)localSummaryValue.getSummaryValue()), false);
      } else {
        a(localSummaryValue.getSummaryValue().toString(), false);
      }
      if (localSummaryValue.getItemCount() == -1) {
        a("", false);
      } else {
        a(localSummaryValue.getItemCount(), false);
      }
      if (i2 == paramArrayList.size() - 1) {
        a("", true);
      } else {
        a("", false);
      }
    }
    return i1;
  }
  
  protected class SummaryValue
  {
    private int jdField_if = -1;
    private Object a = null;
    private int jdField_do = -1;
    
    public SummaryValue(int paramInt1, Object paramObject, int paramInt2)
    {
      this.jdField_if = paramInt1;
      this.a = paramObject;
      this.jdField_do = paramInt2;
    }
    
    public SummaryValue(int paramInt, Object paramObject)
    {
      this(paramInt, paramObject, -1);
    }
    
    public int getBAICode()
    {
      return this.jdField_if;
    }
    
    public Object getSummaryValue()
    {
      return this.a;
    }
    
    public int getItemCount()
    {
      return this.jdField_do;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterBAIBalanceSummary
 * JD-Core Version:    0.7.0.1
 */