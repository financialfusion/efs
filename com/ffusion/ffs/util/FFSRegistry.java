package com.ffusion.ffs.util;

import com.ffusion.ffs.interfaces.FFSException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Set;

public class FFSRegistry
{
  private static HashMap a = new HashMap();
  
  public static Object lookup(String paramString)
  {
    return a.get(paramString);
  }
  
  public static void bind(String paramString, Object paramObject)
    throws AlreadyBoundException, FFSException
  {
    a.put(paramString, paramObject);
  }
  
  public static void unbind(String paramString)
    throws NotBoundException, FFSException
  {
    Object localObject = a.remove(paramString);
    localObject = null;
  }
  
  public static Object[] list()
    throws FFSException
  {
    return a.keySet().toArray();
  }
  
  public static void unregisterAll()
  {
    a.clear();
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSRegistry
 * JD-Core Version:    0.7.0.1
 */