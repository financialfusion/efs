package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsStatuses;
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

public class GetPendingItems
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    HashMap localHashMap1 = new HashMap();
    int i = Integer.parseInt((String)localHttpSession.getAttribute("ApprovalsItemType"));
    HashMap localHashMap2 = (HashMap)localHttpSession.getAttribute("SearchCriteria");
    try
    {
      localHttpSession.removeAttribute("ApprovalsStatuses");
      ApprovalsStatuses localApprovalsStatuses = Approvals.getPendingItems(i, localHashMap2, localHashMap1);
      if (localApprovalsStatuses != null)
      {
        localApprovalsStatuses.setLocale(localLocale);
        localHttpSession.setAttribute("ApprovalsStatuses", localApprovalsStatuses);
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
 * Qualified Name:     com.ffusion.tasks.approvals.GetPendingItems
 * JD-Core Version:    0.7.0.1
 */