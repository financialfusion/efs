package com.ffusion.ffs.scheduling.db;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;
import java.util.Hashtable;

public class EventInfo
{
  public String EventID;
  public String ScheduleID;
  public String InstructionID;
  public String FIId;
  public String InstructionType;
  public String Status;
  public int ScheduleFlag;
  public String LogID;
  public String cutOffId;
  public String processId;
  public boolean createEmptyFile;
  public int fileBasedRecovery;
  public static Hashtable eventCache = new Hashtable(100);
  public static int BATCH_SIZE;
  public static final int DEFAULT_SIZE = 1000;
  
  public EventInfo() {}
  
  public EventInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, String paramString6)
  {
    this.EventID = paramString1;
    this.FIId = paramString3;
    this.InstructionID = paramString2;
    this.InstructionType = paramString4;
    this.Status = paramString5;
    this.ScheduleFlag = paramInt;
    this.LogID = paramString6;
  }
  
  public static EventInfo[] getAllEventInfo(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log("EventInfo.getAllEventInfo: start", 6);
    String str = "SELECT EventID,InstructionID,FIId,InstructionType,Status,ScheduleFlag,LogID FROM SCH_EventInfo";
    Object[] arrayOfObject = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        EventInfo localEventInfo = new EventInfo();
        localEventInfo.EventID = localFFSResultSet.getColumnString(1);
        localEventInfo.FIId = localFFSResultSet.getColumnString(2);
        localEventInfo.InstructionID = localFFSResultSet.getColumnString(3);
        localEventInfo.InstructionType = localFFSResultSet.getColumnString(4);
        localEventInfo.Status = localFFSResultSet.getColumnString(5);
        localEventInfo.ScheduleFlag = localFFSResultSet.getColumnInt(6);
        localEventInfo.LogID = localFFSResultSet.getColumnString(7);
        localArrayList.add(localEventInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** EventInfo.getAllEventInfo failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfo.getAllEventInfo failed:" + localException2.toString());
      }
    }
    if (localArrayList.isEmpty()) {
      return null;
    }
    FFSDebug.log("EventInfo.getAllEventInfo done, vec.size()=" + localArrayList.size(), 6);
    return (EventInfo[])localArrayList.toArray(new EventInfo[localArrayList.size()]);
  }
  
  public static EventInfo get(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("EventInfo.get start,instType=" + paramString1 + ",instID=" + paramString2, 6);
    String str = "SELECT EventID,Status,ScheduleFlag,LogID,FIId FROM SCH_EventInfo WHERE InstructionType=? AND InstructionID=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    EventInfo localEventInfo = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localEventInfo = new EventInfo();
        localEventInfo.EventID = localFFSResultSet.getColumnString(1);
        localEventInfo.InstructionID = paramString2;
        localEventInfo.InstructionType = paramString1;
        localEventInfo.Status = localFFSResultSet.getColumnString(2);
        localEventInfo.ScheduleFlag = localFFSResultSet.getColumnInt(3);
        localEventInfo.LogID = localFFSResultSet.getColumnString(4);
        localEventInfo.FIId = localFFSResultSet.getColumnString(5);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** EventInfo.get failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfo.get failed:" + localException2.toString());
      }
    }
    FFSDebug.log("EventInfo.get done", 6);
    return localEventInfo;
  }
  
  public static boolean checkExist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("EventInfo.checkExist start,instType=" + paramString1 + ",instID=" + paramString2, 6);
    String str = "SELECT EventID FROM SCH_EventInfo WHERE InstructionType=? AND InstructionID=?";
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
      FFSDebug.log("*** EventInfo.checkExist failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfo.checkExist failed:" + localException2.toString());
      }
    }
    FFSDebug.log("EventInfo.checkExist done=" + bool, 6);
    return bool;
  }
  
  public static boolean checkExist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    FFSDebug.log("EventInfo.checkExist start, instType=" + paramString1 + ", instID=" + paramString2 + ", scheduleFlag=" + paramInt, 6);
    String str = "SELECT EventID FROM SCH_EventInfo WHERE InstructionType=? AND InstructionID=? AND ScheduleFlag=?";
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
      FFSDebug.log("*** EventInfo.checkExist failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfo.checkExist failed:" + localException2.toString());
      }
    }
    FFSDebug.log("EventInfo.checkExist done=" + bool, 6);
    return bool;
  }
  
  public static EventInfo[] retrieveEventInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    FFSDebug.log("EventInfo.retrieveEventInfo: start, Status=" + paramString1 + ",InstructionType=" + paramString3, 6);
    String str = "SELECT EventID,InstructionID,ScheduleFlag,LogID FROM SCH_EventInfo WHERE FIId=? AND InstructionType=? AND Status=? ORDER BY EventID ASC";
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = { paramString2, paramString3, paramString1 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      if (eventCache.get(paramString3 + paramString2) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
        eventCache.put(paramString3 + paramString2, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)eventCache.get(paramString3 + paramString2);
      }
      while (localFFSResultSet.getNextRow())
      {
        EventInfo localEventInfo = new EventInfo();
        localEventInfo.EventID = localFFSResultSet.getColumnString(1);
        localEventInfo.InstructionID = localFFSResultSet.getColumnString(2);
        localEventInfo.FIId = paramString2;
        localEventInfo.InstructionType = paramString3;
        localEventInfo.Status = paramString1;
        localEventInfo.ScheduleFlag = localFFSResultSet.getColumnInt(3);
        localEventInfo.LogID = localFFSResultSet.getColumnString(4);
        localArrayList.add(localEventInfo);
        i++;
        if (i == BATCH_SIZE)
        {
          EventInfo[] arrayOfEventInfo = (EventInfo[])localArrayList.toArray(new EventInfo[localArrayList.size()]);
          return arrayOfEventInfo;
        }
      }
      localFFSResultSet.close();
      eventCache.remove(paramString3 + paramString2);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      eventCache.remove(paramString3 + paramString2);
      FFSDebug.log("*** EventInfo.retrieveEventInfo failed:" + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally {}
    FFSDebug.log("EventInfo.retrieveEventInfo done,vec.size()=" + localArrayList.size(), 6);
    return (EventInfo[])localArrayList.toArray(new EventInfo[localArrayList.size()]);
  }
  
  public static EventInfo[] retrieveEventInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt)
    throws FFSException
  {
    FFSDebug.log("EventInfo.retrieveEventInfo: start, Status=" + paramString1 + ",FIId=" + paramString2 + ",InstructionType=" + paramString3 + ",ScheduleFlag=" + paramInt, 6);
    String str = "SELECT EventID,InstructionID,LogID FROM SCH_EventInfo WHERE FIId=? AND InstructionType=? AND Status=? AND ScheduleFlag=? ORDER BY EventID ASC";
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = { paramString2, paramString3, paramString1, new Integer(paramInt) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      if (eventCache.get(paramString3 + paramString2) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
        eventCache.put(paramString3 + paramString2, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)eventCache.get(paramString3 + paramString2);
      }
      while (localFFSResultSet.getNextRow())
      {
        EventInfo localEventInfo = new EventInfo();
        localEventInfo.EventID = localFFSResultSet.getColumnString(1);
        localEventInfo.InstructionID = localFFSResultSet.getColumnString(2);
        localEventInfo.FIId = paramString2;
        localEventInfo.InstructionType = paramString3;
        localEventInfo.Status = paramString1;
        localEventInfo.ScheduleFlag = paramInt;
        localEventInfo.LogID = localFFSResultSet.getColumnString(3);
        localArrayList.add(localEventInfo);
        i++;
        if (i == BATCH_SIZE)
        {
          EventInfo[] arrayOfEventInfo = (EventInfo[])localArrayList.toArray(new EventInfo[localArrayList.size()]);
          return arrayOfEventInfo;
        }
      }
      localFFSResultSet.close();
      eventCache.remove(paramString3 + paramString2);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      eventCache.remove(paramString3 + paramString2);
      FFSDebug.log("*** EventInfo.retrieveEventInfo failed:" + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally {}
    FFSDebug.log("EventInfo.retrieveEventInfo done,vec.size()=" + localArrayList.size(), 6);
    return (EventInfo[])localArrayList.toArray(new EventInfo[localArrayList.size()]);
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
      FFSDebug.log("*** EventInfo.clearBatch failed:" + localException1.toString());
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
        FFSDebug.log("*** EventInfo.clearBatch failed:" + localException2.toString());
      }
    }
  }
  
  public static void cancelEvent(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("EventInfo.cancelEvent: start, eventID=" + paramString, 6);
    String str1 = "DELETE FROM SCH_EventInfo WHERE EventID=?";
    Object[] arrayOfObject = { paramString };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** EventInfo.cancelEvent failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfo.cancelEvent done", 6);
  }
  
  public static void cancelEvent(FFSConnection paramFFSConnection, String paramString)
    throws FFSException
  {
    FFSDebug.log("EventInfo.cancelEvent start, eventID=" + paramString, 6);
    String str1 = "DELETE FROM SCH_EventInfo WHERE EventID=?";
    Object[] arrayOfObject = { paramString };
    int i = -1;
    try
    {
      i = paramFFSConnection.executeCommand(str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** EventInfo.cancelEvent failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfo.cancelEvent done", 6);
  }
  
  public static void cancelEvent(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("EventInfo.cancelEvent start,instType=" + paramString1 + ",instID=" + paramString2, 6);
    String str1 = "DELETE FROM SCH_EventInfo WHERE InstructionType=? AND InstructionID=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** EventInfo.cancelEvent failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfo.cancelEvent done", 6);
  }
  
  public static void updateEventStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("EventInfo.updateEventStatus: start, eventID=" + paramString1 + ",status=" + paramString2, 6);
    String str1 = "UPDATE SCH_EventInfo SET Status=? WHERE EventID=?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** EventInfo.updateEventStatus failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfo.updateEventStatus done", 6);
  }
  
  public static String createEvent(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5)
    throws FFSException
  {
    FFSDebug.log("EventInfo.createEvent start, InstructionID=" + paramString1, 6);
    String str1 = "INSERT INTO SCH_EventInfo (EventID,InstructionID,FIId,InstructionType,Status,ScheduleFlag,LogID) VALUES (?,?,?,?,?,?,?)";
    String str2;
    try
    {
      str2 = DBUtil.getNextIndexString("EventID");
    }
    catch (BPWException localBPWException)
    {
      String str3 = "*** EventInfo.createEvent: failed:";
      FFSDebug.log(str3 + localBPWException.toString());
      throw new FFSException(localBPWException.toString());
    }
    Object[] arrayOfObject = { str2, paramString1, paramString2, paramString3, paramString4, new Integer(paramInt), paramString5 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str4 = "*** EventInfo.createEvent failed:";
      FFSDebug.log(str4 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("EventInfo.createEvent done", 6);
    return str2;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.db.EventInfo
 * JD-Core Version:    0.7.0.1
 */