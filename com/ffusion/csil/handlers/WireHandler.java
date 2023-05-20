package com.ffusion.csil.handlers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireBatches;
import com.ffusion.beans.wiretransfers.WireHistories;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransferPayees;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.WireTransferService4;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Date;
import java.util.HashMap;

public class WireHandler
{
  private static WireTransferService4 jdField_if = null;
  private static String a = "15";
  private static final String jdField_do = "WireTransfer";
  
  public static String getWireTemplateResultSize()
  {
    return a;
  }
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.WireHandler.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "WireTransfer", str, 20107);
      a = HandlerUtil.getGlobalWireTemplateResultSize(paramHashMap);
      jdField_if = (WireTransferService4)HandlerUtil.instantiateService(localHashMap, str, 20107);
      jdField_if.initialize("");
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str, localException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_if;
  }
  
  public static WireTransfer addWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.WireHandler.addWireTransfer";
    try
    {
      WireTransfer localWireTransfer = jdField_if.addWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
      if ((localWireTransfer != null) && (("RECTEMPLATE".equals(localWireTransfer.getWireType())) || ("TEMPLATE".equals(localWireTransfer.getWireType()))) && (localWireTransfer.getWireScope() != null) && ((localWireTransfer.getWireScope().equals("BANK")) || (localWireTransfer.getWireScope().equals("BUSINESS"))) && ((localWireTransfer.getStatus() == 22) || (localWireTransfer.getStatus() == 23))) {
        Wire.autoEntitleWireTemplate(paramSecureUser, localWireTransfer, paramHashMap);
      }
      return localWireTransfer;
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.modifyWireTransfer";
    try
    {
      jdField_if.modifyWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
      if ((paramWireTransfer != null) && (("RECTEMPLATE".equals(paramWireTransfer.getWireType())) || ("TEMPLATE".equals(paramWireTransfer.getWireType()))) && (paramWireTransfer.getWireScope() != null) && ((paramWireTransfer.getWireScope().equals("BANK")) || (paramWireTransfer.getWireScope().equals("BUSINESS"))) && ((paramWireTransfer.getStatus() == 22) || (paramWireTransfer.getStatus() == 23)) && (paramWireTransfer.get("AUTOENTITLE_WIRE_TRANSFER") != null))
      {
        ValueInfo localValueInfo = (ValueInfo)paramWireTransfer.get("AUTOENTITLE_WIRE_TRANSFER");
        if ((localValueInfo.getValue() != null) && (!((String)localValueInfo.getValue()).equals("ignore"))) {
          Wire.autoEntitleWireTemplate(paramSecureUser, paramWireTransfer, paramHashMap);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.deleteWireTransfer";
    try
    {
      jdField_if.deleteWireTransfer(paramSecureUser, paramWireTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.deleteWireBatch";
    try
    {
      jdField_if.deleteWireBatch(paramSecureUser, paramWireBatch, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getWireTransfers(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getWireTransfers";
    try
    {
      return jdField_if.getWireTransfers(paramSecureUser, paramWireTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getWireTransfersByBatchId(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getWireTransfersByBatchId";
    try
    {
      return jdField_if.getWireTransfersByBatchId(paramSecureUser, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getAllWireTemplates(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getAllWireTemplates";
    try
    {
      return jdField_if.getAllWireTemplates(paramSecureUser, paramInt, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getWireTemplates(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getWireTemplates";
    try
    {
      return jdField_if.getWireTemplates(paramSecureUser, paramPagingContext, paramWireTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfer getWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getWireTransferById";
    try
    {
      return jdField_if.getWireTransferById(paramSecureUser, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfer getRecWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getRecWireTransferById";
    try
    {
      return jdField_if.getRecWireTransferById(paramSecureUser, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfer getCompletedWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getCompletedWireTransferById";
    try
    {
      return jdField_if.getCompletedWireTransferById(paramSecureUser, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getPagedPendingWires(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getPagedPendingWires";
    try
    {
      return jdField_if.getPagedPendingWires(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireBatches getPagedPendingWireBatches(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getPagedPendingWireBatches";
    try
    {
      return jdField_if.getPagedPendingWireBatches(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getPagedCompletedWires(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getPagedCompletedWires";
    try
    {
      return jdField_if.getPagedCompletedWires(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireBatches getPagedCompletedWireBatches(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getPagedCompletedWireBatches";
    try
    {
      return jdField_if.getPagedCompletedWireBatches(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransferPayee addWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.addWireTransferPayee";
    try
    {
      return jdField_if.addWireTransferPayee(paramSecureUser, paramWireTransferPayee, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.deleteWireTransferPayee";
    try
    {
      jdField_if.deleteWireTransferPayee(paramSecureUser, paramWireTransferPayee, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransferPayee modifyWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.modifyWireTransferPayee";
    try
    {
      return jdField_if.modifyWireTransferPayee(paramSecureUser, paramWireTransferPayee, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransferPayees getWireTransferPayees(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getWireTransferPayees";
    try
    {
      return jdField_if.getWireTransferPayees(paramSecureUser, paramWireTransferPayee, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransferPayee getWireTransferPayeeById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getWireTransferPayeesById";
    try
    {
      return jdField_if.getWireTransferPayeeById(paramSecureUser, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getReportData";
    try
    {
      return jdField_if.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static int getReleaseWiresCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getReleaseWiresCount";
    try
    {
      return jdField_if.getReleaseWiresCount(paramSecureUser, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getReleaseWires(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getReleaseWires";
    try
    {
      return jdField_if.getReleaseWires(paramSecureUser, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void releaseWires(SecureUser paramSecureUser, WireTransfers paramWireTransfers, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.releaseWire";
    try
    {
      jdField_if.releaseWires(paramSecureUser, paramWireTransfers, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireBatch addWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.addWireBatch";
    try
    {
      return jdField_if.addWireBatch(paramSecureUser, paramWireBatch, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.modifyWireBatch";
    try
    {
      jdField_if.modifyWireBatch(paramSecureUser, paramWireBatch, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfer addHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.addHostWire";
    try
    {
      return jdField_if.addHostWire(paramSecureUser, paramWireTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.modifyHostWire";
    try
    {
      jdField_if.modifyHostWire(paramSecureUser, paramWireTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Date getSmartDate(SecureUser paramSecureUser, DateTime paramDateTime)
    throws CSILException
  {
    String str = "Wire.getSmartDate";
    try
    {
      return jdField_if.getSmartDate(paramSecureUser, paramDateTime);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.addHistory";
    try
    {
      jdField_if.addHistory(paramSecureUser, paramHistories, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getAuditHistoryById(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getAuditHistoryById";
    try
    {
      return jdField_if.getAuditHistoryById(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransferBank getBPWFIById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getBPWFIById";
    try
    {
      return jdField_if.getBPWFIById(paramSecureUser, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfer processApprovalResult(SecureUser paramSecureUser, WireTransfer paramWireTransfer, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.processApprovalResult";
    try
    {
      WireTransfer localWireTransfer = jdField_if.processApprovalResult(paramSecureUser, paramWireTransfer, paramString, paramHashMap);
      if ((localWireTransfer != null) && (("RECTEMPLATE".equals(localWireTransfer.getWireType())) || ("TEMPLATE".equals(localWireTransfer.getWireType()))) && (localWireTransfer.getWireScope() != null) && ((localWireTransfer.getWireScope().equals("BANK")) || (localWireTransfer.getWireScope().equals("BUSINESS"))) && (paramString.equals("Approved")) && (localWireTransfer.get("AUTOENTITLE_WIRE_TRANSFER") != null))
      {
        ValueInfo localValueInfo = (ValueInfo)localWireTransfer.get("AUTOENTITLE_WIRE_TRANSFER");
        localWireTransfer.remove("AUTOENTITLE_WIRE_TRANSFER");
        if ((localValueInfo.getValue() != null) && (!localValueInfo.getValue().equals("ignore")))
        {
          localWireTransfer.setAutoEntitleTransaction(Boolean.valueOf((String)localValueInfo.getValue()).booleanValue());
          Wire.autoEntitleWireTemplate(paramSecureUser, localWireTransfer, paramHashMap);
        }
      }
      return localWireTransfer;
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransferBanks getBPWFIs()
    throws CSILException
  {
    String str = "WireHandler.getBPWFIs";
    try
    {
      return jdField_if.getBPWFIs();
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireHistories getCompletedWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getCompletedWireHistories";
    try
    {
      return jdField_if.getCompletedWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireHistories getPendingWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getPendingWireHistories";
    try
    {
      return jdField_if.getPendingWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireHistories getApprovalWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getApprovalWireHistories";
    try
    {
      return jdField_if.getApprovalWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireBatch getWireBatchById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getWireBatchById";
    try
    {
      return jdField_if.getWireBatchById(paramSecureUser, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getFilteredWires(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getFilteredWires";
    try
    {
      return jdField_if.getFilteredWires(paramSecureUser, paramWireTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static HashMap getWiresConfiguration(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getWiresConfiguration";
    try
    {
      return jdField_if.getWiresConfiguration(paramSecureUser, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean isBatchDeletable(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.isBatchDeletable";
    try
    {
      return jdField_if.isBatchDeletable(paramSecureUser, paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireHistories getPagedWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireHandler.getPagedWireHistories";
    try
    {
      return jdField_if.getPagedWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getPagedWireTemplates(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getPagedWireTemplates";
    try
    {
      return jdField_if.getPagedWireTemplates(paramSecureUser, paramPagingContext, paramWireTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static WireTransfers getDuplicateWires(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getDuplicateWires";
    try
    {
      return jdField_if.getDuplicateWires(paramSecureUser, paramWireTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.WireHandler
 * JD-Core Version:    0.7.0.1
 */