/*    1:     */ package com.ffusion.alert.adminEJB;
/*    2:     */ 
/*    3:     */ import BCD.BinaryHelper;
/*    4:     */ import CtsComponents.ObjectContextHelper;
/*    5:     */ import com.ffusion.alert.interfaces.AEApplicationInfo;
/*    6:     */ import com.ffusion.alert.interfaces.AEApplicationInfoSeqHelper;
/*    7:     */ import com.ffusion.alert.interfaces.AEDBParams;
/*    8:     */ import com.ffusion.alert.interfaces.AEException;
/*    9:     */ import com.ffusion.alert.interfaces.AEExceptionHelper;
/*   10:     */ import com.ffusion.alert.interfaces.AEScheduleInfo;
/*   11:     */ import com.ffusion.alert.interfaces.AEServerInfo;
/*   12:     */ import com.ffusion.alert.interfaces.AEServerInfoSeqHelper;
/*   13:     */ import com.ffusion.alert.interfaces.IAEAlertDefinition;
/*   14:     */ import com.ffusion.alert.interfaces.IAEAlertDefinitionSeqHelper;
/*   15:     */ import com.ffusion.alert.interfaces.IAEAuditInfo;
/*   16:     */ import com.ffusion.alert.interfaces.IAEAuditInfoSeqHelper;
/*   17:     */ import com.ffusion.alert.interfaces.IAEChannelInfo;
/*   18:     */ import com.ffusion.alert.interfaces.IAEChannelInfoSeqHelper;
/*   19:     */ import com.ffusion.alert.interfaces.IAEDeliveryInfo;
/*   20:     */ import com.ffusion.alert.interfaces.IAEDeliveryInfoSeqHelper;
/*   21:     */ import com.sybase.CORBA.UserException;
/*   22:     */ import com.sybase.CORBA._ServerRequest;
/*   23:     */ import com.sybase.CORBA.iiop.Connection;
/*   24:     */ import com.sybase.ejb.SessionContext;
/*   25:     */ import com.sybase.jaguar.server.Jaguar;
/*   26:     */ import java.rmi.RemoteException;
/*   27:     */ import java.util.Date;
/*   28:     */ import java.util.HashMap;
/*   29:     */ import java.util.Properties;
/*   30:     */ import org.omg.CORBA.portable.InputStream;
/*   31:     */ import org.omg.CORBA.portable.OutputStream;
/*   32:     */ 
/*   33:     */ public abstract class _sk_UAEAdminEJB20_UAEAdminBean
/*   34:     */ {
/*   35:  17 */   private static HashMap _methods = new HashMap(138);
/*   36:     */   private static final String _RESET = "org.omg.CORBA.BAD_OPERATION";
/*   37:     */   
/*   38:     */   static
/*   39:     */   {
/*   40:  18 */     _methods.put("setSessionContext", new Integer(0));
/*   41:  19 */     _methods.put("ejbCreate", new Integer(1));
/*   42:  20 */     _methods.put("ejbActivate", new Integer(2));
/*   43:  21 */     _methods.put("ejbPassivate", new Integer(3));
/*   44:  22 */     _methods.put("ejbRemove", new Integer(4));
/*   45:  23 */     _methods.put("shutdown__", new Integer(5));
/*   46:  24 */     _methods.put("shutdown__", new Integer(5));
/*   47:  25 */     _methods.put("shutdown__string", new Integer(6));
/*   48:  26 */     _methods.put("shutdown__CORBA_WStringValue", new Integer(6));
/*   49:  27 */     _methods.put("init__AEDBParams", new Integer(7));
/*   50:  28 */     _methods.put("init__org_omg_boxedIDL_com_ffusion_alert_interfaces_AEDBParams", new Integer(7));
/*   51:  29 */     _methods.put("init__string", new Integer(8));
/*   52:  30 */     _methods.put("init__CORBA_WStringValue", new Integer(8));
/*   53:  31 */     _methods.put("start__", new Integer(9));
/*   54:  32 */     _methods.put("start__", new Integer(9));
/*   55:  33 */     _methods.put("start__string", new Integer(10));
/*   56:  34 */     _methods.put("start__CORBA_WStringValue", new Integer(10));
/*   57:  35 */     _methods.put("resume__", new Integer(11));
/*   58:  36 */     _methods.put("resume__", new Integer(11));
/*   59:  37 */     _methods.put("resume__string", new Integer(12));
/*   60:  38 */     _methods.put("resume__CORBA_WStringValue", new Integer(12));
/*   61:  39 */     _methods.put("stop__string", new Integer(13));
/*   62:  40 */     _methods.put("stop__CORBA_WStringValue", new Integer(13));
/*   63:  41 */     _methods.put("stop__", new Integer(14));
/*   64:  42 */     _methods.put("stop__", new Integer(14));
/*   65:  43 */     _methods.put("suspend__", new Integer(15));
/*   66:  44 */     _methods.put("suspend__", new Integer(15));
/*   67:  45 */     _methods.put("suspend__string", new Integer(16));
/*   68:  46 */     _methods.put("suspend__CORBA_WStringValue", new Integer(16));
/*   69:  47 */     _methods.put("addApplication", new Integer(17));
/*   70:  48 */     _methods.put("getServers", new Integer(18));
/*   71:  49 */     _methods.put("removeApplication", new Integer(19));
/*   72:  50 */     _methods.put("addServer", new Integer(20));
/*   73:  51 */     _methods.put("getApplications", new Integer(21));
/*   74:  52 */     _methods.put("removeServer", new Integer(22));
/*   75:  53 */     _methods.put("isInitialized__", new Integer(23));
/*   76:  54 */     _methods.put("isInitialized__", new Integer(23));
/*   77:  55 */     _methods.put("isInitialized__string", new Integer(24));
/*   78:  56 */     _methods.put("isInitialized__CORBA_WStringValue", new Integer(24));
/*   79:  57 */     _methods.put("destroyRepository", new Integer(25));
/*   80:  58 */     _methods.put("checkRepository", new Integer(26));
/*   81:  59 */     _methods.put("createRepository", new Integer(27));
/*   82:  60 */     _methods.put("initServerAll", new Integer(28));
/*   83:  61 */     _methods.put("startServerNamed", new Integer(29));
/*   84:  62 */     _methods.put("startServerAll", new Integer(30));
/*   85:  63 */     _methods.put("stopServerAll", new Integer(31));
/*   86:  64 */     _methods.put("suspendServerAll", new Integer(32));
/*   87:  65 */     _methods.put("resumeServerAll", new Integer(33));
/*   88:  66 */     _methods.put("shutdownServerAll", new Integer(34));
/*   89:  67 */     _methods.put("isSuspended__", new Integer(35));
/*   90:  68 */     _methods.put("isSuspended__", new Integer(35));
/*   91:  69 */     _methods.put("isSuspended__string", new Integer(36));
/*   92:  70 */     _methods.put("isSuspended__CORBA_WStringValue", new Integer(36));
/*   93:  71 */     _methods.put("isRunning__string", new Integer(37));
/*   94:  72 */     _methods.put("isRunning__CORBA_WStringValue", new Integer(37));
/*   95:  73 */     _methods.put("isRunning__", new Integer(38));
/*   96:  74 */     _methods.put("isRunning__", new Integer(38));
/*   97:  75 */     _methods.put("isClusterStopped", new Integer(39));
/*   98:  76 */     _methods.put("isClusterRunning", new Integer(40));
/*   99:  77 */     _methods.put("updateApplication", new Integer(41));
/*  100:  78 */     _methods.put("installDeliveryChannel__string__string__Properties__long__string", new Integer(42));
/*  101:  79 */     _methods.put("installDeliveryChannel__CORBA_WStringValue__CORBA_WStringValue__org_omg_boxedIDL_java_util_Properties__long__CORBA_WStringValue", new Integer(42));
/*  102:  80 */     _methods.put("installDeliveryChannel__string__string__Properties__long", new Integer(43));
/*  103:  81 */     _methods.put("installDeliveryChannel__CORBA_WStringValue__CORBA_WStringValue__org_omg_boxedIDL_java_util_Properties__long", new Integer(43));
/*  104:  82 */     _methods.put("uninstallDeliveryChannel", new Integer(44));
/*  105:  83 */     _methods.put("loadDeliveryChannel", new Integer(45));
/*  106:  84 */     _methods.put("unloadDeliveryChannel", new Integer(46));
/*  107:  85 */     _methods.put("updateDeliveryChannel", new Integer(47));
/*  108:  86 */     _methods.put("getInstalledDeliveryChannels", new Integer(48));
/*  109:  87 */     _methods.put("getLoadedDeliveryChannels", new Integer(49));
/*  110:  88 */     _methods.put("getAllAlerts", new Integer(50));
/*  111:  89 */     _methods.put("getAppAlerts", new Integer(51));
/*  112:  90 */     _methods.put("getAppAlertsForChannelPaged", new Integer(52));
/*  113:  91 */     _methods.put("getUserAlerts__string__boolean", new Integer(53));
/*  114:  92 */     _methods.put("getUserAlerts__CORBA_WStringValue__boolean", new Integer(53));
/*  115:  93 */     _methods.put("getUserAlerts__string__string__boolean", new Integer(54));
/*  116:  94 */     _methods.put("getUserAlerts__CORBA_WStringValue__CORBA_WStringValue__boolean", new Integer(54));
/*  117:  95 */     _methods.put("getAlert", new Integer(55));
/*  118:  96 */     _methods.put("getAlerts", new Integer(56));
/*  119:  97 */     _methods.put("updateAlert__long__string__long__long__AEScheduleInfo__IAEDeliveryInfoSeq__string", new Integer(57));
/*  120:  98 */     _methods.put("updateAlert__long__CORBA_WStringValue__long__long__org_omg_boxedIDL_com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__CORBA_WStringValue", new Integer(57));
/*  121:  99 */     _methods.put("updateAlert__long__string__long__long__AEScheduleInfo__IAEDeliveryInfoSeq__BCD_Binary", new Integer(58));
/*  122: 100 */     _methods.put("updateAlert__long__CORBA_WStringValue__long__long__org_omg_boxedIDL_com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__org_omg_boxedIDL_byte[]", new Integer(58));
/*  123: 101 */     _methods.put("cancelAlert", new Integer(59));
/*  124: 102 */     _methods.put("cancelUserAlerts", new Integer(60));
/*  125: 103 */     _methods.put("getAuditInfo", new Integer(61));
/*  126: 104 */     _methods.put("getAppAuditInfo", new Integer(62));
/*  127: 105 */     _methods.put("getUserAuditInfo__string__string__Date__Date", new Integer(63));
/*  128: 106 */     _methods.put("getUserAuditInfo__CORBA_WStringValue__CORBA_WStringValue__org_omg_boxedIDL_java_util_Date__org_omg_boxedIDL_java_util_Date", new Integer(63));
/*  129: 107 */     _methods.put("getUserAuditInfo__string__Date__Date", new Integer(64));
/*  130: 108 */     _methods.put("getUserAuditInfo__CORBA_WStringValue__org_omg_boxedIDL_java_util_Date__org_omg_boxedIDL_java_util_Date", new Integer(64));
/*  131: 109 */     _methods.put("setEngineProperties", new Integer(65));
/*  132: 110 */     _methods.put("getEngineProperties", new Integer(66));
/*  133: 111 */     _methods.put("updateServer", new Integer(67));
/*  134: 112 */     _methods.put("setPrimaryServer", new Integer(68));
/*  135:     */   }
/*  136:     */   
/*  137:     */   public static Object create()
/*  138:     */     throws Exception
/*  139:     */   {
/*  140: 120 */     return new AEAlertAdminBean();
/*  141:     */   }
/*  142:     */   
/*  143:     */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream)
/*  144:     */   {
/*  145: 129 */     return invoke(paramObject, paramString, paramInputStream, paramOutputStream, 0);
/*  146:     */   }
/*  147:     */   
/*  148:     */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  149:     */   {
/*  150: 139 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/*  151: 140 */     AEAlertAdminBean localAEAlertAdminBean = (AEAlertAdminBean)paramObject;
/*  152: 141 */     Integer localInteger = (Integer)_methods.get(paramString);
/*  153: 142 */     if (localInteger == null) {
/*  154: 144 */       return "org.omg.CORBA.BAD_OPERATION";
/*  155:     */     }
/*  156:     */     Object localObject1;
/*  157:     */     RemoteException localRemoteException1;
/*  158:     */     RemoteException localRemoteException2;
/*  159:     */     Object localObject2;
/*  160:     */     Properties localProperties;
/*  161:     */     int i;
/*  162:     */     Object localObject3;
/*  163: 146 */     switch (localInteger.intValue())
/*  164:     */     {
/*  165:     */     case 0: 
/*  166:     */       try
/*  167:     */       {
/*  168: 153 */         SessionContext localSessionContext = new SessionContext(ObjectContextHelper.read(paramInputStream));
/*  169: 154 */         localAEAlertAdminBean
/*  170: 155 */           .setSessionContext(
/*  171: 156 */           localSessionContext);
/*  172:     */       }
/*  173:     */       catch (Throwable localThrowable1)
/*  174:     */       {
/*  175: 161 */         localThrowable1.printStackTrace(Jaguar.log);
/*  176: 162 */         localObject1 = null;
/*  177: 163 */         localObject1 = new RemoteException(localThrowable1.getMessage());
/*  178: 164 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  179: 165 */         return localThrowable1.getClass().getName();
/*  180:     */       }
/*  181:     */     case 1: 
/*  182:     */       try
/*  183:     */       {
/*  184: 174 */         localAEAlertAdminBean.ejbCreate();
/*  185:     */       }
/*  186:     */       catch (Throwable localThrowable2)
/*  187:     */       {
/*  188: 179 */         localThrowable2.printStackTrace(Jaguar.log);
/*  189: 180 */         localObject1 = null;
/*  190: 181 */         localObject1 = new RemoteException(localThrowable2.getMessage());
/*  191: 182 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  192: 183 */         return localThrowable2.getClass().getName();
/*  193:     */       }
/*  194:     */     case 2: 
/*  195:     */       try
/*  196:     */       {
/*  197: 192 */         localAEAlertAdminBean.ejbActivate();
/*  198:     */       }
/*  199:     */       catch (Throwable localThrowable3)
/*  200:     */       {
/*  201: 197 */         localThrowable3.printStackTrace(Jaguar.log);
/*  202: 198 */         localObject1 = null;
/*  203: 199 */         localObject1 = new RemoteException(localThrowable3.getMessage());
/*  204: 200 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  205: 201 */         return localThrowable3.getClass().getName();
/*  206:     */       }
/*  207:     */     case 3: 
/*  208:     */       try
/*  209:     */       {
/*  210: 210 */         localAEAlertAdminBean.ejbPassivate();
/*  211:     */       }
/*  212:     */       catch (Throwable localThrowable4)
/*  213:     */       {
/*  214: 215 */         localThrowable4.printStackTrace(Jaguar.log);
/*  215: 216 */         localObject1 = null;
/*  216: 217 */         localObject1 = new RemoteException(localThrowable4.getMessage());
/*  217: 218 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  218: 219 */         return localThrowable4.getClass().getName();
/*  219:     */       }
/*  220:     */     case 4: 
/*  221:     */       try
/*  222:     */       {
/*  223: 228 */         localAEAlertAdminBean.ejbRemove();
/*  224:     */       }
/*  225:     */       catch (Throwable localThrowable5)
/*  226:     */       {
/*  227: 233 */         localThrowable5.printStackTrace(Jaguar.log);
/*  228: 234 */         localObject1 = null;
/*  229: 235 */         localObject1 = new RemoteException(localThrowable5.getMessage());
/*  230: 236 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  231: 237 */         return localThrowable5.getClass().getName();
/*  232:     */       }
/*  233:     */     case 5: 
/*  234:     */       try
/*  235:     */       {
/*  236: 246 */         localAEAlertAdminBean.shutdown();
/*  237:     */       }
/*  238:     */       catch (Throwable localThrowable6)
/*  239:     */       {
/*  240: 251 */         if ((localThrowable6 instanceof AEException))
/*  241:     */         {
/*  242: 253 */           if (UserException.ok(paramOutputStream)) {
/*  243: 255 */             if (local_ServerRequest.isRMI())
/*  244:     */             {
/*  245: 257 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  246: 258 */               local_ServerRequest.write_value((AEException)localThrowable6, AEException.class);
/*  247:     */             }
/*  248:     */             else
/*  249:     */             {
/*  250: 262 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  251: 263 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable6);
/*  252:     */             }
/*  253:     */           }
/*  254: 266 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable6);
/*  255:     */         }
/*  256: 268 */         localThrowable6.printStackTrace(Jaguar.log);
/*  257: 269 */         localObject1 = null;
/*  258: 270 */         localObject1 = new RemoteException(localThrowable6.getMessage());
/*  259: 271 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  260: 272 */         return localThrowable6.getClass().getName();
/*  261:     */       }
/*  262:     */     case 6: 
/*  263:     */       try
/*  264:     */       {
/*  265: 281 */         String str1 = local_ServerRequest.read_string();
/*  266: 282 */         localAEAlertAdminBean
/*  267: 283 */           .shutdown(
/*  268: 284 */           str1);
/*  269:     */       }
/*  270:     */       catch (Throwable localThrowable7)
/*  271:     */       {
/*  272: 289 */         if ((localThrowable7 instanceof AEException))
/*  273:     */         {
/*  274: 291 */           if (UserException.ok(paramOutputStream)) {
/*  275: 293 */             if (local_ServerRequest.isRMI())
/*  276:     */             {
/*  277: 295 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  278: 296 */               local_ServerRequest.write_value((AEException)localThrowable7, AEException.class);
/*  279:     */             }
/*  280:     */             else
/*  281:     */             {
/*  282: 300 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  283: 301 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable7);
/*  284:     */             }
/*  285:     */           }
/*  286: 304 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable7);
/*  287:     */         }
/*  288: 306 */         localThrowable7.printStackTrace(Jaguar.log);
/*  289: 307 */         localObject1 = null;
/*  290: 308 */         localObject1 = new RemoteException(localThrowable7.getMessage());
/*  291: 309 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  292: 310 */         return localThrowable7.getClass().getName();
/*  293:     */       }
/*  294:     */     case 7: 
/*  295:     */       try
/*  296:     */       {
/*  297: 319 */         AEDBParams localAEDBParams1 = (AEDBParams)local_ServerRequest.read_value(AEDBParams.class);
/*  298: 320 */         localAEAlertAdminBean
/*  299: 321 */           .init(
/*  300: 322 */           localAEDBParams1);
/*  301:     */       }
/*  302:     */       catch (Throwable localThrowable8)
/*  303:     */       {
/*  304: 327 */         if ((localThrowable8 instanceof AEException))
/*  305:     */         {
/*  306: 329 */           if (UserException.ok(paramOutputStream)) {
/*  307: 331 */             if (local_ServerRequest.isRMI())
/*  308:     */             {
/*  309: 333 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  310: 334 */               local_ServerRequest.write_value((AEException)localThrowable8, AEException.class);
/*  311:     */             }
/*  312:     */             else
/*  313:     */             {
/*  314: 338 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  315: 339 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable8);
/*  316:     */             }
/*  317:     */           }
/*  318: 342 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable8);
/*  319:     */         }
/*  320: 344 */         localThrowable8.printStackTrace(Jaguar.log);
/*  321: 345 */         localObject1 = null;
/*  322: 346 */         localObject1 = new RemoteException(localThrowable8.getMessage());
/*  323: 347 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  324: 348 */         return localThrowable8.getClass().getName();
/*  325:     */       }
/*  326:     */     case 8: 
/*  327:     */       try
/*  328:     */       {
/*  329: 357 */         String str2 = local_ServerRequest.read_string();
/*  330: 358 */         localAEAlertAdminBean
/*  331: 359 */           .init(
/*  332: 360 */           str2);
/*  333:     */       }
/*  334:     */       catch (Throwable localThrowable9)
/*  335:     */       {
/*  336: 365 */         if ((localThrowable9 instanceof AEException))
/*  337:     */         {
/*  338: 367 */           if (UserException.ok(paramOutputStream)) {
/*  339: 369 */             if (local_ServerRequest.isRMI())
/*  340:     */             {
/*  341: 371 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  342: 372 */               local_ServerRequest.write_value((AEException)localThrowable9, AEException.class);
/*  343:     */             }
/*  344:     */             else
/*  345:     */             {
/*  346: 376 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  347: 377 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable9);
/*  348:     */             }
/*  349:     */           }
/*  350: 380 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable9);
/*  351:     */         }
/*  352: 382 */         localThrowable9.printStackTrace(Jaguar.log);
/*  353: 383 */         localObject1 = null;
/*  354: 384 */         localObject1 = new RemoteException(localThrowable9.getMessage());
/*  355: 385 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  356: 386 */         return localThrowable9.getClass().getName();
/*  357:     */       }
/*  358:     */     case 9: 
/*  359:     */       try
/*  360:     */       {
/*  361: 395 */         localAEAlertAdminBean.start();
/*  362:     */       }
/*  363:     */       catch (Throwable localThrowable10)
/*  364:     */       {
/*  365: 400 */         if ((localThrowable10 instanceof AEException))
/*  366:     */         {
/*  367: 402 */           if (UserException.ok(paramOutputStream)) {
/*  368: 404 */             if (local_ServerRequest.isRMI())
/*  369:     */             {
/*  370: 406 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  371: 407 */               local_ServerRequest.write_value((AEException)localThrowable10, AEException.class);
/*  372:     */             }
/*  373:     */             else
/*  374:     */             {
/*  375: 411 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  376: 412 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable10);
/*  377:     */             }
/*  378:     */           }
/*  379: 415 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable10);
/*  380:     */         }
/*  381: 417 */         localThrowable10.printStackTrace(Jaguar.log);
/*  382: 418 */         localObject1 = null;
/*  383: 419 */         localObject1 = new RemoteException(localThrowable10.getMessage());
/*  384: 420 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  385: 421 */         return localThrowable10.getClass().getName();
/*  386:     */       }
/*  387:     */     case 10: 
/*  388:     */       try
/*  389:     */       {
/*  390: 430 */         String str3 = local_ServerRequest.read_string();
/*  391: 431 */         localAEAlertAdminBean
/*  392: 432 */           .start(
/*  393: 433 */           str3);
/*  394:     */       }
/*  395:     */       catch (Throwable localThrowable11)
/*  396:     */       {
/*  397: 438 */         if ((localThrowable11 instanceof AEException))
/*  398:     */         {
/*  399: 440 */           if (UserException.ok(paramOutputStream)) {
/*  400: 442 */             if (local_ServerRequest.isRMI())
/*  401:     */             {
/*  402: 444 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  403: 445 */               local_ServerRequest.write_value((AEException)localThrowable11, AEException.class);
/*  404:     */             }
/*  405:     */             else
/*  406:     */             {
/*  407: 449 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  408: 450 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable11);
/*  409:     */             }
/*  410:     */           }
/*  411: 453 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable11);
/*  412:     */         }
/*  413: 455 */         localThrowable11.printStackTrace(Jaguar.log);
/*  414: 456 */         localObject1 = null;
/*  415: 457 */         localObject1 = new RemoteException(localThrowable11.getMessage());
/*  416: 458 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  417: 459 */         return localThrowable11.getClass().getName();
/*  418:     */       }
/*  419:     */     case 11: 
/*  420:     */       try
/*  421:     */       {
/*  422: 468 */         localAEAlertAdminBean.resume();
/*  423:     */       }
/*  424:     */       catch (Throwable localThrowable12)
/*  425:     */       {
/*  426: 473 */         if ((localThrowable12 instanceof AEException))
/*  427:     */         {
/*  428: 475 */           if (UserException.ok(paramOutputStream)) {
/*  429: 477 */             if (local_ServerRequest.isRMI())
/*  430:     */             {
/*  431: 479 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  432: 480 */               local_ServerRequest.write_value((AEException)localThrowable12, AEException.class);
/*  433:     */             }
/*  434:     */             else
/*  435:     */             {
/*  436: 484 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  437: 485 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable12);
/*  438:     */             }
/*  439:     */           }
/*  440: 488 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable12);
/*  441:     */         }
/*  442: 490 */         localThrowable12.printStackTrace(Jaguar.log);
/*  443: 491 */         localObject1 = null;
/*  444: 492 */         localObject1 = new RemoteException(localThrowable12.getMessage());
/*  445: 493 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  446: 494 */         return localThrowable12.getClass().getName();
/*  447:     */       }
/*  448:     */     case 12: 
/*  449:     */       try
/*  450:     */       {
/*  451: 503 */         String str4 = local_ServerRequest.read_string();
/*  452: 504 */         localAEAlertAdminBean
/*  453: 505 */           .resume(
/*  454: 506 */           str4);
/*  455:     */       }
/*  456:     */       catch (Throwable localThrowable13)
/*  457:     */       {
/*  458: 511 */         if ((localThrowable13 instanceof AEException))
/*  459:     */         {
/*  460: 513 */           if (UserException.ok(paramOutputStream)) {
/*  461: 515 */             if (local_ServerRequest.isRMI())
/*  462:     */             {
/*  463: 517 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  464: 518 */               local_ServerRequest.write_value((AEException)localThrowable13, AEException.class);
/*  465:     */             }
/*  466:     */             else
/*  467:     */             {
/*  468: 522 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  469: 523 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable13);
/*  470:     */             }
/*  471:     */           }
/*  472: 526 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable13);
/*  473:     */         }
/*  474: 528 */         localThrowable13.printStackTrace(Jaguar.log);
/*  475: 529 */         localObject1 = null;
/*  476: 530 */         localObject1 = new RemoteException(localThrowable13.getMessage());
/*  477: 531 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  478: 532 */         return localThrowable13.getClass().getName();
/*  479:     */       }
/*  480:     */     case 13: 
/*  481:     */       try
/*  482:     */       {
/*  483: 541 */         String str5 = local_ServerRequest.read_string();
/*  484: 542 */         localAEAlertAdminBean
/*  485: 543 */           .stop(
/*  486: 544 */           str5);
/*  487:     */       }
/*  488:     */       catch (Throwable localThrowable14)
/*  489:     */       {
/*  490: 549 */         if ((localThrowable14 instanceof AEException))
/*  491:     */         {
/*  492: 551 */           if (UserException.ok(paramOutputStream)) {
/*  493: 553 */             if (local_ServerRequest.isRMI())
/*  494:     */             {
/*  495: 555 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  496: 556 */               local_ServerRequest.write_value((AEException)localThrowable14, AEException.class);
/*  497:     */             }
/*  498:     */             else
/*  499:     */             {
/*  500: 560 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  501: 561 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable14);
/*  502:     */             }
/*  503:     */           }
/*  504: 564 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable14);
/*  505:     */         }
/*  506: 566 */         localThrowable14.printStackTrace(Jaguar.log);
/*  507: 567 */         localObject1 = null;
/*  508: 568 */         localObject1 = new RemoteException(localThrowable14.getMessage());
/*  509: 569 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  510: 570 */         return localThrowable14.getClass().getName();
/*  511:     */       }
/*  512:     */     case 14: 
/*  513:     */       try
/*  514:     */       {
/*  515: 579 */         localAEAlertAdminBean.stop();
/*  516:     */       }
/*  517:     */       catch (Throwable localThrowable15)
/*  518:     */       {
/*  519: 584 */         if ((localThrowable15 instanceof AEException))
/*  520:     */         {
/*  521: 586 */           if (UserException.ok(paramOutputStream)) {
/*  522: 588 */             if (local_ServerRequest.isRMI())
/*  523:     */             {
/*  524: 590 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  525: 591 */               local_ServerRequest.write_value((AEException)localThrowable15, AEException.class);
/*  526:     */             }
/*  527:     */             else
/*  528:     */             {
/*  529: 595 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  530: 596 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable15);
/*  531:     */             }
/*  532:     */           }
/*  533: 599 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable15);
/*  534:     */         }
/*  535: 601 */         localThrowable15.printStackTrace(Jaguar.log);
/*  536: 602 */         localObject1 = null;
/*  537: 603 */         localObject1 = new RemoteException(localThrowable15.getMessage());
/*  538: 604 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  539: 605 */         return localThrowable15.getClass().getName();
/*  540:     */       }
/*  541:     */     case 15: 
/*  542:     */       try
/*  543:     */       {
/*  544: 614 */         localAEAlertAdminBean.suspend();
/*  545:     */       }
/*  546:     */       catch (Throwable localThrowable16)
/*  547:     */       {
/*  548: 619 */         if ((localThrowable16 instanceof AEException))
/*  549:     */         {
/*  550: 621 */           if (UserException.ok(paramOutputStream)) {
/*  551: 623 */             if (local_ServerRequest.isRMI())
/*  552:     */             {
/*  553: 625 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  554: 626 */               local_ServerRequest.write_value((AEException)localThrowable16, AEException.class);
/*  555:     */             }
/*  556:     */             else
/*  557:     */             {
/*  558: 630 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  559: 631 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable16);
/*  560:     */             }
/*  561:     */           }
/*  562: 634 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable16);
/*  563:     */         }
/*  564: 636 */         localThrowable16.printStackTrace(Jaguar.log);
/*  565: 637 */         localObject1 = null;
/*  566: 638 */         localObject1 = new RemoteException(localThrowable16.getMessage());
/*  567: 639 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  568: 640 */         return localThrowable16.getClass().getName();
/*  569:     */       }
/*  570:     */     case 16: 
/*  571:     */       try
/*  572:     */       {
/*  573: 649 */         String str6 = local_ServerRequest.read_string();
/*  574: 650 */         localAEAlertAdminBean
/*  575: 651 */           .suspend(
/*  576: 652 */           str6);
/*  577:     */       }
/*  578:     */       catch (Throwable localThrowable17)
/*  579:     */       {
/*  580: 657 */         if ((localThrowable17 instanceof AEException))
/*  581:     */         {
/*  582: 659 */           if (UserException.ok(paramOutputStream)) {
/*  583: 661 */             if (local_ServerRequest.isRMI())
/*  584:     */             {
/*  585: 663 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  586: 664 */               local_ServerRequest.write_value((AEException)localThrowable17, AEException.class);
/*  587:     */             }
/*  588:     */             else
/*  589:     */             {
/*  590: 668 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  591: 669 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable17);
/*  592:     */             }
/*  593:     */           }
/*  594: 672 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable17);
/*  595:     */         }
/*  596: 674 */         localThrowable17.printStackTrace(Jaguar.log);
/*  597: 675 */         localObject1 = null;
/*  598: 676 */         localObject1 = new RemoteException(localThrowable17.getMessage());
/*  599: 677 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  600: 678 */         return localThrowable17.getClass().getName();
/*  601:     */       }
/*  602:     */     case 17: 
/*  603:     */       try
/*  604:     */       {
/*  605: 687 */         AEApplicationInfo localAEApplicationInfo = (AEApplicationInfo)local_ServerRequest.read_value(AEApplicationInfo.class);
/*  606: 688 */         localAEAlertAdminBean
/*  607: 689 */           .addApplication(
/*  608: 690 */           localAEApplicationInfo);
/*  609:     */       }
/*  610:     */       catch (Throwable localThrowable18)
/*  611:     */       {
/*  612: 695 */         if ((localThrowable18 instanceof AEException))
/*  613:     */         {
/*  614: 697 */           if (UserException.ok(paramOutputStream)) {
/*  615: 699 */             if (local_ServerRequest.isRMI())
/*  616:     */             {
/*  617: 701 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  618: 702 */               local_ServerRequest.write_value((AEException)localThrowable18, AEException.class);
/*  619:     */             }
/*  620:     */             else
/*  621:     */             {
/*  622: 706 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  623: 707 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable18);
/*  624:     */             }
/*  625:     */           }
/*  626: 710 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable18);
/*  627:     */         }
/*  628: 712 */         localThrowable18.printStackTrace(Jaguar.log);
/*  629: 713 */         localObject1 = null;
/*  630: 714 */         localObject1 = new RemoteException(localThrowable18.getMessage());
/*  631: 715 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  632: 716 */         return localThrowable18.getClass().getName();
/*  633:     */       }
/*  634:     */     case 18: 
/*  635:     */       try
/*  636:     */       {
/*  637: 724 */         AEServerInfo[] arrayOfAEServerInfo = localAEAlertAdminBean
/*  638: 725 */           .getServers();
/*  639: 727 */         if (local_ServerRequest.isRMI()) {
/*  640: 727 */           local_ServerRequest.write_value(arrayOfAEServerInfo, new AEServerInfo[0].getClass());
/*  641:     */         } else {
/*  642: 727 */           AEServerInfoSeqHelper.write(paramOutputStream, arrayOfAEServerInfo);
/*  643:     */         }
/*  644:     */       }
/*  645:     */       catch (Throwable localThrowable19)
/*  646:     */       {
/*  647: 731 */         if ((localThrowable19 instanceof AEException))
/*  648:     */         {
/*  649: 733 */           if (UserException.ok(paramOutputStream)) {
/*  650: 735 */             if (local_ServerRequest.isRMI())
/*  651:     */             {
/*  652: 737 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  653: 738 */               local_ServerRequest.write_value((AEException)localThrowable19, AEException.class);
/*  654:     */             }
/*  655:     */             else
/*  656:     */             {
/*  657: 742 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  658: 743 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable19);
/*  659:     */             }
/*  660:     */           }
/*  661: 746 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable19);
/*  662:     */         }
/*  663: 748 */         localThrowable19.printStackTrace(Jaguar.log);
/*  664: 749 */         localObject1 = null;
/*  665: 750 */         localObject1 = new RemoteException(localThrowable19.getMessage());
/*  666: 751 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  667: 752 */         return localThrowable19.getClass().getName();
/*  668:     */       }
/*  669:     */     case 19: 
/*  670:     */       try
/*  671:     */       {
/*  672: 761 */         String str7 = local_ServerRequest.read_string();
/*  673: 762 */         localAEAlertAdminBean
/*  674: 763 */           .removeApplication(
/*  675: 764 */           str7);
/*  676:     */       }
/*  677:     */       catch (Throwable localThrowable20)
/*  678:     */       {
/*  679: 769 */         if ((localThrowable20 instanceof AEException))
/*  680:     */         {
/*  681: 771 */           if (UserException.ok(paramOutputStream)) {
/*  682: 773 */             if (local_ServerRequest.isRMI())
/*  683:     */             {
/*  684: 775 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  685: 776 */               local_ServerRequest.write_value((AEException)localThrowable20, AEException.class);
/*  686:     */             }
/*  687:     */             else
/*  688:     */             {
/*  689: 780 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  690: 781 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable20);
/*  691:     */             }
/*  692:     */           }
/*  693: 784 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable20);
/*  694:     */         }
/*  695: 786 */         localThrowable20.printStackTrace(Jaguar.log);
/*  696: 787 */         localObject1 = null;
/*  697: 788 */         localObject1 = new RemoteException(localThrowable20.getMessage());
/*  698: 789 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  699: 790 */         return localThrowable20.getClass().getName();
/*  700:     */       }
/*  701:     */     case 20: 
/*  702:     */       try
/*  703:     */       {
/*  704: 799 */         AEServerInfo localAEServerInfo = (AEServerInfo)local_ServerRequest.read_value(AEServerInfo.class);
/*  705: 800 */         localObject1 = localAEAlertAdminBean
/*  706: 801 */           .addServer(
/*  707: 802 */           localAEServerInfo);
/*  708:     */         
/*  709: 804 */         local_ServerRequest.write_value(localObject1, AEServerInfo.class);
/*  710:     */       }
/*  711:     */       catch (Throwable localThrowable21)
/*  712:     */       {
/*  713: 808 */         if ((localThrowable21 instanceof AEException))
/*  714:     */         {
/*  715: 810 */           if (UserException.ok(paramOutputStream)) {
/*  716: 812 */             if (local_ServerRequest.isRMI())
/*  717:     */             {
/*  718: 814 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  719: 815 */               local_ServerRequest.write_value((AEException)localThrowable21, AEException.class);
/*  720:     */             }
/*  721:     */             else
/*  722:     */             {
/*  723: 819 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  724: 820 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable21);
/*  725:     */             }
/*  726:     */           }
/*  727: 823 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable21);
/*  728:     */         }
/*  729: 825 */         localThrowable21.printStackTrace(Jaguar.log);
/*  730: 826 */         localObject1 = null;
/*  731: 827 */         localObject1 = new RemoteException(localThrowable21.getMessage());
/*  732: 828 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  733: 829 */         return localThrowable21.getClass().getName();
/*  734:     */       }
/*  735:     */     case 21: 
/*  736:     */       try
/*  737:     */       {
/*  738: 837 */         AEApplicationInfo[] arrayOfAEApplicationInfo = localAEAlertAdminBean
/*  739: 838 */           .getApplications();
/*  740: 840 */         if (local_ServerRequest.isRMI()) {
/*  741: 840 */           local_ServerRequest.write_value(arrayOfAEApplicationInfo, new AEApplicationInfo[0].getClass());
/*  742:     */         } else {
/*  743: 840 */           AEApplicationInfoSeqHelper.write(paramOutputStream, arrayOfAEApplicationInfo);
/*  744:     */         }
/*  745:     */       }
/*  746:     */       catch (Throwable localThrowable22)
/*  747:     */       {
/*  748: 844 */         if ((localThrowable22 instanceof AEException))
/*  749:     */         {
/*  750: 846 */           if (UserException.ok(paramOutputStream)) {
/*  751: 848 */             if (local_ServerRequest.isRMI())
/*  752:     */             {
/*  753: 850 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  754: 851 */               local_ServerRequest.write_value((AEException)localThrowable22, AEException.class);
/*  755:     */             }
/*  756:     */             else
/*  757:     */             {
/*  758: 855 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  759: 856 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable22);
/*  760:     */             }
/*  761:     */           }
/*  762: 859 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable22);
/*  763:     */         }
/*  764: 861 */         localThrowable22.printStackTrace(Jaguar.log);
/*  765: 862 */         localObject1 = null;
/*  766: 863 */         localObject1 = new RemoteException(localThrowable22.getMessage());
/*  767: 864 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  768: 865 */         return localThrowable22.getClass().getName();
/*  769:     */       }
/*  770:     */     case 22: 
/*  771:     */       try
/*  772:     */       {
/*  773: 874 */         String str8 = local_ServerRequest.read_string();
/*  774: 875 */         localAEAlertAdminBean
/*  775: 876 */           .removeServer(
/*  776: 877 */           str8);
/*  777:     */       }
/*  778:     */       catch (Throwable localThrowable23)
/*  779:     */       {
/*  780: 882 */         if ((localThrowable23 instanceof AEException))
/*  781:     */         {
/*  782: 884 */           if (UserException.ok(paramOutputStream)) {
/*  783: 886 */             if (local_ServerRequest.isRMI())
/*  784:     */             {
/*  785: 888 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  786: 889 */               local_ServerRequest.write_value((AEException)localThrowable23, AEException.class);
/*  787:     */             }
/*  788:     */             else
/*  789:     */             {
/*  790: 893 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  791: 894 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable23);
/*  792:     */             }
/*  793:     */           }
/*  794: 897 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable23);
/*  795:     */         }
/*  796: 899 */         localThrowable23.printStackTrace(Jaguar.log);
/*  797: 900 */         localObject1 = null;
/*  798: 901 */         localObject1 = new RemoteException(localThrowable23.getMessage());
/*  799: 902 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  800: 903 */         return localThrowable23.getClass().getName();
/*  801:     */       }
/*  802:     */     case 23: 
/*  803:     */       try
/*  804:     */       {
/*  805: 911 */         boolean bool1 = localAEAlertAdminBean
/*  806: 912 */           .isInitialized();
/*  807:     */         
/*  808: 914 */         paramOutputStream.write_boolean(bool1);
/*  809:     */       }
/*  810:     */       catch (Throwable localThrowable24)
/*  811:     */       {
/*  812: 918 */         if ((localThrowable24 instanceof AEException))
/*  813:     */         {
/*  814: 920 */           if (UserException.ok(paramOutputStream)) {
/*  815: 922 */             if (local_ServerRequest.isRMI())
/*  816:     */             {
/*  817: 924 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  818: 925 */               local_ServerRequest.write_value((AEException)localThrowable24, AEException.class);
/*  819:     */             }
/*  820:     */             else
/*  821:     */             {
/*  822: 929 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  823: 930 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable24);
/*  824:     */             }
/*  825:     */           }
/*  826: 933 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable24);
/*  827:     */         }
/*  828: 935 */         localThrowable24.printStackTrace(Jaguar.log);
/*  829: 936 */         localObject1 = null;
/*  830: 937 */         localObject1 = new RemoteException(localThrowable24.getMessage());
/*  831: 938 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/*  832: 939 */         return localThrowable24.getClass().getName();
/*  833:     */       }
/*  834:     */     case 24: 
/*  835:     */       try
/*  836:     */       {
/*  837: 948 */         String str9 = local_ServerRequest.read_string();
/*  838: 949 */         boolean bool7 = localAEAlertAdminBean
/*  839: 950 */           .isInitialized(
/*  840: 951 */           str9);
/*  841:     */         
/*  842: 953 */         paramOutputStream.write_boolean(bool7);
/*  843:     */       }
/*  844:     */       catch (Throwable localThrowable25)
/*  845:     */       {
/*  846: 957 */         if ((localThrowable25 instanceof AEException))
/*  847:     */         {
/*  848: 959 */           if (UserException.ok(paramOutputStream)) {
/*  849: 961 */             if (local_ServerRequest.isRMI())
/*  850:     */             {
/*  851: 963 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  852: 964 */               local_ServerRequest.write_value((AEException)localThrowable25, AEException.class);
/*  853:     */             }
/*  854:     */             else
/*  855:     */             {
/*  856: 968 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  857: 969 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable25);
/*  858:     */             }
/*  859:     */           }
/*  860: 972 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable25);
/*  861:     */         }
/*  862: 974 */         localThrowable25.printStackTrace(Jaguar.log);
/*  863: 975 */         localRemoteException1 = null;
/*  864: 976 */         localRemoteException1 = new RemoteException(localThrowable25.getMessage());
/*  865: 977 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException1);
/*  866: 978 */         return localThrowable25.getClass().getName();
/*  867:     */       }
/*  868:     */     case 25: 
/*  869:     */       try
/*  870:     */       {
/*  871: 987 */         AEDBParams localAEDBParams2 = (AEDBParams)local_ServerRequest.read_value(AEDBParams.class);
/*  872: 988 */         localAEAlertAdminBean
/*  873: 989 */           .destroyRepository(
/*  874: 990 */           localAEDBParams2);
/*  875:     */       }
/*  876:     */       catch (Throwable localThrowable26)
/*  877:     */       {
/*  878: 995 */         if ((localThrowable26 instanceof AEException))
/*  879:     */         {
/*  880: 997 */           if (UserException.ok(paramOutputStream)) {
/*  881: 999 */             if (local_ServerRequest.isRMI())
/*  882:     */             {
/*  883:1001 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  884:1002 */               local_ServerRequest.write_value((AEException)localThrowable26, AEException.class);
/*  885:     */             }
/*  886:     */             else
/*  887:     */             {
/*  888:1006 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  889:1007 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable26);
/*  890:     */             }
/*  891:     */           }
/*  892:1010 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable26);
/*  893:     */         }
/*  894:1012 */         localThrowable26.printStackTrace(Jaguar.log);
/*  895:1013 */         localRemoteException1 = null;
/*  896:1014 */         localRemoteException1 = new RemoteException(localThrowable26.getMessage());
/*  897:1015 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException1);
/*  898:1016 */         return localThrowable26.getClass().getName();
/*  899:     */       }
/*  900:     */     case 26: 
/*  901:     */       try
/*  902:     */       {
/*  903:1025 */         AEDBParams localAEDBParams3 = (AEDBParams)local_ServerRequest.read_value(AEDBParams.class);
/*  904:1026 */         localAEAlertAdminBean
/*  905:1027 */           .checkRepository(
/*  906:1028 */           localAEDBParams3);
/*  907:     */       }
/*  908:     */       catch (Throwable localThrowable27)
/*  909:     */       {
/*  910:1033 */         if ((localThrowable27 instanceof AEException))
/*  911:     */         {
/*  912:1035 */           if (UserException.ok(paramOutputStream)) {
/*  913:1037 */             if (local_ServerRequest.isRMI())
/*  914:     */             {
/*  915:1039 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  916:1040 */               local_ServerRequest.write_value((AEException)localThrowable27, AEException.class);
/*  917:     */             }
/*  918:     */             else
/*  919:     */             {
/*  920:1044 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  921:1045 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable27);
/*  922:     */             }
/*  923:     */           }
/*  924:1048 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable27);
/*  925:     */         }
/*  926:1050 */         localThrowable27.printStackTrace(Jaguar.log);
/*  927:1051 */         localRemoteException1 = null;
/*  928:1052 */         localRemoteException1 = new RemoteException(localThrowable27.getMessage());
/*  929:1053 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException1);
/*  930:1054 */         return localThrowable27.getClass().getName();
/*  931:     */       }
/*  932:     */     case 27: 
/*  933:     */       try
/*  934:     */       {
/*  935:1063 */         AEDBParams localAEDBParams4 = (AEDBParams)local_ServerRequest.read_value(AEDBParams.class);
/*  936:     */         
/*  937:1065 */         boolean bool8 = paramInputStream.read_boolean();
/*  938:1066 */         localAEAlertAdminBean
/*  939:1067 */           .createRepository(
/*  940:1068 */           localAEDBParams4, 
/*  941:1069 */           bool8);
/*  942:     */       }
/*  943:     */       catch (Throwable localThrowable28)
/*  944:     */       {
/*  945:1074 */         if ((localThrowable28 instanceof AEException))
/*  946:     */         {
/*  947:1076 */           if (UserException.ok(paramOutputStream)) {
/*  948:1078 */             if (local_ServerRequest.isRMI())
/*  949:     */             {
/*  950:1080 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  951:1081 */               local_ServerRequest.write_value((AEException)localThrowable28, AEException.class);
/*  952:     */             }
/*  953:     */             else
/*  954:     */             {
/*  955:1085 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  956:1086 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable28);
/*  957:     */             }
/*  958:     */           }
/*  959:1089 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable28);
/*  960:     */         }
/*  961:1091 */         localThrowable28.printStackTrace(Jaguar.log);
/*  962:1092 */         localRemoteException2 = null;
/*  963:1093 */         localRemoteException2 = new RemoteException(localThrowable28.getMessage());
/*  964:1094 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/*  965:1095 */         return localThrowable28.getClass().getName();
/*  966:     */       }
/*  967:     */     case 28: 
/*  968:     */       try
/*  969:     */       {
/*  970:1104 */         AEDBParams localAEDBParams5 = (AEDBParams)local_ServerRequest.read_value(AEDBParams.class);
/*  971:1105 */         localAEAlertAdminBean
/*  972:1106 */           .initServerAll(
/*  973:1107 */           localAEDBParams5);
/*  974:     */       }
/*  975:     */       catch (Throwable localThrowable29)
/*  976:     */       {
/*  977:1112 */         if ((localThrowable29 instanceof AEException))
/*  978:     */         {
/*  979:1114 */           if (UserException.ok(paramOutputStream)) {
/*  980:1116 */             if (local_ServerRequest.isRMI())
/*  981:     */             {
/*  982:1118 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/*  983:1119 */               local_ServerRequest.write_value((AEException)localThrowable29, AEException.class);
/*  984:     */             }
/*  985:     */             else
/*  986:     */             {
/*  987:1123 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/*  988:1124 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable29);
/*  989:     */             }
/*  990:     */           }
/*  991:1127 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable29);
/*  992:     */         }
/*  993:1129 */         localThrowable29.printStackTrace(Jaguar.log);
/*  994:1130 */         localRemoteException2 = null;
/*  995:1131 */         localRemoteException2 = new RemoteException(localThrowable29.getMessage());
/*  996:1132 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/*  997:1133 */         return localThrowable29.getClass().getName();
/*  998:     */       }
/*  999:     */     case 29: 
/* 1000:     */       try
/* 1001:     */       {
/* 1002:1142 */         String str10 = local_ServerRequest.read_string();
/* 1003:1143 */         localAEAlertAdminBean
/* 1004:1144 */           .startServerNamed(
/* 1005:1145 */           str10);
/* 1006:     */       }
/* 1007:     */       catch (Throwable localThrowable30)
/* 1008:     */       {
/* 1009:1150 */         if ((localThrowable30 instanceof AEException))
/* 1010:     */         {
/* 1011:1152 */           if (UserException.ok(paramOutputStream)) {
/* 1012:1154 */             if (local_ServerRequest.isRMI())
/* 1013:     */             {
/* 1014:1156 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1015:1157 */               local_ServerRequest.write_value((AEException)localThrowable30, AEException.class);
/* 1016:     */             }
/* 1017:     */             else
/* 1018:     */             {
/* 1019:1161 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1020:1162 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable30);
/* 1021:     */             }
/* 1022:     */           }
/* 1023:1165 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable30);
/* 1024:     */         }
/* 1025:1167 */         localThrowable30.printStackTrace(Jaguar.log);
/* 1026:1168 */         localRemoteException2 = null;
/* 1027:1169 */         localRemoteException2 = new RemoteException(localThrowable30.getMessage());
/* 1028:1170 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/* 1029:1171 */         return localThrowable30.getClass().getName();
/* 1030:     */       }
/* 1031:     */     case 30: 
/* 1032:     */       try
/* 1033:     */       {
/* 1034:1180 */         localAEAlertAdminBean.startServerAll();
/* 1035:     */       }
/* 1036:     */       catch (Throwable localThrowable31)
/* 1037:     */       {
/* 1038:1185 */         if ((localThrowable31 instanceof AEException))
/* 1039:     */         {
/* 1040:1187 */           if (UserException.ok(paramOutputStream)) {
/* 1041:1189 */             if (local_ServerRequest.isRMI())
/* 1042:     */             {
/* 1043:1191 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1044:1192 */               local_ServerRequest.write_value((AEException)localThrowable31, AEException.class);
/* 1045:     */             }
/* 1046:     */             else
/* 1047:     */             {
/* 1048:1196 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1049:1197 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable31);
/* 1050:     */             }
/* 1051:     */           }
/* 1052:1200 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable31);
/* 1053:     */         }
/* 1054:1202 */         localThrowable31.printStackTrace(Jaguar.log);
/* 1055:1203 */         localRemoteException2 = null;
/* 1056:1204 */         localRemoteException2 = new RemoteException(localThrowable31.getMessage());
/* 1057:1205 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/* 1058:1206 */         return localThrowable31.getClass().getName();
/* 1059:     */       }
/* 1060:     */     case 31: 
/* 1061:     */       try
/* 1062:     */       {
/* 1063:1215 */         localAEAlertAdminBean.stopServerAll();
/* 1064:     */       }
/* 1065:     */       catch (Throwable localThrowable32)
/* 1066:     */       {
/* 1067:1220 */         if ((localThrowable32 instanceof AEException))
/* 1068:     */         {
/* 1069:1222 */           if (UserException.ok(paramOutputStream)) {
/* 1070:1224 */             if (local_ServerRequest.isRMI())
/* 1071:     */             {
/* 1072:1226 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1073:1227 */               local_ServerRequest.write_value((AEException)localThrowable32, AEException.class);
/* 1074:     */             }
/* 1075:     */             else
/* 1076:     */             {
/* 1077:1231 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1078:1232 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable32);
/* 1079:     */             }
/* 1080:     */           }
/* 1081:1235 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable32);
/* 1082:     */         }
/* 1083:1237 */         localThrowable32.printStackTrace(Jaguar.log);
/* 1084:1238 */         localRemoteException2 = null;
/* 1085:1239 */         localRemoteException2 = new RemoteException(localThrowable32.getMessage());
/* 1086:1240 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/* 1087:1241 */         return localThrowable32.getClass().getName();
/* 1088:     */       }
/* 1089:     */     case 32: 
/* 1090:     */       try
/* 1091:     */       {
/* 1092:1250 */         localAEAlertAdminBean.suspendServerAll();
/* 1093:     */       }
/* 1094:     */       catch (Throwable localThrowable33)
/* 1095:     */       {
/* 1096:1255 */         if ((localThrowable33 instanceof AEException))
/* 1097:     */         {
/* 1098:1257 */           if (UserException.ok(paramOutputStream)) {
/* 1099:1259 */             if (local_ServerRequest.isRMI())
/* 1100:     */             {
/* 1101:1261 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1102:1262 */               local_ServerRequest.write_value((AEException)localThrowable33, AEException.class);
/* 1103:     */             }
/* 1104:     */             else
/* 1105:     */             {
/* 1106:1266 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1107:1267 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable33);
/* 1108:     */             }
/* 1109:     */           }
/* 1110:1270 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable33);
/* 1111:     */         }
/* 1112:1272 */         localThrowable33.printStackTrace(Jaguar.log);
/* 1113:1273 */         localRemoteException2 = null;
/* 1114:1274 */         localRemoteException2 = new RemoteException(localThrowable33.getMessage());
/* 1115:1275 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/* 1116:1276 */         return localThrowable33.getClass().getName();
/* 1117:     */       }
/* 1118:     */     case 33: 
/* 1119:     */       try
/* 1120:     */       {
/* 1121:1285 */         localAEAlertAdminBean.resumeServerAll();
/* 1122:     */       }
/* 1123:     */       catch (Throwable localThrowable34)
/* 1124:     */       {
/* 1125:1290 */         if ((localThrowable34 instanceof AEException))
/* 1126:     */         {
/* 1127:1292 */           if (UserException.ok(paramOutputStream)) {
/* 1128:1294 */             if (local_ServerRequest.isRMI())
/* 1129:     */             {
/* 1130:1296 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1131:1297 */               local_ServerRequest.write_value((AEException)localThrowable34, AEException.class);
/* 1132:     */             }
/* 1133:     */             else
/* 1134:     */             {
/* 1135:1301 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1136:1302 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable34);
/* 1137:     */             }
/* 1138:     */           }
/* 1139:1305 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable34);
/* 1140:     */         }
/* 1141:1307 */         localThrowable34.printStackTrace(Jaguar.log);
/* 1142:1308 */         localRemoteException2 = null;
/* 1143:1309 */         localRemoteException2 = new RemoteException(localThrowable34.getMessage());
/* 1144:1310 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/* 1145:1311 */         return localThrowable34.getClass().getName();
/* 1146:     */       }
/* 1147:     */     case 34: 
/* 1148:     */       try
/* 1149:     */       {
/* 1150:1320 */         localAEAlertAdminBean.shutdownServerAll();
/* 1151:     */       }
/* 1152:     */       catch (Throwable localThrowable35)
/* 1153:     */       {
/* 1154:1325 */         if ((localThrowable35 instanceof AEException))
/* 1155:     */         {
/* 1156:1327 */           if (UserException.ok(paramOutputStream)) {
/* 1157:1329 */             if (local_ServerRequest.isRMI())
/* 1158:     */             {
/* 1159:1331 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1160:1332 */               local_ServerRequest.write_value((AEException)localThrowable35, AEException.class);
/* 1161:     */             }
/* 1162:     */             else
/* 1163:     */             {
/* 1164:1336 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1165:1337 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable35);
/* 1166:     */             }
/* 1167:     */           }
/* 1168:1340 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable35);
/* 1169:     */         }
/* 1170:1342 */         localThrowable35.printStackTrace(Jaguar.log);
/* 1171:1343 */         localRemoteException2 = null;
/* 1172:1344 */         localRemoteException2 = new RemoteException(localThrowable35.getMessage());
/* 1173:1345 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/* 1174:1346 */         return localThrowable35.getClass().getName();
/* 1175:     */       }
/* 1176:     */     case 35: 
/* 1177:     */       try
/* 1178:     */       {
/* 1179:1354 */         boolean bool2 = localAEAlertAdminBean
/* 1180:1355 */           .isSuspended();
/* 1181:     */         
/* 1182:1357 */         paramOutputStream.write_boolean(bool2);
/* 1183:     */       }
/* 1184:     */       catch (Throwable localThrowable36)
/* 1185:     */       {
/* 1186:1361 */         if ((localThrowable36 instanceof AEException))
/* 1187:     */         {
/* 1188:1363 */           if (UserException.ok(paramOutputStream)) {
/* 1189:1365 */             if (local_ServerRequest.isRMI())
/* 1190:     */             {
/* 1191:1367 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1192:1368 */               local_ServerRequest.write_value((AEException)localThrowable36, AEException.class);
/* 1193:     */             }
/* 1194:     */             else
/* 1195:     */             {
/* 1196:1372 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1197:1373 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable36);
/* 1198:     */             }
/* 1199:     */           }
/* 1200:1376 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable36);
/* 1201:     */         }
/* 1202:1378 */         localThrowable36.printStackTrace(Jaguar.log);
/* 1203:1379 */         localRemoteException2 = null;
/* 1204:1380 */         localRemoteException2 = new RemoteException(localThrowable36.getMessage());
/* 1205:1381 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/* 1206:1382 */         return localThrowable36.getClass().getName();
/* 1207:     */       }
/* 1208:     */     case 36: 
/* 1209:     */       try
/* 1210:     */       {
/* 1211:1391 */         String str11 = local_ServerRequest.read_string();
/* 1212:1392 */         boolean bool9 = localAEAlertAdminBean
/* 1213:1393 */           .isSuspended(
/* 1214:1394 */           str11);
/* 1215:     */         
/* 1216:1396 */         paramOutputStream.write_boolean(bool9);
/* 1217:     */       }
/* 1218:     */       catch (Throwable localThrowable37)
/* 1219:     */       {
/* 1220:1400 */         if ((localThrowable37 instanceof AEException))
/* 1221:     */         {
/* 1222:1402 */           if (UserException.ok(paramOutputStream)) {
/* 1223:1404 */             if (local_ServerRequest.isRMI())
/* 1224:     */             {
/* 1225:1406 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1226:1407 */               local_ServerRequest.write_value((AEException)localThrowable37, AEException.class);
/* 1227:     */             }
/* 1228:     */             else
/* 1229:     */             {
/* 1230:1411 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1231:1412 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable37);
/* 1232:     */             }
/* 1233:     */           }
/* 1234:1415 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable37);
/* 1235:     */         }
/* 1236:1417 */         localThrowable37.printStackTrace(Jaguar.log);
/* 1237:1418 */         RemoteException localRemoteException3 = null;
/* 1238:1419 */         localRemoteException3 = new RemoteException(localThrowable37.getMessage());
/* 1239:1420 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException3);
/* 1240:1421 */         return localThrowable37.getClass().getName();
/* 1241:     */       }
/* 1242:     */     case 37: 
/* 1243:     */       try
/* 1244:     */       {
/* 1245:1430 */         String str12 = local_ServerRequest.read_string();
/* 1246:1431 */         boolean bool10 = localAEAlertAdminBean
/* 1247:1432 */           .isRunning(
/* 1248:1433 */           str12);
/* 1249:     */         
/* 1250:1435 */         paramOutputStream.write_boolean(bool10);
/* 1251:     */       }
/* 1252:     */       catch (Throwable localThrowable38)
/* 1253:     */       {
/* 1254:1439 */         if ((localThrowable38 instanceof AEException))
/* 1255:     */         {
/* 1256:1441 */           if (UserException.ok(paramOutputStream)) {
/* 1257:1443 */             if (local_ServerRequest.isRMI())
/* 1258:     */             {
/* 1259:1445 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1260:1446 */               local_ServerRequest.write_value((AEException)localThrowable38, AEException.class);
/* 1261:     */             }
/* 1262:     */             else
/* 1263:     */             {
/* 1264:1450 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1265:1451 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable38);
/* 1266:     */             }
/* 1267:     */           }
/* 1268:1454 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable38);
/* 1269:     */         }
/* 1270:1456 */         localThrowable38.printStackTrace(Jaguar.log);
/* 1271:1457 */         localObject2 = null;
/* 1272:1458 */         localObject2 = new RemoteException(localThrowable38.getMessage());
/* 1273:1459 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1274:1460 */         return localThrowable38.getClass().getName();
/* 1275:     */       }
/* 1276:     */     case 38: 
/* 1277:     */       try
/* 1278:     */       {
/* 1279:1468 */         boolean bool3 = localAEAlertAdminBean
/* 1280:1469 */           .isRunning();
/* 1281:     */         
/* 1282:1471 */         paramOutputStream.write_boolean(bool3);
/* 1283:     */       }
/* 1284:     */       catch (Throwable localThrowable39)
/* 1285:     */       {
/* 1286:1475 */         if ((localThrowable39 instanceof AEException))
/* 1287:     */         {
/* 1288:1477 */           if (UserException.ok(paramOutputStream)) {
/* 1289:1479 */             if (local_ServerRequest.isRMI())
/* 1290:     */             {
/* 1291:1481 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1292:1482 */               local_ServerRequest.write_value((AEException)localThrowable39, AEException.class);
/* 1293:     */             }
/* 1294:     */             else
/* 1295:     */             {
/* 1296:1486 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1297:1487 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable39);
/* 1298:     */             }
/* 1299:     */           }
/* 1300:1490 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable39);
/* 1301:     */         }
/* 1302:1492 */         localThrowable39.printStackTrace(Jaguar.log);
/* 1303:1493 */         localObject2 = null;
/* 1304:1494 */         localObject2 = new RemoteException(localThrowable39.getMessage());
/* 1305:1495 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1306:1496 */         return localThrowable39.getClass().getName();
/* 1307:     */       }
/* 1308:     */     case 39: 
/* 1309:     */       try
/* 1310:     */       {
/* 1311:1504 */         boolean bool4 = localAEAlertAdminBean
/* 1312:1505 */           .isClusterStopped();
/* 1313:     */         
/* 1314:1507 */         paramOutputStream.write_boolean(bool4);
/* 1315:     */       }
/* 1316:     */       catch (Throwable localThrowable40)
/* 1317:     */       {
/* 1318:1511 */         if ((localThrowable40 instanceof AEException))
/* 1319:     */         {
/* 1320:1513 */           if (UserException.ok(paramOutputStream)) {
/* 1321:1515 */             if (local_ServerRequest.isRMI())
/* 1322:     */             {
/* 1323:1517 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1324:1518 */               local_ServerRequest.write_value((AEException)localThrowable40, AEException.class);
/* 1325:     */             }
/* 1326:     */             else
/* 1327:     */             {
/* 1328:1522 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1329:1523 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable40);
/* 1330:     */             }
/* 1331:     */           }
/* 1332:1526 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable40);
/* 1333:     */         }
/* 1334:1528 */         localThrowable40.printStackTrace(Jaguar.log);
/* 1335:1529 */         localObject2 = null;
/* 1336:1530 */         localObject2 = new RemoteException(localThrowable40.getMessage());
/* 1337:1531 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1338:1532 */         return localThrowable40.getClass().getName();
/* 1339:     */       }
/* 1340:     */     case 40: 
/* 1341:     */       try
/* 1342:     */       {
/* 1343:1540 */         boolean bool5 = localAEAlertAdminBean
/* 1344:1541 */           .isClusterRunning();
/* 1345:     */         
/* 1346:1543 */         paramOutputStream.write_boolean(bool5);
/* 1347:     */       }
/* 1348:     */       catch (Throwable localThrowable41)
/* 1349:     */       {
/* 1350:1547 */         if ((localThrowable41 instanceof AEException))
/* 1351:     */         {
/* 1352:1549 */           if (UserException.ok(paramOutputStream)) {
/* 1353:1551 */             if (local_ServerRequest.isRMI())
/* 1354:     */             {
/* 1355:1553 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1356:1554 */               local_ServerRequest.write_value((AEException)localThrowable41, AEException.class);
/* 1357:     */             }
/* 1358:     */             else
/* 1359:     */             {
/* 1360:1558 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1361:1559 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable41);
/* 1362:     */             }
/* 1363:     */           }
/* 1364:1562 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable41);
/* 1365:     */         }
/* 1366:1564 */         localThrowable41.printStackTrace(Jaguar.log);
/* 1367:1565 */         localObject2 = null;
/* 1368:1566 */         localObject2 = new RemoteException(localThrowable41.getMessage());
/* 1369:1567 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1370:1568 */         return localThrowable41.getClass().getName();
/* 1371:     */       }
/* 1372:     */     case 41: 
/* 1373:     */       try
/* 1374:     */       {
/* 1375:1577 */         String str13 = local_ServerRequest.read_string();
/* 1376:     */         
/* 1377:1579 */         localObject2 = (AEApplicationInfo)local_ServerRequest.read_value(AEApplicationInfo.class);
/* 1378:1580 */         localAEAlertAdminBean
/* 1379:1581 */           .updateApplication(
/* 1380:1582 */           str13, 
/* 1381:1583 */           (AEApplicationInfo)localObject2);
/* 1382:     */       }
/* 1383:     */       catch (Throwable localThrowable42)
/* 1384:     */       {
/* 1385:1588 */         if ((localThrowable42 instanceof AEException))
/* 1386:     */         {
/* 1387:1590 */           if (UserException.ok(paramOutputStream)) {
/* 1388:1592 */             if (local_ServerRequest.isRMI())
/* 1389:     */             {
/* 1390:1594 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1391:1595 */               local_ServerRequest.write_value((AEException)localThrowable42, AEException.class);
/* 1392:     */             }
/* 1393:     */             else
/* 1394:     */             {
/* 1395:1599 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1396:1600 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable42);
/* 1397:     */             }
/* 1398:     */           }
/* 1399:1603 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable42);
/* 1400:     */         }
/* 1401:1605 */         localThrowable42.printStackTrace(Jaguar.log);
/* 1402:1606 */         localObject2 = null;
/* 1403:1607 */         localObject2 = new RemoteException(localThrowable42.getMessage());
/* 1404:1608 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1405:1609 */         return localThrowable42.getClass().getName();
/* 1406:     */       }
/* 1407:     */     case 42: 
/* 1408:     */       try
/* 1409:     */       {
/* 1410:1618 */         String str14 = local_ServerRequest.read_string();
/* 1411:     */         
/* 1412:1620 */         localObject2 = local_ServerRequest.read_string();
/* 1413:     */         
/* 1414:1622 */         localProperties = (Properties)local_ServerRequest.read_value(Properties.class);
/* 1415:     */         
/* 1416:1624 */         i = paramInputStream.read_long();
/* 1417:     */         
/* 1418:1626 */         localObject3 = local_ServerRequest.read_string();
/* 1419:1627 */         IAEChannelInfo localIAEChannelInfo2 = localAEAlertAdminBean
/* 1420:1628 */           .installDeliveryChannel(
/* 1421:1629 */           str14, 
/* 1422:1630 */           (String)localObject2, 
/* 1423:1631 */           localProperties, 
/* 1424:1632 */           i, 
/* 1425:1633 */           (String)localObject3);
/* 1426:     */         
/* 1427:1635 */         local_ServerRequest.write_value(localIAEChannelInfo2, IAEChannelInfo.class);
/* 1428:     */       }
/* 1429:     */       catch (Throwable localThrowable43)
/* 1430:     */       {
/* 1431:1639 */         if ((localThrowable43 instanceof AEException))
/* 1432:     */         {
/* 1433:1641 */           if (UserException.ok(paramOutputStream)) {
/* 1434:1643 */             if (local_ServerRequest.isRMI())
/* 1435:     */             {
/* 1436:1645 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1437:1646 */               local_ServerRequest.write_value((AEException)localThrowable43, AEException.class);
/* 1438:     */             }
/* 1439:     */             else
/* 1440:     */             {
/* 1441:1650 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1442:1651 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable43);
/* 1443:     */             }
/* 1444:     */           }
/* 1445:1654 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable43);
/* 1446:     */         }
/* 1447:1656 */         localThrowable43.printStackTrace(Jaguar.log);
/* 1448:1657 */         localObject2 = null;
/* 1449:1658 */         localObject2 = new RemoteException(localThrowable43.getMessage());
/* 1450:1659 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1451:1660 */         return localThrowable43.getClass().getName();
/* 1452:     */       }
/* 1453:     */     case 43: 
/* 1454:     */       try
/* 1455:     */       {
/* 1456:1669 */         String str15 = local_ServerRequest.read_string();
/* 1457:     */         
/* 1458:1671 */         localObject2 = local_ServerRequest.read_string();
/* 1459:     */         
/* 1460:1673 */         localProperties = (Properties)local_ServerRequest.read_value(Properties.class);
/* 1461:     */         
/* 1462:1675 */         i = paramInputStream.read_long();
/* 1463:1676 */         localObject3 = localAEAlertAdminBean
/* 1464:1677 */           .installDeliveryChannel(
/* 1465:1678 */           str15, 
/* 1466:1679 */           (String)localObject2, 
/* 1467:1680 */           localProperties, 
/* 1468:1681 */           i);
/* 1469:     */         
/* 1470:1683 */         local_ServerRequest.write_value(localObject3, IAEChannelInfo.class);
/* 1471:     */       }
/* 1472:     */       catch (Throwable localThrowable44)
/* 1473:     */       {
/* 1474:1687 */         if ((localThrowable44 instanceof AEException))
/* 1475:     */         {
/* 1476:1689 */           if (UserException.ok(paramOutputStream)) {
/* 1477:1691 */             if (local_ServerRequest.isRMI())
/* 1478:     */             {
/* 1479:1693 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1480:1694 */               local_ServerRequest.write_value((AEException)localThrowable44, AEException.class);
/* 1481:     */             }
/* 1482:     */             else
/* 1483:     */             {
/* 1484:1698 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1485:1699 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable44);
/* 1486:     */             }
/* 1487:     */           }
/* 1488:1702 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable44);
/* 1489:     */         }
/* 1490:1704 */         localThrowable44.printStackTrace(Jaguar.log);
/* 1491:1705 */         localObject2 = null;
/* 1492:1706 */         localObject2 = new RemoteException(localThrowable44.getMessage());
/* 1493:1707 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1494:1708 */         return localThrowable44.getClass().getName();
/* 1495:     */       }
/* 1496:     */     case 44: 
/* 1497:     */       try
/* 1498:     */       {
/* 1499:1717 */         String str16 = local_ServerRequest.read_string();
/* 1500:1718 */         localObject2 = localAEAlertAdminBean
/* 1501:1719 */           .uninstallDeliveryChannel(
/* 1502:1720 */           str16);
/* 1503:     */         
/* 1504:1722 */         local_ServerRequest.write_value(localObject2, IAEChannelInfo.class);
/* 1505:     */       }
/* 1506:     */       catch (Throwable localThrowable45)
/* 1507:     */       {
/* 1508:1726 */         if ((localThrowable45 instanceof AEException))
/* 1509:     */         {
/* 1510:1728 */           if (UserException.ok(paramOutputStream)) {
/* 1511:1730 */             if (local_ServerRequest.isRMI())
/* 1512:     */             {
/* 1513:1732 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1514:1733 */               local_ServerRequest.write_value((AEException)localThrowable45, AEException.class);
/* 1515:     */             }
/* 1516:     */             else
/* 1517:     */             {
/* 1518:1737 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1519:1738 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable45);
/* 1520:     */             }
/* 1521:     */           }
/* 1522:1741 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable45);
/* 1523:     */         }
/* 1524:1743 */         localThrowable45.printStackTrace(Jaguar.log);
/* 1525:1744 */         localObject2 = null;
/* 1526:1745 */         localObject2 = new RemoteException(localThrowable45.getMessage());
/* 1527:1746 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1528:1747 */         return localThrowable45.getClass().getName();
/* 1529:     */       }
/* 1530:     */     case 45: 
/* 1531:     */       try
/* 1532:     */       {
/* 1533:1756 */         String str17 = local_ServerRequest.read_string();
/* 1534:1757 */         localObject2 = localAEAlertAdminBean
/* 1535:1758 */           .loadDeliveryChannel(
/* 1536:1759 */           str17);
/* 1537:     */         
/* 1538:1761 */         local_ServerRequest.write_value(localObject2, IAEChannelInfo.class);
/* 1539:     */       }
/* 1540:     */       catch (Throwable localThrowable46)
/* 1541:     */       {
/* 1542:1765 */         if ((localThrowable46 instanceof AEException))
/* 1543:     */         {
/* 1544:1767 */           if (UserException.ok(paramOutputStream)) {
/* 1545:1769 */             if (local_ServerRequest.isRMI())
/* 1546:     */             {
/* 1547:1771 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1548:1772 */               local_ServerRequest.write_value((AEException)localThrowable46, AEException.class);
/* 1549:     */             }
/* 1550:     */             else
/* 1551:     */             {
/* 1552:1776 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1553:1777 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable46);
/* 1554:     */             }
/* 1555:     */           }
/* 1556:1780 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable46);
/* 1557:     */         }
/* 1558:1782 */         localThrowable46.printStackTrace(Jaguar.log);
/* 1559:1783 */         localObject2 = null;
/* 1560:1784 */         localObject2 = new RemoteException(localThrowable46.getMessage());
/* 1561:1785 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1562:1786 */         return localThrowable46.getClass().getName();
/* 1563:     */       }
/* 1564:     */     case 46: 
/* 1565:     */       try
/* 1566:     */       {
/* 1567:1795 */         String str18 = local_ServerRequest.read_string();
/* 1568:1796 */         localObject2 = localAEAlertAdminBean
/* 1569:1797 */           .unloadDeliveryChannel(
/* 1570:1798 */           str18);
/* 1571:     */         
/* 1572:1800 */         local_ServerRequest.write_value(localObject2, IAEChannelInfo.class);
/* 1573:     */       }
/* 1574:     */       catch (Throwable localThrowable47)
/* 1575:     */       {
/* 1576:1804 */         if ((localThrowable47 instanceof AEException))
/* 1577:     */         {
/* 1578:1806 */           if (UserException.ok(paramOutputStream)) {
/* 1579:1808 */             if (local_ServerRequest.isRMI())
/* 1580:     */             {
/* 1581:1810 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1582:1811 */               local_ServerRequest.write_value((AEException)localThrowable47, AEException.class);
/* 1583:     */             }
/* 1584:     */             else
/* 1585:     */             {
/* 1586:1815 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1587:1816 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable47);
/* 1588:     */             }
/* 1589:     */           }
/* 1590:1819 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable47);
/* 1591:     */         }
/* 1592:1821 */         localThrowable47.printStackTrace(Jaguar.log);
/* 1593:1822 */         localObject2 = null;
/* 1594:1823 */         localObject2 = new RemoteException(localThrowable47.getMessage());
/* 1595:1824 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1596:1825 */         return localThrowable47.getClass().getName();
/* 1597:     */       }
/* 1598:     */     case 47: 
/* 1599:     */       try
/* 1600:     */       {
/* 1601:1834 */         IAEChannelInfo localIAEChannelInfo1 = (IAEChannelInfo)local_ServerRequest.read_value(IAEChannelInfo.class);
/* 1602:1835 */         localObject2 = localAEAlertAdminBean
/* 1603:1836 */           .updateDeliveryChannel(
/* 1604:1837 */           localIAEChannelInfo1);
/* 1605:     */         
/* 1606:1839 */         local_ServerRequest.write_value(localObject2, IAEChannelInfo.class);
/* 1607:     */       }
/* 1608:     */       catch (Throwable localThrowable48)
/* 1609:     */       {
/* 1610:1843 */         if ((localThrowable48 instanceof AEException))
/* 1611:     */         {
/* 1612:1845 */           if (UserException.ok(paramOutputStream)) {
/* 1613:1847 */             if (local_ServerRequest.isRMI())
/* 1614:     */             {
/* 1615:1849 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1616:1850 */               local_ServerRequest.write_value((AEException)localThrowable48, AEException.class);
/* 1617:     */             }
/* 1618:     */             else
/* 1619:     */             {
/* 1620:1854 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1621:1855 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable48);
/* 1622:     */             }
/* 1623:     */           }
/* 1624:1858 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable48);
/* 1625:     */         }
/* 1626:1860 */         localThrowable48.printStackTrace(Jaguar.log);
/* 1627:1861 */         localObject2 = null;
/* 1628:1862 */         localObject2 = new RemoteException(localThrowable48.getMessage());
/* 1629:1863 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1630:1864 */         return localThrowable48.getClass().getName();
/* 1631:     */       }
/* 1632:     */     case 48: 
/* 1633:     */       try
/* 1634:     */       {
/* 1635:1873 */         String str19 = local_ServerRequest.read_string();
/* 1636:1874 */         localObject2 = localAEAlertAdminBean
/* 1637:1875 */           .getInstalledDeliveryChannels(
/* 1638:1876 */           str19);
/* 1639:1878 */         if (local_ServerRequest.isRMI()) {
/* 1640:1878 */           local_ServerRequest.write_value(localObject2, new IAEChannelInfo[0].getClass());
/* 1641:     */         } else {
/* 1642:1878 */           IAEChannelInfoSeqHelper.write(paramOutputStream, (IAEChannelInfo[])localObject2);
/* 1643:     */         }
/* 1644:     */       }
/* 1645:     */       catch (Throwable localThrowable49)
/* 1646:     */       {
/* 1647:1882 */         if ((localThrowable49 instanceof AEException))
/* 1648:     */         {
/* 1649:1884 */           if (UserException.ok(paramOutputStream)) {
/* 1650:1886 */             if (local_ServerRequest.isRMI())
/* 1651:     */             {
/* 1652:1888 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1653:1889 */               local_ServerRequest.write_value((AEException)localThrowable49, AEException.class);
/* 1654:     */             }
/* 1655:     */             else
/* 1656:     */             {
/* 1657:1893 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1658:1894 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable49);
/* 1659:     */             }
/* 1660:     */           }
/* 1661:1897 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable49);
/* 1662:     */         }
/* 1663:1899 */         localThrowable49.printStackTrace(Jaguar.log);
/* 1664:1900 */         localObject2 = null;
/* 1665:1901 */         localObject2 = new RemoteException(localThrowable49.getMessage());
/* 1666:1902 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1667:1903 */         return localThrowable49.getClass().getName();
/* 1668:     */       }
/* 1669:     */     case 49: 
/* 1670:     */       try
/* 1671:     */       {
/* 1672:1912 */         String str20 = local_ServerRequest.read_string();
/* 1673:1913 */         localObject2 = localAEAlertAdminBean
/* 1674:1914 */           .getLoadedDeliveryChannels(
/* 1675:1915 */           str20);
/* 1676:1917 */         if (local_ServerRequest.isRMI()) {
/* 1677:1917 */           local_ServerRequest.write_value(localObject2, new IAEChannelInfo[0].getClass());
/* 1678:     */         } else {
/* 1679:1917 */           IAEChannelInfoSeqHelper.write(paramOutputStream, (IAEChannelInfo[])localObject2);
/* 1680:     */         }
/* 1681:     */       }
/* 1682:     */       catch (Throwable localThrowable50)
/* 1683:     */       {
/* 1684:1921 */         if ((localThrowable50 instanceof AEException))
/* 1685:     */         {
/* 1686:1923 */           if (UserException.ok(paramOutputStream)) {
/* 1687:1925 */             if (local_ServerRequest.isRMI())
/* 1688:     */             {
/* 1689:1927 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1690:1928 */               local_ServerRequest.write_value((AEException)localThrowable50, AEException.class);
/* 1691:     */             }
/* 1692:     */             else
/* 1693:     */             {
/* 1694:1932 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1695:1933 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable50);
/* 1696:     */             }
/* 1697:     */           }
/* 1698:1936 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable50);
/* 1699:     */         }
/* 1700:1938 */         localThrowable50.printStackTrace(Jaguar.log);
/* 1701:1939 */         localObject2 = null;
/* 1702:1940 */         localObject2 = new RemoteException(localThrowable50.getMessage());
/* 1703:1941 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1704:1942 */         return localThrowable50.getClass().getName();
/* 1705:     */       }
/* 1706:     */     case 50: 
/* 1707:     */       try
/* 1708:     */       {
/* 1709:1951 */         boolean bool6 = paramInputStream.read_boolean();
/* 1710:1952 */         localObject2 = localAEAlertAdminBean
/* 1711:1953 */           .getAllAlerts(
/* 1712:1954 */           bool6);
/* 1713:1956 */         if (local_ServerRequest.isRMI()) {
/* 1714:1956 */           local_ServerRequest.write_value(localObject2, new IAEAlertDefinition[0].getClass());
/* 1715:     */         } else {
/* 1716:1956 */           IAEAlertDefinitionSeqHelper.write(paramOutputStream, (IAEAlertDefinition[])localObject2);
/* 1717:     */         }
/* 1718:     */       }
/* 1719:     */       catch (Throwable localThrowable51)
/* 1720:     */       {
/* 1721:1960 */         if ((localThrowable51 instanceof AEException))
/* 1722:     */         {
/* 1723:1962 */           if (UserException.ok(paramOutputStream)) {
/* 1724:1964 */             if (local_ServerRequest.isRMI())
/* 1725:     */             {
/* 1726:1966 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1727:1967 */               local_ServerRequest.write_value((AEException)localThrowable51, AEException.class);
/* 1728:     */             }
/* 1729:     */             else
/* 1730:     */             {
/* 1731:1971 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1732:1972 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable51);
/* 1733:     */             }
/* 1734:     */           }
/* 1735:1975 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable51);
/* 1736:     */         }
/* 1737:1977 */         localThrowable51.printStackTrace(Jaguar.log);
/* 1738:1978 */         localObject2 = null;
/* 1739:1979 */         localObject2 = new RemoteException(localThrowable51.getMessage());
/* 1740:1980 */         Connection.insertExToServiceContext(paramOutputStream, localObject2);
/* 1741:1981 */         return localThrowable51.getClass().getName();
/* 1742:     */       }
/* 1743:     */     default: 
/* 1744:1987 */       return 
/* 1745:1988 */         invoke1(
/* 1746:1989 */         local_ServerRequest, 
/* 1747:1990 */         paramInputStream, 
/* 1748:1991 */         paramOutputStream, 
/* 1749:1992 */         localAEAlertAdminBean, 
/* 1750:1993 */         localInteger);
/* 1751:     */     }
/* 1752:1997 */     return null;
/* 1753:     */   }
/* 1754:     */   
/* 1755:     */   private static String invoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, AEAlertAdminBean paramAEAlertAdminBean, Integer paramInteger)
/* 1756:     */   {
/* 1757:     */     Object localObject2;
/* 1758:     */     Object localObject6;
/* 1759:     */     Object localObject1;
/* 1760:     */     int n;
/* 1761:     */     int i2;
/* 1762:     */     Object localObject5;
/* 1763:     */     Object localObject7;
/* 1764:     */     IAEAlertDefinition localIAEAlertDefinition;
/* 1765:     */     Object localObject3;
/* 1766:     */     Object localObject4;
/* 1767:2007 */     switch (paramInteger.intValue())
/* 1768:     */     {
/* 1769:     */     case 51: 
/* 1770:     */       try
/* 1771:     */       {
/* 1772:2014 */         String str1 = param_ServerRequest.read_string();
/* 1773:     */         
/* 1774:2016 */         boolean bool1 = paramInputStream.read_boolean();
/* 1775:2017 */         localObject2 = paramAEAlertAdminBean
/* 1776:2018 */           .getAppAlerts(
/* 1777:2019 */           str1, 
/* 1778:2020 */           bool1);
/* 1779:2022 */         if (param_ServerRequest.isRMI()) {
/* 1780:2022 */           param_ServerRequest.write_value(localObject2, new IAEAlertDefinition[0].getClass());
/* 1781:     */         } else {
/* 1782:2022 */           IAEAlertDefinitionSeqHelper.write(paramOutputStream, (IAEAlertDefinition[])localObject2);
/* 1783:     */         }
/* 1784:     */       }
/* 1785:     */       catch (Throwable localThrowable1)
/* 1786:     */       {
/* 1787:2026 */         if ((localThrowable1 instanceof AEException))
/* 1788:     */         {
/* 1789:2028 */           if (UserException.ok(paramOutputStream)) {
/* 1790:2030 */             if (param_ServerRequest.isRMI())
/* 1791:     */             {
/* 1792:2032 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1793:2033 */               param_ServerRequest.write_value((AEException)localThrowable1, AEException.class);
/* 1794:     */             }
/* 1795:     */             else
/* 1796:     */             {
/* 1797:2037 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1798:2038 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable1);
/* 1799:     */             }
/* 1800:     */           }
/* 1801:2041 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable1);
/* 1802:     */         }
/* 1803:2043 */         localThrowable1.printStackTrace(Jaguar.log);
/* 1804:2044 */         RemoteException localRemoteException1 = null;
/* 1805:2045 */         localRemoteException1 = new RemoteException(localThrowable1.getMessage());
/* 1806:2046 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException1);
/* 1807:2047 */         return localThrowable1.getClass().getName();
/* 1808:     */       }
/* 1809:     */     case 52: 
/* 1810:     */       try
/* 1811:     */       {
/* 1812:2056 */         String str2 = param_ServerRequest.read_string();
/* 1813:     */         
/* 1814:2058 */         boolean bool2 = paramInputStream.read_boolean();
/* 1815:     */         
/* 1816:2060 */         localObject2 = param_ServerRequest.read_string();
/* 1817:     */         
/* 1818:2062 */         int i1 = paramInputStream.read_long();
/* 1819:     */         
/* 1820:2064 */         int i3 = paramInputStream.read_long();
/* 1821:2065 */         localObject6 = paramAEAlertAdminBean
/* 1822:2066 */           .getAppAlertsForChannelPaged(
/* 1823:2067 */           str2, 
/* 1824:2068 */           bool2, 
/* 1825:2069 */           (String)localObject2, 
/* 1826:2070 */           i1, 
/* 1827:2071 */           i3);
/* 1828:2073 */         if (param_ServerRequest.isRMI()) {
/* 1829:2073 */           param_ServerRequest.write_value(localObject6, new IAEAlertDefinition[0].getClass());
/* 1830:     */         } else {
/* 1831:2073 */           IAEAlertDefinitionSeqHelper.write(paramOutputStream, (IAEAlertDefinition[])localObject6);
/* 1832:     */         }
/* 1833:     */       }
/* 1834:     */       catch (Throwable localThrowable2)
/* 1835:     */       {
/* 1836:2077 */         if ((localThrowable2 instanceof AEException))
/* 1837:     */         {
/* 1838:2079 */           if (UserException.ok(paramOutputStream)) {
/* 1839:2081 */             if (param_ServerRequest.isRMI())
/* 1840:     */             {
/* 1841:2083 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1842:2084 */               param_ServerRequest.write_value((AEException)localThrowable2, AEException.class);
/* 1843:     */             }
/* 1844:     */             else
/* 1845:     */             {
/* 1846:2088 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1847:2089 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable2);
/* 1848:     */             }
/* 1849:     */           }
/* 1850:2092 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable2);
/* 1851:     */         }
/* 1852:2094 */         localThrowable2.printStackTrace(Jaguar.log);
/* 1853:2095 */         RemoteException localRemoteException2 = null;
/* 1854:2096 */         localRemoteException2 = new RemoteException(localThrowable2.getMessage());
/* 1855:2097 */         Connection.insertExToServiceContext(paramOutputStream, localRemoteException2);
/* 1856:2098 */         return localThrowable2.getClass().getName();
/* 1857:     */       }
/* 1858:     */     case 53: 
/* 1859:     */       try
/* 1860:     */       {
/* 1861:2107 */         String str3 = param_ServerRequest.read_string();
/* 1862:     */         
/* 1863:2109 */         boolean bool3 = paramInputStream.read_boolean();
/* 1864:2110 */         localObject2 = paramAEAlertAdminBean
/* 1865:2111 */           .getUserAlerts(
/* 1866:2112 */           str3, 
/* 1867:2113 */           bool3);
/* 1868:2115 */         if (param_ServerRequest.isRMI()) {
/* 1869:2115 */           param_ServerRequest.write_value(localObject2, new IAEAlertDefinition[0].getClass());
/* 1870:     */         } else {
/* 1871:2115 */           IAEAlertDefinitionSeqHelper.write(paramOutputStream, (IAEAlertDefinition[])localObject2);
/* 1872:     */         }
/* 1873:     */       }
/* 1874:     */       catch (Throwable localThrowable3)
/* 1875:     */       {
/* 1876:2119 */         if ((localThrowable3 instanceof AEException))
/* 1877:     */         {
/* 1878:2121 */           if (UserException.ok(paramOutputStream)) {
/* 1879:2123 */             if (param_ServerRequest.isRMI())
/* 1880:     */             {
/* 1881:2125 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1882:2126 */               param_ServerRequest.write_value((AEException)localThrowable3, AEException.class);
/* 1883:     */             }
/* 1884:     */             else
/* 1885:     */             {
/* 1886:2130 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1887:2131 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable3);
/* 1888:     */             }
/* 1889:     */           }
/* 1890:2134 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable3);
/* 1891:     */         }
/* 1892:2136 */         localThrowable3.printStackTrace(Jaguar.log);
/* 1893:2137 */         localObject1 = null;
/* 1894:2138 */         localObject1 = new RemoteException(localThrowable3.getMessage());
/* 1895:2139 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 1896:2140 */         return localThrowable3.getClass().getName();
/* 1897:     */       }
/* 1898:     */     case 54: 
/* 1899:     */       try
/* 1900:     */       {
/* 1901:2149 */         String str4 = param_ServerRequest.read_string();
/* 1902:     */         
/* 1903:2151 */         localObject1 = param_ServerRequest.read_string();
/* 1904:     */         
/* 1905:2153 */         boolean bool4 = paramInputStream.read_boolean();
/* 1906:2154 */         IAEAlertDefinition[] arrayOfIAEAlertDefinition = paramAEAlertAdminBean
/* 1907:2155 */           .getUserAlerts(
/* 1908:2156 */           str4, 
/* 1909:2157 */           (String)localObject1, 
/* 1910:2158 */           bool4);
/* 1911:2160 */         if (param_ServerRequest.isRMI()) {
/* 1912:2160 */           param_ServerRequest.write_value(arrayOfIAEAlertDefinition, new IAEAlertDefinition[0].getClass());
/* 1913:     */         } else {
/* 1914:2160 */           IAEAlertDefinitionSeqHelper.write(paramOutputStream, arrayOfIAEAlertDefinition);
/* 1915:     */         }
/* 1916:     */       }
/* 1917:     */       catch (Throwable localThrowable4)
/* 1918:     */       {
/* 1919:2164 */         if ((localThrowable4 instanceof AEException))
/* 1920:     */         {
/* 1921:2166 */           if (UserException.ok(paramOutputStream)) {
/* 1922:2168 */             if (param_ServerRequest.isRMI())
/* 1923:     */             {
/* 1924:2170 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1925:2171 */               param_ServerRequest.write_value((AEException)localThrowable4, AEException.class);
/* 1926:     */             }
/* 1927:     */             else
/* 1928:     */             {
/* 1929:2175 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1930:2176 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable4);
/* 1931:     */             }
/* 1932:     */           }
/* 1933:2179 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable4);
/* 1934:     */         }
/* 1935:2181 */         localThrowable4.printStackTrace(Jaguar.log);
/* 1936:2182 */         localObject1 = null;
/* 1937:2183 */         localObject1 = new RemoteException(localThrowable4.getMessage());
/* 1938:2184 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 1939:2185 */         return localThrowable4.getClass().getName();
/* 1940:     */       }
/* 1941:     */     case 55: 
/* 1942:     */       try
/* 1943:     */       {
/* 1944:2194 */         int i = paramInputStream.read_long();
/* 1945:2195 */         localObject1 = paramAEAlertAdminBean
/* 1946:2196 */           .getAlert(
/* 1947:2197 */           i);
/* 1948:     */         
/* 1949:2199 */         param_ServerRequest.write_value(localObject1, IAEAlertDefinition.class);
/* 1950:     */       }
/* 1951:     */       catch (Throwable localThrowable5)
/* 1952:     */       {
/* 1953:2203 */         if ((localThrowable5 instanceof AEException))
/* 1954:     */         {
/* 1955:2205 */           if (UserException.ok(paramOutputStream)) {
/* 1956:2207 */             if (param_ServerRequest.isRMI())
/* 1957:     */             {
/* 1958:2209 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 1959:2210 */               param_ServerRequest.write_value((AEException)localThrowable5, AEException.class);
/* 1960:     */             }
/* 1961:     */             else
/* 1962:     */             {
/* 1963:2214 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 1964:2215 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable5);
/* 1965:     */             }
/* 1966:     */           }
/* 1967:2218 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable5);
/* 1968:     */         }
/* 1969:2220 */         localThrowable5.printStackTrace(Jaguar.log);
/* 1970:2221 */         localObject1 = null;
/* 1971:2222 */         localObject1 = new RemoteException(localThrowable5.getMessage());
/* 1972:2223 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 1973:2224 */         return localThrowable5.getClass().getName();
/* 1974:     */       }
/* 1975:     */     case 56: 
/* 1976:     */       try
/* 1977:     */       {
/* 1978:     */         int[] arrayOfInt;
/* 1979:2233 */         if (param_ServerRequest.isRMI()) {
/* 1980:2233 */           arrayOfInt = (int[])param_ServerRequest.read_value(new int[0].getClass());
/* 1981:     */         } else {
/* 1982:2233 */           arrayOfInt = longSeqHelper.read(paramInputStream);
/* 1983:     */         }
/* 1984:2234 */         localObject1 = 
/* 1985:2235 */           paramAEAlertAdminBean.getAlerts(
/* 1986:2236 */           arrayOfInt);
/* 1987:2238 */         if (param_ServerRequest.isRMI()) {
/* 1988:2238 */           param_ServerRequest.write_value(localObject1, new IAEAlertDefinition[0].getClass());
/* 1989:     */         } else {
/* 1990:2238 */           IAEAlertDefinitionSeqHelper.write(paramOutputStream, (IAEAlertDefinition[])localObject1);
/* 1991:     */         }
/* 1992:     */       }
/* 1993:     */       catch (Throwable localThrowable6)
/* 1994:     */       {
/* 1995:2242 */         if ((localThrowable6 instanceof AEException))
/* 1996:     */         {
/* 1997:2244 */           if (UserException.ok(paramOutputStream)) {
/* 1998:2246 */             if (param_ServerRequest.isRMI())
/* 1999:     */             {
/* 2000:2248 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2001:2249 */               param_ServerRequest.write_value((AEException)localThrowable6, AEException.class);
/* 2002:     */             }
/* 2003:     */             else
/* 2004:     */             {
/* 2005:2253 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2006:2254 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable6);
/* 2007:     */             }
/* 2008:     */           }
/* 2009:2257 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable6);
/* 2010:     */         }
/* 2011:2259 */         localThrowable6.printStackTrace(Jaguar.log);
/* 2012:2260 */         localObject1 = null;
/* 2013:2261 */         localObject1 = new RemoteException(localThrowable6.getMessage());
/* 2014:2262 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2015:2263 */         return localThrowable6.getClass().getName();
/* 2016:     */       }
/* 2017:     */     case 57: 
/* 2018:     */       try
/* 2019:     */       {
/* 2020:2272 */         int j = paramInputStream.read_long();
/* 2021:     */         
/* 2022:2274 */         localObject1 = param_ServerRequest.read_string();
/* 2023:     */         
/* 2024:2276 */         n = paramInputStream.read_long();
/* 2025:     */         
/* 2026:2278 */         i2 = paramInputStream.read_long();
/* 2027:     */         
/* 2028:2280 */         localObject5 = (AEScheduleInfo)param_ServerRequest.read_value(AEScheduleInfo.class);
/* 2029:2282 */         if (param_ServerRequest.isRMI()) {
/* 2030:2282 */           localObject6 = (IAEDeliveryInfo[])param_ServerRequest.read_value(new IAEDeliveryInfo[0].getClass());
/* 2031:     */         } else {
/* 2032:2282 */           localObject6 = IAEDeliveryInfoSeqHelper.read(paramInputStream);
/* 2033:     */         }
/* 2034:2284 */         localObject7 = param_ServerRequest.read_string();
/* 2035:2285 */         localIAEAlertDefinition = paramAEAlertAdminBean
/* 2036:2286 */           .updateAlert(
/* 2037:2287 */           j, 
/* 2038:2288 */           (String)localObject1, 
/* 2039:2289 */           n, 
/* 2040:2290 */           i2, 
/* 2041:2291 */           (AEScheduleInfo)localObject5, 
/* 2042:2292 */           (IAEDeliveryInfo[])localObject6, 
/* 2043:2293 */           (String)localObject7);
/* 2044:     */         
/* 2045:2295 */         param_ServerRequest.write_value(localIAEAlertDefinition, IAEAlertDefinition.class);
/* 2046:     */       }
/* 2047:     */       catch (Throwable localThrowable7)
/* 2048:     */       {
/* 2049:2299 */         if ((localThrowable7 instanceof AEException))
/* 2050:     */         {
/* 2051:2301 */           if (UserException.ok(paramOutputStream)) {
/* 2052:2303 */             if (param_ServerRequest.isRMI())
/* 2053:     */             {
/* 2054:2305 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2055:2306 */               param_ServerRequest.write_value((AEException)localThrowable7, AEException.class);
/* 2056:     */             }
/* 2057:     */             else
/* 2058:     */             {
/* 2059:2310 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2060:2311 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable7);
/* 2061:     */             }
/* 2062:     */           }
/* 2063:2314 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable7);
/* 2064:     */         }
/* 2065:2316 */         localThrowable7.printStackTrace(Jaguar.log);
/* 2066:2317 */         localObject1 = null;
/* 2067:2318 */         localObject1 = new RemoteException(localThrowable7.getMessage());
/* 2068:2319 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2069:2320 */         return localThrowable7.getClass().getName();
/* 2070:     */       }
/* 2071:     */     case 58: 
/* 2072:     */       try
/* 2073:     */       {
/* 2074:2329 */         int k = paramInputStream.read_long();
/* 2075:     */         
/* 2076:2331 */         localObject1 = param_ServerRequest.read_string();
/* 2077:     */         
/* 2078:2333 */         n = paramInputStream.read_long();
/* 2079:     */         
/* 2080:2335 */         i2 = paramInputStream.read_long();
/* 2081:     */         
/* 2082:2337 */         localObject5 = (AEScheduleInfo)param_ServerRequest.read_value(AEScheduleInfo.class);
/* 2083:2339 */         if (param_ServerRequest.isRMI()) {
/* 2084:2339 */           localObject6 = (IAEDeliveryInfo[])param_ServerRequest.read_value(new IAEDeliveryInfo[0].getClass());
/* 2085:     */         } else {
/* 2086:2339 */           localObject6 = IAEDeliveryInfoSeqHelper.read(paramInputStream);
/* 2087:     */         }
/* 2088:2341 */         if (param_ServerRequest.isRMI()) {
/* 2089:2341 */           localObject7 = (byte[])param_ServerRequest.read_value(new byte[0].getClass());
/* 2090:     */         } else {
/* 2091:2341 */           localObject7 = BinaryHelper.read(paramInputStream);
/* 2092:     */         }
/* 2093:2342 */         localIAEAlertDefinition = 
/* 2094:2343 */           paramAEAlertAdminBean.updateAlert(
/* 2095:2344 */           k, 
/* 2096:2345 */           (String)localObject1, 
/* 2097:2346 */           n, 
/* 2098:2347 */           i2, 
/* 2099:2348 */           (AEScheduleInfo)localObject5, 
/* 2100:2349 */           (IAEDeliveryInfo[])localObject6, 
/* 2101:2350 */           (byte[])localObject7);
/* 2102:     */         
/* 2103:2352 */         param_ServerRequest.write_value(localIAEAlertDefinition, IAEAlertDefinition.class);
/* 2104:     */       }
/* 2105:     */       catch (Throwable localThrowable8)
/* 2106:     */       {
/* 2107:2356 */         if ((localThrowable8 instanceof AEException))
/* 2108:     */         {
/* 2109:2358 */           if (UserException.ok(paramOutputStream)) {
/* 2110:2360 */             if (param_ServerRequest.isRMI())
/* 2111:     */             {
/* 2112:2362 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2113:2363 */               param_ServerRequest.write_value((AEException)localThrowable8, AEException.class);
/* 2114:     */             }
/* 2115:     */             else
/* 2116:     */             {
/* 2117:2367 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2118:2368 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable8);
/* 2119:     */             }
/* 2120:     */           }
/* 2121:2371 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable8);
/* 2122:     */         }
/* 2123:2373 */         localThrowable8.printStackTrace(Jaguar.log);
/* 2124:2374 */         localObject1 = null;
/* 2125:2375 */         localObject1 = new RemoteException(localThrowable8.getMessage());
/* 2126:2376 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2127:2377 */         return localThrowable8.getClass().getName();
/* 2128:     */       }
/* 2129:     */     case 59: 
/* 2130:     */       try
/* 2131:     */       {
/* 2132:2386 */         int m = paramInputStream.read_long();
/* 2133:2387 */         paramAEAlertAdminBean
/* 2134:2388 */           .cancelAlert(
/* 2135:2389 */           m);
/* 2136:     */       }
/* 2137:     */       catch (Throwable localThrowable9)
/* 2138:     */       {
/* 2139:2394 */         if ((localThrowable9 instanceof AEException))
/* 2140:     */         {
/* 2141:2396 */           if (UserException.ok(paramOutputStream)) {
/* 2142:2398 */             if (param_ServerRequest.isRMI())
/* 2143:     */             {
/* 2144:2400 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2145:2401 */               param_ServerRequest.write_value((AEException)localThrowable9, AEException.class);
/* 2146:     */             }
/* 2147:     */             else
/* 2148:     */             {
/* 2149:2405 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2150:2406 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable9);
/* 2151:     */             }
/* 2152:     */           }
/* 2153:2409 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable9);
/* 2154:     */         }
/* 2155:2411 */         localThrowable9.printStackTrace(Jaguar.log);
/* 2156:2412 */         localObject1 = null;
/* 2157:2413 */         localObject1 = new RemoteException(localThrowable9.getMessage());
/* 2158:2414 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2159:2415 */         return localThrowable9.getClass().getName();
/* 2160:     */       }
/* 2161:     */     case 60: 
/* 2162:     */       try
/* 2163:     */       {
/* 2164:2424 */         String str5 = param_ServerRequest.read_string();
/* 2165:     */         
/* 2166:2426 */         localObject1 = param_ServerRequest.read_string();
/* 2167:2427 */         paramAEAlertAdminBean
/* 2168:2428 */           .cancelUserAlerts(
/* 2169:2429 */           str5, 
/* 2170:2430 */           (String)localObject1);
/* 2171:     */       }
/* 2172:     */       catch (Throwable localThrowable10)
/* 2173:     */       {
/* 2174:2435 */         if ((localThrowable10 instanceof AEException))
/* 2175:     */         {
/* 2176:2437 */           if (UserException.ok(paramOutputStream)) {
/* 2177:2439 */             if (param_ServerRequest.isRMI())
/* 2178:     */             {
/* 2179:2441 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2180:2442 */               param_ServerRequest.write_value((AEException)localThrowable10, AEException.class);
/* 2181:     */             }
/* 2182:     */             else
/* 2183:     */             {
/* 2184:2446 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2185:2447 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable10);
/* 2186:     */             }
/* 2187:     */           }
/* 2188:2450 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable10);
/* 2189:     */         }
/* 2190:2452 */         localThrowable10.printStackTrace(Jaguar.log);
/* 2191:2453 */         localObject1 = null;
/* 2192:2454 */         localObject1 = new RemoteException(localThrowable10.getMessage());
/* 2193:2455 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2194:2456 */         return localThrowable10.getClass().getName();
/* 2195:     */       }
/* 2196:     */     case 61: 
/* 2197:     */       try
/* 2198:     */       {
/* 2199:2465 */         Date localDate = (Date)param_ServerRequest.read_value(Date.class);
/* 2200:     */         
/* 2201:2467 */         localObject1 = (Date)param_ServerRequest.read_value(Date.class);
/* 2202:2468 */         localObject3 = paramAEAlertAdminBean
/* 2203:2469 */           .getAuditInfo(
/* 2204:2470 */           localDate, 
/* 2205:2471 */           (Date)localObject1);
/* 2206:2473 */         if (param_ServerRequest.isRMI()) {
/* 2207:2473 */           param_ServerRequest.write_value(localObject3, new IAEAuditInfo[0].getClass());
/* 2208:     */         } else {
/* 2209:2473 */           IAEAuditInfoSeqHelper.write(paramOutputStream, (IAEAuditInfo[])localObject3);
/* 2210:     */         }
/* 2211:     */       }
/* 2212:     */       catch (Throwable localThrowable11)
/* 2213:     */       {
/* 2214:2477 */         if ((localThrowable11 instanceof AEException))
/* 2215:     */         {
/* 2216:2479 */           if (UserException.ok(paramOutputStream)) {
/* 2217:2481 */             if (param_ServerRequest.isRMI())
/* 2218:     */             {
/* 2219:2483 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2220:2484 */               param_ServerRequest.write_value((AEException)localThrowable11, AEException.class);
/* 2221:     */             }
/* 2222:     */             else
/* 2223:     */             {
/* 2224:2488 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2225:2489 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable11);
/* 2226:     */             }
/* 2227:     */           }
/* 2228:2492 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable11);
/* 2229:     */         }
/* 2230:2494 */         localThrowable11.printStackTrace(Jaguar.log);
/* 2231:2495 */         localObject1 = null;
/* 2232:2496 */         localObject1 = new RemoteException(localThrowable11.getMessage());
/* 2233:2497 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2234:2498 */         return localThrowable11.getClass().getName();
/* 2235:     */       }
/* 2236:     */     case 62: 
/* 2237:     */       try
/* 2238:     */       {
/* 2239:2507 */         String str6 = param_ServerRequest.read_string();
/* 2240:     */         
/* 2241:2509 */         localObject1 = (Date)param_ServerRequest.read_value(Date.class);
/* 2242:     */         
/* 2243:2511 */         localObject3 = (Date)param_ServerRequest.read_value(Date.class);
/* 2244:2512 */         localObject4 = paramAEAlertAdminBean
/* 2245:2513 */           .getAppAuditInfo(
/* 2246:2514 */           str6, 
/* 2247:2515 */           (Date)localObject1, 
/* 2248:2516 */           (Date)localObject3);
/* 2249:2518 */         if (param_ServerRequest.isRMI()) {
/* 2250:2518 */           param_ServerRequest.write_value(localObject4, new IAEAuditInfo[0].getClass());
/* 2251:     */         } else {
/* 2252:2518 */           IAEAuditInfoSeqHelper.write(paramOutputStream, (IAEAuditInfo[])localObject4);
/* 2253:     */         }
/* 2254:     */       }
/* 2255:     */       catch (Throwable localThrowable12)
/* 2256:     */       {
/* 2257:2522 */         if ((localThrowable12 instanceof AEException))
/* 2258:     */         {
/* 2259:2524 */           if (UserException.ok(paramOutputStream)) {
/* 2260:2526 */             if (param_ServerRequest.isRMI())
/* 2261:     */             {
/* 2262:2528 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2263:2529 */               param_ServerRequest.write_value((AEException)localThrowable12, AEException.class);
/* 2264:     */             }
/* 2265:     */             else
/* 2266:     */             {
/* 2267:2533 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2268:2534 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable12);
/* 2269:     */             }
/* 2270:     */           }
/* 2271:2537 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable12);
/* 2272:     */         }
/* 2273:2539 */         localThrowable12.printStackTrace(Jaguar.log);
/* 2274:2540 */         localObject1 = null;
/* 2275:2541 */         localObject1 = new RemoteException(localThrowable12.getMessage());
/* 2276:2542 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2277:2543 */         return localThrowable12.getClass().getName();
/* 2278:     */       }
/* 2279:     */     case 63: 
/* 2280:     */       try
/* 2281:     */       {
/* 2282:2552 */         String str7 = param_ServerRequest.read_string();
/* 2283:     */         
/* 2284:2554 */         localObject1 = param_ServerRequest.read_string();
/* 2285:     */         
/* 2286:2556 */         localObject3 = (Date)param_ServerRequest.read_value(Date.class);
/* 2287:     */         
/* 2288:2558 */         localObject4 = (Date)param_ServerRequest.read_value(Date.class);
/* 2289:2559 */         localObject5 = paramAEAlertAdminBean
/* 2290:2560 */           .getUserAuditInfo(
/* 2291:2561 */           str7, 
/* 2292:2562 */           (String)localObject1, 
/* 2293:2563 */           (Date)localObject3, 
/* 2294:2564 */           (Date)localObject4);
/* 2295:2566 */         if (param_ServerRequest.isRMI()) {
/* 2296:2566 */           param_ServerRequest.write_value(localObject5, new IAEAuditInfo[0].getClass());
/* 2297:     */         } else {
/* 2298:2566 */           IAEAuditInfoSeqHelper.write(paramOutputStream, (IAEAuditInfo[])localObject5);
/* 2299:     */         }
/* 2300:     */       }
/* 2301:     */       catch (Throwable localThrowable13)
/* 2302:     */       {
/* 2303:2570 */         if ((localThrowable13 instanceof AEException))
/* 2304:     */         {
/* 2305:2572 */           if (UserException.ok(paramOutputStream)) {
/* 2306:2574 */             if (param_ServerRequest.isRMI())
/* 2307:     */             {
/* 2308:2576 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2309:2577 */               param_ServerRequest.write_value((AEException)localThrowable13, AEException.class);
/* 2310:     */             }
/* 2311:     */             else
/* 2312:     */             {
/* 2313:2581 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2314:2582 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable13);
/* 2315:     */             }
/* 2316:     */           }
/* 2317:2585 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable13);
/* 2318:     */         }
/* 2319:2587 */         localThrowable13.printStackTrace(Jaguar.log);
/* 2320:2588 */         localObject1 = null;
/* 2321:2589 */         localObject1 = new RemoteException(localThrowable13.getMessage());
/* 2322:2590 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2323:2591 */         return localThrowable13.getClass().getName();
/* 2324:     */       }
/* 2325:     */     case 64: 
/* 2326:     */       try
/* 2327:     */       {
/* 2328:2600 */         String str8 = param_ServerRequest.read_string();
/* 2329:     */         
/* 2330:2602 */         localObject1 = (Date)param_ServerRequest.read_value(Date.class);
/* 2331:     */         
/* 2332:2604 */         localObject3 = (Date)param_ServerRequest.read_value(Date.class);
/* 2333:2605 */         localObject4 = paramAEAlertAdminBean
/* 2334:2606 */           .getUserAuditInfo(
/* 2335:2607 */           str8, 
/* 2336:2608 */           (Date)localObject1, 
/* 2337:2609 */           (Date)localObject3);
/* 2338:2611 */         if (param_ServerRequest.isRMI()) {
/* 2339:2611 */           param_ServerRequest.write_value(localObject4, new IAEAuditInfo[0].getClass());
/* 2340:     */         } else {
/* 2341:2611 */           IAEAuditInfoSeqHelper.write(paramOutputStream, (IAEAuditInfo[])localObject4);
/* 2342:     */         }
/* 2343:     */       }
/* 2344:     */       catch (Throwable localThrowable14)
/* 2345:     */       {
/* 2346:2615 */         if ((localThrowable14 instanceof AEException))
/* 2347:     */         {
/* 2348:2617 */           if (UserException.ok(paramOutputStream)) {
/* 2349:2619 */             if (param_ServerRequest.isRMI())
/* 2350:     */             {
/* 2351:2621 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2352:2622 */               param_ServerRequest.write_value((AEException)localThrowable14, AEException.class);
/* 2353:     */             }
/* 2354:     */             else
/* 2355:     */             {
/* 2356:2626 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2357:2627 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable14);
/* 2358:     */             }
/* 2359:     */           }
/* 2360:2630 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable14);
/* 2361:     */         }
/* 2362:2632 */         localThrowable14.printStackTrace(Jaguar.log);
/* 2363:2633 */         localObject1 = null;
/* 2364:2634 */         localObject1 = new RemoteException(localThrowable14.getMessage());
/* 2365:2635 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2366:2636 */         return localThrowable14.getClass().getName();
/* 2367:     */       }
/* 2368:     */     case 65: 
/* 2369:     */       try
/* 2370:     */       {
/* 2371:2645 */         Properties localProperties1 = (Properties)param_ServerRequest.read_value(Properties.class);
/* 2372:2646 */         paramAEAlertAdminBean
/* 2373:2647 */           .setEngineProperties(
/* 2374:2648 */           localProperties1);
/* 2375:     */       }
/* 2376:     */       catch (Throwable localThrowable15)
/* 2377:     */       {
/* 2378:2653 */         if ((localThrowable15 instanceof AEException))
/* 2379:     */         {
/* 2380:2655 */           if (UserException.ok(paramOutputStream)) {
/* 2381:2657 */             if (param_ServerRequest.isRMI())
/* 2382:     */             {
/* 2383:2659 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2384:2660 */               param_ServerRequest.write_value((AEException)localThrowable15, AEException.class);
/* 2385:     */             }
/* 2386:     */             else
/* 2387:     */             {
/* 2388:2664 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2389:2665 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable15);
/* 2390:     */             }
/* 2391:     */           }
/* 2392:2668 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable15);
/* 2393:     */         }
/* 2394:2670 */         localThrowable15.printStackTrace(Jaguar.log);
/* 2395:2671 */         localObject1 = null;
/* 2396:2672 */         localObject1 = new RemoteException(localThrowable15.getMessage());
/* 2397:2673 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2398:2674 */         return localThrowable15.getClass().getName();
/* 2399:     */       }
/* 2400:     */     case 66: 
/* 2401:     */       try
/* 2402:     */       {
/* 2403:2682 */         Properties localProperties2 = paramAEAlertAdminBean
/* 2404:2683 */           .getEngineProperties();
/* 2405:     */         
/* 2406:2685 */         param_ServerRequest.write_value(localProperties2, Properties.class);
/* 2407:     */       }
/* 2408:     */       catch (Throwable localThrowable16)
/* 2409:     */       {
/* 2410:2689 */         if ((localThrowable16 instanceof AEException))
/* 2411:     */         {
/* 2412:2691 */           if (UserException.ok(paramOutputStream)) {
/* 2413:2693 */             if (param_ServerRequest.isRMI())
/* 2414:     */             {
/* 2415:2695 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2416:2696 */               param_ServerRequest.write_value((AEException)localThrowable16, AEException.class);
/* 2417:     */             }
/* 2418:     */             else
/* 2419:     */             {
/* 2420:2700 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2421:2701 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable16);
/* 2422:     */             }
/* 2423:     */           }
/* 2424:2704 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable16);
/* 2425:     */         }
/* 2426:2706 */         localThrowable16.printStackTrace(Jaguar.log);
/* 2427:2707 */         localObject1 = null;
/* 2428:2708 */         localObject1 = new RemoteException(localThrowable16.getMessage());
/* 2429:2709 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2430:2710 */         return localThrowable16.getClass().getName();
/* 2431:     */       }
/* 2432:     */     case 67: 
/* 2433:     */       try
/* 2434:     */       {
/* 2435:2719 */         String str9 = param_ServerRequest.read_string();
/* 2436:     */         
/* 2437:2721 */         localObject1 = (AEServerInfo)param_ServerRequest.read_value(AEServerInfo.class);
/* 2438:2722 */         localObject3 = paramAEAlertAdminBean
/* 2439:2723 */           .updateServer(
/* 2440:2724 */           str9, 
/* 2441:2725 */           (AEServerInfo)localObject1);
/* 2442:     */         
/* 2443:2727 */         param_ServerRequest.write_value(localObject3, AEServerInfo.class);
/* 2444:     */       }
/* 2445:     */       catch (Throwable localThrowable17)
/* 2446:     */       {
/* 2447:2731 */         if ((localThrowable17 instanceof AEException))
/* 2448:     */         {
/* 2449:2733 */           if (UserException.ok(paramOutputStream)) {
/* 2450:2735 */             if (param_ServerRequest.isRMI())
/* 2451:     */             {
/* 2452:2737 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2453:2738 */               param_ServerRequest.write_value((AEException)localThrowable17, AEException.class);
/* 2454:     */             }
/* 2455:     */             else
/* 2456:     */             {
/* 2457:2742 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2458:2743 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable17);
/* 2459:     */             }
/* 2460:     */           }
/* 2461:2746 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable17);
/* 2462:     */         }
/* 2463:2748 */         localThrowable17.printStackTrace(Jaguar.log);
/* 2464:2749 */         localObject1 = null;
/* 2465:2750 */         localObject1 = new RemoteException(localThrowable17.getMessage());
/* 2466:2751 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2467:2752 */         return localThrowable17.getClass().getName();
/* 2468:     */       }
/* 2469:     */     case 68: 
/* 2470:     */       try
/* 2471:     */       {
/* 2472:2761 */         String str10 = param_ServerRequest.read_string();
/* 2473:2762 */         paramAEAlertAdminBean
/* 2474:2763 */           .setPrimaryServer(
/* 2475:2764 */           str10);
/* 2476:     */       }
/* 2477:     */       catch (Throwable localThrowable18)
/* 2478:     */       {
/* 2479:2769 */         if ((localThrowable18 instanceof AEException))
/* 2480:     */         {
/* 2481:2771 */           if (UserException.ok(paramOutputStream)) {
/* 2482:2773 */             if (param_ServerRequest.isRMI())
/* 2483:     */             {
/* 2484:2775 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEEx:1.0");
/* 2485:2776 */               param_ServerRequest.write_value((AEException)localThrowable18, AEException.class);
/* 2486:     */             }
/* 2487:     */             else
/* 2488:     */             {
/* 2489:2780 */               paramOutputStream.write_string("IDL:com/ffusion/alert/interfaces/AEException:1.0");
/* 2490:2781 */               AEExceptionHelper.write(paramOutputStream, (AEException)localThrowable18);
/* 2491:     */             }
/* 2492:     */           }
/* 2493:2784 */           return UserException.toString("IDL:com/ffusion/alert/interfaces/AEException:1.0", localThrowable18);
/* 2494:     */         }
/* 2495:2786 */         localThrowable18.printStackTrace(Jaguar.log);
/* 2496:2787 */         localObject1 = null;
/* 2497:2788 */         localObject1 = new RemoteException(localThrowable18.getMessage());
/* 2498:2789 */         Connection.insertExToServiceContext(paramOutputStream, localObject1);
/* 2499:2790 */         return localThrowable18.getClass().getName();
/* 2500:     */       }
/* 2501:     */     }
/* 2502:2795 */     return null;
/* 2503:     */   }
/* 2504:     */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB._sk_UAEAdminEJB20_UAEAdminBean
 * JD-Core Version:    0.7.0.1
 */