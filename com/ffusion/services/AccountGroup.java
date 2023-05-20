package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.util.beans.accountgroups.BusinessAccountGroupException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface AccountGroup
{
  public abstract void initialize(HashMap paramHashMap)
    throws BusinessAccountGroupException;
  
  public abstract BusinessAccountGroups getAccountGroups(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws BusinessAccountGroupException;
  
  public abstract ArrayList getAccountGroupIds(SecureUser paramSecureUser, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws BusinessAccountGroupException;
  
  public abstract BusinessAccountGroup getAccountGroup(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws BusinessAccountGroupException;
  
  public abstract BusinessAccountGroup addAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws BusinessAccountGroupException;
  
  public abstract void modifyAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup1, BusinessAccountGroup paramBusinessAccountGroup2, HashMap paramHashMap)
    throws BusinessAccountGroupException;
  
  public abstract void deleteAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws BusinessAccountGroupException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountGroup
 * JD-Core Version:    0.7.0.1
 */