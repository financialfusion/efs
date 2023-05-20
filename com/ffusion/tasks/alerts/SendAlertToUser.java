package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendAlertToUser
  implements Task
{
  private String li;
  private String lj;
  protected static String USERID = "USERID";
  protected static String MESSAGE = "MESSAGE";
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String noAlertSentURL = null;
  protected String validate;
  protected boolean processFlag = false;
  protected String serviceName = "com.ffusion.services.Alerts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    str = processSendAlert(paramHttpServletRequest, localHttpSession);
    return str;
  }
  
  protected String processSendAlert(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = sendAlert(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String sendAlert(HttpSession paramHttpSession)
  {
    String str = null;
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    HashMap localHashMap = null;
    if (localAlerts != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAlerts);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      com.ffusion.csil.core.Alerts.sendAlertToUser(localSecureUser, this.li, this.lj, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      str = this.successURL;
    }
    else
    {
      str = this.noAlertSentURL;
      this.error = 19302;
      paramHttpSession.setAttribute("AlertSent", "false");
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if ((bool) && (this.validate.indexOf(USERID) != -1)) {
        bool = validateUserID();
      }
      if ((bool) && (this.validate.indexOf(MESSAGE) != -1)) {
        bool = validateMessage();
      }
      this.validate = null;
    }
    return bool;
  }
  
  protected boolean validateUserID()
  {
    this.error = 0;
    if ((this.li == null) || (this.li.trim().length() == 0)) {
      this.error = 19113;
    }
    return this.error == 0;
  }
  
  protected boolean validateMessage()
  {
    this.error = 0;
    if ((this.lj == null) || (this.lj.trim().length() == 0)) {
      this.error = 19107;
    }
    return this.error == 0;
  }
  
  public final void setUserID(String paramString)
  {
    this.li = paramString;
  }
  
  public final String getUserID()
  {
    return this.li;
  }
  
  public final void setMessage(String paramString)
  {
    this.lj = paramString;
  }
  
  public final String getMessage()
  {
    return this.lj;
  }
  
  public final void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public final void setNoAlertSentURL(String paramString)
  {
    this.noAlertSentURL = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.error);
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
 * Qualified Name:     com.ffusion.tasks.alerts.SendAlertToUser
 * JD-Core Version:    0.7.0.1
 */