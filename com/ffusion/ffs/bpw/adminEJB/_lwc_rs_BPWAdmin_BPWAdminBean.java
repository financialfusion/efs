/*    1:     */ package com.ffusion.ffs.bpw.adminEJB;
/*    2:     */ 
/*    3:     */ import com.ffusion.ffs.bpw.interfaces.BPWStatistics;
/*    4:     */ import com.ffusion.ffs.bpw.interfaces.BPWStatisticsHelper;
/*    5:     */ import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList;
/*    6:     */ import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
/*    7:     */ import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
/*    8:     */ import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
/*    9:     */ import com.ffusion.ffs.bpw.interfaces.FulfillmentInfoSeqHelper;
/*   10:     */ import com.ffusion.ffs.bpw.interfaces.InstructionType;
/*   11:     */ import com.ffusion.ffs.bpw.interfaces.InstructionTypeSeqHelper;
/*   12:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*   13:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfoSeqHelper;
/*   14:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
/*   15:     */ import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
/*   16:     */ import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
/*   18:     */ import com.ffusion.ffs.bpw.interfaces.PropertyConfigHelper;
/*   19:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleActivityList;
/*   20:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
/*   21:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
/*   22:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleHistHelper;
/*   23:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleHistSeqHelper;
/*   24:     */ import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
/*   25:     */ import com.ffusion.ffs.bpw.interfaces.SchedulerInfoSeqHelper;
/*   26:     */ import com.ffusion.ffs.bpw.interfaces.SmartCalendarFile;
/*   27:     */ import com.ffusion.ffs.db.FFSDBProperties;
/*   28:     */ import com.ffusion.ffs.db.FFSDBPropertiesHelper;
/*   29:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   30:     */ import com.ffusion.ffs.util.FFSProperties;
/*   31:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   32:     */ import com.sybase.ejb.lwc.EJBContext;
/*   33:     */ import com.sybase.ejb.lwc.EJBObject;
/*   34:     */ import com.sybase.ejb.lwc.JavaComponent;
/*   35:     */ import com.sybase.ejb.lwc.Stub;
/*   36:     */ import com.sybase.jaguar.logger.Logger;
/*   37:     */ import java.rmi.RemoteException;
/*   38:     */ import java.util.ArrayList;
/*   39:     */ import java.util.HashMap;
/*   40:     */ import javax.ejb.EJBException;
/*   41:     */ 
/*   42:     */ public class _lwc_rs_BPWAdmin_BPWAdminBean
/*   43:     */   extends EJBObject
/*   44:     */   implements IBPWAdmin
/*   45:     */ {
/*   46:  14 */   private static JavaComponent __component = JavaComponent.get("BPWAdmin/BPWAdminBean");
/*   47:     */   
/*   48:     */   public _lwc_rs_BPWAdmin_BPWAdminBean(Object paramObject)
/*   49:     */   {
/*   50:  19 */     super(__component);
/*   51:  20 */     this.key = paramObject;
/*   52:     */   }
/*   53:     */   
/*   54:     */   private EJBContext _create()
/*   55:     */     throws Exception
/*   56:     */   {
/*   57:  26 */     EJBContext localEJBContext = this._comp.getInstance();
/*   58:  27 */     BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*   59:  28 */     if (localEJBContext.debug) {
/*   60:  30 */       localEJBContext.logger.debug(localEJBContext.debugMsg("ejbCreate"));
/*   61:     */     }
/*   62:  32 */     localBPWAdminBean.ejbCreate();
/*   63:  33 */     return localEJBContext;
/*   64:     */   }
/*   65:     */   
/*   66:     */   public void start(PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
/*   67:     */     throws RemoteException, FFSException
/*   68:     */   {
/*   69:  41 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*   70:  42 */     EJBContext localEJBContext = null;
/*   71:     */     try
/*   72:     */     {
/*   73:  45 */       localEJBContext = this._comp.getPooledInstance();
/*   74:  46 */       if (localEJBContext == null) {
/*   75:  48 */         localEJBContext = _create();
/*   76:     */       }
/*   77:  50 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*   78:  51 */       if (localEJBContext.debug) {
/*   79:  53 */         localEJBContext.logger.debug(localEJBContext.debugMsg("start"));
/*   80:     */       }
/*   81:  56 */       localBPWAdminBean.start(
/*   82:  57 */         PropertyConfigHelper.clone(paramPropertyConfig), 
/*   83:  58 */         InstructionTypeSeqHelper.clone(paramArrayOfInstructionType));
/*   84:     */       
/*   85:  60 */       localEJBContext.returnToPool();
/*   86:     */     }
/*   87:     */     catch (Exception localException)
/*   88:     */     {
/*   89:  64 */       if (localEJBContext != null)
/*   90:     */       {
/*   91:  66 */         if ((localException instanceof FFSException)) {
/*   92:  68 */           throw ((FFSException)EJBContext.clone(localException));
/*   93:     */         }
/*   94:  70 */         localEJBContext.throwRemote(localException);
/*   95:     */       }
/*   96:  72 */       throw new EJBException(localException);
/*   97:     */     }
/*   98:     */     finally
/*   99:     */     {
/*  100:  76 */       this._comp.setCurrent(localJavaComponent);
/*  101:     */     }
/*  102:     */   }
/*  103:     */   
/*  104:     */   public void start(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
/*  105:     */     throws RemoteException, FFSException
/*  106:     */   {
/*  107:  86 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  108:  87 */     EJBContext localEJBContext = null;
/*  109:     */     try
/*  110:     */     {
/*  111:  90 */       localEJBContext = this._comp.getPooledInstance();
/*  112:  91 */       if (localEJBContext == null) {
/*  113:  93 */         localEJBContext = _create();
/*  114:     */       }
/*  115:  95 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  116:  96 */       if (localEJBContext.debug) {
/*  117:  98 */         localEJBContext.logger.debug(localEJBContext.debugMsg("start"));
/*  118:     */       }
/*  119: 101 */       localBPWAdminBean.start(
/*  120: 102 */         (FFSProperties)EJBContext.clone(paramFFSProperties), 
/*  121: 103 */         PropertyConfigHelper.clone(paramPropertyConfig), 
/*  122: 104 */         InstructionTypeSeqHelper.clone(paramArrayOfInstructionType));
/*  123:     */       
/*  124: 106 */       localEJBContext.returnToPool();
/*  125:     */     }
/*  126:     */     catch (Exception localException)
/*  127:     */     {
/*  128: 110 */       if (localEJBContext != null)
/*  129:     */       {
/*  130: 112 */         if ((localException instanceof FFSException)) {
/*  131: 114 */           throw ((FFSException)EJBContext.clone(localException));
/*  132:     */         }
/*  133: 116 */         localEJBContext.throwRemote(localException);
/*  134:     */       }
/*  135: 118 */       throw new EJBException(localException);
/*  136:     */     }
/*  137:     */     finally
/*  138:     */     {
/*  139: 122 */       this._comp.setCurrent(localJavaComponent);
/*  140:     */     }
/*  141:     */   }
/*  142:     */   
/*  143:     */   public void stop()
/*  144:     */     throws RemoteException, FFSException
/*  145:     */   {
/*  146: 129 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  147: 130 */     EJBContext localEJBContext = null;
/*  148:     */     try
/*  149:     */     {
/*  150: 133 */       localEJBContext = this._comp.getPooledInstance();
/*  151: 134 */       if (localEJBContext == null) {
/*  152: 136 */         localEJBContext = _create();
/*  153:     */       }
/*  154: 138 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  155: 139 */       if (localEJBContext.debug) {
/*  156: 141 */         localEJBContext.logger.debug(localEJBContext.debugMsg("stop"));
/*  157:     */       }
/*  158: 144 */       localBPWAdminBean.stop();
/*  159:     */       
/*  160: 146 */       localEJBContext.returnToPool();
/*  161:     */     }
/*  162:     */     catch (Exception localException)
/*  163:     */     {
/*  164: 150 */       if (localEJBContext != null)
/*  165:     */       {
/*  166: 152 */         if ((localException instanceof FFSException)) {
/*  167: 154 */           throw ((FFSException)EJBContext.clone(localException));
/*  168:     */         }
/*  169: 156 */         localEJBContext.throwRemote(localException);
/*  170:     */       }
/*  171: 158 */       throw new EJBException(localException);
/*  172:     */     }
/*  173:     */     finally
/*  174:     */     {
/*  175: 162 */       this._comp.setCurrent(localJavaComponent);
/*  176:     */     }
/*  177:     */   }
/*  178:     */   
/*  179:     */   public void cleanup(String paramString, ArrayList paramArrayList1, ArrayList paramArrayList2, HashMap paramHashMap)
/*  180:     */     throws RemoteException
/*  181:     */   {
/*  182: 173 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  183: 174 */     EJBContext localEJBContext = null;
/*  184:     */     try
/*  185:     */     {
/*  186: 177 */       localEJBContext = this._comp.getPooledInstance();
/*  187: 178 */       if (localEJBContext == null) {
/*  188: 180 */         localEJBContext = _create();
/*  189:     */       }
/*  190: 182 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  191: 183 */       if (localEJBContext.debug) {
/*  192: 185 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cleanup"));
/*  193:     */       }
/*  194: 188 */       localBPWAdminBean.cleanup(
/*  195: 189 */         paramString, 
/*  196: 190 */         (ArrayList)EJBContext.clone(paramArrayList1), 
/*  197: 191 */         (ArrayList)EJBContext.clone(paramArrayList2), 
/*  198: 192 */         (HashMap)EJBContext.clone(paramHashMap));
/*  199:     */       
/*  200: 194 */       localEJBContext.returnToPool();
/*  201:     */     }
/*  202:     */     catch (Exception localException)
/*  203:     */     {
/*  204: 198 */       if (localEJBContext != null) {
/*  205: 200 */         localEJBContext.throwRemote(localException);
/*  206:     */       }
/*  207: 202 */       throw new EJBException(localException);
/*  208:     */     }
/*  209:     */     finally
/*  210:     */     {
/*  211: 206 */       this._comp.setCurrent(localJavaComponent);
/*  212:     */     }
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void cleanup(ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, HashMap paramHashMap)
/*  216:     */     throws RemoteException
/*  217:     */   {
/*  218: 217 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  219: 218 */     EJBContext localEJBContext = null;
/*  220:     */     try
/*  221:     */     {
/*  222: 221 */       localEJBContext = this._comp.getPooledInstance();
/*  223: 222 */       if (localEJBContext == null) {
/*  224: 224 */         localEJBContext = _create();
/*  225:     */       }
/*  226: 226 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  227: 227 */       if (localEJBContext.debug) {
/*  228: 229 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cleanup"));
/*  229:     */       }
/*  230: 232 */       localBPWAdminBean.cleanup(
/*  231: 233 */         (ArrayList)EJBContext.clone(paramArrayList1), 
/*  232: 234 */         (ArrayList)EJBContext.clone(paramArrayList2), 
/*  233: 235 */         (ArrayList)EJBContext.clone(paramArrayList3), 
/*  234: 236 */         (HashMap)EJBContext.clone(paramHashMap));
/*  235:     */       
/*  236: 238 */       localEJBContext.returnToPool();
/*  237:     */     }
/*  238:     */     catch (Exception localException)
/*  239:     */     {
/*  240: 242 */       if (localEJBContext != null) {
/*  241: 244 */         localEJBContext.throwRemote(localException);
/*  242:     */       }
/*  243: 246 */       throw new EJBException(localException);
/*  244:     */     }
/*  245:     */     finally
/*  246:     */     {
/*  247: 250 */       this._comp.setCurrent(localJavaComponent);
/*  248:     */     }
/*  249:     */   }
/*  250:     */   
/*  251:     */   public void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
/*  252:     */     throws RemoteException
/*  253:     */   {
/*  254: 261 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  255: 262 */     EJBContext localEJBContext = null;
/*  256:     */     try
/*  257:     */     {
/*  258: 265 */       localEJBContext = this._comp.getPooledInstance();
/*  259: 266 */       if (localEJBContext == null) {
/*  260: 268 */         localEJBContext = _create();
/*  261:     */       }
/*  262: 270 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  263: 271 */       if (localEJBContext.debug) {
/*  264: 273 */         localEJBContext.logger.debug(localEJBContext.debugMsg("cleanup"));
/*  265:     */       }
/*  266: 276 */       localBPWAdminBean.cleanup(
/*  267: 277 */         paramString1, 
/*  268: 278 */         paramString2, 
/*  269: 279 */         paramInt, 
/*  270: 280 */         (HashMap)EJBContext.clone(paramHashMap));
/*  271:     */       
/*  272: 282 */       localEJBContext.returnToPool();
/*  273:     */     }
/*  274:     */     catch (Exception localException)
/*  275:     */     {
/*  276: 286 */       if (localEJBContext != null) {
/*  277: 288 */         localEJBContext.throwRemote(localException);
/*  278:     */       }
/*  279: 290 */       throw new EJBException(localException);
/*  280:     */     }
/*  281:     */     finally
/*  282:     */     {
/*  283: 294 */       this._comp.setCurrent(localJavaComponent);
/*  284:     */     }
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void refresh(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
/*  288:     */     throws RemoteException, FFSException
/*  289:     */   {
/*  290: 304 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  291: 305 */     EJBContext localEJBContext = null;
/*  292:     */     try
/*  293:     */     {
/*  294: 308 */       localEJBContext = this._comp.getPooledInstance();
/*  295: 309 */       if (localEJBContext == null) {
/*  296: 311 */         localEJBContext = _create();
/*  297:     */       }
/*  298: 313 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  299: 314 */       if (localEJBContext.debug) {
/*  300: 316 */         localEJBContext.logger.debug(localEJBContext.debugMsg("refresh"));
/*  301:     */       }
/*  302: 319 */       localBPWAdminBean.refresh(
/*  303: 320 */         (FFSProperties)EJBContext.clone(paramFFSProperties), 
/*  304: 321 */         PropertyConfigHelper.clone(paramPropertyConfig), 
/*  305: 322 */         InstructionTypeSeqHelper.clone(paramArrayOfInstructionType));
/*  306:     */       
/*  307: 324 */       localEJBContext.returnToPool();
/*  308:     */     }
/*  309:     */     catch (Exception localException)
/*  310:     */     {
/*  311: 328 */       if (localEJBContext != null)
/*  312:     */       {
/*  313: 330 */         if ((localException instanceof FFSException)) {
/*  314: 332 */           throw ((FFSException)EJBContext.clone(localException));
/*  315:     */         }
/*  316: 334 */         localEJBContext.throwRemote(localException);
/*  317:     */       }
/*  318: 336 */       throw new EJBException(localException);
/*  319:     */     }
/*  320:     */     finally
/*  321:     */     {
/*  322: 340 */       this._comp.setCurrent(localJavaComponent);
/*  323:     */     }
/*  324:     */   }
/*  325:     */   
/*  326:     */   public void refresh(PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
/*  327:     */     throws RemoteException, FFSException
/*  328:     */   {
/*  329: 349 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  330: 350 */     EJBContext localEJBContext = null;
/*  331:     */     try
/*  332:     */     {
/*  333: 353 */       localEJBContext = this._comp.getPooledInstance();
/*  334: 354 */       if (localEJBContext == null) {
/*  335: 356 */         localEJBContext = _create();
/*  336:     */       }
/*  337: 358 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  338: 359 */       if (localEJBContext.debug) {
/*  339: 361 */         localEJBContext.logger.debug(localEJBContext.debugMsg("refresh"));
/*  340:     */       }
/*  341: 364 */       localBPWAdminBean.refresh(
/*  342: 365 */         PropertyConfigHelper.clone(paramPropertyConfig), 
/*  343: 366 */         InstructionTypeSeqHelper.clone(paramArrayOfInstructionType));
/*  344:     */       
/*  345: 368 */       localEJBContext.returnToPool();
/*  346:     */     }
/*  347:     */     catch (Exception localException)
/*  348:     */     {
/*  349: 372 */       if (localEJBContext != null)
/*  350:     */       {
/*  351: 374 */         if ((localException instanceof FFSException)) {
/*  352: 376 */           throw ((FFSException)EJBContext.clone(localException));
/*  353:     */         }
/*  354: 378 */         localEJBContext.throwRemote(localException);
/*  355:     */       }
/*  356: 380 */       throw new EJBException(localException);
/*  357:     */     }
/*  358:     */     finally
/*  359:     */     {
/*  360: 384 */       this._comp.setCurrent(localJavaComponent);
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   public boolean ping()
/*  365:     */     throws RemoteException, FFSException
/*  366:     */   {
/*  367: 391 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  368: 392 */     EJBContext localEJBContext = null;
/*  369:     */     try
/*  370:     */     {
/*  371: 395 */       localEJBContext = this._comp.getPooledInstance();
/*  372: 396 */       if (localEJBContext == null) {
/*  373: 398 */         localEJBContext = _create();
/*  374:     */       }
/*  375: 400 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  376: 401 */       if (localEJBContext.debug) {
/*  377: 403 */         localEJBContext.logger.debug(localEJBContext.debugMsg("ping"));
/*  378:     */       }
/*  379: 405 */       boolean bool2 = localBPWAdminBean
/*  380: 406 */         .ping();
/*  381:     */       
/*  382: 408 */       localEJBContext.returnToPool();
/*  383: 409 */       return bool2;
/*  384:     */     }
/*  385:     */     catch (Exception localException)
/*  386:     */     {
/*  387: 413 */       if (localEJBContext != null)
/*  388:     */       {
/*  389: 415 */         if ((localException instanceof FFSException)) {
/*  390: 417 */           throw ((FFSException)EJBContext.clone(localException));
/*  391:     */         }
/*  392: 419 */         localEJBContext.throwRemote(localException);
/*  393:     */       }
/*  394: 421 */       throw new EJBException(localException);
/*  395:     */     }
/*  396:     */     finally
/*  397:     */     {
/*  398: 425 */       this._comp.setCurrent(localJavaComponent);
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public BPWStatistics getStatistics()
/*  403:     */     throws RemoteException, FFSException
/*  404:     */   {
/*  405: 432 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  406: 433 */     EJBContext localEJBContext = null;
/*  407:     */     try
/*  408:     */     {
/*  409: 436 */       localEJBContext = this._comp.getPooledInstance();
/*  410: 437 */       if (localEJBContext == null) {
/*  411: 439 */         localEJBContext = _create();
/*  412:     */       }
/*  413: 441 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  414: 442 */       if (localEJBContext.debug) {
/*  415: 444 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getStatistics"));
/*  416:     */       }
/*  417: 446 */       BPWStatistics localBPWStatistics2 = localBPWAdminBean
/*  418: 447 */         .getStatistics();
/*  419:     */       
/*  420: 449 */       localBPWStatistics2 = BPWStatisticsHelper.clone(localBPWStatistics2);
/*  421: 450 */       localEJBContext.returnToPool();
/*  422: 451 */       return localBPWStatistics2;
/*  423:     */     }
/*  424:     */     catch (Exception localException)
/*  425:     */     {
/*  426: 455 */       if (localEJBContext != null)
/*  427:     */       {
/*  428: 457 */         if ((localException instanceof FFSException)) {
/*  429: 459 */           throw ((FFSException)EJBContext.clone(localException));
/*  430:     */         }
/*  431: 461 */         localEJBContext.throwRemote(localException);
/*  432:     */       }
/*  433: 463 */       throw new EJBException(localException);
/*  434:     */     }
/*  435:     */     finally
/*  436:     */     {
/*  437: 467 */       this._comp.setCurrent(localJavaComponent);
/*  438:     */     }
/*  439:     */   }
/*  440:     */   
/*  441:     */   public void refreshSmartCalendar()
/*  442:     */     throws RemoteException, FFSException
/*  443:     */   {
/*  444: 474 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  445: 475 */     EJBContext localEJBContext = null;
/*  446:     */     try
/*  447:     */     {
/*  448: 478 */       localEJBContext = this._comp.getPooledInstance();
/*  449: 479 */       if (localEJBContext == null) {
/*  450: 481 */         localEJBContext = _create();
/*  451:     */       }
/*  452: 483 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  453: 484 */       if (localEJBContext.debug) {
/*  454: 486 */         localEJBContext.logger.debug(localEJBContext.debugMsg("refreshSmartCalendar"));
/*  455:     */       }
/*  456: 489 */       localBPWAdminBean.refreshSmartCalendar();
/*  457:     */       
/*  458: 491 */       localEJBContext.returnToPool();
/*  459:     */     }
/*  460:     */     catch (Exception localException)
/*  461:     */     {
/*  462: 495 */       if (localEJBContext != null)
/*  463:     */       {
/*  464: 497 */         if ((localException instanceof FFSException)) {
/*  465: 499 */           throw ((FFSException)EJBContext.clone(localException));
/*  466:     */         }
/*  467: 501 */         localEJBContext.throwRemote(localException);
/*  468:     */       }
/*  469: 503 */       throw new EJBException(localException);
/*  470:     */     }
/*  471:     */     finally
/*  472:     */     {
/*  473: 507 */       this._comp.setCurrent(localJavaComponent);
/*  474:     */     }
/*  475:     */   }
/*  476:     */   
/*  477:     */   public long getFreeMem()
/*  478:     */     throws RemoteException, FFSException
/*  479:     */   {
/*  480: 514 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  481: 515 */     EJBContext localEJBContext = null;
/*  482:     */     try
/*  483:     */     {
/*  484: 518 */       localEJBContext = this._comp.getPooledInstance();
/*  485: 519 */       if (localEJBContext == null) {
/*  486: 521 */         localEJBContext = _create();
/*  487:     */       }
/*  488: 523 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  489: 524 */       if (localEJBContext.debug) {
/*  490: 526 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getFreeMem"));
/*  491:     */       }
/*  492: 528 */       long l2 = localBPWAdminBean
/*  493: 529 */         .getFreeMem();
/*  494:     */       
/*  495: 531 */       localEJBContext.returnToPool();
/*  496: 532 */       return l2;
/*  497:     */     }
/*  498:     */     catch (Exception localException)
/*  499:     */     {
/*  500: 536 */       if (localEJBContext != null)
/*  501:     */       {
/*  502: 538 */         if ((localException instanceof FFSException)) {
/*  503: 540 */           throw ((FFSException)EJBContext.clone(localException));
/*  504:     */         }
/*  505: 542 */         localEJBContext.throwRemote(localException);
/*  506:     */       }
/*  507: 544 */       throw new EJBException(localException);
/*  508:     */     }
/*  509:     */     finally
/*  510:     */     {
/*  511: 548 */       this._comp.setCurrent(localJavaComponent);
/*  512:     */     }
/*  513:     */   }
/*  514:     */   
/*  515:     */   public long getTotalMem()
/*  516:     */     throws RemoteException, FFSException
/*  517:     */   {
/*  518: 555 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  519: 556 */     EJBContext localEJBContext = null;
/*  520:     */     try
/*  521:     */     {
/*  522: 559 */       localEJBContext = this._comp.getPooledInstance();
/*  523: 560 */       if (localEJBContext == null) {
/*  524: 562 */         localEJBContext = _create();
/*  525:     */       }
/*  526: 564 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  527: 565 */       if (localEJBContext.debug) {
/*  528: 567 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getTotalMem"));
/*  529:     */       }
/*  530: 569 */       long l2 = localBPWAdminBean
/*  531: 570 */         .getTotalMem();
/*  532:     */       
/*  533: 572 */       localEJBContext.returnToPool();
/*  534: 573 */       return l2;
/*  535:     */     }
/*  536:     */     catch (Exception localException)
/*  537:     */     {
/*  538: 577 */       if (localEJBContext != null)
/*  539:     */       {
/*  540: 579 */         if ((localException instanceof FFSException)) {
/*  541: 581 */           throw ((FFSException)EJBContext.clone(localException));
/*  542:     */         }
/*  543: 583 */         localEJBContext.throwRemote(localException);
/*  544:     */       }
/*  545: 585 */       throw new EJBException(localException);
/*  546:     */     }
/*  547:     */     finally
/*  548:     */     {
/*  549: 589 */       this._comp.setCurrent(localJavaComponent);
/*  550:     */     }
/*  551:     */   }
/*  552:     */   
/*  553:     */   public double getHeapUsage()
/*  554:     */     throws RemoteException, FFSException
/*  555:     */   {
/*  556: 596 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  557: 597 */     EJBContext localEJBContext = null;
/*  558:     */     try
/*  559:     */     {
/*  560: 600 */       localEJBContext = this._comp.getPooledInstance();
/*  561: 601 */       if (localEJBContext == null) {
/*  562: 603 */         localEJBContext = _create();
/*  563:     */       }
/*  564: 605 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  565: 606 */       if (localEJBContext.debug) {
/*  566: 608 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getHeapUsage"));
/*  567:     */       }
/*  568: 610 */       double d2 = localBPWAdminBean
/*  569: 611 */         .getHeapUsage();
/*  570:     */       
/*  571: 613 */       localEJBContext.returnToPool();
/*  572: 614 */       return d2;
/*  573:     */     }
/*  574:     */     catch (Exception localException)
/*  575:     */     {
/*  576: 618 */       if (localEJBContext != null)
/*  577:     */       {
/*  578: 620 */         if ((localException instanceof FFSException)) {
/*  579: 622 */           throw ((FFSException)EJBContext.clone(localException));
/*  580:     */         }
/*  581: 624 */         localEJBContext.throwRemote(localException);
/*  582:     */       }
/*  583: 626 */       throw new EJBException(localException);
/*  584:     */     }
/*  585:     */     finally
/*  586:     */     {
/*  587: 630 */       this._comp.setCurrent(localJavaComponent);
/*  588:     */     }
/*  589:     */   }
/*  590:     */   
/*  591:     */   public void resubmitEvent(String paramString1, String paramString2, String paramString3)
/*  592:     */     throws RemoteException, FFSException
/*  593:     */   {
/*  594: 640 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  595: 641 */     EJBContext localEJBContext = null;
/*  596:     */     try
/*  597:     */     {
/*  598: 644 */       localEJBContext = this._comp.getPooledInstance();
/*  599: 645 */       if (localEJBContext == null) {
/*  600: 647 */         localEJBContext = _create();
/*  601:     */       }
/*  602: 649 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  603: 650 */       if (localEJBContext.debug) {
/*  604: 652 */         localEJBContext.logger.debug(localEJBContext.debugMsg("resubmitEvent"));
/*  605:     */       }
/*  606: 655 */       localBPWAdminBean.resubmitEvent(
/*  607: 656 */         paramString1, 
/*  608: 657 */         paramString2, 
/*  609: 658 */         paramString3);
/*  610:     */       
/*  611: 660 */       localEJBContext.returnToPool();
/*  612:     */     }
/*  613:     */     catch (Exception localException)
/*  614:     */     {
/*  615: 664 */       if (localEJBContext != null)
/*  616:     */       {
/*  617: 666 */         if ((localException instanceof FFSException)) {
/*  618: 668 */           throw ((FFSException)EJBContext.clone(localException));
/*  619:     */         }
/*  620: 670 */         localEJBContext.throwRemote(localException);
/*  621:     */       }
/*  622: 672 */       throw new EJBException(localException);
/*  623:     */     }
/*  624:     */     finally
/*  625:     */     {
/*  626: 676 */       this._comp.setCurrent(localJavaComponent);
/*  627:     */     }
/*  628:     */   }
/*  629:     */   
/*  630:     */   public void resubmitEvent(String paramString1, String paramString2)
/*  631:     */     throws RemoteException, FFSException
/*  632:     */   {
/*  633: 685 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  634: 686 */     EJBContext localEJBContext = null;
/*  635:     */     try
/*  636:     */     {
/*  637: 689 */       localEJBContext = this._comp.getPooledInstance();
/*  638: 690 */       if (localEJBContext == null) {
/*  639: 692 */         localEJBContext = _create();
/*  640:     */       }
/*  641: 694 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  642: 695 */       if (localEJBContext.debug) {
/*  643: 697 */         localEJBContext.logger.debug(localEJBContext.debugMsg("resubmitEvent"));
/*  644:     */       }
/*  645: 700 */       localBPWAdminBean.resubmitEvent(
/*  646: 701 */         paramString1, 
/*  647: 702 */         paramString2);
/*  648:     */       
/*  649: 704 */       localEJBContext.returnToPool();
/*  650:     */     }
/*  651:     */     catch (Exception localException)
/*  652:     */     {
/*  653: 708 */       if (localEJBContext != null)
/*  654:     */       {
/*  655: 710 */         if ((localException instanceof FFSException)) {
/*  656: 712 */           throw ((FFSException)EJBContext.clone(localException));
/*  657:     */         }
/*  658: 714 */         localEJBContext.throwRemote(localException);
/*  659:     */       }
/*  660: 716 */       throw new EJBException(localException);
/*  661:     */     }
/*  662:     */     finally
/*  663:     */     {
/*  664: 720 */       this._comp.setCurrent(localJavaComponent);
/*  665:     */     }
/*  666:     */   }
/*  667:     */   
/*  668:     */   public void resubmitEvent(String paramString1, String paramString2, String paramString3, String paramString4)
/*  669:     */     throws RemoteException, FFSException
/*  670:     */   {
/*  671: 731 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  672: 732 */     EJBContext localEJBContext = null;
/*  673:     */     try
/*  674:     */     {
/*  675: 735 */       localEJBContext = this._comp.getPooledInstance();
/*  676: 736 */       if (localEJBContext == null) {
/*  677: 738 */         localEJBContext = _create();
/*  678:     */       }
/*  679: 740 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  680: 741 */       if (localEJBContext.debug) {
/*  681: 743 */         localEJBContext.logger.debug(localEJBContext.debugMsg("resubmitEvent"));
/*  682:     */       }
/*  683: 746 */       localBPWAdminBean.resubmitEvent(
/*  684: 747 */         paramString1, 
/*  685: 748 */         paramString2, 
/*  686: 749 */         paramString3, 
/*  687: 750 */         paramString4);
/*  688:     */       
/*  689: 752 */       localEJBContext.returnToPool();
/*  690:     */     }
/*  691:     */     catch (Exception localException)
/*  692:     */     {
/*  693: 756 */       if (localEJBContext != null)
/*  694:     */       {
/*  695: 758 */         if ((localException instanceof FFSException)) {
/*  696: 760 */           throw ((FFSException)EJBContext.clone(localException));
/*  697:     */         }
/*  698: 762 */         localEJBContext.throwRemote(localException);
/*  699:     */       }
/*  700: 764 */       throw new EJBException(localException);
/*  701:     */     }
/*  702:     */     finally
/*  703:     */     {
/*  704: 768 */       this._comp.setCurrent(localJavaComponent);
/*  705:     */     }
/*  706:     */   }
/*  707:     */   
/*  708:     */   public String startScheduler()
/*  709:     */     throws RemoteException, FFSException
/*  710:     */   {
/*  711: 775 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  712: 776 */     EJBContext localEJBContext = null;
/*  713:     */     try
/*  714:     */     {
/*  715: 779 */       localEJBContext = this._comp.getPooledInstance();
/*  716: 780 */       if (localEJBContext == null) {
/*  717: 782 */         localEJBContext = _create();
/*  718:     */       }
/*  719: 784 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  720: 785 */       if (localEJBContext.debug) {
/*  721: 787 */         localEJBContext.logger.debug(localEJBContext.debugMsg("startScheduler"));
/*  722:     */       }
/*  723: 789 */       String str2 = localBPWAdminBean
/*  724: 790 */         .startScheduler();
/*  725:     */       
/*  726: 792 */       localEJBContext.returnToPool();
/*  727: 793 */       return str2;
/*  728:     */     }
/*  729:     */     catch (Exception localException)
/*  730:     */     {
/*  731: 797 */       if (localEJBContext != null)
/*  732:     */       {
/*  733: 799 */         if ((localException instanceof FFSException)) {
/*  734: 801 */           throw ((FFSException)EJBContext.clone(localException));
/*  735:     */         }
/*  736: 803 */         localEJBContext.throwRemote(localException);
/*  737:     */       }
/*  738: 805 */       throw new EJBException(localException);
/*  739:     */     }
/*  740:     */     finally
/*  741:     */     {
/*  742: 809 */       this._comp.setCurrent(localJavaComponent);
/*  743:     */     }
/*  744:     */   }
/*  745:     */   
/*  746:     */   public String stopScheduler()
/*  747:     */     throws RemoteException, FFSException
/*  748:     */   {
/*  749: 816 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  750: 817 */     EJBContext localEJBContext = null;
/*  751:     */     try
/*  752:     */     {
/*  753: 820 */       localEJBContext = this._comp.getPooledInstance();
/*  754: 821 */       if (localEJBContext == null) {
/*  755: 823 */         localEJBContext = _create();
/*  756:     */       }
/*  757: 825 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  758: 826 */       if (localEJBContext.debug) {
/*  759: 828 */         localEJBContext.logger.debug(localEJBContext.debugMsg("stopScheduler"));
/*  760:     */       }
/*  761: 830 */       String str2 = localBPWAdminBean
/*  762: 831 */         .stopScheduler();
/*  763:     */       
/*  764: 833 */       localEJBContext.returnToPool();
/*  765: 834 */       return str2;
/*  766:     */     }
/*  767:     */     catch (Exception localException)
/*  768:     */     {
/*  769: 838 */       if (localEJBContext != null)
/*  770:     */       {
/*  771: 840 */         if ((localException instanceof FFSException)) {
/*  772: 842 */           throw ((FFSException)EJBContext.clone(localException));
/*  773:     */         }
/*  774: 844 */         localEJBContext.throwRemote(localException);
/*  775:     */       }
/*  776: 846 */       throw new EJBException(localException);
/*  777:     */     }
/*  778:     */     finally
/*  779:     */     {
/*  780: 850 */       this._comp.setCurrent(localJavaComponent);
/*  781:     */     }
/*  782:     */   }
/*  783:     */   
/*  784:     */   public String refreshScheduler()
/*  785:     */     throws RemoteException, FFSException
/*  786:     */   {
/*  787: 857 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  788: 858 */     EJBContext localEJBContext = null;
/*  789:     */     try
/*  790:     */     {
/*  791: 861 */       localEJBContext = this._comp.getPooledInstance();
/*  792: 862 */       if (localEJBContext == null) {
/*  793: 864 */         localEJBContext = _create();
/*  794:     */       }
/*  795: 866 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  796: 867 */       if (localEJBContext.debug) {
/*  797: 869 */         localEJBContext.logger.debug(localEJBContext.debugMsg("refreshScheduler"));
/*  798:     */       }
/*  799: 871 */       String str2 = localBPWAdminBean
/*  800: 872 */         .refreshScheduler();
/*  801:     */       
/*  802: 874 */       localEJBContext.returnToPool();
/*  803: 875 */       return str2;
/*  804:     */     }
/*  805:     */     catch (Exception localException)
/*  806:     */     {
/*  807: 879 */       if (localEJBContext != null)
/*  808:     */       {
/*  809: 881 */         if ((localException instanceof FFSException)) {
/*  810: 883 */           throw ((FFSException)EJBContext.clone(localException));
/*  811:     */         }
/*  812: 885 */         localEJBContext.throwRemote(localException);
/*  813:     */       }
/*  814: 887 */       throw new EJBException(localException);
/*  815:     */     }
/*  816:     */     finally
/*  817:     */     {
/*  818: 891 */       this._comp.setCurrent(localJavaComponent);
/*  819:     */     }
/*  820:     */   }
/*  821:     */   
/*  822:     */   public void registerPropertyConfig(PropertyConfig paramPropertyConfig)
/*  823:     */     throws RemoteException, FFSException
/*  824:     */   {
/*  825: 899 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  826: 900 */     EJBContext localEJBContext = null;
/*  827:     */     try
/*  828:     */     {
/*  829: 903 */       localEJBContext = this._comp.getPooledInstance();
/*  830: 904 */       if (localEJBContext == null) {
/*  831: 906 */         localEJBContext = _create();
/*  832:     */       }
/*  833: 908 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  834: 909 */       if (localEJBContext.debug) {
/*  835: 911 */         localEJBContext.logger.debug(localEJBContext.debugMsg("registerPropertyConfig"));
/*  836:     */       }
/*  837: 914 */       localBPWAdminBean.registerPropertyConfig(
/*  838: 915 */         PropertyConfigHelper.clone(paramPropertyConfig));
/*  839:     */       
/*  840: 917 */       localEJBContext.returnToPool();
/*  841:     */     }
/*  842:     */     catch (Exception localException)
/*  843:     */     {
/*  844: 921 */       if (localEJBContext != null)
/*  845:     */       {
/*  846: 923 */         if ((localException instanceof FFSException)) {
/*  847: 925 */           throw ((FFSException)EJBContext.clone(localException));
/*  848:     */         }
/*  849: 927 */         localEJBContext.throwRemote(localException);
/*  850:     */       }
/*  851: 929 */       throw new EJBException(localException);
/*  852:     */     }
/*  853:     */     finally
/*  854:     */     {
/*  855: 933 */       this._comp.setCurrent(localJavaComponent);
/*  856:     */     }
/*  857:     */   }
/*  858:     */   
/*  859:     */   public void startEngine(String paramString)
/*  860:     */     throws RemoteException, FFSException
/*  861:     */   {
/*  862: 941 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  863: 942 */     EJBContext localEJBContext = null;
/*  864:     */     try
/*  865:     */     {
/*  866: 945 */       localEJBContext = this._comp.getPooledInstance();
/*  867: 946 */       if (localEJBContext == null) {
/*  868: 948 */         localEJBContext = _create();
/*  869:     */       }
/*  870: 950 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  871: 951 */       if (localEJBContext.debug) {
/*  872: 953 */         localEJBContext.logger.debug(localEJBContext.debugMsg("startEngine"));
/*  873:     */       }
/*  874: 956 */       localBPWAdminBean.startEngine(
/*  875: 957 */         paramString);
/*  876:     */       
/*  877: 959 */       localEJBContext.returnToPool();
/*  878:     */     }
/*  879:     */     catch (Exception localException)
/*  880:     */     {
/*  881: 963 */       if (localEJBContext != null)
/*  882:     */       {
/*  883: 965 */         if ((localException instanceof FFSException)) {
/*  884: 967 */           throw ((FFSException)EJBContext.clone(localException));
/*  885:     */         }
/*  886: 969 */         localEJBContext.throwRemote(localException);
/*  887:     */       }
/*  888: 971 */       throw new EJBException(localException);
/*  889:     */     }
/*  890:     */     finally
/*  891:     */     {
/*  892: 975 */       this._comp.setCurrent(localJavaComponent);
/*  893:     */     }
/*  894:     */   }
/*  895:     */   
/*  896:     */   public void stopEngine(String paramString)
/*  897:     */     throws RemoteException, FFSException
/*  898:     */   {
/*  899: 983 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  900: 984 */     EJBContext localEJBContext = null;
/*  901:     */     try
/*  902:     */     {
/*  903: 987 */       localEJBContext = this._comp.getPooledInstance();
/*  904: 988 */       if (localEJBContext == null) {
/*  905: 990 */         localEJBContext = _create();
/*  906:     */       }
/*  907: 992 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  908: 993 */       if (localEJBContext.debug) {
/*  909: 995 */         localEJBContext.logger.debug(localEJBContext.debugMsg("stopEngine"));
/*  910:     */       }
/*  911: 998 */       localBPWAdminBean.stopEngine(
/*  912: 999 */         paramString);
/*  913:     */       
/*  914:1001 */       localEJBContext.returnToPool();
/*  915:     */     }
/*  916:     */     catch (Exception localException)
/*  917:     */     {
/*  918:1005 */       if (localEJBContext != null)
/*  919:     */       {
/*  920:1007 */         if ((localException instanceof FFSException)) {
/*  921:1009 */           throw ((FFSException)EJBContext.clone(localException));
/*  922:     */         }
/*  923:1011 */         localEJBContext.throwRemote(localException);
/*  924:     */       }
/*  925:1013 */       throw new EJBException(localException);
/*  926:     */     }
/*  927:     */     finally
/*  928:     */     {
/*  929:1017 */       this._comp.setCurrent(localJavaComponent);
/*  930:     */     }
/*  931:     */   }
/*  932:     */   
/*  933:     */   public void stopLimitCheckApprovalProcessor(String paramString)
/*  934:     */     throws RemoteException, FFSException
/*  935:     */   {
/*  936:1025 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  937:1026 */     EJBContext localEJBContext = null;
/*  938:     */     try
/*  939:     */     {
/*  940:1029 */       localEJBContext = this._comp.getPooledInstance();
/*  941:1030 */       if (localEJBContext == null) {
/*  942:1032 */         localEJBContext = _create();
/*  943:     */       }
/*  944:1034 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  945:1035 */       if (localEJBContext.debug) {
/*  946:1037 */         localEJBContext.logger.debug(localEJBContext.debugMsg("stopLimitCheckApprovalProcessor"));
/*  947:     */       }
/*  948:1040 */       localBPWAdminBean.stopLimitCheckApprovalProcessor(
/*  949:1041 */         paramString);
/*  950:     */       
/*  951:1043 */       localEJBContext.returnToPool();
/*  952:     */     }
/*  953:     */     catch (Exception localException)
/*  954:     */     {
/*  955:1047 */       if (localEJBContext != null)
/*  956:     */       {
/*  957:1049 */         if ((localException instanceof FFSException)) {
/*  958:1051 */           throw ((FFSException)EJBContext.clone(localException));
/*  959:     */         }
/*  960:1053 */         localEJBContext.throwRemote(localException);
/*  961:     */       }
/*  962:1055 */       throw new EJBException(localException);
/*  963:     */     }
/*  964:     */     finally
/*  965:     */     {
/*  966:1059 */       this._comp.setCurrent(localJavaComponent);
/*  967:     */     }
/*  968:     */   }
/*  969:     */   
/*  970:     */   public void startLimitCheckApprovalProcessor(String paramString)
/*  971:     */     throws RemoteException, FFSException
/*  972:     */   {
/*  973:1067 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  974:1068 */     EJBContext localEJBContext = null;
/*  975:     */     try
/*  976:     */     {
/*  977:1071 */       localEJBContext = this._comp.getPooledInstance();
/*  978:1072 */       if (localEJBContext == null) {
/*  979:1074 */         localEJBContext = _create();
/*  980:     */       }
/*  981:1076 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/*  982:1077 */       if (localEJBContext.debug) {
/*  983:1079 */         localEJBContext.logger.debug(localEJBContext.debugMsg("startLimitCheckApprovalProcessor"));
/*  984:     */       }
/*  985:1082 */       localBPWAdminBean.startLimitCheckApprovalProcessor(
/*  986:1083 */         paramString);
/*  987:     */       
/*  988:1085 */       localEJBContext.returnToPool();
/*  989:     */     }
/*  990:     */     catch (Exception localException)
/*  991:     */     {
/*  992:1089 */       if (localEJBContext != null)
/*  993:     */       {
/*  994:1091 */         if ((localException instanceof FFSException)) {
/*  995:1093 */           throw ((FFSException)EJBContext.clone(localException));
/*  996:     */         }
/*  997:1095 */         localEJBContext.throwRemote(localException);
/*  998:     */       }
/*  999:1097 */       throw new EJBException(localException);
/* 1000:     */     }
/* 1001:     */     finally
/* 1002:     */     {
/* 1003:1101 */       this._comp.setCurrent(localJavaComponent);
/* 1004:     */     }
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public void runBatchProcess(String paramString1, String paramString2)
/* 1008:     */     throws RemoteException, FFSException
/* 1009:     */   {
/* 1010:1110 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1011:1111 */     EJBContext localEJBContext = null;
/* 1012:     */     try
/* 1013:     */     {
/* 1014:1114 */       localEJBContext = this._comp.getPooledInstance();
/* 1015:1115 */       if (localEJBContext == null) {
/* 1016:1117 */         localEJBContext = _create();
/* 1017:     */       }
/* 1018:1119 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1019:1120 */       if (localEJBContext.debug) {
/* 1020:1122 */         localEJBContext.logger.debug(localEJBContext.debugMsg("runBatchProcess"));
/* 1021:     */       }
/* 1022:1125 */       localBPWAdminBean.runBatchProcess(
/* 1023:1126 */         paramString1, 
/* 1024:1127 */         paramString2);
/* 1025:     */       
/* 1026:1129 */       localEJBContext.returnToPool();
/* 1027:     */     }
/* 1028:     */     catch (Exception localException)
/* 1029:     */     {
/* 1030:1133 */       if (localEJBContext != null)
/* 1031:     */       {
/* 1032:1135 */         if ((localException instanceof FFSException)) {
/* 1033:1137 */           throw ((FFSException)EJBContext.clone(localException));
/* 1034:     */         }
/* 1035:1139 */         localEJBContext.throwRemote(localException);
/* 1036:     */       }
/* 1037:1141 */       throw new EJBException(localException);
/* 1038:     */     }
/* 1039:     */     finally
/* 1040:     */     {
/* 1041:1145 */       this._comp.setCurrent(localJavaComponent);
/* 1042:     */     }
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public void updateScheduleRunTimeConfig(InstructionType paramInstructionType)
/* 1046:     */     throws RemoteException, FFSException
/* 1047:     */   {
/* 1048:1153 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1049:1154 */     EJBContext localEJBContext = null;
/* 1050:     */     try
/* 1051:     */     {
/* 1052:1157 */       localEJBContext = this._comp.getPooledInstance();
/* 1053:1158 */       if (localEJBContext == null) {
/* 1054:1160 */         localEJBContext = _create();
/* 1055:     */       }
/* 1056:1162 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1057:1163 */       if (localEJBContext.debug) {
/* 1058:1165 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updateScheduleRunTimeConfig"));
/* 1059:     */       }
/* 1060:1168 */       localBPWAdminBean.updateScheduleRunTimeConfig(
/* 1061:1169 */         (InstructionType)EJBContext.clone(paramInstructionType));
/* 1062:     */       
/* 1063:1171 */       localEJBContext.returnToPool();
/* 1064:     */     }
/* 1065:     */     catch (Exception localException)
/* 1066:     */     {
/* 1067:1175 */       if (localEJBContext != null)
/* 1068:     */       {
/* 1069:1177 */         if ((localException instanceof FFSException)) {
/* 1070:1179 */           throw ((FFSException)EJBContext.clone(localException));
/* 1071:     */         }
/* 1072:1181 */         localEJBContext.throwRemote(localException);
/* 1073:     */       }
/* 1074:1183 */       throw new EJBException(localException);
/* 1075:     */     }
/* 1076:     */     finally
/* 1077:     */     {
/* 1078:1187 */       this._comp.setCurrent(localJavaComponent);
/* 1079:     */     }
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   public void updateScheduleProcessingConfig(InstructionType paramInstructionType)
/* 1083:     */     throws RemoteException, FFSException
/* 1084:     */   {
/* 1085:1195 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1086:1196 */     EJBContext localEJBContext = null;
/* 1087:     */     try
/* 1088:     */     {
/* 1089:1199 */       localEJBContext = this._comp.getPooledInstance();
/* 1090:1200 */       if (localEJBContext == null) {
/* 1091:1202 */         localEJBContext = _create();
/* 1092:     */       }
/* 1093:1204 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1094:1205 */       if (localEJBContext.debug) {
/* 1095:1207 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updateScheduleProcessingConfig"));
/* 1096:     */       }
/* 1097:1210 */       localBPWAdminBean.updateScheduleProcessingConfig(
/* 1098:1211 */         (InstructionType)EJBContext.clone(paramInstructionType));
/* 1099:     */       
/* 1100:1213 */       localEJBContext.returnToPool();
/* 1101:     */     }
/* 1102:     */     catch (Exception localException)
/* 1103:     */     {
/* 1104:1217 */       if (localEJBContext != null)
/* 1105:     */       {
/* 1106:1219 */         if ((localException instanceof FFSException)) {
/* 1107:1221 */           throw ((FFSException)EJBContext.clone(localException));
/* 1108:     */         }
/* 1109:1223 */         localEJBContext.throwRemote(localException);
/* 1110:     */       }
/* 1111:1225 */       throw new EJBException(localException);
/* 1112:     */     }
/* 1113:     */     finally
/* 1114:     */     {
/* 1115:1229 */       this._comp.setCurrent(localJavaComponent);
/* 1116:     */     }
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public void updateScheduleConfig(InstructionType paramInstructionType)
/* 1120:     */     throws RemoteException, FFSException
/* 1121:     */   {
/* 1122:1237 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1123:1238 */     EJBContext localEJBContext = null;
/* 1124:     */     try
/* 1125:     */     {
/* 1126:1241 */       localEJBContext = this._comp.getPooledInstance();
/* 1127:1242 */       if (localEJBContext == null) {
/* 1128:1244 */         localEJBContext = _create();
/* 1129:     */       }
/* 1130:1246 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1131:1247 */       if (localEJBContext.debug) {
/* 1132:1249 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updateScheduleConfig"));
/* 1133:     */       }
/* 1134:1252 */       localBPWAdminBean.updateScheduleConfig(
/* 1135:1253 */         (InstructionType)EJBContext.clone(paramInstructionType));
/* 1136:     */       
/* 1137:1255 */       localEJBContext.returnToPool();
/* 1138:     */     }
/* 1139:     */     catch (Exception localException)
/* 1140:     */     {
/* 1141:1259 */       if (localEJBContext != null)
/* 1142:     */       {
/* 1143:1261 */         if ((localException instanceof FFSException)) {
/* 1144:1263 */           throw ((FFSException)EJBContext.clone(localException));
/* 1145:     */         }
/* 1146:1265 */         localEJBContext.throwRemote(localException);
/* 1147:     */       }
/* 1148:1267 */       throw new EJBException(localException);
/* 1149:     */     }
/* 1150:     */     finally
/* 1151:     */     {
/* 1152:1271 */       this._comp.setCurrent(localJavaComponent);
/* 1153:     */     }
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public InstructionType getScheduleConfig(String paramString1, String paramString2)
/* 1157:     */     throws RemoteException, FFSException
/* 1158:     */   {
/* 1159:1280 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1160:1281 */     EJBContext localEJBContext = null;
/* 1161:     */     try
/* 1162:     */     {
/* 1163:1284 */       localEJBContext = this._comp.getPooledInstance();
/* 1164:1285 */       if (localEJBContext == null) {
/* 1165:1287 */         localEJBContext = _create();
/* 1166:     */       }
/* 1167:1289 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1168:1290 */       if (localEJBContext.debug) {
/* 1169:1292 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getScheduleConfig"));
/* 1170:     */       }
/* 1171:1294 */       InstructionType localInstructionType2 = localBPWAdminBean
/* 1172:1295 */         .getScheduleConfig(
/* 1173:1296 */         paramString1, 
/* 1174:1297 */         paramString2);
/* 1175:     */       
/* 1176:1299 */       localInstructionType2 = (InstructionType)EJBContext.clone(localInstructionType2);
/* 1177:1300 */       localEJBContext.returnToPool();
/* 1178:1301 */       return localInstructionType2;
/* 1179:     */     }
/* 1180:     */     catch (Exception localException)
/* 1181:     */     {
/* 1182:1305 */       if (localEJBContext != null)
/* 1183:     */       {
/* 1184:1307 */         if ((localException instanceof FFSException)) {
/* 1185:1309 */           throw ((FFSException)EJBContext.clone(localException));
/* 1186:     */         }
/* 1187:1311 */         localEJBContext.throwRemote(localException);
/* 1188:     */       }
/* 1189:1313 */       throw new EJBException(localException);
/* 1190:     */     }
/* 1191:     */     finally
/* 1192:     */     {
/* 1193:1317 */       this._comp.setCurrent(localJavaComponent);
/* 1194:     */     }
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   public InstructionType[] getScheduleConfig()
/* 1198:     */     throws RemoteException, FFSException
/* 1199:     */   {
/* 1200:1324 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1201:1325 */     EJBContext localEJBContext = null;
/* 1202:     */     try
/* 1203:     */     {
/* 1204:1328 */       localEJBContext = this._comp.getPooledInstance();
/* 1205:1329 */       if (localEJBContext == null) {
/* 1206:1331 */         localEJBContext = _create();
/* 1207:     */       }
/* 1208:1333 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1209:1334 */       if (localEJBContext.debug) {
/* 1210:1336 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getScheduleConfig"));
/* 1211:     */       }
/* 1212:1338 */       InstructionType[] arrayOfInstructionType2 = localBPWAdminBean
/* 1213:1339 */         .getScheduleConfig();
/* 1214:     */       
/* 1215:1341 */       arrayOfInstructionType2 = InstructionTypeSeqHelper.clone(arrayOfInstructionType2);
/* 1216:1342 */       localEJBContext.returnToPool();
/* 1217:1343 */       return arrayOfInstructionType2;
/* 1218:     */     }
/* 1219:     */     catch (Exception localException)
/* 1220:     */     {
/* 1221:1347 */       if (localEJBContext != null)
/* 1222:     */       {
/* 1223:1349 */         if ((localException instanceof FFSException)) {
/* 1224:1351 */           throw ((FFSException)EJBContext.clone(localException));
/* 1225:     */         }
/* 1226:1353 */         localEJBContext.throwRemote(localException);
/* 1227:     */       }
/* 1228:1355 */       throw new EJBException(localException);
/* 1229:     */     }
/* 1230:     */     finally
/* 1231:     */     {
/* 1232:1359 */       this._comp.setCurrent(localJavaComponent);
/* 1233:     */     }
/* 1234:     */   }
/* 1235:     */   
/* 1236:     */   public SchedulerInfo getSchedulerInfo(String paramString1, String paramString2)
/* 1237:     */     throws RemoteException, FFSException
/* 1238:     */   {
/* 1239:1368 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1240:1369 */     EJBContext localEJBContext = null;
/* 1241:     */     try
/* 1242:     */     {
/* 1243:1372 */       localEJBContext = this._comp.getPooledInstance();
/* 1244:1373 */       if (localEJBContext == null) {
/* 1245:1375 */         localEJBContext = _create();
/* 1246:     */       }
/* 1247:1377 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1248:1378 */       if (localEJBContext.debug) {
/* 1249:1380 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getSchedulerInfo"));
/* 1250:     */       }
/* 1251:1382 */       SchedulerInfo localSchedulerInfo2 = localBPWAdminBean
/* 1252:1383 */         .getSchedulerInfo(
/* 1253:1384 */         paramString1, 
/* 1254:1385 */         paramString2);
/* 1255:     */       
/* 1256:1387 */       localSchedulerInfo2 = (SchedulerInfo)EJBContext.clone(localSchedulerInfo2);
/* 1257:1388 */       localEJBContext.returnToPool();
/* 1258:1389 */       return localSchedulerInfo2;
/* 1259:     */     }
/* 1260:     */     catch (Exception localException)
/* 1261:     */     {
/* 1262:1393 */       if (localEJBContext != null)
/* 1263:     */       {
/* 1264:1395 */         if ((localException instanceof FFSException)) {
/* 1265:1397 */           throw ((FFSException)EJBContext.clone(localException));
/* 1266:     */         }
/* 1267:1399 */         localEJBContext.throwRemote(localException);
/* 1268:     */       }
/* 1269:1401 */       throw new EJBException(localException);
/* 1270:     */     }
/* 1271:     */     finally
/* 1272:     */     {
/* 1273:1405 */       this._comp.setCurrent(localJavaComponent);
/* 1274:     */     }
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   public SchedulerInfo[] getSchedulerInfo()
/* 1278:     */     throws RemoteException, FFSException
/* 1279:     */   {
/* 1280:1412 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1281:1413 */     EJBContext localEJBContext = null;
/* 1282:     */     try
/* 1283:     */     {
/* 1284:1416 */       localEJBContext = this._comp.getPooledInstance();
/* 1285:1417 */       if (localEJBContext == null) {
/* 1286:1419 */         localEJBContext = _create();
/* 1287:     */       }
/* 1288:1421 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1289:1422 */       if (localEJBContext.debug) {
/* 1290:1424 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getSchedulerInfo"));
/* 1291:     */       }
/* 1292:1426 */       SchedulerInfo[] arrayOfSchedulerInfo2 = localBPWAdminBean
/* 1293:1427 */         .getSchedulerInfo();
/* 1294:     */       
/* 1295:1429 */       arrayOfSchedulerInfo2 = SchedulerInfoSeqHelper.clone(arrayOfSchedulerInfo2);
/* 1296:1430 */       localEJBContext.returnToPool();
/* 1297:1431 */       return arrayOfSchedulerInfo2;
/* 1298:     */     }
/* 1299:     */     catch (Exception localException)
/* 1300:     */     {
/* 1301:1435 */       if (localEJBContext != null)
/* 1302:     */       {
/* 1303:1437 */         if ((localException instanceof FFSException)) {
/* 1304:1439 */           throw ((FFSException)EJBContext.clone(localException));
/* 1305:     */         }
/* 1306:1441 */         localEJBContext.throwRemote(localException);
/* 1307:     */       }
/* 1308:1443 */       throw new EJBException(localException);
/* 1309:     */     }
/* 1310:     */     finally
/* 1311:     */     {
/* 1312:1447 */       this._comp.setCurrent(localJavaComponent);
/* 1313:     */     }
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   public ScheduleHist[] getScheduleHist(String paramString1, String paramString2, ScheduleHist paramScheduleHist)
/* 1317:     */     throws RemoteException, FFSException
/* 1318:     */   {
/* 1319:1457 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1320:1458 */     EJBContext localEJBContext = null;
/* 1321:     */     try
/* 1322:     */     {
/* 1323:1461 */       localEJBContext = this._comp.getPooledInstance();
/* 1324:1462 */       if (localEJBContext == null) {
/* 1325:1464 */         localEJBContext = _create();
/* 1326:     */       }
/* 1327:1466 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1328:1467 */       if (localEJBContext.debug) {
/* 1329:1469 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getScheduleHist"));
/* 1330:     */       }
/* 1331:1471 */       ScheduleHist[] arrayOfScheduleHist2 = localBPWAdminBean
/* 1332:1472 */         .getScheduleHist(
/* 1333:1473 */         paramString1, 
/* 1334:1474 */         paramString2, 
/* 1335:1475 */         ScheduleHistHelper.clone(paramScheduleHist));
/* 1336:     */       
/* 1337:1477 */       arrayOfScheduleHist2 = ScheduleHistSeqHelper.clone(arrayOfScheduleHist2);
/* 1338:1478 */       localEJBContext.returnToPool();
/* 1339:1479 */       return arrayOfScheduleHist2;
/* 1340:     */     }
/* 1341:     */     catch (Exception localException)
/* 1342:     */     {
/* 1343:1483 */       if (localEJBContext != null)
/* 1344:     */       {
/* 1345:1485 */         if ((localException instanceof FFSException)) {
/* 1346:1487 */           throw ((FFSException)EJBContext.clone(localException));
/* 1347:     */         }
/* 1348:1489 */         localEJBContext.throwRemote(localException);
/* 1349:     */       }
/* 1350:1491 */       throw new EJBException(localException);
/* 1351:     */     }
/* 1352:     */     finally
/* 1353:     */     {
/* 1354:1495 */       this._comp.setCurrent(localJavaComponent);
/* 1355:     */     }
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   public PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
/* 1359:     */     throws RemoteException, FFSException
/* 1360:     */   {
/* 1361:1504 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1362:1505 */     EJBContext localEJBContext = null;
/* 1363:     */     try
/* 1364:     */     {
/* 1365:1508 */       localEJBContext = this._comp.getPooledInstance();
/* 1366:1509 */       if (localEJBContext == null) {
/* 1367:1511 */         localEJBContext = _create();
/* 1368:     */       }
/* 1369:1513 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1370:1514 */       if (localEJBContext.debug) {
/* 1371:1516 */         localEJBContext.logger.debug(localEJBContext.debugMsg("searchGlobalPayees"));
/* 1372:     */       }
/* 1373:1518 */       PayeeInfo[] arrayOfPayeeInfo2 = localBPWAdminBean
/* 1374:1519 */         .searchGlobalPayees(
/* 1375:1520 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 1376:1521 */         paramInt);
/* 1377:     */       
/* 1378:1523 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1379:1524 */       localEJBContext.returnToPool();
/* 1380:1525 */       return arrayOfPayeeInfo2;
/* 1381:     */     }
/* 1382:     */     catch (Exception localException)
/* 1383:     */     {
/* 1384:1529 */       if (localEJBContext != null)
/* 1385:     */       {
/* 1386:1531 */         if ((localException instanceof FFSException)) {
/* 1387:1533 */           throw ((FFSException)EJBContext.clone(localException));
/* 1388:     */         }
/* 1389:1535 */         localEJBContext.throwRemote(localException);
/* 1390:     */       }
/* 1391:1537 */       throw new EJBException(localException);
/* 1392:     */     }
/* 1393:     */     finally
/* 1394:     */     {
/* 1395:1541 */       this._comp.setCurrent(localJavaComponent);
/* 1396:     */     }
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public PayeeInfo[] searchGlobalPayees(String paramString)
/* 1400:     */     throws RemoteException, FFSException
/* 1401:     */   {
/* 1402:1549 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1403:1550 */     EJBContext localEJBContext = null;
/* 1404:     */     try
/* 1405:     */     {
/* 1406:1553 */       localEJBContext = this._comp.getPooledInstance();
/* 1407:1554 */       if (localEJBContext == null) {
/* 1408:1556 */         localEJBContext = _create();
/* 1409:     */       }
/* 1410:1558 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1411:1559 */       if (localEJBContext.debug) {
/* 1412:1561 */         localEJBContext.logger.debug(localEJBContext.debugMsg("searchGlobalPayees"));
/* 1413:     */       }
/* 1414:1563 */       PayeeInfo[] arrayOfPayeeInfo2 = localBPWAdminBean
/* 1415:1564 */         .searchGlobalPayees(
/* 1416:1565 */         paramString);
/* 1417:     */       
/* 1418:1567 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1419:1568 */       localEJBContext.returnToPool();
/* 1420:1569 */       return arrayOfPayeeInfo2;
/* 1421:     */     }
/* 1422:     */     catch (Exception localException)
/* 1423:     */     {
/* 1424:1573 */       if (localEJBContext != null)
/* 1425:     */       {
/* 1426:1575 */         if ((localException instanceof FFSException)) {
/* 1427:1577 */           throw ((FFSException)EJBContext.clone(localException));
/* 1428:     */         }
/* 1429:1579 */         localEJBContext.throwRemote(localException);
/* 1430:     */       }
/* 1431:1581 */       throw new EJBException(localException);
/* 1432:     */     }
/* 1433:     */     finally
/* 1434:     */     {
/* 1435:1585 */       this._comp.setCurrent(localJavaComponent);
/* 1436:     */     }
/* 1437:     */   }
/* 1438:     */   
/* 1439:     */   public PayeeInfo getGlobalPayee(String paramString)
/* 1440:     */     throws RemoteException, FFSException
/* 1441:     */   {
/* 1442:1593 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1443:1594 */     EJBContext localEJBContext = null;
/* 1444:     */     try
/* 1445:     */     {
/* 1446:1597 */       localEJBContext = this._comp.getPooledInstance();
/* 1447:1598 */       if (localEJBContext == null) {
/* 1448:1600 */         localEJBContext = _create();
/* 1449:     */       }
/* 1450:1602 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1451:1603 */       if (localEJBContext.debug) {
/* 1452:1605 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getGlobalPayee"));
/* 1453:     */       }
/* 1454:1607 */       PayeeInfo localPayeeInfo2 = localBPWAdminBean
/* 1455:1608 */         .getGlobalPayee(
/* 1456:1609 */         paramString);
/* 1457:     */       
/* 1458:1611 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 1459:1612 */       localEJBContext.returnToPool();
/* 1460:1613 */       return localPayeeInfo2;
/* 1461:     */     }
/* 1462:     */     catch (Exception localException)
/* 1463:     */     {
/* 1464:1617 */       if (localEJBContext != null)
/* 1465:     */       {
/* 1466:1619 */         if ((localException instanceof FFSException)) {
/* 1467:1621 */           throw ((FFSException)EJBContext.clone(localException));
/* 1468:     */         }
/* 1469:1623 */         localEJBContext.throwRemote(localException);
/* 1470:     */       }
/* 1471:1625 */       throw new EJBException(localException);
/* 1472:     */     }
/* 1473:     */     finally
/* 1474:     */     {
/* 1475:1629 */       this._comp.setCurrent(localJavaComponent);
/* 1476:     */     }
/* 1477:     */   }
/* 1478:     */   
/* 1479:     */   public PayeeInfo updateGlobalPayee(PayeeInfo paramPayeeInfo)
/* 1480:     */     throws RemoteException, FFSException
/* 1481:     */   {
/* 1482:1637 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1483:1638 */     EJBContext localEJBContext = null;
/* 1484:     */     try
/* 1485:     */     {
/* 1486:1641 */       localEJBContext = this._comp.getPooledInstance();
/* 1487:1642 */       if (localEJBContext == null) {
/* 1488:1644 */         localEJBContext = _create();
/* 1489:     */       }
/* 1490:1646 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1491:1647 */       if (localEJBContext.debug) {
/* 1492:1649 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updateGlobalPayee"));
/* 1493:     */       }
/* 1494:1651 */       PayeeInfo localPayeeInfo2 = localBPWAdminBean
/* 1495:1652 */         .updateGlobalPayee(
/* 1496:1653 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo));
/* 1497:     */       
/* 1498:1655 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 1499:1656 */       localEJBContext.returnToPool();
/* 1500:1657 */       return localPayeeInfo2;
/* 1501:     */     }
/* 1502:     */     catch (Exception localException)
/* 1503:     */     {
/* 1504:1661 */       if (localEJBContext != null)
/* 1505:     */       {
/* 1506:1663 */         if ((localException instanceof FFSException)) {
/* 1507:1665 */           throw ((FFSException)EJBContext.clone(localException));
/* 1508:     */         }
/* 1509:1667 */         localEJBContext.throwRemote(localException);
/* 1510:     */       }
/* 1511:1669 */       throw new EJBException(localException);
/* 1512:     */     }
/* 1513:     */     finally
/* 1514:     */     {
/* 1515:1673 */       this._comp.setCurrent(localJavaComponent);
/* 1516:     */     }
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   public String addPayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
/* 1520:     */     throws RemoteException, FFSException
/* 1521:     */   {
/* 1522:1683 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1523:1684 */     EJBContext localEJBContext = null;
/* 1524:     */     try
/* 1525:     */     {
/* 1526:1687 */       localEJBContext = this._comp.getPooledInstance();
/* 1527:1688 */       if (localEJBContext == null) {
/* 1528:1690 */         localEJBContext = _create();
/* 1529:     */       }
/* 1530:1692 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1531:1693 */       if (localEJBContext.debug) {
/* 1532:1695 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addPayee"));
/* 1533:     */       }
/* 1534:1697 */       String str2 = localBPWAdminBean
/* 1535:1698 */         .addPayee(
/* 1536:1699 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties), 
/* 1537:1700 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 1538:1701 */         (PayeeRouteInfo)EJBContext.clone(paramPayeeRouteInfo));
/* 1539:     */       
/* 1540:1703 */       localEJBContext.returnToPool();
/* 1541:1704 */       return str2;
/* 1542:     */     }
/* 1543:     */     catch (Exception localException)
/* 1544:     */     {
/* 1545:1708 */       if (localEJBContext != null)
/* 1546:     */       {
/* 1547:1710 */         if ((localException instanceof FFSException)) {
/* 1548:1712 */           throw ((FFSException)EJBContext.clone(localException));
/* 1549:     */         }
/* 1550:1714 */         localEJBContext.throwRemote(localException);
/* 1551:     */       }
/* 1552:1716 */       throw new EJBException(localException);
/* 1553:     */     }
/* 1554:     */     finally
/* 1555:     */     {
/* 1556:1720 */       this._comp.setCurrent(localJavaComponent);
/* 1557:     */     }
/* 1558:     */   }
/* 1559:     */   
/* 1560:     */   public void updatePayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
/* 1561:     */     throws RemoteException, FFSException
/* 1562:     */   {
/* 1563:1730 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1564:1731 */     EJBContext localEJBContext = null;
/* 1565:     */     try
/* 1566:     */     {
/* 1567:1734 */       localEJBContext = this._comp.getPooledInstance();
/* 1568:1735 */       if (localEJBContext == null) {
/* 1569:1737 */         localEJBContext = _create();
/* 1570:     */       }
/* 1571:1739 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1572:1740 */       if (localEJBContext.debug) {
/* 1573:1742 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updatePayee"));
/* 1574:     */       }
/* 1575:1745 */       localBPWAdminBean.updatePayee(
/* 1576:1746 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties), 
/* 1577:1747 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 1578:1748 */         (PayeeRouteInfo)EJBContext.clone(paramPayeeRouteInfo));
/* 1579:     */       
/* 1580:1750 */       localEJBContext.returnToPool();
/* 1581:     */     }
/* 1582:     */     catch (Exception localException)
/* 1583:     */     {
/* 1584:1754 */       if (localEJBContext != null)
/* 1585:     */       {
/* 1586:1756 */         if ((localException instanceof FFSException)) {
/* 1587:1758 */           throw ((FFSException)EJBContext.clone(localException));
/* 1588:     */         }
/* 1589:1760 */         localEJBContext.throwRemote(localException);
/* 1590:     */       }
/* 1591:1762 */       throw new EJBException(localException);
/* 1592:     */     }
/* 1593:     */     finally
/* 1594:     */     {
/* 1595:1766 */       this._comp.setCurrent(localJavaComponent);
/* 1596:     */     }
/* 1597:     */   }
/* 1598:     */   
/* 1599:     */   public void deletePayee(FFSDBProperties paramFFSDBProperties, String paramString)
/* 1600:     */     throws RemoteException, FFSException
/* 1601:     */   {
/* 1602:1775 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1603:1776 */     EJBContext localEJBContext = null;
/* 1604:     */     try
/* 1605:     */     {
/* 1606:1779 */       localEJBContext = this._comp.getPooledInstance();
/* 1607:1780 */       if (localEJBContext == null) {
/* 1608:1782 */         localEJBContext = _create();
/* 1609:     */       }
/* 1610:1784 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1611:1785 */       if (localEJBContext.debug) {
/* 1612:1787 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deletePayee"));
/* 1613:     */       }
/* 1614:1790 */       localBPWAdminBean.deletePayee(
/* 1615:1791 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties), 
/* 1616:1792 */         paramString);
/* 1617:     */       
/* 1618:1794 */       localEJBContext.returnToPool();
/* 1619:     */     }
/* 1620:     */     catch (Exception localException)
/* 1621:     */     {
/* 1622:1798 */       if (localEJBContext != null)
/* 1623:     */       {
/* 1624:1800 */         if ((localException instanceof FFSException)) {
/* 1625:1802 */           throw ((FFSException)EJBContext.clone(localException));
/* 1626:     */         }
/* 1627:1804 */         localEJBContext.throwRemote(localException);
/* 1628:     */       }
/* 1629:1806 */       throw new EJBException(localException);
/* 1630:     */     }
/* 1631:     */     finally
/* 1632:     */     {
/* 1633:1810 */       this._comp.setCurrent(localJavaComponent);
/* 1634:     */     }
/* 1635:     */   }
/* 1636:     */   
/* 1637:     */   public PayeeRouteInfo getPayeeRoute(FFSDBProperties paramFFSDBProperties, String paramString, int paramInt)
/* 1638:     */     throws RemoteException, FFSException
/* 1639:     */   {
/* 1640:1820 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1641:1821 */     EJBContext localEJBContext = null;
/* 1642:     */     try
/* 1643:     */     {
/* 1644:1824 */       localEJBContext = this._comp.getPooledInstance();
/* 1645:1825 */       if (localEJBContext == null) {
/* 1646:1827 */         localEJBContext = _create();
/* 1647:     */       }
/* 1648:1829 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1649:1830 */       if (localEJBContext.debug) {
/* 1650:1832 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeRoute"));
/* 1651:     */       }
/* 1652:1834 */       PayeeRouteInfo localPayeeRouteInfo2 = localBPWAdminBean
/* 1653:1835 */         .getPayeeRoute(
/* 1654:1836 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties), 
/* 1655:1837 */         paramString, 
/* 1656:1838 */         paramInt);
/* 1657:     */       
/* 1658:1840 */       localPayeeRouteInfo2 = (PayeeRouteInfo)EJBContext.clone(localPayeeRouteInfo2);
/* 1659:1841 */       localEJBContext.returnToPool();
/* 1660:1842 */       return localPayeeRouteInfo2;
/* 1661:     */     }
/* 1662:     */     catch (Exception localException)
/* 1663:     */     {
/* 1664:1846 */       if (localEJBContext != null)
/* 1665:     */       {
/* 1666:1848 */         if ((localException instanceof FFSException)) {
/* 1667:1850 */           throw ((FFSException)EJBContext.clone(localException));
/* 1668:     */         }
/* 1669:1852 */         localEJBContext.throwRemote(localException);
/* 1670:     */       }
/* 1671:1854 */       throw new EJBException(localException);
/* 1672:     */     }
/* 1673:     */     finally
/* 1674:     */     {
/* 1675:1858 */       this._comp.setCurrent(localJavaComponent);
/* 1676:     */     }
/* 1677:     */   }
/* 1678:     */   
/* 1679:     */   public PayeeInfo findPayeeByID(FFSDBProperties paramFFSDBProperties, String paramString)
/* 1680:     */     throws RemoteException, FFSException
/* 1681:     */   {
/* 1682:1867 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1683:1868 */     EJBContext localEJBContext = null;
/* 1684:     */     try
/* 1685:     */     {
/* 1686:1871 */       localEJBContext = this._comp.getPooledInstance();
/* 1687:1872 */       if (localEJBContext == null) {
/* 1688:1874 */         localEJBContext = _create();
/* 1689:     */       }
/* 1690:1876 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1691:1877 */       if (localEJBContext.debug) {
/* 1692:1879 */         localEJBContext.logger.debug(localEJBContext.debugMsg("findPayeeByID"));
/* 1693:     */       }
/* 1694:1881 */       PayeeInfo localPayeeInfo2 = localBPWAdminBean
/* 1695:1882 */         .findPayeeByID(
/* 1696:1883 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties), 
/* 1697:1884 */         paramString);
/* 1698:     */       
/* 1699:1886 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 1700:1887 */       localEJBContext.returnToPool();
/* 1701:1888 */       return localPayeeInfo2;
/* 1702:     */     }
/* 1703:     */     catch (Exception localException)
/* 1704:     */     {
/* 1705:1892 */       if (localEJBContext != null)
/* 1706:     */       {
/* 1707:1894 */         if ((localException instanceof FFSException)) {
/* 1708:1896 */           throw ((FFSException)EJBContext.clone(localException));
/* 1709:     */         }
/* 1710:1898 */         localEJBContext.throwRemote(localException);
/* 1711:     */       }
/* 1712:1900 */       throw new EJBException(localException);
/* 1713:     */     }
/* 1714:     */     finally
/* 1715:     */     {
/* 1716:1904 */       this._comp.setCurrent(localJavaComponent);
/* 1717:     */     }
/* 1718:     */   }
/* 1719:     */   
/* 1720:     */   public FulfillmentInfo[] getAllFulfillmentInfo(FFSDBProperties paramFFSDBProperties)
/* 1721:     */     throws RemoteException, FFSException
/* 1722:     */   {
/* 1723:1912 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1724:1913 */     EJBContext localEJBContext = null;
/* 1725:     */     try
/* 1726:     */     {
/* 1727:1916 */       localEJBContext = this._comp.getPooledInstance();
/* 1728:1917 */       if (localEJBContext == null) {
/* 1729:1919 */         localEJBContext = _create();
/* 1730:     */       }
/* 1731:1921 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1732:1922 */       if (localEJBContext.debug) {
/* 1733:1924 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getAllFulfillmentInfo"));
/* 1734:     */       }
/* 1735:1926 */       FulfillmentInfo[] arrayOfFulfillmentInfo2 = localBPWAdminBean
/* 1736:1927 */         .getAllFulfillmentInfo(
/* 1737:1928 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties));
/* 1738:     */       
/* 1739:1930 */       arrayOfFulfillmentInfo2 = FulfillmentInfoSeqHelper.clone(arrayOfFulfillmentInfo2);
/* 1740:1931 */       localEJBContext.returnToPool();
/* 1741:1932 */       return arrayOfFulfillmentInfo2;
/* 1742:     */     }
/* 1743:     */     catch (Exception localException)
/* 1744:     */     {
/* 1745:1936 */       if (localEJBContext != null)
/* 1746:     */       {
/* 1747:1938 */         if ((localException instanceof FFSException)) {
/* 1748:1940 */           throw ((FFSException)EJBContext.clone(localException));
/* 1749:     */         }
/* 1750:1942 */         localEJBContext.throwRemote(localException);
/* 1751:     */       }
/* 1752:1944 */       throw new EJBException(localException);
/* 1753:     */     }
/* 1754:     */     finally
/* 1755:     */     {
/* 1756:1948 */       this._comp.setCurrent(localJavaComponent);
/* 1757:     */     }
/* 1758:     */   }
/* 1759:     */   
/* 1760:     */   public FulfillmentInfo[] getFulfillmentSystems()
/* 1761:     */     throws RemoteException, FFSException
/* 1762:     */   {
/* 1763:1955 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1764:1956 */     EJBContext localEJBContext = null;
/* 1765:     */     try
/* 1766:     */     {
/* 1767:1959 */       localEJBContext = this._comp.getPooledInstance();
/* 1768:1960 */       if (localEJBContext == null) {
/* 1769:1962 */         localEJBContext = _create();
/* 1770:     */       }
/* 1771:1964 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1772:1965 */       if (localEJBContext.debug) {
/* 1773:1967 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getFulfillmentSystems"));
/* 1774:     */       }
/* 1775:1969 */       FulfillmentInfo[] arrayOfFulfillmentInfo2 = localBPWAdminBean
/* 1776:1970 */         .getFulfillmentSystems();
/* 1777:     */       
/* 1778:1972 */       arrayOfFulfillmentInfo2 = FulfillmentInfoSeqHelper.clone(arrayOfFulfillmentInfo2);
/* 1779:1973 */       localEJBContext.returnToPool();
/* 1780:1974 */       return arrayOfFulfillmentInfo2;
/* 1781:     */     }
/* 1782:     */     catch (Exception localException)
/* 1783:     */     {
/* 1784:1978 */       if (localEJBContext != null)
/* 1785:     */       {
/* 1786:1980 */         if ((localException instanceof FFSException)) {
/* 1787:1982 */           throw ((FFSException)EJBContext.clone(localException));
/* 1788:     */         }
/* 1789:1984 */         localEJBContext.throwRemote(localException);
/* 1790:     */       }
/* 1791:1986 */       throw new EJBException(localException);
/* 1792:     */     }
/* 1793:     */     finally
/* 1794:     */     {
/* 1795:1990 */       this._comp.setCurrent(localJavaComponent);
/* 1796:     */     }
/* 1797:     */   }
/* 1798:     */   
/* 1799:     */   public String[] getGlobalPayeeGroups()
/* 1800:     */     throws RemoteException, FFSException
/* 1801:     */   {
/* 1802:1997 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1803:1998 */     EJBContext localEJBContext = null;
/* 1804:     */     try
/* 1805:     */     {
/* 1806:2001 */       localEJBContext = this._comp.getPooledInstance();
/* 1807:2002 */       if (localEJBContext == null) {
/* 1808:2004 */         localEJBContext = _create();
/* 1809:     */       }
/* 1810:2006 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1811:2007 */       if (localEJBContext.debug) {
/* 1812:2009 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getGlobalPayeeGroups"));
/* 1813:     */       }
/* 1814:2011 */       String[] arrayOfString2 = localBPWAdminBean
/* 1815:2012 */         .getGlobalPayeeGroups();
/* 1816:     */       
/* 1817:2014 */       arrayOfString2 = StringSeqHelper.clone(arrayOfString2);
/* 1818:2015 */       localEJBContext.returnToPool();
/* 1819:2016 */       return arrayOfString2;
/* 1820:     */     }
/* 1821:     */     catch (Exception localException)
/* 1822:     */     {
/* 1823:2020 */       if (localEJBContext != null)
/* 1824:     */       {
/* 1825:2022 */         if ((localException instanceof FFSException)) {
/* 1826:2024 */           throw ((FFSException)EJBContext.clone(localException));
/* 1827:     */         }
/* 1828:2026 */         localEJBContext.throwRemote(localException);
/* 1829:     */       }
/* 1830:2028 */       throw new EJBException(localException);
/* 1831:     */     }
/* 1832:     */     finally
/* 1833:     */     {
/* 1834:2032 */       this._comp.setCurrent(localJavaComponent);
/* 1835:     */     }
/* 1836:     */   }
/* 1837:     */   
/* 1838:     */   public void addFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
/* 1839:     */     throws RemoteException, FFSException
/* 1840:     */   {
/* 1841:2041 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1842:2042 */     EJBContext localEJBContext = null;
/* 1843:     */     try
/* 1844:     */     {
/* 1845:2045 */       localEJBContext = this._comp.getPooledInstance();
/* 1846:2046 */       if (localEJBContext == null) {
/* 1847:2048 */         localEJBContext = _create();
/* 1848:     */       }
/* 1849:2050 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1850:2051 */       if (localEJBContext.debug) {
/* 1851:2053 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addFulfillmentInfo"));
/* 1852:     */       }
/* 1853:2056 */       localBPWAdminBean.addFulfillmentInfo(
/* 1854:2057 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties), 
/* 1855:2058 */         (FulfillmentInfo)EJBContext.clone(paramFulfillmentInfo));
/* 1856:     */       
/* 1857:2060 */       localEJBContext.returnToPool();
/* 1858:     */     }
/* 1859:     */     catch (Exception localException)
/* 1860:     */     {
/* 1861:2064 */       if (localEJBContext != null)
/* 1862:     */       {
/* 1863:2066 */         if ((localException instanceof FFSException)) {
/* 1864:2068 */           throw ((FFSException)EJBContext.clone(localException));
/* 1865:     */         }
/* 1866:2070 */         localEJBContext.throwRemote(localException);
/* 1867:     */       }
/* 1868:2072 */       throw new EJBException(localException);
/* 1869:     */     }
/* 1870:     */     finally
/* 1871:     */     {
/* 1872:2076 */       this._comp.setCurrent(localJavaComponent);
/* 1873:     */     }
/* 1874:     */   }
/* 1875:     */   
/* 1876:     */   public void updateFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
/* 1877:     */     throws RemoteException, FFSException
/* 1878:     */   {
/* 1879:2085 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1880:2086 */     EJBContext localEJBContext = null;
/* 1881:     */     try
/* 1882:     */     {
/* 1883:2089 */       localEJBContext = this._comp.getPooledInstance();
/* 1884:2090 */       if (localEJBContext == null) {
/* 1885:2092 */         localEJBContext = _create();
/* 1886:     */       }
/* 1887:2094 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1888:2095 */       if (localEJBContext.debug) {
/* 1889:2097 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updateFulfillmentInfo"));
/* 1890:     */       }
/* 1891:2100 */       localBPWAdminBean.updateFulfillmentInfo(
/* 1892:2101 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties), 
/* 1893:2102 */         (FulfillmentInfo)EJBContext.clone(paramFulfillmentInfo));
/* 1894:     */       
/* 1895:2104 */       localEJBContext.returnToPool();
/* 1896:     */     }
/* 1897:     */     catch (Exception localException)
/* 1898:     */     {
/* 1899:2108 */       if (localEJBContext != null)
/* 1900:     */       {
/* 1901:2110 */         if ((localException instanceof FFSException)) {
/* 1902:2112 */           throw ((FFSException)EJBContext.clone(localException));
/* 1903:     */         }
/* 1904:2114 */         localEJBContext.throwRemote(localException);
/* 1905:     */       }
/* 1906:2116 */       throw new EJBException(localException);
/* 1907:     */     }
/* 1908:     */     finally
/* 1909:     */     {
/* 1910:2120 */       this._comp.setCurrent(localJavaComponent);
/* 1911:     */     }
/* 1912:     */   }
/* 1913:     */   
/* 1914:     */   public void deleteFulfillmentInfo(FFSDBProperties paramFFSDBProperties, int paramInt)
/* 1915:     */     throws RemoteException, FFSException
/* 1916:     */   {
/* 1917:2129 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1918:2130 */     EJBContext localEJBContext = null;
/* 1919:     */     try
/* 1920:     */     {
/* 1921:2133 */       localEJBContext = this._comp.getPooledInstance();
/* 1922:2134 */       if (localEJBContext == null) {
/* 1923:2136 */         localEJBContext = _create();
/* 1924:     */       }
/* 1925:2138 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1926:2139 */       if (localEJBContext.debug) {
/* 1927:2141 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteFulfillmentInfo"));
/* 1928:     */       }
/* 1929:2144 */       localBPWAdminBean.deleteFulfillmentInfo(
/* 1930:2145 */         FFSDBPropertiesHelper.clone(paramFFSDBProperties), 
/* 1931:2146 */         paramInt);
/* 1932:     */       
/* 1933:2148 */       localEJBContext.returnToPool();
/* 1934:     */     }
/* 1935:     */     catch (Exception localException)
/* 1936:     */     {
/* 1937:2152 */       if (localEJBContext != null)
/* 1938:     */       {
/* 1939:2154 */         if ((localException instanceof FFSException)) {
/* 1940:2156 */           throw ((FFSException)EJBContext.clone(localException));
/* 1941:     */         }
/* 1942:2158 */         localEJBContext.throwRemote(localException);
/* 1943:     */       }
/* 1944:2160 */       throw new EJBException(localException);
/* 1945:     */     }
/* 1946:     */     finally
/* 1947:     */     {
/* 1948:2164 */       this._comp.setCurrent(localJavaComponent);
/* 1949:     */     }
/* 1950:     */   }
/* 1951:     */   
/* 1952:     */   public void setDebugLevel(int paramInt)
/* 1953:     */     throws RemoteException, FFSException
/* 1954:     */   {
/* 1955:2172 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1956:2173 */     EJBContext localEJBContext = null;
/* 1957:     */     try
/* 1958:     */     {
/* 1959:2176 */       localEJBContext = this._comp.getPooledInstance();
/* 1960:2177 */       if (localEJBContext == null) {
/* 1961:2179 */         localEJBContext = _create();
/* 1962:     */       }
/* 1963:2181 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 1964:2182 */       if (localEJBContext.debug) {
/* 1965:2184 */         localEJBContext.logger.debug(localEJBContext.debugMsg("setDebugLevel"));
/* 1966:     */       }
/* 1967:2187 */       localBPWAdminBean.setDebugLevel(
/* 1968:2188 */         paramInt);
/* 1969:     */       
/* 1970:2190 */       localEJBContext.returnToPool();
/* 1971:     */     }
/* 1972:     */     catch (Exception localException)
/* 1973:     */     {
/* 1974:2194 */       if (localEJBContext != null)
/* 1975:     */       {
/* 1976:2196 */         if ((localException instanceof FFSException)) {
/* 1977:2198 */           throw ((FFSException)EJBContext.clone(localException));
/* 1978:     */         }
/* 1979:2200 */         localEJBContext.throwRemote(localException);
/* 1980:     */       }
/* 1981:2202 */       throw new EJBException(localException);
/* 1982:     */     }
/* 1983:     */     finally
/* 1984:     */     {
/* 1985:2206 */       this._comp.setCurrent(localJavaComponent);
/* 1986:     */     }
/* 1987:     */   }
/* 1988:     */   
/* 1989:     */   public ProcessingWindowInfo addProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
/* 1990:     */     throws RemoteException, FFSException
/* 1991:     */   {
/* 1992:2214 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1993:2215 */     EJBContext localEJBContext = null;
/* 1994:     */     try
/* 1995:     */     {
/* 1996:2218 */       localEJBContext = this._comp.getPooledInstance();
/* 1997:2219 */       if (localEJBContext == null) {
/* 1998:2221 */         localEJBContext = _create();
/* 1999:     */       }
/* 2000:2223 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2001:2224 */       if (localEJBContext.debug) {
/* 2002:2226 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addProcessingWindow"));
/* 2003:     */       }
/* 2004:2228 */       ProcessingWindowInfo localProcessingWindowInfo2 = localBPWAdminBean
/* 2005:2229 */         .addProcessingWindow(
/* 2006:2230 */         (ProcessingWindowInfo)EJBContext.clone(paramProcessingWindowInfo));
/* 2007:     */       
/* 2008:2232 */       localProcessingWindowInfo2 = (ProcessingWindowInfo)EJBContext.clone(localProcessingWindowInfo2);
/* 2009:2233 */       localEJBContext.returnToPool();
/* 2010:2234 */       return localProcessingWindowInfo2;
/* 2011:     */     }
/* 2012:     */     catch (Exception localException)
/* 2013:     */     {
/* 2014:2238 */       if (localEJBContext != null)
/* 2015:     */       {
/* 2016:2240 */         if ((localException instanceof FFSException)) {
/* 2017:2242 */           throw ((FFSException)EJBContext.clone(localException));
/* 2018:     */         }
/* 2019:2244 */         localEJBContext.throwRemote(localException);
/* 2020:     */       }
/* 2021:2246 */       throw new EJBException(localException);
/* 2022:     */     }
/* 2023:     */     finally
/* 2024:     */     {
/* 2025:2250 */       this._comp.setCurrent(localJavaComponent);
/* 2026:     */     }
/* 2027:     */   }
/* 2028:     */   
/* 2029:     */   public ProcessingWindowInfo modProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
/* 2030:     */     throws RemoteException, FFSException
/* 2031:     */   {
/* 2032:2258 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2033:2259 */     EJBContext localEJBContext = null;
/* 2034:     */     try
/* 2035:     */     {
/* 2036:2262 */       localEJBContext = this._comp.getPooledInstance();
/* 2037:2263 */       if (localEJBContext == null) {
/* 2038:2265 */         localEJBContext = _create();
/* 2039:     */       }
/* 2040:2267 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2041:2268 */       if (localEJBContext.debug) {
/* 2042:2270 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modProcessingWindow"));
/* 2043:     */       }
/* 2044:2272 */       ProcessingWindowInfo localProcessingWindowInfo2 = localBPWAdminBean
/* 2045:2273 */         .modProcessingWindow(
/* 2046:2274 */         (ProcessingWindowInfo)EJBContext.clone(paramProcessingWindowInfo));
/* 2047:     */       
/* 2048:2276 */       localProcessingWindowInfo2 = (ProcessingWindowInfo)EJBContext.clone(localProcessingWindowInfo2);
/* 2049:2277 */       localEJBContext.returnToPool();
/* 2050:2278 */       return localProcessingWindowInfo2;
/* 2051:     */     }
/* 2052:     */     catch (Exception localException)
/* 2053:     */     {
/* 2054:2282 */       if (localEJBContext != null)
/* 2055:     */       {
/* 2056:2284 */         if ((localException instanceof FFSException)) {
/* 2057:2286 */           throw ((FFSException)EJBContext.clone(localException));
/* 2058:     */         }
/* 2059:2288 */         localEJBContext.throwRemote(localException);
/* 2060:     */       }
/* 2061:2290 */       throw new EJBException(localException);
/* 2062:     */     }
/* 2063:     */     finally
/* 2064:     */     {
/* 2065:2294 */       this._comp.setCurrent(localJavaComponent);
/* 2066:     */     }
/* 2067:     */   }
/* 2068:     */   
/* 2069:     */   public ProcessingWindowInfo delProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
/* 2070:     */     throws RemoteException, FFSException
/* 2071:     */   {
/* 2072:2302 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2073:2303 */     EJBContext localEJBContext = null;
/* 2074:     */     try
/* 2075:     */     {
/* 2076:2306 */       localEJBContext = this._comp.getPooledInstance();
/* 2077:2307 */       if (localEJBContext == null) {
/* 2078:2309 */         localEJBContext = _create();
/* 2079:     */       }
/* 2080:2311 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2081:2312 */       if (localEJBContext.debug) {
/* 2082:2314 */         localEJBContext.logger.debug(localEJBContext.debugMsg("delProcessingWindow"));
/* 2083:     */       }
/* 2084:2316 */       ProcessingWindowInfo localProcessingWindowInfo2 = localBPWAdminBean
/* 2085:2317 */         .delProcessingWindow(
/* 2086:2318 */         (ProcessingWindowInfo)EJBContext.clone(paramProcessingWindowInfo));
/* 2087:     */       
/* 2088:2320 */       localProcessingWindowInfo2 = (ProcessingWindowInfo)EJBContext.clone(localProcessingWindowInfo2);
/* 2089:2321 */       localEJBContext.returnToPool();
/* 2090:2322 */       return localProcessingWindowInfo2;
/* 2091:     */     }
/* 2092:     */     catch (Exception localException)
/* 2093:     */     {
/* 2094:2326 */       if (localEJBContext != null)
/* 2095:     */       {
/* 2096:2328 */         if ((localException instanceof FFSException)) {
/* 2097:2330 */           throw ((FFSException)EJBContext.clone(localException));
/* 2098:     */         }
/* 2099:2332 */         localEJBContext.throwRemote(localException);
/* 2100:     */       }
/* 2101:2334 */       throw new EJBException(localException);
/* 2102:     */     }
/* 2103:     */     finally
/* 2104:     */     {
/* 2105:2338 */       this._comp.setCurrent(localJavaComponent);
/* 2106:     */     }
/* 2107:     */   }
/* 2108:     */   
/* 2109:     */   public ProcessingWindowList getProcessingWindows(ProcessingWindowList paramProcessingWindowList)
/* 2110:     */     throws RemoteException, FFSException
/* 2111:     */   {
/* 2112:2346 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2113:2347 */     EJBContext localEJBContext = null;
/* 2114:     */     try
/* 2115:     */     {
/* 2116:2350 */       localEJBContext = this._comp.getPooledInstance();
/* 2117:2351 */       if (localEJBContext == null) {
/* 2118:2353 */         localEJBContext = _create();
/* 2119:     */       }
/* 2120:2355 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2121:2356 */       if (localEJBContext.debug) {
/* 2122:2358 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getProcessingWindows"));
/* 2123:     */       }
/* 2124:2360 */       ProcessingWindowList localProcessingWindowList2 = localBPWAdminBean
/* 2125:2361 */         .getProcessingWindows(
/* 2126:2362 */         (ProcessingWindowList)EJBContext.clone(paramProcessingWindowList));
/* 2127:     */       
/* 2128:2364 */       localProcessingWindowList2 = (ProcessingWindowList)EJBContext.clone(localProcessingWindowList2);
/* 2129:2365 */       localEJBContext.returnToPool();
/* 2130:2366 */       return localProcessingWindowList2;
/* 2131:     */     }
/* 2132:     */     catch (Exception localException)
/* 2133:     */     {
/* 2134:2370 */       if (localEJBContext != null)
/* 2135:     */       {
/* 2136:2372 */         if ((localException instanceof FFSException)) {
/* 2137:2374 */           throw ((FFSException)EJBContext.clone(localException));
/* 2138:     */         }
/* 2139:2376 */         localEJBContext.throwRemote(localException);
/* 2140:     */       }
/* 2141:2378 */       throw new EJBException(localException);
/* 2142:     */     }
/* 2143:     */     finally
/* 2144:     */     {
/* 2145:2382 */       this._comp.setCurrent(localJavaComponent);
/* 2146:     */     }
/* 2147:     */   }
/* 2148:     */   
/* 2149:     */   public InstructionType[] getScheduleConfigByCategory(InstructionType paramInstructionType)
/* 2150:     */     throws RemoteException, FFSException
/* 2151:     */   {
/* 2152:2390 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2153:2391 */     EJBContext localEJBContext = null;
/* 2154:     */     try
/* 2155:     */     {
/* 2156:2394 */       localEJBContext = this._comp.getPooledInstance();
/* 2157:2395 */       if (localEJBContext == null) {
/* 2158:2397 */         localEJBContext = _create();
/* 2159:     */       }
/* 2160:2399 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2161:2400 */       if (localEJBContext.debug) {
/* 2162:2402 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getScheduleConfigByCategory"));
/* 2163:     */       }
/* 2164:2404 */       InstructionType[] arrayOfInstructionType2 = localBPWAdminBean
/* 2165:2405 */         .getScheduleConfigByCategory(
/* 2166:2406 */         (InstructionType)EJBContext.clone(paramInstructionType));
/* 2167:     */       
/* 2168:2408 */       arrayOfInstructionType2 = InstructionTypeSeqHelper.clone(arrayOfInstructionType2);
/* 2169:2409 */       localEJBContext.returnToPool();
/* 2170:2410 */       return arrayOfInstructionType2;
/* 2171:     */     }
/* 2172:     */     catch (Exception localException)
/* 2173:     */     {
/* 2174:2414 */       if (localEJBContext != null)
/* 2175:     */       {
/* 2176:2416 */         if ((localException instanceof FFSException)) {
/* 2177:2418 */           throw ((FFSException)EJBContext.clone(localException));
/* 2178:     */         }
/* 2179:2420 */         localEJBContext.throwRemote(localException);
/* 2180:     */       }
/* 2181:2422 */       throw new EJBException(localException);
/* 2182:     */     }
/* 2183:     */     finally
/* 2184:     */     {
/* 2185:2426 */       this._comp.setCurrent(localJavaComponent);
/* 2186:     */     }
/* 2187:     */   }
/* 2188:     */   
/* 2189:     */   public void addScheduleConfig(InstructionType paramInstructionType)
/* 2190:     */     throws RemoteException, FFSException
/* 2191:     */   {
/* 2192:2434 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2193:2435 */     EJBContext localEJBContext = null;
/* 2194:     */     try
/* 2195:     */     {
/* 2196:2438 */       localEJBContext = this._comp.getPooledInstance();
/* 2197:2439 */       if (localEJBContext == null) {
/* 2198:2441 */         localEJBContext = _create();
/* 2199:     */       }
/* 2200:2443 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2201:2444 */       if (localEJBContext.debug) {
/* 2202:2446 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addScheduleConfig"));
/* 2203:     */       }
/* 2204:2449 */       localBPWAdminBean.addScheduleConfig(
/* 2205:2450 */         (InstructionType)EJBContext.clone(paramInstructionType));
/* 2206:     */       
/* 2207:2452 */       localEJBContext.returnToPool();
/* 2208:     */     }
/* 2209:     */     catch (Exception localException)
/* 2210:     */     {
/* 2211:2456 */       if (localEJBContext != null)
/* 2212:     */       {
/* 2213:2458 */         if ((localException instanceof FFSException)) {
/* 2214:2460 */           throw ((FFSException)EJBContext.clone(localException));
/* 2215:     */         }
/* 2216:2462 */         localEJBContext.throwRemote(localException);
/* 2217:     */       }
/* 2218:2464 */       throw new EJBException(localException);
/* 2219:     */     }
/* 2220:     */     finally
/* 2221:     */     {
/* 2222:2468 */       this._comp.setCurrent(localJavaComponent);
/* 2223:     */     }
/* 2224:     */   }
/* 2225:     */   
/* 2226:     */   public void deleteScheduleConfig(InstructionType paramInstructionType)
/* 2227:     */     throws RemoteException, FFSException
/* 2228:     */   {
/* 2229:2476 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2230:2477 */     EJBContext localEJBContext = null;
/* 2231:     */     try
/* 2232:     */     {
/* 2233:2480 */       localEJBContext = this._comp.getPooledInstance();
/* 2234:2481 */       if (localEJBContext == null) {
/* 2235:2483 */         localEJBContext = _create();
/* 2236:     */       }
/* 2237:2485 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2238:2486 */       if (localEJBContext.debug) {
/* 2239:2488 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteScheduleConfig"));
/* 2240:     */       }
/* 2241:2491 */       localBPWAdminBean.deleteScheduleConfig(
/* 2242:2492 */         (InstructionType)EJBContext.clone(paramInstructionType));
/* 2243:     */       
/* 2244:2494 */       localEJBContext.returnToPool();
/* 2245:     */     }
/* 2246:     */     catch (Exception localException)
/* 2247:     */     {
/* 2248:2498 */       if (localEJBContext != null)
/* 2249:     */       {
/* 2250:2500 */         if ((localException instanceof FFSException)) {
/* 2251:2502 */           throw ((FFSException)EJBContext.clone(localException));
/* 2252:     */         }
/* 2253:2504 */         localEJBContext.throwRemote(localException);
/* 2254:     */       }
/* 2255:2506 */       throw new EJBException(localException);
/* 2256:     */     }
/* 2257:     */     finally
/* 2258:     */     {
/* 2259:2510 */       this._comp.setCurrent(localJavaComponent);
/* 2260:     */     }
/* 2261:     */   }
/* 2262:     */   
/* 2263:     */   public CutOffInfo deleteCutOff(CutOffInfo paramCutOffInfo)
/* 2264:     */     throws RemoteException, FFSException
/* 2265:     */   {
/* 2266:2518 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2267:2519 */     EJBContext localEJBContext = null;
/* 2268:     */     try
/* 2269:     */     {
/* 2270:2522 */       localEJBContext = this._comp.getPooledInstance();
/* 2271:2523 */       if (localEJBContext == null) {
/* 2272:2525 */         localEJBContext = _create();
/* 2273:     */       }
/* 2274:2527 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2275:2528 */       if (localEJBContext.debug) {
/* 2276:2530 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCutOff"));
/* 2277:     */       }
/* 2278:2532 */       CutOffInfo localCutOffInfo2 = localBPWAdminBean
/* 2279:2533 */         .deleteCutOff(
/* 2280:2534 */         (CutOffInfo)EJBContext.clone(paramCutOffInfo));
/* 2281:     */       
/* 2282:2536 */       localCutOffInfo2 = (CutOffInfo)EJBContext.clone(localCutOffInfo2);
/* 2283:2537 */       localEJBContext.returnToPool();
/* 2284:2538 */       return localCutOffInfo2;
/* 2285:     */     }
/* 2286:     */     catch (Exception localException)
/* 2287:     */     {
/* 2288:2542 */       if (localEJBContext != null)
/* 2289:     */       {
/* 2290:2544 */         if ((localException instanceof FFSException)) {
/* 2291:2546 */           throw ((FFSException)EJBContext.clone(localException));
/* 2292:     */         }
/* 2293:2548 */         localEJBContext.throwRemote(localException);
/* 2294:     */       }
/* 2295:2550 */       throw new EJBException(localException);
/* 2296:     */     }
/* 2297:     */     finally
/* 2298:     */     {
/* 2299:2554 */       this._comp.setCurrent(localJavaComponent);
/* 2300:     */     }
/* 2301:     */   }
/* 2302:     */   
/* 2303:     */   public CutOffInfo addCutOff(CutOffInfo paramCutOffInfo)
/* 2304:     */     throws RemoteException, FFSException
/* 2305:     */   {
/* 2306:2562 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2307:2563 */     EJBContext localEJBContext = null;
/* 2308:     */     try
/* 2309:     */     {
/* 2310:2566 */       localEJBContext = this._comp.getPooledInstance();
/* 2311:2567 */       if (localEJBContext == null) {
/* 2312:2569 */         localEJBContext = _create();
/* 2313:     */       }
/* 2314:2571 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2315:2572 */       if (localEJBContext.debug) {
/* 2316:2574 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCutOff"));
/* 2317:     */       }
/* 2318:2576 */       CutOffInfo localCutOffInfo2 = localBPWAdminBean
/* 2319:2577 */         .addCutOff(
/* 2320:2578 */         (CutOffInfo)EJBContext.clone(paramCutOffInfo));
/* 2321:     */       
/* 2322:2580 */       localCutOffInfo2 = (CutOffInfo)EJBContext.clone(localCutOffInfo2);
/* 2323:2581 */       localEJBContext.returnToPool();
/* 2324:2582 */       return localCutOffInfo2;
/* 2325:     */     }
/* 2326:     */     catch (Exception localException)
/* 2327:     */     {
/* 2328:2586 */       if (localEJBContext != null)
/* 2329:     */       {
/* 2330:2588 */         if ((localException instanceof FFSException)) {
/* 2331:2590 */           throw ((FFSException)EJBContext.clone(localException));
/* 2332:     */         }
/* 2333:2592 */         localEJBContext.throwRemote(localException);
/* 2334:     */       }
/* 2335:2594 */       throw new EJBException(localException);
/* 2336:     */     }
/* 2337:     */     finally
/* 2338:     */     {
/* 2339:2598 */       this._comp.setCurrent(localJavaComponent);
/* 2340:     */     }
/* 2341:     */   }
/* 2342:     */   
/* 2343:     */   public CutOffInfo modCutOff(CutOffInfo paramCutOffInfo)
/* 2344:     */     throws RemoteException, FFSException
/* 2345:     */   {
/* 2346:2606 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2347:2607 */     EJBContext localEJBContext = null;
/* 2348:     */     try
/* 2349:     */     {
/* 2350:2610 */       localEJBContext = this._comp.getPooledInstance();
/* 2351:2611 */       if (localEJBContext == null) {
/* 2352:2613 */         localEJBContext = _create();
/* 2353:     */       }
/* 2354:2615 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2355:2616 */       if (localEJBContext.debug) {
/* 2356:2618 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modCutOff"));
/* 2357:     */       }
/* 2358:2620 */       CutOffInfo localCutOffInfo2 = localBPWAdminBean
/* 2359:2621 */         .modCutOff(
/* 2360:2622 */         (CutOffInfo)EJBContext.clone(paramCutOffInfo));
/* 2361:     */       
/* 2362:2624 */       localCutOffInfo2 = (CutOffInfo)EJBContext.clone(localCutOffInfo2);
/* 2363:2625 */       localEJBContext.returnToPool();
/* 2364:2626 */       return localCutOffInfo2;
/* 2365:     */     }
/* 2366:     */     catch (Exception localException)
/* 2367:     */     {
/* 2368:2630 */       if (localEJBContext != null)
/* 2369:     */       {
/* 2370:2632 */         if ((localException instanceof FFSException)) {
/* 2371:2634 */           throw ((FFSException)EJBContext.clone(localException));
/* 2372:     */         }
/* 2373:2636 */         localEJBContext.throwRemote(localException);
/* 2374:     */       }
/* 2375:2638 */       throw new EJBException(localException);
/* 2376:     */     }
/* 2377:     */     finally
/* 2378:     */     {
/* 2379:2642 */       this._comp.setCurrent(localJavaComponent);
/* 2380:     */     }
/* 2381:     */   }
/* 2382:     */   
/* 2383:     */   public CutOffInfo getCutOff(CutOffInfo paramCutOffInfo)
/* 2384:     */     throws RemoteException, FFSException
/* 2385:     */   {
/* 2386:2650 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2387:2651 */     EJBContext localEJBContext = null;
/* 2388:     */     try
/* 2389:     */     {
/* 2390:2654 */       localEJBContext = this._comp.getPooledInstance();
/* 2391:2655 */       if (localEJBContext == null) {
/* 2392:2657 */         localEJBContext = _create();
/* 2393:     */       }
/* 2394:2659 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2395:2660 */       if (localEJBContext.debug) {
/* 2396:2662 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCutOff"));
/* 2397:     */       }
/* 2398:2664 */       CutOffInfo localCutOffInfo2 = localBPWAdminBean
/* 2399:2665 */         .getCutOff(
/* 2400:2666 */         (CutOffInfo)EJBContext.clone(paramCutOffInfo));
/* 2401:     */       
/* 2402:2668 */       localCutOffInfo2 = (CutOffInfo)EJBContext.clone(localCutOffInfo2);
/* 2403:2669 */       localEJBContext.returnToPool();
/* 2404:2670 */       return localCutOffInfo2;
/* 2405:     */     }
/* 2406:     */     catch (Exception localException)
/* 2407:     */     {
/* 2408:2674 */       if (localEJBContext != null)
/* 2409:     */       {
/* 2410:2676 */         if ((localException instanceof FFSException)) {
/* 2411:2678 */           throw ((FFSException)EJBContext.clone(localException));
/* 2412:     */         }
/* 2413:2680 */         localEJBContext.throwRemote(localException);
/* 2414:     */       }
/* 2415:2682 */       throw new EJBException(localException);
/* 2416:     */     }
/* 2417:     */     finally
/* 2418:     */     {
/* 2419:2686 */       this._comp.setCurrent(localJavaComponent);
/* 2420:     */     }
/* 2421:     */   }
/* 2422:     */   
/* 2423:     */   public CutOffInfoList getCutOffList(CutOffInfoList paramCutOffInfoList)
/* 2424:     */     throws RemoteException, FFSException
/* 2425:     */   {
/* 2426:2694 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2427:2695 */     EJBContext localEJBContext = null;
/* 2428:     */     try
/* 2429:     */     {
/* 2430:2698 */       localEJBContext = this._comp.getPooledInstance();
/* 2431:2699 */       if (localEJBContext == null) {
/* 2432:2701 */         localEJBContext = _create();
/* 2433:     */       }
/* 2434:2703 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2435:2704 */       if (localEJBContext.debug) {
/* 2436:2706 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCutOffList"));
/* 2437:     */       }
/* 2438:2708 */       CutOffInfoList localCutOffInfoList2 = localBPWAdminBean
/* 2439:2709 */         .getCutOffList(
/* 2440:2710 */         (CutOffInfoList)EJBContext.clone(paramCutOffInfoList));
/* 2441:     */       
/* 2442:2712 */       localCutOffInfoList2 = (CutOffInfoList)EJBContext.clone(localCutOffInfoList2);
/* 2443:2713 */       localEJBContext.returnToPool();
/* 2444:2714 */       return localCutOffInfoList2;
/* 2445:     */     }
/* 2446:     */     catch (Exception localException)
/* 2447:     */     {
/* 2448:2718 */       if (localEJBContext != null)
/* 2449:     */       {
/* 2450:2720 */         if ((localException instanceof FFSException)) {
/* 2451:2722 */           throw ((FFSException)EJBContext.clone(localException));
/* 2452:     */         }
/* 2453:2724 */         localEJBContext.throwRemote(localException);
/* 2454:     */       }
/* 2455:2726 */       throw new EJBException(localException);
/* 2456:     */     }
/* 2457:     */     finally
/* 2458:     */     {
/* 2459:2730 */       this._comp.setCurrent(localJavaComponent);
/* 2460:     */     }
/* 2461:     */   }
/* 2462:     */   
/* 2463:     */   public ScheduleCategoryInfo getScheduleCategoryInfo(String paramString1, String paramString2)
/* 2464:     */     throws RemoteException, FFSException
/* 2465:     */   {
/* 2466:2739 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2467:2740 */     EJBContext localEJBContext = null;
/* 2468:     */     try
/* 2469:     */     {
/* 2470:2743 */       localEJBContext = this._comp.getPooledInstance();
/* 2471:2744 */       if (localEJBContext == null) {
/* 2472:2746 */         localEJBContext = _create();
/* 2473:     */       }
/* 2474:2748 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2475:2749 */       if (localEJBContext.debug) {
/* 2476:2751 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getScheduleCategoryInfo"));
/* 2477:     */       }
/* 2478:2753 */       ScheduleCategoryInfo localScheduleCategoryInfo2 = localBPWAdminBean
/* 2479:2754 */         .getScheduleCategoryInfo(
/* 2480:2755 */         paramString1, 
/* 2481:2756 */         paramString2);
/* 2482:     */       
/* 2483:2758 */       localScheduleCategoryInfo2 = (ScheduleCategoryInfo)EJBContext.clone(localScheduleCategoryInfo2);
/* 2484:2759 */       localEJBContext.returnToPool();
/* 2485:2760 */       return localScheduleCategoryInfo2;
/* 2486:     */     }
/* 2487:     */     catch (Exception localException)
/* 2488:     */     {
/* 2489:2764 */       if (localEJBContext != null)
/* 2490:     */       {
/* 2491:2766 */         if ((localException instanceof FFSException)) {
/* 2492:2768 */           throw ((FFSException)EJBContext.clone(localException));
/* 2493:     */         }
/* 2494:2770 */         localEJBContext.throwRemote(localException);
/* 2495:     */       }
/* 2496:2772 */       throw new EJBException(localException);
/* 2497:     */     }
/* 2498:     */     finally
/* 2499:     */     {
/* 2500:2776 */       this._comp.setCurrent(localJavaComponent);
/* 2501:     */     }
/* 2502:     */   }
/* 2503:     */   
/* 2504:     */   public ScheduleCategoryInfo modScheduleCategoryInfo(ScheduleCategoryInfo paramScheduleCategoryInfo)
/* 2505:     */     throws RemoteException, FFSException
/* 2506:     */   {
/* 2507:2784 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2508:2785 */     EJBContext localEJBContext = null;
/* 2509:     */     try
/* 2510:     */     {
/* 2511:2788 */       localEJBContext = this._comp.getPooledInstance();
/* 2512:2789 */       if (localEJBContext == null) {
/* 2513:2791 */         localEJBContext = _create();
/* 2514:     */       }
/* 2515:2793 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2516:2794 */       if (localEJBContext.debug) {
/* 2517:2796 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modScheduleCategoryInfo"));
/* 2518:     */       }
/* 2519:2798 */       ScheduleCategoryInfo localScheduleCategoryInfo2 = localBPWAdminBean
/* 2520:2799 */         .modScheduleCategoryInfo(
/* 2521:2800 */         (ScheduleCategoryInfo)EJBContext.clone(paramScheduleCategoryInfo));
/* 2522:     */       
/* 2523:2802 */       localScheduleCategoryInfo2 = (ScheduleCategoryInfo)EJBContext.clone(localScheduleCategoryInfo2);
/* 2524:2803 */       localEJBContext.returnToPool();
/* 2525:2804 */       return localScheduleCategoryInfo2;
/* 2526:     */     }
/* 2527:     */     catch (Exception localException)
/* 2528:     */     {
/* 2529:2808 */       if (localEJBContext != null)
/* 2530:     */       {
/* 2531:2810 */         if ((localException instanceof FFSException)) {
/* 2532:2812 */           throw ((FFSException)EJBContext.clone(localException));
/* 2533:     */         }
/* 2534:2814 */         localEJBContext.throwRemote(localException);
/* 2535:     */       }
/* 2536:2816 */       throw new EJBException(localException);
/* 2537:     */     }
/* 2538:     */     finally
/* 2539:     */     {
/* 2540:2820 */       this._comp.setCurrent(localJavaComponent);
/* 2541:     */     }
/* 2542:     */   }
/* 2543:     */   
/* 2544:     */   public CutOffActivityInfoList getCutOffActivityList(CutOffActivityInfoList paramCutOffActivityInfoList)
/* 2545:     */     throws RemoteException, FFSException
/* 2546:     */   {
/* 2547:2828 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2548:2829 */     EJBContext localEJBContext = null;
/* 2549:     */     try
/* 2550:     */     {
/* 2551:2832 */       localEJBContext = this._comp.getPooledInstance();
/* 2552:2833 */       if (localEJBContext == null) {
/* 2553:2835 */         localEJBContext = _create();
/* 2554:     */       }
/* 2555:2837 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2556:2838 */       if (localEJBContext.debug) {
/* 2557:2840 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCutOffActivityList"));
/* 2558:     */       }
/* 2559:2842 */       CutOffActivityInfoList localCutOffActivityInfoList2 = localBPWAdminBean
/* 2560:2843 */         .getCutOffActivityList(
/* 2561:2844 */         (CutOffActivityInfoList)EJBContext.clone(paramCutOffActivityInfoList));
/* 2562:     */       
/* 2563:2846 */       localCutOffActivityInfoList2 = (CutOffActivityInfoList)EJBContext.clone(localCutOffActivityInfoList2);
/* 2564:2847 */       localEJBContext.returnToPool();
/* 2565:2848 */       return localCutOffActivityInfoList2;
/* 2566:     */     }
/* 2567:     */     catch (Exception localException)
/* 2568:     */     {
/* 2569:2852 */       if (localEJBContext != null)
/* 2570:     */       {
/* 2571:2854 */         if ((localException instanceof FFSException)) {
/* 2572:2856 */           throw ((FFSException)EJBContext.clone(localException));
/* 2573:     */         }
/* 2574:2858 */         localEJBContext.throwRemote(localException);
/* 2575:     */       }
/* 2576:2860 */       throw new EJBException(localException);
/* 2577:     */     }
/* 2578:     */     finally
/* 2579:     */     {
/* 2580:2864 */       this._comp.setCurrent(localJavaComponent);
/* 2581:     */     }
/* 2582:     */   }
/* 2583:     */   
/* 2584:     */   public ScheduleActivityList getScheduleActivityList(ScheduleActivityList paramScheduleActivityList)
/* 2585:     */     throws RemoteException, FFSException
/* 2586:     */   {
/* 2587:2872 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2588:2873 */     EJBContext localEJBContext = null;
/* 2589:     */     try
/* 2590:     */     {
/* 2591:2876 */       localEJBContext = this._comp.getPooledInstance();
/* 2592:2877 */       if (localEJBContext == null) {
/* 2593:2879 */         localEJBContext = _create();
/* 2594:     */       }
/* 2595:2881 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2596:2882 */       if (localEJBContext.debug) {
/* 2597:2884 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getScheduleActivityList"));
/* 2598:     */       }
/* 2599:2886 */       ScheduleActivityList localScheduleActivityList2 = localBPWAdminBean
/* 2600:2887 */         .getScheduleActivityList(
/* 2601:2888 */         (ScheduleActivityList)EJBContext.clone(paramScheduleActivityList));
/* 2602:     */       
/* 2603:2890 */       localScheduleActivityList2 = (ScheduleActivityList)EJBContext.clone(localScheduleActivityList2);
/* 2604:2891 */       localEJBContext.returnToPool();
/* 2605:2892 */       return localScheduleActivityList2;
/* 2606:     */     }
/* 2607:     */     catch (Exception localException)
/* 2608:     */     {
/* 2609:2896 */       if (localEJBContext != null)
/* 2610:     */       {
/* 2611:2898 */         if ((localException instanceof FFSException)) {
/* 2612:2900 */           throw ((FFSException)EJBContext.clone(localException));
/* 2613:     */         }
/* 2614:2902 */         localEJBContext.throwRemote(localException);
/* 2615:     */       }
/* 2616:2904 */       throw new EJBException(localException);
/* 2617:     */     }
/* 2618:     */     finally
/* 2619:     */     {
/* 2620:2908 */       this._comp.setCurrent(localJavaComponent);
/* 2621:     */     }
/* 2622:     */   }
/* 2623:     */   
/* 2624:     */   public void rerunCutOff(String paramString1, String paramString2, String paramString3, String paramString4)
/* 2625:     */     throws RemoteException, FFSException
/* 2626:     */   {
/* 2627:2919 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2628:2920 */     EJBContext localEJBContext = null;
/* 2629:     */     try
/* 2630:     */     {
/* 2631:2923 */       localEJBContext = this._comp.getPooledInstance();
/* 2632:2924 */       if (localEJBContext == null) {
/* 2633:2926 */         localEJBContext = _create();
/* 2634:     */       }
/* 2635:2928 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2636:2929 */       if (localEJBContext.debug) {
/* 2637:2931 */         localEJBContext.logger.debug(localEJBContext.debugMsg("rerunCutOff"));
/* 2638:     */       }
/* 2639:2934 */       localBPWAdminBean.rerunCutOff(
/* 2640:2935 */         paramString1, 
/* 2641:2936 */         paramString2, 
/* 2642:2937 */         paramString3, 
/* 2643:2938 */         paramString4);
/* 2644:     */       
/* 2645:2940 */       localEJBContext.returnToPool();
/* 2646:     */     }
/* 2647:     */     catch (Exception localException)
/* 2648:     */     {
/* 2649:2944 */       if (localEJBContext != null)
/* 2650:     */       {
/* 2651:2946 */         if ((localException instanceof FFSException)) {
/* 2652:2948 */           throw ((FFSException)EJBContext.clone(localException));
/* 2653:     */         }
/* 2654:2950 */         localEJBContext.throwRemote(localException);
/* 2655:     */       }
/* 2656:2952 */       throw new EJBException(localException);
/* 2657:     */     }
/* 2658:     */     finally
/* 2659:     */     {
/* 2660:2956 */       this._comp.setCurrent(localJavaComponent);
/* 2661:     */     }
/* 2662:     */   }
/* 2663:     */   
/* 2664:     */   public String getGeneratedFileName(String paramString1, String paramString2, String paramString3)
/* 2665:     */     throws RemoteException, FFSException
/* 2666:     */   {
/* 2667:2966 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2668:2967 */     EJBContext localEJBContext = null;
/* 2669:     */     try
/* 2670:     */     {
/* 2671:2970 */       localEJBContext = this._comp.getPooledInstance();
/* 2672:2971 */       if (localEJBContext == null) {
/* 2673:2973 */         localEJBContext = _create();
/* 2674:     */       }
/* 2675:2975 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2676:2976 */       if (localEJBContext.debug) {
/* 2677:2978 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getGeneratedFileName"));
/* 2678:     */       }
/* 2679:2980 */       String str2 = localBPWAdminBean
/* 2680:2981 */         .getGeneratedFileName(
/* 2681:2982 */         paramString1, 
/* 2682:2983 */         paramString2, 
/* 2683:2984 */         paramString3);
/* 2684:     */       
/* 2685:2986 */       localEJBContext.returnToPool();
/* 2686:2987 */       return str2;
/* 2687:     */     }
/* 2688:     */     catch (Exception localException)
/* 2689:     */     {
/* 2690:2991 */       if (localEJBContext != null)
/* 2691:     */       {
/* 2692:2993 */         if ((localException instanceof FFSException)) {
/* 2693:2995 */           throw ((FFSException)EJBContext.clone(localException));
/* 2694:     */         }
/* 2695:2997 */         localEJBContext.throwRemote(localException);
/* 2696:     */       }
/* 2697:2999 */       throw new EJBException(localException);
/* 2698:     */     }
/* 2699:     */     finally
/* 2700:     */     {
/* 2701:3003 */       this._comp.setCurrent(localJavaComponent);
/* 2702:     */     }
/* 2703:     */   }
/* 2704:     */   
/* 2705:     */   public SmartCalendarFile importCalendar(SmartCalendarFile paramSmartCalendarFile)
/* 2706:     */     throws RemoteException, FFSException
/* 2707:     */   {
/* 2708:3011 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2709:3012 */     EJBContext localEJBContext = null;
/* 2710:     */     try
/* 2711:     */     {
/* 2712:3015 */       localEJBContext = this._comp.getPooledInstance();
/* 2713:3016 */       if (localEJBContext == null) {
/* 2714:3018 */         localEJBContext = _create();
/* 2715:     */       }
/* 2716:3020 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2717:3021 */       if (localEJBContext.debug) {
/* 2718:3023 */         localEJBContext.logger.debug(localEJBContext.debugMsg("importCalendar"));
/* 2719:     */       }
/* 2720:3025 */       SmartCalendarFile localSmartCalendarFile2 = localBPWAdminBean
/* 2721:3026 */         .importCalendar(
/* 2722:3027 */         (SmartCalendarFile)EJBContext.clone(paramSmartCalendarFile));
/* 2723:     */       
/* 2724:3029 */       localSmartCalendarFile2 = (SmartCalendarFile)EJBContext.clone(localSmartCalendarFile2);
/* 2725:3030 */       localEJBContext.returnToPool();
/* 2726:3031 */       return localSmartCalendarFile2;
/* 2727:     */     }
/* 2728:     */     catch (Exception localException)
/* 2729:     */     {
/* 2730:3035 */       if (localEJBContext != null)
/* 2731:     */       {
/* 2732:3037 */         if ((localException instanceof FFSException)) {
/* 2733:3039 */           throw ((FFSException)EJBContext.clone(localException));
/* 2734:     */         }
/* 2735:3041 */         localEJBContext.throwRemote(localException);
/* 2736:     */       }
/* 2737:3043 */       throw new EJBException(localException);
/* 2738:     */     }
/* 2739:     */     finally
/* 2740:     */     {
/* 2741:3047 */       this._comp.setCurrent(localJavaComponent);
/* 2742:     */     }
/* 2743:     */   }
/* 2744:     */   
/* 2745:     */   public SmartCalendarFile exportCalendar(SmartCalendarFile paramSmartCalendarFile)
/* 2746:     */     throws RemoteException, FFSException
/* 2747:     */   {
/* 2748:3055 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2749:3056 */     EJBContext localEJBContext = null;
/* 2750:     */     try
/* 2751:     */     {
/* 2752:3059 */       localEJBContext = this._comp.getPooledInstance();
/* 2753:3060 */       if (localEJBContext == null) {
/* 2754:3062 */         localEJBContext = _create();
/* 2755:     */       }
/* 2756:3064 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2757:3065 */       if (localEJBContext.debug) {
/* 2758:3067 */         localEJBContext.logger.debug(localEJBContext.debugMsg("exportCalendar"));
/* 2759:     */       }
/* 2760:3069 */       SmartCalendarFile localSmartCalendarFile2 = localBPWAdminBean
/* 2761:3070 */         .exportCalendar(
/* 2762:3071 */         (SmartCalendarFile)EJBContext.clone(paramSmartCalendarFile));
/* 2763:     */       
/* 2764:3073 */       localSmartCalendarFile2 = (SmartCalendarFile)EJBContext.clone(localSmartCalendarFile2);
/* 2765:3074 */       localEJBContext.returnToPool();
/* 2766:3075 */       return localSmartCalendarFile2;
/* 2767:     */     }
/* 2768:     */     catch (Exception localException)
/* 2769:     */     {
/* 2770:3079 */       if (localEJBContext != null)
/* 2771:     */       {
/* 2772:3081 */         if ((localException instanceof FFSException)) {
/* 2773:3083 */           throw ((FFSException)EJBContext.clone(localException));
/* 2774:     */         }
/* 2775:3085 */         localEJBContext.throwRemote(localException);
/* 2776:     */       }
/* 2777:3087 */       throw new EJBException(localException);
/* 2778:     */     }
/* 2779:     */     finally
/* 2780:     */     {
/* 2781:3091 */       this._comp.setCurrent(localJavaComponent);
/* 2782:     */     }
/* 2783:     */   }
/* 2784:     */   
/* 2785:     */   public PayeeInfo addGlobalPayee(PayeeInfo paramPayeeInfo)
/* 2786:     */     throws RemoteException, FFSException
/* 2787:     */   {
/* 2788:3099 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2789:3100 */     EJBContext localEJBContext = null;
/* 2790:     */     try
/* 2791:     */     {
/* 2792:3103 */       localEJBContext = this._comp.getPooledInstance();
/* 2793:3104 */       if (localEJBContext == null) {
/* 2794:3106 */         localEJBContext = _create();
/* 2795:     */       }
/* 2796:3108 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2797:3109 */       if (localEJBContext.debug) {
/* 2798:3111 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addGlobalPayee"));
/* 2799:     */       }
/* 2800:3113 */       PayeeInfo localPayeeInfo2 = localBPWAdminBean
/* 2801:3114 */         .addGlobalPayee(
/* 2802:3115 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo));
/* 2803:     */       
/* 2804:3117 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 2805:3118 */       localEJBContext.returnToPool();
/* 2806:3119 */       return localPayeeInfo2;
/* 2807:     */     }
/* 2808:     */     catch (Exception localException)
/* 2809:     */     {
/* 2810:3123 */       if (localEJBContext != null)
/* 2811:     */       {
/* 2812:3125 */         if ((localException instanceof FFSException)) {
/* 2813:3127 */           throw ((FFSException)EJBContext.clone(localException));
/* 2814:     */         }
/* 2815:3129 */         localEJBContext.throwRemote(localException);
/* 2816:     */       }
/* 2817:3131 */       throw new EJBException(localException);
/* 2818:     */     }
/* 2819:     */     finally
/* 2820:     */     {
/* 2821:3135 */       this._comp.setCurrent(localJavaComponent);
/* 2822:     */     }
/* 2823:     */   }
/* 2824:     */   
/* 2825:     */   public PayeeInfo deleteGlobalPayee(PayeeInfo paramPayeeInfo)
/* 2826:     */     throws RemoteException, FFSException
/* 2827:     */   {
/* 2828:3143 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2829:3144 */     EJBContext localEJBContext = null;
/* 2830:     */     try
/* 2831:     */     {
/* 2832:3147 */       localEJBContext = this._comp.getPooledInstance();
/* 2833:3148 */       if (localEJBContext == null) {
/* 2834:3150 */         localEJBContext = _create();
/* 2835:     */       }
/* 2836:3152 */       BPWAdminBean localBPWAdminBean = (BPWAdminBean)localEJBContext.getBean();
/* 2837:3153 */       if (localEJBContext.debug) {
/* 2838:3155 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteGlobalPayee"));
/* 2839:     */       }
/* 2840:3157 */       PayeeInfo localPayeeInfo2 = localBPWAdminBean
/* 2841:3158 */         .deleteGlobalPayee(
/* 2842:3159 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo));
/* 2843:     */       
/* 2844:3161 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 2845:3162 */       localEJBContext.returnToPool();
/* 2846:3163 */       return localPayeeInfo2;
/* 2847:     */     }
/* 2848:     */     catch (Exception localException)
/* 2849:     */     {
/* 2850:3167 */       if (localEJBContext != null)
/* 2851:     */       {
/* 2852:3169 */         if ((localException instanceof FFSException)) {
/* 2853:3171 */           throw ((FFSException)EJBContext.clone(localException));
/* 2854:     */         }
/* 2855:3173 */         localEJBContext.throwRemote(localException);
/* 2856:     */       }
/* 2857:3175 */       throw new EJBException(localException);
/* 2858:     */     }
/* 2859:     */     finally
/* 2860:     */     {
/* 2861:3179 */       this._comp.setCurrent(localJavaComponent);
/* 2862:     */     }
/* 2863:     */   }
/* 2864:     */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB._lwc_rs_BPWAdmin_BPWAdminBean
 * JD-Core Version:    0.7.0.1
 */