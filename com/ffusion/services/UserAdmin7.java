package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface UserAdmin7
  extends UserAdmin6
{
  public abstract ArrayList getUsersIDs(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ArrayList getUserIDsByUserNames(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ArrayList getFilteredBusinessEmployeesIDs(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ArrayList getBusinessEmployeesIDs(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ArrayList getBusinessEmployeesIDs(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.UserAdmin7
 * JD-Core Version:    0.7.0.1
 */