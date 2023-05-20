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

public class ExportFooterOptions
  extends BaseTask
  implements ReportingConsts
{
  private String T1 = "COMMA";
  private String T2 = "Report";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute(this.T2);
    if ((this.T1 == null) || (this.T1.length() == 0))
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
        localObject1 = localReport.getReportCriteria().getReportOptions().getProperty("REPORTTYPE");
        localHashMap.put("REPORTTYPE", localObject1);
      }
      localObject1 = Reporting.exportFooterOptions(localSecureUser, localReport.getReportCriteria(), this.T1, localHashMap);
      localHttpSession.setAttribute("ExportedReportFooter", localObject1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      Object localObject1 = super.getServiceErrorURL();
      return localObject1;
    }
    finally
    {
      if (localReport != null) {
        localReport.setReportResult(null);
      }
    }
    return super.getSuccessURL();
  }
  
  public void setExportFormat(String paramString)
  {
    this.T1 = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this.T2 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.ExportFooterOptions
 * JD-Core Version:    0.7.0.1
 */