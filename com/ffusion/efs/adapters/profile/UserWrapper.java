package com.ffusion.efs.adapters.profile;

public class UserWrapper
{
  private int jdField_do;
  private String jdField_if;
  private String jdField_for;
  private String a;
  
  public void setDirectoryID(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public int getDirectoryID()
  {
    return this.jdField_do;
  }
  
  public void setName(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getName()
  {
    return this.jdField_if;
  }
  
  public void setEmail(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getEmail()
  {
    return this.jdField_for;
  }
  
  public void setPreferredLanguage(String paramString)
  {
    this.a = paramString;
  }
  
  public String getPreferredLanguage()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.UserWrapper
 * JD-Core Version:    0.7.0.1
 */