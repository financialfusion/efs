package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.reporting.RptException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;

public abstract interface ChargeableReportHandler
{
  public abstract HashMap getReportMap(HashMap paramHashMap);
  
  public abstract void executeSql(HashMap paramHashMap)
    throws SQLException, Exception;
  
  public abstract void initialize(Connection paramConnection, ServiceChargeReportCriteria paramServiceChargeReportCriteria);
  
  public abstract void close();
  
  public abstract boolean anyRecords(Connection paramConnection, HashMap paramHashMap, ServiceChargeReportCriteria paramServiceChargeReportCriteria)
    throws SQLException, Exception;
  
  public abstract void onKeyChange(long paramLong);
  
  public abstract int processResults(ReportResult paramReportResult, ServiceChargeList paramServiceChargeList, Locale paramLocale, HashMap paramHashMap)
    throws Exception, RptException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ChargeableReportHandler
 * JD-Core Version:    0.7.0.1
 */