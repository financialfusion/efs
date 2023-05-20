package com.ffusion.util;

import java.text.CollationKey;
import java.text.Collator;

public class CollatorWrapper
  extends Collator
{
  Collator a = null;
  
  public CollatorWrapper()
  {
    this.a = CollatorUtil.getCollator();
  }
  
  public CollatorWrapper(Collator paramCollator)
  {
    if (paramCollator == null) {
      this.a = CollatorUtil.getCollator();
    } else {
      this.a = paramCollator;
    }
  }
  
  public Object clone()
  {
    return new CollatorWrapper((Collator)this.a.clone());
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    try
    {
      return this.a.compare(paramObject1, paramObject1);
    }
    catch (NullPointerException localNullPointerException) {}
    return a(paramObject1, paramObject2);
  }
  
  public int compare(String paramString1, String paramString2)
  {
    try
    {
      return this.a.compare(paramString1, paramString2);
    }
    catch (NullPointerException localNullPointerException) {}
    return a(paramString1, paramString2);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if ((paramObject instanceof CollatorWrapper)) {
      return this.a.equals(((CollatorWrapper)paramObject).a);
    }
    return false;
  }
  
  public boolean equals(String paramString1, String paramString2)
  {
    try
    {
      return this.a.equals(paramString1, paramString2);
    }
    catch (NullPointerException localNullPointerException)
    {
      if ((paramString1 == null) && (paramString2 == null)) {
        tmpTernaryOp = true;
      }
    }
    return false;
  }
  
  public CollationKey getCollationKey(String paramString)
  {
    return this.a.getCollationKey(paramString);
  }
  
  public int getDecomposition()
  {
    return this.a.getDecomposition();
  }
  
  public int getStrength()
  {
    return this.a.getStrength();
  }
  
  public int hashCode()
  {
    return this.a.hashCode();
  }
  
  public void setDecomposition(int paramInt)
  {
    this.a.setDecomposition(paramInt);
  }
  
  public void setStrength(int paramInt)
  {
    this.a.setStrength(paramInt);
  }
  
  private int a(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == null) {
      return paramObject2 == null ? 0 : -1;
    }
    return 1;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.CollatorWrapper
 * JD-Core Version:    0.7.0.1
 */