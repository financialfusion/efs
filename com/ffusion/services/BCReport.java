package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.efs.adapters.profile.BCReportException;
import com.ffusion.reporting.IReportResult;
import java.util.HashMap;

public abstract interface BCReport
{
  public abstract void initialize();
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BCReport
 * JD-Core Version:    0.7.0.1
 */