package com.ffusion.services;

import com.ffusion.efs.adapters.profile.BCReportException;
import java.util.HashMap;

public abstract interface BCReport2
  extends BCReport
{
  public abstract void appendReportTypeEntitlements(HashMap paramHashMap)
    throws BCReportException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BCReport2
 * JD-Core Version:    0.7.0.1
 */