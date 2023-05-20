package com.ffusion.util.filemonitor;

import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.beans.filemonitor.FMLogRecords;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class FMLogger
  extends Logger
{
  public static final String LOGGER_NAME = "FFI_FM_LOGGER";
  
  public FMLogger()
  {
    super("FFI_FM_LOGGER", null);
    setUseParentHandlers(false);
  }
  
  public void log(FMLogRecord paramFMLogRecord)
  {
    super.log(paramFMLogRecord);
  }
  
  public void log(Connection paramConnection, FMLogRecord paramFMLogRecord)
  {
    LinkedList localLinkedList = new LinkedList();
    localLinkedList.add(paramFMLogRecord);
    try
    {
      FMLogAdapter.writeRecords(paramConnection, localLinkedList);
    }
    catch (Exception localException) {}catch (Throwable localThrowable) {}
  }
  
  public void log(Connection paramConnection, FMLogRecords paramFMLogRecords)
  {
    if ((paramFMLogRecords == null) || (paramFMLogRecords.isEmpty())) {
      return;
    }
    LinkedList localLinkedList = new LinkedList(paramFMLogRecords);
    try
    {
      FMLogAdapter.writeRecords(paramConnection, localLinkedList);
    }
    catch (Exception localException) {}catch (Throwable localThrowable) {}
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.filemonitor.FMLogger
 * JD-Core Version:    0.7.0.1
 */