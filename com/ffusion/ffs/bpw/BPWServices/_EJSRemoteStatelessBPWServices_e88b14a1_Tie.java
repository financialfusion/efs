/*    1:     */ package com.ffusion.ffs.bpw.BPWServices;
/*    2:     */ 
/*    3:     */ import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
/*    4:     */ import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
/*    5:     */ import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
/*    6:     */ import com.ffusion.ffs.bpw.interfaces.BPWHist;
/*    7:     */ import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
/*    8:     */ import com.ffusion.ffs.bpw.interfaces.BankingDays;
/*    9:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
/*   10:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList;
/*   11:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
/*   12:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
/*   13:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
/*   14:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList;
/*   15:     */ import com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo;
/*   16:     */ import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList;
/*   18:     */ import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
/*   19:     */ import com.ffusion.ffs.bpw.interfaces.CCLocationInfoList;
/*   20:     */ import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
/*   21:     */ import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
/*   22:     */ import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
/*   23:     */ import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
/*   24:     */ import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
/*   25:     */ import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
/*   26:     */ import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
/*   27:     */ import com.ffusion.ffs.bpw.interfaces.LastPaymentInfo;
/*   28:     */ import com.ffusion.ffs.bpw.interfaces.NonBusinessDay;
/*   29:     */ import com.ffusion.ffs.bpw.interfaces.PagingInfo;
/*   30:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*   31:     */ import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
/*   32:     */ import com.ffusion.ffs.bpw.interfaces.PmtInfo;
/*   33:     */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
/*   34:     */ import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo;
/*   35:     */ import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
/*   36:     */ import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
/*   37:     */ import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
/*   38:     */ import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
/*   39:     */ import com.ffusion.ffs.bpw.interfaces.TransferInfo;
/*   40:     */ import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
/*   41:     */ import com.ffusion.ffs.bpw.interfaces.WireInfo;
/*   42:     */ import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
/*   43:     */ import com.ffusion.ffs.bpw.interfaces.WireReleaseInfo;
/*   44:     */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*   45:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   46:     */ import com.ibm.ejs.container.EJSWrapper;
/*   47:     */ import java.io.Serializable;
/*   48:     */ import java.rmi.Remote;
/*   49:     */ import java.util.HashMap;
/*   50:     */ import javax.ejb.EJBHome;
/*   51:     */ import javax.ejb.EJBObject;
/*   52:     */ import javax.ejb.Handle;
/*   53:     */ import javax.ejb.RemoveException;
/*   54:     */ import javax.rmi.CORBA.Tie;
/*   55:     */ import javax.rmi.CORBA.Util;
/*   56:     */ import org.omg.CORBA.BAD_OPERATION;
/*   57:     */ import org.omg.CORBA.ORB;
/*   58:     */ import org.omg.CORBA.SystemException;
/*   59:     */ import org.omg.CORBA.portable.Delegate;
/*   60:     */ import org.omg.CORBA.portable.ResponseHandler;
/*   61:     */ import org.omg.CORBA.portable.UnknownException;
/*   62:     */ 
/*   63:     */ public class _EJSRemoteStatelessBPWServices_e88b14a1_Tie
/*   64:     */   extends org.omg.CORBA_2_3.portable.ObjectImpl
/*   65:     */   implements Tie
/*   66:     */ {
/*   67:  72 */   private EJSRemoteStatelessBPWServices_e88b14a1 target = null;
/*   68:  73 */   private ORB orb = null;
/*   69:  75 */   private static final String[] _type_ids = {
/*   70:  76 */     "RMI:com.ffusion.ffs.bpw.BPWServices.BPWServices:0000000000000000", 
/*   71:  77 */     "RMI:javax.ejb.EJBObject:0000000000000000", 
/*   72:  78 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
/*   73:  79 */     "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000" };
/*   74:     */   
/*   75:     */   public void setTarget(Remote paramRemote)
/*   76:     */   {
/*   77:  83 */     this.target = ((EJSRemoteStatelessBPWServices_e88b14a1)paramRemote);
/*   78:     */   }
/*   79:     */   
/*   80:     */   public Remote getTarget()
/*   81:     */   {
/*   82:  87 */     return this.target;
/*   83:     */   }
/*   84:     */   
/*   85:     */   public org.omg.CORBA.Object thisObject()
/*   86:     */   {
/*   87:  91 */     return this;
/*   88:     */   }
/*   89:     */   
/*   90:     */   public void deactivate()
/*   91:     */   {
/*   92:  95 */     if (this.orb != null)
/*   93:     */     {
/*   94:  96 */       this.orb.disconnect(this);
/*   95:  97 */       _set_delegate(null);
/*   96:     */     }
/*   97:     */   }
/*   98:     */   
/*   99:     */   public ORB orb()
/*  100:     */   {
/*  101: 102 */     return _orb();
/*  102:     */   }
/*  103:     */   
/*  104:     */   public void orb(ORB paramORB)
/*  105:     */   {
/*  106: 106 */     paramORB.connect(this);
/*  107:     */   }
/*  108:     */   
/*  109:     */   public void _set_delegate(Delegate paramDelegate)
/*  110:     */   {
/*  111: 110 */     super._set_delegate(paramDelegate);
/*  112: 111 */     if (paramDelegate != null) {
/*  113: 112 */       this.orb = _orb();
/*  114:     */     } else {
/*  115: 114 */       this.orb = null;
/*  116:     */     }
/*  117:     */   }
/*  118:     */   
/*  119:     */   public String[] _ids()
/*  120:     */   {
/*  121: 118 */     return _type_ids;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public org.omg.CORBA.portable.OutputStream _invoke(String paramString, org.omg.CORBA.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  125:     */     throws SystemException
/*  126:     */   {
/*  127:     */     try
/*  128:     */     {
/*  129: 123 */       org.omg.CORBA_2_3.portable.InputStream localInputStream = 
/*  130: 124 */         (org.omg.CORBA_2_3.portable.InputStream)paramInputStream;
/*  131: 125 */       switch (paramString.hashCode())
/*  132:     */       {
/*  133:     */       case -2138089247: 
/*  134: 127 */         if (paramString.equals("modWirePayee")) {
/*  135: 128 */           return modWirePayee(localInputStream, paramResponseHandler);
/*  136:     */         }
/*  137:     */       case -2132488370: 
/*  138: 131 */         if (paramString.equals("getWireBanksByID")) {
/*  139: 132 */           return getWireBanksByID(localInputStream, paramResponseHandler);
/*  140:     */         }
/*  141:     */       case -2122728785: 
/*  142: 135 */         if (paramString.equals("addCCCompanyAcct")) {
/*  143: 136 */           return addCCCompanyAcct(localInputStream, paramResponseHandler);
/*  144:     */         }
/*  145:     */       case -2117805750: 
/*  146: 139 */         if (paramString.equals("canBPWFIInfo")) {
/*  147: 140 */           return canBPWFIInfo(localInputStream, paramResponseHandler);
/*  148:     */         }
/*  149:     */       case -2104966231: 
/*  150: 143 */         if (paramString.equals("getRecWireTransfers")) {
/*  151: 144 */           return getRecWireTransfers(localInputStream, paramResponseHandler);
/*  152:     */         }
/*  153:     */       case -2104430866: 
/*  154: 147 */         if (paramString.equals("addTransferBatch")) {
/*  155: 148 */           return addTransferBatch(localInputStream, paramResponseHandler);
/*  156:     */         }
/*  157:     */       case -2094795425: 
/*  158: 151 */         if (paramString.equals("addRecWireTransfer")) {
/*  159: 152 */           return addRecWireTransfer(localInputStream, paramResponseHandler);
/*  160:     */         }
/*  161:     */       case -2084034552: 
/*  162: 155 */         if (paramString.equals("cancRecTransfer")) {
/*  163: 156 */           return cancRecTransfer(localInputStream, paramResponseHandler);
/*  164:     */         }
/*  165:     */       case -2072345613: 
/*  166: 159 */         if (paramString.equals("getPagedBillPayments")) {
/*  167: 160 */           return getPagedBillPayments(localInputStream, paramResponseHandler);
/*  168:     */         }
/*  169:     */       case -2064760054: 
/*  170: 163 */         if (paramString.equals("getPagedTransfer")) {
/*  171: 164 */           return getPagedTransfer(localInputStream, paramResponseHandler);
/*  172:     */         }
/*  173:     */       case -2043286091: 
/*  174: 167 */         if (paramString.equals("processWireBackendlRslt")) {
/*  175: 168 */           return processWireBackendlRslt(localInputStream, paramResponseHandler);
/*  176:     */         }
/*  177:     */       case -2026776348: 
/*  178: 171 */         if (paramString.equals("processWireApprovalRevertRslt")) {
/*  179: 172 */           return processWireApprovalRevertRslt(localInputStream, paramResponseHandler);
/*  180:     */         }
/*  181:     */       case -2019917482: 
/*  182: 175 */         if (paramString.equals("getWirePayeeByStatus")) {
/*  183: 176 */           return getWirePayeeByStatus(localInputStream, paramResponseHandler);
/*  184:     */         }
/*  185:     */       case -2007564790: 
/*  186: 179 */         if (paramString.equals("getRecWireTransfer")) {
/*  187: 180 */           return getRecWireTransfer(localInputStream, paramResponseHandler);
/*  188:     */         }
/*  189:     */       case -1943625459: 
/*  190: 183 */         if (paramString.equals("modTransfer")) {
/*  191: 184 */           return modTransfer(localInputStream, paramResponseHandler);
/*  192:     */         }
/*  193:     */       case -1851674302: 
/*  194: 187 */         if (paramString.equals("getPayeeByListId")) {
/*  195: 188 */           return getPayeeByListId(localInputStream, paramResponseHandler);
/*  196:     */         }
/*  197:     */       case -1781024225: 
/*  198: 191 */         if (paramString.equals("deletePaymentBatch")) {
/*  199: 192 */           return deletePaymentBatch(localInputStream, paramResponseHandler);
/*  200:     */         }
/*  201:     */       case -1776939455: 
/*  202: 195 */         if (paramString.equals("deactivateCustomers")) {
/*  203: 196 */           return deactivateCustomers(localInputStream, paramResponseHandler);
/*  204:     */         }
/*  205:     */       case -1776654600: 
/*  206: 199 */         if (paramString.equals("getWireTransferById")) {
/*  207: 200 */           return getWireTransferById(localInputStream, paramResponseHandler);
/*  208:     */         }
/*  209:     */       case -1773915869: 
/*  210: 203 */         if (paramString.equals("addIntermediaryBanksToBeneficiary")) {
/*  211: 204 */           return addIntermediaryBanksToBeneficiary(localInputStream, paramResponseHandler);
/*  212:     */         }
/*  213:     */       case -1768635698: 
/*  214: 207 */         if (paramString.equals("getCustomerByFI")) {
/*  215: 208 */           return getCustomerByFI(localInputStream, paramResponseHandler);
/*  216:     */         }
/*  217:     */       case -1711568941: 
/*  218: 211 */         if (paramString.equals("canWirePayee")) {
/*  219: 212 */           return canWirePayee(localInputStream, paramResponseHandler);
/*  220:     */         }
/*  221:     */       case -1682620807: 
/*  222: 215 */         if (paramString.equals("getWireBanksByRTN")) {
/*  223: 216 */           return getWireBanksByRTN(localInputStream, paramResponseHandler);
/*  224:     */         }
/*  225:     */       case -1681582906: 
/*  226: 219 */         if (paramString.equals("getCustomerByGroupAndFI")) {
/*  227: 220 */           return getCustomerByGroupAndFI(localInputStream, paramResponseHandler);
/*  228:     */         }
/*  229:     */       case -1666109538: 
/*  230: 223 */         if (paramString.equals("getRPPSFIInfoByFIRPPSId")) {
/*  231: 224 */           return getRPPSFIInfoByFIRPPSId(localInputStream, paramResponseHandler);
/*  232:     */         }
/*  233:     */       case -1654552017: 
/*  234: 227 */         if (paramString.equals("cancelCCLocation")) {
/*  235: 228 */           return cancelCCLocation(localInputStream, paramResponseHandler);
/*  236:     */         }
/*  237:     */       case -1640297978: 
/*  238: 231 */         if (paramString.equals("getWireTransfer")) {
/*  239: 232 */           return getWireTransfer(localInputStream, paramResponseHandler);
/*  240:     */         }
/*  241:     */       case -1623985693: 
/*  242: 235 */         if (paramString.equals("getWireReleaseCount")) {
/*  243: 236 */           return getWireReleaseCount(localInputStream, paramResponseHandler);
/*  244:     */         }
/*  245:     */       case -1550521068: 
/*  246: 239 */         if (paramString.equals("_get_EJBHome")) {
/*  247: 240 */           return _get_EJBHome(localInputStream, paramResponseHandler);
/*  248:     */         }
/*  249:     */       case -1546059854: 
/*  250: 243 */         if (paramString.equals("modWireTransfer")) {
/*  251: 244 */           return modWireTransfer(localInputStream, paramResponseHandler);
/*  252:     */         }
/*  253:     */       case -1524648388: 
/*  254: 247 */         if (paramString.equals("getPaymentBatchById")) {
/*  255: 248 */           return getPaymentBatchById(localInputStream, paramResponseHandler);
/*  256:     */         }
/*  257:     */       case -1506722919: 
/*  258: 251 */         if (paramString.equals("addBPWFIInfo")) {
/*  259: 252 */           return addBPWFIInfo(localInputStream, paramResponseHandler);
/*  260:     */         }
/*  261:     */       case -1496165329: 
/*  262: 255 */         if (paramString.equals("getGlobalPayee")) {
/*  263: 256 */           return getGlobalPayee(localInputStream, paramResponseHandler);
/*  264:     */         }
/*  265:     */       case -1487510059: 
/*  266: 259 */         if (paramString.equals("getBPWFIInfoByType")) {
/*  267: 260 */           return getBPWFIInfoByType(localInputStream, paramResponseHandler);
/*  268:     */         }
/*  269:     */       case -1466652951: 
/*  270: 263 */         if (paramString.equals("addWireTransferBatch")) {
/*  271: 264 */           return addWireTransferBatch(localInputStream, paramResponseHandler);
/*  272:     */         }
/*  273:     */       case -1457427465: 
/*  274: 267 */         if (paramString.equals("activateRPPSFI")) {
/*  275: 268 */           return activateRPPSFI(localInputStream, paramResponseHandler);
/*  276:     */         }
/*  277:     */       case -1454154143: 
/*  278: 271 */         if (paramString.equals("getSmartDate")) {
/*  279: 272 */           return getSmartDate(localInputStream, paramResponseHandler);
/*  280:     */         }
/*  281:     */       case -1447393269: 
/*  282: 275 */         if (paramString.equals("getCCLocation")) {
/*  283: 276 */           return getCCLocation(localInputStream, paramResponseHandler);
/*  284:     */         }
/*  285:     */       case -1433460623: 
/*  286: 279 */         if (paramString.equals("getBPWFIInfoByRTN")) {
/*  287: 280 */           return getBPWFIInfoByRTN(localInputStream, paramResponseHandler);
/*  288:     */         }
/*  289:     */       case -1387475397: 
/*  290: 283 */         if (paramString.equals("getTransfersByTrackingId")) {
/*  291: 284 */           return getTransfersByTrackingId(localInputStream, paramResponseHandler);
/*  292:     */         }
/*  293:     */       case -1309038430: 
/*  294: 287 */         if (paramString.equals("activateCustomers")) {
/*  295: 288 */           return activateCustomers(localInputStream, paramResponseHandler);
/*  296:     */         }
/*  297:     */       case -1227350666: 
/*  298: 291 */         if (paramString.equals("searchGlobalPayees")) {
/*  299: 292 */           return searchGlobalPayees(localInputStream, paramResponseHandler);
/*  300:     */         }
/*  301:     */       case -1215792165: 
/*  302: 295 */         if (paramString.equals("getRecTransferBySrvrTId__CORBA_WStringValue")) {
/*  303: 296 */           return getRecTransferBySrvrTId__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  304:     */         }
/*  305:     */       case -1202064098: 
/*  306: 299 */         if (paramString.equals("getCCEntryHist")) {
/*  307: 300 */           return getCCEntryHist(localInputStream, paramResponseHandler);
/*  308:     */         }
/*  309:     */       case -1196181787: 
/*  310: 303 */         if (paramString.equals("modifyBillPayment")) {
/*  311: 304 */           return modifyBillPayment(localInputStream, paramResponseHandler);
/*  312:     */         }
/*  313:     */       case -1190634799: 
/*  314: 307 */         if (paramString.equals("addWireTransfer")) {
/*  315: 308 */           return addWireTransfer(localInputStream, paramResponseHandler);
/*  316:     */         }
/*  317:     */       case -1175081143: 
/*  318: 311 */         if (paramString.equals("getCCLocationList")) {
/*  319: 312 */           return getCCLocationList(localInputStream, paramResponseHandler);
/*  320:     */         }
/*  321:     */       case -1163051884: 
/*  322: 315 */         if (paramString.equals("modRPPSFIInfo")) {
/*  323: 316 */           return modRPPSFIInfo(localInputStream, paramResponseHandler);
/*  324:     */         }
/*  325:     */       case -1159732818: 
/*  326: 319 */         if (paramString.equals("getRecTransfers")) {
/*  327: 320 */           return getRecTransfers(localInputStream, paramResponseHandler);
/*  328:     */         }
/*  329:     */       case -1131674726: 
/*  330: 323 */         if (paramString.equals("canWireTransferBatch")) {
/*  331: 324 */           return canWireTransferBatch(localInputStream, paramResponseHandler);
/*  332:     */         }
/*  333:     */       case -1129607274: 
/*  334: 327 */         if (paramString.equals("addCCLocation")) {
/*  335: 328 */           return addCCLocation(localInputStream, paramResponseHandler);
/*  336:     */         }
/*  337:     */       case -1113425775: 
/*  338: 331 */         if (paramString.equals("addWireBanks")) {
/*  339: 332 */           return addWireBanks(localInputStream, paramResponseHandler);
/*  340:     */         }
/*  341:     */       case -1031201097: 
/*  342: 335 */         if (paramString.equals("getRecTransfersByTrackingId")) {
/*  343: 336 */           return getRecTransfersByTrackingId(localInputStream, paramResponseHandler);
/*  344:     */         }
/*  345:     */       case -1011244123: 
/*  346: 339 */         if (paramString.equals("_get_primaryKey")) {
/*  347: 340 */           return _get_primaryKey(localInputStream, paramResponseHandler);
/*  348:     */         }
/*  349:     */       case -992608424: 
/*  350: 343 */         if (paramString.equals("getTransfersByRecSrvrTId__CORBA_WStringValue__boolean")) {
/*  351: 344 */           return getTransfersByRecSrvrTId__CORBA_WStringValue__boolean(localInputStream, paramResponseHandler);
/*  352:     */         }
/*  353:     */       case -989425098: 
/*  354: 347 */         if (paramString.equals("getBPWFIInfo__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  355: 348 */           return getBPWFIInfo__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  356:     */         }
/*  357:     */       case -985129604: 
/*  358: 351 */         if (paramString.equals("getWireBanks")) {
/*  359: 352 */           return getWireBanks(localInputStream, paramResponseHandler);
/*  360:     */         }
/*  361:     */       case -981599276: 
/*  362: 355 */         if (paramString.equals("getNonBusinessDaysFromFile")) {
/*  363: 356 */           return getNonBusinessDaysFromFile(localInputStream, paramResponseHandler);
/*  364:     */         }
/*  365:     */       case -934610812: 
/*  366: 359 */         if (paramString.equals("remove")) {
/*  367: 360 */           return remove(localInputStream, paramResponseHandler);
/*  368:     */         }
/*  369:     */       case -877274796: 
/*  370: 363 */         if (paramString.equals("deleteBillPayment")) {
/*  371: 364 */           return deleteBillPayment(localInputStream, paramResponseHandler);
/*  372:     */         }
/*  373:     */       case -842315410: 
/*  374: 367 */         if (paramString.equals("getNonBusinessDays")) {
/*  375: 368 */           return getNonBusinessDays(localInputStream, paramResponseHandler);
/*  376:     */         }
/*  377:     */       case -825824286: 
/*  378: 371 */         if (paramString.equals("canRPPSFIInfo")) {
/*  379: 372 */           return canRPPSFIInfo(localInputStream, paramResponseHandler);
/*  380:     */         }
/*  381:     */       case -791372692: 
/*  382: 375 */         if (paramString.equals("addWirePayee__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_WirePayeeInfo")) {
/*  383: 376 */           return addWirePayee__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_WirePayeeInfo(localInputStream, paramResponseHandler);
/*  384:     */         }
/*  385:     */       case -788999280: 
/*  386: 379 */         if (paramString.equals("isWireBatchDeleteable")) {
/*  387: 380 */           return isWireBatchDeleteable(localInputStream, paramResponseHandler);
/*  388:     */         }
/*  389:     */       case -771927004: 
/*  390: 383 */         if (paramString.equals("modExtTransferAccountPrenoteStatus")) {
/*  391: 384 */           return modExtTransferAccountPrenoteStatus(localInputStream, paramResponseHandler);
/*  392:     */         }
/*  393:     */       case -767483875: 
/*  394: 387 */         if (paramString.equals("getRPPSFIInfoByFIId")) {
/*  395: 388 */           return getRPPSFIInfoByFIId(localInputStream, paramResponseHandler);
/*  396:     */         }
/*  397:     */       case -735974853: 
/*  398: 391 */         if (paramString.equals("getBillPaymentById")) {
/*  399: 392 */           return getBillPaymentById(localInputStream, paramResponseHandler);
/*  400:     */         }
/*  401:     */       case -693126214: 
/*  402: 395 */         if (paramString.equals("verifyExtTransferAccount")) {
/*  403: 396 */           return verifyExtTransferAccount(localInputStream, paramResponseHandler);
/*  404:     */         }
/*  405:     */       case -661126717: 
/*  406: 399 */         if (paramString.equals("cancWireTransfer")) {
/*  407: 400 */           return cancWireTransfer(localInputStream, paramResponseHandler);
/*  408:     */         }
/*  409:     */       case -657970351: 
/*  410: 403 */         if (paramString.equals("canExtTransferAccount")) {
/*  411: 404 */           return canExtTransferAccount(localInputStream, paramResponseHandler);
/*  412:     */         }
/*  413:     */       case -600523973: 
/*  414: 407 */         if (paramString.equals("modCCCompany")) {
/*  415: 408 */           return modCCCompany(localInputStream, paramResponseHandler);
/*  416:     */         }
/*  417:     */       case -573793456: 
/*  418: 411 */         if (paramString.equals("getWirePayee__CORBA_WStringValue")) {
/*  419: 412 */           return getWirePayee__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  420:     */         }
/*  421:     */       case -573520534: 
/*  422: 415 */         if (paramString.equals("getBatchWires")) {
/*  423: 416 */           return getBatchWires(localInputStream, paramResponseHandler);
/*  424:     */         }
/*  425:     */       case -563580688: 
/*  426: 419 */         if (paramString.equals("getAuditWireTransferByExtId")) {
/*  427: 420 */           return getAuditWireTransferByExtId(localInputStream, paramResponseHandler);
/*  428:     */         }
/*  429:     */       case -552770791: 
/*  430: 423 */         if (paramString.equals("getWireHistory")) {
/*  431: 424 */           return getWireHistory(localInputStream, paramResponseHandler);
/*  432:     */         }
/*  433:     */       case -527996862: 
/*  434: 427 */         if (paramString.equals("getWirePayeeByCustomer")) {
/*  435: 428 */           return getWirePayeeByCustomer(localInputStream, paramResponseHandler);
/*  436:     */         }
/*  437:     */       case -514148620: 
/*  438: 431 */         if (paramString.equals("addRecWireTransfers")) {
/*  439: 432 */           return addRecWireTransfers(localInputStream, paramResponseHandler);
/*  440:     */         }
/*  441:     */       case -495287513: 
/*  442: 435 */         if (paramString.equals("inactivateExtTransferAcct")) {
/*  443: 436 */           return inactivateExtTransferAcct(localInputStream, paramResponseHandler);
/*  444:     */         }
/*  445:     */       case -470898354: 
/*  446: 439 */         if (paramString.equals("getWirePayee__com_ffusion_ffs_bpw_interfaces_WirePayeeInfo")) {
/*  447: 440 */           return getWirePayee__com_ffusion_ffs_bpw_interfaces_WirePayeeInfo(localInputStream, paramResponseHandler);
/*  448:     */         }
/*  449:     */       case -404725959: 
/*  450: 443 */         if (paramString.equals("getDuplicateWires")) {
/*  451: 444 */           return getDuplicateWires(localInputStream, paramResponseHandler);
/*  452:     */         }
/*  453:     */       case -395937320: 
/*  454: 447 */         if (paramString.equals("getCCCompanyAcctList")) {
/*  455: 448 */           return getCCCompanyAcctList(localInputStream, paramResponseHandler);
/*  456:     */         }
/*  457:     */       case -380858641: 
/*  458: 451 */         if (paramString.equals("getRecTransfersBySrvrTId")) {
/*  459: 452 */           return getRecTransfersBySrvrTId(localInputStream, paramResponseHandler);
/*  460:     */         }
/*  461:     */       case -361415190: 
/*  462: 455 */         if (paramString.equals("getTransferByTrackingId")) {
/*  463: 456 */           return getTransferByTrackingId(localInputStream, paramResponseHandler);
/*  464:     */         }
/*  465:     */       case -357885638: 
/*  466: 459 */         if (paramString.equals("getAccountTypesMap")) {
/*  467: 460 */           return getAccountTypesMap(localInputStream, paramResponseHandler);
/*  468:     */         }
/*  469:     */       case -354119522: 
/*  470: 463 */         if (paramString.equals("addBillPayment")) {
/*  471: 464 */           return addBillPayment(localInputStream, paramResponseHandler);
/*  472:     */         }
/*  473:     */       case -353370136: 
/*  474: 467 */         if (paramString.equals("getCCEntrySummaryList")) {
/*  475: 468 */           return getCCEntrySummaryList(localInputStream, paramResponseHandler);
/*  476:     */         }
/*  477:     */       case -345839895: 
/*  478: 471 */         if (paramString.equals("getCustomerByCategory")) {
/*  479: 472 */           return getCustomerByCategory(localInputStream, paramResponseHandler);
/*  480:     */         }
/*  481:     */       case -324557774: 
/*  482: 475 */         if (paramString.equals("addExtTransferCompany")) {
/*  483: 476 */           return addExtTransferCompany(localInputStream, paramResponseHandler);
/*  484:     */         }
/*  485:     */       case -304252047: 
/*  486: 479 */         if (paramString.equals("getCustomerByCategoryAndFI")) {
/*  487: 480 */           return getCustomerByCategoryAndFI(localInputStream, paramResponseHandler);
/*  488:     */         }
/*  489:     */       case -256003602: 
/*  490: 483 */         if (paramString.equals("modCCCompanyAcct")) {
/*  491: 484 */           return modCCCompanyAcct(localInputStream, paramResponseHandler);
/*  492:     */         }
/*  493:     */       case -244877867: 
/*  494: 487 */         if (paramString.equals("getValidTransferDateDue")) {
/*  495: 488 */           return getValidTransferDateDue(localInputStream, paramResponseHandler);
/*  496:     */         }
/*  497:     */       case -212239500: 
/*  498: 491 */         if (paramString.equals("getRPPSBillerInfoByFIAndBillerRPPSId")) {
/*  499: 492 */           return getRPPSBillerInfoByFIAndBillerRPPSId(localInputStream, paramResponseHandler);
/*  500:     */         }
/*  501:     */       case -172452589: 
/*  502: 495 */         if (paramString.equals("getNextCashConCutOff")) {
/*  503: 496 */           return getNextCashConCutOff(localInputStream, paramResponseHandler);
/*  504:     */         }
/*  505:     */       case -143647632: 
/*  506: 499 */         if (paramString.equals("cancelCCCompanyCutOff")) {
/*  507: 500 */           return cancelCCCompanyCutOff(localInputStream, paramResponseHandler);
/*  508:     */         }
/*  509:     */       case -118248957: 
/*  510: 503 */         if (paramString.equals("modExtTransferAccount")) {
/*  511: 504 */           return modExtTransferAccount(localInputStream, paramResponseHandler);
/*  512:     */         }
/*  513:     */       case -91924569: 
/*  514: 507 */         if (paramString.equals("getExtTransferCompany")) {
/*  515: 508 */           return getExtTransferCompany(localInputStream, paramResponseHandler);
/*  516:     */         }
/*  517:     */       case -88685209: 
/*  518: 511 */         if (paramString.equals("getExtTransferAcctList")) {
/*  519: 512 */           return getExtTransferAcctList(localInputStream, paramResponseHandler);
/*  520:     */         }
/*  521:     */       case -77681173: 
/*  522: 515 */         if (paramString.equals("getTransfersBySrvrTId")) {
/*  523: 516 */           return getTransfersBySrvrTId(localInputStream, paramResponseHandler);
/*  524:     */         }
/*  525:     */       case -46447249: 
/*  526: 519 */         if (paramString.equals("getRecTransferHistory")) {
/*  527: 520 */           return getRecTransferHistory(localInputStream, paramResponseHandler);
/*  528:     */         }
/*  529:     */       case -10428779: 
/*  530: 523 */         if (paramString.equals("getRecWireHistory")) {
/*  531: 524 */           return getRecWireHistory(localInputStream, paramResponseHandler);
/*  532:     */         }
/*  533:     */       case 43209630: 
/*  534: 527 */         if (paramString.equals("cancTransfer")) {
/*  535: 528 */           return cancTransfer(localInputStream, paramResponseHandler);
/*  536:     */         }
/*  537:     */       case 56053989: 
/*  538: 531 */         if (paramString.equals("getExtTransferCompanyList")) {
/*  539: 532 */           return getExtTransferCompanyList(localInputStream, paramResponseHandler);
/*  540:     */         }
/*  541:     */       case 169931344: 
/*  542: 535 */         if (paramString.equals("getPagedCashCon")) {
/*  543: 536 */           return getPagedCashCon(localInputStream, paramResponseHandler);
/*  544:     */         }
/*  545:     */       case 176703902: 
/*  546: 539 */         if (paramString.equals("getWirePayeeByType")) {
/*  547: 540 */           return getWirePayeeByType(localInputStream, paramResponseHandler);
/*  548:     */         }
/*  549:     */       case 199730279: 
/*  550: 543 */         if (paramString.equals("delWireBanks")) {
/*  551: 544 */           return delWireBanks(localInputStream, paramResponseHandler);
/*  552:     */         }
/*  553:     */       case 221126967: 
/*  554: 547 */         if (paramString.equals("releaseWireTransfer")) {
/*  555: 548 */           return releaseWireTransfer(localInputStream, paramResponseHandler);
/*  556:     */         }
/*  557:     */       case 241459049: 
/*  558: 551 */         if (paramString.equals("addCCCompanyCutOff")) {
/*  559: 552 */           return addCCCompanyCutOff(localInputStream, paramResponseHandler);
/*  560:     */         }
/*  561:     */       case 306602924: 
/*  562: 555 */         if (paramString.equals("addTransfer")) {
/*  563: 556 */           return addTransfer(localInputStream, paramResponseHandler);
/*  564:     */         }
/*  565:     */       case 324049180: 
/*  566: 559 */         if (paramString.equals("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/*  567: 560 */           return deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/*  568:     */         }
/*  569:     */       case 324795989: 
/*  570: 563 */         if (paramString.equals("getAuditWireTransfer")) {
/*  571: 564 */           return getAuditWireTransfer(localInputStream, paramResponseHandler);
/*  572:     */         }
/*  573:     */       case 328689684: 
/*  574: 567 */         if (paramString.equals("getCCCompanyCutOff")) {
/*  575: 568 */           return getCCCompanyCutOff(localInputStream, paramResponseHandler);
/*  576:     */         }
/*  577:     */       case 342814190: 
/*  578: 571 */         if (paramString.equals("getRecTransferByTrackingId")) {
/*  579: 572 */           return getRecTransferByTrackingId(localInputStream, paramResponseHandler);
/*  580:     */         }
/*  581:     */       case 422551413: 
/*  582: 575 */         if (paramString.equals("modifyTransferBatch")) {
/*  583: 576 */           return modifyTransferBatch(localInputStream, paramResponseHandler);
/*  584:     */         }
/*  585:     */       case 437079164: 
/*  586: 579 */         if (paramString.equals("addCCCompany")) {
/*  587: 580 */           return addCCCompany(localInputStream, paramResponseHandler);
/*  588:     */         }
/*  589:     */       case 530405532: 
/*  590: 583 */         if (paramString.equals("disconnect")) {
/*  591: 584 */           return disconnect(localInputStream, paramResponseHandler);
/*  592:     */         }
/*  593:     */       case 565375335: 
/*  594: 587 */         if (paramString.equals("getCCCompany")) {
/*  595: 588 */           return getCCCompany(localInputStream, paramResponseHandler);
/*  596:     */         }
/*  597:     */       case 637031611: 
/*  598: 591 */         if (paramString.equals("modifyCustomers")) {
/*  599: 592 */           return modifyCustomers(localInputStream, paramResponseHandler);
/*  600:     */         }
/*  601:     */       case 659539294: 
/*  602: 595 */         if (paramString.equals("accountHasPendingTransfers")) {
/*  603: 596 */           return accountHasPendingTransfers(localInputStream, paramResponseHandler);
/*  604:     */         }
/*  605:     */       case 690370349: 
/*  606: 599 */         if (paramString.equals("getWireTransfers")) {
/*  607: 600 */           return getWireTransfers(localInputStream, paramResponseHandler);
/*  608:     */         }
/*  609:     */       case 690679772: 
/*  610: 603 */         if (paramString.equals("getCCEntry")) {
/*  611: 604 */           return getCCEntry(localInputStream, paramResponseHandler);
/*  612:     */         }
/*  613:     */       case 693267405: 
/*  614: 607 */         if (paramString.equals("getBPWFIInfoByStatus")) {
/*  615: 608 */           return getBPWFIInfoByStatus(localInputStream, paramResponseHandler);
/*  616:     */         }
/*  617:     */       case 719413970: 
/*  618: 611 */         if (paramString.equals("getCCCompanyCutOffList")) {
/*  619: 612 */           return getCCCompanyCutOffList(localInputStream, paramResponseHandler);
/*  620:     */         }
/*  621:     */       case 735123217: 
/*  622: 615 */         if (paramString.equals("processPmtTrnRslt")) {
/*  623: 616 */           return processPmtTrnRslt(localInputStream, paramResponseHandler);
/*  624:     */         }
/*  625:     */       case 757608660: 
/*  626: 619 */         if (paramString.equals("getWireTransferBatch")) {
/*  627: 620 */           return getWireTransferBatch(localInputStream, paramResponseHandler);
/*  628:     */         }
/*  629:     */       case 796729008: 
/*  630: 623 */         if (paramString.equals("modCCEntry")) {
/*  631: 624 */           return modCCEntry(localInputStream, paramResponseHandler);
/*  632:     */         }
/*  633:     */       case 826743006: 
/*  634: 627 */         if (paramString.equals("modRecWireTransfer")) {
/*  635: 628 */           return modRecWireTransfer(localInputStream, paramResponseHandler);
/*  636:     */         }
/*  637:     */       case 835787459: 
/*  638: 631 */         if (paramString.equals("cancelCCCompany")) {
/*  639: 632 */           return cancelCCCompany(localInputStream, paramResponseHandler);
/*  640:     */         }
/*  641:     */       case 878027556: 
/*  642: 635 */         if (paramString.equals("getPagedWire")) {
/*  643: 636 */           return getPagedWire(localInputStream, paramResponseHandler);
/*  644:     */         }
/*  645:     */       case 914756167: 
/*  646: 639 */         if (paramString.equals("addTransfers")) {
/*  647: 640 */           return addTransfers(localInputStream, paramResponseHandler);
/*  648:     */         }
/*  649:     */       case 937874291: 
/*  650: 643 */         if (paramString.equals("addRPPSFIInfo")) {
/*  651: 644 */           return addRPPSFIInfo(localInputStream, paramResponseHandler);
/*  652:     */         }
/*  653:     */       case 976994360: 
/*  654: 647 */         if (paramString.equals("cancelCCEntry")) {
/*  655: 648 */           return cancelCCEntry(localInputStream, paramResponseHandler);
/*  656:     */         }
/*  657:     */       case 981553364: 
/*  658: 651 */         if (paramString.equals("getCombinedWireHistory")) {
/*  659: 652 */           return getCombinedWireHistory(localInputStream, paramResponseHandler);
/*  660:     */         }
/*  661:     */       case 1050730221: 
/*  662: 655 */         if (paramString.equals("getCustomersInfo")) {
/*  663: 656 */           return getCustomersInfo(localInputStream, paramResponseHandler);
/*  664:     */         }
/*  665:     */       case 1064433847: 
/*  666: 659 */         if (paramString.equals("modCCLocation")) {
/*  667: 660 */           return modCCLocation(localInputStream, paramResponseHandler);
/*  668:     */         }
/*  669:     */       case 1075812495: 
/*  670: 663 */         if (paramString.equals("processApprovalResult")) {
/*  671: 664 */           return processApprovalResult(localInputStream, paramResponseHandler);
/*  672:     */         }
/*  673:     */       case 1089499445: 
/*  674: 667 */         if (paramString.equals("getCustomerByTypeAndFI")) {
/*  675: 668 */           return getCustomerByTypeAndFI(localInputStream, paramResponseHandler);
/*  676:     */         }
/*  677:     */       case 1112633606: 
/*  678: 671 */         if (paramString.equals("getBPWFIInfoByACHId")) {
/*  679: 672 */           return getBPWFIInfoByACHId(localInputStream, paramResponseHandler);
/*  680:     */         }
/*  681:     */       case 1117581850: 
/*  682: 675 */         if (paramString.equals("getCCCompanyAcct")) {
/*  683: 676 */           return getCCCompanyAcct(localInputStream, paramResponseHandler);
/*  684:     */         }
/*  685:     */       case 1117915813: 
/*  686: 679 */         if (paramString.equals("getCCCompanyList")) {
/*  687: 680 */           return getCCCompanyList(localInputStream, paramResponseHandler);
/*  688:     */         }
/*  689:     */       case 1119613764: 
/*  690: 683 */         if (paramString.equals("getBPWFIInfoByGroup")) {
/*  691: 684 */           return getBPWFIInfoByGroup(localInputStream, paramResponseHandler);
/*  692:     */         }
/*  693:     */       case 1148610213: 
/*  694: 687 */         if (paramString.equals("getCustomerByType")) {
/*  695: 688 */           return getCustomerByType(localInputStream, paramResponseHandler);
/*  696:     */         }
/*  697:     */       case 1170639003: 
/*  698: 691 */         if (paramString.equals("getWirePayeeByGroup")) {
/*  699: 692 */           return getWirePayeeByGroup(localInputStream, paramResponseHandler);
/*  700:     */         }
/*  701:     */       case 1205701447: 
/*  702: 695 */         if (paramString.equals("getBPWFIInfo__CORBA_WStringValue")) {
/*  703: 696 */           return getBPWFIInfo__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  704:     */         }
/*  705:     */       case 1212136597: 
/*  706: 699 */         if (paramString.equals("getWireBatchHistory")) {
/*  707: 700 */           return getWireBatchHistory(localInputStream, paramResponseHandler);
/*  708:     */         }
/*  709:     */       case 1217760942: 
/*  710: 703 */         if (paramString.equals("modifyPaymentBatch")) {
/*  711: 704 */           return modifyPaymentBatch(localInputStream, paramResponseHandler);
/*  712:     */         }
/*  713:     */       case 1221791921: 
/*  714: 707 */         if (paramString.equals("getBankingDaysInRange")) {
/*  715: 708 */           return getBankingDaysInRange(localInputStream, paramResponseHandler);
/*  716:     */         }
/*  717:     */       case 1231853701: 
/*  718: 711 */         if (paramString.equals("depositAmountsForVerify")) {
/*  719: 712 */           return depositAmountsForVerify(localInputStream, paramResponseHandler);
/*  720:     */         }
/*  721:     */       case 1234963572: 
/*  722: 715 */         if (paramString.equals("getCustomerByGroup")) {
/*  723: 716 */           return getCustomerByGroup(localInputStream, paramResponseHandler);
/*  724:     */         }
/*  725:     */       case 1264756395: 
/*  726: 719 */         if (paramString.equals("isIdentical")) {
/*  727: 720 */           return isIdentical(localInputStream, paramResponseHandler);
/*  728:     */         }
/*  729:     */       case 1288741465: 
/*  730: 723 */         if (paramString.equals("getLastPayments")) {
/*  731: 724 */           return getLastPayments(localInputStream, paramResponseHandler);
/*  732:     */         }
/*  733:     */       case 1351102521: 
/*  734: 727 */         if (paramString.equals("modRecTransfer")) {
/*  735: 728 */           return modRecTransfer(localInputStream, paramResponseHandler);
/*  736:     */         }
/*  737:     */       case 1360564525: 
/*  738: 731 */         if (paramString.equals("cancRecWireTransfer")) {
/*  739: 732 */           return cancRecWireTransfer(localInputStream, paramResponseHandler);
/*  740:     */         }
/*  741:     */       case 1427133090: 
/*  742: 735 */         if (paramString.equals("getTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue")) {
/*  743: 736 */           return getTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  744:     */         }
/*  745:     */       case 1469832609: 
/*  746: 739 */         if (paramString.equals("canExtTransferCompany")) {
/*  747: 740 */           return canExtTransferCompany(localInputStream, paramResponseHandler);
/*  748:     */         }
/*  749:     */       case 1473661108: 
/*  750: 743 */         if (paramString.equals("addCustomers")) {
/*  751: 744 */           return addCustomers(localInputStream, paramResponseHandler);
/*  752:     */         }
/*  753:     */       case 1481249289: 
/*  754: 747 */         if (paramString.equals("getTransfersByRecSrvrTId__org_omg_boxedRMI_CORBA_seq1_WStringValue__boolean")) {
/*  755: 748 */           return getTransfersByRecSrvrTId__org_omg_boxedRMI_CORBA_seq1_WStringValue__boolean(localInputStream, paramResponseHandler);
/*  756:     */         }
/*  757:     */       case 1504868287: 
/*  758: 751 */         if (paramString.equals("getRecWireTransferById__CORBA_WStringValue")) {
/*  759: 752 */           return getRecWireTransferById__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  760:     */         }
/*  761:     */       case 1518134952: 
/*  762: 755 */         if (paramString.equals("modWireTransferBatch")) {
/*  763: 756 */           return modWireTransferBatch(localInputStream, paramResponseHandler);
/*  764:     */         }
/*  765:     */       case 1519324534: 
/*  766: 759 */         if (paramString.equals("cancelCCCompanyAcct")) {
/*  767: 760 */           return cancelCCCompanyAcct(localInputStream, paramResponseHandler);
/*  768:     */         }
/*  769:     */       case 1528336543: 
/*  770: 763 */         if (paramString.equals("getCustomerPayees")) {
/*  771: 764 */           return getCustomerPayees(localInputStream, paramResponseHandler);
/*  772:     */         }
/*  773:     */       case 1537622453: 
/*  774: 767 */         if (paramString.equals("cancelTransferBatch")) {
/*  775: 768 */           return cancelTransferBatch(localInputStream, paramResponseHandler);
/*  776:     */         }
/*  777:     */       case 1547700648: 
/*  778: 771 */         if (paramString.equals("getBPWProperty")) {
/*  779: 772 */           return getBPWProperty(localInputStream, paramResponseHandler);
/*  780:     */         }
/*  781:     */       case 1551887381: 
/*  782: 775 */         if (paramString.equals("addPaymentBatch")) {
/*  783: 776 */           return addPaymentBatch(localInputStream, paramResponseHandler);
/*  784:     */         }
/*  785:     */       case 1562053681: 
/*  786: 779 */         if (paramString.equals("addCCEntry")) {
/*  787: 780 */           return addCCEntry(localInputStream, paramResponseHandler);
/*  788:     */         }
/*  789:     */       case 1567025059: 
/*  790: 783 */         if (paramString.equals("addWirePayee__com_ffusion_ffs_bpw_interfaces_WirePayeeInfo")) {
/*  791: 784 */           return addWirePayee__com_ffusion_ffs_bpw_interfaces_WirePayeeInfo(localInputStream, paramResponseHandler);
/*  792:     */         }
/*  793:     */       case 1570002352: 
/*  794: 787 */         if (paramString.equals("_get_wiresConfiguration")) {
/*  795: 788 */           return _get_wiresConfiguration(localInputStream, paramResponseHandler);
/*  796:     */         }
/*  797:     */       case 1583585824: 
/*  798: 791 */         if (paramString.equals("processWireApprovalRslt")) {
/*  799: 792 */           return processWireApprovalRslt(localInputStream, paramResponseHandler);
/*  800:     */         }
/*  801:     */       case 1600784185: 
/*  802: 795 */         if (paramString.equals("activateBPWFI")) {
/*  803: 796 */           return activateBPWFI(localInputStream, paramResponseHandler);
/*  804:     */         }
/*  805:     */       case 1634875000: 
/*  806: 799 */         if (paramString.equals("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long")) {
/*  807: 800 */           return deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long(localInputStream, paramResponseHandler);
/*  808:     */         }
/*  809:     */       case 1644136940: 
/*  810: 803 */         if (paramString.equals("activateExtTransferAcct")) {
/*  811: 804 */           return activateExtTransferAcct(localInputStream, paramResponseHandler);
/*  812:     */         }
/*  813:     */       case 1663703215: 
/*  814: 807 */         if (paramString.equals("getRPPSBillerInfoByFIRPPSId")) {
/*  815: 808 */           return getRPPSBillerInfoByFIRPPSId(localInputStream, paramResponseHandler);
/*  816:     */         }
/*  817:     */       case 1703320910: 
/*  818: 811 */         if (paramString.equals("getWireHistoryByCustomer")) {
/*  819: 812 */           return getWireHistoryByCustomer(localInputStream, paramResponseHandler);
/*  820:     */         }
/*  821:     */       case 1714889163: 
/*  822: 815 */         if (paramString.equals("getTransferBatchById")) {
/*  823: 816 */           return getTransferBatchById(localInputStream, paramResponseHandler);
/*  824:     */         }
/*  825:     */       case 1722662334: 
/*  826: 819 */         if (paramString.equals("delIntermediaryBanksFromBeneficiary")) {
/*  827: 820 */           return delIntermediaryBanksFromBeneficiary(localInputStream, paramResponseHandler);
/*  828:     */         }
/*  829:     */       case 1745027010: 
/*  830: 823 */         if (paramString.equals("addWireTransfers")) {
/*  831: 824 */           return addWireTransfers(localInputStream, paramResponseHandler);
/*  832:     */         }
/*  833:     */       case 1750641240: 
/*  834: 827 */         if (paramString.equals("modBPWFIInfo")) {
/*  835: 828 */           return modBPWFIInfo(localInputStream, paramResponseHandler);
/*  836:     */         }
/*  837:     */       case 1790907241: 
/*  838: 831 */         if (paramString.equals("getRecWireTransferById__CORBA_WStringValue__boolean")) {
/*  839: 832 */           return getRecWireTransferById__CORBA_WStringValue__boolean(localInputStream, paramResponseHandler);
/*  840:     */         }
/*  841:     */       case 1832744479: 
/*  842: 835 */         if (paramString.equals("deleteCustomerPayee")) {
/*  843: 836 */           return deleteCustomerPayee(localInputStream, paramResponseHandler);
/*  844:     */         }
/*  845:     */       case 1842606562: 
/*  846: 839 */         if (paramString.equals("addExtTransferAccount")) {
/*  847: 840 */           return addExtTransferAccount(localInputStream, paramResponseHandler);
/*  848:     */         }
/*  849:     */       case 1917446131: 
/*  850: 843 */         if (paramString.equals("getTransferHistory")) {
/*  851: 844 */           return getTransferHistory(localInputStream, paramResponseHandler);
/*  852:     */         }
/*  853:     */       case 1923392351: 
/*  854: 847 */         if (paramString.equals("getTransferBySrvrTId__CORBA_WStringValue")) {
/*  855: 848 */           return getTransferBySrvrTId__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  856:     */         }
/*  857:     */       case 1940854046: 
/*  858: 851 */         if (paramString.equals("getRecTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue")) {
/*  859: 852 */           return getRecTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  860:     */         }
/*  861:     */       case 1944413392: 
/*  862: 855 */         if (paramString.equals("_get_handle")) {
/*  863: 856 */           return _get_handle(localInputStream, paramResponseHandler);
/*  864:     */         }
/*  865:     */       case 2009554003: 
/*  866: 859 */         if (paramString.equals("modExtTransferCompany")) {
/*  867: 860 */           return modExtTransferCompany(localInputStream, paramResponseHandler);
/*  868:     */         }
/*  869:     */       case 2055304506: 
/*  870: 863 */         if (paramString.equals("addRecTransfer")) {
/*  871: 864 */           return addRecTransfer(localInputStream, paramResponseHandler);
/*  872:     */         }
/*  873:     */       case 2059595210: 
/*  874: 867 */         if (paramString.equals("getRecWireHistoryByCustomer")) {
/*  875: 868 */           return getRecWireHistoryByCustomer(localInputStream, paramResponseHandler);
/*  876:     */         }
/*  877:     */       case 2073789161: 
/*  878: 871 */         if (paramString.equals("addCustomerPayee")) {
/*  879: 872 */           return addCustomerPayee(localInputStream, paramResponseHandler);
/*  880:     */         }
/*  881:     */       case 2075239767: 
/*  882: 875 */         if (paramString.equals("getExtTransferAccount")) {
/*  883: 876 */           return getExtTransferAccount(localInputStream, paramResponseHandler);
/*  884:     */         }
/*  885:     */       case 2084324289: 
/*  886: 879 */         if (paramString.equals("updateCustomerPayee")) {
/*  887: 880 */           return updateCustomerPayee(localInputStream, paramResponseHandler);
/*  888:     */         }
/*  889:     */       case 2143938384: 
/*  890: 883 */         if (paramString.equals("modWireBanks")) {
/*  891: 884 */           return modWireBanks(localInputStream, paramResponseHandler);
/*  892:     */         }
/*  893:     */         break;
/*  894:     */       }
/*  895: 887 */       throw new BAD_OPERATION();
/*  896:     */     }
/*  897:     */     catch (SystemException localSystemException)
/*  898:     */     {
/*  899: 889 */       throw localSystemException;
/*  900:     */     }
/*  901:     */     catch (Throwable localThrowable)
/*  902:     */     {
/*  903: 891 */       throw new UnknownException(localThrowable);
/*  904:     */     }
/*  905:     */   }
/*  906:     */   
/*  907:     */   private org.omg.CORBA.portable.OutputStream _get_EJBHome(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  908:     */     throws Throwable
/*  909:     */   {
/*  910: 896 */     EJBHome localEJBHome = this.target.getEJBHome();
/*  911: 897 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  912: 898 */     Util.writeRemoteObject(localOutputStream, localEJBHome);
/*  913: 899 */     return localOutputStream;
/*  914:     */   }
/*  915:     */   
/*  916:     */   private org.omg.CORBA.portable.OutputStream _get_primaryKey(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  917:     */     throws Throwable
/*  918:     */   {
/*  919: 903 */     java.lang.Object localObject = this.target.getPrimaryKey();
/*  920: 904 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  921: 905 */     Util.writeAny(localOutputStream, localObject);
/*  922: 906 */     return localOutputStream;
/*  923:     */   }
/*  924:     */   
/*  925:     */   private org.omg.CORBA.portable.OutputStream remove(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  926:     */     throws Throwable
/*  927:     */   {
/*  928:     */     try
/*  929:     */     {
/*  930: 911 */       this.target.remove();
/*  931:     */     }
/*  932:     */     catch (RemoveException localRemoveException)
/*  933:     */     {
/*  934: 913 */       String str = "IDL:javax/ejb/RemoveEx:1.0";
/*  935: 914 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  936: 915 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  937: 916 */       localOutputStream1.write_string(str);
/*  938: 917 */       localOutputStream1.write_value(localRemoveException, RemoveException.class);
/*  939: 918 */       return localOutputStream1;
/*  940:     */     }
/*  941: 920 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  942: 921 */     return localOutputStream;
/*  943:     */   }
/*  944:     */   
/*  945:     */   private org.omg.CORBA.portable.OutputStream _get_handle(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  946:     */     throws Throwable
/*  947:     */   {
/*  948: 925 */     Handle localHandle = this.target.getHandle();
/*  949: 926 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  950: 927 */     Util.writeAbstractObject(localOutputStream, localHandle);
/*  951: 928 */     return localOutputStream;
/*  952:     */   }
/*  953:     */   
/*  954:     */   private org.omg.CORBA.portable.OutputStream isIdentical(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  955:     */     throws Throwable
/*  956:     */   {
/*  957: 932 */     EJBObject localEJBObject = (EJBObject)paramInputStream.read_Object(EJBObject.class);
/*  958: 933 */     boolean bool = this.target.isIdentical(localEJBObject);
/*  959: 934 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  960: 935 */     localOutputStream.write_boolean(bool);
/*  961: 936 */     return localOutputStream;
/*  962:     */   }
/*  963:     */   
/*  964:     */   private org.omg.CORBA.portable.OutputStream disconnect(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  965:     */     throws Throwable
/*  966:     */   {
/*  967: 940 */     this.target.disconnect();
/*  968: 941 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  969: 942 */     return localOutputStream;
/*  970:     */   }
/*  971:     */   
/*  972:     */   private org.omg.CORBA.portable.OutputStream addCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  973:     */     throws Throwable
/*  974:     */   {
/*  975: 946 */     CustomerInfo[] arrayOfCustomerInfo = (CustomerInfo[])paramInputStream.read_value(new CustomerInfo[0].getClass());
/*  976: 947 */     int i = this.target.addCustomers(arrayOfCustomerInfo);
/*  977: 948 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  978: 949 */     localOutputStream.write_long(i);
/*  979: 950 */     return localOutputStream;
/*  980:     */   }
/*  981:     */   
/*  982:     */   private org.omg.CORBA.portable.OutputStream modifyCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  983:     */     throws Throwable
/*  984:     */   {
/*  985: 954 */     CustomerInfo[] arrayOfCustomerInfo = (CustomerInfo[])paramInputStream.read_value(new CustomerInfo[0].getClass());
/*  986: 955 */     int i = this.target.modifyCustomers(arrayOfCustomerInfo);
/*  987: 956 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  988: 957 */     localOutputStream.write_long(i);
/*  989: 958 */     return localOutputStream;
/*  990:     */   }
/*  991:     */   
/*  992:     */   private org.omg.CORBA.portable.OutputStream deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  993:     */     throws Throwable
/*  994:     */   {
/*  995: 962 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/*  996: 963 */     int i = this.target.deleteCustomers(arrayOfString);
/*  997: 964 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  998: 965 */     localOutputStream.write_long(i);
/*  999: 966 */     return localOutputStream;
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   private org.omg.CORBA.portable.OutputStream deactivateCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1003:     */     throws Throwable
/* 1004:     */   {
/* 1005: 970 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1006: 971 */     int i = this.target.deactivateCustomers(arrayOfString);
/* 1007: 972 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1008: 973 */     localOutputStream.write_long(i);
/* 1009: 974 */     return localOutputStream;
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   private org.omg.CORBA.portable.OutputStream activateCustomers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1013:     */     throws Throwable
/* 1014:     */   {
/* 1015: 978 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1016: 979 */     int i = this.target.activateCustomers(arrayOfString);
/* 1017: 980 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1018: 981 */     localOutputStream.write_long(i);
/* 1019: 982 */     return localOutputStream;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   private org.omg.CORBA.portable.OutputStream deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1023:     */     throws Throwable
/* 1024:     */   {
/* 1025: 986 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1026: 987 */     int i = paramInputStream.read_long();
/* 1027: 988 */     int j = this.target.deleteCustomers(arrayOfString, i);
/* 1028: 989 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1029: 990 */     localOutputStream.write_long(j);
/* 1030: 991 */     return localOutputStream;
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   private org.omg.CORBA.portable.OutputStream getCustomersInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1034:     */     throws Throwable
/* 1035:     */   {
/* 1036: 995 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1037: 996 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomersInfo(arrayOfString);
/* 1038: 997 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1039: 998 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1040: 999 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/* 1041:1000 */     return localOutputStream;
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   private org.omg.CORBA.portable.OutputStream getCustomerByType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1045:     */     throws Throwable
/* 1046:     */   {
/* 1047:1004 */     String str = (String)paramInputStream.read_value(String.class);
/* 1048:1005 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByType(str);
/* 1049:1006 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1050:1007 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1051:1008 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/* 1052:1009 */     return localOutputStream;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   private org.omg.CORBA.portable.OutputStream getCustomerByFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1056:     */     throws Throwable
/* 1057:     */   {
/* 1058:1013 */     String str = (String)paramInputStream.read_value(String.class);
/* 1059:1014 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByFI(str);
/* 1060:1015 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1061:1016 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1062:1017 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/* 1063:1018 */     return localOutputStream;
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   private org.omg.CORBA.portable.OutputStream getCustomerByCategory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1067:     */     throws Throwable
/* 1068:     */   {
/* 1069:1022 */     String str = (String)paramInputStream.read_value(String.class);
/* 1070:1023 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByCategory(str);
/* 1071:1024 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1072:1025 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1073:1026 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/* 1074:1027 */     return localOutputStream;
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   private org.omg.CORBA.portable.OutputStream getCustomerByGroup(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1078:     */     throws Throwable
/* 1079:     */   {
/* 1080:1031 */     String str = (String)paramInputStream.read_value(String.class);
/* 1081:1032 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByGroup(str);
/* 1082:1033 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1083:1034 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1084:1035 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/* 1085:1036 */     return localOutputStream;
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   private org.omg.CORBA.portable.OutputStream getCustomerByTypeAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1089:     */     throws Throwable
/* 1090:     */   {
/* 1091:1040 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1092:1041 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1093:1042 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByTypeAndFI(str1, str2);
/* 1094:1043 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1095:1044 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1096:1045 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/* 1097:1046 */     return localOutputStream;
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   private org.omg.CORBA.portable.OutputStream getCustomerByCategoryAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1101:     */     throws Throwable
/* 1102:     */   {
/* 1103:1050 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1104:1051 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1105:1052 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByCategoryAndFI(str1, str2);
/* 1106:1053 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1107:1054 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1108:1055 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/* 1109:1056 */     return localOutputStream;
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   private org.omg.CORBA.portable.OutputStream getCustomerByGroupAndFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1113:     */     throws Throwable
/* 1114:     */   {
/* 1115:1060 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1116:1061 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1117:1062 */     CustomerInfo[] arrayOfCustomerInfo = this.target.getCustomerByGroupAndFI(str1, str2);
/* 1118:1063 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1119:1064 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1120:1065 */     localOutputStream.write_value(cast_array(arrayOfCustomerInfo), new CustomerInfo[0].getClass());
/* 1121:1066 */     return localOutputStream;
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   private org.omg.CORBA.portable.OutputStream getRPPSBillerInfoByFIRPPSId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1125:     */     throws Throwable
/* 1126:     */   {
/* 1127:1070 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1128:     */     RPPSBillerInfo[] arrayOfRPPSBillerInfo;
/* 1129:     */     try
/* 1130:     */     {
/* 1131:1073 */       arrayOfRPPSBillerInfo = this.target.getRPPSBillerInfoByFIRPPSId(str1);
/* 1132:     */     }
/* 1133:     */     catch (FFSException localFFSException)
/* 1134:     */     {
/* 1135:1075 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1136:1076 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1137:1077 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1138:1078 */       localOutputStream2.write_string(str2);
/* 1139:1079 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1140:1080 */       return localOutputStream2;
/* 1141:     */     }
/* 1142:1082 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1143:1083 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1144:1084 */     localOutputStream1.write_value(cast_array(arrayOfRPPSBillerInfo), new RPPSBillerInfo[0].getClass());
/* 1145:1085 */     return localOutputStream1;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   private org.omg.CORBA.portable.OutputStream getRPPSBillerInfoByFIAndBillerRPPSId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1149:     */     throws Throwable
/* 1150:     */   {
/* 1151:1089 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1152:1090 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1153:     */     RPPSBillerInfo localRPPSBillerInfo;
/* 1154:     */     try
/* 1155:     */     {
/* 1156:1093 */       localRPPSBillerInfo = this.target.getRPPSBillerInfoByFIAndBillerRPPSId(str1, str2);
/* 1157:     */     }
/* 1158:     */     catch (FFSException localFFSException)
/* 1159:     */     {
/* 1160:1095 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1161:1096 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1162:1097 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1163:1098 */       localOutputStream2.write_string(str3);
/* 1164:1099 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1165:1100 */       return localOutputStream2;
/* 1166:     */     }
/* 1167:1102 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1168:1103 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1169:1104 */     localOutputStream1.write_value(localRPPSBillerInfo, RPPSBillerInfo.class);
/* 1170:1105 */     return localOutputStream1;
/* 1171:     */   }
/* 1172:     */   
/* 1173:     */   private org.omg.CORBA.portable.OutputStream addWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1174:     */     throws Throwable
/* 1175:     */   {
/* 1176:1109 */     WireInfo localWireInfo1 = (WireInfo)paramInputStream.read_value(WireInfo.class);
/* 1177:1110 */     WireInfo localWireInfo2 = this.target.addWireTransfer(localWireInfo1);
/* 1178:1111 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1179:1112 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1180:1113 */     localOutputStream.write_value(localWireInfo2, WireInfo.class);
/* 1181:1114 */     return localOutputStream;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   private org.omg.CORBA.portable.OutputStream modWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1185:     */     throws Throwable
/* 1186:     */   {
/* 1187:1118 */     WireInfo localWireInfo1 = (WireInfo)paramInputStream.read_value(WireInfo.class);
/* 1188:1119 */     WireInfo localWireInfo2 = this.target.modWireTransfer(localWireInfo1);
/* 1189:1120 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1190:1121 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1191:1122 */     localOutputStream.write_value(localWireInfo2, WireInfo.class);
/* 1192:1123 */     return localOutputStream;
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   private org.omg.CORBA.portable.OutputStream cancWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1196:     */     throws Throwable
/* 1197:     */   {
/* 1198:1127 */     WireInfo localWireInfo1 = (WireInfo)paramInputStream.read_value(WireInfo.class);
/* 1199:1128 */     WireInfo localWireInfo2 = this.target.cancWireTransfer(localWireInfo1);
/* 1200:1129 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1201:1130 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1202:1131 */     localOutputStream.write_value(localWireInfo2, WireInfo.class);
/* 1203:1132 */     return localOutputStream;
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   private org.omg.CORBA.portable.OutputStream getWireTransferById(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1207:     */     throws Throwable
/* 1208:     */   {
/* 1209:1136 */     String str = (String)paramInputStream.read_value(String.class);
/* 1210:1137 */     WireInfo localWireInfo = this.target.getWireTransferById(str);
/* 1211:1138 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1212:1139 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1213:1140 */     localOutputStream.write_value(localWireInfo, WireInfo.class);
/* 1214:1141 */     return localOutputStream;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   private org.omg.CORBA.portable.OutputStream getWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1218:     */     throws Throwable
/* 1219:     */   {
/* 1220:1145 */     String str = (String)paramInputStream.read_value(String.class);
/* 1221:1146 */     WireInfo localWireInfo = this.target.getWireTransfer(str);
/* 1222:1147 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1223:1148 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1224:1149 */     localOutputStream.write_value(localWireInfo, WireInfo.class);
/* 1225:1150 */     return localOutputStream;
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   private org.omg.CORBA.portable.OutputStream getWireTransfers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1229:     */     throws Throwable
/* 1230:     */   {
/* 1231:1154 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1232:1155 */     WireInfo[] arrayOfWireInfo = this.target.getWireTransfers(arrayOfString);
/* 1233:1156 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1234:1157 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1235:1158 */     localOutputStream.write_value(cast_array(arrayOfWireInfo), new WireInfo[0].getClass());
/* 1236:1159 */     return localOutputStream;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   private org.omg.CORBA.portable.OutputStream getDuplicateWires(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1240:     */     throws Throwable
/* 1241:     */   {
/* 1242:1163 */     WireInfo localWireInfo = (WireInfo)paramInputStream.read_value(WireInfo.class);
/* 1243:1164 */     BPWHist localBPWHist = this.target.getDuplicateWires(localWireInfo);
/* 1244:1165 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1245:1166 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1246:1167 */     localOutputStream.write_value(localBPWHist, BPWHist.class);
/* 1247:1168 */     return localOutputStream;
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   private org.omg.CORBA.portable.OutputStream getBatchWires(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1251:     */     throws Throwable
/* 1252:     */   {
/* 1253:1172 */     String str = (String)paramInputStream.read_value(String.class);
/* 1254:1173 */     WireInfo[] arrayOfWireInfo = this.target.getBatchWires(str);
/* 1255:1174 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1256:1175 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1257:1176 */     localOutputStream.write_value(cast_array(arrayOfWireInfo), new WireInfo[0].getClass());
/* 1258:1177 */     return localOutputStream;
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   private org.omg.CORBA.portable.OutputStream getWireHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1262:     */     throws Throwable
/* 1263:     */   {
/* 1264:1181 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1265:1182 */     BPWHist localBPWHist2 = this.target.getWireHistory(localBPWHist1);
/* 1266:1183 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1267:1184 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1268:1185 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1269:1186 */     return localOutputStream;
/* 1270:     */   }
/* 1271:     */   
/* 1272:     */   private org.omg.CORBA.portable.OutputStream getWireHistoryByCustomer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1273:     */     throws Throwable
/* 1274:     */   {
/* 1275:1190 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1276:1191 */     BPWHist localBPWHist2 = this.target.getWireHistoryByCustomer(localBPWHist1);
/* 1277:1192 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1278:1193 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1279:1194 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1280:1195 */     return localOutputStream;
/* 1281:     */   }
/* 1282:     */   
/* 1283:     */   private org.omg.CORBA.portable.OutputStream getCombinedWireHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1284:     */     throws Throwable
/* 1285:     */   {
/* 1286:1199 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1287:1200 */     BPWHist localBPWHist2 = this.target.getCombinedWireHistory(localBPWHist1);
/* 1288:1201 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1289:1202 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1290:1203 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1291:1204 */     return localOutputStream;
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   private org.omg.CORBA.portable.OutputStream getAuditWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1295:     */     throws Throwable
/* 1296:     */   {
/* 1297:1208 */     String str = (String)paramInputStream.read_value(String.class);
/* 1298:1209 */     WireInfo[] arrayOfWireInfo = this.target.getAuditWireTransfer(str);
/* 1299:1210 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1300:1211 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1301:1212 */     localOutputStream.write_value(cast_array(arrayOfWireInfo), new WireInfo[0].getClass());
/* 1302:1213 */     return localOutputStream;
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   private org.omg.CORBA.portable.OutputStream getAuditWireTransferByExtId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1306:     */     throws Throwable
/* 1307:     */   {
/* 1308:1217 */     String str = (String)paramInputStream.read_value(String.class);
/* 1309:1218 */     WireInfo[] arrayOfWireInfo = this.target.getAuditWireTransferByExtId(str);
/* 1310:1219 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1311:1220 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1312:1221 */     localOutputStream.write_value(cast_array(arrayOfWireInfo), new WireInfo[0].getClass());
/* 1313:1222 */     return localOutputStream;
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   private org.omg.CORBA.portable.OutputStream getWireReleaseCount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1317:     */     throws Throwable
/* 1318:     */   {
/* 1319:1226 */     WireReleaseInfo localWireReleaseInfo1 = (WireReleaseInfo)paramInputStream.read_value(WireReleaseInfo.class);
/* 1320:1227 */     WireReleaseInfo localWireReleaseInfo2 = this.target.getWireReleaseCount(localWireReleaseInfo1);
/* 1321:1228 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1322:1229 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1323:1230 */     localOutputStream.write_value(localWireReleaseInfo2, WireReleaseInfo.class);
/* 1324:1231 */     return localOutputStream;
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   private org.omg.CORBA.portable.OutputStream addWireTransfers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1328:     */     throws Throwable
/* 1329:     */   {
/* 1330:1235 */     WireInfo[] arrayOfWireInfo1 = (WireInfo[])paramInputStream.read_value(new WireInfo[0].getClass());
/* 1331:1236 */     WireInfo[] arrayOfWireInfo2 = this.target.addWireTransfers(arrayOfWireInfo1);
/* 1332:1237 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1333:1238 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1334:1239 */     localOutputStream.write_value(cast_array(arrayOfWireInfo2), new WireInfo[0].getClass());
/* 1335:1240 */     return localOutputStream;
/* 1336:     */   }
/* 1337:     */   
/* 1338:     */   private org.omg.CORBA.portable.OutputStream releaseWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1339:     */     throws Throwable
/* 1340:     */   {
/* 1341:1244 */     WireInfo[] arrayOfWireInfo1 = (WireInfo[])paramInputStream.read_value(new WireInfo[0].getClass());
/* 1342:1245 */     WireInfo[] arrayOfWireInfo2 = this.target.releaseWireTransfer(arrayOfWireInfo1);
/* 1343:1246 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1344:1247 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1345:1248 */     localOutputStream.write_value(cast_array(arrayOfWireInfo2), new WireInfo[0].getClass());
/* 1346:1249 */     return localOutputStream;
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   private org.omg.CORBA.portable.OutputStream addRecWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1350:     */     throws Throwable
/* 1351:     */   {
/* 1352:1253 */     RecWireInfo localRecWireInfo1 = (RecWireInfo)paramInputStream.read_value(RecWireInfo.class);
/* 1353:1254 */     RecWireInfo localRecWireInfo2 = this.target.addRecWireTransfer(localRecWireInfo1);
/* 1354:1255 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1355:1256 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1356:1257 */     localOutputStream.write_value(localRecWireInfo2, RecWireInfo.class);
/* 1357:1258 */     return localOutputStream;
/* 1358:     */   }
/* 1359:     */   
/* 1360:     */   private org.omg.CORBA.portable.OutputStream modRecWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1361:     */     throws Throwable
/* 1362:     */   {
/* 1363:1262 */     RecWireInfo localRecWireInfo1 = (RecWireInfo)paramInputStream.read_value(RecWireInfo.class);
/* 1364:1263 */     RecWireInfo localRecWireInfo2 = this.target.modRecWireTransfer(localRecWireInfo1);
/* 1365:1264 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1366:1265 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1367:1266 */     localOutputStream.write_value(localRecWireInfo2, RecWireInfo.class);
/* 1368:1267 */     return localOutputStream;
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   private org.omg.CORBA.portable.OutputStream cancRecWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1372:     */     throws Throwable
/* 1373:     */   {
/* 1374:1271 */     RecWireInfo localRecWireInfo1 = (RecWireInfo)paramInputStream.read_value(RecWireInfo.class);
/* 1375:1272 */     RecWireInfo localRecWireInfo2 = this.target.cancRecWireTransfer(localRecWireInfo1);
/* 1376:1273 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1377:1274 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1378:1275 */     localOutputStream.write_value(localRecWireInfo2, RecWireInfo.class);
/* 1379:1276 */     return localOutputStream;
/* 1380:     */   }
/* 1381:     */   
/* 1382:     */   private org.omg.CORBA.portable.OutputStream getRecWireTransferById__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1383:     */     throws Throwable
/* 1384:     */   {
/* 1385:1280 */     String str = (String)paramInputStream.read_value(String.class);
/* 1386:1281 */     RecWireInfo localRecWireInfo = this.target.getRecWireTransferById(str);
/* 1387:1282 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1388:1283 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1389:1284 */     localOutputStream.write_value(localRecWireInfo, RecWireInfo.class);
/* 1390:1285 */     return localOutputStream;
/* 1391:     */   }
/* 1392:     */   
/* 1393:     */   private org.omg.CORBA.portable.OutputStream getRecWireTransferById__CORBA_WStringValue__boolean(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1394:     */     throws Throwable
/* 1395:     */   {
/* 1396:1289 */     String str = (String)paramInputStream.read_value(String.class);
/* 1397:1290 */     boolean bool = paramInputStream.read_boolean();
/* 1398:1291 */     RecWireInfo localRecWireInfo = this.target.getRecWireTransferById(str, bool);
/* 1399:1292 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1400:1293 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1401:1294 */     localOutputStream.write_value(localRecWireInfo, RecWireInfo.class);
/* 1402:1295 */     return localOutputStream;
/* 1403:     */   }
/* 1404:     */   
/* 1405:     */   private org.omg.CORBA.portable.OutputStream getRecWireTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1406:     */     throws Throwable
/* 1407:     */   {
/* 1408:1299 */     String str = (String)paramInputStream.read_value(String.class);
/* 1409:1300 */     RecWireInfo localRecWireInfo = this.target.getRecWireTransfer(str);
/* 1410:1301 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1411:1302 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1412:1303 */     localOutputStream.write_value(localRecWireInfo, RecWireInfo.class);
/* 1413:1304 */     return localOutputStream;
/* 1414:     */   }
/* 1415:     */   
/* 1416:     */   private org.omg.CORBA.portable.OutputStream getRecWireTransfers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1417:     */     throws Throwable
/* 1418:     */   {
/* 1419:1308 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1420:1309 */     RecWireInfo[] arrayOfRecWireInfo = this.target.getRecWireTransfers(arrayOfString);
/* 1421:1310 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1422:1311 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1423:1312 */     localOutputStream.write_value(cast_array(arrayOfRecWireInfo), new RecWireInfo[0].getClass());
/* 1424:1313 */     return localOutputStream;
/* 1425:     */   }
/* 1426:     */   
/* 1427:     */   private org.omg.CORBA.portable.OutputStream getRecWireHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1428:     */     throws Throwable
/* 1429:     */   {
/* 1430:1317 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1431:1318 */     BPWHist localBPWHist2 = this.target.getRecWireHistory(localBPWHist1);
/* 1432:1319 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1433:1320 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1434:1321 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1435:1322 */     return localOutputStream;
/* 1436:     */   }
/* 1437:     */   
/* 1438:     */   private org.omg.CORBA.portable.OutputStream getRecWireHistoryByCustomer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1439:     */     throws Throwable
/* 1440:     */   {
/* 1441:1326 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1442:1327 */     BPWHist localBPWHist2 = this.target.getRecWireHistoryByCustomer(localBPWHist1);
/* 1443:1328 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1444:1329 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1445:1330 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1446:1331 */     return localOutputStream;
/* 1447:     */   }
/* 1448:     */   
/* 1449:     */   private org.omg.CORBA.portable.OutputStream addRecWireTransfers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1450:     */     throws Throwable
/* 1451:     */   {
/* 1452:1335 */     RecWireInfo[] arrayOfRecWireInfo1 = (RecWireInfo[])paramInputStream.read_value(new RecWireInfo[0].getClass());
/* 1453:1336 */     RecWireInfo[] arrayOfRecWireInfo2 = this.target.addRecWireTransfers(arrayOfRecWireInfo1);
/* 1454:1337 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1455:1338 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1456:1339 */     localOutputStream.write_value(cast_array(arrayOfRecWireInfo2), new RecWireInfo[0].getClass());
/* 1457:1340 */     return localOutputStream;
/* 1458:     */   }
/* 1459:     */   
/* 1460:     */   private org.omg.CORBA.portable.OutputStream _get_wiresConfiguration(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1461:     */     throws Throwable
/* 1462:     */   {
/* 1463:1344 */     HashMap localHashMap = this.target.getWiresConfiguration();
/* 1464:1345 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1465:1346 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1466:1347 */     localOutputStream.write_value(localHashMap, HashMap.class);
/* 1467:1348 */     return localOutputStream;
/* 1468:     */   }
/* 1469:     */   
/* 1470:     */   private org.omg.CORBA.portable.OutputStream addWireTransferBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1471:     */     throws Throwable
/* 1472:     */   {
/* 1473:1352 */     WireBatchInfo localWireBatchInfo1 = (WireBatchInfo)paramInputStream.read_value(WireBatchInfo.class);
/* 1474:1353 */     WireBatchInfo localWireBatchInfo2 = this.target.addWireTransferBatch(localWireBatchInfo1);
/* 1475:1354 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1476:1355 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1477:1356 */     localOutputStream.write_value(localWireBatchInfo2, WireBatchInfo.class);
/* 1478:1357 */     return localOutputStream;
/* 1479:     */   }
/* 1480:     */   
/* 1481:     */   private org.omg.CORBA.portable.OutputStream modWireTransferBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1482:     */     throws Throwable
/* 1483:     */   {
/* 1484:1361 */     WireBatchInfo localWireBatchInfo1 = (WireBatchInfo)paramInputStream.read_value(WireBatchInfo.class);
/* 1485:1362 */     WireBatchInfo localWireBatchInfo2 = this.target.modWireTransferBatch(localWireBatchInfo1);
/* 1486:1363 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1487:1364 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1488:1365 */     localOutputStream.write_value(localWireBatchInfo2, WireBatchInfo.class);
/* 1489:1366 */     return localOutputStream;
/* 1490:     */   }
/* 1491:     */   
/* 1492:     */   private org.omg.CORBA.portable.OutputStream canWireTransferBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1493:     */     throws Throwable
/* 1494:     */   {
/* 1495:1370 */     WireBatchInfo localWireBatchInfo1 = (WireBatchInfo)paramInputStream.read_value(WireBatchInfo.class);
/* 1496:1371 */     WireBatchInfo localWireBatchInfo2 = this.target.canWireTransferBatch(localWireBatchInfo1);
/* 1497:1372 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1498:1373 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1499:1374 */     localOutputStream.write_value(localWireBatchInfo2, WireBatchInfo.class);
/* 1500:1375 */     return localOutputStream;
/* 1501:     */   }
/* 1502:     */   
/* 1503:     */   private org.omg.CORBA.portable.OutputStream getWireTransferBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1504:     */     throws Throwable
/* 1505:     */   {
/* 1506:1379 */     WireBatchInfo localWireBatchInfo = (WireBatchInfo)paramInputStream.read_value(WireBatchInfo.class);
/* 1507:1380 */     WireBatchInfo[] arrayOfWireBatchInfo = this.target.getWireTransferBatch(localWireBatchInfo);
/* 1508:1381 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1509:1382 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1510:1383 */     localOutputStream.write_value(cast_array(arrayOfWireBatchInfo), new WireBatchInfo[0].getClass());
/* 1511:1384 */     return localOutputStream;
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   private org.omg.CORBA.portable.OutputStream getWireBatchHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1515:     */     throws Throwable
/* 1516:     */   {
/* 1517:1388 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 1518:1389 */     BPWHist localBPWHist2 = this.target.getWireBatchHistory(localBPWHist1);
/* 1519:1390 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1520:1391 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1521:1392 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 1522:1393 */     return localOutputStream;
/* 1523:     */   }
/* 1524:     */   
/* 1525:     */   private org.omg.CORBA.portable.OutputStream isWireBatchDeleteable(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1526:     */     throws Throwable
/* 1527:     */   {
/* 1528:1397 */     String str = (String)paramInputStream.read_value(String.class);
/* 1529:1398 */     boolean bool = this.target.isWireBatchDeleteable(str);
/* 1530:1399 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1531:1400 */     localOutputStream.write_boolean(bool);
/* 1532:1401 */     return localOutputStream;
/* 1533:     */   }
/* 1534:     */   
/* 1535:     */   private org.omg.CORBA.portable.OutputStream addWirePayee__com_ffusion_ffs_bpw_interfaces_WirePayeeInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1536:     */     throws Throwable
/* 1537:     */   {
/* 1538:1405 */     WirePayeeInfo localWirePayeeInfo1 = (WirePayeeInfo)paramInputStream.read_value(WirePayeeInfo.class);
/* 1539:1406 */     WirePayeeInfo localWirePayeeInfo2 = this.target.addWirePayee(localWirePayeeInfo1);
/* 1540:1407 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1541:1408 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1542:1409 */     localOutputStream.write_value(localWirePayeeInfo2, WirePayeeInfo.class);
/* 1543:1410 */     return localOutputStream;
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   private org.omg.CORBA.portable.OutputStream addWirePayee__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_WirePayeeInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1547:     */     throws Throwable
/* 1548:     */   {
/* 1549:1414 */     WirePayeeInfo[] arrayOfWirePayeeInfo1 = (WirePayeeInfo[])paramInputStream.read_value(new WirePayeeInfo[0].getClass());
/* 1550:1415 */     WirePayeeInfo[] arrayOfWirePayeeInfo2 = this.target.addWirePayee(arrayOfWirePayeeInfo1);
/* 1551:1416 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1552:1417 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1553:1418 */     localOutputStream.write_value(cast_array(arrayOfWirePayeeInfo2), new WirePayeeInfo[0].getClass());
/* 1554:1419 */     return localOutputStream;
/* 1555:     */   }
/* 1556:     */   
/* 1557:     */   private org.omg.CORBA.portable.OutputStream modWirePayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1558:     */     throws Throwable
/* 1559:     */   {
/* 1560:1423 */     WirePayeeInfo localWirePayeeInfo1 = (WirePayeeInfo)paramInputStream.read_value(WirePayeeInfo.class);
/* 1561:1424 */     WirePayeeInfo localWirePayeeInfo2 = this.target.modWirePayee(localWirePayeeInfo1);
/* 1562:1425 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1563:1426 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1564:1427 */     localOutputStream.write_value(localWirePayeeInfo2, WirePayeeInfo.class);
/* 1565:1428 */     return localOutputStream;
/* 1566:     */   }
/* 1567:     */   
/* 1568:     */   private org.omg.CORBA.portable.OutputStream canWirePayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1569:     */     throws Throwable
/* 1570:     */   {
/* 1571:1432 */     WirePayeeInfo localWirePayeeInfo1 = (WirePayeeInfo)paramInputStream.read_value(WirePayeeInfo.class);
/* 1572:1433 */     WirePayeeInfo localWirePayeeInfo2 = this.target.canWirePayee(localWirePayeeInfo1);
/* 1573:1434 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1574:1435 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1575:1436 */     localOutputStream.write_value(localWirePayeeInfo2, WirePayeeInfo.class);
/* 1576:1437 */     return localOutputStream;
/* 1577:     */   }
/* 1578:     */   
/* 1579:     */   private org.omg.CORBA.portable.OutputStream getWirePayee__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1580:     */     throws Throwable
/* 1581:     */   {
/* 1582:1441 */     String str = (String)paramInputStream.read_value(String.class);
/* 1583:1442 */     WirePayeeInfo localWirePayeeInfo = this.target.getWirePayee(str);
/* 1584:1443 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1585:1444 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1586:1445 */     localOutputStream.write_value(localWirePayeeInfo, WirePayeeInfo.class);
/* 1587:1446 */     return localOutputStream;
/* 1588:     */   }
/* 1589:     */   
/* 1590:     */   private org.omg.CORBA.portable.OutputStream getWirePayee__com_ffusion_ffs_bpw_interfaces_WirePayeeInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1591:     */     throws Throwable
/* 1592:     */   {
/* 1593:1450 */     WirePayeeInfo localWirePayeeInfo1 = (WirePayeeInfo)paramInputStream.read_value(WirePayeeInfo.class);
/* 1594:1451 */     WirePayeeInfo localWirePayeeInfo2 = this.target.getWirePayee(localWirePayeeInfo1);
/* 1595:1452 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1596:1453 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1597:1454 */     localOutputStream.write_value(localWirePayeeInfo2, WirePayeeInfo.class);
/* 1598:1455 */     return localOutputStream;
/* 1599:     */   }
/* 1600:     */   
/* 1601:     */   private org.omg.CORBA.portable.OutputStream getWirePayeeByType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1602:     */     throws Throwable
/* 1603:     */   {
/* 1604:1459 */     BPWPayeeList localBPWPayeeList1 = (BPWPayeeList)paramInputStream.read_value(BPWPayeeList.class);
/* 1605:1460 */     BPWPayeeList localBPWPayeeList2 = this.target.getWirePayeeByType(localBPWPayeeList1);
/* 1606:1461 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1607:1462 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1608:1463 */     localOutputStream.write_value(localBPWPayeeList2, BPWPayeeList.class);
/* 1609:1464 */     return localOutputStream;
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   private org.omg.CORBA.portable.OutputStream getWirePayeeByStatus(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1613:     */     throws Throwable
/* 1614:     */   {
/* 1615:1468 */     BPWPayeeList localBPWPayeeList1 = (BPWPayeeList)paramInputStream.read_value(BPWPayeeList.class);
/* 1616:1469 */     BPWPayeeList localBPWPayeeList2 = this.target.getWirePayeeByStatus(localBPWPayeeList1);
/* 1617:1470 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1618:1471 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1619:1472 */     localOutputStream.write_value(localBPWPayeeList2, BPWPayeeList.class);
/* 1620:1473 */     return localOutputStream;
/* 1621:     */   }
/* 1622:     */   
/* 1623:     */   private org.omg.CORBA.portable.OutputStream getWirePayeeByGroup(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1624:     */     throws Throwable
/* 1625:     */   {
/* 1626:1477 */     BPWPayeeList localBPWPayeeList1 = (BPWPayeeList)paramInputStream.read_value(BPWPayeeList.class);
/* 1627:1478 */     BPWPayeeList localBPWPayeeList2 = this.target.getWirePayeeByGroup(localBPWPayeeList1);
/* 1628:1479 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1629:1480 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1630:1481 */     localOutputStream.write_value(localBPWPayeeList2, BPWPayeeList.class);
/* 1631:1482 */     return localOutputStream;
/* 1632:     */   }
/* 1633:     */   
/* 1634:     */   private org.omg.CORBA.portable.OutputStream getWirePayeeByCustomer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1635:     */     throws Throwable
/* 1636:     */   {
/* 1637:1486 */     BPWPayeeList localBPWPayeeList1 = (BPWPayeeList)paramInputStream.read_value(BPWPayeeList.class);
/* 1638:1487 */     BPWPayeeList localBPWPayeeList2 = this.target.getWirePayeeByCustomer(localBPWPayeeList1);
/* 1639:1488 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1640:1489 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1641:1490 */     localOutputStream.write_value(localBPWPayeeList2, BPWPayeeList.class);
/* 1642:1491 */     return localOutputStream;
/* 1643:     */   }
/* 1644:     */   
/* 1645:     */   private org.omg.CORBA.portable.OutputStream addIntermediaryBanksToBeneficiary(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1646:     */     throws Throwable
/* 1647:     */   {
/* 1648:1495 */     String str = (String)paramInputStream.read_value(String.class);
/* 1649:1496 */     BPWBankInfo[] arrayOfBPWBankInfo = (BPWBankInfo[])paramInputStream.read_value(new BPWBankInfo[0].getClass());
/* 1650:1497 */     int i = this.target.addIntermediaryBanksToBeneficiary(str, arrayOfBPWBankInfo);
/* 1651:1498 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1652:1499 */     localOutputStream.write_long(i);
/* 1653:1500 */     return localOutputStream;
/* 1654:     */   }
/* 1655:     */   
/* 1656:     */   private org.omg.CORBA.portable.OutputStream delIntermediaryBanksFromBeneficiary(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1657:     */     throws Throwable
/* 1658:     */   {
/* 1659:1504 */     String str = (String)paramInputStream.read_value(String.class);
/* 1660:1505 */     BPWBankInfo[] arrayOfBPWBankInfo = (BPWBankInfo[])paramInputStream.read_value(new BPWBankInfo[0].getClass());
/* 1661:1506 */     int i = this.target.delIntermediaryBanksFromBeneficiary(str, arrayOfBPWBankInfo);
/* 1662:1507 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1663:1508 */     localOutputStream.write_long(i);
/* 1664:1509 */     return localOutputStream;
/* 1665:     */   }
/* 1666:     */   
/* 1667:     */   private org.omg.CORBA.portable.OutputStream addWireBanks(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1668:     */     throws Throwable
/* 1669:     */   {
/* 1670:1513 */     BPWBankInfo[] arrayOfBPWBankInfo1 = (BPWBankInfo[])paramInputStream.read_value(new BPWBankInfo[0].getClass());
/* 1671:1514 */     BPWBankInfo[] arrayOfBPWBankInfo2 = this.target.addWireBanks(arrayOfBPWBankInfo1);
/* 1672:1515 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1673:1516 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1674:1517 */     localOutputStream.write_value(cast_array(arrayOfBPWBankInfo2), new BPWBankInfo[0].getClass());
/* 1675:1518 */     return localOutputStream;
/* 1676:     */   }
/* 1677:     */   
/* 1678:     */   private org.omg.CORBA.portable.OutputStream modWireBanks(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1679:     */     throws Throwable
/* 1680:     */   {
/* 1681:1522 */     BPWBankInfo[] arrayOfBPWBankInfo1 = (BPWBankInfo[])paramInputStream.read_value(new BPWBankInfo[0].getClass());
/* 1682:1523 */     BPWBankInfo[] arrayOfBPWBankInfo2 = this.target.modWireBanks(arrayOfBPWBankInfo1);
/* 1683:1524 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1684:1525 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1685:1526 */     localOutputStream.write_value(cast_array(arrayOfBPWBankInfo2), new BPWBankInfo[0].getClass());
/* 1686:1527 */     return localOutputStream;
/* 1687:     */   }
/* 1688:     */   
/* 1689:     */   private org.omg.CORBA.portable.OutputStream delWireBanks(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1690:     */     throws Throwable
/* 1691:     */   {
/* 1692:1531 */     BPWBankInfo[] arrayOfBPWBankInfo1 = (BPWBankInfo[])paramInputStream.read_value(new BPWBankInfo[0].getClass());
/* 1693:1532 */     BPWBankInfo[] arrayOfBPWBankInfo2 = this.target.delWireBanks(arrayOfBPWBankInfo1);
/* 1694:1533 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1695:1534 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1696:1535 */     localOutputStream.write_value(cast_array(arrayOfBPWBankInfo2), new BPWBankInfo[0].getClass());
/* 1697:1536 */     return localOutputStream;
/* 1698:     */   }
/* 1699:     */   
/* 1700:     */   private org.omg.CORBA.portable.OutputStream getWireBanks(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1701:     */     throws Throwable
/* 1702:     */   {
/* 1703:1540 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1704:1541 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1705:1542 */     String str3 = (String)paramInputStream.read_value(String.class);
/* 1706:1543 */     String str4 = (String)paramInputStream.read_value(String.class);
/* 1707:1544 */     BPWBankInfo[] arrayOfBPWBankInfo = this.target.getWireBanks(str1, str2, str3, str4);
/* 1708:1545 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1709:1546 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1710:1547 */     localOutputStream.write_value(cast_array(arrayOfBPWBankInfo), new BPWBankInfo[0].getClass());
/* 1711:1548 */     return localOutputStream;
/* 1712:     */   }
/* 1713:     */   
/* 1714:     */   private org.omg.CORBA.portable.OutputStream getWireBanksByRTN(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1715:     */     throws Throwable
/* 1716:     */   {
/* 1717:1552 */     String str = (String)paramInputStream.read_value(String.class);
/* 1718:1553 */     BPWBankInfo[] arrayOfBPWBankInfo = this.target.getWireBanksByRTN(str);
/* 1719:1554 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1720:1555 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1721:1556 */     localOutputStream.write_value(cast_array(arrayOfBPWBankInfo), new BPWBankInfo[0].getClass());
/* 1722:1557 */     return localOutputStream;
/* 1723:     */   }
/* 1724:     */   
/* 1725:     */   private org.omg.CORBA.portable.OutputStream getWireBanksByID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1726:     */     throws Throwable
/* 1727:     */   {
/* 1728:1561 */     String str = (String)paramInputStream.read_value(String.class);
/* 1729:1562 */     BPWBankInfo localBPWBankInfo = this.target.getWireBanksByID(str);
/* 1730:1563 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1731:1564 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1732:1565 */     localOutputStream.write_value(localBPWBankInfo, BPWBankInfo.class);
/* 1733:1566 */     return localOutputStream;
/* 1734:     */   }
/* 1735:     */   
/* 1736:     */   private org.omg.CORBA.portable.OutputStream processWireApprovalRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1737:     */     throws Throwable
/* 1738:     */   {
/* 1739:1570 */     WireInfo[] arrayOfWireInfo = (WireInfo[])paramInputStream.read_value(new WireInfo[0].getClass());
/* 1740:1571 */     this.target.processWireApprovalRslt(arrayOfWireInfo);
/* 1741:1572 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1742:1573 */     return localOutputStream;
/* 1743:     */   }
/* 1744:     */   
/* 1745:     */   private org.omg.CORBA.portable.OutputStream processWireBackendlRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1746:     */     throws Throwable
/* 1747:     */   {
/* 1748:1577 */     WireInfo[] arrayOfWireInfo = (WireInfo[])paramInputStream.read_value(new WireInfo[0].getClass());
/* 1749:1578 */     this.target.processWireBackendlRslt(arrayOfWireInfo);
/* 1750:1579 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1751:1580 */     return localOutputStream;
/* 1752:     */   }
/* 1753:     */   
/* 1754:     */   private org.omg.CORBA.portable.OutputStream processWireApprovalRevertRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1755:     */     throws Throwable
/* 1756:     */   {
/* 1757:1584 */     WireInfo[] arrayOfWireInfo = (WireInfo[])paramInputStream.read_value(new WireInfo[0].getClass());
/* 1758:1585 */     this.target.processWireApprovalRevertRslt(arrayOfWireInfo);
/* 1759:1586 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1760:1587 */     return localOutputStream;
/* 1761:     */   }
/* 1762:     */   
/* 1763:     */   private org.omg.CORBA.portable.OutputStream addBPWFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1764:     */     throws Throwable
/* 1765:     */   {
/* 1766:1591 */     BPWFIInfo localBPWFIInfo1 = (BPWFIInfo)paramInputStream.read_value(BPWFIInfo.class);
/* 1767:1592 */     BPWFIInfo localBPWFIInfo2 = this.target.addBPWFIInfo(localBPWFIInfo1);
/* 1768:1593 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1769:1594 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1770:1595 */     localOutputStream.write_value(localBPWFIInfo2, BPWFIInfo.class);
/* 1771:1596 */     return localOutputStream;
/* 1772:     */   }
/* 1773:     */   
/* 1774:     */   private org.omg.CORBA.portable.OutputStream modBPWFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1775:     */     throws Throwable
/* 1776:     */   {
/* 1777:1600 */     BPWFIInfo localBPWFIInfo1 = (BPWFIInfo)paramInputStream.read_value(BPWFIInfo.class);
/* 1778:1601 */     BPWFIInfo localBPWFIInfo2 = this.target.modBPWFIInfo(localBPWFIInfo1);
/* 1779:1602 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1780:1603 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1781:1604 */     localOutputStream.write_value(localBPWFIInfo2, BPWFIInfo.class);
/* 1782:1605 */     return localOutputStream;
/* 1783:     */   }
/* 1784:     */   
/* 1785:     */   private org.omg.CORBA.portable.OutputStream canBPWFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1786:     */     throws Throwable
/* 1787:     */   {
/* 1788:1609 */     BPWFIInfo localBPWFIInfo1 = (BPWFIInfo)paramInputStream.read_value(BPWFIInfo.class);
/* 1789:1610 */     BPWFIInfo localBPWFIInfo2 = this.target.canBPWFIInfo(localBPWFIInfo1);
/* 1790:1611 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1791:1612 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1792:1613 */     localOutputStream.write_value(localBPWFIInfo2, BPWFIInfo.class);
/* 1793:1614 */     return localOutputStream;
/* 1794:     */   }
/* 1795:     */   
/* 1796:     */   private org.omg.CORBA.portable.OutputStream activateBPWFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1797:     */     throws Throwable
/* 1798:     */   {
/* 1799:1618 */     String str = (String)paramInputStream.read_value(String.class);
/* 1800:1619 */     BPWFIInfo localBPWFIInfo = this.target.activateBPWFI(str);
/* 1801:1620 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1802:1621 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1803:1622 */     localOutputStream.write_value(localBPWFIInfo, BPWFIInfo.class);
/* 1804:1623 */     return localOutputStream;
/* 1805:     */   }
/* 1806:     */   
/* 1807:     */   private org.omg.CORBA.portable.OutputStream getBPWFIInfo__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1808:     */     throws Throwable
/* 1809:     */   {
/* 1810:1627 */     String str = (String)paramInputStream.read_value(String.class);
/* 1811:1628 */     BPWFIInfo localBPWFIInfo = this.target.getBPWFIInfo(str);
/* 1812:1629 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1813:1630 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1814:1631 */     localOutputStream.write_value(localBPWFIInfo, BPWFIInfo.class);
/* 1815:1632 */     return localOutputStream;
/* 1816:     */   }
/* 1817:     */   
/* 1818:     */   private org.omg.CORBA.portable.OutputStream getBPWFIInfo__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1819:     */     throws Throwable
/* 1820:     */   {
/* 1821:1636 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 1822:1637 */     BPWFIInfo[] arrayOfBPWFIInfo = this.target.getBPWFIInfo(arrayOfString);
/* 1823:1638 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1824:1639 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1825:1640 */     localOutputStream.write_value(cast_array(arrayOfBPWFIInfo), new BPWFIInfo[0].getClass());
/* 1826:1641 */     return localOutputStream;
/* 1827:     */   }
/* 1828:     */   
/* 1829:     */   private org.omg.CORBA.portable.OutputStream getBPWFIInfoByType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1830:     */     throws Throwable
/* 1831:     */   {
/* 1832:1645 */     String str = (String)paramInputStream.read_value(String.class);
/* 1833:1646 */     BPWFIInfo[] arrayOfBPWFIInfo = this.target.getBPWFIInfoByType(str);
/* 1834:1647 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1835:1648 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1836:1649 */     localOutputStream.write_value(cast_array(arrayOfBPWFIInfo), new BPWFIInfo[0].getClass());
/* 1837:1650 */     return localOutputStream;
/* 1838:     */   }
/* 1839:     */   
/* 1840:     */   private org.omg.CORBA.portable.OutputStream getBPWFIInfoByStatus(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1841:     */     throws Throwable
/* 1842:     */   {
/* 1843:1654 */     String str = (String)paramInputStream.read_value(String.class);
/* 1844:1655 */     BPWFIInfo[] arrayOfBPWFIInfo = this.target.getBPWFIInfoByStatus(str);
/* 1845:1656 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1846:1657 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1847:1658 */     localOutputStream.write_value(cast_array(arrayOfBPWFIInfo), new BPWFIInfo[0].getClass());
/* 1848:1659 */     return localOutputStream;
/* 1849:     */   }
/* 1850:     */   
/* 1851:     */   private org.omg.CORBA.portable.OutputStream getBPWFIInfoByGroup(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1852:     */     throws Throwable
/* 1853:     */   {
/* 1854:1663 */     String str = (String)paramInputStream.read_value(String.class);
/* 1855:1664 */     BPWFIInfo[] arrayOfBPWFIInfo = this.target.getBPWFIInfoByGroup(str);
/* 1856:1665 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1857:1666 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1858:1667 */     localOutputStream.write_value(cast_array(arrayOfBPWFIInfo), new BPWFIInfo[0].getClass());
/* 1859:1668 */     return localOutputStream;
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   private org.omg.CORBA.portable.OutputStream getBPWFIInfoByACHId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1863:     */     throws Throwable
/* 1864:     */   {
/* 1865:1672 */     String str = (String)paramInputStream.read_value(String.class);
/* 1866:1673 */     BPWFIInfo localBPWFIInfo = this.target.getBPWFIInfoByACHId(str);
/* 1867:1674 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1868:1675 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1869:1676 */     localOutputStream.write_value(localBPWFIInfo, BPWFIInfo.class);
/* 1870:1677 */     return localOutputStream;
/* 1871:     */   }
/* 1872:     */   
/* 1873:     */   private org.omg.CORBA.portable.OutputStream getBPWFIInfoByRTN(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1874:     */     throws Throwable
/* 1875:     */   {
/* 1876:1681 */     String str = (String)paramInputStream.read_value(String.class);
/* 1877:1682 */     BPWFIInfo localBPWFIInfo = this.target.getBPWFIInfoByRTN(str);
/* 1878:1683 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 1879:1684 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1880:1685 */     localOutputStream.write_value(localBPWFIInfo, BPWFIInfo.class);
/* 1881:1686 */     return localOutputStream;
/* 1882:     */   }
/* 1883:     */   
/* 1884:     */   private org.omg.CORBA.portable.OutputStream addRPPSFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1885:     */     throws Throwable
/* 1886:     */   {
/* 1887:1690 */     RPPSFIInfo localRPPSFIInfo1 = (RPPSFIInfo)paramInputStream.read_value(RPPSFIInfo.class);
/* 1888:     */     RPPSFIInfo localRPPSFIInfo2;
/* 1889:     */     try
/* 1890:     */     {
/* 1891:1693 */       localRPPSFIInfo2 = this.target.addRPPSFIInfo(localRPPSFIInfo1);
/* 1892:     */     }
/* 1893:     */     catch (FFSException localFFSException)
/* 1894:     */     {
/* 1895:1695 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1896:1696 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1897:1697 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1898:1698 */       localOutputStream2.write_string(str);
/* 1899:1699 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1900:1700 */       return localOutputStream2;
/* 1901:     */     }
/* 1902:1702 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1903:1703 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1904:1704 */     localOutputStream1.write_value(localRPPSFIInfo2, RPPSFIInfo.class);
/* 1905:1705 */     return localOutputStream1;
/* 1906:     */   }
/* 1907:     */   
/* 1908:     */   private org.omg.CORBA.portable.OutputStream getRPPSFIInfoByFIId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1909:     */     throws Throwable
/* 1910:     */   {
/* 1911:1709 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1912:     */     RPPSFIInfo localRPPSFIInfo;
/* 1913:     */     try
/* 1914:     */     {
/* 1915:1712 */       localRPPSFIInfo = this.target.getRPPSFIInfoByFIId(str1);
/* 1916:     */     }
/* 1917:     */     catch (FFSException localFFSException)
/* 1918:     */     {
/* 1919:1714 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1920:1715 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1921:1716 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1922:1717 */       localOutputStream2.write_string(str2);
/* 1923:1718 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1924:1719 */       return localOutputStream2;
/* 1925:     */     }
/* 1926:1721 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1927:1722 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1928:1723 */     localOutputStream1.write_value(localRPPSFIInfo, RPPSFIInfo.class);
/* 1929:1724 */     return localOutputStream1;
/* 1930:     */   }
/* 1931:     */   
/* 1932:     */   private org.omg.CORBA.portable.OutputStream getRPPSFIInfoByFIRPPSId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1933:     */     throws Throwable
/* 1934:     */   {
/* 1935:1728 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1936:     */     RPPSFIInfo localRPPSFIInfo;
/* 1937:     */     try
/* 1938:     */     {
/* 1939:1731 */       localRPPSFIInfo = this.target.getRPPSFIInfoByFIRPPSId(str1);
/* 1940:     */     }
/* 1941:     */     catch (FFSException localFFSException)
/* 1942:     */     {
/* 1943:1733 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1944:1734 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1945:1735 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1946:1736 */       localOutputStream2.write_string(str2);
/* 1947:1737 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1948:1738 */       return localOutputStream2;
/* 1949:     */     }
/* 1950:1740 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1951:1741 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1952:1742 */     localOutputStream1.write_value(localRPPSFIInfo, RPPSFIInfo.class);
/* 1953:1743 */     return localOutputStream1;
/* 1954:     */   }
/* 1955:     */   
/* 1956:     */   private org.omg.CORBA.portable.OutputStream canRPPSFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1957:     */     throws Throwable
/* 1958:     */   {
/* 1959:1747 */     RPPSFIInfo localRPPSFIInfo1 = (RPPSFIInfo)paramInputStream.read_value(RPPSFIInfo.class);
/* 1960:     */     RPPSFIInfo localRPPSFIInfo2;
/* 1961:     */     try
/* 1962:     */     {
/* 1963:1750 */       localRPPSFIInfo2 = this.target.canRPPSFIInfo(localRPPSFIInfo1);
/* 1964:     */     }
/* 1965:     */     catch (FFSException localFFSException)
/* 1966:     */     {
/* 1967:1752 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1968:1753 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1969:1754 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1970:1755 */       localOutputStream2.write_string(str);
/* 1971:1756 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1972:1757 */       return localOutputStream2;
/* 1973:     */     }
/* 1974:1759 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1975:1760 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1976:1761 */     localOutputStream1.write_value(localRPPSFIInfo2, RPPSFIInfo.class);
/* 1977:1762 */     return localOutputStream1;
/* 1978:     */   }
/* 1979:     */   
/* 1980:     */   private org.omg.CORBA.portable.OutputStream activateRPPSFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1981:     */     throws Throwable
/* 1982:     */   {
/* 1983:1766 */     RPPSFIInfo localRPPSFIInfo1 = (RPPSFIInfo)paramInputStream.read_value(RPPSFIInfo.class);
/* 1984:     */     RPPSFIInfo localRPPSFIInfo2;
/* 1985:     */     try
/* 1986:     */     {
/* 1987:1769 */       localRPPSFIInfo2 = this.target.activateRPPSFI(localRPPSFIInfo1);
/* 1988:     */     }
/* 1989:     */     catch (FFSException localFFSException)
/* 1990:     */     {
/* 1991:1771 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1992:1772 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1993:1773 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1994:1774 */       localOutputStream2.write_string(str);
/* 1995:1775 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1996:1776 */       return localOutputStream2;
/* 1997:     */     }
/* 1998:1778 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1999:1779 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2000:1780 */     localOutputStream1.write_value(localRPPSFIInfo2, RPPSFIInfo.class);
/* 2001:1781 */     return localOutputStream1;
/* 2002:     */   }
/* 2003:     */   
/* 2004:     */   private org.omg.CORBA.portable.OutputStream modRPPSFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2005:     */     throws Throwable
/* 2006:     */   {
/* 2007:1785 */     RPPSFIInfo localRPPSFIInfo1 = (RPPSFIInfo)paramInputStream.read_value(RPPSFIInfo.class);
/* 2008:     */     RPPSFIInfo localRPPSFIInfo2;
/* 2009:     */     try
/* 2010:     */     {
/* 2011:1788 */       localRPPSFIInfo2 = this.target.modRPPSFIInfo(localRPPSFIInfo1);
/* 2012:     */     }
/* 2013:     */     catch (FFSException localFFSException)
/* 2014:     */     {
/* 2015:1790 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2016:1791 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2017:1792 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2018:1793 */       localOutputStream2.write_string(str);
/* 2019:1794 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2020:1795 */       return localOutputStream2;
/* 2021:     */     }
/* 2022:1797 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2023:1798 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2024:1799 */     localOutputStream1.write_value(localRPPSFIInfo2, RPPSFIInfo.class);
/* 2025:1800 */     return localOutputStream1;
/* 2026:     */   }
/* 2027:     */   
/* 2028:     */   private org.omg.CORBA.portable.OutputStream getSmartDate(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2029:     */     throws Throwable
/* 2030:     */   {
/* 2031:1804 */     String str = (String)paramInputStream.read_value(String.class);
/* 2032:1805 */     int i = paramInputStream.read_long();
/* 2033:1806 */     int j = this.target.getSmartDate(str, i);
/* 2034:1807 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 2035:1808 */     localOutputStream.write_long(j);
/* 2036:1809 */     return localOutputStream;
/* 2037:     */   }
/* 2038:     */   
/* 2039:     */   private org.omg.CORBA.portable.OutputStream processApprovalResult(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2040:     */     throws Throwable
/* 2041:     */   {
/* 2042:1813 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 2043:1814 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 2044:1815 */     String str3 = (String)paramInputStream.read_value(String.class);
/* 2045:1816 */     HashMap localHashMap = (HashMap)paramInputStream.read_value(HashMap.class);
/* 2046:     */     try
/* 2047:     */     {
/* 2048:1818 */       this.target.processApprovalResult(str1, str2, str3, localHashMap);
/* 2049:     */     }
/* 2050:     */     catch (FFSException localFFSException)
/* 2051:     */     {
/* 2052:1820 */       String str4 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2053:1821 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2054:1822 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2055:1823 */       localOutputStream1.write_string(str4);
/* 2056:1824 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 2057:1825 */       return localOutputStream1;
/* 2058:     */     }
/* 2059:1827 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 2060:1828 */     return localOutputStream;
/* 2061:     */   }
/* 2062:     */   
/* 2063:     */   private org.omg.CORBA.portable.OutputStream addCCCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2064:     */     throws Throwable
/* 2065:     */   {
/* 2066:1832 */     CCCompanyInfo localCCCompanyInfo1 = (CCCompanyInfo)paramInputStream.read_value(CCCompanyInfo.class);
/* 2067:     */     CCCompanyInfo localCCCompanyInfo2;
/* 2068:     */     try
/* 2069:     */     {
/* 2070:1835 */       localCCCompanyInfo2 = this.target.addCCCompany(localCCCompanyInfo1);
/* 2071:     */     }
/* 2072:     */     catch (FFSException localFFSException)
/* 2073:     */     {
/* 2074:1837 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2075:1838 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2076:1839 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2077:1840 */       localOutputStream2.write_string(str);
/* 2078:1841 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2079:1842 */       return localOutputStream2;
/* 2080:     */     }
/* 2081:1844 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2082:1845 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2083:1846 */     localOutputStream1.write_value(localCCCompanyInfo2, CCCompanyInfo.class);
/* 2084:1847 */     return localOutputStream1;
/* 2085:     */   }
/* 2086:     */   
/* 2087:     */   private org.omg.CORBA.portable.OutputStream cancelCCCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2088:     */     throws Throwable
/* 2089:     */   {
/* 2090:1851 */     CCCompanyInfo localCCCompanyInfo1 = (CCCompanyInfo)paramInputStream.read_value(CCCompanyInfo.class);
/* 2091:     */     CCCompanyInfo localCCCompanyInfo2;
/* 2092:     */     try
/* 2093:     */     {
/* 2094:1854 */       localCCCompanyInfo2 = this.target.cancelCCCompany(localCCCompanyInfo1);
/* 2095:     */     }
/* 2096:     */     catch (FFSException localFFSException)
/* 2097:     */     {
/* 2098:1856 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2099:1857 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2100:1858 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2101:1859 */       localOutputStream2.write_string(str);
/* 2102:1860 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2103:1861 */       return localOutputStream2;
/* 2104:     */     }
/* 2105:1863 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2106:1864 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2107:1865 */     localOutputStream1.write_value(localCCCompanyInfo2, CCCompanyInfo.class);
/* 2108:1866 */     return localOutputStream1;
/* 2109:     */   }
/* 2110:     */   
/* 2111:     */   private org.omg.CORBA.portable.OutputStream modCCCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2112:     */     throws Throwable
/* 2113:     */   {
/* 2114:1870 */     CCCompanyInfo localCCCompanyInfo1 = (CCCompanyInfo)paramInputStream.read_value(CCCompanyInfo.class);
/* 2115:     */     CCCompanyInfo localCCCompanyInfo2;
/* 2116:     */     try
/* 2117:     */     {
/* 2118:1873 */       localCCCompanyInfo2 = this.target.modCCCompany(localCCCompanyInfo1);
/* 2119:     */     }
/* 2120:     */     catch (FFSException localFFSException)
/* 2121:     */     {
/* 2122:1875 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2123:1876 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2124:1877 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2125:1878 */       localOutputStream2.write_string(str);
/* 2126:1879 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2127:1880 */       return localOutputStream2;
/* 2128:     */     }
/* 2129:1882 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2130:1883 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2131:1884 */     localOutputStream1.write_value(localCCCompanyInfo2, CCCompanyInfo.class);
/* 2132:1885 */     return localOutputStream1;
/* 2133:     */   }
/* 2134:     */   
/* 2135:     */   private org.omg.CORBA.portable.OutputStream getCCCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2136:     */     throws Throwable
/* 2137:     */   {
/* 2138:1889 */     CCCompanyInfo localCCCompanyInfo1 = (CCCompanyInfo)paramInputStream.read_value(CCCompanyInfo.class);
/* 2139:     */     CCCompanyInfo localCCCompanyInfo2;
/* 2140:     */     try
/* 2141:     */     {
/* 2142:1892 */       localCCCompanyInfo2 = this.target.getCCCompany(localCCCompanyInfo1);
/* 2143:     */     }
/* 2144:     */     catch (FFSException localFFSException)
/* 2145:     */     {
/* 2146:1894 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2147:1895 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2148:1896 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2149:1897 */       localOutputStream2.write_string(str);
/* 2150:1898 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2151:1899 */       return localOutputStream2;
/* 2152:     */     }
/* 2153:1901 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2154:1902 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2155:1903 */     localOutputStream1.write_value(localCCCompanyInfo2, CCCompanyInfo.class);
/* 2156:1904 */     return localOutputStream1;
/* 2157:     */   }
/* 2158:     */   
/* 2159:     */   private org.omg.CORBA.portable.OutputStream getCCCompanyList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2160:     */     throws Throwable
/* 2161:     */   {
/* 2162:1908 */     CCCompanyInfoList localCCCompanyInfoList1 = (CCCompanyInfoList)paramInputStream.read_value(CCCompanyInfoList.class);
/* 2163:     */     CCCompanyInfoList localCCCompanyInfoList2;
/* 2164:     */     try
/* 2165:     */     {
/* 2166:1911 */       localCCCompanyInfoList2 = this.target.getCCCompanyList(localCCCompanyInfoList1);
/* 2167:     */     }
/* 2168:     */     catch (FFSException localFFSException)
/* 2169:     */     {
/* 2170:1913 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2171:1914 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2172:1915 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2173:1916 */       localOutputStream2.write_string(str);
/* 2174:1917 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2175:1918 */       return localOutputStream2;
/* 2176:     */     }
/* 2177:1920 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2178:1921 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2179:1922 */     localOutputStream1.write_value(localCCCompanyInfoList2, CCCompanyInfoList.class);
/* 2180:1923 */     return localOutputStream1;
/* 2181:     */   }
/* 2182:     */   
/* 2183:     */   private org.omg.CORBA.portable.OutputStream getNextCashConCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2184:     */     throws Throwable
/* 2185:     */   {
/* 2186:1927 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 2187:1928 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 2188:     */     CutOffInfo localCutOffInfo;
/* 2189:     */     try
/* 2190:     */     {
/* 2191:1931 */       localCutOffInfo = this.target.getNextCashConCutOff(str1, str2);
/* 2192:     */     }
/* 2193:     */     catch (FFSException localFFSException)
/* 2194:     */     {
/* 2195:1933 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2196:1934 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2197:1935 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2198:1936 */       localOutputStream2.write_string(str3);
/* 2199:1937 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2200:1938 */       return localOutputStream2;
/* 2201:     */     }
/* 2202:1940 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2203:1941 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2204:1942 */     localOutputStream1.write_value(localCutOffInfo, CutOffInfo.class);
/* 2205:1943 */     return localOutputStream1;
/* 2206:     */   }
/* 2207:     */   
/* 2208:     */   private org.omg.CORBA.portable.OutputStream addCCCompanyAcct(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2209:     */     throws Throwable
/* 2210:     */   {
/* 2211:1947 */     CCCompanyAcctInfo localCCCompanyAcctInfo1 = (CCCompanyAcctInfo)paramInputStream.read_value(CCCompanyAcctInfo.class);
/* 2212:     */     CCCompanyAcctInfo localCCCompanyAcctInfo2;
/* 2213:     */     try
/* 2214:     */     {
/* 2215:1950 */       localCCCompanyAcctInfo2 = this.target.addCCCompanyAcct(localCCCompanyAcctInfo1);
/* 2216:     */     }
/* 2217:     */     catch (FFSException localFFSException)
/* 2218:     */     {
/* 2219:1952 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2220:1953 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2221:1954 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2222:1955 */       localOutputStream2.write_string(str);
/* 2223:1956 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2224:1957 */       return localOutputStream2;
/* 2225:     */     }
/* 2226:1959 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2227:1960 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2228:1961 */     localOutputStream1.write_value(localCCCompanyAcctInfo2, CCCompanyAcctInfo.class);
/* 2229:1962 */     return localOutputStream1;
/* 2230:     */   }
/* 2231:     */   
/* 2232:     */   private org.omg.CORBA.portable.OutputStream cancelCCCompanyAcct(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2233:     */     throws Throwable
/* 2234:     */   {
/* 2235:1966 */     CCCompanyAcctInfo localCCCompanyAcctInfo1 = (CCCompanyAcctInfo)paramInputStream.read_value(CCCompanyAcctInfo.class);
/* 2236:     */     CCCompanyAcctInfo localCCCompanyAcctInfo2;
/* 2237:     */     try
/* 2238:     */     {
/* 2239:1969 */       localCCCompanyAcctInfo2 = this.target.cancelCCCompanyAcct(localCCCompanyAcctInfo1);
/* 2240:     */     }
/* 2241:     */     catch (FFSException localFFSException)
/* 2242:     */     {
/* 2243:1971 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2244:1972 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2245:1973 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2246:1974 */       localOutputStream2.write_string(str);
/* 2247:1975 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2248:1976 */       return localOutputStream2;
/* 2249:     */     }
/* 2250:1978 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2251:1979 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2252:1980 */     localOutputStream1.write_value(localCCCompanyAcctInfo2, CCCompanyAcctInfo.class);
/* 2253:1981 */     return localOutputStream1;
/* 2254:     */   }
/* 2255:     */   
/* 2256:     */   private org.omg.CORBA.portable.OutputStream modCCCompanyAcct(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2257:     */     throws Throwable
/* 2258:     */   {
/* 2259:1985 */     CCCompanyAcctInfo localCCCompanyAcctInfo1 = (CCCompanyAcctInfo)paramInputStream.read_value(CCCompanyAcctInfo.class);
/* 2260:     */     CCCompanyAcctInfo localCCCompanyAcctInfo2;
/* 2261:     */     try
/* 2262:     */     {
/* 2263:1988 */       localCCCompanyAcctInfo2 = this.target.modCCCompanyAcct(localCCCompanyAcctInfo1);
/* 2264:     */     }
/* 2265:     */     catch (FFSException localFFSException)
/* 2266:     */     {
/* 2267:1990 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2268:1991 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2269:1992 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2270:1993 */       localOutputStream2.write_string(str);
/* 2271:1994 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2272:1995 */       return localOutputStream2;
/* 2273:     */     }
/* 2274:1997 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2275:1998 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2276:1999 */     localOutputStream1.write_value(localCCCompanyAcctInfo2, CCCompanyAcctInfo.class);
/* 2277:2000 */     return localOutputStream1;
/* 2278:     */   }
/* 2279:     */   
/* 2280:     */   private org.omg.CORBA.portable.OutputStream getCCCompanyAcct(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2281:     */     throws Throwable
/* 2282:     */   {
/* 2283:2004 */     CCCompanyAcctInfo localCCCompanyAcctInfo1 = (CCCompanyAcctInfo)paramInputStream.read_value(CCCompanyAcctInfo.class);
/* 2284:     */     CCCompanyAcctInfo localCCCompanyAcctInfo2;
/* 2285:     */     try
/* 2286:     */     {
/* 2287:2007 */       localCCCompanyAcctInfo2 = this.target.getCCCompanyAcct(localCCCompanyAcctInfo1);
/* 2288:     */     }
/* 2289:     */     catch (FFSException localFFSException)
/* 2290:     */     {
/* 2291:2009 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2292:2010 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2293:2011 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2294:2012 */       localOutputStream2.write_string(str);
/* 2295:2013 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2296:2014 */       return localOutputStream2;
/* 2297:     */     }
/* 2298:2016 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2299:2017 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2300:2018 */     localOutputStream1.write_value(localCCCompanyAcctInfo2, CCCompanyAcctInfo.class);
/* 2301:2019 */     return localOutputStream1;
/* 2302:     */   }
/* 2303:     */   
/* 2304:     */   private org.omg.CORBA.portable.OutputStream getCCCompanyAcctList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2305:     */     throws Throwable
/* 2306:     */   {
/* 2307:2023 */     CCCompanyAcctInfoList localCCCompanyAcctInfoList1 = (CCCompanyAcctInfoList)paramInputStream.read_value(CCCompanyAcctInfoList.class);
/* 2308:     */     CCCompanyAcctInfoList localCCCompanyAcctInfoList2;
/* 2309:     */     try
/* 2310:     */     {
/* 2311:2026 */       localCCCompanyAcctInfoList2 = this.target.getCCCompanyAcctList(localCCCompanyAcctInfoList1);
/* 2312:     */     }
/* 2313:     */     catch (FFSException localFFSException)
/* 2314:     */     {
/* 2315:2028 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2316:2029 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2317:2030 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2318:2031 */       localOutputStream2.write_string(str);
/* 2319:2032 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2320:2033 */       return localOutputStream2;
/* 2321:     */     }
/* 2322:2035 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2323:2036 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2324:2037 */     localOutputStream1.write_value(localCCCompanyAcctInfoList2, CCCompanyAcctInfoList.class);
/* 2325:2038 */     return localOutputStream1;
/* 2326:     */   }
/* 2327:     */   
/* 2328:     */   private org.omg.CORBA.portable.OutputStream addCCCompanyCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2329:     */     throws Throwable
/* 2330:     */   {
/* 2331:2042 */     CCCompanyCutOffInfo localCCCompanyCutOffInfo1 = (CCCompanyCutOffInfo)paramInputStream.read_value(CCCompanyCutOffInfo.class);
/* 2332:     */     CCCompanyCutOffInfo localCCCompanyCutOffInfo2;
/* 2333:     */     try
/* 2334:     */     {
/* 2335:2045 */       localCCCompanyCutOffInfo2 = this.target.addCCCompanyCutOff(localCCCompanyCutOffInfo1);
/* 2336:     */     }
/* 2337:     */     catch (FFSException localFFSException)
/* 2338:     */     {
/* 2339:2047 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2340:2048 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2341:2049 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2342:2050 */       localOutputStream2.write_string(str);
/* 2343:2051 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2344:2052 */       return localOutputStream2;
/* 2345:     */     }
/* 2346:2054 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2347:2055 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2348:2056 */     localOutputStream1.write_value(localCCCompanyCutOffInfo2, CCCompanyCutOffInfo.class);
/* 2349:2057 */     return localOutputStream1;
/* 2350:     */   }
/* 2351:     */   
/* 2352:     */   private org.omg.CORBA.portable.OutputStream cancelCCCompanyCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2353:     */     throws Throwable
/* 2354:     */   {
/* 2355:2061 */     CCCompanyCutOffInfo localCCCompanyCutOffInfo1 = (CCCompanyCutOffInfo)paramInputStream.read_value(CCCompanyCutOffInfo.class);
/* 2356:     */     CCCompanyCutOffInfo localCCCompanyCutOffInfo2;
/* 2357:     */     try
/* 2358:     */     {
/* 2359:2064 */       localCCCompanyCutOffInfo2 = this.target.cancelCCCompanyCutOff(localCCCompanyCutOffInfo1);
/* 2360:     */     }
/* 2361:     */     catch (FFSException localFFSException)
/* 2362:     */     {
/* 2363:2066 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2364:2067 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2365:2068 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2366:2069 */       localOutputStream2.write_string(str);
/* 2367:2070 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2368:2071 */       return localOutputStream2;
/* 2369:     */     }
/* 2370:2073 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2371:2074 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2372:2075 */     localOutputStream1.write_value(localCCCompanyCutOffInfo2, CCCompanyCutOffInfo.class);
/* 2373:2076 */     return localOutputStream1;
/* 2374:     */   }
/* 2375:     */   
/* 2376:     */   private org.omg.CORBA.portable.OutputStream getCCCompanyCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2377:     */     throws Throwable
/* 2378:     */   {
/* 2379:2080 */     CCCompanyCutOffInfo localCCCompanyCutOffInfo1 = (CCCompanyCutOffInfo)paramInputStream.read_value(CCCompanyCutOffInfo.class);
/* 2380:     */     CCCompanyCutOffInfo localCCCompanyCutOffInfo2;
/* 2381:     */     try
/* 2382:     */     {
/* 2383:2083 */       localCCCompanyCutOffInfo2 = this.target.getCCCompanyCutOff(localCCCompanyCutOffInfo1);
/* 2384:     */     }
/* 2385:     */     catch (FFSException localFFSException)
/* 2386:     */     {
/* 2387:2085 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2388:2086 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2389:2087 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2390:2088 */       localOutputStream2.write_string(str);
/* 2391:2089 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2392:2090 */       return localOutputStream2;
/* 2393:     */     }
/* 2394:2092 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2395:2093 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2396:2094 */     localOutputStream1.write_value(localCCCompanyCutOffInfo2, CCCompanyCutOffInfo.class);
/* 2397:2095 */     return localOutputStream1;
/* 2398:     */   }
/* 2399:     */   
/* 2400:     */   private org.omg.CORBA.portable.OutputStream getCCCompanyCutOffList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2401:     */     throws Throwable
/* 2402:     */   {
/* 2403:2099 */     CCCompanyCutOffInfoList localCCCompanyCutOffInfoList1 = (CCCompanyCutOffInfoList)paramInputStream.read_value(CCCompanyCutOffInfoList.class);
/* 2404:     */     CCCompanyCutOffInfoList localCCCompanyCutOffInfoList2;
/* 2405:     */     try
/* 2406:     */     {
/* 2407:2102 */       localCCCompanyCutOffInfoList2 = this.target.getCCCompanyCutOffList(localCCCompanyCutOffInfoList1);
/* 2408:     */     }
/* 2409:     */     catch (FFSException localFFSException)
/* 2410:     */     {
/* 2411:2104 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2412:2105 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2413:2106 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2414:2107 */       localOutputStream2.write_string(str);
/* 2415:2108 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2416:2109 */       return localOutputStream2;
/* 2417:     */     }
/* 2418:2111 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2419:2112 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2420:2113 */     localOutputStream1.write_value(localCCCompanyCutOffInfoList2, CCCompanyCutOffInfoList.class);
/* 2421:2114 */     return localOutputStream1;
/* 2422:     */   }
/* 2423:     */   
/* 2424:     */   private org.omg.CORBA.portable.OutputStream addCCLocation(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2425:     */     throws Throwable
/* 2426:     */   {
/* 2427:2118 */     CCLocationInfo localCCLocationInfo1 = (CCLocationInfo)paramInputStream.read_value(CCLocationInfo.class);
/* 2428:     */     CCLocationInfo localCCLocationInfo2;
/* 2429:     */     try
/* 2430:     */     {
/* 2431:2121 */       localCCLocationInfo2 = this.target.addCCLocation(localCCLocationInfo1);
/* 2432:     */     }
/* 2433:     */     catch (FFSException localFFSException)
/* 2434:     */     {
/* 2435:2123 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2436:2124 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2437:2125 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2438:2126 */       localOutputStream2.write_string(str);
/* 2439:2127 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2440:2128 */       return localOutputStream2;
/* 2441:     */     }
/* 2442:2130 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2443:2131 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2444:2132 */     localOutputStream1.write_value(localCCLocationInfo2, CCLocationInfo.class);
/* 2445:2133 */     return localOutputStream1;
/* 2446:     */   }
/* 2447:     */   
/* 2448:     */   private org.omg.CORBA.portable.OutputStream cancelCCLocation(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2449:     */     throws Throwable
/* 2450:     */   {
/* 2451:2137 */     CCLocationInfo localCCLocationInfo1 = (CCLocationInfo)paramInputStream.read_value(CCLocationInfo.class);
/* 2452:     */     CCLocationInfo localCCLocationInfo2;
/* 2453:     */     try
/* 2454:     */     {
/* 2455:2140 */       localCCLocationInfo2 = this.target.cancelCCLocation(localCCLocationInfo1);
/* 2456:     */     }
/* 2457:     */     catch (FFSException localFFSException)
/* 2458:     */     {
/* 2459:2142 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2460:2143 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2461:2144 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2462:2145 */       localOutputStream2.write_string(str);
/* 2463:2146 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2464:2147 */       return localOutputStream2;
/* 2465:     */     }
/* 2466:2149 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2467:2150 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2468:2151 */     localOutputStream1.write_value(localCCLocationInfo2, CCLocationInfo.class);
/* 2469:2152 */     return localOutputStream1;
/* 2470:     */   }
/* 2471:     */   
/* 2472:     */   private org.omg.CORBA.portable.OutputStream modCCLocation(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2473:     */     throws Throwable
/* 2474:     */   {
/* 2475:2156 */     CCLocationInfo localCCLocationInfo1 = (CCLocationInfo)paramInputStream.read_value(CCLocationInfo.class);
/* 2476:     */     CCLocationInfo localCCLocationInfo2;
/* 2477:     */     try
/* 2478:     */     {
/* 2479:2159 */       localCCLocationInfo2 = this.target.modCCLocation(localCCLocationInfo1);
/* 2480:     */     }
/* 2481:     */     catch (FFSException localFFSException)
/* 2482:     */     {
/* 2483:2161 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2484:2162 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2485:2163 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2486:2164 */       localOutputStream2.write_string(str);
/* 2487:2165 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2488:2166 */       return localOutputStream2;
/* 2489:     */     }
/* 2490:2168 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2491:2169 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2492:2170 */     localOutputStream1.write_value(localCCLocationInfo2, CCLocationInfo.class);
/* 2493:2171 */     return localOutputStream1;
/* 2494:     */   }
/* 2495:     */   
/* 2496:     */   private org.omg.CORBA.portable.OutputStream getCCLocation(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2497:     */     throws Throwable
/* 2498:     */   {
/* 2499:2175 */     CCLocationInfo localCCLocationInfo1 = (CCLocationInfo)paramInputStream.read_value(CCLocationInfo.class);
/* 2500:     */     CCLocationInfo localCCLocationInfo2;
/* 2501:     */     try
/* 2502:     */     {
/* 2503:2178 */       localCCLocationInfo2 = this.target.getCCLocation(localCCLocationInfo1);
/* 2504:     */     }
/* 2505:     */     catch (FFSException localFFSException)
/* 2506:     */     {
/* 2507:2180 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2508:2181 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2509:2182 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2510:2183 */       localOutputStream2.write_string(str);
/* 2511:2184 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2512:2185 */       return localOutputStream2;
/* 2513:     */     }
/* 2514:2187 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2515:2188 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2516:2189 */     localOutputStream1.write_value(localCCLocationInfo2, CCLocationInfo.class);
/* 2517:2190 */     return localOutputStream1;
/* 2518:     */   }
/* 2519:     */   
/* 2520:     */   private org.omg.CORBA.portable.OutputStream getCCLocationList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2521:     */     throws Throwable
/* 2522:     */   {
/* 2523:2194 */     CCLocationInfoList localCCLocationInfoList1 = (CCLocationInfoList)paramInputStream.read_value(CCLocationInfoList.class);
/* 2524:     */     CCLocationInfoList localCCLocationInfoList2;
/* 2525:     */     try
/* 2526:     */     {
/* 2527:2197 */       localCCLocationInfoList2 = this.target.getCCLocationList(localCCLocationInfoList1);
/* 2528:     */     }
/* 2529:     */     catch (FFSException localFFSException)
/* 2530:     */     {
/* 2531:2199 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2532:2200 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2533:2201 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2534:2202 */       localOutputStream2.write_string(str);
/* 2535:2203 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2536:2204 */       return localOutputStream2;
/* 2537:     */     }
/* 2538:2206 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2539:2207 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2540:2208 */     localOutputStream1.write_value(localCCLocationInfoList2, CCLocationInfoList.class);
/* 2541:2209 */     return localOutputStream1;
/* 2542:     */   }
/* 2543:     */   
/* 2544:     */   private org.omg.CORBA.portable.OutputStream addCCEntry(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2545:     */     throws Throwable
/* 2546:     */   {
/* 2547:2213 */     CCEntryInfo localCCEntryInfo1 = (CCEntryInfo)paramInputStream.read_value(CCEntryInfo.class);
/* 2548:     */     CCEntryInfo localCCEntryInfo2;
/* 2549:     */     try
/* 2550:     */     {
/* 2551:2216 */       localCCEntryInfo2 = this.target.addCCEntry(localCCEntryInfo1);
/* 2552:     */     }
/* 2553:     */     catch (FFSException localFFSException)
/* 2554:     */     {
/* 2555:2218 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2556:2219 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2557:2220 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2558:2221 */       localOutputStream2.write_string(str);
/* 2559:2222 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2560:2223 */       return localOutputStream2;
/* 2561:     */     }
/* 2562:2225 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2563:2226 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2564:2227 */     localOutputStream1.write_value(localCCEntryInfo2, CCEntryInfo.class);
/* 2565:2228 */     return localOutputStream1;
/* 2566:     */   }
/* 2567:     */   
/* 2568:     */   private org.omg.CORBA.portable.OutputStream cancelCCEntry(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2569:     */     throws Throwable
/* 2570:     */   {
/* 2571:2232 */     CCEntryInfo localCCEntryInfo1 = (CCEntryInfo)paramInputStream.read_value(CCEntryInfo.class);
/* 2572:     */     CCEntryInfo localCCEntryInfo2;
/* 2573:     */     try
/* 2574:     */     {
/* 2575:2235 */       localCCEntryInfo2 = this.target.cancelCCEntry(localCCEntryInfo1);
/* 2576:     */     }
/* 2577:     */     catch (FFSException localFFSException)
/* 2578:     */     {
/* 2579:2237 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2580:2238 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2581:2239 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2582:2240 */       localOutputStream2.write_string(str);
/* 2583:2241 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2584:2242 */       return localOutputStream2;
/* 2585:     */     }
/* 2586:2244 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2587:2245 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2588:2246 */     localOutputStream1.write_value(localCCEntryInfo2, CCEntryInfo.class);
/* 2589:2247 */     return localOutputStream1;
/* 2590:     */   }
/* 2591:     */   
/* 2592:     */   private org.omg.CORBA.portable.OutputStream modCCEntry(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2593:     */     throws Throwable
/* 2594:     */   {
/* 2595:2251 */     CCEntryInfo localCCEntryInfo1 = (CCEntryInfo)paramInputStream.read_value(CCEntryInfo.class);
/* 2596:     */     CCEntryInfo localCCEntryInfo2;
/* 2597:     */     try
/* 2598:     */     {
/* 2599:2254 */       localCCEntryInfo2 = this.target.modCCEntry(localCCEntryInfo1);
/* 2600:     */     }
/* 2601:     */     catch (FFSException localFFSException)
/* 2602:     */     {
/* 2603:2256 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2604:2257 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2605:2258 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2606:2259 */       localOutputStream2.write_string(str);
/* 2607:2260 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2608:2261 */       return localOutputStream2;
/* 2609:     */     }
/* 2610:2263 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2611:2264 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2612:2265 */     localOutputStream1.write_value(localCCEntryInfo2, CCEntryInfo.class);
/* 2613:2266 */     return localOutputStream1;
/* 2614:     */   }
/* 2615:     */   
/* 2616:     */   private org.omg.CORBA.portable.OutputStream getCCEntry(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2617:     */     throws Throwable
/* 2618:     */   {
/* 2619:2270 */     CCEntryInfo localCCEntryInfo1 = (CCEntryInfo)paramInputStream.read_value(CCEntryInfo.class);
/* 2620:     */     CCEntryInfo localCCEntryInfo2;
/* 2621:     */     try
/* 2622:     */     {
/* 2623:2273 */       localCCEntryInfo2 = this.target.getCCEntry(localCCEntryInfo1);
/* 2624:     */     }
/* 2625:     */     catch (FFSException localFFSException)
/* 2626:     */     {
/* 2627:2275 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2628:2276 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2629:2277 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2630:2278 */       localOutputStream2.write_string(str);
/* 2631:2279 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2632:2280 */       return localOutputStream2;
/* 2633:     */     }
/* 2634:2282 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2635:2283 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2636:2284 */     localOutputStream1.write_value(localCCEntryInfo2, CCEntryInfo.class);
/* 2637:2285 */     return localOutputStream1;
/* 2638:     */   }
/* 2639:     */   
/* 2640:     */   private org.omg.CORBA.portable.OutputStream getCCEntryHist(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2641:     */     throws Throwable
/* 2642:     */   {
/* 2643:2289 */     CCEntryHistInfo localCCEntryHistInfo1 = (CCEntryHistInfo)paramInputStream.read_value(CCEntryHistInfo.class);
/* 2644:     */     CCEntryHistInfo localCCEntryHistInfo2;
/* 2645:     */     try
/* 2646:     */     {
/* 2647:2292 */       localCCEntryHistInfo2 = this.target.getCCEntryHist(localCCEntryHistInfo1);
/* 2648:     */     }
/* 2649:     */     catch (FFSException localFFSException)
/* 2650:     */     {
/* 2651:2294 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2652:2295 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2653:2296 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2654:2297 */       localOutputStream2.write_string(str);
/* 2655:2298 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2656:2299 */       return localOutputStream2;
/* 2657:     */     }
/* 2658:2301 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2659:2302 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2660:2303 */     localOutputStream1.write_value(localCCEntryHistInfo2, CCEntryHistInfo.class);
/* 2661:2304 */     return localOutputStream1;
/* 2662:     */   }
/* 2663:     */   
/* 2664:     */   private org.omg.CORBA.portable.OutputStream getCCEntrySummaryList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2665:     */     throws Throwable
/* 2666:     */   {
/* 2667:2308 */     CCEntrySummaryInfoList localCCEntrySummaryInfoList1 = (CCEntrySummaryInfoList)paramInputStream.read_value(CCEntrySummaryInfoList.class);
/* 2668:     */     CCEntrySummaryInfoList localCCEntrySummaryInfoList2;
/* 2669:     */     try
/* 2670:     */     {
/* 2671:2311 */       localCCEntrySummaryInfoList2 = this.target.getCCEntrySummaryList(localCCEntrySummaryInfoList1);
/* 2672:     */     }
/* 2673:     */     catch (FFSException localFFSException)
/* 2674:     */     {
/* 2675:2313 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2676:2314 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2677:2315 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2678:2316 */       localOutputStream2.write_string(str);
/* 2679:2317 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2680:2318 */       return localOutputStream2;
/* 2681:     */     }
/* 2682:2320 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2683:2321 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2684:2322 */     localOutputStream1.write_value(localCCEntrySummaryInfoList2, CCEntrySummaryInfoList.class);
/* 2685:2323 */     return localOutputStream1;
/* 2686:     */   }
/* 2687:     */   
/* 2688:     */   private org.omg.CORBA.portable.OutputStream addTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2689:     */     throws Throwable
/* 2690:     */   {
/* 2691:2327 */     TransferInfo localTransferInfo1 = (TransferInfo)paramInputStream.read_value(TransferInfo.class);
/* 2692:2328 */     TransferInfo localTransferInfo2 = this.target.addTransfer(localTransferInfo1);
/* 2693:2329 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2694:2330 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2695:2331 */     localOutputStream.write_value(localTransferInfo2, TransferInfo.class);
/* 2696:2332 */     return localOutputStream;
/* 2697:     */   }
/* 2698:     */   
/* 2699:     */   private org.omg.CORBA.portable.OutputStream modTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2700:     */     throws Throwable
/* 2701:     */   {
/* 2702:2336 */     TransferInfo localTransferInfo1 = (TransferInfo)paramInputStream.read_value(TransferInfo.class);
/* 2703:2337 */     TransferInfo localTransferInfo2 = this.target.modTransfer(localTransferInfo1);
/* 2704:2338 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2705:2339 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2706:2340 */     localOutputStream.write_value(localTransferInfo2, TransferInfo.class);
/* 2707:2341 */     return localOutputStream;
/* 2708:     */   }
/* 2709:     */   
/* 2710:     */   private org.omg.CORBA.portable.OutputStream cancTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2711:     */     throws Throwable
/* 2712:     */   {
/* 2713:2345 */     TransferInfo localTransferInfo1 = (TransferInfo)paramInputStream.read_value(TransferInfo.class);
/* 2714:2346 */     TransferInfo localTransferInfo2 = this.target.cancTransfer(localTransferInfo1);
/* 2715:2347 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2716:2348 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2717:2349 */     localOutputStream.write_value(localTransferInfo2, TransferInfo.class);
/* 2718:2350 */     return localOutputStream;
/* 2719:     */   }
/* 2720:     */   
/* 2721:     */   private org.omg.CORBA.portable.OutputStream getTransferBySrvrTId__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2722:     */     throws Throwable
/* 2723:     */   {
/* 2724:2354 */     String str = (String)paramInputStream.read_value(String.class);
/* 2725:2355 */     TransferInfo localTransferInfo = this.target.getTransferBySrvrTId(str);
/* 2726:2356 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2727:2357 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2728:2358 */     localOutputStream.write_value(localTransferInfo, TransferInfo.class);
/* 2729:2359 */     return localOutputStream;
/* 2730:     */   }
/* 2731:     */   
/* 2732:     */   private org.omg.CORBA.portable.OutputStream getTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2733:     */     throws Throwable
/* 2734:     */   {
/* 2735:2363 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 2736:2364 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 2737:2365 */     TransferInfo localTransferInfo = this.target.getTransferBySrvrTId(str1, str2);
/* 2738:2366 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2739:2367 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2740:2368 */     localOutputStream.write_value(localTransferInfo, TransferInfo.class);
/* 2741:2369 */     return localOutputStream;
/* 2742:     */   }
/* 2743:     */   
/* 2744:     */   private org.omg.CORBA.portable.OutputStream getTransferByTrackingId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2745:     */     throws Throwable
/* 2746:     */   {
/* 2747:2373 */     String str = (String)paramInputStream.read_value(String.class);
/* 2748:2374 */     TransferInfo localTransferInfo = this.target.getTransferByTrackingId(str);
/* 2749:2375 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2750:2376 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2751:2377 */     localOutputStream.write_value(localTransferInfo, TransferInfo.class);
/* 2752:2378 */     return localOutputStream;
/* 2753:     */   }
/* 2754:     */   
/* 2755:     */   private org.omg.CORBA.portable.OutputStream getTransfersBySrvrTId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2756:     */     throws Throwable
/* 2757:     */   {
/* 2758:2382 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 2759:2383 */     TransferInfo[] arrayOfTransferInfo = this.target.getTransfersBySrvrTId(arrayOfString);
/* 2760:2384 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2761:2385 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2762:2386 */     localOutputStream.write_value(cast_array(arrayOfTransferInfo), new TransferInfo[0].getClass());
/* 2763:2387 */     return localOutputStream;
/* 2764:     */   }
/* 2765:     */   
/* 2766:     */   private org.omg.CORBA.portable.OutputStream getTransfersByRecSrvrTId__CORBA_WStringValue__boolean(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2767:     */     throws Throwable
/* 2768:     */   {
/* 2769:2391 */     String str = (String)paramInputStream.read_value(String.class);
/* 2770:2392 */     boolean bool = paramInputStream.read_boolean();
/* 2771:2393 */     TransferInfo[] arrayOfTransferInfo = this.target.getTransfersByRecSrvrTId(str, bool);
/* 2772:2394 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2773:2395 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2774:2396 */     localOutputStream.write_value(cast_array(arrayOfTransferInfo), new TransferInfo[0].getClass());
/* 2775:2397 */     return localOutputStream;
/* 2776:     */   }
/* 2777:     */   
/* 2778:     */   private org.omg.CORBA.portable.OutputStream getTransfersByRecSrvrTId__org_omg_boxedRMI_CORBA_seq1_WStringValue__boolean(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2779:     */     throws Throwable
/* 2780:     */   {
/* 2781:2401 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 2782:2402 */     boolean bool = paramInputStream.read_boolean();
/* 2783:2403 */     TransferInfo[] arrayOfTransferInfo = this.target.getTransfersByRecSrvrTId(arrayOfString, bool);
/* 2784:2404 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2785:2405 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2786:2406 */     localOutputStream.write_value(cast_array(arrayOfTransferInfo), new TransferInfo[0].getClass());
/* 2787:2407 */     return localOutputStream;
/* 2788:     */   }
/* 2789:     */   
/* 2790:     */   private org.omg.CORBA.portable.OutputStream getTransfersByTrackingId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2791:     */     throws Throwable
/* 2792:     */   {
/* 2793:2411 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 2794:2412 */     TransferInfo[] arrayOfTransferInfo = this.target.getTransfersByTrackingId(arrayOfString);
/* 2795:2413 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2796:2414 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2797:2415 */     localOutputStream.write_value(cast_array(arrayOfTransferInfo), new TransferInfo[0].getClass());
/* 2798:2416 */     return localOutputStream;
/* 2799:     */   }
/* 2800:     */   
/* 2801:     */   private org.omg.CORBA.portable.OutputStream getTransferHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2802:     */     throws Throwable
/* 2803:     */   {
/* 2804:2420 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 2805:2421 */     BPWHist localBPWHist2 = this.target.getTransferHistory(localBPWHist1);
/* 2806:2422 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2807:2423 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2808:2424 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 2809:2425 */     return localOutputStream;
/* 2810:     */   }
/* 2811:     */   
/* 2812:     */   private org.omg.CORBA.portable.OutputStream addTransfers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2813:     */     throws Throwable
/* 2814:     */   {
/* 2815:2429 */     TransferInfo[] arrayOfTransferInfo1 = (TransferInfo[])paramInputStream.read_value(new TransferInfo[0].getClass());
/* 2816:2430 */     TransferInfo[] arrayOfTransferInfo2 = this.target.addTransfers(arrayOfTransferInfo1);
/* 2817:2431 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2818:2432 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2819:2433 */     localOutputStream.write_value(cast_array(arrayOfTransferInfo2), new TransferInfo[0].getClass());
/* 2820:2434 */     return localOutputStream;
/* 2821:     */   }
/* 2822:     */   
/* 2823:     */   private org.omg.CORBA.portable.OutputStream addRecTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2824:     */     throws Throwable
/* 2825:     */   {
/* 2826:2438 */     RecTransferInfo localRecTransferInfo1 = (RecTransferInfo)paramInputStream.read_value(RecTransferInfo.class);
/* 2827:2439 */     RecTransferInfo localRecTransferInfo2 = this.target.addRecTransfer(localRecTransferInfo1);
/* 2828:2440 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2829:2441 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2830:2442 */     localOutputStream.write_value(localRecTransferInfo2, RecTransferInfo.class);
/* 2831:2443 */     return localOutputStream;
/* 2832:     */   }
/* 2833:     */   
/* 2834:     */   private org.omg.CORBA.portable.OutputStream modRecTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2835:     */     throws Throwable
/* 2836:     */   {
/* 2837:2447 */     RecTransferInfo localRecTransferInfo1 = (RecTransferInfo)paramInputStream.read_value(RecTransferInfo.class);
/* 2838:2448 */     RecTransferInfo localRecTransferInfo2 = this.target.modRecTransfer(localRecTransferInfo1);
/* 2839:2449 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2840:2450 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2841:2451 */     localOutputStream.write_value(localRecTransferInfo2, RecTransferInfo.class);
/* 2842:2452 */     return localOutputStream;
/* 2843:     */   }
/* 2844:     */   
/* 2845:     */   private org.omg.CORBA.portable.OutputStream cancRecTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2846:     */     throws Throwable
/* 2847:     */   {
/* 2848:2456 */     RecTransferInfo localRecTransferInfo1 = (RecTransferInfo)paramInputStream.read_value(RecTransferInfo.class);
/* 2849:2457 */     RecTransferInfo localRecTransferInfo2 = this.target.cancRecTransfer(localRecTransferInfo1);
/* 2850:2458 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2851:2459 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2852:2460 */     localOutputStream.write_value(localRecTransferInfo2, RecTransferInfo.class);
/* 2853:2461 */     return localOutputStream;
/* 2854:     */   }
/* 2855:     */   
/* 2856:     */   private org.omg.CORBA.portable.OutputStream getRecTransferBySrvrTId__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2857:     */     throws Throwable
/* 2858:     */   {
/* 2859:2465 */     String str = (String)paramInputStream.read_value(String.class);
/* 2860:2466 */     RecTransferInfo localRecTransferInfo = this.target.getRecTransferBySrvrTId(str);
/* 2861:2467 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2862:2468 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2863:2469 */     localOutputStream.write_value(localRecTransferInfo, RecTransferInfo.class);
/* 2864:2470 */     return localOutputStream;
/* 2865:     */   }
/* 2866:     */   
/* 2867:     */   private org.omg.CORBA.portable.OutputStream getRecTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2868:     */     throws Throwable
/* 2869:     */   {
/* 2870:2474 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 2871:2475 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 2872:2476 */     RecTransferInfo localRecTransferInfo = this.target.getRecTransferBySrvrTId(str1, str2);
/* 2873:2477 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2874:2478 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2875:2479 */     localOutputStream.write_value(localRecTransferInfo, RecTransferInfo.class);
/* 2876:2480 */     return localOutputStream;
/* 2877:     */   }
/* 2878:     */   
/* 2879:     */   private org.omg.CORBA.portable.OutputStream getRecTransferByTrackingId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2880:     */     throws Throwable
/* 2881:     */   {
/* 2882:2484 */     String str = (String)paramInputStream.read_value(String.class);
/* 2883:2485 */     RecTransferInfo localRecTransferInfo = this.target.getRecTransferByTrackingId(str);
/* 2884:2486 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2885:2487 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2886:2488 */     localOutputStream.write_value(localRecTransferInfo, RecTransferInfo.class);
/* 2887:2489 */     return localOutputStream;
/* 2888:     */   }
/* 2889:     */   
/* 2890:     */   private org.omg.CORBA.portable.OutputStream getRecTransfersBySrvrTId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2891:     */     throws Throwable
/* 2892:     */   {
/* 2893:2493 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 2894:2494 */     RecTransferInfo[] arrayOfRecTransferInfo = this.target.getRecTransfersBySrvrTId(arrayOfString);
/* 2895:2495 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2896:2496 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2897:2497 */     localOutputStream.write_value(cast_array(arrayOfRecTransferInfo), new RecTransferInfo[0].getClass());
/* 2898:2498 */     return localOutputStream;
/* 2899:     */   }
/* 2900:     */   
/* 2901:     */   private org.omg.CORBA.portable.OutputStream getRecTransfers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2902:     */     throws Throwable
/* 2903:     */   {
/* 2904:2502 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 2905:2503 */     BPWHist localBPWHist2 = this.target.getRecTransfers(localBPWHist1);
/* 2906:2504 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2907:2505 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2908:2506 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 2909:2507 */     return localOutputStream;
/* 2910:     */   }
/* 2911:     */   
/* 2912:     */   private org.omg.CORBA.portable.OutputStream getRecTransfersByTrackingId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2913:     */     throws Throwable
/* 2914:     */   {
/* 2915:2511 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 2916:2512 */     RecTransferInfo[] arrayOfRecTransferInfo = this.target.getRecTransfersByTrackingId(arrayOfString);
/* 2917:2513 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2918:2514 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2919:2515 */     localOutputStream.write_value(cast_array(arrayOfRecTransferInfo), new RecTransferInfo[0].getClass());
/* 2920:2516 */     return localOutputStream;
/* 2921:     */   }
/* 2922:     */   
/* 2923:     */   private org.omg.CORBA.portable.OutputStream getRecTransferHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2924:     */     throws Throwable
/* 2925:     */   {
/* 2926:2520 */     BPWHist localBPWHist1 = (BPWHist)paramInputStream.read_value(BPWHist.class);
/* 2927:2521 */     BPWHist localBPWHist2 = this.target.getRecTransferHistory(localBPWHist1);
/* 2928:2522 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2929:2523 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2930:2524 */     localOutputStream.write_value(localBPWHist2, BPWHist.class);
/* 2931:2525 */     return localOutputStream;
/* 2932:     */   }
/* 2933:     */   
/* 2934:     */   private org.omg.CORBA.portable.OutputStream addExtTransferCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2935:     */     throws Throwable
/* 2936:     */   {
/* 2937:2529 */     ExtTransferCompanyInfo localExtTransferCompanyInfo1 = (ExtTransferCompanyInfo)paramInputStream.read_value(ExtTransferCompanyInfo.class);
/* 2938:2530 */     ExtTransferCompanyInfo localExtTransferCompanyInfo2 = this.target.addExtTransferCompany(localExtTransferCompanyInfo1);
/* 2939:2531 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2940:2532 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2941:2533 */     localOutputStream.write_value(localExtTransferCompanyInfo2, ExtTransferCompanyInfo.class);
/* 2942:2534 */     return localOutputStream;
/* 2943:     */   }
/* 2944:     */   
/* 2945:     */   private org.omg.CORBA.portable.OutputStream canExtTransferCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2946:     */     throws Throwable
/* 2947:     */   {
/* 2948:2538 */     ExtTransferCompanyInfo localExtTransferCompanyInfo1 = (ExtTransferCompanyInfo)paramInputStream.read_value(ExtTransferCompanyInfo.class);
/* 2949:2539 */     ExtTransferCompanyInfo localExtTransferCompanyInfo2 = this.target.canExtTransferCompany(localExtTransferCompanyInfo1);
/* 2950:2540 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2951:2541 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2952:2542 */     localOutputStream.write_value(localExtTransferCompanyInfo2, ExtTransferCompanyInfo.class);
/* 2953:2543 */     return localOutputStream;
/* 2954:     */   }
/* 2955:     */   
/* 2956:     */   private org.omg.CORBA.portable.OutputStream modExtTransferCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2957:     */     throws Throwable
/* 2958:     */   {
/* 2959:2547 */     ExtTransferCompanyInfo localExtTransferCompanyInfo1 = (ExtTransferCompanyInfo)paramInputStream.read_value(ExtTransferCompanyInfo.class);
/* 2960:2548 */     ExtTransferCompanyInfo localExtTransferCompanyInfo2 = this.target.modExtTransferCompany(localExtTransferCompanyInfo1);
/* 2961:2549 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2962:2550 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2963:2551 */     localOutputStream.write_value(localExtTransferCompanyInfo2, ExtTransferCompanyInfo.class);
/* 2964:2552 */     return localOutputStream;
/* 2965:     */   }
/* 2966:     */   
/* 2967:     */   private org.omg.CORBA.portable.OutputStream getExtTransferCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2968:     */     throws Throwable
/* 2969:     */   {
/* 2970:2556 */     ExtTransferCompanyInfo localExtTransferCompanyInfo1 = (ExtTransferCompanyInfo)paramInputStream.read_value(ExtTransferCompanyInfo.class);
/* 2971:2557 */     ExtTransferCompanyInfo localExtTransferCompanyInfo2 = this.target.getExtTransferCompany(localExtTransferCompanyInfo1);
/* 2972:2558 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2973:2559 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2974:2560 */     localOutputStream.write_value(localExtTransferCompanyInfo2, ExtTransferCompanyInfo.class);
/* 2975:2561 */     return localOutputStream;
/* 2976:     */   }
/* 2977:     */   
/* 2978:     */   private org.omg.CORBA.portable.OutputStream getExtTransferCompanyList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2979:     */     throws Throwable
/* 2980:     */   {
/* 2981:2565 */     ExtTransferCompanyList localExtTransferCompanyList1 = (ExtTransferCompanyList)paramInputStream.read_value(ExtTransferCompanyList.class);
/* 2982:2566 */     ExtTransferCompanyList localExtTransferCompanyList2 = this.target.getExtTransferCompanyList(localExtTransferCompanyList1);
/* 2983:2567 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2984:2568 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2985:2569 */     localOutputStream.write_value(localExtTransferCompanyList2, ExtTransferCompanyList.class);
/* 2986:2570 */     return localOutputStream;
/* 2987:     */   }
/* 2988:     */   
/* 2989:     */   private org.omg.CORBA.portable.OutputStream addExtTransferAccount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2990:     */     throws Throwable
/* 2991:     */   {
/* 2992:2574 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 2993:2575 */     ExtTransferAcctInfo localExtTransferAcctInfo2 = this.target.addExtTransferAccount(localExtTransferAcctInfo1);
/* 2994:2576 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 2995:2577 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2996:2578 */     localOutputStream.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 2997:2579 */     return localOutputStream;
/* 2998:     */   }
/* 2999:     */   
/* 3000:     */   private org.omg.CORBA.portable.OutputStream canExtTransferAccount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3001:     */     throws Throwable
/* 3002:     */   {
/* 3003:2583 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 3004:2584 */     ExtTransferAcctInfo localExtTransferAcctInfo2 = this.target.canExtTransferAccount(localExtTransferAcctInfo1);
/* 3005:2585 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 3006:2586 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3007:2587 */     localOutputStream.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 3008:2588 */     return localOutputStream;
/* 3009:     */   }
/* 3010:     */   
/* 3011:     */   private org.omg.CORBA.portable.OutputStream modExtTransferAccount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3012:     */     throws Throwable
/* 3013:     */   {
/* 3014:2592 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 3015:2593 */     ExtTransferAcctInfo localExtTransferAcctInfo2 = this.target.modExtTransferAccount(localExtTransferAcctInfo1);
/* 3016:2594 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 3017:2595 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3018:2596 */     localOutputStream.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 3019:2597 */     return localOutputStream;
/* 3020:     */   }
/* 3021:     */   
/* 3022:     */   private org.omg.CORBA.portable.OutputStream getExtTransferAccount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3023:     */     throws Throwable
/* 3024:     */   {
/* 3025:2601 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 3026:2602 */     ExtTransferAcctInfo localExtTransferAcctInfo2 = this.target.getExtTransferAccount(localExtTransferAcctInfo1);
/* 3027:2603 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 3028:2604 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3029:2605 */     localOutputStream.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 3030:2606 */     return localOutputStream;
/* 3031:     */   }
/* 3032:     */   
/* 3033:     */   private org.omg.CORBA.portable.OutputStream verifyExtTransferAccount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3034:     */     throws Throwable
/* 3035:     */   {
/* 3036:2610 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3037:2611 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 3038:2612 */     int[] arrayOfInt = (int[])paramInputStream.read_value(new int[0].getClass());
/* 3039:     */     ExtTransferAcctInfo localExtTransferAcctInfo2;
/* 3040:     */     try
/* 3041:     */     {
/* 3042:2615 */       localExtTransferAcctInfo2 = this.target.verifyExtTransferAccount(str1, localExtTransferAcctInfo1, arrayOfInt);
/* 3043:     */     }
/* 3044:     */     catch (FFSException localFFSException)
/* 3045:     */     {
/* 3046:2617 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3047:2618 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3048:2619 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3049:2620 */       localOutputStream2.write_string(str2);
/* 3050:2621 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3051:2622 */       return localOutputStream2;
/* 3052:     */     }
/* 3053:2624 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3054:2625 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3055:2626 */     localOutputStream1.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 3056:2627 */     return localOutputStream1;
/* 3057:     */   }
/* 3058:     */   
/* 3059:     */   private org.omg.CORBA.portable.OutputStream depositAmountsForVerify(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3060:     */     throws Throwable
/* 3061:     */   {
/* 3062:2631 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3063:2632 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 3064:     */     ExtTransferAcctInfo localExtTransferAcctInfo2;
/* 3065:     */     try
/* 3066:     */     {
/* 3067:2635 */       localExtTransferAcctInfo2 = this.target.depositAmountsForVerify(str1, localExtTransferAcctInfo1);
/* 3068:     */     }
/* 3069:     */     catch (FFSException localFFSException)
/* 3070:     */     {
/* 3071:2637 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3072:2638 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3073:2639 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3074:2640 */       localOutputStream2.write_string(str2);
/* 3075:2641 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3076:2642 */       return localOutputStream2;
/* 3077:     */     }
/* 3078:2644 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3079:2645 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3080:2646 */     localOutputStream1.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 3081:2647 */     return localOutputStream1;
/* 3082:     */   }
/* 3083:     */   
/* 3084:     */   private org.omg.CORBA.portable.OutputStream activateExtTransferAcct(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3085:     */     throws Throwable
/* 3086:     */   {
/* 3087:2651 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 3088:     */     ExtTransferAcctInfo localExtTransferAcctInfo2;
/* 3089:     */     try
/* 3090:     */     {
/* 3091:2654 */       localExtTransferAcctInfo2 = this.target.activateExtTransferAcct(localExtTransferAcctInfo1);
/* 3092:     */     }
/* 3093:     */     catch (FFSException localFFSException)
/* 3094:     */     {
/* 3095:2656 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3096:2657 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3097:2658 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3098:2659 */       localOutputStream2.write_string(str);
/* 3099:2660 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3100:2661 */       return localOutputStream2;
/* 3101:     */     }
/* 3102:2663 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3103:2664 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3104:2665 */     localOutputStream1.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 3105:2666 */     return localOutputStream1;
/* 3106:     */   }
/* 3107:     */   
/* 3108:     */   private org.omg.CORBA.portable.OutputStream inactivateExtTransferAcct(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3109:     */     throws Throwable
/* 3110:     */   {
/* 3111:2670 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 3112:     */     ExtTransferAcctInfo localExtTransferAcctInfo2;
/* 3113:     */     try
/* 3114:     */     {
/* 3115:2673 */       localExtTransferAcctInfo2 = this.target.inactivateExtTransferAcct(localExtTransferAcctInfo1);
/* 3116:     */     }
/* 3117:     */     catch (FFSException localFFSException)
/* 3118:     */     {
/* 3119:2675 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3120:2676 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3121:2677 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3122:2678 */       localOutputStream2.write_string(str);
/* 3123:2679 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3124:2680 */       return localOutputStream2;
/* 3125:     */     }
/* 3126:2682 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3127:2683 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3128:2684 */     localOutputStream1.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 3129:2685 */     return localOutputStream1;
/* 3130:     */   }
/* 3131:     */   
/* 3132:     */   private org.omg.CORBA.portable.OutputStream getExtTransferAcctList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3133:     */     throws Throwable
/* 3134:     */   {
/* 3135:2689 */     ExtTransferAcctList localExtTransferAcctList1 = (ExtTransferAcctList)paramInputStream.read_value(ExtTransferAcctList.class);
/* 3136:2690 */     ExtTransferAcctList localExtTransferAcctList2 = this.target.getExtTransferAcctList(localExtTransferAcctList1);
/* 3137:2691 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 3138:2692 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3139:2693 */     localOutputStream.write_value(localExtTransferAcctList2, ExtTransferAcctList.class);
/* 3140:2694 */     return localOutputStream;
/* 3141:     */   }
/* 3142:     */   
/* 3143:     */   private org.omg.CORBA.portable.OutputStream getNonBusinessDays(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3144:     */     throws Throwable
/* 3145:     */   {
/* 3146:2698 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3147:     */     NonBusinessDay[] arrayOfNonBusinessDay;
/* 3148:     */     try
/* 3149:     */     {
/* 3150:2701 */       arrayOfNonBusinessDay = this.target.getNonBusinessDays(str1);
/* 3151:     */     }
/* 3152:     */     catch (FFSException localFFSException)
/* 3153:     */     {
/* 3154:2703 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3155:2704 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3156:2705 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3157:2706 */       localOutputStream2.write_string(str2);
/* 3158:2707 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3159:2708 */       return localOutputStream2;
/* 3160:     */     }
/* 3161:2710 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3162:2711 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3163:2712 */     localOutputStream1.write_value(cast_array(arrayOfNonBusinessDay), new NonBusinessDay[0].getClass());
/* 3164:2713 */     return localOutputStream1;
/* 3165:     */   }
/* 3166:     */   
/* 3167:     */   private org.omg.CORBA.portable.OutputStream getNonBusinessDaysFromFile(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3168:     */     throws Throwable
/* 3169:     */   {
/* 3170:2717 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3171:     */     NonBusinessDay[] arrayOfNonBusinessDay;
/* 3172:     */     try
/* 3173:     */     {
/* 3174:2720 */       arrayOfNonBusinessDay = this.target.getNonBusinessDaysFromFile(str1);
/* 3175:     */     }
/* 3176:     */     catch (FFSException localFFSException)
/* 3177:     */     {
/* 3178:2722 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3179:2723 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3180:2724 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3181:2725 */       localOutputStream2.write_string(str2);
/* 3182:2726 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3183:2727 */       return localOutputStream2;
/* 3184:     */     }
/* 3185:2729 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3186:2730 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3187:2731 */     localOutputStream1.write_value(cast_array(arrayOfNonBusinessDay), new NonBusinessDay[0].getClass());
/* 3188:2732 */     return localOutputStream1;
/* 3189:     */   }
/* 3190:     */   
/* 3191:     */   private org.omg.CORBA.portable.OutputStream getPagedWire(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3192:     */     throws Throwable
/* 3193:     */   {
/* 3194:2736 */     PagingInfo localPagingInfo1 = (PagingInfo)paramInputStream.read_value(PagingInfo.class);
/* 3195:     */     PagingInfo localPagingInfo2;
/* 3196:     */     try
/* 3197:     */     {
/* 3198:2739 */       localPagingInfo2 = this.target.getPagedWire(localPagingInfo1);
/* 3199:     */     }
/* 3200:     */     catch (FFSException localFFSException)
/* 3201:     */     {
/* 3202:2741 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3203:2742 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3204:2743 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3205:2744 */       localOutputStream2.write_string(str);
/* 3206:2745 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3207:2746 */       return localOutputStream2;
/* 3208:     */     }
/* 3209:2748 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3210:2749 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3211:2750 */     localOutputStream1.write_value(localPagingInfo2, PagingInfo.class);
/* 3212:2751 */     return localOutputStream1;
/* 3213:     */   }
/* 3214:     */   
/* 3215:     */   private org.omg.CORBA.portable.OutputStream getPagedTransfer(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3216:     */     throws Throwable
/* 3217:     */   {
/* 3218:2755 */     PagingInfo localPagingInfo1 = (PagingInfo)paramInputStream.read_value(PagingInfo.class);
/* 3219:     */     PagingInfo localPagingInfo2;
/* 3220:     */     try
/* 3221:     */     {
/* 3222:2758 */       localPagingInfo2 = this.target.getPagedTransfer(localPagingInfo1);
/* 3223:     */     }
/* 3224:     */     catch (FFSException localFFSException)
/* 3225:     */     {
/* 3226:2760 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3227:2761 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3228:2762 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3229:2763 */       localOutputStream2.write_string(str);
/* 3230:2764 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3231:2765 */       return localOutputStream2;
/* 3232:     */     }
/* 3233:2767 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3234:2768 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3235:2769 */     localOutputStream1.write_value(localPagingInfo2, PagingInfo.class);
/* 3236:2770 */     return localOutputStream1;
/* 3237:     */   }
/* 3238:     */   
/* 3239:     */   private org.omg.CORBA.portable.OutputStream getPagedBillPayments(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3240:     */     throws Throwable
/* 3241:     */   {
/* 3242:2774 */     PagingInfo localPagingInfo1 = (PagingInfo)paramInputStream.read_value(PagingInfo.class);
/* 3243:     */     PagingInfo localPagingInfo2;
/* 3244:     */     try
/* 3245:     */     {
/* 3246:2777 */       localPagingInfo2 = this.target.getPagedBillPayments(localPagingInfo1);
/* 3247:     */     }
/* 3248:     */     catch (FFSException localFFSException)
/* 3249:     */     {
/* 3250:2779 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3251:2780 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3252:2781 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3253:2782 */       localOutputStream2.write_string(str);
/* 3254:2783 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3255:2784 */       return localOutputStream2;
/* 3256:     */     }
/* 3257:2786 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3258:2787 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3259:2788 */     localOutputStream1.write_value(localPagingInfo2, PagingInfo.class);
/* 3260:2789 */     return localOutputStream1;
/* 3261:     */   }
/* 3262:     */   
/* 3263:     */   private org.omg.CORBA.portable.OutputStream getValidTransferDateDue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3264:     */     throws Throwable
/* 3265:     */   {
/* 3266:2793 */     TransferInfo localTransferInfo = (TransferInfo)paramInputStream.read_value(TransferInfo.class);
/* 3267:2794 */     int i = this.target.getValidTransferDateDue(localTransferInfo);
/* 3268:2795 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 3269:2796 */     localOutputStream.write_long(i);
/* 3270:2797 */     return localOutputStream;
/* 3271:     */   }
/* 3272:     */   
/* 3273:     */   private org.omg.CORBA.portable.OutputStream getPagedCashCon(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3274:     */     throws Throwable
/* 3275:     */   {
/* 3276:2801 */     PagingInfo localPagingInfo1 = (PagingInfo)paramInputStream.read_value(PagingInfo.class);
/* 3277:     */     PagingInfo localPagingInfo2;
/* 3278:     */     try
/* 3279:     */     {
/* 3280:2804 */       localPagingInfo2 = this.target.getPagedCashCon(localPagingInfo1);
/* 3281:     */     }
/* 3282:     */     catch (FFSException localFFSException)
/* 3283:     */     {
/* 3284:2806 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3285:2807 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3286:2808 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3287:2809 */       localOutputStream2.write_string(str);
/* 3288:2810 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3289:2811 */       return localOutputStream2;
/* 3290:     */     }
/* 3291:2813 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3292:2814 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3293:2815 */     localOutputStream1.write_value(localPagingInfo2, PagingInfo.class);
/* 3294:2816 */     return localOutputStream1;
/* 3295:     */   }
/* 3296:     */   
/* 3297:     */   private org.omg.CORBA.portable.OutputStream getPayeeByListId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3298:     */     throws Throwable
/* 3299:     */   {
/* 3300:2820 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3301:2821 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 3302:     */     PayeeInfo localPayeeInfo;
/* 3303:     */     try
/* 3304:     */     {
/* 3305:2824 */       localPayeeInfo = this.target.getPayeeByListId(str1, str2);
/* 3306:     */     }
/* 3307:     */     catch (FFSException localFFSException)
/* 3308:     */     {
/* 3309:2826 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3310:2827 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3311:2828 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3312:2829 */       localOutputStream2.write_string(str3);
/* 3313:2830 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3314:2831 */       return localOutputStream2;
/* 3315:     */     }
/* 3316:2833 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3317:2834 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3318:2835 */     localOutputStream1.write_value(localPayeeInfo, PayeeInfo.class);
/* 3319:2836 */     return localOutputStream1;
/* 3320:     */   }
/* 3321:     */   
/* 3322:     */   private org.omg.CORBA.portable.OutputStream getAccountTypesMap(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3323:     */     throws Throwable
/* 3324:     */   {
/* 3325:     */     AccountTypesMap localAccountTypesMap;
/* 3326:     */     try
/* 3327:     */     {
/* 3328:2842 */       localAccountTypesMap = this.target.getAccountTypesMap();
/* 3329:     */     }
/* 3330:     */     catch (FFSException localFFSException)
/* 3331:     */     {
/* 3332:2844 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3333:2845 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3334:2846 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3335:2847 */       localOutputStream2.write_string(str);
/* 3336:2848 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3337:2849 */       return localOutputStream2;
/* 3338:     */     }
/* 3339:2851 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3340:2852 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3341:2853 */     localOutputStream1.write_value(localAccountTypesMap, AccountTypesMap.class);
/* 3342:2854 */     return localOutputStream1;
/* 3343:     */   }
/* 3344:     */   
/* 3345:     */   private org.omg.CORBA.portable.OutputStream modExtTransferAccountPrenoteStatus(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3346:     */     throws Throwable
/* 3347:     */   {
/* 3348:2858 */     ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramInputStream.read_value(ExtTransferAcctInfo.class);
/* 3349:2859 */     ExtTransferAcctInfo localExtTransferAcctInfo2 = this.target.modExtTransferAccountPrenoteStatus(localExtTransferAcctInfo1);
/* 3350:2860 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 3351:2861 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3352:2862 */     localOutputStream.write_value(localExtTransferAcctInfo2, ExtTransferAcctInfo.class);
/* 3353:2863 */     return localOutputStream;
/* 3354:     */   }
/* 3355:     */   
/* 3356:     */   private org.omg.CORBA.portable.OutputStream getBPWProperty(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3357:     */     throws Throwable
/* 3358:     */   {
/* 3359:2867 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3360:2868 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 3361:     */     String str3;
/* 3362:     */     try
/* 3363:     */     {
/* 3364:2871 */       str3 = this.target.getBPWProperty(str1, str2);
/* 3365:     */     }
/* 3366:     */     catch (FFSException localFFSException)
/* 3367:     */     {
/* 3368:2873 */       String str4 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3369:2874 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3370:2875 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3371:2876 */       localOutputStream2.write_string(str4);
/* 3372:2877 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3373:2878 */       return localOutputStream2;
/* 3374:     */     }
/* 3375:2880 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3376:2881 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3377:2882 */     localOutputStream1.write_value(str3, String.class);
/* 3378:2883 */     return localOutputStream1;
/* 3379:     */   }
/* 3380:     */   
/* 3381:     */   private org.omg.CORBA.portable.OutputStream addTransferBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3382:     */     throws Throwable
/* 3383:     */   {
/* 3384:2887 */     TransferBatchInfo localTransferBatchInfo1 = (TransferBatchInfo)paramInputStream.read_value(TransferBatchInfo.class);
/* 3385:     */     TransferBatchInfo localTransferBatchInfo2;
/* 3386:     */     try
/* 3387:     */     {
/* 3388:2890 */       localTransferBatchInfo2 = this.target.addTransferBatch(localTransferBatchInfo1);
/* 3389:     */     }
/* 3390:     */     catch (FFSException localFFSException)
/* 3391:     */     {
/* 3392:2892 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3393:2893 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3394:2894 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3395:2895 */       localOutputStream2.write_string(str);
/* 3396:2896 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3397:2897 */       return localOutputStream2;
/* 3398:     */     }
/* 3399:2899 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3400:2900 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3401:2901 */     localOutputStream1.write_value(localTransferBatchInfo2, TransferBatchInfo.class);
/* 3402:2902 */     return localOutputStream1;
/* 3403:     */   }
/* 3404:     */   
/* 3405:     */   private org.omg.CORBA.portable.OutputStream modifyTransferBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3406:     */     throws Throwable
/* 3407:     */   {
/* 3408:2906 */     TransferBatchInfo localTransferBatchInfo1 = (TransferBatchInfo)paramInputStream.read_value(TransferBatchInfo.class);
/* 3409:     */     TransferBatchInfo localTransferBatchInfo2;
/* 3410:     */     try
/* 3411:     */     {
/* 3412:2909 */       localTransferBatchInfo2 = this.target.modifyTransferBatch(localTransferBatchInfo1);
/* 3413:     */     }
/* 3414:     */     catch (FFSException localFFSException)
/* 3415:     */     {
/* 3416:2911 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3417:2912 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3418:2913 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3419:2914 */       localOutputStream2.write_string(str);
/* 3420:2915 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3421:2916 */       return localOutputStream2;
/* 3422:     */     }
/* 3423:2918 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3424:2919 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3425:2920 */     localOutputStream1.write_value(localTransferBatchInfo2, TransferBatchInfo.class);
/* 3426:2921 */     return localOutputStream1;
/* 3427:     */   }
/* 3428:     */   
/* 3429:     */   private org.omg.CORBA.portable.OutputStream cancelTransferBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3430:     */     throws Throwable
/* 3431:     */   {
/* 3432:2925 */     TransferBatchInfo localTransferBatchInfo1 = (TransferBatchInfo)paramInputStream.read_value(TransferBatchInfo.class);
/* 3433:     */     TransferBatchInfo localTransferBatchInfo2;
/* 3434:     */     try
/* 3435:     */     {
/* 3436:2928 */       localTransferBatchInfo2 = this.target.cancelTransferBatch(localTransferBatchInfo1);
/* 3437:     */     }
/* 3438:     */     catch (FFSException localFFSException)
/* 3439:     */     {
/* 3440:2930 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3441:2931 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3442:2932 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3443:2933 */       localOutputStream2.write_string(str);
/* 3444:2934 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3445:2935 */       return localOutputStream2;
/* 3446:     */     }
/* 3447:2937 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3448:2938 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3449:2939 */     localOutputStream1.write_value(localTransferBatchInfo2, TransferBatchInfo.class);
/* 3450:2940 */     return localOutputStream1;
/* 3451:     */   }
/* 3452:     */   
/* 3453:     */   private org.omg.CORBA.portable.OutputStream getTransferBatchById(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3454:     */     throws Throwable
/* 3455:     */   {
/* 3456:2944 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3457:     */     TransferBatchInfo localTransferBatchInfo;
/* 3458:     */     try
/* 3459:     */     {
/* 3460:2947 */       localTransferBatchInfo = this.target.getTransferBatchById(str1);
/* 3461:     */     }
/* 3462:     */     catch (FFSException localFFSException)
/* 3463:     */     {
/* 3464:2949 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3465:2950 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3466:2951 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3467:2952 */       localOutputStream2.write_string(str2);
/* 3468:2953 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3469:2954 */       return localOutputStream2;
/* 3470:     */     }
/* 3471:2956 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3472:2957 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3473:2958 */     localOutputStream1.write_value(localTransferBatchInfo, TransferBatchInfo.class);
/* 3474:2959 */     return localOutputStream1;
/* 3475:     */   }
/* 3476:     */   
/* 3477:     */   private org.omg.CORBA.portable.OutputStream accountHasPendingTransfers(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3478:     */     throws Throwable
/* 3479:     */   {
/* 3480:2963 */     AccountTransactions localAccountTransactions1 = (AccountTransactions)paramInputStream.read_value(AccountTransactions.class);
/* 3481:     */     AccountTransactions localAccountTransactions2;
/* 3482:     */     try
/* 3483:     */     {
/* 3484:2966 */       localAccountTransactions2 = this.target.accountHasPendingTransfers(localAccountTransactions1);
/* 3485:     */     }
/* 3486:     */     catch (FFSException localFFSException)
/* 3487:     */     {
/* 3488:2968 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3489:2969 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3490:2970 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3491:2971 */       localOutputStream2.write_string(str);
/* 3492:2972 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3493:2973 */       return localOutputStream2;
/* 3494:     */     }
/* 3495:2975 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3496:2976 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3497:2977 */     localOutputStream1.write_value(localAccountTransactions2, AccountTransactions.class);
/* 3498:2978 */     return localOutputStream1;
/* 3499:     */   }
/* 3500:     */   
/* 3501:     */   private org.omg.CORBA.portable.OutputStream addBillPayment(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3502:     */     throws Throwable
/* 3503:     */   {
/* 3504:2982 */     PmtInfo localPmtInfo1 = (PmtInfo)paramInputStream.read_value(PmtInfo.class);
/* 3505:     */     PmtInfo localPmtInfo2;
/* 3506:     */     try
/* 3507:     */     {
/* 3508:2985 */       localPmtInfo2 = this.target.addBillPayment(localPmtInfo1);
/* 3509:     */     }
/* 3510:     */     catch (FFSException localFFSException)
/* 3511:     */     {
/* 3512:2987 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3513:2988 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3514:2989 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3515:2990 */       localOutputStream2.write_string(str);
/* 3516:2991 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3517:2992 */       return localOutputStream2;
/* 3518:     */     }
/* 3519:2994 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3520:2995 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3521:2996 */     localOutputStream1.write_value(localPmtInfo2, PmtInfo.class);
/* 3522:2997 */     return localOutputStream1;
/* 3523:     */   }
/* 3524:     */   
/* 3525:     */   private org.omg.CORBA.portable.OutputStream modifyBillPayment(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3526:     */     throws Throwable
/* 3527:     */   {
/* 3528:3001 */     PmtInfo localPmtInfo1 = (PmtInfo)paramInputStream.read_value(PmtInfo.class);
/* 3529:     */     PmtInfo localPmtInfo2;
/* 3530:     */     try
/* 3531:     */     {
/* 3532:3004 */       localPmtInfo2 = this.target.modifyBillPayment(localPmtInfo1);
/* 3533:     */     }
/* 3534:     */     catch (FFSException localFFSException)
/* 3535:     */     {
/* 3536:3006 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3537:3007 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3538:3008 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3539:3009 */       localOutputStream2.write_string(str);
/* 3540:3010 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3541:3011 */       return localOutputStream2;
/* 3542:     */     }
/* 3543:3013 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3544:3014 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3545:3015 */     localOutputStream1.write_value(localPmtInfo2, PmtInfo.class);
/* 3546:3016 */     return localOutputStream1;
/* 3547:     */   }
/* 3548:     */   
/* 3549:     */   private org.omg.CORBA.portable.OutputStream deleteBillPayment(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3550:     */     throws Throwable
/* 3551:     */   {
/* 3552:3020 */     PmtInfo localPmtInfo1 = (PmtInfo)paramInputStream.read_value(PmtInfo.class);
/* 3553:     */     PmtInfo localPmtInfo2;
/* 3554:     */     try
/* 3555:     */     {
/* 3556:3023 */       localPmtInfo2 = this.target.deleteBillPayment(localPmtInfo1);
/* 3557:     */     }
/* 3558:     */     catch (FFSException localFFSException)
/* 3559:     */     {
/* 3560:3025 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3561:3026 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3562:3027 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3563:3028 */       localOutputStream2.write_string(str);
/* 3564:3029 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3565:3030 */       return localOutputStream2;
/* 3566:     */     }
/* 3567:3032 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3568:3033 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3569:3034 */     localOutputStream1.write_value(localPmtInfo2, PmtInfo.class);
/* 3570:3035 */     return localOutputStream1;
/* 3571:     */   }
/* 3572:     */   
/* 3573:     */   private org.omg.CORBA.portable.OutputStream getBillPaymentById(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3574:     */     throws Throwable
/* 3575:     */   {
/* 3576:3039 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3577:3040 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 3578:     */     PmtInfo localPmtInfo;
/* 3579:     */     try
/* 3580:     */     {
/* 3581:3043 */       localPmtInfo = this.target.getBillPaymentById(str1, str2);
/* 3582:     */     }
/* 3583:     */     catch (FFSException localFFSException)
/* 3584:     */     {
/* 3585:3045 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3586:3046 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3587:3047 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3588:3048 */       localOutputStream2.write_string(str3);
/* 3589:3049 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3590:3050 */       return localOutputStream2;
/* 3591:     */     }
/* 3592:3052 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3593:3053 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3594:3054 */     localOutputStream1.write_value(localPmtInfo, PmtInfo.class);
/* 3595:3055 */     return localOutputStream1;
/* 3596:     */   }
/* 3597:     */   
/* 3598:     */   private org.omg.CORBA.portable.OutputStream addPaymentBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3599:     */     throws Throwable
/* 3600:     */   {
/* 3601:3059 */     PaymentBatchInfo localPaymentBatchInfo1 = (PaymentBatchInfo)paramInputStream.read_value(PaymentBatchInfo.class);
/* 3602:     */     PaymentBatchInfo localPaymentBatchInfo2;
/* 3603:     */     try
/* 3604:     */     {
/* 3605:3062 */       localPaymentBatchInfo2 = this.target.addPaymentBatch(localPaymentBatchInfo1);
/* 3606:     */     }
/* 3607:     */     catch (FFSException localFFSException)
/* 3608:     */     {
/* 3609:3064 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3610:3065 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3611:3066 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3612:3067 */       localOutputStream2.write_string(str);
/* 3613:3068 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3614:3069 */       return localOutputStream2;
/* 3615:     */     }
/* 3616:3071 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3617:3072 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3618:3073 */     localOutputStream1.write_value(localPaymentBatchInfo2, PaymentBatchInfo.class);
/* 3619:3074 */     return localOutputStream1;
/* 3620:     */   }
/* 3621:     */   
/* 3622:     */   private org.omg.CORBA.portable.OutputStream modifyPaymentBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3623:     */     throws Throwable
/* 3624:     */   {
/* 3625:3078 */     PaymentBatchInfo localPaymentBatchInfo1 = (PaymentBatchInfo)paramInputStream.read_value(PaymentBatchInfo.class);
/* 3626:     */     PaymentBatchInfo localPaymentBatchInfo2;
/* 3627:     */     try
/* 3628:     */     {
/* 3629:3081 */       localPaymentBatchInfo2 = this.target.modifyPaymentBatch(localPaymentBatchInfo1);
/* 3630:     */     }
/* 3631:     */     catch (FFSException localFFSException)
/* 3632:     */     {
/* 3633:3083 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3634:3084 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3635:3085 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3636:3086 */       localOutputStream2.write_string(str);
/* 3637:3087 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3638:3088 */       return localOutputStream2;
/* 3639:     */     }
/* 3640:3090 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3641:3091 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3642:3092 */     localOutputStream1.write_value(localPaymentBatchInfo2, PaymentBatchInfo.class);
/* 3643:3093 */     return localOutputStream1;
/* 3644:     */   }
/* 3645:     */   
/* 3646:     */   private org.omg.CORBA.portable.OutputStream deletePaymentBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3647:     */     throws Throwable
/* 3648:     */   {
/* 3649:3097 */     PaymentBatchInfo localPaymentBatchInfo1 = (PaymentBatchInfo)paramInputStream.read_value(PaymentBatchInfo.class);
/* 3650:     */     PaymentBatchInfo localPaymentBatchInfo2;
/* 3651:     */     try
/* 3652:     */     {
/* 3653:3100 */       localPaymentBatchInfo2 = this.target.deletePaymentBatch(localPaymentBatchInfo1);
/* 3654:     */     }
/* 3655:     */     catch (FFSException localFFSException)
/* 3656:     */     {
/* 3657:3102 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3658:3103 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3659:3104 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3660:3105 */       localOutputStream2.write_string(str);
/* 3661:3106 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3662:3107 */       return localOutputStream2;
/* 3663:     */     }
/* 3664:3109 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3665:3110 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3666:3111 */     localOutputStream1.write_value(localPaymentBatchInfo2, PaymentBatchInfo.class);
/* 3667:3112 */     return localOutputStream1;
/* 3668:     */   }
/* 3669:     */   
/* 3670:     */   private org.omg.CORBA.portable.OutputStream getPaymentBatchById(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3671:     */     throws Throwable
/* 3672:     */   {
/* 3673:3116 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3674:     */     PaymentBatchInfo localPaymentBatchInfo;
/* 3675:     */     try
/* 3676:     */     {
/* 3677:3119 */       localPaymentBatchInfo = this.target.getPaymentBatchById(str1);
/* 3678:     */     }
/* 3679:     */     catch (FFSException localFFSException)
/* 3680:     */     {
/* 3681:3121 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3682:3122 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3683:3123 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3684:3124 */       localOutputStream2.write_string(str2);
/* 3685:3125 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3686:3126 */       return localOutputStream2;
/* 3687:     */     }
/* 3688:3128 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3689:3129 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3690:3130 */     localOutputStream1.write_value(localPaymentBatchInfo, PaymentBatchInfo.class);
/* 3691:3131 */     return localOutputStream1;
/* 3692:     */   }
/* 3693:     */   
/* 3694:     */   private org.omg.CORBA.portable.OutputStream getLastPayments(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3695:     */     throws Throwable
/* 3696:     */   {
/* 3697:3135 */     LastPaymentInfo localLastPaymentInfo1 = (LastPaymentInfo)paramInputStream.read_value(LastPaymentInfo.class);
/* 3698:     */     LastPaymentInfo localLastPaymentInfo2;
/* 3699:     */     try
/* 3700:     */     {
/* 3701:3138 */       localLastPaymentInfo2 = this.target.getLastPayments(localLastPaymentInfo1);
/* 3702:     */     }
/* 3703:     */     catch (FFSException localFFSException)
/* 3704:     */     {
/* 3705:3140 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3706:3141 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3707:3142 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3708:3143 */       localOutputStream2.write_string(str);
/* 3709:3144 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3710:3145 */       return localOutputStream2;
/* 3711:     */     }
/* 3712:3147 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3713:3148 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3714:3149 */     localOutputStream1.write_value(localLastPaymentInfo2, LastPaymentInfo.class);
/* 3715:3150 */     return localOutputStream1;
/* 3716:     */   }
/* 3717:     */   
/* 3718:     */   private org.omg.CORBA.portable.OutputStream getBankingDaysInRange(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3719:     */     throws Throwable
/* 3720:     */   {
/* 3721:3154 */     BankingDays localBankingDays1 = (BankingDays)paramInputStream.read_value(BankingDays.class);
/* 3722:3155 */     HashMap localHashMap = (HashMap)paramInputStream.read_value(HashMap.class);
/* 3723:     */     BankingDays localBankingDays2;
/* 3724:     */     try
/* 3725:     */     {
/* 3726:3158 */       localBankingDays2 = this.target.getBankingDaysInRange(localBankingDays1, localHashMap);
/* 3727:     */     }
/* 3728:     */     catch (FFSException localFFSException)
/* 3729:     */     {
/* 3730:3160 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3731:3161 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3732:3162 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3733:3163 */       localOutputStream2.write_string(str);
/* 3734:3164 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3735:3165 */       return localOutputStream2;
/* 3736:     */     }
/* 3737:3167 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3738:3168 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3739:3169 */     localOutputStream1.write_value(localBankingDays2, BankingDays.class);
/* 3740:3170 */     return localOutputStream1;
/* 3741:     */   }
/* 3742:     */   
/* 3743:     */   private org.omg.CORBA.portable.OutputStream addCustomerPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3744:     */     throws Throwable
/* 3745:     */   {
/* 3746:3174 */     CustomerPayeeInfo localCustomerPayeeInfo1 = (CustomerPayeeInfo)paramInputStream.read_value(CustomerPayeeInfo.class);
/* 3747:     */     CustomerPayeeInfo localCustomerPayeeInfo2;
/* 3748:     */     try
/* 3749:     */     {
/* 3750:3177 */       localCustomerPayeeInfo2 = this.target.addCustomerPayee(localCustomerPayeeInfo1);
/* 3751:     */     }
/* 3752:     */     catch (FFSException localFFSException)
/* 3753:     */     {
/* 3754:3179 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3755:3180 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3756:3181 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3757:3182 */       localOutputStream2.write_string(str);
/* 3758:3183 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3759:3184 */       return localOutputStream2;
/* 3760:     */     }
/* 3761:3186 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3762:3187 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3763:3188 */     localOutputStream1.write_value(localCustomerPayeeInfo2, CustomerPayeeInfo.class);
/* 3764:3189 */     return localOutputStream1;
/* 3765:     */   }
/* 3766:     */   
/* 3767:     */   private org.omg.CORBA.portable.OutputStream deleteCustomerPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3768:     */     throws Throwable
/* 3769:     */   {
/* 3770:3193 */     CustomerPayeeInfo localCustomerPayeeInfo1 = (CustomerPayeeInfo)paramInputStream.read_value(CustomerPayeeInfo.class);
/* 3771:     */     CustomerPayeeInfo localCustomerPayeeInfo2;
/* 3772:     */     try
/* 3773:     */     {
/* 3774:3196 */       localCustomerPayeeInfo2 = this.target.deleteCustomerPayee(localCustomerPayeeInfo1);
/* 3775:     */     }
/* 3776:     */     catch (FFSException localFFSException)
/* 3777:     */     {
/* 3778:3198 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3779:3199 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3780:3200 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3781:3201 */       localOutputStream2.write_string(str);
/* 3782:3202 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3783:3203 */       return localOutputStream2;
/* 3784:     */     }
/* 3785:3205 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3786:3206 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3787:3207 */     localOutputStream1.write_value(localCustomerPayeeInfo2, CustomerPayeeInfo.class);
/* 3788:3208 */     return localOutputStream1;
/* 3789:     */   }
/* 3790:     */   
/* 3791:     */   private org.omg.CORBA.portable.OutputStream updateCustomerPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3792:     */     throws Throwable
/* 3793:     */   {
/* 3794:3212 */     CustomerPayeeInfo localCustomerPayeeInfo1 = (CustomerPayeeInfo)paramInputStream.read_value(CustomerPayeeInfo.class);
/* 3795:     */     CustomerPayeeInfo localCustomerPayeeInfo2;
/* 3796:     */     try
/* 3797:     */     {
/* 3798:3215 */       localCustomerPayeeInfo2 = this.target.updateCustomerPayee(localCustomerPayeeInfo1);
/* 3799:     */     }
/* 3800:     */     catch (FFSException localFFSException)
/* 3801:     */     {
/* 3802:3217 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3803:3218 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3804:3219 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3805:3220 */       localOutputStream2.write_string(str);
/* 3806:3221 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3807:3222 */       return localOutputStream2;
/* 3808:     */     }
/* 3809:3224 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3810:3225 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3811:3226 */     localOutputStream1.write_value(localCustomerPayeeInfo2, CustomerPayeeInfo.class);
/* 3812:3227 */     return localOutputStream1;
/* 3813:     */   }
/* 3814:     */   
/* 3815:     */   private org.omg.CORBA.portable.OutputStream getCustomerPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3816:     */     throws Throwable
/* 3817:     */   {
/* 3818:3231 */     CustomerPayeeInfo localCustomerPayeeInfo = (CustomerPayeeInfo)paramInputStream.read_value(CustomerPayeeInfo.class);
/* 3819:     */     CustomerPayeeInfo[] arrayOfCustomerPayeeInfo;
/* 3820:     */     try
/* 3821:     */     {
/* 3822:3234 */       arrayOfCustomerPayeeInfo = this.target.getCustomerPayees(localCustomerPayeeInfo);
/* 3823:     */     }
/* 3824:     */     catch (FFSException localFFSException)
/* 3825:     */     {
/* 3826:3236 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3827:3237 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3828:3238 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3829:3239 */       localOutputStream2.write_string(str);
/* 3830:3240 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3831:3241 */       return localOutputStream2;
/* 3832:     */     }
/* 3833:3243 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3834:3244 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3835:3245 */     localOutputStream1.write_value(cast_array(arrayOfCustomerPayeeInfo), new CustomerPayeeInfo[0].getClass());
/* 3836:3246 */     return localOutputStream1;
/* 3837:     */   }
/* 3838:     */   
/* 3839:     */   private org.omg.CORBA.portable.OutputStream searchGlobalPayees(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3840:     */     throws Throwable
/* 3841:     */   {
/* 3842:3250 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 3843:3251 */     int i = paramInputStream.read_long();
/* 3844:     */     PayeeInfo[] arrayOfPayeeInfo;
/* 3845:     */     try
/* 3846:     */     {
/* 3847:3254 */       arrayOfPayeeInfo = this.target.searchGlobalPayees(localPayeeInfo, i);
/* 3848:     */     }
/* 3849:     */     catch (FFSException localFFSException)
/* 3850:     */     {
/* 3851:3256 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3852:3257 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3853:3258 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3854:3259 */       localOutputStream2.write_string(str);
/* 3855:3260 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3856:3261 */       return localOutputStream2;
/* 3857:     */     }
/* 3858:3263 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3859:3264 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3860:3265 */     localOutputStream1.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 3861:3266 */     return localOutputStream1;
/* 3862:     */   }
/* 3863:     */   
/* 3864:     */   private org.omg.CORBA.portable.OutputStream getGlobalPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3865:     */     throws Throwable
/* 3866:     */   {
/* 3867:3270 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 3868:     */     PayeeInfo localPayeeInfo;
/* 3869:     */     try
/* 3870:     */     {
/* 3871:3273 */       localPayeeInfo = this.target.getGlobalPayee(str1);
/* 3872:     */     }
/* 3873:     */     catch (FFSException localFFSException)
/* 3874:     */     {
/* 3875:3275 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 3876:3276 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 3877:3277 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 3878:3278 */       localOutputStream2.write_string(str2);
/* 3879:3279 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 3880:3280 */       return localOutputStream2;
/* 3881:     */     }
/* 3882:3282 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 3883:3283 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 3884:3284 */     localOutputStream1.write_value(localPayeeInfo, PayeeInfo.class);
/* 3885:3285 */     return localOutputStream1;
/* 3886:     */   }
/* 3887:     */   
/* 3888:     */   private org.omg.CORBA.portable.OutputStream processPmtTrnRslt(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 3889:     */     throws Throwable
/* 3890:     */   {
/* 3891:3289 */     PmtTrnRslt[] arrayOfPmtTrnRslt = (PmtTrnRslt[])paramInputStream.read_value(new PmtTrnRslt[0].getClass());
/* 3892:3290 */     this.target.processPmtTrnRslt(arrayOfPmtTrnRslt);
/* 3893:3291 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 3894:3292 */     return localOutputStream;
/* 3895:     */   }
/* 3896:     */   
/* 3897:     */   private Serializable cast_array(java.lang.Object paramObject)
/* 3898:     */   {
/* 3899:3299 */     return (Serializable)paramObject;
/* 3900:     */   }
/* 3901:     */ }


/* Location:           D:\drops\jd\jars\Deployed_BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices._EJSRemoteStatelessBPWServices_e88b14a1_Tie
 * JD-Core Version:    0.7.0.1
 */