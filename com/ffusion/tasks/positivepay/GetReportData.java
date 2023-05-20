package com.ffusion.tasks.positivepay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayDecisionRpt;
import com.ffusion.beans.positivepay.PPayIssueRpt;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PositivePay;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportData
  extends BaseTask
  implements Task
{
  public static String PPAY_REPORT_NAME = "ReportData";
  public static String PPAY_REPORT_RECORDS_NAME = "PPayReportRecords";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute(PPAY_REPORT_NAME);
    AccountService3 localAccountService3 = (AccountService3)localHttpSession.getAttribute("com.ffusion.service.AccountService");
    if ((localSecureUser == null) || (localReport == null))
    {
      this.error = 22052;
      str1 = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
        localHashMap.put("UserLocale", localUserLocale);
        localHashMap.put("SERVICE", localAccountService3);
        IReportResult localIReportResult = PositivePay.getReportData(localSecureUser, localReport.getReportCriteria(), localHashMap);
        localReport.setReportResult(localIReportResult);
        localHttpSession.setAttribute(PPAY_REPORT_NAME, localReport);
        String str2 = localReport.getReportCriteria().getReportOptions().getProperty("REPORTTYPE");
        if (str2.equals("Decision History"))
        {
          if ((localReport.getReportResult() instanceof PPayDecisionRpt)) {
            localHttpSession.setAttribute(PPAY_REPORT_RECORDS_NAME, ((PPayDecisionRpt)localReport.getReportResult()).getDecisions());
          }
        }
        else if ((str2.equals("Exceptions Summary")) && ((localReport.getReportResult() instanceof PPayIssueRpt))) {
          localHttpSession.setAttribute(PPAY_REPORT_RECORDS_NAME, ((PPayIssueRpt)localReport.getReportResult()).getIssues());
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.GetReportData
 * JD-Core Version:    0.7.0.1
 */