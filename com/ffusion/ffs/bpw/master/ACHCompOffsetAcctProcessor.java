package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHCompOffsetAcct;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class ACHCompOffsetAcctProcessor
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  private PropertyConfig cO = null;
  private int cN = 1;
  
  public ACHCompOffsetAcctInfo addACHCompOffsetAcct(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcctProcessor.addBPWBakAcct";
    FFSDebug.log(str1 + " start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = str1 + " failed: " + "Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      paramACHCompOffsetAcctInfo = ACHCompOffsetAcct.createACHCompOffsetAcct(localFFSConnectionHolder, paramACHCompOffsetAcctInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = str1 + " failed: " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramACHCompOffsetAcctInfo;
  }
  
  public ACHCompOffsetAcctInfo modACHCompOffsetAcct(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcctProcessor.modBPWBakAcct";
    FFSDebug.log(str1 + " start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = str1 + " failed: " + "Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      paramACHCompOffsetAcctInfo = ACHCompOffsetAcct.modACHCompOffsetAcct(localFFSConnectionHolder, paramACHCompOffsetAcctInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = str1 + " failed: " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramACHCompOffsetAcctInfo;
  }
  
  public ACHCompOffsetAcctInfo getACHCompOffsetAcctByAcctId(String paramString)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcctProcessor.getACHCompOffsetAcctByAcctId";
    FFSDebug.log(str1 + " start ...", 6);
    ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = null;
    if (paramString == null)
    {
      localObject1 = str1 + " failed: acctId is null";
      FFSDebug.log((String)localObject1, 0);
      localACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
      localACHCompOffsetAcctInfo.setStatusCode(16000);
      localACHCompOffsetAcctInfo.setStatusMsg("acctId  is null");
      return localACHCompOffsetAcctInfo;
    }
    Object localObject1 = null;
    String str2;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = str1 + " failed: " + "Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      localACHCompOffsetAcctInfo = ACHCompOffsetAcct.getACHCompOffsetAcctByAcctId((FFSConnectionHolder)localObject1, paramString);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str2 = str1 + " failed:" + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    return localACHCompOffsetAcctInfo;
  }
  
  public ACHCompOffsetAcctInfo deleteACHCompOffsetAcct(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcctProcessor.deleteACHCompOffsetAcct";
    FFSDebug.log(str1 + " start... ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = str1 + " failed: " + "Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      paramACHCompOffsetAcctInfo = ACHCompOffsetAcct.deleteACHCompOffsetAcct(localFFSConnectionHolder, paramACHCompOffsetAcctInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = str1 + " failed: " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      FFSDebug.log(str1 + " end", 6);
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramACHCompOffsetAcctInfo;
  }
  
  public ACHCompOffsetAcctInfo[] getACHCompOffsetAcctsByCompId(String paramString)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcctProcessor.getACHCompOffsetAcctByCompId";
    FFSDebug.log(str1 + " start: compId = " + paramString, 6);
    ACHCompOffsetAcctInfo[] arrayOfACHCompOffsetAcctInfo = null;
    if (paramString == null)
    {
      localObject1 = str1 + " failed: compId is null";
      FFSDebug.log((String)localObject1, 0);
      return arrayOfACHCompOffsetAcctInfo;
    }
    Object localObject1 = null;
    String str2;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = str1 + " failed: " + "Error Retrieving Database Connection " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      arrayOfACHCompOffsetAcctInfo = ACHCompOffsetAcct.getACHCompOffsetAcctsByCompId((FFSConnectionHolder)localObject1, paramString);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str2 = str1 + " failed:" + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    return arrayOfACHCompOffsetAcctInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHCompOffsetAcctProcessor
 * JD-Core Version:    0.7.0.1
 */