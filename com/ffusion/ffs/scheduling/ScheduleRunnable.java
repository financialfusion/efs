package com.ffusion.ffs.scheduling;

import com.ffusion.ffs.bpw.db.DBInstructionType;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleCalloutBase;
import com.ffusion.ffs.bpw.interfaces.ScheduleCalloutInfo;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.bpw.interfaces.ScheduleStatus;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.scheduling.db.InstructionTypeStatus;
import com.ffusion.ffs.scheduling.db.ScheduleHistory;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.scheduling.overlapMonitor.OverlapMonitor;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.lang.reflect.Method;
import java.util.HashSet;

public class ScheduleRunnable
{
  private PropertyConfig jdField_else = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  private FFSConnectionHolder jdField_case = new FFSConnectionHolder();
  private String jdField_char;
  private String jdField_try;
  private int jdField_if;
  private OverlapMonitor jdField_for;
  private String jdField_null;
  private HashSet jdField_do;
  private String jdField_long;
  private Class jdField_new;
  private Method b;
  private Method jdField_int;
  String jdField_goto;
  ScheduleCalloutBase a;
  ScheduleCalloutInfo jdField_void;
  boolean jdField_byte;
  
  public ScheduleRunnable(String paramString1, String paramString2, ScheduleCalloutInfo paramScheduleCalloutInfo, boolean paramBoolean)
    throws Exception
  {
    this.jdField_char = paramString1;
    this.jdField_try = paramString2;
    InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(this.jdField_char, this.jdField_try);
    if (localInstructionType == null) {
      throw new Exception("*** The instruction type " + this.jdField_try + " (FI Id=" + this.jdField_char + ")" + " cannot be found in the registry.");
    }
    this.jdField_byte = localInstructionType.createEmptyFile;
    this.jdField_if = localInstructionType.FileBasedRecovery;
    this.jdField_long = localInstructionType.HandlerClassName;
    this.jdField_new = Class.forName(this.jdField_long);
    this.b = this.jdField_new.getMethod("eventHandler", new Class[] { Integer.TYPE, EventInfoArray.class, FFSConnectionHolder.class });
    this.jdField_int = this.jdField_new.getMethod("resubmitEventHandler", new Class[] { Integer.TYPE, EventInfoArray.class, FFSConnectionHolder.class });
    this.jdField_goto = "com.ffusion.ffs.bpw.custimpl.ScheduleCalloutHandler";
    Class localClass = Class.forName(this.jdField_goto);
    this.a = ((ScheduleCalloutBase)localClass.newInstance());
    this.jdField_void = paramScheduleCalloutInfo;
    this.jdField_null = localInstructionType.category;
    this.jdField_for = OverlapMonitor.getInstance();
    this.jdField_do = new HashSet();
    this.jdField_do.add(this.jdField_null);
    this.jdField_for.register(this.jdField_char, this.jdField_null, this.jdField_do);
    if (paramBoolean == true) {
      try
      {
        this.jdField_case.conn = DBUtil.getConnection();
        ScheduleInfo.updateAllWorkInstanceDate(this.jdField_case, this.jdField_try, this.jdField_char, this.jdField_null);
      }
      finally
      {
        DBUtil.freeConnection(this.jdField_case.conn);
      }
    }
  }
  
