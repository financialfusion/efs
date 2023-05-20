package com.ffusion.csil.handlers;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.services.BankLookupService;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;

public class BankLookupHandler
  extends Initialize
{
  private static BankLookupService a6U = null;
  private static final String a6V = "BankLookup";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankLookupHandler.initialize";
    if (a6U == null)
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "BankLookup", str, 20107);
      a6U = (BankLookupService)HandlerUtil.instantiateService(localHashMap, str, 20107);
      a6U.initialize();
    }
  }
  
  public static Object getService()
  {
    return a6U;
  }
  
  public static FinancialInstitutions getFinancialInstitutions(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankLookupHandler.getFinancialInstitutions";
    try
    {
      return a6U.getFinancialInstitutions(paramSecureUser, paramFinancialInstitution, paramInt, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static FinancialInstitution updateFinancialInstitution(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankLookupHandler.updateFinancialInstitutions";
    try
    {
      return a6U.updateFinancialInstitution(paramSecureUser, paramFinancialInstitution, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getCountries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankLookupHandler.getCountries";
    try
    {
      return a6U.getCountries(paramSecureUser, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.BankLookupHandler
 * JD-Core Version:    0.7.0.1
 */