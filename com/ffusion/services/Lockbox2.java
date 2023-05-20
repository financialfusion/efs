package com.ffusion.services;

import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.services.lockbox.LBoxException;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface Lockbox2
  extends Lockbox
{
  public abstract LockboxSummaries getSummaries(LockboxAccounts paramLockboxAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Lockbox2
 * JD-Core Version:    0.7.0.1
 */