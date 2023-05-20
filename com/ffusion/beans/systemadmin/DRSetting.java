package com.ffusion.beans.systemadmin;

import com.ffusion.beans.ExtendABean;

public class DRSetting
  extends ExtendABean
{
  public static final String BEAN_NAME = DRSetting.class.getName();
  public static final String DRSETTING = "DRSETTING";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.systemadmin.resources";
  public static final String KEY_DRSETTING_DISPLAY_PREFIX = "displayName_";
  private DRKey aXp = null;
  private int aXo = 7;
  
  public DRKey getDataKey()
  {
    return this.aXp;
  }
  
  public int getRetentionDays()
  {
    return this.aXo;
  }
  
  public String getRetentionDaysString()
  {
    String str = null;
    try
    {
      str = Integer.toString(this.aXo);
    }
    catch (Exception localException)
    {
      str = Integer.toString(7);
    }
    return str;
  }
  
  public void setDataKey(DRKey paramDRKey)
  {
    this.aXp = paramDRKey;
  }
  
  public void setRetentionDays(int paramInt)
  {
    this.aXo = paramInt;
  }
  
  public void setRetentionDays(String paramString)
  {
    try
    {
      this.aXo = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.systemadmin.DRSetting
 * JD-Core Version:    0.7.0.1
 */