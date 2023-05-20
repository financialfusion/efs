/*    1:     */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback;
/*    2:     */ 
/*    3:     */ import com.ffusion.ffs.interfaces.FFSException;
/*    4:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*    5:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRqV1;
/*    6:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRqV2;
/*    7:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRsV1;
/*    8:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRsV2;
/*    9:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctSyncRqV1;
/*   10:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctSyncRqV2;
/*   11:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctSyncRsV1;
/*   12:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctSyncRsV2;
/*   13:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctTrnRqV1;
/*   14:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctTrnRqV2;
/*   15:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctTrnRsV1;
/*   16:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctTrnRsV2;
/*   17:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailSyncRqV1;
/*   18:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailSyncRqV2;
/*   19:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailSyncRsV1;
/*   20:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailSyncRsV2;
/*   21:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailTrnRqV1;
/*   22:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailTrnRqV2;
/*   23:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailTrnRsV1;
/*   24:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMailTrnRsV2;
/*   25:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillTblStructTrnRqV1;
/*   26:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillTblStructTrnRsV1;
/*   27:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtEndTrnRqV1;
/*   28:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtEndTrnRqV2;
/*   29:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtEndTrnRsV1;
/*   30:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtEndTrnRsV2;
/*   31:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRqV1;
/*   32:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRqV2;
/*   33:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRsV1;
/*   34:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRsV2;
/*   35:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChallengeTrnRqV1;
/*   36:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChallengeTrnRqV2;
/*   37:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChallengeTrnRsV1;
/*   38:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChallengeTrnRsV2;
/*   39:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoSyncRqV1;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoSyncRqV2;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoSyncRsV1;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoSyncRsV2;
/*   43:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoTrnRqV1;
/*   44:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoTrnRqV2;
/*   45:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoTrnRsV1;
/*   46:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeChgUserInfoTrnRsV2;
/*   47:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeDisconnectRq;
/*   48:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeDisconnectRs;
/*   49:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRqV1;
/*   50:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRqV2;
/*   51:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRsV1;
/*   52:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRsV2;
/*   53:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeFindBillerTrnRqV1;
/*   54:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeFindBillerTrnRsV1;
/*   55:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeGetMIMETrnRqV1;
/*   56:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeGetMIMETrnRqV2;
/*   57:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeGetMIMETrnRsV1;
/*   58:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeGetMIMETrnRsV2;
/*   59:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterSyncRqV1;
/*   60:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterSyncRqV2;
/*   61:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterSyncRsV1;
/*   62:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterSyncRsV2;
/*   63:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterTrnRqV1;
/*   64:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterTrnRqV2;
/*   65:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterTrnRsV1;
/*   66:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterTrnRsV2;
/*   67:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
/*   68:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV2;
/*   69:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
/*   70:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV2;
/*   71:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
/*   72:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV2;
/*   73:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
/*   74:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV2;
/*   75:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailSyncRqV1;
/*   76:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailSyncRqV2;
/*   77:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailSyncRsV1;
/*   78:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailSyncRsV2;
/*   79:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailTrnRqV1;
/*   80:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailTrnRqV2;
/*   81:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailTrnRsV1;
/*   82:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvMailTrnRsV2;
/*   83:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvStmtTrnRqV1;
/*   84:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvStmtTrnRqV2;
/*   85:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvStmtTrnRsV1;
/*   86:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvStmtTrnRsV2;
/*   87:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailSyncRqV1;
/*   88:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailSyncRqV2;
/*   89:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailSyncRsV1;
/*   90:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailSyncRsV2;
/*   91:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailTrnRqV1;
/*   92:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailTrnRqV2;
/*   93:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailTrnRsV1;
/*   94:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMailTrnRsV2;
/*   95:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMultiInterTrnRqV2;
/*   96:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeMultiInterTrnRsV2;
/*   97:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRequest;
/*   98:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXResponse;
/*   99:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
/*  100:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV2;
/*  101:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
/*  102:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV2;
/*  103:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
/*  104:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV2;
/*  105:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
/*  106:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV2;
/*  107:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRqV1;
/*  108:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRqV2;
/*  109:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRsV1;
/*  110:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRsV2;
/*  111:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1;
/*  112:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV2;
/*  113:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1;
/*  114:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV2;
/*  115:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailSyncRqV1;
/*  116:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailSyncRqV2;
/*  117:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailSyncRsV1;
/*  118:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailSyncRsV2;
/*  119:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailTrnRqV1;
/*  120:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailTrnRqV2;
/*  121:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailTrnRsV1;
/*  122:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtMailTrnRsV2;
/*  123:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
/*  124:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV2;
/*  125:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
/*  126:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV2;
/*  127:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
/*  128:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV2;
/*  129:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
/*  130:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV2;
/*  131:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresDetailTrnRqV1;
/*  132:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresDetailTrnRsV1;
/*  133:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresGrpAcctInfoTrnRqV1;
/*  134:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresGrpAcctInfoTrnRsV1;
/*  135:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresListTrnRqV1;
/*  136:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresListTrnRsV1;
/*  137:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresMailSyncRqV1;
/*  138:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresMailSyncRsV1;
/*  139:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresMailTrnRqV1;
/*  140:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresMailTrnRsV1;
/*  141:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresNotifyTrnRqV1;
/*  142:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePresNotifyTrnRsV1;
/*  143:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeProfTrnRqV1;
/*  144:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeProfTrnRqV2;
/*  145:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeProfTrnRsV1;
/*  146:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeProfTrnRsV2;
/*  147:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterSyncRqV1;
/*  148:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterSyncRqV2;
/*  149:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterSyncRsV1;
/*  150:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterSyncRsV2;
/*  151:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterTrnRqV1;
/*  152:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterTrnRqV2;
/*  153:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterTrnRsV1;
/*  154:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecInterTrnRsV2;
/*  155:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
/*  156:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV2;
/*  157:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
/*  158:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV2;
/*  159:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
/*  160:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV2;
/*  161:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
/*  162:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV2;
/*  163:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
/*  164:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV2;
/*  165:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
/*  166:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV2;
/*  167:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
/*  168:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV2;
/*  169:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
/*  170:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV2;
/*  171:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtSyncRqV1;
/*  172:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtSyncRqV2;
/*  173:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtSyncRsV1;
/*  174:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtSyncRsV2;
/*  175:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtTrnRqV1;
/*  176:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtTrnRqV2;
/*  177:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtTrnRsV1;
/*  178:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecTaxPmtTrnRsV2;
/*  179:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSecListTrnRqV1;
/*  180:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSecListTrnRqV2;
/*  181:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSecListTrnRsV1;
/*  182:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSecListTrnRsV2;
/*  183:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRqV1;
/*  184:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRqV2;
/*  185:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV1;
/*  186:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV2;
/*  187:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStartRq;
/*  188:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStartRs;
/*  189:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtEndTrnRqV1;
/*  190:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtEndTrnRqV2;
/*  191:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtEndTrnRsV1;
/*  192:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtEndTrnRsV2;
/*  193:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRqV1;
/*  194:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRqV2;
/*  195:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRsV1;
/*  196:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRsV2;
/*  197:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStopRq;
/*  198:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStopRs;
/*  199:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkSyncRqV1;
/*  200:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkSyncRqV2;
/*  201:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkSyncRsV1;
/*  202:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkSyncRsV2;
/*  203:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkTrnRqV1;
/*  204:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkTrnRqV2;
/*  205:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkTrnRsV1;
/*  206:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStpChkTrnRsV2;
/*  207:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtSyncRqV1;
/*  208:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtSyncRqV2;
/*  209:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtSyncRsV1;
/*  210:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtSyncRsV2;
/*  211:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtTrnRqV1;
/*  212:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtTrnRqV2;
/*  213:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtTrnRsV1;
/*  214:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTaxPmtTrnRsV2;
/*  215:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireSyncRqV1;
/*  216:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireSyncRqV2;
/*  217:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireSyncRsV1;
/*  218:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireSyncRsV2;
/*  219:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRqV1;
/*  220:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRqV2;
/*  221:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1;
/*  222:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV2;
/*  223:     */ import java.rmi.RemoteException;
/*  224:     */ import javax.ejb.CreateException;
/*  225:     */ import javax.ejb.SessionBean;
/*  226:     */ import javax.ejb.SessionContext;
/*  227:     */ 
/*  228:     */ public class OFX151CallbackBean
/*  229:     */   implements SessionBean
/*  230:     */ {
/*  231:     */   SessionContext _context;
/*  232:     */   
/*  233:     */   public void ejbActivate()
/*  234:     */     throws RemoteException
/*  235:     */   {}
/*  236:     */   
/*  237:     */   public void ejbCreate()
/*  238:     */     throws CreateException, RemoteException, FFSException
/*  239:     */   {}
/*  240:     */   
/*  241:     */   public void ejbPassivate()
/*  242:     */     throws RemoteException
/*  243:     */   {}
/*  244:     */   
/*  245:     */   public void ejbRemove()
/*  246:     */     throws RemoteException
/*  247:     */   {}
/*  248:     */   
/*  249:     */   public TypeAcctInfoTrnRsV1 processAcctInfoTrnRqV1(TypeAcctInfoTrnRqV1 paramTypeAcctInfoTrnRqV1, TypeUserData paramTypeUserData)
/*  250:     */     throws RemoteException, FFSException
/*  251:     */   {
/*  252:  31 */     TypeAcctInfoTrnRsV1 localTypeAcctInfoTrnRsV1 = new TypeAcctInfoTrnRsV1();
/*  253:     */     
/*  254:     */ 
/*  255:     */ 
/*  256:  35 */     return localTypeAcctInfoTrnRsV1;
/*  257:     */   }
/*  258:     */   
/*  259:     */   public TypeAcctInfoTrnRsV2 processAcctInfoTrnRqV2(TypeAcctInfoTrnRqV2 paramTypeAcctInfoTrnRqV2, TypeUserData paramTypeUserData)
/*  260:     */     throws RemoteException, FFSException
/*  261:     */   {
/*  262:  40 */     TypeAcctInfoTrnRsV2 localTypeAcctInfoTrnRsV2 = new TypeAcctInfoTrnRsV2();
/*  263:     */     
/*  264:     */ 
/*  265:     */ 
/*  266:  44 */     return localTypeAcctInfoTrnRsV2;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public TypeAcctSyncRsV1 processAcctSyncRqV1(TypeAcctSyncRqV1 paramTypeAcctSyncRqV1, TypeUserData paramTypeUserData)
/*  270:     */     throws RemoteException, FFSException
/*  271:     */   {
/*  272:  49 */     TypeAcctSyncRsV1 localTypeAcctSyncRsV1 = new TypeAcctSyncRsV1();
/*  273:     */     
/*  274:     */ 
/*  275:     */ 
/*  276:  53 */     return localTypeAcctSyncRsV1;
/*  277:     */   }
/*  278:     */   
/*  279:     */   public TypeAcctSyncRsV2 processAcctSyncRqV2(TypeAcctSyncRqV2 paramTypeAcctSyncRqV2, TypeUserData paramTypeUserData)
/*  280:     */     throws RemoteException, FFSException
/*  281:     */   {
/*  282:  58 */     TypeAcctSyncRsV2 localTypeAcctSyncRsV2 = new TypeAcctSyncRsV2();
/*  283:     */     
/*  284:     */ 
/*  285:     */ 
/*  286:  62 */     return localTypeAcctSyncRsV2;
/*  287:     */   }
/*  288:     */   
/*  289:     */   public TypeAcctTrnRsV1 processAcctTrnRqV1(TypeAcctTrnRqV1 paramTypeAcctTrnRqV1, TypeUserData paramTypeUserData)
/*  290:     */     throws RemoteException, FFSException
/*  291:     */   {
/*  292:  67 */     TypeAcctTrnRsV1 localTypeAcctTrnRsV1 = new TypeAcctTrnRsV1();
/*  293:     */     
/*  294:     */ 
/*  295:     */ 
/*  296:  71 */     return localTypeAcctTrnRsV1;
/*  297:     */   }
/*  298:     */   
/*  299:     */   public TypeAcctTrnRsV2 processAcctTrnRqV2(TypeAcctTrnRqV2 paramTypeAcctTrnRqV2, TypeUserData paramTypeUserData)
/*  300:     */     throws RemoteException, FFSException
/*  301:     */   {
/*  302:  76 */     TypeAcctTrnRsV2 localTypeAcctTrnRsV2 = new TypeAcctTrnRsV2();
/*  303:     */     
/*  304:     */ 
/*  305:     */ 
/*  306:  80 */     return localTypeAcctTrnRsV2;
/*  307:     */   }
/*  308:     */   
/*  309:     */   public TypeBankMailSyncRsV1 processBankMailSyncRqV1(TypeBankMailSyncRqV1 paramTypeBankMailSyncRqV1, TypeUserData paramTypeUserData)
/*  310:     */     throws RemoteException, FFSException
/*  311:     */   {
/*  312:  85 */     TypeBankMailSyncRsV1 localTypeBankMailSyncRsV1 = new TypeBankMailSyncRsV1();
/*  313:     */     
/*  314:     */ 
/*  315:     */ 
/*  316:  89 */     return localTypeBankMailSyncRsV1;
/*  317:     */   }
/*  318:     */   
/*  319:     */   public TypeBankMailSyncRsV2 processBankMailSyncRqV2(TypeBankMailSyncRqV2 paramTypeBankMailSyncRqV2, TypeUserData paramTypeUserData)
/*  320:     */     throws RemoteException, FFSException
/*  321:     */   {
/*  322:  94 */     TypeBankMailSyncRsV2 localTypeBankMailSyncRsV2 = new TypeBankMailSyncRsV2();
/*  323:     */     
/*  324:     */ 
/*  325:     */ 
/*  326:  98 */     return localTypeBankMailSyncRsV2;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public TypeBankMailTrnRsV1 processBankMailTrnRqV1(TypeBankMailTrnRqV1 paramTypeBankMailTrnRqV1, TypeUserData paramTypeUserData)
/*  330:     */     throws RemoteException, FFSException
/*  331:     */   {
/*  332: 103 */     TypeBankMailTrnRsV1 localTypeBankMailTrnRsV1 = new TypeBankMailTrnRsV1();
/*  333:     */     
/*  334:     */ 
/*  335:     */ 
/*  336: 107 */     return localTypeBankMailTrnRsV1;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public TypeBankMailTrnRsV2 processBankMailTrnRqV2(TypeBankMailTrnRqV2 paramTypeBankMailTrnRqV2, TypeUserData paramTypeUserData)
/*  340:     */     throws RemoteException, FFSException
/*  341:     */   {
/*  342: 112 */     TypeBankMailTrnRsV2 localTypeBankMailTrnRsV2 = new TypeBankMailTrnRsV2();
/*  343:     */     
/*  344:     */ 
/*  345:     */ 
/*  346: 116 */     return localTypeBankMailTrnRsV2;
/*  347:     */   }
/*  348:     */   
/*  349:     */   public TypeBillTblStructTrnRsV1 processBillTblStructTrnRqV1(TypeBillTblStructTrnRqV1 paramTypeBillTblStructTrnRqV1, TypeUserData paramTypeUserData)
/*  350:     */     throws RemoteException, FFSException
/*  351:     */   {
/*  352: 121 */     TypeBillTblStructTrnRsV1 localTypeBillTblStructTrnRsV1 = new TypeBillTblStructTrnRsV1();
/*  353:     */     
/*  354:     */ 
/*  355:     */ 
/*  356: 125 */     return localTypeBillTblStructTrnRsV1;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public TypeCCStmtEndTrnRsV1 processCCStmtEndTrnRqV1(TypeCCStmtEndTrnRqV1 paramTypeCCStmtEndTrnRqV1, TypeUserData paramTypeUserData)
/*  360:     */     throws RemoteException, FFSException
/*  361:     */   {
/*  362: 130 */     TypeCCStmtEndTrnRsV1 localTypeCCStmtEndTrnRsV1 = new TypeCCStmtEndTrnRsV1();
/*  363:     */     
/*  364:     */ 
/*  365:     */ 
/*  366: 134 */     return localTypeCCStmtEndTrnRsV1;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public TypeCCStmtEndTrnRsV2 processCCStmtEndTrnRqV2(TypeCCStmtEndTrnRqV2 paramTypeCCStmtEndTrnRqV2, TypeUserData paramTypeUserData)
/*  370:     */     throws RemoteException, FFSException
/*  371:     */   {
/*  372: 139 */     TypeCCStmtEndTrnRsV2 localTypeCCStmtEndTrnRsV2 = new TypeCCStmtEndTrnRsV2();
/*  373:     */     
/*  374:     */ 
/*  375:     */ 
/*  376: 143 */     return localTypeCCStmtEndTrnRsV2;
/*  377:     */   }
/*  378:     */   
/*  379:     */   public TypeCCStmtTrnRsV1 processCCStmtTrnRqV1(TypeCCStmtTrnRqV1 paramTypeCCStmtTrnRqV1, TypeUserData paramTypeUserData)
/*  380:     */     throws RemoteException, FFSException
/*  381:     */   {
/*  382: 148 */     TypeCCStmtTrnRsV1 localTypeCCStmtTrnRsV1 = new TypeCCStmtTrnRsV1();
/*  383:     */     
/*  384:     */ 
/*  385:     */ 
/*  386: 152 */     return localTypeCCStmtTrnRsV1;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public TypeCCStmtTrnRsV2 processCCStmtTrnRqV2(TypeCCStmtTrnRqV2 paramTypeCCStmtTrnRqV2, TypeUserData paramTypeUserData)
/*  390:     */     throws RemoteException, FFSException
/*  391:     */   {
/*  392: 157 */     TypeCCStmtTrnRsV2 localTypeCCStmtTrnRsV2 = new TypeCCStmtTrnRsV2();
/*  393:     */     
/*  394:     */ 
/*  395:     */ 
/*  396: 161 */     return localTypeCCStmtTrnRsV2;
/*  397:     */   }
/*  398:     */   
/*  399:     */   public TypeChallengeTrnRsV1 processChallengeTrnRqV1(TypeChallengeTrnRqV1 paramTypeChallengeTrnRqV1, TypeUserData paramTypeUserData)
/*  400:     */     throws RemoteException, FFSException
/*  401:     */   {
/*  402: 166 */     TypeChallengeTrnRsV1 localTypeChallengeTrnRsV1 = new TypeChallengeTrnRsV1();
/*  403:     */     
/*  404:     */ 
/*  405:     */ 
/*  406: 170 */     return localTypeChallengeTrnRsV1;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public TypeChallengeTrnRsV2 processChallengeTrnRqV2(TypeChallengeTrnRqV2 paramTypeChallengeTrnRqV2, TypeUserData paramTypeUserData)
/*  410:     */     throws RemoteException, FFSException
/*  411:     */   {
/*  412: 175 */     TypeChallengeTrnRsV2 localTypeChallengeTrnRsV2 = new TypeChallengeTrnRsV2();
/*  413:     */     
/*  414:     */ 
/*  415:     */ 
/*  416: 179 */     return localTypeChallengeTrnRsV2;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public TypeChgUserInfoSyncRsV1 processChgUserInfoSyncRqV1(TypeChgUserInfoSyncRqV1 paramTypeChgUserInfoSyncRqV1, TypeUserData paramTypeUserData)
/*  420:     */     throws RemoteException, FFSException
/*  421:     */   {
/*  422: 184 */     TypeChgUserInfoSyncRsV1 localTypeChgUserInfoSyncRsV1 = new TypeChgUserInfoSyncRsV1();
/*  423:     */     
/*  424:     */ 
/*  425:     */ 
/*  426: 188 */     return localTypeChgUserInfoSyncRsV1;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public TypeChgUserInfoSyncRsV2 processChgUserInfoSyncRqV2(TypeChgUserInfoSyncRqV2 paramTypeChgUserInfoSyncRqV2, TypeUserData paramTypeUserData)
/*  430:     */     throws RemoteException, FFSException
/*  431:     */   {
/*  432: 193 */     TypeChgUserInfoSyncRsV2 localTypeChgUserInfoSyncRsV2 = new TypeChgUserInfoSyncRsV2();
/*  433:     */     
/*  434:     */ 
/*  435:     */ 
/*  436: 197 */     return localTypeChgUserInfoSyncRsV2;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public TypeChgUserInfoTrnRsV1 processChgUserInfoTrnRqV1(TypeChgUserInfoTrnRqV1 paramTypeChgUserInfoTrnRqV1, TypeUserData paramTypeUserData)
/*  440:     */     throws RemoteException, FFSException
/*  441:     */   {
/*  442: 202 */     TypeChgUserInfoTrnRsV1 localTypeChgUserInfoTrnRsV1 = new TypeChgUserInfoTrnRsV1();
/*  443:     */     
/*  444:     */ 
/*  445:     */ 
/*  446: 206 */     return localTypeChgUserInfoTrnRsV1;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public TypeChgUserInfoTrnRsV2 processChgUserInfoTrnRqV2(TypeChgUserInfoTrnRqV2 paramTypeChgUserInfoTrnRqV2, TypeUserData paramTypeUserData)
/*  450:     */     throws RemoteException, FFSException
/*  451:     */   {
/*  452: 211 */     TypeChgUserInfoTrnRsV2 localTypeChgUserInfoTrnRsV2 = new TypeChgUserInfoTrnRsV2();
/*  453:     */     
/*  454:     */ 
/*  455:     */ 
/*  456: 215 */     return localTypeChgUserInfoTrnRsV2;
/*  457:     */   }
/*  458:     */   
/*  459:     */   public TypeDisconnectRs processDisconnectRq(TypeDisconnectRq paramTypeDisconnectRq, TypeUserData paramTypeUserData)
/*  460:     */     throws RemoteException, FFSException
/*  461:     */   {
/*  462: 220 */     TypeDisconnectRs localTypeDisconnectRs = new TypeDisconnectRs();
/*  463:     */     
/*  464:     */ 
/*  465:     */ 
/*  466: 224 */     return localTypeDisconnectRs;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public TypeEnrollTrnRsV1 processEnrollTrnRqV1(TypeEnrollTrnRqV1 paramTypeEnrollTrnRqV1, TypeUserData paramTypeUserData)
/*  470:     */     throws RemoteException, FFSException
/*  471:     */   {
/*  472: 229 */     TypeEnrollTrnRsV1 localTypeEnrollTrnRsV1 = new TypeEnrollTrnRsV1();
/*  473:     */     
/*  474:     */ 
/*  475:     */ 
/*  476: 233 */     return localTypeEnrollTrnRsV1;
/*  477:     */   }
/*  478:     */   
/*  479:     */   public TypeEnrollTrnRsV2 processEnrollTrnRqV2(TypeEnrollTrnRqV2 paramTypeEnrollTrnRqV2, TypeUserData paramTypeUserData)
/*  480:     */     throws RemoteException, FFSException
/*  481:     */   {
/*  482: 238 */     TypeEnrollTrnRsV2 localTypeEnrollTrnRsV2 = new TypeEnrollTrnRsV2();
/*  483:     */     
/*  484:     */ 
/*  485:     */ 
/*  486: 242 */     return localTypeEnrollTrnRsV2;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public TypeFindBillerTrnRsV1 processFindBillerTrnRqV1(TypeFindBillerTrnRqV1 paramTypeFindBillerTrnRqV1, TypeUserData paramTypeUserData)
/*  490:     */     throws RemoteException, FFSException
/*  491:     */   {
/*  492: 247 */     TypeFindBillerTrnRsV1 localTypeFindBillerTrnRsV1 = new TypeFindBillerTrnRsV1();
/*  493:     */     
/*  494:     */ 
/*  495:     */ 
/*  496: 251 */     return localTypeFindBillerTrnRsV1;
/*  497:     */   }
/*  498:     */   
/*  499:     */   public TypeGetMIMETrnRsV1 processGetMIMETrnRqV1(TypeGetMIMETrnRqV1 paramTypeGetMIMETrnRqV1, TypeUserData paramTypeUserData)
/*  500:     */     throws RemoteException, FFSException
/*  501:     */   {
/*  502: 256 */     TypeGetMIMETrnRsV1 localTypeGetMIMETrnRsV1 = new TypeGetMIMETrnRsV1();
/*  503:     */     
/*  504:     */ 
/*  505:     */ 
/*  506: 260 */     return localTypeGetMIMETrnRsV1;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public TypeGetMIMETrnRsV2 processGetMIMETrnRqV2(TypeGetMIMETrnRqV2 paramTypeGetMIMETrnRqV2, TypeUserData paramTypeUserData)
/*  510:     */     throws RemoteException, FFSException
/*  511:     */   {
/*  512: 265 */     TypeGetMIMETrnRsV2 localTypeGetMIMETrnRsV2 = new TypeGetMIMETrnRsV2();
/*  513:     */     
/*  514:     */ 
/*  515:     */ 
/*  516: 269 */     return localTypeGetMIMETrnRsV2;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public TypeInterSyncRsV1 processInterSyncRqV1(TypeInterSyncRqV1 paramTypeInterSyncRqV1, TypeUserData paramTypeUserData)
/*  520:     */     throws RemoteException, FFSException
/*  521:     */   {
/*  522: 274 */     TypeInterSyncRsV1 localTypeInterSyncRsV1 = new TypeInterSyncRsV1();
/*  523:     */     
/*  524:     */ 
/*  525:     */ 
/*  526: 278 */     return localTypeInterSyncRsV1;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public TypeInterSyncRsV2 processInterSyncRqV2(TypeInterSyncRqV2 paramTypeInterSyncRqV2, TypeUserData paramTypeUserData)
/*  530:     */     throws RemoteException, FFSException
/*  531:     */   {
/*  532: 283 */     TypeInterSyncRsV2 localTypeInterSyncRsV2 = new TypeInterSyncRsV2();
/*  533:     */     
/*  534:     */ 
/*  535:     */ 
/*  536: 287 */     return localTypeInterSyncRsV2;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public TypeInterTrnRsV1 processInterTrnRqV1(TypeInterTrnRqV1 paramTypeInterTrnRqV1, TypeUserData paramTypeUserData)
/*  540:     */     throws RemoteException, FFSException
/*  541:     */   {
/*  542: 292 */     TypeInterTrnRsV1 localTypeInterTrnRsV1 = new TypeInterTrnRsV1();
/*  543:     */     
/*  544:     */ 
/*  545:     */ 
/*  546: 296 */     return localTypeInterTrnRsV1;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public TypeInterTrnRsV2 processInterTrnRqV2(TypeInterTrnRqV2 paramTypeInterTrnRqV2, TypeUserData paramTypeUserData)
/*  550:     */     throws RemoteException, FFSException
/*  551:     */   {
/*  552: 301 */     TypeInterTrnRsV2 localTypeInterTrnRsV2 = new TypeInterTrnRsV2();
/*  553:     */     
/*  554:     */ 
/*  555:     */ 
/*  556: 305 */     return localTypeInterTrnRsV2;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
/*  560:     */     throws RemoteException, FFSException
/*  561:     */   {
/*  562: 310 */     TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
/*  563:     */     
/*  564:     */ 
/*  565:     */ 
/*  566: 314 */     return localTypeIntraSyncRsV1;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public TypeIntraSyncRsV2 processIntraSyncRqV2(TypeIntraSyncRqV2 paramTypeIntraSyncRqV2, TypeUserData paramTypeUserData)
/*  570:     */     throws RemoteException, FFSException
/*  571:     */   {
/*  572: 319 */     TypeIntraSyncRsV2 localTypeIntraSyncRsV2 = new TypeIntraSyncRsV2();
/*  573:     */     
/*  574:     */ 
/*  575:     */ 
/*  576: 323 */     return localTypeIntraSyncRsV2;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
/*  580:     */     throws RemoteException, FFSException
/*  581:     */   {
/*  582: 328 */     TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = new TypeIntraTrnRsV1();
/*  583:     */     
/*  584:     */ 
/*  585:     */ 
/*  586: 332 */     return localTypeIntraTrnRsV1;
/*  587:     */   }
/*  588:     */   
/*  589:     */   public TypeIntraTrnRsV2 processIntraTrnRqV2(TypeIntraTrnRqV2 paramTypeIntraTrnRqV2, TypeUserData paramTypeUserData)
/*  590:     */     throws RemoteException, FFSException
/*  591:     */   {
/*  592: 337 */     TypeIntraTrnRsV2 localTypeIntraTrnRsV2 = new TypeIntraTrnRsV2();
/*  593:     */     
/*  594:     */ 
/*  595:     */ 
/*  596: 341 */     return localTypeIntraTrnRsV2;
/*  597:     */   }
/*  598:     */   
/*  599:     */   public TypeInvMailSyncRsV1 processInvMailSyncRqV1(TypeInvMailSyncRqV1 paramTypeInvMailSyncRqV1, TypeUserData paramTypeUserData)
/*  600:     */     throws RemoteException, FFSException
/*  601:     */   {
/*  602: 346 */     TypeInvMailSyncRsV1 localTypeInvMailSyncRsV1 = new TypeInvMailSyncRsV1();
/*  603:     */     
/*  604:     */ 
/*  605:     */ 
/*  606: 350 */     return localTypeInvMailSyncRsV1;
/*  607:     */   }
/*  608:     */   
/*  609:     */   public TypeInvMailSyncRsV2 processInvMailSyncRqV2(TypeInvMailSyncRqV2 paramTypeInvMailSyncRqV2, TypeUserData paramTypeUserData)
/*  610:     */     throws RemoteException, FFSException
/*  611:     */   {
/*  612: 355 */     TypeInvMailSyncRsV2 localTypeInvMailSyncRsV2 = new TypeInvMailSyncRsV2();
/*  613:     */     
/*  614:     */ 
/*  615:     */ 
/*  616: 359 */     return localTypeInvMailSyncRsV2;
/*  617:     */   }
/*  618:     */   
/*  619:     */   public TypeInvMailTrnRsV1 processInvMailTrnRqV1(TypeInvMailTrnRqV1 paramTypeInvMailTrnRqV1, TypeUserData paramTypeUserData)
/*  620:     */     throws RemoteException, FFSException
/*  621:     */   {
/*  622: 364 */     TypeInvMailTrnRsV1 localTypeInvMailTrnRsV1 = new TypeInvMailTrnRsV1();
/*  623:     */     
/*  624:     */ 
/*  625:     */ 
/*  626: 368 */     return localTypeInvMailTrnRsV1;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public TypeInvMailTrnRsV2 processInvMailTrnRqV2(TypeInvMailTrnRqV2 paramTypeInvMailTrnRqV2, TypeUserData paramTypeUserData)
/*  630:     */     throws RemoteException, FFSException
/*  631:     */   {
/*  632: 373 */     TypeInvMailTrnRsV2 localTypeInvMailTrnRsV2 = new TypeInvMailTrnRsV2();
/*  633:     */     
/*  634:     */ 
/*  635:     */ 
/*  636: 377 */     return localTypeInvMailTrnRsV2;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public TypeInvStmtTrnRsV1 processInvStmtTrnRqV1(TypeInvStmtTrnRqV1 paramTypeInvStmtTrnRqV1, TypeUserData paramTypeUserData)
/*  640:     */     throws RemoteException, FFSException
/*  641:     */   {
/*  642: 382 */     TypeInvStmtTrnRsV1 localTypeInvStmtTrnRsV1 = new TypeInvStmtTrnRsV1();
/*  643:     */     
/*  644:     */ 
/*  645:     */ 
/*  646: 386 */     return localTypeInvStmtTrnRsV1;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public TypeInvStmtTrnRsV2 processInvStmtTrnRqV2(TypeInvStmtTrnRqV2 paramTypeInvStmtTrnRqV2, TypeUserData paramTypeUserData)
/*  650:     */     throws RemoteException, FFSException
/*  651:     */   {
/*  652: 391 */     TypeInvStmtTrnRsV2 localTypeInvStmtTrnRsV2 = new TypeInvStmtTrnRsV2();
/*  653:     */     
/*  654:     */ 
/*  655:     */ 
/*  656: 395 */     return localTypeInvStmtTrnRsV2;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public TypeMailSyncRsV1 processMailSyncRqV1(TypeMailSyncRqV1 paramTypeMailSyncRqV1, TypeUserData paramTypeUserData)
/*  660:     */     throws RemoteException, FFSException
/*  661:     */   {
/*  662: 400 */     TypeMailSyncRsV1 localTypeMailSyncRsV1 = new TypeMailSyncRsV1();
/*  663:     */     
/*  664:     */ 
/*  665:     */ 
/*  666: 404 */     return localTypeMailSyncRsV1;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public TypeMailSyncRsV2 processMailSyncRqV2(TypeMailSyncRqV2 paramTypeMailSyncRqV2, TypeUserData paramTypeUserData)
/*  670:     */     throws RemoteException, FFSException
/*  671:     */   {
/*  672: 409 */     TypeMailSyncRsV2 localTypeMailSyncRsV2 = new TypeMailSyncRsV2();
/*  673:     */     
/*  674:     */ 
/*  675:     */ 
/*  676: 413 */     return localTypeMailSyncRsV2;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public TypeMailTrnRsV1 processMailTrnRqV1(TypeMailTrnRqV1 paramTypeMailTrnRqV1, TypeUserData paramTypeUserData)
/*  680:     */     throws RemoteException, FFSException
/*  681:     */   {
/*  682: 418 */     TypeMailTrnRsV1 localTypeMailTrnRsV1 = new TypeMailTrnRsV1();
/*  683:     */     
/*  684:     */ 
/*  685:     */ 
/*  686: 422 */     return localTypeMailTrnRsV1;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public TypeMailTrnRsV2 processMailTrnRqV2(TypeMailTrnRqV2 paramTypeMailTrnRqV2, TypeUserData paramTypeUserData)
/*  690:     */     throws RemoteException, FFSException
/*  691:     */   {
/*  692: 427 */     TypeMailTrnRsV2 localTypeMailTrnRsV2 = new TypeMailTrnRsV2();
/*  693:     */     
/*  694:     */ 
/*  695:     */ 
/*  696: 431 */     return localTypeMailTrnRsV2;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public TypeMultiInterTrnRsV2 processMultiInterTrnRqV2(TypeMultiInterTrnRqV2 paramTypeMultiInterTrnRqV2, TypeUserData paramTypeUserData)
/*  700:     */     throws RemoteException, FFSException
/*  701:     */   {
/*  702: 436 */     TypeMultiInterTrnRsV2 localTypeMultiInterTrnRsV2 = new TypeMultiInterTrnRsV2();
/*  703:     */     
/*  704:     */ 
/*  705:     */ 
/*  706: 440 */     return localTypeMultiInterTrnRsV2;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public TypeOFXResponse processOFXRequest(TypeOFXRequest paramTypeOFXRequest, TypeUserData paramTypeUserData)
/*  710:     */     throws RemoteException, FFSException
/*  711:     */   {
/*  712: 445 */     TypeOFXResponse localTypeOFXResponse = new TypeOFXResponse();
/*  713:     */     
/*  714:     */ 
/*  715:     */ 
/*  716: 449 */     return localTypeOFXResponse;
/*  717:     */   }
/*  718:     */   
/*  719:     */   public TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
/*  720:     */     throws RemoteException, FFSException
/*  721:     */   {
/*  722: 454 */     TypePayeeSyncRsV1 localTypePayeeSyncRsV1 = new TypePayeeSyncRsV1();
/*  723:     */     
/*  724:     */ 
/*  725:     */ 
/*  726: 458 */     return localTypePayeeSyncRsV1;
/*  727:     */   }
/*  728:     */   
/*  729:     */   public TypePayeeSyncRsV2 processPayeeSyncRqV2(TypePayeeSyncRqV2 paramTypePayeeSyncRqV2, TypeUserData paramTypeUserData)
/*  730:     */     throws RemoteException, FFSException
/*  731:     */   {
/*  732: 463 */     TypePayeeSyncRsV2 localTypePayeeSyncRsV2 = new TypePayeeSyncRsV2();
/*  733:     */     
/*  734:     */ 
/*  735:     */ 
/*  736: 467 */     return localTypePayeeSyncRsV2;
/*  737:     */   }
/*  738:     */   
/*  739:     */   public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
/*  740:     */     throws RemoteException, FFSException
/*  741:     */   {
/*  742: 472 */     TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = new TypePayeeTrnRsV1();
/*  743:     */     
/*  744:     */ 
/*  745:     */ 
/*  746: 476 */     return localTypePayeeTrnRsV1;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public TypePayeeTrnRsV2 processPayeeTrnRqV2(TypePayeeTrnRqV2 paramTypePayeeTrnRqV2, TypeUserData paramTypeUserData)
/*  750:     */     throws RemoteException, FFSException
/*  751:     */   {
/*  752: 481 */     TypePayeeTrnRsV2 localTypePayeeTrnRsV2 = new TypePayeeTrnRsV2();
/*  753:     */     
/*  754:     */ 
/*  755:     */ 
/*  756: 485 */     return localTypePayeeTrnRsV2;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public TypePinChTrnRsV1 processPinChTrnRqV1(TypePinChTrnRqV1 paramTypePinChTrnRqV1, TypeUserData paramTypeUserData)
/*  760:     */     throws RemoteException, FFSException
/*  761:     */   {
/*  762: 490 */     TypePinChTrnRsV1 localTypePinChTrnRsV1 = new TypePinChTrnRsV1();
/*  763:     */     
/*  764:     */ 
/*  765:     */ 
/*  766: 494 */     return localTypePinChTrnRsV1;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public TypePinChTrnRsV2 processPinChTrnRqV2(TypePinChTrnRqV2 paramTypePinChTrnRqV2, TypeUserData paramTypeUserData)
/*  770:     */     throws RemoteException, FFSException
/*  771:     */   {
/*  772: 499 */     TypePinChTrnRsV2 localTypePinChTrnRsV2 = new TypePinChTrnRsV2();
/*  773:     */     
/*  774:     */ 
/*  775:     */ 
/*  776: 503 */     return localTypePinChTrnRsV2;
/*  777:     */   }
/*  778:     */   
/*  779:     */   public TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
/*  780:     */     throws RemoteException, FFSException
/*  781:     */   {
/*  782: 508 */     TypePmtInqTrnRsV1 localTypePmtInqTrnRsV1 = new TypePmtInqTrnRsV1();
/*  783:     */     
/*  784:     */ 
/*  785:     */ 
/*  786: 512 */     return localTypePmtInqTrnRsV1;
/*  787:     */   }
/*  788:     */   
/*  789:     */   public TypePmtInqTrnRsV2 processPmtInqTrnRqV2(TypePmtInqTrnRqV2 paramTypePmtInqTrnRqV2, TypeUserData paramTypeUserData)
/*  790:     */     throws RemoteException, FFSException
/*  791:     */   {
/*  792: 517 */     TypePmtInqTrnRsV2 localTypePmtInqTrnRsV2 = new TypePmtInqTrnRsV2();
/*  793:     */     
/*  794:     */ 
/*  795:     */ 
/*  796: 521 */     return localTypePmtInqTrnRsV2;
/*  797:     */   }
/*  798:     */   
/*  799:     */   public TypePmtMailSyncRsV1 processPmtMailSyncRqV1(TypePmtMailSyncRqV1 paramTypePmtMailSyncRqV1, TypeUserData paramTypeUserData)
/*  800:     */     throws RemoteException, FFSException
/*  801:     */   {
/*  802: 526 */     TypePmtMailSyncRsV1 localTypePmtMailSyncRsV1 = new TypePmtMailSyncRsV1();
/*  803:     */     
/*  804:     */ 
/*  805:     */ 
/*  806: 530 */     return localTypePmtMailSyncRsV1;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public TypePmtMailSyncRsV2 processPmtMailSyncRqV2(TypePmtMailSyncRqV2 paramTypePmtMailSyncRqV2, TypeUserData paramTypeUserData)
/*  810:     */     throws RemoteException, FFSException
/*  811:     */   {
/*  812: 535 */     TypePmtMailSyncRsV2 localTypePmtMailSyncRsV2 = new TypePmtMailSyncRsV2();
/*  813:     */     
/*  814:     */ 
/*  815:     */ 
/*  816: 539 */     return localTypePmtMailSyncRsV2;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public TypePmtMailTrnRsV1 processPmtMailTrnRqV1(TypePmtMailTrnRqV1 paramTypePmtMailTrnRqV1, TypeUserData paramTypeUserData)
/*  820:     */     throws RemoteException, FFSException
/*  821:     */   {
/*  822: 544 */     TypePmtMailTrnRsV1 localTypePmtMailTrnRsV1 = new TypePmtMailTrnRsV1();
/*  823:     */     
/*  824:     */ 
/*  825:     */ 
/*  826: 548 */     return localTypePmtMailTrnRsV1;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public TypePmtMailTrnRsV2 processPmtMailTrnRqV2(TypePmtMailTrnRqV2 paramTypePmtMailTrnRqV2, TypeUserData paramTypeUserData)
/*  830:     */     throws RemoteException, FFSException
/*  831:     */   {
/*  832: 553 */     TypePmtMailTrnRsV2 localTypePmtMailTrnRsV2 = new TypePmtMailTrnRsV2();
/*  833:     */     
/*  834:     */ 
/*  835:     */ 
/*  836: 557 */     return localTypePmtMailTrnRsV2;
/*  837:     */   }
/*  838:     */   
/*  839:     */   public TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
/*  840:     */     throws RemoteException, FFSException
/*  841:     */   {
/*  842: 562 */     TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
/*  843:     */     
/*  844:     */ 
/*  845:     */ 
/*  846: 566 */     return localTypePmtSyncRsV1;
/*  847:     */   }
/*  848:     */   
/*  849:     */   public TypePmtSyncRsV2 processPmtSyncRqV2(TypePmtSyncRqV2 paramTypePmtSyncRqV2, TypeUserData paramTypeUserData)
/*  850:     */     throws RemoteException, FFSException
/*  851:     */   {
/*  852: 571 */     TypePmtSyncRsV2 localTypePmtSyncRsV2 = new TypePmtSyncRsV2();
/*  853:     */     
/*  854:     */ 
/*  855:     */ 
/*  856: 575 */     return localTypePmtSyncRsV2;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
/*  860:     */     throws RemoteException, FFSException
/*  861:     */   {
/*  862: 580 */     TypePmtTrnRsV1 localTypePmtTrnRsV1 = new TypePmtTrnRsV1();
/*  863:     */     
/*  864:     */ 
/*  865:     */ 
/*  866: 584 */     return localTypePmtTrnRsV1;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public TypePmtTrnRsV2 processPmtTrnRqV2(TypePmtTrnRqV2 paramTypePmtTrnRqV2, TypeUserData paramTypeUserData)
/*  870:     */     throws RemoteException, FFSException
/*  871:     */   {
/*  872: 589 */     TypePmtTrnRsV2 localTypePmtTrnRsV2 = new TypePmtTrnRsV2();
/*  873:     */     
/*  874:     */ 
/*  875:     */ 
/*  876: 593 */     return localTypePmtTrnRsV2;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public TypePresDetailTrnRsV1 processPresDetailTrnRqV1(TypePresDetailTrnRqV1 paramTypePresDetailTrnRqV1, TypeUserData paramTypeUserData)
/*  880:     */     throws RemoteException, FFSException
/*  881:     */   {
/*  882: 598 */     TypePresDetailTrnRsV1 localTypePresDetailTrnRsV1 = new TypePresDetailTrnRsV1();
/*  883:     */     
/*  884:     */ 
/*  885:     */ 
/*  886: 602 */     return localTypePresDetailTrnRsV1;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public TypePresGrpAcctInfoTrnRsV1 processPresGrpAcctInfoTrnRqV1(TypePresGrpAcctInfoTrnRqV1 paramTypePresGrpAcctInfoTrnRqV1, TypeUserData paramTypeUserData)
/*  890:     */     throws RemoteException, FFSException
/*  891:     */   {
/*  892: 607 */     TypePresGrpAcctInfoTrnRsV1 localTypePresGrpAcctInfoTrnRsV1 = new TypePresGrpAcctInfoTrnRsV1();
/*  893:     */     
/*  894:     */ 
/*  895:     */ 
/*  896: 611 */     return localTypePresGrpAcctInfoTrnRsV1;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public TypePresListTrnRsV1 processPresListTrnRqV1(TypePresListTrnRqV1 paramTypePresListTrnRqV1, TypeUserData paramTypeUserData)
/*  900:     */     throws RemoteException, FFSException
/*  901:     */   {
/*  902: 616 */     TypePresListTrnRsV1 localTypePresListTrnRsV1 = new TypePresListTrnRsV1();
/*  903:     */     
/*  904:     */ 
/*  905:     */ 
/*  906: 620 */     return localTypePresListTrnRsV1;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public TypePresMailSyncRsV1 processPresMailSyncRqV1(TypePresMailSyncRqV1 paramTypePresMailSyncRqV1, TypeUserData paramTypeUserData)
/*  910:     */     throws RemoteException, FFSException
/*  911:     */   {
/*  912: 625 */     TypePresMailSyncRsV1 localTypePresMailSyncRsV1 = new TypePresMailSyncRsV1();
/*  913:     */     
/*  914:     */ 
/*  915:     */ 
/*  916: 629 */     return localTypePresMailSyncRsV1;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public TypePresMailTrnRsV1 processPresMailTrnRqV1(TypePresMailTrnRqV1 paramTypePresMailTrnRqV1, TypeUserData paramTypeUserData)
/*  920:     */     throws RemoteException, FFSException
/*  921:     */   {
/*  922: 634 */     TypePresMailTrnRsV1 localTypePresMailTrnRsV1 = new TypePresMailTrnRsV1();
/*  923:     */     
/*  924:     */ 
/*  925:     */ 
/*  926: 638 */     return localTypePresMailTrnRsV1;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public TypePresNotifyTrnRsV1 processPresNotifyTrnRqV1(TypePresNotifyTrnRqV1 paramTypePresNotifyTrnRqV1, TypeUserData paramTypeUserData)
/*  930:     */     throws RemoteException, FFSException
/*  931:     */   {
/*  932: 643 */     TypePresNotifyTrnRsV1 localTypePresNotifyTrnRsV1 = new TypePresNotifyTrnRsV1();
/*  933:     */     
/*  934:     */ 
/*  935:     */ 
/*  936: 647 */     return localTypePresNotifyTrnRsV1;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public TypeProfTrnRsV1 processProfTrnRqV1(TypeProfTrnRqV1 paramTypeProfTrnRqV1, TypeUserData paramTypeUserData)
/*  940:     */     throws RemoteException, FFSException
/*  941:     */   {
/*  942: 652 */     TypeProfTrnRsV1 localTypeProfTrnRsV1 = new TypeProfTrnRsV1();
/*  943:     */     
/*  944:     */ 
/*  945:     */ 
/*  946: 656 */     return localTypeProfTrnRsV1;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public TypeProfTrnRsV2 processProfTrnRqV2(TypeProfTrnRqV2 paramTypeProfTrnRqV2, TypeUserData paramTypeUserData)
/*  950:     */     throws RemoteException, FFSException
/*  951:     */   {
/*  952: 661 */     TypeProfTrnRsV2 localTypeProfTrnRsV2 = new TypeProfTrnRsV2();
/*  953:     */     
/*  954:     */ 
/*  955:     */ 
/*  956: 665 */     return localTypeProfTrnRsV2;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public TypeRecInterSyncRsV1 processRecInterSyncRqV1(TypeRecInterSyncRqV1 paramTypeRecInterSyncRqV1, TypeUserData paramTypeUserData)
/*  960:     */     throws RemoteException, FFSException
/*  961:     */   {
/*  962: 670 */     TypeRecInterSyncRsV1 localTypeRecInterSyncRsV1 = new TypeRecInterSyncRsV1();
/*  963:     */     
/*  964:     */ 
/*  965:     */ 
/*  966: 674 */     return localTypeRecInterSyncRsV1;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public TypeRecInterSyncRsV2 processRecInterSyncRqV2(TypeRecInterSyncRqV2 paramTypeRecInterSyncRqV2, TypeUserData paramTypeUserData)
/*  970:     */     throws RemoteException, FFSException
/*  971:     */   {
/*  972: 679 */     TypeRecInterSyncRsV2 localTypeRecInterSyncRsV2 = new TypeRecInterSyncRsV2();
/*  973:     */     
/*  974:     */ 
/*  975:     */ 
/*  976: 683 */     return localTypeRecInterSyncRsV2;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public TypeRecInterTrnRsV1 processRecInterTrnRqV1(TypeRecInterTrnRqV1 paramTypeRecInterTrnRqV1, TypeUserData paramTypeUserData)
/*  980:     */     throws RemoteException, FFSException
/*  981:     */   {
/*  982: 688 */     TypeRecInterTrnRsV1 localTypeRecInterTrnRsV1 = new TypeRecInterTrnRsV1();
/*  983:     */     
/*  984:     */ 
/*  985:     */ 
/*  986: 692 */     return localTypeRecInterTrnRsV1;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public TypeRecInterTrnRsV2 processRecInterTrnRqV2(TypeRecInterTrnRqV2 paramTypeRecInterTrnRqV2, TypeUserData paramTypeUserData)
/*  990:     */     throws RemoteException, FFSException
/*  991:     */   {
/*  992: 697 */     TypeRecInterTrnRsV2 localTypeRecInterTrnRsV2 = new TypeRecInterTrnRsV2();
/*  993:     */     
/*  994:     */ 
/*  995:     */ 
/*  996: 701 */     return localTypeRecInterTrnRsV2;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 1000:     */     throws RemoteException, FFSException
/* 1001:     */   {
/* 1002: 706 */     TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1 = new TypeRecIntraSyncRsV1();
/* 1003:     */     
/* 1004:     */ 
/* 1005:     */ 
/* 1006: 710 */     return localTypeRecIntraSyncRsV1;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public TypeRecIntraSyncRsV2 processRecIntraSyncRqV2(TypeRecIntraSyncRqV2 paramTypeRecIntraSyncRqV2, TypeUserData paramTypeUserData)
/* 1010:     */     throws RemoteException, FFSException
/* 1011:     */   {
/* 1012: 715 */     TypeRecIntraSyncRsV2 localTypeRecIntraSyncRsV2 = new TypeRecIntraSyncRsV2();
/* 1013:     */     
/* 1014:     */ 
/* 1015:     */ 
/* 1016: 719 */     return localTypeRecIntraSyncRsV2;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 1020:     */     throws RemoteException, FFSException
/* 1021:     */   {
/* 1022: 724 */     TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = new TypeRecIntraTrnRsV1();
/* 1023:     */     
/* 1024:     */ 
/* 1025:     */ 
/* 1026: 728 */     return localTypeRecIntraTrnRsV1;
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public TypeRecIntraTrnRsV2 processRecIntraTrnRqV2(TypeRecIntraTrnRqV2 paramTypeRecIntraTrnRqV2, TypeUserData paramTypeUserData)
/* 1030:     */     throws RemoteException, FFSException
/* 1031:     */   {
/* 1032: 733 */     TypeRecIntraTrnRsV2 localTypeRecIntraTrnRsV2 = new TypeRecIntraTrnRsV2();
/* 1033:     */     
/* 1034:     */ 
/* 1035:     */ 
/* 1036: 737 */     return localTypeRecIntraTrnRsV2;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 1040:     */     throws RemoteException, FFSException
/* 1041:     */   {
/* 1042: 742 */     TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV1 = new TypeRecPmtSyncRsV1();
/* 1043:     */     
/* 1044:     */ 
/* 1045:     */ 
/* 1046: 746 */     return localTypeRecPmtSyncRsV1;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public TypeRecPmtSyncRsV2 processRecPmtSyncRqV2(TypeRecPmtSyncRqV2 paramTypeRecPmtSyncRqV2, TypeUserData paramTypeUserData)
/* 1050:     */     throws RemoteException, FFSException
/* 1051:     */   {
/* 1052: 751 */     TypeRecPmtSyncRsV2 localTypeRecPmtSyncRsV2 = new TypeRecPmtSyncRsV2();
/* 1053:     */     
/* 1054:     */ 
/* 1055:     */ 
/* 1056: 755 */     return localTypeRecPmtSyncRsV2;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 1060:     */     throws RemoteException, FFSException
/* 1061:     */   {
/* 1062: 760 */     TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = new TypeRecPmtTrnRsV1();
/* 1063:     */     
/* 1064:     */ 
/* 1065:     */ 
/* 1066: 764 */     return localTypeRecPmtTrnRsV1;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public TypeRecPmtTrnRsV2 processRecPmtTrnRqV2(TypeRecPmtTrnRqV2 paramTypeRecPmtTrnRqV2, TypeUserData paramTypeUserData)
/* 1070:     */     throws RemoteException, FFSException
/* 1071:     */   {
/* 1072: 769 */     TypeRecPmtTrnRsV2 localTypeRecPmtTrnRsV2 = new TypeRecPmtTrnRsV2();
/* 1073:     */     
/* 1074:     */ 
/* 1075:     */ 
/* 1076: 773 */     return localTypeRecPmtTrnRsV2;
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public TypeRecTaxPmtSyncRsV1 processRecTaxPmtSyncRqV1(TypeRecTaxPmtSyncRqV1 paramTypeRecTaxPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 1080:     */     throws RemoteException, FFSException
/* 1081:     */   {
/* 1082: 778 */     TypeRecTaxPmtSyncRsV1 localTypeRecTaxPmtSyncRsV1 = new TypeRecTaxPmtSyncRsV1();
/* 1083:     */     
/* 1084:     */ 
/* 1085:     */ 
/* 1086: 782 */     return localTypeRecTaxPmtSyncRsV1;
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   public TypeRecTaxPmtSyncRsV2 processRecTaxPmtSyncRqV2(TypeRecTaxPmtSyncRqV2 paramTypeRecTaxPmtSyncRqV2, TypeUserData paramTypeUserData)
/* 1090:     */     throws RemoteException, FFSException
/* 1091:     */   {
/* 1092: 787 */     TypeRecTaxPmtSyncRsV2 localTypeRecTaxPmtSyncRsV2 = new TypeRecTaxPmtSyncRsV2();
/* 1093:     */     
/* 1094:     */ 
/* 1095:     */ 
/* 1096: 791 */     return localTypeRecTaxPmtSyncRsV2;
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   public TypeRecTaxPmtTrnRsV1 processRecTaxPmtTrnRqV1(TypeRecTaxPmtTrnRqV1 paramTypeRecTaxPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 1100:     */     throws RemoteException, FFSException
/* 1101:     */   {
/* 1102: 796 */     TypeRecTaxPmtTrnRsV1 localTypeRecTaxPmtTrnRsV1 = new TypeRecTaxPmtTrnRsV1();
/* 1103:     */     
/* 1104:     */ 
/* 1105:     */ 
/* 1106: 800 */     return localTypeRecTaxPmtTrnRsV1;
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public TypeRecTaxPmtTrnRsV2 processRecTaxPmtTrnRqV2(TypeRecTaxPmtTrnRqV2 paramTypeRecTaxPmtTrnRqV2, TypeUserData paramTypeUserData)
/* 1110:     */     throws RemoteException, FFSException
/* 1111:     */   {
/* 1112: 805 */     TypeRecTaxPmtTrnRsV2 localTypeRecTaxPmtTrnRsV2 = new TypeRecTaxPmtTrnRsV2();
/* 1113:     */     
/* 1114:     */ 
/* 1115:     */ 
/* 1116: 809 */     return localTypeRecTaxPmtTrnRsV2;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public TypeSecListTrnRsV1 processSecListTrnRqV1(TypeSecListTrnRqV1 paramTypeSecListTrnRqV1, TypeUserData paramTypeUserData)
/* 1120:     */     throws RemoteException, FFSException
/* 1121:     */   {
/* 1122: 814 */     TypeSecListTrnRsV1 localTypeSecListTrnRsV1 = new TypeSecListTrnRsV1();
/* 1123:     */     
/* 1124:     */ 
/* 1125:     */ 
/* 1126: 818 */     return localTypeSecListTrnRsV1;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public TypeSecListTrnRsV2 processSecListTrnRqV2(TypeSecListTrnRqV2 paramTypeSecListTrnRqV2, TypeUserData paramTypeUserData)
/* 1130:     */     throws RemoteException, FFSException
/* 1131:     */   {
/* 1132: 823 */     TypeSecListTrnRsV2 localTypeSecListTrnRsV2 = new TypeSecListTrnRsV2();
/* 1133:     */     
/* 1134:     */ 
/* 1135:     */ 
/* 1136: 827 */     return localTypeSecListTrnRsV2;
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public TypeSonRsV1 processSonRqV1(TypeSonRqV1 paramTypeSonRqV1, TypeUserData paramTypeUserData)
/* 1140:     */     throws RemoteException, FFSException
/* 1141:     */   {
/* 1142: 832 */     TypeSonRsV1 localTypeSonRsV1 = new TypeSonRsV1();
/* 1143:     */     
/* 1144:     */ 
/* 1145:     */ 
/* 1146: 836 */     return localTypeSonRsV1;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public TypeSonRsV2 processSonRqV2(TypeSonRqV2 paramTypeSonRqV2, TypeUserData paramTypeUserData)
/* 1150:     */     throws RemoteException, FFSException
/* 1151:     */   {
/* 1152: 841 */     TypeSonRsV2 localTypeSonRsV2 = new TypeSonRsV2();
/* 1153:     */     
/* 1154:     */ 
/* 1155:     */ 
/* 1156: 845 */     return localTypeSonRsV2;
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public TypeStartRs processStartRq(TypeStartRq paramTypeStartRq, TypeUserData paramTypeUserData)
/* 1160:     */     throws RemoteException, FFSException
/* 1161:     */   {
/* 1162: 850 */     TypeStartRs localTypeStartRs = new TypeStartRs();
/* 1163:     */     
/* 1164:     */ 
/* 1165:     */ 
/* 1166: 854 */     return localTypeStartRs;
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public TypeStmtEndTrnRsV1 processStmtEndTrnRqV1(TypeStmtEndTrnRqV1 paramTypeStmtEndTrnRqV1, TypeUserData paramTypeUserData)
/* 1170:     */     throws RemoteException, FFSException
/* 1171:     */   {
/* 1172: 859 */     TypeStmtEndTrnRsV1 localTypeStmtEndTrnRsV1 = new TypeStmtEndTrnRsV1();
/* 1173:     */     
/* 1174:     */ 
/* 1175:     */ 
/* 1176: 863 */     return localTypeStmtEndTrnRsV1;
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   public TypeStmtEndTrnRsV2 processStmtEndTrnRqV2(TypeStmtEndTrnRqV2 paramTypeStmtEndTrnRqV2, TypeUserData paramTypeUserData)
/* 1180:     */     throws RemoteException, FFSException
/* 1181:     */   {
/* 1182: 868 */     TypeStmtEndTrnRsV2 localTypeStmtEndTrnRsV2 = new TypeStmtEndTrnRsV2();
/* 1183:     */     
/* 1184:     */ 
/* 1185:     */ 
/* 1186: 872 */     return localTypeStmtEndTrnRsV2;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public TypeStmtTrnRsV1 processStmtTrnRqV1(TypeStmtTrnRqV1 paramTypeStmtTrnRqV1, TypeUserData paramTypeUserData)
/* 1190:     */     throws RemoteException, FFSException
/* 1191:     */   {
/* 1192: 877 */     TypeStmtTrnRsV1 localTypeStmtTrnRsV1 = new TypeStmtTrnRsV1();
/* 1193:     */     
/* 1194:     */ 
/* 1195:     */ 
/* 1196: 881 */     return localTypeStmtTrnRsV1;
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public TypeStmtTrnRsV2 processStmtTrnRqV2(TypeStmtTrnRqV2 paramTypeStmtTrnRqV2, TypeUserData paramTypeUserData)
/* 1200:     */     throws RemoteException, FFSException
/* 1201:     */   {
/* 1202: 886 */     TypeStmtTrnRsV2 localTypeStmtTrnRsV2 = new TypeStmtTrnRsV2();
/* 1203:     */     
/* 1204:     */ 
/* 1205:     */ 
/* 1206: 890 */     return localTypeStmtTrnRsV2;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public TypeStopRs processStopRq(TypeStopRq paramTypeStopRq, TypeUserData paramTypeUserData)
/* 1210:     */     throws RemoteException, FFSException
/* 1211:     */   {
/* 1212: 895 */     TypeStopRs localTypeStopRs = new TypeStopRs();
/* 1213:     */     
/* 1214:     */ 
/* 1215:     */ 
/* 1216: 899 */     return localTypeStopRs;
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public TypeStpChkSyncRsV1 processStpChkSyncRqV1(TypeStpChkSyncRqV1 paramTypeStpChkSyncRqV1, TypeUserData paramTypeUserData)
/* 1220:     */     throws RemoteException, FFSException
/* 1221:     */   {
/* 1222: 904 */     TypeStpChkSyncRsV1 localTypeStpChkSyncRsV1 = new TypeStpChkSyncRsV1();
/* 1223:     */     
/* 1224:     */ 
/* 1225:     */ 
/* 1226: 908 */     return localTypeStpChkSyncRsV1;
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   public TypeStpChkSyncRsV2 processStpChkSyncRqV2(TypeStpChkSyncRqV2 paramTypeStpChkSyncRqV2, TypeUserData paramTypeUserData)
/* 1230:     */     throws RemoteException, FFSException
/* 1231:     */   {
/* 1232: 913 */     TypeStpChkSyncRsV2 localTypeStpChkSyncRsV2 = new TypeStpChkSyncRsV2();
/* 1233:     */     
/* 1234:     */ 
/* 1235:     */ 
/* 1236: 917 */     return localTypeStpChkSyncRsV2;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public TypeStpChkTrnRsV1 processStpChkTrnRqV1(TypeStpChkTrnRqV1 paramTypeStpChkTrnRqV1, TypeUserData paramTypeUserData)
/* 1240:     */     throws RemoteException, FFSException
/* 1241:     */   {
/* 1242: 922 */     TypeStpChkTrnRsV1 localTypeStpChkTrnRsV1 = new TypeStpChkTrnRsV1();
/* 1243:     */     
/* 1244:     */ 
/* 1245:     */ 
/* 1246: 926 */     return localTypeStpChkTrnRsV1;
/* 1247:     */   }
/* 1248:     */   
/* 1249:     */   public TypeStpChkTrnRsV2 processStpChkTrnRqV2(TypeStpChkTrnRqV2 paramTypeStpChkTrnRqV2, TypeUserData paramTypeUserData)
/* 1250:     */     throws RemoteException, FFSException
/* 1251:     */   {
/* 1252: 931 */     TypeStpChkTrnRsV2 localTypeStpChkTrnRsV2 = new TypeStpChkTrnRsV2();
/* 1253:     */     
/* 1254:     */ 
/* 1255:     */ 
/* 1256: 935 */     return localTypeStpChkTrnRsV2;
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public TypeTaxPmtSyncRsV1 processTaxPmtSyncRqV1(TypeTaxPmtSyncRqV1 paramTypeTaxPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 1260:     */     throws RemoteException, FFSException
/* 1261:     */   {
/* 1262: 940 */     TypeTaxPmtSyncRsV1 localTypeTaxPmtSyncRsV1 = new TypeTaxPmtSyncRsV1();
/* 1263:     */     
/* 1264:     */ 
/* 1265:     */ 
/* 1266: 944 */     return localTypeTaxPmtSyncRsV1;
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   public TypeTaxPmtSyncRsV2 processTaxPmtSyncRqV2(TypeTaxPmtSyncRqV2 paramTypeTaxPmtSyncRqV2, TypeUserData paramTypeUserData)
/* 1270:     */     throws RemoteException, FFSException
/* 1271:     */   {
/* 1272: 949 */     TypeTaxPmtSyncRsV2 localTypeTaxPmtSyncRsV2 = new TypeTaxPmtSyncRsV2();
/* 1273:     */     
/* 1274:     */ 
/* 1275:     */ 
/* 1276: 953 */     return localTypeTaxPmtSyncRsV2;
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public TypeTaxPmtTrnRsV1 processTaxPmtTrnRqV1(TypeTaxPmtTrnRqV1 paramTypeTaxPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 1280:     */     throws RemoteException, FFSException
/* 1281:     */   {
/* 1282: 958 */     TypeTaxPmtTrnRsV1 localTypeTaxPmtTrnRsV1 = new TypeTaxPmtTrnRsV1();
/* 1283:     */     
/* 1284:     */ 
/* 1285:     */ 
/* 1286: 962 */     return localTypeTaxPmtTrnRsV1;
/* 1287:     */   }
/* 1288:     */   
/* 1289:     */   public TypeTaxPmtTrnRsV2 processTaxPmtTrnRqV2(TypeTaxPmtTrnRqV2 paramTypeTaxPmtTrnRqV2, TypeUserData paramTypeUserData)
/* 1290:     */     throws RemoteException, FFSException
/* 1291:     */   {
/* 1292: 967 */     TypeTaxPmtTrnRsV2 localTypeTaxPmtTrnRsV2 = new TypeTaxPmtTrnRsV2();
/* 1293:     */     
/* 1294:     */ 
/* 1295:     */ 
/* 1296: 971 */     return localTypeTaxPmtTrnRsV2;
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public TypeWireSyncRsV1 processWireSyncRqV1(TypeWireSyncRqV1 paramTypeWireSyncRqV1, TypeUserData paramTypeUserData)
/* 1300:     */     throws RemoteException, FFSException
/* 1301:     */   {
/* 1302: 976 */     TypeWireSyncRsV1 localTypeWireSyncRsV1 = new TypeWireSyncRsV1();
/* 1303:     */     
/* 1304:     */ 
/* 1305:     */ 
/* 1306: 980 */     return localTypeWireSyncRsV1;
/* 1307:     */   }
/* 1308:     */   
/* 1309:     */   public TypeWireSyncRsV2 processWireSyncRqV2(TypeWireSyncRqV2 paramTypeWireSyncRqV2, TypeUserData paramTypeUserData)
/* 1310:     */     throws RemoteException, FFSException
/* 1311:     */   {
/* 1312: 985 */     TypeWireSyncRsV2 localTypeWireSyncRsV2 = new TypeWireSyncRsV2();
/* 1313:     */     
/* 1314:     */ 
/* 1315:     */ 
/* 1316: 989 */     return localTypeWireSyncRsV2;
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   public TypeWireTrnRsV1 processWireTrnRqV1(TypeWireTrnRqV1 paramTypeWireTrnRqV1, TypeUserData paramTypeUserData)
/* 1320:     */     throws RemoteException, FFSException
/* 1321:     */   {
/* 1322: 994 */     TypeWireTrnRsV1 localTypeWireTrnRsV1 = new TypeWireTrnRsV1();
/* 1323:     */     
/* 1324:     */ 
/* 1325:     */ 
/* 1326: 998 */     return localTypeWireTrnRsV1;
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   public TypeWireTrnRsV2 processWireTrnRqV2(TypeWireTrnRqV2 paramTypeWireTrnRqV2, TypeUserData paramTypeUserData)
/* 1330:     */     throws RemoteException, FFSException
/* 1331:     */   {
/* 1332:1003 */     TypeWireTrnRsV2 localTypeWireTrnRsV2 = new TypeWireTrnRsV2();
/* 1333:     */     
/* 1334:     */ 
/* 1335:     */ 
/* 1336:1007 */     return localTypeWireTrnRsV2;
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   public void setSessionContext(SessionContext paramSessionContext)
/* 1340:     */     throws RemoteException
/* 1341:     */   {
/* 1342:1016 */     this._context = paramSessionContext;
/* 1343:     */   }
/* 1344:     */ }


/* Location:           D:\drops\jd\jars\OFX151Callback.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.OFX151CallbackBean
 * JD-Core Version:    0.7.0.1
 */