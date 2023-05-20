package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApprovalsEntitledBankGroups
  extends BaseTask
{
  private static Entitlement aN2 = new Entitlement("Bank Approve Limits", null, null);
  private static Entitlement aN5 = new Entitlement("Manage Consumer Banking", null, null);
  private static Entitlement aN3 = new Entitlement("Manage Corporate Banking", null, null);
  private String aN6 = "";
  private String aN4 = null;
  public static String CONSUMER = "Consumer";
  public static String CORPORATE = "Corporate";
  
  protected static void insertApprovalBankGroupsInSession(HttpSession paramHttpSession, String paramString1, String paramString2)
    throws CSILException
  {
    paramHttpSession.removeAttribute("ApprovalsEntitledBankGroups");
    EntitlementGroups localEntitlementGroups1 = Entitlements.getEntitlementGroupsByType("EmployeeType");
    EntitlementGroups localEntitlementGroups2 = (EntitlementGroups)localEntitlementGroups1.clone();
    Iterator localIterator1 = localEntitlementGroups1.iterator();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    while (localIterator1.hasNext())
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator1.next();
      int i = localEntitlementGroup.getGroupId();
      if (!Entitlements.checkEntitlement(i, aN2))
      {
        localEntitlementGroups2.remove(localEntitlementGroup);
      }
      else
      {
        if (paramString2 != null)
        {
          if ((paramString2.equals(CONSUMER)) && (!Entitlements.checkEntitlement(i, aN5))) {
            localEntitlementGroups2.remove(localEntitlementGroup);
          }
          if ((paramString2.equals(CORPORATE)) && (!Entitlements.checkEntitlement(i, aN3))) {
            localEntitlementGroups2.remove(localEntitlementGroup);
          }
        }
        int j = 0;
        EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(i);
        Iterator localIterator2 = localEntitlementGroupMembers.iterator();
        while (localIterator2.hasNext())
        {
          EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localIterator2.next();
          BankEmployee localBankEmployee = new BankEmployee(Locale.getDefault());
          localBankEmployee.setId(localEntitlementGroupMember.getId());
          HashMap localHashMap = new HashMap();
          localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
          localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(localSecureUser, localBankEmployee, localHashMap);
          if ((Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement("BC Manage Multiple Banks Simultaneously", null, null))) || (paramString1.equals("")) || ((!paramString1.equals("")) && (localBankEmployee.getAffiliateBankIds().contains(paramString1))))
          {
            j = 1;
            break;
          }
        }
        if (j == 0) {
          localEntitlementGroups2.remove(localEntitlementGroup);
        }
      }
    }
    if (!localEntitlementGroups2.isEmpty()) {
      paramHttpSession.setAttribute("ApprovalsEntitledBankGroups", localEntitlementGroups2);
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      insertApprovalBankGroupsInSession(localHttpSession, this.aN6, this.aN4);
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str;
  }
  
  public void setAffiliateID(String paramString)
  {
    this.aN6 = paramString;
  }
  
  public void setType(String paramString)
  {
    this.aN4 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetApprovalsEntitledBankGroups
 * JD-Core Version:    0.7.0.1
 */