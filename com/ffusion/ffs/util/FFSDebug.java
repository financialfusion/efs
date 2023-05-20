package com.ffusion.ffs.util;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.util.MapUtil;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class FFSDebug
  implements FFSConst
{
  private static final long jdField_void = 1123475334865133929L;
  private static int c = 0;
  private static boolean m = true;
  private static PrintWriter l = new PrintWriter(System.out, true);
  private static final String p = "FFSLOGDIR";
  private static final String k = "FFSLOGFILE";
  private static final String j = "FFusionLogArchive";
  private static final String h = "OVERWRITE";
  private static final String jdField_case = "APPEND";
  private static final String t = "ARCHIVEDATE";
  private static final String jdField_null = "FFSLOGHANDLERTYPE";
  private static final String d = "File";
  private static final String jdField_char = "Console";
  private static final String g = "FFSLOGHANDLERFORMATTER";
  public static final String LOGGER_NAME = "FinancialFusionBPWDebugLog";
  protected static final String o = "DEBUG_LOG_SETTINGS";
  protected static final String e = "HANDLER";
  protected static final String s = "LEVEL";
  protected static final String jdField_byte = "FORMATTER";
  protected static final String n = "USE_PARENT_HANDLERS";
  protected static final String f = "FILE_PATTERN";
  protected static final String jdField_else = "FILECOUNT";
  protected static final String i = "FILEMAXBYTES";
  protected static final String r = "FILE_APPEND";
  protected static boolean jdField_long = false;
  protected static boolean b = false;
  protected static Logger jdField_goto = null;
  protected static Level q = Level.INFO;
  
  public static void log(Level paramLevel, String paramString)
  {
    log(paramLevel, paramString, null);
  }
  
  public static void log(String paramString, Object paramObject)
  {
    log(q, paramString, paramObject);
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
    for (int i1 = 0; i1 < arrayOfHandler.length; i1++) {
      arrayOfHandler[i1].setLevel(paramLevel);
    }
  }
  
  public static Logger getLogger()
  {
    if ((!jdField_long) && (!b)) {
      initialize();
    }
    return jdField_goto;
  }
  
  public static synchronized void initialize(HashMap paramHashMap)
    throws Exception
  {
    if (jdField_long) {
      return;
    }
    HashMap localHashMap = (HashMap)paramHashMap.get("DEBUG_LOG_SETTINGS");
    if (localHashMap != null)
    {
      String str1 = MapUtil.getStringValue(localHashMap, "HANDLER", "java.util.logging.ConsoleHandler");
      String str2 = MapUtil.getStringValue(localHashMap, "FORMATTER", "com.ffusion.ffs.util.DebugFormatter");
      String str3 = MapUtil.getStringValue(localHashMap, "USE_PARENT_HANDLERS", "false");
      Level localLevel = q;
      try
      {
        localLevel = Level.parse(MapUtil.getStringValue(localHashMap, "LEVEL", "SEVERE"));
      }
      catch (Throwable localThrowable1) {}
      try
      {
        Logger localLogger = Logger.getLogger("FinancialFusionBPWDebugLog");
        if (!"true".equalsIgnoreCase(str3)) {
          localLogger.setUseParentHandlers(false);
        }
        localLogger.setLevel(localLevel);
        Class localClass1 = Class.forName(str1);
        Object localObject1 = null;
        if ("java.util.logging.FileHandler".equals(str1))
        {
          String str4 = MapUtil.getStringValue(localHashMap, "FILE_PATTERN", "FFusion.log");
          localObject2 = MapUtil.getStringValue(localHashMap, "FILECOUNT", "1");
          String str5 = MapUtil.getStringValue(localHashMap, "FILEMAXBYTES", "0");
          String str6 = MapUtil.getStringValue(localHashMap, "FILE_APPEND", "false");
          localObject1 = new FileHandler(str4, Integer.valueOf(str5).intValue(), Integer.valueOf((String)localObject2).intValue(), Boolean.valueOf(str6).booleanValue());
        }
        else
        {
          try
          {
            localObject1 = (Handler)localClass1.newInstance();
          }
          catch (Throwable localThrowable3) {}
        }
        ((Handler)localObject1).setLevel(localLevel);
        Class localClass2 = Class.forName(str2);
        Object localObject2 = (Formatter)localClass2.newInstance();
        ((Handler)localObject1).setFormatter((Formatter)localObject2);
        localLogger.addHandler((Handler)localObject1);
        LogManager.getLogManager().addLogger(localLogger);
        jdField_goto = localLogger;
        jdField_long = true;
        b = false;
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
    Logger localLogger = LogManager.getLogManager().getLogger("FinancialFusionBPWDebugLog");
    if (localLogger == null)
    {
      localLogger = Logger.getLogger("FinancialFusionBPWDebugLog");
      LogManager.getLogManager().addLogger(localLogger);
    }
    jdField_goto = localLogger;
    b = true;
  }
  
  public static Level convertBPWLogLevel(int paramInt)
  {
    if (paramInt == -1) {
      return Level.OFF;
    }
    if (paramInt == 0) {
      return Level.SEVERE;
    }
    if (paramInt == 1) {
      return Level.WARNING;
    }
    if (paramInt == 2) {
      return Level.INFO;
    }
    if (paramInt == 3) {
      return Level.CONFIG;
    }
    if (paramInt == 4) {
      return Level.FINE;
    }
    if (paramInt == 5) {
      return Level.FINER;
    }
    if (paramInt == 6) {
      return Level.FINEST;
    }
    return Level.ALL;
  }
  
  private static String a()
  {
    Object localObject = null;
    String str1 = System.getProperty("file.separator");
    String str2 = System.getProperty("FFSLOGDIR");
    String str3 = System.getProperty("FFSLOGFILE", "FFusion.log");
    if ((str2 == null) || (str2.length() == 0)) {
      localObject = str3;
    } else {
      localObject = str2 + str1 + str3;
    }
    return localObject;
  }
  
  public static void register(int paramInt)
    throws FFSException
  {
    c = paramInt;
    setLevel(convertBPWLogLevel(paramInt));
  }
  
  public static void log(String paramString)
  {
    if (m) {
      log(Level.OFF, paramString);
    } else {
      console(paramString);
    }
  }
  
  public static void console(String paramString)
  {
    Date localDate = new Date();
    l.println(localDate + ": " + paramString);
  }
  
  public static void log(Throwable paramThrowable, String paramString)
  {
    String str = null;
    if ((paramThrowable instanceof SQLException)) {
      str = "**** " + paramString + ": " + paramThrowable.toString();
    } else if ((paramThrowable instanceof FFSException)) {
      str = "---- FFSException " + paramString + ": " + paramThrowable.toString();
    } else {
      str = "#### " + paramString + ": " + paramThrowable.toString();
    }
    if (m) {
      throwing(str, paramThrowable);
    }
  }
  
  public static void console(Throwable paramThrowable, String paramString)
  {
    String str = null;
    if ((paramThrowable instanceof SQLException)) {
      str = "**** " + paramString + ": " + paramThrowable.toString();
    } else if ((paramThrowable instanceof FFSException)) {
      str = "---- FFSException " + paramString + ": " + paramThrowable.toString();
    } else {
      str = "#### " + paramString + ": " + paramThrowable.toString();
    }
    Date localDate = new Date();
    l.println(localDate + ": " + str);
    paramThrowable.printStackTrace(l);
  }
  
  public static boolean deregister()
  {
    return true;
  }
  
  public static boolean setDebugLevel(int paramInt)
  {
    if (m)
    {
      c = paramInt;
      setLevel(convertBPWLogLevel(paramInt));
      return true;
    }
    return false;
  }
  
  public static boolean checkLogLevel(int paramInt)
  {
    return paramInt <= c;
  }
  
  public static void log(String paramString, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString);
    }
  }
  
  public static void log(String paramString1, String paramString2, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8 + paramString9);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8 + paramString9 + paramString10);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8 + paramString9 + paramString10 + paramString11);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8 + paramString9 + paramString10 + paramString11 + paramString12);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8 + paramString9 + paramString10 + paramString11 + paramString12 + paramString13);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8 + paramString9 + paramString10 + paramString11 + paramString12 + paramString13 + paramString14);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8 + paramString9 + paramString10 + paramString11 + paramString12 + paramString13 + paramString14 + paramString15);
    }
  }
  
  public static void log(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, int paramInt)
  {
    if (paramInt <= c) {
      log(convertBPWLogLevel(paramInt), paramString1 + paramString2 + paramString3 + paramString4 + paramString5 + paramString6 + paramString7 + paramString8 + paramString9 + paramString10 + paramString11 + paramString12 + paramString13 + paramString14 + paramString15 + paramString16);
    }
  }
  
  public static void log(Throwable paramThrowable, String paramString, int paramInt)
  {
    if (paramInt <= c) {
      throwing(paramString, paramThrowable);
    }
  }
  
  public static void console(Throwable paramThrowable, String paramString, int paramInt)
  {
    if (paramInt <= c) {
      console(paramThrowable, paramString);
    }
  }
  
  public static String stackTrace(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    return localStringWriter.toString();
  }
  
  static
  {
    Object localObject1 = a();
    try
    {
      HashMap localHashMap = new HashMap();
      localObject2 = new HashMap();
      localHashMap.put("DEBUG_LOG_SETTINGS", localObject2);
      q = Level.SEVERE;
      str1 = System.getProperty("FFSLOGHANDLERTYPE", "File");
      if ("File".equals(str1)) {
        ((HashMap)localObject2).put("HANDLER", "java.util.logging.FileHandler");
      } else if ("Console".equals(str1)) {
        ((HashMap)localObject2).put("HANDLER", "java.util.logging.ConsoleHandler");
      } else {
        ((HashMap)localObject2).put("HANDLER", "java.util.logging.FileHandler");
      }
      String str2 = System.getProperty("FFSLOGHANDLERFORMATTER", "com.ffusion.ffs.util.DebugFormatter");
      ((HashMap)localObject2).put("FORMATTER", str2);
      ((HashMap)localObject2).put("LEVEL", "SEVERE");
      String str3 = System.getProperty("FILEMAXBYTES");
      String str4 = System.getProperty("FILECOUNT");
      Object localObject3 = localObject1;
      if ((str3 != null) || (str4 != null))
      {
        int i1 = ((String)localObject1).lastIndexOf('.');
        if (i1 != -1)
        {
          localObject3 = ((String)localObject1).substring(0, i1) + "%g" + ((String)localObject1).substring(i1);
          localObject1 = localObject3;
        }
      }
      ((HashMap)localObject2).put("FILE_PATTERN", localObject3);
      if (str3 == null) {
        str3 = "0";
      }
      if (str4 == null) {
        str4 = "1";
      }
      ((HashMap)localObject2).put("FILEMAXBYTES", str3);
      ((HashMap)localObject2).put("FILECOUNT", str4);
      String str5 = System.getProperty("FFusionLogArchive", "OVERWRITE");
      int i2 = 0;
      if (str5.equalsIgnoreCase("APPEND") == true) {
        i2 = 1;
      } else if (str5.equalsIgnoreCase("ARCHIVEDATE") != true) {}
      ((HashMap)localObject2).put("FILE_APPEND", i2 != 0 ? "true" : "false");
      initialize(localHashMap);
      m = true;
      Date localDate = new Date();
      l.println(localDate + ": " + "This application log file is: " + (String)localObject1);
    }
    catch (Exception localException)
    {
      Object localObject2 = new Date();
      String str1 = "Failed to register log file: " + (String)localObject1 + "\nERROR: " + localException.getMessage() + " " + localException.toString();
      l.println(localObject2 + ": " + str1);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSDebug
 * JD-Core Version:    0.7.0.1
 */