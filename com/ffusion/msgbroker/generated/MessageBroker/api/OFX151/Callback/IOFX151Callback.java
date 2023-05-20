package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillTblStructTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillTblStructTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtEndTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtEndTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtEndTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtEndTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChallengeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChallengeTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChallengeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChallengeTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeDisconnectRq;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeDisconnectRs;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeFindBillerTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeFindBillerTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeGetMIMETrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeGetMIMETrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeGetMIMETrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeGetMIMETrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvStmtTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvStmtTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMultiInterTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMultiInterTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXResponse;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresDetailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresDetailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresGrpAcctInfoTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresGrpAcctInfoTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresListTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresListTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresNotifyTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresNotifyTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeProfTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeProfTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeProfTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeProfTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSecListTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSecListTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSecListTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSecListTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStartRq;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStartRs;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtEndTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtEndTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtEndTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtEndTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStopRq;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStopRs;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtTrnRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireSyncRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireSyncRsV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRqV2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV2;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public abstract interface IOFX151Callback
  extends EJBObject
{
  public abstract TypeAcctInfoTrnRsV1 processAcctInfoTrnRqV1(TypeAcctInfoTrnRqV1 paramTypeAcctInfoTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeAcctInfoTrnRsV2 processAcctInfoTrnRqV2(TypeAcctInfoTrnRqV2 paramTypeAcctInfoTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeAcctSyncRsV1 processAcctSyncRqV1(TypeAcctSyncRqV1 paramTypeAcctSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeAcctSyncRsV2 processAcctSyncRqV2(TypeAcctSyncRqV2 paramTypeAcctSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeAcctTrnRsV1 processAcctTrnRqV1(TypeAcctTrnRqV1 paramTypeAcctTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeAcctTrnRsV2 processAcctTrnRqV2(TypeAcctTrnRqV2 paramTypeAcctTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeBankMailSyncRsV1 processBankMailSyncRqV1(TypeBankMailSyncRqV1 paramTypeBankMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeBankMailSyncRsV2 processBankMailSyncRqV2(TypeBankMailSyncRqV2 paramTypeBankMailSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeBankMailTrnRsV1 processBankMailTrnRqV1(TypeBankMailTrnRqV1 paramTypeBankMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeBankMailTrnRsV2 processBankMailTrnRqV2(TypeBankMailTrnRqV2 paramTypeBankMailTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeBillTblStructTrnRsV1 processBillTblStructTrnRqV1(TypeBillTblStructTrnRqV1 paramTypeBillTblStructTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeCCStmtEndTrnRsV1 processCCStmtEndTrnRqV1(TypeCCStmtEndTrnRqV1 paramTypeCCStmtEndTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeCCStmtEndTrnRsV2 processCCStmtEndTrnRqV2(TypeCCStmtEndTrnRqV2 paramTypeCCStmtEndTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeCCStmtTrnRsV1 processCCStmtTrnRqV1(TypeCCStmtTrnRqV1 paramTypeCCStmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeCCStmtTrnRsV2 processCCStmtTrnRqV2(TypeCCStmtTrnRqV2 paramTypeCCStmtTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChallengeTrnRsV1 processChallengeTrnRqV1(TypeChallengeTrnRqV1 paramTypeChallengeTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChallengeTrnRsV2 processChallengeTrnRqV2(TypeChallengeTrnRqV2 paramTypeChallengeTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChgUserInfoSyncRsV1 processChgUserInfoSyncRqV1(TypeChgUserInfoSyncRqV1 paramTypeChgUserInfoSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChgUserInfoSyncRsV2 processChgUserInfoSyncRqV2(TypeChgUserInfoSyncRqV2 paramTypeChgUserInfoSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChgUserInfoTrnRsV1 processChgUserInfoTrnRqV1(TypeChgUserInfoTrnRqV1 paramTypeChgUserInfoTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeChgUserInfoTrnRsV2 processChgUserInfoTrnRqV2(TypeChgUserInfoTrnRqV2 paramTypeChgUserInfoTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeDisconnectRs processDisconnectRq(TypeDisconnectRq paramTypeDisconnectRq, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeEnrollTrnRsV1 processEnrollTrnRqV1(TypeEnrollTrnRqV1 paramTypeEnrollTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeEnrollTrnRsV2 processEnrollTrnRqV2(TypeEnrollTrnRqV2 paramTypeEnrollTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeFindBillerTrnRsV1 processFindBillerTrnRqV1(TypeFindBillerTrnRqV1 paramTypeFindBillerTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeGetMIMETrnRsV1 processGetMIMETrnRqV1(TypeGetMIMETrnRqV1 paramTypeGetMIMETrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeGetMIMETrnRsV2 processGetMIMETrnRqV2(TypeGetMIMETrnRqV2 paramTypeGetMIMETrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInterSyncRsV1 processInterSyncRqV1(TypeInterSyncRqV1 paramTypeInterSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInterSyncRsV2 processInterSyncRqV2(TypeInterSyncRqV2 paramTypeInterSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInterTrnRsV1 processInterTrnRqV1(TypeInterTrnRqV1 paramTypeInterTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInterTrnRsV2 processInterTrnRqV2(TypeInterTrnRqV2 paramTypeInterTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeIntraSyncRsV2 processIntraSyncRqV2(TypeIntraSyncRqV2 paramTypeIntraSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeIntraTrnRsV2 processIntraTrnRqV2(TypeIntraTrnRqV2 paramTypeIntraTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvMailSyncRsV1 processInvMailSyncRqV1(TypeInvMailSyncRqV1 paramTypeInvMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvMailSyncRsV2 processInvMailSyncRqV2(TypeInvMailSyncRqV2 paramTypeInvMailSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvMailTrnRsV1 processInvMailTrnRqV1(TypeInvMailTrnRqV1 paramTypeInvMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvMailTrnRsV2 processInvMailTrnRqV2(TypeInvMailTrnRqV2 paramTypeInvMailTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvStmtTrnRsV1 processInvStmtTrnRqV1(TypeInvStmtTrnRqV1 paramTypeInvStmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeInvStmtTrnRsV2 processInvStmtTrnRqV2(TypeInvStmtTrnRqV2 paramTypeInvStmtTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeMailSyncRsV1 processMailSyncRqV1(TypeMailSyncRqV1 paramTypeMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeMailSyncRsV2 processMailSyncRqV2(TypeMailSyncRqV2 paramTypeMailSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeMailTrnRsV1 processMailTrnRqV1(TypeMailTrnRqV1 paramTypeMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeMailTrnRsV2 processMailTrnRqV2(TypeMailTrnRqV2 paramTypeMailTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeMultiInterTrnRsV2 processMultiInterTrnRqV2(TypeMultiInterTrnRqV2 paramTypeMultiInterTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeOFXResponse processOFXRequest(TypeOFXRequest paramTypeOFXRequest, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePayeeSyncRsV2 processPayeeSyncRqV2(TypePayeeSyncRqV2 paramTypePayeeSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePayeeTrnRsV2 processPayeeTrnRqV2(TypePayeeTrnRqV2 paramTypePayeeTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePinChTrnRsV1 processPinChTrnRqV1(TypePinChTrnRqV1 paramTypePinChTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePinChTrnRsV2 processPinChTrnRqV2(TypePinChTrnRqV2 paramTypePinChTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtInqTrnRsV2 processPmtInqTrnRqV2(TypePmtInqTrnRqV2 paramTypePmtInqTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtMailSyncRsV1 processPmtMailSyncRqV1(TypePmtMailSyncRqV1 paramTypePmtMailSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtMailSyncRsV2 processPmtMailSyncRqV2(TypePmtMailSyncRqV2 paramTypePmtMailSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtMailTrnRsV1 processPmtMailTrnRqV1(TypePmtMailTrnRqV1 paramTypePmtMailTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtMailTrnRsV2 processPmtMailTrnRqV2(TypePmtMailTrnRqV2 paramTypePmtMailTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtSyncRsV2 processPmtSyncRqV2(TypePmtSyncRqV2 paramTypePmtSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypePmtTrnRsV2 processPmtTrnRqV2(TypePmtTrnRqV2 paramTypePmtTrnRqV2, TypeUserData paramTypeUserData)
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
  
  public abstract TypeProfTrnRsV2 processProfTrnRqV2(TypeProfTrnRqV2 paramTypeProfTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecInterSyncRsV1 processRecInterSyncRqV1(TypeRecInterSyncRqV1 paramTypeRecInterSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecInterSyncRsV2 processRecInterSyncRqV2(TypeRecInterSyncRqV2 paramTypeRecInterSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecInterTrnRsV1 processRecInterTrnRqV1(TypeRecInterTrnRqV1 paramTypeRecInterTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecInterTrnRsV2 processRecInterTrnRqV2(TypeRecInterTrnRqV2 paramTypeRecInterTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecIntraSyncRsV2 processRecIntraSyncRqV2(TypeRecIntraSyncRqV2 paramTypeRecIntraSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecIntraTrnRsV2 processRecIntraTrnRqV2(TypeRecIntraTrnRqV2 paramTypeRecIntraTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecPmtSyncRsV2 processRecPmtSyncRqV2(TypeRecPmtSyncRqV2 paramTypeRecPmtSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecPmtTrnRsV2 processRecPmtTrnRqV2(TypeRecPmtTrnRqV2 paramTypeRecPmtTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecTaxPmtSyncRsV1 processRecTaxPmtSyncRqV1(TypeRecTaxPmtSyncRqV1 paramTypeRecTaxPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecTaxPmtSyncRsV2 processRecTaxPmtSyncRqV2(TypeRecTaxPmtSyncRqV2 paramTypeRecTaxPmtSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecTaxPmtTrnRsV1 processRecTaxPmtTrnRqV1(TypeRecTaxPmtTrnRqV1 paramTypeRecTaxPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeRecTaxPmtTrnRsV2 processRecTaxPmtTrnRqV2(TypeRecTaxPmtTrnRqV2 paramTypeRecTaxPmtTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeSecListTrnRsV1 processSecListTrnRqV1(TypeSecListTrnRqV1 paramTypeSecListTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeSecListTrnRsV2 processSecListTrnRqV2(TypeSecListTrnRqV2 paramTypeSecListTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeSonRsV1 processSonRqV1(TypeSonRqV1 paramTypeSonRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeSonRsV2 processSonRqV2(TypeSonRqV2 paramTypeSonRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStartRs processStartRq(TypeStartRq paramTypeStartRq, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStmtEndTrnRsV1 processStmtEndTrnRqV1(TypeStmtEndTrnRqV1 paramTypeStmtEndTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStmtEndTrnRsV2 processStmtEndTrnRqV2(TypeStmtEndTrnRqV2 paramTypeStmtEndTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStmtTrnRsV1 processStmtTrnRqV1(TypeStmtTrnRqV1 paramTypeStmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStmtTrnRsV2 processStmtTrnRqV2(TypeStmtTrnRqV2 paramTypeStmtTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStopRs processStopRq(TypeStopRq paramTypeStopRq, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStpChkSyncRsV1 processStpChkSyncRqV1(TypeStpChkSyncRqV1 paramTypeStpChkSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStpChkSyncRsV2 processStpChkSyncRqV2(TypeStpChkSyncRqV2 paramTypeStpChkSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStpChkTrnRsV1 processStpChkTrnRqV1(TypeStpChkTrnRqV1 paramTypeStpChkTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeStpChkTrnRsV2 processStpChkTrnRqV2(TypeStpChkTrnRqV2 paramTypeStpChkTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeTaxPmtSyncRsV1 processTaxPmtSyncRqV1(TypeTaxPmtSyncRqV1 paramTypeTaxPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeTaxPmtSyncRsV2 processTaxPmtSyncRqV2(TypeTaxPmtSyncRqV2 paramTypeTaxPmtSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeTaxPmtTrnRsV1 processTaxPmtTrnRqV1(TypeTaxPmtTrnRqV1 paramTypeTaxPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeTaxPmtTrnRsV2 processTaxPmtTrnRqV2(TypeTaxPmtTrnRqV2 paramTypeTaxPmtTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeWireSyncRsV1 processWireSyncRqV1(TypeWireSyncRqV1 paramTypeWireSyncRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeWireSyncRsV2 processWireSyncRqV2(TypeWireSyncRqV2 paramTypeWireSyncRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeWireTrnRsV1 processWireTrnRqV1(TypeWireTrnRqV1 paramTypeWireTrnRqV1, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
  
  public abstract TypeWireTrnRsV2 processWireTrnRqV2(TypeWireTrnRqV2 paramTypeWireTrnRqV2, TypeUserData paramTypeUserData)
    throws RemoteException, FFSException;
}


/* Location:           D:\drops\jd\jars\OFX151Callback.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.IOFX151Callback
 * JD-Core Version:    0.7.0.1
 */