package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class Trans
  implements FFSConst, DBConsts
{
  private String xV;
  private String xR;
  private String xT;
  private String xX;
  private Object xS;
  private int xW;
  private String xU;
  private String xQ;
  
  public void setSrvrTID(String paramString)
  {
    this.xV = paramString;
  }
  
  public String getSrvrTID()
  {
    return this.xV;
  }
  
  public void setSyncType(String paramString)
  {
    this.xT = paramString;
  }
  
  public String getSyncType()
  {
    return this.xT;
  }
  
  public void setAcctID(String paramString)
  {
    this.xX = paramString;
  }
  
  public String getAcctID()
  {
    return this.xX;
  }
  
  public void setStatus(String paramString)
  {
    this.xQ = paramString;
  }
  
  public String getStatus()
  {
    return this.xQ;
  }
  
  public void setResponse(Object paramObject)
  {
    this.xS = paramObject;
  }
  
  public Object getResponse()
  {
    return this.xS;
  }
  
  public void setCustomerID(String paramString)
  {
    this.xR = paramString;
  }
  
  public String getCustomerID()
  {
    return this.xR;
  }
  
  public static Object[] getTrans(String paramString1, String paramString2)
    throws BPWException
  {
    Object[] arrayOfObject1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str = "SELECT Response, Status FROM BPW_Trans WHERE AcctID=? AND SyncType=?";
    Object[] arrayOfObject2 = { paramString1, paramString2 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject2);
      if (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnBytes(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Trans.getTrans failed:" + localException1.toString());
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
        FFSDebug.log("*** Trans.getTrans failed:" + localException2.toString());
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    int i = localVector.size();
    arrayOfObject1 = new Object[i];
    try
    {
      for (int j = 0; j < i; j++) {
        arrayOfObject1[j] = FFSUtil.bytesToObject((byte[])localVector.elementAt(j));
      }
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log("*** Trans.getTrans failed:" + localFFSException.toString());
      throw new BPWException(localFFSException.toString());
    }
    return arrayOfObject1;
  }
  
  public void updateTrans(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str = "UPDATE BPW_Trans SET Response=?, Status=? WHERE AcctID=? AND SyncType=?";
    Object[] arrayOfObject = { this.xX, this.xT };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Trans.updateTrans failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
  }
  
  public static boolean checkDuplicateTIDAndSaveTID(String paramString)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    boolean bool = false;
    try
    {
      if (saveTID(paramString, localFFSConnectionHolder) == 0) {
        bool = true;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return bool;
  }
  
  public static int saveTID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
  {
    int i = 0;
    String str1 = "INSERT INTO BPW_TransID(TID, Submitdate) VALUES( ?,?)";
    String str2 = DBUtil.getCurrentLogDate();
    FFSDebug.log("Trans.saveTID called: tid: " + paramString, 6);
    Object[] arrayOfObject = { paramString, str2 };
    try
    {
      i = DBUtil.executeStatementInTX(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** Trans.saveTID failed:" + localThrowable.toString() + ". tid: " + paramString, 0);
    }
    return i;
  }
  
  public static void deleteTransID(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws BPWException
  {
    FFSDebug.log("Trans.deleteTransID: start, ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    String str2 = "DELETE FROM BPW_TransID WHERE Submitdate <= ?";
    Object[] arrayOfObject = { str1 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** Trans.deleteTransID failed:";
      FFSDebug.log(str3 + localException.getMessage());
      throw new BPWException(str3 + localException.getMessage());
    }
    FFSDebug.log("Trans.deleteTransID: done", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Trans
 * JD-Core Version:    0.7.0.1
 */