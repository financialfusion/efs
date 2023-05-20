package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
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
    this.beanSessionName = "Payments";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    Payment localPayment = null;
    if (this.beanSessionName != null) {
      localPayment = (Payment)localHttpSession.getAttribute(this.beanSessionName);
    }
    if (localPayment == null)
    {
      localObject = (Payments)localHttpSession.getAttribute(this.collectionSessionName);
      if ((this.id == null) || (this.id.length() == 0))
      {
        this.error = 81;
        return this.taskErrorURL;
      }
      if (localObject == null)
      {
        this.error = 2003;
        return this.taskErrorURL;
      }
      localPayment = ((Payments)localObject).getByID(this.id);
      if (localPayment == null)
      {
        this.error = 2006;
        return this.taskErrorURL;
      }
    }
    Object localObject = null;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHashMap.put("UserLocale", localHttpSession.getAttribute("UserLocale"));
    try
    {
      localObject = Billpay.getAuditHistory(localSecureUser, localPayment, localHashMap);
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
 * Qualified Name:     com.ffusion.tasks.billpay.GetAuditHistory
 * JD-Core Version:    0.7.0.1
 */