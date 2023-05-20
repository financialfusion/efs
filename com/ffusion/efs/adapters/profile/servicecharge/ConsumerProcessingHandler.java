package com.ffusion.efs.adapters.profile.servicecharge;

import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.util.UserUtil;
import com.ffusion.efs.adapters.profile.ChargeableReportHandler;
import com.ffusion.efs.adapters.profile.MasterReportData;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.efs.adapters.profile.ServiceChargeList;
import com.ffusion.efs.adapters.profile.ServiceChargeReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.util.db.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ConsumerProcessingHandler
  extends ReportProcessingHandler
  implements ChargeableReportHandler
{
  private static final int[][] jdField_goto = { { 17, 500 }, { 20, 501 }, { 15, 502 }, { 14, 503 }, { 15, 509 }, { 16, 504 }, { 13, 0 }, { 18, 0 }, { 13, 0 }, { 12, 0 } };
  
  public ConsumerProcessingHandler(ChargeableReportHandler[] paramArrayOfChargeableReportHandler, Locale paramLocale)
  {
    super(paramArrayOfChargeableReportHandler, paramLocale);
    setChargeableReportHandler(this);
  }
  
  public HashMap getReportMap(HashMap paramHashMap)
  {
    return null;
  }
  
  public void executeSql(HashMap paramHashMap)
    throws SQLException, Exception
  {
    boolean bool = true;
    StringBuffer localStringBuffer = new StringBuffer("");
    localStringBuffer.append("SELECT DISTINCT a.USER_ID_INT, c.user_name, cd.cust_id, c.first_name, c.last_name, pri.first_name, pri.last_name FROM AUDIT_LOG a, customer c LEFT OUTER JOIN CUSTOMER_REL cr ON c.directory_id = cr.DIRECTORY_ID LEFT OUTER JOIN customer pri ON pri.directory_id = cr.PRIMARY_USER_ID, customer_directory cd");
    localStringBuffer.append(" WHERE a.USER_ID_INT = c.directory_id");
    localStringBuffer.append(" AND cd.directory_id = c.directory_id AND ");
    localStringBuffer.append(" ( ");
    localStringBuffer.append(DBUtil.generateSQLNumericInClause(getReportCriteria().getIdsDivided(), "a.PRIMARY_USER_ID"));
    localStringBuffer.append(" OR ");
    localStringBuffer.append(DBUtil.generateSQLNumericInClause(getReportCriteria().getIdsDivided(), "a.USER_ID_INT"));
    localStringBuffer.append(" )");
    localStringBuffer.append(formatAllTranTypes(paramHashMap));
    if (getReportCriteria().getAffiliateBankId() != 0)
    {
      localStringBuffer.append(" AND c.affiliate_bank_id");
      localStringBuffer.append(" IN ( ");
      localStringBuffer.append(getReportCriteria().getAffiliateBankId());
      localStringBuffer.append(" ) ");
    }
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    localTimestamp1 = Profile.convertToTimestamp(getReportCriteria().getStart());
    bool = Profile.appendSQLSelectDate2(localStringBuffer, "a.LOG_DATE", localTimestamp1, ">=", bool);
    localTimestamp2 = Profile.convertToTimestamp(getReportCriteria().getEnd());
    bool = Profile.appendSQLSelectDate2(localStringBuffer, "a.LOG_DATE", localTimestamp2, "<=", bool);
    localStringBuffer.append(" ORDER BY c.last_name, c.first_name");
    setReportCriteria(getReportCriteria());
    runSql(localStringBuffer.toString());
  }
  
  public void onKeyChange(long paramLong) {}
  
  protected void createConsumerColumns(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    setDimension(paramReportResult, jdField_goto.length, paramLocale);
    for (int i = 0; i < jdField_goto.length; i++)
    {
      ReportColumn localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setLabels(null);
      localReportColumn.setWidthAsPercent(jdField_goto[i][0]);
      paramReportResult.addColumn(localReportColumn);
    }
  }
  
  public void createSubReport(ReportResult paramReportResult, String paramString1, String paramString2, String paramString3, String paramString4)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(getLocale());
    paramReportResult.addSubReport(localReportResult);
    createConsumerColumns(localReportResult, getLocale());
    ReportDataItems localReportDataItems = new ReportDataItems(getLocale());
    ReportRow localReportRow = new ReportRow(getLocale());
    localReportDataItems = new ReportDataItems(getLocale());
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData(paramString1);
    localReportDataItems.add().setData(paramString2);
    localReportDataItems.add().setData(paramString4);
    localReportDataItems.add().setData(paramString3);
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
  }
  
  public int processResults(ReportResult paramReportResult, ServiceChargeList paramServiceChargeList, Locale paramLocale, HashMap paramHashMap)
    throws Exception, RptException
  {
    long l2 = 0L;
    int i = 0;
    String str1 = null;
    String str2 = null;
    ChargeableReportHandler localChargeableReportHandler = null;
    for (int j = 0; j < getHandlers().length; j++)
    {
      localChargeableReportHandler = getHandlers()[j];
      localChargeableReportHandler.initialize(getConnection(), getReportCriteria());
      localChargeableReportHandler.executeSql(paramHashMap);
    }
    while (getResultSet().next())
    {
      long l1 = getResultSet().getLong(1);
      str1 = UserUtil.getFullName(getResultSet().getString(4), getResultSet().getString(5), paramLocale);
      String str3 = getResultSet().getString(6);
      String str4 = getResultSet().getString(7);
      if ((str3 != null) && (str4 != null)) {
        str2 = UserUtil.getFullName(str3, str4, paramLocale);
      } else {
        str2 = "";
      }
      if (hasValueChanged(l2, l1))
      {
        createSubReport(paramReportResult, str1, getResultSet().getString(2), getResultSet().getString(3), str2);
        for (int m = 0; m < getHandlers().length; m++)
        {
          localChargeableReportHandler = getHandlers()[m];
          localChargeableReportHandler.onKeyChange(l1);
          i += localChargeableReportHandler.processResults(paramReportResult, paramServiceChargeList, paramLocale, paramHashMap);
          if ((getMaxDisplay() > 0) && (i >= getMaxDisplay()))
          {
            HashMap localHashMap = paramReportResult.getProperties();
            if (localHashMap == null)
            {
              localHashMap = new HashMap();
              paramReportResult.setProperties(localHashMap);
            }
            localHashMap.put("TRUNCATED", new Integer(i));
          }
        }
      }
      l2 = l1;
    }
    for (int k = 0; k < getHandlers().length; k++)
    {
      localChargeableReportHandler = getHandlers()[k];
      localChargeableReportHandler.close();
    }
    close();
    return i;
  }
  
  private void jdMethod_if(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    for (int i = 0; i < jdField_goto.length; i++)
    {
      ReportColumn localReportColumn = new ReportColumn(paramLocale);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(jdField_goto[i][1], paramLocale));
      localReportColumn.setLabels(localArrayList);
      localReportColumn.setWidthAsPercent(jdField_goto[i][0]);
      paramReportResult.addColumn(localReportColumn);
    }
  }
  
  public ReportResult createMainSubReport(ReportResult paramReportResult, MasterReportData paramMasterReportData, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    setDimension(localReportResult, 10, paramLocale);
    jdMethod_if(localReportResult, paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    ReportRow localReportRow = new ReportRow(paramLocale);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItems.add().setData(paramMasterReportData.marketSegment);
    localReportDataItems.add().setData(paramMasterReportData.servicePackage);
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    return localReportResult;
  }
  
  public ChargeableReportHandler getChargeableReportHandler()
  {
    return this;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.servicecharge.ConsumerProcessingHandler
 * JD-Core Version:    0.7.0.1
 */