package com.ffusion.ffs.bpw.BPWServices;

import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
import com.ffusion.ffs.bpw.interfaces.BankingDays;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList;
import com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfoList;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
import com.ffusion.ffs.bpw.interfaces.LastPaymentInfo;
import com.ffusion.ffs.bpw.interfaces.NonBusinessDay;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo;
import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.interfaces.WireReleaseInfo;
import com.ffusion.ffs.bpw.util.AccountTypesMap;
import com.ffusion.ffs.interfaces.FFSException;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public abstract interface BPWServices
  extends EJBObject
{
  public abstract void disconnect()
    throws RemoteException;
  
  public abstract int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
    throws RemoteException;
  
  public abstract int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
    throws RemoteException;
  
  public abstract int deleteCustomers(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract int deactivateCustomers(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract int activateCustomers(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract int deleteCustomers(String[] paramArrayOfString, int paramInt)
    throws RemoteException;
  
  public abstract CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract CustomerInfo[] getCustomerByType(String paramString)
    throws RemoteException;
  
  public abstract CustomerInfo[] getCustomerByFI(String paramString)
    throws RemoteException;
  
  public abstract CustomerInfo[] getCustomerByCategory(String paramString)
    throws RemoteException;
  
  public abstract CustomerInfo[] getCustomerByGroup(String paramString)
    throws RemoteException;
  
  public abstract CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract RPPSBillerInfo[] getRPPSBillerInfoByFIRPPSId(String paramString)
    throws FFSException, RemoteException;
  
  public abstract RPPSBillerInfo getRPPSBillerInfoByFIAndBillerRPPSId(String paramString1, String paramString2)
    throws FFSException, RemoteException;
  
  public abstract WireInfo addWireTransfer(WireInfo paramWireInfo)
    throws RemoteException;
  
  public abstract WireInfo modWireTransfer(WireInfo paramWireInfo)
    throws RemoteException;
  
  public abstract WireInfo cancWireTransfer(WireInfo paramWireInfo)
    throws RemoteException;
  
  public abstract WireInfo getWireTransferById(String paramString)
    throws RemoteException;
  
  public abstract WireInfo getWireTransfer(String paramString)
    throws RemoteException;
  
  public abstract WireInfo[] getWireTransfers(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract BPWHist getDuplicateWires(WireInfo paramWireInfo)
    throws RemoteException;
  
  public abstract WireInfo[] getBatchWires(String paramString)
    throws RemoteException;
  
  public abstract BPWHist getWireHistory(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract BPWHist getWireHistoryByCustomer(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract BPWHist getCombinedWireHistory(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract WireInfo[] getAuditWireTransfer(String paramString)
    throws RemoteException;
  
  public abstract WireInfo[] getAuditWireTransferByExtId(String paramString)
    throws RemoteException;
  
  public abstract WireReleaseInfo getWireReleaseCount(WireReleaseInfo paramWireReleaseInfo)
    throws RemoteException;
  
  public abstract WireInfo[] addWireTransfers(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException;
  
  public abstract WireInfo[] releaseWireTransfer(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException;
  
  public abstract RecWireInfo addRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws RemoteException;
  
  public abstract RecWireInfo modRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws RemoteException;
  
  public abstract RecWireInfo cancRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws RemoteException;
  
  public abstract RecWireInfo getRecWireTransferById(String paramString)
    throws RemoteException;
  
  public abstract RecWireInfo getRecWireTransferById(String paramString, boolean paramBoolean)
    throws RemoteException;
  
  public abstract RecWireInfo getRecWireTransfer(String paramString)
    throws RemoteException;
  
  public abstract RecWireInfo[] getRecWireTransfers(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract BPWHist getRecWireHistory(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract BPWHist getRecWireHistoryByCustomer(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract RecWireInfo[] addRecWireTransfers(RecWireInfo[] paramArrayOfRecWireInfo)
    throws RemoteException;
  
  public abstract HashMap getWiresConfiguration()
    throws RemoteException;
  
  public abstract WireBatchInfo addWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws RemoteException;
  
  public abstract WireBatchInfo modWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws RemoteException;
  
  public abstract WireBatchInfo canWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws RemoteException;
  
  public abstract WireBatchInfo[] getWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws RemoteException;
  
  public abstract BPWHist getWireBatchHistory(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract boolean isWireBatchDeleteable(String paramString)
    throws RemoteException;
  
  public abstract WirePayeeInfo addWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException;
  
  public abstract WirePayeeInfo[] addWirePayee(WirePayeeInfo[] paramArrayOfWirePayeeInfo)
    throws RemoteException;
  
  public abstract WirePayeeInfo modWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException;
  
  public abstract WirePayeeInfo canWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException;
  
  public abstract WirePayeeInfo getWirePayee(String paramString)
    throws RemoteException;
  
  public abstract WirePayeeInfo getWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException;
  
  public abstract BPWPayeeList getWirePayeeByType(BPWPayeeList paramBPWPayeeList)
    throws RemoteException;
  
  public abstract BPWPayeeList getWirePayeeByStatus(BPWPayeeList paramBPWPayeeList)
    throws RemoteException;
  
  public abstract BPWPayeeList getWirePayeeByGroup(BPWPayeeList paramBPWPayeeList)
    throws RemoteException;
  
  public abstract BPWPayeeList getWirePayeeByCustomer(BPWPayeeList paramBPWPayeeList)
    throws RemoteException;
  
  public abstract int addIntermediaryBanksToBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException;
  
  public abstract int delIntermediaryBanksFromBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException;
  
  public abstract BPWBankInfo[] addWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException;
  
  public abstract BPWBankInfo[] modWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException;
  
  public abstract BPWBankInfo[] delWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException;
  
  public abstract BPWBankInfo[] getWireBanks(String paramString1, String paramString2, String paramString3, String paramString4)
    throws RemoteException;
  
  public abstract BPWBankInfo[] getWireBanksByRTN(String paramString)
    throws RemoteException;
  
  public abstract BPWBankInfo getWireBanksByID(String paramString)
    throws RemoteException;
  
  public abstract void processWireApprovalRslt(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException;
  
  public abstract void processWireBackendlRslt(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException;
  
  public abstract void processWireApprovalRevertRslt(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException;
  
  public abstract BPWFIInfo addBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws RemoteException;
  
  public abstract BPWFIInfo modBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws RemoteException;
  
  public abstract BPWFIInfo canBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws RemoteException;
  
  public abstract BPWFIInfo activateBPWFI(String paramString)
    throws RemoteException;
  
  public abstract BPWFIInfo getBPWFIInfo(String paramString)
    throws RemoteException;
  
  public abstract BPWFIInfo[] getBPWFIInfo(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract BPWFIInfo[] getBPWFIInfoByType(String paramString)
    throws RemoteException;
  
  public abstract BPWFIInfo[] getBPWFIInfoByStatus(String paramString)
    throws RemoteException;
  
  public abstract BPWFIInfo[] getBPWFIInfoByGroup(String paramString)
    throws RemoteException;
  
  public abstract BPWFIInfo getBPWFIInfoByACHId(String paramString)
    throws RemoteException;
  
  public abstract BPWFIInfo getBPWFIInfoByRTN(String paramString)
    throws RemoteException;
  
  public abstract RPPSFIInfo addRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException, RemoteException;
  
  public abstract RPPSFIInfo getRPPSFIInfoByFIId(String paramString)
    throws FFSException, RemoteException;
  
  public abstract RPPSFIInfo getRPPSFIInfoByFIRPPSId(String paramString)
    throws FFSException, RemoteException;
  
  public abstract RPPSFIInfo canRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException, RemoteException;
  
  public abstract RPPSFIInfo activateRPPSFI(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException, RemoteException;
  
  public abstract RPPSFIInfo modRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException, RemoteException;
  
  public abstract int getSmartDate(String paramString, int paramInt)
    throws RemoteException, EJBException;
  
  public abstract void processApprovalResult(String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws RemoteException, EJBException, FFSException;
  
  public abstract CCCompanyInfo addCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyInfo cancelCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyInfo modCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyInfo getCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyInfoList getCCCompanyList(CCCompanyInfoList paramCCCompanyInfoList)
    throws FFSException, RemoteException;
  
  public abstract CutOffInfo getNextCashConCutOff(String paramString1, String paramString2)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyAcctInfo addCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyAcctInfo cancelCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyAcctInfo modCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyAcctInfo getCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyAcctInfoList getCCCompanyAcctList(CCCompanyAcctInfoList paramCCCompanyAcctInfoList)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyCutOffInfo addCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyCutOffInfo cancelCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyCutOffInfo getCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException, RemoteException;
  
  public abstract CCCompanyCutOffInfoList getCCCompanyCutOffList(CCCompanyCutOffInfoList paramCCCompanyCutOffInfoList)
    throws FFSException, RemoteException;
  
  public abstract CCLocationInfo addCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException, RemoteException;
  
  public abstract CCLocationInfo cancelCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException, RemoteException;
  
  public abstract CCLocationInfo modCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException, RemoteException;
  
  public abstract CCLocationInfo getCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException, RemoteException;
  
  public abstract CCLocationInfoList getCCLocationList(CCLocationInfoList paramCCLocationInfoList)
    throws FFSException, RemoteException;
  
  public abstract CCEntryInfo addCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException, RemoteException;
  
  public abstract CCEntryInfo cancelCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException, RemoteException;
  
  public abstract CCEntryInfo modCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException, RemoteException;
  
  public abstract CCEntryInfo getCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException, RemoteException;
  
  public abstract CCEntryHistInfo getCCEntryHist(CCEntryHistInfo paramCCEntryHistInfo)
    throws FFSException, RemoteException;
  
  public abstract CCEntrySummaryInfoList getCCEntrySummaryList(CCEntrySummaryInfoList paramCCEntrySummaryInfoList)
    throws FFSException, RemoteException;
  
  public abstract TransferInfo addTransfer(TransferInfo paramTransferInfo)
    throws RemoteException;
  
  public abstract TransferInfo modTransfer(TransferInfo paramTransferInfo)
    throws RemoteException;
  
  public abstract TransferInfo cancTransfer(TransferInfo paramTransferInfo)
    throws RemoteException;
  
  /**
   * @deprecated
   */
  public abstract TransferInfo getTransferBySrvrTId(String paramString)
    throws RemoteException;
  
  public abstract TransferInfo getTransferBySrvrTId(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract TransferInfo getTransferByTrackingId(String paramString)
    throws RemoteException;
  
  public abstract TransferInfo[] getTransfersBySrvrTId(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract TransferInfo[] getTransfersByRecSrvrTId(String paramString, boolean paramBoolean)
    throws RemoteException;
  
  public abstract TransferInfo[] getTransfersByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
    throws RemoteException;
  
  public abstract TransferInfo[] getTransfersByTrackingId(String[] paramArrayOfString)
    throws RemoteException;
  
  /**
   * @deprecated
   */
  public abstract BPWHist getTransferHistory(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract TransferInfo[] addTransfers(TransferInfo[] paramArrayOfTransferInfo)
    throws RemoteException;
  
  public abstract RecTransferInfo addRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws RemoteException;
  
  public abstract RecTransferInfo modRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws RemoteException;
  
  public abstract RecTransferInfo cancRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws RemoteException;
  
  /**
   * @deprecated
   */
  public abstract RecTransferInfo getRecTransferBySrvrTId(String paramString)
    throws RemoteException;
  
  public abstract RecTransferInfo getRecTransferBySrvrTId(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract RecTransferInfo getRecTransferByTrackingId(String paramString)
    throws RemoteException;
  
  public abstract RecTransferInfo[] getRecTransfersBySrvrTId(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract BPWHist getRecTransfers(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract RecTransferInfo[] getRecTransfersByTrackingId(String[] paramArrayOfString)
    throws RemoteException;
  
  /**
   * @deprecated
   */
  public abstract BPWHist getRecTransferHistory(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract ExtTransferCompanyInfo addExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException;
  
  public abstract ExtTransferCompanyInfo canExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException;
  
  public abstract ExtTransferCompanyInfo modExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException;
  
  public abstract ExtTransferCompanyInfo getExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException;
  
  public abstract ExtTransferCompanyList getExtTransferCompanyList(ExtTransferCompanyList paramExtTransferCompanyList)
    throws RemoteException;
  
  public abstract ExtTransferAcctInfo addExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException;
  
  public abstract ExtTransferAcctInfo canExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException;
  
  public abstract ExtTransferAcctInfo modExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException;
  
  public abstract ExtTransferAcctInfo getExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException;
  
  public abstract ExtTransferAcctInfo verifyExtTransferAccount(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo, int[] paramArrayOfInt)
    throws RemoteException, FFSException;
  
  public abstract ExtTransferAcctInfo depositAmountsForVerify(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, FFSException;
  
  public abstract ExtTransferAcctInfo activateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, FFSException;
  
  public abstract ExtTransferAcctInfo inactivateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, FFSException;
  
  public abstract ExtTransferAcctList getExtTransferAcctList(ExtTransferAcctList paramExtTransferAcctList)
    throws RemoteException;
  
  public abstract NonBusinessDay[] getNonBusinessDays(String paramString)
    throws RemoteException, FFSException;
  
  public abstract NonBusinessDay[] getNonBusinessDaysFromFile(String paramString)
    throws RemoteException, FFSException;
  
  public abstract PagingInfo getPagedWire(PagingInfo paramPagingInfo)
    throws RemoteException, FFSException;
  
  public abstract PagingInfo getPagedTransfer(PagingInfo paramPagingInfo)
    throws RemoteException, FFSException;
  
  public abstract PagingInfo getPagedBillPayments(PagingInfo paramPagingInfo)
    throws RemoteException, FFSException;
  
  public abstract int getValidTransferDateDue(TransferInfo paramTransferInfo)
    throws RemoteException;
  
  public abstract PagingInfo getPagedCashCon(PagingInfo paramPagingInfo)
    throws RemoteException, FFSException;
  
  public abstract PayeeInfo getPayeeByListId(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract AccountTypesMap getAccountTypesMap()
    throws RemoteException, FFSException;
  
  public abstract ExtTransferAcctInfo modExtTransferAccountPrenoteStatus(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException;
  
  public abstract String getBPWProperty(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract TransferBatchInfo addTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws RemoteException, FFSException;
  
  public abstract TransferBatchInfo modifyTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws RemoteException, FFSException;
  
  public abstract TransferBatchInfo cancelTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws RemoteException, FFSException;
  
  public abstract TransferBatchInfo getTransferBatchById(String paramString)
    throws RemoteException, FFSException;
  
  public abstract AccountTransactions accountHasPendingTransfers(AccountTransactions paramAccountTransactions)
    throws RemoteException, FFSException;
  
  public abstract PmtInfo addBillPayment(PmtInfo paramPmtInfo)
    throws RemoteException, FFSException;
  
  public abstract PmtInfo modifyBillPayment(PmtInfo paramPmtInfo)
    throws RemoteException, FFSException;
  
  public abstract PmtInfo deleteBillPayment(PmtInfo paramPmtInfo)
    throws RemoteException, FFSException;
  
  public abstract PmtInfo getBillPaymentById(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract PaymentBatchInfo addPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws RemoteException, FFSException;
  
  public abstract PaymentBatchInfo modifyPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws RemoteException, FFSException;
  
  public abstract PaymentBatchInfo deletePaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws RemoteException, FFSException;
  
  public abstract PaymentBatchInfo getPaymentBatchById(String paramString)
    throws RemoteException, FFSException;
  
  public abstract LastPaymentInfo getLastPayments(LastPaymentInfo paramLastPaymentInfo)
    throws RemoteException, FFSException;
  
  public abstract BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException, RemoteException;
  
  public abstract CustomerPayeeInfo addCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException, RemoteException;
  
  public abstract CustomerPayeeInfo deleteCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException, RemoteException;
  
  public abstract CustomerPayeeInfo updateCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException, RemoteException;
  
  public abstract CustomerPayeeInfo[] getCustomerPayees(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException, RemoteException;
  
  public abstract PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
    throws FFSException, RemoteException;
  
  public abstract PayeeInfo getGlobalPayee(String paramString)
    throws RemoteException, FFSException;
  
  public abstract void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws RemoteException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.BPWServices
 * JD-Core Version:    0.7.0.1
 */