package com.ffusion.beans.affiliatebank;

import com.ffusion.util.FilteredList;

public class ScheduleActivities
  extends FilteredList
{
  public ScheduleActivity add()
  {
    ScheduleActivity localScheduleActivity = new ScheduleActivity();
    add(localScheduleActivity);
    return localScheduleActivity;
  }
  
  public ScheduleActivity createNoAdd()
  {
    ScheduleActivity localScheduleActivity = new ScheduleActivity();
    return localScheduleActivity;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.ScheduleActivities
 * JD-Core Version:    0.7.0.1
 */