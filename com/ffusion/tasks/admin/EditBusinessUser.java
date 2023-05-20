package com.ffusion.tasks.admin;

import com.ffusion.beans.Currency;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyList;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.csil.handlers.CashCon;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Alerts;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.PasswordUtil;
import com.ffusion.tasks.util.ValidateString;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableProperty;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditBusinessUser
  extends AddEditBusinessGroupLimits
  implements AdminTask
{
  private static final String YW = "X@Y.Z";
  private static final char[] Y3 = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ' ', '(', ')', '-' };
  private static final char YE = '@';
  private static final char Y7 = '.';
  public static final String ENTITLEMENT = "entitlement";
  public static final String TRANSACTION_LIMIT = "transaction_limit";
  public static final String DAY_LIMIT = "day_limit";
  public static final String WEEK_LIMIT = "week_limit";
  public static final String MONTH_LIMIT = "month_limit";
  public static final String TRANSACTION_EXCEED = "transaction_exceed";
  public static final String DAY_EXCEED = "day_exceed";
  public static final String WEEK_EXCEED = "week_exceed";
  public static final String MONTH_EXCEED = "month_exceed";
  private String YQ = null;
  private String YR = "ACCOUNTS";
  private String YH = "GroupAccounts";
  protected String _achCompaniesName = "ACHCOMPANIES";
  private String _groupName = "Entitlement_EntitlementGroup";
  private String YJ = "OldBusinessEmployee";
  private String Y0;
  private int Y5 = 3;
  private int YT = 3;
  private int Y2 = 1;
  private int YK = 1;
  private boolean Y6 = false;
  private int YX = 1;
  private static final String Y8 = "com.ffusion.util.logging.audit.task";
  private static final String YL = "AuditMessage_EditBusinessUser_AccountListItem";
  private static final String YG = "AuditMessage_EditBusinessUser_1";
  private static final String YI = "AuditMessage_EditBusinessUser_2";
  private static final String YF = "AuditMessage_EditBusinessUser_3.1";
  private static final String YZ = "AuditMessage_EditBusinessUser_3.2";
  private static final String YY = "AuditMessage_EditBusinessUser_4.1";
  private static final String YM = "AuditMessage_EditBusinessUser_4.2";
  private static final String YP = "AuditMessage_EditBusinessUser";
  private static final String YS = "_5.1";
  private static final String Y1 = "_5.2";
  private static final String YV = "_6.1";
  private static final String YN = "_6.2";
  private static final String YO = "_7.1";
  private static final String YU = "_7.2";
  private static final String Y4 = "_xcurr";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    this.error = 0;
    localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    if (localEntitlementGroupMember1 == null)
    {
      localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      try
      {
        localEntitlementGroupMember1 = com.ffusion.csil.core.Entitlements.getMember(localEntitlementGroupMember1);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        return super.getServiceErrorURL();
      }
    }
    BusinessEmployee localBusinessEmployee1 = (BusinessEmployee)localHttpSession.getAttribute(this.YJ);
    if (localBusinessEmployee1 == null)
    {
      this.error = 4515;
      return super.getTaskErrorURL();
    }
    EntitlementGroupMember localEntitlementGroupMember2 = localBusinessEmployee1.getEntitlementGroupMember();
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute(this._groupName);
    if (localEntitlementGroup == null)
    {
      this.error = 4508;
      resetSessionNames();
      return super.getTaskErrorURL();
    }
    if (this.YQ == null)
    {
      this.error = 4503;
      return super.getTaskErrorURL();
    }
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Object localObject8;
    Object localObject10;
    Object localObject14;
    Object localObject15;
    Object localObject16;
    Object localObject17;
    Object localObject13;
    if (this.YQ.equalsIgnoreCase("UserInfo")) {
      try
      {
        BusinessEmployee localBusinessEmployee2 = (BusinessEmployee)localHttpSession.getAttribute("NewBusinessEmployee");
        if (localBusinessEmployee2 == null)
        {
          this.error = 4514;
          return super.getTaskErrorURL();
        }
        if ((localBusinessEmployee2.getEntitlementGroupId() != localBusinessEmployee1.getEntitlementGroupId()) && (jdMethod_for(localBusinessEmployee1.getEntitlementGroupMember(), localSecureUser, localHashMap)))
        {
          this.error = 4563;
          return this.taskErrorURL;
        }
        if ((!localBusinessEmployee2.getAccountStatus().equals(User.STATUS_ACTIVE)) && (!localBusinessEmployee2.getAccountStatus().equals(localBusinessEmployee1.getAccountStatus())) && (jdMethod_for(localBusinessEmployee1.getEntitlementGroupMember(), localSecureUser, localHashMap)))
        {
          this.error = 4564;
          return this.taskErrorURL;
        }
        if (this._initAutoEntitle) {
          try
          {
            if (localBusinessEmployee2.getEntitlementGroupId() != localBusinessEmployee1.getEntitlementGroupId())
            {
              this._empGroupMember = localBusinessEmployee2.getEntitlementGroupMember();
              this._oldGroupMember = localBusinessEmployee1.getEntitlementGroupMember();
              initialize(localSecureUser);
            }
            this._initAutoEntitle = false;
            return null;
          }
          catch (CSILException localCSILException3)
          {
            this.error = MapError.mapError(localCSILException3);
            return this.taskErrorURL;
          }
        }
        if ((localBusinessEmployee2.getUserName() == null) || (localBusinessEmployee2.getUserName().length() < this.Y5) || (localBusinessEmployee2.getUserName().length() > 20))
        {
          this.error = 1;
          return super.getTaskErrorURL();
        }
        if (!localBusinessEmployee2.getUserName().toLowerCase().equals(localBusinessEmployee2.getUserName()))
        {
          this.error = 1;
          return super.getTaskErrorURL();
        }
        localObject1 = (String)localHttpSession.getAttribute("NameConvention");
        if ((((String)localObject1).equalsIgnoreCase("dual")) && ((localBusinessEmployee2.getFirstName() == null) || (localBusinessEmployee2.getFirstName().length() == 0)))
        {
          this.error = 28;
          return super.getTaskErrorURL();
        }
        if ((localBusinessEmployee2.getLastName() == null) || (localBusinessEmployee2.getLastName().length() == 0))
        {
          this.error = 30;
          return super.getTaskErrorURL();
        }
        if (!ValidateString.validateName(localBusinessEmployee2.getUserName()))
        {
          this.error = 4544;
          return super.getTaskErrorURL();
        }
        if ((((String)localObject1).equalsIgnoreCase("dual")) && ((!ValidateString.validateName(localBusinessEmployee2.getFirstName(), "-'")) || (localBusinessEmployee2.getFirstName().length() > 35)))
        {
          this.error = 4544;
          return super.getTaskErrorURL();
        }
        if ((!ValidateString.validateName(localBusinessEmployee2.getLastName(), "-'")) || (localBusinessEmployee2.getLastName().length() > 35))
        {
          this.error = 4544;
          return super.getTaskErrorURL();
        }
        this.Y0 = localBusinessEmployee2.getPassword();
        localObject2 = localBusinessEmployee2.getConfirmPassword();
        if ((this.Y0 != null) && (this.Y0.length() > 0) && ((localObject2 == null) || (((String)localObject2).length() == 0)))
        {
          this.error = 3;
          return super.getTaskErrorURL();
        }
        if ((localObject2 != null) && (((String)localObject2).length() > 0) && ((this.Y0 == null) || (this.Y0.length() == 0)))
        {
          this.error = 3;
          return super.getTaskErrorURL();
        }
        if ((localObject2 != null) && (this.Y0 != null) && (!((String)localObject2).equals(this.Y0)))
        {
          this.error = 5;
          return super.getTaskErrorURL();
        }
        if (((this.Y0 == null) || (this.Y0.length() <= 0)) && ((localObject2 == null) || (((String)localObject2).length() <= 0)))
        {
          this.Y0 = localBusinessEmployee1.getPassword();
          localBusinessEmployee2.setPassword(this.Y0);
        }
        if (!validatePassword()) {
          return super.getTaskErrorURL();
        }
        if (!jdMethod_byte(localSecureUser, localBusinessEmployee2.getZipCode(), localBusinessEmployee2.getCountry()))
        {
          this.error = 26;
          return super.getTaskErrorURL();
        }
        if (!jdMethod_case(localSecureUser, localBusinessEmployee2.getPhone(), localBusinessEmployee2.getCountry()))
        {
          this.error = 27;
          return super.getTaskErrorURL();
        }
        if (!jdMethod_case(localSecureUser, localBusinessEmployee2.getFaxPhone(), localBusinessEmployee2.getCountry()))
        {
          this.error = 119;
          return super.getTaskErrorURL();
        }
        if (!jdMethod_case(localSecureUser, localBusinessEmployee2.getDataPhone(), localBusinessEmployee2.getCountry()))
        {
          this.error = 120;
          return super.getTaskErrorURL();
        }
        if (!G(localBusinessEmployee2.getEmail()))
        {
          this.error = 31;
          return super.getTaskErrorURL();
        }
        localHashMap.put("BUSINESS_CUST_ID", localSecureUser.getBusinessCustId());
        if ((!localBusinessEmployee2.getUserName().equalsIgnoreCase(localBusinessEmployee1.getUserName())) && (UserAdmin.userExists(localSecureUser, localBusinessEmployee2.getUserName(), localBusinessEmployee2.getBankId(), localHashMap)))
        {
          this.error = 3014;
          return super.getServiceErrorURL();
        }
        localObject3 = (Alerts)localHttpSession.getAttribute("com.ffusion.services.Alerts");
        if (localHashMap == null) {
          localHashMap = new HashMap();
        }
        localHashMap.put("SERVICE", localObject3);
        Object localObject4;
        if (localBusinessEmployee2.getEntitlementGroupId() != localBusinessEmployee1.getEntitlementGroupId())
        {
          localObject4 = buildRestrictedEntitlementsList(localSecureUser);
          com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = null;
          if ((!com.ffusion.csil.core.Entitlements.canAdministerAnyGroup(localBusinessEmployee1.getEntitlementGroupMember())) && (com.ffusion.csil.core.Entitlements.getAdminInfoFor(localBusinessEmployee2.getEntitlementGroupId()).size() != 0))
          {
            localHashMap.put("AUTOENTITLE_FLAG_KEY", new Boolean(this._autoEntitle));
            localHashMap.put("AUTOENTITLE_SETTINGS_KEY", this._autoEntitleSettings);
            localEntitlements2 = AutoEntitleUtil.buildRestrictedAdminEntitlementsList(localSecureUser, this._newEmpGroup, localHashMap);
          }
          if (!this._autoEntitle)
          {
            int i = 0;
            localObject8 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(localBusinessEmployee1.getEntitlementGroupId());
            localObject10 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(localBusinessEmployee2.getEntitlementGroupId());
            ArrayList localArrayList2 = new ArrayList();
            if ((((EntitlementGroup)localObject8).getEntGroupType().equals("Group")) || (((EntitlementGroup)localObject8).getEntGroupType().equals("Division")))
            {
              int m = ((EntitlementGroup)localObject8).getGroupId();
              if (((EntitlementGroup)localObject8).getEntGroupType().equals("Group")) {
                m = ((EntitlementGroup)localObject8).getParentId();
              }
              if ((((EntitlementGroup)localObject10).getEntGroupType().equals("Group")) || (((EntitlementGroup)localObject10).getEntGroupType().equals("Division")))
              {
                int n = ((EntitlementGroup)localObject10).getGroupId();
                if (((EntitlementGroup)localObject10).getEntGroupType().equals("Group")) {
                  n = ((EntitlementGroup)localObject10).getParentId();
                }
                if (n == m) {
                  i = 1;
                } else {
                  i = 0;
                }
              }
              else
              {
                localObject14 = new LocationSearchCriteria();
                ((LocationSearchCriteria)localObject14).setDivisionID(m);
                localObject15 = CashCon.getLocations(localSecureUser, (LocationSearchCriteria)localObject14, localHashMap);
                localObject16 = ((LocationSearchResults)localObject15).iterator();
                while (((Iterator)localObject16).hasNext())
                {
                  localObject17 = (LocationSearchResult)((Iterator)localObject16).next();
                  localArrayList2.add(((LocationSearchResult)localObject17).getLocationBPWID());
                }
                i = 0;
              }
            }
            else
            {
              i = 1;
            }
            localObject14 = localBusinessEmployee1.getEntitlementGroupMember();
            localObject15 = ((com.ffusion.csil.beans.entitlements.Entitlements)localObject4).iterator();
            while (((Iterator)localObject15).hasNext())
            {
              localObject13 = (Entitlement)((Iterator)localObject15).next();
              if (((!"Location".equals(((Entitlement)localObject13).getObjectType())) || (i != 0) || (localArrayList2.contains(((Entitlement)localObject13).getObjectId()))) && (com.ffusion.csil.core.Entitlements.checkEntitlement((EntitlementGroupMember)localObject14, (Entitlement)localObject13))) {
                ((Iterator)localObject15).remove();
              }
            }
          }
          if (localEntitlements2 != null) {
            ((com.ffusion.csil.beans.entitlements.Entitlements)localObject4).addAll(localEntitlements2);
          }
          localHashMap.put("AUTOENTITLE_FLAG_KEY", new Boolean(this._autoEntitle));
          localHashMap.put("AUTOENTITLE_RESTRICTED_ENTS_KEY", localObject4);
        }
        localBusinessEmployee2 = UserAdmin.modifyBusinessEmployee(localSecureUser, localBusinessEmployee2, localHashMap);
        if ((Integer.parseInt(localBusinessEmployee1.getId()) != localSecureUser.getProfileID()) && (!localBusinessEmployee1.getPassword().equals(localBusinessEmployee2.getPassword())))
        {
          localObject4 = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
          ((User)localObject4).setId(localBusinessEmployee2.getId());
          ((User)localObject4).put("PASSWORD_STATUS", "2");
          try
          {
            UserAdmin.modifyPasswordStatus(localSecureUser, (User)localObject4, localHashMap);
          }
          catch (CSILException localCSILException4)
          {
            this.error = MapError.mapError(localCSILException4);
            return super.getServiceErrorURL();
          }
        }
        if (localSecureUser.getProfileID() == localBusinessEmployee2.getSecureUser().getProfileID()) {
          localSecureUser.setEntitlementID(localBusinessEmployee2.getEntitlementGroupId());
        }
        localObject3 = new HistoryTracker(localSecureUser, 1, localBusinessEmployee2.getId());
        localBusinessEmployee2.logChanges((HistoryTracker)localObject3, localBusinessEmployee1, ((HistoryTracker)localObject3).buildLocalizableComment(17));
        try
        {
          HistoryAdapter.addHistory(((HistoryTracker)localObject3).getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for EditBusinessUser: " + localProfileException.toString());
        }
        localHttpSession.removeAttribute("LastRequest");
        return super.getSuccessURL();
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        return super.getServiceErrorURL();
      }
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = (com.ffusion.csil.beans.entitlements.Entitlements)localHttpSession.getAttribute("RestrictedEntitlements");
    if (localEntitlements1 == null) {
      localEntitlements1 = new com.ffusion.csil.beans.entitlements.Entitlements();
    }
    if (this.YQ.equalsIgnoreCase("ACHPermissions"))
    {
      localObject1 = modifyACHCompanyEntitlements(localSecureUser, localEntitlementGroup, localHttpSession, localEntitlementGroupMember2, localEntitlementGroupMember1);
      if ((localObject1 != null) && (((String)localObject1).equalsIgnoreCase(this.serviceErrorURL))) {
        return this.serviceErrorURL;
      }
      if ((localObject1 != null) && (((String)localObject1).equalsIgnoreCase(this.taskErrorURL))) {
        return this.taskErrorURL;
      }
    }
    else
    {
      Object localObject5;
      Object localObject12;
      Object localObject9;
      if (this.YQ.equalsIgnoreCase("Permissions"))
      {
        try
        {
          localObject1 = paramHttpServletRequest.getParameterValues(this.YR);
          localObject2 = new ArrayList();
          if ((localObject1 != null) && (localObject1.length > 0)) {
            localObject2 = new ArrayList(Arrays.asList((Object[])localObject1));
          }
          localObject3 = (Accounts)localHttpSession.getAttribute(this.YH);
          localObject5 = new com.ffusion.csil.beans.entitlements.Entitlements();
          Object localObject6;
          Object localObject7;
          if ((localObject3 != null) && (((Accounts)localObject3).size() > 0))
          {
            localObject6 = ((Accounts)localObject3).iterator();
            while (((Iterator)localObject6).hasNext())
            {
              localObject7 = (Account)((Iterator)localObject6).next();
              localObject8 = new Entitlement(null, "Account", EntitlementsUtil.getEntitlementObjectId((Account)localObject7));
              if (!((ArrayList)localObject2).contains(((Account)localObject7).getID())) {
                localEntitlements1.add(localObject8);
              } else if (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, (Entitlement)localObject8)) {
                ((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).add(localObject8);
              }
            }
          }
          if ((localEntitlements1 != null) && (localEntitlements1.size() > 0))
          {
            com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements1);
            localObject6 = new LocalizableList();
            for (int j = 0; j < localEntitlements1.size(); j++)
            {
              localObject10 = (Entitlement)localEntitlements1.get(j);
              if ((((Entitlement)localObject10).getObjectType() != null) && (((Entitlement)localObject10).getObjectId() != null))
              {
                try
                {
                  localObject7 = AccountUtil.buildLocalizableAccountID(((Entitlement)localObject10).getObjectId());
                }
                catch (UtilException localUtilException1)
                {
                  DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Entitlement)localObject10).getObjectId());
                  localUtilException1.printStackTrace();
                  localObject7 = ((Entitlement)localObject10).getObjectId();
                }
                localObject11 = new Object[] { localObject7 };
                localObject13 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessUser_AccountListItem", (Object[])localObject11);
                ((LocalizableList)localObject6).add(localObject13);
              }
            }
            String str1 = TrackingIDGenerator.GetNextID();
            localObject10 = new Object[2];
            localObject10[0] = localObject6;
            localObject10[1] = localBusinessEmployee1.getName();
            Object localObject11 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessUser_1", (Object[])localObject10);
            Initialize.audit(localSecureUser, (ILocalizable)localObject11, str1, 3225);
          }
          if ((localObject5 != null) && (((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).size() > 0)) {
            try
            {
              com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, (com.ffusion.csil.beans.entitlements.Entitlements)localObject5);
              localObject6 = new LocalizableList();
              for (int k = 0; k < ((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).size(); k++)
              {
                localObject10 = (Entitlement)((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).get(k);
                if ((((Entitlement)localObject10).getObjectType() != null) && (((Entitlement)localObject10).getObjectId() != null))
                {
                  try
                  {
                    localObject7 = AccountUtil.buildAccountDisplayText(((Entitlement)localObject10).getObjectId(), localSecureUser.getLocale());
                  }
                  catch (UtilException localUtilException2)
                  {
                    DebugLog.log(Level.WARNING, "Unable to build account display text for " + ((Entitlement)localObject10).getObjectId());
                    localUtilException2.printStackTrace();
                    localObject7 = ((Entitlement)localObject10).getObjectId();
                  }
                  localObject12 = new Object[] { localObject7 };
                  localObject13 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessUser_AccountListItem", (Object[])localObject12);
                  ((LocalizableList)localObject6).add(localObject13);
                }
              }
              localObject9 = TrackingIDGenerator.GetNextID();
              localObject10 = new Object[2];
              localObject10[0] = localObject6;
              localObject10[1] = localBusinessEmployee1.getName();
              localObject12 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessUser_2", (Object[])localObject10);
              Initialize.audit(localSecureUser, (ILocalizable)localObject12, (String)localObject9, 3225);
            }
            catch (Exception localException2) {}
          }
        }
        catch (Exception localException1)
        {
          this.error = 4511;
          return super.getTaskErrorURL();
        }
      }
      else if (this.YQ.equalsIgnoreCase("Limits"))
      {
        ArrayList localArrayList1 = (ArrayList)localHttpSession.getAttribute("NonlimitableOperationNames");
        if (localArrayList1 == null)
        {
          this.error = 4509;
          resetSessionNames();
          return super.getTaskErrorURL();
        }
        localObject2 = (ArrayList)localHttpSession.getAttribute("LimitableOperationNames");
        if (localObject2 == null)
        {
          this.error = 4509;
          resetSessionNames();
          return super.getTaskErrorURL();
        }
        if (this._operations == null)
        {
          this._operations = ((ArrayList)localHttpSession.getAttribute("NonAccountOperationNames"));
          if (this._operations == null)
          {
            this.error = 4509;
            resetSessionNames();
            return super.getTaskErrorURL();
          }
        }
        localObject3 = (ArrayList)localHttpSession.getAttribute("EntitlementsDisplayedNames");
        if (localObject3 == null)
        {
          this.error = 4509;
          resetSessionNames();
          return super.getTaskErrorURL();
        }
        localObject5 = null;
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements3 = null;
        try
        {
          localObject5 = com.ffusion.csil.core.Entitlements.getGroupLimits(localEntitlementGroupMember2);
          localEntitlements3 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2);
        }
        catch (Exception localException3)
        {
          this.error = 4504;
          resetSessionNames();
          return super.getTaskErrorURL();
        }
        Account localAccount = (Account)localHttpSession.getAttribute("Account");
        if (localAccount == null)
        {
          this.error = 4506;
          resetSessionNames();
          return super.getTaskErrorURL();
        }
        localObject9 = new com.ffusion.csil.beans.entitlements.Entitlements();
        localObject10 = new com.ffusion.csil.beans.entitlements.Entitlements();
        localObject12 = new Limits();
        localObject13 = new Limits();
        localObject14 = new Limits();
        localObject15 = getPreviousDataMap((Limits)localObject5, localEntitlements3);
        localObject16 = getIndexMap(paramHttpServletRequest, (ArrayList)localObject2, localArrayList1, (ArrayList)localObject3);
        localObject17 = null;
        String str2 = null;
        Integer localInteger = null;
        int i1 = -1;
        Object localObject18 = null;
        Entitlement localEntitlement = null;
        Limit[] arrayOfLimit = null;
        Iterator localIterator = ((HashMap)localObject16).entrySet().iterator();
        while (localIterator.hasNext())
        {
          localObject17 = (Map.Entry)localIterator.next();
          str2 = (String)((Map.Entry)localObject17).getKey();
          localInteger = (Integer)((Map.Entry)localObject17).getValue();
          if ((this._operations != null) && (this._operations.indexOf(str2) >= 0)) {
            localObject18 = ((HashMap)localObject15).get(str2 + "_null_null");
          } else if (str2.startsWith("ACHCompany")) {
            localObject18 = ((HashMap)localObject15).get("ACHBatch_ACHCompany_" + str2.substring(this.achCompanySize));
          } else {
            localObject18 = ((HashMap)localObject15).get(str2 + "_" + "Account" + "_" + EntitlementsUtil.getEntitlementObjectId(localAccount));
          }
          if (localInteger == null)
          {
            if ((localObject18 == null) || ((localObject18 instanceof Limit[])))
            {
              if ((this._operations != null) && (this._operations.indexOf(str2) >= 0)) {
                localEntitlement = new Entitlement(str2, null, null);
              } else if (str2.startsWith("ACHCompany")) {
                localEntitlement = new Entitlement("ACHBatch", "ACHCompany", str2.substring(this.achCompanySize));
              } else {
                localEntitlement = new Entitlement(str2, "Account", EntitlementsUtil.getEntitlementObjectId(localAccount));
              }
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject9).add(localEntitlement);
              if ((localObject18 instanceof Limit[]))
              {
                arrayOfLimit = (Limit[])localObject18;
                if (arrayOfLimit[0] != null) {
                  ((Limits)localObject14).add(arrayOfLimit[0]);
                }
                if (arrayOfLimit[1] != null) {
                  ((Limits)localObject14).add(arrayOfLimit[1]);
                }
                if (arrayOfLimit[2] != null) {
                  ((Limits)localObject14).add(arrayOfLimit[2]);
                }
                if (arrayOfLimit[3] != null) {
                  ((Limits)localObject14).add(arrayOfLimit[3]);
                }
              }
            }
          }
          else
          {
            i1 = localInteger.intValue();
            int i2 = isMoreRestrictiveThanCumulativeLimit(paramHttpServletRequest, i1, localAccount, str2, localEntitlementGroupMember2, localEntitlementGroup.getParentId());
            if (i2 != 0)
            {
              this.error = i2;
              resetSessionNames();
              return super.getTaskErrorURL();
            }
            if (localObject18 == null)
            {
              if (!localArrayList1.contains(str2))
              {
                setLimitRestrictions(paramHttpServletRequest, i1, localEntitlementGroup, str2, localEntitlementGroupMember2, localAccount, localLocale, (Limits)localObject12);
                if (i2 != 0) {
                  return this.taskErrorURL;
                }
              }
            }
            else if ((localObject18 instanceof Limit[]))
            {
              if (!localArrayList1.contains(str2))
              {
                addEditLimitRestrictions(localObject18, paramHttpServletRequest, i1, localEntitlementGroup, localEntitlementGroupMember2, str2, localAccount, localLocale, (Limits)localObject12, (Limits)localObject14, (Limits)localObject13);
                if (i2 != 0) {
                  return this.taskErrorURL;
                }
              }
            }
            else
            {
              localEntitlement = (Entitlement)localObject18;
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject10).add(localObject18);
              if (!localArrayList1.contains(str2))
              {
                setLimitRestrictions(paramHttpServletRequest, i1, localEntitlementGroup, str2, localEntitlementGroupMember2, localAccount, localLocale, (Limits)localObject12);
                if (i2 != 0) {
                  return this.taskErrorURL;
                }
              }
            }
          }
        }
        saveEntLimitRestrictions((com.ffusion.csil.beans.entitlements.Entitlements)localObject9, localEntitlementGroupMember1, localEntitlementGroupMember2, localBusinessEmployee1, localSecureUser, localLocale, (com.ffusion.csil.beans.entitlements.Entitlements)localObject10, (Limits)localObject12, (Limits)localObject13, (Limits)localObject14);
        if (this.error != 0) {
          return this.serviceErrorURL;
        }
        resetSessionNames();
      }
      else
      {
        this.error = 4503;
        return super.getTaskErrorURL();
      }
    }
    localHttpSession.removeAttribute("LastRequest");
    return super.getSuccessURL();
  }
  
  protected void saveEntLimitRestrictions(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements1, EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, BusinessEmployee paramBusinessEmployee, SecureUser paramSecureUser, Locale paramLocale, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements2, Limits paramLimits1, Limits paramLimits2, Limits paramLimits3)
  {
    try
    {
      Object localObject1;
      String str1;
      EntitlementTypePropertyList localEntitlementTypePropertyList;
      LocalizableProperty localLocalizableProperty1;
      String str2;
      LocalizableProperty localLocalizableProperty2;
      Object localObject2;
      if (paramEntitlements1.size() > 0)
      {
        com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramEntitlements1);
        for (i = 0; i < paramEntitlements1.size(); i++)
        {
          localObject1 = (Entitlement)paramEntitlements1.get(i);
          str1 = ((Entitlement)localObject1).getOperationName();
          localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str1);
          localLocalizableProperty1 = new LocalizableProperty("display name", localEntitlementTypePropertyList.getPropertiesMap(), str1);
          str2 = ((Entitlement)localObject1).getObjectType();
          localLocalizableProperty2 = null;
          if (str2 != null)
          {
            localObject2 = null;
            try
            {
              localObject2 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties(str2);
            }
            catch (CSILException localCSILException2) {}
            if (localObject2 != null) {
              localLocalizableProperty2 = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject2).getPropertiesMap(), str2);
            }
          }
          Object[] arrayOfObject1;
          if ((str2 != null) && (((Entitlement)localObject1).getObjectId() != null))
          {
            arrayOfObject1 = new Object[4];
            arrayOfObject1[0] = paramBusinessEmployee.getName();
            arrayOfObject1[1] = localLocalizableProperty1;
            arrayOfObject1[2] = str2;
            arrayOfObject1[3] = ((Entitlement)localObject1).getObjectId();
            localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessUser_3.2", arrayOfObject1);
          }
          else
          {
            arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = paramBusinessEmployee.getName();
            arrayOfObject1[1] = localLocalizableProperty1;
            localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessUser_3.1", arrayOfObject1);
          }
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID(), 3225);
        }
      }
      if (paramEntitlements2.size() > 0)
      {
        com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramEntitlements2);
        for (i = 0; i < paramEntitlements2.size(); i++)
        {
          localObject1 = (Entitlement)paramEntitlements2.get(i);
          str1 = ((Entitlement)localObject1).getOperationName();
          localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str1);
          localLocalizableProperty1 = new LocalizableProperty("display name", localEntitlementTypePropertyList.getPropertiesMap(), str1);
          str2 = ((Entitlement)localObject1).getObjectType();
          localLocalizableProperty2 = null;
          if (str2 != null)
          {
            localObject2 = null;
            try
            {
              localObject2 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties(str2);
            }
            catch (CSILException localCSILException3) {}
            if (localObject2 != null) {
              localLocalizableProperty2 = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject2).getPropertiesMap(), str2);
            }
          }
          Object[] arrayOfObject2;
          if ((str2 != null) && (((Entitlement)localObject1).getObjectId() != null))
          {
            arrayOfObject2 = new Object[4];
            arrayOfObject2[0] = paramBusinessEmployee.getName();
            arrayOfObject2[1] = localLocalizableProperty1;
            arrayOfObject2[2] = str2;
            arrayOfObject2[3] = ((Entitlement)localObject1).getObjectId();
            localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessUser_4.2", arrayOfObject2);
          }
          else
          {
            arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = paramBusinessEmployee.getName();
            arrayOfObject2[1] = localLocalizableProperty1;
            localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessUser_4.1", arrayOfObject2);
          }
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID(), 3225);
        }
      }
      String str3;
      for (int i = 0; i < paramLimits1.size(); i++)
      {
        localObject1 = (Limit)paramLimits1.get(i);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember1, (Limit)localObject1);
        str1 = ((Limit)localObject1).getEntitlement().getOperationName();
        localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str1);
        localLocalizableProperty1 = new LocalizableProperty("display name", localEntitlementTypePropertyList.getPropertiesMap(), str1);
        str2 = ((Limit)localObject1).getEntitlement().getObjectType();
        localLocalizableProperty2 = null;
        if (str2 != null)
        {
          localObject2 = null;
          try
          {
            localObject2 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties(str2);
          }
          catch (CSILException localCSILException4) {}
          if (localObject2 != null) {
            localLocalizableProperty2 = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject2).getPropertiesMap(), str2);
          }
        }
        Object localObject3;
        if ((((Limit)localObject1).getEntitlement().getObjectType() != null) && (((Limit)localObject1).getEntitlement().getObjectId() != null))
        {
          localObject3 = new Object[6];
          localObject3[0] = paramBusinessEmployee.getName();
          localObject3[1] = localLocalizableProperty1;
          localObject3[2] = ((Limit)localObject1).getAmount();
          localObject3[5] = ((Limit)localObject1).getCurrencyCode();
          localObject3[3] = localLocalizableProperty2;
          localObject3[4] = ((Limit)localObject1).getEntitlement().getObjectId();
          str3 = "AuditMessage_EditBusinessUser" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_5.2";
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", str3, (Object[])localObject3);
        }
        else
        {
          localObject3 = new Object[4];
          localObject3[0] = paramBusinessEmployee.getName();
          localObject3[1] = localLocalizableProperty1;
          localObject3[2] = ((Limit)localObject1).getAmount();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          str3 = "AuditMessage_EditBusinessUser" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_5.1";
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", str3, (Object[])localObject3);
        }
        try
        {
          localObject3 = new Currency(((Limit)localObject1).getData(), paramLocale, paramLocale.getISO3Country());
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID(), 3225, (Currency)localObject3);
        }
        catch (Exception localException1)
        {
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID(), 3225);
        }
      }
      for (i = 0; i < paramLimits2.size(); i++)
      {
        localObject1 = (Limit)paramLimits2.get(i);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember1, (Limit)paramLimits2.get(i));
        str1 = ((Limit)localObject1).getEntitlement().getOperationName();
        localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str1);
        localLocalizableProperty1 = new LocalizableProperty("display name", localEntitlementTypePropertyList.getPropertiesMap(), str1);
        str2 = ((Limit)localObject1).getEntitlement().getObjectType();
        localLocalizableProperty2 = null;
        if (str2 != null)
        {
          localObject2 = null;
          try
          {
            localObject2 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties(str2);
          }
          catch (CSILException localCSILException5) {}
          if (localObject2 != null) {
            localLocalizableProperty2 = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject2).getPropertiesMap(), str2);
          }
        }
        Object localObject4;
        if ((((Limit)localObject1).getEntitlement().getObjectType() != null) && (((Limit)localObject1).getEntitlement().getObjectId() != null))
        {
          localObject4 = new Object[6];
          localObject4[0] = paramBusinessEmployee.getName();
          localObject4[1] = localLocalizableProperty1;
          localObject4[2] = ((Limit)localObject1).getAmount();
          localObject4[5] = ((Limit)localObject1).getCurrencyCode();
          localObject4[3] = localLocalizableProperty2;
          localObject4[4] = ((Limit)localObject1).getEntitlement().getObjectId();
          str3 = "AuditMessage_EditBusinessUser" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_6.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            str3 = str3 + "_xcurr";
          }
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", str3, (Object[])localObject4);
        }
        else
        {
          localObject4 = new Object[4];
          localObject4[0] = paramBusinessEmployee.getName();
          localObject4[1] = localLocalizableProperty1;
          localObject4[2] = ((Limit)localObject1).getAmount();
          localObject4[3] = ((Limit)localObject1).getCurrencyCode();
          str3 = "AuditMessage_EditBusinessUser" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_6.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            str3 = str3 + "_xcurr";
          }
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", str3, (Object[])localObject4);
        }
        try
        {
          localObject4 = new Currency(((Limit)localObject1).getData(), paramLocale, paramLocale.getISO3Country());
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID(), 3225, (Currency)localObject4);
        }
        catch (Exception localException2)
        {
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID(), 3225);
        }
      }
      for (i = 0; i < paramLimits3.size(); i++)
      {
        localObject1 = (Limit)paramLimits3.get(i);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember1, (Limit)paramLimits3.get(i));
        str1 = ((Limit)localObject1).getEntitlement().getOperationName();
        localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str1);
        localLocalizableProperty1 = new LocalizableProperty("display name", localEntitlementTypePropertyList.getPropertiesMap(), str1);
        str2 = ((Limit)localObject1).getEntitlement().getObjectType();
        localLocalizableProperty2 = null;
        if (str2 != null)
        {
          localObject2 = null;
          try
          {
            localObject2 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties(str2);
          }
          catch (CSILException localCSILException6) {}
          if (localObject2 != null) {
            localLocalizableProperty2 = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject2).getPropertiesMap(), str2);
          }
        }
        Object localObject5;
        if ((((Limit)localObject1).getEntitlement().getObjectType() != null) && (((Limit)localObject1).getEntitlement().getObjectId() != null))
        {
          localObject5 = new Object[4];
          localObject5[0] = paramBusinessEmployee.getName();
          localObject5[1] = localLocalizableProperty1;
          localObject5[2] = localLocalizableProperty2;
          localObject5[3] = ((Limit)localObject1).getEntitlement().getObjectId();
          str3 = "AuditMessage_EditBusinessUser" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_7.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            str3 = str3 + "_xcurr";
          }
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", str3, (Object[])localObject5);
        }
        else
        {
          localObject5 = new Object[2];
          localObject5[0] = paramBusinessEmployee.getName();
          localObject5[1] = localLocalizableProperty1;
          str3 = "AuditMessage_EditBusinessUser" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_7.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            str3 = str3 + "_xcurr";
          }
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", str3, (Object[])localObject5);
        }
        try
        {
          localObject5 = new Currency(((Limit)localObject1).getData(), paramLocale, paramLocale.getISO3Country());
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID(), 3225, (Currency)localObject5);
        }
        catch (Exception localException3)
        {
          Initialize.audit(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID(), 3225);
        }
      }
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      resetSessionNames();
    }
  }
  
  public void setSourcePage(String paramString)
  {
    this.YQ = paramString;
  }
  
  protected String modifyACHCompanyEntitlements(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HttpSession paramHttpSession, EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
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
        StringBuffer localStringBuffer = new StringBuffer();
        Object localObject2 = localHashSet.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          str2 = (String)((Iterator)localObject2).next();
          localObject1 = new Entitlement(null, "ACHCompany", str2);
          if (localStringBuffer.length() == 0) {
            localStringBuffer.append("'" + str2 + "'");
          } else {
            localStringBuffer.append(", '" + str2 + "'");
          }
          if (!localEntitlements.contains(localObject1)) {
            localEntitlements.add(localObject1);
          }
        }
        com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1, localEntitlements);
        localObject2 = new StringBuffer();
        if (localStringBuffer.length() != 0)
        {
          ((StringBuffer)localObject2).append("Restricted ACHCompany(s) " + localStringBuffer);
          ((StringBuffer)localObject2).append(" for " + paramEntitlementGroup.getEntGroupType().toLowerCase() + " '");
          ((StringBuffer)localObject2).append(paramEntitlementGroup.getGroupName());
          ((StringBuffer)localObject2).append("'.");
        }
        else
        {
          ((StringBuffer)localObject2).append("Remove restriction on all ACHCompanies for '");
          ((StringBuffer)localObject2).append(paramEntitlementGroup.getEntGroupType().toLowerCase() + " '");
          ((StringBuffer)localObject2).append(paramEntitlementGroup.getGroupName());
          ((StringBuffer)localObject2).append("'.");
        }
        String str3 = TrackingIDGenerator.GetNextID();
        Initialize.audit(paramSecureUser, ((StringBuffer)localObject2).toString(), str3, 3225);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setCheckGroupAccountsName(String paramString)
  {
    this.YR = paramString;
  }
  
  public void setGroupAccountsName(String paramString)
  {
    this.YH = paramString;
  }
  
  public void setGroupName(String paramString)
  {
    this._groupName = paramString;
  }
  
  public void setOldBusinessName(String paramString)
  {
    this.YJ = paramString;
  }
  
  public void setNotAccountSpecificOperations(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    String str = null;
    this._operations = new ArrayList();
    while (localStringTokenizer.hasMoreTokens())
    {
      str = localStringTokenizer.nextToken();
      this._operations.add(str);
    }
  }
  
  public void setMinimumUserNameLength(int paramInt)
  {
    this.Y5 = paramInt;
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      this.YX = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.YX = 1;
    }
  }
  
  public void setMinimumPasswordLength(int paramInt)
  {
    this.YT = paramInt;
  }
  
  public void setMinimumPasswordLength(String paramString)
  {
    this.YT = Integer.parseInt(paramString);
  }
  
  public void setMinimumNumbersInPassword(int paramInt)
  {
    this.Y2 = paramInt;
  }
  
  public void setMinimumNumbersInPassword(String paramString)
  {
    this.Y2 = Integer.parseInt(paramString);
  }
  
  public void setMinimumLettersInPassword(int paramInt)
  {
    this.YK = paramInt;
  }
  
  public void setMinimumLettersInPassword(String paramString)
  {
    this.YK = Integer.parseInt(paramString);
  }
  
  public void setAllowSpecialChars(boolean paramBoolean)
  {
    this.Y6 = paramBoolean;
  }
  
  public void setAllowSpecialChars(String paramString)
  {
    this.Y6 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setACHCompaniesInSessionName(String paramString)
  {
    this._achCompaniesName = paramString;
  }
  
  private boolean jdMethod_case(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      return true;
    }
    if (paramString1.length() > 14) {
      return false;
    }
    Phone localPhone = new Phone(paramString1.trim(), true);
    String str = Util.validatePhoneFormat(paramSecureUser, paramString2, localPhone.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    if (!str.equals(""))
    {
      localPhone.setFormat(str);
      return true;
    }
    return true;
  }
  
  private boolean jdMethod_byte(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      return true;
    }
    if (paramString1.length() > 11) {
      return false;
    }
    ZipCode localZipCode = new ZipCode(paramString1.trim(), true);
    String str = Util.validateZipCodeFormat(paramSecureUser, paramString2, localZipCode.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    if (!str.equals(""))
    {
      localZipCode.setFormat(str);
      return true;
    }
    return true;
  }
  
  private boolean G(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return true;
    }
    if ((paramString.length() < "X@Y.Z".length()) || (paramString.length() > 40)) {
      return false;
    }
    if ((paramString.charAt(0) == '@') || (paramString.charAt(0) == '.') || (paramString.charAt(paramString.length() - 1) == '.') || (paramString.substring(paramString.length() - 3).indexOf('@') >= 0)) {
      return false;
    }
    int i = 0;
    for (int j = 1; j < paramString.length() - 3; j++) {
      if (paramString.charAt(j) == '@') {
        i++;
      }
    }
    return i == 1;
  }
  
  protected boolean validatePassword()
  {
    int i = PasswordUtil.validatePassword(this.Y0, this.YX);
    if (i != 0)
    {
      this.error = i;
      return false;
    }
    return true;
  }
  
  private boolean jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    int i = 0;
    User localUser1 = new User();
    EntitlementGroupMembers localEntitlementGroupMembers = com.ffusion.csil.core.Entitlements.getMembers(paramEntitlementGroupMember.getEntitlementGroupId());
    Iterator localIterator1 = localEntitlementGroupMembers.iterator();
    while (localIterator1.hasNext())
    {
      localEntitlementGroupMember = (EntitlementGroupMember)localIterator1.next();
      localUser1.setId(localEntitlementGroupMember.getId());
      localObject1 = UserAdmin.getUserById(paramSecureUser, localUser1, paramHashMap);
      if (((User)localObject1).getAccountStatus().equals(User.STATUS_ACTIVE)) {
        i++;
      }
    }
    localEntitlementGroupMembers = null;
    EntitlementGroupMember localEntitlementGroupMember = null;
    localIterator1 = null;
    Object localObject1 = com.ffusion.csil.core.Entitlements.getAdminInfoFor(paramEntitlementGroupMember);
    ((EntitlementAdmins)localObject1).addAll(com.ffusion.csil.core.Entitlements.getAdminInfoFor(paramEntitlementGroupMember.getEntitlementGroupId()));
    Iterator localIterator2 = ((EntitlementAdmins)localObject1).iterator();
    while (localIterator2.hasNext())
    {
      EntitlementAdmin localEntitlementAdmin1 = (EntitlementAdmin)localIterator2.next();
      if (localEntitlementAdmin1.canAdminister())
      {
        EntitlementAdmins localEntitlementAdmins = com.ffusion.csil.core.Entitlements.getAdminsForGroup(localEntitlementAdmin1.getTargetGroupId());
        Iterator localIterator3 = localEntitlementAdmins.iterator();
        int j = 0;
        while (localIterator3.hasNext())
        {
          EntitlementAdmin localEntitlementAdmin2 = (EntitlementAdmin)localIterator3.next();
          Object localObject2;
          if (localEntitlementAdmin2.getGranteeMemberType() != null)
          {
            localUser1.setId(localEntitlementAdmin2.getGranteeMemberId());
            localObject2 = UserAdmin.getUserById(paramSecureUser, localUser1, paramHashMap);
            if (!((User)localObject2).getAccountStatus().equals(User.STATUS_ACTIVE)) {}
          }
          else if (localEntitlementAdmin2.getGranteeGroupId() != paramEntitlementGroupMember.getEntitlementGroupId())
          {
            if (localEntitlementAdmin2.getGranteeMemberType() != null)
            {
              j = 1;
              break;
            }
            localObject2 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(localEntitlementAdmin2.getGranteeGroupId());
            if ((((EntitlementGroup)localObject2).getEntGroupType().equals("Business")) || (((EntitlementGroup)localObject2).getEntGroupType().equals("Division")) || (((EntitlementGroup)localObject2).getEntGroupType().equals("Group")))
            {
              localEntitlementGroupMembers = com.ffusion.csil.core.Entitlements.getMembers(localEntitlementAdmin2.getGranteeGroupId());
              localIterator1 = localEntitlementGroupMembers.iterator();
              while (localIterator1.hasNext())
              {
                localEntitlementGroupMember = (EntitlementGroupMember)localIterator1.next();
                localUser1.setId(localEntitlementGroupMember.getId());
                User localUser2 = UserAdmin.getUserById(paramSecureUser, localUser1, paramHashMap);
                if (localUser2.getAccountStatus().equals(User.STATUS_ACTIVE))
                {
                  j = 1;
                  break;
                }
              }
            }
          }
          else if (localEntitlementAdmin2.getGranteeMemberType() == null)
          {
            if (i > 1)
            {
              j = 1;
              break;
            }
          }
          else if ((!paramEntitlementGroupMember.getMemberType().equals(localEntitlementAdmin2.getGranteeMemberType())) || (!paramEntitlementGroupMember.getMemberSubType().equals(localEntitlementAdmin2.getGranteeMemberSubType())) || (!paramEntitlementGroupMember.getId().equals(localEntitlementAdmin2.getGranteeMemberId())))
          {
            j = 1;
            break;
          }
        }
        if (j == 0) {
          return true;
        }
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.EditBusinessUser
 * JD-Core Version:    0.7.0.1
 */