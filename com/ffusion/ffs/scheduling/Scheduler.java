package com.ffusion.ffs.scheduling;

import com.ffusion.ffs.bpw.db.DBInstructionType;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleActivityList;
import com.ffusion.ffs.bpw.interfaces.ScheduleCalloutInfo;
import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.bpw.interfaces.ScheduleStatus;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.InstructionTypeStatus;
import com.ffusion.ffs.scheduling.db.ScheduleHistory;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.scheduling.overlapMonitor.OverlapMonitor;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import java.io.PrintStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class Scheduler
  implements FFSConst
{
  private static final int jdField_case = 1;
  private PropertyConfig b = null;
  private FFSConnectionHolder jdField_byte = new FFSConnectionHolder();
  private String jdField_goto = null;
  private boolean jdField_void = false;
  private boolean jdField_if = false;
  public boolean _keeprunning = true;
  private Hashtable f;
  private Hashtable g;
  private Hashtable jdField_int;
  private Vector jdField_long;
  private Vector jdField_null;
  private String e;
  private String c;
  private String jdField_char;
  private String jdField_for;
  private String a;
  private String jdField_new;
  private String d;
  private String jdField_do;
  private String jdField_else;
  private boolean jdField_try = false;
  
  public Scheduler()
  {
    FFSDebug.log("Scheduler: Default constructor", 2);
    this.b = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
  }
  
  public synchronized void init()
    throws Exception
  {
    FFSDebug.log("Scheduler: init", 2);
    this.b = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.jdField_if = false;
    this.jdField_void = false;
    a();
    try
    {
      this.jdField_goto = DBUtil.getNextIndexString("SchLockID");
      this.jdField_byte.conn = DBUtil.getSingleConnection();
      this.jdField_byte.conn.setAutoCommit(false);
      String str1 = this.jdField_byte.conn.getDatabaseType();
      if ((str1.startsWith("ORA")) || (str1.startsWith("Ora"))) {
        this.jdField_byte.conn.setTransactionIsolation(8);
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = "Failed to get connection to check scheduler lock table. Scheduler will not start.\nError: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public synchronized boolean start(FFSConnection paramFFSConnection)
    throws Exception
  {
    if (this.jdField_if == true)
    {
      FFSDebug.log("Scheduler.start: This scheduler is currently running!", 0);
      FFSDebug.log("Scheduler.start: Start failed... ", 0);
      throw new Exception("This scheduler is currently running!");
    }
    if (!this.jdField_void)
    {
      FFSDebug.log("Scheduler.start: You must own the lock to the scheduler statring scheduler!", 0);
      FFSDebug.log("Scheduler.start: Start failed... ", 0);
      throw new Exception("You must own the lock to the scheduler before statring scheduler!");
    }
    FFSDebug.log("Scheduler.start: begin", 6);
    FFSDebug.log("Scheduler.start: hostname=" + this.e, 2);
    FFSDebug.log("Scheduler.start: ip=" + this.c, 2);
    OverlapMonitor.reset();
    NamePlace.setValue("HostName", this.e);
    NamePlace.setValue("IpAddress", this.c);
    NamePlace.setValue("EJBProtocol", this.jdField_char);
    NamePlace.setValue("EJBHost", this.jdField_for);
    NamePlace.setValue("EJBPort", this.a);
    NamePlace.setValue("EJBUserName", this.jdField_new);
    NamePlace.setValue("EJBPassword", this.d);
    NamePlace.setValue("EJBJNDIName", this.jdField_do);
    NamePlace.setValue("EJBNameContext", this.jdField_else);
    ScheduleHist localScheduleHist = new ScheduleHist();
    localScheduleHist.ScheduleName = null;
    localScheduleHist.InstructionType = null;
    localScheduleHist.FIID = null;
    localScheduleHist.ServerName = this.b.serverName;
    localScheduleHist.EventType = "SchedulerStart";
    localScheduleHist.EventTrigger = "Internal";
    localScheduleHist.EventDescription = (this.e + "( " + this.c + " ); " + this.jdField_for + ":" + this.a);
    try
    {
      ScheduleHistory.createScheduleHist(localScheduleHist);
    }
    catch (Exception localException1)
    {
      str = FFSDebug.stackTrace(localException1);
      FFSDebug.log("*** Scheduler.start: Log history exception:" + str, 6);
    }
    a(paramFFSConnection);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = paramFFSConnection;
    ScheduleInfo.setFIIDinStaticScheduleInfo(localFFSConnectionHolder);
    ScheduleInfo.recoverModifyingSchedule(localFFSConnectionHolder);
    String str = this.b.otherProperties.getProperty("scheduler.workinstancedate.refresh", "true");
    boolean bool = new Boolean(str).booleanValue();
    if (bool == true) {
      FFSDebug.log("Scheduler: Configured to re-calculate Work Instance dates.");
    }
    this.f = new Hashtable();
    this.g = new Hashtable();
    this.jdField_int = new Hashtable();
    this.jdField_long = new Vector();
    this.jdField_null = new Vector();
    InstructionType[] arrayOfInstructionType = getInstructionTypes();
    if (arrayOfInstructionType != null) {
      for (int i = 0; i < arrayOfInstructionType.length; i++) {
        try
        {
          FFSDebug.log("Scheduler:InstructionType.Scheduler[" + i + "] " + arrayOfInstructionType[i].FIId + "," + arrayOfInstructionType[i].InstructionType + "," + arrayOfInstructionType[i].HandlerClassName + "," + arrayOfInstructionType[i].SchedulerStartTime + "," + arrayOfInstructionType[i].SchedulerInterval + "," + arrayOfInstructionType[i].ActiveFlag, 6);
          InstructionTypeStatus.initSchedulerStatus(localFFSConnectionHolder, arrayOfInstructionType[i].FIId, arrayOfInstructionType[i].InstructionType);
          localFFSConnectionHolder.conn.commit();
          a(arrayOfInstructionType[i].FIId, arrayOfInstructionType[i].InstructionType, bool);
          a(localFFSConnectionHolder, arrayOfInstructionType[i]);
        }
        catch (Exception localException2)
        {
          FFSDebug.log(localException2, "Scheduler: Error initiating process for " + arrayOfInstructionType[i].InstructionType + " (FI Id=" + arrayOfInstructionType[i].FIId + ")." + FFSConst.LINE_SEPARATOR + "The schedule for " + arrayOfInstructionType[i].InstructionType + " (FI Id=" + arrayOfInstructionType[i].FIId + ")" + " will not run." + FFSConst.LINE_SEPARATOR + "Error msg: ");
        }
      }
    }
    this.jdField_if = true;
    FFSDebug.log("Scheduler.start: # of schedule threads: " + this.g.size(), 6);
    FFSDebug.log("Scheduler.start: done.", 6);
    return this.jdField_if;
  }
  
  private void a(String paramString1, String paramString2, boolean paramBoolean)
    throws FFSException
  {
    try
    {
      ScheduleCalloutInfo localScheduleCalloutInfo = getScheduleCalloutInfo(paramString1, paramString2);
      ScheduleRunnable localScheduleRunnable = new ScheduleRunnable(paramString1, paramString2, localScheduleCalloutInfo, paramBoolean);
      String str = jdField_do(paramString2, paramString1);
      this.f.put(str, localScheduleRunnable);
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, InstructionType paramInstructionType)
    throws FFSException
  {
    if ((paramInstructionType.ActiveFlag == 1) && (paramInstructionType.SchedulerStartTime.compareTo("") != 0))
    {
      Calendar localCalendar = null;
      CutOffInfo localCutOffInfo = null;
      try
      {
        if (!paramInstructionType.useCutOffs)
        {
          localCalendar = a(paramInstructionType.SchedulerStartTime, paramInstructionType.SchedulerInterval);
        }
        else
        {
          localCutOffInfo = getNextCutOffTimeTX(paramFFSConnectionHolder, paramInstructionType);
          if (localCutOffInfo.getStatusCode() == 26005)
          {
            FFSDebug.log("Warning: No CutOff exists.Scheduler: " + paramInstructionType.InstructionType + " (FI Id=" + paramInstructionType.FIId + ")" + " will not run." + " Please add CutOff information.");
            return;
          }
          if (localCutOffInfo.getStatusCode() != 0)
          {
            localObject = "Unable to process cutOff for this schedule";
            FFSDebug.log((String)localObject, 6);
            throw new FFSException((String)localObject);
          }
          Object localObject = null;
          localObject = new SimpleDateFormat("yyyy/MM/dd HH:mm");
          ((SimpleDateFormat)localObject).parse(localCutOffInfo.getNextProcessTime());
          localCalendar = ((SimpleDateFormat)localObject).getCalendar();
        }
      }
      catch (Exception localException)
      {
        throw new FFSException(localException.getMessage());
      }
      ScheduleTimer localScheduleTimer = new ScheduleTimer(paramInstructionType.FIId, paramInstructionType.InstructionType, localCalendar, paramInstructionType.SchedulerInterval, 101, this, "Internal", paramInstructionType.useCutOffs, localCutOffInfo, paramInstructionType.processOnHolidays);
      String str = jdField_do(paramInstructionType.InstructionType, paramInstructionType.FIId);
      this.jdField_int.put(str, localScheduleTimer);
      Thread localThread = new Thread(localScheduleTimer);
      this.g.put(str, localThread);
      localThread.start();
      FFSDebug.log("Scheduler: " + paramInstructionType.InstructionType + " (FI Id=" + paramInstructionType.FIId + ")" + " scheduled for " + localScheduleTimer.getNextRunTime().getTime());
    }
  }
  
  private void jdField_for(String paramString1, String paramString2)
  {
    String str = jdField_do(paramString2, paramString1);
    this.f.remove(str);
  }
  
  private void jdField_int(String paramString1, String paramString2)
    throws FFSException
  {
    try
    {
      String str = jdField_do(paramString2, paramString1);
      ScheduleTimer localScheduleTimer = (ScheduleTimer)this.jdField_int.get(str);
      if (localScheduleTimer != null) {
        localScheduleTimer.stop();
      }
      Thread localThread = (Thread)this.g.get(str);
      if (localThread != null)
      {
        localThread.interrupt();
        localThread.join();
      }
      localScheduleTimer = (ScheduleTimer)this.jdField_int.remove(str);
      localThread = (Thread)this.g.remove(str);
    }
    catch (Exception localException)
    {
      throw new FFSException("Failed to remove system schedule timer for " + paramString2 + " (FI Id=" + paramString1 + ")" + ": " + localException.getMessage());
    }
  }
  
  public boolean ownsLock()
  {
    return this.jdField_void;
  }
  
  public void setLock(boolean paramBoolean)
  {
    this.jdField_void = paramBoolean;
  }
  
  public void flipLock()
    throws Exception
  {
    if (!ownsLock())
    {
      FFSDebug.log("Scheduler.flipLock: This scheduler does not owns the lock!", 6);
      return;
    }
    if (this.jdField_byte.conn == null)
    {
      FFSDebug.log("Scheduler.flipLock: _schLockConn.conn == null!", 6);
      return;
    }
    String str = this.jdField_byte.conn.getDatabaseType();
    if ((str.startsWith("DB2")) || (str.indexOf("ASE") != -1)) {
      Locker.flipLockTrans(this.jdField_byte, this.jdField_goto);
    }
  }
  
  public boolean checkSchedulerLock()
    throws Exception
  {
    try
    {
      if ((this.jdField_byte.conn == null) || (this.jdField_byte.conn.isClosed()))
      {
        this.jdField_byte.conn = DBUtil.getSingleConnection();
        this.jdField_byte.conn.setAutoCommit(false);
      }
    }
    catch (Throwable localThrowable)
    {
      String str = "Failed to get database connection to the scheduler lock table. Unable to check scheduler lock.\nError: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
    this.jdField_void = Locker.checkSchedulerLock(this.jdField_byte, this.jdField_goto);
    if (this.jdField_void) {
      FFSDebug.log("Scheduler.checkSchedulerLock: The Scheduler lock is owned by this server.", 2);
    } else {
      FFSDebug.log("Scheduler.checkSchedulerLock: The Scheduler lock is owned by another server.", 2);
    }
    return this.jdField_void;
  }
  
  public boolean getSchedulerLock()
    throws Exception
  {
    if (checkSchedulerLock() == true) {
      return false;
    }
    try
    {
      if ((this.jdField_byte.conn == null) || (this.jdField_byte.conn.isClosed()))
      {
        this.jdField_byte.conn = DBUtil.getSingleConnection();
        this.jdField_byte.conn.setAutoCommit(false);
      }
    }
    catch (Throwable localThrowable1)
    {
      String str = "Failed to get database connection to the Scheduler lock table. The Scheduler will not start.\nError: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      this.jdField_void = Locker.getSchedulerLock(this.jdField_byte, this.jdField_goto);
      if (this.jdField_void) {
        FFSDebug.log("Scheduler.getSchedulerLock: The Scheduler lock was obtained. ", "The Scheduler will start on this server.", 2);
      } else {
        FFSDebug.log("Scheduler.getSchedulerLock: The Scheduler lock is owned by another process. ", "The Scheduler will not start on this server.", 2);
      }
    }
    catch (Throwable localThrowable2)
    {
      FFSDebug.log("Failed to obtain the Scheduler lock. It may be owned ", "by another server. The Scheduler will not start. " + FFSDebug.stackTrace(localThrowable2), 3);
    }
    return this.jdField_void;
  }
  
  public synchronized boolean schIsRunning()
  {
    return this.jdField_if;
  }
  
  public synchronized boolean stop()
    throws Exception
  {
    FFSDebug.log("Scheduler.stop: begin", 6);
    if (!this.jdField_if) {
      FFSDebug.log("Scheduler.stop: This scheduler is currently not running or is shutting down.", 0);
    }
    this.jdField_if = false;
    Enumeration localEnumeration;
    Object localObject;
    if (this.jdField_int != null)
    {
      localEnumeration = this.jdField_int.elements();
      while (localEnumeration.hasMoreElements() == true)
      {
        localObject = (ScheduleTimer)localEnumeration.nextElement();
        FFSDebug.log("Stopping schedule " + ((ScheduleTimer)localObject).getInstructionType() + "(FI Id=" + ((ScheduleTimer)localObject).getFIId() + ")", 6);
        ((ScheduleTimer)localObject).stop();
      }
      this.jdField_int.clear();
      FFSDebug.log("Scheduler.stop: Stopped ScheduleTimer instances.", 6);
    }
    if (this.jdField_long != null)
    {
      localEnumeration = this.jdField_long.elements();
      while (localEnumeration.hasMoreElements() == true)
      {
        localObject = (ScheduleTimer)localEnumeration.nextElement();
        FFSDebug.log("Stopping one-time schedule " + ((ScheduleTimer)localObject).getInstructionType() + "(FI Id=" + ((ScheduleTimer)localObject).getFIId() + ")", 6);
        ((ScheduleTimer)localObject).stop();
      }
      this.jdField_long.clear();
      FFSDebug.log("Scheduler.stop: Stopped one-time ScheduleTimer instances.", 6);
    }
    if (this.g != null)
    {
      localEnumeration = this.g.elements();
      while (localEnumeration.hasMoreElements() == true)
      {
        localObject = (Thread)localEnumeration.nextElement();
        if (localObject != null)
        {
          FFSDebug.log("Interrupting processing thread " + ((Thread)localObject).getName(), 6);
          try
          {
            ((Thread)localObject).interrupt();
          }
          catch (Exception localException2) {}
        }
      }
      FFSDebug.log("Scheduler.stop: Interrupted ScheduleTimer threads.", 6);
    }
    if (this.jdField_null != null)
    {
      localEnumeration = this.jdField_null.elements();
      while (localEnumeration.hasMoreElements() == true)
      {
        localObject = (Thread)localEnumeration.nextElement();
        if (localObject != null)
        {
          FFSDebug.log("Interrupting one-time processing thread " + ((Thread)localObject).getName(), 6);
          try
          {
            ((Thread)localObject).interrupt();
          }
          catch (Exception localException3) {}
        }
      }
      FFSDebug.log("Scheduler.stop: Interrupted one-time ScheduleTimer threads.", 6);
    }
    if (this.g != null)
    {
      localEnumeration = this.g.elements();
      while (localEnumeration.hasMoreElements() == true)
      {
        localObject = (Thread)localEnumeration.nextElement();
        if (localObject != null)
        {
          FFSDebug.log("Joining processing thread " + ((Thread)localObject).getName(), 6);
          try
          {
            ((Thread)localObject).join();
            FFSDebug.log("Thread.join completed", 6);
          }
          catch (Exception localException4)
          {
            if (((Thread)localObject).isAlive()) {
              FFSDebug.log("Unable to shut down thread " + ((Thread)localObject).getName(), 6);
            }
          }
        }
      }
      this.g.clear();
      FFSDebug.log("Scheduler.stop: Removed ScheduleTimer threads.", 6);
    }
    if (this.jdField_null != null)
    {
      localEnumeration = this.jdField_null.elements();
      while (localEnumeration.hasMoreElements() == true)
      {
        localObject = (Thread)localEnumeration.nextElement();
        if (localObject != null)
        {
          FFSDebug.log("Joining one-time processing thread " + ((Thread)localObject).getName(), 6);
          try
          {
            ((Thread)localObject).join();
            FFSDebug.log("Thread.join completed", 6);
          }
          catch (Exception localException5)
          {
            if (((Thread)localObject).isAlive()) {
              FFSDebug.log("Unable to shut down thread " + ((Thread)localObject).getName(), 6);
            }
          }
        }
      }
      this.g.clear();
      FFSDebug.log("Scheduler.stop: Removed one-time ScheduleTimer threads.", 6);
    }
    ScheduleInfo.clearSchedCache();
    try
    {
      if (this.jdField_byte != null) {
        if (this.jdField_void)
        {
          FFSDebug.log("Scheduler.stop: release the locker", 6);
          this.jdField_byte.conn.rollback();
          DBUtil.freeSingleConnection(this.jdField_byte.conn);
          FFSDebug.log("Scheduler.stop: locker rolled back successfully", 6);
          this.jdField_void = false;
        }
        else
        {
          FFSDebug.log("Scheduler.stop: This scheduler does not own the lock!", 0);
          DBUtil.freeSingleConnection(this.jdField_byte.conn);
          this.jdField_byte.conn = null;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("Scheduler.stop: Failed to release scheduler lock. Error: " + FFSDebug.stackTrace(localThrowable), 0);
    }
    ScheduleHist localScheduleHist = new ScheduleHist();
    localScheduleHist.ScheduleName = null;
    localScheduleHist.InstructionType = null;
    localScheduleHist.FIID = null;
    localScheduleHist.ServerName = this.b.serverName;
    localScheduleHist.EventType = "SchedulerStop";
    localScheduleHist.EventTrigger = "Internal";
    localScheduleHist.EventDescription = (this.e + "( " + this.c + " ); " + this.jdField_for + ":" + this.a);
    try
    {
      FFSDebug.log("Schedule.stop: Creating schedule history", 6);
      ScheduleHistory.createScheduleHist(localScheduleHist);
      FFSDebug.log("Schedule.stop: Schedule histor created", 6);
    }
    catch (Exception localException1)
    {
      String str = FFSDebug.stackTrace(localException1);
      FFSDebug.log("*** Scheduler.stop: Log history exception:" + str, 6);
    }
    FFSDebug.log("Schedule.stop returns " + (!this.jdField_if), 6);
    return !this.jdField_if;
  }
  
  public void setBatchSize(int paramInt)
  {
    if (paramInt <= 0)
    {
      EventInfo.BATCH_SIZE = 1000;
      com.ffusion.ffs.scheduling.db.EventInfoLog.BATCH_SIZE = 1000;
      ScheduleInfo.BATCH_SIZE = 1000;
    }
    else
    {
      EventInfo.BATCH_SIZE = paramInt;
      com.ffusion.ffs.scheduling.db.EventInfoLog.BATCH_SIZE = paramInt;
      ScheduleInfo.BATCH_SIZE = paramInt;
    }
  }
  
  private void a(FFSConnection paramFFSConnection)
  {
    try
    {
      List localList = InstructionTypeStatus.getRecoverable(paramFFSConnection);
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        InstructionTypeStatus localInstructionTypeStatus = (InstructionTypeStatus)localIterator.next();
        if (!jdField_do(localInstructionTypeStatus.getInstructionType()))
        {
          String str = "*** Crash recovery from state " + localInstructionTypeStatus.DispatchStatus + " for instruction " + localInstructionTypeStatus.InstructionType + " (FI Id=" + localInstructionTypeStatus.FIId + ").";
          FFSDebug.log(str);
          FFSDebug.console(str);
          try
          {
            ScheduleCalloutInfo localScheduleCalloutInfo = getScheduleCalloutInfo(localInstructionTypeStatus.FIId, localInstructionTypeStatus.InstructionType);
            ScheduleRunnable localScheduleRunnable = new ScheduleRunnable(localInstructionTypeStatus.FIId, localInstructionTypeStatus.InstructionType, localScheduleCalloutInfo, false);
            localScheduleRunnable.recoveryFromCrash(localInstructionTypeStatus);
          }
          catch (Exception localException2)
          {
            FFSDebug.log(str + ". Error: " + FFSDebug.stackTrace(localException2), 0);
          }
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** An error occurred during Crash Recovery. Error: " + FFSDebug.stackTrace(localException1), 0);
    }
  }
  
  public void refreshSmartCalendar()
    throws Exception
  {
    FFSDebug.log("Scheduler:refreshSmartCalendar start", 6);
    SmartCalendar.reset();
    DBUtil.startCalendars();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      InstructionType[] arrayOfInstructionType = getInstructionTypes();
      if (arrayOfInstructionType != null) {
        for (int i = 0; i < arrayOfInstructionType.length; i++) {
          if (arrayOfInstructionType[i].ActiveFlag == 1) {
            ScheduleInfo.updateAllWorkInstanceDate(localFFSConnectionHolder, arrayOfInstructionType[i].InstructionType, arrayOfInstructionType[i].FIId, arrayOfInstructionType[i].category);
          }
        }
      }
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("Scheduler:refreshSmartCalendar end", 6);
  }
  
  public InstructionType[] getInstructionTypes()
    throws FFSException
  {
    return BPWRegistryUtil.getAllInstructionTypes();
  }
  
  public InstructionType getInstructionType(String paramString1, String paramString2)
    throws FFSException
  {
    InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(paramString2, paramString1);
    if (localInstructionType == null) {
      throw new FFSException("The instruction type " + paramString1 + " (FI Id=" + paramString2 + ")" + " was not found in the registry.");
    }
    return localInstructionType;
  }
  
  public void resubmitEvent(FFSConnection paramFFSConnection, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    try
    {
      InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(paramString1, paramString2);
      if (localInstructionType == null) {
        throw new FFSException("The instruction type " + paramString2 + " cannot be found with registry.");
      }
      localObject = new ResubmitRunnable(paramFFSConnection, paramString1, paramString2, paramString3);
      Thread localThread = new Thread((Runnable)localObject);
      localThread.start();
      localThread.join();
    }
    catch (Exception localException)
    {
      Object localObject = "*** Scheduler.resubmitEvent:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(localException, (String)localObject);
      throw new FFSException((String)localObject);
    }
  }
  
  public void rerunCutOff(String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(paramString1, paramString2);
    if (localInstructionType == null) {
      throw new FFSException("The instruction type " + paramString2 + " cannot be found with registry.");
    }
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = "Failed to obtain a connection from from the connection pool";
        System.out.println((String)localObject1);
        throw new FFSException((String)localObject1);
      }
      Object localObject1 = new ResubmitRunnable(localFFSConnectionHolder.conn, paramString1, paramString2, paramString3, paramString4, true);
      localObject2 = new Thread((Runnable)localObject1);
      ((Thread)localObject2).start();
      ((Thread)localObject2).join();
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      Object localObject2 = "*** Scheduler.resubmitEvent:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(localException, (String)localObject2);
      throw new FFSException((String)localObject2);
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public void resubmitEvent(FFSConnection paramFFSConnection, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    try
    {
      InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(paramString1, paramString2);
      if (localInstructionType == null) {
        throw new FFSException("The instruction type " + paramString2 + " cannot be found with registry.");
      }
      localObject = new ResubmitRunnable(paramFFSConnection, paramString1, paramString2, paramString3, paramString4);
      Thread localThread = new Thread((Runnable)localObject);
      localThread.start();
      localThread.join();
    }
    catch (Exception localException)
    {
      Object localObject = "*** Scheduler.resubmitEvent:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(localException, (String)localObject);
      throw new FFSException((String)localObject);
    }
  }
  
  public String createSchedule(FFSConnection paramFFSConnection, String paramString1, String paramString2, ScheduleInfo paramScheduleInfo)
    throws FFSException
  {
    return ScheduleInfo.createSchedule(paramFFSConnection, paramString1, paramString2, paramScheduleInfo);
  }
  
  public int cancelSchedule(FFSConnection paramFFSConnection, String paramString)
    throws FFSException
  {
    ScheduleInfo.cancelSchedule(paramFFSConnection, paramString);
    return 0;
  }
  
  public int cancelSchedule(FFSConnection paramFFSConnection, String paramString1, String paramString2)
    throws FFSException
  {
    ScheduleInfo.cancelSchedule(paramFFSConnection, paramString1, paramString2);
    return 0;
  }
  
  public int cancelEvent(FFSConnection paramFFSConnection, String paramString)
    throws FFSException
  {
    EventInfo.cancelEvent(paramFFSConnection, paramString);
    return 0;
  }
  
  private Calendar a(String paramString, int paramInt)
    throws Exception
  {
    DateFormat localDateFormat = DateFormat.getTimeInstance(3);
    try
    {
      localDateFormat.parse(paramString);
    }
    catch (ParseException localParseException1)
    {
      FFSDebug.log("Scheduler failed to parse: " + paramString + " with default locale", 6);
      try
      {
        localDateFormat = DateFormat.getTimeInstance(3, Locale.US);
        localDateFormat.parse(paramString);
      }
      catch (ParseException localParseException2)
      {
        FFSDebug.log("Scheduler failed to parse: " + paramString + " with US locale", 6);
        throw localParseException2;
      }
    }
    Calendar localCalendar1 = localDateFormat.getCalendar();
    Calendar localCalendar2 = Calendar.getInstance();
    int i = localCalendar2.get(11);
    int j = localCalendar2.get(12);
    int m = localCalendar1.get(11);
    int n = localCalendar1.get(12);
    int k = i * 60 + j;
    int i1 = m * 60 + n;
    int i4;
    if (i1 > k) {
      i4 = i1 - k;
    } else {
      for (int i5 = 1;; i5++)
      {
        int i6 = i1 + paramInt * i5;
        if (i6 >= 1440)
        {
          i4 = i1 + 1440 - k;
          break;
        }
        if (i6 > k)
        {
          i4 = i6 - k;
          break;
        }
      }
    }
    int i2 = i4 / 60;
    int i3 = i4 % 60;
    localCalendar2.add(11, i2);
    localCalendar2.add(12, i3);
    localCalendar2.set(13, 0);
    FFSDebug.log("Scheduler:computeNextWakeupTime=" + localCalendar2.getTime().toString(), 6);
    return localCalendar2;
  }
  
  public synchronized void handleExternalScheduleReq(String paramString1, String paramString2)
    throws FFSException
  {
    if (!schIsRunning())
    {
      localObject1 = "Schedule Run failed. The Scheduler process is not running on this BPW Server.";
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = a(paramString1, paramString2);
    if (localObject1 == null)
    {
      localObject2 = "Schedule Run failed. Unable to find Schedule for " + paramString1 + " (FI Id=" + paramString2 + ").";
      throw new FFSException((String)localObject2);
    }
    Object localObject2 = new ScheduleTimer(paramString2, paramString1, null, 1, 100, this, "External", false, null, false);
    Thread localThread = new Thread((Runnable)localObject2);
    a((ScheduleTimer)localObject2, localThread);
    localThread.start();
  }
  
  private void a(ScheduleTimer paramScheduleTimer, Thread paramThread)
  {
    this.jdField_long.add(paramScheduleTimer);
    this.jdField_null.add(paramThread);
  }
  
  protected void a(ScheduleTimer paramScheduleTimer)
  {
    this.jdField_long.remove(paramScheduleTimer);
    this.jdField_null.remove(Thread.currentThread());
  }
  
  public ScheduleStatus runBatchProcess(ScheduleTimer paramScheduleTimer)
  {
    ScheduleStatus localScheduleStatus = null;
    if (!schIsRunning()) {
      return new ScheduleStatus(1, 4, "Scheduler is not running.");
    }
    String str1 = paramScheduleTimer.getInstructionType();
    String str2 = paramScheduleTimer.getFIId();
    FFSDebug.log("Scheduler:runSchedule called for " + str1 + " (FI Id=" + str2 + ")", 6);
    ScheduleRunnable localScheduleRunnable = a(str1, str2);
    if (localScheduleRunnable == null) {
      localScheduleStatus = new ScheduleStatus(1, 1, "Unable to find runnable for: " + str1 + " (FI Id=" + str2 + ")");
    } else {
      localScheduleStatus = localScheduleRunnable.execute(paramScheduleTimer);
    }
    return localScheduleStatus;
  }
  
  private ScheduleRunnable a(String paramString1, String paramString2)
  {
    String str = jdField_do(paramString1, paramString2);
    ScheduleRunnable localScheduleRunnable = (ScheduleRunnable)this.f.get(str);
    return localScheduleRunnable;
  }
  
  public boolean schThreadCreated()
  {
    if (this.g == null) {
      return false;
    }
    return !this.g.isEmpty();
  }
  
  private String jdField_do(String paramString1, String paramString2)
  {
    return paramString1 + "_" + paramString2;
  }
  
  public SchedulerInfo getSchedulerInfo(String paramString1, String paramString2)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    Calendar localCalendar = null;
    boolean bool = false;
    InstructionType localInstructionType = null;
    InstructionTypeStatus localInstructionTypeStatus = null;
    try
    {
      localInstructionType = BPWRegistryUtil.getInstructionType(paramString2, paramString1);
      if (localInstructionType == null)
      {
        FFSDebug.log("Scheduler.getSchedulerInfo: Can't find InstructionType object by instructionType= " + paramString1 + ", fiid = " + paramString2, 6);
        SchedulerInfo localSchedulerInfo = null;
        return localSchedulerInfo;
      }
      localInstructionTypeStatus = InstructionTypeStatus.get(paramString2, paramString1, localFFSConnectionHolder);
      if (localInstructionType.ActiveFlag == 1)
      {
        localCalendar = jdField_if(localInstructionType);
        bool = a(localInstructionType);
      }
    }
    catch (Exception localException)
    {
      String str = "Scheduler.getSchedulerInfo[] failed. Error: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return a(localInstructionType, localInstructionTypeStatus, localCalendar, bool);
  }
  
  public SchedulerInfo[] getSchedulerInfo()
    throws FFSException
  {
    SchedulerInfo[] arrayOfSchedulerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      InstructionType[] arrayOfInstructionType = getInstructionTypes();
      if (arrayOfInstructionType == null)
      {
        FFSDebug.log("Scheduler.getSchedulerInfo: Did not find InstructionType objects", 6);
        localObject1 = null;
        return localObject1;
      }
      if ((arrayOfInstructionType != null) && (arrayOfInstructionType.length > 0))
      {
        arrayOfSchedulerInfo = new SchedulerInfo[arrayOfInstructionType.length];
        localObject1 = InstructionTypeStatus.getAll(localFFSConnectionHolder);
        int i;
        if ((localObject1 == null) || (localObject1.length <= 0))
        {
          FFSDebug.log("Scheduler.getSchedulerInfo: Did not find InstructionTypeStatus", 6);
          i = 0;
        }
        while (i < arrayOfInstructionType.length)
        {
          arrayOfSchedulerInfo[i] = a(arrayOfInstructionType[i], null, null, false);
          i++;
          continue;
          HashMap localHashMap = a((InstructionTypeStatus[])localObject1);
          for (int j = 0; j < arrayOfInstructionType.length; j++)
          {
            Calendar localCalendar = null;
            boolean bool = false;
            if (arrayOfInstructionType[j].ActiveFlag == 1)
            {
              localCalendar = jdField_if(arrayOfInstructionType[j]);
              bool = a(arrayOfInstructionType[j]);
            }
            String str = jdField_do(arrayOfInstructionType[j].InstructionType, arrayOfInstructionType[j].FIId);
            arrayOfSchedulerInfo[j] = a(arrayOfInstructionType[j], (InstructionTypeStatus)localHashMap.get(str), localCalendar, bool);
          }
        }
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = "Scheduler.getSchedulerInfo[] failed. Error: " + FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfSchedulerInfo;
  }
  
  private Calendar jdField_if(InstructionType paramInstructionType)
  {
    if (this.jdField_int != null)
    {
      String str = jdField_do(paramInstructionType.InstructionType, paramInstructionType.FIId);
      ScheduleTimer localScheduleTimer = (ScheduleTimer)this.jdField_int.get(str);
      if (localScheduleTimer != null) {
        return localScheduleTimer.getNextRunTime();
      }
    }
    return null;
  }
  
  private boolean a(InstructionType paramInstructionType)
  {
    if (this.g != null)
    {
      String str = jdField_do(paramInstructionType.InstructionType, paramInstructionType.FIId);
      Thread localThread = (Thread)this.g.get(str);
      if (localThread != null) {
        return localThread.isAlive();
      }
    }
    return false;
  }
  
  private SchedulerInfo a(InstructionType paramInstructionType, InstructionTypeStatus paramInstructionTypeStatus, Calendar paramCalendar, boolean paramBoolean)
  {
    SchedulerInfo localSchedulerInfo = new SchedulerInfo();
    localSchedulerInfo.SchedulerName = paramInstructionType.InstructionType;
    localSchedulerInfo.FIID = paramInstructionType.FIId;
    localSchedulerInfo.InstructionType = paramInstructionType.InstructionType;
    localSchedulerInfo.HandlerClassName = paramInstructionType.HandlerClassName;
    localSchedulerInfo.SchedulerStartTime = paramInstructionType.SchedulerStartTime;
    localSchedulerInfo.SchedulerInterval = paramInstructionType.SchedulerInterval;
    localSchedulerInfo.FileBasedRecovery = paramInstructionType.FileBasedRecovery;
    localSchedulerInfo.RecoveryTimeLimit = paramInstructionType.ChkTmAutoRecover;
    localSchedulerInfo.ActiveFlag = paramInstructionType.ActiveFlag;
    if (paramInstructionTypeStatus != null)
    {
      localSchedulerInfo.LastRunTime = paramInstructionTypeStatus.LastSchedulerTime;
      localSchedulerInfo.SchedulerStatus = paramInstructionTypeStatus.SchedulerStatus;
    }
    else
    {
      localSchedulerInfo.LastRunTime = "N/A";
      localSchedulerInfo.SchedulerStatus = "N/A";
    }
    if (paramCalendar != null)
    {
      localSchedulerInfo.NextRunTime = paramCalendar.getTime().toString();
      a(localSchedulerInfo, paramCalendar, paramInstructionType);
    }
    else
    {
      localSchedulerInfo.NextRunTime = "N/A";
      localSchedulerInfo.FinalRuntimeForNextProcessDate = "N/A";
    }
    if (paramBoolean) {
      localSchedulerInfo.ThreadStatus = "Running";
    } else {
      localSchedulerInfo.ThreadStatus = "NotRunning";
    }
    return localSchedulerInfo;
  }
  
  private void a(SchedulerInfo paramSchedulerInfo, Calendar paramCalendar, InstructionType paramInstructionType)
  {
    String str = "Scheduler.setFinalRunTime(schedulerInfo)";
    Object localObject1;
    if (!paramInstructionType.useCutOffs)
    {
      localObject1 = (Calendar)paramCalendar.clone();
      int i = paramCalendar.get(6);
      while (((Calendar)localObject1).get(6) == i) {
        ((Calendar)localObject1).add(12, paramInstructionType.SchedulerInterval);
      }
      ((Calendar)localObject1).add(12, paramInstructionType.SchedulerInterval * -1);
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      paramSchedulerInfo.FinalRuntimeForNextProcessDate = localSimpleDateFormat1.format(((Calendar)localObject1).getTime());
    }
    else
    {
      localObject1 = new FFSConnectionHolder();
      FFSResultSet localFFSResultSet = null;
      try
      {
        int j = paramCalendar.get(7);
        localObject2 = new Object[] { paramInstructionType.InstructionType, paramInstructionType.FIId, new Integer(j) };
        ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
        localFFSResultSet = DBUtil.openResultSet((FFSConnectionHolder)localObject1, "select max(ProcessTime) from SCH_CutOffs where Status = 'ACTIVE' and InstructionType = ? and FIID = ? and Day = ?", (Object[])localObject2);
        if ((localFFSResultSet.getNextRow()) && (localFFSResultSet.getColumnString(1) != null))
        {
          SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
          paramSchedulerInfo.FinalRuntimeForNextProcessDate = (localSimpleDateFormat2.format(paramCalendar.getTime()) + " " + localFFSResultSet.getColumnString(1));
        }
        else
        {
          paramSchedulerInfo.FinalRuntimeForNextProcessDate = "N/A";
        }
      }
      catch (Exception localException1)
      {
        Object localObject2 = "*** " + str + " failed:";
        FFSDebug.log((String)localObject2 + localException1.toString());
      }
      finally
      {
        if (localFFSResultSet != null) {
          try
          {
            localFFSResultSet.close();
          }
          catch (Exception localException2) {}
        }
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
  }
  
  private HashMap a(InstructionTypeStatus[] paramArrayOfInstructionTypeStatus)
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < paramArrayOfInstructionTypeStatus.length; i++)
    {
      String str = jdField_do(paramArrayOfInstructionTypeStatus[i].InstructionType, paramArrayOfInstructionTypeStatus[i].FIId);
      localHashMap.put(str, paramArrayOfInstructionTypeStatus[i]);
    }
    return localHashMap;
  }
  
  public static ScheduleHist[] getScheduleHist(String paramString1, String paramString2, ScheduleHist paramScheduleHist)
    throws FFSException
  {
    ScheduleHist[] arrayOfScheduleHist = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      arrayOfScheduleHist = ScheduleHistory.getScheduleHist(paramString1, paramString2, paramScheduleHist, localFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      String str = "Scheduler.getScheduleHist failed. Error: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfScheduleHist;
  }
  
  public synchronized void updateScheduleRunTime(InstructionType paramInstructionType)
    throws FFSException
  {
    FFSDebug.log("Scheduler:updateScheduleRunTime start.", 6);
    updateSchedule(paramInstructionType, true);
    FFSDebug.log("Scheduler:updateScheduleRunTime end.", 6);
  }
  
  public synchronized void updateSchedule(InstructionType paramInstructionType, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Scheduler.updateSchedule: ";
    FFSDebug.log(str1 + "start.", 6);
    if (!schIsRunning()) {
      throw new FFSException("The Scheduler is not running on this BPW Server.");
    }
    String str2 = paramInstructionType.FIId;
    String str3 = paramInstructionType.InstructionType;
    InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(str2, str3);
    if (localInstructionType == null) {
      throw new FFSException("The instruction type " + str3 + " (FI Id=" + str2 + ")" + "  was not found in the registry.");
    }
    if (!paramInstructionType.useCutOffs)
    {
      try
      {
        a(paramInstructionType.SchedulerStartTime, paramInstructionType.SchedulerInterval);
      }
      catch (Exception localException)
      {
        throw new FFSException("Failed to update the configuration values for " + str3 + " (FI Id=" + str2 + "):" + " Invalid SchedulerStartTime value" + " was provided:" + " " + paramInstructionType.SchedulerStartTime + " (Format = HH:MM [AM/PM])");
      }
      if (paramInstructionType.SchedulerInterval <= 0) {
        throw new FFSException("Failed to update the configuration values for " + str3 + " (FI Id=" + str2 + "):" + " Invalid SchedulerInterval value" + " was provided:" + " " + paramInstructionType.SchedulerInterval);
      }
    }
    String str4 = jdField_do(str3, str2);
    ScheduleTimer localScheduleTimer = (ScheduleTimer)this.jdField_int.get(str4);
    if (localScheduleTimer != null) {
      synchronized (localScheduleTimer._processingFlagLock)
      {
        if (localScheduleTimer._processingFlag == true) {
          throw new FFSException("Failed to update the configuration values for " + str3 + " (FI Id=" + str2 + "):" + " Batch Process is currently" + " running.");
        }
        if (!paramBoolean) {
          a(paramInstructionType, localInstructionType);
        }
        localScheduleTimer.stop();
      }
    }
    if (!paramBoolean) {
      a(paramInstructionType, localInstructionType);
    }
    jdField_if(paramInstructionType, localInstructionType);
    FFSDebug.log(str1 + "end.", 6);
  }
  
  private void jdField_if(InstructionType paramInstructionType1, InstructionType paramInstructionType2)
    throws FFSException
  {
    String str1 = paramInstructionType1.FIId;
    String str2 = paramInstructionType1.InstructionType;
    InstructionType localInstructionType = new InstructionType(paramInstructionType2);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      FFSDebug.log("Updating SchedulerStartTime: " + paramInstructionType2.SchedulerStartTime + " --> " + paramInstructionType1.SchedulerStartTime, 6);
      FFSDebug.log("Updating SchedulerInterval: " + paramInstructionType2.SchedulerInterval + " --> " + paramInstructionType1.SchedulerInterval, 6);
      FFSDebug.log("Updating ActiveFlag: " + paramInstructionType2.ActiveFlag + " --> " + paramInstructionType1.ActiveFlag, 6);
      FFSDebug.log("Updating useCutOffs: " + new String(paramInstructionType2.useCutOffs ? "Y" : "N") + " --> " + new String(paramInstructionType1.useCutOffs ? "Y" : "N"), 6);
      FFSDebug.log("Updating createEmptyFile: " + new String(paramInstructionType2.createEmptyFile ? "Y" : "N") + " --> " + new String(paramInstructionType1.createEmptyFile ? "Y" : "N"), 6);
      FFSDebug.log("Updating processOnHolidays: " + new String(paramInstructionType2.processOnHolidays ? "Y" : "N") + " --> " + new String(paramInstructionType1.processOnHolidays ? "Y" : "N"), 6);
      if (paramInstructionType1.category == null) {
        paramInstructionType1.category = "Other";
      }
      FFSDebug.log("Updating Category: " + paramInstructionType2.category + " --> " + paramInstructionType1.category, 6);
      FFSDebug.log("Updating memo: " + paramInstructionType2.memo + " --> " + paramInstructionType1.memo, 6);
      DBInstructionType.updateInstructionType(localFFSConnectionHolder, paramInstructionType2.FIId, paramInstructionType2.InstructionType, paramInstructionType2.HandlerClassName, paramInstructionType1.SchedulerStartTime, paramInstructionType1.SchedulerInterval, paramInstructionType2.DispatchStartTime, paramInstructionType2.DispatchInterval, paramInstructionType2.ResubmitEventSupported == 1, paramInstructionType2.ChkTmAutoRecover == 1, paramInstructionType2.FileBasedRecovery == 1, paramInstructionType1.ActiveFlag == 1, paramInstructionType2.RouteID, paramInstructionType1.useCutOffs, paramInstructionType1.createEmptyFile, paramInstructionType1.processOnHolidays, paramInstructionType1.category, paramInstructionType1.memo);
      paramInstructionType2.SchedulerStartTime = paramInstructionType1.SchedulerStartTime;
      paramInstructionType2.SchedulerInterval = paramInstructionType1.SchedulerInterval;
      paramInstructionType2.ActiveFlag = paramInstructionType1.ActiveFlag;
      paramInstructionType2.useCutOffs = paramInstructionType1.useCutOffs;
      paramInstructionType2.createEmptyFile = paramInstructionType1.createEmptyFile;
      paramInstructionType2.processOnHolidays = paramInstructionType1.processOnHolidays;
      paramInstructionType2.category = paramInstructionType1.category;
      paramInstructionType2.memo = paramInstructionType1.memo;
      String str3 = jdField_do(str2, str1);
      ScheduleTimer localScheduleTimer = (ScheduleTimer)this.jdField_int.remove(str3);
      if (localScheduleTimer != null)
      {
        localScheduleTimer.stop();
        Thread localThread = (Thread)this.g.remove(str3);
        if (localThread != null) {
          try
          {
            localThread.interrupt();
            localThread.join();
          }
          catch (Exception localException) {}
        }
      }
      a(localFFSConnectionHolder, paramInstructionType2);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      paramInstructionType2.SchedulerStartTime = localInstructionType.SchedulerStartTime;
      paramInstructionType2.SchedulerInterval = localInstructionType.SchedulerInterval;
      paramInstructionType2.ActiveFlag = localInstructionType.ActiveFlag;
      throw new FFSException("Failed to update the configuration values for " + str2 + " (FI Id=" + str1 + ")" + ": " + localFFSException.getMessage());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public void updateScheduleProcessing(InstructionType paramInstructionType)
    throws FFSException
  {
    FFSDebug.log("Scheduler:updateScheduleProcessing start.", 6);
    if (!schIsRunning()) {
      throw new FFSException("The Scheduler is not running on this BPW Server.");
    }
    String str1 = paramInstructionType.FIId;
    String str2 = paramInstructionType.InstructionType;
    InstructionType localInstructionType1 = BPWRegistryUtil.getInstructionType(str1, str2);
    if (localInstructionType1 == null) {
      throw new FFSException("The instruction type " + str2 + " (FI Id=" + str1 + ")" + "  was not found in the registry.");
    }
    InstructionType localInstructionType2 = new InstructionType(localInstructionType1);
    ScheduleRunnable localScheduleRunnable = a(str2, str1);
    if (localScheduleRunnable == null) {
      throw new FFSException("The instruction type " + str2 + " (FI Id=" + str1 + ")" + "  was not found in the Scheduler.");
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      FFSDebug.log("Updating HandlerClassName: " + localInstructionType1.HandlerClassName + " --> " + paramInstructionType.HandlerClassName, 6);
      FFSDebug.log("Updating ResubmitEventSupported: " + localInstructionType1.ResubmitEventSupported + " --> " + paramInstructionType.ResubmitEventSupported, 6);
      FFSDebug.log("Updating ChkTmAutoRecover: " + localInstructionType1.ChkTmAutoRecover + " --> " + paramInstructionType.ChkTmAutoRecover, 6);
      FFSDebug.log("Updating FileBasedRecovery: " + localInstructionType1.FileBasedRecovery + " --> " + paramInstructionType.FileBasedRecovery, 6);
      FFSDebug.log("Updating useCutOffs: " + new String(localInstructionType1.useCutOffs ? "Y" : "N") + " --> " + new String(paramInstructionType.useCutOffs ? "Y" : "N"), 6);
      FFSDebug.log("Updating createEmptyFile: " + new String(localInstructionType1.createEmptyFile ? "Y" : "N") + " --> " + new String(paramInstructionType.createEmptyFile ? "Y" : "N"), 6);
      FFSDebug.log("Updating processOnHolidays: " + new String(localInstructionType1.processOnHolidays ? "Y" : "N") + " --> " + new String(paramInstructionType.processOnHolidays ? "Y" : "N"), 6);
      if (paramInstructionType.category == null) {
        paramInstructionType.category = "Other";
      }
      FFSDebug.log("Updating category: " + localInstructionType1.category + " --> " + paramInstructionType.category, 6);
      FFSDebug.log("Updating memo: " + localInstructionType1.memo + " --> " + paramInstructionType.memo, 6);
      DBInstructionType.updateInstructionType(localFFSConnectionHolder, localInstructionType1.FIId, localInstructionType1.InstructionType, paramInstructionType.HandlerClassName, localInstructionType1.SchedulerStartTime, localInstructionType1.SchedulerInterval, localInstructionType1.DispatchStartTime, localInstructionType1.DispatchInterval, paramInstructionType.ResubmitEventSupported == 1, paramInstructionType.ChkTmAutoRecover == 1, paramInstructionType.FileBasedRecovery == 1, localInstructionType1.ActiveFlag == 1, localInstructionType1.RouteID, paramInstructionType.useCutOffs, paramInstructionType.createEmptyFile, paramInstructionType.processOnHolidays, paramInstructionType.category, paramInstructionType.memo);
      localInstructionType1.HandlerClassName = paramInstructionType.HandlerClassName;
      localInstructionType1.ResubmitEventSupported = paramInstructionType.ResubmitEventSupported;
      localInstructionType1.ChkTmAutoRecover = paramInstructionType.ChkTmAutoRecover;
      localInstructionType1.FileBasedRecovery = paramInstructionType.FileBasedRecovery;
      localInstructionType1.useCutOffs = paramInstructionType.useCutOffs;
      localInstructionType1.createEmptyFile = paramInstructionType.createEmptyFile;
      localInstructionType1.processOnHolidays = paramInstructionType.processOnHolidays;
      localInstructionType1.category = paramInstructionType.category;
      localInstructionType1.memo = paramInstructionType.memo;
      a(str1, str2, false);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      localInstructionType1.HandlerClassName = localInstructionType2.HandlerClassName;
      localInstructionType1.ResubmitEventSupported = localInstructionType2.ResubmitEventSupported;
      localInstructionType1.ChkTmAutoRecover = localInstructionType2.ChkTmAutoRecover;
      localInstructionType1.FileBasedRecovery = localInstructionType2.FileBasedRecovery;
      localInstructionType1.useCutOffs = localInstructionType2.useCutOffs;
      localInstructionType1.createEmptyFile = localInstructionType2.createEmptyFile;
      localInstructionType1.processOnHolidays = localInstructionType2.processOnHolidays;
      localInstructionType1.category = localInstructionType2.category;
      localInstructionType1.memo = localInstructionType2.memo;
      throw new FFSException("Failed to update the configuration values for " + str2 + " (FI Id=" + str1 + ")" + ": " + localFFSException.getMessage());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log("Scheduler:updateScheduleProcessing end.", 6);
  }
  
  private void a(InstructionType paramInstructionType1, InstructionType paramInstructionType2)
    throws FFSException
  {
    FFSDebug.log("Scheduler:updateScheduleProcessingAndRouteID start.", 6);
    if (paramInstructionType2 == null) {
      throw new FFSException("The instruction type " + paramInstructionType1.InstructionType + " (FI Id=" + paramInstructionType1.FIId + ")" + "  was not found in the registry.");
    }
    InstructionType localInstructionType = new InstructionType(paramInstructionType2);
    paramInstructionType2.RouteID = paramInstructionType1.RouteID;
    try
    {
      updateScheduleProcessing(paramInstructionType1);
    }
    catch (FFSException localFFSException)
    {
      paramInstructionType2.RouteID = localInstructionType.RouteID;
      throw new FFSException(localFFSException.getMessage());
    }
    FFSDebug.log("Scheduler:updateScheduleProcessingAndRouteID end.", 6);
  }
  
  private boolean jdField_do(InstructionType paramInstructionType)
    throws Exception
  {
    String str = "Scheduler.recoverFromCrash: " + paramInstructionType.FIId + "," + paramInstructionType.InstructionType + " ChkTmAutoRecover=" + paramInstructionType.ChkTmAutoRecover;
    FFSDebug.log(str);
    FFSDebug.console(str);
    if (paramInstructionType.ChkTmAutoRecover == 0) {
      return false;
    }
    Calendar localCalendar = a(paramInstructionType.SchedulerStartTime, paramInstructionType.SchedulerInterval);
    long l = (localCalendar.getTimeInMillis() - System.currentTimeMillis()) / 60000L;
    str = "Scheduler.recoverFromCrash: waitMinutes=" + l + ",MinuteNotToRecover=" + this.b.MinuteNotToRecover;
    FFSDebug.log(str);
    FFSDebug.console(str);
    return l <= this.b.MinuteNotToRecover;
  }
  
  private void a()
    throws Exception
  {
    this.e = InetAddress.getLocalHost().getHostName();
    this.c = InetAddress.getLocalHost().getHostAddress();
    this.jdField_char = this.b.BPWServ_protocol.trim();
    this.jdField_for = this.b.BPWServ_host.trim();
    this.a = Integer.toString(this.b.BPWServ_port);
    this.jdField_new = this.b.BPWServ_userName;
    if (this.jdField_new != null) {
      this.jdField_new = this.jdField_new.trim();
    }
    this.d = this.b.BPWServ_password;
    if (this.d != null) {
      this.d = this.d.trim();
    }
    this.jdField_do = this.b.BPWServ_jndiName.trim();
    this.jdField_else = this.b.BPWServ_nameContext.trim();
    this.jdField_try = true;
  }
  
  public ScheduleCalloutInfo getScheduleCalloutInfo(String paramString1, String paramString2)
    throws Exception
  {
    if (!this.jdField_try) {
      a();
    }
    ScheduleCalloutInfo localScheduleCalloutInfo = new ScheduleCalloutInfo();
    localScheduleCalloutInfo.setFiId(paramString1);
    localScheduleCalloutInfo.setInstructionType(paramString2);
    localScheduleCalloutInfo.setEJBContextFactory(this.jdField_else);
    String str = this.jdField_char + "://" + this.jdField_for + ":" + this.a;
    localScheduleCalloutInfo.setEJBUrl(str);
    localScheduleCalloutInfo.setEJBUsername(this.jdField_new);
    localScheduleCalloutInfo.setEJBPassword(this.d);
    return localScheduleCalloutInfo;
  }
  
  public synchronized void addSchedule(InstructionType paramInstructionType)
    throws FFSException
  {
    String str1 = "Scheduler.addSchedule: ";
    FFSDebug.log(str1 + "start.", 6);
    if (!schIsRunning()) {
      throw new FFSException("The Scheduler is not running on this BPW Server.");
    }
    String str2 = paramInstructionType.FIId;
    String str3 = paramInstructionType.InstructionType;
    InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(str2, str3);
    if (localInstructionType != null) {
      throw new FFSException("The instruction type " + str3 + " (FI Id=" + str2 + ")" + " already exists. " + "Duplicate instruction type and FI Id" + " combination is not allowed.");
    }
    if (!paramInstructionType.verify()) {
      throw new FFSException("The new configuration for " + paramInstructionType.InstructionType + " (FI Id=" + paramInstructionType.FIId + ")" + "  contains incorrect information. " + "Unable to add the new schedule.");
    }
    if ((paramInstructionType.RouteID > 0) && (BPWRegistryUtil.isDuplicateRouteID(paramInstructionType.RouteID, str2) == true)) {
      throw new FFSException("The new configuration for " + paramInstructionType.InstructionType + " (FI Id=" + paramInstructionType.FIId + ")" + "  contains a duplicate route ID. " + "Unable to add the new schedule.");
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      BPWRegistryUtil.addInstructionType(paramInstructionType);
      addScheduleToDB(localFFSConnectionHolder, paramInstructionType);
      a(str2, str3, false);
      a(localFFSConnectionHolder, paramInstructionType);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      BPWRegistryUtil.deleteInstructionType(str2, str3);
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str4 = jdField_do(str3, str2);
      this.f.remove(str4);
      this.jdField_int.remove(str4);
      this.g.remove(str4);
      throw new FFSException("Failed to add new schedule config for " + str3 + " (FI Id=" + str2 + ")" + ": " + localThrowable.getMessage());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log(str1 + "end.", 6);
  }
  
  public synchronized void deleteSchedule(InstructionType paramInstructionType)
    throws FFSException
  {
    String str1 = "Scheduler.deleteSchedule: ";
    FFSDebug.log(str1 + "start.", 6);
    if (!schIsRunning()) {
      throw new FFSException("The Scheduler is not running on this BPW Server.");
    }
    String str2 = paramInstructionType.FIId;
    String str3 = paramInstructionType.InstructionType;
    if ((str2 == null) || (str3 == null) || (str2.trim().length() <= 0) || (str3.trim().length() <= 0)) {
      throw new FFSException(str1 + "The fiid/instruction type is null.");
    }
    InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(str2, str3);
    if (localInstructionType == null) {
      throw new FFSException("The instruction type " + str3 + " (FI Id=" + str2 + ")" + "  was not existed. " + "Can't delete the InstructionType.");
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      String str4 = jdField_do(str3, str2);
      ScheduleTimer localScheduleTimer = (ScheduleTimer)this.jdField_int.get(str4);
      if (localScheduleTimer != null) {
        synchronized (localScheduleTimer._processingFlagLock)
        {
          if (localScheduleTimer._processingFlag == true) {
            throw new FFSException("Failed to delete the configuration values for " + str3 + " (FI Id=" + str2 + "):" + " Batch Process is currently" + " running.");
          }
          localScheduleTimer.stop();
        }
      }
      deleteScheduleFromDB(localFFSConnectionHolder, str2, str3);
      BPWRegistryUtil.deleteInstructionType(str2, str3);
      if (localScheduleTimer != null) {
        jdField_int(str3, str2);
      }
      jdField_for(str3, str2);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      BPWRegistryUtil.addInstructionType(localInstructionType);
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      throw new FFSException("Failed to delete the schedule config for " + str3 + " (FI Id=" + str2 + ")" + ": " + localThrowable.getMessage());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log(str1 + "end.", 6);
  }
  
  public static void addScheduleToDB(FFSConnectionHolder paramFFSConnectionHolder, InstructionType paramInstructionType)
    throws FFSException
  {
    String str1 = "DBInstructionType.addScheduleToDB: ";
    FFSDebug.log(str1 + "start.", 6);
    String str2 = paramInstructionType.FIId;
    String str3 = paramInstructionType.InstructionType;
    try
    {
      DBInstructionType.registerInstructionType(paramFFSConnectionHolder, paramInstructionType.FIId, paramInstructionType.InstructionType, paramInstructionType.HandlerClassName, paramInstructionType.SchedulerStartTime, paramInstructionType.SchedulerInterval, paramInstructionType.DispatchStartTime, paramInstructionType.DispatchInterval, paramInstructionType.ResubmitEventSupported == 1, paramInstructionType.ChkTmAutoRecover == 1, paramInstructionType.FileBasedRecovery == 1, paramInstructionType.ActiveFlag == 1, paramInstructionType.RouteID, paramInstructionType.useCutOffs, paramInstructionType.createEmptyFile, paramInstructionType.processOnHolidays, paramInstructionType.category, paramInstructionType.memo);
      if (!InstructionType.isStandardInstType(str3))
      {
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, str3, "-1", str2);
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        localScheduleInfo.FIId = str2;
        localScheduleInfo.Status = "Active";
        localScheduleInfo.StatusOption = 0;
        localScheduleInfo.Frequency = 12;
        localScheduleInfo.StartDate = 0;
        localScheduleInfo.InstanceCount = 1;
        localScheduleInfo.LogID = "-1";
        localScheduleInfo.NextInstanceDate = 1000010100;
        localScheduleInfo.WorkInstanceDate = 0;
        localScheduleInfo.CurInstanceNum = 1;
        localScheduleInfo.Perpetual = 1;
        ScheduleInfo.createInstTypeSchedule(paramFFSConnectionHolder, str3, "-1", localScheduleInfo);
      }
      InstructionTypeStatus.initSchedulerStatus(paramFFSConnectionHolder, str2, str3);
    }
    catch (Exception localException)
    {
      throw new FFSException("Failed to add schedule to DB for " + str3 + " (FI Id=" + str2 + ")" + ": " + localException.getMessage());
    }
    FFSDebug.log(str1 + "end.", 6);
  }
  
  public static void deleteScheduleFromDB(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str = "DBInstructionType.deleteScheduleToDB: ";
    FFSDebug.log(str + "start.", 6);
    try
    {
      DBInstructionType.deleteInstructionType(paramFFSConnectionHolder, paramString1, paramString2);
      if (!InstructionType.isStandardInstType(paramString2)) {
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, paramString2, "-1", paramString1);
      }
      InstructionTypeStatus.delete(paramFFSConnectionHolder, paramString1, paramString2);
    }
    catch (Exception localException)
    {
      throw new FFSException("Failed to delete schedule from DB for " + paramString2 + " (FI Id=" + paramString1 + ")" + ": " + localException.getMessage());
    }
    FFSDebug.log(str + "end.", 6);
  }
  
  public CutOffInfo deleteCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.deleteCutOff :";
    int i = 0;
    CutOffInfo localCutOffInfo = null;
    String str2 = paramCutOffInfo.getFIId();
    String str3 = paramCutOffInfo.getInstructionType();
    String str4 = jdField_do(str2, str3);
    ScheduleTimer localScheduleTimer = (ScheduleTimer)this.jdField_int.get(str4);
    if (localScheduleTimer != null) {
      synchronized (localScheduleTimer._processingFlagLock)
      {
        if (localScheduleTimer._processingFlag == true) {
          throw new FFSException("Failed to update the configuration values for " + str3 + " (FI Id=" + str2 + "):" + " Batch Process is currently" + " running.");
        }
        localCutOffInfo = jdField_do(paramCutOffInfo);
        if ((localCutOffInfo != null) && (localCutOffInfo.getStatusCode() == 0))
        {
          localScheduleTimer.stop();
          i = 1;
        }
      }
    }
    localCutOffInfo = jdField_do(paramCutOffInfo);
    if ((localCutOffInfo != null) && (localCutOffInfo.getStatusCode() == 0)) {
      i = 1;
    }
    if (i == 1) {
      jdField_if(str2, str3);
    }
    FFSDebug.log(str1 + " end ", 6);
    return localCutOffInfo;
  }
  
  private CutOffInfo jdField_do(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.deleteCutOffInLock :";
    FFSConnectionHolder localFFSConnectionHolder = null;
    CutOffInfo localCutOffInfo1 = null;
    FFSDebug.log(str1 + "start ", 6);
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localCutOffInfo1 = DBInstructionType.deleteCutOffTX(localFFSConnectionHolder, paramCutOffInfo);
      if (paramCutOffInfo.getStatusCode() == 0) {
        localFFSConnectionHolder.conn.commit();
      } else {
        localFFSConnectionHolder.conn.rollback();
      }
      FFSDebug.log(str1 + " : end ", 6);
      CutOffInfo localCutOffInfo2 = localCutOffInfo1;
      return localCutOffInfo2;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = str1 + " failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public CutOffInfo addCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.addCutOff :";
    int i = 0;
    CutOffInfo localCutOffInfo = null;
    String str2 = paramCutOffInfo.getFIId();
    String str3 = paramCutOffInfo.getInstructionType();
    String str4 = jdField_do(str2, str3);
    ScheduleTimer localScheduleTimer = (ScheduleTimer)this.jdField_int.get(str4);
    if (localScheduleTimer != null) {
      synchronized (localScheduleTimer._processingFlagLock)
      {
        if (localScheduleTimer._processingFlag == true) {
          throw new FFSException("Failed to update the configuration values for " + str3 + " (FI Id=" + str2 + "):" + " Batch Process is currently" + " running.");
        }
        localCutOffInfo = a(paramCutOffInfo);
        if ((localCutOffInfo != null) && (localCutOffInfo.getStatusCode() == 0))
        {
          localScheduleTimer.stop();
          i = 1;
        }
      }
    }
    localCutOffInfo = a(paramCutOffInfo);
    if ((localCutOffInfo != null) && (localCutOffInfo.getStatusCode() == 0)) {
      i = 1;
    }
    if (i == 1) {
      jdField_if(str2, str3);
    }
    FFSDebug.log(str1 + " end ", 6);
    return localCutOffInfo;
  }
  
  private CutOffInfo a(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.addCutOffInLock :";
    CutOffInfo localCutOffInfo1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log(str1 + "start ", 6);
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localCutOffInfo1 = DBInstructionType.addCutOff(localFFSConnectionHolder, paramCutOffInfo);
      if (localCutOffInfo1.getStatusCode() == 0) {
        localFFSConnectionHolder.conn.commit();
      } else {
        localFFSConnectionHolder.conn.rollback();
      }
      FFSDebug.log(str1 + " end ", 6);
      CutOffInfo localCutOffInfo2 = localCutOffInfo1;
      return localCutOffInfo2;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public CutOffInfo modCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.modCutOff :";
    int i = 0;
    CutOffInfo localCutOffInfo = null;
    String str2 = paramCutOffInfo.getFIId();
    String str3 = paramCutOffInfo.getInstructionType();
    String str4 = jdField_do(str2, str3);
    ScheduleTimer localScheduleTimer = (ScheduleTimer)this.jdField_int.get(str4);
    if (localScheduleTimer != null) {
      synchronized (localScheduleTimer._processingFlagLock)
      {
        if (localScheduleTimer._processingFlag == true) {
          throw new FFSException("Failed to update the configuration values for " + str3 + " (FI Id=" + str2 + "):" + " Batch Process is currently" + " running.");
        }
        localCutOffInfo = jdField_if(paramCutOffInfo);
        if ((localCutOffInfo != null) && (localCutOffInfo.getStatusCode() == 0))
        {
          localScheduleTimer.stop();
          i = 1;
        }
      }
    }
    localCutOffInfo = jdField_if(paramCutOffInfo);
    if ((localCutOffInfo != null) && (localCutOffInfo.getStatusCode() == 0)) {
      i = 1;
    }
    if (i == 1) {
      jdField_if(str2, str3);
    }
    FFSDebug.log(str1 + " end ", 6);
    return localCutOffInfo;
  }
  
  public CutOffInfo removeCutOffExtension(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.removeCutOffExtension :";
    CutOffInfo localCutOffInfo1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      FFSDebug.log(str1, "start ", 6);
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localCutOffInfo1 = DBInstructionType.removeCutOffExtension(localFFSConnectionHolder, paramCutOffInfo);
      if (localCutOffInfo1.getStatusCode() == 0) {
        localFFSConnectionHolder.conn.commit();
      } else {
        localFFSConnectionHolder.conn.rollback();
      }
      FFSDebug.log(str1 + " : end ", 6);
      CutOffInfo localCutOffInfo2 = localCutOffInfo1;
      return localCutOffInfo2;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  private CutOffInfo jdField_if(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.modCutOffInLock :";
    CutOffInfo localCutOffInfo1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      FFSDebug.log(str1, "start ", 6);
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localCutOffInfo1 = DBInstructionType.updateCutOff(localFFSConnectionHolder, paramCutOffInfo);
      if (localCutOffInfo1.getStatusCode() == 0) {
        localFFSConnectionHolder.conn.commit();
      } else {
        localFFSConnectionHolder.conn.rollback();
      }
      FFSDebug.log(str1 + " : end ", 6);
      CutOffInfo localCutOffInfo2 = localCutOffInfo1;
      return localCutOffInfo2;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public CutOffInfo getCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.getCutOff: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    CutOffInfo localCutOffInfo1 = null;
    try
    {
      FFSDebug.log(str1 + " start ", 6);
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localCutOffInfo1 = DBInstructionType.getCutOffById(localFFSConnectionHolder, paramCutOffInfo);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + " end ", 6);
      CutOffInfo localCutOffInfo2 = localCutOffInfo1;
      return localCutOffInfo2;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public CutOffInfoList getCutOffList(CutOffInfoList paramCutOffInfoList)
    throws FFSException
  {
    String str = "Scheduler.getCutOffList :";
    CutOffInfoList localCutOffInfoList = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      FFSDebug.log(str, "start ", 6);
      if (paramCutOffInfoList == null)
      {
        localCutOffInfoList = new CutOffInfoList();
        localCutOffInfoList.setStatusCode(16000);
        localObject1 = "CutOffInfoList  is null";
        localCutOffInfoList.setStatusMsg((String)localObject1);
        FFSDebug.log(str + (String)localObject1, 0);
        localObject2 = localCutOffInfoList;
        return localObject2;
      }
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localCutOffInfoList = DBInstructionType.getCutOffListByFIIDInstStatus(localFFSConnectionHolder, paramCutOffInfoList);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str + " end ", 6);
      Object localObject1 = localCutOffInfoList;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject2 = "*** DBInstructionType.getCutOffList failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2);
      throw new FFSException((String)localObject2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public ScheduleCategoryInfo getScheduleCategoryInfo(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "Scheduler.getScheduleCategoryInfo :";
    FFSDebug.log(str1, "start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    ScheduleCategoryInfo localScheduleCategoryInfo1 = new ScheduleCategoryInfo();
    try
    {
      if ((paramString2 == null) || (paramString2.trim().equals("")))
      {
        localScheduleCategoryInfo1.setStatusCode(26015);
        localScheduleCategoryInfo1.setStatusMsg("InstructionType for payment model does not exist");
        FFSDebug.log(str1 + "InstructionType for payment model does not exist", 0);
        localScheduleCategoryInfo2 = localScheduleCategoryInfo1;
        return localScheduleCategoryInfo2;
      }
      if ((paramString1 == null) || (paramString1.trim().equals("")))
      {
        localScheduleCategoryInfo1.setStatusCode(23170);
        localScheduleCategoryInfo1.setStatusMsg("FIId does not exist :");
        FFSDebug.log(str1 + "FIId does not exist :", 0);
        localScheduleCategoryInfo2 = localScheduleCategoryInfo1;
        return localScheduleCategoryInfo2;
      }
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localScheduleCategoryInfo1 = DBInstructionType.getScheduleCategoryInfo(localFFSConnectionHolder, paramString1, paramString2);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + " end ", 6);
      ScheduleCategoryInfo localScheduleCategoryInfo2 = localScheduleCategoryInfo1;
      return localScheduleCategoryInfo2;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public ScheduleCategoryInfo modScheduleCategoryInfo(ScheduleCategoryInfo paramScheduleCategoryInfo)
    throws FFSException
  {
    String str = "Scheduler.modScheduleCategoryInfo :";
    FFSDebug.log(str, "start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      Object localObject1;
      if (paramScheduleCategoryInfo == null)
      {
        paramScheduleCategoryInfo = new ScheduleCategoryInfo();
        paramScheduleCategoryInfo.setStatusCode(16000);
        localObject1 = "ScheduleCategoryInfo  is null";
        paramScheduleCategoryInfo.setStatusMsg((String)localObject1);
        FFSDebug.log(str + (String)localObject1, 0);
        localObject2 = paramScheduleCategoryInfo;
        return localObject2;
      }
      if ((paramScheduleCategoryInfo.getCategory() == null) || (paramScheduleCategoryInfo.getCategory().trim().equals("")))
      {
        paramScheduleCategoryInfo.setStatusCode(26015);
        paramScheduleCategoryInfo.setStatusMsg("InstructionType for payment model does not exist");
        FFSDebug.log(str + "InstructionType for payment model does not exist", 0);
        localObject1 = paramScheduleCategoryInfo;
        return localObject1;
      }
      if ((paramScheduleCategoryInfo.getFIId() == null) || (paramScheduleCategoryInfo.getFIId().trim().equals("")))
      {
        paramScheduleCategoryInfo.setStatusCode(23170);
        paramScheduleCategoryInfo.setStatusMsg("FIId does not exist :");
        FFSDebug.log(str + "FIId does not exist :", 0);
        localObject1 = paramScheduleCategoryInfo;
        return localObject1;
      }
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      paramScheduleCategoryInfo = DBInstructionType.modScheduleCategoryInfo(localFFSConnectionHolder, paramScheduleCategoryInfo);
      if (paramScheduleCategoryInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
      }
      else
      {
        for (int i = 0; i < paramScheduleCategoryInfo.getCutOffInfoList().length; i++) {
          if (paramScheduleCategoryInfo.getCutOffInfoList()[i].getStatusCode() == 0) {
            jdField_if(paramScheduleCategoryInfo.getCutOffInfoList()[i].getFIId(), paramScheduleCategoryInfo.getCutOffInfoList()[i].getInstructionType());
          }
        }
        localFFSConnectionHolder.conn.commit();
      }
      FFSDebug.log(str, "end ", 6);
      ScheduleCategoryInfo localScheduleCategoryInfo = paramScheduleCategoryInfo;
      return localScheduleCategoryInfo;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject2 = str + "failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2);
      throw new FFSException((String)localObject2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  private void jdField_if(String paramString1, String paramString2)
    throws FFSException
  {
    boolean bool = true;
    InstructionType localInstructionType = null;
    try
    {
      localInstructionType = getInstructionType(paramString2, paramString1);
      if (localInstructionType == null)
      {
        String str1 = "Unable get Schedule for FIID:" + paramString1 + " and Instructuction Type:" + paramString2;
        FFSDebug.log(str1, 0);
        throw new FFSException(str1);
      }
      updateSchedule(localInstructionType, bool);
    }
    catch (Exception localException)
    {
      String str2 = "Unable to refesh schedule..Please restart the server";
      FFSDebug.log(localException, str2, 0);
      throw new FFSException(localException.getMessage());
    }
  }
  
  public CutOffInfo getNextCutOffTimeTX(FFSConnectionHolder paramFFSConnectionHolder, InstructionType paramInstructionType)
    throws FFSException
  {
    String str1 = "Scheduler.getNextCutOffTimeTX :";
    CutOffInfo localCutOffInfo = null;
    FFSDebug.log(str1 + "start ", 6);
    try
    {
      localCutOffInfo = DBInstructionType.getNextCutOffTime(paramFFSConnectionHolder, paramInstructionType);
      FFSDebug.log(str1 + " end ", 6);
      return localCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public CutOffInfo getNextCutOffTime(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "Scheduler.getNextCutOffTime :";
    FFSDebug.log(str1, "start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    CutOffInfo localCutOffInfo1 = null;
    InstructionType localInstructionType = null;
    try
    {
      FFSDebug.log(str1 + "start ", 6);
      localInstructionType = getInstructionType(paramString2, paramString1);
      FFSDebug.log(str1 + "end ", 6);
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localCutOffInfo1 = getNextCutOffTimeTX(localFFSConnectionHolder, localInstructionType);
      localFFSConnectionHolder.conn.commit();
      CutOffInfo localCutOffInfo2 = localCutOffInfo1;
      return localCutOffInfo2;
    }
    catch (Exception localException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = "*** DBInstructionType.getNextCutOffTime failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public CutOffInfo setLastProcessTime(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "Scheduler.setLastProcessTime :";
    FFSDebug.log(str1, "start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    CutOffInfo localCutOffInfo1 = null;
    try
    {
      FFSDebug.log(str1 + "start ", 6);
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localCutOffInfo1 = DBInstructionType.setLastProcessTime(localFFSConnectionHolder, paramCutOffInfo);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + "end ", 6);
      CutOffInfo localCutOffInfo2 = localCutOffInfo1;
      return localCutOffInfo2;
    }
    catch (Exception localException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = "*** " + str1 + " :" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public InstructionType[] getScheduleConfigByCategory(InstructionType paramInstructionType)
    throws FFSException
  {
    String str = "Scheduler.getScheduleConfigByCategory: ";
    FFSDebug.log(str + "start ", 6);
    InstructionType[] arrayOfInstructionType = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      if (paramInstructionType == null)
      {
        arrayOfInstructionType = new InstructionType[1];
        arrayOfInstructionType[0] = new InstructionType();
        arrayOfInstructionType[0].setStatusCode(16000);
        localObject1 = "InstructionType  is null";
        arrayOfInstructionType[0].setStatusMsg((String)localObject1);
        FFSDebug.log(str + (String)localObject1, 0);
        localObject2 = arrayOfInstructionType;
        return localObject2;
      }
      if ((paramInstructionType.category == null) || (paramInstructionType.category.trim().equals("")))
      {
        arrayOfInstructionType = new InstructionType[1];
        arrayOfInstructionType[0] = paramInstructionType;
        arrayOfInstructionType[0].setStatusCode(26015);
        arrayOfInstructionType[0].setStatusMsg("InstructionType for payment model does not exist");
        FFSDebug.log(str + "InstructionType for payment model does not exist", 0);
        localObject1 = arrayOfInstructionType;
        return localObject1;
      }
      if ((paramInstructionType.FIId == null) || (paramInstructionType.FIId.trim().equals("")))
      {
        arrayOfInstructionType = new InstructionType[1];
        arrayOfInstructionType[0] = paramInstructionType;
        arrayOfInstructionType[0].setStatusCode(23170);
        arrayOfInstructionType[0].setStatusMsg("FIId does not exist :");
        FFSDebug.log(str + "FIId does not exist :", 0);
        localObject1 = arrayOfInstructionType;
        return localObject1;
      }
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfInstructionType = (InstructionType[])DBInstructionType.readInstructionTypesByCategory(localFFSConnectionHolder, paramInstructionType.FIId, paramInstructionType.category).toArray(new InstructionType[0]);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str + " end ", 6);
      Object localObject1 = arrayOfInstructionType;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject2 = str + "failed " + localThrowable.toString();
      FFSDebug.log((String)localObject2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
  }
  
  public CutOffActivityInfoList getCutOffActivityList(CutOffActivityInfoList paramCutOffActivityInfoList)
    throws FFSException
  {
    String str1 = "Scheduler.getCutOffActivityList :";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramCutOffActivityInfoList = DBInstructionType.getCutOffActivityList(localFFSConnectionHolder, paramCutOffActivityInfoList);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
    FFSDebug.log(str1 + "end, status =" + paramCutOffActivityInfoList.getStatusMsg(), 6);
    return paramCutOffActivityInfoList;
  }
  
  public ScheduleActivityList getScheduleActivityList(ScheduleActivityList paramScheduleActivityList)
    throws FFSException
  {
    String str1 = "Scheduler.getScheduleActivityList :";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramScheduleActivityList = DBInstructionType.getScheduleActivityList(localFFSConnectionHolder, paramScheduleActivityList);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
    FFSDebug.log(str1 + "end, status =" + paramScheduleActivityList.getStatusMsg(), 6);
    return paramScheduleActivityList;
  }
  
  public String getGeneratedFileName(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "Scheduler.getGeneratedFileName :";
    FFSDebug.log(str1 + "start", 6);
    String str2 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      str2 = DBInstructionType.getGeneratedFileName(localFFSConnectionHolder, paramString1, paramString2, paramString3, this.e);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      str3 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str3 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str3);
      throw new FFSException(str3);
    }
    finally
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null))
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
        localFFSConnectionHolder = null;
      }
    }
    FFSDebug.log(str1 + "end, fileName =" + str2, 6);
    return str2;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.Scheduler
 * JD-Core Version:    0.7.0.1
 */