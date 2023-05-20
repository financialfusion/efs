package com.ffusion.csil.beans.entitlements;

import java.io.Serializable;

public class Entitlement
  implements Serializable, Cloneable
{
  protected String operation_name;
  protected String objectType;
  protected String objectId;
  
  public Entitlement(String paramString1, String paramString2, String paramString3)
  {
    this.operation_name = paramString1;
    this.objectType = paramString2;
    this.objectId = paramString3;
  }
  
  public Entitlement() {}
  
  public Entitlement(Entitlement paramEntitlement)
  {
    this.operation_name = paramEntitlement.operation_name;
    this.objectType = paramEntitlement.objectType;
    this.objectId = paramEntitlement.objectId;
  }
  
  public Object clone()
  {
    Entitlement localEntitlement = new Entitlement(this);
    return localEntitlement;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if (!(paramObject instanceof Entitlement)) {
      return false;
    }
    Entitlement localEntitlement = (Entitlement)paramObject;
    String str1 = localEntitlement.getOperationName();
    String str2 = localEntitlement.getObjectType();
    String str3 = localEntitlement.getObjectId();
    if (((str1 != null) && (this.operation_name == null)) || ((str1 == null) && (this.operation_name != null))) {
      return false;
    }
    if (((str2 != null) && (this.objectType == null)) || ((str2 == null) && (this.objectType != null))) {
      return false;
    }
    if (((str3 != null) && (this.objectId == null)) || ((str3 == null) && (this.objectId != null))) {
      return false;
    }
    if ((str1 != null) && (!str1.equals(this.operation_name))) {
      return false;
    }
    if ((str2 != null) && (!str2.equals(this.objectType))) {
      return false;
    }
    return (str3 == null) || (str3.equals(this.objectId));
  }
  
  public void setOperationName(String paramString)
  {
    this.operation_name = paramString;
  }
  
  public String getOperationName()
  {
    return this.operation_name;
  }
  
  public void setObjectType(String paramString)
  {
    this.objectType = paramString;
  }
  
  public String getObjectType()
  {
    return this.objectType;
  }
  
  public void setObjectId(String paramString)
  {
    this.objectId = paramString;
  }
  
  public String getObjectId()
  {
    return this.objectId;
  }
  
  public String toString()
  {
    return '(' + getOperationName() + ", " + getObjectType() + ", " + getObjectId() + ')';
  }
  
  public int hashCode()
  {
    if (this.operation_name == null)
    {
      if (this.objectId == null) {
        return 0;
      }
      return this.objectId.hashCode();
    }
    if (this.objectId == null) {
      return this.operation_name.hashCode();
    }
    return this.operation_name.hashCode() ^ this.objectId.hashCode();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.Entitlement
 * JD-Core Version:    0.7.0.1
 */