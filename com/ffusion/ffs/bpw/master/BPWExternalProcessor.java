package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.BPWExternalAPI;
import com.ffusion.ffs.bpw.interfaces.CustPayeeRslt;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ETFACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ETFACHEntryInfo;
import com.ffusion.ffs.bpw.interfaces.ETFACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRslt;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class BPWExternalProcessor
  implements BPWExternalAPI, DBConsts, FFSConst
{
  private static final long bF = -5403340294785758285L;
  
  public void processWireApprovalRslt(WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("BPWExternalProcessor.processWireApprovalRslt: starts", 6);
    WireApprovalRsltProcessor localWireApprovalRsltProcessor = new WireApprovalRsltProcessor();
    WireApprovalRsltProcessor.processWireApprovalRslt(paramArrayOfWireInfo);
    FFSDebug.log("BPWExternalProcessor.processWireApprovalRslt: ends", 6);
  }
  
  public void processWireBackendlRslt(WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("BPWExternalProcessor.processWireBackendlRslt: starts", 6);
    WireBackendRsltProcessor localWireBackendRsltProcessor = new WireBackendRsltProcessor();
    localWireBackendRsltProcessor.processRslt(paramArrayOfWireInfo);
    FFSDebug.log("BPWExternalProcessor.processWireBackendlRslt: ends", 6);
  }
  
  public void processWireApprovalRevertRslt(WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("BPWExternalProcessor.processWireApprovalRevertRslt: starts", 6);
    WireApprovalRsltProcessor localWireApprovalRsltProcessor = new WireApprovalRsltProcessor();
    WireApprovalRsltProcessor.processWireApprovalRevertRslt(paramArrayOfWireInfo);
    FFSDebug.log("BPWExternalProcessor.processWireApprovalRevertRslt: ends", 6);
  }
  
  public void processTransferFundRslt(TransferInfo[] paramArrayOfTransferInfo)
    throws Exception
  {
    FFSDebug.log("BPWExternalProcessor.processTransferFundRslt: starts", 6);
    TransferFundsRsltProcessor.processFundsTransferRslt(paramArrayOfTransferInfo);
    FFSDebug.log("BPWExternalProcessor.processTransferFundRslt: ends", 6);
  }
  
  public void processTransferFundRevertRslt(TransferInfo[] paramArrayOfTransferInfo)
    throws Exception
  {
    FFSDebug.log("BPWExternalProcessor.processTransferFundRevertRslt: starts", 6);
    TransferFundsRsltProcessor.processFundsTransferRevertRslt(paramArrayOfTransferInfo);
    FFSDebug.log("BPWExternalProcessor.processTransferFundRevertRslt: ends", 6);
  }
  
  public void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processIntraTrnRslt(paramArrayOfIntraTrnRslt);
  }
  
  public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processPmtTrnRslt(paramArrayOfPmtTrnRslt);
  }
  
  public void processOnePmtRslt(PmtTrnRslt paramPmtTrnRslt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processOnePmtRslt(paramPmtTrnRslt);
  }
  
  public void processOnePmtRslt(PmtTrnRslt paramPmtTrnRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processOnePmtRslt(paramPmtTrnRslt, paramFFSConnectionHolder);
  }
  
  public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processCustPayeeRslt(paramArrayOfCustPayeeRslt);
  }
  
  public void processOneCustPayeeRslt(CustPayeeRslt paramCustPayeeRslt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processOneCustPayeeRslt(paramCustPayeeRslt);
  }
  
  public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processCustPayeeRslt(paramArrayOfCustPayeeRslt, paramFFSConnectionHolder);
  }
  
  public void processOneCustPayeeRslt(CustPayeeRslt paramCustPayeeRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processOneCustPayeeRslt(paramCustPayeeRslt, paramFFSConnectionHolder);
  }
  
  public String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.addPayeeFromBackend(paramPayeeInfo);
  }
  
  public void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.addPayeeRouteInfo(paramPayeeRouteInfo);
  }
  
  public void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.processPayeeRslt(paramArrayOfPayeeRslt);
  }
  
  public void ProcessFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws Exception
  {
    FundAllocRsltProcessor localFundAllocRsltProcessor = new FundAllocRsltProcessor();
    FundAllocRsltProcessor.ProcessFundAllocRslt(paramArrayOfFundsAllocRslt);
  }
  
  public void ProcessOneFundAllocRslt(FundsAllocRslt paramFundsAllocRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FundAllocRsltProcessor localFundAllocRsltProcessor = new FundAllocRsltProcessor();
    FundAllocRsltProcessor.ProcessOneFundAllocRslt(paramFundsAllocRslt, paramFFSConnectionHolder);
  }
  
  public void ProcessFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws Exception
  {
    FundAllocRsltProcessor localFundAllocRsltProcessor = new FundAllocRsltProcessor();
    FundAllocRsltProcessor.ProcessFundRevertRslt(paramArrayOfFundsAllocRslt);
  }
  
  public void ProcessOneFundRevertRslt(FundsAllocRslt paramFundsAllocRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FundAllocRsltProcessor localFundAllocRsltProcessor = new FundAllocRsltProcessor();
    FundAllocRsltProcessor.ProcessOneFundRevertRslt(paramFundsAllocRslt, paramFFSConnectionHolder);
  }
  
  public PmtInfo[] getFailedPmt(String paramString)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getFailedPmt(paramString);
  }
  
  public PmtInfo[] getNewFailedPmt()
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getNewFailedPmt();
  }
  
  public IntraTrnInfo[] getFailedXfer(String paramString)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getFailedXfer(paramString);
  }
  
  public IntraTrnInfo[] getNewFailedXfer()
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getNewFailedXfer();
  }
  
  public String[] getPayeeNames(String paramString)
    throws Exception, OutOfMemoryError
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getPayeeNames(paramString);
  }
  
  public String[] getPayeeIDs(String paramString)
    throws Exception, OutOfMemoryError
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getPayeeIDs(paramString);
  }
  
  public PayeeInfo[] getPayees(String paramString)
    throws Exception, OutOfMemoryError
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getPayees(paramString);
  }
  
  public void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.updatePayee(paramPayeeInfo, paramPayeeRouteInfo);
  }
  
  public void deletePayee(String paramString)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.deletePayee(paramString);
  }
  
  public void deletePayees(String[] paramArrayOfString)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.deletePayees(paramArrayOfString);
  }
  
  public PayeeInfo findPayeeByID(String paramString)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.findPayeeByID(paramString);
  }
  
  public PayeeInfo[] getMostUsedPayees(int paramInt)
    throws Exception, OutOfMemoryError
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getMostUsedPayees(paramInt);
  }
  
  public PayeeInfo[] getLinkedPayees()
    throws Exception, OutOfMemoryError
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getLinkedPayees();
  }
  
  public PayeeInfo[] searchGlobalPayees(String paramString)
    throws Exception, OutOfMemoryError
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.searchGlobalPayees(paramString);
  }
  
  public PayeeInfo[] getPreferedPayees(String paramString)
    throws Exception, OutOfMemoryError
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getPreferedPayees(paramString);
  }
  
  public void processTransferBackendlRslt(TransferInfo[] paramArrayOfTransferInfo)
    throws Exception
  {
    FFSDebug.log("BPWExternalProcessor.processTransferBackendlRslt: starts", 6);
    TransferBackendRsltProcessor localTransferBackendRsltProcessor = new TransferBackendRsltProcessor();
    localTransferBackendRsltProcessor.processRslt(paramArrayOfTransferInfo);
    FFSDebug.log("BPWExternalProcessor.processTransferBackendlRslt: ends", 6);
  }
  
  public ETFACHFileInfo addETFACHFile(String paramString, ETFACHFileInfo paramETFACHFileInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramString);
    return Transfer.addETFACHFile(localFFSConnectionHolder, paramETFACHFileInfo);
  }
  
  public ETFACHBatchInfo addETFACHBatch(String paramString, ETFACHBatchInfo paramETFACHBatchInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramString);
    return Transfer.addETFACHBatch(localFFSConnectionHolder, paramETFACHBatchInfo);
  }
  
  public ETFACHEntryInfo addETFACHEntry(String paramString, ETFACHEntryInfo paramETFACHEntryInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramString);
    return Transfer.addETFACHEntry(localFFSConnectionHolder, paramETFACHEntryInfo);
  }
  
  public ETFACHBatchInfo updateETFACHBatchWithCtrlInfo(String paramString, ETFACHBatchInfo paramETFACHBatchInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramString);
    return Transfer.updateETFACHBatchWithCtrlInfo(localFFSConnectionHolder, paramETFACHBatchInfo);
  }
  
  public ETFACHFileInfo deleteETFACHFile(String paramString, ETFACHFileInfo paramETFACHFileInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramString);
    return Transfer.deleteETFACHFile(localFFSConnectionHolder, paramETFACHFileInfo);
  }
  
  public ETFACHFileInfo updateETFACHFileWithCtrlInfo(String paramString, ETFACHFileInfo paramETFACHFileInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramString);
    return Transfer.updateETFACHFileWithCtrlInfo(localFFSConnectionHolder, paramETFACHFileInfo);
  }
  
  public ETFACHBatchInfo deleteETFACHBatch(String paramString, ETFACHBatchInfo paramETFACHBatchInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramString);
    return Transfer.deleteETFACHBatch(localFFSConnectionHolder, paramETFACHBatchInfo);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.BPWExternalProcessor
 * JD-Core Version:    0.7.0.1
 */