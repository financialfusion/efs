package com.ffusion.efs.tasks.util;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UseDemoMode
  extends ExtendedBaseTask
  implements Task
{
  protected String sessionName = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    boolean bool = false;
    try
    {
      bool = Util.useDemoMode();
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      if (bool == true) {
        localHttpSession.setAttribute(this.sessionName, "true");
      } else {
        localHttpSession.setAttribute(this.sessionName, "false");
      }
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.util.UseDemoMode
 * JD-Core Version:    0.7.0.1
 */