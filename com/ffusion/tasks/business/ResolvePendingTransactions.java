package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsDecisions;
import com.ffusion.beans.approvals.ApprovalsItemErrors;
import com.ffusion.beans.business.PendingTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResolvePendingTransactions
  extends BaseTask
  implements BusinessTask
{
  private String g7 = null;
  private String g5 = null;
  private String g6 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHashMap.put("BankId", localHttpSession.getAttribute("BankId"));
    PendingTransactions localPendingTransactions = (PendingTransactions)localHttpSession.getAttribute(this.g5);
    ApprovalsDecisions localApprovalsDecisions = (ApprovalsDecisions)localHttpSession.getAttribute(this.g7);
    boolean[] arrayOfBoolean = (boolean[])localHttpSession.getAttribute(this.g6);
    if (localPendingTransactions == null)
    {
      this.error = 4128;
      return this.taskErrorURL;
    }
    if (localApprovalsDecisions == null)
    {
      this.error = 4129;
      return this.taskErrorURL;
    }
    try
    {
      localHashMap.remove("ApprovalsItemErrors");
      localHttpSession.removeAttribute("ApprovalsItemErrors");
      Business.resolvePendingTransactions(localSecureUser, localApprovalsDecisions, localPendingTransactions, arrayOfBoolean, localHashMap);
      if (localHashMap.containsKey("ApprovalsItemErrors"))
      {
        ApprovalsItemErrors localApprovalsItemErrors = (ApprovalsItemErrors)localHashMap.get("ApprovalsItemErrors");
        localHttpSession.setAttribute("ApprovalsItemErrors", localApprovalsItemErrors);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setDecisionsSessionName(String paramString)
  {
    this.g7 = paramString;
  }
  
  public void setPendingTransactionsSessionName(String paramString)
  {
    this.g5 = paramString;
  }
  
  public void setNotifyPrimaryContactSessionName(String paramString)
  {
    this.g6 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ResolvePendingTransactions
 * JD-Core Version:    0.7.0.1
 */