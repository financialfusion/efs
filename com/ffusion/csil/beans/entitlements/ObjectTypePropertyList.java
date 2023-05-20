package com.ffusion.csil.beans.entitlements;

public class ObjectTypePropertyList
  extends TypePropertyList
{
  public ObjectTypePropertyList(String paramString)
  {
    super(paramString);
  }
  
  public String getObjectType()
  {
    return getOperationName();
  }
  
  public ObjectIterator iterator()
  {
    return new ObjectIterator();
  }
  
  public class ObjectIterator
    extends TypePropertyList.PropertyIterator
  {
    ObjectIterator()
    {
      super();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.ObjectTypePropertyList
 * JD-Core Version:    0.7.0.1
 */