package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TempHist
  implements FFSConst, DBConsts, BPWResource
{
  public String HistId;
  public String CursorId;
  public String RecordExtId;
  public String RecordType;
  public int DueDate;
  public String Amount;
  public String SubmitDate;
  
  public static int create(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5)
    throws BPWException
  {
    FFSDebug.log("TempHist.create start, histId=" + paramString1 + ",cursorId=" + paramString2 + ",recordExtId=" + paramString3 + ",recordType=" + paramString4 + ",dueDate=" + paramInt + ",amount=" + paramString5, 6);
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4, new Integer(paramInt), paramString5, DBUtil.getCurrentLogDate() };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_TempHist (HistId, CursorId, RecordExtId, RecordType, DueDate, Amount, Submitdate) VALUES(?,?,?,?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str = "*** TempHist.create failed:";
      FFSDebug.log(str + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("TempHist.create done", 6);
    return i;
  }
  
  public static TempHist[] get(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, long paramLong)
    throws BPWException
  {
    return get(paramFFSConnectionHolder, paramString1, paramString2, paramLong, true);
  }
  
  public static TempHist[] get(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, long paramLong, boolean paramBoolean)
    throws BPWException
  {
    FFSDebug.log("TempHist.get start, histId=" + paramString1 + ",cursorId=" + paramString2 + " ascending=" + paramBoolean, 6);
    TempHist localTempHist = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    try
    {
      Object[] arrayOfObject = { paramString1, paramString2 };
      if (paramBoolean == true) {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CursorId, RecordExtId, RecordType, DueDate, Amount from BPW_TempHist  WHERE HistId = ? and CursorId > ? ORDER BY CursorId ASC", arrayOfObject);
      } else {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CursorId, RecordExtId, RecordType, DueDate, Amount from BPW_TempHist  WHERE HistId = ? and CursorId < ? ORDER BY CursorId DESC", arrayOfObject);
      }
      while (localFFSResultSet.getNextRow())
      {
        localTempHist = new TempHist();
        localTempHist.HistId = paramString1;
        localTempHist.CursorId = localFFSResultSet.getColumnString(1);
        localTempHist.RecordExtId = localFFSResultSet.getColumnString(2);
        localTempHist.RecordType = localFFSResultSet.getColumnString(3);
        localTempHist.DueDate = localFFSResultSet.getColumnInt(4);
        localTempHist.Amount = localFFSResultSet.getColumnString(5);
        localArrayList.add(localTempHist);
        l += 1L;
        if ((paramLong != 0L) && (l >= paramLong)) {
          break;
        }
      }
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.toString() + FFSDebug.stackTrace(localException1));
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
        FFSDebug.log("*** TempHist.get failed:" + localException2.toString());
      }
    }
    FFSDebug.log("TempHist.get done, rows=" + l, 6);
    return (TempHist[])localArrayList.toArray(new TempHist[0]);
  }
  
  public static int getCount(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("TempHist.getCount start, histId=" + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    try
    {
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT Count(*) from BPW_TempHist WHERE HistId = ?", arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.toString() + FFSDebug.stackTrace(localException1));
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
        FFSDebug.log("*** TempHist.getCount failed:" + localException2.toString());
      }
    }
    FFSDebug.log("TempHist.getCount done, rows=" + i, 6);
    return i;
  }
  
  public static int delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("TempHist.delete start, histId=" + paramString, 6);
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_TempHist WHERE HistId = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** TempHist.delete failed: " + localException.toString() + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("TempHist.delete done, histId=" + paramString, 6);
    return i;
  }
  
  public static int deleteOld(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("TempHist.deleteOld start, oldDate=" + paramString, 6);
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_TempHist WHERE Submitdate < ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** TempHist.deleteOld failed: " + localException.toString() + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("TempHist.deleteOld done, oldDate=" + paramString, 6);
    return i;
  }
  
  public static int cleanAll(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws BPWException
  {
    FFSDebug.log("TempHist.cleanAll start, olderThan=" + paramInt, 6);
    String str1 = "SELECT HistId from BPW_TempHist WHERE Submitdate < ?";
    long l1 = System.currentTimeMillis();
    long l2 = l1 - paramInt * 60 * 1000;
    Date localDate = new Date(l2);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String str2 = localSimpleDateFormat.format(localDate);
    Object[] arrayOfObject = { str2 };
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      paramFFSConnectionHolder.conn.commit();
      while (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString("HistId");
        if ((str3 != null) && (str3.trim().length() > 0))
        {
          i += delete(paramFFSConnectionHolder, str3);
          paramFFSConnectionHolder.conn.commit();
          FFSDebug.log("TempHist.cleanAll cleaned dta for histId= " + str3, 6);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** TempHist.cleanAll failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new BPWException(str4);
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
      catch (Exception localException)
      {
        FFSDebug.log("*** TempHist.cleanAll failed:" + localException.toString());
      }
    }
    FFSDebug.log("TempHist.cleanAll done, rows cleaned= " + i, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.TempHist
 * JD-Core Version:    0.7.0.1
 */