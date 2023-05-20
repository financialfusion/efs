package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.bcreport.BusinessServiceChargeList;
import com.ffusion.beans.bcreport.BusinessServiceChargeLists;
import com.ffusion.beans.bcreport.ConsumerServiceChargeList;
import com.ffusion.beans.bcreport.ConsumerServiceChargeLists;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.business.MarketSegment;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.beans.business.ServiceFeature;
import com.ffusion.beans.business.ServiceFeatures;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.handlers.BCReportHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class BCReport
  extends Initialize
{
  private static Entitlement aBR = new Entitlement("Personal Banker", null, null);
  private static Entitlement aBU = new Entitlement("Global Admin", null, null);
  private static ArrayList aBS = new ArrayList();
  private static ArrayList aBQ = new ArrayList();
  private static HashMap aBT;
  
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.core.BCReport.initialize");
    BCReportHandler.initialize(paramHashMap);
    aBT = W();
    aBS.add("External Transfers From");
    aBS.add("External Transfers To");
    aBQ.add("External Transfers From");
    aBQ.add("External Transfers To");
    aBQ.add("Transfers From");
    aBQ.add("Transfers To");
    aBQ.add("Payments");
  }
  
  public static Object getService()
  {
    return BCReportHandler.getService();
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BCReport.getReportData";
    AuditLog.flushAuditLog();
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject1 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new CSILException(str1, 10, (String)localObject1);
    }
    Object localObject1 = paramReportCriteria.getReportOptions();
    String str2 = ((Properties)localObject1).getProperty("REPORTTYPE");
    if (str2 == null)
    {
      str3 = "The report options contained within the Report Criteria used in a call to getBCReportData does not contain a report type.";
      throw new CSILException(11);
    }
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    if (jdMethod_try(paramSecureUser, str2))
    {
      Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
      str3 = jdMethod_for(localProperties, "Affiliate Bank", "AllAffiliateBanks");
      int i = 0;
      AffiliateBanks localAffiliateBanks = new AffiliateBanks();
      AffiliateBank localAffiliateBank = new AffiliateBank();
      Object localObject2;
      if ((str3 != null) && (str3.length() > 0)) {
        if (!str3.equals("AllAffiliateBanks"))
        {
          String str4;
          try
          {
            i = Integer.parseInt(str3);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            str4 = "The affiliate bank ID provided in the Report Criteria is not a valid identifier.";
            throw new CSILException(str1, 30, str4);
          }
          localObject2 = new HashMap();
          localObject2 = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass((HashMap)localObject2);
          localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID(paramSecureUser, i, (HashMap)localObject2);
          if (localAffiliateBank == null)
          {
            str4 = "The affiliate bank ID provided in the Report Criteria is not valid.";
            throw new CSILException(str1, 30, str4);
          }
          localAffiliateBanks.add(localAffiliateBank);
          paramHashMap.put("AffiliateBankNameForReport", localAffiliateBank.getAffiliateBankName());
        }
        else
        {
          localAffiliateBanks = AffiliateBankAdmin.getAffiliateBanks(paramSecureUser, paramHashMap);
          if (localAffiliateBanks == null)
          {
            localObject2 = "The affiliate bank ID provided in the Report Criteria is not valid.";
            throw new CSILException(str1, 30, (String)localObject2);
          }
          localAffiliateBanks.setSortedBy("BANK_NAME");
        }
      }
      Object localObject3;
      Object localObject4;
      Object localObject6;
      Object localObject5;
      if (str2.equals("BC Non-zero Balance Report"))
      {
        localObject2 = jdMethod_for(localProperties, "Business", "AllBusinesses");
        int j = 0;
        localObject3 = new com.ffusion.beans.business.Business();
        localObject4 = new Businesses();
        if ((localObject2 != null) && (((String)localObject2).length() > 0) && (!((String)localObject2).equals("AllBusinesses")))
        {
          try
          {
            j = Integer.parseInt((String)localObject2);
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            localObject6 = "The business ID provided in the Report Criteria is not a valid identifier.";
            throw new CSILException(str1, 18, (String)localObject6);
          }
          localObject3 = Business.jdMethod_int(paramSecureUser, j, paramHashMap);
          if (localObject3 == null)
          {
            localObject5 = "The business ID provided in the Report Criteria is not valid.";
            throw new CSILException(str1, 18, (String)localObject5);
          }
          ((Businesses)localObject4).add(localObject3);
          ((Businesses)localObject4).setSortedBy(com.ffusion.beans.business.Business.BUSINESSNAME);
          paramHashMap.put("BusinessNameForReport", ((com.ffusion.beans.business.Business)localObject3).getBusinessName());
        }
        paramHashMap.put("AffiliateBankCollection", localAffiliateBanks);
        paramHashMap.put("BusinessCollection", localObject4);
      }
      if (T(str2)) {
        return BCReportHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Business Service Charge Report"))
      {
        localObject2 = new BusinessServiceChargeLists();
        jdMethod_for((BusinessServiceChargeLists)localObject2, paramSecureUser, localProperties, i, paramHashMap);
        ((BusinessServiceChargeLists)localObject2).setSortedBy("MARKETSEGMENT,SERVICEPACKAGE");
        paramHashMap.put("BusinessesForServiceChargeReport", localObject2);
      }
      else if (str2.equals("Consumer Service Charge Report"))
      {
        localObject2 = new ConsumerServiceChargeLists();
        jdMethod_for((ConsumerServiceChargeLists)localObject2, paramSecureUser, localProperties, i, paramHashMap);
        ((ConsumerServiceChargeLists)localObject2).setSortedBy("MARKET_SEGMENT,SERVICE_PACKAGE");
        paramHashMap.put("ConsumersForServiceChargeReport", localObject2);
      }
      else if (str2.equals("Market Segment History Report"))
      {
        jdMethod_int(localProperties, paramHashMap);
      }
      else if (str2.equals("Service Package History Report"))
      {
        jdMethod_for(localProperties, paramHashMap);
      }
      else if (str2.equals("Bank Employee History Report"))
      {
        jdMethod_case(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("Customer History Report"))
      {
        jdMethod_char(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("Business History Report"))
      {
        jdMethod_else(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("Managed Participant History Report"))
      {
        jdMethod_for(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("Beneficiary History Report"))
      {
        jdMethod_try(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("Payee History Report"))
      {
        jdMethod_long(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("ACH Company History Report"))
      {
        jdMethod_byte(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("Account Group History Report"))
      {
        jdMethod_null(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("Cash Concentration Company History Report"))
      {
        jdMethod_int(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("External Transfer ACH Batch Information Report"))
      {
        jdMethod_new(paramSecureUser, localProperties, i, paramHashMap);
      }
      else if (str2.equals("External Transfer Account Report"))
      {
        jdMethod_goto(paramSecureUser, localProperties, i, paramHashMap);
      }
      else
      {
        localObject2 = jdMethod_for(localProperties, "User", "AllUsers");
        String str5 = jdMethod_for(localProperties, "Business", "AllBusinesses");
        localObject3 = new BankEmployees(paramSecureUser.getLocale());
        Object localObject7;
        if (str2.equals("OBO CSR Activity Report"))
        {
          localObject4 = jdMethod_for(localProperties, "Agent", "AllAgents");
          localObject3 = jdMethod_for(paramSecureUser, (String)localObject4, i, str2);
          if (((String)localObject4).equals("AllAgents")) {
            localProperties.setProperty("Agent", "AllAgents");
          }
        }
        else if (str2.equals("CSR Performance Report"))
        {
          localObject4 = jdMethod_for(localProperties, "Agent", "AllAgents");
          if (((String)localObject4).equals("AllAgents"))
          {
            localProperties.setProperty("Agent", "AllAgents");
          }
          else
          {
            localObject5 = new BankEmployee(paramSecureUser.getLocale());
            ((BankEmployee)localObject5).setId((String)localObject4);
            ((BankEmployees)localObject3).add(BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject5, paramHashMap));
          }
        }
        else if ((str2.equals("OBO CSR Team Activity Report")) || (str2.equals("CSR Team Performance Report")))
        {
          localObject3 = jdMethod_new(paramSecureUser, i);
        }
        else if ((str2.equals("My OBO Activity Report")) || (str2.equals("My Performance Report")))
        {
          localObject3 = new BankEmployees();
          localObject4 = new BankEmployee(paramSecureUser.getLocale());
          ((BankEmployee)localObject4).setId(Integer.toString(paramSecureUser.getProfileID()));
          ((BankEmployees)localObject3).add(BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject4, paramHashMap));
        }
        else if (str2.equals("My Organization's Performance Report"))
        {
          localObject4 = jdMethod_for(localProperties, "Agent", "");
          localObject5 = new BankEmployee(paramSecureUser.getLocale());
          ((BankEmployee)localObject5).setSupervisor((String)localObject4);
          localObject3 = jdMethod_for(paramSecureUser, (BankEmployee)localObject5, i);
          localObject5 = new BankEmployee(paramSecureUser.getLocale());
          ((BankEmployee)localObject5).setId((String)localObject4);
          ((BankEmployees)localObject3).add(BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject5, paramHashMap));
        }
        else
        {
          Object localObject8;
          Object localObject10;
          if (str2.equals("Bank Employee Permissions Report"))
          {
            Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
            localObject4 = jdMethod_for(localProperties, "Activity", "AllActivities");
            localObject3 = jdMethod_int(paramSecureUser, i);
            localObject5 = new HashMap();
            localObject5 = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass((HashMap)localObject5);
            paramHashMap.put("JobTypesForReport", BankEmployeeAdmin.getJobTypes(paramSecureUser, (HashMap)localObject5));
            paramHashMap.put("LimitTypesForReport", Entitlements.getLimitTypesWithProperties("category", "per bank employee"));
            localObject6 = Entitlements.getEntitlementTypesWithProperties("category", "per bank employee");
            paramHashMap.put("EntitlementTypesForReport", localObject6);
            localObject7 = new HashMap();
            localObject8 = ((EntitlementTypePropertyLists)localObject6).iterator();
            while (((Iterator)localObject8).hasNext())
            {
              EntitlementTypePropertyList localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)((Iterator)localObject8).next();
              if ((((String)localObject4).equals("AllActivities")) || (((String)localObject4).equals(localEntitlementTypePropertyList1.getOperationName())))
              {
                int m = 0;
                if ((localEntitlementTypePropertyList1.isPropertySet("hide")) && (localEntitlementTypePropertyList1.getPropertyValue("hide", 0).equals("yes"))) {
                  m = 1;
                }
                if (m == 0) {
                  ((HashMap)localObject7).put(localEntitlementTypePropertyList1.getOperationName(), null);
                }
              }
            }
            for (int k = 0; k < ((BankEmployees)localObject3).size(); k++)
            {
              localObject10 = ((BankEmployee)((BankEmployees)localObject3).get(k)).getEntitlementGroupMember();
              paramHashMap.put("OpToObjTypeMapForReport" + ((EntitlementGroupMember)localObject10).getId(), localObject7);
              paramHashMap.put("ObjTypeToIdsMapForReport" + ((EntitlementGroupMember)localObject10).getId(), new HashMap());
            }
            paramHashMap.put("ENTITLEMENT_ADAPTER", Entitlements.U());
          }
          else if (str2.equals("Customer Permissions Report"))
          {
            Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
            localObject4 = jdMethod_for(localProperties, "Activity", "AllActivities");
            localObject3 = jdMethod_int(paramSecureUser, i);
            localObject5 = jdMethod_for(paramSecureUser, str5, i, false);
            paramHashMap.put("BusinessesForReport", localObject5);
            localObject6 = jdMethod_for(paramSecureUser, (Businesses)localObject5, (BankEmployees)localObject3, str5, (String)localObject2, i, false);
            localObject7 = jdMethod_for(paramReportCriteria.getSearchCriteria(), "User", "AllUsers");
            localObject8 = paramReportCriteria.getSearchCriteria().getProperty("IncludeSecondaryUsers");
            if ((!((String)localObject7).equals("AllUsers")) && (localObject8 != null) && (((String)localObject8).equals("on")))
            {
              localObject9 = new User(paramReportCriteria.getLocale());
              ((User)localObject9).setId((String)localObject7);
              localObject9 = UserAdmin.getUserById(paramSecureUser, (User)localObject9, new HashMap());
              localObject10 = UserAdmin.getSecondaryUsers(paramSecureUser, (User)localObject9, new HashMap());
              ((Users)localObject10).setSortedBy("LAST ASC,FIRST ASC");
              paramHashMap.put("SecondaryUsersForReport", localObject10);
            }
            else
            {
              paramHashMap.put("SecondaryUsersForReport", null);
            }
            paramHashMap.put("UsersForReport", localObject6);
            Object localObject9 = Entitlements.getEntitlementTypesWithProperties("category", "per user");
            boolean bool = ((String)localObject4).equals("AllActivities");
            Iterator localIterator = ((EntitlementTypePropertyLists)localObject9).iterator();
            while (localIterator.hasNext())
            {
              localObject11 = (EntitlementTypePropertyList)localIterator.next();
              if ((!bool) && (!((String)localObject4).equals(((EntitlementTypePropertyList)localObject11).getOperationName()))) {
                localIterator.remove();
              } else if (((EntitlementTypePropertyList)localObject11).isPropertySet("hide", "yes")) {
                localIterator.remove();
              }
            }
            Object localObject11 = Entitlements.getEntitlementTypesWithProperties("category", "per consumer");
            localIterator = ((EntitlementTypePropertyLists)localObject11).iterator();
            while (localIterator.hasNext())
            {
              localObject12 = (EntitlementTypePropertyList)localIterator.next();
              if ((!bool) && (!((String)localObject4).equals(((EntitlementTypePropertyList)localObject12).getOperationName()))) {
                localIterator.remove();
              } else if (((EntitlementTypePropertyList)localObject12).isPropertySet("hide", "yes")) {
                localIterator.remove();
              }
            }
            Object localObject12 = Entitlements.getEntitlementTypesWithProperties("category", "per subconsumer");
            localIterator = ((EntitlementTypePropertyLists)localObject12).iterator();
            while (localIterator.hasNext())
            {
              EntitlementTypePropertyList localEntitlementTypePropertyList2 = (EntitlementTypePropertyList)localIterator.next();
              if ((!bool) && (!((String)localObject4).equals(localEntitlementTypePropertyList2.getOperationName()))) {
                localIterator.remove();
              } else if (localEntitlementTypePropertyList2.isPropertySet("hide", "yes")) {
                localIterator.remove();
              }
            }
            ((EntitlementTypePropertyLists)localObject12).addAll((Collection)localObject11);
            jdMethod_for((EntitlementTypePropertyLists)localObject11, aBS);
            jdMethod_for((EntitlementTypePropertyLists)localObject12, aBQ);
            paramHashMap.put("BusEmpEntTypesForReport", localObject9);
            paramHashMap.put("ConsumerEntTypesForReport", localObject11);
            paramHashMap.put("SubConsumerEntTypesForReport", localObject12);
            paramHashMap.put("ENTITLEMENT_ADAPTER", Entitlements.U());
          }
          else if (str2.equals("Bank Employee Activity Report"))
          {
            localObject4 = jdMethod_for(localProperties, "Agent", "AllAgents");
            if (((String)localObject4).equals("AllAgents")) {
              localObject3 = jdMethod_int(paramSecureUser, i);
            } else {
              localObject3 = jdMethod_int(paramSecureUser, (String)localObject4, i);
            }
          }
          else if (str2.equals("CSR Activity Report"))
          {
            localObject4 = jdMethod_for(localProperties, "Agent", "AllAgents");
            localObject3 = jdMethod_for(paramSecureUser, (String)localObject4, i, str2);
          }
          else
          {
            localObject3 = new BankEmployees();
            localObject4 = new BankEmployee(paramSecureUser.getLocale());
            ((BankEmployee)localObject4).setId(Integer.toString(paramSecureUser.getProfileID()));
            ((BankEmployees)localObject3).add(localObject4);
          }
        }
        paramHashMap.put("BankEmployeesForReport", localObject3);
        if ((str2.equals("User Activity Report")) || (str2.equals("Login Activity Report")) || (str2.equals("Login Summary Report")) || (str2.equals("OBO CSR Activity Report")) || (str2.equals("OBO CSR Team Activity Report")) || (str2.equals("My OBO Activity Report")) || (str2.equals("Bank Employee Activity Report")) || (str2.equals("CSR Activity Report")))
        {
          if ((((BankEmployees)localObject3).size() != 0) || (str2.equals("User Activity Report")) || (str2.equals("Login Activity Report")) || (str2.equals("Login Summary Report")))
          {
            localObject4 = null;
            localObject5 = null;
            if (str5.equals("AllBusinesses"))
            {
              paramHashMap.put("BusinessesForReport", null);
            }
            else if (str5.equals("AllBusinessesAndConsumers"))
            {
              paramHashMap.put("BusinessesForReport", null);
            }
            else if (!str5.equals("AllConsumers"))
            {
              localObject6 = new ArrayList();
              ((ArrayList)localObject6).add(str5);
              paramHashMap.put("BusinessesForReport", localObject6);
            }
            if (((String)localObject2).equals("AllUsers"))
            {
              paramHashMap.put("UsersForReport", null);
            }
            else
            {
              localObject6 = new ArrayList();
              ((ArrayList)localObject6).add(localObject2);
              paramHashMap.put("UsersForReport", localObject6);
            }
          }
        }
        else if ((str2.equals("ACH Batch Report")) || (str2.equals("Tax Payment Report")) || (str2.equals("Child Support Payment Report")))
        {
          localObject4 = null;
          localObject5 = null;
          paramHashMap.put("BusinessesForReport", localObject4);
          paramHashMap.put("UsersForReport", localObject5);
          localObject4 = jdMethod_for(paramSecureUser, str5, 0, true);
          if ((localObject4 != null) && (!str5.equals("AllBusinesses")) && (!str5.equals("AllConsumers")) && (!str5.equals("AllBusinessesAndConsumers")))
          {
            localObject6 = (com.ffusion.beans.business.Business)((Businesses)localObject4).get(0);
            localObject7 = ACH.getACHCompanies(paramSecureUser, String.valueOf(((com.ffusion.beans.business.Business)localObject6).getId()), paramSecureUser.getBankID(), new HashMap());
            if (localObject7 != null) {
              paramHashMap.put("ACHCompaniesForReport", localObject7);
            }
          }
        }
        else if ((str2.equals("ACH File Report")) || (str2.equals("ACH File Upload Report")) || (str2.equals("External Transfer ACH File Report")))
        {
          localObject4 = null;
          localObject5 = null;
          paramHashMap.put("BusinessesForReport", localObject4);
          paramHashMap.put("UsersForReport", localObject5);
        }
      }
      if ((str2.equals("Positive Pay Exceptions Report")) || (str2.equals("Positive Pay Decisions Report"))) {
        return PositivePay.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      return BCReportHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    String str3 = "User is not entitled to this report.";
    throw new CSILException(str1, 27, str3);
  }
  
  private static boolean S(String paramString)
  {
    return (paramString.equals("User Activity Report")) || (paramString.equals("Login Activity Report")) || (paramString.equals("Login Summary Report")) || (paramString.equals("OBO CSR Activity Report")) || (paramString.equals("OBO CSR Team Activity Report")) || (paramString.equals("My OBO Activity Report"));
  }
  
  private static void jdMethod_for(BusinessServiceChargeLists paramBusinessServiceChargeLists, SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = paramProperties.getProperty("MarketSegments");
    String str2 = paramProperties.getProperty("ServicePackages");
    String str3 = paramProperties.getProperty("Business");
    Object localObject1;
    Object localObject2;
    Object localObject4;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    Object localObject9;
    if (!str3.equals("AllBusinesses"))
    {
      int i = Integer.parseInt(str3);
      localObject1 = new com.ffusion.beans.business.Business();
      ((com.ffusion.beans.business.Business)localObject1).setId(i);
      localObject2 = Business.getBusiness(paramSecureUser, (com.ffusion.beans.business.Business)localObject1, paramHashMap);
      if ((localObject2 != null) && ((paramInt == 0) || (((com.ffusion.beans.business.Business)localObject2).getAffiliateBankID() == paramInt)))
      {
        int n = ((com.ffusion.beans.business.Business)localObject2).getServicesPackageIdValue();
        localObject4 = Entitlements.getEntitlementGroup(n);
        int i1 = ((EntitlementGroup)localObject4).getParentId();
        localObject6 = Entitlements.getEntitlementGroup(i1);
        localObject7 = new BusinessServiceChargeList(((EntitlementGroup)localObject6).getGroupName(), ((EntitlementGroup)localObject4).getGroupName());
        ((BusinessServiceChargeList)localObject7).addBusiness(i, ((com.ffusion.beans.business.Business)localObject2).getBusinessName());
        ServiceFeatures localServiceFeatures = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, n, paramHashMap);
        if (localServiceFeatures != null)
        {
          localObject8 = localServiceFeatures.iterator();
          while (((Iterator)localObject8).hasNext())
          {
            localObject9 = (ServiceFeature)((Iterator)localObject8).next();
            if (((ServiceFeature)localObject9).getChargable()) {
              ((BusinessServiceChargeList)localObject7).addChargeableOperation(((ServiceFeature)localObject9).getFeatureName(), ((ServiceFeature)localObject9).getServiceCharge());
            }
          }
        }
        paramBusinessServiceChargeLists.add(localObject7);
      }
    }
    else
    {
      Object localObject3;
      Object localObject5;
      int i3;
      Object localObject10;
      Object localObject11;
      ServiceFeature localServiceFeature;
      if (str1.equals("AllMarketSegments"))
      {
        if (str2.equals("AllServicePackages"))
        {
          MarketSegments localMarketSegments1 = Business.getMarketSegmentsByUserType(paramSecureUser, "Business", String.valueOf(paramInt), paramHashMap);
          localObject1 = localMarketSegments1.iterator();
          while (((Iterator)localObject1).hasNext())
          {
            localObject2 = (MarketSegment)((Iterator)localObject1).next();
            localObject3 = ((MarketSegment)localObject2).getMarketSegmentName();
            localObject4 = new MarketSegments();
            ((MarketSegments)localObject4).add(localObject2);
            localObject5 = Business.getServicesPackagesForMarketSegments(paramSecureUser, (MarketSegments)localObject4, String.valueOf(paramInt), paramHashMap);
            localObject6 = ((ServicesPackages)localObject5).iterator();
            while (((Iterator)localObject6).hasNext())
            {
              localObject7 = (ServicesPackage)((Iterator)localObject6).next();
              i3 = ((ServicesPackage)localObject7).getIdValue();
              localObject8 = ((ServicesPackage)localObject7).getServicesPackageName();
              localObject9 = new BusinessServiceChargeList((String)localObject3, (String)localObject8);
              ((BusinessServiceChargeList)localObject9).addBusiness(-1, ((ServicesPackage)localObject7).getId());
              localObject10 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, i3, paramHashMap);
              if (localObject10 != null)
              {
                localObject11 = ((ServiceFeatures)localObject10).iterator();
                while (((Iterator)localObject11).hasNext())
                {
                  localServiceFeature = (ServiceFeature)((Iterator)localObject11).next();
                  if (localServiceFeature.getChargable()) {
                    ((BusinessServiceChargeList)localObject9).addChargeableOperation(localServiceFeature.getFeatureName(), localServiceFeature.getServiceCharge());
                  }
                }
              }
              paramBusinessServiceChargeLists.add(localObject9);
            }
          }
        }
        else
        {
          int j = Integer.parseInt(str2);
          localObject1 = Entitlements.getEntitlementGroup(j);
          int m = ((EntitlementGroup)localObject1).getParentId();
          localObject3 = Entitlements.getEntitlementGroup(m);
          if (paramInt != 0)
          {
            localObject4 = null;
            localObject5 = new ServicesPackage();
            ((ServicesPackage)localObject5).setEntitlementGroup((EntitlementGroup)localObject1);
            localObject4 = ((ServicesPackage)localObject5).getAffiliateBankId();
            if ((localObject4 != null) && (((String)localObject4).length() != 0) && (!String.valueOf(paramInt).equals(localObject4))) {
              return;
            }
            localObject6 = new MarketSegment();
            ((MarketSegment)localObject6).setEntitlementGroup((EntitlementGroup)localObject3);
            localObject4 = ((MarketSegment)localObject6).getAffiliateBankId();
            if ((localObject4 != null) && (((String)localObject4).length() != 0) && (!String.valueOf(paramInt).equals(localObject4))) {
              return;
            }
          }
          localObject4 = new BusinessServiceChargeList(((EntitlementGroup)localObject3).getGroupName(), ((EntitlementGroup)localObject1).getGroupName());
          ((BusinessServiceChargeList)localObject4).addBusiness(-1, str2);
          localObject5 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, j, paramHashMap);
          if (localObject5 != null)
          {
            localObject6 = ((ServiceFeatures)localObject5).iterator();
            while (((Iterator)localObject6).hasNext())
            {
              localObject7 = (ServiceFeature)((Iterator)localObject6).next();
              if (((ServiceFeature)localObject7).getChargable()) {
                ((BusinessServiceChargeList)localObject4).addChargeableOperation(((ServiceFeature)localObject7).getFeatureName(), ((ServiceFeature)localObject7).getServiceCharge());
              }
            }
          }
          paramBusinessServiceChargeLists.add(localObject4);
        }
      }
      else
      {
        MarketSegments localMarketSegments2 = Business.getMarketSegmentsByUserType(paramSecureUser, "Business", String.valueOf(paramInt), paramHashMap);
        int k = Integer.parseInt(str1);
        MarketSegment localMarketSegment = localMarketSegments2.getById(k);
        localObject3 = localMarketSegment.getMarketSegmentName();
        localObject4 = new MarketSegments();
        ((MarketSegments)localObject4).add(localMarketSegment);
        localObject5 = Business.getServicesPackagesForMarketSegments(paramSecureUser, (MarketSegments)localObject4, String.valueOf(paramInt), paramHashMap);
        if (str2.equals("AllServicePackages"))
        {
          localObject6 = ((ServicesPackages)localObject5).iterator();
          while (((Iterator)localObject6).hasNext())
          {
            localObject7 = (ServicesPackage)((Iterator)localObject6).next();
            i3 = ((ServicesPackage)localObject7).getIdValue();
            localObject8 = ((ServicesPackage)localObject7).getServicesPackageName();
            localObject9 = new BusinessServiceChargeList((String)localObject3, (String)localObject8);
            ((BusinessServiceChargeList)localObject9).addBusiness(-1, ((ServicesPackage)localObject7).getId());
            localObject10 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, i3, paramHashMap);
            if (localObject10 != null)
            {
              localObject11 = ((ServiceFeatures)localObject10).iterator();
              while (((Iterator)localObject11).hasNext())
              {
                localServiceFeature = (ServiceFeature)((Iterator)localObject11).next();
                if (localServiceFeature.getChargable()) {
                  ((BusinessServiceChargeList)localObject9).addChargeableOperation(localServiceFeature.getFeatureName(), localServiceFeature.getServiceCharge());
                }
              }
            }
            paramBusinessServiceChargeLists.add(localObject9);
          }
        }
        else
        {
          int i2 = Integer.parseInt(str2);
          localObject7 = ((ServicesPackages)localObject5).getById(i2);
          if (localObject7 != null)
          {
            String str4 = ((ServicesPackage)localObject7).getServicesPackageName();
            localObject8 = new BusinessServiceChargeList((String)localObject3, str4);
            ((BusinessServiceChargeList)localObject8).addBusiness(-1, str2);
            localObject9 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, i2, paramHashMap);
            if (localObject9 != null)
            {
              localObject10 = ((ServiceFeatures)localObject9).iterator();
              while (((Iterator)localObject10).hasNext())
              {
                localObject11 = (ServiceFeature)((Iterator)localObject10).next();
                if (((ServiceFeature)localObject11).getChargable()) {
                  ((BusinessServiceChargeList)localObject8).addChargeableOperation(((ServiceFeature)localObject11).getFeatureName(), ((ServiceFeature)localObject11).getServiceCharge());
                }
              }
            }
            paramBusinessServiceChargeLists.add(localObject8);
          }
        }
      }
    }
  }
  
  private static void jdMethod_for(ConsumerServiceChargeLists paramConsumerServiceChargeLists, SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = paramProperties.getProperty("MarketSegments");
    String str2 = paramProperties.getProperty("ServicePackages");
    String str3 = paramProperties.getProperty("Consumer");
    Object localObject1;
    Object localObject3;
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    if (!str3.equals("AllConsumers"))
    {
      localObject1 = new User();
      ((User)localObject1).setId(str3);
      localObject1 = UserAdmin.getUserById(paramSecureUser, (User)localObject1, paramHashMap);
      if ((localObject1 != null) && ((paramInt == 0) || (((User)localObject1).getAffiliateBankID() == paramInt)))
      {
        int j = ((User)localObject1).getServicesPackageIdValue();
        localObject3 = Entitlements.getEntitlementGroup(j);
        int n = ((EntitlementGroup)localObject3).getParentId();
        localObject5 = Entitlements.getEntitlementGroup(n);
        localObject6 = new ConsumerServiceChargeList(((EntitlementGroup)localObject5).getGroupName(), ((EntitlementGroup)localObject3).getGroupName());
        ((ConsumerServiceChargeList)localObject6).addCustomer(str3, new String[] { ((User)localObject1).getName(), ((User)localObject1).getUserName() });
        localObject7 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, j, paramHashMap);
        if (localObject7 != null)
        {
          localObject8 = ((ServiceFeatures)localObject7).iterator();
          while (((Iterator)localObject8).hasNext())
          {
            ServiceFeature localServiceFeature1 = (ServiceFeature)((Iterator)localObject8).next();
            if (localServiceFeature1.getChargable()) {
              ((ConsumerServiceChargeList)localObject6).addChargeableOperation(localServiceFeature1.getFeatureName(), localServiceFeature1.getServiceCharge());
            }
          }
        }
        paramConsumerServiceChargeLists.add(localObject6);
      }
    }
    else
    {
      Object localObject4;
      int i2;
      Object localObject9;
      Object localObject10;
      Object localObject11;
      Object localObject12;
      ServiceFeature localServiceFeature2;
      if (str1.equals("AllMarketSegments"))
      {
        Object localObject2;
        if (str2.equals("AllServicePackages"))
        {
          localObject1 = Business.getMarketSegmentsByUserType(paramSecureUser, "Consumers", String.valueOf(paramInt), paramHashMap);
          localObject2 = ((MarketSegments)localObject1).iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject3 = (MarketSegment)((Iterator)localObject2).next();
            localObject4 = ((MarketSegment)localObject3).getMarketSegmentName();
            localObject5 = new MarketSegments();
            ((MarketSegments)localObject5).add(localObject3);
            localObject6 = Business.getServicesPackagesForMarketSegments(paramSecureUser, (MarketSegments)localObject5, String.valueOf(paramInt), paramHashMap);
            localObject7 = ((ServicesPackages)localObject6).iterator();
            while (((Iterator)localObject7).hasNext())
            {
              localObject8 = (ServicesPackage)((Iterator)localObject7).next();
              i2 = ((ServicesPackage)localObject8).getIdValue();
              localObject9 = ((ServicesPackage)localObject8).getServicesPackageName();
              localObject10 = new ConsumerServiceChargeList((String)localObject4, (String)localObject9);
              paramHashMap.put("UseMaxRows", "false");
              ((ConsumerServiceChargeList)localObject10).addCustomer("ALL", new String[] { ((ServicesPackage)localObject8).getId() });
              localObject11 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, i2, paramHashMap);
              if (localObject11 != null)
              {
                localObject12 = ((ServiceFeatures)localObject11).iterator();
                while (((Iterator)localObject12).hasNext())
                {
                  localServiceFeature2 = (ServiceFeature)((Iterator)localObject12).next();
                  if (localServiceFeature2.getChargable()) {
                    ((ConsumerServiceChargeList)localObject10).addChargeableOperation(localServiceFeature2.getFeatureName(), localServiceFeature2.getServiceCharge());
                  }
                }
              }
              paramConsumerServiceChargeLists.add(localObject10);
            }
          }
        }
        else
        {
          int i = Integer.parseInt(str2);
          localObject2 = Entitlements.getEntitlementGroup(i);
          int m = ((EntitlementGroup)localObject2).getParentId();
          localObject4 = Entitlements.getEntitlementGroup(m);
          if (paramInt != 0)
          {
            localObject5 = null;
            localObject6 = new ServicesPackage();
            ((ServicesPackage)localObject6).setEntitlementGroup((EntitlementGroup)localObject2);
            localObject5 = ((ServicesPackage)localObject6).getAffiliateBankId();
            if ((localObject5 != null) && (((String)localObject5).length() != 0) && (!String.valueOf(paramInt).equals(localObject5))) {
              return;
            }
            localObject7 = new MarketSegment();
            ((MarketSegment)localObject7).setEntitlementGroup((EntitlementGroup)localObject4);
            localObject5 = ((MarketSegment)localObject7).getAffiliateBankId();
            if ((localObject5 != null) && (((String)localObject5).length() != 0) && (!String.valueOf(paramInt).equals(localObject5))) {
              return;
            }
          }
          if (localObject4 != null)
          {
            localObject5 = new ConsumerServiceChargeList(((EntitlementGroup)localObject4).getGroupName(), ((EntitlementGroup)localObject2).getGroupName());
            paramHashMap.put("UseMaxRows", "false");
            ((ConsumerServiceChargeList)localObject5).addCustomer("ALL", new String[] { str2 });
            localObject6 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, i, paramHashMap);
            if (localObject6 != null)
            {
              localObject7 = ((ServiceFeatures)localObject6).iterator();
              while (((Iterator)localObject7).hasNext())
              {
                localObject8 = (ServiceFeature)((Iterator)localObject7).next();
                if (((ServiceFeature)localObject8).getChargable()) {
                  ((ConsumerServiceChargeList)localObject5).addChargeableOperation(((ServiceFeature)localObject8).getFeatureName(), ((ServiceFeature)localObject8).getServiceCharge());
                }
              }
            }
            paramConsumerServiceChargeLists.add(localObject5);
          }
        }
      }
      else
      {
        MarketSegments localMarketSegments = Business.getMarketSegmentsByUserType(paramSecureUser, "Consumers", String.valueOf(paramInt), paramHashMap);
        int k = Integer.parseInt(str1);
        MarketSegment localMarketSegment = localMarketSegments.getById(k);
        localObject4 = localMarketSegment.getMarketSegmentName();
        localObject5 = new MarketSegments();
        ((MarketSegments)localObject5).add(localMarketSegment);
        localObject6 = Business.getServicesPackagesForMarketSegments(paramSecureUser, (MarketSegments)localObject5, String.valueOf(paramInt), paramHashMap);
        if (str2.equals("AllServicePackages"))
        {
          localObject7 = ((ServicesPackages)localObject6).iterator();
          while (((Iterator)localObject7).hasNext())
          {
            localObject8 = (ServicesPackage)((Iterator)localObject7).next();
            i2 = ((ServicesPackage)localObject8).getIdValue();
            localObject9 = ((ServicesPackage)localObject8).getServicesPackageName();
            localObject10 = new ConsumerServiceChargeList((String)localObject4, (String)localObject9);
            paramHashMap.put("UseMaxRows", "false");
            ((ConsumerServiceChargeList)localObject10).addCustomer("ALL", new String[] { ((ServicesPackage)localObject8).getId() });
            localObject11 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, i2, paramHashMap);
            if (localObject11 != null)
            {
              localObject12 = ((ServiceFeatures)localObject11).iterator();
              while (((Iterator)localObject12).hasNext())
              {
                localServiceFeature2 = (ServiceFeature)((Iterator)localObject12).next();
                if (localServiceFeature2.getChargable()) {
                  ((ConsumerServiceChargeList)localObject10).addChargeableOperation(localServiceFeature2.getFeatureName(), localServiceFeature2.getServiceCharge());
                }
              }
            }
            paramConsumerServiceChargeLists.add(localObject10);
          }
        }
        else
        {
          int i1 = Integer.parseInt(str2);
          localObject8 = ((ServicesPackages)localObject6).getById(i1);
          if (localObject8 != null)
          {
            String str4 = ((ServicesPackage)localObject8).getServicesPackageName();
            localObject9 = new ConsumerServiceChargeList((String)localObject4, str4);
            paramHashMap.put("UseMaxRows", "false");
            ((ConsumerServiceChargeList)localObject9).addCustomer("ALL", new String[] { str2 });
            localObject10 = Business.getChargeableServiceFeaturesByGroupId(paramSecureUser, i1, paramHashMap);
            if (localObject10 != null)
            {
              localObject11 = ((ServiceFeatures)localObject10).iterator();
              while (((Iterator)localObject11).hasNext())
              {
                localObject12 = (ServiceFeature)((Iterator)localObject11).next();
                if (((ServiceFeature)localObject12).getChargable()) {
                  ((ConsumerServiceChargeList)localObject9).addChargeableOperation(((ServiceFeature)localObject12).getFeatureName(), ((ServiceFeature)localObject12).getServiceCharge());
                }
              }
            }
            paramConsumerServiceChargeLists.add(localObject9);
          }
        }
      }
    }
  }
  
  private static void jdMethod_int(Properties paramProperties, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramProperties.getProperty("MarketSegments"));
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new CSILException(20118);
    }
    EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(i);
    if (localEntitlementGroup == null) {
      throw new CSILException(20012);
    }
    paramHashMap.put("MarketSegmentNameForReport", localEntitlementGroup.getGroupName());
  }
  
  private static void jdMethod_for(Properties paramProperties, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    int j = -1;
    try
    {
      j = Integer.parseInt(paramProperties.getProperty("ServicePackages"));
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      throw new CSILException(20119);
    }
    try
    {
      i = Integer.parseInt(paramProperties.getProperty("MarketSegments"));
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      throw new CSILException(20118);
    }
    EntitlementGroup localEntitlementGroup1 = Entitlements.getEntitlementGroup(j);
    if (localEntitlementGroup1 == null) {
      throw new CSILException(20012);
    }
    EntitlementGroup localEntitlementGroup2 = Entitlements.getEntitlementGroup(i);
    if (localEntitlementGroup2 == null) {
      throw new CSILException(20012);
    }
    paramHashMap.put("MarketSegmentNameForReport", localEntitlementGroup2.getGroupName());
    paramHashMap.put("ServicePackageNameForReport", localEntitlementGroup1.getGroupName());
  }
  
  private static void jdMethod_case(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    BankEmployee localBankEmployee1 = new BankEmployee(paramSecureUser.getLocale());
    localBankEmployee1.setId(paramProperties.getProperty("BankEmployee"));
    BankEmployee localBankEmployee2 = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee1, paramHashMap);
    if (localBankEmployee2 == null) {
      throw new CSILException(5102);
    }
    if (paramInt != 0)
    {
      int i = 0;
      localObject1 = String.valueOf(paramInt);
      if ((((String)localObject1).equals(localBankEmployee2.getDefaultAffiliateBankId())) || (localBankEmployee2.getDefaultAffiliateBankId().equals("0")))
      {
        i = 1;
      }
      else
      {
        localObject2 = localBankEmployee2.getAffiliateBankIds();
        if (localObject2 != null) {
          for (int j = 0; (j < ((ArrayList)localObject2).size()) && (i == 0); j++) {
            if (((String)localObject1).equals(((ArrayList)localObject2).get(j))) {
              i = 1;
            }
          }
        }
      }
      if (i == 0) {
        throw new CSILException(5102);
      }
    }
    paramHashMap.put("BankEmployeeNameForReport", localBankEmployee2.getFullNameWithLoginId());
    EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(localBankEmployee2.getJobTypeId());
    Object localObject1 = localEntitlementGroup.getProperties();
    Object localObject2 = EntitlementsUtil.getPropertyValue((EntitlementGroupProperties)localObject1, "display name", paramSecureUser.getLocale());
    if (localObject2 == null) {
      localObject2 = localEntitlementGroup.getGroupName();
    }
    paramHashMap.put("BankEmployeeJobTypeForReport", localObject2);
    paramHashMap.put("BankEmployeeStatusForReport", localBankEmployee2.getStatus());
  }
  
  private static void jdMethod_char(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    User localUser1 = new User(paramSecureUser.getLocale());
    localUser1.setId(paramProperties.getProperty("User"));
    User localUser2 = UserAdmin.getUserById(paramSecureUser, localUser1, paramHashMap);
    if (localUser2 == null) {
      throw new CSILException(3005);
    }
    paramHashMap.put("CustomerNameForReport", localUser2.getFullNameWithLoginId());
    paramHashMap.put("UserNameForReport", localUser2.getUserName());
    String str1 = "";
    String str2 = "";
    try
    {
      int i = Integer.parseInt((String)localUser2.get("BUSINESS_ID"));
      com.ffusion.beans.business.Business localBusiness1 = null;
      com.ffusion.beans.business.Business localBusiness2 = new com.ffusion.beans.business.Business();
      localBusiness2.setId(i);
      localBusiness1 = Business.getBusiness(paramSecureUser, localBusiness2, paramHashMap);
      if (localBusiness1 != null)
      {
        str1 = localBusiness1.getBusinessName();
        str2 = localBusiness1.getCustId();
      }
    }
    catch (Exception localException)
    {
      str1 = "";
      str2 = "";
    }
    paramHashMap.put("BusinessNameForReport", str1);
    paramHashMap.put("CustomerIDForReport", str2);
  }
  
  private static void jdMethod_else(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramProperties.getProperty("Business"));
    com.ffusion.beans.business.Business localBusiness2 = Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if ((localBusiness2 == null) || ((paramInt != 0) && (localBusiness2.getAffiliateBankID() != paramInt))) {
      throw new CSILException(4015);
    }
    String str1 = localBusiness2.getBusinessName();
    paramHashMap.put("BusinessNameForReport", str1);
    String str2 = "on";
    Object localObject;
    if (str2.equals(paramProperties.getProperty("IncludeDivisionsAndGroups")))
    {
      int i = Integer.parseInt(localBusiness2.getEntitlementGroupId());
      localObject = k(i);
      paramHashMap.put("DivisionsAndGroupsForReport", localObject);
    }
    String str3 = paramProperties.getProperty("MarketSegments");
    if ((str3 != null) && (str3.length() != 0))
    {
      localObject = paramProperties.getProperty("ServicePackages");
      if ((localObject != null) && (((String)localObject).length() != 0))
      {
        int j = -1;
        int k = -1;
        try
        {
          k = Integer.parseInt((String)localObject);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          throw new CSILException(20119);
        }
        try
        {
          j = Integer.parseInt(str3);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          throw new CSILException(20118);
        }
        EntitlementGroup localEntitlementGroup1 = Entitlements.getEntitlementGroup(k);
        EntitlementGroup localEntitlementGroup2 = Entitlements.getEntitlementGroup(j);
        if ((localEntitlementGroup2 == null) || (localEntitlementGroup1 == null)) {
          throw new CSILException(20012);
        }
        paramHashMap.put("MarketSegmentNameForReport", localEntitlementGroup2.getGroupName());
        paramHashMap.put("ServicePackageNameForReport", localEntitlementGroup1.getGroupName());
      }
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramProperties.getProperty("Business"));
    com.ffusion.beans.business.Business localBusiness2 = Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if ((localBusiness2 == null) || ((paramInt != 0) && (localBusiness2.getAffiliateBankID() != paramInt))) {
      throw new CSILException(4015);
    }
    String str1 = localBusiness2.getBusinessName();
    paramHashMap.put("BusinessNameForReport", str1);
    String str2 = paramProperties.getProperty("Business");
    ACHCompanies localACHCompanies = ACH.getACHCompanies(paramSecureUser, str2, localBusiness2.getBankId(), paramHashMap);
    if (localACHCompanies == null) {
      throw new CSILException(16129);
    }
    String str3 = paramProperties.getProperty("ACHCompanyID");
    String str4 = paramProperties.getProperty("ACHCompanyCoID");
    ACHCompany localACHCompany = localACHCompanies.getByCompanyID(str4);
    if (localACHCompany == null) {
      throw new CSILException(16129);
    }
    String str5 = localACHCompany.getCompanyName();
    paramHashMap.put("ACHCompanyNameForReport", str5);
    ACHPayees localACHPayees = ACH.getACHPayees(paramSecureUser, str2, str3, paramHashMap);
    if (localACHPayees == null) {
      throw new CSILException(16130);
    }
    ACHPayee localACHPayee = localACHPayees.getByID(paramProperties.getProperty("ManagedParticipant"));
    if (localACHPayee == null) {
      throw new CSILException(16130);
    }
    String str6 = localACHPayee.getName();
    paramHashMap.put("ManagedParticipantNameForReport", str6);
  }
  
  private static void jdMethod_try(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramProperties.getProperty("Business"));
    com.ffusion.beans.business.Business localBusiness2 = Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if ((localBusiness2 == null) || ((paramInt != 0) && (localBusiness2.getAffiliateBankID() != paramInt))) {
      throw new CSILException(4015);
    }
    String str1 = localBusiness2.getBusinessName();
    paramHashMap.put("BusinessNameForReport", str1);
    String str2 = paramProperties.getProperty("Beneficiary");
    WireTransferPayee localWireTransferPayee = Wire.getWireTransferPayeeById(paramSecureUser, str2, paramHashMap);
    if (localWireTransferPayee == null) {
      throw new CSILException(31021);
    }
    paramHashMap.put("BeneficiaryNameForReport", localWireTransferPayee.getName());
  }
  
  private static void jdMethod_long(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = paramProperties.getProperty("Business");
    Object localObject2;
    Object localObject3;
    if ((str1 != null) && (str1.length() != 0))
    {
      localObject1 = new com.ffusion.beans.business.Business();
      ((com.ffusion.beans.business.Business)localObject1).setId(str1);
      localObject2 = Business.getBusiness(paramSecureUser, (com.ffusion.beans.business.Business)localObject1, paramHashMap);
      if ((localObject2 == null) || ((paramInt != 0) && (((com.ffusion.beans.business.Business)localObject2).getAffiliateBankID() != paramInt))) {
        throw new CSILException(4015);
      }
      localObject3 = ((com.ffusion.beans.business.Business)localObject2).getBusinessName();
      paramHashMap.put("BusinessNameForReport", localObject3);
    }
    Object localObject1 = paramProperties.getProperty("Consumer");
    if ((localObject1 != null) && (((String)localObject1).length() != 0))
    {
      localObject2 = new User(paramSecureUser.getLocale());
      ((User)localObject2).setId((String)localObject1);
      localObject3 = UserAdmin.getUserById(paramSecureUser, (User)localObject2, paramHashMap);
      if ((localObject3 == null) || ((paramInt != 0) && (((User)localObject3).getAffiliateBankID() != paramInt))) {
        throw new CSILException(3005);
      }
      paramHashMap.put("CustomerNameForReport", ((User)localObject3).getFullNameWithLoginId());
    }
    if ((paramHashMap.get("PayeeNameForReport") == null) || (paramHashMap.get("PayeeNameForReport").equals("")))
    {
      if ((localObject1 != null) && (((String)localObject1).length() != 0)) {
        BPTW.signOnBillPay(paramSecureUser, (String)localObject1, "0", paramHashMap);
      }
      if ((str1 != null) && (str1.length() != 0)) {
        BPTW.signOnBillPay(paramSecureUser, str1, "0", paramHashMap);
      }
      localObject2 = paramProperties.getProperty("Payee");
      if ((localObject2 != null) && (((String)localObject2).length() != 0))
      {
        localObject3 = new Payees();
        paramHashMap.put("PAYEES", localObject3);
        localObject3 = BPTW.getPayees(paramSecureUser, paramHashMap);
        Payee localPayee = null;
        String str2 = "";
        for (int i = 0; i < ((Payees)localObject3).size(); i++)
        {
          localPayee = (Payee)((Payees)localObject3).get(i);
          if (localPayee.getID().equals(localObject2)) {
            str2 = localPayee.getName();
          }
        }
        paramHashMap.put("PayeeNameForReport", str2);
      }
    }
  }
  
  private static void jdMethod_byte(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramProperties.getProperty("Business"));
    com.ffusion.beans.business.Business localBusiness2 = Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if ((localBusiness2 == null) || ((paramInt != 0) && (localBusiness2.getAffiliateBankID() != paramInt))) {
      throw new CSILException(4015);
    }
    String str1 = localBusiness2.getBusinessName();
    paramHashMap.put("BusinessNameForReport", str1);
    String str2 = paramProperties.getProperty("ACHCompanyID");
    String str3 = paramProperties.getProperty("ACHCompanyCoID");
    ACHCompanies localACHCompanies = ACH.getACHCompanies(paramSecureUser, localBusiness1.getId(), localBusiness2.getBankId(), paramHashMap);
    if (localACHCompanies == null) {
      throw new CSILException(16129);
    }
    ACHCompany localACHCompany = localACHCompanies.getByCompanyID(str3);
    if (localACHCompany == null) {
      throw new CSILException(16129);
    }
    String str4 = localACHCompany.getCompanyName();
    paramHashMap.put("ACHCompanyNameForReport", str4);
  }
  
  private static void jdMethod_new(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramProperties.getProperty("Business"));
    com.ffusion.beans.business.Business localBusiness2 = Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if ((localBusiness2 == null) || ((paramInt != 0) && (localBusiness2.getAffiliateBankID() != paramInt))) {
      throw new CSILException(4015);
    }
    String str1 = localBusiness2.getBusinessName();
    paramHashMap.put("BusinessNameForReport", str1);
    String str2 = paramProperties.getProperty("ExtTransferCompany");
    ExtTransferCompanies localExtTransferCompanies = ExternalTransferAdmin.getExtTransferCompanies(paramSecureUser, localBusiness2.getId(), null, paramHashMap);
    ExtTransferCompany localExtTransferCompany = localExtTransferCompanies.getByID(str2);
    if (localExtTransferCompany == null) {
      throw new CSILException(39709);
    }
    paramHashMap.put("ExtTransferCompanyNameForReport", localExtTransferCompany.getCompanyName());
  }
  
  private static void jdMethod_goto(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = paramProperties.getProperty("Business");
    ExtTransferCompany localExtTransferCompany = null;
    if ((str1 != null) && (str1.length() != 0))
    {
      localObject1 = new com.ffusion.beans.business.Business();
      ((com.ffusion.beans.business.Business)localObject1).setId(str1);
      localObject2 = Business.getBusiness(paramSecureUser, (com.ffusion.beans.business.Business)localObject1, paramHashMap);
      if ((localObject2 == null) || ((paramInt != 0) && (((com.ffusion.beans.business.Business)localObject2).getAffiliateBankID() != paramInt))) {
        throw new CSILException(4015);
      }
      localObject3 = ((com.ffusion.beans.business.Business)localObject2).getBusinessName();
      paramHashMap.put("BusinessNameForReport", localObject3);
      localObject4 = paramProperties.getProperty("ExtTransferCompany");
      localObject5 = ExternalTransferAdmin.getExtTransferCompanies(paramSecureUser, ((com.ffusion.beans.business.Business)localObject2).getId(), null, paramHashMap);
      localExtTransferCompany = ((ExtTransferCompanies)localObject5).getByID((String)localObject4);
      if (localExtTransferCompany == null) {
        throw new CSILException(39709);
      }
    }
    Object localObject1 = paramProperties.getProperty("Consumer");
    if ((localObject1 != null) && (((String)localObject1).length() != 0))
    {
      localObject2 = new User(paramSecureUser.getLocale());
      ((User)localObject2).setId((String)localObject1);
      localObject3 = UserAdmin.getUserById(paramSecureUser, (User)localObject2, paramHashMap);
      if ((localObject3 == null) || ((paramInt != 0) && (((User)localObject3).getAffiliateBankID() != paramInt))) {
        throw new CSILException(3005);
      }
      paramHashMap.put("CustomerNameForReport", ((User)localObject3).getFullNameWithLoginId());
      localExtTransferCompany = new ExtTransferCompany();
      localExtTransferCompany.setCustID("" + (String)localObject1);
    }
    Object localObject2 = paramProperties.getProperty("Account");
    Object localObject3 = new ExtTransferAccount();
    Object localObject4 = ExternalTransferAdmin.getExtTransferAccounts(paramSecureUser, (ExtTransferAccount)localObject3, localExtTransferCompany, null, paramHashMap);
    if (localObject4 == null) {
      throw new CSILException(39710);
    }
    Object localObject5 = ((ExtTransferAccounts)localObject4).getByID((String)localObject2);
    if (localObject5 == null) {
      throw new CSILException(39710);
    }
    String str2 = ((ExtTransferAccount)localObject5).getRoutingNumber() + " : " + ((ExtTransferAccount)localObject5).getDisplayText() + " - " + ((ExtTransferAccount)localObject5).getNickname() + " - " + ((ExtTransferAccount)localObject5).getCurrencyCode();
    paramHashMap.put("AccountNameForReport", str2);
  }
  
  private static void jdMethod_null(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramProperties.getProperty("Business"));
    com.ffusion.beans.business.Business localBusiness2 = Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if ((localBusiness2 == null) || ((paramInt != 0) && (localBusiness2.getAffiliateBankID() != paramInt))) {
      throw new CSILException(4015);
    }
    String str1 = localBusiness2.getBusinessName();
    paramHashMap.put("BusinessNameForReport", str1);
    String str2 = paramProperties.getProperty("AccountGroup");
    BusinessAccountGroups localBusinessAccountGroups = AccountGroup.getAccountGroups(paramSecureUser, localBusiness1.getIdValue(), paramHashMap);
    if (localBusinessAccountGroups == null) {
      throw new CSILException(40007);
    }
    BusinessAccountGroup localBusinessAccountGroup = localBusinessAccountGroups.getByID(Integer.parseInt(str2));
    if (localBusinessAccountGroup == null) {
      throw new CSILException(40007);
    }
    String str3 = localBusinessAccountGroup.getName();
    paramHashMap.put("AccountGroupNameForReport", str3);
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, Properties paramProperties, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramProperties.getProperty("Business"));
    com.ffusion.beans.business.Business localBusiness2 = Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if ((localBusiness2 == null) || ((paramInt != 0) && (localBusiness2.getAffiliateBankID() != paramInt))) {
      throw new CSILException(4015);
    }
    String str1 = localBusiness2.getBusinessName();
    paramHashMap.put("BusinessNameForReport", str1);
    AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(paramSecureUser, localBusiness2.getAffiliateBankID(), paramHashMap);
    if (localAffiliateBank == null) {
      throw new CSILException(38506);
    }
    String str2 = paramProperties.getProperty("CashConCompany");
    paramHashMap.put("IncludeDeletedCompanies", "true");
    CashConCompanies localCashConCompanies = CashCon.getCashConCompanies(paramSecureUser, localBusiness1.getId(), localAffiliateBank.getFIBPWID(), paramHashMap);
    if (localCashConCompanies == null) {
      throw new CSILException(38506);
    }
    CashConCompany localCashConCompany = localCashConCompanies.getByID(str2);
    if (localCashConCompany == null) {
      throw new CSILException(38506);
    }
    String str3 = localCashConCompany.getCompanyName();
    paramHashMap.put("CashConCompanyNameForReport", str3);
  }
  
  private static String jdMethod_for(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || (str.equals(""))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  private static BankEmployees jdMethod_new(SecureUser paramSecureUser, int paramInt)
    throws CSILException
  {
    int i = paramSecureUser.getProfileID();
    String str = String.valueOf(i);
    BankEmployee localBankEmployee1 = new BankEmployee(paramSecureUser.getLocale());
    localBankEmployee1.setSupervisor(str);
    if (paramInt != 0) {
      localBankEmployee1.addAffiliateBankId(paramInt);
    }
    HashMap localHashMap = new HashMap();
    localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
    BankEmployees localBankEmployees1 = BankEmployeeAdmin.getBankEmployees(paramSecureUser, localBankEmployee1, localHashMap);
    BankEmployees localBankEmployees2 = new BankEmployees();
    for (int j = 0; j < localBankEmployees1.size(); j++)
    {
      BankEmployee localBankEmployee2 = (BankEmployee)localBankEmployees1.get(j);
      if (Entitlements.checkEntitlement(localBankEmployee2.getJobTypeId(), aBR)) {
        localBankEmployees2.add(localBankEmployee2);
      }
    }
    return localBankEmployees2;
  }
  
  private static BankEmployees jdMethod_for(SecureUser paramSecureUser, BankEmployee paramBankEmployee, int paramInt)
    throws CSILException
  {
    if (paramInt != 0) {
      paramBankEmployee.addAffiliateBankId(paramInt);
    }
    HashMap localHashMap = new HashMap();
    localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
    BankEmployees localBankEmployees1 = BankEmployeeAdmin.getBankEmployees(paramSecureUser, paramBankEmployee, localHashMap);
    BankEmployees localBankEmployees2 = new BankEmployees();
    for (int i = 0; i < localBankEmployees1.size(); i++)
    {
      BankEmployee localBankEmployee = (BankEmployee)localBankEmployees1.get(i);
      if (Entitlements.checkEntitlement(localBankEmployee.getJobTypeId(), aBR)) {
        localBankEmployees2.add(localBankEmployee);
      }
    }
    return localBankEmployees2;
  }
  
  private static BankEmployees jdMethod_for(SecureUser paramSecureUser, int paramInt)
    throws CSILException
  {
    HashMap localHashMap = new HashMap();
    localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
    BankEmployee localBankEmployee1 = new BankEmployee(paramSecureUser.getLocale());
    BankEmployees localBankEmployees1 = new BankEmployees(paramSecureUser.getLocale());
    BankEmployees localBankEmployees2 = null;
    if (paramInt != 0) {
      localBankEmployee1.addAffiliateBankId(paramInt);
    }
    localBankEmployee1.setId(Integer.toString(paramSecureUser.getProfileID()));
    localBankEmployees2 = BankEmployeeAdmin.getSubordinateBankEmployees(paramSecureUser, localBankEmployee1, localHashMap);
    Iterator localIterator = localBankEmployees2.iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee2 = (BankEmployee)localIterator.next();
      int i = localBankEmployee2.getJobTypeId();
      if (Entitlements.checkEntitlement(i, aBR)) {
        localBankEmployees1.add(localBankEmployee2);
      }
    }
    return localBankEmployees1;
  }
  
  private static BankEmployees jdMethod_for(SecureUser paramSecureUser, String paramString, int paramInt)
    throws CSILException
  {
    return jdMethod_for(paramSecureUser, paramString, paramInt, null);
  }
  
  private static BankEmployees jdMethod_for(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
    throws CSILException
  {
    String str1 = "BCReport.getBankEmployees";
    String str2 = String.valueOf(paramSecureUser.getProfileID());
    Object localObject1;
    BankEmployees localBankEmployees;
    Object localObject2;
    Object localObject3;
    if (paramString1.equals("AllAgents"))
    {
      if ((paramString2 != null) && (paramString2.equals("OBO CSR Activity Report")))
      {
        if (Entitlements.checkEntitlement(paramSecureUser.getEntitlementID(), aBU))
        {
          EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByType("EmployeeType");
          localObject1 = null;
          localBankEmployees = new BankEmployees(paramSecureUser.getLocale());
          localObject2 = localEntitlementGroups.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject1 = (EntitlementGroup)((Iterator)localObject2).next();
            if (Entitlements.checkEntitlement(((EntitlementGroup)localObject1).getGroupId(), aBR))
            {
              localObject3 = new HashMap();
              localObject3 = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass((HashMap)localObject3);
              localBankEmployees.set(BankEmployeeAdmin.getBankEmployeesByJobTypeId(paramSecureUser, ((EntitlementGroup)localObject1).getGroupId(), (HashMap)localObject3));
            }
          }
        }
        else
        {
          localBankEmployees = jdMethod_for(paramSecureUser, paramInt);
        }
      }
      else if ((paramString2 != null) && (paramString2.equals("CSR Activity Report"))) {
        localBankEmployees = jdMethod_for(paramSecureUser, paramInt);
      } else {
        localBankEmployees = jdMethod_new(paramSecureUser, paramInt);
      }
    }
    else
    {
      int i = 0;
      localObject1 = new BankEmployee(paramSecureUser.getLocale());
      localObject2 = new HashMap();
      localObject2 = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass((HashMap)localObject2);
      ((BankEmployee)localObject1).setId(paramString1);
      localObject1 = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject1, (HashMap)localObject2);
      if (localObject1 == null) {
        throw new CSILException(str1, 20, "No BankEmployee for id " + paramString1);
      }
      localBankEmployees = new BankEmployees();
      Object localObject4;
      Object localObject5;
      if ((paramString2 != null) && (paramString2.equals("OBO CSR Activity Report")))
      {
        if (Entitlements.checkEntitlement(paramSecureUser.getEntitlementID(), aBU))
        {
          localObject3 = Entitlements.getEntitlementGroupsByType("EmployeeType");
          localObject4 = null;
          localObject5 = new BankEmployees(paramSecureUser.getLocale());
          Iterator localIterator = ((EntitlementGroups)localObject3).iterator();
          while (localIterator.hasNext())
          {
            localObject4 = (EntitlementGroup)localIterator.next();
            if (Entitlements.checkEntitlement(((EntitlementGroup)localObject4).getGroupId(), aBR)) {
              ((BankEmployees)localObject5).set(BankEmployeeAdmin.getBankEmployeesByJobTypeId(paramSecureUser, ((EntitlementGroup)localObject4).getGroupId(), (HashMap)localObject2));
            }
          }
          if (((BankEmployees)localObject5).getByID(((BankEmployee)localObject1).getId()) != null) {
            i = 1;
          }
        }
        else
        {
          localObject3 = jdMethod_for(paramSecureUser, paramInt);
          if (((BankEmployees)localObject3).getByID(((BankEmployee)localObject1).getId()) != null) {
            i = 1;
          }
        }
      }
      else if ((paramString2 != null) && (paramString2.equals("CSR Activity Report")))
      {
        localObject3 = jdMethod_for(paramSecureUser, paramInt);
        if (((BankEmployees)localObject3).getByID(((BankEmployee)localObject1).getId()) != null) {
          i = 1;
        }
      }
      else if ((((BankEmployee)localObject1).getSupervisor().equals(str2)) || (paramString1.equals(str2)))
      {
        i = 1;
      }
      if (i != 0)
      {
        int j = 0;
        if (paramInt != 0)
        {
          localObject4 = String.valueOf(paramInt);
          if (((String)localObject4).equals(((BankEmployee)localObject1).getDefaultAffiliateBankId()))
          {
            j = 1;
          }
          else
          {
            localObject5 = ((BankEmployee)localObject1).getAffiliateBankIds();
            if (localObject5 != null) {
              for (int k = 0; (k < ((ArrayList)localObject5).size()) && (j == 0); k++) {
                if (((String)localObject4).equals(((ArrayList)localObject5).get(k))) {
                  j = 1;
                }
              }
            }
          }
        }
        else
        {
          j = 1;
        }
        if (j != 0) {
          localBankEmployees.add(localObject1);
        }
      }
    }
    return localBankEmployees;
  }
  
  private static BankEmployees jdMethod_int(SecureUser paramSecureUser, String paramString, int paramInt)
    throws CSILException
  {
    String str1 = "BCReport.getBankEmployeeByID";
    String str2 = String.valueOf(paramSecureUser.getProfileID());
    BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
    localBankEmployee.setId(paramString);
    HashMap localHashMap = new HashMap();
    localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
    localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, localHashMap);
    if (localBankEmployee == null) {
      throw new CSILException(str1, 20, "No BankEmployee for id " + paramString);
    }
    if (paramInt != 0)
    {
      String str3 = String.valueOf(paramInt);
      int i = 0;
      if ((str3.equals(localBankEmployee.getDefaultAffiliateBankId())) || (localBankEmployee.getDefaultAffiliateBankId().equals("0")))
      {
        i = 1;
      }
      else
      {
        ArrayList localArrayList = localBankEmployee.getAffiliateBankIds();
        if (localArrayList != null) {
          for (int j = 0; (j < localArrayList.size()) && (i == 0); j++) {
            if (str3.equals(localArrayList.get(j))) {
              i = 1;
            }
          }
        }
      }
      if (i == 0) {
        throw new CSILException(str1, 20, "No BankEmployee for id " + paramString);
      }
    }
    BankEmployees localBankEmployees = new BankEmployees();
    localBankEmployees.add(localBankEmployee);
    return localBankEmployees;
  }
  
  private static BankEmployees jdMethod_int(SecureUser paramSecureUser, int paramInt)
    throws CSILException
  {
    HashMap localHashMap = new HashMap();
    localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
    BankEmployee localBankEmployee = new BankEmployee(Locale.getDefault());
    localBankEmployee.setBankId(paramSecureUser.getBankID());
    if (paramInt != 0) {
      localBankEmployee.addAffiliateBankId(paramInt);
    }
    BankEmployees localBankEmployees = BankEmployeeAdmin.getBankEmployeeList(paramSecureUser, localBankEmployee, localHashMap);
    return localBankEmployees;
  }
  
  private static Businesses jdMethod_for(SecureUser paramSecureUser, BankEmployees paramBankEmployees, String paramString)
    throws CSILException
  {
    return jdMethod_for(paramSecureUser, paramBankEmployees, paramString, 0, true);
  }
  
  private static Businesses jdMethod_for(SecureUser paramSecureUser, BankEmployees paramBankEmployees, String paramString, int paramInt, boolean paramBoolean)
    throws CSILException
  {
    String str1 = "BCReport.getBusinessesByAgents";
    Businesses localBusinesses = new Businesses();
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business();
    if ((!paramString.equals("AllBusinesses")) && (!paramString.equals("AllConsumers")) && (!paramString.equals("AllBusinessesAndConsumers")))
    {
      localBusiness.setId(paramString);
      localBusiness = Business.getBusiness(paramSecureUser, localBusiness, null);
      if (localBusiness == null) {
        throw new CSILException(str1, 18, "No Business for id = " + paramString);
      }
      if ((paramInt != 0) && (localBusiness.getAffiliateBankID() != paramInt)) {
        throw new CSILException(str1, 30, "No Business for this affiliate bank");
      }
      localBusinesses.add(localBusiness);
      return localBusinesses;
    }
    if ((paramBoolean) && (paramString.equals("AllConsumers"))) {
      localBusinesses.add(null);
    } else {
      try
      {
        localBusiness.setBankId(paramSecureUser.getBankID());
        String str2 = null;
        if (paramInt != 0) {
          localBusiness.setAffiliateBankID(paramInt);
        }
        for (int i = 0; i < paramBankEmployees.size(); i++)
        {
          if (!Entitlements.checkEntitlement(paramSecureUser.getEntitlementID(), aBU))
          {
            str2 = ((BankEmployee)paramBankEmployees.get(i)).getId();
            localBusiness.setPersonalBanker(str2);
          }
          HashMap localHashMap = new HashMap();
          localHashMap.put("UseMaxRows", "false");
          localBusinesses.setUniquely(Business.getFilteredBusinesses(paramSecureUser, localBusiness, null, localHashMap));
        }
        if ((paramBoolean) && (paramString.equals("AllBusinessesAndConsumers"))) {
          localBusinesses.add(null);
        }
      }
      catch (Exception localException)
      {
        throw new CSILException(str1, 18, localException);
      }
    }
    return localBusinesses;
  }
  
  private static Businesses jdMethod_for(SecureUser paramSecureUser, String paramString, int paramInt, boolean paramBoolean)
    throws CSILException
  {
    String str = "BCReport.getBusinesses";
    Businesses localBusinesses = new Businesses();
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business();
    if (paramInt != 0) {
      localBusiness.setAffiliateBankID(paramInt);
    }
    if ((!paramString.equals("AllBusinesses")) && (!paramString.equals("AllConsumers")) && (!paramString.equals("AllBusinessesAndConsumers")))
    {
      localBusiness.setId(paramString);
      localBusiness = Business.getBusiness(paramSecureUser, localBusiness, null);
      if ((localBusiness == null) || ((localBusiness.getAffiliateBankID() != paramInt) && (paramInt != 0))) {
        throw new CSILException(str, 18, "No Business for id = " + paramString);
      }
      localBusinesses.add(localBusiness);
      return localBusinesses;
    }
    localBusiness.setBankId(paramSecureUser.getBankID());
    Object localObject;
    if ((paramBoolean) && (!Entitlements.checkEntitlement(paramSecureUser.getEntitlementID(), aBU)))
    {
      localObject = new Integer(paramSecureUser.getProfileID());
      localBusiness.setPersonalBanker(((Integer)localObject).toString());
    }
    if ((paramString.equals("AllBusinesses")) || (paramString.equals("AllBusinessesAndConsumers"))) {
      try
      {
        localObject = new HashMap();
        ((HashMap)localObject).put("UseMaxRows", "false");
        localBusinesses = Business.getFilteredBusinesses(paramSecureUser, localBusiness, null, (HashMap)localObject);
      }
      catch (Exception localException)
      {
        throw new CSILException(str, 18, localException);
      }
    }
    if ((paramString.equals("AllConsumers")) || (paramString.equals("AllBusinessesAndConsumers"))) {
      localBusinesses.add(null);
    }
    return localBusinesses;
  }
  
  private static Users jdMethod_for(SecureUser paramSecureUser, Businesses paramBusinesses, BankEmployees paramBankEmployees, String paramString1, String paramString2, int paramInt, boolean paramBoolean)
    throws CSILException
  {
    String str = "BCReportAdapter.getUsers";
    BusinessEmployee localBusinessEmployee = new BusinessEmployee();
    Users localUsers = new Users(paramSecureUser.getLocale());
    Object localObject1;
    Object localObject2;
    Object localObject4;
    Object localObject3;
    if (paramString2.equals("AllUsers"))
    {
      localObject1 = paramBusinesses.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localBusinessEmployee = new BusinessEmployee();
        if (paramInt != 0) {
          localBusinessEmployee.setAffiliateBankID(paramInt);
        }
        localObject2 = (com.ffusion.beans.business.Business)((Iterator)localObject1).next();
        if (localObject2 == null)
        {
          if ((paramString1.equals("AllBusinessesAndConsumers")) || (paramString1.equals("AllConsumers"))) {
            for (int i = 0; i < paramBankEmployees.size(); i++)
            {
              localObject4 = (BankEmployee)paramBankEmployees.get(i);
              localBusinessEmployee.setBankId(paramSecureUser.getBankID());
              if ((paramBoolean) && (!Entitlements.checkEntitlement(paramSecureUser.getEntitlementID(), aBU))) {
                localBusinessEmployee.setPersonalBanker(((BankEmployee)localObject4).getId());
              }
              HashMap localHashMap = new HashMap();
              localHashMap.put("UseMaxRows", "false");
              localUsers.setUniquely(UserAdmin.getConsumers(paramSecureUser, localBusinessEmployee, localHashMap));
            }
          }
        }
        else if ((paramString1.equals("AllBusinessesAndConsumers")) || (paramString1.equals("AllBusinesses")))
        {
          localBusinessEmployee.setBankId(paramSecureUser.getBankID());
          localObject3 = new HashMap();
          ((HashMap)localObject3).put("UseMaxRows", "false");
          localUsers.setUniquely(UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee, (HashMap)localObject3));
        }
        else
        {
          localBusinessEmployee.setBankId(paramSecureUser.getBankID());
          localBusinessEmployee.setBusinessId(((com.ffusion.beans.business.Business)localObject2).getIdValue());
          localObject3 = new HashMap();
          ((HashMap)localObject3).put("UseMaxRows", "false");
          localUsers.setUniquely(UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee, (HashMap)localObject3));
        }
      }
    }
    else if (paramString1.equals("AllConsumers"))
    {
      localObject1 = new User();
      localObject2 = null;
      ((User)localObject1).setId(paramString2);
      ((User)localObject1).setBankId(paramSecureUser.getBankID());
      localObject2 = UserAdmin.getConsumers(paramSecureUser, (User)localObject1, new HashMap());
      if (localObject2 != null)
      {
        localObject3 = ((Users)localObject2).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (User)((Iterator)localObject3).next();
          if ((paramInt == 0) || (((User)localObject4).getAffiliateBankID() == paramInt)) {
            localUsers.add(localObject4);
          }
        }
      }
    }
    else
    {
      localBusinessEmployee.setBankId(paramSecureUser.getBankID());
      localBusinessEmployee.setId(paramString2);
      if (paramInt != 0) {
        localBusinessEmployee.setAffiliateBankID(paramInt);
      }
      localObject1 = new HashMap();
      ((HashMap)localObject1).put("UseMaxRows", "false");
      localUsers.setUniquely(UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee, (HashMap)localObject1));
    }
    return localUsers;
  }
  
  private static ArrayList k(int paramInt)
    throws CSILException
  {
    String str = "getDivisionAndGroupIds";
    ArrayList localArrayList = new ArrayList();
    EntitlementGroup localEntitlementGroup1 = Entitlements.getEntitlementGroup(paramInt);
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    jdMethod_byte(localEntitlementGroup1, localEntitlementGroups);
    if ((localEntitlementGroups != null) && (localEntitlementGroups.size() != 0))
    {
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator.next();
        localArrayList.add(String.valueOf(localEntitlementGroup2.getGroupId()));
      }
    }
    return localArrayList;
  }
  
  private static void jdMethod_byte(EntitlementGroup paramEntitlementGroup, EntitlementGroups paramEntitlementGroups)
    throws CSILException
  {
    String str = "recurseDivisionsAndGroups";
    if ((paramEntitlementGroup.getEntGroupType().equals("Division")) || (paramEntitlementGroup.getEntGroupType().equals("Group"))) {
      paramEntitlementGroups.add(paramEntitlementGroup);
    }
    Iterator localIterator = null;
    EntitlementGroups localEntitlementGroups = null;
    EntitlementGroup localEntitlementGroup = null;
    localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId());
    if (localEntitlementGroups != null)
    {
      localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup = (EntitlementGroup)localIterator.next();
        jdMethod_byte(localEntitlementGroup, paramEntitlementGroups);
      }
    }
  }
  
  private static boolean jdMethod_try(SecureUser paramSecureUser, String paramString)
    throws CSILException
  {
    String str1 = "entitled";
    boolean bool1 = true;
    String str2 = (String)aBT.get(paramString);
    if ((str2 == null) || (str2.equals("")))
    {
      localObject = "Unable to get entitlement for this report.";
      throw new CSILException(str1, 27, (String)localObject);
    }
    Object localObject = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    String str3 = null;
    boolean bool2 = false;
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = Entitlements.getRestrictedEntitlements((EntitlementGroupMember)localObject, paramSecureUser.getEntitlementID());
    Iterator localIterator = localEntitlements.iterator();
    while ((bool1) && (localIterator.hasNext()))
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      str3 = localEntitlement.getOperationName();
      bool2 = Strings.areStringsEqual(str2, str3);
      bool1 = !bool2;
    }
    return bool1;
  }
  
  private static HashMap W()
  {
    String str = "createReportTypeToEntitlementsMap";
    HashMap localHashMap = new HashMap();
    localHashMap.put("Bank Employee Activity Report", "BC Bank Employee Activity Report");
    localHashMap.put("CSR Activity Report", "BC CSR Activity Report");
    localHashMap.put("OBO CSR Team Activity Report", "BC OBO Team Activity Report");
    localHashMap.put("OBO CSR Activity Report", "BC OBO Activity Report");
    localHashMap.put("My OBO Activity Report", "BC My OBO Activity Report");
    localHashMap.put("Bank Employee Permissions Report", "BC Bank Employee Permissions Report");
    localHashMap.put("User Activity Report", "BC User Activity Report");
    localHashMap.put("Customer Permissions Report", "BC Customer Permissions Report");
    localHashMap.put("Login Activity Report", "BC Login Activity Report");
    localHashMap.put("Login Summary Report", "BC Login Summary Report");
    localHashMap.put("Market Segment History Report", "BC Market Segment History Report");
    localHashMap.put("Service Package History Report", "BC Service Package History Report");
    localHashMap.put("Bank Employee History Report", "BC Bank Employee History Report");
    localHashMap.put("Customer History Report", "BC Customer History History Report");
    localHashMap.put("Business History Report", "BC Business History Report - Customer Modifications");
    localHashMap.put("Transaction History Report", "BC Transaction History Report");
    localHashMap.put("Managed Participant History Report", "BC Managed Participant History Report");
    localHashMap.put("Beneficiary History Report", "BC Beneficiary History Report");
    localHashMap.put("Payee History Report", "BC Payee History Report");
    localHashMap.put("ACH Company History Report", "BC ACH Company History Report");
    localHashMap.put("Account Group History Report", "BC Account Group History Report");
    localHashMap.put("Cash Concentration Company History Report", "BC Cash Con Company History Report");
    localHashMap.put("External Transfer ACH Batch Information Report", "BC External Transfer ACH Batch Information History Report");
    localHashMap.put("External Transfer Account Report", "BC External Transfer Account History Report");
    localHashMap.put("CSR Team Performance Report", "BC Cases Team Performance Report");
    localHashMap.put("CSR Performance Report", "BC Cases Performance Report");
    localHashMap.put("My Performance Report", "BC My Cases Performance Report");
    localHashMap.put("My Organization's Performance Report", "BC My Organizations Performance Report");
    localHashMap.put("Business Service Charge Report", "BC Business Service Charge Report");
    localHashMap.put("Consumer Service Charge Report", "BC Consumer Service Charge Report");
    localHashMap.put("Wire Report", "BC Wire Report");
    localHashMap.put("Wire Exception Items Report", "BC Wire Exception Items Report");
    localHashMap.put("ACH Batch Report", "BC ACH Batch Report");
    localHashMap.put("ACH File Report", "BC ACH File Report");
    localHashMap.put("ACH File Upload Report", "BC ACH File Upload Report");
    localHashMap.put("External Transfer ACH File Report", "BC External Transfer ACH File Report");
    localHashMap.put("Tax Payment Report", "BC Tax Payment Report");
    localHashMap.put("Child Support Payment Report", "BC Child Support Payment Report");
    localHashMap.put("Transfer Report", "BC Transfer Report");
    localHashMap.put("Transfer Exception Items Report", "BC Transfer Exception Items Report");
    localHashMap.put("External Transfer Report", "BC External Transfer Report");
    localHashMap.put("External Transfer Deposit Verification Report", "BC External Transfer Deposit Verification Report");
    localHashMap.put("External Transfer Exception Items Report", "BC External Transfer Exception Items Report");
    localHashMap.put("Bill Payment Report", "BC Bill Payment Report");
    localHashMap.put("Bill Payment Exception Items Report", "BC Bill Payment Exception Items Report");
    localHashMap.put("Positive Pay Exceptions Report", "BC Positive Pay Issues Report");
    localHashMap.put("Positive Pay Decisions Report", "BC Positive Pay Decisions Report");
    localHashMap.put("BC Non-zero Balance Report", "BC Non-zero Balance Report");
    localHashMap.put("Cash Concentration Cut-Off Status Report", "BC Cash Concentration Cutoff Status Report");
    localHashMap.put("Cash Concentration Inactive Divisions and Locations Report", "BC Cash Concentration Inactive Divisions and Locations Report");
    localHashMap.put("Cash Concentration Company Activity Report", "BC Cash Concentration Company Activity Report");
    localHashMap.put("Inactive Business Report", "BC Inactive Business Report");
    localHashMap.put("Inactive Business Module Report", "BC Inactive Business Module Report");
    localHashMap.put("File Activity Report", "BC File Management Report");
    try
    {
      BCReportHandler.appendReportTypeEntitlements(localHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.SEVERE, str + ':' + localCSILException.toString());
    }
    return localHashMap;
  }
  
  private static boolean T(String paramString)
  {
    boolean bool = true;
    if ((paramString.equals("Bank Employee Activity Report")) || (paramString.equals("CSR Activity Report")) || (paramString.equals("OBO CSR Team Activity Report")) || (paramString.equals("OBO CSR Activity Report")) || (paramString.equals("My OBO Activity Report")) || (paramString.equals("Bank Employee Permissions Report")) || (paramString.equals("User Activity Report")) || (paramString.equals("Customer Permissions Report")) || (paramString.equals("Login Activity Report")) || (paramString.equals("Login Summary Report")) || (paramString.equals("Market Segment History Report")) || (paramString.equals("Service Package History Report")) || (paramString.equals("Bank Employee History Report")) || (paramString.equals("Customer History Report")) || (paramString.equals("Business History Report")) || (paramString.equals("Transaction History Report")) || (paramString.equals("Managed Participant History Report")) || (paramString.equals("Beneficiary History Report")) || (paramString.equals("Payee History Report")) || (paramString.equals("ACH Company History Report")) || (paramString.equals("Account Group History Report")) || (paramString.equals("Cash Concentration Company History Report")) || (paramString.equals("External Transfer ACH Batch Information Report")) || (paramString.equals("External Transfer Account Report")) || (paramString.equals("CSR Team Performance Report")) || (paramString.equals("CSR Performance Report")) || (paramString.equals("My Performance Report")) || (paramString.equals("My Organization's Performance Report")) || (paramString.equals("Business Service Charge Report")) || (paramString.equals("Consumer Service Charge Report")) || (paramString.equals("Wire Report")) || (paramString.equals("Wire Exception Items Report")) || (paramString.equals("ACH Batch Report")) || (paramString.equals("Tax Payment Report")) || (paramString.equals("Child Support Payment Report")) || (paramString.equals("ACH File Report")) || (paramString.equals("ACH File Upload Report")) || (paramString.equals("External Transfer ACH File Report")) || (paramString.equals("Transfer Report")) || (paramString.equals("Transfer Exception Items Report")) || (paramString.equals("External Transfer Report")) || (paramString.equals("External Transfer Deposit Verification Report")) || (paramString.equals("External Transfer Exception Items Report")) || (paramString.equals("Bill Payment Report")) || (paramString.equals("Bill Payment Exception Items Report")) || (paramString.equals("Inactive Business Report")) || (paramString.equals("Inactive Business Module Report")) || (paramString.equals("Positive Pay Exceptions Report")) || (paramString.equals("Positive Pay Decisions Report")) || (paramString.equals("File Activity Report")) || (paramString.equals("Cash Concentration Cut-Off Status Report")) || (paramString.equals("Cash Concentration Inactive Divisions and Locations Report")) || (paramString.equals("Cash Concentration Company Activity Report"))) {
      bool = false;
    }
    return bool;
  }
  
  private static void jdMethod_for(EntitlementTypePropertyLists paramEntitlementTypePropertyLists, ArrayList paramArrayList)
  {
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = null;
    Iterator localIterator = null;
    Object localObject = null;
    ArrayList localArrayList = null;
    EntitlementTypePropertyList localEntitlementTypePropertyList1 = null;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    if (paramEntitlementTypePropertyLists != null)
    {
      if (paramArrayList == null) {
        paramArrayList = new ArrayList();
      }
      localIterator = paramEntitlementTypePropertyLists.iterator();
      while (localIterator.hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList2 = (EntitlementTypePropertyList)localIterator.next();
        if (!paramArrayList.contains(localEntitlementTypePropertyList2.getOperationName()))
        {
          localHashMap2 = localEntitlementTypePropertyList2.getPropertiesMap();
          localObject = localHashMap2.get("category");
          if ((localObject != null) && ((localObject instanceof ArrayList)) && (((ArrayList)localObject).contains("per account")))
          {
            localIterator.remove();
            localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)localEntitlementTypePropertyList2.clone();
            localHashMap1 = (HashMap)localHashMap2.clone();
            localArrayList = (ArrayList)((ArrayList)localObject).clone();
            int i = localArrayList.indexOf("per account");
            localArrayList.remove(i);
            localHashMap1.put("category", localArrayList);
            localEntitlementTypePropertyList1.setPropertiesMap(localHashMap1);
            localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList1);
          }
        }
      }
      paramEntitlementTypePropertyLists.addAll(localEntitlementTypePropertyLists);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.BCReport
 * JD-Core Version:    0.7.0.1
 */