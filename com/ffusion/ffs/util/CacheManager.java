package com.ffusion.ffs.util;

import com.ffusion.ffs.interfaces.FFSException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class CacheManager
  implements FFSConst
{
  private static HashMap F = new HashMap();
  
  public static void createCache(BPWCache paramBPWCache)
    throws FFSException
  {
    Hashtable localHashtable = null;
    if ((paramBPWCache == null) || (paramBPWCache.getCacheId() == null)) {
      throw new FFSException("Cache Id is missing");
    }
    localHashtable = new Hashtable();
    F.put(paramBPWCache.getCacheId(), localHashtable);
  }
  
  public static void addItemToCache(BPWCache paramBPWCache)
    throws FFSException
  {
    Hashtable localHashtable = null;
    String str = null;
    Object localObject = null;
    if (paramBPWCache == null) {
      return;
    }
    if (paramBPWCache.getCacheId() == null) {
      throw new FFSException("Cache Id is missing");
    }
    str = paramBPWCache.getKey();
    if (str == null) {
      throw new FFSException("Key is missing");
    }
    localObject = paramBPWCache.getValue();
    if (localObject == null) {
      throw new FFSException("Value is missing");
    }
    localHashtable = (Hashtable)F.get(paramBPWCache.getCacheId());
    localHashtable.put(str, localObject);
    FFSDebug.log("CacheManager.addItemToCache:: adding key=" + str + " value=" + localObject, 6);
  }
  
  public static void addItemsToCache(BPWCache[] paramArrayOfBPWCache)
    throws FFSException
  {
    Hashtable localHashtable = null;
    String str1 = null;
    String str2 = null;
    Object localObject = null;
    if (paramArrayOfBPWCache == null) {
      return;
    }
    for (int i = 0; i < paramArrayOfBPWCache.length; i++)
    {
      str1 = paramArrayOfBPWCache[i].getCacheId();
      if (str1 == null) {
        throw new FFSException("Cache Id is missing");
      }
      str2 = paramArrayOfBPWCache[i].getKey();
      if (str2 == null) {
        throw new FFSException("Key is missing for item number " + i);
      }
      localObject = paramArrayOfBPWCache[i].getValue();
      if (localObject == null) {
        throw new FFSException("Value is missing for item number " + i);
      }
      localHashtable = (Hashtable)F.get(str1);
      localHashtable.put(str2, localObject);
      FFSDebug.log("CacheManager.addItemsToCache:: adding key=" + str2 + " value=" + localObject, 6);
    }
  }
  
  public static Object getItem(BPWCache paramBPWCache)
  {
    Hashtable localHashtable = null;
    String str = null;
    Object localObject = null;
    if (paramBPWCache == null) {
      return null;
    }
    if (paramBPWCache.getCacheId() == null) {
      return null;
    }
    str = paramBPWCache.getKey();
    if (str == null) {
      return null;
    }
    localHashtable = (Hashtable)F.get(paramBPWCache.getCacheId());
    localObject = localHashtable.get(str);
    return localObject;
  }
  
  public static void removeItem(BPWCache paramBPWCache)
  {
    Hashtable localHashtable = null;
    String str = null;
    if ((paramBPWCache == null) || (paramBPWCache.getCacheId() == null)) {
      return;
    }
    str = paramBPWCache.getKey();
    if (str == null) {
      return;
    }
    localHashtable = (Hashtable)F.get(paramBPWCache.getCacheId());
    localHashtable.remove(str);
  }
  
  public static void removeCache(BPWCache paramBPWCache)
  {
    if ((paramBPWCache == null) || (paramBPWCache.getCacheId() == null)) {
      return;
    }
    F.remove(paramBPWCache.getCacheId());
  }
  
  public static void clearCache(BPWCache paramBPWCache)
  {
    Hashtable localHashtable = null;
    if ((paramBPWCache == null) || (paramBPWCache.getCacheId() == null)) {
      return;
    }
    localHashtable = (Hashtable)F.get(paramBPWCache.getCacheId());
    localHashtable.clear();
  }
  
  public static boolean cacheExists(BPWCache paramBPWCache)
  {
    Hashtable localHashtable = null;
    if ((paramBPWCache == null) || (paramBPWCache.getCacheId() == null)) {
      return false;
    }
    localHashtable = (Hashtable)F.get(paramBPWCache.getCacheId());
    return localHashtable != null;
  }
  
  public static void dumpCache(String paramString, PrintWriter paramPrintWriter)
  {
    Hashtable localHashtable = null;
    Enumeration localEnumeration = null;
    String str = null;
    Object localObject = null;
    if ((paramPrintWriter == null) || (paramString == null)) {
      return;
    }
    localHashtable = (Hashtable)F.get(paramString);
    localEnumeration = localHashtable.keys();
    paramPrintWriter.println("Cache:" + paramString);
    paramPrintWriter.println("=============================");
    while (localEnumeration.hasMoreElements())
    {
      str = (String)localEnumeration.nextElement();
      localObject = localHashtable.get(str);
      if ((localObject instanceof String)) {
        paramPrintWriter.println(str + " : " + localObject);
      } else if ((localObject instanceof Integer)) {
        paramPrintWriter.println(str + " : " + ((Integer)localObject).intValue());
      } else if ((localObject instanceof Long)) {
        paramPrintWriter.println(str + " : " + ((Long)localObject).longValue());
      }
    }
    paramPrintWriter.println("\nEnd of Cache:" + paramString);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.CacheManager
 * JD-Core Version:    0.7.0.1
 */