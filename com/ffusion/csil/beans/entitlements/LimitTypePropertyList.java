package com.ffusion.csil.beans.entitlements;

public class LimitTypePropertyList
  extends TypePropertyList
{
  public LimitTypePropertyList(String paramString)
  {
    super(paramString);
  }
  
  public LimitIterator iterator()
  {
    return new LimitIterator();
  }
  
  public class LimitIterator
    extends TypePropertyList.PropertyIterator
  {
    LimitIterator()
    {
      super();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.LimitTypePropertyList
 * JD-Core Version:    0.7.0.1
 */