  public ScheduleStatus execute(ScheduleTimer paramScheduleTimer)
  {
    FFSDebug.log("--- ScheduleRunnable.execute: begin: " + this.jdField_try + " (FI Id=" + this.jdField_char + ")", 6);
    ScheduleStatus localScheduleStatus = null;
    boolean bool1 = false;
    String str1 = null;
    String str2 = null;
    String str5;
    try
    {
      this.jdField_void.clear();
      this.a.eventSchedulePreProcess(this.jdField_void);
      bool1 = this.jdField_for.acquireRunToken(this.jdField_char, this.jdField_try, this.jdField_null, paramScheduleTimer.getRunTokenTimeoutMode(), paramScheduleTimer.getRunTokenTimeout());
      if (bool1 == true) {
        try
        {
          if (paramScheduleTimer.getKeepRunning() == true)
          {
            FFSDebug.log("ScheduleRunnable: Schedule for " + this.jdField_try + " (FI Id=" + this.jdField_char + ")" + " started.");
            boolean bool2 = false;
            str4 = jdField_try();
            if (str4.equals("DispatcherStarted"))
            {
              bool2 = true;
              InstructionTypeStatus localInstructionTypeStatus = jdField_new();
              if ((localInstructionTypeStatus == null) || (localInstructionTypeStatus.ProcessId == null))
              {
                jdField_for();
                localScheduleStatus = new ScheduleStatus(1, 1000, "Unable to recover because SCH_ScheduleHistory table is corrupted. ");
                localObject1 = localScheduleStatus;
                return localObject1;
              }
              str1 = localInstructionTypeStatus.ProcessId;
              str2 = localInstructionTypeStatus.CutOffId;
            }
            else
            {
              str1 = a();
              str2 = null;
              if (paramScheduleTimer.getCutOffInfo() != null) {
                str2 = paramScheduleTimer.getCutOffInfo().getCutOffId();
              }
            }
            this.jdField_void.clear();
            this.a.eventScheduleProcessingBegin(this.jdField_void);
            a(str2, str1, "ProcessingStart", paramScheduleTimer.getEventTrigger(), null);
            batchRun(bool2, str2, str1);
            a(str2, str1, "ProcessingStop", paramScheduleTimer.getEventTrigger(), null);
            this.jdField_void.clear();
            this.a.eventScheduleProcessingEnd(this.jdField_void);
            localScheduleStatus = new ScheduleStatus(0);
            FFSDebug.log("ScheduleRunnable: Schedule for " + this.jdField_try + " (FI Id=" + this.jdField_char + ")" + " done.");
          }
          else
          {
            localScheduleStatus = new ScheduleStatus(1, 1000, "Unable to run because ScheduleRunnable was shutting down.");
          }
        }
        catch (Throwable localThrowable1)
        {
          Object localObject1;
          FFSDebug.log("ScheduleRunnable: Schedule for " + this.jdField_try + " (FI Id=" + this.jdField_char + ")" + " failed!");
          String str4 = FFSDebug.stackTrace(localThrowable1);
          FFSDebug.console("*** ScheduleRunnable.execute: failed:" + this.jdField_try + " (FI Id=" + this.jdField_char + "): " + str4);
          FFSDebug.log("*** ScheduleRunnable.execute: failed:" + this.jdField_try + " (FI Id=" + this.jdField_char + "): " + str4);
          this.jdField_void.clear();
          this.jdField_void.put("ErrorThrowable", localThrowable1);
          try
          {
            this.a.eventScheduleProcessingError(this.jdField_void);
          }
          catch (Throwable localThrowable5)
          {
            localObject1 = FFSDebug.stackTrace(localThrowable5);
            FFSDebug.log("*** ScheduleRunnable.run: processErrorCallout.invoke exception:" + (String)localObject1, 0);
          }
          localScheduleStatus = new ScheduleStatus(1, 3, "Exception: " + localThrowable1.getMessage());
        }
        finally
        {
          this.jdField_for.releaseRunToken(this.jdField_char, this.jdField_null);
          bool1 = false;
        }
      } else {
        localScheduleStatus = new ScheduleStatus(1, 2, "SchedulerRunnable was unable to obtain a RunToken for " + this.jdField_try + " (FI Id=" + this.jdField_char + ")");
      }
    }
    catch (Throwable localThrowable2)
    {
      this.jdField_void.clear();
      this.jdField_void.put("ErrorThrowable", localThrowable2);
      try
      {
        this.a.eventScheduleProcessingError(this.jdField_void);
      }
      catch (Throwable localThrowable4)
      {
        String str6 = FFSDebug.stackTrace(localThrowable4);
        FFSDebug.log("*** ScheduleRunnable.execute: processErrorCallout.invoke exception:" + str6, 0);
      }
      localScheduleStatus = new ScheduleStatus(1, 3, "Exception: " + localThrowable2.getMessage());
      str5 = FFSDebug.stackTrace(localThrowable2);
      FFSDebug.console("*** ScheduleRunnable.execute: failed:" + str5);
      FFSDebug.log("*** ScheduleRunnable.execute: failed:" + str5);
    }
    this.jdField_void.clear();
    try
    {
      this.a.eventSchedulePostProcess(this.jdField_void);
    }
    catch (Throwable localThrowable3)
    {
      str5 = FFSDebug.stackTrace(localThrowable3);
      FFSDebug.log("*** ScheduleRunnable.execute: postProcess.invoke exception:" + str5, 0);
    }
    if ((localScheduleStatus != null) && (localScheduleStatus.runStatus != 0))
    {
      String str3 = "Error :" + localScheduleStatus.errorCode + ":" + localScheduleStatus.errorMsg;
      a(str2, str1, "Exception", paramScheduleTimer.getEventTrigger(), str3);
    }
    FFSDebug.log("--- ScheduleRunnable.execute: end: " + this.jdField_try + " (FI Id=" + this.jdField_char + ")", 6);
    return localScheduleStatus;
  }
  
