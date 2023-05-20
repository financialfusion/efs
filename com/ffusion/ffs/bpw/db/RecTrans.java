package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSDebug;
import java.util.Vector;

public class RecTrans
  implements DBConsts
{
  private String k7;
  private String k5;
  private String k8;
  private String lb;
  private Object k6;
  private int la;
  private String k9;
  private String k4;
  
  public void setRecSrvrTID(String paramString)
  {
    this.k7 = paramString;
  }
  
  public String getRecSrvrTID()
  {
    return this.k7;
  }
  
  public void setSyncType(String paramString)
  {
    this.k8 = paramString;
  }
  
  public String getSyncType()
  {
    return this.k8;
  }
  
  public void setAcctID(String paramString)
  {
    this.lb = paramString;
  }
  
  public String getAcctID()
  {
    return this.lb;
  }
  
  public void setStatus(String paramString)
  {
    this.k4 = paramString;
  }
  
  public String getStatus()
  {
    return this.k4;
  }
  
  public void setResponse(Object paramObject)
  {
    this.k6 = paramObject;
  }
  
  public Object getResponse()
  {
    return this.k6;
  }
  
  public void setCustomerID(String paramString)
  {
    this.k5 = paramString;
  }
  
  public String getCustomerID()
  {
    return this.k5;
  }
  
  public static String[] getRecTrans(String paramString1, String paramString2)
    throws BPWException
  {
    String[] arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str = "SELECT Response, Status FROM BPW_RecTrans WHERE AcctID=? AND SyncType=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecTrans.getRecTrans failed:");
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
        FFSDebug.log("*** RecTrans.getRecTrans failed:" + localException2.toString());
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    int i = localVector.size();
    arrayOfString = new String[i];
    for (int j = 0; j < i; j++) {
      arrayOfString[j] = ((String)localVector.elementAt(j));
    }
    return arrayOfString;
  }
  
  public void updateRecTrans(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str = "UPDATE BPW_RecTrans SET Response=?, Status=? WHERE AcctID=? AND SyncType=?";
    Object[] arrayOfObject = { this.lb, this.k8 };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** RecTrans.updateRecTrans failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RecTrans
 * JD-Core Version:    0.7.0.1
 */