package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsDecisions;
import com.ffusion.beans.approvals.ApprovalsItemErrors;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubmitDecisions
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    localHashMap.put("BankId", localHttpSession.getAttribute("BankId"));
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    ApprovalsDecisions localApprovalsDecisions = (ApprovalsDecisions)localHttpSession.getAttribute("ApprovalsDecisions");
    try
    {
      localHashMap.remove("ApprovalsItemErrors");
      localHttpSession.removeAttribute("ApprovalsItemErrors");
      localHashMap.remove("ExceededApprovalLimitsMap");
      localHttpSession.removeAttribute("ApprovalsExceededLimitsMap");
      Approvals.submitDecisions(localSecureUser, localApprovalsDecisions, localHashMap);
      Object localObject;
      if (localHashMap.containsKey("ApprovalsItemErrors"))
      {
        localObject = (ApprovalsItemErrors)localHashMap.get("ApprovalsItemErrors");
        ((ApprovalsItemErrors)localObject).setLocale(localLocale);
        localHttpSession.setAttribute("ApprovalsItemErrors", localObject);
      }
      if (localHashMap.containsKey("ExceededApprovalLimitsMap"))
      {
        localObject = (HashMap)localHashMap.get("ExceededApprovalLimitsMap");
        localHttpSession.setAttribute("ApprovalsExceededLimitsMap", localObject);
      }
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
 * Qualified Name:     com.ffusion.tasks.approvals.SubmitDecisions
 * JD-Core Version:    0.7.0.1
 */