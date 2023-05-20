package com.ffusion.entitlements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class FromIdLimit
  implements Limit, EntitlementCodes
{
  private String NM;
  private String NK;
  private String NL;
  
  public ArrayList getFromIds()
  {
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(this.NL, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      localArrayList.add(str);
    }
    return localArrayList;
  }
  
  public void setFromIds(ArrayList paramArrayList)
  {
    int i = 1;
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramArrayList.iterator();
    if (localIterator.hasNext())
    {
      if (i == 0) {
        localStringBuffer.append(",");
      }
      i = 0;
      Object localObject = localIterator.next();
      localStringBuffer.append(localObject.toString());
    }
    this.NL = localStringBuffer.toString();
  }
  
  public void setId(String paramString)
  {
    this.NM = paramString;
  }
  
  public String getId()
  {
    return this.NM;
  }
  
  public void setName(String paramString)
  {
    this.NK = paramString;
  }
  
  public String getName()
  {
    return this.NK;
  }
  
  public String getLimitValue()
  {
    return this.NL;
  }
  
  public void setLimitValue(String paramString)
  {
    this.NL = paramString;
  }
  
  public Limit copy()
  {
    FromIdLimit localFromIdLimit = new FromIdLimit();
    localFromIdLimit.setId(getId());
    localFromIdLimit.setName(getName());
    localFromIdLimit.setLimitValue(getLimitValue());
    return localFromIdLimit;
  }
  
  public void set(Limit paramLimit)
  {
    if ((paramLimit != null) && ((paramLimit instanceof FromIdLimit)))
    {
      setId(paramLimit.getId());
      setName(paramLimit.getName());
      setLimitValue(paramLimit.getLimitValue());
    }
  }
  
  public boolean isLimitExceeded(Limit paramLimit)
  {
    if ((paramLimit instanceof FromIdLimit))
    {
      FromIdLimit localFromIdLimit = (FromIdLimit)paramLimit;
      String str1 = localFromIdLimit.getLimitValue();
      int i = 0;
      StringTokenizer localStringTokenizer1 = new StringTokenizer(this.NL, ",");
      while (localStringTokenizer1.hasMoreTokens())
      {
        i = 0;
        String str2 = localStringTokenizer1.nextToken();
        StringTokenizer localStringTokenizer2 = new StringTokenizer(str1, ",");
        while (localStringTokenizer2.hasMoreTokens())
        {
          String str3 = localStringTokenizer2.nextToken();
          if (str3.equals(str2)) {
            i = 1;
          }
        }
        if (i == 0) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean isLimitExceeded(Limitable paramLimitable)
  {
    if ((paramLimitable instanceof FromIdLimitable))
    {
      FromIdLimitable localFromIdLimitable = (FromIdLimitable)paramLimitable;
      String str1 = localFromIdLimitable.getLimitableFromID();
      if (str1 == null) {
        return false;
      }
      StringTokenizer localStringTokenizer = new StringTokenizer(this.NL, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str2 = localStringTokenizer.nextToken();
        if (str1.compareTo(str2) == 0) {
          return true;
        }
      }
    }
    return false;
  }
  
  public HashMap getNameValuePairs()
  {
    HashMap localHashMap = new HashMap();
    if (getLimitValue() != null) {
      localHashMap.put("LimitValue", getLimitValue());
    }
    return localHashMap;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.FromIdLimit
 * JD-Core Version:    0.7.0.1
 */