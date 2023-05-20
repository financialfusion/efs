package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAuditTransferById
  extends ExtendedBaseTask
  implements Task
{
  protected String trackingId;
  protected boolean reload = false;
  
  public GetAuditTransferById()
  {
    this.beanSessionName = "AuditTransfers";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ReportLogRecords localReportLogRecords = (ReportLogRecords)localHttpSession.getAttribute(this.beanSessionName);
    String str = this.successURL;
    this.error = 0;
    if ((this.trackingId == null) || (this.trackingId.length() == 0))
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    if (this.reload)
    {
      localReportLogRecords = null;
      localHttpSession.removeAttribute(this.beanSessionName);
      this.reload = false;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHashMap.put("UserLocale", localHttpSession.getAttribute("UserLocale"));
    try
    {
      localReportLogRecords = Banking.getAuditTransferByID(localSecureUser, this.trackingId, this.id, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    Collections.sort(localReportLogRecords);
    localHttpSession.setAttribute(this.beanSessionName, localReportLogRecords);
    str = this.successURL;
    return str;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingId = paramString;
  }
  
  public String getTrackingID()
  {
    return this.trackingId;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetAuditTransferById
 * JD-Core Version:    0.7.0.1
 */