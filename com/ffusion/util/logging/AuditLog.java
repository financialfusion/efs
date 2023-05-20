package com.ffusion.util.logging;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class AuditLog
{
  private static AuditLogger a = null;
  private static AuditHandler jdField_if = null;
  public static final int NON_CONSUMER_ID = 0;
  
  public static void initialize(HashMap paramHashMap)
    throws Exception
  {
    AuditLogger localAuditLogger = new AuditLogger();
    LogManager.getLogManager().addLogger(localAuditLogger);
    localAuditLogger = (AuditLogger)LogManager.getLogManager().getLogger("FinancialFusionAuditLogger");
    if (localAuditLogger != null)
    {
      jdField_if = new AuditHandler(paramHashMap);
      localAuditLogger.addHandler(jdField_if);
      a = localAuditLogger;
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11)
  {
    if (a == null) {
      DebugLog.log(Level.SEVERE, "AuditLog:Not initialized, must call initialize first.");
    } else {
      a.log(paramString1, 0, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11);
    }
  }
  
  public static void log(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11)
  {
    if (a == null) {
      DebugLog.log(Level.SEVERE, "AuditLog:Not initialized, must call initialize first.");
    } else {
      a.log(paramString1, paramInt1, paramString2, paramString3, paramILocalizable, paramString4, paramInt2, paramInt3, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
  {
    log(paramString1, 0, paramString2, paramString3, new LocalizableString("dummy", paramString4, null), paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12);
  }
  
  public static void log(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
  {
    log(paramString1, paramInt1, paramString2, paramString3, new LocalizableString("dummy", paramString4, null), paramString5, paramInt2, paramInt3, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12);
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7)
  {
    log(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, null, null, null, null, null);
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5, String paramString6)
  {
    log(paramString1, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, paramString6, null, null, null, null, null);
  }
  
  public static void log(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString5, String paramString6)
  {
    log(paramString1, paramInt1, paramString2, paramString3, paramILocalizable, paramString4, paramInt2, paramInt3, paramBigDecimal, paramString5, paramString6, null, null, null, null, null);
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6)
  {
    log(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, null, null, null, null, null, null);
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5)
  {
    log(paramString1, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, null, null, null, null, null, null);
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
  {
    log(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramInt2, null, null, null, null, null, null, null, null);
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2)
  {
    log(paramString1, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, null, null, null, null, null, null, null, null);
  }
  
  public static void log(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3)
  {
    log(paramString1, paramInt1, paramString2, paramString3, paramILocalizable, paramString4, paramInt2, paramInt3, null, null, null, null, null, null, null, null);
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    log(paramString1, null, null, paramString2, paramString3, paramInt, 0, null, null, null, null, null, null, null, null);
  }
  
  public static void log(String paramString1, ILocalizable paramILocalizable, String paramString2, int paramInt)
  {
    log(paramString1, null, null, paramILocalizable, paramString2, paramInt, 0, null, null, null, null, null, null, null, null);
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, int paramInt, BigDecimal paramBigDecimal, String paramString4)
  {
    log(paramString1, null, null, paramString2, paramString3, paramInt, 0, paramBigDecimal, paramString4, null, null, null, null, null, null);
  }
  
  public static void log(String paramString1, ILocalizable paramILocalizable, String paramString2, int paramInt, BigDecimal paramBigDecimal, String paramString3)
  {
    log(paramString1, null, null, paramILocalizable, paramString2, paramInt, 0, paramBigDecimal, paramString3, null, null, null, null, null, null);
  }
  
  public static void log(AuditLogRecord paramAuditLogRecord)
  {
    if (a == null)
    {
      DebugLog.log(Level.SEVERE, "AuditLog: Not initialized, must call initialize first.");
    }
    else
    {
      if (paramAuditLogRecord.getTranID() == null) {
        paramAuditLogRecord.setTranID("");
      }
      a.log(paramAuditLogRecord);
    }
  }
  
  public static void flush()
  {
    if ((a != null) && (jdField_if != null)) {
      jdField_if.flush();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.AuditLog
 * JD-Core Version:    0.7.0.1
 */