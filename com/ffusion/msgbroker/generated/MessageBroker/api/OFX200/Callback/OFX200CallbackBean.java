/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback;
/*   2:    */ 
/*   3:    */ import com.ffusion.ffs.interfaces.FFSException;
/*   4:    */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   5:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoTrnRqV1;
/*   6:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoTrnRsV1;
/*   7:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctSyncRqV1;
/*   8:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctSyncRsV1;
/*   9:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctTrnRqV1;
/*  10:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctTrnRsV1;
/*  11:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailSyncRqV1;
/*  12:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailSyncRsV1;
/*  13:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailTrnRqV1;
/*  14:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailTrnRsV1;
/*  15:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillTblStructTrnRqV1;
/*  16:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillTblStructTrnRsV1;
/*  17:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtEndTrnRqV1;
/*  18:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtEndTrnRsV1;
/*  19:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRqV1;
/*  20:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRsV1;
/*  21:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChallengeTrnRqV1;
/*  22:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChallengeTrnRsV1;
/*  23:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChgUserInfoSyncRqV1;
/*  24:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChgUserInfoSyncRsV1;
/*  25:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChgUserInfoTrnRqV1;
/*  26:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeChgUserInfoTrnRsV1;
/*  27:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeDisconnectRq;
/*  28:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeDisconnectRs;
/*  29:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeEnrollTrnRqV1;
/*  30:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeEnrollTrnRsV1;
/*  31:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeFindBillerTrnRqV1;
/*  32:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeFindBillerTrnRsV1;
/*  33:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeGetMIMETrnRqV1;
/*  34:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeGetMIMETrnRsV1;
/*  35:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterSyncRqV1;
/*  36:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterSyncRsV1;
/*  37:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterTrnRqV1;
/*  38:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterTrnRsV1;
/*  39:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1;
/*  40:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1;
/*  41:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
/*  42:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
/*  43:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvMailSyncRqV1;
/*  44:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvMailSyncRsV1;
/*  45:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvMailTrnRqV1;
/*  46:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvMailTrnRsV1;
/*  47:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvStmtTrnRqV1;
/*  48:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvStmtTrnRsV1;
/*  49:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailSyncRqV1;
/*  50:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailSyncRsV1;
/*  51:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailTrnRqV1;
/*  52:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailTrnRsV1;
/*  53:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRequest;
/*  54:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXResponse;
/*  55:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
/*  56:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
/*  57:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
/*  58:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
/*  59:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChTrnRqV1;
/*  60:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChTrnRsV1;
/*  61:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1;
/*  62:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1;
/*  63:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailSyncRqV1;
/*  64:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailSyncRsV1;
/*  65:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailTrnRqV1;
/*  66:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailTrnRsV1;
/*  67:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
/*  68:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
/*  69:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
/*  70:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
/*  71:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresDetailTrnRqV1;
/*  72:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresDetailTrnRsV1;
/*  73:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresGrpAcctInfoTrnRqV1;
/*  74:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresGrpAcctInfoTrnRsV1;
/*  75:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresListTrnRqV1;
/*  76:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresListTrnRsV1;
/*  77:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresMailSyncRqV1;
/*  78:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresMailSyncRsV1;
/*  79:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresMailTrnRqV1;
/*  80:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresMailTrnRsV1;
/*  81:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresNotifyTrnRqV1;
/*  82:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePresNotifyTrnRsV1;
/*  83:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeProfTrnRqV1;
/*  84:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeProfTrnRsV1;
/*  85:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecInterSyncRqV1;
/*  86:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecInterSyncRsV1;
/*  87:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecInterTrnRqV1;
/*  88:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecInterTrnRsV1;
/*  89:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
/*  90:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
/*  91:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
/*  92:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
/*  93:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
/*  94:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
/*  95:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
/*  96:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
/*  97:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecTaxPmtSyncRqV1;
/*  98:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecTaxPmtSyncRsV1;
/*  99:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecTaxPmtTrnRqV1;
/* 100:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecTaxPmtTrnRsV1;
/* 101:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSecListTrnRqV1;
/* 102:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSecListTrnRsV1;
/* 103:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRqV1;
/* 104:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRsV1;
/* 105:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStartRq;
/* 106:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStartRs;
/* 107:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtEndTrnRqV1;
/* 108:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtEndTrnRsV1;
/* 109:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRqV1;
/* 110:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRsV1;
/* 111:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStopRq;
/* 112:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStopRs;
/* 113:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStpChkSyncRqV1;
/* 114:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStpChkSyncRsV1;
/* 115:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStpChkTrnRqV1;
/* 116:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStpChkTrnRsV1;
/* 117:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTax1099TrnRqV1;
/* 118:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTax1099TrnRsV1;
/* 119:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxPmtSyncRqV1;
/* 120:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxPmtSyncRsV1;
/* 121:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxPmtTrnRqV1;
/* 122:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxPmtTrnRsV1;
/* 123:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxW2TrnRqV1;
/* 124:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTaxW2TrnRsV1;
/* 125:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireSyncRqV1;
/* 126:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireSyncRsV1;
/* 127:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRqV1;
/* 128:    */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsV1;
/* 129:    */ import java.rmi.RemoteException;
/* 130:    */ import javax.ejb.CreateException;
/* 131:    */ import javax.ejb.SessionBean;
/* 132:    */ import javax.ejb.SessionContext;
/* 133:    */ 
/* 134:    */ public class OFX200CallbackBean
/* 135:    */   implements SessionBean
/* 136:    */ {
/* 137:    */   SessionContext _context;
/* 138:    */   
/* 139:    */   public void ejbActivate()
/* 140:    */     throws RemoteException
/* 141:    */   {}
/* 142:    */   
/* 143:    */   public void ejbCreate()
/* 144:    */     throws CreateException, RemoteException, FFSException
/* 145:    */   {}
/* 146:    */   
/* 147:    */   public void ejbPassivate()
/* 148:    */     throws RemoteException
/* 149:    */   {}
/* 150:    */   
/* 151:    */   public void ejbRemove()
/* 152:    */     throws RemoteException
/* 153:    */   {}
/* 154:    */   
/* 155:    */   public TypeAcctInfoTrnRsV1 processAcctInfoTrnRqV1(TypeAcctInfoTrnRqV1 paramTypeAcctInfoTrnRqV1, TypeUserData paramTypeUserData)
/* 156:    */     throws RemoteException, FFSException
/* 157:    */   {
/* 158: 31 */     TypeAcctInfoTrnRsV1 localTypeAcctInfoTrnRsV1 = new TypeAcctInfoTrnRsV1();
/* 159:    */     
/* 160:    */ 
/* 161:    */ 
/* 162: 35 */     return localTypeAcctInfoTrnRsV1;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public TypeAcctSyncRsV1 processAcctSyncRqV1(TypeAcctSyncRqV1 paramTypeAcctSyncRqV1, TypeUserData paramTypeUserData)
/* 166:    */     throws RemoteException, FFSException
/* 167:    */   {
/* 168: 40 */     TypeAcctSyncRsV1 localTypeAcctSyncRsV1 = new TypeAcctSyncRsV1();
/* 169:    */     
/* 170:    */ 
/* 171:    */ 
/* 172: 44 */     return localTypeAcctSyncRsV1;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public TypeAcctTrnRsV1 processAcctTrnRqV1(TypeAcctTrnRqV1 paramTypeAcctTrnRqV1, TypeUserData paramTypeUserData)
/* 176:    */     throws RemoteException, FFSException
/* 177:    */   {
/* 178: 49 */     TypeAcctTrnRsV1 localTypeAcctTrnRsV1 = new TypeAcctTrnRsV1();
/* 179:    */     
/* 180:    */ 
/* 181:    */ 
/* 182: 53 */     return localTypeAcctTrnRsV1;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public TypeBankMailSyncRsV1 processBankMailSyncRqV1(TypeBankMailSyncRqV1 paramTypeBankMailSyncRqV1, TypeUserData paramTypeUserData)
/* 186:    */     throws RemoteException, FFSException
/* 187:    */   {
/* 188: 58 */     TypeBankMailSyncRsV1 localTypeBankMailSyncRsV1 = new TypeBankMailSyncRsV1();
/* 189:    */     
/* 190:    */ 
/* 191:    */ 
/* 192: 62 */     return localTypeBankMailSyncRsV1;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public TypeBankMailTrnRsV1 processBankMailTrnRqV1(TypeBankMailTrnRqV1 paramTypeBankMailTrnRqV1, TypeUserData paramTypeUserData)
/* 196:    */     throws RemoteException, FFSException
/* 197:    */   {
/* 198: 67 */     TypeBankMailTrnRsV1 localTypeBankMailTrnRsV1 = new TypeBankMailTrnRsV1();
/* 199:    */     
/* 200:    */ 
/* 201:    */ 
/* 202: 71 */     return localTypeBankMailTrnRsV1;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public TypeBillTblStructTrnRsV1 processBillTblStructTrnRqV1(TypeBillTblStructTrnRqV1 paramTypeBillTblStructTrnRqV1, TypeUserData paramTypeUserData)
/* 206:    */     throws RemoteException, FFSException
/* 207:    */   {
/* 208: 76 */     TypeBillTblStructTrnRsV1 localTypeBillTblStructTrnRsV1 = new TypeBillTblStructTrnRsV1();
/* 209:    */     
/* 210:    */ 
/* 211:    */ 
/* 212: 80 */     return localTypeBillTblStructTrnRsV1;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public TypeCCStmtEndTrnRsV1 processCCStmtEndTrnRqV1(TypeCCStmtEndTrnRqV1 paramTypeCCStmtEndTrnRqV1, TypeUserData paramTypeUserData)
/* 216:    */     throws RemoteException, FFSException
/* 217:    */   {
/* 218: 85 */     TypeCCStmtEndTrnRsV1 localTypeCCStmtEndTrnRsV1 = new TypeCCStmtEndTrnRsV1();
/* 219:    */     
/* 220:    */ 
/* 221:    */ 
/* 222: 89 */     return localTypeCCStmtEndTrnRsV1;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public TypeCCStmtTrnRsV1 processCCStmtTrnRqV1(TypeCCStmtTrnRqV1 paramTypeCCStmtTrnRqV1, TypeUserData paramTypeUserData)
/* 226:    */     throws RemoteException, FFSException
/* 227:    */   {
/* 228: 94 */     TypeCCStmtTrnRsV1 localTypeCCStmtTrnRsV1 = new TypeCCStmtTrnRsV1();
/* 229:    */     
/* 230:    */ 
/* 231:    */ 
/* 232: 98 */     return localTypeCCStmtTrnRsV1;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public TypeChallengeTrnRsV1 processChallengeTrnRqV1(TypeChallengeTrnRqV1 paramTypeChallengeTrnRqV1, TypeUserData paramTypeUserData)
/* 236:    */     throws RemoteException, FFSException
/* 237:    */   {
/* 238:103 */     TypeChallengeTrnRsV1 localTypeChallengeTrnRsV1 = new TypeChallengeTrnRsV1();
/* 239:    */     
/* 240:    */ 
/* 241:    */ 
/* 242:107 */     return localTypeChallengeTrnRsV1;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public TypeChgUserInfoSyncRsV1 processChgUserInfoSyncRqV1(TypeChgUserInfoSyncRqV1 paramTypeChgUserInfoSyncRqV1, TypeUserData paramTypeUserData)
/* 246:    */     throws RemoteException, FFSException
/* 247:    */   {
/* 248:112 */     TypeChgUserInfoSyncRsV1 localTypeChgUserInfoSyncRsV1 = new TypeChgUserInfoSyncRsV1();
/* 249:    */     
/* 250:    */ 
/* 251:    */ 
/* 252:116 */     return localTypeChgUserInfoSyncRsV1;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public TypeChgUserInfoTrnRsV1 processChgUserInfoTrnRqV1(TypeChgUserInfoTrnRqV1 paramTypeChgUserInfoTrnRqV1, TypeUserData paramTypeUserData)
/* 256:    */     throws RemoteException, FFSException
/* 257:    */   {
/* 258:121 */     TypeChgUserInfoTrnRsV1 localTypeChgUserInfoTrnRsV1 = new TypeChgUserInfoTrnRsV1();
/* 259:    */     
/* 260:    */ 
/* 261:    */ 
/* 262:125 */     return localTypeChgUserInfoTrnRsV1;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public TypeDisconnectRs processDisconnectRq(TypeDisconnectRq paramTypeDisconnectRq, TypeUserData paramTypeUserData)
/* 266:    */     throws RemoteException, FFSException
/* 267:    */   {
/* 268:130 */     TypeDisconnectRs localTypeDisconnectRs = new TypeDisconnectRs();
/* 269:    */     
/* 270:    */ 
/* 271:    */ 
/* 272:134 */     return localTypeDisconnectRs;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public TypeEnrollTrnRsV1 processEnrollTrnRqV1(TypeEnrollTrnRqV1 paramTypeEnrollTrnRqV1, TypeUserData paramTypeUserData)
/* 276:    */     throws RemoteException, FFSException
/* 277:    */   {
/* 278:139 */     TypeEnrollTrnRsV1 localTypeEnrollTrnRsV1 = new TypeEnrollTrnRsV1();
/* 279:    */     
/* 280:    */ 
/* 281:    */ 
/* 282:143 */     return localTypeEnrollTrnRsV1;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public TypeFindBillerTrnRsV1 processFindBillerTrnRqV1(TypeFindBillerTrnRqV1 paramTypeFindBillerTrnRqV1, TypeUserData paramTypeUserData)
/* 286:    */     throws RemoteException, FFSException
/* 287:    */   {
/* 288:148 */     TypeFindBillerTrnRsV1 localTypeFindBillerTrnRsV1 = new TypeFindBillerTrnRsV1();
/* 289:    */     
/* 290:    */ 
/* 291:    */ 
/* 292:152 */     return localTypeFindBillerTrnRsV1;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public TypeGetMIMETrnRsV1 processGetMIMETrnRqV1(TypeGetMIMETrnRqV1 paramTypeGetMIMETrnRqV1, TypeUserData paramTypeUserData)
/* 296:    */     throws RemoteException, FFSException
/* 297:    */   {
/* 298:157 */     TypeGetMIMETrnRsV1 localTypeGetMIMETrnRsV1 = new TypeGetMIMETrnRsV1();
/* 299:    */     
/* 300:    */ 
/* 301:    */ 
/* 302:161 */     return localTypeGetMIMETrnRsV1;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public TypeInterSyncRsV1 processInterSyncRqV1(TypeInterSyncRqV1 paramTypeInterSyncRqV1, TypeUserData paramTypeUserData)
/* 306:    */     throws RemoteException, FFSException
/* 307:    */   {
/* 308:166 */     TypeInterSyncRsV1 localTypeInterSyncRsV1 = new TypeInterSyncRsV1();
/* 309:    */     
/* 310:    */ 
/* 311:    */ 
/* 312:170 */     return localTypeInterSyncRsV1;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public TypeInterTrnRsV1 processInterTrnRqV1(TypeInterTrnRqV1 paramTypeInterTrnRqV1, TypeUserData paramTypeUserData)
/* 316:    */     throws RemoteException, FFSException
/* 317:    */   {
/* 318:175 */     TypeInterTrnRsV1 localTypeInterTrnRsV1 = new TypeInterTrnRsV1();
/* 319:    */     
/* 320:    */ 
/* 321:    */ 
/* 322:179 */     return localTypeInterTrnRsV1;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 326:    */     throws RemoteException, FFSException
/* 327:    */   {
/* 328:184 */     TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
/* 329:    */     
/* 330:    */ 
/* 331:    */ 
/* 332:188 */     return localTypeIntraSyncRsV1;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 336:    */     throws RemoteException, FFSException
/* 337:    */   {
/* 338:193 */     TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = new TypeIntraTrnRsV1();
/* 339:    */     
/* 340:    */ 
/* 341:    */ 
/* 342:197 */     return localTypeIntraTrnRsV1;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public TypeInvMailSyncRsV1 processInvMailSyncRqV1(TypeInvMailSyncRqV1 paramTypeInvMailSyncRqV1, TypeUserData paramTypeUserData)
/* 346:    */     throws RemoteException, FFSException
/* 347:    */   {
/* 348:202 */     TypeInvMailSyncRsV1 localTypeInvMailSyncRsV1 = new TypeInvMailSyncRsV1();
/* 349:    */     
/* 350:    */ 
/* 351:    */ 
/* 352:206 */     return localTypeInvMailSyncRsV1;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public TypeInvMailTrnRsV1 processInvMailTrnRqV1(TypeInvMailTrnRqV1 paramTypeInvMailTrnRqV1, TypeUserData paramTypeUserData)
/* 356:    */     throws RemoteException, FFSException
/* 357:    */   {
/* 358:211 */     TypeInvMailTrnRsV1 localTypeInvMailTrnRsV1 = new TypeInvMailTrnRsV1();
/* 359:    */     
/* 360:    */ 
/* 361:    */ 
/* 362:215 */     return localTypeInvMailTrnRsV1;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public TypeInvStmtTrnRsV1 processInvStmtTrnRqV1(TypeInvStmtTrnRqV1 paramTypeInvStmtTrnRqV1, TypeUserData paramTypeUserData)
/* 366:    */     throws RemoteException, FFSException
/* 367:    */   {
/* 368:220 */     TypeInvStmtTrnRsV1 localTypeInvStmtTrnRsV1 = new TypeInvStmtTrnRsV1();
/* 369:    */     
/* 370:    */ 
/* 371:    */ 
/* 372:224 */     return localTypeInvStmtTrnRsV1;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public TypeMailSyncRsV1 processMailSyncRqV1(TypeMailSyncRqV1 paramTypeMailSyncRqV1, TypeUserData paramTypeUserData)
/* 376:    */     throws RemoteException, FFSException
/* 377:    */   {
/* 378:229 */     TypeMailSyncRsV1 localTypeMailSyncRsV1 = new TypeMailSyncRsV1();
/* 379:    */     
/* 380:    */ 
/* 381:    */ 
/* 382:233 */     return localTypeMailSyncRsV1;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public TypeMailTrnRsV1 processMailTrnRqV1(TypeMailTrnRqV1 paramTypeMailTrnRqV1, TypeUserData paramTypeUserData)
/* 386:    */     throws RemoteException, FFSException
/* 387:    */   {
/* 388:238 */     TypeMailTrnRsV1 localTypeMailTrnRsV1 = new TypeMailTrnRsV1();
/* 389:    */     
/* 390:    */ 
/* 391:    */ 
/* 392:242 */     return localTypeMailTrnRsV1;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public TypeOFXResponse processOFXRequest(TypeOFXRequest paramTypeOFXRequest, TypeUserData paramTypeUserData)
/* 396:    */     throws RemoteException, FFSException
/* 397:    */   {
/* 398:247 */     TypeOFXResponse localTypeOFXResponse = new TypeOFXResponse();
/* 399:    */     
/* 400:    */ 
/* 401:    */ 
/* 402:251 */     return localTypeOFXResponse;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
/* 406:    */     throws RemoteException, FFSException
/* 407:    */   {
/* 408:256 */     TypePayeeSyncRsV1 localTypePayeeSyncRsV1 = new TypePayeeSyncRsV1();
/* 409:    */     
/* 410:    */ 
/* 411:    */ 
/* 412:260 */     return localTypePayeeSyncRsV1;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
/* 416:    */     throws RemoteException, FFSException
/* 417:    */   {
/* 418:265 */     TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = new TypePayeeTrnRsV1();
/* 419:    */     
/* 420:    */ 
/* 421:    */ 
/* 422:269 */     return localTypePayeeTrnRsV1;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public TypePinChTrnRsV1 processPinChTrnRqV1(TypePinChTrnRqV1 paramTypePinChTrnRqV1, TypeUserData paramTypeUserData)
/* 426:    */     throws RemoteException, FFSException
/* 427:    */   {
/* 428:274 */     TypePinChTrnRsV1 localTypePinChTrnRsV1 = new TypePinChTrnRsV1();
/* 429:    */     
/* 430:    */ 
/* 431:    */ 
/* 432:278 */     return localTypePinChTrnRsV1;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
/* 436:    */     throws RemoteException, FFSException
/* 437:    */   {
/* 438:283 */     TypePmtInqTrnRsV1 localTypePmtInqTrnRsV1 = new TypePmtInqTrnRsV1();
/* 439:    */     
/* 440:    */ 
/* 441:    */ 
/* 442:287 */     return localTypePmtInqTrnRsV1;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public TypePmtMailSyncRsV1 processPmtMailSyncRqV1(TypePmtMailSyncRqV1 paramTypePmtMailSyncRqV1, TypeUserData paramTypeUserData)
/* 446:    */     throws RemoteException, FFSException
/* 447:    */   {
/* 448:292 */     TypePmtMailSyncRsV1 localTypePmtMailSyncRsV1 = new TypePmtMailSyncRsV1();
/* 449:    */     
/* 450:    */ 
/* 451:    */ 
/* 452:296 */     return localTypePmtMailSyncRsV1;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public TypePmtMailTrnRsV1 processPmtMailTrnRqV1(TypePmtMailTrnRqV1 paramTypePmtMailTrnRqV1, TypeUserData paramTypeUserData)
/* 456:    */     throws RemoteException, FFSException
/* 457:    */   {
/* 458:301 */     TypePmtMailTrnRsV1 localTypePmtMailTrnRsV1 = new TypePmtMailTrnRsV1();
/* 459:    */     
/* 460:    */ 
/* 461:    */ 
/* 462:305 */     return localTypePmtMailTrnRsV1;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
/* 466:    */     throws RemoteException, FFSException
/* 467:    */   {
/* 468:310 */     TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
/* 469:    */     
/* 470:    */ 
/* 471:    */ 
/* 472:314 */     return localTypePmtSyncRsV1;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
/* 476:    */     throws RemoteException, FFSException
/* 477:    */   {
/* 478:319 */     TypePmtTrnRsV1 localTypePmtTrnRsV1 = new TypePmtTrnRsV1();
/* 479:    */     
/* 480:    */ 
/* 481:    */ 
/* 482:323 */     return localTypePmtTrnRsV1;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public TypePresDetailTrnRsV1 processPresDetailTrnRqV1(TypePresDetailTrnRqV1 paramTypePresDetailTrnRqV1, TypeUserData paramTypeUserData)
/* 486:    */     throws RemoteException, FFSException
/* 487:    */   {
/* 488:328 */     TypePresDetailTrnRsV1 localTypePresDetailTrnRsV1 = new TypePresDetailTrnRsV1();
/* 489:    */     
/* 490:    */ 
/* 491:    */ 
/* 492:332 */     return localTypePresDetailTrnRsV1;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public TypePresGrpAcctInfoTrnRsV1 processPresGrpAcctInfoTrnRqV1(TypePresGrpAcctInfoTrnRqV1 paramTypePresGrpAcctInfoTrnRqV1, TypeUserData paramTypeUserData)
/* 496:    */     throws RemoteException, FFSException
/* 497:    */   {
/* 498:337 */     TypePresGrpAcctInfoTrnRsV1 localTypePresGrpAcctInfoTrnRsV1 = new TypePresGrpAcctInfoTrnRsV1();
/* 499:    */     
/* 500:    */ 
/* 501:    */ 
/* 502:341 */     return localTypePresGrpAcctInfoTrnRsV1;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public TypePresListTrnRsV1 processPresListTrnRqV1(TypePresListTrnRqV1 paramTypePresListTrnRqV1, TypeUserData paramTypeUserData)
/* 506:    */     throws RemoteException, FFSException
/* 507:    */   {
/* 508:346 */     TypePresListTrnRsV1 localTypePresListTrnRsV1 = new TypePresListTrnRsV1();
/* 509:    */     
/* 510:    */ 
/* 511:    */ 
/* 512:350 */     return localTypePresListTrnRsV1;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public TypePresMailSyncRsV1 processPresMailSyncRqV1(TypePresMailSyncRqV1 paramTypePresMailSyncRqV1, TypeUserData paramTypeUserData)
/* 516:    */     throws RemoteException, FFSException
/* 517:    */   {
/* 518:355 */     TypePresMailSyncRsV1 localTypePresMailSyncRsV1 = new TypePresMailSyncRsV1();
/* 519:    */     
/* 520:    */ 
/* 521:    */ 
/* 522:359 */     return localTypePresMailSyncRsV1;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public TypePresMailTrnRsV1 processPresMailTrnRqV1(TypePresMailTrnRqV1 paramTypePresMailTrnRqV1, TypeUserData paramTypeUserData)
/* 526:    */     throws RemoteException, FFSException
/* 527:    */   {
/* 528:364 */     TypePresMailTrnRsV1 localTypePresMailTrnRsV1 = new TypePresMailTrnRsV1();
/* 529:    */     
/* 530:    */ 
/* 531:    */ 
/* 532:368 */     return localTypePresMailTrnRsV1;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public TypePresNotifyTrnRsV1 processPresNotifyTrnRqV1(TypePresNotifyTrnRqV1 paramTypePresNotifyTrnRqV1, TypeUserData paramTypeUserData)
/* 536:    */     throws RemoteException, FFSException
/* 537:    */   {
/* 538:373 */     TypePresNotifyTrnRsV1 localTypePresNotifyTrnRsV1 = new TypePresNotifyTrnRsV1();
/* 539:    */     
/* 540:    */ 
/* 541:    */ 
/* 542:377 */     return localTypePresNotifyTrnRsV1;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public TypeProfTrnRsV1 processProfTrnRqV1(TypeProfTrnRqV1 paramTypeProfTrnRqV1, TypeUserData paramTypeUserData)
/* 546:    */     throws RemoteException, FFSException
/* 547:    */   {
/* 548:382 */     TypeProfTrnRsV1 localTypeProfTrnRsV1 = new TypeProfTrnRsV1();
/* 549:    */     
/* 550:    */ 
/* 551:    */ 
/* 552:386 */     return localTypeProfTrnRsV1;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public TypeRecInterSyncRsV1 processRecInterSyncRqV1(TypeRecInterSyncRqV1 paramTypeRecInterSyncRqV1, TypeUserData paramTypeUserData)
/* 556:    */     throws RemoteException, FFSException
/* 557:    */   {
/* 558:391 */     TypeRecInterSyncRsV1 localTypeRecInterSyncRsV1 = new TypeRecInterSyncRsV1();
/* 559:    */     
/* 560:    */ 
/* 561:    */ 
/* 562:395 */     return localTypeRecInterSyncRsV1;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public TypeRecInterTrnRsV1 processRecInterTrnRqV1(TypeRecInterTrnRqV1 paramTypeRecInterTrnRqV1, TypeUserData paramTypeUserData)
/* 566:    */     throws RemoteException, FFSException
/* 567:    */   {
/* 568:400 */     TypeRecInterTrnRsV1 localTypeRecInterTrnRsV1 = new TypeRecInterTrnRsV1();
/* 569:    */     
/* 570:    */ 
/* 571:    */ 
/* 572:404 */     return localTypeRecInterTrnRsV1;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 576:    */     throws RemoteException, FFSException
/* 577:    */   {
/* 578:409 */     TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1 = new TypeRecIntraSyncRsV1();
/* 579:    */     
/* 580:    */ 
/* 581:    */ 
/* 582:413 */     return localTypeRecIntraSyncRsV1;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 586:    */     throws RemoteException, FFSException
/* 587:    */   {
/* 588:418 */     TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = new TypeRecIntraTrnRsV1();
/* 589:    */     
/* 590:    */ 
/* 591:    */ 
/* 592:422 */     return localTypeRecIntraTrnRsV1;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 596:    */     throws RemoteException, FFSException
/* 597:    */   {
/* 598:427 */     TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV1 = new TypeRecPmtSyncRsV1();
/* 599:    */     
/* 600:    */ 
/* 601:    */ 
/* 602:431 */     return localTypeRecPmtSyncRsV1;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 606:    */     throws RemoteException, FFSException
/* 607:    */   {
/* 608:436 */     TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = new TypeRecPmtTrnRsV1();
/* 609:    */     
/* 610:    */ 
/* 611:    */ 
/* 612:440 */     return localTypeRecPmtTrnRsV1;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public TypeRecTaxPmtSyncRsV1 processRecTaxPmtSyncRqV1(TypeRecTaxPmtSyncRqV1 paramTypeRecTaxPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 616:    */     throws RemoteException, FFSException
/* 617:    */   {
/* 618:445 */     TypeRecTaxPmtSyncRsV1 localTypeRecTaxPmtSyncRsV1 = new TypeRecTaxPmtSyncRsV1();
/* 619:    */     
/* 620:    */ 
/* 621:    */ 
/* 622:449 */     return localTypeRecTaxPmtSyncRsV1;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public TypeRecTaxPmtTrnRsV1 processRecTaxPmtTrnRqV1(TypeRecTaxPmtTrnRqV1 paramTypeRecTaxPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 626:    */     throws RemoteException, FFSException
/* 627:    */   {
/* 628:454 */     TypeRecTaxPmtTrnRsV1 localTypeRecTaxPmtTrnRsV1 = new TypeRecTaxPmtTrnRsV1();
/* 629:    */     
/* 630:    */ 
/* 631:    */ 
/* 632:458 */     return localTypeRecTaxPmtTrnRsV1;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public TypeSecListTrnRsV1 processSecListTrnRqV1(TypeSecListTrnRqV1 paramTypeSecListTrnRqV1, TypeUserData paramTypeUserData)
/* 636:    */     throws RemoteException, FFSException
/* 637:    */   {
/* 638:463 */     TypeSecListTrnRsV1 localTypeSecListTrnRsV1 = new TypeSecListTrnRsV1();
/* 639:    */     
/* 640:    */ 
/* 641:    */ 
/* 642:467 */     return localTypeSecListTrnRsV1;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public TypeSonRsV1 processSonRqV1(TypeSonRqV1 paramTypeSonRqV1, TypeUserData paramTypeUserData)
/* 646:    */     throws RemoteException, FFSException
/* 647:    */   {
/* 648:472 */     TypeSonRsV1 localTypeSonRsV1 = new TypeSonRsV1();
/* 649:    */     
/* 650:    */ 
/* 651:    */ 
/* 652:476 */     return localTypeSonRsV1;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public TypeStartRs processStartRq(TypeStartRq paramTypeStartRq, TypeUserData paramTypeUserData)
/* 656:    */     throws RemoteException, FFSException
/* 657:    */   {
/* 658:481 */     TypeStartRs localTypeStartRs = new TypeStartRs();
/* 659:    */     
/* 660:    */ 
/* 661:    */ 
/* 662:485 */     return localTypeStartRs;
/* 663:    */   }
/* 664:    */   
/* 665:    */   public TypeStmtEndTrnRsV1 processStmtEndTrnRqV1(TypeStmtEndTrnRqV1 paramTypeStmtEndTrnRqV1, TypeUserData paramTypeUserData)
/* 666:    */     throws RemoteException, FFSException
/* 667:    */   {
/* 668:490 */     TypeStmtEndTrnRsV1 localTypeStmtEndTrnRsV1 = new TypeStmtEndTrnRsV1();
/* 669:    */     
/* 670:    */ 
/* 671:    */ 
/* 672:494 */     return localTypeStmtEndTrnRsV1;
/* 673:    */   }
/* 674:    */   
/* 675:    */   public TypeStmtTrnRsV1 processStmtTrnRqV1(TypeStmtTrnRqV1 paramTypeStmtTrnRqV1, TypeUserData paramTypeUserData)
/* 676:    */     throws RemoteException, FFSException
/* 677:    */   {
/* 678:499 */     TypeStmtTrnRsV1 localTypeStmtTrnRsV1 = new TypeStmtTrnRsV1();
/* 679:    */     
/* 680:    */ 
/* 681:    */ 
/* 682:503 */     return localTypeStmtTrnRsV1;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public TypeStopRs processStopRq(TypeStopRq paramTypeStopRq, TypeUserData paramTypeUserData)
/* 686:    */     throws RemoteException, FFSException
/* 687:    */   {
/* 688:508 */     TypeStopRs localTypeStopRs = new TypeStopRs();
/* 689:    */     
/* 690:    */ 
/* 691:    */ 
/* 692:512 */     return localTypeStopRs;
/* 693:    */   }
/* 694:    */   
/* 695:    */   public TypeStpChkSyncRsV1 processStpChkSyncRqV1(TypeStpChkSyncRqV1 paramTypeStpChkSyncRqV1, TypeUserData paramTypeUserData)
/* 696:    */     throws RemoteException, FFSException
/* 697:    */   {
/* 698:517 */     TypeStpChkSyncRsV1 localTypeStpChkSyncRsV1 = new TypeStpChkSyncRsV1();
/* 699:    */     
/* 700:    */ 
/* 701:    */ 
/* 702:521 */     return localTypeStpChkSyncRsV1;
/* 703:    */   }
/* 704:    */   
/* 705:    */   public TypeStpChkTrnRsV1 processStpChkTrnRqV1(TypeStpChkTrnRqV1 paramTypeStpChkTrnRqV1, TypeUserData paramTypeUserData)
/* 706:    */     throws RemoteException, FFSException
/* 707:    */   {
/* 708:526 */     TypeStpChkTrnRsV1 localTypeStpChkTrnRsV1 = new TypeStpChkTrnRsV1();
/* 709:    */     
/* 710:    */ 
/* 711:    */ 
/* 712:530 */     return localTypeStpChkTrnRsV1;
/* 713:    */   }
/* 714:    */   
/* 715:    */   public TypeTax1099TrnRsV1 processTax1099TrnRqV1(TypeTax1099TrnRqV1 paramTypeTax1099TrnRqV1, TypeUserData paramTypeUserData)
/* 716:    */     throws RemoteException, FFSException
/* 717:    */   {
/* 718:535 */     TypeTax1099TrnRsV1 localTypeTax1099TrnRsV1 = new TypeTax1099TrnRsV1();
/* 719:    */     
/* 720:    */ 
/* 721:    */ 
/* 722:539 */     return localTypeTax1099TrnRsV1;
/* 723:    */   }
/* 724:    */   
/* 725:    */   public TypeTaxPmtSyncRsV1 processTaxPmtSyncRqV1(TypeTaxPmtSyncRqV1 paramTypeTaxPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 726:    */     throws RemoteException, FFSException
/* 727:    */   {
/* 728:544 */     TypeTaxPmtSyncRsV1 localTypeTaxPmtSyncRsV1 = new TypeTaxPmtSyncRsV1();
/* 729:    */     
/* 730:    */ 
/* 731:    */ 
/* 732:548 */     return localTypeTaxPmtSyncRsV1;
/* 733:    */   }
/* 734:    */   
/* 735:    */   public TypeTaxPmtTrnRsV1 processTaxPmtTrnRqV1(TypeTaxPmtTrnRqV1 paramTypeTaxPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 736:    */     throws RemoteException, FFSException
/* 737:    */   {
/* 738:553 */     TypeTaxPmtTrnRsV1 localTypeTaxPmtTrnRsV1 = new TypeTaxPmtTrnRsV1();
/* 739:    */     
/* 740:    */ 
/* 741:    */ 
/* 742:557 */     return localTypeTaxPmtTrnRsV1;
/* 743:    */   }
/* 744:    */   
/* 745:    */   public TypeTaxW2TrnRsV1 processTaxW2TrnRqV1(TypeTaxW2TrnRqV1 paramTypeTaxW2TrnRqV1, TypeUserData paramTypeUserData)
/* 746:    */     throws RemoteException, FFSException
/* 747:    */   {
/* 748:562 */     TypeTaxW2TrnRsV1 localTypeTaxW2TrnRsV1 = new TypeTaxW2TrnRsV1();
/* 749:    */     
/* 750:    */ 
/* 751:    */ 
/* 752:566 */     return localTypeTaxW2TrnRsV1;
/* 753:    */   }
/* 754:    */   
/* 755:    */   public TypeWireSyncRsV1 processWireSyncRqV1(TypeWireSyncRqV1 paramTypeWireSyncRqV1, TypeUserData paramTypeUserData)
/* 756:    */     throws RemoteException, FFSException
/* 757:    */   {
/* 758:571 */     TypeWireSyncRsV1 localTypeWireSyncRsV1 = new TypeWireSyncRsV1();
/* 759:    */     
/* 760:    */ 
/* 761:    */ 
/* 762:575 */     return localTypeWireSyncRsV1;
/* 763:    */   }
/* 764:    */   
/* 765:    */   public TypeWireTrnRsV1 processWireTrnRqV1(TypeWireTrnRqV1 paramTypeWireTrnRqV1, TypeUserData paramTypeUserData)
/* 766:    */     throws RemoteException, FFSException
/* 767:    */   {
/* 768:580 */     TypeWireTrnRsV1 localTypeWireTrnRsV1 = new TypeWireTrnRsV1();
/* 769:    */     
/* 770:    */ 
/* 771:    */ 
/* 772:584 */     return localTypeWireTrnRsV1;
/* 773:    */   }
/* 774:    */   
/* 775:    */   public void setSessionContext(SessionContext paramSessionContext)
/* 776:    */     throws RemoteException
/* 777:    */   {
/* 778:593 */     this._context = paramSessionContext;
/* 779:    */   }
/* 780:    */ }


/* Location:           D:\drops\jd\jars\OFX200Callback.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.OFX200CallbackBean
 * JD-Core Version:    0.7.0.1
 */