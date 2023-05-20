package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployees;
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

public class GetApprovalsEntitledBusinessEmployees
  extends BaseTask
{
  private String aOc = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      localHttpSession.removeAttribute("ApprovalsEntitledBusinessEmployees");
      BusinessEmployees localBusinessEmployees = Approvals.getEntitledBusinessEmployees(localSecureUser, this.aOc, localHashMap);
      if (!localBusinessEmployees.isEmpty()) {
        localHttpSession.setAttribute("ApprovalsEntitledBusinessEmployees", localBusinessEmployees);
      }
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str;
  }
  
  public void setActivityType(String paramString)
  {
    this.aOc = paramString;
  }
  
  public String getActivityType()
  {
    return this.aOc;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetApprovalsEntitledBusinessEmployees
 * JD-Core Version:    0.7.0.1
 */