package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.TaskUtil;
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

public class EditAccountGroupAccessPermissions
  extends BaseTask
  implements AdminTask
{
  public static final String ADMIN = "admin";
  public static final String ACCOUNT_GROUP = "account_group";
  public static final String TRANSACTION_LIMIT = "transaction_limit";
  public static final String TRANSACTION_EXCEED = "transaction_exceed";
  public static final String DAY_LIMIT = "day_limit";
  public static final String DAY_EXCEED = "day_exceed";
  public static final String WEEK_LIMIT = "week_limit";
  public static final String WEEK_EXCEED = "week_exceed";
  public static final String MONTH_LIMIT = "month_limit";
  public static final String MONTH_EXCEED = "month_exceed";
  private String Xx = "AccountGroups";
  private int Xw;
  private String Xo;
  private String XG;
  private String XD;
  private String Xu;
  private String XL;
  protected boolean _initOnly = false;
  com.ffusion.csil.beans.entitlements.Entitlements Xp = null;
  Limits XI = null;
  ArrayList Xr = null;
  ArrayList XC = null;
  Limits XK = null;
  Limits XF = null;
  Limits Xq = null;
  ArrayList XE = null;
  private BusinessAccountGroups Xn = null;
  private BusinessAccountGroups Xv = null;
  private boolean XA = true;
  private static final String XN = "com.ffusion.util.logging.audit.task";
  private static final String Xy = "AuditMessage_EditAccountGroupAccessPermissions";
  private static final String XB = "_1";
  private static final String XJ = "_2";
  private static final String XH = "_3";
  private static final String Xz = "_4";
  private static final String Xs = "_5";
  private static final String Xt = "_entfault_1";
  private static final String XM = "_xcurr";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    int i = !"false".equals(this.XL) ? 1 : 0;
    if (i != 0) {
      localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    }
    if (localEntitlementGroupMember1 == null)
    {
      try
      {
        localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
        localEntitlementGroupMember1 = com.ffusion.csil.core.Entitlements.getMember(localEntitlementGroupMember1);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        return super.getServiceErrorURL();
      }
      localHttpSession.setAttribute("EntitlementGroupMember", localEntitlementGroupMember1);
    }
    EntitlementGroupMember localEntitlementGroupMember2 = null;
    if (this.XG != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setMemberType(this.XG);
      localEntitlementGroupMember2.setMemberSubType(this.XD);
      localEntitlementGroupMember2.setId(this.Xo);
      localEntitlementGroupMember2.setEntitlementGroupId(this.Xw);
    }
    int j = 1;
    if (this._initOnly) {
      j = this.XC == null ? 1 : 0;
    }
    if ((!this._initOnly) || (j != 0))
    {
      str = jdMethod_for(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember1, null);
      if (this.error != 0) {
        return str;
      }
    }
    if ((!this._initOnly) || (j == 0))
    {
      try
      {
        EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
        if (localSecureUser.getUserType() == 2)
        {
          EntitlementGroupMember localEntitlementGroupMember3 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          if (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember3, new Entitlement("BusinessProfileEdit", null, null)))
          {
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditAccountGroupAccessPermissions_entfault_1", null);
            Initialize.logEntitlementFault(localSecureUser, localLocalizableString, com.ffusion.util.logging.TrackingIDGenerator.GetNextID());
            throw new CSILException("EditAccountGroupAccessPermissions", 20001);
          }
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        str = this.serviceErrorURL;
        return str;
      }
      HistoryTracker localHistoryTracker = EditAccountPermissions.jdMethod_new(localSecureUser, "EditAccountGroupAccessPermissions", this.Xw, this.Xo, this.Xu);
      str = jdMethod_int(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember1, localHistoryTracker);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for EditAccountGroupAccessPermissions: " + localProfileException.toString());
      }
    }
    if ((i != 0) && (this.error == 0)) {
      localHttpSession.removeAttribute("LastRequest");
    }
    return str;
  }
  
  private String jdMethod_for(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    this.Xp = null;
    this.XI = null;
    this.Xr = new ArrayList();
    this.XC = new ArrayList();
    this.XK = new Limits();
    this.XF = new Limits();
    this.Xq = new Limits();
    this.XE = new ArrayList();
    this.Xn = new BusinessAccountGroups();
    this.Xv = new BusinessAccountGroups();
    int[] arrayOfInt = { 1, 2, 3, 4 };
    try
    {
      int i;
      if (paramEntitlementGroupMember1 == null)
      {
        this.Xp = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, this.Xw);
        this.XI = com.ffusion.csil.core.Entitlements.getGroupLimits(this.Xw);
        localObject1 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.Xw);
        i = ((EntitlementGroup)localObject1).getParentId();
      }
      else
      {
        this.Xp = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1);
        this.XI = com.ffusion.csil.core.Entitlements.getGroupLimits(paramEntitlementGroupMember1);
        i = paramEntitlementGroupMember1.getEntitlementGroupId();
      }
      Object localObject1 = (BusinessAccountGroups)paramHttpSession.getAttribute(this.Xx);
      if (localObject1 == null)
      {
        this.error = 98;
        return this.taskErrorURL;
      }
      HashSet localHashSet1 = new HashSet(((BusinessAccountGroups)localObject1).size() * 4 / 3 + 1);
      HashSet localHashSet2 = new HashSet(((BusinessAccountGroups)localObject1).size() * 4 / 3 + 1);
      String str2 = null;
      String str3 = null;
      Object localObject2 = ((BusinessAccountGroups)localObject1).listIterator();
      while (((ListIterator)localObject2).hasNext())
      {
        str2 = EntitlementsUtil.getEntitlementObjectId((BusinessAccountGroup)((ListIterator)localObject2).next());
        localHashSet1.add(str2);
        localHashSet2.add(str2);
      }
      localObject2 = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      EntitlementTypePropertyList localEntitlementTypePropertyList = ((EntitlementTypePropertyLists)localObject2).getByOperationName("Access");
      String str4 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
      int k;
      Object localObject4;
      Entitlement localEntitlement2;
      for (int j = 0; j < ((BusinessAccountGroups)localObject1).size(); j++)
      {
        str2 = (String)paramHttpSession.getAttribute("account_group" + j);
        str3 = (String)paramHttpSession.getAttribute("admin" + j);
        localObject3 = (BusinessAccountGroup)((BusinessAccountGroups)localObject1).get(j);
        boolean bool1 = new Boolean((String)((BusinessAccountGroup)localObject3).get("CanAdminRow")).booleanValue();
        k = new Boolean((String)((BusinessAccountGroup)localObject3).get("CanInitRow")).booleanValue();
        localObject4 = new Entitlement(str4, "AccountGroup", EntitlementsUtil.getEntitlementObjectId((BusinessAccountGroup)((BusinessAccountGroups)localObject1).get(j)));
        if (bool1)
        {
          if (str3 == null)
          {
            if (!this.Xp.contains(localObject4)) {
              this.Xr.add(localObject4);
            }
          }
          else
          {
            localHashSet2.remove(str3);
            if (this.Xp.contains(localObject4))
            {
              this.XC.add(localObject4);
              this.Xv.add(localObject3);
            }
            this.Xp.remove(localObject4);
          }
        }
        else {
          localHashSet2.remove(((Entitlement)localObject4).getObjectId());
        }
        localEntitlement2 = new Entitlement("Access", "AccountGroup", EntitlementsUtil.getEntitlementObjectId((BusinessAccountGroup)((BusinessAccountGroups)localObject1).get(j)));
        if (k != 0)
        {
          if (str2 == null)
          {
            if (!this.Xp.contains(localEntitlement2)) {
              this.Xr.add(localEntitlement2);
            }
          }
          else
          {
            localHashSet1.remove(str2);
            if (this.Xp.contains(localEntitlement2))
            {
              this.XC.add(localEntitlement2);
              this.Xn.add(localObject3);
            }
            this.Xp.remove(localEntitlement2);
          }
        }
        else {
          localHashSet1.remove(localEntitlement2.getObjectId());
        }
        if (k != 0)
        {
          String[] arrayOfString1 = { (String)paramHttpSession.getAttribute("transaction_limit" + j), (String)paramHttpSession.getAttribute("day_limit" + j), (String)paramHttpSession.getAttribute("week_limit" + j), (String)paramHttpSession.getAttribute("month_limit" + j) };
          String[] arrayOfString2 = { (String)paramHttpSession.getAttribute("transaction_exceed" + j), (String)paramHttpSession.getAttribute("day_exceed" + j), (String)paramHttpSession.getAttribute("week_exceed" + j), (String)paramHttpSession.getAttribute("month_exceed" + j) };
          for (int m = 0; m < arrayOfInt.length; m++)
          {
            this.error = TaskUtil.validateLimitAmount(arrayOfString1[m]);
            if (this.error != 0) {
              return this.taskErrorURL;
            }
            boolean bool2;
            if (arrayOfString2[m] == null) {
              bool2 = false;
            } else {
              bool2 = arrayOfString2[m].equalsIgnoreCase("true");
            }
            Limit localLimit1 = new Limit(this.Xw, arrayOfInt[m], arrayOfString1[m], localEntitlement2, bool2);
            int n = setLimitCurrencyInformation(localLimit1);
            if (n != 0)
            {
              this.error = n;
              return this.serviceErrorURL;
            }
            if (paramEntitlementGroupMember1 == null)
            {
              localLimit1.setRunningTotalType('G');
            }
            else
            {
              localLimit1.setMemberType(paramEntitlementGroupMember1.getMemberType());
              localLimit1.setMemberSubType(paramEntitlementGroupMember1.getMemberSubType());
              localLimit1.setMemberId(paramEntitlementGroupMember1.getId());
              localLimit1.setRunningTotalType('U');
            }
            int i1 = 0;
            for (int i2 = 0; i2 < this.XI.size(); i2++)
            {
              Limit localLimit2 = (Limit)this.XI.get(i2);
              if (localLimit2.isLimitInfoIdentical(localLimit1))
              {
                if ((arrayOfString1[m] == null) || (arrayOfString1[m].equals("")))
                {
                  this.Xq.add(localLimit2);
                }
                else if ((!arrayOfString1[m].equals(localLimit2.getData())) || (!localLimit1.getAllowApproval().equals(localLimit2.getAllowApproval())))
                {
                  this.XE.add(localLimit2.getData());
                  localLimit2.setData(arrayOfString1[m]);
                  localLimit2.setAllowApproval(localLimit1.isAllowedApproval());
                  this.XF.add(localLimit2);
                }
                i1 = 1;
                break;
              }
            }
            if ((i1 == 0) && (arrayOfString1[m] != null) && (!arrayOfString1[m].equals(""))) {
              this.XK.add(localLimit1);
            }
          }
        }
      }
      Entitlement localEntitlement1 = null;
      Object localObject3 = new StringBuffer();
      Iterator localIterator = localHashSet1.iterator();
      while (localIterator.hasNext())
      {
        str2 = (String)localIterator.next();
        localEntitlement1 = new Entitlement("Access", "AccountGroup", str2);
        if (((StringBuffer)localObject3).length() == 0) {
          ((StringBuffer)localObject3).append("'" + str2 + "'");
        } else {
          ((StringBuffer)localObject3).append(", '" + str2 + "'");
        }
        if (!this.Xp.contains(localEntitlement1)) {
          this.Xp.add(localEntitlement1);
        }
        for (k = 0; k < this.XI.size(); k++)
        {
          localObject4 = (Limit)this.XI.get(k);
          localEntitlement2 = ((Limit)localObject4).getEntitlement();
          if (localEntitlement1.equals(localEntitlement2)) {
            this.Xq.add(localObject4);
          }
        }
      }
      localEntitlement1 = null;
      localObject3 = new StringBuffer();
      localIterator = localHashSet2.iterator();
      while (localIterator.hasNext())
      {
        str2 = (String)localIterator.next();
        localEntitlement1 = new Entitlement(str4, "AccountGroup", str2);
        if (((StringBuffer)localObject3).length() == 0) {
          ((StringBuffer)localObject3).append("'" + str2 + "'");
        } else {
          ((StringBuffer)localObject3).append(", '" + str2 + "'");
        }
        if (!this.Xp.contains(localEntitlement1)) {
          this.Xp.add(localEntitlement1);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  private String jdMethod_int(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    try
    {
      String str2 = (String)paramHttpSession.getAttribute("Section");
      String str3 = (String)paramHttpSession.getAttribute("Context");
      if (str2 == null)
      {
        this.error = 4560;
        return this.taskErrorURL;
      }
      if (str3 == null)
      {
        this.error = 4561;
        return this.taskErrorURL;
      }
      int i;
      if (paramSecureUser.getUserType() == 2)
      {
        Business localBusiness = (Business)paramHttpSession.getAttribute("ModifyBusiness");
        i = localBusiness.getIdValue();
      }
      else
      {
        i = paramSecureUser.getBusinessID();
      }
      Object localObject1;
      Object localObject2;
      Object localObject3;
      String str4;
      LocalizableString localLocalizableString;
      if (this.Xr.size() + this.XC.size() != 0)
      {
        if (paramEntitlementGroupMember1 == null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, this.Xw, this.Xp);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1, this.Xp);
        }
        for (j = 0; j < this.Xr.size(); j++)
        {
          localObject1 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localObject2 = (Entitlement)this.Xr.get(j);
          localObject3 = new Object[2];
          localObject3[0] = str3;
          localObject3[1] = ((Entitlement)localObject2).getObjectId();
          str4 = "AuditMessage_EditAccountGroupAccessPermissions" + getContextAuditKey(str2) + "_1";
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str4, (Object[])localObject3);
          Initialize.audit(paramSecureUser, localLocalizableString, i, (String)localObject1, 3225);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localObject2, null);
        }
        for (j = 0; j < this.XC.size(); j++)
        {
          localObject1 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localObject2 = (Entitlement)this.XC.get(j);
          localObject3 = new Object[2];
          localObject3[0] = str3;
          localObject3[1] = ((Entitlement)localObject2).getObjectId();
          str4 = "AuditMessage_EditAccountGroupAccessPermissions" + getContextAuditKey(str2) + "_2";
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str4, (Object[])localObject3);
          Initialize.audit(paramSecureUser, localLocalizableString, i, (String)localObject1, 3225);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localObject2, null);
        }
      }
      for (int j = 0; j < this.XK.size(); j++)
      {
        localObject1 = (Limit)this.XK.get(j);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember2, (Limit)localObject1);
        localObject2 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject3 = new Object[4];
        localObject3[0] = ((Limit)localObject1).getData();
        localObject3[3] = ((Limit)localObject1).getCurrencyCode();
        localObject3[1] = str3;
        localObject3[2] = ((Limit)localObject1).getObjectId();
        str4 = "AuditMessage_EditAccountGroupAccessPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3";
        if (((Limit)localObject1).isCrossCurrency()) {
          str4 = str4 + "_xcurr";
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str4, (Object[])localObject3);
        Initialize.audit(paramSecureUser, localLocalizableString, i, (String)localObject2, 3225);
        paramHistoryTracker.logLimitAdd((Limit)localObject1, null);
      }
      for (j = 0; j < this.XF.size(); j++)
      {
        localObject1 = (Limit)this.XF.get(j);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember2, (Limit)this.XF.get(j));
        localObject2 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject3 = new Object[4];
        localObject3[0] = ((Limit)localObject1).getData();
        localObject3[3] = ((Limit)localObject1).getCurrencyCode();
        localObject3[1] = str3;
        localObject3[2] = ((Limit)localObject1).getObjectId();
        str4 = "AuditMessage_EditAccountGroupAccessPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4";
        if (((Limit)localObject1).isCrossCurrency()) {
          str4 = str4 + "_xcurr";
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str4, (Object[])localObject3);
        Initialize.audit(paramSecureUser, localLocalizableString, i, (String)localObject2, 3225);
        paramHistoryTracker.logLimitChange((Limit)localObject1, null, (String)this.XE.get(j));
      }
      for (j = 0; j < this.Xq.size(); j++)
      {
        localObject1 = (Limit)this.Xq.get(j);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember2, (Limit)this.Xq.get(j));
        localObject2 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject3 = new Object[4];
        localObject3[0] = ((Limit)localObject1).getData();
        localObject3[3] = ((Limit)localObject1).getCurrencyCode();
        localObject3[1] = str3;
        localObject3[2] = ((Limit)localObject1).getObjectId();
        str4 = "AuditMessage_EditAccountGroupAccessPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5";
        if (((Limit)localObject1).isCrossCurrency()) {
          str4 = str4 + "_xcurr";
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str4, (Object[])localObject3);
        Initialize.audit(paramSecureUser, localLocalizableString, i, (String)localObject2, 3225);
        paramHistoryTracker.logLimitDelete((Limit)localObject1, null);
      }
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
      localEntitlements.addAll(this.XC);
      if (paramEntitlementGroupMember1 == null) {
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.Xw), localEntitlements, 6, this.XA, new HashMap());
      }
      if (!this.XA)
      {
        localObject1 = new HashMap();
        if (paramEntitlementGroupMember1 == null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.Xw);
          localObject2 = AutoEntitleUtil.buildPerAccountEntitlementsList(this.Xn, EntitlementsUtil.getTypePropertyCategoryValue(((EntitlementGroup)localObject3).getEntGroupType()));
          AutoEntitleAdmin.restrictFeatures(paramSecureUser, (EntitlementGroup)localObject3, (com.ffusion.csil.beans.entitlements.Entitlements)localObject2, (HashMap)localObject1);
        }
        else
        {
          localObject2 = AutoEntitleUtil.buildPerAccountEntitlementsList(this.Xn, "per user");
          AutoEntitleAdmin.restrictFeatures(paramSecureUser, paramEntitlementGroupMember1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject2, (HashMap)localObject1);
          localObject3 = AutoEntitleUtil.buildPerAccountAdminEntitlementsList(this.Xv, "per user");
          AutoEntitleAdmin.restrictFeatures(paramSecureUser, paramEntitlementGroupMember1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject3, (HashMap)localObject1);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    this.Xp = null;
    this.XI = null;
    this.Xr = null;
    this.XC = null;
    this.XK = null;
    this.XF = null;
    this.Xq = null;
    this.XE = null;
    return str1;
  }
  
  protected String getPeriodAuditKey(int paramInt)
  {
    if (paramInt == 1) {
      return "_trans";
    }
    if (paramInt == 2) {
      return "_day";
    }
    if (paramInt == 3) {
      return "_week";
    }
    if (paramInt == 4) {
      return "_month";
    }
    return null;
  }
  
  protected String getContextAuditKey(String paramString)
  {
    if (paramString.equals("Business")) {
      return "_business";
    }
    if (paramString.equals("Company")) {
      return "_company";
    }
    if (paramString.equals("Divisions")) {
      return "_division";
    }
    if (paramString.equals("Groups")) {
      return "_group";
    }
    return "_default";
  }
  
  public void setMemberId(String paramString)
  {
    this.Xo = paramString;
  }
  
  public String getMemberId()
  {
    return this.Xo;
  }
  
  public void setMemberType(String paramString)
  {
    this.XG = paramString;
  }
  
  public String getMemberType()
  {
    return this.XG;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.XD = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.XD;
  }
  
  public void setGroupId(String paramString)
  {
    this.Xw = Integer.parseInt(paramString);
  }
  
  public void setAccountGroupsName(String paramString)
  {
    this.Xx = paramString;
  }
  
  public void setProfileId(String paramString)
  {
    this.Xu = paramString;
  }
  
  public void setSaveLastRequest(String paramString)
  {
    this.XL = paramString;
  }
  
  public void setAutoEntitle(String paramString)
  {
    this.XA = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitle()
  {
    return String.valueOf(this.XA);
  }
  
  public void setInitOnly(String paramString)
  {
    this._initOnly = Boolean.valueOf(paramString).booleanValue();
    this.Xp = null;
    this.XI = null;
    this.Xr = null;
    this.XC = null;
    this.XK = null;
    this.XF = null;
    this.Xq = null;
    this.XE = null;
  }
  
  public String getInitOnly()
  {
    return new Boolean(this._initOnly).toString();
  }
  
  public String getNumGrantedAccountGroups()
  {
    return Integer.toString(this.XC.size());
  }
  
  protected int setLimitCurrencyInformation(Limit paramLimit)
  {
    String str = Util.getLimitBaseCurrency();
    paramLimit.setCurrencyCode(str);
    paramLimit.setCrossCurrency(true);
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.EditAccountGroupAccessPermissions
 * JD-Core Version:    0.7.0.1
 */