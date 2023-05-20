package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.efs.tasks.SessionNames;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.reporting.GenerateReportBase;
import java.io.IOException;
import java.util.HashMap;
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
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BillPay localBillPay = (BillPay)localHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = new HashMap();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    localHashMap.put("UserLocale", localUserLocale);
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
    if (localAccounts != null) {
      localHashMap.put("ACCOUNTS", localAccounts);
    }
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    if (localPayees != null) {
      localHashMap.put("PAYEES", localPayees);
    }
    Report localReport = (Report)localHttpSession.getAttribute(GenerateReportBase.REPORT_NAME);
    if (localReport == null)
    {
      this.error = 64;
      str = this.taskErrorURL;
    }
    else
    {
      ReportCriteria localReportCriteria = localReport.getReportCriteria();
      try
      {
        IReportResult localIReportResult = Billpay.getReportData(localSecureUser, localReportCriteria, localHashMap);
        localReport.setReportResult(localIReportResult);
        localHttpSession.setAttribute(SessionNames.PAYMENT_REPORT_NAME, localReport);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      catch (Exception localException) {}
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetReportData
 * JD-Core Version:    0.7.0.1
 */