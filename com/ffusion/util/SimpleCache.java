package com.ffusion.util;

import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class SimpleCache
  extends HashMap
{
  private static int jdField_if = 100;
  private static ArrayList a = new ArrayList(100);
  
  public Object put(Object paramObject1, Object paramObject2)
  {
    if (a.size() >= jdField_if)
    {
      super.remove(a.get(a.size() - 1));
      a.remove(a.size() - 1);
    }
    Object localObject1 = super.put(paramObject1, paramObject2);
    synchronized (a)
    {
      if ((localObject1 != null) && (!a.remove(paramObject1))) {
        DebugLog.log(Level.SEVERE, "SimpleCache.put():- data in Cache and LRU are not in sync");
      }
      a.add(0, paramObject1);
    }
    return super.put(paramObject1, paramObject2);
  }
  
  public Object get(Object paramObject)
  {
    Object localObject1 = super.get(paramObject);
    if (localObject1 != null) {
      synchronized (a)
      {
        if (a.remove(paramObject)) {
          a.add(0, paramObject);
        } else {
          DebugLog.log(Level.SEVERE, "SimpleCache.get():- data in Cache and LRU are not in sync");
        }
      }
    }
    return localObject1;
  }
  
  public void setCacheSize(int paramInt)
  {
    if (paramInt > 0)
    {
      synchronized (a)
      {
        if (a.size() > paramInt) {
          for (int i = a.size() - 1; i >= paramInt; i--)
          {
            Object localObject1 = a.remove(i);
            super.remove(localObject1);
          }
        }
      }
      jdField_if = paramInt;
    }
  }
  
  public int getCacheSize()
  {
    return jdField_if;
  }
  
  public void purge()
  {
    synchronized (a)
    {
      if (a.size() > 0) {
        for (int i = a.size() - 1; i >= 0; i--)
        {
          Object localObject1 = a.remove(i);
          super.remove(localObject1);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.SimpleCache
 * JD-Core Version:    0.7.0.1
 */