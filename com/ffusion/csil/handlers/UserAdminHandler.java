package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTagValuesList;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.UserAdmin10;
import com.ffusion.services.UserAdmin9;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class UserAdminHandler
{
  private static final String jdField_int = "UserAdmin";
  private static final String jdField_do = "customTags.xml";
  private static UserAdmin9 jdField_try = null;
  private static String jdField_new;
  private static String a;
  private static String jdField_if;
  private static String jdField_for;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "UserAdmin", str, 20107);
    jdField_try = (UserAdmin9)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      jdField_try.initialize("customTags.xml");
      jdField_if = HandlerUtil.getGlobalUserDisplaySize(paramHashMap);
      jdField_for = HandlerUtil.getGlobalConsumerDisplaySize(paramHashMap);
      jdField_new = HandlerUtil.getGlobalUserMaxResultSize(paramHashMap);
      a = HandlerUtil.getGlobalConsumerMaxResultSize(paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(20107, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_try;
  }
  
  public static String getUserDisplayCount()
  {
    return jdField_if;
  }
  
  public static String getConsumerDisplayCount()
  {
    return jdField_for;
  }
  
  public static String getUserMaxResultCount()
  {
    return jdField_new;
  }
  
  public static String getConsumerMaxResultCount()
  {
    return a;
  }
  
  public static SecureUser authenticate(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.authenticate(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      if ((localCSILException.getCode() == 16002) || (localCSILException.getCode() == 22000)) {
        DebugLog.throwing("UserAdminHandler.authenticate", localCSILException);
      }
      throw localCSILException;
    }
  }
  
  public static boolean userExists(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.userExists(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.userExists", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User addUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.addUser(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.addUser", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addSecondaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      ((UserAdmin10)jdField_try).addSecondaryUser(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.addSecondaryUser", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User getUserInfoFromBackend(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UserAdminHandler.getUserInfoFromBackend";
    try
    {
      return ((UserAdmin10)jdField_try).getUserInfoFromBackend(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      int i = 0;
      switch (localProfileException.code)
      {
      case 8: 
        i = 3013;
        break;
      case 6: 
        i = 3003;
        break;
      default: 
        i = 16002;
      }
      CSILException localCSILException = new CSILException(-1009, i, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Users getSecondaryUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return ((UserAdmin10)jdField_try).getSecondaryUsers(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getSecondaryUsers", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User getPrimaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return ((UserAdmin10)jdField_try).getPrimaryUser(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getPrimaryUser", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User getUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getUser(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getUser", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User getUserById(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getUserById(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getUser", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Users getUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getUsers(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getUsers", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getUsersIDs(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getUsersIDs(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getUsers", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getUserIDsByUserNames(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getUserIDsByUserNames(paramSecureUser, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getUsersIDsByUserIDs", localCSILException);
      throw localCSILException;
    }
  }
  
  public static String getUsersCount(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getUsersCount(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getUsers", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Users getUserList(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getUsers(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getUserList", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Users getFilteredUsers(SecureUser paramSecureUser, User paramUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getFilteredUsers(paramSecureUser, paramUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getFilteredUsers", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User modifyPasswordStatus(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.modifyPasswordStatus(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.modifyPasswordStatus", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User modifyUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.modifyUser(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.modifyUser", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User modifyUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.modifyUserPassword(paramSecureUser, paramUser, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.modifyUserPassword", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.addAccount(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.addAccount", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getAccounts(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getAccounts", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Account modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.modifyAccount(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.modifyAccount", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Account deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.deleteAccount(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.deleteAccount", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addAdditionalData(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if ((paramHashMap != null) && (paramHashMap.containsKey("USER")))
      {
        SecureUser localSecureUser = (SecureUser)paramHashMap.get("USER");
        jdField_try.addAdditionalData(localSecureUser, paramString1, paramString2, paramHashMap);
      }
      else
      {
        jdField_try.addAdditionalData(paramSecureUser, paramString1, paramString2, paramHashMap);
      }
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.addAdditionalData", localCSILException);
      throw localCSILException;
    }
  }
  
  public static String getAdditionalData(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getAdditionalData(paramSecureUser, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getAdditionalData", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.addHistory(paramSecureUser, paramHistories, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.addHistory", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getHistory", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramInt1, paramInt2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getHistory", localCSILException);
      throw localCSILException;
    }
  }
  
  public static CustomTags addCustomTags(SecureUser paramSecureUser, CustomTags paramCustomTags, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.addCustomTags(paramSecureUser, paramCustomTags, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.addCustomTags", localCSILException);
      throw localCSILException;
    }
  }
  
  public static CustomTags getCustomTags(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getCustomTags(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getCustomTags", localCSILException);
      throw localCSILException;
    }
  }
  
  public static CustomTag modifyCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.modifyCustomTag(paramSecureUser, paramCustomTag, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.modifyCustomTag", localCSILException);
      throw localCSILException;
    }
  }
  
  public static CustomTag deleteCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.deleteCustomTag(paramSecureUser, paramCustomTag, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.deleteCustomTag", localCSILException);
      throw localCSILException;
    }
  }
  
  public static CustomTagValuesList getCustomTagValuesList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getCustomTagValuesList(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getCustomTagValuesList", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getExtraFields(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getExtraFields(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getExtraFields", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User deleteUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.deleteUser(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.deleteUser", localCSILException);
      throw localCSILException;
    }
  }
  
  public static BusinessEmployee addBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.addBusinessEmployee(paramSecureUser, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException;
      if ((localProfileException.code == 20) || (localProfileException.code == 21)) {
        localCSILException = (CSILException)localProfileException.childException;
      } else {
        localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      }
      DebugLog.throwing("UserAdminHandler.addBusinessEmployee", localCSILException);
      throw localCSILException;
    }
  }
  
  public static BusinessEmployee modifyBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.modifyBusinessEmployee(paramSecureUser, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException;
      if (localProfileException.code == 20) {
        localCSILException = (CSILException)localProfileException.childException;
      } else {
        localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      }
      DebugLog.throwing("UserAdminHandler.modifyBusinessEmployee", localCSILException);
      throw localCSILException;
    }
  }
  
  public static BusinessEmployees getBusinessEmployees(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getBusinessEmployees(paramSecureUser, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getBusinessEmployees", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getBusinessEmployeesIDs(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getBusinessEmployeesIDs(paramSecureUser, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getBusinessEmployees", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getBusinessEmployeesIDs(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getBusinessEmployeesIDs(paramSecureUser, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getBusinessEmployees", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Users getConsumers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getConsumers(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getConsumers", localCSILException);
      throw localCSILException;
    }
  }
  
  public static String getConsumersCount(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getConsumersCount(paramSecureUser, paramUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getConsumers", localCSILException);
      throw localCSILException;
    }
  }
  
  public static BusinessEmployee deleteBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.deleteBusinessEmployee(paramSecureUser, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.deleteBusinessEmployee", localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean businessIdExists(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.businessIdExists(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.businessIdExists", localCSILException);
      throw localCSILException;
    }
  }
  
  public static BusinessEmployees getFilteredBusinessEmployees(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getFilteredBusinessEmployees(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getFilteredBusinessEmployees", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getFilteredBusinessEmployeesIDs(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getFilteredBusinessEmployeesIDs(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getFilteredBusinessEmployees", localCSILException);
      throw localCSILException;
    }
  }
  
  public static String getFilteredBusinessEmployeesCount(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getFilteredBusinessEmployeesCount(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getFilteredBusinessEmployeesCount", localCSILException);
      throw localCSILException;
    }
  }
  
  public static BusinessEmployees getBusinessEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getBusinessEmployeesByIds(paramSecureUser, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getBusinessEmployeesByIds", localCSILException);
      throw localCSILException;
    }
  }
  
  public static User resetUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.resetUserPassword(paramSecureUser, paramUser, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.resetUserPassword", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.getBankIdsByServicesPackage";
    try
    {
      return jdField_try.getBankIdsByServicesPackage(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static BusinessEmployees getFilteredBusinessEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getFilteredBusinessEmployeesByIds(paramSecureUser, paramArrayList, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getBusinessEmployeesByIds", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Users getUsersByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_try.getUsersByIds(paramSecureUser, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing("UserAdminHandler.getUsersByIds", localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean getInfoForAuditing(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.getProfileId";
    try
    {
      return ((UserAdmin10)jdField_try).getInfoForAuditing(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static DateTime getLastExportedDateTime(SecureUser paramSecureUser, Account paramAccount, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.getLastExportedDate";
    try
    {
      return ((UserAdmin10)jdField_try).getLastExportedDateTime(paramSecureUser, paramAccount, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void setLastExportedDateTime(SecureUser paramSecureUser, Account paramAccount, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.setLastExportedDateTime";
    try
    {
      ((UserAdmin10)jdField_try).setLastExportedDateTime(paramSecureUser, paramAccount, paramString, paramDateTime, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.addContactPoint(SecureUser, ContactPoint, HashMap)";
    if (!(jdField_try instanceof UserAdmin10)) {
      throw new CSILException(str, 2, "This method is only supported by services implementing the UserAdmin10 service interface and higher.");
    }
    try
    {
      ((UserAdmin10)jdField_try).addContactPoint(paramSecureUser, paramContactPoint, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addContactPoints(SecureUser paramSecureUser, ContactPoints paramContactPoints, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.addContactPoints(SecureUser, ContactPoints, HashMap)";
    if (!(jdField_try instanceof UserAdmin10)) {
      throw new CSILException(str, 2, "This method is only supported by services implementing the UserAdmin10 service interface and higher.");
    }
    try
    {
      ((UserAdmin10)jdField_try).addContactPoints(paramSecureUser, paramContactPoints, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.modifyContactPoint(SecureUser, ContactPoint, HashMap)";
    if (!(jdField_try instanceof UserAdmin10)) {
      throw new CSILException(str, 2, "This method is only supported by services implementing the UserAdmin10 service interface and higher.");
    }
    try
    {
      ((UserAdmin10)jdField_try).modifyContactPoint(paramSecureUser, paramContactPoint, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.deleteContactPoint(SecureUser, ContactPoint,HashMap)";
    if (!(jdField_try instanceof UserAdmin10)) {
      throw new CSILException(str, 2, "This method is only supported by services implementing the UserAdmin10 service interface and higher.");
    }
    try
    {
      ((UserAdmin10)jdField_try).deleteContactPoint(paramSecureUser, paramContactPoint, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ContactPoints getContactPoints(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.getContactPoints(SecureUser, HashMap)";
    if (!(jdField_try instanceof UserAdmin10)) {
      throw new CSILException(str, 2, "This method is only supported by services implementing the UserAdmin10 service interface and higher.");
    }
    try
    {
      return ((UserAdmin10)jdField_try).getContactPoints(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ContactPoint getContactPoint(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdminHandler.getContactPoint(SecureUser, int, HashMap)";
    if (!(jdField_try instanceof UserAdmin10)) {
      throw new CSILException(str, 2, "This method is only supported by services implementing the UserAdmin10 service interface and higher.");
    }
    try
    {
      return ((UserAdmin10)jdField_try).getContactPoint(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static UserAdmin9 getUserAdminService()
  {
    return jdField_try;
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
    case 5000: 
      return 3025;
    case 5001: 
      return 3026;
    case 5050: 
      return 3027;
    case 23: 
      return 19002;
    }
    return -1009;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.UserAdminHandler
 * JD-Core Version:    0.7.0.1
 */