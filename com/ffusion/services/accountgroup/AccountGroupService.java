package com.ffusion.services.accountgroup;

import com.ffusion.accountgroups.adapter.AccountGroupAdapter;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.services.AccountGroup;
import com.ffusion.util.beans.accountgroups.BusinessAccountGroupException;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class AccountGroupService
  implements AccountGroup
{
  public void initialize(HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    AccountGroupAdapter.initialize(paramHashMap);
  }
  
  public BusinessAccountGroups getAccountGroups(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    return AccountGroupAdapter.getAccountGroups(paramInt, paramHashMap, paramSecureUser.getLocale());
  }
  
  public ArrayList getAccountGroupIds(SecureUser paramSecureUser, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    return AccountGroupAdapter.getAccountGroupIds(paramInt, paramArrayList, paramHashMap);
  }
  
  public BusinessAccountGroup getAccountGroup(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    return AccountGroupAdapter.getAccountGroup(paramInt, paramHashMap, paramSecureUser.getLocale());
  }
  
  public BusinessAccountGroup addAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    return AccountGroupAdapter.addAccountGroup(paramBusinessAccountGroup, paramHashMap);
  }
  
  public void modifyAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup1, BusinessAccountGroup paramBusinessAccountGroup2, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    AccountGroupAdapter.modifyAccountGroup(paramBusinessAccountGroup1, paramBusinessAccountGroup2, paramHashMap);
  }
  
  public void deleteAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupService.deleteAccountGroup";
    AccountGroupAdapter.deleteAccountGroup(paramBusinessAccountGroup, paramHashMap);
    int i = paramBusinessAccountGroup.getBusDirId();
    try
    {
      EntitlementGroup localEntitlementGroup = a(i);
      if (localEntitlementGroup != null) {
        EntitlementsUtil.removeEntitlementsAndLimitsForObjectUnsafe(localEntitlementGroup.getGroupId(), "AccountGroup", String.valueOf(paramBusinessAccountGroup.getId()));
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str + ": " + localException.getMessage());
      throw new BusinessAccountGroupException(localException, str, 2, "Unable to get the business entitlement group for the business directory id " + i);
    }
  }
  
  private static EntitlementGroup a(int paramInt)
    throws Exception
  {
    EntitlementGroup localEntitlementGroup = null;
    Business localBusiness = null;
    localBusiness = BusinessAdapter.getBusiness(paramInt);
    if (localBusiness != null)
    {
      EntitlementGroupMember localEntitlementGroupMember = Entitlements.getMember(localBusiness.getEntitlementGroupMember());
      localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroupMember.getEntitlementGroupId());
    }
    return localEntitlementGroup;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.accountgroup.AccountGroupService
 * JD-Core Version:    0.7.0.1
 */