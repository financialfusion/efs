package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HandleAccountGroupAccessRowDisplay
  extends BaseTask
  implements AdminTask
{
  protected String _accountGroupsName;
  protected int _groupId;
  protected EntitlementGroupMember _member;
  protected String _memberId;
  protected String _memberType;
  protected String _memberSubType;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessAccountGroups localBusinessAccountGroups = (BusinessAccountGroups)localHttpSession.getAttribute(this._accountGroupsName);
    if (localBusinessAccountGroups == null)
    {
      this.error = 98;
      return super.getTaskErrorURL();
    }
    if (this._groupId == 0)
    {
      this.error = 4524;
      return this.taskErrorURL;
    }
    if (this._memberType != null)
    {
      this._member = new EntitlementGroupMember();
      this._member.setMemberType(this._memberType);
      this._member.setMemberSubType(this._memberSubType);
      this._member.setId(this._memberId);
      this._member.setEntitlementGroupId(this._groupId);
    }
    try
    {
      int i;
      if (this._member == null) {
        i = Entitlements.getEntitlementGroup(this._groupId).getParentId();
      } else {
        i = this._groupId;
      }
      boolean bool1 = true;
      if (this._member != null) {
        bool1 = Entitlements.canAdministerAnyGroup(this._member);
      }
      Iterator localIterator = localBusinessAccountGroups.iterator();
      while (localIterator.hasNext())
      {
        BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)localIterator.next();
        Entitlement localEntitlement1 = new Entitlement("Access", "AccountGroup", EntitlementsUtil.getEntitlementObjectId(localBusinessAccountGroup));
        Entitlement localEntitlement2 = new Entitlement("Access (admin)", "AccountGroup", EntitlementsUtil.getEntitlementObjectId(localBusinessAccountGroup));
        if ((localEntitlement2 != null) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlement2)))
        {
          localBusinessAccountGroup.set("DisplayRow", "no");
          localBusinessAccountGroup.set("CanAdminRow", new Boolean(false).toString().toUpperCase());
          localBusinessAccountGroup.set("CanInitRow", new Boolean(false).toString().toUpperCase());
        }
        else
        {
          boolean bool2 = Entitlements.checkEntitlement(i, localEntitlement1);
          boolean bool3 = bool1;
          if ((bool3) && (localEntitlement2 != null)) {
            bool3 = Entitlements.checkEntitlement(i, localEntitlement2);
          }
          if ((bool2) || (bool3))
          {
            localBusinessAccountGroup.set("DisplayRow", "yes");
            localBusinessAccountGroup.set("CanAdminRow", new Boolean(bool3).toString().toUpperCase());
            localBusinessAccountGroup.set("CanInitRow", new Boolean(bool2).toString().toUpperCase());
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this._accountGroupsName, localBusinessAccountGroups);
    return str;
  }
  
  public void setAccountGroupsName(String paramString)
  {
    this._accountGroupsName = paramString;
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
  
  public void setGroupId(String paramString)
  {
    this._groupId = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.HandleAccountGroupAccessRowDisplay
 * JD-Core Version:    0.7.0.1
 */