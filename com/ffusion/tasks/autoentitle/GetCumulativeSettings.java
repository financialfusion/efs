package com.ffusion.tasks.autoentitle;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.autoentitle.AutoEntitle;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCumulativeSettings
  extends BaseTask
  implements AutoEntitleTask
{
  protected String _entitlementGroupSessionKey;
  protected String _entitlementGroupMemberSessionKey;
  protected AutoEntitle _autoEntitle;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 19506;
      return this.taskErrorURL;
    }
    Object localObject;
    if ((this._entitlementGroupSessionKey != null) && (!this._entitlementGroupSessionKey.equals("")))
    {
      localObject = (EntitlementGroup)localHttpSession.getAttribute(this._entitlementGroupSessionKey);
      if (localObject == null)
      {
        this.error = 19503;
        return this.taskErrorURL;
      }
      try
      {
        this._autoEntitle = AutoEntitleAdmin.getCumulativeSettings(localSecureUser, (EntitlementGroup)localObject, new HashMap());
      }
      catch (CSILException localCSILException1)
      {
        this.error = 19500;
        return this.taskErrorURL;
      }
    }
    if ((this._entitlementGroupMemberSessionKey != null) && (!this._entitlementGroupMemberSessionKey.equals("")))
    {
      localObject = (EntitlementGroupMember)localHttpSession.getAttribute(this._entitlementGroupMemberSessionKey);
      if (localObject == null)
      {
        this.error = 19504;
        return this.taskErrorURL;
      }
      try
      {
        this._autoEntitle = AutoEntitleAdmin.getCumulativeSettings(localSecureUser, (EntitlementGroupMember)localObject, new HashMap());
      }
      catch (CSILException localCSILException2)
      {
        this.error = 19500;
        return this.taskErrorURL;
      }
    }
    return this.successURL;
  }
  
  public void setEntitlementGroupSessionKey(String paramString)
  {
    this._entitlementGroupSessionKey = paramString;
  }
  
  public void setEntitlementGroupMemberSessionKey(String paramString)
  {
    this._entitlementGroupMemberSessionKey = paramString;
  }
  
  public String getEnableAccounts()
  {
    return String.valueOf(AutoEntitleUtil.isPermissionEnabled(this._autoEntitle, 1));
  }
  
  public String getEnableAccountGroups()
  {
    return String.valueOf(AutoEntitleUtil.isPermissionEnabled(this._autoEntitle, 6));
  }
  
  public String getEnableACHCompanies()
  {
    return String.valueOf(AutoEntitleUtil.isPermissionEnabled(this._autoEntitle, 2));
  }
  
  public String getEnableLocations()
  {
    return String.valueOf(AutoEntitleUtil.isPermissionEnabled(this._autoEntitle, 4));
  }
  
  public String getEnablePermissions()
  {
    return String.valueOf(AutoEntitleUtil.isPermissionEnabled(this._autoEntitle, 5));
  }
  
  public String getEnableWireTemplates()
  {
    return String.valueOf(AutoEntitleUtil.isPermissionEnabled(this._autoEntitle, 3));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.autoentitle.GetCumulativeSettings
 * JD-Core Version:    0.7.0.1
 */