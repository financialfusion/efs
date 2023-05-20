package com.ffusion.services.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.UniqueIDGenerator;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.logging.DebugLog;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class ReportExporterBAICustomSummary
  extends ReportExporterBase
{
  private StringBuffer w = null;
  static final Locale R = ;
  String T = null;
  String N = null;
  String U = null;
  String W = null;
  String H = null;
  String u = null;
  String r = null;
  boolean F = true;
  String A = null;
  String y = null;
  String I = null;
  HashMap v = null;
  int x = 0;
  ArrayList D = null;
  int K = -1;
  int S = -1;
  int z = -1;
  int B = -1;
  int J = -1;
  int Y = -1;
  int G = -1;
  int C = 0;
  int Q = 0;
  Currency M = new Currency("0", R);
  int E = 0;
  int P = 0;
  Currency V = new Currency("0", R);
  int O = 0;
  Currency q = new Currency("0", R);
  boolean s = false;
  boolean L = false;
  boolean X = true;
  String t;
  
  public ReportExporterBAICustomSummary(ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    super(paramReportCriteria);
    a(paramHashMap);
    this.w = new StringBuffer();
    this.t = getEOLString();
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    String str = this.w.toString();
    this.w.setLength(0);
    return str;
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    a(this.w, "01", false);
    a(this.w, this.N, false);
    a(this.w, this.U, false);
    Calendar localCalendar = Calendar.getInstance();
    a(this.w, jdMethod_if(localCalendar), false);
    a(this.w, a(localCalendar), false);
    a(this.w, UniqueIDGenerator.getNextID(), false);
    a(this.w, "", false);
    a(this.w, "", false);
    a(this.w, "2", true);
    this.C += 1;
  }
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {
    if (this.L) {
      jdMethod_long();
    }
    if (this.s) {
      jdMethod_void();
    }
    a(this.w, "99", false);
    a(this.w, jdMethod_do(this.M), false);
    a(this.w, Integer.toString(this.Q), false);
    a(this.w, Integer.toString(++this.C), true);
  }
  
  protected void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException
  {}
  
  protected void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException
  {}
  
  protected void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    this.D.add(paramObject);
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    String str = (String)paramArrayList.get(0);
    if (this.x == 0) {
      this.v = new HashMap();
    }
    this.v.put(str, new Integer(this.x));
    this.x += 1;
  }
  
  protected void doInitReportResultTable()
    throws RptException
  {}
  
  protected void doFiniReportResultTable()
    throws RptException
  {}
  
  protected void doInitReportResultRow()
    throws RptException
  {
    if (this.x == 0) {
      this.D = new ArrayList();
    }
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    if (this.x != 0)
    {
      this.K = a("Account ID", this.v, true);
      this.S = a("Routing Number", this.v, true);
      this.z = a("Summary Date", this.v, true);
      this.B = a("Summary Type", this.v, true);
      this.J = a("Amount", this.v, true);
      this.Y = a("Data Classification", this.v, true);
      this.G = a("Currency Code", this.v, true);
      this.x = 0;
      return;
    }
    String str1 = (String)this.D.get(this.K);
    String str2 = (String)this.D.get(this.S);
    String str3 = jdMethod_if((DateTime)this.D.get(this.z));
    int i = 0;
    if ((!this.F) && ((!str1.equals(this.A)) || (!str2.equals(this.y))))
    {
      if ((this.A != null) && (this.y != null)) {
        i = 1;
      }
    }
    else if ((this.F) && (!str1.equals(this.A)) && (this.A != null)) {
      i = 1;
    }
    if (i != 0) {
      jdMethod_long();
    }
    int j = 0;
    if ((this.H.equals("1")) && ((!str2.equals(this.y)) || (!str3.equals(this.I))))
    {
      if ((this.A != null) && (this.y != null) && (this.I != null)) {
        j = 1;
      }
    }
    else if ((!this.H.equals("1")) && (!str3.equals(this.I)) && (this.I != null)) {
      j = 1;
    }
    if (j != 0)
    {
      if (this.L) {
        jdMethod_long();
      }
      jdMethod_void();
    }
    if (!this.s) {
      b();
    }
    if (!this.L) {
      jdMethod_null();
    }
    jdMethod_goto();
    this.A = str1;
    this.y = str2;
    this.I = str3;
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
  
  private void a(HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("AFFILIATE_BANK_ROUTING_NUMBER");
    String str2 = (String)paramHashMap.get("SENDER_ID_TYPE");
    if (str2 == null) {
      str2 = "1";
    }
    if (str2.equals("1")) {
      this.N = str1;
    } else if (str2.equals("2")) {
      this.N = ((String)paramHashMap.get("SENDER_ID_CUSTOM_DATA"));
    }
    String str3 = (String)paramHashMap.get("RECEIVER_ID_TYPE");
    if (str3 == null) {
      str3 = "1";
    }
    if (str3.equals("1")) {
      this.U = str1;
    } else if (str3.equals("2")) {
      this.U = ((String)paramHashMap.get("RECEIVER_ID_CUSTOM_DATA"));
    }
    String str4 = (String)paramHashMap.get("ULTIMATE_RECEIVER_ID_TYPE");
    if (str4 == null) {
      str4 = "1";
    }
    this.W = null;
    if (str4.equals("1")) {
      this.W = str1;
    } else if (str4.equals("2")) {
      this.W = ((String)paramHashMap.get("ULTIMATE_RECEIVER_ID_CUSTOM_DATA"));
    }
    this.H = ((String)paramHashMap.get("ORIGINATOR_ID_TYPE"));
    if (this.H == null) {
      this.H = "1";
    }
    this.u = null;
    if (this.H.equals("2")) {
      this.u = ((String)paramHashMap.get("ORIGINATOR_ID_CUSTOM_DATA"));
    }
    this.r = ((String)paramHashMap.get("CUSTOMER_ACCOUNT_OPTION"));
    if (this.r == null) {
      this.r = "1";
    }
    if (this.r.equals("1")) {
      this.F = true;
    } else if (this.r.equals("2")) {
      this.F = false;
    }
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
  
  private String jdMethod_do(Currency paramCurrency)
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
  
  private String jdMethod_if(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return DateFormatUtil.getFormatter("yyMMdd").format(paramCalendar.getTime());
  }
  
  private String a(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return DateFormatUtil.getFormatter("HHmm").format(paramCalendar.getTime());
  }
  
  private void a(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
  {
    if (paramString != null) {
      paramStringBuffer.append(paramString);
    }
    if (paramBoolean)
    {
      paramStringBuffer.append('/');
      paramStringBuffer.append(this.t);
    }
    else
    {
      paramStringBuffer.append(',');
    }
  }
  
  private int a(String paramString, HashMap paramHashMap, boolean paramBoolean)
    throws RptException
  {
    Integer localInteger = (Integer)paramHashMap.get(paramString);
    if (localInteger == null)
    {
      String str = "The column name \"" + paramString + "\" was not found in the report " + "to be exported.  This column name is required for the proper export " + "of this report in BAI 2 format.";
      if (paramBoolean) {
        DebugLog.log(str);
      }
      throw new RptException(100, str);
    }
    return localInteger.intValue();
  }
  
  private void b()
  {
    this.s = true;
    String str1 = null;
    if (this.H.equals("1")) {
      str1 = (String)this.D.get(this.S);
    } else {
      str1 = this.u;
    }
    DateTime localDateTime = (DateTime)this.D.get(this.z);
    a(this.w, "02", false);
    a(this.w, this.W, false);
    a(this.w, str1, false);
    a(this.w, "1", false);
    a(this.w, jdMethod_if(localDateTime), false);
    a(this.w, a(localDateTime), false);
    a(this.w, "USD", false);
    String str2 = (String)this.D.get(this.Y);
    if (str2.equals("P")) {
      a(this.w, "2", true);
    } else if (str2.equals("I")) {
      a(this.w, "3", true);
    } else {
      a(this.w, "2", true);
    }
    this.E += 1;
  }
  
  private void jdMethod_void()
  {
    this.s = false;
    a(this.w, "98", false);
    a(this.w, jdMethod_do(this.V), false);
    a(this.w, Integer.toString(this.P), false);
    a(this.w, Integer.toString(++this.E), true);
    this.C += this.E;
    this.M.addAmount(this.V);
    this.V = new Currency("0", R);
    this.Q += 1;
    this.E = 0;
    this.P = 0;
  }
  
  private void jdMethod_null()
  {
    this.L = true;
    String str = null;
    if (this.F) {
      str = (String)this.D.get(this.K);
    } else {
      str = (String)this.D.get(this.S) + ":" + (String)this.D.get(this.K);
    }
    this.P += 1;
    a(this.w, "03", false);
    a(this.w, str, false);
    a(this.w, (String)this.D.get(this.G), false);
    this.X = true;
    this.O += 1;
  }
  
  private void jdMethod_long()
  {
    this.L = false;
    this.w.append('/');
    this.w.append(this.t);
    a(this.w, "49", false);
    a(this.w, jdMethod_do(this.q), false);
    a(this.w, Integer.toString(++this.O), true);
    this.E += this.O;
    this.V.addAmount(this.q);
    this.q = new Currency("0", R);
    this.O = 0;
  }
  
  private void jdMethod_goto()
    throws RptException
  {
    if (!this.X) {
      this.w.append(',');
    } else {
      this.X = false;
    }
    a(this.w, ((Integer)this.D.get(this.B)).toString(), false);
    ArrayList localArrayList = a(this.v, this.D);
    Iterator localIterator = localArrayList.iterator();
    Currency localCurrency = (Currency)this.D.get(this.J);
    String str;
    if (localCurrency == null)
    {
      if (localIterator.hasNext())
      {
        str = (String)localIterator.next();
        a(this.w, str, false);
      }
      else
      {
        a(this.w, "", false);
      }
    }
    else
    {
      a(this.w, jdMethod_do(localCurrency), false);
      this.q.addAmount(localCurrency);
    }
    if (localIterator.hasNext())
    {
      str = (String)localIterator.next();
      a(this.w, str, false);
    }
    else
    {
      a(this.w, "", false);
    }
  }
  
  private ArrayList a(HashMap paramHashMap, ArrayList paramArrayList)
    throws RptException
  {
    int i = 0;
    int j = 1;
    ArrayList localArrayList = new ArrayList();
    while (i == 0)
    {
      int k = -1;
      try
      {
        k = a("ExtendABean" + j, paramHashMap, false);
      }
      catch (RptException localRptException)
      {
        i = 1;
        break;
      }
      String str = (String)paramArrayList.get(k);
      localArrayList.add(str);
      j++;
    }
    return localArrayList;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterBAICustomSummary
 * JD-Core Version:    0.7.0.1
 */