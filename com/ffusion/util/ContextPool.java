package com.ffusion.util;

import com.ffusion.util.logging.DebugLog;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.InitialContext;

public class ContextPool
{
  private Properties jdField_byte = null;
  private int jdField_try = 4;
  private int jdField_for = 2;
  private int jdField_int = 10;
  private Vector jdField_new = null;
  private static final String jdField_do = "com.ibm.websphere.naming.jndicache.cacheobject";
  private static final String a = "cleared";
  private static final String jdField_if = "none";
  
  public ContextPool(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.jdField_byte.put("java.naming.factory.initial", paramString4);
    this.jdField_byte.put("java.naming.provider.url", paramString1);
    this.jdField_byte.put("java.naming.security.principal", paramString2);
    this.jdField_byte.put("java.naming.security.credentials", paramString3);
    this.jdField_byte.put("com.ibm.websphere.naming.jndicache.cacheobject", "none");
  }
  
  public int getInitialContexts()
  {
    return this.jdField_try;
  }
  
  public void setInitialContexts(int paramInt)
  {
    this.jdField_try = paramInt;
  }
  
  public int getIncrementalContexts()
  {
    return this.jdField_for;
  }
  
  public void setIncrementalContexts(int paramInt)
  {
    this.jdField_for = paramInt;
  }
  
  public int getMaxContexts()
  {
    return this.jdField_int;
  }
  
  public void setMaxContexts(int paramInt)
  {
    this.jdField_int = paramInt;
  }
  
  public Properties getProperties()
  {
    return this.jdField_byte;
  }
  
  public synchronized void createPool()
    throws Exception
  {
    if (this.jdField_new != null) {
      return;
    }
    this.jdField_new = new Vector();
    jdField_if(this.jdField_try);
  }
  
  private void jdField_if(int paramInt)
    throws Exception
  {
    for (int i = 0; (i < paramInt) && ((this.jdField_int <= 0) || (this.jdField_new.size() < this.jdField_int)); i++)
    {
      this.jdField_new.addElement(new a(new InitialContext(this.jdField_byte)));
      DebugLog.log(Level.INFO, "Context created...");
    }
  }
  
  public synchronized Context getContext()
    throws Exception
  {
    if (this.jdField_new == null) {
      return null;
    }
    Context localContext = jdField_if();
    int i = 0;
    while (localContext == null)
    {
      a(250);
      localContext = jdField_if();
      if (i++ > 20) {
        break;
      }
    }
    return localContext;
  }
  
  private Context jdField_if()
    throws Exception
  {
    Context localContext = a();
    if (localContext == null)
    {
      jdField_if(this.jdField_for);
      localContext = a();
      if (localContext == null) {
        return null;
      }
    }
    return localContext;
  }
  
  private Context a()
    throws Exception
  {
    Object localObject = null;
    a locala = null;
    Enumeration localEnumeration = this.jdField_new.elements();
    while (localEnumeration.hasMoreElements())
    {
      locala = (a)localEnumeration.nextElement();
      if (!locala.a())
      {
        localObject = locala.jdField_if();
        locala.a(true);
        if (!a((Context)localObject))
        {
          localObject = new InitialContext(this.jdField_byte);
          locala.a((Context)localObject);
        }
      }
    }
    return localObject;
  }
  
  private boolean a(Context paramContext)
  {
    return true;
  }
  
  public void returnContext(Context paramContext)
  {
    if (this.jdField_new == null) {
      return;
    }
    a locala = null;
    Enumeration localEnumeration = this.jdField_new.elements();
    while (localEnumeration.hasMoreElements())
    {
      locala = (a)localEnumeration.nextElement();
      if (paramContext == locala.jdField_if()) {
        locala.a(false);
      }
    }
  }
  
  public Context refreshContext(Context paramContext)
    throws Exception
  {
    if (this.jdField_new == null) {
      return null;
    }
    a locala = null;
    Enumeration localEnumeration = this.jdField_new.elements();
    while (localEnumeration.hasMoreElements())
    {
      locala = (a)localEnumeration.nextElement();
      if (paramContext == locala.jdField_if())
      {
        jdField_if(locala.jdField_if());
        locala.a(new InitialContext(this.jdField_byte));
        locala.a(false);
        return locala.jdField_if();
      }
    }
    return null;
  }
  
  public synchronized void refreshContexts()
    throws Exception
  {
    if (this.jdField_new == null) {
      return;
    }
    a locala = null;
    Enumeration localEnumeration = this.jdField_new.elements();
    while (localEnumeration.hasMoreElements())
    {
      locala = (a)localEnumeration.nextElement();
      if (!locala.a()) {
        a(10000);
      }
      jdField_if(locala.jdField_if());
      locala.a(new InitialContext(this.jdField_byte));
      locala.a(false);
    }
  }
  
  public synchronized void closeContexts()
  {
    if (this.jdField_new == null) {
      return;
    }
    a locala = null;
    Enumeration localEnumeration = this.jdField_new.elements();
    while (localEnumeration.hasMoreElements())
    {
      locala = (a)localEnumeration.nextElement();
      if (!locala.a()) {
        a(5000);
      }
      jdField_if(locala.jdField_if());
      this.jdField_new.removeElement(locala);
    }
    this.jdField_new = null;
  }
  
  private void jdField_if(Context paramContext)
  {
    try
    {
      paramContext.close();
    }
    catch (Exception localException) {}
  }
  
  private void a(int paramInt)
  {
    try
    {
      Thread.sleep(paramInt);
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  class a
  {
    Context a = null;
    boolean jdField_if = false;
    
    public a(Context paramContext)
    {
      this.a = paramContext;
    }
    
    public Context jdField_if()
    {
      return this.a;
    }
    
    public void a(Context paramContext)
    {
      this.a = paramContext;
    }
    
    public boolean a()
    {
      return this.jdField_if;
    }
    
    public void a(boolean paramBoolean)
    {
      this.jdField_if = paramBoolean;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ContextPool
 * JD-Core Version:    0.7.0.1
 */