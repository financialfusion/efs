/*    1:     */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;
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
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
/*   18:     */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*   19:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   20:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   21:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
/*   22:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
/*   23:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
/*   24:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
/*   25:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
/*   26:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
/*   27:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
/*   28:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
/*   29:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1;
/*   30:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1;
/*   31:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
/*   32:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
/*   33:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
/*   34:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
/*   35:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
/*   36:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
/*   37:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
/*   38:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
/*   39:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
/*   43:     */ import com.ibm.ejs.container.EJSWrapper;
/*   44:     */ import java.io.Serializable;
/*   45:     */ import java.rmi.Remote;
/*   46:     */ import javax.ejb.EJBHome;
/*   47:     */ import javax.ejb.EJBObject;
/*   48:     */ import javax.ejb.Handle;
/*   49:     */ import javax.ejb.RemoveException;
/*   50:     */ import javax.rmi.CORBA.Tie;
/*   51:     */ import javax.rmi.CORBA.Util;
/*   52:     */ import org.omg.CORBA.BAD_OPERATION;
/*   53:     */ import org.omg.CORBA.ORB;
/*   54:     */ import org.omg.CORBA.SystemException;
/*   55:     */ import org.omg.CORBA.portable.Delegate;
/*   56:     */ import org.omg.CORBA.portable.ResponseHandler;
/*   57:     */ import org.omg.CORBA.portable.UnknownException;
/*   58:     */ 
/*   59:     */ public class _EJSRemoteStatelessIOFX151BPWServices_79a24b5a_Tie
/*   60:     */   extends org.omg.CORBA_2_3.portable.ObjectImpl
/*   61:     */   implements Tie
/*   62:     */ {
/*   63:  68 */   private EJSRemoteStatelessIOFX151BPWServices_79a24b5a target = null;
/*   64:  69 */   private ORB orb = null;
/*   65:  71 */   private static final String[] _type_ids = {
/*   66:  72 */     "RMI:com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.IOFX151BPWServices:0000000000000000", 
/*   67:  73 */     "RMI:javax.ejb.EJBObject:0000000000000000", 
/*   68:  74 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
/*   69:  75 */     "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000" };
/*   70:     */   
/*   71:     */   public void setTarget(Remote paramRemote)
/*   72:     */   {
/*   73:  79 */     this.target = ((EJSRemoteStatelessIOFX151BPWServices_79a24b5a)paramRemote);
/*   74:     */   }
/*   75:     */   
/*   76:     */   public Remote getTarget()
/*   77:     */   {
/*   78:  83 */     return this.target;
/*   79:     */   }
/*   80:     */   
/*   81:     */   public org.omg.CORBA.Object thisObject()
/*   82:     */   {
/*   83:  87 */     return this;
/*   84:     */   }
/*   85:     */   
/*   86:     */   public void deactivate()
/*   87:     */   {
/*   88:  91 */     if (this.orb != null)
/*   89:     */     {
/*   90:  92 */       this.orb.disconnect(this);
/*   91:  93 */       _set_delegate(null);
/*   92:     */     }
/*   93:     */   }
/*   94:     */   
/*   95:     */   public ORB orb()
/*   96:     */   {
/*   97:  98 */     return _orb();
/*   98:     */   }
/*   99:     */   
/*  100:     */   public void orb(ORB paramORB)
/*  101:     */   {
/*  102: 102 */     paramORB.connect(this);
/*  103:     */   }
/*  104:     */   
/*  105:     */   public void _set_delegate(Delegate paramDelegate)
/*  106:     */   {
/*  107: 106 */     super._set_delegate(paramDelegate);
/*  108: 107 */     if (paramDelegate != null) {
/*  109: 108 */       this.orb = _orb();
/*  110:     */     } else {
/*  111: 110 */       this.orb = null;
/*  112:     */     }
/*  113:     */   }
/*  114:     */   
/*  115:     */   public String[] _ids()
/*  116:     */   {
/*  117: 114 */     return _type_ids;
/*  118:     */   }
/*  119:     */   
/*  120:     */   public org.omg.CORBA.portable.OutputStream _invoke(String paramString, org.omg.CORBA.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  121:     */     throws SystemException
/*  122:     */   {
/*  123:     */     try
/*  124:     */     {
/*  125: 119 */       org.omg.CORBA_2_3.portable.InputStream localInputStream = 
/*  126: 120 */         (org.omg.CORBA_2_3.portable.InputStream)paramInputStream;
/*  127: 121 */       switch (paramString.hashCode())
/*  128:     */       {
/*  129:     */       case -1851674302: 
/*  130: 123 */         if (paramString.equals("getPayeeByListId")) {
/*  131: 124 */           return getPayeeByListId(localInputStream, paramResponseHandler);
/*  132:     */         }
/*  133:     */       case -1791303223: 
/*  134: 127 */         if (paramString.equals("addCustomerBankInfo")) {
/*  135: 128 */           return addCustomerBankInfo(localInputStream, paramResponseHandler);
/*  136:     */         }
/*  137:     */       case -1776939455: 
/*  138: 131 */         if (paramString.equals("deactivateCustomers")) {
/*  139: 132 */           return deactivateCustomers(localInputStream, paramResponseHandler);
/*  140:     */         }
/*  141:     */       case -1768635698: 
/*  142: 135 */         if (paramString.equals("getCustomerByFI")) {
/*  143: 136 */           return getCustomerByFI(localInputStream, paramResponseHandler);
/*  144:     */         }
/*  145:     */       case -1681582906: 
/*  146: 139 */         if (paramString.equals("getCustomerByGroupAndFI")) {
/*  147: 140 */           return getCustomerByGroupAndFI(localInputStream, paramResponseHandler);
/*  148:     */         }
/*  149:     */       case -1677309325: 
/*  150: 143 */         if (paramString.equals("getPmtHistory")) {
/*  151: 144 */           return getPmtHistory(localInputStream, paramResponseHandler);
/*  152:     */         }
/*  153:     */       case -1604658875: 
/*  154: 147 */         if (paramString.equals("getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  155: 148 */           return getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  156:     */         }
/*  157:     */       case -1568105773: 
/*  158: 151 */         if (paramString.equals("deleteCustomerBankInfo")) {
/*  159: 152 */           return deleteCustomerBankInfo(localInputStream, paramResponseHandler);
/*  160:     */         }
/*  161:     */       case -1550521068: 
/*  162: 155 */         if (paramString.equals("_get_EJBHome")) {
/*  163: 156 */           return _get_EJBHome(localInputStream, paramResponseHandler);
/*  164:     */         }
/*  165:     */       case -1530678733: 
/*  166: 159 */         if (paramString.equals("getPmtStatus")) {
/*  167: 160 */           return getPmtStatus(localInputStream, paramResponseHandler);
/*  168:     */         }
/*  169:     */       case -1487596648: 
/*  170: 163 */         if (paramString.equals("setPayeeStatus")) {
/*  171: 164 */           return setPayeeStatus(localInputStream, paramResponseHandler);
/*  172:     */         }
/*  173:     */       case -1477108851: 
/*  174: 167 */         if (paramString.equals("getCustomerProductAccessInfo")) {
/*  175: 168 */           return getCustomerProductAccessInfo(localInputStream, paramResponseHandler);
/*  176:     */         }
/*  177:     */       case -1454154143: 
/*  178: 171 */         if (paramString.equals("getSmartDate")) {
/*  179: 172 */           return getSmartDate(localInputStream, paramResponseHandler);
/*  180:     */         }
/*  181:     */       case -1434553393: 
/*  182: 175 */         if (paramString.equals("getRecPmtById")) {
/*  183: 176 */           return getRecPmtById(localInputStream, paramResponseHandler);
/*  184:     */         }
/*  185:     */       case -1406620510: 
/*  186: 179 */         if (paramString.equals("addCustomerProductAccessInfo")) {
/*  187: 180 */           return addCustomerProductAccessInfo(localInputStream, paramResponseHandler);
/*  188:     */         }
/*  189:     */       case -1348384111: 
/*  190: 183 */         if (paramString.equals("processPayeeTrnRqV1")) {
/*  191: 184 */           return processPayeeTrnRqV1(localInputStream, paramResponseHandler);
/*  192:     */         }
/*  193:     */       case -1309038430: 
/*  194: 187 */         if (paramString.equals("activateCustomers")) {
/*  195: 188 */           return activateCustomers(localInputStream, paramResponseHandler);
/*  196:     */         }
/*  197:     */       case -1275891110: 
/*  198: 191 */         if (paramString.equals("updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo")) {
/*  199: 192 */           return updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo(localInputStream, paramResponseHandler);
/*  200:     */         }
/*  201:     */       case -1260463146: 
/*  202: 195 */         if (paramString.equals("getPreferredPayees")) {
/*  203: 196 */           return getPreferredPayees(localInputStream, paramResponseHandler);
/*  204:     */         }
/*  205:     */       case -1250228441: 
/*  206: 199 */         if (paramString.equals("addPayee")) {
/*  207: 200 */           return addPayee(localInputStream, paramResponseHandler);
/*  208:     */         }
/*  209:     */       case -1227350666: 
/*  210: 203 */         if (paramString.equals("searchGlobalPayees")) {
/*  211: 204 */           return searchGlobalPayees(localInputStream, paramResponseHandler);
/*  212:     */         }
/*  213:     */       case -1136226211: 
/*  214: 207 */         if (paramString.equals("deletePayee")) {
/*  215: 208 */           return deletePayee(localInputStream, paramResponseHandler);
/*  216:     */         }
/*  217:     */       case -1011244123: 
/*  218: 211 */         if (paramString.equals("_get_primaryKey")) {
/*  219: 212 */           return _get_primaryKey(localInputStream, paramResponseHandler);
/*  220:     */         }
/*  221:     */       case -992331665: 
/*  222: 215 */         if (paramString.equals("validateMetavanteCustAcctByCustomerID")) {
/*  223: 216 */           return validateMetavanteCustAcctByCustomerID(localInputStream, paramResponseHandler);
/*  224:     */         }
/*  225:     */       case -989279594: 
/*  226: 219 */         if (paramString.equals("getPendingIntras")) {
/*  227: 220 */           return getPendingIntras(localInputStream, paramResponseHandler);
/*  228:     */         }
/*  229:     */       case -934610812: 
/*  230: 223 */         if (paramString.equals("remove")) {
/*  231: 224 */           return remove(localInputStream, paramResponseHandler);
/*  232:     */         }
/*  233:     */       case -863274058: 
/*  234: 227 */         if (paramString.equals("deletePayees")) {
/*  235: 228 */           return deletePayees(localInputStream, paramResponseHandler);
/*  236:     */         }
/*  237:     */       case -841809946: 
/*  238: 231 */         if (paramString.equals("getPayeeIDs")) {
/*  239: 232 */           return getPayeeIDs(localInputStream, paramResponseHandler);
/*  240:     */         }
/*  241:     */       case -795132762: 
/*  242: 235 */         if (paramString.equals("processPmtInqTrnRqV1")) {
/*  243: 236 */           return processPmtInqTrnRqV1(localInputStream, paramResponseHandler);
/*  244:     */         }
/*  245:     */       case -793413663: 
/*  246: 239 */         if (paramString.equals("updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long")) {
/*  247: 240 */           return updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long(localInputStream, paramResponseHandler);
/*  248:     */         }
/*  249:     */       case -606368534: 
/*  250: 243 */         if (paramString.equals("processFundAllocRslt")) {
/*  251: 244 */           return processFundAllocRslt(localInputStream, paramResponseHandler);
/*  252:     */         }
/*  253:     */       case -565874860: 
/*  254: 247 */         if (paramString.equals("processRecPmtTrnRqV1")) {
/*  255: 248 */           return processRecPmtTrnRqV1(localInputStream, paramResponseHandler);
/*  256:     */         }
/*  257:     */       case -554059329: 
/*  258: 251 */         if (paramString.equals("getConsumerCrossRef")) {
/*  259: 252 */           return getConsumerCrossRef(localInputStream, paramResponseHandler);
/*  260:     */         }
/*  261:     */       case -484868392: 
/*  262: 255 */         if (paramString.equals("deleteCustomerProductAccessInfo")) {
/*  263: 256 */           return deleteCustomerProductAccessInfo(localInputStream, paramResponseHandler);
/*  264:     */         }
/*  265:     */       case -473045887: 
/*  266: 259 */         if (paramString.equals("processFundRevertRslt")) {
/*  267: 260 */           return processFundRevertRslt(localInputStream, paramResponseHandler);
/*  268:     */         }
/*  269:     */       case -397719766: 
/*  270: 263 */         if (paramString.equals("getPendingPmtsAndHistoryByCustomerID")) {
/*  271: 264 */           return getPendingPmtsAndHistoryByCustomerID(localInputStream, paramResponseHandler);
/*  272:     */         }
/*  273:     */       case -357885638: 
/*  274: 267 */         if (paramString.equals("getAccountTypesMap")) {
/*  275: 268 */           return getAccountTypesMap(localInputStream, paramResponseHandler);
/*  276:     */         }
/*  277:     */       case -345839895: 
/*  278: 271 */         if (paramString.equals("getCustomerByCategory")) {
/*  279: 272 */           return getCustomerByCategory(localInputStream, paramResponseHandler);
/*  280:     */         }
/*  281:     */       case -322756286: 
/*  282: 275 */         if (paramString.equals("processPayeeRslt")) {
/*  283: 276 */           return processPayeeRslt(localInputStream, paramResponseHandler);
/*  284:     */         }
/*  285:     */       case -307326108: 
/*  286: 279 */         if (paramString.equals("getPayees__CORBA_WStringValue")) {
/*  287: 280 */           return getPayees__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  288:     */         }
/*  289:     */       case -304252047: 
/*  290: 283 */         if (paramString.equals("getCustomerByCategoryAndFI")) {
/*  291: 284 */           return getCustomerByCategoryAndFI(localInputStream, paramResponseHandler);
/*  292:     */         }
/*  293:     */       case -172699861: 
/*  294: 287 */         if (paramString.equals("processRecPmtSyncRqV1")) {
/*  295: 288 */           return processRecPmtSyncRqV1(localInputStream, paramResponseHandler);
/*  296:     */         }
/*  297:     */       case -122856768: 
/*  298: 291 */         if (paramString.equals("getPayees__CORBA_WStringValue__long")) {
/*  299: 292 */           return getPayees__CORBA_WStringValue__long(localInputStream, paramResponseHandler);
/*  300:     */         }
/*  301:     */       case -108337693: 
/*  302: 295 */         if (paramString.equals("addPayeeFromBackend")) {
/*  303: 296 */           return addPayeeFromBackend(localInputStream, paramResponseHandler);
/*  304:     */         }
/*  305:     */       case -74077855: 
/*  306: 299 */         if (paramString.equals("findPayeeByID")) {
/*  307: 300 */           return findPayeeByID(localInputStream, paramResponseHandler);
/*  308:     */         }
/*  309:     */       case -6786256: 
/*  310: 303 */         if (paramString.equals("addPayeeRouteInfo")) {
/*  311: 304 */           return addPayeeRouteInfo(localInputStream, paramResponseHandler);
/*  312:     */         }
/*  313:     */       case 103701334: 
/*  314: 307 */         if (paramString.equals("checkPayeeEditMask")) {
/*  315: 308 */           return checkPayeeEditMask(localInputStream, paramResponseHandler);
/*  316:     */         }
/*  317:     */       case 211047947: 
/*  318: 311 */         if (paramString.equals("updatePayeeFromBackend")) {
/*  319: 312 */           return updatePayeeFromBackend(localInputStream, paramResponseHandler);
/*  320:     */         }
/*  321:     */       case 292003558: 
/*  322: 315 */         if (paramString.equals("getPendingIntrasByCustomerID")) {
/*  323: 316 */           return getPendingIntrasByCustomerID(localInputStream, paramResponseHandler);
/*  324:     */         }
/*  325:     */       case 315560173: 
/*  326: 319 */         if (paramString.equals("processRecIntraTrnRqV1")) {
/*  327: 320 */           return processRecIntraTrnRqV1(localInputStream, paramResponseHandler);
/*  328:     */         }
/*  329:     */       case 324049180: 
/*  330: 323 */         if (paramString.equals("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  331: 324 */           return deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  332:     */         }
/*  333:     */       case 359880623: 
/*  334: 327 */         if (paramString.equals("processCustPayeeRslt")) {
/*  335: 328 */           return processCustPayeeRslt(localInputStream, paramResponseHandler);
/*  336:     */         }
/*  337:     */       case 422614663: 
/*  338: 331 */         if (paramString.equals("validateMetavanteCustAcctByConsumerID")) {
/*  339: 332 */           return validateMetavanteCustAcctByConsumerID(localInputStream, paramResponseHandler);
/*  340:     */         }
/*  341:     */       case 472920637: 
/*  342: 335 */         if (paramString.equals("getPendingPmts")) {
/*  343: 336 */           return getPendingPmts(localInputStream, paramResponseHandler);
/*  344:     */         }
/*  345:     */       case 492840643: 
/*  346: 339 */         if (paramString.equals("getPendingIntrasAndHistoryByCustomerID")) {
/*  347: 340 */           return getPendingIntrasAndHistoryByCustomerID(localInputStream, paramResponseHandler);
/*  348:     */         }
/*  349:     */       case 530405532: 
/*  350: 343 */         if (paramString.equals("disconnect")) {
/*  351: 344 */           return disconnect(localInputStream, paramResponseHandler);
/*  352:     */         }
/*  353:     */       case 637031611: 
/*  354: 347 */         if (paramString.equals("modifyCustomers")) {
/*  355: 348 */           return modifyCustomers(localInputStream, paramResponseHandler);
/*  356:     */         }
/*  357:     */       case 646481814: 
/*  358: 351 */         if (paramString.equals("getPmtById__CORBA_WStringValue")) {
/*  359: 352 */           return getPmtById__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  360:     */         }
/*  361:     */       case 735120546: 
/*  362: 355 */         if (paramString.equals("processPmtTrnRqV1")) {
/*  363: 356 */           return processPmtTrnRqV1(localInputStream, paramResponseHandler);
/*  364:     */         }
/*  365:     */       case 735123217: 
/*  366: 359 */         if (paramString.equals("processPmtTrnRslt")) {
/*  367: 360 */           return processPmtTrnRslt(localInputStream, paramResponseHandler);
/*  368:     */         }
/*  369:     */       case 736662203: 
/*  370: 363 */         if (paramString.equals("processIntraTrnRqV1")) {
/*  371: 364 */           return processIntraTrnRqV1(localInputStream, paramResponseHandler);
/*  372:     */         }
/*  373:     */       case 736664874: 
/*  374: 367 */         if (paramString.equals("processIntraTrnRslt")) {
/*  375: 368 */           return processIntraTrnRslt(localInputStream, paramResponseHandler);
/*  376:     */         }
/*  377:     */       case 832529100: 
/*  378: 371 */         if (paramString.equals("getIntraHistory")) {
/*  379: 372 */           return getIntraHistory(localInputStream, paramResponseHandler);
/*  380:     */         }
/*  381:     */       case 863515172: 
/*  382: 375 */         if (paramString.equals("getGlobalPayees")) {
/*  383: 376 */           return getGlobalPayees(localInputStream, paramResponseHandler);
/*  384:     */         }
/*  385:     */       case 891667469: 
/*  386: 379 */         if (paramString.equals("getPendingPmtsByCustomerID")) {
/*  387: 380 */           return getPendingPmtsByCustomerID(localInputStream, paramResponseHandler);
/*  388:     */         }
/*  389:     */       case 912846462: 
/*  390: 383 */         if (paramString.equals("getCustomerBankInfo")) {
/*  391: 384 */           return getCustomerBankInfo(localInputStream, paramResponseHandler);
/*  392:     */         }
/*  393:     */       case 1036758282: 
/*  394: 387 */         if (paramString.equals("addConsumerCrossRef")) {
/*  395: 388 */           return addConsumerCrossRef(localInputStream, paramResponseHandler);
/*  396:     */         }
/*  397:     */       case 1050730221: 
/*  398: 391 */         if (paramString.equals("getCustomersInfo")) {
/*  399: 392 */           return getCustomersInfo(localInputStream, paramResponseHandler);
/*  400:     */         }
/*  401:     */       case 1089499445: 
/*  402: 395 */         if (paramString.equals("getCustomerByTypeAndFI")) {
/*  403: 396 */           return getCustomerByTypeAndFI(localInputStream, paramResponseHandler);
/*  404:     */         }
/*  405:     */       case 1148610213: 
/*  406: 399 */         if (paramString.equals("getCustomerByType")) {
/*  407: 400 */           return getCustomerByType(localInputStream, paramResponseHandler);
/*  408:     */         }
/*  409:     */       case 1234963572: 
/*  410: 403 */         if (paramString.equals("getCustomerByGroup")) {
/*  411: 404 */           return getCustomerByGroup(localInputStream, paramResponseHandler);
/*  412:     */         }
/*  413:     */       case 1259955732: 
/*  414: 407 */         if (paramString.equals("deleteConsumerCrossRef")) {
/*  415: 408 */           return deleteConsumerCrossRef(localInputStream, paramResponseHandler);
/*  416:     */         }
/*  417:     */       case 1264756395: 
/*  418: 411 */         if (paramString.equals("isIdentical")) {
/*  419: 412 */           return isIdentical(localInputStream, paramResponseHandler);
/*  420:     */         }
/*  421:     */       case 1339317134: 
/*  422: 415 */         if (paramString.equals("processPayeeSyncRqV1")) {
/*  423: 416 */           return processPayeeSyncRqV1(localInputStream, paramResponseHandler);
/*  424:     */         }
/*  425:     */       case 1345737269: 
/*  426: 419 */         if (paramString.equals("getPayeeNames__CORBA_WStringValue__long")) {
/*  427: 420 */           return getPayeeNames__CORBA_WStringValue__long(localInputStream, paramResponseHandler);
/*  428:     */         }
/*  429:     */       case 1381982386: 
/*  430: 423 */         if (paramString.equals("processRecIntraSyncRqV1")) {
/*  431: 424 */           return processRecIntraSyncRqV1(localInputStream, paramResponseHandler);
/*  432:     */         }
/*  433:     */       case 1473661108: 
/*  434: 427 */         if (paramString.equals("addCustomers")) {
/*  435: 428 */           return addCustomers(localInputStream, paramResponseHandler);
/*  436:     */         }
/*  437:     */       case 1503452061: 
/*  438: 431 */         if (paramString.equals("processPmtSyncRqV1")) {
/*  439: 432 */           return processPmtSyncRqV1(localInputStream, paramResponseHandler);
/*  440:     */         }
/*  441:     */       case 1509559389: 
/*  442: 435 */         if (paramString.equals("getIntraById__CORBA_WStringValue")) {
/*  443: 436 */           return getIntraById__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  444:     */         }
/*  445:     */       case 1547577633: 
/*  446: 439 */         if (paramString.equals("getMostUsedPayees")) {
/*  447: 440 */           return getMostUsedPayees(localInputStream, paramResponseHandler);
/*  448:     */         }
/*  449:     */       case 1551243428: 
/*  450: 443 */         if (paramString.equals("processIntraSyncRqV1")) {
/*  451: 444 */           return processIntraSyncRqV1(localInputStream, paramResponseHandler);
/*  452:     */         }
/*  453:     */       case 1634875000: 
/*  454: 447 */         if (paramString.equals("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long")) {
/*  455: 448 */           return deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long(localInputStream, paramResponseHandler);
/*  456:     */         }
/*  457:     */       case 1944413392: 
/*  458: 451 */         if (paramString.equals("_get_handle")) {
/*  459: 452 */           return _get_handle(localInputStream, paramResponseHandler);
/*  460:     */         }
/*  461:     */       case 1948936780: 
/*  462: 455 */         if (paramString.equals("_get_linkedPayees")) {
/*  463: 456 */           return _get_linkedPayees(localInputStream, paramResponseHandler);
/*  464:     */         }
/*  465:     */       case 2023718604: 
/*  466: 459 */         if (paramString.equals("getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  467: 460 */           return getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  468:     */         }
/*  469:     */       case 2135918233: 
/*  470: 463 */         if (paramString.equals("getPayeeNames__CORBA_WStringValue")) {
/*  471: 464 */           return getPayeeNames__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  472:     */         }
/*  473:     */         break;
/*  474:     */       }
/*  475: 467 */       throw new BAD_OPERATION();
/*  476:     */     }
/*  477:     */     catch (SystemException localSystemException)
/*  478:     */     {
/*  479: 469 */       throw localSystemException;
/*  480:     */     }
/*  481:     */     catch (Throwable localThrowable)
/*  482:     */     {
/*  483: 471 */       throw new UnknownException(localThrowable);
/*  484:     */     }
/*  485:     */   }
/*  486:     */   
/*  487:     */   private org.omg.CORBA.portable.OutputStream _get_EJBHome(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  488:     */     throws Throwable
/*  489:     */   {
/*  490: 476 */     EJBHome localEJBHome = this.target.getEJBHome();
/*  491: 477 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  492: 478 */     Util.writeRemoteObject(localOutputStream, localEJBHome);
/*  493: 479 */     return localOutputStream;
/*  494:     */   }
/*  495:     */   
/*  496:     */   private org.omg.CORBA.portable.OutputStream _get_primaryKey(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  497:     */     throws Throwable
/*  498:     */   {
/*  499: 483 */     java.lang.Object localObject = this.target.getPrimaryKey();
/*  500: 484 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  501: 485 */     Util.writeAny(localOutputStream, localObject);
/*  502: 486 */     return localOutputStream;
/*  503:     */   }
/*  504:     */   
/*  505:     */   private org.omg.CORBA.portable.OutputStream remove(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  506:     */     throws Throwable
/*  507:     */   {
/*  508:     */     try
/*  509:     */     {
/*  510: 491 */       this.target.remove();
/*  511:     */     }
/*  512:     */     catch (RemoveException localRemoveException)
/*  513:     */     {
/*  514: 493 */       String str = "IDL:javax/ejb/RemoveEx:1.0";
/*  515: 494 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  516: 495 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  517: 496 */       localOutputStream1.write_string(str);
/*  518: 497 */       localOutputStream1.write_value(localRemoveException, RemoveException.class);
/*  519: 498 */       return localOutputStream1;
/*  520:     */     }
/*  521: 500 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  522: 501 */     return localOutputStream;
/*  523:     */   }
/*  524:     */   
/*  525:     */   private org.omg.CORBA.portable.OutputStream _get_handle(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  526:     */     throws Throwable
/*  527:     */   {
/*  528: 505 */     Handle localHandle = this.target.getHandle();
/*  529: 506 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  530: 507 */     Util.writeAbstractObject(localOutputStream, localHandle);
/*  531: 508 */     return localOutputStream;
/*  532:     */   }
/*  533:     */   
/*  534:     */   private org.omg.CORBA.portable.OutputStream isIdentical(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  535:     */     throws Throwable
/*  536:     */   {
/*  537: 512 */     EJBObject localEJBObject = (EJBObject)paramInputStream.read_Object(EJBObject.class);
/*  538: 513 */     boolean bool = this.target.isIdentical(localEJBObject);
/*  539: 514 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  540: 515 */     localOutputStream.write_boolean(bool);
/*  541: 516 */     return localOutputStream;
/*  542:     */   }
/*  543:     */   
/*  544:     */   private org.omg.CORBA.portable.OutputStream disconnect(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  545:     */     throws Throwable
/*  546:     */   {
/*  547: 520 */     this.target.disconnect();
/*  548: 521 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  549: 522 */     return localOutputStream;
/*  550:     */   }
/*  551:     */   
/*  552:     */   private org.omg.CORBA.portable.OutputStream addCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  553:     */     throws Throwable
/*  554:     */   {
/*  555: 526 */     CustomerInfo[] arrayOfCustomerInfo = (CustomerInfo[])paramInputStream.read_value(new CustomerInfo[0].getClass());
/*  556: 527 */     int i = this.target.addCustomers(arrayOfCustomerInfo);
/*  557: 528 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  558: 529 */     localOutputStream.write_long(i);
/*  559: 530 */     return localOutputStream;
/*  560:     */   }
/*  561:     */   
/*  562:     */   private org.omg.CORBA.portable.OutputStream modifyCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  563:     */     throws Throwable
/*  564:     */   {
/*  565: 534 */     CustomerInfo[] arrayOfCustomerInfo = (CustomerInfo[])paramInputStream.read_value(new CustomerInfo[0].getClass());
/*  566: 535 */     int i = this.target.modifyCustomers(arrayOfCustomerInfo);
/*  567: 536 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  568: 537 */     localOutputStream.write_long(i);
/*  569: 538 */     return localOutputStream;
/*  570:     */   }
/*  571:     */   
/*  572:     */   private org.omg.CORBA.portable.OutputStream deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  573:     */     throws Throwable
/*  574:     */   {
/*  575: 542 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  576: 543 */     int i = this.target.deleteCustomers(arrayOfString);
/*  577: 544 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  578: 545 */     localOutputStream.write_long(i);
/*  579: 546 */     return localOutputStream;
/*  580:     */   }
/*  581:     */   
/*  582:     */   private org.omg.CORBA.portable.OutputStream deactivateCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  583:     */     throws Throwable
/*  584:     */   {
/*  585: 550 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  586: 551 */     int i = this.target.deactivateCustomers(arrayOfString);
/*  587: 552 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  588: 553 */     localOutputStream.write_long(i);
/*  589: 554 */     return localOutputStream;
/*  590:     */   }
/*  591:     */   
/*  592:     */   private org.omg.CORBA.portable.OutputStream activateCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  593:     */     throws Throwable
/*  594:     */   {
/*  595: 558 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  596: 559 */     int i = this.target.activateCustomers(arrayOfString);
/*  597: 560 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  598: 561 */     localOutputStream.write_long(i);
/*  599: 562 */     return localOutputStream;
/*  600:     */   }
/*  601:     */   
/*  602:     */   private org.omg.CORBA.portable.OutputStream deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  603:     */     throws Throwable
/*  604:     */   {
/*  605: 566 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  606: 567 */     int i = paramInputStream.read_long();
/*  607: 568 */     int j = this.target.deleteCustomers(arrayOfString, i);
/*  608: 569 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  609: 570 */     localOutputStream.write_long(j);
/*  610: 571 */     return localOutputStream;
/*  611:     */   }
/*  612:     */   
/*  613:     */   private org.omg.CORBA.portable.OutputStream getCustomersInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  614:     */     throws Throwable
/*  615:     */   {
/*  616: 575 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  617: 576 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomersInfo(arrayOfString);
/*  618: 577 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  619: 578 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  620: 579 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  621: 580 */     return localOutputStream;
/*  622:     */   }
/*  623:     */   
/*  624:     */   private org.omg.CORBA.portable.OutputStream getCustomerByType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  625:     */     throws Throwable
/*  626:     */   {
/*  627: 584 */     String str = (String)paramInputStream.read_value(String.class);
/*  628: 585 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByType(str);
/*  629: 586 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  630: 587 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  631: 588 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  632: 589 */     return localOutputStream;
/*  633:     */   }
/*  634:     */   
/*  635:     */   private org.omg.CORBA.portable.OutputStream getCustomerByFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  636:     */     throws Throwable
/*  637:     */   {
/*  638: 593 */     String str = (String)paramInputStream.read_value(String.class);
/*  639: 594 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByFI(str);
/*  640: 595 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  641: 596 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  642: 597 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  643: 598 */     return localOutputStream;
/*  644:     */   }
/*  645:     */   
/*  646:     */   private org.omg.CORBA.portable.OutputStream getCustomerByCategory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  647:     */     throws Throwable
/*  648:     */   {
/*  649: 602 */     String str = (String)paramInputStream.read_value(String.class);
/*  650: 603 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByCategory(str);
/*  651: 604 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  652: 605 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  653: 606 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  654: 607 */     return localOutputStream;
/*  655:     */   }
/*  656:     */   
/*  657:     */   private org.omg.CORBA.portable.OutputStream getCustomerByGroup(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  658:     */     throws Throwable
/*  659:     */   {
/*  660: 611 */     String str = (String)paramInputStream.read_value(String.class);
/*  661: 612 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByGroup(str);
/*  662: 613 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  663: 614 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  664: 615 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  665: 616 */     return localOutputStream;
/*  666:     */   }
/*  667:     */   
/*  668:     */   private org.omg.CORBA.portable.OutputStream getCustomerByTypeAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  669:     */     throws Throwable
/*  670:     */   {
/*  671: 620 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  672: 621 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  673: 622 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByTypeAndFI(str1, str2);
/*  674: 623 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  675: 624 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  676: 625 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  677: 626 */     return localOutputStream;
/*  678:     */   }
/*  679:     */   
/*  680:     */   private org.omg.CORBA.portable.OutputStream getCustomerByCategoryAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  681:     */     throws Throwable
/*  682:     */   {
/*  683: 630 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  684: 631 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  685: 632 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByCategoryAndFI(str1, str2);
/*  686: 633 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  687: 634 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  688: 635 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  689: 636 */     return localOutputStream;
/*  690:     */   }
/*  691:     */   
/*  692:     */   private org.omg.CORBA.portable.OutputStream getCustomerByGroupAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  693:     */     throws Throwable
/*  694:     */   {
/*  695: 640 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  696: 641 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  697: 642 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByGroupAndFI(str1, str2);
/*  698: 643 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  699: 644 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  700: 645 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/*  701: 646 */     return localOutputStream;
/*  702:     */   }
/*  703:     */   
/*  704:     */   private org.omg.CORBA.portable.OutputStream _get_linkedPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  705:     */     throws Throwable
/*  706:     */   {
/*  707: 650 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getLinkedPayees();
/*  708: 651 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  709: 652 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  710: 653 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/*  711: 654 */     return localOutputStream;
/*  712:     */   }
/*  713:     */   
/*  714:     */   private org.omg.CORBA.portable.OutputStream getMostUsedPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  715:     */     throws Throwable
/*  716:     */   {
/*  717: 658 */     int i = paramInputStream.read_long();
/*  718: 659 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getMostUsedPayees(i);
/*  719: 660 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  720: 661 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  721: 662 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/*  722: 663 */     return localOutputStream;
/*  723:     */   }
/*  724:     */   
/*  725:     */   private org.omg.CORBA.portable.OutputStream getPreferredPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  726:     */     throws Throwable
/*  727:     */   {
/*  728: 667 */     String str = (String)paramInputStream.read_value(String.class);
/*  729: 668 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getPreferredPayees(str);
/*  730: 669 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  731: 670 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  732: 671 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/*  733: 672 */     return localOutputStream;
/*  734:     */   }
/*  735:     */   
/*  736:     */   private org.omg.CORBA.portable.OutputStream getPendingPmtsByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  737:     */     throws Throwable
/*  738:     */   {
/*  739: 676 */     String str = (String)paramInputStream.read_value(String.class);
/*  740: 677 */     int i = paramInputStream.read_long();
/*  741: 678 */     TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = this.target.getPendingPmtsByCustomerID(str, i);
/*  742: 679 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  743: 680 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  744: 681 */     localOutputStream.write_value(cast_array(arrayOfTypePmtSyncRsV1), new TypePmtSyncRsV1[0].getClass());
/*  745: 682 */     return localOutputStream;
/*  746:     */   }
/*  747:     */   
/*  748:     */   private org.omg.CORBA.portable.OutputStream getPendingPmtsAndHistoryByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  749:     */     throws Throwable
/*  750:     */   {
/*  751: 686 */     String str = (String)paramInputStream.read_value(String.class);
/*  752: 687 */     int i = paramInputStream.read_long();
/*  753: 688 */     int j = paramInputStream.read_long();
/*  754: 689 */     TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = this.target.getPendingPmtsAndHistoryByCustomerID(str, i, j);
/*  755: 690 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  756: 691 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  757: 692 */     localOutputStream.write_value(cast_array(arrayOfTypePmtSyncRsV1), new TypePmtSyncRsV1[0].getClass());
/*  758: 693 */     return localOutputStream;
/*  759:     */   }
/*  760:     */   
/*  761:     */   private org.omg.CORBA.portable.OutputStream getPendingIntrasByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  762:     */     throws Throwable
/*  763:     */   {
/*  764: 697 */     String str = (String)paramInputStream.read_value(String.class);
/*  765: 698 */     int i = paramInputStream.read_long();
/*  766: 699 */     TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = this.target.getPendingIntrasByCustomerID(str, i);
/*  767: 700 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  768: 701 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  769: 702 */     localOutputStream.write_value(cast_array(arrayOfTypeIntraSyncRsV1), new TypeIntraSyncRsV1[0].getClass());
/*  770: 703 */     return localOutputStream;
/*  771:     */   }
/*  772:     */   
/*  773:     */   private org.omg.CORBA.portable.OutputStream getPendingIntrasAndHistoryByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  774:     */     throws Throwable
/*  775:     */   {
/*  776: 707 */     String str = (String)paramInputStream.read_value(String.class);
/*  777: 708 */     int i = paramInputStream.read_long();
/*  778: 709 */     int j = paramInputStream.read_long();
/*  779: 710 */     TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = this.target.getPendingIntrasAndHistoryByCustomerID(str, i, j);
/*  780: 711 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  781: 712 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  782: 713 */     localOutputStream.write_value(cast_array(arrayOfTypeIntraSyncRsV1), new TypeIntraSyncRsV1[0].getClass());
/*  783: 714 */     return localOutputStream;
/*  784:     */   }
/*  785:     */   
/*  786:     */   private org.omg.CORBA.portable.OutputStream getPendingPmts(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  787:     */     throws Throwable
/*  788:     */   {
/*  789: 718 */     TypePmtSyncRqV1 localTypePmtSyncRqV1 = (TypePmtSyncRqV1)paramInputStream.read_value(TypePmtSyncRqV1.class);
/*  790: 719 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  791: 720 */     int i = paramInputStream.read_long();
/*  792: 721 */     TypePmtSyncRsV1 localTypePmtSyncRsV1 = this.target.getPendingPmts(localTypePmtSyncRqV1, localTypeUserData, i);
/*  793: 722 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  794: 723 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  795: 724 */     localOutputStream.write_value(localTypePmtSyncRsV1, TypePmtSyncRsV1.class);
/*  796: 725 */     return localOutputStream;
/*  797:     */   }
/*  798:     */   
/*  799:     */   private org.omg.CORBA.portable.OutputStream getPendingIntras(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  800:     */     throws Throwable
/*  801:     */   {
/*  802: 729 */     TypeIntraSyncRqV1 localTypeIntraSyncRqV1 = (TypeIntraSyncRqV1)paramInputStream.read_value(TypeIntraSyncRqV1.class);
/*  803: 730 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  804: 731 */     int i = paramInputStream.read_long();
/*  805: 732 */     TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = this.target.getPendingIntras(localTypeIntraSyncRqV1, localTypeUserData, i);
/*  806: 733 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  807: 734 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  808: 735 */     localOutputStream.write_value(localTypeIntraSyncRsV1, TypeIntraSyncRsV1.class);
/*  809: 736 */     return localOutputStream;
/*  810:     */   }
/*  811:     */   
/*  812:     */   private org.omg.CORBA.portable.OutputStream getPmtStatus(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  813:     */     throws Throwable
/*  814:     */   {
/*  815: 740 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  816: 741 */     String str2 = this.target.getPmtStatus(str1);
/*  817: 742 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  818: 743 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  819: 744 */     localOutputStream.write_value(str2, String.class);
/*  820: 745 */     return localOutputStream;
/*  821:     */   }
/*  822:     */   
/*  823:     */   private org.omg.CORBA.portable.OutputStream checkPayeeEditMask(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  824:     */     throws Throwable
/*  825:     */   {
/*  826: 749 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  827: 750 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  828: 751 */     boolean bool = this.target.checkPayeeEditMask(str1, str2);
/*  829: 752 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  830: 753 */     localOutputStream.write_boolean(bool);
/*  831: 754 */     return localOutputStream;
/*  832:     */   }
/*  833:     */   
/*  834:     */   private org.omg.CORBA.portable.OutputStream processIntraTrnRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  835:     */     throws Throwable
/*  836:     */   {
/*  837: 758 */     IntraTrnRslt[] arrayOfIntraTrnRslt = (IntraTrnRslt[])paramInputStream.read_value(new IntraTrnRslt[0].getClass());
/*  838: 759 */     this.target.processIntraTrnRslt(arrayOfIntraTrnRslt);
/*  839: 760 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  840: 761 */     return localOutputStream;
/*  841:     */   }
/*  842:     */   
/*  843:     */   private org.omg.CORBA.portable.OutputStream processPmtTrnRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  844:     */     throws Throwable
/*  845:     */   {
/*  846: 765 */     PmtTrnRslt[] arrayOfPmtTrnRslt = (PmtTrnRslt[])paramInputStream.read_value(new PmtTrnRslt[0].getClass());
/*  847: 766 */     this.target.processPmtTrnRslt(arrayOfPmtTrnRslt);
/*  848: 767 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  849: 768 */     return localOutputStream;
/*  850:     */   }
/*  851:     */   
/*  852:     */   private org.omg.CORBA.portable.OutputStream processCustPayeeRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  853:     */     throws Throwable
/*  854:     */   {
/*  855: 772 */     CustPayeeRslt[] arrayOfCustPayeeRslt = (CustPayeeRslt[])paramInputStream.read_value(new CustPayeeRslt[0].getClass());
/*  856: 773 */     this.target.processCustPayeeRslt(arrayOfCustPayeeRslt);
/*  857: 774 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  858: 775 */     return localOutputStream;
/*  859:     */   }
/*  860:     */   
/*  861:     */   private org.omg.CORBA.portable.OutputStream processFundAllocRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  862:     */     throws Throwable
/*  863:     */   {
/*  864: 779 */     FundsAllocRslt[] arrayOfFundsAllocRslt = (FundsAllocRslt[])paramInputStream.read_value(new FundsAllocRslt[0].getClass());
/*  865: 780 */     this.target.processFundAllocRslt(arrayOfFundsAllocRslt);
/*  866: 781 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  867: 782 */     return localOutputStream;
/*  868:     */   }
/*  869:     */   
/*  870:     */   private org.omg.CORBA.portable.OutputStream processFundRevertRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  871:     */     throws Throwable
/*  872:     */   {
/*  873: 786 */     FundsAllocRslt[] arrayOfFundsAllocRslt = (FundsAllocRslt[])paramInputStream.read_value(new FundsAllocRslt[0].getClass());
/*  874: 787 */     this.target.processFundRevertRslt(arrayOfFundsAllocRslt);
/*  875: 788 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  876: 789 */     return localOutputStream;
/*  877:     */   }
/*  878:     */   
/*  879:     */   private org.omg.CORBA.portable.OutputStream processPayeeRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  880:     */     throws Throwable
/*  881:     */   {
/*  882: 793 */     PayeeRslt[] arrayOfPayeeRslt = (PayeeRslt[])paramInputStream.read_value(new PayeeRslt[0].getClass());
/*  883: 794 */     this.target.processPayeeRslt(arrayOfPayeeRslt);
/*  884: 795 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  885: 796 */     return localOutputStream;
/*  886:     */   }
/*  887:     */   
/*  888:     */   private org.omg.CORBA.portable.OutputStream addPayeeFromBackend(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  889:     */     throws Throwable
/*  890:     */   {
/*  891: 800 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/*  892: 801 */     String str = this.target.addPayeeFromBackend(localPayeeInfo);
/*  893: 802 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  894: 803 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  895: 804 */     localOutputStream.write_value(str, String.class);
/*  896: 805 */     return localOutputStream;
/*  897:     */   }
/*  898:     */   
/*  899:     */   private org.omg.CORBA.portable.OutputStream updatePayeeFromBackend(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  900:     */     throws Throwable
/*  901:     */   {
/*  902: 809 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/*  903: 810 */     PayeeInfo[] arrayOfPayeeInfo = this.target.updatePayeeFromBackend(localPayeeInfo);
/*  904: 811 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  905: 812 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  906: 813 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/*  907: 814 */     return localOutputStream;
/*  908:     */   }
/*  909:     */   
/*  910:     */   private org.omg.CORBA.portable.OutputStream addPayeeRouteInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  911:     */     throws Throwable
/*  912:     */   {
/*  913: 818 */     PayeeRouteInfo localPayeeRouteInfo = (PayeeRouteInfo)paramInputStream.read_value(PayeeRouteInfo.class);
/*  914: 819 */     this.target.addPayeeRouteInfo(localPayeeRouteInfo);
/*  915: 820 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  916: 821 */     return localOutputStream;
/*  917:     */   }
/*  918:     */   
/*  919:     */   private org.omg.CORBA.portable.OutputStream processIntraSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  920:     */     throws Throwable
/*  921:     */   {
/*  922: 825 */     TypeIntraSyncRqV1 localTypeIntraSyncRqV1 = (TypeIntraSyncRqV1)paramInputStream.read_value(TypeIntraSyncRqV1.class);
/*  923: 826 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  924: 827 */     TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = this.target.processIntraSyncRqV1(localTypeIntraSyncRqV1, localTypeUserData);
/*  925: 828 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  926: 829 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  927: 830 */     localOutputStream.write_value(localTypeIntraSyncRsV1, TypeIntraSyncRsV1.class);
/*  928: 831 */     return localOutputStream;
/*  929:     */   }
/*  930:     */   
/*  931:     */   private org.omg.CORBA.portable.OutputStream processIntraTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  932:     */     throws Throwable
/*  933:     */   {
/*  934: 835 */     TypeIntraTrnRqV1 localTypeIntraTrnRqV1 = (TypeIntraTrnRqV1)paramInputStream.read_value(TypeIntraTrnRqV1.class);
/*  935: 836 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  936: 837 */     TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = this.target.processIntraTrnRqV1(localTypeIntraTrnRqV1, localTypeUserData);
/*  937: 838 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  938: 839 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  939: 840 */     localOutputStream.write_value(localTypeIntraTrnRsV1, TypeIntraTrnRsV1.class);
/*  940: 841 */     return localOutputStream;
/*  941:     */   }
/*  942:     */   
/*  943:     */   private org.omg.CORBA.portable.OutputStream processPayeeSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  944:     */     throws Throwable
/*  945:     */   {
/*  946: 845 */     TypePayeeSyncRqV1 localTypePayeeSyncRqV1 = (TypePayeeSyncRqV1)paramInputStream.read_value(TypePayeeSyncRqV1.class);
/*  947: 846 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  948: 847 */     TypePayeeSyncRsV1 localTypePayeeSyncRsV1 = this.target.processPayeeSyncRqV1(localTypePayeeSyncRqV1, localTypeUserData);
/*  949: 848 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  950: 849 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  951: 850 */     localOutputStream.write_value(localTypePayeeSyncRsV1, TypePayeeSyncRsV1.class);
/*  952: 851 */     return localOutputStream;
/*  953:     */   }
/*  954:     */   
/*  955:     */   private org.omg.CORBA.portable.OutputStream processPayeeTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  956:     */     throws Throwable
/*  957:     */   {
/*  958: 855 */     TypePayeeTrnRqV1 localTypePayeeTrnRqV1 = (TypePayeeTrnRqV1)paramInputStream.read_value(TypePayeeTrnRqV1.class);
/*  959: 856 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  960: 857 */     TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = this.target.processPayeeTrnRqV1(localTypePayeeTrnRqV1, localTypeUserData);
/*  961: 858 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  962: 859 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  963: 860 */     localOutputStream.write_value(localTypePayeeTrnRsV1, TypePayeeTrnRsV1.class);
/*  964: 861 */     return localOutputStream;
/*  965:     */   }
/*  966:     */   
/*  967:     */   private org.omg.CORBA.portable.OutputStream processPmtInqTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  968:     */     throws Throwable
/*  969:     */   {
/*  970: 865 */     TypePmtInqTrnRqV1 localTypePmtInqTrnRqV1 = (TypePmtInqTrnRqV1)paramInputStream.read_value(TypePmtInqTrnRqV1.class);
/*  971: 866 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  972: 867 */     TypePmtInqTrnRsV1 localTypePmtInqTrnRsV1 = this.target.processPmtInqTrnRqV1(localTypePmtInqTrnRqV1, localTypeUserData);
/*  973: 868 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  974: 869 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  975: 870 */     localOutputStream.write_value(localTypePmtInqTrnRsV1, TypePmtInqTrnRsV1.class);
/*  976: 871 */     return localOutputStream;
/*  977:     */   }
/*  978:     */   
/*  979:     */   private org.omg.CORBA.portable.OutputStream processPmtSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  980:     */     throws Throwable
/*  981:     */   {
/*  982: 875 */     TypePmtSyncRqV1 localTypePmtSyncRqV1 = (TypePmtSyncRqV1)paramInputStream.read_value(TypePmtSyncRqV1.class);
/*  983: 876 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  984: 877 */     TypePmtSyncRsV1 localTypePmtSyncRsV1 = this.target.processPmtSyncRqV1(localTypePmtSyncRqV1, localTypeUserData);
/*  985: 878 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  986: 879 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  987: 880 */     localOutputStream.write_value(localTypePmtSyncRsV1, TypePmtSyncRsV1.class);
/*  988: 881 */     return localOutputStream;
/*  989:     */   }
/*  990:     */   
/*  991:     */   private org.omg.CORBA.portable.OutputStream processPmtTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  992:     */     throws Throwable
/*  993:     */   {
/*  994: 885 */     TypePmtTrnRqV1 localTypePmtTrnRqV1 = (TypePmtTrnRqV1)paramInputStream.read_value(TypePmtTrnRqV1.class);
/*  995: 886 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/*  996: 887 */     TypePmtTrnRsV1 localTypePmtTrnRsV1 = this.target.processPmtTrnRqV1(localTypePmtTrnRqV1, localTypeUserData);
/*  997: 888 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/*  998: 889 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  999: 890 */     localOutputStream.write_value(localTypePmtTrnRsV1, TypePmtTrnRsV1.class);
/* 1000: 891 */     return localOutputStream;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   private org.omg.CORBA.portable.OutputStream processRecIntraSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1004:     */     throws Throwable
/* 1005:     */   {
/* 1006: 895 */     TypeRecIntraSyncRqV1 localTypeRecIntraSyncRqV1 = (TypeRecIntraSyncRqV1)paramInputStream.read_value(TypeRecIntraSyncRqV1.class);
/* 1007: 896 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/* 1008: 897 */     TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1 = this.target.processRecIntraSyncRqV1(localTypeRecIntraSyncRqV1, localTypeUserData);
/* 1009: 898 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1010: 899 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1011: 900 */     localOutputStream.write_value(localTypeRecIntraSyncRsV1, TypeRecIntraSyncRsV1.class);
/* 1012: 901 */     return localOutputStream;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   private org.omg.CORBA.portable.OutputStream processRecIntraTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1016:     */     throws Throwable
/* 1017:     */   {
/* 1018: 905 */     TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1 = (TypeRecIntraTrnRqV1)paramInputStream.read_value(TypeRecIntraTrnRqV1.class);
/* 1019: 906 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/* 1020: 907 */     TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = this.target.processRecIntraTrnRqV1(localTypeRecIntraTrnRqV1, localTypeUserData);
/* 1021: 908 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1022: 909 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1023: 910 */     localOutputStream.write_value(localTypeRecIntraTrnRsV1, TypeRecIntraTrnRsV1.class);
/* 1024: 911 */     return localOutputStream;
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   private org.omg.CORBA.portable.OutputStream processRecPmtSyncRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1028:     */     throws Throwable
/* 1029:     */   {
/* 1030: 915 */     TypeRecPmtSyncRqV1 localTypeRecPmtSyncRqV1 = (TypeRecPmtSyncRqV1)paramInputStream.read_value(TypeRecPmtSyncRqV1.class);
/* 1031: 916 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/* 1032: 917 */     TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV1 = this.target.processRecPmtSyncRqV1(localTypeRecPmtSyncRqV1, localTypeUserData);
/* 1033: 918 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1034: 919 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1035: 920 */     localOutputStream.write_value(localTypeRecPmtSyncRsV1, TypeRecPmtSyncRsV1.class);
/* 1036: 921 */     return localOutputStream;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   private org.omg.CORBA.portable.OutputStream processRecPmtTrnRqV1(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1040:     */     throws Throwable
/* 1041:     */   {
/* 1042: 925 */     TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1 = (TypeRecPmtTrnRqV1)paramInputStream.read_value(TypeRecPmtTrnRqV1.class);
/* 1043: 926 */     TypeUserData localTypeUserData = (TypeUserData)paramInputStream.read_value(TypeUserData.class);
/* 1044: 927 */     TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = this.target.processRecPmtTrnRqV1(localTypeRecPmtTrnRqV1, localTypeUserData);
/* 1045: 928 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1046: 929 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1047: 930 */     localOutputStream.write_value(localTypeRecPmtTrnRsV1, TypeRecPmtTrnRsV1.class);
/* 1048: 931 */     return localOutputStream;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   private org.omg.CORBA.portable.OutputStream getPayeeNames__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1052:     */     throws Throwable
/* 1053:     */   {
/* 1054: 935 */     String str = (String)paramInputStream.read_value(String.class);
/* 1055: 936 */     String[] arrayOfString = this.target.getPayeeNames(str);
/* 1056: 937 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1057: 938 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1058: 939 */     localOutputStream.write_value(cast_array(arrayOfString), new String[0].getClass());
/* 1059: 940 */     return localOutputStream;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   private org.omg.CORBA.portable.OutputStream getPayeeIDs(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1063:     */     throws Throwable
/* 1064:     */   {
/* 1065: 944 */     String str = (String)paramInputStream.read_value(String.class);
/* 1066: 945 */     String[] arrayOfString = this.target.getPayeeIDs(str);
/* 1067: 946 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1068: 947 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1069: 948 */     localOutputStream.write_value(cast_array(arrayOfString), new String[0].getClass());
/* 1070: 949 */     return localOutputStream;
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   private org.omg.CORBA.portable.OutputStream getPayees__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1074:     */     throws Throwable
/* 1075:     */   {
/* 1076: 953 */     String str = (String)paramInputStream.read_value(String.class);
/* 1077: 954 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getPayees(str);
/* 1078: 955 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1079: 956 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1080: 957 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1081: 958 */     return localOutputStream;
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   private org.omg.CORBA.portable.OutputStream searchGlobalPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1085:     */     throws Throwable
/* 1086:     */   {
/* 1087: 962 */     String str = (String)paramInputStream.read_value(String.class);
/* 1088: 963 */     PayeeInfo[] arrayOfPayeeInfo = this.target.searchGlobalPayees(str);
/* 1089: 964 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1090: 965 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1091: 966 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1092: 967 */     return localOutputStream;
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   private org.omg.CORBA.portable.OutputStream updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1096:     */     throws Throwable
/* 1097:     */   {
/* 1098: 971 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1099: 972 */     PayeeRouteInfo localPayeeRouteInfo = (PayeeRouteInfo)paramInputStream.read_value(PayeeRouteInfo.class);
/* 1100: 973 */     this.target.updatePayee(localPayeeInfo, localPayeeRouteInfo);
/* 1101: 974 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1102: 975 */     return localOutputStream;
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   private org.omg.CORBA.portable.OutputStream deletePayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1106:     */     throws Throwable
/* 1107:     */   {
/* 1108: 979 */     String str = (String)paramInputStream.read_value(String.class);
/* 1109: 980 */     this.target.deletePayee(str);
/* 1110: 981 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1111: 982 */     return localOutputStream;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   private org.omg.CORBA.portable.OutputStream deletePayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1115:     */     throws Throwable
/* 1116:     */   {
/* 1117: 986 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1118: 987 */     this.target.deletePayees(arrayOfString);
/* 1119: 988 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1120: 989 */     return localOutputStream;
/* 1121:     */   }
/* 1122:     */   
/* 1123:     */   private org.omg.CORBA.portable.OutputStream findPayeeByID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1124:     */     throws Throwable
/* 1125:     */   {
/* 1126: 993 */     String str = (String)paramInputStream.read_value(String.class);
/* 1127: 994 */     PayeeInfo localPayeeInfo = this.target.findPayeeByID(str);
/* 1128: 995 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1129: 996 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1130: 997 */     localOutputStream.write_value(localPayeeInfo, PayeeInfo.class);
/* 1131: 998 */     return localOutputStream;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   private org.omg.CORBA.portable.OutputStream setPayeeStatus(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1135:     */     throws Throwable
/* 1136:     */   {
/* 1137:1002 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1138:1003 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1139:1004 */     this.target.setPayeeStatus(str1, str2);
/* 1140:1005 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1141:1006 */     return localOutputStream;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   private org.omg.CORBA.portable.OutputStream getSmartDate(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1145:     */     throws Throwable
/* 1146:     */   {
/* 1147:1010 */     int i = paramInputStream.read_long();
/* 1148:1011 */     int j = this.target.getSmartDate(i);
/* 1149:1012 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1150:1013 */     localOutputStream.write_long(j);
/* 1151:1014 */     return localOutputStream;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   private org.omg.CORBA.portable.OutputStream getPayees__CORBA_WStringValue__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1155:     */     throws Throwable
/* 1156:     */   {
/* 1157:1018 */     String str = (String)paramInputStream.read_value(String.class);
/* 1158:1019 */     int i = paramInputStream.read_long();
/* 1159:1020 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getPayees(str, i);
/* 1160:1021 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1161:1022 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1162:1023 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1163:1024 */     return localOutputStream;
/* 1164:     */   }
/* 1165:     */   
/* 1166:     */   private org.omg.CORBA.portable.OutputStream getGlobalPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1167:     */     throws Throwable
/* 1168:     */   {
/* 1169:1028 */     String str = (String)paramInputStream.read_value(String.class);
/* 1170:1029 */     int i = paramInputStream.read_long();
/* 1171:1030 */     PayeeInfo[] arrayOfPayeeInfo = this.target.getGlobalPayees(str, i);
/* 1172:1031 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1173:1032 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1174:1033 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1175:1034 */     return localOutputStream;
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   private org.omg.CORBA.portable.OutputStream getPayeeNames__CORBA_WStringValue__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1179:     */     throws Throwable
/* 1180:     */   {
/* 1181:1038 */     String str = (String)paramInputStream.read_value(String.class);
/* 1182:1039 */     int i = paramInputStream.read_long();
/* 1183:1040 */     String[] arrayOfString = this.target.getPayeeNames(str, i);
/* 1184:1041 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1185:1042 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1186:1043 */     localOutputStream.write_value(cast_array(arrayOfString), new String[0].getClass());
/* 1187:1044 */     return localOutputStream;
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   private org.omg.CORBA.portable.OutputStream addPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1191:     */     throws Throwable
/* 1192:     */   {
/* 1193:1048 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1194:1049 */     int i = paramInputStream.read_long();
/* 1195:1050 */     String str = this.target.addPayee(localPayeeInfo, i);
/* 1196:1051 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1197:1052 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1198:1053 */     localOutputStream.write_value(str, String.class);
/* 1199:1054 */     return localOutputStream;
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   private org.omg.CORBA.portable.OutputStream updatePayee__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1203:     */     throws Throwable
/* 1204:     */   {
/* 1205:1058 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1206:1059 */     int i = paramInputStream.read_long();
/* 1207:1060 */     PayeeInfo[] arrayOfPayeeInfo = this.target.updatePayee(localPayeeInfo, i);
/* 1208:1061 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1209:1062 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1210:1063 */     localOutputStream.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1211:1064 */     return localOutputStream;
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   private org.omg.CORBA.portable.OutputStream addConsumerCrossRef(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1215:     */     throws Throwable
/* 1216:     */   {
/* 1217:1068 */     ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = (ConsumerCrossRefInfo[])paramInputStream.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 1218:1069 */     int i = this.target.addConsumerCrossRef(arrayOfConsumerCrossRefInfo);
/* 1219:1070 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1220:1071 */     localOutputStream.write_long(i);
/* 1221:1072 */     return localOutputStream;
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   private org.omg.CORBA.portable.OutputStream deleteConsumerCrossRef(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1225:     */     throws Throwable
/* 1226:     */   {
/* 1227:1076 */     ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = (ConsumerCrossRefInfo[])paramInputStream.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 1228:1077 */     int i = this.target.deleteConsumerCrossRef(arrayOfConsumerCrossRefInfo);
/* 1229:1078 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1230:1079 */     localOutputStream.write_long(i);
/* 1231:1080 */     return localOutputStream;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   private org.omg.CORBA.portable.OutputStream getConsumerCrossRef(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1235:     */     throws Throwable
/* 1236:     */   {
/* 1237:1084 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1238:1085 */     ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = this.target.getConsumerCrossRef(arrayOfString);
/* 1239:1086 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1240:1087 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1241:1088 */     localOutputStream.write_value(cast_array(arrayOfConsumerCrossRefInfo), new ConsumerCrossRefInfo[0].getClass());
/* 1242:1089 */     return localOutputStream;
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   private org.omg.CORBA.portable.OutputStream addCustomerBankInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1246:     */     throws Throwable
/* 1247:     */   {
/* 1248:1093 */     CustomerBankInfo[] arrayOfCustomerBankInfo = (CustomerBankInfo[])paramInputStream.read_value(new CustomerBankInfo[0].getClass());
/* 1249:1094 */     int i = this.target.addCustomerBankInfo(arrayOfCustomerBankInfo);
/* 1250:1095 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1251:1096 */     localOutputStream.write_long(i);
/* 1252:1097 */     return localOutputStream;
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   private org.omg.CORBA.portable.OutputStream deleteCustomerBankInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1256:     */     throws Throwable
/* 1257:     */   {
/* 1258:1101 */     CustomerBankInfo[] arrayOfCustomerBankInfo = (CustomerBankInfo[])paramInputStream.read_value(new CustomerBankInfo[0].getClass());
/* 1259:1102 */     int i = this.target.deleteCustomerBankInfo(arrayOfCustomerBankInfo);
/* 1260:1103 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1261:1104 */     localOutputStream.write_long(i);
/* 1262:1105 */     return localOutputStream;
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   private org.omg.CORBA.portable.OutputStream getCustomerBankInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1266:     */     throws Throwable
/* 1267:     */   {
/* 1268:1109 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1269:1110 */     CustomerBankInfo[] arrayOfCustomerBankInfo = this.target.getCustomerBankInfo(arrayOfString);
/* 1270:1111 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1271:1112 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1272:1113 */     localOutputStream.write_value(cast_array(arrayOfCustomerBankInfo), new CustomerBankInfo[0].getClass());
/* 1273:1114 */     return localOutputStream;
/* 1274:     */   }
/* 1275:     */   
/* 1276:     */   private org.omg.CORBA.portable.OutputStream addCustomerProductAccessInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1277:     */     throws Throwable
/* 1278:     */   {
/* 1279:1118 */     CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo = (CustomerProductAccessInfo[])paramInputStream.read_value(new CustomerProductAccessInfo[0].getClass());
/* 1280:1119 */     int i = this.target.addCustomerProductAccessInfo(arrayOfCustomerProductAccessInfo);
/* 1281:1120 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1282:1121 */     localOutputStream.write_long(i);
/* 1283:1122 */     return localOutputStream;
/* 1284:     */   }
/* 1285:     */   
/* 1286:     */   private org.omg.CORBA.portable.OutputStream deleteCustomerProductAccessInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1287:     */     throws Throwable
/* 1288:     */   {
/* 1289:1126 */     CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo = (CustomerProductAccessInfo[])paramInputStream.read_value(new CustomerProductAccessInfo[0].getClass());
/* 1290:1127 */     int i = this.target.deleteCustomerProductAccessInfo(arrayOfCustomerProductAccessInfo);
/* 1291:1128 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1292:1129 */     localOutputStream.write_long(i);
/* 1293:1130 */     return localOutputStream;
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   private org.omg.CORBA.portable.OutputStream getCustomerProductAccessInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1297:     */     throws Throwable
/* 1298:     */   {
/* 1299:1134 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1300:1135 */     CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo = this.target.getCustomerProductAccessInfo(arrayOfString);
/* 1301:1136 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1302:1137 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1303:1138 */     localOutputStream.write_value(cast_array(arrayOfCustomerProductAccessInfo), new CustomerProductAccessInfo[0].getClass());
/* 1304:1139 */     return localOutputStream;
/* 1305:     */   }
/* 1306:     */   
/* 1307:     */   private org.omg.CORBA.portable.OutputStream validateMetavanteCustAcctByConsumerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1308:     */     throws Throwable
/* 1309:     */   {
/* 1310:1143 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1311:1144 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1312:1145 */     boolean bool = this.target.validateMetavanteCustAcctByConsumerID(str1, str2);
/* 1313:1146 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1314:1147 */     localOutputStream.write_boolean(bool);
/* 1315:1148 */     return localOutputStream;
/* 1316:     */   }
/* 1317:     */   
/* 1318:     */   private org.omg.CORBA.portable.OutputStream validateMetavanteCustAcctByCustomerID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1319:     */     throws Throwable
/* 1320:     */   {
/* 1321:1152 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1322:1153 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1323:1154 */     boolean bool = this.target.validateMetavanteCustAcctByCustomerID(str1, str2);
/* 1324:1155 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1325:1156 */     localOutputStream.write_boolean(bool);
/* 1326:1157 */     return localOutputStream;
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   private org.omg.CORBA.portable.OutputStream getPmtHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1330:     */     throws Throwable
/* 1331:     */   {
/* 1332:1161 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1333:1162 */     BPWHist localBPWHist2 = this.target.getPmtHistory(localBPWHist1);
/* 1334:1163 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1335:1164 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1336:1165 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1337:1166 */     return localOutputStream;
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   private org.omg.CORBA.portable.OutputStream getIntraHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1341:     */     throws Throwable
/* 1342:     */   {
/* 1343:1170 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1344:1171 */     BPWHist localBPWHist2 = this.target.getIntraHistory(localBPWHist1);
/* 1345:1172 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1346:1173 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1347:1174 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1348:1175 */     return localOutputStream;
/* 1349:     */   }
/* 1350:     */   
/* 1351:     */   private org.omg.CORBA.portable.OutputStream getIntraById__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1352:     */     throws Throwable
/* 1353:     */   {
/* 1354:1179 */     String str = (String)paramInputStream.read_value(String.class);
/* 1355:1180 */     IntraTrnInfo localIntraTrnInfo = this.target.getIntraById(str);
/* 1356:1181 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1357:1182 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1358:1183 */     localOutputStream.write_value(localIntraTrnInfo, IntraTrnInfo.class);
/* 1359:1184 */     return localOutputStream;
/* 1360:     */   }
/* 1361:     */   
/* 1362:     */   private org.omg.CORBA.portable.OutputStream getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1363:     */     throws Throwable
/* 1364:     */   {
/* 1365:1188 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1366:1189 */     IntraTrnInfo[] arrayOfIntraTrnInfo = this.target.getIntraById(arrayOfString);
/* 1367:1190 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1368:1191 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1369:1192 */     localOutputStream.write_value(cast_array(arrayOfIntraTrnInfo), new IntraTrnInfo[0].getClass());
/* 1370:1193 */     return localOutputStream;
/* 1371:     */   }
/* 1372:     */   
/* 1373:     */   private org.omg.CORBA.portable.OutputStream getPmtById__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1374:     */     throws Throwable
/* 1375:     */   {
/* 1376:1197 */     String str = (String)paramInputStream.read_value(String.class);
/* 1377:1198 */     PmtInfo localPmtInfo = this.target.getPmtById(str);
/* 1378:1199 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1379:1200 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1380:1201 */     localOutputStream.write_value(localPmtInfo, PmtInfo.class);
/* 1381:1202 */     return localOutputStream;
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   private org.omg.CORBA.portable.OutputStream getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1385:     */     throws Throwable
/* 1386:     */   {
/* 1387:1206 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1388:1207 */     PmtInfo[] arrayOfPmtInfo = this.target.getPmtById(arrayOfString);
/* 1389:1208 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1390:1209 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1391:1210 */     localOutputStream.write_value(cast_array(arrayOfPmtInfo), new PmtInfo[0].getClass());
/* 1392:1211 */     return localOutputStream;
/* 1393:     */   }
/* 1394:     */   
/* 1395:     */   private org.omg.CORBA.portable.OutputStream getRecPmtById(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1396:     */     throws Throwable
/* 1397:     */   {
/* 1398:1215 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1399:1216 */     RecPmtInfo[] arrayOfRecPmtInfo = this.target.getRecPmtById(arrayOfString);
/* 1400:1217 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1401:1218 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1402:1219 */     localOutputStream.write_value(cast_array(arrayOfRecPmtInfo), new RecPmtInfo[0].getClass());
/* 1403:1220 */     return localOutputStream;
/* 1404:     */   }
/* 1405:     */   
/* 1406:     */   private org.omg.CORBA.portable.OutputStream getPayeeByListId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1407:     */     throws Throwable
/* 1408:     */   {
/* 1409:1224 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1410:1225 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1411:     */     PayeeInfo localPayeeInfo;
/* 1412:     */     try
/* 1413:     */     {
/* 1414:1228 */       localPayeeInfo = this.target.getPayeeByListId(str1, str2);
/* 1415:     */     }
/* 1416:     */     catch (FFSException localFFSException)
/* 1417:     */     {
/* 1418:1230 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1419:1231 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1420:1232 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1421:1233 */       localOutputStream2.write_string(str3);
/* 1422:1234 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1423:1235 */       return localOutputStream2;
/* 1424:     */     }
/* 1425:1237 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1426:1238 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1427:1239 */     localOutputStream1.write_value(localPayeeInfo, PayeeInfo.class);
/* 1428:1240 */     return localOutputStream1;
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   private org.omg.CORBA.portable.OutputStream getAccountTypesMap(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1432:     */     throws Throwable
/* 1433:     */   {
/* 1434:     */     AccountTypesMap localAccountTypesMap;
/* 1435:     */     try
/* 1436:     */     {
/* 1437:1246 */       localAccountTypesMap = this.target.getAccountTypesMap();
/* 1438:     */     }
/* 1439:     */     catch (FFSException localFFSException)
/* 1440:     */     {
/* 1441:1248 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1442:1249 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1443:1250 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1444:1251 */       localOutputStream2.write_string(str);
/* 1445:1252 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1446:1253 */       return localOutputStream2;
/* 1447:     */     }
/* 1448:1255 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1449:1256 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1450:1257 */     localOutputStream1.write_value(localAccountTypesMap, AccountTypesMap.class);
/* 1451:1258 */     return localOutputStream1;
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   private Serializable cast_array(java.lang.Object paramObject)
/* 1455:     */   {
/* 1456:1265 */     return (Serializable)paramObject;
/* 1457:     */   }
/* 1458:     */ }


/* Location:           D:\drops\jd\jars\Deployed_OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices._EJSRemoteStatelessIOFX151BPWServices_79a24b5a_Tie
 * JD-Core Version:    0.7.0.1
 */