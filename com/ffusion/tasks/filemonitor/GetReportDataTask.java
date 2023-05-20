package com.ffusion.tasks.filemonitor;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileMonitor;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.reporting.NewReportBase;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportDataTask
  extends BaseTask
{
  public static final String DEFAULT_REPORT_NAME = NewReportBase.REPORT_NAME;
  private String aP3 = DEFAULT_REPORT_NAME;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getTaskErrorURL();
    }
    Report localReport = (Report)localHttpSession.getAttribute(this.aP3);
    if (localReport == null)
    {
      this.error = 64;
      return super.getTaskErrorURL();
    }
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    try
    {
      HashMap localHashMap = new HashMap();
      IReportResult localIReportResult = FileMonitor.getReportData(localSecureUser, localReportCriteria, localHashMap);
      localReport.setReportResult(localIReportResult);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setReportName(String paramString)
  {
    this.aP3 = paramString;
  }
  
  public String getReportName()
  {
    return this.aP3;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.filemonitor.GetReportDataTask
 * JD-Core Version:    0.7.0.1
 */