package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import java.util.HashMap;

public class BusinessAdmin
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.BusinessAdmin.initialize");
  }
  
  public static com.ffusion.beans.business.Business modifyBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness1, com.ffusion.beans.business.Business paramBusiness2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BusinessAdmin.ModifyBusiness";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember, paramBusiness1.getEntitlementGroupIdValue());
    if (!Entitlements.canAdminister(localEntitlementAdmin)) {
      throw new CSILException(str, 20001);
    }
    return Business.modifyBusiness(paramSecureUser, paramBusiness1, paramBusiness2, paramHashMap);
  }
  
  public static void addAdditionalData(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Business.addAdditionalData(paramSecureUser, paramBusiness, paramString1, paramString2, paramHashMap);
  }
  
  public static String getAdditionalData(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return Business.getAdditionalData(paramSecureUser, paramBusiness, paramString, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.BusinessAdmin
 * JD-Core Version:    0.7.0.1
 */