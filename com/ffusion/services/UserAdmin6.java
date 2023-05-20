package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTagValuesList;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface UserAdmin6
{
  public static final int SERVICE_ERROR_INVALID_SIGNON = 3000;
  public static final int SERVICE_ERROR_NO_DATABASE_CONNECTION = 3001;
  public static final int SERVICE_ERROR_UNABLE_TO_COMPLETE_REQUEST = 3002;
  public static final int SERVICE_ERROR_INVALID_REQUEST = 3003;
  public static final int SERVICE_ERROR_INVALID_PASSWORD = 3004;
  public static final int SERVICE_ERROR_USER_NOT_FOUND = 3005;
  public static final int ERROR_PASSWORD_AUTHENTICATION_REQUIRED = 3006;
  public static final int ERROR_PASSWORD_CHANGE_REQUIRED = 3007;
  public static final int ERROR_USER_LOCKED_OUT = 3008;
  public static final int SERVICE_ERROR_NO_DATA_FOUND = 3009;
  public static final int SERVICE_ERROR_INIT_FILE_NOT_FOUND = 3010;
  public static final int SERVICE_ERROR_INVALID_INIT_FILE = 3011;
  public static final int SERVICE_ERROR_NOT_SUPPORTED = 3200;
  public static final int SERVICE_ERROR_BUSINESS_NOT_FOUND = 3201;
  
  public abstract void initialize(String paramString)
    throws ProfileException;
  
  public abstract SecureUser authenticate(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract boolean userExists(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User addUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User getUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User getUserById(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Users getUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Users getFilteredUsers(SecureUser paramSecureUser, User paramUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User modifyPasswordStatus(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Users getUserList(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User modifyUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User modifyUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Accounts getAccounts(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Account modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Account deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void addAdditionalData(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getAdditionalData(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract CustomTags addCustomTags(SecureUser paramSecureUser, CustomTags paramCustomTags, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract CustomTags getCustomTags(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract CustomTag modifyCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract CustomTag deleteCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract CustomTagValuesList getCustomTagValuesList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ArrayList getExtraFields(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User deleteUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BusinessEmployee addBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BusinessEmployee modifyBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BusinessEmployees getBusinessEmployees(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Users getConsumers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BusinessEmployee deleteBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract boolean businessIdExists(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BusinessEmployees getFilteredBusinessEmployees(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getFilteredBusinessEmployeesCount(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BusinessEmployees getBusinessEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User resetUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getUsersCount(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getConsumersCount(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BusinessEmployees getFilteredBusinessEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.UserAdmin6
 * JD-Core Version:    0.7.0.1
 */