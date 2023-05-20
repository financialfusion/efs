package com.ffusion.csil.core;

import com.ffusion.beans.Modules;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.TransactionTypes;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.handlers.AdminHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class Admin
  extends Initialize
{
  private static Entitlement asK = new Entitlement("Audit Reporting", null, null);
  private static Entitlement asM = new Entitlement("Positive Pay", null, null);
  private static final String asI = "com.ffusion.util.logging.audit.admin";
  private static final String asJ = "AuditMessage_1";
  private static final String asL = "AuditMessage_2";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Admin.initialize");
    AdminHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return AdminHandler.getService();
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    int i = 0;
    BusinessEmployee localBusinessEmployee1 = null;
    BusinessEmployee localBusinessEmployee2 = null;
    BusinessEmployees localBusinessEmployees = null;
    EntitlementGroupMember localEntitlementGroupMember = null;
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Object localObject = null;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    LocalizableString localLocalizableString = null;
    IReportResult localIReportResult = null;
    long l = 0L;
    Object[] arrayOfObject = null;
    Properties localProperties1 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = "Admin.getReportData";
    String str6 = null;
    AuditLog.flushAuditLog();
    Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    localProperties1 = paramReportCriteria.getReportOptions();
    if (localProperties1 == null)
    {
      str1 = "The report criteria used in a call to getReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new CSILException(str5, 16002, str1);
    }
    str4 = localProperties1.getProperty("REPORTTYPE");
    if (str4 == null)
    {
      str1 = "The report options contained within the Report Criteria used in a call to getReportData does not contain a report type.";
      throw new CSILException(str5, 16002, str1);
    }
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str4, true);
    if (str4.equals("Session Receipt Report"))
    {
      i = 1;
      Properties localProperties2 = paramReportCriteria.getSearchCriteria();
      if (localProperties2 != null) {
        if (paramSecureUser.getProfileID() == paramSecureUser.getPrimaryUserID())
        {
          String str7 = String.valueOf(paramSecureUser.getProfileID());
          StringTokenizer localStringTokenizer = new StringTokenizer(localProperties2.getProperty("User"), ",", false);
          User localUser = new User();
          localUser.setId(str7);
          Users localUsers = UserAdmin.getSecondaryUsers(paramSecureUser, localUser, paramHashMap);
          String str8 = null;
          while (localStringTokenizer.hasMoreTokens())
          {
            str8 = localStringTokenizer.nextToken();
            if ((!str8.equals(str7)) && (localUsers.getByID(str8) == null)) {
              throw new CSILException(100000);
            }
          }
        }
        else if (!String.valueOf(paramSecureUser.getProfileID()).equals(localProperties2.getProperty("User")))
        {
          throw new CSILException(100001);
        }
      }
    }
    else if ((str4.equals("Session Activity Report")) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), asK)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), asM)))
    {
      i = 1;
      if ((paramReportCriteria != null) && (!str4.equals("Positive Pay Activity")) && ((paramReportCriteria.getSearchCriteria().getProperty("User") == null) || (paramReportCriteria.getSearchCriteria().getProperty("User").equals("AllUsers")) || (paramSecureUser.getProfileID() != Integer.parseInt(paramReportCriteria.getSearchCriteria().getProperty("User")))) && (!jdMethod_for(paramSecureUser, paramReportCriteria.getSearchCriteria()))) {
        throw new CSILException(str5, 20007);
      }
      if (str4.equals("User Entitlement Report"))
      {
        Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
        localBusinessEmployee1 = new BusinessEmployee();
        localBusinessEmployee1.setBankId(paramSecureUser.getBankID());
        localBusinessEmployee1.setBusinessId(paramSecureUser.getBusinessID());
        str2 = paramReportCriteria.getSearchCriteria().getProperty("Group");
        str6 = paramReportCriteria.getSearchCriteria().getProperty("User");
        if (!str2.equals("AllGroups")) {
          localBusinessEmployee1.setEntitlementGroupId(str2);
        }
        if (!str6.equals("AllUsers")) {
          localBusinessEmployee1.setId(str6);
        }
        localBusinessEmployees = UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee1, paramHashMap);
        localObject = Entitlements.getEntitlementTypesWithProperties("category", "per user");
        localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
        for (int j = 0; j < ((EntitlementTypePropertyLists)localObject).size(); j++)
        {
          localEntitlementTypePropertyList = (EntitlementTypePropertyList)((EntitlementTypePropertyLists)localObject).get(j);
          if (!localEntitlementTypePropertyList.isPropertySet("hide", "yes")) {
            localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList);
          }
        }
        localObject = localEntitlementTypePropertyLists;
        localEntitlementTypePropertyLists = null;
        for (j = 0; j < localBusinessEmployees.size(); j++)
        {
          localBusinessEmployee2 = (BusinessEmployee)localBusinessEmployees.get(j);
          localEntitlementGroupMember = localBusinessEmployee2.getEntitlementGroupMember();
          paramHashMap.put("OpToObjTypeMapForReport" + localEntitlementGroupMember.getId(), jdMethod_new((EntitlementTypePropertyLists)localObject));
          paramHashMap.put("ObjTypeToIdsMapForReport" + localEntitlementGroupMember.getId(), jdMethod_for(localBusinessEmployee2, (EntitlementTypePropertyLists)localObject));
        }
      }
    }
    if (i != 0)
    {
      l = System.currentTimeMillis();
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("ENTITLEMENT_ADAPTER", Entitlements.U());
      paramHashMap.put("ENTITLEMENT_REP_ADAPTER", Entitlements.V());
      localIReportResult = AdminHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      str3 = localProperties1.getProperty("TITLE");
      if ((str3 == null) || (str3.length() == 0)) {
        str3 = ReportConsts.getReportName(str4, Locale.getDefault());
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = str3;
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.admin", "AuditMessage_1", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 3710);
      PerfLog.log(str5, l, true);
      debug(paramSecureUser, str5);
    }
    else
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.admin", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str5, 20001);
    }
    return localIReportResult;
  }
  
  private static HashMap jdMethod_new(EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      localObject = (EntitlementTypePropertyList)localIterator.next();
      ArrayList localArrayList = new ArrayList();
      if (((EntitlementTypePropertyList)localObject).isPropertySet("category", "per account"))
      {
        localArrayList.add("Account");
        localArrayList.add("AccountGroup");
      }
      if (((EntitlementTypePropertyList)localObject).isPropertySet("category", "per location")) {
        localArrayList.add("Location");
      }
      if (((EntitlementTypePropertyList)localObject).isPropertySet("category", "per ACH company")) {
        localArrayList.add("ACHCompany");
      }
      if (((EntitlementTypePropertyList)localObject).isPropertySet("category", "per wire template")) {
        localArrayList.add("Wire Template");
      }
      if ((((EntitlementTypePropertyList)localObject).getOperationName().equals("ACHBatch")) && (!localArrayList.contains("ACHCompany"))) {
        localArrayList.add("ACHCompany");
      }
      localHashMap.put(((EntitlementTypePropertyList)localObject).getOperationName(), localArrayList);
    }
    Object localObject = new ArrayList();
    ((ArrayList)localObject).add("Account");
    localHashMap.put("", localObject);
    return localHashMap;
  }
  
  private static LocationSearchResults jdMethod_for(BusinessEmployee paramBusinessEmployee)
    throws CSILException
  {
    EntitlementGroup localEntitlementGroup1 = Entitlements.getEntitlementGroup(paramBusinessEmployee.getEntitlementGroupId());
    if (localEntitlementGroup1 == null) {
      return new LocationSearchResults();
    }
    LocationSearchResults localLocationSearchResults1 = null;
    Object localObject1;
    Object localObject2;
    if (localEntitlementGroup1.getEntGroupType().equals("Business"))
    {
      localObject1 = new LocationSearchCriteria();
      localLocationSearchResults1 = new LocationSearchResults();
      localObject2 = Entitlements.getChildrenEntitlementGroups(localEntitlementGroup1.getGroupId());
      Iterator localIterator = ((EntitlementGroups)localObject2).iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator.next();
        ((LocationSearchCriteria)localObject1).setDivisionID(localEntitlementGroup2.getGroupId());
        LocationSearchResults localLocationSearchResults2 = CashCon.getLocations(paramBusinessEmployee.getSecureUser(), (LocationSearchCriteria)localObject1, new HashMap());
        if (localLocationSearchResults2 != null) {
          localLocationSearchResults1.addAll(localLocationSearchResults2);
        }
      }
    }
    if (localEntitlementGroup1.getEntGroupType().equals("Division"))
    {
      localObject1 = new LocationSearchCriteria();
      ((LocationSearchCriteria)localObject1).setDivisionID(localEntitlementGroup1.getGroupId());
      localLocationSearchResults1 = CashCon.getLocations(paramBusinessEmployee.getSecureUser(), (LocationSearchCriteria)localObject1, new HashMap());
    }
    if (localEntitlementGroup1.getEntGroupType().equals("Group"))
    {
      localObject1 = Entitlements.getEntitlementGroup(localEntitlementGroup1.getParentId());
      localObject2 = new LocationSearchCriteria();
      ((LocationSearchCriteria)localObject2).setDivisionID(((EntitlementGroup)localObject1).getGroupId());
      localLocationSearchResults1 = CashCon.getLocations(paramBusinessEmployee.getSecureUser(), (LocationSearchCriteria)localObject2, new HashMap());
    }
    return localLocationSearchResults1;
  }
  
  private static HashMap jdMethod_for(BusinessEmployee paramBusinessEmployee, EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
    throws CSILException
  {
    HashMap localHashMap1 = new HashMap();
    ArrayList localArrayList = new ArrayList();
    com.ffusion.beans.accounts.Accounts localAccounts = Accounts.getAccountsByBusinessEmployee(paramBusinessEmployee.getSecureUser(), new HashMap());
    HashMap localHashMap2 = new HashMap();
    if (localAccounts != null)
    {
      localObject1 = localAccounts.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Account)((Iterator)localObject1).next();
        localArrayList.add(EntitlementsUtil.getEntitlementObjectId((Account)localObject2));
        localObject3 = ((Account)localObject2).getRoutingNum() + ":" + ((Account)localObject2).getDisplayText();
        localHashMap2.put(EntitlementsUtil.getEntitlementObjectId((Account)localObject2), localObject3);
      }
    }
    Object localObject1 = new ArrayList();
    Object localObject2 = AccountGroup.getAccountGroups(paramBusinessEmployee.getSecureUser(), paramBusinessEmployee.getBusinessId(), new HashMap());
    Object localObject3 = new HashMap();
    if (localObject2 != null)
    {
      localObject4 = ((BusinessAccountGroups)localObject2).iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (BusinessAccountGroup)((Iterator)localObject4).next();
        ((ArrayList)localObject1).add("" + ((BusinessAccountGroup)localObject5).getId());
        ((HashMap)localObject3).put("" + ((BusinessAccountGroup)localObject5).getId(), ((BusinessAccountGroup)localObject5).getName());
      }
    }
    Object localObject4 = new ArrayList();
    Object localObject5 = jdMethod_for(paramBusinessEmployee);
    HashMap localHashMap3 = new HashMap();
    if (localObject5 != null)
    {
      localObject6 = ((LocationSearchResults)localObject5).iterator();
      while (((Iterator)localObject6).hasNext())
      {
        localObject7 = (LocationSearchResult)((Iterator)localObject6).next();
        ((ArrayList)localObject4).add(EntitlementsUtil.getEntitlementObjectId((LocationSearchResult)localObject7));
        localHashMap3.put(EntitlementsUtil.getEntitlementObjectId((LocationSearchResult)localObject7), ((LocationSearchResult)localObject7).getLocationName());
      }
    }
    Object localObject6 = new ArrayList();
    Object localObject7 = Wire.getAllWireTemplates(paramBusinessEmployee.getSecureUser(), paramBusinessEmployee.getBusinessId(), new HashMap());
    HashMap localHashMap4 = new HashMap();
    if (localObject7 != null)
    {
      localObject8 = ((WireTransfers)localObject7).iterator();
      while (((Iterator)localObject8).hasNext())
      {
        localObject9 = (WireTransfer)((Iterator)localObject8).next();
        ((ArrayList)localObject6).add("" + ((WireTransfer)localObject9).getID());
        localHashMap4.put("" + ((WireTransfer)localObject9).getID(), ((WireTransfer)localObject9).getNickName());
      }
    }
    Object localObject8 = new ArrayList();
    Object localObject9 = new HashMap();
    Object localObject10;
    try
    {
      ACHCompanies localACHCompanies = ACH.getACHCompanies(paramBusinessEmployee.getSecureUser(), String.valueOf(paramBusinessEmployee.getBusinessId()), paramBusinessEmployee.getSecureUser().getBankID(), false, new HashMap());
      localObject10 = localACHCompanies.iterator();
      while (((Iterator)localObject10).hasNext())
      {
        ACHCompany localACHCompany = (ACHCompany)((Iterator)localObject10).next();
        ((ArrayList)localObject8).add(EntitlementsUtil.getEntitlementObjectId(localACHCompany));
        ((HashMap)localObject9).put("" + localACHCompany.getCompanyID(), localACHCompany.getCompanyID() + "/ " + localACHCompany.getCompanyName());
      }
    }
    catch (CSILException localCSILException)
    {
      if (localCSILException.getCode() != 20001) {
        throw new CSILException(localCSILException.getCode(), localCSILException);
      }
    }
    localHashMap1.put("AccountDisplayValues", localHashMap2);
    localHashMap1.put("LocationDisplayValues", localHashMap3);
    localHashMap1.put("ACHCompanyDisplayValues", localObject9);
    localHashMap1.put("AccountGroupDisplayValues", localObject3);
    localHashMap1.put("Wire TemplateDisplayValues", localHashMap4);
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      localObject10 = (EntitlementTypePropertyList)localIterator.next();
      if (((EntitlementTypePropertyList)localObject10).isPropertySet("category", "per account")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject10).getOperationName().concat("Account"), localArrayList);
      }
      if (((EntitlementTypePropertyList)localObject10).isPropertySet("category", "per location")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject10).getOperationName().concat("Location"), localObject4);
      }
      if (((EntitlementTypePropertyList)localObject10).isPropertySet("category", "per ACH company")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject10).getOperationName().concat("ACHCompany"), localObject8);
      }
      if (((EntitlementTypePropertyList)localObject10).isPropertySet("category", "per account")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject10).getOperationName().concat("AccountGroup"), localObject1);
      }
      if (((EntitlementTypePropertyList)localObject10).isPropertySet("category", "per wire template")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject10).getOperationName().concat("Wire Template"), localObject6);
      }
      localAccounts = null;
      localAccounts = Accounts.getAccountsByBusinessEmployee(paramBusinessEmployee.getSecureUser(), new HashMap());
      if (localAccounts != null) {
        localHashMap1.put("Account", localArrayList);
      }
    }
    localHashMap1.put("ACHBatch".concat("ACHCompany"), localObject8);
    return localHashMap1;
  }
  
  private static boolean jdMethod_for(SecureUser paramSecureUser, Properties paramProperties)
    throws CSILException
  {
    String str1 = null;
    String str2 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("User");
      str2 = paramProperties.getProperty("Group");
    }
    EntitlementAdmin localEntitlementAdmin;
    if ((str1 != null) && (!str1.equals("AllUsers")))
    {
      EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setId(str1);
      localEntitlementGroupMember.setMemberType("USER");
      localEntitlementGroupMember.setMemberSubType(Integer.toString(1));
      localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
      localEntitlementAdmin = new EntitlementAdmin(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlementGroupMember.getEntitlementGroupId());
      if (!Entitlements.canAdminister(localEntitlementAdmin)) {
        return false;
      }
    }
    if ((str2 != null) && (!str2.equals("AllGroups"))) {
      try
      {
        int i = Integer.parseInt(str2);
        localEntitlementAdmin = new EntitlementAdmin(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), i);
        if (!Entitlements.canAdminister(localEntitlementAdmin)) {
          return false;
        }
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    return true;
  }
  
  public static TransactionTypes getTransactionTypes(Locale paramLocale)
  {
    return AdminHandler.getTransactionTypes(paramLocale);
  }
  
  public static TransactionTypes getTransactionTypesForModule(Locale paramLocale, String paramString)
  {
    return AdminHandler.getTransactionTypesForModule(paramLocale, paramString);
  }
  
  public static Modules getModules(Locale paramLocale)
  {
    return AdminHandler.getModules(paramLocale);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Admin
 * JD-Core Version:    0.7.0.1
 */