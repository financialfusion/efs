package com.ffusion.ffs.bpw.util;

import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.enums.UserType;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class LimitCheckUtil
{
  private static PropertyConfig a;
  
  public static Limits checkLimitsAdd(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    a = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = a.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    return checkLimitsAdd(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, paramEntitlement, paramBigDecimal, paramDate, str, paramEntitlementCachedAdapter);
  }
  
  public static Limits checkLimitsAdd(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, String paramString3, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    FFSDebug.log("LimitCheckUtil.checkLimitsAdd start", 6);
    FFSDebug.log("primary cust = " + paramString1, 6);
    FFSDebug.log("submitter = " + paramString2, 6);
    FFSDebug.log("CurrencyCode = " + paramString3, 6);
    if (Customer.getCustomerInfo(paramString1, paramFFSConnectionHolder, paramBPWInfoBase).getResolvedUserType().equals(UserType.BUSINESS))
    {
      EntitlementGroupMember localEntitlementGroupMember = createMember(paramBPWInfoBase);
      return paramEntitlementCachedAdapter.checkLimitsAdd(paramFFSConnectionHolder.conn.getConnection(), localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString3, paramDate);
    }
    return EntitlementsUtil.checkLimitsAddConsumer(paramEntitlementCachedAdapter, paramFFSConnectionHolder.conn.getConnection(), Integer.parseInt(paramString1), Integer.parseInt(paramString2), paramEntitlement, paramBigDecimal, paramString3, paramDate);
  }
  
  public static Limits checkLimitsAdd(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    a = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = a.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    return checkLimitsAdd(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, paramMultiEntitlement, paramBigDecimal, paramDate, str, paramEntitlementCachedAdapter);
  }
  
  public static Limits checkLimitsAdd(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, String paramString3, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    FFSDebug.log("LimitCheckUtil.checkLimitsAdd (multi) start", 6);
    FFSDebug.log("primary cust = " + paramString1, 6);
    FFSDebug.log("submitter = " + paramString2, 6);
    FFSDebug.log("CurrencyCode " + paramString3, 6);
    if (Customer.getCustomerInfo(paramString1, paramFFSConnectionHolder, paramBPWInfoBase).getResolvedUserType().equals(UserType.BUSINESS))
    {
      EntitlementGroupMember localEntitlementGroupMember = createMember(paramBPWInfoBase);
      return paramEntitlementCachedAdapter.checkLimitsAdd(paramFFSConnectionHolder.conn.getConnection(), localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString3, paramDate);
    }
    return EntitlementsUtil.checkLimitsAddConsumer(paramEntitlementCachedAdapter, paramFFSConnectionHolder.conn.getConnection(), Integer.parseInt(paramString1), Integer.parseInt(paramString2), paramMultiEntitlement, paramBigDecimal, paramString3, paramDate);
  }
  
  public static Limits checkLimitsAdd(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement[] paramArrayOfMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    a = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = a.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    return checkLimitsAdd(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, paramArrayOfMultiEntitlement, paramBigDecimal, paramDate, str, paramEntitlementCachedAdapter);
  }
  
  public static Limits checkLimitsAdd(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement[] paramArrayOfMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, String paramString3, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    FFSDebug.log("LimitCheckUtil.checkLimitsAdd (multi [ ]) start", 6);
    FFSDebug.log("primary cust = " + paramString1, 6);
    FFSDebug.log("submitter = " + paramString2, 6);
    FFSDebug.log("CurrencyCode = " + paramString3, 6);
    Limits localLimits = new Limits();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramArrayOfMultiEntitlement.length; i++)
    {
      try
      {
        if (Customer.getCustomerInfo(paramString1, paramFFSConnectionHolder, paramBPWInfoBase).getResolvedUserType().equals(UserType.BUSINESS))
        {
          EntitlementGroupMember localEntitlementGroupMember = createMember(paramBPWInfoBase);
          localLimits.addAll(paramEntitlementCachedAdapter.checkLimitsAdd(paramFFSConnectionHolder.conn.getConnection(), localEntitlementGroupMember, paramArrayOfMultiEntitlement[i], paramBigDecimal, paramString3, paramDate));
        }
        else
        {
          localLimits.addAll(EntitlementsUtil.checkLimitsAddConsumer(paramEntitlementCachedAdapter, paramFFSConnectionHolder.conn.getConnection(), Integer.parseInt(paramString1), Integer.parseInt(paramString2), paramArrayOfMultiEntitlement[i], paramBigDecimal, paramString3, paramDate));
        }
      }
      catch (EntitlementException localEntitlementException)
      {
        localLimits.clear();
        if (localArrayList.size() > 0) {
          try
          {
            checkLimitsDelete(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, (MultiEntitlement[])localArrayList.toArray(new MultiEntitlement[0]), paramBigDecimal, paramDate, paramString3, paramEntitlementCachedAdapter);
          }
          catch (Exception localException) {}
        }
        throw localEntitlementException;
      }
      localArrayList.add(paramArrayOfMultiEntitlement[i]);
    }
    return localLimits;
  }
  
  public static Limits checkLimitsEdit(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    a = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = a.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    return checkLimitsEdit(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramDate1, paramDate2, str, paramEntitlementCachedAdapter);
  }
  
  public static Limits checkLimitsEdit(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2, String paramString3, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    FFSDebug.log("LimitCheckUtil.checkLimitsEdit start");
    FFSDebug.log("primary cust = " + paramString1, 6);
    FFSDebug.log("submitter = " + paramString2, 6);
    FFSDebug.log("CurrencyCode = " + paramString3, 6);
    if (Customer.getCustomerInfo(paramString1, paramFFSConnectionHolder, paramBPWInfoBase).getResolvedUserType().equals(UserType.BUSINESS))
    {
      EntitlementGroupMember localEntitlementGroupMember = createMember(paramBPWInfoBase);
      return paramEntitlementCachedAdapter.checkLimitsEdit(paramFFSConnectionHolder.conn.getConnection(), localEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramString3, paramDate1, paramDate2);
    }
    return EntitlementsUtil.checkLimitsEditConsumer(paramEntitlementCachedAdapter, paramFFSConnectionHolder.conn.getConnection(), Integer.parseInt(paramString1), Integer.parseInt(paramString2), paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramString3, paramDate1, paramDate2);
  }
  
  public static Limits checkLimitsEdit(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    a = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = a.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    return checkLimitsEdit(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramDate1, paramDate2, str, paramEntitlementCachedAdapter);
  }
  
  public static Limits checkLimitsEdit(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2, String paramString3, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    FFSDebug.log("LimitCheckUtil.checkLimitsEdit(multi) start");
    FFSDebug.log("primary cust = " + paramString1, 6);
    FFSDebug.log("submitter = " + paramString2, 6);
    FFSDebug.log("CurrencyCode = " + paramString3, 6);
    if (Customer.getCustomerInfo(paramString1, paramFFSConnectionHolder, paramBPWInfoBase).getResolvedUserType().equals(UserType.BUSINESS))
    {
      EntitlementGroupMember localEntitlementGroupMember = createMember(paramBPWInfoBase);
      return paramEntitlementCachedAdapter.checkLimitsEdit(paramFFSConnectionHolder.conn.getConnection(), localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramString3, paramDate1, paramDate2);
    }
    return EntitlementsUtil.checkLimitsEditConsumer(paramEntitlementCachedAdapter, paramFFSConnectionHolder.conn.getConnection(), Integer.parseInt(paramString1), Integer.parseInt(paramString2), paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramString3, paramDate1, paramDate2);
  }
  
  public static void checkLimitsDelete(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    a = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = a.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    checkLimitsDelete(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, paramEntitlement, paramBigDecimal, paramDate, str, paramEntitlementCachedAdapter);
  }
  
  public static void checkLimitsDelete(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, String paramString3, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    FFSDebug.log("LimitCheckUtil.checkLimitsDelete start");
    FFSDebug.log("primary cust = " + paramString1, 6);
    FFSDebug.log("submitter = " + paramString2, 6);
    FFSDebug.log("CurrencyCode = " + paramString3, 6);
    if (Customer.getCustomerInfo(paramString1, paramFFSConnectionHolder, paramBPWInfoBase).getResolvedUserType().equals(UserType.BUSINESS))
    {
      EntitlementGroupMember localEntitlementGroupMember = createMember(paramBPWInfoBase);
      paramEntitlementCachedAdapter.checkLimitsDelete(paramFFSConnectionHolder.conn.getConnection(), localEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString3, paramDate);
    }
    else
    {
      EntitlementsUtil.checkLimitsDeleteConsumer(paramEntitlementCachedAdapter, paramFFSConnectionHolder.conn.getConnection(), Integer.parseInt(paramString1), Integer.parseInt(paramString2), paramEntitlement, paramBigDecimal, paramString3, paramDate);
    }
  }
  
  public static void checkLimitsDelete(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    a = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = a.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    checkLimitsDelete(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, paramMultiEntitlement, paramBigDecimal, paramDate, str, paramEntitlementCachedAdapter);
  }
  
  public static void checkLimitsDelete(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, String paramString3, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    FFSDebug.log("LimitCheckUtil.checkLimitsDelete (multi) start");
    FFSDebug.log("primary cust = " + paramString1, 6);
    FFSDebug.log("submitter = " + paramString2, 6);
    FFSDebug.log("CurrencyCode = " + paramString3, 6);
    if (Customer.getCustomerInfo(paramString1, paramFFSConnectionHolder, paramBPWInfoBase).getResolvedUserType().equals(UserType.BUSINESS))
    {
      EntitlementGroupMember localEntitlementGroupMember = createMember(paramBPWInfoBase);
      paramEntitlementCachedAdapter.checkLimitsDelete(paramFFSConnectionHolder.conn.getConnection(), localEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString3, paramDate);
    }
    else
    {
      EntitlementsUtil.checkLimitsDeleteConsumer(paramEntitlementCachedAdapter, paramFFSConnectionHolder.conn.getConnection(), Integer.parseInt(paramString1), Integer.parseInt(paramString2), paramMultiEntitlement, paramBigDecimal, paramString3, paramDate);
    }
  }
  
  public static void checkLimitsDelete(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement[] paramArrayOfMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    a = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = a.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    checkLimitsDelete(paramFFSConnectionHolder, paramBPWInfoBase, paramString1, paramString2, paramArrayOfMultiEntitlement, paramBigDecimal, paramDate, str, paramEntitlementCachedAdapter);
  }
  
  public static void checkLimitsDelete(FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase, String paramString1, String paramString2, MultiEntitlement[] paramArrayOfMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, String paramString3, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws BPWException, EntitlementException
  {
    FFSDebug.log("LimitCheckUtil.checkLimitsDelete (multi [ ])start");
    FFSDebug.log("primary cust = " + paramString1, 6);
    FFSDebug.log("submitter = " + paramString2, 6);
    FFSDebug.log("CurrencyCode = " + paramString3, 6);
    Object localObject = null;
    for (int i = 0; i < paramArrayOfMultiEntitlement.length; i++) {
      try
      {
        if (Customer.getCustomerInfo(paramString1, paramFFSConnectionHolder, paramBPWInfoBase).getResolvedUserType().equals(UserType.BUSINESS))
        {
          EntitlementGroupMember localEntitlementGroupMember = createMember(paramBPWInfoBase);
          paramEntitlementCachedAdapter.checkLimitsDelete(paramFFSConnectionHolder.conn.getConnection(), localEntitlementGroupMember, paramArrayOfMultiEntitlement[i], paramBigDecimal, paramString3, paramDate);
        }
        else
        {
          EntitlementsUtil.checkLimitsDeleteConsumer(paramEntitlementCachedAdapter, paramFFSConnectionHolder.conn.getConnection(), Integer.parseInt(paramString1), Integer.parseInt(paramString2), paramArrayOfMultiEntitlement[i], paramBigDecimal, paramString3, paramDate);
        }
      }
      catch (EntitlementException localEntitlementException)
      {
        localObject = localEntitlementException;
      }
    }
    if (localObject != null) {
      throw localObject;
    }
  }
  
  public static EntitlementGroupMember createMember(BPWInfoBase paramBPWInfoBase)
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    if ((paramBPWInfoBase instanceof WireInfo))
    {
      WireInfo localWireInfo = (WireInfo)paramBPWInfoBase;
      String str = localWireInfo.getWireScope();
      if ((localWireInfo.isTemplateWire()) && (str != null) && (str.equalsIgnoreCase("BANK")))
      {
        localEntitlementGroupMember.setId(localWireInfo.getCustomerID());
        localEntitlementGroupMember.setMemberType("BUSINESS");
        localEntitlementGroupMember.setMemberSubType("0");
      }
      else
      {
        localEntitlementGroupMember.setId(localWireInfo.getSubmittedBy());
        localEntitlementGroupMember.setMemberType("USER");
        localEntitlementGroupMember.setMemberSubType("1");
      }
    }
    else
    {
      localEntitlementGroupMember.setId(paramBPWInfoBase.getSubmittedBy());
      localEntitlementGroupMember.setMemberSubType("1");
      localEntitlementGroupMember.setMemberType("USER");
    }
    localEntitlementGroupMember.setBusinessID(-1);
    localEntitlementGroupMember.setEntitlementGroupId(-1);
    return localEntitlementGroupMember;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.LimitCheckUtil
 * JD-Core Version:    0.7.0.1
 */