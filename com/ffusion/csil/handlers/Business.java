package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.BusinessService4;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Business
{
  private static BusinessService4 jdField_do = null;
  private static final String jdField_for = "Business";
  private static String jdField_if;
  private static String a;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Business", str, 20107);
      jdField_do = (BusinessService4)HandlerUtil.instantiateService(localHashMap, str, 20107);
      jdField_do.initialize("");
      jdField_if = HandlerUtil.getGlobalBusinessDisplaySize(paramHashMap);
      a = HandlerUtil.getGlobalBusinessMaxResultSize(paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str, localException);
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
  
  public static com.ffusion.beans.business.Business modifyBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.modifyBusiness";
    try
    {
      jdField_do.modifyBusiness(paramSecureUser, paramBusiness, paramHashMap);
      return paramBusiness;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static void addAdditionalData(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.AddAdditionalData";
    try
    {
      jdField_do.addAdditionalData(paramSecureUser, paramBusiness, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static String getAdditionalData(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.GetAdditionalData";
    try
    {
      return jdField_do.getAdditionalData(paramSecureUser, paramBusiness, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.business.Business addBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.addBusiness";
    try
    {
      return jdField_do.addBusiness(paramSecureUser, paramBusiness, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.business.Business deactivateBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.deactivateBusiness";
    try
    {
      return jdField_do.deactivateBusiness(paramSecureUser, paramBusiness, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.business.Business reactivateBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.reactivateBusiness";
    try
    {
      return jdField_do.reactivateBusiness(paramSecureUser, paramBusiness, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.business.Business purgeDeactivatedBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.purgeDeactivatedBusiness";
    try
    {
      jdField_do.purgeDeactivatedBusiness(paramSecureUser, paramBusiness, paramHashMap);
      return paramBusiness;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.business.Business getBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getBusiness";
    try
    {
      return jdField_do.getBusiness(paramSecureUser, paramBusiness, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Businesses getBusinesses(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getBusinesses";
    try
    {
      return jdField_do.getBusinesses(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.business.Business enrollBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.enrollBusiness";
    try
    {
      jdField_do.enrollBusiness(paramSecureUser, paramBusiness, paramHashMap);
      return paramBusiness;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Businesses getFilteredBusinesses(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getFilteredBusinesses";
    try
    {
      return jdField_do.getFilteredBusinesses(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static int getFilteredBusinessesCount(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getFilteredBusinessesCount";
    try
    {
      return jdField_do.getFilteredBusinessesCount(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Businesses getBusinessesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getBusinessesByIds";
    try
    {
      return jdField_do.getBusinessesByIds(paramSecureUser, paramArrayList, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(-1009, localException);
      DebugLog.throwing(str, localException);
      throw localCSILException;
    }
  }
  
  public static void addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.addHistory";
    try
    {
      jdField_do.addHistory(paramSecureUser, paramHistories, paramHashMap);
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
    String str = "com.ffusion.csil.handlers.Business.getHistory";
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
  
  public static User getBusinessInfoFromBackend(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getBusinessInfoFromBackend";
    try
    {
      return jdField_do.getBusinessInfoFromBackend(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      int i = 0;
      switch (localProfileException.code)
      {
      case 8: 
        i = 4021;
        break;
      case 6: 
        i = 4020;
        break;
      default: 
        i = 16002;
      }
      CSILException localCSILException = new CSILException(-1009, i, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getBankIdsByServicesPackage";
    try
    {
      return jdField_do.getBankIdsByServicesPackage(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getTransactionGroups(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getTransactionGroups";
    try
    {
      return jdField_do.getTransactionGroups(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static String getTypeCodesForGroup(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.getTypeCodesForGroup";
    try
    {
      return jdField_do.getTypeCodesForGroup(paramSecureUser, paramInt, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static void addTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.addTransactionGroup";
    try
    {
      jdField_do.addTransactionGroup(paramSecureUser, paramInt, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static void deleteTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.deleteTransactionGroup";
    try
    {
      jdField_do.deleteTransactionGroup(paramSecureUser, paramInt, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static void modifyTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.modifyTransactionGroup";
    try
    {
      jdField_do.modifyTransactionGroup(paramSecureUser, paramInt, paramString1, paramString2, paramString3, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static boolean uniqueCustId(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Business.uniqueCustId";
    boolean bool = false;
    try
    {
      bool = jdField_do.uniqueCustId(paramSecureUser, paramBusiness, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Business
 * JD-Core Version:    0.7.0.1
 */