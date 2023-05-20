package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.csil.core.Reporting;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewReportBase
  extends BaseTask
  implements Task
{
  public static String REPORT_NAME = "ReportData";
  protected Report _report;
  private String Ui;
  private String Uh;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = null;
    HashMap localHashMap = null;
    if (localSecureUser == null)
    {
      this.error = 38;
      str = this.taskErrorURL;
    }
    localLocale = localSecureUser.getLocale();
    localHashMap = new HashMap();
    this._report = new Report();
    try
    {
      this._report.setReportCriteria(Reporting.getDefaultReportCriteria(this.Ui, localLocale, localHashMap));
    }
    catch (Exception localException)
    {
      this.error = 37004;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute(REPORT_NAME, this._report);
    if (this.Uh != null) {
      paramHttpServletResponse.sendRedirect(this.Uh);
    }
    return str;
  }
  
  public void setTarget(String paramString)
  {
    this.Uh = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this.Ui = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.NewReportBase
 * JD-Core Version:    0.7.0.1
 */