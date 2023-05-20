package com.ffusion.services.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageFilter;
import com.ffusion.beans.messages.GlobalMessageFilters;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.services.MessagingFilters;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.logging.Level;

public class MessagingFiltersService
  implements MessagingFilters
{
  public boolean checkFiltersForDirID(GlobalMessage paramGlobalMessage, int paramInt, HashMap paramHashMap)
  {
    synchronized (this)
    {
      return a(a(paramInt), paramGlobalMessage);
    }
  }
  
  public boolean checkFiltersForSecureUser(GlobalMessage paramGlobalMessage, SecureUser paramSecureUser, HashMap paramHashMap)
  {
    synchronized (this)
    {
      return a(a(paramSecureUser), paramGlobalMessage);
    }
  }
  
  public boolean checkFiltersForUser(GlobalMessage paramGlobalMessage, User paramUser, HashMap paramHashMap)
  {
    synchronized (this)
    {
      return true;
    }
  }
  
  private boolean a(EntitlementGroupMember paramEntitlementGroupMember, GlobalMessage paramGlobalMessage)
  {
    boolean bool = true;
    try
    {
      GlobalMessageFilters localGlobalMessageFilters = (GlobalMessageFilters)paramGlobalMessage.get("_filters");
      GlobalMessageFilter localGlobalMessageFilter = null;
      Entitlement localEntitlement = null;
      int i = 0;
      if ((localGlobalMessageFilters != null) && ((i = localGlobalMessageFilters.size()) > 0)) {
        for (int j = 0; j < i; j++)
        {
          localGlobalMessageFilter = (GlobalMessageFilter)localGlobalMessageFilters.get(j);
          localEntitlement = new Entitlement(localGlobalMessageFilter.getFilterValue(), null, null);
          if (!Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))
          {
            bool = false;
            break;
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      bool = false;
      DebugLog.log(Level.SEVERE, getClass().getName() + localCSILException.getMessage());
    }
    return bool;
  }
  
  private EntitlementGroupMember a(int paramInt)
  {
    EntitlementGroupMember localEntitlementGroupMember = null;
    try
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setId(String.valueOf(paramInt));
      localEntitlementGroupMember.setMemberType("USER");
      localEntitlementGroupMember.setMemberSubType(String.valueOf(1));
      localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.SEVERE, getClass().getName() + localCSILException.getMessage());
    }
    return localEntitlementGroupMember;
  }
  
  private EntitlementGroupMember a(SecureUser paramSecureUser)
  {
    return EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.MessagingFiltersService
 * JD-Core Version:    0.7.0.1
 */