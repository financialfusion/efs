package com.ffusion.beans.bcreport;

import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.User;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class CSRActivityRpt
  extends BCRptBase
{
  public CSRActivityRpt(UserActivityRecords paramUserActivityRecords)
  {
    super(paramUserActivityRecords);
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
    String str = (String)localReportCriteria.getSearchCriteria().get("Business");
    boolean bool = str.equals("AllConsumers");
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',', bool);
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t', bool);
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this._detail != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(350, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(356, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(351, this.locale));
      localStringBuffer.append(paramChar);
      if (!paramBoolean)
      {
        localStringBuffer.append(ReportConsts.getColumnName(352, this.locale));
        localStringBuffer.append(paramChar);
      }
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
        localUserActivityRecord.getAuditLogRecord().setDateFormat("h:mm a (z)");
        localStringBuffer.append(localUserActivityRecord.getAuditLogRecord().getTranDate());
        localStringBuffer.append(paramChar);
        BankEmployee localBankEmployee = localUserActivityRecord.getAgent();
        if (localBankEmployee != null) {
          localStringBuffer.append(localBankEmployee.getName());
        }
        localStringBuffer.append(paramChar);
        if (!paramBoolean)
        {
          localStringBuffer.append(localUserActivityRecord.getBusinessName());
          localStringBuffer.append(paramChar);
        }
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
 * Qualified Name:     com.ffusion.beans.bcreport.CSRActivityRpt
 * JD-Core Version:    0.7.0.1
 */