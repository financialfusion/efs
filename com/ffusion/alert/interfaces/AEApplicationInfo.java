package com.ffusion.alert.interfaces;

import java.io.Serializable;

public class AEApplicationInfo
  implements Serializable
{
  private String a;
  private String jdField_if;
  
  public AEApplicationInfo(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.jdField_if = paramString2;
  }
  
  public String getName()
  {
    return this.a;
  }
  
  public String getPassword()
  {
    return this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.AEApplicationInfo
 * JD-Core Version:    0.7.0.1
 */