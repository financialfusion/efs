package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.BCReportException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.BCReport2;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;

public class BCReportHandler
{
  private static final String a = "BCReport";
  private static BCReport2 jdField_if = null;
  
  public static void initialize(HashMap paramHashMap)
  {
    String str = "BCReportHandler.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "BCReport", str, 20107);
      jdField_if = (BCReport2)HandlerUtil.instantiateService(localHashMap, str, 20107);
      jdField_if.initialize();
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str + ':' + localException.toString());
    }
  }
  
  public static Object getService()
  {
    return jdField_if;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BCReportHandler.getReportData";
    try
    {
      return jdField_if.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (BCReportException localBCReportException)
    {
      CSILException localCSILException = new CSILException(-1009, localBCReportException.code, localBCReportException);
      DebugLog.throwing("BCReportHandler.getReportData", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void appendReportTypeEntitlements(HashMap paramHashMap)
    throws CSILException
  {
    String str = "BCReportHandler.appendReportTypeEntitlements";
    try
    {
      jdField_if.appendReportTypeEntitlements(paramHashMap);
    }
    catch (BCReportException localBCReportException)
    {
      CSILException localCSILException = new CSILException(22000, localBCReportException.code, localBCReportException);
      DebugLog.throwing("BCReportHandler.appendReportTypeEntitlements", localCSILException);
      throw localCSILException;
    }
  }
  
  private static Locale a(HashMap paramHashMap)
  {
    Locale localLocale = (Locale)paramHashMap.get("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    return localLocale;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.BCReportHandler
 * JD-Core Version:    0.7.0.1
 */