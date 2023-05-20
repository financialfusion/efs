package com.ffusion.beans.affiliatebank;

import com.ffusion.beans.ExtendABean;
import java.io.Serializable;

public class CutOffDefinition
  extends ExtendABean
  implements Comparable, Serializable
{
  private CutOffTimes a5E;
  private String a5H;
  private ScheduleTypes a5G;
  private String a5F;
  private boolean a5I;
  private boolean a5D;
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return 0;
  }
  
  public void setCreateEmptyFile(String paramString)
  {
    try
    {
      this.a5I = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setCreateEmptyFileValue(boolean paramBoolean)
  {
    this.a5I = paramBoolean;
  }
  
  public String getCreateEmptyFile()
  {
    if (this.a5I) {
      return "true";
    }
    return "false";
  }
  
  public boolean getCreateEmptyFileValue()
  {
    return this.a5I;
  }
  
  public void setProcessOnHolidays(String paramString)
  {
    try
    {
      this.a5D = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setProcessOnHolidaysValue(boolean paramBoolean)
  {
    this.a5D = paramBoolean;
  }
  
  public String getProcessOnHolidays()
  {
    if (this.a5D) {
      return "true";
    }
    return "false";
  }
  
  public boolean getProcessOnHolidaysValue()
  {
    return this.a5D;
  }
  
  public void setCutOffs(CutOffTimes paramCutOffTimes)
  {
    this.a5E = paramCutOffTimes;
  }
  
  public CutOffTimes getCutOffs()
  {
    return this.a5E;
  }
  
  public String getFIId()
  {
    return this.a5H;
  }
  
  public void setFIId(String paramString)
  {
    this.a5H = paramString;
  }
  
  public String getGroup()
  {
    return this.a5F;
  }
  
  public void setGroup(String paramString)
  {
    this.a5F = paramString;
  }
  
  public ScheduleTypes getScheduleTypes()
  {
    return this.a5G;
  }
  
  public void setScheduleTypes(ScheduleTypes paramScheduleTypes)
  {
    this.a5G = paramScheduleTypes;
  }
  
  public void set(CutOffDefinition paramCutOffDefinition)
  {
    if (paramCutOffDefinition == null) {
      return;
    }
    this.a5E = paramCutOffDefinition.a5E;
    this.a5H = paramCutOffDefinition.a5H;
    this.a5G = paramCutOffDefinition.a5G;
    this.a5F = paramCutOffDefinition.a5F;
    this.a5D = paramCutOffDefinition.a5D;
    this.a5I = paramCutOffDefinition.a5I;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.CutOffDefinition
 * JD-Core Version:    0.7.0.1
 */