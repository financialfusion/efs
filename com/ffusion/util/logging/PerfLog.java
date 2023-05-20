package com.ffusion.util.logging;

import com.ffusion.util.MapUtil;
import com.ffusion.util.logging.a.a;
import com.ffusion.util.logging.a.f;
import java.util.HashMap;
import java.util.logging.LogManager;

public class PerfLog
{
  protected static boolean initialized = false;
  protected static boolean enabled = false;
  protected static PerfLogger logger = null;
  protected static PerfHandler handler = null;
  protected static boolean inTuningMode = false;
  protected static final String SETTINGS_KEY = "PERF_LOG_SETTINGS";
  protected static final String PERF_LOG_ENABLED_KEY = "PERF_LOG_ENABLED";
  
  public static void initialize(HashMap paramHashMap)
    throws Exception
  {
    HashMap localHashMap = (HashMap)paramHashMap.get("PERF_LOG_SETTINGS");
    enabled = Boolean.valueOf(MapUtil.getStringValue(localHashMap, "PERF_LOG_ENABLED", "true")).booleanValue();
    logger = new PerfLogger();
    LogManager.getLogManager().addLogger(logger);
    if (logger != null)
    {
      handler = new PerfHandler(paramHashMap);
      logger.addHandler(handler);
      initialized = true;
    }
  }
  
  public static void log(String paramString, long paramLong, boolean paramBoolean)
  {
    if ((initialized) && (enabled)) {
      logger.log(paramString, paramLong, paramBoolean);
    }
  }
  
  public static void flush()
  {
    if ((initialized) && (enabled)) {
      handler.flush();
    }
  }
  
  public static boolean inTuningMode()
  {
    return inTuningMode;
  }
  
  public static void enableTuningMode()
  {
    flush();
    inTuningMode = true;
  }
  
  public static void disableTuningMode()
  {
    inTuningMode = false;
    flush();
  }
  
  public static void clearTuningStats()
  {
    if (initialized) {
      handler.clearTuningStats();
    }
  }
  
  public static void enableLogging()
  {
    enabled = true;
  }
  
  public static void disableLogging()
  {
    enabled = false;
  }
  
  public static boolean isEnabled()
  {
    return enabled == true;
  }
  
  public static String[] getTuningStats()
  {
    if (initialized) {
      return handler.getTuningStats();
    }
    return new String[0];
  }
  
  public a[] getTCPConnections()
  {
    if (initialized) {
      return handler.getTCPConnections();
    }
    return new a[0];
  }
  
  public f getCPU()
  {
    if (initialized) {
      return handler.getCPU();
    }
    return new f("Unavailable", 0, 0, 0, 0);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.PerfLog
 * JD-Core Version:    0.7.0.1
 */