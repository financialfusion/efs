package com.ffusion.tasks.reporting;

import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportByOrdinal
  extends BaseTask
  implements ReportingConsts
{
  private String TY = "Reports";
  private String T0 = "Report";
  private int TZ;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ReportIdentifications localReportIdentifications = (ReportIdentifications)localHttpSession.getAttribute(this.TY);
    ReportIdentification localReportIdentification = (ReportIdentification)localReportIdentifications.get(this.TZ);
    localHttpSession.setAttribute(this.T0, localReportIdentification);
    return super.getSuccessURL();
  }
  
  public void setOrdinal(String paramString)
  {
    this.TZ = (Integer.parseInt(paramString) - 1);
  }
  
  public void setReportsName(String paramString)
  {
    this.TY = paramString;
  }
  
  public String getReportsName()
  {
    return this.TY;
  }
  
  public String getReportName()
  {
    return this.T0;
  }
  
  public void setReportName(String paramString)
  {
    this.T0 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GetReportByOrdinal
 * JD-Core Version:    0.7.0.1
 */