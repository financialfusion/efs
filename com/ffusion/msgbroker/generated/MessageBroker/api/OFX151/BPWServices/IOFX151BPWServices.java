package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;

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
import com.ffusion.ffs.bpw.util.AccountTypesMap;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
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
import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public abstract interface IOFX151BPWServices
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
  
  public abstract PayeeInfo[] getLinkedPayees()
    throws RemoteException;
  
  public abstract PayeeInfo[] getMostUsedPayees(int paramInt)
    throws RemoteException;
  
  public abstract PayeeInfo[] getPreferredPayees(String paramString)
    throws RemoteException;
  
  public abstract TypePmtSyncRsV1[] getPendingPmtsByCustomerID(String paramString, int paramInt)
    throws RemoteException;
  
  public abstract TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(String paramString, int paramInt)
    throws RemoteException;
  
  public abstract TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract TypePmtSyncRsV1 getPendingPmts(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws RemoteException;
  
  public abstract TypeIntraSyncRsV1 getPendingIntras(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws RemoteException;
  
  public abstract String getPmtStatus(String paramString)
    throws RemoteException;
  
  public abstract boolean checkPayeeEditMask(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
    throws RemoteException;
  
  public abstract void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws RemoteException;
  
  public abstract void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
    throws RemoteException;
  
  public abstract void processFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws RemoteException;
  
  public abstract void processFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws RemoteException;
  
  public abstract void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
    throws RemoteException;
  
  public abstract String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws RemoteException;
  
  public abstract PayeeInfo[] updatePayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws RemoteException;
  
  public abstract void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
    throws RemoteException;
  
  public abstract TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException;
  
  public abstract String[] getPayeeNames(String paramString)
    throws RemoteException, EJBException;
  
  public abstract String[] getPayeeIDs(String paramString)
    throws RemoteException, EJBException;
  
  public abstract PayeeInfo[] getPayees(String paramString)
    throws RemoteException, EJBException;
  
  public abstract PayeeInfo[] searchGlobalPayees(String paramString)
    throws RemoteException, EJBException;
  
  public abstract void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws RemoteException, EJBException;
  
  public abstract void deletePayee(String paramString)
    throws RemoteException, EJBException;
  
  public abstract void deletePayees(String[] paramArrayOfString)
    throws RemoteException, EJBException;
  
  public abstract PayeeInfo findPayeeByID(String paramString)
    throws RemoteException, EJBException;
  
  public abstract void setPayeeStatus(String paramString1, String paramString2)
    throws RemoteException, EJBException;
  
  public abstract int getSmartDate(int paramInt)
    throws RemoteException, EJBException;
  
  public abstract PayeeInfo[] getPayees(String paramString, int paramInt)
    throws RemoteException, EJBException;
  
  public abstract PayeeInfo[] getGlobalPayees(String paramString, int paramInt)
    throws RemoteException, EJBException;
  
  public abstract String[] getPayeeNames(String paramString, int paramInt)
    throws RemoteException, EJBException;
  
  public abstract String addPayee(PayeeInfo paramPayeeInfo, int paramInt)
    throws RemoteException, EJBException;
  
  public abstract PayeeInfo[] updatePayee(PayeeInfo paramPayeeInfo, int paramInt)
    throws RemoteException, EJBException;
  
  public abstract int addConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
    throws RemoteException;
  
  public abstract int deleteConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
    throws RemoteException;
  
  public abstract ConsumerCrossRefInfo[] getConsumerCrossRef(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract int addCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
    throws RemoteException;
  
  public abstract int deleteCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
    throws RemoteException;
  
  public abstract CustomerBankInfo[] getCustomerBankInfo(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract int addCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
    throws RemoteException;
  
  public abstract int deleteCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
    throws RemoteException;
  
  public abstract CustomerProductAccessInfo[] getCustomerProductAccessInfo(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract boolean validateMetavanteCustAcctByConsumerID(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract boolean validateMetavanteCustAcctByCustomerID(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract BPWHist getPmtHistory(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract BPWHist getIntraHistory(BPWHist paramBPWHist)
    throws RemoteException;
  
  public abstract IntraTrnInfo getIntraById(String paramString)
    throws RemoteException;
  
  public abstract IntraTrnInfo[] getIntraById(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract PmtInfo getPmtById(String paramString)
    throws RemoteException;
  
  public abstract PmtInfo[] getPmtById(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract RecPmtInfo[] getRecPmtById(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract PayeeInfo getPayeeByListId(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract AccountTypesMap getAccountTypesMap()
    throws RemoteException, FFSException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.IOFX151BPWServices
 * JD-Core Version:    0.7.0.1
 */