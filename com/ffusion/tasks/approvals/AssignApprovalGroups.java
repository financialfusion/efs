package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsGroups;
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

public class AssignApprovalGroups
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ApprovalsGroups localApprovalsGroups = (ApprovalsGroups)localHttpSession.getAttribute("ApprovalsGroups");
    String str2 = (String)localHttpSession.getAttribute("ApprovalsUserID");
    int i = -1;
    try
    {
      if (str2 != null) {
        i = Integer.parseInt(str2);
      }
      Approvals.assignApprovalGroups(localSecureUser, i, localApprovalsGroups, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      str1 = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.AssignApprovalGroups
 * JD-Core Version:    0.7.0.1
 */