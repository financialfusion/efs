package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Lockbox;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.reporting.GenerateReportBase;
import com.ffusion.tasks.reporting.NewReportBase;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxReportDataTask
  extends BaseTask
{
  protected String _reportName = GenerateReportBase.REPORT_NAME;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute(this._reportName);
    if (localReport == null) {
      localReport = (Report)localHttpSession.getAttribute(NewReportBase.REPORT_NAME);
    }
    if (localReport == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    try
    {
      HashMap localHashMap = new HashMap();
      UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
      localHashMap.put("UserLocale", localUserLocale);
      localHashMap.put("LOCKBOX_ACCOUNTS", localHttpSession.getAttribute("LOCKBOX_ACCOUNTS"));
      IReportResult localIReportResult = Lockbox.getReportData(localSecureUser, localReportCriteria, localHashMap);
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
    this._reportName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxReportDataTask
 * JD-Core Version:    0.7.0.1
 */