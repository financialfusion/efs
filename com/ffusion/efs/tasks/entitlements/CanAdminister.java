package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CanAdminister
  extends BaseTask
  implements Task
{
  private int Kv;
  protected String _memberId;
  protected String _memberType;
  protected String _memberSubType;
  private int Kw;
  private String Kx;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.Kw == 0)
    {
      this.error = 35017;
      return this.taskErrorURL;
    }
    EntitlementAdmin localEntitlementAdmin;
    if (this.Kx != null)
    {
      try
      {
        EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localHttpSession.getAttribute(this.Kx);
        if (localEntitlementGroupMember == null)
        {
          this.error = 35026;
          return this.taskErrorURL;
        }
        localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember, this.Kw);
        boolean bool2 = Entitlements.canAdminister(localEntitlementAdmin);
        localHttpSession.setAttribute("Entitlement_CanAdminister", new Boolean(bool2).toString().toUpperCase());
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
      }
    }
    else
    {
      if (this._memberType == null)
      {
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        localEntitlementAdmin = new EntitlementAdmin(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), this.Kw);
      }
      else
      {
        localEntitlementAdmin = new EntitlementAdmin(this.Kv, this.Kw);
        localEntitlementAdmin.setGranteeMemberType(this._memberType);
        localEntitlementAdmin.setGranteeMemberSubType(this._memberSubType);
        localEntitlementAdmin.setGranteeMemberId(this._memberId);
      }
      try
      {
        boolean bool1 = Entitlements.canAdminister(localEntitlementAdmin);
        localHttpSession.setAttribute("Entitlement_CanAdminister", new Boolean(bool1).toString().toUpperCase());
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
      }
    }
    if (this.error == 0) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setGroupId(String paramString)
  {
    this.Kv = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.Kv);
  }
  
  public void setCanAdminGroupId(String paramString)
  {
    this.Kw = Integer.parseInt(paramString);
  }
  
  public String getCanAdminGroupId()
  {
    return Integer.toString(this.Kw);
  }
  
  public void setMemberId(String paramString)
  {
    this._memberId = paramString;
  }
  
  public String getMemberId()
  {
    return this._memberId;
  }
  
  public void setMemberType(String paramString)
  {
    this._memberType = paramString;
  }
  
  public String getMemberType()
  {
    return this._memberType;
  }
  
  public void setMemberSubType(String paramString)
  {
    this._memberSubType = paramString;
  }
  
  public String getMemberSubType()
  {
    return this._memberSubType;
  }
  
  public void setMemberSessionName(String paramString)
  {
    this.Kx = paramString;
  }
  
  public String getMemberSessionName()
  {
    return this.Kx;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CanAdminister
 * JD-Core Version:    0.7.0.1
 */