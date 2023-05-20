package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.RPPSBiller;
import com.ffusion.ffs.bpw.db.RPPSFI;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo;
import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class RPPSProcessor
  implements DBConsts, FFSConst
{
  public RPPSFIInfo addRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log("RPPSProcessor.addRPPSFIInfo start...", 6);
    if (paramRPPSFIInfo == null)
    {
      String str1 = "***RPPSFIInfo.addRPPSFIInfo failed: Null FI object passed";
      FFSDebug.log(str1, 0);
      paramRPPSFIInfo = new RPPSFIInfo();
      paramRPPSFIInfo.setStatusCode(16000);
      paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  is null");
      return paramRPPSFIInfo;
    }
    String str2;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Exception localException1)
    {
      str2 = "***RPPSProcessor.addRPPSFIInfo failed: Error retrieving database connection " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException1, str2);
    }
    try
    {
      paramRPPSFIInfo = RPPSFI.createRPPSFIInfo(localFFSConnectionHolder, paramRPPSFIInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***RPPSProcessor.addRPPSFIInfo failed: " + FFSDebug.stackTrace(localException2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramRPPSFIInfo;
  }
  
  public RPPSFIInfo modRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log("RPPSFIIProcessor.modRPPSFIInfo start...", 6);
    if (paramRPPSFIInfo == null)
    {
      String str1 = "***RPPSFIInfo.modRPPSFIInfo failed: Null FI object passed";
      FFSDebug.log(str1, 0);
      paramRPPSFIInfo = new RPPSFIInfo();
      paramRPPSFIInfo.setStatusCode(16000);
      paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  is null");
      return paramRPPSFIInfo;
    }
    String str2;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Exception localException1)
    {
      str2 = "***RPPSProcessor.modRPPSFIInfo failed: Error retrieving database connection " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException1, str2);
    }
    try
    {
      paramRPPSFIInfo = RPPSFI.modRPPSFIInfo(localFFSConnectionHolder, paramRPPSFIInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***RPPSProcessor.modRPPSFIInfo failed: " + FFSDebug.stackTrace(localException2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramRPPSFIInfo;
  }
  
  public RPPSFIInfo getRPPSFIInfoByFIRPPSId(String paramString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    RPPSFIInfo localRPPSFIInfo = null;
    FFSDebug.log("RPPSProcessor.getRPPSFIInfo start...", 6);
    if (paramString == null)
    {
      String str1 = "***RPPSProcessor.getRPPSFIInfoByFIRPPSId failed: fiRPPSId is null";
      FFSDebug.log(str1, 0);
      localRPPSFIInfo = new RPPSFIInfo();
      localRPPSFIInfo.setStatusCode(16000);
      localRPPSFIInfo.setStatusMsg("FIRPPSId  is null");
      return localRPPSFIInfo;
    }
    String str2;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Exception localException1)
    {
      str2 = "***RPPSProcessor.getRPPSFIInfoByFIRPPSId failed: Error retrieving database connection " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException1, str2);
    }
    try
    {
      localRPPSFIInfo = RPPSFI.getRPPSFIInfoByFIRPPSId(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***RPPSProcessor.getRPPSFIInfo failed:" + localException2.toString() + FFSDebug.stackTrace(localException2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localRPPSFIInfo;
  }
  
  public RPPSFIInfo getRPPSFIInfoByFIId(String paramString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    RPPSFIInfo localRPPSFIInfo = null;
    FFSDebug.log("RPPSProcessor.getRPPSFIInfoByFIId start...", 6);
    if (paramString == null)
    {
      String str1 = "***RPPSProcessor.getRPPSFIInfoByFIId failed: fiId is null";
      FFSDebug.log(str1, 0);
      localRPPSFIInfo = new RPPSFIInfo();
      localRPPSFIInfo.setStatusCode(16000);
      localRPPSFIInfo.setStatusMsg("FIId  is null");
      return localRPPSFIInfo;
    }
    String str2;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Exception localException1)
    {
      str2 = "***RPPSProcessor.getRPPSFIInfoByFIId failed: Error retrieving database connection " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException1, str2);
    }
    try
    {
      localRPPSFIInfo = RPPSFI.getRPPSFIInfoByFIId(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***RPPSProcessor.getRPPSFIInfo failed:" + localException2.toString() + FFSDebug.stackTrace(localException2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localRPPSFIInfo;
  }
  
  public RPPSFIInfo canRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log("RPPSProcessor.canRPPSFIInfo start...", 6);
    if (paramRPPSFIInfo == null)
    {
      String str1 = "***RPPSFIInfo.canRPPSFIInfo failed: Null FI object passed";
      FFSDebug.log(str1, 0);
      paramRPPSFIInfo = new RPPSFIInfo();
      paramRPPSFIInfo.setStatusCode(16000);
      paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  is null");
      return paramRPPSFIInfo;
    }
    String str2;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Exception localException1)
    {
      str2 = "***RPPSProcessor.canRPPSFIInfo failed: Error retrieving database connection " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException1, str2);
    }
    try
    {
      paramRPPSFIInfo = RPPSFI.canRPPSFIInfo(localFFSConnectionHolder, paramRPPSFIInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***RPPSProcessor.canRPPSFIInfo failed:" + localException2.toString() + FFSDebug.stackTrace(localException2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramRPPSFIInfo;
  }
  
  public RPPSFIInfo activateRPPSFI(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log("RPPSProcessor.activateRPPSFI start...", 6);
    if (paramRPPSFIInfo == null)
    {
      String str1 = "***RPPSFIInfo.activateRPPSFI failed: Null FI object passed";
      FFSDebug.log(str1, 0);
      paramRPPSFIInfo = new RPPSFIInfo();
      paramRPPSFIInfo.setStatusCode(16000);
      paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  is null");
      return paramRPPSFIInfo;
    }
    String str2;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Exception localException1)
    {
      str2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException1, "Error retrieving database connection");
    }
    try
    {
      paramRPPSFIInfo = RPPSFI.activateRPPSFIInfo(localFFSConnectionHolder, paramRPPSFIInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "*** RPPSProcessor.activateRPPSFI failed: ";
      String str3 = FFSDebug.stackTrace(localException2);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("RPPSProcessor.activateRPPSFI: end", 6);
    return paramRPPSFIInfo;
  }
  
  public RPPSBillerInfo[] getRPPSBillerInfoByFIRPPSId(String paramString)
    throws FFSException
  {
    String str1 = "RPPSProcessor.getRPPSBillerInfoByFIRPPSId: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    RPPSBillerInfo localRPPSBillerInfo = new RPPSBillerInfo();
    RPPSBillerInfo[] arrayOfRPPSBillerInfo = null;
    FFSDebug.log(str1 + "start...", 6);
    if (paramString == null)
    {
      String str2 = "***" + str1 + "failed: " + "FIRPPSId is null";
      FFSDebug.log(str2, 0);
      localRPPSBillerInfo.setStatusCode(16000);
      localRPPSBillerInfo.setStatusMsg("FIRPPSId  is null");
      arrayOfRPPSBillerInfo = new RPPSBillerInfo[1];
      arrayOfRPPSBillerInfo[0] = localRPPSBillerInfo;
      return arrayOfRPPSBillerInfo;
    }
    String str3;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Exception localException1)
    {
      str3 = str1 + "failed: " + "Error retrieving database connection " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str3);
    }
    try
    {
      arrayOfRPPSBillerInfo = RPPSBiller.getRPPSBillerInfoByFIRPPSId(localFFSConnectionHolder, paramString);
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      str3 = str1 + "failed: " + FFSDebug.stackTrace(localException2);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException2, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfRPPSBillerInfo;
  }
  
  public RPPSBillerInfo getRPPSBillerInfoByFIAndBillerRPPSId(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "RPPSProcessor.getRPPSBillerInfoByFIAndBillerRPPSId: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    RPPSBillerInfo localRPPSBillerInfo = new RPPSBillerInfo();
    FFSDebug.log(str1 + "start...", 6);
    if (paramString2 == null)
    {
      String str2 = "***" + str1 + "failed: " + "BillerRPPSId is null";
      FFSDebug.log(str2, 0);
      localRPPSBillerInfo.setStatusCode(16000);
      localRPPSBillerInfo.setStatusMsg("BillerRPPSId  is null");
      return localRPPSBillerInfo;
    }
    String str3;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Exception localException1)
    {
      str3 = str1 + "failed: " + "Error retrieving database connection " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str3);
    }
    try
    {
      localRPPSBillerInfo = RPPSBiller.getRPPSBillerInfoByFIAndBillerRPPSId(localFFSConnectionHolder, paramString1, paramString2);
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      str3 = str1 + "failed: " + FFSDebug.stackTrace(localException2);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException2, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localRPPSBillerInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.RPPSProcessor
 * JD-Core Version:    0.7.0.1
 */