package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.bcreport.BCReportConsts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.UserUtil;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyList;
import com.ffusion.csil.beans.entitlements.TypePropertyList;
import com.ffusion.csil.beans.entitlementsReporting.ReportEntitlementComparator;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.csil.core.CashCon;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Wire;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.positivepay.adapter.PPayAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.admin.AdminException;
import com.ffusion.treasurydirect.TreasuryDirectAdapter;
import com.ffusion.util.CollatorUtil;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class BCReportAdapter
  implements BCReportConsts
{
  protected static MessageAdapter messageAdapter = new MessageAdapter();
  protected static BusinessReportAdapter businessReportAdapter = new BusinessReportAdapter();
  protected static HistoryReportAdapter historyReportAdapter = new HistoryReportAdapter();
  protected static PPayAdapter pPayAdapter = new PPayAdapter();
  private static final String jdField_try = "ENTITLEMENT_OBJECT_ID";
  private static final String jdField_int = com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("Overall", "ACH Payment Approval");
  private static final String jdField_do = com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("Overall", "ACH Payment Entry");
  private static final Entitlement jdField_case = new Entitlement("Approvals Admin", null, null);
  private static final Entitlement jdField_char = new Entitlement("ACHBatch", null, null);
  private static final Entitlement jdField_for = new Entitlement("CCD + DED", null, null);
  private static final Entitlement jdField_if = new Entitlement("CCD + TXP", null, null);
  private static final Entitlement jdField_byte = new Entitlement("Cash Con - Disbursement Request", null, null);
  private static final Entitlement a = new Entitlement("Cash Con - Deposit Entry", null, null);
  private static HashMap jdField_new;
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException
  {
    String str1 = "BCReportAdapter.getReportData";
    try
    {
      Properties localProperties = paramReportCriteria.getReportOptions();
      String str2 = localProperties.getProperty("REPORTTYPE");
      if (str2 == null)
      {
        String str3 = "The report options contained within the Report Criteria used in a call to getBCReportData does not contain a report type.";
        throw new BCReportException(11);
      }
      if (str2.equals("CSR Team Performance Report")) {
        return messageAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("CSR Performance Report")) {
        return messageAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("My Performance Report")) {
        return messageAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("My Organization's Performance Report")) {
        return messageAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Market Segment History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Service Package History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Bank Employee History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Customer History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Business History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Transaction History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Managed Participant History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Beneficiary History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Payee History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("ACH Company History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Account Group History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Cash Concentration Company History Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("External Transfer ACH Batch Information Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("External Transfer Account Report")) {
        return historyReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if ((str2.equals("Inactive Business Report")) || (str2.equals("Inactive Business Module Report"))) {
        return businessReportAdapter.getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if ((str2.equals("ACH Batch Report")) || (str2.equals("ACH File Report")) || (str2.equals("ACH File Upload Report")) || (str2.equals("External Transfer ACH File Report")) || (str2.equals("Tax Payment Report")) || (str2.equals("Child Support Payment Report")) || (str2.equals("Wire Report")) || (str2.equals("Wire Exception Items Report")) || (str2.equals("Bill Payment Report")) || (str2.equals("Bill Payment Exception Items Report")) || (str2.equals("Transfer Report")) || (str2.equals("Transfer Exception Items Report")) || (str2.equals("External Transfer Report")) || (str2.equals("External Transfer Deposit Verification Report")) || (str2.equals("External Transfer Exception Items Report")) || (str2.equals("Cash Concentration Cut-Off Status Report")) || (str2.equals("Cash Concentration Inactive Divisions and Locations Report")) || (str2.equals("Cash Concentration Company Activity Report"))) {
        return ReportTransactionAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Bank Employee Permissions Report")) {
        return getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Customer Permissions Report")) {
        return getBCReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("BC Non-zero Balance Report")) {
        return TreasuryDirectAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      return ReportAuditAdapter.getBCReportData(paramSecureUser, (ReportCriteria)paramReportCriteria.clone(), paramHashMap, paramReportCriteria);
    }
    catch (AdminException localAdminException)
    {
      throw new BCReportException(localAdminException, str1, 19);
    }
    catch (BCReportException localBCReportException)
    {
      throw new BCReportException(localBCReportException, str1, localBCReportException.code);
    }
    catch (Exception localException)
    {
      throw new BCReportException(localException, str1, 21);
    }
  }
  
  public IReportResult getBCReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException
  {
    String str = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    if (str.equals("Bank Employee Permissions Report")) {
      return jdField_if(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    if (str.equals("Customer Permissions Report")) {
      return a(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    throw new BCReportException(11);
  }
  
  private IReportResult jdField_if(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException
  {
    ReportResult localReportResult = new ReportResult(paramSecureUser.getLocale());
    Locale localLocale = localReportResult.getLocale();
    int i = 0;
    int j = Integer.parseInt(a(paramReportCriteria.getReportOptions(), "MAXDISPLAYSIZE", String.valueOf(2147483647)));
    String str1 = "BCReportAdapter.getBankEmpPermissionReport";
    int k = 0;
    BankEmployees localBankEmployees1 = (BankEmployees)paramHashMap.get("BankEmployeesForReport");
    EntitlementGroups localEntitlementGroups = (EntitlementGroups)paramHashMap.get("JobTypesForReport");
    String str2 = a(paramReportCriteria.getSearchCriteria(), "JobType", "AllJobTypes");
    Object localObject4;
    if (str2.equals("AllJobTypes"))
    {
      paramReportCriteria.setDisplayValue("JobType", ReportConsts.getText(10005, localLocale));
    }
    else
    {
      int m = Integer.parseInt(str2);
      for (int n = 0; n < localEntitlementGroups.size(); n++)
      {
        localObject2 = (EntitlementGroup)localEntitlementGroups.get(n);
        if (((EntitlementGroup)localObject2).getGroupId() == m)
        {
          localObject3 = ((EntitlementGroup)localObject2).getProperties();
          localObject4 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue((EntitlementGroupProperties)localObject3, "display name", paramSecureUser.getLocale());
          if (localObject4 == null) {
            localObject4 = ((EntitlementGroup)localObject2).getGroupName();
          }
          paramReportCriteria.setDisplayValue("JobType", (String)localObject4);
          break;
        }
      }
    }
    String str3 = a(paramReportCriteria.getSearchCriteria(), "User", "AllUsers");
    if (str3.equals("AllUsers"))
    {
      paramReportCriteria.setDisplayValue("User", ReportConsts.getText(10017, localLocale));
    }
    else
    {
      localObject1 = localBankEmployees1.getByID(str3);
      if (localObject1 != null) {
        paramReportCriteria.setDisplayValue("User", UserUtil.getFullNameWithLoginId(((BankEmployee)localObject1).getFirstName(), ((BankEmployee)localObject1).getLastName(), ((BankEmployee)localObject1).getUserName(), ((BankEmployee)localObject1).getEmployeeId(), localLocale));
      } else {
        paramReportCriteria.setDisplayValue("User", "");
      }
    }
    Object localObject1 = a(paramReportCriteria.getSearchCriteria(), "Status", "AllStatuses");
    if (((String)localObject1).equals("AllStatuses")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10018, localLocale));
    } else if (((String)localObject1).equals("Active")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10002, localLocale));
    } else if (((String)localObject1).equals("Inactive")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10003, localLocale));
    } else if (((String)localObject1).equals("Temporarily Inactive")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10019, localLocale));
    }
    Object localObject2 = a(paramReportCriteria.getSearchCriteria(), "Activity", "AllActivities");
    Object localObject5;
    if (((String)localObject2).equals("AllActivities"))
    {
      paramReportCriteria.setDisplayValue("Activity", ReportConsts.getText(10007, localLocale));
    }
    else
    {
      localObject3 = (EntitlementTypePropertyLists)paramHashMap.get("EntitlementTypesForReport");
      localObject4 = ((EntitlementTypePropertyLists)localObject3).getByOperationName((String)localObject2);
      if (localObject4 != null)
      {
        localObject5 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue((TypePropertyList)localObject4, "display name", localLocale);
        if (localObject5 == null) {
          localObject5 = localObject2;
        }
        paramReportCriteria.setDisplayValue("Activity", (String)localObject5);
      }
    }
    Object localObject3 = a(paramReportCriteria.getSearchCriteria(), "Affiliate Bank", "AllAffiliateBanks");
    if (((String)localObject3).equals("AllAffiliateBanks"))
    {
      paramReportCriteria.setDisplayValue("Affiliate Bank", ReportConsts.getText(10048, localLocale));
    }
    else
    {
      localObject4 = (String)paramHashMap.get("AffiliateBankNameForReport");
      paramReportCriteria.setDisplayValue("Affiliate Bank", (String)localObject4);
    }
    try
    {
      localReportResult.init(paramReportCriteria);
      localObject4 = new BankEmployees();
      ((BankEmployees)localObject4).set(localBankEmployees1);
      localObject5 = a(paramReportCriteria.getSearchCriteria(), "JobType", "AllJobTypes");
      String str4 = a(paramReportCriteria.getSearchCriteria(), "User", "AllUsers");
      Object localObject6;
      if ((!((String)localObject5).equals("AllJobTypes")) || (!str4.equals("AllUsers")))
      {
        int i1 = 0;
        try
        {
          i1 = Integer.parseInt((String)localObject5);
        }
        catch (Exception localException2) {}
        localObject6 = ((BankEmployees)localObject4).iterator();
        while (((Iterator)localObject6).hasNext())
        {
          BankEmployee localBankEmployee1 = (BankEmployee)((Iterator)localObject6).next();
          if ((!((String)localObject5).equals("AllJobTypes")) && (i1 != localBankEmployee1.getJobTypeId())) {
            ((Iterator)localObject6).remove();
          } else if ((!str4.equals("AllUsers")) && (!str4.equals(localBankEmployee1.getId()))) {
            ((Iterator)localObject6).remove();
          }
        }
      }
      String str5 = a(paramReportCriteria.getSortCriteria(), 1, "User");
      Object localObject7;
      if (str5.equals("User"))
      {
        ((BankEmployees)localObject4).setSortedBy("LAST");
      }
      else if (!((String)localObject5).equals("AllJobTypes"))
      {
        ((BankEmployees)localObject4).setSortedBy("LAST");
      }
      else
      {
        localObject6 = new HashMap();
        Object localObject8;
        BankEmployees localBankEmployees2;
        for (int i3 = 0; i3 < ((BankEmployees)localObject4).size(); i3++)
        {
          BankEmployee localBankEmployee2 = (BankEmployee)((BankEmployees)localObject4).get(i3);
          localObject8 = String.valueOf(localBankEmployee2.getJobTypeId());
          if (((HashMap)localObject6).containsKey(localObject8))
          {
            localBankEmployees2 = (BankEmployees)((HashMap)localObject6).get(localObject8);
            localBankEmployees2.add(localBankEmployee2);
          }
          else
          {
            localBankEmployees2 = new BankEmployees();
            localBankEmployees2.add(localBankEmployee2);
            ((HashMap)localObject6).put(localObject8, localBankEmployees2);
          }
        }
        localObject7 = new HashMap();
        for (int i4 = 0; i4 < localEntitlementGroups.size(); i4++)
        {
          localObject8 = (EntitlementGroup)localEntitlementGroups.get(i4);
          ((HashMap)localObject7).put(((EntitlementGroup)localObject8).getGroupName(), String.valueOf(((EntitlementGroup)localObject8).getGroupId()));
        }
        Object[] arrayOfObject = ((HashMap)localObject7).keySet().toArray();
        Arrays.sort(arrayOfObject, CollatorUtil.getCollator());
        ((BankEmployees)localObject4).clear();
        for (int i5 = 0; i5 < arrayOfObject.length; i5++)
        {
          localBankEmployees2 = (BankEmployees)((HashMap)localObject6).get(((HashMap)localObject7).get((String)arrayOfObject[i5]));
          localBankEmployees2.setSortedBy("LAST");
          ((BankEmployees)localObject4).addAll(localBankEmployees2);
        }
      }
      for (int i2 = 0; i2 < ((BankEmployees)localObject4).size(); i2++)
      {
        localObject7 = (BankEmployee)((BankEmployees)localObject4).get(i2);
        if ((((String)localObject1).equals("AllStatuses")) || ((((String)localObject1).equals("Active")) && (((BankEmployee)localObject7).getStatusInt() == 0)) || ((((String)localObject1).equals("Inactive")) && (((BankEmployee)localObject7).getStatusInt() == 1)) || ((((String)localObject1).equals("Temporarily Inactive")) && (((BankEmployee)localObject7).getStatusInt() == 2))) {
          i = a(paramReportCriteria, (BankEmployee)localObject7, localBankEmployees1, localReportResult, localEntitlementGroups, i, j, paramHashMap);
        }
        if (i == j) {
          break;
        }
      }
    }
    catch (Exception localException1)
    {
      k = 1;
      localReportResult.setError(localException1);
      throw new BCReportException(localException1, str1, 21);
    }
    finally
    {
      if (i == j)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap);
      }
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        if (k == 0) {
          throw new BCReportException(localRptException, str1, 21);
        }
      }
    }
    return localReportResult;
  }
  
  private IReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException
  {
    ReportResult localReportResult = new ReportResult(paramSecureUser.getLocale());
    Locale localLocale = paramSecureUser.getLocale();
    int i = 0;
    int j = Integer.parseInt(a(paramReportCriteria.getReportOptions(), "MAXDISPLAYSIZE", String.valueOf(2147483647)));
    String str1 = "BCReportAdapter.getCustomerPermissionReport";
    int k = 0;
    Businesses localBusinesses = (Businesses)paramHashMap.get("BusinessesForReport");
    Users localUsers = (Users)paramHashMap.get("UsersForReport");
    String str2 = a(paramReportCriteria.getSearchCriteria(), "Business", "AllBusinessesAndConsumers");
    if (str2.equals("AllBusinessesAndConsumers"))
    {
      paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10014, localLocale));
    }
    else if (str2.equals("AllBusinesses"))
    {
      paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10015, localLocale));
    }
    else if (str2.equals("AllConsumers"))
    {
      paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10016, localLocale));
    }
    else
    {
      localObject1 = localBusinesses.getById(Integer.parseInt(str2));
      localObject2 = new StringBuffer(((Business)localObject1).getBusinessName());
      str3 = ((Business)localObject1).getCustId();
      if (str3 != null)
      {
        str3 = str3.trim();
        if (str3.length() > 0) {
          ((StringBuffer)localObject2).append("(").append(str3).append(")");
        }
      }
      paramReportCriteria.setDisplayValue("Business", ((StringBuffer)localObject2).toString());
    }
    Object localObject1 = a(paramReportCriteria.getSearchCriteria(), "User", "AllUsers");
    if (((String)localObject1).equals("AllUsers"))
    {
      paramReportCriteria.setDisplayValue("User", ReportConsts.getText(10017, localLocale));
    }
    else
    {
      localObject2 = localUsers.getByID((String)localObject1);
      if (localObject2 != null) {
        paramReportCriteria.setDisplayValue("User", UserUtil.getFullNameWithLoginId(((User)localObject2).getFirstName(), ((User)localObject2).getLastName(), ((User)localObject2).getUserName(), ((User)localObject2).getCustId(), localLocale));
      } else {
        paramReportCriteria.setDisplayValue("User", "");
      }
    }
    Object localObject2 = a(paramReportCriteria.getSearchCriteria(), "Status", "AllStatuses");
    if (((String)localObject2).equals("AllStatuses")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10018, localLocale));
    } else if (((String)localObject2).equals("Active")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10002, localLocale));
    } else if (((String)localObject2).equals("Inactive")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10003, localLocale));
    } else if (((String)localObject2).equals("Temporarily Inactive")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10019, localLocale));
    } else if (((String)localObject2).equals("Enrolled")) {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10004, localLocale));
    }
    String str3 = a(paramReportCriteria.getSearchCriteria(), "Activity", "AllActivities");
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    Object localObject9;
    Object localObject10;
    if (str3.equals("AllActivities"))
    {
      paramReportCriteria.setDisplayValue("Activity", ReportConsts.getText(10007, localLocale));
    }
    else
    {
      if (str2.equals("AllConsumers")) {
        localObject3 = (EntitlementTypePropertyLists)paramHashMap.get("ConsumerEntTypesForReport");
      } else {
        localObject3 = (EntitlementTypePropertyLists)paramHashMap.get("BusEmpEntTypesForReport");
      }
      localObject4 = ((EntitlementTypePropertyLists)localObject3).getByOperationName(str3);
      localObject5 = ReportConsts.getText(10415, paramSecureUser.getLocale());
      localObject6 = null;
      localObject7 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue((TypePropertyList)localObject4, "display name", localLocale);
      if (localObject7 == null)
      {
        ((EntitlementTypePropertyList)localObject4).setCurrentProperty("display name");
        localObject7 = ((EntitlementTypePropertyList)localObject4).getValue();
      }
      localObject8 = localObject7;
      try
      {
        localObject6 = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      }
      catch (CSILException localCSILException) {}
      if (localObject4 != null)
      {
        ((EntitlementTypePropertyList)localObject4).setCurrentProperty("display parent");
        String str4 = ((EntitlementTypePropertyList)localObject4).getValue();
        if ((((EntitlementTypePropertyList)localObject4).isPropertySet("category", "per ACH company")) || (((EntitlementTypePropertyList)localObject4).isPropertySet("category", "cross ACH company")))
        {
          ((EntitlementTypePropertyList)localObject4).setCurrentProperty("module_name");
          localObject9 = ((EntitlementTypePropertyList)localObject4).getValue();
          if ((str4.equals("")) && (((String)localObject9).equals("ACH")))
          {
            localObject8 = (String)localObject5 + " - " + (String)localObject7;
          }
          else if (!str4.equals(""))
          {
            EntitlementTypePropertyList localEntitlementTypePropertyList = ((EntitlementTypePropertyLists)localObject6).getByOperationName(str4);
            localObject10 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList, "display name", localLocale);
            if (localObject10 == null)
            {
              localEntitlementTypePropertyList.setCurrentProperty("display name");
              localObject10 = localEntitlementTypePropertyList.getValue();
            }
            localObject8 = (String)localObject10 + " - " + (String)localObject7;
          }
        }
        paramReportCriteria.setDisplayValue("Activity", (String)localObject8);
      }
    }
    Object localObject3 = a(paramReportCriteria.getSearchCriteria(), "Affiliate Bank", "AllAffiliateBanks");
    if (((String)localObject3).equals("AllAffiliateBanks"))
    {
      paramReportCriteria.setDisplayValue("Affiliate Bank", ReportConsts.getText(10048, localLocale));
    }
    else
    {
      localObject4 = (String)paramHashMap.get("AffiliateBankNameForReport");
      paramReportCriteria.setDisplayValue("Affiliate Bank", (String)localObject4);
    }
    Object localObject4 = a(paramReportCriteria.getSearchCriteria(), "IncludeSecondaryUsers", "");
    if (((String)localObject4).equals("on")) {
      paramReportCriteria.setDisplayValue("IncludeSecondaryUsers", ReportConsts.getText(10081, localLocale));
    } else if (((String)localObject4).equals("off")) {
      paramReportCriteria.setDisplayValue("IncludeSecondaryUsers", ReportConsts.getText(10082, localLocale));
    }
    try
    {
      localReportResult.init(paramReportCriteria);
      localObject5 = new Users(Locale.getDefault());
      localObject6 = localUsers.iterator();
      if (str2.equals("AllBusinessesAndConsumers"))
      {
        ((Users)localObject5).addAll(localUsers);
      }
      else
      {
        if (str2.equals("AllBusinesses")) {
          while (((Iterator)localObject6).hasNext())
          {
            localObject7 = (User)((Iterator)localObject6).next();
            if ((localObject7 instanceof BusinessEmployee)) {
              ((Users)localObject5).add(localObject7);
            }
          }
        }
        if (str2.equals("AllConsumers")) {
          while (((Iterator)localObject6).hasNext())
          {
            localObject7 = (User)((Iterator)localObject6).next();
            if ((!(localObject7 instanceof BusinessEmployee)) && ((((String)localObject1).equals("AllUsers")) || (((String)localObject1).equals(((User)localObject7).getId())))) {
              ((Users)localObject5).add(localObject7);
            }
          }
        }
        while (((Iterator)localObject6).hasNext())
        {
          localObject7 = (User)((Iterator)localObject6).next();
          if (((localObject7 instanceof BusinessEmployee)) && (((BusinessEmployee)localObject7).getBusinessId() == Integer.parseInt(str2)) && ((((String)localObject1).equals("AllUsers")) || (((String)localObject1).equals(((User)localObject7).getId())))) {
            ((Users)localObject5).add(localObject7);
          }
        }
      }
      localObject7 = a(paramReportCriteria.getSortCriteria(), 1, "User");
      if (((String)localObject7).equals("User"))
      {
        ((Users)localObject5).setSortedBy("LAST");
      }
      else if ((str2.equals("AllBusinessesAndConsumers")) || (str2.equals("AllBusinesses")))
      {
        localObject8 = new BCReportUsersComparator(localBusinesses);
        Collections.sort((List)localObject5, (Comparator)localObject8);
      }
      localObject8 = (Users)paramHashMap.get("SecondaryUsersForReport");
      if (localObject8 != null) {
        ((Users)localObject5).addAll((Collection)localObject8);
      }
      for (int m = 0; m < ((Users)localObject5).size(); m++)
      {
        localObject9 = (User)((Users)localObject5).get(m);
        int n = a(((User)localObject9).getEntitlementGroupId());
        localObject10 = new HashMap();
        if ((((String)localObject2).equals("AllStatuses")) || ((((String)localObject2).equals("Active")) && (((User)localObject9).getAccountStatus().equals(User.STATUS_ACTIVE))) || ((((String)localObject2).equals("Inactive")) && (((User)localObject9).getAccountStatus().equals(User.STATUS_INACTIVE))) || ((((String)localObject2).equals("Temporarily Inactive")) && (((User)localObject9).getAccountStatus().equals(User.STATUS_TEMP_INACTIVE))) || ((((String)localObject2).equals("Enrolled")) && (((User)localObject9).getAccountStatus().equals(User.STATUS_ENROLLED)))) {
          i = a(paramSecureUser, (User)localObject9, localBusinesses, localReportResult, i, j, n, (HashMap)localObject10, paramReportCriteria, paramHashMap);
        }
        if (i == j) {
          break;
        }
      }
    }
    catch (Exception localException)
    {
      k = 1;
      localReportResult.setError(localException);
      throw new BCReportException(localException, str1, 21);
    }
    finally
    {
      if (i == j)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap);
      }
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        if (k == 0) {
          throw new BCReportException(localRptException, str1, 21);
        }
      }
    }
    return localReportResult;
  }
  
  private int a(ReportCriteria paramReportCriteria, BankEmployee paramBankEmployee, BankEmployees paramBankEmployees, ReportResult paramReportResult, EntitlementGroups paramEntitlementGroups, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws RptException, EntitlementException
  {
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    Object[] arrayOfObject = { UserUtil.getFullNameWithLoginId(paramBankEmployee.getFirstName(), paramBankEmployee.getLastName(), paramBankEmployee.getUserName(), paramBankEmployee.getEmployeeId(), localLocale) };
    localReportHeading.setLabel(MessageFormat.format(ReportConsts.getText(10001, localLocale), arrayOfObject));
    localReportResult.setHeading(localReportHeading);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(5);
    localReportDataDimensions.setNumRows(1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportRow localReportRow = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportColumn localReportColumn1 = new ReportColumn(localLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(1900, localLocale));
    localReportColumn1.setLabels(localArrayList1);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn1);
    ReportDataItem localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(UserUtil.getFullName(paramBankEmployee.getFirstName(), paramBankEmployee.getLastName(), localLocale));
    ReportColumn localReportColumn2 = new ReportColumn(localLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(1901, localLocale));
    localReportColumn2.setLabels(localArrayList2);
    localReportColumn2.setDataType("java.lang.String");
    localReportResult.addColumn(localReportColumn2);
    localReportColumn2.setWidthAsPercent(20);
    localReportDataItem = localReportDataItems.add();
    for (int i = 0; i < paramEntitlementGroups.size(); i++)
    {
      localObject1 = (EntitlementGroup)paramEntitlementGroups.get(i);
      if (((EntitlementGroup)localObject1).getGroupId() == paramBankEmployee.getJobTypeId())
      {
        EntitlementGroupProperties localEntitlementGroupProperties = ((EntitlementGroup)localObject1).getProperties();
        localObject2 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementGroupProperties, "display name", localLocale);
        if (localObject2 == null) {
          localObject2 = ((EntitlementGroup)localObject1).getGroupName();
        }
        localReportDataItem.setData(localObject2);
        break;
      }
    }
    ReportColumn localReportColumn3 = new ReportColumn(localLocale);
    Object localObject1 = new ArrayList();
    ((ArrayList)localObject1).add(ReportConsts.getColumnName(1902, localLocale));
    localReportColumn3.setLabels((ArrayList)localObject1);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn3);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    for (int j = 0; j < paramBankEmployees.size(); j++)
    {
      localObject2 = (BankEmployee)paramBankEmployees.get(j);
      if (paramBankEmployee.getSupervisor().equals(((BankEmployee)localObject2).getId()))
      {
        localReportDataItem.setData(UserUtil.getFullNameWithLoginId(((BankEmployee)localObject2).getFirstName(), ((BankEmployee)localObject2).getLastName(), ((BankEmployee)localObject2).getUserName(), ((BankEmployee)localObject2).getEmployeeId(), localLocale));
        break;
      }
    }
    ReportColumn localReportColumn4 = new ReportColumn(localLocale);
    Object localObject2 = new ArrayList();
    ((ArrayList)localObject2).add(ReportConsts.getColumnName(1903, localLocale));
    localReportColumn4.setLabels((ArrayList)localObject2);
    localReportColumn4.setDataType("java.lang.String");
    localReportColumn4.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn4);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(paramBankEmployee.getUserName());
    ReportColumn localReportColumn5 = new ReportColumn(localLocale);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(1904, localLocale));
    localReportColumn5.setLabels(localArrayList3);
    localReportColumn5.setDataType("java.lang.String");
    localReportColumn5.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn5);
    localReportDataItem = localReportDataItems.add();
    if (paramBankEmployee.getStatusInt() == 0) {
      localReportDataItem.setData(ReportConsts.getText(10002, localLocale));
    } else if (paramBankEmployee.getStatusInt() == 1) {
      localReportDataItem.setData(ReportConsts.getText(10003, localLocale));
    } else {
      localReportDataItem.setData(ReportConsts.getText(10019, localLocale));
    }
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    localReportResult.addRow(localReportRow);
    paramInt1++;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHashMap.get("EntitlementTypesForReport");
    paramInt1 = a(paramReportCriteria, paramReportResult, paramBankEmployee, localEntitlementTypePropertyLists, paramInt1, paramInt2, paramHashMap);
    return paramInt1;
  }
  
  private int a(SecureUser paramSecureUser, User paramUser, Businesses paramBusinesses, ReportResult paramReportResult, int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap1, ReportCriteria paramReportCriteria, HashMap paramHashMap2)
    throws RptException, EntitlementException
  {
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    String str = paramReportCriteria.getSearchCriteria().getProperty("Business");
    boolean bool1 = User.CUSTOMER_TYPE_CONSUMER.equals(paramUser.getCustomerType());
    boolean bool2 = User.CUSTOMER_TYPE_EMPLOYEE.equals(paramUser.getCustomerType());
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    Object[] arrayOfObject = { UserUtil.getFullNameWithLoginId(paramUser.getFirstName(), paramUser.getLastName(), paramUser.getUserName(), paramUser.getCustId(), localLocale) };
    localReportHeading.setLabel(MessageFormat.format(ReportConsts.getText(10001, localLocale), arrayOfObject));
    localReportResult.setHeading(localReportHeading);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    if ((!bool1) && (!bool2)) {
      localReportDataDimensions.setNumColumns(6);
    } else {
      localReportDataDimensions.setNumColumns(5);
    }
    localReportDataDimensions.setNumRows(1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportRow localReportRow = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportColumn localReportColumn1 = new ReportColumn(localLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(1910, localLocale));
    localReportColumn1.setLabels(localArrayList1);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn1);
    ReportDataItem localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(UserUtil.getFullName(paramUser.getFirstName(), paramUser.getLastName(), localLocale));
    if (!bool2)
    {
      localReportColumn2 = new ReportColumn(localLocale);
      localArrayList2 = new ArrayList();
      localArrayList2.add(ReportConsts.getColumnName(1915, localLocale));
      localReportColumn2.setLabels(localArrayList2);
      localReportColumn2.setDataType("java.lang.String");
      localReportColumn2.setWidthAsPercent(20);
      localReportResult.addColumn(localReportColumn2);
      localReportDataItem = localReportDataItems.add();
      if (paramUser.getPrimarySecondary().equals(User.USER_TYPE_PRIMARY)) {
        localReportDataItem.setData(ReportConsts.getText(10079, localLocale));
      } else {
        localReportDataItem.setData(ReportConsts.getText(10080, localLocale));
      }
    }
    if (!bool1)
    {
      localReportColumn2 = new ReportColumn(localLocale);
      localArrayList2 = new ArrayList();
      localArrayList2.add(ReportConsts.getColumnName(1911, localLocale));
      localReportColumn2.setLabels(localArrayList2);
      localReportColumn2.setDataType("java.lang.String");
      localReportColumn2.setWidthAsPercent(20);
      localReportResult.addColumn(localReportColumn2);
      localReportDataItem = localReportDataItems.add();
      if ((paramUser instanceof BusinessEmployee))
      {
        localObject1 = paramBusinesses.getById(((BusinessEmployee)paramUser).getBusinessId());
        localObject2 = ((Business)localObject1).getBusinessName();
        if (localObject2 == null) {
          localReportDataItem.setData("");
        } else {
          localReportDataItem.setData(localObject2);
        }
      }
      else
      {
        localReportDataItem.setData("");
      }
    }
    ReportColumn localReportColumn2 = new ReportColumn(localLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(1912, localLocale));
    localReportColumn2.setLabels(localArrayList2);
    localReportColumn2.setDataType("java.lang.String");
    localReportColumn2.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn2);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(paramUser.getUserName());
    Object localObject1 = new ReportColumn(localLocale);
    Object localObject2 = new ArrayList();
    ((ArrayList)localObject2).add(ReportConsts.getColumnName(1913, localLocale));
    ((ReportColumn)localObject1).setLabels((ArrayList)localObject2);
    ((ReportColumn)localObject1).setDataType("java.lang.String");
    ((ReportColumn)localObject1).setWidthAsPercent(20);
    localReportResult.addColumn((ReportColumn)localObject1);
    localReportDataItem = localReportDataItems.add();
    if (paramUser.getAccountStatus().equals(User.STATUS_ENROLLED)) {
      localReportDataItem.setData(ReportConsts.getText(10004, localLocale));
    } else if (paramUser.getAccountStatus().equals(User.STATUS_ACTIVE)) {
      localReportDataItem.setData(ReportConsts.getText(10002, localLocale));
    } else if (paramUser.getAccountStatus().equals(User.STATUS_INACTIVE)) {
      localReportDataItem.setData(ReportConsts.getText(10003, localLocale));
    } else {
      localReportDataItem.setData(ReportConsts.getText(10019, localLocale));
    }
    ReportColumn localReportColumn3 = new ReportColumn(localLocale);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(1914, localLocale));
    localReportColumn3.setLabels(localArrayList3);
    localReportColumn3.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn3.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn3);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(paramUser.get("LAST_ACTIVE_DATE"));
    if (localReportDataItem.getData() == null)
    {
      localReportDataItem.setData(ReportConsts.getText(10013, localLocale));
      localReportColumn3.setDataType("java.lang.String");
    }
    localReportResult.addRow(localReportRow);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists;
    if ((paramUser instanceof BusinessEmployee)) {
      localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHashMap2.get("BusEmpEntTypesForReport");
    } else if (paramUser.getPrimarySecondaryValue() == 2) {
      localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHashMap2.get("SubConsumerEntTypesForReport");
    } else {
      localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHashMap2.get("ConsumerEntTypesForReport");
    }
    if ((paramUser instanceof BusinessEmployee))
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramUser;
      paramInt1 = a(paramReportResult, localBusinessEmployee, localEntitlementTypePropertyLists, paramInt1, paramInt2, paramHashMap2);
      paramInt1 = a(paramSecureUser, paramReportResult, paramUser, localEntitlementTypePropertyLists, paramInt1, paramInt2, paramInt3, paramHashMap1, paramHashMap2, paramReportCriteria);
    }
    else
    {
      paramInt1 = a(paramSecureUser, paramReportResult, paramUser, localEntitlementTypePropertyLists, paramInt1, paramInt2, paramInt3, paramHashMap1, paramHashMap2, paramReportCriteria);
    }
    return paramInt1;
  }
  
  private static int a(ReportResult paramReportResult, BusinessEmployee paramBusinessEmployee, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws RptException, EntitlementException
  {
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    EntitlementCachedAdapter localEntitlementCachedAdapter = (EntitlementCachedAdapter)paramHashMap.get("ENTITLEMENT_ADAPTER");
    Locale localLocale = paramReportResult.getLocale();
    EntitlementGroupMember localEntitlementGroupMember = paramBusinessEmployee.getEntitlementGroupMember();
    EntitlementGroups localEntitlementGroups = null;
    localEntitlementGroups = localEntitlementCachedAdapter.getGroupsAdministeredBy(localEntitlementGroupMember);
    if ((localEntitlementGroups != null) && (localEntitlementGroups.size() > 0))
    {
      ReportResult localReportResult = new ReportResult(localLocale);
      paramReportResult.addSubReport(localReportResult);
      ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
      localReportDataDimensions.setNumColumns(1);
      localReportDataDimensions.setNumRows(-1);
      localReportResult.setDataDimensions(localReportDataDimensions);
      ReportColumn localReportColumn = new ReportColumn(localLocale);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(1934, localLocale));
      localReportColumn.setLabels(localArrayList);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(100);
      localReportResult.addColumn(localReportColumn);
      localEntitlementGroups.setSortedBy("NAME");
      int i = 0;
      for (int j = 0; j < localEntitlementGroups.size(); j++)
      {
        ReportRow localReportRow = new ReportRow(localLocale);
        if (i % 2 == 1) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
        localReportRow.setDataItems(localReportDataItems);
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localEntitlementGroups.get(j);
        String str = localEntitlementGroup.getGroupName() + " (" + ReportConsts.getEntGroupType(localEntitlementGroup.getEntGroupType(), localLocale) + ")";
        ReportDataItem localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(str);
        localReportResult.addRow(localReportRow);
        paramInt1++;
        i++;
        if (paramInt1 == paramInt2)
        {
          localReportDataDimensions.setNumRows(i);
          return paramInt2;
        }
      }
    }
    return paramInt1;
  }
  
  private static int a(ReportCriteria paramReportCriteria, ReportResult paramReportResult, BankEmployee paramBankEmployee, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws RptException, EntitlementException
  {
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    Locale localLocale = paramReportResult.getLocale();
    String str1 = ReportConsts.getText(10007, localLocale);
    String str2 = ReportConsts.getText(10012, localLocale);
    String str3 = ReportConsts.getText(10010, localLocale);
    String str4 = ReportConsts.getText(10011, localLocale);
    String str5 = ReportConsts.getText(10056, localLocale);
    String str6 = ReportConsts.getText(10057, localLocale);
    String str7 = ReportConsts.getText(2308, localLocale);
    String str8 = ReportConsts.getText(2309, localLocale);
    EntitlementGroupMember localEntitlementGroupMember = paramBankEmployee.getEntitlementGroupMember();
    HashMap localHashMap1 = (HashMap)paramHashMap.get("OpToObjTypeMapForReport" + localEntitlementGroupMember.getId());
    HashMap localHashMap2 = (HashMap)paramHashMap.get("ObjTypeToIdsMapForReport" + localEntitlementGroupMember.getId());
    EntitlementCachedAdapter localEntitlementCachedAdapter = (EntitlementCachedAdapter)paramHashMap.get("ENTITLEMENT_ADAPTER");
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = localEntitlementCachedAdapter.getPositiveEntitlements(localEntitlementGroupMember, localHashMap1, localHashMap2);
    com.ffusion.csil.core.EntitlementsUtil.filterEntitlementsOnAccountGroup(localEntitlements, localEntitlementGroupMember);
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(5);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    if (localEntitlements.size() == 0)
    {
      localReportDataDimensions.setNumColumns(1);
      localReportDataDimensions.setNumRows(1);
      localReportColumn1 = new ReportColumn(localLocale);
      localArrayList1 = new ArrayList();
      localArrayList1.add("");
      localReportColumn1.setLabels(localArrayList1);
      localReportColumn1.setDataType("java.lang.String");
      localReportColumn1.setWidthAsPercent(100);
      localReportResult.addColumn(localReportColumn1);
      localObject1 = new ReportRow(localLocale);
      localObject2 = new ReportDataItems(localLocale);
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      localObject3 = ((ReportDataItems)localObject2).add();
      ((ReportDataItem)localObject3).setData(ReportConsts.getText(10006, localLocale));
      localReportResult.addRow((ReportRow)localObject1);
      paramInt1++;
      return paramInt1;
    }
    ReportColumn localReportColumn1 = new ReportColumn(localLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(1920, localLocale));
    localReportColumn1.setLabels(localArrayList1);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(65);
    localReportResult.addColumn(localReportColumn1);
    Object localObject1 = new ReportColumn(localLocale);
    Object localObject2 = new ArrayList();
    ((ArrayList)localObject2).add(ReportConsts.getColumnName(1921, localLocale));
    ((ReportColumn)localObject1).setLabels((ArrayList)localObject2);
    ((ReportColumn)localObject1).setDataType("java.lang.String");
    ((ReportColumn)localObject1).setWidthAsPercent(10);
    localReportResult.addColumn((ReportColumn)localObject1);
    Object localObject3 = new ReportColumn(localLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(1922, localLocale));
    ((ReportColumn)localObject3).setLabels(localArrayList2);
    ((ReportColumn)localObject3).setDataType("java.lang.String");
    ((ReportColumn)localObject3).setWidthAsPercent(10);
    localReportResult.addColumn((ReportColumn)localObject3);
    ReportColumn localReportColumn2 = new ReportColumn(localLocale);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(1926, localLocale));
    localReportColumn2.setLabels(localArrayList3);
    localReportColumn2.setDataType("java.lang.String");
    localReportColumn2.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn2);
    ReportColumn localReportColumn3 = new ReportColumn(localLocale);
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add(ReportConsts.getColumnName(1923, localLocale));
    localReportColumn3.setLabels(localArrayList4);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(15);
    localReportResult.addColumn(localReportColumn3);
    Object localObject4 = localEntitlements.toArray();
    String str9 = a(paramReportCriteria.getSearchCriteria(), "Activity", "AllActivities");
    if (str9.equals("AllActivities"))
    {
      localObject5 = null;
      if (paramBankEmployee.getOboEnabled().equals("1")) {
        localObject5 = new Entitlement(str5, null, null);
      } else if (paramBankEmployee.getOboEnabled().equals("2")) {
        localObject5 = new Entitlement(str6, null, null);
      }
      if (localObject5 != null)
      {
        Object[] arrayOfObject = new Object[localObject4.length + 1];
        System.arraycopy(localObject4, 0, arrayOfObject, 0, localObject4.length);
        arrayOfObject[localObject4.length] = localObject5;
        localObject4 = arrayOfObject;
      }
    }
    Object localObject5 = new ReportEntitlementComparator(paramEntitlementTypePropertyLists, localLocale);
    Arrays.sort((Object[])localObject4, (Comparator)localObject5);
    int i = 0;
    Object localObject6 = "-";
    Object localObject7 = "-";
    Limits localLimits1 = localEntitlementCachedAdapter.getCompressedLimits(localEntitlementGroupMember);
    int j = 0;
    for (int k = 0; k < localObject4.length; k++)
    {
      Entitlement localEntitlement1 = (Entitlement)localObject4[k];
      Entitlement localEntitlement2 = null;
      Entitlement localEntitlement3 = null;
      String str10 = localEntitlement1.getOperationName();
      if (str10 != null)
      {
        String str12 = str10;
        EntitlementTypePropertyList localEntitlementTypePropertyList = paramEntitlementTypePropertyLists.getByOperationName(str10);
        int m = 0;
        int n = 0;
        if (localEntitlementTypePropertyList != null)
        {
          str12 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList, "display name", localLocale);
          if (localEntitlementTypePropertyList.isPropertySet("credit", "yes"))
          {
            m = 1;
            localEntitlement2 = new Entitlement(str10 + " (Credit)", localEntitlement1.getObjectType(), localEntitlement1.getObjectId());
          }
          if (localEntitlementTypePropertyList.isPropertySet("debit", "yes"))
          {
            n = 1;
            localEntitlement3 = new Entitlement(str10 + " (Debit)", localEntitlement1.getObjectType(), localEntitlement1.getObjectId());
          }
          if (str12 == null) {
            str12 = str10;
          }
        }
        Limits localLimits2 = new Limits();
        Limits localLimits3 = new Limits();
        Limits localLimits4 = new Limits();
        Iterator localIterator = localLimits1.iterator();
        Object localObject8;
        Object localObject11;
        while (localIterator.hasNext())
        {
          localObject8 = (Limit)localIterator.next();
          int i1;
          int i2;
          int i3;
          if (a((Limit)localObject8, localEntitlement1))
          {
            if (localLimits2.size() == 0)
            {
              localLimits2.add(localObject8);
              continue;
            }
            i1 = 0;
            for (i2 = 0; i2 < localLimits2.size(); i2++)
            {
              localObject11 = (Limit)localLimits2.get(i2);
              i3 = a(((Limit)localObject8).getPeriod(), ((Limit)localObject11).getPeriod());
              if (i3 == -1)
              {
                localLimits2.add(i2, localObject8);
                i1 = 1;
                break;
              }
              if (i3 == 0)
              {
                if (((((Limit)localObject11).isAllowedApproval()) && (((Limit)localObject8).isAllowedApproval())) || ((!((Limit)localObject11).isAllowedApproval()) && (!((Limit)localObject8).isAllowedApproval())))
                {
                  if (((Limit)localObject8).getAmount().compareTo(((Limit)localObject11).getAmount()) == -1) {
                    localLimits2.set(i2, localObject8);
                  }
                  i1 = 1;
                  break;
                }
                if ((!((Limit)localObject11).isAllowedApproval()) && (((Limit)localObject8).isAllowedApproval()))
                {
                  localLimits2.add(i2, localObject8);
                  i1 = 1;
                  break;
                }
                if (i2 == localLimits2.size())
                {
                  localLimits2.add(localObject8);
                  i1 = 1;
                  break;
                }
              }
            }
            if (i1 == 0) {
              localLimits2.add(localObject8);
            }
          }
          if ((m != 0) && (a((Limit)localObject8, localEntitlement2)))
          {
            if (localLimits3.size() == 0)
            {
              localLimits3.add(localObject8);
              continue;
            }
            i1 = 0;
            for (i2 = 0; i2 < localLimits3.size(); i2++)
            {
              localObject11 = (Limit)localLimits3.get(i2);
              i3 = a(((Limit)localObject8).getPeriod(), ((Limit)localObject11).getPeriod());
              if (i3 == -1)
              {
                localLimits3.add(i2, localObject8);
                i1 = 1;
                break;
              }
              if (i3 == 0)
              {
                if (((((Limit)localObject11).isAllowedApproval()) && (((Limit)localObject8).isAllowedApproval())) || ((!((Limit)localObject11).isAllowedApproval()) && (!((Limit)localObject8).isAllowedApproval())))
                {
                  if (((Limit)localObject8).getAmount().compareTo(((Limit)localObject11).getAmount()) == -1) {
                    localLimits3.set(i2, localObject8);
                  }
                  i1 = 1;
                  break;
                }
                if ((!((Limit)localObject11).isAllowedApproval()) && (((Limit)localObject8).isAllowedApproval()))
                {
                  localLimits3.add(i2, localObject8);
                  i1 = 1;
                  break;
                }
                if (i2 == localLimits3.size())
                {
                  localLimits3.add(localObject8);
                  i1 = 1;
                  break;
                }
              }
            }
            if (i1 == 0) {
              localLimits3.add(localObject8);
            }
          }
          if ((n != 0) && (a((Limit)localObject8, localEntitlement3))) {
            if (localLimits4.size() == 0)
            {
              localLimits4.add(localObject8);
            }
            else
            {
              i1 = 0;
              for (i2 = 0; i2 < localLimits4.size(); i2++)
              {
                localObject11 = (Limit)localLimits4.get(i2);
                i3 = a(((Limit)localObject8).getPeriod(), ((Limit)localObject11).getPeriod());
                if (i3 == -1)
                {
                  localLimits4.add(i2, localObject8);
                  i1 = 1;
                  break;
                }
                if (i3 == 0)
                {
                  if (((((Limit)localObject11).isAllowedApproval()) && (((Limit)localObject8).isAllowedApproval())) || ((!((Limit)localObject11).isAllowedApproval()) && (!((Limit)localObject8).isAllowedApproval())))
                  {
                    if (((Limit)localObject8).getAmount().compareTo(((Limit)localObject11).getAmount()) == -1) {
                      localLimits4.set(i2, localObject8);
                    }
                    i1 = 1;
                    break;
                  }
                  if ((!((Limit)localObject11).isAllowedApproval()) && (((Limit)localObject8).isAllowedApproval()))
                  {
                    localLimits4.add(i2, localObject8);
                    i1 = 1;
                    break;
                  }
                  if (i2 == localLimits4.size())
                  {
                    localLimits4.add(localObject8);
                    i1 = 1;
                    break;
                  }
                }
              }
              if (i1 == 0) {
                localLimits4.add(localObject8);
              }
            }
          }
        }
        if (((String)localObject6).equals(str10)) {
          str12 = "";
        } else {
          j++;
        }
        localLimits2.addAll(localLimits3);
        localLimits2.addAll(localLimits4);
        Object localObject9;
        Object localObject10;
        if (localLimits2.size() == 0)
        {
          localObject8 = new ReportRow(localLocale);
          if (j % 2 == 0) {
            ((ReportRow)localObject8).set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject9 = new ReportDataItems(localLocale);
          ((ReportRow)localObject8).setDataItems((ReportDataItems)localObject9);
          localObject10 = ((ReportDataItems)localObject9).add();
          ((ReportDataItem)localObject10).setData((!((String)localObject6).equals(str10)) && (str10.equals("")) ? str1 : str12);
          localObject10 = ((ReportDataItems)localObject9).add();
          ((ReportDataItem)localObject10).setData("");
          localObject10 = ((ReportDataItems)localObject9).add();
          ((ReportDataItem)localObject10).setData(str2);
          localObject10 = ((ReportDataItems)localObject9).add();
          ((ReportDataItem)localObject10).setData("");
          localObject10 = ((ReportDataItems)localObject9).add();
          ((ReportDataItem)localObject10).setData("");
          localObject6 = str10;
          if (paramInt1 == paramInt2)
          {
            localReportDataDimensions.setNumRows(i);
            return paramInt2;
          }
          localReportResult.addRow((ReportRow)localObject8);
          paramInt1++;
          i++;
        }
        if (localLimits2.size() != 0)
        {
          localIterator = localLimits2.iterator();
          localObject7 = "-";
          while (localIterator.hasNext())
          {
            localObject8 = (Limit)localIterator.next();
            if (((String)localObject6).equals(str10)) {
              str12 = "";
            }
            localObject9 = new ReportRow(localLocale);
            if (j % 2 == 0) {
              ((ReportRow)localObject9).set("CELLBACKGROUND", "reportDataShaded");
            }
            localObject10 = new ReportDataItems(localLocale);
            ((ReportRow)localObject9).setDataItems((ReportDataItems)localObject10);
            localObject11 = ((ReportDataItems)localObject10).add();
            ((ReportDataItem)localObject11).setData((!((String)localObject6).equals(str10)) && (str10.equals("")) ? str1 : str12);
            localObject11 = ((ReportDataItems)localObject10).add();
            String str11 = a((Limit)localObject8, localLocale);
            ((ReportDataItem)localObject11).setData(((String)localObject7).equals(str11) ? "" : str11);
            localObject11 = ((ReportDataItems)localObject10).add();
            String str13 = ((Limit)localObject8).getData() + " " + ((Limit)localObject8).getCurrencyCode();
            if (localLimits3.contains(localObject8)) {
              str13 = str13 + " " + ReportConsts.getText(10110, localLocale);
            }
            if (localLimits4.contains(localObject8)) {
              str13 = str13 + " " + ReportConsts.getText(10111, localLocale);
            }
            ((ReportDataItem)localObject11).setData(str13);
            localObject11 = ((ReportDataItems)localObject10).add();
            if (((Limit)localObject8).isCrossCurrency()) {
              ((ReportDataItem)localObject11).setData(str7);
            } else {
              ((ReportDataItem)localObject11).setData(str8);
            }
            localObject11 = ((ReportDataItems)localObject10).add();
            ((ReportDataItem)localObject11).setData(((Limit)localObject8).isAllowedApproval() ? str3 : str4);
            if (paramInt1 == paramInt2)
            {
              localReportDataDimensions.setNumRows(i);
              return paramInt2;
            }
            localObject6 = str10;
            localObject7 = str11;
            localReportResult.addRow((ReportRow)localObject9);
            paramInt1++;
            i++;
          }
        }
      }
    }
    localReportDataDimensions.setNumRows(i);
    return paramInt1;
  }
  
  private static int a(SecureUser paramSecureUser, ReportResult paramReportResult, User paramUser, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap1, HashMap paramHashMap2, ReportCriteria paramReportCriteria)
    throws RptException, EntitlementException
  {
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    Locale localLocale = paramReportResult.getLocale();
    String str1 = ReportConsts.getText(10007, localLocale);
    String str2 = ReportConsts.getText(10008, localLocale);
    String str3 = ReportConsts.getText(10009, localLocale);
    String str4 = ReportConsts.getText(10012, localLocale);
    String str5 = ReportConsts.getText(10010, localLocale);
    String str6 = ReportConsts.getText(10011, localLocale);
    String str7 = ReportConsts.getText(10415, localLocale);
    String str8 = ReportConsts.getText(2308, localLocale);
    String str9 = ReportConsts.getText(2309, localLocale);
    String str10 = a(paramReportCriteria.getSearchCriteria(), "Activity", "AllActivities");
    EntitlementGroupMember localEntitlementGroupMember = paramUser.getEntitlementGroupMember();
    HashMap localHashMap1 = a(paramUser, paramEntitlementTypePropertyLists, str10);
    com.ffusion.beans.accounts.Accounts localAccounts = null;
    HashMap localHashMap2;
    try
    {
      if (paramUser.getCustomerTypeValue() == 1)
      {
        localAccounts = com.ffusion.csil.core.Accounts.getAccountsByBusinessEmployee(paramUser.getSecureUser(), new HashMap());
      }
      else
      {
        SecureUser localSecureUser = paramUser.getSecureUser();
        if (paramUser.getPrimarySecondaryValue() == 2)
        {
          localObject1 = UserAdmin.getPrimaryUser(paramSecureUser, paramUser, new HashMap());
          if (localObject1 != null) {
            localSecureUser.setPrimaryUserID(((User)localObject1).getId());
          }
        }
        else
        {
          localSecureUser.setPrimaryUserID(localSecureUser.getProfileID());
        }
        localAccounts = com.ffusion.csil.core.Accounts.getAccounts(localSecureUser, new HashMap());
      }
      localHashMap2 = a(paramUser, paramEntitlementTypePropertyLists, str10, localAccounts);
    }
    catch (CSILException localCSILException1)
    {
      throw new RptException(100, localCSILException1);
    }
    if (localAccounts != null) {
      localAccounts.setFilter("COREACCOUNT=2");
    }
    EntitlementCachedAdapter localEntitlementCachedAdapter = (EntitlementCachedAdapter)paramHashMap2.get("ENTITLEMENT_ADAPTER");
    Object localObject1 = localEntitlementCachedAdapter.getPositiveEntitlements(localEntitlementGroupMember, localHashMap1, localHashMap2);
    com.ffusion.csil.core.EntitlementsUtil.filterEntitlementsOnAccountGroup((com.ffusion.csil.beans.entitlements.Entitlements)localObject1, localEntitlementGroupMember);
    a(paramUser, localAccounts, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1);
    EntitlementTypePropertyList localEntitlementTypePropertyList1 = null;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    try
    {
      localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    catch (CSILException localCSILException2) {}
    for (int i = 0; i < paramEntitlementTypePropertyLists.size(); i++)
    {
      localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)paramEntitlementTypePropertyLists.get(i);
      localObject2 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList1, "display name", localLocale);
      if (localObject2 == null)
      {
        localEntitlementTypePropertyList1.setCurrentProperty("display name");
        localObject2 = localEntitlementTypePropertyList1.getValue();
      }
      localEntitlementTypePropertyList1.setCurrentProperty("display parent");
      localObject3 = localEntitlementTypePropertyList1.getValue();
      if ((localEntitlementTypePropertyList1.isPropertySet("category", "per ACH company")) || (localEntitlementTypePropertyList1.isPropertySet("category", "cross ACH company")))
      {
        localEntitlementTypePropertyList1.setCurrentProperty("module_name");
        localObject4 = localEntitlementTypePropertyList1.getValue();
        if ((((String)localObject3).equals("")) && (((String)localObject4).equals("ACH")))
        {
          localEntitlementTypePropertyList1.clearProperty("display name");
          localEntitlementTypePropertyList1.addProperty("display name", str7 + " - " + (String)localObject2);
        }
        else if (!((String)localObject3).equals(""))
        {
          localObject5 = localEntitlementTypePropertyLists.getByOperationName((String)localObject3);
          localObject6 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue((TypePropertyList)localObject5, "display name", localLocale);
          if (localObject6 == null)
          {
            ((EntitlementTypePropertyList)localObject5).setCurrentProperty("display name");
            localObject6 = ((EntitlementTypePropertyList)localObject5).getValue();
          }
          com.ffusion.csil.core.EntitlementsUtil.setPropertyValue(localEntitlementTypePropertyList1, "display name", (String)localObject6 + " - " + (String)localObject2, localLocale);
        }
      }
    }
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    Object localObject2 = new ReportDataDimensions(localLocale);
    ((ReportDataDimensions)localObject2).setNumColumns(7);
    ((ReportDataDimensions)localObject2).setNumRows(-1);
    localReportResult.setDataDimensions((ReportDataDimensions)localObject2);
    if ((localObject1 == null) || (((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).size() == 0))
    {
      ((ReportDataDimensions)localObject2).setNumColumns(1);
      ((ReportDataDimensions)localObject2).setNumRows(1);
      localObject3 = new ReportColumn(localLocale);
      localObject4 = new ArrayList();
      ((ArrayList)localObject4).add("");
      ((ReportColumn)localObject3).setLabels((ArrayList)localObject4);
      ((ReportColumn)localObject3).setDataType("java.lang.String");
      ((ReportColumn)localObject3).setWidthAsPercent(100);
      localReportResult.addColumn((ReportColumn)localObject3);
      localObject5 = new ReportRow(localLocale);
      localObject6 = new ReportDataItems(localLocale);
      ((ReportRow)localObject5).setDataItems((ReportDataItems)localObject6);
      localObject7 = ((ReportDataItems)localObject6).add();
      ((ReportDataItem)localObject7).setData(ReportConsts.getText(10006, localLocale));
      localReportResult.addRow((ReportRow)localObject5);
      paramInt1++;
      return paramInt1;
    }
    Object localObject3 = new ReportColumn(localLocale);
    Object localObject4 = new ArrayList();
    ((ArrayList)localObject4).add(ReportConsts.getColumnName(1920, localLocale));
    ((ReportColumn)localObject3).setLabels((ArrayList)localObject4);
    ((ReportColumn)localObject3).setDataType("java.lang.String");
    ((ReportColumn)localObject3).setWidthAsPercent(30);
    localReportResult.addColumn((ReportColumn)localObject3);
    Object localObject5 = new ReportColumn(localLocale);
    Object localObject6 = new ArrayList();
    ((ArrayList)localObject6).add(ReportConsts.getColumnName(1924, localLocale));
    ((ReportColumn)localObject5).setLabels((ArrayList)localObject6);
    ((ReportColumn)localObject5).setDataType("java.lang.String");
    ((ReportColumn)localObject5).setWidthAsPercent(15);
    localReportResult.addColumn((ReportColumn)localObject5);
    Object localObject7 = new ReportColumn(localLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(1925, localLocale));
    ((ReportColumn)localObject7).setLabels(localArrayList1);
    ((ReportColumn)localObject7).setDataType("java.lang.String");
    ((ReportColumn)localObject7).setWidthAsPercent(20);
    localReportResult.addColumn((ReportColumn)localObject7);
    ReportColumn localReportColumn1 = new ReportColumn(localLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(1921, localLocale));
    localReportColumn1.setLabels(localArrayList2);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn1);
    ReportColumn localReportColumn2 = new ReportColumn(localLocale);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(1922, localLocale));
    localReportColumn2.setLabels(localArrayList3);
    localReportColumn2.setDataType("java.lang.String");
    localReportColumn2.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn2);
    ReportColumn localReportColumn3 = new ReportColumn(localLocale);
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add(ReportConsts.getColumnName(1926, localLocale));
    localReportColumn3.setLabels(localArrayList4);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn3);
    ReportColumn localReportColumn4 = new ReportColumn(localLocale);
    ArrayList localArrayList5 = new ArrayList();
    localArrayList5.add(ReportConsts.getColumnName(1923, localLocale));
    localReportColumn4.setLabels(localArrayList5);
    localReportColumn4.setDataType("java.lang.String");
    localReportColumn4.setWidthAsPercent(15);
    localReportResult.addColumn(localReportColumn4);
    Object[] arrayOfObject = ((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).toArray();
    ReportEntitlementComparator localReportEntitlementComparator = new ReportEntitlementComparator(paramEntitlementTypePropertyLists, localLocale);
    Arrays.sort(arrayOfObject, localReportEntitlementComparator);
    int j = 0;
    Object localObject8 = "-";
    Object localObject9 = "-";
    Object localObject10 = "-";
    Object localObject11 = "-";
    Limits localLimits1 = localEntitlementCachedAdapter.getCompressedLimits(localEntitlementGroupMember);
    int k = 0;
    for (int m = 0; m < arrayOfObject.length; m++)
    {
      Entitlement localEntitlement1 = (Entitlement)arrayOfObject[m];
      Entitlement localEntitlement2 = null;
      Entitlement localEntitlement3 = null;
      String str11 = localEntitlement1.getOperationName();
      if (str11 != null)
      {
        try
        {
          if ((str11 != null) && (!a(str11, paramInt3, paramHashMap1))) {
            continue;
          }
        }
        catch (Exception localException) {}
        String str12 = localEntitlement1.getObjectId();
        String str13 = localEntitlement1.getObjectType();
        str12 = str12 == null ? "" : str12;
        str13 = str13 == null ? "" : str13;
        String str15 = str11;
        String str16 = str13;
        String str17 = str12;
        EntitlementTypePropertyList localEntitlementTypePropertyList2 = paramEntitlementTypePropertyLists.getByOperationName(str11);
        int n = 0;
        int i1 = 0;
        if (localEntitlementTypePropertyList2 != null)
        {
          str15 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList2, "display name", localLocale);
          if (localEntitlementTypePropertyList2.isPropertySet("credit", "yes"))
          {
            n = 1;
            localEntitlement2 = new Entitlement(str11 + " (Credit)", str13, str12);
          }
          if (localEntitlementTypePropertyList2.isPropertySet("debit", "yes"))
          {
            i1 = 1;
            localEntitlement3 = new Entitlement(str11 + " (Debit)", str13, str12);
          }
          if (str15 == null) {
            str15 = str11;
          }
        }
        ObjectTypePropertyList localObjectTypePropertyList = null;
        localObjectTypePropertyList = localEntitlementCachedAdapter.getObjectTypeWithProperties(str13);
        if (localObjectTypePropertyList != null) {
          str16 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localObjectTypePropertyList, "display name", localLocale);
        }
        HashMap localHashMap3 = (HashMap)localHashMap2.get(str13 + "DisplayValues");
        if (localHashMap3 != null) {
          str17 = (String)localHashMap3.get(str12);
        }
        Limits localLimits2 = new Limits();
        Limits localLimits3 = new Limits();
        Limits localLimits4 = new Limits();
        Iterator localIterator = localLimits1.iterator();
        Object localObject14;
        while (localIterator.hasNext())
        {
          Limit localLimit = (Limit)localIterator.next();
          int i3;
          int i4;
          int i5;
          if (a(localLimit, localEntitlement1))
          {
            if (localLimits2.size() == 0)
            {
              localLimits2.add(localLimit);
              continue;
            }
            i3 = 0;
            for (i4 = 0; i4 < localLimits2.size(); i4++)
            {
              localObject14 = (Limit)localLimits2.get(i4);
              i5 = a(localLimit.getPeriod(), ((Limit)localObject14).getPeriod());
              if (i5 == -1)
              {
                localLimits2.add(i4, localLimit);
                i3 = 1;
                break;
              }
              if (i5 == 0)
              {
                if (((((Limit)localObject14).isAllowedApproval()) && (localLimit.isAllowedApproval())) || ((!((Limit)localObject14).isAllowedApproval()) && (!localLimit.isAllowedApproval())))
                {
                  if (localLimit.getAmount().compareTo(((Limit)localObject14).getAmount()) == -1) {
                    localLimits2.set(i4, localLimit);
                  }
                  i3 = 1;
                  break;
                }
                if ((!((Limit)localObject14).isAllowedApproval()) && (localLimit.isAllowedApproval()))
                {
                  localLimits2.add(i4, localLimit);
                  i3 = 1;
                  break;
                }
                if (i4 == localLimits2.size())
                {
                  localLimits2.add(localLimit);
                  i3 = 1;
                  break;
                }
              }
            }
            if (i3 == 0) {
              localLimits2.add(localLimit);
            }
          }
          if ((n != 0) && (a(localLimit, localEntitlement2)))
          {
            if (localLimits3.size() == 0)
            {
              localLimits3.add(localLimit);
              continue;
            }
            i3 = 0;
            for (i4 = 0; i4 < localLimits3.size(); i4++)
            {
              localObject14 = (Limit)localLimits3.get(i4);
              i5 = a(localLimit.getPeriod(), ((Limit)localObject14).getPeriod());
              if (i5 == -1)
              {
                localLimits3.add(i4, localLimit);
                i3 = 1;
                break;
              }
              if (i5 == 0)
              {
                if (((((Limit)localObject14).isAllowedApproval()) && (localLimit.isAllowedApproval())) || ((!((Limit)localObject14).isAllowedApproval()) && (!localLimit.isAllowedApproval())))
                {
                  if (localLimit.getAmount().compareTo(((Limit)localObject14).getAmount()) == -1) {
                    localLimits3.set(i4, localLimit);
                  }
                  i3 = 1;
                  break;
                }
                if ((!((Limit)localObject14).isAllowedApproval()) && (localLimit.isAllowedApproval()))
                {
                  localLimits3.add(i4, localLimit);
                  i3 = 1;
                  break;
                }
                if (i4 == localLimits3.size())
                {
                  localLimits3.add(localLimit);
                  i3 = 1;
                  break;
                }
              }
            }
            if (i3 == 0) {
              localLimits3.add(localLimit);
            }
          }
          if ((i1 != 0) && (a(localLimit, localEntitlement3))) {
            if (localLimits4.size() == 0)
            {
              localLimits4.add(localLimit);
            }
            else
            {
              i3 = 0;
              for (i4 = 0; i4 < localLimits4.size(); i4++)
              {
                localObject14 = (Limit)localLimits4.get(i4);
                i5 = a(localLimit.getPeriod(), ((Limit)localObject14).getPeriod());
                if (i5 == -1)
                {
                  localLimits4.add(i4, localLimit);
                  i3 = 1;
                  break;
                }
                if (i5 == 0)
                {
                  if (((((Limit)localObject14).isAllowedApproval()) && (localLimit.isAllowedApproval())) || ((!((Limit)localObject14).isAllowedApproval()) && (!localLimit.isAllowedApproval())))
                  {
                    if (localLimit.getAmount().compareTo(((Limit)localObject14).getAmount()) == -1) {
                      localLimits4.set(i4, localLimit);
                    }
                    i3 = 1;
                    break;
                  }
                  if ((!((Limit)localObject14).isAllowedApproval()) && (localLimit.isAllowedApproval()))
                  {
                    localLimits4.add(i4, localLimit);
                    i3 = 1;
                    break;
                  }
                  if (i4 == localLimits4.size())
                  {
                    localLimits4.add(localLimit);
                    i3 = 1;
                    break;
                  }
                }
              }
              if (i3 == 0) {
                localLimits4.add(localLimit);
              }
            }
          }
        }
        int i2 = 0;
        if (((String)localObject8).equals(str11))
        {
          str15 = "";
        }
        else
        {
          localObject9 = "-";
          localObject10 = "-";
          k++;
          i2 = 1;
        }
        if (((String)localObject9).equals(str13))
        {
          str16 = "";
        }
        else
        {
          localObject10 = "-";
          if (i2 == 0)
          {
            k++;
            i2 = 1;
          }
        }
        if (((String)localObject10).equals(str12))
        {
          str17 = "";
        }
        else if (i2 == 0)
        {
          k++;
          i2 = 1;
        }
        localLimits2.addAll(localLimits3);
        localLimits2.addAll(localLimits4);
        Object localObject12;
        Object localObject13;
        if (localLimits2.size() == 0)
        {
          localObject12 = new ReportRow(localLocale);
          if (k % 2 == 0) {
            ((ReportRow)localObject12).set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject13 = new ReportDataItems(localLocale);
          ((ReportRow)localObject12).setDataItems((ReportDataItems)localObject13);
          localObject14 = ((ReportDataItems)localObject13).add();
          ((ReportDataItem)localObject14).setData((!((String)localObject8).equals(str11)) && (str11.equals("")) ? str1 : str15);
          localObject14 = ((ReportDataItems)localObject13).add();
          ((ReportDataItem)localObject14).setData((!((String)localObject9).equals(str13)) && (str13.equals("")) ? str2 : str16);
          localObject14 = ((ReportDataItems)localObject13).add();
          ((ReportDataItem)localObject14).setData((!((String)localObject10).equals(str12)) && (str12.equals("")) ? str3 : str17);
          localObject14 = ((ReportDataItems)localObject13).add();
          ((ReportDataItem)localObject14).setData("");
          localObject14 = ((ReportDataItems)localObject13).add();
          ((ReportDataItem)localObject14).setData(str4);
          localObject14 = ((ReportDataItems)localObject13).add();
          ((ReportDataItem)localObject14).setData("");
          localObject14 = ((ReportDataItems)localObject13).add();
          ((ReportDataItem)localObject14).setData("");
          localObject8 = str11;
          localObject9 = str13;
          localObject10 = str12;
          if (paramInt1 == paramInt2)
          {
            ((ReportDataDimensions)localObject2).setNumRows(j);
            return paramInt2;
          }
          localReportResult.addRow((ReportRow)localObject12);
          paramInt1++;
          j++;
        }
        if (localLimits2.size() != 0)
        {
          localIterator = localLimits2.iterator();
          localObject11 = "-";
          while (localIterator.hasNext())
          {
            localObject12 = (Limit)localIterator.next();
            if (((String)localObject8).equals(str11))
            {
              str15 = "";
            }
            else
            {
              localObject9 = "-";
              localObject10 = "-";
            }
            if (((String)localObject9).equals(str13)) {
              str16 = "";
            } else {
              localObject10 = "-";
            }
            if (((String)localObject10).equals(str12)) {
              str17 = "";
            }
            localObject13 = new ReportRow(localLocale);
            if (k % 2 == 0) {
              ((ReportRow)localObject13).set("CELLBACKGROUND", "reportDataShaded");
            }
            localObject14 = new ReportDataItems(localLocale);
            ((ReportRow)localObject13).setDataItems((ReportDataItems)localObject14);
            ReportDataItem localReportDataItem = ((ReportDataItems)localObject14).add();
            localReportDataItem.setData((!((String)localObject8).equals(str11)) && (str11.equals("")) ? str1 : str15);
            localReportDataItem = ((ReportDataItems)localObject14).add();
            localReportDataItem.setData((!((String)localObject9).equals(str13)) && (str13.equals("")) ? str2 : str16);
            localReportDataItem = ((ReportDataItems)localObject14).add();
            localReportDataItem.setData((!((String)localObject10).equals(str12)) && (str12.equals("")) ? str3 : str17);
            localReportDataItem = ((ReportDataItems)localObject14).add();
            String str14 = a((Limit)localObject12, localLocale);
            localReportDataItem.setData(((String)localObject11).equals(str14) ? "" : str14);
            localReportDataItem = ((ReportDataItems)localObject14).add();
            String str18 = ((Limit)localObject12).getData() + " " + ((Limit)localObject12).getCurrencyCode();
            if (localLimits3.contains(localObject12)) {
              str18 = str18 + " " + ReportConsts.getText(10110, localLocale);
            }
            if (localLimits4.contains(localObject12)) {
              str18 = str18 + " " + ReportConsts.getText(10111, localLocale);
            }
            localReportDataItem.setData(str18);
            localReportDataItem = ((ReportDataItems)localObject14).add();
            if (((Limit)localObject12).isCrossCurrency()) {
              localReportDataItem.setData(str8);
            } else {
              localReportDataItem.setData(str9);
            }
            localReportDataItem = ((ReportDataItems)localObject14).add();
            localReportDataItem.setData(((Limit)localObject12).isAllowedApproval() ? str5 : str6);
            if (paramInt1 == paramInt2)
            {
              ((ReportDataDimensions)localObject2).setNumRows(j);
              return paramInt2;
            }
            localObject8 = str11;
            localObject9 = str13;
            localObject10 = str12;
            localObject11 = str14;
            localReportResult.addRow((ReportRow)localObject13);
            paramInt1++;
            j++;
          }
        }
      }
    }
    ((ReportDataDimensions)localObject2).setNumRows(j);
    return paramInt1;
  }
  
  private static String a(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || (str.equals(""))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  private static String a(ReportSortCriteria paramReportSortCriteria, int paramInt, String paramString)
  {
    for (int i = 0; i < paramReportSortCriteria.size(); i++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(i);
      if (localReportSortCriterion.getOrdinal() == paramInt)
      {
        String str = localReportSortCriterion.getName();
        if ((str == null) || (str.equals(""))) {
          return paramString;
        }
        return localReportSortCriterion.getName();
      }
    }
    return paramString;
  }
  
  private static String a(Limit paramLimit, Locale paramLocale)
  {
    int i = paramLimit == null ? 0 : paramLimit.getPeriod();
    switch (i)
    {
    case 0: 
      return ReportConsts.getText(10100, paramLocale);
    case 1: 
      return ReportConsts.getText(10101, paramLocale);
    case 2: 
      return ReportConsts.getText(10102, paramLocale);
    case 3: 
      return ReportConsts.getText(10103, paramLocale);
    case 4: 
      return ReportConsts.getText(10104, paramLocale);
    }
    return ReportConsts.getText(10100, paramLocale);
  }
  
  private static boolean a(Limit paramLimit, Entitlement paramEntitlement)
    throws EntitlementException
  {
    boolean bool = true;
    if ((paramLimit.getLimitName() != null) && (!paramLimit.getLimitName().equalsIgnoreCase(paramEntitlement.getOperationName()))) {
      return false;
    }
    if ((paramLimit.getObjectType() != null) && (!paramLimit.getObjectType().equalsIgnoreCase(paramEntitlement.getObjectType()))) {
      return false;
    }
    if ((paramLimit.getObjectId() != null) && (!paramLimit.getObjectId().equalsIgnoreCase(paramEntitlement.getObjectId()))) {
      return false;
    }
    return bool;
  }
  
  private static void a(User paramUser, com.ffusion.beans.accounts.Accounts paramAccounts, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
  {
    if ((paramUser.getCustomerTypeValue() == 2) && (paramEntitlements != null))
    {
      if (paramAccounts == null) {
        paramAccounts = new com.ffusion.beans.accounts.Accounts();
      }
      Entitlement localEntitlement = null;
      Account localAccount = null;
      int i = 0;
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      Iterator localIterator1 = paramEntitlements.iterator();
      Object localObject;
      while (localIterator1.hasNext())
      {
        localEntitlement = (Entitlement)localIterator1.next();
        localObject = localEntitlement.getOperationName();
        String str = localEntitlement.getObjectId();
        if (("Account".equals(localEntitlement.getObjectType())) && (str != null))
        {
          m = 0;
          n = 0;
          if ("External Transfers To".equals(localObject))
          {
            m = 1;
            j = 1;
          }
          else if ("External Transfers From".equals(localObject))
          {
            n = 1;
            k = 1;
          }
          if ((m != 0) || (n != 0))
          {
            i = 0;
            if (paramAccounts.size() != 0)
            {
              Iterator localIterator2 = paramAccounts.iterator();
              while ((localIterator2.hasNext()) && (i == 0))
              {
                localAccount = (Account)localIterator2.next();
                if (str.equals((String)localAccount.get("ENTITLEMENT_OBJECT_ID"))) {
                  i = 1;
                }
              }
            }
            if (i == 0) {
              localIterator1.remove();
            } else if (m != 0) {
              i1++;
            } else {
              i2++;
            }
          }
        }
      }
      if ((j != 0) && (i1 == 0))
      {
        localObject = new Entitlement("External Transfers To", "Account", null);
        paramEntitlements.add(localObject);
      }
      if ((k != 0) && (i2 == 0))
      {
        localObject = new Entitlement("External Transfers From", "Account", null);
        paramEntitlements.add(localObject);
      }
    }
  }
  
  private static int a(int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2) {
      return 0;
    }
    switch (paramInt1)
    {
    case 1: 
      return -1;
    case 2: 
      if (paramInt2 == 1) {
        return 1;
      }
      return -1;
    case 3: 
      if (paramInt2 == 4) {
        return -1;
      }
      return 1;
    }
    return 1;
  }
  
  private static void a()
  {
    String[] arrayOfString1 = { "Approvals Admin" };
    MultiEntitlement localMultiEntitlement1 = new MultiEntitlement();
    localMultiEntitlement1.setOperations(arrayOfString1);
    String[] arrayOfString2 = { "Approvals Admin", "Wires" };
    MultiEntitlement localMultiEntitlement2 = new MultiEntitlement();
    localMultiEntitlement2.setOperations(arrayOfString2);
    String[] arrayOfString3 = { "Cash Con - Disbursement Request" };
    MultiEntitlement localMultiEntitlement3 = new MultiEntitlement();
    localMultiEntitlement3.setOperations(arrayOfString3);
    String[] arrayOfString4 = { "Cash Con - Deposit Entry" };
    MultiEntitlement localMultiEntitlement4 = new MultiEntitlement();
    localMultiEntitlement4.setOperations(arrayOfString4);
    String[] arrayOfString5 = { "ACHBatch" };
    MultiEntitlement localMultiEntitlement5 = new MultiEntitlement();
    localMultiEntitlement5.setOperations(arrayOfString5);
    jdField_new = new HashMap();
    jdField_new.put("Manage ACH Participants", localMultiEntitlement5);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("ACHBatch", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("ARC", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CCD", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CCD + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CCD + DED", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CCD + TXP", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CIE", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CIE + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CTX", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CTX + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("XCK", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("WEB", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("WEB + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("PPD", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("PPD + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("POP", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("RCK", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("TEL", "ACH Payment Approval"), localMultiEntitlement1);
    jdField_new.put("Wire Domestic Templated Approval", localMultiEntitlement2);
    jdField_new.put("Wire Domestic Freeform Approval", localMultiEntitlement2);
    jdField_new.put("Wire International Templated Approval", localMultiEntitlement2);
    jdField_new.put("Wire International Freeform Approval", localMultiEntitlement2);
    jdField_new.put("Wire Host Approval", localMultiEntitlement2);
    jdField_new.put("Wire Drawdown Templated Approval", localMultiEntitlement2);
    jdField_new.put("Wire Drawdown Freeform Approval", localMultiEntitlement2);
    jdField_new.put("Wire FED Templated Approval", localMultiEntitlement2);
    jdField_new.put("Wire FED Freeform Approval", localMultiEntitlement2);
    jdField_new.put("Wire Book Templated Approval", localMultiEntitlement2);
    jdField_new.put("Wire Book Freeform Approval", localMultiEntitlement2);
    jdField_new.put("Wire Approval", localMultiEntitlement2);
    jdField_new.put("Wire Templates Approval", localMultiEntitlement1);
    jdField_new.put("Cash Con - Disbursement Request View Other", localMultiEntitlement3);
    jdField_new.put("Cash Con - Disbursement Request Delete Other", localMultiEntitlement3);
    jdField_new.put("Cash Con - Disbursement Request Edit Other", localMultiEntitlement3);
    jdField_new.put("Cash Con - Deposit Entry View Other", localMultiEntitlement4);
    jdField_new.put("Cash Con - Deposit Entry Delete Other", localMultiEntitlement4);
    jdField_new.put("Cash Con - Deposit Entry Edit Other", localMultiEntitlement4);
  }
  
  private int a(int paramInt)
    throws Exception
  {
    for (EntitlementGroup localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(paramInt); !localEntitlementGroup.getEntGroupType().equals("ServicesPackage"); localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(localEntitlementGroup.getParentId())) {}
    return localEntitlementGroup.getGroupId();
  }
  
  private static boolean a(String paramString, int paramInt, HashMap paramHashMap)
    throws Exception
  {
    boolean bool;
    if (paramHashMap.containsKey(paramString))
    {
      bool = Boolean.TRUE.equals(paramHashMap.get(paramString));
    }
    else
    {
      Object localObject1;
      if (jdField_new.containsKey(paramString))
      {
        localObject1 = new com.ffusion.csil.beans.entitlements.Entitlements();
        Entitlement localEntitlement = new Entitlement(paramString, null, null);
        a(localEntitlement, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1);
        ArrayList localArrayList = new ArrayList();
        Object localObject2;
        for (int i = 0; i < ((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).size(); i++)
        {
          localObject2 = (Entitlement)((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).get(i);
          localArrayList.add(((Entitlement)localObject2).getOperationName());
        }
        MultiEntitlement localMultiEntitlement = (MultiEntitlement)jdField_new.get(paramString);
        if (((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).size() > 0)
        {
          localObject2 = localMultiEntitlement.getOperations();
          for (int j = 0; j < localObject2.length; j++) {
            localArrayList.add(localObject2[j]);
          }
          localMultiEntitlement.setOperations((String[])localArrayList.toArray(new String[localArrayList.size()]));
        }
        bool = com.ffusion.csil.core.Entitlements.checkEntitlement(paramInt, localMultiEntitlement) == null;
      }
      else if (paramString.equals("Cash Con - Reporting"))
      {
        bool = (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, a) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_byte) == null);
      }
      else if (paramString.equals(jdField_do))
      {
        bool = (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_char) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_for) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_if) == null);
      }
      else if (paramString.equals(jdField_int))
      {
        bool = (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_case) == null) && ((com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_char) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_for) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_if) == null));
      }
      else
      {
        localObject1 = new Entitlement(paramString, null, null);
        bool = com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, (Entitlement)localObject1) == null;
      }
      if (bool) {
        paramHashMap.put(paramString, Boolean.TRUE);
      } else {
        paramHashMap.put(paramString, Boolean.FALSE);
      }
    }
    return bool;
  }
  
  private static void a(Entitlement paramEntitlement, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    if (paramEntitlements.indexOf(paramEntitlement) == -1)
    {
      paramEntitlements.add(paramEntitlement);
      EntitlementTypePropertyList localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(paramEntitlement.getOperationName());
      if (localEntitlementTypePropertyList == null) {
        return;
      }
      int i = localEntitlementTypePropertyList.numPropertyValues("control parent");
      for (int j = 0; j < i; j++)
      {
        String str = localEntitlementTypePropertyList.getPropertyValue("control parent", j);
        Entitlement localEntitlement = new Entitlement();
        localEntitlement.setOperationName(str);
        a(localEntitlement, paramEntitlements);
      }
    }
  }
  
  private static HashMap a(User paramUser, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, String paramString, com.ffusion.beans.accounts.Accounts paramAccounts)
    throws CSILException
  {
    HashMap localHashMap1 = new HashMap();
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap2 = new HashMap();
    if (paramAccounts != null)
    {
      localObject1 = paramAccounts.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Account)((Iterator)localObject1).next();
        localObject3 = com.ffusion.csil.core.EntitlementsUtil.getEntitlementObjectId((Account)localObject2);
        localArrayList.add(localObject3);
        ((Account)localObject2).put("ENTITLEMENT_OBJECT_ID", localObject3);
        localHashMap2.put(localObject3, ((Account)localObject2).getRoutingNum() + ":" + ((Account)localObject2).getDisplayText());
      }
    }
    Object localObject1 = new ArrayList();
    Object localObject2 = new HashMap();
    Object localObject3 = null;
    if ((paramUser instanceof BusinessEmployee))
    {
      localObject3 = AccountGroup.getAccountGroups(paramUser.getSecureUser(), ((BusinessEmployee)paramUser).getBusinessId(), new HashMap());
      if (localObject3 != null)
      {
        localObject4 = ((BusinessAccountGroups)localObject3).iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject5 = (BusinessAccountGroup)((Iterator)localObject4).next();
          ((ArrayList)localObject1).add("" + ((BusinessAccountGroup)localObject5).getId());
          ((HashMap)localObject2).put("" + ((BusinessAccountGroup)localObject5).getId(), ((BusinessAccountGroup)localObject5).getName());
        }
      }
    }
    Object localObject4 = new HashMap();
    Object localObject5 = new ArrayList();
    if ((paramUser instanceof BusinessEmployee))
    {
      localObject6 = a((BusinessEmployee)paramUser);
      if (localObject6 != null)
      {
        localObject7 = ((LocationSearchResults)localObject6).iterator();
        while (((Iterator)localObject7).hasNext())
        {
          localObject8 = (LocationSearchResult)((Iterator)localObject7).next();
          ((ArrayList)localObject5).add(com.ffusion.csil.core.EntitlementsUtil.getEntitlementObjectId((LocationSearchResult)localObject8));
          ((HashMap)localObject4).put(com.ffusion.csil.core.EntitlementsUtil.getEntitlementObjectId((LocationSearchResult)localObject8), ((LocationSearchResult)localObject8).getLocationName());
        }
      }
    }
    Object localObject6 = new ArrayList();
    Object localObject7 = new HashMap();
    Object localObject10;
    if ((paramUser instanceof BusinessEmployee))
    {
      localObject8 = Wire.getAllWireTemplates(paramUser.getSecureUser(), ((BusinessEmployee)paramUser).getBusinessId(), new HashMap());
      if (localObject8 != null)
      {
        localObject9 = ((WireTransfers)localObject8).iterator();
        while (((Iterator)localObject9).hasNext())
        {
          localObject10 = (WireTransfer)((Iterator)localObject9).next();
          ((ArrayList)localObject6).add("" + ((WireTransfer)localObject10).getID());
          ((HashMap)localObject7).put("" + ((WireTransfer)localObject10).getID(), ((WireTransfer)localObject10).getNickName());
        }
      }
    }
    Object localObject8 = new ArrayList();
    Object localObject9 = new HashMap();
    Object localObject11;
    if ((paramUser instanceof BusinessEmployee)) {
      try
      {
        localObject10 = (BusinessEmployee)paramUser;
        ACHCompanies localACHCompanies = ACH.getACHCompanies(((BusinessEmployee)localObject10).getSecureUser(), String.valueOf(((BusinessEmployee)localObject10).getBusinessId()), ((BusinessEmployee)localObject10).getSecureUser().getBankID(), false, new HashMap());
        localObject11 = localACHCompanies.iterator();
        while (((Iterator)localObject11).hasNext())
        {
          ACHCompany localACHCompany = (ACHCompany)((Iterator)localObject11).next();
          ((ArrayList)localObject8).add(com.ffusion.csil.core.EntitlementsUtil.getEntitlementObjectId(localACHCompany));
          ((HashMap)localObject9).put("" + localACHCompany.getCompanyID(), localACHCompany.getCompanyID() + "/ " + localACHCompany.getCompanyName());
        }
      }
      catch (CSILException localCSILException)
      {
        if (localCSILException.getCode() != 20001) {
          throw new CSILException(localCSILException.getCode(), localCSILException);
        }
      }
    }
    localHashMap1.put("AccountDisplayValues", localHashMap2);
    localHashMap1.put("LocationDisplayValues", localObject4);
    localHashMap1.put("ACHCompanyDisplayValues", localObject9);
    localHashMap1.put("AccountGroupDisplayValues", localObject2);
    localHashMap1.put("Wire TemplateDisplayValues", localObject7);
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      localObject11 = (EntitlementTypePropertyList)localIterator.next();
      if (((EntitlementTypePropertyList)localObject11).isPropertySet("category", "per account")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject11).getOperationName().concat("Account"), localArrayList);
      }
      if (((EntitlementTypePropertyList)localObject11).isPropertySet("category", "per location")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject11).getOperationName().concat("Location"), localObject5);
      }
      if (((EntitlementTypePropertyList)localObject11).isPropertySet("category", "per account")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject11).getOperationName().concat("AccountGroup"), localObject1);
      }
      if (((EntitlementTypePropertyList)localObject11).isPropertySet("category", "per wire template")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject11).getOperationName().concat("Wire Template"), localObject6);
      }
      if (((EntitlementTypePropertyList)localObject11).isPropertySet("category", "per ACH company")) {
        localHashMap1.put(((EntitlementTypePropertyList)localObject11).getOperationName().concat("ACHCompany"), localObject8);
      }
    }
    if (paramString.equals("AllActivities"))
    {
      paramAccounts = null;
      if (paramAccounts != null) {
        localHashMap1.put("Account", localArrayList);
      }
    }
    if ((paramUser instanceof BusinessEmployee)) {
      localHashMap1.put("ACHBatch".concat("ACHCompany"), localObject8);
    }
    return localHashMap1;
  }
  
  private static LocationSearchResults a(BusinessEmployee paramBusinessEmployee)
    throws CSILException
  {
    EntitlementGroup localEntitlementGroup1 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(paramBusinessEmployee.getEntitlementGroupId());
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
      localObject2 = com.ffusion.csil.core.Entitlements.getChildrenEntitlementGroups(localEntitlementGroup1.getGroupId());
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
      localObject1 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(localEntitlementGroup1.getParentId());
      localObject2 = new LocationSearchCriteria();
      ((LocationSearchCriteria)localObject2).setDivisionID(((EntitlementGroup)localObject1).getGroupId());
      localLocationSearchResults1 = CashCon.getLocations(paramBusinessEmployee.getSecureUser(), (LocationSearchCriteria)localObject2, new HashMap());
    }
    return localLocationSearchResults1;
  }
  
  private static HashMap a(User paramUser, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, String paramString)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (EntitlementTypePropertyList)localIterator.next();
      ArrayList localArrayList = new ArrayList();
      if (((EntitlementTypePropertyList)localObject).isPropertySet("category", "per account"))
      {
        localArrayList.add("Account");
        if ((paramUser instanceof BusinessEmployee)) {
          localArrayList.add("AccountGroup");
        }
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
    if (paramString.equals("AllActivities"))
    {
      localObject = new ArrayList();
      ((ArrayList)localObject).add("Account");
      localHashMap.put("", localObject);
    }
    return localHashMap;
  }
  
  static
  {
    a();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.BCReportAdapter
 * JD-Core Version:    0.7.0.1
 */