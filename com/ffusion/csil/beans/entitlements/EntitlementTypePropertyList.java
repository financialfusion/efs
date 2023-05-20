package com.ffusion.csil.beans.entitlements;

public class EntitlementTypePropertyList
  extends TypePropertyList
{
  public EntitlementTypePropertyList(String paramString)
  {
    super(paramString);
  }
  
  public EntIterator iterator()
  {
    return new EntIterator();
  }
  
  public class EntIterator
    extends TypePropertyList.PropertyIterator
  {
    EntIterator()
    {
      super();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList
 * JD-Core Version:    0.7.0.1
 */