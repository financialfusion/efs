package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.beans.DateTime;
import java.util.HashMap;

public abstract interface UserAdmin10
  extends UserAdmin9
{
  public abstract boolean getInfoForAuditing(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void addSecondaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Users getSecondaryUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User getUserInfoFromBackend(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract User getPrimaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract DateTime getLastExportedDateTime(SecureUser paramSecureUser, Account paramAccount, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void setLastExportedDateTime(SecureUser paramSecureUser, Account paramAccount, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void addContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void addContactPoints(SecureUser paramSecureUser, ContactPoints paramContactPoints, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ContactPoints getContactPoints(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ContactPoint getContactPoint(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.UserAdmin10
 * JD-Core Version:    0.7.0.1
 */