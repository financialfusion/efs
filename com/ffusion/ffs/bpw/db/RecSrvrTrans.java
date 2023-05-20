package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.io.ByteArrayInputStream;
import java.util.Vector;

public class RecSrvrTrans
  implements FFSConst, DBConsts
{
  private String kV;
  private String kS;
  private String kY;
  private String kZ;
  private String kT;
  private String kU;
  private String kR;
  private int kQ;
  private String kW;
  private String kX;
  
  public void setRecSrvrTID(String paramString)
  {
    this.kV = paramString;
  }
  
  public String getRecSrvrTID()
  {
    return this.kV;
  }
  
  public void setSyncType(String paramString)
  {
    this.kY = paramString;
  }
  
  public String getSyncType()
  {
    return this.kY;
  }
  
  public void setBankID(String paramString)
  {
    this.kZ = paramString;
  }
  
  public String getBankID()
  {
    return this.kZ;
  }
  
  public void setAcctID(String paramString)
  {
    this.kT = paramString;
  }
  
  public String getAcctID()
  {
    return this.kT;
  }
  
  public void setAcctType(String paramString)
  {
    this.kU = paramString;
  }
  
  public String getAcctType()
  {
    return this.kU;
  }
  
  public void setStatus(String paramString)
  {
    this.kX = paramString;
  }
  
  public String getStatus()
  {
    return this.kX;
  }
  
  public void setResponse(String paramString)
  {
    this.kR = paramString;
  }
  
  public String getResponse()
  {
    return this.kR;
  }
  
  public void setSubmitdate(String paramString)
  {
    this.kW = paramString;
  }
  
  public String getSubmitdate()
  {
    return this.kW;
  }
  
  public void setCustomerID(String paramString)
  {
    this.kS = paramString;
  }
  
  public String getCustomerID()
  {
    return this.kS;
  }
  
  public void setToken(int paramInt)
  {
    this.kQ = paramInt;
  }
  
  public int getToken()
  {
    return this.kQ;
  }
  
  public static String[] findResponseByRecSrvrTID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.findResponseByRecSrvrTID start,recsrvrTID=" + paramString, 6);
    String[] arrayOfString = new String[2];
    String str = "SELECT SyncType, Response FROM BPW_RecSrvrTrans WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        arrayOfString[0] = localFFSResultSet.getColumnString(1);
        arrayOfString[1] = localFFSResultSet.getColumnString(2);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecSrvrTrans.findResponseByRecSrvrTID failed:" + localException1.toString());
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
        FFSDebug.log("*** InstructionActivityLog.getInstructionActivityLogs failed:" + localException2.toString());
      }
    }
    FFSDebug.log("RecSrvrTrans.findResponseByRecSrvrTID done,recsrvrTID=" + paramString, 6);
    return arrayOfString;
  }
  
  public static void updateResponseByRecSrvrTID(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder, String paramString3)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.updateResponseByRecSrvrTID start,recsrvrTID=" + paramString2, 6);
    String str1 = FFSUtil.getDateString();
    String str2 = "UPDATE BPW_RecSrvrTrans SET Response=?, Submitdate=?, Status=?  WHERE RecSrvrTID=?";
    try
    {
      byte[] arrayOfByte = paramString1.getBytes("UTF-8");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { paramString1, str1, paramString3, paramString2 };
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject, i);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** RecSrvrTrans.updateResponseBySrvrTID failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecSrvrTrans.updateResponseByRecSrvrTID done,recsrvrTID=" + paramString2, 6);
  }
  
  public void updateRecSrvrTrans(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.updateRecSrvrTrans start,RecSrvrTID=" + this.kV, 6);
    String str1 = FFSUtil.getDateString();
    String str2 = "UPDATE BPW_RecSrvrTrans SET BankID=?, AcctID=?, AcctType=?, Response=?, Submitdate=?, Status=? WHERE RecSrvrTID=?";
    try
    {
      byte[] arrayOfByte = this.kR.getBytes("UTF-8");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { this.kZ, this.kT, this.kU, this.kR, str1, this.kX, this.kV };
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject, i);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** RecSrvrTrans.updateRecSrvrTrans failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecSrvrTrans.updateRecSrvrTrans done,RecSrvrTID=" + this.kV, 6);
  }
  
  public static String[] getRecSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.getRecSrvrTrans start,acctID=" + paramString2, 6);
    String[] arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str = "SELECT Response, Status FROM BPW_RecSrvrTrans WHERE CustomerID=? AND AcctID=? AND AcctType=? AND SyncType=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecSrvrTrans.getRecTrans failed:" + FFSDebug.stackTrace(localException1));
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
        FFSDebug.log("*** RecSrvrTIDToSrvrTID.getSrvrTIDs:failed:" + FFSDebug.stackTrace(localException2));
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfString = (String[])localVector.toArray(new String[0]);
    FFSDebug.log("RecSrvrTrans.getRecSrvrTrans done,acctID=" + paramString2, 6);
    return arrayOfString;
  }
  
  public static String[] getRecSrvrTransWithBank(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.getRecSrvrTransWithBank start,acctID=" + paramString2, 6);
    FFSDebug.log("RecSrvrTrans.getRecSrvrTransWithBank bankId=" + paramString5, 6);
    String[] arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str = "SELECT Response, Status FROM BPW_RecSrvrTrans WHERE CustomerID=? AND AcctID=? AND AcctType=? AND SyncType=? AND BankID=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4, paramString5 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecSrvrTrans.getRecSrvrTransWithBank failed:" + FFSDebug.stackTrace(localException1));
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
        FFSDebug.log("*** RecSrvrTIDToSrvrTID.getRecSrvrTransWithBank:failed:" + FFSDebug.stackTrace(localException2));
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfString = (String[])localVector.toArray(new String[0]);
    FFSDebug.log("RecSrvrTrans.getRecSrvrTransWithBank done,acctID=" + paramString2, 6);
    return arrayOfString;
  }
  
  public static String[] getRecSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.getRecSrvrTrans start,acctID=" + paramString2, 6);
    String[] arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str = "SELECT Response FROM BPW_RecSrvrTrans WHERE CustomerID=? AND AcctID=? AND AcctType=? AND SyncType=? AND Status=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4, paramString5 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecSrvrTrans.getRecTrans failed:" + localException1.toString());
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
        FFSDebug.log("*** RecSrvrTIDToSrvrTID.getSrvrTIDs:failed:" + FFSDebug.stackTrace(localException2));
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfString = (String[])localVector.toArray(new String[0]);
    FFSDebug.log("RecSrvrTrans.getRecSrvrTrans done,acctID=" + paramString2, 6);
    return arrayOfString;
  }
  
  public static void cancelRecSrvrTrans(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.cancelRecSrvrTrans start,recsrvrTID=" + paramString, 6);
    String str = "DELETE FROM BPW_RecSrvrTrans WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** RecSrvrTrans.cancelTrans failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecSrvrTrans.cancelRecSrvrTrans done,recsrvrTID=" + paramString, 6);
  }
  
  public void storeToDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.storeToDB start,recsrvrTID=" + this.kV, 6);
    String str = "INSERT INTO BPW_RecSrvrTrans( RecSrvrTID,BankID, AcctID,AcctType, CustomerID, Response, Token, Submitdate, SyncType, Status) VALUES(?,?,?,?,?,?,?,?,?,?)";
    try
    {
      byte[] arrayOfByte = this.kR.getBytes("UTF-8");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { this.kV, this.kZ, this.kT, this.kU, this.kS, this.kR, new Integer(0), this.kW, this.kY, this.kX };
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject, i);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** RecSrvrTrans.storeToDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecSrvrTrans.storeToDB done,recsrvrTID=" + this.kV, 6);
  }
  
  public static RecSrvrTrans getRecSrvrTransById(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.getRecSrvrTransById start, recsrvrTID=" + paramString, 6);
    String str = "SELECT BankID, AcctID, AcctType, CustomerID, Response, SyncType, Token, Submitdate, Status FROM BPW_RecSrvrTrans WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    RecSrvrTrans localRecSrvrTrans = new RecSrvrTrans();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localRecSrvrTrans.kZ = localFFSResultSet.getColumnString("BankID");
        localRecSrvrTrans.kT = localFFSResultSet.getColumnString("AcctID");
        localRecSrvrTrans.kU = localFFSResultSet.getColumnString("AcctType");
        localRecSrvrTrans.kS = localFFSResultSet.getColumnString("CustomerID");
        localRecSrvrTrans.kR = localFFSResultSet.getColumnString("Response");
        localRecSrvrTrans.kY = localFFSResultSet.getColumnString("SyncType");
        localRecSrvrTrans.kQ = localFFSResultSet.getColumnInt("Token");
        localRecSrvrTrans.kW = localFFSResultSet.getColumnString("Submitdate");
        localRecSrvrTrans.kX = localFFSResultSet.getColumnString("Status");
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecSrvrTrans.getRecSrvrTransById failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** RecSrvrTrans.getRecSrvrTransById  failed:" + localException2.toString());
      }
    }
    FFSDebug.log("RecSrvrTrans.getRecSrvrTransById done, recsrvrTID=" + paramString, 6);
    return localRecSrvrTrans;
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws BPWException
  {
    FFSDebug.log("RecSrvrTrans.deleteBatch start.", 6);
    String str = "DELETE FROM BPW_RecSrvrTrans WHERE RecSrvrTID=?";
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** RecSrvrTrans.deleteBatch failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecSrvrTrans.deleteBatch done. No of rows = " + paramArrayOfString.length, 6);
    return paramArrayOfString.length;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RecSrvrTrans
 * JD-Core Version:    0.7.0.1
 */