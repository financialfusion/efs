package com.ffusion.ffs.util;

import com.ffusion.ffs.interfaces.FFSException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Set;

public class FFSSrvrsRegistry
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
    a.remove(paramString);
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
  
  public static Object getAnySrvr()
    throws FFSException
  {
    Object[] arrayOfObject = list();
    if (arrayOfObject.length != 0) {
      return lookup((String)arrayOfObject[0]);
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSSrvrsRegistry
 * JD-Core Version:    0.7.0.1
 */