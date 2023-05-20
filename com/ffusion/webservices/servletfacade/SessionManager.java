package com.ffusion.webservices.servletfacade;

import com.ffusion.util.logging.DebugLog;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class SessionManager
{
  private static HttpSessionContext jdField_for = null;
  private static Hashtable jdField_try = null;
  private static int jdField_if = 1000;
  private static int jdField_new = 1800;
  private static SecureRandom jdField_int = null;
  private static final String jdField_do = "timeOut";
  private static final String a = "maxSessions";
  public static final String msg_limitReached = "SessionManager: cannot create a new session as the limit on maximum number of sessions has already been reached: (%1)";
  public static final String msg_sessionType = "SessionManager: can manage only SimpleSessions";
  public static final String msg_maxSessionsNotValid = "SessionManager: maximum number of sessions should be an integral number; using a default value (%1)";
  public static final String msg_timeoutNotValid = "SessionManager: session timeout value should be an integral number; using a default value (%1)";
  public static final String msg_SessionManagerDefaultInit = "SessionManager: Default values for maximum number of sessions is %1 with a time out value of %2 seconds";
  public static final String msg_SessionManagerUnbindError = "SessionManager: Failed to call unbind method on an object bound to the session by name (%1) due to %2";
  public static final String msg_SessionManagerInit = "SessionManager: Maximum number of sessions (%1) with a time out value of (%2) seconds";
  
  private void a()
  {
    a locala = new a();
    locala.start();
  }
  
  public static HttpSession createSession()
  {
    String str = generateSID();
    Session localSession = new Session(str, jdField_new);
    localSession.setNew();
    if (jdField_try.size() < jdField_if) {
      jdField_try.put(str, localSession);
    } else {
      localSession = null;
    }
    return localSession;
  }
  
  public static HttpSession getSessionForID(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    Session localSession = (Session)jdField_try.get(paramString);
    if (localSession == null) {
      return null;
    }
    localSession.unsetNew();
    return localSession;
  }
  
  public static void deleteSession(HttpSession paramHttpSession)
  {
    if (paramHttpSession == null) {
      return;
    }
    if ((paramHttpSession instanceof Session))
    {
      ((Session)paramHttpSession).removeAllObjects();
      jdField_try.remove(((Session)paramHttpSession).getIdWithoutExceptions());
    }
  }
  
  public static HttpSessionContext getContext()
  {
    return jdField_for;
  }
  
  public static String generateSID()
  {
    return Long.toHexString(jdField_int.nextLong()) + Long.toHexString(jdField_int.nextLong());
  }
  
  static
  {
    jdField_try = new Hashtable(jdField_if);
    try
    {
      jdField_int = SecureRandom.getInstance("SHA1PRNG");
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "An error has occurred while getting Secure Random Generator while intializing Web Services Session Manager.");
    }
    SessionManager localSessionManager = new SessionManager();
    localSessionManager.a();
  }
  
  public class a
    extends Thread
  {
    private boolean a = false;
    
    public a() {}
    
    public void run()
    {
      long l = 15000L;
      for (;;)
      {
        try
        {
          sleep(l);
        }
        catch (InterruptedException localInterruptedException) {}catch (Throwable localThrowable) {}
        a();
      }
    }
    
    public void a()
    {
      if (!this.a)
      {
        this.a = true;
        Enumeration localEnumeration = SessionManager.try.elements();
        if (localEnumeration == null) {
          return;
        }
        while (localEnumeration.hasMoreElements())
        {
          HttpSession localHttpSession = (HttpSession)localEnumeration.nextElement();
          try
          {
            localHttpSession.getSessionContext();
          }
          catch (IllegalStateException localIllegalStateException)
          {
            SessionManager.deleteSession(localHttpSession);
          }
        }
        this.a = false;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.servletfacade.SessionManager
 * JD-Core Version:    0.7.0.1
 */