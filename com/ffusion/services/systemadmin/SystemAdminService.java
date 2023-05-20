package com.ffusion.services.systemadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.MarketSegment;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.services.SystemAdmin;
import com.ffusion.systemadmin.SAConstants;
import com.ffusion.systemadmin.SAException;
import com.ffusion.systemadmin.SystemAdminUtil;
import com.ffusion.systemadmin.adapter.SystemAdminAdapter;
import java.util.ArrayList;
import java.util.HashMap;

public class SystemAdminService
  implements SAConstants, SystemAdmin
{
  public void initialize(HashMap paramHashMap)
    throws SAException
  {
    SystemAdminAdapter.initialize(paramHashMap);
  }
  
  public HashMap getCumulativeDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws SAException
  {
    String str = "SystemAdminService.getCumulativeDataRetentionSettings";
    HashMap localHashMap = new HashMap();
    Business localBusiness = null;
    EntitlementGroup localEntitlementGroup = null;
    int i = 0;
    boolean bool = false;
    ArrayList localArrayList = new ArrayList(5);
    int j = paramInt2;
    if (paramHashMap != null)
    {
      Boolean localBoolean = (Boolean)paramHashMap.get("DefaultSettingsFlag");
      if (localBoolean != null) {
        bool = localBoolean.booleanValue();
      }
    }
    switch (paramInt1)
    {
    case 4: 
      try
      {
        localBusiness = BusinessAdapter.getBusiness(j);
      }
      catch (Exception localException1)
      {
        throw new SAException(jdMethod_for(localException1), localException1);
      }
      if (localBusiness == null) {
        throw new SAException(38206, "The specified data retention ID does not correspond to any existing business.");
      }
      try
      {
        if (!bool) {
          localArrayList.add(0, SystemAdminAdapter.getDataRetentionSettings(4, j, paramHashMap));
        }
        bool = false;
        localEntitlementGroup = Entitlements.getEntitlementGroup(Entitlements.getMember(localBusiness.getEntitlementGroupMember()).getEntitlementGroupId());
        j = localEntitlementGroup.getParentId();
      }
      catch (Exception localException2)
      {
        throw new SAException(jdMethod_for(localException2), localException2);
      }
    case 3: 
      try
      {
        if (!bool) {
          localArrayList.add(0, SystemAdminAdapter.getDataRetentionSettings(3, j, paramHashMap));
        }
        bool = false;
        localEntitlementGroup = Entitlements.getEntitlementGroup(j);
        j = localEntitlementGroup.getParentId();
      }
      catch (Exception localException3)
      {
        throw new SAException(jdMethod_for(localException3), localException3);
      }
    case 2: 
      try
      {
        if (!bool) {
          localArrayList.add(0, SystemAdminAdapter.getDataRetentionSettings(2, j, paramHashMap));
        }
        bool = false;
        if (localBusiness == null)
        {
          MarketSegment localMarketSegment = new MarketSegment();
          localEntitlementGroup = Entitlements.getEntitlementGroup(j);
          localMarketSegment.setEntitlementGroup(localEntitlementGroup);
          try
          {
            j = -1;
            j = Integer.parseInt(localMarketSegment.getAffiliateBankId());
            if ((j == -1) || (j == 0)) {
              i = 1;
            } else {
              i = 0;
            }
          }
          catch (Exception localException7)
          {
            j = -1;
            i = 1;
          }
        }
        else
        {
          j = localBusiness.getAffiliateBankID();
          i = 0;
        }
      }
      catch (Exception localException4)
      {
        throw new SAException(jdMethod_for(localException4), localException4);
      }
    case 1: 
      if (i == 0) {
        try
        {
          if (!bool) {
            localArrayList.add(0, SystemAdminAdapter.getDataRetentionSettings(1, j, paramHashMap));
          }
          bool = false;
        }
        catch (Exception localException5)
        {
          throw new SAException(jdMethod_for(localException5), localException5);
        }
      }
      j = 0;
    case 0: 
      try
      {
        if (!bool) {
          localArrayList.add(0, SystemAdminAdapter.getDataRetentionSettings(0, j, paramHashMap));
        }
        bool = false;
      }
      catch (Exception localException6)
      {
        throw new SAException(jdMethod_for(localException6), localException6);
      }
    }
    throw new SAException(38205, "Invalid data retention type specified.");
    localHashMap = SystemAdminUtil.getOverriddenDataRetentionSettings(localArrayList, paramHashMap);
    return localHashMap;
  }
  
  public HashMap getDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws SAException
  {
    String str = "SystemAdminService.getDataRetentionSettings";
    HashMap localHashMap = null;
    try
    {
      localHashMap = SystemAdminAdapter.getDataRetentionSettings(paramInt1, paramInt2, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new SAException(jdMethod_for(localException), localException);
    }
    return localHashMap;
  }
  
  private static int jdMethod_for(Exception paramException)
  {
    int i = 38201;
    if ((paramException instanceof SAException))
    {
      SAException localSAException = (SAException)paramException;
      i = localSAException.getErrorCode();
    }
    return i;
  }
  
  public void setDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap1, HashMap paramHashMap2)
    throws SAException
  {
    String str = "SystemAdminService.setDataRetentionSettings";
    try
    {
      SystemAdminAdapter.setDataRetentionSettings(paramInt1, paramInt2, paramHashMap1, paramHashMap2);
    }
    catch (Exception localException)
    {
      throw new SAException(jdMethod_for(localException), localException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.systemadmin.SystemAdminService
 * JD-Core Version:    0.7.0.1
 */