package com.ffusion.util.logging.a;

import com.ffusion.util.MapUtil;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.logging.Level;

public class c
  implements b
{
  protected b jdField_case = null;
  
  public void a(HashMap paramHashMap)
  {
    this.jdField_case = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("PERF_LOG_SETTINGS");
      String str1 = null;
      String str2 = null;
      if ((localHashMap != null) && (MapUtil.getStringValue(localHashMap, "ENABLE_OS_MONITORING", "false").equalsIgnoreCase("true")))
      {
        str1 = MapUtil.getStringValue(localHashMap, "CLASS", null);
        if ((str1 == null) || (str1.length() < 2))
        {
          str2 = MapUtil.getStringValue(localHashMap, "OS", null);
          if (str2 == null)
          {
            str2 = System.getProperty("os.name");
            if (str2 != null)
            {
              str2 = str2.toLowerCase();
              if (str2.indexOf("windows") != -1) {
                str2 = "WINDOWS";
              } else if (str2.indexOf("aix") != -1) {
                str2 = "AIX";
              } else if (str2.indexOf("linux") != -1) {
                str2 = "LINUX";
              }
            }
          }
          if (((str2 != null) && (str2.equalsIgnoreCase("AIX"))) || (str2.equalsIgnoreCase("LINUX"))) {
            str1 = "com.ffusion.util.logging.system.SystemMonitorAIX";
          } else if ((str2 != null) && (str2.equalsIgnoreCase("WINDOWS"))) {
            str1 = "com.ffusion.util.logging.system.SystemMonitorWin";
          }
        }
        if (str1 == null)
        {
          DebugLog.log(Level.WARNING, "SystemMonitor.initialize: Unsupported system monitor configuration.  Please specify <PERF_LOG_SETTINGS><CLASSNAME>aclassname</CLASSNAME>... in csil.xml. Or disable system monitoring by adding <DISABLE>true</DISABLE>");
        }
        else
        {
          DebugLog.log(Level.INFO, "SystemMonitor.initialize: Starting OS monitor " + str1);
          Class localClass = Class.forName(str1);
          Object localObject = localClass.newInstance();
          this.jdField_case = ((b)localObject);
          this.jdField_case.a(localHashMap);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      this.jdField_case = null;
      DebugLog.throwing("SystemMonitor.initialize: ", localThrowable);
      jdMethod_do();
    }
  }
  
  public void jdMethod_do()
  {
    try
    {
      if (this.jdField_case != null) {
        this.jdField_case.jdMethod_do();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("SystemMonitor.shutdown: ", localThrowable);
    }
    finally
    {
      this.jdField_case = null;
    }
  }
  
  public a[] a()
  {
    if (this.jdField_case != null) {
      return this.jdField_case.a();
    }
    return null;
  }
  
  public f jdMethod_if()
  {
    if (this.jdField_case != null) {
      return this.jdField_case.jdMethod_if();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.a.c
 * JD-Core Version:    0.7.0.1
 */