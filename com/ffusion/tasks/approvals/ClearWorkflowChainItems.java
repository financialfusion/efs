package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ClearWorkflowChainItems
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ApprovalsLevel localApprovalsLevel = (ApprovalsLevel)localHttpSession.getAttribute("ApprovalsLevel");
    try
    {
      Approvals.clearWorkflowChainItems(localSecureUser, localApprovalsLevel, localHashMap);
      localHttpSession.removeAttribute("ApprovalsLevel");
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.ClearWorkflowChainItems
 * JD-Core Version:    0.7.0.1
 */