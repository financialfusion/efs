package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.enums.UserType;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;

public class BPWAuditLogRecord
  extends AuditLogRecord
{
  protected UserType jdField_if;
  
  public BPWAuditLogRecord()
  {
    super(null, 0, null, null, null, null, 0, 0, new BigDecimal(0.0D), null, null, null, null, null, null, null, 0);
  }
  
  public BPWAuditLogRecord(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt4, UserType paramUserType)
  {
    super(paramString1, paramInt1, paramString2, paramString3, paramILocalizable, paramString4, paramInt2, paramInt3, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramInt4);
    this.jdField_if = paramUserType;
  }
  
  public BPWAuditLogRecord(AuditLogRecord paramAuditLogRecord, UserType paramUserType)
  {
    super(paramAuditLogRecord.getUserID(), paramAuditLogRecord.getPrimaryUserID(), paramAuditLogRecord.getAgentID(), paramAuditLogRecord.getAgentType(), paramAuditLogRecord.getLocalizableMessage(), paramAuditLogRecord.getTranID(), paramAuditLogRecord.getTranType(), paramAuditLogRecord.getBusinessID(), paramAuditLogRecord.getAmountValue(), paramAuditLogRecord.getCurrencyCode(), paramAuditLogRecord.getSrvrTid(), paramAuditLogRecord.getState(), paramAuditLogRecord.getToAcctID(), paramAuditLogRecord.getToAcctRtgNum(), paramAuditLogRecord.getFromAcctID(), paramAuditLogRecord.getFromAcctRtgNum(), paramAuditLogRecord.getModule());
    this.jdField_if = paramUserType;
  }
  
  public void setUserType(UserType paramUserType)
  {
    this.jdField_if = paramUserType;
  }
  
  public UserType getUserType()
  {
    return this.jdField_if;
  }
  
  public String recordToString()
  {
    return super.toString() + "userType=" + this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.BPWAuditLogRecord
 * JD-Core Version:    0.7.0.1
 */