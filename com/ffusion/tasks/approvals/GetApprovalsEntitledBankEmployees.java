package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Entitlements;
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

public class GetApprovalsEntitledBankEmployees
  extends GetApprovalsEntitledBankGroups
{
  private static Entitlement aN7 = new Entitlement("Bank Approve Limits", null, null);
  private static Entitlement aOa = new Entitlement("Manage Consumer Banking", null, null);
  private static Entitlement aN8 = new Entitlement("Manage Corporate Banking", null, null);
  private String aOb = "";
  private String aN9 = null;
  public static String CONSUMER = "Consumer";
  public static String CORPORATE = "Corporate";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      localHttpSession.removeAttribute("ApprovalsEntitledBankEmployees");
      EntitlementGroups localEntitlementGroups = null;
      Object localObject = localHttpSession.getAttribute("ApprovalsEntitledBankGroups");
      if ((localObject != null) && ((localObject instanceof EntitlementGroups)))
      {
        localEntitlementGroups = (EntitlementGroups)localObject;
      }
      else
      {
        GetApprovalsEntitledBankGroups.insertApprovalBankGroupsInSession(localHttpSession, this.aOb, this.aN9);
        localEntitlementGroups = (EntitlementGroups)localHttpSession.getAttribute("ApprovalsEntitledBankGroups");
      }
      if ((localEntitlementGroups != null) && (!localEntitlementGroups.isEmpty()))
      {
        BankEmployees localBankEmployees = new BankEmployees(Locale.getDefault());
        Iterator localIterator1 = localEntitlementGroups.iterator();
        while (localIterator1.hasNext())
        {
          EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator1.next();
          int i = localEntitlementGroup.getGroupId();
          EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(i);
          Iterator localIterator2 = localEntitlementGroupMembers.iterator();
          while (localIterator2.hasNext())
          {
            EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localIterator2.next();
            if (Entitlements.checkEntitlement(localEntitlementGroupMember, aN7))
            {
              BankEmployee localBankEmployee = localBankEmployees.add();
              localBankEmployee.setId(localEntitlementGroupMember.getId());
              localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
              localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(localSecureUser, localBankEmployee, localHashMap);
              if ((!this.aOb.equals("")) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement("BC Manage Multiple Banks Simultaneously", null, null))))
              {
                ArrayList localArrayList = localBankEmployee.getAffiliateBankIds();
                if (!localArrayList.contains(this.aOb))
                {
                  localBankEmployees.removeByID(localBankEmployee.getId());
                  continue;
                }
              }
              if (this.aN9 != null)
              {
                if ((this.aN9.equals(CONSUMER)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aOa))) {
                  localBankEmployees.removeByID(localBankEmployee.getId());
                }
                if ((this.aN9.equals(CORPORATE)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aN8))) {
                  localBankEmployees.removeByID(localBankEmployee.getId());
                }
              }
            }
          }
        }
        if (!localBankEmployees.isEmpty()) {
          localHttpSession.setAttribute("ApprovalsEntitledBankEmployees", localBankEmployees);
        }
      }
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
    this.aOb = paramString;
  }
  
  public void setType(String paramString)
  {
    this.aN9 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetApprovalsEntitledBankEmployees
 * JD-Core Version:    0.7.0.1
 */