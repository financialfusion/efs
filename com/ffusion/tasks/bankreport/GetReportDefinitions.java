package com.ffusion.tasks.bankreport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankreport.BankReportDefinitions;
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

public class GetReportDefinitions
  extends BaseTask
{
  public static final String DEFAULT_REPORT_DEFINITIONS_NAME = "BankReportDefinitions";
  private String Wy = "BankReportDefinitions";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      BankReportDefinitions localBankReportDefinitions = BankReport.getReportDefinitions(localSecureUser, new HashMap());
      localHttpSession.setAttribute(this.Wy, localBankReportDefinitions);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setReportDefinitionsName(String paramString)
  {
    this.Wy = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankreport.GetReportDefinitions
 * JD-Core Version:    0.7.0.1
 */