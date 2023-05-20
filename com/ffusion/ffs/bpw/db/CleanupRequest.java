package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CleanupRequestInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;

public class CleanupRequest
  implements FFSConst, ACHConsts, DBConsts, BPWResource
{
  private static final int yq = 10;
  
  public static void create(FFSConnectionHolder paramFFSConnectionHolder, CleanupRequestInfo paramCleanupRequestInfo)
    throws FFSException
  {
    FFSDebug.log("CleanupRequest.create: start, customerID=", paramCleanupRequestInfo.getCustomerId(), 6);
    String str1 = "INSERT INTO BPW_CleanupReq (CustomerId,PaymentTypeList,AgeInDaysList,Memo) VALUES (?,?,?,?)";
    Object[] arrayOfObject = { paramCleanupRequestInfo.getCustomerId(), paramCleanupRequestInfo.getPaymentTypeList(";"), paramCleanupRequestInfo.getAgeInDaysList(";"), paramCleanupRequestInfo.getMemo() };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** CleanupRequest.create: failed:";
      FFSDebug.log(str2 + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** CleanupRequest.create: failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("CleanupRequest.create: done", 6);
  }
  
  public static int delete(FFSConnectionHolder paramFFSConnectionHolder, CleanupRequestInfo paramCleanupRequestInfo)
    throws FFSException
  {
    FFSDebug.log("CleanupRequestInfo.delete: start", 6);
    String str1 = "DELETE FROM BPW_CleanupReq WHERE CustomerId=? AND PaymentTypeList=? AND AgeInDaysList=?";
    Object[] arrayOfObject = { paramCleanupRequestInfo.getCustomerId(), paramCleanupRequestInfo.getPaymentTypeList(";"), paramCleanupRequestInfo.getAgeInDaysList(";") };
    if ((paramCleanupRequestInfo.getCustomerId() == null) || (paramCleanupRequestInfo.getCustomerId().trim().length() == 0))
    {
      str1 = "DELETE FROM BPW_CleanupReq WHERE CustomerId is null AND PaymentTypeList=? AND AgeInDaysList=?";
      arrayOfObject = new Object[] { paramCleanupRequestInfo.getPaymentTypeList(";"), paramCleanupRequestInfo.getAgeInDaysList(";") };
    }
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** CleanupRequestInfo.delete: failed:";
      FFSDebug.log(str2 + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** CleanupRequestInfo.delete: failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("CleanupRequestInfo.delete: done", 6);
    return i;
  }
  
  public static int deleteall(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("CleanupRequestInfo.deleteall: start", 6);
    String str1 = "DELETE FROM BPW_CleanupReq";
    Object[] arrayOfObject = new Object[0];
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** CleanupRequestInfo.deleteall: failed:";
      FFSDebug.log(str2 + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** CleanupRequestInfo.deleteall: failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("CleanupRequestInfo.deleteall: done", 6);
    return i;
  }
  
  public static CleanupRequestInfo[] getCleanupRequest(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    String str1 = "CleanupRequest.getCleanupRequest :";
    FFSDebug.log(str1 + "start, pageSize =" + paramInt, 6);
    FFSResultSet localFFSResultSet = null;
    CleanupRequestInfo localCleanupRequestInfo = null;
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = new Object[0];
    String str2 = "SELECT CustomerId, PaymentTypeList,AgeInDaysList,Memo FROM BPW_CleanupReq";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while ((localFFSResultSet.getNextRow()) && (localArrayList.size() <= paramInt))
      {
        localCleanupRequestInfo = new CleanupRequestInfo();
        localCleanupRequestInfo.setCustomerId(localFFSResultSet.getColumnString("CustomerId"));
        localCleanupRequestInfo.setPaymentTypeList(localFFSResultSet.getColumnString("PaymentTypeList"), ";");
        localCleanupRequestInfo.setAgeInDaysList(localFFSResultSet.getColumnString("AgeInDaysList"), ";");
        localCleanupRequestInfo.setMemo(localFFSResultSet.getColumnString("Memo"));
        localArrayList.add(localCleanupRequestInfo);
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, count =" + localArrayList.size(), 6);
    return (CleanupRequestInfo[])localArrayList.toArray(new CleanupRequestInfo[0]);
  }
  
  public static int delete(FFSConnectionHolder paramFFSConnectionHolder, CleanupRequestInfo[] paramArrayOfCleanupRequestInfo)
    throws FFSException
  {
    String str = "CleanupRequestInfo.delete (array): ";
    FFSDebug.log(str + "start", 6);
    int i = 0;
    if (paramArrayOfCleanupRequestInfo == null) {
      return 0;
    }
    int j = paramArrayOfCleanupRequestInfo.length;
    for (int k = 0; k < j; k++) {
      i += delete(paramFFSConnectionHolder, paramArrayOfCleanupRequestInfo[k]);
    }
    FFSDebug.log(str + "done, no of rows deleted =" + i, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.CleanupRequest
 * JD-Core Version:    0.7.0.1
 */