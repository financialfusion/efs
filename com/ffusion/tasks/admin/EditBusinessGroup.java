package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.autoentitle.AutoEntitle;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
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
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditBusinessGroup
  extends AutoEntitleBaseTask
  implements AdminTask
{
  private EntitlementGroup Zc = new EntitlementGroup();
  public static final String ACCOUNT = "account";
  public static final String KEY_VALUE_DELIM = "||";
  private boolean Zg = true;
  protected boolean _processACHCompanies = false;
  private String Zl = "EntitlementGroup";
  private String Zi = null;
  private String Zb = null;
  protected String _achCompaniesName = "ACHCOMPANIES";
  private boolean Zk = false;
  private static final String Zj = "com.ffusion.util.logging.audit.task";
  private static final String Za = "AuditMessage_EditBusinessGroup_1";
  private static final String Y9 = "AuditMessage_EditBusinessGroup_2";
  private static final String Zf = "AuditMessage_EditBusinessGroup_3";
  private static final String Zd = "AuditMessage_EditBusinessGroup_4";
  private static final String Ze = "AuditMessage_EditBusinessGroup_5";
  private static final String Zh = "AuditMessage_EditBusinessGroup_6";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
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
        EntitlementGroup localEntitlementGroup1 = (EntitlementGroup)localHttpSession.getAttribute(this.Zl);
        if (localEntitlementGroup1.getParentId() != this.Zc.getParentId())
        {
          if (this.Zc.getEntGroupType() == null) {
            this.Zc.setEntGroupType(localEntitlementGroup1.getEntGroupType());
          }
          this._entGroup = this.Zc;
          this._oldGroup = localEntitlementGroup1;
          initialize(localSecureUser);
        }
        this._initAutoEntitle = false;
        return null;
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        return super.getServiceErrorURL();
      }
    }
    if ((this.Zc.getGroupName() == null) || (this.Zc.getGroupName().equals("")))
    {
      this.error = 4540;
      return super.getTaskErrorURL();
    }
    if (this.Zc.getGroupName().length() > 255)
    {
      this.error = 4545;
      return super.getTaskErrorURL();
    }
    if ((!ValidateString.validateName(this.Zc.getGroupName())) || (this.Zc.getGroupName().length() > 255))
    {
      this.error = 4544;
      return super.getTaskErrorURL();
    }
    if (this.Zk)
    {
      this.Zk = false;
      return super.getSuccessURL();
    }
    EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
    }
    catch (CSILException localCSILException3)
    {
      this.error = MapError.mapError(localCSILException3);
      return super.getServiceErrorURL();
    }
    if (localEntitlementGroupMember == null)
    {
      try
      {
        localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
        localEntitlementGroupMember = com.ffusion.csil.core.Entitlements.getMember(localEntitlementGroupMember);
      }
      catch (CSILException localCSILException4)
      {
        this.error = MapError.mapError(localCSILException4);
        return super.getServiceErrorURL();
      }
      localHttpSession.setAttribute("EntitlementGroupMember", localEntitlementGroupMember);
    }
    EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localHttpSession.getAttribute(this.Zl);
    if (localEntitlementGroup2 == null)
    {
      this.error = 4508;
      return super.getTaskErrorURL();
    }
    EntitlementGroup localEntitlementGroup3 = (EntitlementGroup)localEntitlementGroup2.clone();
    int i = 0;
    int j = 0;
    String str2 = localEntitlementGroup2.getGroupName();
    int k = localEntitlementGroup2.getParentId();
    if (!localEntitlementGroup2.getGroupName().equalsIgnoreCase(this.Zc.getGroupName())) {
      i = 1;
    }
    if (localEntitlementGroup2.getParentId() != this.Zc.getParentId()) {
      j = 1;
    }
    try
    {
      if ((i != 0) || (j != 0))
      {
        EntitlementGroups localEntitlementGroups = com.ffusion.csil.core.Entitlements.getChildrenEntitlementGroups(this.Zc.getParentId());
        for (int n = 0; n < localEntitlementGroups.size(); n++)
        {
          EntitlementGroup localEntitlementGroup4 = (EntitlementGroup)localEntitlementGroups.get(n);
          if (localEntitlementGroup4.getGroupName().equalsIgnoreCase(this.Zc.getGroupName()))
          {
            this.error = 4542;
            return getTaskErrorURL();
          }
        }
      }
    }
    catch (CSILException localCSILException5)
    {
      this.error = MapError.mapError(localCSILException5);
      return super.getServiceErrorURL();
    }
    localEntitlementGroup2.setGroupName(this.Zc.getGroupName());
    localEntitlementGroup2.setParentId(this.Zc.getParentId());
    localEntitlementGroup2.setProperties(this.Zc.getProperties());
    Object localObject3;
    try
    {
      com.ffusion.csil.core.Entitlements.modifyEntitlementGroup(localEntitlementGroupMember, localEntitlementGroup2);
      if (j != 0)
      {
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = buildRestrictedEntitlementsList(localSecureUser);
        if (this._autoEntitle)
        {
          localObject1 = AutoEntitleUtil.buildAdminEntitlementsList(com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember, localEntitlementGroup2.getGroupId()));
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(localEntitlementGroupMember, localEntitlementGroup2.getGroupId(), (com.ffusion.csil.beans.entitlements.Entitlements)localObject1);
        }
        else
        {
          int i2 = localEntitlementGroup3.getParentId();
          localObject3 = localEntitlements.iterator();
          while (((Iterator)localObject3).hasNext())
          {
            localObject1 = (Entitlement)((Iterator)localObject3).next();
            if ((!"Location".equals(((Entitlement)localObject1).getObjectType())) && (com.ffusion.csil.core.Entitlements.checkEntitlement(i2, (Entitlement)localObject1))) {
              ((Iterator)localObject3).remove();
            }
          }
        }
        AutoEntitleAdmin.restrictFeatures(localSecureUser, localEntitlementGroup2, localEntitlements, new HashMap());
        Object localObject1 = new AutoEntitle();
        ((AutoEntitle)localObject1).setEntitlementGroup(localEntitlementGroup2);
        localObject2 = new AutoEntitle();
        ((AutoEntitle)localObject2).setEntitlementGroup(localEntitlementGroup2);
        AutoEntitleAdmin.getSettings(localSecureUser, (AutoEntitle)localObject2, new HashMap());
        AutoEntitleAdmin.setSettings(localSecureUser, (AutoEntitle)localObject1, (AutoEntitle)localObject2, new HashMap());
      }
    }
    catch (CSILException localCSILException6)
    {
      this.error = MapError.mapError(localCSILException6);
      return super.getServiceErrorURL();
    }
    int m = 0;
    int i1 = 0;
    if ("Division".equals(localEntitlementGroup2.getEntGroupType()))
    {
      i1 = 6;
      m = 1;
    }
    else if ("Group".equals(localEntitlementGroup2.getEntGroupType()))
    {
      i1 = 7;
      m = 1;
    }
    Object localObject2 = null;
    if (m != 0)
    {
      localObject2 = new HistoryTracker(localSecureUser, i1, Integer.toString(localEntitlementGroup2.getGroupId()));
      jdMethod_for((HistoryTracker)localObject2, localEntitlementGroup2, localEntitlementGroup3, null);
    }
    Object[] arrayOfObject;
    LocalizableString localLocalizableString;
    if (i != 0)
    {
      localObject3 = TrackingIDGenerator.GetNextID();
      arrayOfObject = new Object[3];
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + localEntitlementGroup2.getEntGroupType(), null);
      arrayOfObject[1] = str2;
      arrayOfObject[2] = localEntitlementGroup2.getGroupName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessGroup_1", arrayOfObject);
      Initialize.audit(localSecureUser, localLocalizableString, (String)localObject3, 3223);
    }
    if (j != 0)
    {
      localObject3 = TrackingIDGenerator.GetNextID();
      arrayOfObject = new Object[4];
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + localEntitlementGroup2.getEntGroupType(), null);
      arrayOfObject[1] = str2;
      arrayOfObject[2] = jdMethod_goto(k);
      arrayOfObject[3] = jdMethod_goto(localEntitlementGroup2.getParentId());
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessGroup_2", arrayOfObject);
      Initialize.audit(localSecureUser, localLocalizableString, (String)localObject3, 3223);
    }
    str1 = jdMethod_for(localEntitlementGroup2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember, str2, localSecureUser);
    if ((this.error == 0) && (this._processACHCompanies)) {
      str1 = jdMethod_int(localEntitlementGroup2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember, str2, localSecureUser);
    }
    if (m != 0) {
      try
      {
        HistoryAdapter.addHistory(((HistoryTracker)localObject2).getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for EditBusinessGroup: " + localProfileException.toString());
      }
    }
    this.Zl = "EntitlementGroup";
    localHttpSession.removeAttribute("LastRequest");
    return str1;
  }
  
  private String jdMethod_int(EntitlementGroup paramEntitlementGroup, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember, String paramString, SecureUser paramSecureUser)
  {
    String str1 = this.successURL;
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    try
    {
      localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(paramEntitlementGroup.getGroupId());
      ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute(this._achCompaniesName);
      if (localACHCompanies == null)
      {
        this.error = 16505;
        str1 = this.taskErrorURL;
      }
      else
      {
        HashSet localHashSet = new HashSet(localACHCompanies.size() * 4 / 3 + 1);
        String str2 = null;
        Iterator localIterator = localACHCompanies.iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (ACHCompany)localIterator.next();
          str2 = ((ACHCompany)localObject1).getCompanyID();
          localHashSet.add(str2);
        }
        localIterator = localACHCompanies.iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (ACHCompany)localIterator.next();
          str2 = (String)paramHttpSession.getAttribute(((ACHCompany)localObject1).getCompanyID());
          if (str2 != null)
          {
            localHashSet.remove(str2);
            localEntitlements.remove(new Entitlement(null, "ACHCompany", str2));
          }
        }
        Object localObject1 = null;
        LocalizableList localLocalizableList = new LocalizableList();
        Object localObject2 = localHashSet.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          str2 = (String)((Iterator)localObject2).next();
          localObject1 = new Entitlement(null, "ACHCompany", str2);
          localLocalizableList.add(str2);
          if (!localEntitlements.contains(localObject1)) {
            localEntitlements.add(localObject1);
          }
        }
        com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlementGroup.getGroupId(), localEntitlements);
        if (localLocalizableList.size() != 0)
        {
          localObject3 = new Object[3];
          localObject3[0] = localLocalizableList;
          localObject3[1] = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
          localObject3[2] = paramString;
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessGroup_3", (Object[])localObject3);
        }
        else
        {
          localObject3 = new Object[2];
          localObject3[0] = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
          localObject3[1] = paramString;
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessGroup_4", (Object[])localObject3);
        }
        Object localObject3 = TrackingIDGenerator.GetNextID();
        Initialize.audit(paramSecureUser, (ILocalizable)localObject2, (String)localObject3, 3223);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  private String jdMethod_for(EntitlementGroup paramEntitlementGroup, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember, String paramString, SecureUser paramSecureUser)
  {
    String str1 = this.successURL;
    if (this.Zg)
    {
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
      try
      {
        localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(paramEntitlementGroup.getGroupId());
        Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("Accounts");
        if (localAccounts == null)
        {
          this.error = 39;
          str1 = this.taskErrorURL;
        }
        else
        {
          HashSet localHashSet = new HashSet(localAccounts.size() * 4 / 3 + 1);
          String str2 = null;
          ListIterator localListIterator = localAccounts.listIterator();
          while (localListIterator.hasNext())
          {
            str2 = EntitlementsUtil.getEntitlementObjectId((Account)localListIterator.next());
            localHashSet.add(str2);
          }
          for (int i = 0; i < localAccounts.size(); i++)
          {
            str2 = (String)paramHttpSession.getAttribute("account" + i);
            if (str2 != null)
            {
              localHashSet.remove(str2);
              localEntitlements.remove(new Entitlement(null, "Account", str2));
            }
          }
          Entitlement localEntitlement = null;
          LocalizableList localLocalizableList = new LocalizableList();
          Object localObject2 = localHashSet.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            str2 = (String)((Iterator)localObject2).next();
            localEntitlement = new Entitlement(null, "Account", str2);
            Object localObject1;
            try
            {
              localObject1 = AccountUtil.buildLocalizableAccountID(str2);
            }
            catch (UtilException localUtilException)
            {
              DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + str2);
              localUtilException.printStackTrace();
              localObject1 = str2;
            }
            localLocalizableList.add(localObject1);
            if (!localEntitlements.contains(localEntitlement)) {
              localEntitlements.add(localEntitlement);
            }
          }
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlementGroup.getGroupId(), localEntitlements);
          if (localLocalizableList.size() != 0)
          {
            localObject3 = new Object[3];
            localObject3[0] = localLocalizableList;
            localObject3[1] = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
            localObject3[2] = paramString;
            localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessGroup_5", (Object[])localObject3);
          }
          else
          {
            localObject3 = new Object[2];
            localObject3[0] = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
            localObject3[1] = paramString;
            localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessGroup_6", (Object[])localObject3);
          }
          Object localObject3 = TrackingIDGenerator.GetNextID();
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, (String)localObject3, 3223);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setCurrentProperty(String paramString)
  {
    this.Zc.getProperties().setCurrentProperty(paramString);
  }
  
  private String jdMethod_goto(int paramInt)
  {
    try
    {
      EntitlementGroup localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(paramInt);
      if (localEntitlementGroup == null) {
        return "";
      }
      return localEntitlementGroup.getGroupName();
    }
    catch (CSILException localCSILException) {}
    return "";
  }
  
  public void setValueOfCurrentProperty(String paramString)
  {
    this.Zc.getProperties().setValueOfCurrentProperty(paramString);
  }
  
  public void setGroupProperty(String paramString)
  {
    int i = paramString.indexOf("||");
    if (i != -1)
    {
      String str1 = paramString.substring(0, i);
      String str2 = paramString.substring(i + 2);
      this.Zc.getProperties().setCurrentProperty(str1);
      this.Zc.getProperties().setValueOfCurrentProperty(str2);
    }
  }
  
  public void setParentGroupId(String paramString)
  {
    this.Zc.setParentId(Integer.parseInt(paramString));
  }
  
  public int getParentGroupId()
  {
    return this.Zc.getParentId();
  }
  
  public void setParentGroupName(String paramString)
  {
    this.Zb = paramString;
  }
  
  public String getParentGroupName()
  {
    return this.Zb;
  }
  
  public void setGroupName(String paramString)
  {
    this.Zc.setGroupName(paramString);
  }
  
  public String getGroupName()
  {
    return this.Zc.getGroupName();
  }
  
  public void setCheckboxesAvailable(String paramString)
  {
    this.Zg = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSessionGroupName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.Zl = paramString;
    }
  }
  
  public void setSupervisorId(String paramString)
  {
    this.Zi = paramString;
  }
  
  public String getSupervisorId()
  {
    return this.Zi;
  }
  
  public void setProcessACHCompanies(String paramString)
  {
    this._processACHCompanies = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setACHCompaniesInSessionName(String paramString)
  {
    this._achCompaniesName = paramString;
  }
  
  public void setValidateOnly(String paramString)
  {
    this.Zk = (paramString.equalsIgnoreCase("true"));
  }
  
  public boolean getValidateOnly()
  {
    return this.Zk;
  }
  
  private void jdMethod_for(HistoryTracker paramHistoryTracker, EntitlementGroup paramEntitlementGroup1, EntitlementGroup paramEntitlementGroup2, String paramString)
  {
    paramHistoryTracker.detectChange(paramEntitlementGroup1.getClass().getName(), "ID", paramEntitlementGroup2.getGroupId(), paramEntitlementGroup1.getGroupId(), paramString);
    paramHistoryTracker.detectChange(paramEntitlementGroup1.getClass().getName(), "NAME", paramEntitlementGroup2.getGroupName(), paramEntitlementGroup1.getGroupName(), paramString);
    paramHistoryTracker.detectChange(paramEntitlementGroup1.getClass().getName(), "PARENT GROUP", jdMethod_goto(paramEntitlementGroup2.getParentId()), jdMethod_goto(paramEntitlementGroup1.getParentId()), paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.EditBusinessGroup
 * JD-Core Version:    0.7.0.1
 */