package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.ReportingHandler;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;
import java.util.Locale;

public class Reporting
  extends Initialize
{
  private static Entitlement aBr = new Entitlement("Export Reports", null, null);
  private static String aBq;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    ReportingHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return ReportingHandler.getService();
  }
  
  public static ReportCriteria getDefaultReportCriteria(String paramString)
    throws CSILException
  {
    long l = 0L;
    String str = "Reporting.getDefaultReportCriteria";
    try
    {
      l = System.currentTimeMillis();
      ReportCriteria localReportCriteria = ReportingHandler.getDefaultReportCriteria(paramString);
      return localReportCriteria;
    }
    finally
    {
      PerfLog.log(str, l, true);
    }
  }
  
  public static ReportCriteria getDefaultReportCriteria(String paramString, Locale paramLocale, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Reporting.getDefaultReportCriteria";
    long l = 0L;
    try
    {
      ReportCriteria localReportCriteria1 = null;
      l = System.currentTimeMillis();
      localReportCriteria1 = ReportingHandler.getDefaultReportCriteria(paramString, paramLocale, paramHashMap);
      ReportCriteria localReportCriteria2 = localReportCriteria1;
      return localReportCriteria2;
    }
    finally
    {
      PerfLog.log(str, l, true);
    }
  }
  
  public static ReportIdentifications getReports(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    long l = 0L;
    String str = "Reporting.getReports";
    try
    {
      l = System.currentTimeMillis();
      ReportIdentifications localReportIdentifications = ReportingHandler.getReports(paramSecureUser, paramHashMap);
      return localReportIdentifications;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static ReportIdentification getReport(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    long l = 0L;
    String str = "Reporting.getReport";
    try
    {
      l = System.currentTimeMillis();
      ReportIdentification localReportIdentification = ReportingHandler.getReport(paramSecureUser, paramString, paramHashMap);
      return localReportIdentification;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static Report getReportCriteria(SecureUser paramSecureUser, ReportIdentification paramReportIdentification)
    throws CSILException
  {
    long l = 0L;
    String str = "Reporting.getReportCriteria";
    try
    {
      l = System.currentTimeMillis();
      Report localReport = ReportingHandler.getReportCriteria(paramSecureUser, paramReportIdentification);
      return localReport;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static void addReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws CSILException
  {
    long l = 0L;
    String str = "Reporting.addReport";
    try
    {
      l = System.currentTimeMillis();
      ReportingHandler.addReport(paramSecureUser, paramReport, paramHashMap);
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static void modifyReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws CSILException
  {
    long l = 0L;
    String str = "Reporting.modifyReport";
    try
    {
      l = System.currentTimeMillis();
      ReportingHandler.modifyReport(paramSecureUser, paramReport, paramHashMap);
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static void deleteReport(SecureUser paramSecureUser, ReportIdentification paramReportIdentification, HashMap paramHashMap)
    throws CSILException
  {
    long l = 0L;
    String str = "Reporting.deleteReport";
    try
    {
      l = System.currentTimeMillis();
      ReportingHandler.deleteReport(paramSecureUser, paramReportIdentification, paramHashMap);
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static Object exportReport(SecureUser paramSecureUser, Report paramReport, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Reporting.exportReport";
    long l = 0L;
    try
    {
      l = System.currentTimeMillis();
      Object localObject1 = ReportingHandler.exportReport(paramSecureUser, paramReport, paramString, paramHashMap);
      return localObject1;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static Object exportHeaderOptions(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Reporting.exportHeaderOptions";
    long l = 0L;
    try
    {
      l = System.currentTimeMillis();
      Object localObject1 = ReportingHandler.exportHeaderOptions(paramSecureUser, paramReportCriteria, paramString, paramHashMap);
      return localObject1;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static Object exportFooterOptions(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Reporting.exportFooterOptions";
    long l = 0L;
    try
    {
      l = System.currentTimeMillis();
      Object localObject1 = ReportingHandler.exportFooterOptions(paramSecureUser, paramReportCriteria, paramString, paramHashMap);
      return localObject1;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static void prepareForReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Reporting.prepareForReport";
    long l = 0L;
    try
    {
      l = System.currentTimeMillis();
      ReportingHandler.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static void calculateDateRange(SecureUser paramSecureUser, Accounts paramAccounts, ReportCriteria paramReportCriteria, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3)
    throws CSILException
  {
    String str = "Reporting.calculateDateRange";
    long l = 0L;
    try
    {
      l = System.currentTimeMillis();
      ReportingHandler.calculateDateRange(paramSecureUser, paramAccounts, paramReportCriteria, paramHashMap1, paramHashMap2, paramHashMap3);
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Reporting
 * JD-Core Version:    0.7.0.1
 */