package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.admin.AdminException;
import java.util.HashMap;

public abstract interface Admin
{
  public static final String SERVICE_INIT_XML = "admin.xml";
  
  public abstract void initialize()
    throws AdminException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws AdminException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Admin
 * JD-Core Version:    0.7.0.1
 */