package com.ffusion.services;

import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;

public abstract interface BusinessService
  extends SignOn2
{
  public static final int SUCCESS = 0;
  public static final int ERROR_MUST_BE_SUPERUSER = 4000;
  public static final int ERROR_CANT_DELETE_SUPERUSER = 4001;
  public static final int ERROR_EMPLOYEE_ID_ALREADY_EXISTS = 4002;
  public static final int ERROR_EMPLOYEE_NOT_FOUND = 4003;
  public static final int ERROR_INVALID_SIGNON = 4004;
  public static final int ERROR_NO_DATABASE_CONNECTION = 4005;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 4006;
  public static final int ERROR_INVALID_REQUEST = 4007;
  public static final int ERROR_INVALID_USER_NAME = 4008;
  public static final int ERROR_INVALID_PASSWORD = 4009;
  public static final int ERROR_NOT_SUPPORTED = 4010;
  public static final int ERROR_INVALID_BUSINESS_ID = 4011;
  public static final int ERROR_INVALID_BANK_ID = 4022;
  public static final int ERROR_DUPLICATE_CUSTOMER_ID = 4023;
  public static final int ERROR_DUPLICATE_BUSINESS_CIF = 4025;
  
  public abstract int initialize(String paramString);
  
  public abstract void setSettings(String paramString);
  
  public abstract String getSettings();
  
  public abstract int signOn(String paramString1, String paramString2);
  
  public abstract void signOff();
  
  public abstract int modifyBusiness(Business paramBusiness);
  
  public abstract int addBusiness(Business paramBusiness);
  
  public abstract int deactivateBusiness(Business paramBusiness);
  
  public abstract int reactivateBusiness(Business paramBusiness);
  
  public abstract int purgeDeactivatedBusiness(Business paramBusiness);
  
  public abstract int getBusiness(Business paramBusiness);
  
  public abstract int getBusinesses(Businesses paramBusinesses);
  
  public abstract int enrollBusiness(Business paramBusiness);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BusinessService
 * JD-Core Version:    0.7.0.1
 */