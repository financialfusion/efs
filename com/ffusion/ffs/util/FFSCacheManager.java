package com.ffusion.ffs.util;

import com.ffusion.ffs.interfaces.FFSException;
import java.util.Hashtable;

public class FFSCacheManager
  implements FFSConst
{
  private Hashtable jdField_try = new Hashtable();
  
  public void createCache(FFSCache paramFFSCache)
    throws FFSException
  {
    if ((paramFFSCache == null) || (paramFFSCache.cacheId == null)) {
      throw new FFSException("Cache Id is missing");
    }
    this.jdField_try.put(paramFFSCache.cacheId, paramFFSCache);
  }
  
  public FFSCache getCache(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new FFSException("Cache Id is missing");
    }
    return (FFSCache)this.jdField_try.get(paramString);
  }
  
  public void removeCache(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return;
    }
    this.jdField_try.remove(paramString);
  }
  
  public boolean cacheExists(String paramString)
  {
    Object localObject = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return false;
    }
    localObject = this.jdField_try.get(paramString);
    return localObject != null;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSCacheManager
 * JD-Core Version:    0.7.0.1
 */