  public void batchRun(boolean paramBoolean, String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("ScheduleRunnable: Internal processing for " + this.jdField_try + " (FI Id=" + this.jdField_char + ")" + " started.");
    FFSDebug.log("### ScheduleRunnable.batchRun: InstructionType=" + this.jdField_try + ",FileBasedRecovery=" + this.jdField_if + ", execute begin", 6);
    int i = jdField_int();
    jdField_if();
    try
    {
      Method localMethod = a(paramBoolean);
      InstructionTypeStatus.updateStatus(this.jdField_case, this.jdField_char, this.jdField_try, "DispatcherStarted", paramString1, paramString2);
      this.jdField_case.conn.commit();
      a(paramBoolean, paramString1, paramString2, localMethod);
      InstructionTypeStatus.updateStatus(this.jdField_case, this.jdField_char, this.jdField_try, "DispatcherCleanup");
      this.jdField_case.conn.commit();
      a(this.jdField_case);
      InstructionTypeStatus.updateStatus(this.jdField_case, this.jdField_char, this.jdField_try, "DispatcherCompleted");
      this.jdField_case.conn.commit();
    }
    catch (Exception localException)
    {
      if (this.jdField_case.conn != null) {
        this.jdField_case.conn.rollback();
      }
      FFSDebug.log(localException, "*** ScheduleRunnable.batchRun failed:");
      FFSDebug.console("*** ScheduleRunnable.batchRun failed:" + FFSDebug.stackTrace(localException));
      throw localException;
    }
    catch (Throwable localThrowable)
    {
      if (this.jdField_case.conn != null) {
        this.jdField_case.conn.rollback();
      }
      String str = FFSDebug.stackTrace(localThrowable);
      FFSDebug.console("*** ScheduleRunnable.batchRun: failed:" + str);
      FFSDebug.log("*** ScheduleRunnable.batchRun: failed:" + str);
      throw new Exception(localThrowable.toString());
    }
    finally
    {
      DBUtil.setTransactionIsolation(this.jdField_case, i);
      DBUtil.freeConnection(this.jdField_case.conn);
    }
    FFSDebug.log("### ScheduleRunnable.batchRun: InstructionType=" + this.jdField_try + ", execute end", 6);
  }
  
  private Method a(boolean paramBoolean)
  {
    Method localMethod = paramBoolean ? this.jdField_int : this.b;
    return localMethod;
  }
  
  private void a(boolean paramBoolean, String paramString1, String paramString2, Method paramMethod)
    throws Exception
  {
    Object localObject = this.jdField_new.newInstance();
    EventInfoArray localEventInfoArray = a(paramString1, paramString2);
    paramMethod.invoke(localObject, new Object[] { new Integer(0), localEventInfoArray, this.jdField_case });
    if (paramBoolean) {
      a("Processing", true, localObject, this.jdField_case, paramString1, paramString2);
    }
    a("Active", false, localObject, this.jdField_case, paramString1, paramString2);
    paramMethod.invoke(localObject, new Object[] { new Integer(2), localEventInfoArray, this.jdField_case });
  }
  
  private EventInfoArray a(String paramString1, String paramString2)
  {
    EventInfoArray localEventInfoArray = new EventInfoArray();
    localEventInfoArray._array = new EventInfo[1];
    localEventInfoArray._array[0] = new EventInfo();
    localEventInfoArray._array[0].ScheduleFlag = 0;
    localEventInfoArray._array[0].InstructionID = "";
    localEventInfoArray._array[0].FIId = this.jdField_char;
    localEventInfoArray._array[0].InstructionType = this.jdField_try;
    localEventInfoArray._array[0].cutOffId = paramString1;
    localEventInfoArray._array[0].processId = paramString2;
    localEventInfoArray._array[0].createEmptyFile = this.jdField_byte;
    localEventInfoArray._array[0].fileBasedRecovery = this.jdField_if;
    return localEventInfoArray;
  }
  
