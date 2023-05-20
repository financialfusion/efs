package com.ffusion.approvals;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLable;
import com.ffusion.util.logging.AuditLogRecord;

public abstract interface IApprovable
  extends XMLable
{
  public abstract int getApprovalType();
  
  public abstract int getApprovalSubType();
  
  public abstract String getApprovalSubTypeName();
  
  public abstract Currency getApprovalAmount();
  
  public abstract DateTime getApprovalDueDate();
  
  public abstract AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2);
  
  public abstract AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.IApprovable
 * JD-Core Version:    0.7.0.1
 */