package com.ffusion.ffs.scheduling.db;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Semimonthly;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ScheduleInfo
{
  public String ScheduleID;
  public String FIId;
  public String Status;
  public int StatusOption;
  public int Frequency;
  public int StartDate;
  public String RecordCreateDate;
  public int InstanceCount;
  public int NextInstanceDate;
  public int WorkInstanceDate;
  public int CurInstanceNum;
  public int Perpetual;
  public String InstructionType;
  public String InstructionID;
  public String LogID;
  public static int BATCH_SIZE;
  public static final int DEFAULT_SIZE = 1000;
  public static PropertyConfig propertyConfig = null;
  private static Set jdField_char = Collections.synchronizedSet(new HashSet());
  static String jdField_for = "UPDATE SCH_ScheduleInfo set Status= 'Modifying' WHERE    FIId=?    and InstructionType=?    and InstructionID =?    and Status=    'Active'   and SCH_ScheduleInfo.InstructionType in        (SELECT SCH_ScheduleInfo.InstructionType from            SCH_ScheduleInfo, SCH_InstructionTypeStatus             WHERE  SCH_ScheduleInfo.InstructionType =                SCH_InstructionTypeStatus.InstructionType                AND SchedulerStatus =                'DispatcherCompleted'       )";
  static String jdField_new = "UPDATE SCH_ScheduleInfo set Status= 'Active' WHERE    FIId=?    and InstructionType=?    and InstructionID =?    and Status=    'Modifying'";
  static String jdField_byte = "UPDATE SCH_ScheduleInfo set Status= 'Active' WHERE    Status=    'Modifying'";
  static String jdField_do = "UPDATE SCH_ScheduleInfo set Status= 'Modifying' WHERE    FIId=? and    (        InstructionType=?        and InstructionID =?        and Status=        'Active'       and SCH_ScheduleInfo.InstructionType in        (SELECT SCH_ScheduleInfo.InstructionType from                SCH_ScheduleInfo, SCH_InstructionTypeStatus                 WHERE  SCH_ScheduleInfo.InstructionType =                SCH_InstructionTypeStatus.InstructionType                AND SchedulerStatus =                'DispatcherCompleted'       )   ) or (            InstructionType=?            and InstructionID in ( ";
  static String jdField_if = "                               )           and Status=            'Active'           and SCH_ScheduleInfo.InstructionType in            (SELECT SCH_ScheduleInfo.InstructionType from                SCH_ScheduleInfo, SCH_InstructionTypeStatus                 WHERE  SCH_ScheduleInfo.InstructionType =                SCH_InstructionTypeStatus.InstructionType                AND SchedulerStatus =                'DispatcherCompleted'           )       ) ";
  static String jdField_try = "UPDATE SCH_ScheduleInfo set Status= 'Active' WHERE    FIId=? and    (        InstructionType=?        and InstructionID =?        and Status=        'Modifying'   ) or (            InstructionType=?            and InstructionID in                ( ";
  static String jdField_int = "               )            and Status=            'Modifying'   ) ";
  static String a = "UPDATE SCH_ScheduleInfo set Status= 'Modifying' WHERE    FIId=?    and InstructionType=?    and Status=    'Active'   and SCH_ScheduleInfo.InstructionType in        (SELECT SCH_ScheduleInfo.InstructionType from            SCH_ScheduleInfo, SCH_InstructionTypeStatus             WHERE  SCH_ScheduleInfo.InstructionType =                SCH_InstructionTypeStatus.InstructionType                AND SchedulerStatus =                'DispatcherCompleted'       )   and SCH_ScheduleInfo.InstructionID in    (SELECT BPW_WireInfo.SrvrTID         FROM BPW_WireInfo         WHERE BPW_WireInfo.BatchId = ? AND BPW_WireInfo.Status = 'WILLPROCESSON' )";
  static String jdField_case = "UPDATE SCH_ScheduleInfo set SCH_ScheduleInfo.Status= 'Active' WHERE    FIId=?    and InstructionType=?    and Status=    'Modifying'   and SCH_ScheduleInfo.InstructionID in    (SELECT BPW_WireInfo.SrvrTID         FROM BPW_WireInfo         WHERE BPW_WireInfo.BatchId = ? )";
  
  public ScheduleInfo() {}
  
  public ScheduleInfo(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString5, String paramString6, String paramString7)
  {
    a(paramString1, paramString2, paramString3, 0, paramInt1, paramInt2, paramString4, paramInt3, paramInt4, paramInt5, paramInt6, 0, paramString5, paramString6, paramString7);
  }
  
  public ScheduleInfo(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    a(paramString1, paramString7, paramString2, 0, paramInt1, paramInt2, paramString3, paramInt3, paramInt4, paramInt5, paramInt6, 0, paramString4, paramString5, paramString6);
  }
  
  public ScheduleInfo(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3, String paramString4, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, String paramString5, String paramString6, String paramString7)
  {
    a(paramString1, paramString2, paramString3, paramInt1, paramInt2, paramInt3, paramString4, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramString5, paramString6, paramString7);
  }
  
  private void a(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3, String paramString4, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, String paramString5, String paramString6, String paramString7)
  {
    this.ScheduleID = paramString1;
    this.FIId = paramString2;
    this.Status = paramString3;
    this.StatusOption = paramInt1;
    this.Frequency = paramInt2;
    this.StartDate = paramInt3;
    this.RecordCreateDate = paramString4;
    this.InstanceCount = paramInt4;
    this.NextInstanceDate = paramInt5;
    this.WorkInstanceDate = paramInt6;
    this.CurInstanceNum = paramInt7;
    this.Perpetual = paramInt8;
    this.InstructionType = paramString5;
    this.InstructionID = paramString6;
    this.LogID = paramString7;
  }
  
  public static void setFIIDinStaticScheduleInfo(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "UPDATE SCH_ScheduleInfo SET FIId=? WHERE ScheduleID<'0' AND FIId= 'null'";
    Object[] arrayOfObject = { "1000" };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.setFIIDinStaticScheduleInfo failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
  }
  
  public static ScheduleInfo[] getAllScheduleInfo(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("ScheduleInfo.getAllScheduleInfo: start", 6);
    String str = "SELECT ScheduleID,Status, StatusOption, Frequency,StartDate,RecordCreateDate,InstanceCount,NextInstanceDate,WorkInstanceDate,CurInstanceNum,InstructionType,InstructionID,LogID,Perpetual, FIId FROM SCH_ScheduleInfo";
    Object[] arrayOfObject = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        localScheduleInfo.ScheduleID = localFFSResultSet.getColumnString("ScheduleID");
        localScheduleInfo.Status = localFFSResultSet.getColumnString("Status");
        localScheduleInfo.StatusOption = localFFSResultSet.getColumnInt("StatusOption");
        localScheduleInfo.Frequency = localFFSResultSet.getColumnInt("Frequency");
        localScheduleInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localScheduleInfo.RecordCreateDate = localFFSResultSet.getColumnString("RecordCreateDate");
        localScheduleInfo.InstanceCount = localFFSResultSet.getColumnInt("InstanceCount");
        localScheduleInfo.NextInstanceDate = localFFSResultSet.getColumnInt("NextInstanceDate");
        localScheduleInfo.WorkInstanceDate = localFFSResultSet.getColumnInt("WorkInstanceDate");
        localScheduleInfo.CurInstanceNum = localFFSResultSet.getColumnInt("CurInstanceNum");
        localScheduleInfo.InstructionType = localFFSResultSet.getColumnString("InstructionType");
        localScheduleInfo.InstructionID = localFFSResultSet.getColumnString("InstructionID");
        localScheduleInfo.LogID = localFFSResultSet.getColumnString("LogID");
        localScheduleInfo.Perpetual = localFFSResultSet.getColumnInt("Perpetual");
        localScheduleInfo.FIId = localFFSResultSet.getColumnString("FIId");
        localArrayList.add(localScheduleInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ScheduleInfo.getAllScheduleInfo:" + localException1.toString());
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
        FFSDebug.log("*** ScheduleInfo.getAllScheduleInfo: failed:" + localException2.toString());
      }
    }
    FFSDebug.log("ScheduleInfo.getAllScheduleInfo done, vec.size()=" + localArrayList.size(), 6);
    return localArrayList.isEmpty() ? null : (ScheduleInfo[])localArrayList.toArray(new ScheduleInfo[0]);
  }
  
  public static String createInstTypeSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, ScheduleInfo paramScheduleInfo)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.createSchedule start, instType" + paramString1 + ",instID=" + paramString2, 6);
    String str1 = "INSERT INTO SCH_ScheduleInfo (ScheduleID,Status,StatusOption,Frequency,StartDate,RecordCreateDate,InstanceCount,NextInstanceDate,WorkInstanceDate,CurInstanceNum,InstructionType,InstructionID,LogID,Perpetual, FIId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    int i = paramScheduleInfo.StartDate;
    if (paramScheduleInfo.NextInstanceDate != 0) {
      i = paramScheduleInfo.NextInstanceDate;
    }
    int j;
    if (paramScheduleInfo.WorkInstanceDate != 0)
    {
      j = paramScheduleInfo.WorkInstanceDate;
    }
    else
    {
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if (localScheduler != null) {
        j = DBUtil.getProcessDay(paramFFSConnectionHolder, paramScheduleInfo.FIId, paramString1, i);
      } else {
        j = i;
      }
    }
    int k = 1;
    if (paramScheduleInfo.CurInstanceNum != 0) {
      k = paramScheduleInfo.CurInstanceNum;
    }
    String str2 = paramScheduleInfo.FIId;
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      str3 = "ScheduleInfo.createSchedule: failed. FIID is null";
      FFSDebug.log(str3, 0);
      throw new FFSException(str3);
    }
    String str3 = str2 + "_" + paramString1;
    DateFormat.getDateInstance(2);
    Object[] arrayOfObject = { str3, paramScheduleInfo.Status, new Integer(paramScheduleInfo.StatusOption), new Integer(paramScheduleInfo.Frequency), new Integer(paramScheduleInfo.StartDate), FFSUtil.getDateString(), new Integer(paramScheduleInfo.InstanceCount), new Integer(i), new Integer(j), new Integer(k), paramString1, paramString2, paramScheduleInfo.LogID, new Integer(paramScheduleInfo.Perpetual), str2 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str4 = "*** ScheduleInfo.createSchedule failed:";
      FFSDebug.log(str4 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.createSchedule done", 6);
    return str3;
  }
  
  public static String createSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, ScheduleInfo paramScheduleInfo)
    throws FFSException
  {
    String str = "System";
    return createSchedule(paramFFSConnectionHolder, paramString1, paramString2, paramScheduleInfo, str);
  }
  
  public static String createSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, ScheduleInfo paramScheduleInfo, String paramString3)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.createSchedule start, instType" + paramString1 + ",instID=" + paramString2, 6);
    String str1 = "INSERT INTO SCH_ScheduleInfo (ScheduleID,Status,StatusOption,Frequency,StartDate,RecordCreateDate,InstanceCount,NextInstanceDate,WorkInstanceDate,CurInstanceNum,InstructionType,InstructionID,LogID,Perpetual, FIId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String str2;
    try
    {
      str2 = DBUtil.getNextIndexString("ScheduleID");
    }
    catch (BPWException localBPWException)
    {
      String str3 = "*** ScheduleInfo.createSchedule failed:";
      FFSDebug.log(str3 + localBPWException.toString());
      throw new FFSException(localBPWException.toString());
    }
    int i = paramScheduleInfo.StartDate;
    if (paramScheduleInfo.NextInstanceDate != 0) {
      i = paramScheduleInfo.NextInstanceDate;
    }
    int j;
    if (paramScheduleInfo.WorkInstanceDate != 0)
    {
      j = paramScheduleInfo.WorkInstanceDate;
    }
    else
    {
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if (localScheduler != null) {
        j = DBUtil.getProcessDay(paramFFSConnectionHolder, paramScheduleInfo.FIId, paramString1, paramString3, i);
      } else {
        j = i;
      }
    }
    int k = 1;
    if (paramScheduleInfo.CurInstanceNum != 0) {
      k = paramScheduleInfo.CurInstanceNum;
    }
    String str4 = paramScheduleInfo.FIId;
    if ((str4 == null) || (str4.length() == 0))
    {
      localObject = "ScheduleInfo.createSchedule: failed. FIID is null";
      FFSDebug.log((String)localObject, 0);
      throw new FFSException((String)localObject);
    }
    DateFormat.getDateInstance(2);
    Object localObject = { str2, paramScheduleInfo.Status, new Integer(paramScheduleInfo.StatusOption), new Integer(paramScheduleInfo.Frequency), new Integer(paramScheduleInfo.StartDate), FFSUtil.getDateString(), new Integer(paramScheduleInfo.InstanceCount), new Integer(i), new Integer(j), new Integer(k), paramString1, paramString2, paramScheduleInfo.LogID, new Integer(paramScheduleInfo.Perpetual), str4 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
    }
    catch (Exception localException)
    {
      String str5 = "*** ScheduleInfo.createSchedule failed:";
      FFSDebug.log(str5 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.createSchedule done", 6);
    return str2;
  }
  
  public static String createSchedule(FFSConnection paramFFSConnection, String paramString1, String paramString2, ScheduleInfo paramScheduleInfo)
    throws FFSException
  {
    String str = "System";
    return createSchedule(paramFFSConnection, paramString1, paramString2, paramScheduleInfo, str);
  }
  
  public static String createSchedule(FFSConnection paramFFSConnection, String paramString1, String paramString2, ScheduleInfo paramScheduleInfo, String paramString3)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.createSchedule start, instType" + paramString1 + ",instID=" + paramString2, 6);
    String str1 = "INSERT INTO SCH_ScheduleInfo (ScheduleID,Status,StatusOption,Frequency,StartDate,RecordCreateDate,InstanceCount,NextInstanceDate,WorkInstanceDate,CurInstanceNum,InstructionType,InstructionID,LogID,Perpetual, FIId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String str2;
    try
    {
      str2 = DBUtil.getNextIndexString("ScheduleID");
    }
    catch (BPWException localBPWException)
    {
      String str3 = "*** ScheduleInfo.createSchedule failed:";
      System.out.println(str3 + localBPWException.toString());
      throw new FFSException(localBPWException.toString());
    }
    int i = paramScheduleInfo.StartDate;
    if (paramScheduleInfo.NextInstanceDate != 0) {
      i = paramScheduleInfo.NextInstanceDate;
    }
    int j;
    if (paramScheduleInfo.WorkInstanceDate != 0)
    {
      j = paramScheduleInfo.WorkInstanceDate;
    }
    else
    {
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if (localScheduler != null)
      {
        localObject1 = new FFSConnectionHolder();
        ((FFSConnectionHolder)localObject1).conn = paramFFSConnection;
        j = DBUtil.getProcessDay((FFSConnectionHolder)localObject1, paramScheduleInfo.FIId, paramString1, paramString3, i);
      }
      else
      {
        j = i;
      }
    }
    int k = 1;
    if (paramScheduleInfo.CurInstanceNum != 0) {
      k = paramScheduleInfo.CurInstanceNum;
    }
    Object localObject1 = paramScheduleInfo.FIId;
    if ((localObject1 == null) || (((String)localObject1).length() == 0))
    {
      localObject2 = "ScheduleInfo.createSchedule: failed. FIID is null";
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException((String)localObject2);
    }
    Object localObject2 = { str2, paramScheduleInfo.Status, new Integer(paramScheduleInfo.StatusOption), new Integer(paramScheduleInfo.Frequency), new Integer(paramScheduleInfo.StartDate), FFSUtil.getDateString(), new Integer(paramScheduleInfo.InstanceCount), new Integer(i), new Integer(j), new Integer(k), paramString1, paramString2, paramScheduleInfo.LogID, new Integer(paramScheduleInfo.Perpetual), localObject1 };
    try
    {
      paramFFSConnection.executeCommand(str1, (Object[])localObject2);
    }
    catch (Exception localException)
    {
      String str4 = "*** ScheduleInfo.createSchedule failed:";
      FFSDebug.log(str4 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.createSchedule done", 6);
    return str2;
  }
  
  public static void updateAllWorkInstanceDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str = "System";
    updateAllWorkInstanceDate(paramFFSConnectionHolder, paramString1, paramString2, str);
  }
  
  public static void updateAllWorkInstanceDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.updateAllWorkInstanceDate start, " + paramString1, 6);
    String str1 = "SELECT ScheduleID,NextInstanceDate,WorkInstanceDate,FIId  FROM SCH_ScheduleInfo WHERE InstructionType=? AND FIId=? AND Status='Active'";
    Object[] arrayOfObject = { paramString1, paramString2 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      int i = 0;
      while (localFFSResultSet.getNextRow())
      {
        str2 = localFFSResultSet.getColumnString("ScheduleID");
        int j = localFFSResultSet.getColumnInt("NextInstanceDate");
        int k = localFFSResultSet.getColumnInt("WorkInstanceDate");
        updateWorkInstanceDate(paramFFSConnectionHolder, str2, paramString1, j, k, paramString2, paramString3);
        i++;
        if (i % BATCH_SIZE == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str2 = "*** ScheduleInfo.updateAllWorkInstanceDate failed:";
      FFSDebug.log(str2 + localException1.toString());
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
        FFSDebug.log("*** ScheduleInfo.updateAllWorkInstanceDate failed:" + localException2.toString());
      }
    }
    FFSDebug.log("ScheduleInfo.updateAllWorkInstanceDate done", 6);
  }
  
  public static void updateWorkInstanceDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3)
    throws FFSException
  {
    String str = "System";
    updateWorkInstanceDate(paramFFSConnectionHolder, paramString1, paramString2, paramInt1, paramInt2, paramString3, str);
  }
  
  public static void updateWorkInstanceDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "UPDATE SCH_ScheduleInfo SET WorkInstanceDate=? WHERE ScheduleID=?";
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    int i;
    if (localScheduler != null)
    {
      i = DBUtil.getProcessDay(paramFFSConnectionHolder, paramString3, paramString2, paramString4, paramInt1);
      FFSDebug.log("ScheduleInfo.updateWorkInstanceDate: schedID=" + paramString1 + ",payDate=" + i, 6);
      if (i != paramInt2) {}
    }
    else
    {
      throw new FFSException("*** ScheduleInfo.updateWorkInstanceDate: Scheduler not started");
    }
    Object[] arrayOfObject = { new Integer(i), paramString1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.updateWorkInstanceDate failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
  }
  
  public static void modifySchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString, ScheduleInfo paramScheduleInfo)
    throws FFSException
  {
    String str = "System";
    modifySchedule(paramFFSConnectionHolder, paramString, paramScheduleInfo, str);
  }
  
  public static void modifySchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, ScheduleInfo paramScheduleInfo, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.modifySchedule: start, ScheduleID=" + paramString1, 6);
    String str1 = "UPDATE SCH_ScheduleInfo SET Status=?,StatusOption=?,Frequency=?,StartDate=?,InstanceCount=?,NextInstanceDate=?,WorkInstanceDate=?,CurInstanceNum=?,Perpetual=? WHERE ScheduleID=?";
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    int i;
    if (localScheduler != null) {
      i = DBUtil.getProcessDay(paramFFSConnectionHolder, paramScheduleInfo.FIId, paramScheduleInfo.InstructionType, paramString2, paramScheduleInfo.NextInstanceDate);
    } else {
      i = paramScheduleInfo.NextInstanceDate;
    }
    Object[] arrayOfObject = { paramScheduleInfo.Status, new Integer(paramScheduleInfo.StatusOption), new Integer(paramScheduleInfo.Frequency), new Integer(paramScheduleInfo.StartDate), new Integer(paramScheduleInfo.InstanceCount), new Integer(paramScheduleInfo.NextInstanceDate), new Integer(i), new Integer(paramScheduleInfo.CurInstanceNum), new Integer(paramScheduleInfo.Perpetual), paramString1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.modifySchedule failed: InstructionType=" + paramScheduleInfo.InstructionType + ",srvrTID =" + paramScheduleInfo.InstructionID;
      FFSDebug.log(str2 + localException.getMessage(), 0);
      throw new FFSException(localException.getMessage());
    }
    FFSDebug.log("ScheduleInfo.modifySchedule done", 6);
  }
  
  public static void modifySchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, ScheduleInfo paramScheduleInfo)
    throws FFSException
  {
    String str = "System";
    modifySchedule(paramFFSConnectionHolder, paramString1, paramString2, paramScheduleInfo, str);
  }
  
  public static void modifySchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, ScheduleInfo paramScheduleInfo, String paramString3)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.modifySchedule: start, InstructionType=" + paramString1 + ",InstuctionID=" + paramString2, 6);
    if ((paramScheduleInfo.FIId == null) || (paramScheduleInfo.FIId.trim().length() == 0))
    {
      str1 = "ScheduleInfo.modifySchedule: FIID is null";
      FFSDebug.log(str1, 0);
      throw new FFSException(str1);
    }
    String str1 = "UPDATE SCH_ScheduleInfo SET Status=?,StatusOption=?,Frequency=?,StartDate=?,InstanceCount=?,NextInstanceDate=?,WorkInstanceDate=?,CurInstanceNum=?,Perpetual=? WHERE InstructionType=? AND InstructionID=? AND FIId=?";
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    int i;
    if (localScheduler != null) {
      i = DBUtil.getProcessDay(paramFFSConnectionHolder, paramScheduleInfo.FIId, paramString1, paramString3, paramScheduleInfo.NextInstanceDate);
    } else {
      i = paramScheduleInfo.NextInstanceDate;
    }
    Object[] arrayOfObject = { paramScheduleInfo.Status, new Integer(paramScheduleInfo.StatusOption), new Integer(paramScheduleInfo.Frequency), new Integer(paramScheduleInfo.StartDate), new Integer(paramScheduleInfo.InstanceCount), new Integer(paramScheduleInfo.NextInstanceDate), new Integer(i), new Integer(paramScheduleInfo.CurInstanceNum), new Integer(paramScheduleInfo.Perpetual), paramString1, paramString2, paramScheduleInfo.FIId };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.modifySchedule failed: InstructionType=" + paramString1 + ",srvrTID =" + paramString2;
      FFSDebug.log(str2 + localException.getMessage(), 0);
      throw new FFSException(localException.getMessage());
    }
    FFSDebug.log("ScheduleInfo.modifySchedule done", 6);
  }
  
  public static int modifyScheduleStartDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.modifyScheduleStartDate: start, InstructionType=" + paramString2 + ",InstuctionID=" + paramString3 + ",FIID=" + paramString1, 6);
    String str1 = "UPDATE SCH_ScheduleInfo SET StartDate=?,NextInstanceDate=?,WorkInstanceDate=? WHERE InstructionType=? AND InstructionID=? AND FIId=?";
    int i = paramInt;
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    int j;
    if (localScheduler != null) {
      j = DBUtil.getProcessDay(paramFFSConnectionHolder, paramString1, paramString2, i);
    } else {
      j = i;
    }
    Object[] arrayOfObject = { new Integer(paramInt), new Integer(i), new Integer(j), paramString2, paramString3, paramString1 };
    int k = -1;
    try
    {
      k = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.modifyScheduleStartDate failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.modifyScheduleStartDate done. Row updated: " + k, 6);
    return k;
  }
  
  public static void cancelSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.cancelSchedule start", 6);
    String str1 = "DELETE FROM SCH_ScheduleInfo WHERE ScheduleID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.cancelSchedule failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.cancelSchedule done", 6);
  }
  
  public static void cancelSchedule(FFSConnection paramFFSConnection, String paramString)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.cancelSchedule start", 6);
    String str1 = "DELETE FROM SCH_ScheduleInfo WHERE ScheduleID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      paramFFSConnection.executeCommand(str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.cancelSchedule failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.cancelSchedule done", 6);
  }
  
  public static int cancelSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.cancelSchedule start,instType=" + paramString1 + ",instID=" + paramString2, 6);
    if (paramString2.startsWith("-"))
    {
      str1 = "ScheduleInfo.cancelSchedule failed: instID and instType comb may not unique.";
      throw new FFSException(str1);
    }
    String str1 = "DELETE FROM SCH_ScheduleInfo WHERE InstructionType=? AND InstructionID=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.cancelSchedule failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.cancelSchedule done", 6);
    return i;
  }
  
  public static int cancelSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.cancelSchedule start,instType=" + paramString1 + ",instID=" + paramString2 + ",FIID=" + paramString3, 6);
    String str1 = "DELETE FROM SCH_ScheduleInfo WHERE InstructionType=? AND InstructionID=? AND FIId=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.cancelSchedule failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.cancelSchedule done", 6);
    return i;
  }
  
  public static int cancelSchedule(FFSConnection paramFFSConnection, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.cancelSchedule start,instType=" + paramString1 + ",instID=" + paramString2, 6);
    if (paramString2.startsWith("-"))
    {
      str1 = "ScheduleInfo.cancelSchedule failed: instID and instType comb may not unique.";
      throw new FFSException(str1);
    }
    String str1 = "DELETE FROM SCH_ScheduleInfo WHERE InstructionType=? AND InstructionID=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    int i = 0;
    try
    {
      i = paramFFSConnection.executeCommand(str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.cancelSchedule failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.cancelSchedule done", 6);
    return i;
  }
  
  public static int cancelSchedule(FFSConnection paramFFSConnection, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.cancelSchedule start,instType=" + paramString1 + ",instID=" + paramString2 + ",fiId=" + paramString3, 6);
    String str1 = "DELETE FROM SCH_ScheduleInfo WHERE InstructionType=? AND InstructionID=? AND FIId=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    int i = 0;
    try
    {
      i = paramFFSConnection.executeCommand(str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.cancelSchedule failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.cancelSchedule done", 6);
    return i;
  }
  
  public static ScheduleInfo[] retrieveScheduleInfo(String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.retrieveScheduleInfo start, instType" + paramString2, 6);
    String str = "SELECT ScheduleID,Status,StatusOption,Frequency,StartDate,RecordCreateDate,InstanceCount,NextInstanceDate,WorkInstanceDate,CurInstanceNum,InstructionID,LogID, Perpetual, FIId FROM SCH_ScheduleInfo WHERE FIId=? AND InstructionType=?";
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = { paramString1, paramString2 };
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (BPWException localBPWException)
    {
      localObject1 = "*** ScheduleInfo.retrieveScheduleInfo failed:";
      FFSDebug.log((String)localObject1 + localBPWException.toString());
      throw new FFSException(localBPWException.toString());
    }
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = new ScheduleInfo();
        ((ScheduleInfo)localObject1).ScheduleID = localFFSResultSet.getColumnString("ScheduleID");
        ((ScheduleInfo)localObject1).Status = localFFSResultSet.getColumnString("Status");
        ((ScheduleInfo)localObject1).StatusOption = localFFSResultSet.getColumnInt("StatusOption");
        ((ScheduleInfo)localObject1).Frequency = localFFSResultSet.getColumnInt("Frequency");
        ((ScheduleInfo)localObject1).StartDate = localFFSResultSet.getColumnInt("StartDate");
        ((ScheduleInfo)localObject1).RecordCreateDate = localFFSResultSet.getColumnString("RecordCreateDate");
        ((ScheduleInfo)localObject1).InstanceCount = localFFSResultSet.getColumnInt("InstanceCount");
        ((ScheduleInfo)localObject1).NextInstanceDate = localFFSResultSet.getColumnInt("NextInstanceDate");
        ((ScheduleInfo)localObject1).WorkInstanceDate = localFFSResultSet.getColumnInt("WorkInstanceDate");
        ((ScheduleInfo)localObject1).CurInstanceNum = localFFSResultSet.getColumnInt("CurInstanceNum");
        ((ScheduleInfo)localObject1).InstructionType = paramString2;
        ((ScheduleInfo)localObject1).InstructionID = localFFSResultSet.getColumnString("InstructionID");
        ((ScheduleInfo)localObject1).LogID = localFFSResultSet.getColumnString("LogID");
        ((ScheduleInfo)localObject1).Perpetual = localFFSResultSet.getColumnInt("Perpetual");
        ((ScheduleInfo)localObject1).FIId = paramString1;
        localArrayList.add(localObject1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ScheduleInfo.retrieveScheduleInfo failed:" + localException1.toString());
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
        FFSDebug.log("*** ScheduleInfo.retrieveScheduleInfo failed:" + localException2.toString());
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("ScheduleInfo.retrieveScheduleInfo done, rows=" + localArrayList.size(), 6);
    return localArrayList.isEmpty() ? null : (ScheduleInfo[])localArrayList.toArray(new ScheduleInfo[0]);
  }
  
  public static ScheduleInfo getScheduleInfo(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.getScheduleInfo start, FIId=" + paramString1 + ",InstructionType=" + paramString2 + ",InstructionID=" + paramString3, 6);
    ScheduleInfo localScheduleInfo = null;
    String str = "SELECT ScheduleID,Status,StatusOption, Frequency,StartDate,RecordCreateDate,InstanceCount,NextInstanceDate,WorkInstanceDate,CurInstanceNum,LogID,Perpetual FROM SCH_ScheduleInfo WHERE FIId = ? AND InstructionType=? AND InstructionID=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localScheduleInfo = new ScheduleInfo();
        localScheduleInfo.ScheduleID = localFFSResultSet.getColumnString("ScheduleID");
        localScheduleInfo.Status = localFFSResultSet.getColumnString("Status");
        localScheduleInfo.StatusOption = localFFSResultSet.getColumnInt("StatusOption");
        localScheduleInfo.Frequency = localFFSResultSet.getColumnInt("Frequency");
        localScheduleInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localScheduleInfo.RecordCreateDate = localFFSResultSet.getColumnString("RecordCreateDate");
        localScheduleInfo.InstanceCount = localFFSResultSet.getColumnInt("InstanceCount");
        localScheduleInfo.NextInstanceDate = localFFSResultSet.getColumnInt("NextInstanceDate");
        localScheduleInfo.WorkInstanceDate = localFFSResultSet.getColumnInt("WorkInstanceDate");
        localScheduleInfo.CurInstanceNum = localFFSResultSet.getColumnInt("CurInstanceNum");
        localScheduleInfo.InstructionType = paramString2;
        localScheduleInfo.InstructionID = paramString3;
        localScheduleInfo.LogID = localFFSResultSet.getColumnString("LogID");
        localScheduleInfo.Perpetual = localFFSResultSet.getColumnInt("Perpetual");
        localScheduleInfo.FIId = paramString1;
        i++;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ScheduleInfo.getScheduleInfo failed:" + localException1.toString());
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
        FFSDebug.log("*** ScheduleInfo.getScheduleInfo failed:" + localException2.toString());
      }
    }
    FFSDebug.log("ScheduleInfo.getScheduleInfo done, rows=" + i, 6);
    return localScheduleInfo;
  }
  
  public static boolean checkExist(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.checkExist start, FIId=" + paramString1 + ",InstructionType=" + paramString2 + ",InstructionID=" + paramString3, 6);
    String str = "SELECT ScheduleID FROM SCH_ScheduleInfo WHERE FIId = ? AND InstructionType=? AND InstructionID=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    boolean bool = false;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        bool = true;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ScheduleInfo.checkExist failed:" + localException1.toString());
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
        FFSDebug.log("*** ScheduleInfo.checkExist failed:" + localException2.toString());
      }
    }
    FFSDebug.log("ScheduleInfo.checkExist done=" + bool, 6);
    return bool;
  }
  
  public static ScheduleInfo[] getMaturedScheduleInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("ScheduleInfo.getMaturedScheduleInfo start,instType=" + paramString2, 6);
    String str1 = "SELECT ScheduleID,Status,StatusOption,Frequency,StartDate,RecordCreateDate,InstanceCount,NextInstanceDate,WorkInstanceDate,CurInstanceNum,InstructionType,InstructionID,LogID,Perpetual FROM SCH_ScheduleInfo WHERE FIId=? AND InstructionType=? AND Status='Active' AND WorkInstanceDate<=? ";
    str1 = paramFFSConnectionHolder.conn.getLimitedSelect(str1, BATCH_SIZE);
    FFSDebug.log("Obtaining matured schedule info using query " + str1);
    DateFormat localDateFormat = DateFormat.getDateInstance(2);
    Object[] arrayOfObject = { paramString1, paramString2, new Integer(convertInstanceDateToNum(localDateFormat.format(new Date()))) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        localScheduleInfo.ScheduleID = localFFSResultSet.getColumnString("ScheduleID");
        localScheduleInfo.Status = localFFSResultSet.getColumnString("Status");
        localScheduleInfo.StatusOption = localFFSResultSet.getColumnInt("StatusOption");
        localScheduleInfo.Frequency = localFFSResultSet.getColumnInt("Frequency");
        localScheduleInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localScheduleInfo.RecordCreateDate = localFFSResultSet.getColumnString("RecordCreateDate");
        localScheduleInfo.InstanceCount = localFFSResultSet.getColumnInt("InstanceCount");
        localScheduleInfo.NextInstanceDate = localFFSResultSet.getColumnInt("NextInstanceDate");
        localScheduleInfo.WorkInstanceDate = localFFSResultSet.getColumnInt("WorkInstanceDate");
        localScheduleInfo.CurInstanceNum = localFFSResultSet.getColumnInt("CurInstanceNum");
        localScheduleInfo.InstructionType = localFFSResultSet.getColumnString("InstructionType");
        localScheduleInfo.InstructionID = localFFSResultSet.getColumnString("InstructionID");
        localScheduleInfo.LogID = localFFSResultSet.getColumnString("LogID");
        localScheduleInfo.Perpetual = localFFSResultSet.getColumnInt("Perpetual");
        localScheduleInfo.FIId = paramString1;
        localArrayList.add(localScheduleInfo);
      }
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.getMaturedScheduleInfo failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
    }
    FFSDebug.log("ScheduleInfo.getMaturedScheduleInfo done, rows=" + localArrayList.size(), 6);
    if (localArrayList.size() == BATCH_SIZE) {
      jdField_char.add(paramString2 + paramString1);
    } else {
      jdField_char.remove(paramString2 + paramString1);
    }
    return localArrayList.isEmpty() ? null : (ScheduleInfo[])localArrayList.toArray(new ScheduleInfo[0]);
  }
  
  public static ScheduleInfo[] retrieveScheduleInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("ScheduleInfo.retrieveScheduleInfo by status start,instType=" + paramString2, 6);
    String str1 = paramString3.equals("Active") ? "SELECT ScheduleID, Status, StatusOption, Frequency, StartDate, RecordCreateDate, InstanceCount, NextInstanceDate, WorkInstanceDate, CurInstanceNum, InstructionType, InstructionID, LogID, Perpetual FROM SCH_ScheduleInfo WHERE FIId=? AND InstructionType=? AND Status=? AND WorkInstanceDate<=? ORDER BY ScheduleID ASC" : "SELECT ScheduleID, Status, StatusOption, Frequency, StartDate, RecordCreateDate, InstanceCount, NextInstanceDate, WorkInstanceDate, CurInstanceNum, InstructionType, InstructionID, LogID, Perpetual FROM SCH_ScheduleInfo WHERE FIId=? AND InstructionType=? AND Status=? ORDER BY ScheduleID ASC";
    str1 = paramFFSConnectionHolder.conn.getLimitedSelect(str1, BATCH_SIZE);
    FFSDebug.log("Obtaining schedule info using query " + str1);
    Object[] arrayOfObject = a(paramString1, paramString2, paramString3);
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        localScheduleInfo.ScheduleID = localFFSResultSet.getColumnString("ScheduleID");
        localScheduleInfo.Status = localFFSResultSet.getColumnString("Status");
        localScheduleInfo.StatusOption = localFFSResultSet.getColumnInt("StatusOption");
        localScheduleInfo.Frequency = localFFSResultSet.getColumnInt("Frequency");
        localScheduleInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localScheduleInfo.RecordCreateDate = localFFSResultSet.getColumnString("RecordCreateDate");
        localScheduleInfo.InstanceCount = localFFSResultSet.getColumnInt("InstanceCount");
        localScheduleInfo.NextInstanceDate = localFFSResultSet.getColumnInt("NextInstanceDate");
        localScheduleInfo.WorkInstanceDate = localFFSResultSet.getColumnInt("WorkInstanceDate");
        localScheduleInfo.CurInstanceNum = localFFSResultSet.getColumnInt("CurInstanceNum");
        localScheduleInfo.InstructionType = localFFSResultSet.getColumnString("InstructionType");
        localScheduleInfo.InstructionID = localFFSResultSet.getColumnString("InstructionID");
        localScheduleInfo.LogID = localFFSResultSet.getColumnString("LogID");
        localScheduleInfo.Perpetual = localFFSResultSet.getColumnInt("Perpetual");
        localScheduleInfo.FIId = paramString1;
        localArrayList.add(localScheduleInfo);
      }
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.retrieveScheduleInfo by status failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
    }
    FFSDebug.log("ScheduleInfo.retrieveScheduleInfo by status done, rows=" + localArrayList.size(), 6);
    if (localArrayList.size() == BATCH_SIZE) {
      jdField_char.add(paramString2 + paramString1);
    } else {
      jdField_char.remove(paramString2 + paramString1);
    }
    return localArrayList.isEmpty() ? null : (ScheduleInfo[])localArrayList.toArray(new ScheduleInfo[0]);
  }
  
  private static Object[] a(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    Object[] arrayOfObject = null;
    if (paramString3.equals("Active"))
    {
      DateFormat localDateFormat = DateFormat.getDateInstance(2);
      arrayOfObject = new Object[] { paramString1, paramString2, paramString3, new Integer(convertInstanceDateToNum(localDateFormat.format(new Date()))) };
    }
    else
    {
      arrayOfObject = new Object[] { paramString1, paramString2, paramString3 };
    }
    return arrayOfObject;
  }
  
  public static void updateStatusByScheduleID(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.updateStatusByScheduleID: start, scheduleID=" + paramString1, 6);
    String str1 = "UPDATE SCH_ScheduleInfo SET Status=? WHERE ScheduleID=?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.updateStatusByScheduleID failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.updateStatusByScheduleID done", 6);
  }
  
  public static boolean isBatchDone(String paramString1, String paramString2)
  {
    boolean bool = !jdField_char.contains(paramString2 + paramString1);
    FFSDebug.log("ScheduleInfo.isBatchDone instType=" + paramString2 + "fiid " + paramString1 + "done = " + bool, 6);
    return bool;
  }
  
  public static void clearBatch(String paramString1, String paramString2)
  {
    jdField_char.remove(paramString2 + paramString1);
  }
  
  public static void clearSchedCache()
  {
    if (jdField_char != null) {
      jdField_char.clear();
    }
  }
  
  public static ScheduleInfo[] getOldScheduleInfo(String paramString1, FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString2)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("ScheduleInfo.getOldScheduleInfo start,instType=" + paramString2 + ",ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    String str2 = "SELECT InstructionID,ScheduleID FROM SCH_ScheduleInfo WHERE FIId = ? AND InstructionType=? AND Status='Active' AND RecordCreateDate<=? ";
    Object[] arrayOfObject = { paramString1, paramString2, str1 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        localScheduleInfo.InstructionID = localFFSResultSet.getColumnString("InstructionID");
        localScheduleInfo.ScheduleID = localFFSResultSet.getColumnString("ScheduleID");
        localArrayList.add(localScheduleInfo);
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** ScheduleInfo.getOldScheduleInfo failed:";
      FFSDebug.log(str3 + localException1.toString());
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
        FFSDebug.log("*** ScheduleInfo.getOldScheduleInfo failed:" + localException2.toString());
      }
    }
    FFSDebug.log("ScheduleInfo.getOldScheduleInfo done, rows=" + localArrayList.size(), 6);
    if (localArrayList.isEmpty()) {
      return null;
    }
    return (ScheduleInfo[])localArrayList.toArray(new ScheduleInfo[0]);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.delete: start, schedID=" + paramString, 6);
    String str1 = "DELETE FROM SCH_ScheduleInfo WHERE ScheduleID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.delete done", 6);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.delete: start, instId=" + paramString1 + ",instType=" + paramString2, 6);
    if (paramString1.startsWith("-"))
    {
      str1 = "ScheduleInfo.delete failed: instID and instType comb may not unique.";
      throw new FFSException(str1);
    }
    String str1 = "DELETE FROM SCH_ScheduleInfo WHERE InstructionID=? AND InstructionType=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ScheduleInfo.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.delete done", 6);
  }
  
  public static void changeToNextSchedule(FFSConnectionHolder paramFFSConnectionHolder, ScheduleInfo paramScheduleInfo)
    throws Exception
  {
    String str = "System";
    changeToNextSchedule(paramFFSConnectionHolder, paramScheduleInfo, str);
  }
  
  public static void changeToNextSchedule(FFSConnectionHolder paramFFSConnectionHolder, ScheduleInfo paramScheduleInfo, String paramString)
    throws Exception
  {
    FFSDebug.log("ScheduleInfo.changeToNextSchedule start", 6);
    paramScheduleInfo.CurInstanceNum += 1;
    if ((paramScheduleInfo.Perpetual != 1) && ((paramScheduleInfo.CurInstanceNum > paramScheduleInfo.InstanceCount) || (paramScheduleInfo.Frequency == 10))) {
      paramScheduleInfo.Status = "Close";
    }
    if (paramScheduleInfo.Status.equals("Active"))
    {
      computeNextInstanceDate(paramScheduleInfo);
      modifySchedule(paramFFSConnectionHolder, paramScheduleInfo.ScheduleID, paramScheduleInfo, paramString);
    }
    else if (paramScheduleInfo.Status.equals("Close"))
    {
      delete(paramFFSConnectionHolder, paramScheduleInfo.ScheduleID);
    }
    FFSDebug.log("ScheduleInfo.changeToNextSchedule done", 6);
  }
  
  public static void scheduleRunUpdate(FFSConnectionHolder paramFFSConnectionHolder, ScheduleInfo paramScheduleInfo)
    throws Exception
  {
    FFSDebug.log("ScheduleInfo.scheduleRunUpdate start", 6);
    paramScheduleInfo.Status = "Processing";
    paramScheduleInfo.CurInstanceNum += 1;
    if ((paramScheduleInfo.Perpetual != 1) && ((paramScheduleInfo.CurInstanceNum > paramScheduleInfo.InstanceCount) || (paramScheduleInfo.Frequency == 10))) {
      paramScheduleInfo.StatusOption = 1;
    }
    if (paramScheduleInfo.StatusOption != 1) {
      computeNextInstanceDate(paramScheduleInfo);
    }
    modifySchedule(paramFFSConnectionHolder, paramScheduleInfo.ScheduleID, paramScheduleInfo);
    FFSDebug.log("ScheduleInfo.scheduleRunUpdate done", 6);
  }
  
  public static void moveNextInstanceDate(ScheduleInfo paramScheduleInfo)
    throws Exception
  {
    FFSDebug.log("ScheduleInfo.moveNextInstanceDate start, " + paramScheduleInfo.NextInstanceDate, 6);
    paramScheduleInfo.NextInstanceDate = computeFutureDate(paramScheduleInfo.NextInstanceDate, paramScheduleInfo.Frequency, 1);
    FFSDebug.log("ScheduleInfo.moveNextInstanceDate done, " + paramScheduleInfo.NextInstanceDate, 6);
  }
  
  public static void moveNextInstanceDate(ScheduleInfo paramScheduleInfo, int paramInt)
    throws Exception
  {
    FFSDebug.log("ScheduleInfo.moveNextInstanceDate start, " + paramScheduleInfo.NextInstanceDate, 6);
    paramScheduleInfo.NextInstanceDate = computeFutureDate(paramScheduleInfo.NextInstanceDate, paramScheduleInfo.Frequency, paramInt);
    FFSDebug.log("ScheduleInfo.moveNextInstanceDate done, " + paramScheduleInfo.NextInstanceDate, 6);
  }
  
  public static void computeNextInstanceDate(ScheduleInfo paramScheduleInfo)
    throws Exception
  {
    FFSDebug.log("ScheduleInfo.computeNextInstanceDate start, " + paramScheduleInfo.NextInstanceDate, 6);
    int i;
    int j;
    if (paramScheduleInfo.StartDate == 0)
    {
      if (paramScheduleInfo.NextInstanceDate == 0)
      {
        Calendar localCalendar = Calendar.getInstance();
        i = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
      }
      else
      {
        i = paramScheduleInfo.NextInstanceDate;
      }
      j = 1;
    }
    else
    {
      i = paramScheduleInfo.StartDate;
      j = paramScheduleInfo.CurInstanceNum - 1;
    }
    paramScheduleInfo.NextInstanceDate = computeFutureDate(i, paramScheduleInfo.Frequency, j);
    FFSDebug.log("ScheduleInfo.computeNextInstanceDate done, " + paramScheduleInfo.NextInstanceDate, 6);
  }
  
  public static int computeFutureDate(int paramInt1, int paramInt2, int paramInt3)
    throws Exception
  {
    int i = 0;
    int j = 0;
    int k = 0;
    i = paramInt1 / 1000000;
    paramInt1 %= 1000000;
    j = paramInt1 / 10000 - 1;
    paramInt1 %= 10000;
    k = paramInt1 / 100;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(1, i);
    localCalendar.set(2, j);
    localCalendar.set(5, k);
    switch (paramInt2)
    {
    case 10: 
      break;
    case 0: 
      localCalendar.add(1, 1 * paramInt3);
      break;
    case 1: 
      localCalendar.add(2, 2 * paramInt3);
      break;
    case 2: 
      localCalendar.add(3, 2 * paramInt3);
      break;
    case 3: 
      localCalendar.add(3, 4 * paramInt3);
      break;
    case 4: 
      localCalendar.add(2, 1 * paramInt3);
      break;
    case 5: 
      localCalendar.add(2, 3 * paramInt3);
      break;
    case 6: 
      localCalendar.add(2, 6 * paramInt3);
      break;
    case 7: 
      localCalendar.add(2, 4 * paramInt3);
      break;
    case 9: 
      localCalendar.add(3, 1 * paramInt3);
      break;
    case 11: 
      localCalendar.add(6, 1 * paramInt3);
      break;
    case 12: 
      localCalendar.set(1, 1000);
      localCalendar.set(2, 0);
      localCalendar.set(5, 1);
      break;
    case 8: 
      if (Semimonthly.isInitialized() == true)
      {
        if (paramInt3 < 0)
        {
          String str1 = "*** ScheduleInfo.computeFutureDate failed: Negative period values (" + paramInt3 + ") are not supported for " + "frequency " + paramInt2 + " when using " + "the semimonthly look-up table.";
          FFSDebug.log(str1);
          throw new FFSException(str1);
        }
        for (int m = 0; m < paramInt3; m++)
        {
          int i1 = localCalendar.get(2) + 1;
          int i2 = localCalendar.get(5);
          int i3 = i1 * 100 + i2;
          int i4 = Semimonthly.getNext(i3);
          if (i4 == 0)
          {
            localCalendar.add(6, 15);
          }
          else
          {
            int i5 = i4 / 100;
            int i6 = i4 % 100;
            localCalendar.set(2, i5 - 1);
            localCalendar.set(5, i6);
            if ((i1 == 12) && (i5 == 1))
            {
              i++;
              localCalendar.set(1, i);
            }
          }
        }
      }
      localCalendar.add(6, 15 * paramInt3);
      break;
    default: 
      String str2 = "*** ScheduleInfo.computeFutureDate failed: Unsupported frequency (" + paramInt2 + ").";
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    int n = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
    return n;
  }
  
  public static int convertInstanceDateToNum(String paramString)
    throws FFSException
  {
    int i = 0;
    DateFormat localDateFormat = DateFormat.getDateInstance(2);
    Date localDate;
    try
    {
      localDate = localDateFormat.parse(paramString);
    }
    catch (ParseException localParseException)
    {
      String str = "*** ScheduleInfo.convertInstanceDateToNum parse failed:";
      FFSDebug.log(str + localParseException.getMessage());
      throw new FFSException(str + localParseException.getMessage());
    }
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    i = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
    return i;
  }
  
  public static int convertInstanceDateToNum(Date paramDate)
    throws FFSException
  {
    int i = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    i = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
    return i;
  }
  
  public static int getInstanceDate(int paramInt)
    throws FFSException
  {
    int i = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(6, 1 * paramInt);
    i = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
    return i;
  }
  
  public static String[] getPendingDates(ScheduleInfo paramScheduleInfo, int paramInt)
    throws Exception
  {
    int i = getInstanceDate(0);
    int j = getInstanceDate(paramInt);
    return a(paramScheduleInfo, i, j);
  }
  
  public static String[] getPendingDatesByStartAndEndDate(ScheduleInfo paramScheduleInfo, int paramInt1, int paramInt2)
    throws Exception
  {
    return a(paramScheduleInfo, paramInt1, paramInt2);
  }
  
  private static String[] a(ScheduleInfo paramScheduleInfo, int paramInt1, int paramInt2)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramScheduleInfo.CurInstanceNum;
    int j = paramScheduleInfo.InstanceCount;
    while ((i < j) || (paramScheduleInfo.Perpetual == 1))
    {
      int k = computeFutureDate(paramScheduleInfo.NextInstanceDate, paramScheduleInfo.Frequency, i);
      if ((k >= paramInt1) && (k <= paramInt2)) {
        localArrayList.add(Integer.toString(k) + "0000");
      } else {
        if (k > paramInt2) {
          break;
        }
      }
      i++;
    }
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static boolean startModifyingSingleSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "ScheduleInfo.startModifyingSingleSchedule";
    FFSDebug.log(str1 + ": start, InstructionType=" + paramString1 + ",InstuctionID=" + paramString2, 6);
    int i = -1;
    String str2 = jdField_for;
    FFSDebug.log(str1 + " sql: " + str2, 6);
    Object[] arrayOfObject = { paramString3, paramString1, paramString2 };
    boolean bool = false;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 1)
      {
        paramFFSConnectionHolder.conn.commit();
        bool = true;
      }
      else
      {
        paramFFSConnectionHolder.conn.rollback();
        bool = false;
      }
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str3 = "*** " + str1 + ":";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log(str1 + " done", 6);
    return bool;
  }
  
  public static boolean endModifyingSingleSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "ScheduleInfo.endModifyingSingleSchedule";
    FFSDebug.log(str1 + ": start, InstructionType=" + paramString1 + ",InstuctionID=" + paramString2, 6);
    String str2 = jdField_new;
    Object[] arrayOfObject = { paramString3, paramString1, paramString2 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      String str3 = "*** ScheduleInfo.startModifyingSchedule failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.startModifyingSchedule done", 6);
    return true;
  }
  
  public static boolean lockScheduleOfWireBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "ScheduleInfo.lockScheduleOfWireBatch";
    FFSDebug.log(str1 + ": start, InstructionType=" + paramString1 + ",batchId=" + paramString2, 6);
    int i = -1;
    String str2 = a;
    FFSDebug.log(str1 + " sql: " + str2, 6);
    Object[] arrayOfObject = { paramString3, paramString1, paramString2 };
    boolean bool = false;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i != -1)
      {
        paramFFSConnectionHolder.conn.commit();
        bool = true;
      }
      else
      {
        paramFFSConnectionHolder.conn.rollback();
        bool = false;
      }
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str3 = "*** " + str1 + ":";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log(str1 + " done", 6);
    return bool;
  }
  
  public static boolean unlockScheduleOfWireBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "ScheduleInfo.unlockScheduleOfWireBatch";
    FFSDebug.log(str1 + ": start, InstructionType=" + paramString1 + ", batchId=" + paramString2, 6);
    String str2 = jdField_case;
    Object[] arrayOfObject = { paramString3, paramString1, paramString2 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      String str3 = "*** ScheduleInfo.unlockScheduleOfWireBatch failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.unlockScheduleOfWireBatch done", 6);
    return true;
  }
  
  public static boolean startModifyingRecSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, String paramString4)
    throws FFSException
  {
    String str1 = "ScheduleInfo.startModifyingRecSchedule";
    FFSDebug.log(str1 + ": start, " + " FIId=" + paramString4 + " RecInstructionType=" + paramString1 + ",RecInstuctionID=" + paramString2 + " SingleInstructionType=" + paramString3 + ",SingleInstuctionID=" + paramArrayOfString, 6);
    String str2 = a(paramArrayOfString);
    int i = 1 + paramArrayOfString.length;
    String str3 = jdField_do + str2 + jdField_if;
    FFSDebug.log(str1 + " sql: " + str3, 6);
    Object[] arrayOfObject = { paramString4, paramString1, paramString2, paramString3 };
    boolean bool = false;
    try
    {
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if (j == i)
      {
        if (!a(paramFFSConnectionHolder, paramString4, paramString1, paramString2))
        {
          paramFFSConnectionHolder.conn.commit();
          bool = true;
        }
        else
        {
          paramFFSConnectionHolder.conn.rollback();
          bool = false;
        }
      }
      else
      {
        paramFFSConnectionHolder.conn.rollback();
        bool = false;
      }
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str4 = "*** " + str1 + ":";
      FFSDebug.log(str4 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log(str1 + " done", 6);
    return bool;
  }
  
  public static boolean endModifyingRecSchedule(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, String paramString4)
    throws FFSException
  {
    String str1 = "ScheduleInfo.endModifyingRecSchedule";
    FFSDebug.log(str1 + ": start, " + " RecInstructionType=" + paramString1 + ",RecInstuctionID=" + paramString2 + " SingleInstructionType=" + paramString3 + ",SingleInstuctionID=" + paramArrayOfString, 6);
    String str2 = a(paramArrayOfString);
    int i = 1;
    if (paramArrayOfString != null) {
      i += paramArrayOfString.length;
    }
    String str3 = jdField_try + str2 + jdField_int;
    FFSDebug.log(str1 + " sql: " + str3, 6);
    Object[] arrayOfObject = { paramString4, paramString1, paramString2, paramString3 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str4 = "*** " + str1 + ":";
      FFSDebug.log(str4 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log(str1 + " done", 6);
    return false;
  }
  
  private static String a(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return " '0'";
    }
    StringBuffer localStringBuffer = new StringBuffer("");
    for (int i = 0; i < paramArrayOfString.length; i++) {
      localStringBuffer.append("'").append(paramArrayOfString[i]).append("'").append(",");
    }
    String str = localStringBuffer.toString();
    str = str.substring(0, str.length() - 1);
    return str;
  }
  
  public static boolean recoverModifyingSchedule(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "ScheduleInfo.recoverModifyingSchedule";
    FFSDebug.log(str1 + ": start. ", 6);
    String str2 = jdField_byte;
    Object[] arrayOfObject = new Object[0];
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str3 = "*** ScheduleInfo.startModifyingSchedule failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.startModifyingSchedule done", 6);
    return true;
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    ScheduleInfo localScheduleInfo = getScheduleInfo(paramString1, paramString2, paramString3, paramFFSConnectionHolder);
    if (localScheduleInfo == null) {
      return true;
    }
    if (localScheduleInfo.Status == "Close") {
      return true;
    }
    return localScheduleInfo.CurInstanceNum >= localScheduleInfo.InstanceCount;
  }
  
  public static void deleteByStatusOption(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.deleteByStatusOption start for FI " + paramString1 + ", Instruction Type " + paramString2, 6);
    String str1 = "SELECT ScheduleID FROM SCH_ScheduleInfo WHERE InstructionType=? and FIId=? and Status=? and StatusOption=? ";
    if (paramFFSConnectionHolder.conn.getDatabaseType().toLowerCase().indexOf("ase") == -1) {
      str1 = paramFFSConnectionHolder.conn.getLimitedSelect(str1, BATCH_SIZE);
    }
    String str2 = "DELETE FROM SCH_ScheduleInfo WHERE ScheduleID in (" + str1 + ")";
    if (paramFFSConnectionHolder.conn.getDatabaseType().toLowerCase().indexOf("ase") != -1) {
      str2 = paramFFSConnectionHolder.conn.getLimitedSelect(str2, BATCH_SIZE);
    }
    FFSDebug.log("DELETE SCH_ScheduleInfo statement: " + str2, 6);
    Object[] arrayOfObject = { paramString2, paramString1, paramString3, new Integer(paramInt) };
    try
    {
      int i;
      do
      {
        i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      } while (i > 0);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str3 = "*** ScheduleInfo.deleteByStatusOption failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.deleteByStatusOption done", 6);
  }
  
  public static void updateStatusByStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    FFSDebug.log("ScheduleInfo.updateStatusByStatus start for FI " + paramString1 + ", Instruction Type " + paramString2, 6);
    String str1 = "UPDATE SCH_ScheduleInfo set Status = ? WHERE ScheduleID in (SELECT ScheduleID  FROM SCH_ScheduleInfo  WHERE InstructionType=? AND FIId=?  AND Status=?";
    if (paramFFSConnectionHolder.conn.getDatabaseType().toLowerCase().indexOf("db2") != -1) {
      str1 = str1 + " FETCH FIRST " + BATCH_SIZE + " ROWS ONLY)";
    } else if (paramFFSConnectionHolder.conn.getDatabaseType().toLowerCase().indexOf("ora") != -1) {
      str1 = str1 + " and rownum <= " + BATCH_SIZE + ")";
    } else {
      str1 = str1 + ")";
    }
    Object[] arrayOfObject = { paramString4, paramString2, paramString1, paramString3 };
    try
    {
      int i;
      do
      {
        i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      } while (i > 0);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str2 = "*** ScheduleInfo.updateStatusByStatus failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("ScheduleInfo.updateStatusByStatus done", 6);
  }
  
  public static int checkScheduleStatus(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str = "ScheduleInfo.checkScheduleStatus: ";
    SchedulerInfo localSchedulerInfo = BPWUtil.getSchedulerInfo(paramString2, paramString3);
    if ((localSchedulerInfo == null) || (localSchedulerInfo.NextRunTime == null) || (localSchedulerInfo.NextRunTime.compareTo("N/A") == 0))
    {
      FFSDebug.log(str + "No " + paramString3 + " Schedule exists: " + "return Success.", 6);
      return 0;
    }
    FFSDebug.log(str + "schInfo.NextRunTime = " + localSchedulerInfo.NextRunTime, 6);
    if (canModify(paramFFSConnectionHolder, paramString3, paramString1, paramString2, localSchedulerInfo.NextRunTime) == true) {
      return 0;
    }
    return 2016;
  }
  
  public static boolean canModify(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "ScheduleInfo.canModify: ";
    String str2 = BPWUtil.getDateInNewFormat(paramString4, "EEE MMM dd HH:mm:ss z yyyy", "yyyy/MM/dd HH:mm");
    String str3 = BPWUtil.getDateInNewFormat(paramString4, "EEE MMM dd HH:mm:ss z yyyy", "yyyyMMdd");
    FFSDebug.log(str1 + "nextRunTimeInStartTimeFormat =" + str2 + ", nextRunTimeInDueDateFormat =" + str3, 6);
    ScheduleInfo localScheduleInfo = getScheduleInfo(paramString3, paramString1, paramString2, paramFFSConnectionHolder);
    if ((localScheduleInfo == null) || (localScheduleInfo.Status.compareTo("Close") == 0) || (localScheduleInfo.Status.compareTo("InActive") == 0))
    {
      FFSDebug.log(str1 + " ScheduleInfo not found, closed or inactive: return false", 6);
      return false;
    }
    if (localScheduleInfo.Status.compareTo("Processing") == 0)
    {
      FFSDebug.log(str1 + "ScheduleInfo status is Processing", 6);
      return !isScheduleRunning(paramFFSConnectionHolder, paramString3, paramString1);
    }
    String str4 = BPWUtil.getLeadTimeForCutOff();
    FFSDebug.log(str1 + "now with lead time added =" + str4, 6);
    if (str2.compareTo(str4) > 0)
    {
      FFSDebug.log(str1 + "end, nextRunTimeInStartTimeFormat > nowWithLeadTime: return true", 6);
      return true;
    }
    String str5 = BPWUtil.getDateInNewFormat(String.valueOf(localScheduleInfo.NextInstanceDate), "yyyyMMddhh", "yyyyMMdd");
    FFSDebug.log(str1 + "nextInstanceDate =" + str5, 6);
    if (str5.compareTo(str3) <= 0)
    {
      FFSDebug.log(str1 + "end, nextInstanceDate <= nextRunTimeInDueDateFormat: return false", 6);
      return false;
    }
    FFSDebug.log(str1 + "end, true", 6);
    return true;
  }
  
  public static boolean isScheduleRunning(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "ScheduleInfo.isScheduleRunning: ";
    FFSDebug.log(str1 + " start. FIID = " + paramString1 + ", Instruction type = " + paramString2, 6);
    String str2 = "SELECT SchHistID, EventType from SCH_ScheduleHistory where ProcessId is not null AND  ProcessId = (SELECT ProcessId from SCH_InstructionTypeStatus where FIId = ? AND InstructionType = ?) ";
    Object[] arrayOfObject = { paramString1, paramString2 };
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    boolean bool = true;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      int i = 0;
      while (localFFSResultSet.getNextRow())
      {
        int j = Integer.parseInt(localFFSResultSet.getColumnString(1));
        if (j > i)
        {
          i = j;
          str3 = localFFSResultSet.getColumnString(2);
        }
      }
    }
    catch (Exception localException1)
    {
      paramFFSConnectionHolder.conn.rollback();
      String str4 = "*** " + str1 + " failed:";
      FFSDebug.log(str4 + localException1.toString());
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
        FFSDebug.log("*** " + str1 + " failed:" + localException2.toString());
      }
    }
    FFSDebug.log(str1 + " Last Event Type: " + str3, 6);
    if ((str3 == null) || (str3.compareTo("ProcessingStop") == 0) || (str3.compareTo("Exception") == 0) || (str3.compareTo("RecoveryStop") == 0)) {
      bool = false;
    }
    FFSDebug.log(str1 + "done. " + bool, 6);
    return bool;
  }
  
  public String getFirstInstanceDateInRange(int paramInt1, int paramInt2)
    throws Exception
  {
    int i = this.CurInstanceNum;
    int j = this.InstanceCount;
    String str = null;
    int k = this.NextInstanceDate;
    while ((i <= j) || (this.Perpetual == 1))
    {
      if ((k >= paramInt1) && (k <= paramInt2))
      {
        str = Integer.toString(k);
        break;
      }
      if (k > paramInt2) {
        break;
      }
      k = computeFutureDate(k, this.Frequency, 1);
      i++;
    }
    if (str != null) {
      str = str.substring(0, 8);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.db.ScheduleInfo
 * JD-Core Version:    0.7.0.1
 */