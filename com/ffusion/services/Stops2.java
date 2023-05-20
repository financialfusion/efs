package com.ffusion.services;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.stoppayments.StopChecks;

public abstract interface Stops2
  extends Stops
{
  public abstract int getStopPayments(StopChecks paramStopChecks, Accounts paramAccounts);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Stops2
 * JD-Core Version:    0.7.0.1
 */