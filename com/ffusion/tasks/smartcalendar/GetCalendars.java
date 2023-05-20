package com.ffusion.tasks.smartcalendar;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.smartcalendar.SCCalendars;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCalendars
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localHttpSession.removeAttribute("SCCalendars");
      SCCalendars localSCCalendars = SmartCalendar.getCalendars(localSecureUser, new HashMap());
      if (localSCCalendars != null) {
        localHttpSession.setAttribute("SCCalendars", localSCCalendars);
      }
    }
    catch (CSILException localCSILException)
    {
      str = this.taskErrorURL;
      this.error = localCSILException.getCode();
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.smartcalendar.GetCalendars
 * JD-Core Version:    0.7.0.1
 */