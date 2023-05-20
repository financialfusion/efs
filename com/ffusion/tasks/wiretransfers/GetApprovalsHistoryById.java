package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApprovalsHistoryById
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected static boolean reload = false;
  
  public GetApprovalsHistoryById()
  {
    this.beanSessionName = "ApprovalsHistory";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransfers localWireTransfers = (WireTransfers)localHttpSession.getAttribute(this.beanSessionName);
    String str = this.successURL;
    this.error = 0;
    if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    if (reload)
    {
      localWireTransfers = null;
      localHttpSession.removeAttribute(this.beanSessionName);
      reload = false;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localWireTransfers = Wire.getWireApprovalAuditHistory(localSecureUser, this.id, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    Collections.sort(localWireTransfers);
    localHttpSession.setAttribute(this.beanSessionName, localWireTransfers);
    str = this.successURL;
    return str;
  }
  
  public void setReload(String paramString)
  {
    reload = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetApprovalsHistoryById
 * JD-Core Version:    0.7.0.1
 */