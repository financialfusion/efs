package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.reporting.RptException;
import java.util.HashMap;

public abstract interface Reporting
{
  public static final String SERVICE_INIT_XML = "reporting.xml";
  public static final String REPORTING_ADAPTER = "REPORTINGADAPTER";
  public static final String ADAPTER_CLASS = "JAVA_CLASS";
  public static final String REPORTING_ADAPTER_SETTINGS = "REPORTINGADAPTER_SETTINGS";
  
  public abstract void initialize()
    throws RptException;
  
  public abstract ReportIdentifications getReports(SecureUser paramSecureUser, HashMap paramHashMap)
    throws RptException;
  
  public abstract Report getReportCriteria(SecureUser paramSecureUser, ReportIdentification paramReportIdentification)
    throws RptException;
  
  public abstract void addReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws RptException;
  
  public abstract void modifyReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws RptException;
  
  public abstract void deleteReport(SecureUser paramSecureUser, ReportIdentification paramReportIdentification, HashMap paramHashMap)
    throws RptException;
  
  public abstract Object exportReport(Report paramReport, String paramString, HashMap paramHashMap)
    throws RptException;
  
  public abstract void deleteUser(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws RptException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Reporting
 * JD-Core Version:    0.7.0.1
 */