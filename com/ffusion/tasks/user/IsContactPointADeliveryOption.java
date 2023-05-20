package com.ffusion.tasks.user;

import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsContactPointADeliveryOption
  extends BaseTask
{
  public static final String CONTACT_POINT = "CONTACT_POINT";
  public static final String USER_ALERT = "USER_ALERT";
  protected String _contactPointSessionName = "CONTACT_POINT";
  protected String _userAlertSessionName = "USER_ALERT";
  protected boolean _contactPointIsDeliveryOption = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    this._contactPointIsDeliveryOption = false;
    ContactPoint localContactPoint = (ContactPoint)localHttpSession.getAttribute(this._contactPointSessionName);
    UserAlert localUserAlert = (UserAlert)localHttpSession.getAttribute(this._userAlertSessionName);
    if (localContactPoint == null)
    {
      this.error = 3540;
      return this.taskErrorURL;
    }
    if (localUserAlert == null)
    {
      this.error = 19001;
      return this.taskErrorURL;
    }
    DeliveryInfos localDeliveryInfos = localUserAlert.getDeliveryInfos();
    DeliveryInfo localDeliveryInfo = localDeliveryInfos.getByProperty("ContactPointID", "" + localContactPoint.getContactPointID());
    if (localDeliveryInfo != null) {
      this._contactPointIsDeliveryOption = true;
    }
    return str;
  }
  
  public void setContactPointSessionName(String paramString)
  {
    this._contactPointSessionName = paramString;
  }
  
  public void setUserAlertSessionName(String paramString)
  {
    this._userAlertSessionName = paramString;
  }
  
  public boolean getIsContactPointADeliveryOption()
  {
    return this._contactPointIsDeliveryOption;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.IsContactPointADeliveryOption
 * JD-Core Version:    0.7.0.1
 */