package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.cashcon.CashConAccount;
import com.ffusion.beans.cashcon.CashConAccounts;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.cashcon.Locations;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.ReportAuditAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.cashcon.CashConException;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class CashCon
  extends Initialize
{
  private static Entitlement avn = new Entitlement("Cash Con - Reporting", null, null);
  private static Entitlement avj = new Entitlement("Cash Con - Deposit Entry", null, null);
  private static Entitlement avo = new Entitlement("Cash Con - Disbursement Request", null, null);
  private static Entitlement avk = new Entitlement("Cash Con - Deposit Entry Edit Other", null, null);
  private static Entitlement avm = new Entitlement("Cash Con - Disbursement Request Edit Other", null, null);
  private static Entitlement avl = new Entitlement("Cash Con - Deposit Entry Delete Other", null, null);
  private static Entitlement avh = new Entitlement("Cash Con - Disbursement Request Delete Other", null, null);
  private static Entitlement avi = new Entitlement("Location Management", null, null);
  private static Entitlement avp = new Entitlement("BC Cash Con Company Management", null, null);
  public static final String CASHCON_AUDIT_STATE_ADDED = "ADDED";
  public static final String CASHCON_AUDIT_STATE_DELETED = "DELETED";
  public static final String CASHCON_AUDIT_STATE_MODIFIED = "MODIFIED";
  public static final String CASHCON_AUDIT_STATE_FAILED = "FAILED";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.CashCon.initialize");
    com.ffusion.csil.handlers.CashCon.initialize(paramHashMap);
  }
  
  public static Object getService(String paramString)
  {
    return com.ffusion.csil.handlers.CashCon.getService(paramString);
  }
  
  public static String getDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "CashCon.getDisplayCount";
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avj)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avo)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avi)))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.CashCon.getDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Concentration.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getMaxResultCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "CashCon.getMaxResultCount";
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avj)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avo)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avi)))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.CashCon.getMaxResultCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Concentration.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.cashcon.CashCon addCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.addCashCon";
    int i = 0;
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    int j = 5103;
    int k = 22;
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramCashCon.getType() == 15)
    {
      localStringBuffer.append("Add Cash Concentration");
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avj)) {
        i = 1;
      }
    }
    else if (paramCashCon.getType() == 16)
    {
      localStringBuffer.append("Add Cash Disbursement");
      j = 5100;
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avo)) {
        i = 1;
      }
    }
    if (i == 0)
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Concentration.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    String str2 = paramCashCon.getTrackingID();
    if (str2 == null) {
      str2 = TrackingIDGenerator.GetNextID();
    }
    paramCashCon.setTrackingID(str2);
    paramCashCon.setSubmittedBy(paramSecureUser.getProfileID());
    com.ffusion.beans.cashcon.CashCon localCashCon = null;
    try
    {
      localCashCon = com.ffusion.csil.handlers.CashCon.addCashCon(paramSecureUser, paramCashCon, paramHashMap);
      audit(localCashCon.getAuditRecord(paramSecureUser, localStringBuffer.toString(), j, "ADDED"));
    }
    catch (CashConException localCashConException)
    {
      localStringBuffer.setLength(0);
      localStringBuffer.append("An error occurred while adding the ");
      if (paramCashCon.getType() == 15) {
        localStringBuffer.append("Cash Concentration");
      } else if (paramCashCon.getType() == 16) {
        localStringBuffer.append("Cash Disbursement");
      }
      audit(paramCashCon.getAuditRecord(paramSecureUser, localStringBuffer.toString(), j, "FAILED"));
      throw new CSILException(localCashConException.getErrorCode(), localCashConException, localCashConException.where, localCashConException.why);
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, k, localCashCon.getID(), str2);
    localCashCon.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return localCashCon;
  }
  
  public static com.ffusion.beans.cashcon.CashCon modifyCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon1, com.ffusion.beans.cashcon.CashCon paramCashCon2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.modifyCashCon";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    boolean bool = false;
    int i = 5104;
    int j = 22;
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramCashCon1.getType() == 15)
    {
      localStringBuffer.append("Modify the Cash Concentration");
      bool = Entitlements.checkEntitlement(localEntitlementGroupMember, avj);
      if ((bool) && (paramCashCon1.getSubmittedBy() != null) && (!paramCashCon1.getSubmittedBy().equals("" + paramSecureUser.getProfileID()))) {
        bool = Entitlements.checkEntitlement(localEntitlementGroupMember, avk);
      }
    }
    else if (paramCashCon1.getType() == 16)
    {
      localStringBuffer.append("Modify the Cash Disbursement");
      i = 5101;
      bool = Entitlements.checkEntitlement(localEntitlementGroupMember, avo);
      if ((bool) && (paramCashCon1.getSubmittedBy() != null) && (!paramCashCon1.getSubmittedBy().equals("" + paramSecureUser.getProfileID()))) {
        bool = Entitlements.checkEntitlement(localEntitlementGroupMember, avm);
      }
    }
    if (!bool)
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to modify Cash Concentration.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    String str2 = paramCashCon2.getTrackingID();
    if (str2 == null) {
      str2 = TrackingIDGenerator.GetNextID();
    }
    paramCashCon1.setTrackingID(str2);
    com.ffusion.beans.cashcon.CashCon localCashCon = null;
    try
    {
      localCashCon = com.ffusion.csil.handlers.CashCon.modifyCashCon(paramSecureUser, paramCashCon1, paramCashCon2, paramHashMap);
      audit(localCashCon.getAuditRecord(paramSecureUser, localStringBuffer.toString(), i, "MODIFIED"));
    }
    catch (CashConException localCashConException)
    {
      localStringBuffer.setLength(0);
      localStringBuffer.append("An error occurred while modifying the ");
      if (paramCashCon1.getType() == 15) {
        localStringBuffer.append("Cash Concentration");
      } else if (paramCashCon1.getType() == 16) {
        localStringBuffer.append("Cash Disbursement");
      }
      audit(paramCashCon1.getAuditRecord(paramSecureUser, localStringBuffer.toString(), i, "FAILED"));
      throw new CSILException(localCashConException.getErrorCode(), localCashConException, localCashConException.where, localCashConException.why);
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, j, localCashCon.getID(), str2);
    localCashCon.logChanges(localHistoryTracker, paramCashCon2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
    return localCashCon;
  }
  
  public static void deleteCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.deleteCashCon";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = paramCashCon.getTrackingID();
    boolean bool = false;
    int i = 5105;
    int j = 22;
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramCashCon.getType() == 15)
    {
      localStringBuffer.append("Delete the Cash Concentration");
      bool = Entitlements.checkEntitlement(localEntitlementGroupMember, avj);
      if ((bool) && (paramCashCon.getSubmittedBy() != null) && (!paramCashCon.getSubmittedBy().equals("" + paramSecureUser.getProfileID()))) {
        bool = Entitlements.checkEntitlement(localEntitlementGroupMember, avl);
      }
    }
    else if (paramCashCon.getType() == 16)
    {
      localStringBuffer.append("Delete the Cash Disbursement");
      i = 5102;
      bool = Entitlements.checkEntitlement(localEntitlementGroupMember, avo);
      if ((bool) && (paramCashCon.getSubmittedBy() != null) && (!paramCashCon.getSubmittedBy().equals("" + paramSecureUser.getProfileID()))) {
        bool = Entitlements.checkEntitlement(localEntitlementGroupMember, avh);
      }
    }
    if (!bool)
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to delete Cash Concentration.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    try
    {
      com.ffusion.csil.handlers.CashCon.deleteCashCon(paramSecureUser, paramCashCon, paramHashMap);
      audit(paramCashCon.getAuditRecord(paramSecureUser, localStringBuffer.toString(), i, "DELETED"));
    }
    catch (CashConException localCashConException)
    {
      localStringBuffer.setLength(0);
      localStringBuffer.append("An error occurred while deleting the ");
      if (paramCashCon.getType() == 15) {
        localStringBuffer.append("Cash Concentration");
      } else if (paramCashCon.getType() == 16) {
        localStringBuffer.append("Cash Disbursement");
      }
      audit(paramCashCon.getAuditRecord(paramSecureUser, localStringBuffer.toString(), i, "FAILED"));
      throw new CSILException(localCashConException.getErrorCode(), localCashConException, localCashConException.where, localCashConException.why);
    }
    PerfLog.log(str1, l, true);
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, j, paramCashCon.getID(), str2);
    paramCashCon.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
  }
  
  public static ReportLogRecords getAuditHistory(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getAuditHistory";
    int i = 0;
    if (paramCashCon.getType() == 15)
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avj)) {
        i = 1;
      }
    }
    else if ((paramCashCon.getType() == 16) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avo))) {
      i = 1;
    }
    if (i == 0)
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to retrieve the Audit History.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    ReportLogRecords localReportLogRecords = ReportAuditAdapter.getAuditHistoryByTrackingId(paramSecureUser, paramCashCon.getTrackingID());
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localReportLogRecords;
  }
  
  public static CashCons getPagedCashConHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.getPagedCashConHistories";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getPagedCashConHistories(paramSecureUser, paramPagingContext, paramHashMap);
        String str2 = (String)paramHashMap.get("CASHCON_STATUS");
        if ((str2 != null) && (str2.equals("CASHCON_STATUS_APPROVAL")))
        {
          Iterator localIterator = localCashCons.iterator();
          while (localIterator.hasNext())
          {
            com.ffusion.beans.cashcon.CashCon localCashCon = (com.ffusion.beans.cashcon.CashCon)localIterator.next();
            Util.insertApprovalInfo(localCashCon, paramSecureUser, null, paramHashMap);
          }
        }
      }
      catch (CSILException localCSILException)
      {
        localCSILException.printStackTrace();
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the pending Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static CashCons getPagedPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getPagedPendingCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getPagedPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the pending Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashCons getNextPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getNextPendingCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getNextPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the pending Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashCons getPreviousPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getPreviousPendingCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getPreviousPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the pending Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashCons getPagedCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getPagedCompletedCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getPagedCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the completed Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashCons getNextCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getNextCompletedCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getNextCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the completed Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashCons getPreviousCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getPreviousCompletedCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getPreviousCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the completed Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashCons getPagedApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getPagedApprovalCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getPagedApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the approval Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashCons getNextApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getNextApprovalCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getNextApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the approval Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashCons getPreviousApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getPreviousApprovalCashCons";
    ArrayList localArrayList = jdMethod_long(paramSecureUser);
    if (localArrayList.size() > 0)
    {
      long l = System.currentTimeMillis();
      paramHashMap.put("TypeList", localArrayList);
      CashCons localCashCons = null;
      try
      {
        localCashCons = com.ffusion.csil.handlers.CashCon.getPreviousApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        localCashConException.printStackTrace();
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashCons;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to get the approval Cash Concentrations.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.GetReportData";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avn))
    {
      long l = System.currentTimeMillis();
      Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
      IReportResult localIReportResult = null;
      try
      {
        localIReportResult = com.ffusion.csil.handlers.CashCon.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      catch (CashConException localCashConException)
      {
        throw new CSILException(-1009, localCashConException.getErrorCode(), localCashConException);
      }
      String str2 = paramReportCriteria.getReportOptions().getProperty("TITLE");
      if ((str2 == null) || (str2.length() == 0))
      {
        String str3 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
        str2 = ReportConsts.getReportName(str3, Locale.getDefault());
      }
      audit(paramSecureUser, "Generate " + str2 + ".", TrackingIDGenerator.GetNextID(), 5106);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localIReportResult;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to CashCon reports.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  private static ArrayList jdMethod_long(SecureUser paramSecureUser)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    boolean bool1 = false;
    boolean bool2 = false;
    ArrayList localArrayList = new ArrayList();
    bool1 = Entitlements.checkEntitlement(localEntitlementGroupMember, avj);
    if (bool1) {
      localArrayList.add("concentration");
    }
    bool2 = Entitlements.checkEntitlement(localEntitlementGroupMember, avo);
    if (bool2) {
      localArrayList.add("disbursement");
    }
    return localArrayList;
  }
  
  public static CashConCompanies getCashConCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getACHCompanies";
    if ((paramSecureUser.getUserType() == 2) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp)))
    {
      long l = System.currentTimeMillis();
      CashConCompanies localCashConCompanies = com.ffusion.csil.handlers.CashCon.getCashConCompanies(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashConCompanies;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static CashConCompany addCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.addCashConCompany";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      paramCashConCompany.setTrackingID(str2);
      int i = 5107;
      StringBuffer localStringBuffer = new StringBuffer();
      CashConCompany localCashConCompany = null;
      try
      {
        localCashConCompany = com.ffusion.csil.handlers.CashCon.addCashConCompany(paramSecureUser, paramCashConCompany, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while adding a Cash Concentration Company");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append(" BPW Error:" + localCSILException.why);
        }
        audit(jdMethod_for(paramSecureUser, paramCashConCompany, localStringBuffer.toString(), i, "FAILED"));
        throw localCSILException;
      }
      localStringBuffer.append("Add Cash Concentration Company with Name " + localCashConCompany.getCompanyName());
      audit(jdMethod_for(paramSecureUser, localCashConCompany, localStringBuffer.toString(), i, "ADDED"));
      String str3 = localCashConCompany.getBPWID();
      try
      {
        Integer.parseInt(str3);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str3.length();
        if (j > 9) {
          str3 = str3.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 23, str3, str2);
      paramCashConCompany.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
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
      return localCashConCompany;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void modifyCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany1, CashConCompany paramCashConCompany2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.modifyCashConCompany";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramCashConCompany1.getTrackingID();
      if (str2 == null)
      {
        str2 = TrackingIDGenerator.GetNextID();
        paramCashConCompany1.setTrackingID(str2);
      }
      int i = 5108;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.modifyCashConCompany(paramSecureUser, paramCashConCompany1, paramCashConCompany2, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while modifying Cash Concentration company");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append(" BPW Error:" + localCSILException.why);
        }
        audit(jdMethod_for(paramSecureUser, paramCashConCompany1, localStringBuffer.toString(), i, "FAILED"));
        throw localCSILException;
      }
      localStringBuffer.append("Modify Cash Concentration Company with Name " + paramCashConCompany2.getCompanyName());
      audit(jdMethod_for(paramSecureUser, paramCashConCompany1, localStringBuffer.toString(), i, "MODIFIED"));
      String str3 = paramCashConCompany1.getBPWID();
      try
      {
        Integer.parseInt(str3);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str3.length();
        if (j > 9) {
          str3 = str3.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 23, str3, str2);
      paramCashConCompany1.logChanges(localHistoryTracker, paramCashConCompany2, localHistoryTracker.buildLocalizableComment(17));
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
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Concentration Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void deleteCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.deleteCashConCompany";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramCashConCompany.getTrackingID();
      if (str2 == null)
      {
        str2 = TrackingIDGenerator.GetNextID();
        paramCashConCompany.setTrackingID(str2);
      }
      int i = 5109;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.deleteCashConCompany(paramSecureUser, paramCashConCompany, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while deleting Cash Concentration company");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append(" BPW Error:" + localCSILException.why);
        }
        audit(jdMethod_for(paramSecureUser, paramCashConCompany, localStringBuffer.toString(), i, "FAILED"));
        throw localCSILException;
      }
      localStringBuffer.append("Delete Cash Concentration Company with Name " + paramCashConCompany.getCompanyName());
      audit(jdMethod_for(paramSecureUser, paramCashConCompany, localStringBuffer.toString(), i, "DELETED"));
      String str3 = paramCashConCompany.getBPWID();
      try
      {
        Integer.parseInt(str3);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str3.length();
        if (j > 9) {
          str3 = str3.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 23, str3, str2);
      paramCashConCompany.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
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
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void addConcAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.addConcAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5110;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.addConcAccount(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while adding CashCon concentration account");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append("BPW Error: " + localCSILException.why);
        }
        audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCashConAccount.getBPWID());
        throw localCSILException;
      }
      localStringBuffer.append("Add CashCon concentration account with bank ");
      localStringBuffer.append(paramCashConAccount.getBankName());
      localStringBuffer.append(" and number ");
      localStringBuffer.append(paramCashConAccount.getNumber());
      localStringBuffer.append(" for company ");
      localStringBuffer.append(paramCashConCompany.getCompanyID());
      audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCashConAccount.getBPWID());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static CashConAccounts getConcAccounts(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getConcAccounts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      CashConAccounts localCashConAccounts = com.ffusion.csil.handlers.CashCon.getConcAccounts(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashConAccounts;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void deleteConcAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.deleteConcAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5111;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.deleteConcAccount(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while deleting CashCon concentration account");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append("BPW Error: " + localCSILException.why);
        }
        audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCashConAccount.getBPWID());
        throw localCSILException;
      }
      localStringBuffer.append("Delete CashCon concentration account with bank ");
      localStringBuffer.append(paramCashConAccount.getBankName());
      localStringBuffer.append(" and number ");
      localStringBuffer.append(paramCashConAccount.getNumber());
      localStringBuffer.append(" for company ");
      localStringBuffer.append(paramCashConCompany.getCompanyID());
      audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCashConAccount.getBPWID());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void addDisbAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.addDisbAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5110;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.addDisbAccount(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while adding CashCon disbursement account");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append("BPW Error: " + localCSILException.why);
        }
        audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCashConAccount.getBPWID());
        throw localCSILException;
      }
      localStringBuffer.append("Add CashCon disbursement account with bank ");
      localStringBuffer.append(paramCashConAccount.getBankName());
      localStringBuffer.append(" and number ");
      localStringBuffer.append(paramCashConAccount.getNumber());
      localStringBuffer.append(" for company ");
      localStringBuffer.append(paramCashConCompany.getCompanyID());
      audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCashConAccount.getBPWID());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static CashConAccounts getDisbAccounts(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getDisbAccounts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      CashConAccounts localCashConAccounts = com.ffusion.csil.handlers.CashCon.getDisbAccounts(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCashConAccounts;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void deleteDisbAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.deleteDisbAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5111;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.deleteDisbAccount(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while deleting CashCon disbursement account");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append("BPW Error: " + localCSILException.why);
        }
        audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCashConAccount.getBPWID());
        throw localCSILException;
      }
      localStringBuffer.append("Delete CashCon disbursement account with bank ");
      localStringBuffer.append(paramCashConAccount.getBankName());
      localStringBuffer.append(" and number ");
      localStringBuffer.append(paramCashConAccount.getNumber());
      localStringBuffer.append(" for company ");
      localStringBuffer.append(paramCashConCompany.getCompanyID());
      audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCashConAccount.getBPWID());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void addConcCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.addConcCutOff";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5112;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.addConcCutOff(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while adding CashCon concentration cutoff");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append("BPW Error: " + localCSILException.why);
        }
        audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCutOffTime.getCutOffId());
        throw localCSILException;
      }
      localStringBuffer.append("Add CashCon concentration cutoff with bank ");
      localStringBuffer.append(paramCutOffTime.getFIId());
      localStringBuffer.append(" and cutoff ");
      localStringBuffer.append(paramCutOffTime.getDayOfWeekString() + " " + paramCutOffTime.getTimeOfDay());
      localStringBuffer.append(" for company ");
      localStringBuffer.append(paramCashConCompany.getCompanyID());
      audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCutOffTime.getCutOffId());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static CutOffTimes getConcCutOffs(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getConcCutOffs";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      CutOffTimes localCutOffTimes = com.ffusion.csil.handlers.CashCon.getConcCutOffs(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCutOffTimes;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void deleteConcCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.deleteConcCutOff";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5113;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.deleteConcCutOff(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while deleting CashCon concentration cutoff");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append("BPW Error: " + localCSILException.why);
        }
        audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCutOffTime.getCutOffId());
        throw localCSILException;
      }
      localStringBuffer.append("Delete CashCon concentration cutoff with bank ");
      localStringBuffer.append(paramCutOffTime.getFIId());
      localStringBuffer.append(" and cutoff ");
      localStringBuffer.append(paramCutOffTime.getDayOfWeekString() + " " + paramCutOffTime.getTimeOfDay());
      localStringBuffer.append(" for company ");
      localStringBuffer.append(paramCashConCompany.getCompanyID());
      audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCutOffTime.getCutOffId());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void addDisbCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.addDisbCutOff";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5112;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.addDisbCutOff(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while adding CashCon disbursement cutoff");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append("BPW Error: " + localCSILException.why);
        }
        audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCutOffTime.getCutOffId());
        throw localCSILException;
      }
      localStringBuffer.append("Add CashCon disbursement cutoff with bank ");
      localStringBuffer.append(paramCutOffTime.getFIId());
      localStringBuffer.append(" and cutoff ");
      localStringBuffer.append(paramCutOffTime.getDayOfWeekString() + " " + paramCutOffTime.getTimeOfDay());
      localStringBuffer.append(" for company ");
      localStringBuffer.append(paramCashConCompany.getCompanyID());
      audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCutOffTime.getCutOffId());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static CutOffTimes getDisbCutOffs(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getDisbCutOffs";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      CutOffTimes localCutOffTimes = com.ffusion.csil.handlers.CashCon.getDisbCutOffs(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localCutOffTimes;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void deleteDisbCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.deleteDisbCutOff";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5113;
      StringBuffer localStringBuffer = new StringBuffer();
      try
      {
        com.ffusion.csil.handlers.CashCon.deleteDisbCutOff(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while deleting CashCon disbursement cutoff");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append("BPW Error: " + localCSILException.why);
        }
        audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCutOffTime.getCutOffId());
        throw localCSILException;
      }
      localStringBuffer.append("Delete CashCon disbursement cutoff with bank ");
      localStringBuffer.append(paramCutOffTime.getFIId());
      localStringBuffer.append(" and cutoff ");
      localStringBuffer.append(paramCutOffTime.getDayOfWeekString() + " " + paramCutOffTime.getTimeOfDay());
      localStringBuffer.append(" for company ");
      localStringBuffer.append(paramCashConCompany.getCompanyID());
      audit(paramSecureUser, localStringBuffer.toString(), str2, i, null, null, paramCutOffTime.getCutOffId());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static int getNumberOfLocations(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getNumberOfLocations";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      int i = com.ffusion.csil.handlers.CashCon.getNumberOfLocations(paramSecureUser, paramString1, paramString2, paramString3, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return i;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Locations getLocationsByAccount(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getLocationsByAccount";
    if ((paramSecureUser.getUserType() == 2) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp)))
    {
      long l = System.currentTimeMillis();
      Locations localLocations = com.ffusion.csil.handlers.CashCon.getLocationsByAccount(paramSecureUser, paramString1, paramString2, paramString3, paramString4, paramBoolean, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLocations;
    }
    logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static LocationSearchResults getLocations(SecureUser paramSecureUser, LocationSearchCriteria paramLocationSearchCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getLocations";
    long l = System.currentTimeMillis();
    try
    {
      LocationSearchResults localLocationSearchResults1 = com.ffusion.csil.handlers.CashCon.getLocations(paramSecureUser, paramLocationSearchCriteria, paramHashMap);
      LocationSearchResults localLocationSearchResults2 = localLocationSearchResults1;
      return localLocationSearchResults2;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static LocationSearchResults getLocationsForDisbursement(SecureUser paramSecureUser, LocationSearchCriteria paramLocationSearchCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getLocationsForDisbursement";
    long l = System.currentTimeMillis();
    try
    {
      LocationSearchResults localLocationSearchResults1 = com.ffusion.csil.handlers.CashCon.getLocations(paramSecureUser, paramLocationSearchCriteria, paramHashMap);
      Entitlement localEntitlement = new Entitlement("Cash Con - Disbursement Request", "Location", null);
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      LocationSearchResults localLocationSearchResults2 = new LocationSearchResults();
      Iterator localIterator = localLocationSearchResults1.iterator();
      while (localIterator.hasNext())
      {
        localObject1 = (LocationSearchResult)localIterator.next();
        localEntitlement.setObjectId(((LocationSearchResult)localObject1).getLocationBPWID());
        if (Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement)) {
          localLocationSearchResults2.add(localObject1);
        }
      }
      Object localObject1 = localLocationSearchResults2;
      return localObject1;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static LocationSearchResults getLocationsForDeposit(SecureUser paramSecureUser, LocationSearchCriteria paramLocationSearchCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getLocationsForDeposit";
    long l = System.currentTimeMillis();
    try
    {
      LocationSearchResults localLocationSearchResults1 = com.ffusion.csil.handlers.CashCon.getLocations(paramSecureUser, paramLocationSearchCriteria, paramHashMap);
      Entitlement localEntitlement = new Entitlement("Cash Con - Deposit Entry", "Location", null);
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      LocationSearchResults localLocationSearchResults2 = new LocationSearchResults();
      Iterator localIterator = localLocationSearchResults1.iterator();
      while (localIterator.hasNext())
      {
        localObject1 = (LocationSearchResult)localIterator.next();
        localEntitlement.setObjectId(((LocationSearchResult)localObject1).getLocationBPWID());
        if (Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement)) {
          localLocationSearchResults2.add(localObject1);
        }
      }
      Object localObject1 = localLocationSearchResults2;
      return localObject1;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static Location getLocation(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getLocation";
    long l = System.currentTimeMillis();
    try
    {
      Location localLocation = com.ffusion.csil.handlers.CashCon.getLocation(paramSecureUser, paramString, paramHashMap);
      return localLocation;
    }
    finally
    {
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
  }
  
  public static void addLocation(SecureUser paramSecureUser, Location paramLocation, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CashCon.addLocation";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avi))
    {
      int i = 5114;
      StringBuffer localStringBuffer = new StringBuffer();
      long l = System.currentTimeMillis();
      try
      {
        paramLocation.setLogId(TrackingIDGenerator.GetNextID());
        paramLocation.setSubmittedBy(new Integer(paramSecureUser.getProfileID()).toString());
        com.ffusion.csil.handlers.CashCon.addLocation(paramSecureUser, paramLocation, paramHashMap);
        EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("category", "per location");
        ArrayList localArrayList = new ArrayList();
        String str2 = null;
        Object localObject = localEntitlementTypePropertyLists.iterator();
        while (((Iterator)localObject).hasNext())
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)((Iterator)localObject).next();
          str2 = localEntitlementTypePropertyList.getPropertyValue("hide", 0);
          if ((str2 == null) || (!str2.equals("yes"))) {
            localArrayList.add(localEntitlementTypePropertyList.getOperationName());
          }
        }
        localObject = new com.ffusion.csil.beans.entitlements.Entitlements();
        for (int j = 0; j < localArrayList.size(); j++)
        {
          Entitlement localEntitlement = new Entitlement((String)localArrayList.get(j), "Location", paramLocation.getLocationBPWID());
          ((com.ffusion.csil.beans.entitlements.Entitlements)localObject).add(localEntitlement);
        }
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramLocation.getDivisionID());
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, localEntitlementGroup, (com.ffusion.csil.beans.entitlements.Entitlements)localObject, 4, paramBoolean, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while adding a CashCon Location");
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append(" Error:" + localCSILException.why);
        }
        audit(jdMethod_for(paramSecureUser, paramLocation, localStringBuffer.toString(), i, "FAILED"));
        throw localCSILException;
      }
      localStringBuffer.append("Add CashCon Location ");
      localStringBuffer.append("with Name " + paramLocation.getLocationName());
      audit(jdMethod_for(paramSecureUser, paramLocation, localStringBuffer.toString(), i, "ADDED"));
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Location Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void addLocation(SecureUser paramSecureUser, Location paramLocation, HashMap paramHashMap)
    throws CSILException
  {
    addLocation(paramSecureUser, paramLocation, true, paramHashMap);
  }
  
  private static AuditLogRecord jdMethod_for(SecureUser paramSecureUser, Location paramLocation, String paramString1, int paramInt, String paramString2)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
      i = paramSecureUser.getPrimaryUserID();
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, new LocalizableString("dummy", paramString1, null), paramLocation != null ? paramLocation.getLogId() : null, paramInt, paramSecureUser.getBusinessID(), null, null, paramLocation != null ? paramLocation.getLocationBPWID() : null, paramString2, null, null, null, null, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  private static AuditLogRecord jdMethod_for(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString1, int paramInt, String paramString2)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
      i = paramSecureUser.getPrimaryUserID();
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, new LocalizableString("dummy", paramString1, null), paramCashConCompany != null ? paramCashConCompany.getTrackingID() : null, paramInt, Integer.parseInt(paramCashConCompany.getCustID()), null, null, paramCashConCompany != null ? paramCashConCompany.getBPWID() : null, paramString2, null, null, null, null, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  public static void deleteLocation(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.deleteLocation";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avi))
    {
      int i = 5115;
      StringBuffer localStringBuffer = new StringBuffer();
      long l = System.currentTimeMillis();
      Location localLocation = null;
      try
      {
        localLocation = com.ffusion.csil.handlers.CashCon.getLocation(paramSecureUser, paramString, paramHashMap);
        com.ffusion.csil.handlers.CashCon.deleteLocation(paramSecureUser, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while deleting a CashCon Location with BPWID ").append(paramString);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append(" Error:" + localCSILException.why);
        }
        audit(jdMethod_for(paramSecureUser, localLocation, localStringBuffer.toString(), i, "FAILED"));
        throw localCSILException;
      }
      localStringBuffer.append("Deleted CashCon Location");
      audit(jdMethod_for(paramSecureUser, localLocation, localStringBuffer.toString(), i, "DELETED"));
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Location Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static void modifyLocation(SecureUser paramSecureUser, Location paramLocation, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.modifyLocation";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avi))
    {
      int i = 5116;
      StringBuffer localStringBuffer = new StringBuffer();
      long l = System.currentTimeMillis();
      try
      {
        com.ffusion.csil.handlers.CashCon.modifyLocation(paramSecureUser, paramLocation, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localStringBuffer.append("An error occurred while modifying a CashCon Location with BPWID ").append(paramLocation.getLocationBPWID());
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          localStringBuffer.append(" Error:" + localCSILException.why);
        }
        audit(jdMethod_for(paramSecureUser, paramLocation, localStringBuffer.toString(), i, "FAILED"));
        throw localCSILException;
      }
      localStringBuffer.append("Modified CashCon Location with name " + paramLocation.getLocationName());
      audit(jdMethod_for(paramSecureUser, paramLocation, localStringBuffer.toString(), i, "MODIFIED"));
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Location Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static int getPendingCashConCount(SecureUser paramSecureUser, String paramString1, String paramString2, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getNumberOfLocations";
    int i = 0;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avp))
    {
      long l = System.currentTimeMillis();
      i = com.ffusion.csil.handlers.CashCon.getPendingCashConCount(paramSecureUser, paramString1, paramString2, paramArrayOfString, paramHashMap);
      PerfLog.log("CashCon.getNumberOfLocations", l, true);
      debug(paramSecureUser, "CashCon.getNumberOfLocations");
    }
    else
    {
      logEntitlementFault(paramSecureUser, "The user is not entitled to Cash Con Company Management.", TrackingIDGenerator.GetNextID());
      throw new CSILException("CashCon.getNumberOfLocations", 20001);
    }
    return i;
  }
  
  public static LocationSearchResults getEntitledLocations(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getEntitledLocations";
    LocationSearchResults localLocationSearchResults1 = new LocationSearchResults();
    int i = -1;
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    for (EntitlementGroup localEntitlementGroup1 = Entitlements.getEntitlementGroup(paramSecureUser.getEntitlementID()); !"Business".equals(localEntitlementGroup1.getEntGroupType()); localEntitlementGroup1 = Entitlements.getEntitlementGroup(localEntitlementGroup1.getParentId())) {
      if ("Division".equals(localEntitlementGroup1.getEntGroupType()))
      {
        i = localEntitlementGroup1.getGroupId();
        localEntitlementGroups.add(localEntitlementGroup1);
        break;
      }
    }
    if ((localEntitlementGroup1 != null) && (i == -1)) {
      jdMethod_try(localEntitlementGroup1, localEntitlementGroups);
    }
    Iterator localIterator1 = localEntitlementGroups.iterator();
    LocationSearchCriteria localLocationSearchCriteria = new LocationSearchCriteria();
    while (localIterator1.hasNext())
    {
      EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator1.next();
      LocationSearchResults localLocationSearchResults2 = null;
      localLocationSearchCriteria.setDivisionID(localEntitlementGroup2.getGroupId());
      localLocationSearchResults2 = getLocations(paramSecureUser, localLocationSearchCriteria, new HashMap());
      if (localLocationSearchResults2 != null)
      {
        Iterator localIterator2 = localLocationSearchResults2.iterator();
        while (localIterator2.hasNext())
        {
          LocationSearchResult localLocationSearchResult = (LocationSearchResult)localIterator2.next();
          localLocationSearchResult.put("DivisionName", localEntitlementGroup2.getGroupName());
        }
        localLocationSearchResults1.addAll(localLocationSearchResults2);
      }
    }
    debug(paramSecureUser, "CashCon.getEntitledLocations");
    return localLocationSearchResults1;
  }
  
  private static void jdMethod_try(EntitlementGroup paramEntitlementGroup, EntitlementGroups paramEntitlementGroups)
    throws CSILException
  {
    if (paramEntitlementGroup.getEntGroupType().equals("Division")) {
      paramEntitlementGroups.add(paramEntitlementGroup);
    }
    Iterator localIterator = null;
    EntitlementGroups localEntitlementGroups = null;
    EntitlementGroup localEntitlementGroup = null;
    localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId());
    if (localEntitlementGroups != null)
    {
      localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup = (EntitlementGroup)localIterator.next();
        jdMethod_try(localEntitlementGroup, paramEntitlementGroups);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.CashCon
 * JD-Core Version:    0.7.0.1
 */