package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.ffs.bpw.db.InstructionActivityLog;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;

public class BPTW
  extends Initialize
{
  private static final String axR = "com.ffusion.util.logging.audit.bptw";
  private static final String axS = "AuditEntFault_1";
  private static final String axT = "AuditEntFault_2";
  private static final String axZ = "AuditEntFault_3";
  private static final String axU = "AuditEntFault_4";
  private static final String axY = "AuditEntFault_5";
  private static final String axI = "AuditEntFault_6";
  private static final String axW = "AuditEntFault_7";
  private static final String axK = "AuditEntFault_8";
  private static final String axG = "AuditEntFault_9";
  private static final String ax0 = "AuditEntFault_10";
  private static final String axJ = "AuditEntFault_11";
  private static final String axL = "AuditEntFault_12";
  private static final String axQ = "AuditEntFault_13";
  private static final String axP = "AuditEntFault_14";
  private static final String axO = "AuditEntFault_15";
  private static final String axV = "AuditEntFault_16";
  private static final String axH = "AuditEntFault_17";
  private static final String axN = "AuditEntFault_18";
  private static Entitlement axM = new Entitlement("BPTW_Logs", null, null);
  private static Entitlement axX = new Entitlement("Payments", null, null);
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.BPTW.initialize");
    com.ffusion.csil.handlers.BPTW.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.BPTW.getService();
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.SignOn";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      paramSecureUser = com.ffusion.csil.handlers.BPTW.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramSecureUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static SecureUser signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.SignOff";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      paramSecureUser = com.ffusion.csil.handlers.BPTW.signOff(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramSecureUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_2", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CustomerInfo[] addCustomers(SecureUser paramSecureUser, CustomerInfo[] paramArrayOfCustomerInfo, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.addCustomers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      CustomerInfo[] arrayOfCustomerInfo = com.ffusion.csil.handlers.BPTW.addCustomers(paramSecureUser, paramArrayOfCustomerInfo, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfCustomerInfo;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_3", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CustomerInfo[] modifyCustomers(SecureUser paramSecureUser, CustomerInfo[] paramArrayOfCustomerInfo, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.modifyCustomers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      CustomerInfo[] arrayOfCustomerInfo = com.ffusion.csil.handlers.BPTW.modifyCustomers(paramSecureUser, paramArrayOfCustomerInfo, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfCustomerInfo;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_4", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String[] deleteCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.deleteCustomers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      String[] arrayOfString = com.ffusion.csil.handlers.BPTW.deleteCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfString;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_5", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String[] deactivateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.deactivateCustomers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      String[] arrayOfString = com.ffusion.csil.handlers.BPTW.deactivateCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfString;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_6", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String[] activateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.activateCustomers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      String[] arrayOfString = com.ffusion.csil.handlers.BPTW.activateCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfString;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CustomerInfo[] getCustomersInfo(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getCustomersInfo";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      CustomerInfo[] arrayOfCustomerInfo = com.ffusion.csil.handlers.BPTW.getCustomersInfo(paramSecureUser, paramArrayOfString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfCustomerInfo;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_8", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static PayeeInfo[] getLinkedPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getLinkedPayees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      PayeeInfo[] arrayOfPayeeInfo = com.ffusion.csil.handlers.BPTW.getLinkedPayees(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfPayeeInfo;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_9", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static PayeeInfo[] getMostUsedPayees(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getMostUsedPayees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axX))
    {
      long l = System.currentTimeMillis();
      PayeeInfo[] arrayOfPayeeInfo = com.ffusion.csil.handlers.BPTW.getMostUsedPayees(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfPayeeInfo;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_10", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static PayeeInfo[] getPreferredPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getPreferredPayees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axX))
    {
      long l = System.currentTimeMillis();
      PayeeInfo[] arrayOfPayeeInfo = com.ffusion.csil.handlers.BPTW.getPreferredPayees(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfPayeeInfo;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_11", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static InstructionActivityLog[] getLogInfo(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getLogInfo";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      InstructionActivityLog[] arrayOfInstructionActivityLog = com.ffusion.csil.handlers.BPTW.getLogInfo(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfInstructionActivityLog;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_12", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static SecureUser signOnBanking(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.signOnBanking";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      paramSecureUser = com.ffusion.csil.handlers.BPTW.signOnBanking(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramSecureUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_13", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static SecureUser signOnBillPay(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.signOnBillPay";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      paramSecureUser = com.ffusion.csil.handlers.BPTW.signOnBillPay(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramSecureUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_14", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Transfers getTransfers(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getTransfers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      Transfers localTransfers = com.ffusion.csil.handlers.BPTW.getTransfers(paramSecureUser, paramAccounts, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localTransfers;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_15", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static RecTransfers getRecTransfers(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getRecTransfers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      RecTransfers localRecTransfers = com.ffusion.csil.handlers.BPTW.getRecTransfers(paramSecureUser, paramAccounts, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localRecTransfers;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_16", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Payees getPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getPayees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      Payees localPayees = com.ffusion.csil.handlers.BPTW.getPayees(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localPayees;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_17", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Object[] getPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BPTW.getPayments";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axM))
    {
      long l = System.currentTimeMillis();
      Object[] arrayOfObject = com.ffusion.csil.handlers.BPTW.getPayments(paramSecureUser, paramAccounts, paramPayees, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfObject;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bptw", "AuditEntFault_18", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.BPTW
 * JD-Core Version:    0.7.0.1
 */