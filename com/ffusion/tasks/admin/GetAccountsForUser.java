package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountsForUser
  extends BaseTask
  implements AdminTask
{
  protected String businessEmployeeSessionName = "BusinessEmployee";
  private String aae = "GroupAccounts";
  private int aag = -1;
  private String aad = "";
  private int aai = -1;
  private boolean aaf = true;
  private boolean aah = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    com.ffusion.beans.accounts.Accounts localAccounts = null;
    try
    {
      SecureUser localSecureUser = null;
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localHttpSession.getAttribute(this.businessEmployeeSessionName);
      if (localBusinessEmployee != null) {
        localSecureUser = localBusinessEmployee.getSecureUser();
      } else {
        localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      }
      localAccounts = com.ffusion.csil.core.Accounts.getAccountsByBusinessEmployee(localSecureUser, null);
      EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setId(this.aad);
      localEntitlementGroupMember.setMemberType("USER");
      localEntitlementGroupMember.setMemberSubType(Integer.toString(this.aai));
      localEntitlementGroupMember.setEntitlementGroupId(this.aag);
      if (this.aah) {
        this.aah = Entitlements.canAdministerAnyGroup(localEntitlementGroupMember);
      }
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      String[] arrayOfString1 = { "Account" };
      String[] arrayOfString2 = new String[1];
      localMultiEntitlement.setOperations(new String[0]);
      Iterator localIterator = localAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(localAccount);
        localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
        if (this.aaf)
        {
          Entitlement localEntitlement1 = EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, localSecureUser.getBusinessID());
          if ((localEntitlement1 != null) && (this.aah))
          {
            localMultiEntitlement.setOperations(new String[] { "Access (admin)" });
            localEntitlement1 = EntitlementsUtil.checkAccountAndAccountGroupEntitlement(localEntitlementGroupMember, localMultiEntitlement, localSecureUser.getBusinessID());
            localMultiEntitlement.setOperations(null);
          }
          if (localEntitlement1 != null) {
            localIterator.remove();
          }
        }
        else
        {
          boolean bool = false;
          Entitlement localEntitlement2 = new Entitlement("Access", "Account", arrayOfString2[0]);
          if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement2))
          {
            if (this.aah)
            {
              localEntitlement2.setOperationName("Access (admin)");
              bool = Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement2);
            }
          }
          else {
            bool = true;
          }
          if (!bool) {
            localIterator.remove();
          }
        }
      }
      localHttpSession.setAttribute(this.aae, localAccounts);
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
    this.aag = -1;
    this.aad = "";
    this.aai = -1;
    return str;
  }
  
  public void setGroupID(String paramString)
  {
    this.aag = Integer.parseInt(paramString);
  }
  
  public void setMemberID(String paramString)
  {
    this.aad = paramString;
  }
  
  public void setUserType(String paramString)
  {
    this.aai = Integer.parseInt(paramString);
  }
  
  public void setGroupAccountsName(String paramString)
  {
    this.aae = paramString;
  }
  
  public void setCheckAdminAccess(String paramString)
  {
    this.aah = new Boolean(paramString).booleanValue();
  }
  
  public void setCheckAccountGroupAccess(String paramString)
  {
    this.aaf = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetAccountsForUser
 * JD-Core Version:    0.7.0.1
 */