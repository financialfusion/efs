package com.ffusion.services.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.UniqueIDGenerator;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class ReportExporterBAITransactions
  extends ReportExporterBase
{
  private StringBuffer aj = null;
  private static String aH = System.getProperty("line.separator");
  static final Locale av = Locale.getDefault();
  String az = null;
  String ar = null;
  String ao = null;
  String aG = null;
  String al = null;
  String aK = null;
  String ak = null;
  boolean am = true;
  String ad = null;
  String as = null;
  String aa = null;
  HashMap ai = null;
  int aq = 0;
  ArrayList aD = null;
  int aI = -1;
  int aE = -1;
  int aJ = -1;
  int af = -1;
  int aB = -1;
  int an = -1;
  int ap = -1;
  int ay = -1;
  int ac = -1;
  int aC = -1;
  int ah = -1;
  int aw = -1;
  int aF = -1;
  int aL = 0;
  int aO = 0;
  Currency at = new Currency("0", av);
  int ae = 0;
  int aA = 0;
  Currency ab = new Currency("0", av);
  int ax = 0;
  Currency aN = new Currency("0", av);
  boolean ag = false;
  boolean au = false;
  String aM;
  protected String _currentAccountCurrencyCode = null;
  
  public ReportExporterBAITransactions(ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    super(paramReportCriteria);
    jdMethod_if(paramHashMap);
    this.aj = new StringBuffer();
    this.aM = getEOLString();
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    String str = this.aj.toString();
    this.aj.setLength(0);
    return str;
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    jdMethod_if(this.aj, "01", false);
    jdMethod_if(this.aj, this.ar, false);
    jdMethod_if(this.aj, this.ao, false);
    Calendar localCalendar = Calendar.getInstance();
    jdMethod_if(this.aj, jdMethod_for(localCalendar), false);
    jdMethod_if(this.aj, jdMethod_do(localCalendar), false);
    jdMethod_if(this.aj, UniqueIDGenerator.getNextID(), false);
    jdMethod_if(this.aj, "", false);
    jdMethod_if(this.aj, "", false);
    jdMethod_if(this.aj, "2", true);
    this.aL += 1;
  }
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {
    if (this.au) {
      d();
    }
    if (this.ag) {
      f();
    }
    jdMethod_if(this.aj, "99", false);
    jdMethod_if(this.aj, jdMethod_int(this.at), false);
    jdMethod_if(this.aj, Integer.toString(this.aO), false);
    jdMethod_if(this.aj, Integer.toString(++this.aL), true);
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
    this.aD.add(paramObject);
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    String str = (String)paramArrayList.get(0);
    if (this.aq == 0) {
      this.ai = new HashMap();
    }
    this.ai.put(str, new Integer(this.aq));
    this.aq += 1;
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
    if (this.aq == 0) {
      this.aD = new ArrayList();
    }
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    if (this.aq != 0)
    {
      this.aI = jdMethod_if("Account ID", this.ai, false);
      this.aE = jdMethod_if("Routing Number", this.ai, false);
      this.aJ = jdMethod_if("Processing Date", this.ai, false);
      this.af = jdMethod_if("Sub Type", this.ai, false);
      this.aB = jdMethod_if("Currency Code", this.ai, false);
      this.an = jdMethod_if("Data Classification", this.ai, false);
      this.ap = jdMethod_if("Amount", this.ai, false);
      this.ay = jdMethod_if("Bank Reference Number", this.ai, false);
      this.ac = jdMethod_if("Customer Reference Number", this.ai, false);
      this.aC = jdMethod_if("Description", this.ai, false);
      this.ah = jdMethod_if("Immediate Avalaible Amount", this.ai, false);
      this.aw = jdMethod_if("One Day Available Amount", this.ai, false);
      this.aF = jdMethod_if("More Than One Day Available Amount", this.ai, false);
      this.aq = 0;
      return;
    }
    String str1 = (String)this.aD.get(this.aI);
    String str2 = (String)this.aD.get(this.aE);
    String str3 = jdMethod_for((DateTime)this.aD.get(this.aJ));
    int i = 0;
    if ((!this.am) && ((!str1.equals(this.ad)) || (!str2.equals(this.as))))
    {
      if ((this.ad != null) && (this.as != null)) {
        i = 1;
      }
    }
    else if ((this.am) && (!str1.equals(this.ad)) && (this.ad != null)) {
      i = 1;
    }
    if (i != 0) {
      d();
    }
    int j = 0;
    if ((this.al.equals("1")) && ((!str2.equals(this.as)) || (!str3.equals(this.aa))))
    {
      if ((this.ad != null) && (this.as != null) && (this.aa != null)) {
        j = 1;
      }
    }
    else if ((!this.al.equals("1")) && (!str3.equals(this.aa)) && (this.aa != null)) {
      j = 1;
    }
    if (j != 0)
    {
      if (this.au) {
        d();
      }
      f();
    }
    if (!this.ag) {
      g();
    }
    if (!this.au) {
      e();
    }
    c();
    this.ad = str1;
    this.as = str2;
    this.aa = str3;
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
  
  private void jdMethod_if(HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("AFFILIATE_BANK_ROUTING_NUMBER");
    String str2 = (String)paramHashMap.get("SENDER_ID_TYPE");
    if (str2 == null) {
      str2 = "1";
    }
    if (str2.equals("1")) {
      this.ar = str1;
    } else if (str2.equals("2")) {
      this.ar = ((String)paramHashMap.get("SENDER_ID_CUSTOM_DATA"));
    }
    String str3 = (String)paramHashMap.get("RECEIVER_ID_TYPE");
    if (str3 == null) {
      str3 = "1";
    }
    if (str3.equals("1")) {
      this.ao = str1;
    } else if (str3.equals("2")) {
      this.ao = ((String)paramHashMap.get("RECEIVER_ID_CUSTOM_DATA"));
    }
    String str4 = (String)paramHashMap.get("ULTIMATE_RECEIVER_ID_TYPE");
    if (str4 == null) {
      str4 = "1";
    }
    this.aG = null;
    if (str4.equals("1")) {
      this.aG = str1;
    } else if (str4.equals("2")) {
      this.aG = ((String)paramHashMap.get("ULTIMATE_RECEIVER_ID_CUSTOM_DATA"));
    }
    this.al = ((String)paramHashMap.get("ORIGINATOR_ID_TYPE"));
    if (this.al == null) {
      this.al = "1";
    }
    this.aK = null;
    if (this.al.equals("2")) {
      this.aK = ((String)paramHashMap.get("ORIGINATOR_ID_CUSTOM_DATA"));
    }
    this.ak = ((String)paramHashMap.get("CUSTOMER_ACCOUNT_OPTION"));
    if (this.ak == null) {
      this.ak = "1";
    }
    if (this.ak.equals("1")) {
      this.am = true;
    } else if (this.ak.equals("2")) {
      this.am = false;
    }
  }
  
  private String jdMethod_new(Currency paramCurrency)
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
  
  private String jdMethod_for(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return DateFormatUtil.getFormatter("yyMMdd").format(paramCalendar.getTime());
  }
  
  private String jdMethod_do(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return DateFormatUtil.getFormatter("HHmm").format(paramCalendar.getTime());
  }
  
  private void jdMethod_if(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
  {
    a(paramStringBuffer, paramString, paramBoolean, false);
  }
  
  private void a(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramString != null) {
      paramStringBuffer.append(paramString);
    }
    if (paramBoolean1)
    {
      if (paramBoolean2)
      {
        if ((paramString == null) || (paramString.equals("")))
        {
          paramStringBuffer.append('/');
          paramStringBuffer.append(this.aM);
        }
        else
        {
          paramStringBuffer.append(this.aM);
        }
      }
      else
      {
        paramStringBuffer.append('/');
        paramStringBuffer.append(this.aM);
      }
    }
    else {
      paramStringBuffer.append(',');
    }
  }
  
  private int jdMethod_if(String paramString, HashMap paramHashMap, boolean paramBoolean)
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
  
  private void g()
  {
    this.ag = true;
    String str1 = null;
    if (this.al.equals("1")) {
      str1 = (String)this.aD.get(this.aE);
    } else {
      str1 = this.aK;
    }
    DateTime localDateTime = (DateTime)this.aD.get(this.aJ);
    jdMethod_if(this.aj, "02", false);
    jdMethod_if(this.aj, this.aG, false);
    jdMethod_if(this.aj, str1, false);
    jdMethod_if(this.aj, "1", false);
    jdMethod_if(this.aj, jdMethod_for(localDateTime), false);
    jdMethod_if(this.aj, jdMethod_do(localDateTime), false);
    jdMethod_if(this.aj, "USD", false);
    String str2 = (String)this.aD.get(this.an);
    if (str2.equals("P")) {
      jdMethod_if(this.aj, "2", true);
    } else if (str2.equals("I")) {
      jdMethod_if(this.aj, "3", true);
    } else {
      jdMethod_if(this.aj, "2", true);
    }
    this.ae += 1;
  }
  
  private void f()
  {
    this.ag = false;
    jdMethod_if(this.aj, "98", false);
    jdMethod_if(this.aj, jdMethod_int(this.ab), false);
    jdMethod_if(this.aj, Integer.toString(this.aA), false);
    jdMethod_if(this.aj, Integer.toString(++this.ae), true);
    this.aL += this.ae;
    this.at.addAmount(this.ab);
    this.ab = new Currency("0", av);
    this.aO += 1;
    this.ae = 0;
    this.aA = 0;
  }
  
  private void e()
  {
    this.au = true;
    this._currentAccountCurrencyCode = ((String)this.aD.get(this.aB));
    String str = null;
    if (this.am) {
      str = (String)this.aD.get(this.aI);
    } else {
      str = (String)this.aD.get(this.aE) + ":" + (String)this.aD.get(this.aI);
    }
    this.aA += 1;
    jdMethod_if(this.aj, "03", false);
    jdMethod_if(this.aj, str, false);
    jdMethod_if(this.aj, (String)this.aD.get(this.aB), false);
    jdMethod_if(this.aj, "", false);
    jdMethod_if(this.aj, "", false);
    jdMethod_if(this.aj, "", false);
    jdMethod_if(this.aj, "", true);
    this.ax += 1;
  }
  
  private void d()
  {
    this.au = false;
    jdMethod_if(this.aj, "49", false);
    jdMethod_if(this.aj, jdMethod_int(this.aN), false);
    jdMethod_if(this.aj, Integer.toString(++this.ax), true);
    this.ae += this.ax;
    this.ab.addAmount(this.aN);
    this.aN = new Currency("0", av);
    this.ax = 0;
  }
  
  private void c()
    throws RptException
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    jdMethod_if(localStringBuffer1, "16", false);
    Integer localInteger = (Integer)this.aD.get(this.af);
    String str1 = null;
    if (localInteger.intValue() == 0)
    {
      localCurrency1 = (Currency)this.aD.get(this.ap);
      if (localCurrency1 != null)
      {
        if (localCurrency1.isNegative()) {
          str1 = "409";
        } else {
          str1 = "108";
        }
      }
      else {
        str1 = "";
      }
    }
    else
    {
      str1 = localInteger.toString();
    }
    jdMethod_if(localStringBuffer1, str1, false);
    Currency localCurrency1 = (Currency)this.aD.get(this.ap);
    if (localCurrency1 == null)
    {
      ArrayList localArrayList = jdMethod_if(this.ai, this.aD);
      if (localArrayList.size() == 0)
      {
        jdMethod_if(localStringBuffer1, "", false);
      }
      else
      {
        localObject1 = localArrayList.iterator();
        localObject2 = null;
        if (((Iterator)localObject1).hasNext()) {
          localObject2 = (String)((Iterator)localObject1).next();
        } else {
          localObject2 = "";
        }
        jdMethod_if(localStringBuffer1, (String)localObject2, false);
      }
    }
    else
    {
      jdMethod_if(localStringBuffer1, jdMethod_new(localCurrency1), false);
      if (localCurrency1.isNegative()) {
        this.aN.addAmount(localCurrency1.getAmountValue().negate());
      } else {
        this.aN.addAmount(localCurrency1);
      }
    }
    int i = 0;
    Object localObject1 = null;
    Object localObject2 = null;
    Currency localCurrency2 = null;
    if (this.ah != -1)
    {
      localObject1 = (Currency)this.aD.get(this.ah);
      if (localObject1 != null) {
        i++;
      } else {
        localObject1 = new Currency("0", this._currentAccountCurrencyCode, Locale.getDefault());
      }
    }
    if (this.aw != -1)
    {
      localObject2 = (Currency)this.aD.get(this.aw);
      if (localObject2 != null) {
        i++;
      } else {
        localObject2 = new Currency("0", this._currentAccountCurrencyCode, Locale.getDefault());
      }
    }
    if (this.aF != -1)
    {
      localCurrency2 = (Currency)this.aD.get(this.aF);
      if (localCurrency2 != null) {
        i++;
      } else {
        localCurrency2 = new Currency("0", this._currentAccountCurrencyCode, Locale.getDefault());
      }
    }
    if (i > 0)
    {
      jdMethod_if(localStringBuffer1, "S", false);
      jdMethod_if(localStringBuffer1, jdMethod_new((Currency)localObject1), false);
      jdMethod_if(localStringBuffer1, jdMethod_new((Currency)localObject2), false);
      jdMethod_if(localStringBuffer1, jdMethod_new(localCurrency2), false);
    }
    else
    {
      jdMethod_if(localStringBuffer1, "", false);
    }
    String str2 = (String)this.aD.get(this.ay);
    if (str2 == null) {
      str2 = "";
    }
    jdMethod_if(localStringBuffer1, str2, false);
    String str3 = (String)this.aD.get(this.ac);
    if (str3 == null) {
      str3 = "";
    }
    jdMethod_if(localStringBuffer1, str3, false);
    String str4 = (String)this.aD.get(this.aC);
    if (str4 == null)
    {
      a(localStringBuffer1, "", true, true);
    }
    else
    {
      StringBuffer localStringBuffer2 = new StringBuffer();
      int j = exportTransactionTextColumn(localStringBuffer1.length(), str4, localStringBuffer2);
      this.ax += j;
      a(localStringBuffer1, localStringBuffer2.toString(), true, true);
    }
    this.aj.append(localStringBuffer1.toString());
    this.ax += 1;
  }
  
  private ArrayList jdMethod_if(HashMap paramHashMap, ArrayList paramArrayList)
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
        k = jdMethod_if("ExtendABean" + j, paramHashMap, false);
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
  
  protected int exportTransactionTextColumn(int paramInt, String paramString, StringBuffer paramStringBuffer)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return 0;
    }
    int i = 0;
    int j = 0;
    for (int k = paramString.indexOf(aH, j); k != -1; k = paramString.indexOf(aH, j))
    {
      paramStringBuffer.append(paramString.substring(j, k));
      j = k + aH.length();
      if (j < paramString.length())
      {
        paramStringBuffer.append(this.aM + "88,");
        i++;
      }
    }
    paramStringBuffer.append(paramString.substring(j, paramString.length()));
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterBAITransactions
 * JD-Core Version:    0.7.0.1
 */