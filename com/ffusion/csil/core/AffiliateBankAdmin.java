package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.affiliatebank.CutOffDefinition;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.ACH;
import com.ffusion.csil.handlers.ExternalTransferAdmin;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;

public class AffiliateBankAdmin
  extends Initialize
{
  private static Entitlement aDS = new Entitlement("BankView", null, null);
  private static Entitlement aDy = new Entitlement("BankAdd", null, null);
  private static Entitlement aDL = new Entitlement("BankDelete", null, null);
  private static Entitlement aDG = new Entitlement("BankModify", null, null);
  private static Entitlement aDQ = new Entitlement("BC Positive Pay Cut-offs View", null, null);
  private static Entitlement aDH = new Entitlement("BC Positive Pay Cut-offs Administration", null, null);
  private static final String aDI = "com.ffusion.util.logging.audit.affiliatebankadmin";
  private static final String aDz = "AuditMessage_1";
  private static final String aDB = "AuditMessage_2";
  private static final String aDA = "AuditMessage_3";
  private static final String aDF = "AuditMessage_4";
  private static final String aDT = "AuditMessage_5";
  private static final String aDR = "AuditMessage_6";
  private static final String aDE = "AuditMessage_7";
  private static final String aDN = "AuditMessage_8";
  private static final String aDJ = "AuditMessage_9";
  private static final String aDD = "AuditMessage_10";
  private static final String aDO = "AuditMessage_11";
  private static final String aDK = "AuditMessage_12";
  private static final String aDM = "AuditMessage_13";
  private static final String aDC = "AuditMessage_14";
  private static final String aDP = "AuditMessage_15";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.AffiliateBankAdmin.initialize");
    com.ffusion.csil.handlers.AffiliateBankAdmin.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.AffiliateBankAdmin.getService();
  }
  
  public static AffiliateBanks getAffiliateBankNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getAffiliateBankNames";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDS)))
    {
      long l = System.currentTimeMillis();
      AffiliateBanks localAffiliateBanks = com.ffusion.csil.handlers.AffiliateBankAdmin.getAffiliateBankNames(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAffiliateBanks;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_6", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getAffiliateBanks";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDS)))
    {
      long l = System.currentTimeMillis();
      AffiliateBanks localAffiliateBanks = com.ffusion.csil.handlers.AffiliateBankAdmin.getAffiliateBanks(paramSecureUser, paramHashMap);
      ACH.getAffiliateBanks(paramSecureUser, localAffiliateBanks, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAffiliateBanks;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static AffiliateBank getAffiliateBankInfoByID(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getAffiliateBankByID";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDS)))
    {
      long l = System.currentTimeMillis();
      AffiliateBank localAffiliateBank = com.ffusion.csil.handlers.AffiliateBankAdmin.getAffiliateBankByID(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAffiliateBank;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_6", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static AffiliateBank getAffiliateBankByID(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getAffiliateBankByID";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDS)))
    {
      long l = System.currentTimeMillis();
      AffiliateBank localAffiliateBank = com.ffusion.csil.handlers.AffiliateBankAdmin.getAffiliateBankByID(paramSecureUser, paramInt, paramHashMap);
      localAffiliateBank = ACH.getAffiliateBank(paramSecureUser, localAffiliateBank, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAffiliateBank;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_6", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static AffiliateBank getAffiliateBankByRoutingNumber(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getAffiliateBankByRoutingNumber";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDS)))
    {
      long l = System.currentTimeMillis();
      AffiliateBank localAffiliateBank = com.ffusion.csil.handlers.AffiliateBankAdmin.getAffiliateBankByRoutingNumber(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAffiliateBank;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_6", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static AffiliateBank getAffiliateBankByBPWID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getAffiliateBankByBPWID";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDS))
    {
      long l = System.currentTimeMillis();
      AffiliateBank localAffiliateBank = com.ffusion.csil.handlers.AffiliateBankAdmin.getAffiliateBankByBPWID(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAffiliateBank;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_6", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getRestrictedCalculations(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getRestrictedCalculations";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDS)))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = com.ffusion.csil.handlers.AffiliateBankAdmin.getRestrictedCalculations(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localArrayList;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_8", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void saveRestrictedCalculations(SecureUser paramSecureUser, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AffiliateBankAdmin.saveRestrictedCalculations";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDG))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.csil.handlers.AffiliateBankAdmin.saveRestrictedCalculations(paramSecureUser, paramInt, paramArrayList, paramHashMap);
      PerfLog.log(str1, l, true);
      AffiliateBank localAffiliateBank = com.ffusion.csil.handlers.AffiliateBankAdmin.getAffiliateBankByID(paramSecureUser, paramInt, paramHashMap);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localAffiliateBank.getAffiliateBankName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_1", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 1404);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_9", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static AffiliateBank addAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AffiliateBankAdmin.addAffiliateBank";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDy))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      AffiliateBank localAffiliateBank = ACH.addAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
      try
      {
        localAffiliateBank = com.ffusion.csil.handlers.AffiliateBankAdmin.addAffiliateBank(paramSecureUser, localAffiliateBank, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        ACH.deleteAffiliateBank(paramSecureUser, localAffiliateBank, paramHashMap);
        throw localCSILException;
      }
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramAffiliateBank.getAffiliateBankName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_2", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 1400);
      debug(paramSecureUser, str1);
      return localAffiliateBank;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_10", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static AffiliateBank deleteAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AffiliateBank.deleteAffiliateBank";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDL))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      AffiliateBank localAffiliateBank = ACH.deleteAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
      localAffiliateBank = com.ffusion.csil.handlers.AffiliateBankAdmin.deleteAffiliateBank(paramSecureUser, localAffiliateBank, paramHashMap);
      AutoEntitleAdmin.deleteSettings(paramSecureUser, localAffiliateBank, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramAffiliateBank.getAffiliateBankName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_3", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 1402);
      debug(paramSecureUser, str1);
      return localAffiliateBank;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_11", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static AffiliateBank modifyAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AffiliateBankAdmin.ModifyAffiliateBank";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDG))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      AffiliateBank localAffiliateBank = ACH.modifyAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
      localAffiliateBank = com.ffusion.csil.handlers.AffiliateBankAdmin.modifyAffiliateBank(paramSecureUser, localAffiliateBank, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramAffiliateBank.getAffiliateBankName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_4", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 1401);
      debug(paramSecureUser, str1);
      return localAffiliateBank;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_12", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static CutOffDefinition getCutOffDefinition(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getCutOffDefinition";
    long l;
    CutOffDefinition localCutOffDefinition;
    if ((paramSecureUser.getUserType() == 1) || (paramSecureUser.getUserType() == 3))
    {
      if (paramSecureUser.getAffiliateIDValue() != paramInt1)
      {
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_13", null);
        logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 20001);
      }
      l = System.currentTimeMillis();
      localCutOffDefinition = com.ffusion.csil.handlers.AffiliateBankAdmin.getCutOffDefinition(paramSecureUser, paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCutOffDefinition;
    }
    if (paramSecureUser.getUserType() == 2)
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDQ))
      {
        l = System.currentTimeMillis();
        localCutOffDefinition = com.ffusion.csil.handlers.AffiliateBankAdmin.getCutOffDefinition(paramSecureUser, paramInt1, paramInt2, paramHashMap);
        PerfLog.log(str, l, true);
        debug(paramSecureUser, str);
        return localCutOffDefinition;
      }
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_14", null);
      logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_14", null);
    logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void setCutOffDefinition(SecureUser paramSecureUser, int paramInt1, int paramInt2, CutOffDefinition paramCutOffDefinition, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AffiliateBank.setCutOffDefinition";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDH))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.csil.handlers.AffiliateBankAdmin.setCutOffDefinition(paramSecureUser, paramInt1, paramInt2, paramCutOffDefinition, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = new Integer(paramInt2).toString();
      arrayOfObject[1] = new Integer(paramInt1).toString();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_5", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 1403);
      debug(paramSecureUser, str1);
      return;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_15", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static AffiliateBank updateVirtualUserAndETFCompany(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AffiliateBankAdmin.addVirtualUser";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDy))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      try
      {
        if (paramAffiliateBank.getEtfVirtualUserID() == null)
        {
          paramAffiliateBank.setEtfVirtualUserID("" + CustomerAdapter.getVirtualUserID());
          Business localBusiness = new Business();
          localBusiness.setId(paramAffiliateBank.getEtfVirtualUserID());
          localBusiness.setBusinessName(paramAffiliateBank.getAffiliateBankName());
          localBusiness.setDescription("Virtual User");
          ACH.addCustomer(paramSecureUser, paramAffiliateBank.getFIBPWID(), localBusiness, paramHashMap);
        }
        int i = 0;
        ExtTransferCompany localExtTransferCompany1 = new ExtTransferCompany();
        ExtTransferCompany localExtTransferCompany2 = paramAffiliateBank.getEtfCompany();
        if (localExtTransferCompany2 == null) {
          localExtTransferCompany2 = new ExtTransferCompany();
        } else {
          localExtTransferCompany1.set(localExtTransferCompany2);
        }
        localExtTransferCompany1.setCustID(null);
        String str3 = paramAffiliateBank.getEtfCompanyBatchDescription();
        if ((str3 != null) && (str3.trim().length() > 0) && (!str3.trim().equals(localExtTransferCompany2.getBatchDescription())))
        {
          localExtTransferCompany1.setBatchDescription(str3.trim());
          i = 1;
        }
        String str4 = paramAffiliateBank.getEtfCompanyName();
        if ((str4 != null) && (str4.trim().length() > 0) && (!str4.trim().equals(localExtTransferCompany2.getCompanyName())))
        {
          localExtTransferCompany1.setCompanyName(str4.trim());
          i = 1;
        }
        String str5 = paramAffiliateBank.getEtfCompanyID();
        if ((str5 != null) && (str5.trim().length() > 0) && (!str5.trim().equals(localExtTransferCompany2.getCompanyID())))
        {
          localExtTransferCompany1.setCompanyID(str5.trim());
          i = 1;
        }
        if (i != 0) {
          if (paramAffiliateBank.getEtfCompany() != null) {
            ExternalTransferAdmin.modifyExtTransferCompany(paramSecureUser, localExtTransferCompany1, localExtTransferCompany2, paramAffiliateBank.getFIBPWID(), paramHashMap);
          } else {
            ExternalTransferAdmin.addExtTransferCompany(paramSecureUser, localExtTransferCompany1, paramAffiliateBank.getFIBPWID(), paramHashMap);
          }
        }
      }
      catch (ProfileException localProfileException) {}catch (CSILException localCSILException)
      {
        throw localCSILException;
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return paramAffiliateBank;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.affiliatebankadmin", "AuditMessage_10", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.AffiliateBankAdmin
 * JD-Core Version:    0.7.0.1
 */