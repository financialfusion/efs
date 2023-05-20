package com.ffusion.csil.beans.entitlements;

import java.io.Serializable;

public class MultiEntitlement
  implements Serializable
{
  private String[] jdField_do;
  private String[] jdField_if;
  private String[] a;
  
  public void setOperations(String[] paramArrayOfString)
  {
    this.jdField_do = paramArrayOfString;
  }
  
  public String[] getOperations()
  {
    return this.jdField_do;
  }
  
  public void setObjects(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    this.jdField_if = paramArrayOfString1;
    this.a = paramArrayOfString2;
  }
  
  public String[] getObjectTypes()
  {
    return this.jdField_if;
  }
  
  public String[] getObjectIds()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.MultiEntitlement
 * JD-Core Version:    0.7.0.1
 */