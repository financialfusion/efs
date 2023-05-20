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

public class d
  implements b
{
  protected a jdField_char = null;
  protected int jdField_goto = 120;
  protected int jdField_long = 0;
  protected int jdField_else = 0;
  
  public void a(HashMap paramHashMap)
  {
    try
    {
      this.jdField_goto = MapUtil.getIntValue(paramHashMap, "OS_MONITOR_FREQUENCY_SECONDS", 120);
      if (this.jdField_goto < 30) {
        this.jdField_goto = 30;
      }
      this.jdField_char = new a();
      this.jdField_char.start();
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("SystemMonitorWIN.shutdown: ", localThrowable);
    }
  }
  
  public void jdMethod_do() {}
  
  public f jdMethod_if()
  {
    return null;
  }
  
  public a[] a()
  {
    a[] arrayOfa = null;
    try
    {
      HashMap localHashMap = new HashMap();
      Process localProcess = Runtime.getRuntime().exec("netstat -n -p TCP");
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
          ((StringTokenizer)localObject).nextToken();
          ((StringTokenizer)localObject).nextToken();
          String str2 = ((StringTokenizer)localObject).nextToken();
          int k = str2.indexOf(":");
          str2 = str2.substring(0, k);
          if (!str2.startsWith("127."))
          {
            int m = 0;
            Integer localInteger2 = (Integer)localHashMap.get(str2);
            if (localInteger2 != null) {
              m = localInteger2.intValue();
            }
            localHashMap.put(str2, new Integer(++m));
          }
        }
      }
      int i = localHashMap.size();
      if (i > 0)
      {
        arrayOfa = new a[i];
        localObject = localHashMap.keySet().iterator();
        for (int j = 0; j < i; j++)
        {
          String str3 = (String)((Iterator)localObject).next();
          Integer localInteger1 = (Integer)localHashMap.get(str3);
          arrayOfa[j] = new a(str3, localInteger1.intValue());
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(localThrowable.getMessage(), localThrowable);
    }
    if (arrayOfa == null) {
      this.jdField_else += 1;
    } else {
      this.jdField_else = 0;
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
          sleep(d.this.jdField_goto * 1000);
          a[] arrayOfa = d.this.a();
          if (arrayOfa != null)
          {
            int i = 0;
            for (int j = 0; j < arrayOfa.length; j++) {
              i += arrayOfa[j].a();
            }
            PerfLog.log("TCP Connections: " + i + " connections to " + arrayOfa.length + " hosts", System.currentTimeMillis(), true);
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
 * Qualified Name:     com.ffusion.util.logging.a.d
 * JD-Core Version:    0.7.0.1
 */