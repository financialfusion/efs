package com.ffusion.services.bcreport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.efs.adapters.profile.BCReportAdapter;
import com.ffusion.efs.adapters.profile.BCReportException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.BCReport2;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class BCReportService
  implements BCReport2
{
  public static String OPERATION_MAP_KEY = "OPERATION_MAP_KEY";
  public static String DURATION_MAP_KEY = "DURATION_MAP_KEY";
  public static final Locale currentLocale = Locale.getDefault();
  public static final String ACCOUNTS_FOR_REPORTS = "Accounts";
  private BCReportAdapter jdField_if;
  protected static HashMap _operationMap;
  private static HashMap a;
  
  public void initialize()
  {
    this.jdField_if = new BCReportAdapter();
    initializeOperationNameToAuditTypeMap();
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException
  {
    String str1 = "BCReportService.getReportData";
    paramHashMap.put(OPERATION_MAP_KEY, _operationMap);
    paramHashMap.put(DURATION_MAP_KEY, getDurationMap());
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    try
    {
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      paramHashMap.put("StartDates", localHashMap1);
      paramHashMap.put("EndDates", localHashMap2);
      Properties localProperties = paramReportCriteria.getReportOptions();
      str2 = localProperties.getProperty("REPORTTYPE");
      Locale localLocale = paramSecureUser.getLocale();
      if (str2.equals("BC Non-zero Balance Report"))
      {
        a(paramReportCriteria, localLocale, paramHashMap);
        paramReportCriteria.setHiddenSearchCriterion("Display", true);
        paramReportCriteria.setHiddenSearchCriterion("DataClassification", true);
      }
    }
    catch (CSILException localCSILException)
    {
      String str2 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log(str2);
      if (localCSILException.getCode() == -1009) {
        throw new BCReportException(localCSILException, str1, localCSILException.getServiceError(), str2);
      }
      throw new BCReportException(localCSILException, str1, localCSILException.getCode(), str2);
    }
    return this.jdField_if.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
  }
  
  public static void initializeOperationNameToAuditTypeMap()
  {
    if (getDurationMap() == null) {
      setDurationMap(new HashMap());
    }
    if (_operationMap == null)
    {
      _operationMap = new HashMap();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new Integer(3600));
      _operationMap.put("ACHBatch", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3603));
      _operationMap.put("Transfers", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3601));
      _operationMap.put("Payments", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3604));
      _operationMap.put("Wire Domestic", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3605));
      _operationMap.put("Wire International", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3612));
      _operationMap.put("Wire Host", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3606));
      _operationMap.put("Wire Drawdown", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3608));
      _operationMap.put("Wire FED", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3607));
      _operationMap.put("Wire Book", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(3602));
      _operationMap.put("CCD + TXP", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(5408));
      localArrayList.add(new Integer(5409));
      getDurationMap().put("TTY", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(5411));
      localArrayList.add(new Integer(5412));
      localArrayList.add(new Integer(5413));
      localArrayList.add(new Integer(5414));
      localArrayList.add(new Integer(5415));
      localArrayList.add(new Integer(5416));
      _operationMap.put("Information Reporting - Intra Day Detail", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(5410));
      localArrayList.add(new Integer(5412));
      localArrayList.add(new Integer(5417));
      _operationMap.put("Information Reporting - Intra Day Summary", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(5431));
      localArrayList.add(new Integer(5432));
      localArrayList.add(new Integer(5433));
      localArrayList.add(new Integer(5434));
      localArrayList.add(new Integer(5435));
      localArrayList.add(new Integer(5436));
      _operationMap.put("Information Reporting - Previous Day Detail", localArrayList);
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(5430));
      localArrayList.add(new Integer(5432));
      localArrayList.add(new Integer(5437));
      _operationMap.put("Information Reporting - Previous Day Summary", localArrayList);
    }
  }
  
  public void appendReportTypeEntitlements(HashMap paramHashMap)
    throws BCReportException
  {}
  
  protected static HashMap getDurationMap()
  {
    return a;
  }
  
  protected static void setDurationMap(HashMap paramHashMap)
  {
    a = paramHashMap;
  }
  
  private static String a(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || (str.equals(""))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  private static void a(ReportCriteria paramReportCriteria, Locale paramLocale, HashMap paramHashMap)
  {
    String str1 = a(paramReportCriteria.getSearchCriteria(), "Affiliate Bank", "AllAffiliateBanks");
    if (str1.equals("AllAffiliateBanks"))
    {
      paramReportCriteria.setDisplayValue("Affiliate Bank", ReportConsts.getText(10048, paramLocale));
    }
    else
    {
      str2 = (String)paramHashMap.get("AffiliateBankNameForReport");
      paramReportCriteria.setDisplayValue("Affiliate Bank", str2);
    }
    String str2 = a(paramReportCriteria.getSearchCriteria(), "Business", "AllBusinesses");
    if (str2.equals("AllBusinesses"))
    {
      paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10015, paramLocale));
    }
    else
    {
      Businesses localBusinesses = (Businesses)paramHashMap.get("BusinessCollection");
      if (localBusinesses != null)
      {
        Iterator localIterator = localBusinesses.iterator();
        for (Business localBusiness = null; localIterator.hasNext(); localBusiness = (Business)localIterator.next()) {}
        StringBuffer localStringBuffer = new StringBuffer(localBusiness.getBusinessName());
        String str3 = localBusiness.getCustId();
        if (str3 != null)
        {
          str3 = str3.trim();
          if (str3.length() > 0) {
            localStringBuffer.append("(").append(str3).append(")");
          }
        }
        paramReportCriteria.setDisplayValue("Business", localStringBuffer.toString());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bcreport.BCReportService
 * JD-Core Version:    0.7.0.1
 */