package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.user.ContactPoints;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FillBankMessageAlert
  extends FillUserAlert
{
  protected boolean _init = false;
  protected boolean _updateExisitngAlertBean = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this._init)
    {
      localObject = (UserAlert)localHttpSession.getAttribute(this._alertSessionKey);
      if (localObject == null)
      {
        this.error = 19001;
        str = this.taskErrorURL;
      }
      else
      {
        initDeliveryInfos(localHttpSession);
      }
      return str;
    }
    Object localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ContactPoints localContactPoints = (ContactPoints)localHttpSession.getAttribute(this._contactPointsCollections);
    if (localContactPoints == null)
    {
      this.error = 19307;
      return this.taskErrorURL;
    }
    if (jdMethod_long(localHttpSession))
    {
      UserAlert localUserAlert;
      if (this._updateExisitngAlertBean) {
        localUserAlert = (UserAlert)localHttpSession.getAttribute(this._alertSessionKey);
      } else {
        localUserAlert = new UserAlert();
      }
      localUserAlert.setAlertType(6);
      localUserAlert.setStatus(1);
      localUserAlert.setDirectoryId(((SecureUser)localObject).getProfileID());
      localUserAlert.setDeliveryInfos(constructDeliveryInfos(localHttpSession));
      localHttpSession.setAttribute(this._alertSessionKey, localUserAlert);
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  private boolean jdMethod_long(HttpSession paramHttpSession)
  {
    return validateDeliveryInfos(paramHttpSession);
  }
  
  public void setInit(String paramString)
  {
    try
    {
      this._init = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getInit()
  {
    return Boolean.toString(this._init);
  }
  
  public void setUpdateExisitngAlertBean(String paramString)
  {
    try
    {
      this._updateExisitngAlertBean = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getUpdateExisitngAlertBean()
  {
    return Boolean.toString(this._updateExisitngAlertBean);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.FillBankMessageAlert
 * JD-Core Version:    0.7.0.1
 */