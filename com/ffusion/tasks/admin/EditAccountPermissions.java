package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
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
import com.ffusion.util.beans.LocalizableAccountID;
import com.ffusion.util.beans.LocalizableProperty;
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

public class EditAccountPermissions
  extends BaseTask
  implements AdminTask
{
  protected boolean _initOnly = false;
  com.ffusion.csil.beans.entitlements.Entitlements aby = null;
  com.ffusion.csil.beans.entitlements.Entitlements abz = null;
  Limits ab1 = null;
  com.ffusion.csil.beans.entitlements.Entitlements abP = null;
  com.ffusion.csil.beans.entitlements.Entitlements abK = null;
  Limits ab2 = null;
  Limits abW = null;
  Limits abC = null;
  ArrayList abV = null;
  EntitlementTypePropertyLists abY = null;
  EntitlementTypePropertyLists abJ = null;
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
  private String abw;
  private String abX;
  private String abU;
  private int abI;
  private String abA;
  private String abG;
  private String ab5;
  private String abZ = "NonAccountEntitlementsWithLimits";
  private String abH = "NonAccountEntitlementsWithoutLimits";
  private String abT = "";
  protected String _listName;
  protected boolean _enrollingBusiness = false;
  private static final String ab8 = "com.ffusion.util.logging.audit.task";
  private static final String abO = "AuditMessage_EditAccountPermissions";
  private static final String abS = "_1.1";
  private static final String abE = "_1.2";
  private static final String abN = "_2.1";
  private static final String abD = "_2.2";
  private static final String abB = "_3.1";
  private static final String ab3 = "_3.2";
  private static final String abM = "_3.3";
  private static final String abv = "_3.4";
  private static final String ab0 = "_4.1";
  private static final String abR = "_4.2";
  private static final String abL = "_4.3";
  private static final String abx = "_4.4";
  private static final String ab7 = "_5.1";
  private static final String ab4 = "_5.2";
  private static final String abu = "_5.3";
  private static final String abQ = "_5.4";
  private static final String abF = "_entfault_1";
  private static final String ab6 = "_xcurr";
  
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
    int i = !"false".equals(this.ab5) ? 1 : 0;
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
    if (this.abX != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setMemberType(this.abX);
      localEntitlementGroupMember2.setMemberSubType(this.abU);
      localEntitlementGroupMember2.setId(this.abw);
      localEntitlementGroupMember2.setEntitlementGroupId(this.abI);
    }
    int j = 1;
    if (this._initOnly) {
      j = this.abK == null ? 1 : 0;
    }
    if ((!this._initOnly) || (j != 0))
    {
      str = modifyAccountEntitlementsPhase1(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementTypePropertyLists, localEntitlementGroupMember1, null);
      if (this.error != 0)
      {
        this._initOnly = false;
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
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditAccountPermissions_entfault_1", null);
            Initialize.logEntitlementFault(localSecureUser, localLocalizableString, com.ffusion.util.logging.TrackingIDGenerator.GetNextID());
            throw new CSILException("EditAccountPermissions", 20001);
          }
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        str = this.serviceErrorURL;
        return str;
      }
      HistoryTracker localHistoryTracker = jdMethod_new(localSecureUser, "EditAccountPermissions", this.abI, this.abw, this.abG);
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
  
  static HistoryTracker jdMethod_new(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2, String paramString3)
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
        } else if ("BusinessAdmin".equals(localEntitlementGroup.getEntGroupType())) {
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
    this.aby = null;
    this.abz = null;
    this.ab1 = null;
    this.abP = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.abK = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.ab2 = new Limits();
    this.abW = new Limits();
    this.abC = new Limits();
    this.abV = new ArrayList();
    int[] arrayOfInt = { 1, 2, 3, 4 };
    try
    {
      int i = (this.abT != null) && (!this.abT.equals("")) ? 1 : 0;
      int j;
      if (paramEntitlementGroupMember1 == null)
      {
        this.aby = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, this.abI);
        this.ab1 = com.ffusion.csil.core.Entitlements.getGroupLimits(this.abI);
        localObject1 = com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.abI);
        j = ((EntitlementGroup)localObject1).getParentId();
      }
      else
      {
        this.aby = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1);
        this.ab1 = com.ffusion.csil.core.Entitlements.getGroupLimits(paramEntitlementGroupMember1);
        j = paramEntitlementGroupMember1.getEntitlementGroupId();
      }
      this.abz = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, j);
      Object localObject1 = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.abZ);
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.abH);
      this.abY = null;
      int k = 0;
      int m = 0;
      if (i != 0)
      {
        this.abY = ((EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.abT));
        m = this.abY.size();
      }
      else
      {
        this.abY = ((EntitlementTypePropertyLists)((EntitlementTypePropertyLists)localObject1).clone());
        this.abY.addAll(localEntitlementTypePropertyLists);
        k = ((EntitlementTypePropertyLists)localObject1).size();
        m = k + localEntitlementTypePropertyLists.size();
      }
      HashSet localHashSet = new HashSet(m * 4 / 3 + 1);
      String str2 = null;
      String str3 = null;
      ListIterator localListIterator = this.abY.listIterator();
      while (localListIterator.hasNext())
      {
        localObject2 = (EntitlementTypePropertyList)localListIterator.next();
        localHashSet.add(((EntitlementTypePropertyList)localObject2).getOperationName());
        if (((EntitlementTypePropertyList)localObject2).isPropertySet("admin partner")) {
          localHashSet.add(((EntitlementTypePropertyList)localObject2).getPropertyValue("admin partner", 0));
        }
      }
      Object localObject4;
      Object localObject5;
      boolean bool;
      for (int n = 0; n < m; n++)
      {
        str2 = (String)paramHttpSession.getAttribute("entitlement" + n);
        str3 = (String)paramHttpSession.getAttribute("admin" + n);
        localObject2 = (EntitlementTypePropertyList)this.abY.get(n);
        localObject3 = new Entitlement(((EntitlementTypePropertyList)localObject2).getOperationName(), getObjectType(), this.abA);
        Entitlement localEntitlement2 = new Entitlement(((EntitlementTypePropertyList)localObject2).getPropertyValue("admin partner", 0), getObjectType(), this.abA);
        if ((!((EntitlementTypePropertyList)localObject2).isPropertySet("DisplayRow")) || (!((EntitlementTypePropertyList)localObject2).getPropertyValue("DisplayRow", 0).equals("yes")))
        {
          localHashSet.remove(((Entitlement)localObject3).getOperationName());
          localHashSet.remove(localEntitlement2.getOperationName());
        }
        else
        {
          if (new Boolean(((EntitlementTypePropertyList)localObject2).getPropertyValue("CanAdminRow", 0)).booleanValue())
          {
            if (str3 == null)
            {
              if (!this.aby.contains(localEntitlement2)) {
                this.abP.add(localEntitlement2);
              }
            }
            else
            {
              localHashSet.remove(str3);
              if (this.aby.contains(localEntitlement2)) {
                this.abK.add(localEntitlement2);
              }
            }
            this.aby.remove(localEntitlement2);
          }
          else
          {
            localHashSet.remove(localEntitlement2.getOperationName());
          }
          if (new Boolean(((EntitlementTypePropertyList)localObject2).getPropertyValue("CanInitRow", 0)).booleanValue())
          {
            if (str2 == null)
            {
              if (!this.aby.contains(localObject3)) {
                this.abP.add(localObject3);
              }
            }
            else
            {
              localHashSet.remove(str2);
              if (this.aby.contains(localObject3)) {
                this.abK.add(localObject3);
              }
            }
          }
          else {
            localHashSet.remove(((Entitlement)localObject3).getOperationName());
          }
          this.aby.remove(localObject3);
          localObject4 = new String[] { (String)paramHttpSession.getAttribute("transaction_limit" + n), (String)paramHttpSession.getAttribute("day_limit" + n), (String)paramHttpSession.getAttribute("week_limit" + n), (String)paramHttpSession.getAttribute("month_limit" + n) };
          localObject5 = new String[] { (String)paramHttpSession.getAttribute("transaction_exceed" + n), (String)paramHttpSession.getAttribute("day_exceed" + n), (String)paramHttpSession.getAttribute("week_exceed" + n), (String)paramHttpSession.getAttribute("month_exceed" + n) };
          if ((n < k) || ((i != 0) && (((EntitlementTypePropertyList)this.abY.get(n)).isPropertySet("isLimit")))) {
            for (int i2 = 0; i2 < arrayOfInt.length; i2++)
            {
              this.error = TaskUtil.validateLimitAmount(localObject4[i2]);
              if (this.error != 0) {
                return this.taskErrorURL;
              }
              if (localObject5[i2] == null) {
                bool = false;
              } else {
                bool = localObject5[i2].equalsIgnoreCase("true");
              }
              Limit localLimit1 = new Limit(this.abI, arrayOfInt[i2], localObject4[i2], (Entitlement)localObject3, bool);
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
              for (int i5 = 0; i5 < this.ab1.size(); i5++)
              {
                Limit localLimit2 = (Limit)this.ab1.get(i5);
                if (localLimit2.isLimitInfoIdentical(localLimit1))
                {
                  if ((localObject4[i2] == null) || (localObject4[i2].equals("")))
                  {
                    this.abC.add(localLimit2);
                  }
                  else if ((!localObject4[i2].equals(localLimit2.getData())) || (!localLimit1.getAllowApproval().equals(localLimit2.getAllowApproval())))
                  {
                    this.abV.add(localLimit2.getData());
                    localLimit2.setData(localObject4[i2]);
                    localLimit2.setAllowApproval(localLimit1.isAllowedApproval());
                    this.abW.add(localLimit2);
                  }
                  i4 = 1;
                  break;
                }
              }
              if ((i4 == 0) && (localObject4[i2] != null) && (!localObject4[i2].equals(""))) {
                this.ab2.add(localLimit1);
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
        localEntitlement1 = new Entitlement(str2, getObjectType(), this.abA);
        if (((StringBuffer)localObject2).length() == 0) {
          ((StringBuffer)localObject2).append("'" + str2 + "'");
        } else {
          ((StringBuffer)localObject2).append(", '" + str2 + "'");
        }
        if (!this.aby.contains(localEntitlement1)) {
          this.aby.add(localEntitlement1);
        }
        for (int i1 = 0; i1 < this.ab1.size(); i1++)
        {
          localObject4 = (Limit)this.ab1.get(i1);
          localObject5 = ((Limit)localObject4).getEntitlement();
          if (localEntitlement1.equals(localObject5)) {
            this.abC.add(localObject4);
          }
        }
      }
      this.abJ = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      localObject3 = this.abY.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject3).next();
        localObject4 = new Entitlement(localEntitlementTypePropertyList.getOperationName(), getObjectType(), this.abA);
        localObject5 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
        Entitlement localEntitlement3 = new Entitlement((String)localObject5, getObjectType(), this.abA);
        bool = jdMethod_int(paramEntitlementGroupMember1, localEntitlementTypePropertyList, (Entitlement)localObject4, localEntitlement3, false);
        if ((!this._initOnly) && (!bool))
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
      LocalizableAccountID localLocalizableAccountID;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      if (this.abP.size() + this.abK.size() != 0)
      {
        if (paramEntitlementGroupMember1 == null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, this.abI, this.aby);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1, this.aby);
        }
        LocalizableProperty localLocalizableProperty;
        String str6;
        for (j = 0; j < this.abP.size(); j++)
        {
          localObject1 = (Entitlement)this.abP.get(j);
          str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localLocalizableAccountID = null;
          localObject2 = ((Entitlement)localObject1).getOperationName();
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localLocalizableProperty = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject3).getPropertiesMap(), (String)localObject2);
          if (((Entitlement)localObject1).getObjectId() == null)
          {
            localObject4 = new Object[2];
            localObject4[0] = str3;
            localObject4[1] = localLocalizableProperty;
            String str5 = "AuditMessage_EditAccountPermissions" + getContextAuditKey(str2) + "_1.1";
            localObject5 = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject4);
          }
          else
          {
            localObject4 = new Object[3];
            localObject4[0] = str3;
            localObject4[1] = localLocalizableProperty;
            try
            {
              localLocalizableAccountID = AccountUtil.buildLocalizableAccountID(((Entitlement)localObject1).getObjectId());
              localObject4[2] = localLocalizableAccountID;
            }
            catch (UtilException localUtilException4)
            {
              DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Entitlement)localObject1).getObjectId());
              localUtilException4.printStackTrace();
              localObject4[2] = ((Entitlement)localObject1).getObjectId();
            }
            str6 = "AuditMessage_EditAccountPermissions" + getContextAuditKey(str2) + "_1.2";
            localObject5 = new LocalizableString("com.ffusion.util.logging.audit.task", str6, (Object[])localObject4);
          }
          jdMethod_for(paramSecureUser, (ILocalizable)localObject5, i, str4, 3225, localLocalizableAccountID);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localObject1, this.abY);
        }
        for (j = 0; j < this.abK.size(); j++)
        {
          localObject1 = (Entitlement)this.abK.get(j);
          str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localLocalizableAccountID = null;
          localObject2 = ((Entitlement)localObject1).getOperationName();
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localLocalizableProperty = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject3).getPropertiesMap(), (String)localObject2);
          if (((Entitlement)localObject1).getObjectId() == null)
          {
            localObject4 = new Object[2];
            localObject4[0] = str3;
            localObject4[1] = localLocalizableProperty;
            str6 = "AuditMessage_EditAccountPermissions" + getContextAuditKey(str2) + "_2.1";
            localObject5 = new LocalizableString("com.ffusion.util.logging.audit.task", str6, (Object[])localObject4);
          }
          else
          {
            localObject4 = new Object[3];
            localObject4[0] = str3;
            localObject4[1] = localLocalizableProperty;
            try
            {
              localLocalizableAccountID = AccountUtil.buildLocalizableAccountID(((Entitlement)localObject1).getObjectId());
              localObject4[2] = localLocalizableAccountID;
            }
            catch (UtilException localUtilException5)
            {
              DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Entitlement)localObject1).getObjectId());
              localUtilException5.printStackTrace();
              localObject4[2] = ((Entitlement)localObject1).getObjectId();
            }
            String str7 = "AuditMessage_EditAccountPermissions" + getContextAuditKey(str2) + "_2.2";
            localObject5 = new LocalizableString("com.ffusion.util.logging.audit.task", str7, (Object[])localObject4);
          }
          jdMethod_for(paramSecureUser, (ILocalizable)localObject5, i, str4, 3225, localLocalizableAccountID);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localObject1, this.abY);
        }
      }
      for (int j = 0; j < this.ab2.size(); j++)
      {
        localObject1 = (Limit)this.ab2.get(j);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember2, (Limit)localObject1);
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localLocalizableAccountID = null;
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        localObject3 = null;
        if (((Limit)localObject1).getObjectId() != null) {
          try
          {
            localObject3 = AccountUtil.buildLocalizableAccountID(((Limit)localObject1).getObjectId());
            localLocalizableAccountID = (LocalizableAccountID)localObject3;
          }
          catch (UtilException localUtilException1)
          {
            DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Limit)localObject1).getObjectId());
            localUtilException1.printStackTrace();
            localObject3 = ((Limit)localObject1).getObjectId();
          }
        }
        Object[] arrayOfObject1;
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          arrayOfObject1 = new Object[3];
          arrayOfObject1[0] = ((Limit)localObject1).getData();
          arrayOfObject1[2] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject1[1] = str3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject1);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          arrayOfObject1 = new Object[4];
          arrayOfObject1[0] = ((Limit)localObject1).getData();
          arrayOfObject1[3] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject1[1] = str3;
          arrayOfObject1[2] = localObject2;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject1);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          arrayOfObject1 = new Object[4];
          arrayOfObject1[0] = ((Limit)localObject1).getData();
          arrayOfObject1[4] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject1[1] = str3;
          arrayOfObject1[2] = localObject3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject1);
        }
        else
        {
          arrayOfObject1 = new Object[5];
          arrayOfObject1[0] = ((Limit)localObject1).getData();
          arrayOfObject1[4] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject1[1] = str3;
          arrayOfObject1[2] = localObject2;
          arrayOfObject1[3] = localObject3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject1);
        }
        jdMethod_for(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225, localLocalizableAccountID);
        paramHistoryTracker.logLimitAdd((Limit)localObject1, this.abY);
      }
      for (j = 0; j < this.abW.size(); j++)
      {
        localObject1 = (Limit)this.abW.get(j);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember2, (Limit)this.abW.get(j));
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localLocalizableAccountID = null;
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        localObject3 = null;
        if (((Limit)localObject1).getObjectId() != null) {
          try
          {
            localObject3 = AccountUtil.buildLocalizableAccountID(((Limit)localObject1).getObjectId());
            localLocalizableAccountID = (LocalizableAccountID)localObject3;
          }
          catch (UtilException localUtilException2)
          {
            DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Limit)localObject1).getObjectId());
            localUtilException2.printStackTrace();
            localObject3 = ((Limit)localObject1).getObjectId();
          }
        }
        Object[] arrayOfObject2;
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          arrayOfObject2 = new Object[3];
          arrayOfObject2[0] = ((Limit)localObject1).getData();
          arrayOfObject2[2] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject2[1] = str3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject2);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          arrayOfObject2 = new Object[4];
          arrayOfObject2[0] = ((Limit)localObject1).getData();
          arrayOfObject2[3] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject2[1] = str3;
          arrayOfObject2[2] = localObject2;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject2);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          arrayOfObject2 = new Object[4];
          arrayOfObject2[0] = ((Limit)localObject1).getData();
          arrayOfObject2[3] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject2[1] = str3;
          arrayOfObject2[2] = localObject3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject2);
        }
        else
        {
          arrayOfObject2 = new Object[5];
          arrayOfObject2[0] = ((Limit)localObject1).getData();
          arrayOfObject2[4] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject2[1] = str3;
          arrayOfObject2[2] = localObject2;
          arrayOfObject2[3] = localObject3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject2);
        }
        jdMethod_for(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225, localLocalizableAccountID);
        paramHistoryTracker.logLimitChange((Limit)localObject1, this.abY, (String)this.abV.get(j));
      }
      for (j = 0; j < this.abC.size(); j++)
      {
        localObject1 = (Limit)this.abC.get(j);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember2, (Limit)this.abC.get(j));
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localLocalizableAccountID = null;
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        localObject3 = null;
        if (((Limit)localObject1).getObjectId() != null) {
          try
          {
            localObject3 = AccountUtil.buildLocalizableAccountID(((Limit)localObject1).getObjectId());
            localLocalizableAccountID = (LocalizableAccountID)localObject3;
          }
          catch (UtilException localUtilException3)
          {
            DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + ((Limit)localObject1).getObjectId());
            localUtilException3.printStackTrace();
            localObject3 = ((Limit)localObject1).getObjectId();
          }
        }
        Object[] arrayOfObject3;
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          arrayOfObject3 = new Object[3];
          arrayOfObject3[0] = ((Limit)localObject1).getData();
          arrayOfObject3[5] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject3[1] = str3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject3);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          arrayOfObject3 = new Object[4];
          arrayOfObject3[0] = ((Limit)localObject1).getData();
          arrayOfObject3[3] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject3[1] = str3;
          arrayOfObject3[2] = localObject2;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject3);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          arrayOfObject3 = new Object[4];
          arrayOfObject3[0] = ((Limit)localObject1).getData();
          arrayOfObject3[3] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject3[1] = str3;
          arrayOfObject3[2] = localObject3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject3);
        }
        else
        {
          arrayOfObject3 = new Object[5];
          arrayOfObject3[0] = ((Limit)localObject1).getData();
          arrayOfObject3[4] = ((Limit)localObject1).getCurrencyCode();
          arrayOfObject3[1] = str3;
          arrayOfObject3[2] = localObject2;
          arrayOfObject3[3] = localObject3;
          localObject5 = "AuditMessage_EditAccountPermissions" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, arrayOfObject3);
        }
        jdMethod_for(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225, localLocalizableAccountID);
        paramHistoryTracker.logLimitDelete((Limit)localObject1, this.abY);
      }
      if ((paramEntitlementGroupMember1 == null) && (!this._enrollingBusiness)) {
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.abI), this.abK, 5, this._autoEntitle, new HashMap());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    this.aby = null;
    this.abz = null;
    this.ab1 = null;
    this.abP = null;
    this.abK = null;
    this.ab2 = null;
    this.abW = null;
    this.abC = null;
    this.abV = null;
    this.abY = null;
    return str1;
  }
  
  private boolean jdMethod_int(EntitlementGroupMember paramEntitlementGroupMember, EntitlementTypePropertyList paramEntitlementTypePropertyList, Entitlement paramEntitlement1, Entitlement paramEntitlement2, boolean paramBoolean)
    throws CSILException
  {
    String str1 = "EditAccountPermissions.performParentChildCheck";
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
    if ((i == 0) && (!paramBoolean) && (this.aby.contains(paramEntitlement1))) {
      i = 1;
    }
    if ((j == 0) && (!paramBoolean) && (str2 != null) && (this.aby.contains(paramEntitlement2))) {
      j = 1;
    }
    if ((i != 0) && (j != 0)) {
      return true;
    }
    if ((i == 0) && (!paramBoolean) && (!this.abK.contains(paramEntitlement1))) {
      if (paramEntitlementGroupMember == null)
      {
        if (!com.ffusion.csil.core.Entitlements.checkEntitlement(this.abI, paramEntitlement1)) {
          i = 1;
        }
      }
      else if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement1)) {
        i = 1;
      }
    }
    if ((j == 0) && (!paramBoolean) && (!this.abK.contains(paramEntitlement2))) {
      if (paramEntitlementGroupMember == null)
      {
        if (!com.ffusion.csil.core.Entitlements.checkEntitlement(this.abI, paramEntitlement2)) {
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
      if ((this.abA == null) || (this.abA.equals(""))) {
        localEntitlement1 = new Entitlement(str3, null, null);
      } else {
        localEntitlement1 = new Entitlement(str3, getObjectType(), this.abA);
      }
      EntitlementTypePropertyList localEntitlementTypePropertyList = this.abJ.getByOperationName(str3);
      String str4 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
      Entitlement localEntitlement2 = null;
      if ((this.abA == null) || (this.abA.equals(""))) {
        localEntitlement2 = new Entitlement(str4, null, null);
      } else {
        localEntitlement2 = new Entitlement(str4, getObjectType(), this.abA);
      }
      if ((i == 0) && (this.abK.contains(localEntitlement1))) {
        i = 1;
      }
      if ((j == 0) && (this.abK.contains(localEntitlement2))) {
        j = 1;
      }
      if ((i == 0) && (this.aby.contains(localEntitlement1))) {
        return false;
      }
      if ((j == 0) && (str4 != null) && (this.aby.contains(localEntitlement2))) {
        return false;
      }
      if (getObjectType() != null)
      {
        localEntitlement1.setObjectType(null);
        localEntitlement1.setObjectId(null);
        localEntitlement2.setObjectType(null);
        localEntitlement2.setObjectId(null);
        if ((i == 0) && (this.aby.contains(localEntitlement1))) {
          return false;
        }
        if ((j == 0) && (str4 != null) && (this.aby.contains(localEntitlement2))) {
          return false;
        }
        localEntitlement1.setObjectType(getObjectType());
        localEntitlement1.setObjectId(this.abA);
        localEntitlement2.setObjectType(getObjectType());
        localEntitlement2.setObjectId(this.abA);
      }
    }
    return true;
  }
  
  private void jdMethod_for(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt1, String paramString, int paramInt2, LocalizableAccountID paramLocalizableAccountID)
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
    this.abw = paramString;
  }
  
  public String getMemberId()
  {
    return this.abw;
  }
  
  public void setMemberType(String paramString)
  {
    this.abX = paramString;
  }
  
  public String getMemberType()
  {
    return this.abX;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.abU = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.abU;
  }
  
  public void setEntitlementTypesWithLimits(String paramString)
  {
    this.abZ = paramString;
  }
  
  public void setEntitlementTypesMerged(String paramString)
  {
    this.abT = paramString;
  }
  
  public void setEntitlementTypesWithoutLimits(String paramString)
  {
    this.abH = paramString;
  }
  
  public void setAccountId(String paramString)
  {
    this.abA = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.abI = Integer.parseInt(paramString);
  }
  
  public void setProfileId(String paramString)
  {
    this.abG = paramString;
  }
  
  protected String getObjectType()
  {
    return "Account";
  }
  
  public void setSaveLastRequest(String paramString)
  {
    this.ab5 = paramString;
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
    this.aby = null;
    this.abz = null;
    this.ab1 = null;
    this.abP = null;
    this.abK = null;
    this.ab2 = null;
    this.abW = null;
    this.abC = null;
    this.abV = null;
    this.abY = null;
  }
  
  public String getInitOnly()
  {
    return new Boolean(this._initOnly).toString();
  }
  
  public String getNumGrantedEntitlements()
  {
    return Integer.toString(this.abK.size());
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
 * Qualified Name:     com.ffusion.tasks.admin.EditAccountPermissions
 * JD-Core Version:    0.7.0.1
 */