  private void jdField_if()
    throws Exception
  {
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    boolean bool = this.jdField_else.getSupportCluster();
    try
    {
      if ((localScheduler == null) || ((bool) && (!localScheduler.checkSchedulerLock())) || ((!bool) && (!localScheduler.ownsLock())))
      {
        FFSDebug.log("--- ScheduleRunnable: schedule " + this.jdField_try + " (FI Id=" + this.jdField_char + ") will not run.  The scheduler" + " is not running on this server", 6);
        throw new Exception("*** ScheduleRunnable.batchRun:  The scheduler is not running on this server.");
      }
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      FFSDebug.log("*** ScheduleRunnable.batchRun: Check scheduler lock exception:" + str, 6);
      throw localException;
    }
  }
  
  private int jdField_int()
    throws Exception
  {
    int i = 2;
    try
    {
      this.jdField_case.conn = DBUtil.getValidConnection();
      i = DBUtil.getTransactionIsolation(this.jdField_case);
      DBUtil.setTransactionIsolation(this.jdField_case, 2);
    }
    catch (BPWException localBPWException)
    {
      FFSDebug.log("*** ScheduleRunnable.batchRun:Can not get DB Connection.");
      throw new Exception("*** ScheduleRunnable.batchRun:Can not get DB Connection.");
    }
    return i;
  }
  
  private void a(String paramString1, boolean paramBoolean, Object paramObject, FFSConnectionHolder paramFFSConnectionHolder, String paramString2, String paramString3)
    throws Exception
  {
    FFSDebug.log("ScheduleRunnable.eventExecute: InstructionType=" + this.jdField_try + ",retrieveStatus=" + paramString1, 6);
    Method localMethod = a(paramBoolean);
    EventInfo[] arrayOfEventInfo = null;
    ScheduleInfo[] arrayOfScheduleInfo = null;
    try
    {
      arrayOfScheduleInfo = ScheduleInfo.retrieveScheduleInfo(paramFFSConnectionHolder, this.jdField_char, this.jdField_try, paramString1);
      if (arrayOfScheduleInfo != null)
      {
        EventInfoArray localEventInfoArray1 = a(paramString2, paramString3);
        EventInfoArray localEventInfoArray2 = new EventInfoArray();
        while ((arrayOfScheduleInfo != null) && (arrayOfScheduleInfo.length > 0))
        {
          arrayOfEventInfo = new EventInfo[arrayOfScheduleInfo.length];
          try
          {
            for (int i = 0; i < arrayOfScheduleInfo.length; i++)
            {
              if (!paramBoolean) {
                ScheduleInfo.scheduleRunUpdate(paramFFSConnectionHolder, arrayOfScheduleInfo[i]);
              }
              arrayOfEventInfo[i] = a(paramString2, paramString3, arrayOfScheduleInfo[i]);
            }
            paramFFSConnectionHolder.conn.commit();
          }
          catch (Exception localException2)
          {
            paramFFSConnectionHolder.conn.rollback();
            FFSDebug.console("*** ScheduleRunnable.eventExecute failed: " + FFSDebug.stackTrace(localException2));
            FFSDebug.log(localException2, "*** ScheduleRunnable.eventExecute failed:");
            throw localException2;
          }
          localEventInfoArray2._array = arrayOfEventInfo;
          localMethod.invoke(paramObject, new Object[] { new Integer(3), localEventInfoArray1, paramFFSConnectionHolder });
          localMethod.invoke(paramObject, new Object[] { new Integer(1), localEventInfoArray2, paramFFSConnectionHolder });
          localMethod.invoke(paramObject, new Object[] { new Integer(4), localEventInfoArray1, paramFFSConnectionHolder });
          try
          {
            for (int j = 0; j < arrayOfEventInfo.length; j++)
            {
              EventInfoLog.writeEventInfoLog(paramFFSConnectionHolder, arrayOfEventInfo[j]);
              if (!jdField_do()) {
                ScheduleInfo.updateStatusByScheduleID(paramFFSConnectionHolder, arrayOfEventInfo[j].ScheduleID, "Processed");
              }
            }
            paramFFSConnectionHolder.conn.commit();
          }
          catch (Exception localException3)
          {
            paramFFSConnectionHolder.conn.rollback();
            FFSDebug.console("*** ScheduleRunnable.eventExecute failed: " + FFSDebug.stackTrace(localException3));
            FFSDebug.log(localException3, "*** ScheduleRunnable.eventExecute failed:");
            throw localException3;
          }
          if (ScheduleInfo.isBatchDone(this.jdField_char, this.jdField_try)) {
            arrayOfScheduleInfo = new ScheduleInfo[0];
          } else {
            arrayOfScheduleInfo = ScheduleInfo.retrieveScheduleInfo(paramFFSConnectionHolder, this.jdField_char, this.jdField_try, paramString1);
          }
        }
      }
    }
    catch (Exception localException1)
    {
      paramFFSConnectionHolder.conn.rollback();
      FFSDebug.log(localException1, "*** ScheduleRunnable.eventExecute failed:");
      FFSDebug.console("*** ScheduleRunnable.eventExecute failed:" + FFSDebug.stackTrace(localException1));
      throw localException1;
    }
    finally
    {
      ScheduleInfo.clearBatch(this.jdField_char, this.jdField_try);
    }
  }
  
