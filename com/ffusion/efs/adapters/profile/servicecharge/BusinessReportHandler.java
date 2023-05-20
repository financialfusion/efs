package com.ffusion.efs.adapters.profile.servicecharge;

import com.ffusion.beans.Currency;
import com.ffusion.beans.bcreport.ServiceChargeEntries;
import com.ffusion.beans.bcreport.ServiceChargeEntry;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.efs.adapters.profile.ServiceChargeList;
import com.ffusion.efs.adapters.profile.ServiceChargeReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.util.db.DBUtil;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class BusinessReportHandler
  extends TransactionTypeSummaryReportHandler
{
  public BusinessReportHandler(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  protected void createColumns(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    setDimension(paramReportResult, 7, paramLocale);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    addBlankColumns(paramReportResult, paramLocale, 3);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(403, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(404, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(406, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(405, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
  }
  
  public ReportResult createSubReport(ReportResult paramReportResult)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(getLocale());
    paramReportResult.addSubReport(localReportResult);
    setDimension(localReportResult, 7, getLocale());
    createColumns(localReportResult, getLocale());
    return localReportResult;
  }
  
  public int processResults(ReportResult paramReportResult, ServiceChargeList paramServiceChargeList, Locale paramLocale, HashMap paramHashMap)
    throws Exception, RptException
  {
    long l2 = 0L;
    ServiceChargeEntries localServiceChargeEntries = new ServiceChargeEntries();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    BigDecimal localBigDecimal = null;
    String str1 = null;
    ReportResult localReportResult = null;
    int n = 0;
    Object localObject = null;
    int i1 = 1;
    if ((getPreviousRecord() != null) && (!isValidValue(getPreviousRecord().ag(), getPreviousRecord().af())))
    {
      i1 = 0;
    }
    else if (getPreviousRecord() != null)
    {
      localObject = getPreviousRecord();
      localServiceChargeEntries.add(localObject);
      setPreviousRecord(null);
    }
    if (i1 != 0)
    {
      while (getResultSet().next())
      {
        m++;
        long l1 = getResultSet().getLong(3);
        i = getResultSet().getInt(1);
        j = getResultSet().getInt(2);
        k = 0;
        localBigDecimal = getResultSet().getBigDecimal(4);
        str1 = getResultSet().getString(6);
        ArrayList localArrayList = getServiceCodesFromTransactionType(j, getReportMap(paramHashMap));
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          String str3 = paramServiceChargeList.getServiceCodeByOperationName(getReportCriteria().getChargeableOperations(), str2);
          if (localBigDecimal == null) {
            localBigDecimal = new BigDecimal(0.0D);
          }
          storeRow(str2);
          if (!isValidValue(str2, l1)) {
            break;
          }
          n = (l2 > 0L) && (hasValueChanged(l2, l1)) ? 1 : 0;
          if (n != 0) {
            break;
          }
          if (m == 1) {
            localReportResult = createSubReport(paramReportResult);
          }
          localObject = getEntryByServiceCode(localServiceChargeEntries, str3);
          if (localObject == null)
          {
            ServiceChargeEntry localServiceChargeEntry = new ServiceChargeEntry(str3, i, new Currency(localBigDecimal, str1, getLocale()));
            localServiceChargeEntry.setCurrencyCode(str1);
            localServiceChargeEntries.add(localServiceChargeEntry);
          }
          else
          {
            ((ServiceChargeEntry)localObject).addNumTransactions(i);
            ((ServiceChargeEntry)localObject).addTotalTransactionValue(localBigDecimal);
          }
          if (maxRowLimitReached(m)) {
            break;
          }
          l2 = l1;
        }
      }
      if ((localServiceChargeEntries != null) && (localServiceChargeEntries.size() > 0) && (localReportResult == null))
      {
        localReportResult = createSubReport(paramReportResult);
        m++;
      }
      if (localReportResult != null) {
        k += addBusinessEntriesToReport(localReportResult, localServiceChargeEntries, 3);
      }
    }
    return m;
  }
  
  public void executeSql(HashMap paramHashMap)
    throws SQLException, Exception
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    localStringBuffer.append("Select Tempx.count1, Tempx.tran_type, Tempx.business_id, Tempx.sum1, b.business_name, Tempx.currency_code ");
    localStringBuffer.append("FROM (SELECT COUNT(TRAN_ID) count1, a.TRAN_TYPE tran_type, a.CURRENCY_CODE currency_code, a.BUSINESS_ID business_id, SUM(AMOUNT) sum1 FROM AUDIT_LOG a ");
    localStringBuffer.append(" WHERE ");
    localStringBuffer.append(DBUtil.generateSQLNumericInClause(getReportCriteria().getIdsDivided(), "a.BUSINESS_ID"));
    localStringBuffer.append("AND ");
    localStringBuffer.append(DBUtil.generateSQLNumericInClause(getTransTypesDelimited(getReportCriteria().getChargeableOperationsAsArray(), this, paramHashMap, ""), "a.TRAN_TYPE"));
    localStringBuffer.append(" AND (a.SRVR_TID is null or a.TRAN_ID > all (select TRAN_ID from AUDIT_LOG a2 where a2.SRVR_TID = a.SRVR_TID and a2.TRAN_ID != a.TRAN_ID))");
    formatLogDatesForSql(localStringBuffer);
    localStringBuffer.append(" GROUP BY a.BUSINESS_ID, a.TRAN_TYPE, a.CURRENCY_CODE) Tempx, business b WHERE Tempx.business_id=b.directory_id");
    if (getReportCriteria().getAffiliateBankId() != 0)
    {
      localStringBuffer.append(" and b.affiliate_bank_id=");
      localStringBuffer.append(getReportCriteria().getAffiliateBankId());
    }
    localStringBuffer.append(" ORDER BY b.business_name");
    runSql(localStringBuffer.toString());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.servicecharge.BusinessReportHandler
 * JD-Core Version:    0.7.0.1
 */