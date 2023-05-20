package com.ffusion.beans.systemadmin;

import java.util.HashMap;

public class DRSettings
{
  private HashMap jdField_if;
  private String a;
  private String jdField_do;
  
  public DRSettings(HashMap paramHashMap)
  {
    if (paramHashMap == null) {
      this.jdField_if = new HashMap();
    } else {
      this.jdField_if = paramHashMap;
    }
  }
  
  public String getDataClass()
  {
    return this.jdField_do;
  }
  
  public String getDataType()
  {
    return this.a;
  }
  
  public String getDRSetting()
  {
    String str = "";
    DRKey localDRKey = new DRKey(this.a, this.jdField_do);
    try
    {
      DRSetting localDRSetting = (DRSetting)this.jdField_if.get(localDRKey);
      if (localDRSetting != null) {
        str = localDRSetting.getRetentionDaysString();
      }
    }
    catch (Exception localException)
    {
      str = "";
    }
    return str;
  }
  
  public void setDataClass(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public void setDataType(String paramString)
  {
    this.a = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.systemadmin.DRSettings
 * JD-Core Version:    0.7.0.1
 */