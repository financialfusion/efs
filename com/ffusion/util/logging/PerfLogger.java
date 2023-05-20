package com.ffusion.util.logging;

import java.util.logging.Logger;

public class PerfLogger
  extends Logger
{
  public static final String LOGGER_NAME = "FinancialFusionPerfLogger";
  
  public PerfLogger()
  {
    super("FinancialFusionPerfLogger", null);
    setUseParentHandlers(false);
  }
  
  public void log(String paramString, long paramLong, boolean paramBoolean)
  {
    PerfLogRecord localPerfLogRecord = new PerfLogRecord(paramString, paramLong, paramBoolean);
    log(localPerfLogRecord);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.PerfLogger
 * JD-Core Version:    0.7.0.1
 */