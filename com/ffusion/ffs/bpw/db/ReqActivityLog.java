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

public class ReqActivityLog
  implements Serializable, DBConsts, FFSConst
{
  public String LogID;
  public String CustomerID;
  public int ReqType;
  public String LogDate;
  public String Request;
  
  public ReqActivityLog() {}
  
  public ReqActivityLog(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    this.LogID = paramString1;
    this.CustomerID = paramString2;
    this.ReqType = paramInt;
    this.LogDate = paramString3;
    this.Request = paramString4;
  }
  
  public ReqActivityLog(String paramString)
    throws BPWException
  {
    FFSDebug.log("ReqActivityLog(logID) start, logID=" + paramString, 6);
    this.LogID = paramString;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str = "SELECT LogID,CustomerID, ReqType, LogDate, Content FROM BPW_ReqActivityLog WHERE LogID=? ORDER BY LogDate";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        this.CustomerID = localFFSResultSet.getColumnString(1);
        this.ReqType = localFFSResultSet.getColumnInt(2);
        this.LogDate = localFFSResultSet.getColumnString(3);
        this.Request = localFFSResultSet.getColumnString(4);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ReqActivityLog(logID) failed:" + localException1.toString());
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
        FFSDebug.log("*** ReqActivityLog(logID) failed:" + localException2.toString());
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("ReqActivityLog(logID) done, logID=" + paramString, 6);
  }
  
  public void setCustomerID(String paramString)
  {
    this.CustomerID = paramString;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setLogDate(String paramString)
  {
    this.LogDate = paramString;
  }
  
  public String getLogDate()
  {
    return this.LogDate;
  }
  
  public void setLogID(String paramString)
  {
    this.LogID = paramString;
  }
  
  public String getLogID()
  {
    return this.LogID;
  }
  
  public void setReqType(int paramInt)
  {
    this.ReqType = paramInt;
  }
  
  public int getReqType()
  {
    return this.ReqType;
  }
  
  public static String createLog(String paramString1, int paramInt, String paramString2)
    throws BPWException
  {
    FFSDebug.log("ReqActivityLog.createLog start, customerID=" + paramString1, 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str1 = "INSERT INTO BPW_ReqActivityLog (LogID, CustomerID, ReqType, LogDate,Content) VALUES (?,?,?,?,?)";
    String str2 = DBUtil.getNextIndexString("LogID");
    String str3 = DBUtil.getCurrentLogDate();
    try
    {
      byte[] arrayOfByte = paramString2.getBytes("UTF-8");
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { str2, paramString1, new Integer(paramInt), str3, paramString2 };
      DBUtil.executeStatement(localFFSConnectionHolder, str1, arrayOfObject, i);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** ReqActivityLog.creatLog failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("ReqActivityLog.createLog done, customerID=" + paramString1, 6);
    return str2;
  }
  
  public static ReqActivityLog[] getReqActivityLogs(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, int paramInt, String paramString2, String paramString3)
    throws OutOfMemoryError, FFSException
  {
    FFSDebug.log("ReqActivityLog.getReqActivityLogs start, customerID=" + paramString1, 6);
    Vector localVector = new Vector();
    Object[] arrayOfObject1 = new Object[4];
    int i = 0;
    String str1 = "CustomerID=? AND ";
    String str2 = "ReqType=? AND ";
    String str3 = "LogDate>=? AND ";
    String str4 = "LogDate<=?";
    if ((paramString1.equals("")) || (paramString1 == null))
    {
      str1 = "";
    }
    else
    {
      arrayOfObject1[i] = paramString1;
      i += 1;
    }
    if (paramInt == 0)
    {
      str2 = "";
    }
    else
    {
      arrayOfObject1[i] = new Integer(paramInt);
      i += 1;
    }
    if ((paramString2.equals("")) || (paramString2 == null))
    {
      str3 = "";
    }
    else
    {
      arrayOfObject1[i] = paramString2;
      i += 1;
    }
    if ((paramString3.equals("")) || (paramString3 == null))
    {
      paramString3 = DBUtil.getCurrentLogDate();
      arrayOfObject1[i] = paramString3;
      i += 1;
    }
    else
    {
      arrayOfObject1[i] = paramString3;
      i += 1;
    }
    String str5 = "SELECT LogID, CustomerID, ReqType, LogDate, Content FROM BPW_ReqActivityLog  WHERE " + str1 + str2 + str3 + str4 + " ORDER BY LogDate";
    FFSDebug.log("ReqActivityLog.getReqActivityLogs, sql : " + str5, 6);
    Object[] arrayOfObject2 = new Object[i];
    for (int j = 0; j < i; j++)
    {
      arrayOfObject2[j] = arrayOfObject1[j];
      FFSDebug.log("ReqActivityLog.getReqActivityLogs, args " + j + " : " + arrayOfObject2[j], 6);
    }
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str5, arrayOfObject2);
      while (localFFSResultSet.getNextRow())
      {
        ReqActivityLog localReqActivityLog = new ReqActivityLog();
        localReqActivityLog.LogID = localFFSResultSet.getColumnString(1);
        localReqActivityLog.CustomerID = localFFSResultSet.getColumnString(2);
        localReqActivityLog.ReqType = localFFSResultSet.getColumnInt(3);
        localReqActivityLog.LogDate = localFFSResultSet.getColumnString(4);
        localReqActivityLog.Request = localFFSResultSet.getColumnString(5);
        localVector.addElement(localReqActivityLog);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localVector = null;
      throw localOutOfMemoryError;
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ReqActivityLog.getReqActivityLogs failed:" + localException1.toString());
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
        FFSDebug.log("*** ReqActivityLog.getReqActivityLogs failed:" + localException2.toString());
      }
    }
    if (localVector.isEmpty()) {
      return null;
    }
    FFSDebug.log("ReqActivityLog.getReqActivityLogs done, vec.size()=" + localVector.size(), 6);
    return (ReqActivityLog[])localVector.toArray(new ReqActivityLog[0]);
  }
  
  public static ReqActivityLog[] getReqActivityLogs(FFSConnection paramFFSConnection, String paramString1, int paramInt, String paramString2, String paramString3)
    throws OutOfMemoryError, FFSException
  {
    Vector localVector = new Vector();
    FFSDebug.log("ReqActivityLog.getReqActivityLogs start, customerID=" + paramString1, 6);
    Object[] arrayOfObject1 = new Object[4];
    int i = 0;
    String str1 = "CustomerID=? AND ";
    String str2 = "ReqType=? AND ";
    String str3 = "LogDate>=? AND ";
    String str4 = "LogDate<=?";
    if ((paramString1.equals("")) || (paramString1 == null))
    {
      str1 = "";
    }
    else
    {
      arrayOfObject1[i] = paramString1;
      i += 1;
    }
    if (paramInt == 0)
    {
      str2 = "";
    }
    else
    {
      arrayOfObject1[i] = new Integer(paramInt);
      i += 1;
    }
    if ((paramString2.equals("")) || (paramString2 == null))
    {
      str3 = "";
    }
    else
    {
      arrayOfObject1[i] = paramString2;
      i += 1;
    }
    if ((paramString3.equals("")) || (paramString3 == null))
    {
      paramString3 = DBUtil.getCurrentLogDate();
      arrayOfObject1[i] = paramString3;
      i += 1;
    }
    else
    {
      arrayOfObject1[i] = paramString3;
      i += 1;
    }
    String str5 = "SELECT LogID, CustomerID, ReqType, LogDate, Content FROM BPW_ReqActivityLog  WHERE " + str1 + str2 + str3 + str4 + " ORDER BY LogDate";
    FFSDebug.log("ReqActivityLog.getReqActivityLogs, sql : " + str5, 6);
    Object[] arrayOfObject2 = new Object[i];
    for (int j = 0; j < i; j++)
    {
      arrayOfObject2[j] = arrayOfObject1[j];
      FFSDebug.log("ReqActivityLog.getReqActivityLogs, args " + j + " : " + arrayOfObject2[j], 6);
    }
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = paramFFSConnection.prepareStmt(str5);
      localFFSResultSet.open(arrayOfObject2);
      while (localFFSResultSet.getNextRow())
      {
        ReqActivityLog localReqActivityLog = new ReqActivityLog();
        localReqActivityLog.LogID = localFFSResultSet.getColumnString(1);
        localReqActivityLog.CustomerID = localFFSResultSet.getColumnString(2);
        localReqActivityLog.ReqType = localFFSResultSet.getColumnInt(3);
        localReqActivityLog.LogDate = localFFSResultSet.getColumnString(4);
        localReqActivityLog.Request = localFFSResultSet.getColumnString(5);
        localVector.addElement(localReqActivityLog);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localVector = null;
      throw localOutOfMemoryError;
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ReqActivityLog.getReqActivityLogs failed:" + localException1.toString());
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
        FFSDebug.log("*** ReqActivityLog.getReqActivityLogs failed:" + localException2.toString());
      }
    }
    if (localVector.isEmpty()) {
      return null;
    }
    FFSDebug.log("ReqActivityLog.getReqActivityLogs done,vec.size()=" + localVector.size(), 6);
    return (ReqActivityLog[])localVector.toArray(new ReqActivityLog[0]);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws BPWException
  {
    FFSDebug.log("ReqActivityLog.delete start, ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    String str2 = "DELETE FROM BPW_ReqActivityLog WHERE LogDate <= ?";
    Object[] arrayOfObject = { str1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** ReqActivityLog.delete failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("ReqActivityLog.delete: done", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ReqActivityLog
 * JD-Core Version:    0.7.0.1
 */