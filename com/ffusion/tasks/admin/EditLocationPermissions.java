package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
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
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.TaskUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableProperty;
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

public class EditLocationPermissions
  extends BaseTask
  implements AdminTask
{
  protected boolean _initOnly = false;
  com.ffusion.csil.beans.entitlements.Entitlements aaV = null;
  Limits abm = null;
  com.ffusion.csil.beans.entitlements.Entitlements aa9 = null;
  com.ffusion.csil.beans.entitlements.Entitlements aa4 = null;
  Limits abo = null;
  Limits abg = null;
  Limits aaY = null;
  ArrayList abe = null;
  EntitlementTypePropertyLists abk = null;
  protected boolean _autoEntitle = true;
  public static final String ENTITLEMENT = "entitlement";
  public static final String ADMIN = "admin";
  public static final String TRANSACTION_LIMIT = "transaction_limit";
  public static final String TRANSACTION_EXCEED = "transaction_exceed";
  public static final String DAY_LIMIT = "day_limit";
  public static final String DAY_EXCEED = "day_exceed";
  public static final String WEEK_LIMIT = "week_limit";
  public static final String WEEK_EXCEED = "week_exceed";
  public static final String MONTH_LIMIT = "month_limit";
  public static final String MONTH_EXCEED = "month_exceed";
  private String aaT;
  private String abi;
  private String abd;
  private int aa3;
  private String aaS;
  private String aa1;
  private String abq;
  private String abl = "LocationEntitlementsWithLimits";
  private String aa2 = "LocationEntitlementsWithoutLimits";
  private String abc = "";
  protected String _listName;
  private static final String abt = "com.ffusion.util.logging.audit.task";
  private static final String aa8 = "AuditMessage_EditLocationPermissions";
  private static final String abb = "_1.1";
  private static final String aaX = "_1.2";
  private static final String aa5 = "_2.1";
  private static final String abs = "_2.2";
  private static final String abj = "_3.1";
  private static final String abh = "_3.2";
  private static final String abf = "_3.3";
  private static final String aa6 = "_3.4";
  private static final String abp = "_4.1";
  private static final String abn = "_4.2";
  private static final String aa0 = "_4.3";
  private static final String aaW = "_4.4";
  private static final String aaZ = "_5.1";
  private static final String aaU = "_5.2";
  private static final String aba = "_5.3";
  private static final String aa7 = "_5.4";
  private static final String abr = "_xcurr";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this._listName);
    if (localEntitlementTypePropertyLists == null)
    {
      this.error = 4549;
      return super.getTaskErrorURL();
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    int i = !"false".equals(this.abq) ? 1 : 0;
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
    if (this.abi != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setMemberType(this.abi);
      localEntitlementGroupMember2.setMemberSubType(this.abd);
      localEntitlementGroupMember2.setId(this.aaT);
      localEntitlementGroupMember2.setEntitlementGroupId(this.aa3);
    }
    int j = 1;
    if (this._initOnly) {
      j = this.aa4 == null ? 1 : 0;
    }
    if ((!this._initOnly) || (j != 0))
    {
      str = modifyAccountEntitlementsPhase1(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementTypePropertyLists, localEntitlementGroupMember1, null);
      if (this.error != 0) {
        return str;
      }
    }
    if ((!this._initOnly) || (j == 0))
    {
      try
      {
        EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        str = this.serviceErrorURL;
        return str;
      }
      HistoryTracker localHistoryTracker = jdMethod_int(localSecureUser, "EditAccountPermissions", this.aa3, this.aaT, this.aa1);
      str = modifyAccountEntitlementsPhase2(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementTypePropertyLists, localEntitlementGroupMember1, localHistoryTracker);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for EditAccountPermissions: " + localProfileException.toString());
      }
    }
    if ((i != 0) && (this.error == 0)) {
      localHttpSession.removeAttribute("LastRequest");
    }
    return str;
  }
  
  static HistoryTracker jdMethod_int(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2, String paramString3)
  {
    int i = 0;
    String str = null;
    if (paramString2 == null)
    {
      str = Integer.toString(paramInt);
      try
      {
        EntitlementGroup localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(paramInt);
        if ("Division".equals(localEntitlementGroup.getEntGroupType())) {
          i = 6;
        } else if ("Group".equals(localEntitlementGroup.getEntGroupType())) {
          i = 7;
        } else if ("Business".equals(localEntitlementGroup.getEntGroupType())) {
          i = 2;
        }
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.SEVERE, "Failed to get history object type for " + paramString1 + " with id=\"" + paramInt + "\": " + localException.toString());
      }
    }
    else
    {
      i = 1;
      str = paramString2;
    }
    if (paramString3 != null) {
      str = paramString3;
    }
    return new HistoryTracker(paramSecureUser, i, str);
  }
  
  protected String modifyAccountEntitlementsPhase1(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    this.aaV = null;
    this.abm = null;
    this.aa9 = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.aa4 = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.abo = new Limits();
    this.abg = new Limits();
    this.aaY = new Limits();
    this.abe = new ArrayList();
    int[] arrayOfInt = { 1, 2, 3, 4 };
    try
    {
      int i = (this.abc != null) && (!this.abc.equals("")) ? 1 : 0;
      int j;
      if (paramEntitlementGroupMember1 == null)
      {
        this.aaV = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, this.aa3);
        this.abm = com.ffusion.csil.core.Entitlements.getGroupLimits(this.aa3);
        localObject1 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.aa3);
        j = ((EntitlementGroup)localObject1).getParentId();
      }
      else
      {
        this.aaV = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1);
        this.abm = com.ffusion.csil.core.Entitlements.getGroupLimits(paramEntitlementGroupMember1);
        j = paramEntitlementGroupMember1.getEntitlementGroupId();
      }
      Object localObject1 = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.abl);
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.aa2);
      this.abk = null;
      int k = 0;
      int m = 0;
      if (i == 0)
      {
        this.abk = ((EntitlementTypePropertyLists)((EntitlementTypePropertyLists)localObject1).clone());
        this.abk.addAll(localEntitlementTypePropertyLists);
        k = ((EntitlementTypePropertyLists)localObject1).size();
        m = k + localEntitlementTypePropertyLists.size();
      }
      else
      {
        this.abk = ((EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.abc));
        m = this.abk.size();
      }
      HashSet localHashSet = new HashSet(m * 4 / 3 + 1);
      String str2 = null;
      String str3 = null;
      ListIterator localListIterator = this.abk.listIterator();
      while (localListIterator.hasNext())
      {
        localObject2 = (EntitlementTypePropertyList)localListIterator.next();
        localHashSet.add(((EntitlementTypePropertyList)localObject2).getOperationName());
        if (((EntitlementTypePropertyList)localObject2).isPropertySet("admin partner")) {
          localHashSet.add(((EntitlementTypePropertyList)localObject2).getPropertyValue("admin partner", 0));
        }
      }
      Object localObject4;
      for (int n = 0; n < m; n++)
      {
        str2 = (String)paramHttpSession.getAttribute("entitlement" + n);
        str3 = (String)paramHttpSession.getAttribute("admin" + n);
        localObject2 = new Entitlement(((EntitlementTypePropertyList)this.abk.get(n)).getOperationName(), getObjectType(), this.aaS);
        localObject3 = new Entitlement(((EntitlementTypePropertyList)this.abk.get(n)).getPropertyValue("admin partner", 0), getObjectType(), this.aaS);
        if (new Boolean(paramEntitlementTypePropertyLists.getByOperationName(((EntitlementTypePropertyList)this.abk.get(n)).getOperationName()).getPropertyValue("CanAdminRow", 0)).booleanValue())
        {
          if (str3 == null)
          {
            if (!this.aaV.contains(localObject3)) {
              this.aa9.add(localObject3);
            }
          }
          else
          {
            localHashSet.remove(str3);
            if (this.aaV.contains(localObject3)) {
              this.aa4.add(localObject3);
            }
          }
          this.aaV.remove(localObject3);
        }
        else
        {
          localHashSet.remove(((Entitlement)localObject3).getOperationName());
        }
        if (new Boolean(paramEntitlementTypePropertyLists.getByOperationName(((EntitlementTypePropertyList)this.abk.get(n)).getOperationName()).getPropertyValue("CanInitRow", 0)).booleanValue())
        {
          if (str2 == null)
          {
            if (!this.aaV.contains(localObject2)) {
              this.aa9.add(localObject2);
            }
          }
          else
          {
            localHashSet.remove(str2);
            if (this.aaV.contains(localObject2)) {
              this.aa4.add(localObject2);
            }
          }
        }
        else {
          localHashSet.remove(str2);
        }
        this.aaV.remove(localObject2);
        String[] arrayOfString = { (String)paramHttpSession.getAttribute("transaction_limit" + n), (String)paramHttpSession.getAttribute("day_limit" + n), (String)paramHttpSession.getAttribute("week_limit" + n), (String)paramHttpSession.getAttribute("month_limit" + n) };
        localObject4 = new String[] { (String)paramHttpSession.getAttribute("transaction_exceed" + n), (String)paramHttpSession.getAttribute("day_exceed" + n), (String)paramHttpSession.getAttribute("week_exceed" + n), (String)paramHttpSession.getAttribute("month_exceed" + n) };
        if ((n < k) || ((i != 0) && (((EntitlementTypePropertyList)this.abk.get(n)).isPropertySet("isLimit")))) {
          for (int i2 = 0; i2 < arrayOfInt.length; i2++)
          {
            this.error = TaskUtil.validateLimitAmount(arrayOfString[i2]);
            if (this.error != 0) {
              return this.taskErrorURL;
            }
            boolean bool;
            if (localObject4[i2] == null) {
              bool = false;
            } else {
              bool = localObject4[i2].equalsIgnoreCase("true");
            }
            Limit localLimit1 = new Limit(this.aa3, arrayOfInt[i2], arrayOfString[i2], (Entitlement)localObject2, bool);
            int i3 = setLimitCurrencyInformation(localLimit1);
            if (i3 != 0)
            {
              this.error = i3;
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
            int i4 = 0;
            for (int i5 = 0; i5 < this.abm.size(); i5++)
            {
              Limit localLimit2 = (Limit)this.abm.get(i5);
              if (localLimit2.isLimitInfoIdentical(localLimit1))
              {
                if ((arrayOfString[i2] == null) || (arrayOfString[i2].equals("")))
                {
                  this.aaY.add(localLimit2);
                }
                else if ((!arrayOfString[i2].equals(localLimit2.getData())) || (!localLimit1.getAllowApproval().equals(localLimit2.getAllowApproval())))
                {
                  this.abe.add(localLimit2.getData());
                  localLimit2.setData(arrayOfString[i2]);
                  localLimit2.setAllowApproval(localLimit1.isAllowedApproval());
                  this.abg.add(localLimit2);
                }
                i4 = 1;
                break;
              }
            }
            if ((i4 == 0) && (arrayOfString[i2] != null) && (!arrayOfString[i2].equals(""))) {
              this.abo.add(localLimit1);
            }
          }
        }
      }
      Entitlement localEntitlement1 = null;
      Object localObject2 = new StringBuffer();
      Object localObject3 = localHashSet.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        str2 = (String)((Iterator)localObject3).next();
        localEntitlement1 = new Entitlement(str2, getObjectType(), this.aaS);
        if (((StringBuffer)localObject2).length() == 0) {
          ((StringBuffer)localObject2).append("'" + str2 + "'");
        } else {
          ((StringBuffer)localObject2).append(", '" + str2 + "'");
        }
        if (!this.aaV.contains(localEntitlement1)) {
          this.aaV.add(localEntitlement1);
        }
        for (int i1 = 0; i1 < this.abm.size(); i1++)
        {
          localObject4 = (Limit)this.abm.get(i1);
          Entitlement localEntitlement2 = ((Limit)localObject4).getEntitlement();
          if (localEntitlement1.equals(localEntitlement2)) {
            this.aaY.add(localObject4);
          }
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
  
  protected String modifyAccountEntitlementsPhase2(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
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
      String str4;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      if (this.aa9.size() + this.aa4.size() != 0)
      {
        if (paramEntitlementGroupMember1 == null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, this.aa3, this.aaV);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1, this.aaV);
        }
        String str5;
        LocalizableString localLocalizableString;
        for (j = 0; j < this.aa9.size(); j++)
        {
          localObject1 = (Entitlement)this.aa9.get(j);
          str4 = TrackingIDGenerator.GetNextID();
          localObject2 = ((Entitlement)localObject1).getOperationName();
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localObject4 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject3).getPropertiesMap(), (String)localObject2);
          if (((Entitlement)localObject1).getObjectId() == null)
          {
            localObject5 = new Object[2];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            str5 = "AuditMessage_EditLocationPermissions" + getContextAuditKey(str2) + "_1.1";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          else
          {
            localObject5 = new Object[3];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            localObject5[2] = ((Entitlement)localObject1).getObjectId();
            str5 = "AuditMessage_EditLocationPermissions" + getContextAuditKey(str2) + "_1.2";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, i, str4, 3225);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localObject1, this.abk);
        }
        for (j = 0; j < this.aa4.size(); j++)
        {
          localObject1 = (Entitlement)this.aa4.get(j);
          str4 = TrackingIDGenerator.GetNextID();
          localObject2 = ((Entitlement)localObject1).getOperationName();
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localObject4 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject3).getPropertiesMap(), (String)localObject2);
          if (((Entitlement)localObject1).getObjectId() == null)
          {
            localObject5 = new Object[2];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            str5 = "AuditMessage_EditLocationPermissions" + getContextAuditKey(str2) + "_2.1";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          else
          {
            localObject5 = new Object[3];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            localObject5[2] = ((Entitlement)localObject1).getObjectId();
            str5 = "AuditMessage_EditLocationPermissions" + getContextAuditKey(str2) + "_2.2";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, i, str4, 3225);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localObject1, this.abk);
        }
      }
      for (int j = 0; j < this.abo.size(); j++)
      {
        localObject1 = (Limit)this.abo.get(j);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember2, (Limit)localObject1);
        str4 = TrackingIDGenerator.GetNextID();
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[3];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[2] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else
        {
          localObject3 = new Object[5];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[4] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject3[3] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitAdd((Limit)localObject1, this.abk);
      }
      for (j = 0; j < this.abg.size(); j++)
      {
        localObject1 = (Limit)this.abg.get(j);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember2, (Limit)this.abg.get(j));
        str4 = TrackingIDGenerator.GetNextID();
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[3];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[2] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else
        {
          localObject3 = new Object[5];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[4] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject3[3] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitChange((Limit)localObject1, this.abk, (String)this.abe.get(j));
      }
      for (j = 0; j < this.aaY.size(); j++)
      {
        localObject1 = (Limit)this.aaY.get(j);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember2, (Limit)this.aaY.get(j));
        str4 = TrackingIDGenerator.GetNextID();
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[3];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[2] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else
        {
          localObject3 = new Object[5];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[4] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject3[3] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditLocationPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitDelete((Limit)localObject1, this.abk);
      }
      if (paramEntitlementGroupMember1 == null) {
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.aa3), this.aa4, 4, this._autoEntitle, new HashMap());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    this.aaV = null;
    this.abm = null;
    this.aa9 = null;
    this.aa4 = null;
    this.abo = null;
    this.abg = null;
    this.aaY = null;
    this.abe = null;
    this.abk = null;
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
    this.aaT = paramString;
  }
  
  public String getMemberId()
  {
    return this.aaT;
  }
  
  public void setMemberType(String paramString)
  {
    this.abi = paramString;
  }
  
  public String getMemberType()
  {
    return this.abi;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.abd = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.abd;
  }
  
  public void setEntitlementTypesWithLimits(String paramString)
  {
    this.abl = paramString;
  }
  
  public void setEntitlementTypesWithoutLimits(String paramString)
  {
    this.aa2 = paramString;
  }
  
  public void setEntitlementTypesMerged(String paramString)
  {
    this.abc = paramString;
  }
  
  public void setLocationBPWID(String paramString)
  {
    this.aaS = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.aa3 = Integer.parseInt(paramString);
  }
  
  public void setProfileId(String paramString)
  {
    this.aa1 = paramString;
  }
  
  protected String getObjectType()
  {
    return "Location";
  }
  
  public void setSaveLastRequest(String paramString)
  {
    this.abq = paramString;
  }
  
  public void setEntitlementTypePropertyLists(String paramString)
  {
    this._listName = paramString;
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
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    Object localObject7 = null;
    Object localObject8 = null;
    Object localObject9 = null;
  }
  
  public String getInitOnly()
  {
    return new Boolean(this._initOnly).toString();
  }
  
  public String getNumGrantedEntitlements()
  {
    return Integer.toString(this.aa4.size());
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
 * Qualified Name:     com.ffusion.tasks.admin.EditLocationPermissions
 * JD-Core Version:    0.7.0.1
 */