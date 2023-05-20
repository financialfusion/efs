package com.ffusion.efs.adapters.profile.servicecharge;

import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.efs.adapters.profile.ChargeableReportHandler;
import com.ffusion.efs.adapters.profile.MasterReportData;
import com.ffusion.efs.adapters.profile.ServiceChargeReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.util.db.DBUtil;
import java.util.HashMap;
import java.util.Locale;

public abstract class ReportProcessingHandler
  extends BaseReportServiceCharge
{
  private ChargeableReportHandler jdField_else = null;
  private ChargeableReportHandler[] jdField_char = null;
  
  public ReportProcessingHandler(ChargeableReportHandler[] paramArrayOfChargeableReportHandler, Locale paramLocale)
  {
    super(paramLocale);
    setHandlers(paramArrayOfChargeableReportHandler);
  }
  
  protected String formatAllTranTypes(HashMap paramHashMap)
  {
    ChargeableReportHandler localChargeableReportHandler = null;
    String str = null;
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    for (int i = 0; i < getHandlers().length; i++)
    {
      localChargeableReportHandler = getHandlers()[i];
      str = getTransTypesDelimited(getReportCriteria().getChargeableOperationsAsArray(), localChargeableReportHandler, paramHashMap, "");
      localStringBuffer1.append(str);
      localStringBuffer1.append((str == null) || (str.trim().length() == 0) ? "" : ",");
    }
    if (localStringBuffer1 != null)
    {
      localStringBuffer1.deleteCharAt(localStringBuffer1.length() - 1);
      localStringBuffer2.append("AND ");
      localStringBuffer2.append(DBUtil.generateSQLNumericInClause(localStringBuffer1.toString(), "a.TRAN_TYPE"));
    }
    return localStringBuffer2.toString();
  }
  
  public ReportProcessingHandler(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public abstract ReportResult createMainSubReport(ReportResult paramReportResult, MasterReportData paramMasterReportData, Locale paramLocale)
    throws RptException;
  
  public ChargeableReportHandler getChargeableReportHandler()
  {
    return this.jdField_else;
  }
  
  protected void setChargeableReportHandler(ChargeableReportHandler paramChargeableReportHandler)
  {
    this.jdField_else = paramChargeableReportHandler;
  }
  
  public ChargeableReportHandler[] getHandlers()
  {
    return this.jdField_char;
  }
  
  public void setHandlers(ChargeableReportHandler[] paramArrayOfChargeableReportHandler)
  {
    this.jdField_char = paramArrayOfChargeableReportHandler;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.servicecharge.ReportProcessingHandler
 * JD-Core Version:    0.7.0.1
 */