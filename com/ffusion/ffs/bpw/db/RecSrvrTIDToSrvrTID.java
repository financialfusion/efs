package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;
import java.util.Vector;

public class RecSrvrTIDToSrvrTID
  implements FFSConst, DBConsts, BPWResource
{
  public String RecSrvrTID;
  public String SrvrTID;
  public int IndexType;
  
  public RecSrvrTIDToSrvrTID() {}
  
  public RecSrvrTIDToSrvrTID(String paramString1, String paramString2)
  {
    this.RecSrvrTID = paramString1;
    this.SrvrTID = paramString2;
    this.IndexType = 0;
  }
  
  public RecSrvrTIDToSrvrTID(String paramString1, String paramString2, int paramInt)
  {
    this.RecSrvrTID = paramString1;
    this.SrvrTID = paramString2;
    this.IndexType = paramInt;
  }
  
  public static void create(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    create(paramFFSConnectionHolder, paramString1, paramString2, 0);
  }
  
  public static void create(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    FFSDebug.log("RecSrvrTIDToSrvrTID.create: start, RecSrvrTID=" + paramString1 + ",SrvrTID=" + paramString2 + ", IndexType=" + paramInt, 6);
    String str1 = "INSERT INTO BPW_RecSrvrTIDToSrvrTID (RecSrvrTID,SrvrTID, IndexType) VALUES (?,?,?)";
    Object[] arrayOfObject = { paramString1, paramString2, new Integer(paramInt) };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** RecSrvrTIDToSrvrTID.create: failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecSrvrTIDToSrvrTID.create: done", 6);
  }
  
  public static String[] getSrvrTIDs(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return getSrvrTIDs(paramFFSConnectionHolder, paramString, 0);
  }
  
  public static String[] getSrvrTIDs(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    Vector localVector = new Vector();
    FFSDebug.log("RecSrvrTIDToSrvrTID.getSrvrTIDs: start, RecSrvrTID=" + paramString + ", indexType=" + paramInt, 6);
    String str = "SELECT SrvrTID FROM BPW_RecSrvrTIDToSrvrTID WHERE RecSrvrTID=? AND IndexType=?";
    Object[] arrayOfObject = { paramString, new Integer(paramInt) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecSrvrTIDToSrvrTID.getSrvrTIDs:" + localException1.toString());
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
        FFSDebug.log("*** RecSrvrTIDToSrvrTID.getSrvrTIDs:failed:" + localException2.toString());
      }
    }
    FFSDebug.log("RecSrvrTIDToSrvrTID.getSrvrTIDs: done, rows=" + localVector.size(), 6);
    return (String[])localVector.toArray(new String[0]);
  }
  
  public static String[] getRecSrvrTIDs(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return getRecSrvrTIDs(paramFFSConnectionHolder, paramString, 0);
  }
  
  public static String[] getRecSrvrTIDs(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    Vector localVector = new Vector();
    FFSDebug.log("RecSrvrTIDToSrvrTID.getRecSrvrTIDs: start, SrvrTID=" + paramString, 6);
    String str = "SELECT RecSrvrTID FROM BPW_RecSrvrTIDToSrvrTID WHERE SrvrTID=? AND IndexType=?";
    Object[] arrayOfObject = { paramString, new Integer(paramInt) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecSrvrTIDToSrvrTID.getRecSrvrTIDs:" + localException1.toString());
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
        FFSDebug.log("*** RecSrvrTIDToSrvrTID.getSrvrTIDs:failed:" + localException2.toString());
      }
    }
    FFSDebug.log("RecSrvrTIDToSrvrTID.getRecSrvrTIDs: done", 6);
    return (String[])localVector.toArray(new String[0]);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException, BPWException
  {
    delete(paramFFSConnectionHolder, paramString1, paramString2, 0);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException, BPWException
  {
    FFSDebug.log("RecSrvrTIDToSrvrTID.delete: start, RecSrvrTID=" + paramString1 + ",SrvrTID=" + paramString2 + ", indexType =" + paramInt, 6);
    String str1 = "DELETE FROM BPW_RecSrvrTIDToSrvrTID WHERE RecSrvrTID=? AND SrvrTID=? AND IndexType=?";
    Object[] arrayOfObject = { paramString1, paramString2, new Integer(paramInt) };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** RecSrvrTIDToSrvrTID.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecSrvrTIDToSrvrTID.delete: done, rows deleted =" + i, 6);
  }
  
  public static void deleteBySrvrTID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException, BPWException
  {
    FFSDebug.log("RecSrvrTIDToSrvrTID.deleteBySrvrTID: start, SrvrTID=" + paramString, 6);
    String str1 = "DELETE FROM BPW_RecSrvrTIDToSrvrTID WHERE SrvrTID=? AND IndexType=?";
    Object[] arrayOfObject = { paramString, new Integer(0) };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** RecSrvrTIDToSrvrTID.deleteBySrvrTID failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecSrvrTIDToSrvrTID.deleteBySrvrTID: done", 6);
  }
  
  public static void deleteByRecSrvrTID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException, BPWException
  {
    FFSDebug.log("RecSrvrTIDToSrvrTID.deleteByRecSrvrTID: start, RecSrvrTID=" + paramString, 6);
    String str1 = "DELETE FROM BPW_RecSrvrTIDToSrvrTID WHERE RecSrvrTID=? AND IndexType=?";
    Object[] arrayOfObject = { paramString, new Integer(0) };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** RecSrvrTIDToSrvrTID.deleteByRecSrvrTID failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecSrvrTIDToSrvrTID.deleteByRecSrvrTID: done", 6);
  }
  
  public static int deleteBatchByRecSrvrTID(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("RecSrvrTIDToSrvrTID.deleteBatchByRecSrvrTID: start.", 6);
    String str1 = "DELETE FROM BPW_RecSrvrTIDToSrvrTID WHERE RecSrvrTID=? AND IndexType=?";
    try
    {
      ArrayList localArrayList1 = new ArrayList();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.add(paramArrayOfString[i]);
        localArrayList2.add(String.valueOf(0));
        localArrayList1.add(localArrayList2);
      }
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, localArrayList1);
    }
    catch (Exception localException)
    {
      String str2 = "*** RecSrvrTIDToSrvrTID.deleteBatchByRecSrvrTID failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecSrvrTIDToSrvrTID.deleteBatchByRecSrvrTID done. No of rows = " + paramArrayOfString.length, 6);
    return paramArrayOfString.length;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID
 * JD-Core Version:    0.7.0.1
 */