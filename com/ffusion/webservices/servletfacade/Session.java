package com.ffusion.webservices.servletfacade;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;

public class Session
  implements HttpSession
{
  private String jdField_if = null;
  private long jdField_try = 0L;
  private long jdField_byte = 0L;
  private boolean jdField_int = false;
  private long a = 1800000L;
  private boolean jdField_new = true;
  private Hashtable jdField_for = new Hashtable();
  private int jdField_do = 0;
  
  public Session(String paramString, int paramInt)
  {
    this.jdField_if = paramString;
    this.a = (paramInt * 1000);
    this.jdField_byte = (this.jdField_try = System.currentTimeMillis());
  }
  
  public long getCreationTime()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return this.jdField_try;
  }
  
  public String getId()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return this.jdField_if;
  }
  
  public long getLastAccessedTime()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return this.jdField_byte;
  }
  
  public int getMaxInactiveInterval()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return (int)(this.a / 1000L);
  }
  
  public void setMaxInactiveInterval(int paramInt)
  {
    if (a()) {
      throw new IllegalStateException();
    }
    this.a = (paramInt * 1000);
    if (paramInt <= 0)
    {
      SessionManager.deleteSession(this);
      this.jdField_new = false;
    }
  }
  
  public HttpSessionContext getSessionContext()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return SessionManager.getContext();
  }
  
  public ServletContext getServletContext()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return null;
  }
  
  public Object getAttribute(String paramString)
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return this.jdField_for.get(paramString);
  }
  
  public Object getValue(String paramString)
  {
    return getAttribute(paramString);
  }
  
  public Enumeration getAttributeNames()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return this.jdField_for.keys();
  }
  
  public String[] getValueNames()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    String[] arrayOfString = null;
    synchronized (this.jdField_for)
    {
      arrayOfString = new String[this.jdField_for.size()];
      int i = 0;
      Enumeration localEnumeration = this.jdField_for.keys();
      while (localEnumeration.hasMoreElements())
      {
        arrayOfString[i] = ((String)localEnumeration.nextElement());
        i++;
      }
    }
    return arrayOfString;
  }
  
  public void invalidate()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    SessionManager.deleteSession(this);
    this.jdField_new = false;
  }
  
  public void setAttribute(String paramString, Object paramObject)
  {
    if (a()) {
      throw new IllegalStateException();
    }
    Object localObject = this.jdField_for.remove(paramString);
    if (localObject != null) {
      a(paramString, localObject);
    }
    if ((paramObject instanceof HttpSessionBindingListener))
    {
      HttpSessionBindingListener localHttpSessionBindingListener = (HttpSessionBindingListener)paramObject;
      localHttpSessionBindingListener.valueBound(new HttpSessionBindingEvent(this, paramString));
      this.jdField_do += 1;
    }
    this.jdField_for.put(paramString, paramObject);
  }
  
  public void putValue(String paramString, Object paramObject)
  {
    setAttribute(paramString, paramObject);
  }
  
  public void removeAttribute(String paramString)
  {
    if (a()) {
      throw new IllegalStateException();
    }
    Object localObject = this.jdField_for.remove(paramString);
    if (localObject != null) {
      a(paramString, localObject);
    }
  }
  
  public void removeValue(String paramString)
  {
    removeAttribute(paramString);
  }
  
  private boolean a()
  {
    if (!this.jdField_new) {
      return true;
    }
    if (System.currentTimeMillis() - this.jdField_byte >= this.a)
    {
      this.jdField_new = false;
      return true;
    }
    return false;
  }
  
  public boolean isNew()
  {
    if (a()) {
      throw new IllegalStateException();
    }
    return this.jdField_int;
  }
  
  protected void setNew()
  {
    this.jdField_int = true;
  }
  
  protected void unsetNew()
  {
    this.jdField_int = false;
    this.jdField_byte = System.currentTimeMillis();
  }
  
  protected String getIdWithoutExceptions()
  {
    return this.jdField_if;
  }
  
  protected synchronized void removeAllObjects()
  {
    if (this.jdField_do > 0)
    {
      Enumeration localEnumeration = this.jdField_for.keys();
      while (localEnumeration.hasMoreElements())
      {
        String str = (String)localEnumeration.nextElement();
        Object localObject = this.jdField_for.get(str);
        if (localObject != null)
        {
          try
          {
            a(str, localObject);
          }
          catch (Exception localException) {}
          if (this.jdField_do == 0) {
            break;
          }
        }
      }
    }
    this.jdField_for = null;
  }
  
  private void a(String paramString, Object paramObject)
  {
    if ((paramObject instanceof HttpSessionBindingListener))
    {
      HttpSessionBindingListener localHttpSessionBindingListener = (HttpSessionBindingListener)paramObject;
      localHttpSessionBindingListener.valueUnbound(new HttpSessionBindingEvent(this, paramString));
      this.jdField_do -= 1;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.servletfacade.Session
 * JD-Core Version:    0.7.0.1
 */