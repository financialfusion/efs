package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
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
  implements ReportingConsts
{
  private ReportIdentifications T4;
  private String T3 = "ReportIdentifications";
  
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
      this.T4 = Reporting.getReports(localSecureUser, localHashMap);
      localHttpSession.setAttribute(this.T3, this.T4);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public ReportIdentifications getIdentifications()
  {
    return this.T4;
  }
  
  public void setIdentificationsName(String paramString)
  {
    this.T3 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GetReports
 * JD-Core Version:    0.7.0.1
 */