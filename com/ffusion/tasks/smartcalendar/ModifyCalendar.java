package com.ffusion.tasks.smartcalendar;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.smartcalendar.SCBusinessDays;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyCalendar
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    SCCalendar localSCCalendar = (SCCalendar)localHttpSession.getAttribute("SCCalendar");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getTaskErrorURL();
    }
    if (localSCCalendar == null)
    {
      this.error = 35531;
      return super.getTaskErrorURL();
    }
    this.error = jdMethod_int(localSCCalendar);
    if (this.error != 0) {
      return super.getTaskErrorURL();
    }
    try
    {
      SmartCalendar.modifyCalendar(localSecureUser, localSCCalendar, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      str = this.taskErrorURL;
      this.error = localCSILException.getCode();
    }
    return str;
  }
  
  private int jdMethod_int(SCCalendar paramSCCalendar)
  {
    SCBusinessDays localSCBusinessDays = paramSCCalendar.getBusinessDays();
    if ((localSCBusinessDays == null) || (localSCBusinessDays.getBusinessDays() == null)) {
      return 35533;
    }
    if (localSCBusinessDays.getBusinessDays().indexOf("Y") < 0) {
      return 35533;
    }
    String str = paramSCCalendar.getCalendarName();
    if ((str == null) || (str.trim().length() <= 0)) {
      return 35532;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.smartcalendar.ModifyCalendar
 * JD-Core Version:    0.7.0.1
 */