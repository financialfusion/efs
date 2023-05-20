package com.ffusion.tasks.autoentitle;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.autoentitle.AutoEntitle;
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

public class ModifyAutoEntitleSettings
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
  protected boolean _initFlag = true;
  protected boolean _processFlag = false;
  
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
  
  public void setEnableAccounts(String paramString)
  {
    this._autoEntitle.setEnableAccounts(Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setEnableAccountGroups(String paramString)
  {
    this._autoEntitle.setEnableAccountGroups(Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setEnableACHCompanies(String paramString)
  {
    this._autoEntitle.setEnableACHCompanies(Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setEnableLocations(String paramString)
  {
    this._autoEntitle.setEnableLocations(Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setEnablePermissions(String paramString)
  {
    this._autoEntitle.setEnablePermissions(Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setEnableWireTemplates(String paramString)
  {
    this._autoEntitle.setEnableWireTemplates(Boolean.valueOf(paramString).booleanValue());
  }
  
  public String getEnableAccounts()
  {
    return String.valueOf(this._autoEntitle.getEnableAccounts());
  }
  
  public String getEnableAccountGroups()
  {
    return String.valueOf(this._autoEntitle.getEnableAccountGroups());
  }
  
  public String getEnableACHCompanies()
  {
    return String.valueOf(this._autoEntitle.getEnableACHCompanies());
  }
  
  public String getEnableLocations()
  {
    return String.valueOf(this._autoEntitle.getEnableLocations());
  }
  
  public String getEnablePermissions()
  {
    return String.valueOf(this._autoEntitle.getEnablePermissions());
  }
  
  public String getEnableWireTemplates()
  {
    return String.valueOf(this._autoEntitle.getEnableWireTemplates());
  }
  
  public String getAnyPermissionEnabled()
  {
    return String.valueOf(AutoEntitleUtil.isAnyPermissionEnabled(this._autoEntitle));
  }
  
  public String getParentEnableAccounts()
  {
    return String.valueOf(this._parentAutoEntitle.getEnableAccounts());
  }
  
  public String getParentEnableAccountGroups()
  {
    return String.valueOf(this._parentAutoEntitle.getEnableAccountGroups());
  }
  
  public String getParentEnableACHCompanies()
  {
    return String.valueOf(this._parentAutoEntitle.getEnableACHCompanies());
  }
  
  public String getParentEnableLocations()
  {
    return String.valueOf(this._parentAutoEntitle.getEnableLocations());
  }
  
  public String getParentEnablePermissions()
  {
    return String.valueOf(this._parentAutoEntitle.getEnablePermissions());
  }
  
  public String getParentEnableWireTemplates()
  {
    return String.valueOf(this._parentAutoEntitle.getEnableWireTemplates());
  }
  
  public String getParentAnyPermissionEnabled()
  {
    return String.valueOf(AutoEntitleUtil.isAnyPermissionEnabled(this._parentAutoEntitle));
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this._initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else {
      str = modifyAutoEntitleSettings(localHttpSession);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("AutoEntitleUpdated", "true");
    }
    return str;
  }
  
  public String process(HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    if (this._initFlag) {
      str = initProcess(paramHttpServletRequest, paramHttpSession);
    } else {
      str = modifyAutoEntitleSettings(paramHttpSession);
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute("AutoEntitleUpdated", "true");
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    if (localSecureUser == null)
    {
      this.error = 19506;
      return this.taskErrorURL;
    }
    AffiliateBank localAffiliateBank = null;
    if (this._affiliateBankSessionKey != null)
    {
      localAffiliateBank = (AffiliateBank)paramHttpSession.getAttribute(this._affiliateBankSessionKey);
      if (localAffiliateBank == null)
      {
        this.error = 19505;
        return this.taskErrorURL;
      }
      try
      {
        this._originalAutoEntitle = AutoEntitleAdmin.getSettings(localSecureUser, localAffiliateBank, localHashMap);
        if (this._originalAutoEntitle != null) {
          this._autoEntitle = ((AutoEntitle)this._originalAutoEntitle.clone());
        }
      }
      catch (Exception localException1)
      {
        this.error = 19500;
        return this.taskErrorURL;
      }
    }
    EntitlementGroup localEntitlementGroup = null;
    if (this._entitlementGroupSessionKey != null)
    {
      localEntitlementGroup = (EntitlementGroup)paramHttpSession.getAttribute(this._entitlementGroupSessionKey);
      if (localEntitlementGroup == null)
      {
        this.error = 19503;
        return this.taskErrorURL;
      }
      try
      {
        this._originalAutoEntitle = AutoEntitleAdmin.getCumulativeSettings(localSecureUser, localEntitlementGroup, localHashMap);
        if (this._originalAutoEntitle != null) {
          this._autoEntitle = ((AutoEntitle)this._originalAutoEntitle.clone());
        }
        this._parentAutoEntitle = AutoEntitleAdmin.getCumulativeParentSettings(localSecureUser, this._autoEntitle, localHashMap);
      }
      catch (Exception localException2)
      {
        this.error = 19500;
        return this.taskErrorURL;
      }
    }
    EntitlementGroupMember localEntitlementGroupMember = null;
    if (this._entitlementGroupMemberSessionKey != null)
    {
      localEntitlementGroupMember = (EntitlementGroupMember)paramHttpSession.getAttribute(this._entitlementGroupMemberSessionKey);
      if (localEntitlementGroupMember == null)
      {
        this.error = 19504;
        return this.taskErrorURL;
      }
      try
      {
        this._originalAutoEntitle = AutoEntitleAdmin.getCumulativeSettings(localSecureUser, localEntitlementGroupMember, localHashMap);
        if (this._originalAutoEntitle != null) {
          this._autoEntitle = ((AutoEntitle)this._originalAutoEntitle.clone());
        }
        this._parentAutoEntitle = AutoEntitleAdmin.getCumulativeParentSettings(localSecureUser, this._autoEntitle, localHashMap);
      }
      catch (Exception localException3)
      {
        this.error = 19500;
        return this.taskErrorURL;
      }
    }
    this._initFlag = false;
    return this.successURL;
  }
  
  protected String modifyAutoEntitleSettings(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    if (localSecureUser == null)
    {
      this.error = 19506;
      return this.taskErrorURL;
    }
    String str = null;
    if (this._processFlag) {
      this._processFlag = false;
    } else {
      try
      {
        AutoEntitleAdmin.setSettings(localSecureUser, this._autoEntitle, this._originalAutoEntitle, localHashMap);
        str = this.successURL;
      }
      catch (Exception localException)
      {
        this.error = 19507;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void reset()
  {
    this._autoEntitle.set(this._originalAutoEntitle);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.autoentitle.ModifyAutoEntitleSettings
 * JD-Core Version:    0.7.0.1
 */