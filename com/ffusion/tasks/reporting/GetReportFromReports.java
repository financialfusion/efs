package com.ffusion.tasks.reporting;

import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.Reports;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportFromReports
  extends BaseTask
  implements ReportingConsts
{
  private String Tx = "";
  private String Tv = "";
  private String Ty = "Reports";
  private String Tz = "Report";
  private String Tw;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Reports localReports = (Reports)localHttpSession.getAttribute(this.Ty);
    try
    {
      for (int i = 0; i < localReports.size(); i++)
      {
        Report localReport = (Report)localReports.get(i);
        int j = 0;
        if (this.Tx.equals("")) {
          j = Integer.parseInt((String)localHttpSession.getAttribute(this.Tv));
        } else {
          j = Integer.parseInt(this.Tx);
        }
        if (localReport.getReportID().getReportID() == j)
        {
          localHttpSession.setAttribute(this.Tz, localReport);
          break;
        }
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return super.getTaskErrorURL();
    }
    if (this.Tw != null) {
      paramHttpServletResponse.sendRedirect(this.Tw);
    }
    return super.getSuccessURL();
  }
  
  public void setID(String paramString)
  {
    this.Tx = paramString;
  }
  
  public void setIDInSessionName(String paramString)
  {
    this.Tv = paramString;
  }
  
  public void setReportsName(String paramString)
  {
    this.Ty = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this.Tz = paramString;
  }
  
  public void setTarget(String paramString)
  {
    this.Tw = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GetReportFromReports
 * JD-Core Version:    0.7.0.1
 */