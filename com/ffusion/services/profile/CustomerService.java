package com.ffusion.services.profile;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.UserAdmin5;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Level;

public class CustomerService
  extends ProfileServices
  implements UserAdmin5
{
  public int addUser(User paramUser)
  {
    int i = 0;
    return i;
  }
  
  public int authenticateUser(User paramUser)
  {
    int i = 0;
    return i;
  }
  
  public int getUsers(Users paramUsers, User paramUser)
  {
    int i = 0;
    return i;
  }
  
  public int modifyUserPassword(User paramUser, String paramString1, String paramString2)
  {
    int i = 0;
    return i;
  }
  
  public int userExists(User paramUser)
  {
    int i = 0;
    try
    {
      Users localUsers = null;
      localUsers = CustomerAdapter.getUsers(paramUser);
      if (localUsers != null)
      {
        Iterator localIterator = localUsers.iterator();
        if (!localIterator.hasNext()) {
          i = 3005;
        }
      }
      else
      {
        i = 3005;
      }
    }
    catch (Exception localException)
    {
      i = mapError(localException);
    }
    return i;
  }
  
  public int deleteUser(User paramUser)
  {
    int i = 0;
    return i;
  }
  
  public int getUser(User paramUser)
  {
    int i = 0;
    return i;
  }
  
  public int modifyUser(User paramUser)
  {
    int i = 0;
    return i;
  }
  
  public int addHistory(Histories paramHistories)
  {
    int i = 0;
    return i;
  }
  
  public int getHistory(History paramHistory, Histories paramHistories, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    return 0;
  }
  
  protected int mapError(Exception paramException)
  {
    int i = 3002;
    DebugLog.throwing("CustomerService: Exception from ProfileAdapter", paramException);
    if ((paramException instanceof ProfileException))
    {
      ProfileException localProfileException = (ProfileException)paramException;
      DebugLog.log(Level.SEVERE, "CustomerService: " + localProfileException.where + " : " + localProfileException.why + " : " + localProfileException.code);
      switch (localProfileException.code)
      {
      case 0: 
        i = 0;
        break;
      case 1: 
        i = 3001;
        break;
      case 12: 
        i = 3009;
        break;
      case 3: 
      case 4: 
        i = 3003;
        break;
      case 6: 
      case 8: 
        i = 3004;
        break;
      case 10: 
        i = 3006;
        break;
      case 9: 
        i = 3007;
        break;
      case 7: 
        i = 3005;
        break;
      case 11: 
        i = 3008;
        break;
      case 2: 
      case 5: 
      default: 
        i = 3002;
      }
    }
    return i;
  }
  
  public int addAccount(Account paramAccount)
  {
    return 3200;
  }
  
  public int getAccounts(Accounts paramAccounts, Account paramAccount)
  {
    return 3200;
  }
  
  public int modifyAccount(Account paramAccount)
  {
    return 3200;
  }
  
  public int deleteAccount(Account paramAccount)
  {
    return 3200;
  }
  
  public int addAdditionalData(String paramString1, String paramString2)
  {
    return 3200;
  }
  
  public int getAdditionalData(String paramString, StringBuffer paramStringBuffer)
  {
    return 3200;
  }
  
  public int getLastError()
  {
    return 3200;
  }
  
  public String getLastMessage()
  {
    return null;
  }
  
  public int getHistoryUserType()
  {
    return 3200;
  }
  
  public int getHistoryEmployeeType()
  {
    return 3200;
  }
  
  public int addCustomTags(CustomTags paramCustomTags, User paramUser)
  {
    return 3200;
  }
  
  public int getCustomTags(User paramUser)
  {
    return 3200;
  }
  
  public int modifyCustomTags(CustomTag paramCustomTag, User paramUser)
  {
    return 3200;
  }
  
  public int deleteCustomTags(CustomTag paramCustomTag, User paramUser)
  {
    return 3200;
  }
  
  public int addBusinessEmployee(BusinessEmployee paramBusinessEmployee)
  {
    int i = 0;
    try
    {
      CustomerAdapter.addBusinessEmployee(paramBusinessEmployee);
    }
    catch (Exception localException)
    {
      i = mapError(localException);
    }
    return i;
  }
  
  public int getBusinessEmployees(BusinessEmployees paramBusinessEmployees, BusinessEmployee paramBusinessEmployee)
  {
    int i = 0;
    try
    {
      BusinessEmployees localBusinessEmployees = CustomerAdapter.getBusinessEmployees(paramBusinessEmployee);
      paramBusinessEmployees.set(localBusinessEmployees);
    }
    catch (Exception localException)
    {
      i = mapError(localException);
    }
    return i;
  }
  
  public int modifyBusinessEmployee(BusinessEmployee paramBusinessEmployee)
  {
    int i = 0;
    try
    {
      CustomerAdapter.modifyBusinessEmployee(paramBusinessEmployee);
    }
    catch (Exception localException)
    {
      i = mapError(localException);
    }
    return i;
  }
  
  public int deleteBusinessEmployee(BusinessEmployee paramBusinessEmployee)
  {
    int i = 0;
    try
    {
      CustomerAdapter.deleteBusinessEmployee(paramBusinessEmployee);
    }
    catch (Exception localException)
    {
      i = mapError(localException);
    }
    return i;
  }
  
  public int businessIdExists(int paramInt)
  {
    int i = 0;
    try
    {
      boolean bool = CustomerAdapter.isValidBusinessId(paramInt);
      if (!bool) {
        i = 3201;
      }
    }
    catch (Exception localException)
    {
      i = mapError(localException);
    }
    return i;
  }
  
  public int getFilteredBusinessEmployees(BusinessEmployees paramBusinessEmployees, Business paramBusiness, BusinessEmployee paramBusinessEmployee)
  {
    int i = 0;
    try
    {
      BusinessEmployees localBusinessEmployees = CustomerAdapter.getFilteredBusinessEmployees(paramBusiness, paramBusinessEmployee);
      paramBusinessEmployees.set(localBusinessEmployees);
    }
    catch (Exception localException)
    {
      i = mapError(localException);
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.CustomerService
 * JD-Core Version:    0.7.0.1
 */