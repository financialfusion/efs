package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.LogMessages;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetLogMessages
  extends BaseTask
  implements Task
{
  protected String serviceName = "com.ffusion.services.Alerts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)localHttpSession.getAttribute(this.serviceName);
    String str;
    if (getLogMessages(localHttpSession, localAlerts)) {
      str = this.successURL;
    } else if (this.error == 19000) {
      str = this.taskErrorURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getLogMessages(HttpSession paramHttpSession, com.ffusion.services.Alerts paramAlerts)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    LogMessages localLogMessages = new LogMessages(localLocale);
    HashMap localHashMap = new HashMap();
    if (paramAlerts != null) {
      localHashMap.put("SERVICE", paramAlerts);
    }
    localHashMap.put("LOGMESSAGES", localLogMessages);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localLogMessages = com.ffusion.csil.core.Alerts.getLogMessages(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute("LogMessages", localLogMessages);
    }
    return this.error == 0;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.GetLogMessages
 * JD-Core Version:    0.7.0.1
 */