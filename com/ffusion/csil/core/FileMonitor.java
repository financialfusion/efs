package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.FileMonitorHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.filemonitor.FMFileDescriptions;
import com.ffusion.util.logging.PerfLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.util.HashMap;
import java.util.Properties;

public class FileMonitor
  extends Initialize
{
  private static Entitlement aHv = new Entitlement("File Monitor", null, null);
  private static final String aHt = "com.ffusion.util.logging.audit.filemonitor";
  private static final String aHu = "AuditEntFault_1";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    FileMonitorHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return FileMonitorHandler.getService();
  }
  
  private static boolean i(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aHv);
    if (!bool)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.filemonitor", "AuditEntFault_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    }
    return bool;
  }
  
  public static FMFileDescriptions getFileDescriptions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "FileMonitor.getFileDescriptions";
    FMFileDescriptions localFMFileDescriptions = null;
    if (i(paramSecureUser)) {
      localFMFileDescriptions = FileMonitorHandler.getFileDescriptions(paramSecureUser, paramHashMap);
    } else {
      throw new CSILException("FileMonitor getFileDescriptions failed.", 20001);
    }
    PerfLog.log(str, l, true);
    return localFMFileDescriptions;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str1 = "FileMonitor.getReportData";
    IReportResult localIReportResult = null;
    String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    if (i(paramSecureUser))
    {
      Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
      localIReportResult = FileMonitorHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    else
    {
      throw new CSILException("FileMonitor.getReportData failed.", 20001);
    }
    PerfLog.log(str1, l, true);
    return localIReportResult;
  }
  
  public static void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    FileMonitorHandler.cleanup(paramString, paramInt, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.FileMonitor
 * JD-Core Version:    0.7.0.1
 */