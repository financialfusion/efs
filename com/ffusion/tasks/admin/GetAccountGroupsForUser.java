package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountGroupsForUser
  extends BaseTask
  implements AdminTask
{
  protected String businessEmployeeSessionName = "BusinessEmployee";
  private static final String Z9 = "USER";
  private String Z8 = "GroupAccounts";
  private int Z7 = -1;
  private String Z6 = "";
  private int aac = -1;
  private int aab = -1;
  private boolean aaa = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    HashMap localHashMap = new HashMap();
    BusinessAccountGroups localBusinessAccountGroups = null;
    try
    {
      SecureUser localSecureUser = null;
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localHttpSession.getAttribute(this.businessEmployeeSessionName);
      if (localBusinessEmployee != null) {
        localSecureUser = localBusinessEmployee.getSecureUser();
      } else {
        localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      }
      if (this.aab != -1) {
        localBusinessAccountGroups = AccountGroup.getAccountGroups(localSecureUser, this.aab, localHashMap);
      }
      if (localBusinessAccountGroups != null)
      {
        EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
        localEntitlementGroupMember.setId(this.Z6);
        localEntitlementGroupMember.setMemberType("USER");
        localEntitlementGroupMember.setMemberSubType(Integer.toString(this.aac));
        localEntitlementGroupMember.setEntitlementGroupId(this.Z7);
        if (this.aaa) {
          this.aaa = Entitlements.canAdministerAnyGroup(localEntitlementGroupMember);
        }
        Iterator localIterator = localBusinessAccountGroups.iterator();
        while (localIterator.hasNext())
        {
          BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)localIterator.next();
          Entitlement localEntitlement = null;
          localEntitlement = new Entitlement("Access", "AccountGroup", EntitlementsUtil.getEntitlementObjectId(localBusinessAccountGroup));
          if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement)) {
            if (this.aaa)
            {
              localEntitlement.setOperationName("Access (admin)");
              if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement)) {
                localIterator.remove();
              }
            }
            else
            {
              localIterator.remove();
            }
          }
        }
        localHttpSession.setAttribute(this.Z8, localBusinessAccountGroups);
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 4512;
      str = this.taskErrorURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    this.Z7 = -1;
    this.Z6 = "";
    this.aac = -1;
    return str;
  }
  
  public void setMemberID(String paramString)
  {
    this.Z6 = paramString;
  }
  
  public void setUserType(String paramString)
  {
    this.aac = Integer.parseInt(paramString);
  }
  
  public void setGroupID(String paramString)
  {
    try
    {
      this.Z7 = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.Z7 = -1;
    }
  }
  
  public void setGroupAccountGroupsName(String paramString)
  {
    this.Z8 = paramString;
  }
  
  public void setBusDirectoryId(String paramString)
  {
    try
    {
      this.aab = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aab = -1;
    }
  }
  
  public void setCheckAdminAccess(String paramString)
  {
    this.aaa = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetAccountGroupsForUser
 * JD-Core Version:    0.7.0.1
 */