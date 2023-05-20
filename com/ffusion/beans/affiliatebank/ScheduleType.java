package com.ffusion.beans.affiliatebank;

import com.ffusion.beans.ExtendABean;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.DateTime;
import java.io.Serializable;
import java.text.Collator;

public class ScheduleType
  extends ExtendABean
  implements Comparable, Serializable
{
  private static final String F9 = "com.ffusion.beans.affiliatebank.resources";
  public static final String USECUTOFFS = "USECUTOFFS";
  public static final String CREATEEMPTYFILE = "CREATEEMPTYFILE";
  public static final String PROCESSONHOLIDAYS = "PROCESSONHOLIDAYS";
  public static final String CATEGORY = "CATEGORY";
  public static final String MEMO = "MEMO";
  public static final String INSTRUCTIONTYPE = "INSTRUCTIONTYPE";
  public static final String ACTIVE = "ACTIVE";
  private boolean Ge = false;
  private boolean F2 = false;
  private boolean Gc = false;
  private String Gd;
  private String Gh;
  private String F5;
  private String F1;
  private String Gj;
  private String Gb;
  private int Gi;
  private boolean F3 = false;
  private boolean Gf = false;
  private boolean F8 = false;
  private boolean F4 = false;
  private int Gg;
  private String Ga;
  private String F6;
  private String F7;
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return 0;
  }
  
  public String getFIId()
  {
    return this.F5;
  }
  
  public void setFIId(String paramString)
  {
    this.F5 = paramString;
  }
  
  public String getInstructionType()
  {
    return this.F1;
  }
  
  public void setInstructionType(String paramString)
  {
    this.F1 = paramString;
  }
  
  public String getHandlerName()
  {
    return this.Gj;
  }
  
  public void setHandlerName(String paramString)
  {
    this.Gj = paramString;
  }
  
  public String getStartTime()
  {
    return this.Gb;
  }
  
  public void setStartTime(String paramString)
  {
    this.Gb = paramString;
  }
  
  public String getInterval()
  {
    return String.valueOf(this.Gi);
  }
  
  public void setInterval(String paramString)
  {
    this.Gi = Integer.parseInt(paramString);
  }
  
  public String getNextRunTime()
  {
    return this.Ga;
  }
  
  public void setNextRunTime(String paramString)
  {
    this.Ga = paramString;
  }
  
  public String getFinalRunTime()
  {
    return this.F6;
  }
  
  public void setFinalRunTime(String paramString)
  {
    this.F6 = paramString;
  }
  
  public String getNextRunTimeZone()
  {
    return this.F7;
  }
  
  public void setNextRunTimeZone(String paramString)
  {
    this.F7 = paramString;
  }
  
  public String getCanResubmit()
  {
    if (this.F3) {
      return "true";
    }
    return "false";
  }
  
  public boolean getCanResubmitValue()
  {
    return this.F3;
  }
  
  public void setCanResubmit(String paramString)
  {
    try
    {
      this.F3 = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setCanResubmit(boolean paramBoolean)
  {
    this.F3 = paramBoolean;
  }
  
  public String getAutoRecover()
  {
    if (this.Gf) {
      return "true";
    }
    return "false";
  }
  
  public boolean getAutoRecoverValue()
  {
    return this.Gf;
  }
  
  public void setAutoRecover(String paramString)
  {
    try
    {
      this.Gf = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setAutoRecover(boolean paramBoolean)
  {
    this.Gf = paramBoolean;
  }
  
  public String getFileBasedRecovery()
  {
    if (this.F8) {
      return "true";
    }
    return "false";
  }
  
  public void setFileBasedRecovery(String paramString)
  {
    try
    {
      this.F8 = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getActive()
  {
    if (this.F4) {
      return "true";
    }
    return "false";
  }
  
  public void setActive(String paramString)
  {
    try
    {
      this.F4 = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getRouteId()
  {
    return String.valueOf(this.Gg);
  }
  
  public int getRouteIdValue()
  {
    return this.Gg;
  }
  
  public void setRouteId(String paramString)
  {
    this.Gg = Integer.parseInt(paramString);
  }
  
  public void setRouteId(int paramInt)
  {
    this.Gg = paramInt;
  }
  
  public void setCreateEmptyFile(String paramString)
  {
    try
    {
      this.F2 = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setCreateEmptyFileValue(boolean paramBoolean)
  {
    this.F2 = paramBoolean;
  }
  
  public String getCreateEmptyFile()
  {
    if (this.F2) {
      return "true";
    }
    return "false";
  }
  
  public boolean getCreateEmptyFileValue()
  {
    return this.F2;
  }
  
  public void setProcessOnHolidays(String paramString)
  {
    try
    {
      this.Gc = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setProcessOnHolidaysValue(boolean paramBoolean)
  {
    this.Gc = paramBoolean;
  }
  
  public String getProcessOnHolidays()
  {
    if (this.Gc) {
      return "true";
    }
    return "false";
  }
  
  public boolean getProcessOnHolidaysValue()
  {
    return this.Gc;
  }
  
  public void setUseCutOffs(String paramString)
  {
    try
    {
      this.Ge = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setUseCutOffs(boolean paramBoolean)
  {
    this.Ge = paramBoolean;
  }
  
  public String getUseCutOffs()
  {
    if (this.Ge) {
      return "true";
    }
    return "false";
  }
  
  public boolean getUseCutOffsValue()
  {
    return this.Ge;
  }
  
  public String getCategoryDisplayName()
  {
    String str1 = "SchedCategory" + this.Gd;
    String str2 = ResourceUtil.getString(str1, "com.ffusion.beans.affiliatebank.resources", this.locale);
    if (str2 != null) {
      return str2;
    }
    return this.Gd;
  }
  
  public String getCategory()
  {
    return this.Gd;
  }
  
  public void setCategory(String paramString)
  {
    this.Gd = paramString;
  }
  
  public String getMemo()
  {
    return this.Gh;
  }
  
  public void setMemo(String paramString)
  {
    this.Gh = paramString;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ScheduleType localScheduleType = (ScheduleType)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("CATEGORY")) && (this.Gd != null) && (localScheduleType.getCategory() != null)) {
      i = localCollator.compare(getCategory(), localScheduleType.getCategory());
    } else if (paramString.equals("ACTIVE")) {
      i = compareStrings(getActive(), localScheduleType.getActive());
    } else if ((paramString.equals("MEMO")) && (this.Gh != null) && (localScheduleType.getMemo() != null)) {
      i = localCollator.compare(getMemo().toLowerCase(), localScheduleType.getMemo().toLowerCase());
    } else if ((paramString.equals("USECUTOFFS")) && (getUseCutOffs() != null) && (localScheduleType.getUseCutOffs() != null)) {
      i = localCollator.compare(getUseCutOffs(), localScheduleType.getUseCutOffs());
    } else if ((paramString.equals("INSTRUCTIONTYPE")) && (this.F1 != null) && (localScheduleType.getInstructionType() != null)) {
      i = localCollator.compare(getInstructionType().toLowerCase(), localScheduleType.getInstructionType().toLowerCase());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void set(ScheduleType paramScheduleType)
  {
    if (paramScheduleType == null) {
      return;
    }
    this.Ge = paramScheduleType.Ge;
    this.F2 = paramScheduleType.F2;
    this.Gc = paramScheduleType.Gc;
    this.Gd = paramScheduleType.Gd;
    this.Gh = paramScheduleType.Gh;
    this.F5 = paramScheduleType.F5;
    this.F1 = paramScheduleType.F1;
    this.Gj = paramScheduleType.Gj;
    this.Gb = paramScheduleType.Gb;
    this.Gi = paramScheduleType.Gi;
    this.F3 = paramScheduleType.F3;
    this.Gf = paramScheduleType.Gf;
    this.F8 = paramScheduleType.F8;
    this.F4 = paramScheduleType.F4;
    this.Gg = paramScheduleType.Gg;
  }
  
  public InstructionType getInstructionTypeInfo()
  {
    InstructionType localInstructionType = new InstructionType();
    localInstructionType.FIId = this.F5;
    localInstructionType.InstructionType = this.F1;
    localInstructionType.HandlerClassName = this.Gj;
    localInstructionType.SchedulerInterval = this.Gi;
    if (this.F3) {
      localInstructionType.ResubmitEventSupported = 1;
    } else {
      localInstructionType.ResubmitEventSupported = 0;
    }
    if (this.Gf) {
      localInstructionType.ChkTmAutoRecover = 1;
    } else {
      localInstructionType.ChkTmAutoRecover = 0;
    }
    if (this.F8) {
      localInstructionType.FileBasedRecovery = 1;
    } else {
      localInstructionType.FileBasedRecovery = 0;
    }
    if (this.F4) {
      localInstructionType.ActiveFlag = 1;
    } else {
      localInstructionType.ActiveFlag = 0;
    }
    localInstructionType.RouteID = this.Gg;
    localInstructionType.useCutOffs = this.Ge;
    localInstructionType.createEmptyFile = this.F2;
    localInstructionType.processOnHolidays = this.Gc;
    localInstructionType.category = this.Gd;
    localInstructionType.memo = this.Gh;
    DateTime localDateTime = new DateTime();
    localDateTime.setFormat("HH:mm");
    localDateTime.setDate(this.Gb);
    localDateTime.setFormat("hh:mm a");
    localInstructionType.SchedulerStartTime = localDateTime.toString();
    return localInstructionType;
  }
  
  public void setInstructionTypeInfo(InstructionType paramInstructionType)
  {
    if (paramInstructionType == null) {
      return;
    }
    this.F5 = paramInstructionType.FIId;
    this.F1 = paramInstructionType.InstructionType;
    this.Gj = paramInstructionType.HandlerClassName;
    this.Gi = paramInstructionType.SchedulerInterval;
    if (paramInstructionType.ResubmitEventSupported == 1) {
      this.F3 = true;
    } else {
      this.F3 = false;
    }
    if (paramInstructionType.ChkTmAutoRecover == 1) {
      this.Gf = true;
    } else {
      this.Gf = false;
    }
    if (paramInstructionType.FileBasedRecovery == 1) {
      this.F8 = true;
    } else {
      this.F8 = false;
    }
    if (paramInstructionType.ActiveFlag == 1) {
      this.F4 = true;
    } else {
      this.F4 = false;
    }
    this.Gg = paramInstructionType.RouteID;
    this.Ge = paramInstructionType.useCutOffs;
    this.F2 = paramInstructionType.createEmptyFile;
    this.Gc = paramInstructionType.processOnHolidays;
    this.Gd = paramInstructionType.category;
    this.Gh = paramInstructionType.memo;
    DateTime localDateTime = new DateTime();
    localDateTime.setFormat("hh:mm a");
    localDateTime.setDate(paramInstructionType.SchedulerStartTime);
    localDateTime.setFormat("HH:mm");
    this.Gb = localDateTime.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.ScheduleType
 * JD-Core Version:    0.7.0.1
 */