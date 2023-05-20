package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportCriteria
  extends BaseTask
  implements ReportingConsts
{
  private String Tr = "";
  private String Ts = "ReportIdentifications";
  private String Tt = "Report";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ReportIdentifications localReportIdentifications = (ReportIdentifications)localHttpSession.getAttribute(this.Ts);
    ReportIdentification localReportIdentification = localReportIdentifications.getByID(Integer.parseInt(this.Tr));
    try
    {
      Report localReport = Reporting.getReportCriteria(localSecureUser, localReportIdentification);
      localHttpSession.setAttribute(this.Tt, localReport);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setID(String paramString)
  {
    this.Tr = paramString;
  }
  
  public void setIdentificationsName(String paramString)
  {
    this.Ts = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this.Tt = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GetReportCriteria
 * JD-Core Version:    0.7.0.1
 */