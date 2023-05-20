package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsItem;
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

public class ModifyApprovalItem
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    ApprovalsItem localApprovalsItem1 = (ApprovalsItem)localHttpSession.getAttribute("ApprovalsItem");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    try
    {
      localHashMap.put("ApprovalsUser", localSecureUser);
      ApprovalsItem localApprovalsItem2 = Approvals.modifyApprovalItem(localApprovalsItem1, localHashMap);
      localHttpSession.removeAttribute("ApprovalsItem");
      if (localApprovalsItem2 != null)
      {
        localApprovalsItem2.setLocale(localLocale);
        localHttpSession.setAttribute("ApprovalsItem", localApprovalsItem2);
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
 * Qualified Name:     com.ffusion.tasks.approvals.ModifyApprovalItem
 * JD-Core Version:    0.7.0.1
 */