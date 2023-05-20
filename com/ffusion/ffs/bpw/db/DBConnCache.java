package com.ffusion.ffs.bpw.db;

import java.util.HashMap;
import java.util.Set;

public class DBConnCache
{
  private static HashMap jdField_if = new HashMap();
  private static String a = "DBConnCacheKey";
  
  public static Object lookup(String paramString)
  {
    Object localObject1 = null;
    synchronized (jdField_if)
    {
      localObject1 = jdField_if.get(paramString);
    }
    return localObject1;
  }
  
  public static void bind(String paramString, Object paramObject)
    throws Exception
  {
    synchronized (jdField_if)
    {
      jdField_if.put(paramString, paramObject);
    }
  }
  
  public static void unbind(String paramString)
    throws Exception
  {
    synchronized (jdField_if)
    {
      jdField_if.remove(paramString);
    }
  }
  
  public static Object[] list()
    throws Exception
  {
    Object[] arrayOfObject = new Object[0];
    synchronized (jdField_if)
    {
      arrayOfObject = jdField_if.keySet().toArray();
    }
    return arrayOfObject;
  }
  
  public static void unregisterAll()
  {
    synchronized (jdField_if)
    {
      jdField_if.clear();
    }
  }
  
  public static synchronized String save(Object paramObject)
    throws Exception
  {
    String str = getNewBatchKey();
    bind(str, paramObject);
    return str;
  }
  
  public static String getNewBatchKey()
    throws Exception
  {
    String str = a + DBUtil.getNextIndexString("BatchDBTransID");
    return str;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.DBConnCache
 * JD-Core Version:    0.7.0.1
 */