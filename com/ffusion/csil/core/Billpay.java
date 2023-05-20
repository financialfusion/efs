package com.ffusion.csil.core;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeRoute;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
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
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.ReportAuditAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class Billpay
  extends Initialize
{
  private static Entitlement as7 = new Entitlement("Payments", null, null);
  private static Entitlement asR = new Entitlement("Payees", null, null);
  private static Entitlement as2 = new Entitlement("Multi-Currency Payments", null, null);
  private static final String atz = "com.ffusion.util.logging.audit.billpay";
  private static final String asY = "AuditMessage_1";
  private static final String atp = "AuditMessage_2";
  private static final String asW = "AuditMessage_3";
  private static final String att = "AuditMessage_4";
  private static final String asZ = "AuditMessage_5";
  private static final String ata = "AuditMessage_6";
  private static final String atx = "AuditMessage_7";
  private static final String atw = "AuditMessage_8";
  private static final String as5 = "AuditMessage_9";
  private static final String ats = "AuditMessage_10";
  private static final String ath = "AuditMessage_11";
  private static final String atm = "AuditMessage_12";
  private static final String as8 = "AuditMessage_13";
  private static final String atd = "AuditMessage_14";
  private static final String atk = "AuditMessage_15";
  private static final String atl = "AuditMessage_16";
  private static final String as0 = "AuditMessage_17";
  private static final String asU = "AuditMessage_18";
  private static final String ato = "AuditMessage_19";
  private static final String atr = "AuditMessage_20";
  private static final String atv = "AuditMessage_21";
  private static final String as1 = "AuditMessage_22";
  private static final String asT = "AuditMessage_23";
  private static final String atf = "AuditMessage_24";
  private static final String ate = "AuditMessage_25";
  private static final String asS = "AuditMessage_26";
  private static final String as6 = "AuditMessage_27";
  private static final String atj = "AuditMessage_28";
  private static final String aty = "AuditMessage_29";
  private static final String atb = "AuditMessage_30";
  private static final String ati = "AuditMessage_31";
  private static final String atu = "AuditMessage_32";
  private static final String asX = "AuditMessage_33";
  private static final String as4 = "AuditMessage_34";
  private static final String atc = "AuditMessage_35";
  private static final String atn = "AuditMessage_36";
  private static final String atg = "AuditMessage_37";
  private static final String as3 = "AuditMessage_38";
  private static final String as9 = "AuditMessage_39";
  private static final String asV = "AuditMessage_40";
  private static final String atq = "AuditMessage_41";
  public static final String PROFILEID_KEY = "PROFILEID_KEY";
  public static Currency defaultCurrency = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Billpay.initialize");
    com.ffusion.csil.handlers.Billpay.initialize(paramHashMap);
    defaultCurrency = com.ffusion.csil.handlers.Billpay.getDefaultCurrency();
  }
  
  public static Object getService(String paramString)
  {
    return com.ffusion.csil.handlers.Billpay.getService(paramString);
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.SignOn";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      long l = System.currentTimeMillis();
      paramSecureUser = com.ffusion.csil.handlers.Billpay.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramSecureUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static SecureUser signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.SignOff";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      long l = System.currentTimeMillis();
      paramSecureUser = com.ffusion.csil.handlers.Billpay.signOff(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramSecureUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static SecureUser changePIN(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.ChangePIN";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      paramSecureUser = com.ffusion.csil.handlers.Billpay.changePIN(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str1, l, true);
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_1", null);
      audit(paramSecureUser, localLocalizableString2, str2, 2000);
      debug(paramSecureUser, str1);
      return paramSecureUser;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.GetAccounts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      long l = System.currentTimeMillis();
      Accounts localAccounts = com.ffusion.csil.handlers.Billpay.getAccounts(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAccounts;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Payees getPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.GetPayees";
    if ((!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), asR)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7)))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_23", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    Payees localPayees = com.ffusion.csil.handlers.Billpay.getPayees(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localPayees;
  }
  
  public static Payees getGlobalPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.GetGlobalPayees";
    if ((!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), asR)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7)))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_23", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as2))
    {
      localObject = getDefaultCurrency();
      String str2 = paramSecureUser.getBaseCurrency();
      if (localObject != null) {
        str2 = ((Currency)localObject).getCurrencyCode();
      }
      paramHashMap.put("DefaultCurrency", str2);
    }
    Object localObject = com.ffusion.csil.handlers.Billpay.getGlobalPayees(paramSecureUser, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localObject;
  }
  
  public static Payees addPayees(SecureUser paramSecureUser, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.AddPayees";
    Object localObject1 = null;
    if ((paramSecureUser.getUserType() == 2) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), asR)))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      Object localObject2;
      Object localObject3;
      Object localObject4;
      try
      {
        localObject1 = paramPayees.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          Payee localPayee1 = (Payee)((Iterator)localObject1).next();
          localObject2 = TrackingIDGenerator.GetNextID();
          localPayee1.setTrackingID((String)localObject2);
        }
        paramPayees = com.ffusion.csil.handlers.Billpay.addPayees(paramSecureUser, paramPayees, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localObject1 = paramPayees.listIterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Payee)((Iterator)localObject1).next();
          localObject3 = new Object[1];
          localObject3[0] = ((Payee)localObject2).getName();
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_2", (Object[])localObject3);
          audit(paramSecureUser, (ILocalizable)localObject4, ((Payee)localObject2).getTrackingID(), 2001);
        }
        throw localCSILException;
      }
      PerfLog.log(str, l, true);
      localObject1 = paramPayees.listIterator();
      while (((Iterator)localObject1).hasNext())
      {
        Payee localPayee2 = (Payee)((Iterator)localObject1).next();
        localObject2 = new Object[1];
        localObject2[0] = localPayee2.getName();
        localObject3 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_3", (Object[])localObject2);
        audit(paramSecureUser, (ILocalizable)localObject3, localPayee2.getTrackingID(), 2001);
        localObject4 = new HistoryTracker(paramSecureUser, 10, localPayee2.getID(), localPayee2.getTrackingID());
        localPayee2.logCreation((HistoryTracker)localObject4, ((HistoryTracker)localObject4).buildLocalizableComment(1));
        try
        {
          HistoryAdapter.addHistory(((HistoryTracker)localObject4).getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localProfileException.toString());
        }
      }
      debug(paramSecureUser, str);
      return paramPayees;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_24", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Payee modifyPayee(SecureUser paramSecureUser, Payee paramPayee1, Payee paramPayee2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.ModifyPayee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), asR))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramPayee1.getTrackingID();
      try
      {
        paramPayee1 = com.ffusion.csil.handlers.Billpay.modifyPayee(paramSecureUser, paramPayee1, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localObject1 = new Object[1];
        localObject1[0] = paramPayee1.getName();
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_4", (Object[])localObject1);
        audit(paramSecureUser, (ILocalizable)localObject2, str2, 2002);
        throw localCSILException;
      }
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramPayee1.getName();
      Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_5", arrayOfObject);
      audit(paramSecureUser, (ILocalizable)localObject1, str2, 2002);
      Object localObject2 = new HistoryTracker(paramSecureUser, 10, paramPayee1.getID(), str2);
      paramPayee1.logChanges((HistoryTracker)localObject2, paramPayee2, ((HistoryTracker)localObject2).buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(((HistoryTracker)localObject2).getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
      return paramPayee1;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_25", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Payee deletePayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.DeletePayee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), asR))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramPayee.getTrackingID();
      try
      {
        paramPayee = com.ffusion.csil.handlers.Billpay.deletePayee(paramSecureUser, paramPayee, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localObject1 = new Object[1];
        localObject1[0] = paramPayee.getName();
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_6", (Object[])localObject1);
        audit(paramSecureUser, (ILocalizable)localObject2, str2, 2003);
        throw localCSILException;
      }
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramPayee.getName();
      Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_7", arrayOfObject);
      audit(paramSecureUser, (ILocalizable)localObject1, str2, 2003);
      Object localObject2 = new HistoryTracker(paramSecureUser, 10, paramPayee.getID(), str2);
      paramPayee.logDeletion((HistoryTracker)localObject2, ((HistoryTracker)localObject2).buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(((HistoryTracker)localObject2).getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
      return paramPayee;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_26", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Payments sendPayments(SecureUser paramSecureUser, Payments paramPayments, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.SendPayments";
    long l = System.currentTimeMillis();
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      jdMethod_for(paramSecureUser, paramPayments, str);
      Iterator localIterator = paramPayments.iterator();
      localObject1 = null;
      while (localIterator.hasNext())
      {
        localObject1 = (Payment)localIterator.next();
        localObject2 = ((Payment)localObject1).getTrackingID();
        if (localObject2 == null) {
          localObject2 = TrackingIDGenerator.GetNextID();
        }
        ((Payment)localObject1).setTrackingID((String)localObject2);
        ((Payment)localObject1).setSubmittedBy(paramSecureUser.getProfileID());
        if (paramSecureUser.getBusinessID() == 0) {
          ((Payment)localObject1).setCustomerID(paramSecureUser.getPrimaryUserID());
        } else {
          ((Payment)localObject1).setCustomerID(paramSecureUser.getBusinessID());
        }
        ((Payment)localObject1).setFIID(paramSecureUser.getBPWFIID());
      }
      if (!paramPayments.isEmpty())
      {
        paramPayments = com.ffusion.csil.handlers.Billpay.sendPayments(paramSecureUser, paramPayments, paramHashMap);
        localObject2 = paramPayments.listIterator();
        while (((ListIterator)localObject2).hasNext())
        {
          localObject1 = (Payment)((ListIterator)localObject2).next();
          localObject3 = ((Payment)localObject1).getTrackingID();
          localObject4 = new Object[2];
          localObject4[0] = ((Payment)localObject1).getAmountValue().toString();
          localObject4[1] = ((Payment)localObject1).getAccount().getDisplayText();
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_8", (Object[])localObject4);
          audit(((Payment)localObject1).getAuditRecord(paramSecureUser, localLocalizableString, 2004, "ADDED"));
          HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, ((Payment)localObject1).getID(), (String)localObject3);
          ((Payment)localObject1).logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
          try
          {
            HistoryAdapter.addHistory(localHistoryTracker.getHistories());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localProfileException.toString());
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject1 = paramPayments.listIterator();
      while (((ListIterator)localObject1).hasNext())
      {
        localObject2 = (Payment)((ListIterator)localObject1).next();
        localObject3 = new Object[2];
        localObject3[0] = ((Payment)localObject2).getAmountValue().toString();
        localObject3[1] = ((Payment)localObject2).getAccount().getDisplayText();
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_9", (Object[])localObject3);
        audit(((Payment)localObject2).getAuditRecord(paramSecureUser, (ILocalizable)localObject4, 2004, "FAILED"));
      }
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramPayments, paramSecureUser, paramHashMap);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramPayments;
  }
  
  public static Payment modifyPayment(SecureUser paramSecureUser, Payment paramPayment1, Payment paramPayment2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.ModifyPayment";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramPayment1, str1);
    long l = System.currentTimeMillis();
    String str2 = paramPayment1.getTrackingID();
    if (str2 == null)
    {
      str2 = TrackingIDGenerator.GetNextID();
      paramPayment1.setTrackingID(str2);
    }
    try
    {
      paramPayment1 = com.ffusion.csil.handlers.Billpay.modifyPayment(paramSecureUser, paramPayment1, paramPayment2, paramHashMap);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramPayment1.getAmountValue().toString();
      arrayOfObject[1] = paramPayment1.getAccount().getDisplayText();
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_10", arrayOfObject);
      audit(paramPayment1.getAuditRecord(paramSecureUser, (ILocalizable)localObject1, 2005, "MODIFIED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = new Object[2];
      localObject1[0] = paramPayment1.getAmountValue().toString();
      localObject1[1] = paramPayment1.getAccount().getDisplayText();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_11", (Object[])localObject1);
      audit(paramPayment1.getAuditRecord(paramSecureUser, localLocalizableString, 2005, "FAILED"));
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramPayment1, paramSecureUser, paramHashMap);
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, paramPayment1.getID(), str2);
    paramPayment1.logChanges(localHistoryTracker, paramPayment2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    return paramPayment1;
  }
  
  public static Payments cancelPayments(SecureUser paramSecureUser, Payments paramPayments, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.CancelPayments";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramPayments, str);
    long l = System.currentTimeMillis();
    try
    {
      if (paramPayments.size() > 0) {
        paramPayments = com.ffusion.csil.handlers.Billpay.cancelPayments(paramSecureUser, paramPayments, paramHashMap);
      }
      ListIterator localListIterator = paramPayments.listIterator();
      while (localListIterator.hasNext())
      {
        localObject1 = (Payment)localListIterator.next();
        localObject2 = ((Payment)localObject1).getTrackingID();
        arrayOfObject = new Object[2];
        arrayOfObject[0] = ((Payment)localObject1).getAmountValue().toString();
        arrayOfObject[1] = ((Payment)localObject1).getAccount().getDisplayText();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_12", arrayOfObject);
        audit(((Payment)localObject1).getAuditRecord(paramSecureUser, localLocalizableString, 2006, "DELETED"));
        HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, ((Payment)localObject1).getID(), (String)localObject2);
        ((Payment)localObject1).logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localProfileException.toString());
        }
      }
    }
    catch (CSILException localCSILException)
    {
      Object localObject2;
      Object[] arrayOfObject;
      LocalizableString localLocalizableString;
      Object localObject1 = paramPayments.listIterator();
      while (((ListIterator)localObject1).hasNext())
      {
        localObject2 = (Payment)((ListIterator)localObject1).next();
        arrayOfObject = new Object[2];
        arrayOfObject[0] = ((Payment)localObject2).getAmountValue().toString();
        arrayOfObject[1] = ((Payment)localObject2).getAccount().getDisplayText();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_13", arrayOfObject);
        audit(((Payment)localObject2).getAuditRecord(paramSecureUser, localLocalizableString, 2006, "FAILED"));
      }
      throw localCSILException;
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramPayments;
  }
  
  public static Object[] getPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.GetPayments";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      long l = System.currentTimeMillis();
      Object[] arrayOfObject = com.ffusion.csil.handlers.Billpay.getPayments(paramSecureUser, paramAccounts, paramPayees, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfObject;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static RecPayment getRecPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getRecPaymentByID";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      long l = System.currentTimeMillis();
      RecPayment localRecPayment = com.ffusion.csil.handlers.Billpay.getRecPaymentByID(paramSecureUser, paramString, paramAccounts, paramPayees, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localRecPayment;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Payment getPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getPaymentByID";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      long l = System.currentTimeMillis();
      Payment localPayment = com.ffusion.csil.handlers.Billpay.getPaymentByID(paramSecureUser, paramString, paramAccounts, paramPayees, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localPayment;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static RecPayments sendRecPayments(SecureUser paramSecureUser, RecPayments paramRecPayments, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.SendRecPayments";
    long l = System.currentTimeMillis();
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      jdMethod_for(paramSecureUser, paramRecPayments, str);
      RecPayment localRecPayment = null;
      localObject1 = paramRecPayments.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localRecPayment = (RecPayment)((Iterator)localObject1).next();
        localObject2 = localRecPayment.getTrackingID();
        if (localObject2 == null) {
          localObject2 = TrackingIDGenerator.GetNextID();
        }
        localRecPayment.setTrackingID((String)localObject2);
        if (paramSecureUser.getBusinessID() == 0) {
          localRecPayment.setCustomerID(paramSecureUser.getPrimaryUserID());
        } else {
          localRecPayment.setCustomerID(paramSecureUser.getBusinessID());
        }
        localRecPayment.setFIID(paramSecureUser.getBPWFIID());
        localRecPayment.setSubmittedBy(paramSecureUser.getProfileID());
      }
      if (paramRecPayments.size() != 0)
      {
        paramRecPayments = com.ffusion.csil.handlers.Billpay.sendRecPayments(paramSecureUser, paramRecPayments, paramHashMap);
        localObject2 = paramRecPayments.listIterator();
        while (((ListIterator)localObject2).hasNext())
        {
          localRecPayment = (RecPayment)((ListIterator)localObject2).next();
          localObject3 = localRecPayment.getTrackingID();
          localObject4 = new Object[2];
          localObject4[0] = localRecPayment.getAmountValue().toString();
          localObject4[1] = localRecPayment.getAccount().getDisplayText();
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_14", (Object[])localObject4);
          audit(localRecPayment.getAuditRecord(paramSecureUser, localLocalizableString, 2007, "ADDED"));
          HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, localRecPayment.getID(), (String)localObject3);
          localRecPayment.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
          try
          {
            HistoryAdapter.addHistory(localHistoryTracker.getHistories());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localProfileException.toString());
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject1 = paramRecPayments.listIterator();
      while (((ListIterator)localObject1).hasNext())
      {
        localObject2 = (RecPayment)((ListIterator)localObject1).next();
        localObject3 = new Object[2];
        localObject3[0] = ((RecPayment)localObject2).getAmountValue().toString();
        localObject3[1] = ((RecPayment)localObject2).getAccount().getDisplayText();
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_15", (Object[])localObject3);
        audit(((RecPayment)localObject2).getAuditRecord(paramSecureUser, (ILocalizable)localObject4, 2007, "FAILED"));
      }
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramRecPayments, paramSecureUser, paramHashMap);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramRecPayments;
  }
  
  public static RecPayment deleteRecPayment(SecureUser paramSecureUser, RecPayment paramRecPayment, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.DeleteRecPayment";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramRecPayment, str1);
    long l = System.currentTimeMillis();
    String str2 = paramRecPayment.getTrackingID();
    try
    {
      com.ffusion.csil.handlers.Billpay.deleteRecPayment(paramSecureUser, paramRecPayment, paramHashMap);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramRecPayment.getAmountValue().toString();
      arrayOfObject[1] = paramRecPayment.getAccount().getDisplayText();
      localObject = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_16", arrayOfObject);
      audit(paramRecPayment.getAuditRecord(paramSecureUser, (ILocalizable)localObject, 2008, "DELETED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject = new Object[2];
      localObject[0] = paramRecPayment.getAmountValue().toString();
      localObject[1] = paramRecPayment.getAccount().getDisplayText();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_17", (Object[])localObject);
      audit(paramRecPayment.getAuditRecord(paramSecureUser, localLocalizableString, 2008, "FAILED"));
      throw localCSILException;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, paramRecPayment.getID(), str2);
    paramRecPayment.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return paramRecPayment;
  }
  
  public static RecPayment modifyRecPayment(SecureUser paramSecureUser, RecPayment paramRecPayment1, RecPayment paramRecPayment2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.ModifyRecPayment";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    jdMethod_for(paramSecureUser, paramRecPayment1, str1);
    long l = System.currentTimeMillis();
    String str2 = paramRecPayment2.getTrackingID();
    if (str2 == null) {
      str2 = TrackingIDGenerator.GetNextID();
    }
    paramRecPayment1.setTrackingID(str2);
    try
    {
      paramRecPayment1 = com.ffusion.csil.handlers.Billpay.modifyRecPayment(paramSecureUser, paramRecPayment1, paramRecPayment2, paramHashMap);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramRecPayment1.getAmountValue().toString();
      arrayOfObject[1] = paramRecPayment1.getAccount().getDisplayText();
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_18", arrayOfObject);
      audit(paramRecPayment1.getAuditRecord(paramSecureUser, (ILocalizable)localObject1, 2009, "MODIFIED"));
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = new Object[2];
      localObject1[0] = paramRecPayment1.getAmountValue().toString();
      localObject1[1] = paramRecPayment1.getAccount().getDisplayText();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_19", (Object[])localObject1);
      audit(paramRecPayment1.getAuditRecord(paramSecureUser, localLocalizableString, 2009, "FAILED"));
      throw localCSILException;
    }
    finally
    {
      jdMethod_for(paramRecPayment1, paramSecureUser, paramHashMap);
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, paramRecPayment1.getID(), str2);
    paramRecPayment1.logChanges(localHistoryTracker, paramRecPayment2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    return paramRecPayment1;
  }
  
  public static RecPayment skipRecPayment(SecureUser paramSecureUser, Account paramAccount, RecPayment paramRecPayment, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.SkipRecPayment";
    jdMethod_for(paramSecureUser, paramRecPayment, str1);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = paramRecPayment.getTrackingID();
    RecPayment localRecPayment = com.ffusion.csil.handlers.Billpay.skipRecPayment(paramSecureUser, paramAccount, paramRecPayment, paramHashMap);
    PerfLog.log(str1, l, true);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRecPayment.getID();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_20", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, str2, 2010, paramRecPayment.getAmountValue(), paramRecPayment.getID());
    debug(paramSecureUser, str1);
    return localRecPayment;
  }
  
  public static Payments getPagedPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getPagedPendingPayments";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Billpay.getPagedPendingPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localPayments;
  }
  
  public static Payments getNextPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getNextPendingPayments";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Billpay.getNextPendingPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localPayments;
  }
  
  public static Payments getPreviousPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getPreviousPendingPayments";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Billpay.getPreviousPendingPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localPayments;
  }
  
  public static Payments getPagedCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getPagedCompletedPayments";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Billpay.getPagedCompletedPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localPayments;
  }
  
  public static Payments getNextCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getNextCompletedPayments";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Billpay.getNextCompletedPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localPayments;
  }
  
  public static Payments getPreviousCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getPreviousCompletedPayments";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Billpay.getPreviousCompletedPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localPayments;
  }
  
  public static Accounts getPaymentAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.GetPaymentAccounts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      long l = System.currentTimeMillis();
      Accounts localAccounts1 = null;
      Accounts localAccounts2 = new Accounts();
      if (paramHashMap != null) {
        localAccounts1 = (Accounts)paramHashMap.get("ACCOUNTS");
      }
      if (localAccounts1 == null) {
        localAccounts1 = AccountHandler.getAccounts(paramSecureUser, paramHashMap);
      }
      if (localAccounts1 != null)
      {
        localAccounts1.setFilter("All");
        Iterator localIterator = localAccounts1.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          Entitlement localEntitlement = new Entitlement("Payments", "Account", EntitlementsUtil.getEntitlementObjectId(localAccount));
          if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement)) {
            localAccounts2.add(localAccount);
          }
        }
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAccounts2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.getReportData";
    debug(paramSecureUser, str1);
    jdMethod_for(paramSecureUser, as7, str1);
    String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    long l = System.currentTimeMillis();
    Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    IReportResult localIReportResult = com.ffusion.csil.handlers.Billpay.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    String str3 = paramReportCriteria.getReportOptions().getProperty("TITLE");
    if ((str3 == null) || (str3.length() == 0)) {
      str3 = ReportConsts.getReportName(str2, Locale.getDefault());
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = str3;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_21", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 2011);
    PerfLog.log(str1, l, true);
    return localIReportResult;
  }
  
  public static Payments getApprovalPayments(SecureUser paramSecureUser, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean1, boolean paramBoolean2, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Billpay.getApprovalPayments";
    Payments localPayments = new Payments();
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
    localHashMap.put("ItemSubType", new Integer(3));
    ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, localHashMap, paramHashMap);
    Iterator localIterator;
    ApprovalsItem localApprovalsItem;
    Payment localPayment;
    DateTime localDateTime;
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      localIterator = localApprovalsStatuses.iterator();
      while (localIterator.hasNext()) {
        try
        {
          ApprovalsStatus localApprovalsStatus1 = (ApprovalsStatus)localIterator.next();
          localApprovalsItem = localApprovalsStatus1.getApprovalItem();
          localPayment = (Payment)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
          localDateTime = localApprovalsItem.getSubmissionDate();
          localDateTime.setFormat("yyyy-MM-dd hh:mm:ss");
          localPayment.set("LogDate", localDateTime.toString());
          localPayment.set("UserId", Integer.toString(localApprovalsItem.getSubmittingUserID()));
          if ("Rejected".equals(localApprovalsStatus1.getDecision())) {
            localPayment.setStatus(15);
          } else {
            localPayment.setStatus(9);
          }
          localPayments.add(localPayment);
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
      localHashMap.put("ItemSubType", new Integer(4));
      localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, localHashMap, paramHashMap);
      if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
      {
        localIterator = localApprovalsStatuses.iterator();
        while (localIterator.hasNext()) {
          try
          {
            ApprovalsStatus localApprovalsStatus2 = (ApprovalsStatus)localIterator.next();
            localApprovalsItem = localApprovalsStatus2.getApprovalItem();
            localPayment = (Payment)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
            localDateTime = localApprovalsItem.getSubmissionDate();
            localDateTime.setFormat("yyyy-MM-dd hh:mm:ss");
            localPayment.set("LogDate", localDateTime.toString());
            localPayment.set("UserId", Integer.toString(localApprovalsItem.getSubmittingUserID()));
            if ("Rejected".equals(localApprovalsStatus2.getDecision())) {
              localPayment.setStatus(15);
            } else {
              localPayment.setStatus(9);
            }
            localPayments.add(localPayment);
          }
          catch (Exception localException2)
          {
            DebugLog.log(str1 + ": " + localException2.toString());
            DebugLog.log(str1 + ": Continuing ...");
          }
        }
      }
    }
    return localPayments;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, Entitlement paramEntitlement, String paramString)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), paramEntitlement))
    {
      debug("User:" + paramSecureUser.getUserName() + " not entitled for " + paramString);
      LocalizableString localLocalizableString;
      if (paramEntitlement.equals(as7))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_22", null);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      }
      else if (paramEntitlement.equals(asR))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_29", null);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      }
      throw new CSILException(paramString, 20001);
    }
  }
  
  public static Entitlement getPaymentEntitlement(Payment paramPayment)
  {
    String str = EntitlementsUtil.getEntitlementObjectId(paramPayment.getAccount());
    Entitlement localEntitlement = new Entitlement("Payments", "Account", str);
    return localEntitlement;
  }
  
  public static ReportLogRecords getAuditHistory(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getAuditHistory";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), as7))
    {
      long l = System.currentTimeMillis();
      ReportLogRecords localReportLogRecords = ReportAuditAdapter.getAuditHistoryByTrackingId(paramSecureUser, paramPayment.getTrackingID());
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localReportLogRecords;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_27", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, Payments paramPayments, String paramString)
    throws CSILException
  {
    Iterator localIterator = paramPayments.iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      jdMethod_for(paramSecureUser, localPayment, paramString);
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, Payment paramPayment, String paramString)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    int i = paramSecureUser.getBusinessID();
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    Account localAccount = paramPayment.getAccount();
    localMultiEntitlement.setOperations(new String[] { "Payments" });
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localAccount) });
    if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) != null) {
      throw new CSILException(paramString, 20001);
    }
    if ((jdMethod_for(paramPayment)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, as2))) {
      throw new CSILException(paramString, 20001);
    }
  }
  
  public static Payments getPagedPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPay.getPagedPayments";
    debug(paramSecureUser, str1);
    jdMethod_for(paramSecureUser, as7, str1);
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Billpay.getPagedPayments(paramSecureUser, paramPagingContext, paramHashMap);
    String str2 = (String)paramHashMap.get("PAYMENT_STATUS");
    if ((str2 != null) && (str2.equals("PAYMENT_STATUS_APPROVAL")))
    {
      Iterator localIterator = localPayments.iterator();
      while (localIterator.hasNext())
      {
        Payment localPayment = (Payment)localIterator.next();
        Util.insertApprovalInfo(localPayment, paramSecureUser, null, paramHashMap);
      }
    }
    PerfLog.log(str1, l, true);
    return localPayments;
  }
  
  public static PaymentBatch getPaymentBatchByID(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPay.getPagedPayments";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    paramPaymentBatch = com.ffusion.csil.handlers.Billpay.getPaymentBatchByID(paramSecureUser, paramPaymentBatch, paramHashMap);
    PerfLog.log(str, l, true);
    return paramPaymentBatch;
  }
  
  public static Payment addPaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPay.addPaymentTemplate";
    debug(paramSecureUser, str1);
    jdMethod_for(paramSecureUser, as7, str1);
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((jdMethod_for(paramPayment)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, as2))) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramPayment.getTemplateName();
    paramPayment.setTrackingID(str2);
    try
    {
      if (paramSecureUser.getBusinessID() == 0) {
        paramPayment.setCustomerID(paramSecureUser.getPrimaryUserID());
      } else {
        paramPayment.setCustomerID(paramSecureUser.getBusinessID());
      }
      paramPayment.setFIID(paramSecureUser.getBPWFIID());
      paramPayment.setSubmittedBy(paramSecureUser.getProfileID());
      paramPayment = com.ffusion.csil.handlers.Billpay.addPaymentTemplate(paramSecureUser, paramPayment, paramHashMap);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, paramPayment.getID(), str2);
      paramPayment.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_31", arrayOfObject);
      audit(paramPayment.getAuditRecord(paramSecureUser, localLocalizableString2, 2012, null));
      throw localCSILException;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_30", arrayOfObject);
    audit(paramPayment.getAuditRecord(paramSecureUser, localLocalizableString1, 2012, null));
    PerfLog.log(str1, l, true);
    return paramPayment;
  }
  
  public static Payment modifyPaymentTemplate(SecureUser paramSecureUser, Payment paramPayment1, Payment paramPayment2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPay.modifyPaymentTemplate";
    debug(paramSecureUser, str1);
    jdMethod_for(paramSecureUser, as7, str1);
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((jdMethod_for(paramPayment1)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, as2))) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramPayment1.getTemplateName();
    paramPayment1.setTrackingID(str2);
    try
    {
      if (paramSecureUser.getBusinessID() == 0) {
        paramPayment1.setCustomerID(paramSecureUser.getPrimaryUserID());
      } else {
        paramPayment1.setCustomerID(paramSecureUser.getBusinessID());
      }
      paramPayment1.setFIID(paramSecureUser.getBPWFIID());
      paramPayment1.setSubmittedBy(paramSecureUser.getProfileID());
      paramPayment1 = com.ffusion.csil.handlers.Billpay.modifyPaymentTemplate(paramSecureUser, paramPayment1, paramHashMap);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, paramPayment1.getID(), str2);
      paramPayment1.logChanges(localHistoryTracker, paramPayment2, localHistoryTracker.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_33", arrayOfObject);
      audit(paramPayment1.getAuditRecord(paramSecureUser, localLocalizableString2, 2012, null));
      throw localCSILException;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_32", arrayOfObject);
    audit(paramPayment1.getAuditRecord(paramSecureUser, localLocalizableString1, 2013, null));
    PerfLog.log(str1, l, true);
    return paramPayment1;
  }
  
  public static void deletePaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPay.deletePaymentTemplate";
    debug(paramSecureUser, str1);
    jdMethod_for(paramSecureUser, as7, str1);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramPayment.getTemplateName();
    paramPayment.setTrackingID(str2);
    try
    {
      if (paramSecureUser.getBusinessID() == 0) {
        paramPayment.setCustomerID(paramSecureUser.getPrimaryUserID());
      } else {
        paramPayment.setCustomerID(paramSecureUser.getBusinessID());
      }
      paramPayment.setFIID(paramSecureUser.getBPWFIID());
      paramPayment.setSubmittedBy(paramSecureUser.getProfileID());
      com.ffusion.csil.handlers.Billpay.deletePaymentTemplate(paramSecureUser, paramPayment, paramHashMap);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 9, paramPayment.getID(), str2);
      paramPayment.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_35", arrayOfObject);
      audit(paramPayment.getAuditRecord(paramSecureUser, localLocalizableString2, 2014, null));
      throw localCSILException;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_34", arrayOfObject);
    audit(paramPayment.getAuditRecord(paramSecureUser, localLocalizableString1, 2014, null));
    PerfLog.log(str1, l, true);
  }
  
  public static FundsTransactions getAllPaymentTemplates(SecureUser paramSecureUser, Payment paramPayment, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPay.getAllPaymentTemplates";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    if (paramPagingContext == null) {
      paramPagingContext = new PagingContext(null, null);
    }
    FundsTransactions localFundsTransactions = com.ffusion.csil.handlers.Billpay.getAllPaymentTemplates(paramSecureUser, paramPayment, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localFundsTransactions;
  }
  
  public static void addPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPay.addPaymentBatch";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, paramPaymentBatch.getPayments(), str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    paramPaymentBatch.setTrackingID(TrackingIDGenerator.GetNextID());
    if (paramSecureUser.getBusinessID() == 0) {
      paramPaymentBatch.setCustomerID(paramSecureUser.getPrimaryUserID());
    } else {
      paramPaymentBatch.setCustomerID(paramSecureUser.getBusinessID());
    }
    paramPaymentBatch.setFIID(paramSecureUser.getBPWFIID());
    paramPaymentBatch.setSubmittedBy(paramSecureUser.getProfileID());
    Object localObject1 = paramPaymentBatch.getPayments().listIterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Payment)((Iterator)localObject1).next();
      ((Payment)localObject2).setSubmittedBy(paramPaymentBatch.getSubmittedBy());
      ((Payment)localObject2).setTrackingID(TrackingIDGenerator.GetNextID());
    }
    try
    {
      com.ffusion.csil.handlers.Billpay.addPaymentBatch(paramSecureUser, paramPaymentBatch, paramHashMap);
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_37", null);
      audit(paramPaymentBatch.getAuditRecord(paramSecureUser, (ILocalizable)localObject1, 2015, null));
    }
    catch (CSILException localCSILException)
    {
      localObject2 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_36", null);
      audit(paramPaymentBatch.getAuditRecord(paramSecureUser, (ILocalizable)localObject2, 2015, null));
      throw localCSILException;
    }
    PerfLog.log(str, l, true);
  }
  
  public static void cancelPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPay.cancelPaymentBatch";
    debug(paramSecureUser, str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    jdMethod_for(paramSecureUser, as7, str);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramPaymentBatch.getTemplateName();
    try
    {
      com.ffusion.csil.handlers.Billpay.cancelPaymentBatch(paramSecureUser, paramPaymentBatch, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_41", arrayOfObject);
      audit(paramPaymentBatch.getAuditRecord(paramSecureUser, localLocalizableString2, 2017, null));
      throw localCSILException;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_40", arrayOfObject);
    audit(paramPaymentBatch.getAuditRecord(paramSecureUser, localLocalizableString1, 2017, null));
    PerfLog.log(str, l, true);
  }
  
  public static PaymentBatch modifyPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch1, PaymentBatch paramPaymentBatch2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPay.modifyPaymentBatch";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, paramPaymentBatch1.getPayments(), str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramPaymentBatch1.getTemplateName();
    ListIterator localListIterator = paramPaymentBatch1.getPayments().listIterator();
    while (localListIterator.hasNext())
    {
      localObject = (Payment)localListIterator.next();
      if (((Payment)localObject).getTrackingID() == null)
      {
        ((Payment)localObject).setTrackingID(TrackingIDGenerator.GetNextID());
        ((Payment)localObject).setSubmittedBy(paramPaymentBatch1.getSubmittedBy());
      }
    }
    try
    {
      paramPaymentBatch1 = com.ffusion.csil.handlers.Billpay.modifyPaymentBatch(paramSecureUser, paramPaymentBatch1, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_39", arrayOfObject);
      audit(paramPaymentBatch1.getAuditRecord(paramSecureUser, (ILocalizable)localObject, 2016, null));
      throw localCSILException;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.billpay", "AuditMessage_38", arrayOfObject);
    audit(paramPaymentBatch1.getAuditRecord(paramSecureUser, localLocalizableString, 2016, null));
    Object localObject = new HistoryTracker(paramSecureUser, 10, paramPaymentBatch1.getID(), paramPaymentBatch1.getTrackingID());
    paramPaymentBatch1.logChanges((HistoryTracker)localObject, paramPaymentBatch2, ((HistoryTracker)localObject).buildLocalizableComment(17));
    try
    {
      HistoryAdapter.addHistory(((HistoryTracker)localObject).getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localProfileException.toString());
    }
    PerfLog.log(str, l, true);
    return paramPaymentBatch1;
  }
  
  public static Payments getLastPayments(SecureUser paramSecureUser, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPay.getLastPayments";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, as7, str);
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Billpay.getLastPayments(paramSecureUser, paramPayees, paramHashMap);
    PerfLog.log(str, l, true);
    return localPayments;
  }
  
  private static void jdMethod_for(Payment paramPayment, SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    Payments localPayments = new Payments();
    localPayments.add(paramPayment);
    jdMethod_for(localPayments, paramSecureUser, paramHashMap);
  }
  
  private static void jdMethod_for(Payments paramPayments, SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    Limits localLimits1 = new Limits();
    EntitlementGroupMember localEntitlementGroupMember = null;
    Limits localLimits2 = null;
    if (paramHashMap == null) {
      return;
    }
    Iterator localIterator1 = paramPayments.iterator();
    while (localIterator1.hasNext())
    {
      Payment localPayment = (Payment)localIterator1.next();
      Limits localLimits3 = (Limits)localPayment.get("FAILED_LIMITS");
      if ((localLimits3 != null) && (!localLimits3.isEmpty()))
      {
        Limit localLimit1 = (Limit)localLimits3.get(0);
        if (localLimit1.getGroupId() == -1)
        {
          if (localEntitlementGroupMember == null) {
            localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
          }
          if (localLimits2 == null) {
            localLimits2 = Entitlements.getCumulativeLimits(localEntitlementGroupMember);
          }
          Iterator localIterator2 = localLimits3.iterator();
          while (localIterator2.hasNext())
          {
            localLimit1 = (Limit)localIterator2.next();
            Limit localLimit2 = localLimits2.getLimitById(localLimit1.getLimitId());
            if (localLimit2 != null) {
              localLimits1.add(localLimit2);
            }
          }
        }
      }
    }
    if (!localLimits1.isEmpty()) {
      paramHashMap.put("ExceededLimits", localLimits1);
    }
  }
  
  public static String getGlobalPayeeDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "BillPay.getDisplayPayeeCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), asR))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.Billpay.getGlobalPayeeDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    throw new CSILException(str1, 20001);
  }
  
  public static Currency getDefaultCurrency()
    throws CSILException
  {
    return defaultCurrency;
  }
  
  public static FinancialInstitutions getFinancialInstitutions(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getFinancialInstitutions";
    long l = System.currentTimeMillis();
    FinancialInstitutions localFinancialInstitutions = new FinancialInstitutions();
    try
    {
      localFinancialInstitutions = BankLookup.getFinancialInstitutions(paramSecureUser, paramFinancialInstitution, paramInt, paramHashMap);
      if (localFinancialInstitutions == null) {
        DebugLog.log("No FinancialInstitutions are found matching the search criteria.");
      }
    }
    catch (CSILException localCSILException)
    {
      localCSILException.printStackTrace(System.err);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localFinancialInstitutions;
  }
  
  public static Payee getGlobalPayee(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPay.getGlobalPayee";
    Payee localPayee = com.ffusion.csil.handlers.Billpay.getGlobalPayee(paramSecureUser, paramInt, paramHashMap);
    return localPayee;
  }
  
  private static boolean jdMethod_for(Payment paramPayment)
  {
    if (paramPayment.getPayee().getPayeeRoute().getCurrencyCode() != null) {
      return !defaultCurrency.getCurrencyCode().equals(paramPayment.getPayee().getPayeeRoute().getCurrencyCode());
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Billpay
 * JD-Core Version:    0.7.0.1
 */