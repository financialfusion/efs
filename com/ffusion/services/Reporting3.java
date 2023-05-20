package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.RptException;
import java.util.HashMap;

public abstract interface Reporting3
  extends Reporting2
{
  public static final String REPORT_DEFAULT_CRITERIA = "REPORTDEFAULTCRITERIA_LIST";
  public static final String REPORTCRITERIA = "REPORTCRITERIA";
  public static final String NAME = "NAME";
  public static final String VALUE = "VALUE";
  public static final String SEARCH_CRITERIA_LIST = "SEARCH_CRITERIA_LIST";
  public static final String SORT_CRITERIA_LIST = "SORT_CRITERIA_LIST";
  public static final String CRITERIA = "CRITERIA";
  public static final String SORT_CRITERION = "SORT_CRITERION";
  public static final String ORDINAL = "ORDINAL";
  public static final String CRITERIONNAME = "CRITERIONNAME";
  public static final String ASC = "ASC";
  public static final String ENTITLEMENT_TYPE_LIST = "ENTITLEMENT_TYPE_LIST";
  public static final String ENTITLEMENT_TYPE_LIST_OPERATION = "OPERATION";
  public static final String ENTITLEMENT_TYPE_LIST_OPERATION_VALUE_OR = "OR";
  public static final String ENTITLEMENT_TYPE_LIST_OPERATION_VALUE_AND = "AND";
  public static final String ENTITLEMENT_TYPE = "ENTITLEMENT_TYPE";
  
  public abstract ReportCriteria getDefaultReportCriteria(String paramString)
    throws RptException;
  
  public abstract Object exportHeaderOptions(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws RptException;
  
  public abstract Object exportFooterOptions(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws RptException;
  
  public abstract void prepareForReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException;
  
  public abstract Object exportPiece(SecureUser paramSecureUser, Object paramObject1, String paramString, Object paramObject2, HashMap paramHashMap)
    throws RptException;
  
  public abstract Object closeReport(SecureUser paramSecureUser, String paramString, Object paramObject, HashMap paramHashMap)
    throws RptException;
  
  public abstract void calculateDateRange(SecureUser paramSecureUser, Accounts paramAccounts, ReportCriteria paramReportCriteria, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3)
    throws RptException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Reporting3
 * JD-Core Version:    0.7.0.1
 */