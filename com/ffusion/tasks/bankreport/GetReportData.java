package com.ffusion.tasks.bankreport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.reporting.GenerateReportBase;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportData
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute(GenerateReportBase.REPORT_NAME);
    com.ffusion.beans.bankreport.BankReport localBankReport = (com.ffusion.beans.bankreport.BankReport)localHttpSession.getAttribute("SelectedBankReport");
    if (localBankReport == null)
    {
      localBankReport = new com.ffusion.beans.bankreport.BankReport(Locale.getDefault());
      localReport.getReportCriteria().setCurrentSearchCriterion("Report_HIDE");
      localBankReport.setReportID(Integer.parseInt(localReport.getReportCriteria().getCurrentSearchCriterionValue()));
      localReport.getReportCriteria().setCurrentReportOption("REPORTTYPE");
      localBankReport.setType(localReport.getReportCriteria().getCurrentReportOptionValue());
    }
    HashMap localHashMap = new HashMap();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    localHashMap.put("UserLocale", localUserLocale);
    if ((localReport == null) || (localReport.getReportCriteria() == null))
    {
      this.error = 64;
      str1 = this.taskErrorURL;
    }
    else
    {
      ReportCriteria localReportCriteria = localReport.getReportCriteria();
      String str2 = localReportCriteria.getSearchCriteria().getProperty("Accounts");
      Object localObject;
      if ((str2 != null) && (!str2.equals("")))
      {
        localObject = new StringTokenizer(str2, ", ");
        Accounts localAccounts1 = null;
        Accounts localAccounts2 = new Accounts(localSecureUser.getLocale());
        try
        {
          localAccounts1 = com.ffusion.csil.core.BankReport.getAccountsForReport(localSecureUser, localBankReport, new HashMap());
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
          str1 = this.serviceErrorURL;
        }
        catch (Exception localException2) {}
        while (((StringTokenizer)localObject).hasMoreTokens())
        {
          String str3 = ((StringTokenizer)localObject).nextToken();
          Account localAccount = localAccounts1.getByID(str3);
          if (localAccount != null) {
            localAccounts2.add(localAccount);
          }
        }
        localHashMap.put("ACCOUNTS", localAccounts2);
      }
      try
      {
        localObject = com.ffusion.csil.core.BankReport.getReportData(localSecureUser, localReportCriteria, localHashMap);
        localReport.setReportResult((IReportResult)localObject);
        localHttpSession.setAttribute("BankReportData", localReport);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str1 = this.serviceErrorURL;
      }
      catch (Exception localException1) {}
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankreport.GetReportData
 * JD-Core Version:    0.7.0.1
 */