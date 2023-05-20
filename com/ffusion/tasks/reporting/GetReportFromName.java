package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportFromName
  extends BaseTask
  implements ReportingConsts
{
  private String TH = "";
  private String TJ = "Report";
  private String TI;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = (String)localHttpSession.getAttribute(this.TH);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport;
    try
    {
      ReportIdentification localReportIdentification = Reporting.getReport(localSecureUser, str, localHashMap);
      localReport = Reporting.getReportCriteria(localSecureUser, localReportIdentification);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    localHttpSession.setAttribute(this.TJ, localReport);
    if (this.TI != null)
    {
      paramHttpServletResponse.sendRedirect(this.TI);
      return null;
    }
    return super.getSuccessURL();
  }
  
  public void setIDInSessionName(String paramString)
  {
    this.TH = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this.TJ = paramString;
  }
  
  public void setTarget(String paramString)
  {
    this.TI = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GetReportFromName
 * JD-Core Version:    0.7.0.1
 */