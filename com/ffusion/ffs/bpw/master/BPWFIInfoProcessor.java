package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class BPWFIInfoProcessor
  implements DBConsts, FFSConst, OFXConsts
{
  public BPWFIInfo addBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.addBPWFIInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWFIInfo localBPWFIInfo1 = null;
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***BPWFIInfoProcessor.addBPWFIInfo failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      localBPWFIInfo1 = BPWFI.create(localFFSConnectionHolder, paramBPWFIInfo);
      localFFSConnectionHolder.conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***BPWFIInfoProcessor.addBPWFIInfo failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWFIInfo modBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.modBPWFIInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWFIInfo localBPWFIInfo1 = null;
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***BPWFIInfoProcessor.modBPWFIInfo failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      localBPWFIInfo1 = BPWFI.modify(localFFSConnectionHolder, paramBPWFIInfo);
      localFFSConnectionHolder.conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***BPWFIInfoProcessor.modBPWFIInfo failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWFIInfo canBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.canBPWFIInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWFIInfo localBPWFIInfo1 = null;
    if (paramBPWFIInfo == null)
    {
      String str1 = "***BPWFIInfoProcessor.canBPWFIInfo failed: FI Object passed is null";
      FFSDebug.log(str1, 0);
      localBPWFIInfo1 = new BPWFIInfo();
      localBPWFIInfo1.setStatusCode(16000);
      localBPWFIInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWFIInfo " }, "API_MESSAGE"));
      return localBPWFIInfo1;
    }
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***BPWFIInfoProcessor.canBPWFIInfo failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      localBPWFIInfo1 = BPWFI.canBPWFIInfo(localFFSConnectionHolder, paramBPWFIInfo);
      localFFSConnectionHolder.conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***BPWFIInfoProcessor.canBPWFIInfo failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWFIInfo getBPWFIInfo(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.getBPWFIInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWFIInfo localBPWFIInfo1 = null;
    if (paramString == null)
    {
      String str1 = "***BPWFIInfoProcessor.getBPWFIInfo failed: FIID is null";
      FFSDebug.log(str1, 0);
      localBPWFIInfo1 = new BPWFIInfo();
      localBPWFIInfo1.setStatusCode(16000);
      localBPWFIInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FIID " }, "API_MESSAGE"));
      return localBPWFIInfo1;
    }
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***BPWFIInfoProcessor.getBPWFIInfo failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      localBPWFIInfo1 = BPWFI.getBPWFIInfo(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***BPWFIInfoProcessor.getBPWFIInfo failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByType(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.getBPWFIInfoByType start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1;
    if (paramString == null)
    {
      String str = "***BPWFIInfoProcessor.getBPWFIInfoByType failed: FIType is null";
      FFSDebug.log(str, 0);
      localObject1 = new BPWFIInfo();
      ((BPWFIInfo)localObject1).setStatusCode(16000);
      ((BPWFIInfo)localObject1).setStatusMsg("FIType  is null");
      BPWFIInfo[] arrayOfBPWFIInfo2 = { localObject1 };
      return arrayOfBPWFIInfo2;
    }
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfoByType failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      BPWFIInfo[] arrayOfBPWFIInfo1 = BPWFI.getBPWFIInfoByType(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      localObject1 = arrayOfBPWFIInfo1;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfoByType failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByStatus(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.getBPWFIInfoByStatus start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1;
    if (paramString == null)
    {
      String str = "***BPWFIInfoProcessor.getBPWFIInfoByStatus failed: FIStatus is null";
      FFSDebug.log(str, 0);
      localObject1 = new BPWFIInfo();
      ((BPWFIInfo)localObject1).setStatusCode(16000);
      ((BPWFIInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FIStatus " }, "API_MESSAGE"));
      BPWFIInfo[] arrayOfBPWFIInfo2 = { localObject1 };
      return arrayOfBPWFIInfo2;
    }
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfoByStatus failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      BPWFIInfo[] arrayOfBPWFIInfo1 = BPWFI.getBPWFIInfoByStatus(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      localObject1 = arrayOfBPWFIInfo1;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfoByStatus failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWFIInfo getBPWFIInfoByACHId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.getBPWFIInfoByACHId start", 6);
    BPWFIInfo localBPWFIInfo1 = null;
    if (paramString == null)
    {
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfoByACHId failed: oDFIACHId is null";
      FFSDebug.log((String)localObject1, 0);
      localBPWFIInfo1 = new BPWFIInfo();
      localBPWFIInfo1.setStatusCode(16010);
      localBPWFIInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWFIInfo", "oDFIACHId " }, "API_MESSAGE"));
      return localBPWFIInfo1;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***BPWFIInfoProcessor.getBPWFIInfoByACHId failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      localBPWFIInfo1 = BPWFI.getBPWFIInfoByACHId((FFSConnectionHolder)localObject1, paramString);
      ((FFSConnectionHolder)localObject1).conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str = "***BPWFIInfoProcessor.getBPWFIInfoByACHId failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  public BPWFIInfo getBPWFIInfoByRTN(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.getBPWFIInfoByRTN start", 6);
    BPWFIInfo localBPWFIInfo1 = null;
    if (paramString == null)
    {
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfoByRTN failed: fiRTN is null";
      FFSDebug.log((String)localObject1, 0);
      localBPWFIInfo1 = new BPWFIInfo();
      localBPWFIInfo1.setStatusCode(16000);
      localBPWFIInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FIRTN " }, "API_MESSAGE"));
      return localBPWFIInfo1;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***BPWFIInfoProcessor.getBPWFIInfoByRTN failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      localBPWFIInfo1 = BPWFI.getBPWFIInfoByRTN((FFSConnectionHolder)localObject1, paramString);
      ((FFSConnectionHolder)localObject1).conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str = "***BPWFIInfoProcessor.getBPWFIInfoByRTN failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByGroup(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.getBPWFIInfoByGroup start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1;
    if (paramString == null)
    {
      String str = "***BPWFIInfoProcessor.getBPWFIInfoByGroup failed: FIType is null";
      FFSDebug.log(str, 0);
      localObject1 = new BPWFIInfo();
      ((BPWFIInfo)localObject1).setStatusCode(16000);
      ((BPWFIInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FIGroup " }, "API_MESSAGE"));
      BPWFIInfo[] arrayOfBPWFIInfo2 = { localObject1 };
      return arrayOfBPWFIInfo2;
    }
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfoByGroup failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      BPWFIInfo[] arrayOfBPWFIInfo1 = BPWFI.getBPWFIInfoByGroup(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      localObject1 = arrayOfBPWFIInfo1;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfoByGroup failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWFIInfo activateBPWFI(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWFIInfoProcessor.activateBPWFI start", 6);
    BPWFIInfo localBPWFIInfo = null;
    if (paramString == null)
    {
      localBPWFIInfo = new BPWFIInfo();
      localBPWFIInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "fIId " }, "API_MESSAGE");
      localBPWFIInfo.setStatusMsg((String)localObject1);
      FFSDebug.log("BPWFIInfoProcessor.activateBPWFI, " + (String)localObject1, 0);
      return localBPWFIInfo;
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
      localBPWFIInfo = BPWFI.activateBPWFI(paramString, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str1 = "*** BPWFIInfoProcessor.activateBPWFI failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log("BPWFIInfoProcessor.activateBPWFI: done", 6);
    return localBPWFIInfo;
  }
  
  public BPWFIInfo[] getBPWFIInfo(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWFIIProcessor.getBPWFIInfo (Multiple) start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWFIInfo localBPWFIInfo = null;
    Object localObject1;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      String str = "***BPWFIInfoProcessor.getBPWFIInfo  (Multiple) failed: Null or Empty TemplateId Array passed";
      FFSDebug.log(str, 0);
      localBPWFIInfo = new BPWFIInfo();
      localBPWFIInfo.setStatusCode(16000);
      localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FIType " }, "API_MESSAGE"));
      localObject1 = new BPWFIInfo[] { localBPWFIInfo };
      return localObject1;
    }
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfo (Multiple) failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      BPWFIInfo[] arrayOfBPWFIInfo = BPWFI.getBPWFIInfo(localFFSConnectionHolder, paramArrayOfString);
      localFFSConnectionHolder.conn.commit();
      localObject1 = arrayOfBPWFIInfo;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***BPWFIInfoProcessor.getBPWFIInfo Multiple) failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.BPWFIInfoProcessor
 * JD-Core Version:    0.7.0.1
 */