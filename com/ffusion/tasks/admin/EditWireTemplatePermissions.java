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

public class EditWireTemplatePermissions
  extends BaseTask
  implements AdminTask
{
  public static final String ENTITLEMENT = "entitlement";
  public static final String TRANSACTION_LIMIT = "transaction_limit";
  public static final String TRANSACTION_EXCEED = "transaction_exceed";
  public static final String DAY_LIMIT = "day_limit";
  public static final String DAY_EXCEED = "day_exceed";
  public static final String WEEK_LIMIT = "week_limit";
  public static final String WEEK_EXCEED = "week_exceed";
  public static final String MONTH_LIMIT = "month_limit";
  public static final String MONTH_EXCEED = "month_exceed";
  public static final String WIRE_TEMPLATE_NUMBER = "WireTemplateNumber";
  private String ab9;
  private String acA;
  private String acx;
  private int acm;
  private String acu;
  private String aci;
  private String ack;
  private String acI;
  private String acC = "NonAccountEntitlementsWithLimits";
  private String acl = "NonAccountEntitlementsWithoutLimits";
  private String acw = "NonAccountEntitlementsMerged";
  protected boolean _initOnly = false;
  com.ffusion.csil.beans.entitlements.Entitlements acb = null;
  Limits acD = null;
  com.ffusion.csil.beans.entitlements.Entitlements acr = null;
  com.ffusion.csil.beans.entitlements.Entitlements acn = null;
  Limits acF = null;
  Limits acz = null;
  Limits ace = null;
  ArrayList acy = null;
  EntitlementTypePropertyLists acB = null;
  protected boolean _autoEntitle = true;
  private static final String acL = "com.ffusion.util.logging.audit.task";
  private static final String acq = "AuditMessage_EditWireTemplatePermissions";
  private static final String acH = "_1.1";
  private static final String acj = "_1.2";
  private static final String acd = "_2.1";
  private static final String act = "_2.2";
  private static final String acJ = "_3.1";
  private static final String acf = "_3.2";
  private static final String aca = "_3.3";
  private static final String acE = "_3.4";
  private static final String acg = "_4.1";
  private static final String acv = "_4.2";
  private static final String acc = "_4.3";
  private static final String ach = "_4.4";
  private static final String acp = "_5.1";
  private static final String acs = "_5.2";
  private static final String acG = "_5.3";
  private static final String aco = "_5.4";
  private static final String acK = "_xcurr";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    int i = !"false".equals(this.acI) ? 1 : 0;
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
    if (this.acA != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setMemberType(this.acA);
      localEntitlementGroupMember2.setMemberSubType(this.acx);
      localEntitlementGroupMember2.setId(this.ab9);
      localEntitlementGroupMember2.setEntitlementGroupId(this.acm);
    }
    int j = 1;
    if (this._initOnly) {
      j = this.acn == null ? 1 : 0;
    }
    if ((!this._initOnly) || (j != 0))
    {
      str = modifyWireTemplateEntitlementsPhase1(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember1, null);
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
      HistoryTracker localHistoryTracker = EditAccountPermissions.jdMethod_new(localSecureUser, "EditWireTemplatesPermissions", this.acm, this.ab9, this.ack);
      str = modifyWireTemplateEntitlementsPhase2(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember1, localHistoryTracker);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for EditWireTemplatesPermissions: " + localProfileException.toString());
      }
    }
    if ((i != 0) && (this.error == 0)) {
      localHttpSession.removeAttribute("LastRequest");
    }
    return str;
  }
  
  protected String modifyWireTemplateEntitlementsPhase1(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    this.acb = null;
    this.acD = null;
    this.acr = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.acn = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.acF = new Limits();
    this.acz = new Limits();
    this.ace = new Limits();
    this.acy = new ArrayList();
    int[] arrayOfInt = { 1, 2, 3, 4 };
    try
    {
      int i = (this.acw != null) && (!this.acw.equals("")) ? 1 : 0;
      int j;
      if (paramEntitlementGroupMember1 == null)
      {
        this.acb = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, this.acm);
        this.acD = com.ffusion.csil.core.Entitlements.getGroupLimits(this.acm);
        localObject1 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.acm);
        j = ((EntitlementGroup)localObject1).getParentId();
      }
      else
      {
        this.acb = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1);
        this.acD = com.ffusion.csil.core.Entitlements.getGroupLimits(paramEntitlementGroupMember1);
        j = paramEntitlementGroupMember1.getEntitlementGroupId();
      }
      Object localObject1 = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.acC);
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.acl);
      this.acB = null;
      int k = 0;
      int m = 0;
      if (i != 0)
      {
        this.acB = ((EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.acw));
        m = this.acB.size();
      }
      else
      {
        this.acB = ((EntitlementTypePropertyLists)((EntitlementTypePropertyLists)localObject1).clone());
        this.acB.addAll(localEntitlementTypePropertyLists);
        k = ((EntitlementTypePropertyLists)localObject1).size();
        m = k + localEntitlementTypePropertyLists.size();
      }
      HashSet localHashSet = new HashSet(m * 4 / 3 + 1);
      String str2 = null;
      ListIterator localListIterator = this.acB.listIterator();
      while (localListIterator.hasNext()) {
        localHashSet.add(((EntitlementTypePropertyList)localListIterator.next()).getOperationName());
      }
      for (int n = 0; n < m; n++)
      {
        str2 = (String)paramHttpSession.getAttribute("entitlement" + n + "_" + this.aci);
        localObject2 = new Entitlement(((EntitlementTypePropertyList)this.acB.get(n)).getOperationName(), getObjectType(), this.acu);
        if (str2 == null)
        {
          if (!com.ffusion.csil.core.Entitlements.checkEntitlement(j, (Entitlement)localObject2)) {
            localHashSet.remove(((Entitlement)localObject2).getOperationName());
          } else if (!this.acb.contains(localObject2)) {
            this.acr.add(localObject2);
          }
        }
        else
        {
          localObject3 = (String)paramHttpSession.getAttribute("WireTemplateNumber" + this.aci);
          if ((localObject3 == null) || (((String)localObject3).equals("")))
          {
            this.error = 4548;
            return this.taskErrorURL;
          }
          localHashSet.remove(str2);
          if (this.acb.contains(localObject2)) {
            this.acn.add(localObject2);
          }
          this.acb.remove(localObject2);
          localObject3 = new String[] { (String)paramHttpSession.getAttribute("transaction_limit" + n + "_" + this.aci), (String)paramHttpSession.getAttribute("day_limit" + n + "_" + this.aci), (String)paramHttpSession.getAttribute("week_limit" + n + "_" + this.aci), (String)paramHttpSession.getAttribute("month_limit" + n + "_" + this.aci) };
          String[] arrayOfString = { (String)paramHttpSession.getAttribute("transaction_exceed" + n + "_" + this.aci), (String)paramHttpSession.getAttribute("day_exceed" + n + "_" + this.aci), (String)paramHttpSession.getAttribute("week_exceed" + n + "_" + this.aci), (String)paramHttpSession.getAttribute("month_exceed" + n + "_" + this.aci) };
          if ((n < k) || ((i != 0) && (((EntitlementTypePropertyList)this.acB.get(n)).isPropertySet("isLimit")))) {
            for (int i2 = 0; i2 < arrayOfInt.length; i2++)
            {
              this.error = TaskUtil.validateLimitAmount(localObject3[i2]);
              if (this.error != 0) {
                return this.taskErrorURL;
              }
              boolean bool;
              if (arrayOfString[i2] == null) {
                bool = false;
              } else {
                bool = arrayOfString[i2].equalsIgnoreCase("true");
              }
              Limit localLimit2 = new Limit(this.acm, arrayOfInt[i2], localObject3[i2], (Entitlement)localObject2, bool);
              if (paramEntitlementGroupMember1 == null)
              {
                localLimit2.setRunningTotalType('G');
              }
              else
              {
                localLimit2.setMemberType(paramEntitlementGroupMember1.getMemberType());
                localLimit2.setMemberSubType(paramEntitlementGroupMember1.getMemberSubType());
                localLimit2.setMemberId(paramEntitlementGroupMember1.getId());
                localLimit2.setRunningTotalType('U');
              }
              int i3 = setLimitCurrencyInformation(localLimit2);
              if (i3 != 0)
              {
                this.error = i3;
                return this.serviceErrorURL;
              }
              int i4 = 0;
              for (int i5 = 0; i5 < this.acD.size(); i5++)
              {
                Limit localLimit3 = (Limit)this.acD.get(i5);
                if (localLimit3.isLimitInfoIdentical(localLimit2))
                {
                  if ((localObject3[i2] == null) || (localObject3[i2].equals("")))
                  {
                    this.ace.add(localLimit3);
                  }
                  else if ((!localObject3[i2].equals(localLimit3.getData())) || (!localLimit2.getAllowApproval().equals(localLimit3.getAllowApproval())))
                  {
                    this.acy.add(localLimit3.getData());
                    localLimit3.setData(localObject3[i2]);
                    localLimit3.setAllowApproval(localLimit2.isAllowedApproval());
                    this.acz.add(localLimit3);
                  }
                  i4 = 1;
                  break;
                }
              }
              if ((i4 == 0) && (localObject3[i2] != null) && (!localObject3[i2].equals(""))) {
                this.acF.add(localLimit2);
              }
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
        localEntitlement1 = new Entitlement(str2, getObjectType(), this.acu);
        if (((StringBuffer)localObject2).length() == 0) {
          ((StringBuffer)localObject2).append("'" + str2 + "'");
        } else {
          ((StringBuffer)localObject2).append(", '" + str2 + "'");
        }
        if (!this.acb.contains(localEntitlement1)) {
          this.acb.add(localEntitlement1);
        }
        for (int i1 = 0; i1 < this.acD.size(); i1++)
        {
          Limit localLimit1 = (Limit)this.acD.get(i1);
          Entitlement localEntitlement2 = localLimit1.getEntitlement();
          if (localEntitlement1.equals(localEntitlement2)) {
            this.ace.add(localLimit1);
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
  
  protected String modifyWireTemplateEntitlementsPhase2(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    try
    {
      String str2 = (String)paramHttpSession.getAttribute("Section");
      str3 = (String)paramHttpSession.getAttribute("Context");
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
        localObject3 = (Business)paramHttpSession.getAttribute("ModifyBusiness");
        i = ((Business)localObject3).getIdValue();
      }
      else
      {
        i = paramSecureUser.getBusinessID();
      }
      localObject3 = new Entitlement(null, getObjectType(), this.acu);
      str4 = (String)paramHttpSession.getAttribute("WireTemplateNumber" + this.aci);
      int j = 0;
      if ((str4 == null) || ("".equals(str4)))
      {
        if (!this.acb.contains(localObject3))
        {
          this.acb.add(localObject3);
          j = 1;
        }
      }
      else if (this.acb.contains(localObject3))
      {
        this.acb.remove(localObject3);
        j = 1;
      }
      Object localObject7;
      Object localObject8;
      Object localObject9;
      Object localObject10;
      if ((this.acr.size() + this.acn.size() != 0) || (j != 0))
      {
        if (paramEntitlementGroupMember1 == null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, this.acm, this.acb);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1, this.acb);
        }
        String str6;
        LocalizableString localLocalizableString;
        for (k = 0; k < this.acr.size(); k++)
        {
          localObject6 = (Entitlement)this.acr.get(k);
          str5 = TrackingIDGenerator.GetNextID();
          localObject7 = ((Entitlement)localObject6).getOperationName();
          localObject8 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject7);
          localObject9 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject8).getPropertiesMap(), (String)localObject7);
          if (((Entitlement)localObject6).getObjectId() == null)
          {
            localObject10 = new Object[2];
            localObject10[0] = str3;
            localObject10[1] = localObject9;
            str6 = "AuditMessage_EditWireTemplatePermissions" + getContextAuditKey(str2) + "_1.1";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str6, (Object[])localObject10);
          }
          else
          {
            localObject10 = new Object[3];
            localObject10[0] = str3;
            localObject10[1] = localObject9;
            localObject10[2] = ((Entitlement)localObject6).getObjectId();
            str6 = "AuditMessage_EditWireTemplatePermissions" + getContextAuditKey(str2) + "_1.2";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str6, (Object[])localObject10);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, i, str5, 3225);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localObject6, this.acB);
        }
        for (k = 0; k < this.acn.size(); k++)
        {
          localObject6 = (Entitlement)this.acn.get(k);
          str5 = TrackingIDGenerator.GetNextID();
          localObject7 = ((Entitlement)localObject6).getOperationName();
          localObject8 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject7);
          localObject9 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject8).getPropertiesMap(), (String)localObject7);
          if (((Entitlement)localObject6).getObjectId() == null)
          {
            localObject10 = new Object[2];
            localObject10[0] = str3;
            localObject10[1] = localObject9;
            str6 = "AuditMessage_EditWireTemplatePermissions" + getContextAuditKey(str2) + "_2.1";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str6, (Object[])localObject10);
          }
          else
          {
            localObject10 = new Object[3];
            localObject10[0] = str3;
            localObject10[1] = localObject9;
            localObject10[2] = ((Entitlement)localObject6).getObjectId();
            str6 = "AuditMessage_EditWireTemplatePermissions" + getContextAuditKey(str2) + "_2.2";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str6, (Object[])localObject10);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, i, str5, 3225);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localObject6, this.acB);
        }
        if (paramEntitlementGroupMember1 == null) {
          AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.acm), this.acn, 3, this._autoEntitle, new HashMap());
        }
      }
      for (int k = 0; k < this.acF.size(); k++)
      {
        localObject6 = (Limit)this.acF.get(k);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember2, (Limit)localObject6);
        str5 = TrackingIDGenerator.GetNextID();
        localObject7 = null;
        if (((Limit)localObject6).getLimitName() != null)
        {
          localObject8 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject6).getLimitName());
          localObject7 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject8).getPropertiesMap(), ((Limit)localObject6).getLimitName());
        }
        if ((((Limit)localObject6).getLimitName() == null) && (((Limit)localObject6).getObjectId() == null))
        {
          localObject8 = new Object[3];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[2] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_3.1";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else if ((((Limit)localObject6).getLimitName() != null) && (((Limit)localObject6).getObjectId() == null))
        {
          localObject8 = new Object[4];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[3] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = localObject7;
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_3.2";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else if ((((Limit)localObject6).getLimitName() == null) && (((Limit)localObject6).getObjectId() != null))
        {
          localObject8 = new Object[4];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[3] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = ((Limit)localObject6).getObjectId();
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_3.3";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else
        {
          localObject8 = new Object[5];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[4] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = localObject7;
          localObject8[3] = ((Limit)localObject6).getObjectId();
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_3.4";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject9, i, str5, 3225);
        paramHistoryTracker.logLimitAdd((Limit)localObject6, this.acB);
      }
      for (k = 0; k < this.acz.size(); k++)
      {
        localObject6 = (Limit)this.acz.get(k);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember2, (Limit)this.acz.get(k));
        str5 = TrackingIDGenerator.GetNextID();
        localObject7 = null;
        if (((Limit)localObject6).getLimitName() != null)
        {
          localObject8 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject6).getLimitName());
          localObject7 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject8).getPropertiesMap(), ((Limit)localObject6).getLimitName());
        }
        if ((((Limit)localObject6).getLimitName() == null) && (((Limit)localObject6).getObjectId() == null))
        {
          localObject8 = new Object[3];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[2] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_4.1";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else if ((((Limit)localObject6).getLimitName() != null) && (((Limit)localObject6).getObjectId() == null))
        {
          localObject8 = new Object[4];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[3] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = localObject7;
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_4.2";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else if ((((Limit)localObject6).getLimitName() == null) && (((Limit)localObject6).getObjectId() != null))
        {
          localObject8 = new Object[4];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[3] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = ((Limit)localObject6).getObjectId();
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_4.3";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else
        {
          localObject8 = new Object[5];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[4] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = localObject7;
          localObject8[3] = ((Limit)localObject6).getObjectId();
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_4.4";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject9, i, str5, 3225);
        paramHistoryTracker.logLimitChange((Limit)localObject6, this.acB, (String)this.acy.get(k));
      }
      for (k = 0; k < this.ace.size(); k++)
      {
        localObject6 = (Limit)this.ace.get(k);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember2, (Limit)this.ace.get(k));
        str5 = TrackingIDGenerator.GetNextID();
        localObject7 = null;
        if (((Limit)localObject6).getLimitName() != null)
        {
          localObject8 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject6).getLimitName());
          localObject7 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject8).getPropertiesMap(), ((Limit)localObject6).getLimitName());
        }
        if ((((Limit)localObject6).getLimitName() == null) && (((Limit)localObject6).getObjectId() == null))
        {
          localObject8 = new Object[3];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[2] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_5.1";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else if ((((Limit)localObject6).getLimitName() != null) && (((Limit)localObject6).getObjectId() == null))
        {
          localObject8 = new Object[4];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[3] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = localObject7;
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_5.2";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else if ((((Limit)localObject6).getLimitName() == null) && (((Limit)localObject6).getObjectId() != null))
        {
          localObject8 = new Object[4];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[3] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = ((Limit)localObject6).getObjectId();
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_5.3";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        else
        {
          localObject8 = new Object[5];
          localObject8[0] = ((Limit)localObject6).getData();
          localObject8[4] = ((Limit)localObject6).getCurrencyCode();
          localObject8[1] = str3;
          localObject8[2] = localObject7;
          localObject8[3] = ((Limit)localObject6).getObjectId();
          localObject10 = "AuditMessage_EditWireTemplatePermissions" + getPeriodAuditKey(((Limit)localObject6).getPeriod()) + getContextAuditKey(str2) + "_5.4";
          if (((Limit)localObject6).isCrossCurrency()) {
            localObject10 = (String)localObject10 + "_xcurr";
          }
          localObject9 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject10, (Object[])localObject8);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject9, i, str5, 3225);
        paramHistoryTracker.logLimitDelete((Limit)localObject6, this.acB);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    Object localObject1 = null;
    String str3 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    String str4 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    String str5 = null;
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
    this.ab9 = paramString;
  }
  
  public String getMemberId()
  {
    return this.ab9;
  }
  
  public void setMemberType(String paramString)
  {
    this.acA = paramString;
  }
  
  public String getMemberType()
  {
    return this.acA;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.acx = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.acx;
  }
  
  public void setEntitlementTypesWithLimits(String paramString)
  {
    this.acC = paramString;
  }
  
  public void setEntitlementTypesMerged(String paramString)
  {
    this.acw = paramString;
  }
  
  public void setEntitlementTypesWithoutLimits(String paramString)
  {
    this.acl = paramString;
  }
  
  public void setWireTemplateId(String paramString)
  {
    this.acu = paramString;
  }
  
  public void setTemplateIndex(String paramString)
  {
    this.aci = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.acm = Integer.parseInt(paramString);
  }
  
  public void setProfileId(String paramString)
  {
    this.ack = paramString;
  }
  
  protected String getObjectType()
  {
    return "Wire Template";
  }
  
  public void setSaveLastRequest(String paramString)
  {
    this.acI = paramString;
  }
  
  public void setAutoEntitle(String paramString)
  {
    this._autoEntitle = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitle()
  {
    return String.valueOf(this._autoEntitle);
  }
  
  public void setInitOnly(String paramString)
  {
    this._initOnly = Boolean.valueOf(paramString).booleanValue();
    this.acb = null;
    this.acD = null;
    this.acr = null;
    this.acn = null;
    this.acF = null;
    this.acz = null;
    this.ace = null;
    this.acy = null;
    this.acB = null;
  }
  
  public String getInitOnly()
  {
    return new Boolean(this._initOnly).toString();
  }
  
  public String getNumGrantedEntitlements()
  {
    return Integer.toString(this.acn.size());
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
 * Qualified Name:     com.ffusion.tasks.admin.EditWireTemplatePermissions
 * JD-Core Version:    0.7.0.1
 */