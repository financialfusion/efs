package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.WireBank;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class WireBankProcessor
  implements DBConsts, FFSConst, OFXConsts
{
  public BPWBankInfo[] addWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    FFSDebug.log("WireBankProcessor.addWireBank start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWBankInfo[] arrayOfBPWBankInfo1 = null;
    int i = 0;
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***WireBankProcessor.addWirePayee failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      i = paramArrayOfBPWBankInfo.length;
      arrayOfBPWBankInfo1 = new BPWBankInfo[i];
      for (int j = 0; j < i; j++)
      {
        arrayOfBPWBankInfo1[j] = WireBank.addWireBank(localFFSConnectionHolder, paramArrayOfBPWBankInfo[j]);
        localFFSConnectionHolder.conn.commit();
      }
      BPWBankInfo[] arrayOfBPWBankInfo2 = arrayOfBPWBankInfo1;
      return arrayOfBPWBankInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***WireBankProcessor.addWirePayee failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWBankInfo[] modWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.modWireBank: ";
    FFSDebug.log("***", str1, "start ...", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWBankInfo[] arrayOfBPWBankInfo1 = null;
    int i = 0;
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***WireBankProcessor.addWirePayee failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      i = paramArrayOfBPWBankInfo.length;
      arrayOfBPWBankInfo1 = new BPWBankInfo[i];
      for (int j = 0; j < i; j++)
      {
        arrayOfBPWBankInfo1[j] = WireBank.modWireBank(localFFSConnectionHolder, paramArrayOfBPWBankInfo[j]);
        localFFSConnectionHolder.conn.commit();
      }
      BPWBankInfo[] arrayOfBPWBankInfo2 = arrayOfBPWBankInfo1;
      return arrayOfBPWBankInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***WireBankProcessor.addWirePayee failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWBankInfo[] delWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.delWireBank: ";
    FFSDebug.log("***", str1, "start ...", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWBankInfo[] arrayOfBPWBankInfo1 = null;
    int i = 0;
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***WireBankProcessor.addWirePayee failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      i = paramArrayOfBPWBankInfo.length;
      arrayOfBPWBankInfo1 = new BPWBankInfo[i];
      for (int j = 0; j < i; j++)
      {
        arrayOfBPWBankInfo1[j] = WireBank.delWireBank(localFFSConnectionHolder, paramArrayOfBPWBankInfo[j]);
        localFFSConnectionHolder.conn.commit();
      }
      BPWBankInfo[] arrayOfBPWBankInfo2 = arrayOfBPWBankInfo1;
      return arrayOfBPWBankInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***WireBankProcessor.addWirePayee failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWBankInfo[] getWireBanks(String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    FFSDebug.log("WireBankProcessor.getWireBanks start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    BPWBankInfo[] arrayOfBPWBankInfo1 = null;
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***WireBankProcessor.getWireBanks failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      arrayOfBPWBankInfo1 = WireBank.getWireBanks(localFFSConnectionHolder, paramString1, paramString2, paramString3, paramString4);
      localFFSConnectionHolder.conn.commit();
      BPWBankInfo[] arrayOfBPWBankInfo2 = arrayOfBPWBankInfo1;
      return arrayOfBPWBankInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***WireBankProcessor.getWireBanks failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWBankInfo[] getWireBanksByRTN(String paramString)
    throws FFSException
  {
    FFSDebug.log("WireBankProcessor.getWireBanksByRTN start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    BPWBankInfo[] arrayOfBPWBankInfo1 = null;
    if (paramString == null)
    {
      String str1 = "***WireBankProcessor.getWireBanksByRTN failed: routing number is null";
      FFSDebug.log(str1, 0);
      arrayOfBPWBankInfo1 = new BPWBankInfo[1];
      arrayOfBPWBankInfo1[0] = new BPWBankInfo();
      arrayOfBPWBankInfo1[0].setStatusCode(16000);
      arrayOfBPWBankInfo1[0].setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "rtn " }, "WIRE_MESSAGE"));
      return arrayOfBPWBankInfo1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***WireBankProcessor.getWireBanksByRTN failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      arrayOfBPWBankInfo1 = WireBank.getWireBanksByRTN(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      BPWBankInfo[] arrayOfBPWBankInfo2 = arrayOfBPWBankInfo1;
      return arrayOfBPWBankInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***WireBankProcessor.getWireBanksByRTN failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWBankInfo getWireBanksByID(String paramString)
    throws FFSException
  {
    FFSDebug.log("WireBankProcessor.getWireBanksByID start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    BPWBankInfo localBPWBankInfo1 = null;
    if (paramString == null)
    {
      String str1 = "***WireBankProcessor.getWireBanksByID failed: bankId is null";
      FFSDebug.log(str1, 0);
      localBPWBankInfo1 = new BPWBankInfo();
      localBPWBankInfo1.setStatusCode(16000);
      localBPWBankInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "bankId " }, "WIRE_MESSAGE"));
      return localBPWBankInfo1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***WireBankProcessor.getWireBanksByID failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      localBPWBankInfo1 = WireBank.getWireBanksByID(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      BPWBankInfo localBPWBankInfo2 = localBPWBankInfo1;
      return localBPWBankInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***WireBankProcessor.getWireBanksByID failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.WireBankProcessor
 * JD-Core Version:    0.7.0.1
 */