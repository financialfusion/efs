package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPendingApprovalsCount
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap1 = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap2 = (HashMap)localHttpSession.getAttribute("SearchCriteria");
    try
    {
      localHttpSession.removeAttribute("PendingApprovalsCount");
      int i = 0;
      if (localHashMap2 == null) {
        i = Approvals.getNumberOfPendingApprovals(localSecureUser, localHashMap1);
      } else {
        i = Approvals.getNumberOfPendingApprovals(localSecureUser, localHashMap2, localHashMap1);
      }
      String str2 = String.valueOf(i);
      if (i >= 0) {
        localHttpSession.setAttribute("PendingApprovalsCount", str2);
      } else {
        localHttpSession.setAttribute("PendingApprovalsCount", "-1");
      }
    }
    catch (CSILException localCSILException)
    {
      localHttpSession.setAttribute("PendingApprovalsCount", "-1");
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetPendingApprovalsCount
 * JD-Core Version:    0.7.0.1
 */