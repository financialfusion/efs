package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditStatus
  extends Status
  implements Task
{
  protected String id = "0";
  protected String successURL;
  protected int error = 0;
  protected String taskErrorURL;
  protected String serviceErrorURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
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
          Applications.modifyStatus(localSecureUser, localStatus, localHashMap);
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
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.EditStatus
 * JD-Core Version:    0.7.0.1
 */