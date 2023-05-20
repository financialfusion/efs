package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.services.IBlockedAccounts;
import com.ffusion.services.blockedaccts.BLKException;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchCriteria;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResults;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class BlockedAccounts
  extends Initialize
{
  private static final String a7l = "Blocked Accounts";
  private static IBlockedAccounts a7m;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BlockedAccounts.initialize";
    debug(str);
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Blocked Accounts", str, 50004);
    a7m = (IBlockedAccounts)HandlerUtil.instantiateService(localHashMap, str, 50004);
    try
    {
      a7m.initialize(paramHashMap);
    }
    catch (Exception localException)
    {
      throw new CSILException(str, jdMethod_new(localException), localException);
    }
  }
  
  public static void addBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BlockedAccounts.addBlockedAccount";
    try
    {
      if (a7m == null) {
        throwing(-1009, 50000);
      }
      a7m.addBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
    }
    catch (BLKException localBLKException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localBLKException), localBLKException.getCode(), localBLKException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BlockedAccounts.deleteBlockedAccount";
    try
    {
      if (a7m == null) {
        throwing(-1009, 50000);
      }
      a7m.deleteBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
    }
    catch (BLKException localBLKException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localBLKException), localBLKException.getCode(), localBLKException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount1, BlockedAccount paramBlockedAccount2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BlockedAccounts.modifyBlockedAccount";
    try
    {
      if (a7m == null) {
        throwing(-1009, 50000);
      }
      a7m.modifyBlockedAccount(paramSecureUser, paramBlockedAccount1, paramBlockedAccount2, paramHashMap);
    }
    catch (BLKException localBLKException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localBLKException), localBLKException.getCode(), localBLKException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static int getNumBlockedAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BlockedAccounts.getNumBlockedAccounts";
    try
    {
      if (a7m == null) {
        throwing(-1009, 50000);
      }
      return a7m.getNumBlockedAccounts(paramSecureUser, paramHashMap);
    }
    catch (BLKException localBLKException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localBLKException), localBLKException.getCode(), localBLKException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static BlockedAccountSearchResults getBlockedAccounts(SecureUser paramSecureUser, BlockedAccountSearchCriteria paramBlockedAccountSearchCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BlockedAccounts.getBlockedAccounts";
    try
    {
      if (a7m == null) {
        throwing(-1009, 50000);
      }
      return a7m.getBlockedAccounts(paramSecureUser, paramBlockedAccountSearchCriteria, paramHashMap);
    }
    catch (BLKException localBLKException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localBLKException), localBLKException.getCode(), localBLKException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean isBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BlockedAccounts.isBlockedAccount";
    try
    {
      if (a7m == null) {
        throwing(-1009, 50000);
      }
      return a7m.isBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
    }
    catch (BLKException localBLKException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localBLKException), localBLKException.getCode(), localBLKException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static String getStrippedAccountNumber(String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BlockedAccounts.getStrippedAccountNumber";
    try
    {
      if (a7m == null) {
        throwing(-1009, 50000);
      }
      return a7m.getStrippedAccountNumber(paramString, paramHashMap);
    }
    catch (BLKException localBLKException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localBLKException), localBLKException.getCode(), localBLKException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static int jdMethod_new(Throwable paramThrowable)
  {
    int i = 1;
    if ((paramThrowable instanceof BLKException))
    {
      BLKException localBLKException = (BLKException)paramThrowable;
      switch (localBLKException.getCode())
      {
      case 6: 
        i = 50001;
        break;
      case 7: 
        i = 50002;
        break;
      case 8: 
        i = 50003;
        break;
      case 10: 
        i = 50008;
        break;
      case 12: 
        i = 50010;
        break;
      case 4: 
        i = 50000;
        break;
      case 1: 
      case 3: 
        i = 50004;
        break;
      case 2: 
        i = 50005;
        break;
      case 5: 
        i = 50006;
        break;
      case 9: 
        i = 50007;
        break;
      case 11: 
        i = 50009;
        break;
      default: 
        i = 1;
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.BlockedAccounts
 * JD-Core Version:    0.7.0.1
 */