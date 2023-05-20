package com.ffusion.beans.affiliatebank;

import com.ffusion.util.FilteredList;

public class ScheduleTypes
  extends FilteredList
{
  public ScheduleType add()
  {
    ScheduleType localScheduleType = new ScheduleType();
    add(localScheduleType);
    return localScheduleType;
  }
  
  public ScheduleType createNoAdd()
  {
    ScheduleType localScheduleType = new ScheduleType();
    return localScheduleType;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.ScheduleTypes
 * JD-Core Version:    0.7.0.1
 */