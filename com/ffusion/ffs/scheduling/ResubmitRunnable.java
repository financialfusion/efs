package com.ffusion.ffs.scheduling;

import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.lang.reflect.Method;

public class ResubmitRunnable
  implements Runnable
{
  private FFSConnectionHolder jdField_try = new FFSConnectionHolder();
  private String jdField_do;
  private String jdField_char;
  private String jdField_int;
  private String jdField_void;
  private String jdField_byte;
  private String jdField_null;
  private boolean jdField_if = false;
  private String jdField_goto;
  private Class jdField_long;
  private Method jdField_new;
  private PropertyConfig a;
  private int jdField_for = 1;
  private boolean jdField_else = false;
  boolean jdField_case;
  
  public ResubmitRunnable(FFSConnection paramFFSConnection, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    this.jdField_try.conn = paramFFSConnection;
    this.jdField_do = paramString1;
    this.jdField_char = paramString2;
    this.jdField_int = null;
    this.jdField_byte = paramString3;
    this.jdField_null = paramString4;
    this.jdField_if = paramBoolean;
    this.a = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.jdField_for = this.a.LogLevel;
  }
  
  public ResubmitRunnable(FFSConnection paramFFSConnection, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.jdField_try.conn = paramFFSConnection;
    this.jdField_do = paramString1;
    this.jdField_char = paramString2;
    this.jdField_int = paramString3;
    this.jdField_void = paramString4;
    this.a = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.jdField_for = this.a.LogLevel;
  }
  
  public ResubmitRunnable(FFSConnection paramFFSConnection, String paramString1, String paramString2, String paramString3)
  {
    this.jdField_try.conn = paramFFSConnection;
    this.jdField_do = paramString1;
    this.jdField_char = paramString2;
    this.jdField_int = paramString3;
    this.a = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.jdField_for = this.a.LogLevel;
  }
  
  public synchronized void run()
  {
    try
    {
      InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(this.jdField_do, this.jdField_char);
      if (localInstructionType == null) {
        throw new FFSException("The instruction type " + this.jdField_char + " cannot be found with registry.");
      }
      this.jdField_case = localInstructionType.createEmptyFile;
      this.jdField_goto = localInstructionType.HandlerClassName;
      this.jdField_long = Class.forName(this.jdField_goto);
      this.jdField_new = this.jdField_long.getMethod("resubmitEventHandler", new Class[] { Integer.TYPE, EventInfoArray.class, FFSConnectionHolder.class });
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** EventResubmitRunnable.run:");
      FFSDebug.console("*** EventResubmitRunnable.run failed:" + localException1.toString());
      return;
    }
    try
    {
      a();
    }
    catch (Exception localException2)
    {
      FFSDebug.log(localException2, "*** EventRunnable.run failed:");
      FFSDebug.console("*** EventRunnable.run failed:" + localException2.toString());
    }
  }
  
  private synchronized void a()
  {
    try
    {
      FFSDebug.log("ResubmitRunnable: Resubmit of schedule for " + this.jdField_char + " (FI Id=" + this.jdField_do + ")" + " started.");
      Object localObject1 = this.jdField_long.newInstance();
      localObject2 = new EventInfoArray();
      ((EventInfoArray)localObject2)._array = new EventInfo[1];
      ((EventInfoArray)localObject2)._array[0] = new EventInfo();
      localObject2._array[0].ScheduleFlag = -1;
      localObject2._array[0].InstructionID = this.jdField_int;
      localObject2._array[0].FIId = this.jdField_do;
      localObject2._array[0].InstructionType = this.jdField_char;
      localObject2._array[0].cutOffId = this.jdField_byte;
      localObject2._array[0].processId = this.jdField_null;
      localObject2._array[0].createEmptyFile = this.jdField_case;
      this.jdField_new.invoke(localObject1, new Object[] { new Integer(0), localObject2, this.jdField_try });
      EventInfo[] arrayOfEventInfo = null;
      if ((this.jdField_int != null) && (this.jdField_int.length() != 0))
      {
        if ((this.jdField_void != null) && (this.jdField_void.length() > 0)) {
          arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(this.jdField_try, this.jdField_char, this.jdField_int, this.jdField_void, 0);
        } else {
          arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(this.jdField_try, 0, this.jdField_do, this.jdField_char, this.jdField_int);
        }
        if (arrayOfEventInfo != null)
        {
          EventInfoArray localEventInfoArray = new EventInfoArray();
          while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
          {
            localEventInfoArray._array = arrayOfEventInfo;
            this.jdField_new.invoke(localObject1, new Object[] { new Integer(3), localObject2, this.jdField_try });
            this.jdField_new.invoke(localObject1, new Object[] { new Integer(1), localEventInfoArray, this.jdField_try });
            this.jdField_new.invoke(localObject1, new Object[] { new Integer(4), localObject2, this.jdField_try });
            if (EventInfoLog.isBatchDone(this.jdField_do, this.jdField_char)) {
              arrayOfEventInfo = new EventInfo[0];
            } else if ((this.jdField_void != null) && (this.jdField_void.length() > 0)) {
              arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(this.jdField_try, this.jdField_char, this.jdField_int, this.jdField_void, 0);
            } else {
              arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(this.jdField_try, 0, this.jdField_do, this.jdField_char, this.jdField_int);
            }
          }
        }
      }
      this.jdField_new.invoke(localObject1, new Object[] { new Integer(2), localObject2, this.jdField_try });
      this.jdField_try.conn.commit();
      FFSDebug.log("ResubmitRunnable: Resubmit of schedule for " + this.jdField_char + " (FI Id=" + this.jdField_do + ")" + " done.");
    }
    catch (Exception localException)
    {
      FFSDebug.log("ResubmitRunnable: Resubmit of schedule for " + this.jdField_char + " (FI Id=" + this.jdField_do + ")" + " failed!");
      Object localObject2 = FFSDebug.stackTrace(localException);
      FFSDebug.log("*** ResubmitRunnable.execute: failed:" + this.jdField_char + " (FI Id=" + this.jdField_do + "): " + (String)localObject2);
      FFSDebug.console("*** ResubmitRunnable.execute: failed:" + this.jdField_char + " (FI Id=" + this.jdField_do + "): " + (String)localObject2);
    }
  }
  
  public synchronized void threadDone()
  {
    this.jdField_else = true;
    notifyAll();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.ResubmitRunnable
 * JD-Core Version:    0.7.0.1
 */