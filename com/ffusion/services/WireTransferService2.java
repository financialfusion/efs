package com.ffusion.services;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireBatches;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransferPayees;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.PagingContext;
import java.util.Date;
import java.util.HashMap;

public abstract interface WireTransferService2
{
  public abstract int initialize(String paramString);
  
  public abstract WireTransfer addWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void modifyWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getWireTransfers(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getWireTransfersByBatchId(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getWireTemplates(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfer getWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfer getRecWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfer getCompletedWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getPagedCompletedWires(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireBatches getPagedCompletedWireBatches(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getPagedPendingWires(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireBatches getPagedPendingWireBatches(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransferPayee addWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransferPayee modifyWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransferPayees getWireTransferPayees(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransferPayee getWireTransferPayeeById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException;
  
  public abstract int getReleaseWiresCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getReleaseWires(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void releaseWires(SecureUser paramSecureUser, WireTransfers paramWireTransfers, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireBatch addWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void modifyWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfer addHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void modifyHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract Date getSmartDate(SecureUser paramSecureUser, DateTime paramDateTime)
    throws CSILException;
  
  public abstract void addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getAuditHistoryById(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransferBank getBPWFIById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfer processApprovalResult(SecureUser paramSecureUser, WireTransfer paramWireTransfer, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransferBanks getBPWFIs()
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.WireTransferService2
 * JD-Core Version:    0.7.0.1
 */