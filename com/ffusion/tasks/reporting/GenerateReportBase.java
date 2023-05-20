package com.ffusion.tasks.reporting;

import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.jtf.UrlEncryptor;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import com.ffusion.tasks.util.TaskUtil;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GenerateReportBase
  extends BaseTask
  implements Task
{
  public static String REPORT_NAME = "ReportData";
  public static String LAST_INTRADAY_TRANSACTION_VIEW_DATE_FOR_EXPORT = "LastIntradayTransactionViewDateForExport";
  public static final String EXTRA_PREFIX = "Extra_";
  private static final int Uz = "Extra_".length();
  public static final String RPT_STYLESHEET_NAME = "ReportStyleSheetName";
  private String Uv;
  private String Uy;
  private String UA;
  private String Ux;
  private static final String Uw = "com.ffusion.beans.user.resources";
  private static final String UB = "DateFormat";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Report localReport = (Report)localHttpSession.getAttribute(REPORT_NAME);
    ReportCriteria localReportCriteria1 = localReport.getReportCriteria();
    Properties localProperties = localReportCriteria1.getReportOptions();
    this.Uy = this.UA;
    String str2 = null;
    if (this.UA != null)
    {
      str2 = localProperties.getProperty("DESTINATION");
      localProperties.setProperty("DESTINATION", this.UA);
      this.UA = null;
    }
    String str3 = null;
    if (this.Ux != null)
    {
      str3 = localProperties.getProperty("FORMAT");
      localProperties.setProperty("FORMAT", this.Ux);
      this.Ux = null;
    }
    String str4 = localProperties.getProperty("DATEFORMAT");
    localProperties.setProperty("DATEFORMAT", ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", localReportCriteria1.getLocale()));
    int i = 0;
    localReportCriteria1.setCurrentSearchCriterion("Date Range Type");
    String str5 = localReportCriteria1.getCurrentSearchCriterionValue();
    if ((str5 != null) && (str5.equals("Relative")))
    {
      localReportCriteria1.setCurrentSearchCriterion("Date Range");
      str6 = localReportCriteria1.getCurrentSearchCriterionValue();
      if ((str6 != null) && (str6.equals("Today(new)"))) {
        i = 1;
      }
    }
    String str6 = (String)localHttpSession.getAttribute(LAST_INTRADAY_TRANSACTION_VIEW_DATE_FOR_EXPORT);
    if (str6 == null)
    {
      localReportCriteria1.setCurrentSearchCriterion("Datasource Load Start Time");
      localReportCriteria1.setRemoveSearchCriterionValue();
      localReportCriteria1.setCurrentSearchCriterion("Datasource Load End Time");
      localReportCriteria1.setRemoveSearchCriterionValue();
    }
    String str7 = null;
    if ((localProperties.containsKey("FORMAT")) && (localProperties.getProperty("FORMAT").equals("HTML")) && (localProperties.containsKey("DESTINATION")))
    {
      localObject1 = localProperties.getProperty("DESTINATION");
      if ((((String)localObject1).equals("USER_FILE_SYSTEM")) && (localProperties.containsKey("HTMLEXPORTPAGEWIDTH")))
      {
        localObject2 = localProperties.getProperty("HTMLEXPORTPAGEWIDTH");
        str7 = localProperties.getProperty("PAGEWIDTH");
        localProperties.setProperty("PAGEWIDTH", (String)localObject2);
      }
    }
    Object localObject1 = localProperties.propertyNames();
    Object localObject2 = new HashMap();
    ((HashMap)localObject2).put("UserLocale", localHttpSession.getAttribute("UserLocale"));
    while (((Enumeration)localObject1).hasMoreElements())
    {
      str8 = (String)((Enumeration)localObject1).nextElement();
      if (str8.startsWith("Extra_"))
      {
        localObject3 = str8.substring(Uz);
        String str9 = localProperties.getProperty(str8);
        ((HashMap)localObject2).put(localObject3, localHttpSession.getAttribute(str9));
      }
    }
    if (!((HashMap)localObject2).isEmpty()) {
      localHttpSession.setAttribute("Extra", localObject2);
    }
    String str8 = localProperties.getProperty("GENERATETASK");
    Object localObject3 = null;
    int j = 0;
    int k = 0;
    int m = 0;
    ReportCriteria localReportCriteria2 = null;
    String str11;
    String str12;
    try
    {
      localReport.getReportCriteria().setHttpServletResponse(paramHttpServletResponse);
      String str10 = localProperties.getProperty("MAXDISPLAYSIZE");
      str11 = localProperties.getProperty("DESTINATION");
      str12 = localProperties.getProperty("STYLESHEET_NAME");
      String str13 = localProperties.getProperty("EOL_STYLE");
      if ((str10 == null) && (str11.equals("OBJECT")))
      {
        localProperties.setProperty("MAXDISPLAYSIZE", "0");
        j = 1;
      }
      if (str12 == null)
      {
        str12 = (String)localHttpSession.getAttribute("ReportStyleSheetName");
        if (str12 != null)
        {
          localProperties.setProperty("STYLESHEET_NAME", str12);
          k = 1;
        }
      }
      if (str13 == null)
      {
        str13 = "CRLF";
        if (TaskUtil.getOSType(paramHttpServletRequest).equals("Unix")) {
          str13 = "LF";
        }
        localProperties.setProperty("EOL_STYLE", str13);
        m = 1;
      }
      localReportCriteria2 = (ReportCriteria)localReportCriteria1.clone();
      localReportCriteria2.getSortCriteria().setSortedBy("ORDINAL,REVERSE");
      Iterator localIterator = localReportCriteria2.getSortCriteria().iterator();
      ReportSortCriterion localReportSortCriterion = null;
      while (localIterator.hasNext())
      {
        localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        localReportCriteria1.getSortCriteria().create(localReportSortCriterion.getOrdinal(), localReportSortCriterion.getName(), localReportSortCriterion.getAsc());
      }
      HashMap localHashMap = new HashMap();
      localReportCriteria1.remove("ReportImageList");
      if ("OBJECT".equals(localProperties.getProperty("DESTINATION"))) {
        localReportCriteria1.put("ReportImageList", localHashMap);
      }
      localProperties.setProperty("SecureServletPath", (String)localHttpSession.getAttribute("SecureServletPath"));
      UrlEncryptor localUrlEncryptor = (UrlEncryptor)localHttpSession.getAttribute("URL_ENCRYPTOR");
      if (localUrlEncryptor != null) {
        localReportCriteria1.put("URL_ENCRYPTOR", localUrlEncryptor);
      }
      localObject3 = (BaseTask)Class.forName(str8).newInstance();
      str1 = ((BaseTask)localObject3).process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
      if (localReportCriteria1.get("ReportImageList") != null) {
        localHttpSession.setAttribute("ReportImageList", localReportCriteria1.get("ReportImageList"));
      }
      this.error = Integer.parseInt(((BaseTask)localObject3).getError());
    }
    catch (Exception localException)
    {
      this.error = 37003;
      str11 = this.taskErrorURL;
      return str11;
    }
    finally
    {
      if (localReportCriteria2 != null) {
        localReportCriteria1.set(localReportCriteria2);
      }
      localReportCriteria1.remove("URL_ENCRYPTOR");
      localReport.getReportCriteria().setHttpServletResponse(null);
      if (j != 0) {
        localProperties.remove("MAXDISPLAYSIZE");
      }
      if (k != 0) {
        localProperties.remove("STYLESHEET_NAME");
      }
      if (m != 0) {
        localProperties.remove("EOL_STYLE");
      }
      localHttpSession.removeAttribute("Extra");
    }
    int n = 1;
    if (localProperties.containsKey("DESTINATION"))
    {
      str11 = localProperties.getProperty("DESTINATION");
      if ((str11.equals("USER_FILE_SYSTEM")) || (str11.equals("HTTP"))) {
        n = 0;
      }
    }
    if ((i != 0) && (localProperties.containsKey("DESTINATION")))
    {
      str11 = localProperties.getProperty("DESTINATION");
      if (str11.equals("OBJECT"))
      {
        localReportCriteria1.setCurrentSearchCriterion("Datasource Load Start Time");
        str12 = localReportCriteria1.getCurrentSearchCriterionValue();
        localHttpSession.setAttribute(LAST_INTRADAY_TRANSACTION_VIEW_DATE_FOR_EXPORT, str12);
      }
    }
    if (str2 != null) {
      localProperties.setProperty("DESTINATION", str2);
    }
    if (str3 != null) {
      localProperties.setProperty("FORMAT", str3);
    }
    localProperties.setProperty("DATEFORMAT", str4);
    if (str7 != null) {
      localProperties.setProperty("PAGEWIDTH", str7);
    }
    if ((n != 0) && (this.Uv != null) && ((str1 == null) || (str1.equals(((BaseTask)localObject3).getSuccessURL())))) {
      paramHttpServletResponse.sendRedirect(this.Uv);
    }
    return str1;
  }
  
  public void setTarget(String paramString)
  {
    this.Uv = paramString;
  }
  
  public void setDestination(String paramString)
  {
    this.UA = paramString;
  }
  
  public String getLastDestination()
  {
    return this.Uy;
  }
  
  public void setFormat(String paramString)
  {
    this.Ux = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GenerateReportBase
 * JD-Core Version:    0.7.0.1
 */