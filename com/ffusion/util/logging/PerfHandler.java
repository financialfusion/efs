package com.ffusion.util.logging;

import com.ffusion.util.MapUtil;
import com.ffusion.util.Qsort;
import com.ffusion.util.logging.a.a;
import com.ffusion.util.logging.a.c;
import com.ffusion.util.logging.a.f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class PerfHandler
  extends Handler
{
  protected static final String SETTINGS_KEY = "PERF_LOG_SETTINGS";
  protected static final String FLUSHTIME_KEY = "FLUSH_TIME";
  private static final String jdField_if = "DB_PROPERTIES";
  private static final String jdField_for = "SERVERID";
  private int jdField_do = 0;
  private Hashtable[] jdField_new = null;
  private int jdField_try = 30;
  private PerfHandlerThread jdField_int;
  private c a = null;
  
  public PerfHandler(HashMap paramHashMap)
    throws Exception
  {
    Properties localProperties = null;
    this.jdField_new = new Hashtable[2];
    this.jdField_new[0] = new Hashtable();
    this.jdField_new[1] = new Hashtable();
    HashMap localHashMap = (HashMap)paramHashMap.get("PERF_LOG_SETTINGS");
    if (localHashMap != null) {
      localProperties = (Properties)localHashMap.get("DB_PROPERTIES");
    }
    if (localProperties == null) {
      localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
    }
    this.jdField_try = MapUtil.getIntValue(localHashMap, "FLUSH_TIME", this.jdField_try);
    String str = MapUtil.getStringValue(paramHashMap, "SERVERID", "00");
    PerfAdapter.initialize(localProperties, str);
    this.jdField_int = new PerfHandlerThread();
    this.jdField_int.start();
    this.a = new c();
    this.a.a(paramHashMap);
  }
  
  public void publish(LogRecord paramLogRecord)
  {
    try
    {
      if ((paramLogRecord instanceof PerfLogRecord))
      {
        PerfLogRecord localPerfLogRecord1 = (PerfLogRecord)paramLogRecord;
        String str = String.valueOf(localPerfLogRecord1.getHashcode());
        int i = this.jdField_do;
        PerfLogRecord localPerfLogRecord2 = (PerfLogRecord)this.jdField_new[i].get(str);
        if (localPerfLogRecord2 != null) {
          synchronized (localPerfLogRecord2)
          {
            localPerfLogRecord2.add(localPerfLogRecord1);
          }
        }
        this.jdField_new[i].put(str, localPerfLogRecord1);
        if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
          DebugLog.log(Level.FINEST, "Perf: " + localPerfLogRecord1.toString());
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("PerfHandler.publish(): ", localThrowable);
    }
  }
  
  public void close()
  {
    flush();
  }
  
  public synchronized void flush()
  {
    if (!this.jdField_int.flushing()) {
      this.jdField_int.interrupt();
    }
  }
  
  public String[] getTuningStats()
  {
    String str1 = "PerfHandler.getTuningStats";
    String[] arrayOfString = null;
    try
    {
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      i = this.jdField_new[this.jdField_do].size();
      Object localObject = this.jdField_new[this.jdField_do].values().iterator();
      while (((Iterator)localObject).hasNext()) {
        localArrayList.add(((Iterator)localObject).next());
      }
      i = localArrayList.size();
      arrayOfString = new String[i];
      Qsort.sortSortables(localArrayList, "duration", -1);
      localObject = "           ";
      PerfLogRecord localPerfLogRecord = null;
      for (int j = 0; j < i; j++)
      {
        localPerfLogRecord = (PerfLogRecord)localArrayList.get(j);
        String str2 = String.valueOf(localPerfLogRecord.getSuccessCount());
        String str3 = String.valueOf(localPerfLogRecord.getAverageResponseTime());
        String str4 = String.valueOf(localPerfLogRecord.getDuration());
        arrayOfString[j] = (((String)localObject).substring(str2.length()) + str2 + ((String)localObject).substring(str3.length()) + str3 + ((String)localObject).substring(str4.length()) + str4 + " " + localPerfLogRecord.getMessage());
      }
    }
    catch (Throwable localThrowable)
    {
      arrayOfString = new String[1];
      arrayOfString[0] = (str1 + ": " + localThrowable.toString());
    }
    return arrayOfString;
  }
  
  public a[] getTCPConnections()
  {
    a[] arrayOfa = null;
    long l = System.currentTimeMillis();
    if (this.a != null) {
      arrayOfa = this.a.a();
    } else {
      arrayOfa = new a[0];
    }
    PerfLog.log("PerfHandler.getTCPConnections", l, true);
    return arrayOfa;
  }
  
  public f getCPU()
  {
    f localf = null;
    long l = System.currentTimeMillis();
    if (this.a != null) {
      localf = this.a.jdField_if();
    }
    PerfLog.log("PerfHandler.getTCPConnections", l, true);
    return localf;
  }
  
  public void clearTuningStats()
  {
    String str = "PerfHandler.clearTuningStats";
    try
    {
      this.jdField_new[this.jdField_do].clear();
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localThrowable.toString());
    }
  }
  
  public class PerfHandlerThread
    extends Thread
  {
    private boolean a = false;
    
    public PerfHandlerThread() {}
    
    public boolean flushing()
    {
      return this.a;
    }
    
    public void run()
    {
      long l = PerfHandler.this.jdField_try * 60000;
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
    
    private synchronized void a()
    {
      long l = System.currentTimeMillis();
      if (!this.a)
      {
        this.a = true;
        int i = PerfHandler.this.jdField_do;
        int j = i == 0 ? 1 : 0;
        PerfHandler.this.jdField_do = j;
        LinkedList localLinkedList = new LinkedList();
        try
        {
          Iterator localIterator = PerfHandler.this.jdField_new[i].values().iterator();
          while (localIterator.hasNext()) {
            localLinkedList.add(localIterator.next());
          }
          PerfAdapter.writeRecords(localLinkedList);
          PerfHandler.this.jdField_new[i].clear();
        }
        catch (Throwable localThrowable)
        {
          DebugLog.throwing("PerfHandler.flushRecords", localThrowable);
        }
        finally
        {
          localLinkedList = null;
          this.a = false;
          PerfLog.log("PerfHandler.flushRecords", l, true);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.PerfHandler
 * JD-Core Version:    0.7.0.1
 */