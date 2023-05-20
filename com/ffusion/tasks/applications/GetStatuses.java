package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.bankemployee.BankEmployeeTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatuses
  extends BaseTask
  implements Task, BankEmployeeTask
{
  private String ux = "en_US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Status localStatus = new Status();
    localStatus.setSearchLanguage(this.ux);
    Statuses localStatuses1 = new Statuses();
    localHashMap.put("STATUSES", localStatuses1);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localStatus.setBankID(localSecureUser.getBankID());
    try
    {
      localStatuses1 = Applications.getStatuses(localSecureUser, localStatus, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if ((this.error == 0) || (this.error == 7053))
    {
      localHttpSession.setAttribute("Statuses", localStatuses1);
      Statuses localStatuses2 = new Statuses();
      localHttpSession.setAttribute("SearchStatuses", localStatuses2);
    }
    return str;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.ux = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetStatuses
 * JD-Core Version:    0.7.0.1
 */