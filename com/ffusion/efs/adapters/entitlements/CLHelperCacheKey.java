package com.ffusion.efs.adapters.entitlements;

class CLHelperCacheKey
{
  String jdField_new;
  int jdField_if;
  char jdField_int;
  boolean jdField_for;
  String a;
  boolean jdField_do;
  
  CLHelperCacheKey(String paramString1, int paramInt, char paramChar, boolean paramBoolean1, String paramString2, boolean paramBoolean2)
  {
    this.jdField_new = paramString1;
    this.jdField_if = paramInt;
    this.jdField_int = paramChar;
    this.jdField_for = paramBoolean1;
    this.a = paramString2;
    this.jdField_do = paramBoolean2;
  }
  
  public boolean equals(Object paramObject)
  {
    CLHelperCacheKey localCLHelperCacheKey = (CLHelperCacheKey)paramObject;
    if (((this.jdField_new != null) && (localCLHelperCacheKey.jdField_new == null)) || ((this.jdField_new == null) && (localCLHelperCacheKey.jdField_new != null))) {
      return false;
    }
    if ((this.jdField_new != null) && (localCLHelperCacheKey.jdField_new != null) && (!this.jdField_new.equals(localCLHelperCacheKey.jdField_new))) {
      return false;
    }
    if (this.jdField_if != localCLHelperCacheKey.jdField_if) {
      return false;
    }
    if (this.jdField_int != localCLHelperCacheKey.jdField_int) {
      return false;
    }
    if ((!this.jdField_do) && (!localCLHelperCacheKey.jdField_do))
    {
      if (!this.a.equals(localCLHelperCacheKey.a)) {
        return false;
      }
    }
    else if ((!this.jdField_do) || (!localCLHelperCacheKey.jdField_do)) {
      return false;
    }
    return this.jdField_for == localCLHelperCacheKey.jdField_for;
  }
  
  public int hashCode()
  {
    if (this.jdField_new == null) {
      return this.jdField_if + this.jdField_int + (this.jdField_for ? 3975 : 8123);
    }
    return this.jdField_new.hashCode() + this.jdField_if + this.jdField_int + (this.jdField_for ? 3975 : 8123);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.CLHelperCacheKey
 * JD-Core Version:    0.7.0.1
 */