package com.ffusion.csil.core;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsChainItems;
import com.ffusion.beans.approvals.ApprovalsDecision;
import com.ffusion.beans.approvals.ApprovalsDecisions;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroupMembers;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsHistory;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsItemCounts;
import com.ffusion.beans.approvals.ApprovalsItemError;
import com.ffusion.beans.approvals.ApprovalsItemErrors;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.handlers.ApprovalsHandler;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLog;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class Approvals
  extends Initialize
{
  private static final Entitlement aHW = new Entitlement("Approvals Admin", null, null);
  private static final String aH9 = "com.ffusion.util.logging.audit.approvals";
  private static final String aHI = "AuditMessage_1";
  private static final String aHx = "AuditMessage_2";
  private static final String aHw = "AuditMessage_3";
  private static final String aHA = "AuditMessage_4";
  private static final String aHG = "AuditMessage_5";
  private static final String aHC = "AuditMessage_6";
  private static final String aHS = "AuditMessage_7";
  private static final String aH8 = "AuditMessage_101";
  private static final String aH6 = "AuditMessage_102";
  private static final String aHU = "AuditMessage_103";
  private static final String aHz = "AuditMessage_104";
  private static final String aHZ = "AuditMessage_105";
  private static final String aHN = "AuditMessage_106";
  private static final String aHY = "AuditMessage_107";
  private static final String aHP = "AuditMessage_108";
  private static final String aH4 = "AuditMessage_109";
  private static final String aH2 = "AuditMessage_110";
  private static final String aHH = "AuditMessage_111";
  private static final String aH7 = "AuditMessage_112";
  private static final String aHM = "AuditMessage_113";
  private static final String aH5 = "AuditMessage_114";
  private static final String aHy = "AuditMessage_115";
  private static final String aHB = "AuditMessage_116";
  private static final String aHD = "AuditType_0";
  private static final String aH0 = "AuditType_1";
  private static final String aHX = "AuditType_3";
  private static final String aHQ = "AuditType_5";
  private static final String aHJ = "AuditType_7";
  private static final String aH3 = "AuditType_9";
  private static final String aHL = "AuditType_10";
  private static final String aHK = "AuditType_11";
  private static final String aH1 = "AuditType_13";
  private static final String aHT = "AuditDecision_1";
  private static final String aHV = "AuditDecision_2";
  private static final String aHF = "AuditDecision_3";
  private static final String aHE = "AuditDecision_4";
  private static final String aHO = "AuditEntFault_1";
  private static final String aHR = "AuditList_1";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.initialize";
    debug(str);
    try
    {
      ApprovalsHandler.initialize(paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      CSILException localCSILException = null;
      if ((localThrowable instanceof CSILException)) {
        localCSILException = (CSILException)localThrowable;
      } else if ((localThrowable instanceof ClassCastException)) {
        localCSILException = new CSILException(-1007, (ClassCastException)localThrowable);
      } else if ((localThrowable instanceof Exception)) {
        localCSILException = new CSILException(-1007, (Exception)localThrowable);
      } else {
        localCSILException = new CSILException(-1007, new Exception(localThrowable.toString()));
      }
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return getWorkflowLevels(paramSecureUser, null, paramHashMap);
  }
  
  public static ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getWorkflowLevels_1";
    debug(paramSecureUser, str);
    jdMethod_case(paramSecureUser, str);
    if (paramSecureUser.getUserType() != 1)
    {
      DebugLog.log("The given user is not a business employee. The call to " + str + " has failed for this reason.");
      throw new CSILException(30207);
    }
    ApprovalsLevels localApprovalsLevels = null;
    try
    {
      long l = System.currentTimeMillis();
      int i = paramSecureUser.getBusinessID();
      localApprovalsLevels = ApprovalsHandler.getWorkflowLevels(paramSecureUser, i, 1, "Business_defined", paramString, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsLevels;
  }
  
  public static ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return getWorkflowLevels(paramSecureUser, paramInt, paramString, null, paramHashMap);
  }
  
  public static ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    return getWorkflowLevels(paramSecureUser, paramInt, 1, paramString1, paramString2, paramHashMap);
  }
  
  public static ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt1, int paramInt2, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getWorkflowLevels_2";
    debug(paramSecureUser, str);
    jdMethod_case(paramSecureUser, str);
    if (paramSecureUser.getUserType() == 1)
    {
      if (paramSecureUser.getBusinessID() != paramInt1)
      {
        DebugLog.log("The user does not belong to the specified business. The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
      if (paramInt2 != 1)
      {
        DebugLog.log("A business user can only obtain levels for the business to which the user belongs. The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
    }
    ApprovalsLevels localApprovalsLevels = null;
    try
    {
      long l = System.currentTimeMillis();
      localApprovalsLevels = ApprovalsHandler.getWorkflowLevels(paramSecureUser, paramInt1, paramInt2, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsLevels;
  }
  
  public static void addWorkflowLevel(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Approvals.addWorkflowLevel";
    debug(paramSecureUser, str1);
    try
    {
      jdMethod_case(paramSecureUser, str1);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if (paramSecureUser.getUserType() == 1)
      {
        if (paramSecureUser.getBusinessID() != paramApprovalsLevel.getBusinessID())
        {
          DebugLog.log("The user does not belong to the specified business for which the approval level is being added. The call to " + str1 + " has failed for this reason.");
          throw new CSILException(30205);
        }
        if (paramApprovalsLevel.getObjectType() != 1)
        {
          DebugLog.log("A business user can only define levels for the business to which the user belongs. The call to " + str1 + " has failed for this reason.");
          throw new CSILException(30205);
        }
      }
      long l = System.currentTimeMillis();
      ApprovalsHandler.addWorkflowLevel(paramSecureUser, paramApprovalsLevel, paramHashMap);
      PerfLog.log(str1, l, true);
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject;
      String str3;
      LocalizableString localLocalizableString;
      if (paramApprovalsLevel.getObjectType() == 1)
      {
        arrayOfObject = new Object[3];
        arrayOfObject[0] = paramApprovalsLevel.getMinAmount();
        arrayOfObject[1] = paramApprovalsLevel.getMaxAmount();
        str3 = Util.getLimitBaseCurrency();
        if (arrayOfObject[0] == null) {
          arrayOfObject[0] = new Currency(new BigDecimal("0"), str3, null);
        }
        if (arrayOfObject[1] == null) {
          arrayOfObject[1] = new Currency(new BigDecimal("1000000000000"), str3, null);
        }
        arrayOfObject[2] = new Integer(paramApprovalsLevel.getLevelID());
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_101", arrayOfObject);
        jdMethod_for(paramSecureUser, localLocalizableString, paramApprovalsLevel.getBusinessID(), 1700);
      }
      else
      {
        arrayOfObject = new Object[4];
        arrayOfObject[0] = new Integer(paramApprovalsLevel.getBusinessID());
        arrayOfObject[1] = paramApprovalsLevel.getMinAmount();
        arrayOfObject[2] = paramApprovalsLevel.getMaxAmount();
        str3 = Util.getLimitBaseCurrency();
        if (arrayOfObject[1] == null) {
          arrayOfObject[1] = new Currency(new BigDecimal("0"), str3, null);
        }
        if (arrayOfObject[2] == null) {
          arrayOfObject[2] = new Currency(new BigDecimal("1000000000000"), str3, null);
        }
        arrayOfObject[3] = new Integer(paramApprovalsLevel.getLevelID());
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_115", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 1700);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void updateWorkflowLevels(SecureUser paramSecureUser, ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.updateWorkflowLevels";
    debug(paramSecureUser, str);
    try
    {
      jdMethod_case(paramSecureUser, str);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      int i = -1;
      int j = -1;
      ApprovalsLevel localApprovalsLevel = null;
      Iterator localIterator = paramApprovalsLevels.iterator();
      while (localIterator.hasNext())
      {
        localApprovalsLevel = (ApprovalsLevel)localIterator.next();
        int k = localApprovalsLevel.getBusinessID();
        int m = localApprovalsLevel.getObjectType();
        if ((k != i) && (i != -1))
        {
          DebugLog.log("The approval levels being updated do not all belong to the same business. The call to " + str + " has failed for this reason.");
          throw new CSILException(30206);
        }
        if ((m != j) && (j != -1))
        {
          DebugLog.log("The approval levels being updated do not all belong to the same object type. The call to " + str + " has failed for this reason.");
          throw new CSILException(30220);
        }
        i = k;
        j = m;
      }
      if (paramSecureUser.getUserType() == 1)
      {
        if (paramSecureUser.getBusinessID() != i)
        {
          DebugLog.log("The user does not belong to the specified business for which the approval levels are being modified. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
        if (j != 1)
        {
          DebugLog.log("A business user can only modify levels for the business to which the user belongs. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
      }
      long l = System.currentTimeMillis();
      ApprovalsHandler.updateWorkflowLevels(paramSecureUser, paramApprovalsLevels, paramHashMap);
      PerfLog.log(str, l, true);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_102", null);
      jdMethod_for(paramSecureUser, localLocalizableString, i, 1701);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void removeWorkflowLevels(SecureUser paramSecureUser, ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.removeWorkflowLevels";
    debug(paramSecureUser, str);
    try
    {
      jdMethod_case(paramSecureUser, str);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      int i = -1;
      int j = -1;
      Iterator localIterator = paramApprovalsLevels.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsLevel localApprovalsLevel = (ApprovalsLevel)localIterator.next();
        int k = localApprovalsLevel.getBusinessID();
        int m = localApprovalsLevel.getObjectType();
        if ((k != i) && (i != -1))
        {
          DebugLog.log("The approval levels being removed do not all belong to the same business. The call to " + str + " has failed for this reason.");
          throw new CSILException(30206);
        }
        if ((m != j) && (j != -1))
        {
          DebugLog.log("The approval levels being updated do not all belong to the same object type. The call to " + str + " has failed for this reason.");
          throw new CSILException(30220);
        }
        i = k;
        j = m;
      }
      if (paramSecureUser.getUserType() == 1)
      {
        if (paramSecureUser.getBusinessID() != i)
        {
          DebugLog.log("The user does not belong to the specified business for which the approval levels are being modified. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
        if (j != 1)
        {
          DebugLog.log("A business user can only delete levels for the business to which the user belongs. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
      }
      long l = System.currentTimeMillis();
      ApprovalsHandler.removeWorkflowLevels(paramSecureUser, paramApprovalsLevels, paramHashMap);
      PerfLog.log(str, l, true);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_103", null);
      jdMethod_for(paramSecureUser, localLocalizableString, i, 1702);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ApprovalsChainItems getWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getWorkflowChainItems";
    debug(paramSecureUser, str);
    ApprovalsChainItems localApprovalsChainItems = null;
    try
    {
      jdMethod_case(paramSecureUser, str);
      if (paramSecureUser.getUserType() == 1)
      {
        if (paramSecureUser.getBusinessID() != paramApprovalsLevel.getBusinessID())
        {
          DebugLog.log("The user does not belong to the business for which the approval chain is being retrieved. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
        if (paramApprovalsLevel.getObjectType() != 1)
        {
          DebugLog.log("A business user can only obtain levels for the business to which the user belongs. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
      }
      long l = System.currentTimeMillis();
      localApprovalsChainItems = ApprovalsHandler.getWorkflowChainItems(paramSecureUser, paramApprovalsLevel, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsChainItems;
  }
  
  public static void setWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, ApprovalsChainItems paramApprovalsChainItems, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.setWorkflowChainItems";
    debug(paramSecureUser, str);
    try
    {
      jdMethod_case(paramSecureUser, str);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      int i = paramApprovalsLevel.getBusinessID();
      if (paramSecureUser.getUserType() == 1)
      {
        if (paramSecureUser.getBusinessID() != paramApprovalsLevel.getBusinessID())
        {
          DebugLog.log("The user does not belong to the business for which the approval chain is being set. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
        if (paramApprovalsLevel.getObjectType() != 1)
        {
          DebugLog.log("A business user can only define levels for the business to which the user belongs. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
      }
      long l = System.currentTimeMillis();
      if (paramSecureUser.getUserType() == 1)
      {
        localObject1 = new User();
        ((User)localObject1).setAccountStatus(User.STATUS_ACTIVE);
        ((User)localObject1).setBankId(paramSecureUser.getBankID());
        ((User)localObject1).set("BUSINESS_ID", String.valueOf(i));
        localObject2 = UserAdmin.getUserList(paramSecureUser, (User)localObject1, paramHashMap);
        paramHashMap.put("Active Users List", localObject2);
      }
      else if (paramSecureUser.getUserType() == 2)
      {
        localObject1 = new BankEmployees(paramSecureUser.getLocale());
        localObject2 = ((BankEmployees)localObject1).create();
        ((BankEmployee)localObject2).setStatus("0");
        com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(paramHashMap);
        BankEmployees localBankEmployees = BankEmployeeAdmin.getBankEmployees(paramSecureUser, (BankEmployee)localObject2, paramHashMap);
        paramHashMap.put("Active Users List", localBankEmployees);
      }
      ApprovalsHandler.setWorkflowChainItems(paramSecureUser, paramApprovalsLevel, paramApprovalsChainItems, paramHashMap);
      PerfLog.log(str, l, true);
      Object localObject1 = new Object[1];
      localObject1[0] = new Integer(paramApprovalsLevel.getLevelID());
      Object localObject2 = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_104", (Object[])localObject1);
      jdMethod_for(paramSecureUser, (ILocalizable)localObject2, paramApprovalsLevel.getBusinessID(), 1703);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    finally
    {
      paramHashMap.remove("Active Users List");
      com.ffusion.util.entitlements.EntitlementsUtil.removeEntitlementBypass(paramHashMap);
    }
  }
  
  public static void clearWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.clearWorkflowChainItems";
    debug(paramSecureUser, str);
    try
    {
      jdMethod_case(paramSecureUser, str);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if (paramSecureUser.getUserType() == 1)
      {
        if (paramSecureUser.getBusinessID() != paramApprovalsLevel.getBusinessID())
        {
          DebugLog.log("The user does not belong to the business for which the approval chain is being cleared. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
        if (paramApprovalsLevel.getObjectType() != 1)
        {
          DebugLog.log("A business user can only delete levels for the business to which the user belongs. The call to " + str + " has failed for this reason.");
          throw new CSILException(30205);
        }
      }
      long l = System.currentTimeMillis();
      ApprovalsHandler.clearWorkflowChainItems(paramSecureUser, paramApprovalsLevel, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new Integer(paramApprovalsLevel.getLevelID());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_105", arrayOfObject);
      jdMethod_for(paramSecureUser, localLocalizableString, paramApprovalsLevel.getBusinessID(), 1704);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ApprovalsStatuses getPendingItems(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "Approvals.getPendingItems";
    debug(str);
    ApprovalsStatuses localApprovalsStatuses = null;
    try
    {
      long l = System.currentTimeMillis();
      localApprovalsStatuses = ApprovalsHandler.getPendingItems(paramInt, paramHashMap1, paramHashMap2);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsStatuses;
  }
  
  public static ApprovalsStatuses getSubmittedItems(int paramInt, String[] paramArrayOfString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "Approvals.getSubmittedItems";
    debug(str);
    ApprovalsStatuses localApprovalsStatuses = null;
    try
    {
      long l = System.currentTimeMillis();
      localApprovalsStatuses = ApprovalsHandler.getSubmittedItems(paramInt, paramArrayOfString, paramHashMap1, paramHashMap2);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsStatuses;
  }
  
  public static int getNumberOfPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return getNumberOfPendingApprovals(paramSecureUser, null, paramHashMap);
  }
  
  public static int getNumberOfPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "Approvals.getNumberOfPendingApprovals";
    debug(paramSecureUser, str);
    int i = 0;
    try
    {
      long l = System.currentTimeMillis();
      i = ApprovalsHandler.getNumberOfPendingApprovals(paramSecureUser, paramHashMap1, paramHashMap2);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return i;
  }
  
  public static ApprovalsItemCounts getNumberOfPendingApprovalsDetail(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getNumberOfPendingApprovalsDetails";
    debug(paramSecureUser, str);
    ApprovalsItemCounts localApprovalsItemCounts = null;
    try
    {
      long l = System.currentTimeMillis();
      localApprovalsItemCounts = ApprovalsHandler.getNumberOfPendingApprovalsDetail(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsItemCounts;
  }
  
  public static ApprovalsItems getPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return getPendingApprovals(paramSecureUser, null, paramHashMap);
  }
  
  public static ApprovalsItems getPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "Approvals.getPendingApprovals";
    debug(paramSecureUser, str);
    ApprovalsItems localApprovalsItems = null;
    try
    {
      long l = System.currentTimeMillis();
      localApprovalsItems = ApprovalsHandler.getPendingApprovals(paramSecureUser, paramHashMap1, paramHashMap2);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsItems;
  }
  
  public static ApprovalsItems getPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getPendingApprovalsForBank";
    debug(paramSecureUser, str);
    ApprovalsItems localApprovalsItems = null;
    try
    {
      if (paramSecureUser.getUserType() != 2)
      {
        DebugLog.log("The given user is not a bank employee. The call to " + str + " has failed for this reason.");
        throw new CSILException(30215);
      }
      long l = System.currentTimeMillis();
      localApprovalsItems = ApprovalsHandler.getPendingApprovalsForBank(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsItems;
  }
  
  public static ApprovalsItems getConsumerPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getConsumerPendingApprovalsForBank";
    debug(paramSecureUser, str);
    ApprovalsItems localApprovalsItems = null;
    try
    {
      if (paramSecureUser.getUserType() != 2)
      {
        DebugLog.log("The given user is not a bank employee. The call to " + str + " has failed for this reason.");
        throw new CSILException(30215);
      }
      long l = System.currentTimeMillis();
      localApprovalsItems = ApprovalsHandler.getConsumerPendingApprovalsForBank(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsItems;
  }
  
  public static ApprovalsItem createApprovalItem(SecureUser paramSecureUser, int paramInt, IApprovable paramIApprovable, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    return createApprovalItem(paramSecureUser, paramInt, paramIApprovable, paramArrayOfString, null, paramHashMap);
  }
  
  public static ApprovalsItem createApprovalItem(SecureUser paramSecureUser, int paramInt, IApprovable paramIApprovable, String[] paramArrayOfString1, String[] paramArrayOfString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.createApprovalItem";
    debug(paramSecureUser, str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    ApprovalsItem localApprovalsItem = null;
    try
    {
      if (paramSecureUser.getUserType() != 1)
      {
        DebugLog.log("The given user is not a business employee. The call to " + str + " has failed for this reason.");
        throw new CSILException(30207);
      }
      long l = System.currentTimeMillis();
      localApprovalsItem = ApprovalsHandler.createApprovalItem(paramSecureUser, paramInt, paramIApprovable, paramArrayOfString1, paramArrayOfString2, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = new Integer(localApprovalsItem.getItemID());
      arrayOfObject[1] = jdMethod_for(localApprovalsItem);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_1", arrayOfObject);
      AuditLogRecord localAuditLogRecord = ((TWTransaction)localApprovalsItem.getItem()).getTransaction().getAuditRecord(paramSecureUser, localLocalizableString, 1705, "PendingApproval");
      AuditLog.log(localAuditLogRecord);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsItem;
  }
  
  public static ApprovalsItem modifyApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.modifyApprovalItem";
    debug(str);
    ApprovalsItem localApprovalsItem = null;
    try
    {
      if (paramApprovalsItem == null) {
        throw new CSILException(30071);
      }
      SecureUser localSecureUser1 = null;
      try
      {
        localSecureUser1 = Util.getSecureUserFromProfile(new SecureUser(), paramApprovalsItem.getSubmittingUserID());
      }
      catch (Exception localException)
      {
        throw new CSILException(30223);
      }
      SecureUser localSecureUser2 = (SecureUser)paramHashMap.get("ApprovalsUser");
      if ((localSecureUser2 == null) && (localSecureUser1.getBusinessID() == 0)) {
        throw new CSILException(30221);
      }
      if ((localSecureUser2 != null) && (localSecureUser1.getBusinessID() == 0) && (!jdMethod_for(localSecureUser2, paramApprovalsItem, paramHashMap))) {
        throw new CSILException(30222);
      }
      long l = System.currentTimeMillis();
      localApprovalsItem = ApprovalsHandler.modifyApprovalItem(paramApprovalsItem, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = new Integer(paramApprovalsItem.getItemID());
      arrayOfObject[1] = jdMethod_for(paramApprovalsItem);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_2", arrayOfObject);
      AuditLogRecord localAuditLogRecord = ((TWTransaction)localApprovalsItem.getItem()).getTransaction().getAuditRecord(new SecureUser(), localLocalizableString, 1712, "PendingApproval");
      localAuditLogRecord.setBusinessID(localSecureUser1.getBusinessID());
      AuditLog.log(localAuditLogRecord);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsItem;
  }
  
  public static void submitDecisions(SecureUser paramSecureUser, ApprovalsDecisions paramApprovalsDecisions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.submitDecisions";
    debug(paramSecureUser, str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    try
    {
      long l = System.currentTimeMillis();
      if (paramHashMap == null)
      {
        DebugLog.log(str + ": no 'extra' HashMap was provided. Error info will not be available to caller.");
        paramHashMap = new HashMap();
      }
      ApprovalsItemErrors localApprovalsItemErrors = new ApprovalsItemErrors(Locale.getDefault());
      paramHashMap.put("ApprovalsItemErrors", localApprovalsItemErrors);
      ApprovalsItems localApprovalsItems = ApprovalsHandler.submitDecisions(paramSecureUser, paramApprovalsDecisions, paramHashMap);
      PerfLog.log(str, l, true);
      localApprovalsItemErrors = (ApprovalsItemErrors)paramHashMap.get("ApprovalsItemErrors");
      HashMap localHashMap = new HashMap(localApprovalsItemErrors.size() * 2 + 1);
      Iterator localIterator = localApprovalsItemErrors.iterator();
      while (localIterator.hasNext())
      {
        localObject1 = (ApprovalsItemError)localIterator.next();
        localHashMap.put(((ApprovalsItemError)localObject1).getApprovalItem(), localObject1);
      }
      Object localObject1 = paramApprovalsDecisions.iterator();
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (ApprovalsDecision)((Iterator)localObject1).next();
        localObject3 = ((ApprovalsDecision)localObject2).getDecision();
        localObject4 = ((ApprovalsDecision)localObject2).getApprovalItem();
        ApprovalsItemError localApprovalsItemError = (ApprovalsItemError)localHashMap.get(localObject4);
        Object localObject7;
        Object localObject8;
        if (localApprovalsItemError == null)
        {
          localObject5 = new Object[3];
          localObject5[0] = U((String)localObject3);
          localObject5[1] = new Integer(((ApprovalsItem)localObject4).getItemID());
          localObject5[2] = jdMethod_for((ApprovalsItem)localObject4);
          localObject6 = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_3", (Object[])localObject5);
          localObject7 = null;
          if ((((String)localObject3).equals("Approved")) && (localApprovalsItems != null) && (localApprovalsItems.contains(localObject4))) {
            localObject7 = "Approved";
          } else {
            localObject7 = W((String)localObject3);
          }
          localObject8 = ((TWTransaction)((ApprovalsItem)localObject4).getItem()).getTransaction().getAuditRecord(paramSecureUser, (ILocalizable)localObject6, V((String)localObject3), (String)localObject7);
          SecureUser localSecureUser1 = null;
          if (paramSecureUser.getUserType() == 2) {
            try
            {
              localSecureUser1 = Util.getSecureUserFromProfile(paramSecureUser, ((ApprovalsItem)localObject4).getSubmittingUserID());
            }
            catch (Exception localException3)
            {
              localSecureUser1 = new SecureUser();
            }
          } else {
            localSecureUser1 = paramSecureUser;
          }
          ((AuditLogRecord)localObject8).setBusinessID(localSecureUser1.getBusinessID());
          AuditLog.log((AuditLogRecord)localObject8);
          if (((String)localObject3).equals("Rejected"))
          {
            SecureUser localSecureUser2 = null;
            try
            {
              localSecureUser2 = Util.getSecureUserFromProfile(paramSecureUser, ((ApprovalsItem)localObject4).getSubmittingUserID());
            }
            catch (Exception localException4)
            {
              localSecureUser2 = new SecureUser();
            }
            localObject5 = new Object[2];
            localObject5[0] = new Integer(((ApprovalsItem)localObject4).getItemID());
            localObject5[1] = jdMethod_for((ApprovalsItem)localObject4);
            localObject6 = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_4", (Object[])localObject5);
            localObject8 = ((TWTransaction)((ApprovalsItem)localObject4).getItem()).getTransaction().getAuditRecord(localSecureUser2, (ILocalizable)localObject6, V((String)localObject3), W((String)localObject3));
            AuditLog.log((AuditLogRecord)localObject8);
          }
        }
        else
        {
          localObject5 = new Object[5];
          localObject5[0] = U((String)localObject3);
          localObject5[1] = new Integer(((ApprovalsItem)localObject4).getItemID());
          localObject5[2] = jdMethod_for((ApprovalsItem)localObject4);
          localObject5[3] = new LocalizableString("com.ffusion.services.errors", "Error" + localApprovalsItemError.getErrorCode() + "_descr", null);
          localObject5[4] = new Integer(localApprovalsItemError.getErrorCode());
          localObject6 = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_5", (Object[])localObject5);
          localObject7 = ((TWTransaction)((ApprovalsItem)localObject4).getItem()).getTransaction().getAuditRecord(paramSecureUser, (ILocalizable)localObject6, V((String)localObject3), W((String)localObject3));
          localObject8 = null;
          if (paramSecureUser.getUserType() == 2) {
            try
            {
              localObject8 = Util.getSecureUserFromProfile(paramSecureUser, ((ApprovalsItem)localObject4).getSubmittingUserID());
            }
            catch (Exception localException2)
            {
              localObject8 = new SecureUser();
            }
          } else {
            localObject8 = paramSecureUser;
          }
          ((AuditLogRecord)localObject7).setBusinessID(((SecureUser)localObject8).getBusinessID());
          AuditLog.log((AuditLogRecord)localObject7);
        }
      }
      if ((localApprovalsItems != null) && (localApprovalsItems.size() > 0))
      {
        localObject2 = localApprovalsItems.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (ApprovalsItem)((Iterator)localObject2).next();
          localObject4 = null;
          try
          {
            localObject4 = Util.getSecureUserFromProfile(paramSecureUser, ((ApprovalsItem)localObject3).getSubmittingUserID());
          }
          catch (Exception localException1)
          {
            localObject4 = new SecureUser();
          }
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = new Integer(((ApprovalsItem)localObject3).getItemID());
          arrayOfObject[1] = jdMethod_for((ApprovalsItem)localObject3);
          localObject5 = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_6", arrayOfObject);
          localObject6 = ((TWTransaction)((ApprovalsItem)localObject3).getItem()).getTransaction().getAuditRecord((SecureUser)localObject4, (ILocalizable)localObject5, V("Approved"), "Approved");
          AuditLog.log((AuditLogRecord)localObject6);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void removeApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.removeApprovalItem";
    debug(str);
    try
    {
      if (paramApprovalsItem == null) {
        throw new CSILException(30071);
      }
      SecureUser localSecureUser1 = null;
      try
      {
        localSecureUser1 = Util.getSecureUserFromProfile(new SecureUser(), paramApprovalsItem.getSubmittingUserID());
      }
      catch (Exception localException)
      {
        throw new CSILException(30223);
      }
      SecureUser localSecureUser2 = (SecureUser)paramHashMap.get("ApprovalsUser");
      if ((localSecureUser2 == null) && (localSecureUser1.getBusinessID() == 0)) {
        throw new CSILException(30221);
      }
      if ((localSecureUser2 != null) && (localSecureUser1.getBusinessID() == 0) && (!jdMethod_for(localSecureUser2, paramApprovalsItem, paramHashMap))) {
        throw new CSILException(30222);
      }
      long l = System.currentTimeMillis();
      ApprovalsHandler.removeApprovalItem(paramApprovalsItem, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = new Integer(paramApprovalsItem.getItemID());
      arrayOfObject[1] = jdMethod_for(paramApprovalsItem);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_7", arrayOfObject);
      AuditLogRecord localAuditLogRecord = ((TWTransaction)paramApprovalsItem.getItem()).getTransaction().getAuditRecord(new SecureUser(), localLocalizableString, 1707, null);
      localAuditLogRecord.setBusinessID(localSecureUser1.getBusinessID());
      AuditLog.log(localAuditLogRecord);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void removeBusiness(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.removeBusiness";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      ApprovalsHandler.removeBusiness(paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_106", null);
      SecureUser localSecureUser;
      if (paramHashMap.get("CURRENT_USER") == null) {
        localSecureUser = new SecureUser();
      } else {
        localSecureUser = (SecureUser)paramHashMap.get("CURRENT_USER");
      }
      jdMethod_for(localSecureUser, localLocalizableString, paramInt, 1708);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void removeObject(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    if (paramInt2 == 1) {
      removeBusiness(paramInt1, paramHashMap);
    }
    String str1 = "Approvals.removeObject";
    debug(str1);
    try
    {
      long l = System.currentTimeMillis();
      ApprovalsHandler.removeObject(paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str1, l, true);
      if (paramInt2 == 2)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = new Integer(paramInt1);
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_116", arrayOfObject);
        SecureUser localSecureUser;
        if (paramHashMap.get("CURRENT_USER") == null) {
          localSecureUser = new SecureUser();
        } else {
          localSecureUser = (SecureUser)paramHashMap.get("CURRENT_USER");
        }
        String str2 = TrackingIDGenerator.GetNextID();
        audit(localSecureUser, localLocalizableString, str2, 1720);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean isUserInUse(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.isUserInUse";
    debug(paramSecureUser, str);
    boolean bool = false;
    try
    {
      long l = System.currentTimeMillis();
      bool = ApprovalsHandler.isUserInUse(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return bool;
  }
  
  public static void removeUser(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.removeUser";
    debug(paramSecureUser, str);
    ApprovalsItems localApprovalsItems = null;
    try
    {
      long l = System.currentTimeMillis();
      localApprovalsItems = ApprovalsHandler.removeUser(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramSecureUser.getUserName();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_107", arrayOfObject);
      jdMethod_for(paramSecureUser, localLocalizableString, paramSecureUser.getBusinessID(), 1709);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean isGroupInUse(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.isGroupInUse";
    debug(str);
    boolean bool = false;
    try
    {
      long l = System.currentTimeMillis();
      bool = ApprovalsHandler.isGroupInUse(paramInt, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return bool;
  }
  
  public static boolean isBusinessInUse(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.isBusinessInUse";
    debug(str);
    boolean bool = false;
    try
    {
      long l = System.currentTimeMillis();
      bool = ApprovalsHandler.isBusinessInUse(paramInt, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return bool;
  }
  
  public static boolean isObjectInUse(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.isObjectInUse";
    debug(str);
    boolean bool = false;
    try
    {
      long l = System.currentTimeMillis();
      bool = ApprovalsHandler.isObjectInUse(paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return bool;
  }
  
  public static void removeGroup(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.removeGroup";
    debug(str);
    ApprovalsItems localApprovalsItems = null;
    try
    {
      long l = System.currentTimeMillis();
      localApprovalsItems = ApprovalsHandler.removeGroup(paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new Integer(paramInt);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_108", arrayOfObject);
      jdMethod_for(new SecureUser(), localLocalizableString, 0, 1710);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ApprovalsHistory getItemHistory(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "Approvals.getItemHistory";
    debug(str);
    ApprovalsHistory localApprovalsHistory = null;
    try
    {
      long l = System.currentTimeMillis();
      localApprovalsHistory = ApprovalsHandler.getItemHistory(paramInt, paramHashMap1, paramHashMap2);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsHistory;
  }
  
  public static boolean isUserApprover(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.isUserApprover";
    debug(paramSecureUser, str);
    boolean bool = false;
    try
    {
      long l = System.currentTimeMillis();
      bool = ApprovalsHandler.isUserApprover(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return bool;
  }
  
  public static void addApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.addApprovalGroup";
    debug(paramSecureUser, str);
    try
    {
      jdMethod_case(paramSecureUser, str);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if ((paramSecureUser.getUserType() == 1) && (paramApprovalsGroup != null) && (paramSecureUser.getBusinessID() != paramApprovalsGroup.getBusinessID()))
      {
        DebugLog.log("The user does not belong to the business for which the approval group is being added. The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
      long l = System.currentTimeMillis();
      ApprovalsHandler.addApprovalGroup(paramSecureUser, paramApprovalsGroup, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramApprovalsGroup.getGroupName();
      arrayOfObject[1] = new Integer(paramApprovalsGroup.getApprovalsGroupID());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_109", arrayOfObject);
      jdMethod_for(paramSecureUser, localLocalizableString, paramApprovalsGroup.getBusinessID(), 1716);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void removeApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.removeApprovalGroup";
    debug(str);
    ApprovalsItems localApprovalsItems = null;
    try
    {
      jdMethod_case(paramSecureUser, str);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if ((paramSecureUser.getUserType() == 1) && (paramSecureUser.getBusinessID() != paramApprovalsGroup.getBusinessID()))
      {
        DebugLog.log("The user does not belong to the business for which the approval group is being removed. The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
      long l = System.currentTimeMillis();
      localApprovalsItems = ApprovalsHandler.removeApprovalGroup(paramSecureUser, paramApprovalsGroup, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramApprovalsGroup.getGroupName();
      arrayOfObject[1] = new Integer(paramApprovalsGroup.getApprovalsGroupID());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_110", arrayOfObject);
      jdMethod_for(paramSecureUser, localLocalizableString, paramApprovalsGroup.getBusinessID(), 1717);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void updateApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.updateApprovalGroup";
    debug(paramSecureUser, str);
    try
    {
      jdMethod_case(paramSecureUser, str);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if ((paramSecureUser.getUserType() == 1) && (paramApprovalsGroup != null) && (paramSecureUser.getBusinessID() != paramApprovalsGroup.getBusinessID()))
      {
        DebugLog.log("The user does not belong to the business for which the approval group is being modified. The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
      long l = System.currentTimeMillis();
      ApprovalsHandler.updateApprovalGroup(paramSecureUser, paramApprovalsGroup, paramHashMap);
      PerfLog.log(str, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = new Integer(paramApprovalsGroup.getApprovalsGroupID());
      arrayOfObject[1] = paramApprovalsGroup.getGroupName();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_111", arrayOfObject);
      jdMethod_for(paramSecureUser, localLocalizableString, paramApprovalsGroup.getBusinessID(), 1718);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean isApprovalGroupInUse(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.isApprovalsGroupInUse";
    boolean bool = false;
    try
    {
      long l = System.currentTimeMillis();
      bool = ApprovalsHandler.isApprovalGroupInUse(paramInt, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return bool;
  }
  
  public static ApprovalsGroupMembers getApprovalGroupMembers(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getApprovalGroupMembers";
    debug(paramSecureUser, str);
    ApprovalsGroupMembers localApprovalsGroupMembers = null;
    try
    {
      jdMethod_case(paramSecureUser, str);
      long l = System.currentTimeMillis();
      localApprovalsGroupMembers = ApprovalsHandler.getApprovalGroupMembers(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsGroupMembers;
  }
  
  public static void assignApprovalGroups(SecureUser paramSecureUser, int paramInt, ApprovalsGroups paramApprovalsGroups, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Approvals.assignApprovalGroups";
    debug(paramSecureUser, str1);
    try
    {
      jdMethod_case(paramSecureUser, str1);
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      BusinessEmployee localBusinessEmployee = Util.getBusinessEmployeeFromProfileID(paramSecureUser, paramInt, null);
      if ((paramSecureUser.getUserType() == 1) && (paramApprovalsGroups != null))
      {
        Iterator localIterator = paramApprovalsGroups.iterator();
        ApprovalsGroup localApprovalsGroup = null;
        while (localIterator.hasNext())
        {
          localApprovalsGroup = (ApprovalsGroup)localIterator.next();
          if (localBusinessEmployee.getBusinessId() != localApprovalsGroup.getBusinessID())
          {
            DebugLog.log("The user does not belong to the business for which the approval group is being assigned. The call to " + str1 + " has failed for this reason.");
            throw new CSILException(30205);
          }
        }
      }
      long l = System.currentTimeMillis();
      ApprovalsHandler.assignApprovalGroups(paramSecureUser, paramInt, paramApprovalsGroups, paramHashMap);
      PerfLog.log(str1, l, true);
      String str2 = null;
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = localBusinessEmployee.getFirstName();
      arrayOfObject[1] = localBusinessEmployee.getLastName();
      arrayOfObject[2] = localBusinessEmployee.getUserName();
      if ((paramApprovalsGroups != null) && (paramApprovalsGroups.size() > 0))
      {
        localObject = paramApprovalsGroups.iterator();
        LocalizableList localLocalizableList = new LocalizableList("com.ffusion.util.logging.audit.approvals", "AuditList_1");
        arrayOfObject[3] = localLocalizableList;
        while (((Iterator)localObject).hasNext()) {
          localLocalizableList.add(((Iterator)localObject).next());
        }
        str2 = "AuditMessage_112";
      }
      else
      {
        str2 = "AuditMessage_113";
      }
      Object localObject = new LocalizableString("com.ffusion.util.logging.audit.approvals", str2, arrayOfObject);
      jdMethod_for(paramSecureUser, (ILocalizable)localObject, paramSecureUser.getBusinessID(), 1719);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ApprovalsGroups getApprovalGroups(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getApprovalGroups";
    debug(paramSecureUser, str);
    ApprovalsGroups localApprovalsGroups = null;
    try
    {
      jdMethod_case(paramSecureUser, str);
      if (paramSecureUser.getUserType() != 1)
      {
        DebugLog.log("The user is not a business user.  The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
      long l = System.currentTimeMillis();
      localApprovalsGroups = ApprovalsHandler.getApprovalGroups(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsGroups;
  }
  
  public static ApprovalsGroups getApprovalGroupsForUser(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getApprovalGroupsForUser";
    debug(paramSecureUser, str);
    ApprovalsGroups localApprovalsGroups = null;
    try
    {
      jdMethod_case(paramSecureUser, str);
      if (paramSecureUser.getUserType() != 1)
      {
        DebugLog.log("The user is not a business user.  The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
      long l = System.currentTimeMillis();
      localApprovalsGroups = ApprovalsHandler.getApprovalGroupsForUser(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localApprovalsGroups;
  }
  
  public static BusinessEmployees getEntitledBusinessEmployees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getEntitledBusinessEmployees";
    debug(paramSecureUser, str);
    BusinessEmployees localBusinessEmployees = null;
    try
    {
      jdMethod_case(paramSecureUser, str);
      if (paramSecureUser.getUserType() != 1)
      {
        DebugLog.log("The user is not a business user.  The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
      long l = System.currentTimeMillis();
      localBusinessEmployees = ApprovalsHandler.getEntitledBusinessEmployees(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localBusinessEmployees;
  }
  
  public static EntitlementGroups getEntitledGroups(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Approvals.getEntitledGroups";
    debug(paramSecureUser, str);
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      jdMethod_case(paramSecureUser, str);
      if (paramSecureUser.getUserType() != 1)
      {
        DebugLog.log("The user is not a business user.  The call to " + str + " has failed for this reason.");
        throw new CSILException(30205);
      }
      long l = System.currentTimeMillis();
      localEntitlementGroups = ApprovalsHandler.getEntitledGroups(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localEntitlementGroups;
  }
  
  public static void cleanup(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Approvals.cleanup";
    debug(str1);
    long l1 = System.currentTimeMillis();
    try
    {
      long l2 = System.currentTimeMillis();
      ApprovalsHandler.cleanup(paramInt, paramHashMap);
      PerfLog.log(str1, l2, true);
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new Integer(paramInt);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditMessage_114", arrayOfObject);
      StringBuffer localStringBuffer = new StringBuffer();
      jdMethod_for(new SecureUser(), localLocalizableString, 0, 1711);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
  }
  
  private static void jdMethod_case(SecureUser paramSecureUser, String paramString)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aHW))
    {
      DebugLog.log("The user is not entitled to administer the approvals workflow. The call to " + paramString + " has failed for this reason.");
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.approvals", "AuditEntFault_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(30205);
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt1, int paramInt2)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if (paramSecureUser != null) {
      if (paramSecureUser.getUserType() == 2)
      {
        str1 = "";
        str2 = String.valueOf(paramSecureUser.getProfileID());
        str3 = String.valueOf(paramSecureUser.getUserType());
      }
      else
      {
        str1 = String.valueOf(paramSecureUser.getProfileID());
        localObject = paramSecureUser.getAgent();
        if (localObject != null)
        {
          str2 = String.valueOf(((SecureUser)localObject).getProfileID());
          str3 = String.valueOf(((SecureUser)localObject).getUserType());
        }
        i = paramSecureUser.getPrimaryUserID();
      }
    }
    if (str1 == null) {
      str1 = "Approvals";
    }
    Object localObject = TrackingIDGenerator.GetNextID();
    AuditLog.log(str1, i, str2, str3, paramILocalizable, (String)localObject, paramInt2, paramInt1, null, null, null, null, null, null, null, null);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString1, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if (paramSecureUser != null) {
      if (paramSecureUser.getUserType() == 2)
      {
        str1 = "";
        str2 = String.valueOf(paramSecureUser.getProfileID());
        str3 = String.valueOf(paramSecureUser.getUserType());
      }
      else
      {
        str1 = String.valueOf(paramSecureUser.getProfileID());
        SecureUser localSecureUser = paramSecureUser.getAgent();
        if (localSecureUser != null)
        {
          str2 = String.valueOf(localSecureUser.getProfileID());
          str3 = String.valueOf(localSecureUser.getUserType());
        }
        i = paramSecureUser.getPrimaryUserID();
      }
    }
    if (str1 == null) {
      str1 = "Approvals";
    }
    AuditLog.log(str1, i, str2, str3, paramILocalizable, paramString1, paramInt2, paramInt1, paramBigDecimal, paramString2, paramString3, paramString4, paramString7, paramString8, paramString5, paramString6);
  }
  
  private static String W(String paramString)
  {
    String str = null;
    if ((paramString != null) && (paramString.equalsIgnoreCase("Rejected"))) {
      str = "Rejected";
    } else {
      str = "PendingApproval";
    }
    return str;
  }
  
  private static int V(String paramString)
  {
    int i = -1;
    if ((paramString != null) && (paramString.equalsIgnoreCase("Rejected"))) {
      i = 1714;
    } else if ((paramString != null) && (paramString.equalsIgnoreCase("Hold"))) {
      i = 1715;
    } else {
      i = 1713;
    }
    return i;
  }
  
  private static String jdMethod_int(ApprovalsItem paramApprovalsItem)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("ApprovalsType" + paramApprovalsItem.getItemType() + "SubType" + paramApprovalsItem.getItemSubType() + "AuditDesc", "com.ffusion.approvals.resources", paramApprovalsItem.getLocale());
    }
    catch (Exception localException)
    {
      str = "unknown";
    }
    return str;
  }
  
  private static ILocalizable jdMethod_for(ApprovalsItem paramApprovalsItem)
  {
    String str = "AuditType_0";
    if (paramApprovalsItem.getItemType() == 1) {
      switch (paramApprovalsItem.getItemSubType())
      {
      case 1: 
        str = "AuditType_1";
        break;
      case 3: 
        str = "AuditType_3";
        break;
      case 5: 
        str = "AuditType_5";
        break;
      case 7: 
        str = "AuditType_7";
        break;
      case 9: 
        str = "AuditType_9";
        break;
      case 10: 
        str = "AuditType_10";
        break;
      case 11: 
        str = "AuditType_11";
        break;
      case 13: 
        str = "AuditType_13";
      }
    }
    return new LocalizableString("com.ffusion.util.logging.audit.approvals", str, null);
  }
  
  private static ILocalizable U(String paramString)
  {
    String str = null;
    if (paramString.equals("Approved")) {
      str = "AuditDecision_1";
    } else if (paramString.equals("Rejected")) {
      str = "AuditDecision_2";
    } else if (paramString.equals("Hold")) {
      str = "AuditDecision_3";
    } else if (paramString.equals("Release")) {
      str = "AuditDecision_4";
    }
    if (str == null) {
      return null;
    }
    return new LocalizableString("com.ffusion.util.logging.audit.approvals", str, null);
  }
  
  private static boolean jdMethod_for(SecureUser paramSecureUser, ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws CSILException
  {
    if ((paramSecureUser == null) || ((paramSecureUser != null) && (paramSecureUser.getBusinessID() != 0))) {
      return false;
    }
    int i = paramApprovalsItem.getSubmittingUserID();
    int j = paramSecureUser.getProfileID();
    if (i == j) {
      return true;
    }
    int k = Util.getEntitlementIDFromCustomerProfile(j);
    EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(k);
    Iterator localIterator = localEntitlementGroupMembers.iterator();
    while (localIterator.hasNext())
    {
      EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localIterator.next();
      if (Integer.parseInt(localEntitlementGroupMember.getId()) == i) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Approvals
 * JD-Core Version:    0.7.0.1
 */