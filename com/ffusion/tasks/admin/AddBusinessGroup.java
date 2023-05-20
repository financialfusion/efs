package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.autoentitle.AutoEntitle;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.ValidateString;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddBusinessGroup
  extends AutoEntitleBaseTask
  implements AdminTask
{
  public static final String ACCOUNT = "account";
  public static final String KEY_VALUE_DELIM = "||";
  private EntitlementGroup Yg = new EntitlementGroup();
  private boolean Yj = true;
  private String Yk = null;
  private String Yi = null;
  private String Yh = null;
  private boolean Ym = false;
  private static final String Yl = "com.ffusion.util.logging.audit.task";
  private static final String Yn = "AuditMessage_AddBusinessGroup_1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      return super.getServiceErrorURL();
    }
    if (this._initAutoEntitle) {
      try
      {
        this._entGroup = this.Yg;
        initialize(localSecureUser);
        this._initAutoEntitle = false;
        return null;
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        return super.getServiceErrorURL();
      }
    }
    if ((this.Yg.getGroupName() == null) || (this.Yg.getGroupName().length() <= 0))
    {
      this.error = 4540;
      return super.getTaskErrorURL();
    }
    if (this.Yg.getGroupName().length() > 255)
    {
      this.error = 4545;
      return super.getTaskErrorURL();
    }
    if (!ValidateString.validateName(this.Yg.getGroupName()))
    {
      this.error = 4544;
      return super.getTaskErrorURL();
    }
    if (this.Ym)
    {
      this.Ym = false;
      return super.getSuccessURL();
    }
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    if (localEntitlementGroupMember1 == null)
    {
      try
      {
        localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
        localEntitlementGroupMember1 = com.ffusion.csil.core.Entitlements.getMember(localEntitlementGroupMember1);
      }
      catch (CSILException localCSILException3)
      {
        this.error = MapError.mapError(localCSILException3);
        return super.getServiceErrorURL();
      }
      localHttpSession.setAttribute("EntitlementGroupMember", localEntitlementGroupMember1);
    }
    this.Yg.setSvcBureauId(localSecureUser.getBankID());
    Object localObject4;
    Object localObject5;
    Object localObject6;
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
      EntitlementGroups localEntitlementGroups = com.ffusion.csil.core.Entitlements.getChildrenEntitlementGroups(this.Yg.getParentId());
      for (int i = 0; i < localEntitlementGroups.size(); i++)
      {
        localObject3 = (EntitlementGroup)localEntitlementGroups.get(i);
        if (((EntitlementGroup)localObject3).getGroupName().equalsIgnoreCase(this.Yg.getGroupName()))
        {
          this.error = 4542;
          return getTaskErrorURL();
        }
      }
      this.Yg = com.ffusion.csil.core.Entitlements.addEntitlementGroup(localEntitlementGroupMember1, this.Yg);
      localObject2 = new ArrayList();
      localObject3 = new EntitlementAdmins();
      localObject4 = (ArrayList)localHttpSession.getAttribute(this.Yh);
      if ((localObject4 != null) && (((ArrayList)localObject4).size() != 0)) {
        for (int k = 0; k < ((ArrayList)localObject4).size(); k++) {
          if ((((ArrayList)localObject4).get(k) instanceof EntitlementGroupMember))
          {
            localObject5 = (EntitlementGroupMember)((ArrayList)localObject4).get(k);
            localObject6 = new EntitlementAdmin();
            ((EntitlementAdmin)localObject6).setGranteeGroupId(((EntitlementGroupMember)localObject5).getEntitlementGroupId());
            ((EntitlementAdmin)localObject6).setGranteeMemberType(((EntitlementGroupMember)localObject5).getMemberType());
            ((EntitlementAdmin)localObject6).setGranteeMemberSubType(((EntitlementGroupMember)localObject5).getMemberSubType());
            ((EntitlementAdmin)localObject6).setGranteeMemberId(((EntitlementGroupMember)localObject5).getId());
            ((EntitlementAdmin)localObject6).setTargetGroupId(this.Yg.getGroupId());
            ((EntitlementAdmin)localObject6).setAdminister(true);
            ((EntitlementAdmin)localObject6).setExtend(true);
            ((EntitlementAdmins)localObject3).add(localObject6);
            if ((!((ArrayList)localObject2).contains(localObject5)) && (!com.ffusion.csil.core.Entitlements.canAdministerAnyGroup((EntitlementGroupMember)localObject5))) {
              ((ArrayList)localObject2).add(localObject5);
            }
          }
          else
          {
            localObject5 = (String)((ArrayList)localObject4).get(k);
            localObject6 = new EntitlementAdmin();
            ((EntitlementAdmin)localObject6).setGranteeGroupId(Integer.parseInt((String)localObject5));
            ((EntitlementAdmin)localObject6).setGranteeMemberType(null);
            ((EntitlementAdmin)localObject6).setGranteeMemberSubType(null);
            ((EntitlementAdmin)localObject6).setGranteeMemberId(null);
            ((EntitlementAdmin)localObject6).setTargetGroupId(this.Yg.getGroupId());
            ((EntitlementAdmin)localObject6).setAdminister(true);
            ((EntitlementAdmin)localObject6).setExtend(true);
            ((EntitlementAdmins)localObject3).add(localObject6);
            EntitlementGroupMembers localEntitlementGroupMembers = com.ffusion.csil.core.Entitlements.getMembers(Integer.parseInt((String)localObject5));
            if (localEntitlementGroupMembers != null)
            {
              Iterator localIterator1 = localEntitlementGroupMembers.iterator();
              while (localIterator1.hasNext())
              {
                localObject7 = (EntitlementGroupMember)localIterator1.next();
                if ((!((ArrayList)localObject2).contains(localObject7)) && (!com.ffusion.csil.core.Entitlements.canAdministerAnyGroup((EntitlementGroupMember)localObject7))) {
                  ((ArrayList)localObject2).add(localObject7);
                }
              }
            }
          }
        }
      }
      EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember1.getEntitlementGroupId(), this.Yg.getGroupId());
      localObject5 = new EntitlementAdmin(localEntitlementGroupMember1.getEntitlementGroupId(), this.Yg.getGroupId());
      ((EntitlementAdmin)localObject5).setAdminister(true);
      ((EntitlementAdmin)localObject5).setExtend(true);
      com.ffusion.csil.core.Entitlements.modifyAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin, (EntitlementAdmin)localObject5);
      localObject6 = buildRestrictedEntitlementsList(localSecureUser);
      AutoEntitleAdmin.restrictFeatures(localSecureUser, this.Yg, (com.ffusion.csil.beans.entitlements.Entitlements)localObject6, new HashMap());
      int n = 0;
      int i1 = 0;
      if ("Division".equals(this.Yg.getEntGroupType()))
      {
        i1 = 6;
        n = 1;
      }
      else if ("Group".equals(this.Yg.getEntGroupType()))
      {
        i1 = 7;
        n = 1;
      }
      if ((localObject4 == null) || (((ArrayList)localObject4).size() == 0))
      {
        this.error = 4547;
        return super.getTaskErrorURL();
      }
      com.ffusion.csil.core.Entitlements.setAdministratorGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), this.Yg.getGroupId(), (EntitlementAdmins)localObject3);
      Object localObject7 = new HashMap();
      String str = (String)localHttpSession.getAttribute("AutoEntitleAdministrators");
      Boolean localBoolean = str != null ? Boolean.valueOf(str) : new Boolean("false");
      ((HashMap)localObject7).put("AUTOENTITLE_FLAG_KEY", localBoolean);
      Iterator localIterator2 = ((ArrayList)localObject2).iterator();
      while (localIterator2.hasNext())
      {
        EntitlementGroupMember localEntitlementGroupMember2 = (EntitlementGroupMember)localIterator2.next();
        AutoEntitle localAutoEntitle = AutoEntitleAdmin.getCumulativeSettings(localSecureUser, localEntitlementGroupMember2, (HashMap)localObject7);
        ((HashMap)localObject7).put("AUTOENTITLE_SETTINGS_KEY", localAutoEntitle);
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = AutoEntitleUtil.buildRestrictedAdminEntitlementsList(localSecureUser, localEntitlementGroupMember2, (HashMap)localObject7);
        AutoEntitleAdmin.restrictFeatures(localSecureUser, localEntitlementGroupMember2, localEntitlements2, (HashMap)localObject7);
      }
      if (n != 0)
      {
        localObject7 = new HistoryTracker(localSecureUser, i1, Integer.toString(this.Yg.getGroupId()));
        jdMethod_int((HistoryTracker)localObject7, this.Yg, ((HistoryTracker)localObject7).buildLocalizableComment(1));
        try
        {
          HistoryAdapter.addHistory(((HistoryTracker)localObject7).getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for AddBusinessGroup: " + localProfileException.toString());
        }
      }
    }
    catch (CSILException localCSILException4)
    {
      this.error = MapError.mapError(localCSILException4);
      return super.getServiceErrorURL();
    }
    if (this.Yj)
    {
      localObject1 = new HashSet();
      localObject2 = (Accounts)localHttpSession.getAttribute("Accounts");
      if (localObject2 == null)
      {
        this.error = 39;
        return super.getTaskErrorURL();
      }
      localObject3 = null;
      localObject4 = ((Accounts)localObject2).listIterator();
      while (((ListIterator)localObject4).hasNext())
      {
        localObject3 = EntitlementsUtil.getEntitlementObjectId((Account)((ListIterator)localObject4).next());
        ((HashSet)localObject1).add(localObject3);
      }
      int j = ((HashSet)localObject1).size();
      for (int m = 0; m < j; m++)
      {
        localObject3 = paramHttpServletRequest.getParameter("account" + m);
        if (localObject3 != null) {
          ((HashSet)localObject1).remove(localObject3);
        }
      }
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = new com.ffusion.csil.beans.entitlements.Entitlements();
      localObject5 = null;
      localObject6 = ((HashSet)localObject1).iterator();
      while (((Iterator)localObject6).hasNext())
      {
        localObject3 = (String)((Iterator)localObject6).next();
        localObject5 = new Entitlement(null, "Account", (String)localObject3);
        localEntitlements1.add(localObject5);
      }
      try
      {
        com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, this.Yg.getGroupId(), localEntitlements1);
      }
      catch (CSILException localCSILException5)
      {
        this.error = MapError.mapError(localCSILException5);
        return super.getServiceErrorURL();
      }
    }
    this.Yg.getProperties().setCurrentProperty("ACHCompanyID");
    this.Yg.getProperties().setValueOfCurrentProperty("");
    this.Yg.getProperties().setCurrentProperty("ACHCompanyName");
    this.Yg.getProperties().setValueOfCurrentProperty("");
    this.Yg.getProperties().setCurrentProperty("BillPayWarehouseID");
    this.Yg.getProperties().setValueOfCurrentProperty("");
    localHttpSession.removeAttribute("LastRequest");
    Object localObject1 = TrackingIDGenerator.GetNextID();
    Object localObject2 = new Object[2];
    localObject2[0] = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + this.Yg.getEntGroupType(), null);
    localObject2[1] = this.Yg.getGroupName();
    Object localObject3 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AddBusinessGroup_1", (Object[])localObject2);
    Initialize.audit(localSecureUser, (ILocalizable)localObject3, (String)localObject1, 3222);
    return super.getSuccessURL();
  }
  
  public void setCurrentProperty(String paramString)
  {
    this.Yg.getProperties().setCurrentProperty(paramString);
  }
  
  public void setValueOfCurrentProperty(String paramString)
  {
    this.Yg.getProperties().setValueOfCurrentProperty(paramString);
  }
  
  public void setGroupProperty(String paramString)
  {
    int i = paramString.indexOf("||");
    if (i != -1)
    {
      String str1 = paramString.substring(0, i);
      String str2 = paramString.substring(i + 2);
      this.Yg.getProperties().setCurrentProperty(str1);
      this.Yg.getProperties().setValueOfCurrentProperty(str2);
    }
  }
  
  public void setParentGroupId(String paramString)
  {
    this.Yg.setParentId(Integer.parseInt(paramString));
  }
  
  public int getParentGroupId()
  {
    return this.Yg.getParentId();
  }
  
  public void setGroupName(String paramString)
  {
    this.Yg.setGroupName(paramString);
  }
  
  public String getGroupName()
  {
    return this.Yg.getGroupName();
  }
  
  public void setGroupType(String paramString)
  {
    this.Yg.setEntGroupType(paramString);
  }
  
  public void setCheckboxesAvailable(String paramString)
  {
    this.Yj = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setACHCompanyID(String paramString)
  {
    if (paramString != null) {
      this.Yk = paramString.trim();
    }
  }
  
  public String getACHCompanyID()
  {
    return this.Yk;
  }
  
  public void setACHCompanyName(String paramString)
  {
    if (paramString != null) {
      this.Yi = paramString.trim();
    }
  }
  
  public String getACHCompanyName()
  {
    return this.Yi;
  }
  
  public void setAdminListName(String paramString)
  {
    this.Yh = paramString;
  }
  
  public String getAdminListName()
  {
    return this.Yh;
  }
  
  public void setValidateOnly(String paramString)
  {
    this.Ym = (paramString.equalsIgnoreCase("true"));
  }
  
  public boolean getValidateOnly()
  {
    return this.Ym;
  }
  
  private void jdMethod_int(HistoryTracker paramHistoryTracker, EntitlementGroup paramEntitlementGroup, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(paramEntitlementGroup.getClass().getName(), "NAME", paramEntitlementGroup.getGroupName(), paramILocalizable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.AddBusinessGroup
 * JD-Core Version:    0.7.0.1
 */