package com.ffusion.util;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class ContextPoolList
{
  private Vector a = null;
  
  public ContextPool getContextPool(String paramString)
    throws Exception
  {
    if (this.a == null) {
      return null;
    }
    ContextPool localContextPool = null;
    Enumeration localEnumeration = this.a.elements();
    while (localEnumeration.hasMoreElements())
    {
      localContextPool = (ContextPool)localEnumeration.nextElement();
      if ((localContextPool != null) && (localContextPool.getProperties().getProperty("java.naming.provider.url").equals(paramString))) {
        return localContextPool;
      }
    }
    return null;
  }
  
  public void add(Object paramObject)
  {
    if ((paramObject instanceof ContextPool)) {
      this.a.addElement(paramObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ContextPoolList
 * JD-Core Version:    0.7.0.1
 */