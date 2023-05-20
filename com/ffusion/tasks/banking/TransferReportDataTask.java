package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TransferReportDataTask
  extends BaseTask
  implements Task
{
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute("TransferReport");
    if (localReport == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    if (this.error != 0) {
      return super.getServiceErrorURL();
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("SERVICE", localHttpSession.getAttribute(this.bankingServiceName));
      Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BankingAccounts");
      if (localAccounts != null) {
        localHashMap.put("ACCOUNTS", localAccounts);
      }
      IReportResult localIReportResult = Banking.getReportData(localSecureUser, localReportCriteria, localHashMap);
      localReport.setReportResult(localIReportResult);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.TransferReportDataTask
 * JD-Core Version:    0.7.0.1
 */