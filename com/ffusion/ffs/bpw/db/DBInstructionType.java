package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.ScheduleActivityInfo;
import com.ffusion.ffs.bpw.interfaces.ScheduleActivityList;
import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBInstructionType
  implements DBConsts
{
  private static final String xk = "SELECT FIId, InstructionType, HandlerClassName, SchedulerStartTime, SchedulerInterval, DispatchStartTime, DispatchInterval, ResubmitEventSupported, ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Memo, Category FROM SCH_InstructionType";
  private static final String xo = "SELECT FIId, InstructionType, HandlerClassName, SchedulerStartTime, SchedulerInterval, DispatchStartTime, DispatchInterval, ResubmitEventSupported , ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Memo, Category FROM SCH_InstructionType WHERE FIId=?";
  private static final String xd = "SELECT FIId, InstructionType, HandlerClassName, SchedulerStartTime, SchedulerInterval, DispatchStartTime, DispatchInterval, ResubmitEventSupported, ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Category , Memo FROM SCH_InstructionType WHERE FIId = ? and Category = ? ";
  private static final String xe = "INSERT INTO SCH_InstructionType (FIId, InstructionType, HandlerClassName, SchedulerStartTime, SchedulerInterval, DispatchStartTime, DispatchInterval, ResubmitEventSupported , ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Category, Memo )VALUES ( ?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?, ?, ? ,  ? , ? , ? , ? , ? ) ";
  private static final String xf = "UPDATE SCH_InstructionType SET HandlerClassName=?, SchedulerStartTime=?, SchedulerInterval=?, DispatchStartTime=?, DispatchInterval=?, ResubmitEventSupported =?, ChkTmAutoRecover=?, FileBasedRecovery=?, ActiveFlag=?, RouteID=? , ProcessOnHolidays=?, CreateEmptyFile=?, UseCutOffs=?, Category=?, Memo=? WHERE FIId=? AND InstructionType=?";
  private static final String xh = "DELETE FROM SCH_InstructionType WHERE FIId=? AND InstructionType=? ";
  private static final String w9 = "DELETE FROM SCH_InstructionType";
  private static final String xn = "SELECT InstructionType, HandlerClassName, SchedulerStartTime,SchedulerInterval, DispatchStartTime, DispatchInterval,ResubmitEventSupported, ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Category, Memo  FROM SCH_InstructionType WHERE FIId=? AND InstructionType = ? ";
  private static final String xm = "SELECT Category FROM SCH_InstructionType WHERE FIId=? AND InstructionType = ? ";
  private static final String xa = "INSERT INTO SCH_CutOffs ( CutOffId, FIID, InstructionType, Frequency, Day, ProcessTime, Extension, Status, SubmittedBy, LogId, Memo ) VALUES( ?,?,?,?,?,?,?,?,?,?,? )";
  private static final String w7 = "SELECT  CutOffId, FIID, InstructionType, Frequency, Day, ProcessTime, Extension, NextProcessTime, LastProcessTime, Status, SubmittedBy, LogId, Memo FROM SCH_CutOffs Where Status != 'CANCELEDON'";
  private static final String w8 = "SELECT CutOffId, FIID, InstructionType, Frequency, Day, ProcessTime, Extension, NextProcessTime, LastProcessTime, Status, SubmittedBy, LogId, Memo FROM SCH_CutOffs WHERE FIID = ? and InstructionType = ? and Status !='CANCELEDON'";
  private static final String xg = "SELECT CutOffId, FIID, InstructionType, Frequency, Day, ProcessTime, Extension, NextProcessTime, LastProcessTime, Status, SubmittedBy, LogId, Memo FROM SCH_CutOffs WHERE CutOffId = ?  and Status != 'CANCELEDON'";
  private static final String w4 = "UPDATE SCH_CutOffs SET  FIID= ?, InstructionType = ?, Frequency = ?, Day = ?, ProcessTime = ?, Extension = ?, Status = ?, SubmittedBy = ?, LogId = ?, Memo = ? WHERE CutOffId = ? ";
  private static final String w6 = "UPDATE SCH_CutOffs SET  Status = ?  WHERE CutOffId = ? ";
  private static final String w5 = "UPDATE SCH_CutOffs SET NextProcessTime = ?  WHERE CutOffId = ? ";
  private static final String xl = "UPDATE SCH_CutOffs SET LastProcessTime = ?  WHERE CutOffId = ? ";
  private static final String xp = "UPDATE SCH_CutOffs SET  Extension = ?  WHERE CutOffId = ? ";
  private static final String xj = "SELECT CutOffId, Status FROM SCH_CutOffs WHERE FIID = ? and InstructionType = ? and Frequency = ? and Day = ?  and ProcessTime = ? and Status != 'CANCELEDON'";
  private static final String xi = " AND FIID = ?";
  private static final String xc = " AND InstructionType = ?";
  private static final String xb = " AND Status IN (";
  public static final int START_DAY_OF_WEEK = 1;
  public static final int END_DAY_OF_WEEK = 7;
  public static final int START_DAY_OF_MONTH = 1;
  public static final int END_DAY_OF_MONTH = 31;
  public static final int START_DAY_OF_YEAR = 1;
  public static final int END_DAY_OF_YEAR = 365;
  
  public static ArrayList readInstructionTypes(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    String str = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT FIId, InstructionType, HandlerClassName, SchedulerStartTime, SchedulerInterval, DispatchStartTime, DispatchInterval, ResubmitEventSupported, ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Memo, Category FROM SCH_InstructionType", new Object[0]);
      while (localFFSResultSet.getNextRow())
      {
        InstructionType localInstructionType = new InstructionType();
        localInstructionType.FIId = localFFSResultSet.getColumnString("FIId");
        localInstructionType.InstructionType = localFFSResultSet.getColumnString("InstructionType");
        localInstructionType.HandlerClassName = localFFSResultSet.getColumnString("HandlerClassName");
        localInstructionType.SchedulerStartTime = localFFSResultSet.getColumnString("SchedulerStartTime");
        localInstructionType.SchedulerInterval = localFFSResultSet.getColumnInt("SchedulerInterval");
        localInstructionType.DispatchStartTime = localFFSResultSet.getColumnString("DispatchStartTime");
        localInstructionType.DispatchInterval = localFFSResultSet.getColumnInt("DispatchInterval");
        localInstructionType.ResubmitEventSupported = localFFSResultSet.getColumnInt("ResubmitEventSupported");
        localInstructionType.ChkTmAutoRecover = localFFSResultSet.getColumnInt("ChkTmAutoRecover");
        localInstructionType.FileBasedRecovery = localFFSResultSet.getColumnInt("FileBasedRecovery");
        localInstructionType.ActiveFlag = localFFSResultSet.getColumnInt("ActiveFlag");
        localInstructionType.RouteID = localFFSResultSet.getColumnInt("RouteID");
        str = localFFSResultSet.getColumnString("ProcessOnHolidays");
        if (str != null) {
          localInstructionType.processOnHolidays = (str.equalsIgnoreCase("Y"));
        }
        str = localFFSResultSet.getColumnString("CreateEmptyFile");
        if (str != null) {
          localInstructionType.createEmptyFile = (str.equalsIgnoreCase("Y"));
        }
        str = localFFSResultSet.getColumnString("UseCutOffs");
        if (str != null) {
          localInstructionType.useCutOffs = (str.equalsIgnoreCase("Y"));
        }
        localInstructionType.memo = localFFSResultSet.getColumnString("Memo");
        localInstructionType.category = localFFSResultSet.getColumnString("Category");
        localInstructionType.setStatusCode(0);
        localInstructionType.setStatusMsg("Success");
        localArrayList.add(localInstructionType);
      }
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
    }
    return localArrayList;
  }
  
  public static ArrayList readInstructionTypes(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    String str = null;
    try
    {
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT FIId, InstructionType, HandlerClassName, SchedulerStartTime, SchedulerInterval, DispatchStartTime, DispatchInterval, ResubmitEventSupported , ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Memo, Category FROM SCH_InstructionType WHERE FIId=?", arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        InstructionType localInstructionType = new InstructionType();
        localInstructionType.FIId = localFFSResultSet.getColumnString("FIId");
        localInstructionType.InstructionType = localFFSResultSet.getColumnString("InstructionType");
        localInstructionType.HandlerClassName = localFFSResultSet.getColumnString("HandlerClassName");
        localInstructionType.SchedulerStartTime = localFFSResultSet.getColumnString("SchedulerStartTime");
        localInstructionType.SchedulerInterval = localFFSResultSet.getColumnInt("SchedulerInterval");
        localInstructionType.DispatchStartTime = localFFSResultSet.getColumnString("DispatchStartTime");
        localInstructionType.DispatchInterval = localFFSResultSet.getColumnInt("DispatchInterval");
        localInstructionType.ResubmitEventSupported = localFFSResultSet.getColumnInt("ResubmitEventSupported");
        localInstructionType.ChkTmAutoRecover = localFFSResultSet.getColumnInt("ChkTmAutoRecover");
        localInstructionType.FileBasedRecovery = localFFSResultSet.getColumnInt("FileBasedRecovery");
        localInstructionType.ActiveFlag = localFFSResultSet.getColumnInt("ActiveFlag");
        localInstructionType.RouteID = localFFSResultSet.getColumnInt("RouteID");
        str = localFFSResultSet.getColumnString("ProcessOnHolidays");
        if (str != null) {
          localInstructionType.processOnHolidays = (str.equalsIgnoreCase("Y"));
        }
        str = localFFSResultSet.getColumnString("CreateEmptyFile");
        if (str != null) {
          localInstructionType.createEmptyFile = (str.equalsIgnoreCase("Y"));
        }
        str = localFFSResultSet.getColumnString("UseCutOffs");
        if (str != null) {
          localInstructionType.useCutOffs = (str.equalsIgnoreCase("Y"));
        }
        localInstructionType.memo = localFFSResultSet.getColumnString("Memo");
        localInstructionType.category = localFFSResultSet.getColumnString("Category");
        localInstructionType.setStatusCode(0);
        localInstructionType.setStatusMsg("Success");
        localArrayList.add(localInstructionType);
      }
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
    }
    return localArrayList;
  }
  
  public static final InstructionType[] readInstructionTypes(FFSProperties paramFFSProperties)
  {
    FFSDebug.log("*** DBInstructionType.readInstructionType started", 6);
    InstructionType[] arrayOfInstructionType = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      FFSDBProperties localFFSDBProperties = new FFSDBProperties(paramFFSProperties);
      localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(localFFSDBProperties);
      arrayOfInstructionType = (InstructionType[])readInstructionTypes(localFFSConnectionHolder).toArray(new InstructionType[0]);
    }
    catch (Exception localException1)
    {
      String str1 = FFSDebug.stackTrace(localException1);
      FFSDebug.log("*** DBPropertyConfi.readInstructionType failes:" + str1);
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Exception localException2)
      {
        String str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log("*** DBPropertyConfi.readInstructionType failes:" + str2);
      }
    }
    FFSDebug.log("*** DBInstructionType.readInstructionType done", 6);
    return arrayOfInstructionType;
  }
  
  public static final void registerInstructionType(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, String paramString6, String paramString7)
    throws FFSException
  {
    try
    {
      if (paramString6 == null) {
        paramString6 = d(paramFFSConnectionHolder, paramString1, paramString2);
      }
      Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4, new Integer(paramInt1), paramString5, new Integer(paramInt2), new Integer(paramBoolean1 ? 1 : 0), new Integer(paramBoolean2 ? 1 : 0), new Integer(paramBoolean3 ? 1 : 0), new Integer(paramBoolean4 ? 1 : 0), new Integer(paramInt3), new String(paramBoolean7 ? "Y" : "N"), new String(paramBoolean6 ? "Y" : "N"), new String(paramBoolean5 ? "Y" : "N"), paramString6, paramString7 };
      DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO SCH_InstructionType (FIId, InstructionType, HandlerClassName, SchedulerStartTime, SchedulerInterval, DispatchStartTime, DispatchInterval, ResubmitEventSupported , ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Category, Memo )VALUES ( ?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?, ?, ? ,  ? , ? , ? , ? , ? ) ", arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
  }
  
  public static final void updateInstructionType(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, String paramString6, String paramString7)
    throws FFSException
  {
    try
    {
      Object[] arrayOfObject = { paramString3, paramString4, new Integer(paramInt1), paramString5, new Integer(paramInt2), new Integer(paramBoolean1 ? 1 : 0), new Integer(paramBoolean2 ? 1 : 0), new Integer(paramBoolean3 ? 1 : 0), new Integer(paramBoolean4 ? 1 : 0), new Integer(paramInt3), new String(paramBoolean7 ? "Y" : "N"), new String(paramBoolean6 ? "Y" : "N"), new String(paramBoolean5 ? "Y" : "N"), paramString6, paramString7, paramString1, paramString2 };
      DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE SCH_InstructionType SET HandlerClassName=?, SchedulerStartTime=?, SchedulerInterval=?, DispatchStartTime=?, DispatchInterval=?, ResubmitEventSupported =?, ChkTmAutoRecover=?, FileBasedRecovery=?, ActiveFlag=?, RouteID=? , ProcessOnHolidays=?, CreateEmptyFile=?, UseCutOffs=?, Category=?, Memo=? WHERE FIId=? AND InstructionType=?", arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
  }
  
  public static final void deleteInstructionType(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM SCH_InstructionType", null);
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
  }
  
  public static final void deleteInstructionType(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    try
    {
      Object[] arrayOfObject = { paramString1, paramString2 };
      DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM SCH_InstructionType WHERE FIId=? AND InstructionType=? ", arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
  }
  
  public static InstructionType readInstructionType(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSResultSet localFFSResultSet = null;
    InstructionType localInstructionType = null;
    String str1 = null;
    try
    {
      Object[] arrayOfObject = { paramString1, paramString2 };
      FFSDebug.log("DBInstructionType.readInstructionType: fiId: ", paramString1, 6);
      FFSDebug.log("DBInstructionType.readInstructionType: insType: ", paramString2, 6);
      FFSDebug.log("DBInstructionType.readInstructionType: sql: ", "SELECT InstructionType, HandlerClassName, SchedulerStartTime,SchedulerInterval, DispatchStartTime, DispatchInterval,ResubmitEventSupported, ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Category, Memo  FROM SCH_InstructionType WHERE FIId=? AND InstructionType = ? ", 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT InstructionType, HandlerClassName, SchedulerStartTime,SchedulerInterval, DispatchStartTime, DispatchInterval,ResubmitEventSupported, ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Category, Memo  FROM SCH_InstructionType WHERE FIId=? AND InstructionType = ? ", arrayOfObject);
      if (!localFFSResultSet.getNextRow())
      {
        localObject1 = new String[] { paramString2, paramString1 };
        String str2 = BPWLocaleUtil.getMessage(1002000, (String[])localObject1, "SERVER_MESSAGE");
        FFSException localFFSException2 = new FFSException(1002000, str2);
        throw localFFSException2;
      }
      localInstructionType = new InstructionType();
      localInstructionType.FIId = paramString1;
      localInstructionType.InstructionType = localFFSResultSet.getColumnString("InstructionType");
      localInstructionType.HandlerClassName = localFFSResultSet.getColumnString("HandlerClassName");
      localInstructionType.SchedulerStartTime = localFFSResultSet.getColumnString("SchedulerStartTime");
      localInstructionType.SchedulerInterval = localFFSResultSet.getColumnInt("SchedulerInterval");
      localInstructionType.DispatchStartTime = localFFSResultSet.getColumnString("DispatchStartTime");
      localInstructionType.DispatchInterval = localFFSResultSet.getColumnInt("DispatchInterval");
      localInstructionType.ResubmitEventSupported = localFFSResultSet.getColumnInt("ResubmitEventSupported");
      localInstructionType.ChkTmAutoRecover = localFFSResultSet.getColumnInt("ChkTmAutoRecover");
      localInstructionType.FileBasedRecovery = localFFSResultSet.getColumnInt("FileBasedRecovery");
      localInstructionType.ActiveFlag = localFFSResultSet.getColumnInt("ActiveFlag");
      localInstructionType.RouteID = localFFSResultSet.getColumnInt("RouteID");
      str1 = localFFSResultSet.getColumnString("ProcessOnHolidays");
      if (str1 != null) {
        localInstructionType.processOnHolidays = (str1.equalsIgnoreCase("Y"));
      }
      str1 = localFFSResultSet.getColumnString("CreateEmptyFile");
      if (str1 != null) {
        localInstructionType.createEmptyFile = (str1.equalsIgnoreCase("Y"));
      }
      str1 = localFFSResultSet.getColumnString("UseCutOffs");
      if (str1 != null) {
        localInstructionType.useCutOffs = (str1.equalsIgnoreCase("Y"));
      }
      localInstructionType.category = localFFSResultSet.getColumnString("Category");
      localInstructionType.memo = localFFSResultSet.getColumnString("Memo");
      localInstructionType.setStatusCode(0);
      localInstructionType.setStatusMsg("Success");
    }
    catch (FFSException localFFSException1)
    {
      localObject1 = "*** DBInstructionType.readInstructionType. Error: " + FFSDebug.stackTrace(localFFSException1);
      FFSDebug.log((String)localObject1, 0);
      throw localFFSException1;
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = "*** DBInstructionType.readInstructionType. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
    }
    return localInstructionType;
  }
  
  public static ArrayList readInstructionTypesByCategory(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSResultSet localFFSResultSet = null;
    InstructionType localInstructionType = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      Object[] arrayOfObject = { paramString1, paramString2 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT FIId, InstructionType, HandlerClassName, SchedulerStartTime, SchedulerInterval, DispatchStartTime, DispatchInterval, ResubmitEventSupported, ChkTmAutoRecover, FileBasedRecovery, ActiveFlag, RouteID, ProcessOnHolidays, CreateEmptyFile, UseCutOffs, Category , Memo FROM SCH_InstructionType WHERE FIId = ? and Category = ? ", arrayOfObject);
      String str = null;
      while (localFFSResultSet.getNextRow())
      {
        localInstructionType = new InstructionType();
        localInstructionType.FIId = localFFSResultSet.getColumnString("FIId");
        localInstructionType.InstructionType = localFFSResultSet.getColumnString("InstructionType");
        localInstructionType.HandlerClassName = localFFSResultSet.getColumnString("HandlerClassName");
        localInstructionType.SchedulerStartTime = localFFSResultSet.getColumnString("SchedulerStartTime");
        localInstructionType.SchedulerInterval = localFFSResultSet.getColumnInt("SchedulerInterval");
        localInstructionType.DispatchStartTime = localFFSResultSet.getColumnString("DispatchStartTime");
        localInstructionType.DispatchInterval = localFFSResultSet.getColumnInt("DispatchInterval");
        localInstructionType.ResubmitEventSupported = localFFSResultSet.getColumnInt("ResubmitEventSupported");
        localInstructionType.ChkTmAutoRecover = localFFSResultSet.getColumnInt("ChkTmAutoRecover");
        localInstructionType.FileBasedRecovery = localFFSResultSet.getColumnInt("FileBasedRecovery");
        localInstructionType.ActiveFlag = localFFSResultSet.getColumnInt("ActiveFlag");
        localInstructionType.RouteID = localFFSResultSet.getColumnInt("RouteID");
        str = localFFSResultSet.getColumnString("ProcessOnHolidays");
        if (str != null) {
          localInstructionType.processOnHolidays = (str.equalsIgnoreCase("Y"));
        }
        str = localFFSResultSet.getColumnString("CreateEmptyFile");
        if (str != null) {
          localInstructionType.createEmptyFile = (str.equalsIgnoreCase("Y"));
        }
        str = localFFSResultSet.getColumnString("UseCutOffs");
        if (str != null) {
          localInstructionType.useCutOffs = (str.equalsIgnoreCase("Y"));
        }
        localInstructionType.category = localFFSResultSet.getColumnString("Category");
        localInstructionType.memo = localFFSResultSet.getColumnString("Memo");
        localInstructionType.setStatusCode(0);
        localInstructionType.setStatusMsg("Success");
        localArrayList.add(localInstructionType);
      }
      if (localArrayList.size() == 0)
      {
        localInstructionType = new InstructionType();
        localInstructionType.setStatusCode(26001);
        localInstructionType.setStatusMsg("Instruction type does not exist");
        localArrayList.add(localInstructionType);
      }
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
    }
    return localArrayList;
  }
  
  private static CutOffInfo a(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = " DBInstructionType.insertCutOff : ";
    InstructionType localInstructionType = null;
    String str2 = null;
    try
    {
      FFSDebug.log(str1 + "start ", 6);
      if (paramFFSConnectionHolder == null)
      {
        paramCutOffInfo.setStatusCode(16000);
        String str3 = "FFSConnectionHolder object  is null";
        paramCutOffInfo.setStatusMsg(str3);
        FFSDebug.log(str1, str3, 0);
        return paramCutOffInfo;
      }
      try
      {
        localInstructionType = readInstructionType(paramFFSConnectionHolder, paramCutOffInfo.getFIId(), paramCutOffInfo.getInstructionType());
      }
      catch (FFSException localFFSException)
      {
        paramCutOffInfo.setStatusCode(26001);
        paramCutOffInfo.setStatusMsg("Instruction type does not exist");
        return paramCutOffInfo;
      }
      catch (Exception localException)
      {
        throw localException;
      }
      str2 = DBUtil.getNextIndexStringWithPadding("SCHCUTOFFID", 16, '0');
      paramCutOffInfo.setStatus("ACTIVE");
      Object[] arrayOfObject = { str2, paramCutOffInfo.getFIId(), paramCutOffInfo.getInstructionType(), new Integer(paramCutOffInfo.getFrequency()), new Integer(paramCutOffInfo.getDay()), paramCutOffInfo.getProcessTime(), paramCutOffInfo.getExtension(), paramCutOffInfo.getStatus(), paramCutOffInfo.getSubmittedBy(), paramCutOffInfo.getLogId(), paramCutOffInfo.getMemo() };
      DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO SCH_CutOffs ( CutOffId, FIID, InstructionType, Frequency, Day, ProcessTime, Extension, Status, SubmittedBy, LogId, Memo ) VALUES( ?,?,?,?,?,?,?,?,?,?,? )", arrayOfObject);
      paramCutOffInfo.setCutOffId(str2);
      paramCutOffInfo.setStatusCode(0);
      paramCutOffInfo.setStatusMsg("Success");
      FFSDebug.log(str1, "end ", 6);
      return paramCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str4, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  public static CutOffInfo updateCutOff(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    Object localObject = null;
    InstructionType localInstructionType = null;
    String str1 = " DBInstructionType.updateCutOff : ";
    CutOffInfo localCutOffInfo = null;
    try
    {
      FFSDebug.log(str1, "start ", 6);
      String str2;
      if (paramFFSConnectionHolder == null)
      {
        paramCutOffInfo.setStatusCode(16000);
        str2 = "FFSConnectionHolder object  is null";
        paramCutOffInfo.setStatusMsg(str2);
        FFSDebug.log(str1, str2, 0);
        return paramCutOffInfo;
      }
      if (paramCutOffInfo == null)
      {
        paramCutOffInfo = new CutOffInfo();
        paramCutOffInfo.setStatusCode(16000);
        str2 = "CutOffInfo  is null";
        paramCutOffInfo.setStatusMsg(str2);
        FFSDebug.log(str1 + str2, 0);
        return paramCutOffInfo;
      }
      if ((paramCutOffInfo.getCutOffId() == null) || (paramCutOffInfo.getCutOffId().trim().equals("")))
      {
        paramCutOffInfo.setStatusCode(26004);
        paramCutOffInfo.setStatusMsg("CutOffId is null");
        FFSDebug.log(str1 + "CutOffId is null", 0);
        return paramCutOffInfo;
      }
      validateCutOff(paramCutOffInfo);
      if (paramCutOffInfo.getStatusCode() != 0) {
        return paramCutOffInfo;
      }
      if ((paramCutOffInfo.getStatus() == null) || (paramCutOffInfo.getStatus().trim().equals("")))
      {
        paramCutOffInfo.setStatusCode(26021);
        paramCutOffInfo.setStatusMsg("CutOff Status is null");
        return paramCutOffInfo;
      }
      if ((!paramCutOffInfo.getStatus().trim().equals("ACTIVE")) && (!paramCutOffInfo.getStatus().trim().equals("INACTIVE")))
      {
        paramCutOffInfo.setStatusCode(26022);
        paramCutOffInfo.setStatusMsg("CutOff Status should be either ACTIVE or INACTIVE");
        return paramCutOffInfo;
      }
      try
      {
        localInstructionType = readInstructionType(paramFFSConnectionHolder, paramCutOffInfo.getFIId(), paramCutOffInfo.getInstructionType());
      }
      catch (FFSException localFFSException)
      {
        paramCutOffInfo.setStatusCode(26001);
        paramCutOffInfo.setStatusMsg("Instruction type does not exist");
        return paramCutOffInfo;
      }
      catch (Exception localException)
      {
        throw localException;
      }
      localCutOffInfo = new CutOffInfo();
      localCutOffInfo.setCutOffId(paramCutOffInfo.getCutOffId());
      localCutOffInfo = getCutOffById(paramFFSConnectionHolder, localCutOffInfo);
      if (localCutOffInfo.getStatusCode() != 0)
      {
        paramCutOffInfo.setStatusCode(localCutOffInfo.getStatusCode());
        paramCutOffInfo.setStatusMsg(localCutOffInfo.getStatusMsg());
        return paramCutOffInfo;
      }
      if ((!localCutOffInfo.getFIId().equals(paramCutOffInfo.getFIId())) || (!localCutOffInfo.getInstructionType().equals(paramCutOffInfo.getInstructionType())))
      {
        paramCutOffInfo.setStatusCode(26014);
        paramCutOffInfo.setStatusMsg("Instruction type cannot be modified");
        return paramCutOffInfo;
      }
      checkCutOffExists(paramFFSConnectionHolder, paramCutOffInfo);
      if (paramCutOffInfo.getStatusCode() != 26005) {
        return paramCutOffInfo;
      }
      Object[] arrayOfObject = { paramCutOffInfo.getFIId(), paramCutOffInfo.getInstructionType(), new Integer(paramCutOffInfo.getFrequency()), new Integer(paramCutOffInfo.getDay()), paramCutOffInfo.getProcessTime(), paramCutOffInfo.getExtension(), paramCutOffInfo.getStatus(), paramCutOffInfo.getSubmittedBy(), paramCutOffInfo.getLogId(), paramCutOffInfo.getMemo(), paramCutOffInfo.getCutOffId() };
      int i = 0;
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE SCH_CutOffs SET  FIID= ?, InstructionType = ?, Frequency = ?, Day = ?, ProcessTime = ?, Extension = ?, Status = ?, SubmittedBy = ?, LogId = ?, Memo = ? WHERE CutOffId = ? ", arrayOfObject);
      if (i > 0)
      {
        paramCutOffInfo.setStatusCode(0);
        paramCutOffInfo.setStatusMsg("Success");
      }
      else
      {
        paramCutOffInfo.setStatusCode(26005);
        paramCutOffInfo.setStatusMsg("No CutOff exists");
      }
      FFSDebug.log(str1 + "end ", 6);
      return paramCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str3, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static CutOffInfo getCutOffById(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str = " DBInstructionType. getCutOffById :";
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    try
    {
      FFSDebug.log(str, "start ", 6);
      if (paramFFSConnectionHolder == null)
      {
        paramCutOffInfo.setStatusCode(16000);
        localObject2 = "FFSConnectionHolder object  is null";
        paramCutOffInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str, (String)localObject2, 0);
        localObject3 = paramCutOffInfo;
        return localObject3;
      }
      if (paramCutOffInfo == null)
      {
        paramCutOffInfo = new CutOffInfo();
        paramCutOffInfo.setStatusCode(16000);
        localObject2 = "CutOffInfo  is null";
        paramCutOffInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str + (String)localObject2, 0);
        localObject3 = paramCutOffInfo;
        return localObject3;
      }
      if ((paramCutOffInfo.getCutOffId() == null) || (paramCutOffInfo.getCutOffId().trim().equals("")))
      {
        paramCutOffInfo.setStatusCode(26004);
        paramCutOffInfo.setStatusMsg("CutOffId is null");
        FFSDebug.log(str + "CutOffId is null", 0);
        localObject2 = paramCutOffInfo;
        return localObject2;
      }
      Object localObject2 = { paramCutOffInfo.getCutOffId() };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CutOffId, FIID, InstructionType, Frequency, Day, ProcessTime, Extension, NextProcessTime, LastProcessTime, Status, SubmittedBy, LogId, Memo FROM SCH_CutOffs WHERE CutOffId = ?  and Status != 'CANCELEDON'", (Object[])localObject2);
      if (localFFSResultSet.getNextRow())
      {
        jdMethod_if(localFFSResultSet, paramCutOffInfo);
      }
      else
      {
        paramCutOffInfo.setStatusCode(26005);
        paramCutOffInfo.setStatusMsg("No CutOff exists");
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject3 = str + "failed " + localThrowable.toString();
      FFSDebug.log((String)localObject3, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject3);
    }
    finally
    {
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
    }
    FFSDebug.log(str, "end ", 6);
    return paramCutOffInfo;
  }
  
  public static ArrayList getCutOffByInstList(FFSConnectionHolder paramFFSConnectionHolder, String paramString, InstructionType[] paramArrayOfInstructionType)
    throws FFSException
  {
    String str1 = "DBInstructionType. getCutOffByInstList ";
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = null;
    CutOffInfo localCutOffInfo = null;
    try
    {
      FFSDebug.log(str1, "start ", 6);
      for (int i = 0; i < paramArrayOfInstructionType.length; i++)
      {
        localArrayList2 = getCutOffByInst(paramFFSConnectionHolder, paramString, paramArrayOfInstructionType[i].InstructionType);
        localCutOffInfo = (CutOffInfo)localArrayList2.get(0);
        if (localCutOffInfo.getStatusCode() == 0) {
          localArrayList1.addAll(localArrayList2);
        }
      }
      if (localArrayList1.size() == 0)
      {
        if (localCutOffInfo == null) {
          localCutOffInfo = new CutOffInfo();
        }
        localCutOffInfo.setStatusCode(26005);
        localCutOffInfo.setStatusMsg("No CutOff exists");
        localArrayList1.add(localCutOffInfo);
      }
      FFSDebug.log(str1, "end ", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    return localArrayList1;
  }
  
  public static ArrayList getCutOffByInst(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "DBInstructionType. getCutOffByInst ";
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    CutOffInfo localCutOffInfo = null;
    try
    {
      FFSDebug.log(str1, "start ", 6);
      Object[] arrayOfObject = { paramString1, paramString2 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CutOffId, FIID, InstructionType, Frequency, Day, ProcessTime, Extension, NextProcessTime, LastProcessTime, Status, SubmittedBy, LogId, Memo FROM SCH_CutOffs WHERE FIID = ? and InstructionType = ? and Status !='CANCELEDON'", arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCutOffInfo = new CutOffInfo();
        jdMethod_if(localFFSResultSet, localCutOffInfo);
        localArrayList.add(localCutOffInfo);
      }
      if (localArrayList.size() == 0)
      {
        localCutOffInfo = new CutOffInfo();
        localCutOffInfo.setStatusCode(26005);
        localCutOffInfo.setStatusMsg("No CutOff exists");
        localArrayList.add(localCutOffInfo);
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
    }
    FFSDebug.log(str1, "end ", 6);
    return localArrayList;
  }
  
  public static CutOffInfoList getCutOffListByFIIDInstStatus(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfoList paramCutOffInfoList)
    throws FFSException
  {
    String str1 = " DBInstructionType.getCutOffListByFIIDInstStatus :";
    FFSResultSet localFFSResultSet = null;
    CutOffInfo localCutOffInfo = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      FFSDebug.log(str1, "start ", 6);
      if (paramCutOffInfoList.getCutOffIdList() != null)
      {
        int i = paramCutOffInfoList.getCutOffIdList().length;
        for (int j = 0; j < i; j++)
        {
          localCutOffInfo = new CutOffInfo();
          localCutOffInfo.setCutOffId(paramCutOffInfoList.getCutOffIdList()[j]);
          localCutOffInfo = getCutOffById(paramFFSConnectionHolder, localCutOffInfo);
          localArrayList.add(localCutOffInfo);
        }
      }
      Object localObject1 = new ArrayList();
      localObject2 = new StringBuffer("SELECT  CutOffId, FIID, InstructionType, Frequency, Day, ProcessTime, Extension, NextProcessTime, LastProcessTime, Status, SubmittedBy, LogId, Memo FROM SCH_CutOffs Where Status != 'CANCELEDON'");
      DBUtil.appendToCondition((StringBuffer)localObject2, paramCutOffInfoList.getFIId(), " AND FIID = ?", (ArrayList)localObject1);
      DBUtil.appendToCondition((StringBuffer)localObject2, paramCutOffInfoList.getInstructionType(), " AND InstructionType = ?", (ArrayList)localObject1);
      DBUtil.appendArrayToCondition((StringBuffer)localObject2, paramCutOffInfoList.getStatusList(), " AND Status IN (", (ArrayList)localObject1);
      Object[] arrayOfObject = (Object[])((ArrayList)localObject1).toArray(new Object[0]);
      String str2 = ((StringBuffer)localObject2).toString();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCutOffInfo = new CutOffInfo();
        jdMethod_if(localFFSResultSet, localCutOffInfo);
        localArrayList.add(localCutOffInfo);
      }
      if (localArrayList.size() == 0)
      {
        paramCutOffInfoList.setStatusCode(16020);
        paramCutOffInfoList.setStatusMsg(" record not found");
      }
      else
      {
        paramCutOffInfoList.setStatusCode(0);
        paramCutOffInfoList.setStatusMsg("Success");
      }
      paramCutOffInfoList.setCutOffInfoList((CutOffInfo[])localArrayList.toArray(new CutOffInfo[0]));
      FFSDebug.log(str1, "end ", 6);
      localObject1 = paramCutOffInfoList;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log((String)localObject2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    finally
    {
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
    }
  }
  
  public static CutOffInfo deleteCutOff(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    Object localObject1 = null;
    String str1 = " DBInstructionType.deleteCutOff : ";
    CutOffInfo localCutOffInfo = null;
    Object localObject2 = null;
    try
    {
      FFSDebug.log(str1, "start :", 6);
      FFSDebug.log(str1, "CutOffId:" + paramCutOffInfo.getCutOffId(), 6);
      if (paramFFSConnectionHolder == null)
      {
        paramCutOffInfo.setStatusCode(16000);
        String str2 = "FFSConnectionHolder object  is null";
        paramCutOffInfo.setStatusMsg(str2);
        FFSDebug.log(str1, str2, 0);
        return paramCutOffInfo;
      }
      localCutOffInfo = getCutOffById(paramFFSConnectionHolder, paramCutOffInfo);
      if (localCutOffInfo.getStatusCode() != 0) {
        return localCutOffInfo;
      }
      paramCutOffInfo.setStatus("CANCELEDON");
      updateCutOffStatus(paramFFSConnectionHolder, paramCutOffInfo);
      CashCon.deleteCompanyCutOffByCutOffId(paramFFSConnectionHolder, paramCutOffInfo);
      FFSDebug.log(str1 + "end ", 6);
      return paramCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str3, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static void updateCutOffStatus(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = " DBInstructionType.updateCutOffStatus : ";
    try
    {
      FFSDebug.log(str1, "start ", 6);
      FFSDebug.log(str1, "CutOffId :" + paramCutOffInfo.getCutOffId(), 6);
      Object[] arrayOfObject = { paramCutOffInfo.getStatus(), paramCutOffInfo.getCutOffId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE SCH_CutOffs SET  Status = ?  WHERE CutOffId = ? ", arrayOfObject);
      paramCutOffInfo.setStatusCode(0);
      paramCutOffInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + "end ", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static CutOffInfo updateCutOffNextProcessTime(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = " DBInstructionType.updateCutOffNextProcessTime : ";
    try
    {
      FFSDebug.log(str1, "start ", 6);
      FFSDebug.log(str1, "CutOffId :" + paramCutOffInfo.getCutOffId(), 6);
      Object[] arrayOfObject = { paramCutOffInfo.getNextProcessTime(), paramCutOffInfo.getCutOffId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE SCH_CutOffs SET NextProcessTime = ?  WHERE CutOffId = ? ", arrayOfObject);
      paramCutOffInfo.setStatusCode(0);
      paramCutOffInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + "end ", 6);
      return paramCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static CutOffInfo removeCutOffExtension(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = " DBInstructionType.removeCutOffExtension : ";
    try
    {
      FFSDebug.log(str1, "start ", 6);
      FFSDebug.log(str1, "CutOffId :" + paramCutOffInfo.getCutOffId(), 6);
      Object[] arrayOfObject = { null, paramCutOffInfo.getCutOffId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE SCH_CutOffs SET  Extension = ?  WHERE CutOffId = ? ", arrayOfObject);
      paramCutOffInfo.setStatusCode(0);
      paramCutOffInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + "end ", 6);
      return paramCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static void checkCutOffExists(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = " DBInstructionType.checkCutOffExists : ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      FFSDebug.log(str1, "start ", 6);
      Object[] arrayOfObject = { paramCutOffInfo.getFIId(), paramCutOffInfo.getInstructionType(), new Integer(paramCutOffInfo.getFrequency()), new Integer(paramCutOffInfo.getDay()), paramCutOffInfo.getProcessTime() };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CutOffId, Status FROM SCH_CutOffs WHERE FIID = ? and InstructionType = ? and Frequency = ? and Day = ?  and ProcessTime = ? and Status != 'CANCELEDON'", arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        str2 = localFFSResultSet.getColumnString("Status");
        str3 = paramCutOffInfo.getCutOffId();
        if ((str3 != null) && (str3.length() != 0))
        {
          String str4 = localFFSResultSet.getColumnString("CutOffId");
          if (str3.equals(str4))
          {
            paramCutOffInfo.setStatusCode(26005);
            paramCutOffInfo.setStatusMsg("No CutOff exists");
          }
        }
      }
      else
      {
        paramCutOffInfo.setStatusCode(26005);
        paramCutOffInfo.setStatusMsg("No CutOff exists");
        return;
      }
      if (str2 != null) {
        if (str2.equalsIgnoreCase("ACTIVE"))
        {
          paramCutOffInfo.setStatusCode(26003);
          paramCutOffInfo.setStatusMsg("Same CutOff already exists");
        }
        else if (str2.equalsIgnoreCase("INACTIVE"))
        {
          paramCutOffInfo.setStatusCode(26010);
          paramCutOffInfo.setStatusMsg("Same CutOff already exists but Status Inactive ");
        }
      }
      FFSDebug.log(str1 + "end ", 6);
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str3, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  private static void jdMethod_if(FFSResultSet paramFFSResultSet, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str = "DBInstructionType.getCutOffInfo(FFSResultSet)";
    FFSDebug.log(str, "start ", 6);
    paramCutOffInfo.setCutOffId(paramFFSResultSet.getColumnString("CutOffId"));
    paramCutOffInfo.setFIId(paramFFSResultSet.getColumnString("FIID"));
    paramCutOffInfo.setInstructionType(paramFFSResultSet.getColumnString("InstructionType"));
    paramCutOffInfo.setFrequency(paramFFSResultSet.getColumnInt("Frequency"));
    paramCutOffInfo.setDay(paramFFSResultSet.getColumnInt("Day"));
    paramCutOffInfo.setProcessTime(paramFFSResultSet.getColumnString("ProcessTime"));
    paramCutOffInfo.setExtension(paramFFSResultSet.getColumnString("Extension"));
    paramCutOffInfo.setNextProcessTime(paramFFSResultSet.getColumnString("NextProcessTime"));
    paramCutOffInfo.setLastProcessTime(paramFFSResultSet.getColumnString("LastProcessTime"));
    paramCutOffInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramCutOffInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramCutOffInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramCutOffInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
    paramCutOffInfo.setStatusCode(0);
    paramCutOffInfo.setStatusMsg("Success");
    FFSDebug.log(str, "end ", 6);
  }
  
  public static boolean isCuttOffUsed(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str = "DBInstructionType.isCutOffUsed ";
    boolean bool = false;
    try
    {
      FFSDebug.log(str, "start ", 6);
      CCCompanyCutOffInfoList localCCCompanyCutOffInfoList = new CCCompanyCutOffInfoList();
      localCCCompanyCutOffInfoList.setCutOffId(paramString);
      localCCCompanyCutOffInfoList = CashCon.getCCCompanyCutOffList(paramFFSConnectionHolder, localCCCompanyCutOffInfoList);
      localObject = localCCCompanyCutOffInfoList.getCCCompanyCutOffInfoList();
      if ((localObject != null) && (localObject.length > 0)) {
        bool = true;
      }
      FFSDebug.log(str, "end, isCuttOffUSed =" + bool, 6);
      return bool;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = str + " has failed";
      FFSDebug.log((String)localObject, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  public static CutOffInfo deleteCutOffTX(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "DBInstructionType.deleteCutOffTX :";
    CutOffInfo localCutOffInfo = null;
    FFSDebug.log(str1 + "start ", 6);
    try
    {
      String str2;
      if (paramCutOffInfo == null)
      {
        localCutOffInfo = new CutOffInfo();
        localCutOffInfo.setStatusCode(16000);
        str2 = "CutOffInfo  is null";
        localCutOffInfo.setStatusMsg(str2);
        FFSDebug.log(str1 + str2, 0);
        return localCutOffInfo;
      }
      if ((paramCutOffInfo.getCutOffId() == null) || (paramCutOffInfo.getCutOffId().trim().equals("")))
      {
        paramCutOffInfo.setStatusCode(26004);
        str2 = "CutOffInfo CutOffId is null";
        paramCutOffInfo.setStatusMsg(str2);
        FFSDebug.log(str1 + str2, 0);
        return paramCutOffInfo;
      }
      if (isCuttOffUsed(paramFFSConnectionHolder, paramCutOffInfo.getCutOffId()))
      {
        paramCutOffInfo.setStatusCode(26017);
        paramCutOffInfo.setStatusMsg("This cutOff is used by a CashConCompany ");
        FFSDebug.log(str1 + 26017, 0);
        return paramCutOffInfo;
      }
      localCutOffInfo = deleteCutOff(paramFFSConnectionHolder, paramCutOffInfo);
      FFSDebug.log(str1 + " : end ", 6);
      return localCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** " + str1 + " failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3);
      throw new FFSException(str3);
    }
  }
  
  public static CutOffInfo addCutOff(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "DBInstructionType.addCutOff :";
    CutOffInfo localCutOffInfo = null;
    FFSDebug.log(str1 + "start ", 6);
    try
    {
      if (paramCutOffInfo == null)
      {
        localCutOffInfo = new CutOffInfo();
        localCutOffInfo.setStatusCode(16000);
        String str2 = "CutOffInfo  is null";
        localCutOffInfo.setStatusMsg(str2);
        FFSDebug.log(str1 + str2, 0);
        return localCutOffInfo;
      }
      if ((paramCutOffInfo.getCutOffId() != null) && (!paramCutOffInfo.getCutOffId().trim().equals("")))
      {
        paramCutOffInfo.setStatusCode(26002);
        paramCutOffInfo.setStatusMsg("CutOffId must be null");
        FFSDebug.log(str1 + "CutOffId must be null", 0);
        return paramCutOffInfo;
      }
      validateCutOff(paramCutOffInfo);
      if (paramCutOffInfo.getStatusCode() != 0) {
        return paramCutOffInfo;
      }
      checkCutOffExists(paramFFSConnectionHolder, paramCutOffInfo);
      if (paramCutOffInfo.getStatusCode() != 26005) {
        return paramCutOffInfo;
      }
      localCutOffInfo = a(paramFFSConnectionHolder, paramCutOffInfo);
      FFSDebug.log(str1 + " end ", 6);
      return localCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str3, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static ScheduleCategoryInfo getScheduleCategoryInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "DBInstructionType.getScheduleCategoryInfo :";
    FFSDebug.log(str1, "start ", 6);
    CutOffInfo[] arrayOfCutOffInfo = null;
    InstructionType[] arrayOfInstructionType = null;
    ScheduleCategoryInfo localScheduleCategoryInfo = new ScheduleCategoryInfo();
    try
    {
      arrayOfInstructionType = (InstructionType[])readInstructionTypesByCategory(paramFFSConnectionHolder, paramString1, paramString2).toArray(new InstructionType[0]);
      if (arrayOfInstructionType[0].getStatusCode() != 0)
      {
        arrayOfCutOffInfo = new CutOffInfo[1];
        arrayOfCutOffInfo[0] = new CutOffInfo();
        arrayOfCutOffInfo[0].setStatusCode(26005);
        arrayOfCutOffInfo[0].setStatusMsg("No CutOff exists");
        localScheduleCategoryInfo.setCutOffInfoList(arrayOfCutOffInfo);
        localScheduleCategoryInfo.setInstructionTypeList(arrayOfInstructionType);
        localScheduleCategoryInfo.setStatusCode(16020);
        localScheduleCategoryInfo.setStatusMsg(" record not found");
        return localScheduleCategoryInfo;
      }
      arrayOfCutOffInfo = (CutOffInfo[])getCutOffByInstList(paramFFSConnectionHolder, paramString1, arrayOfInstructionType).toArray(new CutOffInfo[0]);
      localScheduleCategoryInfo.setCutOffInfoList(arrayOfCutOffInfo);
      localScheduleCategoryInfo.setInstructionTypeList(arrayOfInstructionType);
      localScheduleCategoryInfo.setStatusCode(0);
      localScheduleCategoryInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + " end ", 6);
      return localScheduleCategoryInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
  }
  
  public static ScheduleCategoryInfo modScheduleCategoryInfo(FFSConnectionHolder paramFFSConnectionHolder, ScheduleCategoryInfo paramScheduleCategoryInfo)
    throws FFSException
  {
    String str1 = "DBInstructionType.modScheduleCategoryInfo :";
    FFSDebug.log(str1, "start ", 6);
    CutOffInfo[] arrayOfCutOffInfo = null;
    String str2 = null;
    try
    {
      arrayOfCutOffInfo = paramScheduleCategoryInfo.getCutOffInfoList();
      for (int i = 0; i < arrayOfCutOffInfo.length; i++) {
        if (arrayOfCutOffInfo[i] == null)
        {
          arrayOfCutOffInfo[i] = new CutOffInfo();
          arrayOfCutOffInfo[i].setStatusCode(16000);
          str3 = "CutOffInfo  is null";
          arrayOfCutOffInfo[i].setStatusMsg(str3);
          FFSDebug.log(str1 + str3, 0);
        }
        else
        {
          str2 = arrayOfCutOffInfo[i].getAction();
          if ((str2 != null) && (!str2.trim().equalsIgnoreCase(""))) {
            if (str2.trim().equalsIgnoreCase("Add"))
            {
              FFSDebug.log(str1, "Action Add", 6);
              if ((arrayOfCutOffInfo[i].getCutOffId() != null) && (!arrayOfCutOffInfo[i].getCutOffId().trim().equals("")))
              {
                arrayOfCutOffInfo[i].setStatusCode(26002);
                arrayOfCutOffInfo[i].setStatusMsg("CutOffId must be null");
                FFSDebug.log(str1 + "CutOffId must be null", 0);
              }
              else
              {
                validateCutOff(arrayOfCutOffInfo[i]);
                if (arrayOfCutOffInfo[i].getStatusCode() == 0)
                {
                  checkCutOffExists(paramFFSConnectionHolder, arrayOfCutOffInfo[i]);
                  if (arrayOfCutOffInfo[i].getStatusCode() == 26005) {
                    arrayOfCutOffInfo[i] = a(paramFFSConnectionHolder, arrayOfCutOffInfo[i]);
                  }
                }
              }
            }
            else if (str2.trim().equalsIgnoreCase("Mod"))
            {
              FFSDebug.log(str1, "Action Mod", 6);
              if (arrayOfCutOffInfo[i].getCutOffId() == null)
              {
                arrayOfCutOffInfo[i].setStatusCode(26004);
                arrayOfCutOffInfo[i].setStatusMsg("CutOffId is null");
                FFSDebug.log(str1 + "CutOffId is null", 0);
              }
              else
              {
                validateCutOff(arrayOfCutOffInfo[i]);
                if (arrayOfCutOffInfo[i].getStatusCode() == 0) {
                  arrayOfCutOffInfo[i] = updateCutOff(paramFFSConnectionHolder, arrayOfCutOffInfo[i]);
                }
              }
            }
            else if (str2.trim().equalsIgnoreCase("Can"))
            {
              FFSDebug.log(str1, "Action Can", 6);
              if (arrayOfCutOffInfo[i].getCutOffId() == null)
              {
                arrayOfCutOffInfo[i].setStatusCode(26004);
                arrayOfCutOffInfo[i].setStatusMsg("CutOffId is null");
                FFSDebug.log(str1 + "CutOffId is null", 0);
              }
              else
              {
                deleteCutOff(paramFFSConnectionHolder, arrayOfCutOffInfo[i]);
              }
            }
          }
        }
      }
      paramScheduleCategoryInfo.setCutOffInfoList(arrayOfCutOffInfo);
      paramScheduleCategoryInfo.setStatusCode(0);
      paramScheduleCategoryInfo.setStatusMsg("Success");
      FFSDebug.log(str1, "end ", 6);
      return paramScheduleCategoryInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(str3);
    }
  }
  
  public static void validateCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "DBInstructionType.validateCutOff :";
    FFSDebug.log(str1, "start ", 6);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
    try
    {
      if ((paramCutOffInfo.getFIId() == null) || (paramCutOffInfo.getFIId().trim().equalsIgnoreCase("")))
      {
        paramCutOffInfo.setStatusCode(23170);
        paramCutOffInfo.setStatusMsg("FIId does not exist :");
        FFSDebug.log(str1 + "FIId does not exist :", 0);
        return;
      }
      if ((paramCutOffInfo.getInstructionType() == null) || (paramCutOffInfo.getInstructionType().trim().equalsIgnoreCase("")))
      {
        paramCutOffInfo.setStatusCode(26001);
        paramCutOffInfo.setStatusMsg("Instruction type does not exist");
        FFSDebug.log(str1 + "Instruction type does not exist", 0);
        return;
      }
      if ((paramCutOffInfo.getProcessTime() == null) || (paramCutOffInfo.getProcessTime().trim().equalsIgnoreCase("")))
      {
        paramCutOffInfo.setStatusCode(26016);
        paramCutOffInfo.setStatusMsg("Process Time is Null");
        FFSDebug.log(str1 + "Process Time is Null", 0);
        return;
      }
      String str2 = paramCutOffInfo.getProcessTime().trim();
      if (!BPWUtil.validateDate(str2, "HH:mm"))
      {
        paramCutOffInfo.setStatusCode(26007);
        paramCutOffInfo.setStatusMsg("Process time format is not valid");
        return;
      }
      if ((paramCutOffInfo.getExtension() != null) && (!paramCutOffInfo.getExtension().trim().equalsIgnoreCase("")) && (!BPWUtil.validateDate(paramCutOffInfo.getExtension(), "HH:mm")))
      {
        paramCutOffInfo.setStatusCode(26008);
        paramCutOffInfo.setStatusMsg("Extension time format is not valid");
        return;
      }
      if ((paramCutOffInfo.getLogId() == null) || (paramCutOffInfo.getLogId().trim().equalsIgnoreCase("")))
      {
        paramCutOffInfo.setStatusCode(23870);
        paramCutOffInfo.setStatusMsg("LogID field is null");
        FFSDebug.log(str1 + "LogID field is null", 0);
        return;
      }
      if ((paramCutOffInfo.getSubmittedBy() == null) || (paramCutOffInfo.getSubmittedBy().trim().equalsIgnoreCase("")))
      {
        paramCutOffInfo.setStatusCode(23890);
        paramCutOffInfo.setStatusMsg("SubmittedBy field is null");
        FFSDebug.log(str1 + "SubmittedBy field is null", 0);
        return;
      }
      int i = paramCutOffInfo.getFrequency();
      int j = paramCutOffInfo.getDay();
      switch (i)
      {
      case 9: 
        if ((j != 2) && (j != 3) && (j != 4) && (j != 5) && (j != 6) && (j != 7) && (j != 1))
        {
          paramCutOffInfo.setStatusCode(26018);
          paramCutOffInfo.setStatusMsg("Invalid day for given Frequency of CutOff Frequency: " + i + ", day: " + j);
          FFSDebug.log(str1 + paramCutOffInfo.getStatusMsg(), 0);
          return;
        }
        break;
      default: 
        paramCutOffInfo.setStatusCode(26009);
        paramCutOffInfo.setStatusMsg("26009. We only support WEEKLY. Frequency Received is " + FFSUtil.getFreqString(i));
        FFSDebug.log(str1 + paramCutOffInfo.getStatusMsg(), 0);
        return;
      }
      Calendar localCalendar = Calendar.getInstance();
      int k = localCalendar.get(7);
      String str4 = localSimpleDateFormat.format(new Date());
      if ((paramCutOffInfo.getExtension() != null) && (!paramCutOffInfo.getExtension().trim().equals("")) && (k == j) && (str4.compareTo(paramCutOffInfo.getExtension()) > 0))
      {
        paramCutOffInfo.setStatusCode(26006);
        paramCutOffInfo.setStatusMsg("Extension is earlier than current time");
        FFSDebug.log(str1, paramCutOffInfo.getStatusMsg(), 0);
        return;
      }
      paramCutOffInfo.setStatusCode(0);
      FFSDebug.log(str1, "end ", 6);
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str3, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static CutOffInfo getNextCutOffTime(FFSConnectionHolder paramFFSConnectionHolder, InstructionType paramInstructionType)
    throws FFSException
  {
    CutOffInfoList localCutOffInfoList = null;
    CutOffInfo localCutOffInfo = null;
    CutOffInfo[] arrayOfCutOffInfo = null;
    String[] arrayOfString = null;
    String str1 = "DBInstructionType.getNextCutOffTime: ";
    try
    {
      FFSDebug.log(str1, "Start ", 6);
      if ((paramInstructionType.FIId == null) || (paramInstructionType.FIId.trim().equalsIgnoreCase("")))
      {
        localCutOffInfo = new CutOffInfo();
        localCutOffInfo.setStatusCode(23170);
        localCutOffInfo.setStatusMsg("FIId does not exist :");
        FFSDebug.log(str1 + "FIId does not exist :", 0);
        return localCutOffInfo;
      }
      if ((paramInstructionType.InstructionType == null) || (paramInstructionType.InstructionType.trim().equalsIgnoreCase("")))
      {
        localCutOffInfo = new CutOffInfo();
        localCutOffInfo.setStatusCode(26001);
        localCutOffInfo.setStatusMsg("Instruction type does not exist");
        FFSDebug.log(str1 + "Instruction type does not exist", 0);
        return localCutOffInfo;
      }
      localCutOffInfoList = new CutOffInfoList();
      localCutOffInfoList.setFIId(paramInstructionType.FIId);
      localCutOffInfoList.setInstructionType(paramInstructionType.InstructionType);
      arrayOfString = new String[1];
      arrayOfString[0] = "ACTIVE";
      localCutOffInfoList.setStatusList(arrayOfString);
      getCutOffListByFIIDInstStatus(paramFFSConnectionHolder, localCutOffInfoList);
      if (localCutOffInfoList.getStatusCode() != 0)
      {
        localCutOffInfo = new CutOffInfo();
        localCutOffInfo.setStatusCode(26005);
        localCutOffInfo.setStatusMsg("No CutOff exists");
        FFSDebug.log(str1 + "No CutOff exists", 0);
        return localCutOffInfo;
      }
      arrayOfCutOffInfo = localCutOffInfoList.getCutOffInfoList();
      if (arrayOfCutOffInfo.length == 0)
      {
        localCutOffInfo = new CutOffInfo();
        localCutOffInfo.setStatusCode(26005);
        localCutOffInfo.setStatusMsg("No CutOff exists");
        FFSDebug.log(str1 + "No CutOff exists", 0);
        return localCutOffInfo;
      }
      int i = 0;
      str2 = null;
      for (int j = 0; j < arrayOfCutOffInfo.length; j++) {
        if (arrayOfCutOffInfo[j].getStatusCode() == 0)
        {
          computeNextProcessTime(arrayOfCutOffInfo[j], paramInstructionType.processOnHolidays);
          localCutOffInfo = updateCutOffNextProcessTime(paramFFSConnectionHolder, arrayOfCutOffInfo[j]);
          if (localCutOffInfo.getStatusCode() != 0)
          {
            String str3 = "Failed to update NextProcessTime for CutOff withCutOffId :" + arrayOfCutOffInfo[j].getCutOffId();
            FFSDebug.log(str1, str3, 0);
          }
          else if ((str2 == null) || (str2.compareTo(arrayOfCutOffInfo[j].getNextProcessTime().trim()) > 0))
          {
            str2 = arrayOfCutOffInfo[j].getNextProcessTime().trim();
            i = j;
          }
        }
      }
      FFSDebug.log(str1, "End ", 6);
      return arrayOfCutOffInfo[i];
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static CutOffInfo setLastProcessTime(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "DBInstructionType.setLastProcessTime: ";
    try
    {
      FFSDebug.log(str1, "Start ", 6);
      String str2 = paramCutOffInfo.getNextProcessTime();
      paramCutOffInfo.setLastProcessTime(str2);
      localObject = new Object[] { str2, paramCutOffInfo.getCutOffId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE SCH_CutOffs SET LastProcessTime = ?  WHERE CutOffId = ? ", (Object[])localObject);
      paramCutOffInfo.setStatusCode(0);
      paramCutOffInfo.setStatusMsg("Success");
      FFSDebug.log(str1, "End ", 6);
      return paramCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = str1 + "failed " + localThrowable.toString();
      FFSDebug.log((String)localObject, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  public static void computeNextProcessTime(CutOffInfo paramCutOffInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "DBInstructionType.computeNextProcessTime: ";
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");
    try
    {
      FFSDebug.log(str1, "start for CutOffID :" + paramCutOffInfo.getCutOffId(), 6);
      int i = paramCutOffInfo.getFrequency();
      if (i != 9)
      {
        localObject = "Failed to compute NextProcessTime for CutOff withCutOffId :" + paramCutOffInfo.getCutOffId() + "reason :CutOff frequency not supported";
        FFSDebug.log(str1, (String)localObject, 0);
        throw new FFSException((String)localObject);
      }
      localObject = Calendar.getInstance();
      int j = ((Calendar)localObject).get(7);
      String str2 = localSimpleDateFormat1.format(new Date());
      String str3 = paramCutOffInfo.getProcessTime();
      if ((paramCutOffInfo.getExtension() != null) && (!paramCutOffInfo.getExtension().trim().equals("")) && (j == paramCutOffInfo.getDay()) && (str2.compareTo(paramCutOffInfo.getExtension()) < 0)) {
        str3 = paramCutOffInfo.getExtension();
      }
      String str4 = BPWUtil.getDateInNewFormat(str3, "HH:mm", "HHmm");
      FFSDebug.log(str1, "prsTime = " + str4, 6);
      String str5 = BPWUtil.getDateInNewFormat(str2, "HH:mm", "HHmm");
      FFSDebug.log(str1, "currentTime = " + str5, 6);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar = a(paramCutOffInfo, j, str5, str4, localCalendar);
      int k = Integer.parseInt(localSimpleDateFormat2.format(((Calendar)localObject).getTime()));
      int m = Integer.parseInt(localSimpleDateFormat2.format(localCalendar.getTime()));
      if (!paramBoolean)
      {
        FFSDebug.log(str1, "Process on Holiday Not Set ", 6);
        for (int n = SmartCalendar.getPayday(String.valueOf(paramCutOffInfo.getFIId()), m); m != n; n = SmartCalendar.getPayday(String.valueOf(paramCutOffInfo.getFIId()), m))
        {
          String str7 = "Future cutOff run date " + m + " is a non Business  day ";
          FFSDebug.log(str1, str7, 6);
          localCalendar.add(3, 1);
          localCalendar.set(7, paramCutOffInfo.getDay());
          m = Integer.parseInt(localSimpleDateFormat2.format(localCalendar.getTime()));
        }
      }
      if ((paramCutOffInfo.getExtension() != null) && (!paramCutOffInfo.getExtension().trim().equals("")) && (m != k)) {
        str3 = paramCutOffInfo.getExtension();
      }
      String str6 = BPWUtil.getDateInNewFormat(String.valueOf(m), "yyyyMMdd", "yyyy/MM/dd") + " " + str3;
      FFSDebug.log(str1, "Next cutOff processing time =" + str6, 6);
      paramCutOffInfo.setNextProcessTime(str6);
      paramCutOffInfo.setStatusCode(0);
      paramCutOffInfo.setStatusMsg("Success");
      FFSDebug.log(str1, "End", 6);
    }
    catch (Exception localException)
    {
      Object localObject = str1 + "failed " + localException.toString();
      FFSDebug.log((String)localObject, FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, (String)localObject);
    }
  }
  
  private static Calendar a(CutOffInfo paramCutOffInfo, int paramInt, String paramString1, String paramString2, Calendar paramCalendar)
    throws FFSException
  {
    String str1 = "DBInstructionType.getNextCalendarForWeek :";
    try
    {
      FFSDebug.log(str1, "Start", 6);
      if (paramInt == paramCutOffInfo.getDay())
      {
        if (Integer.parseInt(paramString1) >= Integer.parseInt(paramString2))
        {
          paramCalendar.add(3, 1);
          paramCalendar.set(7, paramCutOffInfo.getDay());
        }
      }
      else if (paramInt < paramCutOffInfo.getDay())
      {
        int i = paramCutOffInfo.getDay() - paramInt;
        FFSDebug.log(str1, "daysTillNextProcessDay =" + i, 6);
        paramCalendar.add(7, i);
      }
      else
      {
        paramCalendar.add(3, 1);
        paramCalendar.set(7, paramCutOffInfo.getDay());
      }
      FFSDebug.log(str1, "End", 6);
      return paramCalendar;
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed " + localException.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str2);
    }
  }
  
  public static CutOffActivityInfoList getCutOffActivityList(FFSConnectionHolder paramFFSConnectionHolder, CutOffActivityInfoList paramCutOffActivityInfoList)
    throws FFSException
  {
    String str1 = "DBInstructionType.getCutOffActivityList: ";
    FFSDebug.log(str1, "End", 6);
    String[] arrayOfString = paramCutOffActivityInfoList.getCutOffIdList();
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      paramCutOffActivityInfoList.setStatusCode(26004);
      paramCutOffActivityInfoList.setStatusMsg("The list of CutOffId is null");
      return paramCutOffActivityInfoList;
    }
    int i = arrayOfString.length;
    String str2 = paramCutOffActivityInfoList.getStartDate();
    if (str2 == null)
    {
      paramCutOffActivityInfoList.setStatusCode(27002);
      paramCutOffActivityInfoList.setStatusMsg("The FromDate parameter is null");
      return paramCutOffActivityInfoList;
    }
    String str3 = paramCutOffActivityInfoList.getEndDate();
    if (str3 == null)
    {
      paramCutOffActivityInfoList.setStatusCode(27003);
      paramCutOffActivityInfoList.setStatusMsg("The ToDate parameter is null");
      return paramCutOffActivityInfoList;
    }
    String str4 = "SELECT SchHistID, LogDate, ScheduleName, FIId, InstructionType, ServerName, EventType, EventTrigger, EventDescription, ProcessId, FileName, CutOffId, CutOffDay, CutOffProcessTime, CutOffExtension FROM SCH_ScheduleHistory WHERE ProcessId IN ( SELECT DISTINCT ProcessId FROM SCH_ScheduleHistory WHERE CutOffId = ? AND LogDate >= ? AND LogDate <= ? AND EventType = ? )  Order By ProcessId, LogDate, SchHistID ";
    Object[] arrayOfObject = { arrayOfString[0], str2, str3, "ProcessingStart" };
    ArrayList localArrayList = new ArrayList();
    try
    {
      for (int j = 0; j < i; j++)
      {
        arrayOfObject[0] = arrayOfString[j];
        jdMethod_if(paramFFSConnectionHolder, str4, arrayOfObject, localArrayList, str2);
      }
    }
    catch (FFSException localFFSException)
    {
      str5 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str5);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str5 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str5);
      throw new FFSException(str5);
    }
    paramCutOffActivityInfoList.setCutOffActivity((CutOffActivityInfo[])localArrayList.toArray(new CutOffActivityInfo[0]));
    if (localArrayList.size() == 0)
    {
      paramCutOffActivityInfoList.setStatusCode(16020);
      paramCutOffActivityInfoList.setStatusMsg(" record not found");
    }
    else
    {
      paramCutOffActivityInfoList.setStatusCode(0);
      paramCutOffActivityInfoList.setStatusMsg("Success");
    }
    FFSDebug.log(str1 + "end, status =" + paramCutOffActivityInfoList.getStatusMsg(), 6);
    return paramCutOffActivityInfoList;
  }
  
  private static void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, Object[] paramArrayOfObject, ArrayList paramArrayList, String paramString2)
    throws FFSException
  {
    String str1 = "DBInstructionType.getCutOffActivityForEachCutOffId :";
    FFSDebug.log(str1 + "start", 6);
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString1, paramArrayOfObject);
      Object localObject2 = "";
      str2 = "";
      String str3 = "";
      String str4 = "";
      String str5 = "";
      while (localFFSResultSet.getNextRow())
      {
        str2 = localFFSResultSet.getColumnString("ProcessId");
        str3 = localFFSResultSet.getColumnString("EventType");
        str5 = localFFSResultSet.getColumnString("LogDate");
        if (!str2.equals(localObject2))
        {
          if (localObject1 != null)
          {
            if ((!str4.equals("Exception")) && (!str4.equals("RecoveryStop")) && (!str4.equals("ProcessingStop")))
            {
              ((CutOffActivityInfo)localObject1).setStatus("PROCESSING");
              ((CutOffActivityInfo)localObject1).setEndTime(null);
            }
            paramArrayList.add(localObject1);
            localObject1 = null;
          }
          if ((str3.equals("ProcessingStart") == true) && (str5.compareTo(paramString2) >= 0))
          {
            localObject1 = new CutOffActivityInfo();
            ((CutOffActivityInfo)localObject1).setStartTime(str5);
            ((CutOffActivityInfo)localObject1).setProcessId(str2);
            ((CutOffActivityInfo)localObject1).setCutOffId(localFFSResultSet.getColumnString("CutOffId"));
            ((CutOffActivityInfo)localObject1).setCutOffDay(localFFSResultSet.getColumnString("CutOffDay"));
            ((CutOffActivityInfo)localObject1).setCutOffProcessTime(localFFSResultSet.getColumnString("CutOffProcessTime"));
            ((CutOffActivityInfo)localObject1).setCutOffExtension(localFFSResultSet.getColumnString("CutOffExtension"));
          }
          localObject2 = str2;
        }
        else if (localObject1 != null)
        {
          if (str3.equals("Exception"))
          {
            ((CutOffActivityInfo)localObject1).setEndTime(str5);
            ((CutOffActivityInfo)localObject1).setStatus("FAILED");
          }
          else if ((str3.equals("RecoveryStop")) || (str3.equals("ProcessingStop")))
          {
            ((CutOffActivityInfo)localObject1).setEndTime(str5);
            ((CutOffActivityInfo)localObject1).setStatus("COMPLETED");
          }
        }
        str4 = str3;
      }
      if (localObject1 != null)
      {
        if ((!str3.equals("Exception")) && (!str3.equals("RecoveryStop")) && (!str3.equals("ProcessingStop")))
        {
          ((CutOffActivityInfo)localObject1).setStatus("PROCESSING");
          ((CutOffActivityInfo)localObject1).setEndTime(null);
        }
        paramArrayList.add(localObject1);
        localObject1 = null;
      }
    }
    catch (FFSException localFFSException)
    {
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end", 6);
  }
  
  public static ScheduleActivityList getScheduleActivityList(FFSConnectionHolder paramFFSConnectionHolder, ScheduleActivityList paramScheduleActivityList)
    throws FFSException
  {
    String str1 = "DBInstructionType.getScheduleActivityList: ";
    FFSDebug.log(str1 + "begin.", 6);
    String[] arrayOfString = paramScheduleActivityList.getInstructionTypeList();
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      paramScheduleActivityList.setStatusCode(27001);
      paramScheduleActivityList.setStatusMsg("The list of Instruction Type is null");
      return paramScheduleActivityList;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String str2 = paramScheduleActivityList.getFromDate();
    if (str2 == null)
    {
      paramScheduleActivityList.setStatusCode(27002);
      paramScheduleActivityList.setStatusMsg("The FromDate parameter is null");
      return paramScheduleActivityList;
    }
    String str3 = paramScheduleActivityList.getToDate();
    if (str3 == null)
    {
      paramScheduleActivityList.setStatusCode(27003);
      paramScheduleActivityList.setStatusMsg("The ToDate parameter is null");
      return paramScheduleActivityList;
    }
    String str4 = "SELECT SchHistID, LogDate, ScheduleName, FIId, InstructionType, ServerName, EventType, EventTrigger, EventDescription, ProcessId, FileName, CutOffId, CutOffDay, CutOffProcessTime, CutOffExtension FROM SCH_ScheduleHistory WHERE ProcessId IN ( SELECT DISTINCT ProcessId FROM SCH_ScheduleHistory WHERE InstructionType = ? AND LogDate >= ? AND LogDate <= ? AND EventType = ?";
    Object[] arrayOfObject = { arrayOfString[0], str2, str3, "ProcessingStart" };
    if (paramScheduleActivityList.getFIID() != null)
    {
      str4 = str4 + " AND FIId = ? ";
      arrayOfObject = new Object[5];
      arrayOfObject[0] = arrayOfString[0];
      arrayOfObject[1] = str2;
      arrayOfObject[2] = str3;
      arrayOfObject[3] = "ProcessingStart";
      arrayOfObject[4] = paramScheduleActivityList.getFIID();
    }
    str4 = str4 + " )  Order By ProcessId, LogDate, SchHistID ";
    ArrayList localArrayList = new ArrayList();
    try
    {
      for (int i = 0; i < arrayOfString.length; i++) {
        if (arrayOfString[i] != null)
        {
          arrayOfObject[0] = arrayOfString[i];
          a(paramFFSConnectionHolder, str4, arrayOfObject, localArrayList, str2);
        }
      }
    }
    catch (FFSException localFFSException)
    {
      str5 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str5);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str5 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str5);
      throw new FFSException(str5);
    }
    paramScheduleActivityList.setScheduleActivity((ScheduleActivityInfo[])localArrayList.toArray(new ScheduleActivityInfo[0]));
    if (localArrayList.size() == 0)
    {
      paramScheduleActivityList.setStatusCode(16020);
      paramScheduleActivityList.setStatusMsg(" record not found");
    }
    else
    {
      paramScheduleActivityList.setStatusCode(0);
      paramScheduleActivityList.setStatusMsg("Success");
    }
    FFSDebug.log(str1 + "end. status = " + paramScheduleActivityList.getStatusMsg(), 6);
    return paramScheduleActivityList;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, Object[] paramArrayOfObject, ArrayList paramArrayList, String paramString2)
    throws FFSException
  {
    String str1 = "DBInstructionType.getScheduleActivityForEachSchedule :";
    FFSDebug.log(str1 + "start", 6);
    Object localObject1 = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString1, paramArrayOfObject);
      String str2 = "";
      str3 = "";
      Object localObject2 = "";
      while (localFFSResultSet.getNextRow())
      {
        String str4 = localFFSResultSet.getColumnString("LogDate");
        str2 = localFFSResultSet.getColumnString("EventType");
        String str5 = localFFSResultSet.getColumnString("ProcessId");
        String str6 = localFFSResultSet.getColumnString("FileName");
        String str7 = localFFSResultSet.getColumnString("CutOffId");
        if (!str5.equals(localObject2))
        {
          if (localObject1 != null)
          {
            if ((!str3.equals("Exception")) && (!str3.equals("RecoveryStop")) && (!str3.equals("ProcessingStop")))
            {
              ((ScheduleActivityInfo)localObject1).setStatus("PROCESSING");
              ((ScheduleActivityInfo)localObject1).setEndTime(null);
            }
            paramArrayList.add(localObject1);
            localObject1 = null;
          }
          if ((str2.equals("ProcessingStart") == true) && (str4.compareTo(paramString2) >= 0))
          {
            localObject1 = new ScheduleActivityInfo();
            ((ScheduleActivityInfo)localObject1).setStartTime(str4);
            ((ScheduleActivityInfo)localObject1).setProcessId(str5);
            ((ScheduleActivityInfo)localObject1).setCutOffId(str7);
          }
          localObject2 = str5;
        }
        else if (localObject1 != null)
        {
          if (str2.equals("ProcessingFileGenerated"))
          {
            ((ScheduleActivityInfo)localObject1).setFileName(str6);
          }
          else if (str2.equals("Exception"))
          {
            ((ScheduleActivityInfo)localObject1).setEndTime(str4);
            ((ScheduleActivityInfo)localObject1).setStatus("FAILED");
          }
          else if ((str2.equals("RecoveryStop")) || (str2.equals("ProcessingStop")))
          {
            ((ScheduleActivityInfo)localObject1).setEndTime(str4);
            ((ScheduleActivityInfo)localObject1).setStatus("COMPLETED");
          }
        }
        str3 = str2;
      }
      if (localObject1 != null)
      {
        if ((!str2.equals("Exception")) && (!str2.equals("RecoveryStop")) && (!str2.equals("ProcessingStop")))
        {
          ((ScheduleActivityInfo)localObject1).setStatus("PROCESSING");
          ((ScheduleActivityInfo)localObject1).setEndTime(null);
        }
        paramArrayList.add(localObject1);
        localObject1 = null;
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str3 = str1 + "failed:" + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3);
      throw new FFSException(str3);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end", 6);
  }
  
  public static String getGeneratedFileName(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "DBInstructionType.getGeneratedFileName: ";
    FFSDebug.log(str1 + "end", 6);
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str3 = "SELECT SchHistID, LogDate, ScheduleName, FIId, InstructionType, ServerName, EventType, EventTrigger, EventDescription, ProcessId, FileName, CutOffId, CutOffDay, CutOffProcessTime, CutOffExtension FROM SCH_ScheduleHistory WHERE  FIId = ? AND InstructionType = ? AND ProcessId = ? AND FileName is not null ";
      str3 = str3 + " Order By LogDate Desc";
      localObject1 = new Object[] { paramString1, paramString2, paramString3 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, (Object[])localObject1);
      String str4 = null;
      if (localFFSResultSet.getNextRow()) {
        str4 = localFFSResultSet.getColumnString("FileName");
      }
      if (str4 == null) {
        str2 = "";
      } else {
        str2 = "\\" + paramString4 + ":" + str4;
      }
    }
    catch (FFSException localFFSException)
    {
      localObject1 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject1);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      Object localObject1 = str1 + "failed:" + FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject1);
      throw new FFSException((String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end fileName =" + str2, 6);
    return str2;
  }
  
  private static String d(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    String str1 = "Other";
    String str2 = BPWUtil.trimInstructionType(paramString2);
    Object[] arrayOfObject = { paramString1, str2 };
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT Category FROM SCH_InstructionType WHERE FIId=? AND InstructionType = ? ", arrayOfObject);
    if (localFFSResultSet.getNextRow()) {
      str1 = localFFSResultSet.getColumnString("Category");
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.DBInstructionType
 * JD-Core Version:    0.7.0.1
 */