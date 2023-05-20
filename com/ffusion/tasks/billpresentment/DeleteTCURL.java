package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.TCURL;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteTCURL
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    TCURL localTCURL = (TCURL)localHttpSession.getAttribute("TCURL");
    if (localTCURL == null)
    {
      str = this.taskErrorURL;
      this.error = 6513;
    }
    else
    {
      this.error = 0;
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        BillPresentment.deleteTCURL(localSecureUser, localTCURL, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0)
      {
        str = this.successURL;
        localHttpSession.setAttribute("TCURL", null);
      }
      else
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.DeleteTCURL
 * JD-Core Version:    0.7.0.1
 */