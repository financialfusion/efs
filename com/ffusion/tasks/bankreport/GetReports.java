package com.ffusion.tasks.bankreport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankreport.BankReports;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankReport;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReports
  extends BaseTask
{
  public static final String DEFAULT_REPORTS_NAME = "BankReports";
  private String WB = "BankReports";
  private String WC;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      BankReports localBankReports = BankReport.getReports(localSecureUser, this.WC, new HashMap());
      localHttpSession.setAttribute(this.WB, localBankReports);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setReportType(String paramString)
  {
    this.WC = paramString;
  }
  
  public void setReportsName(String paramString)
  {
    this.WB = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankreport.GetReports
 * JD-Core Version:    0.7.0.1
 */