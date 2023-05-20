package com.ffusion.ffs.bpw.BPWServices;

import com.ffusion.ffs.bpw.BPWServer;
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
import com.ffusion.ffs.bpw.master.BPWEngine;
import com.ffusion.ffs.bpw.util.AccountTypesMap;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class BPWServicesBean
  implements SessionBean
{
  SessionContext _context;
  
  public void ejbCreate()
    throws CreateException, RemoteException
  {}
  
  public void setSessionContext(SessionContext paramSessionContext)
    throws RemoteException
  {
    this._context = paramSessionContext;
  }
  
  public void ejbRemove()
    throws RemoteException
  {}
  
  public void ejbPassivate()
    throws RemoteException
  {}
  
  public void ejbActivate()
    throws RemoteException
  {}
  
  public void disconnect()
    throws RemoteException
  {}
  
  public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    int i = 0;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      i = localBPWEngine.addCustomers(paramArrayOfCustomerInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** addCustomers failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return i;
  }
  
  public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    int i = 0;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      i = localBPWEngine.modifyCustomers(paramArrayOfCustomerInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** modifyCustomers failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return i;
  }
  
  public int deleteCustomers(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    int i = 0;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      i = localBPWEngine.deleteCustomers(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** deleteCustomers failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return i;
  }
  
  public int deactivateCustomers(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    int i = 0;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      i = localBPWEngine.deactivateCustomers(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** deactivateCustomers failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return i;
  }
  
  public int activateCustomers(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    int i = 0;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      i = localBPWEngine.activateCustomers(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** activateCustomers failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return i;
  }
  
  public int deleteCustomers(String[] paramArrayOfString, int paramInt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    int i = 0;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      i = localBPWEngine.deleteCustomers(paramArrayOfString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** deleteCustomers failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return i;
  }
  
  public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerInfo[] arrayOfCustomerInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerInfo = localBPWEngine.getCustomersInfo(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomersInfo failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByType(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerInfo[] arrayOfCustomerInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerInfo = localBPWEngine.getCustomerByType(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerByType failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByFI(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerInfo[] arrayOfCustomerInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerInfo = localBPWEngine.getCustomerByFI(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerByFI failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByCategory(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerInfo[] arrayOfCustomerInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerInfo = localBPWEngine.getCustomerByCategory(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerByCategory failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByGroup(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerInfo[] arrayOfCustomerInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerInfo = localBPWEngine.getCustomerByGroup(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerByGroup failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerInfo[] arrayOfCustomerInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerInfo = localBPWEngine.getCustomerByTypeAndFI(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerByTypeAndFI failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerInfo[] arrayOfCustomerInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerInfo = localBPWEngine.getCustomerByCategoryAndFI(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerByCategoryAndFI failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerInfo[] arrayOfCustomerInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerInfo = localBPWEngine.getCustomerByGroupAndFI(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerByGroupAndFI failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfCustomerInfo;
  }
  
  public RPPSBillerInfo[] getRPPSBillerInfoByFIRPPSId(String paramString)
    throws FFSException
  {
    String str1 = "BPWServicesBean.getRPPSBillerInfoByFIRPPSId: ";
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRPPSBillerInfoByFIRPPSId(paramString);
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
    String str1 = "BPWServicesBean.getRPPSBillerInfoByFIAndBillerRPPSId: ";
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRPPSBillerInfoByFIAndBillerRPPSId(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(localException, str2);
    }
  }
  
  public WireInfo addWireTransfer(WireInfo paramWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addWireTransfer(paramWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo modWireTransfer(WireInfo paramWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modWireTransfer(paramWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo cancWireTransfer(WireInfo paramWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancWireTransfer(paramWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** cancWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo getWireTransferById(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireTransferById(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireTransferById failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo getWireTransfer(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireTransfer(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo[] getWireTransfers(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireTransfers(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getDuplicateWires(WireInfo paramWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getDuplicateWires(paramWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getDuplicateWires failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo[] getBatchWires(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBatchWires(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBatchWires failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getWireHistory(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getWireHistoryByCustomer(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireHistoryByCustomer(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireHistoryByCustomer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getCombinedWireHistory(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCombinedWireHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getCombinedWireHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo[] getAuditWireTransfer(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getAuditWireTransfer(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getAuditWireTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo[] getAuditWireTransferByExtId(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getAuditWireTransferByExtId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getAuditWireTransferByExtId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireReleaseInfo getWireReleaseCount(WireReleaseInfo paramWireReleaseInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireReleaseCount(paramWireReleaseInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireReleaseCount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo[] addWireTransfers(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addWireTransfers(paramArrayOfWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addWireTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireInfo[] releaseWireTransfer(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.releaseWireTransfer(paramArrayOfWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** releaseWireTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecWireInfo addRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addRecWireTransfer(paramRecWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addRecWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecWireInfo modRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modRecWireTransfer(paramRecWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modRecWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecWireInfo cancRecWireTransfer(RecWireInfo paramRecWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancRecWireTransfer(paramRecWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** cancRecWireTrn failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecWireInfo getRecWireTransferById(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecWireTransferById(paramString, true);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecWireTransferById failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecWireInfo getRecWireTransferById(String paramString, boolean paramBoolean)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecWireTransferById(paramString, paramBoolean);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecWireTransferById failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecWireInfo getRecWireTransfer(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecWireTransfer(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecWireTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecWireInfo[] getRecWireTransfers(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecWireTransfers(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecWireTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getRecWireHistory(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecWireHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecWireHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getRecWireHistoryByCustomer(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecWireHistoryByCustomer(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecWireHistoryByCustomer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecWireInfo[] addRecWireTransfers(RecWireInfo[] paramArrayOfRecWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addRecWireTransfers(paramArrayOfRecWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addRecWireTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public HashMap getWiresConfiguration()
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWiresConfiguration();
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWiresConfiguration failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireBatchInfo addWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addWireTransferBatch(paramWireBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addWireTransferBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireBatchInfo modWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modWireTransferBatch(paramWireBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modWireTransferBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireBatchInfo canWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canWireTransferBatch(paramWireBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canWireTransferBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WireBatchInfo[] getWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireTransferBatch(paramWireBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireTransferBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getWireBatchHistory(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireBatchHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireBatchHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public boolean isWireBatchDeleteable(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.isWireBatchDeleteable(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** isWireBatchDeleteable failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WirePayeeInfo addWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addWirePayee(paramWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WirePayeeInfo[] addWirePayee(WirePayeeInfo[] paramArrayOfWirePayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addWirePayee(paramArrayOfWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WirePayeeInfo modWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modWirePayee(paramWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WirePayeeInfo canWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canWirePayee(paramWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WirePayeeInfo getWirePayee(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWirePayee(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public WirePayeeInfo getWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWirePayee(paramWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWirePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWPayeeList getWirePayeeByType(BPWPayeeList paramBPWPayeeList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWirePayeeInfoByType(paramBPWPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWirePayeeByType failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWPayeeList getWirePayeeByStatus(BPWPayeeList paramBPWPayeeList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWirePayeeInfoByStatus(paramBPWPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWirePayeeByStatus failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWPayeeList getWirePayeeByGroup(BPWPayeeList paramBPWPayeeList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWirePayeeInfoByGroup(paramBPWPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWirePayeeByGroup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWPayeeList getWirePayeeByCustomer(BPWPayeeList paramBPWPayeeList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWirePayeeInfoByCustomer(paramBPWPayeeList);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWirePayeeByCustomer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public int addIntermediaryBanksToBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addIntermediaryBanksToBeneficiary(paramString, paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addIntermediaryBanksToBeneficiary failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public int delIntermediaryBanksFromBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.delIntermediaryBanksFromBeneficiary(paramString, paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** delIntermediaryBanksFromBeneficiary failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWBankInfo[] addWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addWireBanks(paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addWireBanks failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWBankInfo[] modWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modWireBanks(paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modWireBanks failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWBankInfo[] delWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.delWireBanks(paramArrayOfBPWBankInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** delWireBanks failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWBankInfo[] getWireBanks(String paramString1, String paramString2, String paramString3, String paramString4)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireBanks(paramString1, paramString2, paramString3, paramString4);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireBanks failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWBankInfo[] getWireBanksByRTN(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireBanksByRTN(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireBanksByRTN failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWBankInfo getWireBanksByID(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getWireBanksByID(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireBanksByID failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public void processWireBackendlRslt(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processWireBackendlRslt(paramArrayOfWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireBanksByID failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public void processWireApprovalRevertRslt(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processWireApprovalRevertRslt(paramArrayOfWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireBanksByID failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public void processWireApprovalRslt(WireInfo[] paramArrayOfWireInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processWireApprovalRslt(paramArrayOfWireInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getWireBanksByID failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo addBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addBPWFIInfo(paramBPWFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo modBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modBPWFIInfo(paramBPWFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo canBPWFIInfo(BPWFIInfo paramBPWFIInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canBPWFIInfo(paramBPWFIInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo activateBPWFI(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.activateBPWFI(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** activateBPWFI failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo getBPWFIInfo(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBPWFIInfo(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBPWFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfo(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBPWFIInfo(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBPWFIInfo (mutiple) failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByType(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBPWFIInfoByType(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBPWFIInfoByType failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByStatus(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBPWFIInfoByStatus(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBPWFIInfoByStatus failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo[] getBPWFIInfoByGroup(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBPWFIInfoByGroup(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBPWFIInfoByGroup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo getBPWFIInfoByACHId(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBPWFIInfoByACHId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBPWFIInfoByACHId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWFIInfo getBPWFIInfoByRTN(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBPWFIInfoByRTN(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBPWFIInfoByRTN failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RPPSFIInfo addRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addRPPSFIInfo(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      String str = "*** BPWAdminBean.addRPPSFIInfo failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo getRPPSFIInfoByFIId(String paramString)
    throws FFSException
  {
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRPPSFIInfoByFIId(paramString);
    }
    catch (Exception localException)
    {
      String str = "*** BPWAdminBean.getRPPSFIInfoByFIId failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo getRPPSFIInfoByFIRPPSId(String paramString)
    throws FFSException
  {
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRPPSFIInfoByFIRPPSId(paramString);
    }
    catch (Exception localException)
    {
      String str = "*** BPWAdminBean.getRPPSFIInfoByFIRPPSId failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo canRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canRPPSFIInfo(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      String str = "*** BPWAdminBean.canRPPSFIInfo failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo activateRPPSFI(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.activateRPPSFI(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      String str = "*** BPWAdminBean.activateRPPSFI failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public RPPSFIInfo modRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modRPPSFIInfo(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      String str = "*** BPWAdminBean.activateRPPSFI failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public int getSmartDate(String paramString, int paramInt)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    int i;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      i = localBPWEngine.getSmartDate(paramString, paramInt);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getSmartDate failed: " + FFSDebug.stackTrace(localThrowable));
      throw new RemoteException("getSmartDate failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
    return i;
  }
  
  public void processApprovalResult(String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws RemoteException, EJBException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processApprovalResult(paramString1, paramString2, paramString3, paramHashMap);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log("*** processApprovalResult failed: " + FFSDebug.stackTrace(localFFSException));
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** processApprovalResult failed: " + FFSDebug.stackTrace(localThrowable));
      throw new RemoteException("processApprovalResult failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyInfo addCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addCCCompany(paramCCCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** addCCCompany failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("addCCCompany failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyInfo cancelCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancelCCCompany(paramCCCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** cancelCCCompany failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("cancelCCCompany failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyInfo modCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modCCCompany(paramCCCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** modCCCompany failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("modCCCompany failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyInfo getCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCCompany(paramCCCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCCompany failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCCompany failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyInfoList getCCCompanyList(CCCompanyInfoList paramCCCompanyInfoList)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCCompanyList(paramCCCompanyInfoList);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCCompanyList failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCCompanyList failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CutOffInfo getNextCashConCutOff(String paramString1, String paramString2)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getNextCashConCutOff(paramString1, paramString2);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getNextCashConCutOff failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getNextCashConCutOff failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyAcctInfo addCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addCCCompanyAcct(paramCCCompanyAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** addCCCompanyAcct failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("addCCCompanyAcct failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyAcctInfo cancelCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancelCCCompanyAcct(paramCCCompanyAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** cancelCCCompanyAcct failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("cancelCCCompanyAcct failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyAcctInfo modCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modCCCompanyAcct(paramCCCompanyAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** modCCCompanyAcct failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("modCCCompanyAcct failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyAcctInfo getCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCCompanyAcct(paramCCCompanyAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCCompanyAcct failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCCompanyAcct failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyAcctInfoList getCCCompanyAcctList(CCCompanyAcctInfoList paramCCCompanyAcctInfoList)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCCompanyAcctList(paramCCCompanyAcctInfoList);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCCompanyAcctList failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCCompanyAcctList failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyCutOffInfo addCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addCCCompanyCutOff(paramCCCompanyCutOffInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** addCCCompanyCutOff failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("addCCCompanyCutOff failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyCutOffInfo cancelCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancelCCCompanyCutOff(paramCCCompanyCutOffInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** cancelCCCompanyCutOff failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("cancelCCCompanyCutOff failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyCutOffInfo getCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCCompanyCutOff(paramCCCompanyCutOffInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCCompanyCutOff failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCCompanyCutOff failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCCompanyCutOffInfoList getCCCompanyCutOffList(CCCompanyCutOffInfoList paramCCCompanyCutOffInfoList)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCCompanyCutOffList(paramCCCompanyCutOffInfoList);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCCompanyCutOffList failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCCompanyCutOffList failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCLocationInfo addCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addCCLocation(paramCCLocationInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** addCCLocation failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("addCCLocation failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCLocationInfo cancelCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancelCCLocation(paramCCLocationInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** cancelCCLocation failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("cancelCCLocation failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCLocationInfo modCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modCCLocation(paramCCLocationInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** modCCLocation failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("modCCLocation failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCLocationInfo getCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCLocation(paramCCLocationInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCLocation failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCLocation failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCLocationInfoList getCCLocationList(CCLocationInfoList paramCCLocationInfoList)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCLocationList(paramCCLocationInfoList);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCLocationList failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCLocationList failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCEntryInfo addCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addCCEntry(paramCCEntryInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** addCCEntry failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("addCCEntry failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCEntryInfo cancelCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancelCCEntry(paramCCEntryInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** cancelCCEntry failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("cancelCCEntry failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCEntryInfo modCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modCCEntry(paramCCEntryInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** modCCEntry failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("modCCEntry failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCEntryInfo getCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCEntry(paramCCEntryInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCEntry failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCEntry failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCEntryHistInfo getCCEntryHist(CCEntryHistInfo paramCCEntryHistInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCEntryHist(paramCCEntryHistInfo);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCEntryHist failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCEntryHist failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public CCEntrySummaryInfoList getCCEntrySummaryList(CCEntrySummaryInfoList paramCCEntrySummaryInfoList)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCCEntrySummaryList(paramCCEntrySummaryInfoList);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** getCCEntrySummaryList failed: " + FFSDebug.stackTrace(localThrowable));
      throw new FFSException("getCCEntrySummaryList failed. Server error: " + FFSDebug.stackTrace(localThrowable));
    }
  }
  
  public TransferInfo addTransfer(TransferInfo paramTransferInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addTransfer(paramTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo modTransfer(TransferInfo paramTransferInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modTransfer(paramTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo cancTransfer(TransferInfo paramTransferInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancTransfer(paramTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** cancTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  /**
   * @deprecated
   */
  public TransferInfo getTransferBySrvrTId(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getTransferBySrvrTId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransferBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo getTransferBySrvrTId(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return null;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransferBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo getTransferByTrackingId(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getTransferByTrackingId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransferByTrackingId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo[] getTransfersByRecSrvrTId(String paramString, boolean paramBoolean)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getTransfersByRecSrvrTId(paramString, paramBoolean);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransfersByRecSrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo[] getTransfersByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getTransfersByRecSrvrTId(paramArrayOfString, paramBoolean);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransfersByRecSrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo[] getTransfersBySrvrTId(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getTransfersBySrvrTId(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransfersBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo[] getTransfersByTrackingId(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getTransfersByTrackingId(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransfersByTrackingId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getTransferHistory(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getTransferHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getTransferHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getRecTransfers(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecTransfers(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public TransferInfo[] addTransfers(TransferInfo[] paramArrayOfTransferInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addTransfers(paramArrayOfTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addTransfers failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecTransferInfo addRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addRecTransfer(paramRecTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addRecTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecTransferInfo modRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modRecTransfer(paramRecTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modRecTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecTransferInfo cancRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancRecTransfer(paramRecTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** cancRecTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  /**
   * @deprecated
   */
  public RecTransferInfo getRecTransferBySrvrTId(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecTransferBySrvrTId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecTransferBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecTransferInfo getRecTransferBySrvrTId(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return null;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecTransferBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecTransferInfo getRecTransferByTrackingId(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecTransferByTrackingId(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecTransferByTrackingId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecTransferInfo[] getRecTransfersBySrvrTId(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecTransfersBySrvrTId(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecTransfersBySrvrTId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public RecTransferInfo[] getRecTransfersByTrackingId(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecTransfersByTrackingId(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecTransfersByTrackingId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWHist getRecTransferHistory(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecTransferHistory(paramBPWHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecTransferHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferCompanyInfo addExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addExtTransferCompany(paramExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addExtTransferCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferCompanyInfo canExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canExtTransferCompany(paramExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canExtTransferCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferCompanyInfo modExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modExtTransferCompany(paramExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modExtTransferCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferCompanyInfo getExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getExtTransferCompany(paramExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getExtTransferCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferCompanyList getExtTransferCompanyList(ExtTransferCompanyList paramExtTransferCompanyList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getExtTransferCompanyList(paramExtTransferCompanyList);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getExtTransferCompanyList failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo addExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addExtTransferAccount(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo canExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canExtTransferAccount(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo modExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modExtTransferAccount(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo modExtTransferAccountPrenoteStatus(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modExtTransferAccountPrenoteStatus(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo getExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getExtTransferAccount(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo verifyExtTransferAccount(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo, int[] paramArrayOfInt)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.verifyExtTransferAccount(paramString, paramExtTransferAcctInfo, paramArrayOfInt);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** verifyExtTransferAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo depositAmountsForVerify(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.depositAmountsForVerify(paramString, paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** depositAmountsForVerify failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo activateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.activateExtTransferAcct(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** activateExtTransferAcct failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctInfo inactivateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.inactivateExtTransferAcct(paramExtTransferAcctInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** inactivateExtTransferAcct failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ExtTransferAcctList getExtTransferAcctList(ExtTransferAcctList paramExtTransferAcctList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getExtTransferAcctList(paramExtTransferAcctList);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getExtTransferAcctList failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public NonBusinessDay[] getNonBusinessDays(String paramString)
    throws FFSException
  {
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getNonBusinessDays(paramString);
    }
    catch (Exception localException)
    {
      String str = "*** BPWAdminBean.getNonBusinessDays failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public NonBusinessDay[] getNonBusinessDaysFromFile(String paramString)
    throws FFSException
  {
    BPWEngine localBPWEngine = null;
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getNonBusinessDaysFromFile(paramString);
    }
    catch (Exception localException)
    {
      String str = "*** BPWAdminBean.getNonBusinessDays failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw new FFSException(localException, str);
    }
  }
  
  public PagingInfo getPagedWire(PagingInfo paramPagingInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPagedWire(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getPagedWire failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PagingInfo getPagedTransfer(PagingInfo paramPagingInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPagedTransfer(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getPagedTransfer failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PagingInfo getPagedBillPayments(PagingInfo paramPagingInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPagedBillPayments(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getPagedBillPayments failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public int getValidTransferDateDue(TransferInfo paramTransferInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getValidTransferDateDue(paramTransferInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getValidTransferDateDue failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public PagingInfo getPagedCashCon(PagingInfo paramPagingInfo)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPagedCashCon(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getPagedCashCon failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPayeeByListId(paramString1, paramString2);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getPayeeByListID failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public AccountTypesMap getAccountTypesMap()
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("AccountTypesMap.getAccountTypesMap():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getAccountTypesMap();
    }
    catch (Throwable localThrowable)
    {
      String str = "*** AccountTypesMap.getAccountTypesMap():: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public String getBPWProperty(String paramString1, String paramString2)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("getBPWProperty():: Server is not running!");
    }
    try
    {
      FFSProperties localFFSProperties = (FFSProperties)FFSRegistry.lookup("FFSPROPS");
      str = null;
      if (localFFSProperties != null) {
        str = localFFSProperties.getProperty(paramString2);
      }
      return str;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBPWProperty():: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public TransferBatchInfo addTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("addTransferBatch():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addTransferBatch(paramTransferBatchInfo);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("modifyTransferBatch():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modifyTransferBatch(paramTransferBatchInfo);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("cancelTransferBatch():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.cancelTransferBatch(paramTransferBatchInfo);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("getTransferBatchById():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getTransferBatchById(paramString);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("getPendingTransfers():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.accountHasPendingTransfers(paramAccountTransactions);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** accountHasPendingTransfers:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PmtInfo addBillPayment(PmtInfo paramPmtInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("addBillPayment():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addBillPayment(paramPmtInfo);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("modifyBillPayment():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modifyBillPayment(paramPmtInfo);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("deleteBillPayment():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.deleteBillPayment(paramPmtInfo);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("getBillPaymentById():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getBillPaymentById(paramString1, paramString2);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("addPaymentBatch():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addPaymentBatch(paramPaymentBatchInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addPaymentBatch:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public PaymentBatchInfo modifyPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("modifyPaymentBatch():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modifyPaymentBatch(paramPaymentBatchInfo);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("deletePaymentBatch():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.deletePaymentBatch(paramPaymentBatchInfo);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("getPaymentBatchById():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPaymentBatchById(paramString);
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
    if (!BPWServer.isAlive()) {
      throw new FFSException("getLastPayments():: Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getLastPayments(paramLastPaymentInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getLastPayments:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    BankingDays localBankingDays = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBankingDays = localBPWEngine.getBankingDaysInRange(paramBankingDays, paramHashMap);
    }
    catch (FFSException localFFSException)
    {
      str = "*** getBankingDaysInRange failed: " + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getBankingDaysInRange failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
    return localBankingDays;
  }
  
  public CustomerPayeeInfo addCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localCustomerPayeeInfo = localBPWEngine.addCustomerPayee(paramCustomerPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      str = "*** addCustomerPayee failed: " + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addCustomerPayee failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
    return localCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo deleteCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localCustomerPayeeInfo = localBPWEngine.deleteCustomerPayee(paramCustomerPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      str = "*** deleteCustomerPayee failed: " + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** deleteCustomerPayee failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
    return localCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo updateCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localCustomerPayeeInfo = localBPWEngine.updateCustomerPayee(paramCustomerPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      str = "*** updateCustomerPayee failed: " + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** updateCustomerPayee failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
    return localCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo[] getCustomerPayees(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfCustomerPayeeInfo = localBPWEngine.getCustomerPayees(paramCustomerPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      str = "*** getCustomerPayees failed: " + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getCustomerPayees failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
    return arrayOfCustomerPayeeInfo;
  }
  
  public PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfPayeeInfo = localBPWEngine.searchGlobalPayees(paramPayeeInfo, paramInt);
    }
    catch (FFSException localFFSException)
    {
      str = "*** searchGlobalPayees failed: " + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** searchGlobalPayees failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo getGlobalPayee(String paramString)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getGlobalPayee(paramString);
    }
    catch (FFSException localFFSException)
    {
      str = "*** searchGlobalPayees failed: " + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** searchGlobalPayees failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processPmtTrnRslt(paramArrayOfPmtTrnRslt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processPmtTrnRslt failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */