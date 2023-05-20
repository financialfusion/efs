package com.ffusion.tasks.smartcalendar;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.BankIdentifier;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPreviousDay
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
      Date localDate1 = (Date)localHttpSession.getAttribute("SCCurrentDay");
      BankIdentifier localBankIdentifier = (BankIdentifier)localHttpSession.getAttribute("SCBankIdentifier");
      localHttpSession.removeAttribute("SCPreviousDay");
      Date localDate2 = SmartCalendar.getPreviousDay(localSecureUser, localBankIdentifier, localDate1, new HashMap());
      if (localDate2 != null) {
        localHttpSession.setAttribute("SCPreviousDay", localDate2);
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
 * Qualified Name:     com.ffusion.tasks.smartcalendar.GetPreviousDay
 * JD-Core Version:    0.7.0.1
 */