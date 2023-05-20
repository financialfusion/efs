package com.ffusion.services.profile;

import com.ffusion.banksim.interfaces.BSException;
import com.ffusion.banksim.proxy.BankSim;
import com.ffusion.beans.Contact;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.DirectoryDataAdapter;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.BusinessService4;
import com.ffusion.util.MapUtil;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class BusinessService
  implements BusinessService4
{
  public void initialize(String paramString)
    throws ProfileException
  {}
  
  public void setSettings(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException
  {}
  
  public String getSettings(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Business addBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BusinessAdapter.addBusiness(paramBusiness, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public Business getBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BusinessAdapter.getBusiness(paramBusiness.getIdValue());
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public void modifyBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      jdMethod_if(paramSecureUser, paramBusiness, paramHashMap);
      if ((paramHashMap.get("APPROVED") != null) && (paramHashMap.get("APPROVED").equals("yes"))) {
        a(paramSecureUser, paramBusiness, paramHashMap);
      }
      BusinessAdapter.modifyBusiness(paramBusiness, paramHashMap);
      int i = 0;
      if (paramHashMap != null)
      {
        String str = (String)paramHashMap.get("APPROVED");
        if ((str != null) && (str.equals("yes"))) {
          i = 1;
        }
      }
      if (i != 0)
      {
        int j = paramBusiness.getIdValue();
        com.ffusion.beans.accounts.Accounts localAccounts = AccountAdapter.getAccountsById(j);
        Account localAccount = null;
        Contact localContact = null;
        Iterator localIterator = localAccounts.iterator();
        while (localIterator.hasNext())
        {
          localAccount = (Account)localIterator.next();
          localContact = new Contact();
          localContact.set(paramBusiness);
          localContact.clear();
          localAccount.setContact(localContact);
          AccountAdapter.modifyAccount(localAccount, j);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw new ProfileException(2);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  private EntitlementGroupMember a(String paramString)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = null;
    if (paramString.length() > 0)
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setMemberType("USER");
      localEntitlementGroupMember.setMemberSubType(String.valueOf(1));
      localEntitlementGroupMember.setId(paramString);
      localEntitlementGroupMember = com.ffusion.csil.core.Entitlements.getMember(localEntitlementGroupMember);
    }
    return localEntitlementGroupMember;
  }
  
  private void a(Entitlement paramEntitlement, EntitlementGroupMember paramEntitlementGroupMember, boolean paramBoolean1, boolean paramBoolean2, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements1, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements2)
    throws CSILException
  {
    if (com.ffusion.csil.core.Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement))
    {
      if (paramBoolean1) {
        paramEntitlements1.add(paramEntitlement);
      }
      if (paramBoolean2) {
        paramEntitlements2.add(paramEntitlement);
      }
    }
  }
  
  private void jdMethod_if(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    if (paramHashMap == null) {
      return;
    }
    String str1 = MapUtil.getStringValue(paramHashMap, "primaryContactPermissions", "");
    String str2 = MapUtil.getStringValue(paramHashMap, "secondaryContactPermissions", "");
    String str3 = MapUtil.getStringValue(paramHashMap, "PRIMARY_CONTACT_ID", "");
    String str4 = MapUtil.getStringValue(paramHashMap, "SECONDARY_CONTACT_ID", "");
    if (str4.equals("none")) {
      str4 = "";
    }
    EntitlementGroupMember localEntitlementGroupMember1 = paramBusiness.getEntitlementGroupMember();
    EntitlementGroupMember localEntitlementGroupMember2 = a(str3);
    EntitlementGroupMember localEntitlementGroupMember3 = a(str4);
    if ((str1.equals("edit")) && (paramBusiness.getPrimaryContactPermsValue() != 3))
    {
      if (str3.length() == 0) {
        throw new CSILException(3003);
      }
      com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlementGroupMember2, new com.ffusion.csil.beans.entitlements.Entitlements());
    }
    if ((str2.equals("edit")) && (paramBusiness.getSecondaryContactPermsValue() != 3) && (str4.length() != 0)) {
      com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlementGroupMember3, new com.ffusion.csil.beans.entitlements.Entitlements());
    }
    boolean bool1 = false;
    boolean bool2 = false;
    if (((str1.equals("create")) || (str1.equals("edit"))) && (paramBusiness.getPrimaryContactPermsValue() == 1) && (str3.length() > 0)) {
      bool1 = true;
    }
    if (((str2.equals("create")) || (str2.equals("edit"))) && (paramBusiness.getSecondaryContactPermsValue() == 1) && (str4.length() > 0)) {
      bool2 = true;
    }
    if ((bool1) || (bool2))
    {
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = new com.ffusion.csil.beans.entitlements.Entitlements();
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
      EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties("category", "per user");
      EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties("component", "Administration");
      com.ffusion.beans.accounts.Accounts localAccounts = com.ffusion.csil.core.Accounts.getAccountsById(paramSecureUser, paramBusiness.getIdValue(), paramHashMap);
      BusinessAccountGroups localBusinessAccountGroups = AccountGroup.getAccountGroups(paramSecureUser, paramBusiness.getIdValue(), null);
      Iterator localIterator = localEntitlementTypePropertyLists1.iterator();
      while (localIterator.hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          Object localObject1;
          if (localEntitlementTypePropertyList.isPropertySet("category", "cross account"))
          {
            localObject1 = new Entitlement(localEntitlementTypePropertyList.getOperationName(), null, null);
            a((Entitlement)localObject1, localEntitlementGroupMember1, bool1, bool2, localEntitlements1, localEntitlements2);
          }
          else if ((localEntitlementTypePropertyList.isPropertySet("category", "per account")) || (localEntitlementTypePropertyList.getOperationName().equals("Access")))
          {
            localObject1 = localAccounts.iterator();
            Object localObject3;
            while (((Iterator)localObject1).hasNext())
            {
              localObject2 = (Account)((Iterator)localObject1).next();
              localObject3 = new Entitlement(localEntitlementTypePropertyList.getOperationName(), "Account", EntitlementsUtil.getEntitlementObjectId((Account)localObject2));
              a((Entitlement)localObject3, localEntitlementGroupMember1, bool1, bool2, localEntitlements1, localEntitlements2);
            }
            Object localObject2 = localBusinessAccountGroups.iterator();
            while (((Iterator)localObject2).hasNext())
            {
              localObject3 = (BusinessAccountGroup)((Iterator)localObject2).next();
              Entitlement localEntitlement = new Entitlement(localEntitlementTypePropertyList.getOperationName(), "AccountGroup", String.valueOf(((BusinessAccountGroup)localObject3).getId()));
              a(localEntitlement, localEntitlementGroupMember1, bool1, bool2, localEntitlements1, localEntitlements2);
            }
          }
          else
          {
            if (localEntitlementTypePropertyList.isPropertySet("category", "per wire template")) {
              continue;
            }
            if ((!localEntitlementTypePropertyList.isPropertySet("category", "cross ACH company")) && (!localEntitlementTypePropertyList.isPropertySet("category", "per ACH company")))
            {
              if (localEntitlementTypePropertyLists2.getByOperationName(localEntitlementTypePropertyList.getOperationName()) != null) {
                continue;
              }
              localObject1 = new Entitlement(localEntitlementTypePropertyList.getOperationName(), null, null);
              a((Entitlement)localObject1, localEntitlementGroupMember1, bool1, bool2, localEntitlements1, localEntitlements2);
            }
          }
          if (localEntitlementTypePropertyList.isPropertySet("category", "cross ACH company"))
          {
            localObject1 = new Entitlement(localEntitlementTypePropertyList.getOperationName(), null, null);
            a((Entitlement)localObject1, localEntitlementGroupMember1, bool1, bool2, localEntitlements1, localEntitlements2);
          }
        }
      }
      if (localEntitlements1.size() > 0) {
        com.ffusion.csil.core.Entitlements.setRestrictedEntitlementsUnsafe(localEntitlementGroupMember2, localEntitlements1);
      }
      if (localEntitlements2.size() > 0) {
        com.ffusion.csil.core.Entitlements.setRestrictedEntitlementsUnsafe(localEntitlementGroupMember3, localEntitlements2);
      }
    }
  }
  
  public void addAdditionalData(SecureUser paramSecureUser, Business paramBusiness, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    if ((paramString1 == null) || (paramString1.equals("DIRECTORY_ID"))) {
      throw new ProfileException(3750);
    }
    try
    {
      DirectoryDataAdapter.addAdditionalData(paramBusiness.getIdValue(), paramString1, paramString2);
    }
    catch (Exception localException)
    {
      throw new ProfileException(3503, localException);
    }
  }
  
  public String getAdditionalData(SecureUser paramSecureUser, Business paramBusiness, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    if ((paramString != null) && (paramString.equals("DIRECTORY_ID"))) {
      throw new ProfileException(3750);
    }
    try
    {
      return DirectoryDataAdapter.getAdditionalData(paramBusiness.getIdValue(), paramString);
    }
    catch (Exception localException)
    {
      throw new ProfileException(3503, localException);
    }
  }
  
  public Business deactivateBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      paramBusiness.setStatus(String.valueOf("2"));
      modifyBusiness(paramSecureUser, paramBusiness, paramHashMap);
      return paramBusiness;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public Business reactivateBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      paramBusiness.setStatus(String.valueOf("1"));
      modifyBusiness(paramSecureUser, paramBusiness, paramHashMap);
      return paramBusiness;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public void purgeDeactivatedBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    throw new ProfileException(13);
  }
  
  public void purgeAllDeactivatedBusinesses(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    throw new ProfileException(13);
  }
  
  public Businesses getBusinesses(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BusinessAdapter.getBusinesses(Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public Businesses getFilteredBusinesses(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BusinessAdapter.getFilteredBusinesses(paramBusiness, paramBusinessEmployee, Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public int getFilteredBusinessesCount(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BusinessAdapter.getFilteredBusinessesCount(paramBusiness, paramBusinessEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public void enrollBusiness(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      addBusiness(paramSecureUser, paramBusiness, paramHashMap);
      return;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public Businesses getBusinessesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    return BusinessAdapter.getBusinessesByIds(paramArrayList, Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
  }
  
  public void addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      HistoryAdapter.addHistory(paramHistories);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      Histories localHistories = HistoryAdapter.getHistory(paramHistory, paramCalendar1, paramCalendar2);
      localHistories.setLocale(paramSecureUser.getLocale());
      return localHistories;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public User getBusinessInfoFromBackend(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BankSim.signOn(paramString1, paramString2);
    }
    catch (BSException localBSException)
    {
      int i = 0;
      switch (localBSException.getErrorCode())
      {
      case 1006: 
        i = 8;
        break;
      case 1008: 
        i = 6;
        break;
      default: 
        i = 2;
      }
      throw new ProfileException(i);
    }
  }
  
  public ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    return BusinessAdapter.getBankIdsByServicesPackage(paramSecureUser, paramInt, paramHashMap);
  }
  
  public ArrayList getTransactionGroups(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    return BusinessAdapter.getTransactionGroups(paramInt, paramHashMap);
  }
  
  public String getTypeCodesForGroup(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    return BusinessAdapter.getTypeCodesForGroup(paramInt, paramString, paramHashMap);
  }
  
  public void addTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    BusinessAdapter.addTransactionGroup(paramInt, paramString1, paramString2, paramHashMap);
  }
  
  public void deleteTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    BusinessAdapter.deleteTransactionGroup(paramInt, paramString, paramHashMap);
  }
  
  public void modifyTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws ProfileException
  {
    BusinessAdapter.modifyTransactionGroup(paramInt, paramString1, paramString2, paramString3, paramHashMap);
  }
  
  public boolean uniqueCustId(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    return BusinessAdapter.uniqueCustId(paramBusiness);
  }
  
  protected int mapError(Exception paramException)
  {
    int i = 2;
    paramException.printStackTrace(System.out);
    if ((paramException instanceof ProfileException))
    {
      ProfileException localProfileException = (ProfileException)paramException;
      DebugLog.log("mapProfileException: " + localProfileException.where + " : " + localProfileException.why + " : " + localProfileException.code);
      switch (localProfileException.code)
      {
      case 0: 
        i = 0;
        break;
      case 1: 
        i = 4005;
        break;
      case 12: 
        i = 0;
        break;
      case 4: 
        i = 4011;
        break;
      case 4022: 
        i = 4022;
        break;
      case 4023: 
        i = 4023;
        break;
      case 4025: 
        i = 4025;
        break;
      case 9: 
        i = 8;
        break;
      case 3503: 
      default: 
        i = 2;
      }
    }
    return i;
  }
  
  private void a(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      EntitlementGroup localEntitlementGroup1 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(paramBusiness.getEntitlementGroupIdValue());
      paramHashMap.put("AUTOENTITLE_SETTINGS_KEY", AutoEntitleAdmin.getCumulativeSettings(paramSecureUser, localEntitlementGroup1, paramHashMap));
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = AutoEntitleUtil.buildRestrictedEntitlementsList(paramSecureUser, localEntitlementGroup1, "per company", paramHashMap);
      EntitlementGroups localEntitlementGroups = com.ffusion.csil.core.Entitlements.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "Business");
      EntitlementGroup localEntitlementGroup2 = null;
      if (localEntitlementGroups.size() > 0) {
        localEntitlementGroup2 = (EntitlementGroup)localEntitlementGroups.get(0);
      } else {
        throw new ProfileException(2);
      }
      AutoEntitleAdmin.restrictFeatures(paramSecureUser, localEntitlementGroup2, localEntitlements, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw new ProfileException(2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.BusinessService
 * JD-Core Version:    0.7.0.1
 */