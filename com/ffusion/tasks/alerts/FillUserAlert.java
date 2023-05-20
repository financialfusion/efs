package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.tasks.BaseTask;
import javax.servlet.http.HttpSession;

public abstract class FillUserAlert
  extends BaseTask
  implements Task
{
  protected String _alertSessionKey = "Alert";
  protected String _deliveryOptionsPrefix = "DELIVERY_OPTION";
  protected String _contactPointsCollections = "CONTACT_POINTS";
  
  public void setSessionKey(String paramString)
  {
    this._alertSessionKey = paramString;
  }
  
  public String getSessionKey()
  {
    return this._alertSessionKey;
  }
  
  public void setDeliveryOptionsPrefix(String paramString)
  {
    this._deliveryOptionsPrefix = paramString;
  }
  
  public String getDeliveryOptionsPrefix()
  {
    return this._deliveryOptionsPrefix;
  }
  
  public void setContactPointsCollectionsKey(String paramString)
  {
    this._contactPointsCollections = paramString;
  }
  
  public String getContactPointsCollectionsKey()
  {
    return this._contactPointsCollections;
  }
  
  public DeliveryInfos constructDeliveryInfos(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    DeliveryInfos localDeliveryInfos = new DeliveryInfos(localSecureUser.getLocale());
    ContactPoints localContactPoints = (ContactPoints)paramHttpSession.getAttribute(this._contactPointsCollections);
    if (localContactPoints != null) {
      for (int i = 0; i < localContactPoints.size(); i++)
      {
        int j = Integer.parseInt((String)paramHttpSession.getAttribute(this._deliveryOptionsPrefix + i));
        if ((j == 1) || (j == 2))
        {
          ContactPoint localContactPoint = (ContactPoint)localContactPoints.get(i);
          DeliveryInfo localDeliveryInfo = (DeliveryInfo)localDeliveryInfos.create();
          localDeliveryInfo.setPropertyKey("ContactPointID");
          localDeliveryInfo.setPropertyValue(Integer.toString(localContactPoint.getContactPointID()));
          localDeliveryInfo.setOrder(j);
        }
      }
    }
    return localDeliveryInfos;
  }
  
  public void initDeliveryInfos(HttpSession paramHttpSession)
  {
    ContactPoints localContactPoints = (ContactPoints)paramHttpSession.getAttribute(this._contactPointsCollections);
    UserAlert localUserAlert = (UserAlert)paramHttpSession.getAttribute(this._alertSessionKey);
    DeliveryInfos localDeliveryInfos = localUserAlert.getDeliveryInfos();
    if (localContactPoints != null) {
      for (int i = 0; i < localContactPoints.size(); i++)
      {
        ContactPoint localContactPoint = (ContactPoint)localContactPoints.get(i);
        DeliveryInfo localDeliveryInfo = localDeliveryInfos.getByProperty("ContactPointID", "" + localContactPoint.getContactPointID());
        paramHttpSession.setAttribute(this._deliveryOptionsPrefix + i, localDeliveryInfo == null ? Integer.toString(0) : localDeliveryInfo.getOrder());
      }
    }
  }
  
  public boolean validateDeliveryInfos(HttpSession paramHttpSession)
  {
    ContactPoints localContactPoints = (ContactPoints)paramHttpSession.getAttribute(this._contactPointsCollections);
    if ((localContactPoints != null) && (localContactPoints.size() > 0))
    {
      for (int i = 0; i < localContactPoints.size(); i++)
      {
        int j = Integer.parseInt((String)paramHttpSession.getAttribute(this._deliveryOptionsPrefix + i));
        if (j == 1) {
          return true;
        }
      }
      this.error = 19308;
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.FillUserAlert
 * JD-Core Version:    0.7.0.1
 */