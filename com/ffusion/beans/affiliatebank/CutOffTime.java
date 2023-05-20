package com.ffusion.beans.affiliatebank;

import com.ffusion.beans.ExtendABean;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.util.ResourceUtil;
import java.io.Serializable;
import java.text.Collator;

public class CutOffTime
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.affiliatebank.resources";
  public static final String DAYOFWEEK = "DAYOFWEEK";
  public static final String TIMEOFDAY = "TIMEOFDAY";
  public static final String EXTENSION = "EXTENSION";
  public static final String INSTRUCTIONTYPE = "INSTRUCTIONTYPE";
  public static final String MEMO = "MEMO";
  public static final String NEXTPROCESSTIME = "NEXTPROCESSTIME";
  public static final String ACTIVE = "ACTIVE";
  public static final String ACTION = "ACTION";
  public static final String KEY_DAY_PREFIX = "Day";
  public static final String KEY_CUTOFF_DISPLAY_TEXT = "CutOffDisplayText";
  private String Gv;
  private String Gs;
  private String Gn;
  private String Gq;
  private String Gz;
  private String Gm;
  private int Gw = 9;
  private int Gx;
  private String Gu;
  private String Gr;
  private String Gt;
  private String GB;
  private String Gy;
  private boolean Gp;
  private String GA;
  private String GC;
  private String Go;
  public static final int DAY_INVALID = 0;
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return 0;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    CutOffTime localCutOffTime = (CutOffTime)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equals("DAYOFWEEK")) {
      i = this.Gx - localCutOffTime.getDayOfWeekValue();
    } else if ((paramString.equals("TIMEOFDAY")) && (this.Gu != null) && (localCutOffTime.getTimeOfDay() != null)) {
      i = localCollator.compare(getTimeOfDay(), localCutOffTime.getTimeOfDay());
    } else if ((paramString.equals("EXTENSION")) && (this.Gr != null) && (localCutOffTime.getExtension() != null)) {
      i = localCollator.compare(getExtension(), localCutOffTime.getExtension());
    } else if ((paramString.equals("NEXTPROCESSTIME")) && (this.Gt != null) && (localCutOffTime.getNextProcessTime() != null)) {
      i = localCollator.compare(getNextProcessTime(), localCutOffTime.getNextProcessTime());
    } else if ((paramString.equals("STATUS")) && (this.Gy != null) && (localCutOffTime.getStatus() != null)) {
      i = localCollator.compare(getStatus(), localCutOffTime.getStatus());
    } else if (paramString.equals("ACTIVE")) {
      i = compareBoolean(getActive(), localCutOffTime.getActive());
    } else if ((paramString.equals("MEMO")) && (this.GB != null) && (localCutOffTime.getMemo() != null)) {
      i = localCollator.compare(getMemo().toLowerCase(), localCutOffTime.getMemo().toLowerCase());
    } else if ((paramString.equals("INSTRUCTIONTYPE")) && (this.Gm != null) && (localCutOffTime.getInstructionType() != null)) {
      i = localCollator.compare(getInstructionType().toLowerCase(), localCutOffTime.getInstructionType().toLowerCase());
    } else if ((paramString.equals("ACTION")) && (this.GA != null) && (localCutOffTime.getAction() != null)) {
      i = localCollator.compare(getAction(), localCutOffTime.getAction());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  private int jdMethod_null(String paramString1, String paramString2)
  {
    int i = 1;
    if (((paramString1 == null) || (paramString1.trim().length() == 0)) && ((paramString2 == null) || (paramString2.trim().length() == 0)))
    {
      i = 0;
    }
    else if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      i = -1;
    }
    else if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      i = 1;
    }
    else
    {
      int j = paramString2.trim().length();
      int k = paramString1.trim().length();
      int m;
      int n;
      int i1;
      int i2;
      if ((k > 6) && (j > 6))
      {
        m = Integer.parseInt(paramString1.substring(0, 4));
        n = Integer.parseInt(paramString2.substring(0, 4));
        i = m - n;
        if (i == 0)
        {
          paramString1 = paramString1.substring(5);
          paramString2 = paramString2.substring(5);
          i1 = Integer.parseInt(paramString1.substring(0, 2));
          i2 = Integer.parseInt(paramString2.substring(0, 2));
          i = i1 - i2;
          paramString1 = paramString1.substring(3);
          paramString2 = paramString2.substring(3);
        }
      }
      if ((i == 0) || (k <= 5))
      {
        m = Integer.parseInt(paramString1.substring(0, 2));
        n = Integer.parseInt(paramString2.substring(0, 2));
        i = m - n;
        if (i == 0)
        {
          i1 = Integer.parseInt(paramString1.substring(3));
          i2 = Integer.parseInt(paramString2.substring(3));
          i = i1 - i2;
        }
      }
    }
    return i;
  }
  
  public void setDayOfWeek(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
      setDayOfWeekValue(i);
    }
    catch (Exception localException) {}
  }
  
  public void setDayOfWeekValue(int paramInt)
  {
    this.Gx = paramInt;
  }
  
  public String getDayOfWeek()
  {
    return Integer.toString(this.Gx);
  }
  
  public int getDayOfWeekValue()
  {
    return this.Gx;
  }
  
  public String getDayOfWeekString()
  {
    return ResourceUtil.getString("Day" + this.Gx, "com.ffusion.beans.affiliatebank.resources", this.locale);
  }
  
  public void setTimeOfDay(String paramString)
  {
    this.Gu = paramString;
  }
  
  public String getTimeOfDay()
  {
    return this.Gu;
  }
  
  public void setExtension(String paramString)
  {
    this.Gr = paramString;
  }
  
  public String getExtension()
  {
    return this.Gr;
  }
  
  public void setActive(String paramString)
  {
    try
    {
      boolean bool = Boolean.valueOf(paramString).booleanValue();
      setActiveValue(bool);
    }
    catch (Exception localException) {}
  }
  
  public void setActiveValue(boolean paramBoolean)
  {
    this.Gp = paramBoolean;
    if (this.Gp) {
      this.Gy = "ACTIVE";
    } else {
      this.Gy = "INACTIVE";
    }
  }
  
  public String getActive()
  {
    boolean bool = getActiveValue();
    if (bool) {
      return "true";
    }
    return "false";
  }
  
  public boolean getActiveValue()
  {
    if (this.Gy.equalsIgnoreCase("ACTIVE")) {
      this.Gp = true;
    } else {
      this.Gp = false;
    }
    return this.Gp;
  }
  
  public String getStatus()
  {
    return this.Gy;
  }
  
  public void setStatus(String paramString)
  {
    this.Gy = paramString;
  }
  
  public String getCutOffId()
  {
    return this.Gn;
  }
  
  public void setCutOffId(String paramString)
  {
    this.Gn = paramString;
  }
  
  public String getCompCutOffId()
  {
    return this.Gv;
  }
  
  public void setCompCutOffId(String paramString)
  {
    this.Gv = paramString;
  }
  
  public String getCompId()
  {
    return this.Gs;
  }
  
  public void setCompId(String paramString)
  {
    this.Gs = paramString;
  }
  
  public String getFIId()
  {
    return this.Gq;
  }
  
  public void setFIId(String paramString)
  {
    this.Gq = paramString;
  }
  
  public String getCategory()
  {
    return this.Gz;
  }
  
  public void setCategory(String paramString)
  {
    this.Gz = paramString;
  }
  
  public String getInstructionType()
  {
    return this.Gm;
  }
  
  public void setInstructionType(String paramString)
  {
    this.Gm = paramString;
  }
  
  public String getFrequency()
  {
    return String.valueOf(this.Gw);
  }
  
  public int getFrequencyValue()
  {
    return this.Gw;
  }
  
  public void setFrequency(int paramInt)
  {
    this.Gw = paramInt;
  }
  
  public void setFrequency(String paramString)
  {
    try
    {
      this.Gw = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getNextProcessTime()
  {
    return this.Gt;
  }
  
  public void setNextProcessTime(String paramString)
  {
    this.Gt = paramString;
  }
  
  public String getMemo()
  {
    return this.GB;
  }
  
  public void setMemo(String paramString)
  {
    this.GB = paramString;
  }
  
  public String getAction()
  {
    return this.GA;
  }
  
  public void setAction(String paramString)
  {
    this.GA = paramString;
  }
  
  public String getLogId()
  {
    return this.GC;
  }
  
  public void setLogId(String paramString)
  {
    this.GC = paramString;
  }
  
  public String getSubmittedBy()
  {
    return this.Go;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.Go = paramString;
  }
  
  public void set(CutOffTime paramCutOffTime)
  {
    if (paramCutOffTime == null) {
      return;
    }
    this.Gn = paramCutOffTime.Gn;
    this.Gv = paramCutOffTime.Gv;
    this.Gs = paramCutOffTime.Gs;
    this.Gq = paramCutOffTime.Gq;
    this.Gm = paramCutOffTime.Gm;
    this.Gw = paramCutOffTime.Gw;
    this.Gx = paramCutOffTime.Gx;
    this.Gu = paramCutOffTime.Gu;
    this.Gr = paramCutOffTime.Gr;
    this.Gt = paramCutOffTime.Gt;
    this.GB = paramCutOffTime.GB;
    this.Gy = paramCutOffTime.Gy;
    this.GA = paramCutOffTime.GA;
    this.GC = paramCutOffTime.GC;
    this.Go = paramCutOffTime.Go;
  }
  
  public CutOffInfo getCutOffInfo()
  {
    CutOffInfo localCutOffInfo = new CutOffInfo();
    localCutOffInfo.setCutOffId(this.Gn);
    localCutOffInfo.setFIId(this.Gq);
    localCutOffInfo.setInstructionType(this.Gm);
    localCutOffInfo.setFrequency(this.Gw);
    localCutOffInfo.setDay(this.Gx);
    localCutOffInfo.setProcessTime(this.Gu);
    localCutOffInfo.setExtension(this.Gr);
    localCutOffInfo.setNextProcessTime(this.Gt);
    localCutOffInfo.setMemo(this.GB);
    localCutOffInfo.setStatus(this.Gy);
    localCutOffInfo.setAction(this.GA);
    localCutOffInfo.setLogId(this.GC);
    localCutOffInfo.setSubmittedBy(this.Go);
    return localCutOffInfo;
  }
  
  public void setCutOffInfo(CutOffInfo paramCutOffInfo)
  {
    if (paramCutOffInfo == null) {
      return;
    }
    this.Gn = paramCutOffInfo.getCutOffId();
    this.Gq = paramCutOffInfo.getFIId();
    this.Gm = paramCutOffInfo.getInstructionType();
    this.Gw = paramCutOffInfo.getFrequency();
    this.Gx = paramCutOffInfo.getDay();
    this.Gu = paramCutOffInfo.getProcessTime();
    this.Gr = paramCutOffInfo.getExtension();
    this.Gt = paramCutOffInfo.getNextProcessTime();
    this.GB = paramCutOffInfo.getMemo();
    this.Gy = paramCutOffInfo.getStatus();
    this.GA = paramCutOffInfo.getAction();
    this.GC = paramCutOffInfo.getLogId();
    this.Go = paramCutOffInfo.getSubmittedBy();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.CutOffTime
 * JD-Core Version:    0.7.0.1
 */