package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;

public class SystemAdmin
  extends Initialize
{
  private static final String asF = "com.ffusion.util.logging.audit.systemadmin";
  private static final String asw = "AuditEntFault_1";
  private static final String asz = "AuditEntFault_2";
  private static final String asB = "AuditEntFault_3";
  private static final Entitlement asA = new Entitlement("BCDataRetentionView", null, null);
  private static final Entitlement asD = new Entitlement("BCDataRetentionAdministration", null, null);
  private static final Entitlement asH = new Entitlement("BankView", null, null);
  private static final Entitlement asG = new Entitlement("BankModify", null, null);
  private static final Entitlement asC = new Entitlement("BankServicesView", null, null);
  private static final Entitlement asE = new Entitlement("BankServicesCrud", null, null);
  private static final Entitlement asx = new Entitlement("BusinessProfileView", null, null);
  private static final Entitlement asy = new Entitlement("BusinessProfileEdit", null, null);
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.SystemAdmin.initialize");
    com.ffusion.csil.handlers.SystemAdmin.initialize(paramHashMap);
  }
  
  public static HashMap getDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SystemAdmin.getDataRetentionSettings";
    HashMap localHashMap = null;
    Entitlement localEntitlement = null;
    StringBuffer localStringBuffer = null;
    localEntitlement = new Entitlement();
    localStringBuffer = new StringBuffer();
    if (!jdMethod_for(paramInt1, localEntitlement, localStringBuffer, true))
    {
      CSILException localCSILException1 = new CSILException(str, 38000);
      debug("Invalid data retention type specified.");
      throw localCSILException1;
    }
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
    {
      long l = System.currentTimeMillis();
      localHashMap = com.ffusion.csil.handlers.SystemAdmin.getDataRetentionSettings(paramSecureUser, paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      CSILException localCSILException2 = new CSILException(str, 20001);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localStringBuffer.toString();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.systemadmin", "AuditEntFault_1", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw localCSILException2;
    }
    return localHashMap;
  }
  
  public static HashMap getCumulativeDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SystemAdmin.getCumulativeDataRetentionSettings";
    HashMap localHashMap = null;
    Entitlement localEntitlement = null;
    StringBuffer localStringBuffer = null;
    localEntitlement = new Entitlement();
    localStringBuffer = new StringBuffer();
    if (!jdMethod_for(paramInt1, localEntitlement, localStringBuffer, true))
    {
      CSILException localCSILException1 = new CSILException(str, 38000);
      debug("Invalid data retention type specified.");
      throw localCSILException1;
    }
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
    {
      long l = System.currentTimeMillis();
      localHashMap = com.ffusion.csil.handlers.SystemAdmin.getCumulativeDataRetentionSettings(paramSecureUser, paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      CSILException localCSILException2 = new CSILException(str, 20001);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localStringBuffer.toString();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.systemadmin", "AuditEntFault_2", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw localCSILException2;
    }
    return localHashMap;
  }
  
  public static void setDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "SystemAdmin.setDataRetentionSettings";
    Object localObject = null;
    Entitlement localEntitlement = null;
    StringBuffer localStringBuffer = null;
    localEntitlement = new Entitlement();
    localStringBuffer = new StringBuffer();
    if (!jdMethod_for(paramInt1, localEntitlement, localStringBuffer, false))
    {
      CSILException localCSILException1 = new CSILException(str, 38000);
      debug("Invalid data retention type specified.");
      throw localCSILException1;
    }
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
    {
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.SystemAdmin.setDataRetentionSettings(paramSecureUser, paramInt1, paramInt2, paramHashMap1, paramHashMap2);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      CSILException localCSILException2 = new CSILException(str, 20001);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localStringBuffer.toString();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.systemadmin", "AuditEntFault_3", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw localCSILException2;
    }
  }
  
  private static boolean jdMethod_for(int paramInt, Entitlement paramEntitlement, StringBuffer paramStringBuffer, boolean paramBoolean)
  {
    boolean bool = true;
    switch (paramInt)
    {
    case 0: 
      if (paramBoolean)
      {
        paramEntitlement.setOperationName(asA.getOperationName());
        paramEntitlement.setObjectType(asA.getObjectType());
        paramEntitlement.setObjectId(asA.getObjectId());
        paramStringBuffer.setLength(0);
        paramStringBuffer.append("BCDataRetentionView");
      }
      else
      {
        paramEntitlement.setOperationName(asD.getOperationName());
        paramEntitlement.setObjectType(asD.getObjectType());
        paramEntitlement.setObjectId(asD.getObjectId());
        paramStringBuffer.setLength(0);
        paramStringBuffer.append("BCDataRetentionAdministration");
      }
      break;
    case 1: 
      if (paramBoolean)
      {
        paramEntitlement.setOperationName(asH.getOperationName());
        paramEntitlement.setObjectType(asH.getObjectType());
        paramEntitlement.setObjectId(asH.getObjectId());
        paramStringBuffer.setLength(0);
        paramStringBuffer.append("BankView");
      }
      else
      {
        paramEntitlement.setOperationName(asG.getOperationName());
        paramEntitlement.setObjectType(asG.getObjectType());
        paramEntitlement.setObjectId(asG.getObjectId());
        paramStringBuffer.setLength(0);
        paramStringBuffer.append("BankModify");
      }
      break;
    case 2: 
    case 3: 
      if (paramBoolean)
      {
        paramEntitlement.setOperationName(asC.getOperationName());
        paramEntitlement.setObjectType(asC.getObjectType());
        paramEntitlement.setObjectId(asC.getObjectId());
        paramStringBuffer.setLength(0);
        paramStringBuffer.append("BankServicesView");
      }
      else
      {
        paramEntitlement.setOperationName(asE.getOperationName());
        paramEntitlement.setObjectType(asE.getObjectType());
        paramEntitlement.setObjectId(asE.getObjectId());
        paramStringBuffer.setLength(0);
        paramStringBuffer.append("BankServicesCrud");
      }
      break;
    case 4: 
      if (paramBoolean)
      {
        paramEntitlement.setOperationName(asx.getOperationName());
        paramEntitlement.setObjectType(asx.getObjectType());
        paramEntitlement.setObjectId(asx.getObjectId());
        paramStringBuffer.setLength(0);
        paramStringBuffer.append("BusinessProfileView");
      }
      else
      {
        paramEntitlement.setOperationName(asy.getOperationName());
        paramEntitlement.setObjectType(asy.getObjectType());
        paramEntitlement.setObjectId(asy.getObjectId());
        paramStringBuffer.setLength(0);
        paramStringBuffer.append("BusinessProfileEdit");
      }
      break;
    default: 
      bool = false;
      paramEntitlement.setOperationName(null);
      paramEntitlement.setObjectType(null);
      paramEntitlement.setObjectId(null);
      paramStringBuffer.setLength(0);
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.SystemAdmin
 * JD-Core Version:    0.7.0.1
 */