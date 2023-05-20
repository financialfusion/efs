/*    1:     */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices;
/*    2:     */ 
/*    3:     */ import com.ffusion.ffs.bpw.interfaces.BPWHist;
/*    4:     */ import com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo;
/*    5:     */ import com.ffusion.ffs.bpw.interfaces.CustPayeeRslt;
/*    6:     */ import com.ffusion.ffs.bpw.interfaces.CustomerBankInfo;
/*    7:     */ import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
/*    8:     */ import com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo;
/*    9:     */ import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
/*   10:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
/*   11:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
/*   12:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*   13:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
/*   14:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRslt;
/*   15:     */ import com.ffusion.ffs.bpw.interfaces.PmtInfo;
/*   16:     */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfo;
/*   18:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
/*   19:     */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*   20:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   21:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   22:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1;
/*   23:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1;
/*   24:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
/*   25:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
/*   26:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
/*   27:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
/*   28:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
/*   29:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
/*   30:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1;
/*   31:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1;
/*   32:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
/*   33:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
/*   34:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
/*   35:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
/*   36:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
/*   37:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
/*   38:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
/*   39:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
/*   43:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
/*   44:     */ import com.ibm.ejs.container.EJSWrapper;
/*   45:     */ import java.io.Serializable;
/*   46:     */ import java.rmi.Remote;
/*   47:     */ import javax.ejb.EJBHome;
/*   48:     */ import javax.ejb.EJBObject;
/*   49:     */ import javax.ejb.Handle;
/*   50:     */ import javax.ejb.RemoveException;
/*   51:     */ import javax.rmi.CORBA.Tie;
/*   52:     */ import javax.rmi.CORBA.Util;
/*   53:     */ import org.omg.CORBA.BAD_OPERATION;
/*   54:     */ import org.omg.CORBA.ORB;
/*   55:     */ import org.omg.CORBA.SystemException;
/*   56:     */ import org.omg.CORBA.portable.Delegate;
/*   57:     */ import org.omg.CORBA.portable.ResponseHandler;
/*   58:     */ import org.omg.CORBA.portable.UnknownException;
/*   59:     */ 
/*   60:     */ public class _EJSRemoteStatelessIOFX200BPWServices_Tie
/*   61:     */   extends org.omg.CORBA_2_3.portable.ObjectImpl
/*   62:     */   implements Tie
/*   63:     */ {
/*   64:  69 */   private EJSRemoteStatelessIOFX200BPWServices target = null;
/*   65:  70 */   private ORB orb = null;
/*   66:  72 */   private static final String[] _type_ids = {
/*   67:  73 */     "RMI:com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices:0000000000000000", 
/*   68:  74 */     "RMI:javax.ejb.EJBObject:0000000000000000", 
/*   69:  75 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
/*   70:  76 */     "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000" };
/*   71:     */   
/*   72:     */   public void setTarget(Remote paramRemote)
/*   73:     */   {
/*   74:  80 */     this.target = ((EJSRemoteStatelessIOFX200BPWServices)paramRemote);
/*   75:     */   }
/*   76:     */   
/*   77:     */   public Remote getTarget()
/*   78:     */   {
/*   79:  84 */     return this.target;
/*   80:     */   }
/*   81:     */   
/*   82:     */   public org.omg.CORBA.Object thisObject()
/*   83:     */   {
/*   84:  88 */     return this;
/*   85:     */   }
/*   86:     */   
/*   87:     */   public void deactivate()
/*   88:     */   {
/*   89:  92 */     if (this.orb != null)
/*   90:     */     {
/*   91:  93 */       this.orb.disconnect(this);
/*   92:  94 */       _set_delegate(null);
/*   93:     */     }
/*   94:     */   }
/*   95:     */   
/*   96:     */   public ORB orb()
/*   97:     */   {
/*   98:  99 */     return _orb();
/*   99:     */   }
/*  100:     */   
/*  101:     */   public void orb(ORB paramORB)
/*  102:     */   {
/*  103: 103 */     paramORB.connect(this);
/*  104:     */   }
/*  105:     */   
/*  106:     */   public void _set_delegate(Delegate paramDelegate)
/*  107:     */   {
/*  108: 107 */     super._set_delegate(paramDelegate);
/*  109: 108 */     if (paramDelegate != null) {
/*  110: 109 */       this.orb = _orb();
/*  111:     */     } else {
/*  112: 111 */       this.orb = null;
/*  113:     */     }
/*  114:     */   }
/*  115:     */   
/*  116:     */   public String[] _ids()
/*  117:     */   {
/*  118: 115 */     return _type_ids;
/*  119:     */   }
/*  120:     */   
/*  121:     */   public org.omg.CORBA.portable.OutputStream _invoke(String paramString, org.omg.CORBA.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  122:     */     throws SystemException
/*  123:     */   {
/*  124:     */     try
/*  125:     */     {
/*  126: 120 */       org.omg.CORBA_2_3.portable.InputStream localInputStream = 
/*  127: 121 */         (org.omg.CORBA_2_3.portable.InputStream)paramInputStream;
/*  128: 122 */       switch (paramString.hashCode())
/*  129:     */       {
/*  130:     */       case -1851674302: 
/*  131: 124 */         if (paramString.equals("getPayeeByListId")) {
/*  132: 125 */           return getPayeeByListId(localInputStream, paramResponseHandler);
/*  133:     */         }
/*  134:     */       case -1791303223: 
/*  135: 128 */         if (paramString.equals("addCustomerBankInfo")) {
/*  136: 129 */           return addCustomerBankInfo(localInputStream, paramResponseHandler);
/*  137:     */         }
/*  138:     */       case -1776939455: 
/*  139: 132 */         if (paramString.equals("deactivateCustomers")) {
/*  140: 133 */           return deactivateCustomers(localInputStream, paramResponseHandler);
/*  141:     */         }
/*  142:     */       case -1768635698: 
/*  143: 136 */         if (paramString.equals("getCustomerByFI")) {
/*  144: 137 */           return getCustomerByFI(localInputStream, paramResponseHandler);
/*  145:     */         }
/*  146:     */       case -1681582906: 
/*  147: 140 */         if (paramString.equals("getCustomerByGroupAndFI")) {
/*  148: 141 */           return getCustomerByGroupAndFI(localInputStream, paramResponseHandler);
/*  149:     */         }
/*  150:     */       case -1677309325: 
/*  151: 144 */         if (paramString.equals("getPmtHistory")) {
/*  152: 145 */           return getPmtHistory(localInputStream, paramResponseHandler);
/*  153:     */         }
/*  154:     */       case -1674523064: 
/*  155: 148 */         if (paramString.equals("getRecIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  156: 149 */           return getRecIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  157:     */         }
/*  158:     */       case -1604658875: 
/*  159: 152 */         if (paramString.equals("getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  160: 153 */           return getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  161:     */         }
/*  162:     */       case -1568105773: 
/*  163: 156 */         if (paramString.equals("deleteCustomerBankInfo")) {
/*  164: 157 */           return deleteCustomerBankInfo(localInputStream, paramResponseHandler);
/*  165:     */         }
/*  166:     */       case -1550521068: 
/*  167: 160 */         if (paramString.equals("_get_EJBHome")) {
/*  168: 161 */           return _get_EJBHome(localInputStream, paramResponseHandler);
/*  169:     */         }
/*  170:     */       case -1530678733: 
/*  171: 164 */         if (paramString.equals("getPmtStatus")) {
/*  172: 165 */           return getPmtStatus(localInputStream, paramResponseHandler);
/*  173:     */         }
/*  174:     */       case -1492377383: 
/*  175: 168 */         if (paramString.equals("getRecIntraById__CORBA_WStringValue")) {
/*  176: 169 */           return getRecIntraById__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  177:     */         }
/*  178:     */       case -1487596648: 
/*  179: 172 */         if (paramString.equals("setPayeeStatus")) {
/*  180: 173 */           return setPayeeStatus(localInputStream, paramResponseHandler);
/*  181:     */         }
/*  182:     */       case -1477108851: 
/*  183: 176 */         if (paramString.equals("getCustomerProductAccessInfo")) {
/*  184: 177 */           return getCustomerProductAccessInfo(localInputStream, paramResponseHandler);
/*  185:     */         }
/*  186:     */       case -1454154143: 
/*  187: 180 */         if (paramString.equals("getSmartDate")) {
/*  188: 181 */           return getSmartDate(localInputStream, paramResponseHandler);
/*  189:     */         }
/*  190:     */       case -1434553393: 
/*  191: 184 */         if (paramString.equals("getRecPmtById")) {
/*  192: 185 */           return getRecPmtById(localInputStream, paramResponseHandler);
/*  193:     */         }
/*  194:     */       case -1406620510: 
/*  195: 188 */         if (paramString.equals("addCustomerProductAccessInfo")) {
/*  196: 189 */           return addCustomerProductAccessInfo(localInputStream, paramResponseHandler);
/*  197:     */         }
/*  198:     */       case -1348384111: 
/*  199: 192 */         if (paramString.equals("processPayeeTrnRqV1")) {
/*  200: 193 */           return processPayeeTrnRqV1(localInputStream, paramResponseHandler);
/*  201:     */         }
/*  202:     */       case -1309038430: 
/*  203: 196 */         if (paramString.equals("activateCustomers")) {
/*  204: 197 */           return activateCustomers(localInputStream, paramResponseHandler);
/*  205:     */         }
/*  206:     */       case -1275891110: 
/*  207: 200 */         if (paramString.equals("updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo")) {
/*  208: 201 */           return updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo(localInputStream, paramResponseHandler);
/*  209:     */         }
/*  210:     */       case -1260463146: 
/*  211: 204 */         if (paramString.equals("getPreferredPayees")) {
/*  212: 205 */           return getPreferredPayees(localInputStream, paramResponseHandler);
/*  213:     */         }
/*  214:     */       case -1250228441: 
/*  215: 208 */         if (paramString.equals("addPayee")) {
/*  216: 209 */           return addPayee(localInputStream, paramResponseHandler);
/*  217:     */         }
/*  218:     */       case -1227350666: 
/*  219: 212 */         if (paramString.equals("searchGlobalPayees")) {
/*  220: 213 */           return searchGlobalPayees(localInputStream, paramResponseHandler);
/*  221:     */         }
/*  222:     */       case -1136226211: 
/*  223: 216 */         if (paramString.equals("deletePayee")) {
/*  224: 217 */           return deletePayee(localInputStream, paramResponseHandler);
/*  225:     */         }
/*  226:     */       case -1011244123: 
/*  227: 220 */         if (paramString.equals("_get_primaryKey")) {
/*  228: 221 */           return _get_primaryKey(localInputStream, paramResponseHandler);
/*  229:     */         }
/*  230:     */       case -992331665: 
/*  231: 224 */         if (paramString.equals("validateMetavanteCustAcctByCustomerID")) {
/*  232: 225 */           return validateMetavanteCustAcctByCustomerID(localInputStream, paramResponseHandler);
/*  233:     */         }
/*  234:     */       case -989279594: 
/*  235: 228 */         if (paramString.equals("getPendingIntras")) {
/*  236: 229 */           return getPendingIntras(localInputStream, paramResponseHandler);
/*  237:     */         }
/*  238:     */       case -934610812: 
/*  239: 232 */         if (paramString.equals("remove")) {
/*  240: 233 */           return remove(localInputStream, paramResponseHandler);
/*  241:     */         }
/*  242:     */       case -863274058: 
/*  243: 236 */         if (paramString.equals("deletePayees")) {
/*  244: 237 */           return deletePayees(localInputStream, paramResponseHandler);
/*  245:     */         }
/*  246:     */       case -841809946: 
/*  247: 240 */         if (paramString.equals("getPayeeIDs")) {
/*  248: 241 */           return getPayeeIDs(localInputStream, paramResponseHandler);
/*  249:     */         }
/*  250:     */       case -795132762: 
/*  251: 244 */         if (paramString.equals("processPmtInqTrnRqV1")) {
/*  252: 245 */           return processPmtInqTrnRqV1(localInputStream, paramResponseHandler);
/*  253:     */         }
/*  254:     */       case -793413663: 
/*  255: 248 */         if (paramString.equals("updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long")) {
/*  256: 249 */           return updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long(localInputStream, paramResponseHandler);
/*  257:     */         }
/*  258:     */       case -606368534: 
/*  259: 252 */         if (paramString.equals("processFundAllocRslt")) {
/*  260: 253 */           return processFundAllocRslt(localInputStream, paramResponseHandler);
/*  261:     */         }
/*  262:     */       case -565874860: 
/*  263: 256 */         if (paramString.equals("processRecPmtTrnRqV1")) {
/*  264: 257 */           return processRecPmtTrnRqV1(localInputStream, paramResponseHandler);
/*  265:     */         }
/*  266:     */       case -554059329: 
/*  267: 260 */         if (paramString.equals("getConsumerCrossRef")) {
/*  268: 261 */           return getConsumerCrossRef(localInputStream, paramResponseHandler);
/*  269:     */         }
/*  270:     */       case -484868392: 
/*  271: 264 */         if (paramString.equals("deleteCustomerProductAccessInfo")) {
/*  272: 265 */           return deleteCustomerProductAccessInfo(localInputStream, paramResponseHandler);
/*  273:     */         }
/*  274:     */       case -473045887: 
/*  275: 268 */         if (paramString.equals("processFundRevertRslt")) {
/*  276: 269 */           return processFundRevertRslt(localInputStream, paramResponseHandler);
/*  277:     */         }
/*  278:     */       case -397719766: 
/*  279: 272 */         if (paramString.equals("getPendingPmtsAndHistoryByCustomerID")) {
/*  280: 273 */           return getPendingPmtsAndHistoryByCustomerID(localInputStream, paramResponseHandler);
/*  281:     */         }
/*  282:     */       case -357885638: 
/*  283: 276 */         if (paramString.equals("getAccountTypesMap")) {
/*  284: 277 */           return getAccountTypesMap(localInputStream, paramResponseHandler);
/*  285:     */         }
/*  286:     */       case -345839895: 
/*  287: 280 */         if (paramString.equals("getCustomerByCategory")) {
/*  288: 281 */           return getCustomerByCategory(localInputStream, paramResponseHandler);
/*  289:     */         }
/*  290:     */       case -322756286: 
/*  291: 284 */         if (paramString.equals("processPayeeRslt")) {
/*  292: 285 */           return processPayeeRslt(localInputStream, paramResponseHandler);
/*  293:     */         }
/*  294:     */       case -307326108: 
/*  295: 288 */         if (paramString.equals("getPayees__CORBA_WStringValue")) {
/*  296: 289 */           return getPayees__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  297:     */         }
/*  298:     */       case -304252047: 
/*  299: 292 */         if (paramString.equals("getCustomerByCategoryAndFI")) {
/*  300: 293 */           return getCustomerByCategoryAndFI(localInputStream, paramResponseHandler);
/*  301:     */         }
/*  302:     */       case -172699861: 
/*  303: 296 */         if (paramString.equals("processRecPmtSyncRqV1")) {
/*  304: 297 */           return processRecPmtSyncRqV1(localInputStream, paramResponseHandler);
/*  305:     */         }
/*  306:     */       case -122856768: 
/*  307: 300 */         if (paramString.equals("getPayees__CORBA_WStringValue__long")) {
/*  308: 301 */           return getPayees__CORBA_WStringValue__long(localInputStream, paramResponseHandler);
/*  309:     */         }
/*  310:     */       case -108337693: 
/*  311: 304 */         if (paramString.equals("addPayeeFromBackend")) {
/*  312: 305 */           return addPayeeFromBackend(localInputStream, paramResponseHandler);
/*  313:     */         }
/*  314:     */       case -74077855: 
/*  315: 308 */         if (paramString.equals("findPayeeByID")) {
/*  316: 309 */           return findPayeeByID(localInputStream, paramResponseHandler);
/*  317:     */         }
/*  318:     */       case -6786256: 
/*  319: 312 */         if (paramString.equals("addPayeeRouteInfo")) {
/*  320: 313 */           return addPayeeRouteInfo(localInputStream, paramResponseHandler);
/*  321:     */         }
/*  322:     */       case 103701334: 
/*  323: 316 */         if (paramString.equals("checkPayeeEditMask")) {
/*  324: 317 */           return checkPayeeEditMask(localInputStream, paramResponseHandler);
/*  325:     */         }
/*  326:     */       case 211047947: 
/*  327: 320 */         if (paramString.equals("updatePayeeFromBackend")) {
/*  328: 321 */           return updatePayeeFromBackend(localInputStream, paramResponseHandler);
/*  329:     */         }
/*  330:     */       case 292003558: 
/*  331: 324 */         if (paramString.equals("getPendingIntrasByCustomerID")) {
/*  332: 325 */           return getPendingIntrasByCustomerID(localInputStream, paramResponseHandler);
/*  333:     */         }
/*  334:     */       case 315560173: 
/*  335: 328 */         if (paramString.equals("processRecIntraTrnRqV1")) {
/*  336: 329 */           return processRecIntraTrnRqV1(localInputStream, paramResponseHandler);
/*  337:     */         }
/*  338:     */       case 324049180: 
/*  339: 332 */         if (paramString.equals("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  340: 333 */           return deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  341:     */         }
/*  342:     */       case 359880623: 
/*  343: 336 */         if (paramString.equals("processCustPayeeRslt")) {
/*  344: 337 */           return processCustPayeeRslt(localInputStream, paramResponseHandler);
/*  345:     */         }
/*  346:     */       case 422614663: 
/*  347: 340 */         if (paramString.equals("validateMetavanteCustAcctByConsumerID")) {
/*  348: 341 */           return validateMetavanteCustAcctByConsumerID(localInputStream, paramResponseHandler);
/*  349:     */         }
/*  350:     */       case 472920637: 
/*  351: 344 */         if (paramString.equals("getPendingPmts")) {
/*  352: 345 */           return getPendingPmts(localInputStream, paramResponseHandler);
/*  353:     */         }
/*  354:     */       case 492840643: 
/*  355: 348 */         if (paramString.equals("getPendingIntrasAndHistoryByCustomerID")) {
/*  356: 349 */           return getPendingIntrasAndHistoryByCustomerID(localInputStream, paramResponseHandler);
/*  357:     */         }
/*  358:     */       case 637031611: 
/*  359: 352 */         if (paramString.equals("modifyCustomers")) {
/*  360: 353 */           return modifyCustomers(localInputStream, paramResponseHandler);
/*  361:     */         }
/*  362:     */       case 646481814: 
/*  363: 356 */         if (paramString.equals("getPmtById__CORBA_WStringValue")) {
/*  364: 357 */           return getPmtById__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  365:     */         }
/*  366:     */       case 735120546: 
/*  367: 360 */         if (paramString.equals("processPmtTrnRqV1")) {
/*  368: 361 */           return processPmtTrnRqV1(localInputStream, paramResponseHandler);
/*  369:     */         }
/*  370:     */       case 735123217: 
/*  371: 364 */         if (paramString.equals("processPmtTrnRslt")) {
/*  372: 365 */           return processPmtTrnRslt(localInputStream, paramResponseHandler);
/*  373:     */         }
/*  374:     */       case 736662203: 
/*  375: 368 */         if (paramString.equals("processIntraTrnRqV1")) {
/*  376: 369 */           return processIntraTrnRqV1(localInputStream, paramResponseHandler);
/*  377:     */         }
/*  378:     */       case 736664874: 
/*  379: 372 */         if (paramString.equals("processIntraTrnRslt")) {
/*  380: 373 */           return processIntraTrnRslt(localInputStream, paramResponseHandler);
/*  381:     */         }
/*  382:     */       case 832529100: 
/*  383: 376 */         if (paramString.equals("getIntraHistory")) {
/*  384: 377 */           return getIntraHistory(localInputStream, paramResponseHandler);
/*  385:     */         }
/*  386:     */       case 863515172: 
/*  387: 380 */         if (paramString.equals("getGlobalPayees")) {
/*  388: 381 */           return getGlobalPayees(localInputStream, paramResponseHandler);
/*  389:     */         }
/*  390:     */       case 891667469: 
/*  391: 384 */         if (paramString.equals("getPendingPmtsByCustomerID")) {
/*  392: 385 */           return getPendingPmtsByCustomerID(localInputStream, paramResponseHandler);
/*  393:     */         }
/*  394:     */       case 912846462: 
/*  395: 388 */         if (paramString.equals("getCustomerBankInfo")) {
/*  396: 389 */           return getCustomerBankInfo(localInputStream, paramResponseHandler);
/*  397:     */         }
/*  398:     */       case 935619363: 
/*  399: 392 */         if (paramString.equals("getIntraByRecSrvrTId")) {
/*  400: 393 */           return getIntraByRecSrvrTId(localInputStream, paramResponseHandler);
/*  401:     */         }
/*  402:     */       case 1036758282: 
/*  403: 396 */         if (paramString.equals("addConsumerCrossRef")) {
/*  404: 397 */           return addConsumerCrossRef(localInputStream, paramResponseHandler);
/*  405:     */         }
/*  406:     */       case 1050730221: 
/*  407: 400 */         if (paramString.equals("getCustomersInfo")) {
/*  408: 401 */           return getCustomersInfo(localInputStream, paramResponseHandler);
/*  409:     */         }
/*  410:     */       case 1089499445: 
/*  411: 404 */         if (paramString.equals("getCustomerByTypeAndFI")) {
/*  412: 405 */           return getCustomerByTypeAndFI(localInputStream, paramResponseHandler);
/*  413:     */         }
/*  414:     */       case 1148610213: 
/*  415: 408 */         if (paramString.equals("getCustomerByType")) {
/*  416: 409 */           return getCustomerByType(localInputStream, paramResponseHandler);
/*  417:     */         }
/*  418:     */       case 1234963572: 
/*  419: 412 */         if (paramString.equals("getCustomerByGroup")) {
/*  420: 413 */           return getCustomerByGroup(localInputStream, paramResponseHandler);
/*  421:     */         }
/*  422:     */       case 1259955732: 
/*  423: 416 */         if (paramString.equals("deleteConsumerCrossRef")) {
/*  424: 417 */           return deleteConsumerCrossRef(localInputStream, paramResponseHandler);
/*  425:     */         }
/*  426:     */       case 1264756395: 
/*  427: 420 */         if (paramString.equals("isIdentical")) {
/*  428: 421 */           return isIdentical(localInputStream, paramResponseHandler);
/*  429:     */         }
/*  430:     */       case 1339317134: 
/*  431: 424 */         if (paramString.equals("processPayeeSyncRqV1")) {
/*  432: 425 */           return processPayeeSyncRqV1(localInputStream, paramResponseHandler);
/*  433:     */         }
/*  434:     */       case 1345737269: 
/*  435: 428 */         if (paramString.equals("getPayeeNames__CORBA_WStringValue__long")) {
/*  436: 429 */           return getPayeeNames__CORBA_WStringValue__long(localInputStream, paramResponseHandler);
/*  437:     */         }
/*  438:     */       case 1381982386: 
/*  439: 432 */         if (paramString.equals("processRecIntraSyncRqV1")) {
/*  440: 433 */           return processRecIntraSyncRqV1(localInputStream, paramResponseHandler);
/*  441:     */         }
/*  442:     */       case 1473661108: 
/*  443: 436 */         if (paramString.equals("addCustomers")) {
/*  444: 437 */           return addCustomers(localInputStream, paramResponseHandler);
/*  445:     */         }
/*  446:     */       case 1503452061: 
/*  447: 440 */         if (paramString.equals("processPmtSyncRqV1")) {
/*  448: 441 */           return processPmtSyncRqV1(localInputStream, paramResponseHandler);
/*  449:     */         }
/*  450:     */       case 1509559389: 
/*  451: 444 */         if (paramString.equals("getIntraById__CORBA_WStringValue")) {
/*  452: 445 */           return getIntraById__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  453:     */         }
/*  454:     */       case 1547577633: 
/*  455: 448 */         if (paramString.equals("getMostUsedPayees")) {
/*  456: 449 */           return getMostUsedPayees(localInputStream, paramResponseHandler);
/*  457:     */         }
/*  458:     */       case 1551243428: 
/*  459: 452 */         if (paramString.equals("processIntraSyncRqV1")) {
/*  460: 453 */           return processIntraSyncRqV1(localInputStream, paramResponseHandler);
/*  461:     */         }
/*  462:     */       case 1634875000: 
/*  463: 456 */         if (paramString.equals("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long")) {
/*  464: 457 */           return deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long(localInputStream, paramResponseHandler);
/*  465:     */         }
/*  466:     */       case 1944413392: 
/*  467: 460 */         if (paramString.equals("_get_handle")) {
/*  468: 461 */           return _get_handle(localInputStream, paramResponseHandler);
/*  469:     */         }
/*  470:     */       case 1948936780: 
/*  471: 464 */         if (paramString.equals("_get_linkedPayees")) {
/*  472: 465 */           return _get_linkedPayees(localInputStream, paramResponseHandler);
/*  473:     */         }
/*  474:     */       case 2023718604: 
/*  475: 468 */         if (paramString.equals("getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  476: 469 */           return getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  477:     */         }
/*  478:     */       case 2135918233: 
/*  479: 472 */         if (paramString.equals("getPayeeNames__CORBA_WStringValue")) {
/*  480: 473 */           return getPayeeNames__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  481:     */         }
/*  482:     */         break;
/*  483:     */       }
/*  484: 476 */       throw new BAD_OPERATION();
/*  485:     */     }
/*  486:     */     catch (SystemException localSystemException)
/*  487:     */     {
/*  488: 478 */       throw localSystemException;
/*  489:     */     }
/*  490:     */     catch (Throwable localThrowable)
/*  491:     */     {
/*  492: 480 */       throw new UnknownException(localThrowable);
/*  493:     */     }
/*  494:     */   }
/*  495:     */   
/*  496:     */   private org.omg.CORBA.portable.OutputStream _get_EJBHome(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  497:     */     throws Throwable
/*  498:     */   {
/*  499: 485 */     EJBHome localEJBHome = this.target.getEJBHome();
/*  500: 486 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  501: 487 */     Util.writeRemoteObject(localOutputStream, localEJBHome);
/*  502: 488 */     return localOutputStream;
/*  503:     */   }
/*  504:     */   
/*  505:     */   private org.omg.CORBA.portable.OutputStream _get_primaryKey(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  506:     */     throws Throwable
/*  507:     */   {
/*  508: 492 */     java.lang.Object localObject = this.target.getPrimaryKey();
/*  509: 493 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  510: 494 */     Util.writeAny(localOutputStream, localObject);
/*  511: 495 */     return localOutputStream;
/*  512:     */   }
/*  513:     */   
/*  514:     */   private org.omg.CORBA.portable.OutputStream remove(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  515:     */     throws Throwable
/*  516:     */   {
/*  517:     */     try
/*  518:     */     {
/*  519: 500 */       this.target.remove();
/*  520:     */     }
/*  521:     */     catch (RemoveException localRemoveException)
/*  522:     */     {
/*  523: 502 */       String str = "IDL:javax/ejb/RemoveEx:1.0";
/*  524: 503 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  525: 504 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  526: 505 */       localOutputStream1.write_string(str);
/*  527: 506 */       localOutputStream1.write_value(localRemoveException, RemoveException.class);
/*  528: 507 */       return localOutputStream1;
/*  529:     */     }
/*  530: 509 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  531: 510 */     return localOutputStream;
/*  532:     */   }
/*  533:     */   
/*  534:     */   private org.omg.CORBA.portable.OutputStream _get_handle(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  535:     */     throws Throwable
/*  536:     */   {
/*  537: 514 */     Handle localHandle = this.target.getHandle();
/*  538: 515 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  539: 516 */     Util.writeAbstractObject(localOutputStream, localHandle);
/*  540: 517 */     return localOutputStream;
/*  541:     */   }
/*  542:     */   
/*  543:     */   private org.omg.CORBA.portable.OutputStream isIdentical(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  544:     */     throws Throwable
/*  545:     */   {
/*  546: 521 */     EJBObject localEJBObject = (EJBObject)paramInputStream.read_Object(EJBObject.class);
/*  547: 522 */     boolean bool = this.target.isIdentical(localEJBObject);
/*  548: 523 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  549: 524 */     localOutputStream.write_boolean(bool);
/*  550: 525 */     return localOutputStream;
/*  551:     */   }
/*  552:     */   
/*  553:     */   private org.omg.CORBA.portable.OutputStream addCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  554:     */     throws Throwable
/*  555:     */   {
/*  556: 529 */     CustomerInfo[] arrayOfCustomerInfo = (CustomerInfo[])paramInputStream.read_value(new CustomerInfo[0].getClass());
/*  557: 530 */     int i = this.target.addCustomers(arrayOfCustomerInfo);
/*  558: 531 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  559: 532 */     localOutputStream.write_long(i);
/*  560: 533 */     return localOutputStream;
/*  561:     */   }
/*  562:     */   
/*  563:     */   private org.omg.CORBA.portable.OutputStream modifyCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  564:     */     throws Throwable
/*  565:     */   {
/*  566: 537 */     CustomerInfo[] arrayOfCustomerInfo = (CustomerInfo[])paramInputStream.read_value(new CustomerInfo[0].getClass());
/*  567: 538 */     int i = this.target.modifyCustomers(arrayOfCustomerInfo);
/*  568: 539 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  569: 540 */     localOutputStream.write_long(i);
/*  570: 541 */     return localOutputStream;
/*  571:     */   }
/*  572:     */   
/*  573:     */   private org.omg.CORBA.portable.OutputStream deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  574:     */     throws Throwable
/*  575:     */   {
/*  576: 545 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  577: 546 */     int i = this.target.deleteCustomers(arrayOfString);
/*  578: 547 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  579: 548 */     localOutputStream.write_long(i);
/*  580: 549 */     return localOutputStream;
/*  581:     */   }
/*  582:     */   
/*  583:     */   private org.omg.CORBA.portable.OutputStream deactivateCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  584:     */     throws Throwable
/*  585:     */   {
/*  586: 553 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  587: 554 */     int i = this.target.deactivateCustomers(arrayOfString);
/*  588: 555 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  589: 556 */     localOutputStream.write_long(i);
/*  590: 557 */     return localOutputStream;
/*  591:     */   }
/*  592:     */   
/*  593:     */   private org.omg.CORBA.portable.OutputStream activateCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  594:     */     throws Throwable
/*  595:     */   {
/*  596: 561 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  597: 562 */     int i = this.target.activateCustomers(arrayOfString);
/*  598: 563 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  599: 564 */     localOutputStream.write_long(i);
/*  600: 565 */     return localOutputStream;
/*  601:     */   }
/*  602:     */   
/*  603:     */   private org.omg.CORBA.portable.OutputStream deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  604:     */     throws Throwable
/*  605:     */   {
/*  606: 569 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  607: 570 */     int i = paramInputStream.read_long();
/*  608: 571 */     int j = this.target.deleteCustomers(arrayOfString, i);
/*  609: 572 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  610: 573 */     localOutputStream.write_long(j);
/*  611: 574 */     return localOutputStream;
/*  612:     */   }
/*  613:     */   
/*  614:     */   private org.omg.CORBA.portable.OutputStream getCustomersInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  615:     */     throws Throwable
/*  616:     */   {
/*  617: 578 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  618: 579 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomersInfo(arrayOfString);
/*  619: 580 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  620: 581 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  621: 582 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  622: 583 */     return localOutputStream;
/*  623:     */   }
/*  624:     */   
/*  625:     */   private org.omg.CORBA.portable.OutputStream getCustomerByType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  626:     */     throws Throwable
/*  627:     */   {
/*  628: 587 */     String str = (String)paramInputStream.read_value(String.class);
/*  629: 588 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByType(str);
/*  630: 589 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  631: 590 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  632: 591 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  633: 592 */     return localOutputStream;
/*  634:     */   }
/*  635:     */   
/*  636:     */   private org.omg.CORBA.portable.OutputStream getCustomerByFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  637:     */     throws Throwable
/*  638:     */   {
/*  639: 596 */     String str = (String)paramInputStream.read_value(String.class);
/*  640: 597 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByFI(str);
/*  641: 598 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  642: 599 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  643: 600 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  644: 601 */     return localOutputStream;
/*  645:     */   }
/*  646:     */   
/*  647:     */   private org.omg.CORBA.portable.OutputStream getCustomerByCategory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  648:     */     throws Throwable
/*  649:     */   {
/*  650: 605 */     String str = (String)paramInputStream.read_value(String.class);
/*  651: 606 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByCategory(str);
/*  652: 607 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  653: 608 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  654: 609 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  655: 610 */     return localOutputStream;
/*  656:     */   }
/*  657:     */   
/*  658:     */   private org.omg.CORBA.portable.OutputStream getCustomerByGroup(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  659:     */     throws Throwable
/*  660:     */   {
/*  661: 614 */     String str = (String)paramInputStream.read_value(String.class);
/*  662: 615 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByGroup(str);
/*  663: 616 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  664: 617 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  665: 618 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  666: 619 */     return localOutputStream;
/*  667:     */   }
/*  668:     */   
/*  669:     */   private org.omg.CORBA.portable.OutputStream getCustomerByTypeAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  670:     */     throws Throwable
/*  671:     */   {
/*  672: 623 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  673: 624 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  674: 625 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByTypeAndFI(str1, str2);
/*  675: 626 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  676: 627 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  677: 628 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  678: 629 */     return localOutputStream;
/*  679:     */   }
/*  680:     */   
/*  681:     */   private org.omg.CORBA.portable.OutputStream getCustomerByCategoryAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  682:     */     throws Throwable
/*  683:     */   {
/*  684: 633 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  685: 634 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  686: 635 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByCategoryAndFI(str1, str2);
/*  687: 636 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  688: 637 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  689: 638 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  690: 639 */     return localOutputStream;
/*  691:     */   }
/*  692:     */   
/*  693:     */   private org.omg.CORBA.portable.OutputStream getCustomerByGroupAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  694:     */     throws Throwable
/*  695:     */   {
/*  696: 643 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  697: 644 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  698: 645 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByGroupAndFI(str1, str2);
/*  699: 646 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  700: 647 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  701: 648 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  702: 649 */     return localOutputStream;
/*  703:     */   }
/*  704:     */   
/*  705:     */   private org.omg.CORBA.portable.OutputStream _get_linkedPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  706:     */     throws Throwable
/*  707:     */   {
/*  708: 653 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getLinkedPayees();
/*  709: 654 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  710: 655 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  711: 656 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/*  712: 657 */     return localOutputStream;
/*  713:     */   }
/*  714:     */   
/*  715:     */   private org.omg.CORBA.portable.OutputStream getMostUsedPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  716:     */     throws Throwable
/*  717:     */   {
/*  718: 661 */     int i = paramInputStream.read_long();
/*  719: 662 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getMostUsedPayees(i);
/*  720: 663 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  721: 664 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  722: 665 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/*  723: 666 */     return localOutputStream;
/*  724:     */   }
/*  725:     */   
/*  726:     */   private org.omg.CORBA.portable.OutputStream getPreferredPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  727:     */     throws Throwable
/*  728:     */   {
/*  729: 670 */     String str = (String)paramInputStream.read_value(String.class);
/*  730: 671 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getPreferredPayees(str);
/*  731: 672 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  732: 673 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  733: 674 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/*  734: 675 */     return localOutputStream;
/*  735:     */   }
/*  736:     */   
/*  737:     */   private org.omg.CORBA.portable.OutputStream getPendingPmtsByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  738:     */     throws Throwable
/*  739:     */   {
/*  740: 679 */     String str = (String)paramInputStream.read_value(String.class);
/*  741: 680 */     int i = paramInputStream.read_long();
/*  742: 681 */     TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = this.target.getPendingPmtsByCustomerID(str, i);
/*  743: 682 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  744: 683 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  745: 684 */     localOutputStream.write_value(cast_array(arrayOfTypePmtSyncRsV1), new TypePmtSyncRsV1[0].getClass());
/*  746: 685 */     return localOutputStream;
/*  747:     */   }
/*  748:     */   
/*  749:     */   private org.omg.CORBA.portable.OutputStream getPendingPmtsAndHistoryByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  750:     */     throws Throwable
/*  751:     */   {
/*  752: 689 */     String str = (String)paramInputStream.read_value(String.class);
/*  753: 690 */     int i = paramInputStream.read_long();
/*  754: 691 */     int j = paramInputStream.read_long();
/*  755: 692 */     TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = this.target.getPendingPmtsAndHistoryByCustomerID(str, i, j);
/*  756: 693 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  757: 694 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  758: 695 */     localOutputStream.write_value(cast_array(arrayOfTypePmtSyncRsV1), new TypePmtSyncRsV1[0].getClass());
/*  759: 696 */     return localOutputStream;
/*  760:     */   }
/*  761:     */   
/*  762:     */   private org.omg.CORBA.portable.OutputStream getPendingIntrasByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  763:     */     throws Throwable
/*  764:     */   {
/*  765: 700 */     String str = (String)paramInputStream.read_value(String.class);
/*  766: 701 */     int i = paramInputStream.read_long();
/*  767: 702 */     TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = this.target.getPendingIntrasByCustomerID(str, i);
/*  768: 703 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  769: 704 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  770: 705 */     localOutputStream.write_value(cast_array(arrayOfTypeIntraSyncRsV1), new TypeIntraSyncRsV1[0].getClass());
/*  771: 706 */     return localOutputStream;
/*  772:     */   }
/*  773:     */   
/*  774:     */   private org.omg.CORBA.portable.OutputStream getPendingIntrasAndHistoryByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  775:     */     throws Throwable
/*  776:     */   {
/*  777: 710 */     String str = (String)paramInputStream.read_value(String.class);
/*  778: 711 */     int i = paramInputStream.read_long();
/*  779: 712 */     int j = paramInputStream.read_long();
/*  780: 713 */     TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = this.target.getPendingIntrasAndHistoryByCustomerID(str, i, j);
/*  781: 714 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  782: 715 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  783: 716 */     localOutputStream.write_value(cast_array(arrayOfTypeIntraSyncRsV1), new TypeIntraSyncRsV1[0].getClass());
/*  784: 717 */     return localOutputStream;
/*  785:     */   }
/*  786:     */   
/*  787:     */   private org.omg.CORBA.portable.OutputStream getPendingPmts(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  788:     */     throws Throwable
/*  789:     */   {
/*  790: 721 */     TypePmtSyncRqV1 localTypePmtSyncRqV1 = (TypePmtSyncRqV1)paramInputStream.read_value(TypePmtSyncRqV1.class);
/*  791: 722 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  792: 723 */     int i = paramInputStream.read_long();
/*  793: 724 */     TypePmtSyncRsV1 localTypePmtSyncRsV1 = this.target.getPendingPmts(localTypePmtSyncRqV1, localTypeUserData, i);
/*  794: 725 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  795: 726 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  796: 727 */     localOutputStream.write_value(localTypePmtSyncRsV1, TypePmtSyncRsV1.class);
/*  797: 728 */     return localOutputStream;
/*  798:     */   }
/*  799:     */   
/*  800:     */   private org.omg.CORBA.portable.OutputStream getPendingIntras(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  801:     */     throws Throwable
/*  802:     */   {
/*  803: 732 */     TypeIntraSyncRqV1 localTypeIntraSyncRqV1 = (TypeIntraSyncRqV1)paramInputStream.read_value(TypeIntraSyncRqV1.class);
/*  804: 733 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  805: 734 */     int i = paramInputStream.read_long();
/*  806: 735 */     TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = this.target.getPendingIntras(localTypeIntraSyncRqV1, localTypeUserData, i);
/*  807: 736 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  808: 737 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  809: 738 */     localOutputStream.write_value(localTypeIntraSyncRsV1, TypeIntraSyncRsV1.class);
/*  810: 739 */     return localOutputStream;
/*  811:     */   }
/*  812:     */   
/*  813:     */   private org.omg.CORBA.portable.OutputStream getPmtStatus(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  814:     */     throws Throwable
/*  815:     */   {
/*  816: 743 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  817: 744 */     String str2 = this.target.getPmtStatus(str1);
/*  818: 745 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  819: 746 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  820: 747 */     localOutputStream.write_value(str2, String.class);
/*  821: 748 */     return localOutputStream;
/*  822:     */   }
/*  823:     */   
/*  824:     */   private org.omg.CORBA.portable.OutputStream checkPayeeEditMask(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  825:     */     throws Throwable
/*  826:     */   {
/*  827: 752 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  828: 753 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  829: 754 */     boolean bool = this.target.checkPayeeEditMask(str1, str2);
/*  830: 755 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  831: 756 */     localOutputStream.write_boolean(bool);
/*  832: 757 */     return localOutputStream;
/*  833:     */   }
/*  834:     */   
/*  835:     */   private org.omg.CORBA.portable.OutputStream processIntraTrnRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  836:     */     throws Throwable
/*  837:     */   {
/*  838: 761 */     IntraTrnRslt[] arrayOfIntraTrnRslt = (IntraTrnRslt[])paramInputStream.read_value(new IntraTrnRslt[0].getClass());
/*  839: 762 */     this.target.processIntraTrnRslt(arrayOfIntraTrnRslt);
/*  840: 763 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  841: 764 */     return localOutputStream;
/*  842:     */   }
/*  843:     */   
/*  844:     */   private org.omg.CORBA.portable.OutputStream processPmtTrnRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  845:     */     throws Throwable
/*  846:     */   {
/*  847: 768 */     PmtTrnRslt[] arrayOfPmtTrnRslt = (PmtTrnRslt[])paramInputStream.read_value(new PmtTrnRslt[0].getClass());
/*  848: 769 */     this.target.processPmtTrnRslt(arrayOfPmtTrnRslt);
/*  849: 770 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  850: 771 */     return localOutputStream;
/*  851:     */   }
/*  852:     */   
/*  853:     */   private org.omg.CORBA.portable.OutputStream processCustPayeeRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  854:     */     throws Throwable
/*  855:     */   {
/*  856: 775 */     CustPayeeRslt[] arrayOfCustPayeeRslt = (CustPayeeRslt[])paramInputStream.read_value(new CustPayeeRslt[0].getClass());
/*  857: 776 */     this.target.processCustPayeeRslt(arrayOfCustPayeeRslt);
/*  858: 777 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  859: 778 */     return localOutputStream;
/*  860:     */   }
/*  861:     */   
/*  862:     */   private org.omg.CORBA.portable.OutputStream processFundAllocRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  863:     */     throws Throwable
/*  864:     */   {
/*  865: 782 */     FundsAllocRslt[] arrayOfFundsAllocRslt = (FundsAllocRslt[])paramInputStream.read_value(new FundsAllocRslt[0].getClass());
/*  866: 783 */     this.target.processFundAllocRslt(arrayOfFundsAllocRslt);
/*  867: 784 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  868: 785 */     return localOutputStream;
/*  869:     */   }
/*  870:     */   
/*  871:     */   private org.omg.CORBA.portable.OutputStream processFundRevertRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  872:     */     throws Throwable
/*  873:     */   {
/*  874: 789 */     FundsAllocRslt[] arrayOfFundsAllocRslt = (FundsAllocRslt[])paramInputStream.read_value(new FundsAllocRslt[0].getClass());
/*  875: 790 */     this.target.processFundRevertRslt(arrayOfFundsAllocRslt);
/*  876: 791 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  877: 792 */     return localOutputStream;
/*  878:     */   }
/*  879:     */   
/*  880:     */   private org.omg.CORBA.portable.OutputStream processPayeeRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  881:     */     throws Throwable
/*  882:     */   {
/*  883: 796 */     PayeeRslt[] arrayOfPayeeRslt = (PayeeRslt[])paramInputStream.read_value(new PayeeRslt[0].getClass());
/*  884: 797 */     this.target.processPayeeRslt(arrayOfPayeeRslt);
/*  885: 798 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  886: 799 */     return localOutputStream;
/*  887:     */   }
/*  888:     */   
/*  889:     */   private org.omg.CORBA.portable.OutputStream addPayeeFromBackend(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  890:     */     throws Throwable
/*  891:     */   {
/*  892: 803 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/*  893: 804 */     String str = this.target.addPayeeFromBackend(localPayeeInfo);
/*  894: 805 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  895: 806 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  896: 807 */     localOutputStream.write_value(str, String.class);
/*  897: 808 */     return localOutputStream;
/*  898:     */   }
/*  899:     */   
/*  900:     */   private org.omg.CORBA.portable.OutputStream updatePayeeFromBackend(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  901:     */     throws Throwable
/*  902:     */   {
/*  903: 812 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/*  904: 813 */     PayeeInfo[] arrayOfPayeeInfo = this.target.updatePayeeFromBackend(localPayeeInfo);
/*  905: 814 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  906: 815 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  907: 816 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/*  908: 817 */     return localOutputStream;
/*  909:     */   }
/*  910:     */   
/*  911:     */   private org.omg.CORBA.portable.OutputStream addPayeeRouteInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  912:     */     throws Throwable
/*  913:     */   {
/*  914: 821 */     PayeeRouteInfo localPayeeRouteInfo = (PayeeRouteInfo)paramInputStream.read_value(PayeeRouteInfo.class);
/*  915: 822 */     this.target.addPayeeRouteInfo(localPayeeRouteInfo);
/*  916: 823 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  917: 824 */     return localOutputStream;
/*  918:     */   }
/*  919:     */   
/*  920:     */   private org.omg.CORBA.portable.OutputStream processIntraSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  921:     */     throws Throwable
/*  922:     */   {
/*  923: 828 */     TypeIntraSyncRqV1 localTypeIntraSyncRqV1 = (TypeIntraSyncRqV1)paramInputStream.read_value(TypeIntraSyncRqV1.class);
/*  924: 829 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  925: 830 */     TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = this.target.processIntraSyncRqV1(localTypeIntraSyncRqV1, localTypeUserData);
/*  926: 831 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  927: 832 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  928: 833 */     localOutputStream.write_value(localTypeIntraSyncRsV1, TypeIntraSyncRsV1.class);
/*  929: 834 */     return localOutputStream;
/*  930:     */   }
/*  931:     */   
/*  932:     */   private org.omg.CORBA.portable.OutputStream processIntraTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  933:     */     throws Throwable
/*  934:     */   {
/*  935: 838 */     TypeIntraTrnRqV1 localTypeIntraTrnRqV1 = (TypeIntraTrnRqV1)paramInputStream.read_value(TypeIntraTrnRqV1.class);
/*  936: 839 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  937: 840 */     TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = this.target.processIntraTrnRqV1(localTypeIntraTrnRqV1, localTypeUserData);
/*  938: 841 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  939: 842 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  940: 843 */     localOutputStream.write_value(localTypeIntraTrnRsV1, TypeIntraTrnRsV1.class);
/*  941: 844 */     return localOutputStream;
/*  942:     */   }
/*  943:     */   
/*  944:     */   private org.omg.CORBA.portable.OutputStream processPayeeSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  945:     */     throws Throwable
/*  946:     */   {
/*  947: 848 */     TypePayeeSyncRqV1 localTypePayeeSyncRqV1 = (TypePayeeSyncRqV1)paramInputStream.read_value(TypePayeeSyncRqV1.class);
/*  948: 849 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  949: 850 */     TypePayeeSyncRsV1 localTypePayeeSyncRsV1 = this.target.processPayeeSyncRqV1(localTypePayeeSyncRqV1, localTypeUserData);
/*  950: 851 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  951: 852 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  952: 853 */     localOutputStream.write_value(localTypePayeeSyncRsV1, TypePayeeSyncRsV1.class);
/*  953: 854 */     return localOutputStream;
/*  954:     */   }
/*  955:     */   
/*  956:     */   private org.omg.CORBA.portable.OutputStream processPayeeTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  957:     */     throws Throwable
/*  958:     */   {
/*  959: 858 */     TypePayeeTrnRqV1 localTypePayeeTrnRqV1 = (TypePayeeTrnRqV1)paramInputStream.read_value(TypePayeeTrnRqV1.class);
/*  960: 859 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  961: 860 */     TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = this.target.processPayeeTrnRqV1(localTypePayeeTrnRqV1, localTypeUserData);
/*  962: 861 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  963: 862 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  964: 863 */     localOutputStream.write_value(localTypePayeeTrnRsV1, TypePayeeTrnRsV1.class);
/*  965: 864 */     return localOutputStream;
/*  966:     */   }
/*  967:     */   
/*  968:     */   private org.omg.CORBA.portable.OutputStream processPmtInqTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  969:     */     throws Throwable
/*  970:     */   {
/*  971: 868 */     TypePmtInqTrnRqV1 localTypePmtInqTrnRqV1 = (TypePmtInqTrnRqV1)paramInputStream.read_value(TypePmtInqTrnRqV1.class);
/*  972: 869 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  973: 870 */     TypePmtInqTrnRsV1 localTypePmtInqTrnRsV1 = this.target.processPmtInqTrnRqV1(localTypePmtInqTrnRqV1, localTypeUserData);
/*  974: 871 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  975: 872 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  976: 873 */     localOutputStream.write_value(localTypePmtInqTrnRsV1, TypePmtInqTrnRsV1.class);
/*  977: 874 */     return localOutputStream;
/*  978:     */   }
/*  979:     */   
/*  980:     */   private org.omg.CORBA.portable.OutputStream processPmtSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  981:     */     throws Throwable
/*  982:     */   {
/*  983: 878 */     TypePmtSyncRqV1 localTypePmtSyncRqV1 = (TypePmtSyncRqV1)paramInputStream.read_value(TypePmtSyncRqV1.class);
/*  984: 879 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  985: 880 */     TypePmtSyncRsV1 localTypePmtSyncRsV1 = this.target.processPmtSyncRqV1(localTypePmtSyncRqV1, localTypeUserData);
/*  986: 881 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  987: 882 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  988: 883 */     localOutputStream.write_value(localTypePmtSyncRsV1, TypePmtSyncRsV1.class);
/*  989: 884 */     return localOutputStream;
/*  990:     */   }
/*  991:     */   
/*  992:     */   private org.omg.CORBA.portable.OutputStream processPmtTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  993:     */     throws Throwable
/*  994:     */   {
/*  995: 888 */     TypePmtTrnRqV1 localTypePmtTrnRqV1 = (TypePmtTrnRqV1)paramInputStream.read_value(TypePmtTrnRqV1.class);
/*  996: 889 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  997: 890 */     TypePmtTrnRsV1 localTypePmtTrnRsV1 = this.target.processPmtTrnRqV1(localTypePmtTrnRqV1, localTypeUserData);
/*  998: 891 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  999: 892 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1000: 893 */     localOutputStream.write_value(localTypePmtTrnRsV1, TypePmtTrnRsV1.class);
/* 1001: 894 */     return localOutputStream;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   private org.omg.CORBA.portable.OutputStream processRecIntraSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1005:     */     throws Throwable
/* 1006:     */   {
/* 1007: 898 */     TypeRecIntraSyncRqV1 localTypeRecIntraSyncRqV1 = (TypeRecIntraSyncRqV1)paramInputStream.read_value(TypeRecIntraSyncRqV1.class);
/* 1008: 899 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/* 1009: 900 */     TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1 = this.target.processRecIntraSyncRqV1(localTypeRecIntraSyncRqV1, localTypeUserData);
/* 1010: 901 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1011: 902 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1012: 903 */     localOutputStream.write_value(localTypeRecIntraSyncRsV1, TypeRecIntraSyncRsV1.class);
/* 1013: 904 */     return localOutputStream;
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   private org.omg.CORBA.portable.OutputStream processRecIntraTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1017:     */     throws Throwable
/* 1018:     */   {
/* 1019: 908 */     TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1 = (TypeRecIntraTrnRqV1)paramInputStream.read_value(TypeRecIntraTrnRqV1.class);
/* 1020: 909 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/* 1021: 910 */     TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = this.target.processRecIntraTrnRqV1(localTypeRecIntraTrnRqV1, localTypeUserData);
/* 1022: 911 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1023: 912 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1024: 913 */     localOutputStream.write_value(localTypeRecIntraTrnRsV1, TypeRecIntraTrnRsV1.class);
/* 1025: 914 */     return localOutputStream;
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   private org.omg.CORBA.portable.OutputStream processRecPmtSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1029:     */     throws Throwable
/* 1030:     */   {
/* 1031: 918 */     TypeRecPmtSyncRqV1 localTypeRecPmtSyncRqV1 = (TypeRecPmtSyncRqV1)paramInputStream.read_value(TypeRecPmtSyncRqV1.class);
/* 1032: 919 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/* 1033: 920 */     TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV1 = this.target.processRecPmtSyncRqV1(localTypeRecPmtSyncRqV1, localTypeUserData);
/* 1034: 921 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1035: 922 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1036: 923 */     localOutputStream.write_value(localTypeRecPmtSyncRsV1, TypeRecPmtSyncRsV1.class);
/* 1037: 924 */     return localOutputStream;
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   private org.omg.CORBA.portable.OutputStream processRecPmtTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1041:     */     throws Throwable
/* 1042:     */   {
/* 1043: 928 */     TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1 = (TypeRecPmtTrnRqV1)paramInputStream.read_value(TypeRecPmtTrnRqV1.class);
/* 1044: 929 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/* 1045: 930 */     TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = this.target.processRecPmtTrnRqV1(localTypeRecPmtTrnRqV1, localTypeUserData);
/* 1046: 931 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1047: 932 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1048: 933 */     localOutputStream.write_value(localTypeRecPmtTrnRsV1, TypeRecPmtTrnRsV1.class);
/* 1049: 934 */     return localOutputStream;
/* 1050:     */   }
/* 1051:     */   
/* 1052:     */   private org.omg.CORBA.portable.OutputStream getPayeeNames__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1053:     */     throws Throwable
/* 1054:     */   {
/* 1055: 938 */     String str = (String)paramInputStream.read_value(String.class);
/* 1056: 939 */     String[] arrayOfString = this.target.getPayeeNames(str);
/* 1057: 940 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1058: 941 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1059: 942 */     localOutputStream.write_value(cast_array(arrayOfString), new String[0].getClass());
/* 1060: 943 */     return localOutputStream;
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   private org.omg.CORBA.portable.OutputStream getPayeeIDs(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1064:     */     throws Throwable
/* 1065:     */   {
/* 1066: 947 */     String str = (String)paramInputStream.read_value(String.class);
/* 1067: 948 */     String[] arrayOfString = this.target.getPayeeIDs(str);
/* 1068: 949 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1069: 950 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1070: 951 */     localOutputStream.write_value(cast_array(arrayOfString), new String[0].getClass());
/* 1071: 952 */     return localOutputStream;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   private org.omg.CORBA.portable.OutputStream getPayees__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1075:     */     throws Throwable
/* 1076:     */   {
/* 1077: 956 */     String str = (String)paramInputStream.read_value(String.class);
/* 1078: 957 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getPayees(str);
/* 1079: 958 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1080: 959 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1081: 960 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1082: 961 */     return localOutputStream;
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   private org.omg.CORBA.portable.OutputStream searchGlobalPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1086:     */     throws Throwable
/* 1087:     */   {
/* 1088: 965 */     String str = (String)paramInputStream.read_value(String.class);
/* 1089: 966 */     PayeeInfo[] arrayOfPayeeInfo = this.target.searchGlobalPayees(str);
/* 1090: 967 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1091: 968 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1092: 969 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1093: 970 */     return localOutputStream;
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   private org.omg.CORBA.portable.OutputStream updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1097:     */     throws Throwable
/* 1098:     */   {
/* 1099: 974 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1100: 975 */     PayeeRouteInfo localPayeeRouteInfo = (PayeeRouteInfo)paramInputStream.read_value(PayeeRouteInfo.class);
/* 1101: 976 */     this.target.updatePayee(localPayeeInfo, localPayeeRouteInfo);
/* 1102: 977 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1103: 978 */     return localOutputStream;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   private org.omg.CORBA.portable.OutputStream deletePayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1107:     */     throws Throwable
/* 1108:     */   {
/* 1109: 982 */     String str = (String)paramInputStream.read_value(String.class);
/* 1110: 983 */     this.target.deletePayee(str);
/* 1111: 984 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1112: 985 */     return localOutputStream;
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   private org.omg.CORBA.portable.OutputStream deletePayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1116:     */     throws Throwable
/* 1117:     */   {
/* 1118: 989 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1119: 990 */     this.target.deletePayees(arrayOfString);
/* 1120: 991 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1121: 992 */     return localOutputStream;
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   private org.omg.CORBA.portable.OutputStream findPayeeByID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1125:     */     throws Throwable
/* 1126:     */   {
/* 1127: 996 */     String str = (String)paramInputStream.read_value(String.class);
/* 1128: 997 */     PayeeInfo localPayeeInfo = this.target.findPayeeByID(str);
/* 1129: 998 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1130: 999 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1131:1000 */     localOutputStream.write_value(localPayeeInfo, PayeeInfo.class);
/* 1132:1001 */     return localOutputStream;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   private org.omg.CORBA.portable.OutputStream setPayeeStatus(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1136:     */     throws Throwable
/* 1137:     */   {
/* 1138:1005 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1139:1006 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1140:1007 */     this.target.setPayeeStatus(str1, str2);
/* 1141:1008 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1142:1009 */     return localOutputStream;
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   private org.omg.CORBA.portable.OutputStream getSmartDate(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1146:     */     throws Throwable
/* 1147:     */   {
/* 1148:1013 */     int i = paramInputStream.read_long();
/* 1149:1014 */     int j = this.target.getSmartDate(i);
/* 1150:1015 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1151:1016 */     localOutputStream.write_long(j);
/* 1152:1017 */     return localOutputStream;
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   private org.omg.CORBA.portable.OutputStream getPayees__CORBA_WStringValue__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1156:     */     throws Throwable
/* 1157:     */   {
/* 1158:1021 */     String str = (String)paramInputStream.read_value(String.class);
/* 1159:1022 */     int i = paramInputStream.read_long();
/* 1160:1023 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getPayees(str, i);
/* 1161:1024 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1162:1025 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1163:1026 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1164:1027 */     return localOutputStream;
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   private org.omg.CORBA.portable.OutputStream getGlobalPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1168:     */     throws Throwable
/* 1169:     */   {
/* 1170:1031 */     String str = (String)paramInputStream.read_value(String.class);
/* 1171:1032 */     int i = paramInputStream.read_long();
/* 1172:1033 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getGlobalPayees(str, i);
/* 1173:1034 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1174:1035 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1175:1036 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1176:1037 */     return localOutputStream;
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   private org.omg.CORBA.portable.OutputStream getPayeeNames__CORBA_WStringValue__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1180:     */     throws Throwable
/* 1181:     */   {
/* 1182:1041 */     String str = (String)paramInputStream.read_value(String.class);
/* 1183:1042 */     int i = paramInputStream.read_long();
/* 1184:1043 */     String[] arrayOfString = this.target.getPayeeNames(str, i);
/* 1185:1044 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1186:1045 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1187:1046 */     localOutputStream.write_value(cast_array(arrayOfString), new String[0].getClass());
/* 1188:1047 */     return localOutputStream;
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   private org.omg.CORBA.portable.OutputStream addPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1192:     */     throws Throwable
/* 1193:     */   {
/* 1194:1051 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1195:1052 */     int i = paramInputStream.read_long();
/* 1196:1053 */     String str = this.target.addPayee(localPayeeInfo, i);
/* 1197:1054 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1198:1055 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1199:1056 */     localOutputStream.write_value(str, String.class);
/* 1200:1057 */     return localOutputStream;
/* 1201:     */   }
/* 1202:     */   
/* 1203:     */   private org.omg.CORBA.portable.OutputStream updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1204:     */     throws Throwable
/* 1205:     */   {
/* 1206:1061 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1207:1062 */     int i = paramInputStream.read_long();
/* 1208:1063 */     PayeeInfo[] arrayOfPayeeInfo = this.target.updatePayee(localPayeeInfo, i);
/* 1209:1064 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1210:1065 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1211:1066 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1212:1067 */     return localOutputStream;
/* 1213:     */   }
/* 1214:     */   
/* 1215:     */   private org.omg.CORBA.portable.OutputStream addConsumerCrossRef(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1216:     */     throws Throwable
/* 1217:     */   {
/* 1218:1071 */     ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = (ConsumerCrossRefInfo[])paramInputStream.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 1219:1072 */     int i = this.target.addConsumerCrossRef(arrayOfConsumerCrossRefInfo);
/* 1220:1073 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1221:1074 */     localOutputStream.write_long(i);
/* 1222:1075 */     return localOutputStream;
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   private org.omg.CORBA.portable.OutputStream deleteConsumerCrossRef(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1226:     */     throws Throwable
/* 1227:     */   {
/* 1228:1079 */     ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = (ConsumerCrossRefInfo[])paramInputStream.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 1229:1080 */     int i = this.target.deleteConsumerCrossRef(arrayOfConsumerCrossRefInfo);
/* 1230:1081 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1231:1082 */     localOutputStream.write_long(i);
/* 1232:1083 */     return localOutputStream;
/* 1233:     */   }
/* 1234:     */   
/* 1235:     */   private org.omg.CORBA.portable.OutputStream getConsumerCrossRef(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1236:     */     throws Throwable
/* 1237:     */   {
/* 1238:1087 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1239:1088 */     ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = this.target.getConsumerCrossRef(arrayOfString);
/* 1240:1089 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1241:1090 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1242:1091 */     localOutputStream.write_value(cast_array(arrayOfConsumerCrossRefInfo), new ConsumerCrossRefInfo[0].getClass());
/* 1243:1092 */     return localOutputStream;
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   private org.omg.CORBA.portable.OutputStream addCustomerBankInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1247:     */     throws Throwable
/* 1248:     */   {
/* 1249:1096 */     CustomerBankInfo[] arrayOfCustomerBankInfo = (CustomerBankInfo[])paramInputStream.read_value(new CustomerBankInfo[0].getClass());
/* 1250:1097 */     int i = this.target.addCustomerBankInfo(arrayOfCustomerBankInfo);
/* 1251:1098 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1252:1099 */     localOutputStream.write_long(i);
/* 1253:1100 */     return localOutputStream;
/* 1254:     */   }
/* 1255:     */   
/* 1256:     */   private org.omg.CORBA.portable.OutputStream deleteCustomerBankInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1257:     */     throws Throwable
/* 1258:     */   {
/* 1259:1104 */     CustomerBankInfo[] arrayOfCustomerBankInfo = (CustomerBankInfo[])paramInputStream.read_value(new CustomerBankInfo[0].getClass());
/* 1260:1105 */     int i = this.target.deleteCustomerBankInfo(arrayOfCustomerBankInfo);
/* 1261:1106 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1262:1107 */     localOutputStream.write_long(i);
/* 1263:1108 */     return localOutputStream;
/* 1264:     */   }
/* 1265:     */   
/* 1266:     */   private org.omg.CORBA.portable.OutputStream getCustomerBankInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1267:     */     throws Throwable
/* 1268:     */   {
/* 1269:1112 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1270:1113 */     CustomerBankInfo[] arrayOfCustomerBankInfo = this.target.getCustomerBankInfo(arrayOfString);
/* 1271:1114 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1272:1115 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1273:1116 */     localOutputStream.write_value(cast_array(arrayOfCustomerBankInfo), new CustomerBankInfo[0].getClass());
/* 1274:1117 */     return localOutputStream;
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   private org.omg.CORBA.portable.OutputStream addCustomerProductAccessInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1278:     */     throws Throwable
/* 1279:     */   {
/* 1280:1121 */     CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo = (CustomerProductAccessInfo[])paramInputStream.read_value(new CustomerProductAccessInfo[0].getClass());
/* 1281:1122 */     int i = this.target.addCustomerProductAccessInfo(arrayOfCustomerProductAccessInfo);
/* 1282:1123 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1283:1124 */     localOutputStream.write_long(i);
/* 1284:1125 */     return localOutputStream;
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   private org.omg.CORBA.portable.OutputStream deleteCustomerProductAccessInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1288:     */     throws Throwable
/* 1289:     */   {
/* 1290:1129 */     CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo = (CustomerProductAccessInfo[])paramInputStream.read_value(new CustomerProductAccessInfo[0].getClass());
/* 1291:1130 */     int i = this.target.deleteCustomerProductAccessInfo(arrayOfCustomerProductAccessInfo);
/* 1292:1131 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1293:1132 */     localOutputStream.write_long(i);
/* 1294:1133 */     return localOutputStream;
/* 1295:     */   }
/* 1296:     */   
/* 1297:     */   private org.omg.CORBA.portable.OutputStream getCustomerProductAccessInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1298:     */     throws Throwable
/* 1299:     */   {
/* 1300:1137 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1301:1138 */     CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo = this.target.getCustomerProductAccessInfo(arrayOfString);
/* 1302:1139 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1303:1140 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1304:1141 */     localOutputStream.write_value(cast_array(arrayOfCustomerProductAccessInfo), new CustomerProductAccessInfo[0].getClass());
/* 1305:1142 */     return localOutputStream;
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   private org.omg.CORBA.portable.OutputStream validateMetavanteCustAcctByConsumerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1309:     */     throws Throwable
/* 1310:     */   {
/* 1311:1146 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1312:1147 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1313:1148 */     boolean bool = this.target.validateMetavanteCustAcctByConsumerID(str1, str2);
/* 1314:1149 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1315:1150 */     localOutputStream.write_boolean(bool);
/* 1316:1151 */     return localOutputStream;
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   private org.omg.CORBA.portable.OutputStream validateMetavanteCustAcctByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1320:     */     throws Throwable
/* 1321:     */   {
/* 1322:1155 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1323:1156 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1324:1157 */     boolean bool = this.target.validateMetavanteCustAcctByCustomerID(str1, str2);
/* 1325:1158 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1326:1159 */     localOutputStream.write_boolean(bool);
/* 1327:1160 */     return localOutputStream;
/* 1328:     */   }
/* 1329:     */   
/* 1330:     */   private org.omg.CORBA.portable.OutputStream getPmtHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1331:     */     throws Throwable
/* 1332:     */   {
/* 1333:1164 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1334:1165 */     BPWHist localBPWHist2 = this.target.getPmtHistory(localBPWHist1);
/* 1335:1166 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1336:1167 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1337:1168 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1338:1169 */     return localOutputStream;
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   private org.omg.CORBA.portable.OutputStream getIntraHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1342:     */     throws Throwable
/* 1343:     */   {
/* 1344:1173 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1345:1174 */     BPWHist localBPWHist2 = this.target.getIntraHistory(localBPWHist1);
/* 1346:1175 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1347:1176 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1348:1177 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1349:1178 */     return localOutputStream;
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   private org.omg.CORBA.portable.OutputStream getIntraById__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1353:     */     throws Throwable
/* 1354:     */   {
/* 1355:1182 */     String str = (String)paramInputStream.read_value(String.class);
/* 1356:1183 */     IntraTrnInfo localIntraTrnInfo = this.target.getIntraById(str);
/* 1357:1184 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1358:1185 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1359:1186 */     localOutputStream.write_value(localIntraTrnInfo, IntraTrnInfo.class);
/* 1360:1187 */     return localOutputStream;
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   private org.omg.CORBA.portable.OutputStream getIntraByRecSrvrTId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1364:     */     throws Throwable
/* 1365:     */   {
/* 1366:1191 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1367:1192 */     boolean bool = paramInputStream.read_boolean();
/* 1368:1193 */     IntraTrnInfo[] arrayOfIntraTrnInfo = this.target.getIntraByRecSrvrTId(arrayOfString, bool);
/* 1369:1194 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1370:1195 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1371:1196 */     localOutputStream.write_value(cast_array(arrayOfIntraTrnInfo), new IntraTrnInfo[0].getClass());
/* 1372:1197 */     return localOutputStream;
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   private org.omg.CORBA.portable.OutputStream getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1376:     */     throws Throwable
/* 1377:     */   {
/* 1378:1201 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1379:1202 */     IntraTrnInfo[] arrayOfIntraTrnInfo = this.target.getIntraById(arrayOfString);
/* 1380:1203 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1381:1204 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1382:1205 */     localOutputStream.write_value(cast_array(arrayOfIntraTrnInfo), new IntraTrnInfo[0].getClass());
/* 1383:1206 */     return localOutputStream;
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   private org.omg.CORBA.portable.OutputStream getPmtById__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1387:     */     throws Throwable
/* 1388:     */   {
/* 1389:1210 */     String str = (String)paramInputStream.read_value(String.class);
/* 1390:1211 */     PmtInfo localPmtInfo = this.target.getPmtById(str);
/* 1391:1212 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1392:1213 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1393:1214 */     localOutputStream.write_value(localPmtInfo, PmtInfo.class);
/* 1394:1215 */     return localOutputStream;
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   private org.omg.CORBA.portable.OutputStream getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1398:     */     throws Throwable
/* 1399:     */   {
/* 1400:1219 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1401:1220 */     PmtInfo[] arrayOfPmtInfo = this.target.getPmtById(arrayOfString);
/* 1402:1221 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1403:1222 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1404:1223 */     localOutputStream.write_value(cast_array(arrayOfPmtInfo), new PmtInfo[0].getClass());
/* 1405:1224 */     return localOutputStream;
/* 1406:     */   }
/* 1407:     */   
/* 1408:     */   private org.omg.CORBA.portable.OutputStream getRecPmtById(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1409:     */     throws Throwable
/* 1410:     */   {
/* 1411:1228 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1412:1229 */     RecPmtInfo[] arrayOfRecPmtInfo = this.target.getRecPmtById(arrayOfString);
/* 1413:1230 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1414:1231 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1415:1232 */     localOutputStream.write_value(cast_array(arrayOfRecPmtInfo), new RecPmtInfo[0].getClass());
/* 1416:1233 */     return localOutputStream;
/* 1417:     */   }
/* 1418:     */   
/* 1419:     */   private org.omg.CORBA.portable.OutputStream getRecIntraById__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1420:     */     throws Throwable
/* 1421:     */   {
/* 1422:1237 */     String str = (String)paramInputStream.read_value(String.class);
/* 1423:1238 */     RecIntraTrnInfo localRecIntraTrnInfo = this.target.getRecIntraById(str);
/* 1424:1239 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1425:1240 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1426:1241 */     localOutputStream.write_value(localRecIntraTrnInfo, RecIntraTrnInfo.class);
/* 1427:1242 */     return localOutputStream;
/* 1428:     */   }
/* 1429:     */   
/* 1430:     */   private org.omg.CORBA.portable.OutputStream getRecIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1431:     */     throws Throwable
/* 1432:     */   {
/* 1433:1246 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1434:1247 */     RecIntraTrnInfo[] arrayOfRecIntraTrnInfo = this.target.getRecIntraById(arrayOfString);
/* 1435:1248 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1436:1249 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1437:1250 */     localOutputStream.write_value(cast_array(arrayOfRecIntraTrnInfo), new RecIntraTrnInfo[0].getClass());
/* 1438:1251 */     return localOutputStream;
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   private org.omg.CORBA.portable.OutputStream getPayeeByListId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1442:     */     throws Throwable
/* 1443:     */   {
/* 1444:1255 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1445:1256 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1446:     */     PayeeInfo localPayeeInfo;
/* 1447:     */     try
/* 1448:     */     {
/* 1449:1259 */       localPayeeInfo = this.target.getPayeeByListId(str1, str2);
/* 1450:     */     }
/* 1451:     */     catch (FFSException localFFSException)
/* 1452:     */     {
/* 1453:1261 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1454:1262 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1455:1263 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1456:1264 */       localOutputStream2.write_string(str3);
/* 1457:1265 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1458:1266 */       return localOutputStream2;
/* 1459:     */     }
/* 1460:1268 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1461:1269 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1462:1270 */     localOutputStream1.write_value(localPayeeInfo, PayeeInfo.class);
/* 1463:1271 */     return localOutputStream1;
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   private org.omg.CORBA.portable.OutputStream getAccountTypesMap(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1467:     */     throws Throwable
/* 1468:     */   {
/* 1469:     */     AccountTypesMap localAccountTypesMap;
/* 1470:     */     try
/* 1471:     */     {
/* 1472:1277 */       localAccountTypesMap = this.target.getAccountTypesMap();
/* 1473:     */     }
/* 1474:     */     catch (FFSException localFFSException)
/* 1475:     */     {
/* 1476:1279 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1477:1280 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1478:1281 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1479:1282 */       localOutputStream2.write_string(str);
/* 1480:1283 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1481:1284 */       return localOutputStream2;
/* 1482:     */     }
/* 1483:1286 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1484:1287 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1485:1288 */     localOutputStream1.write_value(localAccountTypesMap, AccountTypesMap.class);
/* 1486:1289 */     return localOutputStream1;
/* 1487:     */   }
/* 1488:     */   
/* 1489:     */   private Serializable cast_array(java.lang.Object paramObject)
/* 1490:     */   {
/* 1491:1296 */     return (Serializable)paramObject;
/* 1492:     */   }
/* 1493:     */ }


/* Location:           D:\drops\jd\jars\Deployed_OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices._EJSRemoteStatelessIOFX200BPWServices_Tie
 * JD-Core Version:    0.7.0.1
 */