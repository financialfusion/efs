package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillTblStructTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillTblStructTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtEndTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtEndTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChallengeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChallengeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChgUserInfoSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChgUserInfoSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChgUserInfoTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChgUserInfoTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeDisconnectRq;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeDisconnectRs;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeEnrollTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeEnrollTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeFindBillerTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeFindBillerTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeGetMIMETrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeGetMIMETrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXResponse;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresDetailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresDetailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresGrpAcctInfoTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresGrpAcctInfoTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresListTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresListTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresNotifyTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresNotifyTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeProfTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeProfTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecInterSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecInterSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecInterTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecInterTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecTaxPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecTaxPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecTaxPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecTaxPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSecListTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSecListTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStartRq;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStartRs;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtEndTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtEndTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStopRq;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStopRs;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStpChkSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStpChkSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStpChkTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStpChkTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTax1099TrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTax1099TrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxW2TrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxW2TrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsV1;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public abstract interface IOFX200Callback
  extends EJBObject
{
  public abstract TypeAcctInfoTrnRsV1 processAcctInfoTrnRqV1(TypeAcctInfoTrnRqV1 paramTypeAcctInfoTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeAcctSyncRsV1 processAcctSyncRqV1(TypeAcctSyncRqV1 paramTypeAcctSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeAcctTrnRsV1 processAcctTrnRqV1(TypeAcctTrnRqV1 paramTypeAcctTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeBankMailSyncRsV1 processBankMailSyncRqV1(TypeBankMailSyncRqV1 paramTypeBankMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeBankMailTrnRsV1 processBankMailTrnRqV1(TypeBankMailTrnRqV1 paramTypeBankMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeBillTblStructTrnRsV1 processBillTblStructTrnRqV1(TypeBillTblStructTrnRqV1 paramTypeBillTblStructTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeCCStmtEndTrnRsV1 processCCStmtEndTrnRqV1(TypeCCStmtEndTrnRqV1 paramTypeCCStmtEndTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeCCStmtTrnRsV1 processCCStmtTrnRqV1(TypeCCStmtTrnRqV1 paramTypeCCStmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChallengeTrnRsV1 processChallengeTrnRqV1(TypeChallengeTrnRqV1 paramTypeChallengeTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChgUserInfoSyncRsV1 processChgUserInfoSyncRqV1(TypeChgUserInfoSyncRqV1 paramTypeChgUserInfoSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChgUserInfoTrnRsV1 processChgUserInfoTrnRqV1(TypeChgUserInfoTrnRqV1 paramTypeChgUserInfoTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeDisconnectRs processDisconnectRq(TypeDisconnectRq paramTypeDisconnectRq, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeEnrollTrnRsV1 processEnrollTrnRqV1(TypeEnrollTrnRqV1 paramTypeEnrollTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeFindBillerTrnRsV1 processFindBillerTrnRqV1(TypeFindBillerTrnRqV1 paramTypeFindBillerTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeGetMIMETrnRsV1 processGetMIMETrnRqV1(TypeGetMIMETrnRqV1 paramTypeGetMIMETrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInterSyncRsV1 processInterSyncRqV1(TypeInterSyncRqV1 paramTypeInterSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInterTrnRsV1 processInterTrnRqV1(TypeInterTrnRqV1 paramTypeInterTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvMailSyncRsV1 processInvMailSyncRqV1(TypeInvMailSyncRqV1 paramTypeInvMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvMailTrnRsV1 processInvMailTrnRqV1(TypeInvMailTrnRqV1 paramTypeInvMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvStmtTrnRsV1 processInvStmtTrnRqV1(TypeInvStmtTrnRqV1 paramTypeInvStmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeMailSyncRsV1 processMailSyncRqV1(TypeMailSyncRqV1 paramTypeMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeMailTrnRsV1 processMailTrnRqV1(TypeMailTrnRqV1 paramTypeMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeOFXResponse processOFXRequest(TypeOFXRequest paramTypeOFXRequest, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePinChTrnRsV1 processPinChTrnRqV1(TypePinChTrnRqV1 paramTypePinChTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtMailSyncRsV1 processPmtMailSyncRqV1(TypePmtMailSyncRqV1 paramTypePmtMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtMailTrnRsV1 processPmtMailTrnRqV1(TypePmtMailTrnRqV1 paramTypePmtMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePresDetailTrnRsV1 processPresDetailTrnRqV1(TypePresDetailTrnRqV1 paramTypePresDetailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePresGrpAcctInfoTrnRsV1 processPresGrpAcctInfoTrnRqV1(TypePresGrpAcctInfoTrnRqV1 paramTypePresGrpAcctInfoTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePresListTrnRsV1 processPresListTrnRqV1(TypePresListTrnRqV1 paramTypePresListTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePresMailSyncRsV1 processPresMailSyncRqV1(TypePresMailSyncRqV1 paramTypePresMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePresMailTrnRsV1 processPresMailTrnRqV1(TypePresMailTrnRqV1 paramTypePresMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePresNotifyTrnRsV1 processPresNotifyTrnRqV1(TypePresNotifyTrnRqV1 paramTypePresNotifyTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeProfTrnRsV1 processProfTrnRqV1(TypeProfTrnRqV1 paramTypeProfTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecInterSyncRsV1 processRecInterSyncRqV1(TypeRecInterSyncRqV1 paramTypeRecInterSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecInterTrnRsV1 processRecInterTrnRqV1(TypeRecInterTrnRqV1 paramTypeRecInterTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecTaxPmtSyncRsV1 processRecTaxPmtSyncRqV1(TypeRecTaxPmtSyncRqV1 paramTypeRecTaxPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecTaxPmtTrnRsV1 processRecTaxPmtTrnRqV1(TypeRecTaxPmtTrnRqV1 paramTypeRecTaxPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeSecListTrnRsV1 processSecListTrnRqV1(TypeSecListTrnRqV1 paramTypeSecListTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeSonRsV1 processSonRqV1(TypeSonRqV1 paramTypeSonRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStartRs processStartRq(TypeStartRq paramTypeStartRq, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStmtEndTrnRsV1 processStmtEndTrnRqV1(TypeStmtEndTrnRqV1 paramTypeStmtEndTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStmtTrnRsV1 processStmtTrnRqV1(TypeStmtTrnRqV1 paramTypeStmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStopRs processStopRq(TypeStopRq paramTypeStopRq, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStpChkSyncRsV1 processStpChkSyncRqV1(TypeStpChkSyncRqV1 paramTypeStpChkSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStpChkTrnRsV1 processStpChkTrnRqV1(TypeStpChkTrnRqV1 paramTypeStpChkTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeTax1099TrnRsV1 processTax1099TrnRqV1(TypeTax1099TrnRqV1 paramTypeTax1099TrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeTaxPmtSyncRsV1 processTaxPmtSyncRqV1(TypeTaxPmtSyncRqV1 paramTypeTaxPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeTaxPmtTrnRsV1 processTaxPmtTrnRqV1(TypeTaxPmtTrnRqV1 paramTypeTaxPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeTaxW2TrnRsV1 processTaxW2TrnRqV1(TypeTaxW2TrnRqV1 paramTypeTaxW2TrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeWireSyncRsV1 processWireSyncRqV1(TypeWireSyncRqV1 paramTypeWireSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeWireTrnRsV1 processWireTrnRqV1(TypeWireTrnRqV1 paramTypeWireTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
}


/* Location:           D:\drops\jd\jars\OFX200Callback.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.IOFX200Callback
 * JD-Core Version:    0.7.0.1
 */