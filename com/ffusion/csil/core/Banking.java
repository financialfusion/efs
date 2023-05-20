package com.ffusion.csil.core;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AcctTransactionRpt;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.bcreport.ReportLogRecord;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.ReportAuditAdapter;
import com.ffusion.efs.adapters.profile.WireAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Banking
  extends Initialize
{
  private static Entitlement aGj = new Entitlement("Transfers", null, null);
  private static Entitlement aHn = new Entitlement("Transfers", null, null);
  private static Entitlement aHe = new Entitlement("External Transfers", null, null);
  private static Entitlement aHd = new Entitlement("External Transfers From", null, null);
  private static Entitlement aGp = new Entitlement("External Transfers To", null, null);
  private static Entitlement aGn = new Entitlement("Multi-Currency Transfers", null, null);
  private static Entitlement aGq = new Entitlement("Transfers", null, null);
  private static final String aG8 = "com.ffusion.util.logging.audit.banking";
  private static final String aGz = "AuditMessage_1";
  private static final String aGY = "AuditMessage_2";
  private static final String aG5 = "AuditMessage_3";
  private static final String aGT = "AuditMessage_4";
  private static final String aGg = "AuditMessage_5";
  private static final String aGO = "AuditMessage_6";
  private static final String aG6 = "AuditMessage_7";
  private static final String aGL = "AuditMessage_8";
  private static final String aHm = "AuditMessage_9";
  private static final String aGD = "AuditMessage_10";
  private static final String aGu = "AuditMessage_11";
  private static final String aGy = "AuditMessage_12";
  private static final String aGl = "AuditMessage_13";
  private static final String aGw = "AuditMessage_14";
  private static final String aGS = "AuditMessage_15";
  private static final String aGk = "AuditMessage_16";
  private static final String aHr = "AuditMessage_17";
  private static final String aGB = "AuditMessage_18";
  private static final String aGH = "AuditMessage_19";
  private static final String aG4 = "AuditMessage_20";
  private static final String aGZ = "AuditMessage_21";
  private static final String aHl = "AuditMessage_22";
  private static final String aGx = "AuditMessage_23";
  private static final String aHg = "AuditMessage_24";
  private static final String aHp = "AuditMessage_25";
  private static final String aG0 = "AuditMessage_26";
  private static final String aGU = "AuditMessage_27";
  private static final String aGr = "AuditMessage_28";
  private static final String aGK = "AuditMessage_29";
  private static final String aHh = "AuditMessage_30";
  private static final String aHb = "AuditMessage_31";
  private static final String aGI = "AuditMessage_32";
  private static final String aG9 = "AuditMessage_33";
  private static final String aHf = "AuditMessage_34";
  private static final String aGX = "AuditMessage_35";
  private static final String aHi = "AuditMessage_36";
  private static final String aGv = "AuditMessage_37";
  private static final String aGM = "AuditMessage_38";
  private static final String aHs = "AuditMessage_39";
  private static final String aG7 = "AuditMessage_40";
  private static final String aGA = "AuditMessage_41";
  private static final String aHa = "AuditMessage_42";
  private static final String aGm = "AuditMessage_43";
  private static final String aHk = "AuditMessage_44";
  private static final String aHj = "AuditMessage_45";
  private static final String aGR = "AuditMessage_46";
  private static final String aGJ = "AuditMessage_47";
  private static final String aGi = "AuditMessage_48";
  private static final String aGo = "AuditMessage_49";
  private static final String aHq = "AuditMessage_50";
  private static final String aGC = "AuditMessage_51";
  private static final String aGW = "AuditMessage_52";
  private static final String aHc = "AuditMessage_53";
  private static final String aGN = "AuditMessage_54";
  private static final String aGh = "AuditMessage_55";
  private static final String aGE = "AuditMessage_56";
  private static final String aHo = "AuditMessage_57";
  private static final String aG3 = "AuditMessage_58";
  private static final String aGP = "AuditMessage_59";
  private static final String aGF = "AuditMessage_60";
  private static final String aGt = "AuditMessage_61";
  private static final String aG2 = "AuditMessage_62";
  private static final String aGQ = "AuditMessage_63";
  private static final String aGV = "AuditMessage_64";
  private static final String aG1 = "AuditMessage_65";
  private static final String aGG = "AuditMessage_66";
  private static final String aGs = "sUser";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Banking.initialize");
    com.ffusion.csil.handlers.Banking.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.Banking.getService();
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.SignOn";
    long l = System.currentTimeMillis();
    paramSecureUser = com.ffusion.csil.handlers.Banking.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramSecureUser;
  }
  
  public static SecureUser signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.SignOff";
    long l = System.currentTimeMillis();
    paramSecureUser = com.ffusion.csil.handlers.Banking.signOff(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramSecureUser;
  }
  
  public static SecureUser changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.ChangePIN";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    paramSecureUser = com.ffusion.csil.handlers.Banking.changePIN(paramSecureUser, paramString1, paramString2, paramHashMap);
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_1", null);
    audit(paramSecureUser, localLocalizableString, null, 1900);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramSecureUser;
  }
  
  public static com.ffusion.beans.accounts.Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetAccounts";
    long l = System.currentTimeMillis();
    com.ffusion.beans.accounts.Accounts localAccounts = com.ffusion.csil.handlers.Banking.getAccounts(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetTransactions";
    throw new CSILException(str, 19003);
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetTransactions";
    long l = System.currentTimeMillis();
    Transactions localTransactions = com.ffusion.csil.handlers.Banking.getTransactions(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localTransactions;
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetTransactions";
    long l = System.currentTimeMillis();
    Transactions localTransactions = com.ffusion.csil.handlers.Banking.getTransactions(paramSecureUser, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localTransactions;
  }
  
  public static Transfers getTransfers(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetTransfers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aGj))
    {
      long l = System.currentTimeMillis();
      Transfers localTransfers = com.ffusion.csil.handlers.Banking.getTransfers(paramSecureUser, paramAccounts, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localTransfers;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_59", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static RecTransfers getRecTransfers(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetRecTransfers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aGj))
    {
      long l = System.currentTimeMillis();
      RecTransfers localRecTransfers = com.ffusion.csil.handlers.Banking.getRecTransfers(paramSecureUser, paramAccounts, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localRecTransfers;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_60", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static RecTransfer getRecTransferByID(SecureUser paramSecureUser, String paramString, com.ffusion.beans.accounts.Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetRecTransferByID";
    jdMethod_int(paramSecureUser, aGj, str);
    long l = System.currentTimeMillis();
    RecTransfer localRecTransfer = com.ffusion.csil.handlers.Banking.getRecTransferById(paramSecureUser, paramString, paramAccounts, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRecTransfer;
  }
  
  public static RecTransfer getRecTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetRecTransferByID";
    jdMethod_int(paramSecureUser, aGj, str);
    long l = System.currentTimeMillis();
    RecTransfer localRecTransfer = com.ffusion.csil.handlers.Banking.getRecTransferByID(paramSecureUser, paramTransfer, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRecTransfer;
  }
  
  public static Transfer getTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetTransferByID";
    jdMethod_int(paramSecureUser, aGj, str);
    long l = System.currentTimeMillis();
    Transfer localTransfer = com.ffusion.csil.handlers.Banking.getTransferByID(paramSecureUser, paramTransfer, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localTransfer;
  }
  
  public static ReportLogRecords getAuditTransferByID(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetAuditTransferByID";
    jdMethod_int(paramSecureUser, aGj, str);
    long l = System.currentTimeMillis();
    ReportLogRecords localReportLogRecords = WireAdapter.getAuditHistoryRecordsById(paramString1, paramString2, paramSecureUser.getLocale());
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localReportLogRecords;
  }
  
  public static Transfers getTransferTemplates(SecureUser paramSecureUser, Transfer paramTransfer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetTransferTemplates";
    jdMethod_int(paramSecureUser, aGj, str);
    long l = System.currentTimeMillis();
    Transfers localTransfers = com.ffusion.csil.handlers.Banking.getTransferTemplates(paramSecureUser, paramTransfer, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localTransfers;
  }
  
  public static Transfer addTransferTemplate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.AddTransferTemplate";
    if ((paramTransfer.getTransferDestination().equals("INTERNAL")) || (paramTransfer.getTransferDestination().equals("ITOI"))) {
      jdMethod_int(paramSecureUser, aGj, str1);
    } else {
      jdMethod_int(paramSecureUser, aHe, str1);
    }
    if (jdMethod_new(paramTransfer)) {
      jdMethod_int(paramSecureUser, aGn, str1);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    int i = 1909;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramTransfer.getTemplateName();
    try
    {
      paramTransfer.setTrackingID(str2);
      paramTransfer = com.ffusion.csil.handlers.Banking.sendTransfer(paramSecureUser, paramTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_3", arrayOfObject);
      audit(paramTransfer.getAuditRecord(paramSecureUser, localLocalizableString2, i, ""));
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramTransfer, paramSecureUser, paramHashMap);
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_2", arrayOfObject);
    audit(paramTransfer.getAuditRecord(paramSecureUser, localLocalizableString1, i, "TEMPLATE_ADDED"));
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return paramTransfer;
  }
  
  public static Transfer addRecTransferTemplate(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.AddRecTransferTemplate";
    if ((paramRecTransfer.getTransferDestination().equals("INTERNAL")) || (paramRecTransfer.getTransferDestination().equals("ITOI"))) {
      jdMethod_int(paramSecureUser, aHn, str);
    } else {
      jdMethod_int(paramSecureUser, aHe, str);
    }
    if (jdMethod_new(paramRecTransfer)) {
      jdMethod_int(paramSecureUser, aGn, str);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    int i = 1909;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRecTransfer.getTemplateName();
    try
    {
      paramRecTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
      paramRecTransfer = com.ffusion.csil.handlers.Banking.sendRecTransfer(paramSecureUser, paramRecTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_5", arrayOfObject);
      audit(paramRecTransfer.getAuditRecord(paramSecureUser, localLocalizableString2, i, ""));
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramRecTransfer, paramSecureUser, paramHashMap);
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_4", arrayOfObject);
    audit(paramRecTransfer.getAuditRecord(paramSecureUser, localLocalizableString1, i, "TEMPLATE_ADDED"));
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramRecTransfer;
  }
  
  public static Transfer modifyTransferTemplate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.ModifyTransferTemplate";
    if ((paramTransfer.getTransferDestination().equals("INTERNAL")) || (paramTransfer.getTransferDestination().equals("ITOI"))) {
      jdMethod_int(paramSecureUser, aGj, str);
    } else {
      jdMethod_int(paramSecureUser, aHe, str);
    }
    if (jdMethod_new(paramTransfer)) {
      jdMethod_int(paramSecureUser, aGn, str);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramTransfer.getTemplateName();
    int i = 1910;
    try
    {
      paramTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
      com.ffusion.csil.handlers.Banking.modifyTransfer(paramSecureUser, paramTransfer, null, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_7", arrayOfObject);
      audit(paramTransfer.getAuditRecord(paramSecureUser, localLocalizableString2, i, ""));
      throw localCSILException;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_6", arrayOfObject);
    audit(paramTransfer.getAuditRecord(paramSecureUser, localLocalizableString1, i, "TEMPLATE_MODIFIED"));
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramTransfer;
  }
  
  public static RecTransfer modifyRecTransferTemplate(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.ModifyRecTransferTemplate";
    if ((paramRecTransfer.getTransferDestination().equals("INTERNAL")) || (paramRecTransfer.getTransferDestination().equals("ITOI"))) {
      jdMethod_int(paramSecureUser, aHn, str);
    } else {
      jdMethod_int(paramSecureUser, aHe, str);
    }
    if (jdMethod_new(paramRecTransfer)) {
      jdMethod_int(paramSecureUser, aGn, str);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRecTransfer.getTemplateName();
    int i = 1910;
    try
    {
      paramRecTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
      com.ffusion.csil.handlers.Banking.modifyRecTransfer(paramSecureUser, paramRecTransfer, null, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_9", arrayOfObject);
      audit(paramRecTransfer.getAuditRecord(paramSecureUser, localLocalizableString2, i, ""));
      throw localCSILException;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_8", arrayOfObject);
    audit(paramRecTransfer.getAuditRecord(paramSecureUser, localLocalizableString1, i, "TEMPLATE_MODIFIED"));
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramRecTransfer;
  }
  
  public static Transfer deleteTransferTemplate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.DeleteTransferTemplate";
    if ((paramTransfer.getTransferDestination().equals("INTERNAL")) || (paramTransfer.getTransferDestination().equals("ITOI"))) {
      jdMethod_int(paramSecureUser, aGj, str);
    } else {
      jdMethod_int(paramSecureUser, aHe, str);
    }
    if (jdMethod_new(paramTransfer)) {
      jdMethod_int(paramSecureUser, aGn, str);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramTransfer.getTemplateName();
    int i = 1911;
    try
    {
      paramTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
      com.ffusion.csil.handlers.Banking.cancelTransfer(paramSecureUser, paramTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_11", arrayOfObject);
      audit(paramTransfer.getAuditRecord(paramSecureUser, localLocalizableString2, i, ""));
      throw localCSILException;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_10", arrayOfObject);
    audit(paramTransfer.getAuditRecord(paramSecureUser, localLocalizableString1, i, "TEMPLATE_DELETED"));
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramTransfer;
  }
  
  public static RecTransfer deleteRecTransferTemplate(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.DeleteRecTransferTemplate";
    if ((paramRecTransfer.getTransferDestination().equals("INTERNAL")) || (paramRecTransfer.getTransferDestination().equals("ITOI"))) {
      jdMethod_int(paramSecureUser, aHn, str);
    } else {
      jdMethod_int(paramSecureUser, aHe, str);
    }
    if (jdMethod_new(paramRecTransfer)) {
      jdMethod_int(paramSecureUser, aGn, str);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRecTransfer.getTemplateName();
    int i = 1911;
    try
    {
      paramRecTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
      com.ffusion.csil.handlers.Banking.cancelRecTransfer(paramSecureUser, paramRecTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_13", arrayOfObject);
      audit(paramRecTransfer.getAuditRecord(paramSecureUser, localLocalizableString2, i, ""));
      throw localCSILException;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_12", arrayOfObject);
    audit(paramRecTransfer.getAuditRecord(paramSecureUser, localLocalizableString1, i, "TEMPLATE_DELETED"));
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramRecTransfer;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, Transfer paramTransfer, String paramString)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    int i = paramSecureUser.getBusinessID();
    Entitlement localEntitlement = null;
    if ((paramTransfer.getTransferDestination().equals("INTERNAL")) || (paramTransfer.getTransferDestination().equals("ITOI"))) {
      localEntitlement = aGj;
    } else {
      localEntitlement = aHe;
    }
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement)) {
      throw new CSILException(paramString, 20001);
    }
    if ((jdMethod_new(paramTransfer)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aGn))) {
      throw new CSILException(paramString, 20001);
    }
    if ((paramTransfer.getTransferDestination().equals("ETOI")) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aHd))) {
      throw new CSILException(paramString, 20001);
    }
    if ((paramTransfer.getTransferDestination().equals("ITOE")) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aGp))) {
      throw new CSILException(paramString, 20001);
    }
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    Account localAccount = paramTransfer.getFromAccount();
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localAccount) });
    if ("1".equals(localAccount.getCoreAccount())) {
      localMultiEntitlement.setOperations(new String[] { "Transfers From" });
    } else {
      localMultiEntitlement.setOperations(new String[] { "External Transfers From" });
    }
    if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) != null) {
      throw new CSILException(paramString, 20001);
    }
    localAccount = paramTransfer.getToAccount();
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localAccount) });
    if ("1".equals(localAccount.getCoreAccount())) {
      localMultiEntitlement.setOperations(new String[] { "Transfers To" });
    } else {
      localMultiEntitlement.setOperations(new String[] { "External Transfers To" });
    }
    if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) != null) {
      throw new CSILException(paramString, 20001);
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, TransferBatch paramTransferBatch, String paramString)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    int i = paramSecureUser.getBusinessID();
    if (paramTransferBatch.getTransfers() == null) {
      return;
    }
    Iterator localIterator = paramTransferBatch.getTransfers().iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      Entitlement localEntitlement = null;
      if ((localTransfer.getTransferDestination().equals("INTERNAL")) || (localTransfer.getTransferDestination().equals("ITOI"))) {
        localEntitlement = aGj;
      } else {
        localEntitlement = aHe;
      }
      if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement)) {
        throw new CSILException(paramString, 20001);
      }
      if ((jdMethod_new(localTransfer)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aGn))) {
        throw new CSILException(paramString, 20001);
      }
      if ((localTransfer.getTransferDestination().equals("ETOI")) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aHd))) {
        throw new CSILException(paramString, 20001);
      }
      if ((localTransfer.getTransferDestination().equals("ITOE")) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aGp))) {
        throw new CSILException(paramString, 20001);
      }
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      Account localAccount = localTransfer.getFromAccount();
      localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localAccount) });
      if ("1".equals(localAccount.getCoreAccount())) {
        localMultiEntitlement.setOperations(new String[] { "Transfers From" });
      } else {
        localMultiEntitlement.setOperations(new String[] { "External Transfers From" });
      }
      if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) != null) {
        throw new CSILException(paramString, 20001);
      }
      localAccount = localTransfer.getToAccount();
      localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localAccount) });
      if ("1".equals(localAccount.getCoreAccount())) {
        localMultiEntitlement.setOperations(new String[] { "Transfers To" });
      } else {
        localMultiEntitlement.setOperations(new String[] { "External Transfers To" });
      }
      if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) != null) {
        throw new CSILException(paramString, 20001);
      }
    }
  }
  
  public static Transfer sendTransfer(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.SendTransfer";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramTransfer, str1);
    long l = System.currentTimeMillis();
    String str2 = paramTransfer.getTrackingID();
    if (str2 == null) {
      str2 = TrackingIDGenerator.GetNextID();
    }
    paramTransfer.setTrackingID(str2);
    paramTransfer.setSubmittedBy(paramSecureUser.getProfileID());
    try
    {
      paramTransfer = com.ffusion.csil.handlers.Banking.sendTransfer(paramSecureUser, paramTransfer, paramHashMap);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramTransfer.getAmountValue().toString();
      arrayOfObject[1] = paramTransfer.getFromAccount().buildLocalizableAccountID();
      arrayOfObject[2] = paramTransfer.getToAccount().buildLocalizableAccountID();
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_14", arrayOfObject);
      if (paramTransfer.getDate() != null)
      {
        arrayOfObject = new Object[4];
        arrayOfObject[0] = paramTransfer.getAmountValue().toString();
        arrayOfObject[1] = paramTransfer.getFromAccount().buildLocalizableAccountID();
        arrayOfObject[2] = paramTransfer.getToAccount().buildLocalizableAccountID();
        arrayOfObject[3] = paramTransfer.getDate();
        localObject1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_16", arrayOfObject);
      }
      audit(paramTransfer.getAuditRecord(paramSecureUser, (ILocalizable)localObject1, 1901, "ADDED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = new Object[3];
      localObject1[0] = paramTransfer.getAmountValue().toString();
      localObject1[1] = paramTransfer.getFromAccount().buildLocalizableAccountID();
      localObject1[2] = paramTransfer.getToAccount().buildLocalizableAccountID();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_15", (Object[])localObject1);
      if (paramTransfer.getDate() != null)
      {
        localObject1 = new Object[4];
        localObject1[0] = paramTransfer.getAmountValue().toString();
        localObject1[1] = paramTransfer.getFromAccount().buildLocalizableAccountID();
        localObject1[2] = paramTransfer.getToAccount().buildLocalizableAccountID();
        localObject1[3] = paramTransfer.getDate();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_17", (Object[])localObject1);
      }
      audit(paramTransfer.getAuditRecord(paramSecureUser, localLocalizableString, 1901, ""));
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramTransfer, paramSecureUser, paramHashMap);
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 5, paramTransfer.getID(), str2);
    paramTransfer.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return paramTransfer;
  }
  
  public static Transfer cancelTransfer(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.CancelTransfer";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramTransfer, str1);
    long l = System.currentTimeMillis();
    String str2 = paramTransfer.getTrackingID();
    if (str2 == null)
    {
      str2 = TrackingIDGenerator.GetNextID();
      paramTransfer.setTrackingID(str2);
    }
    try
    {
      com.ffusion.csil.handlers.Banking.cancelTransfer(paramSecureUser, paramTransfer, paramHashMap);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramTransfer.getAmountValue().toString();
      arrayOfObject[1] = paramTransfer.getFromAccount().buildLocalizableAccountID();
      arrayOfObject[2] = paramTransfer.getToAccount().buildLocalizableAccountID();
      localObject = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_18", arrayOfObject);
      audit(paramTransfer.getAuditRecord(paramSecureUser, (ILocalizable)localObject, 1902, "DELETED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject = new Object[3];
      localObject[0] = paramTransfer.getAmountValue().toString();
      localObject[1] = paramTransfer.getFromAccount().buildLocalizableAccountID();
      localObject[2] = paramTransfer.getToAccount().buildLocalizableAccountID();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_19", (Object[])localObject);
      audit(paramTransfer.getAuditRecord(paramSecureUser, localLocalizableString, 1902, ""));
      throw localCSILException;
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 5, paramTransfer.getID(), str2);
    paramTransfer.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return paramTransfer;
  }
  
  public static RecTransfer sendRecTransfer(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.SendRecTransfer";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramRecTransfer, str1);
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    paramRecTransfer.setTrackingID(str2);
    try
    {
      paramRecTransfer = com.ffusion.csil.handlers.Banking.sendRecTransfer(paramSecureUser, paramRecTransfer, paramHashMap);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramRecTransfer.getAmountValue().toString();
      arrayOfObject[1] = paramRecTransfer.getFromAccount().buildLocalizableAccountID();
      arrayOfObject[2] = paramRecTransfer.getToAccount().buildLocalizableAccountID();
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_20", arrayOfObject);
      if (paramRecTransfer.getDate() != null)
      {
        arrayOfObject = new Object[4];
        arrayOfObject[0] = paramRecTransfer.getAmountValue().toString();
        arrayOfObject[1] = paramRecTransfer.getFromAccount().buildLocalizableAccountID();
        arrayOfObject[2] = paramRecTransfer.getToAccount().buildLocalizableAccountID();
        arrayOfObject[3] = paramRecTransfer.getDate();
        localObject1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_22", arrayOfObject);
      }
      audit(paramRecTransfer.getAuditRecord(paramSecureUser, (ILocalizable)localObject1, 1903, "ADDED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = new Object[3];
      localObject1[0] = paramRecTransfer.getAmountValue().toString();
      localObject1[1] = paramRecTransfer.getFromAccount().buildLocalizableAccountID();
      localObject1[2] = paramRecTransfer.getToAccount().buildLocalizableAccountID();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_21", (Object[])localObject1);
      if (paramRecTransfer.getDate() != null)
      {
        localObject1 = new Object[4];
        localObject1[0] = paramRecTransfer.getAmountValue().toString();
        localObject1[1] = paramRecTransfer.getFromAccount().buildLocalizableAccountID();
        localObject1[2] = paramRecTransfer.getToAccount().buildLocalizableAccountID();
        localObject1[3] = paramRecTransfer.getDate();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_23", (Object[])localObject1);
      }
      audit(paramRecTransfer.getAuditRecord(paramSecureUser, localLocalizableString, 1903, ""));
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramRecTransfer, paramSecureUser, paramHashMap);
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 5, paramRecTransfer.getID(), str2);
    paramRecTransfer.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return paramRecTransfer;
  }
  
  public static RecTransfer cancelRecTransfer(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.CancelRecTransfer";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramRecTransfer, str1);
    long l = System.currentTimeMillis();
    String str2 = paramRecTransfer.getTrackingID();
    if (str2 == null)
    {
      str2 = TrackingIDGenerator.GetNextID();
      paramRecTransfer.setTrackingID(str2);
    }
    try
    {
      com.ffusion.csil.handlers.Banking.cancelRecTransfer(paramSecureUser, paramRecTransfer, paramHashMap);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramRecTransfer.getAmountValue().toString();
      arrayOfObject[1] = paramRecTransfer.getFromAccount().buildLocalizableAccountID();
      arrayOfObject[2] = paramRecTransfer.getToAccount().buildLocalizableAccountID();
      localObject = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_24", arrayOfObject);
      audit(paramRecTransfer.getAuditRecord(paramSecureUser, (ILocalizable)localObject, 1904, "DELETED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject = new Object[3];
      localObject[0] = paramRecTransfer.getAmountValue().toString();
      localObject[1] = paramRecTransfer.getFromAccount().buildLocalizableAccountID();
      localObject[2] = paramRecTransfer.getToAccount().buildLocalizableAccountID();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_25", (Object[])localObject);
      audit(paramRecTransfer.getAuditRecord(paramSecureUser, localLocalizableString, 1904, ""));
      throw localCSILException;
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 5, paramRecTransfer.getID(), str2);
    paramRecTransfer.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return paramRecTransfer;
  }
  
  public static Transfer modifyTransfer(SecureUser paramSecureUser, Transfer paramTransfer1, Transfer paramTransfer2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.ModifyTransfer";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramTransfer1, str1);
    long l = System.currentTimeMillis();
    String str2 = paramTransfer1.getTrackingID();
    if (str2 == null)
    {
      str2 = TrackingIDGenerator.GetNextID();
      paramTransfer1.setTrackingID(str2);
    }
    try
    {
      paramTransfer1 = com.ffusion.csil.handlers.Banking.modifyTransfer(paramSecureUser, paramTransfer1, paramTransfer2, paramHashMap);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramTransfer1.getAmountValue().toString();
      arrayOfObject[1] = paramTransfer1.getFromAccount().buildLocalizableAccountID();
      arrayOfObject[2] = paramTransfer1.getToAccount().buildLocalizableAccountID();
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_26", arrayOfObject);
      audit(paramTransfer1.getAuditRecord(paramSecureUser, (ILocalizable)localObject1, 1905, "MODIFIED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = new Object[3];
      localObject1[0] = paramTransfer1.getAmountValue().toString();
      localObject1[1] = paramTransfer1.getFromAccount().buildLocalizableAccountID();
      localObject1[2] = paramTransfer1.getToAccount().buildLocalizableAccountID();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_27", (Object[])localObject1);
      audit(paramTransfer1.getAuditRecord(paramSecureUser, localLocalizableString, 1905, ""));
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramTransfer1, paramSecureUser, paramHashMap);
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 5, paramTransfer1.getID(), str2);
    paramTransfer1.logChanges(localHistoryTracker, paramTransfer2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return paramTransfer1;
  }
  
  public static RecTransfer modifyRecTransfer(SecureUser paramSecureUser, RecTransfer paramRecTransfer1, RecTransfer paramRecTransfer2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.ModifyRecTransfer";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramRecTransfer1, str1);
    long l = System.currentTimeMillis();
    String str2 = paramRecTransfer1.getTrackingID();
    if (str2 == null)
    {
      str2 = TrackingIDGenerator.GetNextID();
      paramRecTransfer1.setTrackingID(str2);
    }
    try
    {
      paramRecTransfer1 = com.ffusion.csil.handlers.Banking.modifyRecTransfer(paramSecureUser, paramRecTransfer1, paramRecTransfer2, paramHashMap);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramRecTransfer1.getAmountValue().toString();
      arrayOfObject[1] = paramRecTransfer1.getFromAccount().buildLocalizableAccountID();
      arrayOfObject[2] = paramRecTransfer1.getToAccount().buildLocalizableAccountID();
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_28", arrayOfObject);
      audit(paramRecTransfer1.getAuditRecord(paramSecureUser, (ILocalizable)localObject1, 1906, "MODIFIED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = new Object[3];
      localObject1[0] = paramRecTransfer1.getAmountValue().toString();
      localObject1[1] = paramRecTransfer1.getFromAccount().buildLocalizableAccountID();
      localObject1[2] = paramRecTransfer1.getToAccount().buildLocalizableAccountID();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_29", (Object[])localObject1);
      audit(paramRecTransfer1.getAuditRecord(paramSecureUser, localLocalizableString, 1906, ""));
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramRecTransfer1, paramSecureUser, paramHashMap);
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 5, paramRecTransfer1.getID(), str2);
    paramRecTransfer1.logChanges(localHistoryTracker, paramRecTransfer2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return paramRecTransfer1;
  }
  
  public static RecTransfer skipRecTransfer(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.SkipRecTransfer";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aHn))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramRecTransfer.getTrackingID();
      RecTransfer localRecTransfer = com.ffusion.csil.handlers.Banking.skipRecTransfer(paramSecureUser, paramRecTransfer, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramRecTransfer.getAmountValue().toString();
      arrayOfObject[1] = paramRecTransfer.getFromAccount().buildLocalizableAccountID();
      arrayOfObject[2] = paramRecTransfer.getToAccount().buildLocalizableAccountID();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_30", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 1907, paramRecTransfer.getAmountValue(), paramRecTransfer.getID());
      debug(paramSecureUser, str1);
      return localRecTransfer;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_60", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static TransferBatch addTransferBatch(SecureUser paramSecureUser, TransferBatch paramTransferBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.AddTransferBatch";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramTransferBatch, str1);
    long l = System.currentTimeMillis();
    String str2 = paramTransferBatch.getTrackingID();
    if (str2 == null) {
      str2 = TrackingIDGenerator.GetNextID();
    }
    paramTransferBatch.setTrackingID(str2);
    if (paramSecureUser.getBusinessID() == 0) {
      paramTransferBatch.setCustomerID(paramSecureUser.getPrimaryUserID());
    } else {
      paramTransferBatch.setCustomerID(paramSecureUser.getBusinessID());
    }
    paramTransferBatch.setBankID(paramSecureUser.getBPWFIID());
    paramTransferBatch.setSubmittedBy(paramSecureUser.getProfileID());
    Object localObject1 = paramTransferBatch.getTransfers().iterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Transfer)((Iterator)localObject1).next();
      ((Transfer)localObject2).setBankID(paramTransferBatch.getBankID());
      ((Transfer)localObject2).setSubmittedBy(paramTransferBatch.getSubmittedBy());
      ((Transfer)localObject2).setCustomerID(paramTransferBatch.getCustomerID());
      ((Transfer)localObject2).setTrackingID(TrackingIDGenerator.GetNextID());
      ((Transfer)localObject2).setTransferDestinationByAccounts();
    }
    try
    {
      paramTransferBatch = com.ffusion.csil.handlers.Banking.addTransferBatch(paramSecureUser, paramTransferBatch, paramHashMap);
      localObject1 = new Object[2];
      localObject1[0] = paramTransferBatch.getTemplateName();
      localObject1[1] = paramTransferBatch.getAmountValue().toString();
      localObject2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_64", (Object[])localObject1);
      audit(paramTransferBatch.getAuditRecord(paramSecureUser, (ILocalizable)localObject2, 1901, "ADDED"));
    }
    catch (CSILException localCSILException)
    {
      localCSILException = localCSILException;
      localObject2 = new Object[2];
      localObject2[0] = paramTransferBatch.getTemplateName();
      localObject2[1] = paramTransferBatch.getAmount();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_64", (Object[])localObject2);
      audit(paramTransferBatch.getAuditRecord(paramSecureUser, localLocalizableString, 1901, ""));
      throw localCSILException;
    }
    finally {}
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 27, paramTransferBatch.getID(), str2);
    paramTransferBatch.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return paramTransferBatch;
  }
  
  public static TransferBatch modifyTransferBatch(SecureUser paramSecureUser, TransferBatch paramTransferBatch1, TransferBatch paramTransferBatch2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.ModifyTransferBatch";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramTransferBatch1, str);
    long l = System.currentTimeMillis();
    paramTransferBatch1.setBankID(paramSecureUser.getBPWFIID());
    paramTransferBatch1.setSubmittedBy(paramSecureUser.getProfileID());
    Object localObject1 = paramTransferBatch1.getTransfers().iterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Transfer)((Iterator)localObject1).next();
      ((Transfer)localObject2).setBankID(paramTransferBatch1.getBankID());
      ((Transfer)localObject2).setSubmittedBy(paramTransferBatch1.getSubmittedBy());
      ((Transfer)localObject2).setCustomerID(paramTransferBatch1.getCustomerID());
      ((Transfer)localObject2).setTransferDestinationByAccounts();
    }
    try
    {
      paramTransferBatch1 = com.ffusion.csil.handlers.Banking.modifyTransferBatch(paramSecureUser, paramTransferBatch1, paramHashMap);
      localObject1 = new Object[2];
      localObject1[0] = paramTransferBatch1.getTemplateName();
      localObject1[1] = paramTransferBatch1.getAmountValue().toString();
      localObject2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_65", (Object[])localObject1);
      audit(paramTransferBatch1.getAuditRecord(paramSecureUser, (ILocalizable)localObject2, 1905, "MODIFIED"));
    }
    catch (CSILException localCSILException)
    {
      localCSILException = localCSILException;
      localObject2 = new Object[2];
      localObject2[0] = paramTransferBatch1.getTemplateName();
      localObject2[1] = paramTransferBatch1.getAmount();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_65", (Object[])localObject2);
      audit(paramTransferBatch1.getAuditRecord(paramSecureUser, localLocalizableString, 1905, ""));
      throw localCSILException;
    }
    finally {}
    PerfLog.log(str, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 27, paramTransferBatch1.getID(), paramTransferBatch1.getTrackingID());
    paramTransferBatch1.logChanges(localHistoryTracker, paramTransferBatch2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str);
    return paramTransferBatch1;
  }
  
  public static TransferBatch cancelTransferBatch(SecureUser paramSecureUser, TransferBatch paramTransferBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.CancelTransferBatch";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramTransferBatch, str1);
    long l = System.currentTimeMillis();
    String str2 = paramTransferBatch.getTrackingID();
    if (str2 == null)
    {
      str2 = TrackingIDGenerator.GetNextID();
      paramTransferBatch.setTrackingID(str2);
    }
    try
    {
      com.ffusion.csil.handlers.Banking.cancelTransferBatch(paramSecureUser, paramTransferBatch, paramHashMap);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramTransferBatch.getTemplateName();
      arrayOfObject[1] = paramTransferBatch.getAmountValue().toString();
      localObject = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_63", arrayOfObject);
      audit(paramTransferBatch.getAuditRecord(paramSecureUser, (ILocalizable)localObject, 1902, "DELETED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject = new Object[2];
      localObject[0] = paramTransferBatch.getTemplateName();
      localObject[1] = paramTransferBatch.getAmount();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_19", (Object[])localObject);
      audit(paramTransferBatch.getAuditRecord(paramSecureUser, localLocalizableString, 1902, ""));
      throw localCSILException;
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 27, paramTransferBatch.getID(), str2);
    paramTransferBatch.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return paramTransferBatch;
  }
  
  public static FixedDepositInstruments getFixedDepositInstruments(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getFixedDepositInstruments";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    FixedDepositInstruments localFixedDepositInstruments = com.ffusion.csil.handlers.Banking.getFixedDepositInstruments(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localFixedDepositInstruments;
  }
  
  public static void updateFixedDepositInstrument(SecureUser paramSecureUser, FixedDepositInstrument paramFixedDepositInstrument, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.updateFixedDepositInstrument";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    com.ffusion.csil.handlers.Banking.updateFixedDepositInstrument(paramSecureUser, paramFixedDepositInstrument, paramHashMap);
    PerfLog.log(str, l, true);
  }
  
  public static Transactions getRecentTransactions(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getRecentTransactions";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    Transactions localTransactions = com.ffusion.csil.handlers.Banking.getRecentTransactions(paramSecureUser, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransactions;
  }
  
  public static Transactions getRecentTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getRecentTransactions";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    Transactions localTransactions = com.ffusion.csil.handlers.Banking.getRecentTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransactions;
  }
  
  public static Transactions getPagedTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.getPagedTransactions";
    debug(paramSecureUser, str1);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    ReportCriteria localReportCriteria = (paramPagingContext == null) || (paramPagingContext.getMap() == null) ? null : (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    Properties localProperties = localReportCriteria == null ? null : localReportCriteria.getSearchCriteria();
    String str2 = localProperties.getProperty("Transaction Groups");
    ArrayList localArrayList = convertTransTypesToBAITypes(paramSecureUser, str2, paramHashMap);
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < localArrayList.size(); i++)
    {
      if (i > 0) {
        localStringBuffer.append(",");
      }
      localStringBuffer.append((String)localArrayList.get(i));
    }
    if (localStringBuffer.length() > 0) {
      paramHashMap.put("CustomTransGroupCodesForReport", localStringBuffer.toString());
    }
    Transactions localTransactions = com.ffusion.csil.handlers.Banking.getPagedTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
    paramHashMap.remove("CustomTransGroupCodesForReport");
    PerfLog.log(str1, l, true);
    return localTransactions;
  }
  
  public static Transactions getNextTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.getNextTransactions";
    debug(paramSecureUser, str1);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    ReportCriteria localReportCriteria = (paramPagingContext == null) || (paramPagingContext.getMap() == null) ? null : (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    Properties localProperties = localReportCriteria == null ? null : localReportCriteria.getSearchCriteria();
    String str2 = localProperties.getProperty("Transaction Groups");
    ArrayList localArrayList = convertTransTypesToBAITypes(paramSecureUser, str2, paramHashMap);
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < localArrayList.size(); i++)
    {
      if (i > 0) {
        localStringBuffer.append(",");
      }
      localStringBuffer.append((String)localArrayList.get(i));
    }
    if (localStringBuffer.length() > 0) {
      paramHashMap.put("CustomTransGroupCodesForReport", localStringBuffer.toString());
    }
    Transactions localTransactions = com.ffusion.csil.handlers.Banking.getNextTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
    paramHashMap.remove("CustomTransGroupCodesForReport");
    PerfLog.log(str1, l, true);
    return localTransactions;
  }
  
  public static Transactions getPreviousTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.getPreviousTransactions";
    debug(paramSecureUser, str1);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    ReportCriteria localReportCriteria = (paramPagingContext == null) || (paramPagingContext.getMap() == null) ? null : (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    Properties localProperties = localReportCriteria == null ? null : localReportCriteria.getSearchCriteria();
    String str2 = localProperties.getProperty("Transaction Groups");
    ArrayList localArrayList = convertTransTypesToBAITypes(paramSecureUser, str2, paramHashMap);
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < localArrayList.size(); i++)
    {
      if (i > 0) {
        localStringBuffer.append(",");
      }
      localStringBuffer.append((String)localArrayList.get(i));
    }
    if (localStringBuffer.length() > 0) {
      paramHashMap.put("CustomTransGroupCodesForReport", localStringBuffer.toString());
    }
    Transactions localTransactions = com.ffusion.csil.handlers.Banking.getPreviousTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
    paramHashMap.remove("CustomTransGroupCodesForReport");
    PerfLog.log(str1, l, true);
    return localTransactions;
  }
  
  public static Transactions getFDInstrumentTransactions(SecureUser paramSecureUser, FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getFDInstrumentTransactions";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    Transactions localTransactions = com.ffusion.csil.handlers.Banking.getFDInstrumentTransactions(paramSecureUser, paramFixedDepositInstrument, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransactions;
  }
  
  public static int getNumTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getNumTransactions";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    int i = com.ffusion.csil.handlers.Banking.getNumTransactions(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return i;
  }
  
  public static int getNumTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Properties paramProperties, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getNumTransactions";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    int i = com.ffusion.csil.handlers.Banking.getNumTransactions(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramProperties, paramHashMap);
    PerfLog.log(str, l, true);
    return i;
  }
  
  public static AccountHistories getHistory(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getHistory";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    AccountHistories localAccountHistories = com.ffusion.csil.handlers.Banking.getHistory(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localAccountHistories;
  }
  
  public static AccountSummaries getSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getSummary";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    AccountSummaries localAccountSummaries = com.ffusion.csil.handlers.Banking.getSummary(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    localAccountSummaries.setSecureUser(paramSecureUser);
    PerfLog.log(str, l, true);
    return localAccountSummaries;
  }
  
  public static ExtendedAccountSummaries getExtendedSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getExtendedSummary";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    paramHashMap.put("sUser", paramSecureUser);
    ExtendedAccountSummaries localExtendedAccountSummaries = com.ffusion.csil.handlers.Banking.getExtendedSummary(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localExtendedAccountSummaries;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.getReportData";
    debug(paramSecureUser, str1);
    long l = System.currentTimeMillis();
    com.ffusion.beans.accounts.Accounts localAccounts = null;
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    int i = paramSecureUser.getBusinessID() == 0 ? 1 : 0;
    if (i != 0)
    {
      localObject1 = new HashMap(paramHashMap);
      ((HashMap)localObject1).remove("SERVICE");
      localAccounts = Accounts.getAccounts(paramSecureUser, (HashMap)localObject1);
    }
    else
    {
      localAccounts = Accounts.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    }
    paramHashMap.put("AccountsForReport", localAccounts);
    Object localObject1 = paramReportCriteria.getSearchCriteria();
    Object localObject2;
    if ((((Properties)localObject1).getProperty("TransactionType") != null) && ((str2.equals("DepositDetail")) || (str2.equals("TransactionDetail")) || (str2.equals("CreditReport")) || (str2.equals("DebitReport")) || (str2.equals("TransactionDetail")) || (str2.equals("BalanceDetailOnlyReport")) || (str2.equals("BalanceDetailReport"))))
    {
      String str3 = ((Properties)localObject1).getProperty("TransactionType");
      localObject2 = convertTransTypesToBAITypes(paramSecureUser, str3, paramHashMap);
      localObject3 = new StringBuffer();
      for (int m = 0; m < ((ArrayList)localObject2).size(); m++)
      {
        if (m > 0) {
          ((StringBuffer)localObject3).append(",");
        }
        ((StringBuffer)localObject3).append((String)((ArrayList)localObject2).get(m));
      }
      if (((StringBuffer)localObject3).length() > 0) {
        paramHashMap.put("CustomTransGroupCodesForReport", ((StringBuffer)localObject3).toString());
      }
    }
    if (i == 0)
    {
      int j = paramSecureUser.getBusinessID();
      if (j < 0) {
        throw new CSILException("csil.core.Banking.getReportData", 4011, "The business ID in the secure user passed as a parameter to this method call is invalid.");
      }
      localObject2 = Business.jdMethod_int(paramSecureUser, j, paramHashMap);
      if (localObject2 == null) {
        throw new CSILException("csil.core.Banking.getReportData", 4015, "The business of the user passed in a parameter to this method could not be retrieved.");
      }
      paramHashMap.put("BusinessForReport", localObject2);
    }
    Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    IReportResult localIReportResult = null;
    localIReportResult = com.ffusion.csil.handlers.Banking.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    paramHashMap.remove("CustomTransGroupCodesForReport");
    int k = 0;
    Object localObject3 = paramReportCriteria.getReportOptions().getProperty("TITLE");
    String str4 = (String)paramReportCriteria.getSearchCriteria().get("DataClassification");
    if (str4 == null) {
      str4 = "P";
    }
    String str5 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localObject3;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_31", arrayOfObject);
    if (str2.equals("DepositDetail"))
    {
      if (str4.equals("I")) {
        k = 5413;
      } else {
        k = 5433;
      }
    }
    else if (str2.equals("TransactionDetail"))
    {
      if (str4.equals("I")) {
        k = 5414;
      } else {
        k = 5434;
      }
    }
    else if (str2.equals("CreditReport"))
    {
      if (str4.equals("I")) {
        k = 5415;
      } else {
        k = 5435;
      }
    }
    else if (str2.equals("DebitReport"))
    {
      if (str4.equals("I")) {
        k = 5416;
      } else {
        k = 5436;
      }
    }
    else if (str2.equals("BalanceDetailOnlyReport"))
    {
      if (str4.equals("I")) {
        k = 5411;
      } else {
        k = 5431;
      }
    }
    else if (str2.equals("BalanceSummaryReport"))
    {
      if (str4.equals("I")) {
        k = 5410;
      } else {
        k = 5430;
      }
    }
    else if (str2.equals("BalanceDetailReport"))
    {
      if (str4.equals("I")) {
        k = 5412;
      } else {
        k = 5432;
      }
    }
    else if (str2.equals("CustomSummaryReport")) {
      if (str4.equals("I")) {
        k = 5417;
      } else {
        k = 5437;
      }
    }
    if (k != 0) {
      audit(paramSecureUser, localLocalizableString, str5, k);
    }
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Account localAccount;
    if (str2.equals("ConsumerAccountHistory"))
    {
      localObject4 = paramReportCriteria.getReportOptions().getProperty("FORMAT");
      localObject5 = (HashMap)paramHashMap.get("CURRENT EXPORT DATES");
      if (localObject5 != null)
      {
        localObject6 = ((HashMap)localObject5).keySet();
        localObject7 = ((Set)localObject6).iterator();
        while (((Iterator)localObject7).hasNext())
        {
          localAccount = (Account)((Iterator)localObject7).next();
          String str6 = localAccount.getDisplayText();
          str5 = TrackingIDGenerator.GetNextID();
          StringBuffer localStringBuffer = new StringBuffer();
          arrayOfObject = null;
          localLocalizableString = null;
          arrayOfObject = new Object[2];
          arrayOfObject[0] = str6;
          arrayOfObject[1] = localObject4;
          k = 6301;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_62", arrayOfObject);
          audit(paramSecureUser, localLocalizableString, str5, k);
        }
      }
    }
    if ((localAccounts != null) && ((localIReportResult instanceof AcctTransactionRpt)))
    {
      localObject4 = (AcctTransactionRpt)localIReportResult;
      localObject5 = ((AcctTransactionRpt)localObject4).getAccounts();
      localObject6 = ((com.ffusion.beans.accounts.Accounts)localObject5).iterator();
      while (((Iterator)localObject6).hasNext())
      {
        localObject7 = (Account)((Iterator)localObject6).next();
        localAccount = localAccounts.getByIDAndBankIDAndRoutingNum(((Account)localObject7).getID(), ((Account)localObject7).getBankID(), ((Account)localObject7).getRoutingNum());
        if (localAccount != null) {
          ((Account)localObject7).setNickName(localAccount.getNickName());
        }
      }
    }
    Object localObject4 = ReportConsts.getReportName(str2, Locale.getDefault());
    PerfLog.log(str1, l, true);
    return localIReportResult;
  }
  
  public static FundsTransactions getFundsTransactions(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getFundsTransactions";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    FundsTransactions localFundsTransactions = com.ffusion.csil.handlers.Banking.getFundsTransactions(paramSecureUser, paramAccounts, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localFundsTransactions;
  }
  
  public static FundsTransactions getPendingFundsTransactions(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getPendingFundsTransactions";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    FundsTransactions localFundsTransactions = com.ffusion.csil.handlers.Banking.getPendingFundsTransactions(paramSecureUser, paramAccounts, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localFundsTransactions;
  }
  
  public static FundsTransactions getCompletedFundsTransactions(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getCompletedFundsTransactions";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    FundsTransactions localFundsTransactions = com.ffusion.csil.handlers.Banking.getCompletedFundsTransactions(paramSecureUser, paramAccounts, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localFundsTransactions;
  }
  
  public static Transfers getPagedPendingTransfers(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getPagedPendingTransfers";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    Transfers localTransfers = com.ffusion.csil.handlers.Banking.getPagedPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransfers;
  }
  
  public static Transfers getNextPendingTransfers(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getNextPendingTransfers";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    Transfers localTransfers = com.ffusion.csil.handlers.Banking.getNextPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransfers;
  }
  
  public static Transfers getPreviousPendingTransfers(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getPreviousPendingTransfers";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    Transfers localTransfers = com.ffusion.csil.handlers.Banking.getPreviousPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransfers;
  }
  
  public static Transfers getPagedCompletedTransfers(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getPagedCompletedTransfers";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    Transfers localTransfers = com.ffusion.csil.handlers.Banking.getPagedCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransfers;
  }
  
  public static Transfers getNextCompletedTransfers(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getNextCompletedTransfers";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    Transfers localTransfers = com.ffusion.csil.handlers.Banking.getNextCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransfers;
  }
  
  public static Transfers getPreviousCompletedTransfers(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getPreviousCompletedTransfers";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    Transfers localTransfers = com.ffusion.csil.handlers.Banking.getPreviousCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localTransfers;
  }
  
  public static FundsTransactionTemplates getFundsTransactionTemplates(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getFundsTransactionTemplates";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    Object localObject = com.ffusion.csil.handlers.Banking.getFundTransactionTemplates(paramSecureUser, paramInt, paramHashMap);
    if (paramInt == 1007)
    {
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      Iterator localIterator = ((FundsTransactionTemplates)localObject).iterator();
      FundsTransactionTemplates localFundsTransactionTemplates = new FundsTransactionTemplates();
      while (localIterator.hasNext())
      {
        FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localIterator.next();
        ACHBatch localACHBatch = (ACHBatch)localFundsTransactionTemplate.getFundsTransaction();
        if ((ACH.checkACHSECEntitlement(paramSecureUser, localACHBatch, "ACH Template", paramHashMap)) || (ACH.checkACHSECEntitlement(paramSecureUser, localACHBatch, "ACH Payment Entry", paramHashMap))) {
          localFundsTransactionTemplates.add(localFundsTransactionTemplate);
        }
      }
      localObject = localFundsTransactionTemplates;
    }
    PerfLog.log(str, l, true);
    return localObject;
  }
  
  public static FundsTransactionTemplate addFundsTransactionTemplate(SecureUser paramSecureUser, FundsTransactionTemplate paramFundsTransactionTemplate, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.addFundsTransactionTemplate";
    debug(paramSecureUser, str1);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    paramFundsTransactionTemplate.getFundsTransaction().setTrackingID(str2);
    int i = 1325;
    Object[] arrayOfObject1 = null;
    LocalizableString localLocalizableString1 = null;
    if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof ACHBatch))
    {
      ACHBatch localACHBatch1 = (ACHBatch)paramFundsTransactionTemplate.getFundsTransaction();
      if (ACH.checkACHSECEntitlement(paramSecureUser, localACHBatch1, "ACH Template", paramHashMap))
      {
        localACHBatch1.setTemplateName(paramFundsTransactionTemplate.getTemplateName());
        if (!paramFundsTransactionTemplate.getSingleUseValue())
        {
          arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = localACHBatch1.getBatchScope();
          arrayOfObject1[1] = localACHBatch1.getTemplateName();
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_32", arrayOfObject1);
        }
        else if (localACHBatch1.getTaxForm() != null)
        {
          i = 1316;
          arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localACHBatch1.getName();
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_33", arrayOfObject1);
        }
        else
        {
          i = 1318;
          arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localACHBatch1.getName();
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_34", arrayOfObject1);
        }
      }
      else
      {
        throw new CSILException("The user is not entitled to add ACHBatch template ", 20001);
      }
    }
    else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Payment))
    {
      i = 2012;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramFundsTransactionTemplate.getTemplateName();
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_35", arrayOfObject1);
    }
    else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Transfer))
    {
      i = 1909;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramFundsTransactionTemplate.getTemplateName();
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_36", arrayOfObject1);
    }
    try
    {
      paramFundsTransactionTemplate = com.ffusion.csil.handlers.Banking.addFundsTransactionTemplate(paramSecureUser, paramFundsTransactionTemplate, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      Object[] arrayOfObject2 = null;
      LocalizableString localLocalizableString2 = null;
      if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof ACHBatch))
      {
        ACHBatch localACHBatch2 = (ACHBatch)paramFundsTransactionTemplate.getFundsTransaction();
        if (!paramFundsTransactionTemplate.getSingleUseValue())
        {
          arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = localACHBatch2.getBatchScope();
          arrayOfObject2[1] = localACHBatch2.getTemplateName();
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_37", arrayOfObject2);
        }
        else if (localACHBatch2.getTaxForm() != null)
        {
          i = 1316;
          arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localACHBatch2.getName();
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_38", arrayOfObject2);
        }
        else
        {
          i = 1318;
          arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localACHBatch2.getName();
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_39", arrayOfObject2);
        }
      }
      else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Payment))
      {
        i = 2012;
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramFundsTransactionTemplate.getTemplateName();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_40", arrayOfObject2);
      }
      else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Transfer))
      {
        i = 1909;
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramFundsTransactionTemplate.getTemplateName();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_41", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, i);
      throw localCSILException;
    }
    if (localLocalizableString1 != null) {
      audit(paramSecureUser, localLocalizableString1, str2, i);
    }
    PerfLog.log(str1, l, true);
    return paramFundsTransactionTemplate;
  }
  
  public static FundsTransactionTemplate modifyFundsTransactionTemplate(SecureUser paramSecureUser, FundsTransactionTemplate paramFundsTransactionTemplate, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.modifyFundsTransactionTemplate";
    debug(paramSecureUser, str1);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = paramFundsTransactionTemplate.getFundsTransaction().getTrackingID();
    if (str2 == null) {
      str2 = TrackingIDGenerator.GetNextID();
    }
    Object[] arrayOfObject1 = null;
    LocalizableString localLocalizableString1 = null;
    int i = 1326;
    if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof ACHBatch))
    {
      ACHBatch localACHBatch1 = (ACHBatch)paramFundsTransactionTemplate.getFundsTransaction();
      if (ACH.checkACHSECEntitlement(paramSecureUser, localACHBatch1, "ACH Template", paramHashMap))
      {
        if (!paramFundsTransactionTemplate.getSingleUseValue())
        {
          arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = localACHBatch1.getBatchScope();
          arrayOfObject1[1] = paramFundsTransactionTemplate.getTemplateName();
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_42", arrayOfObject1);
        }
        else if (localACHBatch1.getTaxForm() != null)
        {
          i = 1317;
          arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localACHBatch1.getName();
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_43", arrayOfObject1);
        }
        else
        {
          i = 1319;
          arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localACHBatch1.getName();
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_44", arrayOfObject1);
        }
      }
      else {
        throw new CSILException("The user is not entitled to modify ACHBatch template ", 20001);
      }
    }
    else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Payment))
    {
      i = 2013;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramFundsTransactionTemplate.getTemplateName();
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_45", arrayOfObject1);
    }
    else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Transfer))
    {
      i = 1910;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramFundsTransactionTemplate.getTemplateName();
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_46", arrayOfObject1);
    }
    try
    {
      paramFundsTransactionTemplate = com.ffusion.csil.handlers.Banking.modifyFundsTransactionTemplate(paramSecureUser, paramFundsTransactionTemplate, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      Object[] arrayOfObject2 = null;
      LocalizableString localLocalizableString2 = null;
      if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof ACHBatch))
      {
        ACHBatch localACHBatch2 = (ACHBatch)paramFundsTransactionTemplate.getFundsTransaction();
        if (!paramFundsTransactionTemplate.getSingleUseValue())
        {
          arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = localACHBatch2.getBatchScope();
          arrayOfObject2[1] = paramFundsTransactionTemplate.getTemplateName();
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_47", arrayOfObject2);
        }
        else if (localACHBatch2.getTaxForm() != null)
        {
          i = 1316;
          arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localACHBatch2.getName();
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_48", arrayOfObject2);
        }
        else
        {
          i = 1318;
          arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localACHBatch2.getName();
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_49", arrayOfObject2);
        }
      }
      else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Payment))
      {
        i = 2012;
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramFundsTransactionTemplate.getTemplateName();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_50", arrayOfObject2);
      }
      else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Transfer))
      {
        i = 1909;
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramFundsTransactionTemplate.getTemplateName();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_51", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, i);
      throw localCSILException;
    }
    if (localLocalizableString1 != null) {
      audit(paramSecureUser, localLocalizableString1, str2, i);
    }
    PerfLog.log(str1, l, true);
    return paramFundsTransactionTemplate;
  }
  
  public static void deleteFundsTransactionTemplate(SecureUser paramSecureUser, FundsTransactionTemplate paramFundsTransactionTemplate, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.deleteFundsTransactionTemplate";
    debug(paramSecureUser, str1);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = paramFundsTransactionTemplate.getFundsTransaction().getTrackingID();
    if (str2 == null) {
      str2 = TrackingIDGenerator.GetNextID();
    }
    Object[] arrayOfObject1 = null;
    LocalizableString localLocalizableString1 = null;
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 1327;
    if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof ACHBatch))
    {
      ACHBatch localACHBatch1 = (ACHBatch)paramFundsTransactionTemplate.getFundsTransaction();
      if (ACH.checkACHSECEntitlement(paramSecureUser, localACHBatch1, "ACH Template", paramHashMap))
      {
        if (!paramFundsTransactionTemplate.getSingleUseValue())
        {
          localStringBuffer.append("Delete ACH " + localACHBatch1.getBatchScope() + " Template named " + paramFundsTransactionTemplate.getTemplateName());
          arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = localACHBatch1.getBatchScope();
          arrayOfObject1[1] = paramFundsTransactionTemplate.getTemplateName();
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_52", arrayOfObject1);
        }
      }
      else {
        throw new CSILException("The user is not entitled to delete ACHBatch template ", 20001);
      }
    }
    else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Payment))
    {
      i = 2014;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramFundsTransactionTemplate.getTemplateName();
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_53", arrayOfObject1);
      localStringBuffer.append("Delete Payment Template named " + paramFundsTransactionTemplate.getTemplateName());
    }
    else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Transfer))
    {
      i = 1911;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramFundsTransactionTemplate.getTemplateName();
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_54", arrayOfObject1);
      localStringBuffer.append("Delete Transfer Template named " + paramFundsTransactionTemplate.getTemplateName());
    }
    try
    {
      com.ffusion.csil.handlers.Banking.deleteFundsTransactionTemplate(paramSecureUser, paramFundsTransactionTemplate, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      Object[] arrayOfObject2 = null;
      LocalizableString localLocalizableString2 = null;
      if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof ACHBatch))
      {
        ACHBatch localACHBatch2 = (ACHBatch)paramFundsTransactionTemplate.getFundsTransaction();
        if (!paramFundsTransactionTemplate.getSingleUseValue())
        {
          arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = localACHBatch2.getBatchScope();
          arrayOfObject2[1] = paramFundsTransactionTemplate.getTemplateName();
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_55", arrayOfObject2);
        }
      }
      else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Payment))
      {
        i = 2012;
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramFundsTransactionTemplate.getTemplateName();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_56", arrayOfObject2);
      }
      else if ((paramFundsTransactionTemplate.getFundsTransaction() instanceof Transfer))
      {
        i = 1909;
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramFundsTransactionTemplate.getTemplateName();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_57", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, i);
      throw localCSILException;
    }
    if (localLocalizableString1 != null) {
      audit(paramSecureUser, localLocalizableString1, str2, i);
    }
    PerfLog.log(str1, l, true);
  }
  
  public static com.ffusion.beans.accounts.Accounts getTransferAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.GetTransferAccounts";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    long l = System.currentTimeMillis();
    com.ffusion.beans.accounts.Accounts localAccounts1 = null;
    com.ffusion.beans.accounts.Accounts localAccounts2 = new com.ffusion.beans.accounts.Accounts();
    if (paramHashMap != null) {
      localAccounts1 = (com.ffusion.beans.accounts.Accounts)paramHashMap.get("ACCOUNTS");
    }
    if (localAccounts1 == null) {
      localAccounts1 = Accounts.getAccountsById(paramSecureUser, paramSecureUser.getBusinessID(), paramHashMap);
    }
    ExtTransferAccounts localExtTransferAccounts = null;
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, aHe)) {
      try
      {
        localExtTransferAccounts = ExternalTransferAdmin.getExtTransferAccounts(paramSecureUser, paramHashMap);
      }
      catch (CSILException localCSILException) {}catch (Exception localException)
      {
        DebugLog.log(str1 + ": Exception thrown retrieving external transfer accounts from BPW.");
      }
    }
    if (localAccounts1 != null)
    {
      localAccounts1.setFilter("All");
      int i = paramSecureUser.getBusinessID();
      Iterator localIterator = localAccounts1.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount1 = (Account)localIterator.next();
        Account localAccount2 = (Account)localAccount1.clone();
        MultiEntitlement localMultiEntitlement = new MultiEntitlement();
        localMultiEntitlement.setOperations(new String[] { "Transfers From" });
        localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localAccount1) });
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) != null) {
          localAccount2.removeFilter("TransferFrom");
        } else {
          localAccount2.setFilterable("TransferFrom");
        }
        String str2 = localAccount2.getCoreAccount();
        int j = 0;
        int k = 0;
        if ((paramSecureUser.getUserType() == 1) && (paramSecureUser.getBusinessID() == 0)) {
          k = 1;
        }
        if ("2".equals(localAccount2.getCoreAccount())) {
          j = 1;
        } else if ((k == 0) && ("0".equals(localAccount2.getCoreAccount()))) {
          j = 1;
        }
        if (j != 0)
        {
          localMultiEntitlement.setOperations(new String[] { "External Transfers To" });
          ExtTransferAccount localExtTransferAccount;
          if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) == null)
          {
            if (localExtTransferAccounts != null)
            {
              localExtTransferAccount = localExtTransferAccounts.getByAccountNumberAndTypeAndRTN(localAccount2.getNumber(), localAccount2.getTypeValue(), localAccount2.getRoutingNum());
              if (localExtTransferAccount != null)
              {
                localAccount2.put("EXTACCTID", localExtTransferAccount.getBpwID());
                localAccount2.put("ACCTBANKRTNTYPE", localExtTransferAccount.getAcctBankIDType());
                if (localExtTransferAccount.getVerifyStatusValue() != 5)
                {
                  localAccount2.setFilterable("ExternalTransferTo");
                }
                else
                {
                  localMultiEntitlement.setOperations(new String[] { "External Transfers To Unverified Account" });
                  if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) == null) {
                    localAccount2.setFilterable("ExternalTransferTo");
                  }
                }
              }
            }
          }
          else {
            localAccount2.removeFilter("ExternalTransferTo");
          }
          localMultiEntitlement.setOperations(new String[] { "External Transfers From" });
          if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) == null)
          {
            if (localExtTransferAccounts != null)
            {
              localExtTransferAccount = localExtTransferAccounts.getByAccountNumberAndTypeAndRTN(localAccount2.getNumber(), localAccount2.getTypeValue(), localAccount2.getRoutingNum());
              if (localExtTransferAccount != null)
              {
                localAccount2.put("EXTACCTID", localExtTransferAccount.getBpwID());
                localAccount2.put("ACCTBANKRTNTYPE", localExtTransferAccount.getAcctBankIDType());
                if (localExtTransferAccount.getVerifyStatusValue() != 5) {
                  localAccount2.setFilterable("ExternalTransferFrom");
                }
              }
            }
          }
          else {
            localAccount2.removeFilter("ExternalTransferFrom");
          }
        }
        else
        {
          localMultiEntitlement.setOperations(new String[] { "Transfers To" });
          if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) != null) {
            localAccount2.removeFilter("TransferTo");
          } else {
            localAccount2.setFilterable("TransferTo");
          }
        }
        if ((localAccount2.isFilterable("TransferTo")) || (localAccount2.isFilterable("TransferFrom")) || (localAccount2.isFilterable("ExternalTransferTo"))) {
          localAccounts2.add(localAccount2);
        }
      }
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localAccounts2;
  }
  
  private static ExtTransferAccounts jdMethod_int(SecureUser paramSecureUser, HashMap paramHashMap)
  {
    ExtTransferAccounts localExtTransferAccounts = null;
    String str = String.valueOf(paramSecureUser.getBusinessID());
    try
    {
      ExtTransferCompanies localExtTransferCompanies = ExternalTransferAdmin.getExtTransferCompanies(paramSecureUser, str, paramSecureUser.getBPWFIID(), paramHashMap);
      if ((localExtTransferCompanies != null) && (localExtTransferCompanies.size() > 0))
      {
        ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)localExtTransferCompanies.get(0);
        localExtTransferAccounts = ExternalTransferAdmin.getExtTransferAccounts(paramSecureUser, null, localExtTransferCompany, paramSecureUser.getBPWFIID(), paramHashMap);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log("WARNING: Error occured retrieving ExtTransferAccounts from BPW for Customer " + str);
    }
    return localExtTransferAccounts;
  }
  
  public static HashMap getTransactionTypes(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getTransactionTypes";
    long l = System.currentTimeMillis();
    HashMap localHashMap = com.ffusion.csil.handlers.Banking.getTransactionTypes(paramHashMap);
    PerfLog.log(str, l, true);
    return localHashMap;
  }
  
  public static HashMap getTransactionTypes(Locale paramLocale, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getTransactionTypes";
    long l = System.currentTimeMillis();
    HashMap localHashMap = com.ffusion.csil.handlers.Banking.getTransactionTypes(paramLocale, paramHashMap);
    PerfLog.log(str, l, true);
    return localHashMap;
  }
  
  public static ArrayList getTransactionTypeDescriptions(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getTransactionTypeDescriptions";
    long l = System.currentTimeMillis();
    ArrayList localArrayList = com.ffusion.csil.handlers.Banking.getTransactionTypeDescriptions(paramHashMap);
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static ArrayList getTransactionTypeDescriptions(Locale paramLocale, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getTransactionTypeDescriptions";
    long l = System.currentTimeMillis();
    ArrayList localArrayList = com.ffusion.csil.handlers.Banking.getTransactionTypeDescriptions(paramLocale, paramHashMap);
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static Transfers getApprovalTransfers(SecureUser paramSecureUser, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean1, boolean paramBoolean2, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.getApprovalTransfers";
    Transfers localTransfers = new Transfers();
    HashMap localHashMap = new HashMap();
    if (paramSecureUser != null) {
      localHashMap.put("BusinessID", new Integer(paramSecureUser.getBusinessID()));
    }
    String str2 = null;
    String str3 = null;
    if (paramDateTime1 != null) {
      if (paramBoolean2 == true)
      {
        str2 = "MM/dd/yyyy";
        str3 = DateFormatUtil.getFormatter(str2).format(paramDateTime1.getTime());
        localHashMap.put("StartDueDate", str3);
      }
      else
      {
        str2 = "MM/dd/yyyy HH:mm:ss";
        str3 = DateFormatUtil.getFormatter(str2).format(paramDateTime1.getTime());
        localHashMap.put("StartDate", str3);
      }
    }
    if (paramDateTime2 != null) {
      if (paramBoolean2 == true)
      {
        str3 = DateFormatUtil.getFormatter(str2).format(paramDateTime2.getTime());
        localHashMap.put("EndDueDate", str3);
      }
      else
      {
        str3 = DateFormatUtil.getFormatter(str2).format(paramDateTime2.getTime());
        localHashMap.put("EndDate", str3);
      }
    }
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      paramArrayOfString = new String[] { "Pending" };
    }
    localHashMap.put("ItemSubType", new Integer(1));
    ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, localHashMap, paramHashMap);
    Iterator localIterator;
    ApprovalsItem localApprovalsItem;
    Transfer localTransfer;
    DateTime localDateTime;
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      localIterator = localApprovalsStatuses.iterator();
      while (localIterator.hasNext()) {
        try
        {
          ApprovalsStatus localApprovalsStatus1 = (ApprovalsStatus)localIterator.next();
          localApprovalsItem = localApprovalsStatus1.getApprovalItem();
          localTransfer = (Transfer)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
          localDateTime = localApprovalsItem.getSubmissionDate();
          localDateTime.setFormat("yyyy-MM-dd hh:mm:ss");
          localTransfer.set("LogDate", localDateTime.toString());
          localTransfer.set("UserId", Integer.toString(localApprovalsItem.getSubmittingUserID()));
          if ("Rejected".equals(localApprovalsStatus1.getDecision())) {
            localTransfer.setStatus(12);
          } else {
            localTransfer.setStatus(8);
          }
          localTransfers.add(localTransfer);
        }
        catch (Exception localException1)
        {
          DebugLog.log(str1 + ": " + localException1.toString());
          DebugLog.log(str1 + ": Continuing ...");
        }
      }
    }
    if (paramBoolean1 == true)
    {
      localHashMap.put("ItemSubType", new Integer(2));
      localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, localHashMap, paramHashMap);
      if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
      {
        localIterator = localApprovalsStatuses.iterator();
        while (localIterator.hasNext()) {
          try
          {
            ApprovalsStatus localApprovalsStatus2 = (ApprovalsStatus)localIterator.next();
            localApprovalsItem = localApprovalsStatus2.getApprovalItem();
            localTransfer = (Transfer)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
            localDateTime = localApprovalsItem.getSubmissionDate();
            localDateTime.setFormat("yyyy-MM-dd hh:mm:ss");
            localTransfer.set("LogDate", localDateTime.toString());
            localTransfer.set("UserId", Integer.toString(localApprovalsItem.getSubmittingUserID()));
            if ("Rejected".equals(localApprovalsStatus2.getDecision())) {
              localTransfer.setStatus(12);
            } else {
              localTransfer.setStatus(8);
            }
            localTransfers.add(localTransfer);
          }
          catch (Exception localException2)
          {
            DebugLog.log(str1 + ": " + localException2.toString());
            DebugLog.log(str1 + ": Continuing ...");
          }
        }
      }
    }
    return localTransfers;
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, Entitlement paramEntitlement, String paramString)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, paramEntitlement))
    {
      debug("User:" + paramSecureUser.getUserName() + " not entitled for " + paramString);
      LocalizableString localLocalizableString;
      if (paramEntitlement.equals(aGj))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_59", null);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      }
      else if (paramEntitlement.equals(aHn))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_60", null);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      }
      else if (paramEntitlement.equals(aHe))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_61", null);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      }
      else if (paramEntitlement.equals(aGn))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_66", null);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      }
      throw new CSILException(paramString, 20001);
    }
  }
  
  static Entitlement jdMethod_for(Transfer paramTransfer, boolean paramBoolean)
  {
    String str = "Transfers From";
    Account localAccount = paramTransfer.getFromAccount();
    if (!paramBoolean)
    {
      str = "Transfers To";
      localAccount = paramTransfer.getToAccount();
    }
    Entitlement localEntitlement = new Entitlement(str, "Account", EntitlementsUtil.getEntitlementObjectId(localAccount));
    return localEntitlement;
  }
  
  public static Limits checkLimitsAdd(SecureUser paramSecureUser, Transfer paramTransfer)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    BigDecimal localBigDecimal = paramTransfer.getAmountValue().getAmountValue();
    Date localDate = paramTransfer.getDateValue().getTime();
    Limits localLimits1 = Entitlements.checkLimitsAdd(localEntitlementGroupMember, jdMethod_for(paramTransfer, true), localBigDecimal, localDate);
    Limits localLimits2 = Entitlements.checkLimitsAdd(localEntitlementGroupMember, jdMethod_for(paramTransfer, false), localBigDecimal, localDate);
    Limits localLimits3 = Entitlements.checkLimitsAdd(localEntitlementGroupMember, aGq, localBigDecimal, localDate);
    if (localLimits1 == null) {
      localLimits1 = new Limits();
    }
    if (localLimits2 != null) {
      localLimits1.addAll(localLimits2);
    }
    if (localLimits3 != null) {
      localLimits1.addAll(localLimits3);
    }
    return localLimits1;
  }
  
  public static Limits checkLimitsEdit(SecureUser paramSecureUser, Transfer paramTransfer1, Transfer paramTransfer2)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Limits localLimits = null;
    if (paramTransfer2 != null)
    {
      checkLimitsDelete(paramSecureUser, paramTransfer2);
      localLimits = checkLimitsAdd(paramSecureUser, paramTransfer1);
    }
    else
    {
      localLimits = checkLimitsAdd(paramSecureUser, paramTransfer1);
    }
    return localLimits;
  }
  
  public static void checkLimitsDelete(SecureUser paramSecureUser, Transfer paramTransfer)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    checkLimitsDelete(localEntitlementGroupMember, paramTransfer);
  }
  
  public static void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Transfer paramTransfer)
    throws CSILException
  {
    BigDecimal localBigDecimal = paramTransfer.getAmountValue().getAmountValue();
    Date localDate = paramTransfer.getDateValue().getTime();
    Entitlements.checkLimitsDelete(paramEntitlementGroupMember, jdMethod_for(paramTransfer, true), localBigDecimal, localDate);
    Entitlements.checkLimitsDelete(paramEntitlementGroupMember, jdMethod_for(paramTransfer, false), localBigDecimal, localDate);
    Entitlements.checkLimitsDelete(paramEntitlementGroupMember, aGq, localBigDecimal, localDate);
  }
  
  public static String getSrvrTIdByTrackingId(SecureUser paramSecureUser, String paramString)
  {
    String str = null;
    if ((paramString != null) && (paramString.length() > 0))
    {
      ReportLogRecords localReportLogRecords = ReportAuditAdapter.getAuditHistoryByTrackingId(paramSecureUser, paramString);
      if ((localReportLogRecords != null) && (localReportLogRecords.size() > 0)) {
        for (int i = 0; i < localReportLogRecords.size(); i++)
        {
          ReportLogRecord localReportLogRecord = (ReportLogRecord)localReportLogRecords.get(i);
          str = localReportLogRecord.getSrvrTid();
          if ((str != null) && (str.length() > 0)) {
            break;
          }
        }
      }
    }
    return str;
  }
  
  public static Date getEffectiveDate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getEffectiveDate";
    debug(paramSecureUser, str);
    return com.ffusion.csil.handlers.Banking.getEffectiveDate(paramSecureUser, paramTransfer, paramHashMap);
  }
  
  public static Transfers getPagedTransfers(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.getPagedTransfers";
    debug(paramSecureUser, str1);
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aGj))
    {
      localObject = com.ffusion.csil.handlers.Banking.getPagedTransfers(paramSecureUser, paramPagingContext, paramHashMap);
      String str2 = (String)paramHashMap.get("TRANSFER_STATUS");
      if ((str2 != null) && (str2.equals("TRANSFER_STATUS_APPROVAL")))
      {
        Iterator localIterator = ((Transfers)localObject).iterator();
        while (localIterator.hasNext())
        {
          Transfer localTransfer = (Transfer)localIterator.next();
          Util.insertApprovalInfo(localTransfer, paramSecureUser, null, paramHashMap);
        }
      }
      return localObject;
    }
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_59", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static FundsTransactions getPagedFundsTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.getPagedFundsTransactions";
    debug(paramSecureUser, str1);
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aGj))
    {
      localObject = com.ffusion.csil.handlers.Banking.getPagedFundsTransactions(paramSecureUser, paramPagingContext, paramHashMap);
      String str2 = (String)paramHashMap.get("TRANSFER_STATUS");
      if ((str2 != null) && (str2.equals("TRANSFER_STATUS_APPROVAL")))
      {
        Iterator localIterator1 = ((FundsTransactions)localObject).iterator();
        while (localIterator1.hasNext())
        {
          FundsTransaction localFundsTransaction = (FundsTransaction)localIterator1.next();
          if ((localFundsTransaction instanceof Transfer))
          {
            Util.insertApprovalInfo((Transfer)localFundsTransaction, paramSecureUser, null, paramHashMap);
          }
          else if ((localFundsTransaction instanceof TransferBatch))
          {
            TransferBatch localTransferBatch = (TransferBatch)localFundsTransaction;
            Iterator localIterator2 = localTransferBatch.getTransfers().iterator();
            while (localIterator2.hasNext()) {
              Util.insertApprovalInfo((Transfer)localIterator2.next(), paramSecureUser, null, paramHashMap);
            }
          }
        }
      }
      return localObject;
    }
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_59", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ArrayList convertTransTypesToBAITypes(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if ((paramString == null) || (paramString.length() <= 0) || (paramString.equalsIgnoreCase("AllTransactionTypes"))) {
      return new ArrayList(0);
    }
    String str1 = "com.ffusion.csil.core.Banking.convertTransTypesToBAITypes";
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    HashSet localHashSet = new HashSet();
    StringBuffer localStringBuffer = new StringBuffer(paramString.length());
    while (localStringTokenizer.hasMoreTokens())
    {
      localObject1 = localStringTokenizer.nextToken();
      if ("AllTransactionTypes".equals(localObject1)) {
        return new ArrayList(0);
      }
      Object localObject2;
      int j;
      Object localObject3;
      if (((String)localObject1).equals("AllCreditTransactions"))
      {
        localObject2 = DataConsolidator.getBAITypeCodesForTransaction(1, paramHashMap);
        if (localObject2 != null) {
          for (j = 0; j < ((ArrayList)localObject2).size(); j++)
          {
            localObject3 = (String)((ArrayList)localObject2).get(j);
            if (!localHashSet.contains(localObject3)) {
              localHashSet.add(localObject3);
            }
          }
        }
      }
      else if (((String)localObject1).equals("AllDebitTransactions"))
      {
        localObject2 = DataConsolidator.getBAITypeCodesForTransaction(2, paramHashMap);
        if (localObject2 != null) {
          for (j = 0; j < ((ArrayList)localObject2).size(); j++)
          {
            localObject3 = (String)((ArrayList)localObject2).get(j);
            if (!localHashSet.contains(localObject3)) {
              localHashSet.add(localObject3);
            }
          }
        }
      }
      else
      {
        String str3;
        if (((String)localObject1).startsWith("CustTransGrp_"))
        {
          localObject2 = ((String)localObject1).substring("CustTransGrp_".length());
          String str2 = Business.getTypeCodesForGroup(paramSecureUser, paramSecureUser.getBusinessID(), (String)localObject2, paramHashMap);
          localObject3 = new StringTokenizer(str2, ",");
          while (((StringTokenizer)localObject3).hasMoreTokens())
          {
            str3 = ((StringTokenizer)localObject3).nextToken();
            if (!localHashSet.contains(str3)) {
              localHashSet.add(str3);
            }
          }
        }
        else if (!"------------------------------".equals(localObject1))
        {
          int i = 0;
          try
          {
            i = Integer.parseInt((String)localObject1);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            localObject3 = new CSILException(1067, localNumberFormatException, str1, "Transaction code is not an integer");
            DebugLog.throwing(str1, (Throwable)localObject3);
            throw ((Throwable)localObject3);
          }
          ArrayList localArrayList = DataConsolidator.getBAITypeCodesByTransactionType(i);
          if (localArrayList != null) {
            for (int k = 0; k < localArrayList.size(); k++)
            {
              str3 = (String)localArrayList.get(k);
              if (!localHashSet.contains(str3)) {
                localHashSet.add(str3);
              }
            }
          }
        }
      }
    }
    Object localObject1 = new ArrayList(localHashSet);
    return localObject1;
  }
  
  public static HashMap getTransactionTypes(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    HashMap localHashMap = com.ffusion.csil.handlers.Banking.getTransactionTypes(paramInt, paramHashMap);
    return localHashMap;
  }
  
  public static TransactionTypeInfo getTransactionTypeInfo(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    TransactionTypeInfo localTransactionTypeInfo = com.ffusion.csil.handlers.Banking.getTransactionTypeInfo(paramInt, paramHashMap);
    return localTransactionTypeInfo;
  }
  
  private static void jdMethod_for(Transfer paramTransfer, SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = (Limits)paramTransfer.get("FAILED_LIMITS");
    if ((localObject != null) && (!((Limits)localObject).isEmpty()))
    {
      Limit localLimit1 = (Limit)((Limits)localObject).get(0);
      if (localLimit1.getGroupId() == -1)
      {
        EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
        Limits localLimits1 = Entitlements.getCumulativeLimits(localEntitlementGroupMember);
        Limits localLimits2 = new Limits();
        Iterator localIterator = ((Limits)localObject).iterator();
        while (localIterator.hasNext())
        {
          localLimit1 = (Limit)localIterator.next();
          Limit localLimit2 = localLimits1.getLimitById(localLimit1.getLimitId());
          if (localLimit2 != null) {
            localLimits2.add(localLimit2);
          }
        }
        localObject = localLimits2;
      }
      if (!((Limits)localObject).isEmpty()) {
        paramHashMap.put("ExceededLimits", localObject);
      }
    }
  }
  
  private static boolean jdMethod_new(Transfer paramTransfer)
  {
    if (!paramTransfer.getFromAccount().getCurrencyCode().equals(paramTransfer.getToAccount().getCurrencyCode())) {
      return true;
    }
    return (paramTransfer.getToAmtCurrency() != null) && (paramTransfer.getToAmtCurrency().length() > 0) && (!paramTransfer.getAmtCurrency().equals(paramTransfer.getToAmtCurrency()));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Banking
 * JD-Core Version:    0.7.0.1
 */