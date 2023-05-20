package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
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

public class EditAccountGroupPermissions
  extends BaseTask
  implements AdminTask
{
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
  private String Zo;
  private String ZO;
  private String ZK;
  private int Zx;
  private String Zq;
  private String Zv;
  private String ZW;
  private String ZQ = "NonAccountEntitlementsWithLimits";
  private String Zw = "NonAccountEntitlementsWithoutLimits";
  private String ZJ = "";
  protected String _listName;
  protected boolean _initOnly = false;
  com.ffusion.csil.beans.entitlements.Entitlements Zp = null;
  Limits ZS = null;
  com.ffusion.csil.beans.entitlements.Entitlements ZE = null;
  com.ffusion.csil.beans.entitlements.Entitlements Zz = null;
  Limits ZT = null;
  Limits ZM = null;
  Limits Zr = null;
  ArrayList ZL = null;
  EntitlementTypePropertyLists ZP = null;
  EntitlementTypePropertyLists Zy = null;
  private boolean ZG = true;
  private static final String Z0 = "com.ffusion.util.logging.audit.task";
  private static final String ZD = "AuditMessage_EditAccountGroupPermissions";
  private static final String ZH = "_1.1";
  private static final String ZU = "_1.2";
  private static final String ZR = "_2.1";
  private static final String ZI = "_2.2";
  private static final String ZN = "_3.1";
  private static final String ZC = "_3.2";
  private static final String ZX = "_3.3";
  private static final String ZV = "_3.4";
  private static final String ZF = "_4.1";
  private static final String ZA = "_4.2";
  private static final String Zm = "_4.3";
  private static final String ZZ = "_4.4";
  private static final String Zt = "_5.1";
  private static final String Zn = "_5.2";
  private static final String ZB = "_5.3";
  private static final String Zs = "_5.4";
  private static final String Zu = "_entfault_1";
  private static final String ZY = "_xcurr";
  
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
    int i = !"false".equals(this.ZW) ? 1 : 0;
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
    if (this.ZO != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setMemberType(this.ZO);
      localEntitlementGroupMember2.setMemberSubType(this.ZK);
      localEntitlementGroupMember2.setId(this.Zo);
      localEntitlementGroupMember2.setEntitlementGroupId(this.Zx);
    }
    int j = 1;
    if (this._initOnly) {
      j = this.Zz == null ? 1 : 0;
    }
    if ((!this._initOnly) || (j != 0))
    {
      str = modifyAccountGroupEntitlementsPhase1(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementTypePropertyLists, localEntitlementGroupMember1, null);
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
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditAccountGroupPermissions_entfault_1", null);
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
      HistoryTracker localHistoryTracker = EditAccountPermissions.jdMethod_new(localSecureUser, "EditAccountGroupPermissions", this.Zx, this.Zo, this.Zv);
      str = modifyAccountGroupEntitlementsPhase2(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementTypePropertyLists, localEntitlementGroupMember1, localHistoryTracker);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for EditAccountGroupPermissions: " + localProfileException.toString());
      }
    }
    if ((i != 0) && (this.error == 0)) {
      localHttpSession.removeAttribute("LastRequest");
    }
    return str;
  }
  
  protected String modifyAccountGroupEntitlementsPhase1(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    this.Zp = null;
    this.ZS = null;
    this.ZE = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.Zz = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.ZT = new Limits();
    this.ZM = new Limits();
    this.Zr = new Limits();
    this.ZL = new ArrayList();
    int[] arrayOfInt = { 1, 2, 3, 4 };
    try
    {
      int i = (this.ZJ != null) && (!this.ZJ.equals("")) ? 1 : 0;
      if (paramEntitlementGroupMember1 == null)
      {
        this.Zp = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, this.Zx);
        this.ZS = com.ffusion.csil.core.Entitlements.getGroupLimits(this.Zx);
      }
      else
      {
        this.Zp = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1);
        this.ZS = com.ffusion.csil.core.Entitlements.getGroupLimits(paramEntitlementGroupMember1);
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.ZQ);
      EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.Zw);
      this.ZP = null;
      int j = 0;
      int k = 0;
      if (i != 0)
      {
        this.ZP = ((EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.ZJ));
        k = this.ZP.size();
      }
      else
      {
        this.ZP = ((EntitlementTypePropertyLists)localEntitlementTypePropertyLists1.clone());
        this.ZP.addAll(localEntitlementTypePropertyLists2);
        j = localEntitlementTypePropertyLists1.size();
        k = j + localEntitlementTypePropertyLists2.size();
      }
      HashSet localHashSet = new HashSet(k * 4 / 3 + 1);
      String str2 = null;
      String str3 = null;
      ListIterator localListIterator = this.ZP.listIterator();
      while (localListIterator.hasNext())
      {
        localObject1 = (EntitlementTypePropertyList)localListIterator.next();
        localHashSet.add(((EntitlementTypePropertyList)localObject1).getOperationName());
        if (((EntitlementTypePropertyList)localObject1).isPropertySet("admin partner")) {
          localHashSet.add(((EntitlementTypePropertyList)localObject1).getPropertyValue("admin partner", 0));
        }
      }
      Object localObject3;
      for (int m = 0; m < k; m++)
      {
        str2 = (String)paramHttpSession.getAttribute("entitlement" + m);
        str3 = (String)paramHttpSession.getAttribute("admin" + m);
        localObject1 = new Entitlement(((EntitlementTypePropertyList)this.ZP.get(m)).getOperationName(), getObjectType(), this.Zq);
        localObject2 = new Entitlement(((EntitlementTypePropertyList)this.ZP.get(m)).getPropertyValue("admin partner", 0), getObjectType(), this.Zq);
        if (new Boolean(paramEntitlementTypePropertyLists.getByOperationName(((EntitlementTypePropertyList)this.ZP.get(m)).getOperationName()).getPropertyValue("CanAdminRow", 0)).booleanValue())
        {
          if (str3 == null)
          {
            if (!this.Zp.contains(localObject2)) {
              this.ZE.add(localObject2);
            }
          }
          else
          {
            localHashSet.remove(str3);
            if (this.Zp.contains(localObject2)) {
              this.Zz.add(localObject2);
            }
          }
          this.Zp.remove(localObject2);
        }
        else
        {
          localHashSet.remove(((Entitlement)localObject2).getOperationName());
        }
        if (new Boolean(paramEntitlementTypePropertyLists.getByOperationName(((EntitlementTypePropertyList)this.ZP.get(m)).getOperationName()).getPropertyValue("CanInitRow", 0)).booleanValue())
        {
          if (str2 == null)
          {
            if (!this.Zp.contains(localObject1)) {
              this.ZE.add(localObject1);
            }
          }
          else
          {
            localHashSet.remove(str2);
            if (this.Zp.contains(localObject1)) {
              this.Zz.add(localObject1);
            }
          }
        }
        else {
          localHashSet.remove(((Entitlement)localObject1).getOperationName());
        }
        this.Zp.remove(localObject1);
        String[] arrayOfString = { (String)paramHttpSession.getAttribute("transaction_limit" + m), (String)paramHttpSession.getAttribute("day_limit" + m), (String)paramHttpSession.getAttribute("week_limit" + m), (String)paramHttpSession.getAttribute("month_limit" + m) };
        localObject3 = new String[] { (String)paramHttpSession.getAttribute("transaction_exceed" + m), (String)paramHttpSession.getAttribute("day_exceed" + m), (String)paramHttpSession.getAttribute("week_exceed" + m), (String)paramHttpSession.getAttribute("month_exceed" + m) };
        if ((m < j) || ((i != 0) && (((EntitlementTypePropertyList)this.ZP.get(m)).isPropertySet("isLimit")))) {
          for (int i1 = 0; i1 < arrayOfInt.length; i1++)
          {
            this.error = TaskUtil.validateLimitAmount(arrayOfString[i1]);
            if (this.error != 0) {
              return this.taskErrorURL;
            }
            boolean bool1;
            if (localObject3[i1] == null) {
              bool1 = false;
            } else {
              bool1 = localObject3[i1].equalsIgnoreCase("true");
            }
            Limit localLimit1 = new Limit(this.Zx, arrayOfInt[i1], arrayOfString[i1], (Entitlement)localObject1, bool1);
            int i2 = setLimitCurrencyInformation(localLimit1);
            if (i2 != 0)
            {
              this.error = i2;
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
            int i3 = 0;
            for (int i4 = 0; i4 < this.ZS.size(); i4++)
            {
              Limit localLimit2 = (Limit)this.ZS.get(i4);
              if (localLimit2.isLimitInfoIdentical(localLimit1))
              {
                if ((arrayOfString[i1] == null) || (arrayOfString[i1].equals("")))
                {
                  this.Zr.add(localLimit2);
                }
                else if ((!arrayOfString[i1].equals(localLimit2.getData())) || (!localLimit1.getAllowApproval().equals(localLimit2.getAllowApproval())))
                {
                  this.ZL.add(localLimit2.getData());
                  localLimit2.setData(arrayOfString[i1]);
                  localLimit2.setAllowApproval(localLimit1.isAllowedApproval());
                  this.ZM.add(localLimit2);
                }
                i3 = 1;
                break;
              }
            }
            if ((i3 == 0) && (arrayOfString[i1] != null) && (!arrayOfString[i1].equals(""))) {
              this.ZT.add(localLimit1);
            }
          }
        }
      }
      Entitlement localEntitlement1 = null;
      Object localObject1 = new StringBuffer();
      Object localObject2 = localHashSet.iterator();
      Object localObject4;
      while (((Iterator)localObject2).hasNext())
      {
        str2 = (String)((Iterator)localObject2).next();
        localEntitlement1 = new Entitlement(str2, getObjectType(), this.Zq);
        if (((StringBuffer)localObject1).length() == 0) {
          ((StringBuffer)localObject1).append("'" + str2 + "'");
        } else {
          ((StringBuffer)localObject1).append(", '" + str2 + "'");
        }
        if (!this.Zp.contains(localEntitlement1)) {
          this.Zp.add(localEntitlement1);
        }
        for (int n = 0; n < this.ZS.size(); n++)
        {
          localObject3 = (Limit)this.ZS.get(n);
          localObject4 = ((Limit)localObject3).getEntitlement();
          if (localEntitlement1.equals(localObject4)) {
            this.Zr.add(localObject3);
          }
        }
      }
      this.Zy = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      localObject2 = this.ZP.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject2).next();
        localObject3 = new Entitlement(localEntitlementTypePropertyList.getOperationName(), getObjectType(), this.Zq);
        localObject4 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
        Entitlement localEntitlement2 = new Entitlement((String)localObject4, getObjectType(), this.Zq);
        boolean bool2 = jdMethod_for(paramEntitlementGroupMember1, localEntitlementTypePropertyList, (Entitlement)localObject3, localEntitlement2, false);
        if ((!this._initOnly) && (!bool2))
        {
          this.error = 4548;
          return this.taskErrorURL;
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
  
  protected String modifyAccountGroupEntitlementsPhase2(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
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
      if (this.ZE.size() + this.Zz.size() != 0)
      {
        if (paramEntitlementGroupMember1 == null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, this.Zx, this.Zp);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1, this.Zp);
        }
        String str5;
        LocalizableString localLocalizableString;
        for (j = 0; j < this.ZE.size(); j++)
        {
          localObject1 = (Entitlement)this.ZE.get(j);
          str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localObject2 = ((Entitlement)localObject1).getOperationName();
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localObject4 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject3).getPropertiesMap(), (String)localObject2);
          if (((Entitlement)localObject1).getObjectId() != null)
          {
            localObject5 = new Object[3];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            localObject5[2] = ((Entitlement)localObject1).getObjectId();
            str5 = "AuditMessage_EditAccountGroupPermissions" + getContextAuditKey(str2) + "_1.2";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          else
          {
            localObject5 = new Object[2];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            str5 = "AuditMessage_EditAccountGroupPermissions" + getContextAuditKey(str2) + "_1.1";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, i, str4, 3225);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localObject1, this.ZP);
        }
        for (j = 0; j < this.Zz.size(); j++)
        {
          localObject1 = (Entitlement)this.Zz.get(j);
          str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localObject2 = ((Entitlement)localObject1).getOperationName();
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localObject4 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject3).getPropertiesMap(), (String)localObject2);
          if (((Entitlement)localObject1).getObjectId() != null)
          {
            localObject5 = new Object[3];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            localObject5[2] = ((Entitlement)localObject1).getObjectId();
            str5 = "AuditMessage_EditAccountGroupPermissions" + getContextAuditKey(str2) + "_2.2";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          else
          {
            localObject5 = new Object[2];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            str5 = "AuditMessage_EditAccountGroupPermissions" + getContextAuditKey(str2) + "_2.1";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, i, str4, 3225);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localObject1, this.ZP);
        }
      }
      for (int j = 0; j < this.ZT.size(); j++)
      {
        localObject1 = (Limit)this.ZT.get(j);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember2, (Limit)localObject1);
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.1";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.2";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.3";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitAdd((Limit)localObject1, this.ZP);
      }
      for (j = 0; j < this.ZM.size(); j++)
      {
        localObject1 = (Limit)this.ZM.get(j);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember2, (Limit)this.ZM.get(j));
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.1";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.2";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.3";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitChange((Limit)localObject1, this.ZP, (String)this.ZL.get(j));
      }
      for (j = 0; j < this.Zr.size(); j++)
      {
        localObject1 = (Limit)this.Zr.get(j);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember2, (Limit)this.Zr.get(j));
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.1";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.2";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.3";
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
          localObject5 = "AuditMessage_EditAccountGroupPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitDelete((Limit)localObject1, this.ZP);
      }
      if (paramEntitlementGroupMember1 == null) {
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.Zx), this.Zz, 5, this.ZG, new HashMap());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    this.Zp = null;
    this.ZS = null;
    this.ZE = null;
    this.Zz = null;
    this.ZT = null;
    this.ZM = null;
    this.Zr = null;
    this.ZL = null;
    this.ZP = null;
    return str1;
  }
  
  private boolean jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, EntitlementTypePropertyList paramEntitlementTypePropertyList, Entitlement paramEntitlement1, Entitlement paramEntitlement2, boolean paramBoolean)
    throws CSILException
  {
    String str1 = "EditAccountGroupPremissions.performParentChildCheck";
    if (paramEntitlementTypePropertyList == null) {
      throw new CSILException(str1, 4548);
    }
    if ((paramEntitlement1 == null) && (paramEntitlement2 == null)) {
      throw new CSILException(str1, 4548);
    }
    if ((paramEntitlement1 != null) && (!paramEntitlementTypePropertyList.getOperationName().equals(paramEntitlement1.getOperationName()))) {
      throw new CSILException(str1, 4548);
    }
    if ((paramEntitlement2 != null) && (!paramEntitlementTypePropertyList.isPropertySet("admin partner", paramEntitlement2.getOperationName()))) {
      throw new CSILException(str1, 4548);
    }
    String str2 = paramEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
    int i = paramEntitlement1 == null ? 1 : 0;
    int j = (paramEntitlement2 == null) || (str2 == null) ? 1 : 0;
    if ((i == 0) && (!paramBoolean) && (this.Zp.contains(paramEntitlement1))) {
      i = 1;
    }
    if ((j == 0) && (!paramBoolean) && (str2 != null) && (this.Zp.contains(paramEntitlement2))) {
      j = 1;
    }
    if ((i != 0) && (j != 0)) {
      return true;
    }
    if ((i == 0) && (!paramBoolean) && (!this.Zz.contains(paramEntitlement1))) {
      if (paramEntitlementGroupMember == null)
      {
        if (!com.ffusion.csil.core.Entitlements.checkEntitlement(this.Zx, paramEntitlement1)) {
          i = 1;
        }
      }
      else if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement1)) {
        i = 1;
      }
    }
    if ((j == 0) && (!paramBoolean) && (!this.Zz.contains(paramEntitlement2))) {
      if (paramEntitlementGroupMember == null)
      {
        if (!com.ffusion.csil.core.Entitlements.checkEntitlement(this.Zx, paramEntitlement2)) {
          j = 1;
        }
      }
      else if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement2)) {
        j = 1;
      }
    }
    if ((i != 0) && (j != 0)) {
      return true;
    }
    if (!paramEntitlementTypePropertyList.isPropertySet("control parent")) {
      return true;
    }
    if ((i == 0) && (!paramBoolean) && (paramEntitlementTypePropertyList.isPropertySet("CanInitRow", "FALSE"))) {
      i = 1;
    }
    if ((j == 0) && (!paramBoolean) && (paramEntitlementTypePropertyList.isPropertySet("CanAdminRow", "FALSE"))) {
      j = 1;
    }
    if ((i != 0) && (j != 0)) {
      return true;
    }
    int k = paramEntitlementTypePropertyList.numPropertyValues("control parent");
    for (int m = 0; m < k; m++)
    {
      String str3 = paramEntitlementTypePropertyList.getPropertyValue("control parent", m);
      Entitlement localEntitlement1 = null;
      if ((this.Zq == null) || (this.Zq.equals(""))) {
        localEntitlement1 = new Entitlement(str3, null, null);
      } else {
        localEntitlement1 = new Entitlement(str3, getObjectType(), this.Zq);
      }
      EntitlementTypePropertyList localEntitlementTypePropertyList = this.Zy.getByOperationName(str3);
      String str4 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
      Entitlement localEntitlement2 = null;
      if ((this.Zq == null) || (this.Zq.equals(""))) {
        localEntitlement2 = new Entitlement(str4, null, null);
      } else {
        localEntitlement2 = new Entitlement(str4, getObjectType(), this.Zq);
      }
      if ((i == 0) && (this.Zz.contains(localEntitlement1))) {
        i = 1;
      }
      if ((j == 0) && (this.Zz.contains(localEntitlement2))) {
        j = 1;
      }
      if ((i == 0) && (this.Zp.contains(localEntitlement1))) {
        return false;
      }
      if ((j == 0) && (str4 != null) && (this.Zp.contains(localEntitlement2))) {
        return false;
      }
      if (getObjectType() != null)
      {
        localEntitlement1.setObjectType(null);
        localEntitlement1.setObjectId(null);
        localEntitlement2.setObjectType(null);
        localEntitlement2.setObjectId(null);
        if ((i == 0) && (this.Zp.contains(localEntitlement1))) {
          return false;
        }
        if ((j == 0) && (str4 != null) && (this.Zp.contains(localEntitlement2))) {
          return false;
        }
        localEntitlement1.setObjectType(getObjectType());
        localEntitlement1.setObjectId(this.Zq);
        localEntitlement2.setObjectType(getObjectType());
        localEntitlement2.setObjectId(this.Zq);
      }
    }
    return true;
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
    this.Zo = paramString;
  }
  
  public String getMemberId()
  {
    return this.Zo;
  }
  
  public void setMemberType(String paramString)
  {
    this.ZO = paramString;
  }
  
  public String getMemberType()
  {
    return this.ZO;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.ZK = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.ZK;
  }
  
  public void setEntitlementTypesWithLimits(String paramString)
  {
    this.ZQ = paramString;
  }
  
  public void setEntitlementTypesMerged(String paramString)
  {
    this.ZJ = paramString;
  }
  
  public void setEntitlementTypesWithoutLimits(String paramString)
  {
    this.Zw = paramString;
  }
  
  public void setAccountGroupId(String paramString)
  {
    this.Zq = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.Zx = Integer.parseInt(paramString);
  }
  
  public void setProfileId(String paramString)
  {
    this.Zv = paramString;
  }
  
  protected String getObjectType()
  {
    return "AccountGroup";
  }
  
  public void setSaveLastRequest(String paramString)
  {
    this.ZW = paramString;
  }
  
  public void setEntitlementTypePropertyLists(String paramString)
  {
    this._listName = paramString;
  }
  
  public void setAutoEntitle(String paramString)
  {
    this.ZG = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitle()
  {
    return String.valueOf(this.ZG);
  }
  
  public void setInitOnly(String paramString)
  {
    this._initOnly = Boolean.valueOf(paramString).booleanValue();
    this.Zp = null;
    this.ZS = null;
    this.ZE = null;
    this.Zz = null;
    this.ZT = null;
    this.ZM = null;
    this.Zr = null;
    this.ZL = null;
    this.ZP = null;
  }
  
  public String getInitOnly()
  {
    return new Boolean(this._initOnly).toString();
  }
  
  public String getNumGrantedEntitlements()
  {
    return Integer.toString(this.Zz.size());
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
 * Qualified Name:     com.ffusion.tasks.admin.EditAccountGroupPermissions
 * JD-Core Version:    0.7.0.1
 */