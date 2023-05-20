package com.ffusion.tasks.smartcalendar;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCalendarForName
  extends BaseTask
{
  private String aNw = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      BankIdentifier localBankIdentifier = (BankIdentifier)localHttpSession.getAttribute("SCBankIdentifier");
      localHttpSession.removeAttribute("SCCalendar");
      SCCalendar localSCCalendar = SmartCalendar.getCalendarForName(localSecureUser, this.aNw, new HashMap());
      if (localSCCalendar != null) {
        localHttpSession.setAttribute("SCCalendar", localSCCalendar);
      }
    }
    catch (CSILException localCSILException)
    {
      str = this.taskErrorURL;
      this.error = localCSILException.getCode();
    }
    return str;
  }
  
  public void setCalendarName(String paramString)
  {
    this.aNw = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.smartcalendar.GetCalendarForName
 * JD-Core Version:    0.7.0.1
 */