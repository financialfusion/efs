package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.TreasuryDirect;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import com.ffusion.tasks.reporting.GenerateReportBase;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportData
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getServiceErrorURL();
    }
    Report localReport = (Report)localHttpSession.getAttribute(GenerateReportBase.REPORT_NAME);
    if (localReport == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    try
    {
      HashMap localHashMap = (HashMap)localHttpSession.getAttribute("Extra");
      if (localHashMap == null) {
        localHashMap = new HashMap();
      }
      IReportResult localIReportResult = TreasuryDirect.getReportData(localSecureUser, localReportCriteria, localHashMap);
      localReport.setReportResult(localIReportResult);
      localHttpSession.setAttribute(GenerateReportBase.REPORT_NAME, localReport);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    catch (Exception localException)
    {
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.GetReportData
 * JD-Core Version:    0.7.0.1
 */