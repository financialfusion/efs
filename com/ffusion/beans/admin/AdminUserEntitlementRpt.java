package com.ffusion.beans.admin;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupPermission;
import com.ffusion.csil.beans.entitlements.EntitlementGroupPermissions;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Entitlements;
import com.ffusion.csil.beans.entitlements.GroupEntitlementComparator;
import com.ffusion.csil.beans.entitlements.GroupLimitComparator;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocaleableBean;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class AdminUserEntitlementRpt
  extends LocaleableBean
  implements Serializable, IReportResult
{
  private static final Object[] R6 = new Object[0];
  private static final GroupLimitComparator Sc = new GroupLimitComparator();
  private static final GroupEntitlementComparator R8 = new GroupEntitlementComparator();
  private static final String Sb = System.getProperty("line.separator", "\n");
  private BusinessEmployees Sa;
  private Accounts R9;
  private EntitlementGroupPermissions R5;
  private HashMap R7;
  
  public void setBusinessEmployees(BusinessEmployees paramBusinessEmployees)
  {
    this.Sa = paramBusinessEmployees;
  }
  
  public BusinessEmployees getBusinessEmployees()
  {
    return this.Sa;
  }
  
  public void setAccounts(Accounts paramAccounts)
  {
    this.R9 = paramAccounts;
  }
  
  public Accounts getAccounts()
  {
    return this.R9;
  }
  
  public void setEntitlementGroupPermissions(EntitlementGroupPermissions paramEntitlementGroupPermissions)
  {
    this.R5 = paramEntitlementGroupPermissions;
  }
  
  public EntitlementGroupPermissions getEntitlementGroupPermissions()
  {
    return this.R5;
  }
  
  public void setSupervisorNameMap(HashMap paramHashMap)
  {
    this.R7 = paramHashMap;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    ReportCriteria localReportCriteria = null;
    try
    {
      localReportCriteria = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
    }
    catch (Exception localException) {}
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',', localReportCriteria);
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t', localReportCriteria);
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar, ReportCriteria paramReportCriteria)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    String str1 = null;
    String str2 = null;
    int j = 0;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties != null)
    {
      str1 = localProperties.getProperty("User");
      str1 = (str1 != null) && (str1.length() == 0) ? null : str1;
      str2 = localProperties.getProperty("Group");
      str2 = (str2 != null) && (str2.length() == 0) ? null : str2;
    }
    if ((str2 != null) && (!str2.equals("AllGroups"))) {
      try
      {
        j = Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        str2 = null;
      }
    }
    int k = this.Sa == null ? 0 : this.Sa.size();
    for (int m = 0; m < k; m++)
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)this.Sa.get(m);
      if ((str2 == null) || (j == localBusinessEmployee.getEntitlementGroupId()))
      {
        int n = 0;
        EntitlementGroupMember localEntitlementGroupMember = localBusinessEmployee.getEntitlementGroupMember();
        if (localEntitlementGroupMember != null)
        {
          Object localObject1 = null;
          int i1 = this.R5 == null ? 0 : this.R5.size();
          Object localObject2;
          for (int i2 = 0; i2 < i1; i2++)
          {
            localObject2 = this.R5.getPermission(i2);
            if ((localObject2 != null) && (localEntitlementGroupMember.equals(((EntitlementGroupPermission)localObject2).getEntitlementGroupMember())))
            {
              localObject1 = localObject2;
              break;
            }
          }
          if (localObject1 != null)
          {
            i = 1;
            localStringBuffer.append(Sb);
            localStringBuffer.append(localBusinessEmployee.getName() == null ? "" : localBusinessEmployee.getName());
            EntitlementGroup localEntitlementGroup = localObject1.getEntitlementGroup();
            if (localEntitlementGroup != null)
            {
              localStringBuffer.append(paramChar);
              localStringBuffer.append("Group: ");
              localStringBuffer.append(localEntitlementGroup.getGroupName() == null ? "" : localEntitlementGroup.getGroupName());
              localStringBuffer.append(paramChar);
              localStringBuffer.append("Supervisor: ");
              if (this.R7 != null)
              {
                localObject2 = (String)this.R7.get(new Integer(localEntitlementGroup.getGroupId()));
                if (localObject2 != null) {
                  localStringBuffer.append((String)localObject2);
                }
              }
            }
            localStringBuffer.append(Sb);
            int i3 = 0;
            int i4 = 0;
            Object localObject3 = "";
            Object localObject4 = "";
            Object[] arrayOfObject1 = localObject1.getLimits() == null ? R6 : localObject1.getLimits().toArray();
            Arrays.sort(arrayOfObject1, Sc);
            Object localObject5;
            String str4;
            for (int i5 = 0; i5 < arrayOfObject1.length; i5++)
            {
              Limit localLimit = (Limit)arrayOfObject1[i5];
              if (("Account".equals(localLimit.getObjectType())) || ("ACHCompany".equals(localLimit.getObjectType())))
              {
                localObject5 = localLimit.getObjectId();
                String str3 = localLimit.getLimitName();
                str4 = str3;
                int i8 = 0;
                EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = localObject1.getEntitlementTypePropertyLists();
                for (int i10 = 0; i10 < localEntitlementTypePropertyLists2.size(); i10++)
                {
                  EntitlementTypePropertyList localEntitlementTypePropertyList2 = (EntitlementTypePropertyList)localEntitlementTypePropertyLists2.get(i10);
                  if (((str3 != null) || (localEntitlementTypePropertyList2.getOperationName() == null)) && ((str3 == null) || (localEntitlementTypePropertyList2.getOperationName() != null)) && (str3.equals(localEntitlementTypePropertyList2.getOperationName())))
                  {
                    if (localEntitlementTypePropertyList2.isPropertySet("display name")) {
                      str4 = localEntitlementTypePropertyList2.getPropertyValue("display name", 0);
                    }
                    if ((localEntitlementTypePropertyList2.isPropertySet("hide")) && (localEntitlementTypePropertyList2.getPropertyValue("hide", 0).equals("yes"))) {
                      i8 = 1;
                    }
                  }
                }
                if (i8 == 0)
                {
                  n = 1;
                  if (i3 == 0)
                  {
                    jdMethod_for(localStringBuffer, paramChar);
                    i3 = 1;
                    i4 = 1;
                  }
                  localObject5 = localObject5 == null ? "" : localObject5;
                  str3 = str3 == null ? "" : str3;
                  i10 = (((String)localObject3).equals(localObject5)) && (((String)localObject4).equals(str3)) ? 1 : 0;
                  localObject3 = localObject5 == null ? "" : localObject5;
                  localObject4 = str3 == null ? "" : str3;
                  if (i10 != 0)
                  {
                    localStringBuffer.append(paramChar);
                    localStringBuffer.append(paramChar);
                    localStringBuffer.append(paramChar);
                  }
                  else
                  {
                    if ("Account".equals(localLimit.getObjectType())) {
                      jdMethod_int(localStringBuffer, paramChar, (String)localObject5);
                    } else if ("ACHCompany".equals(localLimit.getObjectType())) {
                      jdMethod_for(localStringBuffer, paramChar, (String)localObject5);
                    }
                    localStringBuffer.append(str3.equals("") ? "All Transactions" : str4);
                    localStringBuffer.append(paramChar);
                  }
                  localStringBuffer.append(jdMethod_new(localLimit));
                  localStringBuffer.append(paramChar);
                  localStringBuffer.append(localLimit.getData() == null ? "" : localLimit.getData());
                  localStringBuffer.append(paramChar);
                  localStringBuffer.append(localLimit.isAllowedApproval() ? "W / Approval" : "Never");
                  localStringBuffer.append(Sb);
                }
              }
            }
            Object[] arrayOfObject2 = localObject1.getEntitlements() == null ? R6 : localObject1.getEntitlements().toArray();
            Arrays.sort(arrayOfObject2, R8);
            for (int i6 = 0; i6 < arrayOfObject2.length; i6++)
            {
              localObject5 = (Entitlement)arrayOfObject2[i6];
              if (("Account".equals(((Entitlement)localObject5).getObjectType())) || ("ACHCompany".equals(((Entitlement)localObject5).getObjectType())))
              {
                int i7 = 0;
                str4 = ((Entitlement)localObject5).getOperationName();
                EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = localObject1.getEntitlementTypePropertyLists();
                for (int i9 = 0; i9 < localEntitlementTypePropertyLists1.size(); i9++)
                {
                  EntitlementTypePropertyList localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)localEntitlementTypePropertyLists1.get(i9);
                  if (((((Entitlement)localObject5).getOperationName() != null) || (localEntitlementTypePropertyList1.getOperationName() == null)) && ((((Entitlement)localObject5).getOperationName() == null) || (localEntitlementTypePropertyList1.getOperationName() != null)) && (((Entitlement)localObject5).getOperationName().equals(localEntitlementTypePropertyList1.getOperationName())))
                  {
                    if (localEntitlementTypePropertyList1.isPropertySet("display name")) {
                      str4 = localEntitlementTypePropertyList1.getPropertyValue("display name", 0);
                    }
                    if ((localEntitlementTypePropertyList1.isPropertySet("hide")) && (localEntitlementTypePropertyList1.getPropertyValue("hide", 0).equals("yes"))) {
                      i7 = 1;
                    }
                  }
                }
                if (i7 == 0)
                {
                  n = 1;
                  if (i3 == 0)
                  {
                    jdMethod_for(localStringBuffer, paramChar);
                    i3 = 1;
                  }
                  if (i4 != 0)
                  {
                    localStringBuffer.append(Sb);
                    i4 = 0;
                  }
                  if ("Account".equals(((Entitlement)localObject5).getObjectType())) {
                    jdMethod_int(localStringBuffer, paramChar, ((Entitlement)localObject5).getObjectId());
                  } else if ("ACHCompany".equals(((Entitlement)localObject5).getObjectType())) {
                    jdMethod_for(localStringBuffer, paramChar, ((Entitlement)localObject5).getObjectId());
                  }
                  localStringBuffer.append(str4 == null ? "All Transactions" : str4);
                  localStringBuffer.append(paramChar);
                  localStringBuffer.append("Transaction is prohibited");
                  localStringBuffer.append(Sb);
                }
              }
            }
            if (n == 0)
            {
              localStringBuffer.append("User has no restrictions");
              localStringBuffer.append(Sb);
            }
          }
        }
      }
    }
    if (i == 0)
    {
      localStringBuffer.append("No report data available");
      localStringBuffer.append(Sb);
    }
    return localStringBuffer;
  }
  
  private static String jdMethod_new(Limit paramLimit)
  {
    int i = paramLimit == null ? 0 : paramLimit.getPeriod();
    switch (i)
    {
    case 0: 
      return "Undefined";
    case 1: 
      return "Transaction";
    case 2: 
      return "Day";
    case 3: 
      return "Week";
    case 4: 
      return "Month";
    }
    return "Undefined";
  }
  
  private static void jdMethod_for(StringBuffer paramStringBuffer, char paramChar)
  {
    Locale localLocale = ExtendABean.DEFAULT_LOCALE;
    paramStringBuffer.append(ReportConsts.getColumnName(307, localLocale));
    paramStringBuffer.append(paramChar);
    paramStringBuffer.append(paramChar);
    paramStringBuffer.append(ReportConsts.getColumnName(303, localLocale));
    paramStringBuffer.append(paramChar);
    paramStringBuffer.append(ReportConsts.getColumnName(304, localLocale));
    paramStringBuffer.append(paramChar);
    paramStringBuffer.append(ReportConsts.getColumnName(305, localLocale));
    paramStringBuffer.append(paramChar);
    paramStringBuffer.append(ReportConsts.getColumnName(306, localLocale));
    paramStringBuffer.append(Sb);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, char paramChar, String paramString)
  {
    if ((paramString == null) || (paramString.equals("")))
    {
      paramStringBuffer.append("All Accounts");
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(paramChar);
    }
    else
    {
      int i = this.R9 == null ? 0 : this.R9.size();
      for (int j = 0; j < i; j++)
      {
        Account localAccount = (Account)this.R9.get(j);
        String str1 = EntitlementsUtil.getEntitlementObjectId(localAccount);
        if (paramString.equals(str1))
        {
          paramStringBuffer.append(localAccount.getID() == null ? "" : localAccount.getID());
          String str2 = localAccount.getNickName();
          if ((str2 != null) && (str2.length() != 0))
          {
            paramStringBuffer.append(" - ");
            paramStringBuffer.append(localAccount.getNickName());
          }
          paramStringBuffer.append(paramChar);
          paramStringBuffer.append("Type: " + localAccount.getType());
          paramStringBuffer.append(paramChar);
          break;
        }
      }
    }
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, char paramChar, String paramString)
  {
    if ((paramString == null) || (paramString.equals("")))
    {
      paramStringBuffer.append("All ACH Companies");
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append(paramChar);
    }
    else
    {
      paramStringBuffer.append("ACH Company");
      paramStringBuffer.append(paramChar);
      paramStringBuffer.append("ID: " + paramString);
      paramStringBuffer.append(paramChar);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.admin.AdminUserEntitlementRpt
 * JD-Core Version:    0.7.0.1
 */