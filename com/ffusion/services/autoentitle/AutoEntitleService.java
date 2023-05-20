package com.ffusion.services.autoentitle;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.ObjectPropertyAdapter;
import com.ffusion.util.db.ConnectionHolder;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

public class AutoEntitleService
  implements com.ffusion.services.AutoEntitle
{
  private static final String jdField_do = "BUSINESS_OBJECT_KEY";
  private static final String jdField_if = "AFFILIATE_BANK_MAP_KEY";
  private static final String a = "ENT_GROUP_MAP_KEY";
  
  public void initialize()
    throws AutoEntitleException
  {}
  
  public com.ffusion.beans.autoentitle.AutoEntitle getSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws AutoEntitleException
  {
    String str1 = "AutoEntitleService.getSettings";
    com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = null;
    int i = paramAutoEntitle.getObjectType();
    String str2 = paramAutoEntitle.getObjectID();
    String str3 = null;
    String str4 = str2 + ":" + i;
    try
    {
      if (paramHashMap != null)
      {
        HashMap localHashMap = null;
        str5 = null;
        if (i == 1) {
          str5 = "AFFILIATE_BANK_MAP_KEY";
        } else {
          str5 = "ENT_GROUP_MAP_KEY";
        }
        localHashMap = (HashMap)paramHashMap.get(str5);
        if (localHashMap == null)
        {
          localHashMap = new HashMap();
          paramHashMap.put(str5, localHashMap);
        }
        localAutoEntitle = (com.ffusion.beans.autoentitle.AutoEntitle)localHashMap.get(str4);
        if (localAutoEntitle == null)
        {
          if (i == 2) {
            localHashMap.clear();
          }
          ConnectionHolder localConnectionHolder = (ConnectionHolder)paramHashMap.get("ObjectTypeConnHolder");
          if (localConnectionHolder == null) {
            str3 = ObjectPropertyAdapter.getObjectPropertyValue(i, str2, "AUTO_ENTITLE");
          } else {
            str3 = ObjectPropertyAdapter.getObjectPropertyValue(localConnectionHolder, i, str2, "AUTO_ENTITLE");
          }
          AutoEntitleUtil.setFromPropertyString(paramAutoEntitle, str3);
          localHashMap.put(str4, paramAutoEntitle);
          localAutoEntitle = paramAutoEntitle;
        }
        paramAutoEntitle = (com.ffusion.beans.autoentitle.AutoEntitle)localAutoEntitle.clone();
      }
      else
      {
        str3 = ObjectPropertyAdapter.getObjectPropertyValue(i, str2, "AUTO_ENTITLE");
        AutoEntitleUtil.setFromPropertyString(paramAutoEntitle, str3);
      }
      return paramAutoEntitle;
    }
    catch (Exception localException)
    {
      String str5 = "The Settings requested for AutoEntitle " + paramAutoEntitle.toXML() + " could not be retrieved.  The error " + " received from the back end was " + stackTrace(localException);
      DebugLog.log(str5);
      throw new AutoEntitleException(16, localException);
    }
  }
  
  public com.ffusion.beans.autoentitle.AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws AutoEntitleException
  {
    String str = "AutoEntitleService.getCumulativeSettings";
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = getSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
      localObject = null;
      HashMap localHashMap = (HashMap)paramHashMap.get("AutoEntitleSettingsMap");
      if (paramAutoEntitle.getAffiliateBank() != null) {
        return localAutoEntitle;
      }
      EntitlementGroupMember localEntitlementGroupMember;
      EntitlementGroup localEntitlementGroup;
      Integer localInteger;
      if (localHashMap == null)
      {
        localObject = getCumulativeParentSettings(paramSecureUser, localAutoEntitle, paramHashMap);
      }
      else
      {
        localEntitlementGroupMember = paramAutoEntitle.getEntitlementGroupMember();
        localEntitlementGroup = paramAutoEntitle.getEntitlementGroup();
        if (localEntitlementGroupMember != null)
        {
          localInteger = new Integer(localEntitlementGroupMember.getEntitlementGroupId());
          localObject = (com.ffusion.beans.autoentitle.AutoEntitle)localHashMap.get(localInteger);
          if (localObject == null) {
            localObject = getCumulativeParentSettings(paramSecureUser, localAutoEntitle, paramHashMap);
          }
        }
        else if (localEntitlementGroup != null)
        {
          localInteger = new Integer(localEntitlementGroup.getGroupId());
          localObject = (com.ffusion.beans.autoentitle.AutoEntitle)localHashMap.get(localInteger);
          if (localObject == null) {
            localObject = getCumulativeParentSettings(paramSecureUser, localAutoEntitle, paramHashMap);
          }
        }
        else
        {
          localObject = getCumulativeParentSettings(paramSecureUser, localAutoEntitle, paramHashMap);
        }
      }
      if (!((com.ffusion.beans.autoentitle.AutoEntitle)localObject).getEnableAccounts()) {
        localAutoEntitle.setEnableAccounts(false);
      }
      if (!((com.ffusion.beans.autoentitle.AutoEntitle)localObject).getEnableAccountGroups()) {
        localAutoEntitle.setEnableAccountGroups(false);
      }
      if (!((com.ffusion.beans.autoentitle.AutoEntitle)localObject).getEnableACHCompanies()) {
        localAutoEntitle.setEnableACHCompanies(false);
      }
      if (!((com.ffusion.beans.autoentitle.AutoEntitle)localObject).getEnableLocations()) {
        localAutoEntitle.setEnableLocations(false);
      }
      if (!((com.ffusion.beans.autoentitle.AutoEntitle)localObject).getEnablePermissions()) {
        localAutoEntitle.setEnablePermissions(false);
      }
      if (!((com.ffusion.beans.autoentitle.AutoEntitle)localObject).getEnableWireTemplates()) {
        localAutoEntitle.setEnableWireTemplates(false);
      }
      if (localHashMap != null)
      {
        localEntitlementGroupMember = paramAutoEntitle.getEntitlementGroupMember();
        localEntitlementGroup = paramAutoEntitle.getEntitlementGroup();
        localInteger = null;
        if (localEntitlementGroup != null) {
          localInteger = new Integer(localEntitlementGroup.getGroupId());
        } else if (localEntitlementGroupMember != null) {
          localInteger = new Integer(localEntitlementGroupMember.getEntitlementGroupId());
        }
        if (localInteger != null) {
          localHashMap.put(localInteger, localAutoEntitle);
        }
      }
      return localAutoEntitle;
    }
    catch (Exception localException)
    {
      Object localObject = "The Settings requested for AutoEntitle " + paramAutoEntitle.toXML() + " could not be retrieved.  The error " + " received from the back end was " + stackTrace(localException);
      DebugLog.log((String)localObject);
      throw new AutoEntitleException(16, localException);
    }
  }
  
  public com.ffusion.beans.autoentitle.AutoEntitle getCumulativeParentSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws AutoEntitleException
  {
    String str1 = "AutoEntitleService.getCumulativeParentSettings";
    com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
    int i = 1;
    if ((paramHashMap.containsKey("HaveWriteLock")) && (paramHashMap.get("HaveWriteLock").equals("yes"))) {
      i = 0;
    }
    try
    {
      EntitlementGroup localEntitlementGroup = null;
      localObject1 = paramAutoEntitle.getEntitlementGroupMember();
      if (localObject1 != null)
      {
        if (i != 0) {
          localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        } else {
          localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroupNoCache(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        }
        localObject2 = new com.ffusion.beans.autoentitle.AutoEntitle();
        ((com.ffusion.beans.autoentitle.AutoEntitle)localObject2).setEntitlementGroup(localEntitlementGroup);
        return getCumulativeSettings(paramSecureUser, (com.ffusion.beans.autoentitle.AutoEntitle)localObject2, paramHashMap);
      }
      Object localObject2 = paramAutoEntitle.getEntitlementGroup();
      if (localObject2 != null)
      {
        String str2 = ((EntitlementGroup)localObject2).getEntGroupType();
        if ((str2.equals("Business")) || (str2.equals("Division")) || (str2.equals("Group")))
        {
          if (i != 0) {
            localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          } else {
            localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroupNoCache(((EntitlementGroup)localObject2).getParentId());
          }
          localAutoEntitle.setEntitlementGroup(localEntitlementGroup);
          return getCumulativeSettings(paramSecureUser, localAutoEntitle, paramHashMap);
        }
        if (str2.equals("BusinessAdmin"))
        {
          EntitlementCachedAdapter localEntitlementCachedAdapter = (EntitlementCachedAdapter)paramHashMap.get("EntitlementAdapter");
          EntitlementGroupMembers localEntitlementGroupMembers;
          if (localEntitlementCachedAdapter == null) {
            localEntitlementGroupMembers = com.ffusion.csil.core.Entitlements.getMembers(((EntitlementGroup)localObject2).getGroupId());
          } else {
            localEntitlementGroupMembers = localEntitlementCachedAdapter.getMembers(((EntitlementGroup)localObject2).getGroupId(), (ConnectionHolder)paramHashMap.get("MemberConnHolder"));
          }
          AffiliateBank localAffiliateBank = null;
          if ((localEntitlementGroupMembers != null) && (localEntitlementGroupMembers.size() > 0))
          {
            int j = Integer.parseInt(((EntitlementGroupMember)localEntitlementGroupMembers.get(0)).getId());
            Business localBusiness = null;
            if (paramHashMap != null)
            {
              localBusiness = (Business)paramHashMap.get("BUSINESS_OBJECT_KEY");
              if ((localBusiness == null) || (localBusiness.getIdValue() != j))
              {
                localBusiness = BusinessAdapter.getBusiness(j);
                paramHashMap.put("BUSINESS_OBJECT_KEY", localBusiness);
              }
            }
            else
            {
              localBusiness = BusinessAdapter.getBusiness(j);
            }
            int k = localBusiness.getAffiliateBankID();
            if (paramHashMap != null)
            {
              AffiliateBanks localAffiliateBanks = (AffiliateBanks)paramHashMap.get("AFFILIATE_BANK_KEY");
              if (localAffiliateBanks != null) {
                localAffiliateBank = localAffiliateBanks.getByID(k);
              }
            }
            if (localAffiliateBank == null) {
              localAffiliateBank = AffiliateBankAdapter.getAffiliateBankByID(paramSecureUser, k);
            }
          }
          localAutoEntitle.setAffiliateBank(localAffiliateBank);
          return getCumulativeSettings(paramSecureUser, localAutoEntitle, paramHashMap);
        }
        return paramAutoEntitle;
      }
      return paramAutoEntitle;
    }
    catch (Exception localException)
    {
      Object localObject1 = "The Settings requested for AutoEntitle " + paramAutoEntitle.toXML() + " could not be retrieved.  The error " + " received from the back end was " + stackTrace(localException);
      DebugLog.log((String)localObject1);
      throw new AutoEntitleException(16, localException);
    }
  }
  
  public void setSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle1, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle2, HashMap paramHashMap)
    throws AutoEntitleException
  {
    String str1 = "AutoEntitleService.setSettings";
    int i = paramAutoEntitle1.getObjectType();
    String str2 = paramAutoEntitle1.getObjectID();
    try
    {
      String str3 = AutoEntitleUtil.getPropertyString(paramAutoEntitle1);
      ObjectPropertyAdapter.modifyObjectProperty(i, str2, "AUTO_ENTITLE", str3);
    }
    catch (Exception localException)
    {
      String str4 = "The Settings requested for AutoEntitle " + paramAutoEntitle1.toXML() + " could not be set.  The error " + " received from the back end was " + stackTrace(localException);
      DebugLog.log(str4);
      throw new AutoEntitleException(15, localException);
    }
  }
  
  public void deleteSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws AutoEntitleException
  {
    String str1 = "AutoEntitleService.deleteSettings";
    int i = paramAutoEntitle.getObjectType();
    String str2 = paramAutoEntitle.getObjectID();
    try
    {
      ObjectPropertyAdapter.deleteObjectProperty(i, str2, "AUTO_ENTITLE");
    }
    catch (Exception localException)
    {
      String str3 = "The Settings requested for AutoEntitle " + paramAutoEntitle.toXML() + " could not be retrieved.  The error " + " received from the back end was " + stackTrace(localException);
      DebugLog.log(str3);
      throw new AutoEntitleException(17, localException);
    }
  }
  
  protected String stackTrace(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    paramThrowable.printStackTrace(localPrintWriter);
    return localStringWriter.toString();
  }
  
  public com.ffusion.csil.beans.entitlements.Entitlements filterEntitlements(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    Entitlement localEntitlement = null;
    String str = null;
    for (int i = 0; i < paramEntitlements.size(); i++)
    {
      localEntitlement = (Entitlement)paramEntitlements.get(i);
      str = localEntitlement.getOperationName();
      if ((str != null) && (str.length() >= 8))
      {
        if (!str.substring(str.length() - 8, str.length()).equals(" (admin)")) {
          localEntitlements.add(localEntitlement);
        }
      }
      else {
        localEntitlements.add(localEntitlement);
      }
    }
    return localEntitlements;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.autoentitle.AutoEntitleService
 * JD-Core Version:    0.7.0.1
 */