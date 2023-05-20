package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Businesses;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.AccountService;
import com.ffusion.services.AccountService6;
import com.ffusion.services.AccountService7;
import com.ffusion.services.SignOn3;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

public class AccountHandler
  extends Initialize
{
  private static final String a6S = "Accounts";
  private static AccountService6 a6T = null;
  
  public static void initialize(HashMap paramHashMap)
  {
    String str = "AccountHandler.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Accounts", str, 20107);
      a6T = (AccountService6)HandlerUtil.instantiateService(localHashMap, str, 20107);
      a6T.initialize();
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "Accounts.initialize:" + localException.toString());
    }
  }
  
  public static Object getService()
  {
    return a6T;
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    if (paramHashMap != null)
    {
      localObject = paramHashMap.get("SERVICE");
      if ((localObject != null) && ((localObject instanceof AccountService)))
      {
        debug("com.ffusion.csil.handlers.Accounts.signOn");
        if (paramSecureUser == null) {
          paramSecureUser = new SecureUser();
        }
        AccountService localAccountService = (AccountService)localObject;
        if ((localAccountService instanceof SignOn3))
        {
          boolean bool = ((SignOn3)localAccountService).signOn(paramSecureUser, paramString1, paramString2);
          if (!bool) {
            throwing(-1009);
          }
        }
        else
        {
          int i = localAccountService.signOn(paramString1, paramString2);
          if (i != 0) {
            throwing(-1009, i);
          }
        }
      }
    }
    return paramSecureUser;
  }
  
  public static SecureUser changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    if (paramHashMap != null)
    {
      localObject = paramHashMap.get("SERVICE");
      if ((localObject != null) && ((localObject instanceof AccountService)))
      {
        debug("com.ffusion.csil.handlers.Accounts.changePIN");
        AccountService localAccountService = (AccountService)localObject;
        int i = 0;
        i = localAccountService.changePIN(paramString1, paramString2);
        if (i != 0) {
          throwing(-1009, i);
        }
        paramSecureUser.setPassword(paramString2);
      }
    }
    return paramSecureUser;
  }
  
  public static Account getAccountAddress(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountsHandler.getAccountAddress";
    Account localAccount = null;
    try
    {
      localAccount = a6T.getAccountAddress(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localAccount;
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountsHandler.getAccounts";
    Object localObject = null;
    Accounts localAccounts = null;
    if (paramHashMap != null) {
      localObject = paramHashMap.get("SERVICE");
    }
    if (localObject == null)
    {
      debug("com.ffusion.csil.handlers.AccountsHandler.getAccounts");
      debug("No service in extra hashmap, calling com.ffusion.services.AccountService3 getAccounts()");
      try
      {
        localAccounts = a6T.getAccounts(paramSecureUser, paramHashMap);
      }
      catch (ProfileException localProfileException)
      {
        CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
        DebugLog.throwing(str, localCSILException);
        throw localCSILException;
      }
    }
    else
    {
      debug("com.ffusion.csil.handlers.Accounts.getAccounts");
      if ((localObject instanceof AccountService))
      {
        localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
        AccountService localAccountService = (AccountService)localObject;
        if (localAccounts == null)
        {
          debug("Missing required parameter: extra.'ACCOUNTS'");
          throwing(-1009, 18004);
        }
        int i = 0;
        localAccounts.setSecureUser(paramSecureUser);
        i = localAccountService.getAccounts(localAccounts);
        if (i != 0) {
          throwing(-1009, i);
        }
      }
      else
      {
        debug("extra.'SERVICE' is not an instance of com.ffusion.services.AccountService");
        throwing(-1009, -1010);
      }
    }
    return localAccounts;
  }
  
  public static Accounts searchAccounts(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountsHandler.searchAccounts";
    Accounts localAccounts = null;
    try
    {
      localAccounts = a6T.searchAccounts(paramSecureUser, paramAccount, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localAccounts;
  }
  
  public static ArrayList getRestrictedCalculations(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountsHandler.getRestrictedCalculations";
    ArrayList localArrayList = null;
    try
    {
      localArrayList = a6T.getRestrictedCalculations(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localArrayList;
  }
  
  public static void saveRestrictedCalculations(SecureUser paramSecureUser, Account paramAccount, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountsHandler.saveRestrictedCalculations";
    try
    {
      a6T.saveRestrictedCalculations(paramSecureUser, paramAccount, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = null;
    String str2 = null;
    String str3 = "AccountHandler.addAccount";
    try
    {
      if (paramAccount != null)
      {
        str1 = BlockedAccounts.getStrippedAccountNumber(paramAccount.getNumber(), paramHashMap);
        str2 = paramAccount.getStrippedAccountNumber();
        if (str2 == null)
        {
          paramAccount.setStrippedAccountNumber(str1);
        }
        else if (!str2.equals(str1))
        {
          CSILException localCSILException1 = new CSILException(str3, 50010);
          DebugLog.throwing(str3, localCSILException1);
          throw localCSILException1;
        }
      }
      return a6T.addAccount(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException2 = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str3, localCSILException2);
      throw localCSILException2;
    }
  }
  
  public static Account modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramAccount != null) {
        paramAccount.setStrippedAccountNumber(BlockedAccounts.getStrippedAccountNumber(paramAccount.getNumber(), paramHashMap));
      }
      a6T.modifyAccount(paramSecureUser, paramAccount, paramHashMap);
      return paramAccount;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing("AccountHandler.modifyAccount", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Account modifyAccountById(SecureUser paramSecureUser, int paramInt, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramAccount != null) {
        paramAccount.setStrippedAccountNumber(BlockedAccounts.getStrippedAccountNumber(paramAccount.getNumber(), paramHashMap));
      }
      a6T.modifyAccountById(paramSecureUser, paramInt, paramAccount, paramHashMap);
      return paramAccount;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing("AccountHandler.modifyAccountById", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Account deleteAccount(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramAccount != null) {
        paramAccount.setStrippedAccountNumber(BlockedAccounts.getStrippedAccountNumber(paramAccount.getNumber(), paramHashMap));
      }
      a6T.deleteAccount(paramSecureUser, paramAccount, paramInt, paramHashMap);
      return paramAccount;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing("AccountHandler.deleteAccount", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Accounts getAccountsById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountHandler.getAccountsById";
    try
    {
      return a6T.getAccountsById(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Accounts getAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountHandler.getAccountsByBusinessEmployee";
    try
    {
      return a6T.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addAccounts(SecureUser paramSecureUser, Accounts paramAccounts, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    Iterator localIterator = null;
    String str1 = null;
    String str2 = null;
    String str3 = "AccountHandler.addAccounts";
    try
    {
      if ((paramAccounts != null) && (!paramAccounts.isEmpty()))
      {
        localIterator = paramAccounts.iterator();
        while (localIterator.hasNext())
        {
          localAccount = (Account)localIterator.next();
          if (localAccount != null)
          {
            str1 = BlockedAccounts.getStrippedAccountNumber(localAccount.getNumber(), paramHashMap);
            str2 = localAccount.getStrippedAccountNumber();
            if (str2 == null)
            {
              localAccount.setStrippedAccountNumber(str1);
            }
            else if (!str2.equals(str1))
            {
              CSILException localCSILException1 = new CSILException(str3, 50010);
              DebugLog.throwing(str3, localCSILException1);
              throw localCSILException1;
            }
          }
        }
      }
      a6T.addAccounts(paramSecureUser, paramAccounts, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException2 = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str3, localCSILException2);
      throw localCSILException2;
    }
  }
  
  public static AccountService6 getAccountService()
  {
    return a6T;
  }
  
  public static Accounts mergeAccounts(SecureUser paramSecureUser, Accounts paramAccounts, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    Iterator localIterator = null;
    String str1 = "AccountHandler.mergeAccounts";
    String str2 = null;
    String str3 = null;
    try
    {
      if ((paramAccounts != null) && (!paramAccounts.isEmpty()))
      {
        localIterator = paramAccounts.iterator();
        while (localIterator.hasNext())
        {
          localAccount = (Account)localIterator.next();
          if (localAccount != null)
          {
            str2 = BlockedAccounts.getStrippedAccountNumber(localAccount.getNumber(), paramHashMap);
            str3 = localAccount.getStrippedAccountNumber();
            if (str3 == null)
            {
              localAccount.setStrippedAccountNumber(str2);
            }
            else if (!str3.equals(str2))
            {
              CSILException localCSILException1 = new CSILException(str1, 50010);
              DebugLog.throwing(str1, localCSILException1);
              throw localCSILException1;
            }
          }
        }
      }
      return a6T.mergeAccounts(paramSecureUser, paramAccounts, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException2 = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str1, localCSILException2);
      throw localCSILException2;
    }
  }
  
  public static Businesses getBusinessesForAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountHandler.getBusinessesForAccount";
    try
    {
      return a6T.getBusinessesForAccount(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void fillAccountCollection(Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountHandler.fillAccountCollection";
    try
    {
      ((AccountService7)a6T).fillAccountCollection(paramAccounts, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.AccountHandler
 * JD-Core Version:    0.7.0.1
 */