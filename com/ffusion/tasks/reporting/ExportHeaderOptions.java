package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportHeaderOptions
  extends BaseTask
  implements ReportingConsts
{
  private String TA = "COMMA";
  private String TB = "Report";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute(this.TB);
    if ((this.TA == null) || (this.TA.length() == 0))
    {
      this.error = 76;
      return this.taskErrorURL;
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("UserLocale", localHttpSession.getAttribute("UserLocale"));
      if (localReport != null)
      {
        localHashMap.put("REPORTCRITERIA", localReport.getReportCriteria());
        localObject = localReport.getReportCriteria().getReportOptions().getProperty("REPORTTYPE");
        localHashMap.put("REPORTTYPE", localObject);
      }
      Object localObject = Reporting.exportHeaderOptions(localSecureUser, localReport.getReportCriteria(), this.TA, localHashMap);
      localHttpSession.setAttribute("ExportedReportHeader", localObject);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setExportFormat(String paramString)
  {
    this.TA = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this.TB = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.ExportHeaderOptions
 * JD-Core Version:    0.7.0.1
 */