package com.ffusion.services.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.admin.AdminOBORpt;
import com.ffusion.beans.admin.AdminRptConsts;
import com.ffusion.beans.admin.AdminSysActivityRpt;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Reporting;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.efs.adapters.entitlementsReporting.EntitlementReportingAdapter;
import com.ffusion.efs.adapters.profile.BankEmployeeAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.ReportAuditAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.Admin;
import com.ffusion.services.UserAdmin6;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Properties;

public class AdminService
  implements Admin, AdminConsts, AdminRptConsts
{
  public void initialize()
    throws AdminException
  {}
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "AdminService.getReportData";
    Locale localLocale = Locale.getDefault();
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str2 = localProperties.getProperty("Group");
    String str3 = localProperties.getProperty("User");
    String str4 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    try
    {
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      paramHashMap.put("StartDates", localHashMap1);
      paramHashMap.put("EndDates", localHashMap2);
    }
    catch (CSILException localCSILException1)
    {
      localObject1 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log((String)localObject1);
      if (localCSILException1.getCode() == -1009) {
        throw new AdminException(localCSILException1.getServiceError());
      }
      throw new AdminException(localCSILException1.getCode());
    }
    BusinessEmployees localBusinessEmployees1 = null;
    Object localObject1 = (EntitlementReportingAdapter)paramHashMap.get("ENTITLEMENT_REP_ADAPTER");
    Object localObject3;
    Object localObject5;
    Object localObject14;
    Object localObject15;
    Object localObject16;
    label704:
    Object localObject8;
    if ((str4 == null) || (!str4.equals("Session Receipt Report")))
    {
      if ((str2 == null) || (str2.equals(""))) {
        str2 = "AllGroups";
      }
      if ((str3 == null) || (str3.equals(""))) {
        str3 = "AllUsers";
      }
      str2 = str2.trim();
      str3 = str3.trim();
      localObject2 = null;
      localObject3 = new EntitlementGroupMembers();
      localObject5 = (EntitlementCachedAdapter)paramHashMap.get("ENTITLEMENT_ADAPTER");
      Object localObject9;
      Object localObject7;
      Object localObject11;
      if (str3.equals("AllUsers"))
      {
        if (str2.equals("AllGroups"))
        {
          try
          {
            localObject2 = ((EntitlementCachedAdapter)localObject5).getGroupsAdministeredBy(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser));
          }
          catch (EntitlementException localEntitlementException2)
          {
            throw new AdminException(str1, 16, localEntitlementException2);
          }
        }
        else
        {
          int i = -1;
          try
          {
            i = Integer.parseInt(str2);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            throw new AdminException(str1, 10, localNumberFormatException1);
          }
          localObject9 = null;
          try
          {
            localObject9 = Entitlements.getEntitlementGroup(i);
            if (localObject9 == null) {
              throw new AdminException(str1, 16, "No entitlement group for group ID = " + i);
            }
            localObject2 = new EntitlementGroups();
            ((EntitlementGroups)localObject2).add(localObject9);
          }
          catch (CSILException localCSILException4)
          {
            throw new AdminException(str1, 16, localCSILException4);
          }
        }
        localObject7 = new ArrayList();
        localObject9 = new EntitlementGroupMembers();
        localObject11 = null;
        try
        {
          EntitlementGroupMembers localEntitlementGroupMembers = null;
          int m = -1;
          for (int n = 0; n < ((EntitlementGroups)localObject2).size(); n++)
          {
            m = ((EntitlementGroup)((EntitlementGroups)localObject2).get(n)).getGroupId();
            localEntitlementGroupMembers = ((EntitlementCachedAdapter)localObject5).getMembers(m);
            for (int i1 = 0; i1 < localEntitlementGroupMembers.size(); i1++)
            {
              localObject11 = (EntitlementGroupMember)localEntitlementGroupMembers.get(i1);
              ((ArrayList)localObject7).add(((EntitlementGroupMember)localObject11).getId());
              ((EntitlementGroupMembers)localObject9).add(localObject11);
            }
          }
        }
        catch (EntitlementException localEntitlementException3)
        {
          throw new AdminException(str1, 16, localEntitlementException3);
        }
        if (str4.equals("User Entitlement Report")) {
          try
          {
            UserAdmin6 localUserAdmin6 = (UserAdmin6)paramHashMap.get("USER_ADMIN_SERVICE");
            localBusinessEmployees1 = localUserAdmin6.getBusinessEmployeesByIds(paramSecureUser, (ArrayList)localObject7, null);
            localObject14 = null;
            localObject15 = null;
            localObject16 = null;
            localObject14 = localBusinessEmployees1.listIterator();
            for (;;)
            {
              if (!((ListIterator)localObject14).hasNext()) {
                break label704;
              }
              localObject16 = (BusinessEmployee)((ListIterator)localObject14).next();
              localObject15 = ((EntitlementGroupMembers)localObject9).listIterator();
              if (((ListIterator)localObject15).hasNext())
              {
                localObject11 = (EntitlementGroupMember)((ListIterator)localObject15).next();
                ((EntitlementGroupMembers)localObject3).add(localObject11);
                if (!((EntitlementGroupMember)localObject11).getId().equals(((BusinessEmployee)localObject16).getId())) {
                  break;
                }
                ((BusinessEmployee)localObject16).setEntitlementGroupId(((EntitlementGroupMember)localObject11).getEntitlementGroupId());
                ((ListIterator)localObject15).remove();
              }
            }
            paramHashMap.put("BusinessEmployeesForReport", localBusinessEmployees1);
            paramHashMap.put("EntGroupMembersForReport", localObject3);
          }
          catch (ProfileException localProfileException3)
          {
            throw new AdminException(str1, 19, localProfileException3);
          }
        } else {
          paramHashMap.put("BusinessEmployeesForReport", localObject7);
        }
      }
      else
      {
        localObject7 = str3;
        localObject9 = new ArrayList(1);
        ((ArrayList)localObject9).add(localObject7);
        if (str4.equals("User Entitlement Report")) {
          try
          {
            localBusinessEmployees1 = CustomerAdapter.getBusinessEmployeesByIds((ArrayList)localObject9);
            localObject11 = null;
            EntitlementGroupMember localEntitlementGroupMember = null;
            localObject14 = localBusinessEmployees1.listIterator();
            while (((ListIterator)localObject14).hasNext())
            {
              localObject11 = (BusinessEmployee)((ListIterator)localObject14).next();
              localEntitlementGroupMember = ((BusinessEmployee)localObject11).getEntitlementGroupMember();
              localEntitlementGroupMember = ((EntitlementCachedAdapter)localObject5).getMember(localEntitlementGroupMember);
              ((EntitlementGroupMembers)localObject3).add(localEntitlementGroupMember);
              ((BusinessEmployee)localObject11).setEntitlementGroupId(localEntitlementGroupMember.getEntitlementGroupId());
            }
            paramHashMap.put("BusinessEmployeesForReport", localBusinessEmployees1);
            paramHashMap.put("EntGroupMembersForReport", localObject3);
          }
          catch (Exception localException)
          {
            throw new AdminException(str1, 19, localException);
          }
        } else {
          paramHashMap.put("BusinessEmployeesForReport", localObject9);
        }
      }
      Object localObject10;
      Object localObject12;
      if ((str4.equals("System Activity Report")) || (str4.equals("On Behalf Of Report")) || (str4.equals("Session Activity Report")))
      {
        if (!str2.equals("AllGroups"))
        {
          int j = -1;
          try
          {
            j = Integer.parseInt(str2);
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            throw new AdminException(str1, 10, localNumberFormatException2);
          }
          localObject10 = null;
          try
          {
            localObject10 = Entitlements.getEntitlementGroup(j);
            if (localObject10 == null) {
              throw new AdminException(str1, 16, "No entitlement group for group ID = " + j);
            }
            paramHashMap.put("GroupNameForSearch", ((EntitlementGroup)localObject10).getGroupName());
          }
          catch (CSILException localCSILException5)
          {
            throw new AdminException(str1, 16, localCSILException5);
          }
        }
        if (!str3.equals("AllUsers")) {
          try
          {
            BusinessEmployee localBusinessEmployee1 = new BusinessEmployee(localLocale);
            localBusinessEmployee1.setBankId(paramSecureUser.getBankID());
            localBusinessEmployee1.setId(str3);
            localObject10 = UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee1, paramHashMap);
            if ((localObject10 != null) && (((BusinessEmployees)localObject10).size() != 0))
            {
              localObject12 = (BusinessEmployee)((BusinessEmployees)localObject10).get(0);
              paramHashMap.put("UserForSearch", localObject12);
            }
          }
          catch (CSILException localCSILException3)
          {
            throw new AdminException(str1, 19, localCSILException3);
          }
        }
      }
      if (str4.equals("On Behalf Of Report"))
      {
        localObject8 = localProperties.getProperty("Agent");
        if ((localObject8 != null) && (!((String)localObject8).equals("")) && (!((String)localObject8).equals("AllAgents")))
        {
          localObject10 = new BankEmployee(localLocale);
          ((BankEmployee)localObject10).setId((String)localObject8);
          ((BankEmployee)localObject10).setBankId(paramSecureUser.getBankID());
          localObject12 = null;
          try
          {
            localObject12 = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject10, paramHashMap);
          }
          catch (CSILException localCSILException7)
          {
            throw new AdminException(str1, 23, localCSILException7);
          }
          if (localObject12 != null) {
            paramHashMap.put("AgentForSearch", localObject12);
          }
        }
        if ((localObject8 != null) && ((((String)localObject8).equals("")) || (((String)localObject8).equals("AllAgents"))))
        {
          localObject10 = new BankEmployee(Locale.getDefault());
          ((BankEmployee)localObject10).setBankId(paramSecureUser.getBankID());
          try
          {
            localObject12 = BankEmployeeAdmin.getBankEmployeeList(paramSecureUser, (BankEmployee)localObject10, paramHashMap);
            paramHashMap.put("BankEmployeesForReport", localObject12);
          }
          catch (CSILException localCSILException6)
          {
            throw new AdminException(str1, 23, localCSILException6);
          }
        }
      }
    }
    Object localObject2 = null;
    Object localObject4;
    Object localObject13;
    if ((str4.equals("System Activity Report")) || (str4.equals("Session Activity Report")) || (str4.equals("On Behalf Of Report")) || (str4.equals("Positive Pay Activity")) || (str4.equals("Session Receipt Report")))
    {
      localObject2 = ReportAuditAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    else if (str4.equals("User Entitlement Report"))
    {
      try
      {
        localObject3 = (AccountService3)paramHashMap.get("ACCOUNT_SERVICE");
        localObject5 = ((AccountService3)localObject3).getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
        paramHashMap.put("AccountsForReport", localObject5);
      }
      catch (ProfileException localProfileException1)
      {
        throw new AdminException(str1, 18, localProfileException1);
      }
      localObject4 = new HashMap();
      try
      {
        localObject5 = (EntitlementGroupMembers)paramHashMap.get("EntGroupMembersForReport");
        localObject8 = ((EntitlementGroupMembers)localObject5).iterator();
        while (((Iterator)localObject8).hasNext())
        {
          int k = ((EntitlementGroupMember)((Iterator)localObject8).next()).getEntitlementGroupId();
          localObject13 = null;
          EntitlementAdmins localEntitlementAdmins = Entitlements.getAdminsForGroup(k);
          if ((localEntitlementAdmins != null) && (localEntitlementAdmins.size() > 0))
          {
            localObject14 = localEntitlementAdmins.iterator();
            while (((Iterator)localObject14).hasNext())
            {
              localObject15 = (EntitlementAdmin)((Iterator)localObject14).next();
              localObject16 = ((EntitlementAdmin)localObject15).getGranteeMemberId();
              if (localObject16 != null)
              {
                String str5 = ((EntitlementAdmin)localObject15).getGranteeMemberType();
                String str6 = ((EntitlementAdmin)localObject15).getGranteeMemberSubType();
                if ((str5 != null) && (str5.equals("USER")) && (str6 != null) && (str6.equals(String.valueOf(1))) && (((EntitlementAdmin)localObject15).canAdminister()))
                {
                  BusinessEmployee localBusinessEmployee2 = new BusinessEmployee();
                  localBusinessEmployee2.setBankId(paramSecureUser.getBankID());
                  localBusinessEmployee2.setId((String)localObject16);
                  BusinessEmployees localBusinessEmployees2 = UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee2, null);
                  if ((localBusinessEmployees2 != null) && (localBusinessEmployees2.size() > 0))
                  {
                    BusinessEmployee localBusinessEmployee3 = (BusinessEmployee)localBusinessEmployees2.get(0);
                    localObject13 = localBusinessEmployee3.getFirstName() + " " + localBusinessEmployee3.getLastName();
                  }
                }
              }
            }
          }
          ((HashMap)localObject4).put(new Integer(k), localObject13);
        }
      }
      catch (CSILException localCSILException2)
      {
        throw new AdminException(str1, 21, localCSILException2);
      }
      paramHashMap.put("SupervisorNamesForReport", localObject4);
      try
      {
        localObject2 = ((EntitlementReportingAdapter)localObject1).getReportData(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), paramReportCriteria, paramHashMap);
      }
      catch (EntitlementException localEntitlementException1)
      {
        throw new AdminException(str1, 17, localEntitlementException1);
      }
    }
    else
    {
      throw new AdminException(str1, 11, str4 + " is an invalid report type");
    }
    if ((localObject2 instanceof AdminSysActivityRpt))
    {
      ((AdminSysActivityRpt)localObject2).setUsers(localBusinessEmployees1);
    }
    else if ((localObject2 instanceof AdminOBORpt))
    {
      ((AdminOBORpt)localObject2).setUsers(localBusinessEmployees1);
      localObject4 = new HashSet();
      Object localObject6 = ((AdminOBORpt)localObject2).getAuditLogRecords().listIterator();
      localObject8 = null;
      while (((Iterator)localObject6).hasNext())
      {
        localObject8 = (AuditLogRecord)((Iterator)localObject6).next();
        if (((AuditLogRecord)localObject8).getAgentID() != null) {
          ((HashSet)localObject4).add(((AuditLogRecord)localObject8).getAgentID());
        }
      }
      ArrayList localArrayList = new ArrayList();
      localObject6 = ((HashSet)localObject4).iterator();
      while (((Iterator)localObject6).hasNext()) {
        localArrayList.add(((Iterator)localObject6).next());
      }
      try
      {
        localObject13 = BankEmployeeAdapter.getBankEmployeesByIds(localArrayList);
        ((AdminOBORpt)localObject2).setAgents((BankEmployees)localObject13);
      }
      catch (ProfileException localProfileException2)
      {
        throw new AdminException(str1, 19, localProfileException2);
      }
    }
    return localObject2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.admin.AdminService
 * JD-Core Version:    0.7.0.1
 */