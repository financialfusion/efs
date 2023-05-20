package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteStatus
  extends BaseTask
  implements Task
{
  protected String id = "0";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    Statuses localStatuses = (Statuses)localHttpSession.getAttribute("Statuses");
    if (localStatuses == null)
    {
      this.error = 7270;
      str = this.taskErrorURL;
    }
    else
    {
      Status localStatus = localStatuses.getByID(this.id);
      if (localStatus == null)
      {
        this.error = 7271;
        str = this.taskErrorURL;
      }
      else
      {
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          Applications.deleteStatus(localSecureUser, localStatus, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.DeleteStatus
 * JD-Core Version:    0.7.0.1
 */