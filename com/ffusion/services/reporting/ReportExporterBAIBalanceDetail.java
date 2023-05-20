package com.ffusion.services.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.UniqueIDGenerator;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.DateTime;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

public class ReportExporterBAIBalanceDetail
  extends ReportExporterBase
{
  private StringBuffer bi = null;
  private HashMap be = null;
  private static String bo = System.getProperty("line.separator");
  private Locale bh = Locale.getDefault();
  private String bg;
  private ArrayList by;
  private HashMap bz = new HashMap();
  private int bu;
  private String bq;
  private DateTime bf;
  private String bk;
  private boolean bj = true;
  private int bs = 0;
  private int bx = 0;
  private Currency bw;
  private int bp = 0;
  private int bn = 0;
  private Currency bt;
  private int bm = 0;
  private Currency br;
  private String bd;
  boolean bv;
  String bl;
  protected String _currentAccountCurrencyCode = null;
  
  public ReportExporterBAIBalanceDetail(ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    super(paramReportCriteria);
    this.be = paramHashMap;
    this.bi = new StringBuffer();
    this.bw = new Currency("0", this.bh);
    this.bt = new Currency("0", this.bh);
    this.br = new Currency("0", this.bh);
    this.bg = getEOLString();
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    String str = this.bi.toString();
    this.bi.setLength(0);
    return str;
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    String str1 = (String)this.be.get("AFFILIATE_BANK_ROUTING_NUMBER");
    String str2 = (String)this.be.get("SENDER_ID_TYPE");
    if (str2 == null) {
      str2 = "1";
    }
    String str3 = null;
    if (str2.equals("1")) {
      str3 = str1;
    } else if (str2.equals("2")) {
      str3 = (String)this.be.get("SENDER_ID_CUSTOM_DATA");
    }
    String str4 = (String)this.be.get("RECEIVER_ID_TYPE");
    if (str4 == null) {
      str4 = "1";
    }
    String str5 = null;
    if (str4.equals("1")) {
      str5 = str1;
    } else if (str4.equals("2")) {
      str5 = (String)this.be.get("RECEIVER_ID_CUSTOM_DATA");
    }
    this.bs += 1;
    jdMethod_for(str3, str5);
    String str6 = (String)this.be.get("ULTIMATE_RECEIVER_ID_TYPE");
    if (str6 == null) {
      str6 = "1";
    }
    this.bd = null;
    if (str6.equals("1")) {
      this.bd = str1;
    } else if (str6.equals("2")) {
      this.bd = ((String)this.be.get("ULTIMATE_RECEIVER_ID_CUSTOM_DATA"));
    }
    this.bv = (!"2".equals(this.be.get("CUSTOMER_ACCOUNT_OPTION")));
    if ("2".equals(this.be.get("ORIGINATOR_ID_TYPE"))) {
      this.bl = ((String)this.be.get("ORIGINATOR_ID_CUSTOM_DATA"));
    }
  }
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {
    h();
    i();
    this.bs += 1;
    jdMethod_for(this.bw, this.bx, this.bs);
  }
  
  protected void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException
  {
    this.bk = paramString1;
  }
  
  protected void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException
  {}
  
  protected void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    this.by.add(paramObject);
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    this.bz.put(paramArrayList.get(0), new Integer(this.bu++));
  }
  
  protected void doInitReportResultTable()
    throws RptException
  {
    this.by = null;
  }
  
  protected void doFiniReportResultTable()
    throws RptException
  {}
  
  protected void doInitReportResultRow()
    throws RptException
  {
    if (this.by == null)
    {
      this.bz = new HashMap();
      this.bu = 0;
    }
    else
    {
      this.by.clear();
    }
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    if (this.by == null) {
      this.by = new ArrayList(this.bz.size());
    } else {
      try
      {
        if ("Transactions".equals(this.bk)) {
          n();
        } else if ("AccountSummary".equals(this.bk)) {
          k();
        }
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
  
  private void i()
  {
    if (this.bf != null)
    {
      this.bs += 1;
      this.bp += 1;
      jdMethod_do(this.bt, this.bn, this.bp);
      this.bt.setAmount("0");
      this.bn = 0;
      this.bp = 0;
    }
  }
  
  private void h()
  {
    if (!this.bj)
    {
      this.bj = true;
      this.bs += 1;
      this.bp += 1;
      this.bm += 1;
      jdMethod_if(this.br, this.bm);
      this.br.setAmount("0");
      this.bm = 0;
    }
  }
  
  private void k()
    throws RptException
  {
    String str1;
    if (this.bl != null) {
      str1 = this.bl;
    } else {
      str1 = (String)jdMethod_char("RoutingNum");
    }
    DateTime localDateTime1 = (DateTime)jdMethod_char("Date");
    String str2 = (String)jdMethod_char("DataClassification");
    DateTime localDateTime2 = new DateTime(localDateTime1, localDateTime1.getLocale());
    localDateTime2.set(13, 0);
    localDateTime2.set(14, 0);
    h();
    this.bj = false;
    if ((!str1.equals(this.bq)) || (!localDateTime2.equals(this.bf)))
    {
      i();
      this.bq = str1;
      this.bf = localDateTime2;
      this.bs += 1;
      this.bp += 1;
      jdMethod_if(jdMethod_if(localDateTime1), a(localDateTime1), str2, this.bd, str1);
      this.bx += 1;
    }
    this.bn += 1;
    this.bs += 1;
    this.bp += 1;
    this.bm += 1;
    Currency localCurrency = l();
    this.br.addAmount(localCurrency);
    this.bt.addAmount(localCurrency);
    this.bw.addAmount(localCurrency);
  }
  
  private void n()
    throws RptException
  {
    this.bs += 1;
    this.bp += 1;
    this.bm += 1;
    jdMethod_if("16", false);
    Integer localInteger = (Integer)jdMethod_char("Sub Type");
    jdMethod_if(localInteger.intValue(), false);
    Currency localCurrency1 = (Currency)jdMethod_char("Amount");
    if (localCurrency1 != null)
    {
      if (localCurrency1.isNegative()) {
        localCurrency1.setAmount(localCurrency1.getAmountValue().negate());
      }
      jdMethod_if(jdMethod_try(localCurrency1), false);
      this.br.addAmount(localCurrency1);
      this.bt.addAmount(localCurrency1);
      this.bw.addAmount(localCurrency1);
    }
    else
    {
      ArrayList localArrayList = m();
      if ((localArrayList.isEmpty()) || (localArrayList.get(0) == null)) {
        jdMethod_if("", false);
      } else {
        jdMethod_if(localArrayList.get(0).toString(), false);
      }
    }
    int i = 0;
    Currency localCurrency2 = (Currency)jdMethod_char("Immediate Avalaible Amount");
    Currency localCurrency3 = (Currency)jdMethod_char("One Day Available Amount");
    Currency localCurrency4 = (Currency)jdMethod_char("More Than One Day Available Amount");
    if (localCurrency2 != null) {
      i++;
    } else {
      localCurrency2 = new Currency("0", this._currentAccountCurrencyCode, Locale.getDefault());
    }
    if (localCurrency3 != null) {
      i++;
    } else {
      localCurrency3 = new Currency("0", this._currentAccountCurrencyCode, Locale.getDefault());
    }
    if (localCurrency4 != null) {
      i++;
    } else {
      localCurrency4 = new Currency("0", this._currentAccountCurrencyCode, Locale.getDefault());
    }
    if (i > 0)
    {
      jdMethod_if("S", false);
      jdMethod_if(jdMethod_try(localCurrency2), false);
      jdMethod_if(jdMethod_try(localCurrency3), false);
      jdMethod_if(jdMethod_try(localCurrency4), false);
    }
    else
    {
      jdMethod_if("", false);
    }
    String str = (String)jdMethod_char("Bank Reference Number");
    if (str != null) {
      jdMethod_if(str, false);
    } else {
      jdMethod_if("", false);
    }
    str = (String)jdMethod_char("Customer Reference Number");
    if (str != null) {
      jdMethod_if(str, false);
    } else {
      jdMethod_if("", false);
    }
    str = (String)jdMethod_char("Description");
    if (str != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      int j = exportTransactionTextColumn(this.bi.length(), str, localStringBuffer);
      this.bs += j;
      this.bp += j;
      this.bm += j;
      a(localStringBuffer.toString(), true, true);
    }
    else
    {
      a("", true, true);
    }
  }
  
  private Object jdMethod_char(String paramString)
    throws RptException
  {
    Integer localInteger = (Integer)this.bz.get(paramString);
    if ((localInteger == null) || (localInteger.intValue() < 0) || (localInteger.intValue() >= this.by.size()))
    {
      String str = "The column name \"" + paramString + "\" was not found in the report " + "to be exported.  This column name is required for the proper export " + "of this report in BAI 2 format.";
      throw new RptException(100, str);
    }
    return this.by.get(localInteger.intValue());
  }
  
  private void jdMethod_for(String paramString1, String paramString2)
  {
    jdMethod_if("01", false);
    jdMethod_if(paramString1, false);
    jdMethod_if(paramString2, false);
    jdMethod_if(o(), false);
    jdMethod_if(j(), false);
    jdMethod_if(UniqueIDGenerator.getNextID(), false);
    jdMethod_if("", false);
    jdMethod_if("", false);
    jdMethod_if("2", true);
  }
  
  private void jdMethod_for(Currency paramCurrency, int paramInt1, int paramInt2)
  {
    jdMethod_if("99", false);
    jdMethod_if(jdMethod_byte(paramCurrency), false);
    jdMethod_if(paramInt1, false);
    jdMethod_if(paramInt2, true);
  }
  
  private void jdMethod_if(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    jdMethod_if("02", false);
    jdMethod_if(paramString4, false);
    jdMethod_if(paramString5, false);
    jdMethod_if("1", false);
    jdMethod_if(paramString1, false);
    jdMethod_if(paramString2, false);
    jdMethod_if("USD", false);
    if (paramString3.equals("P")) {
      jdMethod_if("2", true);
    } else if (paramString3.equals("I")) {
      jdMethod_if("3", true);
    } else {
      jdMethod_if("2", true);
    }
  }
  
  private void jdMethod_do(Currency paramCurrency, int paramInt1, int paramInt2)
  {
    jdMethod_if("98", false);
    jdMethod_if(jdMethod_byte(paramCurrency), false);
    jdMethod_if(paramInt1, false);
    jdMethod_if(paramInt2, true);
  }
  
  private Currency l()
    throws RptException
  {
    String str1 = (String)jdMethod_char("AccountId");
    if (!this.bv)
    {
      String str2 = (String)jdMethod_char("RoutingNum");
      if (str2 != null)
      {
        localObject1 = new StringBuffer(str2.length() + 1 + str1.length());
        ((StringBuffer)localObject1).append(str2).append(':').append(str1);
        str1 = ((StringBuffer)localObject1).toString();
      }
    }
    this._currentAccountCurrencyCode = ((String)jdMethod_char("CurrencyCode"));
    int i = this.bi.length();
    jdMethod_if("03", false);
    jdMethod_if(str1, false);
    jdMethod_if(this._currentAccountCurrencyCode, false);
    Object localObject1 = new ArrayList();
    Set localSet = this.bz.entrySet();
    Iterator localIterator = localSet.iterator();
    Object localObject4;
    while (localIterator.hasNext())
    {
      localObject2 = (Map.Entry)localIterator.next();
      String str3 = (String)((Map.Entry)localObject2).getKey();
      if (str3.startsWith("BAI_"))
      {
        Object localObject3 = jdMethod_char(str3);
        String str4 = str3.substring("BAI_".length());
        localObject4 = "ITEMCOUNT_" + str4;
        Object localObject5 = null;
        if (this.bz.containsKey(localObject4)) {
          localObject5 = jdMethod_char((String)localObject4);
        }
        a(str4, localObject3, localObject5, (ArrayList)localObject1);
      }
    }
    Object localObject2 = new Currency("0", this.bh);
    if (((ArrayList)localObject1).size() == 0)
    {
      jdMethod_if("", false);
      jdMethod_if("", false);
      jdMethod_if("", false);
      jdMethod_if("", true);
    }
    else
    {
      int j = this.bi.length() - i;
      int k = exportSummaryAndStatusFields(j, (ArrayList)localObject1, this.bi);
      this.bs += k;
      this.bp += k;
      this.bm += k;
      for (int m = 0; m < ((ArrayList)localObject1).size(); m++)
      {
        localObject4 = (SummaryValue)((ArrayList)localObject1).get(m);
        if ((((SummaryValue)localObject4).getSummaryValue() instanceof Currency)) {
          ((Currency)localObject2).addAmount((Currency)((SummaryValue)localObject4).getSummaryValue());
        }
      }
    }
    return localObject2;
  }
  
  private void a(String paramString, Object paramObject1, Object paramObject2, ArrayList paramArrayList)
    throws RptException
  {
    if (paramObject1 != null) {
      if (paramObject2 == null) {
        paramArrayList.add(new SummaryValue(Integer.parseInt(paramString), paramObject1));
      } else {
        paramArrayList.add(new SummaryValue(Integer.parseInt(paramString), paramObject1, ((Integer)paramObject2).intValue()));
      }
    }
  }
  
  private void jdMethod_if(Currency paramCurrency, int paramInt)
  {
    jdMethod_if("49", false);
    jdMethod_if(jdMethod_byte(paramCurrency), false);
    jdMethod_if(paramInt, true);
  }
  
  private void a(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = this.bi.length();
    if (paramString != null) {
      this.bi.append(paramString);
    }
    if (!paramBoolean1) {
      this.bi.append(',');
    } else if (paramBoolean2)
    {
      if ((paramString == null) || (paramString.equals(""))) {
        this.bi.append('/').append(this.bg);
      } else {
        this.bi.append(this.bg);
      }
    }
    else {
      this.bi.append('/').append(this.bg);
    }
  }
  
  private void jdMethod_if(String paramString, boolean paramBoolean)
  {
    a(paramString, paramBoolean, false);
  }
  
  private void jdMethod_if(int paramInt, boolean paramBoolean)
  {
    jdMethod_if(Integer.toString(paramInt), paramBoolean);
  }
  
  private String jdMethod_try(Currency paramCurrency)
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
  
  private String jdMethod_byte(Currency paramCurrency)
  {
    if ((paramCurrency != null) && (paramCurrency.isNegative())) {
      return "-" + jdMethod_try(paramCurrency);
    }
    return jdMethod_try(paramCurrency);
  }
  
  private String o()
  {
    return jdMethod_if(new DateTime());
  }
  
  private String jdMethod_if(DateTime paramDateTime)
  {
    return DateFormatUtil.getFormatter("yyMMdd").format(paramDateTime.getTime());
  }
  
  private String j()
  {
    return a(new DateTime());
  }
  
  private String a(DateTime paramDateTime)
  {
    return DateFormatUtil.getFormatter("HHmm").format(paramDateTime.getTime());
  }
  
  private ArrayList m()
    throws RptException
  {
    int i = 1;
    ArrayList localArrayList = new ArrayList();
    try
    {
      for (;;)
      {
        String str = "ExtendABean" + i;
        i++;
        if (!this.bz.containsKey(str)) {
          break;
        }
        localArrayList.add(jdMethod_char(str));
      }
    }
    catch (RptException localRptException) {}
    return localArrayList;
  }
  
  protected int exportTransactionTextColumn(int paramInt, String paramString, StringBuffer paramStringBuffer)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return 0;
    }
    int i = 0;
    int j = 0;
    for (int k = paramString.indexOf(bo, j); k != -1; k = paramString.indexOf(bo, j))
    {
      paramStringBuffer.append(paramString.substring(j, k));
      j = k + bo.length();
      if (j < paramString.length())
      {
        paramStringBuffer.append(bo + "88,");
        i++;
      }
    }
    paramStringBuffer.append(paramString.substring(j, paramString.length()));
    return i;
  }
  
  protected int exportSummaryAndStatusFields(int paramInt, ArrayList paramArrayList, StringBuffer paramStringBuffer)
  {
    int i = 0;
    for (int j = 0; j < paramArrayList.size(); j++)
    {
      SummaryValue localSummaryValue = (SummaryValue)paramArrayList.get(j);
      String str = new Integer(localSummaryValue.getBAICode()).toString();
      if (str.length() == 2) {
        str = "0" + str;
      }
      jdMethod_if(str, false);
      if ((localSummaryValue.getSummaryValue() instanceof Currency)) {
        jdMethod_if(jdMethod_byte((Currency)localSummaryValue.getSummaryValue()), false);
      } else {
        jdMethod_if(localSummaryValue.getSummaryValue().toString(), false);
      }
      if (localSummaryValue.getItemCount() == -1) {
        jdMethod_if("", false);
      } else {
        jdMethod_if(localSummaryValue.getItemCount(), false);
      }
      if (j == paramArrayList.size() - 1) {
        jdMethod_if("", true);
      } else {
        jdMethod_if("", false);
      }
    }
    return i;
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
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterBAIBalanceDetail
 * JD-Core Version:    0.7.0.1
 */