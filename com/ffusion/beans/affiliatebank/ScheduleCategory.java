package com.ffusion.beans.affiliatebank;

import com.ffusion.beans.ExtendABean;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
import com.ffusion.util.ResourceUtil;

public class ScheduleCategory
  extends ExtendABean
{
  private static final String a5u = "com.ffusion.beans.affiliatebank.resources";
  private CutOffTimes a5y = new CutOffTimes();
  private ScheduleTypes a5x = new ScheduleTypes();
  private String a5w;
  private String a5v;
  
  public CutOffTimes getCutOffTimes()
  {
    return this.a5y;
  }
  
  public void setCutOffTimes(CutOffTimes paramCutOffTimes)
  {
    this.a5y = paramCutOffTimes;
  }
  
  public ScheduleTypes getScheduleTypes()
  {
    return this.a5x;
  }
  
  public void setScheduleTypes(ScheduleTypes paramScheduleTypes)
  {
    this.a5x = paramScheduleTypes;
  }
  
  public String getFIId()
  {
    return this.a5w;
  }
  
  public void setFIId(String paramString)
  {
    this.a5w = paramString;
  }
  
  public String getCategoryDisplayName()
  {
    String str1 = "SchedCategory" + this.a5v;
    String str2 = ResourceUtil.getString(str1, "com.ffusion.beans.affiliatebank.resources", this.locale);
    if (str2 != null) {
      return str2;
    }
    return this.a5v;
  }
  
  public String getCategory()
  {
    return this.a5v;
  }
  
  public void setCategory(String paramString)
  {
    this.a5v = paramString;
  }
  
  public void setScheduleCategoryInfo(ScheduleCategoryInfo paramScheduleCategoryInfo)
  {
    if (paramScheduleCategoryInfo == null) {
      return;
    }
    this.a5w = paramScheduleCategoryInfo.getFIId();
    this.a5v = paramScheduleCategoryInfo.getCategory();
    CutOffInfo[] arrayOfCutOffInfo = paramScheduleCategoryInfo.getCutOffInfoList();
    if ((arrayOfCutOffInfo != null) && (arrayOfCutOffInfo.length > 0))
    {
      localObject = new CutOffTimes();
      for (int i = 0; i < arrayOfCutOffInfo.length; i++)
      {
        CutOffTime localCutOffTime = new CutOffTime();
        if (arrayOfCutOffInfo[i].getStatusCode() == 0)
        {
          localCutOffTime.setCutOffInfo(arrayOfCutOffInfo[i]);
          ((CutOffTimes)localObject).add(localCutOffTime);
        }
      }
      this.a5y = ((CutOffTimes)localObject);
    }
    Object localObject = paramScheduleCategoryInfo.getInstructionTypeList();
    if ((localObject != null) && (localObject.length > 0))
    {
      ScheduleTypes localScheduleTypes = new ScheduleTypes();
      for (int j = 0; j < localObject.length; j++)
      {
        ScheduleType localScheduleType = new ScheduleType();
        localScheduleType.setLocale(this.locale);
        if (localObject[j].getStatusCode() == 0)
        {
          localScheduleType.setInstructionTypeInfo(localObject[j]);
          localScheduleTypes.add(localScheduleType);
        }
      }
      this.a5x = localScheduleTypes;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.ScheduleCategory
 * JD-Core Version:    0.7.0.1
 */