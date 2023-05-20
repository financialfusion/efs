package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;

import com.ffusion.ffs.bpw.BPWServer;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo;
import com.ffusion.ffs.bpw.interfaces.CustPayeeRslt;
import com.ffusion.ffs.bpw.interfaces.CustomerBankInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo;
import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRslt;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.master.BPWEngine;
import com.ffusion.ffs.bpw.util.AccountTypesMap;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class OFX151BPWServicesBean
  implements SessionBean
{
  SessionContext _context;
  
  public void ejbCreate()
    throws CreateException, RemoteException
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
  
  public TypePmtSyncRsV1[] getPendingPmtsByCustomerID(String paramString, int paramInt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfTypePmtSyncRsV1 = localBPWEngine.getPendingPmtsByCustomerID151(paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPendingPmtsByCustomerID failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfTypePmtSyncRsV1;
  }
  
  public TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfTypePmtSyncRsV1 = localBPWEngine.getPendingPmtsAndHistoryByCustomerID151(paramString, paramInt1, paramInt2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPendingPmtsAndHistoryByCustomerID failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfTypePmtSyncRsV1;
  }
  
  public TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(String paramString, int paramInt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfTypeIntraSyncRsV1 = localBPWEngine.getPendingIntrasByCustomerID151(paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPendingIntrasByCustomerID failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfTypeIntraSyncRsV1;
  }
  
  public TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfTypeIntraSyncRsV1 = localBPWEngine.getPendingIntrasAndHistoryByCustomerID151(paramString, paramInt1, paramInt2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPendingIntrasAndHistoryByCustomerID failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfTypeIntraSyncRsV1;
  }
  
  public TypePmtSyncRsV1 getPendingPmts(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypePmtSyncRsV1 = localBPWEngine.getPendingPmts(paramTypePmtSyncRqV1, paramTypeUserData, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPendingPmts failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return localTypePmtSyncRsV1;
  }
  
  public TypeIntraSyncRsV1 getPendingIntras(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypeIntraSyncRsV1 = localBPWEngine.getPendingIntras(paramTypeIntraSyncRqV1, paramTypeUserData, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPendingIntras failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return localTypeIntraSyncRsV1;
  }
  
  public PayeeInfo[] getLinkedPayees()
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfPayeeInfo = localBPWEngine.getLinkedPayees();
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getLinkedPayees failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getMostUsedPayees(int paramInt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfPayeeInfo = localBPWEngine.getMostUsedPayees(paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getMostUsedPayees failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getPreferredPayees(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfPayeeInfo = localBPWEngine.getPreferedPayees(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPreferedPayees failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfPayeeInfo;
  }
  
  public String getPmtStatus(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    String str;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      str = localBPWEngine.getPmtStatus(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPmtStatus failed:" + localException.toString());
      localException.printStackTrace();
      throw new RemoteException("Server error: " + localException.toString());
    }
    return str;
  }
  
  public boolean checkPayeeEditMask(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    boolean bool;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      bool = localBPWEngine.checkPayeeEditMask(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** checkPayeeEditMask failed:" + localException.toString());
      localException.printStackTrace();
      throw new RemoteException("Server error: " + localException.toString());
    }
    return bool;
  }
  
  public void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processIntraTrnRslt(paramArrayOfIntraTrnRslt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processIntraTrnRslt failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
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
  
  public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processCustPayeeRslt(paramArrayOfCustPayeeRslt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processCustPayeeRslt failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public void processFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processFundAllocRslt(paramArrayOfFundsAllocRslt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processFundRevertRslt failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public void processFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processFundRevertRslt(paramArrayOfFundsAllocRslt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processFundRevertRslt failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.processPayeeRslt(paramArrayOfPayeeRslt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processPayeeRslt failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addPayeeFromBackend(paramPayeeInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** addPayeeFromBackend failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public PayeeInfo[] updatePayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.updatePayeeFromBackend(paramPayeeInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** updatePayeeFromBackend failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.addPayeeRouteInfo(paramPayeeRouteInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** addPayeeRouteInfo failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypeIntraSyncRsV1 = localBPWEngine.processIntraSyncRqV1(paramTypeIntraSyncRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processIntraSyncRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypeIntraSyncRsV1;
  }
  
  public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypeIntraTrnRsV1 = localBPWEngine.processIntraTrnRqV1(paramTypeIntraTrnRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processIntraTrnRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypeIntraTrnRsV1;
  }
  
  public TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypePayeeSyncRsV1 localTypePayeeSyncRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypePayeeSyncRsV1 = localBPWEngine.processPayeeSyncRqV1(paramTypePayeeSyncRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processPayeeSyncRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypePayeeSyncRsV1;
  }
  
  public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypePayeeTrnRsV1 = localBPWEngine.processPayeeTrnRqV1(paramTypePayeeTrnRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processPayeeTrnRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypePayeeTrnRsV1;
  }
  
  public TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypePmtInqTrnRsV1 localTypePmtInqTrnRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypePmtInqTrnRsV1 = localBPWEngine.processPmtInqTrnRqV1(paramTypePmtInqTrnRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processPmtInqTrnRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypePmtInqTrnRsV1;
  }
  
  public TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypePmtSyncRsV1 localTypePmtSyncRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypePmtSyncRsV1 = localBPWEngine.processPmtSyncRqV1(paramTypePmtSyncRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processPmtSyncRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypePmtSyncRsV1;
  }
  
  public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypePmtTrnRsV1 localTypePmtTrnRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypePmtTrnRsV1 = localBPWEngine.processPmtTrnRqV1(paramTypePmtTrnRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processPmtTrnRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypePmtTrnRsV1;
  }
  
  public TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypeRecIntraSyncRsV1 = localBPWEngine.processRecIntraSyncRqV1(paramTypeRecIntraSyncRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processRecIntraSyncRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypeRecIntraSyncRsV1;
  }
  
  public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypeRecIntraTrnRsV1 = localBPWEngine.processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processRecIntraTrnRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  public TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypeRecPmtSyncRsV1 = localBPWEngine.processRecPmtSyncRqV1(paramTypeRecPmtSyncRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processRecPmtSyncRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypeRecPmtSyncRsV1;
  }
  
  public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localTypeRecPmtTrnRsV1 = localBPWEngine.processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** processRecPmtTrnRqV1 failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
    return localTypeRecPmtTrnRsV1;
  }
  
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
  
  public String[] getPayeeNames(String paramString)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    String[] arrayOfString = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfString = localBPWEngine.getPayeeNames(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPayeeNames failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfString;
  }
  
  public String[] getPayeeIDs(String paramString)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    String[] arrayOfString = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfString = localBPWEngine.getPayeeIDs(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPayeeIDs failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfString;
  }
  
  public PayeeInfo[] getPayees(String paramString)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfPayeeInfo = localBPWEngine.getPayees(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPayees failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] searchGlobalPayees(String paramString)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfPayeeInfo = localBPWEngine.searchGlobalPayees(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** searchGlobalPayees failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfPayeeInfo;
  }
  
  public void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.updatePayee(paramPayeeInfo, paramPayeeRouteInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** updatePayee failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public void deletePayee(String paramString)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.deletePayee(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** deletePayee failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public void deletePayees(String[] paramArrayOfString)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.deletePayees(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** deletePayees failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public PayeeInfo findPayeeByID(String paramString)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo localPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localPayeeInfo = localBPWEngine.findPayeeByID(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** findPayeeByID failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return localPayeeInfo;
  }
  
  public void setPayeeStatus(String paramString1, String paramString2)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    Object localObject = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.setPayeeStatus(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** setPayeeStatus failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public int getSmartDate(int paramInt)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    int i;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      i = localBPWEngine.getSmartDate(paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getSmartDate failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return i;
  }
  
  public PayeeInfo[] getPayees(String paramString, int paramInt)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfPayeeInfo = localBPWEngine.getPayees(paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPayees failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getGlobalPayees(String paramString, int paramInt)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfPayeeInfo = localBPWEngine.getGlobalPayees(paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getGlobalPayees failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfPayeeInfo;
  }
  
  public String[] getPayeeNames(String paramString, int paramInt)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    String[] arrayOfString = null;
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      arrayOfString = localBPWEngine.getPayeeNames(paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPayeeNames failed:" + localException.toString() + FFSDebug.stackTrace(localException));
      throw new RemoteException("Server error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    return arrayOfString;
  }
  
  public String addPayee(PayeeInfo paramPayeeInfo, int paramInt)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addPayee(paramPayeeInfo, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** addPayee failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public PayeeInfo[] updatePayee(PayeeInfo paramPayeeInfo, int paramInt)
    throws RemoteException, EJBException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.updatePayee(paramPayeeInfo, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** updatePayee failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public int addConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addConsumerCrossRef(paramArrayOfConsumerCrossRefInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** addConsumerCrossRef failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public int deleteConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.deleteConsumerCrossRef(paramArrayOfConsumerCrossRefInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** deleteConsumerCrossRef failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public ConsumerCrossRefInfo[] getConsumerCrossRef(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getConsumerCrossRef(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getConsumerCrossRef failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public int addCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addCustomerBankInfo(paramArrayOfCustomerBankInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** addCustomerBankInfo failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public int deleteCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.deleteCustomerBankInfo(paramArrayOfCustomerBankInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** deleteCustomerBankInfo failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public CustomerBankInfo[] getCustomerBankInfo(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCustomerBankInfo(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerBankInfo failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public int addCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addCustomerProductAccessInfo(paramArrayOfCustomerProductAccessInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** addCustomerProductAccessInfo failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public int deleteCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.deleteCustomerProductAccessInfo(paramArrayOfCustomerProductAccessInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** deleteCustomerProductAccessInfo failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public CustomerProductAccessInfo[] getCustomerProductAccessInfo(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getCustomerProductAccessInfo(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getCustomerProductAccessInfo failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public boolean validateMetavanteCustAcctByConsumerID(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.validateMetavanteCustAcctByConsumerID(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** validateMetavanteCustAcctByConsumerID failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public boolean validateMetavanteCustAcctByCustomerID(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.validateMetavanteCustAcctByCustomerID(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** validateMetavanteCustAcctByCustomerID failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public BPWHist getPmtHistory(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPmtHistory(paramBPWHist);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPmtHistory failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public BPWHist getIntraHistory(BPWHist paramBPWHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getIntraHistory(paramBPWHist);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getIntraHistory failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public IntraTrnInfo getIntraById(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getIntraById151(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getIntraById failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public IntraTrnInfo[] getIntraById(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getIntraById151(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getIntraById (multiple) failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public PmtInfo getPmtById(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPmtById151(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPmtById failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public PmtInfo[] getPmtById(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPmtById151(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getPmtById (multiple) failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
    }
  }
  
  public RecPmtInfo[] getRecPmtById(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecPmtById151(paramArrayOfString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** getRecPmtById (multiple) failed:" + localException.toString());
      throw new RemoteException("Server error: " + localException.toString());
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
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.OFX151BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */