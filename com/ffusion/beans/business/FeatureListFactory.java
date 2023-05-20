package com.ffusion.beans.business;

import com.ffusion.beans.ExtendABean;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import java.util.HashMap;

public class FeatureListFactory
  extends ExtendABean
{
  public static HashMap createChargeableFeatureList()
  {
    HashMap localHashMap = new HashMap();
    try
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = null;
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("category", "chargeable");
      for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
        localHashMap.put(localEntitlementTypePropertyList.getOperationName(), localEntitlementTypePropertyList.getPropertyValue("display name", 0));
      }
    }
    catch (Exception localException) {}
    return localHashMap;
  }
  
  public static HashMap generateFeatureList()
  {
    HashMap localHashMap = new HashMap();
    try
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = null;
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("category", "per package");
      for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
        localHashMap.put(localEntitlementTypePropertyList.getOperationName(), localEntitlementTypePropertyList.getPropertyValue("display name", 0));
      }
    }
    catch (Exception localException) {}
    return localHashMap;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.FeatureListFactory
 * JD-Core Version:    0.7.0.1
 */