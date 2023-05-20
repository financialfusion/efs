package com.ffusion.csil.core;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.approvals.ApprovalsChainItem;
import com.ffusion.beans.approvals.ApprovalsChainItems;
import com.ffusion.beans.approvals.ApprovalsDecision;
import com.ffusion.beans.approvals.ApprovalsDecisions;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsItemError;
import com.ffusion.beans.approvals.ApprovalsItemErrors;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.business.FeatureListFactory;
import com.ffusion.beans.business.MarketSegment;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.beans.business.PendingTransaction;
import com.ffusion.beans.business.PendingTransactions;
import com.ffusion.beans.business.ServiceFeature;
import com.ffusion.beans.business.ServiceFeatures;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.beans.business.TransactionLimit;
import com.ffusion.beans.business.TransactionLimits;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.handlers.Messages;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.admin.HandleParentChildDisplay;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.MapUtil;
import com.ffusion.util.beans.LocalizableProperty;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Business
  extends Initialize
{
  private static Entitlement aC3 = new Entitlement("BusinessProfileCreate", null, null);
  private static Entitlement aDd = new Entitlement("BusinessProfileEdit", null, null);
  private static Entitlement aCF = new Entitlement("BusinessProfileDelete", null, null);
  private static Entitlement aC4 = new Entitlement("BusinessProfileView", null, null);
  private static Entitlement aCv = new Entitlement("BusinessApproval", null, null);
  private static Entitlement aCn = new Entitlement("BankServicesCrud", null, null);
  private static Entitlement aCJ = new Entitlement("BankServicesView", null, null);
  private static Entitlement aCA = new Entitlement("Approvals Admin", null, null);
  private static Entitlement aDw = new Entitlement("HistoryCreate", null, null);
  private static Entitlement aCV = new Entitlement("HistoryView", null, null);
  private static Entitlement aCZ = new Entitlement("BC ACH Management", null, null);
  private static Entitlement aDf = new Entitlement("BC Cash Con Company Management", null, null);
  private static final String aDs = "0";
  public static final String PRIMARY_CONTACT_PERMISSIONS = "primaryContactPermissions";
  public static final String SECONDARY_CONTACT_PERMISSIONS = "secondaryContactPermissions";
  public static final String PERMISSIONS_CREATE = "create";
  public static final String PERMISSIONS_EDIT = "edit";
  public static final String PRIMARY_CONTACT_ID = "PRIMARY_CONTACT_ID";
  public static final String SECONDARY_CONTACT_ID = "SECONDARY_CONTACT_ID";
  public static final String OLD_PRIMARY_CONTACT_ID = "OLD_PRIMARY_CONTACT_ID";
  public static final String OLD_SECONDARY_CONTACT_ID = "OLD_SECONDARY_CONTACT_ID";
  private static final String aC5 = "com.ffusion.util.logging.audit.business";
  private static final String aCk = "AuditMessage_1";
  private static final String aCM = "AuditMessage_2";
  private static final String aCj = "AuditMessage_3";
  private static final String aCS = "AuditMessage_4";
  private static final String aDn = "AuditMessage_5";
  private static final String aC1 = "AuditMessage_6";
  private static final String aDj = "AuditMessage_7";
  private static final String aDb = "AuditMessage_8";
  private static final String aC0 = "AuditMessage_9";
  private static final String aDt = "AuditMessage_10";
  private static final String aCH = "AuditMessage_11";
  private static final String aCB = "AuditMessage_12";
  private static final String aCO = "AuditMessage_13";
  private static final String aCE = "AuditMessage_14";
  private static final String aCp = "AuditMessage_15";
  private static final String aCz = "AuditMessage_16";
  private static final String aCT = "AuditMessage_17";
  private static final String aCR = "AuditMessage_18";
  private static final String aDu = "AuditMessage_19";
  private static final String aCP = "AuditMessage_20";
  private static final String aC8 = "AuditMessage_21";
  private static final String aCI = "AuditEntFault_1";
  private static final String aCC = "AuditEntFault_2";
  private static final String aDe = "AuditEntFault_3";
  private static final String aDr = "AuditEntFault_4";
  private static final String aDo = "AuditEntFault_5";
  private static final String aCK = "AuditEntFault_6";
  private static final String aC6 = "AuditEntFault_7";
  private static final String aCW = "AuditEntFault_8";
  private static final String aC9 = "AuditEntFault_9";
  private static final String aCr = "AuditEntFault_10";
  private static final String aDp = "AuditEntFault_11";
  private static final String aDl = "AuditEntFault_12";
  private static final String aCN = "AuditEntFault_13";
  private static final String aCY = "AuditEntFault_14";
  private static final String aCD = "AuditEntFault_15";
  private static final String aCi = "AuditEntFault_16";
  private static final String aCx = "AuditEntFault_17";
  private static final String aCw = "AuditEntFault_18";
  private static final String aCG = "AuditEntFault_19";
  private static final String aDh = "AuditEntFault_20";
  private static final String aCl = "AuditEntFault_21";
  private static final String aCU = "AuditEntFault_22";
  private static final String aDm = "AuditEntFault_23";
  private static final String aCu = "AuditEntFault_24";
  private static final String aC2 = "AuditEntFault_25";
  private static final String aCq = "AuditEntFault_26";
  private static final String aDa = "AuditEntFault_27";
  private static final String aCt = "AuditEntFault_28";
  private static final String aDx = "AuditEntFault_29";
  private static final String aDq = "AuditEntFault_30";
  private static final String aCL = "AuditEntFault_31";
  private static final String aDk = "AuditEntFault_32";
  private static final String aCX = "AuditEntFault_33";
  private static final String aCQ = "AuditEntFault_34";
  private static final String aDg = "AuditEntFault_35";
  private static final String aDi = "AuditEntFault_36";
  private static final String aCy = "AuditEntFault_37";
  private static final String aCs = "AuditEntFault_38";
  private static final String aCo = "com.ffusion.approvals.resources";
  private static final String aCm = "BusinessPrimaryContactNotificationMessageSubject";
  private static final String aC7 = "BusinessPrimaryContactNotificationMessageBody";
  private static final String aDc = "Decision_Approved";
  private static final String aDv = "Decision_Rejected";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Business.initialize");
    com.ffusion.csil.handlers.Business.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.Business.getService();
  }
  
  public static com.ffusion.beans.business.Business modifyBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness1, com.ffusion.beans.business.Business paramBusiness2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.ModifyBusiness";
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember1, aDd))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if (!jdMethod_for(localEntitlementGroupMember1, paramBusiness1, paramBusiness2)) {
        throw new CSILException(str1, 20001);
      }
      if (!jdMethod_int(localEntitlementGroupMember1, paramBusiness1, paramBusiness2)) {
        throw new CSILException(str1, 20001);
      }
      localObject1 = new EntitlementAdmin(localEntitlementGroupMember1, paramBusiness1.getEntitlementGroupIdValue());
      if (Entitlements.canAdminister((EntitlementAdmin)localObject1))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        EntitlementGroupMember localEntitlementGroupMember2 = paramBusiness1.getEntitlementGroupMember();
        localEntitlementGroupMember2 = Entitlements.getMember(localEntitlementGroupMember2);
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroupMember2.getEntitlementGroupId());
        int i = paramBusiness2.getServicesPackageIdValue();
        int j = paramBusiness1.getServicesPackageIdValue();
        if ((i != j) && (!jdMethod_for(j, localEntitlementGroup.getGroupId()))) {
          throw new CSILException(str1, 4019);
        }
        if ((paramBusiness1.getStatusValue() == 1) && (paramBusiness2.getStatusValue() == 0))
        {
          if (paramHashMap == null) {
            paramHashMap = new HashMap();
          }
          paramHashMap.put("APPROVED", "yes");
          paramHashMap.put("primaryContactPermissions", "create");
          paramHashMap.put("secondaryContactPermissions", "create");
        }
        if ((paramBusiness2.getStatusValue() == 1) && (paramBusiness1.getStatusValue() == 1))
        {
          if (paramHashMap == null) {
            paramHashMap = new HashMap();
          }
          localObject2 = MapUtil.getStringValue(paramHashMap, "PRIMARY_CONTACT_ID", "");
          localObject3 = MapUtil.getStringValue(paramHashMap, "SECONDARY_CONTACT_ID", "");
          str3 = MapUtil.getStringValue(paramHashMap, "OLD_PRIMARY_CONTACT_ID", "");
          str4 = MapUtil.getStringValue(paramHashMap, "OLD_SECONDARY_CONTACT_ID", "");
          if ((!((String)localObject2).equals(str3)) && (paramBusiness1.getPrimaryContactPermsValue() != 3)) {
            paramHashMap.put("primaryContactPermissions", "edit");
          }
          if ((!((String)localObject3).equals(str4)) && (((String)localObject3).length() > 0) && (!((String)localObject3).equalsIgnoreCase("none")) && (paramBusiness1.getSecondaryContactPermsValue() != 3)) {
            paramHashMap.put("secondaryContactPermissions", "edit");
          }
        }
        Object localObject2 = com.ffusion.csil.handlers.Business.modifyBusiness(paramSecureUser, paramBusiness1, paramHashMap);
        if ((isBusinessRegisteredWithBPW(paramSecureUser, paramBusiness1.getId(), paramHashMap)) && (!paramBusiness1.getBusinessName().equals(paramBusiness2.getBusinessName()))) {
          modifyBusinessRegisteredWithBPW(paramSecureUser, paramBusiness1, paramHashMap);
        }
        Object localObject3 = new HistoryTracker(paramSecureUser, 2, paramBusiness1.getId());
        String str3 = paramBusiness2.getBusinessName();
        String str4 = paramBusiness1.getBusinessName();
        if (!str3.equals(str4))
        {
          localEntitlementGroup.setGroupName("Admin_" + str4);
          Entitlements.modifyEntitlementGroup(localEntitlementGroupMember1, localEntitlementGroup);
          localObject4 = Entitlements.getChildrenByGroupType(localEntitlementGroup.getGroupId(), "Business");
          localObject5 = null;
          if (((EntitlementGroups)localObject4).size() > 0)
          {
            localObject5 = (EntitlementGroup)((EntitlementGroups)localObject4).get(0);
            ((EntitlementGroup)localObject5).setGroupName(str4);
            Entitlements.modifyEntitlementGroup(localEntitlementGroupMember1, (EntitlementGroup)localObject5);
          }
        }
        if (i != j)
        {
          localEntitlementGroup.setParentId(j);
          Entitlements.modifyEntitlementGroup(localEntitlementGroupMember1, localEntitlementGroup);
        }
        Object localObject4 = ((com.ffusion.beans.business.Business)localObject2).getTransactionLimits();
        Object localObject5 = getTXLimitsByGroupId(paramSecureUser, paramBusiness2.getEntitlementGroupIdValue(), "per business", paramHashMap);
        if (localObject4 != null)
        {
          TransactionLimits localTransactionLimits = getTXLimitsByGroupId(paramSecureUser, paramBusiness1.getServicesPackageIdValue(), "per business", paramHashMap);
          LimitTypePropertyLists localLimitTypePropertyLists = Entitlements.getLimitTypesWithProperties(new HashMap());
          localObject6 = ((TransactionLimits)localObject4).getLimits(localEntitlementGroup, localLimitTypePropertyLists);
          localObject7 = ((Limits)localObject6).iterator();
          TransactionLimit localTransactionLimit = null;
          while (((Iterator)localObject7).hasNext())
          {
            Limit localLimit = (Limit)((Iterator)localObject7).next();
            if (!jdMethod_for(localLimit, localTransactionLimits)) {
              throw new CSILException(str1, 4017);
            }
            localTransactionLimit = ((TransactionLimits)localObject5).getLimitByNameAndPeriod(localLimit.getLimitName(), localLimit.getPeriod());
            if (localTransactionLimit != null)
            {
              if ((localLimit.getData() == null) || (localLimit.getData().trim().length() == 0))
              {
                Entitlements.deleteGroupLimit(localEntitlementGroupMember1, localTransactionLimit);
                ((HistoryTracker)localObject3).logLimitDelete(localTransactionLimit, localLimitTypePropertyLists);
              }
              else
              {
                Entitlements.modifyGroupLimit(localEntitlementGroupMember1, localLimit);
                if ((localLimit.getData() == null) || (!localLimit.getData().equals(localTransactionLimit.getData()))) {
                  ((HistoryTracker)localObject3).logLimitChange(localLimit, localLimitTypePropertyLists, localTransactionLimit.getData());
                }
              }
            }
            else if ((localLimit.getData() != null) && (localLimit.getData().trim().length() > 0))
            {
              Entitlements.addGroupLimit(localEntitlementGroupMember1, localLimit);
              ((HistoryTracker)localObject3).logLimitAdd(localLimit, localLimitTypePropertyLists);
            }
          }
        }
        int k = paramBusiness1.getStatusValue();
        int m = paramBusiness2.getStatusValue();
        if ((k == 8) && (m != 8)) {
          jdMethod_for(paramSecureUser, paramBusiness1.getIdValue(), paramHashMap);
        }
        PerfLog.log(str1, l, true);
        Object localObject6 = new Object[1];
        localObject6[0] = paramBusiness1.getBusinessName();
        Object localObject7 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_1", (Object[])localObject6);
        audit(paramSecureUser, (ILocalizable)localObject7, paramBusiness1.getId(), str2, 2200);
        if (k != m) {
          if (k == 1)
          {
            localObject7 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_2", (Object[])localObject6);
            audit(paramSecureUser, (ILocalizable)localObject7, paramBusiness1.getId(), str2, 2203);
          }
          else if (k == 2)
          {
            localObject7 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_3", (Object[])localObject6);
            audit(paramSecureUser, (ILocalizable)localObject7, paramBusiness1.getId(), str2, 2202);
          }
          else if (k == 8)
          {
            localObject7 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_20", (Object[])localObject6);
            audit(paramSecureUser, (ILocalizable)localObject7, paramBusiness1.getId(), str2, 2213);
          }
          else if ((k == 16) && (m == 0))
          {
            localObject7 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_21", (Object[])localObject6);
            audit(paramSecureUser, (ILocalizable)localObject7, paramBusiness1.getId(), str2, 2214);
          }
        }
        debug(paramSecureUser, str1);
        paramBusiness1.logChanges((HistoryTracker)localObject3, paramBusiness2, (String)null);
        try
        {
          com.ffusion.csil.handlers.Business.addHistory(paramSecureUser, ((HistoryTracker)localObject3).getHistories(), null);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for Business.modifyBusiness: " + localCSILException.toString());
        }
        return localObject2;
      }
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_2", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static boolean isBusinessRegisteredWithBPW(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return ACH.isBusinessRegisteredWithBPW(paramSecureUser, paramString, paramHashMap);
  }
  
  public static boolean getBusinessRegistrationBPWInfo(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    paramHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(paramHashMap);
    AffiliateBank localAffiliateBank = null;
    if ((paramHashMap != null) && (paramHashMap.get("AffBank" + paramBusiness.getAffiliateBankID()) != null))
    {
      localAffiliateBank = (AffiliateBank)paramHashMap.get("AffBank" + paramBusiness.getAffiliateBankID());
    }
    else
    {
      localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(paramSecureUser, paramBusiness.getAffiliateBankID(), paramHashMap);
      if (paramHashMap != null) {
        paramHashMap.put("AffBank" + paramBusiness.getAffiliateBankID(), localAffiliateBank);
      }
    }
    return com.ffusion.csil.handlers.ACH.getBusinessRegistrationBPWInfo(paramSecureUser, localAffiliateBank.getFIBPWID(), paramBusiness, paramHashMap);
  }
  
  public static void registerBusinessWithBPW(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(paramSecureUser, paramBusiness.getAffiliateBankID(), paramHashMap);
    ACH.addCustomer(paramSecureUser, localAffiliateBank.getFIBPWID(), paramBusiness, paramHashMap);
  }
  
  public static void modifyBusinessRegisteredWithBPW(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    paramHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(paramHashMap);
    AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(paramSecureUser, paramBusiness.getAffiliateBankID(), paramHashMap);
    ACH.modifyCustomer(paramSecureUser, localAffiliateBank.getFIBPWID(), paramBusiness, paramHashMap);
  }
  
  public static void addAdditionalData(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.AddAdditionalData";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Object localObject;
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aDd))
    {
      localObject = new EntitlementAdmin(localEntitlementGroupMember, paramBusiness.getEntitlementGroupIdValue());
      if (Entitlements.canAdminister((EntitlementAdmin)localObject))
      {
        EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        com.ffusion.csil.handlers.Business.addAdditionalData(paramSecureUser, paramBusiness, paramString1, paramString2, paramHashMap);
        PerfLog.log(str1, l, true);
        debug(paramSecureUser, str1);
      }
      else
      {
        throw new CSILException(str1, 20001);
      }
    }
    else
    {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_3", null);
      logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static String getAdditionalData(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetAdditionalData";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    String str2 = null;
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aC4))
    {
      long l = System.currentTimeMillis();
      str2 = com.ffusion.csil.handlers.Business.getAdditionalData(paramSecureUser, paramBusiness, paramString, paramHashMap);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_4", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    return str2;
  }
  
  public static com.ffusion.beans.business.Business addBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.AddBusiness";
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember1, aC3))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      localObject1 = new EntitlementAdmin(localEntitlementGroupMember1, paramBusiness.getServicesPackageIdValue());
      if (Entitlements.canExtend((EntitlementAdmin)localObject1))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        com.ffusion.beans.business.Business localBusiness = com.ffusion.csil.handlers.Business.addBusiness(paramSecureUser, paramBusiness, paramHashMap);
        HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 2, localBusiness.getId());
        EntitlementGroup localEntitlementGroup1 = new EntitlementGroup();
        localEntitlementGroup1.setGroupName(localBusiness.getBusinessName());
        localEntitlementGroup1.setEntGroupType("Business");
        localEntitlementGroup1.setSvcBureauId(localBusiness.getBankId());
        localEntitlementGroup1.setParentId(localBusiness.getServicesPackageIdValue());
        localEntitlementGroup1.setGroupId(localBusiness.getEntitlementGroupIdValue());
        localEntitlementGroup1.setEntGroupType("BusinessAdmin");
        localEntitlementGroup1.setGroupName("Admin_" + localBusiness.getBusinessName());
        localEntitlementGroup1 = Entitlements.addEntitlementGroup(localEntitlementGroupMember1, localEntitlementGroup1);
        int i = localEntitlementGroup1.getGroupId();
        EntitlementAdmin localEntitlementAdmin1 = new EntitlementAdmin(localEntitlementGroupMember1.getEntitlementGroupId(), i);
        EntitlementAdmin localEntitlementAdmin2 = new EntitlementAdmin(localEntitlementGroupMember1.getEntitlementGroupId(), i);
        localEntitlementAdmin2.setAdminister(true);
        localEntitlementAdmin2.setExtend(true);
        Entitlements.modifyAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin1, localEntitlementAdmin2);
        EntitlementAdmin localEntitlementAdmin3 = new EntitlementAdmin(0, i);
        EntitlementGroup localEntitlementGroup2 = null;
        EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByType("EmployeeType");
        Iterator localIterator1 = localEntitlementGroups.iterator();
        while (localIterator1.hasNext())
        {
          localEntitlementGroup2 = (EntitlementGroup)localIterator1.next();
          if (localEntitlementGroupMember1.getEntitlementGroupId() != localEntitlementGroup2.getGroupId())
          {
            localEntitlementAdmin3.setGranteeGroupId(localEntitlementGroup2.getGroupId());
            if (Entitlements.checkEntitlement(localEntitlementGroup2.getGroupId(), aC3))
            {
              localEntitlementAdmin3.setAdminister(true);
              localEntitlementAdmin3.setExtend(true);
              Entitlements.addAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin3);
            }
            else if ((Entitlements.checkEntitlement(localEntitlementGroup2.getGroupId(), aDd)) || (Entitlements.checkEntitlement(localEntitlementGroup2.getGroupId(), aCZ)) || (Entitlements.checkEntitlement(localEntitlementGroup2.getGroupId(), aDf)))
            {
              localEntitlementAdmin3.setAdminister(true);
              localEntitlementAdmin3.setExtend(false);
              Entitlements.addAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin3);
            }
          }
        }
        localBusiness.setEntitlementGroupId(i);
        localBusiness.setEntitlementGroupId(Entitlements.getEntitlementGroup(i).getGroupId());
        localBusiness.setServicesPackageId(Entitlements.getEntitlementGroup(i).getParentId());
        EntitlementGroupMember localEntitlementGroupMember2 = localBusiness.getEntitlementGroupMember();
        Entitlements.addMember(localEntitlementGroupMember1, localEntitlementGroupMember2);
        EntitlementGroup localEntitlementGroup3 = new EntitlementGroup();
        localEntitlementGroup3.setGroupName(localBusiness.getBusinessName());
        localEntitlementGroup3.setEntGroupType("Business");
        localEntitlementGroup3.setSvcBureauId(localBusiness.getBankId());
        localEntitlementGroup3.setParentId(localBusiness.getServicesPackageIdValue());
        localEntitlementGroup3.setGroupId(localBusiness.getEntitlementGroupIdValue());
        localEntitlementGroup3.setParentId(i);
        localEntitlementGroup3 = Entitlements.addEntitlementGroup(localEntitlementGroupMember1, localEntitlementGroup3);
        int j = localEntitlementGroup3.getGroupId();
        EntitlementAdmin localEntitlementAdmin4 = new EntitlementAdmin(j, j);
        localEntitlementAdmin4.setAdminister(true);
        localEntitlementAdmin4.setExtend(true);
        Entitlements.addAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin4);
        localIterator1 = localEntitlementGroups.iterator();
        localEntitlementAdmin3.setTargetGroupId(j);
        while (localIterator1.hasNext())
        {
          localEntitlementGroup2 = (EntitlementGroup)localIterator1.next();
          if ((localEntitlementGroupMember1.getEntitlementGroupId() != localEntitlementGroup2.getGroupId()) && (Entitlements.checkEntitlement(localEntitlementGroup2.getGroupId(), aC3)))
          {
            localEntitlementAdmin3.setGranteeGroupId(localEntitlementGroup2.getGroupId());
            localEntitlementAdmin3.setAdminister(true);
            localEntitlementAdmin3.setExtend(false);
            Entitlements.addAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin3);
          }
        }
        TransactionLimits localTransactionLimits1 = getTXLimitsByGroupId(paramSecureUser, localBusiness.getServicesPackageIdValue(), "per package", paramHashMap);
        TransactionLimits localTransactionLimits2 = localBusiness.getTransactionLimits();
        if (localTransactionLimits2 == null) {
          localTransactionLimits2 = new TransactionLimits(getLimitTypeProps(), "per business", paramSecureUser.getLocale());
        }
        LimitTypePropertyLists localLimitTypePropertyLists = Entitlements.getLimitTypesWithProperties(new HashMap());
        Limits localLimits = localTransactionLimits2.getLimits(localEntitlementGroup1, localLimitTypePropertyLists);
        Iterator localIterator2 = localLimits.iterator();
        while (localIterator2.hasNext())
        {
          localObject2 = (Limit)localIterator2.next();
          if (!jdMethod_for((Limit)localObject2, localTransactionLimits1)) {
            throw new CSILException(str1, 4017);
          }
          if ((((Limit)localObject2).getData() != null) && (((Limit)localObject2).getData().trim().length() > 0))
          {
            Entitlements.addGroupLimit(localEntitlementGroupMember1, (Limit)localObject2);
            localHistoryTracker.logLimitAdd((Limit)localObject2, localLimitTypePropertyLists);
          }
        }
        PerfLog.log(str1, l, true);
        Object localObject2 = new Object[2];
        localObject2[0] = paramBusiness.getBusinessName();
        localObject2[1] = new Integer(paramBusiness.getId());
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_5", (Object[])localObject2);
        audit(paramSecureUser, localLocalizableString, localBusiness.getId(), str2, 2201);
        debug(paramSecureUser, str1);
        localBusiness.logCreation(localHistoryTracker, localHistoryTracker.lookupComment(1));
        try
        {
          com.ffusion.csil.handlers.Business.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for Business.addBusiness: " + localCSILException.toString());
        }
        return localBusiness;
      }
      throw new CSILException(str1, 20001);
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_5", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.business.Business deactivateBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.DeactivateBusiness";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDd))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.beans.business.Business localBusiness = com.ffusion.csil.handlers.Business.deactivateBusiness(paramSecureUser, paramBusiness, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramBusiness.getBusinessName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_3", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 2202);
      debug(paramSecureUser, str1);
      return localBusiness;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_6", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.business.Business reactivateBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.ReactivateBusiness";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDd))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.beans.business.Business localBusiness = com.ffusion.csil.handlers.Business.reactivateBusiness(paramSecureUser, paramBusiness, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramBusiness.getBusinessName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_2", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 2203);
      debug(paramSecureUser, str1);
      return localBusiness;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.business.Business purgeDeactivatedBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.PurgeDeactivatedBusiness";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCF))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.beans.business.Business localBusiness = com.ffusion.csil.handlers.Business.purgeDeactivatedBusiness(paramSecureUser, paramBusiness, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramBusiness.getBusinessName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_6", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 2204);
      debug(paramSecureUser, str1);
      return localBusiness;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_8", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.business.Business getBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    return getBusinessCB(paramSecureUser, paramBusiness, false, paramHashMap);
  }
  
  public static com.ffusion.beans.business.Business getBusinessCB(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetBusiness";
    boolean bool = false;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      com.ffusion.beans.business.Business localBusiness = com.ffusion.csil.handlers.Business.getBusiness(paramSecureUser, paramBusiness, paramHashMap);
      if ((paramSecureUser.getAffiliateIDValue() != 0) && (paramSecureUser.getAffiliateIDValue() != localBusiness.getAffiliateBankID()))
      {
        DebugLog.log(Level.SEVERE, "Business.GetBusiness.  Attempt to access a business that the secure user is not entitled to access.");
        throw new CSILException(str, 104);
      }
      bool = getBusinessRegistrationBPWInfo(paramSecureUser, localBusiness, paramHashMap);
      if (paramBoolean == true) {
        jdMethod_for(paramSecureUser, localBusiness, "Business");
      } else {
        jdMethod_for(paramSecureUser, localBusiness, null);
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusiness;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_9", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Businesses getBusinesses(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetBusinesses";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      Businesses localBusinesses = com.ffusion.csil.handlers.Business.getBusinesses(paramSecureUser, paramHashMap);
      jdMethod_for(paramSecureUser, localBusinesses);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusinesses;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_10", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static com.ffusion.beans.business.Business enrollBusiness(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.EnrollBusiness";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC3))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.beans.business.Business localBusiness = com.ffusion.csil.handlers.Business.enrollBusiness(paramSecureUser, paramBusiness, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramBusiness.getBusinessName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_7", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 2205);
      debug(paramSecureUser, str1);
      return localBusiness;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_11", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Businesses getFilteredBusinesses(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetFilteredBusinesses";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      Businesses localBusinesses = com.ffusion.csil.handlers.Business.getFilteredBusinesses(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
      if (localBusinesses != null)
      {
        Iterator localIterator = localBusinesses.iterator();
        while (localIterator.hasNext())
        {
          com.ffusion.beans.business.Business localBusiness = (com.ffusion.beans.business.Business)localIterator.next();
          try
          {
            getBusinessRegistrationBPWInfo(paramSecureUser, localBusiness, paramHashMap);
          }
          catch (Exception localException)
          {
            debug(localException.toString());
          }
        }
      }
      jdMethod_for(paramSecureUser, localBusinesses);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusinesses;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_12", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static int getFilteredBusinessesCount(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetFilteredBusinessesCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      int i = com.ffusion.csil.handlers.Business.getFilteredBusinessesCount(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return i;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_13", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "Business.getDisplayCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.Business.getDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_14", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getMaxResultCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "Business.getMaxResultCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.Business.getMaxResultCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_15", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MarketSegments getMarketSegments(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return getMarketSegments(paramSecureUser, null, paramHashMap);
  }
  
  public static MarketSegments getMarketSegments(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetMarketSegments";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCJ)))
    {
      long l = System.currentTimeMillis();
      EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByTypeAndSvcBureau("MarketSegment", paramSecureUser.getBankIDValue());
      MarketSegments localMarketSegments = jdMethod_for(localEntitlementGroups);
      if ((paramString != null) && (paramString.length() > 0))
      {
        ListIterator localListIterator = localMarketSegments.listIterator();
        while (localListIterator.hasNext())
        {
          String str2 = ((MarketSegment)localListIterator.next()).getAffiliateBankId();
          if ((str2 != null) && (str2.length() > 0) && (!paramString.equals(str2))) {
            localListIterator.remove();
          }
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localMarketSegments;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_16", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ServicesPackages getServicesPackages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return getServicesPackages(paramSecureUser, null, paramHashMap);
  }
  
  public static ServicesPackages getServicesPackages(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetServiesPackages";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCJ)))
    {
      long l = System.currentTimeMillis();
      EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByTypeAndSvcBureau("ServicesPackage", paramSecureUser.getBankIDValue());
      ServicesPackages localServicesPackages = jdMethod_int(localEntitlementGroups);
      if ((paramString != null) && (paramString.length() > 0))
      {
        ListIterator localListIterator = localServicesPackages.listIterator();
        while (localListIterator.hasNext())
        {
          String str2 = ((ServicesPackage)localListIterator.next()).getAffiliateBankId();
          if ((str2 != null) && (str2.length() > 0) && (!paramString.equals(str2))) {
            localListIterator.remove();
          }
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localServicesPackages;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_17", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MarketSegments getMarketSegmentsByUserType(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return getMarketSegmentsByUserType(paramSecureUser, paramString, null, paramHashMap);
  }
  
  public static MarketSegments getMarketSegmentsByUserType(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetMarketSegmentsByType";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCJ)))
    {
      long l = System.currentTimeMillis();
      EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroupByNameAndSvcBureau(paramString1, "UserType", paramSecureUser.getBankIDValue());
      EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(localEntitlementGroup.getGroupId(), "MarketSegment");
      MarketSegments localMarketSegments = jdMethod_for(localEntitlementGroups);
      if ((paramString2 != null) && (paramString2.length() > 0))
      {
        ListIterator localListIterator = localMarketSegments.listIterator();
        while (localListIterator.hasNext())
        {
          String str2 = ((MarketSegment)localListIterator.next()).getAffiliateBankId();
          if ((str2 != null) && (str2.length() > 0) && (!paramString2.equals(str2))) {
            localListIterator.remove();
          }
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localMarketSegments;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_18", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ServicesPackages getServicesPackagesForMarketSegments(SecureUser paramSecureUser, MarketSegments paramMarketSegments, HashMap paramHashMap)
    throws CSILException
  {
    return getServicesPackagesForMarketSegments(paramSecureUser, paramMarketSegments, null, paramHashMap);
  }
  
  public static ServicesPackages getServicesPackagesForMarketSegments(SecureUser paramSecureUser, MarketSegments paramMarketSegments, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetServicesPackagesForMarketSegments";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCJ)))
    {
      long l = System.currentTimeMillis();
      EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByTypeAndSvcBureau("ServicesPackage", paramSecureUser.getBankIDValue());
      Object localObject;
      for (int i = localEntitlementGroups.size() - 1; i >= 0; i--)
      {
        localObject = (EntitlementGroup)localEntitlementGroups.get(i);
        if (paramMarketSegments.getById(((EntitlementGroup)localObject).getParentId()) == null) {
          localEntitlementGroups.remove(i);
        }
      }
      ServicesPackages localServicesPackages = jdMethod_int(localEntitlementGroups);
      if ((paramString != null) && (paramString.length() > 0))
      {
        localObject = localServicesPackages.listIterator();
        while (((ListIterator)localObject).hasNext())
        {
          String str2 = ((ServicesPackage)((ListIterator)localObject).next()).getAffiliateBankId();
          if ((str2 != null) && (str2.length() > 0) && (!paramString.equals(str2))) {
            ((ListIterator)localObject).remove();
          }
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localServicesPackages;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_19", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MarketSegment addMarketSegment(SecureUser paramSecureUser, MarketSegment paramMarketSegment, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.AddMarketSegment";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aCn))
    {
      localObject = new EntitlementAdmin(localEntitlementGroupMember, paramInt);
      if (Entitlements.canExtend((EntitlementAdmin)localObject))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        EntitlementGroup localEntitlementGroup1 = paramMarketSegment.getEntitlementGroup();
        localEntitlementGroup1.setParentId(paramInt);
        localEntitlementGroup1 = Entitlements.addEntitlementGroup(localEntitlementGroupMember, localEntitlementGroup1);
        int i = localEntitlementGroup1.getGroupId();
        EntitlementAdmin localEntitlementAdmin1 = new EntitlementAdmin(localEntitlementGroupMember.getEntitlementGroupId(), i);
        EntitlementAdmin localEntitlementAdmin2 = new EntitlementAdmin(localEntitlementGroupMember.getEntitlementGroupId(), i);
        localEntitlementAdmin2.setAdminister(true);
        localEntitlementAdmin2.setExtend(true);
        Entitlements.modifyAdministratorGroup(localEntitlementGroupMember, localEntitlementAdmin1, localEntitlementAdmin2);
        paramMarketSegment.setId(i);
        EntitlementAdmin localEntitlementAdmin3 = new EntitlementAdmin(0, i);
        localEntitlementAdmin3.setAdminister(true);
        localEntitlementAdmin3.setExtend(true);
        EntitlementGroup localEntitlementGroup2 = null;
        EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByType("EmployeeType");
        Iterator localIterator = localEntitlementGroups.iterator();
        while (localIterator.hasNext())
        {
          localEntitlementGroup2 = (EntitlementGroup)localIterator.next();
          if ((localEntitlementGroupMember.getEntitlementGroupId() != localEntitlementGroup2.getGroupId()) && (Entitlements.checkEntitlement(localEntitlementGroup2.getGroupId(), aCn)))
          {
            localEntitlementAdmin3.setGranteeGroupId(localEntitlementGroup2.getGroupId());
            Entitlements.addAdministratorGroup(localEntitlementGroupMember, localEntitlementAdmin3);
          }
        }
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramMarketSegment.getMarketSegmentName();
        arrayOfObject[1] = new Integer(paramMarketSegment.getId());
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_8", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 2206);
        debug(paramSecureUser, str1);
        HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 3, paramMarketSegment.getId());
        paramMarketSegment.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
        try
        {
          com.ffusion.csil.handlers.Business.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for Business.addMarketSegment: " + localCSILException.toString());
        }
        return paramMarketSegment;
      }
      throw new CSILException(str1, 20001);
    }
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_20", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MarketSegment modifyMarketSegment(SecureUser paramSecureUser, MarketSegment paramMarketSegment1, MarketSegment paramMarketSegment2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.ModifyMarketSegment";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aCn))
    {
      localObject = new EntitlementAdmin(localEntitlementGroupMember, paramMarketSegment1.getIdValue());
      if (Entitlements.canAdminister((EntitlementAdmin)localObject))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        EntitlementGroup localEntitlementGroup = paramMarketSegment1.getEntitlementGroup();
        int i = l(localEntitlementGroup.getGroupId());
        localEntitlementGroup.setParentId(i);
        Entitlements.modifyEntitlementGroup(localEntitlementGroupMember, localEntitlementGroup);
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramMarketSegment1.getMarketSegmentName();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_9", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 2207);
        debug(paramSecureUser, str1);
        HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 3, paramMarketSegment1.getId());
        paramMarketSegment1.logChanges(localHistoryTracker, paramMarketSegment2, localHistoryTracker.buildLocalizableComment(17));
        try
        {
          com.ffusion.csil.handlers.Business.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for Business.modifyMarketSegment: " + localCSILException.toString());
        }
        return paramMarketSegment1;
      }
      throw new CSILException(str1, 20001);
    }
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_21", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Businesses getBusinessesByMarketSegment(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetBusinessesByMarketSegment";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      Businesses localBusinesses1 = new Businesses();
      EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(paramInt, "ServicesPackage");
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
        Businesses localBusinesses2 = getBusinessesByServicesPackage(paramSecureUser, localEntitlementGroup.getGroupId(), paramHashMap);
        localBusinesses1.addAll(localBusinesses2);
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusinesses1;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getBusinessNamesByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetBusinessesNamesByServicesPackage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = new ArrayList();
      EntitlementGroups localEntitlementGroups1 = Entitlements.getChildrenByGroupType(paramInt, "BusinessAdmin");
      Iterator localIterator = localEntitlementGroups1.iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup1 = (EntitlementGroup)localIterator.next();
        try
        {
          String str2 = null;
          if (paramSecureUser.getUserType() == 2)
          {
            str2 = localEntitlementGroup1.getGroupName();
            int i = str2.indexOf("Admin_");
            if (i == -1) {
              throw new CSILException(str1, 26001);
            }
            str2 = str2.substring(i + "Admin_".length());
          }
          else if ((paramSecureUser.getUserType() == 1) || (paramSecureUser.getUserType() == 3))
          {
            EntitlementGroups localEntitlementGroups2 = Entitlements.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "Business");
            EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localEntitlementGroups2.get(0);
            str2 = localEntitlementGroup2.getGroupName();
          }
          if (str2 != null) {
            localArrayList.add(str2);
          }
        }
        catch (CSILException localCSILException) {}catch (Exception localException)
        {
          DebugLog.log("WARNING: Exception thrown from getBusinessesByServicesPackage" + localException.getMessage());
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localArrayList;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_23", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Businesses getBusinessesByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    return getBusinessesByServicesPackage(paramSecureUser, paramInt, 0, paramHashMap);
  }
  
  public static Businesses getBusinessesByServicesPackage(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetBusinessesByServicesPackage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      Businesses localBusinesses = new Businesses();
      EntitlementGroups localEntitlementGroups1 = Entitlements.getChildrenByGroupType(paramInt1, "BusinessAdmin");
      Iterator localIterator = localEntitlementGroups1.iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup1 = (EntitlementGroup)localIterator.next();
        try
        {
          com.ffusion.beans.business.Business localBusiness = null;
          if (paramSecureUser.getUserType() == 2)
          {
            localBusiness = jdMethod_for(paramSecureUser, localEntitlementGroup1, paramInt2, paramHashMap);
          }
          else if ((paramSecureUser.getUserType() == 1) || (paramSecureUser.getUserType() == 3))
          {
            EntitlementGroups localEntitlementGroups2 = Entitlements.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "Business");
            EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localEntitlementGroups2.get(0);
            localBusiness = jdMethod_int(paramSecureUser, localEntitlementGroup2, paramInt2, paramHashMap);
          }
          if (localBusiness != null) {
            localBusinesses.add(localBusiness);
          }
        }
        catch (CSILException localCSILException) {}catch (Exception localException)
        {
          DebugLog.log("WARNING: Exception thrown from getBusinessesByServicesPackage" + localException.getMessage());
        }
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusinesses;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_24", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetBankIdsByServicesPackage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = null;
      localArrayList = com.ffusion.csil.handlers.Business.getBankIdsByServicesPackage(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localArrayList;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_25", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void deleteMarketSegment(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.DeleteMarketSegment";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Object localObject;
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aCn))
    {
      localObject = new EntitlementAdmin(localEntitlementGroupMember, paramInt);
      if (Entitlements.canAdminister((EntitlementAdmin)localObject))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt);
        Entitlements.deleteEntitlementGroup(localEntitlementGroupMember, localEntitlementGroup);
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localEntitlementGroup.getGroupName();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_10", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 2208);
        debug(paramSecureUser, str1);
      }
      else
      {
        throw new CSILException(str1, 20001);
      }
    }
    else
    {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_26", null);
      logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static ServiceFeatures getServiceFeatures(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetServiceFeatures";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCJ))
    {
      long l = System.currentTimeMillis();
      EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByTypeAndSvcBureau("MarketSegment", paramSecureUser.getBankIDValue());
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localObject = (EntitlementGroup)localIterator.next();
        localEntitlements.addAll(Entitlements.getCumulativeEntitlements(((EntitlementGroup)localObject).getGroupId()));
      }
      Object localObject = Entitlements.getEntitlementTypesWithProperties(new HashMap());
      ServiceFeatures localServiceFeatures = jdMethod_for(localEntitlements, (EntitlementTypePropertyLists)localObject, paramSecureUser.getLocale());
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localServiceFeatures;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_27", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ServiceFeatures getServiceFeaturesByGroupId(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetServiceFeaturesByGroupId";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCJ))
    {
      long l = System.currentTimeMillis();
      EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt);
      HashMap localHashMap = FeatureListFactory.generateFeatureList();
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = Entitlements.getCumulativeEntitlements(paramInt);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
      Iterator localIterator1 = localEntitlements1.iterator();
      while (localIterator1.hasNext())
      {
        localObject = (Entitlement)localIterator1.next();
        jdMethod_int((Entitlement)localObject, localEntitlements2);
      }
      Object localObject = jdMethod_for(localHashMap, localEntitlements2, paramSecureUser.getLocale());
      String str2 = localEntitlementGroup.getEntGroupType();
      Iterator localIterator2 = ((ServiceFeatures)localObject).iterator();
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
      while (localIterator2.hasNext())
      {
        ServiceFeature localServiceFeature = (ServiceFeature)localIterator2.next();
        String str3 = localServiceFeature.getFeatureName();
        localServiceFeature.setChargable(false);
        for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
          if (localEntitlementTypePropertyList.getOperationName().equals(str3))
          {
            localServiceFeature.setLocale(paramSecureUser.getLocale());
            if (!localEntitlementTypePropertyList.isPropertySet("category", "chargeable")) {
              break;
            }
            localServiceFeature.setChargable(true);
            break;
          }
        }
        if (str2.equals("ServicesPackage") == true)
        {
          localServiceFeature.setLocale(paramSecureUser.getLocale());
          EntitlementGroupProperties localEntitlementGroupProperties = localEntitlementGroup.getProperties();
          localEntitlementGroupProperties.setCurrentProperty(str3);
          localServiceFeature.setSelected("1");
          localServiceFeature.setServiceCharge(localEntitlementGroupProperties.getValueOfCurrentProperty());
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localObject;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_28", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ServiceFeatures getChargeableServiceFeaturesByGroupId(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetServiceFeaturesByGroupId";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCJ)))
    {
      long l = System.currentTimeMillis();
      EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt);
      HashMap localHashMap = FeatureListFactory.createChargeableFeatureList();
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = Entitlements.getCumulativeEntitlements(paramInt);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
      Iterator localIterator1 = localEntitlements1.iterator();
      while (localIterator1.hasNext())
      {
        localObject = (Entitlement)localIterator1.next();
        jdMethod_int((Entitlement)localObject, localEntitlements2);
      }
      Object localObject = jdMethod_int(localHashMap, localEntitlements2, paramSecureUser.getLocale());
      String str2 = localEntitlementGroup.getEntGroupType();
      Iterator localIterator2 = ((ServiceFeatures)localObject).iterator();
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
      while (localIterator2.hasNext())
      {
        ServiceFeature localServiceFeature = (ServiceFeature)localIterator2.next();
        String str3 = localServiceFeature.getFeatureName();
        localServiceFeature.setChargable(false);
        for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
          if (localEntitlementTypePropertyList.getOperationName().equals(str3))
          {
            localServiceFeature.setLocale(paramSecureUser.getLocale());
            if (!localEntitlementTypePropertyList.isPropertySet("category", "chargeable")) {
              break;
            }
            localServiceFeature.setChargable(true);
            break;
          }
        }
        if (str2.equals("ServicesPackage") == true)
        {
          localServiceFeature.setLocale(paramSecureUser.getLocale());
          EntitlementGroupProperties localEntitlementGroupProperties = localEntitlementGroup.getProperties();
          localEntitlementGroupProperties.setCurrentProperty(str3);
          localServiceFeature.setSelected("1");
          localServiceFeature.setServiceCharge(localEntitlementGroupProperties.getValueOfCurrentProperty());
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localObject;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_29", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ServicesPackage addServicesPackage(SecureUser paramSecureUser, ServicesPackage paramServicesPackage, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.AddServicesPackage";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aCn))
    {
      localObject1 = new EntitlementAdmin(localEntitlementGroupMember, paramServicesPackage.getMarketSegmentIdValue());
      if (Entitlements.canExtend((EntitlementAdmin)localObject1))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        EntitlementGroup localEntitlementGroup1 = paramServicesPackage.getEntitlementGroup();
        localEntitlementGroup1 = Entitlements.addEntitlementGroup(localEntitlementGroupMember, localEntitlementGroup1);
        int i = localEntitlementGroup1.getGroupId();
        EntitlementGroup localEntitlementGroup2 = Entitlements.getEntitlementGroup(localEntitlementGroup1.getParentId());
        localEntitlementGroup2 = Entitlements.getEntitlementGroup(localEntitlementGroup2.getParentId());
        boolean bool = localEntitlementGroup2.getGroupName().equals("Business");
        EntitlementAdmin localEntitlementAdmin1 = new EntitlementAdmin(localEntitlementGroupMember.getEntitlementGroupId(), i);
        EntitlementAdmin localEntitlementAdmin2 = new EntitlementAdmin(localEntitlementGroupMember.getEntitlementGroupId(), i);
        localEntitlementAdmin2.setAdminister(true);
        localEntitlementAdmin2.setExtend(true);
        Entitlements.modifyAdministratorGroup(localEntitlementGroupMember, localEntitlementAdmin1, localEntitlementAdmin2);
        paramServicesPackage.setId(i);
        EntitlementAdmin localEntitlementAdmin3 = new EntitlementAdmin(0, i);
        EntitlementGroup localEntitlementGroup3 = null;
        EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByType("EmployeeType");
        Iterator localIterator = localEntitlementGroups.iterator();
        while (localIterator.hasNext())
        {
          localEntitlementGroup3 = (EntitlementGroup)localIterator.next();
          if (localEntitlementGroupMember.getEntitlementGroupId() != localEntitlementGroup3.getGroupId())
          {
            localEntitlementAdmin3.setGranteeGroupId(localEntitlementGroup3.getGroupId());
            if (Entitlements.checkEntitlement(localEntitlementGroup3.getGroupId(), aCn))
            {
              localEntitlementAdmin3.setAdminister(true);
              localEntitlementAdmin3.setExtend(true);
              Entitlements.addAdministratorGroup(localEntitlementGroupMember, localEntitlementAdmin3);
            }
            else if ((Entitlements.checkEntitlement(localEntitlementGroup3.getGroupId(), aC3)) || (Entitlements.checkEntitlement(localEntitlementGroup3.getGroupId(), aDd)))
            {
              localEntitlementAdmin3.setAdminister(false);
              localEntitlementAdmin3.setExtend(true);
              Entitlements.addAdministratorGroup(localEntitlementGroupMember, localEntitlementAdmin3);
            }
          }
        }
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = paramServicesPackage.getRestrictedEntitlements(Entitlements.getEntitlementTypesWithProperties(new HashMap()));
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
        EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
        localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
        if (localEntitlementTypePropertyLists != null)
        {
          localObject2 = new EntitlementTypePropertyLists();
          localObject3 = localEntitlementTypePropertyLists.iterator();
          while (((Iterator)localObject3).hasNext())
          {
            EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject3).next();
            for (int k = 0; k < localEntitlements1.size(); k++) {
              if (localEntitlementTypePropertyList.getOperationName().equals(localEntitlements1.getEntitlement(k).getOperationName())) {
                ((EntitlementTypePropertyLists)localObject2).add(localEntitlementTypePropertyList);
              }
            }
          }
          localObject3 = null;
          localObject3 = HandleParentChildDisplay.buildParentChildMapFromParentList((EntitlementTypePropertyLists)localObject2, null);
          if (localObject3 != null) {
            for (int j = 0; j < ((ArrayList)localObject3).size(); j++)
            {
              localObject4 = (ArrayList)((ArrayList)localObject3).get(j);
              for (int m = 0; m < ((ArrayList)localObject4).size(); m++)
              {
                localObject6 = (String)((ArrayList)localObject4).get(m);
                localObject7 = new Entitlement();
                ((Entitlement)localObject7).setOperationName((String)localObject6);
                if (!localEntitlements2.contains(localObject7)) {
                  localEntitlements2.add(localObject7);
                }
              }
            }
          }
        }
        Entitlements.setRestrictedEntitlements(localEntitlementGroupMember, localEntitlementGroup1.getGroupId(), localEntitlements2);
        Object localObject2 = paramServicesPackage.getTransactionLimits();
        if (localObject2 == null) {
          localObject2 = new TransactionLimits(getLimitTypeProps(), "per package", paramSecureUser.getLocale());
        }
        Object localObject3 = Entitlements.getLimitTypesWithProperties(new HashMap());
        Limits localLimits = ((TransactionLimits)localObject2).getLimits(localEntitlementGroup1, (LimitTypePropertyLists)localObject3);
        Object localObject4 = localLimits.iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject5 = (Limit)((Iterator)localObject4).next();
          if ((((Limit)localObject5).getData() != null) && (((Limit)localObject5).getData().trim().length() > 0))
          {
            if (!bool) {
              ((Limit)localObject5).setRunningTotalType('U');
            }
            Entitlements.addGroupLimit(localEntitlementGroupMember, (Limit)localObject5);
          }
        }
        PerfLog.log(str1, l, true);
        Object localObject5 = new Object[2];
        localObject5[0] = paramServicesPackage.getServicesPackageName();
        localObject5[1] = new Integer(paramServicesPackage.getId());
        Object localObject6 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_11", (Object[])localObject5);
        audit(paramSecureUser, (ILocalizable)localObject6, str2, 2209);
        Object localObject7 = new HistoryTracker(paramSecureUser, 4, paramServicesPackage.getId());
        paramServicesPackage.logCreation((HistoryTracker)localObject7, ((HistoryTracker)localObject7).buildLocalizableComment(1));
        try
        {
          com.ffusion.csil.handlers.Business.addHistory(paramSecureUser, ((HistoryTracker)localObject7).getHistories(), null);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for Business.addServicesPackage: " + localCSILException.toString());
        }
        return paramServicesPackage;
      }
      throw new CSILException(str1, 20001);
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_30", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  private static void jdMethod_for(HistoryTracker paramHistoryTracker, ServiceFeatures paramServiceFeatures1, ServiceFeatures paramServiceFeatures2)
  {
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.beans.history.resources", "SELECTED", null);
    LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.beans.history.resources", "UNSELECTED", null);
    int i = 0;
    int j = paramServiceFeatures2.size();
    while (i < j)
    {
      ServiceFeature localServiceFeature1 = (ServiceFeature)paramServiceFeatures2.get(i);
      if (!localServiceFeature1.getHide())
      {
        ServiceFeature localServiceFeature2 = null;
        if (paramServiceFeatures1 != null)
        {
          if (paramServiceFeatures1.size() > i)
          {
            localServiceFeature2 = (ServiceFeature)paramServiceFeatures1.get(i);
            if (!localServiceFeature1.getFeatureName().equals(localServiceFeature2.getFeatureName())) {
              localServiceFeature2 = null;
            }
          }
          if (localServiceFeature2 == null) {
            localServiceFeature2 = paramServiceFeatures1.getByFeatureName(localServiceFeature1.getFeatureName());
          }
        }
        HashMap localHashMap = null;
        try
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = Entitlements.getEntitlementTypeWithProperties(localServiceFeature1.getFeatureName());
          if (localEntitlementTypePropertyList != null) {
            localHashMap = localEntitlementTypePropertyList.getPropertiesMap();
          } else {
            localHashMap = new HashMap();
          }
        }
        catch (CSILException localCSILException)
        {
          localHashMap = new HashMap();
        }
        LocalizableProperty localLocalizableProperty = new LocalizableProperty("display name", localHashMap, localServiceFeature1.getFeatureName());
        if (localServiceFeature2 == null)
        {
          if (localServiceFeature1.isSelected())
          {
            paramHistoryTracker.logChange(ServiceFeature.BEAN_NAME, "SERVICE_FEATURE", localLocalizableString2, localLocalizableString1, localLocalizableProperty);
            if (localServiceFeature1.getChargable()) {
              paramHistoryTracker.logChange(ServiceFeature.BEAN_NAME, "SERVICE_CHARGE", null, localServiceFeature1.getServiceCharge(), localLocalizableProperty);
            }
          }
        }
        else
        {
          if (localServiceFeature1.isSelected() != localServiceFeature2.isSelected()) {
            paramHistoryTracker.logChange(ServiceFeature.BEAN_NAME, "SERVICE_FEATURE", localServiceFeature2.isSelected() ? localLocalizableString1 : localLocalizableString2, localServiceFeature1.isSelected() ? localLocalizableString1 : localLocalizableString2, localLocalizableProperty);
          }
          if ((localServiceFeature1.getChargable()) && (!localServiceFeature1.getServiceCharge().equals(localServiceFeature2.getServiceCharge()))) {
            paramHistoryTracker.logChange(ServiceFeature.BEAN_NAME, "SERVICE_CHARGE", localServiceFeature2.getServiceCharge(), localServiceFeature1.getServiceCharge(), localLocalizableProperty);
          }
        }
      }
      i++;
    }
  }
  
  public static ServicesPackage modifyServicesPackage(SecureUser paramSecureUser, ServicesPackage paramServicesPackage1, ServicesPackage paramServicesPackage2, HashMap paramHashMap)
    throws CSILException
  {
    return modifyServicesPackage(paramSecureUser, paramServicesPackage1, paramServicesPackage2, true, paramHashMap);
  }
  
  public static ServicesPackage modifyServicesPackage(SecureUser paramSecureUser, ServicesPackage paramServicesPackage1, ServicesPackage paramServicesPackage2, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.ModifyServicesPackage";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aCn))
    {
      localObject1 = new EntitlementAdmin(localEntitlementGroupMember, paramServicesPackage1.getIdValue());
      if (Entitlements.canAdminister((EntitlementAdmin)localObject1))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        EntitlementGroup localEntitlementGroup1 = paramServicesPackage1.getEntitlementGroup();
        Entitlements.modifyEntitlementGroup(localEntitlementGroupMember, localEntitlementGroup1);
        EntitlementGroup localEntitlementGroup2 = Entitlements.getEntitlementGroup(localEntitlementGroup1.getParentId());
        localEntitlementGroup2 = Entitlements.getEntitlementGroup(localEntitlementGroup2.getParentId());
        boolean bool = localEntitlementGroup2.getGroupName().equals("Business");
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = paramServicesPackage1.getRestrictedEntitlements(Entitlements.getEntitlementTypesWithProperties(new HashMap()));
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
        EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
        localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
        if (localEntitlementTypePropertyLists != null)
        {
          localObject2 = new EntitlementTypePropertyLists();
          localObject3 = localEntitlementTypePropertyLists.iterator();
          while (((Iterator)localObject3).hasNext())
          {
            EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject3).next();
            for (int j = 0; j < localEntitlements1.size(); j++) {
              if (localEntitlementTypePropertyList.getOperationName().equals(localEntitlements1.getEntitlement(j).getOperationName())) {
                ((EntitlementTypePropertyLists)localObject2).add(localEntitlementTypePropertyList);
              }
            }
          }
          localObject3 = null;
          localObject3 = HandleParentChildDisplay.buildParentChildMapFromParentList((EntitlementTypePropertyLists)localObject2, null);
          if (localObject3 != null) {
            for (int i = 0; i < ((ArrayList)localObject3).size(); i++)
            {
              ArrayList localArrayList = (ArrayList)((ArrayList)localObject3).get(i);
              for (int m = 0; m < localArrayList.size(); m++)
              {
                localObject6 = (String)localArrayList.get(m);
                localObject7 = new Entitlement();
                ((Entitlement)localObject7).setOperationName((String)localObject6);
                if (!localEntitlements2.contains(localObject7)) {
                  localEntitlements2.add(localObject7);
                }
              }
            }
          }
        }
        Entitlements.T();
        try
        {
          Entitlements.setRestrictedEntitlementsUnsafe(localEntitlementGroupMember, localEntitlementGroup1.getGroupId(), localEntitlements2);
        }
        finally
        {
          Entitlements.S();
        }
        Object localObject2 = new com.ffusion.csil.beans.entitlements.Entitlements();
        Object localObject3 = paramServicesPackage1.getServiceFeatures().iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (ServiceFeature)((Iterator)localObject3).next();
          int k = (((ServiceFeature)localObject4).getSelected().equals("1")) && (paramServicesPackage2.getServiceFeatures().getByFeatureName(((ServiceFeature)localObject4).getFeatureName()) == null) ? 1 : 0;
          if (k != 0)
          {
            localObject5 = new Entitlement(((ServiceFeature)localObject4).getFeatureName(), null, null);
            ((com.ffusion.csil.beans.entitlements.Entitlements)localObject2).add(localObject5);
          }
        }
        if ((bool) && (localObject2 != null) && (((com.ffusion.csil.beans.entitlements.Entitlements)localObject2).size() > 0)) {
          AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, localEntitlementGroup1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject2, 5, paramBoolean, paramHashMap);
        }
        Object localObject4 = new HistoryTracker(paramSecureUser, 4, paramServicesPackage1.getId());
        paramServicesPackage1.logChanges((HistoryTracker)localObject4, paramServicesPackage2, ((HistoryTracker)localObject4).buildLocalizableComment(17));
        TransactionLimits localTransactionLimits = paramServicesPackage1.getTransactionLimits();
        Object localObject5 = paramServicesPackage2.getTransactionLimits();
        Object localObject6 = Entitlements.getLimitTypesWithProperties(new HashMap());
        Object localObject7 = localTransactionLimits.getLimits(localEntitlementGroup1, (LimitTypePropertyLists)localObject6);
        Iterator localIterator = ((Limits)localObject7).iterator();
        TransactionLimit localTransactionLimit = null;
        try
        {
          Entitlements.T();
          paramHashMap.put("HaveWriteLock", "yes");
          while (localIterator.hasNext())
          {
            Limit localLimit = (Limit)localIterator.next();
            if (!bool) {
              localLimit.setRunningTotalType('U');
            }
            localTransactionLimit = ((TransactionLimits)localObject5).getLimitByNameAndPeriod(localLimit.getLimitName(), localLimit.getPeriod());
            if (localTransactionLimit != null)
            {
              if (!jdMethod_for(paramSecureUser, localLimit, (TransactionLimits)localObject5, paramHashMap)) {
                throw new CSILException(str1, 4018);
              }
              if ((localLimit.getData() == null) || (localLimit.getData().trim().length() == 0))
              {
                Entitlements.jdMethod_int(localEntitlementGroupMember, localTransactionLimit);
                ((HistoryTracker)localObject4).logLimitDelete(localTransactionLimit, (ArrayList)localObject6);
              }
              else
              {
                Entitlements.jdMethod_new(localEntitlementGroupMember, localLimit);
                if (!localLimit.getData().equals(localTransactionLimit.getData())) {
                  ((HistoryTracker)localObject4).logLimitChange(localLimit, (ArrayList)localObject6, localTransactionLimit.getData());
                }
              }
            }
            else if ((localLimit.getData() != null) && (localLimit.getData().trim().length() > 0))
            {
              Entitlements.jdMethod_for(localEntitlementGroupMember, localLimit);
              ((HistoryTracker)localObject4).logLimitAdd(localLimit, (ArrayList)localObject6);
            }
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace(System.out);
        }
        finally
        {
          Entitlements.S();
          paramHashMap.remove("HaveWriteLock");
        }
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramServicesPackage1.getServicesPackageName();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_12", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 2210);
        debug(paramSecureUser, str1);
        jdMethod_for((HistoryTracker)localObject4, paramServicesPackage2.getServiceFeatures(), paramServicesPackage1.getServiceFeatures());
        jdMethod_for((HistoryTracker)localObject4, paramServicesPackage2.getChargeableFeatures(), paramServicesPackage1.getChargeableFeatures());
        try
        {
          com.ffusion.csil.handlers.Business.addHistory(paramSecureUser, ((HistoryTracker)localObject4).getHistories(), null);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for Business.modifyServicesPackage: " + localCSILException.toString());
        }
        return paramServicesPackage1;
      }
      throw new CSILException(str1, 20001);
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_31", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void deleteServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.DeleteServicesPackage";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Object localObject;
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aCn))
    {
      localObject = new EntitlementAdmin(localEntitlementGroupMember, paramInt);
      if (Entitlements.canAdminister((EntitlementAdmin)localObject))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt);
        HashMap localHashMap;
        if (paramHashMap == null) {
          localHashMap = new HashMap();
        } else {
          localHashMap = paramHashMap;
        }
        if (localHashMap.get("CURRENT_USER") == null) {
          localHashMap.put("CURRENT_USER", paramSecureUser);
        }
        Approvals.removeObject(paramInt, 2, localHashMap);
        localHashMap.remove("CURRENT_USER");
        Entitlements.deleteEntitlementGroup(localEntitlementGroupMember, localEntitlementGroup);
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localEntitlementGroup.getGroupName();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_13", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 2211);
        debug(paramSecureUser, str1);
      }
      else
      {
        throw new CSILException(str1, 20001);
      }
    }
    else
    {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_32", null);
      logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static TransactionLimits getTXLimitsByGroupId(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetTXLimitsByGroupId";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("HaveWriteLock");
    }
    boolean bool;
    if ((str2 != null) && (str2.equals("yes"))) {
      bool = Entitlements.checkEntitlementUnsafe(localEntitlementGroupMember, aC4);
    } else {
      bool = Entitlements.checkEntitlement(localEntitlementGroupMember, aC4);
    }
    if (bool)
    {
      long l = System.currentTimeMillis();
      Limit localLimit = new Limit();
      Limits localLimits = Entitlements.getGroupLimits(localEntitlementGroupMember, paramInt, localLimit, paramHashMap);
      TransactionLimits localTransactionLimits = new TransactionLimits(getLimitTypeProps(), paramString, paramSecureUser.getLocale());
      LimitTypePropertyLists localLimitTypePropertyLists = Entitlements.getLimitTypesWithProperties(new HashMap());
      localTransactionLimits = jdMethod_for(localLimits, localLimitTypePropertyLists, paramString, paramSecureUser.getLocale());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localTransactionLimits;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_33", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  private static MarketSegments jdMethod_for(EntitlementGroups paramEntitlementGroups)
    throws CSILException
  {
    MarketSegments localMarketSegments = new MarketSegments();
    Iterator localIterator = paramEntitlementGroups.iterator();
    while (localIterator.hasNext())
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
      MarketSegment localMarketSegment = new MarketSegment();
      int i = m(localEntitlementGroup.getGroupId());
      localMarketSegment.setEntitlementGroup(localEntitlementGroup);
      localMarketSegment.setServicesAdminGroupId(Integer.toString(i));
      localMarketSegments.add(localMarketSegment);
    }
    return localMarketSegments;
  }
  
  private static ServicesPackages jdMethod_int(EntitlementGroups paramEntitlementGroups)
    throws CSILException
  {
    ServicesPackages localServicesPackages = new ServicesPackages();
    Iterator localIterator = paramEntitlementGroups.iterator();
    while (localIterator.hasNext())
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
      ServicesPackage localServicesPackage = new ServicesPackage();
      int i = m(localEntitlementGroup.getGroupId());
      localServicesPackage.setEntitlementGroup(localEntitlementGroup);
      localServicesPackage.setServicesAdminGroupId(Integer.toString(i));
      localServicesPackages.add(localServicesPackage);
    }
    return localServicesPackages;
  }
  
  private static ServiceFeatures jdMethod_for(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, Locale paramLocale)
    throws CSILException
  {
    Hashtable localHashtable = new Hashtable();
    Iterator localIterator = paramEntitlements.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Entitlement)localIterator.next();
      localObject2 = ((Entitlement)localObject1).getOperationName();
      ServiceFeature localServiceFeature = new ServiceFeature(paramLocale);
      localServiceFeature.setFeatureName((String)localObject2);
      for (int i = 0; i < paramEntitlementTypePropertyLists.size(); i++)
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)paramEntitlementTypePropertyLists.get(i);
        if (localEntitlementTypePropertyList.getOperationName().equals(localObject2))
        {
          localServiceFeature.setDisplayName(localEntitlementTypePropertyList.getPropertyValue("display name", 0));
          localServiceFeature.setFeatureCategory(localEntitlementTypePropertyList.getPropertyValue("component", 0));
          if (localEntitlementTypePropertyList.isPropertySet("hide"))
          {
            String str = localEntitlementTypePropertyList.getPropertyValue("hide", 0);
            if ((str != null) && (str.equalsIgnoreCase("yes"))) {
              localServiceFeature.setHide(true);
            } else {
              localServiceFeature.setHide(false);
            }
          }
          localServiceFeature.setChargable(false);
          if (!localEntitlementTypePropertyList.isPropertySet("category", "chargeable")) {
            break;
          }
          localServiceFeature.setChargable(true);
          break;
        }
      }
      localHashtable.put(localObject2, localServiceFeature);
    }
    Object localObject1 = new ServiceFeatures();
    Object localObject2 = localHashtable.elements();
    while (((Enumeration)localObject2).hasMoreElements()) {
      ((ServiceFeatures)localObject1).add((ServiceFeature)((Enumeration)localObject2).nextElement());
    }
    return localObject1;
  }
  
  private static TransactionLimits jdMethod_for(Limits paramLimits, LimitTypePropertyLists paramLimitTypePropertyLists, String paramString, Locale paramLocale)
    throws CSILException
  {
    Hashtable localHashtable = new Hashtable();
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Limit)localIterator.next();
      localObject2 = ((Limit)localObject1).getLimitName();
      LimitTypePropertyList localLimitTypePropertyList = paramLimitTypePropertyLists.getByOperationName((String)localObject2);
      if ((localLimitTypePropertyList != null) && (localLimitTypePropertyList.isPropertySet("category", paramString)))
      {
        TransactionLimit localTransactionLimit = new TransactionLimit(paramLocale);
        localTransactionLimit.setLimitName((String)localObject2);
        localTransactionLimit.set((Limit)localObject1);
        String str1 = EntitlementsUtil.getPropertyValue(localLimitTypePropertyList, "display name", paramLocale);
        if (str1 != null) {
          localTransactionLimit.setDisplayName(str1);
        } else {
          localTransactionLimit.setDisplayName((String)localObject2);
        }
        localTransactionLimit.setLimitCategory("per business");
        if (localLimitTypePropertyList.isPropertySet("hide"))
        {
          String str2 = localLimitTypePropertyList.getPropertyValue("hide", 0);
          if ((str2 != null) && (str2.equalsIgnoreCase("yes"))) {
            localTransactionLimit.setHide(true);
          } else {
            localTransactionLimit.setHide(false);
          }
        }
        localHashtable.put((String)localObject2 + ((Limit)localObject1).getPeriod(), localTransactionLimit);
      }
    }
    Object localObject1 = new TransactionLimits(getLimitTypeProps(), paramString, paramLocale);
    ((TransactionLimits)localObject1).clear();
    Object localObject2 = localHashtable.elements();
    while (((Enumeration)localObject2).hasMoreElements()) {
      ((TransactionLimits)localObject1).add((TransactionLimit)((Enumeration)localObject2).nextElement());
    }
    return localObject1;
  }
  
  private static int m(int paramInt)
    throws CSILException
  {
    EntitlementGroups localEntitlementGroups = Entitlements.getAdministratorsFor(paramInt);
    if ((localEntitlementGroups != null) && (localEntitlementGroups.size() > 0))
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localEntitlementGroups.get(0);
      return localEntitlementGroup.getGroupId();
    }
    throw new CSILException(4012);
  }
  
  private static int l(int paramInt)
    throws CSILException
  {
    EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt);
    if (localEntitlementGroup != null) {
      return localEntitlementGroup.getParentId();
    }
    throw new CSILException(4014);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, String paramString)
    throws CSILException
  {
    if (paramString == null) {
      if (paramSecureUser.getUserType() == 2) {
        paramString = "BusinessAdmin";
      } else if ((paramSecureUser.getUserType() == 1) || (paramSecureUser.getUserType() == 3)) {
        paramString = "Business";
      }
    }
    EntitlementGroupMember localEntitlementGroupMember = paramBusiness.getEntitlementGroupMember();
    localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
    EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroupMember.getEntitlementGroupId());
    if (localEntitlementGroup != null)
    {
      paramBusiness.setServicesPackageId(localEntitlementGroup.getParentId());
      if (paramString.equals("BusinessAdmin"))
      {
        paramBusiness.setEntitlementGroupId(localEntitlementGroup.getGroupId());
        paramBusiness.setEntitlementGroup(Entitlements.getEntitlementGroup(localEntitlementGroup.getGroupId()));
      }
      else
      {
        EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(localEntitlementGroup.getGroupId(), "Business");
        if (localEntitlementGroups.size() > 0)
        {
          paramBusiness.setEntitlementGroupId(((EntitlementGroup)localEntitlementGroups.get(0)).getGroupId());
          paramBusiness.setEntitlementGroup(Entitlements.getEntitlementGroup(((EntitlementGroup)localEntitlementGroups.get(0)).getGroupId()));
        }
      }
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, Businesses paramBusinesses)
    throws CSILException
  {
    Iterator localIterator = paramBusinesses.iterator();
    while (localIterator.hasNext())
    {
      com.ffusion.beans.business.Business localBusiness = (com.ffusion.beans.business.Business)localIterator.next();
      try
      {
        jdMethod_for(paramSecureUser, localBusiness, null);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log("WARNING: Unable to retrieve EntitlementGroup for Businesswith ID = " + localBusiness.getId());
      }
    }
  }
  
  private static ServiceFeatures jdMethod_for(HashMap paramHashMap, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, Locale paramLocale)
    throws CSILException
  {
    Iterator localIterator = paramEntitlements.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Entitlement)localIterator.next();
      paramHashMap.remove(((Entitlement)localObject).getOperationName());
    }
    Object localObject = paramHashMap.keySet();
    ServiceFeatures localServiceFeatures = new ServiceFeatures();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
    localIterator = null;
    localIterator = ((Set)localObject).iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      ServiceFeature localServiceFeature = new ServiceFeature();
      localServiceFeature.setFeatureName(str1);
      for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
        if (localEntitlementTypePropertyList.getOperationName().equals(str1))
        {
          String str2 = EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList, "display name", paramLocale);
          if (str2 != null) {
            localServiceFeature.setDisplayName(str2);
          } else {
            localServiceFeature.setDisplayName(str1);
          }
          localServiceFeature.setFeatureCategory(EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList, "component", paramLocale));
          if (!localEntitlementTypePropertyList.isPropertySet("hide")) {
            break;
          }
          String str3 = localEntitlementTypePropertyList.getPropertyValue("hide", 0);
          if ((str3 != null) && (str3.equalsIgnoreCase("yes"))) {
            localServiceFeature.setHide(true);
          } else {
            localServiceFeature.setHide(false);
          }
          break;
        }
      }
      localServiceFeatures.add(localServiceFeature);
    }
    return localServiceFeatures;
  }
  
  private static ServiceFeatures jdMethod_int(HashMap paramHashMap, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, Locale paramLocale)
    throws CSILException
  {
    Iterator localIterator = paramEntitlements.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Entitlement)localIterator.next();
      if (((Entitlement)localObject).getOperationName().equals("Wires"))
      {
        paramHashMap.remove("Wires Release");
        paramHashMap.remove("Wire Templates Create");
        paramHashMap.remove("Wire Domestic");
        paramHashMap.remove("Wire International");
        paramHashMap.remove("Wire Host");
        paramHashMap.remove("Wire Drawdown");
        paramHashMap.remove("Wire FED");
        paramHashMap.remove("Wire Book");
        paramHashMap.remove("Wire Domestic");
      }
      paramHashMap.remove(((Entitlement)localObject).getOperationName());
    }
    Object localObject = paramHashMap.keySet();
    ServiceFeatures localServiceFeatures = new ServiceFeatures();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
    localIterator = null;
    localIterator = ((Set)localObject).iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      ServiceFeature localServiceFeature = new ServiceFeature();
      localServiceFeature.setFeatureName(str1);
      for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
        if (localEntitlementTypePropertyList.getOperationName().equals(str1))
        {
          String str2 = EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList, "display name", paramLocale);
          if (str2 != null) {
            localServiceFeature.setDisplayName(str2);
          } else {
            localServiceFeature.setDisplayName(str1);
          }
          localServiceFeature.setFeatureCategory(EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList, "component", paramLocale));
          if (!localEntitlementTypePropertyList.isPropertySet("hide")) {
            break;
          }
          String str3 = localEntitlementTypePropertyList.getPropertyValue("hide", 0);
          if ((str3 != null) && (str3.equalsIgnoreCase("yes"))) {
            localServiceFeature.setHide(true);
          } else {
            localServiceFeature.setHide(false);
          }
          break;
        }
      }
      localServiceFeatures.add(localServiceFeature);
    }
    return localServiceFeatures;
  }
  
  private static void jdMethod_int(Entitlement paramEntitlement, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    if (paramEntitlements.indexOf(paramEntitlement) == -1)
    {
      paramEntitlements.add(paramEntitlement);
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("control parent", paramEntitlement.getOperationName());
      if (localEntitlementTypePropertyLists != null) {
        for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
          Entitlement localEntitlement = new Entitlement(localEntitlementTypePropertyList.getOperationName(), null, null);
          jdMethod_int(localEntitlement, paramEntitlements);
        }
      }
    }
  }
  
  public static void addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.AddHistory";
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Business.addHistory(paramSecureUser, paramHistories, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.GetHistory";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCV))
    {
      long l = System.currentTimeMillis();
      Histories localHistories = com.ffusion.csil.handlers.Business.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localHistories;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_34", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  private static boolean jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, com.ffusion.beans.business.Business paramBusiness1, com.ffusion.beans.business.Business paramBusiness2)
    throws CSILException
  {
    String str1 = paramBusiness1.getStatus();
    String str2 = paramBusiness2.getStatus();
    boolean bool = true;
    if ((!str1.equals(str2)) && ((str1.equals(String.valueOf(1)) == true) || (str1.equals(String.valueOf(16)) == true))) {
      bool = Entitlements.checkEntitlement(paramEntitlementGroupMember, aCv);
    }
    return bool;
  }
  
  private static boolean jdMethod_int(EntitlementGroupMember paramEntitlementGroupMember, com.ffusion.beans.business.Business paramBusiness1, com.ffusion.beans.business.Business paramBusiness2)
    throws CSILException
  {
    boolean bool = true;
    int i = paramBusiness1.getStatusValue();
    if (i == 8) {
      bool = Entitlements.checkEntitlement(paramEntitlementGroupMember, aCF);
    }
    return bool;
  }
  
  private static boolean jdMethod_for(Limit paramLimit, TransactionLimits paramTransactionLimits)
  {
    boolean bool = true;
    if ((paramLimit.getData() == null) || (paramLimit.getData().trim().length() == 0)) {
      return bool;
    }
    BigDecimal localBigDecimal1 = paramLimit.getAmount();
    TransactionLimit localTransactionLimit = paramTransactionLimits.getLimitByNameAndPeriod(paramLimit.getLimitName(), paramLimit.getPeriod());
    if ((localTransactionLimit == null) || (localTransactionLimit.getData() == null) || (localTransactionLimit.getData().trim().length() == 0)) {
      return bool;
    }
    BigDecimal localBigDecimal2 = localTransactionLimit.getAmount();
    if (localBigDecimal1.compareTo(localBigDecimal2) > 0) {
      bool = false;
    }
    return bool;
  }
  
  private static boolean jdMethod_for(SecureUser paramSecureUser, Limit paramLimit, TransactionLimits paramTransactionLimits, HashMap paramHashMap)
  {
    boolean bool = true;
    try
    {
      int i = paramLimit.getGroupId();
      String str = paramLimit.getLimitName();
      int j = paramLimit.getPeriod();
      if ((paramLimit.getData() == null) || (paramLimit.getData().trim().length() == 0)) {
        return bool;
      }
      BigDecimal localBigDecimal1 = paramLimit.getAmount();
      TransactionLimit localTransactionLimit1 = paramTransactionLimits.getLimitByNameAndPeriod(str, j);
      BigDecimal localBigDecimal2 = null;
      if (localTransactionLimit1 != null) {
        localBigDecimal2 = localTransactionLimit1.getAmount();
      } else {
        localBigDecimal2 = new BigDecimal(0.0D);
      }
      if (localBigDecimal1.compareTo(localBigDecimal2) < 0)
      {
        EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(i, "BusinessAdmin");
        Iterator localIterator = localEntitlementGroups.iterator();
        while (localIterator.hasNext())
        {
          EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
          TransactionLimits localTransactionLimits = getTXLimitsByGroupId(paramSecureUser, localEntitlementGroup.getGroupId(), "per package", paramHashMap);
          TransactionLimit localTransactionLimit2 = localTransactionLimits.getLimitByNameAndPeriod(str, j);
          BigDecimal localBigDecimal3 = null;
          if (localTransactionLimit2 != null) {
            localBigDecimal3 = localTransactionLimit2.getAmount();
          } else {
            localBigDecimal3 = new BigDecimal(0.0D);
          }
          if (localBigDecimal3.compareTo(localBigDecimal1) > 0)
          {
            bool = false;
            break;
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("Exception thrown when checking new values of Package TransactionLimits!");
      bool = false;
    }
    return bool;
  }
  
  private static com.ffusion.beans.business.Business jdMethod_for(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetBusinessBC";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      String str2 = paramEntitlementGroup.getGroupName();
      int i = str2.indexOf("Admin_");
      if (i == -1) {
        throw new CSILException(str1, 26001);
      }
      str2 = str2.substring(i + "Admin_".length());
      com.ffusion.beans.business.Business localBusiness = jdMethod_for(paramSecureUser, str2, paramInt, paramHashMap);
      if (localBusiness != null)
      {
        localBusiness.setEntitlementGroupId(paramEntitlementGroup.getGroupId());
        localBusiness.setServicesPackageId(paramEntitlementGroup.getParentId());
        localBusiness.setEntitlementGroup(paramEntitlementGroup);
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localBusiness;
    }
    throw new CSILException(str1, 20001);
  }
  
  private static com.ffusion.beans.business.Business jdMethod_int(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.GetBusinessCB";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aC4))
    {
      long l = System.currentTimeMillis();
      String str2 = paramEntitlementGroup.getGroupName();
      com.ffusion.beans.business.Business localBusiness = jdMethod_for(paramSecureUser, str2, paramInt, paramHashMap);
      if (localBusiness != null)
      {
        localBusiness.setEntitlementGroupId(paramEntitlementGroup.getGroupId());
        localBusiness.setServicesPackageId(l(paramEntitlementGroup.getParentId()));
        localBusiness.setEntitlementGroup(paramEntitlementGroup);
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localBusiness;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_35", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  static com.ffusion.beans.business.Business jdMethod_int(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setBankId(paramSecureUser.getBankID());
    localBusiness1.setId(paramInt);
    com.ffusion.beans.business.Business localBusiness2 = com.ffusion.csil.handlers.Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if (localBusiness2 != null) {
      jdMethod_for(paramSecureUser, localBusiness2, null);
    }
    return localBusiness2;
  }
  
  private static com.ffusion.beans.business.Business jdMethod_for(SecureUser paramSecureUser, String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.getBusinessByName";
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setBankId(paramSecureUser.getBankID());
    localBusiness1.setBusinessName(paramString);
    if (paramInt != 0) {
      localBusiness1.setAffiliateBankID(paramInt);
    }
    Businesses localBusinesses = com.ffusion.csil.handlers.Business.getFilteredBusinesses(paramSecureUser, localBusiness1, null, paramHashMap);
    com.ffusion.beans.business.Business localBusiness2 = null;
    if (localBusinesses.size() > 0) {
      localBusiness2 = (com.ffusion.beans.business.Business)localBusinesses.get(0);
    }
    if (localBusiness2 == null) {
      throw new CSILException(str, 4015);
    }
    return localBusiness2;
  }
  
  public static void registerTXLimitsApprovalGroup(SecureUser paramSecureUser, String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.registerTXLimitsApprovalGroup";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCA))
    {
      jdMethod_for(paramSecureUser, paramString, paramInt1, paramInt2, paramHashMap);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_36", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static void updateTXLimitsApprovalGroup(SecureUser paramSecureUser, String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.updateTXLimitsApprovalGroup";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCA))
    {
      jdMethod_int(paramSecureUser, paramString, paramInt1, paramInt2, paramHashMap);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_37", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static void unregisterTXLimitsApprovalGroup(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.unregisterTXLimitsApprovalGroup";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aCA))
    {
      jdMethod_for(paramSecureUser, paramInt, paramHashMap);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditEntFault_38", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.txLimitApprovalGroupRegister";
    long l = System.currentTimeMillis();
    ApprovalsLevel localApprovalsLevel = new ApprovalsLevel(paramSecureUser.getLocale());
    localApprovalsLevel.setLevelType(paramString);
    localApprovalsLevel.setBusinessID(paramInt2);
    localApprovalsLevel.setMinAmount(new Currency("0", paramSecureUser.getLocale()));
    localApprovalsLevel.setMaxAmount(null);
    Approvals.addWorkflowLevel(paramSecureUser, localApprovalsLevel, paramHashMap);
    ApprovalsChainItems localApprovalsChainItems = new ApprovalsChainItems(paramSecureUser.getLocale());
    ApprovalsChainItem localApprovalsChainItem = localApprovalsChainItems.add();
    localApprovalsChainItem.setGroupOrUserID(paramInt1);
    localApprovalsChainItem.setUsingUser(false);
    Approvals.setWorkflowChainItems(paramSecureUser, localApprovalsLevel, localApprovalsChainItems, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.txLimitApprovalGroupUpdate";
    long l = System.currentTimeMillis();
    ApprovalsLevels localApprovalsLevels = Approvals.getWorkflowLevels(paramSecureUser, paramInt2, paramString, paramHashMap);
    if (localApprovalsLevels == null) {
      throw new CSILException(str, 4016);
    }
    ApprovalsLevel localApprovalsLevel = (ApprovalsLevel)localApprovalsLevels.get(0);
    if (localApprovalsLevel == null) {
      throw new CSILException(str, 4016);
    }
    ApprovalsChainItems localApprovalsChainItems = Approvals.getWorkflowChainItems(paramSecureUser, localApprovalsLevel, paramHashMap);
    if (localApprovalsChainItems == null) {
      throw new CSILException(str, 4016);
    }
    if (localApprovalsChainItems.size() != 1) {
      throw new CSILException(str, 4016);
    }
    ApprovalsChainItem localApprovalsChainItem = (ApprovalsChainItem)localApprovalsChainItems.get(0);
    if (localApprovalsChainItem.getGroupOrUserID() != paramInt1)
    {
      localApprovalsChainItem.setGroupOrUserID(paramInt1);
      localApprovalsChainItem.setUsingUser(false);
      Approvals.setWorkflowChainItems(paramSecureUser, localApprovalsLevel, localApprovalsChainItems, paramHashMap);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.txLimitApprovalGroupUnregister";
    long l = System.currentTimeMillis();
    paramHashMap.put("CURRENT_USER", paramSecureUser);
    Approvals.removeBusiness(paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static Businesses getPendingTransactionBusinessList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.getPendingTransactionBusinessList";
    long l = System.currentTimeMillis();
    ApprovalsItems localApprovalsItems = Approvals.getPendingApprovalsForBank(paramSecureUser, paramHashMap);
    Businesses localBusinesses = new Businesses();
    if (localApprovalsItems != null)
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator = localApprovalsItems.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsItem localApprovalsItem = (ApprovalsItem)localIterator.next();
        com.ffusion.beans.business.Business localBusiness1 = jdMethod_for(paramSecureUser, localApprovalsItem);
        if (localBusiness1 != null)
        {
          com.ffusion.beans.business.Business localBusiness2 = (com.ffusion.beans.business.Business)localHashMap.get(localBusiness1.getBusinessName());
          if (localBusiness2 == null) {
            localHashMap.put(localBusiness1.getBusinessName(), localBusiness1);
          }
          localBusiness1 = null;
        }
      }
      localBusinesses.addAll(localHashMap.values());
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localBusinesses;
  }
  
  public static PendingTransactions getPendingTransactionsByBusiness(SecureUser paramSecureUser, boolean paramBoolean, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.getPendingTransactionsByBusiness";
    long l = System.currentTimeMillis();
    Object localObject = null;
    BusinessEmployee localBusinessEmployee = null;
    ApprovalsItems localApprovalsItems = Approvals.getPendingApprovalsForBank(paramSecureUser, paramHashMap);
    PendingTransactions localPendingTransactions = new PendingTransactions();
    if (localApprovalsItems != null)
    {
      Iterator localIterator = localApprovalsItems.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsItem localApprovalsItem = (ApprovalsItem)localIterator.next();
        com.ffusion.beans.business.Business localBusiness = jdMethod_for(paramSecureUser, localApprovalsItem);
        if (localBusiness != null)
        {
          int i = 0;
          if (paramBoolean == true)
          {
            if (localBusiness.getId().compareTo(paramString) == 0) {
              i = 1;
            }
          }
          else if (localBusiness.getBusinessName().compareTo(paramString) == 0) {
            i = 1;
          }
          if (i == 1)
          {
            if (localObject == null)
            {
              localObject = localBusiness;
              localBusinessEmployee = jdMethod_for(paramSecureUser, localBusiness);
            }
            PendingTransaction localPendingTransaction = new PendingTransaction(localApprovalsItem, localObject, localBusinessEmployee);
            localPendingTransactions.add(localPendingTransaction);
          }
          localBusiness = null;
        }
      }
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localPendingTransactions;
  }
  
  private static com.ffusion.beans.business.Business jdMethod_for(SecureUser paramSecureUser, ApprovalsItem paramApprovalsItem)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business(paramSecureUser.getLocale());
    localBusiness.setBankId(paramSecureUser.getBankID());
    BusinessEmployee localBusinessEmployee = new BusinessEmployee(paramSecureUser.getLocale());
    localBusinessEmployee.setId(String.valueOf(paramApprovalsItem.getSubmittingUserID()));
    Businesses localBusinesses = getFilteredBusinesses(paramSecureUser, localBusiness, localBusinessEmployee, null);
    if (localBusinesses.size() != 1) {
      return null;
    }
    return (com.ffusion.beans.business.Business)localBusinesses.get(0);
  }
  
  private static BusinessEmployee jdMethod_for(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness)
    throws CSILException
  {
    BusinessEmployee localBusinessEmployee1 = null;
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business(paramSecureUser.getLocale());
    localBusiness.setBankId(paramBusiness.getBankId());
    localBusiness.setBusinessName(paramBusiness.getBusinessName());
    BusinessEmployee localBusinessEmployee2 = new BusinessEmployee(paramSecureUser.getLocale());
    localBusinessEmployee2.setBankId(paramBusiness.getBankId());
    localBusinessEmployee2.setBusinessId(paramBusiness.getId());
    localBusinessEmployee2.setPrimaryUser("1");
    BusinessEmployees localBusinessEmployees = UserAdmin.getFilteredBusinessEmployees(paramSecureUser, localBusiness, localBusinessEmployee2, null);
    if (localBusinessEmployees.size() > 0) {
      localBusinessEmployee1 = (BusinessEmployee)localBusinessEmployees.get(0);
    }
    return localBusinessEmployee1;
  }
  
  public static void resolvePendingTransaction(SecureUser paramSecureUser, String paramString, PendingTransaction paramPendingTransaction, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.resolvePendingTransaction";
    long l = System.currentTimeMillis();
    ApprovalsDecisions localApprovalsDecisions = new ApprovalsDecisions(paramSecureUser.getLocale());
    ApprovalsDecision localApprovalsDecision = localApprovalsDecisions.add();
    localApprovalsDecision.setApprovalItem(paramPendingTransaction.getApprovalsItem());
    localApprovalsDecision.setDecision(paramString);
    Approvals.submitDecisions(paramSecureUser, localApprovalsDecisions, paramHashMap);
    if (paramBoolean == true)
    {
      Message localMessage = new Message(paramSecureUser.getLocale());
      localMessage.setTo(paramPendingTransaction.getPrimaryContactID());
      localMessage.setToType(String.valueOf(1));
      String str2 = null;
      try
      {
        User localUser = CustomerAdapter.getUserById(Integer.parseInt(paramPendingTransaction.getPrimaryContactID()));
        if (localUser != null) {
          str2 = localUser.getPreferredLanguage();
        }
      }
      catch (ProfileException localProfileException)
      {
        str2 = null;
      }
      Locale localLocale = null;
      Object localObject2;
      if (str2 != null)
      {
        localObject1 = new StringTokenizer(str2, "_");
        localObject2 = ((StringTokenizer)localObject1).nextToken();
        String str3 = ((StringTokenizer)localObject1).nextToken().toUpperCase();
        localLocale = new Locale((String)localObject2, str3);
      }
      else
      {
        localLocale = paramSecureUser.getLocale();
      }
      Object localObject1 = null;
      if ((paramString.compareTo("Approved") == 0) || (paramString.compareTo("Release") == 0)) {
        localObject1 = new LocalizableString("com.ffusion.approvals.resources", "Decision_Approved", null);
      } else if (paramString.compareTo("Rejected") == 0) {
        localObject1 = new LocalizableString("com.ffusion.approvals.resources", "Decision_Rejected", null);
      }
      if (localObject1 != null)
      {
        localObject2 = new Object[4];
        int i = paramPendingTransaction.getApprovalsItem().getItemSubType();
        localObject2[0] = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsType1SubType" + i + "AuditDesc", null);
        localObject2[1] = ((TWTransaction)paramPendingTransaction.getApprovalsItem().getItem()).getTransaction().getApprovalAmount();
        com.ffusion.beans.DateTime localDateTime = paramPendingTransaction.getApprovalsItem().getSubmissionDate();
        try
        {
          com.ffusion.util.beans.DateTime localDateTime1 = new com.ffusion.util.beans.DateTime(localDateTime, localLocale);
          localObject2[2] = localDateTime1.toString();
        }
        catch (Exception localException)
        {
          localObject2[2] = localDateTime.toString();
        }
        localObject2[3] = localObject1;
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.approvals.resources", "BusinessPrimaryContactNotificationMessageBody", (Object[])localObject2);
        localMessage.setMemo((String)localLocalizableString1.localize(localLocale));
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.approvals.resources", "BusinessPrimaryContactNotificationMessageSubject", null);
        localMessage.setSubject((String)localLocalizableString2.localize(localLocale));
        localMessage.setType(9);
        localMessage.setFromType(String.valueOf(0));
        localMessage.setFrom(paramSecureUser.getProfileID());
        Messages.createNewCase(paramSecureUser, paramHashMap, localMessage, "0", true);
      }
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void resolvePendingTransactions(SecureUser paramSecureUser, ApprovalsDecisions paramApprovalsDecisions, PendingTransactions paramPendingTransactions, boolean[] paramArrayOfBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.resolvePendingTransaction";
    long l = System.currentTimeMillis();
    Approvals.submitDecisions(paramSecureUser, paramApprovalsDecisions, paramHashMap);
    ApprovalsItemErrors localApprovalsItemErrors = null;
    if (paramHashMap.get("ApprovalsItemErrors") != null) {
      localApprovalsItemErrors = (ApprovalsItemErrors)paramHashMap.get("ApprovalsItemErrors");
    }
    if (paramArrayOfBoolean != null)
    {
      int i = paramArrayOfBoolean.length;
      for (int j = 0; j < i; j++) {
        if (paramArrayOfBoolean[j] != 0)
        {
          PendingTransaction localPendingTransaction = (PendingTransaction)paramPendingTransactions.get(j);
          ApprovalsDecision localApprovalsDecision = (ApprovalsDecision)paramApprovalsDecisions.get(j);
          String str2 = null;
          try
          {
            User localUser = CustomerAdapter.getUserById(Integer.parseInt(localPendingTransaction.getPrimaryContactID()));
            if (localUser != null) {
              str2 = localUser.getPreferredLanguage();
            }
          }
          catch (ProfileException localProfileException)
          {
            str2 = null;
          }
          Locale localLocale = null;
          Object localObject1;
          String str3;
          Object localObject2;
          if (str2 != null)
          {
            localObject1 = new StringTokenizer(str2, "_");
            str3 = ((StringTokenizer)localObject1).nextToken();
            localObject2 = ((StringTokenizer)localObject1).nextToken().toUpperCase();
            localLocale = new Locale(str3, (String)localObject2);
          }
          else
          {
            localLocale = paramSecureUser.getLocale();
          }
          if (!jdMethod_for(localPendingTransaction, localApprovalsItemErrors))
          {
            localObject1 = new Message(localLocale);
            ((Message)localObject1).setTo(localPendingTransaction.getPrimaryContactID());
            ((Message)localObject1).setToType(String.valueOf(1));
            str3 = localApprovalsDecision.getDecision();
            localObject2 = null;
            if ((str3.compareTo("Approved") == 0) || (str3.compareTo("Release") == 0)) {
              localObject2 = new LocalizableString("com.ffusion.approvals.resources", "Decision_Approved", null);
            } else if (str3.compareTo("Rejected") == 0) {
              localObject2 = new LocalizableString("com.ffusion.approvals.resources", "Decision_Rejected", null);
            }
            if (localObject2 != null)
            {
              Object[] arrayOfObject = new Object[4];
              int k = localApprovalsDecision.getApprovalItem().getItemSubType();
              arrayOfObject[0] = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsType1SubType" + k + "AuditDesc", null);
              arrayOfObject[1] = ((TWTransaction)localApprovalsDecision.getApprovalItem().getItem()).getTransaction().getApprovalAmount();
              com.ffusion.beans.DateTime localDateTime = localApprovalsDecision.getApprovalItem().getSubmissionDate();
              try
              {
                com.ffusion.util.beans.DateTime localDateTime1 = new com.ffusion.util.beans.DateTime(localDateTime, localLocale);
                arrayOfObject[2] = localDateTime1.toString();
              }
              catch (Exception localException)
              {
                arrayOfObject[2] = localDateTime.toString();
              }
              arrayOfObject[3] = localObject2;
              LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.approvals.resources", "BusinessPrimaryContactNotificationMessageBody", arrayOfObject);
              ((Message)localObject1).setMemo((String)localLocalizableString1.localize(localLocale));
              LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.approvals.resources", "BusinessPrimaryContactNotificationMessageSubject", null);
              ((Message)localObject1).setSubject((String)localLocalizableString2.localize(localLocale));
              ((Message)localObject1).setType(9);
              ((Message)localObject1).setFromType(String.valueOf(0));
              ((Message)localObject1).setFrom(paramSecureUser.getProfileID());
              Messages.createNewCase(paramSecureUser, paramHashMap, (Message)localObject1, "0", true);
            }
          }
        }
      }
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  private static boolean jdMethod_for(PendingTransaction paramPendingTransaction, ApprovalsItemErrors paramApprovalsItemErrors)
  {
    boolean bool = false;
    if (paramApprovalsItemErrors != null)
    {
      int i = Integer.parseInt(paramPendingTransaction.getItemID());
      int j = paramApprovalsItemErrors.size();
      for (int k = 0; k < j; k++)
      {
        ApprovalsItemError localApprovalsItemError = (ApprovalsItemError)paramApprovalsItemErrors.get(k);
        if (localApprovalsItemError.getApprovalItem().getItemID() == i)
        {
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
  
  public static String getServicesPackageName(int paramInt)
    throws CSILException
  {
    EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt);
    return localEntitlementGroup.getGroupName();
  }
  
  public static User getBusinessInfoFromBackend(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.getBusinessInfoFromBackend";
    long l = System.currentTimeMillis();
    User localUser = com.ffusion.csil.handlers.Business.getBusinessInfoFromBackend(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUser;
  }
  
  private static boolean jdMethod_for(int paramInt1, int paramInt2)
  {
    try
    {
      EntitlementGroups localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(paramInt2);
      int i = ((EntitlementGroup)localEntitlementGroups.get(0)).getGroupId();
      if (Entitlements.getChildrenEntitlementGroups(i).size() > 0)
      {
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = Entitlements.getCumulativeEntitlements(paramInt1);
        for (int j = 0; j < localEntitlements.size(); j++)
        {
          Entitlement localEntitlement = localEntitlements.getEntitlement(j);
          if (localEntitlement.getOperationName().equals("Division Management")) {
            return false;
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      return false;
    }
    return true;
  }
  
  public static LimitTypePropertyLists getLimitTypeProps()
  {
    LimitTypePropertyLists localLimitTypePropertyLists = null;
    try
    {
      localLimitTypePropertyLists = Entitlements.getLimitTypesWithProperties(new HashMap());
    }
    catch (Exception localException) {}
    return localLimitTypePropertyLists;
  }
  
  public static ArrayList getTransactionGroups(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    return com.ffusion.csil.handlers.Business.getTransactionGroups(paramSecureUser, paramInt, paramHashMap);
  }
  
  public static String getTypeCodesForGroup(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return com.ffusion.csil.handlers.Business.getTypeCodesForGroup(paramSecureUser, paramInt, paramString, paramHashMap);
  }
  
  public static void addTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.addTransationGroup";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramInt);
    com.ffusion.beans.business.Business localBusiness2 = getBusinessCB(paramSecureUser, localBusiness1, true, paramHashMap);
    int i = localBusiness2.getEntitlementGroupIdValue();
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember, i);
    boolean bool = Entitlements.canAdminister(localEntitlementAdmin);
    String str2;
    Object[] arrayOfObject;
    LocalizableString localLocalizableString;
    if (bool)
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      com.ffusion.csil.handlers.Business.addTransactionGroup(paramSecureUser, paramInt, paramString1, paramString2, paramHashMap);
      str2 = TrackingIDGenerator.GetNextID();
      arrayOfObject = new Object[3];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = paramString2;
      arrayOfObject[2] = new Integer(paramInt);
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_14", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, localBusiness2.getId(), str2, 2200);
    }
    else
    {
      str2 = TrackingIDGenerator.GetNextID();
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = new Integer(paramInt);
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_17", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, localBusiness2.getId(), str2, 2200);
      throw new CSILException(str1, 20001, localLocalizableString.localize(Locale.getDefault()).toString());
    }
  }
  
  public static void deleteTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.deleteTransationGroup";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramInt);
    com.ffusion.beans.business.Business localBusiness2 = getBusinessCB(paramSecureUser, localBusiness1, true, paramHashMap);
    int i = localBusiness2.getEntitlementGroupIdValue();
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember, i);
    boolean bool = Entitlements.canAdminister(localEntitlementAdmin);
    String str2;
    Object[] arrayOfObject;
    LocalizableString localLocalizableString;
    if (bool)
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      com.ffusion.csil.handlers.Business.deleteTransactionGroup(paramSecureUser, paramInt, paramString, paramHashMap);
      str2 = TrackingIDGenerator.GetNextID();
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = new Integer(paramInt);
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_15", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, localBusiness2.getId(), str2, 2200);
    }
    else
    {
      str2 = TrackingIDGenerator.GetNextID();
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = new Integer(paramInt);
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_18", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, localBusiness2.getId(), str2, 2200);
      throw new CSILException(str1, 20001, localLocalizableString.localize(Locale.getDefault()).toString());
    }
  }
  
  public static void modifyTransactionGroup(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Business.modifyTransationGroup";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramInt);
    com.ffusion.beans.business.Business localBusiness2 = getBusinessCB(paramSecureUser, localBusiness1, true, paramHashMap);
    int i = localBusiness2.getEntitlementGroupIdValue();
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember, i);
    boolean bool = Entitlements.canAdminister(localEntitlementAdmin);
    String str2;
    Object[] arrayOfObject;
    LocalizableString localLocalizableString;
    if (bool)
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      com.ffusion.csil.handlers.Business.modifyTransactionGroup(paramSecureUser, paramInt, paramString1, paramString2, paramString3, paramHashMap);
      str2 = TrackingIDGenerator.GetNextID();
      arrayOfObject = new Object[4];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = paramString2;
      arrayOfObject[2] = paramString3;
      arrayOfObject[3] = new Integer(paramInt);
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_16", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, localBusiness2.getId(), str2, 2200);
    }
    else
    {
      str2 = TrackingIDGenerator.GetNextID();
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = new Integer(paramInt);
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.business", "AuditMessage_19", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, localBusiness2.getId(), str2, 2200);
      throw new CSILException(str1, 20001, localLocalizableString.localize(Locale.getDefault()).toString());
    }
  }
  
  public static boolean uniqueCustId(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Business.AddBusiness";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    int i = paramBusiness.getIdValue();
    if (((i <= 0) && (Entitlements.checkEntitlement(localEntitlementGroupMember, aC3))) || ((i > 0) && (Entitlements.checkEntitlement(localEntitlementGroupMember, aDd)))) {
      return com.ffusion.csil.handlers.Business.uniqueCustId(paramSecureUser, paramBusiness, paramHashMap);
    }
    StringBuffer localStringBuffer = new StringBuffer();
    throw new CSILException(str, 20001, localStringBuffer.toString());
  }
  
  public static int getProfileID(EntitlementGroup paramEntitlementGroup)
    throws CSILException
  {
    try
    {
      EntitlementGroup localEntitlementGroup = null;
      if (!paramEntitlementGroup.getEntGroupType().equals("BusinessAdmin")) {
        for (localEntitlementGroup = Entitlements.getEntitlementGroupNoCache(paramEntitlementGroup.getParentId()); !localEntitlementGroup.getEntGroupType().equals("BusinessAdmin"); localEntitlementGroup = Entitlements.getEntitlementGroupNoCache(localEntitlementGroup.getParentId())) {}
      }
      localEntitlementGroup = paramEntitlementGroup;
      EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(localEntitlementGroup.getGroupId());
      Iterator localIterator = localEntitlementGroupMembers.iterator();
      EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localIterator.next();
      Integer localInteger = new Integer(localEntitlementGroupMember.getId());
      return localInteger.intValue();
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
  }
  
  public static int getProfileID(int paramInt)
    throws CSILException
  {
    try
    {
      EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt);
      return getProfileID(localEntitlementGroup);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Business
 * JD-Core Version:    0.7.0.1
 */