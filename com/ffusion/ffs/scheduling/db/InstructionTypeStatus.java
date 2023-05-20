package com.ffusion.ffs.scheduling.db;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstructionTypeStatus
{
  private static final String a = "SELECT its.FIId, its.InstructionType, its.LastSchedulerTime, its.LastDispatchTime, its.SchedulerStatus, its.DispatchStatus, its.ProcessId FROM SCH_InstructionTypeStatus its, SCH_InstructionType it WHERE it.FIId = its.FIId AND it.InstructionType = its.InstructionType AND it.ActiveFlag = 1 AND NOT DispatchStatus = 'DispatcherCompleted' ";
  public String FIId;
  public String InstructionType;
  public String LastSchedulerTime;
  public String LastDispatchTime;
  public String SchedulerStatus;
  public String DispatchStatus;
  public String CutOffId;
  public String ProcessId;
  
  public InstructionTypeStatus() {}
  
  public InstructionTypeStatus(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.FIId = paramString1;
    this.InstructionType = paramString2;
    this.LastSchedulerTime = paramString3;
    this.LastDispatchTime = paramString4;
    this.SchedulerStatus = paramString5;
    this.DispatchStatus = paramString6;
  }
  
  public InstructionTypeStatus(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    this.FIId = paramString1;
    this.InstructionType = paramString2;
    this.LastSchedulerTime = paramString3;
    this.LastDispatchTime = paramString4;
    this.SchedulerStatus = paramString5;
    this.DispatchStatus = paramString6;
    this.CutOffId = paramString7;
    this.ProcessId = paramString8;
  }
  
  public static InstructionTypeStatus[] getAll(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("InstructionTypeStatus.getAll start", 6);
    String str = "SELECT FIId,InstructionType,LastSchedulerTime,LastDispatchTime,SchedulerStatus,DispatchStatus FROM SCH_InstructionTypeStatus";
    Object[] arrayOfObject = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        InstructionTypeStatus localInstructionTypeStatus = new InstructionTypeStatus();
        localInstructionTypeStatus.FIId = localFFSResultSet.getColumnString(1);
        localInstructionTypeStatus.InstructionType = localFFSResultSet.getColumnString(2);
        localInstructionTypeStatus.LastSchedulerTime = localFFSResultSet.getColumnString(3);
        localInstructionTypeStatus.LastDispatchTime = localFFSResultSet.getColumnString(4);
        localInstructionTypeStatus.SchedulerStatus = localFFSResultSet.getColumnString(5);
        localInstructionTypeStatus.DispatchStatus = localFFSResultSet.getColumnString(6);
        localArrayList.add(localInstructionTypeStatus);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** InstructionTypeStatus.getAll failed:" + localException1.toString());
      throw new FFSException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** InstructionTypeStatus.getAll failed:" + localException2.toString());
      }
    }
    if (localArrayList.isEmpty()) {
      return null;
    }
    FFSDebug.log("InstructionTypeStatus.getAll done, vec.size()=" + localArrayList.size(), 6);
    return (InstructionTypeStatus[])localArrayList.toArray(new InstructionTypeStatus[0]);
  }
  
  public static List getRecoverable(FFSConnection paramFFSConnection)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    FFSDebug.log("InstructionTypeStatus.getRecoverable start", 6);
    try
    {
      localFFSResultSet = paramFFSConnection.prepareStmt("SELECT its.FIId, its.InstructionType, its.LastSchedulerTime, its.LastDispatchTime, its.SchedulerStatus, its.DispatchStatus, its.ProcessId FROM SCH_InstructionTypeStatus its, SCH_InstructionType it WHERE it.FIId = its.FIId AND it.InstructionType = its.InstructionType AND it.ActiveFlag = 1 AND NOT DispatchStatus = 'DispatcherCompleted' ");
      localFFSResultSet.open(null);
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = new InstructionTypeStatus();
        ((InstructionTypeStatus)localObject1).FIId = localFFSResultSet.getColumnString(1);
        ((InstructionTypeStatus)localObject1).InstructionType = localFFSResultSet.getColumnString(2);
        ((InstructionTypeStatus)localObject1).LastSchedulerTime = localFFSResultSet.getColumnString(3);
        ((InstructionTypeStatus)localObject1).LastDispatchTime = localFFSResultSet.getColumnString(4);
        ((InstructionTypeStatus)localObject1).SchedulerStatus = localFFSResultSet.getColumnString(5);
        ((InstructionTypeStatus)localObject1).DispatchStatus = localFFSResultSet.getColumnString(6);
        ((InstructionTypeStatus)localObject1).ProcessId = localFFSResultSet.getColumnString(7);
        localArrayList.add(localObject1);
      }
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** InstructionTypeStatus.getRecoverable failed:" + localException1.toString());
      throw new FFSException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** InstructionTypeStatus.getRecoverable failed:" + localException2.toString());
      }
    }
  }
  
  public static InstructionTypeStatus[] getAll(FFSConnection paramFFSConnection)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("InstructionTypeStatus.getAll start", 6);
    String str = "SELECT FIId,InstructionType,LastSchedulerTime,LastDispatchTime,SchedulerStatus,DispatchStatus FROM SCH_InstructionTypeStatus";
    Object[] arrayOfObject = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = paramFFSConnection.prepareStmt(str);
      localFFSResultSet.open(arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        InstructionTypeStatus localInstructionTypeStatus = new InstructionTypeStatus();
        localInstructionTypeStatus.FIId = localFFSResultSet.getColumnString(1);
        localInstructionTypeStatus.InstructionType = localFFSResultSet.getColumnString(2);
        localInstructionTypeStatus.LastSchedulerTime = localFFSResultSet.getColumnString(3);
        localInstructionTypeStatus.LastDispatchTime = localFFSResultSet.getColumnString(4);
        localInstructionTypeStatus.SchedulerStatus = localFFSResultSet.getColumnString(5);
        localInstructionTypeStatus.DispatchStatus = localFFSResultSet.getColumnString(6);
        localArrayList.add(localInstructionTypeStatus);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** InstructionTypeStatus.getAll failed:" + localException1.toString());
      throw new FFSException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** InstructionTypeStatus.getAll failed:" + localException2.toString());
      }
    }
    if (localArrayList.isEmpty()) {
      return null;
    }
    FFSDebug.log("InstructionTypeStatus.getAll done, vec.size()=" + localArrayList.size(), 6);
    return (InstructionTypeStatus[])localArrayList.toArray(new InstructionTypeStatus[0]);
  }
  
  public static InstructionTypeStatus get(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("InstructionTypeStatus.get start, FIId=" + paramString1 + ",instType=" + paramString2, 6);
    String str = "SELECT InstructionType,LastSchedulerTime,LastDispatchTime,SchedulerStatus,DispatchStatus, CutOffId, ProcessId FROM SCH_InstructionTypeStatus WHERE FIId=? AND InstructionType=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    InstructionTypeStatus localInstructionTypeStatus = null;
    try
    {
      FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localInstructionTypeStatus = new InstructionTypeStatus();
        localInstructionTypeStatus.InstructionType = localFFSResultSet.getColumnString(1);
        localInstructionTypeStatus.LastSchedulerTime = localFFSResultSet.getColumnString(2);
        localInstructionTypeStatus.LastDispatchTime = localFFSResultSet.getColumnString(3);
        localInstructionTypeStatus.SchedulerStatus = localFFSResultSet.getColumnString(4);
        localInstructionTypeStatus.DispatchStatus = localFFSResultSet.getColumnString(5);
        localInstructionTypeStatus.CutOffId = localFFSResultSet.getColumnString("CutOffId");
        localInstructionTypeStatus.ProcessId = localFFSResultSet.getColumnString("ProcessId");
      }
      localFFSResultSet.close();
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** InstructionTypeStatus.get failed:" + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("InstructionTypeStatus.get done", 6);
    return localInstructionTypeStatus;
  }
  
  public static String getSchedulerStatus(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str = "DispatcherCompleted";
    InstructionTypeStatus localInstructionTypeStatus = get(paramString1, paramString2, paramFFSConnectionHolder);
    if ((localInstructionTypeStatus != null) && (localInstructionTypeStatus.SchedulerStatus != null)) {
      str = localInstructionTypeStatus.SchedulerStatus;
    }
    return str;
  }
  
  public static String getDispatchStatus(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str = "DispatcherCompleted";
    InstructionTypeStatus localInstructionTypeStatus = get(paramString1, paramString2, paramFFSConnectionHolder);
    if ((localInstructionTypeStatus != null) && (localInstructionTypeStatus.DispatchStatus != null)) {
      str = localInstructionTypeStatus.DispatchStatus;
    }
    return str;
  }
  
  public static void updateSchedulerStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    FFSDebug.log("InstructionTypeStatus.updateSchedulerStatus start " + paramString2 + " status=" + paramString3, 6);
    String str1 = "UPDATE SCH_InstructionTypeStatus SET LastSchedulerTime=?,SchedulerStatus=? WHERE FIId=? AND InstructionType=?";
    String str2 = "INSERT INTO SCH_InstructionTypeStatus (LastSchedulerTime,SchedulerStatus,FIId,InstructionType) VALUES (?,?,?,?)";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String str3 = localSimpleDateFormat.format(new Date());
    Object[] arrayOfObject = { str3, paramString3, paramString1, paramString2 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      if (i == 0) {
        i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log("*** InstructionTypeStatus.updateSchedulerStatus failed:" + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally {}
    FFSDebug.log("InstructionTypeStatus.updateSchedulerStatus done ", 6);
  }
  
  public static void updateStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    FFSDebug.log("InstructionTypeStatus.updateStatus start " + paramString2 + " status=" + paramString3, 6);
    String str1 = "UPDATE SCH_InstructionTypeStatus SET LastSchedulerTime=?,SchedulerStatus=?,  LastDispatchTime=?,DispatchStatus=? WHERE FIId=? AND InstructionType=?";
    String str2 = "INSERT INTO SCH_InstructionTypeStatus (LastSchedulerTime,SchedulerStatus,LastDispatchTime,DispatchStatus,FIId,InstructionType) VALUES (?,?,?,?,?,?)";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String str3 = localSimpleDateFormat.format(new Date());
    Object[] arrayOfObject = { str3, paramString3, str3, paramString3, paramString1, paramString2 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      if (i == 0) {
        i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** InstructionTypeStatus.updateStatus failed:" + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("InstructionTypeStatus.updateStatus done ", 6);
  }
  
  public static void updateStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws FFSException
  {
    FFSDebug.log("InstructionTypeStatus.updateStatus start " + paramString2 + " status=" + paramString3, 6);
    String str1 = "UPDATE SCH_InstructionTypeStatus SET LastSchedulerTime=?,SchedulerStatus=?,  LastDispatchTime=?,DispatchStatus=?, CutOffId=?, ProcessId=? WHERE FIId=? AND InstructionType=?";
    String str2 = "INSERT INTO SCH_InstructionTypeStatus (LastSchedulerTime,SchedulerStatus,LastDispatchTime,DispatchStatus, CutOffId, ProcessId,FIId,InstructionType) VALUES (?,?,?,?,?,?,?,?)";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String str3 = localSimpleDateFormat.format(new Date());
    Object[] arrayOfObject = { str3, paramString3, str3, paramString3, paramString4, paramString5, paramString1, paramString2 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      if (i == 0) {
        i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** InstructionTypeStatus.updateStatus failed:" + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("InstructionTypeStatus.updateStatus done ", 6);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("InstructionTypeStatus.delete start, FIId=" + paramString1 + ",InstructionType=" + paramString2, 6);
    String str1 = "DELETE FROM SCH_InstructionTypeStatus WHERE FIId=? AND InstructionType=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** InstructionTypeStatus.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("InstructionTypeStatus.delete done", 6);
  }
  
  public static void delete(FFSConnection paramFFSConnection, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("InstructionTypeStatus.delete start, FIId=" + paramString1 + ",InstructionType=" + paramString2, 6);
    String str1 = "DELETE FROM SCH_InstructionTypeStatus WHERE FIId=? AND InstructionType=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    try
    {
      paramFFSConnection.executeCommand(str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** InstructionTypeStatus.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("InstructionTypeStatus.delete done", 6);
  }
  
  public static void initSchedulerStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "InstructionTypeStatus.initSchedulerStatus";
    FFSDebug.log(str1 + " start " + paramString2, 6);
    InstructionTypeStatus localInstructionTypeStatus = get(paramString1, paramString2, paramFFSConnectionHolder);
    if (localInstructionTypeStatus == null)
    {
      String str2 = "INSERT INTO SCH_InstructionTypeStatus (LastSchedulerTime,SchedulerStatus,FIId,InstructionType) VALUES (?,?,?,?)";
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      String str3 = localSimpleDateFormat.format(new Date());
      Object[] arrayOfObject = { str3, "DispatcherCompleted", paramString1, paramString2 };
      try
      {
        DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      }
      catch (Exception localException)
      {
        localException = localException;
        FFSDebug.log("*** " + str1 + " failed:" + localException.toString());
        throw new FFSException(localException.toString());
      }
      finally {}
    }
    FFSDebug.log(str1 + " done ", 6);
  }
  
  public InstructionType getInstructionType()
  {
    return BPWRegistryUtil.getInstructionType(this.FIId, this.InstructionType);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.db.InstructionTypeStatus
 * JD-Core Version:    0.7.0.1
 */