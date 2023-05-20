package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HandleAccountRowDisplay
  extends BaseTask
  implements AdminTask
{
  protected String _listName;
  protected int _groupId;
  protected String _objectType;
  protected String _objectId;
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
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this._listName);
    if (localEntitlementTypePropertyLists == null)
    {
      this.error = 4549;
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
        i = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this._groupId).getParentId();
      } else {
        i = this._groupId;
      }
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = new com.ffusion.csil.beans.entitlements.Entitlements();
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements3 = new com.ffusion.csil.beans.entitlements.Entitlements();
      int j = 1;
      if ((this._objectType != null) && (!this._objectType.equals("Wire Template")))
      {
        j = 0;
        Entitlement localEntitlement1 = new Entitlement("Access (admin)", this._objectType, this._objectId);
        if (com.ffusion.csil.core.Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlement1)) {
          j = 1;
        }
      }
      boolean bool1 = true;
      if ((this._objectType != null) && (!this._objectType.equals("Wire Template")))
      {
        bool1 = false;
        Entitlement localEntitlement2 = new Entitlement("Access (admin)", this._objectType, this._objectId);
        if (this._member != null)
        {
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(this._member, localEntitlement2)) {
            bool1 = true;
          }
        }
        else if (com.ffusion.csil.core.Entitlements.checkEntitlement(this._groupId, localEntitlement2)) {
          bool1 = true;
        }
      }
      if ((bool1) && (this._member != null)) {
        bool1 = com.ffusion.csil.core.Entitlements.canAdministerAnyGroup(this._member);
      }
      boolean bool2 = true;
      if ((this._objectType != null) && (!this._objectType.equals("Wire Template")))
      {
        bool2 = false;
        localObject = new Entitlement("Access", this._objectType, this._objectId);
        if (this._member != null)
        {
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(this._member, (Entitlement)localObject)) {
            bool2 = true;
          }
        }
        else if (com.ffusion.csil.core.Entitlements.checkEntitlement(this._groupId, (Entitlement)localObject)) {
          bool2 = true;
        }
      }
      Object localObject = localEntitlementTypePropertyLists.iterator();
      while (((Iterator)localObject).hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject).next();
        localEntitlementTypePropertyList.clearProperty("DisplayRow");
        localEntitlementTypePropertyList.clearProperty("CanAdminRow");
        localEntitlementTypePropertyList.clearProperty("CanInitRow");
        if (j == 0)
        {
          localEntitlementTypePropertyList.addProperty("CanAdminRow", new Boolean(false).toString().toUpperCase());
          localEntitlementTypePropertyList.addProperty("CanInitRow", new Boolean(false).toString().toUpperCase());
          localEntitlementTypePropertyList.addProperty("DisplayRow", "no");
        }
        else
        {
          Entitlement localEntitlement3 = new Entitlement(localEntitlementTypePropertyList.getOperationName(), this._objectType, this._objectId);
          Entitlement localEntitlement4 = null;
          if (localEntitlementTypePropertyList.isPropertySet("admin partner")) {
            localEntitlement4 = new Entitlement(localEntitlementTypePropertyList.getPropertyValue("admin partner", 0), this._objectType, this._objectId);
          }
          if ((localEntitlement4 != null) && (!jdMethod_for(-1, EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlements1, localEntitlementTypePropertyLists, localEntitlement4, localEntitlementTypePropertyList)))
          {
            localEntitlementTypePropertyList.addProperty("CanAdminRow", new Boolean(false).toString().toUpperCase());
            localEntitlementTypePropertyList.addProperty("CanInitRow", new Boolean(false).toString().toUpperCase());
            localEntitlementTypePropertyList.addProperty("DisplayRow", "no");
          }
          else
          {
            boolean bool3 = bool2;
            if (bool3) {
              bool3 = jdMethod_for(i, null, localEntitlements3, localEntitlementTypePropertyLists, localEntitlement3, localEntitlementTypePropertyList);
            }
            boolean bool4 = (bool1) && (localEntitlement4 != null);
            if ((bool4) && (localEntitlement4 != null)) {
              bool4 = jdMethod_for(i, null, localEntitlements3, localEntitlementTypePropertyLists, localEntitlement4, localEntitlementTypePropertyList);
            }
            if (this._objectType != null)
            {
              if (bool3)
              {
                localEntitlement3.setObjectType(null);
                localEntitlement3.setObjectId(null);
                if (this._member == null) {
                  bool3 = jdMethod_for(this._groupId, null, localEntitlements2, localEntitlementTypePropertyLists, localEntitlement3, localEntitlementTypePropertyList);
                } else {
                  bool3 = jdMethod_for(-1, this._member, localEntitlements2, localEntitlementTypePropertyLists, localEntitlement3, localEntitlementTypePropertyList);
                }
              }
              if (bool4)
              {
                localEntitlement4.setObjectType(null);
                localEntitlement4.setObjectId(null);
                if (this._member == null) {
                  bool4 = jdMethod_for(this._groupId, null, localEntitlements2, localEntitlementTypePropertyLists, localEntitlement4, localEntitlementTypePropertyList);
                } else {
                  bool4 = jdMethod_for(-1, this._member, localEntitlements2, localEntitlementTypePropertyLists, localEntitlement4, localEntitlementTypePropertyList);
                }
              }
            }
            localEntitlementTypePropertyList.addProperty("CanAdminRow", new Boolean(bool4).toString().toUpperCase());
            localEntitlementTypePropertyList.addProperty("CanInitRow", new Boolean(bool3).toString().toUpperCase());
            if ((bool3) || (bool4))
            {
              localEntitlementTypePropertyList.addProperty("DisplayRow", "yes");
              jdMethod_for(localEntitlementTypePropertyLists, localEntitlementTypePropertyList);
            }
            else
            {
              localEntitlementTypePropertyList.addProperty("DisplayRow", "no");
            }
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this._listName, localEntitlementTypePropertyLists);
    return str;
  }
  
  private boolean jdMethod_for(int paramInt, EntitlementGroupMember paramEntitlementGroupMember, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, Entitlement paramEntitlement, EntitlementTypePropertyList paramEntitlementTypePropertyList)
    throws CSILException
  {
    if ((paramEntitlement == null) || (paramEntitlementTypePropertyList == null)) {
      return false;
    }
    if ((paramInt < 0) && (paramEntitlementGroupMember == null)) {
      return false;
    }
    if ((!paramEntitlement.getOperationName().equals(paramEntitlementTypePropertyList.getOperationName())) && (!paramEntitlement.getOperationName().equals(paramEntitlementTypePropertyList.getPropertyValue("admin partner", 0)))) {
      return false;
    }
    if (paramEntitlements.contains(paramEntitlement)) {
      return true;
    }
    if (paramEntitlementGroupMember == null)
    {
      if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramInt, paramEntitlement)) {
        return false;
      }
    }
    else if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement)) {
      return false;
    }
    int i = paramEntitlementTypePropertyList.numPropertyValues("control parent");
    if (i == 0)
    {
      paramEntitlements.add(paramEntitlement.clone());
      return true;
    }
    int j = 0;
    if (paramEntitlement.getOperationName().equals(paramEntitlementTypePropertyList.getPropertyValue("admin partner", 0))) {
      j = 1;
    }
    Entitlement localEntitlement = (Entitlement)paramEntitlement.clone();
    for (int k = 0; k < i; k++)
    {
      String str = paramEntitlementTypePropertyList.getPropertyValue("control parent", k);
      EntitlementTypePropertyList localEntitlementTypePropertyList = paramEntitlementTypePropertyLists.getByOperationName(str);
      if (localEntitlementTypePropertyList == null) {
        localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str);
      }
      if (j != 0)
      {
        if (localEntitlementTypePropertyList.isPropertySet("admin partner"))
        {
          localEntitlement.setOperationName(localEntitlementTypePropertyList.getPropertyValue("admin partner", 0));
          if (!jdMethod_for(paramInt, paramEntitlementGroupMember, paramEntitlements, paramEntitlementTypePropertyLists, localEntitlement, localEntitlementTypePropertyList)) {
            return false;
          }
        }
      }
      else
      {
        localEntitlement.setOperationName(str);
        if (!jdMethod_for(paramInt, paramEntitlementGroupMember, paramEntitlements, paramEntitlementTypePropertyLists, localEntitlement, localEntitlementTypePropertyList)) {
          return false;
        }
      }
    }
    paramEntitlements.add(paramEntitlement.clone());
    return true;
  }
  
  private void jdMethod_for(EntitlementTypePropertyLists paramEntitlementTypePropertyLists, EntitlementTypePropertyList paramEntitlementTypePropertyList)
  {
    for (int i = 0; i < paramEntitlementTypePropertyList.numPropertyValues("display parent"); i++)
    {
      String str = paramEntitlementTypePropertyList.getPropertyValue("display parent", i);
      EntitlementTypePropertyList localEntitlementTypePropertyList = paramEntitlementTypePropertyLists.getByOperationName(str);
      if (localEntitlementTypePropertyList != null)
      {
        localEntitlementTypePropertyList.clearProperty("DisplayRow");
        localEntitlementTypePropertyList.addProperty("DisplayRow", "yes");
        jdMethod_for(paramEntitlementTypePropertyLists, localEntitlementTypePropertyList);
      }
    }
  }
  
  public void setEntitlementTypePropertyLists(String paramString)
  {
    this._listName = paramString;
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
  
  public void setObjectType(String paramString)
  {
    this._objectType = paramString;
  }
  
  public String getObjectType()
  {
    return this._objectType;
  }
  
  public void setObjectId(String paramString)
  {
    this._objectId = paramString;
  }
  
  public String getObjectId()
  {
    return this._objectId;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.HandleAccountRowDisplay
 * JD-Core Version:    0.7.0.1
 */