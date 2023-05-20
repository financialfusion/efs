package com.ffusion.tasks.approvals;

import com.ffusion.beans.approvals.ApprovalsGroup;
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

public class IsApprovalGroupInUse
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    ApprovalsGroup localApprovalsGroup = (ApprovalsGroup)localHttpSession.getAttribute("ApprovalsGroup");
    try
    {
      localHttpSession.removeAttribute("IsApprovalsGroupInUse");
      int i = -1;
      Boolean localBoolean = null;
      if (localApprovalsGroup != null) {
        i = localApprovalsGroup.getApprovalsGroupID();
      }
      if (i != -1) {
        localBoolean = Approvals.isApprovalGroupInUse(i, localHashMap) ? Boolean.TRUE : Boolean.FALSE;
      }
      localHttpSession.setAttribute("IsApprovalsGroupInUse", localBoolean);
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
 * Qualified Name:     com.ffusion.tasks.approvals.IsApprovalGroupInUse
 * JD-Core Version:    0.7.0.1
 */