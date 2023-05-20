package com.ffusion.efs.adapters.profile.servicecharge;

import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
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

public class BusinessProcessingHandler
  extends ReportProcessingHandler
  implements ChargeableReportHandler
{
  public BusinessProcessingHandler(ChargeableReportHandler[] paramArrayOfChargeableReportHandler, Locale paramLocale)
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
    localStringBuffer.append("SELECT DISTINCT a.BUSINESS_ID business_id, b.business_name FROM AUDIT_LOG a, business b ");
    localStringBuffer.append(" WHERE a.BUSINESS_ID = b.business_id AND ");
    localStringBuffer.append(DBUtil.generateSQLNumericInClause(getReportCriteria().getIdsDivided(), "a.BUSINESS_ID"));
    localStringBuffer.append(formatAllTranTypes(paramHashMap));
    if (getReportCriteria().getAffiliateBankId() != 0)
    {
      localStringBuffer.append(" AND b.affiliate_bank_id");
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
    localStringBuffer.append(" ORDER BY b.business_name");
    setReportCriteria(getReportCriteria());
    runSql(localStringBuffer.toString());
  }
  
  public void onKeyChange(long paramLong) {}
  
  protected void createBusinessColumns(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    setDimension(paramReportResult, 7, paramLocale);
    addBlankColumns(paramReportResult, paramLocale, 7);
  }
  
  public void createBusinessSubReport(ReportResult paramReportResult, String paramString)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(getLocale());
    paramReportResult.addSubReport(localReportResult);
    setDimension(localReportResult, 7, getLocale());
    createBusinessColumns(localReportResult, getLocale());
    ReportDataItems localReportDataItems = new ReportDataItems(getLocale());
    ReportRow localReportRow = new ReportRow(getLocale());
    localReportDataItems = new ReportDataItems(getLocale());
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData(paramString);
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
    String str = null;
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
      str = getResultSet().getString(2);
      if (hasValueChanged(l2, l1))
      {
        createBusinessSubReport(paramReportResult, str);
        for (j = 0; j < getHandlers().length; j++)
        {
          localChargeableReportHandler = getHandlers()[j];
          localChargeableReportHandler.onKeyChange(getResultSet().getLong(1));
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
    for (j = 0; j < getHandlers().length; j++)
    {
      localChargeableReportHandler = getHandlers()[j];
      localChargeableReportHandler.close();
    }
    close();
    return i;
  }
  
  private void a(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(400, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(18);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(401, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(18);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(402, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(18);
    paramReportResult.addColumn(localReportColumn);
    addBlankColumns(paramReportResult, getLocale(), 4);
  }
  
  public ReportResult createMainSubReport(ReportResult paramReportResult, MasterReportData paramMasterReportData, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    setDimension(localReportResult, 7, paramLocale);
    a(localReportResult, paramLocale);
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
 * Qualified Name:     com.ffusion.efs.adapters.profile.servicecharge.BusinessProcessingHandler
 * JD-Core Version:    0.7.0.1
 */