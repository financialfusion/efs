package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAuditHistory
  extends ExtendedBaseTask
{
  protected String auditLogSessionName = "ReportLogRecords";
  
  public GetAuditHistory()
  {
    this.beanSessionName = "ACHBatches";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    ACHBatch localACHBatch = null;
    if (this.beanSessionName != null) {
      localACHBatch = (ACHBatch)localHttpSession.getAttribute(this.beanSessionName);
    }
    if (localACHBatch == null)
    {
      localObject = (ACHBatches)localHttpSession.getAttribute(this.collectionSessionName);
      if ((this.id == null) || (this.id.length() == 0))
      {
        this.error = 81;
        return this.taskErrorURL;
      }
      if (localObject == null)
      {
        this.error = 16010;
        return this.taskErrorURL;
      }
      localACHBatch = ((ACHBatches)localObject).getByID(this.id);
    }
    if (localACHBatch == null)
    {
      this.error = 16002;
      return this.taskErrorURL;
    }
    Object localObject = null;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHashMap.put("UserLocale", localHttpSession.getAttribute("UserLocale"));
    try
    {
      localObject = ACH.getAuditHistory(localSecureUser, localACHBatch, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    Collections.sort((List)localObject);
    localHttpSession.setAttribute(this.auditLogSessionName, localObject);
    str = this.successURL;
    return str;
  }
  
  public void setAuditLogSessionName(String paramString)
  {
    this.auditLogSessionName = paramString;
  }
  
  public String getAuditLogSessionName()
  {
    return this.auditLogSessionName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetAuditHistory
 * JD-Core Version:    0.7.0.1
 */