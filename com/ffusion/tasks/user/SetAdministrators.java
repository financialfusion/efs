package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetAdministrators
  implements UserTask
{
  private String uZ;
  private String u4;
  private String u8;
  private int uX = -1;
  private String uY = "Business";
  private String u7 = "";
  private String u0;
  protected int _error;
  protected String _successURL;
  protected String _taskErrorURL = "TE";
  protected String _serviceErrorURL = "SE";
  protected String _commit = Boolean.TRUE.toString();
  protected boolean _autoEntitle = false;
  public static final String AUTO_ENTITLE_SESSION_KEY = "AutoEntitleAdministrators";
  private static final String u2 = "com.ffusion.util.logging.audit.task";
  private static final String u3 = "AuditMessage_SetAdministrators_1.1";
  private static final String u6 = "AuditMessage_SetAdministrators_1.2";
  private static final String u5 = "AuditMessage_AdminListEmployeeItem";
  private static final String u1 = "AuditMessage_AdminListGroupItem";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    this._error = 0;
    if ((this.uX == -1) && (this._commit == "true"))
    {
      this._error = 3523;
      return this._taskErrorURL;
    }
    if (this.uZ == null)
    {
      this._error = 3524;
      return this._taskErrorURL;
    }
    if (this.u8 == null)
    {
      this._error = 3525;
      return this._taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMembers localEntitlementGroupMembers = (EntitlementGroupMembers)localHttpSession.getAttribute(this.uZ);
    ArrayList localArrayList1 = (ArrayList)localHttpSession.getAttribute(this.u8);
    ArrayList localArrayList2 = (ArrayList)localHttpSession.getAttribute(this.u4);
    ArrayList localArrayList3 = new ArrayList();
    ArrayList localArrayList4 = new ArrayList();
    ArrayList localArrayList5 = new ArrayList();
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
      EntitlementAdmins localEntitlementAdmins = new EntitlementAdmins();
      if (this.uX != -1) {
        localEntitlementAdmins = com.ffusion.csil.core.Entitlements.getAdminsForGroup(this.uX);
      }
      Object localObject2;
      EntitlementAdmin localEntitlementAdmin1;
      for (int i = 0; i < localEntitlementGroupMembers.size(); i++)
      {
        localObject2 = (EntitlementGroupMember)localEntitlementGroupMembers.get(i);
        for (k = 0; k < localEntitlementAdmins.size(); k++)
        {
          localEntitlementAdmin1 = (EntitlementAdmin)localEntitlementAdmins.get(k);
          if ((((EntitlementGroupMember)localObject2).getMemberType().equals(localEntitlementAdmin1.getGranteeMemberType())) && (((EntitlementGroupMember)localObject2).getMemberSubType().equals(localEntitlementAdmin1.getGranteeMemberSubType())) && (((EntitlementGroupMember)localObject2).getId().equals(localEntitlementAdmin1.getGranteeMemberId())) && (((EntitlementGroupMember)localObject2).getEntitlementGroupId() == localEntitlementAdmin1.getGranteeGroupId()))
          {
            localEntitlementAdmins.remove(k);
            localArrayList4.add(((EntitlementGroupMember)localObject2).getId());
            break;
          }
        }
      }
      for (i = 0; i < localArrayList2.size(); i++)
      {
        localObject2 = (String)localArrayList2.get(i);
        for (k = 0; k < localEntitlementAdmins.size(); k++)
        {
          localEntitlementAdmin1 = (EntitlementAdmin)localEntitlementAdmins.get(k);
          if ((localEntitlementAdmin1.getGranteeMemberType() == null) && (localEntitlementAdmin1.getGranteeMemberSubType() == null) && (localEntitlementAdmin1.getGranteeMemberId() == null) && (Integer.parseInt((String)localObject2) == localEntitlementAdmin1.getGranteeGroupId()))
          {
            localEntitlementAdmins.remove(k);
            localArrayList4.add(localObject2);
            break;
          }
        }
      }
      Object localObject4;
      for (i = 0; i < localArrayList1.size(); i++) {
        if ((localArrayList1.get(i) instanceof EntitlementGroupMember))
        {
          localObject2 = (EntitlementGroupMember)localArrayList1.get(i);
          k = 0;
          for (int m = 0; m < localEntitlementAdmins.size(); m++)
          {
            localObject4 = (EntitlementAdmin)localEntitlementAdmins.get(m);
            if ((((EntitlementGroupMember)localObject2).getMemberType().equals(((EntitlementAdmin)localObject4).getGranteeMemberType())) && (((EntitlementGroupMember)localObject2).getMemberSubType().equals(((EntitlementAdmin)localObject4).getGranteeMemberSubType())) && (((EntitlementGroupMember)localObject2).getId().equals(((EntitlementAdmin)localObject4).getGranteeMemberId())) && (((EntitlementGroupMember)localObject2).getEntitlementGroupId() == ((EntitlementAdmin)localObject4).getGranteeGroupId()))
            {
              k = 1;
              break;
            }
          }
          if (k == 0)
          {
            EntitlementAdmin localEntitlementAdmin2 = new EntitlementAdmin();
            localEntitlementAdmin2.setGranteeGroupId(((EntitlementGroupMember)localObject2).getEntitlementGroupId());
            localEntitlementAdmin2.setGranteeMemberType(((EntitlementGroupMember)localObject2).getMemberType());
            localEntitlementAdmin2.setGranteeMemberSubType(((EntitlementGroupMember)localObject2).getMemberSubType());
            localEntitlementAdmin2.setGranteeMemberId(((EntitlementGroupMember)localObject2).getId());
            localEntitlementAdmin2.setTargetGroupId(this.uX);
            localEntitlementAdmin2.setAdminister(true);
            localEntitlementAdmin2.setExtend(true);
            localEntitlementAdmins.add(localEntitlementAdmin2);
            localArrayList3.add(((EntitlementGroupMember)localObject2).getId());
            if ((!localArrayList5.contains(localObject2)) && (!com.ffusion.csil.core.Entitlements.canAdministerAnyGroup((EntitlementGroupMember)localObject2))) {
              localArrayList5.add(localObject2);
            }
          }
        }
        else
        {
          localObject2 = (String)localArrayList1.get(i);
          k = 0;
          for (int n = 0; n < localEntitlementAdmins.size(); n++)
          {
            localObject4 = (EntitlementAdmin)localEntitlementAdmins.get(n);
            if ((((EntitlementAdmin)localObject4).getGranteeMemberType() == null) && (((EntitlementAdmin)localObject4).getGranteeMemberSubType() == null) && (((EntitlementAdmin)localObject4).getGranteeMemberId() == null) && (Integer.parseInt((String)localObject2) == ((EntitlementAdmin)localObject4).getGranteeGroupId()))
            {
              k = 1;
              break;
            }
          }
          if (k == 0)
          {
            EntitlementAdmin localEntitlementAdmin3 = new EntitlementAdmin();
            localEntitlementAdmin3.setGranteeGroupId(Integer.parseInt((String)localObject2));
            localEntitlementAdmin3.setGranteeMemberType(null);
            localEntitlementAdmin3.setGranteeMemberSubType(null);
            localEntitlementAdmin3.setGranteeMemberId(null);
            localEntitlementAdmin3.setTargetGroupId(this.uX);
            localEntitlementAdmin3.setAdminister(true);
            localEntitlementAdmin3.setExtend(true);
            localEntitlementAdmins.add(localEntitlementAdmin3);
            localArrayList3.add(localObject2);
            localObject4 = com.ffusion.csil.core.Entitlements.getMembers(Integer.parseInt((String)localObject2));
            if (localObject4 != null)
            {
              localObject6 = ((EntitlementGroupMembers)localObject4).iterator();
              while (((Iterator)localObject6).hasNext())
              {
                EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)((Iterator)localObject6).next();
                if ((!localArrayList5.contains(localEntitlementGroupMember)) && (!com.ffusion.csil.core.Entitlements.canAdministerAnyGroup(localEntitlementGroupMember))) {
                  localArrayList5.add(localEntitlementGroupMember);
                }
              }
            }
          }
        }
      }
      if (localEntitlementAdmins.isEmpty())
      {
        this._error = 3534;
        return this._serviceErrorURL;
      }
      Object localObject1 = new ArrayList();
      int j = 0;
      int k = 0;
      for (int i1 = 0; i1 < localEntitlementAdmins.size(); i1++)
      {
        localObject4 = (EntitlementAdmin)localEntitlementAdmins.get(i1);
        if ((((EntitlementAdmin)localObject4).getGranteeMemberType() != null) && (((EntitlementAdmin)localObject4).getGranteeMemberType().equals("USER")) && (((EntitlementAdmin)localObject4).getGranteeMemberSubType() != null) && (((EntitlementAdmin)localObject4).getGranteeMemberSubType().equals(String.valueOf(1))))
        {
          j = 1;
          ((ArrayList)localObject1).add(((EntitlementAdmin)localObject4).getGranteeMemberId());
        }
      }
      if (j != 0)
      {
        localObject3 = UserAdmin.getUsersByIds(localSecureUser, (ArrayList)localObject1, localHashMap);
        localObject6 = ((Users)localObject3).iterator();
        while (((Iterator)localObject6).hasNext())
        {
          localObject4 = (User)((Iterator)localObject6).next();
          if (((User)localObject4).getAccountStatus().equals(User.STATUS_ACTIVE)) {
            k = 1;
          }
        }
      }
      Object localObject8;
      Object localObject9;
      if ((j == 0) || (k == 0)) {
        for (int i2 = 0; i2 < localEntitlementAdmins.size(); i2++)
        {
          localObject6 = (EntitlementAdmin)localEntitlementAdmins.get(i2);
          if ((((EntitlementAdmin)localObject6).getGranteeMemberType() == null) && (((EntitlementAdmin)localObject6).getGranteeMemberSubType() == null))
          {
            i3 = ((EntitlementAdmin)localObject6).getGranteeGroupId();
            localObject8 = UserAdmin.getBusinessEmployeesByEntGroupId(localSecureUser, i3, localHashMap);
            if (!((BusinessEmployees)localObject8).isEmpty())
            {
              j = 1;
              localObject9 = ((BusinessEmployees)localObject8).iterator();
              while (((Iterator)localObject9).hasNext())
              {
                localObject3 = (BusinessEmployee)((Iterator)localObject9).next();
                if (((BusinessEmployee)localObject3).getAccountStatus().equals(User.STATUS_ACTIVE)) {
                  k = 1;
                }
              }
            }
          }
          if ((j != 0) && (k != 0)) {
            break;
          }
        }
      }
      if ((j == 0) || (k == 0))
      {
        this._error = 3534;
        return this._taskErrorURL;
      }
      if (!Boolean.valueOf(this._commit).booleanValue())
      {
        if (localArrayList5.size() != 0) {
          this._autoEntitle = true;
        } else {
          this._autoEntitle = false;
        }
        localHttpSession.setAttribute("AutoEntitleAdministrators", String.valueOf(this._autoEntitle));
        return this._successURL;
      }
      com.ffusion.csil.core.Entitlements.setAdministratorGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), this.uX, localEntitlementAdmins);
      localObject1 = (String)localHttpSession.getAttribute("AutoEntitleAdministrators");
      Boolean localBoolean = localObject1 != null ? Boolean.valueOf((String)localObject1) : new Boolean("false");
      localHashMap.put("AUTOENTITLE_FLAG_KEY", localBoolean);
      Iterator localIterator = localArrayList5.iterator();
      while (localIterator.hasNext())
      {
        localObject3 = (EntitlementGroupMember)localIterator.next();
        localObject5 = AutoEntitleAdmin.getCumulativeSettings(localSecureUser, (EntitlementGroupMember)localObject3, localHashMap);
        localHashMap.put("AUTOENTITLE_SETTINGS_KEY", localObject5);
        localObject6 = AutoEntitleUtil.buildRestrictedAdminEntitlementsList(localSecureUser, (EntitlementGroupMember)localObject3, localHashMap);
        AutoEntitleAdmin.restrictFeatures(localSecureUser, (EntitlementGroupMember)localObject3, (com.ffusion.csil.beans.entitlements.Entitlements)localObject6, localHashMap);
      }
      Object localObject3 = new HistoryTracker(localSecureUser, 2, this.u0);
      Object localObject5 = new LocalizableList();
      Object localObject6 = (Locale)localHttpSession.getAttribute("java.util.Locale");
      Object localObject11;
      for (int i3 = 0; i3 < localEntitlementAdmins.size(); i3++)
      {
        localObject8 = (EntitlementAdmin)localEntitlementAdmins.get(i3);
        if ((((EntitlementAdmin)localObject8).getGranteeMemberType() != null) && (((EntitlementAdmin)localObject8).getGranteeMemberType().equals("USER")) && (((EntitlementAdmin)localObject8).getGranteeMemberSubType() != null) && (((EntitlementAdmin)localObject8).getGranteeMemberSubType().equals(String.valueOf(1))))
        {
          localObject9 = new BusinessEmployee((Locale)localObject6);
          ((BusinessEmployee)localObject9).setBankId(localSecureUser.getBankID());
          ((BusinessEmployee)localObject9).setId(((EntitlementAdmin)localObject8).getGranteeMemberId());
          localObject10 = UserAdmin.getBusinessEmployees(localSecureUser, (BusinessEmployee)localObject9, null);
          if ((localObject10 != null) && (((BusinessEmployees)localObject10).size() > 0))
          {
            localObject11 = (BusinessEmployee)((BusinessEmployees)localObject10).get(0);
            Object[] arrayOfObject = { ((BusinessEmployee)localObject11).getName() };
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AdminListEmployeeItem", arrayOfObject);
            ((LocalizableList)localObject5).add(localLocalizableString);
            int i4 = localArrayList3.indexOf(((EntitlementAdmin)localObject8).getGranteeMemberId());
            if (i4 != -1) {
              ((HistoryTracker)localObject3).logChange(Business.BEAN_NAME, Business.ADMINISTRATOR, null, ((BusinessEmployee)localObject11).getName(), ((HistoryTracker)localObject3).buildLocalizableComment(5));
            }
          }
        }
        else
        {
          localObject9 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(((EntitlementAdmin)localObject8).getGranteeGroupId());
          if (localObject9 != null)
          {
            localObject10 = new Object[2];
            localObject10[0] = ((EntitlementGroup)localObject9).getGroupName();
            localObject10[1] = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + ((EntitlementGroup)localObject9).getEntGroupType(), null);
            localObject11 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AdminListGroupItem", (Object[])localObject10);
            ((LocalizableList)localObject5).add(localObject11);
          }
        }
      }
      Object localObject7 = localArrayList4.iterator();
      while (((Iterator)localObject7).hasNext())
      {
        localObject8 = (String)((Iterator)localObject7).next();
        localObject9 = new BusinessEmployee((Locale)localObject6);
        ((BusinessEmployee)localObject9).setBankId(localSecureUser.getBankID());
        ((BusinessEmployee)localObject9).setId((String)localObject8);
        localObject10 = UserAdmin.getBusinessEmployees(localSecureUser, (BusinessEmployee)localObject9, null);
        if ((localObject10 != null) && (((BusinessEmployees)localObject10).size() > 0))
        {
          localObject11 = (BusinessEmployee)((BusinessEmployees)localObject10).get(0);
          ((HistoryTracker)localObject3).logChange(Business.BEAN_NAME, Business.ADMINISTRATOR, ((BusinessEmployee)localObject11).getName(), null, ((HistoryTracker)localObject3).buildLocalizableComment(6));
        }
      }
      localObject7 = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + this.uY, null);
      if (this.uY.equals("Business"))
      {
        localObject8 = new Object[2];
        localObject8[0] = localObject5;
        localObject8[1] = localObject7;
        localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_SetAdministrators_1.1", (Object[])localObject8);
      }
      else
      {
        localObject8 = new Object[3];
        localObject8[0] = localObject5;
        localObject8[1] = localObject7;
        localObject8[2] = this.u7;
        localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_SetAdministrators_1.2", (Object[])localObject8);
      }
      Object localObject10 = TrackingIDGenerator.GetNextID();
      Initialize.audit(localSecureUser, (ILocalizable)localObject9, (String)localObject10, 3226);
      try
      {
        HistoryAdapter.addHistory(((HistoryTracker)localObject3).getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for SetAdministrators: " + localProfileException.toString());
      }
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.childException != null) && ((localCSILException.childException instanceof CSILException))) {
        this._error = MapError.mapError((CSILException)localCSILException.childException);
      } else {
        this._error = MapError.mapError(localCSILException);
      }
      return this._serviceErrorURL;
    }
    return this._successURL;
  }
  
  public void setEntitlementGroupType(String paramString)
  {
    this.uY = paramString;
  }
  
  public void setEntitlementGroupName(String paramString)
  {
    this.u7 = paramString;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.uX = Integer.parseInt(paramString);
  }
  
  public void setGroupListName(String paramString)
  {
    this.u4 = paramString;
  }
  
  public void setUserListName(String paramString)
  {
    this.uZ = paramString;
  }
  
  public void setAdminListName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.u8 = null;
    } else {
      this.u8 = paramString;
    }
  }
  
  public boolean setError(int paramInt)
  {
    this._error = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this._error);
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this._taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this._serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this._successURL = paramString;
  }
  
  public void setHistoryId(String paramString)
  {
    this.u0 = paramString;
  }
  
  public void setCommit(String paramString)
  {
    this._commit = paramString;
  }
  
  public void setAutoEntitle(String paramString)
  {
    this._autoEntitle = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitle()
  {
    return String.valueOf(this._autoEntitle);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetAdministrators
 * JD-Core Version:    0.7.0.1
 */