package com.ffusion.tasks.autoentitle;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.autoentitle.AutoEntitle;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteAutoEntitleSettings
  extends BaseTask
  implements AutoEntitleTask
{
  protected String _affiliateBankSessionKey;
  protected String _entitlementGroupSessionKey;
  protected String _entitlementGroupMemberSessionKey;
  protected AutoEntitle _originalAutoEntitle;
  protected AutoEntitle _autoEntitle;
  protected AutoEntitle _parentAutoEntitle;
  protected SecureUser _secureUser;
  
  public void setAffiliateBankSessionKey(String paramString)
  {
    this._affiliateBankSessionKey = paramString;
  }
  
  public void setEntitlementGroupSessionKey(String paramString)
  {
    this._entitlementGroupSessionKey = paramString;
  }
  
  public void setEntitlementGroupMemberSessionKey(String paramString)
  {
    this._entitlementGroupMemberSessionKey = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    if (localSecureUser == null)
    {
      this.error = 19506;
      return this.taskErrorURL;
    }
    AffiliateBank localAffiliateBank = null;
    if (this._affiliateBankSessionKey != null)
    {
      localAffiliateBank = (AffiliateBank)localHttpSession.getAttribute(this._affiliateBankSessionKey);
      if (localAffiliateBank == null)
      {
        this.error = 19505;
        return this.taskErrorURL;
      }
      try
      {
        AutoEntitleAdmin.deleteSettings(localSecureUser, localAffiliateBank, localHashMap);
      }
      catch (Exception localException1)
      {
        this.error = 19501;
        return this.taskErrorURL;
      }
    }
    EntitlementGroup localEntitlementGroup = null;
    if (this._entitlementGroupSessionKey != null)
    {
      localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute(this._entitlementGroupSessionKey);
      if (localEntitlementGroup == null)
      {
        this.error = 19503;
        return this.taskErrorURL;
      }
      try
      {
        AutoEntitleAdmin.deleteSettings(localSecureUser, localEntitlementGroup, localHashMap);
      }
      catch (Exception localException2)
      {
        this.error = 19501;
        return this.taskErrorURL;
      }
    }
    EntitlementGroupMember localEntitlementGroupMember = null;
    if (this._entitlementGroupMemberSessionKey != null)
    {
      localEntitlementGroupMember = (EntitlementGroupMember)localHttpSession.getAttribute(this._entitlementGroupMemberSessionKey);
      if (localEntitlementGroupMember == null)
      {
        this.error = 19504;
        return this.taskErrorURL;
      }
      try
      {
        AutoEntitleAdmin.deleteSettings(localSecureUser, localEntitlementGroupMember, localHashMap);
      }
      catch (Exception localException3)
      {
        this.error = 19501;
        return this.taskErrorURL;
      }
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.autoentitle.DeleteAutoEntitleSettings
 * JD-Core Version:    0.7.0.1
 */