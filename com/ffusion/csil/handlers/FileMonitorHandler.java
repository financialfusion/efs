package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.IFileMonitorService;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMFileDescriptions;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class FileMonitorHandler
{
  private static final String a = "File Monitor";
  private static IFileMonitorService jdField_if = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "FileMonitorHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "File Monitor", str, 20107);
    jdField_if = (IFileMonitorService)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      jdField_if.initialize(paramHashMap);
    }
    catch (FMException localFMException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFMException), localFMException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_if;
  }
  
  public static FMFileDescriptions getFileDescriptions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    
    try
    {
      return jdField_if.getFileDescriptions(paramSecureUser, paramHashMap);
    }
    catch (FMException localFMException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFMException), localFMException);
      DebugLog.throwing("FileMonitorHandler.getFileDescriptions", localCSILException);
      throw localCSILException;
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    
    try
    {
      return jdField_if.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing("FileMonitorHandler.getReportData", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    
    try
    {
      jdField_if.cleanup(paramString, paramInt, paramHashMap);
    }
    catch (FMException localFMException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFMException), localFMException);
      DebugLog.throwing("FileMonitorHandler.getReportData", localCSILException);
      throw localCSILException;
    }
  }
  
  private static void a()
    throws CSILException
  {
    if (jdField_if == null) {
      throw new CSILException(-1009, 34504, "FileMonitorHandler.assertService", "File monitor service is not initialized");
    }
  }
  
  private static int a(FMException paramFMException)
  {
    int i = 34500;
    switch (paramFMException.getCode())
    {
    case -1: 
      break;
    case 1: 
    case 2: 
      i = 34503;
      break;
    case 3: 
      i = 34505;
      break;
    case 4: 
      i = 34506;
      break;
    case 5: 
    case 6: 
      i = 34502;
      break;
    case 7: 
    case 8: 
      i = 34501;
      break;
    }
    return i;
  }
  
  private static int a(RptException paramRptException)
  {
    Throwable localThrowable = paramRptException.getChained();
    if ((localThrowable != null) && ((localThrowable instanceof FMException))) {
      return a((FMException)localThrowable);
    }
    int i = paramRptException.getErrorCode();
    switch (paramRptException.getErrorCode())
    {
    case 5: 
    case 100: 
      i = 34506;
      break;
    case 111: 
    case 113: 
    case 114: 
      i = 34505;
      break;
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.FileMonitorHandler
 * JD-Core Version:    0.7.0.1
 */