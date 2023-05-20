package com.ffusion.ffs.bpw.interfaces;

import java.util.HashMap;

public class ScheduleCalloutInfo
  extends HashMap
{
  private String jdField_for;
  private String a;
  private String jdField_do;
  private String jdField_new;
  private String jdField_int;
  private String jdField_if;
  public static final String KEY_NEWITEMCOUNT = "NewItemCount";
  public static final String KEY_RECOVERYITEMCOUNT = "RecoveryItemCount";
  public static final String KEY_FILEBASEDRECOVERYFLAG = "FileBasedRecoveryFlag";
  public static final String KEY_ERRORTHROWABLE = "ErrorThrowable";
  
  public String getInstructionType()
  {
    return this.jdField_for;
  }
  
  public void setInstructionType(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getFiId()
  {
    return this.a;
  }
  
  public void setFiId(String paramString)
  {
    this.a = paramString;
  }
  
  public String getEJBContextFactory()
  {
    return this.jdField_do;
  }
  
  public void setEJBContextFactory(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getEJBUrl()
  {
    return this.jdField_new;
  }
  
  public void setEJBUrl(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getEJBUsername()
  {
    return this.jdField_int;
  }
  
  public void setEJBUsername(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getEJBPassword()
  {
    return this.jdField_if;
  }
  
  public void setEJBPassword(String paramString)
  {
    this.jdField_if = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleCalloutInfo
 * JD-Core Version:    0.7.0.1
 */