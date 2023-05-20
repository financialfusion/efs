package com.ffusion.services.fx;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.systemadmin.DRKey;
import com.ffusion.beans.systemadmin.DRSetting;
import com.ffusion.beans.user.User;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.fx.FXException;
import com.ffusion.fx.adapter.FXAdapter;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.systemadmin.SystemAdminUtil;
import com.ffusion.systemadmin.adapter.SystemAdminAdapter;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FXStoredService
  extends FXCommonStoredService
  implements IForeignExchangeService
{
  public static final String BANKID = "BANKID";
  public static final String DEFAULT_BANKID = "1000";
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    String str1 = "FXStoredService.cleanup";
    if (paramInt >= 0)
    {
      this._adapter.cleanup(paramInt, paramHashMap);
    }
    else
    {
      HashMap localHashMap1 = null;
      HashMap localHashMap2 = null;
      HashMap localHashMap3 = null;
      HashMap localHashMap4 = null;
      HashMap localHashMap5 = null;
      EntitlementGroups localEntitlementGroups1 = null;
      HashMap localHashMap6 = new HashMap();
      DRKey localDRKey = null;
      DRSetting localDRSetting = null;
      String str2 = " ";
      localDRKey = new DRKey();
      localDRKey.setDataType("FX");
      localDRKey.setDataClass(str2);
      ArrayList localArrayList = null;
      HashMap localHashMap7 = null;
      try
      {
        localHashMap1 = SystemAdminAdapter.getDataRetentionSettings(0, 0, paramHashMap);
        localDRSetting = (DRSetting)localHashMap1.get(localDRKey);
        paramHashMap.put("OBJECT_ID", "0");
        paramHashMap.put("OBJECT_TYPE", Integer.toString(0));
        if (localDRSetting != null) {
          this._adapter.cleanup(localDRSetting.getRetentionDays(), paramHashMap);
        }
        SecureUser localSecureUser = new SecureUser();
        localObject1 = (String)paramHashMap.get("BANKID");
        if (localObject1 == null) {
          localObject1 = "1000";
        }
        localSecureUser.setBankID((String)localObject1);
        AffiliateBanks localAffiliateBanks = AffiliateBankAdapter.getAffiliateBanks(localSecureUser);
        Iterator localIterator1 = localAffiliateBanks.iterator();
        Object localObject2;
        while (localIterator1.hasNext())
        {
          localObject2 = (AffiliateBank)localIterator1.next();
          localHashMap2 = SystemAdminAdapter.getDataRetentionSettings(1, ((AffiliateBank)localObject2).getAffiliateBankID(), paramHashMap);
          localHashMap6.put(Integer.toString(((AffiliateBank)localObject2).getAffiliateBankID()), localHashMap2);
          localArrayList = new ArrayList(2);
          localArrayList.add(0, localHashMap2);
          localArrayList.add(0, localHashMap1);
          localHashMap7 = SystemAdminUtil.getOverriddenDataRetentionSettings(localArrayList, paramHashMap);
          localDRSetting = (DRSetting)localHashMap7.get(localDRKey);
          paramHashMap.put("OBJECT_ID", Integer.toString(((AffiliateBank)localObject2).getAffiliateBankID()));
          paramHashMap.put("OBJECT_TYPE", Integer.toString(1));
          if (localDRSetting != null) {
            this._adapter.cleanup(localDRSetting.getRetentionDays(), paramHashMap);
          }
        }
        localEntitlementGroups1 = this.eadapter.getEntitlementGroupsByType("MarketSegment");
        localIterator1 = localEntitlementGroups1.iterator();
        while (localIterator1.hasNext())
        {
          localObject2 = (EntitlementGroup)localIterator1.next();
          EntitlementGroups localEntitlementGroups2 = this.eadapter.getChildrenByGroupType(((EntitlementGroup)localObject2).getGroupId(), "ServicesPackage");
          localHashMap3 = SystemAdminAdapter.getDataRetentionSettings(2, ((EntitlementGroup)localObject2).getGroupId(), paramHashMap);
          Iterator localIterator2 = localEntitlementGroups2.iterator();
          while (localIterator2.hasNext())
          {
            EntitlementGroup localEntitlementGroup1 = (EntitlementGroup)localIterator2.next();
            localHashMap7 = null;
            EntitlementGroups localEntitlementGroups3 = this.eadapter.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "BusinessAdmin");
            EntitlementGroups localEntitlementGroups4 = this.eadapter.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "ConsumerAdmin");
            localHashMap4 = SystemAdminAdapter.getDataRetentionSettings(3, localEntitlementGroup1.getGroupId(), paramHashMap);
            int i = 0;
            Iterator localIterator3 = localEntitlementGroups3.iterator();
            EntitlementGroup localEntitlementGroup2;
            Object localObject3;
            Iterator localIterator4;
            Object localObject4;
            Object localObject5;
            Object localObject6;
            Object localObject7;
            while (localIterator3.hasNext())
            {
              localEntitlementGroup2 = (EntitlementGroup)localIterator3.next();
              localObject3 = this.eadapter.getMembers(localEntitlementGroup2.getGroupId());
              localIterator4 = ((EntitlementGroupMembers)localObject3).iterator();
              while (localIterator4.hasNext())
              {
                localObject4 = (EntitlementGroupMember)localIterator4.next();
                int j = -1;
                localObject5 = null;
                localHashMap7 = null;
                try
                {
                  j = Integer.parseInt(((EntitlementGroupMember)localObject4).getId());
                }
                catch (Exception localException2)
                {
                  j = -1;
                }
                localObject5 = BusinessAdapter.getBusiness(j);
                if (localObject5 != null)
                {
                  if (i == 0)
                  {
                    localHashMap2 = (HashMap)localHashMap6.get(((Business)localObject5).getAffiliateBankIDStr());
                    localArrayList = new ArrayList(4);
                    localArrayList.add(0, localHashMap4);
                    localArrayList.add(0, localHashMap3);
                    localArrayList.add(0, localHashMap2);
                    localArrayList.add(0, localHashMap1);
                    localHashMap7 = SystemAdminUtil.getOverriddenDataRetentionSettings(localArrayList, paramHashMap);
                    localDRSetting = (DRSetting)localHashMap7.get(localDRKey);
                    paramHashMap.put("OBJECT_ID", Integer.toString(localEntitlementGroup1.getGroupId()));
                    paramHashMap.put("OBJECT_TYPE", Integer.toString(2));
                    if (localDRSetting != null) {
                      this._adapter.cleanup(localDRSetting.getRetentionDays(), paramHashMap);
                    }
                    i = 1;
                  }
                  localHashMap5 = SystemAdminAdapter.getDataRetentionSettings(4, ((Business)localObject5).getIdValue(), paramHashMap);
                  localArrayList = new ArrayList(5);
                  localArrayList.add(0, localHashMap5);
                  localArrayList.add(0, localHashMap4);
                  localArrayList.add(0, localHashMap3);
                  localArrayList.add(0, localHashMap2);
                  localArrayList.add(0, localHashMap1);
                  localHashMap7 = SystemAdminUtil.getOverriddenDataRetentionSettings(localArrayList, paramHashMap);
                  localDRSetting = (DRSetting)localHashMap7.get(localDRKey);
                  localObject6 = null;
                  EntitlementGroups localEntitlementGroups5 = this.eadapter.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "Business");
                  localObject7 = localEntitlementGroups5.iterator();
                  if (((Iterator)localObject7).hasNext())
                  {
                    EntitlementGroup localEntitlementGroup3 = (EntitlementGroup)((Iterator)localObject7).next();
                    localObject6 = Integer.toString(localEntitlementGroup3.getGroupId());
                  }
                  paramHashMap.put("OBJECT_ID", localObject6);
                  paramHashMap.put("OBJECT_TYPE", Integer.toString(3));
                  if (localDRSetting != null) {
                    this._adapter.cleanup(localDRSetting.getRetentionDays(), paramHashMap);
                  }
                }
              }
            }
            localIterator3 = localEntitlementGroups4.iterator();
            while ((localIterator3.hasNext()) && (i == 0))
            {
              localEntitlementGroup2 = (EntitlementGroup)localIterator3.next();
              localObject3 = this.eadapter.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "USER");
              localIterator4 = ((EntitlementGroups)localObject3).iterator();
              while (localIterator4.hasNext())
              {
                localObject4 = (EntitlementGroup)localIterator4.next();
                EntitlementGroupMembers localEntitlementGroupMembers = this.eadapter.getMembers(((EntitlementGroup)localObject4).getGroupId());
                localObject5 = localEntitlementGroupMembers.iterator();
                while (((Iterator)localObject5).hasNext())
                {
                  localObject6 = (EntitlementGroupMember)((Iterator)localObject5).next();
                  int k = -1;
                  localObject7 = null;
                  localArrayList = null;
                  localHashMap7 = null;
                  try
                  {
                    k = Integer.parseInt(((EntitlementGroupMember)localObject6).getId());
                  }
                  catch (Exception localException3)
                  {
                    k = -1;
                  }
                  localObject7 = CustomerAdapter.getUserById(k);
                  if ((localObject7 != null) && (i == 0))
                  {
                    localHashMap2 = (HashMap)localHashMap6.get(Integer.toString(((User)localObject7).getAffiliateBankID()));
                    localArrayList = new ArrayList(4);
                    localArrayList.add(0, localHashMap4);
                    localArrayList.add(0, localHashMap3);
                    localArrayList.add(0, localHashMap2);
                    localArrayList.add(0, localHashMap1);
                    localHashMap7 = SystemAdminUtil.getOverriddenDataRetentionSettings(localArrayList, paramHashMap);
                    localDRSetting = (DRSetting)localHashMap7.get(localDRKey);
                    paramHashMap.put("OBJECT_ID", Integer.toString(localEntitlementGroup1.getGroupId()));
                    paramHashMap.put("OBJECT_TYPE", Integer.toString(2));
                    if (localDRSetting != null) {
                      this._adapter.cleanup(localDRSetting.getRetentionDays(), paramHashMap);
                    }
                    i = 1;
                  }
                }
              }
            }
          }
        }
      }
      catch (Exception localException1)
      {
        Object localObject1 = new FXException("Could not cleanup the foreign exchange rates.", localException1);
        DebugLog.throwing("FXService.cleanup", localException1);
        throw ((Throwable)localObject1);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.fx.FXStoredService
 * JD-Core Version:    0.7.0.1
 */