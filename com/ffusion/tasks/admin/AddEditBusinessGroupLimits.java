package com.ffusion.tasks.admin;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyList;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableProperty;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddEditBusinessGroupLimits
  extends AutoEntitleBaseTask
  implements AdminTask
{
  public static final String ENTITLEMENT = "entitlement";
  public static final String TRANSACTION_LIMIT = "transaction_limit";
  public static final String DAY_LIMIT = "day_limit";
  public static final String WEEK_LIMIT = "week_limit";
  public static final String MONTH_LIMIT = "month_limit";
  public static final String TRANSACTION_EXCEED = "transaction_exceed";
  public static final String DAY_EXCEED = "day_exceed";
  public static final String WEEK_EXCEED = "week_exceed";
  public static final String MONTH_EXCEED = "month_exceed";
  protected String _groupName = "EntitlementGroup";
  protected String _limitsName = "Limits";
  protected String _entitlementsName = "Entitlements";
  protected ArrayList _operations = null;
  protected int achCompanySize = 11;
  protected boolean _onlyConfirm = false;
  private static final String Yw = "com.ffusion.util.logging.audit.task";
  private static final String YD = "AuditMessage_AddEditBusinessGroupLimits_1.1";
  private static final String Yv = "AuditMessage_AddEditBusinessGroupLimits_1.2";
  private static final String Ys = "AuditMessage_AddEditBusinessGroupLimits_2.1";
  private static final String Yx = "AuditMessage_AddEditBusinessGroupLimits_2.2";
  private static final String Yp = "AuditMessage_AddEditBusinessGroupLimits";
  private static final String Yz = "_3.1";
  private static final String Yt = "_3.2";
  private static final String Yu = "_3.3";
  private static final String YA = "_3.4";
  private static final String Yq = "_4.1";
  private static final String Yr = "_4.2";
  private static final String YB = "_4.3";
  private static final String Yo = "_4.4";
  private static final String Yy = "_5.1";
  private static final String YC = "_5.2";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    if (localEntitlementGroupMember == null)
    {
      localEntitlementGroupMember = getGroupMember(localSecureUser, localEntitlementGroupMember);
      if (localEntitlementGroupMember == null) {
        return this.serviceErrorURL;
      }
      localHttpSession.setAttribute("EntitlementGroupMember", localEntitlementGroupMember);
    }
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute(this._groupName);
    if (localEntitlementGroup == null)
    {
      this.error = 4508;
      resetSessionNames();
      return super.getTaskErrorURL();
    }
    ArrayList localArrayList1 = (ArrayList)localHttpSession.getAttribute("NonlimitableOperationNames");
    if (localArrayList1 == null)
    {
      this.error = 4509;
      resetSessionNames();
      return super.getTaskErrorURL();
    }
    ArrayList localArrayList2 = (ArrayList)localHttpSession.getAttribute("LimitableOperationNames");
    if (localArrayList2 == null)
    {
      this.error = 4509;
      resetSessionNames();
      return super.getTaskErrorURL();
    }
    this._operations = ((ArrayList)localHttpSession.getAttribute("NonAccountOperationNames"));
    if (this._operations == null)
    {
      this.error = 4509;
      resetSessionNames();
      return super.getTaskErrorURL();
    }
    ArrayList localArrayList3 = (ArrayList)localHttpSession.getAttribute("EntitlementsDisplayedNames");
    if (localArrayList3 == null)
    {
      this.error = 4509;
      resetSessionNames();
      return super.getTaskErrorURL();
    }
    Limits localLimits1 = (Limits)localHttpSession.getAttribute(this._limitsName);
    if (localLimits1 == null)
    {
      this.error = 4505;
      resetSessionNames();
      return super.getTaskErrorURL();
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = (com.ffusion.csil.beans.entitlements.Entitlements)localHttpSession.getAttribute(this._entitlementsName);
    if (localEntitlements1 == null)
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
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements3 = new com.ffusion.csil.beans.entitlements.Entitlements();
    Limits localLimits2 = new Limits();
    Limits localLimits3 = new Limits();
    Limits localLimits4 = new Limits();
    HashMap localHashMap1 = getPreviousDataMap(localLimits1, localEntitlements1);
    HashMap localHashMap2 = getIndexMap(paramHttpServletRequest, localArrayList2, localArrayList1, localArrayList3);
    Map.Entry localEntry = null;
    String str = null;
    Integer localInteger = null;
    int i = -1;
    Object localObject = null;
    Entitlement localEntitlement = null;
    Limit[] arrayOfLimit = null;
    Iterator localIterator = localHashMap2.entrySet().iterator();
    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      str = (String)localEntry.getKey();
      localInteger = (Integer)localEntry.getValue();
      if ((this._operations != null) && (this._operations.indexOf(str) >= 0)) {
        localObject = localHashMap1.get(str + "_null_null");
      } else if (str.startsWith("ACHCompany")) {
        localObject = localHashMap1.get("ACHBatch_ACHCompany_" + str.substring(this.achCompanySize));
      } else {
        localObject = localHashMap1.get(str + "_" + "Account" + "_" + EntitlementsUtil.getEntitlementObjectId(localAccount));
      }
      if (localInteger == null)
      {
        if ((localObject == null) || ((localObject instanceof Limit[])))
        {
          if ((this._operations != null) && (this._operations.indexOf(str) >= 0)) {
            localEntitlement = new Entitlement(str, null, null);
          } else if (str.startsWith("ACHCompany")) {
            localEntitlement = new Entitlement("ACHBatch", "ACHCompany", str.substring(this.achCompanySize));
          } else {
            localEntitlement = new Entitlement(str, "Account", EntitlementsUtil.getEntitlementObjectId(localAccount));
          }
          localEntitlements2.add(localEntitlement);
          if ((localObject instanceof Limit[]))
          {
            arrayOfLimit = (Limit[])localObject;
            if (arrayOfLimit[0] != null) {
              localLimits4.add(arrayOfLimit[0]);
            }
            if (arrayOfLimit[1] != null) {
              localLimits4.add(arrayOfLimit[1]);
            }
            if (arrayOfLimit[2] != null) {
              localLimits4.add(arrayOfLimit[2]);
            }
            if (arrayOfLimit[3] != null) {
              localLimits4.add(arrayOfLimit[3]);
            }
          }
        }
      }
      else
      {
        i = localInteger.intValue();
        int j = isMoreRestrictiveThanCumulativeLimit(paramHttpServletRequest, i, localAccount, str, localEntitlementGroupMember, localEntitlementGroup.getParentId());
        if (j != 0)
        {
          this.error = j;
          resetSessionNames();
          return this.serviceErrorURL;
        }
        if (localObject == null)
        {
          if (!localArrayList1.contains(str))
          {
            setLimitRestrictions(paramHttpServletRequest, i, localEntitlementGroup, str, null, localAccount, localLocale, localLimits2);
            if (j != 0) {
              return this.taskErrorURL;
            }
          }
        }
        else if ((localObject instanceof Limit[]))
        {
          if (!localArrayList1.contains(str))
          {
            addEditLimitRestrictions(localObject, paramHttpServletRequest, i, localEntitlementGroup, null, str, localAccount, localLocale, localLimits2, localLimits4, localLimits3);
            if (j != 0) {
              return this.taskErrorURL;
            }
          }
        }
        else
        {
          localEntitlements3.add(localObject);
          if (!localArrayList1.contains(str))
          {
            setLimitRestrictions(paramHttpServletRequest, i, localEntitlementGroup, str, null, localAccount, localLocale, localLimits2);
            if (j != 0) {
              return this.taskErrorURL;
            }
          }
        }
      }
    }
    saveEntLimitRestrictions(localEntitlements2, localEntitlementGroupMember, localEntitlementGroup, localSecureUser, localLocale, localEntitlements3, localLimits2, localLimits3, localLimits4);
    if (this.error != 0) {
      return this.serviceErrorURL;
    }
    resetSessionNames();
    return super.getSuccessURL();
  }
  
  protected void saveEntLimitRestrictions(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements1, EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup, SecureUser paramSecureUser, Locale paramLocale, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements2, Limits paramLimits1, Limits paramLimits2, Limits paramLimits3)
  {
    try
    {
      Object localObject1;
      String str1;
      Object[] arrayOfObject;
      LocalizableString localLocalizableString;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject6;
      LocalizableProperty localLocalizableProperty;
      if (paramEntitlements1.size() > 0)
      {
        com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlementGroup.getGroupId(), paramEntitlements1);
        for (i = 0; i < paramEntitlements1.size(); i++)
        {
          localObject1 = (Entitlement)paramEntitlements1.get(i);
          str1 = TrackingIDGenerator.GetNextID();
          arrayOfObject = null;
          localLocalizableString = null;
          localObject2 = ((Entitlement)localObject1).getObjectType();
          localObject3 = null;
          if (localObject2 != null)
          {
            localObject4 = null;
            try
            {
              localObject4 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties((String)localObject2);
            }
            catch (CSILException localCSILException2) {}
            if (localObject4 != null) {
              localObject3 = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject4).getPropertiesMap(), (String)localObject2);
            }
          }
          localObject4 = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
          String str2 = ((Entitlement)localObject1).getOperationName();
          localObject6 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str2);
          localLocalizableProperty = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject6).getPropertiesMap(), str2);
          if ((localObject2 != null) && (((Entitlement)localObject1).getObjectId() != null))
          {
            arrayOfObject = new Object[5];
            arrayOfObject[0] = localObject4;
            arrayOfObject[1] = paramEntitlementGroup.getGroupName();
            arrayOfObject[2] = localLocalizableProperty;
            arrayOfObject[3] = localObject2;
            arrayOfObject[4] = ((Entitlement)localObject1).getObjectId();
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AddEditBusinessGroupLimits_1.2", arrayOfObject);
          }
          else
          {
            arrayOfObject = new Object[3];
            arrayOfObject[0] = localObject4;
            arrayOfObject[1] = paramEntitlementGroup.getGroupName();
            arrayOfObject[2] = localLocalizableProperty;
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AddEditBusinessGroupLimits_1.1", arrayOfObject);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, str1, 3225);
        }
      }
      Object localObject5;
      if (paramEntitlements2.size() > 0)
      {
        com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlementGroup.getGroupId(), paramEntitlements2);
        for (i = 0; i < paramEntitlements2.size(); i++)
        {
          localObject1 = (Entitlement)paramEntitlements2.get(i);
          str1 = TrackingIDGenerator.GetNextID();
          arrayOfObject = null;
          localLocalizableString = null;
          localObject2 = ((Entitlement)localObject1).getObjectType();
          localObject3 = null;
          if (localObject2 != null)
          {
            localObject4 = null;
            try
            {
              localObject4 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties((String)localObject2);
            }
            catch (CSILException localCSILException3) {}
            if (localObject4 != null) {
              localObject3 = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject4).getPropertiesMap(), (String)localObject2);
            }
          }
          localObject4 = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
          localObject5 = ((Entitlement)localObject1).getOperationName();
          localObject6 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject5);
          localLocalizableProperty = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject6).getPropertiesMap(), (String)localObject5);
          if ((localObject2 != null) && (((Entitlement)localObject1).getObjectId() != null))
          {
            arrayOfObject = new Object[5];
            arrayOfObject[0] = localObject4;
            arrayOfObject[1] = paramEntitlementGroup.getGroupName();
            arrayOfObject[2] = localLocalizableProperty;
            arrayOfObject[3] = localObject2;
            arrayOfObject[4] = ((Entitlement)localObject1).getObjectId();
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AddEditBusinessGroupLimits_2.2", arrayOfObject);
          }
          else
          {
            arrayOfObject = new Object[3];
            arrayOfObject[0] = localObject4;
            arrayOfObject[1] = paramEntitlementGroup.getGroupName();
            arrayOfObject[2] = localLocalizableProperty;
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AddEditBusinessGroupLimits_2.1", arrayOfObject);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, str1, 3225);
        }
      }
      for (int i = 0; i < paramLimits1.size(); i++)
      {
        localObject1 = (Limit)paramLimits1.get(i);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember, (Limit)localObject1);
        str1 = TrackingIDGenerator.GetNextID();
        arrayOfObject = null;
        localLocalizableString = null;
        localObject2 = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
        localObject3 = ((Limit)localObject1).getEntitlement().getOperationName();
        localObject4 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject3);
        localObject5 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject4).getPropertiesMap(), (String)localObject3);
        localObject6 = ((Limit)localObject1).getEntitlement().getObjectType();
        localLocalizableProperty = null;
        Object localObject7;
        if (localObject6 != null)
        {
          localObject7 = null;
          try
          {
            localObject7 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties((String)localObject6);
          }
          catch (CSILException localCSILException4) {}
          if (localObject7 != null) {
            localLocalizableProperty = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject7).getPropertiesMap(), (String)localObject6);
          }
        }
        if ((localObject6 != null) && (((Limit)localObject1).getEntitlement().getObjectId() != null))
        {
          arrayOfObject = new Object[6];
          arrayOfObject[0] = localObject2;
          arrayOfObject[1] = paramEntitlementGroup.getGroupName();
          arrayOfObject[2] = localObject5;
          arrayOfObject[3] = ((Limit)localObject1).getAmount();
          arrayOfObject[4] = localLocalizableProperty;
          arrayOfObject[5] = ((Limit)localObject1).getEntitlement().getObjectId();
          arrayOfObject[6] = ((Limit)localObject1).getCurrencyCode();
          localObject7 = null;
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject7 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_3.4";
          } else {
            localObject7 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_3.2";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject7, arrayOfObject);
        }
        else
        {
          arrayOfObject = new Object[4];
          arrayOfObject[0] = localObject2;
          arrayOfObject[1] = paramEntitlementGroup.getGroupName();
          arrayOfObject[2] = localObject5;
          arrayOfObject[3] = ((Limit)localObject1).getAmount();
          arrayOfObject[4] = ((Limit)localObject1).getCurrencyCode();
          localObject7 = null;
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject7 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_3.3";
          } else {
            localObject7 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_3.1";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject7, arrayOfObject);
        }
        try
        {
          localObject7 = new Currency(((Limit)localObject1).getData(), paramLocale, paramLocale.getISO3Country());
          Initialize.audit(paramSecureUser, localLocalizableString, str1, 3225, (Currency)localObject7);
        }
        catch (Exception localException1)
        {
          Initialize.audit(paramSecureUser, localLocalizableString, str1, 3225);
        }
      }
      for (i = 0; i < paramLimits2.size(); i++)
      {
        localObject1 = (Limit)paramLimits2.get(i);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember, (Limit)localObject1);
        str1 = TrackingIDGenerator.GetNextID();
        arrayOfObject = null;
        localLocalizableString = null;
        localObject2 = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
        localObject3 = ((Limit)localObject1).getEntitlement().getOperationName();
        localObject4 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject3);
        localObject5 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject4).getPropertiesMap(), (String)localObject3);
        localObject6 = ((Limit)localObject1).getEntitlement().getObjectType();
        localLocalizableProperty = null;
        Object localObject8;
        if (localObject6 != null)
        {
          localObject8 = null;
          try
          {
            localObject8 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties((String)localObject6);
          }
          catch (CSILException localCSILException5) {}
          if (localObject8 != null) {
            localLocalizableProperty = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject8).getPropertiesMap(), (String)localObject6);
          }
        }
        if ((localObject6 != null) && (((Limit)localObject1).getEntitlement().getObjectId() != null))
        {
          arrayOfObject = new Object[6];
          arrayOfObject[0] = localObject2;
          arrayOfObject[1] = paramEntitlementGroup.getGroupName();
          arrayOfObject[2] = localObject5;
          arrayOfObject[3] = ((Limit)localObject1).getAmount();
          arrayOfObject[4] = localLocalizableProperty;
          arrayOfObject[5] = ((Limit)localObject1).getEntitlement().getObjectId();
          localObject8 = null;
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject8 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_4.4";
          } else {
            localObject8 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_4.2";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject8, arrayOfObject);
        }
        else
        {
          arrayOfObject = new Object[4];
          arrayOfObject[0] = localObject2;
          arrayOfObject[1] = paramEntitlementGroup.getGroupName();
          arrayOfObject[2] = localObject5;
          arrayOfObject[3] = ((Limit)localObject1).getAmount();
          localObject8 = null;
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject8 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_4.3";
          } else {
            localObject8 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_4.1";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject8, arrayOfObject);
        }
        try
        {
          localObject8 = new Currency(((Limit)localObject1).getData(), paramLocale, paramLocale.getISO3Country());
          Initialize.audit(paramSecureUser, localLocalizableString, str1, 3225, (Currency)localObject8);
        }
        catch (Exception localException2)
        {
          Initialize.audit(paramSecureUser, localLocalizableString, str1, 3225);
        }
      }
      for (i = 0; i < paramLimits3.size(); i++)
      {
        localObject1 = (Limit)paramLimits3.get(i);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember, (Limit)localObject1);
        str1 = TrackingIDGenerator.GetNextID();
        arrayOfObject = null;
        localLocalizableString = null;
        localObject2 = new LocalizableString("com.ffusion.beans.reporting.reports", "Ent_Group_Type." + paramEntitlementGroup.getEntGroupType(), null);
        localObject3 = ((Limit)localObject1).getEntitlement().getOperationName();
        localObject4 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject3);
        localObject5 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject4).getPropertiesMap(), (String)localObject3);
        localObject6 = ((Limit)localObject1).getEntitlement().getObjectType();
        localLocalizableProperty = null;
        Object localObject9;
        if (localObject6 != null)
        {
          localObject9 = null;
          try
          {
            localObject9 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties((String)localObject6);
          }
          catch (CSILException localCSILException6) {}
          if (localObject9 != null) {
            localLocalizableProperty = new LocalizableProperty("display name", ((ObjectTypePropertyList)localObject9).getPropertiesMap(), (String)localObject6);
          }
        }
        if ((localObject6 != null) && (((Limit)localObject1).getEntitlement().getObjectId() != null))
        {
          arrayOfObject = new Object[5];
          arrayOfObject[0] = localObject2;
          arrayOfObject[1] = paramEntitlementGroup.getGroupName();
          arrayOfObject[2] = localObject5;
          arrayOfObject[3] = localLocalizableProperty;
          arrayOfObject[4] = ((Limit)localObject1).getEntitlement().getObjectId();
          localObject9 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_5.2";
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject9, arrayOfObject);
        }
        else
        {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = localObject2;
          arrayOfObject[1] = paramEntitlementGroup.getGroupName();
          arrayOfObject[2] = localObject5;
          localObject9 = "AuditMessage_AddEditBusinessGroupLimits" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + "_5.1";
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject9, arrayOfObject);
        }
        try
        {
          localObject9 = new Currency(((Limit)localObject1).getData(), paramLocale, paramLocale.getISO3Country());
          Initialize.audit(paramSecureUser, localLocalizableString, str1, 3225, (Currency)localObject9);
        }
        catch (Exception localException3)
        {
          Initialize.audit(paramSecureUser, localLocalizableString, str1, 3225);
        }
      }
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      resetSessionNames();
    }
  }
  
  protected void addEditLimitRestrictions(Object paramObject, HttpServletRequest paramHttpServletRequest, int paramInt, EntitlementGroup paramEntitlementGroup, EntitlementGroupMember paramEntitlementGroupMember, String paramString, Account paramAccount, Locale paramLocale, Limits paramLimits1, Limits paramLimits2, Limits paramLimits3)
  {
    Limit localLimit1 = null;
    Limit localLimit2 = null;
    Limit localLimit3 = null;
    Limit localLimit4 = null;
    Limit[] arrayOfLimit = (Limit[])paramObject;
    try
    {
      if (arrayOfLimit[0] == null)
      {
        localLimit1 = createLimit(paramHttpServletRequest, paramInt, paramEntitlementGroup.getGroupId(), 1, paramString, paramAccount, paramEntitlementGroupMember, paramLocale);
        if (localLimit1 != null) {
          paramLimits1.add(localLimit1);
        }
      }
      else
      {
        localLimit1 = updateLimit(paramHttpServletRequest, paramInt, arrayOfLimit[0], paramEntitlementGroupMember, paramLocale);
        if (localLimit1 == null) {
          paramLimits2.add(arrayOfLimit[0]);
        } else {
          paramLimits3.add(localLimit1);
        }
      }
    }
    catch (a locala1)
    {
      this.error = 4530;
    }
    if (this.error == 0) {
      try
      {
        if (arrayOfLimit[1] == null)
        {
          localLimit2 = createLimit(paramHttpServletRequest, paramInt, paramEntitlementGroup.getGroupId(), 2, paramString, paramAccount, paramEntitlementGroupMember, paramLocale);
          if (localLimit2 != null) {
            paramLimits1.add(localLimit2);
          }
        }
        else
        {
          localLimit2 = updateLimit(paramHttpServletRequest, paramInt, arrayOfLimit[1], paramEntitlementGroupMember, paramLocale);
          if (localLimit2 == null) {
            paramLimits2.add(arrayOfLimit[1]);
          } else {
            paramLimits3.add(localLimit2);
          }
        }
      }
      catch (a locala2)
      {
        this.error = 4533;
      }
    }
    if (this.error == 0) {
      try
      {
        if (arrayOfLimit[2] == null)
        {
          localLimit3 = createLimit(paramHttpServletRequest, paramInt, paramEntitlementGroup.getGroupId(), 3, paramString, paramAccount, paramEntitlementGroupMember, paramLocale);
          if (localLimit3 != null) {
            paramLimits1.add(localLimit3);
          }
        }
        else
        {
          localLimit3 = updateLimit(paramHttpServletRequest, paramInt, arrayOfLimit[2], paramEntitlementGroupMember, paramLocale);
          if (localLimit3 == null) {
            paramLimits2.add(arrayOfLimit[2]);
          } else {
            paramLimits3.add(localLimit3);
          }
        }
      }
      catch (a locala3)
      {
        this.error = 4532;
      }
    }
    if (this.error == 0) {
      try
      {
        if (arrayOfLimit[3] == null)
        {
          localLimit4 = createLimit(paramHttpServletRequest, paramInt, paramEntitlementGroup.getGroupId(), 4, paramString, paramAccount, paramEntitlementGroupMember, paramLocale);
          if (localLimit4 != null) {
            paramLimits1.add(localLimit4);
          }
        }
        else
        {
          localLimit4 = updateLimit(paramHttpServletRequest, paramInt, arrayOfLimit[3], paramEntitlementGroupMember, paramLocale);
          if (localLimit4 == null) {
            paramLimits2.add(arrayOfLimit[3]);
          } else {
            paramLimits3.add(localLimit4);
          }
        }
      }
      catch (a locala4)
      {
        this.error = 4533;
      }
    }
    if (this.error == 0) {
      this.error = CheckLimitRange(localLimit1, localLimit2, localLimit3, localLimit4);
    }
  }
  
  protected void setLimitRestrictions(HttpServletRequest paramHttpServletRequest, int paramInt, EntitlementGroup paramEntitlementGroup, String paramString, EntitlementGroupMember paramEntitlementGroupMember, Account paramAccount, Locale paramLocale, Limits paramLimits)
  {
    Limit localLimit1 = null;
    Limit localLimit2 = null;
    Limit localLimit3 = null;
    Limit localLimit4 = null;
    try
    {
      localLimit1 = createLimit(paramHttpServletRequest, paramInt, paramEntitlementGroup.getGroupId(), 1, paramString, paramAccount, paramEntitlementGroupMember, paramLocale);
      if (localLimit1 != null) {
        paramLimits.add(localLimit1);
      }
    }
    catch (a locala1)
    {
      this.error = 4530;
    }
    if (this.error == 0) {
      try
      {
        localLimit2 = createLimit(paramHttpServletRequest, paramInt, paramEntitlementGroup.getGroupId(), 2, paramString, paramAccount, paramEntitlementGroupMember, paramLocale);
        if (localLimit2 != null) {
          paramLimits.add(localLimit2);
        }
      }
      catch (a locala2)
      {
        this.error = 4533;
      }
    }
    if (this.error == 0) {
      try
      {
        localLimit3 = createLimit(paramHttpServletRequest, paramInt, paramEntitlementGroup.getGroupId(), 3, paramString, paramAccount, paramEntitlementGroupMember, paramLocale);
        if (localLimit3 != null) {
          paramLimits.add(localLimit3);
        }
      }
      catch (a locala3)
      {
        this.error = 4532;
      }
    }
    if (this.error == 0) {
      try
      {
        localLimit4 = createLimit(paramHttpServletRequest, paramInt, paramEntitlementGroup.getGroupId(), 4, paramString, paramAccount, paramEntitlementGroupMember, paramLocale);
        if (localLimit4 != null) {
          paramLimits.add(localLimit4);
        }
      }
      catch (a locala4)
      {
        this.error = 4533;
      }
    }
    if (this.error == 0) {
      this.error = CheckLimitRange(localLimit1, localLimit2, localLimit3, localLimit4);
    }
  }
  
  protected EntitlementGroupMember getGroupMember(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember)
  {
    try
    {
      paramEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      paramEntitlementGroupMember = com.ffusion.csil.core.Entitlements.getMember(paramEntitlementGroupMember);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      resetSessionNames();
      return null;
    }
    return paramEntitlementGroupMember;
  }
  
  protected String getPeriodString(int paramInt)
  {
    if (paramInt == 1) {
      return "per transaction";
    }
    if (paramInt == 2) {
      return "per day";
    }
    if (paramInt == 3) {
      return "per week";
    }
    if (paramInt == 4) {
      return "per month";
    }
    return "";
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
  
  protected HashMap getPreviousDataMap(Limits paramLimits, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
  {
    HashMap localHashMap = new HashMap((paramLimits.size() + paramEntitlements.size()) * 4 / 3 + 1);
    Limit localLimit = null;
    Limit[] arrayOfLimit = null;
    String str = null;
    Object localObject = paramLimits.listIterator();
    while (((ListIterator)localObject).hasNext())
    {
      localLimit = (Limit)((ListIterator)localObject).next();
      if (localLimit.getLimitName() != null)
      {
        str = localLimit.getLimitName() + "_" + localLimit.getObjectType() + "_" + localLimit.getObjectId();
        arrayOfLimit = (Limit[])localHashMap.get(str);
        if (arrayOfLimit == null)
        {
          arrayOfLimit = new Limit[4];
          localHashMap.put(str, arrayOfLimit);
        }
        if (localLimit.getPeriod() == 1) {
          arrayOfLimit[0] = localLimit;
        } else if (localLimit.getPeriod() == 2) {
          arrayOfLimit[1] = localLimit;
        } else if (localLimit.getPeriod() == 3) {
          arrayOfLimit[2] = localLimit;
        } else if (localLimit.getPeriod() == 4) {
          arrayOfLimit[3] = localLimit;
        }
      }
    }
    localObject = null;
    ListIterator localListIterator = paramEntitlements.listIterator();
    while (localListIterator.hasNext())
    {
      localObject = (Entitlement)localListIterator.next();
      if (((Entitlement)localObject).getOperationName() != null)
      {
        str = ((Entitlement)localObject).getOperationName() + "_" + ((Entitlement)localObject).getObjectType() + "_" + ((Entitlement)localObject).getObjectId();
        localHashMap.put(str, localObject);
      }
    }
    return localHashMap;
  }
  
  protected HashMap getIndexMap(HttpServletRequest paramHttpServletRequest, ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3)
  {
    HashMap localHashMap = new HashMap(paramArrayList3.size() * 4 / 3 + 1);
    LinkedList localLinkedList = null;
    String str1 = null;
    String str2 = null;
    localLinkedList = new LinkedList();
    localLinkedList.addAll(paramArrayList1);
    localLinkedList.addAll(paramArrayList2);
    for (int i = 0; i < paramArrayList1.size() + paramArrayList2.size(); i++)
    {
      str1 = (String)paramHttpServletRequest.getSession().getAttribute("entitlement" + i);
      if (str1 != null)
      {
        localHashMap.put(str1, new Integer(i));
        localLinkedList.remove(str1);
      }
    }
    ListIterator localListIterator = localLinkedList.listIterator();
    while (localListIterator.hasNext())
    {
      str1 = (String)localListIterator.next();
      if (paramArrayList3.contains(str1)) {
        localHashMap.put(str1, null);
      }
    }
    for (int j = 0; j < paramArrayList3.size(); j++)
    {
      str2 = (String)paramArrayList3.get(j);
      if (!localHashMap.containsKey(str2)) {
        localHashMap.put(str2, null);
      }
    }
    return localHashMap;
  }
  
  protected Limit createLimit(HttpServletRequest paramHttpServletRequest, int paramInt1, int paramInt2, int paramInt3, String paramString, Account paramAccount, EntitlementGroupMember paramEntitlementGroupMember, Locale paramLocale)
    throws AddEditBusinessGroupLimits.a
  {
    String str1 = null;
    if (paramString.startsWith("ACHCompany")) {
      str1 = paramString.substring(this.achCompanySize);
    } else {
      str1 = EntitlementsUtil.getEntitlementObjectId(paramAccount);
    }
    String str2 = null;
    Boolean localBoolean = null;
    Entitlement localEntitlement = null;
    if ((this._operations != null) && (this._operations.indexOf(paramString) >= 0)) {
      localEntitlement = new Entitlement(paramString, null, null);
    } else if (paramString.startsWith("ACHCompany")) {
      localEntitlement = new Entitlement("ACHBatch", "ACHCompany", str1);
    } else {
      localEntitlement = new Entitlement(paramString, "Account", str1);
    }
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (paramInt3 == 1)
    {
      str2 = (String)localHttpSession.getAttribute("transaction_limit" + paramInt1);
      localBoolean = Boolean.valueOf((String)localHttpSession.getAttribute("transaction_exceed" + paramInt1));
    }
    else if (paramInt3 == 2)
    {
      str2 = (String)localHttpSession.getAttribute("day_limit" + paramInt1);
      localBoolean = Boolean.valueOf((String)localHttpSession.getAttribute("day_exceed" + paramInt1));
    }
    else if (paramInt3 == 3)
    {
      str2 = (String)localHttpSession.getAttribute("week_limit" + paramInt1);
      localBoolean = Boolean.valueOf((String)localHttpSession.getAttribute("week_exceed" + paramInt1));
    }
    else if (paramInt3 == 4)
    {
      str2 = (String)localHttpSession.getAttribute("month_limit" + paramInt1);
      localBoolean = Boolean.valueOf((String)localHttpSession.getAttribute("month_exceed" + paramInt1));
    }
    if ((str2 == null) || (str2.trim().length() == 0)) {
      return null;
    }
    if (!Currency.isValid(str2, paramLocale)) {
      throw new a();
    }
    Limit localLimit = new Limit(paramInt2, paramInt3, str2, localEntitlement, localBoolean.booleanValue());
    if (paramEntitlementGroupMember != null)
    {
      localLimit.setMemberId(paramEntitlementGroupMember.getId());
      localLimit.setMemberType(paramEntitlementGroupMember.getMemberType());
      localLimit.setMemberSubType(paramEntitlementGroupMember.getMemberSubType());
    }
    return localLimit;
  }
  
  protected Limit updateLimit(HttpServletRequest paramHttpServletRequest, int paramInt, Limit paramLimit, EntitlementGroupMember paramEntitlementGroupMember, Locale paramLocale)
    throws AddEditBusinessGroupLimits.a
  {
    Boolean localBoolean = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (paramLimit.getPeriod() == 1)
    {
      localBoolean = Boolean.valueOf((String)localHttpSession.getAttribute("transaction_exceed" + paramInt));
      paramLimit.setData((String)localHttpSession.getAttribute("transaction_limit" + paramInt));
      paramLimit.setAllowApproval(localBoolean.booleanValue());
    }
    else if (paramLimit.getPeriod() == 2)
    {
      localBoolean = Boolean.valueOf((String)localHttpSession.getAttribute("day_exceed" + paramInt));
      paramLimit.setData((String)localHttpSession.getAttribute("day_limit" + paramInt));
      paramLimit.setAllowApproval(localBoolean.booleanValue());
    }
    else if (paramLimit.getPeriod() == 3)
    {
      localBoolean = Boolean.valueOf((String)localHttpSession.getAttribute("week_exceed" + paramInt));
      paramLimit.setData((String)localHttpSession.getAttribute("week_limit" + paramInt));
      paramLimit.setAllowApproval(localBoolean.booleanValue());
    }
    else if (paramLimit.getPeriod() == 4)
    {
      localBoolean = Boolean.valueOf((String)localHttpSession.getAttribute("month_exceed" + paramInt));
      paramLimit.setData((String)localHttpSession.getAttribute("month_limit" + paramInt));
      paramLimit.setAllowApproval(localBoolean.booleanValue());
    }
    String str = paramLimit.getData();
    if ((str == null) || (str.trim().length() == 0)) {
      return null;
    }
    if (!Currency.isValid(paramLimit.getData(), paramLocale)) {
      throw new a();
    }
    if (paramEntitlementGroupMember != null)
    {
      paramLimit.setMemberId(paramEntitlementGroupMember.getId());
      paramLimit.setMemberType(paramEntitlementGroupMember.getMemberType());
      paramLimit.setMemberSubType(paramEntitlementGroupMember.getMemberSubType());
    }
    return paramLimit;
  }
  
  protected int isMoreRestrictiveThanCumulativeLimit(HttpServletRequest paramHttpServletRequest, int paramInt1, Account paramAccount, String paramString, EntitlementGroupMember paramEntitlementGroupMember, int paramInt2)
  {
    Limits localLimits = null;
    String str = null;
    if (paramString.startsWith("ACHCompany")) {
      str = paramString.substring(this.achCompanySize);
    } else {
      str = EntitlementsUtil.getEntitlementObjectId(paramAccount);
    }
    try
    {
      Limit localLimit = new Limit();
      Entitlement localEntitlement = null;
      if (paramString.startsWith("ACHCompany")) {
        localEntitlement = new Entitlement("ACHBatch", "", "");
      } else {
        localEntitlement = new Entitlement(paramString, "", "");
      }
      localLimit.setEntitlement(localEntitlement);
      localLimits = com.ffusion.csil.core.Entitlements.getCumulativeLimits(paramEntitlementGroupMember, localLimit);
      int i = isMoreRestrictive(paramHttpServletRequest, paramInt1, localLimits);
      if (i != 0) {
        return i;
      }
      if ((this._operations != null) && (this._operations.indexOf(paramString) == -1))
      {
        if (paramString.startsWith("ACHCompany")) {
          localEntitlement.setObjectType("ACHCompany");
        } else {
          localEntitlement.setObjectType("Account");
        }
        localEntitlement.setObjectId(str);
        localLimits = com.ffusion.csil.core.Entitlements.getCumulativeLimits(paramEntitlementGroupMember, localLimit);
        i = isMoreRestrictive(paramHttpServletRequest, paramInt1, localLimits);
        if (i != 0) {
          return i;
        }
        localEntitlement.setOperationName("");
        localLimits = com.ffusion.csil.core.Entitlements.getCumulativeLimits(paramEntitlementGroupMember, localLimit);
        i = isMoreRestrictive(paramHttpServletRequest, paramInt1, localLimits);
        if (i != 0) {
          return i;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      return MapError.mapError(localCSILException);
    }
    return 0;
  }
  
  protected int isMoreRestrictive(HttpServletRequest paramHttpServletRequest, int paramInt, Limits paramLimits)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    for (int i = 0; i < paramLimits.size(); i++)
    {
      Limit localLimit = (Limit)paramLimits.get(i);
      String str = null;
      if (localLimit.getPeriod() == 1) {
        str = (String)localHttpSession.getAttribute("transaction_limit" + paramInt);
      } else if (localLimit.getPeriod() == 2) {
        str = (String)localHttpSession.getAttribute("day_limit" + paramInt);
      } else if (localLimit.getPeriod() == 3) {
        str = (String)localHttpSession.getAttribute("week_limit" + paramInt);
      } else if (localLimit.getPeriod() == 4) {
        str = (String)localHttpSession.getAttribute("month_limit" + paramInt);
      }
      try
      {
        if ((str != null) && (!str.equals("")))
        {
          BigDecimal localBigDecimal = new BigDecimal(str);
          if (localBigDecimal.compareTo(localLimit.getAmount()) > 0) {
            return 4517;
          }
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return 4518;
      }
    }
    return 0;
  }
  
  protected int CheckLimitRange(Limit paramLimit1, Limit paramLimit2, Limit paramLimit3, Limit paramLimit4)
  {
    int i = 0;
    Currency localCurrency1 = new Currency();
    Currency localCurrency2 = new Currency();
    Currency localCurrency3 = new Currency();
    Currency localCurrency4 = new Currency();
    if (paramLimit1 != null) {
      localCurrency1.fromString(paramLimit1.getData());
    }
    if (paramLimit2 != null) {
      localCurrency2.fromString(paramLimit2.getData());
    }
    if (paramLimit3 != null) {
      localCurrency3.fromString(paramLimit3.getData());
    }
    if (paramLimit4 != null) {
      localCurrency4.fromString(paramLimit4.getData());
    }
    if (paramLimit1 != null)
    {
      if ((paramLimit2 != null) && (localCurrency2.compareTo(localCurrency1) == -1)) {
        i = 4534;
      }
      if ((paramLimit3 != null) && (localCurrency3.compareTo(localCurrency1) == -1)) {
        i = 4535;
      }
      if ((paramLimit4 != null) && (localCurrency4.compareTo(localCurrency1) == -1)) {
        i = 4537;
      }
    }
    if (paramLimit2 != null)
    {
      if ((paramLimit3 != null) && (localCurrency3.compareTo(localCurrency2) == -1)) {
        i = 4536;
      }
      if ((paramLimit4 != null) && (localCurrency4.compareTo(localCurrency2) == -1)) {
        i = 4538;
      }
    }
    if ((paramLimit3 != null) && (paramLimit4 != null) && (localCurrency4.compareTo(localCurrency3) == -1)) {
      i = 4539;
    }
    return i;
  }
  
  protected void resetSessionNames()
  {
    this._groupName = "EntitlementGroup";
    this._limitsName = "Limits";
    this._entitlementsName = "Entitlements";
  }
  
  public void setGroupAttributeName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this._groupName = paramString;
    }
  }
  
  public void setLimitsAttributeName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this._limitsName = paramString;
    }
  }
  
  public void setEntitlementsAttributeName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this._entitlementsName = paramString;
    }
  }
  
  public String isOnlyConfirm()
  {
    return String.valueOf(this._onlyConfirm);
  }
  
  public void setOnlyConfirm(String paramString)
  {
    if ((paramString != null) && (paramString.equalsIgnoreCase("TRUE"))) {
      this._onlyConfirm = true;
    } else {
      this._onlyConfirm = false;
    }
  }
  
  class a
    extends Exception
  {
    a() {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.AddEditBusinessGroupLimits
 * JD-Core Version:    0.7.0.1
 */