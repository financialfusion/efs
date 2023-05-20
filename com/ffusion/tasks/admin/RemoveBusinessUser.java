package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Alerts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RemoveBusinessUser
  extends BaseTask
  implements AdminTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessEmployee localBusinessEmployee = (BusinessEmployee)localHttpSession.getAttribute("BusinessEmployee");
    if (localBusinessEmployee == null)
    {
      this.error = 4501;
      return super.getTaskErrorURL();
    }
    if (String.valueOf(localSecureUser.getProfileID()).equals(localBusinessEmployee.getId()))
    {
      this.error = 4541;
      return super.getTaskErrorURL();
    }
    try
    {
      if (Approvals.isUserInUse(localBusinessEmployee.getSecureUser(), null))
      {
        this.error = 4526;
        return this.taskErrorURL;
      }
      if (jdMethod_for(localBusinessEmployee.getEntitlementGroupMember()))
      {
        this.error = 4562;
        return this.taskErrorURL;
      }
      localBusinessEmployee.setAccountStatus(String.valueOf(8));
      Alerts localAlerts = (Alerts)localHttpSession.getAttribute("com.ffusion.services.Alerts");
      HashMap localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAlerts);
      UserAdmin.modifyBusinessEmployee(localSecureUser, localBusinessEmployee, localHashMap);
      Entitlements.removeMember(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localBusinessEmployee.getEntitlementGroupMember());
      HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, localBusinessEmployee.getId());
      localBusinessEmployee.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for RemoveBusinessUser: " + localProfileException.toString());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  private boolean jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    int i = Entitlements.getNumMembers(paramEntitlementGroupMember.getEntitlementGroupId());
    EntitlementAdmins localEntitlementAdmins1 = Entitlements.getAdminInfoFor(paramEntitlementGroupMember);
    localEntitlementAdmins1.addAll(Entitlements.getAdminInfoFor(paramEntitlementGroupMember.getEntitlementGroupId()));
    Iterator localIterator1 = localEntitlementAdmins1.iterator();
    while (localIterator1.hasNext())
    {
      EntitlementAdmin localEntitlementAdmin1 = (EntitlementAdmin)localIterator1.next();
      if (localEntitlementAdmin1.canAdminister())
      {
        EntitlementAdmins localEntitlementAdmins2 = Entitlements.getAdminsForGroup(localEntitlementAdmin1.getTargetGroupId());
        Iterator localIterator2 = localEntitlementAdmins2.iterator();
        int j = 0;
        while (localIterator2.hasNext())
        {
          EntitlementAdmin localEntitlementAdmin2 = (EntitlementAdmin)localIterator2.next();
          if ((localEntitlementAdmin2.getGranteeGroupId() != paramEntitlementGroupMember.getEntitlementGroupId()) && ((localEntitlementAdmin2.getGranteeMemberType() != null) || (Entitlements.getNumMembers(localEntitlementAdmin2.getGranteeGroupId()) > 0)))
          {
            j = 1;
            break;
          }
          if (localEntitlementAdmin2.getGranteeMemberType() == null)
          {
            if (i > 1)
            {
              j = 1;
              break;
            }
          }
          else if ((!paramEntitlementGroupMember.getMemberType().equals(localEntitlementAdmin2.getGranteeMemberType())) || (!paramEntitlementGroupMember.getMemberSubType().equals(localEntitlementAdmin2.getGranteeMemberSubType())) || (!paramEntitlementGroupMember.getId().equals(localEntitlementAdmin2.getGranteeMemberId())))
          {
            j = 1;
            break;
          }
        }
        if (j == 0) {
          return true;
        }
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.RemoveBusinessUser
 * JD-Core Version:    0.7.0.1
 */