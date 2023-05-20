package com.ffusion.services;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import java.io.Serializable;
import java.util.Calendar;

public abstract interface UserAdmin
  extends Serializable
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
  
  public abstract int addUser(User paramUser);
  
  public abstract int getUser(User paramUser);
  
  public abstract int authenticateUser(User paramUser);
  
  public abstract int getUsers(Users paramUsers, User paramUser);
  
  public abstract int modifyUser(User paramUser);
  
  public abstract int modifyUserPassword(User paramUser, String paramString1, String paramString2);
  
  public abstract int addAccount(Account paramAccount);
  
  public abstract int getAccounts(Accounts paramAccounts, Account paramAccount);
  
  public abstract int modifyAccount(Account paramAccount);
  
  public abstract int deleteAccount(Account paramAccount);
  
  public abstract int addAdditionalData(String paramString1, String paramString2);
  
  public abstract int getAdditionalData(String paramString, StringBuffer paramStringBuffer);
  
  public abstract int addHistory(Histories paramHistories);
  
  public abstract int getHistory(History paramHistory, Histories paramHistories, Calendar paramCalendar1, Calendar paramCalendar2);
  
  public abstract int getLastError();
  
  public abstract String getLastMessage();
  
  public abstract void setInitURL(String paramString);
  
  public abstract int addCustomTags(CustomTags paramCustomTags, User paramUser);
  
  public abstract int getCustomTags(User paramUser);
  
  public abstract int modifyCustomTags(CustomTag paramCustomTag, User paramUser);
  
  public abstract int deleteCustomTags(CustomTag paramCustomTag, User paramUser);
  
  public abstract int getHistoryUserType();
  
  public abstract int getHistoryEmployeeType();
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.UserAdmin
 * JD-Core Version:    0.7.0.1
 */