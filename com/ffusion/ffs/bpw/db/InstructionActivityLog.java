package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class InstructionActivityLog
  implements Serializable, DBConsts, FFSConst
{
  public int LogType;
  public String LogDate;
  public String LogID;
  public String InstructionType;
  public String InstructionID;
  public String Content;
  public int LogLevel;
  
  public InstructionActivityLog() {}
  
  public InstructionActivityLog(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2)
  {
    this.LogType = paramInt1;
    this.LogDate = paramString1;
    this.LogID = paramString2;
    this.InstructionType = paramString3;
    this.InstructionID = paramString4;
    this.Content = paramString5;
    this.LogLevel = paramInt2;
  }
  
  public InstructionActivityLog(String paramString)
    throws BPWException
  {
    this.LogID = paramString;
    FFSDebug.log("InstructionActivityLog.constructor start, logID= " + paramString, 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str = "SELECT LogType, LogDate, LogID, InstructionType, InstructionID, Content, LogLevel FROM BPW_InstructionActivityLog WHERE LogID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        this.LogType = localFFSResultSet.getColumnInt(1);
        this.LogDate = localFFSResultSet.getColumnString(2);
        this.LogID = localFFSResultSet.getColumnString(3);
        this.InstructionType = localFFSResultSet.getColumnString(4);
        this.InstructionID = localFFSResultSet.getColumnString(5);
        this.Content = localFFSResultSet.getColumnString(6);
        this.LogLevel = localFFSResultSet.getColumnInt(7);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Exception in InstructionActivityLog:" + localException1.toString());
      throw new BPWException(localException1.toString());
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
        FFSDebug.log("*** Exception in InstructionActivityLog: failed:" + localException2.toString());
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("InstructionActivityLog.constructor done, logID= " + paramString, 6);
  }
  
  public static boolean createLog(FFSConnectionHolder paramFFSConnectionHolder, int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
    throws BPWException
  {
    FFSDebug.log("InstructionActivityLog.createLog start, logID= " + paramString1, 6);
    String str1 = DBUtil.getCurrentLogDate();
    String str2 = "INSERT INTO BPW_InstructionActivityLog (LogType, LogDate, LogID, InstructionType, InstructionID, Content, LogLevel) VALUES (?,?,?,?,?,?,?)";
    try
    {
      Object[] arrayOfObject = { new Integer(paramInt1), str1, paramString1, paramString2, paramString3, paramString4, new Integer(paramInt2) };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Error in InstructionActivityLog.creatLog: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("InstructionActivityLog.createLog done, logID= " + paramString1, 6);
    return true;
  }
  
  public static InstructionActivityLog[] getInstructionActivityLogs(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    Vector localVector = new Vector();
    FFSDebug.log("InstructionActivityLog.getInstructionActivityLogs start, logID=" + paramString, 6);
    String str = "SELECT LogType, LogDate, LogID, InstructionType, InstructionID, Content, LogLevel FROM BPW_InstructionActivityLog WHERE LogID=? ORDER BY LogDate ASC";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        InstructionActivityLog localInstructionActivityLog = new InstructionActivityLog();
        localInstructionActivityLog.LogType = localFFSResultSet.getColumnInt(1);
        localInstructionActivityLog.LogDate = localFFSResultSet.getColumnString(2);
        localInstructionActivityLog.LogID = localFFSResultSet.getColumnString(3);
        localInstructionActivityLog.InstructionType = localFFSResultSet.getColumnString(4);
        localInstructionActivityLog.InstructionID = localFFSResultSet.getColumnString(5);
        localInstructionActivityLog.Content = localFFSResultSet.getColumnString(6);
        localInstructionActivityLog.LogLevel = localFFSResultSet.getColumnInt(7);
        localVector.addElement(localInstructionActivityLog);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** InstructionActivityLog.getInstructionActivityLogs: " + localException1.toString());
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
        FFSDebug.log("*** InstructionActivityLog.getInstructionActivityLogs failed:" + localException2.toString());
      }
    }
    if (localVector.isEmpty()) {
      return null;
    }
    FFSDebug.log("InstructionActivityLog.getInstructionActivityLogs done,vec.size(): " + localVector.size(), 6);
    return (InstructionActivityLog[])localVector.toArray(new InstructionActivityLog[0]);
  }
  
  public static InstructionActivityLog[] getInstructionActivityLogs(FFSConnection paramFFSConnection, String paramString)
    throws FFSException
  {
    Vector localVector = new Vector();
    FFSDebug.log("InstructionActivityLog.getReqActivityLogs start, logID=" + paramString, 6);
    String str = "SELECT LogType, LogDate, LogID, InstructionType, InstructionID, Content, LogLevel FROM BPW_InstructionActivityLog WHERE LogID=? ORDER BY LogDate ASC";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = paramFFSConnection.prepareStmt(str);
      localFFSResultSet.open(arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        InstructionActivityLog localInstructionActivityLog = new InstructionActivityLog();
        localInstructionActivityLog.LogType = localFFSResultSet.getColumnInt(1);
        localInstructionActivityLog.LogDate = localFFSResultSet.getColumnString(2);
        localInstructionActivityLog.LogID = localFFSResultSet.getColumnString(3);
        localInstructionActivityLog.InstructionType = localFFSResultSet.getColumnString(4);
        localInstructionActivityLog.InstructionID = localFFSResultSet.getColumnString(5);
        localInstructionActivityLog.Content = localFFSResultSet.getColumnString(6);
        localInstructionActivityLog.LogLevel = localFFSResultSet.getColumnInt(7);
        localVector.addElement(localInstructionActivityLog);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** InstructionActivityLog.getReqActivityLogs : " + localException1.toString());
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
        FFSDebug.log("*** InstructionActivityLog.getReqActivityLogs failed:" + localException2.toString());
      }
    }
    if (localVector.isEmpty()) {
      return null;
    }
    FFSDebug.log("InstructionActivityLog.getInstructionActivityLogs done ,vec.size(): " + localVector.size(), 6);
    return (InstructionActivityLog[])localVector.toArray(new InstructionActivityLog[0]);
  }
  
  public static InstructionActivityLog[] getInstructionActivityLogs(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    Vector localVector = new Vector();
    FFSDebug.log("InstructionActivityLog.getReqActivityLogs by instructionID AND instructionType: start", 6);
    String str = "SELECT LogType, LogDate, LogID, InstructionType, InstructionID, Content, LogLevel FROM BPW_InstructionActivityLog WHERE LogID IN (SELECT DISTINCT LogID FROM BPW_InstructionActivityLog WHERE InstructionID=? AND InstructionType=? AND LogType>6 AND LogType<=14) ORDER BY LogID ASC, LogDate ASC";
    Object[] arrayOfObject = { paramString1, paramString2 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        InstructionActivityLog localInstructionActivityLog = new InstructionActivityLog();
        localInstructionActivityLog.LogType = localFFSResultSet.getColumnInt(1);
        localInstructionActivityLog.LogDate = localFFSResultSet.getColumnString(2);
        localInstructionActivityLog.LogID = localFFSResultSet.getColumnString(3);
        localInstructionActivityLog.InstructionType = localFFSResultSet.getColumnString(4);
        localInstructionActivityLog.InstructionID = localFFSResultSet.getColumnString(5);
        localInstructionActivityLog.Content = localFFSResultSet.getColumnString(6);
        localInstructionActivityLog.LogLevel = localFFSResultSet.getColumnInt(7);
        localVector.addElement(localInstructionActivityLog);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** InstructionActivityLog.getReqActivityLogs : " + localException1.toString());
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
        FFSDebug.log("*** InstructionActivityLog.getReqActivityLogs failed:" + localException2.toString());
      }
    }
    if (localVector.isEmpty()) {
      return null;
    }
    FFSDebug.log("InstructionActivityLog.getInstructionActivityLogs done,vec.size(): " + localVector.size(), 6);
    return (InstructionActivityLog[])localVector.toArray(new InstructionActivityLog[0]);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws BPWException
  {
    FFSDebug.log("InstructionActivityLog.delete: start, ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    String str2 = "DELETE FROM BPW_InstructionActivityLog WHERE LogDate <= ?";
    Object[] arrayOfObject = { str1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** InstructionActivityLog.delete failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("InstructionActivityLog.delete: done", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.InstructionActivityLog
 * JD-Core Version:    0.7.0.1
 */