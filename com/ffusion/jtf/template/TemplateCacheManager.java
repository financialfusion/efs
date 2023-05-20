package com.ffusion.jtf.template;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class TemplateCacheManager
  implements Serializable
{
  public static final String CACHE_MANAGER = "com.ffusion.jtf.template.TemplateCacheManager";
  private HashMap a = new HashMap();
  private HashMap jdField_if = new HashMap();
  private boolean jdField_do;
  
  public TemplateCacheManager(boolean paramBoolean)
  {
    this.jdField_do = paramBoolean;
  }
  
  public String getTemplateCacheKey(String[] paramArrayOfString)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramArrayOfString[0]);
    for (int i = 1; i < paramArrayOfString.length; i++)
    {
      localStringBuffer.append(';');
      localStringBuffer.append(paramArrayOfString[i]);
    }
    return localStringBuffer.toString();
  }
  
  public TemplateCache getTemplateCache(String paramString)
  {
    TemplateCache localTemplateCache = (TemplateCache)this.jdField_if.get(paramString);
    if (localTemplateCache == null)
    {
      localTemplateCache = a(paramString);
      this.jdField_if.put(paramString, localTemplateCache);
    }
    return localTemplateCache;
  }
  
  private TemplateCache a(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
    TemplatePathCache[] arrayOfTemplatePathCache = new TemplatePathCache[localStringTokenizer.countTokens()];
    int i = 0;
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      TemplatePathCache localTemplatePathCache = (TemplatePathCache)this.a.get(str);
      if (localTemplatePathCache == null)
      {
        localTemplatePathCache = new TemplatePathCache(str, this.jdField_do);
        this.a.put(str, localTemplatePathCache);
      }
      arrayOfTemplatePathCache[(i++)] = localTemplatePathCache;
    }
    return new TemplateCache(arrayOfTemplatePathCache, this.jdField_do);
  }
  
  public void clearCache()
  {
    Iterator localIterator = this.a.values().iterator();
    while (localIterator.hasNext())
    {
      TemplatePathCache localTemplatePathCache = (TemplatePathCache)localIterator.next();
      localTemplatePathCache.clearCache();
    }
  }
  
  public void setUseCache(boolean paramBoolean)
  {
    this.jdField_do = paramBoolean;
    Iterator localIterator = this.a.values().iterator();
    while (localIterator.hasNext())
    {
      TemplatePathCache localTemplatePathCache = (TemplatePathCache)localIterator.next();
      localTemplatePathCache.setUseCache(paramBoolean);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.TemplateCacheManager
 * JD-Core Version:    0.7.0.1
 */