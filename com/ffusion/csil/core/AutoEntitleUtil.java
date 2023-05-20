package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.autoentitle.AutoEntitle;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.handlers.ACH;
import com.ffusion.csil.handlers.AccountGroup;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.csil.handlers.AffiliateBankAdmin;
import com.ffusion.csil.handlers.CashCon;
import com.ffusion.csil.handlers.WireHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AutoEntitleUtil
{
  public static final String AFFILIATE_BANK_KEY = "AFFILIATE_BANK_KEY";
  public static final String AUTOENTITLE_FLAG_KEY = "AUTOENTITLE_FLAG_KEY";
  public static final String AUTOENTITLE_SETTINGS_KEY = "AUTOENTITLE_SETTINGS_KEY";
  public static final String PROCESS_LOCATIONS_KEY = "PROCESS_LOCATIONS_KEY";
  public static final String AUTOENTITLE_RESTRICTED_ENTS_KEY = "AUTOENTITLE_RESTRICTED_ENTS_KEY";
  public static final String ENT_GROUP_CHANGED_KEY = "ENT_GROUP_CHANGED_KEY";
  
  public static String getPropertyString(AutoEntitle paramAutoEntitle)
    throws CSILException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (!paramAutoEntitle.getEnableAccounts()) {
      localStringBuffer.append("ACCOUNTS");
    }
    if (!paramAutoEntitle.getEnableAccountGroups())
    {
      if (localStringBuffer.length() > 0) {
        localStringBuffer.append(':');
      }
      localStringBuffer.append("ACCOUNT_GROUPS");
    }
    if (!paramAutoEntitle.getEnableACHCompanies())
    {
      if (localStringBuffer.length() > 0) {
        localStringBuffer.append(':');
      }
      localStringBuffer.append("ACH_COMPANIES");
    }
    if (!paramAutoEntitle.getEnableLocations())
    {
      if (localStringBuffer.length() > 0) {
        localStringBuffer.append(':');
      }
      localStringBuffer.append("LOCATIONS");
    }
    if (!paramAutoEntitle.getEnablePermissions())
    {
      if (localStringBuffer.length() > 0) {
        localStringBuffer.append(':');
      }
      localStringBuffer.append("PERMISSIONS");
    }
    if (!paramAutoEntitle.getEnableWireTemplates())
    {
      if (localStringBuffer.length() > 0) {
        localStringBuffer.append(':');
      }
      localStringBuffer.append("WIRE_TEMPLATES");
    }
    return localStringBuffer.toString();
  }
  
  public static void setFromPropertyString(AutoEntitle paramAutoEntitle, String paramString)
    throws CSILException
  {
    paramAutoEntitle.setEnableAccounts(true);
    paramAutoEntitle.setEnableAccountGroups(true);
    paramAutoEntitle.setEnableACHCompanies(true);
    paramAutoEntitle.setEnableLocations(true);
    paramAutoEntitle.setEnablePermissions(true);
    paramAutoEntitle.setEnableWireTemplates(true);
    paramAutoEntitle.setEnableAccounts(true);
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return;
    }
    if (paramString.indexOf("ACCOUNTS") != -1) {
      paramAutoEntitle.setEnableAccounts(false);
    }
    if (paramString.indexOf("ACCOUNT_GROUPS") != -1) {
      paramAutoEntitle.setEnableAccountGroups(false);
    }
    if (paramString.indexOf("ACH_COMPANIES") != -1) {
      paramAutoEntitle.setEnableACHCompanies(false);
    }
    if (paramString.indexOf("LOCATIONS") != -1) {
      paramAutoEntitle.setEnableLocations(false);
    }
    if (paramString.indexOf("PERMISSIONS") != -1) {
      paramAutoEntitle.setEnablePermissions(false);
    }
    if (paramString.indexOf("WIRE_TEMPLATES") != -1) {
      paramAutoEntitle.setEnableWireTemplates(false);
    }
  }
  
  public static boolean isPermissionEnabled(AutoEntitle paramAutoEntitle, int paramInt)
  {
    if (paramInt == 1) {
      return paramAutoEntitle.getEnableAccounts();
    }
    if (paramInt == 6) {
      return paramAutoEntitle.getEnableAccountGroups();
    }
    if (paramInt == 2) {
      return paramAutoEntitle.getEnableACHCompanies();
    }
    if (paramInt == 3) {
      return paramAutoEntitle.getEnableWireTemplates();
    }
    if (paramInt == 4) {
      return paramAutoEntitle.getEnableLocations();
    }
    if (paramInt == 5) {
      return paramAutoEntitle.getEnablePermissions();
    }
    return false;
  }
  
  public static boolean isAnyPermissionEnabled(AutoEntitle paramAutoEntitle)
  {
    return (paramAutoEntitle.getEnableAccounts()) || (paramAutoEntitle.getEnableAccountGroups()) || (paramAutoEntitle.getEnableACHCompanies()) || (paramAutoEntitle.getEnableWireTemplates()) || (paramAutoEntitle.getEnableLocations()) || (paramAutoEntitle.getEnablePermissions());
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements buildRestrictedEntitlementsList(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    Entitlement localEntitlement = null;
    boolean bool1 = ((Boolean)paramHashMap.get("AUTOENTITLE_FLAG_KEY")).booleanValue();
    AutoEntitle localAutoEntitle = (AutoEntitle)paramHashMap.get("AUTOENTITLE_SETTINGS_KEY");
    boolean bool2 = true;
    if (paramHashMap.containsKey("PROCESS_LOCATIONS_KEY")) {
      bool2 = ((Boolean)paramHashMap.get("PROCESS_LOCATIONS_KEY")).booleanValue();
    }
    HashMap localHashMap = new HashMap();
    int i = Business.getProfileID(paramEntitlementGroup);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Accounts localAccounts = null;
    Account localAccount = null;
    String str1;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 1)))
    {
      if (localAccounts == null) {
        localAccounts = AccountHandler.getAccountsById(paramSecureUser, i, paramHashMap);
      }
      localObject1 = localAccounts.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localAccount = (Account)((Iterator)localObject1).next();
        str1 = EntitlementsUtil.getEntitlementObjectId(localAccount);
        localEntitlement = new Entitlement("Access", "Account", str1);
        if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
          localEntitlements.add(localEntitlement);
        }
      }
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    String str2;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 5)))
    {
      if (localEntitlementTypePropertyLists == null) {
        localEntitlementTypePropertyLists = a(paramString, "per account");
      }
      if (localAccounts == null) {
        localAccounts = AccountHandler.getAccountsById(paramSecureUser, i, paramHashMap);
      }
      localObject1 = localAccounts.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localAccount = (Account)((Iterator)localObject1).next();
        str1 = EntitlementsUtil.getEntitlementObjectId(localAccount);
        localObject2 = localEntitlementTypePropertyLists.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject2).next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str2 = localEntitlementTypePropertyList.getOperationName();
            localEntitlement = new Entitlement(str2, "Account", str1);
            if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
              localEntitlements.add(localEntitlement);
            }
          }
        }
      }
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    localAccount = null;
    localAccounts = null;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 6)))
    {
      if (localObject1 == null) {
        localObject1 = AccountGroup.getAccountGroups(paramSecureUser, i, paramHashMap);
      }
      localObject3 = ((BusinessAccountGroups)localObject1).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (BusinessAccountGroup)((Iterator)localObject3).next();
        str1 = EntitlementsUtil.getEntitlementObjectId((BusinessAccountGroup)localObject2);
        localEntitlement = new Entitlement("Access", "AccountGroup", str1);
        if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
          localEntitlements.add(localEntitlement);
        }
      }
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 5)))
    {
      if (localEntitlementTypePropertyLists == null) {
        localEntitlementTypePropertyLists = a(paramString, "per account");
      }
      if (localObject1 == null) {
        localObject1 = AccountGroup.getAccountGroups(paramSecureUser, i, paramHashMap);
      }
      localObject3 = ((BusinessAccountGroups)localObject1).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (BusinessAccountGroup)((Iterator)localObject3).next();
        str1 = EntitlementsUtil.getEntitlementObjectId((BusinessAccountGroup)localObject2);
        Iterator localIterator1 = localEntitlementTypePropertyLists.iterator();
        while (localIterator1.hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator1.next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str2 = localEntitlementTypePropertyList.getOperationName();
            localEntitlement = new Entitlement(str2, "AccountGroup", str1);
            if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
              localEntitlements.add(localEntitlement);
            }
          }
        }
      }
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    localObject2 = null;
    localObject1 = null;
    localEntitlementTypePropertyLists = null;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 5)))
    {
      localEntitlementTypePropertyLists = a(paramString);
      localObject3 = localEntitlementTypePropertyLists.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject3).next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          str2 = localEntitlementTypePropertyList.getOperationName();
          localEntitlement = new Entitlement(str2, null, null);
          if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
            localEntitlements.add(localEntitlement);
          }
        }
      }
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 2)))
    {
      localEntitlementTypePropertyLists = a(paramString, "cross ACH company");
      localObject3 = localEntitlementTypePropertyLists.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject3).next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          str2 = localEntitlementTypePropertyList.getOperationName();
          localEntitlement = new Entitlement(str2, null, null);
          if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
            localEntitlements.add(localEntitlement);
          }
        }
      }
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    Object localObject5;
    Object localObject7;
    Object localObject8;
    Object localObject9;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 2)))
    {
      localEntitlementTypePropertyLists = a(paramString, "per ACH company");
      localObject3 = Business.jdMethod_int(paramSecureUser, i, localHashMap);
      int j = ((com.ffusion.beans.business.Business)localObject3).getAffiliateBankID();
      localObject5 = AffiliateBankAdmin.getAffiliateBankByID(paramSecureUser, j, localHashMap);
      localObject7 = ((AffiliateBank)localObject5).getFIBPWID();
      localObject8 = ACH.getACHCompanies(paramSecureUser, ((com.ffusion.beans.business.Business)localObject3).getId(), (String)localObject7, localHashMap);
      Iterator localIterator2 = ((ACHCompanies)localObject8).iterator();
      while (localIterator2.hasNext())
      {
        localObject9 = (ACHCompany)localIterator2.next();
        Iterator localIterator3 = localEntitlementTypePropertyLists.iterator();
        while (localIterator3.hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator3.next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str2 = localEntitlementTypePropertyList.getOperationName();
            localEntitlement = new Entitlement(str2, "ACHCompany", ((ACHCompany)localObject9).getCompanyID());
            if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
              localEntitlements.add(localEntitlement);
            }
          }
        }
      }
      localObject8 = null;
      localObject9 = null;
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    Object localObject4;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 3)))
    {
      localEntitlementTypePropertyLists = a(paramString, "per wire template");
      localObject3 = WireHandler.getAllWireTemplates(paramSecureUser, i, localHashMap);
      localObject5 = ((WireTransfers)localObject3).iterator();
      while (((Iterator)localObject5).hasNext())
      {
        localObject4 = (WireTransfer)((Iterator)localObject5).next();
        localEntitlement = new Entitlement(null, "Wire Template", ((WireTransfer)localObject4).getID());
        if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
          localEntitlements.add(localEntitlement);
        }
        localObject7 = localEntitlementTypePropertyLists.iterator();
        while (((Iterator)localObject7).hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject7).next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str2 = localEntitlementTypePropertyList.getOperationName();
            localEntitlement = new Entitlement(str2, "Wire Template", ((WireTransfer)localObject4).getID());
            if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
              localEntitlements.add(localEntitlement);
            }
          }
        }
      }
      localObject3 = null;
      localObject4 = null;
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    if ((bool2) && ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 4))))
    {
      localEntitlementTypePropertyLists = a(paramString, "per location");
      localObject3 = new LocationSearchCriteria();
      localObject4 = new LocationSearchResults();
      if (paramEntitlementGroup.getEntGroupType().equals("Division"))
      {
        ((LocationSearchCriteria)localObject3).setDivisionID(paramEntitlementGroup.getGroupId());
        ((LocationSearchResults)localObject4).addAll(CashCon.getLocations(paramSecureUser, (LocationSearchCriteria)localObject3, localHashMap));
      }
      else if ((paramEntitlementGroup.getEntGroupType().equals("Business")) || (paramEntitlementGroup.getEntGroupType().equals("BusinessAdmin")))
      {
        int k = paramEntitlementGroup.getGroupId();
        if (paramEntitlementGroup.getEntGroupType().equals("BusinessAdmin"))
        {
          localObject7 = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId());
          k = ((EntitlementGroup)((EntitlementGroups)localObject7).get(0)).getGroupId();
        }
        localObject7 = Entitlements.getChildrenEntitlementGroups(k);
        localObject9 = ((EntitlementGroups)localObject7).iterator();
        while (((Iterator)localObject9).hasNext())
        {
          localObject8 = (EntitlementGroup)((Iterator)localObject9).next();
          ((LocationSearchCriteria)localObject3).setDivisionID(((EntitlementGroup)localObject8).getGroupId());
          ((LocationSearchResults)localObject4).addAll(CashCon.getLocations(paramSecureUser, (LocationSearchCriteria)localObject3, localHashMap));
        }
      }
      else
      {
        for (localObject6 = Entitlements.getEntitlementGroup(paramEntitlementGroup.getParentId()); (localObject6 != null) && (!((EntitlementGroup)localObject6).getEntGroupType().equals("Division")); localObject6 = Entitlements.getEntitlementGroup(((EntitlementGroup)localObject6).getParentId())) {}
        if (localObject6 != null)
        {
          ((LocationSearchCriteria)localObject3).setDivisionID(((EntitlementGroup)localObject6).getGroupId());
          ((LocationSearchResults)localObject4).addAll(CashCon.getLocations(paramSecureUser, (LocationSearchCriteria)localObject3, localHashMap));
        }
      }
      localObject7 = ((LocationSearchResults)localObject4).iterator();
      while (((Iterator)localObject7).hasNext())
      {
        localObject6 = (LocationSearchResult)((Iterator)localObject7).next();
        localObject8 = localEntitlementTypePropertyLists.iterator();
        while (((Iterator)localObject8).hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject8).next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str2 = localEntitlementTypePropertyList.getOperationName();
            localEntitlement = new Entitlement(str2, "Location", ((LocationSearchResult)localObject6).getLocationBPWID());
            if (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)) {
              localEntitlements.add(localEntitlement);
            }
          }
        }
      }
      Object localObject6 = null;
      localObject4 = null;
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    return localEntitlements;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements buildRestrictedAdminEntitlementsList(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    return a(paramSecureUser, paramEntitlementGroupMember, null, paramHashMap);
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements buildRestrictedAdminEntitlementsList(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    return a(paramSecureUser, null, paramEntitlementGroup, paramHashMap);
  }
  
  private static com.ffusion.csil.beans.entitlements.Entitlements a(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    String str1 = "per user";
    EntitlementGroup localEntitlementGroup;
    if (paramEntitlementGroupMember != null)
    {
      paramEntitlementGroup = null;
      localEntitlementGroup = Entitlements.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
    }
    else
    {
      localEntitlementGroup = paramEntitlementGroup;
    }
    Entitlement localEntitlement = null;
    boolean bool1 = ((Boolean)paramHashMap.get("AUTOENTITLE_FLAG_KEY")).booleanValue();
    AutoEntitle localAutoEntitle = (AutoEntitle)paramHashMap.get("AUTOENTITLE_SETTINGS_KEY");
    boolean bool2 = true;
    if (paramHashMap.containsKey("PROCESS_LOCATIONS_KEY")) {
      bool2 = ((Boolean)paramHashMap.get("PROCESS_LOCATIONS_KEY")).booleanValue();
    }
    HashMap localHashMap = new HashMap();
    int i = Business.getProfileID(localEntitlementGroup);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Accounts localAccounts = null;
    Account localAccount = null;
    String str2;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 1)))
    {
      if (localAccounts == null) {
        localAccounts = AccountHandler.getAccountsById(paramSecureUser, i, paramHashMap);
      }
      localObject1 = localAccounts.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localAccount = (Account)((Iterator)localObject1).next();
        str2 = EntitlementsUtil.getEntitlementObjectId(localAccount);
        localEntitlement = new Entitlement("Access (admin)", "Account", str2);
        if (((paramEntitlementGroupMember != null) && (Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) || ((paramEntitlementGroup != null) && (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)))) {
          localEntitlements.add(localEntitlement);
        }
      }
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    String str3;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 5)))
    {
      if (localEntitlementTypePropertyLists == null) {
        localEntitlementTypePropertyLists = a(str1, "per account");
      }
      if (localAccounts == null) {
        localAccounts = AccountHandler.getAccountsById(paramSecureUser, i, paramHashMap);
      }
      localObject1 = localAccounts.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localAccount = (Account)((Iterator)localObject1).next();
        str2 = EntitlementsUtil.getEntitlementObjectId(localAccount);
        localObject2 = localEntitlementTypePropertyLists.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject2).next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str3 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
            if ((str3 != null) && (str3.length() > 0))
            {
              localEntitlement = new Entitlement(str3, "Account", str2);
              if (((paramEntitlementGroupMember != null) && (Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) || ((paramEntitlementGroup != null) && (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)))) {
                localEntitlements.add(localEntitlement);
              }
            }
          }
        }
      }
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    localAccount = null;
    localAccounts = null;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 6)))
    {
      if (localObject1 == null) {
        localObject1 = AccountGroup.getAccountGroups(paramSecureUser, i, paramHashMap);
      }
      localObject3 = ((BusinessAccountGroups)localObject1).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (BusinessAccountGroup)((Iterator)localObject3).next();
        str2 = EntitlementsUtil.getEntitlementObjectId((BusinessAccountGroup)localObject2);
        localEntitlement = new Entitlement("Access (admin)", "AccountGroup", str2);
        if (((paramEntitlementGroupMember != null) && (Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) || ((paramEntitlementGroup != null) && (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)))) {
          localEntitlements.add(localEntitlement);
        }
      }
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 5)))
    {
      if (localEntitlementTypePropertyLists == null) {
        localEntitlementTypePropertyLists = a(str1, "per account");
      }
      if (localObject1 == null) {
        localObject1 = AccountGroup.getAccountGroups(paramSecureUser, i, paramHashMap);
      }
      localObject3 = ((BusinessAccountGroups)localObject1).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (BusinessAccountGroup)((Iterator)localObject3).next();
        str2 = EntitlementsUtil.getEntitlementObjectId((BusinessAccountGroup)localObject2);
        Iterator localIterator1 = localEntitlementTypePropertyLists.iterator();
        while (localIterator1.hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator1.next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str3 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
            if ((str3 != null) && (str3.length() > 0))
            {
              localEntitlement = new Entitlement(str3, "AccountGroup", str2);
              if (((paramEntitlementGroupMember != null) && (Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) || ((paramEntitlementGroup != null) && (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)))) {
                localEntitlements.add(localEntitlement);
              }
            }
          }
        }
      }
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    localObject2 = null;
    localObject1 = null;
    localEntitlementTypePropertyLists = null;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 5)))
    {
      localEntitlementTypePropertyLists = a(str1);
      localObject3 = localEntitlementTypePropertyLists.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject3).next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          str3 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
          if ((str3 != null) && (str3.length() > 0))
          {
            localEntitlement = new Entitlement(str3, null, null);
            if (((paramEntitlementGroupMember != null) && (Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) || ((paramEntitlementGroup != null) && (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)))) {
              localEntitlements.add(localEntitlement);
            }
          }
        }
      }
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 2)))
    {
      localEntitlementTypePropertyLists = a(str1, "cross ACH company");
      localObject3 = localEntitlementTypePropertyLists.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject3).next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          str3 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
          if ((str3 != null) && (str3.length() > 0))
          {
            localEntitlement = new Entitlement(str3, null, null);
            if (((paramEntitlementGroupMember != null) && (Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) || ((paramEntitlementGroup != null) && (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)))) {
              localEntitlements.add(localEntitlement);
            }
          }
        }
      }
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    Object localObject5;
    Object localObject6;
    Object localObject7;
    if ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 2)))
    {
      localEntitlementTypePropertyLists = a(str1, "per ACH company");
      localObject3 = Business.jdMethod_int(paramSecureUser, i, localHashMap);
      int j = ((com.ffusion.beans.business.Business)localObject3).getAffiliateBankID();
      AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID(paramSecureUser, j, localHashMap);
      localObject5 = localAffiliateBank.getFIBPWID();
      localObject6 = ACH.getACHCompanies(paramSecureUser, ((com.ffusion.beans.business.Business)localObject3).getId(), (String)localObject5, localHashMap);
      Iterator localIterator2 = ((ACHCompanies)localObject6).iterator();
      while (localIterator2.hasNext())
      {
        localObject7 = (ACHCompany)localIterator2.next();
        Iterator localIterator3 = localEntitlementTypePropertyLists.iterator();
        while (localIterator3.hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator3.next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str3 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
            if ((str3 != null) && (str3.length() > 0))
            {
              localEntitlement = new Entitlement(str3, "ACHCompany", ((ACHCompany)localObject7).getCompanyID());
              if (((paramEntitlementGroupMember != null) && (Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) || ((paramEntitlementGroup != null) && (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)))) {
                localEntitlements.add(localEntitlement);
              }
            }
          }
        }
      }
      localObject6 = null;
      localObject7 = null;
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    if ((bool2) && ((!bool1) || (!isPermissionEnabled(localAutoEntitle, 4))))
    {
      localEntitlementTypePropertyLists = a(str1, "per location");
      localObject3 = new LocationSearchCriteria();
      LocationSearchResults localLocationSearchResults = new LocationSearchResults();
      if (localEntitlementGroup.getEntGroupType().equals("Division"))
      {
        ((LocationSearchCriteria)localObject3).setDivisionID(localEntitlementGroup.getGroupId());
        localLocationSearchResults.addAll(CashCon.getLocations(paramSecureUser, (LocationSearchCriteria)localObject3, localHashMap));
      }
      else if ((localEntitlementGroup.getEntGroupType().equals("Business")) || (localEntitlementGroup.getEntGroupType().equals("BusinessAdmin")))
      {
        int k = localEntitlementGroup.getGroupId();
        if (localEntitlementGroup.getEntGroupType().equals("BusinessAdmin"))
        {
          localObject5 = Entitlements.getChildrenEntitlementGroups(localEntitlementGroup.getGroupId());
          k = ((EntitlementGroup)((EntitlementGroups)localObject5).get(0)).getGroupId();
        }
        localObject5 = Entitlements.getChildrenEntitlementGroups(k);
        localObject7 = ((EntitlementGroups)localObject5).iterator();
        while (((Iterator)localObject7).hasNext())
        {
          localObject6 = (EntitlementGroup)((Iterator)localObject7).next();
          ((LocationSearchCriteria)localObject3).setDivisionID(((EntitlementGroup)localObject6).getGroupId());
          localLocationSearchResults.addAll(CashCon.getLocations(paramSecureUser, (LocationSearchCriteria)localObject3, localHashMap));
        }
      }
      else
      {
        for (localObject4 = Entitlements.getEntitlementGroup(localEntitlementGroup.getParentId()); (localObject4 != null) && (!((EntitlementGroup)localObject4).getEntGroupType().equals("Division")); localObject4 = Entitlements.getEntitlementGroup(((EntitlementGroup)localObject4).getParentId())) {}
        if (localObject4 != null)
        {
          ((LocationSearchCriteria)localObject3).setDivisionID(((EntitlementGroup)localObject4).getGroupId());
          localLocationSearchResults.addAll(CashCon.getLocations(paramSecureUser, (LocationSearchCriteria)localObject3, localHashMap));
        }
      }
      localObject5 = localLocationSearchResults.iterator();
      while (((Iterator)localObject5).hasNext())
      {
        localObject4 = (LocationSearchResult)((Iterator)localObject5).next();
        localObject6 = localEntitlementTypePropertyLists.iterator();
        while (((Iterator)localObject6).hasNext())
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject6).next();
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
          {
            str3 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
            if ((str3 != null) && (str3.length() > 0))
            {
              localEntitlement = new Entitlement(str3, "Location", ((LocationSearchResult)localObject4).getLocationBPWID());
              if (((paramEntitlementGroupMember != null) && (Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) || ((paramEntitlementGroup != null) && (Entitlements.checkEntitlement(paramEntitlementGroup.getGroupId(), localEntitlement)))) {
                localEntitlements.add(localEntitlement);
              }
            }
          }
        }
      }
      Object localObject4 = null;
      localLocationSearchResults = null;
      localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyList = null;
      localEntitlement = null;
    }
    return localEntitlements;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements buildPerAccountEntitlementsList(Accounts paramAccounts, String paramString)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = a(paramString, "per account");
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Iterator localIterator1 = paramAccounts.iterator();
    while (localIterator1.hasNext())
    {
      Account localAccount = (Account)localIterator1.next();
      String str1 = EntitlementsUtil.getEntitlementObjectId(localAccount);
      Iterator localIterator2 = localEntitlementTypePropertyLists.iterator();
      while (localIterator2.hasNext())
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator2.next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          String str2 = localEntitlementTypePropertyList.getOperationName();
          localEntitlements.add(new Entitlement(str2, "Account", str1));
        }
      }
    }
    return localEntitlements;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements buildPerAccountEntitlementsList(BusinessAccountGroups paramBusinessAccountGroups, String paramString)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = a(paramString, "per account");
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Iterator localIterator1 = paramBusinessAccountGroups.iterator();
    while (localIterator1.hasNext())
    {
      BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)localIterator1.next();
      String str1 = EntitlementsUtil.getEntitlementObjectId(localBusinessAccountGroup);
      Iterator localIterator2 = localEntitlementTypePropertyLists.iterator();
      while (localIterator2.hasNext())
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator2.next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          String str2 = localEntitlementTypePropertyList.getOperationName();
          localEntitlements.add(new Entitlement(str2, "AccountGroup", str1));
        }
      }
    }
    return localEntitlements;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements buildPerAccountAdminEntitlementsList(Accounts paramAccounts, String paramString)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = a(paramString, "per account");
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Iterator localIterator1 = paramAccounts.iterator();
    while (localIterator1.hasNext())
    {
      Account localAccount = (Account)localIterator1.next();
      String str1 = EntitlementsUtil.getEntitlementObjectId(localAccount);
      Iterator localIterator2 = localEntitlementTypePropertyLists.iterator();
      while (localIterator2.hasNext())
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator2.next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          String str2 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
          if ((str2 != null) && (str2.length() > 0)) {
            localEntitlements.add(new Entitlement(str2, "Account", str1));
          }
        }
      }
    }
    return localEntitlements;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements buildPerAccountAdminEntitlementsList(BusinessAccountGroups paramBusinessAccountGroups, String paramString)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = a(paramString, "per account");
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Iterator localIterator1 = paramBusinessAccountGroups.iterator();
    while (localIterator1.hasNext())
    {
      BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)localIterator1.next();
      String str1 = EntitlementsUtil.getEntitlementObjectId(localBusinessAccountGroup);
      Iterator localIterator2 = localEntitlementTypePropertyLists.iterator();
      while (localIterator2.hasNext())
      {
        localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator2.next();
        if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes"))
        {
          String str2 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
          if ((str2 != null) && (str2.length() > 0)) {
            localEntitlements.add(new Entitlement(str2, "AccountGroup", str1));
          }
        }
      }
    }
    return localEntitlements;
  }
  
  private static EntitlementTypePropertyLists a(String paramString)
    throws CSILException
  {
    EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = new EntitlementTypePropertyLists();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = null;
    localEntitlementTypePropertyLists2 = Entitlements.getEntitlementTypesWithProperties("category", paramString);
    Iterator localIterator = localEntitlementTypePropertyLists2.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if ((localEntitlementTypePropertyList.isPropertySet("component")) && (EntitlementsUtil.isCrossAccount(localEntitlementTypePropertyList)) && (((!paramString.equals("per company")) && (!paramString.equals("per business"))) || (!localEntitlementTypePropertyList.getOperationName().equals("UserAdmin")))) {
        localEntitlementTypePropertyLists1.add(localEntitlementTypePropertyList);
      }
    }
    return localEntitlementTypePropertyLists1;
  }
  
  private static EntitlementTypePropertyLists a(String paramString1, String paramString2)
    throws CSILException
  {
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList = new ArrayList();
    if ((paramString1 != null) && (!paramString1.equals(""))) {
      localArrayList.add(paramString1);
    }
    localArrayList.add(paramString2);
    localHashMap.put("category", localArrayList);
    return Entitlements.getEntitlementTypesWithProperties(localHashMap);
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements buildAdminEntitlementsList(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    Iterator localIterator = paramEntitlements.iterator();
    while (localIterator.hasNext())
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      if (localEntitlement.getOperationName() != null)
      {
        EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("admin partner", localEntitlement.getOperationName());
        if (localEntitlementTypePropertyLists.size() != 0) {
          localEntitlements.add(localEntitlement);
        }
      }
    }
    return localEntitlements;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.AutoEntitleUtil
 * JD-Core Version:    0.7.0.1
 */