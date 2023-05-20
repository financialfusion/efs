package com.ffusion.beans.affiliatebank;

import com.ffusion.ffs.bpw.interfaces.ScheduleActivityInfo;
import com.ffusion.util.beans.ExtendABean;

public class ScheduleActivity
  extends ExtendABean
{
  private String a;
  private String jdField_new;
  private String jdField_try;
  private String jdField_int = null;
  private String jdField_for = null;
  private String jdField_do = null;
  private String jdField_if = null;
  
  public String getInstructionType()
  {
    return this.a;
  }
  
  public void setInstructionType(String paramString)
  {
    this.a = paramString;
  }
  
  public String getProcessId()
  {
    return this.jdField_new;
  }
  
  public void setProcessId(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getFileName()
  {
    return this.jdField_try;
  }
  
  public void setFileName(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getCutOffId()
  {
    return this.jdField_int;
  }
  
  public void setCutOffId(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getStartTime()
  {
    return this.jdField_for;
  }
  
  public void setStartTime(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getEndTime()
  {
    return this.jdField_do;
  }
  
  public void setEndTime(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getStatus()
  {
    return this.jdField_if;
  }
  
  public void setStatus(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public void set(ScheduleActivity paramScheduleActivity)
  {
    if (paramScheduleActivity == null) {
      return;
    }
    this.a = paramScheduleActivity.a;
    this.jdField_new = paramScheduleActivity.jdField_new;
    this.jdField_try = paramScheduleActivity.jdField_try;
    this.jdField_int = paramScheduleActivity.jdField_int;
    this.jdField_for = paramScheduleActivity.jdField_for;
    this.jdField_do = paramScheduleActivity.jdField_do;
    this.jdField_if = paramScheduleActivity.jdField_if;
  }
  
  public ScheduleActivityInfo getScheduleActivityInfo()
  {
    ScheduleActivityInfo localScheduleActivityInfo = new ScheduleActivityInfo();
    localScheduleActivityInfo.setInstructionType(this.a);
    localScheduleActivityInfo.setProcessId(this.jdField_new);
    localScheduleActivityInfo.setFileName(this.jdField_try);
    localScheduleActivityInfo.setCutOffId(this.jdField_int);
    localScheduleActivityInfo.setStartTime(this.jdField_for);
    localScheduleActivityInfo.setEndTime(this.jdField_do);
    localScheduleActivityInfo.setStatus(this.jdField_if);
    return localScheduleActivityInfo;
  }
  
  public void setScheduleActivityInfo(ScheduleActivityInfo paramScheduleActivityInfo)
  {
    if (paramScheduleActivityInfo == null) {
      return;
    }
    this.a = paramScheduleActivityInfo.getInstructionType();
    this.jdField_new = paramScheduleActivityInfo.getProcessId();
    this.jdField_try = paramScheduleActivityInfo.getFileName();
    this.jdField_int = paramScheduleActivityInfo.getCutOffId();
    this.jdField_for = paramScheduleActivityInfo.getStartTime();
    this.jdField_do = paramScheduleActivityInfo.getEndTime();
    this.jdField_if = paramScheduleActivityInfo.getStatus();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.ScheduleActivity
 * JD-Core Version:    0.7.0.1
 */