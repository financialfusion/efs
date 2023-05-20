package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMFileDescriptions;
import java.util.HashMap;

public abstract interface IFileMonitorService
{
  public static final String SERVICE_INIT_XML = "filemonitor.xml";
  public static final String FILE_DESC = "FILEDESC";
  public static final String FILE_TYPE = "FILETYPE";
  public static final String TAG_SYSTEMS = "SYSTEMS";
  public static final String TAG_SYSTEM = "SYSTEM";
  public static final String DB_PROPERTIES = "DB_PROPERTIES";
  
  public abstract void initialize(HashMap paramHashMap)
    throws FMException;
  
  public abstract FMFileDescriptions getFileDescriptions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws FMException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException;
  
  public abstract void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws FMException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IFileMonitorService
 * JD-Core Version:    0.7.0.1
 */