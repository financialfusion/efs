/*    1:     */ package com.ffusion.ffs.bpw.BPWServices;
/*    2:     */ 
/*    3:     */ import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
/*    4:     */ import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
/*    5:     */ import com.ffusion.ffs.bpw.interfaces.BPWBankInfoSeqHelper;
/*    6:     */ import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
/*    7:     */ import com.ffusion.ffs.bpw.interfaces.BPWFIInfoSeqHelper;
/*    8:     */ import com.ffusion.ffs.bpw.interfaces.BPWHist;
/*    9:     */ import com.ffusion.ffs.bpw.interfaces.BPWHistHelper;
/*   10:     */ import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
/*   11:     */ import com.ffusion.ffs.bpw.interfaces.BankingDays;
/*   12:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
/*   13:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList;
/*   14:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
/*   15:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
/*   16:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList;
/*   18:     */ import com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo;
/*   19:     */ import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
/*   20:     */ import com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList;
/*   21:     */ import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
/*   22:     */ import com.ffusion.ffs.bpw.interfaces.CCLocationInfoList;
/*   23:     */ import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
/*   24:     */ import com.ffusion.ffs.bpw.interfaces.CustomerInfoSeqHelper;
/*   25:     */ import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
/*   26:     */ import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfoSeqHelper;
/*   27:     */ import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
/*   28:     */ import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
/*   29:     */ import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
/*   30:     */ import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
/*   31:     */ import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
/*   32:     */ import com.ffusion.ffs.bpw.interfaces.LastPaymentInfo;
/*   33:     */ import com.ffusion.ffs.bpw.interfaces.NonBusinessDay;
/*   34:     */ import com.ffusion.ffs.bpw.interfaces.NonBusinessDaySeqHelper;
/*   35:     */ import com.ffusion.ffs.bpw.interfaces.PagingInfo;
/*   36:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*   37:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfoSeqHelper;
/*   38:     */ import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
/*   39:     */ import com.ffusion.ffs.bpw.interfaces.PmtInfo;
/*   40:     */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
/*   41:     */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRsltSeqHelper;
/*   42:     */ import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo;
/*   43:     */ import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfoSeqHelper;
/*   44:     */ import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
/*   45:     */ import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
/*   46:     */ import com.ffusion.ffs.bpw.interfaces.RecTransferInfoSeqHelper;
/*   47:     */ import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
/*   48:     */ import com.ffusion.ffs.bpw.interfaces.RecWireInfoSeqHelper;
/*   49:     */ import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
/*   50:     */ import com.ffusion.ffs.bpw.interfaces.TransferInfo;
/*   51:     */ import com.ffusion.ffs.bpw.interfaces.TransferInfoSeqHelper;
/*   52:     */ import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
/*   53:     */ import com.ffusion.ffs.bpw.interfaces.WireBatchInfoSeqHelper;
/*   54:     */ import com.ffusion.ffs.bpw.interfaces.WireInfo;
/*   55:     */ import com.ffusion.ffs.bpw.interfaces.WireInfoSeqHelper;
/*   56:     */ import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
/*   57:     */ import com.ffusion.ffs.bpw.interfaces.WirePayeeInfoSeqHelper;
/*   58:     */ import com.ffusion.ffs.bpw.interfaces.WireReleaseInfo;
/*   59:     */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*   60:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   61:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   62:     */ import com.sybase.ejb.lwc.EJBContext;
/*   63:     */ import com.sybase.ejb.lwc.EJBObject;
/*   64:     */ import com.sybase.ejb.lwc.JavaComponent;
/*   65:     */ import com.sybase.ejb.lwc.Stub;
/*   66:     */ import com.sybase.jaguar.logger.Logger;
/*   67:     */ import java.rmi.RemoteException;
/*   68:     */ import java.util.HashMap;
/*   69:     */ import javax.ejb.EJBException;
/*   70:     */ 
/*   71:     */ public class _lwc_rs_BPWServices_BPWServicesBean
/*   72:     */   extends EJBObject
/*   73:     */   implements BPWServices
/*   74:     */ {
/*   75:  14 */   private static JavaComponent __component = JavaComponent.get("BPWServices/BPWServicesBean");
/*   76:     */   
/*   77:     */   public _lwc_rs_BPWServices_BPWServicesBean(Object paramObject)
/*   78:     */   {
/*   79:  19 */     super(__component);
/*   80:  20 */     this.key = paramObject;
/*   81:     */   }
/*   82:     */   
/*   83:     */   private EJBContext _create()
/*   84:     */     throws Exception
/*   85:     */   {
/*   86:  26 */     EJBContext localEJBContext = this._comp.getInstance();
/*   87:  27 */     BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*   88:  28 */     if (localEJBContext.debug) {
/*   89:  30 */       localEJBContext.logger.debug(localEJBContext.debugMsg("ejbCreate"));
/*   90:     */     }
/*   91:  32 */     localBPWServicesBean.ejbCreate();
/*   92:  33 */     return localEJBContext;
/*   93:     */   }
/*   94:     */   
/*   95:     */   public void disconnect()
/*   96:     */     throws RemoteException
/*   97:     */   {
/*   98:  39 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*   99:  40 */     EJBContext localEJBContext = null;
/*  100:     */     try
/*  101:     */     {
/*  102:  43 */       localEJBContext = this._comp.getPooledInstance();
/*  103:  44 */       if (localEJBContext == null) {
/*  104:  46 */         localEJBContext = _create();
/*  105:     */       }
/*  106:  48 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  107:  49 */       if (localEJBContext.debug) {
/*  108:  51 */         localEJBContext.logger.debug(localEJBContext.debugMsg("disconnect"));
/*  109:     */       }
/*  110:  54 */       localBPWServicesBean.disconnect();
/*  111:     */       
/*  112:  56 */       localEJBContext.returnToPool();
/*  113:     */     }
/*  114:     */     catch (Exception localException)
/*  115:     */     {
/*  116:  60 */       if (localEJBContext != null) {
/*  117:  62 */         localEJBContext.throwRemote(localException);
/*  118:     */       }
/*  119:  64 */       throw new EJBException(localException);
/*  120:     */     }
/*  121:     */     finally
/*  122:     */     {
/*  123:  68 */       this._comp.setCurrent(localJavaComponent);
/*  124:     */     }
/*  125:     */   }
/*  126:     */   
/*  127:     */   public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  128:     */     throws RemoteException
/*  129:     */   {
/*  130:  76 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  131:  77 */     EJBContext localEJBContext = null;
/*  132:     */     try
/*  133:     */     {
/*  134:  80 */       localEJBContext = this._comp.getPooledInstance();
/*  135:  81 */       if (localEJBContext == null) {
/*  136:  83 */         localEJBContext = _create();
/*  137:     */       }
/*  138:  85 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  139:  86 */       if (localEJBContext.debug) {
/*  140:  88 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCustomers"));
/*  141:     */       }
/*  142:  90 */       int j = localBPWServicesBean
/*  143:  91 */         .addCustomers(
/*  144:  92 */         CustomerInfoSeqHelper.clone(paramArrayOfCustomerInfo));
/*  145:     */       
/*  146:  94 */       localEJBContext.returnToPool();
/*  147:  95 */       return j;
/*  148:     */     }
/*  149:     */     catch (Exception localException)
/*  150:     */     {
/*  151:  99 */       if (localEJBContext != null) {
/*  152: 101 */         localEJBContext.throwRemote(localException);
/*  153:     */       }
/*  154: 103 */       throw new EJBException(localException);
/*  155:     */     }
/*  156:     */     finally
/*  157:     */     {
/*  158: 107 */       this._comp.setCurrent(localJavaComponent);
/*  159:     */     }
/*  160:     */   }
/*  161:     */   
/*  162:     */   public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  163:     */     throws RemoteException
/*  164:     */   {
/*  165: 115 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  166: 116 */     EJBContext localEJBContext = null;
/*  167:     */     try
/*  168:     */     {
/*  169: 119 */       localEJBContext = this._comp.getPooledInstance();
/*  170: 120 */       if (localEJBContext == null) {
/*  171: 122 */         localEJBContext = _create();
/*  172:     */       }
/*  173: 124 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  174: 125 */       if (localEJBContext.debug) {
/*  175: 127 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modifyCustomers"));
/*  176:     */       }
/*  177: 129 */       int j = localBPWServicesBean
/*  178: 130 */         .modifyCustomers(
/*  179: 131 */         CustomerInfoSeqHelper.clone(paramArrayOfCustomerInfo));
/*  180:     */       
/*  181: 133 */       localEJBContext.returnToPool();
/*  182: 134 */       return j;
/*  183:     */     }
/*  184:     */     catch (Exception localException)
/*  185:     */     {
/*  186: 138 */       if (localEJBContext != null) {
/*  187: 140 */         localEJBContext.throwRemote(localException);
/*  188:     */       }
/*  189: 142 */       throw new EJBException(localException);
/*  190:     */     }
/*  191:     */     finally
/*  192:     */     {
/*  193: 146 */       this._comp.setCurrent(localJavaComponent);
/*  194:     */     }
/*  195:     */   }
/*  196:     */   
/*  197:     */   public int deleteCustomers(String[] paramArrayOfString)
/*  198:     */     throws RemoteException
/*  199:     */   {
/*  200: 154 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  201: 155 */     EJBContext localEJBContext = null;
/*  202:     */     try
/*  203:     */     {
/*  204: 158 */       localEJBContext = this._comp.getPooledInstance();
/*  205: 159 */       if (localEJBContext == null) {
/*  206: 161 */         localEJBContext = _create();
/*  207:     */       }
/*  208: 163 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  209: 164 */       if (localEJBContext.debug) {
/*  210: 166 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomers"));
/*  211:     */       }
/*  212: 168 */       int j = localBPWServicesBean
/*  213: 169 */         .deleteCustomers(
/*  214: 170 */         StringSeqHelper.clone(paramArrayOfString));
/*  215:     */       
/*  216: 172 */       localEJBContext.returnToPool();
/*  217: 173 */       return j;
/*  218:     */     }
/*  219:     */     catch (Exception localException)
/*  220:     */     {
/*  221: 177 */       if (localEJBContext != null) {
/*  222: 179 */         localEJBContext.throwRemote(localException);
/*  223:     */       }
/*  224: 181 */       throw new EJBException(localException);
/*  225:     */     }
/*  226:     */     finally
/*  227:     */     {
/*  228: 185 */       this._comp.setCurrent(localJavaComponent);
/*  229:     */     }
/*  230:     */   }
/*  231:     */   
/*  232:     */   public int deleteCustomers(String[] paramArrayOfString, int paramInt)
/*  233:     */     throws RemoteException
/*  234:     */   {
/*  235: 194 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  236: 195 */     EJBContext localEJBContext = null;
/*  237:     */     try
/*  238:     */     {
/*  239: 198 */       localEJBContext = this._comp.getPooledInstance();
/*  240: 199 */       if (localEJBContext == null) {
/*  241: 201 */         localEJBContext = _create();
/*  242:     */       }
/*  243: 203 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  244: 204 */       if (localEJBContext.debug) {
/*  245: 206 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomers"));
/*  246:     */       }
/*  247: 208 */       int j = localBPWServicesBean
/*  248: 209 */         .deleteCustomers(
/*  249: 210 */         StringSeqHelper.clone(paramArrayOfString), 
/*  250: 211 */         paramInt);
/*  251:     */       
/*  252: 213 */       localEJBContext.returnToPool();
/*  253: 214 */       return j;
/*  254:     */     }
/*  255:     */     catch (Exception localException)
/*  256:     */     {
/*  257: 218 */       if (localEJBContext != null) {
/*  258: 220 */         localEJBContext.throwRemote(localException);
/*  259:     */       }
/*  260: 222 */       throw new EJBException(localException);
/*  261:     */     }
/*  262:     */     finally
/*  263:     */     {
/*  264: 226 */       this._comp.setCurrent(localJavaComponent);
/*  265:     */     }
/*  266:     */   }
/*  267:     */   
/*  268:     */   public int deactivateCustomers(String[] paramArrayOfString)
/*  269:     */     throws RemoteException
/*  270:     */   {
/*  271: 234 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  272: 235 */     EJBContext localEJBContext = null;
/*  273:     */     try
/*  274:     */     {
/*  275: 238 */       localEJBContext = this._comp.getPooledInstance();
/*  276: 239 */       if (localEJBContext == null) {
/*  277: 241 */         localEJBContext = _create();
/*  278:     */       }
/*  279: 243 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  280: 244 */       if (localEJBContext.debug) {
/*  281: 246 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deactivateCustomers"));
/*  282:     */       }
/*  283: 248 */       int j = localBPWServicesBean
/*  284: 249 */         .deactivateCustomers(
/*  285: 250 */         StringSeqHelper.clone(paramArrayOfString));
/*  286:     */       
/*  287: 252 */       localEJBContext.returnToPool();
/*  288: 253 */       return j;
/*  289:     */     }
/*  290:     */     catch (Exception localException)
/*  291:     */     {
/*  292: 257 */       if (localEJBContext != null) {
/*  293: 259 */         localEJBContext.throwRemote(localException);
/*  294:     */       }
/*  295: 261 */       throw new EJBException(localException);
/*  296:     */     }
/*  297:     */     finally
/*  298:     */     {
/*  299: 265 */       this._comp.setCurrent(localJavaComponent);
/*  300:     */     }
/*  301:     */   }
/*  302:     */   
/*  303:     */   public int activateCustomers(String[] paramArrayOfString)
/*  304:     */     throws RemoteException
/*  305:     */   {
/*  306: 273 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  307: 274 */     EJBContext localEJBContext = null;
/*  308:     */     try
/*  309:     */     {
/*  310: 277 */       localEJBContext = this._comp.getPooledInstance();
/*  311: 278 */       if (localEJBContext == null) {
/*  312: 280 */         localEJBContext = _create();
/*  313:     */       }
/*  314: 282 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  315: 283 */       if (localEJBContext.debug) {
/*  316: 285 */         localEJBContext.logger.debug(localEJBContext.debugMsg("activateCustomers"));
/*  317:     */       }
/*  318: 287 */       int j = localBPWServicesBean
/*  319: 288 */         .activateCustomers(
/*  320: 289 */         StringSeqHelper.clone(paramArrayOfString));
/*  321:     */       
/*  322: 291 */       localEJBContext.returnToPool();
/*  323: 292 */       return j;
/*  324:     */     }
/*  325:     */     catch (Exception localException)
/*  326:     */     {
/*  327: 296 */       if (localEJBContext != null) {
/*  328: 298 */         localEJBContext.throwRemote(localException);
/*  329:     */       }
/*  330: 300 */       throw new EJBException(localException);
/*  331:     */     }
/*  332:     */     finally
/*  333:     */     {
/*  334: 304 */       this._comp.setCurrent(localJavaComponent);
/*  335:     */     }
/*  336:     */   }
/*  337:     */   
/*  338:     */   public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
/*  339:     */     throws RemoteException
/*  340:     */   {
/*  341: 312 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  342: 313 */     EJBContext localEJBContext = null;
/*  343:     */     try
/*  344:     */     {
/*  345: 316 */       localEJBContext = this._comp.getPooledInstance();
/*  346: 317 */       if (localEJBContext == null) {
/*  347: 319 */         localEJBContext = _create();
/*  348:     */       }
/*  349: 321 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  350: 322 */       if (localEJBContext.debug) {
/*  351: 324 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomersInfo"));
/*  352:     */       }
/*  353: 326 */       CustomerInfo[] arrayOfCustomerInfo2 = localBPWServicesBean
/*  354: 327 */         .getCustomersInfo(
/*  355: 328 */         StringSeqHelper.clone(paramArrayOfString));
/*  356:     */       
/*  357: 330 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  358: 331 */       localEJBContext.returnToPool();
/*  359: 332 */       return arrayOfCustomerInfo2;
/*  360:     */     }
/*  361:     */     catch (Exception localException)
/*  362:     */     {
/*  363: 336 */       if (localEJBContext != null) {
/*  364: 338 */         localEJBContext.throwRemote(localException);
/*  365:     */       }
/*  366: 340 */       throw new EJBException(localException);
/*  367:     */     }
/*  368:     */     finally
/*  369:     */     {
/*  370: 344 */       this._comp.setCurrent(localJavaComponent);
/*  371:     */     }
/*  372:     */   }
/*  373:     */   
/*  374:     */   public CustomerInfo[] getCustomerByType(String paramString)
/*  375:     */     throws RemoteException
/*  376:     */   {
/*  377: 352 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  378: 353 */     EJBContext localEJBContext = null;
/*  379:     */     try
/*  380:     */     {
/*  381: 356 */       localEJBContext = this._comp.getPooledInstance();
/*  382: 357 */       if (localEJBContext == null) {
/*  383: 359 */         localEJBContext = _create();
/*  384:     */       }
/*  385: 361 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  386: 362 */       if (localEJBContext.debug) {
/*  387: 364 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByType"));
/*  388:     */       }
/*  389: 366 */       CustomerInfo[] arrayOfCustomerInfo2 = localBPWServicesBean
/*  390: 367 */         .getCustomerByType(
/*  391: 368 */         paramString);
/*  392:     */       
/*  393: 370 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  394: 371 */       localEJBContext.returnToPool();
/*  395: 372 */       return arrayOfCustomerInfo2;
/*  396:     */     }
/*  397:     */     catch (Exception localException)
/*  398:     */     {
/*  399: 376 */       if (localEJBContext != null) {
/*  400: 378 */         localEJBContext.throwRemote(localException);
/*  401:     */       }
/*  402: 380 */       throw new EJBException(localException);
/*  403:     */     }
/*  404:     */     finally
/*  405:     */     {
/*  406: 384 */       this._comp.setCurrent(localJavaComponent);
/*  407:     */     }
/*  408:     */   }
/*  409:     */   
/*  410:     */   public CustomerInfo[] getCustomerByFI(String paramString)
/*  411:     */     throws RemoteException
/*  412:     */   {
/*  413: 392 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  414: 393 */     EJBContext localEJBContext = null;
/*  415:     */     try
/*  416:     */     {
/*  417: 396 */       localEJBContext = this._comp.getPooledInstance();
/*  418: 397 */       if (localEJBContext == null) {
/*  419: 399 */         localEJBContext = _create();
/*  420:     */       }
/*  421: 401 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  422: 402 */       if (localEJBContext.debug) {
/*  423: 404 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByFI"));
/*  424:     */       }
/*  425: 406 */       CustomerInfo[] arrayOfCustomerInfo2 = localBPWServicesBean
/*  426: 407 */         .getCustomerByFI(
/*  427: 408 */         paramString);
/*  428:     */       
/*  429: 410 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  430: 411 */       localEJBContext.returnToPool();
/*  431: 412 */       return arrayOfCustomerInfo2;
/*  432:     */     }
/*  433:     */     catch (Exception localException)
/*  434:     */     {
/*  435: 416 */       if (localEJBContext != null) {
/*  436: 418 */         localEJBContext.throwRemote(localException);
/*  437:     */       }
/*  438: 420 */       throw new EJBException(localException);
/*  439:     */     }
/*  440:     */     finally
/*  441:     */     {
/*  442: 424 */       this._comp.setCurrent(localJavaComponent);
/*  443:     */     }
/*  444:     */   }
/*  445:     */   
/*  446:     */   public CustomerInfo[] getCustomerByCategory(String paramString)
/*  447:     */     throws RemoteException
/*  448:     */   {
/*  449: 432 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  450: 433 */     EJBContext localEJBContext = null;
/*  451:     */     try
/*  452:     */     {
/*  453: 436 */       localEJBContext = this._comp.getPooledInstance();
/*  454: 437 */       if (localEJBContext == null) {
/*  455: 439 */         localEJBContext = _create();
/*  456:     */       }
/*  457: 441 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  458: 442 */       if (localEJBContext.debug) {
/*  459: 444 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByCategory"));
/*  460:     */       }
/*  461: 446 */       CustomerInfo[] arrayOfCustomerInfo2 = localBPWServicesBean
/*  462: 447 */         .getCustomerByCategory(
/*  463: 448 */         paramString);
/*  464:     */       
/*  465: 450 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  466: 451 */       localEJBContext.returnToPool();
/*  467: 452 */       return arrayOfCustomerInfo2;
/*  468:     */     }
/*  469:     */     catch (Exception localException)
/*  470:     */     {
/*  471: 456 */       if (localEJBContext != null) {
/*  472: 458 */         localEJBContext.throwRemote(localException);
/*  473:     */       }
/*  474: 460 */       throw new EJBException(localException);
/*  475:     */     }
/*  476:     */     finally
/*  477:     */     {
/*  478: 464 */       this._comp.setCurrent(localJavaComponent);
/*  479:     */     }
/*  480:     */   }
/*  481:     */   
/*  482:     */   public CustomerInfo[] getCustomerByGroup(String paramString)
/*  483:     */     throws RemoteException
/*  484:     */   {
/*  485: 472 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  486: 473 */     EJBContext localEJBContext = null;
/*  487:     */     try
/*  488:     */     {
/*  489: 476 */       localEJBContext = this._comp.getPooledInstance();
/*  490: 477 */       if (localEJBContext == null) {
/*  491: 479 */         localEJBContext = _create();
/*  492:     */       }
/*  493: 481 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  494: 482 */       if (localEJBContext.debug) {
/*  495: 484 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByGroup"));
/*  496:     */       }
/*  497: 486 */       CustomerInfo[] arrayOfCustomerInfo2 = localBPWServicesBean
/*  498: 487 */         .getCustomerByGroup(
/*  499: 488 */         paramString);
/*  500:     */       
/*  501: 490 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  502: 491 */       localEJBContext.returnToPool();
/*  503: 492 */       return arrayOfCustomerInfo2;
/*  504:     */     }
/*  505:     */     catch (Exception localException)
/*  506:     */     {
/*  507: 496 */       if (localEJBContext != null) {
/*  508: 498 */         localEJBContext.throwRemote(localException);
/*  509:     */       }
/*  510: 500 */       throw new EJBException(localException);
/*  511:     */     }
/*  512:     */     finally
/*  513:     */     {
/*  514: 504 */       this._comp.setCurrent(localJavaComponent);
/*  515:     */     }
/*  516:     */   }
/*  517:     */   
/*  518:     */   public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
/*  519:     */     throws RemoteException
/*  520:     */   {
/*  521: 513 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  522: 514 */     EJBContext localEJBContext = null;
/*  523:     */     try
/*  524:     */     {
/*  525: 517 */       localEJBContext = this._comp.getPooledInstance();
/*  526: 518 */       if (localEJBContext == null) {
/*  527: 520 */         localEJBContext = _create();
/*  528:     */       }
/*  529: 522 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  530: 523 */       if (localEJBContext.debug) {
/*  531: 525 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByTypeAndFI"));
/*  532:     */       }
/*  533: 527 */       CustomerInfo[] arrayOfCustomerInfo2 = localBPWServicesBean
/*  534: 528 */         .getCustomerByTypeAndFI(
/*  535: 529 */         paramString1, 
/*  536: 530 */         paramString2);
/*  537:     */       
/*  538: 532 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  539: 533 */       localEJBContext.returnToPool();
/*  540: 534 */       return arrayOfCustomerInfo2;
/*  541:     */     }
/*  542:     */     catch (Exception localException)
/*  543:     */     {
/*  544: 538 */       if (localEJBContext != null) {
/*  545: 540 */         localEJBContext.throwRemote(localException);
/*  546:     */       }
/*  547: 542 */       throw new EJBException(localException);
/*  548:     */     }
/*  549:     */     finally
/*  550:     */     {
/*  551: 546 */       this._comp.setCurrent(localJavaComponent);
/*  552:     */     }
/*  553:     */   }
/*  554:     */   
/*  555:     */   public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
/*  556:     */     throws RemoteException
/*  557:     */   {
/*  558: 555 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  559: 556 */     EJBContext localEJBContext = null;
/*  560:     */     try
/*  561:     */     {
/*  562: 559 */       localEJBContext = this._comp.getPooledInstance();
/*  563: 560 */       if (localEJBContext == null) {
/*  564: 562 */         localEJBContext = _create();
/*  565:     */       }
/*  566: 564 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  567: 565 */       if (localEJBContext.debug) {
/*  568: 567 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByCategoryAndFI"));
/*  569:     */       }
/*  570: 569 */       CustomerInfo[] arrayOfCustomerInfo2 = localBPWServicesBean
/*  571: 570 */         .getCustomerByCategoryAndFI(
/*  572: 571 */         paramString1, 
/*  573: 572 */         paramString2);
/*  574:     */       
/*  575: 574 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  576: 575 */       localEJBContext.returnToPool();
/*  577: 576 */       return arrayOfCustomerInfo2;
/*  578:     */     }
/*  579:     */     catch (Exception localException)
/*  580:     */     {
/*  581: 580 */       if (localEJBContext != null) {
/*  582: 582 */         localEJBContext.throwRemote(localException);
/*  583:     */       }
/*  584: 584 */       throw new EJBException(localException);
/*  585:     */     }
/*  586:     */     finally
/*  587:     */     {
/*  588: 588 */       this._comp.setCurrent(localJavaComponent);
/*  589:     */     }
/*  590:     */   }
/*  591:     */   
/*  592:     */   public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
/*  593:     */     throws RemoteException
/*  594:     */   {
/*  595: 597 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  596: 598 */     EJBContext localEJBContext = null;
/*  597:     */     try
/*  598:     */     {
/*  599: 601 */       localEJBContext = this._comp.getPooledInstance();
/*  600: 602 */       if (localEJBContext == null) {
/*  601: 604 */         localEJBContext = _create();
/*  602:     */       }
/*  603: 606 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  604: 607 */       if (localEJBContext.debug) {
/*  605: 609 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByGroupAndFI"));
/*  606:     */       }
/*  607: 611 */       CustomerInfo[] arrayOfCustomerInfo2 = localBPWServicesBean
/*  608: 612 */         .getCustomerByGroupAndFI(
/*  609: 613 */         paramString1, 
/*  610: 614 */         paramString2);
/*  611:     */       
/*  612: 616 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  613: 617 */       localEJBContext.returnToPool();
/*  614: 618 */       return arrayOfCustomerInfo2;
/*  615:     */     }
/*  616:     */     catch (Exception localException)
/*  617:     */     {
/*  618: 622 */       if (localEJBContext != null) {
/*  619: 624 */         localEJBContext.throwRemote(localException);
/*  620:     */       }
/*  621: 626 */       throw new EJBException(localException);
/*  622:     */     }
/*  623:     */     finally
/*  624:     */     {
/*  625: 630 */       this._comp.setCurrent(localJavaComponent);
/*  626:     */     }
/*  627:     */   }
/*  628:     */   
/*  629:     */   public RPPSBillerInfo[] getRPPSBillerInfoByFIRPPSId(String paramString)
/*  630:     */     throws RemoteException, FFSException
/*  631:     */   {
/*  632: 638 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  633: 639 */     EJBContext localEJBContext = null;
/*  634:     */     try
/*  635:     */     {
/*  636: 642 */       localEJBContext = this._comp.getPooledInstance();
/*  637: 643 */       if (localEJBContext == null) {
/*  638: 645 */         localEJBContext = _create();
/*  639:     */       }
/*  640: 647 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  641: 648 */       if (localEJBContext.debug) {
/*  642: 650 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRPPSBillerInfoByFIRPPSId"));
/*  643:     */       }
/*  644: 652 */       RPPSBillerInfo[] arrayOfRPPSBillerInfo2 = localBPWServicesBean
/*  645: 653 */         .getRPPSBillerInfoByFIRPPSId(
/*  646: 654 */         paramString);
/*  647:     */       
/*  648: 656 */       arrayOfRPPSBillerInfo2 = RPPSBillerInfoSeqHelper.clone(arrayOfRPPSBillerInfo2);
/*  649: 657 */       localEJBContext.returnToPool();
/*  650: 658 */       return arrayOfRPPSBillerInfo2;
/*  651:     */     }
/*  652:     */     catch (Exception localException)
/*  653:     */     {
/*  654: 662 */       if (localEJBContext != null)
/*  655:     */       {
/*  656: 664 */         if ((localException instanceof FFSException)) {
/*  657: 666 */           throw ((FFSException)EJBContext.clone(localException));
/*  658:     */         }
/*  659: 668 */         localEJBContext.throwRemote(localException);
/*  660:     */       }
/*  661: 670 */       throw new EJBException(localException);
/*  662:     */     }
/*  663:     */     finally
/*  664:     */     {
/*  665: 674 */       this._comp.setCurrent(localJavaComponent);
/*  666:     */     }
/*  667:     */   }
/*  668:     */   
/*  669:     */   public RPPSBillerInfo getRPPSBillerInfoByFIAndBillerRPPSId(String paramString1, String paramString2)
/*  670:     */     throws RemoteException, FFSException
/*  671:     */   {
/*  672: 683 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  673: 684 */     EJBContext localEJBContext = null;
/*  674:     */     try
/*  675:     */     {
/*  676: 687 */       localEJBContext = this._comp.getPooledInstance();
/*  677: 688 */       if (localEJBContext == null) {
/*  678: 690 */         localEJBContext = _create();
/*  679:     */       }
/*  680: 692 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  681: 693 */       if (localEJBContext.debug) {
/*  682: 695 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRPPSBillerInfoByFIAndBillerRPPSId"));
/*  683:     */       }
/*  684: 697 */       RPPSBillerInfo localRPPSBillerInfo2 = localBPWServicesBean
/*  685: 698 */         .getRPPSBillerInfoByFIAndBillerRPPSId(
/*  686: 699 */         paramString1, 
/*  687: 700 */         paramString2);
/*  688:     */       
/*  689: 702 */       localRPPSBillerInfo2 = (RPPSBillerInfo)EJBContext.clone(localRPPSBillerInfo2);
/*  690: 703 */       localEJBContext.returnToPool();
/*  691: 704 */       return localRPPSBillerInfo2;
/*  692:     */     }
/*  693:     */     catch (Exception localException)
/*  694:     */     {
/*  695: 708 */       if (localEJBContext != null)
/*  696:     */       {
/*  697: 710 */         if ((localException instanceof FFSException)) {
/*  698: 712 */           throw ((FFSException)EJBContext.clone(localException));
/*  699:     */         }
/*  700: 714 */         localEJBContext.throwRemote(localException);
/*  701:     */       }
/*  702: 716 */       throw new EJBException(localException);
/*  703:     */     }
/*  704:     */     finally
/*  705:     */     {
/*  706: 720 */       this._comp.setCurrent(localJavaComponent);
/*  707:     */     }
/*  708:     */   }
/*  709:     */   
/*  710:     */   public WireInfo addWireTransfer(WireInfo paramWireInfo)
/*  711:     */     throws RemoteException
/*  712:     */   {
/*  713: 728 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  714: 729 */     EJBContext localEJBContext = null;
/*  715:     */     try
/*  716:     */     {
/*  717: 732 */       localEJBContext = this._comp.getPooledInstance();
/*  718: 733 */       if (localEJBContext == null) {
/*  719: 735 */         localEJBContext = _create();
/*  720:     */       }
/*  721: 737 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  722: 738 */       if (localEJBContext.debug) {
/*  723: 740 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addWireTransfer"));
/*  724:     */       }
/*  725: 742 */       WireInfo localWireInfo2 = localBPWServicesBean
/*  726: 743 */         .addWireTransfer(
/*  727: 744 */         (WireInfo)EJBContext.clone(paramWireInfo));
/*  728:     */       
/*  729: 746 */       localWireInfo2 = (WireInfo)EJBContext.clone(localWireInfo2);
/*  730: 747 */       localEJBContext.returnToPool();
/*  731: 748 */       return localWireInfo2;
/*  732:     */     }
/*  733:     */     catch (Exception localException)
/*  734:     */     {
/*  735: 752 */       if (localEJBContext != null) {
/*  736: 754 */         localEJBContext.throwRemote(localException);
/*  737:     */       }
/*  738: 756 */       throw new EJBException(localException);
/*  739:     */     }
/*  740:     */     finally
/*  741:     */     {
/*  742: 760 */       this._comp.setCurrent(localJavaComponent);
/*  743:     */     }
/*  744:     */   }
/*  745:     */   
/*  746:     */   public WireInfo modWireTransfer(WireInfo paramWireInfo)
/*  747:     */     throws RemoteException
/*  748:     */   {
/*  749: 768 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  750: 769 */     EJBContext localEJBContext = null;
/*  751:     */     try
/*  752:     */     {
/*  753: 772 */       localEJBContext = this._comp.getPooledInstance();
/*  754: 773 */       if (localEJBContext == null) {
/*  755: 775 */         localEJBContext = _create();
/*  756:     */       }
/*  757: 777 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  758: 778 */       if (localEJBContext.debug) {
/*  759: 780 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modWireTransfer"));
/*  760:     */       }
/*  761: 782 */       WireInfo localWireInfo2 = localBPWServicesBean
/*  762: 783 */         .modWireTransfer(
/*  763: 784 */         (WireInfo)EJBContext.clone(paramWireInfo));
/*  764:     */       
/*  765: 786 */       localWireInfo2 = (WireInfo)EJBContext.clone(localWireInfo2);
/*  766: 787 */       localEJBContext.returnToPool();
/*  767: 788 */       return localWireInfo2;
/*  768:     */     }
/*  769:     */     catch (Exception localException)
/*  770:     */     {
/*  771: 792 */       if (localEJBContext != null) {
/*  772: 794 */         localEJBContext.throwRemote(localException);
/*  773:     */       }
/*  774: 796 */       throw new EJBException(localException);
/*  775:     */     }
/*  776:     */     finally
/*  777:     */     {
/*  778: 800 */       this._comp.setCurrent(localJavaComponent);
/*  779:     */     }
/*  780:     */   }
/*  781:     */   
/*  782:     */   public WireInfo cancWireTransfer(WireInfo paramWireInfo)
/*  783:     */     throws RemoteException
/*  784:     */   {
/*  785: 808 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  786: 809 */     EJBContext localEJBContext = null;
/*  787:     */     try
/*  788:     */     {
/*  789: 812 */       localEJBContext = this._comp.getPooledInstance();
/*  790: 813 */       if (localEJBContext == null) {
/*  791: 815 */         localEJBContext = _create();
/*  792:     */       }
/*  793: 817 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  794: 818 */       if (localEJBContext.debug) {
/*  795: 820 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancWireTransfer"));
/*  796:     */       }
/*  797: 822 */       WireInfo localWireInfo2 = localBPWServicesBean
/*  798: 823 */         .cancWireTransfer(
/*  799: 824 */         (WireInfo)EJBContext.clone(paramWireInfo));
/*  800:     */       
/*  801: 826 */       localWireInfo2 = (WireInfo)EJBContext.clone(localWireInfo2);
/*  802: 827 */       localEJBContext.returnToPool();
/*  803: 828 */       return localWireInfo2;
/*  804:     */     }
/*  805:     */     catch (Exception localException)
/*  806:     */     {
/*  807: 832 */       if (localEJBContext != null) {
/*  808: 834 */         localEJBContext.throwRemote(localException);
/*  809:     */       }
/*  810: 836 */       throw new EJBException(localException);
/*  811:     */     }
/*  812:     */     finally
/*  813:     */     {
/*  814: 840 */       this._comp.setCurrent(localJavaComponent);
/*  815:     */     }
/*  816:     */   }
/*  817:     */   
/*  818:     */   public WireInfo getWireTransferById(String paramString)
/*  819:     */     throws RemoteException
/*  820:     */   {
/*  821: 848 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  822: 849 */     EJBContext localEJBContext = null;
/*  823:     */     try
/*  824:     */     {
/*  825: 852 */       localEJBContext = this._comp.getPooledInstance();
/*  826: 853 */       if (localEJBContext == null) {
/*  827: 855 */         localEJBContext = _create();
/*  828:     */       }
/*  829: 857 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  830: 858 */       if (localEJBContext.debug) {
/*  831: 860 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireTransferById"));
/*  832:     */       }
/*  833: 862 */       WireInfo localWireInfo2 = localBPWServicesBean
/*  834: 863 */         .getWireTransferById(
/*  835: 864 */         paramString);
/*  836:     */       
/*  837: 866 */       localWireInfo2 = (WireInfo)EJBContext.clone(localWireInfo2);
/*  838: 867 */       localEJBContext.returnToPool();
/*  839: 868 */       return localWireInfo2;
/*  840:     */     }
/*  841:     */     catch (Exception localException)
/*  842:     */     {
/*  843: 872 */       if (localEJBContext != null) {
/*  844: 874 */         localEJBContext.throwRemote(localException);
/*  845:     */       }
/*  846: 876 */       throw new EJBException(localException);
/*  847:     */     }
/*  848:     */     finally
/*  849:     */     {
/*  850: 880 */       this._comp.setCurrent(localJavaComponent);
/*  851:     */     }
/*  852:     */   }
/*  853:     */   
/*  854:     */   public WireInfo getWireTransfer(String paramString)
/*  855:     */     throws RemoteException
/*  856:     */   {
/*  857: 888 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  858: 889 */     EJBContext localEJBContext = null;
/*  859:     */     try
/*  860:     */     {
/*  861: 892 */       localEJBContext = this._comp.getPooledInstance();
/*  862: 893 */       if (localEJBContext == null) {
/*  863: 895 */         localEJBContext = _create();
/*  864:     */       }
/*  865: 897 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  866: 898 */       if (localEJBContext.debug) {
/*  867: 900 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireTransfer"));
/*  868:     */       }
/*  869: 902 */       WireInfo localWireInfo2 = localBPWServicesBean
/*  870: 903 */         .getWireTransfer(
/*  871: 904 */         paramString);
/*  872:     */       
/*  873: 906 */       localWireInfo2 = (WireInfo)EJBContext.clone(localWireInfo2);
/*  874: 907 */       localEJBContext.returnToPool();
/*  875: 908 */       return localWireInfo2;
/*  876:     */     }
/*  877:     */     catch (Exception localException)
/*  878:     */     {
/*  879: 912 */       if (localEJBContext != null) {
/*  880: 914 */         localEJBContext.throwRemote(localException);
/*  881:     */       }
/*  882: 916 */       throw new EJBException(localException);
/*  883:     */     }
/*  884:     */     finally
/*  885:     */     {
/*  886: 920 */       this._comp.setCurrent(localJavaComponent);
/*  887:     */     }
/*  888:     */   }
/*  889:     */   
/*  890:     */   public WireInfo[] getWireTransfers(String[] paramArrayOfString)
/*  891:     */     throws RemoteException
/*  892:     */   {
/*  893: 928 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  894: 929 */     EJBContext localEJBContext = null;
/*  895:     */     try
/*  896:     */     {
/*  897: 932 */       localEJBContext = this._comp.getPooledInstance();
/*  898: 933 */       if (localEJBContext == null) {
/*  899: 935 */         localEJBContext = _create();
/*  900:     */       }
/*  901: 937 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  902: 938 */       if (localEJBContext.debug) {
/*  903: 940 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireTransfers"));
/*  904:     */       }
/*  905: 942 */       WireInfo[] arrayOfWireInfo2 = localBPWServicesBean
/*  906: 943 */         .getWireTransfers(
/*  907: 944 */         StringSeqHelper.clone(paramArrayOfString));
/*  908:     */       
/*  909: 946 */       arrayOfWireInfo2 = WireInfoSeqHelper.clone(arrayOfWireInfo2);
/*  910: 947 */       localEJBContext.returnToPool();
/*  911: 948 */       return arrayOfWireInfo2;
/*  912:     */     }
/*  913:     */     catch (Exception localException)
/*  914:     */     {
/*  915: 952 */       if (localEJBContext != null) {
/*  916: 954 */         localEJBContext.throwRemote(localException);
/*  917:     */       }
/*  918: 956 */       throw new EJBException(localException);
/*  919:     */     }
/*  920:     */     finally
/*  921:     */     {
/*  922: 960 */       this._comp.setCurrent(localJavaComponent);
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   public BPWHist getDuplicateWires(WireInfo paramWireInfo)
/*  927:     */     throws RemoteException
/*  928:     */   {
/*  929: 968 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  930: 969 */     EJBContext localEJBContext = null;
/*  931:     */     try
/*  932:     */     {
/*  933: 972 */       localEJBContext = this._comp.getPooledInstance();
/*  934: 973 */       if (localEJBContext == null) {
/*  935: 975 */         localEJBContext = _create();
/*  936:     */       }
/*  937: 977 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  938: 978 */       if (localEJBContext.debug) {
/*  939: 980 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getDuplicateWires"));
/*  940:     */       }
/*  941: 982 */       BPWHist localBPWHist2 = localBPWServicesBean
/*  942: 983 */         .getDuplicateWires(
/*  943: 984 */         (WireInfo)EJBContext.clone(paramWireInfo));
/*  944:     */       
/*  945: 986 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/*  946: 987 */       localEJBContext.returnToPool();
/*  947: 988 */       return localBPWHist2;
/*  948:     */     }
/*  949:     */     catch (Exception localException)
/*  950:     */     {
/*  951: 992 */       if (localEJBContext != null) {
/*  952: 994 */         localEJBContext.throwRemote(localException);
/*  953:     */       }
/*  954: 996 */       throw new EJBException(localException);
/*  955:     */     }
/*  956:     */     finally
/*  957:     */     {
/*  958:1000 */       this._comp.setCurrent(localJavaComponent);
/*  959:     */     }
/*  960:     */   }
/*  961:     */   
/*  962:     */   public WireInfo[] getBatchWires(String paramString)
/*  963:     */     throws RemoteException
/*  964:     */   {
/*  965:1008 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  966:1009 */     EJBContext localEJBContext = null;
/*  967:     */     try
/*  968:     */     {
/*  969:1012 */       localEJBContext = this._comp.getPooledInstance();
/*  970:1013 */       if (localEJBContext == null) {
/*  971:1015 */         localEJBContext = _create();
/*  972:     */       }
/*  973:1017 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/*  974:1018 */       if (localEJBContext.debug) {
/*  975:1020 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBatchWires"));
/*  976:     */       }
/*  977:1022 */       WireInfo[] arrayOfWireInfo2 = localBPWServicesBean
/*  978:1023 */         .getBatchWires(
/*  979:1024 */         paramString);
/*  980:     */       
/*  981:1026 */       arrayOfWireInfo2 = WireInfoSeqHelper.clone(arrayOfWireInfo2);
/*  982:1027 */       localEJBContext.returnToPool();
/*  983:1028 */       return arrayOfWireInfo2;
/*  984:     */     }
/*  985:     */     catch (Exception localException)
/*  986:     */     {
/*  987:1032 */       if (localEJBContext != null) {
/*  988:1034 */         localEJBContext.throwRemote(localException);
/*  989:     */       }
/*  990:1036 */       throw new EJBException(localException);
/*  991:     */     }
/*  992:     */     finally
/*  993:     */     {
/*  994:1040 */       this._comp.setCurrent(localJavaComponent);
/*  995:     */     }
/*  996:     */   }
/*  997:     */   
/*  998:     */   public BPWHist getWireHistory(BPWHist paramBPWHist)
/*  999:     */     throws RemoteException
/* 1000:     */   {
/* 1001:1048 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1002:1049 */     EJBContext localEJBContext = null;
/* 1003:     */     try
/* 1004:     */     {
/* 1005:1052 */       localEJBContext = this._comp.getPooledInstance();
/* 1006:1053 */       if (localEJBContext == null) {
/* 1007:1055 */         localEJBContext = _create();
/* 1008:     */       }
/* 1009:1057 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1010:1058 */       if (localEJBContext.debug) {
/* 1011:1060 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireHistory"));
/* 1012:     */       }
/* 1013:1062 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 1014:1063 */         .getWireHistory(
/* 1015:1064 */         BPWHistHelper.clone(paramBPWHist));
/* 1016:     */       
/* 1017:1066 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 1018:1067 */       localEJBContext.returnToPool();
/* 1019:1068 */       return localBPWHist2;
/* 1020:     */     }
/* 1021:     */     catch (Exception localException)
/* 1022:     */     {
/* 1023:1072 */       if (localEJBContext != null) {
/* 1024:1074 */         localEJBContext.throwRemote(localException);
/* 1025:     */       }
/* 1026:1076 */       throw new EJBException(localException);
/* 1027:     */     }
/* 1028:     */     finally
/* 1029:     */     {
/* 1030:1080 */       this._comp.setCurrent(localJavaComponent);
/* 1031:     */     }
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public BPWHist getWireHistoryByCustomer(BPWHist paramBPWHist)
/* 1035:     */     throws RemoteException
/* 1036:     */   {
/* 1037:1088 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1038:1089 */     EJBContext localEJBContext = null;
/* 1039:     */     try
/* 1040:     */     {
/* 1041:1092 */       localEJBContext = this._comp.getPooledInstance();
/* 1042:1093 */       if (localEJBContext == null) {
/* 1043:1095 */         localEJBContext = _create();
/* 1044:     */       }
/* 1045:1097 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1046:1098 */       if (localEJBContext.debug) {
/* 1047:1100 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireHistoryByCustomer"));
/* 1048:     */       }
/* 1049:1102 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 1050:1103 */         .getWireHistoryByCustomer(
/* 1051:1104 */         BPWHistHelper.clone(paramBPWHist));
/* 1052:     */       
/* 1053:1106 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 1054:1107 */       localEJBContext.returnToPool();
/* 1055:1108 */       return localBPWHist2;
/* 1056:     */     }
/* 1057:     */     catch (Exception localException)
/* 1058:     */     {
/* 1059:1112 */       if (localEJBContext != null) {
/* 1060:1114 */         localEJBContext.throwRemote(localException);
/* 1061:     */       }
/* 1062:1116 */       throw new EJBException(localException);
/* 1063:     */     }
/* 1064:     */     finally
/* 1065:     */     {
/* 1066:1120 */       this._comp.setCurrent(localJavaComponent);
/* 1067:     */     }
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public BPWHist getCombinedWireHistory(BPWHist paramBPWHist)
/* 1071:     */     throws RemoteException
/* 1072:     */   {
/* 1073:1128 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1074:1129 */     EJBContext localEJBContext = null;
/* 1075:     */     try
/* 1076:     */     {
/* 1077:1132 */       localEJBContext = this._comp.getPooledInstance();
/* 1078:1133 */       if (localEJBContext == null) {
/* 1079:1135 */         localEJBContext = _create();
/* 1080:     */       }
/* 1081:1137 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1082:1138 */       if (localEJBContext.debug) {
/* 1083:1140 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCombinedWireHistory"));
/* 1084:     */       }
/* 1085:1142 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 1086:1143 */         .getCombinedWireHistory(
/* 1087:1144 */         BPWHistHelper.clone(paramBPWHist));
/* 1088:     */       
/* 1089:1146 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 1090:1147 */       localEJBContext.returnToPool();
/* 1091:1148 */       return localBPWHist2;
/* 1092:     */     }
/* 1093:     */     catch (Exception localException)
/* 1094:     */     {
/* 1095:1152 */       if (localEJBContext != null) {
/* 1096:1154 */         localEJBContext.throwRemote(localException);
/* 1097:     */       }
/* 1098:1156 */       throw new EJBException(localException);
/* 1099:     */     }
/* 1100:     */     finally
/* 1101:     */     {
/* 1102:1160 */       this._comp.setCurrent(localJavaComponent);
/* 1103:     */     }
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public WireInfo[] getAuditWireTransfer(String paramString)
/* 1107:     */     throws RemoteException
/* 1108:     */   {
/* 1109:1168 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1110:1169 */     EJBContext localEJBContext = null;
/* 1111:     */     try
/* 1112:     */     {
/* 1113:1172 */       localEJBContext = this._comp.getPooledInstance();
/* 1114:1173 */       if (localEJBContext == null) {
/* 1115:1175 */         localEJBContext = _create();
/* 1116:     */       }
/* 1117:1177 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1118:1178 */       if (localEJBContext.debug) {
/* 1119:1180 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getAuditWireTransfer"));
/* 1120:     */       }
/* 1121:1182 */       WireInfo[] arrayOfWireInfo2 = localBPWServicesBean
/* 1122:1183 */         .getAuditWireTransfer(
/* 1123:1184 */         paramString);
/* 1124:     */       
/* 1125:1186 */       arrayOfWireInfo2 = WireInfoSeqHelper.clone(arrayOfWireInfo2);
/* 1126:1187 */       localEJBContext.returnToPool();
/* 1127:1188 */       return arrayOfWireInfo2;
/* 1128:     */     }
/* 1129:     */     catch (Exception localException)
/* 1130:     */     {
/* 1131:1192 */       if (localEJBContext != null) {
/* 1132:1194 */         localEJBContext.throwRemote(localException);
/* 1133:     */       }
/* 1134:1196 */       throw new EJBException(localException);
/* 1135:     */     }
/* 1136:     */     finally
/* 1137:     */     {
/* 1138:1200 */       this._comp.setCurrent(localJavaComponent);
/* 1139:     */     }
/* 1140:     */   }
/* 1141:     */   
/* 1142:     */   public WireInfo[] getAuditWireTransferByExtId(String paramString)
/* 1143:     */     throws RemoteException
/* 1144:     */   {
/* 1145:1208 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1146:1209 */     EJBContext localEJBContext = null;
/* 1147:     */     try
/* 1148:     */     {
/* 1149:1212 */       localEJBContext = this._comp.getPooledInstance();
/* 1150:1213 */       if (localEJBContext == null) {
/* 1151:1215 */         localEJBContext = _create();
/* 1152:     */       }
/* 1153:1217 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1154:1218 */       if (localEJBContext.debug) {
/* 1155:1220 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getAuditWireTransferByExtId"));
/* 1156:     */       }
/* 1157:1222 */       WireInfo[] arrayOfWireInfo2 = localBPWServicesBean
/* 1158:1223 */         .getAuditWireTransferByExtId(
/* 1159:1224 */         paramString);
/* 1160:     */       
/* 1161:1226 */       arrayOfWireInfo2 = WireInfoSeqHelper.clone(arrayOfWireInfo2);
/* 1162:1227 */       localEJBContext.returnToPool();
/* 1163:1228 */       return arrayOfWireInfo2;
/* 1164:     */     }
/* 1165:     */     catch (Exception localException)
/* 1166:     */     {
/* 1167:1232 */       if (localEJBContext != null) {
/* 1168:1234 */         localEJBContext.throwRemote(localException);
/* 1169:     */       }
/* 1170:1236 */       throw new EJBException(localException);
/* 1171:     */     }
/* 1172:     */     finally
/* 1173:     */     {
/* 1174:1240 */       this._comp.setCurrent(localJavaComponent);
/* 1175:     */     }
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public WireReleaseInfo getWireReleaseCount(WireReleaseInfo paramWireReleaseInfo)
/* 1179:     */     throws RemoteException
/* 1180:     */   {
/* 1181:1248 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1182:1249 */     EJBContext localEJBContext = null;
/* 1183:     */     try
/* 1184:     */     {
/* 1185:1252 */       localEJBContext = this._comp.getPooledInstance();
/* 1186:1253 */       if (localEJBContext == null) {
/* 1187:1255 */         localEJBContext = _create();
/* 1188:     */       }
/* 1189:1257 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1190:1258 */       if (localEJBContext.debug) {
/* 1191:1260 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireReleaseCount"));
/* 1192:     */       }
/* 1193:1262 */       WireReleaseInfo localWireReleaseInfo2 = localBPWServicesBean
/* 1194:1263 */         .getWireReleaseCount(
/* 1195:1264 */         (WireReleaseInfo)EJBContext.clone(paramWireReleaseInfo));
/* 1196:     */       
/* 1197:1266 */       localWireReleaseInfo2 = (WireReleaseInfo)EJBContext.clone(localWireReleaseInfo2);
/* 1198:1267 */       localEJBContext.returnToPool();
/* 1199:1268 */       return localWireReleaseInfo2;
/* 1200:     */     }
/* 1201:     */     catch (Exception localException)
/* 1202:     */     {
/* 1203:1272 */       if (localEJBContext != null) {
/* 1204:1274 */         localEJBContext.throwRemote(localException);
/* 1205:     */       }
/* 1206:1276 */       throw new EJBException(localException);
/* 1207:     */     }
/* 1208:     */     finally
/* 1209:     */     {
/* 1210:1280 */       this._comp.setCurrent(localJavaComponent);
/* 1211:     */     }
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public WireInfo[] addWireTransfers(WireInfo[] paramArrayOfWireInfo)
/* 1215:     */     throws RemoteException
/* 1216:     */   {
/* 1217:1288 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1218:1289 */     EJBContext localEJBContext = null;
/* 1219:     */     try
/* 1220:     */     {
/* 1221:1292 */       localEJBContext = this._comp.getPooledInstance();
/* 1222:1293 */       if (localEJBContext == null) {
/* 1223:1295 */         localEJBContext = _create();
/* 1224:     */       }
/* 1225:1297 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1226:1298 */       if (localEJBContext.debug) {
/* 1227:1300 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addWireTransfers"));
/* 1228:     */       }
/* 1229:1302 */       WireInfo[] arrayOfWireInfo2 = localBPWServicesBean
/* 1230:1303 */         .addWireTransfers(
/* 1231:1304 */         WireInfoSeqHelper.clone(paramArrayOfWireInfo));
/* 1232:     */       
/* 1233:1306 */       arrayOfWireInfo2 = WireInfoSeqHelper.clone(arrayOfWireInfo2);
/* 1234:1307 */       localEJBContext.returnToPool();
/* 1235:1308 */       return arrayOfWireInfo2;
/* 1236:     */     }
/* 1237:     */     catch (Exception localException)
/* 1238:     */     {
/* 1239:1312 */       if (localEJBContext != null) {
/* 1240:1314 */         localEJBContext.throwRemote(localException);
/* 1241:     */       }
/* 1242:1316 */       throw new EJBException(localException);
/* 1243:     */     }
/* 1244:     */     finally
/* 1245:     */     {
/* 1246:1320 */       this._comp.setCurrent(localJavaComponent);
/* 1247:     */     }
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public WireInfo[] releaseWireTransfer(WireInfo[] paramArrayOfWireInfo)
/* 1251:     */     throws RemoteException
/* 1252:     */   {
/* 1253:1328 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1254:1329 */     EJBContext localEJBContext = null;
/* 1255:     */     try
/* 1256:     */     {
/* 1257:1332 */       localEJBContext = this._comp.getPooledInstance();
/* 1258:1333 */       if (localEJBContext == null) {
/* 1259:1335 */         localEJBContext = _create();
/* 1260:     */       }
/* 1261:1337 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1262:1338 */       if (localEJBContext.debug) {
/* 1263:1340 */         localEJBContext.logger.debug(localEJBContext.debugMsg("releaseWireTransfer"));
/* 1264:     */       }
/* 1265:1342 */       WireInfo[] arrayOfWireInfo2 = localBPWServicesBean
/* 1266:1343 */         .releaseWireTransfer(
/* 1267:1344 */         WireInfoSeqHelper.clone(paramArrayOfWireInfo));
/* 1268:     */       
/* 1269:1346 */       arrayOfWireInfo2 = WireInfoSeqHelper.clone(arrayOfWireInfo2);
/* 1270:1347 */       localEJBContext.returnToPool();
/* 1271:1348 */       return arrayOfWireInfo2;
/* 1272:     */     }
/* 1273:     */     catch (Exception localException)
/* 1274:     */     {
/* 1275:1352 */       if (localEJBContext != null) {
/* 1276:1354 */         localEJBContext.throwRemote(localException);
/* 1277:     */       }
/* 1278:1356 */       throw new EJBException(localException);
/* 1279:     */     }
/* 1280:     */     finally
/* 1281:     */     {
/* 1282:1360 */       this._comp.setCurrent(localJavaComponent);
/* 1283:     */     }
/* 1284:     */   }
/* 1285:     */   
/* 1286:     */   public RecWireInfo addRecWireTransfer(RecWireInfo paramRecWireInfo)
/* 1287:     */     throws RemoteException
/* 1288:     */   {
/* 1289:1368 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1290:1369 */     EJBContext localEJBContext = null;
/* 1291:     */     try
/* 1292:     */     {
/* 1293:1372 */       localEJBContext = this._comp.getPooledInstance();
/* 1294:1373 */       if (localEJBContext == null) {
/* 1295:1375 */         localEJBContext = _create();
/* 1296:     */       }
/* 1297:1377 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1298:1378 */       if (localEJBContext.debug) {
/* 1299:1380 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addRecWireTransfer"));
/* 1300:     */       }
/* 1301:1382 */       RecWireInfo localRecWireInfo2 = localBPWServicesBean
/* 1302:1383 */         .addRecWireTransfer(
/* 1303:1384 */         (RecWireInfo)EJBContext.clone(paramRecWireInfo));
/* 1304:     */       
/* 1305:1386 */       localRecWireInfo2 = (RecWireInfo)EJBContext.clone(localRecWireInfo2);
/* 1306:1387 */       localEJBContext.returnToPool();
/* 1307:1388 */       return localRecWireInfo2;
/* 1308:     */     }
/* 1309:     */     catch (Exception localException)
/* 1310:     */     {
/* 1311:1392 */       if (localEJBContext != null) {
/* 1312:1394 */         localEJBContext.throwRemote(localException);
/* 1313:     */       }
/* 1314:1396 */       throw new EJBException(localException);
/* 1315:     */     }
/* 1316:     */     finally
/* 1317:     */     {
/* 1318:1400 */       this._comp.setCurrent(localJavaComponent);
/* 1319:     */     }
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   public RecWireInfo modRecWireTransfer(RecWireInfo paramRecWireInfo)
/* 1323:     */     throws RemoteException
/* 1324:     */   {
/* 1325:1408 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1326:1409 */     EJBContext localEJBContext = null;
/* 1327:     */     try
/* 1328:     */     {
/* 1329:1412 */       localEJBContext = this._comp.getPooledInstance();
/* 1330:1413 */       if (localEJBContext == null) {
/* 1331:1415 */         localEJBContext = _create();
/* 1332:     */       }
/* 1333:1417 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1334:1418 */       if (localEJBContext.debug) {
/* 1335:1420 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modRecWireTransfer"));
/* 1336:     */       }
/* 1337:1422 */       RecWireInfo localRecWireInfo2 = localBPWServicesBean
/* 1338:1423 */         .modRecWireTransfer(
/* 1339:1424 */         (RecWireInfo)EJBContext.clone(paramRecWireInfo));
/* 1340:     */       
/* 1341:1426 */       localRecWireInfo2 = (RecWireInfo)EJBContext.clone(localRecWireInfo2);
/* 1342:1427 */       localEJBContext.returnToPool();
/* 1343:1428 */       return localRecWireInfo2;
/* 1344:     */     }
/* 1345:     */     catch (Exception localException)
/* 1346:     */     {
/* 1347:1432 */       if (localEJBContext != null) {
/* 1348:1434 */         localEJBContext.throwRemote(localException);
/* 1349:     */       }
/* 1350:1436 */       throw new EJBException(localException);
/* 1351:     */     }
/* 1352:     */     finally
/* 1353:     */     {
/* 1354:1440 */       this._comp.setCurrent(localJavaComponent);
/* 1355:     */     }
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   public RecWireInfo cancRecWireTransfer(RecWireInfo paramRecWireInfo)
/* 1359:     */     throws RemoteException
/* 1360:     */   {
/* 1361:1448 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1362:1449 */     EJBContext localEJBContext = null;
/* 1363:     */     try
/* 1364:     */     {
/* 1365:1452 */       localEJBContext = this._comp.getPooledInstance();
/* 1366:1453 */       if (localEJBContext == null) {
/* 1367:1455 */         localEJBContext = _create();
/* 1368:     */       }
/* 1369:1457 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1370:1458 */       if (localEJBContext.debug) {
/* 1371:1460 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancRecWireTransfer"));
/* 1372:     */       }
/* 1373:1462 */       RecWireInfo localRecWireInfo2 = localBPWServicesBean
/* 1374:1463 */         .cancRecWireTransfer(
/* 1375:1464 */         (RecWireInfo)EJBContext.clone(paramRecWireInfo));
/* 1376:     */       
/* 1377:1466 */       localRecWireInfo2 = (RecWireInfo)EJBContext.clone(localRecWireInfo2);
/* 1378:1467 */       localEJBContext.returnToPool();
/* 1379:1468 */       return localRecWireInfo2;
/* 1380:     */     }
/* 1381:     */     catch (Exception localException)
/* 1382:     */     {
/* 1383:1472 */       if (localEJBContext != null) {
/* 1384:1474 */         localEJBContext.throwRemote(localException);
/* 1385:     */       }
/* 1386:1476 */       throw new EJBException(localException);
/* 1387:     */     }
/* 1388:     */     finally
/* 1389:     */     {
/* 1390:1480 */       this._comp.setCurrent(localJavaComponent);
/* 1391:     */     }
/* 1392:     */   }
/* 1393:     */   
/* 1394:     */   public RecWireInfo getRecWireTransferById(String paramString, boolean paramBoolean)
/* 1395:     */     throws RemoteException
/* 1396:     */   {
/* 1397:1489 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1398:1490 */     EJBContext localEJBContext = null;
/* 1399:     */     try
/* 1400:     */     {
/* 1401:1493 */       localEJBContext = this._comp.getPooledInstance();
/* 1402:1494 */       if (localEJBContext == null) {
/* 1403:1496 */         localEJBContext = _create();
/* 1404:     */       }
/* 1405:1498 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1406:1499 */       if (localEJBContext.debug) {
/* 1407:1501 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecWireTransferById"));
/* 1408:     */       }
/* 1409:1503 */       RecWireInfo localRecWireInfo2 = localBPWServicesBean
/* 1410:1504 */         .getRecWireTransferById(
/* 1411:1505 */         paramString, 
/* 1412:1506 */         paramBoolean);
/* 1413:     */       
/* 1414:1508 */       localRecWireInfo2 = (RecWireInfo)EJBContext.clone(localRecWireInfo2);
/* 1415:1509 */       localEJBContext.returnToPool();
/* 1416:1510 */       return localRecWireInfo2;
/* 1417:     */     }
/* 1418:     */     catch (Exception localException)
/* 1419:     */     {
/* 1420:1514 */       if (localEJBContext != null) {
/* 1421:1516 */         localEJBContext.throwRemote(localException);
/* 1422:     */       }
/* 1423:1518 */       throw new EJBException(localException);
/* 1424:     */     }
/* 1425:     */     finally
/* 1426:     */     {
/* 1427:1522 */       this._comp.setCurrent(localJavaComponent);
/* 1428:     */     }
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public RecWireInfo getRecWireTransferById(String paramString)
/* 1432:     */     throws RemoteException
/* 1433:     */   {
/* 1434:1530 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1435:1531 */     EJBContext localEJBContext = null;
/* 1436:     */     try
/* 1437:     */     {
/* 1438:1534 */       localEJBContext = this._comp.getPooledInstance();
/* 1439:1535 */       if (localEJBContext == null) {
/* 1440:1537 */         localEJBContext = _create();
/* 1441:     */       }
/* 1442:1539 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1443:1540 */       if (localEJBContext.debug) {
/* 1444:1542 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecWireTransferById"));
/* 1445:     */       }
/* 1446:1544 */       RecWireInfo localRecWireInfo2 = localBPWServicesBean
/* 1447:1545 */         .getRecWireTransferById(
/* 1448:1546 */         paramString);
/* 1449:     */       
/* 1450:1548 */       localRecWireInfo2 = (RecWireInfo)EJBContext.clone(localRecWireInfo2);
/* 1451:1549 */       localEJBContext.returnToPool();
/* 1452:1550 */       return localRecWireInfo2;
/* 1453:     */     }
/* 1454:     */     catch (Exception localException)
/* 1455:     */     {
/* 1456:1554 */       if (localEJBContext != null) {
/* 1457:1556 */         localEJBContext.throwRemote(localException);
/* 1458:     */       }
/* 1459:1558 */       throw new EJBException(localException);
/* 1460:     */     }
/* 1461:     */     finally
/* 1462:     */     {
/* 1463:1562 */       this._comp.setCurrent(localJavaComponent);
/* 1464:     */     }
/* 1465:     */   }
/* 1466:     */   
/* 1467:     */   public RecWireInfo getRecWireTransfer(String paramString)
/* 1468:     */     throws RemoteException
/* 1469:     */   {
/* 1470:1570 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1471:1571 */     EJBContext localEJBContext = null;
/* 1472:     */     try
/* 1473:     */     {
/* 1474:1574 */       localEJBContext = this._comp.getPooledInstance();
/* 1475:1575 */       if (localEJBContext == null) {
/* 1476:1577 */         localEJBContext = _create();
/* 1477:     */       }
/* 1478:1579 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1479:1580 */       if (localEJBContext.debug) {
/* 1480:1582 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecWireTransfer"));
/* 1481:     */       }
/* 1482:1584 */       RecWireInfo localRecWireInfo2 = localBPWServicesBean
/* 1483:1585 */         .getRecWireTransfer(
/* 1484:1586 */         paramString);
/* 1485:     */       
/* 1486:1588 */       localRecWireInfo2 = (RecWireInfo)EJBContext.clone(localRecWireInfo2);
/* 1487:1589 */       localEJBContext.returnToPool();
/* 1488:1590 */       return localRecWireInfo2;
/* 1489:     */     }
/* 1490:     */     catch (Exception localException)
/* 1491:     */     {
/* 1492:1594 */       if (localEJBContext != null) {
/* 1493:1596 */         localEJBContext.throwRemote(localException);
/* 1494:     */       }
/* 1495:1598 */       throw new EJBException(localException);
/* 1496:     */     }
/* 1497:     */     finally
/* 1498:     */     {
/* 1499:1602 */       this._comp.setCurrent(localJavaComponent);
/* 1500:     */     }
/* 1501:     */   }
/* 1502:     */   
/* 1503:     */   public RecWireInfo[] getRecWireTransfers(String[] paramArrayOfString)
/* 1504:     */     throws RemoteException
/* 1505:     */   {
/* 1506:1610 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1507:1611 */     EJBContext localEJBContext = null;
/* 1508:     */     try
/* 1509:     */     {
/* 1510:1614 */       localEJBContext = this._comp.getPooledInstance();
/* 1511:1615 */       if (localEJBContext == null) {
/* 1512:1617 */         localEJBContext = _create();
/* 1513:     */       }
/* 1514:1619 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1515:1620 */       if (localEJBContext.debug) {
/* 1516:1622 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecWireTransfers"));
/* 1517:     */       }
/* 1518:1624 */       RecWireInfo[] arrayOfRecWireInfo2 = localBPWServicesBean
/* 1519:1625 */         .getRecWireTransfers(
/* 1520:1626 */         StringSeqHelper.clone(paramArrayOfString));
/* 1521:     */       
/* 1522:1628 */       arrayOfRecWireInfo2 = RecWireInfoSeqHelper.clone(arrayOfRecWireInfo2);
/* 1523:1629 */       localEJBContext.returnToPool();
/* 1524:1630 */       return arrayOfRecWireInfo2;
/* 1525:     */     }
/* 1526:     */     catch (Exception localException)
/* 1527:     */     {
/* 1528:1634 */       if (localEJBContext != null) {
/* 1529:1636 */         localEJBContext.throwRemote(localException);
/* 1530:     */       }
/* 1531:1638 */       throw new EJBException(localException);
/* 1532:     */     }
/* 1533:     */     finally
/* 1534:     */     {
/* 1535:1642 */       this._comp.setCurrent(localJavaComponent);
/* 1536:     */     }
/* 1537:     */   }
/* 1538:     */   
/* 1539:     */   public BPWHist getRecWireHistory(BPWHist paramBPWHist)
/* 1540:     */     throws RemoteException
/* 1541:     */   {
/* 1542:1650 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1543:1651 */     EJBContext localEJBContext = null;
/* 1544:     */     try
/* 1545:     */     {
/* 1546:1654 */       localEJBContext = this._comp.getPooledInstance();
/* 1547:1655 */       if (localEJBContext == null) {
/* 1548:1657 */         localEJBContext = _create();
/* 1549:     */       }
/* 1550:1659 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1551:1660 */       if (localEJBContext.debug) {
/* 1552:1662 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecWireHistory"));
/* 1553:     */       }
/* 1554:1664 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 1555:1665 */         .getRecWireHistory(
/* 1556:1666 */         BPWHistHelper.clone(paramBPWHist));
/* 1557:     */       
/* 1558:1668 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 1559:1669 */       localEJBContext.returnToPool();
/* 1560:1670 */       return localBPWHist2;
/* 1561:     */     }
/* 1562:     */     catch (Exception localException)
/* 1563:     */     {
/* 1564:1674 */       if (localEJBContext != null) {
/* 1565:1676 */         localEJBContext.throwRemote(localException);
/* 1566:     */       }
/* 1567:1678 */       throw new EJBException(localException);
/* 1568:     */     }
/* 1569:     */     finally
/* 1570:     */     {
/* 1571:1682 */       this._comp.setCurrent(localJavaComponent);
/* 1572:     */     }
/* 1573:     */   }
/* 1574:     */   
/* 1575:     */   public BPWHist getRecWireHistoryByCustomer(BPWHist paramBPWHist)
/* 1576:     */     throws RemoteException
/* 1577:     */   {
/* 1578:1690 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1579:1691 */     EJBContext localEJBContext = null;
/* 1580:     */     try
/* 1581:     */     {
/* 1582:1694 */       localEJBContext = this._comp.getPooledInstance();
/* 1583:1695 */       if (localEJBContext == null) {
/* 1584:1697 */         localEJBContext = _create();
/* 1585:     */       }
/* 1586:1699 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1587:1700 */       if (localEJBContext.debug) {
/* 1588:1702 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecWireHistoryByCustomer"));
/* 1589:     */       }
/* 1590:1704 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 1591:1705 */         .getRecWireHistoryByCustomer(
/* 1592:1706 */         BPWHistHelper.clone(paramBPWHist));
/* 1593:     */       
/* 1594:1708 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 1595:1709 */       localEJBContext.returnToPool();
/* 1596:1710 */       return localBPWHist2;
/* 1597:     */     }
/* 1598:     */     catch (Exception localException)
/* 1599:     */     {
/* 1600:1714 */       if (localEJBContext != null) {
/* 1601:1716 */         localEJBContext.throwRemote(localException);
/* 1602:     */       }
/* 1603:1718 */       throw new EJBException(localException);
/* 1604:     */     }
/* 1605:     */     finally
/* 1606:     */     {
/* 1607:1722 */       this._comp.setCurrent(localJavaComponent);
/* 1608:     */     }
/* 1609:     */   }
/* 1610:     */   
/* 1611:     */   public RecWireInfo[] addRecWireTransfers(RecWireInfo[] paramArrayOfRecWireInfo)
/* 1612:     */     throws RemoteException
/* 1613:     */   {
/* 1614:1730 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1615:1731 */     EJBContext localEJBContext = null;
/* 1616:     */     try
/* 1617:     */     {
/* 1618:1734 */       localEJBContext = this._comp.getPooledInstance();
/* 1619:1735 */       if (localEJBContext == null) {
/* 1620:1737 */         localEJBContext = _create();
/* 1621:     */       }
/* 1622:1739 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1623:1740 */       if (localEJBContext.debug) {
/* 1624:1742 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addRecWireTransfers"));
/* 1625:     */       }
/* 1626:1744 */       RecWireInfo[] arrayOfRecWireInfo2 = localBPWServicesBean
/* 1627:1745 */         .addRecWireTransfers(
/* 1628:1746 */         RecWireInfoSeqHelper.clone(paramArrayOfRecWireInfo));
/* 1629:     */       
/* 1630:1748 */       arrayOfRecWireInfo2 = RecWireInfoSeqHelper.clone(arrayOfRecWireInfo2);
/* 1631:1749 */       localEJBContext.returnToPool();
/* 1632:1750 */       return arrayOfRecWireInfo2;
/* 1633:     */     }
/* 1634:     */     catch (Exception localException)
/* 1635:     */     {
/* 1636:1754 */       if (localEJBContext != null) {
/* 1637:1756 */         localEJBContext.throwRemote(localException);
/* 1638:     */       }
/* 1639:1758 */       throw new EJBException(localException);
/* 1640:     */     }
/* 1641:     */     finally
/* 1642:     */     {
/* 1643:1762 */       this._comp.setCurrent(localJavaComponent);
/* 1644:     */     }
/* 1645:     */   }
/* 1646:     */   
/* 1647:     */   public HashMap getWiresConfiguration()
/* 1648:     */     throws RemoteException
/* 1649:     */   {
/* 1650:1769 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1651:1770 */     EJBContext localEJBContext = null;
/* 1652:     */     try
/* 1653:     */     {
/* 1654:1773 */       localEJBContext = this._comp.getPooledInstance();
/* 1655:1774 */       if (localEJBContext == null) {
/* 1656:1776 */         localEJBContext = _create();
/* 1657:     */       }
/* 1658:1778 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1659:1779 */       if (localEJBContext.debug) {
/* 1660:1781 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWiresConfiguration"));
/* 1661:     */       }
/* 1662:1783 */       HashMap localHashMap2 = localBPWServicesBean
/* 1663:1784 */         .getWiresConfiguration();
/* 1664:     */       
/* 1665:1786 */       localHashMap2 = (HashMap)EJBContext.clone(localHashMap2);
/* 1666:1787 */       localEJBContext.returnToPool();
/* 1667:1788 */       return localHashMap2;
/* 1668:     */     }
/* 1669:     */     catch (Exception localException)
/* 1670:     */     {
/* 1671:1792 */       if (localEJBContext != null) {
/* 1672:1794 */         localEJBContext.throwRemote(localException);
/* 1673:     */       }
/* 1674:1796 */       throw new EJBException(localException);
/* 1675:     */     }
/* 1676:     */     finally
/* 1677:     */     {
/* 1678:1800 */       this._comp.setCurrent(localJavaComponent);
/* 1679:     */     }
/* 1680:     */   }
/* 1681:     */   
/* 1682:     */   public WireBatchInfo addWireTransferBatch(WireBatchInfo paramWireBatchInfo)
/* 1683:     */     throws RemoteException
/* 1684:     */   {
/* 1685:1808 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1686:1809 */     EJBContext localEJBContext = null;
/* 1687:     */     try
/* 1688:     */     {
/* 1689:1812 */       localEJBContext = this._comp.getPooledInstance();
/* 1690:1813 */       if (localEJBContext == null) {
/* 1691:1815 */         localEJBContext = _create();
/* 1692:     */       }
/* 1693:1817 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1694:1818 */       if (localEJBContext.debug) {
/* 1695:1820 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addWireTransferBatch"));
/* 1696:     */       }
/* 1697:1822 */       WireBatchInfo localWireBatchInfo2 = localBPWServicesBean
/* 1698:1823 */         .addWireTransferBatch(
/* 1699:1824 */         (WireBatchInfo)EJBContext.clone(paramWireBatchInfo));
/* 1700:     */       
/* 1701:1826 */       localWireBatchInfo2 = (WireBatchInfo)EJBContext.clone(localWireBatchInfo2);
/* 1702:1827 */       localEJBContext.returnToPool();
/* 1703:1828 */       return localWireBatchInfo2;
/* 1704:     */     }
/* 1705:     */     catch (Exception localException)
/* 1706:     */     {
/* 1707:1832 */       if (localEJBContext != null) {
/* 1708:1834 */         localEJBContext.throwRemote(localException);
/* 1709:     */       }
/* 1710:1836 */       throw new EJBException(localException);
/* 1711:     */     }
/* 1712:     */     finally
/* 1713:     */     {
/* 1714:1840 */       this._comp.setCurrent(localJavaComponent);
/* 1715:     */     }
/* 1716:     */   }
/* 1717:     */   
/* 1718:     */   public WireBatchInfo modWireTransferBatch(WireBatchInfo paramWireBatchInfo)
/* 1719:     */     throws RemoteException
/* 1720:     */   {
/* 1721:1848 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1722:1849 */     EJBContext localEJBContext = null;
/* 1723:     */     try
/* 1724:     */     {
/* 1725:1852 */       localEJBContext = this._comp.getPooledInstance();
/* 1726:1853 */       if (localEJBContext == null) {
/* 1727:1855 */         localEJBContext = _create();
/* 1728:     */       }
/* 1729:1857 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1730:1858 */       if (localEJBContext.debug) {
/* 1731:1860 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modWireTransferBatch"));
/* 1732:     */       }
/* 1733:1862 */       WireBatchInfo localWireBatchInfo2 = localBPWServicesBean
/* 1734:1863 */         .modWireTransferBatch(
/* 1735:1864 */         (WireBatchInfo)EJBContext.clone(paramWireBatchInfo));
/* 1736:     */       
/* 1737:1866 */       localWireBatchInfo2 = (WireBatchInfo)EJBContext.clone(localWireBatchInfo2);
/* 1738:1867 */       localEJBContext.returnToPool();
/* 1739:1868 */       return localWireBatchInfo2;
/* 1740:     */     }
/* 1741:     */     catch (Exception localException)
/* 1742:     */     {
/* 1743:1872 */       if (localEJBContext != null) {
/* 1744:1874 */         localEJBContext.throwRemote(localException);
/* 1745:     */       }
/* 1746:1876 */       throw new EJBException(localException);
/* 1747:     */     }
/* 1748:     */     finally
/* 1749:     */     {
/* 1750:1880 */       this._comp.setCurrent(localJavaComponent);
/* 1751:     */     }
/* 1752:     */   }
/* 1753:     */   
/* 1754:     */   public WireBatchInfo canWireTransferBatch(WireBatchInfo paramWireBatchInfo)
/* 1755:     */     throws RemoteException
/* 1756:     */   {
/* 1757:1888 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1758:1889 */     EJBContext localEJBContext = null;
/* 1759:     */     try
/* 1760:     */     {
/* 1761:1892 */       localEJBContext = this._comp.getPooledInstance();
/* 1762:1893 */       if (localEJBContext == null) {
/* 1763:1895 */         localEJBContext = _create();
/* 1764:     */       }
/* 1765:1897 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1766:1898 */       if (localEJBContext.debug) {
/* 1767:1900 */         localEJBContext.logger.debug(localEJBContext.debugMsg("canWireTransferBatch"));
/* 1768:     */       }
/* 1769:1902 */       WireBatchInfo localWireBatchInfo2 = localBPWServicesBean
/* 1770:1903 */         .canWireTransferBatch(
/* 1771:1904 */         (WireBatchInfo)EJBContext.clone(paramWireBatchInfo));
/* 1772:     */       
/* 1773:1906 */       localWireBatchInfo2 = (WireBatchInfo)EJBContext.clone(localWireBatchInfo2);
/* 1774:1907 */       localEJBContext.returnToPool();
/* 1775:1908 */       return localWireBatchInfo2;
/* 1776:     */     }
/* 1777:     */     catch (Exception localException)
/* 1778:     */     {
/* 1779:1912 */       if (localEJBContext != null) {
/* 1780:1914 */         localEJBContext.throwRemote(localException);
/* 1781:     */       }
/* 1782:1916 */       throw new EJBException(localException);
/* 1783:     */     }
/* 1784:     */     finally
/* 1785:     */     {
/* 1786:1920 */       this._comp.setCurrent(localJavaComponent);
/* 1787:     */     }
/* 1788:     */   }
/* 1789:     */   
/* 1790:     */   public WireBatchInfo[] getWireTransferBatch(WireBatchInfo paramWireBatchInfo)
/* 1791:     */     throws RemoteException
/* 1792:     */   {
/* 1793:1928 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1794:1929 */     EJBContext localEJBContext = null;
/* 1795:     */     try
/* 1796:     */     {
/* 1797:1932 */       localEJBContext = this._comp.getPooledInstance();
/* 1798:1933 */       if (localEJBContext == null) {
/* 1799:1935 */         localEJBContext = _create();
/* 1800:     */       }
/* 1801:1937 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1802:1938 */       if (localEJBContext.debug) {
/* 1803:1940 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireTransferBatch"));
/* 1804:     */       }
/* 1805:1942 */       WireBatchInfo[] arrayOfWireBatchInfo2 = localBPWServicesBean
/* 1806:1943 */         .getWireTransferBatch(
/* 1807:1944 */         (WireBatchInfo)EJBContext.clone(paramWireBatchInfo));
/* 1808:     */       
/* 1809:1946 */       arrayOfWireBatchInfo2 = WireBatchInfoSeqHelper.clone(arrayOfWireBatchInfo2);
/* 1810:1947 */       localEJBContext.returnToPool();
/* 1811:1948 */       return arrayOfWireBatchInfo2;
/* 1812:     */     }
/* 1813:     */     catch (Exception localException)
/* 1814:     */     {
/* 1815:1952 */       if (localEJBContext != null) {
/* 1816:1954 */         localEJBContext.throwRemote(localException);
/* 1817:     */       }
/* 1818:1956 */       throw new EJBException(localException);
/* 1819:     */     }
/* 1820:     */     finally
/* 1821:     */     {
/* 1822:1960 */       this._comp.setCurrent(localJavaComponent);
/* 1823:     */     }
/* 1824:     */   }
/* 1825:     */   
/* 1826:     */   public BPWHist getWireBatchHistory(BPWHist paramBPWHist)
/* 1827:     */     throws RemoteException
/* 1828:     */   {
/* 1829:1968 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1830:1969 */     EJBContext localEJBContext = null;
/* 1831:     */     try
/* 1832:     */     {
/* 1833:1972 */       localEJBContext = this._comp.getPooledInstance();
/* 1834:1973 */       if (localEJBContext == null) {
/* 1835:1975 */         localEJBContext = _create();
/* 1836:     */       }
/* 1837:1977 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1838:1978 */       if (localEJBContext.debug) {
/* 1839:1980 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireBatchHistory"));
/* 1840:     */       }
/* 1841:1982 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 1842:1983 */         .getWireBatchHistory(
/* 1843:1984 */         BPWHistHelper.clone(paramBPWHist));
/* 1844:     */       
/* 1845:1986 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 1846:1987 */       localEJBContext.returnToPool();
/* 1847:1988 */       return localBPWHist2;
/* 1848:     */     }
/* 1849:     */     catch (Exception localException)
/* 1850:     */     {
/* 1851:1992 */       if (localEJBContext != null) {
/* 1852:1994 */         localEJBContext.throwRemote(localException);
/* 1853:     */       }
/* 1854:1996 */       throw new EJBException(localException);
/* 1855:     */     }
/* 1856:     */     finally
/* 1857:     */     {
/* 1858:2000 */       this._comp.setCurrent(localJavaComponent);
/* 1859:     */     }
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   public boolean isWireBatchDeleteable(String paramString)
/* 1863:     */     throws RemoteException
/* 1864:     */   {
/* 1865:2008 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1866:2009 */     EJBContext localEJBContext = null;
/* 1867:     */     try
/* 1868:     */     {
/* 1869:2012 */       localEJBContext = this._comp.getPooledInstance();
/* 1870:2013 */       if (localEJBContext == null) {
/* 1871:2015 */         localEJBContext = _create();
/* 1872:     */       }
/* 1873:2017 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1874:2018 */       if (localEJBContext.debug) {
/* 1875:2020 */         localEJBContext.logger.debug(localEJBContext.debugMsg("isWireBatchDeleteable"));
/* 1876:     */       }
/* 1877:2022 */       boolean bool2 = localBPWServicesBean
/* 1878:2023 */         .isWireBatchDeleteable(
/* 1879:2024 */         paramString);
/* 1880:     */       
/* 1881:2026 */       localEJBContext.returnToPool();
/* 1882:2027 */       return bool2;
/* 1883:     */     }
/* 1884:     */     catch (Exception localException)
/* 1885:     */     {
/* 1886:2031 */       if (localEJBContext != null) {
/* 1887:2033 */         localEJBContext.throwRemote(localException);
/* 1888:     */       }
/* 1889:2035 */       throw new EJBException(localException);
/* 1890:     */     }
/* 1891:     */     finally
/* 1892:     */     {
/* 1893:2039 */       this._comp.setCurrent(localJavaComponent);
/* 1894:     */     }
/* 1895:     */   }
/* 1896:     */   
/* 1897:     */   public WirePayeeInfo[] addWirePayee(WirePayeeInfo[] paramArrayOfWirePayeeInfo)
/* 1898:     */     throws RemoteException
/* 1899:     */   {
/* 1900:2047 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1901:2048 */     EJBContext localEJBContext = null;
/* 1902:     */     try
/* 1903:     */     {
/* 1904:2051 */       localEJBContext = this._comp.getPooledInstance();
/* 1905:2052 */       if (localEJBContext == null) {
/* 1906:2054 */         localEJBContext = _create();
/* 1907:     */       }
/* 1908:2056 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1909:2057 */       if (localEJBContext.debug) {
/* 1910:2059 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addWirePayee"));
/* 1911:     */       }
/* 1912:2061 */       WirePayeeInfo[] arrayOfWirePayeeInfo2 = localBPWServicesBean
/* 1913:2062 */         .addWirePayee(
/* 1914:2063 */         WirePayeeInfoSeqHelper.clone(paramArrayOfWirePayeeInfo));
/* 1915:     */       
/* 1916:2065 */       arrayOfWirePayeeInfo2 = WirePayeeInfoSeqHelper.clone(arrayOfWirePayeeInfo2);
/* 1917:2066 */       localEJBContext.returnToPool();
/* 1918:2067 */       return arrayOfWirePayeeInfo2;
/* 1919:     */     }
/* 1920:     */     catch (Exception localException)
/* 1921:     */     {
/* 1922:2071 */       if (localEJBContext != null) {
/* 1923:2073 */         localEJBContext.throwRemote(localException);
/* 1924:     */       }
/* 1925:2075 */       throw new EJBException(localException);
/* 1926:     */     }
/* 1927:     */     finally
/* 1928:     */     {
/* 1929:2079 */       this._comp.setCurrent(localJavaComponent);
/* 1930:     */     }
/* 1931:     */   }
/* 1932:     */   
/* 1933:     */   public WirePayeeInfo addWirePayee(WirePayeeInfo paramWirePayeeInfo)
/* 1934:     */     throws RemoteException
/* 1935:     */   {
/* 1936:2087 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1937:2088 */     EJBContext localEJBContext = null;
/* 1938:     */     try
/* 1939:     */     {
/* 1940:2091 */       localEJBContext = this._comp.getPooledInstance();
/* 1941:2092 */       if (localEJBContext == null) {
/* 1942:2094 */         localEJBContext = _create();
/* 1943:     */       }
/* 1944:2096 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1945:2097 */       if (localEJBContext.debug) {
/* 1946:2099 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addWirePayee"));
/* 1947:     */       }
/* 1948:2101 */       WirePayeeInfo localWirePayeeInfo2 = localBPWServicesBean
/* 1949:2102 */         .addWirePayee(
/* 1950:2103 */         (WirePayeeInfo)EJBContext.clone(paramWirePayeeInfo));
/* 1951:     */       
/* 1952:2105 */       localWirePayeeInfo2 = (WirePayeeInfo)EJBContext.clone(localWirePayeeInfo2);
/* 1953:2106 */       localEJBContext.returnToPool();
/* 1954:2107 */       return localWirePayeeInfo2;
/* 1955:     */     }
/* 1956:     */     catch (Exception localException)
/* 1957:     */     {
/* 1958:2111 */       if (localEJBContext != null) {
/* 1959:2113 */         localEJBContext.throwRemote(localException);
/* 1960:     */       }
/* 1961:2115 */       throw new EJBException(localException);
/* 1962:     */     }
/* 1963:     */     finally
/* 1964:     */     {
/* 1965:2119 */       this._comp.setCurrent(localJavaComponent);
/* 1966:     */     }
/* 1967:     */   }
/* 1968:     */   
/* 1969:     */   public WirePayeeInfo modWirePayee(WirePayeeInfo paramWirePayeeInfo)
/* 1970:     */     throws RemoteException
/* 1971:     */   {
/* 1972:2127 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1973:2128 */     EJBContext localEJBContext = null;
/* 1974:     */     try
/* 1975:     */     {
/* 1976:2131 */       localEJBContext = this._comp.getPooledInstance();
/* 1977:2132 */       if (localEJBContext == null) {
/* 1978:2134 */         localEJBContext = _create();
/* 1979:     */       }
/* 1980:2136 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 1981:2137 */       if (localEJBContext.debug) {
/* 1982:2139 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modWirePayee"));
/* 1983:     */       }
/* 1984:2141 */       WirePayeeInfo localWirePayeeInfo2 = localBPWServicesBean
/* 1985:2142 */         .modWirePayee(
/* 1986:2143 */         (WirePayeeInfo)EJBContext.clone(paramWirePayeeInfo));
/* 1987:     */       
/* 1988:2145 */       localWirePayeeInfo2 = (WirePayeeInfo)EJBContext.clone(localWirePayeeInfo2);
/* 1989:2146 */       localEJBContext.returnToPool();
/* 1990:2147 */       return localWirePayeeInfo2;
/* 1991:     */     }
/* 1992:     */     catch (Exception localException)
/* 1993:     */     {
/* 1994:2151 */       if (localEJBContext != null) {
/* 1995:2153 */         localEJBContext.throwRemote(localException);
/* 1996:     */       }
/* 1997:2155 */       throw new EJBException(localException);
/* 1998:     */     }
/* 1999:     */     finally
/* 2000:     */     {
/* 2001:2159 */       this._comp.setCurrent(localJavaComponent);
/* 2002:     */     }
/* 2003:     */   }
/* 2004:     */   
/* 2005:     */   public WirePayeeInfo canWirePayee(WirePayeeInfo paramWirePayeeInfo)
/* 2006:     */     throws RemoteException
/* 2007:     */   {
/* 2008:2167 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2009:2168 */     EJBContext localEJBContext = null;
/* 2010:     */     try
/* 2011:     */     {
/* 2012:2171 */       localEJBContext = this._comp.getPooledInstance();
/* 2013:2172 */       if (localEJBContext == null) {
/* 2014:2174 */         localEJBContext = _create();
/* 2015:     */       }
/* 2016:2176 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2017:2177 */       if (localEJBContext.debug) {
/* 2018:2179 */         localEJBContext.logger.debug(localEJBContext.debugMsg("canWirePayee"));
/* 2019:     */       }
/* 2020:2181 */       WirePayeeInfo localWirePayeeInfo2 = localBPWServicesBean
/* 2021:2182 */         .canWirePayee(
/* 2022:2183 */         (WirePayeeInfo)EJBContext.clone(paramWirePayeeInfo));
/* 2023:     */       
/* 2024:2185 */       localWirePayeeInfo2 = (WirePayeeInfo)EJBContext.clone(localWirePayeeInfo2);
/* 2025:2186 */       localEJBContext.returnToPool();
/* 2026:2187 */       return localWirePayeeInfo2;
/* 2027:     */     }
/* 2028:     */     catch (Exception localException)
/* 2029:     */     {
/* 2030:2191 */       if (localEJBContext != null) {
/* 2031:2193 */         localEJBContext.throwRemote(localException);
/* 2032:     */       }
/* 2033:2195 */       throw new EJBException(localException);
/* 2034:     */     }
/* 2035:     */     finally
/* 2036:     */     {
/* 2037:2199 */       this._comp.setCurrent(localJavaComponent);
/* 2038:     */     }
/* 2039:     */   }
/* 2040:     */   
/* 2041:     */   public WirePayeeInfo getWirePayee(WirePayeeInfo paramWirePayeeInfo)
/* 2042:     */     throws RemoteException
/* 2043:     */   {
/* 2044:2207 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2045:2208 */     EJBContext localEJBContext = null;
/* 2046:     */     try
/* 2047:     */     {
/* 2048:2211 */       localEJBContext = this._comp.getPooledInstance();
/* 2049:2212 */       if (localEJBContext == null) {
/* 2050:2214 */         localEJBContext = _create();
/* 2051:     */       }
/* 2052:2216 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2053:2217 */       if (localEJBContext.debug) {
/* 2054:2219 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWirePayee"));
/* 2055:     */       }
/* 2056:2221 */       WirePayeeInfo localWirePayeeInfo2 = localBPWServicesBean
/* 2057:2222 */         .getWirePayee(
/* 2058:2223 */         (WirePayeeInfo)EJBContext.clone(paramWirePayeeInfo));
/* 2059:     */       
/* 2060:2225 */       localWirePayeeInfo2 = (WirePayeeInfo)EJBContext.clone(localWirePayeeInfo2);
/* 2061:2226 */       localEJBContext.returnToPool();
/* 2062:2227 */       return localWirePayeeInfo2;
/* 2063:     */     }
/* 2064:     */     catch (Exception localException)
/* 2065:     */     {
/* 2066:2231 */       if (localEJBContext != null) {
/* 2067:2233 */         localEJBContext.throwRemote(localException);
/* 2068:     */       }
/* 2069:2235 */       throw new EJBException(localException);
/* 2070:     */     }
/* 2071:     */     finally
/* 2072:     */     {
/* 2073:2239 */       this._comp.setCurrent(localJavaComponent);
/* 2074:     */     }
/* 2075:     */   }
/* 2076:     */   
/* 2077:     */   public WirePayeeInfo getWirePayee(String paramString)
/* 2078:     */     throws RemoteException
/* 2079:     */   {
/* 2080:2247 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2081:2248 */     EJBContext localEJBContext = null;
/* 2082:     */     try
/* 2083:     */     {
/* 2084:2251 */       localEJBContext = this._comp.getPooledInstance();
/* 2085:2252 */       if (localEJBContext == null) {
/* 2086:2254 */         localEJBContext = _create();
/* 2087:     */       }
/* 2088:2256 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2089:2257 */       if (localEJBContext.debug) {
/* 2090:2259 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWirePayee"));
/* 2091:     */       }
/* 2092:2261 */       WirePayeeInfo localWirePayeeInfo2 = localBPWServicesBean
/* 2093:2262 */         .getWirePayee(
/* 2094:2263 */         paramString);
/* 2095:     */       
/* 2096:2265 */       localWirePayeeInfo2 = (WirePayeeInfo)EJBContext.clone(localWirePayeeInfo2);
/* 2097:2266 */       localEJBContext.returnToPool();
/* 2098:2267 */       return localWirePayeeInfo2;
/* 2099:     */     }
/* 2100:     */     catch (Exception localException)
/* 2101:     */     {
/* 2102:2271 */       if (localEJBContext != null) {
/* 2103:2273 */         localEJBContext.throwRemote(localException);
/* 2104:     */       }
/* 2105:2275 */       throw new EJBException(localException);
/* 2106:     */     }
/* 2107:     */     finally
/* 2108:     */     {
/* 2109:2279 */       this._comp.setCurrent(localJavaComponent);
/* 2110:     */     }
/* 2111:     */   }
/* 2112:     */   
/* 2113:     */   public BPWPayeeList getWirePayeeByType(BPWPayeeList paramBPWPayeeList)
/* 2114:     */     throws RemoteException
/* 2115:     */   {
/* 2116:2287 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2117:2288 */     EJBContext localEJBContext = null;
/* 2118:     */     try
/* 2119:     */     {
/* 2120:2291 */       localEJBContext = this._comp.getPooledInstance();
/* 2121:2292 */       if (localEJBContext == null) {
/* 2122:2294 */         localEJBContext = _create();
/* 2123:     */       }
/* 2124:2296 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2125:2297 */       if (localEJBContext.debug) {
/* 2126:2299 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWirePayeeByType"));
/* 2127:     */       }
/* 2128:2301 */       BPWPayeeList localBPWPayeeList2 = localBPWServicesBean
/* 2129:2302 */         .getWirePayeeByType(
/* 2130:2303 */         (BPWPayeeList)EJBContext.clone(paramBPWPayeeList));
/* 2131:     */       
/* 2132:2305 */       localBPWPayeeList2 = (BPWPayeeList)EJBContext.clone(localBPWPayeeList2);
/* 2133:2306 */       localEJBContext.returnToPool();
/* 2134:2307 */       return localBPWPayeeList2;
/* 2135:     */     }
/* 2136:     */     catch (Exception localException)
/* 2137:     */     {
/* 2138:2311 */       if (localEJBContext != null) {
/* 2139:2313 */         localEJBContext.throwRemote(localException);
/* 2140:     */       }
/* 2141:2315 */       throw new EJBException(localException);
/* 2142:     */     }
/* 2143:     */     finally
/* 2144:     */     {
/* 2145:2319 */       this._comp.setCurrent(localJavaComponent);
/* 2146:     */     }
/* 2147:     */   }
/* 2148:     */   
/* 2149:     */   public BPWPayeeList getWirePayeeByStatus(BPWPayeeList paramBPWPayeeList)
/* 2150:     */     throws RemoteException
/* 2151:     */   {
/* 2152:2327 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2153:2328 */     EJBContext localEJBContext = null;
/* 2154:     */     try
/* 2155:     */     {
/* 2156:2331 */       localEJBContext = this._comp.getPooledInstance();
/* 2157:2332 */       if (localEJBContext == null) {
/* 2158:2334 */         localEJBContext = _create();
/* 2159:     */       }
/* 2160:2336 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2161:2337 */       if (localEJBContext.debug) {
/* 2162:2339 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWirePayeeByStatus"));
/* 2163:     */       }
/* 2164:2341 */       BPWPayeeList localBPWPayeeList2 = localBPWServicesBean
/* 2165:2342 */         .getWirePayeeByStatus(
/* 2166:2343 */         (BPWPayeeList)EJBContext.clone(paramBPWPayeeList));
/* 2167:     */       
/* 2168:2345 */       localBPWPayeeList2 = (BPWPayeeList)EJBContext.clone(localBPWPayeeList2);
/* 2169:2346 */       localEJBContext.returnToPool();
/* 2170:2347 */       return localBPWPayeeList2;
/* 2171:     */     }
/* 2172:     */     catch (Exception localException)
/* 2173:     */     {
/* 2174:2351 */       if (localEJBContext != null) {
/* 2175:2353 */         localEJBContext.throwRemote(localException);
/* 2176:     */       }
/* 2177:2355 */       throw new EJBException(localException);
/* 2178:     */     }
/* 2179:     */     finally
/* 2180:     */     {
/* 2181:2359 */       this._comp.setCurrent(localJavaComponent);
/* 2182:     */     }
/* 2183:     */   }
/* 2184:     */   
/* 2185:     */   public BPWPayeeList getWirePayeeByGroup(BPWPayeeList paramBPWPayeeList)
/* 2186:     */     throws RemoteException
/* 2187:     */   {
/* 2188:2367 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2189:2368 */     EJBContext localEJBContext = null;
/* 2190:     */     try
/* 2191:     */     {
/* 2192:2371 */       localEJBContext = this._comp.getPooledInstance();
/* 2193:2372 */       if (localEJBContext == null) {
/* 2194:2374 */         localEJBContext = _create();
/* 2195:     */       }
/* 2196:2376 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2197:2377 */       if (localEJBContext.debug) {
/* 2198:2379 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWirePayeeByGroup"));
/* 2199:     */       }
/* 2200:2381 */       BPWPayeeList localBPWPayeeList2 = localBPWServicesBean
/* 2201:2382 */         .getWirePayeeByGroup(
/* 2202:2383 */         (BPWPayeeList)EJBContext.clone(paramBPWPayeeList));
/* 2203:     */       
/* 2204:2385 */       localBPWPayeeList2 = (BPWPayeeList)EJBContext.clone(localBPWPayeeList2);
/* 2205:2386 */       localEJBContext.returnToPool();
/* 2206:2387 */       return localBPWPayeeList2;
/* 2207:     */     }
/* 2208:     */     catch (Exception localException)
/* 2209:     */     {
/* 2210:2391 */       if (localEJBContext != null) {
/* 2211:2393 */         localEJBContext.throwRemote(localException);
/* 2212:     */       }
/* 2213:2395 */       throw new EJBException(localException);
/* 2214:     */     }
/* 2215:     */     finally
/* 2216:     */     {
/* 2217:2399 */       this._comp.setCurrent(localJavaComponent);
/* 2218:     */     }
/* 2219:     */   }
/* 2220:     */   
/* 2221:     */   public BPWPayeeList getWirePayeeByCustomer(BPWPayeeList paramBPWPayeeList)
/* 2222:     */     throws RemoteException
/* 2223:     */   {
/* 2224:2407 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2225:2408 */     EJBContext localEJBContext = null;
/* 2226:     */     try
/* 2227:     */     {
/* 2228:2411 */       localEJBContext = this._comp.getPooledInstance();
/* 2229:2412 */       if (localEJBContext == null) {
/* 2230:2414 */         localEJBContext = _create();
/* 2231:     */       }
/* 2232:2416 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2233:2417 */       if (localEJBContext.debug) {
/* 2234:2419 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWirePayeeByCustomer"));
/* 2235:     */       }
/* 2236:2421 */       BPWPayeeList localBPWPayeeList2 = localBPWServicesBean
/* 2237:2422 */         .getWirePayeeByCustomer(
/* 2238:2423 */         (BPWPayeeList)EJBContext.clone(paramBPWPayeeList));
/* 2239:     */       
/* 2240:2425 */       localBPWPayeeList2 = (BPWPayeeList)EJBContext.clone(localBPWPayeeList2);
/* 2241:2426 */       localEJBContext.returnToPool();
/* 2242:2427 */       return localBPWPayeeList2;
/* 2243:     */     }
/* 2244:     */     catch (Exception localException)
/* 2245:     */     {
/* 2246:2431 */       if (localEJBContext != null) {
/* 2247:2433 */         localEJBContext.throwRemote(localException);
/* 2248:     */       }
/* 2249:2435 */       throw new EJBException(localException);
/* 2250:     */     }
/* 2251:     */     finally
/* 2252:     */     {
/* 2253:2439 */       this._comp.setCurrent(localJavaComponent);
/* 2254:     */     }
/* 2255:     */   }
/* 2256:     */   
/* 2257:     */   public int addIntermediaryBanksToBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
/* 2258:     */     throws RemoteException
/* 2259:     */   {
/* 2260:2448 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2261:2449 */     EJBContext localEJBContext = null;
/* 2262:     */     try
/* 2263:     */     {
/* 2264:2452 */       localEJBContext = this._comp.getPooledInstance();
/* 2265:2453 */       if (localEJBContext == null) {
/* 2266:2455 */         localEJBContext = _create();
/* 2267:     */       }
/* 2268:2457 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2269:2458 */       if (localEJBContext.debug) {
/* 2270:2460 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addIntermediaryBanksToBeneficiary"));
/* 2271:     */       }
/* 2272:2462 */       int j = localBPWServicesBean
/* 2273:2463 */         .addIntermediaryBanksToBeneficiary(
/* 2274:2464 */         paramString, 
/* 2275:2465 */         BPWBankInfoSeqHelper.clone(paramArrayOfBPWBankInfo));
/* 2276:     */       
/* 2277:2467 */       localEJBContext.returnToPool();
/* 2278:2468 */       return j;
/* 2279:     */     }
/* 2280:     */     catch (Exception localException)
/* 2281:     */     {
/* 2282:2472 */       if (localEJBContext != null) {
/* 2283:2474 */         localEJBContext.throwRemote(localException);
/* 2284:     */       }
/* 2285:2476 */       throw new EJBException(localException);
/* 2286:     */     }
/* 2287:     */     finally
/* 2288:     */     {
/* 2289:2480 */       this._comp.setCurrent(localJavaComponent);
/* 2290:     */     }
/* 2291:     */   }
/* 2292:     */   
/* 2293:     */   public int delIntermediaryBanksFromBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
/* 2294:     */     throws RemoteException
/* 2295:     */   {
/* 2296:2489 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2297:2490 */     EJBContext localEJBContext = null;
/* 2298:     */     try
/* 2299:     */     {
/* 2300:2493 */       localEJBContext = this._comp.getPooledInstance();
/* 2301:2494 */       if (localEJBContext == null) {
/* 2302:2496 */         localEJBContext = _create();
/* 2303:     */       }
/* 2304:2498 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2305:2499 */       if (localEJBContext.debug) {
/* 2306:2501 */         localEJBContext.logger.debug(localEJBContext.debugMsg("delIntermediaryBanksFromBeneficiary"));
/* 2307:     */       }
/* 2308:2503 */       int j = localBPWServicesBean
/* 2309:2504 */         .delIntermediaryBanksFromBeneficiary(
/* 2310:2505 */         paramString, 
/* 2311:2506 */         BPWBankInfoSeqHelper.clone(paramArrayOfBPWBankInfo));
/* 2312:     */       
/* 2313:2508 */       localEJBContext.returnToPool();
/* 2314:2509 */       return j;
/* 2315:     */     }
/* 2316:     */     catch (Exception localException)
/* 2317:     */     {
/* 2318:2513 */       if (localEJBContext != null) {
/* 2319:2515 */         localEJBContext.throwRemote(localException);
/* 2320:     */       }
/* 2321:2517 */       throw new EJBException(localException);
/* 2322:     */     }
/* 2323:     */     finally
/* 2324:     */     {
/* 2325:2521 */       this._comp.setCurrent(localJavaComponent);
/* 2326:     */     }
/* 2327:     */   }
/* 2328:     */   
/* 2329:     */   public BPWBankInfo[] addWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
/* 2330:     */     throws RemoteException
/* 2331:     */   {
/* 2332:2529 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2333:2530 */     EJBContext localEJBContext = null;
/* 2334:     */     try
/* 2335:     */     {
/* 2336:2533 */       localEJBContext = this._comp.getPooledInstance();
/* 2337:2534 */       if (localEJBContext == null) {
/* 2338:2536 */         localEJBContext = _create();
/* 2339:     */       }
/* 2340:2538 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2341:2539 */       if (localEJBContext.debug) {
/* 2342:2541 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addWireBanks"));
/* 2343:     */       }
/* 2344:2543 */       BPWBankInfo[] arrayOfBPWBankInfo2 = localBPWServicesBean
/* 2345:2544 */         .addWireBanks(
/* 2346:2545 */         BPWBankInfoSeqHelper.clone(paramArrayOfBPWBankInfo));
/* 2347:     */       
/* 2348:2547 */       arrayOfBPWBankInfo2 = BPWBankInfoSeqHelper.clone(arrayOfBPWBankInfo2);
/* 2349:2548 */       localEJBContext.returnToPool();
/* 2350:2549 */       return arrayOfBPWBankInfo2;
/* 2351:     */     }
/* 2352:     */     catch (Exception localException)
/* 2353:     */     {
/* 2354:2553 */       if (localEJBContext != null) {
/* 2355:2555 */         localEJBContext.throwRemote(localException);
/* 2356:     */       }
/* 2357:2557 */       throw new EJBException(localException);
/* 2358:     */     }
/* 2359:     */     finally
/* 2360:     */     {
/* 2361:2561 */       this._comp.setCurrent(localJavaComponent);
/* 2362:     */     }
/* 2363:     */   }
/* 2364:     */   
/* 2365:     */   public BPWBankInfo[] modWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
/* 2366:     */     throws RemoteException
/* 2367:     */   {
/* 2368:2569 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2369:2570 */     EJBContext localEJBContext = null;
/* 2370:     */     try
/* 2371:     */     {
/* 2372:2573 */       localEJBContext = this._comp.getPooledInstance();
/* 2373:2574 */       if (localEJBContext == null) {
/* 2374:2576 */         localEJBContext = _create();
/* 2375:     */       }
/* 2376:2578 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2377:2579 */       if (localEJBContext.debug) {
/* 2378:2581 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modWireBanks"));
/* 2379:     */       }
/* 2380:2583 */       BPWBankInfo[] arrayOfBPWBankInfo2 = localBPWServicesBean
/* 2381:2584 */         .modWireBanks(
/* 2382:2585 */         BPWBankInfoSeqHelper.clone(paramArrayOfBPWBankInfo));
/* 2383:     */       
/* 2384:2587 */       arrayOfBPWBankInfo2 = BPWBankInfoSeqHelper.clone(arrayOfBPWBankInfo2);
/* 2385:2588 */       localEJBContext.returnToPool();
/* 2386:2589 */       return arrayOfBPWBankInfo2;
/* 2387:     */     }
/* 2388:     */     catch (Exception localException)
/* 2389:     */     {
/* 2390:2593 */       if (localEJBContext != null) {
/* 2391:2595 */         localEJBContext.throwRemote(localException);
/* 2392:     */       }
/* 2393:2597 */       throw new EJBException(localException);
/* 2394:     */     }
/* 2395:     */     finally
/* 2396:     */     {
/* 2397:2601 */       this._comp.setCurrent(localJavaComponent);
/* 2398:     */     }
/* 2399:     */   }
/* 2400:     */   
/* 2401:     */   public BPWBankInfo[] delWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
/* 2402:     */     throws RemoteException
/* 2403:     */   {
/* 2404:2609 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2405:2610 */     EJBContext localEJBContext = null;
/* 2406:     */     try
/* 2407:     */     {
/* 2408:2613 */       localEJBContext = this._comp.getPooledInstance();
/* 2409:2614 */       if (localEJBContext == null) {
/* 2410:2616 */         localEJBContext = _create();
/* 2411:     */       }
/* 2412:2618 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2413:2619 */       if (localEJBContext.debug) {
/* 2414:2621 */         localEJBContext.logger.debug(localEJBContext.debugMsg("delWireBanks"));
/* 2415:     */       }
/* 2416:2623 */       BPWBankInfo[] arrayOfBPWBankInfo2 = localBPWServicesBean
/* 2417:2624 */         .delWireBanks(
/* 2418:2625 */         BPWBankInfoSeqHelper.clone(paramArrayOfBPWBankInfo));
/* 2419:     */       
/* 2420:2627 */       arrayOfBPWBankInfo2 = BPWBankInfoSeqHelper.clone(arrayOfBPWBankInfo2);
/* 2421:2628 */       localEJBContext.returnToPool();
/* 2422:2629 */       return arrayOfBPWBankInfo2;
/* 2423:     */     }
/* 2424:     */     catch (Exception localException)
/* 2425:     */     {
/* 2426:2633 */       if (localEJBContext != null) {
/* 2427:2635 */         localEJBContext.throwRemote(localException);
/* 2428:     */       }
/* 2429:2637 */       throw new EJBException(localException);
/* 2430:     */     }
/* 2431:     */     finally
/* 2432:     */     {
/* 2433:2641 */       this._comp.setCurrent(localJavaComponent);
/* 2434:     */     }
/* 2435:     */   }
/* 2436:     */   
/* 2437:     */   public BPWBankInfo[] getWireBanks(String paramString1, String paramString2, String paramString3, String paramString4)
/* 2438:     */     throws RemoteException
/* 2439:     */   {
/* 2440:2652 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2441:2653 */     EJBContext localEJBContext = null;
/* 2442:     */     try
/* 2443:     */     {
/* 2444:2656 */       localEJBContext = this._comp.getPooledInstance();
/* 2445:2657 */       if (localEJBContext == null) {
/* 2446:2659 */         localEJBContext = _create();
/* 2447:     */       }
/* 2448:2661 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2449:2662 */       if (localEJBContext.debug) {
/* 2450:2664 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireBanks"));
/* 2451:     */       }
/* 2452:2666 */       BPWBankInfo[] arrayOfBPWBankInfo2 = localBPWServicesBean
/* 2453:2667 */         .getWireBanks(
/* 2454:2668 */         paramString1, 
/* 2455:2669 */         paramString2, 
/* 2456:2670 */         paramString3, 
/* 2457:2671 */         paramString4);
/* 2458:     */       
/* 2459:2673 */       arrayOfBPWBankInfo2 = BPWBankInfoSeqHelper.clone(arrayOfBPWBankInfo2);
/* 2460:2674 */       localEJBContext.returnToPool();
/* 2461:2675 */       return arrayOfBPWBankInfo2;
/* 2462:     */     }
/* 2463:     */     catch (Exception localException)
/* 2464:     */     {
/* 2465:2679 */       if (localEJBContext != null) {
/* 2466:2681 */         localEJBContext.throwRemote(localException);
/* 2467:     */       }
/* 2468:2683 */       throw new EJBException(localException);
/* 2469:     */     }
/* 2470:     */     finally
/* 2471:     */     {
/* 2472:2687 */       this._comp.setCurrent(localJavaComponent);
/* 2473:     */     }
/* 2474:     */   }
/* 2475:     */   
/* 2476:     */   public BPWBankInfo[] getWireBanksByRTN(String paramString)
/* 2477:     */     throws RemoteException
/* 2478:     */   {
/* 2479:2695 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2480:2696 */     EJBContext localEJBContext = null;
/* 2481:     */     try
/* 2482:     */     {
/* 2483:2699 */       localEJBContext = this._comp.getPooledInstance();
/* 2484:2700 */       if (localEJBContext == null) {
/* 2485:2702 */         localEJBContext = _create();
/* 2486:     */       }
/* 2487:2704 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2488:2705 */       if (localEJBContext.debug) {
/* 2489:2707 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireBanksByRTN"));
/* 2490:     */       }
/* 2491:2709 */       BPWBankInfo[] arrayOfBPWBankInfo2 = localBPWServicesBean
/* 2492:2710 */         .getWireBanksByRTN(
/* 2493:2711 */         paramString);
/* 2494:     */       
/* 2495:2713 */       arrayOfBPWBankInfo2 = BPWBankInfoSeqHelper.clone(arrayOfBPWBankInfo2);
/* 2496:2714 */       localEJBContext.returnToPool();
/* 2497:2715 */       return arrayOfBPWBankInfo2;
/* 2498:     */     }
/* 2499:     */     catch (Exception localException)
/* 2500:     */     {
/* 2501:2719 */       if (localEJBContext != null) {
/* 2502:2721 */         localEJBContext.throwRemote(localException);
/* 2503:     */       }
/* 2504:2723 */       throw new EJBException(localException);
/* 2505:     */     }
/* 2506:     */     finally
/* 2507:     */     {
/* 2508:2727 */       this._comp.setCurrent(localJavaComponent);
/* 2509:     */     }
/* 2510:     */   }
/* 2511:     */   
/* 2512:     */   public BPWBankInfo getWireBanksByID(String paramString)
/* 2513:     */     throws RemoteException
/* 2514:     */   {
/* 2515:2735 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2516:2736 */     EJBContext localEJBContext = null;
/* 2517:     */     try
/* 2518:     */     {
/* 2519:2739 */       localEJBContext = this._comp.getPooledInstance();
/* 2520:2740 */       if (localEJBContext == null) {
/* 2521:2742 */         localEJBContext = _create();
/* 2522:     */       }
/* 2523:2744 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2524:2745 */       if (localEJBContext.debug) {
/* 2525:2747 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getWireBanksByID"));
/* 2526:     */       }
/* 2527:2749 */       BPWBankInfo localBPWBankInfo2 = localBPWServicesBean
/* 2528:2750 */         .getWireBanksByID(
/* 2529:2751 */         paramString);
/* 2530:     */       
/* 2531:2753 */       localBPWBankInfo2 = (BPWBankInfo)EJBContext.clone(localBPWBankInfo2);
/* 2532:2754 */       localEJBContext.returnToPool();
/* 2533:2755 */       return localBPWBankInfo2;
/* 2534:     */     }
/* 2535:     */     catch (Exception localException)
/* 2536:     */     {
/* 2537:2759 */       if (localEJBContext != null) {
/* 2538:2761 */         localEJBContext.throwRemote(localException);
/* 2539:     */       }
/* 2540:2763 */       throw new EJBException(localException);
/* 2541:     */     }
/* 2542:     */     finally
/* 2543:     */     {
/* 2544:2767 */       this._comp.setCurrent(localJavaComponent);
/* 2545:     */     }
/* 2546:     */   }
/* 2547:     */   
/* 2548:     */   public void processWireApprovalRslt(WireInfo[] paramArrayOfWireInfo)
/* 2549:     */     throws RemoteException
/* 2550:     */   {
/* 2551:2775 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2552:2776 */     EJBContext localEJBContext = null;
/* 2553:     */     try
/* 2554:     */     {
/* 2555:2779 */       localEJBContext = this._comp.getPooledInstance();
/* 2556:2780 */       if (localEJBContext == null) {
/* 2557:2782 */         localEJBContext = _create();
/* 2558:     */       }
/* 2559:2784 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2560:2785 */       if (localEJBContext.debug) {
/* 2561:2787 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processWireApprovalRslt"));
/* 2562:     */       }
/* 2563:2790 */       localBPWServicesBean.processWireApprovalRslt(
/* 2564:2791 */         WireInfoSeqHelper.clone(paramArrayOfWireInfo));
/* 2565:     */       
/* 2566:2793 */       localEJBContext.returnToPool();
/* 2567:     */     }
/* 2568:     */     catch (Exception localException)
/* 2569:     */     {
/* 2570:2797 */       if (localEJBContext != null) {
/* 2571:2799 */         localEJBContext.throwRemote(localException);
/* 2572:     */       }
/* 2573:2801 */       throw new EJBException(localException);
/* 2574:     */     }
/* 2575:     */     finally
/* 2576:     */     {
/* 2577:2805 */       this._comp.setCurrent(localJavaComponent);
/* 2578:     */     }
/* 2579:     */   }
/* 2580:     */   
/* 2581:     */   public void processWireBackendlRslt(WireInfo[] paramArrayOfWireInfo)
/* 2582:     */     throws RemoteException
/* 2583:     */   {
/* 2584:2813 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2585:2814 */     EJBContext localEJBContext = null;
/* 2586:     */     try
/* 2587:     */     {
/* 2588:2817 */       localEJBContext = this._comp.getPooledInstance();
/* 2589:2818 */       if (localEJBContext == null) {
/* 2590:2820 */         localEJBContext = _create();
/* 2591:     */       }
/* 2592:2822 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2593:2823 */       if (localEJBContext.debug) {
/* 2594:2825 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processWireBackendlRslt"));
/* 2595:     */       }
/* 2596:2828 */       localBPWServicesBean.processWireBackendlRslt(
/* 2597:2829 */         WireInfoSeqHelper.clone(paramArrayOfWireInfo));
/* 2598:     */       
/* 2599:2831 */       localEJBContext.returnToPool();
/* 2600:     */     }
/* 2601:     */     catch (Exception localException)
/* 2602:     */     {
/* 2603:2835 */       if (localEJBContext != null) {
/* 2604:2837 */         localEJBContext.throwRemote(localException);
/* 2605:     */       }
/* 2606:2839 */       throw new EJBException(localException);
/* 2607:     */     }
/* 2608:     */     finally
/* 2609:     */     {
/* 2610:2843 */       this._comp.setCurrent(localJavaComponent);
/* 2611:     */     }
/* 2612:     */   }
/* 2613:     */   
/* 2614:     */   public void processWireApprovalRevertRslt(WireInfo[] paramArrayOfWireInfo)
/* 2615:     */     throws RemoteException
/* 2616:     */   {
/* 2617:2851 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2618:2852 */     EJBContext localEJBContext = null;
/* 2619:     */     try
/* 2620:     */     {
/* 2621:2855 */       localEJBContext = this._comp.getPooledInstance();
/* 2622:2856 */       if (localEJBContext == null) {
/* 2623:2858 */         localEJBContext = _create();
/* 2624:     */       }
/* 2625:2860 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2626:2861 */       if (localEJBContext.debug) {
/* 2627:2863 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processWireApprovalRevertRslt"));
/* 2628:     */       }
/* 2629:2866 */       localBPWServicesBean.processWireApprovalRevertRslt(
/* 2630:2867 */         WireInfoSeqHelper.clone(paramArrayOfWireInfo));
/* 2631:     */       
/* 2632:2869 */       localEJBContext.returnToPool();
/* 2633:     */     }
/* 2634:     */     catch (Exception localException)
/* 2635:     */     {
/* 2636:2873 */       if (localEJBContext != null) {
/* 2637:2875 */         localEJBContext.throwRemote(localException);
/* 2638:     */       }
/* 2639:2877 */       throw new EJBException(localException);
/* 2640:     */     }
/* 2641:     */     finally
/* 2642:     */     {
/* 2643:2881 */       this._comp.setCurrent(localJavaComponent);
/* 2644:     */     }
/* 2645:     */   }
/* 2646:     */   
/* 2647:     */   public BPWFIInfo addBPWFIInfo(BPWFIInfo paramBPWFIInfo)
/* 2648:     */     throws RemoteException
/* 2649:     */   {
/* 2650:2889 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2651:2890 */     EJBContext localEJBContext = null;
/* 2652:     */     try
/* 2653:     */     {
/* 2654:2893 */       localEJBContext = this._comp.getPooledInstance();
/* 2655:2894 */       if (localEJBContext == null) {
/* 2656:2896 */         localEJBContext = _create();
/* 2657:     */       }
/* 2658:2898 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2659:2899 */       if (localEJBContext.debug) {
/* 2660:2901 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addBPWFIInfo"));
/* 2661:     */       }
/* 2662:2903 */       BPWFIInfo localBPWFIInfo2 = localBPWServicesBean
/* 2663:2904 */         .addBPWFIInfo(
/* 2664:2905 */         (BPWFIInfo)EJBContext.clone(paramBPWFIInfo));
/* 2665:     */       
/* 2666:2907 */       localBPWFIInfo2 = (BPWFIInfo)EJBContext.clone(localBPWFIInfo2);
/* 2667:2908 */       localEJBContext.returnToPool();
/* 2668:2909 */       return localBPWFIInfo2;
/* 2669:     */     }
/* 2670:     */     catch (Exception localException)
/* 2671:     */     {
/* 2672:2913 */       if (localEJBContext != null) {
/* 2673:2915 */         localEJBContext.throwRemote(localException);
/* 2674:     */       }
/* 2675:2917 */       throw new EJBException(localException);
/* 2676:     */     }
/* 2677:     */     finally
/* 2678:     */     {
/* 2679:2921 */       this._comp.setCurrent(localJavaComponent);
/* 2680:     */     }
/* 2681:     */   }
/* 2682:     */   
/* 2683:     */   public BPWFIInfo modBPWFIInfo(BPWFIInfo paramBPWFIInfo)
/* 2684:     */     throws RemoteException
/* 2685:     */   {
/* 2686:2929 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2687:2930 */     EJBContext localEJBContext = null;
/* 2688:     */     try
/* 2689:     */     {
/* 2690:2933 */       localEJBContext = this._comp.getPooledInstance();
/* 2691:2934 */       if (localEJBContext == null) {
/* 2692:2936 */         localEJBContext = _create();
/* 2693:     */       }
/* 2694:2938 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2695:2939 */       if (localEJBContext.debug) {
/* 2696:2941 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modBPWFIInfo"));
/* 2697:     */       }
/* 2698:2943 */       BPWFIInfo localBPWFIInfo2 = localBPWServicesBean
/* 2699:2944 */         .modBPWFIInfo(
/* 2700:2945 */         (BPWFIInfo)EJBContext.clone(paramBPWFIInfo));
/* 2701:     */       
/* 2702:2947 */       localBPWFIInfo2 = (BPWFIInfo)EJBContext.clone(localBPWFIInfo2);
/* 2703:2948 */       localEJBContext.returnToPool();
/* 2704:2949 */       return localBPWFIInfo2;
/* 2705:     */     }
/* 2706:     */     catch (Exception localException)
/* 2707:     */     {
/* 2708:2953 */       if (localEJBContext != null) {
/* 2709:2955 */         localEJBContext.throwRemote(localException);
/* 2710:     */       }
/* 2711:2957 */       throw new EJBException(localException);
/* 2712:     */     }
/* 2713:     */     finally
/* 2714:     */     {
/* 2715:2961 */       this._comp.setCurrent(localJavaComponent);
/* 2716:     */     }
/* 2717:     */   }
/* 2718:     */   
/* 2719:     */   public BPWFIInfo canBPWFIInfo(BPWFIInfo paramBPWFIInfo)
/* 2720:     */     throws RemoteException
/* 2721:     */   {
/* 2722:2969 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2723:2970 */     EJBContext localEJBContext = null;
/* 2724:     */     try
/* 2725:     */     {
/* 2726:2973 */       localEJBContext = this._comp.getPooledInstance();
/* 2727:2974 */       if (localEJBContext == null) {
/* 2728:2976 */         localEJBContext = _create();
/* 2729:     */       }
/* 2730:2978 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2731:2979 */       if (localEJBContext.debug) {
/* 2732:2981 */         localEJBContext.logger.debug(localEJBContext.debugMsg("canBPWFIInfo"));
/* 2733:     */       }
/* 2734:2983 */       BPWFIInfo localBPWFIInfo2 = localBPWServicesBean
/* 2735:2984 */         .canBPWFIInfo(
/* 2736:2985 */         (BPWFIInfo)EJBContext.clone(paramBPWFIInfo));
/* 2737:     */       
/* 2738:2987 */       localBPWFIInfo2 = (BPWFIInfo)EJBContext.clone(localBPWFIInfo2);
/* 2739:2988 */       localEJBContext.returnToPool();
/* 2740:2989 */       return localBPWFIInfo2;
/* 2741:     */     }
/* 2742:     */     catch (Exception localException)
/* 2743:     */     {
/* 2744:2993 */       if (localEJBContext != null) {
/* 2745:2995 */         localEJBContext.throwRemote(localException);
/* 2746:     */       }
/* 2747:2997 */       throw new EJBException(localException);
/* 2748:     */     }
/* 2749:     */     finally
/* 2750:     */     {
/* 2751:3001 */       this._comp.setCurrent(localJavaComponent);
/* 2752:     */     }
/* 2753:     */   }
/* 2754:     */   
/* 2755:     */   public BPWFIInfo activateBPWFI(String paramString)
/* 2756:     */     throws RemoteException
/* 2757:     */   {
/* 2758:3009 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2759:3010 */     EJBContext localEJBContext = null;
/* 2760:     */     try
/* 2761:     */     {
/* 2762:3013 */       localEJBContext = this._comp.getPooledInstance();
/* 2763:3014 */       if (localEJBContext == null) {
/* 2764:3016 */         localEJBContext = _create();
/* 2765:     */       }
/* 2766:3018 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2767:3019 */       if (localEJBContext.debug) {
/* 2768:3021 */         localEJBContext.logger.debug(localEJBContext.debugMsg("activateBPWFI"));
/* 2769:     */       }
/* 2770:3023 */       BPWFIInfo localBPWFIInfo2 = localBPWServicesBean
/* 2771:3024 */         .activateBPWFI(
/* 2772:3025 */         paramString);
/* 2773:     */       
/* 2774:3027 */       localBPWFIInfo2 = (BPWFIInfo)EJBContext.clone(localBPWFIInfo2);
/* 2775:3028 */       localEJBContext.returnToPool();
/* 2776:3029 */       return localBPWFIInfo2;
/* 2777:     */     }
/* 2778:     */     catch (Exception localException)
/* 2779:     */     {
/* 2780:3033 */       if (localEJBContext != null) {
/* 2781:3035 */         localEJBContext.throwRemote(localException);
/* 2782:     */       }
/* 2783:3037 */       throw new EJBException(localException);
/* 2784:     */     }
/* 2785:     */     finally
/* 2786:     */     {
/* 2787:3041 */       this._comp.setCurrent(localJavaComponent);
/* 2788:     */     }
/* 2789:     */   }
/* 2790:     */   
/* 2791:     */   public BPWFIInfo getBPWFIInfo(String paramString)
/* 2792:     */     throws RemoteException
/* 2793:     */   {
/* 2794:3049 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2795:3050 */     EJBContext localEJBContext = null;
/* 2796:     */     try
/* 2797:     */     {
/* 2798:3053 */       localEJBContext = this._comp.getPooledInstance();
/* 2799:3054 */       if (localEJBContext == null) {
/* 2800:3056 */         localEJBContext = _create();
/* 2801:     */       }
/* 2802:3058 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2803:3059 */       if (localEJBContext.debug) {
/* 2804:3061 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBPWFIInfo"));
/* 2805:     */       }
/* 2806:3063 */       BPWFIInfo localBPWFIInfo2 = localBPWServicesBean
/* 2807:3064 */         .getBPWFIInfo(
/* 2808:3065 */         paramString);
/* 2809:     */       
/* 2810:3067 */       localBPWFIInfo2 = (BPWFIInfo)EJBContext.clone(localBPWFIInfo2);
/* 2811:3068 */       localEJBContext.returnToPool();
/* 2812:3069 */       return localBPWFIInfo2;
/* 2813:     */     }
/* 2814:     */     catch (Exception localException)
/* 2815:     */     {
/* 2816:3073 */       if (localEJBContext != null) {
/* 2817:3075 */         localEJBContext.throwRemote(localException);
/* 2818:     */       }
/* 2819:3077 */       throw new EJBException(localException);
/* 2820:     */     }
/* 2821:     */     finally
/* 2822:     */     {
/* 2823:3081 */       this._comp.setCurrent(localJavaComponent);
/* 2824:     */     }
/* 2825:     */   }
/* 2826:     */   
/* 2827:     */   public BPWFIInfo[] getBPWFIInfo(String[] paramArrayOfString)
/* 2828:     */     throws RemoteException
/* 2829:     */   {
/* 2830:3089 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2831:3090 */     EJBContext localEJBContext = null;
/* 2832:     */     try
/* 2833:     */     {
/* 2834:3093 */       localEJBContext = this._comp.getPooledInstance();
/* 2835:3094 */       if (localEJBContext == null) {
/* 2836:3096 */         localEJBContext = _create();
/* 2837:     */       }
/* 2838:3098 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2839:3099 */       if (localEJBContext.debug) {
/* 2840:3101 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBPWFIInfo"));
/* 2841:     */       }
/* 2842:3103 */       BPWFIInfo[] arrayOfBPWFIInfo2 = localBPWServicesBean
/* 2843:3104 */         .getBPWFIInfo(
/* 2844:3105 */         StringSeqHelper.clone(paramArrayOfString));
/* 2845:     */       
/* 2846:3107 */       arrayOfBPWFIInfo2 = BPWFIInfoSeqHelper.clone(arrayOfBPWFIInfo2);
/* 2847:3108 */       localEJBContext.returnToPool();
/* 2848:3109 */       return arrayOfBPWFIInfo2;
/* 2849:     */     }
/* 2850:     */     catch (Exception localException)
/* 2851:     */     {
/* 2852:3113 */       if (localEJBContext != null) {
/* 2853:3115 */         localEJBContext.throwRemote(localException);
/* 2854:     */       }
/* 2855:3117 */       throw new EJBException(localException);
/* 2856:     */     }
/* 2857:     */     finally
/* 2858:     */     {
/* 2859:3121 */       this._comp.setCurrent(localJavaComponent);
/* 2860:     */     }
/* 2861:     */   }
/* 2862:     */   
/* 2863:     */   public BPWFIInfo[] getBPWFIInfoByType(String paramString)
/* 2864:     */     throws RemoteException
/* 2865:     */   {
/* 2866:3129 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2867:3130 */     EJBContext localEJBContext = null;
/* 2868:     */     try
/* 2869:     */     {
/* 2870:3133 */       localEJBContext = this._comp.getPooledInstance();
/* 2871:3134 */       if (localEJBContext == null) {
/* 2872:3136 */         localEJBContext = _create();
/* 2873:     */       }
/* 2874:3138 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2875:3139 */       if (localEJBContext.debug) {
/* 2876:3141 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBPWFIInfoByType"));
/* 2877:     */       }
/* 2878:3143 */       BPWFIInfo[] arrayOfBPWFIInfo2 = localBPWServicesBean
/* 2879:3144 */         .getBPWFIInfoByType(
/* 2880:3145 */         paramString);
/* 2881:     */       
/* 2882:3147 */       arrayOfBPWFIInfo2 = BPWFIInfoSeqHelper.clone(arrayOfBPWFIInfo2);
/* 2883:3148 */       localEJBContext.returnToPool();
/* 2884:3149 */       return arrayOfBPWFIInfo2;
/* 2885:     */     }
/* 2886:     */     catch (Exception localException)
/* 2887:     */     {
/* 2888:3153 */       if (localEJBContext != null) {
/* 2889:3155 */         localEJBContext.throwRemote(localException);
/* 2890:     */       }
/* 2891:3157 */       throw new EJBException(localException);
/* 2892:     */     }
/* 2893:     */     finally
/* 2894:     */     {
/* 2895:3161 */       this._comp.setCurrent(localJavaComponent);
/* 2896:     */     }
/* 2897:     */   }
/* 2898:     */   
/* 2899:     */   public BPWFIInfo[] getBPWFIInfoByStatus(String paramString)
/* 2900:     */     throws RemoteException
/* 2901:     */   {
/* 2902:3169 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2903:3170 */     EJBContext localEJBContext = null;
/* 2904:     */     try
/* 2905:     */     {
/* 2906:3173 */       localEJBContext = this._comp.getPooledInstance();
/* 2907:3174 */       if (localEJBContext == null) {
/* 2908:3176 */         localEJBContext = _create();
/* 2909:     */       }
/* 2910:3178 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2911:3179 */       if (localEJBContext.debug) {
/* 2912:3181 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBPWFIInfoByStatus"));
/* 2913:     */       }
/* 2914:3183 */       BPWFIInfo[] arrayOfBPWFIInfo2 = localBPWServicesBean
/* 2915:3184 */         .getBPWFIInfoByStatus(
/* 2916:3185 */         paramString);
/* 2917:     */       
/* 2918:3187 */       arrayOfBPWFIInfo2 = BPWFIInfoSeqHelper.clone(arrayOfBPWFIInfo2);
/* 2919:3188 */       localEJBContext.returnToPool();
/* 2920:3189 */       return arrayOfBPWFIInfo2;
/* 2921:     */     }
/* 2922:     */     catch (Exception localException)
/* 2923:     */     {
/* 2924:3193 */       if (localEJBContext != null) {
/* 2925:3195 */         localEJBContext.throwRemote(localException);
/* 2926:     */       }
/* 2927:3197 */       throw new EJBException(localException);
/* 2928:     */     }
/* 2929:     */     finally
/* 2930:     */     {
/* 2931:3201 */       this._comp.setCurrent(localJavaComponent);
/* 2932:     */     }
/* 2933:     */   }
/* 2934:     */   
/* 2935:     */   public BPWFIInfo[] getBPWFIInfoByGroup(String paramString)
/* 2936:     */     throws RemoteException
/* 2937:     */   {
/* 2938:3209 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2939:3210 */     EJBContext localEJBContext = null;
/* 2940:     */     try
/* 2941:     */     {
/* 2942:3213 */       localEJBContext = this._comp.getPooledInstance();
/* 2943:3214 */       if (localEJBContext == null) {
/* 2944:3216 */         localEJBContext = _create();
/* 2945:     */       }
/* 2946:3218 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2947:3219 */       if (localEJBContext.debug) {
/* 2948:3221 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBPWFIInfoByGroup"));
/* 2949:     */       }
/* 2950:3223 */       BPWFIInfo[] arrayOfBPWFIInfo2 = localBPWServicesBean
/* 2951:3224 */         .getBPWFIInfoByGroup(
/* 2952:3225 */         paramString);
/* 2953:     */       
/* 2954:3227 */       arrayOfBPWFIInfo2 = BPWFIInfoSeqHelper.clone(arrayOfBPWFIInfo2);
/* 2955:3228 */       localEJBContext.returnToPool();
/* 2956:3229 */       return arrayOfBPWFIInfo2;
/* 2957:     */     }
/* 2958:     */     catch (Exception localException)
/* 2959:     */     {
/* 2960:3233 */       if (localEJBContext != null) {
/* 2961:3235 */         localEJBContext.throwRemote(localException);
/* 2962:     */       }
/* 2963:3237 */       throw new EJBException(localException);
/* 2964:     */     }
/* 2965:     */     finally
/* 2966:     */     {
/* 2967:3241 */       this._comp.setCurrent(localJavaComponent);
/* 2968:     */     }
/* 2969:     */   }
/* 2970:     */   
/* 2971:     */   public BPWFIInfo getBPWFIInfoByACHId(String paramString)
/* 2972:     */     throws RemoteException
/* 2973:     */   {
/* 2974:3249 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2975:3250 */     EJBContext localEJBContext = null;
/* 2976:     */     try
/* 2977:     */     {
/* 2978:3253 */       localEJBContext = this._comp.getPooledInstance();
/* 2979:3254 */       if (localEJBContext == null) {
/* 2980:3256 */         localEJBContext = _create();
/* 2981:     */       }
/* 2982:3258 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 2983:3259 */       if (localEJBContext.debug) {
/* 2984:3261 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBPWFIInfoByACHId"));
/* 2985:     */       }
/* 2986:3263 */       BPWFIInfo localBPWFIInfo2 = localBPWServicesBean
/* 2987:3264 */         .getBPWFIInfoByACHId(
/* 2988:3265 */         paramString);
/* 2989:     */       
/* 2990:3267 */       localBPWFIInfo2 = (BPWFIInfo)EJBContext.clone(localBPWFIInfo2);
/* 2991:3268 */       localEJBContext.returnToPool();
/* 2992:3269 */       return localBPWFIInfo2;
/* 2993:     */     }
/* 2994:     */     catch (Exception localException)
/* 2995:     */     {
/* 2996:3273 */       if (localEJBContext != null) {
/* 2997:3275 */         localEJBContext.throwRemote(localException);
/* 2998:     */       }
/* 2999:3277 */       throw new EJBException(localException);
/* 3000:     */     }
/* 3001:     */     finally
/* 3002:     */     {
/* 3003:3281 */       this._comp.setCurrent(localJavaComponent);
/* 3004:     */     }
/* 3005:     */   }
/* 3006:     */   
/* 3007:     */   public BPWFIInfo getBPWFIInfoByRTN(String paramString)
/* 3008:     */     throws RemoteException
/* 3009:     */   {
/* 3010:3289 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3011:3290 */     EJBContext localEJBContext = null;
/* 3012:     */     try
/* 3013:     */     {
/* 3014:3293 */       localEJBContext = this._comp.getPooledInstance();
/* 3015:3294 */       if (localEJBContext == null) {
/* 3016:3296 */         localEJBContext = _create();
/* 3017:     */       }
/* 3018:3298 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3019:3299 */       if (localEJBContext.debug) {
/* 3020:3301 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBPWFIInfoByRTN"));
/* 3021:     */       }
/* 3022:3303 */       BPWFIInfo localBPWFIInfo2 = localBPWServicesBean
/* 3023:3304 */         .getBPWFIInfoByRTN(
/* 3024:3305 */         paramString);
/* 3025:     */       
/* 3026:3307 */       localBPWFIInfo2 = (BPWFIInfo)EJBContext.clone(localBPWFIInfo2);
/* 3027:3308 */       localEJBContext.returnToPool();
/* 3028:3309 */       return localBPWFIInfo2;
/* 3029:     */     }
/* 3030:     */     catch (Exception localException)
/* 3031:     */     {
/* 3032:3313 */       if (localEJBContext != null) {
/* 3033:3315 */         localEJBContext.throwRemote(localException);
/* 3034:     */       }
/* 3035:3317 */       throw new EJBException(localException);
/* 3036:     */     }
/* 3037:     */     finally
/* 3038:     */     {
/* 3039:3321 */       this._comp.setCurrent(localJavaComponent);
/* 3040:     */     }
/* 3041:     */   }
/* 3042:     */   
/* 3043:     */   public RPPSFIInfo addRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
/* 3044:     */     throws RemoteException, FFSException
/* 3045:     */   {
/* 3046:3329 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3047:3330 */     EJBContext localEJBContext = null;
/* 3048:     */     try
/* 3049:     */     {
/* 3050:3333 */       localEJBContext = this._comp.getPooledInstance();
/* 3051:3334 */       if (localEJBContext == null) {
/* 3052:3336 */         localEJBContext = _create();
/* 3053:     */       }
/* 3054:3338 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3055:3339 */       if (localEJBContext.debug) {
/* 3056:3341 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addRPPSFIInfo"));
/* 3057:     */       }
/* 3058:3343 */       RPPSFIInfo localRPPSFIInfo2 = localBPWServicesBean
/* 3059:3344 */         .addRPPSFIInfo(
/* 3060:3345 */         (RPPSFIInfo)EJBContext.clone(paramRPPSFIInfo));
/* 3061:     */       
/* 3062:3347 */       localRPPSFIInfo2 = (RPPSFIInfo)EJBContext.clone(localRPPSFIInfo2);
/* 3063:3348 */       localEJBContext.returnToPool();
/* 3064:3349 */       return localRPPSFIInfo2;
/* 3065:     */     }
/* 3066:     */     catch (Exception localException)
/* 3067:     */     {
/* 3068:3353 */       if (localEJBContext != null)
/* 3069:     */       {
/* 3070:3355 */         if ((localException instanceof FFSException)) {
/* 3071:3357 */           throw ((FFSException)EJBContext.clone(localException));
/* 3072:     */         }
/* 3073:3359 */         localEJBContext.throwRemote(localException);
/* 3074:     */       }
/* 3075:3361 */       throw new EJBException(localException);
/* 3076:     */     }
/* 3077:     */     finally
/* 3078:     */     {
/* 3079:3365 */       this._comp.setCurrent(localJavaComponent);
/* 3080:     */     }
/* 3081:     */   }
/* 3082:     */   
/* 3083:     */   public RPPSFIInfo getRPPSFIInfoByFIId(String paramString)
/* 3084:     */     throws RemoteException, FFSException
/* 3085:     */   {
/* 3086:3373 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3087:3374 */     EJBContext localEJBContext = null;
/* 3088:     */     try
/* 3089:     */     {
/* 3090:3377 */       localEJBContext = this._comp.getPooledInstance();
/* 3091:3378 */       if (localEJBContext == null) {
/* 3092:3380 */         localEJBContext = _create();
/* 3093:     */       }
/* 3094:3382 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3095:3383 */       if (localEJBContext.debug) {
/* 3096:3385 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRPPSFIInfoByFIId"));
/* 3097:     */       }
/* 3098:3387 */       RPPSFIInfo localRPPSFIInfo2 = localBPWServicesBean
/* 3099:3388 */         .getRPPSFIInfoByFIId(
/* 3100:3389 */         paramString);
/* 3101:     */       
/* 3102:3391 */       localRPPSFIInfo2 = (RPPSFIInfo)EJBContext.clone(localRPPSFIInfo2);
/* 3103:3392 */       localEJBContext.returnToPool();
/* 3104:3393 */       return localRPPSFIInfo2;
/* 3105:     */     }
/* 3106:     */     catch (Exception localException)
/* 3107:     */     {
/* 3108:3397 */       if (localEJBContext != null)
/* 3109:     */       {
/* 3110:3399 */         if ((localException instanceof FFSException)) {
/* 3111:3401 */           throw ((FFSException)EJBContext.clone(localException));
/* 3112:     */         }
/* 3113:3403 */         localEJBContext.throwRemote(localException);
/* 3114:     */       }
/* 3115:3405 */       throw new EJBException(localException);
/* 3116:     */     }
/* 3117:     */     finally
/* 3118:     */     {
/* 3119:3409 */       this._comp.setCurrent(localJavaComponent);
/* 3120:     */     }
/* 3121:     */   }
/* 3122:     */   
/* 3123:     */   public RPPSFIInfo getRPPSFIInfoByFIRPPSId(String paramString)
/* 3124:     */     throws RemoteException, FFSException
/* 3125:     */   {
/* 3126:3417 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3127:3418 */     EJBContext localEJBContext = null;
/* 3128:     */     try
/* 3129:     */     {
/* 3130:3421 */       localEJBContext = this._comp.getPooledInstance();
/* 3131:3422 */       if (localEJBContext == null) {
/* 3132:3424 */         localEJBContext = _create();
/* 3133:     */       }
/* 3134:3426 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3135:3427 */       if (localEJBContext.debug) {
/* 3136:3429 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRPPSFIInfoByFIRPPSId"));
/* 3137:     */       }
/* 3138:3431 */       RPPSFIInfo localRPPSFIInfo2 = localBPWServicesBean
/* 3139:3432 */         .getRPPSFIInfoByFIRPPSId(
/* 3140:3433 */         paramString);
/* 3141:     */       
/* 3142:3435 */       localRPPSFIInfo2 = (RPPSFIInfo)EJBContext.clone(localRPPSFIInfo2);
/* 3143:3436 */       localEJBContext.returnToPool();
/* 3144:3437 */       return localRPPSFIInfo2;
/* 3145:     */     }
/* 3146:     */     catch (Exception localException)
/* 3147:     */     {
/* 3148:3441 */       if (localEJBContext != null)
/* 3149:     */       {
/* 3150:3443 */         if ((localException instanceof FFSException)) {
/* 3151:3445 */           throw ((FFSException)EJBContext.clone(localException));
/* 3152:     */         }
/* 3153:3447 */         localEJBContext.throwRemote(localException);
/* 3154:     */       }
/* 3155:3449 */       throw new EJBException(localException);
/* 3156:     */     }
/* 3157:     */     finally
/* 3158:     */     {
/* 3159:3453 */       this._comp.setCurrent(localJavaComponent);
/* 3160:     */     }
/* 3161:     */   }
/* 3162:     */   
/* 3163:     */   public RPPSFIInfo canRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
/* 3164:     */     throws RemoteException, FFSException
/* 3165:     */   {
/* 3166:3461 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3167:3462 */     EJBContext localEJBContext = null;
/* 3168:     */     try
/* 3169:     */     {
/* 3170:3465 */       localEJBContext = this._comp.getPooledInstance();
/* 3171:3466 */       if (localEJBContext == null) {
/* 3172:3468 */         localEJBContext = _create();
/* 3173:     */       }
/* 3174:3470 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3175:3471 */       if (localEJBContext.debug) {
/* 3176:3473 */         localEJBContext.logger.debug(localEJBContext.debugMsg("canRPPSFIInfo"));
/* 3177:     */       }
/* 3178:3475 */       RPPSFIInfo localRPPSFIInfo2 = localBPWServicesBean
/* 3179:3476 */         .canRPPSFIInfo(
/* 3180:3477 */         (RPPSFIInfo)EJBContext.clone(paramRPPSFIInfo));
/* 3181:     */       
/* 3182:3479 */       localRPPSFIInfo2 = (RPPSFIInfo)EJBContext.clone(localRPPSFIInfo2);
/* 3183:3480 */       localEJBContext.returnToPool();
/* 3184:3481 */       return localRPPSFIInfo2;
/* 3185:     */     }
/* 3186:     */     catch (Exception localException)
/* 3187:     */     {
/* 3188:3485 */       if (localEJBContext != null)
/* 3189:     */       {
/* 3190:3487 */         if ((localException instanceof FFSException)) {
/* 3191:3489 */           throw ((FFSException)EJBContext.clone(localException));
/* 3192:     */         }
/* 3193:3491 */         localEJBContext.throwRemote(localException);
/* 3194:     */       }
/* 3195:3493 */       throw new EJBException(localException);
/* 3196:     */     }
/* 3197:     */     finally
/* 3198:     */     {
/* 3199:3497 */       this._comp.setCurrent(localJavaComponent);
/* 3200:     */     }
/* 3201:     */   }
/* 3202:     */   
/* 3203:     */   public RPPSFIInfo activateRPPSFI(RPPSFIInfo paramRPPSFIInfo)
/* 3204:     */     throws RemoteException, FFSException
/* 3205:     */   {
/* 3206:3505 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3207:3506 */     EJBContext localEJBContext = null;
/* 3208:     */     try
/* 3209:     */     {
/* 3210:3509 */       localEJBContext = this._comp.getPooledInstance();
/* 3211:3510 */       if (localEJBContext == null) {
/* 3212:3512 */         localEJBContext = _create();
/* 3213:     */       }
/* 3214:3514 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3215:3515 */       if (localEJBContext.debug) {
/* 3216:3517 */         localEJBContext.logger.debug(localEJBContext.debugMsg("activateRPPSFI"));
/* 3217:     */       }
/* 3218:3519 */       RPPSFIInfo localRPPSFIInfo2 = localBPWServicesBean
/* 3219:3520 */         .activateRPPSFI(
/* 3220:3521 */         (RPPSFIInfo)EJBContext.clone(paramRPPSFIInfo));
/* 3221:     */       
/* 3222:3523 */       localRPPSFIInfo2 = (RPPSFIInfo)EJBContext.clone(localRPPSFIInfo2);
/* 3223:3524 */       localEJBContext.returnToPool();
/* 3224:3525 */       return localRPPSFIInfo2;
/* 3225:     */     }
/* 3226:     */     catch (Exception localException)
/* 3227:     */     {
/* 3228:3529 */       if (localEJBContext != null)
/* 3229:     */       {
/* 3230:3531 */         if ((localException instanceof FFSException)) {
/* 3231:3533 */           throw ((FFSException)EJBContext.clone(localException));
/* 3232:     */         }
/* 3233:3535 */         localEJBContext.throwRemote(localException);
/* 3234:     */       }
/* 3235:3537 */       throw new EJBException(localException);
/* 3236:     */     }
/* 3237:     */     finally
/* 3238:     */     {
/* 3239:3541 */       this._comp.setCurrent(localJavaComponent);
/* 3240:     */     }
/* 3241:     */   }
/* 3242:     */   
/* 3243:     */   public RPPSFIInfo modRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
/* 3244:     */     throws RemoteException, FFSException
/* 3245:     */   {
/* 3246:3549 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3247:3550 */     EJBContext localEJBContext = null;
/* 3248:     */     try
/* 3249:     */     {
/* 3250:3553 */       localEJBContext = this._comp.getPooledInstance();
/* 3251:3554 */       if (localEJBContext == null) {
/* 3252:3556 */         localEJBContext = _create();
/* 3253:     */       }
/* 3254:3558 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3255:3559 */       if (localEJBContext.debug) {
/* 3256:3561 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modRPPSFIInfo"));
/* 3257:     */       }
/* 3258:3563 */       RPPSFIInfo localRPPSFIInfo2 = localBPWServicesBean
/* 3259:3564 */         .modRPPSFIInfo(
/* 3260:3565 */         (RPPSFIInfo)EJBContext.clone(paramRPPSFIInfo));
/* 3261:     */       
/* 3262:3567 */       localRPPSFIInfo2 = (RPPSFIInfo)EJBContext.clone(localRPPSFIInfo2);
/* 3263:3568 */       localEJBContext.returnToPool();
/* 3264:3569 */       return localRPPSFIInfo2;
/* 3265:     */     }
/* 3266:     */     catch (Exception localException)
/* 3267:     */     {
/* 3268:3573 */       if (localEJBContext != null)
/* 3269:     */       {
/* 3270:3575 */         if ((localException instanceof FFSException)) {
/* 3271:3577 */           throw ((FFSException)EJBContext.clone(localException));
/* 3272:     */         }
/* 3273:3579 */         localEJBContext.throwRemote(localException);
/* 3274:     */       }
/* 3275:3581 */       throw new EJBException(localException);
/* 3276:     */     }
/* 3277:     */     finally
/* 3278:     */     {
/* 3279:3585 */       this._comp.setCurrent(localJavaComponent);
/* 3280:     */     }
/* 3281:     */   }
/* 3282:     */   
/* 3283:     */   public int getSmartDate(String paramString, int paramInt)
/* 3284:     */     throws RemoteException
/* 3285:     */   {
/* 3286:3594 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3287:3595 */     EJBContext localEJBContext = null;
/* 3288:     */     try
/* 3289:     */     {
/* 3290:3598 */       localEJBContext = this._comp.getPooledInstance();
/* 3291:3599 */       if (localEJBContext == null) {
/* 3292:3601 */         localEJBContext = _create();
/* 3293:     */       }
/* 3294:3603 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3295:3604 */       if (localEJBContext.debug) {
/* 3296:3606 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getSmartDate"));
/* 3297:     */       }
/* 3298:3608 */       int j = localBPWServicesBean
/* 3299:3609 */         .getSmartDate(
/* 3300:3610 */         paramString, 
/* 3301:3611 */         paramInt);
/* 3302:     */       
/* 3303:3613 */       localEJBContext.returnToPool();
/* 3304:3614 */       return j;
/* 3305:     */     }
/* 3306:     */     catch (Exception localException)
/* 3307:     */     {
/* 3308:3618 */       if (localEJBContext != null) {
/* 3309:3620 */         localEJBContext.throwRemote(localException);
/* 3310:     */       }
/* 3311:3622 */       throw new EJBException(localException);
/* 3312:     */     }
/* 3313:     */     finally
/* 3314:     */     {
/* 3315:3626 */       this._comp.setCurrent(localJavaComponent);
/* 3316:     */     }
/* 3317:     */   }
/* 3318:     */   
/* 3319:     */   public void processApprovalResult(String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
/* 3320:     */     throws RemoteException, FFSException
/* 3321:     */   {
/* 3322:3637 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3323:3638 */     EJBContext localEJBContext = null;
/* 3324:     */     try
/* 3325:     */     {
/* 3326:3641 */       localEJBContext = this._comp.getPooledInstance();
/* 3327:3642 */       if (localEJBContext == null) {
/* 3328:3644 */         localEJBContext = _create();
/* 3329:     */       }
/* 3330:3646 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3331:3647 */       if (localEJBContext.debug) {
/* 3332:3649 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processApprovalResult"));
/* 3333:     */       }
/* 3334:3652 */       localBPWServicesBean.processApprovalResult(
/* 3335:3653 */         paramString1, 
/* 3336:3654 */         paramString2, 
/* 3337:3655 */         paramString3, 
/* 3338:3656 */         (HashMap)EJBContext.clone(paramHashMap));
/* 3339:     */       
/* 3340:3658 */       localEJBContext.returnToPool();
/* 3341:     */     }
/* 3342:     */     catch (Exception localException)
/* 3343:     */     {
/* 3344:3662 */       if (localEJBContext != null)
/* 3345:     */       {
/* 3346:3664 */         if ((localException instanceof FFSException)) {
/* 3347:3666 */           throw ((FFSException)EJBContext.clone(localException));
/* 3348:     */         }
/* 3349:3668 */         localEJBContext.throwRemote(localException);
/* 3350:     */       }
/* 3351:3670 */       throw new EJBException(localException);
/* 3352:     */     }
/* 3353:     */     finally
/* 3354:     */     {
/* 3355:3674 */       this._comp.setCurrent(localJavaComponent);
/* 3356:     */     }
/* 3357:     */   }
/* 3358:     */   
/* 3359:     */   public CCCompanyInfo addCCCompany(CCCompanyInfo paramCCCompanyInfo)
/* 3360:     */     throws RemoteException, FFSException
/* 3361:     */   {
/* 3362:3682 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3363:3683 */     EJBContext localEJBContext = null;
/* 3364:     */     try
/* 3365:     */     {
/* 3366:3686 */       localEJBContext = this._comp.getPooledInstance();
/* 3367:3687 */       if (localEJBContext == null) {
/* 3368:3689 */         localEJBContext = _create();
/* 3369:     */       }
/* 3370:3691 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3371:3692 */       if (localEJBContext.debug) {
/* 3372:3694 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCCCompany"));
/* 3373:     */       }
/* 3374:3696 */       CCCompanyInfo localCCCompanyInfo2 = localBPWServicesBean
/* 3375:3697 */         .addCCCompany(
/* 3376:3698 */         (CCCompanyInfo)EJBContext.clone(paramCCCompanyInfo));
/* 3377:     */       
/* 3378:3700 */       localCCCompanyInfo2 = (CCCompanyInfo)EJBContext.clone(localCCCompanyInfo2);
/* 3379:3701 */       localEJBContext.returnToPool();
/* 3380:3702 */       return localCCCompanyInfo2;
/* 3381:     */     }
/* 3382:     */     catch (Exception localException)
/* 3383:     */     {
/* 3384:3706 */       if (localEJBContext != null)
/* 3385:     */       {
/* 3386:3708 */         if ((localException instanceof FFSException)) {
/* 3387:3710 */           throw ((FFSException)EJBContext.clone(localException));
/* 3388:     */         }
/* 3389:3712 */         localEJBContext.throwRemote(localException);
/* 3390:     */       }
/* 3391:3714 */       throw new EJBException(localException);
/* 3392:     */     }
/* 3393:     */     finally
/* 3394:     */     {
/* 3395:3718 */       this._comp.setCurrent(localJavaComponent);
/* 3396:     */     }
/* 3397:     */   }
/* 3398:     */   
/* 3399:     */   public CCCompanyInfo cancelCCCompany(CCCompanyInfo paramCCCompanyInfo)
/* 3400:     */     throws RemoteException, FFSException
/* 3401:     */   {
/* 3402:3726 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3403:3727 */     EJBContext localEJBContext = null;
/* 3404:     */     try
/* 3405:     */     {
/* 3406:3730 */       localEJBContext = this._comp.getPooledInstance();
/* 3407:3731 */       if (localEJBContext == null) {
/* 3408:3733 */         localEJBContext = _create();
/* 3409:     */       }
/* 3410:3735 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3411:3736 */       if (localEJBContext.debug) {
/* 3412:3738 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancelCCCompany"));
/* 3413:     */       }
/* 3414:3740 */       CCCompanyInfo localCCCompanyInfo2 = localBPWServicesBean
/* 3415:3741 */         .cancelCCCompany(
/* 3416:3742 */         (CCCompanyInfo)EJBContext.clone(paramCCCompanyInfo));
/* 3417:     */       
/* 3418:3744 */       localCCCompanyInfo2 = (CCCompanyInfo)EJBContext.clone(localCCCompanyInfo2);
/* 3419:3745 */       localEJBContext.returnToPool();
/* 3420:3746 */       return localCCCompanyInfo2;
/* 3421:     */     }
/* 3422:     */     catch (Exception localException)
/* 3423:     */     {
/* 3424:3750 */       if (localEJBContext != null)
/* 3425:     */       {
/* 3426:3752 */         if ((localException instanceof FFSException)) {
/* 3427:3754 */           throw ((FFSException)EJBContext.clone(localException));
/* 3428:     */         }
/* 3429:3756 */         localEJBContext.throwRemote(localException);
/* 3430:     */       }
/* 3431:3758 */       throw new EJBException(localException);
/* 3432:     */     }
/* 3433:     */     finally
/* 3434:     */     {
/* 3435:3762 */       this._comp.setCurrent(localJavaComponent);
/* 3436:     */     }
/* 3437:     */   }
/* 3438:     */   
/* 3439:     */   public CCCompanyInfo modCCCompany(CCCompanyInfo paramCCCompanyInfo)
/* 3440:     */     throws RemoteException, FFSException
/* 3441:     */   {
/* 3442:3770 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3443:3771 */     EJBContext localEJBContext = null;
/* 3444:     */     try
/* 3445:     */     {
/* 3446:3774 */       localEJBContext = this._comp.getPooledInstance();
/* 3447:3775 */       if (localEJBContext == null) {
/* 3448:3777 */         localEJBContext = _create();
/* 3449:     */       }
/* 3450:3779 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3451:3780 */       if (localEJBContext.debug) {
/* 3452:3782 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modCCCompany"));
/* 3453:     */       }
/* 3454:3784 */       CCCompanyInfo localCCCompanyInfo2 = localBPWServicesBean
/* 3455:3785 */         .modCCCompany(
/* 3456:3786 */         (CCCompanyInfo)EJBContext.clone(paramCCCompanyInfo));
/* 3457:     */       
/* 3458:3788 */       localCCCompanyInfo2 = (CCCompanyInfo)EJBContext.clone(localCCCompanyInfo2);
/* 3459:3789 */       localEJBContext.returnToPool();
/* 3460:3790 */       return localCCCompanyInfo2;
/* 3461:     */     }
/* 3462:     */     catch (Exception localException)
/* 3463:     */     {
/* 3464:3794 */       if (localEJBContext != null)
/* 3465:     */       {
/* 3466:3796 */         if ((localException instanceof FFSException)) {
/* 3467:3798 */           throw ((FFSException)EJBContext.clone(localException));
/* 3468:     */         }
/* 3469:3800 */         localEJBContext.throwRemote(localException);
/* 3470:     */       }
/* 3471:3802 */       throw new EJBException(localException);
/* 3472:     */     }
/* 3473:     */     finally
/* 3474:     */     {
/* 3475:3806 */       this._comp.setCurrent(localJavaComponent);
/* 3476:     */     }
/* 3477:     */   }
/* 3478:     */   
/* 3479:     */   public CCCompanyInfo getCCCompany(CCCompanyInfo paramCCCompanyInfo)
/* 3480:     */     throws RemoteException, FFSException
/* 3481:     */   {
/* 3482:3814 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3483:3815 */     EJBContext localEJBContext = null;
/* 3484:     */     try
/* 3485:     */     {
/* 3486:3818 */       localEJBContext = this._comp.getPooledInstance();
/* 3487:3819 */       if (localEJBContext == null) {
/* 3488:3821 */         localEJBContext = _create();
/* 3489:     */       }
/* 3490:3823 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3491:3824 */       if (localEJBContext.debug) {
/* 3492:3826 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCCompany"));
/* 3493:     */       }
/* 3494:3828 */       CCCompanyInfo localCCCompanyInfo2 = localBPWServicesBean
/* 3495:3829 */         .getCCCompany(
/* 3496:3830 */         (CCCompanyInfo)EJBContext.clone(paramCCCompanyInfo));
/* 3497:     */       
/* 3498:3832 */       localCCCompanyInfo2 = (CCCompanyInfo)EJBContext.clone(localCCCompanyInfo2);
/* 3499:3833 */       localEJBContext.returnToPool();
/* 3500:3834 */       return localCCCompanyInfo2;
/* 3501:     */     }
/* 3502:     */     catch (Exception localException)
/* 3503:     */     {
/* 3504:3838 */       if (localEJBContext != null)
/* 3505:     */       {
/* 3506:3840 */         if ((localException instanceof FFSException)) {
/* 3507:3842 */           throw ((FFSException)EJBContext.clone(localException));
/* 3508:     */         }
/* 3509:3844 */         localEJBContext.throwRemote(localException);
/* 3510:     */       }
/* 3511:3846 */       throw new EJBException(localException);
/* 3512:     */     }
/* 3513:     */     finally
/* 3514:     */     {
/* 3515:3850 */       this._comp.setCurrent(localJavaComponent);
/* 3516:     */     }
/* 3517:     */   }
/* 3518:     */   
/* 3519:     */   public CCCompanyInfoList getCCCompanyList(CCCompanyInfoList paramCCCompanyInfoList)
/* 3520:     */     throws RemoteException, FFSException
/* 3521:     */   {
/* 3522:3858 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3523:3859 */     EJBContext localEJBContext = null;
/* 3524:     */     try
/* 3525:     */     {
/* 3526:3862 */       localEJBContext = this._comp.getPooledInstance();
/* 3527:3863 */       if (localEJBContext == null) {
/* 3528:3865 */         localEJBContext = _create();
/* 3529:     */       }
/* 3530:3867 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3531:3868 */       if (localEJBContext.debug) {
/* 3532:3870 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCCompanyList"));
/* 3533:     */       }
/* 3534:3872 */       CCCompanyInfoList localCCCompanyInfoList2 = localBPWServicesBean
/* 3535:3873 */         .getCCCompanyList(
/* 3536:3874 */         (CCCompanyInfoList)EJBContext.clone(paramCCCompanyInfoList));
/* 3537:     */       
/* 3538:3876 */       localCCCompanyInfoList2 = (CCCompanyInfoList)EJBContext.clone(localCCCompanyInfoList2);
/* 3539:3877 */       localEJBContext.returnToPool();
/* 3540:3878 */       return localCCCompanyInfoList2;
/* 3541:     */     }
/* 3542:     */     catch (Exception localException)
/* 3543:     */     {
/* 3544:3882 */       if (localEJBContext != null)
/* 3545:     */       {
/* 3546:3884 */         if ((localException instanceof FFSException)) {
/* 3547:3886 */           throw ((FFSException)EJBContext.clone(localException));
/* 3548:     */         }
/* 3549:3888 */         localEJBContext.throwRemote(localException);
/* 3550:     */       }
/* 3551:3890 */       throw new EJBException(localException);
/* 3552:     */     }
/* 3553:     */     finally
/* 3554:     */     {
/* 3555:3894 */       this._comp.setCurrent(localJavaComponent);
/* 3556:     */     }
/* 3557:     */   }
/* 3558:     */   
/* 3559:     */   public CutOffInfo getNextCashConCutOff(String paramString1, String paramString2)
/* 3560:     */     throws RemoteException, FFSException
/* 3561:     */   {
/* 3562:3903 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3563:3904 */     EJBContext localEJBContext = null;
/* 3564:     */     try
/* 3565:     */     {
/* 3566:3907 */       localEJBContext = this._comp.getPooledInstance();
/* 3567:3908 */       if (localEJBContext == null) {
/* 3568:3910 */         localEJBContext = _create();
/* 3569:     */       }
/* 3570:3912 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3571:3913 */       if (localEJBContext.debug) {
/* 3572:3915 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getNextCashConCutOff"));
/* 3573:     */       }
/* 3574:3917 */       CutOffInfo localCutOffInfo2 = localBPWServicesBean
/* 3575:3918 */         .getNextCashConCutOff(
/* 3576:3919 */         paramString1, 
/* 3577:3920 */         paramString2);
/* 3578:     */       
/* 3579:3922 */       localCutOffInfo2 = (CutOffInfo)EJBContext.clone(localCutOffInfo2);
/* 3580:3923 */       localEJBContext.returnToPool();
/* 3581:3924 */       return localCutOffInfo2;
/* 3582:     */     }
/* 3583:     */     catch (Exception localException)
/* 3584:     */     {
/* 3585:3928 */       if (localEJBContext != null)
/* 3586:     */       {
/* 3587:3930 */         if ((localException instanceof FFSException)) {
/* 3588:3932 */           throw ((FFSException)EJBContext.clone(localException));
/* 3589:     */         }
/* 3590:3934 */         localEJBContext.throwRemote(localException);
/* 3591:     */       }
/* 3592:3936 */       throw new EJBException(localException);
/* 3593:     */     }
/* 3594:     */     finally
/* 3595:     */     {
/* 3596:3940 */       this._comp.setCurrent(localJavaComponent);
/* 3597:     */     }
/* 3598:     */   }
/* 3599:     */   
/* 3600:     */   public CCCompanyAcctInfo addCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
/* 3601:     */     throws RemoteException, FFSException
/* 3602:     */   {
/* 3603:3948 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3604:3949 */     EJBContext localEJBContext = null;
/* 3605:     */     try
/* 3606:     */     {
/* 3607:3952 */       localEJBContext = this._comp.getPooledInstance();
/* 3608:3953 */       if (localEJBContext == null) {
/* 3609:3955 */         localEJBContext = _create();
/* 3610:     */       }
/* 3611:3957 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3612:3958 */       if (localEJBContext.debug) {
/* 3613:3960 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCCCompanyAcct"));
/* 3614:     */       }
/* 3615:3962 */       CCCompanyAcctInfo localCCCompanyAcctInfo2 = localBPWServicesBean
/* 3616:3963 */         .addCCCompanyAcct(
/* 3617:3964 */         (CCCompanyAcctInfo)EJBContext.clone(paramCCCompanyAcctInfo));
/* 3618:     */       
/* 3619:3966 */       localCCCompanyAcctInfo2 = (CCCompanyAcctInfo)EJBContext.clone(localCCCompanyAcctInfo2);
/* 3620:3967 */       localEJBContext.returnToPool();
/* 3621:3968 */       return localCCCompanyAcctInfo2;
/* 3622:     */     }
/* 3623:     */     catch (Exception localException)
/* 3624:     */     {
/* 3625:3972 */       if (localEJBContext != null)
/* 3626:     */       {
/* 3627:3974 */         if ((localException instanceof FFSException)) {
/* 3628:3976 */           throw ((FFSException)EJBContext.clone(localException));
/* 3629:     */         }
/* 3630:3978 */         localEJBContext.throwRemote(localException);
/* 3631:     */       }
/* 3632:3980 */       throw new EJBException(localException);
/* 3633:     */     }
/* 3634:     */     finally
/* 3635:     */     {
/* 3636:3984 */       this._comp.setCurrent(localJavaComponent);
/* 3637:     */     }
/* 3638:     */   }
/* 3639:     */   
/* 3640:     */   public CCCompanyAcctInfo cancelCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
/* 3641:     */     throws RemoteException, FFSException
/* 3642:     */   {
/* 3643:3992 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3644:3993 */     EJBContext localEJBContext = null;
/* 3645:     */     try
/* 3646:     */     {
/* 3647:3996 */       localEJBContext = this._comp.getPooledInstance();
/* 3648:3997 */       if (localEJBContext == null) {
/* 3649:3999 */         localEJBContext = _create();
/* 3650:     */       }
/* 3651:4001 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3652:4002 */       if (localEJBContext.debug) {
/* 3653:4004 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancelCCCompanyAcct"));
/* 3654:     */       }
/* 3655:4006 */       CCCompanyAcctInfo localCCCompanyAcctInfo2 = localBPWServicesBean
/* 3656:4007 */         .cancelCCCompanyAcct(
/* 3657:4008 */         (CCCompanyAcctInfo)EJBContext.clone(paramCCCompanyAcctInfo));
/* 3658:     */       
/* 3659:4010 */       localCCCompanyAcctInfo2 = (CCCompanyAcctInfo)EJBContext.clone(localCCCompanyAcctInfo2);
/* 3660:4011 */       localEJBContext.returnToPool();
/* 3661:4012 */       return localCCCompanyAcctInfo2;
/* 3662:     */     }
/* 3663:     */     catch (Exception localException)
/* 3664:     */     {
/* 3665:4016 */       if (localEJBContext != null)
/* 3666:     */       {
/* 3667:4018 */         if ((localException instanceof FFSException)) {
/* 3668:4020 */           throw ((FFSException)EJBContext.clone(localException));
/* 3669:     */         }
/* 3670:4022 */         localEJBContext.throwRemote(localException);
/* 3671:     */       }
/* 3672:4024 */       throw new EJBException(localException);
/* 3673:     */     }
/* 3674:     */     finally
/* 3675:     */     {
/* 3676:4028 */       this._comp.setCurrent(localJavaComponent);
/* 3677:     */     }
/* 3678:     */   }
/* 3679:     */   
/* 3680:     */   public CCCompanyAcctInfo modCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
/* 3681:     */     throws RemoteException, FFSException
/* 3682:     */   {
/* 3683:4036 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3684:4037 */     EJBContext localEJBContext = null;
/* 3685:     */     try
/* 3686:     */     {
/* 3687:4040 */       localEJBContext = this._comp.getPooledInstance();
/* 3688:4041 */       if (localEJBContext == null) {
/* 3689:4043 */         localEJBContext = _create();
/* 3690:     */       }
/* 3691:4045 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3692:4046 */       if (localEJBContext.debug) {
/* 3693:4048 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modCCCompanyAcct"));
/* 3694:     */       }
/* 3695:4050 */       CCCompanyAcctInfo localCCCompanyAcctInfo2 = localBPWServicesBean
/* 3696:4051 */         .modCCCompanyAcct(
/* 3697:4052 */         (CCCompanyAcctInfo)EJBContext.clone(paramCCCompanyAcctInfo));
/* 3698:     */       
/* 3699:4054 */       localCCCompanyAcctInfo2 = (CCCompanyAcctInfo)EJBContext.clone(localCCCompanyAcctInfo2);
/* 3700:4055 */       localEJBContext.returnToPool();
/* 3701:4056 */       return localCCCompanyAcctInfo2;
/* 3702:     */     }
/* 3703:     */     catch (Exception localException)
/* 3704:     */     {
/* 3705:4060 */       if (localEJBContext != null)
/* 3706:     */       {
/* 3707:4062 */         if ((localException instanceof FFSException)) {
/* 3708:4064 */           throw ((FFSException)EJBContext.clone(localException));
/* 3709:     */         }
/* 3710:4066 */         localEJBContext.throwRemote(localException);
/* 3711:     */       }
/* 3712:4068 */       throw new EJBException(localException);
/* 3713:     */     }
/* 3714:     */     finally
/* 3715:     */     {
/* 3716:4072 */       this._comp.setCurrent(localJavaComponent);
/* 3717:     */     }
/* 3718:     */   }
/* 3719:     */   
/* 3720:     */   public CCCompanyAcctInfo getCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
/* 3721:     */     throws RemoteException, FFSException
/* 3722:     */   {
/* 3723:4080 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3724:4081 */     EJBContext localEJBContext = null;
/* 3725:     */     try
/* 3726:     */     {
/* 3727:4084 */       localEJBContext = this._comp.getPooledInstance();
/* 3728:4085 */       if (localEJBContext == null) {
/* 3729:4087 */         localEJBContext = _create();
/* 3730:     */       }
/* 3731:4089 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3732:4090 */       if (localEJBContext.debug) {
/* 3733:4092 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCCompanyAcct"));
/* 3734:     */       }
/* 3735:4094 */       CCCompanyAcctInfo localCCCompanyAcctInfo2 = localBPWServicesBean
/* 3736:4095 */         .getCCCompanyAcct(
/* 3737:4096 */         (CCCompanyAcctInfo)EJBContext.clone(paramCCCompanyAcctInfo));
/* 3738:     */       
/* 3739:4098 */       localCCCompanyAcctInfo2 = (CCCompanyAcctInfo)EJBContext.clone(localCCCompanyAcctInfo2);
/* 3740:4099 */       localEJBContext.returnToPool();
/* 3741:4100 */       return localCCCompanyAcctInfo2;
/* 3742:     */     }
/* 3743:     */     catch (Exception localException)
/* 3744:     */     {
/* 3745:4104 */       if (localEJBContext != null)
/* 3746:     */       {
/* 3747:4106 */         if ((localException instanceof FFSException)) {
/* 3748:4108 */           throw ((FFSException)EJBContext.clone(localException));
/* 3749:     */         }
/* 3750:4110 */         localEJBContext.throwRemote(localException);
/* 3751:     */       }
/* 3752:4112 */       throw new EJBException(localException);
/* 3753:     */     }
/* 3754:     */     finally
/* 3755:     */     {
/* 3756:4116 */       this._comp.setCurrent(localJavaComponent);
/* 3757:     */     }
/* 3758:     */   }
/* 3759:     */   
/* 3760:     */   public CCCompanyAcctInfoList getCCCompanyAcctList(CCCompanyAcctInfoList paramCCCompanyAcctInfoList)
/* 3761:     */     throws RemoteException, FFSException
/* 3762:     */   {
/* 3763:4124 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3764:4125 */     EJBContext localEJBContext = null;
/* 3765:     */     try
/* 3766:     */     {
/* 3767:4128 */       localEJBContext = this._comp.getPooledInstance();
/* 3768:4129 */       if (localEJBContext == null) {
/* 3769:4131 */         localEJBContext = _create();
/* 3770:     */       }
/* 3771:4133 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3772:4134 */       if (localEJBContext.debug) {
/* 3773:4136 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCCompanyAcctList"));
/* 3774:     */       }
/* 3775:4138 */       CCCompanyAcctInfoList localCCCompanyAcctInfoList2 = localBPWServicesBean
/* 3776:4139 */         .getCCCompanyAcctList(
/* 3777:4140 */         (CCCompanyAcctInfoList)EJBContext.clone(paramCCCompanyAcctInfoList));
/* 3778:     */       
/* 3779:4142 */       localCCCompanyAcctInfoList2 = (CCCompanyAcctInfoList)EJBContext.clone(localCCCompanyAcctInfoList2);
/* 3780:4143 */       localEJBContext.returnToPool();
/* 3781:4144 */       return localCCCompanyAcctInfoList2;
/* 3782:     */     }
/* 3783:     */     catch (Exception localException)
/* 3784:     */     {
/* 3785:4148 */       if (localEJBContext != null)
/* 3786:     */       {
/* 3787:4150 */         if ((localException instanceof FFSException)) {
/* 3788:4152 */           throw ((FFSException)EJBContext.clone(localException));
/* 3789:     */         }
/* 3790:4154 */         localEJBContext.throwRemote(localException);
/* 3791:     */       }
/* 3792:4156 */       throw new EJBException(localException);
/* 3793:     */     }
/* 3794:     */     finally
/* 3795:     */     {
/* 3796:4160 */       this._comp.setCurrent(localJavaComponent);
/* 3797:     */     }
/* 3798:     */   }
/* 3799:     */   
/* 3800:     */   public CCCompanyCutOffInfo addCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
/* 3801:     */     throws RemoteException, FFSException
/* 3802:     */   {
/* 3803:4168 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3804:4169 */     EJBContext localEJBContext = null;
/* 3805:     */     try
/* 3806:     */     {
/* 3807:4172 */       localEJBContext = this._comp.getPooledInstance();
/* 3808:4173 */       if (localEJBContext == null) {
/* 3809:4175 */         localEJBContext = _create();
/* 3810:     */       }
/* 3811:4177 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3812:4178 */       if (localEJBContext.debug) {
/* 3813:4180 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCCCompanyCutOff"));
/* 3814:     */       }
/* 3815:4182 */       CCCompanyCutOffInfo localCCCompanyCutOffInfo2 = localBPWServicesBean
/* 3816:4183 */         .addCCCompanyCutOff(
/* 3817:4184 */         (CCCompanyCutOffInfo)EJBContext.clone(paramCCCompanyCutOffInfo));
/* 3818:     */       
/* 3819:4186 */       localCCCompanyCutOffInfo2 = (CCCompanyCutOffInfo)EJBContext.clone(localCCCompanyCutOffInfo2);
/* 3820:4187 */       localEJBContext.returnToPool();
/* 3821:4188 */       return localCCCompanyCutOffInfo2;
/* 3822:     */     }
/* 3823:     */     catch (Exception localException)
/* 3824:     */     {
/* 3825:4192 */       if (localEJBContext != null)
/* 3826:     */       {
/* 3827:4194 */         if ((localException instanceof FFSException)) {
/* 3828:4196 */           throw ((FFSException)EJBContext.clone(localException));
/* 3829:     */         }
/* 3830:4198 */         localEJBContext.throwRemote(localException);
/* 3831:     */       }
/* 3832:4200 */       throw new EJBException(localException);
/* 3833:     */     }
/* 3834:     */     finally
/* 3835:     */     {
/* 3836:4204 */       this._comp.setCurrent(localJavaComponent);
/* 3837:     */     }
/* 3838:     */   }
/* 3839:     */   
/* 3840:     */   public CCCompanyCutOffInfo cancelCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
/* 3841:     */     throws RemoteException, FFSException
/* 3842:     */   {
/* 3843:4212 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3844:4213 */     EJBContext localEJBContext = null;
/* 3845:     */     try
/* 3846:     */     {
/* 3847:4216 */       localEJBContext = this._comp.getPooledInstance();
/* 3848:4217 */       if (localEJBContext == null) {
/* 3849:4219 */         localEJBContext = _create();
/* 3850:     */       }
/* 3851:4221 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3852:4222 */       if (localEJBContext.debug) {
/* 3853:4224 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancelCCCompanyCutOff"));
/* 3854:     */       }
/* 3855:4226 */       CCCompanyCutOffInfo localCCCompanyCutOffInfo2 = localBPWServicesBean
/* 3856:4227 */         .cancelCCCompanyCutOff(
/* 3857:4228 */         (CCCompanyCutOffInfo)EJBContext.clone(paramCCCompanyCutOffInfo));
/* 3858:     */       
/* 3859:4230 */       localCCCompanyCutOffInfo2 = (CCCompanyCutOffInfo)EJBContext.clone(localCCCompanyCutOffInfo2);
/* 3860:4231 */       localEJBContext.returnToPool();
/* 3861:4232 */       return localCCCompanyCutOffInfo2;
/* 3862:     */     }
/* 3863:     */     catch (Exception localException)
/* 3864:     */     {
/* 3865:4236 */       if (localEJBContext != null)
/* 3866:     */       {
/* 3867:4238 */         if ((localException instanceof FFSException)) {
/* 3868:4240 */           throw ((FFSException)EJBContext.clone(localException));
/* 3869:     */         }
/* 3870:4242 */         localEJBContext.throwRemote(localException);
/* 3871:     */       }
/* 3872:4244 */       throw new EJBException(localException);
/* 3873:     */     }
/* 3874:     */     finally
/* 3875:     */     {
/* 3876:4248 */       this._comp.setCurrent(localJavaComponent);
/* 3877:     */     }
/* 3878:     */   }
/* 3879:     */   
/* 3880:     */   public CCCompanyCutOffInfo getCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
/* 3881:     */     throws RemoteException, FFSException
/* 3882:     */   {
/* 3883:4256 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3884:4257 */     EJBContext localEJBContext = null;
/* 3885:     */     try
/* 3886:     */     {
/* 3887:4260 */       localEJBContext = this._comp.getPooledInstance();
/* 3888:4261 */       if (localEJBContext == null) {
/* 3889:4263 */         localEJBContext = _create();
/* 3890:     */       }
/* 3891:4265 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3892:4266 */       if (localEJBContext.debug) {
/* 3893:4268 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCCompanyCutOff"));
/* 3894:     */       }
/* 3895:4270 */       CCCompanyCutOffInfo localCCCompanyCutOffInfo2 = localBPWServicesBean
/* 3896:4271 */         .getCCCompanyCutOff(
/* 3897:4272 */         (CCCompanyCutOffInfo)EJBContext.clone(paramCCCompanyCutOffInfo));
/* 3898:     */       
/* 3899:4274 */       localCCCompanyCutOffInfo2 = (CCCompanyCutOffInfo)EJBContext.clone(localCCCompanyCutOffInfo2);
/* 3900:4275 */       localEJBContext.returnToPool();
/* 3901:4276 */       return localCCCompanyCutOffInfo2;
/* 3902:     */     }
/* 3903:     */     catch (Exception localException)
/* 3904:     */     {
/* 3905:4280 */       if (localEJBContext != null)
/* 3906:     */       {
/* 3907:4282 */         if ((localException instanceof FFSException)) {
/* 3908:4284 */           throw ((FFSException)EJBContext.clone(localException));
/* 3909:     */         }
/* 3910:4286 */         localEJBContext.throwRemote(localException);
/* 3911:     */       }
/* 3912:4288 */       throw new EJBException(localException);
/* 3913:     */     }
/* 3914:     */     finally
/* 3915:     */     {
/* 3916:4292 */       this._comp.setCurrent(localJavaComponent);
/* 3917:     */     }
/* 3918:     */   }
/* 3919:     */   
/* 3920:     */   public CCCompanyCutOffInfoList getCCCompanyCutOffList(CCCompanyCutOffInfoList paramCCCompanyCutOffInfoList)
/* 3921:     */     throws RemoteException, FFSException
/* 3922:     */   {
/* 3923:4300 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3924:4301 */     EJBContext localEJBContext = null;
/* 3925:     */     try
/* 3926:     */     {
/* 3927:4304 */       localEJBContext = this._comp.getPooledInstance();
/* 3928:4305 */       if (localEJBContext == null) {
/* 3929:4307 */         localEJBContext = _create();
/* 3930:     */       }
/* 3931:4309 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3932:4310 */       if (localEJBContext.debug) {
/* 3933:4312 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCCompanyCutOffList"));
/* 3934:     */       }
/* 3935:4314 */       CCCompanyCutOffInfoList localCCCompanyCutOffInfoList2 = localBPWServicesBean
/* 3936:4315 */         .getCCCompanyCutOffList(
/* 3937:4316 */         (CCCompanyCutOffInfoList)EJBContext.clone(paramCCCompanyCutOffInfoList));
/* 3938:     */       
/* 3939:4318 */       localCCCompanyCutOffInfoList2 = (CCCompanyCutOffInfoList)EJBContext.clone(localCCCompanyCutOffInfoList2);
/* 3940:4319 */       localEJBContext.returnToPool();
/* 3941:4320 */       return localCCCompanyCutOffInfoList2;
/* 3942:     */     }
/* 3943:     */     catch (Exception localException)
/* 3944:     */     {
/* 3945:4324 */       if (localEJBContext != null)
/* 3946:     */       {
/* 3947:4326 */         if ((localException instanceof FFSException)) {
/* 3948:4328 */           throw ((FFSException)EJBContext.clone(localException));
/* 3949:     */         }
/* 3950:4330 */         localEJBContext.throwRemote(localException);
/* 3951:     */       }
/* 3952:4332 */       throw new EJBException(localException);
/* 3953:     */     }
/* 3954:     */     finally
/* 3955:     */     {
/* 3956:4336 */       this._comp.setCurrent(localJavaComponent);
/* 3957:     */     }
/* 3958:     */   }
/* 3959:     */   
/* 3960:     */   public CCLocationInfo addCCLocation(CCLocationInfo paramCCLocationInfo)
/* 3961:     */     throws RemoteException, FFSException
/* 3962:     */   {
/* 3963:4344 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3964:4345 */     EJBContext localEJBContext = null;
/* 3965:     */     try
/* 3966:     */     {
/* 3967:4348 */       localEJBContext = this._comp.getPooledInstance();
/* 3968:4349 */       if (localEJBContext == null) {
/* 3969:4351 */         localEJBContext = _create();
/* 3970:     */       }
/* 3971:4353 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 3972:4354 */       if (localEJBContext.debug) {
/* 3973:4356 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCCLocation"));
/* 3974:     */       }
/* 3975:4358 */       CCLocationInfo localCCLocationInfo2 = localBPWServicesBean
/* 3976:4359 */         .addCCLocation(
/* 3977:4360 */         (CCLocationInfo)EJBContext.clone(paramCCLocationInfo));
/* 3978:     */       
/* 3979:4362 */       localCCLocationInfo2 = (CCLocationInfo)EJBContext.clone(localCCLocationInfo2);
/* 3980:4363 */       localEJBContext.returnToPool();
/* 3981:4364 */       return localCCLocationInfo2;
/* 3982:     */     }
/* 3983:     */     catch (Exception localException)
/* 3984:     */     {
/* 3985:4368 */       if (localEJBContext != null)
/* 3986:     */       {
/* 3987:4370 */         if ((localException instanceof FFSException)) {
/* 3988:4372 */           throw ((FFSException)EJBContext.clone(localException));
/* 3989:     */         }
/* 3990:4374 */         localEJBContext.throwRemote(localException);
/* 3991:     */       }
/* 3992:4376 */       throw new EJBException(localException);
/* 3993:     */     }
/* 3994:     */     finally
/* 3995:     */     {
/* 3996:4380 */       this._comp.setCurrent(localJavaComponent);
/* 3997:     */     }
/* 3998:     */   }
/* 3999:     */   
/* 4000:     */   public CCLocationInfo cancelCCLocation(CCLocationInfo paramCCLocationInfo)
/* 4001:     */     throws RemoteException, FFSException
/* 4002:     */   {
/* 4003:4388 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4004:4389 */     EJBContext localEJBContext = null;
/* 4005:     */     try
/* 4006:     */     {
/* 4007:4392 */       localEJBContext = this._comp.getPooledInstance();
/* 4008:4393 */       if (localEJBContext == null) {
/* 4009:4395 */         localEJBContext = _create();
/* 4010:     */       }
/* 4011:4397 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4012:4398 */       if (localEJBContext.debug) {
/* 4013:4400 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancelCCLocation"));
/* 4014:     */       }
/* 4015:4402 */       CCLocationInfo localCCLocationInfo2 = localBPWServicesBean
/* 4016:4403 */         .cancelCCLocation(
/* 4017:4404 */         (CCLocationInfo)EJBContext.clone(paramCCLocationInfo));
/* 4018:     */       
/* 4019:4406 */       localCCLocationInfo2 = (CCLocationInfo)EJBContext.clone(localCCLocationInfo2);
/* 4020:4407 */       localEJBContext.returnToPool();
/* 4021:4408 */       return localCCLocationInfo2;
/* 4022:     */     }
/* 4023:     */     catch (Exception localException)
/* 4024:     */     {
/* 4025:4412 */       if (localEJBContext != null)
/* 4026:     */       {
/* 4027:4414 */         if ((localException instanceof FFSException)) {
/* 4028:4416 */           throw ((FFSException)EJBContext.clone(localException));
/* 4029:     */         }
/* 4030:4418 */         localEJBContext.throwRemote(localException);
/* 4031:     */       }
/* 4032:4420 */       throw new EJBException(localException);
/* 4033:     */     }
/* 4034:     */     finally
/* 4035:     */     {
/* 4036:4424 */       this._comp.setCurrent(localJavaComponent);
/* 4037:     */     }
/* 4038:     */   }
/* 4039:     */   
/* 4040:     */   public CCLocationInfo modCCLocation(CCLocationInfo paramCCLocationInfo)
/* 4041:     */     throws RemoteException, FFSException
/* 4042:     */   {
/* 4043:4432 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4044:4433 */     EJBContext localEJBContext = null;
/* 4045:     */     try
/* 4046:     */     {
/* 4047:4436 */       localEJBContext = this._comp.getPooledInstance();
/* 4048:4437 */       if (localEJBContext == null) {
/* 4049:4439 */         localEJBContext = _create();
/* 4050:     */       }
/* 4051:4441 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4052:4442 */       if (localEJBContext.debug) {
/* 4053:4444 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modCCLocation"));
/* 4054:     */       }
/* 4055:4446 */       CCLocationInfo localCCLocationInfo2 = localBPWServicesBean
/* 4056:4447 */         .modCCLocation(
/* 4057:4448 */         (CCLocationInfo)EJBContext.clone(paramCCLocationInfo));
/* 4058:     */       
/* 4059:4450 */       localCCLocationInfo2 = (CCLocationInfo)EJBContext.clone(localCCLocationInfo2);
/* 4060:4451 */       localEJBContext.returnToPool();
/* 4061:4452 */       return localCCLocationInfo2;
/* 4062:     */     }
/* 4063:     */     catch (Exception localException)
/* 4064:     */     {
/* 4065:4456 */       if (localEJBContext != null)
/* 4066:     */       {
/* 4067:4458 */         if ((localException instanceof FFSException)) {
/* 4068:4460 */           throw ((FFSException)EJBContext.clone(localException));
/* 4069:     */         }
/* 4070:4462 */         localEJBContext.throwRemote(localException);
/* 4071:     */       }
/* 4072:4464 */       throw new EJBException(localException);
/* 4073:     */     }
/* 4074:     */     finally
/* 4075:     */     {
/* 4076:4468 */       this._comp.setCurrent(localJavaComponent);
/* 4077:     */     }
/* 4078:     */   }
/* 4079:     */   
/* 4080:     */   public CCLocationInfo getCCLocation(CCLocationInfo paramCCLocationInfo)
/* 4081:     */     throws RemoteException, FFSException
/* 4082:     */   {
/* 4083:4476 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4084:4477 */     EJBContext localEJBContext = null;
/* 4085:     */     try
/* 4086:     */     {
/* 4087:4480 */       localEJBContext = this._comp.getPooledInstance();
/* 4088:4481 */       if (localEJBContext == null) {
/* 4089:4483 */         localEJBContext = _create();
/* 4090:     */       }
/* 4091:4485 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4092:4486 */       if (localEJBContext.debug) {
/* 4093:4488 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCLocation"));
/* 4094:     */       }
/* 4095:4490 */       CCLocationInfo localCCLocationInfo2 = localBPWServicesBean
/* 4096:4491 */         .getCCLocation(
/* 4097:4492 */         (CCLocationInfo)EJBContext.clone(paramCCLocationInfo));
/* 4098:     */       
/* 4099:4494 */       localCCLocationInfo2 = (CCLocationInfo)EJBContext.clone(localCCLocationInfo2);
/* 4100:4495 */       localEJBContext.returnToPool();
/* 4101:4496 */       return localCCLocationInfo2;
/* 4102:     */     }
/* 4103:     */     catch (Exception localException)
/* 4104:     */     {
/* 4105:4500 */       if (localEJBContext != null)
/* 4106:     */       {
/* 4107:4502 */         if ((localException instanceof FFSException)) {
/* 4108:4504 */           throw ((FFSException)EJBContext.clone(localException));
/* 4109:     */         }
/* 4110:4506 */         localEJBContext.throwRemote(localException);
/* 4111:     */       }
/* 4112:4508 */       throw new EJBException(localException);
/* 4113:     */     }
/* 4114:     */     finally
/* 4115:     */     {
/* 4116:4512 */       this._comp.setCurrent(localJavaComponent);
/* 4117:     */     }
/* 4118:     */   }
/* 4119:     */   
/* 4120:     */   public CCLocationInfoList getCCLocationList(CCLocationInfoList paramCCLocationInfoList)
/* 4121:     */     throws RemoteException, FFSException
/* 4122:     */   {
/* 4123:4520 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4124:4521 */     EJBContext localEJBContext = null;
/* 4125:     */     try
/* 4126:     */     {
/* 4127:4524 */       localEJBContext = this._comp.getPooledInstance();
/* 4128:4525 */       if (localEJBContext == null) {
/* 4129:4527 */         localEJBContext = _create();
/* 4130:     */       }
/* 4131:4529 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4132:4530 */       if (localEJBContext.debug) {
/* 4133:4532 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCLocationList"));
/* 4134:     */       }
/* 4135:4534 */       CCLocationInfoList localCCLocationInfoList2 = localBPWServicesBean
/* 4136:4535 */         .getCCLocationList(
/* 4137:4536 */         (CCLocationInfoList)EJBContext.clone(paramCCLocationInfoList));
/* 4138:     */       
/* 4139:4538 */       localCCLocationInfoList2 = (CCLocationInfoList)EJBContext.clone(localCCLocationInfoList2);
/* 4140:4539 */       localEJBContext.returnToPool();
/* 4141:4540 */       return localCCLocationInfoList2;
/* 4142:     */     }
/* 4143:     */     catch (Exception localException)
/* 4144:     */     {
/* 4145:4544 */       if (localEJBContext != null)
/* 4146:     */       {
/* 4147:4546 */         if ((localException instanceof FFSException)) {
/* 4148:4548 */           throw ((FFSException)EJBContext.clone(localException));
/* 4149:     */         }
/* 4150:4550 */         localEJBContext.throwRemote(localException);
/* 4151:     */       }
/* 4152:4552 */       throw new EJBException(localException);
/* 4153:     */     }
/* 4154:     */     finally
/* 4155:     */     {
/* 4156:4556 */       this._comp.setCurrent(localJavaComponent);
/* 4157:     */     }
/* 4158:     */   }
/* 4159:     */   
/* 4160:     */   public CCEntryInfo addCCEntry(CCEntryInfo paramCCEntryInfo)
/* 4161:     */     throws RemoteException, FFSException
/* 4162:     */   {
/* 4163:4564 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4164:4565 */     EJBContext localEJBContext = null;
/* 4165:     */     try
/* 4166:     */     {
/* 4167:4568 */       localEJBContext = this._comp.getPooledInstance();
/* 4168:4569 */       if (localEJBContext == null) {
/* 4169:4571 */         localEJBContext = _create();
/* 4170:     */       }
/* 4171:4573 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4172:4574 */       if (localEJBContext.debug) {
/* 4173:4576 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCCEntry"));
/* 4174:     */       }
/* 4175:4578 */       CCEntryInfo localCCEntryInfo2 = localBPWServicesBean
/* 4176:4579 */         .addCCEntry(
/* 4177:4580 */         (CCEntryInfo)EJBContext.clone(paramCCEntryInfo));
/* 4178:     */       
/* 4179:4582 */       localCCEntryInfo2 = (CCEntryInfo)EJBContext.clone(localCCEntryInfo2);
/* 4180:4583 */       localEJBContext.returnToPool();
/* 4181:4584 */       return localCCEntryInfo2;
/* 4182:     */     }
/* 4183:     */     catch (Exception localException)
/* 4184:     */     {
/* 4185:4588 */       if (localEJBContext != null)
/* 4186:     */       {
/* 4187:4590 */         if ((localException instanceof FFSException)) {
/* 4188:4592 */           throw ((FFSException)EJBContext.clone(localException));
/* 4189:     */         }
/* 4190:4594 */         localEJBContext.throwRemote(localException);
/* 4191:     */       }
/* 4192:4596 */       throw new EJBException(localException);
/* 4193:     */     }
/* 4194:     */     finally
/* 4195:     */     {
/* 4196:4600 */       this._comp.setCurrent(localJavaComponent);
/* 4197:     */     }
/* 4198:     */   }
/* 4199:     */   
/* 4200:     */   public CCEntryInfo cancelCCEntry(CCEntryInfo paramCCEntryInfo)
/* 4201:     */     throws RemoteException, FFSException
/* 4202:     */   {
/* 4203:4608 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4204:4609 */     EJBContext localEJBContext = null;
/* 4205:     */     try
/* 4206:     */     {
/* 4207:4612 */       localEJBContext = this._comp.getPooledInstance();
/* 4208:4613 */       if (localEJBContext == null) {
/* 4209:4615 */         localEJBContext = _create();
/* 4210:     */       }
/* 4211:4617 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4212:4618 */       if (localEJBContext.debug) {
/* 4213:4620 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancelCCEntry"));
/* 4214:     */       }
/* 4215:4622 */       CCEntryInfo localCCEntryInfo2 = localBPWServicesBean
/* 4216:4623 */         .cancelCCEntry(
/* 4217:4624 */         (CCEntryInfo)EJBContext.clone(paramCCEntryInfo));
/* 4218:     */       
/* 4219:4626 */       localCCEntryInfo2 = (CCEntryInfo)EJBContext.clone(localCCEntryInfo2);
/* 4220:4627 */       localEJBContext.returnToPool();
/* 4221:4628 */       return localCCEntryInfo2;
/* 4222:     */     }
/* 4223:     */     catch (Exception localException)
/* 4224:     */     {
/* 4225:4632 */       if (localEJBContext != null)
/* 4226:     */       {
/* 4227:4634 */         if ((localException instanceof FFSException)) {
/* 4228:4636 */           throw ((FFSException)EJBContext.clone(localException));
/* 4229:     */         }
/* 4230:4638 */         localEJBContext.throwRemote(localException);
/* 4231:     */       }
/* 4232:4640 */       throw new EJBException(localException);
/* 4233:     */     }
/* 4234:     */     finally
/* 4235:     */     {
/* 4236:4644 */       this._comp.setCurrent(localJavaComponent);
/* 4237:     */     }
/* 4238:     */   }
/* 4239:     */   
/* 4240:     */   public CCEntryInfo modCCEntry(CCEntryInfo paramCCEntryInfo)
/* 4241:     */     throws RemoteException, FFSException
/* 4242:     */   {
/* 4243:4652 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4244:4653 */     EJBContext localEJBContext = null;
/* 4245:     */     try
/* 4246:     */     {
/* 4247:4656 */       localEJBContext = this._comp.getPooledInstance();
/* 4248:4657 */       if (localEJBContext == null) {
/* 4249:4659 */         localEJBContext = _create();
/* 4250:     */       }
/* 4251:4661 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4252:4662 */       if (localEJBContext.debug) {
/* 4253:4664 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modCCEntry"));
/* 4254:     */       }
/* 4255:4666 */       CCEntryInfo localCCEntryInfo2 = localBPWServicesBean
/* 4256:4667 */         .modCCEntry(
/* 4257:4668 */         (CCEntryInfo)EJBContext.clone(paramCCEntryInfo));
/* 4258:     */       
/* 4259:4670 */       localCCEntryInfo2 = (CCEntryInfo)EJBContext.clone(localCCEntryInfo2);
/* 4260:4671 */       localEJBContext.returnToPool();
/* 4261:4672 */       return localCCEntryInfo2;
/* 4262:     */     }
/* 4263:     */     catch (Exception localException)
/* 4264:     */     {
/* 4265:4676 */       if (localEJBContext != null)
/* 4266:     */       {
/* 4267:4678 */         if ((localException instanceof FFSException)) {
/* 4268:4680 */           throw ((FFSException)EJBContext.clone(localException));
/* 4269:     */         }
/* 4270:4682 */         localEJBContext.throwRemote(localException);
/* 4271:     */       }
/* 4272:4684 */       throw new EJBException(localException);
/* 4273:     */     }
/* 4274:     */     finally
/* 4275:     */     {
/* 4276:4688 */       this._comp.setCurrent(localJavaComponent);
/* 4277:     */     }
/* 4278:     */   }
/* 4279:     */   
/* 4280:     */   public CCEntryInfo getCCEntry(CCEntryInfo paramCCEntryInfo)
/* 4281:     */     throws RemoteException, FFSException
/* 4282:     */   {
/* 4283:4696 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4284:4697 */     EJBContext localEJBContext = null;
/* 4285:     */     try
/* 4286:     */     {
/* 4287:4700 */       localEJBContext = this._comp.getPooledInstance();
/* 4288:4701 */       if (localEJBContext == null) {
/* 4289:4703 */         localEJBContext = _create();
/* 4290:     */       }
/* 4291:4705 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4292:4706 */       if (localEJBContext.debug) {
/* 4293:4708 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCEntry"));
/* 4294:     */       }
/* 4295:4710 */       CCEntryInfo localCCEntryInfo2 = localBPWServicesBean
/* 4296:4711 */         .getCCEntry(
/* 4297:4712 */         (CCEntryInfo)EJBContext.clone(paramCCEntryInfo));
/* 4298:     */       
/* 4299:4714 */       localCCEntryInfo2 = (CCEntryInfo)EJBContext.clone(localCCEntryInfo2);
/* 4300:4715 */       localEJBContext.returnToPool();
/* 4301:4716 */       return localCCEntryInfo2;
/* 4302:     */     }
/* 4303:     */     catch (Exception localException)
/* 4304:     */     {
/* 4305:4720 */       if (localEJBContext != null)
/* 4306:     */       {
/* 4307:4722 */         if ((localException instanceof FFSException)) {
/* 4308:4724 */           throw ((FFSException)EJBContext.clone(localException));
/* 4309:     */         }
/* 4310:4726 */         localEJBContext.throwRemote(localException);
/* 4311:     */       }
/* 4312:4728 */       throw new EJBException(localException);
/* 4313:     */     }
/* 4314:     */     finally
/* 4315:     */     {
/* 4316:4732 */       this._comp.setCurrent(localJavaComponent);
/* 4317:     */     }
/* 4318:     */   }
/* 4319:     */   
/* 4320:     */   public CCEntryHistInfo getCCEntryHist(CCEntryHistInfo paramCCEntryHistInfo)
/* 4321:     */     throws RemoteException, FFSException
/* 4322:     */   {
/* 4323:4740 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4324:4741 */     EJBContext localEJBContext = null;
/* 4325:     */     try
/* 4326:     */     {
/* 4327:4744 */       localEJBContext = this._comp.getPooledInstance();
/* 4328:4745 */       if (localEJBContext == null) {
/* 4329:4747 */         localEJBContext = _create();
/* 4330:     */       }
/* 4331:4749 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4332:4750 */       if (localEJBContext.debug) {
/* 4333:4752 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCEntryHist"));
/* 4334:     */       }
/* 4335:4754 */       CCEntryHistInfo localCCEntryHistInfo2 = localBPWServicesBean
/* 4336:4755 */         .getCCEntryHist(
/* 4337:4756 */         (CCEntryHistInfo)EJBContext.clone(paramCCEntryHistInfo));
/* 4338:     */       
/* 4339:4758 */       localCCEntryHistInfo2 = (CCEntryHistInfo)EJBContext.clone(localCCEntryHistInfo2);
/* 4340:4759 */       localEJBContext.returnToPool();
/* 4341:4760 */       return localCCEntryHistInfo2;
/* 4342:     */     }
/* 4343:     */     catch (Exception localException)
/* 4344:     */     {
/* 4345:4764 */       if (localEJBContext != null)
/* 4346:     */       {
/* 4347:4766 */         if ((localException instanceof FFSException)) {
/* 4348:4768 */           throw ((FFSException)EJBContext.clone(localException));
/* 4349:     */         }
/* 4350:4770 */         localEJBContext.throwRemote(localException);
/* 4351:     */       }
/* 4352:4772 */       throw new EJBException(localException);
/* 4353:     */     }
/* 4354:     */     finally
/* 4355:     */     {
/* 4356:4776 */       this._comp.setCurrent(localJavaComponent);
/* 4357:     */     }
/* 4358:     */   }
/* 4359:     */   
/* 4360:     */   public CCEntrySummaryInfoList getCCEntrySummaryList(CCEntrySummaryInfoList paramCCEntrySummaryInfoList)
/* 4361:     */     throws RemoteException, FFSException
/* 4362:     */   {
/* 4363:4784 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4364:4785 */     EJBContext localEJBContext = null;
/* 4365:     */     try
/* 4366:     */     {
/* 4367:4788 */       localEJBContext = this._comp.getPooledInstance();
/* 4368:4789 */       if (localEJBContext == null) {
/* 4369:4791 */         localEJBContext = _create();
/* 4370:     */       }
/* 4371:4793 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4372:4794 */       if (localEJBContext.debug) {
/* 4373:4796 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCCEntrySummaryList"));
/* 4374:     */       }
/* 4375:4798 */       CCEntrySummaryInfoList localCCEntrySummaryInfoList2 = localBPWServicesBean
/* 4376:4799 */         .getCCEntrySummaryList(
/* 4377:4800 */         (CCEntrySummaryInfoList)EJBContext.clone(paramCCEntrySummaryInfoList));
/* 4378:     */       
/* 4379:4802 */       localCCEntrySummaryInfoList2 = (CCEntrySummaryInfoList)EJBContext.clone(localCCEntrySummaryInfoList2);
/* 4380:4803 */       localEJBContext.returnToPool();
/* 4381:4804 */       return localCCEntrySummaryInfoList2;
/* 4382:     */     }
/* 4383:     */     catch (Exception localException)
/* 4384:     */     {
/* 4385:4808 */       if (localEJBContext != null)
/* 4386:     */       {
/* 4387:4810 */         if ((localException instanceof FFSException)) {
/* 4388:4812 */           throw ((FFSException)EJBContext.clone(localException));
/* 4389:     */         }
/* 4390:4814 */         localEJBContext.throwRemote(localException);
/* 4391:     */       }
/* 4392:4816 */       throw new EJBException(localException);
/* 4393:     */     }
/* 4394:     */     finally
/* 4395:     */     {
/* 4396:4820 */       this._comp.setCurrent(localJavaComponent);
/* 4397:     */     }
/* 4398:     */   }
/* 4399:     */   
/* 4400:     */   public TransferInfo addTransfer(TransferInfo paramTransferInfo)
/* 4401:     */     throws RemoteException
/* 4402:     */   {
/* 4403:4828 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4404:4829 */     EJBContext localEJBContext = null;
/* 4405:     */     try
/* 4406:     */     {
/* 4407:4832 */       localEJBContext = this._comp.getPooledInstance();
/* 4408:4833 */       if (localEJBContext == null) {
/* 4409:4835 */         localEJBContext = _create();
/* 4410:     */       }
/* 4411:4837 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4412:4838 */       if (localEJBContext.debug) {
/* 4413:4840 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addTransfer"));
/* 4414:     */       }
/* 4415:4842 */       TransferInfo localTransferInfo2 = localBPWServicesBean
/* 4416:4843 */         .addTransfer(
/* 4417:4844 */         (TransferInfo)EJBContext.clone(paramTransferInfo));
/* 4418:     */       
/* 4419:4846 */       localTransferInfo2 = (TransferInfo)EJBContext.clone(localTransferInfo2);
/* 4420:4847 */       localEJBContext.returnToPool();
/* 4421:4848 */       return localTransferInfo2;
/* 4422:     */     }
/* 4423:     */     catch (Exception localException)
/* 4424:     */     {
/* 4425:4852 */       if (localEJBContext != null) {
/* 4426:4854 */         localEJBContext.throwRemote(localException);
/* 4427:     */       }
/* 4428:4856 */       throw new EJBException(localException);
/* 4429:     */     }
/* 4430:     */     finally
/* 4431:     */     {
/* 4432:4860 */       this._comp.setCurrent(localJavaComponent);
/* 4433:     */     }
/* 4434:     */   }
/* 4435:     */   
/* 4436:     */   public TransferInfo modTransfer(TransferInfo paramTransferInfo)
/* 4437:     */     throws RemoteException
/* 4438:     */   {
/* 4439:4868 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4440:4869 */     EJBContext localEJBContext = null;
/* 4441:     */     try
/* 4442:     */     {
/* 4443:4872 */       localEJBContext = this._comp.getPooledInstance();
/* 4444:4873 */       if (localEJBContext == null) {
/* 4445:4875 */         localEJBContext = _create();
/* 4446:     */       }
/* 4447:4877 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4448:4878 */       if (localEJBContext.debug) {
/* 4449:4880 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modTransfer"));
/* 4450:     */       }
/* 4451:4882 */       TransferInfo localTransferInfo2 = localBPWServicesBean
/* 4452:4883 */         .modTransfer(
/* 4453:4884 */         (TransferInfo)EJBContext.clone(paramTransferInfo));
/* 4454:     */       
/* 4455:4886 */       localTransferInfo2 = (TransferInfo)EJBContext.clone(localTransferInfo2);
/* 4456:4887 */       localEJBContext.returnToPool();
/* 4457:4888 */       return localTransferInfo2;
/* 4458:     */     }
/* 4459:     */     catch (Exception localException)
/* 4460:     */     {
/* 4461:4892 */       if (localEJBContext != null) {
/* 4462:4894 */         localEJBContext.throwRemote(localException);
/* 4463:     */       }
/* 4464:4896 */       throw new EJBException(localException);
/* 4465:     */     }
/* 4466:     */     finally
/* 4467:     */     {
/* 4468:4900 */       this._comp.setCurrent(localJavaComponent);
/* 4469:     */     }
/* 4470:     */   }
/* 4471:     */   
/* 4472:     */   public TransferInfo cancTransfer(TransferInfo paramTransferInfo)
/* 4473:     */     throws RemoteException
/* 4474:     */   {
/* 4475:4908 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4476:4909 */     EJBContext localEJBContext = null;
/* 4477:     */     try
/* 4478:     */     {
/* 4479:4912 */       localEJBContext = this._comp.getPooledInstance();
/* 4480:4913 */       if (localEJBContext == null) {
/* 4481:4915 */         localEJBContext = _create();
/* 4482:     */       }
/* 4483:4917 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4484:4918 */       if (localEJBContext.debug) {
/* 4485:4920 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancTransfer"));
/* 4486:     */       }
/* 4487:4922 */       TransferInfo localTransferInfo2 = localBPWServicesBean
/* 4488:4923 */         .cancTransfer(
/* 4489:4924 */         (TransferInfo)EJBContext.clone(paramTransferInfo));
/* 4490:     */       
/* 4491:4926 */       localTransferInfo2 = (TransferInfo)EJBContext.clone(localTransferInfo2);
/* 4492:4927 */       localEJBContext.returnToPool();
/* 4493:4928 */       return localTransferInfo2;
/* 4494:     */     }
/* 4495:     */     catch (Exception localException)
/* 4496:     */     {
/* 4497:4932 */       if (localEJBContext != null) {
/* 4498:4934 */         localEJBContext.throwRemote(localException);
/* 4499:     */       }
/* 4500:4936 */       throw new EJBException(localException);
/* 4501:     */     }
/* 4502:     */     finally
/* 4503:     */     {
/* 4504:4940 */       this._comp.setCurrent(localJavaComponent);
/* 4505:     */     }
/* 4506:     */   }
/* 4507:     */   
/* 4508:     */   public TransferInfo getTransferBySrvrTId(String paramString1, String paramString2)
/* 4509:     */     throws RemoteException
/* 4510:     */   {
/* 4511:4949 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4512:4950 */     EJBContext localEJBContext = null;
/* 4513:     */     try
/* 4514:     */     {
/* 4515:4953 */       localEJBContext = this._comp.getPooledInstance();
/* 4516:4954 */       if (localEJBContext == null) {
/* 4517:4956 */         localEJBContext = _create();
/* 4518:     */       }
/* 4519:4958 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4520:4959 */       if (localEJBContext.debug) {
/* 4521:4961 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransferBySrvrTId"));
/* 4522:     */       }
/* 4523:4963 */       TransferInfo localTransferInfo2 = localBPWServicesBean
/* 4524:4964 */         .getTransferBySrvrTId(
/* 4525:4965 */         paramString1, 
/* 4526:4966 */         paramString2);
/* 4527:     */       
/* 4528:4968 */       localTransferInfo2 = (TransferInfo)EJBContext.clone(localTransferInfo2);
/* 4529:4969 */       localEJBContext.returnToPool();
/* 4530:4970 */       return localTransferInfo2;
/* 4531:     */     }
/* 4532:     */     catch (Exception localException)
/* 4533:     */     {
/* 4534:4974 */       if (localEJBContext != null) {
/* 4535:4976 */         localEJBContext.throwRemote(localException);
/* 4536:     */       }
/* 4537:4978 */       throw new EJBException(localException);
/* 4538:     */     }
/* 4539:     */     finally
/* 4540:     */     {
/* 4541:4982 */       this._comp.setCurrent(localJavaComponent);
/* 4542:     */     }
/* 4543:     */   }
/* 4544:     */   
/* 4545:     */   public TransferInfo getTransferBySrvrTId(String paramString)
/* 4546:     */     throws RemoteException
/* 4547:     */   {
/* 4548:4990 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4549:4991 */     EJBContext localEJBContext = null;
/* 4550:     */     try
/* 4551:     */     {
/* 4552:4994 */       localEJBContext = this._comp.getPooledInstance();
/* 4553:4995 */       if (localEJBContext == null) {
/* 4554:4997 */         localEJBContext = _create();
/* 4555:     */       }
/* 4556:4999 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4557:5000 */       if (localEJBContext.debug) {
/* 4558:5002 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransferBySrvrTId"));
/* 4559:     */       }
/* 4560:5004 */       TransferInfo localTransferInfo2 = localBPWServicesBean
/* 4561:5005 */         .getTransferBySrvrTId(
/* 4562:5006 */         paramString);
/* 4563:     */       
/* 4564:5008 */       localTransferInfo2 = (TransferInfo)EJBContext.clone(localTransferInfo2);
/* 4565:5009 */       localEJBContext.returnToPool();
/* 4566:5010 */       return localTransferInfo2;
/* 4567:     */     }
/* 4568:     */     catch (Exception localException)
/* 4569:     */     {
/* 4570:5014 */       if (localEJBContext != null) {
/* 4571:5016 */         localEJBContext.throwRemote(localException);
/* 4572:     */       }
/* 4573:5018 */       throw new EJBException(localException);
/* 4574:     */     }
/* 4575:     */     finally
/* 4576:     */     {
/* 4577:5022 */       this._comp.setCurrent(localJavaComponent);
/* 4578:     */     }
/* 4579:     */   }
/* 4580:     */   
/* 4581:     */   public TransferInfo getTransferByTrackingId(String paramString)
/* 4582:     */     throws RemoteException
/* 4583:     */   {
/* 4584:5030 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4585:5031 */     EJBContext localEJBContext = null;
/* 4586:     */     try
/* 4587:     */     {
/* 4588:5034 */       localEJBContext = this._comp.getPooledInstance();
/* 4589:5035 */       if (localEJBContext == null) {
/* 4590:5037 */         localEJBContext = _create();
/* 4591:     */       }
/* 4592:5039 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4593:5040 */       if (localEJBContext.debug) {
/* 4594:5042 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransferByTrackingId"));
/* 4595:     */       }
/* 4596:5044 */       TransferInfo localTransferInfo2 = localBPWServicesBean
/* 4597:5045 */         .getTransferByTrackingId(
/* 4598:5046 */         paramString);
/* 4599:     */       
/* 4600:5048 */       localTransferInfo2 = (TransferInfo)EJBContext.clone(localTransferInfo2);
/* 4601:5049 */       localEJBContext.returnToPool();
/* 4602:5050 */       return localTransferInfo2;
/* 4603:     */     }
/* 4604:     */     catch (Exception localException)
/* 4605:     */     {
/* 4606:5054 */       if (localEJBContext != null) {
/* 4607:5056 */         localEJBContext.throwRemote(localException);
/* 4608:     */       }
/* 4609:5058 */       throw new EJBException(localException);
/* 4610:     */     }
/* 4611:     */     finally
/* 4612:     */     {
/* 4613:5062 */       this._comp.setCurrent(localJavaComponent);
/* 4614:     */     }
/* 4615:     */   }
/* 4616:     */   
/* 4617:     */   public TransferInfo[] getTransfersBySrvrTId(String[] paramArrayOfString)
/* 4618:     */     throws RemoteException
/* 4619:     */   {
/* 4620:5070 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4621:5071 */     EJBContext localEJBContext = null;
/* 4622:     */     try
/* 4623:     */     {
/* 4624:5074 */       localEJBContext = this._comp.getPooledInstance();
/* 4625:5075 */       if (localEJBContext == null) {
/* 4626:5077 */         localEJBContext = _create();
/* 4627:     */       }
/* 4628:5079 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4629:5080 */       if (localEJBContext.debug) {
/* 4630:5082 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransfersBySrvrTId"));
/* 4631:     */       }
/* 4632:5084 */       TransferInfo[] arrayOfTransferInfo2 = localBPWServicesBean
/* 4633:5085 */         .getTransfersBySrvrTId(
/* 4634:5086 */         StringSeqHelper.clone(paramArrayOfString));
/* 4635:     */       
/* 4636:5088 */       arrayOfTransferInfo2 = TransferInfoSeqHelper.clone(arrayOfTransferInfo2);
/* 4637:5089 */       localEJBContext.returnToPool();
/* 4638:5090 */       return arrayOfTransferInfo2;
/* 4639:     */     }
/* 4640:     */     catch (Exception localException)
/* 4641:     */     {
/* 4642:5094 */       if (localEJBContext != null) {
/* 4643:5096 */         localEJBContext.throwRemote(localException);
/* 4644:     */       }
/* 4645:5098 */       throw new EJBException(localException);
/* 4646:     */     }
/* 4647:     */     finally
/* 4648:     */     {
/* 4649:5102 */       this._comp.setCurrent(localJavaComponent);
/* 4650:     */     }
/* 4651:     */   }
/* 4652:     */   
/* 4653:     */   public TransferInfo[] getTransfersByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
/* 4654:     */     throws RemoteException
/* 4655:     */   {
/* 4656:5111 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4657:5112 */     EJBContext localEJBContext = null;
/* 4658:     */     try
/* 4659:     */     {
/* 4660:5115 */       localEJBContext = this._comp.getPooledInstance();
/* 4661:5116 */       if (localEJBContext == null) {
/* 4662:5118 */         localEJBContext = _create();
/* 4663:     */       }
/* 4664:5120 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4665:5121 */       if (localEJBContext.debug) {
/* 4666:5123 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransfersByRecSrvrTId"));
/* 4667:     */       }
/* 4668:5125 */       TransferInfo[] arrayOfTransferInfo2 = localBPWServicesBean
/* 4669:5126 */         .getTransfersByRecSrvrTId(
/* 4670:5127 */         StringSeqHelper.clone(paramArrayOfString), 
/* 4671:5128 */         paramBoolean);
/* 4672:     */       
/* 4673:5130 */       arrayOfTransferInfo2 = TransferInfoSeqHelper.clone(arrayOfTransferInfo2);
/* 4674:5131 */       localEJBContext.returnToPool();
/* 4675:5132 */       return arrayOfTransferInfo2;
/* 4676:     */     }
/* 4677:     */     catch (Exception localException)
/* 4678:     */     {
/* 4679:5136 */       if (localEJBContext != null) {
/* 4680:5138 */         localEJBContext.throwRemote(localException);
/* 4681:     */       }
/* 4682:5140 */       throw new EJBException(localException);
/* 4683:     */     }
/* 4684:     */     finally
/* 4685:     */     {
/* 4686:5144 */       this._comp.setCurrent(localJavaComponent);
/* 4687:     */     }
/* 4688:     */   }
/* 4689:     */   
/* 4690:     */   public TransferInfo[] getTransfersByRecSrvrTId(String paramString, boolean paramBoolean)
/* 4691:     */     throws RemoteException
/* 4692:     */   {
/* 4693:5153 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4694:5154 */     EJBContext localEJBContext = null;
/* 4695:     */     try
/* 4696:     */     {
/* 4697:5157 */       localEJBContext = this._comp.getPooledInstance();
/* 4698:5158 */       if (localEJBContext == null) {
/* 4699:5160 */         localEJBContext = _create();
/* 4700:     */       }
/* 4701:5162 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4702:5163 */       if (localEJBContext.debug) {
/* 4703:5165 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransfersByRecSrvrTId"));
/* 4704:     */       }
/* 4705:5167 */       TransferInfo[] arrayOfTransferInfo2 = localBPWServicesBean
/* 4706:5168 */         .getTransfersByRecSrvrTId(
/* 4707:5169 */         paramString, 
/* 4708:5170 */         paramBoolean);
/* 4709:     */       
/* 4710:5172 */       arrayOfTransferInfo2 = TransferInfoSeqHelper.clone(arrayOfTransferInfo2);
/* 4711:5173 */       localEJBContext.returnToPool();
/* 4712:5174 */       return arrayOfTransferInfo2;
/* 4713:     */     }
/* 4714:     */     catch (Exception localException)
/* 4715:     */     {
/* 4716:5178 */       if (localEJBContext != null) {
/* 4717:5180 */         localEJBContext.throwRemote(localException);
/* 4718:     */       }
/* 4719:5182 */       throw new EJBException(localException);
/* 4720:     */     }
/* 4721:     */     finally
/* 4722:     */     {
/* 4723:5186 */       this._comp.setCurrent(localJavaComponent);
/* 4724:     */     }
/* 4725:     */   }
/* 4726:     */   
/* 4727:     */   public TransferInfo[] getTransfersByTrackingId(String[] paramArrayOfString)
/* 4728:     */     throws RemoteException
/* 4729:     */   {
/* 4730:5194 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4731:5195 */     EJBContext localEJBContext = null;
/* 4732:     */     try
/* 4733:     */     {
/* 4734:5198 */       localEJBContext = this._comp.getPooledInstance();
/* 4735:5199 */       if (localEJBContext == null) {
/* 4736:5201 */         localEJBContext = _create();
/* 4737:     */       }
/* 4738:5203 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4739:5204 */       if (localEJBContext.debug) {
/* 4740:5206 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransfersByTrackingId"));
/* 4741:     */       }
/* 4742:5208 */       TransferInfo[] arrayOfTransferInfo2 = localBPWServicesBean
/* 4743:5209 */         .getTransfersByTrackingId(
/* 4744:5210 */         StringSeqHelper.clone(paramArrayOfString));
/* 4745:     */       
/* 4746:5212 */       arrayOfTransferInfo2 = TransferInfoSeqHelper.clone(arrayOfTransferInfo2);
/* 4747:5213 */       localEJBContext.returnToPool();
/* 4748:5214 */       return arrayOfTransferInfo2;
/* 4749:     */     }
/* 4750:     */     catch (Exception localException)
/* 4751:     */     {
/* 4752:5218 */       if (localEJBContext != null) {
/* 4753:5220 */         localEJBContext.throwRemote(localException);
/* 4754:     */       }
/* 4755:5222 */       throw new EJBException(localException);
/* 4756:     */     }
/* 4757:     */     finally
/* 4758:     */     {
/* 4759:5226 */       this._comp.setCurrent(localJavaComponent);
/* 4760:     */     }
/* 4761:     */   }
/* 4762:     */   
/* 4763:     */   public BPWHist getTransferHistory(BPWHist paramBPWHist)
/* 4764:     */     throws RemoteException
/* 4765:     */   {
/* 4766:5234 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4767:5235 */     EJBContext localEJBContext = null;
/* 4768:     */     try
/* 4769:     */     {
/* 4770:5238 */       localEJBContext = this._comp.getPooledInstance();
/* 4771:5239 */       if (localEJBContext == null) {
/* 4772:5241 */         localEJBContext = _create();
/* 4773:     */       }
/* 4774:5243 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4775:5244 */       if (localEJBContext.debug) {
/* 4776:5246 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransferHistory"));
/* 4777:     */       }
/* 4778:5248 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 4779:5249 */         .getTransferHistory(
/* 4780:5250 */         BPWHistHelper.clone(paramBPWHist));
/* 4781:     */       
/* 4782:5252 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 4783:5253 */       localEJBContext.returnToPool();
/* 4784:5254 */       return localBPWHist2;
/* 4785:     */     }
/* 4786:     */     catch (Exception localException)
/* 4787:     */     {
/* 4788:5258 */       if (localEJBContext != null) {
/* 4789:5260 */         localEJBContext.throwRemote(localException);
/* 4790:     */       }
/* 4791:5262 */       throw new EJBException(localException);
/* 4792:     */     }
/* 4793:     */     finally
/* 4794:     */     {
/* 4795:5266 */       this._comp.setCurrent(localJavaComponent);
/* 4796:     */     }
/* 4797:     */   }
/* 4798:     */   
/* 4799:     */   public TransferInfo[] addTransfers(TransferInfo[] paramArrayOfTransferInfo)
/* 4800:     */     throws RemoteException
/* 4801:     */   {
/* 4802:5274 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4803:5275 */     EJBContext localEJBContext = null;
/* 4804:     */     try
/* 4805:     */     {
/* 4806:5278 */       localEJBContext = this._comp.getPooledInstance();
/* 4807:5279 */       if (localEJBContext == null) {
/* 4808:5281 */         localEJBContext = _create();
/* 4809:     */       }
/* 4810:5283 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4811:5284 */       if (localEJBContext.debug) {
/* 4812:5286 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addTransfers"));
/* 4813:     */       }
/* 4814:5288 */       TransferInfo[] arrayOfTransferInfo2 = localBPWServicesBean
/* 4815:5289 */         .addTransfers(
/* 4816:5290 */         TransferInfoSeqHelper.clone(paramArrayOfTransferInfo));
/* 4817:     */       
/* 4818:5292 */       arrayOfTransferInfo2 = TransferInfoSeqHelper.clone(arrayOfTransferInfo2);
/* 4819:5293 */       localEJBContext.returnToPool();
/* 4820:5294 */       return arrayOfTransferInfo2;
/* 4821:     */     }
/* 4822:     */     catch (Exception localException)
/* 4823:     */     {
/* 4824:5298 */       if (localEJBContext != null) {
/* 4825:5300 */         localEJBContext.throwRemote(localException);
/* 4826:     */       }
/* 4827:5302 */       throw new EJBException(localException);
/* 4828:     */     }
/* 4829:     */     finally
/* 4830:     */     {
/* 4831:5306 */       this._comp.setCurrent(localJavaComponent);
/* 4832:     */     }
/* 4833:     */   }
/* 4834:     */   
/* 4835:     */   public RecTransferInfo addRecTransfer(RecTransferInfo paramRecTransferInfo)
/* 4836:     */     throws RemoteException
/* 4837:     */   {
/* 4838:5314 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4839:5315 */     EJBContext localEJBContext = null;
/* 4840:     */     try
/* 4841:     */     {
/* 4842:5318 */       localEJBContext = this._comp.getPooledInstance();
/* 4843:5319 */       if (localEJBContext == null) {
/* 4844:5321 */         localEJBContext = _create();
/* 4845:     */       }
/* 4846:5323 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4847:5324 */       if (localEJBContext.debug) {
/* 4848:5326 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addRecTransfer"));
/* 4849:     */       }
/* 4850:5328 */       RecTransferInfo localRecTransferInfo2 = localBPWServicesBean
/* 4851:5329 */         .addRecTransfer(
/* 4852:5330 */         (RecTransferInfo)EJBContext.clone(paramRecTransferInfo));
/* 4853:     */       
/* 4854:5332 */       localRecTransferInfo2 = (RecTransferInfo)EJBContext.clone(localRecTransferInfo2);
/* 4855:5333 */       localEJBContext.returnToPool();
/* 4856:5334 */       return localRecTransferInfo2;
/* 4857:     */     }
/* 4858:     */     catch (Exception localException)
/* 4859:     */     {
/* 4860:5338 */       if (localEJBContext != null) {
/* 4861:5340 */         localEJBContext.throwRemote(localException);
/* 4862:     */       }
/* 4863:5342 */       throw new EJBException(localException);
/* 4864:     */     }
/* 4865:     */     finally
/* 4866:     */     {
/* 4867:5346 */       this._comp.setCurrent(localJavaComponent);
/* 4868:     */     }
/* 4869:     */   }
/* 4870:     */   
/* 4871:     */   public RecTransferInfo modRecTransfer(RecTransferInfo paramRecTransferInfo)
/* 4872:     */     throws RemoteException
/* 4873:     */   {
/* 4874:5354 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4875:5355 */     EJBContext localEJBContext = null;
/* 4876:     */     try
/* 4877:     */     {
/* 4878:5358 */       localEJBContext = this._comp.getPooledInstance();
/* 4879:5359 */       if (localEJBContext == null) {
/* 4880:5361 */         localEJBContext = _create();
/* 4881:     */       }
/* 4882:5363 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4883:5364 */       if (localEJBContext.debug) {
/* 4884:5366 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modRecTransfer"));
/* 4885:     */       }
/* 4886:5368 */       RecTransferInfo localRecTransferInfo2 = localBPWServicesBean
/* 4887:5369 */         .modRecTransfer(
/* 4888:5370 */         (RecTransferInfo)EJBContext.clone(paramRecTransferInfo));
/* 4889:     */       
/* 4890:5372 */       localRecTransferInfo2 = (RecTransferInfo)EJBContext.clone(localRecTransferInfo2);
/* 4891:5373 */       localEJBContext.returnToPool();
/* 4892:5374 */       return localRecTransferInfo2;
/* 4893:     */     }
/* 4894:     */     catch (Exception localException)
/* 4895:     */     {
/* 4896:5378 */       if (localEJBContext != null) {
/* 4897:5380 */         localEJBContext.throwRemote(localException);
/* 4898:     */       }
/* 4899:5382 */       throw new EJBException(localException);
/* 4900:     */     }
/* 4901:     */     finally
/* 4902:     */     {
/* 4903:5386 */       this._comp.setCurrent(localJavaComponent);
/* 4904:     */     }
/* 4905:     */   }
/* 4906:     */   
/* 4907:     */   public RecTransferInfo cancRecTransfer(RecTransferInfo paramRecTransferInfo)
/* 4908:     */     throws RemoteException
/* 4909:     */   {
/* 4910:5394 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4911:5395 */     EJBContext localEJBContext = null;
/* 4912:     */     try
/* 4913:     */     {
/* 4914:5398 */       localEJBContext = this._comp.getPooledInstance();
/* 4915:5399 */       if (localEJBContext == null) {
/* 4916:5401 */         localEJBContext = _create();
/* 4917:     */       }
/* 4918:5403 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4919:5404 */       if (localEJBContext.debug) {
/* 4920:5406 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancRecTransfer"));
/* 4921:     */       }
/* 4922:5408 */       RecTransferInfo localRecTransferInfo2 = localBPWServicesBean
/* 4923:5409 */         .cancRecTransfer(
/* 4924:5410 */         (RecTransferInfo)EJBContext.clone(paramRecTransferInfo));
/* 4925:     */       
/* 4926:5412 */       localRecTransferInfo2 = (RecTransferInfo)EJBContext.clone(localRecTransferInfo2);
/* 4927:5413 */       localEJBContext.returnToPool();
/* 4928:5414 */       return localRecTransferInfo2;
/* 4929:     */     }
/* 4930:     */     catch (Exception localException)
/* 4931:     */     {
/* 4932:5418 */       if (localEJBContext != null) {
/* 4933:5420 */         localEJBContext.throwRemote(localException);
/* 4934:     */       }
/* 4935:5422 */       throw new EJBException(localException);
/* 4936:     */     }
/* 4937:     */     finally
/* 4938:     */     {
/* 4939:5426 */       this._comp.setCurrent(localJavaComponent);
/* 4940:     */     }
/* 4941:     */   }
/* 4942:     */   
/* 4943:     */   public RecTransferInfo getRecTransferBySrvrTId(String paramString1, String paramString2)
/* 4944:     */     throws RemoteException
/* 4945:     */   {
/* 4946:5435 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4947:5436 */     EJBContext localEJBContext = null;
/* 4948:     */     try
/* 4949:     */     {
/* 4950:5439 */       localEJBContext = this._comp.getPooledInstance();
/* 4951:5440 */       if (localEJBContext == null) {
/* 4952:5442 */         localEJBContext = _create();
/* 4953:     */       }
/* 4954:5444 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4955:5445 */       if (localEJBContext.debug) {
/* 4956:5447 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecTransferBySrvrTId"));
/* 4957:     */       }
/* 4958:5449 */       RecTransferInfo localRecTransferInfo2 = localBPWServicesBean
/* 4959:5450 */         .getRecTransferBySrvrTId(
/* 4960:5451 */         paramString1, 
/* 4961:5452 */         paramString2);
/* 4962:     */       
/* 4963:5454 */       localRecTransferInfo2 = (RecTransferInfo)EJBContext.clone(localRecTransferInfo2);
/* 4964:5455 */       localEJBContext.returnToPool();
/* 4965:5456 */       return localRecTransferInfo2;
/* 4966:     */     }
/* 4967:     */     catch (Exception localException)
/* 4968:     */     {
/* 4969:5460 */       if (localEJBContext != null) {
/* 4970:5462 */         localEJBContext.throwRemote(localException);
/* 4971:     */       }
/* 4972:5464 */       throw new EJBException(localException);
/* 4973:     */     }
/* 4974:     */     finally
/* 4975:     */     {
/* 4976:5468 */       this._comp.setCurrent(localJavaComponent);
/* 4977:     */     }
/* 4978:     */   }
/* 4979:     */   
/* 4980:     */   public RecTransferInfo getRecTransferBySrvrTId(String paramString)
/* 4981:     */     throws RemoteException
/* 4982:     */   {
/* 4983:5476 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 4984:5477 */     EJBContext localEJBContext = null;
/* 4985:     */     try
/* 4986:     */     {
/* 4987:5480 */       localEJBContext = this._comp.getPooledInstance();
/* 4988:5481 */       if (localEJBContext == null) {
/* 4989:5483 */         localEJBContext = _create();
/* 4990:     */       }
/* 4991:5485 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 4992:5486 */       if (localEJBContext.debug) {
/* 4993:5488 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecTransferBySrvrTId"));
/* 4994:     */       }
/* 4995:5490 */       RecTransferInfo localRecTransferInfo2 = localBPWServicesBean
/* 4996:5491 */         .getRecTransferBySrvrTId(
/* 4997:5492 */         paramString);
/* 4998:     */       
/* 4999:5494 */       localRecTransferInfo2 = (RecTransferInfo)EJBContext.clone(localRecTransferInfo2);
/* 5000:5495 */       localEJBContext.returnToPool();
/* 5001:5496 */       return localRecTransferInfo2;
/* 5002:     */     }
/* 5003:     */     catch (Exception localException)
/* 5004:     */     {
/* 5005:5500 */       if (localEJBContext != null) {
/* 5006:5502 */         localEJBContext.throwRemote(localException);
/* 5007:     */       }
/* 5008:5504 */       throw new EJBException(localException);
/* 5009:     */     }
/* 5010:     */     finally
/* 5011:     */     {
/* 5012:5508 */       this._comp.setCurrent(localJavaComponent);
/* 5013:     */     }
/* 5014:     */   }
/* 5015:     */   
/* 5016:     */   public RecTransferInfo getRecTransferByTrackingId(String paramString)
/* 5017:     */     throws RemoteException
/* 5018:     */   {
/* 5019:5516 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5020:5517 */     EJBContext localEJBContext = null;
/* 5021:     */     try
/* 5022:     */     {
/* 5023:5520 */       localEJBContext = this._comp.getPooledInstance();
/* 5024:5521 */       if (localEJBContext == null) {
/* 5025:5523 */         localEJBContext = _create();
/* 5026:     */       }
/* 5027:5525 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5028:5526 */       if (localEJBContext.debug) {
/* 5029:5528 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecTransferByTrackingId"));
/* 5030:     */       }
/* 5031:5530 */       RecTransferInfo localRecTransferInfo2 = localBPWServicesBean
/* 5032:5531 */         .getRecTransferByTrackingId(
/* 5033:5532 */         paramString);
/* 5034:     */       
/* 5035:5534 */       localRecTransferInfo2 = (RecTransferInfo)EJBContext.clone(localRecTransferInfo2);
/* 5036:5535 */       localEJBContext.returnToPool();
/* 5037:5536 */       return localRecTransferInfo2;
/* 5038:     */     }
/* 5039:     */     catch (Exception localException)
/* 5040:     */     {
/* 5041:5540 */       if (localEJBContext != null) {
/* 5042:5542 */         localEJBContext.throwRemote(localException);
/* 5043:     */       }
/* 5044:5544 */       throw new EJBException(localException);
/* 5045:     */     }
/* 5046:     */     finally
/* 5047:     */     {
/* 5048:5548 */       this._comp.setCurrent(localJavaComponent);
/* 5049:     */     }
/* 5050:     */   }
/* 5051:     */   
/* 5052:     */   public RecTransferInfo[] getRecTransfersBySrvrTId(String[] paramArrayOfString)
/* 5053:     */     throws RemoteException
/* 5054:     */   {
/* 5055:5556 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5056:5557 */     EJBContext localEJBContext = null;
/* 5057:     */     try
/* 5058:     */     {
/* 5059:5560 */       localEJBContext = this._comp.getPooledInstance();
/* 5060:5561 */       if (localEJBContext == null) {
/* 5061:5563 */         localEJBContext = _create();
/* 5062:     */       }
/* 5063:5565 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5064:5566 */       if (localEJBContext.debug) {
/* 5065:5568 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecTransfersBySrvrTId"));
/* 5066:     */       }
/* 5067:5570 */       RecTransferInfo[] arrayOfRecTransferInfo2 = localBPWServicesBean
/* 5068:5571 */         .getRecTransfersBySrvrTId(
/* 5069:5572 */         StringSeqHelper.clone(paramArrayOfString));
/* 5070:     */       
/* 5071:5574 */       arrayOfRecTransferInfo2 = RecTransferInfoSeqHelper.clone(arrayOfRecTransferInfo2);
/* 5072:5575 */       localEJBContext.returnToPool();
/* 5073:5576 */       return arrayOfRecTransferInfo2;
/* 5074:     */     }
/* 5075:     */     catch (Exception localException)
/* 5076:     */     {
/* 5077:5580 */       if (localEJBContext != null) {
/* 5078:5582 */         localEJBContext.throwRemote(localException);
/* 5079:     */       }
/* 5080:5584 */       throw new EJBException(localException);
/* 5081:     */     }
/* 5082:     */     finally
/* 5083:     */     {
/* 5084:5588 */       this._comp.setCurrent(localJavaComponent);
/* 5085:     */     }
/* 5086:     */   }
/* 5087:     */   
/* 5088:     */   public BPWHist getRecTransfers(BPWHist paramBPWHist)
/* 5089:     */     throws RemoteException
/* 5090:     */   {
/* 5091:5596 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5092:5597 */     EJBContext localEJBContext = null;
/* 5093:     */     try
/* 5094:     */     {
/* 5095:5600 */       localEJBContext = this._comp.getPooledInstance();
/* 5096:5601 */       if (localEJBContext == null) {
/* 5097:5603 */         localEJBContext = _create();
/* 5098:     */       }
/* 5099:5605 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5100:5606 */       if (localEJBContext.debug) {
/* 5101:5608 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecTransfers"));
/* 5102:     */       }
/* 5103:5610 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 5104:5611 */         .getRecTransfers(
/* 5105:5612 */         BPWHistHelper.clone(paramBPWHist));
/* 5106:     */       
/* 5107:5614 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 5108:5615 */       localEJBContext.returnToPool();
/* 5109:5616 */       return localBPWHist2;
/* 5110:     */     }
/* 5111:     */     catch (Exception localException)
/* 5112:     */     {
/* 5113:5620 */       if (localEJBContext != null) {
/* 5114:5622 */         localEJBContext.throwRemote(localException);
/* 5115:     */       }
/* 5116:5624 */       throw new EJBException(localException);
/* 5117:     */     }
/* 5118:     */     finally
/* 5119:     */     {
/* 5120:5628 */       this._comp.setCurrent(localJavaComponent);
/* 5121:     */     }
/* 5122:     */   }
/* 5123:     */   
/* 5124:     */   public RecTransferInfo[] getRecTransfersByTrackingId(String[] paramArrayOfString)
/* 5125:     */     throws RemoteException
/* 5126:     */   {
/* 5127:5636 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5128:5637 */     EJBContext localEJBContext = null;
/* 5129:     */     try
/* 5130:     */     {
/* 5131:5640 */       localEJBContext = this._comp.getPooledInstance();
/* 5132:5641 */       if (localEJBContext == null) {
/* 5133:5643 */         localEJBContext = _create();
/* 5134:     */       }
/* 5135:5645 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5136:5646 */       if (localEJBContext.debug) {
/* 5137:5648 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecTransfersByTrackingId"));
/* 5138:     */       }
/* 5139:5650 */       RecTransferInfo[] arrayOfRecTransferInfo2 = localBPWServicesBean
/* 5140:5651 */         .getRecTransfersByTrackingId(
/* 5141:5652 */         StringSeqHelper.clone(paramArrayOfString));
/* 5142:     */       
/* 5143:5654 */       arrayOfRecTransferInfo2 = RecTransferInfoSeqHelper.clone(arrayOfRecTransferInfo2);
/* 5144:5655 */       localEJBContext.returnToPool();
/* 5145:5656 */       return arrayOfRecTransferInfo2;
/* 5146:     */     }
/* 5147:     */     catch (Exception localException)
/* 5148:     */     {
/* 5149:5660 */       if (localEJBContext != null) {
/* 5150:5662 */         localEJBContext.throwRemote(localException);
/* 5151:     */       }
/* 5152:5664 */       throw new EJBException(localException);
/* 5153:     */     }
/* 5154:     */     finally
/* 5155:     */     {
/* 5156:5668 */       this._comp.setCurrent(localJavaComponent);
/* 5157:     */     }
/* 5158:     */   }
/* 5159:     */   
/* 5160:     */   public BPWHist getRecTransferHistory(BPWHist paramBPWHist)
/* 5161:     */     throws RemoteException
/* 5162:     */   {
/* 5163:5676 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5164:5677 */     EJBContext localEJBContext = null;
/* 5165:     */     try
/* 5166:     */     {
/* 5167:5680 */       localEJBContext = this._comp.getPooledInstance();
/* 5168:5681 */       if (localEJBContext == null) {
/* 5169:5683 */         localEJBContext = _create();
/* 5170:     */       }
/* 5171:5685 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5172:5686 */       if (localEJBContext.debug) {
/* 5173:5688 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecTransferHistory"));
/* 5174:     */       }
/* 5175:5690 */       BPWHist localBPWHist2 = localBPWServicesBean
/* 5176:5691 */         .getRecTransferHistory(
/* 5177:5692 */         BPWHistHelper.clone(paramBPWHist));
/* 5178:     */       
/* 5179:5694 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 5180:5695 */       localEJBContext.returnToPool();
/* 5181:5696 */       return localBPWHist2;
/* 5182:     */     }
/* 5183:     */     catch (Exception localException)
/* 5184:     */     {
/* 5185:5700 */       if (localEJBContext != null) {
/* 5186:5702 */         localEJBContext.throwRemote(localException);
/* 5187:     */       }
/* 5188:5704 */       throw new EJBException(localException);
/* 5189:     */     }
/* 5190:     */     finally
/* 5191:     */     {
/* 5192:5708 */       this._comp.setCurrent(localJavaComponent);
/* 5193:     */     }
/* 5194:     */   }
/* 5195:     */   
/* 5196:     */   public ExtTransferCompanyInfo addExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
/* 5197:     */     throws RemoteException
/* 5198:     */   {
/* 5199:5716 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5200:5717 */     EJBContext localEJBContext = null;
/* 5201:     */     try
/* 5202:     */     {
/* 5203:5720 */       localEJBContext = this._comp.getPooledInstance();
/* 5204:5721 */       if (localEJBContext == null) {
/* 5205:5723 */         localEJBContext = _create();
/* 5206:     */       }
/* 5207:5725 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5208:5726 */       if (localEJBContext.debug) {
/* 5209:5728 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addExtTransferCompany"));
/* 5210:     */       }
/* 5211:5730 */       ExtTransferCompanyInfo localExtTransferCompanyInfo2 = localBPWServicesBean
/* 5212:5731 */         .addExtTransferCompany(
/* 5213:5732 */         (ExtTransferCompanyInfo)EJBContext.clone(paramExtTransferCompanyInfo));
/* 5214:     */       
/* 5215:5734 */       localExtTransferCompanyInfo2 = (ExtTransferCompanyInfo)EJBContext.clone(localExtTransferCompanyInfo2);
/* 5216:5735 */       localEJBContext.returnToPool();
/* 5217:5736 */       return localExtTransferCompanyInfo2;
/* 5218:     */     }
/* 5219:     */     catch (Exception localException)
/* 5220:     */     {
/* 5221:5740 */       if (localEJBContext != null) {
/* 5222:5742 */         localEJBContext.throwRemote(localException);
/* 5223:     */       }
/* 5224:5744 */       throw new EJBException(localException);
/* 5225:     */     }
/* 5226:     */     finally
/* 5227:     */     {
/* 5228:5748 */       this._comp.setCurrent(localJavaComponent);
/* 5229:     */     }
/* 5230:     */   }
/* 5231:     */   
/* 5232:     */   public ExtTransferCompanyInfo canExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
/* 5233:     */     throws RemoteException
/* 5234:     */   {
/* 5235:5756 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5236:5757 */     EJBContext localEJBContext = null;
/* 5237:     */     try
/* 5238:     */     {
/* 5239:5760 */       localEJBContext = this._comp.getPooledInstance();
/* 5240:5761 */       if (localEJBContext == null) {
/* 5241:5763 */         localEJBContext = _create();
/* 5242:     */       }
/* 5243:5765 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5244:5766 */       if (localEJBContext.debug) {
/* 5245:5768 */         localEJBContext.logger.debug(localEJBContext.debugMsg("canExtTransferCompany"));
/* 5246:     */       }
/* 5247:5770 */       ExtTransferCompanyInfo localExtTransferCompanyInfo2 = localBPWServicesBean
/* 5248:5771 */         .canExtTransferCompany(
/* 5249:5772 */         (ExtTransferCompanyInfo)EJBContext.clone(paramExtTransferCompanyInfo));
/* 5250:     */       
/* 5251:5774 */       localExtTransferCompanyInfo2 = (ExtTransferCompanyInfo)EJBContext.clone(localExtTransferCompanyInfo2);
/* 5252:5775 */       localEJBContext.returnToPool();
/* 5253:5776 */       return localExtTransferCompanyInfo2;
/* 5254:     */     }
/* 5255:     */     catch (Exception localException)
/* 5256:     */     {
/* 5257:5780 */       if (localEJBContext != null) {
/* 5258:5782 */         localEJBContext.throwRemote(localException);
/* 5259:     */       }
/* 5260:5784 */       throw new EJBException(localException);
/* 5261:     */     }
/* 5262:     */     finally
/* 5263:     */     {
/* 5264:5788 */       this._comp.setCurrent(localJavaComponent);
/* 5265:     */     }
/* 5266:     */   }
/* 5267:     */   
/* 5268:     */   public ExtTransferCompanyInfo modExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
/* 5269:     */     throws RemoteException
/* 5270:     */   {
/* 5271:5796 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5272:5797 */     EJBContext localEJBContext = null;
/* 5273:     */     try
/* 5274:     */     {
/* 5275:5800 */       localEJBContext = this._comp.getPooledInstance();
/* 5276:5801 */       if (localEJBContext == null) {
/* 5277:5803 */         localEJBContext = _create();
/* 5278:     */       }
/* 5279:5805 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5280:5806 */       if (localEJBContext.debug) {
/* 5281:5808 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modExtTransferCompany"));
/* 5282:     */       }
/* 5283:5810 */       ExtTransferCompanyInfo localExtTransferCompanyInfo2 = localBPWServicesBean
/* 5284:5811 */         .modExtTransferCompany(
/* 5285:5812 */         (ExtTransferCompanyInfo)EJBContext.clone(paramExtTransferCompanyInfo));
/* 5286:     */       
/* 5287:5814 */       localExtTransferCompanyInfo2 = (ExtTransferCompanyInfo)EJBContext.clone(localExtTransferCompanyInfo2);
/* 5288:5815 */       localEJBContext.returnToPool();
/* 5289:5816 */       return localExtTransferCompanyInfo2;
/* 5290:     */     }
/* 5291:     */     catch (Exception localException)
/* 5292:     */     {
/* 5293:5820 */       if (localEJBContext != null) {
/* 5294:5822 */         localEJBContext.throwRemote(localException);
/* 5295:     */       }
/* 5296:5824 */       throw new EJBException(localException);
/* 5297:     */     }
/* 5298:     */     finally
/* 5299:     */     {
/* 5300:5828 */       this._comp.setCurrent(localJavaComponent);
/* 5301:     */     }
/* 5302:     */   }
/* 5303:     */   
/* 5304:     */   public ExtTransferCompanyInfo getExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
/* 5305:     */     throws RemoteException
/* 5306:     */   {
/* 5307:5836 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5308:5837 */     EJBContext localEJBContext = null;
/* 5309:     */     try
/* 5310:     */     {
/* 5311:5840 */       localEJBContext = this._comp.getPooledInstance();
/* 5312:5841 */       if (localEJBContext == null) {
/* 5313:5843 */         localEJBContext = _create();
/* 5314:     */       }
/* 5315:5845 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5316:5846 */       if (localEJBContext.debug) {
/* 5317:5848 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getExtTransferCompany"));
/* 5318:     */       }
/* 5319:5850 */       ExtTransferCompanyInfo localExtTransferCompanyInfo2 = localBPWServicesBean
/* 5320:5851 */         .getExtTransferCompany(
/* 5321:5852 */         (ExtTransferCompanyInfo)EJBContext.clone(paramExtTransferCompanyInfo));
/* 5322:     */       
/* 5323:5854 */       localExtTransferCompanyInfo2 = (ExtTransferCompanyInfo)EJBContext.clone(localExtTransferCompanyInfo2);
/* 5324:5855 */       localEJBContext.returnToPool();
/* 5325:5856 */       return localExtTransferCompanyInfo2;
/* 5326:     */     }
/* 5327:     */     catch (Exception localException)
/* 5328:     */     {
/* 5329:5860 */       if (localEJBContext != null) {
/* 5330:5862 */         localEJBContext.throwRemote(localException);
/* 5331:     */       }
/* 5332:5864 */       throw new EJBException(localException);
/* 5333:     */     }
/* 5334:     */     finally
/* 5335:     */     {
/* 5336:5868 */       this._comp.setCurrent(localJavaComponent);
/* 5337:     */     }
/* 5338:     */   }
/* 5339:     */   
/* 5340:     */   public ExtTransferCompanyList getExtTransferCompanyList(ExtTransferCompanyList paramExtTransferCompanyList)
/* 5341:     */     throws RemoteException
/* 5342:     */   {
/* 5343:5876 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5344:5877 */     EJBContext localEJBContext = null;
/* 5345:     */     try
/* 5346:     */     {
/* 5347:5880 */       localEJBContext = this._comp.getPooledInstance();
/* 5348:5881 */       if (localEJBContext == null) {
/* 5349:5883 */         localEJBContext = _create();
/* 5350:     */       }
/* 5351:5885 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5352:5886 */       if (localEJBContext.debug) {
/* 5353:5888 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getExtTransferCompanyList"));
/* 5354:     */       }
/* 5355:5890 */       ExtTransferCompanyList localExtTransferCompanyList2 = localBPWServicesBean
/* 5356:5891 */         .getExtTransferCompanyList(
/* 5357:5892 */         (ExtTransferCompanyList)EJBContext.clone(paramExtTransferCompanyList));
/* 5358:     */       
/* 5359:5894 */       localExtTransferCompanyList2 = (ExtTransferCompanyList)EJBContext.clone(localExtTransferCompanyList2);
/* 5360:5895 */       localEJBContext.returnToPool();
/* 5361:5896 */       return localExtTransferCompanyList2;
/* 5362:     */     }
/* 5363:     */     catch (Exception localException)
/* 5364:     */     {
/* 5365:5900 */       if (localEJBContext != null) {
/* 5366:5902 */         localEJBContext.throwRemote(localException);
/* 5367:     */       }
/* 5368:5904 */       throw new EJBException(localException);
/* 5369:     */     }
/* 5370:     */     finally
/* 5371:     */     {
/* 5372:5908 */       this._comp.setCurrent(localJavaComponent);
/* 5373:     */     }
/* 5374:     */   }
/* 5375:     */   
/* 5376:     */   public ExtTransferAcctInfo addExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 5377:     */     throws RemoteException
/* 5378:     */   {
/* 5379:5916 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5380:5917 */     EJBContext localEJBContext = null;
/* 5381:     */     try
/* 5382:     */     {
/* 5383:5920 */       localEJBContext = this._comp.getPooledInstance();
/* 5384:5921 */       if (localEJBContext == null) {
/* 5385:5923 */         localEJBContext = _create();
/* 5386:     */       }
/* 5387:5925 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5388:5926 */       if (localEJBContext.debug) {
/* 5389:5928 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addExtTransferAccount"));
/* 5390:     */       }
/* 5391:5930 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 5392:5931 */         .addExtTransferAccount(
/* 5393:5932 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo));
/* 5394:     */       
/* 5395:5934 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 5396:5935 */       localEJBContext.returnToPool();
/* 5397:5936 */       return localExtTransferAcctInfo2;
/* 5398:     */     }
/* 5399:     */     catch (Exception localException)
/* 5400:     */     {
/* 5401:5940 */       if (localEJBContext != null) {
/* 5402:5942 */         localEJBContext.throwRemote(localException);
/* 5403:     */       }
/* 5404:5944 */       throw new EJBException(localException);
/* 5405:     */     }
/* 5406:     */     finally
/* 5407:     */     {
/* 5408:5948 */       this._comp.setCurrent(localJavaComponent);
/* 5409:     */     }
/* 5410:     */   }
/* 5411:     */   
/* 5412:     */   public ExtTransferAcctInfo canExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 5413:     */     throws RemoteException
/* 5414:     */   {
/* 5415:5956 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5416:5957 */     EJBContext localEJBContext = null;
/* 5417:     */     try
/* 5418:     */     {
/* 5419:5960 */       localEJBContext = this._comp.getPooledInstance();
/* 5420:5961 */       if (localEJBContext == null) {
/* 5421:5963 */         localEJBContext = _create();
/* 5422:     */       }
/* 5423:5965 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5424:5966 */       if (localEJBContext.debug) {
/* 5425:5968 */         localEJBContext.logger.debug(localEJBContext.debugMsg("canExtTransferAccount"));
/* 5426:     */       }
/* 5427:5970 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 5428:5971 */         .canExtTransferAccount(
/* 5429:5972 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo));
/* 5430:     */       
/* 5431:5974 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 5432:5975 */       localEJBContext.returnToPool();
/* 5433:5976 */       return localExtTransferAcctInfo2;
/* 5434:     */     }
/* 5435:     */     catch (Exception localException)
/* 5436:     */     {
/* 5437:5980 */       if (localEJBContext != null) {
/* 5438:5982 */         localEJBContext.throwRemote(localException);
/* 5439:     */       }
/* 5440:5984 */       throw new EJBException(localException);
/* 5441:     */     }
/* 5442:     */     finally
/* 5443:     */     {
/* 5444:5988 */       this._comp.setCurrent(localJavaComponent);
/* 5445:     */     }
/* 5446:     */   }
/* 5447:     */   
/* 5448:     */   public ExtTransferAcctInfo modExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 5449:     */     throws RemoteException
/* 5450:     */   {
/* 5451:5996 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5452:5997 */     EJBContext localEJBContext = null;
/* 5453:     */     try
/* 5454:     */     {
/* 5455:6000 */       localEJBContext = this._comp.getPooledInstance();
/* 5456:6001 */       if (localEJBContext == null) {
/* 5457:6003 */         localEJBContext = _create();
/* 5458:     */       }
/* 5459:6005 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5460:6006 */       if (localEJBContext.debug) {
/* 5461:6008 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modExtTransferAccount"));
/* 5462:     */       }
/* 5463:6010 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 5464:6011 */         .modExtTransferAccount(
/* 5465:6012 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo));
/* 5466:     */       
/* 5467:6014 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 5468:6015 */       localEJBContext.returnToPool();
/* 5469:6016 */       return localExtTransferAcctInfo2;
/* 5470:     */     }
/* 5471:     */     catch (Exception localException)
/* 5472:     */     {
/* 5473:6020 */       if (localEJBContext != null) {
/* 5474:6022 */         localEJBContext.throwRemote(localException);
/* 5475:     */       }
/* 5476:6024 */       throw new EJBException(localException);
/* 5477:     */     }
/* 5478:     */     finally
/* 5479:     */     {
/* 5480:6028 */       this._comp.setCurrent(localJavaComponent);
/* 5481:     */     }
/* 5482:     */   }
/* 5483:     */   
/* 5484:     */   public ExtTransferAcctInfo getExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 5485:     */     throws RemoteException
/* 5486:     */   {
/* 5487:6036 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5488:6037 */     EJBContext localEJBContext = null;
/* 5489:     */     try
/* 5490:     */     {
/* 5491:6040 */       localEJBContext = this._comp.getPooledInstance();
/* 5492:6041 */       if (localEJBContext == null) {
/* 5493:6043 */         localEJBContext = _create();
/* 5494:     */       }
/* 5495:6045 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5496:6046 */       if (localEJBContext.debug) {
/* 5497:6048 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getExtTransferAccount"));
/* 5498:     */       }
/* 5499:6050 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 5500:6051 */         .getExtTransferAccount(
/* 5501:6052 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo));
/* 5502:     */       
/* 5503:6054 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 5504:6055 */       localEJBContext.returnToPool();
/* 5505:6056 */       return localExtTransferAcctInfo2;
/* 5506:     */     }
/* 5507:     */     catch (Exception localException)
/* 5508:     */     {
/* 5509:6060 */       if (localEJBContext != null) {
/* 5510:6062 */         localEJBContext.throwRemote(localException);
/* 5511:     */       }
/* 5512:6064 */       throw new EJBException(localException);
/* 5513:     */     }
/* 5514:     */     finally
/* 5515:     */     {
/* 5516:6068 */       this._comp.setCurrent(localJavaComponent);
/* 5517:     */     }
/* 5518:     */   }
/* 5519:     */   
/* 5520:     */   public ExtTransferAcctInfo verifyExtTransferAccount(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo, int[] paramArrayOfInt)
/* 5521:     */     throws RemoteException, FFSException
/* 5522:     */   {
/* 5523:6078 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5524:6079 */     EJBContext localEJBContext = null;
/* 5525:     */     try
/* 5526:     */     {
/* 5527:6082 */       localEJBContext = this._comp.getPooledInstance();
/* 5528:6083 */       if (localEJBContext == null) {
/* 5529:6085 */         localEJBContext = _create();
/* 5530:     */       }
/* 5531:6087 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5532:6088 */       if (localEJBContext.debug) {
/* 5533:6090 */         localEJBContext.logger.debug(localEJBContext.debugMsg("verifyExtTransferAccount"));
/* 5534:     */       }
/* 5535:6092 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 5536:6093 */         .verifyExtTransferAccount(
/* 5537:6094 */         paramString, 
/* 5538:6095 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo), 
/* 5539:6096 */         longSeqHelper.clone(paramArrayOfInt));
/* 5540:     */       
/* 5541:6098 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 5542:6099 */       localEJBContext.returnToPool();
/* 5543:6100 */       return localExtTransferAcctInfo2;
/* 5544:     */     }
/* 5545:     */     catch (Exception localException)
/* 5546:     */     {
/* 5547:6104 */       if (localEJBContext != null)
/* 5548:     */       {
/* 5549:6106 */         if ((localException instanceof FFSException)) {
/* 5550:6108 */           throw ((FFSException)EJBContext.clone(localException));
/* 5551:     */         }
/* 5552:6110 */         localEJBContext.throwRemote(localException);
/* 5553:     */       }
/* 5554:6112 */       throw new EJBException(localException);
/* 5555:     */     }
/* 5556:     */     finally
/* 5557:     */     {
/* 5558:6116 */       this._comp.setCurrent(localJavaComponent);
/* 5559:     */     }
/* 5560:     */   }
/* 5561:     */   
/* 5562:     */   public ExtTransferAcctInfo depositAmountsForVerify(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 5563:     */     throws RemoteException, FFSException
/* 5564:     */   {
/* 5565:6125 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5566:6126 */     EJBContext localEJBContext = null;
/* 5567:     */     try
/* 5568:     */     {
/* 5569:6129 */       localEJBContext = this._comp.getPooledInstance();
/* 5570:6130 */       if (localEJBContext == null) {
/* 5571:6132 */         localEJBContext = _create();
/* 5572:     */       }
/* 5573:6134 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5574:6135 */       if (localEJBContext.debug) {
/* 5575:6137 */         localEJBContext.logger.debug(localEJBContext.debugMsg("depositAmountsForVerify"));
/* 5576:     */       }
/* 5577:6139 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 5578:6140 */         .depositAmountsForVerify(
/* 5579:6141 */         paramString, 
/* 5580:6142 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo));
/* 5581:     */       
/* 5582:6144 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 5583:6145 */       localEJBContext.returnToPool();
/* 5584:6146 */       return localExtTransferAcctInfo2;
/* 5585:     */     }
/* 5586:     */     catch (Exception localException)
/* 5587:     */     {
/* 5588:6150 */       if (localEJBContext != null)
/* 5589:     */       {
/* 5590:6152 */         if ((localException instanceof FFSException)) {
/* 5591:6154 */           throw ((FFSException)EJBContext.clone(localException));
/* 5592:     */         }
/* 5593:6156 */         localEJBContext.throwRemote(localException);
/* 5594:     */       }
/* 5595:6158 */       throw new EJBException(localException);
/* 5596:     */     }
/* 5597:     */     finally
/* 5598:     */     {
/* 5599:6162 */       this._comp.setCurrent(localJavaComponent);
/* 5600:     */     }
/* 5601:     */   }
/* 5602:     */   
/* 5603:     */   public ExtTransferAcctInfo activateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 5604:     */     throws RemoteException, FFSException
/* 5605:     */   {
/* 5606:6170 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5607:6171 */     EJBContext localEJBContext = null;
/* 5608:     */     try
/* 5609:     */     {
/* 5610:6174 */       localEJBContext = this._comp.getPooledInstance();
/* 5611:6175 */       if (localEJBContext == null) {
/* 5612:6177 */         localEJBContext = _create();
/* 5613:     */       }
/* 5614:6179 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5615:6180 */       if (localEJBContext.debug) {
/* 5616:6182 */         localEJBContext.logger.debug(localEJBContext.debugMsg("activateExtTransferAcct"));
/* 5617:     */       }
/* 5618:6184 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 5619:6185 */         .activateExtTransferAcct(
/* 5620:6186 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo));
/* 5621:     */       
/* 5622:6188 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 5623:6189 */       localEJBContext.returnToPool();
/* 5624:6190 */       return localExtTransferAcctInfo2;
/* 5625:     */     }
/* 5626:     */     catch (Exception localException)
/* 5627:     */     {
/* 5628:6194 */       if (localEJBContext != null)
/* 5629:     */       {
/* 5630:6196 */         if ((localException instanceof FFSException)) {
/* 5631:6198 */           throw ((FFSException)EJBContext.clone(localException));
/* 5632:     */         }
/* 5633:6200 */         localEJBContext.throwRemote(localException);
/* 5634:     */       }
/* 5635:6202 */       throw new EJBException(localException);
/* 5636:     */     }
/* 5637:     */     finally
/* 5638:     */     {
/* 5639:6206 */       this._comp.setCurrent(localJavaComponent);
/* 5640:     */     }
/* 5641:     */   }
/* 5642:     */   
/* 5643:     */   public ExtTransferAcctInfo inactivateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 5644:     */     throws RemoteException, FFSException
/* 5645:     */   {
/* 5646:6214 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5647:6215 */     EJBContext localEJBContext = null;
/* 5648:     */     try
/* 5649:     */     {
/* 5650:6218 */       localEJBContext = this._comp.getPooledInstance();
/* 5651:6219 */       if (localEJBContext == null) {
/* 5652:6221 */         localEJBContext = _create();
/* 5653:     */       }
/* 5654:6223 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5655:6224 */       if (localEJBContext.debug) {
/* 5656:6226 */         localEJBContext.logger.debug(localEJBContext.debugMsg("inactivateExtTransferAcct"));
/* 5657:     */       }
/* 5658:6228 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 5659:6229 */         .inactivateExtTransferAcct(
/* 5660:6230 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo));
/* 5661:     */       
/* 5662:6232 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 5663:6233 */       localEJBContext.returnToPool();
/* 5664:6234 */       return localExtTransferAcctInfo2;
/* 5665:     */     }
/* 5666:     */     catch (Exception localException)
/* 5667:     */     {
/* 5668:6238 */       if (localEJBContext != null)
/* 5669:     */       {
/* 5670:6240 */         if ((localException instanceof FFSException)) {
/* 5671:6242 */           throw ((FFSException)EJBContext.clone(localException));
/* 5672:     */         }
/* 5673:6244 */         localEJBContext.throwRemote(localException);
/* 5674:     */       }
/* 5675:6246 */       throw new EJBException(localException);
/* 5676:     */     }
/* 5677:     */     finally
/* 5678:     */     {
/* 5679:6250 */       this._comp.setCurrent(localJavaComponent);
/* 5680:     */     }
/* 5681:     */   }
/* 5682:     */   
/* 5683:     */   public ExtTransferAcctList getExtTransferAcctList(ExtTransferAcctList paramExtTransferAcctList)
/* 5684:     */     throws RemoteException
/* 5685:     */   {
/* 5686:6258 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5687:6259 */     EJBContext localEJBContext = null;
/* 5688:     */     try
/* 5689:     */     {
/* 5690:6262 */       localEJBContext = this._comp.getPooledInstance();
/* 5691:6263 */       if (localEJBContext == null) {
/* 5692:6265 */         localEJBContext = _create();
/* 5693:     */       }
/* 5694:6267 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5695:6268 */       if (localEJBContext.debug) {
/* 5696:6270 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getExtTransferAcctList"));
/* 5697:     */       }
/* 5698:6272 */       ExtTransferAcctList localExtTransferAcctList2 = localBPWServicesBean
/* 5699:6273 */         .getExtTransferAcctList(
/* 5700:6274 */         (ExtTransferAcctList)EJBContext.clone(paramExtTransferAcctList));
/* 5701:     */       
/* 5702:6276 */       localExtTransferAcctList2 = (ExtTransferAcctList)EJBContext.clone(localExtTransferAcctList2);
/* 5703:6277 */       localEJBContext.returnToPool();
/* 5704:6278 */       return localExtTransferAcctList2;
/* 5705:     */     }
/* 5706:     */     catch (Exception localException)
/* 5707:     */     {
/* 5708:6282 */       if (localEJBContext != null) {
/* 5709:6284 */         localEJBContext.throwRemote(localException);
/* 5710:     */       }
/* 5711:6286 */       throw new EJBException(localException);
/* 5712:     */     }
/* 5713:     */     finally
/* 5714:     */     {
/* 5715:6290 */       this._comp.setCurrent(localJavaComponent);
/* 5716:     */     }
/* 5717:     */   }
/* 5718:     */   
/* 5719:     */   public NonBusinessDay[] getNonBusinessDays(String paramString)
/* 5720:     */     throws RemoteException, FFSException
/* 5721:     */   {
/* 5722:6298 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5723:6299 */     EJBContext localEJBContext = null;
/* 5724:     */     try
/* 5725:     */     {
/* 5726:6302 */       localEJBContext = this._comp.getPooledInstance();
/* 5727:6303 */       if (localEJBContext == null) {
/* 5728:6305 */         localEJBContext = _create();
/* 5729:     */       }
/* 5730:6307 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5731:6308 */       if (localEJBContext.debug) {
/* 5732:6310 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getNonBusinessDays"));
/* 5733:     */       }
/* 5734:6312 */       NonBusinessDay[] arrayOfNonBusinessDay2 = localBPWServicesBean
/* 5735:6313 */         .getNonBusinessDays(
/* 5736:6314 */         paramString);
/* 5737:     */       
/* 5738:6316 */       arrayOfNonBusinessDay2 = NonBusinessDaySeqHelper.clone(arrayOfNonBusinessDay2);
/* 5739:6317 */       localEJBContext.returnToPool();
/* 5740:6318 */       return arrayOfNonBusinessDay2;
/* 5741:     */     }
/* 5742:     */     catch (Exception localException)
/* 5743:     */     {
/* 5744:6322 */       if (localEJBContext != null)
/* 5745:     */       {
/* 5746:6324 */         if ((localException instanceof FFSException)) {
/* 5747:6326 */           throw ((FFSException)EJBContext.clone(localException));
/* 5748:     */         }
/* 5749:6328 */         localEJBContext.throwRemote(localException);
/* 5750:     */       }
/* 5751:6330 */       throw new EJBException(localException);
/* 5752:     */     }
/* 5753:     */     finally
/* 5754:     */     {
/* 5755:6334 */       this._comp.setCurrent(localJavaComponent);
/* 5756:     */     }
/* 5757:     */   }
/* 5758:     */   
/* 5759:     */   public NonBusinessDay[] getNonBusinessDaysFromFile(String paramString)
/* 5760:     */     throws RemoteException, FFSException
/* 5761:     */   {
/* 5762:6342 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5763:6343 */     EJBContext localEJBContext = null;
/* 5764:     */     try
/* 5765:     */     {
/* 5766:6346 */       localEJBContext = this._comp.getPooledInstance();
/* 5767:6347 */       if (localEJBContext == null) {
/* 5768:6349 */         localEJBContext = _create();
/* 5769:     */       }
/* 5770:6351 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5771:6352 */       if (localEJBContext.debug) {
/* 5772:6354 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getNonBusinessDaysFromFile"));
/* 5773:     */       }
/* 5774:6356 */       NonBusinessDay[] arrayOfNonBusinessDay2 = localBPWServicesBean
/* 5775:6357 */         .getNonBusinessDaysFromFile(
/* 5776:6358 */         paramString);
/* 5777:     */       
/* 5778:6360 */       arrayOfNonBusinessDay2 = NonBusinessDaySeqHelper.clone(arrayOfNonBusinessDay2);
/* 5779:6361 */       localEJBContext.returnToPool();
/* 5780:6362 */       return arrayOfNonBusinessDay2;
/* 5781:     */     }
/* 5782:     */     catch (Exception localException)
/* 5783:     */     {
/* 5784:6366 */       if (localEJBContext != null)
/* 5785:     */       {
/* 5786:6368 */         if ((localException instanceof FFSException)) {
/* 5787:6370 */           throw ((FFSException)EJBContext.clone(localException));
/* 5788:     */         }
/* 5789:6372 */         localEJBContext.throwRemote(localException);
/* 5790:     */       }
/* 5791:6374 */       throw new EJBException(localException);
/* 5792:     */     }
/* 5793:     */     finally
/* 5794:     */     {
/* 5795:6378 */       this._comp.setCurrent(localJavaComponent);
/* 5796:     */     }
/* 5797:     */   }
/* 5798:     */   
/* 5799:     */   public PagingInfo getPagedWire(PagingInfo paramPagingInfo)
/* 5800:     */     throws RemoteException, FFSException
/* 5801:     */   {
/* 5802:6386 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5803:6387 */     EJBContext localEJBContext = null;
/* 5804:     */     try
/* 5805:     */     {
/* 5806:6390 */       localEJBContext = this._comp.getPooledInstance();
/* 5807:6391 */       if (localEJBContext == null) {
/* 5808:6393 */         localEJBContext = _create();
/* 5809:     */       }
/* 5810:6395 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5811:6396 */       if (localEJBContext.debug) {
/* 5812:6398 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPagedWire"));
/* 5813:     */       }
/* 5814:6400 */       PagingInfo localPagingInfo2 = localBPWServicesBean
/* 5815:6401 */         .getPagedWire(
/* 5816:6402 */         (PagingInfo)EJBContext.clone(paramPagingInfo));
/* 5817:     */       
/* 5818:6404 */       localPagingInfo2 = (PagingInfo)EJBContext.clone(localPagingInfo2);
/* 5819:6405 */       localEJBContext.returnToPool();
/* 5820:6406 */       return localPagingInfo2;
/* 5821:     */     }
/* 5822:     */     catch (Exception localException)
/* 5823:     */     {
/* 5824:6410 */       if (localEJBContext != null)
/* 5825:     */       {
/* 5826:6412 */         if ((localException instanceof FFSException)) {
/* 5827:6414 */           throw ((FFSException)EJBContext.clone(localException));
/* 5828:     */         }
/* 5829:6416 */         localEJBContext.throwRemote(localException);
/* 5830:     */       }
/* 5831:6418 */       throw new EJBException(localException);
/* 5832:     */     }
/* 5833:     */     finally
/* 5834:     */     {
/* 5835:6422 */       this._comp.setCurrent(localJavaComponent);
/* 5836:     */     }
/* 5837:     */   }
/* 5838:     */   
/* 5839:     */   public PagingInfo getPagedTransfer(PagingInfo paramPagingInfo)
/* 5840:     */     throws RemoteException, FFSException
/* 5841:     */   {
/* 5842:6430 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5843:6431 */     EJBContext localEJBContext = null;
/* 5844:     */     try
/* 5845:     */     {
/* 5846:6434 */       localEJBContext = this._comp.getPooledInstance();
/* 5847:6435 */       if (localEJBContext == null) {
/* 5848:6437 */         localEJBContext = _create();
/* 5849:     */       }
/* 5850:6439 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5851:6440 */       if (localEJBContext.debug) {
/* 5852:6442 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPagedTransfer"));
/* 5853:     */       }
/* 5854:6444 */       PagingInfo localPagingInfo2 = localBPWServicesBean
/* 5855:6445 */         .getPagedTransfer(
/* 5856:6446 */         (PagingInfo)EJBContext.clone(paramPagingInfo));
/* 5857:     */       
/* 5858:6448 */       localPagingInfo2 = (PagingInfo)EJBContext.clone(localPagingInfo2);
/* 5859:6449 */       localEJBContext.returnToPool();
/* 5860:6450 */       return localPagingInfo2;
/* 5861:     */     }
/* 5862:     */     catch (Exception localException)
/* 5863:     */     {
/* 5864:6454 */       if (localEJBContext != null)
/* 5865:     */       {
/* 5866:6456 */         if ((localException instanceof FFSException)) {
/* 5867:6458 */           throw ((FFSException)EJBContext.clone(localException));
/* 5868:     */         }
/* 5869:6460 */         localEJBContext.throwRemote(localException);
/* 5870:     */       }
/* 5871:6462 */       throw new EJBException(localException);
/* 5872:     */     }
/* 5873:     */     finally
/* 5874:     */     {
/* 5875:6466 */       this._comp.setCurrent(localJavaComponent);
/* 5876:     */     }
/* 5877:     */   }
/* 5878:     */   
/* 5879:     */   public PagingInfo getPagedBillPayments(PagingInfo paramPagingInfo)
/* 5880:     */     throws RemoteException, FFSException
/* 5881:     */   {
/* 5882:6474 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5883:6475 */     EJBContext localEJBContext = null;
/* 5884:     */     try
/* 5885:     */     {
/* 5886:6478 */       localEJBContext = this._comp.getPooledInstance();
/* 5887:6479 */       if (localEJBContext == null) {
/* 5888:6481 */         localEJBContext = _create();
/* 5889:     */       }
/* 5890:6483 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5891:6484 */       if (localEJBContext.debug) {
/* 5892:6486 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPagedBillPayments"));
/* 5893:     */       }
/* 5894:6488 */       PagingInfo localPagingInfo2 = localBPWServicesBean
/* 5895:6489 */         .getPagedBillPayments(
/* 5896:6490 */         (PagingInfo)EJBContext.clone(paramPagingInfo));
/* 5897:     */       
/* 5898:6492 */       localPagingInfo2 = (PagingInfo)EJBContext.clone(localPagingInfo2);
/* 5899:6493 */       localEJBContext.returnToPool();
/* 5900:6494 */       return localPagingInfo2;
/* 5901:     */     }
/* 5902:     */     catch (Exception localException)
/* 5903:     */     {
/* 5904:6498 */       if (localEJBContext != null)
/* 5905:     */       {
/* 5906:6500 */         if ((localException instanceof FFSException)) {
/* 5907:6502 */           throw ((FFSException)EJBContext.clone(localException));
/* 5908:     */         }
/* 5909:6504 */         localEJBContext.throwRemote(localException);
/* 5910:     */       }
/* 5911:6506 */       throw new EJBException(localException);
/* 5912:     */     }
/* 5913:     */     finally
/* 5914:     */     {
/* 5915:6510 */       this._comp.setCurrent(localJavaComponent);
/* 5916:     */     }
/* 5917:     */   }
/* 5918:     */   
/* 5919:     */   public int getValidTransferDateDue(TransferInfo paramTransferInfo)
/* 5920:     */     throws RemoteException
/* 5921:     */   {
/* 5922:6518 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5923:6519 */     EJBContext localEJBContext = null;
/* 5924:     */     try
/* 5925:     */     {
/* 5926:6522 */       localEJBContext = this._comp.getPooledInstance();
/* 5927:6523 */       if (localEJBContext == null) {
/* 5928:6525 */         localEJBContext = _create();
/* 5929:     */       }
/* 5930:6527 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5931:6528 */       if (localEJBContext.debug) {
/* 5932:6530 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getValidTransferDateDue"));
/* 5933:     */       }
/* 5934:6532 */       int j = localBPWServicesBean
/* 5935:6533 */         .getValidTransferDateDue(
/* 5936:6534 */         (TransferInfo)EJBContext.clone(paramTransferInfo));
/* 5937:     */       
/* 5938:6536 */       localEJBContext.returnToPool();
/* 5939:6537 */       return j;
/* 5940:     */     }
/* 5941:     */     catch (Exception localException)
/* 5942:     */     {
/* 5943:6541 */       if (localEJBContext != null) {
/* 5944:6543 */         localEJBContext.throwRemote(localException);
/* 5945:     */       }
/* 5946:6545 */       throw new EJBException(localException);
/* 5947:     */     }
/* 5948:     */     finally
/* 5949:     */     {
/* 5950:6549 */       this._comp.setCurrent(localJavaComponent);
/* 5951:     */     }
/* 5952:     */   }
/* 5953:     */   
/* 5954:     */   public PagingInfo getPagedCashCon(PagingInfo paramPagingInfo)
/* 5955:     */     throws RemoteException, FFSException
/* 5956:     */   {
/* 5957:6557 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5958:6558 */     EJBContext localEJBContext = null;
/* 5959:     */     try
/* 5960:     */     {
/* 5961:6561 */       localEJBContext = this._comp.getPooledInstance();
/* 5962:6562 */       if (localEJBContext == null) {
/* 5963:6564 */         localEJBContext = _create();
/* 5964:     */       }
/* 5965:6566 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 5966:6567 */       if (localEJBContext.debug) {
/* 5967:6569 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPagedCashCon"));
/* 5968:     */       }
/* 5969:6571 */       PagingInfo localPagingInfo2 = localBPWServicesBean
/* 5970:6572 */         .getPagedCashCon(
/* 5971:6573 */         (PagingInfo)EJBContext.clone(paramPagingInfo));
/* 5972:     */       
/* 5973:6575 */       localPagingInfo2 = (PagingInfo)EJBContext.clone(localPagingInfo2);
/* 5974:6576 */       localEJBContext.returnToPool();
/* 5975:6577 */       return localPagingInfo2;
/* 5976:     */     }
/* 5977:     */     catch (Exception localException)
/* 5978:     */     {
/* 5979:6581 */       if (localEJBContext != null)
/* 5980:     */       {
/* 5981:6583 */         if ((localException instanceof FFSException)) {
/* 5982:6585 */           throw ((FFSException)EJBContext.clone(localException));
/* 5983:     */         }
/* 5984:6587 */         localEJBContext.throwRemote(localException);
/* 5985:     */       }
/* 5986:6589 */       throw new EJBException(localException);
/* 5987:     */     }
/* 5988:     */     finally
/* 5989:     */     {
/* 5990:6593 */       this._comp.setCurrent(localJavaComponent);
/* 5991:     */     }
/* 5992:     */   }
/* 5993:     */   
/* 5994:     */   public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
/* 5995:     */     throws RemoteException, FFSException
/* 5996:     */   {
/* 5997:6602 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 5998:6603 */     EJBContext localEJBContext = null;
/* 5999:     */     try
/* 6000:     */     {
/* 6001:6606 */       localEJBContext = this._comp.getPooledInstance();
/* 6002:6607 */       if (localEJBContext == null) {
/* 6003:6609 */         localEJBContext = _create();
/* 6004:     */       }
/* 6005:6611 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6006:6612 */       if (localEJBContext.debug) {
/* 6007:6614 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeByListId"));
/* 6008:     */       }
/* 6009:6616 */       PayeeInfo localPayeeInfo2 = localBPWServicesBean
/* 6010:6617 */         .getPayeeByListId(
/* 6011:6618 */         paramString1, 
/* 6012:6619 */         paramString2);
/* 6013:     */       
/* 6014:6621 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 6015:6622 */       localEJBContext.returnToPool();
/* 6016:6623 */       return localPayeeInfo2;
/* 6017:     */     }
/* 6018:     */     catch (Exception localException)
/* 6019:     */     {
/* 6020:6627 */       if (localEJBContext != null)
/* 6021:     */       {
/* 6022:6629 */         if ((localException instanceof FFSException)) {
/* 6023:6631 */           throw ((FFSException)EJBContext.clone(localException));
/* 6024:     */         }
/* 6025:6633 */         localEJBContext.throwRemote(localException);
/* 6026:     */       }
/* 6027:6635 */       throw new EJBException(localException);
/* 6028:     */     }
/* 6029:     */     finally
/* 6030:     */     {
/* 6031:6639 */       this._comp.setCurrent(localJavaComponent);
/* 6032:     */     }
/* 6033:     */   }
/* 6034:     */   
/* 6035:     */   public AccountTypesMap getAccountTypesMap()
/* 6036:     */     throws RemoteException, FFSException
/* 6037:     */   {
/* 6038:6646 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6039:6647 */     EJBContext localEJBContext = null;
/* 6040:     */     try
/* 6041:     */     {
/* 6042:6650 */       localEJBContext = this._comp.getPooledInstance();
/* 6043:6651 */       if (localEJBContext == null) {
/* 6044:6653 */         localEJBContext = _create();
/* 6045:     */       }
/* 6046:6655 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6047:6656 */       if (localEJBContext.debug) {
/* 6048:6658 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getAccountTypesMap"));
/* 6049:     */       }
/* 6050:6660 */       AccountTypesMap localAccountTypesMap2 = localBPWServicesBean
/* 6051:6661 */         .getAccountTypesMap();
/* 6052:     */       
/* 6053:6663 */       localAccountTypesMap2 = (AccountTypesMap)EJBContext.clone(localAccountTypesMap2);
/* 6054:6664 */       localEJBContext.returnToPool();
/* 6055:6665 */       return localAccountTypesMap2;
/* 6056:     */     }
/* 6057:     */     catch (Exception localException)
/* 6058:     */     {
/* 6059:6669 */       if (localEJBContext != null)
/* 6060:     */       {
/* 6061:6671 */         if ((localException instanceof FFSException)) {
/* 6062:6673 */           throw ((FFSException)EJBContext.clone(localException));
/* 6063:     */         }
/* 6064:6675 */         localEJBContext.throwRemote(localException);
/* 6065:     */       }
/* 6066:6677 */       throw new EJBException(localException);
/* 6067:     */     }
/* 6068:     */     finally
/* 6069:     */     {
/* 6070:6681 */       this._comp.setCurrent(localJavaComponent);
/* 6071:     */     }
/* 6072:     */   }
/* 6073:     */   
/* 6074:     */   public ExtTransferAcctInfo modExtTransferAccountPrenoteStatus(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 6075:     */     throws RemoteException
/* 6076:     */   {
/* 6077:6689 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6078:6690 */     EJBContext localEJBContext = null;
/* 6079:     */     try
/* 6080:     */     {
/* 6081:6693 */       localEJBContext = this._comp.getPooledInstance();
/* 6082:6694 */       if (localEJBContext == null) {
/* 6083:6696 */         localEJBContext = _create();
/* 6084:     */       }
/* 6085:6698 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6086:6699 */       if (localEJBContext.debug) {
/* 6087:6701 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modExtTransferAccountPrenoteStatus"));
/* 6088:     */       }
/* 6089:6703 */       ExtTransferAcctInfo localExtTransferAcctInfo2 = localBPWServicesBean
/* 6090:6704 */         .modExtTransferAccountPrenoteStatus(
/* 6091:6705 */         (ExtTransferAcctInfo)EJBContext.clone(paramExtTransferAcctInfo));
/* 6092:     */       
/* 6093:6707 */       localExtTransferAcctInfo2 = (ExtTransferAcctInfo)EJBContext.clone(localExtTransferAcctInfo2);
/* 6094:6708 */       localEJBContext.returnToPool();
/* 6095:6709 */       return localExtTransferAcctInfo2;
/* 6096:     */     }
/* 6097:     */     catch (Exception localException)
/* 6098:     */     {
/* 6099:6713 */       if (localEJBContext != null) {
/* 6100:6715 */         localEJBContext.throwRemote(localException);
/* 6101:     */       }
/* 6102:6717 */       throw new EJBException(localException);
/* 6103:     */     }
/* 6104:     */     finally
/* 6105:     */     {
/* 6106:6721 */       this._comp.setCurrent(localJavaComponent);
/* 6107:     */     }
/* 6108:     */   }
/* 6109:     */   
/* 6110:     */   public String getBPWProperty(String paramString1, String paramString2)
/* 6111:     */     throws RemoteException, FFSException
/* 6112:     */   {
/* 6113:6730 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6114:6731 */     EJBContext localEJBContext = null;
/* 6115:     */     try
/* 6116:     */     {
/* 6117:6734 */       localEJBContext = this._comp.getPooledInstance();
/* 6118:6735 */       if (localEJBContext == null) {
/* 6119:6737 */         localEJBContext = _create();
/* 6120:     */       }
/* 6121:6739 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6122:6740 */       if (localEJBContext.debug) {
/* 6123:6742 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBPWProperty"));
/* 6124:     */       }
/* 6125:6744 */       String str2 = localBPWServicesBean
/* 6126:6745 */         .getBPWProperty(
/* 6127:6746 */         paramString1, 
/* 6128:6747 */         paramString2);
/* 6129:     */       
/* 6130:6749 */       localEJBContext.returnToPool();
/* 6131:6750 */       return str2;
/* 6132:     */     }
/* 6133:     */     catch (Exception localException)
/* 6134:     */     {
/* 6135:6754 */       if (localEJBContext != null)
/* 6136:     */       {
/* 6137:6756 */         if ((localException instanceof FFSException)) {
/* 6138:6758 */           throw ((FFSException)EJBContext.clone(localException));
/* 6139:     */         }
/* 6140:6760 */         localEJBContext.throwRemote(localException);
/* 6141:     */       }
/* 6142:6762 */       throw new EJBException(localException);
/* 6143:     */     }
/* 6144:     */     finally
/* 6145:     */     {
/* 6146:6766 */       this._comp.setCurrent(localJavaComponent);
/* 6147:     */     }
/* 6148:     */   }
/* 6149:     */   
/* 6150:     */   public TransferBatchInfo addTransferBatch(TransferBatchInfo paramTransferBatchInfo)
/* 6151:     */     throws RemoteException, FFSException
/* 6152:     */   {
/* 6153:6774 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6154:6775 */     EJBContext localEJBContext = null;
/* 6155:     */     try
/* 6156:     */     {
/* 6157:6778 */       localEJBContext = this._comp.getPooledInstance();
/* 6158:6779 */       if (localEJBContext == null) {
/* 6159:6781 */         localEJBContext = _create();
/* 6160:     */       }
/* 6161:6783 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6162:6784 */       if (localEJBContext.debug) {
/* 6163:6786 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addTransferBatch"));
/* 6164:     */       }
/* 6165:6788 */       TransferBatchInfo localTransferBatchInfo2 = localBPWServicesBean
/* 6166:6789 */         .addTransferBatch(
/* 6167:6790 */         (TransferBatchInfo)EJBContext.clone(paramTransferBatchInfo));
/* 6168:     */       
/* 6169:6792 */       localTransferBatchInfo2 = (TransferBatchInfo)EJBContext.clone(localTransferBatchInfo2);
/* 6170:6793 */       localEJBContext.returnToPool();
/* 6171:6794 */       return localTransferBatchInfo2;
/* 6172:     */     }
/* 6173:     */     catch (Exception localException)
/* 6174:     */     {
/* 6175:6798 */       if (localEJBContext != null)
/* 6176:     */       {
/* 6177:6800 */         if ((localException instanceof FFSException)) {
/* 6178:6802 */           throw ((FFSException)EJBContext.clone(localException));
/* 6179:     */         }
/* 6180:6804 */         localEJBContext.throwRemote(localException);
/* 6181:     */       }
/* 6182:6806 */       throw new EJBException(localException);
/* 6183:     */     }
/* 6184:     */     finally
/* 6185:     */     {
/* 6186:6810 */       this._comp.setCurrent(localJavaComponent);
/* 6187:     */     }
/* 6188:     */   }
/* 6189:     */   
/* 6190:     */   public TransferBatchInfo modifyTransferBatch(TransferBatchInfo paramTransferBatchInfo)
/* 6191:     */     throws RemoteException, FFSException
/* 6192:     */   {
/* 6193:6818 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6194:6819 */     EJBContext localEJBContext = null;
/* 6195:     */     try
/* 6196:     */     {
/* 6197:6822 */       localEJBContext = this._comp.getPooledInstance();
/* 6198:6823 */       if (localEJBContext == null) {
/* 6199:6825 */         localEJBContext = _create();
/* 6200:     */       }
/* 6201:6827 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6202:6828 */       if (localEJBContext.debug) {
/* 6203:6830 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modifyTransferBatch"));
/* 6204:     */       }
/* 6205:6832 */       TransferBatchInfo localTransferBatchInfo2 = localBPWServicesBean
/* 6206:6833 */         .modifyTransferBatch(
/* 6207:6834 */         (TransferBatchInfo)EJBContext.clone(paramTransferBatchInfo));
/* 6208:     */       
/* 6209:6836 */       localTransferBatchInfo2 = (TransferBatchInfo)EJBContext.clone(localTransferBatchInfo2);
/* 6210:6837 */       localEJBContext.returnToPool();
/* 6211:6838 */       return localTransferBatchInfo2;
/* 6212:     */     }
/* 6213:     */     catch (Exception localException)
/* 6214:     */     {
/* 6215:6842 */       if (localEJBContext != null)
/* 6216:     */       {
/* 6217:6844 */         if ((localException instanceof FFSException)) {
/* 6218:6846 */           throw ((FFSException)EJBContext.clone(localException));
/* 6219:     */         }
/* 6220:6848 */         localEJBContext.throwRemote(localException);
/* 6221:     */       }
/* 6222:6850 */       throw new EJBException(localException);
/* 6223:     */     }
/* 6224:     */     finally
/* 6225:     */     {
/* 6226:6854 */       this._comp.setCurrent(localJavaComponent);
/* 6227:     */     }
/* 6228:     */   }
/* 6229:     */   
/* 6230:     */   public TransferBatchInfo cancelTransferBatch(TransferBatchInfo paramTransferBatchInfo)
/* 6231:     */     throws RemoteException, FFSException
/* 6232:     */   {
/* 6233:6862 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6234:6863 */     EJBContext localEJBContext = null;
/* 6235:     */     try
/* 6236:     */     {
/* 6237:6866 */       localEJBContext = this._comp.getPooledInstance();
/* 6238:6867 */       if (localEJBContext == null) {
/* 6239:6869 */         localEJBContext = _create();
/* 6240:     */       }
/* 6241:6871 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6242:6872 */       if (localEJBContext.debug) {
/* 6243:6874 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cancelTransferBatch"));
/* 6244:     */       }
/* 6245:6876 */       TransferBatchInfo localTransferBatchInfo2 = localBPWServicesBean
/* 6246:6877 */         .cancelTransferBatch(
/* 6247:6878 */         (TransferBatchInfo)EJBContext.clone(paramTransferBatchInfo));
/* 6248:     */       
/* 6249:6880 */       localTransferBatchInfo2 = (TransferBatchInfo)EJBContext.clone(localTransferBatchInfo2);
/* 6250:6881 */       localEJBContext.returnToPool();
/* 6251:6882 */       return localTransferBatchInfo2;
/* 6252:     */     }
/* 6253:     */     catch (Exception localException)
/* 6254:     */     {
/* 6255:6886 */       if (localEJBContext != null)
/* 6256:     */       {
/* 6257:6888 */         if ((localException instanceof FFSException)) {
/* 6258:6890 */           throw ((FFSException)EJBContext.clone(localException));
/* 6259:     */         }
/* 6260:6892 */         localEJBContext.throwRemote(localException);
/* 6261:     */       }
/* 6262:6894 */       throw new EJBException(localException);
/* 6263:     */     }
/* 6264:     */     finally
/* 6265:     */     {
/* 6266:6898 */       this._comp.setCurrent(localJavaComponent);
/* 6267:     */     }
/* 6268:     */   }
/* 6269:     */   
/* 6270:     */   public TransferBatchInfo getTransferBatchById(String paramString)
/* 6271:     */     throws RemoteException, FFSException
/* 6272:     */   {
/* 6273:6906 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6274:6907 */     EJBContext localEJBContext = null;
/* 6275:     */     try
/* 6276:     */     {
/* 6277:6910 */       localEJBContext = this._comp.getPooledInstance();
/* 6278:6911 */       if (localEJBContext == null) {
/* 6279:6913 */         localEJBContext = _create();
/* 6280:     */       }
/* 6281:6915 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6282:6916 */       if (localEJBContext.debug) {
/* 6283:6918 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTransferBatchById"));
/* 6284:     */       }
/* 6285:6920 */       TransferBatchInfo localTransferBatchInfo2 = localBPWServicesBean
/* 6286:6921 */         .getTransferBatchById(
/* 6287:6922 */         paramString);
/* 6288:     */       
/* 6289:6924 */       localTransferBatchInfo2 = (TransferBatchInfo)EJBContext.clone(localTransferBatchInfo2);
/* 6290:6925 */       localEJBContext.returnToPool();
/* 6291:6926 */       return localTransferBatchInfo2;
/* 6292:     */     }
/* 6293:     */     catch (Exception localException)
/* 6294:     */     {
/* 6295:6930 */       if (localEJBContext != null)
/* 6296:     */       {
/* 6297:6932 */         if ((localException instanceof FFSException)) {
/* 6298:6934 */           throw ((FFSException)EJBContext.clone(localException));
/* 6299:     */         }
/* 6300:6936 */         localEJBContext.throwRemote(localException);
/* 6301:     */       }
/* 6302:6938 */       throw new EJBException(localException);
/* 6303:     */     }
/* 6304:     */     finally
/* 6305:     */     {
/* 6306:6942 */       this._comp.setCurrent(localJavaComponent);
/* 6307:     */     }
/* 6308:     */   }
/* 6309:     */   
/* 6310:     */   public AccountTransactions accountHasPendingTransfers(AccountTransactions paramAccountTransactions)
/* 6311:     */     throws RemoteException, FFSException
/* 6312:     */   {
/* 6313:6950 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6314:6951 */     EJBContext localEJBContext = null;
/* 6315:     */     try
/* 6316:     */     {
/* 6317:6954 */       localEJBContext = this._comp.getPooledInstance();
/* 6318:6955 */       if (localEJBContext == null) {
/* 6319:6957 */         localEJBContext = _create();
/* 6320:     */       }
/* 6321:6959 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6322:6960 */       if (localEJBContext.debug) {
/* 6323:6962 */         localEJBContext.logger.debug(localEJBContext.debugMsg("accountHasPendingTransfers"));
/* 6324:     */       }
/* 6325:6964 */       AccountTransactions localAccountTransactions2 = localBPWServicesBean
/* 6326:6965 */         .accountHasPendingTransfers(
/* 6327:6966 */         (AccountTransactions)EJBContext.clone(paramAccountTransactions));
/* 6328:     */       
/* 6329:6968 */       localAccountTransactions2 = (AccountTransactions)EJBContext.clone(localAccountTransactions2);
/* 6330:6969 */       localEJBContext.returnToPool();
/* 6331:6970 */       return localAccountTransactions2;
/* 6332:     */     }
/* 6333:     */     catch (Exception localException)
/* 6334:     */     {
/* 6335:6974 */       if (localEJBContext != null)
/* 6336:     */       {
/* 6337:6976 */         if ((localException instanceof FFSException)) {
/* 6338:6978 */           throw ((FFSException)EJBContext.clone(localException));
/* 6339:     */         }
/* 6340:6980 */         localEJBContext.throwRemote(localException);
/* 6341:     */       }
/* 6342:6982 */       throw new EJBException(localException);
/* 6343:     */     }
/* 6344:     */     finally
/* 6345:     */     {
/* 6346:6986 */       this._comp.setCurrent(localJavaComponent);
/* 6347:     */     }
/* 6348:     */   }
/* 6349:     */   
/* 6350:     */   public PmtInfo addBillPayment(PmtInfo paramPmtInfo)
/* 6351:     */     throws RemoteException, FFSException
/* 6352:     */   {
/* 6353:6994 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6354:6995 */     EJBContext localEJBContext = null;
/* 6355:     */     try
/* 6356:     */     {
/* 6357:6998 */       localEJBContext = this._comp.getPooledInstance();
/* 6358:6999 */       if (localEJBContext == null) {
/* 6359:7001 */         localEJBContext = _create();
/* 6360:     */       }
/* 6361:7003 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6362:7004 */       if (localEJBContext.debug) {
/* 6363:7006 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addBillPayment"));
/* 6364:     */       }
/* 6365:7008 */       PmtInfo localPmtInfo2 = localBPWServicesBean
/* 6366:7009 */         .addBillPayment(
/* 6367:7010 */         (PmtInfo)EJBContext.clone(paramPmtInfo));
/* 6368:     */       
/* 6369:7012 */       localPmtInfo2 = (PmtInfo)EJBContext.clone(localPmtInfo2);
/* 6370:7013 */       localEJBContext.returnToPool();
/* 6371:7014 */       return localPmtInfo2;
/* 6372:     */     }
/* 6373:     */     catch (Exception localException)
/* 6374:     */     {
/* 6375:7018 */       if (localEJBContext != null)
/* 6376:     */       {
/* 6377:7020 */         if ((localException instanceof FFSException)) {
/* 6378:7022 */           throw ((FFSException)EJBContext.clone(localException));
/* 6379:     */         }
/* 6380:7024 */         localEJBContext.throwRemote(localException);
/* 6381:     */       }
/* 6382:7026 */       throw new EJBException(localException);
/* 6383:     */     }
/* 6384:     */     finally
/* 6385:     */     {
/* 6386:7030 */       this._comp.setCurrent(localJavaComponent);
/* 6387:     */     }
/* 6388:     */   }
/* 6389:     */   
/* 6390:     */   public PmtInfo modifyBillPayment(PmtInfo paramPmtInfo)
/* 6391:     */     throws RemoteException, FFSException
/* 6392:     */   {
/* 6393:7038 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6394:7039 */     EJBContext localEJBContext = null;
/* 6395:     */     try
/* 6396:     */     {
/* 6397:7042 */       localEJBContext = this._comp.getPooledInstance();
/* 6398:7043 */       if (localEJBContext == null) {
/* 6399:7045 */         localEJBContext = _create();
/* 6400:     */       }
/* 6401:7047 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6402:7048 */       if (localEJBContext.debug) {
/* 6403:7050 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modifyBillPayment"));
/* 6404:     */       }
/* 6405:7052 */       PmtInfo localPmtInfo2 = localBPWServicesBean
/* 6406:7053 */         .modifyBillPayment(
/* 6407:7054 */         (PmtInfo)EJBContext.clone(paramPmtInfo));
/* 6408:     */       
/* 6409:7056 */       localPmtInfo2 = (PmtInfo)EJBContext.clone(localPmtInfo2);
/* 6410:7057 */       localEJBContext.returnToPool();
/* 6411:7058 */       return localPmtInfo2;
/* 6412:     */     }
/* 6413:     */     catch (Exception localException)
/* 6414:     */     {
/* 6415:7062 */       if (localEJBContext != null)
/* 6416:     */       {
/* 6417:7064 */         if ((localException instanceof FFSException)) {
/* 6418:7066 */           throw ((FFSException)EJBContext.clone(localException));
/* 6419:     */         }
/* 6420:7068 */         localEJBContext.throwRemote(localException);
/* 6421:     */       }
/* 6422:7070 */       throw new EJBException(localException);
/* 6423:     */     }
/* 6424:     */     finally
/* 6425:     */     {
/* 6426:7074 */       this._comp.setCurrent(localJavaComponent);
/* 6427:     */     }
/* 6428:     */   }
/* 6429:     */   
/* 6430:     */   public PmtInfo deleteBillPayment(PmtInfo paramPmtInfo)
/* 6431:     */     throws RemoteException, FFSException
/* 6432:     */   {
/* 6433:7082 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6434:7083 */     EJBContext localEJBContext = null;
/* 6435:     */     try
/* 6436:     */     {
/* 6437:7086 */       localEJBContext = this._comp.getPooledInstance();
/* 6438:7087 */       if (localEJBContext == null) {
/* 6439:7089 */         localEJBContext = _create();
/* 6440:     */       }
/* 6441:7091 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6442:7092 */       if (localEJBContext.debug) {
/* 6443:7094 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteBillPayment"));
/* 6444:     */       }
/* 6445:7096 */       PmtInfo localPmtInfo2 = localBPWServicesBean
/* 6446:7097 */         .deleteBillPayment(
/* 6447:7098 */         (PmtInfo)EJBContext.clone(paramPmtInfo));
/* 6448:     */       
/* 6449:7100 */       localPmtInfo2 = (PmtInfo)EJBContext.clone(localPmtInfo2);
/* 6450:7101 */       localEJBContext.returnToPool();
/* 6451:7102 */       return localPmtInfo2;
/* 6452:     */     }
/* 6453:     */     catch (Exception localException)
/* 6454:     */     {
/* 6455:7106 */       if (localEJBContext != null)
/* 6456:     */       {
/* 6457:7108 */         if ((localException instanceof FFSException)) {
/* 6458:7110 */           throw ((FFSException)EJBContext.clone(localException));
/* 6459:     */         }
/* 6460:7112 */         localEJBContext.throwRemote(localException);
/* 6461:     */       }
/* 6462:7114 */       throw new EJBException(localException);
/* 6463:     */     }
/* 6464:     */     finally
/* 6465:     */     {
/* 6466:7118 */       this._comp.setCurrent(localJavaComponent);
/* 6467:     */     }
/* 6468:     */   }
/* 6469:     */   
/* 6470:     */   public PmtInfo getBillPaymentById(String paramString1, String paramString2)
/* 6471:     */     throws RemoteException, FFSException
/* 6472:     */   {
/* 6473:7127 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6474:7128 */     EJBContext localEJBContext = null;
/* 6475:     */     try
/* 6476:     */     {
/* 6477:7131 */       localEJBContext = this._comp.getPooledInstance();
/* 6478:7132 */       if (localEJBContext == null) {
/* 6479:7134 */         localEJBContext = _create();
/* 6480:     */       }
/* 6481:7136 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6482:7137 */       if (localEJBContext.debug) {
/* 6483:7139 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBillPaymentById"));
/* 6484:     */       }
/* 6485:7141 */       PmtInfo localPmtInfo2 = localBPWServicesBean
/* 6486:7142 */         .getBillPaymentById(
/* 6487:7143 */         paramString1, 
/* 6488:7144 */         paramString2);
/* 6489:     */       
/* 6490:7146 */       localPmtInfo2 = (PmtInfo)EJBContext.clone(localPmtInfo2);
/* 6491:7147 */       localEJBContext.returnToPool();
/* 6492:7148 */       return localPmtInfo2;
/* 6493:     */     }
/* 6494:     */     catch (Exception localException)
/* 6495:     */     {
/* 6496:7152 */       if (localEJBContext != null)
/* 6497:     */       {
/* 6498:7154 */         if ((localException instanceof FFSException)) {
/* 6499:7156 */           throw ((FFSException)EJBContext.clone(localException));
/* 6500:     */         }
/* 6501:7158 */         localEJBContext.throwRemote(localException);
/* 6502:     */       }
/* 6503:7160 */       throw new EJBException(localException);
/* 6504:     */     }
/* 6505:     */     finally
/* 6506:     */     {
/* 6507:7164 */       this._comp.setCurrent(localJavaComponent);
/* 6508:     */     }
/* 6509:     */   }
/* 6510:     */   
/* 6511:     */   public PaymentBatchInfo addPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
/* 6512:     */     throws RemoteException, FFSException
/* 6513:     */   {
/* 6514:7172 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6515:7173 */     EJBContext localEJBContext = null;
/* 6516:     */     try
/* 6517:     */     {
/* 6518:7176 */       localEJBContext = this._comp.getPooledInstance();
/* 6519:7177 */       if (localEJBContext == null) {
/* 6520:7179 */         localEJBContext = _create();
/* 6521:     */       }
/* 6522:7181 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6523:7182 */       if (localEJBContext.debug) {
/* 6524:7184 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addPaymentBatch"));
/* 6525:     */       }
/* 6526:7186 */       PaymentBatchInfo localPaymentBatchInfo2 = localBPWServicesBean
/* 6527:7187 */         .addPaymentBatch(
/* 6528:7188 */         (PaymentBatchInfo)EJBContext.clone(paramPaymentBatchInfo));
/* 6529:     */       
/* 6530:7190 */       localPaymentBatchInfo2 = (PaymentBatchInfo)EJBContext.clone(localPaymentBatchInfo2);
/* 6531:7191 */       localEJBContext.returnToPool();
/* 6532:7192 */       return localPaymentBatchInfo2;
/* 6533:     */     }
/* 6534:     */     catch (Exception localException)
/* 6535:     */     {
/* 6536:7196 */       if (localEJBContext != null)
/* 6537:     */       {
/* 6538:7198 */         if ((localException instanceof FFSException)) {
/* 6539:7200 */           throw ((FFSException)EJBContext.clone(localException));
/* 6540:     */         }
/* 6541:7202 */         localEJBContext.throwRemote(localException);
/* 6542:     */       }
/* 6543:7204 */       throw new EJBException(localException);
/* 6544:     */     }
/* 6545:     */     finally
/* 6546:     */     {
/* 6547:7208 */       this._comp.setCurrent(localJavaComponent);
/* 6548:     */     }
/* 6549:     */   }
/* 6550:     */   
/* 6551:     */   public PaymentBatchInfo modifyPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
/* 6552:     */     throws RemoteException, FFSException
/* 6553:     */   {
/* 6554:7216 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6555:7217 */     EJBContext localEJBContext = null;
/* 6556:     */     try
/* 6557:     */     {
/* 6558:7220 */       localEJBContext = this._comp.getPooledInstance();
/* 6559:7221 */       if (localEJBContext == null) {
/* 6560:7223 */         localEJBContext = _create();
/* 6561:     */       }
/* 6562:7225 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6563:7226 */       if (localEJBContext.debug) {
/* 6564:7228 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modifyPaymentBatch"));
/* 6565:     */       }
/* 6566:7230 */       PaymentBatchInfo localPaymentBatchInfo2 = localBPWServicesBean
/* 6567:7231 */         .modifyPaymentBatch(
/* 6568:7232 */         (PaymentBatchInfo)EJBContext.clone(paramPaymentBatchInfo));
/* 6569:     */       
/* 6570:7234 */       localPaymentBatchInfo2 = (PaymentBatchInfo)EJBContext.clone(localPaymentBatchInfo2);
/* 6571:7235 */       localEJBContext.returnToPool();
/* 6572:7236 */       return localPaymentBatchInfo2;
/* 6573:     */     }
/* 6574:     */     catch (Exception localException)
/* 6575:     */     {
/* 6576:7240 */       if (localEJBContext != null)
/* 6577:     */       {
/* 6578:7242 */         if ((localException instanceof FFSException)) {
/* 6579:7244 */           throw ((FFSException)EJBContext.clone(localException));
/* 6580:     */         }
/* 6581:7246 */         localEJBContext.throwRemote(localException);
/* 6582:     */       }
/* 6583:7248 */       throw new EJBException(localException);
/* 6584:     */     }
/* 6585:     */     finally
/* 6586:     */     {
/* 6587:7252 */       this._comp.setCurrent(localJavaComponent);
/* 6588:     */     }
/* 6589:     */   }
/* 6590:     */   
/* 6591:     */   public PaymentBatchInfo deletePaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
/* 6592:     */     throws RemoteException, FFSException
/* 6593:     */   {
/* 6594:7260 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6595:7261 */     EJBContext localEJBContext = null;
/* 6596:     */     try
/* 6597:     */     {
/* 6598:7264 */       localEJBContext = this._comp.getPooledInstance();
/* 6599:7265 */       if (localEJBContext == null) {
/* 6600:7267 */         localEJBContext = _create();
/* 6601:     */       }
/* 6602:7269 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6603:7270 */       if (localEJBContext.debug) {
/* 6604:7272 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deletePaymentBatch"));
/* 6605:     */       }
/* 6606:7274 */       PaymentBatchInfo localPaymentBatchInfo2 = localBPWServicesBean
/* 6607:7275 */         .deletePaymentBatch(
/* 6608:7276 */         (PaymentBatchInfo)EJBContext.clone(paramPaymentBatchInfo));
/* 6609:     */       
/* 6610:7278 */       localPaymentBatchInfo2 = (PaymentBatchInfo)EJBContext.clone(localPaymentBatchInfo2);
/* 6611:7279 */       localEJBContext.returnToPool();
/* 6612:7280 */       return localPaymentBatchInfo2;
/* 6613:     */     }
/* 6614:     */     catch (Exception localException)
/* 6615:     */     {
/* 6616:7284 */       if (localEJBContext != null)
/* 6617:     */       {
/* 6618:7286 */         if ((localException instanceof FFSException)) {
/* 6619:7288 */           throw ((FFSException)EJBContext.clone(localException));
/* 6620:     */         }
/* 6621:7290 */         localEJBContext.throwRemote(localException);
/* 6622:     */       }
/* 6623:7292 */       throw new EJBException(localException);
/* 6624:     */     }
/* 6625:     */     finally
/* 6626:     */     {
/* 6627:7296 */       this._comp.setCurrent(localJavaComponent);
/* 6628:     */     }
/* 6629:     */   }
/* 6630:     */   
/* 6631:     */   public PaymentBatchInfo getPaymentBatchById(String paramString)
/* 6632:     */     throws RemoteException, FFSException
/* 6633:     */   {
/* 6634:7304 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6635:7305 */     EJBContext localEJBContext = null;
/* 6636:     */     try
/* 6637:     */     {
/* 6638:7308 */       localEJBContext = this._comp.getPooledInstance();
/* 6639:7309 */       if (localEJBContext == null) {
/* 6640:7311 */         localEJBContext = _create();
/* 6641:     */       }
/* 6642:7313 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6643:7314 */       if (localEJBContext.debug) {
/* 6644:7316 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPaymentBatchById"));
/* 6645:     */       }
/* 6646:7318 */       PaymentBatchInfo localPaymentBatchInfo2 = localBPWServicesBean
/* 6647:7319 */         .getPaymentBatchById(
/* 6648:7320 */         paramString);
/* 6649:     */       
/* 6650:7322 */       localPaymentBatchInfo2 = (PaymentBatchInfo)EJBContext.clone(localPaymentBatchInfo2);
/* 6651:7323 */       localEJBContext.returnToPool();
/* 6652:7324 */       return localPaymentBatchInfo2;
/* 6653:     */     }
/* 6654:     */     catch (Exception localException)
/* 6655:     */     {
/* 6656:7328 */       if (localEJBContext != null)
/* 6657:     */       {
/* 6658:7330 */         if ((localException instanceof FFSException)) {
/* 6659:7332 */           throw ((FFSException)EJBContext.clone(localException));
/* 6660:     */         }
/* 6661:7334 */         localEJBContext.throwRemote(localException);
/* 6662:     */       }
/* 6663:7336 */       throw new EJBException(localException);
/* 6664:     */     }
/* 6665:     */     finally
/* 6666:     */     {
/* 6667:7340 */       this._comp.setCurrent(localJavaComponent);
/* 6668:     */     }
/* 6669:     */   }
/* 6670:     */   
/* 6671:     */   public LastPaymentInfo getLastPayments(LastPaymentInfo paramLastPaymentInfo)
/* 6672:     */     throws RemoteException, FFSException
/* 6673:     */   {
/* 6674:7348 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6675:7349 */     EJBContext localEJBContext = null;
/* 6676:     */     try
/* 6677:     */     {
/* 6678:7352 */       localEJBContext = this._comp.getPooledInstance();
/* 6679:7353 */       if (localEJBContext == null) {
/* 6680:7355 */         localEJBContext = _create();
/* 6681:     */       }
/* 6682:7357 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6683:7358 */       if (localEJBContext.debug) {
/* 6684:7360 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getLastPayments"));
/* 6685:     */       }
/* 6686:7362 */       LastPaymentInfo localLastPaymentInfo2 = localBPWServicesBean
/* 6687:7363 */         .getLastPayments(
/* 6688:7364 */         (LastPaymentInfo)EJBContext.clone(paramLastPaymentInfo));
/* 6689:     */       
/* 6690:7366 */       localLastPaymentInfo2 = (LastPaymentInfo)EJBContext.clone(localLastPaymentInfo2);
/* 6691:7367 */       localEJBContext.returnToPool();
/* 6692:7368 */       return localLastPaymentInfo2;
/* 6693:     */     }
/* 6694:     */     catch (Exception localException)
/* 6695:     */     {
/* 6696:7372 */       if (localEJBContext != null)
/* 6697:     */       {
/* 6698:7374 */         if ((localException instanceof FFSException)) {
/* 6699:7376 */           throw ((FFSException)EJBContext.clone(localException));
/* 6700:     */         }
/* 6701:7378 */         localEJBContext.throwRemote(localException);
/* 6702:     */       }
/* 6703:7380 */       throw new EJBException(localException);
/* 6704:     */     }
/* 6705:     */     finally
/* 6706:     */     {
/* 6707:7384 */       this._comp.setCurrent(localJavaComponent);
/* 6708:     */     }
/* 6709:     */   }
/* 6710:     */   
/* 6711:     */   public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
/* 6712:     */     throws RemoteException, FFSException
/* 6713:     */   {
/* 6714:7393 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6715:7394 */     EJBContext localEJBContext = null;
/* 6716:     */     try
/* 6717:     */     {
/* 6718:7397 */       localEJBContext = this._comp.getPooledInstance();
/* 6719:7398 */       if (localEJBContext == null) {
/* 6720:7400 */         localEJBContext = _create();
/* 6721:     */       }
/* 6722:7402 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6723:7403 */       if (localEJBContext.debug) {
/* 6724:7405 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getBankingDaysInRange"));
/* 6725:     */       }
/* 6726:7407 */       BankingDays localBankingDays2 = localBPWServicesBean
/* 6727:7408 */         .getBankingDaysInRange(
/* 6728:7409 */         (BankingDays)EJBContext.clone(paramBankingDays), 
/* 6729:7410 */         (HashMap)EJBContext.clone(paramHashMap));
/* 6730:     */       
/* 6731:7412 */       localBankingDays2 = (BankingDays)EJBContext.clone(localBankingDays2);
/* 6732:7413 */       localEJBContext.returnToPool();
/* 6733:7414 */       return localBankingDays2;
/* 6734:     */     }
/* 6735:     */     catch (Exception localException)
/* 6736:     */     {
/* 6737:7418 */       if (localEJBContext != null)
/* 6738:     */       {
/* 6739:7420 */         if ((localException instanceof FFSException)) {
/* 6740:7422 */           throw ((FFSException)EJBContext.clone(localException));
/* 6741:     */         }
/* 6742:7424 */         localEJBContext.throwRemote(localException);
/* 6743:     */       }
/* 6744:7426 */       throw new EJBException(localException);
/* 6745:     */     }
/* 6746:     */     finally
/* 6747:     */     {
/* 6748:7430 */       this._comp.setCurrent(localJavaComponent);
/* 6749:     */     }
/* 6750:     */   }
/* 6751:     */   
/* 6752:     */   public CustomerPayeeInfo addCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
/* 6753:     */     throws RemoteException, FFSException
/* 6754:     */   {
/* 6755:7438 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6756:7439 */     EJBContext localEJBContext = null;
/* 6757:     */     try
/* 6758:     */     {
/* 6759:7442 */       localEJBContext = this._comp.getPooledInstance();
/* 6760:7443 */       if (localEJBContext == null) {
/* 6761:7445 */         localEJBContext = _create();
/* 6762:     */       }
/* 6763:7447 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6764:7448 */       if (localEJBContext.debug) {
/* 6765:7450 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCustomerPayee"));
/* 6766:     */       }
/* 6767:7452 */       CustomerPayeeInfo localCustomerPayeeInfo2 = localBPWServicesBean
/* 6768:7453 */         .addCustomerPayee(
/* 6769:7454 */         (CustomerPayeeInfo)EJBContext.clone(paramCustomerPayeeInfo));
/* 6770:     */       
/* 6771:7456 */       localCustomerPayeeInfo2 = (CustomerPayeeInfo)EJBContext.clone(localCustomerPayeeInfo2);
/* 6772:7457 */       localEJBContext.returnToPool();
/* 6773:7458 */       return localCustomerPayeeInfo2;
/* 6774:     */     }
/* 6775:     */     catch (Exception localException)
/* 6776:     */     {
/* 6777:7462 */       if (localEJBContext != null)
/* 6778:     */       {
/* 6779:7464 */         if ((localException instanceof FFSException)) {
/* 6780:7466 */           throw ((FFSException)EJBContext.clone(localException));
/* 6781:     */         }
/* 6782:7468 */         localEJBContext.throwRemote(localException);
/* 6783:     */       }
/* 6784:7470 */       throw new EJBException(localException);
/* 6785:     */     }
/* 6786:     */     finally
/* 6787:     */     {
/* 6788:7474 */       this._comp.setCurrent(localJavaComponent);
/* 6789:     */     }
/* 6790:     */   }
/* 6791:     */   
/* 6792:     */   public CustomerPayeeInfo deleteCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
/* 6793:     */     throws RemoteException, FFSException
/* 6794:     */   {
/* 6795:7482 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6796:7483 */     EJBContext localEJBContext = null;
/* 6797:     */     try
/* 6798:     */     {
/* 6799:7486 */       localEJBContext = this._comp.getPooledInstance();
/* 6800:7487 */       if (localEJBContext == null) {
/* 6801:7489 */         localEJBContext = _create();
/* 6802:     */       }
/* 6803:7491 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6804:7492 */       if (localEJBContext.debug) {
/* 6805:7494 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomerPayee"));
/* 6806:     */       }
/* 6807:7496 */       CustomerPayeeInfo localCustomerPayeeInfo2 = localBPWServicesBean
/* 6808:7497 */         .deleteCustomerPayee(
/* 6809:7498 */         (CustomerPayeeInfo)EJBContext.clone(paramCustomerPayeeInfo));
/* 6810:     */       
/* 6811:7500 */       localCustomerPayeeInfo2 = (CustomerPayeeInfo)EJBContext.clone(localCustomerPayeeInfo2);
/* 6812:7501 */       localEJBContext.returnToPool();
/* 6813:7502 */       return localCustomerPayeeInfo2;
/* 6814:     */     }
/* 6815:     */     catch (Exception localException)
/* 6816:     */     {
/* 6817:7506 */       if (localEJBContext != null)
/* 6818:     */       {
/* 6819:7508 */         if ((localException instanceof FFSException)) {
/* 6820:7510 */           throw ((FFSException)EJBContext.clone(localException));
/* 6821:     */         }
/* 6822:7512 */         localEJBContext.throwRemote(localException);
/* 6823:     */       }
/* 6824:7514 */       throw new EJBException(localException);
/* 6825:     */     }
/* 6826:     */     finally
/* 6827:     */     {
/* 6828:7518 */       this._comp.setCurrent(localJavaComponent);
/* 6829:     */     }
/* 6830:     */   }
/* 6831:     */   
/* 6832:     */   public CustomerPayeeInfo updateCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
/* 6833:     */     throws RemoteException, FFSException
/* 6834:     */   {
/* 6835:7526 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6836:7527 */     EJBContext localEJBContext = null;
/* 6837:     */     try
/* 6838:     */     {
/* 6839:7530 */       localEJBContext = this._comp.getPooledInstance();
/* 6840:7531 */       if (localEJBContext == null) {
/* 6841:7533 */         localEJBContext = _create();
/* 6842:     */       }
/* 6843:7535 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6844:7536 */       if (localEJBContext.debug) {
/* 6845:7538 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updateCustomerPayee"));
/* 6846:     */       }
/* 6847:7540 */       CustomerPayeeInfo localCustomerPayeeInfo2 = localBPWServicesBean
/* 6848:7541 */         .updateCustomerPayee(
/* 6849:7542 */         (CustomerPayeeInfo)EJBContext.clone(paramCustomerPayeeInfo));
/* 6850:     */       
/* 6851:7544 */       localCustomerPayeeInfo2 = (CustomerPayeeInfo)EJBContext.clone(localCustomerPayeeInfo2);
/* 6852:7545 */       localEJBContext.returnToPool();
/* 6853:7546 */       return localCustomerPayeeInfo2;
/* 6854:     */     }
/* 6855:     */     catch (Exception localException)
/* 6856:     */     {
/* 6857:7550 */       if (localEJBContext != null)
/* 6858:     */       {
/* 6859:7552 */         if ((localException instanceof FFSException)) {
/* 6860:7554 */           throw ((FFSException)EJBContext.clone(localException));
/* 6861:     */         }
/* 6862:7556 */         localEJBContext.throwRemote(localException);
/* 6863:     */       }
/* 6864:7558 */       throw new EJBException(localException);
/* 6865:     */     }
/* 6866:     */     finally
/* 6867:     */     {
/* 6868:7562 */       this._comp.setCurrent(localJavaComponent);
/* 6869:     */     }
/* 6870:     */   }
/* 6871:     */   
/* 6872:     */   public CustomerPayeeInfo[] getCustomerPayees(CustomerPayeeInfo paramCustomerPayeeInfo)
/* 6873:     */     throws RemoteException, FFSException
/* 6874:     */   {
/* 6875:7570 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6876:7571 */     EJBContext localEJBContext = null;
/* 6877:     */     try
/* 6878:     */     {
/* 6879:7574 */       localEJBContext = this._comp.getPooledInstance();
/* 6880:7575 */       if (localEJBContext == null) {
/* 6881:7577 */         localEJBContext = _create();
/* 6882:     */       }
/* 6883:7579 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6884:7580 */       if (localEJBContext.debug) {
/* 6885:7582 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerPayees"));
/* 6886:     */       }
/* 6887:7584 */       CustomerPayeeInfo[] arrayOfCustomerPayeeInfo2 = localBPWServicesBean
/* 6888:7585 */         .getCustomerPayees(
/* 6889:7586 */         (CustomerPayeeInfo)EJBContext.clone(paramCustomerPayeeInfo));
/* 6890:     */       
/* 6891:7588 */       arrayOfCustomerPayeeInfo2 = CustomerPayeeInfoSeqHelper.clone(arrayOfCustomerPayeeInfo2);
/* 6892:7589 */       localEJBContext.returnToPool();
/* 6893:7590 */       return arrayOfCustomerPayeeInfo2;
/* 6894:     */     }
/* 6895:     */     catch (Exception localException)
/* 6896:     */     {
/* 6897:7594 */       if (localEJBContext != null)
/* 6898:     */       {
/* 6899:7596 */         if ((localException instanceof FFSException)) {
/* 6900:7598 */           throw ((FFSException)EJBContext.clone(localException));
/* 6901:     */         }
/* 6902:7600 */         localEJBContext.throwRemote(localException);
/* 6903:     */       }
/* 6904:7602 */       throw new EJBException(localException);
/* 6905:     */     }
/* 6906:     */     finally
/* 6907:     */     {
/* 6908:7606 */       this._comp.setCurrent(localJavaComponent);
/* 6909:     */     }
/* 6910:     */   }
/* 6911:     */   
/* 6912:     */   public PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
/* 6913:     */     throws RemoteException, FFSException
/* 6914:     */   {
/* 6915:7615 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6916:7616 */     EJBContext localEJBContext = null;
/* 6917:     */     try
/* 6918:     */     {
/* 6919:7619 */       localEJBContext = this._comp.getPooledInstance();
/* 6920:7620 */       if (localEJBContext == null) {
/* 6921:7622 */         localEJBContext = _create();
/* 6922:     */       }
/* 6923:7624 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6924:7625 */       if (localEJBContext.debug) {
/* 6925:7627 */         localEJBContext.logger.debug(localEJBContext.debugMsg("searchGlobalPayees"));
/* 6926:     */       }
/* 6927:7629 */       PayeeInfo[] arrayOfPayeeInfo2 = localBPWServicesBean
/* 6928:7630 */         .searchGlobalPayees(
/* 6929:7631 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 6930:7632 */         paramInt);
/* 6931:     */       
/* 6932:7634 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 6933:7635 */       localEJBContext.returnToPool();
/* 6934:7636 */       return arrayOfPayeeInfo2;
/* 6935:     */     }
/* 6936:     */     catch (Exception localException)
/* 6937:     */     {
/* 6938:7640 */       if (localEJBContext != null)
/* 6939:     */       {
/* 6940:7642 */         if ((localException instanceof FFSException)) {
/* 6941:7644 */           throw ((FFSException)EJBContext.clone(localException));
/* 6942:     */         }
/* 6943:7646 */         localEJBContext.throwRemote(localException);
/* 6944:     */       }
/* 6945:7648 */       throw new EJBException(localException);
/* 6946:     */     }
/* 6947:     */     finally
/* 6948:     */     {
/* 6949:7652 */       this._comp.setCurrent(localJavaComponent);
/* 6950:     */     }
/* 6951:     */   }
/* 6952:     */   
/* 6953:     */   public PayeeInfo getGlobalPayee(String paramString)
/* 6954:     */     throws RemoteException, FFSException
/* 6955:     */   {
/* 6956:7660 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6957:7661 */     EJBContext localEJBContext = null;
/* 6958:     */     try
/* 6959:     */     {
/* 6960:7664 */       localEJBContext = this._comp.getPooledInstance();
/* 6961:7665 */       if (localEJBContext == null) {
/* 6962:7667 */         localEJBContext = _create();
/* 6963:     */       }
/* 6964:7669 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 6965:7670 */       if (localEJBContext.debug) {
/* 6966:7672 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getGlobalPayee"));
/* 6967:     */       }
/* 6968:7674 */       PayeeInfo localPayeeInfo2 = localBPWServicesBean
/* 6969:7675 */         .getGlobalPayee(
/* 6970:7676 */         paramString);
/* 6971:     */       
/* 6972:7678 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 6973:7679 */       localEJBContext.returnToPool();
/* 6974:7680 */       return localPayeeInfo2;
/* 6975:     */     }
/* 6976:     */     catch (Exception localException)
/* 6977:     */     {
/* 6978:7684 */       if (localEJBContext != null)
/* 6979:     */       {
/* 6980:7686 */         if ((localException instanceof FFSException)) {
/* 6981:7688 */           throw ((FFSException)EJBContext.clone(localException));
/* 6982:     */         }
/* 6983:7690 */         localEJBContext.throwRemote(localException);
/* 6984:     */       }
/* 6985:7692 */       throw new EJBException(localException);
/* 6986:     */     }
/* 6987:     */     finally
/* 6988:     */     {
/* 6989:7696 */       this._comp.setCurrent(localJavaComponent);
/* 6990:     */     }
/* 6991:     */   }
/* 6992:     */   
/* 6993:     */   public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
/* 6994:     */     throws RemoteException
/* 6995:     */   {
/* 6996:7704 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 6997:7705 */     EJBContext localEJBContext = null;
/* 6998:     */     try
/* 6999:     */     {
/* 7000:7708 */       localEJBContext = this._comp.getPooledInstance();
/* 7001:7709 */       if (localEJBContext == null) {
/* 7002:7711 */         localEJBContext = _create();
/* 7003:     */       }
/* 7004:7713 */       BPWServicesBean localBPWServicesBean = (BPWServicesBean)localEJBContext.getBean();
/* 7005:7714 */       if (localEJBContext.debug) {
/* 7006:7716 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtTrnRslt"));
/* 7007:     */       }
/* 7008:7719 */       localBPWServicesBean.processPmtTrnRslt(
/* 7009:7720 */         PmtTrnRsltSeqHelper.clone(paramArrayOfPmtTrnRslt));
/* 7010:     */       
/* 7011:7722 */       localEJBContext.returnToPool();
/* 7012:     */     }
/* 7013:     */     catch (Exception localException)
/* 7014:     */     {
/* 7015:7726 */       if (localEJBContext != null) {
/* 7016:7728 */         localEJBContext.throwRemote(localException);
/* 7017:     */       }
/* 7018:7730 */       throw new EJBException(localException);
/* 7019:     */     }
/* 7020:     */     finally
/* 7021:     */     {
/* 7022:7734 */       this._comp.setCurrent(localJavaComponent);
/* 7023:     */     }
/* 7024:     */   }
/* 7025:     */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices._lwc_rs_BPWServices_BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */