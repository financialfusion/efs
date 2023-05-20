package com.ffusion.services;

import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;

public abstract interface UserAdmin5
  extends UserAdmin4
{
  public static final int SERVICE_ERROR_NOT_SUPPORTED = 3200;
  public static final int SERVICE_ERROR_BUSINESS_NOT_FOUND = 3201;
  
  public abstract int addBusinessEmployee(BusinessEmployee paramBusinessEmployee);
  
  public abstract int getBusinessEmployees(BusinessEmployees paramBusinessEmployees, BusinessEmployee paramBusinessEmployee);
  
  public abstract int modifyBusinessEmployee(BusinessEmployee paramBusinessEmployee);
  
  public abstract int deleteBusinessEmployee(BusinessEmployee paramBusinessEmployee);
  
  public abstract int businessIdExists(int paramInt);
  
  public abstract int getFilteredBusinessEmployees(BusinessEmployees paramBusinessEmployees, Business paramBusiness, BusinessEmployee paramBusinessEmployee);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.UserAdmin5
 * JD-Core Version:    0.7.0.1
 */