  private boolean jdField_do()
  {
    return this.jdField_if == 1;
  }
  
  private EventInfo a(String paramString1, String paramString2, ScheduleInfo paramScheduleInfo)
    throws BPWException
  {
    EventInfo localEventInfo = new EventInfo();
    localEventInfo.EventID = DBUtil.getNextIndexString("EventID");
    localEventInfo.ScheduleID = paramScheduleInfo.ScheduleID;
    localEventInfo.InstructionID = paramScheduleInfo.InstructionID;
    localEventInfo.FIId = paramScheduleInfo.FIId;
    localEventInfo.InstructionType = paramScheduleInfo.InstructionType;
    localEventInfo.Status = paramScheduleInfo.Status;
    localEventInfo.ScheduleFlag = 0;
    localEventInfo.LogID = paramScheduleInfo.LogID;
    localEventInfo.cutOffId = paramString1;
    localEventInfo.processId = paramString2;
    localEventInfo.createEmptyFile = this.jdField_byte;
    localEventInfo.fileBasedRecovery = this.jdField_if;
    return localEventInfo;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ScheduleRunnable: Internal clean-up for " + this.jdField_try + " (FI Id=" + this.jdField_char + ")" + " started.");
    FFSDebug.log("ScheduleRunnable.eventClean: InstructionType=" + this.jdField_try, 6);
    try
    {
      String str = jdField_do() ? "Processing" : "Processed";
      ScheduleInfo.deleteByStatusOption(paramFFSConnectionHolder, this.jdField_char, this.jdField_try, str, 1);
      ScheduleInfo.updateStatusByStatus(paramFFSConnectionHolder, this.jdField_char, this.jdField_try, str, "Active");
      a("Submitted", this.jdField_case);
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      FFSDebug.console("*** ScheduleRunnable.eventClean failed:" + FFSDebug.stackTrace(localException));
      FFSDebug.log(localException, "*** ScheduleRunnable.eventClean failed:");
      throw new Exception(localException.toString());
    }
  }
  
