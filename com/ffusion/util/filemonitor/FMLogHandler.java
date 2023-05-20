package com.ffusion.util.filemonitor;

import com.ffusion.util.MapUtil;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class FMLogHandler
  extends Handler
{
  public static final String SETTINGS_KEY = "FILE_MONITOR_LOG_SETTINGS";
  public static final String BUFFERSIZE_KEY = "BUFFER_SIZE";
  public static final String FLUSHTIME_KEY = "FLUSH_INTERVAL";
  public static final String LOG_DIRECTORY = "LOG_DIRECTORY";
  public static final String DB_PROPERTIES = "DB_PROPERTIES";
  public static final int DEFAULT_BUFFER_SIZE = 255;
  public static final int DEFAULT_FLUSH_INTERVAL = 30;
  public static final String DEFAULT_LOG_DIR = "/usr/filemonitor/log/";
  private LinkedList jdField_if;
  private int jdField_for = 255;
  private FMLogHandlerThread a;
  private int jdField_do = 30;
  
  public FMLogHandler(HashMap paramHashMap)
    throws FMException
  {
    HashMap localHashMap = (HashMap)paramHashMap.get("FILE_MONITOR_LOG_SETTINGS");
    if (localHashMap == null) {
      throw new FMException("No file monitor handler settings found. Please check the file monitor configuration XML", 7);
    }
    this.jdField_for = MapUtil.getIntValue(localHashMap, "BUFFER_SIZE", 255);
    this.jdField_do = MapUtil.getIntValue(localHashMap, "FLUSH_INTERVAL", this.jdField_do);
    if (this.jdField_do < 0)
    {
      localObject = new FMException("Flush interval can not be a negative number.", 7);
      throw ((Throwable)localObject);
    }
    Object localObject = MapUtil.getStringValue(localHashMap, "LOG_DIRECTORY", "/usr/filemonitor/log/");
    Properties localProperties = (Properties)localHashMap.get("DB_PROPERTIES");
    if (localProperties == null)
    {
      DebugLog.log(Level.INFO, "No database properties defined for file monitor log. Use global database settings by default...");
      localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
    }
    if (localProperties == null) {
      throw new FMException("No database configuration properties found.", 8);
    }
    FMLogAdapter.initialize(localProperties, (String)localObject);
    this.jdField_if = new LinkedList();
    this.a = new FMLogHandlerThread();
    this.a.start();
  }
  
  public void publish(LogRecord paramLogRecord)
  {
    if ((paramLogRecord instanceof FMLogRecord))
    {
      synchronized (this.jdField_if)
      {
        this.jdField_if.add(paramLogRecord);
      }
      if (this.jdField_if.size() >= this.jdField_for) {
        flush();
      }
    }
  }
  
  public void close()
  {
    flush();
  }
  
  public void flush()
  {
    synchronized (this.a)
    {
      try
      {
        LinkedList localLinkedList = null;
        synchronized (this.jdField_if)
        {
          localLinkedList = new LinkedList(this.jdField_if);
          this.jdField_if.clear();
        }
        FMLogAdapter.writeRecords(localLinkedList);
      }
      catch (Throwable localThrowable) {}
    }
  }
  
  public class FMLogHandlerThread
    extends Thread
  {
    public FMLogHandlerThread() {}
    
    public void run()
    {
      long l = FMLogHandler.this.jdField_do * 60 * 1000;
      for (;;)
      {
        try
        {
          sleep(l);
        }
        catch (InterruptedException localInterruptedException) {}catch (Throwable localThrowable) {}
        a();
      }
    }
    
    private void a()
    {
      FMLogHandler.this.flush();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.filemonitor.FMLogHandler
 * JD-Core Version:    0.7.0.1
 */