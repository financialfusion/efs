package com.ffusion.ffs.scheduling.db;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleHistory
{
  public String LogDate;
  public String SchedulerName;
  public String FIID;
  public String InstructionType;
  public String ServerName;
  public String EventType;
  public String EventTrigger;
  public String EventDescription;
  
  public static ScheduleHist[] getScheduleHist(String paramString1, String paramString2, ScheduleHist paramScheduleHist, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "ScheduleHistory.getScheduleHist";
    FFSDebug.log(str1 + ": start.", 6);
    String str2 = "SELECT SchHistID, LogDate, ScheduleName, FIId, InstructionType, ServerName, EventType, EventTrigger, EventDescription, ProcessId, FileName, CutOffId, CutOffDay, CutOffProcessTime, CutOffExtension FROM SCH_ScheduleHistory WHERE ";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(str2);
    if ((paramString2 == null) || (paramString2.trim().length() <= 0))
    {
      localObject1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      paramString2 = ((DateFormat)localObject1).format(new Date());
    }
    localStringBuffer.append(" LogDate<=");
    localStringBuffer.append("'");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("'");
    if ((paramString1 != null) && (paramString1.trim().length() > 0))
    {
      localStringBuffer.append(" AND LogDate>=");
      localStringBuffer.append("'");
      localStringBuffer.append(paramString1);
      localStringBuffer.append("'");
    }
    if (paramScheduleHist != null)
    {
      if ((paramScheduleHist.SchHistID != null) && (paramScheduleHist.SchHistID.trim().length() > 0))
      {
        localStringBuffer.append(" AND SchHistID=");
        localStringBuffer.append("'");
        localStringBuffer.append(paramScheduleHist.SchHistID.trim());
        localStringBuffer.append("'");
      }
      if ((paramScheduleHist.ScheduleName != null) && (paramScheduleHist.ScheduleName.trim().length() > 0))
      {
        localStringBuffer.append(" AND ScheduleName=");
        localStringBuffer.append("'");
        localStringBuffer.append(paramScheduleHist.ScheduleName.trim());
        localStringBuffer.append("'");
      }
      if ((paramScheduleHist.FIID != null) && (paramScheduleHist.FIID.trim().length() != 0))
      {
        localStringBuffer.append(" AND FIId=");
        localStringBuffer.append("'");
        localStringBuffer.append(paramScheduleHist.FIID.trim());
        localStringBuffer.append("'");
      }
      if ((paramScheduleHist.InstructionType != null) && (paramScheduleHist.InstructionType.trim().length() > 0))
      {
        localStringBuffer.append(" AND InstructionType=");
        localStringBuffer.append("'");
        localStringBuffer.append(paramScheduleHist.InstructionType.trim());
        localStringBuffer.append("'");
      }
      if ((paramScheduleHist.ServerName != null) && (paramScheduleHist.ServerName.trim().length() > 0))
      {
        localStringBuffer.append(" AND ServerName=");
        localStringBuffer.append("'");
        localStringBuffer.append(paramScheduleHist.ServerName.trim());
        localStringBuffer.append("'");
      }
      if ((paramScheduleHist.EventType != null) && (paramScheduleHist.EventType.trim().length() > 0))
      {
        localStringBuffer.append(" AND EventType=");
        localStringBuffer.append("'");
        localStringBuffer.append(paramScheduleHist.EventType.trim());
        localStringBuffer.append("'");
      }
      if ((paramScheduleHist.EventTrigger != null) && (paramScheduleHist.EventTrigger.trim().length() > 0))
      {
        localStringBuffer.append(" AND EventTrigger=");
        localStringBuffer.append("'");
        localStringBuffer.append(paramScheduleHist.EventTrigger.trim());
        localStringBuffer.append("'");
      }
      if ((paramScheduleHist.EventDescription != null) && (paramScheduleHist.EventDescription.trim().length() > 0))
      {
        localStringBuffer.append(" AND EventDescription=");
        localStringBuffer.append("'");
        localStringBuffer.append(paramScheduleHist.EventDescription.trim());
        localStringBuffer.append("'");
      }
    }
    str2 = localStringBuffer.toString();
    FFSDebug.log(str1 + ": sql= " + str2, 6);
    Object localObject1 = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, null);
      while (localFFSResultSet.getNextRow())
      {
        ScheduleHist localScheduleHist = new ScheduleHist();
        localScheduleHist.SchHistID = localFFSResultSet.getColumnString("SchHistID");
        localScheduleHist.LogDate = localFFSResultSet.getColumnString("LogDate");
        localScheduleHist.ScheduleName = localFFSResultSet.getColumnString("ScheduleName");
        localScheduleHist.FIID = localFFSResultSet.getColumnString("FIId");
        localScheduleHist.InstructionType = localFFSResultSet.getColumnString("InstructionType");
        localScheduleHist.ServerName = localFFSResultSet.getColumnString("ServerName");
        localScheduleHist.EventType = localFFSResultSet.getColumnString("EventType");
        localScheduleHist.EventTrigger = localFFSResultSet.getColumnString("EventTrigger");
        localScheduleHist.EventDescription = localFFSResultSet.getColumnString("EventDescription");
        localScheduleHist.ProcessId = localFFSResultSet.getColumnString("ProcessId");
        localScheduleHist.FileName = localFFSResultSet.getColumnString("FileName");
        localScheduleHist.CutOffId = localFFSResultSet.getColumnString("CutOffId");
        localScheduleHist.CutOffDay = localFFSResultSet.getColumnInt("CutOffDay");
        localScheduleHist.CutOffProcessTime = localFFSResultSet.getColumnString("CutOffProcessTime");
        localScheduleHist.CutOffExtension = localFFSResultSet.getColumnString("CutOffExtension");
        ((ArrayList)localObject1).add(localScheduleHist);
      }
      localFFSResultSet.close();
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      String str3 = "*** " + str1 + " failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally {}
    FFSDebug.log(str1 + ": done, rows=" + ((ArrayList)localObject1).size(), 6);
    return ((ArrayList)localObject1).isEmpty() ? null : (ScheduleHist[])((ArrayList)localObject1).toArray(new ScheduleHist[0]);
  }
  
  public static int createScheduleHist(ScheduleHist paramScheduleHist)
    throws FFSException
  {
    String str1 = "ScheduleHistory.createScheduleHist(schHist)";
    FFSDebug.log(str1 + ": start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    int i = -1;
    try
    {
      i = createScheduleHist(localFFSConnectionHolder, paramScheduleHist);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** " + str1 + " failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1 + ": done", 6);
    return i;
  }
  
  public static int createScheduleHist(FFSConnectionHolder paramFFSConnectionHolder, ScheduleHist paramScheduleHist)
    throws FFSException
  {
    String str1 = "ScheduleHistory.createScheduleHist(dbh, schHist)";
    FFSDebug.log(str1 + ": start", 6);
    int i = -1;
    String str2 = "INSERT INTO SCH_ScheduleHistory ( SchHistID, LogDate, ScheduleName, FIId, InstructionType, ServerName, EventType, EventTrigger, EventDescription, ProcessId, FileName, CutOffId, CutOffDay, CutOffProcessTime, CutOffExtension ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String str3;
    String str4;
    try
    {
      str3 = DBUtil.getNextIndexString("SchHistID");
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      str4 = localSimpleDateFormat.format(new Date());
    }
    catch (Exception localException1)
    {
      String str5 = "*** " + str1 + " failed:";
      System.out.println(str5 + localException1.toString());
      throw new FFSException(localException1.toString());
    }
    if ((paramScheduleHist.EventDescription != null) && (paramScheduleHist.EventDescription.length() > 256))
    {
      FFSDebug.log(str1 + ": Warning. The event description is too large. " + "It will be trimmed to fit the size of its column in database: " + paramScheduleHist.EventDescription);
      paramScheduleHist.EventDescription = paramScheduleHist.EventDescription.substring(0, 255);
    }
    Object[] arrayOfObject = { str3, str4, paramScheduleHist.ScheduleName, paramScheduleHist.FIID, paramScheduleHist.InstructionType, paramScheduleHist.ServerName, paramScheduleHist.EventType, paramScheduleHist.EventTrigger, paramScheduleHist.EventDescription, paramScheduleHist.ProcessId, paramScheduleHist.FileName, paramScheduleHist.CutOffId, new Integer(paramScheduleHist.CutOffDay), paramScheduleHist.CutOffProcessTime, paramScheduleHist.CutOffExtension };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException2)
    {
      String str6 = "*** " + str1 + " failed:";
      FFSDebug.log(str6 + localException2.toString());
      throw new FFSException(localException2.toString());
    }
    FFSDebug.log(str1 + ": done", 6);
    return i;
  }
  
  public static ScheduleHist getLastScheduleHist(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "ScheduleHistory.getLastScheduleHist (fiId, instructionType)";
    FFSDebug.log(str1 + ": start", 6);
    FFSDebug.log(str1 + ": fiid:" + paramString1, 6);
    FFSDebug.log(str1 + ": instructionType:" + paramString2, 6);
    String str2 = "SELECT SchHistID, LogDate, ScheduleName, FIId, InstructionType, ServerName, EventType, EventTrigger, EventDescription, ProcessId, FileName, CutOffId, CutOffDay, CutOffProcessTime, CutOffExtension FROM SCH_ScheduleHistory WHERE FIId=? and InstructionType=? and EventType=? ORDER BY LogDate DESC";
    ScheduleHist localScheduleHist = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    FFSResultSet localFFSResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramString1, paramString2, "ProcessingStart" };
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localScheduleHist = new ScheduleHist();
        localScheduleHist.SchHistID = localFFSResultSet.getColumnString("SchHistID");
        localScheduleHist.LogDate = localFFSResultSet.getColumnString("LogDate");
        localScheduleHist.ScheduleName = localFFSResultSet.getColumnString("ScheduleName");
        localScheduleHist.FIID = localFFSResultSet.getColumnString("FIId");
        localScheduleHist.InstructionType = localFFSResultSet.getColumnString("InstructionType");
        localScheduleHist.ServerName = localFFSResultSet.getColumnString("ServerName");
        localScheduleHist.EventType = localFFSResultSet.getColumnString("EventType");
        localScheduleHist.EventTrigger = localFFSResultSet.getColumnString("EventTrigger");
        localScheduleHist.EventDescription = localFFSResultSet.getColumnString("EventDescription");
        localScheduleHist.ProcessId = localFFSResultSet.getColumnString("ProcessId");
        localScheduleHist.FileName = localFFSResultSet.getColumnString("FileName");
        localScheduleHist.CutOffId = localFFSResultSet.getColumnString("CutOffId");
        localScheduleHist.CutOffDay = localFFSResultSet.getColumnInt("CutOffDay");
        localScheduleHist.CutOffProcessTime = localFFSResultSet.getColumnString("CutOffProcessTime");
        localScheduleHist.CutOffExtension = localFFSResultSet.getColumnString("CutOffExtension");
      }
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + ": done", 6);
      localObject1 = localScheduleHist;
      return localObject1;
    }
    catch (Exception localException)
    {
      if ((localFFSConnectionHolder != null) && (localFFSConnectionHolder.conn != null)) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject1 = "*** " + str1 + " failed:";
      FFSDebug.log((String)localObject1 + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
      localFFSConnectionHolder = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.db.ScheduleHistory
 * JD-Core Version:    0.7.0.1
 */