package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.beans.reporting.Reports;
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

public class GetReportsByCategory
  extends BaseTask
  implements ReportingConsts
{
  private ReportIdentifications Tp;
  private Reports Tq;
  private String To = "Reports";
  private String Tm;
  private String Tn = "REPORTNAME";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("UserLocale", localHttpSession.getAttribute("UserLocale"));
      if (this.Tp == null) {
        this.Tp = Reporting.getReports(localSecureUser, localHashMap);
      }
      if ((this.Tn == null) || (this.Tn.trim().equals(""))) {
        this.Tn = "REPORTNAME";
      }
      this.Tp.setSortedBy(this.Tn);
      if (this.Tq == null)
      {
        this.Tq = new Reports();
        for (int i = 0; i < this.Tp.size(); i++) {
          if (this.Tm != null)
          {
            Report localReport = Reporting.getReportCriteria(localSecureUser, (ReportIdentification)this.Tp.get(i));
            if ((localReport.getReportCriteria() != null) && (localReport.getReportCriteria().getReportOptions() != null))
            {
              String str = localReport.getReportCriteria().getReportOptions().getProperty("REPORTCATEGORY");
              if ((str != null) && (str.equalsIgnoreCase(this.Tm))) {
                this.Tq.add(localReport);
              }
            }
            else
            {
              this.Tq.add(Reporting.getReportCriteria(localSecureUser, (ReportIdentification)this.Tp.get(i)));
            }
          }
        }
      }
      else
      {
        Reports localReports = new Reports();
        for (int j = 0; j < this.Tp.size(); j++) {
          for (int k = 0; k < this.Tq.size(); k++) {
            if (((Report)this.Tq.get(k)).getReportID().getReportID() == ((ReportIdentification)this.Tp.get(j)).getReportID()) {
              localReports.add(this.Tq.get(k));
            }
          }
        }
        this.Tq = localReports;
      }
      localHttpSession.setAttribute(this.To, this.Tq);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setReportsName(String paramString)
  {
    this.To = paramString;
  }
  
  public String getReportsName()
  {
    String str = "";
    if (this.To != null) {
      str = this.To;
    }
    return str;
  }
  
  public void setReportCategory(String paramString)
  {
    this.Tm = paramString;
  }
  
  public String getReportCategory()
  {
    String str = "";
    if (this.Tm != null) {
      str = this.Tm;
    }
    return str;
  }
  
  public void setSort(String paramString)
  {
    this.Tn = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GetReportsByCategory
 * JD-Core Version:    0.7.0.1
 */