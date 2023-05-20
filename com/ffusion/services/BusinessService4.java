package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface BusinessService4
  extends BusinessService3
{
  public abstract ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ArrayList getTransactionGroups(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getTypeCodesForGroup(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void addTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract boolean uniqueCustId(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BusinessService4
 * JD-Core Version:    0.7.0.1
 */