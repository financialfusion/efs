package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportData
  extends BaseTask
{
  protected String companyID;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    localHashMap.put("UserLocale", localUserLocale);
    Report localReport = (Report)localHttpSession.getAttribute("ReportData");
    if (localReport == null)
    {
      this.error = 64;
      str1 = this.taskErrorURL;
    }
    else
    {
      try
      {
        String str2 = localReport.getReportCriteria().getReportOptions().getProperty("REPORTTYPE");
        if ((str2.equals("Cash Concentration Activity Report")) || (str2.equals("Inactive or Non-Reporting Divisions and Locations Report")))
        {
          ReportCriteria localReportCriteria = localReport.getReportCriteria();
          IReportResult localIReportResult = CashCon.getReportData(localSecureUser, localReportCriteria, localHashMap);
          localReport.setReportResult(localIReportResult);
        }
      }
      catch (CSILException localCSILException)
      {
        localCSILException.printStackTrace();
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return str1;
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetReportData
 * JD-Core Version:    0.7.0.1
 */