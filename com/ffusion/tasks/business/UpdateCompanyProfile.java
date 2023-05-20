package com.ffusion.tasks.business;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.services.Alerts;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateCompanyProfile
  implements BusinessTask
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error;
  protected String employeeName;
  protected String contactMethod;
  protected String accountsName;
  protected String groupId;
  protected String operationName;
  protected String objectType;
  protected int period;
  protected String achCompanyName = null;
  protected String achCompanyId = null;
  public static final String ACCOUNTPREFIX = "Acct_";
  public static final String ACCOUNTNUMBERSEPARATOR = "-";
  public static final String ACCOUNTLIMITSUFFIX = "_Limit";
  public static final String ACCOUNTNICKNAMESUFFIX = "_Nick";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessEmployee localBusinessEmployee = (BusinessEmployee)localHttpSession.getAttribute(this.employeeName);
    if (localBusinessEmployee == null)
    {
      this.error = 4126;
      str1 = this.taskErrorURL;
    }
    else
    {
      localBusinessEmployee.setPreferredContactMethod(this.contactMethod);
      Alerts localAlerts = null;
      localAlerts = (Alerts)localHttpSession.getAttribute("com.ffusion.services.Alerts");
      if (localAlerts == null) {
        localAlerts = (Alerts)localHttpSession.getAttribute("com.ffusion.services.Alerts");
      }
      if (localHashMap == null) {
        localHashMap = new HashMap();
      }
      localHashMap.put("SERVICE", localAlerts);
      try
      {
        localBusinessEmployee = UserAdmin.modifyBusinessEmployee(localSecureUser, localBusinessEmployee, localHashMap);
        localHttpSession.setAttribute(this.employeeName, localBusinessEmployee);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str1 = this.serviceErrorURL;
      }
      Object localObject2;
      Object localObject3;
      String str2;
      String str3;
      Object localObject4;
      try
      {
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(Integer.parseInt(this.groupId));
        localObject1 = localEntitlementGroup.getProperties();
        ((EntitlementGroupProperties)localObject1).setCurrentProperty("ACHCompanyID");
        localObject2 = ((EntitlementGroupProperties)localObject1).getValueOfCurrentProperty();
        ((EntitlementGroupProperties)localObject1).setCurrentProperty("ACHCompanyName");
        localObject3 = ((EntitlementGroupProperties)localObject1).getValueOfCurrentProperty();
        ((EntitlementGroupProperties)localObject1).setCurrentProperty("BillPayWarehouseID");
        str2 = ((EntitlementGroupProperties)localObject1).getValueOfCurrentProperty();
        if ((this.achCompanyName != null) && (this.achCompanyId != null))
        {
          this.achCompanyName = this.achCompanyName.trim();
          this.achCompanyId = this.achCompanyId.trim();
        }
        if ((str2 == null) || (str2.trim().length() == 0))
        {
          if ((this.achCompanyName != null) && (this.achCompanyName.length() != 0) && (this.achCompanyName.length() <= 16) && (this.achCompanyId != null) && (this.achCompanyId.length() != 0) && (this.achCompanyId.length() <= 10)) {
            try
            {
              ACHCompany localACHCompany1 = new ACHCompany();
              localACHCompany1.setCompanyID(this.achCompanyId);
              localACHCompany1.setCompanyName(this.achCompanyName);
              localACHCompany1 = ACH.addACHCompany(localSecureUser, localACHCompany1, localSecureUser.getBankID(), localHashMap);
              ((EntitlementGroupProperties)localObject1).setCurrentProperty("ACHCompanyID");
              ((EntitlementGroupProperties)localObject1).setValueOfCurrentProperty(this.achCompanyId);
              ((EntitlementGroupProperties)localObject1).setCurrentProperty("ACHCompanyName");
              ((EntitlementGroupProperties)localObject1).setValueOfCurrentProperty(this.achCompanyName);
              ((EntitlementGroupProperties)localObject1).setCurrentProperty("BillPayWarehouseID");
              ((EntitlementGroupProperties)localObject1).setValueOfCurrentProperty(localACHCompany1.getID());
              Entitlements.modifyEntitlementGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup);
            }
            catch (CSILException localCSILException3)
            {
              this.error = MapError.mapError(localCSILException3);
              str1 = this.serviceErrorURL;
            }
          }
        }
        else
        {
          if ((this.achCompanyName == null) || (this.achCompanyName.length() == 0) || (this.achCompanyName.length() > 16) || (this.achCompanyId == null) || (this.achCompanyId.length() == 0) || (this.achCompanyId.length() > 10))
          {
            this.error = 4137;
            str1 = this.taskErrorURL;
            return str1;
          }
          if ((!this.achCompanyName.equals(localObject3)) || (!this.achCompanyId.equals(localObject2)))
          {
            ((EntitlementGroupProperties)localObject1).setCurrentProperty("BillPayWarehouseID");
            str3 = ((EntitlementGroupProperties)localObject1).getValueOfCurrentProperty();
            try
            {
              ACHCompany localACHCompany2 = new ACHCompany(str3, Integer.toString(localSecureUser.getBusinessID()), this.achCompanyId, this.achCompanyName);
              localObject4 = new ACHCompany(str3, Integer.toString(localSecureUser.getBusinessID()), (String)localObject2, (String)localObject3);
              ACH.modifyACHCompany(localSecureUser, localACHCompany2, (ACHCompany)localObject4, localSecureUser.getBankID(), localHashMap);
              ((EntitlementGroupProperties)localObject1).setCurrentProperty("ACHCompanyID");
              ((EntitlementGroupProperties)localObject1).setValueOfCurrentProperty(this.achCompanyId);
              ((EntitlementGroupProperties)localObject1).setCurrentProperty("ACHCompanyName");
              ((EntitlementGroupProperties)localObject1).setValueOfCurrentProperty(this.achCompanyName);
              Entitlements.modifyEntitlementGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup);
            }
            catch (CSILException localCSILException4)
            {
              this.error = MapError.mapError(localCSILException4);
              str1 = this.serviceErrorURL;
            }
          }
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        str1 = this.serviceErrorURL;
      }
      com.ffusion.beans.accounts.Accounts localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.accountsName);
      Object localObject1 = (Locale)localHttpSession.getAttribute("java.util.Locale");
      if (localAccounts == null)
      {
        this.error = 39;
        str1 = this.taskErrorURL;
      }
      else
      {
        localObject2 = localAccounts.iterator();
        while ((((Iterator)localObject2).hasNext()) && (this.error == 0))
        {
          localObject3 = (Account)((Iterator)localObject2).next();
          str2 = (String)localHttpSession.getAttribute("Acct_" + ((Account)localObject3).getBankID() + "-" + ((Account)localObject3).getRoutingNum() + "-" + ((Account)localObject3).getNumber() + "-" + ((Account)localObject3).getTypeValue() + "_Nick");
          str3 = (String)localHttpSession.getAttribute("Acct_" + ((Account)localObject3).getBankID() + "-" + ((Account)localObject3).getRoutingNum() + "-" + ((Account)localObject3).getNumber() + "-" + ((Account)localObject3).getTypeValue() + "_Limit");
          if (!Currency.isValid(str3, (Locale)localObject1))
          {
            this.error = 4136;
            return this.taskErrorURL;
          }
          if (str2.compareTo(((Account)localObject3).getNickName()) != 0)
          {
            ((Account)localObject3).setNickName(str2);
            try
            {
              localObject3 = com.ffusion.csil.core.Accounts.modifyAccountById(localSecureUser, localBusinessEmployee.getBusinessId(), (Account)localObject3, localHashMap);
            }
            catch (CSILException localCSILException5)
            {
              this.error = MapError.mapError(localCSILException5);
              str1 = this.serviceErrorURL;
            }
          }
          if (this.error == 0) {
            try
            {
              Entitlement localEntitlement = new Entitlement(this.operationName, this.objectType, EntitlementsUtil.getEntitlementObjectId((Account)localObject3));
              localObject4 = new Limit();
              ((Limit)localObject4).setEntitlement(localEntitlement);
              ((Limit)localObject4).setPeriod(this.period);
              Limits localLimits = Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), Integer.parseInt(this.groupId), (Limit)localObject4, localHashMap);
              Iterator localIterator = localLimits.iterator();
              Object localObject5 = null;
              if (localIterator.hasNext())
              {
                localObject5 = (Limit)localIterator.next();
                if ((str3 == null) || (str3.length() <= 0))
                {
                  Entitlements.deleteGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), (Limit)localObject5);
                }
                else
                {
                  ((Limit)localObject5).setData(str3);
                  localObject5 = Entitlements.modifyGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), (Limit)localObject5);
                }
              }
              else if ((str3 != null) && (str3.length() > 0))
              {
                localObject5 = localObject4;
                ((Limit)localObject5).setLimitName(null);
                ((Limit)localObject5).setGroupId(Integer.parseInt(this.groupId));
                ((Limit)localObject5).setData(str3);
                localObject5 = Entitlements.addGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), (Limit)localObject5);
              }
            }
            catch (CSILException localCSILException6)
            {
              this.error = MapError.mapError(localCSILException6);
              str1 = this.serviceErrorURL;
            }
          }
        }
      }
    }
    return str1;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setEmployeeName(String paramString)
  {
    this.employeeName = paramString;
  }
  
  public void setContactMethod(String paramString)
  {
    this.contactMethod = paramString;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.groupId = paramString;
  }
  
  public void setOperationName(String paramString)
  {
    this.operationName = paramString;
  }
  
  public void setObjectType(String paramString)
  {
    this.objectType = paramString;
  }
  
  public void setPeriod(String paramString)
  {
    try
    {
      this.period = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setAchCompanyID(String paramString)
  {
    this.achCompanyId = paramString;
  }
  
  public void setAchCompanyName(String paramString)
  {
    this.achCompanyName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.UpdateCompanyProfile
 * JD-Core Version:    0.7.0.1
 */