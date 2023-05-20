package com.ffusion.util.filemonitor;

import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMFileDescription;
import com.ffusion.util.beans.filemonitor.FMFileDescriptions;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.LogManager;

public class FMLog
{
  private static FMLogger jdField_do;
  private static FMLogHandler a;
  private static FMFileDescriptions jdField_if;
  
  public static void initialize(HashMap paramHashMap)
    throws FMException
  {
    FMLogger localFMLogger = new FMLogger();
    a = new FMLogHandler(paramHashMap);
    localFMLogger.addHandler(a);
    jdField_do = localFMLogger;
    LogManager.getLogManager().addLogger(jdField_do);
  }
  
  public static void setAvailableFileDescriptions(FMFileDescriptions paramFMFileDescriptions)
  {
    jdField_if = paramFMFileDescriptions;
  }
  
  public static FMFileDescriptions getAvailableFileDescriptions()
  {
    return jdField_if;
  }
  
  public static void log(FMLogRecord paramFMLogRecord)
    throws FMException
  {
    if (jdField_do == null)
    {
      FMException localFMException = new FMException("FMLog is not successfully initialized.", 5);
      throw localFMException;
    }
    a(paramFMLogRecord);
    jdField_do.log(paramFMLogRecord);
  }
  
  public static void log(Connection paramConnection, FMLogRecord paramFMLogRecord)
    throws FMException
  {
    if (jdField_do == null)
    {
      FMException localFMException = new FMException("FMLog is not successfully initialized.", 5);
      throw localFMException;
    }
    a(paramFMLogRecord);
    jdField_do.log(paramConnection, paramFMLogRecord);
  }
  
  public static void flush()
    throws FMException
  {
    if (a == null)
    {
      FMException localFMException = new FMException("FMLog is not successfully initialized.", 6);
      throw localFMException;
    }
    a.flush();
  }
  
  public static FMLogHandler getHandler()
  {
    return a;
  }
  
  private static void a(FMLogRecord paramFMLogRecord)
    throws FMException
  {
    if (paramFMLogRecord == null)
    {
      localObject = new FMException("The log record is null", 9);
      throw ((Throwable)localObject);
    }
    if ((jdField_if == null) || (jdField_if.isEmpty()))
    {
      localObject = new FMException("No file description available", 9);
      throw ((Throwable)localObject);
    }
    Object localObject = paramFMLogRecord.getFileType();
    String str1 = paramFMLogRecord.getFromSystem();
    String str2 = paramFMLogRecord.getToSystem();
    for (int i = 0; i < jdField_if.size(); i++)
    {
      FMFileDescription localFMFileDescription = (FMFileDescription)jdField_if.get(i);
      if (localFMFileDescription.getFileType().equals(localObject)) {
        return;
      }
    }
    FMException localFMException = new FMException("Unavailable file type: " + (String)localObject, 9);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.filemonitor.FMLog
 * JD-Core Version:    0.7.0.1
 */