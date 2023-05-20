package com.ffusion.csil.core;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.approvals.ApprovalsHistory;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireBatches;
import com.ffusion.beans.wiretransfers.WireHistories;
import com.ffusion.beans.wiretransfers.WireHistory;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransferPayees;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.csil.handlers.WireHandler;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class Wire
  extends Initialize
{
  public static Entitlement ENT_WIRES = new Entitlement("Wires", null, null);
  public static Entitlement ENT_WIRES_CREATE = new Entitlement("Wires Create", null, null);
  public static Entitlement ENT_WIRES_RELEASE = new Entitlement("Wires Release", null, null);
  public static Entitlement ENT_BATCH_CREATE = new Entitlement("Wires Batch Create", null, null);
  public static Entitlement ENT_TEMPLATES_DELETE = new Entitlement("Wire Templates Delete", null, null);
  public static Entitlement ENT_WIRES_ADMIN_VIEW = new Entitlement("Wires Admin View", null, null);
  public static Entitlement ENT_WIRE_BENEFICIARY_MANAGEMENT = new Entitlement("Wire Beneficiary Management", null, null);
  public static BigDecimal maxAmount = new BigDecimal("1000000000000");
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Wire.initialize");
    WireHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return WireHandler.getService();
  }
  
  public static String getFormattedAuditLogMessage(boolean paramBoolean, String paramString, int paramInt, WireTransfer paramWireTransfer)
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    String str = "";
    if ((paramInt == 3300) || (paramInt == 3306))
    {
      if (paramBoolean) {
        str = "send a ";
      } else {
        str = "Sent a ";
      }
    }
    else if ((paramInt == 3301) || (paramInt == 3307))
    {
      if (paramBoolean) {
        str = "send a modified";
      } else {
        str = "Sent a modified";
      }
    }
    else if ((paramInt == 3302) || (paramInt == 3308)) {
      if (paramBoolean) {
        str = "cancel a";
      } else {
        str = "Cancelled a";
      }
    }
    if (paramBoolean)
    {
      localStringBuffer.append("Failed to ");
      localStringBuffer.append(str);
      localStringBuffer.append(paramString);
    }
    else
    {
      localStringBuffer.append(str);
      localStringBuffer.append(paramString);
    }
    localStringBuffer.append(" of ").append(paramWireTransfer.getAmount());
    if ((paramWireTransfer.getWireDestination() != null) && (paramWireTransfer.getWireDestination().equals("DRAWDOWN"))) {
      localStringBuffer.append(" to account ").append(paramWireTransfer.getFromAccountDisplayText());
    } else {
      localStringBuffer.append(" from account ").append(paramWireTransfer.getFromAccountDisplayText());
    }
    if (paramWireTransfer.getStatus() >= 100) {
      localStringBuffer.append(" that exceeded limits for approvals.");
    } else {
      localStringBuffer.append(".");
    }
    return localStringBuffer.toString();
  }
  
  public static void logWireAuditException(int paramInt, String paramString, SecureUser paramSecureUser, WireTransfer paramWireTransfer)
  {
    String str = getFormattedAuditLogMessage(true, paramString, paramInt, paramWireTransfer);
    audit(paramWireTransfer.getAuditRecord(paramSecureUser, str, paramInt, ""));
  }
  
  public static WireTransfer addWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.addWireTransfer";
    String str2 = " wire transfer ";
    int i = 3300;
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, paramWireTransfer, paramHashMap))
    {
      if (paramHashMap != null)
      {
        Entitlement localEntitlement = (Entitlement)paramHashMap.get("ENTITLEMENT_KEY");
        if (localEntitlement != null) {
          debug("User: " + paramSecureUser.getUserName() + " is not entitled to " + localEntitlement.getOperationName());
        }
      }
      else
      {
        debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
      }
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    paramWireTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
    if ((paramWireTransfer.getAmountValue() == null) || (paramWireTransfer.getAmountValue().getAmountValue().doubleValue() < 0.01D))
    {
      DebugLog.log(Level.SEVERE + str1 + ": Wire amount cannot be too small ");
      throw new CSILException(-1009, 31061);
    }
    paramWireTransfer.setOrigAmount(paramWireTransfer.getAmountForBPW());
    paramWireTransfer.setOrigCurrency(paramWireTransfer.getAmtCurrencyType());
    BigDecimal localBigDecimal = paramWireTransfer.getAmountValue().getAmountValue();
    if (!paramWireTransfer.getOrigCurrency().equals("USD")) {
      localBigDecimal = jdMethod_int(paramSecureUser, paramWireTransfer, paramHashMap);
    }
    if (paramWireTransfer.getWireSource().equals("TEMPLATE") == true)
    {
      localObject1 = paramWireTransfer.getWireLimitValue().getAmountValue();
      if ((((BigDecimal)localObject1).doubleValue() > 0.0D) && (localBigDecimal.doubleValue() > ((BigDecimal)localObject1).doubleValue()))
      {
        logWireAuditException(i, str2, paramSecureUser, paramWireTransfer);
        throw new CSILException(-1009, 31018);
      }
    }
    Object localObject1 = paramWireTransfer.getWirePayeeID();
    Object localObject2;
    if ((localObject1 == null) || (((String)localObject1).length() == 0))
    {
      localObject2 = paramWireTransfer.getWirePayee();
      if (localObject2 != null) {
        ((WireTransferPayee)localObject2).setTrackingID(TrackingIDGenerator.GetNextID());
      }
    }
    try
    {
      paramWireTransfer = WireHandler.addWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
      localObject2 = "ADDED";
      if (paramWireTransfer.getStatus() >= 100) {
        localObject2 = "APPROVAL_PENDING";
      }
      WireUtil.logWireTransfer(paramSecureUser, paramWireTransfer, i, getFormattedAuditLogMessage(false, str2, i, paramWireTransfer), (String)localObject2);
    }
    catch (CSILException localCSILException1)
    {
      logWireAuditException(i, str2, paramSecureUser, paramWireTransfer);
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer.getTrackingID());
    paramWireTransfer.logCreate(paramSecureUser, localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return paramWireTransfer;
  }
  
  public static WireTransfer addWireTemplate(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    return addWireTemplate(paramSecureUser, paramWireTransfer, true, paramHashMap);
  }
  
  public static WireTransfer addWireTemplate(SecureUser paramSecureUser, WireTransfer paramWireTransfer, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.addWireTemplate";
    String str2 = " wire template ";
    int i = 3306;
    paramWireTransfer.setAutoEntitleTransaction(paramBoolean);
    ValueInfo localValueInfo = new ValueInfo();
    localValueInfo.setValue(Boolean.toString(paramWireTransfer.getAutoEntitleTransaction()));
    if (paramWireTransfer.getWireScope().equals("USER")) {
      localValueInfo.setValue("ignore");
    }
    localValueInfo.setAction("add");
    paramWireTransfer.put("AUTOENTITLE_WIRE_TRANSFER", localValueInfo);
    if (!WireEntitlementUtil.checkWireTemplateEntitlements(paramSecureUser, paramWireTransfer, paramHashMap)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    paramWireTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
    paramWireTransfer.setAmount(paramWireTransfer.getWireLimitValue());
    paramWireTransfer.setOrigAmount(paramWireTransfer.getAmountForBPW());
    paramWireTransfer.setOrigCurrency(paramWireTransfer.getAmtCurrencyType());
    BigDecimal localBigDecimal = paramWireTransfer.getWireLimitValue().getAmountValue();
    if (!paramWireTransfer.getOrigCurrency().equals("USD"))
    {
      localBigDecimal = jdMethod_int(paramSecureUser, paramWireTransfer, paramHashMap);
      paramWireTransfer.setWireLimit(new BigDecimal(localBigDecimal.doubleValue()));
    }
    String str3 = paramWireTransfer.getWirePayeeID();
    if ((str3 == null) || (str3.length() == 0))
    {
      WireTransferPayee localWireTransferPayee = paramWireTransfer.getWirePayee();
      if (localWireTransferPayee != null) {
        localWireTransferPayee.setTrackingID(TrackingIDGenerator.GetNextID());
      }
    }
    try
    {
      paramWireTransfer = WireHandler.addWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
      if (paramWireTransfer.getWireDestination().equals("HOST")) {
        paramWireTransfer.setFromAccountID("HOST");
      }
    }
    catch (CSILException localCSILException1)
    {
      logWireAuditException(i, str2, paramSecureUser, paramWireTransfer);
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer.getTrackingID());
    paramWireTransfer.logCreate(paramSecureUser, localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return paramWireTransfer;
  }
  
  public static void modifyWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer1, WireTransfer paramWireTransfer2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.modifyWireTransfer";
    String str2 = " wire transfer ";
    int i = 3301;
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, paramWireTransfer1, paramHashMap))
    {
      if (paramHashMap != null)
      {
        Entitlement localEntitlement = (Entitlement)paramHashMap.get("ENTITLEMENT_KEY");
        if (localEntitlement != null) {
          debug("User: " + paramSecureUser.getUserName() + " is not entitled to " + localEntitlement.getOperationName());
        }
      }
      else
      {
        debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
      }
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    BigDecimal localBigDecimal = paramWireTransfer1.getAmountValue().getAmountValue();
    if (!paramWireTransfer1.getOrigCurrency().equals("USD"))
    {
      localBigDecimal = jdMethod_for(paramSecureUser, paramWireTransfer1, paramWireTransfer2, paramHashMap);
    }
    else
    {
      localBigDecimal = new BigDecimal(paramWireTransfer1.getOrigAmount());
      paramWireTransfer1.setAmount(paramWireTransfer1.getOrigAmount());
    }
    if (paramWireTransfer1.getWireSource().equals("TEMPLATE") == true)
    {
      localObject1 = paramWireTransfer1.getWireLimitValue().getAmountValue();
      if ((((BigDecimal)localObject1).doubleValue() > 0.0D) && (localBigDecimal.doubleValue() > ((BigDecimal)localObject1).doubleValue()))
      {
        logWireAuditException(i, str2, paramSecureUser, paramWireTransfer1);
        throw new CSILException(-1009, 31018);
      }
    }
    Object localObject1 = paramWireTransfer1.getWirePayeeID();
    Object localObject2;
    if ((localObject1 == null) || (((String)localObject1).length() == 0))
    {
      localObject2 = paramWireTransfer1.getWirePayee();
      if (localObject2 != null) {
        ((WireTransferPayee)localObject2).setTrackingID(TrackingIDGenerator.GetNextID());
      }
    }
    try
    {
      WireHandler.modifyWireTransfer(paramSecureUser, paramWireTransfer1, paramHashMap);
      localObject2 = "MODIFIED";
      if (paramWireTransfer1.getStatus() >= 100) {
        localObject2 = "APPROVAL_PENDING";
      }
    }
    catch (CSILException localCSILException1)
    {
      logWireAuditException(i, str2, paramSecureUser, paramWireTransfer1);
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer1.getTrackingID());
    paramWireTransfer1.logChanges(paramSecureUser, localHistoryTracker, paramWireTransfer2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void modifyWireTemplate(SecureUser paramSecureUser, WireTransfer paramWireTransfer1, WireTransfer paramWireTransfer2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.modifyWireTemplate";
    String str2 = " wire template ";
    int i = 3307;
    if (!WireEntitlementUtil.checkWireTemplateEntitlements(paramSecureUser, paramWireTransfer1, paramHashMap)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    if (!paramWireTransfer1.getOrigCurrency().equals("USD"))
    {
      localBigDecimal = jdMethod_for(paramSecureUser, paramWireTransfer1, paramWireTransfer2, paramHashMap);
    }
    else
    {
      localBigDecimal = new BigDecimal(paramWireTransfer1.getOrigAmount());
      paramWireTransfer1.setAmount(paramWireTransfer1.getOrigAmount());
    }
    paramWireTransfer1.setWireLimit(paramWireTransfer1.getAmountValue());
    String str3 = paramWireTransfer1.getWirePayeeID();
    if ((str3 == null) || (str3.length() == 0))
    {
      localObject = paramWireTransfer1.getWirePayee();
      if (localObject != null) {
        ((WireTransferPayee)localObject).setTrackingID(TrackingIDGenerator.GetNextID());
      }
    }
    Object localObject = new ValueInfo();
    if (paramWireTransfer1.get("AUTOENTITLE_WIRE_TRANSFER") != null) {
      ((ValueInfo)localObject).setAction("mod");
    } else {
      ((ValueInfo)localObject).setAction("add");
    }
    if ((paramWireTransfer2.getWireScope().equals("USER")) && ((paramWireTransfer1.getWireScope().equals("BUSINESS")) || (paramWireTransfer1.getWireScope().equals("BANK")))) {
      ((ValueInfo)localObject).setValue(Boolean.toString(paramWireTransfer1.getAutoEntitleTransaction()));
    } else {
      ((ValueInfo)localObject).setValue("ignore");
    }
    paramWireTransfer1.put("AUTOENTITLE_WIRE_TRANSFER", localObject);
    try
    {
      WireHandler.modifyWireTransfer(paramSecureUser, paramWireTransfer1, paramHashMap);
      if (paramWireTransfer1.getWireDestination().equals("HOST")) {
        paramWireTransfer1.setFromAccountID("HOST");
      }
    }
    catch (CSILException localCSILException1)
    {
      logWireAuditException(i, str2, paramSecureUser, paramWireTransfer1);
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer1.getTrackingID());
    paramWireTransfer1.logChanges(paramSecureUser, localHistoryTracker, paramWireTransfer2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void deleteWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.deleteWireTransfer";
    String str2 = " wire transfer ";
    int i = 3302;
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, paramWireTransfer, paramHashMap))
    {
      if (paramHashMap != null)
      {
        Entitlement localEntitlement = (Entitlement)paramHashMap.get("ENTITLEMENT_KEY");
        if (localEntitlement != null) {
          debug("User: " + paramSecureUser.getUserName() + " is not entitled to " + localEntitlement.getOperationName());
        }
      }
      else
      {
        debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
      }
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    try
    {
      WireHandler.deleteWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
      WireUtil.logWireTransfer(paramSecureUser, paramWireTransfer, i, getFormattedAuditLogMessage(false, str2, i, paramWireTransfer), "DELETED");
    }
    catch (CSILException localCSILException1)
    {
      logWireAuditException(i, str2, paramSecureUser, paramWireTransfer);
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer.getTrackingID());
    paramWireTransfer.logDelete(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void deleteWireTemplate(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.deleteWireTemplate";
    String str2 = " wire template ";
    int i = 3308;
    if (!WireEntitlementUtil.checkWireTemplateEntitlements(paramSecureUser, paramWireTransfer, paramHashMap)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    BigDecimal localBigDecimal = paramWireTransfer.getWireLimitValue().getAmountValue();
    try
    {
      WireHandler.deleteWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
      if (paramWireTransfer.getWireDestination().equals("HOST")) {
        paramWireTransfer.setFromAccountID("HOST");
      }
      audit(paramWireTransfer.getAuditRecord(paramSecureUser, getFormattedAuditLogMessage(false, str2, i, paramWireTransfer), i, "TEMPLATE_DELETED"));
    }
    catch (CSILException localCSILException1)
    {
      logWireAuditException(i, str2, paramSecureUser, paramWireTransfer);
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer.getTrackingID());
    paramWireTransfer.logDelete(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static WireTransfers getWireTransfers(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getWireTransfers";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransfers localWireTransfers = WireHandler.getWireTransfers(paramSecureUser, paramWireTransfer, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransfers;
  }
  
  public static WireTransfers getWireTransfersByBatchId(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getWireTransfersByBatchId";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransfers localWireTransfers = WireHandler.getWireTransfersByBatchId(paramSecureUser, paramString, paramHashMap);
    if (localWireTransfers != null)
    {
      Iterator localIterator = localWireTransfers.iterator();
      while (localIterator.hasNext())
      {
        WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
        if ((localWireTransfer.getStatus() == 100) || (localWireTransfer.getStatus() == 101)) {
          Util.insertApprovalInfo(localWireTransfer, paramSecureUser, null, paramHashMap);
        }
        localWireTransfer.setCanEdit(false);
        localWireTransfer.setCanDelete(false);
        int i = localWireTransfer.getStatus();
        if (((i == 1) || (i == 21) || (i == 2) || (i == 105) || (i == 100) || (i == 101)) && (WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, paramHashMap) == true))
        {
          localWireTransfer.setCanEdit(true);
          localWireTransfer.setCanDelete(true);
        }
      }
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransfers;
  }
  
  public static String getWireTemplateResultSize()
  {
    return WireHandler.getWireTemplateResultSize();
  }
  
  public static WireTransfers getAllWireTemplates(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getAllWireTemplates";
    long l = System.currentTimeMillis();
    WireTransfers localWireTransfers = WireHandler.getAllWireTemplates(paramSecureUser, paramInt, paramHashMap);
    return localWireTransfers;
  }
  
  public static WireTransfers getWireTemplates(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getWireTemplates";
    debug(paramSecureUser, str1);
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    WireTransfers localWireTransfers = WireHandler.getWireTemplates(paramSecureUser, paramPagingContext, paramWireTransfer, paramHashMap);
    if (paramSecureUser.getUserType() == 2) {
      return localWireTransfers;
    }
    String str2 = (String)paramHashMap.get("PENDING_APPROVAL");
    if ((str2.equalsIgnoreCase("true")) && (paramPagingContext.isLastPage()))
    {
      localObject1 = getApprovalTemplates(paramSecureUser, paramWireTransfer, paramHashMap);
      try
      {
        localWireTransfers.addAll((Collection)localObject1);
      }
      catch (Exception localException)
      {
        DebugLog.log(str1 + ": Failed to merge templates collection" + " retrieved from BPW and Approvals.");
        localException.printStackTrace(System.err);
      }
    }
    Object localObject1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Iterator localIterator = localWireTransfers.iterator();
    while (localIterator.hasNext())
    {
      WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
      Object localObject2;
      if (!localWireTransfer.getWireScope().equals("BANK"))
      {
        localObject2 = new MultiEntitlement();
        ((MultiEntitlement)localObject2).setOperations(new String[] { "Wire Templates Create" });
        ((MultiEntitlement)localObject2).setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localWireTransfer) });
        Entitlement localEntitlement = EntitlementsUtil.checkAccountEntitlement((EntitlementGroupMember)localObject1, (MultiEntitlement)localObject2, paramSecureUser.getBusinessID());
        if (localEntitlement == null) {
          localWireTransfer.setCanEdit(true);
        }
        ((MultiEntitlement)localObject2).setOperations(new String[] { "Wire Templates Delete" });
        localEntitlement = EntitlementsUtil.checkAccountEntitlement((EntitlementGroupMember)localObject1, (MultiEntitlement)localObject2, paramSecureUser.getBusinessID());
        if (localEntitlement == null) {
          localWireTransfer.setCanDelete(true);
        }
      }
      if (localWireTransfer.getStatus() < 100)
      {
        localObject2 = WireEntitlementUtil.getEntitlementFlowName(localWireTransfer.getWireDestination(), "TEMPLATE");
        if (WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, (String)localObject2, paramHashMap) == true) {
          localWireTransfer.setCanLoad(true);
        }
      }
    }
    return localWireTransfers;
  }
  
  public static WireTransfer getWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getWireTransferById";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransfer localWireTransfer = WireHandler.getWireTransferById(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransfer;
  }
  
  public static WireTransfer getRecWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getRecWireTransferById";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransfer localWireTransfer = WireHandler.getRecWireTransferById(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransfer;
  }
  
  public static WireTransfer getCompletedWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getCompletedWireTransferById";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransfer localWireTransfer = WireHandler.getCompletedWireTransferById(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransfer;
  }
  
  public static WireTransfers getPagedPendingWires(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getPagedPendingWires";
    debug(paramSecureUser, str1);
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL"))) {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
    } else {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    }
    long l = System.currentTimeMillis();
    WireTransfers localWireTransfers = WireHandler.getPagedPendingWires(paramSecureUser, paramPagingContext, paramHashMap);
    PerfLog.log(str1, l, true);
    return localWireTransfers;
  }
  
  public static WireBatches getPagedPendingWireBatches(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getPagedPendingWireBatches";
    debug(paramSecureUser, str1);
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL"))) {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
    } else {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    }
    long l = System.currentTimeMillis();
    WireBatches localWireBatches = WireHandler.getPagedPendingWireBatches(paramSecureUser, paramPagingContext, paramHashMap);
    if (localWireBatches != null)
    {
      Iterator localIterator = localWireBatches.iterator();
      while (localIterator.hasNext())
      {
        WireBatch localWireBatch = (WireBatch)localIterator.next();
        WireUtil.setCanEditDeleteForBatch(paramSecureUser, localWireBatch, paramHashMap);
      }
    }
    PerfLog.log(str1, l, true);
    return localWireBatches;
  }
  
  public static WireTransfers getPagedCompletedWires(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getPagedCompletedWires";
    debug(paramSecureUser, str1);
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL"))) {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
    } else {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    }
    long l = System.currentTimeMillis();
    WireTransfers localWireTransfers = WireHandler.getPagedCompletedWires(paramSecureUser, paramPagingContext, paramHashMap);
    PerfLog.log(str1, l, true);
    return localWireTransfers;
  }
  
  public static WireBatches getPagedCompletedWireBatches(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getPagedCompletedWireBatches";
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL"))) {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
    } else {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    }
    long l = System.currentTimeMillis();
    WireBatches localWireBatches = WireHandler.getPagedCompletedWireBatches(paramSecureUser, paramPagingContext, paramHashMap);
    if (localWireBatches != null)
    {
      Iterator localIterator = localWireBatches.iterator();
      while (localIterator.hasNext())
      {
        WireBatch localWireBatch = (WireBatch)localIterator.next();
        WireUtil.setCanEditDeleteForBatch(paramSecureUser, localWireBatch, paramHashMap);
      }
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localWireBatches;
  }
  
  public static WireTransfers getApprovalWires(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getApprovalWires";
    long l = System.currentTimeMillis();
    HashMap localHashMap = new HashMap();
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL")))
    {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
      localHashMap.put("BusinessID", new Integer(paramSecureUser.getBusinessID()));
    }
    else
    {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
      localHashMap.put("SubmittingUser", new Integer(paramSecureUser.getProfileID()));
    }
    WireTransfers localWireTransfers = WireUtil.getApprovalItems(paramSecureUser, paramArrayOfString, localHashMap, paramHashMap);
    if (paramHashMap != null)
    {
      String str3 = (String)paramHashMap.get("DESTINATION");
      if ((str3 != null) && (str3.length() > 0)) {
        localWireTransfers = WireUtil.filterWiresByDest(localWireTransfers, str3);
      }
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return WireUtil.filterWiresByType(localWireTransfers, "WIRE");
  }
  
  public static WireTransfers getApprovalTemplates(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getApprovalTemplates";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    long l = System.currentTimeMillis();
    HashMap localHashMap = new HashMap();
    String str2 = paramWireTransfer.getWireScope();
    if ((str2 != null) && (str2.equals("USER"))) {
      localHashMap.put("SubmittingUser", new Integer(paramSecureUser.getProfileID()));
    } else {
      localHashMap.put("BusinessID", new Integer(paramWireTransfer.getCustomerID()));
    }
    String[] arrayOfString = { "Pending", "Rejected" };
    WireTransfers localWireTransfers = WireUtil.getApprovalItems(paramSecureUser, arrayOfString, localHashMap, paramHashMap);
    localWireTransfers.addAll(WireUtil.getRecApprovalItems(paramSecureUser, arrayOfString, localHashMap, paramHashMap));
    localWireTransfers = WireUtil.filterWiresByType(localWireTransfers, "TEMPLATE");
    String str3 = paramWireTransfer.getWireDestination();
    localWireTransfers = WireUtil.filterTemplatesByScopeAndDest(localWireTransfers, str2, str3);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localWireTransfers;
  }
  
  public static WireBatches getApprovalWireBatches(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getApprovalWireBatches";
    HashMap localHashMap = new HashMap();
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL")))
    {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
      localHashMap.put("BusinessID", new Integer(paramSecureUser.getBusinessID()));
    }
    else
    {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
      localHashMap.put("SubmittingUser", new Integer(paramSecureUser.getProfileID()));
    }
    WireBatches localWireBatches = new WireBatches();
    localHashMap.put("ItemSubType", new Integer(10));
    ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, localHashMap, paramHashMap);
    Object localObject1;
    if (localApprovalsStatuses != null)
    {
      localObject1 = localApprovalsStatuses.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)((Iterator)localObject1).next();
        ApprovalsItem localApprovalsItem = localApprovalsStatus.getApprovalItem();
        WireBatch localWireBatch = (WireBatch)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
        localWireBatch.setID(String.valueOf(localApprovalsItem.getItemID()));
        if ((localApprovalsStatus.getDecision() != null) && (localApprovalsStatus.getDecision().equals("Rejected")))
        {
          localWireBatch.setStatus(101);
          localObject2 = localApprovalsItem.getApprovalItemProperty("RejectReason");
          if ((localObject2 != null) && (((String)localObject2).trim().length() > 0)) {
            localWireBatch.setRejectReason((String)localObject2);
          }
        }
        else
        {
          localWireBatch.setStatus(100);
        }
        localWireBatch.setCanEdit(true);
        localWireBatch.setCanDelete(true);
        localWireBatch.setType(14);
        Object localObject2 = localWireBatch.getWires();
        if (localObject2 != null) {
          for (int i = 0; i < ((WireTransfers)localObject2).size(); i++)
          {
            WireTransfer localWireTransfer = (WireTransfer)((WireTransfers)localObject2).get(i);
            localWireTransfer.setCanEdit(true);
            localWireTransfer.setCanDelete(true);
            localWireTransfer.setAction("");
          }
        }
        localWireBatches.add(localWireBatch);
      }
    }
    if (paramHashMap != null)
    {
      localObject1 = (String)paramHashMap.get("DESTINATION");
      if ((localObject1 != null) && (((String)localObject1).length() > 0)) {
        localWireBatches = WireUtil.filterWireBatchesByDest(localWireBatches, (String)localObject1);
      }
    }
    debug(paramSecureUser, str1);
    return localWireBatches;
  }
  
  public static com.ffusion.beans.accounts.Accounts getWiresAccounts(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.accounts.Accounts localAccounts1 = new com.ffusion.beans.accounts.Accounts();
    try
    {
      String str = "Wire.getWiresAccounts";
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
      com.ffusion.beans.accounts.Accounts localAccounts2 = null;
      if (paramHashMap != null) {
        localAccounts2 = (com.ffusion.beans.accounts.Accounts)paramHashMap.get("ACCOUNTS");
      }
      if ((localAccounts2 == null) || (localAccounts2.size() == 0)) {
        localAccounts2 = AccountHandler.getAccountsById(paramSecureUser, paramSecureUser.getBusinessID(), paramHashMap);
      }
      if (localAccounts2 == null)
      {
        DebugLog.log("WARNING: " + str + " - No Accounts found for specified Business!");
        return localAccounts1;
      }
      if (paramSecureUser.getUserType() == 2) {
        return localAccounts2;
      }
      Iterator localIterator = localAccounts2.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        if ((paramString != null) && (paramString.length() > 0))
        {
          if (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, paramString, paramHashMap) == true) {
            localAccounts1.add(localAccount);
          }
        }
        else if ((WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "DOMESTIC_FREEFORM_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "DOMESTIC_TEMPLATED_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "INTERNATIONAL_FREEFORM_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "INTERNATIONAL_TEMPLATED_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "BOOK_FREEFORM_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "BOOK_TEMPLATED_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "DRAWDOWN_FREEFORM_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "DRAWDOWN_TEMPLATED_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "FED_FREEFORM_WIRE", paramHashMap) == true) || (WireEntitlementUtil.checkAccountEntitlements(paramSecureUser, localAccount, "FED_TEMPLATED_WIRE", paramHashMap) == true)) {
          localAccounts1.add(localAccount);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localAccounts1;
  }
  
  public static WireTransferPayee addWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.addWireTransferPayee";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    if (paramWireTransferPayee.getTrackingID() == null) {
      paramWireTransferPayee.setTrackingID(TrackingIDGenerator.GetNextID());
    }
    WireHandler.addWireTransferPayee(paramSecureUser, paramWireTransferPayee, paramHashMap);
    audit(paramWireTransferPayee.getAuditRecord(paramSecureUser, "A wire beneficiary has been added.", 3303, "ADDED"));
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 13, paramWireTransferPayee.getID(), paramWireTransferPayee.getTrackingID());
    paramWireTransferPayee.logCreate(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localCSILException.toString());
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramWireTransferPayee;
  }
  
  public static void deleteWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.deleteWireTransferPayee";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    WireHandler.deleteWireTransferPayee(paramSecureUser, paramWireTransferPayee, paramHashMap);
    audit(paramWireTransferPayee.getAuditRecord(paramSecureUser, "A wire beneficiary has been deleted.", 3305, "DELETED"));
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 13, paramWireTransferPayee.getID(), paramWireTransferPayee.getTrackingID());
    paramWireTransferPayee.logDelete(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localCSILException.toString());
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static WireTransferPayee modifyWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee1, WireTransferPayee paramWireTransferPayee2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.modifyWireTransferPayee";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    WireTransferBank localWireTransferBank1 = paramWireTransferPayee2.getDestinationBank();
    WireTransferBank localWireTransferBank2 = paramWireTransferPayee1.getDestinationBank();
    WireHandler.modifyWireTransferPayee(paramSecureUser, paramWireTransferPayee1, paramHashMap);
    audit(paramWireTransferPayee1.getAuditRecord(paramSecureUser, "A wire beneficiary has been modified.", 3304, "MODIFIED"));
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 13, paramWireTransferPayee1.getID(), paramWireTransferPayee1.getTrackingID());
    paramWireTransferPayee1.logChanges(localHistoryTracker, paramWireTransferPayee2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localCSILException.toString());
    }
    if (paramWireTransferPayee1.getErrorCode() == 31056) {
      throw new CSILException(31056);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramWireTransferPayee1;
  }
  
  public static WireTransferPayees getWireTransferPayees(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getWireTransferPayees";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransferPayees localWireTransferPayees = WireHandler.getWireTransferPayees(paramSecureUser, paramWireTransferPayee, paramHashMap);
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    boolean bool = Entitlements.checkEntitlement(localEntitlementGroupMember, ENT_WIRE_BENEFICIARY_MANAGEMENT);
    Iterator localIterator = localWireTransferPayees.iterator();
    while (localIterator.hasNext())
    {
      WireTransferPayee localWireTransferPayee = (WireTransferPayee)localIterator.next();
      if ((bool == true) || (localWireTransferPayee.getPayeeScope().equalsIgnoreCase("USER")))
      {
        localWireTransferPayee.setCanEdit(true);
        localWireTransferPayee.setCanDelete(true);
      }
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransferPayees;
  }
  
  public static WireTransferPayee getWireTransferPayeeById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getWireTransferPayeesById";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransferPayee localWireTransferPayee = WireHandler.getWireTransferPayeeById(paramSecureUser, paramString, paramHashMap);
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    boolean bool = Entitlements.checkEntitlement(localEntitlementGroupMember, ENT_WIRE_BENEFICIARY_MANAGEMENT);
    if ((bool == true) || (localWireTransferPayee.getPayeeScope().equalsIgnoreCase("USER")))
    {
      localWireTransferPayee.setCanEdit(true);
      localWireTransferPayee.setCanDelete(true);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransferPayee;
  }
  
  public static WireTransferBanks getWireTransferBanks(SecureUser paramSecureUser, WireTransferBank paramWireTransferBank, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getWireTransferBanks";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransferBanks localWireTransferBanks = new WireTransferBanks();
    try
    {
      FinancialInstitution localFinancialInstitution1 = new FinancialInstitution();
      localFinancialInstitution1.setInstitutionName(paramWireTransferBank.getBankName());
      localFinancialInstitution1.setCity(paramWireTransferBank.getCity());
      localFinancialInstitution1.setStateCode(paramWireTransferBank.getState());
      localFinancialInstitution1.setCountry(paramWireTransferBank.getCountry());
      localFinancialInstitution1.set("NON_NULL_ROUTING_NUMBER", "fi.WireRoutingNumber,fi.SwiftBIC");
      if (!WireUtil.isNullOrEmpty(paramWireTransferBank.getRoutingFedWire())) {
        try
        {
          localFinancialInstitution1.setWireRoutingNumberForSearch(paramWireTransferBank.getRoutingFedWire());
        }
        catch (Exception localException)
        {
          DebugLog.log(str + ": BankLookup failed due to invalid FedABA routing number");
          localException.printStackTrace(System.err);
          throw new CSILException(-1009, 32009);
        }
      }
      if (!WireUtil.isNullOrEmpty(paramWireTransferBank.getRoutingSwift())) {
        localFinancialInstitution1.setSwiftBIC(paramWireTransferBank.getRoutingSwift());
      }
      if (!WireUtil.isNullOrEmpty(paramWireTransferBank.getRoutingChips())) {
        localFinancialInstitution1.setChipsUID(paramWireTransferBank.getRoutingChips());
      }
      if (!WireUtil.isNullOrEmpty(paramWireTransferBank.getRoutingOther())) {
        localFinancialInstitution1.setNationalID(paramWireTransferBank.getRoutingOther());
      }
      FinancialInstitutions localFinancialInstitutions = BankLookup.getFinancialInstitutions(paramSecureUser, localFinancialInstitution1, paramInt, paramHashMap);
      if (localFinancialInstitutions != null)
      {
        Iterator localIterator = localFinancialInstitutions.iterator();
        while (localIterator.hasNext())
        {
          FinancialInstitution localFinancialInstitution2 = (FinancialInstitution)localIterator.next();
          WireTransferBank localWireTransferBank = localWireTransferBanks.create();
          localWireTransferBank.setFI(localFinancialInstitution2);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      localCSILException.printStackTrace(System.err);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransferBanks;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getReportData";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    long l = System.currentTimeMillis();
    String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    com.ffusion.beans.accounts.Accounts localAccounts = Accounts.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("AccountsForReport", localAccounts);
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ENT_WIRES_ADMIN_VIEW) == true) {
      paramHashMap.put("VIEW", "ALL");
    }
    IReportResult localIReportResult = WireHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    String str3 = paramReportCriteria.getReportOptions().getProperty("TITLE");
    if ((str3 == null) || (str3.length() == 0)) {
      str3 = ReportConsts.getReportName(str2, Locale.getDefault());
    }
    audit(paramSecureUser, "Generate " + str3 + ".", TrackingIDGenerator.GetNextID(), 3313);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localIReportResult;
  }
  
  public static int getReleaseWiresCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getReleaseWiresCount";
    int i = 0;
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, ENT_WIRES_RELEASE)) {
      return i;
    }
    long l = System.currentTimeMillis();
    i = WireHandler.getReleaseWiresCount(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return i;
  }
  
  public static WireTransfers getReleaseWires(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getReleaseWiresCount";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES_RELEASE, str);
    long l = System.currentTimeMillis();
    WireTransfers localWireTransfers = WireHandler.getReleaseWires(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransfers;
  }
  
  public static void releaseWires(SecureUser paramSecureUser, WireTransfers paramWireTransfers, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.releaseWire";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES_RELEASE, str);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    WireTransfers localWireTransfers = new WireTransfers();
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    for (int i = 0; i < paramWireTransfers.size(); i++)
    {
      WireTransfer localWireTransfer = (WireTransfer)paramWireTransfers.get(i);
      if (localWireTransfer.getStatus() != 25)
      {
        if (localWireTransfer.getStatus() == 18)
        {
          DateTime localDateTime = new DateTime(paramSecureUser.getLocale());
          Limits localLimits = Entitlements.checkLimitsAdd(localEntitlementGroupMember, ENT_WIRES_RELEASE, localWireTransfer.getAmountValue().getAmountValue(), localDateTime.getTime());
          if (localWireTransfer.getWireDestination().equals("HOST")) {
            localWireTransfer.setFromAccountID("HOST");
          }
        }
        localWireTransfers.add(localWireTransfer);
      }
    }
    long l = System.currentTimeMillis();
    WireHandler.releaseWires(paramSecureUser, localWireTransfers, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static WireBatch addWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.addWireBatch";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "BATCH_WIRE"))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str);
      throw new CSILException(str, 20001);
    }
    WireTransfers localWireTransfers = paramWireBatch.getWires();
    for (int i = 0; i < localWireTransfers.size(); i++)
    {
      WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.get(i);
      localWireTransfer.setWireSource(paramWireBatch.getBatchType());
      localWireTransfer.setWireDestination(paramWireBatch.getBatchDestination());
      if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, paramHashMap))
      {
        if (paramHashMap != null)
        {
          Entitlement localEntitlement = (Entitlement)paramHashMap.get("ENTITLEMENT_KEY");
          if (localEntitlement != null) {
            debug("User: " + paramSecureUser.getUserName() + " is not entitled to " + localEntitlement.getOperationName());
          }
        }
        else
        {
          debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str);
        }
        throw new CSILException(str, 20001);
      }
    }
    long l = System.currentTimeMillis();
    paramWireBatch.setTrackingID(TrackingIDGenerator.GetNextID());
    paramWireBatch.setOrigAmount(paramWireBatch.calculateTotalAmount().getAmountValue().toString());
    paramWireBatch.setOrigCurrency(paramWireBatch.getAmtCurrencyType());
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if (!paramWireBatch.getOrigCurrency().equals("USD")) {
      jdMethod_for(paramSecureUser, paramWireBatch, paramWireBatch.getWires(), true, paramHashMap);
    } else {
      for (j = 0; j < localWireTransfers.size(); j++)
      {
        localObject1 = (WireTransfer)localWireTransfers.get(j);
        if (paramWireBatch.getBatchType().equals("TEMPLATE") == true)
        {
          localObject2 = ((WireTransfer)localObject1).getAmountValue().getAmountValue();
          localObject3 = ((WireTransfer)localObject1).getWireLimitValue().getAmountValue();
          if ((((BigDecimal)localObject3).doubleValue() > 0.0D) && (((BigDecimal)localObject2).doubleValue() > ((BigDecimal)localObject3).doubleValue())) {
            throw new CSILException(-1009, 31019);
          }
        }
        ((WireTransfer)localObject1).setOrigAmount(((WireTransfer)localObject1).getAmountForBPW());
      }
    }
    if (paramWireBatch.getBatchDestination().equals("DRAWDOWN"))
    {
      paramWireBatch.setTotalCredit(paramWireBatch.calculateTotalAmount());
      paramWireBatch.setAmount(paramWireBatch.getTotalCreditValue());
    }
    else
    {
      paramWireBatch.setTotalDebit(paramWireBatch.calculateTotalAmount());
      paramWireBatch.setAmount(paramWireBatch.getTotalDebitValue());
    }
    paramWireBatch.setWireCount(paramWireBatch.getWires().size());
    for (int j = 0; j < localWireTransfers.size(); j++)
    {
      localObject1 = (WireTransfer)localWireTransfers.get(j);
      ((WireTransfer)localObject1).setTrackingID(TrackingIDGenerator.GetNextID());
      localObject2 = ((WireTransfer)localObject1).getWirePayeeID();
      if ((localObject2 == null) || (((String)localObject2).length() == 0))
      {
        localObject3 = ((WireTransfer)localObject1).getWirePayee();
        if (localObject3 != null) {
          ((WireTransferPayee)localObject3).setTrackingID(TrackingIDGenerator.GetNextID());
        }
      }
    }
    try
    {
      paramWireBatch = WireHandler.addWireBatch(paramSecureUser, paramWireBatch, paramHashMap);
      WireUtil.logWireBatch(paramSecureUser, paramWireBatch, 3309, ".");
    }
    catch (CSILException localCSILException1)
    {
      localObject1 = new StringBuffer("Failed to add a wire batch of amount ");
      ((StringBuffer)localObject1).append(paramWireBatch.getAmount());
      if (paramWireBatch.getStatus() >= 100) {
        ((StringBuffer)localObject1).append(" that exceeded limits for approvals.");
      } else {
        ((StringBuffer)localObject1).append(".");
      }
      audit(paramWireBatch.getAuditRecord(paramSecureUser, ((StringBuffer)localObject1).toString(), 3309, ""));
      throw localCSILException1;
    }
    Histories localHistories = paramWireBatch.logCreate(paramSecureUser, (ILocalizable)null);
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistories, null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localCSILException2.toString());
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramWireBatch;
  }
  
  public static void modifyWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch1, WireBatch paramWireBatch2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.modifyWireBatch";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "BATCH_WIRE"))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
      throw new CSILException(str1, 20001);
    }
    WireTransfers localWireTransfers = paramWireBatch1.getWires();
    for (int i = 0; i < localWireTransfers.size(); i++)
    {
      WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.get(i);
      localWireTransfer.setWireSource(paramWireBatch1.getBatchType());
      localWireTransfer.setWireDestination(paramWireBatch1.getBatchDestination());
      if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, paramHashMap))
      {
        if (paramHashMap != null)
        {
          Entitlement localEntitlement = (Entitlement)paramHashMap.get("ENTITLEMENT_KEY");
          if (localEntitlement != null) {
            debug("User: " + paramSecureUser.getUserName() + " is not entitled to " + localEntitlement.getOperationName());
          }
        }
        else
        {
          debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
        }
        throw new CSILException(str1, 20001);
      }
    }
    long l = System.currentTimeMillis();
    paramWireBatch1.setOrigAmount(paramWireBatch1.calculateTotalOrigAmount());
    Object localObject2;
    Object localObject3;
    if (!paramWireBatch1.getOrigCurrency().equals("USD")) {
      jdMethod_for(paramSecureUser, paramWireBatch1, paramWireBatch2, paramHashMap);
    } else {
      for (int j = 0; j < localWireTransfers.size(); j++)
      {
        localObject1 = (WireTransfer)localWireTransfers.get(j);
        if (((WireTransfer)localObject1).getAction().equals("add") == true) {
          ((WireTransfer)localObject1).setOrigAmount(((WireTransfer)localObject1).getAmountForBPW());
        } else {
          ((WireTransfer)localObject1).setAmount(((WireTransfer)localObject1).getOrigAmount());
        }
        if (paramWireBatch1.getBatchType().equals("TEMPLATE") == true)
        {
          localObject2 = ((WireTransfer)localObject1).getAmountValue().getAmountValue();
          localObject3 = ((WireTransfer)localObject1).getWireLimitValue().getAmountValue();
          if ((((BigDecimal)localObject3).doubleValue() > 0.0D) && (((BigDecimal)localObject2).doubleValue() > ((BigDecimal)localObject3).doubleValue())) {
            throw new CSILException(-1009, 31019);
          }
        }
      }
    }
    if (paramWireBatch1.getBatchDestination().equals("DRAWDOWN"))
    {
      paramWireBatch1.setTotalCredit(paramWireBatch1.calculateTotalAmount());
      paramWireBatch1.setAmount(paramWireBatch1.getTotalCreditValue());
    }
    else
    {
      paramWireBatch1.setTotalDebit(paramWireBatch1.calculateTotalAmount());
      paramWireBatch1.setAmount(paramWireBatch1.getTotalDebitValue());
    }
    paramWireBatch1.setWireCount(paramWireBatch1.calculateWireCount());
    String str2 = paramWireBatch1.getDueDate();
    Object localObject1 = paramWireBatch2.getDueDate();
    if (!str2.equals(localObject1))
    {
      jdMethod_for(paramWireBatch1);
    }
    else if ("INTERNATIONAL".equals(paramWireBatch1.getBatchDestination()))
    {
      localObject2 = paramWireBatch1.getSettlementDate();
      localObject3 = paramWireBatch2.getSettlementDate();
      if (!((String)localObject2).equals(localObject3)) {
        jdMethod_for(paramWireBatch1);
      }
    }
    for (int k = 0; k < localWireTransfers.size(); k++)
    {
      localObject3 = (WireTransfer)localWireTransfers.get(k);
      if (((WireTransfer)localObject3).getAction().equals("add")) {
        ((WireTransfer)localObject3).setTrackingID(TrackingIDGenerator.GetNextID());
      }
      String str3 = ((WireTransfer)localObject3).getWirePayeeID();
      if ((str3 == null) || (str3.length() == 0))
      {
        WireTransferPayee localWireTransferPayee = ((WireTransfer)localObject3).getWirePayee();
        if (localWireTransferPayee != null) {
          localWireTransferPayee.setTrackingID(TrackingIDGenerator.GetNextID());
        }
      }
    }
    try
    {
      WireHandler.modifyWireBatch(paramSecureUser, paramWireBatch1, paramHashMap);
      WireUtil.logWireBatch(paramSecureUser, paramWireBatch1, 3310, ".");
    }
    catch (CSILException localCSILException1)
    {
      localObject3 = new StringBuffer("Failed to send a modified wire batch of amount ");
      ((StringBuffer)localObject3).append(paramWireBatch1.getAmount());
      if (paramWireBatch1.getStatus() >= 100) {
        ((StringBuffer)localObject3).append(" that exceeded limits for approvals.");
      } else {
        ((StringBuffer)localObject3).append(".");
      }
      audit(paramWireBatch1.getAuditRecord(paramSecureUser, ((StringBuffer)localObject3).toString(), 3310, ""));
      throw localCSILException1;
    }
    Histories localHistories = paramWireBatch1.logChanges(paramSecureUser, paramWireBatch2, (ILocalizable)null);
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistories, null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void deleteWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.deleteWireBatch";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "BATCH_WIRE"))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    WireTransfers localWireTransfers = paramWireBatch.getWires();
    Object localObject;
    for (int i = 0; i < localWireTransfers.size(); i++)
    {
      localObject = (WireTransfer)localWireTransfers.get(i);
      ((WireTransfer)localObject).setWireSource(paramWireBatch.getBatchType());
      ((WireTransfer)localObject).setWireDestination(paramWireBatch.getBatchDestination());
      if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, (WireTransfer)localObject, paramHashMap))
      {
        if (paramHashMap != null)
        {
          Entitlement localEntitlement = (Entitlement)paramHashMap.get("ENTITLEMENT_KEY");
          if (localEntitlement != null) {
            debug("User: " + paramSecureUser.getUserName() + " is not entitled to " + localEntitlement.getOperationName());
          }
        }
        else
        {
          debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
        }
        throw new CSILException(str1, 20001);
      }
    }
    try
    {
      WireHandler.deleteWireBatch(paramSecureUser, paramWireBatch, paramHashMap);
      String str2 = "";
      if (paramWireBatch.getStatus() >= 100) {
        str2 = "that exceeded limits from approvals.";
      } else {
        str2 = ".";
      }
      WireUtil.logWireBatch(paramSecureUser, paramWireBatch, 3311, str2);
    }
    catch (CSILException localCSILException1)
    {
      localObject = new StringBuffer("Failed to cancel a wire batch of amount ");
      ((StringBuffer)localObject).append(paramWireBatch.getAmount());
      if (paramWireBatch.getStatus() >= 100) {
        ((StringBuffer)localObject).append(" that exceeded limits for approvals.");
      } else {
        ((StringBuffer)localObject).append(".");
      }
      audit(paramWireBatch.getAuditRecord(paramSecureUser, ((StringBuffer)localObject).toString(), 3311, ""));
      throw localCSILException1;
    }
    Histories localHistories = paramWireBatch.logDelete(paramSecureUser, (ILocalizable)null);
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistories, null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static WireTransfer addHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.addHostWire";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "HOST_WIRE"))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    paramWireTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
    try
    {
      paramWireTransfer = WireHandler.addHostWire(paramSecureUser, paramWireTransfer, paramHashMap);
      if (paramWireTransfer.getWireDestination().equals("HOST")) {
        paramWireTransfer.setFromAccountID("HOST");
      }
      String str2 = "";
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("Sent a host wire of amount ");
      localStringBuffer.append(paramWireTransfer.getAmount());
      if (paramWireTransfer.getStatus() >= 100)
      {
        localStringBuffer.append(" that exceeded limits for approvals.");
        str2 = "APPROVAL_PENDING";
      }
      else
      {
        localStringBuffer.append(".");
        str2 = "ADDED";
      }
      audit(paramWireTransfer.getAuditRecord(paramSecureUser, localStringBuffer.toString(), 3300, str2));
    }
    catch (CSILException localCSILException1)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Failed to send a host wire of amount ");
      localStringBuffer.append(paramWireTransfer.getAmount());
      if (paramWireTransfer.getStatus() >= 100) {
        localStringBuffer.append(" that exceeded limits for approvals.");
      } else {
        localStringBuffer.append(".");
      }
      audit(paramWireTransfer.getAuditRecord(paramSecureUser, localStringBuffer.toString(), 3300, ""));
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer.getTrackingID());
    paramWireTransfer.logCreate(paramSecureUser, localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return paramWireTransfer;
  }
  
  public static void modifyHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer1, WireTransfer paramWireTransfer2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.modifyHostWire";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "HOST_WIRE"))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str1);
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    try
    {
      WireHandler.modifyHostWire(paramSecureUser, paramWireTransfer1, paramHashMap);
      if (paramWireTransfer1.getWireDestination().equals("HOST")) {
        paramWireTransfer1.setFromAccountID("HOST");
      }
      String str2 = "";
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("Sent a modified host wire of amount ");
      localStringBuffer.append(paramWireTransfer1.getAmount());
      if (paramWireTransfer1.getStatus() >= 100)
      {
        localStringBuffer.append(" that exceeded limits for approvals.");
        str2 = "APPROVAL_PENDING";
      }
      else
      {
        localStringBuffer.append(".");
        str2 = "MODIFIED";
      }
      audit(paramWireTransfer1.getAuditRecord(paramSecureUser, localStringBuffer.toString(), 3301, str2));
    }
    catch (CSILException localCSILException1)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Failed to send a modified host wire of amount ");
      localStringBuffer.append(paramWireTransfer1.getAmount());
      if (paramWireTransfer1.getStatus() >= 100) {
        localStringBuffer.append(" that exceeded limits for approvals.");
      } else {
        localStringBuffer.append(".");
      }
      audit(paramWireTransfer1.getAuditRecord(paramSecureUser, localStringBuffer.toString(), 3301, ""));
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer1.getTrackingID());
    paramWireTransfer1.logChanges(paramSecureUser, localHistoryTracker, paramWireTransfer2, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localCSILException2.toString());
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void deleteHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.deleteHostWire";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (!WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "HOST_WIRE"))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str);
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    try
    {
      WireHandler.deleteWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
      if (paramWireTransfer.getWireDestination().equals("HOST")) {
        paramWireTransfer.setFromAccountID("HOST");
      }
      StringBuffer localStringBuffer1 = new StringBuffer("Cancelled a host wire of amount ");
      localStringBuffer1.append(paramWireTransfer.getAmount());
      if (paramWireTransfer.getStatus() >= 100) {
        localStringBuffer1.append(" that exceeded limits from approvals.");
      } else {
        localStringBuffer1.append(".");
      }
      audit(paramWireTransfer.getAuditRecord(paramSecureUser, localStringBuffer1.toString(), 3302, "DELETED"));
    }
    catch (CSILException localCSILException1)
    {
      StringBuffer localStringBuffer2 = new StringBuffer("Failed to cancel a host wire of amount ");
      localStringBuffer2.append(paramWireTransfer.getAmount());
      if (paramWireTransfer.getStatus() >= 100) {
        localStringBuffer2.append(" that exceeded limits for approvals.");
      } else {
        localStringBuffer2.append(".");
      }
      audit(paramWireTransfer.getAuditRecord(paramSecureUser, localStringBuffer2.toString(), 3302, ""));
      throw localCSILException1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, paramWireTransfer.getTrackingID());
    paramWireTransfer.logDelete(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
    try
    {
      WireHandler.addHistory(paramSecureUser, localHistoryTracker.getHistories(), null);
    }
    catch (CSILException localCSILException2)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str + ": " + localCSILException2.toString());
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static Date getSmartDate(SecureUser paramSecureUser, DateTime paramDateTime)
    throws CSILException
  {
    String str = "Wire.getSmartDate";
    debug(paramSecureUser, str);
    return WireHandler.getSmartDate(paramSecureUser, paramDateTime);
  }
  
  public static void confirmWiresLimits(SecureUser paramSecureUser, WireTransfers paramWireTransfers, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wires.confirmWiresLimits";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    WireTransfers localWireTransfers = new WireTransfers();
    DateTime localDateTime = new DateTime(paramSecureUser.getLocale());
    WireTransfer localWireTransfer;
    for (int i = 0; i < paramWireTransfers.size(); i++)
    {
      localWireTransfer = (WireTransfer)paramWireTransfers.get(i);
      if (localWireTransfer.getStatus() == 18)
      {
        Limits localLimits = Entitlements.checkLimitsAdd(localEntitlementGroupMember, ENT_WIRES_RELEASE, localWireTransfer.getAmountValue().getAmountValue(), localDateTime.getTime());
        if ((localLimits != null) && (!localLimits.isEmpty()))
        {
          localWireTransfer.setStatus(25);
          int j = 1;
          Iterator localIterator = localLimits.iterator();
          while (localIterator.hasNext())
          {
            Limit localLimit = (Limit)localIterator.next();
            if (!localLimit.isAllowedApproval())
            {
              j = 0;
              break;
            }
          }
          if (j == 1) {
            localWireTransfers.add(localWireTransfer);
          }
        }
        else
        {
          localWireTransfers.add(localWireTransfer);
        }
      }
    }
    for (i = 0; i < localWireTransfers.size(); i++)
    {
      localWireTransfer = (WireTransfer)localWireTransfers.get(i);
      Entitlements.checkLimitsDelete(localEntitlementGroupMember, ENT_WIRES_RELEASE, localWireTransfer.getAmountValue().getAmountValue(), localDateTime.getTime());
    }
  }
  
  public static void getWireAmounts(SecureUser paramSecureUser, WireTransfer paramWireTransfer, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    if (paramBoolean == true)
    {
      paramWireTransfer.setOrigAmount(paramWireTransfer.getAmountForBPW());
      paramWireTransfer.setOrigCurrency(paramWireTransfer.getAmtCurrencyType());
      if (!paramWireTransfer.getAmtCurrencyType().equals("USD")) {
        try
        {
          jdMethod_int(paramSecureUser, paramWireTransfer, paramHashMap);
        }
        catch (CSILException localCSILException1)
        {
          if (localCSILException1.getServiceError() == 31009) {
            DebugLog.log("WARNING: Can't calculate USD Amount. Failed to get ExchangeRate from " + paramWireTransfer.getAmtCurrencyType() + " to " + "USD");
          }
        }
      }
    }
    else if (!paramWireTransfer.getOrigCurrency().equals("USD"))
    {
      try
      {
        jdMethod_int(paramSecureUser, paramWireTransfer, paramHashMap);
      }
      catch (CSILException localCSILException2)
      {
        if (localCSILException2.getServiceError() == 31009) {
          DebugLog.log("WARNING: Can't calculate USD Amount. Failed to get ExchangeRate from " + paramWireTransfer.getAmtCurrencyType() + " to " + "USD");
        }
      }
    }
    else
    {
      paramWireTransfer.setAmount(paramWireTransfer.getOrigAmount());
    }
    if (!paramWireTransfer.getPayeeCurrencyType().equals("USD"))
    {
      FXRate localFXRate = FX.getFXRate(paramSecureUser, "USD", paramWireTransfer.getPayeeCurrencyType(), paramHashMap);
      if (localFXRate == null)
      {
        paramWireTransfer.setPaymentAmount("0.0");
        DebugLog.log("WARNING: Can't calculate Payment Amount. Failed to get ExchangeRate from USD to " + paramWireTransfer.getPayeeCurrencyType());
        return;
      }
      Currency localCurrency = localFXRate.getBuyPrice();
      if (localCurrency == null)
      {
        paramWireTransfer.setPaymentAmount("0.0");
        DebugLog.log("WARNING: Can't calculate Payment Amount. Failed to get ExchangeRate from USD to " + paramWireTransfer.getPayeeCurrencyType());
        return;
      }
      BigDecimal localBigDecimal = new BigDecimal(paramWireTransfer.getAmountForBPW());
      localBigDecimal = localBigDecimal.multiply(localCurrency.getAmountValue().setScale(7, 4));
      paramWireTransfer.setPaymentAmount(localBigDecimal.toString());
    }
    else
    {
      paramWireTransfer.setPaymentAmount(paramWireTransfer.getAmountForBPW());
    }
  }
  
  public static void getBatchAmounts(SecureUser paramSecureUser, WireBatch paramWireBatch, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    WireTransfers localWireTransfers = paramWireBatch.getWires();
    Object localObject;
    if (paramBoolean == true)
    {
      Currency localCurrency = paramWireBatch.calculateTotalAmount();
      paramWireBatch.setOrigAmount(localCurrency.getAmountValue().toString());
      paramWireBatch.setOrigCurrency(paramWireBatch.getAmtCurrencyType());
      if (!paramWireBatch.getAmtCurrencyType().equals("USD"))
      {
        try
        {
          jdMethod_for(paramSecureUser, paramWireBatch, localWireTransfers, paramBoolean, paramHashMap);
        }
        catch (CSILException localCSILException2)
        {
          if (localCSILException2.getServiceError() == 31009) {
            DebugLog.log("WARNING: Can't calculate USD Amount. Failed to get ExchangeRate from " + paramWireBatch.getAmtCurrencyType() + " to " + "USD");
          }
        }
        if (paramWireBatch.getBatchDestination().equals("DRAWDOWN")) {
          paramWireBatch.setTotalCredit(paramWireBatch.calculateTotalAmount());
        } else {
          paramWireBatch.setTotalDebit(paramWireBatch.calculateTotalAmount());
        }
      }
      else if (paramWireBatch.getBatchDestination().equals("DRAWDOWN"))
      {
        paramWireBatch.setTotalCredit(localCurrency);
      }
      else
      {
        paramWireBatch.setTotalDebit(localCurrency);
      }
    }
    else
    {
      paramWireBatch.setOrigAmount(paramWireBatch.calculateTotalOrigAmount());
      if (!paramWireBatch.getOrigCurrency().equals("USD")) {
        try
        {
          jdMethod_for(paramSecureUser, paramWireBatch, localWireTransfers, paramBoolean, paramHashMap);
        }
        catch (CSILException localCSILException1)
        {
          if (localCSILException1.getServiceError() == 31009) {
            DebugLog.log("WARNING: Can't calculate USD Amount. Failed to get ExchangeRate from " + paramWireBatch.getAmtCurrencyType() + " to " + "USD");
          }
        }
      } else {
        for (int i = 0; i < localWireTransfers.size(); i++)
        {
          localObject = (WireTransfer)localWireTransfers.get(i);
          if (((WireTransfer)localObject).getAction().equals("add") == true) {
            ((WireTransfer)localObject).setOrigAmount(((WireTransfer)localObject).getAmountForBPW());
          } else {
            ((WireTransfer)localObject).setAmount(((WireTransfer)localObject).getOrigAmount());
          }
        }
      }
      if (paramWireBatch.getBatchDestination().equals("DRAWDOWN")) {
        paramWireBatch.setTotalCredit(paramWireBatch.calculateTotalAmount());
      } else {
        paramWireBatch.setTotalDebit(paramWireBatch.calculateTotalAmount());
      }
    }
    if (!paramWireBatch.getPayeeCurrencyType().equals("USD"))
    {
      FXRate localFXRate = FX.getFXRate(paramSecureUser, "USD", paramWireBatch.getPayeeCurrencyType(), paramHashMap);
      if (localFXRate == null)
      {
        paramWireBatch.setPaymentAmount("0.0");
        DebugLog.log("WARNING: Can't calculate Payment Amount. Failed to get ExchangeRate from USD to " + paramWireBatch.getPayeeCurrencyType());
        return;
      }
      localObject = localFXRate.getBuyPrice();
      if (localObject == null)
      {
        paramWireBatch.setPaymentAmount("0.0");
        DebugLog.log("WARNING: Can't calculate Payment Amount. Failed to get ExchangeRate from USD to " + paramWireBatch.getPayeeCurrencyType());
        return;
      }
      for (int j = 0; j < localWireTransfers.size(); j++)
      {
        WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.get(j);
        BigDecimal localBigDecimal = new BigDecimal(localWireTransfer.getAmountForBPW());
        localBigDecimal = localBigDecimal.multiply(((Currency)localObject).getAmountValue().setScale(7, 4));
        localWireTransfer.setPaymentAmount(localBigDecimal.toString());
      }
      paramWireBatch.setPaymentAmount(paramWireBatch.getTotalPaymentAmount());
    }
    else
    {
      paramWireBatch.setPaymentAmount(paramWireBatch.getAmount());
    }
  }
  
  public static WireTransfers getWireApprovalAuditHistory(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getApprovalsHistory";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    HashMap localHashMap = new HashMap();
    localHashMap.put("ObjectID", paramString);
    localHashMap.put("ItemSubType", new Integer(5));
    ApprovalsHistory localApprovalsHistory = Approvals.getItemHistory(1, localHashMap, paramHashMap);
    WireTransfers localWireTransfers = new WireTransfers();
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransfers;
  }
  
  public static WireTransferBank getBPWFIById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getBPWFIById";
    WireTransferBank localWireTransferBank = null;
    localWireTransferBank = WireHandler.getBPWFIById(paramSecureUser, paramString, paramHashMap);
    return localWireTransferBank;
  }
  
  public static WireHistories getCompletedWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getCompletedWireHistories";
    debug(paramSecureUser, str1);
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL"))) {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
    } else {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    }
    WireHistories localWireHistories = WireHandler.getCompletedWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
    return localWireHistories;
  }
  
  public static WireHistories getPendingWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getPendingWireHistories";
    debug(paramSecureUser, str1);
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL"))) {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
    } else {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    }
    WireHistories localWireHistories = WireHandler.getPendingWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
    return localWireHistories;
  }
  
  public static WireHistories getApprovalWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getApprovalWireHistories";
    debug(paramSecureUser, str);
    return WireUtil.getApprWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static WireBatch getWireBatchById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getWireBatchById";
    debug(paramSecureUser, str);
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    WireBatch localWireBatch = WireHandler.getWireBatchById(paramSecureUser, paramString, paramHashMap);
    WireUtil.setCanEditDeleteForBatch(paramSecureUser, localWireBatch, paramHashMap);
    return localWireBatch;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    if (paramWireTransfer != null) {
      if (paramWireTransfer.getStatus() >= 100)
      {
        ApprovalsItem localApprovalsItem = new ApprovalsItem(paramSecureUser.getLocale());
        TWTransaction localTWTransaction = new TWTransaction(paramSecureUser.getLocale());
        localTWTransaction.setTransaction(paramWireTransfer);
        localApprovalsItem.setItemID(Integer.parseInt(paramWireTransfer.getID()));
        localApprovalsItem.setItemType(1);
        localApprovalsItem.setItem(localTWTransaction);
        Approvals.removeApprovalItem(localApprovalsItem, paramHashMap);
      }
      else
      {
        WireHandler.deleteWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
      }
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    if (paramWireBatch != null) {
      if (paramWireBatch.getStatus() >= 100)
      {
        ApprovalsItem localApprovalsItem = new ApprovalsItem(paramSecureUser.getLocale());
        localApprovalsItem.setItemID(Integer.parseInt(paramWireBatch.getID()));
        localApprovalsItem.setItemType(1);
        localApprovalsItem.setItem(paramWireBatch);
        Approvals.removeApprovalItem(localApprovalsItem, paramHashMap);
      }
      else
      {
        WireHandler.deleteWireBatch(paramSecureUser, paramWireBatch, paramHashMap);
      }
    }
  }
  
  private static BigDecimal jdMethod_int(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    BigDecimal localBigDecimal1 = new BigDecimal(paramWireTransfer.getOrigAmount());
    BigDecimal localBigDecimal2 = new BigDecimal(0.0D);
    Object localObject;
    if ((!WireUtil.isNullOrEmpty(paramWireTransfer.getContractNumber())) && (paramWireTransfer.getExchangeRateValue() != 0.0F) && (!WireUtil.isNullOrEmpty(paramWireTransfer.getMathRule())))
    {
      localObject = new BigDecimal(paramWireTransfer.getExchangeRate()).setScale(7, 4);
      localBigDecimal2 = jdMethod_for(localBigDecimal1, (BigDecimal)localObject, paramWireTransfer.getMathRule());
    }
    else
    {
      localObject = FX.getFXRate(paramSecureUser, paramWireTransfer.getOrigCurrency(), "USD", paramHashMap);
      if (localObject == null) {
        throw new CSILException(-1009, 31009);
      }
      Currency localCurrency = ((FXRate)localObject).getBuyPrice();
      if (localCurrency == null) {
        throw new CSILException(-1009, 31009);
      }
      BigDecimal localBigDecimal3 = localCurrency.getAmountValue().setScale(7, 4);
      localBigDecimal2 = jdMethod_for(localBigDecimal1, localBigDecimal3, "MULT");
      paramWireTransfer.setExchangeRate(localBigDecimal3.floatValue());
      paramWireTransfer.setContractNumber(null);
      paramWireTransfer.setMathRule("MULT");
    }
    paramWireTransfer.setAmount(new BigDecimal(localBigDecimal2.doubleValue()));
    paramWireTransfer.setAmtCurrencyType("USD");
    return localBigDecimal2;
  }
  
  private static BigDecimal jdMethod_for(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString)
  {
    if (paramString.equalsIgnoreCase("DIV") == true) {
      paramBigDecimal1 = paramBigDecimal1.divide(paramBigDecimal2, 1);
    } else {
      paramBigDecimal1 = paramBigDecimal1.multiply(paramBigDecimal2);
    }
    return paramBigDecimal1;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, WireBatch paramWireBatch, WireTransfers paramWireTransfers, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject1;
    Object localObject2;
    Object localObject3;
    BigDecimal localBigDecimal2;
    BigDecimal localBigDecimal3;
    if ((!WireUtil.isNullOrEmpty(paramWireBatch.getContractNumber())) && (paramWireBatch.getExchangeRateValue() != 0.0F) && (!WireUtil.isNullOrEmpty(paramWireBatch.getMathRule())))
    {
      localObject1 = new BigDecimal(paramWireBatch.getExchangeRate()).setScale(7, 4);
      localObject2 = paramWireBatch.getMathRule();
      for (int i = 0; i < paramWireTransfers.size(); i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)paramWireTransfers.get(i);
        localObject3 = null;
        if (paramBoolean == true)
        {
          localObject3 = localWireTransfer.getAmountValue().getAmountValue();
          localWireTransfer.setOrigAmount(localWireTransfer.getAmountForBPW());
        }
        else
        {
          if (localWireTransfer.getAction().equals("add") == true) {
            localWireTransfer.setOrigAmount(localWireTransfer.getAmountForBPW());
          } else if (localWireTransfer.getAction().equals("") == true) {
            localWireTransfer.setAction("mod");
          }
          localObject3 = new BigDecimal(localWireTransfer.getOrigAmount());
        }
        localBigDecimal2 = jdMethod_for((BigDecimal)localObject3, (BigDecimal)localObject1, (String)localObject2);
        if (paramWireBatch.getBatchType().equals("TEMPLATE") == true)
        {
          localBigDecimal3 = localWireTransfer.getWireLimitValue().getAmountValue();
          if ((localBigDecimal3.doubleValue() > 0.0D) && (localBigDecimal2.doubleValue() > localBigDecimal3.doubleValue()))
          {
            localWireTransfer.setAmount(new BigDecimal(localBigDecimal2.doubleValue()));
            throw new CSILException(-1009, 31019);
          }
        }
        localWireTransfer.setAmount(new BigDecimal(localBigDecimal2.doubleValue()));
        localWireTransfer.setAmtCurrencyType("USD");
      }
    }
    else
    {
      localObject1 = FX.getFXRate(paramSecureUser, paramWireBatch.getOrigCurrency(), "USD", paramHashMap);
      if (localObject1 == null) {
        throw new CSILException(-1009, 31009);
      }
      localObject2 = ((FXRate)localObject1).getBuyPrice();
      if (localObject2 == null) {
        throw new CSILException(-1009, 31009);
      }
      BigDecimal localBigDecimal1 = ((Currency)localObject2).getAmountValue().setScale(7, 4);
      for (int j = 0; j < paramWireTransfers.size(); j++)
      {
        localObject3 = (WireTransfer)paramWireTransfers.get(j);
        localBigDecimal2 = null;
        if (paramBoolean == true)
        {
          localBigDecimal2 = ((WireTransfer)localObject3).getAmountValue().getAmountValue();
          ((WireTransfer)localObject3).setOrigAmount(((WireTransfer)localObject3).getAmountForBPW());
        }
        else
        {
          if (((WireTransfer)localObject3).getAction().equals("add") == true) {
            ((WireTransfer)localObject3).setOrigAmount(((WireTransfer)localObject3).getAmountForBPW());
          } else if (((WireTransfer)localObject3).getAction().equals("") == true) {
            ((WireTransfer)localObject3).setAction("mod");
          }
          localBigDecimal2 = new BigDecimal(((WireTransfer)localObject3).getOrigAmount());
        }
        localBigDecimal3 = jdMethod_for(localBigDecimal2, localBigDecimal1, "MULT");
        if (paramWireBatch.getBatchType().equals("TEMPLATE") == true)
        {
          BigDecimal localBigDecimal4 = ((WireTransfer)localObject3).getWireLimitValue().getAmountValue();
          if ((localBigDecimal4.doubleValue() > 0.0D) && (localBigDecimal3.doubleValue() > localBigDecimal4.doubleValue()))
          {
            ((WireTransfer)localObject3).setAmount(new BigDecimal(localBigDecimal3.doubleValue()));
            throw new CSILException(-1009, 31019);
          }
        }
        ((WireTransfer)localObject3).setAmount(new BigDecimal(localBigDecimal3.doubleValue()));
        ((WireTransfer)localObject3).setAmtCurrencyType("USD");
      }
      paramWireBatch.setExchangeRate(localBigDecimal1.floatValue());
      paramWireBatch.setContractNumber(null);
      paramWireBatch.setMathRule("MULT");
    }
    paramWireBatch.setAmtCurrencyType("USD");
  }
  
  public static Limits checkModifyWireLimit(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, WireTransfer paramWireTransfer1, WireTransfer paramWireTransfer2, String paramString, HashMap paramHashMap, boolean paramBoolean)
    throws CSILException
  {
    Limits localLimits = null;
    Date localDate1 = null;
    MultiEntitlement localMultiEntitlement1 = null;
    BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
    if (!paramWireTransfer1.getOrigCurrency().equals("USD"))
    {
      localBigDecimal1 = jdMethod_for(paramSecureUser, paramWireTransfer1, paramWireTransfer2, paramHashMap);
    }
    else
    {
      localBigDecimal1 = new BigDecimal(paramWireTransfer1.getOrigAmount());
      paramWireTransfer1.setAmount(paramWireTransfer1.getOrigAmount());
    }
    DateTime localDateTime = new DateTime(paramSecureUser.getLocale());
    if (paramBoolean == true)
    {
      localDate1 = getSmartDate(paramSecureUser, paramWireTransfer1.getDueDateValue());
      localMultiEntitlement1 = WireEntitlementUtil.getMultiEntitlementObject(paramSecureUser, paramString, paramWireTransfer1);
    }
    else
    {
      paramWireTransfer1.setWireLimit(paramWireTransfer1.getAmountValue());
      localMultiEntitlement1 = WireEntitlementUtil.getMultiEntitlementObject(paramSecureUser, paramString, paramWireTransfer1);
      localDate1 = localDateTime.getTime();
      if (localBigDecimal1.compareTo(new BigDecimal(0.0D)) == 0) {
        localBigDecimal1 = maxAmount;
      }
    }
    if (paramWireTransfer2 != null)
    {
      BigDecimal localBigDecimal2 = paramWireTransfer2.getAmountValue().getAmountValue();
      MultiEntitlement localMultiEntitlement2 = null;
      Date localDate2 = null;
      if (paramBoolean == true)
      {
        localMultiEntitlement2 = WireEntitlementUtil.getMultiEntitlementObject(paramSecureUser, paramString, paramWireTransfer2);
        localDate2 = getSmartDate(paramSecureUser, paramWireTransfer2.getDueDateValue());
      }
      else
      {
        localMultiEntitlement2 = WireEntitlementUtil.getMultiEntitlementObject(paramSecureUser, paramString, paramWireTransfer2);
        localDate2 = paramWireTransfer2.getDueDateValue().getTime();
        if (localBigDecimal2.compareTo(new BigDecimal(0.0D)) == 0) {
          localBigDecimal2 = maxAmount;
        }
      }
      if (paramWireTransfer2.getFromAccountID().equals(paramWireTransfer1.getFromAccountID()) == true)
      {
        localLimits = Entitlements.checkLimitsEdit(paramEntitlementGroupMember, localMultiEntitlement1, localBigDecimal2, localBigDecimal1, localDate2, localDate1);
      }
      else
      {
        try
        {
          Entitlements.checkLimitsDelete(paramEntitlementGroupMember, localMultiEntitlement2, localBigDecimal2, localDate2);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log("Wire.checkWireLimit: Failed to delete limits for Original  FromAccount. Can't modify the wire.");
          throw new CSILException("Wire.checkWireLimit", 31005, "Failed to delete limits for Original FromAccount");
        }
        localLimits = Entitlements.checkLimitsAdd(paramEntitlementGroupMember, localMultiEntitlement1, localBigDecimal1, localDate1);
      }
    }
    else
    {
      localLimits = Entitlements.checkLimitsAdd(paramEntitlementGroupMember, localMultiEntitlement1, localBigDecimal1, localDate1);
    }
    return localLimits;
  }
  
  private static BigDecimal jdMethod_for(SecureUser paramSecureUser, WireTransfer paramWireTransfer1, WireTransfer paramWireTransfer2, HashMap paramHashMap)
    throws CSILException
  {
    BigDecimal localBigDecimal = paramWireTransfer1.getAmountValue().getAmountValue();
    if (paramWireTransfer2 != null)
    {
      if (!paramWireTransfer2.getOrigCurrency().equals(paramWireTransfer1.getOrigCurrency())) {
        localBigDecimal = jdMethod_int(paramSecureUser, paramWireTransfer1, paramHashMap);
      } else if (!paramWireTransfer2.getOrigAmount().equals(paramWireTransfer1.getOrigAmount())) {
        localBigDecimal = jdMethod_int(paramSecureUser, paramWireTransfer1, paramHashMap);
      } else if (paramWireTransfer2.getExchangeRateValue() != paramWireTransfer1.getExchangeRateValue()) {
        localBigDecimal = jdMethod_int(paramSecureUser, paramWireTransfer1, paramHashMap);
      } else if (!paramWireTransfer2.getMathRule().equals(paramWireTransfer1.getMathRule())) {
        localBigDecimal = jdMethod_int(paramSecureUser, paramWireTransfer1, paramHashMap);
      }
    }
    else
    {
      DebugLog.log("WARNING: Modify Wire/Template method missing the Orig object. Reconverting wire/template amount to USD");
      localBigDecimal = jdMethod_int(paramSecureUser, paramWireTransfer1, paramHashMap);
    }
    return localBigDecimal;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, WireBatch paramWireBatch1, WireBatch paramWireBatch2, HashMap paramHashMap)
    throws CSILException
  {
    if (paramWireBatch2 != null)
    {
      if (!paramWireBatch2.getOrigCurrency().equals(paramWireBatch1.getOrigCurrency()))
      {
        jdMethod_for(paramSecureUser, paramWireBatch1, paramWireBatch1.getWires(), false, paramHashMap);
      }
      else if (paramWireBatch2.getExchangeRateValue() != paramWireBatch1.getExchangeRateValue())
      {
        jdMethod_for(paramSecureUser, paramWireBatch1, paramWireBatch1.getWires(), false, paramHashMap);
      }
      else if (!paramWireBatch2.getMathRule().equals(paramWireBatch1.getMathRule()))
      {
        jdMethod_for(paramSecureUser, paramWireBatch1, paramWireBatch1.getWires(), false, paramHashMap);
      }
      else
      {
        WireTransfers localWireTransfers1 = new WireTransfers();
        WireTransfers localWireTransfers2 = paramWireBatch1.getWires();
        for (int i = 0; i < localWireTransfers2.size(); i++)
        {
          WireTransfer localWireTransfer = (WireTransfer)localWireTransfers2.get(i);
          if (localWireTransfer.getAction().equals("add") == true)
          {
            localWireTransfers1.add(localWireTransfer);
          }
          else if (localWireTransfer.getAction().equals("mod") == true)
          {
            BigDecimal localBigDecimal1 = localWireTransfer.getAmountValue().getAmountValue();
            BigDecimal localBigDecimal2 = jdMethod_for(new BigDecimal(localWireTransfer.getOrigAmount()), new BigDecimal(localWireTransfer.getExchangeRateValue()), localWireTransfer.getMathRule());
            if (localBigDecimal1 != localBigDecimal2) {
              localWireTransfers1.add(localWireTransfer);
            }
          }
        }
        if (localWireTransfers1.size() > 0) {
          jdMethod_for(paramSecureUser, paramWireBatch1, localWireTransfers1, false, paramHashMap);
        }
      }
    }
    else
    {
      DebugLog.log("WARNING: Modify Batch method missing the Orig Batch. Reconverting batch wires amounts to USD");
      jdMethod_for(paramSecureUser, paramWireBatch1, paramWireBatch1.getWires(), false, paramHashMap);
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, WireTransfer paramWireTransfer, Entitlement paramEntitlement, CSILException paramCSILException)
    throws CSILException
  {
    if (paramCSILException.getCode() == 20003)
    {
      paramWireTransfer.setStatus(25);
    }
    else if ((paramWireTransfer.getType() != 6) && (paramWireTransfer.getStatus() != 101))
    {
      paramWireTransfer.setStatus(6);
      Date localDate = getSmartDate(paramSecureUser, paramWireTransfer.getDueDateValue());
      Entitlements.checkLimitsDelete(paramEntitlementGroupMember, paramEntitlement, paramWireTransfer.getAmountValue().getAmountValue(), localDate);
    }
    throw paramCSILException;
  }
  
  public static void addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.addHistory";
    long l = System.currentTimeMillis();
    WireHandler.addHistory(paramSecureUser, paramHistories, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static WireTransfers getAuditHistoryById(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getAuditHistoryById";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    long l = System.currentTimeMillis();
    WireTransfers localWireTransfers = WireHandler.getAuditHistoryById(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localWireTransfers;
  }
  
  public static String getBPWFIId(SecureUser paramSecureUser, String paramString)
  {
    return WireUtil.getBPWFIId(paramSecureUser, paramString);
  }
  
  public static WireTransferBanks getBPWFIs()
    throws CSILException
  {
    return WireHandler.getBPWFIs();
  }
  
  public static HashMap getWiresConfiguration(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return WireHandler.getWiresConfiguration(paramSecureUser, paramHashMap);
  }
  
  public boolean isBatchDeletable(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return WireHandler.isBatchDeletable(paramSecureUser, paramString, paramHashMap);
  }
  
  public static boolean isDuplicateWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, boolean paramBoolean, HashMap paramHashMap)
  {
    return WireUtil.isDuplicateWire(paramSecureUser, paramWireTransfer, paramBoolean, paramHashMap);
  }
  
  public static boolean isDuplicateWireInBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, boolean paramBoolean, HashMap paramHashMap)
  {
    return WireUtil.isDuplicateWireInBatch(paramSecureUser, paramWireBatch, paramBoolean, paramHashMap);
  }
  
  public static Limits checkWireTemplateLimits(SecureUser paramSecureUser, WireTransfer paramWireTransfer, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.checkWireTemplateLimits";
    if (!WireEntitlementUtil.checkWireTemplateEntitlements(paramSecureUser, paramWireTransfer, paramHashMap)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    paramWireTransfer.setTrackingID(TrackingIDGenerator.GetNextID());
    paramWireTransfer.setAmount(paramWireTransfer.getWireLimitValue());
    paramWireTransfer.setOrigAmount(paramWireTransfer.getAmountForBPW());
    paramWireTransfer.setOrigCurrency(paramWireTransfer.getAmtCurrencyType());
    BigDecimal localBigDecimal = paramWireTransfer.getWireLimitValue().getAmountValue();
    if (!paramWireTransfer.getOrigCurrency().equals("USD"))
    {
      localBigDecimal = jdMethod_int(paramSecureUser, paramWireTransfer, paramHashMap);
      paramWireTransfer.setWireLimit(new BigDecimal(localBigDecimal.doubleValue()));
    }
    DateTime localDateTime = new DateTime(paramSecureUser.getLocale());
    EntitlementGroupMember localEntitlementGroupMember = WireUtil.getEntGroupMember(paramSecureUser, paramWireTransfer);
    MultiEntitlement localMultiEntitlement = WireEntitlementUtil.getMultiEntitlementObject(paramSecureUser, "Wire Templates Create", paramWireTransfer);
    Limits localLimits = null;
    if (localBigDecimal.compareTo(new BigDecimal(0.0D)) == 0)
    {
      if (paramBoolean) {
        localLimits = Entitlements.confirmLimitsBeforeAdd(localEntitlementGroupMember, localMultiEntitlement, maxAmount, localDateTime.getTime());
      } else {
        localLimits = Entitlements.checkLimitsAdd(localEntitlementGroupMember, localMultiEntitlement, maxAmount, localDateTime.getTime());
      }
    }
    else if (paramBoolean) {
      localLimits = Entitlements.confirmLimitsBeforeAdd(localEntitlementGroupMember, localMultiEntitlement, localBigDecimal, localDateTime.getTime());
    } else {
      localLimits = Entitlements.checkLimitsAdd(localEntitlementGroupMember, localMultiEntitlement, localBigDecimal, localDateTime.getTime());
    }
    return localLimits;
  }
  
  public static void autoEntitleWireTemplate(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.business.Business localBusiness = Business.jdMethod_int(paramSecureUser, Integer.parseInt(paramWireTransfer.getCustomerID()), paramHashMap);
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    localEntitlements.add(new Entitlement(null, "Wire Template", paramWireTransfer.getID()));
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("category", "per wire template");
    Iterator localIterator = localEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      Entitlement localEntitlement = new Entitlement(localEntitlementTypePropertyList.getOperationName(), "Wire Template", paramWireTransfer.getID());
      localEntitlements.add(localEntitlement);
    }
    AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, localBusiness.getEntitlementGroup(), localEntitlements, 3, paramWireTransfer.getAutoEntitleTransaction(), paramHashMap);
  }
  
  public static WireHistories getPagedWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getPagedWireHistories";
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    if ((str2 != null) && (str2.equals("ALL"))) {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
    } else {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str1);
    }
    WireHistories localWireHistories = WireHandler.getPagedWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
    if (localWireHistories != null)
    {
      Iterator localIterator = localWireHistories.iterator();
      while (localIterator.hasNext())
      {
        WireHistory localWireHistory = (WireHistory)localIterator.next();
        if (localWireHistory != null) {
          if (((localWireHistory.getStatus() == 100) || (localWireHistory.getStatus() == 101)) && (("RECURRING".equals(localWireHistory.getTransType())) || ("SINGLE".equals(localWireHistory.getTransType())))) {
            Util.insertApprovalInfo(localWireHistory, paramSecureUser, null, paramHashMap);
          }
        }
      }
    }
    return localWireHistories;
  }
  
  public static WireTransfers getPagedWireTemplates(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getPagedWireTemplates";
    WireEntitlementUtil.checkEntitlements(paramSecureUser, ENT_WIRES, str);
    WireTransfers localWireTransfers = WireHandler.getPagedWireTemplates(paramSecureUser, paramPagingContext, paramWireTransfer, paramHashMap);
    if (paramSecureUser.getUserType() == 2) {
      return localWireTransfers;
    }
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Iterator localIterator = localWireTransfers.iterator();
    while (localIterator.hasNext())
    {
      WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
      Object localObject;
      if (!localWireTransfer.getWireScope().equals("BANK"))
      {
        localObject = new MultiEntitlement();
        ((MultiEntitlement)localObject).setOperations(new String[] { "Wire Templates Create" });
        ((MultiEntitlement)localObject).setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localWireTransfer) });
        Entitlement localEntitlement = EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, (MultiEntitlement)localObject, paramSecureUser.getBusinessID());
        if (localEntitlement == null) {
          localWireTransfer.setCanEdit(true);
        }
        ((MultiEntitlement)localObject).setOperations(new String[] { "Wire Templates Delete" });
        localEntitlement = EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, (MultiEntitlement)localObject, paramSecureUser.getBusinessID());
        if (localEntitlement == null) {
          localWireTransfer.setCanDelete(true);
        }
      }
      if (localWireTransfer.getStatus() < 100)
      {
        localObject = WireEntitlementUtil.getEntitlementFlowName(localWireTransfer.getWireDestination(), "TEMPLATE");
        if (WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, (String)localObject, paramHashMap) == true) {
          localWireTransfer.setCanLoad(true);
        }
      }
    }
    return localWireTransfers;
  }
  
  private static void jdMethod_for(WireBatch paramWireBatch)
  {
    WireTransfers localWireTransfers = paramWireBatch.getWires();
    if (localWireTransfers != null)
    {
      Iterator localIterator = localWireTransfers.iterator();
      while (localIterator.hasNext())
      {
        WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
        if ((localWireTransfer != null) && ((localWireTransfer.getAction() == null) || (localWireTransfer.getAction().trim().equals("")))) {
          localWireTransfer.setAction("mod");
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Wire
 * JD-Core Version:    0.7.0.1
 */