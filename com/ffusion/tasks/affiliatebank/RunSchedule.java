package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RunSchedule
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  public RunSchedule()
  {
    this.beanSessionName = "ScheduleType";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ScheduleType localScheduleType = (ScheduleType)localHttpSession.getAttribute(this.beanSessionName);
    if (localScheduleType == null)
    {
      this.error = 26012;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    try
    {
      PaymentsAdmin.runSchedule(localSecureUser, localScheduleType.getInstructionType(), localScheduleType.getFIId(), localScheduleType.getCategory(), localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.RunSchedule
 * JD-Core Version:    0.7.0.1
 */