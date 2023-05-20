package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.beans.util.UtilException;
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
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableAccountID;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditAdapter;
import com.ffusion.util.logging.AuditLog;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditAccountAccessPermissions
  extends BaseTask
  implements AdminTask
{
  protected boolean _initOnly = false;
  com.ffusion.csil.beans.entitlements.Entitlements adt = null;
  private ArrayList adK = null;
  private ArrayList adv = null;
  private Limits adL = null;
  private Limits adG = null;
  private Limits adu = null;
  private ArrayList adF = null;
  private Accounts adr = null;
  private Accounts adD = null;
  protected boolean _autoEntitle = true;
  public static final String ADMIN = "admin";
  public static final String ACCOUNT = "account";
  public static final String TRANSACTION_LIMIT = "transaction_limit";
  public static final String TRANSACTION_EXCEED = "transaction_exceed";
  public static final String DAY_LIMIT = "day_limit";
  public static final String DAY_EXCEED = "day_exceed";
  public static final String WEEK_LIMIT = "week_limit";
  public static final String WEEK_EXCEED = "week_exceed";
  public static final String MONTH_LIMIT = "month_limit";
  public static final String MONTH_EXCEED = "month_exceed";
  private String adN = "Accounts";
  private int adz;
  private String ads;
  private String adH;
  private String adE;
  private String ady;
  private String adM;
  protected boolean _enrollingBusiness = false;
  private static final String adP = "com.ffusion.util.logging.audit.task";
  private static final String adA = "AuditMessage_EditAccountAccessPermissions";
  private static final String adC = "_1";
  private static final String adJ = "_2";
  private static final String adI = "_3";
  private static final String adB = "_4";
  private static final String adw = "_5";
  private static final String adx = "_entfault_1";
  private static final String adO = "_xcurr";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    int i = !"false".equals(this.adM) ? 1 : 0;
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
    if (this.adH != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setMemberType(this.adH);
      localEntitlementGroupMember2.setMemberSubType(this.adE);
      localEntitlementGroupMember2.setId(this.ads);
      localEntitlementGroupMember2.setEntitlementGroupId(this.adz);
    }
    int j = 1;
    if (this._initOnly) {
      j = this.adv == null ? 1 : 0;
    }
    if ((!this._initOnly) || (j != 0))
    {
      str = jdMethod_byte(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember1, null);
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
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditAccountAccessPermissions_entfault_1", null);
            Initialize.logEntitlementFault(localSecureUser, localLocalizableString, com.ffusion.util.logging.TrackingIDGenerator.GetNextID());
            throw new CSILException("EditAccountAccessPermissions", 20001);
          }
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        str = this.serviceErrorURL;
        return str;
      }
      HistoryTracker localHistoryTracker = EditAccountPermissions.jdMethod_new(localSecureUser, "EditAccountAccessPermissions", this.adz, this.ads, this.ady);
      str = jdMethod_try(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember1, localHistoryTracker);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for EditAccountAccessPermissions: " + localProfileException.toString());
      }
    }
    if ((i != 0) && (this.error == 0)) {
      localHttpSession.removeAttribute("LastRequest");
    }
    return str;
  }
  
  private String jdMethod_byte(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    this.adt = null;
    Limits localLimits = null;
    this.adK = new ArrayList();
    this.adv = new ArrayList();
    this.adL = new Limits();
    this.adG = new Limits();
    this.adu = new Limits();
    this.adF = new ArrayList();
    this.adr = new Accounts();
    this.adD = new Accounts();
    int[] arrayOfInt = { 1, 2, 3, 4 };
    try
    {
      int i;
      if (paramEntitlementGroupMember1 == null)
      {
        this.adt = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, this.adz);
        localLimits = com.ffusion.csil.core.Entitlements.getGroupLimits(this.adz);
        localObject1 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.adz);
        i = ((EntitlementGroup)localObject1).getParentId();
      }
      else
      {
        this.adt = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1);
        localLimits = com.ffusion.csil.core.Entitlements.getGroupLimits(paramEntitlementGroupMember1);
        i = paramEntitlementGroupMember1.getEntitlementGroupId();
      }
      Object localObject1 = (Accounts)paramHttpSession.getAttribute(this.adN);
      if (localObject1 == null)
      {
        this.error = 39;
        return this.taskErrorURL;
      }
      HashSet localHashSet1 = new HashSet(((Accounts)localObject1).size() * 4 / 3 + 1);
      HashSet localHashSet2 = new HashSet(((Accounts)localObject1).size() * 4 / 3 + 1);
      String str2 = null;
      String str3 = null;
      Object localObject2 = ((Accounts)localObject1).listIterator();
      while (((ListIterator)localObject2).hasNext())
      {
        str2 = EntitlementsUtil.getEntitlementObjectId((Account)((ListIterator)localObject2).next());
        localHashSet1.add(str2);
        localHashSet2.add(str2);
      }
      localObject2 = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      EntitlementTypePropertyList localEntitlementTypePropertyList = ((EntitlementTypePropertyLists)localObject2).getByOperationName("Access");
      String str4 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
      int k;
      Object localObject4;
      Entitlement localEntitlement2;
      for (int j = 0; j < ((Accounts)localObject1).size(); j++)
      {
        str2 = (String)paramHttpSession.getAttribute("account" + j);
        str3 = (String)paramHttpSession.getAttribute("admin" + j);
        localObject3 = (Account)((Accounts)localObject1).get(j);
        boolean bool1 = new Boolean((String)((Account)localObject3).get("CanAdminRow")).booleanValue();
        k = new Boolean((String)((Account)localObject3).get("CanInitRow")).booleanValue();
        localObject4 = new Entitlement(str4, "Account", EntitlementsUtil.getEntitlementObjectId((Account)((Accounts)localObject1).get(j)));
        if (bool1)
        {
          if (str3 == null)
          {
            if (!this.adt.contains(localObject4)) {
              this.adK.add(localObject4);
            }
          }
          else
          {
            localHashSet2.remove(str3);
            if (this.adt.contains(localObject4))
            {
              this.adv.add(localObject4);
              this.adD.add(localObject3);
            }
            this.adt.remove(localObject4);
          }
        }
        else {
          localHashSet2.remove(((Entitlement)localObject4).getObjectId());
        }
        localEntitlement2 = new Entitlement("Access", "Account", EntitlementsUtil.getEntitlementObjectId((Account)((Accounts)localObject1).get(j)));
        if (k != 0)
        {
          if (str2 == null)
          {
            if (!this.adt.contains(localEntitlement2)) {
              this.adK.add(localEntitlement2);
            }
          }
          else
          {
            localHashSet1.remove(str2);
            if (this.adt.contains(localEntitlement2))
            {
              this.adv.add(localEntitlement2);
              this.adr.add(localObject3);
            }
            this.adt.remove(localEntitlement2);
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
            Limit localLimit1 = new Limit(this.adz, arrayOfInt[m], arrayOfString1[m], localEntitlement2, bool2);
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
            for (int i2 = 0; i2 < localLimits.size(); i2++)
            {
              Limit localLimit2 = (Limit)localLimits.get(i2);
              if (localLimit2.isLimitInfoIdentical(localLimit1))
              {
                if ((arrayOfString1[m] == null) || (arrayOfString1[m].equals("")))
                {
                  this.adu.add(localLimit2);
                }
                else if ((!arrayOfString1[m].equals(localLimit2.getData())) || (!localLimit1.getAllowApproval().equals(localLimit2.getAllowApproval())))
                {
                  this.adF.add(localLimit2.getData());
                  localLimit2.setData(arrayOfString1[m]);
                  localLimit2.setAllowApproval(localLimit1.isAllowedApproval());
                  this.adG.add(localLimit2);
                }
                i1 = 1;
                break;
              }
            }
            if ((i1 == 0) && (arrayOfString1[m] != null) && (!arrayOfString1[m].equals(""))) {
              this.adL.add(localLimit1);
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
        localEntitlement1 = new Entitlement("Access", "Account", str2);
        if (((StringBuffer)localObject3).length() == 0) {
          ((StringBuffer)localObject3).append("'" + str2 + "'");
        } else {
          ((StringBuffer)localObject3).append(", '" + str2 + "'");
        }
        if (!this.adt.contains(localEntitlement1)) {
          this.adt.add(localEntitlement1);
        }
        for (k = 0; k < localLimits.size(); k++)
        {
          localObject4 = (Limit)localLimits.get(k);
          localEntitlement2 = ((Limit)localObject4).getEntitlement();
          if (localEntitlement1.equals(localEntitlement2)) {
            this.adu.add(localObject4);
          }
        }
      }
      localEntitlement1 = null;
      localObject3 = new StringBuffer();
      localIterator = localHashSet2.iterator();
      while (localIterator.hasNext())
      {
        str2 = (String)localIterator.next();
        localEntitlement1 = new Entitlement(str4, "Account", str2);
        if (((StringBuffer)localObject3).length() == 0) {
          ((StringBuffer)localObject3).append("'" + str2 + "'");
        } else {
          ((StringBuffer)localObject3).append(", '" + str2 + "'");
        }
        if (!this.adt.contains(localEntitlement1)) {
          this.adt.add(localEntitlement1);
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
  
  private String jdMethod_try(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
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
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object[] arrayOfObject;
      LocalizableString localLocalizableString;
      if (this.adK.size() + this.adv.size() != 0)
      {
        if (paramEntitlementGroupMember1 == null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, this.adz, this.adt);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1, this.adt);
        }
        for (j = 0; j < this.adK.size(); j++)
        {
          localObject2 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localObject3 = null;
          localObject4 = (Entitlement)this.adK.get(j);
          arrayOfObject = new Object[2];
          arrayOfObject[0] = str3;
          try
          {
            localObject3 = AccountUtil.buildLocalizableAccountID(((Entitlement)localObject4).getObjectId());
            arrayOfObject[1] = localObject3;
          }
          catch (UtilException localUtilException1)
          {
            DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Entitlement)localObject4).getObjectId());
            localUtilException1.printStackTrace();
            arrayOfObject[1] = ((Entitlement)localObject4).getObjectId();
          }
          String str4 = "AuditMessage_EditAccountAccessPermissions" + getContextAuditKey(str2) + "_1";
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str4, arrayOfObject);
          jdMethod_int(paramSecureUser, localLocalizableString, i, (String)localObject2, 3225, (LocalizableAccountID)localObject3);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localObject4, null);
        }
        for (j = 0; j < this.adv.size(); j++)
        {
          localObject2 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localObject3 = null;
          localObject4 = (Entitlement)this.adv.get(j);
          arrayOfObject = new Object[2];
          arrayOfObject[0] = str3;
          try
          {
            localObject3 = AccountUtil.buildLocalizableAccountID(((Entitlement)localObject4).getObjectId());
            arrayOfObject[1] = localObject3;
          }
          catch (UtilException localUtilException2)
          {
            DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Entitlement)localObject4).getObjectId());
            localUtilException2.printStackTrace();
            arrayOfObject[1] = ((Entitlement)localObject4).getObjectId();
          }
          String str5 = "AuditMessage_EditAccountAccessPermissions" + getContextAuditKey(str2) + "_2";
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, arrayOfObject);
          jdMethod_int(paramSecureUser, localLocalizableString, i, (String)localObject2, 3225, (LocalizableAccountID)localObject3);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localObject4, null);
        }
      }
      for (int j = 0; j < this.adL.size(); j++)
      {
        localObject2 = (Limit)this.adL.get(j);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember2, (Limit)localObject2);
        localObject3 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject4 = null;
        arrayOfObject = new Object[4];
        arrayOfObject[0] = ((Limit)localObject2).getData();
        arrayOfObject[3] = ((Limit)localObject2).getCurrencyCode();
        arrayOfObject[1] = str3;
        try
        {
          localObject4 = AccountUtil.buildLocalizableAccountID(((Limit)localObject2).getObjectId());
          arrayOfObject[2] = localObject4;
        }
        catch (UtilException localUtilException3)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Limit)localObject2).getObjectId());
          localUtilException3.printStackTrace();
          arrayOfObject[2] = ((Limit)localObject2).getObjectId();
        }
        String str6 = "AuditMessage_EditAccountAccessPermissions" + getPeriodAuditKey(((Limit)localObject2).getPeriod()) + getContextAuditKey(str2) + "_3";
        if (((Limit)localObject2).isCrossCurrency()) {
          str6 = str6 + "_xcurr";
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str6, arrayOfObject);
        jdMethod_int(paramSecureUser, localLocalizableString, i, (String)localObject3, 3225, (LocalizableAccountID)localObject4);
        paramHistoryTracker.logLimitAdd((Limit)localObject2, null);
      }
      for (j = 0; j < this.adG.size(); j++)
      {
        localObject2 = (Limit)this.adG.get(j);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember2, (Limit)this.adG.get(j));
        localObject3 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject4 = null;
        arrayOfObject = new Object[4];
        arrayOfObject[0] = ((Limit)localObject2).getData();
        arrayOfObject[3] = ((Limit)localObject2).getCurrencyCode();
        arrayOfObject[1] = str3;
        try
        {
          localObject4 = AccountUtil.buildLocalizableAccountID(((Limit)localObject2).getObjectId());
          arrayOfObject[2] = localObject4;
        }
        catch (UtilException localUtilException4)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Limit)localObject2).getObjectId());
          localUtilException4.printStackTrace();
          arrayOfObject[2] = ((Limit)localObject2).getObjectId();
        }
        String str7 = "AuditMessage_EditAccountAccessPermissions" + getPeriodAuditKey(((Limit)localObject2).getPeriod()) + getContextAuditKey(str2) + "_4";
        if (((Limit)localObject2).isCrossCurrency()) {
          str7 = str7 + "_xcurr";
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str7, arrayOfObject);
        jdMethod_int(paramSecureUser, localLocalizableString, i, (String)localObject3, 3225, (LocalizableAccountID)localObject4);
        paramHistoryTracker.logLimitChange((Limit)localObject2, null, (String)this.adF.get(j));
      }
      for (j = 0; j < this.adu.size(); j++)
      {
        localObject2 = (Limit)this.adu.get(j);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember2, (Limit)this.adu.get(j));
        localObject3 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject4 = null;
        arrayOfObject = new Object[4];
        arrayOfObject[0] = ((Limit)localObject2).getData();
        arrayOfObject[3] = ((Limit)localObject2).getCurrencyCode();
        arrayOfObject[1] = str3;
        try
        {
          localObject4 = AccountUtil.buildLocalizableAccountID(((Limit)localObject2).getObjectId());
          arrayOfObject[2] = localObject4;
        }
        catch (UtilException localUtilException5)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Limit)localObject2).getObjectId());
          localUtilException5.printStackTrace();
          arrayOfObject[2] = ((Limit)localObject2).getObjectId();
        }
        String str8 = "AuditMessage_EditAccountAccessPermissions" + getPeriodAuditKey(((Limit)localObject2).getPeriod()) + getContextAuditKey(str2) + "_5";
        if (((Limit)localObject2).isCrossCurrency()) {
          str8 = str8 + "_xcurr";
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str8, arrayOfObject);
        jdMethod_int(paramSecureUser, localLocalizableString, i, (String)localObject3, 3225, (LocalizableAccountID)localObject4);
        paramHistoryTracker.logLimitDelete((Limit)localObject2, null);
      }
      Object localObject1;
      if ((paramEntitlementGroupMember1 == null) && (!this._enrollingBusiness))
      {
        localObject1 = new com.ffusion.csil.beans.entitlements.Entitlements();
        for (int k = 0; k < this.adv.size(); k++)
        {
          localObject3 = (Entitlement)this.adv.get(k);
          ((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).add(localObject3);
        }
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.adz), (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, 1, this._autoEntitle, new HashMap());
      }
      if ((!this._autoEntitle) && (!this._enrollingBusiness))
      {
        localObject1 = new HashMap();
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements;
        if (paramEntitlementGroupMember1 == null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.adz);
          localEntitlements = AutoEntitleUtil.buildPerAccountEntitlementsList(this.adr, EntitlementsUtil.getTypePropertyCategoryValue(((EntitlementGroup)localObject3).getEntGroupType()));
          AutoEntitleAdmin.restrictFeatures(paramSecureUser, (EntitlementGroup)localObject3, localEntitlements, (HashMap)localObject1);
        }
        else
        {
          localEntitlements = AutoEntitleUtil.buildPerAccountEntitlementsList(this.adr, "per user");
          AutoEntitleAdmin.restrictFeatures(paramSecureUser, paramEntitlementGroupMember1, localEntitlements, (HashMap)localObject1);
          localObject3 = AutoEntitleUtil.buildPerAccountAdminEntitlementsList(this.adD, "per user");
          AutoEntitleAdmin.restrictFeatures(paramSecureUser, paramEntitlementGroupMember1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject3, (HashMap)localObject1);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    this.adt = null;
    this.adK = null;
    this.adv = null;
    this.adL = null;
    this.adG = null;
    this.adu = null;
    this.adF = null;
    return str1;
  }
  
  private void jdMethod_int(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt1, String paramString, int paramInt2, LocalizableAccountID paramLocalizableAccountID)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
    }
    String str4 = null;
    String str5 = null;
    if (paramLocalizableAccountID != null)
    {
      ArrayList localArrayList = AuditAdapter.getLogLanguages();
      String str6 = (String)paramLocalizableAccountID.localize((Locale)localArrayList.get(0));
      try
      {
        str4 = AccountUtil.getAccountInfoFromAccountDisplayText(str6);
        str5 = AccountUtil.getRoutingNumberFromAccountDisplayText(str6);
      }
      catch (UtilException localUtilException)
      {
        DebugLog.log(Level.WARNING, localUtilException.getMessage());
        localUtilException.printStackTrace();
      }
    }
    AuditLog.log(str1, str2, str3, paramILocalizable, paramString, 3225, paramInt1, null, null, null, null, null, null, str4, str5);
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
    this.ads = paramString;
  }
  
  public String getMemberId()
  {
    return this.ads;
  }
  
  public void setMemberType(String paramString)
  {
    this.adH = paramString;
  }
  
  public String getMemberType()
  {
    return this.adH;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.adE = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.adE;
  }
  
  public void setGroupId(String paramString)
  {
    this.adz = Integer.parseInt(paramString);
  }
  
  public void setAccountsName(String paramString)
  {
    this.adN = paramString;
  }
  
  public void setProfileId(String paramString)
  {
    this.ady = paramString;
  }
  
  public void setSaveLastRequest(String paramString)
  {
    this.adM = paramString;
  }
  
  public void setAutoEntitle(String paramString)
  {
    this._autoEntitle = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitle()
  {
    return new Boolean(this._autoEntitle).toString();
  }
  
  public void setInitOnly(String paramString)
  {
    this._initOnly = Boolean.valueOf(paramString).booleanValue();
    this.adt = null;
    this.adK = null;
    this.adv = null;
    this.adL = null;
    this.adG = null;
    this.adu = null;
    this.adF = null;
  }
  
  public String getInitOnly()
  {
    return new Boolean(this._initOnly).toString();
  }
  
  public String getNumGrantedAccounts()
  {
    return Integer.toString(this.adv.size());
  }
  
  public void setEnrollingBusiness(String paramString)
  {
    this._enrollingBusiness = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getEnrollingBusiness()
  {
    return this._enrollingBusiness;
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
 * Qualified Name:     com.ffusion.tasks.admin.EditAccountAccessPermissions
 * JD-Core Version:    0.7.0.1
 */