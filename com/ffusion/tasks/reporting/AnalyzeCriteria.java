package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.Reports;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AnalyzeCriteria
  extends BaseTask
{
  public static final String DEFAULT_REPORTS_KEY = "DEFAULT_REPORTS_KEY";
  public static final String DEFAULT_SORT_CRITERIA_KEY = "DEFAULT_SORT_CRITERIA_KEY";
  public static final String DEFAULT_SEARCH_CRITERIA_KEY = "DEFAULT_SEARCH_CRITERIA_KEY";
  public static final String DEFAULT_OPTIONS_KEY = "DEFAULT_OPTIONS_KEY";
  private String Un = "DEFAULT_REPORTS_KEY";
  private int Ul = -1;
  private String Um = "DEFAULT_SEARCH_CRITERIA_KEY";
  private String Up = "DEFAULT_SORT_CRITERIA_KEY";
  private String Uo = "DEFAULT_OPTIONS_KEY";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getTaskErrorURL();
    }
    try
    {
      Reports localReports = (Reports)localHttpSession.getAttribute(this.Un);
      if (localReports == null)
      {
        this.error = 51;
        return super.getTaskErrorURL();
      }
      Report localReport = null;
      for (int i = 0; i < localReports.size(); i++) {
        if (((Report)localReports.get(i)).getReportID().getReportID() == this.Ul)
        {
          localReport = (Report)localReports.get(i);
          break;
        }
      }
      if (localReport == null)
      {
        this.error = 63;
        return super.getTaskErrorURL();
      }
      ReportCriteria localReportCriteria = localReport.getReportCriteria();
      localHttpSession.setAttribute(this.Um, localReportCriteria.getSearchCriteriaCollection());
      localHttpSession.setAttribute(this.Up, localReportCriteria.getSortCriteria());
      localHttpSession.setAttribute(this.Uo, localReportCriteria.getReportOptionsCollection());
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public String getReportsKey()
  {
    return this.Un;
  }
  
  public String getSearchCriteriaKey()
  {
    return this.Um;
  }
  
  public String getSortCriteriaKey()
  {
    return this.Up;
  }
  
  public String getOptionsKey()
  {
    return this.Uo;
  }
  
  public int getReportID()
  {
    return this.Ul;
  }
  
  public void setReportsKey(String paramString)
  {
    this.Un = paramString;
  }
  
  public void setSearchCritiaKey(String paramString)
  {
    this.Um = paramString;
  }
  
  public void setSortCriteriaKey(String paramString)
  {
    this.Up = paramString;
  }
  
  public void setOptionsKey(String paramString)
  {
    this.Uo = paramString;
  }
  
  public void setReportID(int paramInt)
  {
    this.Ul = paramInt;
  }
  
  public void setReportID(String paramString)
  {
    this.Ul = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.AnalyzeCriteria
 * JD-Core Version:    0.7.0.1
 */