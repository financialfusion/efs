/*    1:     */ package com.ffusion.alert.adminEJB;
/*    2:     */ 
/*    3:     */ import BCD.BinaryHelper;
/*    4:     */ import com.ffusion.alert.interfaces.AEApplicationInfo;
/*    5:     */ import com.ffusion.alert.interfaces.AEApplicationInfoSeqHelper;
/*    6:     */ import com.ffusion.alert.interfaces.AEDBParams;
/*    7:     */ import com.ffusion.alert.interfaces.AEException;
/*    8:     */ import com.ffusion.alert.interfaces.AEExceptionHelper;
/*    9:     */ import com.ffusion.alert.interfaces.AEScheduleInfo;
/*   10:     */ import com.ffusion.alert.interfaces.AEServerInfo;
/*   11:     */ import com.ffusion.alert.interfaces.AEServerInfoSeqHelper;
/*   12:     */ import com.ffusion.alert.interfaces.IAEAlertDefinition;
/*   13:     */ import com.ffusion.alert.interfaces.IAEAlertDefinitionSeqHelper;
/*   14:     */ import com.ffusion.alert.interfaces.IAEAuditInfo;
/*   15:     */ import com.ffusion.alert.interfaces.IAEAuditInfoSeqHelper;
/*   16:     */ import com.ffusion.alert.interfaces.IAEChannelInfo;
/*   17:     */ import com.ffusion.alert.interfaces.IAEChannelInfoSeqHelper;
/*   18:     */ import com.ffusion.alert.interfaces.IAEDeliveryInfo;
/*   19:     */ import com.ffusion.alert.interfaces.IAEDeliveryInfoSeqHelper;
/*   20:     */ import com.sybase.CORBA.ObjectRef;
/*   21:     */ import com.sybase.CORBA.UserException;
/*   22:     */ import com.sybase.CORBA._Request;
/*   23:     */ import com.sybase.ejb.EJBObject;
/*   24:     */ import java.util.Date;
/*   25:     */ import java.util.Properties;
/*   26:     */ import org.omg.CORBA.SystemException;
/*   27:     */ import org.omg.CORBA.TRANSIENT;
/*   28:     */ import org.omg.CORBA.portable.IDLEntity;
/*   29:     */ import org.omg.CORBA.portable.InputStream;
/*   30:     */ import org.omg.CORBA.portable.OutputStream;
/*   31:     */ 
/*   32:     */ public class IAEAlertAdmin_Stub
/*   33:     */   extends EJBObject
/*   34:     */   implements IAEAlertAdmin, IDLEntity
/*   35:     */ {
/*   36:     */   public IAEAlertAdmin_Stub(ObjectRef paramObjectRef)
/*   37:     */   {
/*   38:  21 */     super("RMI:com.ffusion.alert.adminEJB.IAEAlertAdmin:0000000000000000", paramObjectRef);
/*   39:     */   }
/*   40:     */   
/*   41:     */   public void shutdown()
/*   42:     */     throws java.rmi.RemoteException, AEException
/*   43:     */   {
/*   44:  27 */     for (int i = 1;; i++)
/*   45:     */     {
/*   46:  29 */       _Request local_Request = null;
/*   47:     */       try
/*   48:     */       {
/*   49:  32 */         local_Request = __request("shutdown__", "shutdown__");
/*   50:  33 */         local_Request.invoke();
/*   51:  34 */         return;
/*   52:     */       }
/*   53:     */       catch (TRANSIENT localTRANSIENT)
/*   54:     */       {
/*   55:  38 */         if (i == 10) {
/*   56:  40 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   57:     */         }
/*   58:     */       }
/*   59:     */       catch (UserException localUserException)
/*   60:     */       {
/*   61:  45 */         if (local_Request.isRMI())
/*   62:     */         {
/*   63:  47 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*   64:  49 */             throw ((AEException)local_Request.read_value(AEException.class));
/*   65:     */           }
/*   66:     */         }
/*   67:  54 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*   68:  56 */           throw AEExceptionHelper.read(localUserException.input);
/*   69:     */         }
/*   70:  59 */         throw new java.rmi.RemoteException(localUserException.type);
/*   71:     */       }
/*   72:     */       catch (SystemException localSystemException)
/*   73:     */       {
/*   74:  63 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   75:     */       }
/*   76:     */       finally
/*   77:     */       {
/*   78:  67 */         if (local_Request != null) {
/*   79:  69 */           local_Request.close();
/*   80:     */         }
/*   81:     */       }
/*   82:     */     }
/*   83:     */   }
/*   84:     */   
/*   85:     */   public void shutdown(String paramString)
/*   86:     */     throws java.rmi.RemoteException, AEException
/*   87:     */   {
/*   88:  79 */     for (int i = 1;; i++)
/*   89:     */     {
/*   90:  81 */       _Request local_Request = null;
/*   91:     */       try
/*   92:     */       {
/*   93:  84 */         local_Request = __request("shutdown__string", "shutdown__CORBA_WStringValue");
/*   94:  85 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*   95:  86 */         local_Request.write_string(paramString);
/*   96:  87 */         local_Request.invoke();
/*   97:  88 */         return;
/*   98:     */       }
/*   99:     */       catch (TRANSIENT localTRANSIENT)
/*  100:     */       {
/*  101:  92 */         if (i == 10) {
/*  102:  94 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  103:     */         }
/*  104:     */       }
/*  105:     */       catch (UserException localUserException)
/*  106:     */       {
/*  107:  99 */         if (local_Request.isRMI())
/*  108:     */         {
/*  109: 101 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  110: 103 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  111:     */           }
/*  112:     */         }
/*  113: 108 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  114: 110 */           throw AEExceptionHelper.read(localUserException.input);
/*  115:     */         }
/*  116: 113 */         throw new java.rmi.RemoteException(localUserException.type);
/*  117:     */       }
/*  118:     */       catch (SystemException localSystemException)
/*  119:     */       {
/*  120: 117 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  121:     */       }
/*  122:     */       finally
/*  123:     */       {
/*  124: 121 */         if (local_Request != null) {
/*  125: 123 */           local_Request.close();
/*  126:     */         }
/*  127:     */       }
/*  128:     */     }
/*  129:     */   }
/*  130:     */   
/*  131:     */   public void init(AEDBParams paramAEDBParams)
/*  132:     */     throws java.rmi.RemoteException, AEException
/*  133:     */   {
/*  134: 133 */     for (int i = 1;; i++)
/*  135:     */     {
/*  136: 135 */       _Request local_Request = null;
/*  137:     */       try
/*  138:     */       {
/*  139: 138 */         local_Request = __request("init__AEDBParams", "init__org_omg_boxedIDL_com_ffusion_alert_interfaces_AEDBParams");
/*  140: 139 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  141: 140 */         local_Request.write_value(paramAEDBParams, AEDBParams.class);
/*  142: 141 */         local_Request.invoke();
/*  143: 142 */         return;
/*  144:     */       }
/*  145:     */       catch (TRANSIENT localTRANSIENT)
/*  146:     */       {
/*  147: 146 */         if (i == 10) {
/*  148: 148 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  149:     */         }
/*  150:     */       }
/*  151:     */       catch (UserException localUserException)
/*  152:     */       {
/*  153: 153 */         if (local_Request.isRMI())
/*  154:     */         {
/*  155: 155 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  156: 157 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  157:     */           }
/*  158:     */         }
/*  159: 162 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  160: 164 */           throw AEExceptionHelper.read(localUserException.input);
/*  161:     */         }
/*  162: 167 */         throw new java.rmi.RemoteException(localUserException.type);
/*  163:     */       }
/*  164:     */       catch (SystemException localSystemException)
/*  165:     */       {
/*  166: 171 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  167:     */       }
/*  168:     */       finally
/*  169:     */       {
/*  170: 175 */         if (local_Request != null) {
/*  171: 177 */           local_Request.close();
/*  172:     */         }
/*  173:     */       }
/*  174:     */     }
/*  175:     */   }
/*  176:     */   
/*  177:     */   public void init(String paramString)
/*  178:     */     throws java.rmi.RemoteException, AEException
/*  179:     */   {
/*  180: 187 */     for (int i = 1;; i++)
/*  181:     */     {
/*  182: 189 */       _Request local_Request = null;
/*  183:     */       try
/*  184:     */       {
/*  185: 192 */         local_Request = __request("init__string", "init__CORBA_WStringValue");
/*  186: 193 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  187: 194 */         local_Request.write_string(paramString);
/*  188: 195 */         local_Request.invoke();
/*  189: 196 */         return;
/*  190:     */       }
/*  191:     */       catch (TRANSIENT localTRANSIENT)
/*  192:     */       {
/*  193: 200 */         if (i == 10) {
/*  194: 202 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  195:     */         }
/*  196:     */       }
/*  197:     */       catch (UserException localUserException)
/*  198:     */       {
/*  199: 207 */         if (local_Request.isRMI())
/*  200:     */         {
/*  201: 209 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  202: 211 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  203:     */           }
/*  204:     */         }
/*  205: 216 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  206: 218 */           throw AEExceptionHelper.read(localUserException.input);
/*  207:     */         }
/*  208: 221 */         throw new java.rmi.RemoteException(localUserException.type);
/*  209:     */       }
/*  210:     */       catch (SystemException localSystemException)
/*  211:     */       {
/*  212: 225 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  213:     */       }
/*  214:     */       finally
/*  215:     */       {
/*  216: 229 */         if (local_Request != null) {
/*  217: 231 */           local_Request.close();
/*  218:     */         }
/*  219:     */       }
/*  220:     */     }
/*  221:     */   }
/*  222:     */   
/*  223:     */   public void start()
/*  224:     */     throws java.rmi.RemoteException, AEException
/*  225:     */   {
/*  226: 240 */     for (int i = 1;; i++)
/*  227:     */     {
/*  228: 242 */       _Request local_Request = null;
/*  229:     */       try
/*  230:     */       {
/*  231: 245 */         local_Request = __request("start__", "start__");
/*  232: 246 */         local_Request.invoke();
/*  233: 247 */         return;
/*  234:     */       }
/*  235:     */       catch (TRANSIENT localTRANSIENT)
/*  236:     */       {
/*  237: 251 */         if (i == 10) {
/*  238: 253 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  239:     */         }
/*  240:     */       }
/*  241:     */       catch (UserException localUserException)
/*  242:     */       {
/*  243: 258 */         if (local_Request.isRMI())
/*  244:     */         {
/*  245: 260 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  246: 262 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  247:     */           }
/*  248:     */         }
/*  249: 267 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  250: 269 */           throw AEExceptionHelper.read(localUserException.input);
/*  251:     */         }
/*  252: 272 */         throw new java.rmi.RemoteException(localUserException.type);
/*  253:     */       }
/*  254:     */       catch (SystemException localSystemException)
/*  255:     */       {
/*  256: 276 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  257:     */       }
/*  258:     */       finally
/*  259:     */       {
/*  260: 280 */         if (local_Request != null) {
/*  261: 282 */           local_Request.close();
/*  262:     */         }
/*  263:     */       }
/*  264:     */     }
/*  265:     */   }
/*  266:     */   
/*  267:     */   public void start(String paramString)
/*  268:     */     throws java.rmi.RemoteException, AEException
/*  269:     */   {
/*  270: 292 */     for (int i = 1;; i++)
/*  271:     */     {
/*  272: 294 */       _Request local_Request = null;
/*  273:     */       try
/*  274:     */       {
/*  275: 297 */         local_Request = __request("start__string", "start__CORBA_WStringValue");
/*  276: 298 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  277: 299 */         local_Request.write_string(paramString);
/*  278: 300 */         local_Request.invoke();
/*  279: 301 */         return;
/*  280:     */       }
/*  281:     */       catch (TRANSIENT localTRANSIENT)
/*  282:     */       {
/*  283: 305 */         if (i == 10) {
/*  284: 307 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  285:     */         }
/*  286:     */       }
/*  287:     */       catch (UserException localUserException)
/*  288:     */       {
/*  289: 312 */         if (local_Request.isRMI())
/*  290:     */         {
/*  291: 314 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  292: 316 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  293:     */           }
/*  294:     */         }
/*  295: 321 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  296: 323 */           throw AEExceptionHelper.read(localUserException.input);
/*  297:     */         }
/*  298: 326 */         throw new java.rmi.RemoteException(localUserException.type);
/*  299:     */       }
/*  300:     */       catch (SystemException localSystemException)
/*  301:     */       {
/*  302: 330 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  303:     */       }
/*  304:     */       finally
/*  305:     */       {
/*  306: 334 */         if (local_Request != null) {
/*  307: 336 */           local_Request.close();
/*  308:     */         }
/*  309:     */       }
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void resume()
/*  314:     */     throws java.rmi.RemoteException, AEException
/*  315:     */   {
/*  316: 345 */     for (int i = 1;; i++)
/*  317:     */     {
/*  318: 347 */       _Request local_Request = null;
/*  319:     */       try
/*  320:     */       {
/*  321: 350 */         local_Request = __request("resume__", "resume__");
/*  322: 351 */         local_Request.invoke();
/*  323: 352 */         return;
/*  324:     */       }
/*  325:     */       catch (TRANSIENT localTRANSIENT)
/*  326:     */       {
/*  327: 356 */         if (i == 10) {
/*  328: 358 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  329:     */         }
/*  330:     */       }
/*  331:     */       catch (UserException localUserException)
/*  332:     */       {
/*  333: 363 */         if (local_Request.isRMI())
/*  334:     */         {
/*  335: 365 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  336: 367 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  337:     */           }
/*  338:     */         }
/*  339: 372 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  340: 374 */           throw AEExceptionHelper.read(localUserException.input);
/*  341:     */         }
/*  342: 377 */         throw new java.rmi.RemoteException(localUserException.type);
/*  343:     */       }
/*  344:     */       catch (SystemException localSystemException)
/*  345:     */       {
/*  346: 381 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  347:     */       }
/*  348:     */       finally
/*  349:     */       {
/*  350: 385 */         if (local_Request != null) {
/*  351: 387 */           local_Request.close();
/*  352:     */         }
/*  353:     */       }
/*  354:     */     }
/*  355:     */   }
/*  356:     */   
/*  357:     */   public void resume(String paramString)
/*  358:     */     throws java.rmi.RemoteException, AEException
/*  359:     */   {
/*  360: 397 */     for (int i = 1;; i++)
/*  361:     */     {
/*  362: 399 */       _Request local_Request = null;
/*  363:     */       try
/*  364:     */       {
/*  365: 402 */         local_Request = __request("resume__string", "resume__CORBA_WStringValue");
/*  366: 403 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  367: 404 */         local_Request.write_string(paramString);
/*  368: 405 */         local_Request.invoke();
/*  369: 406 */         return;
/*  370:     */       }
/*  371:     */       catch (TRANSIENT localTRANSIENT)
/*  372:     */       {
/*  373: 410 */         if (i == 10) {
/*  374: 412 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  375:     */         }
/*  376:     */       }
/*  377:     */       catch (UserException localUserException)
/*  378:     */       {
/*  379: 417 */         if (local_Request.isRMI())
/*  380:     */         {
/*  381: 419 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  382: 421 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  383:     */           }
/*  384:     */         }
/*  385: 426 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  386: 428 */           throw AEExceptionHelper.read(localUserException.input);
/*  387:     */         }
/*  388: 431 */         throw new java.rmi.RemoteException(localUserException.type);
/*  389:     */       }
/*  390:     */       catch (SystemException localSystemException)
/*  391:     */       {
/*  392: 435 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  393:     */       }
/*  394:     */       finally
/*  395:     */       {
/*  396: 439 */         if (local_Request != null) {
/*  397: 441 */           local_Request.close();
/*  398:     */         }
/*  399:     */       }
/*  400:     */     }
/*  401:     */   }
/*  402:     */   
/*  403:     */   public void stop(String paramString)
/*  404:     */     throws java.rmi.RemoteException, AEException
/*  405:     */   {
/*  406: 451 */     for (int i = 1;; i++)
/*  407:     */     {
/*  408: 453 */       _Request local_Request = null;
/*  409:     */       try
/*  410:     */       {
/*  411: 456 */         local_Request = __request("stop__string", "stop__CORBA_WStringValue");
/*  412: 457 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  413: 458 */         local_Request.write_string(paramString);
/*  414: 459 */         local_Request.invoke();
/*  415: 460 */         return;
/*  416:     */       }
/*  417:     */       catch (TRANSIENT localTRANSIENT)
/*  418:     */       {
/*  419: 464 */         if (i == 10) {
/*  420: 466 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  421:     */         }
/*  422:     */       }
/*  423:     */       catch (UserException localUserException)
/*  424:     */       {
/*  425: 471 */         if (local_Request.isRMI())
/*  426:     */         {
/*  427: 473 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  428: 475 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  429:     */           }
/*  430:     */         }
/*  431: 480 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  432: 482 */           throw AEExceptionHelper.read(localUserException.input);
/*  433:     */         }
/*  434: 485 */         throw new java.rmi.RemoteException(localUserException.type);
/*  435:     */       }
/*  436:     */       catch (SystemException localSystemException)
/*  437:     */       {
/*  438: 489 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  439:     */       }
/*  440:     */       finally
/*  441:     */       {
/*  442: 493 */         if (local_Request != null) {
/*  443: 495 */           local_Request.close();
/*  444:     */         }
/*  445:     */       }
/*  446:     */     }
/*  447:     */   }
/*  448:     */   
/*  449:     */   public void stop()
/*  450:     */     throws java.rmi.RemoteException, AEException
/*  451:     */   {
/*  452: 504 */     for (int i = 1;; i++)
/*  453:     */     {
/*  454: 506 */       _Request local_Request = null;
/*  455:     */       try
/*  456:     */       {
/*  457: 509 */         local_Request = __request("stop__", "stop__");
/*  458: 510 */         local_Request.invoke();
/*  459: 511 */         return;
/*  460:     */       }
/*  461:     */       catch (TRANSIENT localTRANSIENT)
/*  462:     */       {
/*  463: 515 */         if (i == 10) {
/*  464: 517 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  465:     */         }
/*  466:     */       }
/*  467:     */       catch (UserException localUserException)
/*  468:     */       {
/*  469: 522 */         if (local_Request.isRMI())
/*  470:     */         {
/*  471: 524 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  472: 526 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  473:     */           }
/*  474:     */         }
/*  475: 531 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  476: 533 */           throw AEExceptionHelper.read(localUserException.input);
/*  477:     */         }
/*  478: 536 */         throw new java.rmi.RemoteException(localUserException.type);
/*  479:     */       }
/*  480:     */       catch (SystemException localSystemException)
/*  481:     */       {
/*  482: 540 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  483:     */       }
/*  484:     */       finally
/*  485:     */       {
/*  486: 544 */         if (local_Request != null) {
/*  487: 546 */           local_Request.close();
/*  488:     */         }
/*  489:     */       }
/*  490:     */     }
/*  491:     */   }
/*  492:     */   
/*  493:     */   public void suspend()
/*  494:     */     throws java.rmi.RemoteException, AEException
/*  495:     */   {
/*  496: 555 */     for (int i = 1;; i++)
/*  497:     */     {
/*  498: 557 */       _Request local_Request = null;
/*  499:     */       try
/*  500:     */       {
/*  501: 560 */         local_Request = __request("suspend__", "suspend__");
/*  502: 561 */         local_Request.invoke();
/*  503: 562 */         return;
/*  504:     */       }
/*  505:     */       catch (TRANSIENT localTRANSIENT)
/*  506:     */       {
/*  507: 566 */         if (i == 10) {
/*  508: 568 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  509:     */         }
/*  510:     */       }
/*  511:     */       catch (UserException localUserException)
/*  512:     */       {
/*  513: 573 */         if (local_Request.isRMI())
/*  514:     */         {
/*  515: 575 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  516: 577 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  517:     */           }
/*  518:     */         }
/*  519: 582 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  520: 584 */           throw AEExceptionHelper.read(localUserException.input);
/*  521:     */         }
/*  522: 587 */         throw new java.rmi.RemoteException(localUserException.type);
/*  523:     */       }
/*  524:     */       catch (SystemException localSystemException)
/*  525:     */       {
/*  526: 591 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  527:     */       }
/*  528:     */       finally
/*  529:     */       {
/*  530: 595 */         if (local_Request != null) {
/*  531: 597 */           local_Request.close();
/*  532:     */         }
/*  533:     */       }
/*  534:     */     }
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void suspend(String paramString)
/*  538:     */     throws java.rmi.RemoteException, AEException
/*  539:     */   {
/*  540: 607 */     for (int i = 1;; i++)
/*  541:     */     {
/*  542: 609 */       _Request local_Request = null;
/*  543:     */       try
/*  544:     */       {
/*  545: 612 */         local_Request = __request("suspend__string", "suspend__CORBA_WStringValue");
/*  546: 613 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  547: 614 */         local_Request.write_string(paramString);
/*  548: 615 */         local_Request.invoke();
/*  549: 616 */         return;
/*  550:     */       }
/*  551:     */       catch (TRANSIENT localTRANSIENT)
/*  552:     */       {
/*  553: 620 */         if (i == 10) {
/*  554: 622 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  555:     */         }
/*  556:     */       }
/*  557:     */       catch (UserException localUserException)
/*  558:     */       {
/*  559: 627 */         if (local_Request.isRMI())
/*  560:     */         {
/*  561: 629 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  562: 631 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  563:     */           }
/*  564:     */         }
/*  565: 636 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  566: 638 */           throw AEExceptionHelper.read(localUserException.input);
/*  567:     */         }
/*  568: 641 */         throw new java.rmi.RemoteException(localUserException.type);
/*  569:     */       }
/*  570:     */       catch (SystemException localSystemException)
/*  571:     */       {
/*  572: 645 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  573:     */       }
/*  574:     */       finally
/*  575:     */       {
/*  576: 649 */         if (local_Request != null) {
/*  577: 651 */           local_Request.close();
/*  578:     */         }
/*  579:     */       }
/*  580:     */     }
/*  581:     */   }
/*  582:     */   
/*  583:     */   public void addApplication(AEApplicationInfo paramAEApplicationInfo)
/*  584:     */     throws java.rmi.RemoteException, AEException
/*  585:     */   {
/*  586: 661 */     for (int i = 1;; i++)
/*  587:     */     {
/*  588: 663 */       _Request local_Request = null;
/*  589:     */       try
/*  590:     */       {
/*  591: 666 */         local_Request = __request("addApplication");
/*  592: 667 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  593: 668 */         local_Request.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/*  594: 669 */         local_Request.invoke();
/*  595: 670 */         return;
/*  596:     */       }
/*  597:     */       catch (TRANSIENT localTRANSIENT)
/*  598:     */       {
/*  599: 674 */         if (i == 10) {
/*  600: 676 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  601:     */         }
/*  602:     */       }
/*  603:     */       catch (UserException localUserException)
/*  604:     */       {
/*  605: 681 */         if (local_Request.isRMI())
/*  606:     */         {
/*  607: 683 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  608: 685 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  609:     */           }
/*  610:     */         }
/*  611: 690 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  612: 692 */           throw AEExceptionHelper.read(localUserException.input);
/*  613:     */         }
/*  614: 695 */         throw new java.rmi.RemoteException(localUserException.type);
/*  615:     */       }
/*  616:     */       catch (SystemException localSystemException)
/*  617:     */       {
/*  618: 699 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  619:     */       }
/*  620:     */       finally
/*  621:     */       {
/*  622: 703 */         if (local_Request != null) {
/*  623: 705 */           local_Request.close();
/*  624:     */         }
/*  625:     */       }
/*  626:     */     }
/*  627:     */   }
/*  628:     */   
/*  629:     */   public AEServerInfo[] getServers()
/*  630:     */     throws java.rmi.RemoteException, AEException
/*  631:     */   {
/*  632: 714 */     for (int i = 1;; i++)
/*  633:     */     {
/*  634: 716 */       _Request local_Request = null;
/*  635:     */       try
/*  636:     */       {
/*  637: 719 */         local_Request = __request("getServers");
/*  638: 720 */         local_Request.invoke();
/*  639: 721 */         InputStream localInputStream = local_Request.getInputStream();
/*  640:     */         AEServerInfo[] arrayOfAEServerInfo2;
/*  641: 723 */         if (local_Request.isRMI()) {
/*  642: 723 */           arrayOfAEServerInfo2 = (AEServerInfo[])local_Request.read_value(new AEServerInfo[0].getClass());
/*  643:     */         } else {
/*  644: 723 */           arrayOfAEServerInfo2 = AEServerInfoSeqHelper.read(localInputStream);
/*  645:     */         }
/*  646: 724 */         return arrayOfAEServerInfo2;
/*  647:     */       }
/*  648:     */       catch (TRANSIENT localTRANSIENT)
/*  649:     */       {
/*  650: 728 */         if (i == 10) {
/*  651: 730 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  652:     */         }
/*  653:     */       }
/*  654:     */       catch (UserException localUserException)
/*  655:     */       {
/*  656: 735 */         if (local_Request.isRMI())
/*  657:     */         {
/*  658: 737 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  659: 739 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  660:     */           }
/*  661:     */         }
/*  662: 744 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  663: 746 */           throw AEExceptionHelper.read(localUserException.input);
/*  664:     */         }
/*  665: 749 */         throw new java.rmi.RemoteException(localUserException.type);
/*  666:     */       }
/*  667:     */       catch (SystemException localSystemException)
/*  668:     */       {
/*  669: 753 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  670:     */       }
/*  671:     */       finally
/*  672:     */       {
/*  673: 757 */         if (local_Request != null) {
/*  674: 759 */           local_Request.close();
/*  675:     */         }
/*  676:     */       }
/*  677:     */     }
/*  678:     */   }
/*  679:     */   
/*  680:     */   public void removeApplication(String paramString)
/*  681:     */     throws java.rmi.RemoteException, AEException
/*  682:     */   {
/*  683: 769 */     for (int i = 1;; i++)
/*  684:     */     {
/*  685: 771 */       _Request local_Request = null;
/*  686:     */       try
/*  687:     */       {
/*  688: 774 */         local_Request = __request("removeApplication");
/*  689: 775 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  690: 776 */         local_Request.write_string(paramString);
/*  691: 777 */         local_Request.invoke();
/*  692: 778 */         return;
/*  693:     */       }
/*  694:     */       catch (TRANSIENT localTRANSIENT)
/*  695:     */       {
/*  696: 782 */         if (i == 10) {
/*  697: 784 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  698:     */         }
/*  699:     */       }
/*  700:     */       catch (UserException localUserException)
/*  701:     */       {
/*  702: 789 */         if (local_Request.isRMI())
/*  703:     */         {
/*  704: 791 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  705: 793 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  706:     */           }
/*  707:     */         }
/*  708: 798 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  709: 800 */           throw AEExceptionHelper.read(localUserException.input);
/*  710:     */         }
/*  711: 803 */         throw new java.rmi.RemoteException(localUserException.type);
/*  712:     */       }
/*  713:     */       catch (SystemException localSystemException)
/*  714:     */       {
/*  715: 807 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  716:     */       }
/*  717:     */       finally
/*  718:     */       {
/*  719: 811 */         if (local_Request != null) {
/*  720: 813 */           local_Request.close();
/*  721:     */         }
/*  722:     */       }
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   public AEServerInfo addServer(AEServerInfo paramAEServerInfo)
/*  727:     */     throws java.rmi.RemoteException, AEException
/*  728:     */   {
/*  729: 823 */     for (int i = 1;; i++)
/*  730:     */     {
/*  731: 825 */       _Request local_Request = null;
/*  732:     */       try
/*  733:     */       {
/*  734: 828 */         local_Request = __request("addServer");
/*  735: 829 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  736: 830 */         local_Request.write_value(paramAEServerInfo, AEServerInfo.class);
/*  737: 831 */         local_Request.invoke();
/*  738: 832 */         InputStream localInputStream = local_Request.getInputStream();
/*  739:     */         
/*  740: 834 */         AEServerInfo localAEServerInfo2 = (AEServerInfo)local_Request.read_value(AEServerInfo.class);
/*  741: 835 */         return localAEServerInfo2;
/*  742:     */       }
/*  743:     */       catch (TRANSIENT localTRANSIENT)
/*  744:     */       {
/*  745: 839 */         if (i == 10) {
/*  746: 841 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  747:     */         }
/*  748:     */       }
/*  749:     */       catch (UserException localUserException)
/*  750:     */       {
/*  751: 846 */         if (local_Request.isRMI())
/*  752:     */         {
/*  753: 848 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  754: 850 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  755:     */           }
/*  756:     */         }
/*  757: 855 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  758: 857 */           throw AEExceptionHelper.read(localUserException.input);
/*  759:     */         }
/*  760: 860 */         throw new java.rmi.RemoteException(localUserException.type);
/*  761:     */       }
/*  762:     */       catch (SystemException localSystemException)
/*  763:     */       {
/*  764: 864 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  765:     */       }
/*  766:     */       finally
/*  767:     */       {
/*  768: 868 */         if (local_Request != null) {
/*  769: 870 */           local_Request.close();
/*  770:     */         }
/*  771:     */       }
/*  772:     */     }
/*  773:     */   }
/*  774:     */   
/*  775:     */   public AEApplicationInfo[] getApplications()
/*  776:     */     throws java.rmi.RemoteException, AEException
/*  777:     */   {
/*  778: 879 */     for (int i = 1;; i++)
/*  779:     */     {
/*  780: 881 */       _Request local_Request = null;
/*  781:     */       try
/*  782:     */       {
/*  783: 884 */         local_Request = __request("getApplications");
/*  784: 885 */         local_Request.invoke();
/*  785: 886 */         InputStream localInputStream = local_Request.getInputStream();
/*  786:     */         AEApplicationInfo[] arrayOfAEApplicationInfo2;
/*  787: 888 */         if (local_Request.isRMI()) {
/*  788: 888 */           arrayOfAEApplicationInfo2 = (AEApplicationInfo[])local_Request.read_value(new AEApplicationInfo[0].getClass());
/*  789:     */         } else {
/*  790: 888 */           arrayOfAEApplicationInfo2 = AEApplicationInfoSeqHelper.read(localInputStream);
/*  791:     */         }
/*  792: 889 */         return arrayOfAEApplicationInfo2;
/*  793:     */       }
/*  794:     */       catch (TRANSIENT localTRANSIENT)
/*  795:     */       {
/*  796: 893 */         if (i == 10) {
/*  797: 895 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  798:     */         }
/*  799:     */       }
/*  800:     */       catch (UserException localUserException)
/*  801:     */       {
/*  802: 900 */         if (local_Request.isRMI())
/*  803:     */         {
/*  804: 902 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  805: 904 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  806:     */           }
/*  807:     */         }
/*  808: 909 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  809: 911 */           throw AEExceptionHelper.read(localUserException.input);
/*  810:     */         }
/*  811: 914 */         throw new java.rmi.RemoteException(localUserException.type);
/*  812:     */       }
/*  813:     */       catch (SystemException localSystemException)
/*  814:     */       {
/*  815: 918 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  816:     */       }
/*  817:     */       finally
/*  818:     */       {
/*  819: 922 */         if (local_Request != null) {
/*  820: 924 */           local_Request.close();
/*  821:     */         }
/*  822:     */       }
/*  823:     */     }
/*  824:     */   }
/*  825:     */   
/*  826:     */   public void removeServer(String paramString)
/*  827:     */     throws java.rmi.RemoteException, AEException
/*  828:     */   {
/*  829: 934 */     for (int i = 1;; i++)
/*  830:     */     {
/*  831: 936 */       _Request local_Request = null;
/*  832:     */       try
/*  833:     */       {
/*  834: 939 */         local_Request = __request("removeServer");
/*  835: 940 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  836: 941 */         local_Request.write_string(paramString);
/*  837: 942 */         local_Request.invoke();
/*  838: 943 */         return;
/*  839:     */       }
/*  840:     */       catch (TRANSIENT localTRANSIENT)
/*  841:     */       {
/*  842: 947 */         if (i == 10) {
/*  843: 949 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  844:     */         }
/*  845:     */       }
/*  846:     */       catch (UserException localUserException)
/*  847:     */       {
/*  848: 954 */         if (local_Request.isRMI())
/*  849:     */         {
/*  850: 956 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  851: 958 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  852:     */           }
/*  853:     */         }
/*  854: 963 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  855: 965 */           throw AEExceptionHelper.read(localUserException.input);
/*  856:     */         }
/*  857: 968 */         throw new java.rmi.RemoteException(localUserException.type);
/*  858:     */       }
/*  859:     */       catch (SystemException localSystemException)
/*  860:     */       {
/*  861: 972 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  862:     */       }
/*  863:     */       finally
/*  864:     */       {
/*  865: 976 */         if (local_Request != null) {
/*  866: 978 */           local_Request.close();
/*  867:     */         }
/*  868:     */       }
/*  869:     */     }
/*  870:     */   }
/*  871:     */   
/*  872:     */   public boolean isInitialized()
/*  873:     */     throws java.rmi.RemoteException, AEException
/*  874:     */   {
/*  875: 987 */     for (int i = 1;; i++)
/*  876:     */     {
/*  877: 989 */       _Request local_Request = null;
/*  878:     */       try
/*  879:     */       {
/*  880: 992 */         local_Request = __request("isInitialized__", "isInitialized__");
/*  881: 993 */         local_Request.invoke();
/*  882: 994 */         InputStream localInputStream = local_Request.getInputStream();
/*  883:     */         
/*  884: 996 */         boolean bool2 = localInputStream.read_boolean();
/*  885: 997 */         return bool2;
/*  886:     */       }
/*  887:     */       catch (TRANSIENT localTRANSIENT)
/*  888:     */       {
/*  889:1001 */         if (i == 10) {
/*  890:1003 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  891:     */         }
/*  892:     */       }
/*  893:     */       catch (UserException localUserException)
/*  894:     */       {
/*  895:1008 */         if (local_Request.isRMI())
/*  896:     */         {
/*  897:1010 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  898:1012 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  899:     */           }
/*  900:     */         }
/*  901:1017 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  902:1019 */           throw AEExceptionHelper.read(localUserException.input);
/*  903:     */         }
/*  904:1022 */         throw new java.rmi.RemoteException(localUserException.type);
/*  905:     */       }
/*  906:     */       catch (SystemException localSystemException)
/*  907:     */       {
/*  908:1026 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  909:     */       }
/*  910:     */       finally
/*  911:     */       {
/*  912:1030 */         if (local_Request != null) {
/*  913:1032 */           local_Request.close();
/*  914:     */         }
/*  915:     */       }
/*  916:     */     }
/*  917:     */   }
/*  918:     */   
/*  919:     */   public boolean isInitialized(String paramString)
/*  920:     */     throws java.rmi.RemoteException, AEException
/*  921:     */   {
/*  922:1042 */     for (int i = 1;; i++)
/*  923:     */     {
/*  924:1044 */       _Request local_Request = null;
/*  925:     */       try
/*  926:     */       {
/*  927:1047 */         local_Request = __request("isInitialized__string", "isInitialized__CORBA_WStringValue");
/*  928:1048 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  929:1049 */         local_Request.write_string(paramString);
/*  930:1050 */         local_Request.invoke();
/*  931:1051 */         InputStream localInputStream = local_Request.getInputStream();
/*  932:     */         
/*  933:1053 */         boolean bool2 = localInputStream.read_boolean();
/*  934:1054 */         return bool2;
/*  935:     */       }
/*  936:     */       catch (TRANSIENT localTRANSIENT)
/*  937:     */       {
/*  938:1058 */         if (i == 10) {
/*  939:1060 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  940:     */         }
/*  941:     */       }
/*  942:     */       catch (UserException localUserException)
/*  943:     */       {
/*  944:1065 */         if (local_Request.isRMI())
/*  945:     */         {
/*  946:1067 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  947:1069 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  948:     */           }
/*  949:     */         }
/*  950:1074 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  951:1076 */           throw AEExceptionHelper.read(localUserException.input);
/*  952:     */         }
/*  953:1079 */         throw new java.rmi.RemoteException(localUserException.type);
/*  954:     */       }
/*  955:     */       catch (SystemException localSystemException)
/*  956:     */       {
/*  957:1083 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  958:     */       }
/*  959:     */       finally
/*  960:     */       {
/*  961:1087 */         if (local_Request != null) {
/*  962:1089 */           local_Request.close();
/*  963:     */         }
/*  964:     */       }
/*  965:     */     }
/*  966:     */   }
/*  967:     */   
/*  968:     */   public void destroyRepository(AEDBParams paramAEDBParams)
/*  969:     */     throws java.rmi.RemoteException, AEException
/*  970:     */   {
/*  971:1099 */     for (int i = 1;; i++)
/*  972:     */     {
/*  973:1101 */       _Request local_Request = null;
/*  974:     */       try
/*  975:     */       {
/*  976:1104 */         local_Request = __request("destroyRepository");
/*  977:1105 */         OutputStream localOutputStream = local_Request.getOutputStream();
/*  978:1106 */         local_Request.write_value(paramAEDBParams, AEDBParams.class);
/*  979:1107 */         local_Request.invoke();
/*  980:1108 */         return;
/*  981:     */       }
/*  982:     */       catch (TRANSIENT localTRANSIENT)
/*  983:     */       {
/*  984:1112 */         if (i == 10) {
/*  985:1114 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  986:     */         }
/*  987:     */       }
/*  988:     */       catch (UserException localUserException)
/*  989:     */       {
/*  990:1119 */         if (local_Request.isRMI())
/*  991:     */         {
/*  992:1121 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/*  993:1123 */             throw ((AEException)local_Request.read_value(AEException.class));
/*  994:     */           }
/*  995:     */         }
/*  996:1128 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/*  997:1130 */           throw AEExceptionHelper.read(localUserException.input);
/*  998:     */         }
/*  999:1133 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1000:     */       }
/* 1001:     */       catch (SystemException localSystemException)
/* 1002:     */       {
/* 1003:1137 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1004:     */       }
/* 1005:     */       finally
/* 1006:     */       {
/* 1007:1141 */         if (local_Request != null) {
/* 1008:1143 */           local_Request.close();
/* 1009:     */         }
/* 1010:     */       }
/* 1011:     */     }
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void checkRepository(AEDBParams paramAEDBParams)
/* 1015:     */     throws java.rmi.RemoteException, AEException
/* 1016:     */   {
/* 1017:1153 */     for (int i = 1;; i++)
/* 1018:     */     {
/* 1019:1155 */       _Request local_Request = null;
/* 1020:     */       try
/* 1021:     */       {
/* 1022:1158 */         local_Request = __request("checkRepository");
/* 1023:1159 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1024:1160 */         local_Request.write_value(paramAEDBParams, AEDBParams.class);
/* 1025:1161 */         local_Request.invoke();
/* 1026:1162 */         return;
/* 1027:     */       }
/* 1028:     */       catch (TRANSIENT localTRANSIENT)
/* 1029:     */       {
/* 1030:1166 */         if (i == 10) {
/* 1031:1168 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1032:     */         }
/* 1033:     */       }
/* 1034:     */       catch (UserException localUserException)
/* 1035:     */       {
/* 1036:1173 */         if (local_Request.isRMI())
/* 1037:     */         {
/* 1038:1175 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1039:1177 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1040:     */           }
/* 1041:     */         }
/* 1042:1182 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1043:1184 */           throw AEExceptionHelper.read(localUserException.input);
/* 1044:     */         }
/* 1045:1187 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1046:     */       }
/* 1047:     */       catch (SystemException localSystemException)
/* 1048:     */       {
/* 1049:1191 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1050:     */       }
/* 1051:     */       finally
/* 1052:     */       {
/* 1053:1195 */         if (local_Request != null) {
/* 1054:1197 */           local_Request.close();
/* 1055:     */         }
/* 1056:     */       }
/* 1057:     */     }
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public void createRepository(AEDBParams paramAEDBParams, boolean paramBoolean)
/* 1061:     */     throws java.rmi.RemoteException, AEException
/* 1062:     */   {
/* 1063:1208 */     for (int i = 1;; i++)
/* 1064:     */     {
/* 1065:1210 */       _Request local_Request = null;
/* 1066:     */       try
/* 1067:     */       {
/* 1068:1213 */         local_Request = __request("createRepository");
/* 1069:1214 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1070:1215 */         local_Request.write_value(paramAEDBParams, AEDBParams.class);
/* 1071:1216 */         localOutputStream.write_boolean(paramBoolean);
/* 1072:1217 */         local_Request.invoke();
/* 1073:1218 */         return;
/* 1074:     */       }
/* 1075:     */       catch (TRANSIENT localTRANSIENT)
/* 1076:     */       {
/* 1077:1222 */         if (i == 10) {
/* 1078:1224 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1079:     */         }
/* 1080:     */       }
/* 1081:     */       catch (UserException localUserException)
/* 1082:     */       {
/* 1083:1229 */         if (local_Request.isRMI())
/* 1084:     */         {
/* 1085:1231 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1086:1233 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1087:     */           }
/* 1088:     */         }
/* 1089:1238 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1090:1240 */           throw AEExceptionHelper.read(localUserException.input);
/* 1091:     */         }
/* 1092:1243 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1093:     */       }
/* 1094:     */       catch (SystemException localSystemException)
/* 1095:     */       {
/* 1096:1247 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1097:     */       }
/* 1098:     */       finally
/* 1099:     */       {
/* 1100:1251 */         if (local_Request != null) {
/* 1101:1253 */           local_Request.close();
/* 1102:     */         }
/* 1103:     */       }
/* 1104:     */     }
/* 1105:     */   }
/* 1106:     */   
/* 1107:     */   public void initServerAll(AEDBParams paramAEDBParams)
/* 1108:     */     throws java.rmi.RemoteException, AEException
/* 1109:     */   {
/* 1110:1263 */     for (int i = 1;; i++)
/* 1111:     */     {
/* 1112:1265 */       _Request local_Request = null;
/* 1113:     */       try
/* 1114:     */       {
/* 1115:1268 */         local_Request = __request("initServerAll");
/* 1116:1269 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1117:1270 */         local_Request.write_value(paramAEDBParams, AEDBParams.class);
/* 1118:1271 */         local_Request.invoke();
/* 1119:1272 */         return;
/* 1120:     */       }
/* 1121:     */       catch (TRANSIENT localTRANSIENT)
/* 1122:     */       {
/* 1123:1276 */         if (i == 10) {
/* 1124:1278 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1125:     */         }
/* 1126:     */       }
/* 1127:     */       catch (UserException localUserException)
/* 1128:     */       {
/* 1129:1283 */         if (local_Request.isRMI())
/* 1130:     */         {
/* 1131:1285 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1132:1287 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1133:     */           }
/* 1134:     */         }
/* 1135:1292 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1136:1294 */           throw AEExceptionHelper.read(localUserException.input);
/* 1137:     */         }
/* 1138:1297 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1139:     */       }
/* 1140:     */       catch (SystemException localSystemException)
/* 1141:     */       {
/* 1142:1301 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1143:     */       }
/* 1144:     */       finally
/* 1145:     */       {
/* 1146:1305 */         if (local_Request != null) {
/* 1147:1307 */           local_Request.close();
/* 1148:     */         }
/* 1149:     */       }
/* 1150:     */     }
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   public void startServerNamed(String paramString)
/* 1154:     */     throws java.rmi.RemoteException, AEException
/* 1155:     */   {
/* 1156:1317 */     for (int i = 1;; i++)
/* 1157:     */     {
/* 1158:1319 */       _Request local_Request = null;
/* 1159:     */       try
/* 1160:     */       {
/* 1161:1322 */         local_Request = __request("startServerNamed");
/* 1162:1323 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1163:1324 */         local_Request.write_string(paramString);
/* 1164:1325 */         local_Request.invoke();
/* 1165:1326 */         return;
/* 1166:     */       }
/* 1167:     */       catch (TRANSIENT localTRANSIENT)
/* 1168:     */       {
/* 1169:1330 */         if (i == 10) {
/* 1170:1332 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1171:     */         }
/* 1172:     */       }
/* 1173:     */       catch (UserException localUserException)
/* 1174:     */       {
/* 1175:1337 */         if (local_Request.isRMI())
/* 1176:     */         {
/* 1177:1339 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1178:1341 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1179:     */           }
/* 1180:     */         }
/* 1181:1346 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1182:1348 */           throw AEExceptionHelper.read(localUserException.input);
/* 1183:     */         }
/* 1184:1351 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1185:     */       }
/* 1186:     */       catch (SystemException localSystemException)
/* 1187:     */       {
/* 1188:1355 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1189:     */       }
/* 1190:     */       finally
/* 1191:     */       {
/* 1192:1359 */         if (local_Request != null) {
/* 1193:1361 */           local_Request.close();
/* 1194:     */         }
/* 1195:     */       }
/* 1196:     */     }
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public void startServerAll()
/* 1200:     */     throws java.rmi.RemoteException, AEException
/* 1201:     */   {
/* 1202:1370 */     for (int i = 1;; i++)
/* 1203:     */     {
/* 1204:1372 */       _Request local_Request = null;
/* 1205:     */       try
/* 1206:     */       {
/* 1207:1375 */         local_Request = __request("startServerAll");
/* 1208:1376 */         local_Request.invoke();
/* 1209:1377 */         return;
/* 1210:     */       }
/* 1211:     */       catch (TRANSIENT localTRANSIENT)
/* 1212:     */       {
/* 1213:1381 */         if (i == 10) {
/* 1214:1383 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1215:     */         }
/* 1216:     */       }
/* 1217:     */       catch (UserException localUserException)
/* 1218:     */       {
/* 1219:1388 */         if (local_Request.isRMI())
/* 1220:     */         {
/* 1221:1390 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1222:1392 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1223:     */           }
/* 1224:     */         }
/* 1225:1397 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1226:1399 */           throw AEExceptionHelper.read(localUserException.input);
/* 1227:     */         }
/* 1228:1402 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1229:     */       }
/* 1230:     */       catch (SystemException localSystemException)
/* 1231:     */       {
/* 1232:1406 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1233:     */       }
/* 1234:     */       finally
/* 1235:     */       {
/* 1236:1410 */         if (local_Request != null) {
/* 1237:1412 */           local_Request.close();
/* 1238:     */         }
/* 1239:     */       }
/* 1240:     */     }
/* 1241:     */   }
/* 1242:     */   
/* 1243:     */   public void stopServerAll()
/* 1244:     */     throws java.rmi.RemoteException, AEException
/* 1245:     */   {
/* 1246:1421 */     for (int i = 1;; i++)
/* 1247:     */     {
/* 1248:1423 */       _Request local_Request = null;
/* 1249:     */       try
/* 1250:     */       {
/* 1251:1426 */         local_Request = __request("stopServerAll");
/* 1252:1427 */         local_Request.invoke();
/* 1253:1428 */         return;
/* 1254:     */       }
/* 1255:     */       catch (TRANSIENT localTRANSIENT)
/* 1256:     */       {
/* 1257:1432 */         if (i == 10) {
/* 1258:1434 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1259:     */         }
/* 1260:     */       }
/* 1261:     */       catch (UserException localUserException)
/* 1262:     */       {
/* 1263:1439 */         if (local_Request.isRMI())
/* 1264:     */         {
/* 1265:1441 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1266:1443 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1267:     */           }
/* 1268:     */         }
/* 1269:1448 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1270:1450 */           throw AEExceptionHelper.read(localUserException.input);
/* 1271:     */         }
/* 1272:1453 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1273:     */       }
/* 1274:     */       catch (SystemException localSystemException)
/* 1275:     */       {
/* 1276:1457 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1277:     */       }
/* 1278:     */       finally
/* 1279:     */       {
/* 1280:1461 */         if (local_Request != null) {
/* 1281:1463 */           local_Request.close();
/* 1282:     */         }
/* 1283:     */       }
/* 1284:     */     }
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   public void suspendServerAll()
/* 1288:     */     throws java.rmi.RemoteException, AEException
/* 1289:     */   {
/* 1290:1472 */     for (int i = 1;; i++)
/* 1291:     */     {
/* 1292:1474 */       _Request local_Request = null;
/* 1293:     */       try
/* 1294:     */       {
/* 1295:1477 */         local_Request = __request("suspendServerAll");
/* 1296:1478 */         local_Request.invoke();
/* 1297:1479 */         return;
/* 1298:     */       }
/* 1299:     */       catch (TRANSIENT localTRANSIENT)
/* 1300:     */       {
/* 1301:1483 */         if (i == 10) {
/* 1302:1485 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1303:     */         }
/* 1304:     */       }
/* 1305:     */       catch (UserException localUserException)
/* 1306:     */       {
/* 1307:1490 */         if (local_Request.isRMI())
/* 1308:     */         {
/* 1309:1492 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1310:1494 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1311:     */           }
/* 1312:     */         }
/* 1313:1499 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1314:1501 */           throw AEExceptionHelper.read(localUserException.input);
/* 1315:     */         }
/* 1316:1504 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1317:     */       }
/* 1318:     */       catch (SystemException localSystemException)
/* 1319:     */       {
/* 1320:1508 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1321:     */       }
/* 1322:     */       finally
/* 1323:     */       {
/* 1324:1512 */         if (local_Request != null) {
/* 1325:1514 */           local_Request.close();
/* 1326:     */         }
/* 1327:     */       }
/* 1328:     */     }
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   public void resumeServerAll()
/* 1332:     */     throws java.rmi.RemoteException, AEException
/* 1333:     */   {
/* 1334:1523 */     for (int i = 1;; i++)
/* 1335:     */     {
/* 1336:1525 */       _Request local_Request = null;
/* 1337:     */       try
/* 1338:     */       {
/* 1339:1528 */         local_Request = __request("resumeServerAll");
/* 1340:1529 */         local_Request.invoke();
/* 1341:1530 */         return;
/* 1342:     */       }
/* 1343:     */       catch (TRANSIENT localTRANSIENT)
/* 1344:     */       {
/* 1345:1534 */         if (i == 10) {
/* 1346:1536 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1347:     */         }
/* 1348:     */       }
/* 1349:     */       catch (UserException localUserException)
/* 1350:     */       {
/* 1351:1541 */         if (local_Request.isRMI())
/* 1352:     */         {
/* 1353:1543 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1354:1545 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1355:     */           }
/* 1356:     */         }
/* 1357:1550 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1358:1552 */           throw AEExceptionHelper.read(localUserException.input);
/* 1359:     */         }
/* 1360:1555 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1361:     */       }
/* 1362:     */       catch (SystemException localSystemException)
/* 1363:     */       {
/* 1364:1559 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1365:     */       }
/* 1366:     */       finally
/* 1367:     */       {
/* 1368:1563 */         if (local_Request != null) {
/* 1369:1565 */           local_Request.close();
/* 1370:     */         }
/* 1371:     */       }
/* 1372:     */     }
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   public void shutdownServerAll()
/* 1376:     */     throws java.rmi.RemoteException, AEException
/* 1377:     */   {
/* 1378:1574 */     for (int i = 1;; i++)
/* 1379:     */     {
/* 1380:1576 */       _Request local_Request = null;
/* 1381:     */       try
/* 1382:     */       {
/* 1383:1579 */         local_Request = __request("shutdownServerAll");
/* 1384:1580 */         local_Request.invoke();
/* 1385:1581 */         return;
/* 1386:     */       }
/* 1387:     */       catch (TRANSIENT localTRANSIENT)
/* 1388:     */       {
/* 1389:1585 */         if (i == 10) {
/* 1390:1587 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1391:     */         }
/* 1392:     */       }
/* 1393:     */       catch (UserException localUserException)
/* 1394:     */       {
/* 1395:1592 */         if (local_Request.isRMI())
/* 1396:     */         {
/* 1397:1594 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1398:1596 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1399:     */           }
/* 1400:     */         }
/* 1401:1601 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1402:1603 */           throw AEExceptionHelper.read(localUserException.input);
/* 1403:     */         }
/* 1404:1606 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1405:     */       }
/* 1406:     */       catch (SystemException localSystemException)
/* 1407:     */       {
/* 1408:1610 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1409:     */       }
/* 1410:     */       finally
/* 1411:     */       {
/* 1412:1614 */         if (local_Request != null) {
/* 1413:1616 */           local_Request.close();
/* 1414:     */         }
/* 1415:     */       }
/* 1416:     */     }
/* 1417:     */   }
/* 1418:     */   
/* 1419:     */   public boolean isSuspended()
/* 1420:     */     throws java.rmi.RemoteException, AEException
/* 1421:     */   {
/* 1422:1625 */     for (int i = 1;; i++)
/* 1423:     */     {
/* 1424:1627 */       _Request local_Request = null;
/* 1425:     */       try
/* 1426:     */       {
/* 1427:1630 */         local_Request = __request("isSuspended__", "isSuspended__");
/* 1428:1631 */         local_Request.invoke();
/* 1429:1632 */         InputStream localInputStream = local_Request.getInputStream();
/* 1430:     */         
/* 1431:1634 */         boolean bool2 = localInputStream.read_boolean();
/* 1432:1635 */         return bool2;
/* 1433:     */       }
/* 1434:     */       catch (TRANSIENT localTRANSIENT)
/* 1435:     */       {
/* 1436:1639 */         if (i == 10) {
/* 1437:1641 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1438:     */         }
/* 1439:     */       }
/* 1440:     */       catch (UserException localUserException)
/* 1441:     */       {
/* 1442:1646 */         if (local_Request.isRMI())
/* 1443:     */         {
/* 1444:1648 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1445:1650 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1446:     */           }
/* 1447:     */         }
/* 1448:1655 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1449:1657 */           throw AEExceptionHelper.read(localUserException.input);
/* 1450:     */         }
/* 1451:1660 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1452:     */       }
/* 1453:     */       catch (SystemException localSystemException)
/* 1454:     */       {
/* 1455:1664 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1456:     */       }
/* 1457:     */       finally
/* 1458:     */       {
/* 1459:1668 */         if (local_Request != null) {
/* 1460:1670 */           local_Request.close();
/* 1461:     */         }
/* 1462:     */       }
/* 1463:     */     }
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public boolean isSuspended(String paramString)
/* 1467:     */     throws java.rmi.RemoteException, AEException
/* 1468:     */   {
/* 1469:1680 */     for (int i = 1;; i++)
/* 1470:     */     {
/* 1471:1682 */       _Request local_Request = null;
/* 1472:     */       try
/* 1473:     */       {
/* 1474:1685 */         local_Request = __request("isSuspended__string", "isSuspended__CORBA_WStringValue");
/* 1475:1686 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1476:1687 */         local_Request.write_string(paramString);
/* 1477:1688 */         local_Request.invoke();
/* 1478:1689 */         InputStream localInputStream = local_Request.getInputStream();
/* 1479:     */         
/* 1480:1691 */         boolean bool2 = localInputStream.read_boolean();
/* 1481:1692 */         return bool2;
/* 1482:     */       }
/* 1483:     */       catch (TRANSIENT localTRANSIENT)
/* 1484:     */       {
/* 1485:1696 */         if (i == 10) {
/* 1486:1698 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1487:     */         }
/* 1488:     */       }
/* 1489:     */       catch (UserException localUserException)
/* 1490:     */       {
/* 1491:1703 */         if (local_Request.isRMI())
/* 1492:     */         {
/* 1493:1705 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1494:1707 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1495:     */           }
/* 1496:     */         }
/* 1497:1712 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1498:1714 */           throw AEExceptionHelper.read(localUserException.input);
/* 1499:     */         }
/* 1500:1717 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1501:     */       }
/* 1502:     */       catch (SystemException localSystemException)
/* 1503:     */       {
/* 1504:1721 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1505:     */       }
/* 1506:     */       finally
/* 1507:     */       {
/* 1508:1725 */         if (local_Request != null) {
/* 1509:1727 */           local_Request.close();
/* 1510:     */         }
/* 1511:     */       }
/* 1512:     */     }
/* 1513:     */   }
/* 1514:     */   
/* 1515:     */   public boolean isRunning(String paramString)
/* 1516:     */     throws java.rmi.RemoteException, AEException
/* 1517:     */   {
/* 1518:1737 */     for (int i = 1;; i++)
/* 1519:     */     {
/* 1520:1739 */       _Request local_Request = null;
/* 1521:     */       try
/* 1522:     */       {
/* 1523:1742 */         local_Request = __request("isRunning__string", "isRunning__CORBA_WStringValue");
/* 1524:1743 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1525:1744 */         local_Request.write_string(paramString);
/* 1526:1745 */         local_Request.invoke();
/* 1527:1746 */         InputStream localInputStream = local_Request.getInputStream();
/* 1528:     */         
/* 1529:1748 */         boolean bool2 = localInputStream.read_boolean();
/* 1530:1749 */         return bool2;
/* 1531:     */       }
/* 1532:     */       catch (TRANSIENT localTRANSIENT)
/* 1533:     */       {
/* 1534:1753 */         if (i == 10) {
/* 1535:1755 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1536:     */         }
/* 1537:     */       }
/* 1538:     */       catch (UserException localUserException)
/* 1539:     */       {
/* 1540:1760 */         if (local_Request.isRMI())
/* 1541:     */         {
/* 1542:1762 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1543:1764 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1544:     */           }
/* 1545:     */         }
/* 1546:1769 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1547:1771 */           throw AEExceptionHelper.read(localUserException.input);
/* 1548:     */         }
/* 1549:1774 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1550:     */       }
/* 1551:     */       catch (SystemException localSystemException)
/* 1552:     */       {
/* 1553:1778 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1554:     */       }
/* 1555:     */       finally
/* 1556:     */       {
/* 1557:1782 */         if (local_Request != null) {
/* 1558:1784 */           local_Request.close();
/* 1559:     */         }
/* 1560:     */       }
/* 1561:     */     }
/* 1562:     */   }
/* 1563:     */   
/* 1564:     */   public boolean isRunning()
/* 1565:     */     throws java.rmi.RemoteException, AEException
/* 1566:     */   {
/* 1567:1793 */     for (int i = 1;; i++)
/* 1568:     */     {
/* 1569:1795 */       _Request local_Request = null;
/* 1570:     */       try
/* 1571:     */       {
/* 1572:1798 */         local_Request = __request("isRunning__", "isRunning__");
/* 1573:1799 */         local_Request.invoke();
/* 1574:1800 */         InputStream localInputStream = local_Request.getInputStream();
/* 1575:     */         
/* 1576:1802 */         boolean bool2 = localInputStream.read_boolean();
/* 1577:1803 */         return bool2;
/* 1578:     */       }
/* 1579:     */       catch (TRANSIENT localTRANSIENT)
/* 1580:     */       {
/* 1581:1807 */         if (i == 10) {
/* 1582:1809 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1583:     */         }
/* 1584:     */       }
/* 1585:     */       catch (UserException localUserException)
/* 1586:     */       {
/* 1587:1814 */         if (local_Request.isRMI())
/* 1588:     */         {
/* 1589:1816 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1590:1818 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1591:     */           }
/* 1592:     */         }
/* 1593:1823 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1594:1825 */           throw AEExceptionHelper.read(localUserException.input);
/* 1595:     */         }
/* 1596:1828 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1597:     */       }
/* 1598:     */       catch (SystemException localSystemException)
/* 1599:     */       {
/* 1600:1832 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1601:     */       }
/* 1602:     */       finally
/* 1603:     */       {
/* 1604:1836 */         if (local_Request != null) {
/* 1605:1838 */           local_Request.close();
/* 1606:     */         }
/* 1607:     */       }
/* 1608:     */     }
/* 1609:     */   }
/* 1610:     */   
/* 1611:     */   public boolean isClusterStopped()
/* 1612:     */     throws java.rmi.RemoteException, AEException
/* 1613:     */   {
/* 1614:1847 */     for (int i = 1;; i++)
/* 1615:     */     {
/* 1616:1849 */       _Request local_Request = null;
/* 1617:     */       try
/* 1618:     */       {
/* 1619:1852 */         local_Request = __request("isClusterStopped");
/* 1620:1853 */         local_Request.invoke();
/* 1621:1854 */         InputStream localInputStream = local_Request.getInputStream();
/* 1622:     */         
/* 1623:1856 */         boolean bool2 = localInputStream.read_boolean();
/* 1624:1857 */         return bool2;
/* 1625:     */       }
/* 1626:     */       catch (TRANSIENT localTRANSIENT)
/* 1627:     */       {
/* 1628:1861 */         if (i == 10) {
/* 1629:1863 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1630:     */         }
/* 1631:     */       }
/* 1632:     */       catch (UserException localUserException)
/* 1633:     */       {
/* 1634:1868 */         if (local_Request.isRMI())
/* 1635:     */         {
/* 1636:1870 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1637:1872 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1638:     */           }
/* 1639:     */         }
/* 1640:1877 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1641:1879 */           throw AEExceptionHelper.read(localUserException.input);
/* 1642:     */         }
/* 1643:1882 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1644:     */       }
/* 1645:     */       catch (SystemException localSystemException)
/* 1646:     */       {
/* 1647:1886 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1648:     */       }
/* 1649:     */       finally
/* 1650:     */       {
/* 1651:1890 */         if (local_Request != null) {
/* 1652:1892 */           local_Request.close();
/* 1653:     */         }
/* 1654:     */       }
/* 1655:     */     }
/* 1656:     */   }
/* 1657:     */   
/* 1658:     */   public boolean isClusterRunning()
/* 1659:     */     throws java.rmi.RemoteException, AEException
/* 1660:     */   {
/* 1661:1901 */     for (int i = 1;; i++)
/* 1662:     */     {
/* 1663:1903 */       _Request local_Request = null;
/* 1664:     */       try
/* 1665:     */       {
/* 1666:1906 */         local_Request = __request("isClusterRunning");
/* 1667:1907 */         local_Request.invoke();
/* 1668:1908 */         InputStream localInputStream = local_Request.getInputStream();
/* 1669:     */         
/* 1670:1910 */         boolean bool2 = localInputStream.read_boolean();
/* 1671:1911 */         return bool2;
/* 1672:     */       }
/* 1673:     */       catch (TRANSIENT localTRANSIENT)
/* 1674:     */       {
/* 1675:1915 */         if (i == 10) {
/* 1676:1917 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1677:     */         }
/* 1678:     */       }
/* 1679:     */       catch (UserException localUserException)
/* 1680:     */       {
/* 1681:1922 */         if (local_Request.isRMI())
/* 1682:     */         {
/* 1683:1924 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1684:1926 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1685:     */           }
/* 1686:     */         }
/* 1687:1931 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1688:1933 */           throw AEExceptionHelper.read(localUserException.input);
/* 1689:     */         }
/* 1690:1936 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1691:     */       }
/* 1692:     */       catch (SystemException localSystemException)
/* 1693:     */       {
/* 1694:1940 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1695:     */       }
/* 1696:     */       finally
/* 1697:     */       {
/* 1698:1944 */         if (local_Request != null) {
/* 1699:1946 */           local_Request.close();
/* 1700:     */         }
/* 1701:     */       }
/* 1702:     */     }
/* 1703:     */   }
/* 1704:     */   
/* 1705:     */   public void updateApplication(String paramString, AEApplicationInfo paramAEApplicationInfo)
/* 1706:     */     throws java.rmi.RemoteException, AEException
/* 1707:     */   {
/* 1708:1957 */     for (int i = 1;; i++)
/* 1709:     */     {
/* 1710:1959 */       _Request local_Request = null;
/* 1711:     */       try
/* 1712:     */       {
/* 1713:1962 */         local_Request = __request("updateApplication");
/* 1714:1963 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1715:1964 */         local_Request.write_string(paramString);
/* 1716:1965 */         local_Request.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 1717:1966 */         local_Request.invoke();
/* 1718:1967 */         return;
/* 1719:     */       }
/* 1720:     */       catch (TRANSIENT localTRANSIENT)
/* 1721:     */       {
/* 1722:1971 */         if (i == 10) {
/* 1723:1973 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1724:     */         }
/* 1725:     */       }
/* 1726:     */       catch (UserException localUserException)
/* 1727:     */       {
/* 1728:1978 */         if (local_Request.isRMI())
/* 1729:     */         {
/* 1730:1980 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1731:1982 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1732:     */           }
/* 1733:     */         }
/* 1734:1987 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1735:1989 */           throw AEExceptionHelper.read(localUserException.input);
/* 1736:     */         }
/* 1737:1992 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1738:     */       }
/* 1739:     */       catch (SystemException localSystemException)
/* 1740:     */       {
/* 1741:1996 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1742:     */       }
/* 1743:     */       finally
/* 1744:     */       {
/* 1745:2000 */         if (local_Request != null) {
/* 1746:2002 */           local_Request.close();
/* 1747:     */         }
/* 1748:     */       }
/* 1749:     */     }
/* 1750:     */   }
/* 1751:     */   
/* 1752:     */   public IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt, String paramString3)
/* 1753:     */     throws java.rmi.RemoteException, AEException
/* 1754:     */   {
/* 1755:2016 */     for (int i = 1;; i++)
/* 1756:     */     {
/* 1757:2018 */       _Request local_Request = null;
/* 1758:     */       try
/* 1759:     */       {
/* 1760:2021 */         local_Request = __request("installDeliveryChannel__string__string__Properties__long__string", "installDeliveryChannel__CORBA_WStringValue__CORBA_WStringValue__org_omg_boxedIDL_java_util_Properties__long__CORBA_WStringValue");
/* 1761:2022 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1762:2023 */         local_Request.write_string(paramString1);
/* 1763:2024 */         local_Request.write_string(paramString2);
/* 1764:2025 */         local_Request.write_value(paramProperties, Properties.class);
/* 1765:2026 */         localOutputStream.write_long(paramInt);
/* 1766:2027 */         local_Request.write_string(paramString3);
/* 1767:2028 */         local_Request.invoke();
/* 1768:2029 */         InputStream localInputStream = local_Request.getInputStream();
/* 1769:     */         
/* 1770:2031 */         IAEChannelInfo localIAEChannelInfo2 = (IAEChannelInfo)local_Request.read_value(IAEChannelInfo.class);
/* 1771:2032 */         return localIAEChannelInfo2;
/* 1772:     */       }
/* 1773:     */       catch (TRANSIENT localTRANSIENT)
/* 1774:     */       {
/* 1775:2036 */         if (i == 10) {
/* 1776:2038 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1777:     */         }
/* 1778:     */       }
/* 1779:     */       catch (UserException localUserException)
/* 1780:     */       {
/* 1781:2043 */         if (local_Request.isRMI())
/* 1782:     */         {
/* 1783:2045 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1784:2047 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1785:     */           }
/* 1786:     */         }
/* 1787:2052 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1788:2054 */           throw AEExceptionHelper.read(localUserException.input);
/* 1789:     */         }
/* 1790:2057 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1791:     */       }
/* 1792:     */       catch (SystemException localSystemException)
/* 1793:     */       {
/* 1794:2061 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1795:     */       }
/* 1796:     */       finally
/* 1797:     */       {
/* 1798:2065 */         if (local_Request != null) {
/* 1799:2067 */           local_Request.close();
/* 1800:     */         }
/* 1801:     */       }
/* 1802:     */     }
/* 1803:     */   }
/* 1804:     */   
/* 1805:     */   public IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt)
/* 1806:     */     throws java.rmi.RemoteException, AEException
/* 1807:     */   {
/* 1808:2080 */     for (int i = 1;; i++)
/* 1809:     */     {
/* 1810:2082 */       _Request local_Request = null;
/* 1811:     */       try
/* 1812:     */       {
/* 1813:2085 */         local_Request = __request("installDeliveryChannel__string__string__Properties__long", "installDeliveryChannel__CORBA_WStringValue__CORBA_WStringValue__org_omg_boxedIDL_java_util_Properties__long");
/* 1814:2086 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1815:2087 */         local_Request.write_string(paramString1);
/* 1816:2088 */         local_Request.write_string(paramString2);
/* 1817:2089 */         local_Request.write_value(paramProperties, Properties.class);
/* 1818:2090 */         localOutputStream.write_long(paramInt);
/* 1819:2091 */         local_Request.invoke();
/* 1820:2092 */         InputStream localInputStream = local_Request.getInputStream();
/* 1821:     */         
/* 1822:2094 */         IAEChannelInfo localIAEChannelInfo2 = (IAEChannelInfo)local_Request.read_value(IAEChannelInfo.class);
/* 1823:2095 */         return localIAEChannelInfo2;
/* 1824:     */       }
/* 1825:     */       catch (TRANSIENT localTRANSIENT)
/* 1826:     */       {
/* 1827:2099 */         if (i == 10) {
/* 1828:2101 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1829:     */         }
/* 1830:     */       }
/* 1831:     */       catch (UserException localUserException)
/* 1832:     */       {
/* 1833:2106 */         if (local_Request.isRMI())
/* 1834:     */         {
/* 1835:2108 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1836:2110 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1837:     */           }
/* 1838:     */         }
/* 1839:2115 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1840:2117 */           throw AEExceptionHelper.read(localUserException.input);
/* 1841:     */         }
/* 1842:2120 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1843:     */       }
/* 1844:     */       catch (SystemException localSystemException)
/* 1845:     */       {
/* 1846:2124 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1847:     */       }
/* 1848:     */       finally
/* 1849:     */       {
/* 1850:2128 */         if (local_Request != null) {
/* 1851:2130 */           local_Request.close();
/* 1852:     */         }
/* 1853:     */       }
/* 1854:     */     }
/* 1855:     */   }
/* 1856:     */   
/* 1857:     */   public IAEChannelInfo uninstallDeliveryChannel(String paramString)
/* 1858:     */     throws java.rmi.RemoteException, AEException
/* 1859:     */   {
/* 1860:2140 */     for (int i = 1;; i++)
/* 1861:     */     {
/* 1862:2142 */       _Request local_Request = null;
/* 1863:     */       try
/* 1864:     */       {
/* 1865:2145 */         local_Request = __request("uninstallDeliveryChannel");
/* 1866:2146 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1867:2147 */         local_Request.write_string(paramString);
/* 1868:2148 */         local_Request.invoke();
/* 1869:2149 */         InputStream localInputStream = local_Request.getInputStream();
/* 1870:     */         
/* 1871:2151 */         IAEChannelInfo localIAEChannelInfo2 = (IAEChannelInfo)local_Request.read_value(IAEChannelInfo.class);
/* 1872:2152 */         return localIAEChannelInfo2;
/* 1873:     */       }
/* 1874:     */       catch (TRANSIENT localTRANSIENT)
/* 1875:     */       {
/* 1876:2156 */         if (i == 10) {
/* 1877:2158 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1878:     */         }
/* 1879:     */       }
/* 1880:     */       catch (UserException localUserException)
/* 1881:     */       {
/* 1882:2163 */         if (local_Request.isRMI())
/* 1883:     */         {
/* 1884:2165 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1885:2167 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1886:     */           }
/* 1887:     */         }
/* 1888:2172 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1889:2174 */           throw AEExceptionHelper.read(localUserException.input);
/* 1890:     */         }
/* 1891:2177 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1892:     */       }
/* 1893:     */       catch (SystemException localSystemException)
/* 1894:     */       {
/* 1895:2181 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1896:     */       }
/* 1897:     */       finally
/* 1898:     */       {
/* 1899:2185 */         if (local_Request != null) {
/* 1900:2187 */           local_Request.close();
/* 1901:     */         }
/* 1902:     */       }
/* 1903:     */     }
/* 1904:     */   }
/* 1905:     */   
/* 1906:     */   public IAEChannelInfo loadDeliveryChannel(String paramString)
/* 1907:     */     throws java.rmi.RemoteException, AEException
/* 1908:     */   {
/* 1909:2197 */     for (int i = 1;; i++)
/* 1910:     */     {
/* 1911:2199 */       _Request local_Request = null;
/* 1912:     */       try
/* 1913:     */       {
/* 1914:2202 */         local_Request = __request("loadDeliveryChannel");
/* 1915:2203 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1916:2204 */         local_Request.write_string(paramString);
/* 1917:2205 */         local_Request.invoke();
/* 1918:2206 */         InputStream localInputStream = local_Request.getInputStream();
/* 1919:     */         
/* 1920:2208 */         IAEChannelInfo localIAEChannelInfo2 = (IAEChannelInfo)local_Request.read_value(IAEChannelInfo.class);
/* 1921:2209 */         return localIAEChannelInfo2;
/* 1922:     */       }
/* 1923:     */       catch (TRANSIENT localTRANSIENT)
/* 1924:     */       {
/* 1925:2213 */         if (i == 10) {
/* 1926:2215 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1927:     */         }
/* 1928:     */       }
/* 1929:     */       catch (UserException localUserException)
/* 1930:     */       {
/* 1931:2220 */         if (local_Request.isRMI())
/* 1932:     */         {
/* 1933:2222 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1934:2224 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1935:     */           }
/* 1936:     */         }
/* 1937:2229 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1938:2231 */           throw AEExceptionHelper.read(localUserException.input);
/* 1939:     */         }
/* 1940:2234 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1941:     */       }
/* 1942:     */       catch (SystemException localSystemException)
/* 1943:     */       {
/* 1944:2238 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1945:     */       }
/* 1946:     */       finally
/* 1947:     */       {
/* 1948:2242 */         if (local_Request != null) {
/* 1949:2244 */           local_Request.close();
/* 1950:     */         }
/* 1951:     */       }
/* 1952:     */     }
/* 1953:     */   }
/* 1954:     */   
/* 1955:     */   public IAEChannelInfo unloadDeliveryChannel(String paramString)
/* 1956:     */     throws java.rmi.RemoteException, AEException
/* 1957:     */   {
/* 1958:2254 */     for (int i = 1;; i++)
/* 1959:     */     {
/* 1960:2256 */       _Request local_Request = null;
/* 1961:     */       try
/* 1962:     */       {
/* 1963:2259 */         local_Request = __request("unloadDeliveryChannel");
/* 1964:2260 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 1965:2261 */         local_Request.write_string(paramString);
/* 1966:2262 */         local_Request.invoke();
/* 1967:2263 */         InputStream localInputStream = local_Request.getInputStream();
/* 1968:     */         
/* 1969:2265 */         IAEChannelInfo localIAEChannelInfo2 = (IAEChannelInfo)local_Request.read_value(IAEChannelInfo.class);
/* 1970:2266 */         return localIAEChannelInfo2;
/* 1971:     */       }
/* 1972:     */       catch (TRANSIENT localTRANSIENT)
/* 1973:     */       {
/* 1974:2270 */         if (i == 10) {
/* 1975:2272 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1976:     */         }
/* 1977:     */       }
/* 1978:     */       catch (UserException localUserException)
/* 1979:     */       {
/* 1980:2277 */         if (local_Request.isRMI())
/* 1981:     */         {
/* 1982:2279 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 1983:2281 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 1984:     */           }
/* 1985:     */         }
/* 1986:2286 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 1987:2288 */           throw AEExceptionHelper.read(localUserException.input);
/* 1988:     */         }
/* 1989:2291 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1990:     */       }
/* 1991:     */       catch (SystemException localSystemException)
/* 1992:     */       {
/* 1993:2295 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1994:     */       }
/* 1995:     */       finally
/* 1996:     */       {
/* 1997:2299 */         if (local_Request != null) {
/* 1998:2301 */           local_Request.close();
/* 1999:     */         }
/* 2000:     */       }
/* 2001:     */     }
/* 2002:     */   }
/* 2003:     */   
/* 2004:     */   public IAEChannelInfo updateDeliveryChannel(IAEChannelInfo paramIAEChannelInfo)
/* 2005:     */     throws java.rmi.RemoteException, AEException
/* 2006:     */   {
/* 2007:2311 */     for (int i = 1;; i++)
/* 2008:     */     {
/* 2009:2313 */       _Request local_Request = null;
/* 2010:     */       try
/* 2011:     */       {
/* 2012:2316 */         local_Request = __request("updateDeliveryChannel");
/* 2013:2317 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2014:2318 */         local_Request.write_value(paramIAEChannelInfo, IAEChannelInfo.class);
/* 2015:2319 */         local_Request.invoke();
/* 2016:2320 */         InputStream localInputStream = local_Request.getInputStream();
/* 2017:     */         
/* 2018:2322 */         IAEChannelInfo localIAEChannelInfo2 = (IAEChannelInfo)local_Request.read_value(IAEChannelInfo.class);
/* 2019:2323 */         return localIAEChannelInfo2;
/* 2020:     */       }
/* 2021:     */       catch (TRANSIENT localTRANSIENT)
/* 2022:     */       {
/* 2023:2327 */         if (i == 10) {
/* 2024:2329 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2025:     */         }
/* 2026:     */       }
/* 2027:     */       catch (UserException localUserException)
/* 2028:     */       {
/* 2029:2334 */         if (local_Request.isRMI())
/* 2030:     */         {
/* 2031:2336 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2032:2338 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2033:     */           }
/* 2034:     */         }
/* 2035:2343 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2036:2345 */           throw AEExceptionHelper.read(localUserException.input);
/* 2037:     */         }
/* 2038:2348 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2039:     */       }
/* 2040:     */       catch (SystemException localSystemException)
/* 2041:     */       {
/* 2042:2352 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2043:     */       }
/* 2044:     */       finally
/* 2045:     */       {
/* 2046:2356 */         if (local_Request != null) {
/* 2047:2358 */           local_Request.close();
/* 2048:     */         }
/* 2049:     */       }
/* 2050:     */     }
/* 2051:     */   }
/* 2052:     */   
/* 2053:     */   public IAEChannelInfo[] getInstalledDeliveryChannels(String paramString)
/* 2054:     */     throws java.rmi.RemoteException, AEException
/* 2055:     */   {
/* 2056:2368 */     for (int i = 1;; i++)
/* 2057:     */     {
/* 2058:2370 */       _Request local_Request = null;
/* 2059:     */       try
/* 2060:     */       {
/* 2061:2373 */         local_Request = __request("getInstalledDeliveryChannels");
/* 2062:2374 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2063:2375 */         local_Request.write_string(paramString);
/* 2064:2376 */         local_Request.invoke();
/* 2065:2377 */         InputStream localInputStream = local_Request.getInputStream();
/* 2066:     */         IAEChannelInfo[] arrayOfIAEChannelInfo2;
/* 2067:2379 */         if (local_Request.isRMI()) {
/* 2068:2379 */           arrayOfIAEChannelInfo2 = (IAEChannelInfo[])local_Request.read_value(new IAEChannelInfo[0].getClass());
/* 2069:     */         } else {
/* 2070:2379 */           arrayOfIAEChannelInfo2 = IAEChannelInfoSeqHelper.read(localInputStream);
/* 2071:     */         }
/* 2072:2380 */         return arrayOfIAEChannelInfo2;
/* 2073:     */       }
/* 2074:     */       catch (TRANSIENT localTRANSIENT)
/* 2075:     */       {
/* 2076:2384 */         if (i == 10) {
/* 2077:2386 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2078:     */         }
/* 2079:     */       }
/* 2080:     */       catch (UserException localUserException)
/* 2081:     */       {
/* 2082:2391 */         if (local_Request.isRMI())
/* 2083:     */         {
/* 2084:2393 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2085:2395 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2086:     */           }
/* 2087:     */         }
/* 2088:2400 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2089:2402 */           throw AEExceptionHelper.read(localUserException.input);
/* 2090:     */         }
/* 2091:2405 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2092:     */       }
/* 2093:     */       catch (SystemException localSystemException)
/* 2094:     */       {
/* 2095:2409 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2096:     */       }
/* 2097:     */       finally
/* 2098:     */       {
/* 2099:2413 */         if (local_Request != null) {
/* 2100:2415 */           local_Request.close();
/* 2101:     */         }
/* 2102:     */       }
/* 2103:     */     }
/* 2104:     */   }
/* 2105:     */   
/* 2106:     */   public IAEChannelInfo[] getLoadedDeliveryChannels(String paramString)
/* 2107:     */     throws java.rmi.RemoteException, AEException
/* 2108:     */   {
/* 2109:2425 */     for (int i = 1;; i++)
/* 2110:     */     {
/* 2111:2427 */       _Request local_Request = null;
/* 2112:     */       try
/* 2113:     */       {
/* 2114:2430 */         local_Request = __request("getLoadedDeliveryChannels");
/* 2115:2431 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2116:2432 */         local_Request.write_string(paramString);
/* 2117:2433 */         local_Request.invoke();
/* 2118:2434 */         InputStream localInputStream = local_Request.getInputStream();
/* 2119:     */         IAEChannelInfo[] arrayOfIAEChannelInfo2;
/* 2120:2436 */         if (local_Request.isRMI()) {
/* 2121:2436 */           arrayOfIAEChannelInfo2 = (IAEChannelInfo[])local_Request.read_value(new IAEChannelInfo[0].getClass());
/* 2122:     */         } else {
/* 2123:2436 */           arrayOfIAEChannelInfo2 = IAEChannelInfoSeqHelper.read(localInputStream);
/* 2124:     */         }
/* 2125:2437 */         return arrayOfIAEChannelInfo2;
/* 2126:     */       }
/* 2127:     */       catch (TRANSIENT localTRANSIENT)
/* 2128:     */       {
/* 2129:2441 */         if (i == 10) {
/* 2130:2443 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2131:     */         }
/* 2132:     */       }
/* 2133:     */       catch (UserException localUserException)
/* 2134:     */       {
/* 2135:2448 */         if (local_Request.isRMI())
/* 2136:     */         {
/* 2137:2450 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2138:2452 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2139:     */           }
/* 2140:     */         }
/* 2141:2457 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2142:2459 */           throw AEExceptionHelper.read(localUserException.input);
/* 2143:     */         }
/* 2144:2462 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2145:     */       }
/* 2146:     */       catch (SystemException localSystemException)
/* 2147:     */       {
/* 2148:2466 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2149:     */       }
/* 2150:     */       finally
/* 2151:     */       {
/* 2152:2470 */         if (local_Request != null) {
/* 2153:2472 */           local_Request.close();
/* 2154:     */         }
/* 2155:     */       }
/* 2156:     */     }
/* 2157:     */   }
/* 2158:     */   
/* 2159:     */   public IAEAlertDefinition[] getAllAlerts(boolean paramBoolean)
/* 2160:     */     throws java.rmi.RemoteException, AEException
/* 2161:     */   {
/* 2162:2482 */     for (int i = 1;; i++)
/* 2163:     */     {
/* 2164:2484 */       _Request local_Request = null;
/* 2165:     */       try
/* 2166:     */       {
/* 2167:2487 */         local_Request = __request("getAllAlerts");
/* 2168:2488 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2169:2489 */         localOutputStream.write_boolean(paramBoolean);
/* 2170:2490 */         local_Request.invoke();
/* 2171:2491 */         InputStream localInputStream = local_Request.getInputStream();
/* 2172:     */         IAEAlertDefinition[] arrayOfIAEAlertDefinition2;
/* 2173:2493 */         if (local_Request.isRMI()) {
/* 2174:2493 */           arrayOfIAEAlertDefinition2 = (IAEAlertDefinition[])local_Request.read_value(new IAEAlertDefinition[0].getClass());
/* 2175:     */         } else {
/* 2176:2493 */           arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.read(localInputStream);
/* 2177:     */         }
/* 2178:2494 */         return arrayOfIAEAlertDefinition2;
/* 2179:     */       }
/* 2180:     */       catch (TRANSIENT localTRANSIENT)
/* 2181:     */       {
/* 2182:2498 */         if (i == 10) {
/* 2183:2500 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2184:     */         }
/* 2185:     */       }
/* 2186:     */       catch (UserException localUserException)
/* 2187:     */       {
/* 2188:2505 */         if (local_Request.isRMI())
/* 2189:     */         {
/* 2190:2507 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2191:2509 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2192:     */           }
/* 2193:     */         }
/* 2194:2514 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2195:2516 */           throw AEExceptionHelper.read(localUserException.input);
/* 2196:     */         }
/* 2197:2519 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2198:     */       }
/* 2199:     */       catch (SystemException localSystemException)
/* 2200:     */       {
/* 2201:2523 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2202:     */       }
/* 2203:     */       finally
/* 2204:     */       {
/* 2205:2527 */         if (local_Request != null) {
/* 2206:2529 */           local_Request.close();
/* 2207:     */         }
/* 2208:     */       }
/* 2209:     */     }
/* 2210:     */   }
/* 2211:     */   
/* 2212:     */   public IAEAlertDefinition[] getAppAlerts(String paramString, boolean paramBoolean)
/* 2213:     */     throws java.rmi.RemoteException, AEException
/* 2214:     */   {
/* 2215:2540 */     for (int i = 1;; i++)
/* 2216:     */     {
/* 2217:2542 */       _Request local_Request = null;
/* 2218:     */       try
/* 2219:     */       {
/* 2220:2545 */         local_Request = __request("getAppAlerts");
/* 2221:2546 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2222:2547 */         local_Request.write_string(paramString);
/* 2223:2548 */         localOutputStream.write_boolean(paramBoolean);
/* 2224:2549 */         local_Request.invoke();
/* 2225:2550 */         InputStream localInputStream = local_Request.getInputStream();
/* 2226:     */         IAEAlertDefinition[] arrayOfIAEAlertDefinition2;
/* 2227:2552 */         if (local_Request.isRMI()) {
/* 2228:2552 */           arrayOfIAEAlertDefinition2 = (IAEAlertDefinition[])local_Request.read_value(new IAEAlertDefinition[0].getClass());
/* 2229:     */         } else {
/* 2230:2552 */           arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.read(localInputStream);
/* 2231:     */         }
/* 2232:2553 */         return arrayOfIAEAlertDefinition2;
/* 2233:     */       }
/* 2234:     */       catch (TRANSIENT localTRANSIENT)
/* 2235:     */       {
/* 2236:2557 */         if (i == 10) {
/* 2237:2559 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2238:     */         }
/* 2239:     */       }
/* 2240:     */       catch (UserException localUserException)
/* 2241:     */       {
/* 2242:2564 */         if (local_Request.isRMI())
/* 2243:     */         {
/* 2244:2566 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2245:2568 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2246:     */           }
/* 2247:     */         }
/* 2248:2573 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2249:2575 */           throw AEExceptionHelper.read(localUserException.input);
/* 2250:     */         }
/* 2251:2578 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2252:     */       }
/* 2253:     */       catch (SystemException localSystemException)
/* 2254:     */       {
/* 2255:2582 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2256:     */       }
/* 2257:     */       finally
/* 2258:     */       {
/* 2259:2586 */         if (local_Request != null) {
/* 2260:2588 */           local_Request.close();
/* 2261:     */         }
/* 2262:     */       }
/* 2263:     */     }
/* 2264:     */   }
/* 2265:     */   
/* 2266:     */   public IAEAlertDefinition[] getAppAlertsForChannelPaged(String paramString1, boolean paramBoolean, String paramString2, int paramInt1, int paramInt2)
/* 2267:     */     throws java.rmi.RemoteException, AEException
/* 2268:     */   {
/* 2269:2602 */     for (int i = 1;; i++)
/* 2270:     */     {
/* 2271:2604 */       _Request local_Request = null;
/* 2272:     */       try
/* 2273:     */       {
/* 2274:2607 */         local_Request = __request("getAppAlertsForChannelPaged");
/* 2275:2608 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2276:2609 */         local_Request.write_string(paramString1);
/* 2277:2610 */         localOutputStream.write_boolean(paramBoolean);
/* 2278:2611 */         local_Request.write_string(paramString2);
/* 2279:2612 */         localOutputStream.write_long(paramInt1);
/* 2280:2613 */         localOutputStream.write_long(paramInt2);
/* 2281:2614 */         local_Request.invoke();
/* 2282:2615 */         InputStream localInputStream = local_Request.getInputStream();
/* 2283:     */         IAEAlertDefinition[] arrayOfIAEAlertDefinition2;
/* 2284:2617 */         if (local_Request.isRMI()) {
/* 2285:2617 */           arrayOfIAEAlertDefinition2 = (IAEAlertDefinition[])local_Request.read_value(new IAEAlertDefinition[0].getClass());
/* 2286:     */         } else {
/* 2287:2617 */           arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.read(localInputStream);
/* 2288:     */         }
/* 2289:2618 */         return arrayOfIAEAlertDefinition2;
/* 2290:     */       }
/* 2291:     */       catch (TRANSIENT localTRANSIENT)
/* 2292:     */       {
/* 2293:2622 */         if (i == 10) {
/* 2294:2624 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2295:     */         }
/* 2296:     */       }
/* 2297:     */       catch (UserException localUserException)
/* 2298:     */       {
/* 2299:2629 */         if (local_Request.isRMI())
/* 2300:     */         {
/* 2301:2631 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2302:2633 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2303:     */           }
/* 2304:     */         }
/* 2305:2638 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2306:2640 */           throw AEExceptionHelper.read(localUserException.input);
/* 2307:     */         }
/* 2308:2643 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2309:     */       }
/* 2310:     */       catch (SystemException localSystemException)
/* 2311:     */       {
/* 2312:2647 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2313:     */       }
/* 2314:     */       finally
/* 2315:     */       {
/* 2316:2651 */         if (local_Request != null) {
/* 2317:2653 */           local_Request.close();
/* 2318:     */         }
/* 2319:     */       }
/* 2320:     */     }
/* 2321:     */   }
/* 2322:     */   
/* 2323:     */   public IAEAlertDefinition[] getUserAlerts(String paramString, boolean paramBoolean)
/* 2324:     */     throws java.rmi.RemoteException, AEException
/* 2325:     */   {
/* 2326:2664 */     for (int i = 1;; i++)
/* 2327:     */     {
/* 2328:2666 */       _Request local_Request = null;
/* 2329:     */       try
/* 2330:     */       {
/* 2331:2669 */         local_Request = __request("getUserAlerts__string__boolean", "getUserAlerts__CORBA_WStringValue__boolean");
/* 2332:2670 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2333:2671 */         local_Request.write_string(paramString);
/* 2334:2672 */         localOutputStream.write_boolean(paramBoolean);
/* 2335:2673 */         local_Request.invoke();
/* 2336:2674 */         InputStream localInputStream = local_Request.getInputStream();
/* 2337:     */         IAEAlertDefinition[] arrayOfIAEAlertDefinition2;
/* 2338:2676 */         if (local_Request.isRMI()) {
/* 2339:2676 */           arrayOfIAEAlertDefinition2 = (IAEAlertDefinition[])local_Request.read_value(new IAEAlertDefinition[0].getClass());
/* 2340:     */         } else {
/* 2341:2676 */           arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.read(localInputStream);
/* 2342:     */         }
/* 2343:2677 */         return arrayOfIAEAlertDefinition2;
/* 2344:     */       }
/* 2345:     */       catch (TRANSIENT localTRANSIENT)
/* 2346:     */       {
/* 2347:2681 */         if (i == 10) {
/* 2348:2683 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2349:     */         }
/* 2350:     */       }
/* 2351:     */       catch (UserException localUserException)
/* 2352:     */       {
/* 2353:2688 */         if (local_Request.isRMI())
/* 2354:     */         {
/* 2355:2690 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2356:2692 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2357:     */           }
/* 2358:     */         }
/* 2359:2697 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2360:2699 */           throw AEExceptionHelper.read(localUserException.input);
/* 2361:     */         }
/* 2362:2702 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2363:     */       }
/* 2364:     */       catch (SystemException localSystemException)
/* 2365:     */       {
/* 2366:2706 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2367:     */       }
/* 2368:     */       finally
/* 2369:     */       {
/* 2370:2710 */         if (local_Request != null) {
/* 2371:2712 */           local_Request.close();
/* 2372:     */         }
/* 2373:     */       }
/* 2374:     */     }
/* 2375:     */   }
/* 2376:     */   
/* 2377:     */   public IAEAlertDefinition[] getUserAlerts(String paramString1, String paramString2, boolean paramBoolean)
/* 2378:     */     throws java.rmi.RemoteException, AEException
/* 2379:     */   {
/* 2380:2724 */     for (int i = 1;; i++)
/* 2381:     */     {
/* 2382:2726 */       _Request local_Request = null;
/* 2383:     */       try
/* 2384:     */       {
/* 2385:2729 */         local_Request = __request("getUserAlerts__string__string__boolean", "getUserAlerts__CORBA_WStringValue__CORBA_WStringValue__boolean");
/* 2386:2730 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2387:2731 */         local_Request.write_string(paramString1);
/* 2388:2732 */         local_Request.write_string(paramString2);
/* 2389:2733 */         localOutputStream.write_boolean(paramBoolean);
/* 2390:2734 */         local_Request.invoke();
/* 2391:2735 */         InputStream localInputStream = local_Request.getInputStream();
/* 2392:     */         IAEAlertDefinition[] arrayOfIAEAlertDefinition2;
/* 2393:2737 */         if (local_Request.isRMI()) {
/* 2394:2737 */           arrayOfIAEAlertDefinition2 = (IAEAlertDefinition[])local_Request.read_value(new IAEAlertDefinition[0].getClass());
/* 2395:     */         } else {
/* 2396:2737 */           arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.read(localInputStream);
/* 2397:     */         }
/* 2398:2738 */         return arrayOfIAEAlertDefinition2;
/* 2399:     */       }
/* 2400:     */       catch (TRANSIENT localTRANSIENT)
/* 2401:     */       {
/* 2402:2742 */         if (i == 10) {
/* 2403:2744 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2404:     */         }
/* 2405:     */       }
/* 2406:     */       catch (UserException localUserException)
/* 2407:     */       {
/* 2408:2749 */         if (local_Request.isRMI())
/* 2409:     */         {
/* 2410:2751 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2411:2753 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2412:     */           }
/* 2413:     */         }
/* 2414:2758 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2415:2760 */           throw AEExceptionHelper.read(localUserException.input);
/* 2416:     */         }
/* 2417:2763 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2418:     */       }
/* 2419:     */       catch (SystemException localSystemException)
/* 2420:     */       {
/* 2421:2767 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2422:     */       }
/* 2423:     */       finally
/* 2424:     */       {
/* 2425:2771 */         if (local_Request != null) {
/* 2426:2773 */           local_Request.close();
/* 2427:     */         }
/* 2428:     */       }
/* 2429:     */     }
/* 2430:     */   }
/* 2431:     */   
/* 2432:     */   public IAEAlertDefinition getAlert(int paramInt)
/* 2433:     */     throws java.rmi.RemoteException, AEException
/* 2434:     */   {
/* 2435:2783 */     for (int i = 1;; i++)
/* 2436:     */     {
/* 2437:2785 */       _Request local_Request = null;
/* 2438:     */       try
/* 2439:     */       {
/* 2440:2788 */         local_Request = __request("getAlert");
/* 2441:2789 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2442:2790 */         localOutputStream.write_long(paramInt);
/* 2443:2791 */         local_Request.invoke();
/* 2444:2792 */         InputStream localInputStream = local_Request.getInputStream();
/* 2445:     */         
/* 2446:2794 */         IAEAlertDefinition localIAEAlertDefinition2 = (IAEAlertDefinition)local_Request.read_value(IAEAlertDefinition.class);
/* 2447:2795 */         return localIAEAlertDefinition2;
/* 2448:     */       }
/* 2449:     */       catch (TRANSIENT localTRANSIENT)
/* 2450:     */       {
/* 2451:2799 */         if (i == 10) {
/* 2452:2801 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2453:     */         }
/* 2454:     */       }
/* 2455:     */       catch (UserException localUserException)
/* 2456:     */       {
/* 2457:2806 */         if (local_Request.isRMI())
/* 2458:     */         {
/* 2459:2808 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2460:2810 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2461:     */           }
/* 2462:     */         }
/* 2463:2815 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2464:2817 */           throw AEExceptionHelper.read(localUserException.input);
/* 2465:     */         }
/* 2466:2820 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2467:     */       }
/* 2468:     */       catch (SystemException localSystemException)
/* 2469:     */       {
/* 2470:2824 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2471:     */       }
/* 2472:     */       finally
/* 2473:     */       {
/* 2474:2828 */         if (local_Request != null) {
/* 2475:2830 */           local_Request.close();
/* 2476:     */         }
/* 2477:     */       }
/* 2478:     */     }
/* 2479:     */   }
/* 2480:     */   
/* 2481:     */   public IAEAlertDefinition[] getAlerts(int[] paramArrayOfInt)
/* 2482:     */     throws java.rmi.RemoteException, AEException
/* 2483:     */   {
/* 2484:2840 */     for (int i = 1;; i++)
/* 2485:     */     {
/* 2486:2842 */       _Request local_Request = null;
/* 2487:     */       try
/* 2488:     */       {
/* 2489:2845 */         local_Request = __request("getAlerts");
/* 2490:2846 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2491:2847 */         if (local_Request.isRMI()) {
/* 2492:2847 */           local_Request.write_value(paramArrayOfInt, new int[0].getClass());
/* 2493:     */         } else {
/* 2494:2847 */           longSeqHelper.write(localOutputStream, paramArrayOfInt);
/* 2495:     */         }
/* 2496:2848 */         local_Request.invoke();
/* 2497:2849 */         InputStream localInputStream = local_Request.getInputStream();
/* 2498:     */         IAEAlertDefinition[] arrayOfIAEAlertDefinition2;
/* 2499:2851 */         if (local_Request.isRMI()) {
/* 2500:2851 */           arrayOfIAEAlertDefinition2 = (IAEAlertDefinition[])local_Request.read_value(new IAEAlertDefinition[0].getClass());
/* 2501:     */         } else {
/* 2502:2851 */           arrayOfIAEAlertDefinition2 = IAEAlertDefinitionSeqHelper.read(localInputStream);
/* 2503:     */         }
/* 2504:2852 */         return arrayOfIAEAlertDefinition2;
/* 2505:     */       }
/* 2506:     */       catch (TRANSIENT localTRANSIENT)
/* 2507:     */       {
/* 2508:2856 */         if (i == 10) {
/* 2509:2858 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2510:     */         }
/* 2511:     */       }
/* 2512:     */       catch (UserException localUserException)
/* 2513:     */       {
/* 2514:2863 */         if (local_Request.isRMI())
/* 2515:     */         {
/* 2516:2865 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2517:2867 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2518:     */           }
/* 2519:     */         }
/* 2520:2872 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2521:2874 */           throw AEExceptionHelper.read(localUserException.input);
/* 2522:     */         }
/* 2523:2877 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2524:     */       }
/* 2525:     */       catch (SystemException localSystemException)
/* 2526:     */       {
/* 2527:2881 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2528:     */       }
/* 2529:     */       finally
/* 2530:     */       {
/* 2531:2885 */         if (local_Request != null) {
/* 2532:2887 */           local_Request.close();
/* 2533:     */         }
/* 2534:     */       }
/* 2535:     */     }
/* 2536:     */   }
/* 2537:     */   
/* 2538:     */   public IAEAlertDefinition updateAlert(int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
/* 2539:     */     throws java.rmi.RemoteException, AEException
/* 2540:     */   {
/* 2541:2903 */     for (int i = 1;; i++)
/* 2542:     */     {
/* 2543:2905 */       _Request local_Request = null;
/* 2544:     */       try
/* 2545:     */       {
/* 2546:2908 */         local_Request = __request("updateAlert__long__string__long__long__AEScheduleInfo__IAEDeliveryInfoSeq__string", "updateAlert__long__CORBA_WStringValue__long__long__org_omg_boxedIDL_com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__CORBA_WStringValue");
/* 2547:2909 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2548:2910 */         localOutputStream.write_long(paramInt1);
/* 2549:2911 */         local_Request.write_string(paramString1);
/* 2550:2912 */         localOutputStream.write_long(paramInt2);
/* 2551:2913 */         localOutputStream.write_long(paramInt3);
/* 2552:2914 */         local_Request.write_value(paramAEScheduleInfo, AEScheduleInfo.class);
/* 2553:2915 */         if (local_Request.isRMI()) {
/* 2554:2915 */           local_Request.write_value(paramArrayOfIAEDeliveryInfo, new IAEDeliveryInfo[0].getClass());
/* 2555:     */         } else {
/* 2556:2915 */           IAEDeliveryInfoSeqHelper.write(localOutputStream, paramArrayOfIAEDeliveryInfo);
/* 2557:     */         }
/* 2558:2916 */         local_Request.write_string(paramString2);
/* 2559:2917 */         local_Request.invoke();
/* 2560:2918 */         InputStream localInputStream = local_Request.getInputStream();
/* 2561:     */         
/* 2562:2920 */         IAEAlertDefinition localIAEAlertDefinition2 = (IAEAlertDefinition)local_Request.read_value(IAEAlertDefinition.class);
/* 2563:2921 */         return localIAEAlertDefinition2;
/* 2564:     */       }
/* 2565:     */       catch (TRANSIENT localTRANSIENT)
/* 2566:     */       {
/* 2567:2925 */         if (i == 10) {
/* 2568:2927 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2569:     */         }
/* 2570:     */       }
/* 2571:     */       catch (UserException localUserException)
/* 2572:     */       {
/* 2573:2932 */         if (local_Request.isRMI())
/* 2574:     */         {
/* 2575:2934 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2576:2936 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2577:     */           }
/* 2578:     */         }
/* 2579:2941 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2580:2943 */           throw AEExceptionHelper.read(localUserException.input);
/* 2581:     */         }
/* 2582:2946 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2583:     */       }
/* 2584:     */       catch (SystemException localSystemException)
/* 2585:     */       {
/* 2586:2950 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2587:     */       }
/* 2588:     */       finally
/* 2589:     */       {
/* 2590:2954 */         if (local_Request != null) {
/* 2591:2956 */           local_Request.close();
/* 2592:     */         }
/* 2593:     */       }
/* 2594:     */     }
/* 2595:     */   }
/* 2596:     */   
/* 2597:     */   public IAEAlertDefinition updateAlert(int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
/* 2598:     */     throws java.rmi.RemoteException, AEException
/* 2599:     */   {
/* 2600:2972 */     for (int i = 1;; i++)
/* 2601:     */     {
/* 2602:2974 */       _Request local_Request = null;
/* 2603:     */       try
/* 2604:     */       {
/* 2605:2977 */         local_Request = __request("updateAlert__long__string__long__long__AEScheduleInfo__IAEDeliveryInfoSeq__BCD_Binary", "updateAlert__long__CORBA_WStringValue__long__long__org_omg_boxedIDL_com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__org_omg_boxedIDL_byte[]");
/* 2606:2978 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2607:2979 */         localOutputStream.write_long(paramInt1);
/* 2608:2980 */         local_Request.write_string(paramString);
/* 2609:2981 */         localOutputStream.write_long(paramInt2);
/* 2610:2982 */         localOutputStream.write_long(paramInt3);
/* 2611:2983 */         local_Request.write_value(paramAEScheduleInfo, AEScheduleInfo.class);
/* 2612:2984 */         if (local_Request.isRMI()) {
/* 2613:2984 */           local_Request.write_value(paramArrayOfIAEDeliveryInfo, new IAEDeliveryInfo[0].getClass());
/* 2614:     */         } else {
/* 2615:2984 */           IAEDeliveryInfoSeqHelper.write(localOutputStream, paramArrayOfIAEDeliveryInfo);
/* 2616:     */         }
/* 2617:2985 */         if (local_Request.isRMI()) {
/* 2618:2985 */           local_Request.write_value(paramArrayOfByte, new byte[0].getClass());
/* 2619:     */         } else {
/* 2620:2985 */           BinaryHelper.write(localOutputStream, paramArrayOfByte);
/* 2621:     */         }
/* 2622:2986 */         local_Request.invoke();
/* 2623:2987 */         InputStream localInputStream = local_Request.getInputStream();
/* 2624:     */         
/* 2625:2989 */         IAEAlertDefinition localIAEAlertDefinition2 = (IAEAlertDefinition)local_Request.read_value(IAEAlertDefinition.class);
/* 2626:2990 */         return localIAEAlertDefinition2;
/* 2627:     */       }
/* 2628:     */       catch (TRANSIENT localTRANSIENT)
/* 2629:     */       {
/* 2630:2994 */         if (i == 10) {
/* 2631:2996 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2632:     */         }
/* 2633:     */       }
/* 2634:     */       catch (UserException localUserException)
/* 2635:     */       {
/* 2636:3001 */         if (local_Request.isRMI())
/* 2637:     */         {
/* 2638:3003 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2639:3005 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2640:     */           }
/* 2641:     */         }
/* 2642:3010 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2643:3012 */           throw AEExceptionHelper.read(localUserException.input);
/* 2644:     */         }
/* 2645:3015 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2646:     */       }
/* 2647:     */       catch (SystemException localSystemException)
/* 2648:     */       {
/* 2649:3019 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2650:     */       }
/* 2651:     */       finally
/* 2652:     */       {
/* 2653:3023 */         if (local_Request != null) {
/* 2654:3025 */           local_Request.close();
/* 2655:     */         }
/* 2656:     */       }
/* 2657:     */     }
/* 2658:     */   }
/* 2659:     */   
/* 2660:     */   public void cancelAlert(int paramInt)
/* 2661:     */     throws java.rmi.RemoteException, AEException
/* 2662:     */   {
/* 2663:3035 */     for (int i = 1;; i++)
/* 2664:     */     {
/* 2665:3037 */       _Request local_Request = null;
/* 2666:     */       try
/* 2667:     */       {
/* 2668:3040 */         local_Request = __request("cancelAlert");
/* 2669:3041 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2670:3042 */         localOutputStream.write_long(paramInt);
/* 2671:3043 */         local_Request.invoke();
/* 2672:3044 */         return;
/* 2673:     */       }
/* 2674:     */       catch (TRANSIENT localTRANSIENT)
/* 2675:     */       {
/* 2676:3048 */         if (i == 10) {
/* 2677:3050 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2678:     */         }
/* 2679:     */       }
/* 2680:     */       catch (UserException localUserException)
/* 2681:     */       {
/* 2682:3055 */         if (local_Request.isRMI())
/* 2683:     */         {
/* 2684:3057 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2685:3059 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2686:     */           }
/* 2687:     */         }
/* 2688:3064 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2689:3066 */           throw AEExceptionHelper.read(localUserException.input);
/* 2690:     */         }
/* 2691:3069 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2692:     */       }
/* 2693:     */       catch (SystemException localSystemException)
/* 2694:     */       {
/* 2695:3073 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2696:     */       }
/* 2697:     */       finally
/* 2698:     */       {
/* 2699:3077 */         if (local_Request != null) {
/* 2700:3079 */           local_Request.close();
/* 2701:     */         }
/* 2702:     */       }
/* 2703:     */     }
/* 2704:     */   }
/* 2705:     */   
/* 2706:     */   public void cancelUserAlerts(String paramString1, String paramString2)
/* 2707:     */     throws java.rmi.RemoteException, AEException
/* 2708:     */   {
/* 2709:3090 */     for (int i = 1;; i++)
/* 2710:     */     {
/* 2711:3092 */       _Request local_Request = null;
/* 2712:     */       try
/* 2713:     */       {
/* 2714:3095 */         local_Request = __request("cancelUserAlerts");
/* 2715:3096 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2716:3097 */         local_Request.write_string(paramString1);
/* 2717:3098 */         local_Request.write_string(paramString2);
/* 2718:3099 */         local_Request.invoke();
/* 2719:3100 */         return;
/* 2720:     */       }
/* 2721:     */       catch (TRANSIENT localTRANSIENT)
/* 2722:     */       {
/* 2723:3104 */         if (i == 10) {
/* 2724:3106 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2725:     */         }
/* 2726:     */       }
/* 2727:     */       catch (UserException localUserException)
/* 2728:     */       {
/* 2729:3111 */         if (local_Request.isRMI())
/* 2730:     */         {
/* 2731:3113 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2732:3115 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2733:     */           }
/* 2734:     */         }
/* 2735:3120 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2736:3122 */           throw AEExceptionHelper.read(localUserException.input);
/* 2737:     */         }
/* 2738:3125 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2739:     */       }
/* 2740:     */       catch (SystemException localSystemException)
/* 2741:     */       {
/* 2742:3129 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2743:     */       }
/* 2744:     */       finally
/* 2745:     */       {
/* 2746:3133 */         if (local_Request != null) {
/* 2747:3135 */           local_Request.close();
/* 2748:     */         }
/* 2749:     */       }
/* 2750:     */     }
/* 2751:     */   }
/* 2752:     */   
/* 2753:     */   public IAEAuditInfo[] getAuditInfo(Date paramDate1, Date paramDate2)
/* 2754:     */     throws java.rmi.RemoteException, AEException
/* 2755:     */   {
/* 2756:3146 */     for (int i = 1;; i++)
/* 2757:     */     {
/* 2758:3148 */       _Request local_Request = null;
/* 2759:     */       try
/* 2760:     */       {
/* 2761:3151 */         local_Request = __request("getAuditInfo");
/* 2762:3152 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2763:3153 */         local_Request.write_value(paramDate1, Date.class);
/* 2764:3154 */         local_Request.write_value(paramDate2, Date.class);
/* 2765:3155 */         local_Request.invoke();
/* 2766:3156 */         InputStream localInputStream = local_Request.getInputStream();
/* 2767:     */         IAEAuditInfo[] arrayOfIAEAuditInfo2;
/* 2768:3158 */         if (local_Request.isRMI()) {
/* 2769:3158 */           arrayOfIAEAuditInfo2 = (IAEAuditInfo[])local_Request.read_value(new IAEAuditInfo[0].getClass());
/* 2770:     */         } else {
/* 2771:3158 */           arrayOfIAEAuditInfo2 = IAEAuditInfoSeqHelper.read(localInputStream);
/* 2772:     */         }
/* 2773:3159 */         return arrayOfIAEAuditInfo2;
/* 2774:     */       }
/* 2775:     */       catch (TRANSIENT localTRANSIENT)
/* 2776:     */       {
/* 2777:3163 */         if (i == 10) {
/* 2778:3165 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2779:     */         }
/* 2780:     */       }
/* 2781:     */       catch (UserException localUserException)
/* 2782:     */       {
/* 2783:3170 */         if (local_Request.isRMI())
/* 2784:     */         {
/* 2785:3172 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2786:3174 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2787:     */           }
/* 2788:     */         }
/* 2789:3179 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2790:3181 */           throw AEExceptionHelper.read(localUserException.input);
/* 2791:     */         }
/* 2792:3184 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2793:     */       }
/* 2794:     */       catch (SystemException localSystemException)
/* 2795:     */       {
/* 2796:3188 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2797:     */       }
/* 2798:     */       finally
/* 2799:     */       {
/* 2800:3192 */         if (local_Request != null) {
/* 2801:3194 */           local_Request.close();
/* 2802:     */         }
/* 2803:     */       }
/* 2804:     */     }
/* 2805:     */   }
/* 2806:     */   
/* 2807:     */   public IAEAuditInfo[] getAppAuditInfo(String paramString, Date paramDate1, Date paramDate2)
/* 2808:     */     throws java.rmi.RemoteException, AEException
/* 2809:     */   {
/* 2810:3206 */     for (int i = 1;; i++)
/* 2811:     */     {
/* 2812:3208 */       _Request local_Request = null;
/* 2813:     */       try
/* 2814:     */       {
/* 2815:3211 */         local_Request = __request("getAppAuditInfo");
/* 2816:3212 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2817:3213 */         local_Request.write_string(paramString);
/* 2818:3214 */         local_Request.write_value(paramDate1, Date.class);
/* 2819:3215 */         local_Request.write_value(paramDate2, Date.class);
/* 2820:3216 */         local_Request.invoke();
/* 2821:3217 */         InputStream localInputStream = local_Request.getInputStream();
/* 2822:     */         IAEAuditInfo[] arrayOfIAEAuditInfo2;
/* 2823:3219 */         if (local_Request.isRMI()) {
/* 2824:3219 */           arrayOfIAEAuditInfo2 = (IAEAuditInfo[])local_Request.read_value(new IAEAuditInfo[0].getClass());
/* 2825:     */         } else {
/* 2826:3219 */           arrayOfIAEAuditInfo2 = IAEAuditInfoSeqHelper.read(localInputStream);
/* 2827:     */         }
/* 2828:3220 */         return arrayOfIAEAuditInfo2;
/* 2829:     */       }
/* 2830:     */       catch (TRANSIENT localTRANSIENT)
/* 2831:     */       {
/* 2832:3224 */         if (i == 10) {
/* 2833:3226 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2834:     */         }
/* 2835:     */       }
/* 2836:     */       catch (UserException localUserException)
/* 2837:     */       {
/* 2838:3231 */         if (local_Request.isRMI())
/* 2839:     */         {
/* 2840:3233 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2841:3235 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2842:     */           }
/* 2843:     */         }
/* 2844:3240 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2845:3242 */           throw AEExceptionHelper.read(localUserException.input);
/* 2846:     */         }
/* 2847:3245 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2848:     */       }
/* 2849:     */       catch (SystemException localSystemException)
/* 2850:     */       {
/* 2851:3249 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2852:     */       }
/* 2853:     */       finally
/* 2854:     */       {
/* 2855:3253 */         if (local_Request != null) {
/* 2856:3255 */           local_Request.close();
/* 2857:     */         }
/* 2858:     */       }
/* 2859:     */     }
/* 2860:     */   }
/* 2861:     */   
/* 2862:     */   public IAEAuditInfo[] getUserAuditInfo(String paramString1, String paramString2, Date paramDate1, Date paramDate2)
/* 2863:     */     throws java.rmi.RemoteException, AEException
/* 2864:     */   {
/* 2865:3268 */     for (int i = 1;; i++)
/* 2866:     */     {
/* 2867:3270 */       _Request local_Request = null;
/* 2868:     */       try
/* 2869:     */       {
/* 2870:3273 */         local_Request = __request("getUserAuditInfo__string__string__Date__Date", "getUserAuditInfo__CORBA_WStringValue__CORBA_WStringValue__org_omg_boxedIDL_java_util_Date__org_omg_boxedIDL_java_util_Date");
/* 2871:3274 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2872:3275 */         local_Request.write_string(paramString1);
/* 2873:3276 */         local_Request.write_string(paramString2);
/* 2874:3277 */         local_Request.write_value(paramDate1, Date.class);
/* 2875:3278 */         local_Request.write_value(paramDate2, Date.class);
/* 2876:3279 */         local_Request.invoke();
/* 2877:3280 */         InputStream localInputStream = local_Request.getInputStream();
/* 2878:     */         IAEAuditInfo[] arrayOfIAEAuditInfo2;
/* 2879:3282 */         if (local_Request.isRMI()) {
/* 2880:3282 */           arrayOfIAEAuditInfo2 = (IAEAuditInfo[])local_Request.read_value(new IAEAuditInfo[0].getClass());
/* 2881:     */         } else {
/* 2882:3282 */           arrayOfIAEAuditInfo2 = IAEAuditInfoSeqHelper.read(localInputStream);
/* 2883:     */         }
/* 2884:3283 */         return arrayOfIAEAuditInfo2;
/* 2885:     */       }
/* 2886:     */       catch (TRANSIENT localTRANSIENT)
/* 2887:     */       {
/* 2888:3287 */         if (i == 10) {
/* 2889:3289 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2890:     */         }
/* 2891:     */       }
/* 2892:     */       catch (UserException localUserException)
/* 2893:     */       {
/* 2894:3294 */         if (local_Request.isRMI())
/* 2895:     */         {
/* 2896:3296 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2897:3298 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2898:     */           }
/* 2899:     */         }
/* 2900:3303 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2901:3305 */           throw AEExceptionHelper.read(localUserException.input);
/* 2902:     */         }
/* 2903:3308 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2904:     */       }
/* 2905:     */       catch (SystemException localSystemException)
/* 2906:     */       {
/* 2907:3312 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2908:     */       }
/* 2909:     */       finally
/* 2910:     */       {
/* 2911:3316 */         if (local_Request != null) {
/* 2912:3318 */           local_Request.close();
/* 2913:     */         }
/* 2914:     */       }
/* 2915:     */     }
/* 2916:     */   }
/* 2917:     */   
/* 2918:     */   public IAEAuditInfo[] getUserAuditInfo(String paramString, Date paramDate1, Date paramDate2)
/* 2919:     */     throws java.rmi.RemoteException, AEException
/* 2920:     */   {
/* 2921:3330 */     for (int i = 1;; i++)
/* 2922:     */     {
/* 2923:3332 */       _Request local_Request = null;
/* 2924:     */       try
/* 2925:     */       {
/* 2926:3335 */         local_Request = __request("getUserAuditInfo__string__Date__Date", "getUserAuditInfo__CORBA_WStringValue__org_omg_boxedIDL_java_util_Date__org_omg_boxedIDL_java_util_Date");
/* 2927:3336 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2928:3337 */         local_Request.write_string(paramString);
/* 2929:3338 */         local_Request.write_value(paramDate1, Date.class);
/* 2930:3339 */         local_Request.write_value(paramDate2, Date.class);
/* 2931:3340 */         local_Request.invoke();
/* 2932:3341 */         InputStream localInputStream = local_Request.getInputStream();
/* 2933:     */         IAEAuditInfo[] arrayOfIAEAuditInfo2;
/* 2934:3343 */         if (local_Request.isRMI()) {
/* 2935:3343 */           arrayOfIAEAuditInfo2 = (IAEAuditInfo[])local_Request.read_value(new IAEAuditInfo[0].getClass());
/* 2936:     */         } else {
/* 2937:3343 */           arrayOfIAEAuditInfo2 = IAEAuditInfoSeqHelper.read(localInputStream);
/* 2938:     */         }
/* 2939:3344 */         return arrayOfIAEAuditInfo2;
/* 2940:     */       }
/* 2941:     */       catch (TRANSIENT localTRANSIENT)
/* 2942:     */       {
/* 2943:3348 */         if (i == 10) {
/* 2944:3350 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2945:     */         }
/* 2946:     */       }
/* 2947:     */       catch (UserException localUserException)
/* 2948:     */       {
/* 2949:3355 */         if (local_Request.isRMI())
/* 2950:     */         {
/* 2951:3357 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2952:3359 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2953:     */           }
/* 2954:     */         }
/* 2955:3364 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 2956:3366 */           throw AEExceptionHelper.read(localUserException.input);
/* 2957:     */         }
/* 2958:3369 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2959:     */       }
/* 2960:     */       catch (SystemException localSystemException)
/* 2961:     */       {
/* 2962:3373 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2963:     */       }
/* 2964:     */       finally
/* 2965:     */       {
/* 2966:3377 */         if (local_Request != null) {
/* 2967:3379 */           local_Request.close();
/* 2968:     */         }
/* 2969:     */       }
/* 2970:     */     }
/* 2971:     */   }
/* 2972:     */   
/* 2973:     */   public void setEngineProperties(Properties paramProperties)
/* 2974:     */     throws java.rmi.RemoteException, AEException
/* 2975:     */   {
/* 2976:3389 */     for (int i = 1;; i++)
/* 2977:     */     {
/* 2978:3391 */       _Request local_Request = null;
/* 2979:     */       try
/* 2980:     */       {
/* 2981:3394 */         local_Request = __request("setEngineProperties");
/* 2982:3395 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 2983:3396 */         local_Request.write_value(paramProperties, Properties.class);
/* 2984:3397 */         local_Request.invoke();
/* 2985:3398 */         return;
/* 2986:     */       }
/* 2987:     */       catch (TRANSIENT localTRANSIENT)
/* 2988:     */       {
/* 2989:3402 */         if (i == 10) {
/* 2990:3404 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2991:     */         }
/* 2992:     */       }
/* 2993:     */       catch (UserException localUserException)
/* 2994:     */       {
/* 2995:3409 */         if (local_Request.isRMI())
/* 2996:     */         {
/* 2997:3411 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 2998:3413 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 2999:     */           }
/* 3000:     */         }
/* 3001:3418 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 3002:3420 */           throw AEExceptionHelper.read(localUserException.input);
/* 3003:     */         }
/* 3004:3423 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3005:     */       }
/* 3006:     */       catch (SystemException localSystemException)
/* 3007:     */       {
/* 3008:3427 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3009:     */       }
/* 3010:     */       finally
/* 3011:     */       {
/* 3012:3431 */         if (local_Request != null) {
/* 3013:3433 */           local_Request.close();
/* 3014:     */         }
/* 3015:     */       }
/* 3016:     */     }
/* 3017:     */   }
/* 3018:     */   
/* 3019:     */   public Properties getEngineProperties()
/* 3020:     */     throws java.rmi.RemoteException, AEException
/* 3021:     */   {
/* 3022:3442 */     for (int i = 1;; i++)
/* 3023:     */     {
/* 3024:3444 */       _Request local_Request = null;
/* 3025:     */       try
/* 3026:     */       {
/* 3027:3447 */         local_Request = __request("getEngineProperties");
/* 3028:3448 */         local_Request.invoke();
/* 3029:3449 */         InputStream localInputStream = local_Request.getInputStream();
/* 3030:     */         
/* 3031:3451 */         Properties localProperties2 = (Properties)local_Request.read_value(Properties.class);
/* 3032:3452 */         return localProperties2;
/* 3033:     */       }
/* 3034:     */       catch (TRANSIENT localTRANSIENT)
/* 3035:     */       {
/* 3036:3456 */         if (i == 10) {
/* 3037:3458 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3038:     */         }
/* 3039:     */       }
/* 3040:     */       catch (UserException localUserException)
/* 3041:     */       {
/* 3042:3463 */         if (local_Request.isRMI())
/* 3043:     */         {
/* 3044:3465 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 3045:3467 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 3046:     */           }
/* 3047:     */         }
/* 3048:3472 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 3049:3474 */           throw AEExceptionHelper.read(localUserException.input);
/* 3050:     */         }
/* 3051:3477 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3052:     */       }
/* 3053:     */       catch (SystemException localSystemException)
/* 3054:     */       {
/* 3055:3481 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3056:     */       }
/* 3057:     */       finally
/* 3058:     */       {
/* 3059:3485 */         if (local_Request != null) {
/* 3060:3487 */           local_Request.close();
/* 3061:     */         }
/* 3062:     */       }
/* 3063:     */     }
/* 3064:     */   }
/* 3065:     */   
/* 3066:     */   public AEServerInfo updateServer(String paramString, AEServerInfo paramAEServerInfo)
/* 3067:     */     throws java.rmi.RemoteException, AEException
/* 3068:     */   {
/* 3069:3498 */     for (int i = 1;; i++)
/* 3070:     */     {
/* 3071:3500 */       _Request local_Request = null;
/* 3072:     */       try
/* 3073:     */       {
/* 3074:3503 */         local_Request = __request("updateServer");
/* 3075:3504 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 3076:3505 */         local_Request.write_string(paramString);
/* 3077:3506 */         local_Request.write_value(paramAEServerInfo, AEServerInfo.class);
/* 3078:3507 */         local_Request.invoke();
/* 3079:3508 */         InputStream localInputStream = local_Request.getInputStream();
/* 3080:     */         
/* 3081:3510 */         AEServerInfo localAEServerInfo2 = (AEServerInfo)local_Request.read_value(AEServerInfo.class);
/* 3082:3511 */         return localAEServerInfo2;
/* 3083:     */       }
/* 3084:     */       catch (TRANSIENT localTRANSIENT)
/* 3085:     */       {
/* 3086:3515 */         if (i == 10) {
/* 3087:3517 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3088:     */         }
/* 3089:     */       }
/* 3090:     */       catch (UserException localUserException)
/* 3091:     */       {
/* 3092:3522 */         if (local_Request.isRMI())
/* 3093:     */         {
/* 3094:3524 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 3095:3526 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 3096:     */           }
/* 3097:     */         }
/* 3098:3531 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 3099:3533 */           throw AEExceptionHelper.read(localUserException.input);
/* 3100:     */         }
/* 3101:3536 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3102:     */       }
/* 3103:     */       catch (SystemException localSystemException)
/* 3104:     */       {
/* 3105:3540 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3106:     */       }
/* 3107:     */       finally
/* 3108:     */       {
/* 3109:3544 */         if (local_Request != null) {
/* 3110:3546 */           local_Request.close();
/* 3111:     */         }
/* 3112:     */       }
/* 3113:     */     }
/* 3114:     */   }
/* 3115:     */   
/* 3116:     */   public void setPrimaryServer(String paramString)
/* 3117:     */     throws java.rmi.RemoteException, AEException
/* 3118:     */   {
/* 3119:3556 */     for (int i = 1;; i++)
/* 3120:     */     {
/* 3121:3558 */       _Request local_Request = null;
/* 3122:     */       try
/* 3123:     */       {
/* 3124:3561 */         local_Request = __request("setPrimaryServer");
/* 3125:3562 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 3126:3563 */         local_Request.write_string(paramString);
/* 3127:3564 */         local_Request.invoke();
/* 3128:3565 */         return;
/* 3129:     */       }
/* 3130:     */       catch (TRANSIENT localTRANSIENT)
/* 3131:     */       {
/* 3132:3569 */         if (i == 10) {
/* 3133:3571 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3134:     */         }
/* 3135:     */       }
/* 3136:     */       catch (UserException localUserException)
/* 3137:     */       {
/* 3138:3576 */         if (local_Request.isRMI())
/* 3139:     */         {
/* 3140:3578 */           if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 3141:3580 */             throw ((AEException)local_Request.read_value(AEException.class));
/* 3142:     */           }
/* 3143:     */         }
/* 3144:3585 */         else if (localUserException.type.equals("IDL:com/ffusion/alert/interfaces/AEException:1.0")) {
/* 3145:3587 */           throw AEExceptionHelper.read(localUserException.input);
/* 3146:     */         }
/* 3147:3590 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3148:     */       }
/* 3149:     */       catch (SystemException localSystemException)
/* 3150:     */       {
/* 3151:3594 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3152:     */       }
/* 3153:     */       finally
/* 3154:     */       {
/* 3155:3598 */         if (local_Request != null) {
/* 3156:3600 */           local_Request.close();
/* 3157:     */         }
/* 3158:     */       }
/* 3159:     */     }
/* 3160:     */   }
/* 3161:     */   
/* 3162:     */   public IAEAlertAdmin_Stub() {}
/* 3163:     */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.IAEAlertAdmin_Stub
 * JD-Core Version:    0.7.0.1
 */