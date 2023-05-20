package com.ffusion.util.logging.a;

import com.ffusion.util.MapUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class e
  implements b
{
  protected int c = 120;
  protected b jdField_null = null;
  protected a jdField_void = null;
  protected int d = 0;
  protected int b = 0;
  
  public void a(HashMap paramHashMap)
  {
    try
    {
      this.c = MapUtil.getIntValue(paramHashMap, "OS_MONITOR_FREQUENCY_SECONDS", 120);
      if (this.c < 30) {
        this.c = 30;
      }
      DebugLog.log(Level.INFO, "SystemMonitorAIX: Frequency=" + this.c);
      this.jdField_void = new a();
      this.jdField_void.start();
      this.jdField_null = new b();
      this.jdField_null.start();
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("SystemMonitorAIX.shutdown: ", localThrowable);
    }
  }
  
  public void jdMethod_do() {}
  
  public f jdMethod_if()
  {
    f localf = null;
    try
    {
      Process localProcess = Runtime.getRuntime().exec("vmstat 1 2");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      String str = null;
      int i = 0;
      for (;;)
      {
        str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        if (DebugLog.getLogger().isLoggable(Level.FINE)) {
          DebugLog.log(Level.FINE, "[" + str + "]");
        }
        StringTokenizer localStringTokenizer = new StringTokenizer(str, " ");
        if ((a(localStringTokenizer.nextToken())) && (a(localStringTokenizer.nextToken())))
        {
          i++;
          if (i >= 2)
          {
            for (int j = 0; j < 11; j++) {
              localStringTokenizer.nextToken();
            }
            localf = new f(str, Integer.parseInt(localStringTokenizer.nextToken()), Integer.parseInt(localStringTokenizer.nextToken()), Integer.parseInt(localStringTokenizer.nextToken()), Integer.parseInt(localStringTokenizer.nextToken()));
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(localThrowable.getMessage(), localThrowable);
    }
    if (localf == null) {
      this.d += 1;
    } else {
      this.d = 0;
    }
    return localf;
  }
  
  protected boolean a(String paramString)
  {
    try
    {
      Integer.parseInt(paramString);
      return true;
    }
    catch (Throwable localThrowable) {}
    return false;
  }
  
  public a[] a()
  {
    a[] arrayOfa = null;
    try
    {
      HashMap localHashMap = new HashMap();
      Process localProcess = Runtime.getRuntime().exec("netstat -an");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      Object localObject;
      for (;;)
      {
        String str1 = localBufferedReader.readLine();
        if (str1 == null) {
          break;
        }
        if (str1.indexOf("ESTABLISHED") != -1)
        {
          localObject = new StringTokenizer(str1);
          for (int j = 0; j < 3; j++) {
            ((StringTokenizer)localObject).nextToken();
          }
          String str2 = ((StringTokenizer)localObject).nextToken();
          int m = str2.lastIndexOf(".");
          str2 = str2.substring(0, m);
          if (!str2.startsWith("127."))
          {
            int n = 0;
            Integer localInteger2 = (Integer)localHashMap.get(str2);
            if (localInteger2 != null) {
              n = localInteger2.intValue();
            }
            localHashMap.put(str2, new Integer(++n));
          }
        }
      }
      int i = localHashMap.size();
      if (i > 0)
      {
        arrayOfa = new a[i];
        localObject = localHashMap.keySet().iterator();
        for (int k = 0; k < i; k++)
        {
          String str3 = (String)((Iterator)localObject).next();
          Integer localInteger1 = (Integer)localHashMap.get(str3);
          arrayOfa[k] = new a(str3, localInteger1.intValue());
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(localThrowable.getMessage(), localThrowable);
    }
    if (arrayOfa == null) {
      this.b += 1;
    } else {
      this.b = 0;
    }
    return arrayOfa;
  }
  
  public class a
    extends Thread
  {
    public boolean jdField_if = false;
    public boolean a = false;
    
    public a() {}
    
    public void run()
    {
      while (!this.jdField_if) {
        try
        {
          sleep(e.this.c * 1000);
          a[] arrayOfa = e.this.a();
          if (arrayOfa != null)
          {
            int i = 0;
            for (int j = 0; j < arrayOfa.length; j++) {
              i += arrayOfa[j].a();
            }
            PerfLog.log("TCP (" + System.currentTimeMillis() + "): " + i + " to " + arrayOfa.length + " hosts", System.currentTimeMillis(), true);
          }
        }
        catch (Throwable localThrowable)
        {
          DebugLog.throwing(localThrowable.getMessage(), localThrowable);
        }
      }
      this.a = true;
    }
  }
  
  public class b
    extends Thread
  {
    public boolean jdField_if = false;
    public boolean a = false;
    
    public b() {}
    
    public void run()
    {
      while (!this.jdField_if) {
        try
        {
          sleep(e.this.c * 1000);
          if (e.this.d < 10)
          {
            f localf = e.this.jdField_if();
            if (localf != null) {
              PerfLog.log("CPU " + localf.jdMethod_new() + ": " + localf.jdMethod_do() + " usr," + localf.jdMethod_int() + " sys," + localf.jdField_if() + " wait," + localf.a() + " idle", System.currentTimeMillis(), true);
            }
          }
        }
        catch (Throwable localThrowable)
        {
          DebugLog.throwing(localThrowable.getMessage(), localThrowable);
        }
      }
      this.a = true;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.a.e
 * JD-Core Version:    0.7.0.1
 */