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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class CustomSummaryRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String CUSTOM_SUMMARY_RPT = "CUSTOM_SUMMARY_RPT";
  public static final String CUSTOM_SUMMARY_DISPLAY_CUSTOM_FIELD_NAMES = "CUSTOM_SUMMARY_DISPLAY_FIELD_NAMES";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private ExtendedAccountSummaries SA = null;
  private Accounts SB = null;
  
  public CustomSummaryRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof CustomSummaryRpt))) {
      return false;
    }
    boolean bool = false;
    CustomSummaryRpt localCustomSummaryRpt = (CustomSummaryRpt)paramObject;
    ExtendedAccountSummaries localExtendedAccountSummaries = localCustomSummaryRpt.getExtendedAccountSummaries();
    if (this.SA == null) {
      bool = localExtendedAccountSummaries == null;
    } else {
      bool = this.SA.equals(localExtendedAccountSummaries);
    }
    return bool;
  }
  
  public void setExtendedAccountSummaries(ExtendedAccountSummaries paramExtendedAccountSummaries)
  {
    this.SA = paramExtendedAccountSummaries;
  }
  
  public ExtendedAccountSummaries getExtendedAccountSummaries()
  {
    return this.SA;
  }
  
  public void setAccounts(Accounts paramAccounts)
  {
    this.SB = paramAccounts;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    String str = (String)paramHashMap.get("CUSTOM_SUMMARY_DISPLAY_FIELD_NAMES");
    boolean bool = true;
    if (str != null) {
      bool = new Boolean(str).booleanValue();
    }
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',', bool);
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t', bool);
    } else if (paramString.equals("BAI2")) {
      localStringBuffer = jdMethod_try(paramHashMap);
    }
    return localStringBuffer;
  }
  
  private void jdMethod_char(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null)
    {
      paramCurrency.setFormat("DECIMAL");
      paramStringBuffer.append(paramCurrency.toString());
    }
  }
  
  private StringBuffer jdMethod_try(HashMap paramHashMap)
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
    Object localObject5;
    for (int j = 0; j < this.SA.size(); j++)
    {
      ExtendedAccountSummary localExtendedAccountSummary1 = (ExtendedAccountSummary)this.SA.get(j);
      if (str8.equals("1")) {
        str9 = localExtendedAccountSummary1.getRoutingNumber();
      }
      localObject1 = (HashMap)localHashMap1.get(str9);
      if (localObject1 == null)
      {
        localObject1 = new HashMap();
        localHashMap1.put(str9, localObject1);
      }
      localObject2 = new DateTime(localExtendedAccountSummary1.getSummaryDate(), localExtendedAccountSummary1.getSummaryDate().getLocale());
      ((DateTime)localObject2).set(13, 0);
      ((DateTime)localObject2).set(14, 0);
      localObject3 = (HashMap)((HashMap)localObject1).get(localObject2);
      if (localObject3 == null)
      {
        localObject3 = new HashMap();
        ((HashMap)localObject1).put(localObject2, localObject3);
      }
      localObject4 = localExtendedAccountSummary1.getAccountID();
      localObject5 = (ArrayList)((HashMap)localObject3).get(localObject4);
      if (localObject5 == null)
      {
        localObject5 = new ArrayList();
        ((HashMap)localObject3).put(localObject4, localObject5);
      }
      ((ArrayList)localObject5).add(localExtendedAccountSummary1);
    }
    j = 0;
    int k = 0;
    Object localObject1 = new Currency("0", this.locale);
    jdMethod_new(localStringBuffer, "01", false);
    jdMethod_new(localStringBuffer, str3, false);
    jdMethod_new(localStringBuffer, str5, false);
    Object localObject2 = Calendar.getInstance();
    jdMethod_new(localStringBuffer, jdMethod_try((Calendar)localObject2), false);
    jdMethod_new(localStringBuffer, jdMethod_new((Calendar)localObject2), false);
    jdMethod_new(localStringBuffer, UniqueIDGenerator.getNextID(), false);
    jdMethod_new(localStringBuffer, "", false);
    jdMethod_new(localStringBuffer, "", false);
    jdMethod_new(localStringBuffer, "2", true);
    j++;
    Object localObject3 = localHashMap1.keySet();
    Object localObject4 = ((Set)localObject3).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      localObject5 = (String)((Iterator)localObject4).next();
      HashMap localHashMap2 = (HashMap)localHashMap1.get(localObject5);
      Set localSet1 = localHashMap2.keySet();
      ArrayList localArrayList1 = new ArrayList(localSet1);
      Qsort.sortSortables(localArrayList1, "", 1);
      Iterator localIterator1 = localArrayList1.iterator();
      while (localIterator1.hasNext())
      {
        DateTime localDateTime = (DateTime)localIterator1.next();
        HashMap localHashMap3 = (HashMap)localHashMap2.get(localDateTime);
        k++;
        int m = 0;
        int n = 0;
        Currency localCurrency1 = new Currency("0", this.locale);
        jdMethod_new(localStringBuffer, "02", false);
        jdMethod_new(localStringBuffer, str7, false);
        jdMethod_new(localStringBuffer, (String)localObject5, false);
        jdMethod_new(localStringBuffer, "1", false);
        jdMethod_new(localStringBuffer, jdMethod_try(localDateTime), false);
        jdMethod_new(localStringBuffer, jdMethod_new(localDateTime), false);
        jdMethod_new(localStringBuffer, "USD", false);
        ReportCriteria localReportCriteria = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
        Properties localProperties = localReportCriteria.getSearchCriteria();
        String str11 = (String)localProperties.get("DataClassification");
        if (str11 == null) {
          str11 = "P";
        }
        if (str11.equals("P")) {
          jdMethod_new(localStringBuffer, "2", true);
        } else if (str11.equals("I")) {
          jdMethod_new(localStringBuffer, "3", true);
        } else {
          jdMethod_new(localStringBuffer, "2", true);
        }
        m++;
        Set localSet2 = localHashMap3.keySet();
        Iterator localIterator2 = localSet2.iterator();
        while (localIterator2.hasNext())
        {
          String str12 = (String)localIterator2.next();
          n++;
          Currency localCurrency2 = new Currency("0", this.locale);
          String str13 = null;
          if (i != 0)
          {
            str13 = str12;
          }
          else if (this.SB != null)
          {
            localObject6 = this.SB.getByID(str12);
            if (localObject6 == null) {
              str13 = str12;
            } else {
              str13 = ((Account)localObject6).getRoutingNum() + ":" + ((Account)localObject6).getID();
            }
          }
          else
          {
            str13 = str12;
          }
          jdMethod_new(localStringBuffer, "03", false);
          jdMethod_new(localStringBuffer, str13, false);
          if (this.SB != null)
          {
            localObject6 = this.SB.getByID(str12);
            if (localObject6 == null) {
              jdMethod_new(localStringBuffer, "", false);
            } else {
              jdMethod_new(localStringBuffer, ((Account)localObject6).getCurrencyCode(), false);
            }
          }
          else
          {
            jdMethod_new(localStringBuffer, "", false);
          }
          Object localObject6 = (ArrayList)localHashMap3.get(str12);
          for (int i1 = 0; i1 < ((ArrayList)localObject6).size(); i1++)
          {
            ExtendedAccountSummary localExtendedAccountSummary2 = (ExtendedAccountSummary)((ArrayList)localObject6).get(i1);
            jdMethod_new(localStringBuffer, Integer.toString(localExtendedAccountSummary2.getSummaryType()), false);
            HashMap localHashMap4 = localExtendedAccountSummary2.getHash();
            Set localSet3 = localHashMap4.keySet();
            ArrayList localArrayList2 = new ArrayList(localSet3);
            Qsort.sortStrings(localArrayList2, 1);
            Iterator localIterator3 = localArrayList2.iterator();
            String str14;
            String str15;
            if (localExtendedAccountSummary2.getAmt() == null)
            {
              if (localIterator3.hasNext())
              {
                str14 = (String)localIterator3.next();
                str15 = (String)localHashMap4.get(str14);
                jdMethod_new(localStringBuffer, str15, false);
              }
              else
              {
                jdMethod_new(localStringBuffer, "", false);
              }
            }
            else
            {
              jdMethod_new(localStringBuffer, jdMethod_byte(localExtendedAccountSummary2.getAmt()), false);
              localCurrency2.addAmount(localExtendedAccountSummary2.getAmt());
            }
            if (localIterator3.hasNext())
            {
              str14 = (String)localIterator3.next();
              str15 = (String)localHashMap4.get(str14);
              jdMethod_new(localStringBuffer, str15, false);
            }
            else
            {
              jdMethod_new(localStringBuffer, "", false);
            }
            if (i1 == ((ArrayList)localObject6).size() - 1) {
              jdMethod_new(localStringBuffer, "", true);
            } else {
              jdMethod_new(localStringBuffer, "", false);
            }
          }
          jdMethod_new(localStringBuffer, "49", false);
          jdMethod_new(localStringBuffer, jdMethod_byte(localCurrency2), false);
          jdMethod_new(localStringBuffer, "2", true);
          m += 2;
          localCurrency1.addAmount(localCurrency2);
        }
        jdMethod_new(localStringBuffer, "98", false);
        jdMethod_new(localStringBuffer, jdMethod_byte(localCurrency1), false);
        jdMethod_new(localStringBuffer, Integer.toString(n), false);
        jdMethod_new(localStringBuffer, Integer.toString(++m), true);
        j += m;
        ((Currency)localObject1).addAmount(localCurrency1);
      }
    }
    jdMethod_new(localStringBuffer, "99", false);
    jdMethod_new(localStringBuffer, jdMethod_byte((Currency)localObject1), false);
    jdMethod_new(localStringBuffer, Integer.toString(k), false);
    jdMethod_new(localStringBuffer, Integer.toString(++j), true);
    return localStringBuffer;
  }
  
  private String jdMethod_case(Currency paramCurrency)
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
  
  private String jdMethod_byte(Currency paramCurrency)
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
  
  private String jdMethod_try(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return DateFormatUtil.getFormatter("yyMMdd").format(paramCalendar.getTime());
  }
  
  private String jdMethod_new(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return DateFormatUtil.getFormatter("HHmm").format(paramCalendar.getTime());
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
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
  
  protected StringBuffer getDelimitedReport(char paramChar, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    ExtendedAccountSummary localExtendedAccountSummary;
    for (int j = 0; j < this.SA.size(); j++)
    {
      localExtendedAccountSummary = (ExtendedAccountSummary)this.SA.get(j);
      HashMap localHashMap1 = localExtendedAccountSummary.getHash();
      int m = localHashMap1.size();
      if (localExtendedAccountSummary.getAmt() != null) {
        m++;
      }
      if (m > i) {
        i = m;
      }
    }
    localStringBuffer.append(ReportConsts.getColumnName(450, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(451, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(452, this.locale));
    localStringBuffer.append(paramChar);
    for (j = 1; j <= i; j++)
    {
      localStringBuffer.append(ReportConsts.getColumnName(453, this.locale));
      localStringBuffer.append(" ");
      localStringBuffer.append(j);
      if (j != i) {
        localStringBuffer.append(paramChar);
      }
    }
    localStringBuffer.append(_lineSeparator);
    for (j = 0; j < this.SA.size(); j++)
    {
      localExtendedAccountSummary = (ExtendedAccountSummary)this.SA.get(j);
      localStringBuffer.append(localExtendedAccountSummary.getSummaryDate());
      localStringBuffer.append(paramChar);
      localStringBuffer.append(localExtendedAccountSummary.getAccountID());
      localStringBuffer.append(paramChar);
      localStringBuffer.append(localExtendedAccountSummary.getSummaryType());
      localStringBuffer.append(paramChar);
      int k = 0;
      if (localExtendedAccountSummary.getAmt() != null)
      {
        jdMethod_char(localStringBuffer, localExtendedAccountSummary.getAmt());
        localStringBuffer.append(paramChar);
        k++;
      }
      HashMap localHashMap2 = localExtendedAccountSummary.getHash();
      Set localSet = localHashMap2.keySet();
      ArrayList localArrayList = new ArrayList(localSet);
      Qsort.sortStrings(localArrayList, 1);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = (String)localHashMap2.get(str1);
        if (paramBoolean)
        {
          localStringBuffer.append(str1);
          localStringBuffer.append(": ");
        }
        localStringBuffer.append(str2);
        k++;
        if (k != i) {
          localStringBuffer.append(paramChar);
        }
      }
      while (k < i - 1)
      {
        localStringBuffer.append(paramChar);
        k++;
      }
      localStringBuffer.append(_lineSeparator);
    }
    return localStringBuffer;
  }
  
  public void set(CustomSummaryRpt paramCustomSummaryRpt)
  {
    super.set(paramCustomSummaryRpt);
    setExtendedAccountSummaries(paramCustomSummaryRpt.getExtendedAccountSummaries());
    setLocale(paramCustomSummaryRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "CUSTOM_SUMMARY_RPT");
    if (this.SA != null) {
      localStringBuffer.append(this.SA.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "CUSTOM_SUMMARY_RPT");
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
      if (paramString.equals("EXTENDED_ACCOUNT_SUMMARIES"))
      {
        if (CustomSummaryRpt.this.SA == null) {
          CustomSummaryRpt.this.SA = new ExtendedAccountSummaries();
        }
        CustomSummaryRpt.this.SA.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.CustomSummaryRpt
 * JD-Core Version:    0.7.0.1
 */