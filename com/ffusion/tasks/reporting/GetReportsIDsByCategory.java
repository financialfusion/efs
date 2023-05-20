package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
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

public class GetReportsIDsByCategory
  extends BaseTask
  implements ReportingConsts
{
  private String TC = "ReportsIDs";
  private String TE = "ReportNamesAndCategories";
  private String TD = "REPORTNAME";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      HashMap localHashMap1 = new HashMap();
      ReportIdentifications localReportIdentifications1 = Reporting.getReports(localSecureUser, localHashMap1);
      HashMap localHashMap2 = new HashMap();
      if ((this.TD == null) || (this.TD.trim().equals(""))) {
        this.TD = "REPORTNAME";
      }
      localReportIdentifications1.setSortedBy(this.TD);
      for (int i = 0; i < localReportIdentifications1.size(); i++)
      {
        Report localReport = Reporting.getReportCriteria(localSecureUser, (ReportIdentification)localReportIdentifications1.get(i));
        if ((localReport.getReportCriteria() != null) && (localReport.getReportCriteria().getReportOptions() != null))
        {
          String str = localReport.getReportCriteria().getReportOptions().getProperty("REPORTCATEGORY");
          ReportIdentifications localReportIdentifications2;
          if (localHashMap2.get(str) == null)
          {
            localReportIdentifications2 = new ReportIdentifications();
            localReportIdentifications2.add(localReportIdentifications1.get(i));
            localHashMap2.put(str, localReportIdentifications2);
          }
          else
          {
            localReportIdentifications2 = (ReportIdentifications)localHashMap2.get(str);
            localReportIdentifications2.add(localReportIdentifications1.get(i));
          }
        }
      }
      localHttpSession.setAttribute(this.TE, localHashMap2);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setSort(String paramString)
  {
    this.TD = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GetReportsIDsByCategory
 * JD-Core Version:    0.7.0.1
 */