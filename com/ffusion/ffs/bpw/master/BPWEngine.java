package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ConsumerCrossRef;
import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustRoute;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.CustomerBank;
import com.ffusion.ffs.bpw.db.CustomerProductAccess;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SmartDate;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.interfaces.ACHBatchHist;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyList;
import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFileHist;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeList;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
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
import com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo;
import com.ffusion.ffs.bpw.interfaces.CustPayeeRslt;
import com.ffusion.ffs.bpw.interfaces.CustomerBankInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerRouteInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
import com.ffusion.ffs.bpw.interfaces.LastPaymentInfo;
import com.ffusion.ffs.bpw.interfaces.NonBusinessDay;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRslt;
import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo;
import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.interfaces.WireReleaseInfo;
import com.ffusion.ffs.bpw.purge.PurgeProcessor;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.AccountTypesMap;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXResponse;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV1Aggregate;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BPWEngine
  implements FFSConst, DBConsts, BPWResource
{
  private RsCmBuilder by;
  private PmtProcessor bq;
  private PmtProcessor2 bc;
  private RecPmtProcessor a1;
  private RecPmtProcessor2 bk;
  private PmtSyncProcessor a7;
  private PmtSyncProcessor2 aU;
  private RecPmtSyncProcessor aN;
  private RecPmtSyncProcessor2 br;
  private XferProcessor a3;
  private XferProcessor2 bl;
  private RecXferProcessor a8;
  private RecXferProcessor2 bi;
  private XferSyncProcessor bm;
  private XferSyncProcessor2 aT;
  private RecXferSyncProcessor a6;
  private RecXferSyncProcessor2 aS;
  private PayeeProcessor aZ;
  private PayeeProcessor2 a0;
  private PayeeSyncProcessor bh;
  private PayeeSyncProcessor2 a4;
  private BackendProcessor aQ;
  private PaymentPayeeProcessor bg;
  private CustomerProcessor bb;
  private WireProcessor aX;
  private WirePayeeProcessor aP;
  private WireBankProcessor aR;
  private TransferProcessor bj;
  private ExtTransferAcctProcessor a9;
  private ExtTransferCompProcessor bx;
  private PaymentProcessor aW;
  private ACHCompanyProcessor aV;
  private BPWFIInfoProcessor bt;
  private ACHFIInfoProcessor bn;
  private ACHPayeeProcessor aO;
  private ACHBatchTemplateProcessor bf;
  private ACHCompOffsetAcctProcessor bo;
  private ACHBatchProcessor bu;
  private ACHFileProcessor bd;
  private ACHTotalsProcessor be;
  private RPPSProcessor aY;
  private CashConProcessor bs;
  private ACHSameDayEffDateProcessor bw;
  private PropertyConfig ba = null;
  private boolean bp = false;
  private PurgeProcessor a5 = null;
  private BPWExternalProcessor a2 = null;
  private AccountTypesMap bv = null;
  
  public void start()
  {
    this.by = new RsCmBuilder();
    this.bq = new PmtProcessor();
    this.bc = new PmtProcessor2();
    this.a1 = new RecPmtProcessor(this.bq);
    this.bk = new RecPmtProcessor2(this.bc);
    this.a7 = new PmtSyncProcessor(this.bq);
    this.aU = new PmtSyncProcessor2(this.bc);
    this.aN = new RecPmtSyncProcessor(this.a1);
    this.br = new RecPmtSyncProcessor2(this.bk);
    this.a3 = new XferProcessor();
    this.bl = new XferProcessor2();
    this.a8 = new RecXferProcessor(this.a3);
    this.bi = new RecXferProcessor2(this.bl);
    this.bm = new XferSyncProcessor(this.a3);
    this.aT = new XferSyncProcessor2(this.bl);
    this.a6 = new RecXferSyncProcessor(this.a8);
    this.aS = new RecXferSyncProcessor2(this.bi);
    this.aZ = new PayeeProcessor();
    this.a0 = new PayeeProcessor2();
    this.bh = new PayeeSyncProcessor(this.aZ);
    this.a4 = new PayeeSyncProcessor2(this.a0);
    this.aQ = new BackendProcessor();
    this.bb = new CustomerProcessor();
    this.ba = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.bp = this.ba.getEnforceEnrollment();
    this.bg = new PaymentPayeeProcessor();
    this.aX = new WireProcessor();
    this.aP = new WirePayeeProcessor();
    this.aR = new WireBankProcessor();
    this.bj = new TransferProcessor();
    this.a9 = new ExtTransferAcctProcessor();
    this.bx = new ExtTransferCompProcessor();
    this.aW = new PaymentProcessor();
    this.aV = new ACHCompanyProcessor();
    this.bt = new BPWFIInfoProcessor();
    this.bn = new ACHFIInfoProcessor();
    this.aO = new ACHPayeeProcessor();
    this.bf = new ACHBatchTemplateProcessor();
    this.bo = new ACHCompOffsetAcctProcessor();
    this.bu = new ACHBatchProcessor();
    this.bd = new ACHFileProcessor();
    this.be = new ACHTotalsProcessor();
    this.aY = new RPPSProcessor();
    this.bs = new CashConProcessor();
    this.a2 = new BPWExternalProcessor();
    this.bw = new ACHSameDayEffDateProcessor();
    this.a5 = new PurgeProcessor();
    this.bv = new AccountTypesMap();
    this.bv.init();
    BPWTrackingIDGenerator.initialize();
  }
  
  public void stop()
  {
    CustPayee.clearCache();
    Payee.clearCache();
  }
  
  public AccountTypesMap getAccountTypesMap()
  {
    return this.bv;
  }
  
  public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
    throws Exception
  {
    int i = 0;
    if (paramArrayOfCustomerInfo == null) {
      return 0;
    }
    for (int j = 0; j < paramArrayOfCustomerInfo.length; j++) {
      try
      {
        int k = this.bb.addCustomer(paramArrayOfCustomerInfo[j]);
        if (k == 1) {
          i++;
        } else {
          FFSDebug.log("Failed to add customer rows updated: " + k, 0);
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("Failed to add customer : " + paramArrayOfCustomerInfo[j].toString(), 0);
        FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
        FFSDebug.log("SERVER WILL IGNORE THIS CUSTOMER AND CONTINUE PROCESSING", 0);
      }
    }
    FFSDebug.log("Number of Customers added successfully: " + i, 6);
    return i;
  }
  
  public String[] getGlobalPayeeGroups()
    throws FFSException
  {
    return this.bg.getGlobalPayeeGroups();
  }
  
  public PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
    throws FFSException
  {
    return this.bg.searchGlobalPayees(paramPayeeInfo, paramInt);
  }
  
  public PayeeInfo getGlobalPayee(String paramString)
    throws FFSException
  {
    return this.bg.getGlobalPayee(paramString);
  }
  
  public PayeeInfo updateGlobalPayee(PayeeInfo paramPayeeInfo)
    throws RemoteException, FFSException
  {
    return this.bg.updateGlobalPayee(paramPayeeInfo);
  }
  
  public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
    throws Exception
  {
    int i = 0;
    if (paramArrayOfCustomerInfo == null) {
      return 0;
    }
    for (int j = 0; j < paramArrayOfCustomerInfo.length; j++) {
      try
      {
        int k = this.bb.updateCustomer(paramArrayOfCustomerInfo[j]);
        if (k == 1) {
          i++;
        } else {
          FFSDebug.log("Failed to modify customer rows updated: " + k, 0);
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("Failed to modify customer : " + paramArrayOfCustomerInfo[j].toString(), 0);
        FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
        FFSDebug.log("SERVER WILL IGNORE THIS CUSTOMER AND CONTINUE PROCESSING", 0);
      }
    }
    FFSDebug.log("Number of Customers modified successfully: " + i, 6);
    return i;
  }
  
  public int deleteCustomers(String[] paramArrayOfString)
    throws RemoteException
  {
    int i = 0;
    if (paramArrayOfString == null) {
      return 0;
    }
    for (int j = 0; j < paramArrayOfString.length; j++) {
      try
      {
        int k = this.bb.deleteCustomer(paramArrayOfString[j]);
        if (k == 1) {
          i++;
        } else {
          FFSDebug.log("Failed to delete customer rows updated: " + k, 0);
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("Failed to delete customer : " + paramArrayOfString[j], 0);
        FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
        FFSDebug.log("SERVER WILL IGNORE THIS CUSTOMER AND CONTINUE PROCESSING", 0);
      }
    }
    FFSDebug.log("Number of Customers deleted successfully: " + i, 6);
    return i;
  }
  
  public int deactivateCustomers(String[] paramArrayOfString)
    throws Exception
  {
    int i = 0;
    if (paramArrayOfString == null) {
      return 0;
    }
    for (int j = 0; j < paramArrayOfString.length; j++) {
      try
      {
        int k = this.bb.deactivateCustomer(paramArrayOfString[j]);
        if (k == 1) {
          i++;
        } else {
          FFSDebug.log("Failed to deactivate customer rows updated: " + k, 0);
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("Failed to deactivate customer : " + paramArrayOfString[j], 0);
        FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
        FFSDebug.log("SERVER WILL IGNORE THIS CUSTOMER AND CONTINUE PROCESSING", 0);
      }
    }
    FFSDebug.log("Number of Customers deactivated successfully: " + i, 6);
    return i;
  }
  
  public int activateCustomers(String[] paramArrayOfString)
    throws Exception
  {
    int i = 0;
    if (paramArrayOfString == null) {
      return 0;
    }
    for (int j = 0; j < paramArrayOfString.length; j++) {
      try
      {
        int k = this.bb.activateCustomer(paramArrayOfString[j]);
        if (k == 1) {
          i++;
        } else {
          FFSDebug.log("Failed to deactivate customer rows updated: " + k, 0);
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("Failed to activate customer : " + paramArrayOfString[j], 0);
        FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
        FFSDebug.log("SERVER WILL IGNORE THIS CUSTOMER AND CONTINUE PROCESSING", 0);
      }
    }
    FFSDebug.log("Number of Customers activated successfully: " + i, 6);
    return i;
  }
  
  public int deleteCustomers(String[] paramArrayOfString, int paramInt)
    throws Exception
  {
    int i = 0;
    if (paramArrayOfString == null) {
      return 0;
    }
    for (int j = 0; j < paramArrayOfString.length; j++) {
      try
      {
        int k = this.bb.deleteCustomer(paramArrayOfString[j], paramInt);
        if (k == 1) {
          i++;
        } else {
          FFSDebug.log("Failed to delete customer rows updated: " + k, 0);
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("Failed to delete customer : " + paramArrayOfString[j], 0);
        FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
        FFSDebug.log("SERVER WILL IGNORE THIS CUSTOMER AND CONTINUE PROCESSING", 0);
      }
    }
    FFSDebug.log("Number of Customers deleted successfully: " + i, 6);
    return i;
  }
  
  public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
    throws Exception
  {
    LinkedList localLinkedList = new LinkedList();
    if (paramArrayOfString == null) {
      return null;
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      try
      {
        CustomerInfo localCustomerInfo = this.bb.getCustomerInfo(paramArrayOfString[i]);
        if (localCustomerInfo != null) {
          localLinkedList.add(localCustomerInfo);
        } else {
          FFSDebug.log("Failed to get customer info: " + paramArrayOfString[i], 0);
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("Failed to get Customers Info : " + paramArrayOfString[i], 0);
        FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
        FFSDebug.log("SERVER WILL IGNORE THIS CUSTOMER AND CONTINUE PROCESSING", 0);
      }
    }
    FFSDebug.log("Number of Customers retrived successfully: " + localLinkedList.size(), 6);
    return (CustomerInfo[])localLinkedList.toArray(new CustomerInfo[0]);
  }
  
  public CustomerInfo[] getCustomerByType(String paramString)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    if (paramString == null) {
      return null;
    }
    try
    {
      arrayOfCustomerInfo = this.bb.getCustomerByType(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to get Customers Info for Type: ", paramString, 0);
      FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
    }
    FFSDebug.log("Number of Customers retrived successfully: " + arrayOfCustomerInfo.length, 6);
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByFI(String paramString)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    if (paramString == null) {
      return null;
    }
    try
    {
      arrayOfCustomerInfo = this.bb.getCustomerByFI(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to get Customers Info for FI: ", paramString, 0);
      FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
    }
    FFSDebug.log("Number of Customers retrived successfully: " + arrayOfCustomerInfo.length, 6);
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByCategory(String paramString)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    if (paramString == null) {
      return null;
    }
    try
    {
      arrayOfCustomerInfo = this.bb.getCustomerByCategory(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to get Customers Info for Catg: ", paramString, 0);
      FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
    }
    FFSDebug.log("Number of Customers retrived successfully: " + arrayOfCustomerInfo.length, 6);
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByGroup(String paramString)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    if (paramString == null) {
      return null;
    }
    try
    {
      arrayOfCustomerInfo = this.bb.getCustomerByGroup(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to get Customers Info for Group: ", paramString, 0);
      FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
    }
    FFSDebug.log("Number of Customers retrived successfully: " + arrayOfCustomerInfo.length, 6);
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    try
    {
      arrayOfCustomerInfo = this.bb.getCustomerByTypeAndFI(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to get Customers Info for Type: ", paramString1, "And FI: ", paramString2, 0);
      FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
    }
    FFSDebug.log("Number of Customers retrived successfully: " + arrayOfCustomerInfo.length, 6);
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    try
    {
      arrayOfCustomerInfo = this.bb.getCustomerByCategoryAndFI(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to get Customers Info for Catg: ", paramString1, "And FI: ", paramString2, 0);
      FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
    }
    FFSDebug.log("Number of Customers retrived successfully: " + arrayOfCustomerInfo.length, 6);
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    try
    {
      arrayOfCustomerInfo = this.bb.getCustomerByGroupAndFI(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to get Customers Info for Group: ", paramString1, "And FI: ", paramString2, 0);
      FFSDebug.log("Error : " + localException.toString() + FFSDebug.stackTrace(localException), 0);
    }
    FFSDebug.log("Number of Customers retrived successfully: " + arrayOfCustomerInfo.length, 6);
    return arrayOfCustomerInfo;
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1[] getPendingPmtsByCustomerID151(String paramString, int paramInt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingPmtsByCustomerID151: start,", 6);
    return this.a7.getPendingPmtsByCustomerID(paramString, paramInt);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1[] getPendingPmtsByCustomerID200(String paramString, int paramInt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingPmtsByCustomerID200: start,", 6);
    return this.aU.getPendingPmtsByCustomerID(paramString, paramInt);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID151(String paramString, int paramInt1, int paramInt2)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingPmtsAndHistoryByCustomerID151: start,", 6);
    return this.a7.getPendingPmtsAndHistoryByCustomerID(paramString, paramInt1, paramInt2);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID200(String paramString, int paramInt1, int paramInt2)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingPmtsAndHistoryByCustomerID200: start,", 6);
    return this.aU.getPendingPmtsAndHistoryByCustomerID(paramString, paramInt1, paramInt2);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1[] getPendingIntrasByCustomerID151(String paramString, int paramInt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingIntrasByCustomerID151: start,", 6);
    return this.bm.getPendingIntrasByCustomerID(paramString, paramInt);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1[] getPendingIntrasByCustomerID200(String paramString, int paramInt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingIntrasByCustomerID200: start,", 6);
    return this.aT.getPendingIntrasByCustomerID(paramString, paramInt);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID151(String paramString, int paramInt1, int paramInt2)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingIntrasAndHistoryByCustomerID151: start,", 6);
    return this.bm.getPendingIntrasAndHistoryByCustomerID(paramString, paramInt1, paramInt2);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID200(String paramString, int paramInt1, int paramInt2)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingIntrasAndHistoryByCustomerID200: start,", 6);
    return this.aT.getPendingIntrasAndHistoryByCustomerID(paramString, paramInt1, paramInt2);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1 getPendingPmts(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingPmts: start,", 6);
    return this.a7.processPendingPmtRqV1(paramTypePmtSyncRqV1, paramTypeUserData, paramInt);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1 getPendingPmts(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingPmts: start,", 6);
    return this.aU.processPendingPmtRqV1(paramTypePmtSyncRqV1, paramTypeUserData, paramInt);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1 getPendingIntras(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingIntras: start,", 6);
    return this.bm.processPendingIntraRqV1(paramTypeIntraSyncRqV1, paramTypeUserData, paramInt);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1 getPendingIntras(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPendingIntras: start,", 6);
    return this.aT.processPendingIntraRqV1(paramTypeIntraSyncRqV1, paramTypeUserData, paramInt);
  }
  
  public PayeeInfo[] getLinkedPayees()
    throws Exception
  {
    PayeeInfo[] arrayOfPayeeInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfPayeeInfo = Payee.getLinkedPayees(localFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      String str = "Fail to get linked payees : \n Error: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getMostUsedPayees(int paramInt)
    throws Exception
  {
    PayeeInfo[] arrayOfPayeeInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfPayeeInfo = Payee.getMostUsedPayees(localFFSConnectionHolder, paramInt);
    }
    catch (Exception localException)
    {
      String str = "Fail to get most used payees : " + localException.toString() + "\n Error: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getPreferedPayees(String paramString)
    throws Exception
  {
    PayeeInfo[] arrayOfPayeeInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfPayeeInfo = Payee.getPreferedPayees(localFFSConnectionHolder, paramString);
    }
    catch (Exception localException)
    {
      String str = "Fail to get preferd payees : " + localException.toString() + "\n Error: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public String getPmtStatus(String paramString)
    throws Exception
  {
    String str = null;
    FFSDebug.log("BPWEngine.getPmtStatus: srvrTID=" + paramString, 6);
    str = this.aQ.getPmtStatus(paramString);
    return str;
  }
  
  public boolean checkPayeeEditMask(String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("BPWEngine.checkPayeeEditMask: payeeID=" + paramString1 + ",acctNum=" + paramString2, 6);
    return this.aQ.checkPayeeEditMask(paramString1, paramString2);
  }
  
  public void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processIntraTrnRslt: call", 6);
    this.aQ.processIntraTrnRslt(paramArrayOfIntraTrnRslt);
  }
  
  public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processPmtTrnRslt: call", 6);
    this.aQ.processPmtTrnRslt(paramArrayOfPmtTrnRslt);
    FFSDebug.log("BPWEngine.processPmtTrnRslt: end", 6);
  }
  
  public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processCustPayeeRslt: call", 6);
    this.aQ.processCustPayeeRslt(paramArrayOfCustPayeeRslt);
    FFSDebug.log("BPWEngine.processCustPayeeRslt: end", 6);
  }
  
  public void processFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processFundAllocRslt: call", 6);
    FundAllocRsltProcessor.ProcessFundAllocRslt(paramArrayOfFundsAllocRslt);
    FFSDebug.log("BPWEngine.processFundAllocRslt: end", 6);
  }
  
  public void processFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processFundRevertRslt: call", 6);
    FundAllocRsltProcessor.ProcessFundRevertRslt(paramArrayOfFundsAllocRslt);
    FFSDebug.log("BPWEngine.processFundRevertRslt: end", 6);
  }
  
  public void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processPayeeRslt: call", 6);
    this.aQ.processPayeeRslt(paramArrayOfPayeeRslt);
    FFSDebug.log("BPWEngine.processPayeeRslt: end", 6);
  }
  
  public String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.addPayeeFromBackend: call", 6);
    String str = this.aQ.addPayeeFromBackend(paramPayeeInfo);
    FFSDebug.log("BPWEngine.addPayeeFromBackend: end", 6);
    return str;
  }
  
  public PayeeInfo[] updatePayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.updatePayeeFromBackend: call", 6);
    PayeeInfo[] arrayOfPayeeInfo = this.aQ.updatePayeeFromBackend(paramPayeeInfo);
    FFSDebug.log("BPWEngine.updatePayeeFromBackend: end", 6);
    return arrayOfPayeeInfo;
  }
  
  public void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.addPayeeRouteInfo: call", 6);
    this.aQ.addPayeeRouteInfo(paramPayeeRouteInfo);
    FFSDebug.log("BPWEngine.addPayeeRouteInfo: end", 6);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1 processIntraSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processIntraSyncRqV1: call", 6);
    return this.bm.processIntraSyncRqV1(paramTypeIntraSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1 processIntraSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processIntraSyncRqV1: call", 6);
    return this.aT.processIntraSyncRqV1(paramTypeIntraSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1 processIntraTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processIntraTrnRqV1: call", 6);
    return this.a3.processIntraTrnRqV1(paramTypeIntraTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1 processIntraTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processIntraTrnRqV1: call", 6);
    return this.bl.processIntraTrnRqV1(paramTypeIntraTrnRqV1, paramTypeUserData);
  }
  
  public WireInfo addWireTransfer(WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addWireTrn start...", 6);
    try
    {
      return this.aX.addWireTrn(paramWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo modWireTransfer(WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modWireTrn start...", 6);
    try
    {
      return this.aX.modWireTrn(paramWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo cancWireTransfer(WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancWireTrn start...", 6);
    try
    {
      return this.aX.cancWireTrn(paramWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: cancWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo getWireTransferById(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireTransferById start...", 6);
    try
    {
      return this.aX.getWireTransferById(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireTransferById failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo getWireTransfer(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireTransfer start...", 6);
    try
    {
      return this.aX.getWireTransfer(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo[] getWireTransfers(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireTransfer start...", 6);
    try
    {
      return this.aX.getWireTransfers(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo[] getBatchWires(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getBatchWires start...", 6);
    try
    {
      return this.aX.getBatchWires(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getBatchWires failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getWireHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireHistory start...", 6);
    try
    {
      return this.aX.getWireHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getWireHistoryByCustomer(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireHistoryByCustomer start...", 6);
    try
    {
      return this.aX.getWireHistoryByCustomer(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireHistoryByCustomer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getCombinedWireHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCombinedWireHistory start...", 6);
    try
    {
      return this.aX.getCombinedWireHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getCombinedWireHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo[] getAuditWireTransfer(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getAuditWireTransfer start...", 6);
    try
    {
      return WireProcessor.getAuditWireTransfer(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getAuditWireTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo[] getAuditWireTransferByExtId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getAuditWireTransferByExtId start...", 6);
    try
    {
      return WireProcessor.getAuditWireTransferByExtId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getAuditWireTransferByExtId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireReleaseInfo getWireReleaseCount(WireReleaseInfo paramWireReleaseInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireReleaseCount start...", 6);
    try
    {
      return WireProcessor.getWireReleaseCount(paramWireReleaseInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireReleaseCount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getDuplicateWires(WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getDuplicateWires start...", 6);
    try
    {
      return this.aX.getDuplicateWires(paramWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getDuplicateWires failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo[] addWireTransfers(WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addWireTransfers start...", 6);
    try
    {
      return this.aX.addWireTransfers(paramArrayOfWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addWireTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireInfo[] releaseWireTransfer(WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.releaseWireTransfer start...", 6);
    try
    {
      return this.aX.releaseWireTransfer(paramArrayOfWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: releaseWireTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecWireInfo addRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addRecWireTrn start...", 6);
    try
    {
      return this.aX.addRecWireTrn(paramRecWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addRecWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecWireInfo modRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modRecWireTrn start...", 6);
    try
    {
      return this.aX.modRecWireTrn(paramRecWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modRecWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecWireInfo cancRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancRecWireTrn start...", 6);
    try
    {
      return this.aX.cancRecWireTrn(paramRecWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: cancRecWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecWireInfo getRecWireTransferById(String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecWireTransferById start...", 6);
    try
    {
      return this.aX.getRecWireTransferById(paramString, paramBoolean);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecWireTransferById failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecWireInfo getRecWireTransfer(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecWireTransfer start...", 6);
    try
    {
      return this.aX.getRecWireTransfer(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecWireTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecWireInfo[] getRecWireTransfers(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecWireTransfers start...", 6);
    try
    {
      return this.aX.getRecWireTransfers(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecWireTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getRecWireHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecWireHistory start...", 6);
    try
    {
      return this.aX.getRecWireHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecWireHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getRecWireHistoryByCustomer(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecWireHistoryByCustomer start...", 6);
    try
    {
      return this.aX.getRecWireHistoryByCustomer(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecWireHistoryByCustomer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecWireInfo[] addRecWireTransfers(RecWireInfo[] paramArrayOfRecWireInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addRecWireTransfers start...", 6);
    try
    {
      return this.aX.addRecWireTransfers(paramArrayOfRecWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addRecWireTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public HashMap getWiresConfiguration()
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWiresConfiguration start...", 6);
    try
    {
      return this.aX.getWiresConfiguration();
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWiresConfiguration failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireBatchInfo addWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addWireTransferBatch start...", 6);
    try
    {
      return this.aX.addWireTransferBatch(paramWireBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addWireTransferBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireBatchInfo modWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modWireTransferBatch start...", 6);
    try
    {
      return this.aX.modWireTransferBatch(paramWireBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modWireTransferBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireBatchInfo canWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canWireTransferBatch start...", 6);
    try
    {
      return this.aX.canWireTransferBatch(paramWireBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canWireTransferBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WireBatchInfo[] getWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireTransferBatch start...", 6);
    try
    {
      return this.aX.getWireTransferBatch(paramWireBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireTransferBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getWireBatchHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireBatchHistory start...", 6);
    try
    {
      return this.aX.getWireBatchHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireBatchHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public boolean isWireBatchDeleteable(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireBatchHistory start...", 6);
    try
    {
      return this.aX.isWireBatchDeleteable(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: isWireBatchDeleteable failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public String getWireStatus(String paramString)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getWireStatus: call", 6);
    return this.aX.getWireStatus(paramString);
  }
  
  public TypeOFXResponse processOFXRequest(TypeOFXRequest paramTypeOFXRequest, TypeUserData paramTypeUserData)
    throws Exception
  {
    return null;
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1 processPayeeSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processPayeeSyncRqV1: start,", 6);
    return this.bh.processPayeeSyncRqV1(paramTypePayeeSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1 processPayeeSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processPayeeSyncRqV1: start,", 6);
    return this.a4.processPayeeSyncRqV1(paramTypePayeeSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1 processPayeeTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    if ((this.bp) && (!Customer.validCustomer(paramTypeUserData._uid)))
    {
      String str = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    FFSDebug.log("BPWEngine.processPayeeTrnRqV1: start,", 6);
    return this.aZ.processPayeeTrnRqV1(paramTypePayeeTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1 processPayeeTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    if ((this.bp) && (!Customer.validCustomer(paramTypeUserData._uid)))
    {
      String str = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    FFSDebug.log("BPWEngine.processPayeeTrnRqV1: start,", 6);
    return this.a0.processPayeeTrnRqV1(paramTypePayeeTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1 processPmtInqTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processPmtInqTrnRqV1: start,", 6);
    return OFX151Handler.processPmtInqTrnRq(paramTypePmtInqTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1 processPmtInqTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processPmtInqTrnRqV1: start,", 6);
    return OFX200Handler.processPmtInqTrnRq(paramTypePmtInqTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1 processPmtSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processPmtSyncRqV1: start,", 6);
    return this.a7.processPmtSyncRqV1(paramTypePmtSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1 processPmtSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processPmtSyncRqV1: start,", 6);
    return this.aU.processPmtSyncRqV1(paramTypePmtSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 processPmtTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    if ((this.bp) && (!Customer.validCustomer(paramTypeUserData._uid)))
    {
      String str = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    FFSDebug.log("BPWEngine.processPmtTrnRqV1: start,", 6);
    return this.bq.processPmtTrnRqV1(paramTypePmtTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1 processPmtTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    if ((this.bp) && (!Customer.validCustomer(paramTypeUserData._uid)))
    {
      String str = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    FFSDebug.log("BPWEngine.processPmtTrnRqV1: start,", 6);
    return this.bc.processPmtTrnRqV1(paramTypePmtTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processRecPmtSyncRqV1: start,", 6);
    return this.aN.processRecPmtSyncRqV1(paramTypeRecPmtSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processRecPmtSyncRqV1: start,", 6);
    return this.br.processRecPmtSyncRqV1(paramTypeRecPmtSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processRecIntraSyncRqV1: call", 6);
    return this.a6.processRecIntraSyncRqV1(paramTypeRecIntraSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processRecIntraSyncRqV1: call", 6);
    return this.aS.processRecIntraSyncRqV1(paramTypeRecIntraSyncRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processRecIntraTrnRqV1: call", 6);
    return this.a8.processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processRecIntraTrnRqV1: call", 6);
    return this.bi.processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    if ((this.bp) && (!Customer.validCustomer(paramTypeUserData._uid)))
    {
      String str = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    FFSDebug.log("BPWEngine.processRecPmtTrnRqV1: start,", 6);
    return this.a1.processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, paramTypeUserData);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    if ((this.bp) && (!Customer.validCustomer(paramTypeUserData._uid)))
    {
      String str = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    FFSDebug.log("BPWEngine.processRecPmtTrnRqV1: start,", 6);
    return this.bk.processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, paramTypeUserData);
  }
  
  public TypeSonRsV1 processSonRqV1(TypeSonRqV1 paramTypeSonRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    try
    {
      TypeSonRsV1Aggregate localTypeSonRsV1Aggregate = this.by.buildSonRsV1(paramTypeSonRqV1.SonRq, null);
      return new TypeSonRsV1(localTypeSonRsV1Aggregate);
    }
    catch (BPWException localBPWException) {}
    return null;
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
  
  public PayeeInfo[] searchGlobalPayees(String paramString)
    throws Exception, OutOfMemoryError
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.searchGlobalPayees(paramString);
  }
  
  public void setPayeeStatus(String paramString1, String paramString2)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    localBackendProcessor.setPayeeStatus(paramString1, paramString2);
  }
  
  public int getSmartDate(int paramInt)
    throws Exception
  {
    return SmartCalendar.getPayday(paramInt);
  }
  
  public int getSmartDate(String paramString, int paramInt)
    throws Exception
  {
    return SmartCalendar.getPayday(paramString, paramInt);
  }
  
  public PayeeInfo[] getPayees(String paramString, int paramInt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getPayees(paramString, paramInt);
  }
  
  public PayeeInfo[] getGlobalPayees(String paramString, int paramInt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getGlobalPayees(paramString, paramInt);
  }
  
  public String[] getPayeeNames(String paramString, int paramInt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.getPayeeNames(paramString, paramInt);
  }
  
  public String addPayee(PayeeInfo paramPayeeInfo, int paramInt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.addPayee(paramPayeeInfo, paramInt);
  }
  
  public PayeeInfo[] updatePayee(PayeeInfo paramPayeeInfo, int paramInt)
    throws Exception
  {
    BackendProcessor localBackendProcessor = new BackendProcessor();
    return localBackendProcessor.updatePayee(paramPayeeInfo, paramInt);
  }
  
  public int addConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.addConsumerCrossRef start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfConsumerCrossRefInfo.length; j++) {
        if (ConsumerCrossRef.add(paramArrayOfConsumerCrossRefInfo[j], localFFSConnectionHolder) == 1) {
          i++;
        }
      }
      localFFSConnectionHolder.conn.commit();
      j = i;
      return j;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public int deleteConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.deleteConsumerCrossRef start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfConsumerCrossRefInfo.length; j++) {
        i += ConsumerCrossRef.delete(paramArrayOfConsumerCrossRefInfo[j], localFFSConnectionHolder);
      }
      localFFSConnectionHolder.conn.commit();
      j = i;
      return j;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ConsumerCrossRefInfo[] getConsumerCrossRef(String[] paramArrayOfString)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getConsumerCrossRef start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    ArrayList localArrayList = new ArrayList();
    try
    {
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        ConsumerCrossRefInfo localConsumerCrossRefInfo = ConsumerCrossRef.getByConsumerID(paramArrayOfString[i], localFFSConnectionHolder);
        if (localConsumerCrossRefInfo != null) {
          localArrayList.add(localConsumerCrossRefInfo);
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = (ConsumerCrossRefInfo[])localArrayList.toArray(new ConsumerCrossRefInfo[0]);
    return arrayOfConsumerCrossRefInfo;
  }
  
  public int addCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.addCustomerBankInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfCustomerBankInfo.length; j++) {
        if (CustomerBank.add(paramArrayOfCustomerBankInfo[j], localFFSConnectionHolder) != 0)
        {
          String str = ConsumerCrossRef.lookupCustomerID(paramArrayOfCustomerBankInfo[j].consumerID, localFFSConnectionHolder);
          if (!Customer.isExists(str, localFFSConnectionHolder))
          {
            localObject1 = "Failed to find customer with customerID: " + str;
            FFSDebug.log((String)localObject1, 0);
            throw new Exception((String)localObject1);
          }
          Object localObject1 = BPWRegistryUtil.getFulfillmentInfo("METAVANTE");
          CustomerRouteInfo localCustomerRouteInfo = CustRoute.getCustomerRoute(str, ((FulfillmentInfo)localObject1).RouteID, localFFSConnectionHolder);
          if ((localCustomerRouteInfo != null) && (!localCustomerRouteInfo.Status.equals("NEW")) && (!localCustomerRouteInfo.Status.equals("MOD")) && (localCustomerRouteInfo.Status.indexOf("CANC") == -1)) {
            CustRoute.updateCustRouteStatus(str, ((FulfillmentInfo)localObject1).RouteID, "MODACCT", localFFSConnectionHolder);
          }
          i++;
        }
      }
      localFFSConnectionHolder.conn.commit();
      j = i;
      return j;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public int deleteCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.deleteCustomerBankInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfCustomerBankInfo.length; j++) {
        if (CustomerBank.cancel(paramArrayOfCustomerBankInfo[j], localFFSConnectionHolder) != 0)
        {
          String str = ConsumerCrossRef.lookupCustomerID(paramArrayOfCustomerBankInfo[j].consumerID, localFFSConnectionHolder);
          if (!Customer.isExists(str, localFFSConnectionHolder))
          {
            localObject1 = "Failed to find customer with customerID: " + str;
            FFSDebug.log((String)localObject1, 0);
            throw new Exception((String)localObject1);
          }
          Object localObject1 = BPWRegistryUtil.getFulfillmentInfo("METAVANTE");
          CustomerRouteInfo localCustomerRouteInfo = CustRoute.getCustomerRoute(str, ((FulfillmentInfo)localObject1).RouteID, localFFSConnectionHolder);
          if ((localCustomerRouteInfo != null) && (!localCustomerRouteInfo.Status.equals("NEW")) && (!localCustomerRouteInfo.Status.equals("MOD")) && (localCustomerRouteInfo.Status.indexOf("CANC") == -1)) {
            CustRoute.updateCustRouteStatus(str, ((FulfillmentInfo)localObject1).RouteID, "MODACCT", localFFSConnectionHolder);
          }
          i++;
        }
      }
      localFFSConnectionHolder.conn.commit();
      j = i;
      return j;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public CustomerBankInfo[] getCustomerBankInfo(String[] paramArrayOfString)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getCustomerBankInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    ArrayList localArrayList = new ArrayList();
    try
    {
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        CustomerBankInfo[] arrayOfCustomerBankInfo2 = CustomerBank.getByConsumerID(paramArrayOfString[i], localFFSConnectionHolder);
        for (int j = 0; j < arrayOfCustomerBankInfo2.length; j++) {
          localArrayList.add(arrayOfCustomerBankInfo2[j]);
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    CustomerBankInfo[] arrayOfCustomerBankInfo1 = (CustomerBankInfo[])localArrayList.toArray(new CustomerBankInfo[0]);
    return arrayOfCustomerBankInfo1;
  }
  
  public int addCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.addCustomerProductAccessInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfCustomerProductAccessInfo.length; j++) {
        if (CustomerProductAccess.add(paramArrayOfCustomerProductAccessInfo[j], localFFSConnectionHolder) == 1) {
          i++;
        }
      }
      localFFSConnectionHolder.conn.commit();
      j = i;
      return j;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public int deleteCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.deleteCustomerProductAccessInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      int i = 0;
      for (int j = 0; j < paramArrayOfCustomerProductAccessInfo.length; j++) {
        i += CustomerProductAccess.cancel(paramArrayOfCustomerProductAccessInfo[j], localFFSConnectionHolder);
      }
      localFFSConnectionHolder.conn.commit();
      j = i;
      return j;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public CustomerProductAccessInfo[] getCustomerProductAccessInfo(String[] paramArrayOfString)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getCustomerProductAccessInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    ArrayList localArrayList = new ArrayList();
    try
    {
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo2 = CustomerProductAccess.get(paramArrayOfString[i], localFFSConnectionHolder);
        for (int j = 0; j < arrayOfCustomerProductAccessInfo2.length; j++) {
          localArrayList.add(arrayOfCustomerProductAccessInfo2[j]);
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo1 = (CustomerProductAccessInfo[])localArrayList.toArray(new CustomerProductAccessInfo[0]);
    return arrayOfCustomerProductAccessInfo1;
  }
  
  public boolean validateMetavanteCustAcctByConsumerID(String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("BPWEngine.validateMetavanteCustAcctByConsumerID start, ,consumerID=" + paramString1 + ",acctID=" + paramString2, 6);
    boolean bool1 = true;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      String str = ConsumerCrossRef.lookupCustomerID(paramString1, localFFSConnectionHolder);
      boolean bool2;
      if (str == null)
      {
        bool1 = false;
        bool2 = bool1;
        return bool2;
      }
      if (!Customer.validCustomer(str))
      {
        bool1 = false;
        bool2 = bool1;
        return bool2;
      }
      CustomerBankInfo localCustomerBankInfo = new CustomerBankInfo();
      localCustomerBankInfo.consumerID = paramString1;
      localCustomerBankInfo.acctNumber = paramString2;
      int i = CustomerBank.find(localCustomerBankInfo, "ACTIVE", localFFSConnectionHolder);
      if (i == 0) {
        i = CustomerBank.find(localCustomerBankInfo, "NEW", localFFSConnectionHolder);
      }
      boolean bool3;
      if (i == 0)
      {
        bool1 = false;
        bool3 = bool1;
        return bool3;
      }
      i = CustomerProductAccess.find(paramString1, "ACTIVE", localFFSConnectionHolder);
      if (i == 0) {
        i = CustomerProductAccess.find(paramString1, "NEW", localFFSConnectionHolder);
      }
      if (i == 0)
      {
        bool1 = false;
        bool3 = bool1;
        return bool3;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return bool1;
  }
  
  public boolean validateMetavanteCustAcctByCustomerID(String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("BPWEngine.validateMetavanteCustAcctByCustomerID start, ,customerID=" + paramString1 + ",acctID=" + paramString2, 6);
    boolean bool1 = true;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      String str = ConsumerCrossRef.lookupConsumerID(paramString1, localFFSConnectionHolder);
      boolean bool2;
      if (str == null)
      {
        bool1 = false;
        bool2 = bool1;
        return bool2;
      }
      if (!Customer.validCustomer(paramString1))
      {
        bool1 = false;
        bool2 = bool1;
        return bool2;
      }
      CustomerBankInfo localCustomerBankInfo = new CustomerBankInfo();
      localCustomerBankInfo.consumerID = str;
      localCustomerBankInfo.acctNumber = paramString2;
      int i = CustomerBank.find(localCustomerBankInfo, "ACTIVE", localFFSConnectionHolder);
      if (i == 0) {
        i = CustomerBank.find(localCustomerBankInfo, "NEW", localFFSConnectionHolder);
      }
      boolean bool3;
      if (i == 0)
      {
        bool1 = false;
        bool3 = bool1;
        return bool3;
      }
      i = CustomerProductAccess.find(str, "ACTIVE", localFFSConnectionHolder);
      if (i == 0) {
        i = CustomerProductAccess.find(str, "NEW", localFFSConnectionHolder);
      }
      if (i == 0)
      {
        bool1 = false;
        bool3 = bool1;
        return bool3;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return bool1;
  }
  
  public BPWFIInfo addBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addBPWFIInfo start...", 6);
    try
    {
      return this.bt.addBPWFIInfo(paramBPWFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo modBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modBPWFIInfo start...", 6);
    try
    {
      return this.bt.modBPWFIInfo(paramBPWFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo activateBPWFI(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.activateBPWFI start...", 6);
    BPWFIInfo localBPWFIInfo = null;
    try
    {
      localBPWFIInfo = this.bt.activateBPWFI(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: activateBPWFI failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    return localBPWFIInfo;
  }
  
  public BPWFIInfo canBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canBPWFIInfo start...", 6);
    try
    {
      return this.bt.canBPWFIInfo(paramBPWFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo getBPWFIInfo(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getBPWFIInfo start...", 6);
    try
    {
      return this.bt.getBPWFIInfo(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfo(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getBPWFIInfo start...", 6);
    try
    {
      return this.bt.getBPWFIInfo(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByType(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getBPWFIInfoByType start...", 6);
    try
    {
      return this.bt.getBPWFIInfoByType(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getBPWFIInfoByType failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByStatus(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getBPWFIInfoByStatus start...", 6);
    try
    {
      return this.bt.getBPWFIInfoByStatus(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getBPWFIInfoByStatus failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByGroup(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getBPWFIInfoByGroup start...", 6);
    try
    {
      return this.bt.getBPWFIInfoByGroup(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getBPWFIInfoByGroup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo getBPWFIInfoByACHId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getBPWFIInfoByACHId start...", 6);
    try
    {
      return this.bt.getBPWFIInfoByACHId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getBPWFIInfoByACHId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWFIInfo getBPWFIInfoByRTN(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getBPWFIInfoByRTN start...", 6);
    try
    {
      return this.bt.getBPWFIInfoByRTN(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getBPWFIInfoByRTN failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHFIInfo addACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addACHFIInfo start...", 6);
    try
    {
      return this.bn.addACHFIInfo(paramACHFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addACHFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHFIInfo activateACHFI(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.activateACHFI start...", 6);
    ACHFIInfo localACHFIInfo = null;
    try
    {
      localACHFIInfo = this.bn.activateACHFI(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: activateACHFI failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    return localACHFIInfo;
  }
  
  public ACHFIInfo modACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modACHFIInfo start...", 6);
    try
    {
      return this.bn.modACHFIInfo(paramACHFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modACHFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHFIInfo canACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canACHFIInfo start...", 6);
    try
    {
      return this.bn.canACHFIInfo(paramACHFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canACHFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHFIInfo getACHFIInfo(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHFIInfo start...", 6);
    try
    {
      return this.bn.getACHFIInfo(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHFIInfo[] getFIInfoByRTN(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getFIInfoByRTN start...", 6);
    try
    {
      return ACHFIInfoProcessor.getFIInfoByRTN(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getFIInfoByRTN failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHCompanyInfo addACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addACHCompany start...", 6);
    try
    {
      paramACHCompanyInfo = this.aV.addACHCompany(paramACHCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      String str = "***BPWEngine: addACHCompany failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    finally {}
    return paramACHCompanyInfo;
  }
  
  public ACHCompanyInfo modACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modACHCompany start...", 6);
    try
    {
      paramACHCompanyInfo = this.aV.modACHCompany(paramACHCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      String str = "***BPWEngine: modACHCompany failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    finally {}
    return paramACHCompanyInfo;
  }
  
  public ACHCompanyInfo canACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canACHCompany start...", 6);
    try
    {
      paramACHCompanyInfo = this.aV.canACHCompany(paramACHCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      String str = "***BPWEngine: canACHCompany failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    finally {}
    return paramACHCompanyInfo;
  }
  
  public ACHCompanyInfo activateCompany(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.activateCompany start...", 6);
    ACHCompanyInfo localACHCompanyInfo = null;
    try
    {
      localACHCompanyInfo = this.aV.activateCompany(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: activateCompany failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    return localACHCompanyInfo;
  }
  
  public ACHCompanyInfo getACHCompany(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHCompany start...", 6);
    ACHCompanyInfo localACHCompanyInfo = null;
    try
    {
      localACHCompanyInfo = this.aV.getACHCompany(paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      String str = "***BPWEngine: getACHCompany failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    finally {}
    return localACHCompanyInfo;
  }
  
  public ACHCompanyList getACHCompanyList(ACHCompanyList paramACHCompanyList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHCompanyList start...", 6);
    ACHCompanyList localACHCompanyList = null;
    try
    {
      localACHCompanyList = this.aV.getACHCompanyList(paramACHCompanyList);
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      String str = "***BPWEngine: getACHCompanyList failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    finally {}
    return localACHCompanyList;
  }
  
  public ACHCompanySummaryList getACHCompanySummaries(ACHCompanySummaryList paramACHCompanySummaryList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHCompanySummaries start...", 6);
    ACHCompanySummaryList localACHCompanySummaryList = null;
    try
    {
      localACHCompanySummaryList = this.aV.getACHCompanySummaries(paramACHCompanySummaryList);
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      String str = "***BPWEngine: getACHCompanySummaries failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    finally {}
    return localACHCompanySummaryList;
  }
  
  public ACHPayeeInfo addACHPayee(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addACHPayee start...", 6);
    try
    {
      return this.aO.addACHPayee(paramACHPayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addACHPayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHPayeeInfo[] addACHPayee(ACHPayeeInfo[] paramArrayOfACHPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addACHPayee (Multiple) start...", 6);
    try
    {
      return this.aO.addACHPayee(paramArrayOfACHPayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addACHPayee (Multiple) failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHPayeeInfo modACHPayeeInfo(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modACHPayeeInfo start...", 6);
    try
    {
      return this.aO.modACHPayeeInfo(paramACHPayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modACHPayeeInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHPayeeInfo canACHPayeeInfo(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canACHPayeeInfo start...", 6);
    try
    {
      return this.aO.canACHPayeeInfo(paramACHPayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canACHPayeeInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHPayeeInfo activatePayee(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.activatePayee start...", 6);
    ACHPayeeInfo localACHPayeeInfo = null;
    try
    {
      localACHPayeeInfo = this.aO.activatePayee(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: activatePayee failed:" + FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    return localACHPayeeInfo;
  }
  
  public ACHPayeeInfo getACHPayeeInfo(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHPayeeInfo start...", 6);
    try
    {
      return this.aO.getACHPayeeInfo(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHPayeeInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHPayeeList getACHPayeeList(ACHPayeeList paramACHPayeeList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHPayeeList start...", 6);
    try
    {
      return this.aO.getACHPayeeList(paramACHPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHPayeeList failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHPayeeInfo updateACHPayeePrenoteStatus(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.updateACHPayeePrenoteStatus start...", 6);
    try
    {
      return this.aO.updateACHPayeePrenoteStatus(paramACHPayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: updateACHPayeePrenoteStatus failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchTemplateInfo addACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addACHBatchTemplate start...", 6);
    try
    {
      return this.bf.addACHBatchTemplate(paramACHBatchTemplateInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addACHBatchTemplate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchTemplateInfo modACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modACHBatchTemplate start...", 6);
    try
    {
      return this.bf.modACHBatchTemplate(paramACHBatchTemplateInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modACHBatchTemplate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchTemplateInfo deleteACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.deleteACHBatchTemplate start...", 6);
    try
    {
      return this.bf.deleteACHBatchTemplate(paramACHBatchTemplateInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: deleteACHBatchTemplate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchTemplateInfo getACHBatchTemplate(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHBatchTemplate start...", 6);
    try
    {
      return this.bf.getACHBatchTemplate(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHBatchTemplate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplate(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHBatchTemplate (Multiple) start...", 6);
    try
    {
      return this.bf.getACHBatchTemplate(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHBatchTemplate (Multiple) failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplateByCompany(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHBatchTemplateByCompany start...", 6);
    try
    {
      return this.bf.getACHBatchTemplateByCompany(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHBatchTemplateByCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplateByGroup(String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHBatchTemplateByGroup start...", 6);
    try
    {
      return this.bf.getACHBatchTemplateByGroup(paramString1, paramString2);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHBatchTemplateByGroup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getPmtHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getPmtHistory start...", 6);
    try
    {
      if (paramBPWHist.getVersion() == null)
      {
        str1 = "***BPWEngine: getPmtHistory failed: Version is missing in the BPWHist object";
        FFSDebug.log(str1);
        throw new FFSException(str1);
      }
      if (paramBPWHist.getVersion().endsWith("151")) {
        return this.a7.getPmtHistory(paramBPWHist);
      }
      if (paramBPWHist.getVersion().endsWith("200")) {
        return this.a7.getPmtHistory(paramBPWHist);
      }
      String str1 = "***BPWEngine: getPmtHistory failed: Unknown version in the BPWHist object";
      FFSDebug.log(str1);
      throw new FFSException(str1);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***BPWEngine: getPmtHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public ACHBatchInfo addACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addACHBatch start...", 6);
    try
    {
      return this.bu.addACHBatch(paramACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchInfo modACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modACHBatch start...", 6);
    try
    {
      return this.bu.modACHBatch(paramACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchInfo canACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canACHBatch start...", 6);
    try
    {
      return this.bu.canACHBatch(paramACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchInfo getACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHBatch start...", 6);
    try
    {
      return this.bu.getACHBatch(paramACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchHist getACHBatchHistory(ACHBatchHist paramACHBatchHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHBatchHistory start...", 6);
    try
    {
      return this.bu.getACHBatchHistory(paramACHBatchHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHBatchHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getIntraHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getIntraHistory start...", 6);
    try
    {
      if (paramBPWHist == null)
      {
        str1 = "***BPWEngine.getIntraHistory failed: Null histInfo object passed";
        FFSDebug.log(str1);
        throw new FFSException(str1);
      }
      if (paramBPWHist.getVersion() == null)
      {
        str1 = "***BPWEngine: getIntraHistory failed: Version is missing in the BPWIntraHist object";
        FFSDebug.log(str1);
        throw new FFSException(str1);
      }
      if (paramBPWHist.getVersion().endsWith("151")) {
        return this.bm.getIntraHistory(paramBPWHist);
      }
      if (paramBPWHist.getVersion().endsWith("200")) {
        return this.bm.getIntraHistory(paramBPWHist);
      }
      String str1 = "***BPWEngine: getIntraHistory failed: Unknown version in the BPWIntraHist object";
      FFSDebug.log(str1);
      throw new FFSException(str1);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***BPWEngine: getIntraHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public ACHFileInfo addACHFile(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addACHFile start...", 6);
    try
    {
      return this.bd.addACHFile(paramACHFileInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log("***BPWEngine.addACHFile failed: " + localFFSException.getMessage());
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addACHFile failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHFileInfo canACHFile(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canACHFile start...", 6);
    try
    {
      return this.bd.canACHFile(paramACHFileInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canACHFile failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHFileInfo getACHFile(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHFile start...", 6);
    try
    {
      return this.bd.getACHFile(paramACHFileInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHFile failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHFileHist getACHFileHistory(ACHFileHist paramACHFileHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHFileHistory start...", 6);
    try
    {
      return this.bd.getACHFileHistory(paramACHFileHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHFileHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecACHBatchInfo addRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addRecACHBatch start...", 6);
    try
    {
      return this.bu.addRecACHBatch(paramRecACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addRecACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecACHBatchInfo modRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modRecACHBatch start...", 6);
    try
    {
      return this.bu.modRecACHBatch(paramRecACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modRecACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecACHBatchInfo canRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canRecACHBatch start...", 6);
    try
    {
      return this.bu.canRecACHBatch(paramRecACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canRecACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecACHBatchInfo getRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecACHBatch start...", 6);
    try
    {
      return this.bu.getRecACHBatch(paramRecACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchHist getRecACHBatchHistory(ACHBatchHist paramACHBatchHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecACHBatchHistory start...", 6);
    try
    {
      return this.bu.getRecACHBatchHistory(paramACHBatchHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecACHBatchHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHCompOffsetAcctInfo addACHCompanyOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addACHCompanyOffsetAccount start...", 6);
    try
    {
      return this.bo.addACHCompOffsetAcct(paramACHCompOffsetAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addACHCompanyOffsetAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHCompOffsetAcctInfo modACHCompnayOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modACHCompnayOffsetAccount start...", 6);
    try
    {
      return this.bo.modACHCompOffsetAcct(paramACHCompOffsetAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modACHCompnayOffsetAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHCompOffsetAcctInfo canACHCompanyOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canACHCompanyOffsetAccount start...", 6);
    try
    {
      return this.bo.deleteACHCompOffsetAcct(paramACHCompOffsetAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canACHCompanyOffsetAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHCompOffsetAcctInfo getACHCompanyOffsetAccountByAccountId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHCompanyOffsetAccountByAccountId start...", 6);
    try
    {
      return this.bo.getACHCompOffsetAcctByAcctId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHCompanyOffsetAccountByAccountId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHCompOffsetAcctInfo[] getACHCompanyOffsetAccountByCompId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHCompanyOffsetAccountByCompId start...", 6);
    try
    {
      return this.bo.getACHCompOffsetAcctsByCompId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHCompanyOffsetAccountByCompId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public IntraTrnInfo getIntraById151(String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getIntraById151 : start", 6);
    try
    {
      return this.a3.getIntraById(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getIntraById151 : failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public IntraTrnInfo getIntraById200(String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getIntraById200 : start", 6);
    try
    {
      return this.bl.getIntraById(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getIntraById200 : failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public IntraTrnInfo[] getIntraById151(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getIntraById151 (multiple) : start", 6);
    try
    {
      return this.a3.getIntraById(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getIntraById151 (multiple) : failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public IntraTrnInfo[] getIntraByRecSrvrTId200(String[] paramArrayOfString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getIntraByRecSrvrTId200 (multiple) : start", 6);
    try
    {
      return this.bl.getIntraByRecSrvrTId(paramArrayOfString, paramBoolean);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getIntraById200 (multiple): failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public IntraTrnInfo[] getIntraById200(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getIntraById200 (multiple) : start", 6);
    try
    {
      return this.bl.getIntraById(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getIntraById200 (multiple): failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PmtInfo getPmtById151(String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getPmtById151 : start", 6);
    try
    {
      return this.bq.getPmtById(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getPmtById151 : failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PmtInfo getPmtById200(String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getPmtById200 : start", 6);
    try
    {
      return this.bc.getPmtById(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getPmtById200 : failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PmtInfo[] getPmtById151(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getPmtById151 (multiple) : start", 6);
    try
    {
      return this.bq.getPmtById(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getPmtById151 (multiple): failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PmtInfo[] getPmtById200(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getPmtById200 (multiple) : start", 6);
    try
    {
      return this.bc.getPmtById(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getPmtById200 (multiple): failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecPmtInfo[] getRecPmtById151(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getRecPmtById151 (multiple): start", 6);
    try
    {
      RecPmtInfo[] arrayOfRecPmtInfo = this.a1.getRecPmtById(paramArrayOfString);
      return arrayOfRecPmtInfo;
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getRecPmtById151 : failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecPmtInfo[] getRecPmtById200(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getRecPmtById200 (multiple): start", 6);
    try
    {
      RecPmtInfo[] arrayOfRecPmtInfo = this.bk.getRecPmtById(paramArrayOfString);
      return arrayOfRecPmtInfo;
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getRecPmtById200 : failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WirePayeeInfo addWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addWirePayee start...", 6);
    try
    {
      return this.aP.addWirePayee(paramWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WirePayeeInfo[] addWirePayee(WirePayeeInfo[] paramArrayOfWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addWirePayee (Multiple) start...", 6);
    try
    {
      return this.aP.addWirePayee(paramArrayOfWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addWirePayee (Multiple) failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WirePayeeInfo getWirePayee(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWirePayee start...", 6);
    try
    {
      return this.aP.getWirePayee(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WirePayeeInfo getWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWirePayee start...", 6);
    try
    {
      return this.aP.getWirePayee(paramWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WirePayeeInfo canWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canWirePayeeInfo start...", 6);
    try
    {
      return this.aP.canWirePayee(paramWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public WirePayeeInfo modWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canWirePayeeInfo start...", 6);
    try
    {
      return this.aP.modWirePayee(paramWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWPayeeList getWirePayeeInfoByType(BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWirePayeeInfoByType start...", 6);
    try
    {
      return this.aP.getWirePayeeInfoByType(paramBPWPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWirePayeeInfoByType failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWPayeeList getWirePayeeInfoByStatus(BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWirePayeeInfoByStatus start...", 6);
    try
    {
      return this.aP.getWirePayeeInfoByStatus(paramBPWPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWirePayeeInfoByStatus failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWPayeeList getWirePayeeInfoByGroup(BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWirePayeeInfoByGroup start...", 6);
    try
    {
      return this.aP.getWirePayeeInfoByGroup(paramBPWPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWirePayeeInfoByGroup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWPayeeList getWirePayeeInfoByCustomer(BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWirePayeeInfoByCustomer start...", 6);
    try
    {
      return this.aP.getWirePayeeInfoByCustomer(paramBPWPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWirePayeeInfoByCustomer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public int addIntermediaryBanksToBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addIntermediaryBanksToBeneficiary start...", 6);
    try
    {
      return this.aP.addIntermediaryBanksToBeneficiary(paramString, paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addIntermediaryBanksToBeneficiary failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public int delIntermediaryBanksFromBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.delIntermediaryBanksFromBeneficiary start...", 6);
    try
    {
      return this.aP.delIntermediaryBanksFromBeneficiary(paramString, paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: delIntermediaryBanksFromBeneficiary failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWBankInfo[] addWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addWireBanks start...", 6);
    try
    {
      return this.aR.addWireBanks(paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addWireBanks failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWBankInfo[] modWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modWireBanks start...", 6);
    try
    {
      return this.aR.modWireBanks(paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modWireBanks failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWBankInfo[] delWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.delWireBanks start...", 6);
    try
    {
      return this.aR.delWireBanks(paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: delWireBanks failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWBankInfo[] getWireBanks(String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireBanks start...", 6);
    try
    {
      return this.aR.getWireBanks(paramString1, paramString2, paramString3, paramString4);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireBanks failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWBankInfo[] getWireBanksByRTN(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireBanksByRTN start...", 6);
    try
    {
      return this.aR.getWireBanksByRTN(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireBanksByRTN failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWBankInfo getWireBanksByID(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getWireBanksByID start...", 6);
    try
    {
      return this.aR.getWireBanksByID(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getWireBanksByID failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RPPSFIInfo addRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addRPPSFIInfo start...", 6);
    try
    {
      return this.aY.addRPPSFIInfo(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: addRPPSFIInfo failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo getRPPSFIInfoByFIId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRPPSFIInfoByFIId start...", 6);
    try
    {
      return this.aY.getRPPSFIInfoByFIId(paramString);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getRPPSFIInfoByFIId failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo getRPPSFIInfoByFIRPPSId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRPPSFIInfoByFIRPPSId start...", 6);
    try
    {
      return this.aY.getRPPSFIInfoByFIRPPSId(paramString);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getRPPSFIInfoByFIId failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo canRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canRPPSFIInfo start...", 6);
    try
    {
      return this.aY.canRPPSFIInfo(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: canRPPSFIInfo failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo activateRPPSFI(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.activateRPPSFI start...", 6);
    try
    {
      return this.aY.activateRPPSFI(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: activateRPPSFI failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo modRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modRPPSFIInfo start...", 6);
    try
    {
      return this.aY.modRPPSFIInfo(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: modRPPSFIInfo failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSBillerInfo[] getRPPSBillerInfoByFIRPPSId(String paramString)
    throws FFSException
  {
    String str1 = "BPWEngine.getRPPSBillerInfoByFIRPPSId: ";
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      return this.aY.getRPPSBillerInfoByFIRPPSId(paramString);
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(localException, str2);
    }
  }
  
  public RPPSBillerInfo getRPPSBillerInfoByFIAndBillerRPPSId(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "BPWEngine.getRPPSBillerInfoByFIAndBillerRPPSId: ";
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      return this.aY.getRPPSBillerInfoByFIAndBillerRPPSId(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(localException, str2);
    }
  }
  
  public RecIntraTrnInfo getRecIntraById200(String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWEngine.getRecIntraById200 : start", 6);
    try
    {
      RecIntraTrnInfo localRecIntraTrnInfo = this.bi.getRecIntraById(paramString);
      int i = localRecIntraTrnInfo.freq;
      int j = CommonProcessor.mapBPWFreqToOFX200Freq(i);
      localRecIntraTrnInfo.freq = j;
      return localRecIntraTrnInfo;
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine.getRecIntraById200 : failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecIntraTrnInfo[] getRecIntraById200(String[] paramArrayOfString)
    throws FFSException
  {
    String str1 = "BPWEngine.getRecIntraById200 (multiple): ";
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      RecIntraTrnInfo[] arrayOfRecIntraTrnInfo = this.bi.getRecIntraById(paramArrayOfString);
      for (int i = 0; i < arrayOfRecIntraTrnInfo.length; i++) {
        if (arrayOfRecIntraTrnInfo[i].statusCode != 16504)
        {
          int j = arrayOfRecIntraTrnInfo[i].freq;
          int k = CommonProcessor.mapBPWFreqToOFX200Freq(j);
          arrayOfRecIntraTrnInfo[i].freq = k;
        }
      }
      return arrayOfRecIntraTrnInfo;
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(localException, str2);
    }
  }
  
  public void processApprovalResult(String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "BPWEngine.processApprovalResult: ";
    if (paramString1 == null) {
      throw new FFSException(str1 + "transactionType can not be null!");
    }
    String str2;
    if (paramString1.compareTo("PMTTRN") == 0)
    {
      str2 = getOFXVersion(paramString2);
      if ("OFX151".equalsIgnoreCase(str2)) {
        this.a1.processApprovalResult(paramString2, paramString3, paramHashMap);
      } else {
        RecPmtProcessor2.processApprovalResult(paramString2, paramString3, paramHashMap);
      }
    }
    else if (paramString1.compareTo("INTRATRN") == 0)
    {
      str2 = getOFXVersion(paramString2);
      if ("OFX151".equalsIgnoreCase(str2)) {
        this.a8.processApprovalResult(paramString2, paramString3, paramHashMap);
      } else {
        RecXferProcessor2.processApprovalResult(paramString2, paramString3, paramHashMap);
      }
    }
    else if (paramString1.compareTo("WIRETRN") == 0)
    {
      this.aX.processApprovalResult(paramString2, paramString3, paramHashMap);
    }
    else if (paramString1.compareTo("ACHBATCHTRN") == 0)
    {
      this.bu.processApprovalResult(paramString2, paramString3, paramHashMap);
    }
    else if (paramString1.compareTo("ETFTRN") == 0)
    {
      this.bj.processApprovalResult(paramString2, paramString3, paramHashMap);
    }
    else if (paramString1.compareTo("CASHCONTRN") == 0)
    {
      this.bs.processApprovalResult(paramString2, paramString3, paramHashMap);
    }
    else
    {
      throw new FFSException(str1 + "Invalid transactionType = " + paramString1);
    }
  }
  
  public static String getOFXVersion(String paramString)
    throws FFSException
  {
    String str1 = "OFX200";
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new FFSException("BPWEngine.getOFXVersion: Can not get DB Connection.");
    }
    try
    {
      String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString, localFFSConnectionHolder);
      if (arrayOfString[0] == null)
      {
        str2 = "BPWEngine.getOFXVersion: Failed: could not find the SrvrTID: " + paramString + " in BPW_SrvrTrans";
        FFSDebug.log(str2, 6);
        throw new FFSException(str2);
      }
      if (arrayOfString[0].startsWith("OFX151")) {
        str1 = "OFX151";
      } else {
        str1 = "OFX200";
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "BPWEngine.getOFXVersion failed:  Error: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return str1;
  }
  
  public CCCompanyInfo addCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addCCCompany start...", 6);
    try
    {
      return this.bs.addCCCompany(paramCCCompanyInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: addCCCompany failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyInfo cancelCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancelCCCompany start...", 6);
    try
    {
      return this.bs.cancelCCCompany(paramCCCompanyInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: cancelCCCompany failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyInfo modCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modCCCompany start...", 6);
    try
    {
      return this.bs.modCCCompany(paramCCCompanyInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: modCCCompany failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyInfo getCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCCompany start...", 6);
    try
    {
      return this.bs.getCCCompany(paramCCCompanyInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCCompany failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyInfoList getCCCompanyList(CCCompanyInfoList paramCCCompanyInfoList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCCompanyList start...", 6);
    try
    {
      return this.bs.getCCCompanyList(paramCCCompanyInfoList);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCCompanyList failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CutOffInfo getNextCashConCutOff(String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getNextCashConCutOff start...", 6);
    try
    {
      return this.bs.getNextCashConCutOff(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getNextCashConCutOff failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyAcctInfo addCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addCCCompanyAcct start...", 6);
    try
    {
      return this.bs.addCCCompanyAcct(paramCCCompanyAcctInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: addCCCompanyAcct failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyAcctInfo cancelCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancelCCCompanyAcct start...", 6);
    try
    {
      return this.bs.cancelCCCompanyAcct(paramCCCompanyAcctInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: cancelCCCompanyAcct failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyAcctInfo modCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modCCCompanyAcct start...", 6);
    try
    {
      return this.bs.modCCCompanyAcct(paramCCCompanyAcctInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: modCCCompanyAcct failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyAcctInfo getCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCCompanyAcct start...", 6);
    try
    {
      return this.bs.getCCCompanyAcct(paramCCCompanyAcctInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCCompanyAcct failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyAcctInfoList getCCCompanyAcctList(CCCompanyAcctInfoList paramCCCompanyAcctInfoList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCCompanyAcctList start...", 6);
    try
    {
      return this.bs.getCCCompanyAcctList(paramCCCompanyAcctInfoList);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCCompanyAcctList failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyCutOffInfo addCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addCCCompanyCutOff start...", 6);
    try
    {
      return this.bs.addCCCompanyCutOff(paramCCCompanyCutOffInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: addCCCompanyCutOff failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyCutOffInfo cancelCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancelCCCompanyCutOff start...", 6);
    try
    {
      return this.bs.cancelCCCompanyCutOff(paramCCCompanyCutOffInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: cancelCCCompanyCutOff failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyCutOffInfo getCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCCompanyCutOff start...", 6);
    try
    {
      return this.bs.getCCCompanyCutOff(paramCCCompanyCutOffInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCCompanyCutOff failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCCompanyCutOffInfoList getCCCompanyCutOffList(CCCompanyCutOffInfoList paramCCCompanyCutOffInfoList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCCompanyCutOffList start...", 6);
    try
    {
      return this.bs.getCCCompanyCutOffList(paramCCCompanyCutOffInfoList);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCCompanyCutOffList failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCLocationInfo addCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addCCLocation start...", 6);
    try
    {
      return this.bs.addCCLocation(paramCCLocationInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: addCCLocation failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCLocationInfo cancelCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancelCCLocation start...", 6);
    try
    {
      return this.bs.cancelCCLocation(paramCCLocationInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: cancelCCLocation failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCLocationInfo modCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modCCLocation start...", 6);
    try
    {
      return this.bs.modCCLocation(paramCCLocationInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: modCCLocation failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCLocationInfo getCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCLocation start...", 6);
    try
    {
      return this.bs.getCCLocation(paramCCLocationInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCLocation failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCLocationInfoList getCCLocationList(CCLocationInfoList paramCCLocationInfoList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCLocationList start...", 6);
    try
    {
      return this.bs.getCCLocationList(paramCCLocationInfoList);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCLocationList failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCEntryInfo addCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addCCEntry start...", 6);
    try
    {
      return this.bs.addCCEntry(paramCCEntryInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: addCCEntry failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCEntryInfo cancelCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancelCCEntry start...", 6);
    try
    {
      return this.bs.cancelCCEntry(paramCCEntryInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: cancelCCEntry failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCEntryInfo modCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modCCEntry start...", 6);
    try
    {
      return this.bs.modCCEntry(paramCCEntryInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: modCCEntry failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCEntryInfo getCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCEntry start...", 6);
    try
    {
      return this.bs.getCCEntry(paramCCEntryInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCEntry failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCEntryHistInfo getCCEntryHist(CCEntryHistInfo paramCCEntryHistInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCEntryHist start...", 6);
    try
    {
      return this.bs.getCCEntryHist(paramCCEntryHistInfo);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCEntryHist failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public CCEntrySummaryInfoList getCCEntrySummaryList(CCEntrySummaryInfoList paramCCEntrySummaryInfoList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getCCEntrySummaryList start...", 6);
    try
    {
      return this.bs.getCCEntrySummaryList(paramCCEntrySummaryInfoList);
    }
    catch (Exception localException)
    {
      String str = "***BPWEngine: getCCEntrySummaryList failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public TransferInfo addTransfer(TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addTransfer start...", 6);
    try
    {
      return this.bj.addTransfer(paramTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo modTransfer(TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modTransfer start...", 6);
    try
    {
      return this.bj.modTransfer(paramTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo cancTransfer(TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancTransfer start...", 6);
    try
    {
      return this.bj.cancTransfer(paramTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: cancTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo getTransferBySrvrTId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getTransferBySrvrTId start...", 6);
    try
    {
      return this.bj.getTransferBySrvrTId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getTransferBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo getTransferByTrackingId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getTransfer start...", 6);
    try
    {
      return this.bj.getTransferByTrackingId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo[] getTransfersByRecSrvrTId(String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getTransfersByRecSrvrTId start...", 6);
    try
    {
      return this.bj.getTransfersByRecSrvrTId(paramString, paramBoolean);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getTransfersByRecSrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo[] getTransfersByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getTransfersByRecSrvrTId start...", 6);
    try
    {
      return this.bj.getTransfersByRecSrvrTId(paramArrayOfString, paramBoolean);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getTransfersByRecSrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo[] getTransfersBySrvrTId(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getTransfersBySrvrTId start...", 6);
    try
    {
      return this.bj.getTransfersBySrvrTId(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getTransfersBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo[] getTransfersByTrackingId(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getTransfersByTrackingId start...", 6);
    try
    {
      return this.bj.getTransfersByTrackingId(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getTransfersByTrackingId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getTransferHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getTransferHistory start...", 6);
    try
    {
      return this.bj.getTransferHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getTransferHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getRecTransfers(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getTransferHistory start...", 6);
    try
    {
      return this.bj.getRecTransfers(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getTransferHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferInfo[] addTransfers(TransferInfo[] paramArrayOfTransferInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addTransfers start...", 6);
    try
    {
      return this.bj.addTransfers(paramArrayOfTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecTransferInfo addRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addRecTransfer start...", 6);
    try
    {
      return this.bj.addRecTransfer(paramRecTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addRecTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecTransferInfo modRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modRecTransfer start...", 6);
    try
    {
      return this.bj.modRecTransfer(paramRecTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modRecTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecTransferInfo cancRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cancRecTransfer start...", 6);
    try
    {
      return this.bj.cancRecTransfer(paramRecTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: cancRecTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecTransferInfo getRecTransferBySrvrTId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecTransferBySrvrId start...", 6);
    try
    {
      return this.bj.getRecTransferBySrvrTId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecTransferBySrvrId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecTransferInfo getRecTransferByTrackingId(String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecTransfer start...", 6);
    try
    {
      return this.bj.getRecTransferByTrackingId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecTransferInfo[] getRecTransfersBySrvrTId(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecTransfersBySrvrTId start...", 6);
    try
    {
      return this.bj.getRecTransfersBySrvrTId(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecTransfersBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public RecTransferInfo[] getRecTransfersByTrackingId(String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecTransfersByTrackingId start...", 6);
    try
    {
      return this.bj.getRecTransfersByTrackingId(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecTransfersByTrackingId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWHist getRecTransferHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getRecTransferHistory start...", 6);
    try
    {
      return this.bj.getRecTransferHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getRecTransferHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferCompanyInfo addExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addExtTransferCompany start...", 6);
    try
    {
      return this.bx.addExtTransferCompany(paramExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addExtTransferCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferCompanyInfo canExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canExtTransferCompany start...", 6);
    try
    {
      return this.bx.canExtTransferCompany(paramExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canExtTransferCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferCompanyInfo modExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modExtTransferCompany start...", 6);
    try
    {
      return this.bx.modExtTransferCompany(paramExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modExtTransferCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferCompanyInfo getExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getExtTransferCompany start...", 6);
    try
    {
      return this.bx.getExtTransferCompany(paramExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getExtTransferCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferCompanyList getExtTransferCompanyList(ExtTransferCompanyList paramExtTransferCompanyList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getExtTransferCompanyList start...", 6);
    try
    {
      return this.bx.getExtTransferCompanyList(paramExtTransferCompanyList);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getExtTransferCompanyList failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo addExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addExtTransferAccount start...", 6);
    try
    {
      return this.a9.addExtTransferAccount(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: addExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo canExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.canExtTransferAccount start...", 6);
    try
    {
      return this.a9.canExtTransferAccount(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: canExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo modExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modExtTransferAccount start...", 6);
    try
    {
      return this.a9.modExtTransferAccount(paramExtTransferAcctInfo, false);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo modExtTransferAccountPrenoteStatus(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.modExtTransferAccount start...", 6);
    try
    {
      return this.a9.modExtTransferAccount(paramExtTransferAcctInfo, true);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: modExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo getExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getExtTransferAccount start...", 6);
    try
    {
      return this.a9.getExtTransferAccount(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo verifyExtTransferAccount(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo, int[] paramArrayOfInt)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.verifyExtTransferAccount start...", 6);
    try
    {
      return this.a9.verifyExtTransferAccount(paramString, paramExtTransferAcctInfo, paramArrayOfInt);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: verifyExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo depositAmountsForVerify(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.depositAmountsForVerify start...", 6);
    try
    {
      return this.a9.depositAmountsForVerify(paramString, paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: depositAmountsForVerify failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo activateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.activateExtTransferAcct start...", 6);
    try
    {
      return this.a9.activateExtTransferAcct(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: activateExtTransferAcct failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctInfo inactivateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.inactivateExtTransferAcct start...", 6);
    try
    {
      return this.a9.inactivateExtTransferAcct(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: inactivateExtTransferAcct failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ExtTransferAcctList getExtTransferAcctList(ExtTransferAcctList paramExtTransferAcctList)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getExtTransferAcctList start...", 6);
    try
    {
      return this.a9.getExtTransferAcctList(paramExtTransferAcctList);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getExtTransferAcctList failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public NonBusinessDay[] getNonBusinessDays(String paramString)
    throws FFSException
  {
    String str = "BPWEngine.getNonBusinessDays: ";
    try
    {
      SmartDate[] arrayOfSmartDate = SmartCalendar.getNonBusinessDays(paramString);
      if (arrayOfSmartDate == null) {
        return null;
      }
      localObject = new NonBusinessDay[arrayOfSmartDate.length];
      for (int i = 0; i < arrayOfSmartDate.length; i++) {
        localObject[i] = new NonBusinessDay(arrayOfSmartDate[i]._thisDay, arrayOfSmartDate[i]._payDay);
      }
      return localObject;
    }
    catch (Exception localException)
    {
      Object localObject = str + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject);
      throw new FFSException(localException, (String)localObject);
    }
  }
  
  public NonBusinessDay[] getNonBusinessDaysFromFile(String paramString)
    throws FFSException
  {
    String str = "BPWEngine.getNonBusinessDaysFromFile: ";
    try
    {
      SmartDate[] arrayOfSmartDate = SmartCalendar.getNonBusinessDaysFromFile(paramString);
      if (arrayOfSmartDate == null) {
        return null;
      }
      localObject = new NonBusinessDay[arrayOfSmartDate.length];
      for (int i = 0; i < arrayOfSmartDate.length; i++) {
        localObject[i] = new NonBusinessDay(arrayOfSmartDate[i]._thisDay, arrayOfSmartDate[i]._payDay);
      }
      return localObject;
    }
    catch (Exception localException)
    {
      Object localObject = str + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject);
      throw new FFSException(localException, (String)localObject);
    }
  }
  
  public void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cleanup start...", 6);
    try
    {
      this.a5.cleanup(paramString1, paramString2, paramInt, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: cleanup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public void cleanup(String paramString, ArrayList paramArrayList1, ArrayList paramArrayList2, HashMap paramHashMap)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cleanup start...", 6);
    try
    {
      this.a5.cleanup(paramString, paramArrayList1, paramArrayList2, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: cleanup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public void cleanup(ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, HashMap paramHashMap)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.cleanup start...", 6);
    try
    {
      this.a5.cleanup(paramArrayList1, paramArrayList2, paramArrayList3, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: cleanup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHBatchInfo exportACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.exportACHBatch start...", 6);
    try
    {
      return this.bu.exportACHBatch(paramACHBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: exportACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public void processWireApprovalRslt(WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processWireApprovalRslt: starts", 6);
    this.a2.processWireApprovalRslt(paramArrayOfWireInfo);
    FFSDebug.log("BPWEngine.processWireApprovalRslt: ends", 6);
  }
  
  public void processWireBackendlRslt(WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processWireBackendlRslt: starts", 6);
    this.a2.processWireBackendlRslt(paramArrayOfWireInfo);
    FFSDebug.log("BPWEngine.processWireBackendlRslt: ends", 6);
  }
  
  public void processWireApprovalRevertRslt(WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("BPWEngine.processWireApprovalRevertRslt: starts", 6);
    this.a2.processWireApprovalRevertRslt(paramArrayOfWireInfo);
    FFSDebug.log("BPWEngine.processWireApprovalRevertRslt: ends", 6);
  }
  
  public ACHSameDayEffDateInfo getACHSameDayEffDateInfo(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHSameDayEffDateInfo start...", 6);
    try
    {
      return this.bw.getACHSameDayEffDateInfo(paramACHSameDayEffDateInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHSameDayEffDateInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHSameDayEffDateInfo setACHSameDayEffDateInfo(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.setACHSameDayEffDateInfo start...", 6);
    try
    {
      return this.bw.setACHSameDayEffDateInfo(paramACHSameDayEffDateInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: setACHSameDayEffDateInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public String getDefaultACHBatchEffectiveDate(String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getDefaultACHBatchEffectiveDate start...", 6);
    try
    {
      return this.bu.getDefaultACHBatchEffectiveDate(paramString1, paramString2);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getDefaultACHBatchEffectiveDate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PagingInfo getPagedACH(PagingInfo paramPagingInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getPagedACH start...", 6);
    try
    {
      return this.bu.getPagedACH(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getPagedACH failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PagingInfo getPagedWire(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "BPWEngine.getpagedWire";
    FFSDebug.log(str1 + " start...", 6);
    try
    {
      return this.aX.getPagedWire(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***" + str1 + " failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public PagingInfo getPagedTransfer(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "BPWEngine.getPagedTransfer";
    FFSDebug.log(str1 + " start...", 6);
    try
    {
      return this.bj.getPagedTransfer(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***" + str1 + " failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public PagingInfo getPagedBillPayments(PagingInfo paramPagingInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getPagedBillPayments start...", 6);
    PagingInfo localPagingInfo = null;
    try
    {
      localPagingInfo = this.a7.getPagedBillPayments(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "BPWEngine: getPagedBillPayments failed";
      FFSDebug.log(localThrowable, str);
      throw new FFSException(localThrowable, str);
    }
    FFSDebug.log("BPWEngine.getPagedBillPayments end.", 6);
    return localPagingInfo;
  }
  
  public int getValidTransferDateDue(TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getValidTransferDateDue start...", 6);
    try
    {
      return this.bj.getValidTransferDueDate(paramTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getValidTransferDateDue failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PagingInfo getPagedCashCon(PagingInfo paramPagingInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getPagedCashCon start...", 6);
    try
    {
      return this.bs.getPagedCashCon(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getPagedCashCon failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("BPWEngine.getPayeeByListId start...", 6);
    PayeeInfo localPayeeInfo = null;
    try
    {
      BackendProcessor localBackendProcessor = new BackendProcessor();
      localPayeeInfo = localBackendProcessor.getPayeeByListId(paramString1, paramString2);
    }
    catch (Throwable localThrowable)
    {
      throw new Exception(localThrowable);
    }
    FFSDebug.log("BPWEngine.getPayeeByListId end...", 6);
    return localPayeeInfo;
  }
  
  public BPWExtdHist getACHTotals(BPWExtdHist paramBPWExtdHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHTotals start...", 6);
    try
    {
      return this.be.getACHTotals(paramBPWExtdHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHTotals failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWExtdHist getACHParticipantTotals(BPWExtdHist paramBPWExtdHist)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.getACHParticipantTotals start...", 6);
    try
    {
      return this.be.getACHParticipantTotals(paramBPWExtdHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "***BPWEngine: getACHParticipantTotals failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public TransferBatchInfo addTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.addTransferBatch start...", 6);
      return this.bj.addTransferBatch(paramTransferBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addTransferBatch:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public TransferBatchInfo modifyTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.modifyTransferBatch start...", 6);
      return this.bj.modifyTransferBatch(paramTransferBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modifyTransferBatch:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public TransferBatchInfo cancelTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.cancelTransferBatch start...", 6);
      return this.bj.cancelTransferBatch(paramTransferBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** cancelTransferBatch:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public TransferBatchInfo getTransferBatchById(String paramString)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.getTransferBatchById start...", 6);
      return this.bj.getTransferBatchById(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransferBatchById:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public AccountTransactions accountHasPendingTransfers(AccountTransactions paramAccountTransactions)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.getTransferBatchById start...", 6);
      return this.bj.accountHasPendingTransfers(paramAccountTransactions);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransferBatchById:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PmtInfo addBillPayment(PmtInfo paramPmtInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.addBillPayment start...", 6);
      return this.aW.addBillPayment(paramPmtInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addBillPayment:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PmtInfo modifyBillPayment(PmtInfo paramPmtInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.modifyBillPayment start...", 6);
      return this.aW.modifyBillPayment(paramPmtInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modifyBillPayment:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PmtInfo deleteBillPayment(PmtInfo paramPmtInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.deleteBillPayment start...", 6);
      return this.aW.deleteBillPayment(paramPmtInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** deleteBillPayment:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PmtInfo getBillPaymentById(String paramString1, String paramString2)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.getBillPaymentById start...", 6);
      return this.aW.getBillPaymentById(paramString1, paramString2);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBillPaymentById:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PaymentBatchInfo addPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws FFSException
  {
    try
    {
      if ((this.bp) && (!Customer.validCustomer(paramPaymentBatchInfo.getCustomerId())))
      {
        String str1 = "This customer, customerID=" + paramPaymentBatchInfo.getCustomerId() + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
        FFSDebug.log(str1, 0);
        throw new Exception(str1);
      }
      FFSDebug.log("BPWEngine.addPaymentBatch start...", 6);
      return this.aW.addPaymentBatch(paramPaymentBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** addPaymentBatch:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
  }
  
  public PaymentBatchInfo modifyPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.modifyPaymentBatch start...", 6);
      return this.aW.modifyPaymentBatch(paramPaymentBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modifyPaymentBatch:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PaymentBatchInfo deletePaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.deletePaymentBatch start...", 6);
      return this.aW.deletePaymentBatch(paramPaymentBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** deletePaymentBatch:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PaymentBatchInfo getPaymentBatchById(String paramString)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.getPaymentBatchById start...", 6);
      return this.aW.getPaymentBatchById(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getPaymentBatchById:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public LastPaymentInfo getLastPayments(LastPaymentInfo paramLastPaymentInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("BPWEngine.getLastPayment start...", 6);
      return this.aW.getLastPayments(paramLastPaymentInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getLastPayment:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "BPWEngine.getBankingDaysInRange: ";
    BankingDays localBankingDays = null;
    String str2 = paramBankingDays.getTransType();
    if (str2 == null) {
      throw new FFSException(str1 + "transaction type can not be null!");
    }
    if (str2.compareTo("PMTTRN") == 0) {
      localBankingDays = this.bk.getBankingDaysInRange(paramBankingDays, paramHashMap);
    } else if (str2.compareTo("INTRATRN") == 0) {
      localBankingDays = this.bi.getBankingDaysInRange(paramBankingDays, paramHashMap);
    } else if (str2.compareTo("WIRETRN") == 0) {
      localBankingDays = this.aX.getBankingDaysInRange(paramBankingDays, paramHashMap);
    } else if (str2.compareTo("ACHBATCHTRN") == 0) {
      localBankingDays = this.bu.getBankingDaysInRange(paramBankingDays, paramHashMap);
    } else if (str2.compareTo("ETFTRN") == 0) {
      localBankingDays = this.bj.getBankingDaysInRange(paramBankingDays, paramHashMap);
    } else if (str2.compareTo("CASHCONTRN") == 0) {
      localBankingDays = this.bs.getBankingDaysInRange(paramBankingDays, paramHashMap);
    } else {
      throw new FFSException(str1 + "Invalid transaction type = " + str2);
    }
    return localBankingDays;
  }
  
  public PayeeInfo addGlobalPayee(PayeeInfo paramPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.addGlobalPayee: ", ": Start ", 6);
    paramPayeeInfo = this.bg.addGlobalPayee(paramPayeeInfo);
    FFSDebug.log("BPWEngine.addGlobalPayee: ", ": End ", 6);
    return paramPayeeInfo;
  }
  
  public PayeeInfo deleteGlobalPayee(PayeeInfo paramPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("BPWEngine.deleteGlobalPayee: ", ": Start ", 6);
    paramPayeeInfo = this.bg.deleteGlobalPayee(paramPayeeInfo);
    FFSDebug.log("BPWEngine.deleteGlobalPayee: ", ": End ", 6);
    return paramPayeeInfo;
  }
  
  public CustomerPayeeInfo addCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException
  {
    CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
    FFSDebug.log("BPWEngine.addCustomerPayee: call", 6);
    localCustomerPayeeInfo = this.bg.addCustomerPayee(paramCustomerPayeeInfo);
    FFSDebug.log("BPWEngine.addCustomerPayee: end", 6);
    return localCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo deleteCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException
  {
    CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
    FFSDebug.log("BPWEngine.deleteCustomerPayee: call", 6);
    localCustomerPayeeInfo = this.bg.deleteCustomerPayee(paramCustomerPayeeInfo);
    FFSDebug.log("BPWEngine.deleteCustomerPayee: end", 6);
    return localCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo updateCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException
  {
    CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
    FFSDebug.log("BPWEngine.deleteCustomerPayee: call", 6);
    localCustomerPayeeInfo = this.bg.updateCustomerPayee(paramCustomerPayeeInfo);
    FFSDebug.log("BPWEngine.deleteCustomerPayee: end", 6);
    return localCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo[] getCustomerPayees(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException
  {
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = null;
    FFSDebug.log("BPWEngine.deleteCustomerPayee: call", 6);
    arrayOfCustomerPayeeInfo = this.bg.getCustomerPayees(paramCustomerPayeeInfo);
    FFSDebug.log("BPWEngine.deleteCustomerPayee: end", 6);
    return arrayOfCustomerPayeeInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.BPWEngine
 * JD-Core Version:    0.7.0.1
 */