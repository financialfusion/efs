package com.ffusion.ffs.bpw.audit;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.filemonitor.FMLogAdapter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FMLogAgent
{
  public static void writeRecords(Connection paramConnection, LinkedList paramLinkedList)
    throws SQLException, NoSuchElementException, Throwable
  {
    FMLogAdapter.writeRecords(paramConnection, paramLinkedList);
  }
  
  public static void writeToFMLog(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    FMLogRecord localFMLogRecord = new FMLogRecord();
    localFMLogRecord.setMillis(System.currentTimeMillis());
    localFMLogRecord.setFileType(paramString1);
    localFMLogRecord.setFileName(paramString2);
    try
    {
      localFMLogRecord.setHostName(InetAddress.getLocalHost().getHostName());
    }
    catch (Exception localException1)
    {
      String str1 = FFSDebug.stackTrace(localException1);
      FFSDebug.log("*** FMLogAgent.writeToFMLog: File Monitor Logging failed while getting host name :" + str1, 0);
    }
    localFMLogRecord.setActivityType("Transfer");
    localFMLogRecord.setFromSystem(paramString3);
    localFMLogRecord.setToSystem(paramString4);
    localFMLogRecord.setStatus(paramString5);
    LinkedList localLinkedList = new LinkedList();
    localLinkedList.add(localFMLogRecord);
    int i = 0;
    try
    {
      if (paramFFSConnectionHolder == null)
      {
        paramFFSConnectionHolder = new FFSConnectionHolder();
        paramFFSConnectionHolder.conn = DBUtil.getConnection();
        i = 1;
      }
      writeRecords(paramFFSConnectionHolder.conn.getConnection(), localLinkedList);
      if (i == 1)
      {
        paramFFSConnectionHolder.conn.commit();
        DBUtil.freeConnection(paramFFSConnectionHolder.conn);
      }
    }
    catch (Exception localException2)
    {
      str2 = FFSDebug.stackTrace(localException2);
      FFSDebug.log("*** FMLogAgent.writeToFMLog: File Monitor Logging failed :" + str2, 0);
      if (i == 1)
      {
        paramFFSConnectionHolder.conn.rollback();
        DBUtil.freeConnection(paramFFSConnectionHolder.conn);
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** FMLogAgent.writeToFMLog: File Monitor Logging failed :" + str2, 0);
      if (i == 1)
      {
        paramFFSConnectionHolder.conn.rollback();
        DBUtil.freeConnection(paramFFSConnectionHolder.conn);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.audit.FMLogAgent
 * JD-Core Version:    0.7.0.1
 */