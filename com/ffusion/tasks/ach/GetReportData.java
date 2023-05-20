package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.Reporting;
import com.ffusion.fileimporter.fileadapters.ACHAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.fileImport.ProcessACHImportTask;
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
    Report localReport = (Report)localHttpSession.getAttribute("ReportData");
    HashMap localHashMap = new HashMap();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    localHashMap.put("UserLocale", localUserLocale);
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
        ReportCriteria localReportCriteria;
        Object localObject;
        if ((str2.equals("Completed ACH Payments")) || (str2.equals("Total Child Support Payments")) || (str2.equals("Total Tax Payments")) || (str2.equals("CB ACH File Upload")) || (str2.equals("ACH Participant Prenote Report")))
        {
          localReportCriteria = localReport.getReportCriteria();
          localObject = (ACHPayees)localHttpSession.getAttribute("ACHPayees");
          localHashMap.put("ACHPayees", localObject);
          ACHCompanies localACHCompanies = ACH.getACHCompanies(localSecureUser, String.valueOf(localSecureUser.getBusinessID()), localSecureUser.getBankID(), false, new HashMap());
          localHashMap.put("ACHCOMPANIES", localACHCompanies);
          IReportResult localIReportResult = ACH.getReportData(localSecureUser, this.companyID, localReportCriteria, localHashMap);
          localReport.setReportResult(localIReportResult);
        }
        else if (str2.equals("ACH File Upload"))
        {
          localHashMap = (HashMap)localHttpSession.getAttribute("UploadResults");
          localReportCriteria = localReport.getReportCriteria();
          Reporting.prepareForReport(localSecureUser, localReportCriteria, localHashMap);
          localObject = ACHAdapter.generateReport(localSecureUser, localReportCriteria, localHashMap);
          localReport.setReportResult((IReportResult)localObject);
        }
        else if (str2.equals("ACH Import Entries Report"))
        {
          localHashMap = (HashMap)localHttpSession.getAttribute("UploadResults");
          localReportCriteria = localReport.getReportCriteria();
          Reporting.prepareForReport(localSecureUser, localReportCriteria, localHashMap);
          localObject = ProcessACHImportTask.generateReport(localSecureUser, localReportCriteria, localHashMap);
          localReport.setReportResult((IReportResult)localObject);
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
 * Qualified Name:     com.ffusion.tasks.ach.GetReportData
 * JD-Core Version:    0.7.0.1
 */