package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.autoentitle.AutoEntitle;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import java.util.HashMap;

public abstract class AutoEntitleBaseTask
  extends BaseTask
{
  protected boolean _initAutoEntitle = false;
  protected boolean _autoEntitle = true;
  protected EntitlementGroup _parentGroup = null;
  protected EntitlementGroup _entGroup = null;
  protected EntitlementGroup _oldGroup = null;
  protected EntitlementGroup _empGroup = null;
  protected EntitlementGroup _newEmpGroup = null;
  protected EntitlementGroupMember _empGroupMember = null;
  protected EntitlementGroupMember _oldGroupMember = null;
  protected AutoEntitle _autoEntitleSettings;
  
  protected void initialize(SecureUser paramSecureUser)
    throws CSILException
  {
    if (this._entGroup != null)
    {
      this._parentGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this._entGroup.getParentId());
      this._autoEntitleSettings = AutoEntitleAdmin.getCumulativeSettings(paramSecureUser, this._parentGroup, new HashMap());
    }
    else if (this._empGroup != null)
    {
      this._autoEntitleSettings = AutoEntitleAdmin.getCumulativeSettings(paramSecureUser, this._empGroup, new HashMap());
    }
    else
    {
      this._newEmpGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this._empGroupMember.getEntitlementGroupId());
      this._autoEntitleSettings = AutoEntitleAdmin.getCumulativeSettings(paramSecureUser, this._newEmpGroup, new HashMap());
    }
    this._autoEntitle = AutoEntitleUtil.isAnyPermissionEnabled(this._autoEntitleSettings);
  }
  
  protected com.ffusion.csil.beans.entitlements.Entitlements buildRestrictedEntitlementsList(SecureUser paramSecureUser)
    throws CSILException
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("AUTOENTITLE_FLAG_KEY", new Boolean(this._autoEntitle));
    localHashMap.put("AUTOENTITLE_SETTINGS_KEY", this._autoEntitleSettings);
    EntitlementGroup localEntitlementGroup = null;
    String str;
    if (this._parentGroup != null)
    {
      localEntitlementGroup = this._parentGroup;
      if (this._entGroup.getEntGroupType().equals("Division")) {
        localHashMap.put("PROCESS_LOCATIONS_KEY", new Boolean(false));
      }
      str = EntitlementsUtil.getTypePropertyCategoryValue(this._entGroup.getEntGroupType());
    }
    else if (this._empGroup != null)
    {
      localEntitlementGroup = this._empGroup;
      str = "per user";
    }
    else
    {
      localEntitlementGroup = this._newEmpGroup;
      str = "per user";
    }
    return AutoEntitleUtil.buildRestrictedEntitlementsList(paramSecureUser, localEntitlementGroup, str, localHashMap);
  }
  
  public void setInitAutoEntitle(String paramString)
  {
    this._initAutoEntitle = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getInitAutoEntitle()
  {
    return new Boolean(this._autoEntitle).toString();
  }
  
  public void setAutoEntitle(String paramString)
  {
    this._autoEntitle = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitle()
  {
    return new Boolean(this._autoEntitle).toString();
  }
  
  protected void setAutoEntitleSettings(AutoEntitle paramAutoEntitle)
  {
    this._autoEntitleSettings = paramAutoEntitle;
  }
  
  protected AutoEntitle getAutoEntitleSettings()
  {
    return this._autoEntitleSettings;
  }
  
  public String getParentChanged()
  {
    boolean bool = false;
    if ((this._oldGroup != null) && (this._oldGroup.getGroupId() != this._entGroup.getGroupId())) {
      bool = true;
    } else if ((this._oldGroupMember != null) && (this._oldGroupMember.getEntitlementGroupId() != this._empGroupMember.getEntitlementGroupId())) {
      bool = true;
    }
    return new Boolean(bool).toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.AutoEntitleBaseTask
 * JD-Core Version:    0.7.0.1
 */