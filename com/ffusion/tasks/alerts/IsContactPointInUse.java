package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Alerts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsContactPointInUse
  extends BaseTask
{
  public static final String CONTACT_POINT = "CONTACT_POINT";
  protected String _sessionName = "CONTACT_POINT";
  protected int _numberOfAlertsUsingContactPoint = -1;
  protected int _numberOfAlertsExclusivelyUsingContactPoint = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ContactPoint localContactPoint = (ContactPoint)localHttpSession.getAttribute(this._sessionName);
    if (localContactPoint == null)
    {
      this.error = 3540;
      return this.taskErrorURL;
    }
    this._numberOfAlertsUsingContactPoint = 0;
    this._numberOfAlertsExclusivelyUsingContactPoint = 0;
    HashMap localHashMap = new HashMap();
    try
    {
      UserAlerts localUserAlerts = Alerts.getUserAlerts(localSecureUser, localSecureUser.getProfileID(), localHashMap);
      for (int i = 0; i < localUserAlerts.size(); i++)
      {
        UserAlert localUserAlert = (UserAlert)localUserAlerts.get(i);
        DeliveryInfos localDeliveryInfos = localUserAlert.getDeliveryInfos();
        for (int j = 0; j < localDeliveryInfos.size(); j++)
        {
          DeliveryInfo localDeliveryInfo = (DeliveryInfo)localDeliveryInfos.get(j);
          localDeliveryInfo.setPropertyKey("ContactPointID");
          String str2 = localDeliveryInfo.getProperty();
          int k = -1;
          try
          {
            k = Integer.parseInt(str2);
          }
          catch (NumberFormatException localNumberFormatException) {}
          if (localContactPoint.getContactPointID() == k)
          {
            this._numberOfAlertsUsingContactPoint += 1;
            if (localDeliveryInfos.size() == 1) {
              this._numberOfAlertsExclusivelyUsingContactPoint += 1;
            }
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setSessionName(String paramString)
  {
    this._sessionName = paramString;
  }
  
  public int getNumberOfAlertsUsingContactPoint()
  {
    return this._numberOfAlertsUsingContactPoint;
  }
  
  public int getNumberOfAlertsExclusivelyUsingContactPoint()
  {
    return this._numberOfAlertsExclusivelyUsingContactPoint;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.IsContactPointInUse
 * JD-Core Version:    0.7.0.1
 */