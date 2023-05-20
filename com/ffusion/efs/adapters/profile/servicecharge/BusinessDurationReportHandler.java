package com.ffusion.efs.adapters.profile.servicecharge;

import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.efs.adapters.profile.ServiceChargeList;
import com.ffusion.efs.adapters.profile.ServiceChargeReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.util.db.DBUtil;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class BusinessDurationReportHandler
  extends DurationReportHandler
{
  public BusinessDurationReportHandler(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  protected ReportResult createSubReport(ReportResult paramReportResult)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(getLocale());
    paramReportResult.addSubReport(localReportResult);
    setDimension(localReportResult, 7, getLocale());
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    addBlankColumns(localReportResult, getLocale(), 2);
    addBlankColumn(localReportResult, getLocale(), 19);
    localReportColumn = new ReportColumn(getLocale());
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(403, getLocale()));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(12);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(getLocale());
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(410, getLocale()));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(11);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(getLocale());
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(411, getLocale()));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(11);
    localReportResult.addColumn(localReportColumn);
    addBlankColumn(localReportResult, getLocale(), 11);
    return localReportResult;
  }
  
  public void addEntriesToSubReport(ReportResult paramReportResult, DurationReportHandler.a parama)
    throws RptException
  {
    ReportDataItems localReportDataItems = new ReportDataItems(getLocale());
    ReportRow localReportRow = new ReportRow(getLocale());
    localReportDataItems = new ReportDataItems(getLocale());
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData("");
    localReportDataItems.add().setData(parama.jdMethod_new());
    localReportDataItems.add().setData(Long.toString(parama.jdMethod_byte()));
    localReportDataItems.add().setData(Long.toString(parama.jdMethod_try()));
    localReportDataItems.add().setData("");
    localReportRow.setDataItems(localReportDataItems);
    paramReportResult.addRow(localReportRow);
  }
  
  public int processResults(ReportResult paramReportResult, ServiceChargeList paramServiceChargeList, Locale paramLocale, HashMap paramHashMap)
    throws Exception, RptException
  {
    ReportResult localReportResult = null;
    int j = 0;
    long l2 = 0L;
    int k = 0;
    String str1 = null;
    String[] arrayOfString = null;
    DurationReportHandler.a locala = null;
    Date localDate = null;
    int m = 1;
    int n = 0;
    int i1 = 0;
    if ((getPreviousRecord() != null) && (!isValidValue(Integer.toString(getPreviousRecord().jdMethod_for()), getPreviousRecord().jdMethod_if())))
    {
      m = 0;
    }
    else if (getPreviousRecord() != null)
    {
      locala = getPreviousRecord();
      str1 = getServiceCodeFromTransactionType(locala.jdMethod_for(), getReportMap(paramHashMap));
      arrayOfString = getTransTypePairs(paramHashMap, str1);
      prepareReport(locala, locala.jdMethod_do(), arrayOfString);
      setPreviousRecord(null);
    }
    else
    {
      locala = new DurationReportHandler.a();
    }
    if (m != 0)
    {
      while (getResultSet().next())
      {
        localDate = getResultSet().getDate(4);
        long l1 = getResultSet().getLong(2);
        int i = getResultSet().getInt(1);
        if (invalidLogRecord(arrayOfString, i, localDate)) {
          i1 = 1;
        }
        if (i1 == 0)
        {
          storeRow();
          str1 = getServiceCodeFromTransactionType(i, getReportMap(paramHashMap));
          if (!isValidValue(str1, l1)) {
            break;
          }
          n = (l2 > 0L) && (hasValueChanged(l2, l1)) ? 1 : 0;
          if (n != 0) {
            break;
          }
          k++;
          arrayOfString = getTransTypePairs(paramHashMap, str1);
          String str2 = paramServiceChargeList.getServiceCodeByOperationName(getReportCriteria().getChargeableOperations(), str1);
          if (k == 1) {
            localReportResult = createSubReport(paramReportResult);
          }
          j++;
          locala.jdMethod_if(getResultSet().getInt(1));
          locala.a(getResultSet().getString(5));
          locala.jdMethod_if(str2);
          locala.a(getResultSet().getLong(2));
          prepareReport(locala, localDate, arrayOfString);
          if (maxRowLimitReached(k)) {
            break;
          }
          l2 = l1;
        }
      }
      if (localReportResult != null) {
        addEntriesToSubReport(localReportResult, locala);
      }
    }
    return k;
  }
  
  public void executeSql(HashMap paramHashMap)
    throws SQLException, Exception
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    setReportCriteria(getReportCriteria());
    localStringBuffer.append("SELECT a.TRAN_TYPE, a.BUSINESS_ID business_id, cust.user_name, a.LOG_DATE, b.business_name FROM AUDIT_LOG a, customer cust, business b WHERE ");
    localStringBuffer.append(DBUtil.generateSQLNumericInClause(getReportCriteria().getIdsDivided(), "a.BUSINESS_ID"));
    localStringBuffer.append(formatSqlTranTypes(paramHashMap));
    localStringBuffer.append(" and a.BUSINESS_ID = b.business_id and cust.directory_id = a.USER_ID_INT ");
    formatLogDatesForSql(localStringBuffer);
    if (getReportCriteria().getAffiliateBankId() != 0)
    {
      localStringBuffer.append(" and b.affiliate_bank_id=");
      localStringBuffer.append(getReportCriteria().getAffiliateBankId());
    }
    localStringBuffer.append(" ORDER BY b.business_name, cust.directory_id, a.LOG_DATE");
    setReportCriteria(getReportCriteria());
    runSql(localStringBuffer.toString());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.servicecharge.BusinessDurationReportHandler
 * JD-Core Version:    0.7.0.1
 */