package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.BankEmployeeAdmin7;
import com.ffusion.services.BankEmployeeAdmin8;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class BankEmployeeAdmin
{
  private static final String jdField_for = "BankEmployeeAdmin";
  private static BankEmployeeAdmin7 jdField_do = null;
  private static String jdField_if;
  private static String a;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "BankEmployeeAdmin", str, 20107);
      jdField_do = (BankEmployeeAdmin7)HandlerUtil.instantiateService(localHashMap, str, 20107);
      jdField_do.initialize("");
      jdField_if = HandlerUtil.getGlobalEmployeeDisplaySize(paramHashMap);
      a = HandlerUtil.getGlobalEmployeeMaxResultSize(paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(20107, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_do;
  }
  
  public static String getDisplayCount()
  {
    return jdField_if;
  }
  
  public static String getMaxResultCount()
  {
    return a;
  }
  
  public static BankEmployee addBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.addBankEmployee";
    try
    {
      return jdField_do.addBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BankEmployee getBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployee";
    try
    {
      return jdField_do.getBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BankEmployee getBankEmployeeById(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeeById";
    try
    {
      return jdField_do.getBankEmployeeById(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BankEmployees getBankEmployees(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployees";
    try
    {
      return jdField_do.getBankEmployees(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BankEmployees getBankEmployeeList(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeeList";
    try
    {
      return jdField_do.getBankEmployeeList(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static String getBankEmployeeCount(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeeCount";
    try
    {
      return jdField_do.getBankEmployeeCount(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BankEmployees getBankEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getBankEmployeesByIds";
    try
    {
      return jdField_do.getBankEmployeesByIds(paramSecureUser, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static boolean hasDirectReports(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.hasDirectReports";
    try
    {
      return jdField_do.hasDirectReports(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static boolean hasDirectReports(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.hasDirectReports";
    try
    {
      return jdField_do.hasDirectReports(paramSecureUser, paramInt1, paramInt2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BankEmployee modifyBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.modifyBankEmployee";
    try
    {
      jdField_do.modifyBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
      return paramBankEmployee;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localProfileException.code), localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BankEmployee deleteBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.deleteBankEmployee";
    try
    {
      jdField_do.deleteBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
      return paramBankEmployee;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.addHistory";
    try
    {
      jdField_do.addHistory(paramSecureUser, paramHistories, paramHashMap);
      return paramHistories;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getHistory";
    try
    {
      return jdField_do.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getHistory";
    try
    {
      return jdField_do.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BankEmployee redistributeWorkloads(SecureUser paramSecureUser, BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.redistributeWorkloads";
    return null;
  }
  
  public static BankEmployee signonBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.signonBankEmployee";
    try
    {
      return jdField_do.signonBankEmployee(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      if (localProfileException.code != 3810) {
        DebugLog.throwing(str, localProfileException);
      }
      throw localCSILException;
    }
  }
  
  public static boolean getInfoForAuditing(BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.getInfoForAuditing";
    try
    {
      return ((BankEmployeeAdmin8)jdField_do).getInfoForAuditing(paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void resetBankEmployeePassword(SecureUser paramSecureUser, BankEmployee paramBankEmployee, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.resetBankEmployeePassword";
    try
    {
      ((BankEmployeeAdmin8)jdField_do).resetBankEmployeePassword(paramSecureUser, paramBankEmployee, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyBankEmployeePasswordStatus(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.modifyBankEmployeePasswordStatus";
    try
    {
      ((BankEmployeeAdmin8)jdField_do).modifyBankEmployeePasswordStatus(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyBankEmployeePassword(SecureUser paramSecureUser, BankEmployee paramBankEmployee, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankEmployeeAdmin.modifyBankEmployeePassword";
    try
    {
      ((BankEmployeeAdmin8)jdField_do).modifyBankEmployeePassword(paramSecureUser, paramBankEmployee, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static int a(int paramInt)
  {
    switch (paramInt)
    {
    case 2: 
      return 16002;
    case 3505: 
      return 100;
    case 3: 
      return 3004;
    case 4: 
      return 3003;
    case 6: 
      return 19001;
    case 7: 
      return 3005;
    case 8: 
      return 19002;
    case 9: 
      return 3007;
    case 10: 
      return 3006;
    case 11: 
      return 3008;
    case 3644: 
      return 3010;
    case 3750: 
      return 3011;
    case 13: 
      return 19003;
    case 12: 
      return 3009;
    case 14: 
      return 3016;
    case 16: 
      return 3019;
    case 17: 
      return 3020;
    case 18: 
      return 3021;
    case 19: 
      return 3022;
    case 22: 
      return 90006;
    case 15: 
      return 5103;
    }
    return -1009;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.BankEmployeeAdmin
 * JD-Core Version:    0.7.0.1
 */