package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface BusinessService3
{
  public abstract void initialize(String paramString)
    throws ProfileException;
  
  public abstract void setSettings(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getSettings(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void addAdditionalData(SecureUser paramSecureUser, Business paramBusiness, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getAdditionalData(SecureUser paramSecureUser, Business paramBusiness, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Business addBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Business deactivateBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Business reactivateBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void purgeDeactivatedBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Business getBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Businesses getBusinesses(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void enrollBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Businesses getFilteredBusinesses(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract int getFilteredBusinessesCount(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Businesses getBusinessesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User getBusinessInfoFromBackend(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BusinessService3
 * JD-Core Version:    0.7.0.1
 */