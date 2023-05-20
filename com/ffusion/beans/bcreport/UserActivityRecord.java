package com.ffusion.beans.bcreport;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.user.User;

public class UserActivityRecord
  extends ExtendABean
{
  private User a4M;
  private ReportLogRecord a4N;
  private String a4O;
  private BankEmployee a4L;
  
  public UserActivityRecord(User paramUser, ReportLogRecord paramReportLogRecord, String paramString, BankEmployee paramBankEmployee)
  {
    this.a4M = paramUser;
    this.a4N = paramReportLogRecord;
    this.a4O = paramString;
    this.a4L = paramBankEmployee;
  }
  
  public void setUser(User paramUser)
  {
    this.a4M = paramUser;
  }
  
  public User getUser()
  {
    return this.a4M;
  }
  
  public void setAuditLogRecord(ReportLogRecord paramReportLogRecord)
  {
    this.a4N = paramReportLogRecord;
  }
  
  public ReportLogRecord getAuditLogRecord()
  {
    return this.a4N;
  }
  
  public void setBusinessName(String paramString)
  {
    this.a4O = paramString;
  }
  
  public String getBusinessName()
  {
    return this.a4O;
  }
  
  public void setAgent(BankEmployee paramBankEmployee)
  {
    this.a4L = paramBankEmployee;
  }
  
  public BankEmployee getAgent()
  {
    return this.a4L;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.UserActivityRecord
 * JD-Core Version:    0.7.0.1
 */