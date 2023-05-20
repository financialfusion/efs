package com.ffusion.beans.bcreport;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.user.User;
import com.ffusion.reporting.IReportResult;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

class BCRptBase
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  protected UserActivityRecords _detail;
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  
  public BCRptBase(UserActivityRecords paramUserActivityRecords)
  {
    this._detail = paramUserActivityRecords;
  }
  
  public UserActivityRecords getUserActivityRecords()
  {
    return this._detail;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this._detail != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(350, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(351, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(352, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(353, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(354, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(355, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this._detail.iterator();
      while (localIterator.hasNext())
      {
        UserActivityRecord localUserActivityRecord = (UserActivityRecord)localIterator.next();
        localStringBuffer.append(localUserActivityRecord.getAuditLogRecord().getTranDate());
        localStringBuffer.append(paramChar);
        BankEmployee localBankEmployee = localUserActivityRecord.getAgent();
        if (localBankEmployee != null) {
          localStringBuffer.append(localBankEmployee.getName());
        }
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localUserActivityRecord.getBusinessName());
        localStringBuffer.append(paramChar);
        User localUser = localUserActivityRecord.getUser();
        if (localUser != null) {
          localStringBuffer.append(localUser.getName());
        }
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localUserActivityRecord.getAuditLogRecord().getTranTypeText());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localUserActivityRecord.getAuditLogRecord().getMessage());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.BCRptBase
 * JD-Core Version:    0.7.0.1
 */