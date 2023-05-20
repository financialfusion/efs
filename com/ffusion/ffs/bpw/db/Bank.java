package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class Bank
  implements FFSConst, DBConsts, BPWResource
{
  public static void create(FFSConnectionHolder paramFFSConnectionHolder, BankInfo paramBankInfo)
    throws FFSException
  {
    FFSDebug.log("Bank.create: start, BankID=" + paramBankInfo.bankID, 6);
    String str1 = "INSERT INTO BPW_Bank (BankID,SiteName,SponsorID,DebitAcctRTN,DebitAcctNumber,DebitGLAcct,DollarLimit, Submitdate ) VALUES (?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramBankInfo.bankID, paramBankInfo.siteName, paramBankInfo.sponsorID, paramBankInfo.debitAcctRTN, paramBankInfo.debitAcctNumber, paramBankInfo.debitGLAcct, new Float(paramBankInfo.dollarLimit), paramBankInfo.submitDate == null ? paramBankInfo.submitDate : FFSUtil.getDateString() };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** Bank.create: failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("Bank.create: done", 6);
  }
  
  public static BankInfo get(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("Bank.get: start, BankID=" + paramString, 6);
    String str = "SELECT SiteName,SponsorID,DebitAcctRTN,DebitAcctNumber,DebitGLAcct,DollarLimit, Submitdate FROM BPW_Bank WHERE BankID=?";
    Object[] arrayOfObject = { paramString };
    BankInfo localBankInfo = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localBankInfo = new BankInfo();
        localBankInfo.bankID = paramString;
        localBankInfo.siteName = localFFSResultSet.getColumnString(1);
        localBankInfo.sponsorID = localFFSResultSet.getColumnString(2);
        localBankInfo.debitAcctRTN = localFFSResultSet.getColumnString(3);
        localBankInfo.debitAcctNumber = localFFSResultSet.getColumnString(4);
        localBankInfo.debitGLAcct = localFFSResultSet.getColumnString(5);
        localBankInfo.dollarLimit = localFFSResultSet.getColumnFloat(6);
        localBankInfo.submitDate = localFFSResultSet.getColumnString(7);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Bank.get failed:" + localException1.toString());
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
        FFSDebug.log("*** Bank.get failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Bank.get: done", 6);
    return localBankInfo;
  }
  
  public static BankInfo[] getAllBankInfo()
    throws FFSException
  {
    FFSDebug.log("Bank.getAllBankInfo() start...", 6);
    FFSConnection localFFSConnection = DBUtil.getConnection();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = localFFSConnection;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList(10);
    BankInfo localBankInfo = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, "SELECT BankID, SiteName,SponsorID,DebitAcctRTN,DebitAcctNumber,DebitGLAcct,DollarLimit, Submitdate FROM BPW_Bank", null);
      while (localFFSResultSet.getNextRow())
      {
        localBankInfo = new BankInfo();
        localBankInfo.bankID = localFFSResultSet.getColumnString(1);
        localBankInfo.siteName = localFFSResultSet.getColumnString(2);
        localBankInfo.sponsorID = localFFSResultSet.getColumnString(3);
        localBankInfo.debitAcctRTN = localFFSResultSet.getColumnString(4);
        localBankInfo.debitAcctNumber = localFFSResultSet.getColumnString(5);
        localBankInfo.debitGLAcct = localFFSResultSet.getColumnString(6);
        localBankInfo.dollarLimit = localFFSResultSet.getColumnFloat(7);
        localBankInfo.submitDate = localFFSResultSet.getColumnString(8);
        localArrayList.add(localBankInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Bank.getAllBanks failed:" + localException1.toString());
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
        FFSDebug.log("*** Bank.get failed:" + localException2.toString());
      }
      localFFSConnectionHolder.conn = null;
      if (localFFSConnection != null) {
        DBUtil.freeConnection(localFFSConnection);
      }
    }
    FFSDebug.log("Bank.getAllBankInfo() end.", 6);
    return (BankInfo[])localArrayList.toArray(new BankInfo[localArrayList.size()]);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException, BPWException
  {
    FFSDebug.log("Bank.delete: start, BankID=" + paramString, 6);
    String str1 = "DELETE FROM BPW_Bank WHERE BankID=?";
    Object[] arrayOfObject = { paramString };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** Bank.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("Bank.delete: done", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Bank
 * JD-Core Version:    0.7.0.1
 */