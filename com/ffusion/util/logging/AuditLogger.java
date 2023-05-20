package com.ffusion.util.logging;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.logging.Logger;

public class AuditLogger
  extends Logger
{
  public static final String LOGGER_NAME = "FinancialFusionAuditLogger";
  
  public AuditLogger()
  {
    super("FinancialFusionAuditLogger", null);
    setUseParentHandlers(false);
  }
  
  public void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
  {
    log(paramString1, paramString2, paramString3, new LocalizableString("dummy", paramString4, null), paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12);
  }
  
  public void log(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11)
  {
    if (paramString4 == null) {
      paramString4 = "";
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramString1, 0, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, AuditLogUtil.getModuleFromTranType(paramInt1));
    log(localAuditLogRecord);
  }
  
  public void log(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11)
  {
    if (paramString4 == null) {
      paramString4 = "";
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramString1, paramInt1, paramString2, paramString3, paramILocalizable, paramString4, paramInt2, paramInt3, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, AuditLogUtil.getModuleFromTranType(paramInt2));
    log(localAuditLogRecord);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.AuditLogger
 * JD-Core Version:    0.7.0.1
 */