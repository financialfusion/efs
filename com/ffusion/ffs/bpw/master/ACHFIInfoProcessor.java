package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHFI;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class ACHFIInfoProcessor
  implements DBConsts, FFSConst
{
  public ACHFIInfo addACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    FFSDebug.log("ACHFIIProcessor.addACHFIInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***ACHFIInfoProcessor.addACHFIInfo failed: Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      paramACHFIInfo = ACHFI.create(localFFSConnectionHolder, paramACHFIInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***ACHFIInfoProcessor.addACHFIInfo failed: " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramACHFIInfo;
  }
  
  public ACHFIInfo modACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    FFSDebug.log("ACHFIIProcessor.modACHFIInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***ACHFIInfoProcessor.modACHFIInfo failed: Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      paramACHFIInfo = ACHFI.modACHFIInfo(localFFSConnectionHolder, paramACHFIInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***ACHFIInfoProcessor.modACHFIInfo failed: " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramACHFIInfo;
  }
  
  public ACHFIInfo getACHFIInfo(String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHFIInfoProcessor.getACHFIInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    ACHFIInfo localACHFIInfo = null;
    if (paramString == null)
    {
      String str1 = "***ACHFIInfoProcessor.getACHFIInfo failed: ODFIACHId is null";
      FFSDebug.log(str1, 0);
      localACHFIInfo = new ACHFIInfo();
      localACHFIInfo.setStatusCode(16000);
      localACHFIInfo.setStatusMsg("ODFIACHId  is null");
      return localACHFIInfo;
    }
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***ACHFIInfoProcessor.getACHFIInfo failed: Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      localACHFIInfo = ACHFI.getACHFIInfo(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***ACHFIInfoProcessor.getACHFIInfo failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localACHFIInfo;
  }
  
  public static ACHFIInfo[] getFIInfoByRTN(String paramString)
    throws FFSException
  {
    FFSDebug.log("***ACHFIInfoProcessor.getFIInfoByRTN start ...", 6);
    Object localObject2;
    if (paramString == null)
    {
      localObject1 = "***ACHFIInfoProcessor.getFIInfoByRTN failed: oDFIRTN is null";
      FFSDebug.log((String)localObject1, 0);
      ACHFIInfo localACHFIInfo = new ACHFIInfo();
      localACHFIInfo.setStatusCode(16000);
      localACHFIInfo.setStatusMsg("oDFIRTN  is null");
      localObject2 = new ACHFIInfo[] { localACHFIInfo };
      return localObject2;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = "***ACHFIInfoProcessor.getACHFIInfoByRTN failed: Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    try
    {
      ACHFIInfo[] arrayOfACHFIInfo = ACHFI.getFIInfoByRTN((FFSConnectionHolder)localObject1, paramString);
      ((FFSConnectionHolder)localObject1).conn.commit();
      localObject2 = arrayOfACHFIInfo;
      return localObject2;
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      localObject2 = "***ACHFIInfoProcessor.getFIInfoByRTN failed:" + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  public ACHFIInfo canACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    FFSDebug.log("ACHFIInfoProcessor.getACHFIInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    ACHFIInfo localACHFIInfo1 = null;
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***ACHFIInfoProcessor.getACHFIInfo failed: Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      localACHFIInfo1 = ACHFI.canACHFIInfo(localFFSConnectionHolder, paramACHFIInfo);
      if (localACHFIInfo1.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        ACHFIInfo localACHFIInfo2 = localACHFIInfo1;
        return localACHFIInfo2;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***ACHFIInfoProcessor.getACHFIInfo failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localACHFIInfo1;
  }
  
  public ACHFIInfo activateACHFI(String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHFIInfoProcessor.activateACHFI start, oDFIACHId =" + paramString, 6);
    ACHFIInfo localACHFIInfo = null;
    if (paramString == null)
    {
      localACHFIInfo = new ACHFIInfo();
      localACHFIInfo.setStatusCode(16000);
      localObject1 = "ODFIACHId  is null";
      localACHFIInfo.setStatusMsg((String)localObject1);
      FFSDebug.log("ACHFIInfoProcessor.activateACHFI, " + (String)localObject1);
      return localACHFIInfo;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str1;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      localACHFIInfo = ACHFI.activateACHFI(paramString, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str1 = "*** ACHFIInfoProcessor.activateACHFI failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log("ACHFIInfoProcessor.activateACHFI: done", 6);
    return localACHFIInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHFIInfoProcessor
 * JD-Core Version:    0.7.0.1
 */