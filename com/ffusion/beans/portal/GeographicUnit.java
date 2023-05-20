package com.ffusion.beans.portal;

import java.io.Serializable;

public class GeographicUnit
  implements Serializable
{
  private String jdField_if = "";
  private String a = "";
  
  public String getName()
  {
    return this.jdField_if;
  }
  
  public void setName(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getAbbr()
  {
    return this.a;
  }
  
  public void setAbbr(String paramString)
  {
    this.a = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.GeographicUnit
 * JD-Core Version:    0.7.0.1
 */