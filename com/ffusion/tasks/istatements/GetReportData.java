package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportData
  extends BaseTask
{
  private SecureUser Pf;
  private Locale Pg;
  private Report Ph;
  private String Pe = "ReportData";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.Pf = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.Pg = BaseTask.getLocale(localHttpSession, this.Pf);
    this.Ph = ((Report)localHttpSession.getAttribute(this.Pe));
    if (validateInput()) {
      try
      {
        IReportResult localIReportResult = null;
        ReportCriteria localReportCriteria = this.Ph.getReportCriteria();
        localIReportResult = StatementData.getReportData(this.Pf, localReportCriteria, new HashMap());
        this.Ph.setReportResult(localIReportResult);
      }
      catch (CSILException localCSILException)
      {
        this.error = localCSILException.code;
        str = this.serviceErrorURL;
      }
    } else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setReportSessionName(String paramString)
  {
    if (paramString == null) {
      this.Pe = "ReportData";
    } else {
      this.Pe = paramString;
    }
  }
  
  public String getReportSessionName()
  {
    return this.Pe;
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.Pf == null))
    {
      this.error = 38;
      bool = false;
    }
    if ((bool) && (this.Ph == null))
    {
      this.error = 36217;
      bool = false;
    }
    if ((bool) && ((this.Pe == null) || (this.Pe.length() == 0)))
    {
      this.error = 36219;
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetReportData
 * JD-Core Version:    0.7.0.1
 */