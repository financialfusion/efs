package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Reporting;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddReport
  extends BaseTask
  implements ReportingConsts
{
  private String Tu = "Report";
  
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
      Report localReport = (Report)localHttpSession.getAttribute(this.Tu);
      if ((this.error = validateName(localReport.getReportID().getReportName(), localSecureUser.getLocale())) != 0) {
        return super.getTaskErrorURL();
      }
      if ((this.error = validateDescription(localReport.getReportID().getDescription(), localSecureUser.getLocale())) != 0) {
        return super.getTaskErrorURL();
      }
      ReportIdentification localReportIdentification = Reporting.getReport(localSecureUser, localReport.getReportID().getReportName(), localHashMap);
      if (localReportIdentification != null)
      {
        this.error = 37002;
        return super.getTaskErrorURL();
      }
      Reporting.addReport(localSecureUser, localReport, localHashMap);
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
    this.Tu = paramString;
  }
  
  protected int validateName(String paramString, Locale paramLocale)
  {
    if (paramString.length() <= 0) {
      return 37000;
    }
    if (paramString.length() > 40) {
      return 37000;
    }
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if ((!Character.isLetterOrDigit(c)) && (!Character.isSpaceChar(c)) && (c != '_') && (c != '/') && (c != '-')) {
        return 37000;
      }
    }
    return 0;
  }
  
  protected int validateDescription(String paramString, Locale paramLocale)
  {
    if (paramString.length() > 255) {
      return 37001;
    }
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if ((!Character.isLetterOrDigit(c)) && (!Character.isSpaceChar(c)) && (c != '_') && (c != '/') && (c != '-')) {
        return 37001;
      }
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.AddReport
 * JD-Core Version:    0.7.0.1
 */