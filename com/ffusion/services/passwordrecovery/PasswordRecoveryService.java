package com.ffusion.services.passwordrecovery;

import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.passwordrecovery.PasswordRecoveryException;
import com.ffusion.services.PasswordRecovery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PasswordRecoveryService
  implements PasswordRecovery
{
  public void initialize(HashMap paramHashMap)
    throws PasswordRecoveryException
  {}
  
  public User lookup(HashMap paramHashMap1, HashMap paramHashMap2)
    throws PasswordRecoveryException
  {
    Object localObject1 = new User();
    ((User)localObject1).setUserName((String)paramHashMap1.get("PRUserName"));
    ((User)localObject1).setBankId((String)paramHashMap1.get("PRBankId"));
    ((User)localObject1).setEmail((String)paramHashMap1.get("PREmailAddress"));
    String str = (String)paramHashMap1.get("CustomerType");
    int i = 0;
    if ((str != null) && (str.equals(String.valueOf(2))))
    {
      ((User)localObject1).setCustomerType(String.valueOf(2));
      i = 1;
    }
    else
    {
      ((User)localObject1).setCustomerType(String.valueOf(1));
    }
    if (paramHashMap1.containsKey("PRAffiliateBank")) {
      ((User)localObject1).setAffiliateBankID((String)paramHashMap1.get("PRAffiliateBank"));
    }
    try
    {
      Users localUsers = a((User)localObject1);
      if (localUsers.size() == 0)
      {
        if (paramHashMap1.get("PREmailAddress") != null)
        {
          ((User)localObject1).setEmail("");
          localUsers = a((User)localObject1);
          if (localUsers.size() == 1)
          {
            localObject1 = (User)localUsers.get(0);
            if (CustomerAdapter.isUserLockedOut((User)localObject1)) {
              throw new PasswordRecoveryException(33004, "User is locked out");
            }
            CustomerAdapter.logFailedLogin((User)localObject1);
          }
        }
        return null;
      }
      Object localObject2 = null;
      localObject1 = null;
      Object localObject4;
      if ((!SignonSettings.allowDuplicateUserNames()) || ((SignonSettings.allowDuplicateUserNames()) && (i != 0)))
      {
        localObject1 = (User)localUsers.iterator().next();
        localObject3 = new ArrayList(1);
        ((ArrayList)localObject3).add(((User)localObject1).getId());
        localObject4 = CustomerAdapter.getBusinessEmployeesByIds((ArrayList)localObject3);
        if (!((BusinessEmployees)localObject4).isEmpty()) {
          localObject2 = (BusinessEmployee)((BusinessEmployees)localObject4).get(0);
        }
      }
      else
      {
        localObject3 = (String)paramHashMap1.get("CustomerID");
        if (localObject3 == null) {
          return null;
        }
        localObject4 = localUsers.iterator();
        while (((Iterator)localObject4).hasNext())
        {
          User localUser = (User)((Iterator)localObject4).next();
          ArrayList localArrayList = new ArrayList(1);
          localArrayList.add(localUser.getId());
          BusinessEmployees localBusinessEmployees = CustomerAdapter.getBusinessEmployeesByIds(localArrayList);
          for (int j = 0; j < localBusinessEmployees.size(); j++)
          {
            BusinessEmployee localBusinessEmployee = (BusinessEmployee)localBusinessEmployees.get(j);
            if (((String)localObject3).equals(localBusinessEmployee.getBusinessCustId()))
            {
              localObject1 = localUser;
              localObject2 = localBusinessEmployee;
            }
          }
        }
      }
      if (localObject1 == null) {
        return null;
      }
      if (CustomerAdapter.isUserLockedOut((User)localObject1)) {
        throw new PasswordRecoveryException(33004, "User is locked out.");
      }
      if (CustomerAdapter.isUserExpired((User)localObject1)) {
        throw new PasswordRecoveryException(33008, "User is expired.");
      }
      if ((((User)localObject1).getAccountStatus() == null) || (!((User)localObject1).getAccountStatus().equals(Integer.toString(1)))) {
        return null;
      }
      if (localObject2 == null) {
        return localObject1;
      }
      Object localObject3 = BusinessAdapter.getBusiness(((BusinessEmployee)localObject2).getBusinessId());
      if (localObject3 == null) {
        return null;
      }
      return ((Business)localObject3).getStatusValue() == 1 ? localObject1 : null;
    }
    catch (ProfileException localProfileException)
    {
      throw new PasswordRecoveryException(33001, "Error looking up user", localProfileException);
    }
  }
  
  public boolean validate(User paramUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws PasswordRecoveryException
  {
    try
    {
      if (CustomerAdapter.isUserLockedOut(paramUser)) {
        throw new PasswordRecoveryException(33004, "User is locked out");
      }
      String str1 = (String)paramHashMap1.get("PRPasswordAnswer");
      String str2 = paramUser.getPasswordReminder();
      String str3 = (String)paramHashMap1.get("PRPasswordAnswer2");
      String str4 = paramUser.getPasswordReminder2();
      if ((str2 == null) || (str2.length() == 0) || (str4 == null) || (str4.length() == 0))
      {
        CustomerAdapter.logFailedLogin(paramUser);
        return false;
      }
      boolean bool = false;
      if ((str1 != null) && (str3 == null))
      {
        str1 = str1.trim();
        str2 = str2.trim();
        bool = str1.equalsIgnoreCase(str2);
      }
      else if ((str1 == null) && (str3 != null))
      {
        str3 = str3.trim();
        str4 = str4.trim();
        bool = str3.equalsIgnoreCase(str4);
      }
      else if ((str1 != null) && (str3 != null))
      {
        str1 = str1.trim();
        str3 = str3.trim();
        str2 = str2.trim();
        str4 = str4.trim();
        bool = (str1.equalsIgnoreCase(str2)) && (str3.equalsIgnoreCase(str4));
      }
      if (!bool)
      {
        CustomerAdapter.logFailedLogin(paramUser);
        try
        {
          Thread.sleep(3000L);
        }
        catch (InterruptedException localInterruptedException) {}
      }
      return bool;
    }
    catch (ProfileException localProfileException)
    {
      throw new PasswordRecoveryException(33002, "Error validating the user", localProfileException);
    }
  }
  
  public void reset(User paramUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws PasswordRecoveryException
  {
    try
    {
      String str = (String)paramHashMap1.get("PRNewPassword");
      paramUser.setPassword(str);
      boolean bool = false;
      int i = CustomerAdapter.checkPasswordHistory(1, Integer.parseInt(paramUser.getId()), str, bool);
      switch (i)
      {
      case 2: 
        throw new PasswordRecoveryException(33005);
      case 3: 
        throw new PasswordRecoveryException(33006);
      case 4: 
        throw new PasswordRecoveryException(33007);
      }
      CustomerAdapter.resetUserPassword(paramUser, paramUser.getPassword(), str);
      if (i == 1) {
        CustomerAdapter.addToPasswordHistory(1, Integer.parseInt(paramUser.getId()), str);
      }
    }
    catch (ProfileException localProfileException)
    {
      throw new PasswordRecoveryException(33003, "Error resetting user password", localProfileException);
    }
  }
  
  private Users a(User paramUser)
    throws ProfileException
  {
    Users localUsers = CustomerAdapter.getUsers(paramUser);
    if ((localUsers != null) && (localUsers.size() > 0)) {
      for (int i = localUsers.size() - 1; i >= 0; i--)
      {
        User localUser = (User)localUsers.get(i);
        if ((localUser.getUserName() == null) || (paramUser.getUserName() == null) || (!localUser.getUserName().toLowerCase().equals(paramUser.getUserName().toLowerCase()))) {
          localUsers.remove(i);
        }
      }
    }
    return localUsers;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.passwordrecovery.PasswordRecoveryService
 * JD-Core Version:    0.7.0.1
 */