  private synchronized void a(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ScheduleRunnable.evt_clean: InstructionType=" + this.jdField_try, 6);
    EventInfo[] arrayOfEventInfo = null;
    try
    {
      arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, paramString, this.jdField_char, this.jdField_try);
      if (arrayOfEventInfo != null) {
        while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
        {
          int i = arrayOfEventInfo.length;
          for (int j = 0; j < i; j++) {
            try
            {
              EventInfo.cancelEvent(paramFFSConnectionHolder, arrayOfEventInfo[j].EventID);
            }
            catch (Exception localException2)
            {
              paramFFSConnectionHolder.conn.rollback();
              FFSDebug.console("*** ScheduleRunnable.evt_clean failed: " + FFSDebug.stackTrace(localException2));
              FFSDebug.log(localException2, "*** ScheduleRunnable.evt_clean failed:");
              throw new Exception(localException2.toString());
            }
          }
          paramFFSConnectionHolder.conn.commit();
          if (EventInfo.isBatchDone(this.jdField_char, this.jdField_try)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, paramString, this.jdField_char, this.jdField_try);
          }
        }
      }
    }
    catch (Exception localException1)
    {
      paramFFSConnectionHolder.conn.rollback();
      FFSDebug.log(localException1, "*** ScheduleRunnable.evt_clean failed:");
      FFSDebug.console("*** ScheduleRunnable.evt_clean failed:" + FFSDebug.stackTrace(localException1));
      throw new Exception(localException1.toString());
    }
    finally
    {
      EventInfo.clearBatch(this.jdField_char, this.jdField_try);
    }
  }
  
  public void batchClean()
    throws Exception
  {
    FFSDebug.log("ScheduleRunnable.batchClean: InstructionType=" + this.jdField_try, 6);
    int i = 2;
    try
    {
      this.jdField_case.conn = DBUtil.getValidConnection();
      i = DBUtil.getTransactionIsolation(this.jdField_case);
      DBUtil.setTransactionIsolation(this.jdField_case, 2);
    }
    catch (BPWException localBPWException)
    {
      FFSDebug.log("*** ScheduleRunnable.batchClean: Can not get DB Connection.");
      throw new Exception("*** ScheduleRunnable.batchClean: Can not get DB Connection.");
    }
    try
    {
      a(this.jdField_case);
      InstructionTypeStatus.updateStatus(this.jdField_case, this.jdField_char, this.jdField_try, "DispatcherCompleted");
      this.jdField_case.conn.commit();
    }
    catch (Exception localException)
    {
      this.jdField_case.conn.rollback();
      FFSDebug.log(localException, "*** ScheduleRunnable.batchClean failed:");
      FFSDebug.console("*** ScheduleRunnable.batchClean failed:" + FFSDebug.stackTrace(localException));
      throw new Exception(localException.toString());
    }
    catch (Throwable localThrowable)
    {
      String str = FFSDebug.stackTrace(localThrowable);
      FFSDebug.console("*** ScheduleRunnable.batchClean: failed:" + str);
      FFSDebug.log("*** ScheduleRunnable.batchClean: failed:" + str);
      throw new Exception(localThrowable.toString());
    }
    finally
    {
      DBUtil.setTransactionIsolation(this.jdField_case, i);
      DBUtil.freeConnection(this.jdField_case.conn);
    }
  }
  
  public Method getResubmitEventCallbackMethod()
  {
    return this.jdField_int;
  }
  
  private String jdField_try()
    throws Exception
  {
    String str = "DispatcherCompleted";
    try
    {
      this.jdField_case.conn = DBUtil.getConnection();
      str = InstructionTypeStatus.getDispatchStatus(this.jdField_char, this.jdField_try, this.jdField_case);
      this.jdField_case.conn.commit();
    }
    catch (Exception localException)
    {
      this.jdField_case.conn.rollback();
      FFSDebug.log("*** ScheduleRunnable.getDispatchStatus:\nError: " + FFSDebug.stackTrace(localException));
    }
    finally
    {
      DBUtil.freeConnection(this.jdField_case.conn);
    }
    return str;
  }
  
  private InstructionTypeStatus jdField_new()
    throws Exception
  {
    InstructionTypeStatus localInstructionTypeStatus = null;
    try
    {
      this.jdField_case.conn = DBUtil.getConnection();
      localInstructionTypeStatus = InstructionTypeStatus.get(this.jdField_char, this.jdField_try, this.jdField_case);
      this.jdField_case.conn.commit();
    }
    catch (Exception localException)
    {
      this.jdField_case.conn.rollback();
      FFSDebug.log("*** ScheduleRunnable.getInstructionStatus:\nError: " + FFSDebug.stackTrace(localException));
    }
    finally
    {
      DBUtil.freeConnection(this.jdField_case.conn);
    }
    return localInstructionTypeStatus;
  }
  
  public void recoveryFromCrash(InstructionTypeStatus paramInstructionTypeStatus)
    throws Exception
  {
    String str1 = "ScheduleRunnable.recoveryFromCrash: " + this.jdField_try + " (FI Id=" + this.jdField_char + "): " + "SchedulerStatus=" + paramInstructionTypeStatus.SchedulerStatus + ", DispatchStatus =" + paramInstructionTypeStatus.DispatchStatus;
    String str2 = null;
    String str3 = null;
    try
    {
      InstructionTypeStatus localInstructionTypeStatus = jdField_new();
      if ((localInstructionTypeStatus == null) || (localInstructionTypeStatus.ProcessId == null))
      {
        jdField_for();
        return;
      }
      str2 = localInstructionTypeStatus.ProcessId;
      str3 = localInstructionTypeStatus.CutOffId;
      this.jdField_void.clear();
      this.a.eventScheduleRecoveryBegin(this.jdField_void);
      a(str3, str2, "RecoveryStart", "Internal", str1);
      if (paramInstructionTypeStatus.SchedulerStatus.equals("DispatcherCleanup")) {
        batchClean();
      }
      if (paramInstructionTypeStatus.DispatchStatus.equals("DispatcherStarted")) {
        batchRun(true, str3, str2);
      }
      a(str3, str2, "RecoveryStop", "Internal", str1);
      this.jdField_void.clear();
      this.a.eventScheduleRecoveryEnd(this.jdField_void);
    }
    catch (Throwable localThrowable1)
    {
      a(str3, str2, "Exception", "Internal", localThrowable1.getMessage());
      this.jdField_void.clear();
      this.jdField_void.put("ErrorThrowable", localThrowable1);
      try
      {
        this.a.eventScheduleRecoveryError(this.jdField_void);
      }
      catch (Throwable localThrowable2)
      {
        String str4 = FFSDebug.stackTrace(localThrowable2);
        FFSDebug.log("*** ScheduleRunnable.recoveryFromCrash: processErrorCallout.invoke exception:" + str4, 0);
      }
      throw new Exception(localThrowable1.getMessage());
    }
  }
  
  private void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    String str = "ScheduleRunnable.logScheduleHist:";
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      FFSDebug.log(str + " start ", 6);
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      ScheduleHist localScheduleHist = new ScheduleHist();
      localObject1 = null;
      localScheduleHist.ScheduleName = this.jdField_try;
      localScheduleHist.InstructionType = this.jdField_try;
      localScheduleHist.FIID = this.jdField_char;
      localScheduleHist.ServerName = this.jdField_else.serverName;
      localScheduleHist.EventType = paramString3;
      localScheduleHist.EventTrigger = paramString4;
      localScheduleHist.EventDescription = paramString5;
      localScheduleHist.ProcessId = paramString2;
      localScheduleHist.CutOffId = paramString1;
      localScheduleHist.CutOffDay = 0;
      localScheduleHist.CutOffProcessTime = null;
      localScheduleHist.CutOffExtension = null;
      if (paramString1 != null)
      {
        localObject1 = new CutOffInfo();
        ((CutOffInfo)localObject1).setCutOffId(paramString1);
        localObject1 = DBInstructionType.getCutOffById(localFFSConnectionHolder, (CutOffInfo)localObject1);
        if ((localObject1 != null) && (((CutOffInfo)localObject1).getStatusCode() == 0))
        {
          localScheduleHist.CutOffDay = ((CutOffInfo)localObject1).getDay();
          localScheduleHist.CutOffProcessTime = ((CutOffInfo)localObject1).getProcessTime();
          localScheduleHist.CutOffExtension = ((CutOffInfo)localObject1).getExtension();
        }
        else
        {
          FFSDebug.log("*** " + str + "CutOffInfo not found: cutoffid:" + paramString1, 0);
        }
      }
      ScheduleHistory.createScheduleHist(localFFSConnectionHolder, localScheduleHist);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject1 = str + "failed " + localThrowable.toString();
      FFSDebug.log((String)localObject1, FFSDebug.stackTrace(localThrowable), 0);
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
  
  private String a()
  {
    String str1 = null;
    try
    {
      str1 = DBUtil.getNextIndexStringWithPadding("ProcessId", 32, '0');
    }
    catch (Exception localException)
    {
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log("*** ScheduleRunnable.getNextProcessId: exception:" + str2, 6);
    }
    return str1;
  }
  
  private void jdField_for()
  {
    FFSDebug.log("*** ScheduleRunnable: Schedule for " + this.jdField_try + " (FI Id=" + this.jdField_char + ")" + " can not be recovered. InstructionTypeStatus table is corrupted. Please contact with Financial Fusion Inc. Customer Service Representative.");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.ScheduleRunnable
 * JD-Core Version:    0.7.0.1
 */