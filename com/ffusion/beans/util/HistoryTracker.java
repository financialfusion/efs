package com.ffusion.beans.util;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyList;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Stringable;
import com.ffusion.util.beans.LocalizableProperty;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class HistoryTracker
{
  private Histories jdField_case;
  private int jdField_char;
  private String jdField_long;
  private String jdField_if;
  private String jdField_do;
  private String jdField_else;
  private int a;
  private Locale jdField_new = byte;
  private ResourceBundle jdField_goto;
  public static final String HISTORY_BUNDLE_NAME = "com.ffusion.beans.history.resources";
  private static final String jdField_try = "COMMENT";
  private static final String jdField_for = "LIMIT";
  private static final String jdField_int = "COLUMNNAME";
  private static final Locale jdField_byte = Locale.US;
  
  public HistoryTracker(SecureUser paramSecureUser, int paramInt, String paramString)
  {
    this(paramSecureUser.getLocale(), paramSecureUser.getUserType() == 1 ? 1 : 0, paramSecureUser.getProfileID(), paramSecureUser.getUserName(), paramInt, paramString, paramString);
  }
  
  public HistoryTracker(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2)
  {
    this(paramSecureUser.getLocale(), paramSecureUser.getUserType() == 1 ? 1 : 0, paramSecureUser.getProfileID(), paramSecureUser.getUserName(), paramInt, paramString1, paramString2);
  }
  
  public HistoryTracker(Locale paramLocale, int paramInt1, int paramInt2, String paramString1, int paramInt3, String paramString2, String paramString3)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.jdField_new = paramLocale;
    this.jdField_case = new Histories(paramLocale);
    this.jdField_char = paramInt1;
    this.jdField_long = Integer.toString(paramInt2);
    this.jdField_if = paramString1;
    this.jdField_do = paramString3;
    this.jdField_else = paramString2;
    this.a = paramInt3;
    this.jdField_goto = ResourceUtil.getBundle("com.ffusion.beans.history.resources", paramLocale);
  }
  
  public void logChange(String paramString, int paramInt1, int paramInt2)
  {
    logChange(paramString, Integer.toString(paramInt1), Integer.toString(paramInt2), null);
  }
  
  public void logChange(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    logChange(paramString, Integer.toString(paramInt1), Integer.toString(paramInt2), lookupComment(paramInt3));
  }
  
  public void logChange(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    logChange(paramString1, Integer.toString(paramInt1), Integer.toString(paramInt2), paramString2);
  }
  
  public void logChange(String paramString1, String paramString2, String paramString3)
  {
    logChange(paramString1, paramString2, paramString3, null);
  }
  
  public void logChange(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    logChange(paramString1, paramString2, paramString3, lookupComment(paramInt));
  }
  
  public void logChange(String paramString1, Object paramObject1, Object paramObject2, String paramString2)
  {
    logChange(paramString1, paramObject1 == null ? null : paramObject1.toString(), paramObject2 == null ? null : paramObject2.toString(), paramString2);
  }
  
  public void logChange(String paramString, float paramFloat1, float paramFloat2)
  {
    logChange(paramString, Float.toString(paramFloat1), Float.toString(paramFloat2), null);
  }
  
  public void logChange(String paramString, float paramFloat1, float paramFloat2, int paramInt)
  {
    logChange(paramString, Float.toString(paramFloat1), Float.toString(paramFloat2), lookupComment(paramInt));
  }
  
  public void logChange(String paramString1, float paramFloat1, float paramFloat2, String paramString2)
  {
    logChange(paramString1, Float.toString(paramFloat1), Float.toString(paramFloat2), paramString2);
  }
  
  public void logChange(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    logChange(paramString, String.valueOf(paramBoolean1), String.valueOf(paramBoolean2), null);
  }
  
  public void logChange(String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    logChange(paramString, String.valueOf(paramBoolean1), String.valueOf(paramBoolean2), lookupComment(paramInt));
  }
  
  public void logChange(String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2)
  {
    logChange(paramString1, String.valueOf(paramBoolean1), String.valueOf(paramBoolean2), paramString2);
  }
  
  public void logChange(String paramString1, Currency paramCurrency1, Currency paramCurrency2, String paramString2)
  {
    logChange(paramString1, paramCurrency1.getCurrencyCode() + " " + paramCurrency1.toString(), paramCurrency2.getCurrencyCode() + " " + paramCurrency2.toString(), paramString2);
  }
  
  public void logChange(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    History localHistory = this.jdField_case.add();
    localHistory.setModifierId(this.jdField_long);
    localHistory.setModifierType(this.jdField_char);
    localHistory.setModifierName(this.jdField_if);
    localHistory.setTrackingID(this.jdField_do);
    localHistory.setId(this.jdField_else);
    localHistory.setIdType(this.a);
    localHistory.setDataChanged(paramString1);
    localHistory.setOldValue(paramString2);
    localHistory.setNewValue(paramString3);
    localHistory.setComment(paramString4);
  }
  
  public boolean detectAndLogChange(String paramString1, ArrayList paramArrayList1, ArrayList paramArrayList2, HistoryItemAnalyzer paramHistoryItemAnalyzer, String paramString2, String paramString3)
  {
    boolean bool = false;
    int i;
    Object localObject;
    History localHistory;
    if (paramArrayList2 != null) {
      for (i = 0; i < paramArrayList2.size(); i++)
      {
        localObject = paramArrayList2.get(i);
        if ((localObject != null) && ((paramArrayList1 == null) || (!a(paramArrayList1, localObject, paramHistoryItemAnalyzer))))
        {
          localHistory = this.jdField_case.add();
          localHistory.setModifierId(this.jdField_long);
          localHistory.setModifierType(this.jdField_char);
          localHistory.setModifierName(this.jdField_if);
          localHistory.setTrackingID(this.jdField_do);
          localHistory.setId(this.jdField_else);
          localHistory.setIdType(this.a);
          localHistory.setDataChanged(paramString1);
          localHistory.setOldValue(null);
          localHistory.setNewValue(paramHistoryItemAnalyzer.getDisplayString(localObject));
          localHistory.setComment(paramString2);
          bool = true;
        }
      }
    }
    if (paramArrayList1 != null) {
      for (i = 0; i < paramArrayList1.size(); i++)
      {
        localObject = paramArrayList1.get(i);
        if ((localObject != null) && ((paramArrayList2 == null) || (!a(paramArrayList2, localObject, paramHistoryItemAnalyzer))))
        {
          localHistory = this.jdField_case.add();
          localHistory.setModifierId(this.jdField_long);
          localHistory.setModifierType(this.jdField_char);
          localHistory.setModifierName(this.jdField_if);
          localHistory.setTrackingID(this.jdField_do);
          localHistory.setId(this.jdField_else);
          localHistory.setIdType(this.a);
          localHistory.setDataChanged(paramString1);
          localHistory.setOldValue(paramHistoryItemAnalyzer.getDisplayString(localObject));
          localHistory.setNewValue(null);
          localHistory.setComment(paramString3);
          bool = true;
        }
      }
    }
    return bool;
  }
  
  private static boolean a(ArrayList paramArrayList, Object paramObject, HistoryItemAnalyzer paramHistoryItemAnalyzer)
  {
    Object localObject = null;
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      localObject = paramArrayList.get(i);
      if ((localObject != null) && (paramObject != null) && (paramHistoryItemAnalyzer.equal(paramObject, localObject))) {
        return true;
      }
    }
    return false;
  }
  
  public void logCreate(String paramString1, String paramString2, Object paramObject, String paramString3)
  {
    if (paramObject != null)
    {
      String str = paramObject.toString();
      if (str.length() != 0) {
        logChange(lookupField(paramString1, paramString2), null, str, paramString3);
      }
    }
  }
  
  public void logCreate(String paramString1, String paramString2, Stringable paramStringable, String paramString3)
  {
    if (paramStringable != null)
    {
      String str = paramStringable.toString();
      if (str.length() != 0) {
        logChange(lookupField(paramString1, paramString2), null, str, paramString3);
      }
    }
  }
  
  public void logCreate(String paramString1, String paramString2, ExtendABean paramExtendABean, String paramString3)
  {
    if (paramExtendABean != null)
    {
      String str = paramExtendABean.toString();
      if (str.length() != 0) {
        logChange(lookupField(paramString1, paramString2), null, str, paramString3);
      }
    }
  }
  
  public void logCreate(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    logChange(lookupField(paramString1, paramString2), null, Integer.toString(paramInt), paramString3);
  }
  
  public void logCreate(String paramString1, String paramString2, float paramFloat, String paramString3)
  {
    logChange(lookupField(paramString1, paramString2), null, Float.toString(paramFloat), paramString3);
  }
  
  public void logCreate(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((paramString3 != null) && (paramString3.length() != 0)) {
      logChange(lookupField(paramString1, paramString2), null, paramString3, paramString4);
    }
  }
  
  public void logCreate(ILocalizable paramILocalizable)
  {
    a((ILocalizable)null, (ILocalizable)null, (ILocalizable)null, paramILocalizable);
  }
  
  public void logDelete(String paramString1, String paramString2, Object paramObject, String paramString3)
  {
    if (paramObject != null)
    {
      String str = paramObject.toString();
      if (str.length() != 0) {
        logChange(lookupField(paramString1, paramString2), str, null, paramString3);
      }
    }
  }
  
  public void logDelete(String paramString1, String paramString2, Stringable paramStringable, String paramString3)
  {
    if (paramStringable != null)
    {
      String str = paramStringable.toString();
      if (str.length() != 0) {
        logChange(lookupField(paramString1, paramString2), str, null, paramString3);
      }
    }
  }
  
  public void logDelete(String paramString1, String paramString2, ExtendABean paramExtendABean, String paramString3)
  {
    if (paramExtendABean != null)
    {
      String str = paramExtendABean.toString();
      if (str.length() != 0) {
        logChange(lookupField(paramString1, paramString2), str, null, paramString3);
      }
    }
  }
  
  public void logDelete(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    logChange(lookupField(paramString1, paramString2), Integer.toString(paramInt), null, paramString3);
  }
  
  public void logDelete(String paramString1, String paramString2, float paramFloat, String paramString3)
  {
    logChange(lookupField(paramString1, paramString2), Float.toString(paramFloat), null, paramString3);
  }
  
  public void logDelete(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((paramString3 != null) && (paramString3.length() != 0)) {
      logChange(lookupField(paramString1, paramString2), paramString3, null, paramString4);
    }
  }
  
  public Histories getHistories()
  {
    return this.jdField_case;
  }
  
  public void detectChange(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    if (paramInt1 != paramInt2) {
      logChange(paramString1, paramInt1, paramInt2, paramString2);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3)
  {
    if (paramInt1 != paramInt2) {
      logChange(lookupField(paramString1, paramString2), paramInt1, paramInt2, paramString3);
    }
  }
  
  public void detectChange(String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2)
  {
    if (paramBoolean1 != paramBoolean2) {
      logChange(paramString1, paramBoolean1, paramBoolean2, paramString2);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, String paramString3)
  {
    if (paramBoolean1 != paramBoolean2) {
      logChange(lookupField(paramString1, paramString2), paramBoolean1, paramBoolean2, paramString3);
    }
  }
  
  public void detectChange(String paramString1, Currency paramCurrency1, Currency paramCurrency2, String paramString2)
  {
    if ((paramCurrency1 != null) && (paramCurrency2 != null) && ((!paramCurrency1.getAmountValue().equals(paramCurrency2.getAmountValue())) || (!paramCurrency1.getCurrencyCode().equalsIgnoreCase(paramCurrency2.getCurrencyCode())))) {
      logChange(paramString1, paramCurrency1, paramCurrency2, paramString2);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, Currency paramCurrency1, Currency paramCurrency2, String paramString3)
  {
    if ((paramCurrency1 != null) && (paramCurrency2 != null) && ((!paramCurrency1.getAmountValue().equals(paramCurrency2.getAmountValue())) || (!paramCurrency1.getCurrencyCode().equalsIgnoreCase(paramCurrency2.getCurrencyCode())))) {
      logChange(lookupField(paramString1, paramString2), paramCurrency1, paramCurrency2, paramString3);
    }
  }
  
  public void detectChange(String paramString1, Stringable paramStringable1, Stringable paramStringable2, String paramString2)
  {
    detectChangeObject(paramString1, paramStringable1 == null ? null : paramStringable1.toString(), paramStringable2 == null ? null : paramStringable2.toString(), paramString2);
  }
  
  public void detectChange(String paramString1, String paramString2, Stringable paramStringable1, Stringable paramStringable2, String paramString3)
  {
    detectChangeObject(paramString1, paramString2, paramStringable1 == null ? null : paramStringable1.toString(), paramStringable2 == null ? null : paramStringable2.toString(), paramString3);
  }
  
  public void detectChange(String paramString1, Object paramObject1, Object paramObject2, String paramString2)
  {
    detectChangeObject(paramString1, paramObject1, paramObject2, paramString2);
  }
  
  public void detectChange(String paramString1, String paramString2, Object paramObject1, Object paramObject2, String paramString3)
  {
    detectChangeObject(paramString1, paramString2, paramObject1, paramObject2, paramString3);
  }
  
  public void detectChange(String paramString1, float paramFloat1, float paramFloat2, String paramString2)
  {
    if (paramFloat1 != paramFloat2) {
      logChange(paramString1, paramFloat1, paramFloat2, paramString2);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, float paramFloat1, float paramFloat2, String paramString3)
  {
    if (paramFloat1 != paramFloat2) {
      logChange(lookupField(paramString1, paramString2), paramFloat1, paramFloat2, paramString3);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((paramString2 == null) && ((paramString3 == null) || (paramString3.length() == 0))) {
      return;
    }
    detectChangeObject(paramString1, paramString2, paramString3, paramString4);
  }
  
  public void detectChange(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((paramString3 == null) && ((paramString4 == null) || (paramString4.length() == 0))) {
      return;
    }
    detectChangeObject(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public void detectChangeObject(String paramString1, Object paramObject1, Object paramObject2, String paramString2)
  {
    if (paramObject1 != paramObject2) {
      if (paramObject1 == null) {
        logChange(paramString1, null, paramObject2.toString(), paramString2);
      } else if (!paramObject1.equals(paramObject2)) {
        logChange(paramString1, paramObject1.toString(), paramObject2 == null ? null : paramObject2.toString(), paramString2);
      }
    }
  }
  
  public void detectChangeObject(String paramString1, String paramString2, Object paramObject1, Object paramObject2, String paramString3)
  {
    if (paramObject1 != paramObject2) {
      if (paramObject1 == null) {
        logChange(lookupField(paramString1, paramString2), null, paramObject2.toString(), paramString3);
      } else if (!paramObject1.equals(paramObject2)) {
        logChange(lookupField(paramString1, paramString2), paramObject1.toString(), paramObject2 == null ? null : paramObject2.toString(), paramString3);
      }
    }
  }
  
  public String lookupField(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return paramString2;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",", false);
    while (localStringTokenizer.countTokens() > 0)
    {
      String str = localStringTokenizer.nextToken();
      StringBuffer localStringBuffer = new StringBuffer(str.length() + paramString2.length() + 1);
      localStringBuffer.append(str).append('.').append(paramString2);
      try
      {
        if (this.jdField_goto != null) {
          return this.jdField_goto.getString(localStringBuffer.toString());
        }
      }
      catch (MissingResourceException localMissingResourceException) {}
    }
    return paramString2;
  }
  
  public String lookupComment(int paramInt)
  {
    String str = null;
    if (paramInt != 0)
    {
      StringBuffer localStringBuffer = new StringBuffer("COMMENT").append(paramInt);
      try
      {
        if (this.jdField_goto != null) {
          str = this.jdField_goto.getString(localStringBuffer.toString());
        }
      }
      catch (MissingResourceException localMissingResourceException) {}
    }
    return str;
  }
  
  private void a(Limit paramLimit, ArrayList paramArrayList, String paramString1, String paramString2)
  {
    if (paramLimit == null) {
      return;
    }
    String str1 = paramLimit.getLimitName();
    String str2 = paramLimit.getObjectType();
    String str3 = paramLimit.getObjectId();
    HashMap localHashMap1 = null;
    if (str2 != null) {
      try
      {
        localHashMap1 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties(str2).getPropertiesMap();
      }
      catch (CSILException localCSILException1) {}
    }
    HashMap localHashMap2 = null;
    if (str1 != null) {
      try
      {
        localHashMap2 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(str1).getPropertiesMap();
      }
      catch (CSILException localCSILException2) {}
    }
    ArrayList localArrayList = new ArrayList();
    String str4 = "COLUMNNAME4";
    if ((str2 != null) && (localHashMap1 != null) && (localHashMap1.size() > 0))
    {
      localObject = new LocalizableProperty("display name", localHashMap1, str2);
      localArrayList.add(localObject);
      str4 = "COLUMNNAME5";
      if (str3 != null)
      {
        localArrayList.add(str3);
        str4 = "COLUMNNAME6";
      }
    }
    Object localObject = new LocalizableString("com.ffusion.beans.history.resources", str4, localArrayList.toArray());
    String str5 = null;
    LocalizableString localLocalizableString = null;
    localArrayList.clear();
    switch (paramLimit.getPeriod())
    {
    case 1: 
      if (paramLimit.isAllowedApproval()) {
        str5 = "LIMIT" + 13;
      } else {
        str5 = "LIMIT" + 14;
      }
      break;
    case 2: 
      if (paramLimit.isAllowedApproval()) {
        str5 = "LIMIT" + 15;
      } else {
        str5 = "LIMIT" + 16;
      }
      break;
    case 3: 
      if (paramLimit.isAllowedApproval()) {
        str5 = "LIMIT" + 15;
      } else {
        str5 = "LIMIT" + 16;
      }
      break;
    case 4: 
      if (paramLimit.isAllowedApproval()) {
        str5 = "LIMIT" + 15;
      } else {
        str5 = "LIMIT" + 16;
      }
      break;
    }
    LocalizableProperty localLocalizableProperty = new LocalizableProperty("display name", localHashMap2, str1);
    localArrayList.add(localLocalizableProperty);
    localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", str5, localArrayList.toArray());
    a((ILocalizable)localObject, paramString1, paramString2, localLocalizableString);
  }
  
  public void logLimitAdd(Limit paramLimit, ArrayList paramArrayList)
  {
    if (paramLimit == null) {
      return;
    }
    a(paramLimit, paramArrayList, null, paramLimit.getData());
  }
  
  public void logLimitChange(Limit paramLimit, ArrayList paramArrayList, String paramString)
  {
    if (paramLimit == null) {
      return;
    }
    a(paramLimit, paramArrayList, paramString, paramLimit.getData());
  }
  
  public void logLimitDelete(Limit paramLimit, ArrayList paramArrayList)
  {
    if (paramLimit == null) {
      return;
    }
    a(paramLimit, paramArrayList, paramLimit.getData(), null);
  }
  
  private void a(Entitlement paramEntitlement, ArrayList paramArrayList, boolean paramBoolean)
  {
    if (paramEntitlement == null) {
      return;
    }
    String str1 = paramEntitlement.getOperationName();
    String str2 = paramEntitlement.getObjectId();
    String str3 = paramEntitlement.getObjectType();
    HashMap localHashMap1 = null;
    if (str3 != null) {
      try
      {
        localHashMap1 = com.ffusion.csil.core.Entitlements.getObjectTypeWithProperties(str3).getPropertiesMap();
      }
      catch (CSILException localCSILException1) {}
    }
    HashMap localHashMap2 = null;
    if (str1 != null) {
      try
      {
        localHashMap2 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str1).getPropertiesMap();
      }
      catch (CSILException localCSILException2) {}
    }
    ArrayList localArrayList = new ArrayList();
    String str4 = "COLUMNNAME1";
    if ((str3 != null) && (localHashMap1 != null) && (localHashMap1.size() > 0))
    {
      localObject = new LocalizableProperty("display name", localHashMap1, str3);
      localArrayList.add(localObject);
      str4 = "COLUMNNAME2";
      if (str2 != null)
      {
        localArrayList.add(str2);
        str4 = "COLUMNNAME3";
      }
    }
    Object localObject = new LocalizableString("com.ffusion.beans.history.resources", str4, localArrayList.toArray());
    String str5 = null;
    LocalizableString localLocalizableString = null;
    if ((str1 == null) || (localHashMap2 == null) || (localHashMap2.size() == 0))
    {
      if (paramBoolean) {
        str5 = "LIMIT" + 4;
      } else {
        str5 = "LIMIT" + 3;
      }
      localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", str5, null);
    }
    else
    {
      localArrayList.clear();
      if (paramBoolean) {
        str5 = "LIMIT" + 12;
      } else {
        str5 = "LIMIT" + 11;
      }
      LocalizableProperty localLocalizableProperty = new LocalizableProperty("display name", localHashMap2, str1);
      localArrayList.add(localLocalizableProperty);
      localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", str5, localArrayList.toArray());
    }
    a((ILocalizable)localObject, (ILocalizable)null, (ILocalizable)null, localLocalizableString);
  }
  
  public void logEntitlementAdd(Entitlement paramEntitlement, ArrayList paramArrayList)
  {
    a(paramEntitlement, paramArrayList, true);
  }
  
  public void logEntitlementDelete(Entitlement paramEntitlement, ArrayList paramArrayList)
  {
    a(paramEntitlement, paramArrayList, false);
  }
  
  public void logEntitlementAdd(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    if (paramEntitlementTypePropertyLists == null) {
      try
      {
        paramEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      }
      catch (CSILException localCSILException) {}
    }
    for (int i = 0; i < paramEntitlements.size(); i++)
    {
      Entitlement localEntitlement = paramEntitlements.getEntitlement(i);
      logEntitlementAdd(localEntitlement, paramEntitlementTypePropertyLists);
    }
  }
  
  public Locale getLocale()
  {
    return this.jdField_new == null ? LocaleUtil.getDefaultLocale() : this.jdField_new;
  }
  
  private LocalizableString a(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",", false);
      while (localStringTokenizer.countTokens() > 0)
      {
        String str1 = localStringTokenizer.nextToken();
        StringBuffer localStringBuffer = new StringBuffer(str1.length() + paramString2.length() + 1);
        localStringBuffer.append(str1).append('.').append(paramString2);
        try
        {
          if (this.jdField_goto != null)
          {
            String str2 = this.jdField_goto.getString(localStringBuffer.toString());
            return new LocalizableString("com.ffusion.beans.history.resources", localStringBuffer.toString(), null);
          }
        }
        catch (MissingResourceException localMissingResourceException) {}
      }
      return null;
    }
    if (paramString2 != null) {
      return new LocalizableString("com.ffusion.beans.history.resources", paramString2, null);
    }
    return null;
  }
  
  public LocalizableString buildLocalizableComment(int paramInt)
  {
    LocalizableString localLocalizableString = null;
    if (paramInt != 0)
    {
      StringBuffer localStringBuffer = new StringBuffer("COMMENT").append(paramInt);
      try
      {
        if (this.jdField_goto != null) {
          localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", localStringBuffer.toString(), null);
        }
      }
      catch (MissingResourceException localMissingResourceException) {}
    }
    return localLocalizableString;
  }
  
  private void a(String paramString1, String paramString2, String paramString3, String paramString4, ILocalizable paramILocalizable)
  {
    LocalizableString localLocalizableString = a(paramString1, paramString2);
    a(localLocalizableString, paramString3, paramString4, paramILocalizable);
  }
  
  private void a(String paramString1, String paramString2, ILocalizable paramILocalizable1, ILocalizable paramILocalizable2, ILocalizable paramILocalizable3)
  {
    LocalizableString localLocalizableString = a(paramString1, paramString2);
    a(localLocalizableString, paramILocalizable1, paramILocalizable2, paramILocalizable3);
  }
  
  public void logChange(String paramString1, String paramString2, int paramInt1, int paramInt2, ILocalizable paramILocalizable)
  {
    a(paramString1, paramString2, Integer.toString(paramInt1), Integer.toString(paramInt2), paramILocalizable);
  }
  
  public void logChange(String paramString1, String paramString2, float paramFloat1, float paramFloat2, ILocalizable paramILocalizable)
  {
    a(paramString1, paramString2, Float.toString(paramFloat1), Float.toString(paramFloat2), paramILocalizable);
  }
  
  public void logChange(String paramString1, String paramString2, Currency paramCurrency1, Currency paramCurrency2, ILocalizable paramILocalizable)
  {
    a(paramString1, paramString2, paramCurrency1, paramCurrency2, paramILocalizable);
  }
  
  public void logChange(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, ILocalizable paramILocalizable)
  {
    a(paramString1, paramString2, new LocalizableString("com.ffusion.beans.history.resources", String.valueOf(paramBoolean1), null), new LocalizableString("com.ffusion.beans.history.resources", String.valueOf(paramBoolean2), null), paramILocalizable);
  }
  
  public void logChange(String paramString1, String paramString2, String paramString3, String paramString4, ILocalizable paramILocalizable)
  {
    a(paramString1, paramString2, paramString3, paramString4, paramILocalizable);
  }
  
  public void logChange(String paramString1, String paramString2, ILocalizable paramILocalizable1, ILocalizable paramILocalizable2, ILocalizable paramILocalizable3)
  {
    a(paramString1, paramString2, paramILocalizable1, paramILocalizable2, paramILocalizable3);
  }
  
  private void a(ILocalizable paramILocalizable1, ILocalizable paramILocalizable2, ILocalizable paramILocalizable3, ILocalizable paramILocalizable4)
  {
    History localHistory = this.jdField_case.add();
    localHistory.setModifierId(this.jdField_long);
    localHistory.setModifierType(this.jdField_char);
    localHistory.setModifierName(this.jdField_if);
    localHistory.setTrackingID(this.jdField_do);
    localHistory.setId(this.jdField_else);
    localHistory.setIdType(this.a);
    localHistory.setLocalizableDataChanged(paramILocalizable1);
    localHistory.setLocalizableOldValue(paramILocalizable2);
    localHistory.setLocalizableNewValue(paramILocalizable3);
    localHistory.setLocalizableComment(paramILocalizable4);
  }
  
  private void a(ILocalizable paramILocalizable1, String paramString1, String paramString2, ILocalizable paramILocalizable2)
  {
    History localHistory = this.jdField_case.add();
    localHistory.setModifierId(this.jdField_long);
    localHistory.setModifierType(this.jdField_char);
    localHistory.setModifierName(this.jdField_if);
    localHistory.setTrackingID(this.jdField_do);
    localHistory.setId(this.jdField_else);
    localHistory.setIdType(this.a);
    localHistory.setLocalizableDataChanged(paramILocalizable1);
    localHistory.setOldValue(paramString1);
    localHistory.setNewValue(paramString2);
    localHistory.setLocalizableComment(paramILocalizable2);
  }
  
  public void logCreate(String paramString1, String paramString2, ILocalizable paramILocalizable1, ILocalizable paramILocalizable2)
  {
    if (paramILocalizable1 != null) {
      logChange(paramString1, paramString2, null, paramILocalizable1, paramILocalizable2);
    }
  }
  
  public void logCreate(String paramString1, String paramString2, ExtendABean paramExtendABean, ILocalizable paramILocalizable)
  {
    if (paramExtendABean != null)
    {
      String str = paramExtendABean.toString();
      if (str.length() != 0) {
        logChange(paramString1, paramString2, null, str, paramILocalizable);
      }
    }
  }
  
  public void logCreate(String paramString1, String paramString2, int paramInt, ILocalizable paramILocalizable)
  {
    logChange(paramString1, paramString2, null, Integer.toString(paramInt), paramILocalizable);
  }
  
  public void logCreate(String paramString1, String paramString2, float paramFloat, ILocalizable paramILocalizable)
  {
    logChange(paramString1, paramString2, null, Float.toString(paramFloat), paramILocalizable);
  }
  
  public void logCreate(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable)
  {
    if ((paramString3 != null) && (paramString3.length() != 0)) {
      logChange(paramString1, paramString2, null, paramString3, paramILocalizable);
    }
  }
  
  public void logDelete(String paramString1, String paramString2, ILocalizable paramILocalizable1, ILocalizable paramILocalizable2)
  {
    if (paramILocalizable1 != null) {
      logChange(paramString1, paramString2, paramILocalizable1, null, paramILocalizable2);
    }
  }
  
  public void logDelete(String paramString1, String paramString2, ExtendABean paramExtendABean, ILocalizable paramILocalizable)
  {
    if (paramExtendABean != null)
    {
      String str = paramExtendABean.toString();
      if (str.length() != 0) {
        logChange(paramString1, paramString2, str, null, paramILocalizable);
      }
    }
  }
  
  public void logDelete(String paramString1, String paramString2, int paramInt, ILocalizable paramILocalizable)
  {
    logChange(paramString1, paramString2, Integer.toString(paramInt), null, paramILocalizable);
  }
  
  public void logDelete(String paramString1, String paramString2, float paramFloat, ILocalizable paramILocalizable)
  {
    logChange(paramString1, paramString2, Float.toString(paramFloat), null, paramILocalizable);
  }
  
  public void logDelete(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable)
  {
    if ((paramString3 != null) && (paramString3.length() != 0)) {
      logChange(paramString1, paramString2, paramString3, null, paramILocalizable);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, int paramInt1, int paramInt2, ILocalizable paramILocalizable)
  {
    if (paramInt1 != paramInt2) {
      logChange(paramString1, paramString2, paramInt1, paramInt2, paramILocalizable);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, float paramFloat1, float paramFloat2, ILocalizable paramILocalizable)
  {
    if (paramFloat1 != paramFloat2) {
      logChange(paramString1, paramString2, paramFloat1, paramFloat2, paramILocalizable);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, ILocalizable paramILocalizable)
  {
    if (paramBoolean1 != paramBoolean2) {
      logChange(paramString1, paramString2, paramBoolean1, paramBoolean2, paramILocalizable);
    }
  }
  
  public void detectChange(String paramString1, String paramString2, Currency paramCurrency1, Currency paramCurrency2, ILocalizable paramILocalizable)
  {
    if ((paramCurrency1 != null) && (paramCurrency2 != null)) {
      if (!paramCurrency1.getAmountValue().equals(paramCurrency2.getAmountValue())) {
        logChange(paramString1, paramString2, paramCurrency1, paramCurrency2, paramILocalizable);
      } else {
        detectChange(paramString1, paramString2, paramCurrency1.getCurrencyCode(), paramCurrency2.getCurrencyCode(), paramILocalizable);
      }
    }
  }
  
  public void detectChange(String paramString1, String paramString2, String paramString3, String paramString4, ILocalizable paramILocalizable)
  {
    if ((paramString3 == null) && ((paramString4 == null) || (paramString4.length() == 0))) {
      return;
    }
    if (paramString3 != paramString4) {
      if (paramString3 == null) {
        logChange(paramString1, paramString2, null, paramString4.toString(), paramILocalizable);
      } else if (!paramString3.equals(paramString4)) {
        logChange(paramString1, paramString2, paramString3, paramString4, paramILocalizable);
      }
    }
  }
  
  public void detectChange(String paramString1, String paramString2, ILocalizable paramILocalizable1, ILocalizable paramILocalizable2, ILocalizable paramILocalizable3)
  {
    if ((paramILocalizable1 != null) && (paramILocalizable2 != null) && (!paramILocalizable1.equals(paramILocalizable2))) {
      logChange(paramString1, paramString2, paramILocalizable1, paramILocalizable2, paramILocalizable3);
    }
  }
  
  public boolean detectAndLogChange(String paramString1, String paramString2, ArrayList paramArrayList1, ArrayList paramArrayList2, HistoryItemAnalyzer paramHistoryItemAnalyzer, ILocalizable paramILocalizable1, ILocalizable paramILocalizable2)
  {
    boolean bool = false;
    int i;
    Object localObject;
    if (paramArrayList2 != null) {
      for (i = 0; i < paramArrayList2.size(); i++)
      {
        localObject = paramArrayList2.get(i);
        if ((localObject != null) && ((paramArrayList1 == null) || (!a(paramArrayList1, localObject, paramHistoryItemAnalyzer))))
        {
          a(paramString1, paramString2, null, paramHistoryItemAnalyzer.buildLocalizableDisplayString(localObject), paramILocalizable1);
          bool = true;
        }
      }
    }
    if (paramArrayList1 != null) {
      for (i = 0; i < paramArrayList1.size(); i++)
      {
        localObject = paramArrayList1.get(i);
        if ((localObject != null) && ((paramArrayList2 == null) || (!a(paramArrayList2, localObject, paramHistoryItemAnalyzer))))
        {
          a(paramString1, paramString2, paramHistoryItemAnalyzer.buildLocalizableDisplayString(localObject), null, paramILocalizable2);
          bool = true;
        }
      }
    }
    return bool;
  }
  
  public void detectChangeObject(String paramString1, String paramString2, ILocalizable paramILocalizable1, ILocalizable paramILocalizable2, ILocalizable paramILocalizable3)
  {
    if (paramILocalizable1 != paramILocalizable2) {
      if (paramILocalizable1 == null) {
        logChange(paramString1, paramString2, null, paramILocalizable2, paramILocalizable3);
      } else if (!paramILocalizable1.equals(paramILocalizable2)) {
        logChange(paramString1, paramString2, paramILocalizable1, paramILocalizable2 == null ? null : paramILocalizable2, paramILocalizable3);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.HistoryTracker
 * JD-Core Version:    0.7.0.1
 */