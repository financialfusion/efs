package com.ffusion.util.entitlements;

import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.util.accountgroups.AccountGroupsUtil;
import com.ffusion.util.beans.accountgroups.BusinessAccountGroupException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class EntitlementsUtil
{
  private static final String a = "_ENT_BYPASS";
  
  public static MultiEntitlement appendAccountGroups(Connection paramConnection, MultiEntitlement paramMultiEntitlement, int paramInt)
    throws BusinessAccountGroupException
  {
    String str = "EntitlementsUtil.appendAccountGroups";
    String[] arrayOfString1 = paramMultiEntitlement.getObjectTypes();
    String[] arrayOfString2 = paramMultiEntitlement.getObjectIds();
    ArrayList localArrayList1 = new ArrayList();
    if ((arrayOfString1 == null) || (arrayOfString2 == null)) {
      return paramMultiEntitlement;
    }
    if (arrayOfString1.length != arrayOfString2.length) {
      throw new BusinessAccountGroupException(str, 9, "objectType and objectId arrays are not of equal size.");
    }
    for (int i = 0; i < arrayOfString1.length; i++) {
      if ((arrayOfString1[i] != null) && (arrayOfString1[i].equals("Account"))) {
        localArrayList1.add(arrayOfString2[i]);
      }
    }
    ArrayList localArrayList2 = AccountGroupsUtil.getAccountGroupsIds(paramConnection, paramInt, localArrayList1);
    int j = arrayOfString1.length + localArrayList2.size();
    String[] arrayOfString3 = new String[j];
    String[] arrayOfString4 = new String[j];
    for (int k = 0; k < j; k++) {
      if (k < arrayOfString1.length)
      {
        arrayOfString3[k] = arrayOfString1[k];
        arrayOfString4[k] = arrayOfString2[k];
      }
      else
      {
        arrayOfString3[k] = "AccountGroup";
        arrayOfString4[k] = ((String)localArrayList2.get(k - arrayOfString1.length));
      }
    }
    paramMultiEntitlement.setObjects(arrayOfString3, arrayOfString4);
    String[] arrayOfString5 = paramMultiEntitlement.getOperations();
    String[] arrayOfString6 = new String[arrayOfString5 == null ? 1 : arrayOfString5.length + 1];
    if (arrayOfString5 != null) {
      System.arraycopy(arrayOfString5, 0, arrayOfString6, 0, arrayOfString5.length);
    }
    arrayOfString6[(arrayOfString6.length - 1)] = "Access";
    paramMultiEntitlement.setOperations(arrayOfString6);
    return paramMultiEntitlement;
  }
  
  public static Entitlement checkAccountEntitlement(Connection paramConnection, EntitlementCachedAdapter paramEntitlementCachedAdapter, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, int paramInt)
    throws EntitlementException
  {
    return a(paramConnection, paramEntitlementCachedAdapter, paramEntitlementGroupMember, -1, true, paramMultiEntitlement, paramInt);
  }
  
  public static Entitlement checkAccountEntitlement(Connection paramConnection, EntitlementCachedAdapter paramEntitlementCachedAdapter, int paramInt1, MultiEntitlement paramMultiEntitlement, int paramInt2)
    throws EntitlementException
  {
    return a(paramConnection, paramEntitlementCachedAdapter, null, paramInt1, false, paramMultiEntitlement, paramInt2);
  }
  
  private static Entitlement a(Connection paramConnection, EntitlementCachedAdapter paramEntitlementCachedAdapter, EntitlementGroupMember paramEntitlementGroupMember, int paramInt1, boolean paramBoolean, MultiEntitlement paramMultiEntitlement, int paramInt2)
    throws EntitlementException
  {
    String str = "EntitlementsUtil.checkAccountEntitlement";
    String[] arrayOfString1 = paramMultiEntitlement.getObjectTypes();
    String[] arrayOfString2 = paramMultiEntitlement.getObjectIds();
    String[] arrayOfString3 = paramMultiEntitlement.getOperations();
    if ((arrayOfString1 == null) && (arrayOfString2 == null)) {
      return null;
    }
    if ((arrayOfString1 == null) || (arrayOfString2 == null) || (arrayOfString1.length != arrayOfString2.length))
    {
      EntitlementException localEntitlementException1 = new EntitlementException(str, 29);
      DebugLog.throwing(str, localEntitlementException1);
      throw localEntitlementException1;
    }
    int i = -1;
    String[] arrayOfString4 = new String[arrayOfString1.length];
    String[] arrayOfString5 = new String[arrayOfString2.length];
    String[] arrayOfString6 = arrayOfString3;
    Object localObject;
    for (int j = 0; j < arrayOfString1.length; j++)
    {
      arrayOfString4[j] = arrayOfString1[j];
      arrayOfString5[j] = arrayOfString2[j];
      if (arrayOfString1[j] != null) {
        if (arrayOfString1[j].equals("Account"))
        {
          if (i != -1)
          {
            localObject = new EntitlementException(30);
            DebugLog.throwing(str, (Throwable)localObject);
            throw ((Throwable)localObject);
          }
          i = j;
          arrayOfString6 = new String[arrayOfString3 == null ? 1 : arrayOfString3.length + 1];
          if (arrayOfString3 != null) {
            System.arraycopy(arrayOfString3, 0, arrayOfString6, 0, arrayOfString3.length);
          }
          arrayOfString6[(arrayOfString6.length - 1)] = "Access";
        }
        else if (arrayOfString1[j].equals("AccountGroup"))
        {
          localObject = new EntitlementException(31);
          DebugLog.throwing(str, (Throwable)localObject);
          throw ((Throwable)localObject);
        }
      }
    }
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setObjects(arrayOfString4, arrayOfString5);
    localMultiEntitlement.setOperations(arrayOfString6);
    if (paramBoolean) {
      localObject = paramEntitlementCachedAdapter.checkEntitlement(paramEntitlementGroupMember, localMultiEntitlement);
    } else {
      localObject = paramEntitlementCachedAdapter.checkEntitlement(paramInt1, localMultiEntitlement);
    }
    if (localObject == null) {
      return null;
    }
    if (!((Entitlement)localObject).getObjectType().equals("Account")) {
      return localObject;
    }
    if (i == -1) {
      return localObject;
    }
    if (arrayOfString2[i] == null) {
      return localObject;
    }
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(arrayOfString2[i]);
    ArrayList localArrayList2 = null;
    try
    {
      localArrayList2 = AccountGroupsUtil.getAccountGroupsIds(paramConnection, paramInt2, localArrayList1);
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      EntitlementException localEntitlementException2 = new EntitlementException(localBusinessAccountGroupException, str, 32, localBusinessAccountGroupException.getMessage());
      DebugLog.throwing(str, localEntitlementException2);
      throw localEntitlementException2;
    }
    if (localArrayList2 == null) {
      return localObject;
    }
    String[] arrayOfString7 = new String[1];
    for (int k = 0; k < arrayOfString6.length; k++)
    {
      arrayOfString7[0] = arrayOfString6[k];
      localMultiEntitlement.setOperations(arrayOfString7);
      arrayOfString4[i] = "Account";
      arrayOfString5[i] = arrayOfString2[i];
      localMultiEntitlement.setObjects(arrayOfString4, arrayOfString5);
      Entitlement localEntitlement;
      if (paramBoolean) {
        localEntitlement = paramEntitlementCachedAdapter.checkEntitlement(paramEntitlementGroupMember, localMultiEntitlement);
      } else {
        localEntitlement = paramEntitlementCachedAdapter.checkEntitlement(paramInt1, localMultiEntitlement);
      }
      if (localEntitlement != null)
      {
        arrayOfString4[i] = "AccountGroup";
        Iterator localIterator = localArrayList2.iterator();
        while (localIterator.hasNext())
        {
          arrayOfString5[i] = ((String)localIterator.next());
          localMultiEntitlement.setObjects(arrayOfString4, arrayOfString5);
          if (paramBoolean) {
            localEntitlement = paramEntitlementCachedAdapter.checkEntitlement(paramEntitlementGroupMember, localMultiEntitlement);
          } else {
            localEntitlement = paramEntitlementCachedAdapter.checkEntitlement(paramInt1, localMultiEntitlement);
          }
          if (localEntitlement == null) {
            break;
          }
        }
        if (localEntitlement != null) {
          return localObject;
        }
      }
    }
    return null;
  }
  
  public static String getACHEntitlementName(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    localStringBuffer.append("-");
    localStringBuffer.append(paramString2);
    return localStringBuffer.toString();
  }
  
  public static String getACHLimitName(String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    localStringBuffer.append("-");
    localStringBuffer.append(paramString2);
    if (paramBoolean) {
      localStringBuffer.append(" (Debit)");
    } else {
      localStringBuffer.append(" (Credit)");
    }
    return localStringBuffer.toString();
  }
  
  public static String[] getACHSECEntitlementWithAddenda(String paramString)
  {
    String[] arrayOfString = new String[2];
    if ((paramString == null) || ((paramString != null) && (paramString.length() == 0))) {
      return arrayOfString;
    }
    if (paramString.equalsIgnoreCase("ARC"))
    {
      arrayOfString[0] = "ARC";
      arrayOfString[1] = "ARC";
    }
    else if (paramString.equalsIgnoreCase("CCD"))
    {
      arrayOfString[0] = "CCD";
      arrayOfString[1] = "CCD + Addenda";
    }
    else if (paramString.equalsIgnoreCase("CIE"))
    {
      arrayOfString[0] = "CIE";
      arrayOfString[1] = "CIE + Addenda";
    }
    else if (paramString.equalsIgnoreCase("CTX"))
    {
      arrayOfString[0] = "CTX + Addenda";
      arrayOfString[1] = "CTX + Addenda";
    }
    else if (paramString.equalsIgnoreCase("POP"))
    {
      arrayOfString[0] = "POP";
      arrayOfString[1] = "POP";
    }
    else if (paramString.equalsIgnoreCase("PPD"))
    {
      arrayOfString[0] = "PPD";
      arrayOfString[1] = "PPD + Addenda";
    }
    else if (paramString.equalsIgnoreCase("RCK"))
    {
      arrayOfString[0] = "RCK";
      arrayOfString[1] = "RCK";
    }
    else if (paramString.equalsIgnoreCase("TEL"))
    {
      arrayOfString[0] = "TEL";
      arrayOfString[1] = "TEL";
    }
    else if (paramString.equalsIgnoreCase("WEB"))
    {
      arrayOfString[0] = "WEB";
      arrayOfString[1] = "WEB + Addenda";
    }
    else if (paramString.equalsIgnoreCase("XCK"))
    {
      arrayOfString[0] = "XCK";
      arrayOfString[1] = "XCK";
    }
    else if (paramString.equalsIgnoreCase("CCD + TXP"))
    {
      arrayOfString[0] = "CCD + TXP";
      arrayOfString[1] = "CCD + TXP";
    }
    else if (paramString.equalsIgnoreCase("CCD + DED"))
    {
      arrayOfString[0] = "CCD + DED";
      arrayOfString[1] = "CCD + DED";
    }
    return arrayOfString;
  }
  
  public static boolean isEntitlementBypassAllowed(HashMap paramHashMap)
  {
    return (paramHashMap != null) && (paramHashMap.containsKey("_ENT_BYPASS"));
  }
  
  public static HashMap allowEntitlementBypass(HashMap paramHashMap)
  {
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("_ENT_BYPASS", "_ENT_BYPASS");
    return paramHashMap;
  }
  
  public static HashMap removeEntitlementBypass(HashMap paramHashMap)
  {
    if (paramHashMap != null) {
      paramHashMap.remove("_ENT_BYPASS");
    }
    return paramHashMap;
  }
  
  public static Limits checkLimitsAddConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    Limits localLimits1 = paramEntitlementCachedAdapter.checkLimitsAdd(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramDate);
    if (paramInt1 == paramInt2) {
      return localLimits1;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    Limits localLimits2 = paramEntitlementCachedAdapter.checkLimitsAdd(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramDate);
    return a(localLimits1, localLimits2);
  }
  
  public static Limits checkLimitsAddConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    Limits localLimits1 = paramEntitlementCachedAdapter.checkLimitsAdd(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramDate);
    if (paramInt1 == paramInt2) {
      return localLimits1;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    Limits localLimits2 = paramEntitlementCachedAdapter.checkLimitsAdd(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramDate);
    return a(localLimits1, localLimits2);
  }
  
  public static Limits checkLimitsEditConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    Limits localLimits1 = paramEntitlementCachedAdapter.checkLimitsEdit(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramDate1, paramDate2);
    if (paramInt1 == paramInt2) {
      return localLimits1;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    Limits localLimits2 = paramEntitlementCachedAdapter.checkLimitsEdit(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramDate1, paramDate2);
    return a(localLimits1, localLimits2);
  }
  
  public static Limits checkLimitsEditConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    Limits localLimits1 = paramEntitlementCachedAdapter.checkLimitsEdit(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramDate1, paramDate2);
    if (paramInt1 == paramInt2) {
      return localLimits1;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    Limits localLimits2 = paramEntitlementCachedAdapter.checkLimitsEdit(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramDate1, paramDate2);
    return a(localLimits1, localLimits2);
  }
  
  public static void checkLimitsDeleteConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    paramEntitlementCachedAdapter.checkLimitsDelete(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramDate);
    if (paramInt1 == paramInt2) {
      return;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    paramEntitlementCachedAdapter.checkLimitsDelete(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramDate);
  }
  
  public static void checkLimitsDeleteConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    paramEntitlementCachedAdapter.checkLimitsDelete(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramDate);
    if (paramInt1 == paramInt2) {
      return;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    paramEntitlementCachedAdapter.checkLimitsDelete(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramDate);
  }
  
  public static Limits checkLimitsAddConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    Limits localLimits1 = paramEntitlementCachedAdapter.checkLimitsAdd(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate);
    if (paramInt1 == paramInt2) {
      return localLimits1;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    Limits localLimits2 = paramEntitlementCachedAdapter.checkLimitsAdd(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate);
    return a(localLimits1, localLimits2);
  }
  
  public static Limits checkLimitsAddConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    Limits localLimits1 = paramEntitlementCachedAdapter.checkLimitsAdd(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate);
    if (paramInt1 == paramInt2) {
      return localLimits1;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    Limits localLimits2 = paramEntitlementCachedAdapter.checkLimitsAdd(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate);
    return a(localLimits1, localLimits2);
  }
  
  public static Limits checkLimitsEditConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    Limits localLimits1 = paramEntitlementCachedAdapter.checkLimitsEdit(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2);
    if (paramInt1 == paramInt2) {
      return localLimits1;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    Limits localLimits2 = paramEntitlementCachedAdapter.checkLimitsEdit(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2);
    return a(localLimits1, localLimits2);
  }
  
  public static Limits checkLimitsEditConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    Limits localLimits1 = paramEntitlementCachedAdapter.checkLimitsEdit(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2);
    if (paramInt1 == paramInt2) {
      return localLimits1;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    Limits localLimits2 = paramEntitlementCachedAdapter.checkLimitsEdit(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2);
    return a(localLimits1, localLimits2);
  }
  
  public static void checkLimitsDeleteConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    paramEntitlementCachedAdapter.checkLimitsDelete(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate);
    if (paramInt1 == paramInt2) {
      return;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    paramEntitlementCachedAdapter.checkLimitsDelete(paramConnection, localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate);
  }
  
  public static void checkLimitsDeleteConsumer(EntitlementCachedAdapter paramEntitlementCachedAdapter, Connection paramConnection, int paramInt1, int paramInt2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(String.valueOf(paramInt1));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType("1");
    paramEntitlementCachedAdapter.getMember(paramConnection, localEntitlementGroupMember);
    paramEntitlementCachedAdapter.checkLimitsDelete(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate);
    if (paramInt1 == paramInt2) {
      return;
    }
    localEntitlementGroupMember.setId(String.valueOf(paramInt2));
    paramEntitlementCachedAdapter.checkLimitsDelete(paramConnection, localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate);
  }
  
  private static Limits a(Limits paramLimits1, Limits paramLimits2)
  {
    Limits localLimits = new Limits();
    localLimits.addAll(paramLimits1);
    Iterator localIterator = paramLimits2.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (!localLimits.contains(localObject)) {
        localLimits.add(localObject);
      }
    }
    return localLimits;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.entitlements.EntitlementsUtil
 * JD-Core Version:    0.7.0.1
 */