package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroupMembers;
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

public class GetApprovalGroupMembers
  extends BaseTask
{
  private String aOp = "ApprovalsGroup";
  private String aOo = "ApprovalsGroupMembers";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ApprovalsGroup localApprovalsGroup = (ApprovalsGroup)localHttpSession.getAttribute(this.aOp);
    ApprovalsGroupMembers localApprovalsGroupMembers = null;
    try
    {
      int i = -1;
      if (localApprovalsGroup != null) {
        i = localApprovalsGroup.getApprovalsGroupID();
      }
      localApprovalsGroupMembers = Approvals.getApprovalGroupMembers(localSecureUser, i, localHashMap);
      localHttpSession.setAttribute(this.aOo, localApprovalsGroupMembers);
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str;
  }
  
  public void setApprovalsGroupSessionName(String paramString)
  {
    this.aOp = ((paramString == null) || (paramString.length() == 0) ? "ApprovalsGroup" : paramString);
  }
  
  public void setApprovalsGroupMembersSessionName(String paramString)
  {
    this.aOo = ((paramString == null) || (paramString.length() == 0) ? "ApprovalsGroupMembers" : paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetApprovalGroupMembers
 * JD-Core Version:    0.7.0.1
 */