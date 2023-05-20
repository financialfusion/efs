package com.ffusion.beans.alerts;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;

public class UserAlerts
  extends FilteredList
{
  public static final String USER_ALERTS = "USER_ALERTS";
  
  public UserAlert getById(String paramString)
  {
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      UserAlert localUserAlert = (UserAlert)localIterator.next();
      if ((localUserAlert != null) && (localUserAlert.getUserAlertId().equals(paramString))) {
        return localUserAlert;
      }
    }
    return null;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "USER_ALERTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((UserAlert)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "USER_ALERTS");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("USER_ALERT"))
      {
        UserAlert localUserAlert = new UserAlert();
        localUserAlert.continueXMLParsing(getHandler());
        UserAlerts.this.add(localUserAlert);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.UserAlerts
 * JD-Core Version:    0.7.0.1
 */