package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsGroupMember;
import com.ffusion.beans.approvals.ApprovalsGroupMembers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConvertApprovalsGroupMembersToEntitlementGroupMembers
  extends BaseTask
  implements IApprovalsTask
{
  private String aNX = "ApprovalsGroupMembers";
  private String aNW = "Entitlement_Group_Members";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = getSuccessURL();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = null;
    ApprovalsGroupMembers localApprovalsGroupMembers = null;
    EntitlementGroupMembers localEntitlementGroupMembers = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localApprovalsGroupMembers = (ApprovalsGroupMembers)localHttpSession.getAttribute(this.aNX);
    if (localSecureUser == null)
    {
      this.error = 38;
      str = getTaskErrorURL();
    }
    else if (localApprovalsGroupMembers == null)
    {
      this.error = 31009;
      str = getTaskErrorURL();
    }
    else
    {
      localEntitlementGroupMembers = new EntitlementGroupMembers();
      Iterator localIterator = localApprovalsGroupMembers.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsGroupMember localApprovalsGroupMember = (ApprovalsGroupMember)localIterator.next();
        EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
        localEntitlementGroupMember.setId("" + localApprovalsGroupMember.getUserID());
        localEntitlementGroupMember.setMemberType("USER");
        localEntitlementGroupMember.setMemberSubType("1");
        try
        {
          localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = getServiceErrorURL();
          break;
        }
        localEntitlementGroupMembers.add(localEntitlementGroupMember);
      }
      if (this.error == 0) {
        localHttpSession.setAttribute(this.aNW, localEntitlementGroupMembers);
      }
    }
    return str;
  }
  
  public void setApprovalsGroupMembersName(String paramString)
  {
    this.aNX = ((paramString == null) || (paramString.length() == 0) ? "ApprovalsGroupMembers" : paramString);
  }
  
  public void setEntitlementGroupMembersName(String paramString)
  {
    this.aNW = ((paramString == null) || (paramString.length() == 0) ? "Entitlement_Group_Members" : paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.ConvertApprovalsGroupMembersToEntitlementGroupMembers
 * JD-Core Version:    0.7.0.1
 */