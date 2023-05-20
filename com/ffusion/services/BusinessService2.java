package com.ffusion.services;

import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.BusinessEmployee;

public abstract interface BusinessService2
  extends BusinessService
{
  public abstract int getFilteredBusinesses(Businesses paramBusinesses, Business paramBusiness, BusinessEmployee paramBusinessEmployee);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BusinessService2
 * JD-Core Version:    0.7.0.1
 */