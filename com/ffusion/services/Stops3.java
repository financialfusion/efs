package com.ffusion.services;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.stoppayments.StopChecks;
import java.util.Calendar;

public abstract interface Stops3
  extends Stops2
{
  public abstract int getStopPayments(StopChecks paramStopChecks, Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Stops3
 * JD-Core Version:    0.7.0.1
 */