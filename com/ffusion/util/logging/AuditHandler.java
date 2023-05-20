package com.ffusion.util.logging;

import com.ffusion.util.MapUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class AuditHandler
  extends Handler
{
  private int jdField_for = 0;
  private LinkedList[] jdField_new = null;
  private a jdField_int;
  private int jdField_byte = 1;
  protected static final String FLUSHTIME_KEY = "FLUSH_TIME";
  protected static final String LOG_DIRECTORY = "LOG_DIRECTORY";
  protected static final String SETTINGS_KEY = "AUDIT_LOG_SETTINGS";
  private static final String jdField_do = "DB_PROPERTIES";
  private static final String a = "LOG_LANGUAGE_LIST";
  private static final String jdField_if = "LOG_LANGUAGE";
  private static AuditAdapter jdField_try = null;
  
  public AuditHandler(HashMap paramHashMap)
    throws Exception
  {
    Properties localProperties = null;
    this.jdField_new = new LinkedList[2];
    this.jdField_new[0] = new LinkedList();
    this.jdField_new[1] = new LinkedList();
    HashMap localHashMap1 = (HashMap)paramHashMap.get("AUDIT_LOG_SETTINGS");
    if (localHashMap1 != null) {
      localProperties = (Properties)localHashMap1.get("DB_PROPERTIES");
    }
    if (localProperties == null) {
      localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
    }
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap2 = (HashMap)paramHashMap.get("LOG_LANGUAGE_LIST");
    if (localHashMap2 != null)
    {
      localObject = (ArrayList)localHashMap2.get("LOG_LANGUAGE");
      if (localObject != null) {
        for (int i = 0; i < ((ArrayList)localObject).size(); i++)
        {
          String str = (String)((ArrayList)localObject).get(i);
          int j = str.indexOf("_");
          if (j == -1) {
            DebugLog.log(Level.WARNING, "Unable to parse the language " + str);
          } else {
            localArrayList.add(new Locale(str.substring(0, j), str.substring(j + 1, str.length())));
          }
        }
      }
    }
    if (localArrayList.size() == 0)
    {
      DebugLog.log(Level.WARNING, "Unable to parse all the log languages. Defaulting to en_US.");
      localArrayList.add(new Locale("en", "US"));
    }
    this.jdField_byte = MapUtil.getIntValue(localHashMap1, "FLUSH_TIME", this.jdField_byte);
    Object localObject = MapUtil.getStringValue(localHashMap1, "LOG_DIRECTORY", "/");
    AuditAdapter.initialize(localProperties, (String)localObject, localArrayList);
    this.jdField_int = new a();
    this.jdField_int.start();
  }
  
  public void publish(LogRecord paramLogRecord)
  {
    try
    {
      if ((paramLogRecord instanceof AuditLogRecord)) {
        this.jdField_new[this.jdField_for].add(paramLogRecord);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("AuditHandler: publish() : ", localThrowable);
    }
  }
  
  public void close()
  {
    flush();
  }
  
  public int getNumberOfRecords()
  {
    return this.jdField_new[this.jdField_for].size();
  }
  
  public synchronized void flush()
  {
    long l = System.currentTimeMillis();
    int i = this.jdField_for;
    int j = i == 0 ? 1 : 0;
    this.jdField_for = j;
    try
    {
      AuditAdapter.writeRecords(this.jdField_new[i]);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.SEVERE, "AuditHandler.flush: found " + this.jdField_new[i].size() + " remaining records that need to be written");
      DebugLog.throwing("AuditHandler:flush() ", localThrowable);
    }
    finally
    {
      PerfLog.log("AuditHandler.flush", l, true);
    }
  }
  
  public class a
    extends Thread
  {
    private boolean a = false;
    
    public a() {}
    
    public boolean jdMethod_if()
    {
      return this.a;
    }
    
    public void run()
    {
      long l = AuditHandler.this.jdField_byte * 60000;
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
      AuditHandler.this.flush();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.AuditHandler
 * JD-Core Version:    0.7.0.1
 */