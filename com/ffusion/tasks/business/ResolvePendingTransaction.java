package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsItemErrors;
import com.ffusion.beans.business.PendingTransaction;
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

public class ResolvePendingTransaction
  extends BaseTask
  implements BusinessTask
{
  private String gD = "PendingTransaction";
  private String gF = null;
  private boolean gE = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHashMap.put("BankId", localHttpSession.getAttribute("BankId"));
    PendingTransaction localPendingTransaction = (PendingTransaction)localHttpSession.getAttribute(this.gD);
    if (localPendingTransaction == null)
    {
      this.error = 4128;
      return this.taskErrorURL;
    }
    if (this.gF == null)
    {
      this.error = 4129;
      return this.taskErrorURL;
    }
    try
    {
      localHashMap.remove("ApprovalsItemErrors");
      localHttpSession.removeAttribute("ApprovalsItemErrors");
      Business.resolvePendingTransaction(localSecureUser, this.gF, localPendingTransaction, this.gE, localHashMap);
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
  
  public void setDecisionApprove(String paramString)
  {
    setDecision("Approved");
    setNotifyPrimary(false);
  }
  
  public void setDecisionRejectWithNotify(String paramString)
  {
    setDecision("Rejected");
    setNotifyPrimary(true);
  }
  
  public void setDecisionRejectWithoutNotify(String paramString)
  {
    setDecision("Rejected");
    setNotifyPrimary(false);
  }
  
  public void setDecision(String paramString)
  {
    this.gF = paramString;
  }
  
  public void setNotifyPrimary(boolean paramBoolean)
  {
    this.gE = paramBoolean;
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.gD = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.gD;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ResolvePendingTransaction
 * JD-Core Version:    0.7.0.1
 */