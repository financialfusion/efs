package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRestrictedEntitlements
  extends BaseTask
  implements Task
{
  private int an;
  private String al;
  private String am;
  private String ak;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.am != null) && (this.ak != null) && (this.al != null) && (this.an != 0))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.an);
      localEntitlementGroupMember.setMemberType(this.am);
      localEntitlementGroupMember.setMemberSubType(this.ak);
      localEntitlementGroupMember.setId(this.al);
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      if (localEntitlementGroupMember != null) {
        localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember);
      } else if (this.an != 0) {
        localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), this.an);
      } else {
        localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), EntitlementsUtil.getEntitlementGroupMember(localSecureUser).getEntitlementGroupId());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_Restricted_list", localEntitlements);
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setGroupId(String paramString)
  {
    this.an = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.an);
  }
  
  public void setMemberId(String paramString)
  {
    this.al = paramString;
  }
  
  public String getMemberId()
  {
    return this.al;
  }
  
  public void setMemberType(String paramString)
  {
    this.am = paramString;
  }
  
  public String getMemberType()
  {
    return this.am;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.ak = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.ak;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetRestrictedEntitlements
 * JD-Core Version:    0.7.0.1
 */