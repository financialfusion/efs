package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.RptException;
import com.ffusion.services.Reporting4;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class ReportingHandler
{
  private static final String jdField_if = "Reporting";
  private static Reporting4 jdField_do = null;
  private static String a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportingHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Reporting", str, 20107);
    jdField_do = (Reporting4)HandlerUtil.instantiateService(localHashMap, str, 20107);
    a = HandlerUtil.getReportMaxDisplay(paramHashMap);
    try
    {
      jdField_do.initialize();
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_do;
  }
  
  public static ReportCriteria getDefaultReportCriteria(String paramString)
    throws CSILException
  {
    String str = "ReportingHandler.getDefaultReportCriteria";
    try
    {
      return jdField_do.getDefaultReportCriteria(paramString);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ReportCriteria getDefaultReportCriteria(String paramString, Locale paramLocale, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportingHandler.getDefaultReportCriteria";
    try
    {
      ReportCriteria localReportCriteria = null;
      localReportCriteria = jdField_do.getDefaultReportCriteria(paramString, paramLocale, paramHashMap);
      return localReportCriteria;
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ReportIdentifications getReports(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportingHandler.getReports";
    try
    {
      return jdField_do.getReports(paramSecureUser, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ReportIdentification getReport(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportingHandler.getReport";
    try
    {
      return jdField_do.getReport(paramSecureUser, paramString, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Report getReportCriteria(SecureUser paramSecureUser, ReportIdentification paramReportIdentification)
    throws CSILException
  {
    String str = "ReportingHandler.getReportCriteria";
    try
    {
      return jdField_do.getReportCriteria(paramSecureUser, paramReportIdentification);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportingHandler.addReport";
    try
    {
      jdField_do.addReport(paramSecureUser, paramReport, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportingHandler.modifyReport";
    try
    {
      jdField_do.modifyReport(paramSecureUser, paramReport, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteReport(SecureUser paramSecureUser, ReportIdentification paramReportIdentification, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportingHandler.deleteReport";
    try
    {
      jdField_do.deleteReport(paramSecureUser, paramReportIdentification, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object exportReport(SecureUser paramSecureUser, Report paramReport, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportingHandler.exportReport";
    try
    {
      return jdField_do.exportReport(paramReport, paramString, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object exportHeaderOptions(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportHandler.exportHeaderOptions";
    try
    {
      return jdField_do.exportHeaderOptions(paramSecureUser, paramReportCriteria, paramString, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object exportFooterOptions(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ReportHandler.exportFooterOptions";
    try
    {
      return jdField_do.exportFooterOptions(paramSecureUser, paramReportCriteria, paramString, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void prepareForReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ReportHandler.prepareForReport";
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("MAXDISPLAYSIZE");
    if (str2 != null)
    {
      try
      {
        if (Integer.parseInt(str2) == 0) {
          str2 = null;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        str2 = null;
      }
      if (str2 == null) {
        localProperties.setProperty("MAXDISPLAYSIZE", a);
      }
    }
    String str3 = localProperties.getProperty("SHOWCOMPANYNAME");
    if ((str3 != null) && (str3.equals("TRUE"))) {
      if (paramSecureUser.getBusinessName() == null) {
        localProperties.setProperty("COMPANYNAME", "");
      } else {
        localProperties.setProperty("COMPANYNAME", paramSecureUser.getBusinessName());
      }
    }
    paramReportCriteria.clearDisplayValues();
    paramReportCriteria.clearHiddenSearchCriteria();
    try
    {
      jdField_do.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void calculateDateRange(SecureUser paramSecureUser, Accounts paramAccounts, ReportCriteria paramReportCriteria, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3)
    throws CSILException
  {
    String str = "ReportHandler.calculateDateRange";
    try
    {
      jdField_do.calculateDateRange(paramSecureUser, paramAccounts, paramReportCriteria, paramHashMap1, paramHashMap2, paramHashMap3);
    }
    catch (RptException localRptException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localRptException), localRptException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static int a(RptException paramRptException)
  {
    switch (paramRptException.getErrorCode())
    {
    case 1: 
      return 21000;
    case 2: 
      return 21001;
    case 3: 
      return 21002;
    case 4: 
      return 21003;
    case 5: 
      return 21004;
    case 6: 
      return 21005;
    case 7: 
      return 21006;
    case 111: 
      return 21100;
    case 200: 
      return 21100;
    case 201: 
      return 21100;
    case 202: 
      return 21100;
    case 203: 
      return 21100;
    case 204: 
      return 21100;
    case 112: 
      return 21111;
    case 113: 
      return 21112;
    case 114: 
      return 21113;
    case 121: 
      return 21122;
    case 115: 
      return 21114;
    case 116: 
      return 21115;
    case 300: 
      return 21100;
    case 301: 
      return 21100;
    case 302: 
      return 21100;
    case 303: 
      return 21100;
    case 304: 
      return 21100;
    case 305: 
      return 21100;
    case 306: 
      return 21121;
    case 307: 
      return 21100;
    case 117: 
      return 21116;
    case 118: 
      return 21117;
    case 119: 
      return 21118;
    case 11: 
      return 21100;
    case 12: 
      return 21119;
    case 13: 
      return 21100;
    case 14: 
      return 21100;
    case 15: 
      return 21100;
    case 16: 
      return 21100;
    case 308: 
      return 21100;
    }
    return 1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.ReportingHandler
 * JD-Core Version:    0.7.0.1
 */