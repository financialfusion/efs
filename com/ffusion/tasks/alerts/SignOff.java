package com.ffusion.tasks.alerts;

import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected String serviceName = "com.ffusion.services.Alerts";
  
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    paramHttpSession.removeAttribute(this.serviceName);
    paramHttpSession.removeAttribute("com.ffusion.services.Alerts");
    paramHttpSession.removeAttribute("Alert");
    paramHttpSession.removeAttribute("Alerts");
    paramHttpSession.removeAttribute("LogMessages");
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
 * Qualified Name:     com.ffusion.tasks.alerts.SignOff
 * JD-Core Version:    0.7.0.1
 */