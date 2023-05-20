package com.ffusion.tasks.bankreport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportAccounts
  extends BaseTask
{
  public static final String DEFAULT_ACCOUNTS_NAME = "BankReportAccounts";
  private String Wz = "BankReportAccounts";
  private String WA;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.bankreport.BankReport localBankReport = (com.ffusion.beans.bankreport.BankReport)localHttpSession.getAttribute(this.WA);
    try
    {
      Accounts localAccounts = com.ffusion.csil.core.BankReport.getAccountsForReport(localSecureUser, localBankReport, new HashMap());
      localHttpSession.setAttribute(this.Wz, localAccounts);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setReportName(String paramString)
  {
    this.WA = paramString;
  }
  
  public void setAccountsName(String paramString)
  {
    this.Wz = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankreport.GetReportAccounts
 * JD-Core Version:    0.7.0.1
 */