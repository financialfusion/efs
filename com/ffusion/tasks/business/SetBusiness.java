package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBusiness
  extends BaseTask
  implements BusinessTask
{
  protected int id;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Businesses localBusinesses = (Businesses)localHttpSession.getAttribute("Businesses");
    com.ffusion.beans.business.Business localBusiness1 = null;
    com.ffusion.beans.business.Business localBusiness2 = new com.ffusion.beans.business.Business();
    localBusiness2.setId(this.id);
    if (localBusinesses != null) {
      localBusiness1 = localBusinesses.getById(this.id);
    }
    if (localBusiness1 == null) {
      try
      {
        localBusiness1 = com.ffusion.csil.core.Business.getBusiness(localSecureUser, localBusiness2, null);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if (localBusiness1 != null)
    {
      localHttpSession.setAttribute("Business", localBusiness1);
      str = this.successURL;
    }
    else if (this.error == 0)
    {
      str = this.taskErrorURL;
      this.error = 4101;
    }
    return str;
  }
  
  public final void setId(String paramString)
  {
    try
    {
      this.id = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("SetBusiness Task Exception: ", localException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.SetBusiness
 * JD-Core Version:    0.7.0.1
 */