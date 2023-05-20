package com.ffusion.efs.tasks.util;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AuditLog;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAuditLog
  extends ExtendedBaseTask
  implements Task
{
  protected String agentId = "";
  protected String agentType = "";
  protected DateTime fromDate = null;
  protected DateTime toDate = null;
  
  public GetAuditLog()
  {
    this.collectionSessionName = "AuditLogRecords";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ReportLogRecords localReportLogRecords = null;
    int i = 0;
    try
    {
      if ((this.id != null) && (this.id.length() > 0)) {
        i = Integer.parseInt(this.id);
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (i <= 0)
    {
      this.error = 38;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        localReportLogRecords = AuditLog.getAuditEntries(localSecureUser, this.id, this.agentId, this.agentType, this.fromDate, this.toDate, null);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("AuditLogRecords", localReportLogRecords);
      }
    }
    return str;
  }
  
  public void setAgentId(String paramString)
  {
    this.agentId = paramString;
  }
  
  public String getAgentId()
  {
    return this.agentId;
  }
  
  public void setAgentType(String paramString)
  {
    this.agentType = paramString;
  }
  
  public String getAgentType()
  {
    return this.agentType;
  }
  
  public void setFromDate(String paramString)
  {
    try
    {
      if ((paramString == null) || (paramString.equals("")))
      {
        this.fromDate = null;
      }
      else
      {
        if (this.fromDate == null) {
          this.fromDate = new DateTime(this.locale);
        }
        this.fromDate.setFormat(this.dateFormat);
        this.fromDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setFromDate(DateTime paramDateTime)
  {
    this.fromDate = paramDateTime;
  }
  
  public String getFromDate()
  {
    if (this.fromDate == null) {
      return null;
    }
    this.fromDate.setFormat(this.dateFormat);
    return this.fromDate.toString();
  }
  
  public void setToDate(String paramString)
  {
    try
    {
      if ((paramString == null) || (paramString.equals("")))
      {
        this.toDate = null;
      }
      else
      {
        if (this.toDate == null) {
          this.toDate = new DateTime(this.locale);
        }
        this.toDate.setFormat(this.dateFormat);
        this.toDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setToDate(DateTime paramDateTime)
  {
    this.toDate = paramDateTime;
  }
  
  public String getToDate()
  {
    if (this.toDate == null) {
      return null;
    }
    this.toDate.setFormat(this.dateFormat);
    return this.toDate.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.util.GetAuditLog
 * JD-Core Version:    0.7.0.1
 */