package com.ffusion.util.logging;

import com.ffusion.util.MapUtil;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DebugLog
{
  public static final String LOGGER_NAME = "FinancialFusionDebugLog";
  protected static final String SETTINGS_KEY = "DEBUG_LOG_SETTINGS";
  protected static final String HANDLER_KEY = "HANDLER";
  protected static final String LEVEL_KEY = "LEVEL";
  protected static final String FORMATTER_KEY = "FORMATTER";
  protected static final String USE_PARENT_HANDLERS_KEY = "USE_PARENT_HANDLERS";
  protected static boolean initialized = false;
  protected static boolean partialInitialized = false;
  protected static Logger theLogger = null;
  protected static Level DefaultLevel = Level.INFO;
  
  public static void log(Level paramLevel, String paramString)
  {
    log(paramLevel, paramString, null);
  }
  
  public static void log(String paramString)
  {
    log(DefaultLevel, paramString);
  }
  
  public static void log(String paramString, Object paramObject)
  {
    log(DefaultLevel, paramString, paramObject);
  }
  
  public static void log(Level paramLevel, String paramString, Object paramObject)
  {
    Logger localLogger = getLogger();
    if (localLogger.isLoggable(paramLevel))
    {
      LogRecord localLogRecord = new LogRecord(paramLevel, paramString);
      if (paramObject != null) {
        localLogRecord.setSourceClassName(paramObject.getClass().getName());
      } else {
        localLogRecord.setSourceClassName("");
      }
      localLogger.log(localLogRecord);
    }
  }
  
  public static void throwing(String paramString, Throwable paramThrowable)
  {
    Logger localLogger = getLogger();
    LogRecord localLogRecord = new LogRecord(Level.WARNING, paramString);
    localLogRecord.setSourceClassName(paramString);
    localLogRecord.setSourceMethodName("");
    localLogRecord.setThrown(paramThrowable);
    localLogger.log(localLogRecord);
  }
  
  public static void setLevel(Level paramLevel)
  {
    Logger localLogger = getLogger();
    localLogger.setLevel(paramLevel);
    if (localLogger.getUseParentHandlers() == true) {
      localLogger = localLogger.getParent();
    }
    Handler[] arrayOfHandler = localLogger.getHandlers();
    for (int i = 0; i < arrayOfHandler.length; i++) {
      arrayOfHandler[i].setLevel(paramLevel);
    }
  }
  
  public static Logger getLogger()
  {
    if ((!initialized) && (!partialInitialized)) {
      initialize();
    }
    return theLogger;
  }
  
  public static synchronized void initialize(HashMap paramHashMap)
    throws Exception
  {
    if (initialized) {
      return;
    }
    HashMap localHashMap = (HashMap)paramHashMap.get("DEBUG_LOG_SETTINGS");
    if (localHashMap != null)
    {
      String str1 = MapUtil.getStringValue(localHashMap, "HANDLER", "java.util.logging.ConsoleHandler");
      String str2 = MapUtil.getStringValue(localHashMap, "FORMATTER", "com.ffusion.util.logging.DebugFormatter");
      String str3 = MapUtil.getStringValue(localHashMap, "USE_PARENT_HANDLERS", "false");
      Level localLevel = DefaultLevel;
      try
      {
        localLevel = Level.parse(MapUtil.getStringValue(localHashMap, "LEVEL", "SEVERE"));
      }
      catch (Throwable localThrowable1) {}
      try
      {
        Logger localLogger = Logger.getLogger("FinancialFusionDebugLog");
        if (!str3.equalsIgnoreCase("true")) {
          localLogger.setUseParentHandlers(false);
        }
        localLogger.setLevel(localLevel);
        Class localClass1 = Class.forName(str1);
        Handler localHandler = (Handler)localClass1.newInstance();
        localHandler.setLevel(localLevel);
        Class localClass2 = Class.forName(str2);
        Formatter localFormatter = (Formatter)localClass2.newInstance();
        localHandler.setFormatter(localFormatter);
        localLogger.addHandler(localHandler);
        LogManager.getLogManager().addLogger(localLogger);
        theLogger = localLogger;
        initialized = true;
        partialInitialized = false;
      }
      catch (Throwable localThrowable2)
      {
        System.out.println("DebugLog.initialize: Exception = " + localThrowable2.getMessage());
      }
    }
    else
    {
      initialize();
    }
  }
  
  public static synchronized void initialize()
  {
    Logger localLogger = LogManager.getLogManager().getLogger("FinancialFusionDebugLog");
    if (localLogger == null)
    {
      localLogger = Logger.getLogger("FinancialFusionDebugLog");
      LogManager.getLogManager().addLogger(localLogger);
    }
    theLogger = localLogger;
    partialInitialized = true;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.DebugLog
 * JD-Core Version:    0.7.0.1
 */