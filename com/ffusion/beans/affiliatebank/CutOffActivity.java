package com.ffusion.beans.affiliatebank;

import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfo;
import com.ffusion.util.beans.ExtendABean;

public class CutOffActivity
  extends ExtendABean
{
  private String jdField_case = null;
  private String jdField_try = null;
  private String jdField_new = null;
  private String jdField_for = null;
  private String jdField_do = null;
  private String jdField_if = null;
  private String jdField_int = null;
  private String a = null;
  
  public String getProcessId()
  {
    return this.jdField_case;
  }
  
  public void setProcessId(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getCutOffId()
  {
    return this.jdField_try;
  }
  
  public void setCutOffId(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getStartTime()
  {
    return this.jdField_new;
  }
  
  public void setStartTime(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getEndTime()
  {
    return this.jdField_for;
  }
  
  public void setEndTime(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getCutOffDay()
  {
    return this.jdField_do;
  }
  
  public void setCutOffDay(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getCutOffProcessTime()
  {
    return this.jdField_if;
  }
  
  public void setCutOffProcessTime(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getCutOffExtension()
  {
    return this.jdField_int;
  }
  
  public void setCutOffExtension(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getStatus()
  {
    return this.a;
  }
  
  public void setStatus(String paramString)
  {
    this.a = paramString;
  }
  
  public void set(CutOffActivity paramCutOffActivity)
  {
    if (paramCutOffActivity == null) {
      return;
    }
    this.jdField_case = paramCutOffActivity.jdField_case;
    this.jdField_try = paramCutOffActivity.jdField_try;
    this.jdField_new = paramCutOffActivity.jdField_new;
    this.jdField_for = paramCutOffActivity.jdField_for;
    this.jdField_do = paramCutOffActivity.jdField_do;
    this.jdField_if = paramCutOffActivity.jdField_if;
    this.jdField_int = paramCutOffActivity.jdField_int;
    this.a = paramCutOffActivity.a;
  }
  
  public CutOffActivityInfo getCutOffActivityInfo()
  {
    CutOffActivityInfo localCutOffActivityInfo = new CutOffActivityInfo();
    localCutOffActivityInfo.setProcessId(this.jdField_case);
    localCutOffActivityInfo.setCutOffId(this.jdField_try);
    localCutOffActivityInfo.setStartTime(this.jdField_new);
    localCutOffActivityInfo.setEndTime(this.jdField_for);
    localCutOffActivityInfo.setCutOffDay(this.jdField_do);
    localCutOffActivityInfo.setCutOffProcessTime(this.jdField_if);
    localCutOffActivityInfo.setCutOffExtension(this.jdField_int);
    localCutOffActivityInfo.setStatus(this.a);
    return localCutOffActivityInfo;
  }
  
  public void setCutOffActivityInfo(CutOffActivityInfo paramCutOffActivityInfo)
  {
    if (paramCutOffActivityInfo == null) {
      return;
    }
    this.jdField_case = paramCutOffActivityInfo.getProcessId();
    this.jdField_try = paramCutOffActivityInfo.getCutOffId();
    this.jdField_new = paramCutOffActivityInfo.getStartTime();
    this.jdField_for = paramCutOffActivityInfo.getEndTime();
    this.jdField_do = paramCutOffActivityInfo.getCutOffDay();
    this.jdField_if = paramCutOffActivityInfo.getCutOffProcessTime();
    this.jdField_int = paramCutOffActivityInfo.getCutOffExtension();
    this.a = paramCutOffActivityInfo.getStatus();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.CutOffActivity
 * JD-Core Version:    0.7.0.1
 */