package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
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

public class AddMember
  extends BaseTask
  implements Task
{
  protected String _memberId;
  protected String _memberType;
  protected String _memberSubType;
  protected int _groupId = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this._memberId == null)
    {
      this.error = 35026;
      return this.taskErrorURL;
    }
    if (this._memberType == null)
    {
      this.error = 35024;
      return this.taskErrorURL;
    }
    if (this._memberSubType == null)
    {
      this.error = 35025;
      return this.taskErrorURL;
    }
    if (this._groupId == -1)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(this._memberId);
    localEntitlementGroupMember.setMemberType(this._memberType);
    localEntitlementGroupMember.setMemberSubType(this._memberSubType);
    localEntitlementGroupMember.setEntitlementGroupId(this._groupId);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Entitlements.addMember(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
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
  
  public void setEntitlementGroupId(String paramString)
  {
    this._groupId = Integer.parseInt(paramString);
  }
  
  public String getEntitlementGroupId()
  {
    return Integer.toString(this._groupId);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.AddMember
 * JD-Core Version:    0.7.0.1
 */