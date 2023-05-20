package com.ffusion.ffs.scheduling.db;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class EventInfoLog
{
  public String EventID;
  public String InstructionID;
  public String FIId;
  public String InstructionType;
  public int LogDate;
  public int ScheduleFlag;
  public String LogID;
  public static Hashtable eventCache = new Hashtable(100);
  public static int BATCH_SIZE;
  public static final int DEFAULT_SIZE = 1000;
  private static final String jdField_do = "INSERT INTO SCH_EventInfoLog (EventID, InstructionID, FIId, InstructionType, LogDate, ScheduleFlag,  LogID, ScheduleId, ProcessId) VALUES (?,?,?,?,?,?,?,?,?)";
  private static final String a = "UPDATE SCH_EventInfoLog SET LogDate=?, ScheduleFlag=? WHERE ScheduleId = ? AND ProcessId = ?";
  private static final String jdField_if = "UPDATE SCH_EventInfoLog SET InstructionID=?, FIId=?,InstructionType=?, LogDate=?, ScheduleFlag=?, LogID=? WHERE EventID=? ";
  
  public EventInfoLog() {}
  
  public EventInfoLog(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, String paramString5)
  {
    this.EventID = paramString1;
    this.InstructionID = paramString2;
    this.FIId = paramString3;
    this.InstructionType = paramString4;
    this.LogDate = paramInt1;
    this.ScheduleFlag = paramInt2;
    this.LogID = paramString5;
  }
  
  public static EventInfoLog[] getAllEventInfoLogs(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("EventInfoLog.getAllEventInfoLogs start", 6);
    String str = "SELECT EventID,InstructionID,FIId,InstructionType,LogDate,ScheduleFlag,LogID FROM SCH_EventInfoLog";
    Object[] arrayOfObject = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        EventInfoLog localEventInfoLog = new EventInfoLog();
        localEventInfoLog.EventID = localFFSResultSet.getColumnString(1);
        localEventInfoLog.InstructionID = localFFSResultSet.getColumnString(2);
        localEventInfoLog.FIId = localFFSResultSet.getColumnString(3);
        localEventInfoLog.InstructionType = localFFSResultSet.getColumnString(4);
        localEventInfoLog.LogDate = localFFSResultSet.getColumnInt(5);
        localEventInfoLog.ScheduleFlag = localFFSResultSet.getColumnInt(6);
        localEventInfoLog.LogID = localFFSResultSet.getColumnString(7);
        localArrayList.add(localEventInfoLog);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** EventInfoLog.getAllEventInfoLogs failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfoLog.getAllEventInfoLogs failed:" + localException2.toString());
      }
    }
    if (localArrayList.isEmpty()) {
      return null;
    }
    FFSDebug.log("EventInfoLog.getAllEventInfoLogs doen, vec.size()=" + localArrayList.size(), 6);
    return (EventInfoLog[])localArrayList.toArray(new EventInfoLog[0]);
  }
  
  public static boolean checkExist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.checkExist start,instType=" + paramString1 + ",instID=" + paramString2, 6);
    String str = "SELECT EventID FROM SCH_EventInfoLog WHERE InstructionType=? AND InstructionID=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
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
      FFSDebug.log("*** EventInfoLog.checkExist failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfoLog.checkExist failed:" + localException2.toString());
      }
    }
    FFSDebug.log("EventInfoLog.checkExist done=" + bool, 6);
    return bool;
  }
  
  public static boolean checkExist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.checkExist start, instType=" + paramString1 + ", instID=" + paramString2 + ", scheduleFlag=" + paramInt, 6);
    String str = "SELECT EventID FROM SCH_EventInfoLog WHERE InstructionType=? AND InstructionID=? AND ScheduleFlag=?";
    Object[] arrayOfObject = { paramString1, paramString2, new Integer(paramInt) };
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
      FFSDebug.log("*** EventInfoLog.checkExist failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfoLog.checkExist failed:" + localException2.toString());
      }
    }
    FFSDebug.log("EventInfoLog.checkExist done=" + bool, 6);
    return bool;
  }
  
  public static EventInfoLog get(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.get start,instType=" + paramString1 + ",instID=" + paramString2, 6);
    String str = "SELECT EventID,LogDate,ScheduleFlag,LogID FROM SCH_EventInfoLog WHERE InstructionType=? AND InstructionID=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    EventInfoLog localEventInfoLog = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localEventInfoLog = new EventInfoLog();
        localEventInfoLog.EventID = localFFSResultSet.getColumnString(1);
        localEventInfoLog.InstructionID = paramString2;
        localEventInfoLog.InstructionType = paramString1;
        localEventInfoLog.LogDate = localFFSResultSet.getColumnInt(2);
        localEventInfoLog.ScheduleFlag = localFFSResultSet.getColumnInt(3);
        localEventInfoLog.LogID = localFFSResultSet.getColumnString(4);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** EventInfoLog.get failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfoLog.get failed:" + localException2.toString());
      }
    }
    FFSDebug.log("EventInfoLog.get done", 6);
    return localEventInfoLog;
  }
  
  public static EventInfoLog getByEventID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.getByEventID start,eventID=" + paramString, 6);
    String str = "SELECT InstructionID,InstructionType,LogDate,ScheduleFlag,LogID FROM SCH_EventInfoLog WHERE EventID=?";
    Object[] arrayOfObject = { paramString };
    EventInfoLog localEventInfoLog = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localEventInfoLog = new EventInfoLog();
        localEventInfoLog.InstructionID = localFFSResultSet.getColumnString(1);
        localEventInfoLog.InstructionType = localFFSResultSet.getColumnString(2);
        localEventInfoLog.EventID = paramString;
        localEventInfoLog.LogDate = localFFSResultSet.getColumnInt(3);
        localEventInfoLog.ScheduleFlag = localFFSResultSet.getColumnInt(4);
        localEventInfoLog.LogID = localFFSResultSet.getColumnString(5);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** EventInfoLog.getByEventID failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfoLog.getByEventID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("EventInfoLog.getByEventID done", 6);
    return localEventInfoLog;
  }
  
  public static EventInfo[] retrieveEventInfoLogs(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("EventInfoLog.retrieveEventInfoLogs start, LogDate=" + paramString3 + ",FIId=" + paramString1 + ",InstructionType=" + paramString2 + ",ScheduleFlag=" + paramInt, 6);
    String str = "SELECT EventID,InstructionID,LogID FROM SCH_EventInfoLog WHERE FIId=? AND InstructionType=? AND LogDate=? AND ScheduleFlag=? ";
    int i = Integer.parseInt(paramString3 + "00");
    Object[] arrayOfObject = { paramString1, paramString2, new Integer(i), new Integer(paramInt) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      int j = 0;
      if (eventCache.get(paramString2 + paramString1) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
        eventCache.put(paramString2 + paramString1, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)eventCache.get(paramString2 + paramString1);
      }
      while (localFFSResultSet.getNextRow())
      {
        EventInfo localEventInfo = new EventInfo();
        localEventInfo.EventID = localFFSResultSet.getColumnString(1);
        localEventInfo.InstructionID = localFFSResultSet.getColumnString(2);
        localEventInfo.FIId = paramString1;
        localEventInfo.InstructionType = paramString2;
        localEventInfo.ScheduleFlag = paramInt;
        localEventInfo.LogID = localFFSResultSet.getColumnString(3);
        localArrayList.add(localEventInfo);
        j++;
        if (j == BATCH_SIZE)
        {
          FFSDebug.log("EventInfoLog.retrieveEventInfoLogs done, vec.size()=" + localArrayList.size(), 6);
          EventInfo[] arrayOfEventInfo = (EventInfo[])localArrayList.toArray(new EventInfo[localArrayList.size()]);
          return arrayOfEventInfo;
        }
      }
      localFFSResultSet.close();
      eventCache.remove(paramString2 + paramString1);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      eventCache.remove(paramString2 + paramString1);
      FFSDebug.log("*** EventInfoLog.retrieveEventInfoLogs failed:" + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally {}
    FFSDebug.log("EventInfoLog.retrieveEventInfoLogs done, vec.size()=" + localArrayList.size(), 6);
    if (localArrayList.isEmpty()) {
      return null;
    }
    return (EventInfo[])localArrayList.toArray(new EventInfo[localArrayList.size()]);
  }
  
  public static EventInfo[] retrieveEventInfoLogs(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("EventInfoLog.retrieveEventInfoLogs start, LogDate=" + paramString2 + ",InstructionType=" + paramString1 + ",instructionID=" + paramString3 + ",ScheduleFlag=" + paramInt, 6);
    if (paramString3 == null) {
      return null;
    }
    String[] arrayOfString = null;
    if ((paramString3.indexOf(',') == -1) && (paramString3.indexOf('-') == -1))
    {
      arrayOfString = new String[1];
      arrayOfString[0] = paramString3;
    }
    else
    {
      arrayOfString = getSrvrTids(paramString3);
    }
    String str = "SELECT EventID,InstructionID,LogID, FIId FROM SCH_EventInfoLog WHERE InstructionID=? AND InstructionType=? AND LogDate=? AND ScheduleFlag=?  ";
    int i = Integer.parseInt(paramString2 + "00");
    for (int j = 0; j < arrayOfString.length; j++)
    {
      Object[] arrayOfObject = { arrayOfString[j], paramString1, new Integer(i), new Integer(paramInt) };
      FFSResultSet localFFSResultSet = null;
      try
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
        while (localFFSResultSet.getNextRow())
        {
          EventInfo localEventInfo = new EventInfo();
          localEventInfo.EventID = localFFSResultSet.getColumnString(1);
          localEventInfo.InstructionID = localFFSResultSet.getColumnString(2);
          localEventInfo.InstructionType = paramString1;
          localEventInfo.ScheduleFlag = paramInt;
          localEventInfo.LogID = localFFSResultSet.getColumnString(3);
          localEventInfo.FIId = localFFSResultSet.getColumnString(4);
          localArrayList.add(localEventInfo);
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** EventInfoLog.retrieveEventInfoLogs failed:" + FFSDebug.stackTrace(localException));
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
    FFSDebug.log("EventInfoLog.retrieveEventInfoLogs done, vec.size()=" + localArrayList.size(), 6);
    if (localArrayList.isEmpty()) {
      return null;
    }
    return (EventInfo[])localArrayList.toArray(new EventInfo[localArrayList.size()]);
  }
  
  public static String[] getSrvrTids(String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      FFSDebug.log("EventInfoLog.getSrvrTids instructionID: " + paramString, 0);
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      str = str.trim();
      if ((str != null) && (str.length() != 0)) {
        if (str.indexOf('-') != -1) {
          getSrvrTidsByRange(str, localArrayList);
        } else if (localArrayList.contains(str)) {
          FFSDebug.log("EventInfoLog.getSrvrTids duplicate SrvrTID found: " + str + " the duplicate SrvrTID will be ignored", 0);
        } else {
          localArrayList.add(str);
        }
      }
    }
    FFSDebug.log("EventInfoLog.getSrvrTids TransIds: " + localArrayList.size(), 6);
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static void getSrvrTidsByRange(String paramString, ArrayList paramArrayList)
    throws Exception
  {
    if ((paramString == null) || (paramString.length() == 0) || (paramArrayList == null)) {
      return;
    }
    int i = paramString.indexOf('-');
    if (i == -1) {
      return;
    }
    try
    {
      String str1 = paramString.substring(0, i);
      String str2 = paramString.substring(i + 1);
      if (str1 == null) {
        throw new FFSException("EventInfoLog.getSrvrTidsByRange: range error, first=" + str1);
      }
      if (str2 == null) {
        throw new FFSException("EventInfoLog.getSrvrTidsByRange: range error, last=" + str2);
      }
      long l1 = Long.parseLong(str1);
      long l2 = Long.parseLong(str2);
      if (l1 > l2) {
        throw new FFSException("EventInfoLog.getSrvrTidsByRange: range error, first=" + l1 + ",last=" + l2);
      }
      while (l1 <= l2)
      {
        String str3 = "" + l1;
        if (paramArrayList.contains(str3)) {
          FFSDebug.log("EventInfoLog.getSrvrTidsByRange duplicate SrvrTID found: " + str3 + " the duplicate SrvrTID will be ignored", 0);
        } else {
          paramArrayList.add(str3);
        }
        l1 += 1L;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("EventInfoLog.getSrvrTidsByRange Failed. Error: " + FFSDebug.stackTrace(localException), 0);
      throw localException;
    }
    FFSDebug.log("EventInfoLog.getSrvrTidsByRange TransIds: " + paramArrayList.size(), 6);
  }
  
  public static boolean isBatchDone(String paramString1, String paramString2)
  {
    return !eventCache.containsKey(paramString2 + paramString1);
  }
  
  public static void clearBatch(String paramString1, String paramString2)
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      if (eventCache.containsKey(paramString2 + paramString1))
      {
        localFFSResultSet = (FFSResultSet)eventCache.get(paramString2 + paramString1);
        eventCache.remove(paramString2 + paramString1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** EventInfoLog.clearBatch failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfoLog.clearBatch failed:" + localException2.toString());
      }
    }
  }
  
  public static EventInfoLog[] retrieveEventInfoLogs(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("EventInfoLog.retrieveEventInfoLogs start, LogDate=" + paramString, 6);
    String str = "SELECT EventID,InstructionID,InstructionType,ScheduleFlag,LogID,FIId FROM SCH_EventInfoLog WHERE LogDate=?";
    int i = Integer.parseInt(paramString + "00");
    Object[] arrayOfObject = { new Integer(i) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      localFFSResultSet.open(arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        EventInfoLog localEventInfoLog = new EventInfoLog();
        localEventInfoLog.EventID = localFFSResultSet.getColumnString(1);
        localEventInfoLog.InstructionID = localFFSResultSet.getColumnString(2);
        localEventInfoLog.InstructionType = localFFSResultSet.getColumnString(3);
        localEventInfoLog.LogDate = i;
        localEventInfoLog.ScheduleFlag = localFFSResultSet.getColumnInt(4);
        localEventInfoLog.LogID = localFFSResultSet.getColumnString(5);
        localEventInfoLog.FIId = localFFSResultSet.getColumnString(6);
        localArrayList.add(localEventInfoLog);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** EventInfoLog.retrieveEventInfoLogs:" + localException1.toString());
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
        FFSDebug.log("*** EventInfoLog.retrieveEventInfoLogs: failed:" + localException2.toString());
      }
    }
    FFSDebug.log("EventInfoLog.retrieveEventInfoLogs doen, vec.size()=" + localArrayList.size(), 6);
    if (localArrayList.isEmpty()) {
      return null;
    }
    return (EventInfoLog[])localArrayList.toArray(new EventInfoLog[0]);
  }
  
  public static void writeEventInfoLog(FFSConnectionHolder paramFFSConnectionHolder, EventInfo paramEventInfo)
    throws FFSException
  {
    int i = 1;
    FFSDebug.log("EventInfoLog.writeEventInfoLog start, InstructionID=" + paramEventInfo.InstructionID, 6);
    if ((paramEventInfo.Status.equals("Processing")) && (paramEventInfo.fileBasedRecovery == 1)) {
      i = !updateEventInfoLog(paramFFSConnectionHolder, paramEventInfo) ? 1 : 0;
    }
    if (i != 0) {
      createEventInfoLog(paramFFSConnectionHolder, paramEventInfo);
    }
    FFSDebug.log("EventInfoLog.writeEventInfoLog done", 6);
  }
  
  public static boolean updateEventInfoLog(FFSConnectionHolder paramFFSConnectionHolder, EventInfo paramEventInfo)
    throws FFSException
  {
    if (paramEventInfo.ScheduleID != null) {
      return updateEventInfoLogBySchedId(paramFFSConnectionHolder, paramEventInfo);
    }
    return updateEventInfoLogByEventId(paramFFSConnectionHolder, paramEventInfo);
  }
  
  public static boolean updateEventInfoLogBySchedId(FFSConnectionHolder paramFFSConnectionHolder, EventInfo paramEventInfo)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.updateEventInfoLog start, InstructionID=" + paramEventInfo.InstructionID, 6);
    Object[] arrayOfObject = { a(), new Integer(paramEventInfo.ScheduleFlag), paramEventInfo.ScheduleID, paramEventInfo.processId };
    try
    {
      return DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE SCH_EventInfoLog SET LogDate=?, ScheduleFlag=? WHERE ScheduleId = ? AND ProcessId = ?", arrayOfObject) > 0;
    }
    catch (Exception localException)
    {
      String str = "*** EventInfoLog.createEventInfoLog failed:";
      FFSDebug.log(str + localException.toString());
      throw new FFSException(localException.toString());
    }
  }
  
  public static boolean updateEventInfoLogByEventId(FFSConnectionHolder paramFFSConnectionHolder, EventInfo paramEventInfo)
    throws FFSException
  {
    Object[] arrayOfObject = { paramEventInfo.InstructionID, paramEventInfo.FIId, paramEventInfo.InstructionType, a(), new Integer(paramEventInfo.ScheduleFlag), paramEventInfo.LogID, paramEventInfo.EventID };
    try
    {
      return DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE SCH_EventInfoLog SET InstructionID=?, FIId=?,InstructionType=?, LogDate=?, ScheduleFlag=?, LogID=? WHERE EventID=? ", arrayOfObject) > 0;
    }
    catch (Exception localException)
    {
      String str = "*** EventInfoLog.updateEventInfoLog failed:";
      FFSDebug.log(str + localException.toString());
      throw new FFSException(localException.toString());
    }
  }
  
  public static void createEventInfoLog(FFSConnectionHolder paramFFSConnectionHolder, EventInfo paramEventInfo)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.createEventInfoLog start, InstructionID=" + paramEventInfo.InstructionID, 6);
    Object[] arrayOfObject = { paramEventInfo.EventID, paramEventInfo.InstructionID, paramEventInfo.FIId, paramEventInfo.InstructionType, a(), new Integer(paramEventInfo.ScheduleFlag), paramEventInfo.LogID, paramEventInfo.ScheduleID, paramEventInfo.processId };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO SCH_EventInfoLog (EventID, InstructionID, FIId, InstructionType, LogDate, ScheduleFlag,  LogID, ScheduleId, ProcessId) VALUES (?,?,?,?,?,?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str = "*** EventInfoLog.createEventInfoLog failed:";
      FFSDebug.log(str + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfoLog.createEventInfoLog done", 6);
  }
  
  private static final Integer a()
  {
    return new Integer(DBUtil.getCurrentStartDate());
  }
  
  public static void createEventInfoLog(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.createEventInfoLog start, InstructionID=" + paramString2, 6);
    EventInfo localEventInfo = a(paramString1, paramString2, paramString3, paramString4, paramInt, paramString5);
    createEventInfoLog(paramFFSConnectionHolder, localEventInfo);
  }
  
  private static EventInfo a(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5)
  {
    EventInfo localEventInfo = new EventInfo();
    localEventInfo.EventID = paramString1;
    localEventInfo.InstructionID = paramString2;
    localEventInfo.FIId = paramString3;
    localEventInfo.InstructionType = paramString4;
    localEventInfo.ScheduleFlag = paramInt;
    localEventInfo.LogID = paramString5;
    return localEventInfo;
  }
  
  public static void updateEventInfoLog(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5)
    throws FFSException
  {
    EventInfo localEventInfo = a(paramString1, paramString2, paramString3, paramString4, paramInt, paramString5);
    if (!updateEventInfoLogByEventId(paramFFSConnectionHolder, localEventInfo)) {
      createEventInfoLog(paramFFSConnectionHolder, localEventInfo);
    }
    FFSDebug.log("EventInfoLog.updateEventInfoLog done", 6);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.delete start, ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    int i = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
    String str1 = "DELETE FROM SCH_EventInfoLog WHERE LogDate <= ?";
    Object[] arrayOfObject = { new Integer(i) };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** EventInfoLog.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfoLog.delete done", 6);
  }
  
  public static int delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.delete start, instId=" + paramString1 + " instType=", paramString2, 6);
    String str1 = "DELETE FROM SCH_EventInfoLog WHERE InstructionID = ?  AND InstructionType = ? ";
    Object[] arrayOfObject = { paramString1, paramString2 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** EventInfoLog.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfoLog.delete done", 6);
    return i;
  }
  
  public static int deleteBatchByInstructionIdsAndType(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString, String paramString)
    throws FFSException
  {
    FFSDebug.log("EventInfoLog.deleteBatch start.", 6);
    if ((paramArrayOfString == null) || (paramString == null)) {
      return 0;
    }
    String str1 = "DELETE FROM SCH_EventInfoLog WHERE InstructionID = ?  AND InstructionType = ?";
    try
    {
      ArrayList localArrayList1 = new ArrayList();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        ArrayList localArrayList2 = new ArrayList(2);
        localArrayList2.add(paramArrayOfString[i]);
        localArrayList2.add(paramString);
        localArrayList1.add(localArrayList2);
      }
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, localArrayList1);
    }
    catch (Exception localException)
    {
      String str2 = "*** EventInfoLog.deleteBatch failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfoLog.deleteBatch done. No of rows = " + paramArrayOfString.length, 6);
    return paramArrayOfString.length;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.db.EventInfoLog
 * JD-Core Version:    0.7.0.1
 */