package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Reporting;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteReport
  extends BaseTask
{
  private int Ur;
  private String Us;
  private String Uq;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
      HashMap localHashMap = new HashMap();
      localHashMap.put("UserLocale", localHttpSession.getAttribute("UserLocale"));
      ReportIdentification localReportIdentification = new ReportIdentification(this.Ur, this.Us, this.Uq);
      Reporting.deleteReport(localSecureUser, localReportIdentification, localHashMap);
      localHttpSession.removeAttribute("ReportNamesAndCategories");
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setIdentificationID(String paramString)
  {
    this.Ur = Integer.parseInt(paramString);
  }
  
  public void setIdentificationName(String paramString)
  {
    this.Us = paramString;
  }
  
  public void setIdentificationDesc(String paramString)
  {
    this.Uq = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.DeleteReport
 * JD-Core Version:    0.7.0.1
 */