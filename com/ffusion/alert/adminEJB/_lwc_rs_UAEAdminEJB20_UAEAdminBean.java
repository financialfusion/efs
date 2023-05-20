/*    1:     */ package com.ffusion.alert.adminEJB;
/*    2:     */ 
/*    3:     */ import XDT.BinaryHelper;
/*    4:     */ import com.ffusion.alert.interfaces.AEApplicationInfo;
/*    5:     */ import com.ffusion.alert.interfaces.AEApplicationInfoSeqHelper;
/*    6:     */ import com.ffusion.alert.interfaces.AEDBParams;
/*    7:     */ import com.ffusion.alert.interfaces.AEException;
/*    8:     */ import com.ffusion.alert.interfaces.AEScheduleInfo;
/*    9:     */ import com.ffusion.alert.interfaces.AEServerInfo;
/*   10:     */ import com.ffusion.alert.interfaces.AEServerInfoSeqHelper;
/*   11:     */ import com.ffusion.alert.interfaces.IAEAlertDefinition;
/*   12:     */ import com.ffusion.alert.interfaces.IAEAlertDefinitionSeqHelper;
/*   13:     */ import com.ffusion.alert.interfaces.IAEAuditInfo;
/*   14:     */ import com.ffusion.alert.interfaces.IAEAuditInfoSeqHelper;
/*   15:     */ import com.ffusion.alert.interfaces.IAEChannelInfo;
/*   16:     */ import com.ffusion.alert.interfaces.IAEChannelInfoSeqHelper;
/*   17:     */ import com.ffusion.alert.interfaces.IAEDeliveryInfo;
/*   18:     */ import com.ffusion.alert.interfaces.IAEDeliveryInfoSeqHelper;
/*   19:     */ import com.sybase.ejb.lwc.EJBContext;
/*   20:     */ import com.sybase.ejb.lwc.EJBObject;
/*   21:     */ import com.sybase.ejb.lwc.JavaComponent;
/*   22:     */ import com.sybase.ejb.lwc.Stub;
/*   23:     */ import java.rmi.RemoteException;
/*   24:     */ import java.util.Date;
/*   25:     */ import java.util.Properties;
/*   26:     */ import javax.ejb.EJBException;
/*   27:     */ 
/*   28:     */ public class _lwc_rs_UAEAdminEJB20_UAEAdminBean
/*   29:     */   extends EJBObject
/*   30:     */   implements IAEAlertAdmin
/*   31:     */ {
/*   32:  14 */   private static JavaComponent __component = JavaComponent.get("UAEAdminEJB20/UAEAdminBean");
/*   33:     */   
/*   34:     */   public _lwc_rs_UAEAdminEJB20_UAEAdminBean(Object paramObject)
/*   35:     */   {
/*   36:  19 */     super(__component);
/*   37:  20 */     this.key = paramObject;
/*   38:     */   }
/*   39:     */   
/*   40:     */   private EJBContext _create()
/*   41:     */     throws Exception
/*   42:     */   {
/*   43:  26 */     EJBContext localEJBContext = this._comp.getInstance();
/*   44:  27 */     AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*   45:  28 */     if (localEJBContext.debug) {
/*   46:  30 */       localEJBContext.debug("ejbCreate");
/*   47:     */     }
/*   48:  32 */     localAEAlertAdminBean.ejbCreate();
/*   49:  33 */     return localEJBContext;
/*   50:     */   }
/*   51:     */   
/*   52:     */   public void shutdown()
/*   53:     */     throws RemoteException, AEException
/*   54:     */   {
/*   55:  39 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*   56:  40 */     EJBContext localEJBContext = null;
/*   57:     */     try
/*   58:     */     {
/*   59:  43 */       localEJBContext = this._comp.getPooledInstance();
/*   60:  44 */       if (localEJBContext == null) {
/*   61:  46 */         localEJBContext = _create();
/*   62:     */       }
/*   63:  48 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*   64:  49 */       if (localEJBContext.debug) {
/*   65:  51 */         localEJBContext.debug("shutdown");
/*   66:     */       }
/*   67:  54 */       localAEAlertAdminBean.shutdown();
/*   68:     */       
/*   69:  56 */       localEJBContext.returnToPool();
/*   70:     */     }
/*   71:     */     catch (Exception localException)
/*   72:     */     {
/*   73:  60 */       if (localEJBContext != null)
/*   74:     */       {
/*   75:  62 */         if ((localException instanceof AEException)) {
/*   76:  64 */           throw ((AEException)EJBContext.clone(localException));
/*   77:     */         }
/*   78:  66 */         localEJBContext.throwRemote(localException);
/*   79:     */       }
/*   80:  68 */       throw new EJBException(localException);
/*   81:     */     }
/*   82:     */     finally
/*   83:     */     {
/*   84:  72 */       this._comp.setCurrent(localJavaComponent);
/*   85:     */     }
/*   86:     */   }
/*   87:     */   
/*   88:     */   public void shutdown(String paramString)
/*   89:     */     throws RemoteException, AEException
/*   90:     */   {
/*   91:  80 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*   92:  81 */     EJBContext localEJBContext = null;
/*   93:     */     try
/*   94:     */     {
/*   95:  84 */       localEJBContext = this._comp.getPooledInstance();
/*   96:  85 */       if (localEJBContext == null) {
/*   97:  87 */         localEJBContext = _create();
/*   98:     */       }
/*   99:  89 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  100:  90 */       if (localEJBContext.debug) {
/*  101:  92 */         localEJBContext.debug("shutdown");
/*  102:     */       }
/*  103:  95 */       localAEAlertAdminBean.shutdown(
/*  104:  96 */         paramString);
/*  105:     */       
/*  106:  98 */       localEJBContext.returnToPool();
/*  107:     */     }
/*  108:     */     catch (Exception localException)
/*  109:     */     {
/*  110: 102 */       if (localEJBContext != null)
/*  111:     */       {
/*  112: 104 */         if ((localException instanceof AEException)) {
/*  113: 106 */           throw ((AEException)EJBContext.clone(localException));
/*  114:     */         }
/*  115: 108 */         localEJBContext.throwRemote(localException);
/*  116:     */       }
/*  117: 110 */       throw new EJBException(localException);
/*  118:     */     }
/*  119:     */     finally
/*  120:     */     {
/*  121: 114 */       this._comp.setCurrent(localJavaComponent);
/*  122:     */     }
/*  123:     */   }
/*  124:     */   
/*  125:     */   public void init(AEDBParams paramAEDBParams)
/*  126:     */     throws RemoteException, AEException
/*  127:     */   {
/*  128: 122 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  129: 123 */     EJBContext localEJBContext = null;
/*  130:     */     try
/*  131:     */     {
/*  132: 126 */       localEJBContext = this._comp.getPooledInstance();
/*  133: 127 */       if (localEJBContext == null) {
/*  134: 129 */         localEJBContext = _create();
/*  135:     */       }
/*  136: 131 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  137: 132 */       if (localEJBContext.debug) {
/*  138: 134 */         localEJBContext.debug("init");
/*  139:     */       }
/*  140: 137 */       localAEAlertAdminBean.init(
/*  141: 138 */         (AEDBParams)EJBContext.clone(paramAEDBParams));
/*  142:     */       
/*  143: 140 */       localEJBContext.returnToPool();
/*  144:     */     }
/*  145:     */     catch (Exception localException)
/*  146:     */     {
/*  147: 144 */       if (localEJBContext != null)
/*  148:     */       {
/*  149: 146 */         if ((localException instanceof AEException)) {
/*  150: 148 */           throw ((AEException)EJBContext.clone(localException));
/*  151:     */         }
/*  152: 150 */         localEJBContext.throwRemote(localException);
/*  153:     */       }
/*  154: 152 */       throw new EJBException(localException);
/*  155:     */     }
/*  156:     */     finally
/*  157:     */     {
/*  158: 156 */       this._comp.setCurrent(localJavaComponent);
/*  159:     */     }
/*  160:     */   }
/*  161:     */   
/*  162:     */   public void init(String paramString)
/*  163:     */     throws RemoteException, AEException
/*  164:     */   {
/*  165: 164 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  166: 165 */     EJBContext localEJBContext = null;
/*  167:     */     try
/*  168:     */     {
/*  169: 168 */       localEJBContext = this._comp.getPooledInstance();
/*  170: 169 */       if (localEJBContext == null) {
/*  171: 171 */         localEJBContext = _create();
/*  172:     */       }
/*  173: 173 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  174: 174 */       if (localEJBContext.debug) {
/*  175: 176 */         localEJBContext.debug("init");
/*  176:     */       }
/*  177: 179 */       localAEAlertAdminBean.init(
/*  178: 180 */         paramString);
/*  179:     */       
/*  180: 182 */       localEJBContext.returnToPool();
/*  181:     */     }
/*  182:     */     catch (Exception localException)
/*  183:     */     {
/*  184: 186 */       if (localEJBContext != null)
/*  185:     */       {
/*  186: 188 */         if ((localException instanceof AEException)) {
/*  187: 190 */           throw ((AEException)EJBContext.clone(localException));
/*  188:     */         }
/*  189: 192 */         localEJBContext.throwRemote(localException);
/*  190:     */       }
/*  191: 194 */       throw new EJBException(localException);
/*  192:     */     }
/*  193:     */     finally
/*  194:     */     {
/*  195: 198 */       this._comp.setCurrent(localJavaComponent);
/*  196:     */     }
/*  197:     */   }
/*  198:     */   
/*  199:     */   public void start()
/*  200:     */     throws RemoteException, AEException
/*  201:     */   {
/*  202: 205 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  203: 206 */     EJBContext localEJBContext = null;
/*  204:     */     try
/*  205:     */     {
/*  206: 209 */       localEJBContext = this._comp.getPooledInstance();
/*  207: 210 */       if (localEJBContext == null) {
/*  208: 212 */         localEJBContext = _create();
/*  209:     */       }
/*  210: 214 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  211: 215 */       if (localEJBContext.debug) {
/*  212: 217 */         localEJBContext.debug("start");
/*  213:     */       }
/*  214: 220 */       localAEAlertAdminBean.start();
/*  215:     */       
/*  216: 222 */       localEJBContext.returnToPool();
/*  217:     */     }
/*  218:     */     catch (Exception localException)
/*  219:     */     {
/*  220: 226 */       if (localEJBContext != null)
/*  221:     */       {
/*  222: 228 */         if ((localException instanceof AEException)) {
/*  223: 230 */           throw ((AEException)EJBContext.clone(localException));
/*  224:     */         }
/*  225: 232 */         localEJBContext.throwRemote(localException);
/*  226:     */       }
/*  227: 234 */       throw new EJBException(localException);
/*  228:     */     }
/*  229:     */     finally
/*  230:     */     {
/*  231: 238 */       this._comp.setCurrent(localJavaComponent);
/*  232:     */     }
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void start(String paramString)
/*  236:     */     throws RemoteException, AEException
/*  237:     */   {
/*  238: 246 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  239: 247 */     EJBContext localEJBContext = null;
/*  240:     */     try
/*  241:     */     {
/*  242: 250 */       localEJBContext = this._comp.getPooledInstance();
/*  243: 251 */       if (localEJBContext == null) {
/*  244: 253 */         localEJBContext = _create();
/*  245:     */       }
/*  246: 255 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  247: 256 */       if (localEJBContext.debug) {
/*  248: 258 */         localEJBContext.debug("start");
/*  249:     */       }
/*  250: 261 */       localAEAlertAdminBean.start(
/*  251: 262 */         paramString);
/*  252:     */       
/*  253: 264 */       localEJBContext.returnToPool();
/*  254:     */     }
/*  255:     */     catch (Exception localException)
/*  256:     */     {
/*  257: 268 */       if (localEJBContext != null)
/*  258:     */       {
/*  259: 270 */         if ((localException instanceof AEException)) {
/*  260: 272 */           throw ((AEException)EJBContext.clone(localException));
/*  261:     */         }
/*  262: 274 */         localEJBContext.throwRemote(localException);
/*  263:     */       }
/*  264: 276 */       throw new EJBException(localException);
/*  265:     */     }
/*  266:     */     finally
/*  267:     */     {
/*  268: 280 */       this._comp.setCurrent(localJavaComponent);
/*  269:     */     }
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void resume()
/*  273:     */     throws RemoteException, AEException
/*  274:     */   {
/*  275: 287 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  276: 288 */     EJBContext localEJBContext = null;
/*  277:     */     try
/*  278:     */     {
/*  279: 291 */       localEJBContext = this._comp.getPooledInstance();
/*  280: 292 */       if (localEJBContext == null) {
/*  281: 294 */         localEJBContext = _create();
/*  282:     */       }
/*  283: 296 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  284: 297 */       if (localEJBContext.debug) {
/*  285: 299 */         localEJBContext.debug("resume");
/*  286:     */       }
/*  287: 302 */       localAEAlertAdminBean.resume();
/*  288:     */       
/*  289: 304 */       localEJBContext.returnToPool();
/*  290:     */     }
/*  291:     */     catch (Exception localException)
/*  292:     */     {
/*  293: 308 */       if (localEJBContext != null)
/*  294:     */       {
/*  295: 310 */         if ((localException instanceof AEException)) {
/*  296: 312 */           throw ((AEException)EJBContext.clone(localException));
/*  297:     */         }
/*  298: 314 */         localEJBContext.throwRemote(localException);
/*  299:     */       }
/*  300: 316 */       throw new EJBException(localException);
/*  301:     */     }
/*  302:     */     finally
/*  303:     */     {
/*  304: 320 */       this._comp.setCurrent(localJavaComponent);
/*  305:     */     }
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void resume(String paramString)
/*  309:     */     throws RemoteException, AEException
/*  310:     */   {
/*  311: 328 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  312: 329 */     EJBContext localEJBContext = null;
/*  313:     */     try
/*  314:     */     {
/*  315: 332 */       localEJBContext = this._comp.getPooledInstance();
/*  316: 333 */       if (localEJBContext == null) {
/*  317: 335 */         localEJBContext = _create();
/*  318:     */       }
/*  319: 337 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  320: 338 */       if (localEJBContext.debug) {
/*  321: 340 */         localEJBContext.debug("resume");
/*  322:     */       }
/*  323: 343 */       localAEAlertAdminBean.resume(
/*  324: 344 */         paramString);
/*  325:     */       
/*  326: 346 */       localEJBContext.returnToPool();
/*  327:     */     }
/*  328:     */     catch (Exception localException)
/*  329:     */     {
/*  330: 350 */       if (localEJBContext != null)
/*  331:     */       {
/*  332: 352 */         if ((localException instanceof AEException)) {
/*  333: 354 */           throw ((AEException)EJBContext.clone(localException));
/*  334:     */         }
/*  335: 356 */         localEJBContext.throwRemote(localException);
/*  336:     */       }
/*  337: 358 */       throw new EJBException(localException);
/*  338:     */     }
/*  339:     */     finally
/*  340:     */     {
/*  341: 362 */       this._comp.setCurrent(localJavaComponent);
/*  342:     */     }
/*  343:     */   }
/*  344:     */   
/*  345:     */   public void stop(String paramString)
/*  346:     */     throws RemoteException, AEException
/*  347:     */   {
/*  348: 370 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  349: 371 */     EJBContext localEJBContext = null;
/*  350:     */     try
/*  351:     */     {
/*  352: 374 */       localEJBContext = this._comp.getPooledInstance();
/*  353: 375 */       if (localEJBContext == null) {
/*  354: 377 */         localEJBContext = _create();
/*  355:     */       }
/*  356: 379 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  357: 380 */       if (localEJBContext.debug) {
/*  358: 382 */         localEJBContext.debug("stop");
/*  359:     */       }
/*  360: 385 */       localAEAlertAdminBean.stop(
/*  361: 386 */         paramString);
/*  362:     */       
/*  363: 388 */       localEJBContext.returnToPool();
/*  364:     */     }
/*  365:     */     catch (Exception localException)
/*  366:     */     {
/*  367: 392 */       if (localEJBContext != null)
/*  368:     */       {
/*  369: 394 */         if ((localException instanceof AEException)) {
/*  370: 396 */           throw ((AEException)EJBContext.clone(localException));
/*  371:     */         }
/*  372: 398 */         localEJBContext.throwRemote(localException);
/*  373:     */       }
/*  374: 400 */       throw new EJBException(localException);
/*  375:     */     }
/*  376:     */     finally
/*  377:     */     {
/*  378: 404 */       this._comp.setCurrent(localJavaComponent);
/*  379:     */     }
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void stop()
/*  383:     */     throws RemoteException, AEException
/*  384:     */   {
/*  385: 411 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  386: 412 */     EJBContext localEJBContext = null;
/*  387:     */     try
/*  388:     */     {
/*  389: 415 */       localEJBContext = this._comp.getPooledInstance();
/*  390: 416 */       if (localEJBContext == null) {
/*  391: 418 */         localEJBContext = _create();
/*  392:     */       }
/*  393: 420 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  394: 421 */       if (localEJBContext.debug) {
/*  395: 423 */         localEJBContext.debug("stop");
/*  396:     */       }
/*  397: 426 */       localAEAlertAdminBean.stop();
/*  398:     */       
/*  399: 428 */       localEJBContext.returnToPool();
/*  400:     */     }
/*  401:     */     catch (Exception localException)
/*  402:     */     {
/*  403: 432 */       if (localEJBContext != null)
/*  404:     */       {
/*  405: 434 */         if ((localException instanceof AEException)) {
/*  406: 436 */           throw ((AEException)EJBContext.clone(localException));
/*  407:     */         }
/*  408: 438 */         localEJBContext.throwRemote(localException);
/*  409:     */       }
/*  410: 440 */       throw new EJBException(localException);
/*  411:     */     }
/*  412:     */     finally
/*  413:     */     {
/*  414: 444 */       this._comp.setCurrent(localJavaComponent);
/*  415:     */     }
/*  416:     */   }
/*  417:     */   
/*  418:     */   public void suspend()
/*  419:     */     throws RemoteException, AEException
/*  420:     */   {
/*  421: 451 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  422: 452 */     EJBContext localEJBContext = null;
/*  423:     */     try
/*  424:     */     {
/*  425: 455 */       localEJBContext = this._comp.getPooledInstance();
/*  426: 456 */       if (localEJBContext == null) {
/*  427: 458 */         localEJBContext = _create();
/*  428:     */       }
/*  429: 460 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  430: 461 */       if (localEJBContext.debug) {
/*  431: 463 */         localEJBContext.debug("suspend");
/*  432:     */       }
/*  433: 466 */       localAEAlertAdminBean.suspend();
/*  434:     */       
/*  435: 468 */       localEJBContext.returnToPool();
/*  436:     */     }
/*  437:     */     catch (Exception localException)
/*  438:     */     {
/*  439: 472 */       if (localEJBContext != null)
/*  440:     */       {
/*  441: 474 */         if ((localException instanceof AEException)) {
/*  442: 476 */           throw ((AEException)EJBContext.clone(localException));
/*  443:     */         }
/*  444: 478 */         localEJBContext.throwRemote(localException);
/*  445:     */       }
/*  446: 480 */       throw new EJBException(localException);
/*  447:     */     }
/*  448:     */     finally
/*  449:     */     {
/*  450: 484 */       this._comp.setCurrent(localJavaComponent);
/*  451:     */     }
/*  452:     */   }
/*  453:     */   
/*  454:     */   public void suspend(String paramString)
/*  455:     */     throws RemoteException, AEException
/*  456:     */   {
/*  457: 492 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  458: 493 */     EJBContext localEJBContext = null;
/*  459:     */     try
/*  460:     */     {
/*  461: 496 */       localEJBContext = this._comp.getPooledInstance();
/*  462: 497 */       if (localEJBContext == null) {
/*  463: 499 */         localEJBContext = _create();
/*  464:     */       }
/*  465: 501 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  466: 502 */       if (localEJBContext.debug) {
/*  467: 504 */         localEJBContext.debug("suspend");
/*  468:     */       }
/*  469: 507 */       localAEAlertAdminBean.suspend(
/*  470: 508 */         paramString);
/*  471:     */       
/*  472: 510 */       localEJBContext.returnToPool();
/*  473:     */     }
/*  474:     */     catch (Exception localException)
/*  475:     */     {
/*  476: 514 */       if (localEJBContext != null)
/*  477:     */       {
/*  478: 516 */         if ((localException instanceof AEException)) {
/*  479: 518 */           throw ((AEException)EJBContext.clone(localException));
/*  480:     */         }
/*  481: 520 */         localEJBContext.throwRemote(localException);
/*  482:     */       }
/*  483: 522 */       throw new EJBException(localException);
/*  484:     */     }
/*  485:     */     finally
/*  486:     */     {
/*  487: 526 */       this._comp.setCurrent(localJavaComponent);
/*  488:     */     }
/*  489:     */   }
/*  490:     */   
/*  491:     */   public void addApplication(AEApplicationInfo paramAEApplicationInfo)
/*  492:     */     throws RemoteException, AEException
/*  493:     */   {
/*  494: 534 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  495: 535 */     EJBContext localEJBContext = null;
/*  496:     */     try
/*  497:     */     {
/*  498: 538 */       localEJBContext = this._comp.getPooledInstance();
/*  499: 539 */       if (localEJBContext == null) {
/*  500: 541 */         localEJBContext = _create();
/*  501:     */       }
/*  502: 543 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  503: 544 */       if (localEJBContext.debug) {
/*  504: 546 */         localEJBContext.debug("addApplication");
/*  505:     */       }
/*  506: 549 */       localAEAlertAdminBean.addApplication(
/*  507: 550 */         (AEApplicationInfo)EJBContext.clone(paramAEApplicationInfo));
/*  508:     */       
/*  509: 552 */       localEJBContext.returnToPool();
/*  510:     */     }
/*  511:     */     catch (Exception localException)
/*  512:     */     {
/*  513: 556 */       if (localEJBContext != null)
/*  514:     */       {
/*  515: 558 */         if ((localException instanceof AEException)) {
/*  516: 560 */           throw ((AEException)EJBContext.clone(localException));
/*  517:     */         }
/*  518: 562 */         localEJBContext.throwRemote(localException);
/*  519:     */       }
/*  520: 564 */       throw new EJBException(localException);
/*  521:     */     }
/*  522:     */     finally
/*  523:     */     {
/*  524: 568 */       this._comp.setCurrent(localJavaComponent);
/*  525:     */     }
/*  526:     */   }
/*  527:     */   
/*  528:     */   public AEServerInfo[] getServers()
/*  529:     */     throws RemoteException, AEException
/*  530:     */   {
/*  531: 575 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  532: 576 */     EJBContext localEJBContext = null;
/*  533:     */     try
/*  534:     */     {
/*  535: 579 */       localEJBContext = this._comp.getPooledInstance();
/*  536: 580 */       if (localEJBContext == null) {
/*  537: 582 */         localEJBContext = _create();
/*  538:     */       }
/*  539: 584 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  540: 585 */       if (localEJBContext.debug) {
/*  541: 587 */         localEJBContext.debug("getServers");
/*  542:     */       }
/*  543: 589 */       AEServerInfo[] arrayOfAEServerInfo2 = localAEAlertAdminBean
/*  544: 590 */         .getServers();
/*  545:     */       
/*  546: 592 */       arrayOfAEServerInfo2 = AEServerInfoSeqHelper.clone(arrayOfAEServerInfo2);
/*  547: 593 */       localEJBContext.returnToPool();
/*  548: 594 */       return arrayOfAEServerInfo2;
/*  549:     */     }
/*  550:     */     catch (Exception localException)
/*  551:     */     {
/*  552: 598 */       if (localEJBContext != null)
/*  553:     */       {
/*  554: 600 */         if ((localException instanceof AEException)) {
/*  555: 602 */           throw ((AEException)EJBContext.clone(localException));
/*  556:     */         }
/*  557: 604 */         localEJBContext.throwRemote(localException);
/*  558:     */       }
/*  559: 606 */       throw new EJBException(localException);
/*  560:     */     }
/*  561:     */     finally
/*  562:     */     {
/*  563: 610 */       this._comp.setCurrent(localJavaComponent);
/*  564:     */     }
/*  565:     */   }
/*  566:     */   
/*  567:     */   public void removeApplication(String paramString)
/*  568:     */     throws RemoteException, AEException
/*  569:     */   {
/*  570: 618 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  571: 619 */     EJBContext localEJBContext = null;
/*  572:     */     try
/*  573:     */     {
/*  574: 622 */       localEJBContext = this._comp.getPooledInstance();
/*  575: 623 */       if (localEJBContext == null) {
/*  576: 625 */         localEJBContext = _create();
/*  577:     */       }
/*  578: 627 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  579: 628 */       if (localEJBContext.debug) {
/*  580: 630 */         localEJBContext.debug("removeApplication");
/*  581:     */       }
/*  582: 633 */       localAEAlertAdminBean.removeApplication(
/*  583: 634 */         paramString);
/*  584:     */       
/*  585: 636 */       localEJBContext.returnToPool();
/*  586:     */     }
/*  587:     */     catch (Exception localException)
/*  588:     */     {
/*  589: 640 */       if (localEJBContext != null)
/*  590:     */       {
/*  591: 642 */         if ((localException instanceof AEException)) {
/*  592: 644 */           throw ((AEException)EJBContext.clone(localException));
/*  593:     */         }
/*  594: 646 */         localEJBContext.throwRemote(localException);
/*  595:     */       }
/*  596: 648 */       throw new EJBException(localException);
/*  597:     */     }
/*  598:     */     finally
/*  599:     */     {
/*  600: 652 */       this._comp.setCurrent(localJavaComponent);
/*  601:     */     }
/*  602:     */   }
/*  603:     */   
/*  604:     */   public AEServerInfo addServer(AEServerInfo paramAEServerInfo)
/*  605:     */     throws RemoteException, AEException
/*  606:     */   {
/*  607: 660 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  608: 661 */     EJBContext localEJBContext = null;
/*  609:     */     try
/*  610:     */     {
/*  611: 664 */       localEJBContext = this._comp.getPooledInstance();
/*  612: 665 */       if (localEJBContext == null) {
/*  613: 667 */         localEJBContext = _create();
/*  614:     */       }
/*  615: 669 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  616: 670 */       if (localEJBContext.debug) {
/*  617: 672 */         localEJBContext.debug("addServer");
/*  618:     */       }
/*  619: 674 */       AEServerInfo localAEServerInfo2 = localAEAlertAdminBean
/*  620: 675 */         .addServer(
/*  621: 676 */         (AEServerInfo)EJBContext.clone(paramAEServerInfo));
/*  622:     */       
/*  623: 678 */       localAEServerInfo2 = (AEServerInfo)EJBContext.clone(localAEServerInfo2);
/*  624: 679 */       localEJBContext.returnToPool();
/*  625: 680 */       return localAEServerInfo2;
/*  626:     */     }
/*  627:     */     catch (Exception localException)
/*  628:     */     {
/*  629: 684 */       if (localEJBContext != null)
/*  630:     */       {
/*  631: 686 */         if ((localException instanceof AEException)) {
/*  632: 688 */           throw ((AEException)EJBContext.clone(localException));
/*  633:     */         }
/*  634: 690 */         localEJBContext.throwRemote(localException);
/*  635:     */       }
/*  636: 692 */       throw new EJBException(localException);
/*  637:     */     }
/*  638:     */     finally
/*  639:     */     {
/*  640: 696 */       this._comp.setCurrent(localJavaComponent);
/*  641:     */     }
/*  642:     */   }
/*  643:     */   
/*  644:     */   public AEApplicationInfo[] getApplications()
/*  645:     */     throws RemoteException, AEException
/*  646:     */   {
/*  647: 703 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  648: 704 */     EJBContext localEJBContext = null;
/*  649:     */     try
/*  650:     */     {
/*  651: 707 */       localEJBContext = this._comp.getPooledInstance();
/*  652: 708 */       if (localEJBContext == null) {
/*  653: 710 */         localEJBContext = _create();
/*  654:     */       }
/*  655: 712 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  656: 713 */       if (localEJBContext.debug) {
/*  657: 715 */         localEJBContext.debug("getApplications");
/*  658:     */       }
/*  659: 717 */       AEApplicationInfo[] arrayOfAEApplicationInfo2 = localAEAlertAdminBean
/*  660: 718 */         .getApplications();
/*  661:     */       
/*  662: 720 */       arrayOfAEApplicationInfo2 = AEApplicationInfoSeqHelper.clone(arrayOfAEApplicationInfo2);
/*  663: 721 */       localEJBContext.returnToPool();
/*  664: 722 */       return arrayOfAEApplicationInfo2;
/*  665:     */     }
/*  666:     */     catch (Exception localException)
/*  667:     */     {
/*  668: 726 */       if (localEJBContext != null)
/*  669:     */       {
/*  670: 728 */         if ((localException instanceof AEException)) {
/*  671: 730 */           throw ((AEException)EJBContext.clone(localException));
/*  672:     */         }
/*  673: 732 */         localEJBContext.throwRemote(localException);
/*  674:     */       }
/*  675: 734 */       throw new EJBException(localException);
/*  676:     */     }
/*  677:     */     finally
/*  678:     */     {
/*  679: 738 */       this._comp.setCurrent(localJavaComponent);
/*  680:     */     }
/*  681:     */   }
/*  682:     */   
/*  683:     */   public void removeServer(String paramString)
/*  684:     */     throws RemoteException, AEException
/*  685:     */   {
/*  686: 746 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  687: 747 */     EJBContext localEJBContext = null;
/*  688:     */     try
/*  689:     */     {
/*  690: 750 */       localEJBContext = this._comp.getPooledInstance();
/*  691: 751 */       if (localEJBContext == null) {
/*  692: 753 */         localEJBContext = _create();
/*  693:     */       }
/*  694: 755 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  695: 756 */       if (localEJBContext.debug) {
/*  696: 758 */         localEJBContext.debug("removeServer");
/*  697:     */       }
/*  698: 761 */       localAEAlertAdminBean.removeServer(
/*  699: 762 */         paramString);
/*  700:     */       
/*  701: 764 */       localEJBContext.returnToPool();
/*  702:     */     }
/*  703:     */     catch (Exception localException)
/*  704:     */     {
/*  705: 768 */       if (localEJBContext != null)
/*  706:     */       {
/*  707: 770 */         if ((localException instanceof AEException)) {
/*  708: 772 */           throw ((AEException)EJBContext.clone(localException));
/*  709:     */         }
/*  710: 774 */         localEJBContext.throwRemote(localException);
/*  711:     */       }
/*  712: 776 */       throw new EJBException(localException);
/*  713:     */     }
/*  714:     */     finally
/*  715:     */     {
/*  716: 780 */       this._comp.setCurrent(localJavaComponent);
/*  717:     */     }
/*  718:     */   }
/*  719:     */   
/*  720:     */   public boolean isInitialized()
/*  721:     */     throws RemoteException, AEException
/*  722:     */   {
/*  723: 787 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  724: 788 */     EJBContext localEJBContext = null;
/*  725:     */     try
/*  726:     */     {
/*  727: 791 */       localEJBContext = this._comp.getPooledInstance();
/*  728: 792 */       if (localEJBContext == null) {
/*  729: 794 */         localEJBContext = _create();
/*  730:     */       }
/*  731: 796 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  732: 797 */       if (localEJBContext.debug) {
/*  733: 799 */         localEJBContext.debug("isInitialized");
/*  734:     */       }
/*  735: 801 */       boolean bool2 = localAEAlertAdminBean
/*  736: 802 */         .isInitialized();
/*  737:     */       
/*  738: 804 */       localEJBContext.returnToPool();
/*  739: 805 */       return bool2;
/*  740:     */     }
/*  741:     */     catch (Exception localException)
/*  742:     */     {
/*  743: 809 */       if (localEJBContext != null)
/*  744:     */       {
/*  745: 811 */         if ((localException instanceof AEException)) {
/*  746: 813 */           throw ((AEException)EJBContext.clone(localException));
/*  747:     */         }
/*  748: 815 */         localEJBContext.throwRemote(localException);
/*  749:     */       }
/*  750: 817 */       throw new EJBException(localException);
/*  751:     */     }
/*  752:     */     finally
/*  753:     */     {
/*  754: 821 */       this._comp.setCurrent(localJavaComponent);
/*  755:     */     }
/*  756:     */   }
/*  757:     */   
/*  758:     */   public boolean isInitialized(String paramString)
/*  759:     */     throws RemoteException, AEException
/*  760:     */   {
/*  761: 829 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  762: 830 */     EJBContext localEJBContext = null;
/*  763:     */     try
/*  764:     */     {
/*  765: 833 */       localEJBContext = this._comp.getPooledInstance();
/*  766: 834 */       if (localEJBContext == null) {
/*  767: 836 */         localEJBContext = _create();
/*  768:     */       }
/*  769: 838 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  770: 839 */       if (localEJBContext.debug) {
/*  771: 841 */         localEJBContext.debug("isInitialized");
/*  772:     */       }
/*  773: 843 */       boolean bool2 = localAEAlertAdminBean
/*  774: 844 */         .isInitialized(
/*  775: 845 */         paramString);
/*  776:     */       
/*  777: 847 */       localEJBContext.returnToPool();
/*  778: 848 */       return bool2;
/*  779:     */     }
/*  780:     */     catch (Exception localException)
/*  781:     */     {
/*  782: 852 */       if (localEJBContext != null)
/*  783:     */       {
/*  784: 854 */         if ((localException instanceof AEException)) {
/*  785: 856 */           throw ((AEException)EJBContext.clone(localException));
/*  786:     */         }
/*  787: 858 */         localEJBContext.throwRemote(localException);
/*  788:     */       }
/*  789: 860 */       throw new EJBException(localException);
/*  790:     */     }
/*  791:     */     finally
/*  792:     */     {
/*  793: 864 */       this._comp.setCurrent(localJavaComponent);
/*  794:     */     }
/*  795:     */   }
/*  796:     */   
/*  797:     */   public void destroyRepository(AEDBParams paramAEDBParams)
/*  798:     */     throws RemoteException, AEException
/*  799:     */   {
/*  800: 872 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  801: 873 */     EJBContext localEJBContext = null;
/*  802:     */     try
/*  803:     */     {
/*  804: 876 */       localEJBContext = this._comp.getPooledInstance();
/*  805: 877 */       if (localEJBContext == null) {
/*  806: 879 */         localEJBContext = _create();
/*  807:     */       }
/*  808: 881 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  809: 882 */       if (localEJBContext.debug) {
/*  810: 884 */         localEJBContext.debug("destroyRepository");
/*  811:     */       }
/*  812: 887 */       localAEAlertAdminBean.destroyRepository(
/*  813: 888 */         (AEDBParams)EJBContext.clone(paramAEDBParams));
/*  814:     */       
/*  815: 890 */       localEJBContext.returnToPool();
/*  816:     */     }
/*  817:     */     catch (Exception localException)
/*  818:     */     {
/*  819: 894 */       if (localEJBContext != null)
/*  820:     */       {
/*  821: 896 */         if ((localException instanceof AEException)) {
/*  822: 898 */           throw ((AEException)EJBContext.clone(localException));
/*  823:     */         }
/*  824: 900 */         localEJBContext.throwRemote(localException);
/*  825:     */       }
/*  826: 902 */       throw new EJBException(localException);
/*  827:     */     }
/*  828:     */     finally
/*  829:     */     {
/*  830: 906 */       this._comp.setCurrent(localJavaComponent);
/*  831:     */     }
/*  832:     */   }
/*  833:     */   
/*  834:     */   public void checkRepository(AEDBParams paramAEDBParams)
/*  835:     */     throws RemoteException, AEException
/*  836:     */   {
/*  837: 914 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  838: 915 */     EJBContext localEJBContext = null;
/*  839:     */     try
/*  840:     */     {
/*  841: 918 */       localEJBContext = this._comp.getPooledInstance();
/*  842: 919 */       if (localEJBContext == null) {
/*  843: 921 */         localEJBContext = _create();
/*  844:     */       }
/*  845: 923 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  846: 924 */       if (localEJBContext.debug) {
/*  847: 926 */         localEJBContext.debug("checkRepository");
/*  848:     */       }
/*  849: 929 */       localAEAlertAdminBean.checkRepository(
/*  850: 930 */         (AEDBParams)EJBContext.clone(paramAEDBParams));
/*  851:     */       
/*  852: 932 */       localEJBContext.returnToPool();
/*  853:     */     }
/*  854:     */     catch (Exception localException)
/*  855:     */     {
/*  856: 936 */       if (localEJBContext != null)
/*  857:     */       {
/*  858: 938 */         if ((localException instanceof AEException)) {
/*  859: 940 */           throw ((AEException)EJBContext.clone(localException));
/*  860:     */         }
/*  861: 942 */         localEJBContext.throwRemote(localException);
/*  862:     */       }
/*  863: 944 */       throw new EJBException(localException);
/*  864:     */     }
/*  865:     */     finally
/*  866:     */     {
/*  867: 948 */       this._comp.setCurrent(localJavaComponent);
/*  868:     */     }
/*  869:     */   }
/*  870:     */   
/*  871:     */   public void createRepository(AEDBParams paramAEDBParams, boolean paramBoolean)
/*  872:     */     throws RemoteException, AEException
/*  873:     */   {
/*  874: 957 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  875: 958 */     EJBContext localEJBContext = null;
/*  876:     */     try
/*  877:     */     {
/*  878: 961 */       localEJBContext = this._comp.getPooledInstance();
/*  879: 962 */       if (localEJBContext == null) {
/*  880: 964 */         localEJBContext = _create();
/*  881:     */       }
/*  882: 966 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  883: 967 */       if (localEJBContext.debug) {
/*  884: 969 */         localEJBContext.debug("createRepository");
/*  885:     */       }
/*  886: 972 */       localAEAlertAdminBean.createRepository(
/*  887: 973 */         (AEDBParams)EJBContext.clone(paramAEDBParams), 
/*  888: 974 */         paramBoolean);
/*  889:     */       
/*  890: 976 */       localEJBContext.returnToPool();
/*  891:     */     }
/*  892:     */     catch (Exception localException)
/*  893:     */     {
/*  894: 980 */       if (localEJBContext != null)
/*  895:     */       {
/*  896: 982 */         if ((localException instanceof AEException)) {
/*  897: 984 */           throw ((AEException)EJBContext.clone(localException));
/*  898:     */         }
/*  899: 986 */         localEJBContext.throwRemote(localException);
/*  900:     */       }
/*  901: 988 */       throw new EJBException(localException);
/*  902:     */     }
/*  903:     */     finally
/*  904:     */     {
/*  905: 992 */       this._comp.setCurrent(localJavaComponent);
/*  906:     */     }
/*  907:     */   }
/*  908:     */   
/*  909:     */   public void initServerAll(AEDBParams paramAEDBParams)
/*  910:     */     throws RemoteException, AEException
/*  911:     */   {
/*  912:1000 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  913:1001 */     EJBContext localEJBContext = null;
/*  914:     */     try
/*  915:     */     {
/*  916:1004 */       localEJBContext = this._comp.getPooledInstance();
/*  917:1005 */       if (localEJBContext == null) {
/*  918:1007 */         localEJBContext = _create();
/*  919:     */       }
/*  920:1009 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  921:1010 */       if (localEJBContext.debug) {
/*  922:1012 */         localEJBContext.debug("initServerAll");
/*  923:     */       }
/*  924:1015 */       localAEAlertAdminBean.initServerAll(
/*  925:1016 */         (AEDBParams)EJBContext.clone(paramAEDBParams));
/*  926:     */       
/*  927:1018 */       localEJBContext.returnToPool();
/*  928:     */     }
/*  929:     */     catch (Exception localException)
/*  930:     */     {
/*  931:1022 */       if (localEJBContext != null)
/*  932:     */       {
/*  933:1024 */         if ((localException instanceof AEException)) {
/*  934:1026 */           throw ((AEException)EJBContext.clone(localException));
/*  935:     */         }
/*  936:1028 */         localEJBContext.throwRemote(localException);
/*  937:     */       }
/*  938:1030 */       throw new EJBException(localException);
/*  939:     */     }
/*  940:     */     finally
/*  941:     */     {
/*  942:1034 */       this._comp.setCurrent(localJavaComponent);
/*  943:     */     }
/*  944:     */   }
/*  945:     */   
/*  946:     */   public void startServerNamed(String paramString)
/*  947:     */     throws RemoteException, AEException
/*  948:     */   {
/*  949:1042 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  950:1043 */     EJBContext localEJBContext = null;
/*  951:     */     try
/*  952:     */     {
/*  953:1046 */       localEJBContext = this._comp.getPooledInstance();
/*  954:1047 */       if (localEJBContext == null) {
/*  955:1049 */         localEJBContext = _create();
/*  956:     */       }
/*  957:1051 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  958:1052 */       if (localEJBContext.debug) {
/*  959:1054 */         localEJBContext.debug("startServerNamed");
/*  960:     */       }
/*  961:1057 */       localAEAlertAdminBean.startServerNamed(
/*  962:1058 */         paramString);
/*  963:     */       
/*  964:1060 */       localEJBContext.returnToPool();
/*  965:     */     }
/*  966:     */     catch (Exception localException)
/*  967:     */     {
/*  968:1064 */       if (localEJBContext != null)
/*  969:     */       {
/*  970:1066 */         if ((localException instanceof AEException)) {
/*  971:1068 */           throw ((AEException)EJBContext.clone(localException));
/*  972:     */         }
/*  973:1070 */         localEJBContext.throwRemote(localException);
/*  974:     */       }
/*  975:1072 */       throw new EJBException(localException);
/*  976:     */     }
/*  977:     */     finally
/*  978:     */     {
/*  979:1076 */       this._comp.setCurrent(localJavaComponent);
/*  980:     */     }
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void startServerAll()
/*  984:     */     throws RemoteException, AEException
/*  985:     */   {
/*  986:1083 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  987:1084 */     EJBContext localEJBContext = null;
/*  988:     */     try
/*  989:     */     {
/*  990:1087 */       localEJBContext = this._comp.getPooledInstance();
/*  991:1088 */       if (localEJBContext == null) {
/*  992:1090 */         localEJBContext = _create();
/*  993:     */       }
/*  994:1092 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/*  995:1093 */       if (localEJBContext.debug) {
/*  996:1095 */         localEJBContext.debug("startServerAll");
/*  997:     */       }
/*  998:1098 */       localAEAlertAdminBean.startServerAll();
/*  999:     */       
/* 1000:1100 */       localEJBContext.returnToPool();
/* 1001:     */     }
/* 1002:     */     catch (Exception localException)
/* 1003:     */     {
/* 1004:1104 */       if (localEJBContext != null)
/* 1005:     */       {
/* 1006:1106 */         if ((localException instanceof AEException)) {
/* 1007:1108 */           throw ((AEException)EJBContext.clone(localException));
/* 1008:     */         }
/* 1009:1110 */         localEJBContext.throwRemote(localException);
/* 1010:     */       }
/* 1011:1112 */       throw new EJBException(localException);
/* 1012:     */     }
/* 1013:     */     finally
/* 1014:     */     {
/* 1015:1116 */       this._comp.setCurrent(localJavaComponent);
/* 1016:     */     }
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public void stopServerAll()
/* 1020:     */     throws RemoteException, AEException
/* 1021:     */   {
/* 1022:1123 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1023:1124 */     EJBContext localEJBContext = null;
/* 1024:     */     try
/* 1025:     */     {
/* 1026:1127 */       localEJBContext = this._comp.getPooledInstance();
/* 1027:1128 */       if (localEJBContext == null) {
/* 1028:1130 */         localEJBContext = _create();
/* 1029:     */       }
/* 1030:1132 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1031:1133 */       if (localEJBContext.debug) {
/* 1032:1135 */         localEJBContext.debug("stopServerAll");
/* 1033:     */       }
/* 1034:1138 */       localAEAlertAdminBean.stopServerAll();
/* 1035:     */       
/* 1036:1140 */       localEJBContext.returnToPool();
/* 1037:     */     }
/* 1038:     */     catch (Exception localException)
/* 1039:     */     {
/* 1040:1144 */       if (localEJBContext != null)
/* 1041:     */       {
/* 1042:1146 */         if ((localException instanceof AEException)) {
/* 1043:1148 */           throw ((AEException)EJBContext.clone(localException));
/* 1044:     */         }
/* 1045:1150 */         localEJBContext.throwRemote(localException);
/* 1046:     */       }
/* 1047:1152 */       throw new EJBException(localException);
/* 1048:     */     }
/* 1049:     */     finally
/* 1050:     */     {
/* 1051:1156 */       this._comp.setCurrent(localJavaComponent);
/* 1052:     */     }
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public void suspendServerAll()
/* 1056:     */     throws RemoteException, AEException
/* 1057:     */   {
/* 1058:1163 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1059:1164 */     EJBContext localEJBContext = null;
/* 1060:     */     try
/* 1061:     */     {
/* 1062:1167 */       localEJBContext = this._comp.getPooledInstance();
/* 1063:1168 */       if (localEJBContext == null) {
/* 1064:1170 */         localEJBContext = _create();
/* 1065:     */       }
/* 1066:1172 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1067:1173 */       if (localEJBContext.debug) {
/* 1068:1175 */         localEJBContext.debug("suspendServerAll");
/* 1069:     */       }
/* 1070:1178 */       localAEAlertAdminBean.suspendServerAll();
/* 1071:     */       
/* 1072:1180 */       localEJBContext.returnToPool();
/* 1073:     */     }
/* 1074:     */     catch (Exception localException)
/* 1075:     */     {
/* 1076:1184 */       if (localEJBContext != null)
/* 1077:     */       {
/* 1078:1186 */         if ((localException instanceof AEException)) {
/* 1079:1188 */           throw ((AEException)EJBContext.clone(localException));
/* 1080:     */         }
/* 1081:1190 */         localEJBContext.throwRemote(localException);
/* 1082:     */       }
/* 1083:1192 */       throw new EJBException(localException);
/* 1084:     */     }
/* 1085:     */     finally
/* 1086:     */     {
/* 1087:1196 */       this._comp.setCurrent(localJavaComponent);
/* 1088:     */     }
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public void resumeServerAll()
/* 1092:     */     throws RemoteException, AEException
/* 1093:     */   {
/* 1094:1203 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1095:1204 */     EJBContext localEJBContext = null;
/* 1096:     */     try
/* 1097:     */     {
/* 1098:1207 */       localEJBContext = this._comp.getPooledInstance();
/* 1099:1208 */       if (localEJBContext == null) {
/* 1100:1210 */         localEJBContext = _create();
/* 1101:     */       }
/* 1102:1212 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1103:1213 */       if (localEJBContext.debug) {
/* 1104:1215 */         localEJBContext.debug("resumeServerAll");
/* 1105:     */       }
/* 1106:1218 */       localAEAlertAdminBean.resumeServerAll();
/* 1107:     */       
/* 1108:1220 */       localEJBContext.returnToPool();
/* 1109:     */     }
/* 1110:     */     catch (Exception localException)
/* 1111:     */     {
/* 1112:1224 */       if (localEJBContext != null)
/* 1113:     */       {
/* 1114:1226 */         if ((localException instanceof AEException)) {
/* 1115:1228 */           throw ((AEException)EJBContext.clone(localException));
/* 1116:     */         }
/* 1117:1230 */         localEJBContext.throwRemote(localException);
/* 1118:     */       }
/* 1119:1232 */       throw new EJBException(localException);
/* 1120:     */     }
/* 1121:     */     finally
/* 1122:     */     {
/* 1123:1236 */       this._comp.setCurrent(localJavaComponent);
/* 1124:     */     }
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public void shutdownServerAll()
/* 1128:     */     throws RemoteException, AEException
/* 1129:     */   {
/* 1130:1243 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1131:1244 */     EJBContext localEJBContext = null;
/* 1132:     */     try
/* 1133:     */     {
/* 1134:1247 */       localEJBContext = this._comp.getPooledInstance();
/* 1135:1248 */       if (localEJBContext == null) {
/* 1136:1250 */         localEJBContext = _create();
/* 1137:     */       }
/* 1138:1252 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1139:1253 */       if (localEJBContext.debug) {
/* 1140:1255 */         localEJBContext.debug("shutdownServerAll");
/* 1141:     */       }
/* 1142:1258 */       localAEAlertAdminBean.shutdownServerAll();
/* 1143:     */       
/* 1144:1260 */       localEJBContext.returnToPool();
/* 1145:     */     }
/* 1146:     */     catch (Exception localException)
/* 1147:     */     {
/* 1148:1264 */       if (localEJBContext != null)
/* 1149:     */       {
/* 1150:1266 */         if ((localException instanceof AEException)) {
/* 1151:1268 */           throw ((AEException)EJBContext.clone(localException));
/* 1152:     */         }
/* 1153:1270 */         localEJBContext.throwRemote(localException);
/* 1154:     */       }
/* 1155:1272 */       throw new EJBException(localException);
/* 1156:     */     }
/* 1157:     */     finally
/* 1158:     */     {
/* 1159:1276 */       this._comp.setCurrent(localJavaComponent);
/* 1160:     */     }
/* 1161:     */   }
/* 1162:     */   
/* 1163:     */   public boolean isSuspended()
/* 1164:     */     throws RemoteException, AEException
/* 1165:     */   {
/* 1166:1283 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1167:1284 */     EJBContext localEJBContext = null;
/* 1168:     */     try
/* 1169:     */     {
/* 1170:1287 */       localEJBContext = this._comp.getPooledInstance();
/* 1171:1288 */       if (localEJBContext == null) {
/* 1172:1290 */         localEJBContext = _create();
/* 1173:     */       }
/* 1174:1292 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1175:1293 */       if (localEJBContext.debug) {
/* 1176:1295 */         localEJBContext.debug("isSuspended");
/* 1177:     */       }
/* 1178:1297 */       boolean bool2 = localAEAlertAdminBean
/* 1179:1298 */         .isSuspended();
/* 1180:     */       
/* 1181:1300 */       localEJBContext.returnToPool();
/* 1182:1301 */       return bool2;
/* 1183:     */     }
/* 1184:     */     catch (Exception localException)
/* 1185:     */     {
/* 1186:1305 */       if (localEJBContext != null)
/* 1187:     */       {
/* 1188:1307 */         if ((localException instanceof AEException)) {
/* 1189:1309 */           throw ((AEException)EJBContext.clone(localException));
/* 1190:     */         }
/* 1191:1311 */         localEJBContext.throwRemote(localException);
/* 1192:     */       }
/* 1193:1313 */       throw new EJBException(localException);
/* 1194:     */     }
/* 1195:     */     finally
/* 1196:     */     {
/* 1197:1317 */       this._comp.setCurrent(localJavaComponent);
/* 1198:     */     }
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   public boolean isSuspended(String paramString)
/* 1202:     */     throws RemoteException, AEException
/* 1203:     */   {
/* 1204:1325 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1205:1326 */     EJBContext localEJBContext = null;
/* 1206:     */     try
/* 1207:     */     {
/* 1208:1329 */       localEJBContext = this._comp.getPooledInstance();
/* 1209:1330 */       if (localEJBContext == null) {
/* 1210:1332 */         localEJBContext = _create();
/* 1211:     */       }
/* 1212:1334 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1213:1335 */       if (localEJBContext.debug) {
/* 1214:1337 */         localEJBContext.debug("isSuspended");
/* 1215:     */       }
/* 1216:1339 */       boolean bool2 = localAEAlertAdminBean
/* 1217:1340 */         .isSuspended(
/* 1218:1341 */         paramString);
/* 1219:     */       
/* 1220:1343 */       localEJBContext.returnToPool();
/* 1221:1344 */       return bool2;
/* 1222:     */     }
/* 1223:     */     catch (Exception localException)
/* 1224:     */     {
/* 1225:1348 */       if (localEJBContext != null)
/* 1226:     */       {
/* 1227:1350 */         if ((localException instanceof AEException)) {
/* 1228:1352 */           throw ((AEException)EJBContext.clone(localException));
/* 1229:     */         }
/* 1230:1354 */         localEJBContext.throwRemote(localException);
/* 1231:     */       }
/* 1232:1356 */       throw new EJBException(localException);
/* 1233:     */     }
/* 1234:     */     finally
/* 1235:     */     {
/* 1236:1360 */       this._comp.setCurrent(localJavaComponent);
/* 1237:     */     }
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public boolean isRunning(String paramString)
/* 1241:     */     throws RemoteException, AEException
/* 1242:     */   {
/* 1243:1368 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1244:1369 */     EJBContext localEJBContext = null;
/* 1245:     */     try
/* 1246:     */     {
/* 1247:1372 */       localEJBContext = this._comp.getPooledInstance();
/* 1248:1373 */       if (localEJBContext == null) {
/* 1249:1375 */         localEJBContext = _create();
/* 1250:     */       }
/* 1251:1377 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1252:1378 */       if (localEJBContext.debug) {
/* 1253:1380 */         localEJBContext.debug("isRunning");
/* 1254:     */       }
/* 1255:1382 */       boolean bool2 = localAEAlertAdminBean
/* 1256:1383 */         .isRunning(
/* 1257:1384 */         paramString);
/* 1258:     */       
/* 1259:1386 */       localEJBContext.returnToPool();
/* 1260:1387 */       return bool2;
/* 1261:     */     }
/* 1262:     */     catch (Exception localException)
/* 1263:     */     {
/* 1264:1391 */       if (localEJBContext != null)
/* 1265:     */       {
/* 1266:1393 */         if ((localException instanceof AEException)) {
/* 1267:1395 */           throw ((AEException)EJBContext.clone(localException));
/* 1268:     */         }
/* 1269:1397 */         localEJBContext.throwRemote(localException);
/* 1270:     */       }
/* 1271:1399 */       throw new EJBException(localException);
/* 1272:     */     }
/* 1273:     */     finally
/* 1274:     */     {
/* 1275:1403 */       this._comp.setCurrent(localJavaComponent);
/* 1276:     */     }
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public boolean isRunning()
/* 1280:     */     throws RemoteException, AEException
/* 1281:     */   {
/* 1282:1410 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1283:1411 */     EJBContext localEJBContext = null;
/* 1284:     */     try
/* 1285:     */     {
/* 1286:1414 */       localEJBContext = this._comp.getPooledInstance();
/* 1287:1415 */       if (localEJBContext == null) {
/* 1288:1417 */         localEJBContext = _create();
/* 1289:     */       }
/* 1290:1419 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1291:1420 */       if (localEJBContext.debug) {
/* 1292:1422 */         localEJBContext.debug("isRunning");
/* 1293:     */       }
/* 1294:1424 */       boolean bool2 = localAEAlertAdminBean
/* 1295:1425 */         .isRunning();
/* 1296:     */       
/* 1297:1427 */       localEJBContext.returnToPool();
/* 1298:1428 */       return bool2;
/* 1299:     */     }
/* 1300:     */     catch (Exception localException)
/* 1301:     */     {
/* 1302:1432 */       if (localEJBContext != null)
/* 1303:     */       {
/* 1304:1434 */         if ((localException instanceof AEException)) {
/* 1305:1436 */           throw ((AEException)EJBContext.clone(localException));
/* 1306:     */         }
/* 1307:1438 */         localEJBContext.throwRemote(localException);
/* 1308:     */       }
/* 1309:1440 */       throw new EJBException(localException);
/* 1310:     */     }
/* 1311:     */     finally
/* 1312:     */     {
/* 1313:1444 */       this._comp.setCurrent(localJavaComponent);
/* 1314:     */     }
/* 1315:     */   }
/* 1316:     */   
/* 1317:     */   public boolean isClusterStopped()
/* 1318:     */     throws RemoteException, AEException
/* 1319:     */   {
/* 1320:1451 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1321:1452 */     EJBContext localEJBContext = null;
/* 1322:     */     try
/* 1323:     */     {
/* 1324:1455 */       localEJBContext = this._comp.getPooledInstance();
/* 1325:1456 */       if (localEJBContext == null) {
/* 1326:1458 */         localEJBContext = _create();
/* 1327:     */       }
/* 1328:1460 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1329:1461 */       if (localEJBContext.debug) {
/* 1330:1463 */         localEJBContext.debug("isClusterStopped");
/* 1331:     */       }
/* 1332:1465 */       boolean bool2 = localAEAlertAdminBean
/* 1333:1466 */         .isClusterStopped();
/* 1334:     */       
/* 1335:1468 */       localEJBContext.returnToPool();
/* 1336:1469 */       return bool2;
/* 1337:     */     }
/* 1338:     */     catch (Exception localException)
/* 1339:     */     {
/* 1340:1473 */       if (localEJBContext != null)
/* 1341:     */       {
/* 1342:1475 */         if ((localException instanceof AEException)) {
/* 1343:1477 */           throw ((AEException)EJBContext.clone(localException));
/* 1344:     */         }
/* 1345:1479 */         localEJBContext.throwRemote(localException);
/* 1346:     */       }
/* 1347:1481 */       throw new EJBException(localException);
/* 1348:     */     }
/* 1349:     */     finally
/* 1350:     */     {
/* 1351:1485 */       this._comp.setCurrent(localJavaComponent);
/* 1352:     */     }
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public boolean isClusterRunning()
/* 1356:     */     throws RemoteException, AEException
/* 1357:     */   {
/* 1358:1492 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1359:1493 */     EJBContext localEJBContext = null;
/* 1360:     */     try
/* 1361:     */     {
/* 1362:1496 */       localEJBContext = this._comp.getPooledInstance();
/* 1363:1497 */       if (localEJBContext == null) {
/* 1364:1499 */         localEJBContext = _create();
/* 1365:     */       }
/* 1366:1501 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1367:1502 */       if (localEJBContext.debug) {
/* 1368:1504 */         localEJBContext.debug("isClusterRunning");
/* 1369:     */       }
/* 1370:1506 */       boolean bool2 = localAEAlertAdminBean
/* 1371:1507 */         .isClusterRunning();
/* 1372:     */       
/* 1373:1509 */       localEJBContext.returnToPool();
/* 1374:1510 */       return bool2;
/* 1375:     */     }
/* 1376:     */     catch (Exception localException)
/* 1377:     */     {
/* 1378:1514 */       if (localEJBContext != null)
/* 1379:     */       {
/* 1380:1516 */         if ((localException instanceof AEException)) {
/* 1381:1518 */           throw ((AEException)EJBContext.clone(localException));
/* 1382:     */         }
/* 1383:1520 */         localEJBContext.throwRemote(localException);
/* 1384:     */       }
/* 1385:1522 */       throw new EJBException(localException);
/* 1386:     */     }
/* 1387:     */     finally
/* 1388:     */     {
/* 1389:1526 */       this._comp.setCurrent(localJavaComponent);
/* 1390:     */     }
/* 1391:     */   }
/* 1392:     */   
/* 1393:     */   public void updateApplication(String paramString, AEApplicationInfo paramAEApplicationInfo)
/* 1394:     */     throws RemoteException, AEException
/* 1395:     */   {
/* 1396:1535 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1397:1536 */     EJBContext localEJBContext = null;
/* 1398:     */     try
/* 1399:     */     {
/* 1400:1539 */       localEJBContext = this._comp.getPooledInstance();
/* 1401:1540 */       if (localEJBContext == null) {
/* 1402:1542 */         localEJBContext = _create();
/* 1403:     */       }
/* 1404:1544 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1405:1545 */       if (localEJBContext.debug) {
/* 1406:1547 */         localEJBContext.debug("updateApplication");
/* 1407:     */       }
/* 1408:1550 */       localAEAlertAdminBean.updateApplication(
/* 1409:1551 */         paramString, 
/* 1410:1552 */         (AEApplicationInfo)EJBContext.clone(paramAEApplicationInfo));
/* 1411:     */       
/* 1412:1554 */       localEJBContext.returnToPool();
/* 1413:     */     }
/* 1414:     */     catch (Exception localException)
/* 1415:     */     {
/* 1416:1558 */       if (localEJBContext != null)
/* 1417:     */       {
/* 1418:1560 */         if ((localException instanceof AEException)) {
/* 1419:1562 */           throw ((AEException)EJBContext.clone(localException));
/* 1420:     */         }
/* 1421:1564 */         localEJBContext.throwRemote(localException);
/* 1422:     */       }
/* 1423:1566 */       throw new EJBException(localException);
/* 1424:     */     }
/* 1425:     */     finally
/* 1426:     */     {
/* 1427:1570 */       this._comp.setCurrent(localJavaComponent);
/* 1428:     */     }
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt, String paramString3)
/* 1432:     */     throws RemoteException, AEException
/* 1433:     */   {
/* 1434:1582 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1435:1583 */     EJBContext localEJBContext = null;
/* 1436:     */     try
/* 1437:     */     {
/* 1438:1586 */       localEJBContext = this._comp.getPooledInstance();
/* 1439:1587 */       if (localEJBContext == null) {
/* 1440:1589 */         localEJBContext = _create();
/* 1441:     */       }
/* 1442:1591 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1443:1592 */       if (localEJBContext.debug) {
/* 1444:1594 */         localEJBContext.debug("installDeliveryChannel");
/* 1445:     */       }
/* 1446:1596 */       IAEChannelInfo localIAEChannelInfo2 = localAEAlertAdminBean
/* 1447:1597 */         .installDeliveryChannel(
/* 1448:1598 */         paramString1, 
/* 1449:1599 */         paramString2, 
/* 1450:1600 */         (Properties)EJBContext.clone(paramProperties), 
/* 1451:1601 */         paramInt, 
/* 1452:1602 */         paramString3);
/* 1453:     */       
/* 1454:1604 */       localIAEChannelInfo2 = (IAEChannelInfo)EJBContext.clone(localIAEChannelInfo2);
/* 1455:1605 */       localEJBContext.returnToPool();
/* 1456:1606 */       return localIAEChannelInfo2;
/* 1457:     */     }
/* 1458:     */     catch (Exception localException)
/* 1459:     */     {
/* 1460:1610 */       if (localEJBContext != null)
/* 1461:     */       {
/* 1462:1612 */         if ((localException instanceof AEException)) {
/* 1463:1614 */           throw ((AEException)EJBContext.clone(localException));
/* 1464:     */         }
/* 1465:1616 */         localEJBContext.throwRemote(localException);
/* 1466:     */       }
/* 1467:1618 */       throw new EJBException(localException);
/* 1468:     */     }
/* 1469:     */     finally
/* 1470:     */     {
/* 1471:1622 */       this._comp.setCurrent(localJavaComponent);
/* 1472:     */     }
/* 1473:     */   }
/* 1474:     */   
/* 1475:     */   public IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt)
/* 1476:     */     throws RemoteException, AEException
/* 1477:     */   {
/* 1478:1633 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1479:1634 */     EJBContext localEJBContext = null;
/* 1480:     */     try
/* 1481:     */     {
/* 1482:1637 */       localEJBContext = this._comp.getPooledInstance();
/* 1483:1638 */       if (localEJBContext == null) {
/* 1484:1640 */         localEJBContext = _create();
/* 1485:     */       }
/* 1486:1642 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1487:1643 */       if (localEJBContext.debug) {
/* 1488:1645 */         localEJBContext.debug("installDeliveryChannel");
/* 1489:     */       }
/* 1490:1647 */       IAEChannelInfo localIAEChannelInfo2 = localAEAlertAdminBean
/* 1491:1648 */         .installDeliveryChannel(
/* 1492:1649 */         paramString1, 
/* 1493:1650 */         paramString2, 
/* 1494:1651 */         (Properties)EJBContext.clone(paramProperties), 
/* 1495:1652 */         paramInt);
/* 1496:     */       
/* 1497:1654 */       localIAEChannelInfo2 = (IAEChannelInfo)EJBContext.clone(localIAEChannelInfo2);
/* 1498:1655 */       localEJBContext.returnToPool();
/* 1499:1656 */       return localIAEChannelInfo2;
/* 1500:     */     }
/* 1501:     */     catch (Exception localException)
/* 1502:     */     {
/* 1503:1660 */       if (localEJBContext != null)
/* 1504:     */       {
/* 1505:1662 */         if ((localException instanceof AEException)) {
/* 1506:1664 */           throw ((AEException)EJBContext.clone(localException));
/* 1507:     */         }
/* 1508:1666 */         localEJBContext.throwRemote(localException);
/* 1509:     */       }
/* 1510:1668 */       throw new EJBException(localException);
/* 1511:     */     }
/* 1512:     */     finally
/* 1513:     */     {
/* 1514:1672 */       this._comp.setCurrent(localJavaComponent);
/* 1515:     */     }
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   public IAEChannelInfo uninstallDeliveryChannel(String paramString)
/* 1519:     */     throws RemoteException, AEException
/* 1520:     */   {
/* 1521:1680 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1522:1681 */     EJBContext localEJBContext = null;
/* 1523:     */     try
/* 1524:     */     {
/* 1525:1684 */       localEJBContext = this._comp.getPooledInstance();
/* 1526:1685 */       if (localEJBContext == null) {
/* 1527:1687 */         localEJBContext = _create();
/* 1528:     */       }
/* 1529:1689 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1530:1690 */       if (localEJBContext.debug) {
/* 1531:1692 */         localEJBContext.debug("uninstallDeliveryChannel");
/* 1532:     */       }
/* 1533:1694 */       IAEChannelInfo localIAEChannelInfo2 = localAEAlertAdminBean
/* 1534:1695 */         .uninstallDeliveryChannel(
/* 1535:1696 */         paramString);
/* 1536:     */       
/* 1537:1698 */       localIAEChannelInfo2 = (IAEChannelInfo)EJBContext.clone(localIAEChannelInfo2);
/* 1538:1699 */       localEJBContext.returnToPool();
/* 1539:1700 */       return localIAEChannelInfo2;
/* 1540:     */     }
/* 1541:     */     catch (Exception localException)
/* 1542:     */     {
/* 1543:1704 */       if (localEJBContext != null)
/* 1544:     */       {
/* 1545:1706 */         if ((localException instanceof AEException)) {
/* 1546:1708 */           throw ((AEException)EJBContext.clone(localException));
/* 1547:     */         }
/* 1548:1710 */         localEJBContext.throwRemote(localException);
/* 1549:     */       }
/* 1550:1712 */       throw new EJBException(localException);
/* 1551:     */     }
/* 1552:     */     finally
/* 1553:     */     {
/* 1554:1716 */       this._comp.setCurrent(localJavaComponent);
/* 1555:     */     }
/* 1556:     */   }
/* 1557:     */   
/* 1558:     */   public IAEChannelInfo loadDeliveryChannel(String paramString)
/* 1559:     */     throws RemoteException, AEException
/* 1560:     */   {
/* 1561:1724 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1562:1725 */     EJBContext localEJBContext = null;
/* 1563:     */     try
/* 1564:     */     {
/* 1565:1728 */       localEJBContext = this._comp.getPooledInstance();
/* 1566:1729 */       if (localEJBContext == null) {
/* 1567:1731 */         localEJBContext = _create();
/* 1568:     */       }
/* 1569:1733 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1570:1734 */       if (localEJBContext.debug) {
/* 1571:1736 */         localEJBContext.debug("loadDeliveryChannel");
/* 1572:     */       }
/* 1573:1738 */       IAEChannelInfo localIAEChannelInfo2 = localAEAlertAdminBean
/* 1574:1739 */         .loadDeliveryChannel(
/* 1575:1740 */         paramString);
/* 1576:     */       
/* 1577:1742 */       localIAEChannelInfo2 = (IAEChannelInfo)EJBContext.clone(localIAEChannelInfo2);
/* 1578:1743 */       localEJBContext.returnToPool();
/* 1579:1744 */       return localIAEChannelInfo2;
/* 1580:     */     }
/* 1581:     */     catch (Exception localException)
/* 1582:     */     {
/* 1583:1748 */       if (localEJBContext != null)
/* 1584:     */       {
/* 1585:1750 */         if ((localException instanceof AEException)) {
/* 1586:1752 */           throw ((AEException)EJBContext.clone(localException));
/* 1587:     */         }
/* 1588:1754 */         localEJBContext.throwRemote(localException);
/* 1589:     */       }
/* 1590:1756 */       throw new EJBException(localException);
/* 1591:     */     }
/* 1592:     */     finally
/* 1593:     */     {
/* 1594:1760 */       this._comp.setCurrent(localJavaComponent);
/* 1595:     */     }
/* 1596:     */   }
/* 1597:     */   
/* 1598:     */   public IAEChannelInfo unloadDeliveryChannel(String paramString)
/* 1599:     */     throws RemoteException, AEException
/* 1600:     */   {
/* 1601:1768 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1602:1769 */     EJBContext localEJBContext = null;
/* 1603:     */     try
/* 1604:     */     {
/* 1605:1772 */       localEJBContext = this._comp.getPooledInstance();
/* 1606:1773 */       if (localEJBContext == null) {
/* 1607:1775 */         localEJBContext = _create();
/* 1608:     */       }
/* 1609:1777 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1610:1778 */       if (localEJBContext.debug) {
/* 1611:1780 */         localEJBContext.debug("unloadDeliveryChannel");
/* 1612:     */       }
/* 1613:1782 */       IAEChannelInfo localIAEChannelInfo2 = localAEAlertAdminBean
/* 1614:1783 */         .unloadDeliveryChannel(
/* 1615:1784 */         paramString);
/* 1616:     */       
/* 1617:1786 */       localIAEChannelInfo2 = (IAEChannelInfo)EJBContext.clone(localIAEChannelInfo2);
/* 1618:1787 */       localEJBContext.returnToPool();
/* 1619:1788 */       return localIAEChannelInfo2;
/* 1620:     */     }
/* 1621:     */     catch (Exception localException)
/* 1622:     */     {
/* 1623:1792 */       if (localEJBContext != null)
/* 1624:     */       {
/* 1625:1794 */         if ((localException instanceof AEException)) {
/* 1626:1796 */           throw ((AEException)EJBContext.clone(localException));
/* 1627:     */         }
/* 1628:1798 */         localEJBContext.throwRemote(localException);
/* 1629:     */       }
/* 1630:1800 */       throw new EJBException(localException);
/* 1631:     */     }
/* 1632:     */     finally
/* 1633:     */     {
/* 1634:1804 */       this._comp.setCurrent(localJavaComponent);
/* 1635:     */     }
/* 1636:     */   }
/* 1637:     */   
/* 1638:     */   public IAEChannelInfo updateDeliveryChannel(IAEChannelInfo paramIAEChannelInfo)
/* 1639:     */     throws RemoteException, AEException
/* 1640:     */   {
/* 1641:1812 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1642:1813 */     EJBContext localEJBContext = null;
/* 1643:     */     try
/* 1644:     */     {
/* 1645:1816 */       localEJBContext = this._comp.getPooledInstance();
/* 1646:1817 */       if (localEJBContext == null) {
/* 1647:1819 */         localEJBContext = _create();
/* 1648:     */       }
/* 1649:1821 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1650:1822 */       if (localEJBContext.debug) {
/* 1651:1824 */         localEJBContext.debug("updateDeliveryChannel");
/* 1652:     */       }
/* 1653:1826 */       IAEChannelInfo localIAEChannelInfo2 = localAEAlertAdminBean
/* 1654:1827 */         .updateDeliveryChannel(
/* 1655:1828 */         (IAEChannelInfo)EJBContext.clone(paramIAEChannelInfo));
/* 1656:     */       
/* 1657:1830 */       localIAEChannelInfo2 = (IAEChannelInfo)EJBContext.clone(localIAEChannelInfo2);
/* 1658:1831 */       localEJBContext.returnToPool();
/* 1659:1832 */       return localIAEChannelInfo2;
/* 1660:     */     }
/* 1661:     */     catch (Exception localException)
/* 1662:     */     {
/* 1663:1836 */       if (localEJBContext != null)
/* 1664:     */       {
/* 1665:1838 */         if ((localException instanceof AEException)) {
/* 1666:1840 */           throw ((AEException)EJBContext.clone(localException));
/* 1667:     */         }
/* 1668:1842 */         localEJBContext.throwRemote(localException);
/* 1669:     */       }
/* 1670:1844 */       throw new EJBException(localException);
/* 1671:     */     }
/* 1672:     */     finally
/* 1673:     */     {
/* 1674:1848 */       this._comp.setCurrent(localJavaComponent);
/* 1675:     */     }
/* 1676:     */   }
/* 1677:     */   
/* 1678:     */   public IAEChannelInfo[] getInstalledDeliveryChannels(String paramString)
/* 1679:     */     throws RemoteException, AEException
/* 1680:     */   {
/* 1681:1856 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1682:1857 */     EJBContext localEJBContext = null;
/* 1683:     */     try
/* 1684:     */     {
/* 1685:1860 */       localEJBContext = this._comp.getPooledInstance();
/* 1686:1861 */       if (localEJBContext == null) {
/* 1687:1863 */         localEJBContext = _create();
/* 1688:     */       }
/* 1689:1865 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1690:1866 */       if (localEJBContext.debug) {
/* 1691:1868 */         localEJBContext.debug("getInstalledDeliveryChannels");
/* 1692:     */       }
/* 1693:1870 */       IAEChannelInfo[] arrayOfIAEChannelInfo2 = localAEAlertAdminBean
/* 1694:1871 */         .getInstalledDeliveryChannels(
/* 1695:1872 */         paramString);
/* 1696:     */       
/* 1697:1874 */       arrayOfIAEChannelInfo2 = IAEChannelInfoSeqHelper.clone(arrayOfIAEChannelInfo2);
/* 1698:1875 */       localEJBContext.returnToPool();
/* 1699:1876 */       return arrayOfIAEChannelInfo2;
/* 1700:     */     }
/* 1701:     */     catch (Exception localException)
/* 1702:     */     {
/* 1703:1880 */       if (localEJBContext != null)
/* 1704:     */       {
/* 1705:1882 */         if ((localException instanceof AEException)) {
/* 1706:1884 */           throw ((AEException)EJBContext.clone(localException));
/* 1707:     */         }
/* 1708:1886 */         localEJBContext.throwRemote(localException);
/* 1709:     */       }
/* 1710:1888 */       throw new EJBException(localException);
/* 1711:     */     }
/* 1712:     */     finally
/* 1713:     */     {
/* 1714:1892 */       this._comp.setCurrent(localJavaComponent);
/* 1715:     */     }
/* 1716:     */   }
/* 1717:     */   
/* 1718:     */   public IAEChannelInfo[] getLoadedDeliveryChannels(String paramString)
/* 1719:     */     throws RemoteException, AEException
/* 1720:     */   {
/* 1721:1900 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1722:1901 */     EJBContext localEJBContext = null;
/* 1723:     */     try
/* 1724:     */     {
/* 1725:1904 */       localEJBContext = this._comp.getPooledInstance();
/* 1726:1905 */       if (localEJBContext == null) {
/* 1727:1907 */         localEJBContext = _create();
/* 1728:     */       }
/* 1729:1909 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1730:1910 */       if (localEJBContext.debug) {
/* 1731:1912 */         localEJBContext.debug("getLoadedDeliveryChannels");
/* 1732:     */       }
/* 1733:1914 */       IAEChannelInfo[] arrayOfIAEChannelInfo2 = localAEAlertAdminBean
/* 1734:1915 */         .getLoadedDeliveryChannels(
/* 1735:1916 */         paramString);
/* 1736:     */       
/* 1737:1918 */       arrayOfIAEChannelInfo2 = IAEChannelInfoSeqHelper.clone(arrayOfIAEChannelInfo2);
/* 1738:1919 */       localEJBContext.returnToPool();
/* 1739:1920 */       return arrayOfIAEChannelInfo2;
/* 1740:     */     }
/* 1741:     */     catch (Exception localException)
/* 1742:     */     {
/* 1743:1924 */       if (localEJBContext != null)
/* 1744:     */       {
/* 1745:1926 */         if ((localException instanceof AEException)) {
/* 1746:1928 */           throw ((AEException)EJBContext.clone(localException));
/* 1747:     */         }
/* 1748:1930 */         localEJBContext.throwRemote(localException);
/* 1749:     */       }
/* 1750:1932 */       throw new EJBException(localException);
/* 1751:     */     }
/* 1752:     */     finally
/* 1753:     */     {
/* 1754:1936 */       this._comp.setCurrent(localJavaComponent);
/* 1755:     */     }
/* 1756:     */   }
/* 1757:     */   
/* 1758:     */   public IAEAlertDefinition[] getAllAlerts(boolean paramBoolean)
/* 1759:     */     throws RemoteException, AEException
/* 1760:     */   {
/* 1761:1944 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1762:1945 */     EJBContext localEJBContext = null;
/* 1763:     */     try
/* 1764:     */     {
/* 1765:1948 */       localEJBContext = this._comp.getPooledInstance();
/* 1766:1949 */       if (localEJBContext == null) {
/* 1767:1951 */         localEJBContext = _create();
/* 1768:     */       }
/* 1769:1953 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1770:1954 */       if (localEJBContext.debug) {
/* 1771:1956 */         localEJBContext.debug("getAllAlerts");
/* 1772:     */       }
/* 1773:1958 */       IAEAlertDefinition[] arrayOfIAEAlertDefinition2 = localAEAlertAdminBean
/* 1774:1959 */         .getAllAlerts(
/* 1775:1960 */         paramBoolean);
/* 1776:     */       
/* 1777:1962 */       arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.clone(arrayOfIAEAlertDefinition2);
/* 1778:1963 */       localEJBContext.returnToPool();
/* 1779:1964 */       return arrayOfIAEAlertDefinition2;
/* 1780:     */     }
/* 1781:     */     catch (Exception localException)
/* 1782:     */     {
/* 1783:1968 */       if (localEJBContext != null)
/* 1784:     */       {
/* 1785:1970 */         if ((localException instanceof AEException)) {
/* 1786:1972 */           throw ((AEException)EJBContext.clone(localException));
/* 1787:     */         }
/* 1788:1974 */         localEJBContext.throwRemote(localException);
/* 1789:     */       }
/* 1790:1976 */       throw new EJBException(localException);
/* 1791:     */     }
/* 1792:     */     finally
/* 1793:     */     {
/* 1794:1980 */       this._comp.setCurrent(localJavaComponent);
/* 1795:     */     }
/* 1796:     */   }
/* 1797:     */   
/* 1798:     */   public IAEAlertDefinition[] getAppAlerts(String paramString, boolean paramBoolean)
/* 1799:     */     throws RemoteException, AEException
/* 1800:     */   {
/* 1801:1989 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1802:1990 */     EJBContext localEJBContext = null;
/* 1803:     */     try
/* 1804:     */     {
/* 1805:1993 */       localEJBContext = this._comp.getPooledInstance();
/* 1806:1994 */       if (localEJBContext == null) {
/* 1807:1996 */         localEJBContext = _create();
/* 1808:     */       }
/* 1809:1998 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1810:1999 */       if (localEJBContext.debug) {
/* 1811:2001 */         localEJBContext.debug("getAppAlerts");
/* 1812:     */       }
/* 1813:2003 */       IAEAlertDefinition[] arrayOfIAEAlertDefinition2 = localAEAlertAdminBean
/* 1814:2004 */         .getAppAlerts(
/* 1815:2005 */         paramString, 
/* 1816:2006 */         paramBoolean);
/* 1817:     */       
/* 1818:2008 */       arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.clone(arrayOfIAEAlertDefinition2);
/* 1819:2009 */       localEJBContext.returnToPool();
/* 1820:2010 */       return arrayOfIAEAlertDefinition2;
/* 1821:     */     }
/* 1822:     */     catch (Exception localException)
/* 1823:     */     {
/* 1824:2014 */       if (localEJBContext != null)
/* 1825:     */       {
/* 1826:2016 */         if ((localException instanceof AEException)) {
/* 1827:2018 */           throw ((AEException)EJBContext.clone(localException));
/* 1828:     */         }
/* 1829:2020 */         localEJBContext.throwRemote(localException);
/* 1830:     */       }
/* 1831:2022 */       throw new EJBException(localException);
/* 1832:     */     }
/* 1833:     */     finally
/* 1834:     */     {
/* 1835:2026 */       this._comp.setCurrent(localJavaComponent);
/* 1836:     */     }
/* 1837:     */   }
/* 1838:     */   
/* 1839:     */   public IAEAlertDefinition[] getAppAlertsForChannelPaged(String paramString1, boolean paramBoolean, String paramString2, int paramInt1, int paramInt2)
/* 1840:     */     throws RemoteException, AEException
/* 1841:     */   {
/* 1842:2038 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1843:2039 */     EJBContext localEJBContext = null;
/* 1844:     */     try
/* 1845:     */     {
/* 1846:2042 */       localEJBContext = this._comp.getPooledInstance();
/* 1847:2043 */       if (localEJBContext == null) {
/* 1848:2045 */         localEJBContext = _create();
/* 1849:     */       }
/* 1850:2047 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1851:2048 */       if (localEJBContext.debug) {
/* 1852:2050 */         localEJBContext.debug("getAppAlertsForChannelPaged");
/* 1853:     */       }
/* 1854:2052 */       IAEAlertDefinition[] arrayOfIAEAlertDefinition2 = localAEAlertAdminBean
/* 1855:2053 */         .getAppAlertsForChannelPaged(
/* 1856:2054 */         paramString1, 
/* 1857:2055 */         paramBoolean, 
/* 1858:2056 */         paramString2, 
/* 1859:2057 */         paramInt1, 
/* 1860:2058 */         paramInt2);
/* 1861:     */       
/* 1862:2060 */       arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.clone(arrayOfIAEAlertDefinition2);
/* 1863:2061 */       localEJBContext.returnToPool();
/* 1864:2062 */       return arrayOfIAEAlertDefinition2;
/* 1865:     */     }
/* 1866:     */     catch (Exception localException)
/* 1867:     */     {
/* 1868:2066 */       if (localEJBContext != null)
/* 1869:     */       {
/* 1870:2068 */         if ((localException instanceof AEException)) {
/* 1871:2070 */           throw ((AEException)EJBContext.clone(localException));
/* 1872:     */         }
/* 1873:2072 */         localEJBContext.throwRemote(localException);
/* 1874:     */       }
/* 1875:2074 */       throw new EJBException(localException);
/* 1876:     */     }
/* 1877:     */     finally
/* 1878:     */     {
/* 1879:2078 */       this._comp.setCurrent(localJavaComponent);
/* 1880:     */     }
/* 1881:     */   }
/* 1882:     */   
/* 1883:     */   public IAEAlertDefinition[] getUserAlerts(String paramString, boolean paramBoolean)
/* 1884:     */     throws RemoteException, AEException
/* 1885:     */   {
/* 1886:2087 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1887:2088 */     EJBContext localEJBContext = null;
/* 1888:     */     try
/* 1889:     */     {
/* 1890:2091 */       localEJBContext = this._comp.getPooledInstance();
/* 1891:2092 */       if (localEJBContext == null) {
/* 1892:2094 */         localEJBContext = _create();
/* 1893:     */       }
/* 1894:2096 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1895:2097 */       if (localEJBContext.debug) {
/* 1896:2099 */         localEJBContext.debug("getUserAlerts");
/* 1897:     */       }
/* 1898:2101 */       IAEAlertDefinition[] arrayOfIAEAlertDefinition2 = localAEAlertAdminBean
/* 1899:2102 */         .getUserAlerts(
/* 1900:2103 */         paramString, 
/* 1901:2104 */         paramBoolean);
/* 1902:     */       
/* 1903:2106 */       arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.clone(arrayOfIAEAlertDefinition2);
/* 1904:2107 */       localEJBContext.returnToPool();
/* 1905:2108 */       return arrayOfIAEAlertDefinition2;
/* 1906:     */     }
/* 1907:     */     catch (Exception localException)
/* 1908:     */     {
/* 1909:2112 */       if (localEJBContext != null)
/* 1910:     */       {
/* 1911:2114 */         if ((localException instanceof AEException)) {
/* 1912:2116 */           throw ((AEException)EJBContext.clone(localException));
/* 1913:     */         }
/* 1914:2118 */         localEJBContext.throwRemote(localException);
/* 1915:     */       }
/* 1916:2120 */       throw new EJBException(localException);
/* 1917:     */     }
/* 1918:     */     finally
/* 1919:     */     {
/* 1920:2124 */       this._comp.setCurrent(localJavaComponent);
/* 1921:     */     }
/* 1922:     */   }
/* 1923:     */   
/* 1924:     */   public IAEAlertDefinition[] getUserAlerts(String paramString1, String paramString2, boolean paramBoolean)
/* 1925:     */     throws RemoteException, AEException
/* 1926:     */   {
/* 1927:2134 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1928:2135 */     EJBContext localEJBContext = null;
/* 1929:     */     try
/* 1930:     */     {
/* 1931:2138 */       localEJBContext = this._comp.getPooledInstance();
/* 1932:2139 */       if (localEJBContext == null) {
/* 1933:2141 */         localEJBContext = _create();
/* 1934:     */       }
/* 1935:2143 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1936:2144 */       if (localEJBContext.debug) {
/* 1937:2146 */         localEJBContext.debug("getUserAlerts");
/* 1938:     */       }
/* 1939:2148 */       IAEAlertDefinition[] arrayOfIAEAlertDefinition2 = localAEAlertAdminBean
/* 1940:2149 */         .getUserAlerts(
/* 1941:2150 */         paramString1, 
/* 1942:2151 */         paramString2, 
/* 1943:2152 */         paramBoolean);
/* 1944:     */       
/* 1945:2154 */       arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.clone(arrayOfIAEAlertDefinition2);
/* 1946:2155 */       localEJBContext.returnToPool();
/* 1947:2156 */       return arrayOfIAEAlertDefinition2;
/* 1948:     */     }
/* 1949:     */     catch (Exception localException)
/* 1950:     */     {
/* 1951:2160 */       if (localEJBContext != null)
/* 1952:     */       {
/* 1953:2162 */         if ((localException instanceof AEException)) {
/* 1954:2164 */           throw ((AEException)EJBContext.clone(localException));
/* 1955:     */         }
/* 1956:2166 */         localEJBContext.throwRemote(localException);
/* 1957:     */       }
/* 1958:2168 */       throw new EJBException(localException);
/* 1959:     */     }
/* 1960:     */     finally
/* 1961:     */     {
/* 1962:2172 */       this._comp.setCurrent(localJavaComponent);
/* 1963:     */     }
/* 1964:     */   }
/* 1965:     */   
/* 1966:     */   public IAEAlertDefinition getAlert(int paramInt)
/* 1967:     */     throws RemoteException, AEException
/* 1968:     */   {
/* 1969:2180 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1970:2181 */     EJBContext localEJBContext = null;
/* 1971:     */     try
/* 1972:     */     {
/* 1973:2184 */       localEJBContext = this._comp.getPooledInstance();
/* 1974:2185 */       if (localEJBContext == null) {
/* 1975:2187 */         localEJBContext = _create();
/* 1976:     */       }
/* 1977:2189 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 1978:2190 */       if (localEJBContext.debug) {
/* 1979:2192 */         localEJBContext.debug("getAlert");
/* 1980:     */       }
/* 1981:2194 */       IAEAlertDefinition localIAEAlertDefinition2 = localAEAlertAdminBean
/* 1982:2195 */         .getAlert(
/* 1983:2196 */         paramInt);
/* 1984:     */       
/* 1985:2198 */       localIAEAlertDefinition2 = (IAEAlertDefinition)EJBContext.clone(localIAEAlertDefinition2);
/* 1986:2199 */       localEJBContext.returnToPool();
/* 1987:2200 */       return localIAEAlertDefinition2;
/* 1988:     */     }
/* 1989:     */     catch (Exception localException)
/* 1990:     */     {
/* 1991:2204 */       if (localEJBContext != null)
/* 1992:     */       {
/* 1993:2206 */         if ((localException instanceof AEException)) {
/* 1994:2208 */           throw ((AEException)EJBContext.clone(localException));
/* 1995:     */         }
/* 1996:2210 */         localEJBContext.throwRemote(localException);
/* 1997:     */       }
/* 1998:2212 */       throw new EJBException(localException);
/* 1999:     */     }
/* 2000:     */     finally
/* 2001:     */     {
/* 2002:2216 */       this._comp.setCurrent(localJavaComponent);
/* 2003:     */     }
/* 2004:     */   }
/* 2005:     */   
/* 2006:     */   public IAEAlertDefinition[] getAlerts(int[] paramArrayOfInt)
/* 2007:     */     throws RemoteException, AEException
/* 2008:     */   {
/* 2009:2224 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2010:2225 */     EJBContext localEJBContext = null;
/* 2011:     */     try
/* 2012:     */     {
/* 2013:2228 */       localEJBContext = this._comp.getPooledInstance();
/* 2014:2229 */       if (localEJBContext == null) {
/* 2015:2231 */         localEJBContext = _create();
/* 2016:     */       }
/* 2017:2233 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2018:2234 */       if (localEJBContext.debug) {
/* 2019:2236 */         localEJBContext.debug("getAlerts");
/* 2020:     */       }
/* 2021:2238 */       IAEAlertDefinition[] arrayOfIAEAlertDefinition2 = localAEAlertAdminBean
/* 2022:2239 */         .getAlerts(
/* 2023:2240 */         longSeqHelper.clone(paramArrayOfInt));
/* 2024:     */       
/* 2025:2242 */       arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.clone(arrayOfIAEAlertDefinition2);
/* 2026:2243 */       localEJBContext.returnToPool();
/* 2027:2244 */       return arrayOfIAEAlertDefinition2;
/* 2028:     */     }
/* 2029:     */     catch (Exception localException)
/* 2030:     */     {
/* 2031:2248 */       if (localEJBContext != null)
/* 2032:     */       {
/* 2033:2250 */         if ((localException instanceof AEException)) {
/* 2034:2252 */           throw ((AEException)EJBContext.clone(localException));
/* 2035:     */         }
/* 2036:2254 */         localEJBContext.throwRemote(localException);
/* 2037:     */       }
/* 2038:2256 */       throw new EJBException(localException);
/* 2039:     */     }
/* 2040:     */     finally
/* 2041:     */     {
/* 2042:2260 */       this._comp.setCurrent(localJavaComponent);
/* 2043:     */     }
/* 2044:     */   }
/* 2045:     */   
/* 2046:     */   public IAEAlertDefinition updateAlert(int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
/* 2047:     */     throws RemoteException, AEException
/* 2048:     */   {
/* 2049:2274 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2050:2275 */     EJBContext localEJBContext = null;
/* 2051:     */     try
/* 2052:     */     {
/* 2053:2278 */       localEJBContext = this._comp.getPooledInstance();
/* 2054:2279 */       if (localEJBContext == null) {
/* 2055:2281 */         localEJBContext = _create();
/* 2056:     */       }
/* 2057:2283 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2058:2284 */       if (localEJBContext.debug) {
/* 2059:2286 */         localEJBContext.debug("updateAlert");
/* 2060:     */       }
/* 2061:2288 */       IAEAlertDefinition localIAEAlertDefinition2 = localAEAlertAdminBean
/* 2062:2289 */         .updateAlert(
/* 2063:2290 */         paramInt1, 
/* 2064:2291 */         paramString1, 
/* 2065:2292 */         paramInt2, 
/* 2066:2293 */         paramInt3, 
/* 2067:2294 */         (AEScheduleInfo)EJBContext.clone(paramAEScheduleInfo), 
/* 2068:2295 */         IAEDeliveryInfoSeqHelper.clone(paramArrayOfIAEDeliveryInfo), 
/* 2069:2296 */         paramString2);
/* 2070:     */       
/* 2071:2298 */       localIAEAlertDefinition2 = (IAEAlertDefinition)EJBContext.clone(localIAEAlertDefinition2);
/* 2072:2299 */       localEJBContext.returnToPool();
/* 2073:2300 */       return localIAEAlertDefinition2;
/* 2074:     */     }
/* 2075:     */     catch (Exception localException)
/* 2076:     */     {
/* 2077:2304 */       if (localEJBContext != null)
/* 2078:     */       {
/* 2079:2306 */         if ((localException instanceof AEException)) {
/* 2080:2308 */           throw ((AEException)EJBContext.clone(localException));
/* 2081:     */         }
/* 2082:2310 */         localEJBContext.throwRemote(localException);
/* 2083:     */       }
/* 2084:2312 */       throw new EJBException(localException);
/* 2085:     */     }
/* 2086:     */     finally
/* 2087:     */     {
/* 2088:2316 */       this._comp.setCurrent(localJavaComponent);
/* 2089:     */     }
/* 2090:     */   }
/* 2091:     */   
/* 2092:     */   public IAEAlertDefinition updateAlert(int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
/* 2093:     */     throws RemoteException, AEException
/* 2094:     */   {
/* 2095:2330 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2096:2331 */     EJBContext localEJBContext = null;
/* 2097:     */     try
/* 2098:     */     {
/* 2099:2334 */       localEJBContext = this._comp.getPooledInstance();
/* 2100:2335 */       if (localEJBContext == null) {
/* 2101:2337 */         localEJBContext = _create();
/* 2102:     */       }
/* 2103:2339 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2104:2340 */       if (localEJBContext.debug) {
/* 2105:2342 */         localEJBContext.debug("updateAlert");
/* 2106:     */       }
/* 2107:2344 */       IAEAlertDefinition localIAEAlertDefinition2 = localAEAlertAdminBean
/* 2108:2345 */         .updateAlert(
/* 2109:2346 */         paramInt1, 
/* 2110:2347 */         paramString, 
/* 2111:2348 */         paramInt2, 
/* 2112:2349 */         paramInt3, 
/* 2113:2350 */         (AEScheduleInfo)EJBContext.clone(paramAEScheduleInfo), 
/* 2114:2351 */         IAEDeliveryInfoSeqHelper.clone(paramArrayOfIAEDeliveryInfo), 
/* 2115:2352 */         BinaryHelper.clone(paramArrayOfByte));
/* 2116:     */       
/* 2117:2354 */       localIAEAlertDefinition2 = (IAEAlertDefinition)EJBContext.clone(localIAEAlertDefinition2);
/* 2118:2355 */       localEJBContext.returnToPool();
/* 2119:2356 */       return localIAEAlertDefinition2;
/* 2120:     */     }
/* 2121:     */     catch (Exception localException)
/* 2122:     */     {
/* 2123:2360 */       if (localEJBContext != null)
/* 2124:     */       {
/* 2125:2362 */         if ((localException instanceof AEException)) {
/* 2126:2364 */           throw ((AEException)EJBContext.clone(localException));
/* 2127:     */         }
/* 2128:2366 */         localEJBContext.throwRemote(localException);
/* 2129:     */       }
/* 2130:2368 */       throw new EJBException(localException);
/* 2131:     */     }
/* 2132:     */     finally
/* 2133:     */     {
/* 2134:2372 */       this._comp.setCurrent(localJavaComponent);
/* 2135:     */     }
/* 2136:     */   }
/* 2137:     */   
/* 2138:     */   public void cancelAlert(int paramInt)
/* 2139:     */     throws RemoteException, AEException
/* 2140:     */   {
/* 2141:2380 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2142:2381 */     EJBContext localEJBContext = null;
/* 2143:     */     try
/* 2144:     */     {
/* 2145:2384 */       localEJBContext = this._comp.getPooledInstance();
/* 2146:2385 */       if (localEJBContext == null) {
/* 2147:2387 */         localEJBContext = _create();
/* 2148:     */       }
/* 2149:2389 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2150:2390 */       if (localEJBContext.debug) {
/* 2151:2392 */         localEJBContext.debug("cancelAlert");
/* 2152:     */       }
/* 2153:2395 */       localAEAlertAdminBean.cancelAlert(
/* 2154:2396 */         paramInt);
/* 2155:     */       
/* 2156:2398 */       localEJBContext.returnToPool();
/* 2157:     */     }
/* 2158:     */     catch (Exception localException)
/* 2159:     */     {
/* 2160:2402 */       if (localEJBContext != null)
/* 2161:     */       {
/* 2162:2404 */         if ((localException instanceof AEException)) {
/* 2163:2406 */           throw ((AEException)EJBContext.clone(localException));
/* 2164:     */         }
/* 2165:2408 */         localEJBContext.throwRemote(localException);
/* 2166:     */       }
/* 2167:2410 */       throw new EJBException(localException);
/* 2168:     */     }
/* 2169:     */     finally
/* 2170:     */     {
/* 2171:2414 */       this._comp.setCurrent(localJavaComponent);
/* 2172:     */     }
/* 2173:     */   }
/* 2174:     */   
/* 2175:     */   public void cancelUserAlerts(String paramString1, String paramString2)
/* 2176:     */     throws RemoteException, AEException
/* 2177:     */   {
/* 2178:2423 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2179:2424 */     EJBContext localEJBContext = null;
/* 2180:     */     try
/* 2181:     */     {
/* 2182:2427 */       localEJBContext = this._comp.getPooledInstance();
/* 2183:2428 */       if (localEJBContext == null) {
/* 2184:2430 */         localEJBContext = _create();
/* 2185:     */       }
/* 2186:2432 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2187:2433 */       if (localEJBContext.debug) {
/* 2188:2435 */         localEJBContext.debug("cancelUserAlerts");
/* 2189:     */       }
/* 2190:2438 */       localAEAlertAdminBean.cancelUserAlerts(
/* 2191:2439 */         paramString1, 
/* 2192:2440 */         paramString2);
/* 2193:     */       
/* 2194:2442 */       localEJBContext.returnToPool();
/* 2195:     */     }
/* 2196:     */     catch (Exception localException)
/* 2197:     */     {
/* 2198:2446 */       if (localEJBContext != null)
/* 2199:     */       {
/* 2200:2448 */         if ((localException instanceof AEException)) {
/* 2201:2450 */           throw ((AEException)EJBContext.clone(localException));
/* 2202:     */         }
/* 2203:2452 */         localEJBContext.throwRemote(localException);
/* 2204:     */       }
/* 2205:2454 */       throw new EJBException(localException);
/* 2206:     */     }
/* 2207:     */     finally
/* 2208:     */     {
/* 2209:2458 */       this._comp.setCurrent(localJavaComponent);
/* 2210:     */     }
/* 2211:     */   }
/* 2212:     */   
/* 2213:     */   public IAEAuditInfo[] getAuditInfo(Date paramDate1, Date paramDate2)
/* 2214:     */     throws RemoteException, AEException
/* 2215:     */   {
/* 2216:2467 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2217:2468 */     EJBContext localEJBContext = null;
/* 2218:     */     try
/* 2219:     */     {
/* 2220:2471 */       localEJBContext = this._comp.getPooledInstance();
/* 2221:2472 */       if (localEJBContext == null) {
/* 2222:2474 */         localEJBContext = _create();
/* 2223:     */       }
/* 2224:2476 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2225:2477 */       if (localEJBContext.debug) {
/* 2226:2479 */         localEJBContext.debug("getAuditInfo");
/* 2227:     */       }
/* 2228:2481 */       IAEAuditInfo[] arrayOfIAEAuditInfo2 = localAEAlertAdminBean
/* 2229:2482 */         .getAuditInfo(
/* 2230:2483 */         EJBContext.clone(paramDate1), 
/* 2231:2484 */         EJBContext.clone(paramDate2));
/* 2232:     */       
/* 2233:2486 */       arrayOfIAEAuditInfo2 = IAEAuditInfoSeqHelper.clone(arrayOfIAEAuditInfo2);
/* 2234:2487 */       localEJBContext.returnToPool();
/* 2235:2488 */       return arrayOfIAEAuditInfo2;
/* 2236:     */     }
/* 2237:     */     catch (Exception localException)
/* 2238:     */     {
/* 2239:2492 */       if (localEJBContext != null)
/* 2240:     */       {
/* 2241:2494 */         if ((localException instanceof AEException)) {
/* 2242:2496 */           throw ((AEException)EJBContext.clone(localException));
/* 2243:     */         }
/* 2244:2498 */         localEJBContext.throwRemote(localException);
/* 2245:     */       }
/* 2246:2500 */       throw new EJBException(localException);
/* 2247:     */     }
/* 2248:     */     finally
/* 2249:     */     {
/* 2250:2504 */       this._comp.setCurrent(localJavaComponent);
/* 2251:     */     }
/* 2252:     */   }
/* 2253:     */   
/* 2254:     */   public IAEAuditInfo[] getAppAuditInfo(String paramString, Date paramDate1, Date paramDate2)
/* 2255:     */     throws RemoteException, AEException
/* 2256:     */   {
/* 2257:2514 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2258:2515 */     EJBContext localEJBContext = null;
/* 2259:     */     try
/* 2260:     */     {
/* 2261:2518 */       localEJBContext = this._comp.getPooledInstance();
/* 2262:2519 */       if (localEJBContext == null) {
/* 2263:2521 */         localEJBContext = _create();
/* 2264:     */       }
/* 2265:2523 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2266:2524 */       if (localEJBContext.debug) {
/* 2267:2526 */         localEJBContext.debug("getAppAuditInfo");
/* 2268:     */       }
/* 2269:2528 */       IAEAuditInfo[] arrayOfIAEAuditInfo2 = localAEAlertAdminBean
/* 2270:2529 */         .getAppAuditInfo(
/* 2271:2530 */         paramString, 
/* 2272:2531 */         EJBContext.clone(paramDate1), 
/* 2273:2532 */         EJBContext.clone(paramDate2));
/* 2274:     */       
/* 2275:2534 */       arrayOfIAEAuditInfo2 = IAEAuditInfoSeqHelper.clone(arrayOfIAEAuditInfo2);
/* 2276:2535 */       localEJBContext.returnToPool();
/* 2277:2536 */       return arrayOfIAEAuditInfo2;
/* 2278:     */     }
/* 2279:     */     catch (Exception localException)
/* 2280:     */     {
/* 2281:2540 */       if (localEJBContext != null)
/* 2282:     */       {
/* 2283:2542 */         if ((localException instanceof AEException)) {
/* 2284:2544 */           throw ((AEException)EJBContext.clone(localException));
/* 2285:     */         }
/* 2286:2546 */         localEJBContext.throwRemote(localException);
/* 2287:     */       }
/* 2288:2548 */       throw new EJBException(localException);
/* 2289:     */     }
/* 2290:     */     finally
/* 2291:     */     {
/* 2292:2552 */       this._comp.setCurrent(localJavaComponent);
/* 2293:     */     }
/* 2294:     */   }
/* 2295:     */   
/* 2296:     */   public IAEAuditInfo[] getUserAuditInfo(String paramString1, String paramString2, Date paramDate1, Date paramDate2)
/* 2297:     */     throws RemoteException, AEException
/* 2298:     */   {
/* 2299:2563 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2300:2564 */     EJBContext localEJBContext = null;
/* 2301:     */     try
/* 2302:     */     {
/* 2303:2567 */       localEJBContext = this._comp.getPooledInstance();
/* 2304:2568 */       if (localEJBContext == null) {
/* 2305:2570 */         localEJBContext = _create();
/* 2306:     */       }
/* 2307:2572 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2308:2573 */       if (localEJBContext.debug) {
/* 2309:2575 */         localEJBContext.debug("getUserAuditInfo");
/* 2310:     */       }
/* 2311:2577 */       IAEAuditInfo[] arrayOfIAEAuditInfo2 = localAEAlertAdminBean
/* 2312:2578 */         .getUserAuditInfo(
/* 2313:2579 */         paramString1, 
/* 2314:2580 */         paramString2, 
/* 2315:2581 */         EJBContext.clone(paramDate1), 
/* 2316:2582 */         EJBContext.clone(paramDate2));
/* 2317:     */       
/* 2318:2584 */       arrayOfIAEAuditInfo2 = IAEAuditInfoSeqHelper.clone(arrayOfIAEAuditInfo2);
/* 2319:2585 */       localEJBContext.returnToPool();
/* 2320:2586 */       return arrayOfIAEAuditInfo2;
/* 2321:     */     }
/* 2322:     */     catch (Exception localException)
/* 2323:     */     {
/* 2324:2590 */       if (localEJBContext != null)
/* 2325:     */       {
/* 2326:2592 */         if ((localException instanceof AEException)) {
/* 2327:2594 */           throw ((AEException)EJBContext.clone(localException));
/* 2328:     */         }
/* 2329:2596 */         localEJBContext.throwRemote(localException);
/* 2330:     */       }
/* 2331:2598 */       throw new EJBException(localException);
/* 2332:     */     }
/* 2333:     */     finally
/* 2334:     */     {
/* 2335:2602 */       this._comp.setCurrent(localJavaComponent);
/* 2336:     */     }
/* 2337:     */   }
/* 2338:     */   
/* 2339:     */   public IAEAuditInfo[] getUserAuditInfo(String paramString, Date paramDate1, Date paramDate2)
/* 2340:     */     throws RemoteException, AEException
/* 2341:     */   {
/* 2342:2612 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2343:2613 */     EJBContext localEJBContext = null;
/* 2344:     */     try
/* 2345:     */     {
/* 2346:2616 */       localEJBContext = this._comp.getPooledInstance();
/* 2347:2617 */       if (localEJBContext == null) {
/* 2348:2619 */         localEJBContext = _create();
/* 2349:     */       }
/* 2350:2621 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2351:2622 */       if (localEJBContext.debug) {
/* 2352:2624 */         localEJBContext.debug("getUserAuditInfo");
/* 2353:     */       }
/* 2354:2626 */       IAEAuditInfo[] arrayOfIAEAuditInfo2 = localAEAlertAdminBean
/* 2355:2627 */         .getUserAuditInfo(
/* 2356:2628 */         paramString, 
/* 2357:2629 */         EJBContext.clone(paramDate1), 
/* 2358:2630 */         EJBContext.clone(paramDate2));
/* 2359:     */       
/* 2360:2632 */       arrayOfIAEAuditInfo2 = IAEAuditInfoSeqHelper.clone(arrayOfIAEAuditInfo2);
/* 2361:2633 */       localEJBContext.returnToPool();
/* 2362:2634 */       return arrayOfIAEAuditInfo2;
/* 2363:     */     }
/* 2364:     */     catch (Exception localException)
/* 2365:     */     {
/* 2366:2638 */       if (localEJBContext != null)
/* 2367:     */       {
/* 2368:2640 */         if ((localException instanceof AEException)) {
/* 2369:2642 */           throw ((AEException)EJBContext.clone(localException));
/* 2370:     */         }
/* 2371:2644 */         localEJBContext.throwRemote(localException);
/* 2372:     */       }
/* 2373:2646 */       throw new EJBException(localException);
/* 2374:     */     }
/* 2375:     */     finally
/* 2376:     */     {
/* 2377:2650 */       this._comp.setCurrent(localJavaComponent);
/* 2378:     */     }
/* 2379:     */   }
/* 2380:     */   
/* 2381:     */   public void setEngineProperties(Properties paramProperties)
/* 2382:     */     throws RemoteException, AEException
/* 2383:     */   {
/* 2384:2658 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2385:2659 */     EJBContext localEJBContext = null;
/* 2386:     */     try
/* 2387:     */     {
/* 2388:2662 */       localEJBContext = this._comp.getPooledInstance();
/* 2389:2663 */       if (localEJBContext == null) {
/* 2390:2665 */         localEJBContext = _create();
/* 2391:     */       }
/* 2392:2667 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2393:2668 */       if (localEJBContext.debug) {
/* 2394:2670 */         localEJBContext.debug("setEngineProperties");
/* 2395:     */       }
/* 2396:2673 */       localAEAlertAdminBean.setEngineProperties(
/* 2397:2674 */         (Properties)EJBContext.clone(paramProperties));
/* 2398:     */       
/* 2399:2676 */       localEJBContext.returnToPool();
/* 2400:     */     }
/* 2401:     */     catch (Exception localException)
/* 2402:     */     {
/* 2403:2680 */       if (localEJBContext != null)
/* 2404:     */       {
/* 2405:2682 */         if ((localException instanceof AEException)) {
/* 2406:2684 */           throw ((AEException)EJBContext.clone(localException));
/* 2407:     */         }
/* 2408:2686 */         localEJBContext.throwRemote(localException);
/* 2409:     */       }
/* 2410:2688 */       throw new EJBException(localException);
/* 2411:     */     }
/* 2412:     */     finally
/* 2413:     */     {
/* 2414:2692 */       this._comp.setCurrent(localJavaComponent);
/* 2415:     */     }
/* 2416:     */   }
/* 2417:     */   
/* 2418:     */   public Properties getEngineProperties()
/* 2419:     */     throws RemoteException, AEException
/* 2420:     */   {
/* 2421:2699 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2422:2700 */     EJBContext localEJBContext = null;
/* 2423:     */     try
/* 2424:     */     {
/* 2425:2703 */       localEJBContext = this._comp.getPooledInstance();
/* 2426:2704 */       if (localEJBContext == null) {
/* 2427:2706 */         localEJBContext = _create();
/* 2428:     */       }
/* 2429:2708 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2430:2709 */       if (localEJBContext.debug) {
/* 2431:2711 */         localEJBContext.debug("getEngineProperties");
/* 2432:     */       }
/* 2433:2713 */       Properties localProperties2 = localAEAlertAdminBean
/* 2434:2714 */         .getEngineProperties();
/* 2435:     */       
/* 2436:2716 */       localProperties2 = (Properties)EJBContext.clone(localProperties2);
/* 2437:2717 */       localEJBContext.returnToPool();
/* 2438:2718 */       return localProperties2;
/* 2439:     */     }
/* 2440:     */     catch (Exception localException)
/* 2441:     */     {
/* 2442:2722 */       if (localEJBContext != null)
/* 2443:     */       {
/* 2444:2724 */         if ((localException instanceof AEException)) {
/* 2445:2726 */           throw ((AEException)EJBContext.clone(localException));
/* 2446:     */         }
/* 2447:2728 */         localEJBContext.throwRemote(localException);
/* 2448:     */       }
/* 2449:2730 */       throw new EJBException(localException);
/* 2450:     */     }
/* 2451:     */     finally
/* 2452:     */     {
/* 2453:2734 */       this._comp.setCurrent(localJavaComponent);
/* 2454:     */     }
/* 2455:     */   }
/* 2456:     */   
/* 2457:     */   public AEServerInfo updateServer(String paramString, AEServerInfo paramAEServerInfo)
/* 2458:     */     throws RemoteException, AEException
/* 2459:     */   {
/* 2460:2743 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2461:2744 */     EJBContext localEJBContext = null;
/* 2462:     */     try
/* 2463:     */     {
/* 2464:2747 */       localEJBContext = this._comp.getPooledInstance();
/* 2465:2748 */       if (localEJBContext == null) {
/* 2466:2750 */         localEJBContext = _create();
/* 2467:     */       }
/* 2468:2752 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2469:2753 */       if (localEJBContext.debug) {
/* 2470:2755 */         localEJBContext.debug("updateServer");
/* 2471:     */       }
/* 2472:2757 */       AEServerInfo localAEServerInfo2 = localAEAlertAdminBean
/* 2473:2758 */         .updateServer(
/* 2474:2759 */         paramString, 
/* 2475:2760 */         (AEServerInfo)EJBContext.clone(paramAEServerInfo));
/* 2476:     */       
/* 2477:2762 */       localAEServerInfo2 = (AEServerInfo)EJBContext.clone(localAEServerInfo2);
/* 2478:2763 */       localEJBContext.returnToPool();
/* 2479:2764 */       return localAEServerInfo2;
/* 2480:     */     }
/* 2481:     */     catch (Exception localException)
/* 2482:     */     {
/* 2483:2768 */       if (localEJBContext != null)
/* 2484:     */       {
/* 2485:2770 */         if ((localException instanceof AEException)) {
/* 2486:2772 */           throw ((AEException)EJBContext.clone(localException));
/* 2487:     */         }
/* 2488:2774 */         localEJBContext.throwRemote(localException);
/* 2489:     */       }
/* 2490:2776 */       throw new EJBException(localException);
/* 2491:     */     }
/* 2492:     */     finally
/* 2493:     */     {
/* 2494:2780 */       this._comp.setCurrent(localJavaComponent);
/* 2495:     */     }
/* 2496:     */   }
/* 2497:     */   
/* 2498:     */   public void setPrimaryServer(String paramString)
/* 2499:     */     throws RemoteException, AEException
/* 2500:     */   {
/* 2501:2788 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2502:2789 */     EJBContext localEJBContext = null;
/* 2503:     */     try
/* 2504:     */     {
/* 2505:2792 */       localEJBContext = this._comp.getPooledInstance();
/* 2506:2793 */       if (localEJBContext == null) {
/* 2507:2795 */         localEJBContext = _create();
/* 2508:     */       }
/* 2509:2797 */       AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)localEJBContext.getBean();
/* 2510:2798 */       if (localEJBContext.debug) {
/* 2511:2800 */         localEJBContext.debug("setPrimaryServer");
/* 2512:     */       }
/* 2513:2803 */       localAEAlertAdminBean.setPrimaryServer(
/* 2514:2804 */         paramString);
/* 2515:     */       
/* 2516:2806 */       localEJBContext.returnToPool();
/* 2517:     */     }
/* 2518:     */     catch (Exception localException)
/* 2519:     */     {
/* 2520:2810 */       if (localEJBContext != null)
/* 2521:     */       {
/* 2522:2812 */         if ((localException instanceof AEException)) {
/* 2523:2814 */           throw ((AEException)EJBContext.clone(localException));
/* 2524:     */         }
/* 2525:2816 */         localEJBContext.throwRemote(localException);
/* 2526:     */       }
/* 2527:2818 */       throw new EJBException(localException);
/* 2528:     */     }
/* 2529:     */     finally
/* 2530:     */     {
/* 2531:2822 */       this._comp.setCurrent(localJavaComponent);
/* 2532:     */     }
/* 2533:     */   }
/* 2534:     */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB._lwc_rs_UAEAdminEJB20_UAEAdminBean
 * JD-Core Version:    0.7.0.1
 */