package com.ffusion.http.listeners;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.core.TTYAdmin;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class TTYSessionListener
  implements HttpSessionAttributeListener, HttpSessionListener
{
  public static final String LOGIN_FAILURE = "LoginFailure";
  public static final String LOGIN_ESTABLISHED = "UserLoginEstablished";
  
  public void sessionCreated(HttpSessionEvent paramHttpSessionEvent) {}
  
  public void sessionDestroyed(HttpSessionEvent paramHttpSessionEvent)
  {
    SecureUser localSecureUser = getSecureUser(paramHttpSessionEvent.getSession());
    TTYAdmin.logout(localSecureUser, paramHttpSessionEvent.getSession().getId());
  }
  
  protected SecureUser getSecureUser(HttpSession paramHttpSession)
  {
    Object localObject = paramHttpSession.getAttribute("SecureUser");
    SecureUser localSecureUser = null;
    if ((localObject instanceof SecureUser)) {
      localSecureUser = (SecureUser)localObject;
    }
    return localSecureUser;
  }
  
  public void attributeAdded(HttpSessionBindingEvent paramHttpSessionBindingEvent) {}
  
  public void attributeRemoved(HttpSessionBindingEvent paramHttpSessionBindingEvent) {}
  
  public void attributeReplaced(HttpSessionBindingEvent paramHttpSessionBindingEvent) {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.http.listeners.TTYSessionListener
 * JD-Core Version:    0.7.0.1
 */