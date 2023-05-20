package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.MapEntry;
import com.ffusion.csil.beans.entitlements.EntitlementExpression;
import com.ffusion.reporting.reportwriter.ReportWriter;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public class ReportCriteria
  extends ExtendABean
  implements Cloneable
{
  public static final String REPORTCRITERIA = "REPORTCRITERIA";
  public static final String SEARCH_CRITERIA = "SEARCH_CRITERIA";
  public static final String CRITERIA = "CRITERIA";
  public static final String NAME = "NAME";
  public static final String VALUE = "VALUE";
  public static final String REPORT_OPTIONS = "REPORT_OPTIONS";
  static final String aWd = "ENTITLEMENT_TYPES";
  static final String aWs = "ENTITLEMENT_TYPE";
  public static final String HIDE_SUFFIX = "_HIDE";
  public static final String OPT_RPT_TYPE = "REPORTTYPE";
  public static final String OPT_RPT_CATEGORY = "REPORTCATEGORY";
  public static final String OPT_RPT_FORMAT = "REPORTFORMAT";
  public static final String OPT_TITLE = "TITLE";
  public static final String OPT_SHOW_TITLE = "SHOWTITLE";
  public static final String OPT_SHOW_COMPANY_NAME = "SHOWCOMPANYNAME";
  public static final String OPT_SHOW_DATE = "SHOWDATE";
  public static final String OPT_DATE_FORMAT = "DATEFORMAT";
  public static final String OPT_TIME_FORMAT = "TIMEFORMAT";
  public static final String OPT_SHOW_TIME = "SHOWTIME";
  public static final String OPT_SHOW_HEADER = "SHOWHEADER";
  public static final String OPT_PAGE_NUMBER_FORMAT = "PAGENUMBERFORMAT";
  public static final String OPT_SHOW_PAGE_NUMBER_FORMAT = "SHOWPAGENUMBERFORMAT";
  public static final String OPT_EXTRA_FOOTER_LINE = "EXTRAFOOTERLINE";
  public static final String OPT_SHOW_EXTRA_FOOTER_LINE = "SHOWEXTRAFOOTERLINE";
  public static final String OPT_SHOW_FOOTER = "SHOWFOOTER";
  public static final String OPT_ORIENTATION = "ORIENTATION";
  public static final String OPT_SHOW_ORIENTATION = "SHOWORIENTATION";
  public static final String OPT_PAGEWIDTH = "PAGEWIDTH";
  public static final String OPT_PRINTER_READY_PAGEWIDTH = "PRINTERREADYPAGEWIDTH";
  public static final String OPT_HTML_EXPORT_PAGEWIDTH = "HTMLEXPORTPAGEWIDTH";
  public static final String OPT_PAGEWIDTH_TEXT = "PAGEWIDTH_TEXT";
  public static final String OPT_COMPANYNAME = "COMPANYNAME";
  public static final String OPT_STANDARD_CRIT_PAGE = "STANDARDCRITPAGE";
  public static final String OPT_CUSTOM_CRIT_PAGE = "CUSTOMCRITPAGE";
  public static final String OPT_SECURE_SERVLET_PATH = "SecureServletPath";
  public static final String OPT_GENERATE_TASK = "GENERATETASK";
  public static final String OPT_PRINTER_READY = "PRINTER_READY";
  public static final String OPT_FORMAT = "FORMAT";
  public static final String OPT_DESTINATION = "DESTINATION";
  public static final String OPT_MAXDISPLAYSIZE = "MAXDISPLAYSIZE";
  public static final String OPT_FILENAME = "FILENAME";
  public static final String OPT_STYLESHEET_NAME = "STYLESHEET_NAME";
  public static final String OPT_EOL_STYLE = "EOL_STYLE";
  public static final String OPT_REPEAT_COLUMN_HEADER = "REPEAT_COLUMN_HEADER";
  public static final String OPT_MAX_DAYS_IN_DATE_RANGE = "MAX_DAYS_IN_DATE_RANGE";
  public static final String OPT_SHOW_ALTERNATE_TEXT = "SHOW_ALTERNATE_TEXT_FOR_IMAGE";
  public static final String VAL_TRUE = "TRUE";
  public static final String VAL_FALSE = "FALSE";
  public static final String VAL_PORTRAIT = "PORTRAIT";
  public static final String VAL_LANDSCAPE = "LANDSCAPE";
  public static final String VAL_DEST_OBJECT = "OBJECT";
  public static final String VAL_DEST_HTTP = "HTTP";
  public static final String VAL_DEST_USRFS = "USER_FILE_SYSTEM";
  public static final String VAL_DEST_SVRFS = "SERVER_FILE_SYSTEM";
  public static final String VAL_FMT_HTML = "HTML";
  public static final String VAL_FMT_COMMA = "COMMA";
  public static final String VAL_FMT_TAB = "TAB";
  public static final String VAL_FMT_PDF = "PDF";
  public static final String VAL_FMT_BAI2 = "BAI2";
  public static final String VAL_FMT_TEXT = "TEXT";
  public static final String VAL_FMT_QIF = "QIF";
  public static final String VAL_FMT_QFX = "QFX";
  public static final String VAL_FMT_QBO = "QBO";
  public static final String VAL_FMT_IIF = "IIF";
  public static final String VAL_FMT_CSV = "CSV";
  public static final String VAL_FMT_OFX = "OFX";
  public static final String VAL_EOL_STYLE_LF = "LF";
  public static final String VAL_EOL_STYLE_CRLF = "CRLF";
  private Properties aWu = new Properties();
  private ReportSortCriteria aWr = new ReportSortCriteria();
  private Properties aWg = new Properties();
  private ArrayList aWn = new ArrayList();
  private EntitlementExpression aWi;
  private ArrayList aWl = new ArrayList();
  private Properties aWt = new Properties();
  private HashMap aWo = new HashMap();
  private String aWf;
  private String aWp;
  private String aWm;
  private ReportSortCriterion aWk;
  private ReportWriter aWj;
  private HttpServletResponse aWq;
  private SecureUser aWh;
  private Object aWe;
  
  public ReportCriteria() {}
  
  public ReportCriteria(Properties paramProperties1, ReportSortCriteria paramReportSortCriteria, Properties paramProperties2)
  {
    this.aWu = paramProperties1;
    this.aWr = paramReportSortCriteria;
    this.aWg = paramProperties2;
  }
  
  public Object clone()
  {
    ReportCriteria localReportCriteria = new ReportCriteria();
    localReportCriteria.setXML(getXML());
    localReportCriteria.setSearchCriteriaOrder(getSearchCriteriaOrder());
    localReportCriteria.setReportWriter(getReportWriter());
    localReportCriteria.setHttpServletResponse(getHttpServletResponse());
    localReportCriteria.setSecureUser(getSecureUser());
    localReportCriteria.setExporter(getExporter());
    localReportCriteria.setEntitlementExpression(getEntitlementExpression());
    localReportCriteria.setLocale(this.locale);
    return localReportCriteria;
  }
  
  public void setSearchCriteria(Properties paramProperties)
  {
    this.aWu = paramProperties;
  }
  
  public Properties getSearchCriteria()
  {
    return this.aWu;
  }
  
  public Collection getSearchCriteriaCollection()
  {
    return this.aWu == null ? null : Arrays.asList(jdMethod_for(this.aWu));
  }
  
  public void setSortCriteria(ReportSortCriteria paramReportSortCriteria)
  {
    this.aWr = paramReportSortCriteria;
  }
  
  public ReportSortCriteria getSortCriteria()
  {
    return this.aWr;
  }
  
  public void setReportOptions(Properties paramProperties)
  {
    this.aWg = paramProperties;
  }
  
  public Properties getReportOptions()
  {
    return this.aWg;
  }
  
  public Collection getReportOptionsCollection()
  {
    return this.aWg == null ? null : Arrays.asList(jdMethod_for(this.aWg));
  }
  
  public ReportWriter getReportWriter()
  {
    return this.aWj;
  }
  
  public void setReportWriter(ReportWriter paramReportWriter)
  {
    this.aWj = paramReportWriter;
  }
  
  public HttpServletResponse getHttpServletResponse()
  {
    return this.aWq;
  }
  
  public void setHttpServletResponse(HttpServletResponse paramHttpServletResponse)
  {
    this.aWq = paramHttpServletResponse;
  }
  
  public ArrayList getEntitlementTypes()
  {
    return this.aWn;
  }
  
  public void setEntitlementTypes(ArrayList paramArrayList)
  {
    if (paramArrayList != null) {
      this.aWn = paramArrayList;
    } else {
      this.aWn = new ArrayList();
    }
  }
  
  public EntitlementExpression getEntitlementExpression()
  {
    return this.aWi;
  }
  
  public void setEntitlementExpression(EntitlementExpression paramEntitlementExpression)
  {
    this.aWi = paramEntitlementExpression;
  }
  
  public SecureUser getSecureUser()
  {
    return this.aWh;
  }
  
  public void setSecureUser(SecureUser paramSecureUser)
  {
    this.aWh = paramSecureUser;
  }
  
  public Object getExporter()
  {
    return this.aWe;
  }
  
  public void setExporter(Object paramObject)
  {
    this.aWe = paramObject;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORTCRITERIA");
    XMLHandler.appendBeginTag(localStringBuffer, "ENTITLEMENT_TYPES");
    Iterator localIterator = this.aWn.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (String)localIterator.next();
      XMLHandler.appendTag(localStringBuffer, "ENTITLEMENT_TYPE", (String)localObject1);
    }
    XMLHandler.appendEndTag(localStringBuffer, "ENTITLEMENT_TYPES");
    XMLHandler.appendBeginTag(localStringBuffer, "SEARCH_CRITERIA");
    Object localObject1 = this.aWu.propertyNames();
    String str1;
    while (((Enumeration)localObject1).hasMoreElements())
    {
      localObject2 = (String)((Enumeration)localObject1).nextElement();
      str1 = this.aWu.getProperty((String)localObject2);
      XMLHandler.appendBeginTag(localStringBuffer, "CRITERIA");
      XMLHandler.appendTag(localStringBuffer, "NAME", (String)localObject2);
      XMLHandler.appendTag(localStringBuffer, "VALUE", str1);
      XMLHandler.appendEndTag(localStringBuffer, "CRITERIA");
    }
    XMLHandler.appendEndTag(localStringBuffer, "SEARCH_CRITERIA");
    localStringBuffer.append(this.aWr.getXML());
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_OPTIONS");
    Object localObject2 = this.aWg.propertyNames();
    while (((Enumeration)localObject2).hasMoreElements())
    {
      str1 = (String)((Enumeration)localObject2).nextElement();
      String str2 = this.aWg.getProperty(str1);
      XMLHandler.appendTag(localStringBuffer, str1, str2);
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_OPTIONS");
    XMLHandler.appendEndTag(localStringBuffer, "REPORTCRITERIA");
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
  
  public void setCurrentSearchCriterion(String paramString)
  {
    this.aWf = paramString;
  }
  
  public void setCurrentSearchCriterionValue(String paramString)
  {
    if (this.aWf == null) {
      return;
    }
    set(this.aWf, paramString, true, false);
  }
  
  public String getCurrentSearchCriterionValue()
  {
    if (this.aWf == null) {
      return "";
    }
    String str = this.aWu.getProperty(this.aWf);
    if (str == null) {
      return "";
    }
    return str;
  }
  
  public void setRemoveSearchCriterionValue()
  {
    if (this.aWf == null) {
      return;
    }
    this.aWu.remove(this.aWf);
  }
  
  public void setRemoveSearchCriterionValue(String paramString)
  {
    setRemoveSearchCriterionValue();
  }
  
  public void setCurrentReportOption(String paramString)
  {
    this.aWp = paramString;
  }
  
  public void setCurrentReportOptionValue(String paramString)
  {
    if (this.aWp != null) {
      set(this.aWp, paramString, false, true);
    }
  }
  
  public String getCurrentReportOptionValue()
  {
    if (this.aWp == null) {
      return "";
    }
    String str = this.aWg.getProperty(this.aWp);
    if (str == null) {
      return "";
    }
    return str;
  }
  
  public void setCurrentSortCriterion(String paramString)
  {
    int i = Integer.parseInt(paramString);
    for (int j = 0; j < this.aWr.size(); j++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)this.aWr.get(j);
      if (localReportSortCriterion.getOrdinal() == i)
      {
        this.aWk = localReportSortCriterion;
        break;
      }
    }
  }
  
  public void setCurrentDisplayCriterion(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aWm = null;
    } else {
      this.aWm = paramString.trim();
    }
  }
  
  public String getCurrentSortCriterionValue()
  {
    if (this.aWk == null) {
      return "";
    }
    return this.aWk.getName();
  }
  
  public String getCurrentDisplayCriterionValue()
  {
    if (this.aWm == null) {
      return "";
    }
    return getDisplayValue(this.aWm);
  }
  
  public void clearDisplayValues()
  {
    this.aWt.clear();
  }
  
  public void setDisplayValue(String paramString1, String paramString2)
  {
    this.aWt.setProperty(paramString1, paramString2);
  }
  
  public void setDisplayValue(int paramInt, String paramString)
  {
    this.aWt.setProperty(String.valueOf(paramInt), paramString);
  }
  
  public void setHiddenSearchCriterion(String paramString)
  {
    if (this.aWf == null) {
      return;
    }
    this.aWo.put(this.aWf, Boolean.valueOf(paramString));
  }
  
  public void setHiddenSearchCriterion(String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.aWo.put(paramString, Boolean.TRUE);
    } else {
      this.aWo.put(paramString, Boolean.FALSE);
    }
  }
  
  public boolean isSearchCriterionHidden(String paramString)
  {
    Object localObject = this.aWo.get(paramString);
    if (localObject == null) {
      return false;
    }
    return ((Boolean)localObject).booleanValue();
  }
  
  public void clearHiddenSearchCriteria()
  {
    this.aWo.clear();
  }
  
  public void setHiddenSearchCriteria(ReportCriteria paramReportCriteria)
  {
    this.aWo = ((HashMap)paramReportCriteria.getHiddenSearchCriteria().clone());
  }
  
  public HashMap getHiddenSearchCriteria()
  {
    return this.aWo;
  }
  
  public String getDisplayValue(String paramString)
  {
    String str = this.aWt.getProperty(paramString);
    if (str != null) {
      return str;
    }
    return this.aWu.getProperty(paramString);
  }
  
  public String getDisplayValue(int paramInt)
  {
    String str = this.aWt.getProperty(String.valueOf(paramInt));
    if (str != null) {
      return str;
    }
    for (int i = 0; i < this.aWr.size(); i++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)this.aWr.get(i);
      if (localReportSortCriterion.getOrdinal() == paramInt) {
        return localReportSortCriterion.getName();
      }
    }
    return null;
  }
  
  public void addToSearchCriteria(String paramString)
  {
    if (paramString == null) {
      return;
    }
    for (int i = 0; i < this.aWl.size(); i++) {
      if (paramString.equals((String)this.aWl.get(i))) {
        return;
      }
    }
    this.aWl.add(paramString);
  }
  
  public ArrayList getSearchCriteriaOrder()
  {
    return (ArrayList)this.aWl.clone();
  }
  
  public void setSearchCriteriaOrder(ArrayList paramArrayList)
  {
    this.aWl = paramArrayList;
  }
  
  public boolean set(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    return jdMethod_for(paramString1, paramString2, paramBoolean1, paramBoolean2, false);
  }
  
  private boolean jdMethod_for(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    boolean bool = true;
    if (paramBoolean1) {
      this.aWu.setProperty(paramString1, paramString2);
    } else if (paramBoolean2) {
      this.aWg.setProperty(paramString1, paramString2);
    } else if (paramBoolean3) {
      this.aWn.add(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  private static MapEntry[] jdMethod_for(Map paramMap)
  {
    if (paramMap == null) {
      return null;
    }
    Set localSet = paramMap.entrySet();
    Iterator localIterator = localSet.iterator();
    int i = localSet.size();
    MapEntry[] arrayOfMapEntry = new MapEntry[i];
    for (int j = 0; j < i; j++)
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      arrayOfMapEntry[j] = new MapEntry(localEntry.getKey(), localEntry.getValue());
    }
    return arrayOfMapEntry;
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    private boolean jdField_byte = false;
    private boolean jdField_try = false;
    private boolean jdField_int = false;
    private String jdField_new;
    
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("SEARCH_CRITERIA")) {
        this.jdField_byte = true;
      } else if (paramString.equals("SORT_CRITERIA")) {
        ReportCriteria.this.aWr.continueXMLParsing(getHandler());
      } else if (paramString.equals("REPORT_OPTIONS")) {
        this.jdField_try = true;
      } else if (paramString.equals("ENTITLEMENT_TYPES")) {
        this.jdField_int = true;
      } else {
        super.startElement(paramString);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("SEARCH_CRITERIA")) {
        this.jdField_byte = false;
      } else if (paramString.equals("REPORT_OPTIONS")) {
        this.jdField_try = false;
      } else if (paramString.equals("ENTITLEMENT_TYPES")) {
        this.jdField_int = false;
      } else {
        super.endElement(paramString);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      if (str1.length() > 0)
      {
        String str2 = getElement();
        if ((str2.equals("CRITERIA")) || (str2.equals("SEARCH_CRITERIA"))) {
          return;
        }
        if (str2.equalsIgnoreCase("NAME"))
        {
          this.jdField_new = str1;
          if (this.jdField_byte) {
            ReportCriteria.this.set(this.jdField_new, "", this.jdField_byte, this.jdField_try);
          }
        }
        else if (str2.equalsIgnoreCase("VALUE"))
        {
          ReportCriteria.this.set(this.jdField_new, str1, this.jdField_byte, this.jdField_try);
        }
        else
        {
          ReportCriteria.this.jdMethod_for(str2, str1, this.jdField_byte, this.jdField_try, this.jdField_int);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportCriteria
 * JD-Core Version:    0.7.0.1
 */