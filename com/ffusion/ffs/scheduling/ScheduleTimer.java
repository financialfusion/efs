package com.ffusion.ffs.scheduling;

import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.bpw.interfaces.ScheduleStatus;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.scheduling.db.ScheduleHistory;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleTimer
  implements Runnable
{
  public static final int REPEAT_MODE_ONE_TIME = 100;
  public static final int REPEAT_MODE_REPEAT = 101;
  public static final int MINUTES_PER_DAY = 1440;
  private PropertyConfig c;
  private Scheduler jdField_try;
  private String b;
  private String jdField_long;
  private int jdField_for;
  private int jdField_new;
  private long jdField_case;
  private Calendar e;
  private Object d;
  public Object _processingFlagLock;
  public volatile boolean _processingFlag;
  private int jdField_do;
  private int jdField_byte;
  private int jdField_int;
  private int jdField_null;
  private volatile boolean jdField_goto;
  private String jdField_void;
  private CutOffInfo a;
  private boolean jdField_if;
  private boolean jdField_else;
  private String jdField_char;
  
  public ScheduleTimer(String paramString1, String paramString2, Calendar paramCalendar, int paramInt1, int paramInt2, Scheduler paramScheduler, String paramString3, boolean paramBoolean1, CutOffInfo paramCutOffInfo, boolean paramBoolean2)
  {
    this.a = paramCutOffInfo;
    this.jdField_if = paramBoolean1;
    this.jdField_else = paramBoolean2;
    this.c = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.jdField_try = paramScheduler;
    this.b = paramString1;
    this.jdField_long = paramString2;
    this.jdField_for = paramInt2;
    String str1 = BPWRegistryUtil.getProperty("scheduler.runtoken.blockmode", "Wait");
    InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(this.b, this.jdField_long);
    this.jdField_char = localInstructionType.category;
    this.jdField_case = 0L;
    if (str1.equalsIgnoreCase("NoWait") == true)
    {
      this.jdField_new = 100;
    }
    else if (str1.equalsIgnoreCase("TimedWait") == true)
    {
      this.jdField_new = 101;
      String str2 = BPWRegistryUtil.getProperty("scheduler.runtoken.waittime", "60000");
      try
      {
        this.jdField_case = Long.parseLong(str2.trim());
      }
      catch (NumberFormatException localNumberFormatException) {}
      if (this.jdField_case <= 0L) {
        this.jdField_case = Long.parseLong("60000");
      }
    }
    else
    {
      if (!str1.equalsIgnoreCase("Wait")) {
        FFSDebug.log("Invalid value for configuration property 'scheduler.runtoken.blockmode'. Using default value of 'Wait'.", 1);
      }
      this.jdField_new = 102;
    }
    this.e = paramCalendar;
    this.d = new Object();
    this.jdField_do = paramInt1;
    if (!this.jdField_if) {
      if (this.jdField_do < 1440)
      {
        this.jdField_int = (1440 / this.jdField_do);
        this.jdField_byte = (1440 % this.jdField_do);
        if (this.jdField_byte == 0) {
          this.jdField_byte = this.jdField_do;
        }
      }
      else
      {
        this.jdField_int = 0;
        this.jdField_byte = this.jdField_do;
      }
    }
    this.jdField_null = 0;
    this._processingFlagLock = new Object();
    this._processingFlag = false;
    this.jdField_goto = true;
    this.jdField_void = paramString3;
  }
  
  public void run()
  {
    FFSDebug.log("--- ScheduleTimer.run: begin: " + this.jdField_long + " (FI Id=" + this.b + ")", 6);
    Object localObject1 = null;
    Object localObject2 = null;
    ScheduleHist localScheduleHist;
    if (this.jdField_void.equals("Internal"))
    {
      localScheduleHist = new ScheduleHist();
      localScheduleHist.ScheduleName = this.jdField_long;
      localScheduleHist.InstructionType = this.jdField_long;
      localScheduleHist.FIID = this.b;
      localScheduleHist.ServerName = this.c.serverName;
      localScheduleHist.EventType = "ScheduleThreadStart";
      localScheduleHist.EventTrigger = this.jdField_void;
      try
      {
        ScheduleHistory.createScheduleHist(localScheduleHist);
      }
      catch (Exception localException1)
      {
        String str1 = FFSDebug.stackTrace(localException1);
        FFSDebug.log("*** ScheduleTimer.run: Log history exception:" + str1, 6);
      }
    }
    int i = 1;
    int j = 0;
    String str2;
    while ((this.jdField_goto == true) && (!Thread.interrupted()))
    {
      try
      {
        i = 1;
        j = 0;
        if (this.jdField_for == 101)
        {
          long l = a();
          if (l < 0L) {
            l = 0L;
          }
          Calendar localCalendar = Calendar.getInstance();
          FFSDebug.log("ScheduleTimer:" + this.jdField_long + " (FI Id=" + this.b + "): " + "Now=" + localCalendar.getTime().toString(), 6);
          FFSDebug.log("ScheduleTimer:" + this.jdField_long + " (FI Id=" + this.b + "): " + "NextRun=" + this.e.getTime().toString() + ", Sleep ms=" + l, 6);
          if (Thread.interrupted())
          {
            this.jdField_goto = false;
            break;
          }
          while ((this.jdField_goto == true) && (l > 0L))
          {
            synchronized (this.d)
            {
              this.d.wait(l);
            }
            l = a();
            FFSDebug.log("ScheduleTimer:" + this.jdField_long + " (FI Id=" + this.b + "): " + "Remaining Sleep Time ms= " + l);
          }
          if (this.jdField_goto == true)
          {
            ??? = SmartCalendar.getTodayDate();
            Object localObject4 = SmartCalendar.getPayday(this.b, ???, this.jdField_char);
            if (localObject4 != ???) {
              if (this.jdField_else == true)
              {
                if (SmartCalendar.isTodayWeekEnd() == true) {
                  FFSDebug.log("ScheduleTimer:" + this.jdField_long + " (FI Id=" + this.b + "): " + "ProcessOnHoliday flag is on, process on weekends. (today=" + ??? + ",payday=" + localObject4 + ")", 6);
                } else {
                  FFSDebug.log("ScheduleTimer:" + this.jdField_long + " (FI Id=" + this.b + "): " + "ProcessOnHoliday flag is on, process on non-business day (today=" + ??? + ",payday=" + localObject4 + ")", 6);
                }
              }
              else
              {
                FFSDebug.log("ScheduleTimer:" + this.jdField_long + " (FI Id=" + this.b + "): " + "Skip non-business day (today=" + ??? + ",payday=" + localObject4 + ")", 6);
                i = 0;
              }
            }
          }
        }
        if ((this.jdField_goto == true) && (i == 1))
        {
          synchronized (this._processingFlagLock)
          {
            if (this.jdField_goto == true) {
              this._processingFlag = true;
            }
          }
          if (this.jdField_goto == true)
          {
            j = 1;
            ??? = this.jdField_try.runBatchProcess(this);
            if (((ScheduleStatus)???).runStatus != 0)
            {
              str2 = "*** ScheduleTimer.run: failed:" + ((ScheduleStatus)???).errorMsg;
              FFSDebug.console(str2);
              FFSDebug.log(str2);
            }
          }
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        this.jdField_goto = false;
      }
      catch (Throwable localThrowable)
      {
        str2 = "*** ScheduleTimer.run: failed:" + FFSDebug.stackTrace(localThrowable);
        FFSDebug.console(str2);
        FFSDebug.log(str2);
        localScheduleHist = new ScheduleHist();
        localScheduleHist.ScheduleName = this.jdField_long;
        localScheduleHist.InstructionType = this.jdField_long;
        localScheduleHist.FIID = this.b;
        localScheduleHist.ServerName = this.c.serverName;
        localScheduleHist.EventType = "Exception";
        localScheduleHist.EventTrigger = this.jdField_void;
        localScheduleHist.EventDescription = localThrowable.getMessage();
        try
        {
          ScheduleHistory.createScheduleHist(localScheduleHist);
        }
        catch (Exception localException4)
        {
          ??? = FFSDebug.stackTrace(localException4);
          FFSDebug.log("*** ScheduleTimer.run: Log history exception:" + (String)???, 6);
        }
      }
      this._processingFlag = false;
      if (this.jdField_for == 100)
      {
        this.jdField_goto = false;
      }
      else if (!this.jdField_if)
      {
        this.jdField_null += 1;
        if (this.jdField_null > this.jdField_int)
        {
          this.e.add(12, this.jdField_byte);
          this.jdField_null = 0;
        }
        else
        {
          this.e.add(12, this.jdField_do);
        }
      }
      else if (j == 1)
      {
        try
        {
          if (this.a.getExtension() != null)
          {
            this.a.setExtension(null);
            this.a = this.jdField_try.removeCutOffExtension(this.a);
            if ((this.a == null) || (this.a.getStatusCode() != 0)) {
              FFSDebug.log("*** ScheduleTimer.run: Log cutOff Extension modification failed", 0);
            }
          }
          this.a = this.jdField_try.setLastProcessTime(this.a);
          this.a = this.jdField_try.getNextCutOffTime(this.b, this.jdField_long);
          if (this.a.getStatusCode() != 0)
          {
            FFSDebug.log("*** ScheduleTimer.run: Log cutOffInfo not found ", 0);
            return;
          }
          SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
          localSimpleDateFormat.parse(this.a.getNextProcessTime());
          this.e = localSimpleDateFormat.getCalendar();
        }
        catch (Exception localException2)
        {
          str2 = FFSDebug.stackTrace(localException2);
          FFSDebug.log("*** ScheduleTimer.run: Log cutOff exception:" + str2, 0);
          return;
        }
      }
    }
    if (this.jdField_void.equals("Internal"))
    {
      localScheduleHist = new ScheduleHist();
      localScheduleHist.ScheduleName = this.jdField_long;
      localScheduleHist.InstructionType = this.jdField_long;
      localScheduleHist.FIID = this.b;
      localScheduleHist.ServerName = this.c.serverName;
      localScheduleHist.EventType = "ScheduleThreadStop";
      localScheduleHist.EventTrigger = this.jdField_void;
      try
      {
        ScheduleHistory.createScheduleHist(localScheduleHist);
      }
      catch (Exception localException3)
      {
        str2 = FFSDebug.stackTrace(localException3);
        FFSDebug.log("*** ScheduleTimer.run: Log history exception:" + str2, 6);
      }
    }
    if (this.jdField_for == 100) {
      this.jdField_try.a(this);
    }
    FFSDebug.log("--- ScheduleTimer.run: end: " + this.jdField_long + " (FI Id=" + this.b + ")", 6);
  }
  
  public CutOffInfo getCutOffInfo()
  {
    return this.a;
  }
  
  public boolean getUseCutOffs()
  {
    return this.jdField_if;
  }
  
  public String getFIId()
  {
    return this.b;
  }
  
  public String getInstructionType()
  {
    return this.jdField_long;
  }
  
  public boolean getKeepRunning()
  {
    return this.jdField_goto;
  }
  
  public int getRunTokenTimeoutMode()
  {
    return this.jdField_new;
  }
  
  public long getRunTokenTimeout()
  {
    return this.jdField_case;
  }
  
  public void stop()
  {
    synchronized (this.d)
    {
      this.jdField_goto = false;
      this.d.notifyAll();
    }
  }
  
  private long a()
  {
    long l1 = System.currentTimeMillis();
    long l2 = Calendar.getInstance().get(16);
    long l3 = this.e.get(16);
    long l4 = 0L;
    if ((l2 == 0L) && (l3 != 0L)) {
      l4 = this.e.getTime().getTime() - l1 - l3 / 60000L;
    } else if ((l2 != 0L) && (l3 == 0L)) {
      l4 = this.e.getTime().getTime() - l1 + l2 / 60000L;
    } else {
      l4 = this.e.getTime().getTime() - l1;
    }
    return l4;
  }
  
  public Calendar getNextRunTime()
  {
    return this.e;
  }
  
  public String getEventTrigger()
  {
    return this.jdField_void;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.ScheduleTimer
 * JD-Core Version:    0.7.0.1
 */