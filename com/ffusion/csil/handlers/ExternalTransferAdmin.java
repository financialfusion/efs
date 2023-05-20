package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.services.ExternalTransferAdmin2;
import com.ffusion.services.ExternalTransferAdminException;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class ExternalTransferAdmin
  extends Initialize
{
  private static final String a6H = "ExternalTransferAdmin";
  private static String a6D;
  private static com.ffusion.services.ExternalTransferAdmin a6E = null;
  private static com.ffusion.services.ExternalTransferAdmin a6G = null;
  private static boolean a6F;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.initialize";
    debug("com.ffusion.handlers.ExternalTransferAdmin.initialize");
    a6D = HandlerUtil.getGlobalPageSize(paramHashMap);
    a6F = HandlerUtil.useCompatibilityMode(paramHashMap);
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "ExternalTransferAdmin", str1, 20107);
    a6E = (com.ffusion.services.ExternalTransferAdmin)HandlerUtil.instantiateService(localHashMap, str1, 20107);
    String str2 = null;
    if (localHashMap.get("INIT_URL") != null) {
      str2 = (String)localHashMap.get("INIT_URL");
    }
    try
    {
      a6E.initialize(str2);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return null;
  }
  
  public static Accounts getAccountsForExtTransfer(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Accounts localAccounts = null;
    try
    {
      localAccounts = a6E.getAccountsForExtTransfer(paramSecureUser, paramInt, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code);
    }
    return localAccounts;
  }
  
  public static ExtTransferCompanies getExtTransferCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    ExtTransferCompanies localExtTransferCompanies = null;
    try
    {
      localExtTransferCompanies = a6E.getExtTransferCompanies(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code);
    }
    return localExtTransferCompanies;
  }
  
  public static ExtTransferCompany addExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      paramExtTransferCompany = a6E.addExtTransferCompany(paramSecureUser, paramExtTransferCompany, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code, localCSILException.where, localCSILException.why);
    }
    return paramExtTransferCompany;
  }
  
  public static void modifyExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany1, ExtTransferCompany paramExtTransferCompany2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6E.modifyExtTransferCompany(paramSecureUser, paramExtTransferCompany1, paramExtTransferCompany2, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code, localCSILException.where, localCSILException.why);
    }
  }
  
  public static void deleteExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6E.deleteExtTransferCompany(paramSecureUser, paramExtTransferCompany, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code, localCSILException.where, localCSILException.why);
    }
  }
  
  public static void addExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6E.addExtTransferAccount(paramSecureUser, paramExtTransferAccount, paramExtTransferCompany, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code, localCSILException.where, localCSILException.why);
    }
  }
  
  public static ExtTransferAccounts getExtTransferAccounts(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    ExtTransferAccounts localExtTransferAccounts = null;
    try
    {
      localExtTransferAccounts = a6E.getExtTransferAccounts(paramSecureUser, paramExtTransferAccount, paramExtTransferCompany, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code);
    }
    return localExtTransferAccounts;
  }
  
  public static ExtTransferAccounts getExtTransferAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    ExtTransferAccounts localExtTransferAccounts = null;
    try
    {
      localExtTransferAccounts = a6E.getExtTransferAccounts(paramSecureUser, paramHashMap);
    }
    catch (ExternalTransferAdminException localExternalTransferAdminException)
    {
      throw new CSILException(-1009, localExternalTransferAdminException.getErrorCode());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new CSILException(-1009, localException);
    }
    return localExtTransferAccounts;
  }
  
  public static void deleteExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6E.deleteExtTransferAccount(paramSecureUser, paramExtTransferAccount, paramExtTransferCompany, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code, localCSILException.where, localCSILException.why);
    }
  }
  
  public static void modifyExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6E.modifyExtTransferAccount(paramSecureUser, paramExtTransferAccount, paramExtTransferCompany, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code, localCSILException.where, localCSILException.why);
    }
  }
  
  public static ExtTransferAccount verifyExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, int[] paramArrayOfInt, HashMap paramHashMap)
    throws CSILException
  {
    ExtTransferAccount localExtTransferAccount = null;
    try
    {
      if ((a6E instanceof ExternalTransferAdmin2)) {
        localExtTransferAccount = ((ExternalTransferAdmin2)a6E).verifyExtTransferAccount(paramSecureUser, paramExtTransferAccount, paramArrayOfInt, paramHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code, localCSILException.where, localCSILException.why);
    }
    return localExtTransferAccount;
  }
  
  public static ExtTransferAccount depositAmountsForVerify(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    ExtTransferAccount localExtTransferAccount = null;
    try
    {
      if ((a6E instanceof ExternalTransferAdmin2)) {
        localExtTransferAccount = ((ExternalTransferAdmin2)a6E).depositAmountsForVerify(paramSecureUser, paramExtTransferAccount, paramAffiliateBank, paramHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code, localCSILException.where, localCSILException.why);
    }
    return localExtTransferAccount;
  }
  
  public static String getNumberOfVerifyDeposits()
    throws CSILException
  {
    String str = null;
    try
    {
      if ((a6E instanceof ExternalTransferAdmin2)) {
        str = ((ExternalTransferAdmin2)a6E).getNumberOfVerifyDeposits();
      }
    }
    catch (CSILException localCSILException)
    {
      throw new CSILException(-1009, localCSILException.code);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.ExternalTransferAdmin
 * JD-Core Version:    0.7.0.1
 */