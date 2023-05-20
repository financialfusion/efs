package com.ffusion.efs.adapters.profile.servicecharge;

import com.ffusion.beans.Currency;
import com.ffusion.beans.bcreport.ServiceChargeEntries;
import com.ffusion.beans.bcreport.ServiceChargeEntry;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.efs.adapters.profile.ServiceChargeReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.services.bcreport.BCReportService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public abstract class TransactionTypeSummaryReportHandler
  extends BaseReportServiceCharge
{
  private a jdField_byte = null;
  
  public TransactionTypeSummaryReportHandler(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void initialize(Connection paramConnection, ServiceChargeReportCriteria paramServiceChargeReportCriteria)
  {
    super.initialize(paramConnection, paramServiceChargeReportCriteria);
    setPreviousRecord(null);
  }
  
  public void onKeyChange(long paramLong)
  {
    setId(paramLong);
  }
  
  public HashMap getReportMap(HashMap paramHashMap)
  {
    return (HashMap)paramHashMap.get(BCReportService.OPERATION_MAP_KEY);
  }
  
  public a getPreviousRecord()
  {
    return this.jdField_byte;
  }
  
  public void setPreviousRecord(a parama)
  {
    this.jdField_byte = parama;
  }
  
  protected void storeRow(String paramString)
    throws SQLException
  {
    BigDecimal localBigDecimal = null;
    if (getResultSet() != null)
    {
      localBigDecimal = getResultSet().getBigDecimal(4);
      if (localBigDecimal == null) {
        localBigDecimal = new BigDecimal(0.0D);
      }
      setPreviousRecord(new a(paramString, getResultSet().getInt(1)));
      getPreviousRecord().setCurrencyCode(getResultSet().getString(6));
      getPreviousRecord().setTotalTransactionValue(new Currency(localBigDecimal, getPreviousRecord().getCurrencyCode(), getLocale()));
      getPreviousRecord().as(getResultSet().getString(2));
      getPreviousRecord().jdMethod_for(getResultSet().getLong(3));
    }
  }
  
  private void a(ReportDataItems paramReportDataItems, int paramInt)
  {
    if (paramReportDataItems != null) {
      for (int i = 0; i < paramInt; i++) {
        paramReportDataItems.add().setData("");
      }
    }
  }
  
  protected int addBusinessEntriesToReport(ReportResult paramReportResult, ServiceChargeEntries paramServiceChargeEntries, int paramInt)
    throws RptException
  {
    Locale localLocale = getLocale();
    int i = 0;
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    a(localReportDataItems, paramInt);
    Iterator localIterator = paramServiceChargeEntries.iterator();
    while (localIterator.hasNext())
    {
      if (localReportDataItems == null)
      {
        localReportDataItems = new ReportDataItems(localLocale);
        a(localReportDataItems, paramInt);
      }
      ServiceChargeEntry localServiceChargeEntry = (ServiceChargeEntry)localIterator.next();
      localReportDataItems.add().setData(localServiceChargeEntry.getServiceCode());
      localReportDataItems.add().setData(String.valueOf(localServiceChargeEntry.getNumTransactions()));
      localReportDataItems.add().setData(localServiceChargeEntry.getCurrencyCode());
      localReportDataItems.add().setData(localServiceChargeEntry.getTotalTransactionValue().getCurrencyString());
      ReportRow localReportRow = new ReportRow(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      paramReportResult.addRow(localReportRow);
      i++;
      localReportDataItems = null;
    }
    return i;
  }
  
  public static class a
    extends ServiceChargeEntry
  {
    private String a4U = null;
    private long a4T = 0L;
    
    public a(String paramString, int paramInt)
    {
      super(paramInt);
    }
    
    public long af()
    {
      return this.a4T;
    }
    
    public void jdMethod_for(long paramLong)
    {
      this.a4T = paramLong;
    }
    
    public String ag()
    {
      return this.a4U;
    }
    
    public void as(String paramString)
    {
      this.a4U = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.servicecharge.TransactionTypeSummaryReportHandler
 * JD-Core Version:    0.7.0.1
 */