package com.ffusion.entitlements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ToIdLimit
  implements Limit, EntitlementCodes
{
  private String NS;
  private String NQ;
  private String NR;
  
  public ArrayList getToIds()
  {
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(this.NR, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      localArrayList.add(str);
    }
    return localArrayList;
  }
  
  public void setToIds(ArrayList paramArrayList)
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
    this.NR = localStringBuffer.toString();
  }
  
  public void setId(String paramString)
  {
    this.NS = paramString;
  }
  
  public String getId()
  {
    return this.NS;
  }
  
  public void setName(String paramString)
  {
    this.NQ = paramString;
  }
  
  public String getName()
  {
    return this.NQ;
  }
  
  public String getLimitValue()
  {
    return this.NR;
  }
  
  public void setLimitValue(String paramString)
  {
    this.NR = paramString;
  }
  
  public Limit copy()
  {
    ToIdLimit localToIdLimit = new ToIdLimit();
    localToIdLimit.setId(getId());
    localToIdLimit.setName(getName());
    localToIdLimit.setLimitValue(getLimitValue());
    return localToIdLimit;
  }
  
  public void set(Limit paramLimit)
  {
    if ((paramLimit != null) && ((paramLimit instanceof ToIdLimit)))
    {
      setId(paramLimit.getId());
      setName(paramLimit.getName());
      setLimitValue(paramLimit.getLimitValue());
    }
  }
  
  public boolean isLimitExceeded(Limit paramLimit)
  {
    if ((paramLimit instanceof ToIdLimit))
    {
      ToIdLimit localToIdLimit = (ToIdLimit)paramLimit;
      String str1 = localToIdLimit.getLimitValue();
      int i = 0;
      StringTokenizer localStringTokenizer1 = new StringTokenizer(this.NR, ",");
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
    if ((paramLimitable instanceof ToIdLimitable))
    {
      ToIdLimitable localToIdLimitable = (ToIdLimitable)paramLimitable;
      String str1 = localToIdLimitable.getLimitableToID();
      if (str1 == null) {
        return false;
      }
      StringTokenizer localStringTokenizer = new StringTokenizer(this.NR, ",");
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
 * Qualified Name:     com.ffusion.entitlements.ToIdLimit
 * JD-Core Version:    0.7.0.1
 */