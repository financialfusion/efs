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
/*   30:     */ import com.ffusion.ffs.interfaces.FFSExceptionHelper;
/*   31:     */ import com.ffusion.ffs.util.FFSProperties;
/*   32:     */ import com.sybase.CORBA.LocalFrame;
/*   33:     */ import com.sybase.CORBA.LocalStack;
/*   34:     */ import com.sybase.CORBA.ObjectRef;
/*   35:     */ import com.sybase.CORBA.ObjectVal;
/*   36:     */ import com.sybase.CORBA.UserException;
/*   37:     */ import com.sybase.CORBA._Request;
/*   38:     */ import com.sybase.ejb.EJBObject;
/*   39:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   40:     */ import java.util.ArrayList;
/*   41:     */ import java.util.HashMap;
/*   42:     */ import org.omg.CORBA.SystemException;
/*   43:     */ import org.omg.CORBA.TRANSIENT;
/*   44:     */ import org.omg.CORBA.portable.IDLEntity;
/*   45:     */ import org.omg.CORBA.portable.InputStream;
/*   46:     */ import org.omg.CORBA.portable.OutputStream;
/*   47:     */ 
/*   48:     */ public class IBPWAdmin_Stub
/*   49:     */   extends EJBObject
/*   50:     */   implements IBPWAdmin, IDLEntity
/*   51:     */ {
/*   52:     */   public IBPWAdmin_Stub(ObjectRef paramObjectRef)
/*   53:     */   {
/*   54:  21 */     super("RMI:com.ffusion.ffs.bpw.adminEJB.IBPWAdmin:0000000000000000", paramObjectRef);
/*   55:     */   }
/*   56:     */   
/*   57:     */   public void start(PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
/*   58:     */     throws java.rmi.RemoteException, FFSException
/*   59:     */   {
/*   60:  29 */     for (int i = 1;; i++)
/*   61:     */     {
/*   62:  31 */       _Request local_Request = null;
/*   63:  32 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   64:  33 */       boolean bool1 = false;
/*   65:  34 */       LocalFrame localLocalFrame = null;
/*   66:  35 */       boolean bool2 = false;
/*   67:     */       try
/*   68:     */       {
/*   69:  38 */         local_Request = __request("start__PropertyConfig__InstructionTypeSeq", "start__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType");
/*   70:  39 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   71:  40 */         bool2 = localLocalStack.isArgsOnLocal();
/*   72:  41 */         localLocalStack.setArgsOnLocal(bool1);
/*   73:  43 */         if (bool1)
/*   74:     */         {
/*   75:  45 */           localLocalFrame = new LocalFrame(2);
/*   76:  46 */           localLocalStack.push(localLocalFrame);
/*   77:     */         }
/*   78:     */         OutputStream localOutputStream;
/*   79:  48 */         if (!bool1)
/*   80:     */         {
/*   81:  50 */           localOutputStream = local_Request.getOutputStream();
/*   82:  51 */           if (local_Request.isRMI()) {
/*   83:  51 */             local_Request.write_value(paramPropertyConfig, PropertyConfig.class);
/*   84:     */           } else {
/*   85:  51 */             PropertyConfigHelper.write(localOutputStream, paramPropertyConfig);
/*   86:     */           }
/*   87:  52 */           if (local_Request.isRMI()) {
/*   88:  52 */             local_Request.write_value(paramArrayOfInstructionType, new InstructionType[0].getClass());
/*   89:     */           } else {
/*   90:  52 */             InstructionTypeSeqHelper.write(localOutputStream, paramArrayOfInstructionType);
/*   91:     */           }
/*   92:     */         }
/*   93:     */         else
/*   94:     */         {
/*   95:  56 */           localOutputStream = local_Request.getOutputStream();
/*   96:  57 */           localLocalFrame.add(paramPropertyConfig);
/*   97:  58 */           localLocalFrame.add(paramArrayOfInstructionType);
/*   98:     */         }
/*   99:  60 */         local_Request.invoke();
/*  100:  61 */         return;
/*  101:     */       }
/*  102:     */       catch (TRANSIENT localTRANSIENT)
/*  103:     */       {
/*  104:  65 */         if (i == 10) {
/*  105:  67 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  106:     */         }
/*  107:     */       }
/*  108:     */       catch (UserException localUserException)
/*  109:     */       {
/*  110:  72 */         if (local_Request.isRMI())
/*  111:     */         {
/*  112:  74 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  113:  76 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  114:     */           }
/*  115:     */         }
/*  116:     */         else
/*  117:     */         {
/*  118:  81 */           Throwable localThrowable = null;
/*  119:  82 */           if (bool1)
/*  120:     */           {
/*  121:  84 */             localThrowable = localLocalFrame.getException();
/*  122:  85 */             if (localThrowable != null) {
/*  123:  87 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/*  124:     */             }
/*  125:     */           }
/*  126:  90 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  127:     */           {
/*  128:  92 */             if (local_Request.isRMI()) {
/*  129:  94 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  130:     */             }
/*  131:  98 */             if (bool1)
/*  132:     */             {
/*  133: 100 */               if (localThrowable != null) {
/*  134: 100 */                 throw ((FFSException)localThrowable);
/*  135:     */               }
/*  136:     */             }
/*  137:     */             else {
/*  138: 104 */               throw FFSExceptionHelper.read(localUserException.input);
/*  139:     */             }
/*  140:     */           }
/*  141:     */         }
/*  142: 109 */         throw new java.rmi.RemoteException(localUserException.type);
/*  143:     */       }
/*  144:     */       catch (SystemException localSystemException)
/*  145:     */       {
/*  146: 113 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  147:     */       }
/*  148:     */       finally
/*  149:     */       {
/*  150: 117 */         if (local_Request != null) {
/*  151: 119 */           local_Request.close();
/*  152:     */         }
/*  153: 121 */         if (bool1) {
/*  154: 122 */           localLocalStack.pop(localLocalFrame);
/*  155:     */         }
/*  156: 123 */         localLocalStack.setArgsOnLocal(bool2);
/*  157:     */       }
/*  158:     */     }
/*  159:     */   }
/*  160:     */   
/*  161:     */   public void start(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
/*  162:     */     throws java.rmi.RemoteException, FFSException
/*  163:     */   {
/*  164: 134 */     for (int i = 1;; i++)
/*  165:     */     {
/*  166: 136 */       _Request local_Request = null;
/*  167: 137 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  168: 138 */       boolean bool1 = false;
/*  169: 139 */       LocalFrame localLocalFrame = null;
/*  170: 140 */       boolean bool2 = false;
/*  171:     */       try
/*  172:     */       {
/*  173: 143 */         local_Request = __request("start__FFSProperties__PropertyConfig__InstructionTypeSeq", "start__org_omg_boxedIDL_com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType");
/*  174: 144 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  175: 145 */         bool2 = localLocalStack.isArgsOnLocal();
/*  176: 146 */         localLocalStack.setArgsOnLocal(bool1);
/*  177: 148 */         if (bool1)
/*  178:     */         {
/*  179: 150 */           localLocalFrame = new LocalFrame(3);
/*  180: 151 */           localLocalStack.push(localLocalFrame);
/*  181:     */         }
/*  182:     */         OutputStream localOutputStream;
/*  183: 153 */         if (!bool1)
/*  184:     */         {
/*  185: 155 */           localOutputStream = local_Request.getOutputStream();
/*  186: 156 */           local_Request.write_value(paramFFSProperties, FFSProperties.class);
/*  187: 157 */           if (local_Request.isRMI()) {
/*  188: 157 */             local_Request.write_value(paramPropertyConfig, PropertyConfig.class);
/*  189:     */           } else {
/*  190: 157 */             PropertyConfigHelper.write(localOutputStream, paramPropertyConfig);
/*  191:     */           }
/*  192: 158 */           if (local_Request.isRMI()) {
/*  193: 158 */             local_Request.write_value(paramArrayOfInstructionType, new InstructionType[0].getClass());
/*  194:     */           } else {
/*  195: 158 */             InstructionTypeSeqHelper.write(localOutputStream, paramArrayOfInstructionType);
/*  196:     */           }
/*  197:     */         }
/*  198:     */         else
/*  199:     */         {
/*  200: 162 */           localOutputStream = local_Request.getOutputStream();
/*  201: 163 */           localLocalFrame.add(paramFFSProperties);
/*  202: 164 */           localLocalFrame.add(paramPropertyConfig);
/*  203: 165 */           localLocalFrame.add(paramArrayOfInstructionType);
/*  204:     */         }
/*  205: 167 */         local_Request.invoke();
/*  206: 168 */         return;
/*  207:     */       }
/*  208:     */       catch (TRANSIENT localTRANSIENT)
/*  209:     */       {
/*  210: 172 */         if (i == 10) {
/*  211: 174 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  212:     */         }
/*  213:     */       }
/*  214:     */       catch (UserException localUserException)
/*  215:     */       {
/*  216: 179 */         if (local_Request.isRMI())
/*  217:     */         {
/*  218: 181 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  219: 183 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  220:     */           }
/*  221:     */         }
/*  222:     */         else
/*  223:     */         {
/*  224: 188 */           Throwable localThrowable = null;
/*  225: 189 */           if (bool1)
/*  226:     */           {
/*  227: 191 */             localThrowable = localLocalFrame.getException();
/*  228: 192 */             if (localThrowable != null) {
/*  229: 194 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/*  230:     */             }
/*  231:     */           }
/*  232: 197 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  233:     */           {
/*  234: 199 */             if (local_Request.isRMI()) {
/*  235: 201 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  236:     */             }
/*  237: 205 */             if (bool1)
/*  238:     */             {
/*  239: 207 */               if (localThrowable != null) {
/*  240: 207 */                 throw ((FFSException)localThrowable);
/*  241:     */               }
/*  242:     */             }
/*  243:     */             else {
/*  244: 211 */               throw FFSExceptionHelper.read(localUserException.input);
/*  245:     */             }
/*  246:     */           }
/*  247:     */         }
/*  248: 216 */         throw new java.rmi.RemoteException(localUserException.type);
/*  249:     */       }
/*  250:     */       catch (SystemException localSystemException)
/*  251:     */       {
/*  252: 220 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  253:     */       }
/*  254:     */       finally
/*  255:     */       {
/*  256: 224 */         if (local_Request != null) {
/*  257: 226 */           local_Request.close();
/*  258:     */         }
/*  259: 228 */         if (bool1) {
/*  260: 229 */           localLocalStack.pop(localLocalFrame);
/*  261:     */         }
/*  262: 230 */         localLocalStack.setArgsOnLocal(bool2);
/*  263:     */       }
/*  264:     */     }
/*  265:     */   }
/*  266:     */   
/*  267:     */   public void stop()
/*  268:     */     throws java.rmi.RemoteException, FFSException
/*  269:     */   {
/*  270: 238 */     for (int i = 1;; i++)
/*  271:     */     {
/*  272: 240 */       _Request local_Request = null;
/*  273: 241 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  274: 242 */       boolean bool = false;
/*  275:     */       try
/*  276:     */       {
/*  277: 245 */         local_Request = __request("stop");
/*  278: 246 */         bool = localLocalStack.isArgsOnLocal();
/*  279: 247 */         localLocalStack.setArgsOnLocal(false);
/*  280: 248 */         local_Request.invoke();
/*  281: 249 */         return;
/*  282:     */       }
/*  283:     */       catch (TRANSIENT localTRANSIENT)
/*  284:     */       {
/*  285: 253 */         if (i == 10) {
/*  286: 255 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  287:     */         }
/*  288:     */       }
/*  289:     */       catch (UserException localUserException)
/*  290:     */       {
/*  291: 260 */         if (local_Request.isRMI())
/*  292:     */         {
/*  293: 262 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  294: 264 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  295:     */           }
/*  296:     */         }
/*  297: 269 */         else if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  298:     */         {
/*  299: 271 */           if (local_Request.isRMI()) {
/*  300: 273 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  301:     */           }
/*  302: 277 */           throw FFSExceptionHelper.read(localUserException.input);
/*  303:     */         }
/*  304: 281 */         throw new java.rmi.RemoteException(localUserException.type);
/*  305:     */       }
/*  306:     */       catch (SystemException localSystemException)
/*  307:     */       {
/*  308: 285 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  309:     */       }
/*  310:     */       finally
/*  311:     */       {
/*  312: 289 */         if (local_Request != null) {
/*  313: 291 */           local_Request.close();
/*  314:     */         }
/*  315: 293 */         localLocalStack.setArgsOnLocal(bool);
/*  316:     */       }
/*  317:     */     }
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void cleanup(String paramString, ArrayList paramArrayList1, ArrayList paramArrayList2, HashMap paramHashMap)
/*  321:     */     throws java.rmi.RemoteException
/*  322:     */   {
/*  323: 305 */     for (int i = 1;; i++)
/*  324:     */     {
/*  325: 307 */       _Request local_Request = null;
/*  326: 308 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  327: 309 */       boolean bool1 = false;
/*  328: 310 */       LocalFrame localLocalFrame = null;
/*  329: 311 */       boolean bool2 = false;
/*  330:     */       try
/*  331:     */       {
/*  332: 314 */         local_Request = __request("cleanup__string__ArrayList__ArrayList__HashMap", "cleanup__CORBA_WStringValue__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_HashMap");
/*  333: 315 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  334: 316 */         bool2 = localLocalStack.isArgsOnLocal();
/*  335: 317 */         localLocalStack.setArgsOnLocal(bool1);
/*  336: 319 */         if (bool1)
/*  337:     */         {
/*  338: 321 */           localLocalFrame = new LocalFrame(4);
/*  339: 322 */           localLocalStack.push(localLocalFrame);
/*  340:     */         }
/*  341:     */         OutputStream localOutputStream;
/*  342: 324 */         if (!bool1)
/*  343:     */         {
/*  344: 326 */           localOutputStream = local_Request.getOutputStream();
/*  345: 327 */           local_Request.write_string(paramString);
/*  346: 328 */           local_Request.write_value(paramArrayList1, ArrayList.class);
/*  347: 329 */           local_Request.write_value(paramArrayList2, ArrayList.class);
/*  348: 330 */           local_Request.write_value(paramHashMap, HashMap.class);
/*  349:     */         }
/*  350:     */         else
/*  351:     */         {
/*  352: 334 */           localOutputStream = local_Request.getOutputStream();
/*  353: 335 */           localLocalFrame.add(paramString);
/*  354: 336 */           localLocalFrame.add(paramArrayList1);
/*  355: 337 */           localLocalFrame.add(paramArrayList2);
/*  356: 338 */           localLocalFrame.add(paramHashMap);
/*  357:     */         }
/*  358: 340 */         local_Request.invoke();
/*  359: 341 */         return;
/*  360:     */       }
/*  361:     */       catch (TRANSIENT localTRANSIENT)
/*  362:     */       {
/*  363: 345 */         if (i == 10) {
/*  364: 347 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  365:     */         }
/*  366:     */       }
/*  367:     */       catch (UserException localUserException)
/*  368:     */       {
/*  369: 352 */         local_Request.isRMI();
/*  370:     */         
/*  371:     */ 
/*  372: 355 */         throw new java.rmi.RemoteException(localUserException.type);
/*  373:     */       }
/*  374:     */       catch (SystemException localSystemException)
/*  375:     */       {
/*  376: 359 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  377:     */       }
/*  378:     */       finally
/*  379:     */       {
/*  380: 363 */         if (local_Request != null) {
/*  381: 365 */           local_Request.close();
/*  382:     */         }
/*  383: 367 */         if (bool1) {
/*  384: 368 */           localLocalStack.pop(localLocalFrame);
/*  385:     */         }
/*  386: 369 */         localLocalStack.setArgsOnLocal(bool2);
/*  387:     */       }
/*  388:     */     }
/*  389:     */   }
/*  390:     */   
/*  391:     */   public void cleanup(ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, HashMap paramHashMap)
/*  392:     */     throws java.rmi.RemoteException
/*  393:     */   {
/*  394: 381 */     for (int i = 1;; i++)
/*  395:     */     {
/*  396: 383 */       _Request local_Request = null;
/*  397: 384 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  398: 385 */       boolean bool1 = false;
/*  399: 386 */       LocalFrame localLocalFrame = null;
/*  400: 387 */       boolean bool2 = false;
/*  401:     */       try
/*  402:     */       {
/*  403: 390 */         local_Request = __request("cleanup__ArrayList__ArrayList__ArrayList__HashMap", "cleanup__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_HashMap");
/*  404: 391 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  405: 392 */         bool2 = localLocalStack.isArgsOnLocal();
/*  406: 393 */         localLocalStack.setArgsOnLocal(bool1);
/*  407: 395 */         if (bool1)
/*  408:     */         {
/*  409: 397 */           localLocalFrame = new LocalFrame(4);
/*  410: 398 */           localLocalStack.push(localLocalFrame);
/*  411:     */         }
/*  412:     */         OutputStream localOutputStream;
/*  413: 400 */         if (!bool1)
/*  414:     */         {
/*  415: 402 */           localOutputStream = local_Request.getOutputStream();
/*  416: 403 */           local_Request.write_value(paramArrayList1, ArrayList.class);
/*  417: 404 */           local_Request.write_value(paramArrayList2, ArrayList.class);
/*  418: 405 */           local_Request.write_value(paramArrayList3, ArrayList.class);
/*  419: 406 */           local_Request.write_value(paramHashMap, HashMap.class);
/*  420:     */         }
/*  421:     */         else
/*  422:     */         {
/*  423: 410 */           localOutputStream = local_Request.getOutputStream();
/*  424: 411 */           localLocalFrame.add(paramArrayList1);
/*  425: 412 */           localLocalFrame.add(paramArrayList2);
/*  426: 413 */           localLocalFrame.add(paramArrayList3);
/*  427: 414 */           localLocalFrame.add(paramHashMap);
/*  428:     */         }
/*  429: 416 */         local_Request.invoke();
/*  430: 417 */         return;
/*  431:     */       }
/*  432:     */       catch (TRANSIENT localTRANSIENT)
/*  433:     */       {
/*  434: 421 */         if (i == 10) {
/*  435: 423 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  436:     */         }
/*  437:     */       }
/*  438:     */       catch (UserException localUserException)
/*  439:     */       {
/*  440: 428 */         local_Request.isRMI();
/*  441:     */         
/*  442:     */ 
/*  443: 431 */         throw new java.rmi.RemoteException(localUserException.type);
/*  444:     */       }
/*  445:     */       catch (SystemException localSystemException)
/*  446:     */       {
/*  447: 435 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  448:     */       }
/*  449:     */       finally
/*  450:     */       {
/*  451: 439 */         if (local_Request != null) {
/*  452: 441 */           local_Request.close();
/*  453:     */         }
/*  454: 443 */         if (bool1) {
/*  455: 444 */           localLocalStack.pop(localLocalFrame);
/*  456:     */         }
/*  457: 445 */         localLocalStack.setArgsOnLocal(bool2);
/*  458:     */       }
/*  459:     */     }
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
/*  463:     */     throws java.rmi.RemoteException
/*  464:     */   {
/*  465: 457 */     for (int i = 1;; i++)
/*  466:     */     {
/*  467: 459 */       _Request local_Request = null;
/*  468: 460 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  469: 461 */       boolean bool1 = false;
/*  470: 462 */       LocalFrame localLocalFrame = null;
/*  471: 463 */       boolean bool2 = false;
/*  472:     */       try
/*  473:     */       {
/*  474: 466 */         local_Request = __request("cleanup__string__string__long__HashMap", "cleanup__CORBA_WStringValue__CORBA_WStringValue__long__org_omg_boxedIDL_java_util_HashMap");
/*  475: 467 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  476: 468 */         bool2 = localLocalStack.isArgsOnLocal();
/*  477: 469 */         localLocalStack.setArgsOnLocal(bool1);
/*  478: 471 */         if (bool1)
/*  479:     */         {
/*  480: 473 */           localLocalFrame = new LocalFrame(4);
/*  481: 474 */           localLocalStack.push(localLocalFrame);
/*  482:     */         }
/*  483:     */         OutputStream localOutputStream;
/*  484: 476 */         if (!bool1)
/*  485:     */         {
/*  486: 478 */           localOutputStream = local_Request.getOutputStream();
/*  487: 479 */           local_Request.write_string(paramString1);
/*  488: 480 */           local_Request.write_string(paramString2);
/*  489: 481 */           localOutputStream.write_long(paramInt);
/*  490: 482 */           local_Request.write_value(paramHashMap, HashMap.class);
/*  491:     */         }
/*  492:     */         else
/*  493:     */         {
/*  494: 486 */           localOutputStream = local_Request.getOutputStream();
/*  495: 487 */           localLocalFrame.add(paramString1);
/*  496: 488 */           localLocalFrame.add(paramString2);
/*  497: 489 */           localLocalFrame.add(paramInt);
/*  498: 490 */           localLocalFrame.add(paramHashMap);
/*  499:     */         }
/*  500: 492 */         local_Request.invoke();
/*  501: 493 */         return;
/*  502:     */       }
/*  503:     */       catch (TRANSIENT localTRANSIENT)
/*  504:     */       {
/*  505: 497 */         if (i == 10) {
/*  506: 499 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  507:     */         }
/*  508:     */       }
/*  509:     */       catch (UserException localUserException)
/*  510:     */       {
/*  511: 504 */         local_Request.isRMI();
/*  512:     */         
/*  513:     */ 
/*  514: 507 */         throw new java.rmi.RemoteException(localUserException.type);
/*  515:     */       }
/*  516:     */       catch (SystemException localSystemException)
/*  517:     */       {
/*  518: 511 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  519:     */       }
/*  520:     */       finally
/*  521:     */       {
/*  522: 515 */         if (local_Request != null) {
/*  523: 517 */           local_Request.close();
/*  524:     */         }
/*  525: 519 */         if (bool1) {
/*  526: 520 */           localLocalStack.pop(localLocalFrame);
/*  527:     */         }
/*  528: 521 */         localLocalStack.setArgsOnLocal(bool2);
/*  529:     */       }
/*  530:     */     }
/*  531:     */   }
/*  532:     */   
/*  533:     */   public void refresh(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
/*  534:     */     throws java.rmi.RemoteException, FFSException
/*  535:     */   {
/*  536: 532 */     for (int i = 1;; i++)
/*  537:     */     {
/*  538: 534 */       _Request local_Request = null;
/*  539: 535 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  540: 536 */       boolean bool1 = false;
/*  541: 537 */       LocalFrame localLocalFrame = null;
/*  542: 538 */       boolean bool2 = false;
/*  543:     */       try
/*  544:     */       {
/*  545: 541 */         local_Request = __request("refresh__FFSProperties__PropertyConfig__InstructionTypeSeq", "refresh__org_omg_boxedIDL_com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType");
/*  546: 542 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  547: 543 */         bool2 = localLocalStack.isArgsOnLocal();
/*  548: 544 */         localLocalStack.setArgsOnLocal(bool1);
/*  549: 546 */         if (bool1)
/*  550:     */         {
/*  551: 548 */           localLocalFrame = new LocalFrame(3);
/*  552: 549 */           localLocalStack.push(localLocalFrame);
/*  553:     */         }
/*  554:     */         OutputStream localOutputStream;
/*  555: 551 */         if (!bool1)
/*  556:     */         {
/*  557: 553 */           localOutputStream = local_Request.getOutputStream();
/*  558: 554 */           local_Request.write_value(paramFFSProperties, FFSProperties.class);
/*  559: 555 */           if (local_Request.isRMI()) {
/*  560: 555 */             local_Request.write_value(paramPropertyConfig, PropertyConfig.class);
/*  561:     */           } else {
/*  562: 555 */             PropertyConfigHelper.write(localOutputStream, paramPropertyConfig);
/*  563:     */           }
/*  564: 556 */           if (local_Request.isRMI()) {
/*  565: 556 */             local_Request.write_value(paramArrayOfInstructionType, new InstructionType[0].getClass());
/*  566:     */           } else {
/*  567: 556 */             InstructionTypeSeqHelper.write(localOutputStream, paramArrayOfInstructionType);
/*  568:     */           }
/*  569:     */         }
/*  570:     */         else
/*  571:     */         {
/*  572: 560 */           localOutputStream = local_Request.getOutputStream();
/*  573: 561 */           localLocalFrame.add(paramFFSProperties);
/*  574: 562 */           localLocalFrame.add(paramPropertyConfig);
/*  575: 563 */           localLocalFrame.add(paramArrayOfInstructionType);
/*  576:     */         }
/*  577: 565 */         local_Request.invoke();
/*  578: 566 */         return;
/*  579:     */       }
/*  580:     */       catch (TRANSIENT localTRANSIENT)
/*  581:     */       {
/*  582: 570 */         if (i == 10) {
/*  583: 572 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  584:     */         }
/*  585:     */       }
/*  586:     */       catch (UserException localUserException)
/*  587:     */       {
/*  588: 577 */         if (local_Request.isRMI())
/*  589:     */         {
/*  590: 579 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  591: 581 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  592:     */           }
/*  593:     */         }
/*  594:     */         else
/*  595:     */         {
/*  596: 586 */           Throwable localThrowable = null;
/*  597: 587 */           if (bool1)
/*  598:     */           {
/*  599: 589 */             localThrowable = localLocalFrame.getException();
/*  600: 590 */             if (localThrowable != null) {
/*  601: 592 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/*  602:     */             }
/*  603:     */           }
/*  604: 595 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  605:     */           {
/*  606: 597 */             if (local_Request.isRMI()) {
/*  607: 599 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  608:     */             }
/*  609: 603 */             if (bool1)
/*  610:     */             {
/*  611: 605 */               if (localThrowable != null) {
/*  612: 605 */                 throw ((FFSException)localThrowable);
/*  613:     */               }
/*  614:     */             }
/*  615:     */             else {
/*  616: 609 */               throw FFSExceptionHelper.read(localUserException.input);
/*  617:     */             }
/*  618:     */           }
/*  619:     */         }
/*  620: 614 */         throw new java.rmi.RemoteException(localUserException.type);
/*  621:     */       }
/*  622:     */       catch (SystemException localSystemException)
/*  623:     */       {
/*  624: 618 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  625:     */       }
/*  626:     */       finally
/*  627:     */       {
/*  628: 622 */         if (local_Request != null) {
/*  629: 624 */           local_Request.close();
/*  630:     */         }
/*  631: 626 */         if (bool1) {
/*  632: 627 */           localLocalStack.pop(localLocalFrame);
/*  633:     */         }
/*  634: 628 */         localLocalStack.setArgsOnLocal(bool2);
/*  635:     */       }
/*  636:     */     }
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void refresh(PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
/*  640:     */     throws java.rmi.RemoteException, FFSException
/*  641:     */   {
/*  642: 638 */     for (int i = 1;; i++)
/*  643:     */     {
/*  644: 640 */       _Request local_Request = null;
/*  645: 641 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  646: 642 */       boolean bool1 = false;
/*  647: 643 */       LocalFrame localLocalFrame = null;
/*  648: 644 */       boolean bool2 = false;
/*  649:     */       try
/*  650:     */       {
/*  651: 647 */         local_Request = __request("refresh__PropertyConfig__InstructionTypeSeq", "refresh__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType");
/*  652: 648 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  653: 649 */         bool2 = localLocalStack.isArgsOnLocal();
/*  654: 650 */         localLocalStack.setArgsOnLocal(bool1);
/*  655: 652 */         if (bool1)
/*  656:     */         {
/*  657: 654 */           localLocalFrame = new LocalFrame(2);
/*  658: 655 */           localLocalStack.push(localLocalFrame);
/*  659:     */         }
/*  660:     */         OutputStream localOutputStream;
/*  661: 657 */         if (!bool1)
/*  662:     */         {
/*  663: 659 */           localOutputStream = local_Request.getOutputStream();
/*  664: 660 */           if (local_Request.isRMI()) {
/*  665: 660 */             local_Request.write_value(paramPropertyConfig, PropertyConfig.class);
/*  666:     */           } else {
/*  667: 660 */             PropertyConfigHelper.write(localOutputStream, paramPropertyConfig);
/*  668:     */           }
/*  669: 661 */           if (local_Request.isRMI()) {
/*  670: 661 */             local_Request.write_value(paramArrayOfInstructionType, new InstructionType[0].getClass());
/*  671:     */           } else {
/*  672: 661 */             InstructionTypeSeqHelper.write(localOutputStream, paramArrayOfInstructionType);
/*  673:     */           }
/*  674:     */         }
/*  675:     */         else
/*  676:     */         {
/*  677: 665 */           localOutputStream = local_Request.getOutputStream();
/*  678: 666 */           localLocalFrame.add(paramPropertyConfig);
/*  679: 667 */           localLocalFrame.add(paramArrayOfInstructionType);
/*  680:     */         }
/*  681: 669 */         local_Request.invoke();
/*  682: 670 */         return;
/*  683:     */       }
/*  684:     */       catch (TRANSIENT localTRANSIENT)
/*  685:     */       {
/*  686: 674 */         if (i == 10) {
/*  687: 676 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  688:     */         }
/*  689:     */       }
/*  690:     */       catch (UserException localUserException)
/*  691:     */       {
/*  692: 681 */         if (local_Request.isRMI())
/*  693:     */         {
/*  694: 683 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  695: 685 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  696:     */           }
/*  697:     */         }
/*  698:     */         else
/*  699:     */         {
/*  700: 690 */           Throwable localThrowable = null;
/*  701: 691 */           if (bool1)
/*  702:     */           {
/*  703: 693 */             localThrowable = localLocalFrame.getException();
/*  704: 694 */             if (localThrowable != null) {
/*  705: 696 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/*  706:     */             }
/*  707:     */           }
/*  708: 699 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  709:     */           {
/*  710: 701 */             if (local_Request.isRMI()) {
/*  711: 703 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  712:     */             }
/*  713: 707 */             if (bool1)
/*  714:     */             {
/*  715: 709 */               if (localThrowable != null) {
/*  716: 709 */                 throw ((FFSException)localThrowable);
/*  717:     */               }
/*  718:     */             }
/*  719:     */             else {
/*  720: 713 */               throw FFSExceptionHelper.read(localUserException.input);
/*  721:     */             }
/*  722:     */           }
/*  723:     */         }
/*  724: 718 */         throw new java.rmi.RemoteException(localUserException.type);
/*  725:     */       }
/*  726:     */       catch (SystemException localSystemException)
/*  727:     */       {
/*  728: 722 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  729:     */       }
/*  730:     */       finally
/*  731:     */       {
/*  732: 726 */         if (local_Request != null) {
/*  733: 728 */           local_Request.close();
/*  734:     */         }
/*  735: 730 */         if (bool1) {
/*  736: 731 */           localLocalStack.pop(localLocalFrame);
/*  737:     */         }
/*  738: 732 */         localLocalStack.setArgsOnLocal(bool2);
/*  739:     */       }
/*  740:     */     }
/*  741:     */   }
/*  742:     */   
/*  743:     */   public boolean ping()
/*  744:     */     throws java.rmi.RemoteException, FFSException
/*  745:     */   {
/*  746: 740 */     for (int i = 1;; i++)
/*  747:     */     {
/*  748: 742 */       _Request local_Request = null;
/*  749: 743 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  750: 744 */       boolean bool1 = false;
/*  751:     */       try
/*  752:     */       {
/*  753: 747 */         local_Request = __request("ping");
/*  754: 748 */         bool1 = localLocalStack.isArgsOnLocal();
/*  755: 749 */         localLocalStack.setArgsOnLocal(false);
/*  756: 750 */         local_Request.invoke();
/*  757: 751 */         InputStream localInputStream = local_Request.getInputStream();
/*  758:     */         
/*  759: 753 */         boolean bool3 = localInputStream.read_boolean();
/*  760: 754 */         return bool3;
/*  761:     */       }
/*  762:     */       catch (TRANSIENT localTRANSIENT)
/*  763:     */       {
/*  764: 758 */         if (i == 10) {
/*  765: 760 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  766:     */         }
/*  767:     */       }
/*  768:     */       catch (UserException localUserException)
/*  769:     */       {
/*  770: 765 */         if (local_Request.isRMI())
/*  771:     */         {
/*  772: 767 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  773: 769 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  774:     */           }
/*  775:     */         }
/*  776: 774 */         else if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  777:     */         {
/*  778: 776 */           if (local_Request.isRMI()) {
/*  779: 778 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  780:     */           }
/*  781: 782 */           throw FFSExceptionHelper.read(localUserException.input);
/*  782:     */         }
/*  783: 786 */         throw new java.rmi.RemoteException(localUserException.type);
/*  784:     */       }
/*  785:     */       catch (SystemException localSystemException)
/*  786:     */       {
/*  787: 790 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  788:     */       }
/*  789:     */       finally
/*  790:     */       {
/*  791: 794 */         if (local_Request != null) {
/*  792: 796 */           local_Request.close();
/*  793:     */         }
/*  794: 798 */         localLocalStack.setArgsOnLocal(bool1);
/*  795:     */       }
/*  796:     */     }
/*  797:     */   }
/*  798:     */   
/*  799:     */   public BPWStatistics getStatistics()
/*  800:     */     throws java.rmi.RemoteException, FFSException
/*  801:     */   {
/*  802: 806 */     for (int i = 1;; i++)
/*  803:     */     {
/*  804: 808 */       _Request local_Request = null;
/*  805: 809 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  806: 810 */       boolean bool1 = false;
/*  807: 811 */       LocalFrame localLocalFrame = null;
/*  808: 812 */       boolean bool2 = false;
/*  809:     */       try
/*  810:     */       {
/*  811: 815 */         local_Request = __request("getStatistics");
/*  812: 816 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  813: 817 */         bool2 = localLocalStack.isArgsOnLocal();
/*  814: 818 */         localLocalStack.setArgsOnLocal(bool1);
/*  815: 820 */         if (bool1)
/*  816:     */         {
/*  817: 822 */           localLocalFrame = new LocalFrame(0);
/*  818: 823 */           localLocalStack.push(localLocalFrame);
/*  819:     */         }
/*  820: 831 */         local_Request.invoke();
/*  821:     */         Object localObject1;
/*  822: 832 */         if (bool1)
/*  823:     */         {
/*  824: 834 */           localObject4 = null;
/*  825: 835 */           localObject5 = localLocalFrame.getResult();
/*  826: 836 */           if (localObject5 != null) {
/*  827: 838 */             localObject4 = (BPWStatistics)ObjectVal.clone(localObject5);
/*  828:     */           }
/*  829: 840 */           return localObject4;
/*  830:     */         }
/*  831: 842 */         Object localObject4 = local_Request.getInputStream();
/*  832: 844 */         if (local_Request.isRMI()) {
/*  833: 844 */           localObject5 = (BPWStatistics)local_Request.read_value(BPWStatistics.class);
/*  834:     */         } else {
/*  835: 844 */           localObject5 = BPWStatisticsHelper.read((InputStream)localObject4);
/*  836:     */         }
/*  837: 845 */         return localObject5;
/*  838:     */       }
/*  839:     */       catch (TRANSIENT localTRANSIENT)
/*  840:     */       {
/*  841: 849 */         if (i == 10) {
/*  842: 851 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  843:     */         }
/*  844:     */       }
/*  845:     */       catch (UserException localUserException)
/*  846:     */       {
/*  847:     */         Object localObject5;
/*  848: 856 */         if (local_Request.isRMI())
/*  849:     */         {
/*  850: 858 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  851: 860 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  852:     */           }
/*  853:     */         }
/*  854:     */         else
/*  855:     */         {
/*  856: 865 */           localObject5 = null;
/*  857: 866 */           if (bool1)
/*  858:     */           {
/*  859: 868 */             localObject5 = localLocalFrame.getException();
/*  860: 869 */             if (localObject5 != null) {
/*  861: 871 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  862:     */             }
/*  863:     */           }
/*  864: 874 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  865:     */           {
/*  866: 876 */             if (local_Request.isRMI()) {
/*  867: 878 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  868:     */             }
/*  869: 882 */             if (bool1)
/*  870:     */             {
/*  871: 884 */               if (localObject5 != null) {
/*  872: 884 */                 throw ((FFSException)localObject5);
/*  873:     */               }
/*  874:     */             }
/*  875:     */             else {
/*  876: 888 */               throw FFSExceptionHelper.read(localUserException.input);
/*  877:     */             }
/*  878:     */           }
/*  879:     */         }
/*  880: 893 */         throw new java.rmi.RemoteException(localUserException.type);
/*  881:     */       }
/*  882:     */       catch (SystemException localSystemException)
/*  883:     */       {
/*  884: 897 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  885:     */       }
/*  886:     */       finally
/*  887:     */       {
/*  888: 901 */         if (local_Request != null) {
/*  889: 903 */           local_Request.close();
/*  890:     */         }
/*  891: 905 */         if (bool1) {
/*  892: 906 */           localLocalStack.pop(localLocalFrame);
/*  893:     */         }
/*  894: 907 */         localLocalStack.setArgsOnLocal(bool2);
/*  895:     */       }
/*  896:     */     }
/*  897:     */   }
/*  898:     */   
/*  899:     */   public void refreshSmartCalendar()
/*  900:     */     throws java.rmi.RemoteException, FFSException
/*  901:     */   {
/*  902: 915 */     for (int i = 1;; i++)
/*  903:     */     {
/*  904: 917 */       _Request local_Request = null;
/*  905: 918 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  906: 919 */       boolean bool = false;
/*  907:     */       try
/*  908:     */       {
/*  909: 922 */         local_Request = __request("refreshSmartCalendar");
/*  910: 923 */         bool = localLocalStack.isArgsOnLocal();
/*  911: 924 */         localLocalStack.setArgsOnLocal(false);
/*  912: 925 */         local_Request.invoke();
/*  913: 926 */         return;
/*  914:     */       }
/*  915:     */       catch (TRANSIENT localTRANSIENT)
/*  916:     */       {
/*  917: 930 */         if (i == 10) {
/*  918: 932 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  919:     */         }
/*  920:     */       }
/*  921:     */       catch (UserException localUserException)
/*  922:     */       {
/*  923: 937 */         if (local_Request.isRMI())
/*  924:     */         {
/*  925: 939 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  926: 941 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  927:     */           }
/*  928:     */         }
/*  929: 946 */         else if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  930:     */         {
/*  931: 948 */           if (local_Request.isRMI()) {
/*  932: 950 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  933:     */           }
/*  934: 954 */           throw FFSExceptionHelper.read(localUserException.input);
/*  935:     */         }
/*  936: 958 */         throw new java.rmi.RemoteException(localUserException.type);
/*  937:     */       }
/*  938:     */       catch (SystemException localSystemException)
/*  939:     */       {
/*  940: 962 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  941:     */       }
/*  942:     */       finally
/*  943:     */       {
/*  944: 966 */         if (local_Request != null) {
/*  945: 968 */           local_Request.close();
/*  946:     */         }
/*  947: 970 */         localLocalStack.setArgsOnLocal(bool);
/*  948:     */       }
/*  949:     */     }
/*  950:     */   }
/*  951:     */   
/*  952:     */   public long getFreeMem()
/*  953:     */     throws java.rmi.RemoteException, FFSException
/*  954:     */   {
/*  955: 978 */     for (int i = 1;; i++)
/*  956:     */     {
/*  957: 980 */       _Request local_Request = null;
/*  958: 981 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  959: 982 */       boolean bool = false;
/*  960:     */       try
/*  961:     */       {
/*  962: 985 */         local_Request = __request("getFreeMem");
/*  963: 986 */         bool = localLocalStack.isArgsOnLocal();
/*  964: 987 */         localLocalStack.setArgsOnLocal(false);
/*  965: 988 */         local_Request.invoke();
/*  966: 989 */         InputStream localInputStream = local_Request.getInputStream();
/*  967:     */         
/*  968: 991 */         long l2 = localInputStream.read_longlong();
/*  969: 992 */         return l2;
/*  970:     */       }
/*  971:     */       catch (TRANSIENT localTRANSIENT)
/*  972:     */       {
/*  973: 996 */         if (i == 10) {
/*  974: 998 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  975:     */         }
/*  976:     */       }
/*  977:     */       catch (UserException localUserException)
/*  978:     */       {
/*  979:1003 */         if (local_Request.isRMI())
/*  980:     */         {
/*  981:1005 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  982:1007 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  983:     */           }
/*  984:     */         }
/*  985:1012 */         else if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  986:     */         {
/*  987:1014 */           if (local_Request.isRMI()) {
/*  988:1016 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  989:     */           }
/*  990:1020 */           throw FFSExceptionHelper.read(localUserException.input);
/*  991:     */         }
/*  992:1024 */         throw new java.rmi.RemoteException(localUserException.type);
/*  993:     */       }
/*  994:     */       catch (SystemException localSystemException)
/*  995:     */       {
/*  996:1028 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  997:     */       }
/*  998:     */       finally
/*  999:     */       {
/* 1000:1032 */         if (local_Request != null) {
/* 1001:1034 */           local_Request.close();
/* 1002:     */         }
/* 1003:1036 */         localLocalStack.setArgsOnLocal(bool);
/* 1004:     */       }
/* 1005:     */     }
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public long getTotalMem()
/* 1009:     */     throws java.rmi.RemoteException, FFSException
/* 1010:     */   {
/* 1011:1044 */     for (int i = 1;; i++)
/* 1012:     */     {
/* 1013:1046 */       _Request local_Request = null;
/* 1014:1047 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1015:1048 */       boolean bool = false;
/* 1016:     */       try
/* 1017:     */       {
/* 1018:1051 */         local_Request = __request("getTotalMem");
/* 1019:1052 */         bool = localLocalStack.isArgsOnLocal();
/* 1020:1053 */         localLocalStack.setArgsOnLocal(false);
/* 1021:1054 */         local_Request.invoke();
/* 1022:1055 */         InputStream localInputStream = local_Request.getInputStream();
/* 1023:     */         
/* 1024:1057 */         long l2 = localInputStream.read_longlong();
/* 1025:1058 */         return l2;
/* 1026:     */       }
/* 1027:     */       catch (TRANSIENT localTRANSIENT)
/* 1028:     */       {
/* 1029:1062 */         if (i == 10) {
/* 1030:1064 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1031:     */         }
/* 1032:     */       }
/* 1033:     */       catch (UserException localUserException)
/* 1034:     */       {
/* 1035:1069 */         if (local_Request.isRMI())
/* 1036:     */         {
/* 1037:1071 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1038:1073 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1039:     */           }
/* 1040:     */         }
/* 1041:1078 */         else if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1042:     */         {
/* 1043:1080 */           if (local_Request.isRMI()) {
/* 1044:1082 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1045:     */           }
/* 1046:1086 */           throw FFSExceptionHelper.read(localUserException.input);
/* 1047:     */         }
/* 1048:1090 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1049:     */       }
/* 1050:     */       catch (SystemException localSystemException)
/* 1051:     */       {
/* 1052:1094 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1053:     */       }
/* 1054:     */       finally
/* 1055:     */       {
/* 1056:1098 */         if (local_Request != null) {
/* 1057:1100 */           local_Request.close();
/* 1058:     */         }
/* 1059:1102 */         localLocalStack.setArgsOnLocal(bool);
/* 1060:     */       }
/* 1061:     */     }
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public double getHeapUsage()
/* 1065:     */     throws java.rmi.RemoteException, FFSException
/* 1066:     */   {
/* 1067:1110 */     for (int i = 1;; i++)
/* 1068:     */     {
/* 1069:1112 */       _Request local_Request = null;
/* 1070:1113 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1071:1114 */       boolean bool = false;
/* 1072:     */       try
/* 1073:     */       {
/* 1074:1117 */         local_Request = __request("getHeapUsage");
/* 1075:1118 */         bool = localLocalStack.isArgsOnLocal();
/* 1076:1119 */         localLocalStack.setArgsOnLocal(false);
/* 1077:1120 */         local_Request.invoke();
/* 1078:1121 */         InputStream localInputStream = local_Request.getInputStream();
/* 1079:     */         
/* 1080:1123 */         double d2 = localInputStream.read_double();
/* 1081:1124 */         return d2;
/* 1082:     */       }
/* 1083:     */       catch (TRANSIENT localTRANSIENT)
/* 1084:     */       {
/* 1085:1128 */         if (i == 10) {
/* 1086:1130 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1087:     */         }
/* 1088:     */       }
/* 1089:     */       catch (UserException localUserException)
/* 1090:     */       {
/* 1091:1135 */         if (local_Request.isRMI())
/* 1092:     */         {
/* 1093:1137 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1094:1139 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1095:     */           }
/* 1096:     */         }
/* 1097:1144 */         else if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1098:     */         {
/* 1099:1146 */           if (local_Request.isRMI()) {
/* 1100:1148 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1101:     */           }
/* 1102:1152 */           throw FFSExceptionHelper.read(localUserException.input);
/* 1103:     */         }
/* 1104:1156 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1105:     */       }
/* 1106:     */       catch (SystemException localSystemException)
/* 1107:     */       {
/* 1108:1160 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1109:     */       }
/* 1110:     */       finally
/* 1111:     */       {
/* 1112:1164 */         if (local_Request != null) {
/* 1113:1166 */           local_Request.close();
/* 1114:     */         }
/* 1115:1168 */         localLocalStack.setArgsOnLocal(bool);
/* 1116:     */       }
/* 1117:     */     }
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public void resubmitEvent(String paramString1, String paramString2, String paramString3)
/* 1121:     */     throws java.rmi.RemoteException, FFSException
/* 1122:     */   {
/* 1123:1179 */     for (int i = 1;; i++)
/* 1124:     */     {
/* 1125:1181 */       _Request local_Request = null;
/* 1126:1182 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1127:1183 */       boolean bool1 = false;
/* 1128:1184 */       LocalFrame localLocalFrame = null;
/* 1129:1185 */       boolean bool2 = false;
/* 1130:     */       try
/* 1131:     */       {
/* 1132:1188 */         local_Request = __request("resubmitEvent__string__string__string", "resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue");
/* 1133:1189 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1134:1190 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1135:1191 */         localLocalStack.setArgsOnLocal(bool1);
/* 1136:1193 */         if (bool1)
/* 1137:     */         {
/* 1138:1195 */           localLocalFrame = new LocalFrame(3);
/* 1139:1196 */           localLocalStack.push(localLocalFrame);
/* 1140:     */         }
/* 1141:     */         OutputStream localOutputStream;
/* 1142:1198 */         if (!bool1)
/* 1143:     */         {
/* 1144:1200 */           localOutputStream = local_Request.getOutputStream();
/* 1145:1201 */           local_Request.write_string(paramString1);
/* 1146:1202 */           local_Request.write_string(paramString2);
/* 1147:1203 */           local_Request.write_string(paramString3);
/* 1148:     */         }
/* 1149:     */         else
/* 1150:     */         {
/* 1151:1207 */           localOutputStream = local_Request.getOutputStream();
/* 1152:1208 */           localLocalFrame.add(paramString1);
/* 1153:1209 */           localLocalFrame.add(paramString2);
/* 1154:1210 */           localLocalFrame.add(paramString3);
/* 1155:     */         }
/* 1156:1212 */         local_Request.invoke();
/* 1157:1213 */         return;
/* 1158:     */       }
/* 1159:     */       catch (TRANSIENT localTRANSIENT)
/* 1160:     */       {
/* 1161:1217 */         if (i == 10) {
/* 1162:1219 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1163:     */         }
/* 1164:     */       }
/* 1165:     */       catch (UserException localUserException)
/* 1166:     */       {
/* 1167:1224 */         if (local_Request.isRMI())
/* 1168:     */         {
/* 1169:1226 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1170:1228 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1171:     */           }
/* 1172:     */         }
/* 1173:     */         else
/* 1174:     */         {
/* 1175:1233 */           Throwable localThrowable = null;
/* 1176:1234 */           if (bool1)
/* 1177:     */           {
/* 1178:1236 */             localThrowable = localLocalFrame.getException();
/* 1179:1237 */             if (localThrowable != null) {
/* 1180:1239 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 1181:     */             }
/* 1182:     */           }
/* 1183:1242 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1184:     */           {
/* 1185:1244 */             if (local_Request.isRMI()) {
/* 1186:1246 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1187:     */             }
/* 1188:1250 */             if (bool1)
/* 1189:     */             {
/* 1190:1252 */               if (localThrowable != null) {
/* 1191:1252 */                 throw ((FFSException)localThrowable);
/* 1192:     */               }
/* 1193:     */             }
/* 1194:     */             else {
/* 1195:1256 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1196:     */             }
/* 1197:     */           }
/* 1198:     */         }
/* 1199:1261 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1200:     */       }
/* 1201:     */       catch (SystemException localSystemException)
/* 1202:     */       {
/* 1203:1265 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1204:     */       }
/* 1205:     */       finally
/* 1206:     */       {
/* 1207:1269 */         if (local_Request != null) {
/* 1208:1271 */           local_Request.close();
/* 1209:     */         }
/* 1210:1273 */         if (bool1) {
/* 1211:1274 */           localLocalStack.pop(localLocalFrame);
/* 1212:     */         }
/* 1213:1275 */         localLocalStack.setArgsOnLocal(bool2);
/* 1214:     */       }
/* 1215:     */     }
/* 1216:     */   }
/* 1217:     */   
/* 1218:     */   public void resubmitEvent(String paramString1, String paramString2)
/* 1219:     */     throws java.rmi.RemoteException, FFSException
/* 1220:     */   {
/* 1221:1285 */     for (int i = 1;; i++)
/* 1222:     */     {
/* 1223:1287 */       _Request local_Request = null;
/* 1224:1288 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1225:1289 */       boolean bool1 = false;
/* 1226:1290 */       LocalFrame localLocalFrame = null;
/* 1227:1291 */       boolean bool2 = false;
/* 1228:     */       try
/* 1229:     */       {
/* 1230:1294 */         local_Request = __request("resubmitEvent__string__string", "resubmitEvent__CORBA_WStringValue__CORBA_WStringValue");
/* 1231:1295 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1232:1296 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1233:1297 */         localLocalStack.setArgsOnLocal(bool1);
/* 1234:1299 */         if (bool1)
/* 1235:     */         {
/* 1236:1301 */           localLocalFrame = new LocalFrame(2);
/* 1237:1302 */           localLocalStack.push(localLocalFrame);
/* 1238:     */         }
/* 1239:     */         OutputStream localOutputStream;
/* 1240:1304 */         if (!bool1)
/* 1241:     */         {
/* 1242:1306 */           localOutputStream = local_Request.getOutputStream();
/* 1243:1307 */           local_Request.write_string(paramString1);
/* 1244:1308 */           local_Request.write_string(paramString2);
/* 1245:     */         }
/* 1246:     */         else
/* 1247:     */         {
/* 1248:1312 */           localOutputStream = local_Request.getOutputStream();
/* 1249:1313 */           localLocalFrame.add(paramString1);
/* 1250:1314 */           localLocalFrame.add(paramString2);
/* 1251:     */         }
/* 1252:1316 */         local_Request.invoke();
/* 1253:1317 */         return;
/* 1254:     */       }
/* 1255:     */       catch (TRANSIENT localTRANSIENT)
/* 1256:     */       {
/* 1257:1321 */         if (i == 10) {
/* 1258:1323 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1259:     */         }
/* 1260:     */       }
/* 1261:     */       catch (UserException localUserException)
/* 1262:     */       {
/* 1263:1328 */         if (local_Request.isRMI())
/* 1264:     */         {
/* 1265:1330 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1266:1332 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1267:     */           }
/* 1268:     */         }
/* 1269:     */         else
/* 1270:     */         {
/* 1271:1337 */           Throwable localThrowable = null;
/* 1272:1338 */           if (bool1)
/* 1273:     */           {
/* 1274:1340 */             localThrowable = localLocalFrame.getException();
/* 1275:1341 */             if (localThrowable != null) {
/* 1276:1343 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 1277:     */             }
/* 1278:     */           }
/* 1279:1346 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1280:     */           {
/* 1281:1348 */             if (local_Request.isRMI()) {
/* 1282:1350 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1283:     */             }
/* 1284:1354 */             if (bool1)
/* 1285:     */             {
/* 1286:1356 */               if (localThrowable != null) {
/* 1287:1356 */                 throw ((FFSException)localThrowable);
/* 1288:     */               }
/* 1289:     */             }
/* 1290:     */             else {
/* 1291:1360 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1292:     */             }
/* 1293:     */           }
/* 1294:     */         }
/* 1295:1365 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1296:     */       }
/* 1297:     */       catch (SystemException localSystemException)
/* 1298:     */       {
/* 1299:1369 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1300:     */       }
/* 1301:     */       finally
/* 1302:     */       {
/* 1303:1373 */         if (local_Request != null) {
/* 1304:1375 */           local_Request.close();
/* 1305:     */         }
/* 1306:1377 */         if (bool1) {
/* 1307:1378 */           localLocalStack.pop(localLocalFrame);
/* 1308:     */         }
/* 1309:1379 */         localLocalStack.setArgsOnLocal(bool2);
/* 1310:     */       }
/* 1311:     */     }
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   public void resubmitEvent(String paramString1, String paramString2, String paramString3, String paramString4)
/* 1315:     */     throws java.rmi.RemoteException, FFSException
/* 1316:     */   {
/* 1317:1391 */     for (int i = 1;; i++)
/* 1318:     */     {
/* 1319:1393 */       _Request local_Request = null;
/* 1320:1394 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1321:1395 */       boolean bool1 = false;
/* 1322:1396 */       LocalFrame localLocalFrame = null;
/* 1323:1397 */       boolean bool2 = false;
/* 1324:     */       try
/* 1325:     */       {
/* 1326:1400 */         local_Request = __request("resubmitEvent__string__string__string__string", "resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue");
/* 1327:1401 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1328:1402 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1329:1403 */         localLocalStack.setArgsOnLocal(bool1);
/* 1330:1405 */         if (bool1)
/* 1331:     */         {
/* 1332:1407 */           localLocalFrame = new LocalFrame(4);
/* 1333:1408 */           localLocalStack.push(localLocalFrame);
/* 1334:     */         }
/* 1335:     */         OutputStream localOutputStream;
/* 1336:1410 */         if (!bool1)
/* 1337:     */         {
/* 1338:1412 */           localOutputStream = local_Request.getOutputStream();
/* 1339:1413 */           local_Request.write_string(paramString1);
/* 1340:1414 */           local_Request.write_string(paramString2);
/* 1341:1415 */           local_Request.write_string(paramString3);
/* 1342:1416 */           local_Request.write_string(paramString4);
/* 1343:     */         }
/* 1344:     */         else
/* 1345:     */         {
/* 1346:1420 */           localOutputStream = local_Request.getOutputStream();
/* 1347:1421 */           localLocalFrame.add(paramString1);
/* 1348:1422 */           localLocalFrame.add(paramString2);
/* 1349:1423 */           localLocalFrame.add(paramString3);
/* 1350:1424 */           localLocalFrame.add(paramString4);
/* 1351:     */         }
/* 1352:1426 */         local_Request.invoke();
/* 1353:1427 */         return;
/* 1354:     */       }
/* 1355:     */       catch (TRANSIENT localTRANSIENT)
/* 1356:     */       {
/* 1357:1431 */         if (i == 10) {
/* 1358:1433 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1359:     */         }
/* 1360:     */       }
/* 1361:     */       catch (UserException localUserException)
/* 1362:     */       {
/* 1363:1438 */         if (local_Request.isRMI())
/* 1364:     */         {
/* 1365:1440 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1366:1442 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1367:     */           }
/* 1368:     */         }
/* 1369:     */         else
/* 1370:     */         {
/* 1371:1447 */           Throwable localThrowable = null;
/* 1372:1448 */           if (bool1)
/* 1373:     */           {
/* 1374:1450 */             localThrowable = localLocalFrame.getException();
/* 1375:1451 */             if (localThrowable != null) {
/* 1376:1453 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 1377:     */             }
/* 1378:     */           }
/* 1379:1456 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1380:     */           {
/* 1381:1458 */             if (local_Request.isRMI()) {
/* 1382:1460 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1383:     */             }
/* 1384:1464 */             if (bool1)
/* 1385:     */             {
/* 1386:1466 */               if (localThrowable != null) {
/* 1387:1466 */                 throw ((FFSException)localThrowable);
/* 1388:     */               }
/* 1389:     */             }
/* 1390:     */             else {
/* 1391:1470 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1392:     */             }
/* 1393:     */           }
/* 1394:     */         }
/* 1395:1475 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1396:     */       }
/* 1397:     */       catch (SystemException localSystemException)
/* 1398:     */       {
/* 1399:1479 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1400:     */       }
/* 1401:     */       finally
/* 1402:     */       {
/* 1403:1483 */         if (local_Request != null) {
/* 1404:1485 */           local_Request.close();
/* 1405:     */         }
/* 1406:1487 */         if (bool1) {
/* 1407:1488 */           localLocalStack.pop(localLocalFrame);
/* 1408:     */         }
/* 1409:1489 */         localLocalStack.setArgsOnLocal(bool2);
/* 1410:     */       }
/* 1411:     */     }
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   public String startScheduler()
/* 1415:     */     throws java.rmi.RemoteException, FFSException
/* 1416:     */   {
/* 1417:1497 */     for (int i = 1;; i++)
/* 1418:     */     {
/* 1419:1499 */       _Request local_Request = null;
/* 1420:1500 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1421:1501 */       boolean bool1 = false;
/* 1422:1502 */       LocalFrame localLocalFrame = null;
/* 1423:1503 */       boolean bool2 = false;
/* 1424:     */       try
/* 1425:     */       {
/* 1426:1506 */         local_Request = __request("startScheduler");
/* 1427:1507 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1428:1508 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1429:1509 */         localLocalStack.setArgsOnLocal(bool1);
/* 1430:1511 */         if (bool1)
/* 1431:     */         {
/* 1432:1513 */           localLocalFrame = new LocalFrame(0);
/* 1433:1514 */           localLocalStack.push(localLocalFrame);
/* 1434:     */         }
/* 1435:1522 */         local_Request.invoke();
/* 1436:     */         Object localObject1;
/* 1437:1523 */         if (bool1)
/* 1438:     */         {
/* 1439:1525 */           localObject4 = null;
/* 1440:1526 */           localObject4 = (String)localLocalFrame.getResult();
/* 1441:1527 */           return localObject4;
/* 1442:     */         }
/* 1443:1529 */         Object localObject4 = local_Request.getInputStream();
/* 1444:     */         
/* 1445:1531 */         localObject5 = local_Request.read_string();
/* 1446:1532 */         return localObject5;
/* 1447:     */       }
/* 1448:     */       catch (TRANSIENT localTRANSIENT)
/* 1449:     */       {
/* 1450:1536 */         if (i == 10) {
/* 1451:1538 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1452:     */         }
/* 1453:     */       }
/* 1454:     */       catch (UserException localUserException)
/* 1455:     */       {
/* 1456:     */         Object localObject5;
/* 1457:1543 */         if (local_Request.isRMI())
/* 1458:     */         {
/* 1459:1545 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1460:1547 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1461:     */           }
/* 1462:     */         }
/* 1463:     */         else
/* 1464:     */         {
/* 1465:1552 */           localObject5 = null;
/* 1466:1553 */           if (bool1)
/* 1467:     */           {
/* 1468:1555 */             localObject5 = localLocalFrame.getException();
/* 1469:1556 */             if (localObject5 != null) {
/* 1470:1558 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 1471:     */             }
/* 1472:     */           }
/* 1473:1561 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1474:     */           {
/* 1475:1563 */             if (local_Request.isRMI()) {
/* 1476:1565 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1477:     */             }
/* 1478:1569 */             if (bool1)
/* 1479:     */             {
/* 1480:1571 */               if (localObject5 != null) {
/* 1481:1571 */                 throw ((FFSException)localObject5);
/* 1482:     */               }
/* 1483:     */             }
/* 1484:     */             else {
/* 1485:1575 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1486:     */             }
/* 1487:     */           }
/* 1488:     */         }
/* 1489:1580 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1490:     */       }
/* 1491:     */       catch (SystemException localSystemException)
/* 1492:     */       {
/* 1493:1584 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1494:     */       }
/* 1495:     */       finally
/* 1496:     */       {
/* 1497:1588 */         if (local_Request != null) {
/* 1498:1590 */           local_Request.close();
/* 1499:     */         }
/* 1500:1592 */         if (bool1) {
/* 1501:1593 */           localLocalStack.pop(localLocalFrame);
/* 1502:     */         }
/* 1503:1594 */         localLocalStack.setArgsOnLocal(bool2);
/* 1504:     */       }
/* 1505:     */     }
/* 1506:     */   }
/* 1507:     */   
/* 1508:     */   public String stopScheduler()
/* 1509:     */     throws java.rmi.RemoteException, FFSException
/* 1510:     */   {
/* 1511:1602 */     for (int i = 1;; i++)
/* 1512:     */     {
/* 1513:1604 */       _Request local_Request = null;
/* 1514:1605 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1515:1606 */       boolean bool1 = false;
/* 1516:1607 */       LocalFrame localLocalFrame = null;
/* 1517:1608 */       boolean bool2 = false;
/* 1518:     */       try
/* 1519:     */       {
/* 1520:1611 */         local_Request = __request("stopScheduler");
/* 1521:1612 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1522:1613 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1523:1614 */         localLocalStack.setArgsOnLocal(bool1);
/* 1524:1616 */         if (bool1)
/* 1525:     */         {
/* 1526:1618 */           localLocalFrame = new LocalFrame(0);
/* 1527:1619 */           localLocalStack.push(localLocalFrame);
/* 1528:     */         }
/* 1529:1627 */         local_Request.invoke();
/* 1530:     */         Object localObject1;
/* 1531:1628 */         if (bool1)
/* 1532:     */         {
/* 1533:1630 */           localObject4 = null;
/* 1534:1631 */           localObject4 = (String)localLocalFrame.getResult();
/* 1535:1632 */           return localObject4;
/* 1536:     */         }
/* 1537:1634 */         Object localObject4 = local_Request.getInputStream();
/* 1538:     */         
/* 1539:1636 */         localObject5 = local_Request.read_string();
/* 1540:1637 */         return localObject5;
/* 1541:     */       }
/* 1542:     */       catch (TRANSIENT localTRANSIENT)
/* 1543:     */       {
/* 1544:1641 */         if (i == 10) {
/* 1545:1643 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1546:     */         }
/* 1547:     */       }
/* 1548:     */       catch (UserException localUserException)
/* 1549:     */       {
/* 1550:     */         Object localObject5;
/* 1551:1648 */         if (local_Request.isRMI())
/* 1552:     */         {
/* 1553:1650 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1554:1652 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1555:     */           }
/* 1556:     */         }
/* 1557:     */         else
/* 1558:     */         {
/* 1559:1657 */           localObject5 = null;
/* 1560:1658 */           if (bool1)
/* 1561:     */           {
/* 1562:1660 */             localObject5 = localLocalFrame.getException();
/* 1563:1661 */             if (localObject5 != null) {
/* 1564:1663 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 1565:     */             }
/* 1566:     */           }
/* 1567:1666 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1568:     */           {
/* 1569:1668 */             if (local_Request.isRMI()) {
/* 1570:1670 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1571:     */             }
/* 1572:1674 */             if (bool1)
/* 1573:     */             {
/* 1574:1676 */               if (localObject5 != null) {
/* 1575:1676 */                 throw ((FFSException)localObject5);
/* 1576:     */               }
/* 1577:     */             }
/* 1578:     */             else {
/* 1579:1680 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1580:     */             }
/* 1581:     */           }
/* 1582:     */         }
/* 1583:1685 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1584:     */       }
/* 1585:     */       catch (SystemException localSystemException)
/* 1586:     */       {
/* 1587:1689 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1588:     */       }
/* 1589:     */       finally
/* 1590:     */       {
/* 1591:1693 */         if (local_Request != null) {
/* 1592:1695 */           local_Request.close();
/* 1593:     */         }
/* 1594:1697 */         if (bool1) {
/* 1595:1698 */           localLocalStack.pop(localLocalFrame);
/* 1596:     */         }
/* 1597:1699 */         localLocalStack.setArgsOnLocal(bool2);
/* 1598:     */       }
/* 1599:     */     }
/* 1600:     */   }
/* 1601:     */   
/* 1602:     */   public String refreshScheduler()
/* 1603:     */     throws java.rmi.RemoteException, FFSException
/* 1604:     */   {
/* 1605:1707 */     for (int i = 1;; i++)
/* 1606:     */     {
/* 1607:1709 */       _Request local_Request = null;
/* 1608:1710 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1609:1711 */       boolean bool1 = false;
/* 1610:1712 */       LocalFrame localLocalFrame = null;
/* 1611:1713 */       boolean bool2 = false;
/* 1612:     */       try
/* 1613:     */       {
/* 1614:1716 */         local_Request = __request("refreshScheduler");
/* 1615:1717 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1616:1718 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1617:1719 */         localLocalStack.setArgsOnLocal(bool1);
/* 1618:1721 */         if (bool1)
/* 1619:     */         {
/* 1620:1723 */           localLocalFrame = new LocalFrame(0);
/* 1621:1724 */           localLocalStack.push(localLocalFrame);
/* 1622:     */         }
/* 1623:1732 */         local_Request.invoke();
/* 1624:     */         Object localObject1;
/* 1625:1733 */         if (bool1)
/* 1626:     */         {
/* 1627:1735 */           localObject4 = null;
/* 1628:1736 */           localObject4 = (String)localLocalFrame.getResult();
/* 1629:1737 */           return localObject4;
/* 1630:     */         }
/* 1631:1739 */         Object localObject4 = local_Request.getInputStream();
/* 1632:     */         
/* 1633:1741 */         localObject5 = local_Request.read_string();
/* 1634:1742 */         return localObject5;
/* 1635:     */       }
/* 1636:     */       catch (TRANSIENT localTRANSIENT)
/* 1637:     */       {
/* 1638:1746 */         if (i == 10) {
/* 1639:1748 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1640:     */         }
/* 1641:     */       }
/* 1642:     */       catch (UserException localUserException)
/* 1643:     */       {
/* 1644:     */         Object localObject5;
/* 1645:1753 */         if (local_Request.isRMI())
/* 1646:     */         {
/* 1647:1755 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1648:1757 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1649:     */           }
/* 1650:     */         }
/* 1651:     */         else
/* 1652:     */         {
/* 1653:1762 */           localObject5 = null;
/* 1654:1763 */           if (bool1)
/* 1655:     */           {
/* 1656:1765 */             localObject5 = localLocalFrame.getException();
/* 1657:1766 */             if (localObject5 != null) {
/* 1658:1768 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 1659:     */             }
/* 1660:     */           }
/* 1661:1771 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1662:     */           {
/* 1663:1773 */             if (local_Request.isRMI()) {
/* 1664:1775 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1665:     */             }
/* 1666:1779 */             if (bool1)
/* 1667:     */             {
/* 1668:1781 */               if (localObject5 != null) {
/* 1669:1781 */                 throw ((FFSException)localObject5);
/* 1670:     */               }
/* 1671:     */             }
/* 1672:     */             else {
/* 1673:1785 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1674:     */             }
/* 1675:     */           }
/* 1676:     */         }
/* 1677:1790 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1678:     */       }
/* 1679:     */       catch (SystemException localSystemException)
/* 1680:     */       {
/* 1681:1794 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1682:     */       }
/* 1683:     */       finally
/* 1684:     */       {
/* 1685:1798 */         if (local_Request != null) {
/* 1686:1800 */           local_Request.close();
/* 1687:     */         }
/* 1688:1802 */         if (bool1) {
/* 1689:1803 */           localLocalStack.pop(localLocalFrame);
/* 1690:     */         }
/* 1691:1804 */         localLocalStack.setArgsOnLocal(bool2);
/* 1692:     */       }
/* 1693:     */     }
/* 1694:     */   }
/* 1695:     */   
/* 1696:     */   public void registerPropertyConfig(PropertyConfig paramPropertyConfig)
/* 1697:     */     throws java.rmi.RemoteException, FFSException
/* 1698:     */   {
/* 1699:1813 */     for (int i = 1;; i++)
/* 1700:     */     {
/* 1701:1815 */       _Request local_Request = null;
/* 1702:1816 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1703:1817 */       boolean bool1 = false;
/* 1704:1818 */       LocalFrame localLocalFrame = null;
/* 1705:1819 */       boolean bool2 = false;
/* 1706:     */       try
/* 1707:     */       {
/* 1708:1822 */         local_Request = __request("registerPropertyConfig");
/* 1709:1823 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1710:1824 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1711:1825 */         localLocalStack.setArgsOnLocal(bool1);
/* 1712:1827 */         if (bool1)
/* 1713:     */         {
/* 1714:1829 */           localLocalFrame = new LocalFrame(1);
/* 1715:1830 */           localLocalStack.push(localLocalFrame);
/* 1716:     */         }
/* 1717:     */         OutputStream localOutputStream;
/* 1718:1832 */         if (!bool1)
/* 1719:     */         {
/* 1720:1834 */           localOutputStream = local_Request.getOutputStream();
/* 1721:1835 */           if (local_Request.isRMI()) {
/* 1722:1835 */             local_Request.write_value(paramPropertyConfig, PropertyConfig.class);
/* 1723:     */           } else {
/* 1724:1835 */             PropertyConfigHelper.write(localOutputStream, paramPropertyConfig);
/* 1725:     */           }
/* 1726:     */         }
/* 1727:     */         else
/* 1728:     */         {
/* 1729:1839 */           localOutputStream = local_Request.getOutputStream();
/* 1730:1840 */           localLocalFrame.add(paramPropertyConfig);
/* 1731:     */         }
/* 1732:1842 */         local_Request.invoke();
/* 1733:1843 */         return;
/* 1734:     */       }
/* 1735:     */       catch (TRANSIENT localTRANSIENT)
/* 1736:     */       {
/* 1737:1847 */         if (i == 10) {
/* 1738:1849 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1739:     */         }
/* 1740:     */       }
/* 1741:     */       catch (UserException localUserException)
/* 1742:     */       {
/* 1743:1854 */         if (local_Request.isRMI())
/* 1744:     */         {
/* 1745:1856 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1746:1858 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1747:     */           }
/* 1748:     */         }
/* 1749:     */         else
/* 1750:     */         {
/* 1751:1863 */           Throwable localThrowable = null;
/* 1752:1864 */           if (bool1)
/* 1753:     */           {
/* 1754:1866 */             localThrowable = localLocalFrame.getException();
/* 1755:1867 */             if (localThrowable != null) {
/* 1756:1869 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 1757:     */             }
/* 1758:     */           }
/* 1759:1872 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1760:     */           {
/* 1761:1874 */             if (local_Request.isRMI()) {
/* 1762:1876 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1763:     */             }
/* 1764:1880 */             if (bool1)
/* 1765:     */             {
/* 1766:1882 */               if (localThrowable != null) {
/* 1767:1882 */                 throw ((FFSException)localThrowable);
/* 1768:     */               }
/* 1769:     */             }
/* 1770:     */             else {
/* 1771:1886 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1772:     */             }
/* 1773:     */           }
/* 1774:     */         }
/* 1775:1891 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1776:     */       }
/* 1777:     */       catch (SystemException localSystemException)
/* 1778:     */       {
/* 1779:1895 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1780:     */       }
/* 1781:     */       finally
/* 1782:     */       {
/* 1783:1899 */         if (local_Request != null) {
/* 1784:1901 */           local_Request.close();
/* 1785:     */         }
/* 1786:1903 */         if (bool1) {
/* 1787:1904 */           localLocalStack.pop(localLocalFrame);
/* 1788:     */         }
/* 1789:1905 */         localLocalStack.setArgsOnLocal(bool2);
/* 1790:     */       }
/* 1791:     */     }
/* 1792:     */   }
/* 1793:     */   
/* 1794:     */   public void startEngine(String paramString)
/* 1795:     */     throws java.rmi.RemoteException, FFSException
/* 1796:     */   {
/* 1797:1914 */     for (int i = 1;; i++)
/* 1798:     */     {
/* 1799:1916 */       _Request local_Request = null;
/* 1800:1917 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1801:1918 */       boolean bool1 = false;
/* 1802:1919 */       LocalFrame localLocalFrame = null;
/* 1803:1920 */       boolean bool2 = false;
/* 1804:     */       try
/* 1805:     */       {
/* 1806:1923 */         local_Request = __request("startEngine");
/* 1807:1924 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1808:1925 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1809:1926 */         localLocalStack.setArgsOnLocal(bool1);
/* 1810:1928 */         if (bool1)
/* 1811:     */         {
/* 1812:1930 */           localLocalFrame = new LocalFrame(1);
/* 1813:1931 */           localLocalStack.push(localLocalFrame);
/* 1814:     */         }
/* 1815:     */         OutputStream localOutputStream;
/* 1816:1933 */         if (!bool1)
/* 1817:     */         {
/* 1818:1935 */           localOutputStream = local_Request.getOutputStream();
/* 1819:1936 */           local_Request.write_string(paramString);
/* 1820:     */         }
/* 1821:     */         else
/* 1822:     */         {
/* 1823:1940 */           localOutputStream = local_Request.getOutputStream();
/* 1824:1941 */           localLocalFrame.add(paramString);
/* 1825:     */         }
/* 1826:1943 */         local_Request.invoke();
/* 1827:1944 */         return;
/* 1828:     */       }
/* 1829:     */       catch (TRANSIENT localTRANSIENT)
/* 1830:     */       {
/* 1831:1948 */         if (i == 10) {
/* 1832:1950 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1833:     */         }
/* 1834:     */       }
/* 1835:     */       catch (UserException localUserException)
/* 1836:     */       {
/* 1837:1955 */         if (local_Request.isRMI())
/* 1838:     */         {
/* 1839:1957 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1840:1959 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1841:     */           }
/* 1842:     */         }
/* 1843:     */         else
/* 1844:     */         {
/* 1845:1964 */           Throwable localThrowable = null;
/* 1846:1965 */           if (bool1)
/* 1847:     */           {
/* 1848:1967 */             localThrowable = localLocalFrame.getException();
/* 1849:1968 */             if (localThrowable != null) {
/* 1850:1970 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 1851:     */             }
/* 1852:     */           }
/* 1853:1973 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1854:     */           {
/* 1855:1975 */             if (local_Request.isRMI()) {
/* 1856:1977 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1857:     */             }
/* 1858:1981 */             if (bool1)
/* 1859:     */             {
/* 1860:1983 */               if (localThrowable != null) {
/* 1861:1983 */                 throw ((FFSException)localThrowable);
/* 1862:     */               }
/* 1863:     */             }
/* 1864:     */             else {
/* 1865:1987 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1866:     */             }
/* 1867:     */           }
/* 1868:     */         }
/* 1869:1992 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1870:     */       }
/* 1871:     */       catch (SystemException localSystemException)
/* 1872:     */       {
/* 1873:1996 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1874:     */       }
/* 1875:     */       finally
/* 1876:     */       {
/* 1877:2000 */         if (local_Request != null) {
/* 1878:2002 */           local_Request.close();
/* 1879:     */         }
/* 1880:2004 */         if (bool1) {
/* 1881:2005 */           localLocalStack.pop(localLocalFrame);
/* 1882:     */         }
/* 1883:2006 */         localLocalStack.setArgsOnLocal(bool2);
/* 1884:     */       }
/* 1885:     */     }
/* 1886:     */   }
/* 1887:     */   
/* 1888:     */   public void stopEngine(String paramString)
/* 1889:     */     throws java.rmi.RemoteException, FFSException
/* 1890:     */   {
/* 1891:2015 */     for (int i = 1;; i++)
/* 1892:     */     {
/* 1893:2017 */       _Request local_Request = null;
/* 1894:2018 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1895:2019 */       boolean bool1 = false;
/* 1896:2020 */       LocalFrame localLocalFrame = null;
/* 1897:2021 */       boolean bool2 = false;
/* 1898:     */       try
/* 1899:     */       {
/* 1900:2024 */         local_Request = __request("stopEngine");
/* 1901:2025 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1902:2026 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1903:2027 */         localLocalStack.setArgsOnLocal(bool1);
/* 1904:2029 */         if (bool1)
/* 1905:     */         {
/* 1906:2031 */           localLocalFrame = new LocalFrame(1);
/* 1907:2032 */           localLocalStack.push(localLocalFrame);
/* 1908:     */         }
/* 1909:     */         OutputStream localOutputStream;
/* 1910:2034 */         if (!bool1)
/* 1911:     */         {
/* 1912:2036 */           localOutputStream = local_Request.getOutputStream();
/* 1913:2037 */           local_Request.write_string(paramString);
/* 1914:     */         }
/* 1915:     */         else
/* 1916:     */         {
/* 1917:2041 */           localOutputStream = local_Request.getOutputStream();
/* 1918:2042 */           localLocalFrame.add(paramString);
/* 1919:     */         }
/* 1920:2044 */         local_Request.invoke();
/* 1921:2045 */         return;
/* 1922:     */       }
/* 1923:     */       catch (TRANSIENT localTRANSIENT)
/* 1924:     */       {
/* 1925:2049 */         if (i == 10) {
/* 1926:2051 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1927:     */         }
/* 1928:     */       }
/* 1929:     */       catch (UserException localUserException)
/* 1930:     */       {
/* 1931:2056 */         if (local_Request.isRMI())
/* 1932:     */         {
/* 1933:2058 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 1934:2060 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1935:     */           }
/* 1936:     */         }
/* 1937:     */         else
/* 1938:     */         {
/* 1939:2065 */           Throwable localThrowable = null;
/* 1940:2066 */           if (bool1)
/* 1941:     */           {
/* 1942:2068 */             localThrowable = localLocalFrame.getException();
/* 1943:2069 */             if (localThrowable != null) {
/* 1944:2071 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 1945:     */             }
/* 1946:     */           }
/* 1947:2074 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 1948:     */           {
/* 1949:2076 */             if (local_Request.isRMI()) {
/* 1950:2078 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 1951:     */             }
/* 1952:2082 */             if (bool1)
/* 1953:     */             {
/* 1954:2084 */               if (localThrowable != null) {
/* 1955:2084 */                 throw ((FFSException)localThrowable);
/* 1956:     */               }
/* 1957:     */             }
/* 1958:     */             else {
/* 1959:2088 */               throw FFSExceptionHelper.read(localUserException.input);
/* 1960:     */             }
/* 1961:     */           }
/* 1962:     */         }
/* 1963:2093 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1964:     */       }
/* 1965:     */       catch (SystemException localSystemException)
/* 1966:     */       {
/* 1967:2097 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1968:     */       }
/* 1969:     */       finally
/* 1970:     */       {
/* 1971:2101 */         if (local_Request != null) {
/* 1972:2103 */           local_Request.close();
/* 1973:     */         }
/* 1974:2105 */         if (bool1) {
/* 1975:2106 */           localLocalStack.pop(localLocalFrame);
/* 1976:     */         }
/* 1977:2107 */         localLocalStack.setArgsOnLocal(bool2);
/* 1978:     */       }
/* 1979:     */     }
/* 1980:     */   }
/* 1981:     */   
/* 1982:     */   public void stopLimitCheckApprovalProcessor(String paramString)
/* 1983:     */     throws java.rmi.RemoteException, FFSException
/* 1984:     */   {
/* 1985:2116 */     for (int i = 1;; i++)
/* 1986:     */     {
/* 1987:2118 */       _Request local_Request = null;
/* 1988:2119 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1989:2120 */       boolean bool1 = false;
/* 1990:2121 */       LocalFrame localLocalFrame = null;
/* 1991:2122 */       boolean bool2 = false;
/* 1992:     */       try
/* 1993:     */       {
/* 1994:2125 */         local_Request = __request("stopLimitCheckApprovalProcessor");
/* 1995:2126 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1996:2127 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1997:2128 */         localLocalStack.setArgsOnLocal(bool1);
/* 1998:2130 */         if (bool1)
/* 1999:     */         {
/* 2000:2132 */           localLocalFrame = new LocalFrame(1);
/* 2001:2133 */           localLocalStack.push(localLocalFrame);
/* 2002:     */         }
/* 2003:     */         OutputStream localOutputStream;
/* 2004:2135 */         if (!bool1)
/* 2005:     */         {
/* 2006:2137 */           localOutputStream = local_Request.getOutputStream();
/* 2007:2138 */           local_Request.write_string(paramString);
/* 2008:     */         }
/* 2009:     */         else
/* 2010:     */         {
/* 2011:2142 */           localOutputStream = local_Request.getOutputStream();
/* 2012:2143 */           localLocalFrame.add(paramString);
/* 2013:     */         }
/* 2014:2145 */         local_Request.invoke();
/* 2015:2146 */         return;
/* 2016:     */       }
/* 2017:     */       catch (TRANSIENT localTRANSIENT)
/* 2018:     */       {
/* 2019:2150 */         if (i == 10) {
/* 2020:2152 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2021:     */         }
/* 2022:     */       }
/* 2023:     */       catch (UserException localUserException)
/* 2024:     */       {
/* 2025:2157 */         if (local_Request.isRMI())
/* 2026:     */         {
/* 2027:2159 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2028:2161 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2029:     */           }
/* 2030:     */         }
/* 2031:     */         else
/* 2032:     */         {
/* 2033:2166 */           Throwable localThrowable = null;
/* 2034:2167 */           if (bool1)
/* 2035:     */           {
/* 2036:2169 */             localThrowable = localLocalFrame.getException();
/* 2037:2170 */             if (localThrowable != null) {
/* 2038:2172 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 2039:     */             }
/* 2040:     */           }
/* 2041:2175 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2042:     */           {
/* 2043:2177 */             if (local_Request.isRMI()) {
/* 2044:2179 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2045:     */             }
/* 2046:2183 */             if (bool1)
/* 2047:     */             {
/* 2048:2185 */               if (localThrowable != null) {
/* 2049:2185 */                 throw ((FFSException)localThrowable);
/* 2050:     */               }
/* 2051:     */             }
/* 2052:     */             else {
/* 2053:2189 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2054:     */             }
/* 2055:     */           }
/* 2056:     */         }
/* 2057:2194 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2058:     */       }
/* 2059:     */       catch (SystemException localSystemException)
/* 2060:     */       {
/* 2061:2198 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2062:     */       }
/* 2063:     */       finally
/* 2064:     */       {
/* 2065:2202 */         if (local_Request != null) {
/* 2066:2204 */           local_Request.close();
/* 2067:     */         }
/* 2068:2206 */         if (bool1) {
/* 2069:2207 */           localLocalStack.pop(localLocalFrame);
/* 2070:     */         }
/* 2071:2208 */         localLocalStack.setArgsOnLocal(bool2);
/* 2072:     */       }
/* 2073:     */     }
/* 2074:     */   }
/* 2075:     */   
/* 2076:     */   public void startLimitCheckApprovalProcessor(String paramString)
/* 2077:     */     throws java.rmi.RemoteException, FFSException
/* 2078:     */   {
/* 2079:2217 */     for (int i = 1;; i++)
/* 2080:     */     {
/* 2081:2219 */       _Request local_Request = null;
/* 2082:2220 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2083:2221 */       boolean bool1 = false;
/* 2084:2222 */       LocalFrame localLocalFrame = null;
/* 2085:2223 */       boolean bool2 = false;
/* 2086:     */       try
/* 2087:     */       {
/* 2088:2226 */         local_Request = __request("startLimitCheckApprovalProcessor");
/* 2089:2227 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2090:2228 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2091:2229 */         localLocalStack.setArgsOnLocal(bool1);
/* 2092:2231 */         if (bool1)
/* 2093:     */         {
/* 2094:2233 */           localLocalFrame = new LocalFrame(1);
/* 2095:2234 */           localLocalStack.push(localLocalFrame);
/* 2096:     */         }
/* 2097:     */         OutputStream localOutputStream;
/* 2098:2236 */         if (!bool1)
/* 2099:     */         {
/* 2100:2238 */           localOutputStream = local_Request.getOutputStream();
/* 2101:2239 */           local_Request.write_string(paramString);
/* 2102:     */         }
/* 2103:     */         else
/* 2104:     */         {
/* 2105:2243 */           localOutputStream = local_Request.getOutputStream();
/* 2106:2244 */           localLocalFrame.add(paramString);
/* 2107:     */         }
/* 2108:2246 */         local_Request.invoke();
/* 2109:2247 */         return;
/* 2110:     */       }
/* 2111:     */       catch (TRANSIENT localTRANSIENT)
/* 2112:     */       {
/* 2113:2251 */         if (i == 10) {
/* 2114:2253 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2115:     */         }
/* 2116:     */       }
/* 2117:     */       catch (UserException localUserException)
/* 2118:     */       {
/* 2119:2258 */         if (local_Request.isRMI())
/* 2120:     */         {
/* 2121:2260 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2122:2262 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2123:     */           }
/* 2124:     */         }
/* 2125:     */         else
/* 2126:     */         {
/* 2127:2267 */           Throwable localThrowable = null;
/* 2128:2268 */           if (bool1)
/* 2129:     */           {
/* 2130:2270 */             localThrowable = localLocalFrame.getException();
/* 2131:2271 */             if (localThrowable != null) {
/* 2132:2273 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 2133:     */             }
/* 2134:     */           }
/* 2135:2276 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2136:     */           {
/* 2137:2278 */             if (local_Request.isRMI()) {
/* 2138:2280 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2139:     */             }
/* 2140:2284 */             if (bool1)
/* 2141:     */             {
/* 2142:2286 */               if (localThrowable != null) {
/* 2143:2286 */                 throw ((FFSException)localThrowable);
/* 2144:     */               }
/* 2145:     */             }
/* 2146:     */             else {
/* 2147:2290 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2148:     */             }
/* 2149:     */           }
/* 2150:     */         }
/* 2151:2295 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2152:     */       }
/* 2153:     */       catch (SystemException localSystemException)
/* 2154:     */       {
/* 2155:2299 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2156:     */       }
/* 2157:     */       finally
/* 2158:     */       {
/* 2159:2303 */         if (local_Request != null) {
/* 2160:2305 */           local_Request.close();
/* 2161:     */         }
/* 2162:2307 */         if (bool1) {
/* 2163:2308 */           localLocalStack.pop(localLocalFrame);
/* 2164:     */         }
/* 2165:2309 */         localLocalStack.setArgsOnLocal(bool2);
/* 2166:     */       }
/* 2167:     */     }
/* 2168:     */   }
/* 2169:     */   
/* 2170:     */   public void runBatchProcess(String paramString1, String paramString2)
/* 2171:     */     throws java.rmi.RemoteException, FFSException
/* 2172:     */   {
/* 2173:2319 */     for (int i = 1;; i++)
/* 2174:     */     {
/* 2175:2321 */       _Request local_Request = null;
/* 2176:2322 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2177:2323 */       boolean bool1 = false;
/* 2178:2324 */       LocalFrame localLocalFrame = null;
/* 2179:2325 */       boolean bool2 = false;
/* 2180:     */       try
/* 2181:     */       {
/* 2182:2328 */         local_Request = __request("runBatchProcess");
/* 2183:2329 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2184:2330 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2185:2331 */         localLocalStack.setArgsOnLocal(bool1);
/* 2186:2333 */         if (bool1)
/* 2187:     */         {
/* 2188:2335 */           localLocalFrame = new LocalFrame(2);
/* 2189:2336 */           localLocalStack.push(localLocalFrame);
/* 2190:     */         }
/* 2191:     */         OutputStream localOutputStream;
/* 2192:2338 */         if (!bool1)
/* 2193:     */         {
/* 2194:2340 */           localOutputStream = local_Request.getOutputStream();
/* 2195:2341 */           local_Request.write_string(paramString1);
/* 2196:2342 */           local_Request.write_string(paramString2);
/* 2197:     */         }
/* 2198:     */         else
/* 2199:     */         {
/* 2200:2346 */           localOutputStream = local_Request.getOutputStream();
/* 2201:2347 */           localLocalFrame.add(paramString1);
/* 2202:2348 */           localLocalFrame.add(paramString2);
/* 2203:     */         }
/* 2204:2350 */         local_Request.invoke();
/* 2205:2351 */         return;
/* 2206:     */       }
/* 2207:     */       catch (TRANSIENT localTRANSIENT)
/* 2208:     */       {
/* 2209:2355 */         if (i == 10) {
/* 2210:2357 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2211:     */         }
/* 2212:     */       }
/* 2213:     */       catch (UserException localUserException)
/* 2214:     */       {
/* 2215:2362 */         if (local_Request.isRMI())
/* 2216:     */         {
/* 2217:2364 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2218:2366 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2219:     */           }
/* 2220:     */         }
/* 2221:     */         else
/* 2222:     */         {
/* 2223:2371 */           Throwable localThrowable = null;
/* 2224:2372 */           if (bool1)
/* 2225:     */           {
/* 2226:2374 */             localThrowable = localLocalFrame.getException();
/* 2227:2375 */             if (localThrowable != null) {
/* 2228:2377 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 2229:     */             }
/* 2230:     */           }
/* 2231:2380 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2232:     */           {
/* 2233:2382 */             if (local_Request.isRMI()) {
/* 2234:2384 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2235:     */             }
/* 2236:2388 */             if (bool1)
/* 2237:     */             {
/* 2238:2390 */               if (localThrowable != null) {
/* 2239:2390 */                 throw ((FFSException)localThrowable);
/* 2240:     */               }
/* 2241:     */             }
/* 2242:     */             else {
/* 2243:2394 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2244:     */             }
/* 2245:     */           }
/* 2246:     */         }
/* 2247:2399 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2248:     */       }
/* 2249:     */       catch (SystemException localSystemException)
/* 2250:     */       {
/* 2251:2403 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2252:     */       }
/* 2253:     */       finally
/* 2254:     */       {
/* 2255:2407 */         if (local_Request != null) {
/* 2256:2409 */           local_Request.close();
/* 2257:     */         }
/* 2258:2411 */         if (bool1) {
/* 2259:2412 */           localLocalStack.pop(localLocalFrame);
/* 2260:     */         }
/* 2261:2413 */         localLocalStack.setArgsOnLocal(bool2);
/* 2262:     */       }
/* 2263:     */     }
/* 2264:     */   }
/* 2265:     */   
/* 2266:     */   public void updateScheduleRunTimeConfig(InstructionType paramInstructionType)
/* 2267:     */     throws java.rmi.RemoteException, FFSException
/* 2268:     */   {
/* 2269:2422 */     for (int i = 1;; i++)
/* 2270:     */     {
/* 2271:2424 */       _Request local_Request = null;
/* 2272:2425 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2273:2426 */       boolean bool1 = false;
/* 2274:2427 */       LocalFrame localLocalFrame = null;
/* 2275:2428 */       boolean bool2 = false;
/* 2276:     */       try
/* 2277:     */       {
/* 2278:2431 */         local_Request = __request("updateScheduleRunTimeConfig");
/* 2279:2432 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2280:2433 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2281:2434 */         localLocalStack.setArgsOnLocal(bool1);
/* 2282:2436 */         if (bool1)
/* 2283:     */         {
/* 2284:2438 */           localLocalFrame = new LocalFrame(1);
/* 2285:2439 */           localLocalStack.push(localLocalFrame);
/* 2286:     */         }
/* 2287:     */         OutputStream localOutputStream;
/* 2288:2441 */         if (!bool1)
/* 2289:     */         {
/* 2290:2443 */           localOutputStream = local_Request.getOutputStream();
/* 2291:2444 */           local_Request.write_value(paramInstructionType, InstructionType.class);
/* 2292:     */         }
/* 2293:     */         else
/* 2294:     */         {
/* 2295:2448 */           localOutputStream = local_Request.getOutputStream();
/* 2296:2449 */           localLocalFrame.add(paramInstructionType);
/* 2297:     */         }
/* 2298:2451 */         local_Request.invoke();
/* 2299:2452 */         return;
/* 2300:     */       }
/* 2301:     */       catch (TRANSIENT localTRANSIENT)
/* 2302:     */       {
/* 2303:2456 */         if (i == 10) {
/* 2304:2458 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2305:     */         }
/* 2306:     */       }
/* 2307:     */       catch (UserException localUserException)
/* 2308:     */       {
/* 2309:2463 */         if (local_Request.isRMI())
/* 2310:     */         {
/* 2311:2465 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2312:2467 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2313:     */           }
/* 2314:     */         }
/* 2315:     */         else
/* 2316:     */         {
/* 2317:2472 */           Throwable localThrowable = null;
/* 2318:2473 */           if (bool1)
/* 2319:     */           {
/* 2320:2475 */             localThrowable = localLocalFrame.getException();
/* 2321:2476 */             if (localThrowable != null) {
/* 2322:2478 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 2323:     */             }
/* 2324:     */           }
/* 2325:2481 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2326:     */           {
/* 2327:2483 */             if (local_Request.isRMI()) {
/* 2328:2485 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2329:     */             }
/* 2330:2489 */             if (bool1)
/* 2331:     */             {
/* 2332:2491 */               if (localThrowable != null) {
/* 2333:2491 */                 throw ((FFSException)localThrowable);
/* 2334:     */               }
/* 2335:     */             }
/* 2336:     */             else {
/* 2337:2495 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2338:     */             }
/* 2339:     */           }
/* 2340:     */         }
/* 2341:2500 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2342:     */       }
/* 2343:     */       catch (SystemException localSystemException)
/* 2344:     */       {
/* 2345:2504 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2346:     */       }
/* 2347:     */       finally
/* 2348:     */       {
/* 2349:2508 */         if (local_Request != null) {
/* 2350:2510 */           local_Request.close();
/* 2351:     */         }
/* 2352:2512 */         if (bool1) {
/* 2353:2513 */           localLocalStack.pop(localLocalFrame);
/* 2354:     */         }
/* 2355:2514 */         localLocalStack.setArgsOnLocal(bool2);
/* 2356:     */       }
/* 2357:     */     }
/* 2358:     */   }
/* 2359:     */   
/* 2360:     */   public void updateScheduleProcessingConfig(InstructionType paramInstructionType)
/* 2361:     */     throws java.rmi.RemoteException, FFSException
/* 2362:     */   {
/* 2363:2523 */     for (int i = 1;; i++)
/* 2364:     */     {
/* 2365:2525 */       _Request local_Request = null;
/* 2366:2526 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2367:2527 */       boolean bool1 = false;
/* 2368:2528 */       LocalFrame localLocalFrame = null;
/* 2369:2529 */       boolean bool2 = false;
/* 2370:     */       try
/* 2371:     */       {
/* 2372:2532 */         local_Request = __request("updateScheduleProcessingConfig");
/* 2373:2533 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2374:2534 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2375:2535 */         localLocalStack.setArgsOnLocal(bool1);
/* 2376:2537 */         if (bool1)
/* 2377:     */         {
/* 2378:2539 */           localLocalFrame = new LocalFrame(1);
/* 2379:2540 */           localLocalStack.push(localLocalFrame);
/* 2380:     */         }
/* 2381:     */         OutputStream localOutputStream;
/* 2382:2542 */         if (!bool1)
/* 2383:     */         {
/* 2384:2544 */           localOutputStream = local_Request.getOutputStream();
/* 2385:2545 */           local_Request.write_value(paramInstructionType, InstructionType.class);
/* 2386:     */         }
/* 2387:     */         else
/* 2388:     */         {
/* 2389:2549 */           localOutputStream = local_Request.getOutputStream();
/* 2390:2550 */           localLocalFrame.add(paramInstructionType);
/* 2391:     */         }
/* 2392:2552 */         local_Request.invoke();
/* 2393:2553 */         return;
/* 2394:     */       }
/* 2395:     */       catch (TRANSIENT localTRANSIENT)
/* 2396:     */       {
/* 2397:2557 */         if (i == 10) {
/* 2398:2559 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2399:     */         }
/* 2400:     */       }
/* 2401:     */       catch (UserException localUserException)
/* 2402:     */       {
/* 2403:2564 */         if (local_Request.isRMI())
/* 2404:     */         {
/* 2405:2566 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2406:2568 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2407:     */           }
/* 2408:     */         }
/* 2409:     */         else
/* 2410:     */         {
/* 2411:2573 */           Throwable localThrowable = null;
/* 2412:2574 */           if (bool1)
/* 2413:     */           {
/* 2414:2576 */             localThrowable = localLocalFrame.getException();
/* 2415:2577 */             if (localThrowable != null) {
/* 2416:2579 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 2417:     */             }
/* 2418:     */           }
/* 2419:2582 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2420:     */           {
/* 2421:2584 */             if (local_Request.isRMI()) {
/* 2422:2586 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2423:     */             }
/* 2424:2590 */             if (bool1)
/* 2425:     */             {
/* 2426:2592 */               if (localThrowable != null) {
/* 2427:2592 */                 throw ((FFSException)localThrowable);
/* 2428:     */               }
/* 2429:     */             }
/* 2430:     */             else {
/* 2431:2596 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2432:     */             }
/* 2433:     */           }
/* 2434:     */         }
/* 2435:2601 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2436:     */       }
/* 2437:     */       catch (SystemException localSystemException)
/* 2438:     */       {
/* 2439:2605 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2440:     */       }
/* 2441:     */       finally
/* 2442:     */       {
/* 2443:2609 */         if (local_Request != null) {
/* 2444:2611 */           local_Request.close();
/* 2445:     */         }
/* 2446:2613 */         if (bool1) {
/* 2447:2614 */           localLocalStack.pop(localLocalFrame);
/* 2448:     */         }
/* 2449:2615 */         localLocalStack.setArgsOnLocal(bool2);
/* 2450:     */       }
/* 2451:     */     }
/* 2452:     */   }
/* 2453:     */   
/* 2454:     */   public void updateScheduleConfig(InstructionType paramInstructionType)
/* 2455:     */     throws java.rmi.RemoteException, FFSException
/* 2456:     */   {
/* 2457:2624 */     for (int i = 1;; i++)
/* 2458:     */     {
/* 2459:2626 */       _Request local_Request = null;
/* 2460:2627 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2461:2628 */       boolean bool1 = false;
/* 2462:2629 */       LocalFrame localLocalFrame = null;
/* 2463:2630 */       boolean bool2 = false;
/* 2464:     */       try
/* 2465:     */       {
/* 2466:2633 */         local_Request = __request("updateScheduleConfig");
/* 2467:2634 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2468:2635 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2469:2636 */         localLocalStack.setArgsOnLocal(bool1);
/* 2470:2638 */         if (bool1)
/* 2471:     */         {
/* 2472:2640 */           localLocalFrame = new LocalFrame(1);
/* 2473:2641 */           localLocalStack.push(localLocalFrame);
/* 2474:     */         }
/* 2475:     */         OutputStream localOutputStream;
/* 2476:2643 */         if (!bool1)
/* 2477:     */         {
/* 2478:2645 */           localOutputStream = local_Request.getOutputStream();
/* 2479:2646 */           local_Request.write_value(paramInstructionType, InstructionType.class);
/* 2480:     */         }
/* 2481:     */         else
/* 2482:     */         {
/* 2483:2650 */           localOutputStream = local_Request.getOutputStream();
/* 2484:2651 */           localLocalFrame.add(paramInstructionType);
/* 2485:     */         }
/* 2486:2653 */         local_Request.invoke();
/* 2487:2654 */         return;
/* 2488:     */       }
/* 2489:     */       catch (TRANSIENT localTRANSIENT)
/* 2490:     */       {
/* 2491:2658 */         if (i == 10) {
/* 2492:2660 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2493:     */         }
/* 2494:     */       }
/* 2495:     */       catch (UserException localUserException)
/* 2496:     */       {
/* 2497:2665 */         if (local_Request.isRMI())
/* 2498:     */         {
/* 2499:2667 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2500:2669 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2501:     */           }
/* 2502:     */         }
/* 2503:     */         else
/* 2504:     */         {
/* 2505:2674 */           Throwable localThrowable = null;
/* 2506:2675 */           if (bool1)
/* 2507:     */           {
/* 2508:2677 */             localThrowable = localLocalFrame.getException();
/* 2509:2678 */             if (localThrowable != null) {
/* 2510:2680 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 2511:     */             }
/* 2512:     */           }
/* 2513:2683 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2514:     */           {
/* 2515:2685 */             if (local_Request.isRMI()) {
/* 2516:2687 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2517:     */             }
/* 2518:2691 */             if (bool1)
/* 2519:     */             {
/* 2520:2693 */               if (localThrowable != null) {
/* 2521:2693 */                 throw ((FFSException)localThrowable);
/* 2522:     */               }
/* 2523:     */             }
/* 2524:     */             else {
/* 2525:2697 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2526:     */             }
/* 2527:     */           }
/* 2528:     */         }
/* 2529:2702 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2530:     */       }
/* 2531:     */       catch (SystemException localSystemException)
/* 2532:     */       {
/* 2533:2706 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2534:     */       }
/* 2535:     */       finally
/* 2536:     */       {
/* 2537:2710 */         if (local_Request != null) {
/* 2538:2712 */           local_Request.close();
/* 2539:     */         }
/* 2540:2714 */         if (bool1) {
/* 2541:2715 */           localLocalStack.pop(localLocalFrame);
/* 2542:     */         }
/* 2543:2716 */         localLocalStack.setArgsOnLocal(bool2);
/* 2544:     */       }
/* 2545:     */     }
/* 2546:     */   }
/* 2547:     */   
/* 2548:     */   public InstructionType getScheduleConfig(String paramString1, String paramString2)
/* 2549:     */     throws java.rmi.RemoteException, FFSException
/* 2550:     */   {
/* 2551:2726 */     for (int i = 1;; i++)
/* 2552:     */     {
/* 2553:2728 */       _Request local_Request = null;
/* 2554:2729 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2555:2730 */       boolean bool1 = false;
/* 2556:2731 */       LocalFrame localLocalFrame = null;
/* 2557:2732 */       boolean bool2 = false;
/* 2558:     */       try
/* 2559:     */       {
/* 2560:2735 */         local_Request = __request("getScheduleConfig__string__string", "getScheduleConfig__CORBA_WStringValue__CORBA_WStringValue");
/* 2561:2736 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2562:2737 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2563:2738 */         localLocalStack.setArgsOnLocal(bool1);
/* 2564:2740 */         if (bool1)
/* 2565:     */         {
/* 2566:2742 */           localLocalFrame = new LocalFrame(2);
/* 2567:2743 */           localLocalStack.push(localLocalFrame);
/* 2568:     */         }
/* 2569:2745 */         if (!bool1)
/* 2570:     */         {
/* 2571:2747 */           localObject4 = local_Request.getOutputStream();
/* 2572:2748 */           local_Request.write_string(paramString1);
/* 2573:2749 */           local_Request.write_string(paramString2);
/* 2574:     */         }
/* 2575:     */         else
/* 2576:     */         {
/* 2577:2753 */           localObject4 = local_Request.getOutputStream();
/* 2578:2754 */           localLocalFrame.add(paramString1);
/* 2579:2755 */           localLocalFrame.add(paramString2);
/* 2580:     */         }
/* 2581:2757 */         local_Request.invoke();
/* 2582:     */         Object localObject1;
/* 2583:2758 */         if (bool1)
/* 2584:     */         {
/* 2585:2760 */           localObject4 = null;
/* 2586:2761 */           localObject5 = localLocalFrame.getResult();
/* 2587:2762 */           if (localObject5 != null) {
/* 2588:2764 */             localObject4 = (InstructionType)ObjectVal.clone(localObject5);
/* 2589:     */           }
/* 2590:2766 */           return localObject4;
/* 2591:     */         }
/* 2592:2768 */         Object localObject4 = local_Request.getInputStream();
/* 2593:     */         
/* 2594:2770 */         localObject5 = (InstructionType)local_Request.read_value(InstructionType.class);
/* 2595:2771 */         return localObject5;
/* 2596:     */       }
/* 2597:     */       catch (TRANSIENT localTRANSIENT)
/* 2598:     */       {
/* 2599:2775 */         if (i == 10) {
/* 2600:2777 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2601:     */         }
/* 2602:     */       }
/* 2603:     */       catch (UserException localUserException)
/* 2604:     */       {
/* 2605:     */         Object localObject5;
/* 2606:2782 */         if (local_Request.isRMI())
/* 2607:     */         {
/* 2608:2784 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2609:2786 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2610:     */           }
/* 2611:     */         }
/* 2612:     */         else
/* 2613:     */         {
/* 2614:2791 */           localObject5 = null;
/* 2615:2792 */           if (bool1)
/* 2616:     */           {
/* 2617:2794 */             localObject5 = localLocalFrame.getException();
/* 2618:2795 */             if (localObject5 != null) {
/* 2619:2797 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 2620:     */             }
/* 2621:     */           }
/* 2622:2800 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2623:     */           {
/* 2624:2802 */             if (local_Request.isRMI()) {
/* 2625:2804 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2626:     */             }
/* 2627:2808 */             if (bool1)
/* 2628:     */             {
/* 2629:2810 */               if (localObject5 != null) {
/* 2630:2810 */                 throw ((FFSException)localObject5);
/* 2631:     */               }
/* 2632:     */             }
/* 2633:     */             else {
/* 2634:2814 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2635:     */             }
/* 2636:     */           }
/* 2637:     */         }
/* 2638:2819 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2639:     */       }
/* 2640:     */       catch (SystemException localSystemException)
/* 2641:     */       {
/* 2642:2823 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2643:     */       }
/* 2644:     */       finally
/* 2645:     */       {
/* 2646:2827 */         if (local_Request != null) {
/* 2647:2829 */           local_Request.close();
/* 2648:     */         }
/* 2649:2831 */         if (bool1) {
/* 2650:2832 */           localLocalStack.pop(localLocalFrame);
/* 2651:     */         }
/* 2652:2833 */         localLocalStack.setArgsOnLocal(bool2);
/* 2653:     */       }
/* 2654:     */     }
/* 2655:     */   }
/* 2656:     */   
/* 2657:     */   public InstructionType[] getScheduleConfig()
/* 2658:     */     throws java.rmi.RemoteException, FFSException
/* 2659:     */   {
/* 2660:2841 */     for (int i = 1;; i++)
/* 2661:     */     {
/* 2662:2843 */       _Request local_Request = null;
/* 2663:2844 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2664:2845 */       boolean bool1 = false;
/* 2665:2846 */       LocalFrame localLocalFrame = null;
/* 2666:2847 */       boolean bool2 = false;
/* 2667:     */       try
/* 2668:     */       {
/* 2669:2850 */         local_Request = __request("getScheduleConfig__", "getScheduleConfig__");
/* 2670:2851 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2671:2852 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2672:2853 */         localLocalStack.setArgsOnLocal(bool1);
/* 2673:2855 */         if (bool1)
/* 2674:     */         {
/* 2675:2857 */           localLocalFrame = new LocalFrame(0);
/* 2676:2858 */           localLocalStack.push(localLocalFrame);
/* 2677:     */         }
/* 2678:2866 */         local_Request.invoke();
/* 2679:     */         Object localObject1;
/* 2680:2867 */         if (bool1)
/* 2681:     */         {
/* 2682:2869 */           localObject4 = null;
/* 2683:2870 */           localObject5 = localLocalFrame.getResult();
/* 2684:2871 */           if (localObject5 != null) {
/* 2685:2873 */             localObject4 = (InstructionType[])ObjectVal.clone(localObject5);
/* 2686:     */           }
/* 2687:2875 */           return localObject4;
/* 2688:     */         }
/* 2689:2877 */         Object localObject4 = local_Request.getInputStream();
/* 2690:2879 */         if (local_Request.isRMI()) {
/* 2691:2879 */           localObject5 = (InstructionType[])local_Request.read_value(new InstructionType[0].getClass());
/* 2692:     */         } else {
/* 2693:2879 */           localObject5 = InstructionTypeSeqHelper.read((InputStream)localObject4);
/* 2694:     */         }
/* 2695:2880 */         return localObject5;
/* 2696:     */       }
/* 2697:     */       catch (TRANSIENT localTRANSIENT)
/* 2698:     */       {
/* 2699:2884 */         if (i == 10) {
/* 2700:2886 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2701:     */         }
/* 2702:     */       }
/* 2703:     */       catch (UserException localUserException)
/* 2704:     */       {
/* 2705:     */         Object localObject5;
/* 2706:2891 */         if (local_Request.isRMI())
/* 2707:     */         {
/* 2708:2893 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2709:2895 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2710:     */           }
/* 2711:     */         }
/* 2712:     */         else
/* 2713:     */         {
/* 2714:2900 */           localObject5 = null;
/* 2715:2901 */           if (bool1)
/* 2716:     */           {
/* 2717:2903 */             localObject5 = localLocalFrame.getException();
/* 2718:2904 */             if (localObject5 != null) {
/* 2719:2906 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 2720:     */             }
/* 2721:     */           }
/* 2722:2909 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2723:     */           {
/* 2724:2911 */             if (local_Request.isRMI()) {
/* 2725:2913 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2726:     */             }
/* 2727:2917 */             if (bool1)
/* 2728:     */             {
/* 2729:2919 */               if (localObject5 != null) {
/* 2730:2919 */                 throw ((FFSException)localObject5);
/* 2731:     */               }
/* 2732:     */             }
/* 2733:     */             else {
/* 2734:2923 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2735:     */             }
/* 2736:     */           }
/* 2737:     */         }
/* 2738:2928 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2739:     */       }
/* 2740:     */       catch (SystemException localSystemException)
/* 2741:     */       {
/* 2742:2932 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2743:     */       }
/* 2744:     */       finally
/* 2745:     */       {
/* 2746:2936 */         if (local_Request != null) {
/* 2747:2938 */           local_Request.close();
/* 2748:     */         }
/* 2749:2940 */         if (bool1) {
/* 2750:2941 */           localLocalStack.pop(localLocalFrame);
/* 2751:     */         }
/* 2752:2942 */         localLocalStack.setArgsOnLocal(bool2);
/* 2753:     */       }
/* 2754:     */     }
/* 2755:     */   }
/* 2756:     */   
/* 2757:     */   public SchedulerInfo getSchedulerInfo(String paramString1, String paramString2)
/* 2758:     */     throws java.rmi.RemoteException, FFSException
/* 2759:     */   {
/* 2760:2952 */     for (int i = 1;; i++)
/* 2761:     */     {
/* 2762:2954 */       _Request local_Request = null;
/* 2763:2955 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2764:2956 */       boolean bool1 = false;
/* 2765:2957 */       LocalFrame localLocalFrame = null;
/* 2766:2958 */       boolean bool2 = false;
/* 2767:     */       try
/* 2768:     */       {
/* 2769:2961 */         local_Request = __request("getSchedulerInfo__string__string", "getSchedulerInfo__CORBA_WStringValue__CORBA_WStringValue");
/* 2770:2962 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2771:2963 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2772:2964 */         localLocalStack.setArgsOnLocal(bool1);
/* 2773:2966 */         if (bool1)
/* 2774:     */         {
/* 2775:2968 */           localLocalFrame = new LocalFrame(2);
/* 2776:2969 */           localLocalStack.push(localLocalFrame);
/* 2777:     */         }
/* 2778:2971 */         if (!bool1)
/* 2779:     */         {
/* 2780:2973 */           localObject4 = local_Request.getOutputStream();
/* 2781:2974 */           local_Request.write_string(paramString1);
/* 2782:2975 */           local_Request.write_string(paramString2);
/* 2783:     */         }
/* 2784:     */         else
/* 2785:     */         {
/* 2786:2979 */           localObject4 = local_Request.getOutputStream();
/* 2787:2980 */           localLocalFrame.add(paramString1);
/* 2788:2981 */           localLocalFrame.add(paramString2);
/* 2789:     */         }
/* 2790:2983 */         local_Request.invoke();
/* 2791:     */         Object localObject1;
/* 2792:2984 */         if (bool1)
/* 2793:     */         {
/* 2794:2986 */           localObject4 = null;
/* 2795:2987 */           localObject5 = localLocalFrame.getResult();
/* 2796:2988 */           if (localObject5 != null) {
/* 2797:2990 */             localObject4 = (SchedulerInfo)ObjectVal.clone(localObject5);
/* 2798:     */           }
/* 2799:2992 */           return localObject4;
/* 2800:     */         }
/* 2801:2994 */         Object localObject4 = local_Request.getInputStream();
/* 2802:     */         
/* 2803:2996 */         localObject5 = (SchedulerInfo)local_Request.read_value(SchedulerInfo.class);
/* 2804:2997 */         return localObject5;
/* 2805:     */       }
/* 2806:     */       catch (TRANSIENT localTRANSIENT)
/* 2807:     */       {
/* 2808:3001 */         if (i == 10) {
/* 2809:3003 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2810:     */         }
/* 2811:     */       }
/* 2812:     */       catch (UserException localUserException)
/* 2813:     */       {
/* 2814:     */         Object localObject5;
/* 2815:3008 */         if (local_Request.isRMI())
/* 2816:     */         {
/* 2817:3010 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2818:3012 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2819:     */           }
/* 2820:     */         }
/* 2821:     */         else
/* 2822:     */         {
/* 2823:3017 */           localObject5 = null;
/* 2824:3018 */           if (bool1)
/* 2825:     */           {
/* 2826:3020 */             localObject5 = localLocalFrame.getException();
/* 2827:3021 */             if (localObject5 != null) {
/* 2828:3023 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 2829:     */             }
/* 2830:     */           }
/* 2831:3026 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2832:     */           {
/* 2833:3028 */             if (local_Request.isRMI()) {
/* 2834:3030 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2835:     */             }
/* 2836:3034 */             if (bool1)
/* 2837:     */             {
/* 2838:3036 */               if (localObject5 != null) {
/* 2839:3036 */                 throw ((FFSException)localObject5);
/* 2840:     */               }
/* 2841:     */             }
/* 2842:     */             else {
/* 2843:3040 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2844:     */             }
/* 2845:     */           }
/* 2846:     */         }
/* 2847:3045 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2848:     */       }
/* 2849:     */       catch (SystemException localSystemException)
/* 2850:     */       {
/* 2851:3049 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2852:     */       }
/* 2853:     */       finally
/* 2854:     */       {
/* 2855:3053 */         if (local_Request != null) {
/* 2856:3055 */           local_Request.close();
/* 2857:     */         }
/* 2858:3057 */         if (bool1) {
/* 2859:3058 */           localLocalStack.pop(localLocalFrame);
/* 2860:     */         }
/* 2861:3059 */         localLocalStack.setArgsOnLocal(bool2);
/* 2862:     */       }
/* 2863:     */     }
/* 2864:     */   }
/* 2865:     */   
/* 2866:     */   public SchedulerInfo[] getSchedulerInfo()
/* 2867:     */     throws java.rmi.RemoteException, FFSException
/* 2868:     */   {
/* 2869:3067 */     for (int i = 1;; i++)
/* 2870:     */     {
/* 2871:3069 */       _Request local_Request = null;
/* 2872:3070 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2873:3071 */       boolean bool1 = false;
/* 2874:3072 */       LocalFrame localLocalFrame = null;
/* 2875:3073 */       boolean bool2 = false;
/* 2876:     */       try
/* 2877:     */       {
/* 2878:3076 */         local_Request = __request("getSchedulerInfo__", "getSchedulerInfo__");
/* 2879:3077 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2880:3078 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2881:3079 */         localLocalStack.setArgsOnLocal(bool1);
/* 2882:3081 */         if (bool1)
/* 2883:     */         {
/* 2884:3083 */           localLocalFrame = new LocalFrame(0);
/* 2885:3084 */           localLocalStack.push(localLocalFrame);
/* 2886:     */         }
/* 2887:3092 */         local_Request.invoke();
/* 2888:     */         Object localObject1;
/* 2889:3093 */         if (bool1)
/* 2890:     */         {
/* 2891:3095 */           localObject4 = null;
/* 2892:3096 */           localObject5 = localLocalFrame.getResult();
/* 2893:3097 */           if (localObject5 != null) {
/* 2894:3099 */             localObject4 = (SchedulerInfo[])ObjectVal.clone(localObject5);
/* 2895:     */           }
/* 2896:3101 */           return localObject4;
/* 2897:     */         }
/* 2898:3103 */         Object localObject4 = local_Request.getInputStream();
/* 2899:3105 */         if (local_Request.isRMI()) {
/* 2900:3105 */           localObject5 = (SchedulerInfo[])local_Request.read_value(new SchedulerInfo[0].getClass());
/* 2901:     */         } else {
/* 2902:3105 */           localObject5 = SchedulerInfoSeqHelper.read((InputStream)localObject4);
/* 2903:     */         }
/* 2904:3106 */         return localObject5;
/* 2905:     */       }
/* 2906:     */       catch (TRANSIENT localTRANSIENT)
/* 2907:     */       {
/* 2908:3110 */         if (i == 10) {
/* 2909:3112 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2910:     */         }
/* 2911:     */       }
/* 2912:     */       catch (UserException localUserException)
/* 2913:     */       {
/* 2914:     */         Object localObject5;
/* 2915:3117 */         if (local_Request.isRMI())
/* 2916:     */         {
/* 2917:3119 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 2918:3121 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2919:     */           }
/* 2920:     */         }
/* 2921:     */         else
/* 2922:     */         {
/* 2923:3126 */           localObject5 = null;
/* 2924:3127 */           if (bool1)
/* 2925:     */           {
/* 2926:3129 */             localObject5 = localLocalFrame.getException();
/* 2927:3130 */             if (localObject5 != null) {
/* 2928:3132 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 2929:     */             }
/* 2930:     */           }
/* 2931:3135 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 2932:     */           {
/* 2933:3137 */             if (local_Request.isRMI()) {
/* 2934:3139 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 2935:     */             }
/* 2936:3143 */             if (bool1)
/* 2937:     */             {
/* 2938:3145 */               if (localObject5 != null) {
/* 2939:3145 */                 throw ((FFSException)localObject5);
/* 2940:     */               }
/* 2941:     */             }
/* 2942:     */             else {
/* 2943:3149 */               throw FFSExceptionHelper.read(localUserException.input);
/* 2944:     */             }
/* 2945:     */           }
/* 2946:     */         }
/* 2947:3154 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2948:     */       }
/* 2949:     */       catch (SystemException localSystemException)
/* 2950:     */       {
/* 2951:3158 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2952:     */       }
/* 2953:     */       finally
/* 2954:     */       {
/* 2955:3162 */         if (local_Request != null) {
/* 2956:3164 */           local_Request.close();
/* 2957:     */         }
/* 2958:3166 */         if (bool1) {
/* 2959:3167 */           localLocalStack.pop(localLocalFrame);
/* 2960:     */         }
/* 2961:3168 */         localLocalStack.setArgsOnLocal(bool2);
/* 2962:     */       }
/* 2963:     */     }
/* 2964:     */   }
/* 2965:     */   
/* 2966:     */   public ScheduleHist[] getScheduleHist(String paramString1, String paramString2, ScheduleHist paramScheduleHist)
/* 2967:     */     throws java.rmi.RemoteException, FFSException
/* 2968:     */   {
/* 2969:3179 */     for (int i = 1;; i++)
/* 2970:     */     {
/* 2971:3181 */       _Request local_Request = null;
/* 2972:3182 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2973:3183 */       boolean bool1 = false;
/* 2974:3184 */       LocalFrame localLocalFrame = null;
/* 2975:3185 */       boolean bool2 = false;
/* 2976:     */       try
/* 2977:     */       {
/* 2978:3188 */         local_Request = __request("getScheduleHist");
/* 2979:3189 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2980:3190 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2981:3191 */         localLocalStack.setArgsOnLocal(bool1);
/* 2982:3193 */         if (bool1)
/* 2983:     */         {
/* 2984:3195 */           localLocalFrame = new LocalFrame(3);
/* 2985:3196 */           localLocalStack.push(localLocalFrame);
/* 2986:     */         }
/* 2987:3198 */         if (!bool1)
/* 2988:     */         {
/* 2989:3200 */           localObject4 = local_Request.getOutputStream();
/* 2990:3201 */           local_Request.write_string(paramString1);
/* 2991:3202 */           local_Request.write_string(paramString2);
/* 2992:3203 */           if (local_Request.isRMI()) {
/* 2993:3203 */             local_Request.write_value(paramScheduleHist, ScheduleHist.class);
/* 2994:     */           } else {
/* 2995:3203 */             ScheduleHistHelper.write((OutputStream)localObject4, paramScheduleHist);
/* 2996:     */           }
/* 2997:     */         }
/* 2998:     */         else
/* 2999:     */         {
/* 3000:3207 */           localObject4 = local_Request.getOutputStream();
/* 3001:3208 */           localLocalFrame.add(paramString1);
/* 3002:3209 */           localLocalFrame.add(paramString2);
/* 3003:3210 */           localLocalFrame.add(paramScheduleHist);
/* 3004:     */         }
/* 3005:3212 */         local_Request.invoke();
/* 3006:     */         Object localObject1;
/* 3007:3213 */         if (bool1)
/* 3008:     */         {
/* 3009:3215 */           localObject4 = null;
/* 3010:3216 */           localObject5 = localLocalFrame.getResult();
/* 3011:3217 */           if (localObject5 != null) {
/* 3012:3219 */             localObject4 = (ScheduleHist[])ObjectVal.clone(localObject5);
/* 3013:     */           }
/* 3014:3221 */           return localObject4;
/* 3015:     */         }
/* 3016:3223 */         Object localObject4 = local_Request.getInputStream();
/* 3017:3225 */         if (local_Request.isRMI()) {
/* 3018:3225 */           localObject5 = (ScheduleHist[])local_Request.read_value(new ScheduleHist[0].getClass());
/* 3019:     */         } else {
/* 3020:3225 */           localObject5 = ScheduleHistSeqHelper.read((InputStream)localObject4);
/* 3021:     */         }
/* 3022:3226 */         return localObject5;
/* 3023:     */       }
/* 3024:     */       catch (TRANSIENT localTRANSIENT)
/* 3025:     */       {
/* 3026:3230 */         if (i == 10) {
/* 3027:3232 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3028:     */         }
/* 3029:     */       }
/* 3030:     */       catch (UserException localUserException)
/* 3031:     */       {
/* 3032:     */         Object localObject5;
/* 3033:3237 */         if (local_Request.isRMI())
/* 3034:     */         {
/* 3035:3239 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3036:3241 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3037:     */           }
/* 3038:     */         }
/* 3039:     */         else
/* 3040:     */         {
/* 3041:3246 */           localObject5 = null;
/* 3042:3247 */           if (bool1)
/* 3043:     */           {
/* 3044:3249 */             localObject5 = localLocalFrame.getException();
/* 3045:3250 */             if (localObject5 != null) {
/* 3046:3252 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 3047:     */             }
/* 3048:     */           }
/* 3049:3255 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3050:     */           {
/* 3051:3257 */             if (local_Request.isRMI()) {
/* 3052:3259 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3053:     */             }
/* 3054:3263 */             if (bool1)
/* 3055:     */             {
/* 3056:3265 */               if (localObject5 != null) {
/* 3057:3265 */                 throw ((FFSException)localObject5);
/* 3058:     */               }
/* 3059:     */             }
/* 3060:     */             else {
/* 3061:3269 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3062:     */             }
/* 3063:     */           }
/* 3064:     */         }
/* 3065:3274 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3066:     */       }
/* 3067:     */       catch (SystemException localSystemException)
/* 3068:     */       {
/* 3069:3278 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3070:     */       }
/* 3071:     */       finally
/* 3072:     */       {
/* 3073:3282 */         if (local_Request != null) {
/* 3074:3284 */           local_Request.close();
/* 3075:     */         }
/* 3076:3286 */         if (bool1) {
/* 3077:3287 */           localLocalStack.pop(localLocalFrame);
/* 3078:     */         }
/* 3079:3288 */         localLocalStack.setArgsOnLocal(bool2);
/* 3080:     */       }
/* 3081:     */     }
/* 3082:     */   }
/* 3083:     */   
/* 3084:     */   public PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
/* 3085:     */     throws java.rmi.RemoteException, FFSException
/* 3086:     */   {
/* 3087:3298 */     for (int i = 1;; i++)
/* 3088:     */     {
/* 3089:3300 */       _Request local_Request = null;
/* 3090:3301 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3091:3302 */       boolean bool1 = false;
/* 3092:3303 */       LocalFrame localLocalFrame = null;
/* 3093:3304 */       boolean bool2 = false;
/* 3094:     */       try
/* 3095:     */       {
/* 3096:3307 */         local_Request = __request("searchGlobalPayees__PayeeInfo__long", "searchGlobalPayees__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long");
/* 3097:3308 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3098:3309 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3099:3310 */         localLocalStack.setArgsOnLocal(bool1);
/* 3100:3312 */         if (bool1)
/* 3101:     */         {
/* 3102:3314 */           localLocalFrame = new LocalFrame(2);
/* 3103:3315 */           localLocalStack.push(localLocalFrame);
/* 3104:     */         }
/* 3105:3317 */         if (!bool1)
/* 3106:     */         {
/* 3107:3319 */           localObject4 = local_Request.getOutputStream();
/* 3108:3320 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 3109:3321 */           ((OutputStream)localObject4).write_long(paramInt);
/* 3110:     */         }
/* 3111:     */         else
/* 3112:     */         {
/* 3113:3325 */           localObject4 = local_Request.getOutputStream();
/* 3114:3326 */           localLocalFrame.add(paramPayeeInfo);
/* 3115:3327 */           localLocalFrame.add(paramInt);
/* 3116:     */         }
/* 3117:3329 */         local_Request.invoke();
/* 3118:     */         Object localObject1;
/* 3119:3330 */         if (bool1)
/* 3120:     */         {
/* 3121:3332 */           localObject4 = null;
/* 3122:3333 */           localObject5 = localLocalFrame.getResult();
/* 3123:3334 */           if (localObject5 != null) {
/* 3124:3336 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 3125:     */           }
/* 3126:3338 */           return localObject4;
/* 3127:     */         }
/* 3128:3340 */         Object localObject4 = local_Request.getInputStream();
/* 3129:3342 */         if (local_Request.isRMI()) {
/* 3130:3342 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 3131:     */         } else {
/* 3132:3342 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 3133:     */         }
/* 3134:3343 */         return localObject5;
/* 3135:     */       }
/* 3136:     */       catch (TRANSIENT localTRANSIENT)
/* 3137:     */       {
/* 3138:3347 */         if (i == 10) {
/* 3139:3349 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3140:     */         }
/* 3141:     */       }
/* 3142:     */       catch (UserException localUserException)
/* 3143:     */       {
/* 3144:     */         Object localObject5;
/* 3145:3354 */         if (local_Request.isRMI())
/* 3146:     */         {
/* 3147:3356 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3148:3358 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3149:     */           }
/* 3150:     */         }
/* 3151:     */         else
/* 3152:     */         {
/* 3153:3363 */           localObject5 = null;
/* 3154:3364 */           if (bool1)
/* 3155:     */           {
/* 3156:3366 */             localObject5 = localLocalFrame.getException();
/* 3157:3367 */             if (localObject5 != null) {
/* 3158:3369 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 3159:     */             }
/* 3160:     */           }
/* 3161:3372 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3162:     */           {
/* 3163:3374 */             if (local_Request.isRMI()) {
/* 3164:3376 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3165:     */             }
/* 3166:3380 */             if (bool1)
/* 3167:     */             {
/* 3168:3382 */               if (localObject5 != null) {
/* 3169:3382 */                 throw ((FFSException)localObject5);
/* 3170:     */               }
/* 3171:     */             }
/* 3172:     */             else {
/* 3173:3386 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3174:     */             }
/* 3175:     */           }
/* 3176:     */         }
/* 3177:3391 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3178:     */       }
/* 3179:     */       catch (SystemException localSystemException)
/* 3180:     */       {
/* 3181:3395 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3182:     */       }
/* 3183:     */       finally
/* 3184:     */       {
/* 3185:3399 */         if (local_Request != null) {
/* 3186:3401 */           local_Request.close();
/* 3187:     */         }
/* 3188:3403 */         if (bool1) {
/* 3189:3404 */           localLocalStack.pop(localLocalFrame);
/* 3190:     */         }
/* 3191:3405 */         localLocalStack.setArgsOnLocal(bool2);
/* 3192:     */       }
/* 3193:     */     }
/* 3194:     */   }
/* 3195:     */   
/* 3196:     */   public PayeeInfo[] searchGlobalPayees(String paramString)
/* 3197:     */     throws java.rmi.RemoteException, FFSException
/* 3198:     */   {
/* 3199:3414 */     for (int i = 1;; i++)
/* 3200:     */     {
/* 3201:3416 */       _Request local_Request = null;
/* 3202:3417 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3203:3418 */       boolean bool1 = false;
/* 3204:3419 */       LocalFrame localLocalFrame = null;
/* 3205:3420 */       boolean bool2 = false;
/* 3206:     */       try
/* 3207:     */       {
/* 3208:3423 */         local_Request = __request("searchGlobalPayees__string", "searchGlobalPayees__CORBA_WStringValue");
/* 3209:3424 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3210:3425 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3211:3426 */         localLocalStack.setArgsOnLocal(bool1);
/* 3212:3428 */         if (bool1)
/* 3213:     */         {
/* 3214:3430 */           localLocalFrame = new LocalFrame(1);
/* 3215:3431 */           localLocalStack.push(localLocalFrame);
/* 3216:     */         }
/* 3217:3433 */         if (!bool1)
/* 3218:     */         {
/* 3219:3435 */           localObject4 = local_Request.getOutputStream();
/* 3220:3436 */           local_Request.write_string(paramString);
/* 3221:     */         }
/* 3222:     */         else
/* 3223:     */         {
/* 3224:3440 */           localObject4 = local_Request.getOutputStream();
/* 3225:3441 */           localLocalFrame.add(paramString);
/* 3226:     */         }
/* 3227:3443 */         local_Request.invoke();
/* 3228:     */         Object localObject1;
/* 3229:3444 */         if (bool1)
/* 3230:     */         {
/* 3231:3446 */           localObject4 = null;
/* 3232:3447 */           localObject5 = localLocalFrame.getResult();
/* 3233:3448 */           if (localObject5 != null) {
/* 3234:3450 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 3235:     */           }
/* 3236:3452 */           return localObject4;
/* 3237:     */         }
/* 3238:3454 */         Object localObject4 = local_Request.getInputStream();
/* 3239:3456 */         if (local_Request.isRMI()) {
/* 3240:3456 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 3241:     */         } else {
/* 3242:3456 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 3243:     */         }
/* 3244:3457 */         return localObject5;
/* 3245:     */       }
/* 3246:     */       catch (TRANSIENT localTRANSIENT)
/* 3247:     */       {
/* 3248:3461 */         if (i == 10) {
/* 3249:3463 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3250:     */         }
/* 3251:     */       }
/* 3252:     */       catch (UserException localUserException)
/* 3253:     */       {
/* 3254:     */         Object localObject5;
/* 3255:3468 */         if (local_Request.isRMI())
/* 3256:     */         {
/* 3257:3470 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3258:3472 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3259:     */           }
/* 3260:     */         }
/* 3261:     */         else
/* 3262:     */         {
/* 3263:3477 */           localObject5 = null;
/* 3264:3478 */           if (bool1)
/* 3265:     */           {
/* 3266:3480 */             localObject5 = localLocalFrame.getException();
/* 3267:3481 */             if (localObject5 != null) {
/* 3268:3483 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 3269:     */             }
/* 3270:     */           }
/* 3271:3486 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3272:     */           {
/* 3273:3488 */             if (local_Request.isRMI()) {
/* 3274:3490 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3275:     */             }
/* 3276:3494 */             if (bool1)
/* 3277:     */             {
/* 3278:3496 */               if (localObject5 != null) {
/* 3279:3496 */                 throw ((FFSException)localObject5);
/* 3280:     */               }
/* 3281:     */             }
/* 3282:     */             else {
/* 3283:3500 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3284:     */             }
/* 3285:     */           }
/* 3286:     */         }
/* 3287:3505 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3288:     */       }
/* 3289:     */       catch (SystemException localSystemException)
/* 3290:     */       {
/* 3291:3509 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3292:     */       }
/* 3293:     */       finally
/* 3294:     */       {
/* 3295:3513 */         if (local_Request != null) {
/* 3296:3515 */           local_Request.close();
/* 3297:     */         }
/* 3298:3517 */         if (bool1) {
/* 3299:3518 */           localLocalStack.pop(localLocalFrame);
/* 3300:     */         }
/* 3301:3519 */         localLocalStack.setArgsOnLocal(bool2);
/* 3302:     */       }
/* 3303:     */     }
/* 3304:     */   }
/* 3305:     */   
/* 3306:     */   public PayeeInfo getGlobalPayee(String paramString)
/* 3307:     */     throws java.rmi.RemoteException, FFSException
/* 3308:     */   {
/* 3309:3528 */     for (int i = 1;; i++)
/* 3310:     */     {
/* 3311:3530 */       _Request local_Request = null;
/* 3312:3531 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3313:3532 */       boolean bool1 = false;
/* 3314:3533 */       LocalFrame localLocalFrame = null;
/* 3315:3534 */       boolean bool2 = false;
/* 3316:     */       try
/* 3317:     */       {
/* 3318:3537 */         local_Request = __request("getGlobalPayee");
/* 3319:3538 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3320:3539 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3321:3540 */         localLocalStack.setArgsOnLocal(bool1);
/* 3322:3542 */         if (bool1)
/* 3323:     */         {
/* 3324:3544 */           localLocalFrame = new LocalFrame(1);
/* 3325:3545 */           localLocalStack.push(localLocalFrame);
/* 3326:     */         }
/* 3327:3547 */         if (!bool1)
/* 3328:     */         {
/* 3329:3549 */           localObject4 = local_Request.getOutputStream();
/* 3330:3550 */           local_Request.write_string(paramString);
/* 3331:     */         }
/* 3332:     */         else
/* 3333:     */         {
/* 3334:3554 */           localObject4 = local_Request.getOutputStream();
/* 3335:3555 */           localLocalFrame.add(paramString);
/* 3336:     */         }
/* 3337:3557 */         local_Request.invoke();
/* 3338:     */         Object localObject1;
/* 3339:3558 */         if (bool1)
/* 3340:     */         {
/* 3341:3560 */           localObject4 = null;
/* 3342:3561 */           localObject5 = localLocalFrame.getResult();
/* 3343:3562 */           if (localObject5 != null) {
/* 3344:3564 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 3345:     */           }
/* 3346:3566 */           return localObject4;
/* 3347:     */         }
/* 3348:3568 */         Object localObject4 = local_Request.getInputStream();
/* 3349:     */         
/* 3350:3570 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 3351:3571 */         return localObject5;
/* 3352:     */       }
/* 3353:     */       catch (TRANSIENT localTRANSIENT)
/* 3354:     */       {
/* 3355:3575 */         if (i == 10) {
/* 3356:3577 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3357:     */         }
/* 3358:     */       }
/* 3359:     */       catch (UserException localUserException)
/* 3360:     */       {
/* 3361:     */         Object localObject5;
/* 3362:3582 */         if (local_Request.isRMI())
/* 3363:     */         {
/* 3364:3584 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3365:3586 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3366:     */           }
/* 3367:     */         }
/* 3368:     */         else
/* 3369:     */         {
/* 3370:3591 */           localObject5 = null;
/* 3371:3592 */           if (bool1)
/* 3372:     */           {
/* 3373:3594 */             localObject5 = localLocalFrame.getException();
/* 3374:3595 */             if (localObject5 != null) {
/* 3375:3597 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 3376:     */             }
/* 3377:     */           }
/* 3378:3600 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3379:     */           {
/* 3380:3602 */             if (local_Request.isRMI()) {
/* 3381:3604 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3382:     */             }
/* 3383:3608 */             if (bool1)
/* 3384:     */             {
/* 3385:3610 */               if (localObject5 != null) {
/* 3386:3610 */                 throw ((FFSException)localObject5);
/* 3387:     */               }
/* 3388:     */             }
/* 3389:     */             else {
/* 3390:3614 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3391:     */             }
/* 3392:     */           }
/* 3393:     */         }
/* 3394:3619 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3395:     */       }
/* 3396:     */       catch (SystemException localSystemException)
/* 3397:     */       {
/* 3398:3623 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3399:     */       }
/* 3400:     */       finally
/* 3401:     */       {
/* 3402:3627 */         if (local_Request != null) {
/* 3403:3629 */           local_Request.close();
/* 3404:     */         }
/* 3405:3631 */         if (bool1) {
/* 3406:3632 */           localLocalStack.pop(localLocalFrame);
/* 3407:     */         }
/* 3408:3633 */         localLocalStack.setArgsOnLocal(bool2);
/* 3409:     */       }
/* 3410:     */     }
/* 3411:     */   }
/* 3412:     */   
/* 3413:     */   public PayeeInfo updateGlobalPayee(PayeeInfo paramPayeeInfo)
/* 3414:     */     throws java.rmi.RemoteException, FFSException
/* 3415:     */   {
/* 3416:3642 */     for (int i = 1;; i++)
/* 3417:     */     {
/* 3418:3644 */       _Request local_Request = null;
/* 3419:3645 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3420:3646 */       boolean bool1 = false;
/* 3421:3647 */       LocalFrame localLocalFrame = null;
/* 3422:3648 */       boolean bool2 = false;
/* 3423:     */       try
/* 3424:     */       {
/* 3425:3651 */         local_Request = __request("updateGlobalPayee");
/* 3426:3652 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3427:3653 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3428:3654 */         localLocalStack.setArgsOnLocal(bool1);
/* 3429:3656 */         if (bool1)
/* 3430:     */         {
/* 3431:3658 */           localLocalFrame = new LocalFrame(1);
/* 3432:3659 */           localLocalStack.push(localLocalFrame);
/* 3433:     */         }
/* 3434:3661 */         if (!bool1)
/* 3435:     */         {
/* 3436:3663 */           localObject4 = local_Request.getOutputStream();
/* 3437:3664 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 3438:     */         }
/* 3439:     */         else
/* 3440:     */         {
/* 3441:3668 */           localObject4 = local_Request.getOutputStream();
/* 3442:3669 */           localLocalFrame.add(paramPayeeInfo);
/* 3443:     */         }
/* 3444:3671 */         local_Request.invoke();
/* 3445:     */         Object localObject1;
/* 3446:3672 */         if (bool1)
/* 3447:     */         {
/* 3448:3674 */           localObject4 = null;
/* 3449:3675 */           localObject5 = localLocalFrame.getResult();
/* 3450:3676 */           if (localObject5 != null) {
/* 3451:3678 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 3452:     */           }
/* 3453:3680 */           return localObject4;
/* 3454:     */         }
/* 3455:3682 */         Object localObject4 = local_Request.getInputStream();
/* 3456:     */         
/* 3457:3684 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 3458:3685 */         return localObject5;
/* 3459:     */       }
/* 3460:     */       catch (TRANSIENT localTRANSIENT)
/* 3461:     */       {
/* 3462:3689 */         if (i == 10) {
/* 3463:3691 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3464:     */         }
/* 3465:     */       }
/* 3466:     */       catch (UserException localUserException)
/* 3467:     */       {
/* 3468:     */         Object localObject5;
/* 3469:3696 */         if (local_Request.isRMI())
/* 3470:     */         {
/* 3471:3698 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3472:3700 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3473:     */           }
/* 3474:     */         }
/* 3475:     */         else
/* 3476:     */         {
/* 3477:3705 */           localObject5 = null;
/* 3478:3706 */           if (bool1)
/* 3479:     */           {
/* 3480:3708 */             localObject5 = localLocalFrame.getException();
/* 3481:3709 */             if (localObject5 != null) {
/* 3482:3711 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 3483:     */             }
/* 3484:     */           }
/* 3485:3714 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3486:     */           {
/* 3487:3716 */             if (local_Request.isRMI()) {
/* 3488:3718 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3489:     */             }
/* 3490:3722 */             if (bool1)
/* 3491:     */             {
/* 3492:3724 */               if (localObject5 != null) {
/* 3493:3724 */                 throw ((FFSException)localObject5);
/* 3494:     */               }
/* 3495:     */             }
/* 3496:     */             else {
/* 3497:3728 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3498:     */             }
/* 3499:     */           }
/* 3500:     */         }
/* 3501:3733 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3502:     */       }
/* 3503:     */       catch (SystemException localSystemException)
/* 3504:     */       {
/* 3505:3737 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3506:     */       }
/* 3507:     */       finally
/* 3508:     */       {
/* 3509:3741 */         if (local_Request != null) {
/* 3510:3743 */           local_Request.close();
/* 3511:     */         }
/* 3512:3745 */         if (bool1) {
/* 3513:3746 */           localLocalStack.pop(localLocalFrame);
/* 3514:     */         }
/* 3515:3747 */         localLocalStack.setArgsOnLocal(bool2);
/* 3516:     */       }
/* 3517:     */     }
/* 3518:     */   }
/* 3519:     */   
/* 3520:     */   public String addPayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
/* 3521:     */     throws java.rmi.RemoteException, FFSException
/* 3522:     */   {
/* 3523:3758 */     for (int i = 1;; i++)
/* 3524:     */     {
/* 3525:3760 */       _Request local_Request = null;
/* 3526:3761 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3527:3762 */       boolean bool1 = false;
/* 3528:3763 */       LocalFrame localLocalFrame = null;
/* 3529:3764 */       boolean bool2 = false;
/* 3530:     */       try
/* 3531:     */       {
/* 3532:3767 */         local_Request = __request("addPayee");
/* 3533:3768 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3534:3769 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3535:3770 */         localLocalStack.setArgsOnLocal(bool1);
/* 3536:3772 */         if (bool1)
/* 3537:     */         {
/* 3538:3774 */           localLocalFrame = new LocalFrame(3);
/* 3539:3775 */           localLocalStack.push(localLocalFrame);
/* 3540:     */         }
/* 3541:3777 */         if (!bool1)
/* 3542:     */         {
/* 3543:3779 */           localObject4 = local_Request.getOutputStream();
/* 3544:3780 */           if (local_Request.isRMI()) {
/* 3545:3780 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 3546:     */           } else {
/* 3547:3780 */             FFSDBPropertiesHelper.write((OutputStream)localObject4, paramFFSDBProperties);
/* 3548:     */           }
/* 3549:3781 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 3550:3782 */           local_Request.write_value(paramPayeeRouteInfo, PayeeRouteInfo.class);
/* 3551:     */         }
/* 3552:     */         else
/* 3553:     */         {
/* 3554:3786 */           localObject4 = local_Request.getOutputStream();
/* 3555:3787 */           localLocalFrame.add(paramFFSDBProperties);
/* 3556:3788 */           localLocalFrame.add(paramPayeeInfo);
/* 3557:3789 */           localLocalFrame.add(paramPayeeRouteInfo);
/* 3558:     */         }
/* 3559:3791 */         local_Request.invoke();
/* 3560:     */         Object localObject1;
/* 3561:3792 */         if (bool1)
/* 3562:     */         {
/* 3563:3794 */           localObject4 = null;
/* 3564:3795 */           localObject4 = (String)localLocalFrame.getResult();
/* 3565:3796 */           return localObject4;
/* 3566:     */         }
/* 3567:3798 */         Object localObject4 = local_Request.getInputStream();
/* 3568:     */         
/* 3569:3800 */         localObject5 = local_Request.read_string();
/* 3570:3801 */         return localObject5;
/* 3571:     */       }
/* 3572:     */       catch (TRANSIENT localTRANSIENT)
/* 3573:     */       {
/* 3574:3805 */         if (i == 10) {
/* 3575:3807 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3576:     */         }
/* 3577:     */       }
/* 3578:     */       catch (UserException localUserException)
/* 3579:     */       {
/* 3580:     */         Object localObject5;
/* 3581:3812 */         if (local_Request.isRMI())
/* 3582:     */         {
/* 3583:3814 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3584:3816 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3585:     */           }
/* 3586:     */         }
/* 3587:     */         else
/* 3588:     */         {
/* 3589:3821 */           localObject5 = null;
/* 3590:3822 */           if (bool1)
/* 3591:     */           {
/* 3592:3824 */             localObject5 = localLocalFrame.getException();
/* 3593:3825 */             if (localObject5 != null) {
/* 3594:3827 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 3595:     */             }
/* 3596:     */           }
/* 3597:3830 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3598:     */           {
/* 3599:3832 */             if (local_Request.isRMI()) {
/* 3600:3834 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3601:     */             }
/* 3602:3838 */             if (bool1)
/* 3603:     */             {
/* 3604:3840 */               if (localObject5 != null) {
/* 3605:3840 */                 throw ((FFSException)localObject5);
/* 3606:     */               }
/* 3607:     */             }
/* 3608:     */             else {
/* 3609:3844 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3610:     */             }
/* 3611:     */           }
/* 3612:     */         }
/* 3613:3849 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3614:     */       }
/* 3615:     */       catch (SystemException localSystemException)
/* 3616:     */       {
/* 3617:3853 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3618:     */       }
/* 3619:     */       finally
/* 3620:     */       {
/* 3621:3857 */         if (local_Request != null) {
/* 3622:3859 */           local_Request.close();
/* 3623:     */         }
/* 3624:3861 */         if (bool1) {
/* 3625:3862 */           localLocalStack.pop(localLocalFrame);
/* 3626:     */         }
/* 3627:3863 */         localLocalStack.setArgsOnLocal(bool2);
/* 3628:     */       }
/* 3629:     */     }
/* 3630:     */   }
/* 3631:     */   
/* 3632:     */   public void updatePayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
/* 3633:     */     throws java.rmi.RemoteException, FFSException
/* 3634:     */   {
/* 3635:3874 */     for (int i = 1;; i++)
/* 3636:     */     {
/* 3637:3876 */       _Request local_Request = null;
/* 3638:3877 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3639:3878 */       boolean bool1 = false;
/* 3640:3879 */       LocalFrame localLocalFrame = null;
/* 3641:3880 */       boolean bool2 = false;
/* 3642:     */       try
/* 3643:     */       {
/* 3644:3883 */         local_Request = __request("updatePayee");
/* 3645:3884 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3646:3885 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3647:3886 */         localLocalStack.setArgsOnLocal(bool1);
/* 3648:3888 */         if (bool1)
/* 3649:     */         {
/* 3650:3890 */           localLocalFrame = new LocalFrame(3);
/* 3651:3891 */           localLocalStack.push(localLocalFrame);
/* 3652:     */         }
/* 3653:     */         OutputStream localOutputStream;
/* 3654:3893 */         if (!bool1)
/* 3655:     */         {
/* 3656:3895 */           localOutputStream = local_Request.getOutputStream();
/* 3657:3896 */           if (local_Request.isRMI()) {
/* 3658:3896 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 3659:     */           } else {
/* 3660:3896 */             FFSDBPropertiesHelper.write(localOutputStream, paramFFSDBProperties);
/* 3661:     */           }
/* 3662:3897 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 3663:3898 */           local_Request.write_value(paramPayeeRouteInfo, PayeeRouteInfo.class);
/* 3664:     */         }
/* 3665:     */         else
/* 3666:     */         {
/* 3667:3902 */           localOutputStream = local_Request.getOutputStream();
/* 3668:3903 */           localLocalFrame.add(paramFFSDBProperties);
/* 3669:3904 */           localLocalFrame.add(paramPayeeInfo);
/* 3670:3905 */           localLocalFrame.add(paramPayeeRouteInfo);
/* 3671:     */         }
/* 3672:3907 */         local_Request.invoke();
/* 3673:3908 */         return;
/* 3674:     */       }
/* 3675:     */       catch (TRANSIENT localTRANSIENT)
/* 3676:     */       {
/* 3677:3912 */         if (i == 10) {
/* 3678:3914 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3679:     */         }
/* 3680:     */       }
/* 3681:     */       catch (UserException localUserException)
/* 3682:     */       {
/* 3683:3919 */         if (local_Request.isRMI())
/* 3684:     */         {
/* 3685:3921 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3686:3923 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3687:     */           }
/* 3688:     */         }
/* 3689:     */         else
/* 3690:     */         {
/* 3691:3928 */           Throwable localThrowable = null;
/* 3692:3929 */           if (bool1)
/* 3693:     */           {
/* 3694:3931 */             localThrowable = localLocalFrame.getException();
/* 3695:3932 */             if (localThrowable != null) {
/* 3696:3934 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 3697:     */             }
/* 3698:     */           }
/* 3699:3937 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3700:     */           {
/* 3701:3939 */             if (local_Request.isRMI()) {
/* 3702:3941 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3703:     */             }
/* 3704:3945 */             if (bool1)
/* 3705:     */             {
/* 3706:3947 */               if (localThrowable != null) {
/* 3707:3947 */                 throw ((FFSException)localThrowable);
/* 3708:     */               }
/* 3709:     */             }
/* 3710:     */             else {
/* 3711:3951 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3712:     */             }
/* 3713:     */           }
/* 3714:     */         }
/* 3715:3956 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3716:     */       }
/* 3717:     */       catch (SystemException localSystemException)
/* 3718:     */       {
/* 3719:3960 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3720:     */       }
/* 3721:     */       finally
/* 3722:     */       {
/* 3723:3964 */         if (local_Request != null) {
/* 3724:3966 */           local_Request.close();
/* 3725:     */         }
/* 3726:3968 */         if (bool1) {
/* 3727:3969 */           localLocalStack.pop(localLocalFrame);
/* 3728:     */         }
/* 3729:3970 */         localLocalStack.setArgsOnLocal(bool2);
/* 3730:     */       }
/* 3731:     */     }
/* 3732:     */   }
/* 3733:     */   
/* 3734:     */   public void deletePayee(FFSDBProperties paramFFSDBProperties, String paramString)
/* 3735:     */     throws java.rmi.RemoteException, FFSException
/* 3736:     */   {
/* 3737:3980 */     for (int i = 1;; i++)
/* 3738:     */     {
/* 3739:3982 */       _Request local_Request = null;
/* 3740:3983 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3741:3984 */       boolean bool1 = false;
/* 3742:3985 */       LocalFrame localLocalFrame = null;
/* 3743:3986 */       boolean bool2 = false;
/* 3744:     */       try
/* 3745:     */       {
/* 3746:3989 */         local_Request = __request("deletePayee");
/* 3747:3990 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3748:3991 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3749:3992 */         localLocalStack.setArgsOnLocal(bool1);
/* 3750:3994 */         if (bool1)
/* 3751:     */         {
/* 3752:3996 */           localLocalFrame = new LocalFrame(2);
/* 3753:3997 */           localLocalStack.push(localLocalFrame);
/* 3754:     */         }
/* 3755:     */         OutputStream localOutputStream;
/* 3756:3999 */         if (!bool1)
/* 3757:     */         {
/* 3758:4001 */           localOutputStream = local_Request.getOutputStream();
/* 3759:4002 */           if (local_Request.isRMI()) {
/* 3760:4002 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 3761:     */           } else {
/* 3762:4002 */             FFSDBPropertiesHelper.write(localOutputStream, paramFFSDBProperties);
/* 3763:     */           }
/* 3764:4003 */           local_Request.write_string(paramString);
/* 3765:     */         }
/* 3766:     */         else
/* 3767:     */         {
/* 3768:4007 */           localOutputStream = local_Request.getOutputStream();
/* 3769:4008 */           localLocalFrame.add(paramFFSDBProperties);
/* 3770:4009 */           localLocalFrame.add(paramString);
/* 3771:     */         }
/* 3772:4011 */         local_Request.invoke();
/* 3773:4012 */         return;
/* 3774:     */       }
/* 3775:     */       catch (TRANSIENT localTRANSIENT)
/* 3776:     */       {
/* 3777:4016 */         if (i == 10) {
/* 3778:4018 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3779:     */         }
/* 3780:     */       }
/* 3781:     */       catch (UserException localUserException)
/* 3782:     */       {
/* 3783:4023 */         if (local_Request.isRMI())
/* 3784:     */         {
/* 3785:4025 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3786:4027 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3787:     */           }
/* 3788:     */         }
/* 3789:     */         else
/* 3790:     */         {
/* 3791:4032 */           Throwable localThrowable = null;
/* 3792:4033 */           if (bool1)
/* 3793:     */           {
/* 3794:4035 */             localThrowable = localLocalFrame.getException();
/* 3795:4036 */             if (localThrowable != null) {
/* 3796:4038 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 3797:     */             }
/* 3798:     */           }
/* 3799:4041 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3800:     */           {
/* 3801:4043 */             if (local_Request.isRMI()) {
/* 3802:4045 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3803:     */             }
/* 3804:4049 */             if (bool1)
/* 3805:     */             {
/* 3806:4051 */               if (localThrowable != null) {
/* 3807:4051 */                 throw ((FFSException)localThrowable);
/* 3808:     */               }
/* 3809:     */             }
/* 3810:     */             else {
/* 3811:4055 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3812:     */             }
/* 3813:     */           }
/* 3814:     */         }
/* 3815:4060 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3816:     */       }
/* 3817:     */       catch (SystemException localSystemException)
/* 3818:     */       {
/* 3819:4064 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3820:     */       }
/* 3821:     */       finally
/* 3822:     */       {
/* 3823:4068 */         if (local_Request != null) {
/* 3824:4070 */           local_Request.close();
/* 3825:     */         }
/* 3826:4072 */         if (bool1) {
/* 3827:4073 */           localLocalStack.pop(localLocalFrame);
/* 3828:     */         }
/* 3829:4074 */         localLocalStack.setArgsOnLocal(bool2);
/* 3830:     */       }
/* 3831:     */     }
/* 3832:     */   }
/* 3833:     */   
/* 3834:     */   public PayeeRouteInfo getPayeeRoute(FFSDBProperties paramFFSDBProperties, String paramString, int paramInt)
/* 3835:     */     throws java.rmi.RemoteException, FFSException
/* 3836:     */   {
/* 3837:4085 */     for (int i = 1;; i++)
/* 3838:     */     {
/* 3839:4087 */       _Request local_Request = null;
/* 3840:4088 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3841:4089 */       boolean bool1 = false;
/* 3842:4090 */       LocalFrame localLocalFrame = null;
/* 3843:4091 */       boolean bool2 = false;
/* 3844:     */       try
/* 3845:     */       {
/* 3846:4094 */         local_Request = __request("getPayeeRoute");
/* 3847:4095 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3848:4096 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3849:4097 */         localLocalStack.setArgsOnLocal(bool1);
/* 3850:4099 */         if (bool1)
/* 3851:     */         {
/* 3852:4101 */           localLocalFrame = new LocalFrame(3);
/* 3853:4102 */           localLocalStack.push(localLocalFrame);
/* 3854:     */         }
/* 3855:4104 */         if (!bool1)
/* 3856:     */         {
/* 3857:4106 */           localObject4 = local_Request.getOutputStream();
/* 3858:4107 */           if (local_Request.isRMI()) {
/* 3859:4107 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 3860:     */           } else {
/* 3861:4107 */             FFSDBPropertiesHelper.write((OutputStream)localObject4, paramFFSDBProperties);
/* 3862:     */           }
/* 3863:4108 */           local_Request.write_string(paramString);
/* 3864:4109 */           ((OutputStream)localObject4).write_long(paramInt);
/* 3865:     */         }
/* 3866:     */         else
/* 3867:     */         {
/* 3868:4113 */           localObject4 = local_Request.getOutputStream();
/* 3869:4114 */           localLocalFrame.add(paramFFSDBProperties);
/* 3870:4115 */           localLocalFrame.add(paramString);
/* 3871:4116 */           localLocalFrame.add(paramInt);
/* 3872:     */         }
/* 3873:4118 */         local_Request.invoke();
/* 3874:     */         Object localObject1;
/* 3875:4119 */         if (bool1)
/* 3876:     */         {
/* 3877:4121 */           localObject4 = null;
/* 3878:4122 */           localObject5 = localLocalFrame.getResult();
/* 3879:4123 */           if (localObject5 != null) {
/* 3880:4125 */             localObject4 = (PayeeRouteInfo)ObjectVal.clone(localObject5);
/* 3881:     */           }
/* 3882:4127 */           return localObject4;
/* 3883:     */         }
/* 3884:4129 */         Object localObject4 = local_Request.getInputStream();
/* 3885:     */         
/* 3886:4131 */         localObject5 = (PayeeRouteInfo)local_Request.read_value(PayeeRouteInfo.class);
/* 3887:4132 */         return localObject5;
/* 3888:     */       }
/* 3889:     */       catch (TRANSIENT localTRANSIENT)
/* 3890:     */       {
/* 3891:4136 */         if (i == 10) {
/* 3892:4138 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3893:     */         }
/* 3894:     */       }
/* 3895:     */       catch (UserException localUserException)
/* 3896:     */       {
/* 3897:     */         Object localObject5;
/* 3898:4143 */         if (local_Request.isRMI())
/* 3899:     */         {
/* 3900:4145 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 3901:4147 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3902:     */           }
/* 3903:     */         }
/* 3904:     */         else
/* 3905:     */         {
/* 3906:4152 */           localObject5 = null;
/* 3907:4153 */           if (bool1)
/* 3908:     */           {
/* 3909:4155 */             localObject5 = localLocalFrame.getException();
/* 3910:4156 */             if (localObject5 != null) {
/* 3911:4158 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 3912:     */             }
/* 3913:     */           }
/* 3914:4161 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 3915:     */           {
/* 3916:4163 */             if (local_Request.isRMI()) {
/* 3917:4165 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 3918:     */             }
/* 3919:4169 */             if (bool1)
/* 3920:     */             {
/* 3921:4171 */               if (localObject5 != null) {
/* 3922:4171 */                 throw ((FFSException)localObject5);
/* 3923:     */               }
/* 3924:     */             }
/* 3925:     */             else {
/* 3926:4175 */               throw FFSExceptionHelper.read(localUserException.input);
/* 3927:     */             }
/* 3928:     */           }
/* 3929:     */         }
/* 3930:4180 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3931:     */       }
/* 3932:     */       catch (SystemException localSystemException)
/* 3933:     */       {
/* 3934:4184 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3935:     */       }
/* 3936:     */       finally
/* 3937:     */       {
/* 3938:4188 */         if (local_Request != null) {
/* 3939:4190 */           local_Request.close();
/* 3940:     */         }
/* 3941:4192 */         if (bool1) {
/* 3942:4193 */           localLocalStack.pop(localLocalFrame);
/* 3943:     */         }
/* 3944:4194 */         localLocalStack.setArgsOnLocal(bool2);
/* 3945:     */       }
/* 3946:     */     }
/* 3947:     */   }
/* 3948:     */   
/* 3949:     */   public PayeeInfo findPayeeByID(FFSDBProperties paramFFSDBProperties, String paramString)
/* 3950:     */     throws java.rmi.RemoteException, FFSException
/* 3951:     */   {
/* 3952:4204 */     for (int i = 1;; i++)
/* 3953:     */     {
/* 3954:4206 */       _Request local_Request = null;
/* 3955:4207 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3956:4208 */       boolean bool1 = false;
/* 3957:4209 */       LocalFrame localLocalFrame = null;
/* 3958:4210 */       boolean bool2 = false;
/* 3959:     */       try
/* 3960:     */       {
/* 3961:4213 */         local_Request = __request("findPayeeByID");
/* 3962:4214 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3963:4215 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3964:4216 */         localLocalStack.setArgsOnLocal(bool1);
/* 3965:4218 */         if (bool1)
/* 3966:     */         {
/* 3967:4220 */           localLocalFrame = new LocalFrame(2);
/* 3968:4221 */           localLocalStack.push(localLocalFrame);
/* 3969:     */         }
/* 3970:4223 */         if (!bool1)
/* 3971:     */         {
/* 3972:4225 */           localObject4 = local_Request.getOutputStream();
/* 3973:4226 */           if (local_Request.isRMI()) {
/* 3974:4226 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 3975:     */           } else {
/* 3976:4226 */             FFSDBPropertiesHelper.write((OutputStream)localObject4, paramFFSDBProperties);
/* 3977:     */           }
/* 3978:4227 */           local_Request.write_string(paramString);
/* 3979:     */         }
/* 3980:     */         else
/* 3981:     */         {
/* 3982:4231 */           localObject4 = local_Request.getOutputStream();
/* 3983:4232 */           localLocalFrame.add(paramFFSDBProperties);
/* 3984:4233 */           localLocalFrame.add(paramString);
/* 3985:     */         }
/* 3986:4235 */         local_Request.invoke();
/* 3987:     */         Object localObject1;
/* 3988:4236 */         if (bool1)
/* 3989:     */         {
/* 3990:4238 */           localObject4 = null;
/* 3991:4239 */           localObject5 = localLocalFrame.getResult();
/* 3992:4240 */           if (localObject5 != null) {
/* 3993:4242 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 3994:     */           }
/* 3995:4244 */           return localObject4;
/* 3996:     */         }
/* 3997:4246 */         Object localObject4 = local_Request.getInputStream();
/* 3998:     */         
/* 3999:4248 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 4000:4249 */         return localObject5;
/* 4001:     */       }
/* 4002:     */       catch (TRANSIENT localTRANSIENT)
/* 4003:     */       {
/* 4004:4253 */         if (i == 10) {
/* 4005:4255 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4006:     */         }
/* 4007:     */       }
/* 4008:     */       catch (UserException localUserException)
/* 4009:     */       {
/* 4010:     */         Object localObject5;
/* 4011:4260 */         if (local_Request.isRMI())
/* 4012:     */         {
/* 4013:4262 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4014:4264 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4015:     */           }
/* 4016:     */         }
/* 4017:     */         else
/* 4018:     */         {
/* 4019:4269 */           localObject5 = null;
/* 4020:4270 */           if (bool1)
/* 4021:     */           {
/* 4022:4272 */             localObject5 = localLocalFrame.getException();
/* 4023:4273 */             if (localObject5 != null) {
/* 4024:4275 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 4025:     */             }
/* 4026:     */           }
/* 4027:4278 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4028:     */           {
/* 4029:4280 */             if (local_Request.isRMI()) {
/* 4030:4282 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4031:     */             }
/* 4032:4286 */             if (bool1)
/* 4033:     */             {
/* 4034:4288 */               if (localObject5 != null) {
/* 4035:4288 */                 throw ((FFSException)localObject5);
/* 4036:     */               }
/* 4037:     */             }
/* 4038:     */             else {
/* 4039:4292 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4040:     */             }
/* 4041:     */           }
/* 4042:     */         }
/* 4043:4297 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4044:     */       }
/* 4045:     */       catch (SystemException localSystemException)
/* 4046:     */       {
/* 4047:4301 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4048:     */       }
/* 4049:     */       finally
/* 4050:     */       {
/* 4051:4305 */         if (local_Request != null) {
/* 4052:4307 */           local_Request.close();
/* 4053:     */         }
/* 4054:4309 */         if (bool1) {
/* 4055:4310 */           localLocalStack.pop(localLocalFrame);
/* 4056:     */         }
/* 4057:4311 */         localLocalStack.setArgsOnLocal(bool2);
/* 4058:     */       }
/* 4059:     */     }
/* 4060:     */   }
/* 4061:     */   
/* 4062:     */   public FulfillmentInfo[] getAllFulfillmentInfo(FFSDBProperties paramFFSDBProperties)
/* 4063:     */     throws java.rmi.RemoteException, FFSException
/* 4064:     */   {
/* 4065:4320 */     for (int i = 1;; i++)
/* 4066:     */     {
/* 4067:4322 */       _Request local_Request = null;
/* 4068:4323 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4069:4324 */       boolean bool1 = false;
/* 4070:4325 */       LocalFrame localLocalFrame = null;
/* 4071:4326 */       boolean bool2 = false;
/* 4072:     */       try
/* 4073:     */       {
/* 4074:4329 */         local_Request = __request("getAllFulfillmentInfo");
/* 4075:4330 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4076:4331 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4077:4332 */         localLocalStack.setArgsOnLocal(bool1);
/* 4078:4334 */         if (bool1)
/* 4079:     */         {
/* 4080:4336 */           localLocalFrame = new LocalFrame(1);
/* 4081:4337 */           localLocalStack.push(localLocalFrame);
/* 4082:     */         }
/* 4083:4339 */         if (!bool1)
/* 4084:     */         {
/* 4085:4341 */           localObject4 = local_Request.getOutputStream();
/* 4086:4342 */           if (local_Request.isRMI()) {
/* 4087:4342 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 4088:     */           } else {
/* 4089:4342 */             FFSDBPropertiesHelper.write((OutputStream)localObject4, paramFFSDBProperties);
/* 4090:     */           }
/* 4091:     */         }
/* 4092:     */         else
/* 4093:     */         {
/* 4094:4346 */           localObject4 = local_Request.getOutputStream();
/* 4095:4347 */           localLocalFrame.add(paramFFSDBProperties);
/* 4096:     */         }
/* 4097:4349 */         local_Request.invoke();
/* 4098:     */         Object localObject1;
/* 4099:4350 */         if (bool1)
/* 4100:     */         {
/* 4101:4352 */           localObject4 = null;
/* 4102:4353 */           localObject5 = localLocalFrame.getResult();
/* 4103:4354 */           if (localObject5 != null) {
/* 4104:4356 */             localObject4 = (FulfillmentInfo[])ObjectVal.clone(localObject5);
/* 4105:     */           }
/* 4106:4358 */           return localObject4;
/* 4107:     */         }
/* 4108:4360 */         Object localObject4 = local_Request.getInputStream();
/* 4109:4362 */         if (local_Request.isRMI()) {
/* 4110:4362 */           localObject5 = (FulfillmentInfo[])local_Request.read_value(new FulfillmentInfo[0].getClass());
/* 4111:     */         } else {
/* 4112:4362 */           localObject5 = FulfillmentInfoSeqHelper.read((InputStream)localObject4);
/* 4113:     */         }
/* 4114:4363 */         return localObject5;
/* 4115:     */       }
/* 4116:     */       catch (TRANSIENT localTRANSIENT)
/* 4117:     */       {
/* 4118:4367 */         if (i == 10) {
/* 4119:4369 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4120:     */         }
/* 4121:     */       }
/* 4122:     */       catch (UserException localUserException)
/* 4123:     */       {
/* 4124:     */         Object localObject5;
/* 4125:4374 */         if (local_Request.isRMI())
/* 4126:     */         {
/* 4127:4376 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4128:4378 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4129:     */           }
/* 4130:     */         }
/* 4131:     */         else
/* 4132:     */         {
/* 4133:4383 */           localObject5 = null;
/* 4134:4384 */           if (bool1)
/* 4135:     */           {
/* 4136:4386 */             localObject5 = localLocalFrame.getException();
/* 4137:4387 */             if (localObject5 != null) {
/* 4138:4389 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 4139:     */             }
/* 4140:     */           }
/* 4141:4392 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4142:     */           {
/* 4143:4394 */             if (local_Request.isRMI()) {
/* 4144:4396 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4145:     */             }
/* 4146:4400 */             if (bool1)
/* 4147:     */             {
/* 4148:4402 */               if (localObject5 != null) {
/* 4149:4402 */                 throw ((FFSException)localObject5);
/* 4150:     */               }
/* 4151:     */             }
/* 4152:     */             else {
/* 4153:4406 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4154:     */             }
/* 4155:     */           }
/* 4156:     */         }
/* 4157:4411 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4158:     */       }
/* 4159:     */       catch (SystemException localSystemException)
/* 4160:     */       {
/* 4161:4415 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4162:     */       }
/* 4163:     */       finally
/* 4164:     */       {
/* 4165:4419 */         if (local_Request != null) {
/* 4166:4421 */           local_Request.close();
/* 4167:     */         }
/* 4168:4423 */         if (bool1) {
/* 4169:4424 */           localLocalStack.pop(localLocalFrame);
/* 4170:     */         }
/* 4171:4425 */         localLocalStack.setArgsOnLocal(bool2);
/* 4172:     */       }
/* 4173:     */     }
/* 4174:     */   }
/* 4175:     */   
/* 4176:     */   public FulfillmentInfo[] getFulfillmentSystems()
/* 4177:     */     throws java.rmi.RemoteException, FFSException
/* 4178:     */   {
/* 4179:4433 */     for (int i = 1;; i++)
/* 4180:     */     {
/* 4181:4435 */       _Request local_Request = null;
/* 4182:4436 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4183:4437 */       boolean bool1 = false;
/* 4184:4438 */       LocalFrame localLocalFrame = null;
/* 4185:4439 */       boolean bool2 = false;
/* 4186:     */       try
/* 4187:     */       {
/* 4188:4442 */         local_Request = __request("getFulfillmentSystems");
/* 4189:4443 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4190:4444 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4191:4445 */         localLocalStack.setArgsOnLocal(bool1);
/* 4192:4447 */         if (bool1)
/* 4193:     */         {
/* 4194:4449 */           localLocalFrame = new LocalFrame(0);
/* 4195:4450 */           localLocalStack.push(localLocalFrame);
/* 4196:     */         }
/* 4197:4458 */         local_Request.invoke();
/* 4198:     */         Object localObject1;
/* 4199:4459 */         if (bool1)
/* 4200:     */         {
/* 4201:4461 */           localObject4 = null;
/* 4202:4462 */           localObject5 = localLocalFrame.getResult();
/* 4203:4463 */           if (localObject5 != null) {
/* 4204:4465 */             localObject4 = (FulfillmentInfo[])ObjectVal.clone(localObject5);
/* 4205:     */           }
/* 4206:4467 */           return localObject4;
/* 4207:     */         }
/* 4208:4469 */         Object localObject4 = local_Request.getInputStream();
/* 4209:4471 */         if (local_Request.isRMI()) {
/* 4210:4471 */           localObject5 = (FulfillmentInfo[])local_Request.read_value(new FulfillmentInfo[0].getClass());
/* 4211:     */         } else {
/* 4212:4471 */           localObject5 = FulfillmentInfoSeqHelper.read((InputStream)localObject4);
/* 4213:     */         }
/* 4214:4472 */         return localObject5;
/* 4215:     */       }
/* 4216:     */       catch (TRANSIENT localTRANSIENT)
/* 4217:     */       {
/* 4218:4476 */         if (i == 10) {
/* 4219:4478 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4220:     */         }
/* 4221:     */       }
/* 4222:     */       catch (UserException localUserException)
/* 4223:     */       {
/* 4224:     */         Object localObject5;
/* 4225:4483 */         if (local_Request.isRMI())
/* 4226:     */         {
/* 4227:4485 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4228:4487 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4229:     */           }
/* 4230:     */         }
/* 4231:     */         else
/* 4232:     */         {
/* 4233:4492 */           localObject5 = null;
/* 4234:4493 */           if (bool1)
/* 4235:     */           {
/* 4236:4495 */             localObject5 = localLocalFrame.getException();
/* 4237:4496 */             if (localObject5 != null) {
/* 4238:4498 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 4239:     */             }
/* 4240:     */           }
/* 4241:4501 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4242:     */           {
/* 4243:4503 */             if (local_Request.isRMI()) {
/* 4244:4505 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4245:     */             }
/* 4246:4509 */             if (bool1)
/* 4247:     */             {
/* 4248:4511 */               if (localObject5 != null) {
/* 4249:4511 */                 throw ((FFSException)localObject5);
/* 4250:     */               }
/* 4251:     */             }
/* 4252:     */             else {
/* 4253:4515 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4254:     */             }
/* 4255:     */           }
/* 4256:     */         }
/* 4257:4520 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4258:     */       }
/* 4259:     */       catch (SystemException localSystemException)
/* 4260:     */       {
/* 4261:4524 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4262:     */       }
/* 4263:     */       finally
/* 4264:     */       {
/* 4265:4528 */         if (local_Request != null) {
/* 4266:4530 */           local_Request.close();
/* 4267:     */         }
/* 4268:4532 */         if (bool1) {
/* 4269:4533 */           localLocalStack.pop(localLocalFrame);
/* 4270:     */         }
/* 4271:4534 */         localLocalStack.setArgsOnLocal(bool2);
/* 4272:     */       }
/* 4273:     */     }
/* 4274:     */   }
/* 4275:     */   
/* 4276:     */   public String[] getGlobalPayeeGroups()
/* 4277:     */     throws java.rmi.RemoteException, FFSException
/* 4278:     */   {
/* 4279:4542 */     for (int i = 1;; i++)
/* 4280:     */     {
/* 4281:4544 */       _Request local_Request = null;
/* 4282:4545 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4283:4546 */       boolean bool1 = false;
/* 4284:4547 */       LocalFrame localLocalFrame = null;
/* 4285:4548 */       boolean bool2 = false;
/* 4286:     */       try
/* 4287:     */       {
/* 4288:4551 */         local_Request = __request("getGlobalPayeeGroups");
/* 4289:4552 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4290:4553 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4291:4554 */         localLocalStack.setArgsOnLocal(bool1);
/* 4292:4556 */         if (bool1)
/* 4293:     */         {
/* 4294:4558 */           localLocalFrame = new LocalFrame(0);
/* 4295:4559 */           localLocalStack.push(localLocalFrame);
/* 4296:     */         }
/* 4297:4567 */         local_Request.invoke();
/* 4298:     */         Object localObject1;
/* 4299:4568 */         if (bool1)
/* 4300:     */         {
/* 4301:4570 */           localObject4 = null;
/* 4302:4571 */           localObject5 = localLocalFrame.getResult();
/* 4303:4572 */           if (localObject5 != null) {
/* 4304:4574 */             localObject4 = (String[])ObjectVal.clone(localObject5);
/* 4305:     */           }
/* 4306:4576 */           return localObject4;
/* 4307:     */         }
/* 4308:4578 */         Object localObject4 = local_Request.getInputStream();
/* 4309:4580 */         if (local_Request.isRMI()) {
/* 4310:4580 */           localObject5 = (String[])local_Request.read_value(new String[0].getClass());
/* 4311:     */         } else {
/* 4312:4580 */           localObject5 = StringSeqHelper.read((InputStream)localObject4);
/* 4313:     */         }
/* 4314:4581 */         return localObject5;
/* 4315:     */       }
/* 4316:     */       catch (TRANSIENT localTRANSIENT)
/* 4317:     */       {
/* 4318:4585 */         if (i == 10) {
/* 4319:4587 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4320:     */         }
/* 4321:     */       }
/* 4322:     */       catch (UserException localUserException)
/* 4323:     */       {
/* 4324:     */         Object localObject5;
/* 4325:4592 */         if (local_Request.isRMI())
/* 4326:     */         {
/* 4327:4594 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4328:4596 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4329:     */           }
/* 4330:     */         }
/* 4331:     */         else
/* 4332:     */         {
/* 4333:4601 */           localObject5 = null;
/* 4334:4602 */           if (bool1)
/* 4335:     */           {
/* 4336:4604 */             localObject5 = localLocalFrame.getException();
/* 4337:4605 */             if (localObject5 != null) {
/* 4338:4607 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 4339:     */             }
/* 4340:     */           }
/* 4341:4610 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4342:     */           {
/* 4343:4612 */             if (local_Request.isRMI()) {
/* 4344:4614 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4345:     */             }
/* 4346:4618 */             if (bool1)
/* 4347:     */             {
/* 4348:4620 */               if (localObject5 != null) {
/* 4349:4620 */                 throw ((FFSException)localObject5);
/* 4350:     */               }
/* 4351:     */             }
/* 4352:     */             else {
/* 4353:4624 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4354:     */             }
/* 4355:     */           }
/* 4356:     */         }
/* 4357:4629 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4358:     */       }
/* 4359:     */       catch (SystemException localSystemException)
/* 4360:     */       {
/* 4361:4633 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4362:     */       }
/* 4363:     */       finally
/* 4364:     */       {
/* 4365:4637 */         if (local_Request != null) {
/* 4366:4639 */           local_Request.close();
/* 4367:     */         }
/* 4368:4641 */         if (bool1) {
/* 4369:4642 */           localLocalStack.pop(localLocalFrame);
/* 4370:     */         }
/* 4371:4643 */         localLocalStack.setArgsOnLocal(bool2);
/* 4372:     */       }
/* 4373:     */     }
/* 4374:     */   }
/* 4375:     */   
/* 4376:     */   public void addFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
/* 4377:     */     throws java.rmi.RemoteException, FFSException
/* 4378:     */   {
/* 4379:4653 */     for (int i = 1;; i++)
/* 4380:     */     {
/* 4381:4655 */       _Request local_Request = null;
/* 4382:4656 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4383:4657 */       boolean bool1 = false;
/* 4384:4658 */       LocalFrame localLocalFrame = null;
/* 4385:4659 */       boolean bool2 = false;
/* 4386:     */       try
/* 4387:     */       {
/* 4388:4662 */         local_Request = __request("addFulfillmentInfo");
/* 4389:4663 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4390:4664 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4391:4665 */         localLocalStack.setArgsOnLocal(bool1);
/* 4392:4667 */         if (bool1)
/* 4393:     */         {
/* 4394:4669 */           localLocalFrame = new LocalFrame(2);
/* 4395:4670 */           localLocalStack.push(localLocalFrame);
/* 4396:     */         }
/* 4397:     */         OutputStream localOutputStream;
/* 4398:4672 */         if (!bool1)
/* 4399:     */         {
/* 4400:4674 */           localOutputStream = local_Request.getOutputStream();
/* 4401:4675 */           if (local_Request.isRMI()) {
/* 4402:4675 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 4403:     */           } else {
/* 4404:4675 */             FFSDBPropertiesHelper.write(localOutputStream, paramFFSDBProperties);
/* 4405:     */           }
/* 4406:4676 */           local_Request.write_value(paramFulfillmentInfo, FulfillmentInfo.class);
/* 4407:     */         }
/* 4408:     */         else
/* 4409:     */         {
/* 4410:4680 */           localOutputStream = local_Request.getOutputStream();
/* 4411:4681 */           localLocalFrame.add(paramFFSDBProperties);
/* 4412:4682 */           localLocalFrame.add(paramFulfillmentInfo);
/* 4413:     */         }
/* 4414:4684 */         local_Request.invoke();
/* 4415:4685 */         return;
/* 4416:     */       }
/* 4417:     */       catch (TRANSIENT localTRANSIENT)
/* 4418:     */       {
/* 4419:4689 */         if (i == 10) {
/* 4420:4691 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4421:     */         }
/* 4422:     */       }
/* 4423:     */       catch (UserException localUserException)
/* 4424:     */       {
/* 4425:4696 */         if (local_Request.isRMI())
/* 4426:     */         {
/* 4427:4698 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4428:4700 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4429:     */           }
/* 4430:     */         }
/* 4431:     */         else
/* 4432:     */         {
/* 4433:4705 */           Throwable localThrowable = null;
/* 4434:4706 */           if (bool1)
/* 4435:     */           {
/* 4436:4708 */             localThrowable = localLocalFrame.getException();
/* 4437:4709 */             if (localThrowable != null) {
/* 4438:4711 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 4439:     */             }
/* 4440:     */           }
/* 4441:4714 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4442:     */           {
/* 4443:4716 */             if (local_Request.isRMI()) {
/* 4444:4718 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4445:     */             }
/* 4446:4722 */             if (bool1)
/* 4447:     */             {
/* 4448:4724 */               if (localThrowable != null) {
/* 4449:4724 */                 throw ((FFSException)localThrowable);
/* 4450:     */               }
/* 4451:     */             }
/* 4452:     */             else {
/* 4453:4728 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4454:     */             }
/* 4455:     */           }
/* 4456:     */         }
/* 4457:4733 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4458:     */       }
/* 4459:     */       catch (SystemException localSystemException)
/* 4460:     */       {
/* 4461:4737 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4462:     */       }
/* 4463:     */       finally
/* 4464:     */       {
/* 4465:4741 */         if (local_Request != null) {
/* 4466:4743 */           local_Request.close();
/* 4467:     */         }
/* 4468:4745 */         if (bool1) {
/* 4469:4746 */           localLocalStack.pop(localLocalFrame);
/* 4470:     */         }
/* 4471:4747 */         localLocalStack.setArgsOnLocal(bool2);
/* 4472:     */       }
/* 4473:     */     }
/* 4474:     */   }
/* 4475:     */   
/* 4476:     */   public void updateFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
/* 4477:     */     throws java.rmi.RemoteException, FFSException
/* 4478:     */   {
/* 4479:4757 */     for (int i = 1;; i++)
/* 4480:     */     {
/* 4481:4759 */       _Request local_Request = null;
/* 4482:4760 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4483:4761 */       boolean bool1 = false;
/* 4484:4762 */       LocalFrame localLocalFrame = null;
/* 4485:4763 */       boolean bool2 = false;
/* 4486:     */       try
/* 4487:     */       {
/* 4488:4766 */         local_Request = __request("updateFulfillmentInfo");
/* 4489:4767 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4490:4768 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4491:4769 */         localLocalStack.setArgsOnLocal(bool1);
/* 4492:4771 */         if (bool1)
/* 4493:     */         {
/* 4494:4773 */           localLocalFrame = new LocalFrame(2);
/* 4495:4774 */           localLocalStack.push(localLocalFrame);
/* 4496:     */         }
/* 4497:     */         OutputStream localOutputStream;
/* 4498:4776 */         if (!bool1)
/* 4499:     */         {
/* 4500:4778 */           localOutputStream = local_Request.getOutputStream();
/* 4501:4779 */           if (local_Request.isRMI()) {
/* 4502:4779 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 4503:     */           } else {
/* 4504:4779 */             FFSDBPropertiesHelper.write(localOutputStream, paramFFSDBProperties);
/* 4505:     */           }
/* 4506:4780 */           local_Request.write_value(paramFulfillmentInfo, FulfillmentInfo.class);
/* 4507:     */         }
/* 4508:     */         else
/* 4509:     */         {
/* 4510:4784 */           localOutputStream = local_Request.getOutputStream();
/* 4511:4785 */           localLocalFrame.add(paramFFSDBProperties);
/* 4512:4786 */           localLocalFrame.add(paramFulfillmentInfo);
/* 4513:     */         }
/* 4514:4788 */         local_Request.invoke();
/* 4515:4789 */         return;
/* 4516:     */       }
/* 4517:     */       catch (TRANSIENT localTRANSIENT)
/* 4518:     */       {
/* 4519:4793 */         if (i == 10) {
/* 4520:4795 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4521:     */         }
/* 4522:     */       }
/* 4523:     */       catch (UserException localUserException)
/* 4524:     */       {
/* 4525:4800 */         if (local_Request.isRMI())
/* 4526:     */         {
/* 4527:4802 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4528:4804 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4529:     */           }
/* 4530:     */         }
/* 4531:     */         else
/* 4532:     */         {
/* 4533:4809 */           Throwable localThrowable = null;
/* 4534:4810 */           if (bool1)
/* 4535:     */           {
/* 4536:4812 */             localThrowable = localLocalFrame.getException();
/* 4537:4813 */             if (localThrowable != null) {
/* 4538:4815 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 4539:     */             }
/* 4540:     */           }
/* 4541:4818 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4542:     */           {
/* 4543:4820 */             if (local_Request.isRMI()) {
/* 4544:4822 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4545:     */             }
/* 4546:4826 */             if (bool1)
/* 4547:     */             {
/* 4548:4828 */               if (localThrowable != null) {
/* 4549:4828 */                 throw ((FFSException)localThrowable);
/* 4550:     */               }
/* 4551:     */             }
/* 4552:     */             else {
/* 4553:4832 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4554:     */             }
/* 4555:     */           }
/* 4556:     */         }
/* 4557:4837 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4558:     */       }
/* 4559:     */       catch (SystemException localSystemException)
/* 4560:     */       {
/* 4561:4841 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4562:     */       }
/* 4563:     */       finally
/* 4564:     */       {
/* 4565:4845 */         if (local_Request != null) {
/* 4566:4847 */           local_Request.close();
/* 4567:     */         }
/* 4568:4849 */         if (bool1) {
/* 4569:4850 */           localLocalStack.pop(localLocalFrame);
/* 4570:     */         }
/* 4571:4851 */         localLocalStack.setArgsOnLocal(bool2);
/* 4572:     */       }
/* 4573:     */     }
/* 4574:     */   }
/* 4575:     */   
/* 4576:     */   public void deleteFulfillmentInfo(FFSDBProperties paramFFSDBProperties, int paramInt)
/* 4577:     */     throws java.rmi.RemoteException, FFSException
/* 4578:     */   {
/* 4579:4861 */     for (int i = 1;; i++)
/* 4580:     */     {
/* 4581:4863 */       _Request local_Request = null;
/* 4582:4864 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4583:4865 */       boolean bool1 = false;
/* 4584:4866 */       LocalFrame localLocalFrame = null;
/* 4585:4867 */       boolean bool2 = false;
/* 4586:     */       try
/* 4587:     */       {
/* 4588:4870 */         local_Request = __request("deleteFulfillmentInfo");
/* 4589:4871 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4590:4872 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4591:4873 */         localLocalStack.setArgsOnLocal(bool1);
/* 4592:4875 */         if (bool1)
/* 4593:     */         {
/* 4594:4877 */           localLocalFrame = new LocalFrame(2);
/* 4595:4878 */           localLocalStack.push(localLocalFrame);
/* 4596:     */         }
/* 4597:     */         OutputStream localOutputStream;
/* 4598:4880 */         if (!bool1)
/* 4599:     */         {
/* 4600:4882 */           localOutputStream = local_Request.getOutputStream();
/* 4601:4883 */           if (local_Request.isRMI()) {
/* 4602:4883 */             local_Request.write_value(paramFFSDBProperties, FFSDBProperties.class);
/* 4603:     */           } else {
/* 4604:4883 */             FFSDBPropertiesHelper.write(localOutputStream, paramFFSDBProperties);
/* 4605:     */           }
/* 4606:4884 */           localOutputStream.write_long(paramInt);
/* 4607:     */         }
/* 4608:     */         else
/* 4609:     */         {
/* 4610:4888 */           localOutputStream = local_Request.getOutputStream();
/* 4611:4889 */           localLocalFrame.add(paramFFSDBProperties);
/* 4612:4890 */           localLocalFrame.add(paramInt);
/* 4613:     */         }
/* 4614:4892 */         local_Request.invoke();
/* 4615:4893 */         return;
/* 4616:     */       }
/* 4617:     */       catch (TRANSIENT localTRANSIENT)
/* 4618:     */       {
/* 4619:4897 */         if (i == 10) {
/* 4620:4899 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4621:     */         }
/* 4622:     */       }
/* 4623:     */       catch (UserException localUserException)
/* 4624:     */       {
/* 4625:4904 */         if (local_Request.isRMI())
/* 4626:     */         {
/* 4627:4906 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4628:4908 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4629:     */           }
/* 4630:     */         }
/* 4631:     */         else
/* 4632:     */         {
/* 4633:4913 */           Throwable localThrowable = null;
/* 4634:4914 */           if (bool1)
/* 4635:     */           {
/* 4636:4916 */             localThrowable = localLocalFrame.getException();
/* 4637:4917 */             if (localThrowable != null) {
/* 4638:4919 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 4639:     */             }
/* 4640:     */           }
/* 4641:4922 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4642:     */           {
/* 4643:4924 */             if (local_Request.isRMI()) {
/* 4644:4926 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4645:     */             }
/* 4646:4930 */             if (bool1)
/* 4647:     */             {
/* 4648:4932 */               if (localThrowable != null) {
/* 4649:4932 */                 throw ((FFSException)localThrowable);
/* 4650:     */               }
/* 4651:     */             }
/* 4652:     */             else {
/* 4653:4936 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4654:     */             }
/* 4655:     */           }
/* 4656:     */         }
/* 4657:4941 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4658:     */       }
/* 4659:     */       catch (SystemException localSystemException)
/* 4660:     */       {
/* 4661:4945 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4662:     */       }
/* 4663:     */       finally
/* 4664:     */       {
/* 4665:4949 */         if (local_Request != null) {
/* 4666:4951 */           local_Request.close();
/* 4667:     */         }
/* 4668:4953 */         if (bool1) {
/* 4669:4954 */           localLocalStack.pop(localLocalFrame);
/* 4670:     */         }
/* 4671:4955 */         localLocalStack.setArgsOnLocal(bool2);
/* 4672:     */       }
/* 4673:     */     }
/* 4674:     */   }
/* 4675:     */   
/* 4676:     */   public void setDebugLevel(int paramInt)
/* 4677:     */     throws java.rmi.RemoteException, FFSException
/* 4678:     */   {
/* 4679:4964 */     for (int i = 1;; i++)
/* 4680:     */     {
/* 4681:4966 */       _Request local_Request = null;
/* 4682:4967 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4683:4968 */       boolean bool = false;
/* 4684:     */       try
/* 4685:     */       {
/* 4686:4971 */         local_Request = __request("setDebugLevel");
/* 4687:4972 */         bool = localLocalStack.isArgsOnLocal();
/* 4688:4973 */         localLocalStack.setArgsOnLocal(false);
/* 4689:4974 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 4690:4975 */         localOutputStream.write_long(paramInt);
/* 4691:4976 */         local_Request.invoke();
/* 4692:4977 */         return;
/* 4693:     */       }
/* 4694:     */       catch (TRANSIENT localTRANSIENT)
/* 4695:     */       {
/* 4696:4981 */         if (i == 10) {
/* 4697:4983 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4698:     */         }
/* 4699:     */       }
/* 4700:     */       catch (UserException localUserException)
/* 4701:     */       {
/* 4702:4988 */         if (local_Request.isRMI())
/* 4703:     */         {
/* 4704:4990 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4705:4992 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4706:     */           }
/* 4707:     */         }
/* 4708:4997 */         else if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4709:     */         {
/* 4710:4999 */           if (local_Request.isRMI()) {
/* 4711:5001 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4712:     */           }
/* 4713:5005 */           throw FFSExceptionHelper.read(localUserException.input);
/* 4714:     */         }
/* 4715:5009 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4716:     */       }
/* 4717:     */       catch (SystemException localSystemException)
/* 4718:     */       {
/* 4719:5013 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4720:     */       }
/* 4721:     */       finally
/* 4722:     */       {
/* 4723:5017 */         if (local_Request != null) {
/* 4724:5019 */           local_Request.close();
/* 4725:     */         }
/* 4726:5021 */         localLocalStack.setArgsOnLocal(bool);
/* 4727:     */       }
/* 4728:     */     }
/* 4729:     */   }
/* 4730:     */   
/* 4731:     */   public ProcessingWindowInfo addProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
/* 4732:     */     throws java.rmi.RemoteException, FFSException
/* 4733:     */   {
/* 4734:5030 */     for (int i = 1;; i++)
/* 4735:     */     {
/* 4736:5032 */       _Request local_Request = null;
/* 4737:5033 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4738:5034 */       boolean bool1 = false;
/* 4739:5035 */       LocalFrame localLocalFrame = null;
/* 4740:5036 */       boolean bool2 = false;
/* 4741:     */       try
/* 4742:     */       {
/* 4743:5039 */         local_Request = __request("addProcessingWindow");
/* 4744:5040 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4745:5041 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4746:5042 */         localLocalStack.setArgsOnLocal(bool1);
/* 4747:5044 */         if (bool1)
/* 4748:     */         {
/* 4749:5046 */           localLocalFrame = new LocalFrame(1);
/* 4750:5047 */           localLocalStack.push(localLocalFrame);
/* 4751:     */         }
/* 4752:5049 */         if (!bool1)
/* 4753:     */         {
/* 4754:5051 */           localObject4 = local_Request.getOutputStream();
/* 4755:5052 */           local_Request.write_value(paramProcessingWindowInfo, ProcessingWindowInfo.class);
/* 4756:     */         }
/* 4757:     */         else
/* 4758:     */         {
/* 4759:5056 */           localObject4 = local_Request.getOutputStream();
/* 4760:5057 */           localLocalFrame.add(paramProcessingWindowInfo);
/* 4761:     */         }
/* 4762:5059 */         local_Request.invoke();
/* 4763:     */         Object localObject1;
/* 4764:5060 */         if (bool1)
/* 4765:     */         {
/* 4766:5062 */           localObject4 = null;
/* 4767:5063 */           localObject5 = localLocalFrame.getResult();
/* 4768:5064 */           if (localObject5 != null) {
/* 4769:5066 */             localObject4 = (ProcessingWindowInfo)ObjectVal.clone(localObject5);
/* 4770:     */           }
/* 4771:5068 */           return localObject4;
/* 4772:     */         }
/* 4773:5070 */         Object localObject4 = local_Request.getInputStream();
/* 4774:     */         
/* 4775:5072 */         localObject5 = (ProcessingWindowInfo)local_Request.read_value(ProcessingWindowInfo.class);
/* 4776:5073 */         return localObject5;
/* 4777:     */       }
/* 4778:     */       catch (TRANSIENT localTRANSIENT)
/* 4779:     */       {
/* 4780:5077 */         if (i == 10) {
/* 4781:5079 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4782:     */         }
/* 4783:     */       }
/* 4784:     */       catch (UserException localUserException)
/* 4785:     */       {
/* 4786:     */         Object localObject5;
/* 4787:5084 */         if (local_Request.isRMI())
/* 4788:     */         {
/* 4789:5086 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4790:5088 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4791:     */           }
/* 4792:     */         }
/* 4793:     */         else
/* 4794:     */         {
/* 4795:5093 */           localObject5 = null;
/* 4796:5094 */           if (bool1)
/* 4797:     */           {
/* 4798:5096 */             localObject5 = localLocalFrame.getException();
/* 4799:5097 */             if (localObject5 != null) {
/* 4800:5099 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 4801:     */             }
/* 4802:     */           }
/* 4803:5102 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4804:     */           {
/* 4805:5104 */             if (local_Request.isRMI()) {
/* 4806:5106 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4807:     */             }
/* 4808:5110 */             if (bool1)
/* 4809:     */             {
/* 4810:5112 */               if (localObject5 != null) {
/* 4811:5112 */                 throw ((FFSException)localObject5);
/* 4812:     */               }
/* 4813:     */             }
/* 4814:     */             else {
/* 4815:5116 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4816:     */             }
/* 4817:     */           }
/* 4818:     */         }
/* 4819:5121 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4820:     */       }
/* 4821:     */       catch (SystemException localSystemException)
/* 4822:     */       {
/* 4823:5125 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4824:     */       }
/* 4825:     */       finally
/* 4826:     */       {
/* 4827:5129 */         if (local_Request != null) {
/* 4828:5131 */           local_Request.close();
/* 4829:     */         }
/* 4830:5133 */         if (bool1) {
/* 4831:5134 */           localLocalStack.pop(localLocalFrame);
/* 4832:     */         }
/* 4833:5135 */         localLocalStack.setArgsOnLocal(bool2);
/* 4834:     */       }
/* 4835:     */     }
/* 4836:     */   }
/* 4837:     */   
/* 4838:     */   public ProcessingWindowInfo modProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
/* 4839:     */     throws java.rmi.RemoteException, FFSException
/* 4840:     */   {
/* 4841:5144 */     for (int i = 1;; i++)
/* 4842:     */     {
/* 4843:5146 */       _Request local_Request = null;
/* 4844:5147 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4845:5148 */       boolean bool1 = false;
/* 4846:5149 */       LocalFrame localLocalFrame = null;
/* 4847:5150 */       boolean bool2 = false;
/* 4848:     */       try
/* 4849:     */       {
/* 4850:5153 */         local_Request = __request("modProcessingWindow");
/* 4851:5154 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4852:5155 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4853:5156 */         localLocalStack.setArgsOnLocal(bool1);
/* 4854:5158 */         if (bool1)
/* 4855:     */         {
/* 4856:5160 */           localLocalFrame = new LocalFrame(1);
/* 4857:5161 */           localLocalStack.push(localLocalFrame);
/* 4858:     */         }
/* 4859:5163 */         if (!bool1)
/* 4860:     */         {
/* 4861:5165 */           localObject4 = local_Request.getOutputStream();
/* 4862:5166 */           local_Request.write_value(paramProcessingWindowInfo, ProcessingWindowInfo.class);
/* 4863:     */         }
/* 4864:     */         else
/* 4865:     */         {
/* 4866:5170 */           localObject4 = local_Request.getOutputStream();
/* 4867:5171 */           localLocalFrame.add(paramProcessingWindowInfo);
/* 4868:     */         }
/* 4869:5173 */         local_Request.invoke();
/* 4870:     */         Object localObject1;
/* 4871:5174 */         if (bool1)
/* 4872:     */         {
/* 4873:5176 */           localObject4 = null;
/* 4874:5177 */           localObject5 = localLocalFrame.getResult();
/* 4875:5178 */           if (localObject5 != null) {
/* 4876:5180 */             localObject4 = (ProcessingWindowInfo)ObjectVal.clone(localObject5);
/* 4877:     */           }
/* 4878:5182 */           return localObject4;
/* 4879:     */         }
/* 4880:5184 */         Object localObject4 = local_Request.getInputStream();
/* 4881:     */         
/* 4882:5186 */         localObject5 = (ProcessingWindowInfo)local_Request.read_value(ProcessingWindowInfo.class);
/* 4883:5187 */         return localObject5;
/* 4884:     */       }
/* 4885:     */       catch (TRANSIENT localTRANSIENT)
/* 4886:     */       {
/* 4887:5191 */         if (i == 10) {
/* 4888:5193 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4889:     */         }
/* 4890:     */       }
/* 4891:     */       catch (UserException localUserException)
/* 4892:     */       {
/* 4893:     */         Object localObject5;
/* 4894:5198 */         if (local_Request.isRMI())
/* 4895:     */         {
/* 4896:5200 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 4897:5202 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4898:     */           }
/* 4899:     */         }
/* 4900:     */         else
/* 4901:     */         {
/* 4902:5207 */           localObject5 = null;
/* 4903:5208 */           if (bool1)
/* 4904:     */           {
/* 4905:5210 */             localObject5 = localLocalFrame.getException();
/* 4906:5211 */             if (localObject5 != null) {
/* 4907:5213 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 4908:     */             }
/* 4909:     */           }
/* 4910:5216 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 4911:     */           {
/* 4912:5218 */             if (local_Request.isRMI()) {
/* 4913:5220 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 4914:     */             }
/* 4915:5224 */             if (bool1)
/* 4916:     */             {
/* 4917:5226 */               if (localObject5 != null) {
/* 4918:5226 */                 throw ((FFSException)localObject5);
/* 4919:     */               }
/* 4920:     */             }
/* 4921:     */             else {
/* 4922:5230 */               throw FFSExceptionHelper.read(localUserException.input);
/* 4923:     */             }
/* 4924:     */           }
/* 4925:     */         }
/* 4926:5235 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4927:     */       }
/* 4928:     */       catch (SystemException localSystemException)
/* 4929:     */       {
/* 4930:5239 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4931:     */       }
/* 4932:     */       finally
/* 4933:     */       {
/* 4934:5243 */         if (local_Request != null) {
/* 4935:5245 */           local_Request.close();
/* 4936:     */         }
/* 4937:5247 */         if (bool1) {
/* 4938:5248 */           localLocalStack.pop(localLocalFrame);
/* 4939:     */         }
/* 4940:5249 */         localLocalStack.setArgsOnLocal(bool2);
/* 4941:     */       }
/* 4942:     */     }
/* 4943:     */   }
/* 4944:     */   
/* 4945:     */   public ProcessingWindowInfo delProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
/* 4946:     */     throws java.rmi.RemoteException, FFSException
/* 4947:     */   {
/* 4948:5258 */     for (int i = 1;; i++)
/* 4949:     */     {
/* 4950:5260 */       _Request local_Request = null;
/* 4951:5261 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4952:5262 */       boolean bool1 = false;
/* 4953:5263 */       LocalFrame localLocalFrame = null;
/* 4954:5264 */       boolean bool2 = false;
/* 4955:     */       try
/* 4956:     */       {
/* 4957:5267 */         local_Request = __request("delProcessingWindow");
/* 4958:5268 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4959:5269 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4960:5270 */         localLocalStack.setArgsOnLocal(bool1);
/* 4961:5272 */         if (bool1)
/* 4962:     */         {
/* 4963:5274 */           localLocalFrame = new LocalFrame(1);
/* 4964:5275 */           localLocalStack.push(localLocalFrame);
/* 4965:     */         }
/* 4966:5277 */         if (!bool1)
/* 4967:     */         {
/* 4968:5279 */           localObject4 = local_Request.getOutputStream();
/* 4969:5280 */           local_Request.write_value(paramProcessingWindowInfo, ProcessingWindowInfo.class);
/* 4970:     */         }
/* 4971:     */         else
/* 4972:     */         {
/* 4973:5284 */           localObject4 = local_Request.getOutputStream();
/* 4974:5285 */           localLocalFrame.add(paramProcessingWindowInfo);
/* 4975:     */         }
/* 4976:5287 */         local_Request.invoke();
/* 4977:     */         Object localObject1;
/* 4978:5288 */         if (bool1)
/* 4979:     */         {
/* 4980:5290 */           localObject4 = null;
/* 4981:5291 */           localObject5 = localLocalFrame.getResult();
/* 4982:5292 */           if (localObject5 != null) {
/* 4983:5294 */             localObject4 = (ProcessingWindowInfo)ObjectVal.clone(localObject5);
/* 4984:     */           }
/* 4985:5296 */           return localObject4;
/* 4986:     */         }
/* 4987:5298 */         Object localObject4 = local_Request.getInputStream();
/* 4988:     */         
/* 4989:5300 */         localObject5 = (ProcessingWindowInfo)local_Request.read_value(ProcessingWindowInfo.class);
/* 4990:5301 */         return localObject5;
/* 4991:     */       }
/* 4992:     */       catch (TRANSIENT localTRANSIENT)
/* 4993:     */       {
/* 4994:5305 */         if (i == 10) {
/* 4995:5307 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4996:     */         }
/* 4997:     */       }
/* 4998:     */       catch (UserException localUserException)
/* 4999:     */       {
/* 5000:     */         Object localObject5;
/* 5001:5312 */         if (local_Request.isRMI())
/* 5002:     */         {
/* 5003:5314 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5004:5316 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5005:     */           }
/* 5006:     */         }
/* 5007:     */         else
/* 5008:     */         {
/* 5009:5321 */           localObject5 = null;
/* 5010:5322 */           if (bool1)
/* 5011:     */           {
/* 5012:5324 */             localObject5 = localLocalFrame.getException();
/* 5013:5325 */             if (localObject5 != null) {
/* 5014:5327 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 5015:     */             }
/* 5016:     */           }
/* 5017:5330 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5018:     */           {
/* 5019:5332 */             if (local_Request.isRMI()) {
/* 5020:5334 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5021:     */             }
/* 5022:5338 */             if (bool1)
/* 5023:     */             {
/* 5024:5340 */               if (localObject5 != null) {
/* 5025:5340 */                 throw ((FFSException)localObject5);
/* 5026:     */               }
/* 5027:     */             }
/* 5028:     */             else {
/* 5029:5344 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5030:     */             }
/* 5031:     */           }
/* 5032:     */         }
/* 5033:5349 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5034:     */       }
/* 5035:     */       catch (SystemException localSystemException)
/* 5036:     */       {
/* 5037:5353 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5038:     */       }
/* 5039:     */       finally
/* 5040:     */       {
/* 5041:5357 */         if (local_Request != null) {
/* 5042:5359 */           local_Request.close();
/* 5043:     */         }
/* 5044:5361 */         if (bool1) {
/* 5045:5362 */           localLocalStack.pop(localLocalFrame);
/* 5046:     */         }
/* 5047:5363 */         localLocalStack.setArgsOnLocal(bool2);
/* 5048:     */       }
/* 5049:     */     }
/* 5050:     */   }
/* 5051:     */   
/* 5052:     */   public ProcessingWindowList getProcessingWindows(ProcessingWindowList paramProcessingWindowList)
/* 5053:     */     throws java.rmi.RemoteException, FFSException
/* 5054:     */   {
/* 5055:5372 */     for (int i = 1;; i++)
/* 5056:     */     {
/* 5057:5374 */       _Request local_Request = null;
/* 5058:5375 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5059:5376 */       boolean bool1 = false;
/* 5060:5377 */       LocalFrame localLocalFrame = null;
/* 5061:5378 */       boolean bool2 = false;
/* 5062:     */       try
/* 5063:     */       {
/* 5064:5381 */         local_Request = __request("getProcessingWindows");
/* 5065:5382 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5066:5383 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5067:5384 */         localLocalStack.setArgsOnLocal(bool1);
/* 5068:5386 */         if (bool1)
/* 5069:     */         {
/* 5070:5388 */           localLocalFrame = new LocalFrame(1);
/* 5071:5389 */           localLocalStack.push(localLocalFrame);
/* 5072:     */         }
/* 5073:5391 */         if (!bool1)
/* 5074:     */         {
/* 5075:5393 */           localObject4 = local_Request.getOutputStream();
/* 5076:5394 */           local_Request.write_value(paramProcessingWindowList, ProcessingWindowList.class);
/* 5077:     */         }
/* 5078:     */         else
/* 5079:     */         {
/* 5080:5398 */           localObject4 = local_Request.getOutputStream();
/* 5081:5399 */           localLocalFrame.add(paramProcessingWindowList);
/* 5082:     */         }
/* 5083:5401 */         local_Request.invoke();
/* 5084:     */         Object localObject1;
/* 5085:5402 */         if (bool1)
/* 5086:     */         {
/* 5087:5404 */           localObject4 = null;
/* 5088:5405 */           localObject5 = localLocalFrame.getResult();
/* 5089:5406 */           if (localObject5 != null) {
/* 5090:5408 */             localObject4 = (ProcessingWindowList)ObjectVal.clone(localObject5);
/* 5091:     */           }
/* 5092:5410 */           return localObject4;
/* 5093:     */         }
/* 5094:5412 */         Object localObject4 = local_Request.getInputStream();
/* 5095:     */         
/* 5096:5414 */         localObject5 = (ProcessingWindowList)local_Request.read_value(ProcessingWindowList.class);
/* 5097:5415 */         return localObject5;
/* 5098:     */       }
/* 5099:     */       catch (TRANSIENT localTRANSIENT)
/* 5100:     */       {
/* 5101:5419 */         if (i == 10) {
/* 5102:5421 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5103:     */         }
/* 5104:     */       }
/* 5105:     */       catch (UserException localUserException)
/* 5106:     */       {
/* 5107:     */         Object localObject5;
/* 5108:5426 */         if (local_Request.isRMI())
/* 5109:     */         {
/* 5110:5428 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5111:5430 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5112:     */           }
/* 5113:     */         }
/* 5114:     */         else
/* 5115:     */         {
/* 5116:5435 */           localObject5 = null;
/* 5117:5436 */           if (bool1)
/* 5118:     */           {
/* 5119:5438 */             localObject5 = localLocalFrame.getException();
/* 5120:5439 */             if (localObject5 != null) {
/* 5121:5441 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 5122:     */             }
/* 5123:     */           }
/* 5124:5444 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5125:     */           {
/* 5126:5446 */             if (local_Request.isRMI()) {
/* 5127:5448 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5128:     */             }
/* 5129:5452 */             if (bool1)
/* 5130:     */             {
/* 5131:5454 */               if (localObject5 != null) {
/* 5132:5454 */                 throw ((FFSException)localObject5);
/* 5133:     */               }
/* 5134:     */             }
/* 5135:     */             else {
/* 5136:5458 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5137:     */             }
/* 5138:     */           }
/* 5139:     */         }
/* 5140:5463 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5141:     */       }
/* 5142:     */       catch (SystemException localSystemException)
/* 5143:     */       {
/* 5144:5467 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5145:     */       }
/* 5146:     */       finally
/* 5147:     */       {
/* 5148:5471 */         if (local_Request != null) {
/* 5149:5473 */           local_Request.close();
/* 5150:     */         }
/* 5151:5475 */         if (bool1) {
/* 5152:5476 */           localLocalStack.pop(localLocalFrame);
/* 5153:     */         }
/* 5154:5477 */         localLocalStack.setArgsOnLocal(bool2);
/* 5155:     */       }
/* 5156:     */     }
/* 5157:     */   }
/* 5158:     */   
/* 5159:     */   public InstructionType[] getScheduleConfigByCategory(InstructionType paramInstructionType)
/* 5160:     */     throws java.rmi.RemoteException, FFSException
/* 5161:     */   {
/* 5162:5486 */     for (int i = 1;; i++)
/* 5163:     */     {
/* 5164:5488 */       _Request local_Request = null;
/* 5165:5489 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5166:5490 */       boolean bool1 = false;
/* 5167:5491 */       LocalFrame localLocalFrame = null;
/* 5168:5492 */       boolean bool2 = false;
/* 5169:     */       try
/* 5170:     */       {
/* 5171:5495 */         local_Request = __request("getScheduleConfigByCategory");
/* 5172:5496 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5173:5497 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5174:5498 */         localLocalStack.setArgsOnLocal(bool1);
/* 5175:5500 */         if (bool1)
/* 5176:     */         {
/* 5177:5502 */           localLocalFrame = new LocalFrame(1);
/* 5178:5503 */           localLocalStack.push(localLocalFrame);
/* 5179:     */         }
/* 5180:5505 */         if (!bool1)
/* 5181:     */         {
/* 5182:5507 */           localObject4 = local_Request.getOutputStream();
/* 5183:5508 */           local_Request.write_value(paramInstructionType, InstructionType.class);
/* 5184:     */         }
/* 5185:     */         else
/* 5186:     */         {
/* 5187:5512 */           localObject4 = local_Request.getOutputStream();
/* 5188:5513 */           localLocalFrame.add(paramInstructionType);
/* 5189:     */         }
/* 5190:5515 */         local_Request.invoke();
/* 5191:     */         Object localObject1;
/* 5192:5516 */         if (bool1)
/* 5193:     */         {
/* 5194:5518 */           localObject4 = null;
/* 5195:5519 */           localObject5 = localLocalFrame.getResult();
/* 5196:5520 */           if (localObject5 != null) {
/* 5197:5522 */             localObject4 = (InstructionType[])ObjectVal.clone(localObject5);
/* 5198:     */           }
/* 5199:5524 */           return localObject4;
/* 5200:     */         }
/* 5201:5526 */         Object localObject4 = local_Request.getInputStream();
/* 5202:5528 */         if (local_Request.isRMI()) {
/* 5203:5528 */           localObject5 = (InstructionType[])local_Request.read_value(new InstructionType[0].getClass());
/* 5204:     */         } else {
/* 5205:5528 */           localObject5 = InstructionTypeSeqHelper.read((InputStream)localObject4);
/* 5206:     */         }
/* 5207:5529 */         return localObject5;
/* 5208:     */       }
/* 5209:     */       catch (TRANSIENT localTRANSIENT)
/* 5210:     */       {
/* 5211:5533 */         if (i == 10) {
/* 5212:5535 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5213:     */         }
/* 5214:     */       }
/* 5215:     */       catch (UserException localUserException)
/* 5216:     */       {
/* 5217:     */         Object localObject5;
/* 5218:5540 */         if (local_Request.isRMI())
/* 5219:     */         {
/* 5220:5542 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5221:5544 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5222:     */           }
/* 5223:     */         }
/* 5224:     */         else
/* 5225:     */         {
/* 5226:5549 */           localObject5 = null;
/* 5227:5550 */           if (bool1)
/* 5228:     */           {
/* 5229:5552 */             localObject5 = localLocalFrame.getException();
/* 5230:5553 */             if (localObject5 != null) {
/* 5231:5555 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 5232:     */             }
/* 5233:     */           }
/* 5234:5558 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5235:     */           {
/* 5236:5560 */             if (local_Request.isRMI()) {
/* 5237:5562 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5238:     */             }
/* 5239:5566 */             if (bool1)
/* 5240:     */             {
/* 5241:5568 */               if (localObject5 != null) {
/* 5242:5568 */                 throw ((FFSException)localObject5);
/* 5243:     */               }
/* 5244:     */             }
/* 5245:     */             else {
/* 5246:5572 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5247:     */             }
/* 5248:     */           }
/* 5249:     */         }
/* 5250:5577 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5251:     */       }
/* 5252:     */       catch (SystemException localSystemException)
/* 5253:     */       {
/* 5254:5581 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5255:     */       }
/* 5256:     */       finally
/* 5257:     */       {
/* 5258:5585 */         if (local_Request != null) {
/* 5259:5587 */           local_Request.close();
/* 5260:     */         }
/* 5261:5589 */         if (bool1) {
/* 5262:5590 */           localLocalStack.pop(localLocalFrame);
/* 5263:     */         }
/* 5264:5591 */         localLocalStack.setArgsOnLocal(bool2);
/* 5265:     */       }
/* 5266:     */     }
/* 5267:     */   }
/* 5268:     */   
/* 5269:     */   public void addScheduleConfig(InstructionType paramInstructionType)
/* 5270:     */     throws java.rmi.RemoteException, FFSException
/* 5271:     */   {
/* 5272:5600 */     for (int i = 1;; i++)
/* 5273:     */     {
/* 5274:5602 */       _Request local_Request = null;
/* 5275:5603 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5276:5604 */       boolean bool1 = false;
/* 5277:5605 */       LocalFrame localLocalFrame = null;
/* 5278:5606 */       boolean bool2 = false;
/* 5279:     */       try
/* 5280:     */       {
/* 5281:5609 */         local_Request = __request("addScheduleConfig");
/* 5282:5610 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5283:5611 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5284:5612 */         localLocalStack.setArgsOnLocal(bool1);
/* 5285:5614 */         if (bool1)
/* 5286:     */         {
/* 5287:5616 */           localLocalFrame = new LocalFrame(1);
/* 5288:5617 */           localLocalStack.push(localLocalFrame);
/* 5289:     */         }
/* 5290:     */         OutputStream localOutputStream;
/* 5291:5619 */         if (!bool1)
/* 5292:     */         {
/* 5293:5621 */           localOutputStream = local_Request.getOutputStream();
/* 5294:5622 */           local_Request.write_value(paramInstructionType, InstructionType.class);
/* 5295:     */         }
/* 5296:     */         else
/* 5297:     */         {
/* 5298:5626 */           localOutputStream = local_Request.getOutputStream();
/* 5299:5627 */           localLocalFrame.add(paramInstructionType);
/* 5300:     */         }
/* 5301:5629 */         local_Request.invoke();
/* 5302:5630 */         return;
/* 5303:     */       }
/* 5304:     */       catch (TRANSIENT localTRANSIENT)
/* 5305:     */       {
/* 5306:5634 */         if (i == 10) {
/* 5307:5636 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5308:     */         }
/* 5309:     */       }
/* 5310:     */       catch (UserException localUserException)
/* 5311:     */       {
/* 5312:5641 */         if (local_Request.isRMI())
/* 5313:     */         {
/* 5314:5643 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5315:5645 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5316:     */           }
/* 5317:     */         }
/* 5318:     */         else
/* 5319:     */         {
/* 5320:5650 */           Throwable localThrowable = null;
/* 5321:5651 */           if (bool1)
/* 5322:     */           {
/* 5323:5653 */             localThrowable = localLocalFrame.getException();
/* 5324:5654 */             if (localThrowable != null) {
/* 5325:5656 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 5326:     */             }
/* 5327:     */           }
/* 5328:5659 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5329:     */           {
/* 5330:5661 */             if (local_Request.isRMI()) {
/* 5331:5663 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5332:     */             }
/* 5333:5667 */             if (bool1)
/* 5334:     */             {
/* 5335:5669 */               if (localThrowable != null) {
/* 5336:5669 */                 throw ((FFSException)localThrowable);
/* 5337:     */               }
/* 5338:     */             }
/* 5339:     */             else {
/* 5340:5673 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5341:     */             }
/* 5342:     */           }
/* 5343:     */         }
/* 5344:5678 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5345:     */       }
/* 5346:     */       catch (SystemException localSystemException)
/* 5347:     */       {
/* 5348:5682 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5349:     */       }
/* 5350:     */       finally
/* 5351:     */       {
/* 5352:5686 */         if (local_Request != null) {
/* 5353:5688 */           local_Request.close();
/* 5354:     */         }
/* 5355:5690 */         if (bool1) {
/* 5356:5691 */           localLocalStack.pop(localLocalFrame);
/* 5357:     */         }
/* 5358:5692 */         localLocalStack.setArgsOnLocal(bool2);
/* 5359:     */       }
/* 5360:     */     }
/* 5361:     */   }
/* 5362:     */   
/* 5363:     */   public void deleteScheduleConfig(InstructionType paramInstructionType)
/* 5364:     */     throws java.rmi.RemoteException, FFSException
/* 5365:     */   {
/* 5366:5701 */     for (int i = 1;; i++)
/* 5367:     */     {
/* 5368:5703 */       _Request local_Request = null;
/* 5369:5704 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5370:5705 */       boolean bool1 = false;
/* 5371:5706 */       LocalFrame localLocalFrame = null;
/* 5372:5707 */       boolean bool2 = false;
/* 5373:     */       try
/* 5374:     */       {
/* 5375:5710 */         local_Request = __request("deleteScheduleConfig");
/* 5376:5711 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5377:5712 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5378:5713 */         localLocalStack.setArgsOnLocal(bool1);
/* 5379:5715 */         if (bool1)
/* 5380:     */         {
/* 5381:5717 */           localLocalFrame = new LocalFrame(1);
/* 5382:5718 */           localLocalStack.push(localLocalFrame);
/* 5383:     */         }
/* 5384:     */         OutputStream localOutputStream;
/* 5385:5720 */         if (!bool1)
/* 5386:     */         {
/* 5387:5722 */           localOutputStream = local_Request.getOutputStream();
/* 5388:5723 */           local_Request.write_value(paramInstructionType, InstructionType.class);
/* 5389:     */         }
/* 5390:     */         else
/* 5391:     */         {
/* 5392:5727 */           localOutputStream = local_Request.getOutputStream();
/* 5393:5728 */           localLocalFrame.add(paramInstructionType);
/* 5394:     */         }
/* 5395:5730 */         local_Request.invoke();
/* 5396:5731 */         return;
/* 5397:     */       }
/* 5398:     */       catch (TRANSIENT localTRANSIENT)
/* 5399:     */       {
/* 5400:5735 */         if (i == 10) {
/* 5401:5737 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5402:     */         }
/* 5403:     */       }
/* 5404:     */       catch (UserException localUserException)
/* 5405:     */       {
/* 5406:5742 */         if (local_Request.isRMI())
/* 5407:     */         {
/* 5408:5744 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5409:5746 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5410:     */           }
/* 5411:     */         }
/* 5412:     */         else
/* 5413:     */         {
/* 5414:5751 */           Throwable localThrowable = null;
/* 5415:5752 */           if (bool1)
/* 5416:     */           {
/* 5417:5754 */             localThrowable = localLocalFrame.getException();
/* 5418:5755 */             if (localThrowable != null) {
/* 5419:5757 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 5420:     */             }
/* 5421:     */           }
/* 5422:5760 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5423:     */           {
/* 5424:5762 */             if (local_Request.isRMI()) {
/* 5425:5764 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5426:     */             }
/* 5427:5768 */             if (bool1)
/* 5428:     */             {
/* 5429:5770 */               if (localThrowable != null) {
/* 5430:5770 */                 throw ((FFSException)localThrowable);
/* 5431:     */               }
/* 5432:     */             }
/* 5433:     */             else {
/* 5434:5774 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5435:     */             }
/* 5436:     */           }
/* 5437:     */         }
/* 5438:5779 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5439:     */       }
/* 5440:     */       catch (SystemException localSystemException)
/* 5441:     */       {
/* 5442:5783 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5443:     */       }
/* 5444:     */       finally
/* 5445:     */       {
/* 5446:5787 */         if (local_Request != null) {
/* 5447:5789 */           local_Request.close();
/* 5448:     */         }
/* 5449:5791 */         if (bool1) {
/* 5450:5792 */           localLocalStack.pop(localLocalFrame);
/* 5451:     */         }
/* 5452:5793 */         localLocalStack.setArgsOnLocal(bool2);
/* 5453:     */       }
/* 5454:     */     }
/* 5455:     */   }
/* 5456:     */   
/* 5457:     */   public CutOffInfo deleteCutOff(CutOffInfo paramCutOffInfo)
/* 5458:     */     throws java.rmi.RemoteException, FFSException
/* 5459:     */   {
/* 5460:5802 */     for (int i = 1;; i++)
/* 5461:     */     {
/* 5462:5804 */       _Request local_Request = null;
/* 5463:5805 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5464:5806 */       boolean bool1 = false;
/* 5465:5807 */       LocalFrame localLocalFrame = null;
/* 5466:5808 */       boolean bool2 = false;
/* 5467:     */       try
/* 5468:     */       {
/* 5469:5811 */         local_Request = __request("deleteCutOff");
/* 5470:5812 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5471:5813 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5472:5814 */         localLocalStack.setArgsOnLocal(bool1);
/* 5473:5816 */         if (bool1)
/* 5474:     */         {
/* 5475:5818 */           localLocalFrame = new LocalFrame(1);
/* 5476:5819 */           localLocalStack.push(localLocalFrame);
/* 5477:     */         }
/* 5478:5821 */         if (!bool1)
/* 5479:     */         {
/* 5480:5823 */           localObject4 = local_Request.getOutputStream();
/* 5481:5824 */           local_Request.write_value(paramCutOffInfo, CutOffInfo.class);
/* 5482:     */         }
/* 5483:     */         else
/* 5484:     */         {
/* 5485:5828 */           localObject4 = local_Request.getOutputStream();
/* 5486:5829 */           localLocalFrame.add(paramCutOffInfo);
/* 5487:     */         }
/* 5488:5831 */         local_Request.invoke();
/* 5489:     */         Object localObject1;
/* 5490:5832 */         if (bool1)
/* 5491:     */         {
/* 5492:5834 */           localObject4 = null;
/* 5493:5835 */           localObject5 = localLocalFrame.getResult();
/* 5494:5836 */           if (localObject5 != null) {
/* 5495:5838 */             localObject4 = (CutOffInfo)ObjectVal.clone(localObject5);
/* 5496:     */           }
/* 5497:5840 */           return localObject4;
/* 5498:     */         }
/* 5499:5842 */         Object localObject4 = local_Request.getInputStream();
/* 5500:     */         
/* 5501:5844 */         localObject5 = (CutOffInfo)local_Request.read_value(CutOffInfo.class);
/* 5502:5845 */         return localObject5;
/* 5503:     */       }
/* 5504:     */       catch (TRANSIENT localTRANSIENT)
/* 5505:     */       {
/* 5506:5849 */         if (i == 10) {
/* 5507:5851 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5508:     */         }
/* 5509:     */       }
/* 5510:     */       catch (UserException localUserException)
/* 5511:     */       {
/* 5512:     */         Object localObject5;
/* 5513:5856 */         if (local_Request.isRMI())
/* 5514:     */         {
/* 5515:5858 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5516:5860 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5517:     */           }
/* 5518:     */         }
/* 5519:     */         else
/* 5520:     */         {
/* 5521:5865 */           localObject5 = null;
/* 5522:5866 */           if (bool1)
/* 5523:     */           {
/* 5524:5868 */             localObject5 = localLocalFrame.getException();
/* 5525:5869 */             if (localObject5 != null) {
/* 5526:5871 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 5527:     */             }
/* 5528:     */           }
/* 5529:5874 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5530:     */           {
/* 5531:5876 */             if (local_Request.isRMI()) {
/* 5532:5878 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5533:     */             }
/* 5534:5882 */             if (bool1)
/* 5535:     */             {
/* 5536:5884 */               if (localObject5 != null) {
/* 5537:5884 */                 throw ((FFSException)localObject5);
/* 5538:     */               }
/* 5539:     */             }
/* 5540:     */             else {
/* 5541:5888 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5542:     */             }
/* 5543:     */           }
/* 5544:     */         }
/* 5545:5893 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5546:     */       }
/* 5547:     */       catch (SystemException localSystemException)
/* 5548:     */       {
/* 5549:5897 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5550:     */       }
/* 5551:     */       finally
/* 5552:     */       {
/* 5553:5901 */         if (local_Request != null) {
/* 5554:5903 */           local_Request.close();
/* 5555:     */         }
/* 5556:5905 */         if (bool1) {
/* 5557:5906 */           localLocalStack.pop(localLocalFrame);
/* 5558:     */         }
/* 5559:5907 */         localLocalStack.setArgsOnLocal(bool2);
/* 5560:     */       }
/* 5561:     */     }
/* 5562:     */   }
/* 5563:     */   
/* 5564:     */   public CutOffInfo addCutOff(CutOffInfo paramCutOffInfo)
/* 5565:     */     throws java.rmi.RemoteException, FFSException
/* 5566:     */   {
/* 5567:5916 */     for (int i = 1;; i++)
/* 5568:     */     {
/* 5569:5918 */       _Request local_Request = null;
/* 5570:5919 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5571:5920 */       boolean bool1 = false;
/* 5572:5921 */       LocalFrame localLocalFrame = null;
/* 5573:5922 */       boolean bool2 = false;
/* 5574:     */       try
/* 5575:     */       {
/* 5576:5925 */         local_Request = __request("addCutOff");
/* 5577:5926 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5578:5927 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5579:5928 */         localLocalStack.setArgsOnLocal(bool1);
/* 5580:5930 */         if (bool1)
/* 5581:     */         {
/* 5582:5932 */           localLocalFrame = new LocalFrame(1);
/* 5583:5933 */           localLocalStack.push(localLocalFrame);
/* 5584:     */         }
/* 5585:5935 */         if (!bool1)
/* 5586:     */         {
/* 5587:5937 */           localObject4 = local_Request.getOutputStream();
/* 5588:5938 */           local_Request.write_value(paramCutOffInfo, CutOffInfo.class);
/* 5589:     */         }
/* 5590:     */         else
/* 5591:     */         {
/* 5592:5942 */           localObject4 = local_Request.getOutputStream();
/* 5593:5943 */           localLocalFrame.add(paramCutOffInfo);
/* 5594:     */         }
/* 5595:5945 */         local_Request.invoke();
/* 5596:     */         Object localObject1;
/* 5597:5946 */         if (bool1)
/* 5598:     */         {
/* 5599:5948 */           localObject4 = null;
/* 5600:5949 */           localObject5 = localLocalFrame.getResult();
/* 5601:5950 */           if (localObject5 != null) {
/* 5602:5952 */             localObject4 = (CutOffInfo)ObjectVal.clone(localObject5);
/* 5603:     */           }
/* 5604:5954 */           return localObject4;
/* 5605:     */         }
/* 5606:5956 */         Object localObject4 = local_Request.getInputStream();
/* 5607:     */         
/* 5608:5958 */         localObject5 = (CutOffInfo)local_Request.read_value(CutOffInfo.class);
/* 5609:5959 */         return localObject5;
/* 5610:     */       }
/* 5611:     */       catch (TRANSIENT localTRANSIENT)
/* 5612:     */       {
/* 5613:5963 */         if (i == 10) {
/* 5614:5965 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5615:     */         }
/* 5616:     */       }
/* 5617:     */       catch (UserException localUserException)
/* 5618:     */       {
/* 5619:     */         Object localObject5;
/* 5620:5970 */         if (local_Request.isRMI())
/* 5621:     */         {
/* 5622:5972 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5623:5974 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5624:     */           }
/* 5625:     */         }
/* 5626:     */         else
/* 5627:     */         {
/* 5628:5979 */           localObject5 = null;
/* 5629:5980 */           if (bool1)
/* 5630:     */           {
/* 5631:5982 */             localObject5 = localLocalFrame.getException();
/* 5632:5983 */             if (localObject5 != null) {
/* 5633:5985 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 5634:     */             }
/* 5635:     */           }
/* 5636:5988 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5637:     */           {
/* 5638:5990 */             if (local_Request.isRMI()) {
/* 5639:5992 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5640:     */             }
/* 5641:5996 */             if (bool1)
/* 5642:     */             {
/* 5643:5998 */               if (localObject5 != null) {
/* 5644:5998 */                 throw ((FFSException)localObject5);
/* 5645:     */               }
/* 5646:     */             }
/* 5647:     */             else {
/* 5648:6002 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5649:     */             }
/* 5650:     */           }
/* 5651:     */         }
/* 5652:6007 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5653:     */       }
/* 5654:     */       catch (SystemException localSystemException)
/* 5655:     */       {
/* 5656:6011 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5657:     */       }
/* 5658:     */       finally
/* 5659:     */       {
/* 5660:6015 */         if (local_Request != null) {
/* 5661:6017 */           local_Request.close();
/* 5662:     */         }
/* 5663:6019 */         if (bool1) {
/* 5664:6020 */           localLocalStack.pop(localLocalFrame);
/* 5665:     */         }
/* 5666:6021 */         localLocalStack.setArgsOnLocal(bool2);
/* 5667:     */       }
/* 5668:     */     }
/* 5669:     */   }
/* 5670:     */   
/* 5671:     */   public CutOffInfo modCutOff(CutOffInfo paramCutOffInfo)
/* 5672:     */     throws java.rmi.RemoteException, FFSException
/* 5673:     */   {
/* 5674:6030 */     for (int i = 1;; i++)
/* 5675:     */     {
/* 5676:6032 */       _Request local_Request = null;
/* 5677:6033 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5678:6034 */       boolean bool1 = false;
/* 5679:6035 */       LocalFrame localLocalFrame = null;
/* 5680:6036 */       boolean bool2 = false;
/* 5681:     */       try
/* 5682:     */       {
/* 5683:6039 */         local_Request = __request("modCutOff");
/* 5684:6040 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5685:6041 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5686:6042 */         localLocalStack.setArgsOnLocal(bool1);
/* 5687:6044 */         if (bool1)
/* 5688:     */         {
/* 5689:6046 */           localLocalFrame = new LocalFrame(1);
/* 5690:6047 */           localLocalStack.push(localLocalFrame);
/* 5691:     */         }
/* 5692:6049 */         if (!bool1)
/* 5693:     */         {
/* 5694:6051 */           localObject4 = local_Request.getOutputStream();
/* 5695:6052 */           local_Request.write_value(paramCutOffInfo, CutOffInfo.class);
/* 5696:     */         }
/* 5697:     */         else
/* 5698:     */         {
/* 5699:6056 */           localObject4 = local_Request.getOutputStream();
/* 5700:6057 */           localLocalFrame.add(paramCutOffInfo);
/* 5701:     */         }
/* 5702:6059 */         local_Request.invoke();
/* 5703:     */         Object localObject1;
/* 5704:6060 */         if (bool1)
/* 5705:     */         {
/* 5706:6062 */           localObject4 = null;
/* 5707:6063 */           localObject5 = localLocalFrame.getResult();
/* 5708:6064 */           if (localObject5 != null) {
/* 5709:6066 */             localObject4 = (CutOffInfo)ObjectVal.clone(localObject5);
/* 5710:     */           }
/* 5711:6068 */           return localObject4;
/* 5712:     */         }
/* 5713:6070 */         Object localObject4 = local_Request.getInputStream();
/* 5714:     */         
/* 5715:6072 */         localObject5 = (CutOffInfo)local_Request.read_value(CutOffInfo.class);
/* 5716:6073 */         return localObject5;
/* 5717:     */       }
/* 5718:     */       catch (TRANSIENT localTRANSIENT)
/* 5719:     */       {
/* 5720:6077 */         if (i == 10) {
/* 5721:6079 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5722:     */         }
/* 5723:     */       }
/* 5724:     */       catch (UserException localUserException)
/* 5725:     */       {
/* 5726:     */         Object localObject5;
/* 5727:6084 */         if (local_Request.isRMI())
/* 5728:     */         {
/* 5729:6086 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5730:6088 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5731:     */           }
/* 5732:     */         }
/* 5733:     */         else
/* 5734:     */         {
/* 5735:6093 */           localObject5 = null;
/* 5736:6094 */           if (bool1)
/* 5737:     */           {
/* 5738:6096 */             localObject5 = localLocalFrame.getException();
/* 5739:6097 */             if (localObject5 != null) {
/* 5740:6099 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 5741:     */             }
/* 5742:     */           }
/* 5743:6102 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5744:     */           {
/* 5745:6104 */             if (local_Request.isRMI()) {
/* 5746:6106 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5747:     */             }
/* 5748:6110 */             if (bool1)
/* 5749:     */             {
/* 5750:6112 */               if (localObject5 != null) {
/* 5751:6112 */                 throw ((FFSException)localObject5);
/* 5752:     */               }
/* 5753:     */             }
/* 5754:     */             else {
/* 5755:6116 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5756:     */             }
/* 5757:     */           }
/* 5758:     */         }
/* 5759:6121 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5760:     */       }
/* 5761:     */       catch (SystemException localSystemException)
/* 5762:     */       {
/* 5763:6125 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5764:     */       }
/* 5765:     */       finally
/* 5766:     */       {
/* 5767:6129 */         if (local_Request != null) {
/* 5768:6131 */           local_Request.close();
/* 5769:     */         }
/* 5770:6133 */         if (bool1) {
/* 5771:6134 */           localLocalStack.pop(localLocalFrame);
/* 5772:     */         }
/* 5773:6135 */         localLocalStack.setArgsOnLocal(bool2);
/* 5774:     */       }
/* 5775:     */     }
/* 5776:     */   }
/* 5777:     */   
/* 5778:     */   public CutOffInfo getCutOff(CutOffInfo paramCutOffInfo)
/* 5779:     */     throws java.rmi.RemoteException, FFSException
/* 5780:     */   {
/* 5781:6144 */     for (int i = 1;; i++)
/* 5782:     */     {
/* 5783:6146 */       _Request local_Request = null;
/* 5784:6147 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5785:6148 */       boolean bool1 = false;
/* 5786:6149 */       LocalFrame localLocalFrame = null;
/* 5787:6150 */       boolean bool2 = false;
/* 5788:     */       try
/* 5789:     */       {
/* 5790:6153 */         local_Request = __request("getCutOff");
/* 5791:6154 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5792:6155 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5793:6156 */         localLocalStack.setArgsOnLocal(bool1);
/* 5794:6158 */         if (bool1)
/* 5795:     */         {
/* 5796:6160 */           localLocalFrame = new LocalFrame(1);
/* 5797:6161 */           localLocalStack.push(localLocalFrame);
/* 5798:     */         }
/* 5799:6163 */         if (!bool1)
/* 5800:     */         {
/* 5801:6165 */           localObject4 = local_Request.getOutputStream();
/* 5802:6166 */           local_Request.write_value(paramCutOffInfo, CutOffInfo.class);
/* 5803:     */         }
/* 5804:     */         else
/* 5805:     */         {
/* 5806:6170 */           localObject4 = local_Request.getOutputStream();
/* 5807:6171 */           localLocalFrame.add(paramCutOffInfo);
/* 5808:     */         }
/* 5809:6173 */         local_Request.invoke();
/* 5810:     */         Object localObject1;
/* 5811:6174 */         if (bool1)
/* 5812:     */         {
/* 5813:6176 */           localObject4 = null;
/* 5814:6177 */           localObject5 = localLocalFrame.getResult();
/* 5815:6178 */           if (localObject5 != null) {
/* 5816:6180 */             localObject4 = (CutOffInfo)ObjectVal.clone(localObject5);
/* 5817:     */           }
/* 5818:6182 */           return localObject4;
/* 5819:     */         }
/* 5820:6184 */         Object localObject4 = local_Request.getInputStream();
/* 5821:     */         
/* 5822:6186 */         localObject5 = (CutOffInfo)local_Request.read_value(CutOffInfo.class);
/* 5823:6187 */         return localObject5;
/* 5824:     */       }
/* 5825:     */       catch (TRANSIENT localTRANSIENT)
/* 5826:     */       {
/* 5827:6191 */         if (i == 10) {
/* 5828:6193 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5829:     */         }
/* 5830:     */       }
/* 5831:     */       catch (UserException localUserException)
/* 5832:     */       {
/* 5833:     */         Object localObject5;
/* 5834:6198 */         if (local_Request.isRMI())
/* 5835:     */         {
/* 5836:6200 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5837:6202 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5838:     */           }
/* 5839:     */         }
/* 5840:     */         else
/* 5841:     */         {
/* 5842:6207 */           localObject5 = null;
/* 5843:6208 */           if (bool1)
/* 5844:     */           {
/* 5845:6210 */             localObject5 = localLocalFrame.getException();
/* 5846:6211 */             if (localObject5 != null) {
/* 5847:6213 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 5848:     */             }
/* 5849:     */           }
/* 5850:6216 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5851:     */           {
/* 5852:6218 */             if (local_Request.isRMI()) {
/* 5853:6220 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5854:     */             }
/* 5855:6224 */             if (bool1)
/* 5856:     */             {
/* 5857:6226 */               if (localObject5 != null) {
/* 5858:6226 */                 throw ((FFSException)localObject5);
/* 5859:     */               }
/* 5860:     */             }
/* 5861:     */             else {
/* 5862:6230 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5863:     */             }
/* 5864:     */           }
/* 5865:     */         }
/* 5866:6235 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5867:     */       }
/* 5868:     */       catch (SystemException localSystemException)
/* 5869:     */       {
/* 5870:6239 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5871:     */       }
/* 5872:     */       finally
/* 5873:     */       {
/* 5874:6243 */         if (local_Request != null) {
/* 5875:6245 */           local_Request.close();
/* 5876:     */         }
/* 5877:6247 */         if (bool1) {
/* 5878:6248 */           localLocalStack.pop(localLocalFrame);
/* 5879:     */         }
/* 5880:6249 */         localLocalStack.setArgsOnLocal(bool2);
/* 5881:     */       }
/* 5882:     */     }
/* 5883:     */   }
/* 5884:     */   
/* 5885:     */   public CutOffInfoList getCutOffList(CutOffInfoList paramCutOffInfoList)
/* 5886:     */     throws java.rmi.RemoteException, FFSException
/* 5887:     */   {
/* 5888:6258 */     for (int i = 1;; i++)
/* 5889:     */     {
/* 5890:6260 */       _Request local_Request = null;
/* 5891:6261 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5892:6262 */       boolean bool1 = false;
/* 5893:6263 */       LocalFrame localLocalFrame = null;
/* 5894:6264 */       boolean bool2 = false;
/* 5895:     */       try
/* 5896:     */       {
/* 5897:6267 */         local_Request = __request("getCutOffList");
/* 5898:6268 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5899:6269 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5900:6270 */         localLocalStack.setArgsOnLocal(bool1);
/* 5901:6272 */         if (bool1)
/* 5902:     */         {
/* 5903:6274 */           localLocalFrame = new LocalFrame(1);
/* 5904:6275 */           localLocalStack.push(localLocalFrame);
/* 5905:     */         }
/* 5906:6277 */         if (!bool1)
/* 5907:     */         {
/* 5908:6279 */           localObject4 = local_Request.getOutputStream();
/* 5909:6280 */           local_Request.write_value(paramCutOffInfoList, CutOffInfoList.class);
/* 5910:     */         }
/* 5911:     */         else
/* 5912:     */         {
/* 5913:6284 */           localObject4 = local_Request.getOutputStream();
/* 5914:6285 */           localLocalFrame.add(paramCutOffInfoList);
/* 5915:     */         }
/* 5916:6287 */         local_Request.invoke();
/* 5917:     */         Object localObject1;
/* 5918:6288 */         if (bool1)
/* 5919:     */         {
/* 5920:6290 */           localObject4 = null;
/* 5921:6291 */           localObject5 = localLocalFrame.getResult();
/* 5922:6292 */           if (localObject5 != null) {
/* 5923:6294 */             localObject4 = (CutOffInfoList)ObjectVal.clone(localObject5);
/* 5924:     */           }
/* 5925:6296 */           return localObject4;
/* 5926:     */         }
/* 5927:6298 */         Object localObject4 = local_Request.getInputStream();
/* 5928:     */         
/* 5929:6300 */         localObject5 = (CutOffInfoList)local_Request.read_value(CutOffInfoList.class);
/* 5930:6301 */         return localObject5;
/* 5931:     */       }
/* 5932:     */       catch (TRANSIENT localTRANSIENT)
/* 5933:     */       {
/* 5934:6305 */         if (i == 10) {
/* 5935:6307 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5936:     */         }
/* 5937:     */       }
/* 5938:     */       catch (UserException localUserException)
/* 5939:     */       {
/* 5940:     */         Object localObject5;
/* 5941:6312 */         if (local_Request.isRMI())
/* 5942:     */         {
/* 5943:6314 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 5944:6316 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5945:     */           }
/* 5946:     */         }
/* 5947:     */         else
/* 5948:     */         {
/* 5949:6321 */           localObject5 = null;
/* 5950:6322 */           if (bool1)
/* 5951:     */           {
/* 5952:6324 */             localObject5 = localLocalFrame.getException();
/* 5953:6325 */             if (localObject5 != null) {
/* 5954:6327 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 5955:     */             }
/* 5956:     */           }
/* 5957:6330 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 5958:     */           {
/* 5959:6332 */             if (local_Request.isRMI()) {
/* 5960:6334 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 5961:     */             }
/* 5962:6338 */             if (bool1)
/* 5963:     */             {
/* 5964:6340 */               if (localObject5 != null) {
/* 5965:6340 */                 throw ((FFSException)localObject5);
/* 5966:     */               }
/* 5967:     */             }
/* 5968:     */             else {
/* 5969:6344 */               throw FFSExceptionHelper.read(localUserException.input);
/* 5970:     */             }
/* 5971:     */           }
/* 5972:     */         }
/* 5973:6349 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5974:     */       }
/* 5975:     */       catch (SystemException localSystemException)
/* 5976:     */       {
/* 5977:6353 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5978:     */       }
/* 5979:     */       finally
/* 5980:     */       {
/* 5981:6357 */         if (local_Request != null) {
/* 5982:6359 */           local_Request.close();
/* 5983:     */         }
/* 5984:6361 */         if (bool1) {
/* 5985:6362 */           localLocalStack.pop(localLocalFrame);
/* 5986:     */         }
/* 5987:6363 */         localLocalStack.setArgsOnLocal(bool2);
/* 5988:     */       }
/* 5989:     */     }
/* 5990:     */   }
/* 5991:     */   
/* 5992:     */   public ScheduleCategoryInfo getScheduleCategoryInfo(String paramString1, String paramString2)
/* 5993:     */     throws java.rmi.RemoteException, FFSException
/* 5994:     */   {
/* 5995:6373 */     for (int i = 1;; i++)
/* 5996:     */     {
/* 5997:6375 */       _Request local_Request = null;
/* 5998:6376 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5999:6377 */       boolean bool1 = false;
/* 6000:6378 */       LocalFrame localLocalFrame = null;
/* 6001:6379 */       boolean bool2 = false;
/* 6002:     */       try
/* 6003:     */       {
/* 6004:6382 */         local_Request = __request("getScheduleCategoryInfo");
/* 6005:6383 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6006:6384 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6007:6385 */         localLocalStack.setArgsOnLocal(bool1);
/* 6008:6387 */         if (bool1)
/* 6009:     */         {
/* 6010:6389 */           localLocalFrame = new LocalFrame(2);
/* 6011:6390 */           localLocalStack.push(localLocalFrame);
/* 6012:     */         }
/* 6013:6392 */         if (!bool1)
/* 6014:     */         {
/* 6015:6394 */           localObject4 = local_Request.getOutputStream();
/* 6016:6395 */           local_Request.write_string(paramString1);
/* 6017:6396 */           local_Request.write_string(paramString2);
/* 6018:     */         }
/* 6019:     */         else
/* 6020:     */         {
/* 6021:6400 */           localObject4 = local_Request.getOutputStream();
/* 6022:6401 */           localLocalFrame.add(paramString1);
/* 6023:6402 */           localLocalFrame.add(paramString2);
/* 6024:     */         }
/* 6025:6404 */         local_Request.invoke();
/* 6026:     */         Object localObject1;
/* 6027:6405 */         if (bool1)
/* 6028:     */         {
/* 6029:6407 */           localObject4 = null;
/* 6030:6408 */           localObject5 = localLocalFrame.getResult();
/* 6031:6409 */           if (localObject5 != null) {
/* 6032:6411 */             localObject4 = (ScheduleCategoryInfo)ObjectVal.clone(localObject5);
/* 6033:     */           }
/* 6034:6413 */           return localObject4;
/* 6035:     */         }
/* 6036:6415 */         Object localObject4 = local_Request.getInputStream();
/* 6037:     */         
/* 6038:6417 */         localObject5 = (ScheduleCategoryInfo)local_Request.read_value(ScheduleCategoryInfo.class);
/* 6039:6418 */         return localObject5;
/* 6040:     */       }
/* 6041:     */       catch (TRANSIENT localTRANSIENT)
/* 6042:     */       {
/* 6043:6422 */         if (i == 10) {
/* 6044:6424 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6045:     */         }
/* 6046:     */       }
/* 6047:     */       catch (UserException localUserException)
/* 6048:     */       {
/* 6049:     */         Object localObject5;
/* 6050:6429 */         if (local_Request.isRMI())
/* 6051:     */         {
/* 6052:6431 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6053:6433 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6054:     */           }
/* 6055:     */         }
/* 6056:     */         else
/* 6057:     */         {
/* 6058:6438 */           localObject5 = null;
/* 6059:6439 */           if (bool1)
/* 6060:     */           {
/* 6061:6441 */             localObject5 = localLocalFrame.getException();
/* 6062:6442 */             if (localObject5 != null) {
/* 6063:6444 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6064:     */             }
/* 6065:     */           }
/* 6066:6447 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6067:     */           {
/* 6068:6449 */             if (local_Request.isRMI()) {
/* 6069:6451 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6070:     */             }
/* 6071:6455 */             if (bool1)
/* 6072:     */             {
/* 6073:6457 */               if (localObject5 != null) {
/* 6074:6457 */                 throw ((FFSException)localObject5);
/* 6075:     */               }
/* 6076:     */             }
/* 6077:     */             else {
/* 6078:6461 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6079:     */             }
/* 6080:     */           }
/* 6081:     */         }
/* 6082:6466 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6083:     */       }
/* 6084:     */       catch (SystemException localSystemException)
/* 6085:     */       {
/* 6086:6470 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6087:     */       }
/* 6088:     */       finally
/* 6089:     */       {
/* 6090:6474 */         if (local_Request != null) {
/* 6091:6476 */           local_Request.close();
/* 6092:     */         }
/* 6093:6478 */         if (bool1) {
/* 6094:6479 */           localLocalStack.pop(localLocalFrame);
/* 6095:     */         }
/* 6096:6480 */         localLocalStack.setArgsOnLocal(bool2);
/* 6097:     */       }
/* 6098:     */     }
/* 6099:     */   }
/* 6100:     */   
/* 6101:     */   public ScheduleCategoryInfo modScheduleCategoryInfo(ScheduleCategoryInfo paramScheduleCategoryInfo)
/* 6102:     */     throws java.rmi.RemoteException, FFSException
/* 6103:     */   {
/* 6104:6489 */     for (int i = 1;; i++)
/* 6105:     */     {
/* 6106:6491 */       _Request local_Request = null;
/* 6107:6492 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6108:6493 */       boolean bool1 = false;
/* 6109:6494 */       LocalFrame localLocalFrame = null;
/* 6110:6495 */       boolean bool2 = false;
/* 6111:     */       try
/* 6112:     */       {
/* 6113:6498 */         local_Request = __request("modScheduleCategoryInfo");
/* 6114:6499 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6115:6500 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6116:6501 */         localLocalStack.setArgsOnLocal(bool1);
/* 6117:6503 */         if (bool1)
/* 6118:     */         {
/* 6119:6505 */           localLocalFrame = new LocalFrame(1);
/* 6120:6506 */           localLocalStack.push(localLocalFrame);
/* 6121:     */         }
/* 6122:6508 */         if (!bool1)
/* 6123:     */         {
/* 6124:6510 */           localObject4 = local_Request.getOutputStream();
/* 6125:6511 */           local_Request.write_value(paramScheduleCategoryInfo, ScheduleCategoryInfo.class);
/* 6126:     */         }
/* 6127:     */         else
/* 6128:     */         {
/* 6129:6515 */           localObject4 = local_Request.getOutputStream();
/* 6130:6516 */           localLocalFrame.add(paramScheduleCategoryInfo);
/* 6131:     */         }
/* 6132:6518 */         local_Request.invoke();
/* 6133:     */         Object localObject1;
/* 6134:6519 */         if (bool1)
/* 6135:     */         {
/* 6136:6521 */           localObject4 = null;
/* 6137:6522 */           localObject5 = localLocalFrame.getResult();
/* 6138:6523 */           if (localObject5 != null) {
/* 6139:6525 */             localObject4 = (ScheduleCategoryInfo)ObjectVal.clone(localObject5);
/* 6140:     */           }
/* 6141:6527 */           return localObject4;
/* 6142:     */         }
/* 6143:6529 */         Object localObject4 = local_Request.getInputStream();
/* 6144:     */         
/* 6145:6531 */         localObject5 = (ScheduleCategoryInfo)local_Request.read_value(ScheduleCategoryInfo.class);
/* 6146:6532 */         return localObject5;
/* 6147:     */       }
/* 6148:     */       catch (TRANSIENT localTRANSIENT)
/* 6149:     */       {
/* 6150:6536 */         if (i == 10) {
/* 6151:6538 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6152:     */         }
/* 6153:     */       }
/* 6154:     */       catch (UserException localUserException)
/* 6155:     */       {
/* 6156:     */         Object localObject5;
/* 6157:6543 */         if (local_Request.isRMI())
/* 6158:     */         {
/* 6159:6545 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6160:6547 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6161:     */           }
/* 6162:     */         }
/* 6163:     */         else
/* 6164:     */         {
/* 6165:6552 */           localObject5 = null;
/* 6166:6553 */           if (bool1)
/* 6167:     */           {
/* 6168:6555 */             localObject5 = localLocalFrame.getException();
/* 6169:6556 */             if (localObject5 != null) {
/* 6170:6558 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6171:     */             }
/* 6172:     */           }
/* 6173:6561 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6174:     */           {
/* 6175:6563 */             if (local_Request.isRMI()) {
/* 6176:6565 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6177:     */             }
/* 6178:6569 */             if (bool1)
/* 6179:     */             {
/* 6180:6571 */               if (localObject5 != null) {
/* 6181:6571 */                 throw ((FFSException)localObject5);
/* 6182:     */               }
/* 6183:     */             }
/* 6184:     */             else {
/* 6185:6575 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6186:     */             }
/* 6187:     */           }
/* 6188:     */         }
/* 6189:6580 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6190:     */       }
/* 6191:     */       catch (SystemException localSystemException)
/* 6192:     */       {
/* 6193:6584 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6194:     */       }
/* 6195:     */       finally
/* 6196:     */       {
/* 6197:6588 */         if (local_Request != null) {
/* 6198:6590 */           local_Request.close();
/* 6199:     */         }
/* 6200:6592 */         if (bool1) {
/* 6201:6593 */           localLocalStack.pop(localLocalFrame);
/* 6202:     */         }
/* 6203:6594 */         localLocalStack.setArgsOnLocal(bool2);
/* 6204:     */       }
/* 6205:     */     }
/* 6206:     */   }
/* 6207:     */   
/* 6208:     */   public CutOffActivityInfoList getCutOffActivityList(CutOffActivityInfoList paramCutOffActivityInfoList)
/* 6209:     */     throws java.rmi.RemoteException, FFSException
/* 6210:     */   {
/* 6211:6603 */     for (int i = 1;; i++)
/* 6212:     */     {
/* 6213:6605 */       _Request local_Request = null;
/* 6214:6606 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6215:6607 */       boolean bool1 = false;
/* 6216:6608 */       LocalFrame localLocalFrame = null;
/* 6217:6609 */       boolean bool2 = false;
/* 6218:     */       try
/* 6219:     */       {
/* 6220:6612 */         local_Request = __request("getCutOffActivityList");
/* 6221:6613 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6222:6614 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6223:6615 */         localLocalStack.setArgsOnLocal(bool1);
/* 6224:6617 */         if (bool1)
/* 6225:     */         {
/* 6226:6619 */           localLocalFrame = new LocalFrame(1);
/* 6227:6620 */           localLocalStack.push(localLocalFrame);
/* 6228:     */         }
/* 6229:6622 */         if (!bool1)
/* 6230:     */         {
/* 6231:6624 */           localObject4 = local_Request.getOutputStream();
/* 6232:6625 */           local_Request.write_value(paramCutOffActivityInfoList, CutOffActivityInfoList.class);
/* 6233:     */         }
/* 6234:     */         else
/* 6235:     */         {
/* 6236:6629 */           localObject4 = local_Request.getOutputStream();
/* 6237:6630 */           localLocalFrame.add(paramCutOffActivityInfoList);
/* 6238:     */         }
/* 6239:6632 */         local_Request.invoke();
/* 6240:     */         Object localObject1;
/* 6241:6633 */         if (bool1)
/* 6242:     */         {
/* 6243:6635 */           localObject4 = null;
/* 6244:6636 */           localObject5 = localLocalFrame.getResult();
/* 6245:6637 */           if (localObject5 != null) {
/* 6246:6639 */             localObject4 = (CutOffActivityInfoList)ObjectVal.clone(localObject5);
/* 6247:     */           }
/* 6248:6641 */           return localObject4;
/* 6249:     */         }
/* 6250:6643 */         Object localObject4 = local_Request.getInputStream();
/* 6251:     */         
/* 6252:6645 */         localObject5 = (CutOffActivityInfoList)local_Request.read_value(CutOffActivityInfoList.class);
/* 6253:6646 */         return localObject5;
/* 6254:     */       }
/* 6255:     */       catch (TRANSIENT localTRANSIENT)
/* 6256:     */       {
/* 6257:6650 */         if (i == 10) {
/* 6258:6652 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6259:     */         }
/* 6260:     */       }
/* 6261:     */       catch (UserException localUserException)
/* 6262:     */       {
/* 6263:     */         Object localObject5;
/* 6264:6657 */         if (local_Request.isRMI())
/* 6265:     */         {
/* 6266:6659 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6267:6661 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6268:     */           }
/* 6269:     */         }
/* 6270:     */         else
/* 6271:     */         {
/* 6272:6666 */           localObject5 = null;
/* 6273:6667 */           if (bool1)
/* 6274:     */           {
/* 6275:6669 */             localObject5 = localLocalFrame.getException();
/* 6276:6670 */             if (localObject5 != null) {
/* 6277:6672 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6278:     */             }
/* 6279:     */           }
/* 6280:6675 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6281:     */           {
/* 6282:6677 */             if (local_Request.isRMI()) {
/* 6283:6679 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6284:     */             }
/* 6285:6683 */             if (bool1)
/* 6286:     */             {
/* 6287:6685 */               if (localObject5 != null) {
/* 6288:6685 */                 throw ((FFSException)localObject5);
/* 6289:     */               }
/* 6290:     */             }
/* 6291:     */             else {
/* 6292:6689 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6293:     */             }
/* 6294:     */           }
/* 6295:     */         }
/* 6296:6694 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6297:     */       }
/* 6298:     */       catch (SystemException localSystemException)
/* 6299:     */       {
/* 6300:6698 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6301:     */       }
/* 6302:     */       finally
/* 6303:     */       {
/* 6304:6702 */         if (local_Request != null) {
/* 6305:6704 */           local_Request.close();
/* 6306:     */         }
/* 6307:6706 */         if (bool1) {
/* 6308:6707 */           localLocalStack.pop(localLocalFrame);
/* 6309:     */         }
/* 6310:6708 */         localLocalStack.setArgsOnLocal(bool2);
/* 6311:     */       }
/* 6312:     */     }
/* 6313:     */   }
/* 6314:     */   
/* 6315:     */   public ScheduleActivityList getScheduleActivityList(ScheduleActivityList paramScheduleActivityList)
/* 6316:     */     throws java.rmi.RemoteException, FFSException
/* 6317:     */   {
/* 6318:6717 */     for (int i = 1;; i++)
/* 6319:     */     {
/* 6320:6719 */       _Request local_Request = null;
/* 6321:6720 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6322:6721 */       boolean bool1 = false;
/* 6323:6722 */       LocalFrame localLocalFrame = null;
/* 6324:6723 */       boolean bool2 = false;
/* 6325:     */       try
/* 6326:     */       {
/* 6327:6726 */         local_Request = __request("getScheduleActivityList");
/* 6328:6727 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6329:6728 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6330:6729 */         localLocalStack.setArgsOnLocal(bool1);
/* 6331:6731 */         if (bool1)
/* 6332:     */         {
/* 6333:6733 */           localLocalFrame = new LocalFrame(1);
/* 6334:6734 */           localLocalStack.push(localLocalFrame);
/* 6335:     */         }
/* 6336:6736 */         if (!bool1)
/* 6337:     */         {
/* 6338:6738 */           localObject4 = local_Request.getOutputStream();
/* 6339:6739 */           local_Request.write_value(paramScheduleActivityList, ScheduleActivityList.class);
/* 6340:     */         }
/* 6341:     */         else
/* 6342:     */         {
/* 6343:6743 */           localObject4 = local_Request.getOutputStream();
/* 6344:6744 */           localLocalFrame.add(paramScheduleActivityList);
/* 6345:     */         }
/* 6346:6746 */         local_Request.invoke();
/* 6347:     */         Object localObject1;
/* 6348:6747 */         if (bool1)
/* 6349:     */         {
/* 6350:6749 */           localObject4 = null;
/* 6351:6750 */           localObject5 = localLocalFrame.getResult();
/* 6352:6751 */           if (localObject5 != null) {
/* 6353:6753 */             localObject4 = (ScheduleActivityList)ObjectVal.clone(localObject5);
/* 6354:     */           }
/* 6355:6755 */           return localObject4;
/* 6356:     */         }
/* 6357:6757 */         Object localObject4 = local_Request.getInputStream();
/* 6358:     */         
/* 6359:6759 */         localObject5 = (ScheduleActivityList)local_Request.read_value(ScheduleActivityList.class);
/* 6360:6760 */         return localObject5;
/* 6361:     */       }
/* 6362:     */       catch (TRANSIENT localTRANSIENT)
/* 6363:     */       {
/* 6364:6764 */         if (i == 10) {
/* 6365:6766 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6366:     */         }
/* 6367:     */       }
/* 6368:     */       catch (UserException localUserException)
/* 6369:     */       {
/* 6370:     */         Object localObject5;
/* 6371:6771 */         if (local_Request.isRMI())
/* 6372:     */         {
/* 6373:6773 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6374:6775 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6375:     */           }
/* 6376:     */         }
/* 6377:     */         else
/* 6378:     */         {
/* 6379:6780 */           localObject5 = null;
/* 6380:6781 */           if (bool1)
/* 6381:     */           {
/* 6382:6783 */             localObject5 = localLocalFrame.getException();
/* 6383:6784 */             if (localObject5 != null) {
/* 6384:6786 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6385:     */             }
/* 6386:     */           }
/* 6387:6789 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6388:     */           {
/* 6389:6791 */             if (local_Request.isRMI()) {
/* 6390:6793 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6391:     */             }
/* 6392:6797 */             if (bool1)
/* 6393:     */             {
/* 6394:6799 */               if (localObject5 != null) {
/* 6395:6799 */                 throw ((FFSException)localObject5);
/* 6396:     */               }
/* 6397:     */             }
/* 6398:     */             else {
/* 6399:6803 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6400:     */             }
/* 6401:     */           }
/* 6402:     */         }
/* 6403:6808 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6404:     */       }
/* 6405:     */       catch (SystemException localSystemException)
/* 6406:     */       {
/* 6407:6812 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6408:     */       }
/* 6409:     */       finally
/* 6410:     */       {
/* 6411:6816 */         if (local_Request != null) {
/* 6412:6818 */           local_Request.close();
/* 6413:     */         }
/* 6414:6820 */         if (bool1) {
/* 6415:6821 */           localLocalStack.pop(localLocalFrame);
/* 6416:     */         }
/* 6417:6822 */         localLocalStack.setArgsOnLocal(bool2);
/* 6418:     */       }
/* 6419:     */     }
/* 6420:     */   }
/* 6421:     */   
/* 6422:     */   public void rerunCutOff(String paramString1, String paramString2, String paramString3, String paramString4)
/* 6423:     */     throws java.rmi.RemoteException, FFSException
/* 6424:     */   {
/* 6425:6834 */     for (int i = 1;; i++)
/* 6426:     */     {
/* 6427:6836 */       _Request local_Request = null;
/* 6428:6837 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6429:6838 */       boolean bool1 = false;
/* 6430:6839 */       LocalFrame localLocalFrame = null;
/* 6431:6840 */       boolean bool2 = false;
/* 6432:     */       try
/* 6433:     */       {
/* 6434:6843 */         local_Request = __request("rerunCutOff");
/* 6435:6844 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6436:6845 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6437:6846 */         localLocalStack.setArgsOnLocal(bool1);
/* 6438:6848 */         if (bool1)
/* 6439:     */         {
/* 6440:6850 */           localLocalFrame = new LocalFrame(4);
/* 6441:6851 */           localLocalStack.push(localLocalFrame);
/* 6442:     */         }
/* 6443:     */         OutputStream localOutputStream;
/* 6444:6853 */         if (!bool1)
/* 6445:     */         {
/* 6446:6855 */           localOutputStream = local_Request.getOutputStream();
/* 6447:6856 */           local_Request.write_string(paramString1);
/* 6448:6857 */           local_Request.write_string(paramString2);
/* 6449:6858 */           local_Request.write_string(paramString3);
/* 6450:6859 */           local_Request.write_string(paramString4);
/* 6451:     */         }
/* 6452:     */         else
/* 6453:     */         {
/* 6454:6863 */           localOutputStream = local_Request.getOutputStream();
/* 6455:6864 */           localLocalFrame.add(paramString1);
/* 6456:6865 */           localLocalFrame.add(paramString2);
/* 6457:6866 */           localLocalFrame.add(paramString3);
/* 6458:6867 */           localLocalFrame.add(paramString4);
/* 6459:     */         }
/* 6460:6869 */         local_Request.invoke();
/* 6461:6870 */         return;
/* 6462:     */       }
/* 6463:     */       catch (TRANSIENT localTRANSIENT)
/* 6464:     */       {
/* 6465:6874 */         if (i == 10) {
/* 6466:6876 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6467:     */         }
/* 6468:     */       }
/* 6469:     */       catch (UserException localUserException)
/* 6470:     */       {
/* 6471:6881 */         if (local_Request.isRMI())
/* 6472:     */         {
/* 6473:6883 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6474:6885 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6475:     */           }
/* 6476:     */         }
/* 6477:     */         else
/* 6478:     */         {
/* 6479:6890 */           Throwable localThrowable = null;
/* 6480:6891 */           if (bool1)
/* 6481:     */           {
/* 6482:6893 */             localThrowable = localLocalFrame.getException();
/* 6483:6894 */             if (localThrowable != null) {
/* 6484:6896 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/* 6485:     */             }
/* 6486:     */           }
/* 6487:6899 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6488:     */           {
/* 6489:6901 */             if (local_Request.isRMI()) {
/* 6490:6903 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6491:     */             }
/* 6492:6907 */             if (bool1)
/* 6493:     */             {
/* 6494:6909 */               if (localThrowable != null) {
/* 6495:6909 */                 throw ((FFSException)localThrowable);
/* 6496:     */               }
/* 6497:     */             }
/* 6498:     */             else {
/* 6499:6913 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6500:     */             }
/* 6501:     */           }
/* 6502:     */         }
/* 6503:6918 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6504:     */       }
/* 6505:     */       catch (SystemException localSystemException)
/* 6506:     */       {
/* 6507:6922 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6508:     */       }
/* 6509:     */       finally
/* 6510:     */       {
/* 6511:6926 */         if (local_Request != null) {
/* 6512:6928 */           local_Request.close();
/* 6513:     */         }
/* 6514:6930 */         if (bool1) {
/* 6515:6931 */           localLocalStack.pop(localLocalFrame);
/* 6516:     */         }
/* 6517:6932 */         localLocalStack.setArgsOnLocal(bool2);
/* 6518:     */       }
/* 6519:     */     }
/* 6520:     */   }
/* 6521:     */   
/* 6522:     */   public String getGeneratedFileName(String paramString1, String paramString2, String paramString3)
/* 6523:     */     throws java.rmi.RemoteException, FFSException
/* 6524:     */   {
/* 6525:6943 */     for (int i = 1;; i++)
/* 6526:     */     {
/* 6527:6945 */       _Request local_Request = null;
/* 6528:6946 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6529:6947 */       boolean bool1 = false;
/* 6530:6948 */       LocalFrame localLocalFrame = null;
/* 6531:6949 */       boolean bool2 = false;
/* 6532:     */       try
/* 6533:     */       {
/* 6534:6952 */         local_Request = __request("getGeneratedFileName");
/* 6535:6953 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6536:6954 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6537:6955 */         localLocalStack.setArgsOnLocal(bool1);
/* 6538:6957 */         if (bool1)
/* 6539:     */         {
/* 6540:6959 */           localLocalFrame = new LocalFrame(3);
/* 6541:6960 */           localLocalStack.push(localLocalFrame);
/* 6542:     */         }
/* 6543:6962 */         if (!bool1)
/* 6544:     */         {
/* 6545:6964 */           localObject4 = local_Request.getOutputStream();
/* 6546:6965 */           local_Request.write_string(paramString1);
/* 6547:6966 */           local_Request.write_string(paramString2);
/* 6548:6967 */           local_Request.write_string(paramString3);
/* 6549:     */         }
/* 6550:     */         else
/* 6551:     */         {
/* 6552:6971 */           localObject4 = local_Request.getOutputStream();
/* 6553:6972 */           localLocalFrame.add(paramString1);
/* 6554:6973 */           localLocalFrame.add(paramString2);
/* 6555:6974 */           localLocalFrame.add(paramString3);
/* 6556:     */         }
/* 6557:6976 */         local_Request.invoke();
/* 6558:     */         Object localObject1;
/* 6559:6977 */         if (bool1)
/* 6560:     */         {
/* 6561:6979 */           localObject4 = null;
/* 6562:6980 */           localObject4 = (String)localLocalFrame.getResult();
/* 6563:6981 */           return localObject4;
/* 6564:     */         }
/* 6565:6983 */         Object localObject4 = local_Request.getInputStream();
/* 6566:     */         
/* 6567:6985 */         localObject5 = local_Request.read_string();
/* 6568:6986 */         return localObject5;
/* 6569:     */       }
/* 6570:     */       catch (TRANSIENT localTRANSIENT)
/* 6571:     */       {
/* 6572:6990 */         if (i == 10) {
/* 6573:6992 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6574:     */         }
/* 6575:     */       }
/* 6576:     */       catch (UserException localUserException)
/* 6577:     */       {
/* 6578:     */         Object localObject5;
/* 6579:6997 */         if (local_Request.isRMI())
/* 6580:     */         {
/* 6581:6999 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6582:7001 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6583:     */           }
/* 6584:     */         }
/* 6585:     */         else
/* 6586:     */         {
/* 6587:7006 */           localObject5 = null;
/* 6588:7007 */           if (bool1)
/* 6589:     */           {
/* 6590:7009 */             localObject5 = localLocalFrame.getException();
/* 6591:7010 */             if (localObject5 != null) {
/* 6592:7012 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6593:     */             }
/* 6594:     */           }
/* 6595:7015 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6596:     */           {
/* 6597:7017 */             if (local_Request.isRMI()) {
/* 6598:7019 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6599:     */             }
/* 6600:7023 */             if (bool1)
/* 6601:     */             {
/* 6602:7025 */               if (localObject5 != null) {
/* 6603:7025 */                 throw ((FFSException)localObject5);
/* 6604:     */               }
/* 6605:     */             }
/* 6606:     */             else {
/* 6607:7029 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6608:     */             }
/* 6609:     */           }
/* 6610:     */         }
/* 6611:7034 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6612:     */       }
/* 6613:     */       catch (SystemException localSystemException)
/* 6614:     */       {
/* 6615:7038 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6616:     */       }
/* 6617:     */       finally
/* 6618:     */       {
/* 6619:7042 */         if (local_Request != null) {
/* 6620:7044 */           local_Request.close();
/* 6621:     */         }
/* 6622:7046 */         if (bool1) {
/* 6623:7047 */           localLocalStack.pop(localLocalFrame);
/* 6624:     */         }
/* 6625:7048 */         localLocalStack.setArgsOnLocal(bool2);
/* 6626:     */       }
/* 6627:     */     }
/* 6628:     */   }
/* 6629:     */   
/* 6630:     */   public SmartCalendarFile importCalendar(SmartCalendarFile paramSmartCalendarFile)
/* 6631:     */     throws java.rmi.RemoteException, FFSException
/* 6632:     */   {
/* 6633:7057 */     for (int i = 1;; i++)
/* 6634:     */     {
/* 6635:7059 */       _Request local_Request = null;
/* 6636:7060 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6637:7061 */       boolean bool1 = false;
/* 6638:7062 */       LocalFrame localLocalFrame = null;
/* 6639:7063 */       boolean bool2 = false;
/* 6640:     */       try
/* 6641:     */       {
/* 6642:7066 */         local_Request = __request("importCalendar");
/* 6643:7067 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6644:7068 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6645:7069 */         localLocalStack.setArgsOnLocal(bool1);
/* 6646:7071 */         if (bool1)
/* 6647:     */         {
/* 6648:7073 */           localLocalFrame = new LocalFrame(1);
/* 6649:7074 */           localLocalStack.push(localLocalFrame);
/* 6650:     */         }
/* 6651:7076 */         if (!bool1)
/* 6652:     */         {
/* 6653:7078 */           localObject4 = local_Request.getOutputStream();
/* 6654:7079 */           local_Request.write_value(paramSmartCalendarFile, SmartCalendarFile.class);
/* 6655:     */         }
/* 6656:     */         else
/* 6657:     */         {
/* 6658:7083 */           localObject4 = local_Request.getOutputStream();
/* 6659:7084 */           localLocalFrame.add(paramSmartCalendarFile);
/* 6660:     */         }
/* 6661:7086 */         local_Request.invoke();
/* 6662:     */         Object localObject1;
/* 6663:7087 */         if (bool1)
/* 6664:     */         {
/* 6665:7089 */           localObject4 = null;
/* 6666:7090 */           localObject5 = localLocalFrame.getResult();
/* 6667:7091 */           if (localObject5 != null) {
/* 6668:7093 */             localObject4 = (SmartCalendarFile)ObjectVal.clone(localObject5);
/* 6669:     */           }
/* 6670:7095 */           return localObject4;
/* 6671:     */         }
/* 6672:7097 */         Object localObject4 = local_Request.getInputStream();
/* 6673:     */         
/* 6674:7099 */         localObject5 = (SmartCalendarFile)local_Request.read_value(SmartCalendarFile.class);
/* 6675:7100 */         return localObject5;
/* 6676:     */       }
/* 6677:     */       catch (TRANSIENT localTRANSIENT)
/* 6678:     */       {
/* 6679:7104 */         if (i == 10) {
/* 6680:7106 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6681:     */         }
/* 6682:     */       }
/* 6683:     */       catch (UserException localUserException)
/* 6684:     */       {
/* 6685:     */         Object localObject5;
/* 6686:7111 */         if (local_Request.isRMI())
/* 6687:     */         {
/* 6688:7113 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6689:7115 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6690:     */           }
/* 6691:     */         }
/* 6692:     */         else
/* 6693:     */         {
/* 6694:7120 */           localObject5 = null;
/* 6695:7121 */           if (bool1)
/* 6696:     */           {
/* 6697:7123 */             localObject5 = localLocalFrame.getException();
/* 6698:7124 */             if (localObject5 != null) {
/* 6699:7126 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6700:     */             }
/* 6701:     */           }
/* 6702:7129 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6703:     */           {
/* 6704:7131 */             if (local_Request.isRMI()) {
/* 6705:7133 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6706:     */             }
/* 6707:7137 */             if (bool1)
/* 6708:     */             {
/* 6709:7139 */               if (localObject5 != null) {
/* 6710:7139 */                 throw ((FFSException)localObject5);
/* 6711:     */               }
/* 6712:     */             }
/* 6713:     */             else {
/* 6714:7143 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6715:     */             }
/* 6716:     */           }
/* 6717:     */         }
/* 6718:7148 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6719:     */       }
/* 6720:     */       catch (SystemException localSystemException)
/* 6721:     */       {
/* 6722:7152 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6723:     */       }
/* 6724:     */       finally
/* 6725:     */       {
/* 6726:7156 */         if (local_Request != null) {
/* 6727:7158 */           local_Request.close();
/* 6728:     */         }
/* 6729:7160 */         if (bool1) {
/* 6730:7161 */           localLocalStack.pop(localLocalFrame);
/* 6731:     */         }
/* 6732:7162 */         localLocalStack.setArgsOnLocal(bool2);
/* 6733:     */       }
/* 6734:     */     }
/* 6735:     */   }
/* 6736:     */   
/* 6737:     */   public SmartCalendarFile exportCalendar(SmartCalendarFile paramSmartCalendarFile)
/* 6738:     */     throws java.rmi.RemoteException, FFSException
/* 6739:     */   {
/* 6740:7171 */     for (int i = 1;; i++)
/* 6741:     */     {
/* 6742:7173 */       _Request local_Request = null;
/* 6743:7174 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6744:7175 */       boolean bool1 = false;
/* 6745:7176 */       LocalFrame localLocalFrame = null;
/* 6746:7177 */       boolean bool2 = false;
/* 6747:     */       try
/* 6748:     */       {
/* 6749:7180 */         local_Request = __request("exportCalendar");
/* 6750:7181 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6751:7182 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6752:7183 */         localLocalStack.setArgsOnLocal(bool1);
/* 6753:7185 */         if (bool1)
/* 6754:     */         {
/* 6755:7187 */           localLocalFrame = new LocalFrame(1);
/* 6756:7188 */           localLocalStack.push(localLocalFrame);
/* 6757:     */         }
/* 6758:7190 */         if (!bool1)
/* 6759:     */         {
/* 6760:7192 */           localObject4 = local_Request.getOutputStream();
/* 6761:7193 */           local_Request.write_value(paramSmartCalendarFile, SmartCalendarFile.class);
/* 6762:     */         }
/* 6763:     */         else
/* 6764:     */         {
/* 6765:7197 */           localObject4 = local_Request.getOutputStream();
/* 6766:7198 */           localLocalFrame.add(paramSmartCalendarFile);
/* 6767:     */         }
/* 6768:7200 */         local_Request.invoke();
/* 6769:     */         Object localObject1;
/* 6770:7201 */         if (bool1)
/* 6771:     */         {
/* 6772:7203 */           localObject4 = null;
/* 6773:7204 */           localObject5 = localLocalFrame.getResult();
/* 6774:7205 */           if (localObject5 != null) {
/* 6775:7207 */             localObject4 = (SmartCalendarFile)ObjectVal.clone(localObject5);
/* 6776:     */           }
/* 6777:7209 */           return localObject4;
/* 6778:     */         }
/* 6779:7211 */         Object localObject4 = local_Request.getInputStream();
/* 6780:     */         
/* 6781:7213 */         localObject5 = (SmartCalendarFile)local_Request.read_value(SmartCalendarFile.class);
/* 6782:7214 */         return localObject5;
/* 6783:     */       }
/* 6784:     */       catch (TRANSIENT localTRANSIENT)
/* 6785:     */       {
/* 6786:7218 */         if (i == 10) {
/* 6787:7220 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6788:     */         }
/* 6789:     */       }
/* 6790:     */       catch (UserException localUserException)
/* 6791:     */       {
/* 6792:     */         Object localObject5;
/* 6793:7225 */         if (local_Request.isRMI())
/* 6794:     */         {
/* 6795:7227 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6796:7229 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6797:     */           }
/* 6798:     */         }
/* 6799:     */         else
/* 6800:     */         {
/* 6801:7234 */           localObject5 = null;
/* 6802:7235 */           if (bool1)
/* 6803:     */           {
/* 6804:7237 */             localObject5 = localLocalFrame.getException();
/* 6805:7238 */             if (localObject5 != null) {
/* 6806:7240 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6807:     */             }
/* 6808:     */           }
/* 6809:7243 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6810:     */           {
/* 6811:7245 */             if (local_Request.isRMI()) {
/* 6812:7247 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6813:     */             }
/* 6814:7251 */             if (bool1)
/* 6815:     */             {
/* 6816:7253 */               if (localObject5 != null) {
/* 6817:7253 */                 throw ((FFSException)localObject5);
/* 6818:     */               }
/* 6819:     */             }
/* 6820:     */             else {
/* 6821:7257 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6822:     */             }
/* 6823:     */           }
/* 6824:     */         }
/* 6825:7262 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6826:     */       }
/* 6827:     */       catch (SystemException localSystemException)
/* 6828:     */       {
/* 6829:7266 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6830:     */       }
/* 6831:     */       finally
/* 6832:     */       {
/* 6833:7270 */         if (local_Request != null) {
/* 6834:7272 */           local_Request.close();
/* 6835:     */         }
/* 6836:7274 */         if (bool1) {
/* 6837:7275 */           localLocalStack.pop(localLocalFrame);
/* 6838:     */         }
/* 6839:7276 */         localLocalStack.setArgsOnLocal(bool2);
/* 6840:     */       }
/* 6841:     */     }
/* 6842:     */   }
/* 6843:     */   
/* 6844:     */   public PayeeInfo addGlobalPayee(PayeeInfo paramPayeeInfo)
/* 6845:     */     throws java.rmi.RemoteException, FFSException
/* 6846:     */   {
/* 6847:7285 */     for (int i = 1;; i++)
/* 6848:     */     {
/* 6849:7287 */       _Request local_Request = null;
/* 6850:7288 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6851:7289 */       boolean bool1 = false;
/* 6852:7290 */       LocalFrame localLocalFrame = null;
/* 6853:7291 */       boolean bool2 = false;
/* 6854:     */       try
/* 6855:     */       {
/* 6856:7294 */         local_Request = __request("addGlobalPayee");
/* 6857:7295 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6858:7296 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6859:7297 */         localLocalStack.setArgsOnLocal(bool1);
/* 6860:7299 */         if (bool1)
/* 6861:     */         {
/* 6862:7301 */           localLocalFrame = new LocalFrame(1);
/* 6863:7302 */           localLocalStack.push(localLocalFrame);
/* 6864:     */         }
/* 6865:7304 */         if (!bool1)
/* 6866:     */         {
/* 6867:7306 */           localObject4 = local_Request.getOutputStream();
/* 6868:7307 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 6869:     */         }
/* 6870:     */         else
/* 6871:     */         {
/* 6872:7311 */           localObject4 = local_Request.getOutputStream();
/* 6873:7312 */           localLocalFrame.add(paramPayeeInfo);
/* 6874:     */         }
/* 6875:7314 */         local_Request.invoke();
/* 6876:     */         Object localObject1;
/* 6877:7315 */         if (bool1)
/* 6878:     */         {
/* 6879:7317 */           localObject4 = null;
/* 6880:7318 */           localObject5 = localLocalFrame.getResult();
/* 6881:7319 */           if (localObject5 != null) {
/* 6882:7321 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 6883:     */           }
/* 6884:7323 */           return localObject4;
/* 6885:     */         }
/* 6886:7325 */         Object localObject4 = local_Request.getInputStream();
/* 6887:     */         
/* 6888:7327 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 6889:7328 */         return localObject5;
/* 6890:     */       }
/* 6891:     */       catch (TRANSIENT localTRANSIENT)
/* 6892:     */       {
/* 6893:7332 */         if (i == 10) {
/* 6894:7334 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6895:     */         }
/* 6896:     */       }
/* 6897:     */       catch (UserException localUserException)
/* 6898:     */       {
/* 6899:     */         Object localObject5;
/* 6900:7339 */         if (local_Request.isRMI())
/* 6901:     */         {
/* 6902:7341 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6903:7343 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6904:     */           }
/* 6905:     */         }
/* 6906:     */         else
/* 6907:     */         {
/* 6908:7348 */           localObject5 = null;
/* 6909:7349 */           if (bool1)
/* 6910:     */           {
/* 6911:7351 */             localObject5 = localLocalFrame.getException();
/* 6912:7352 */             if (localObject5 != null) {
/* 6913:7354 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6914:     */             }
/* 6915:     */           }
/* 6916:7357 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6917:     */           {
/* 6918:7359 */             if (local_Request.isRMI()) {
/* 6919:7361 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6920:     */             }
/* 6921:7365 */             if (bool1)
/* 6922:     */             {
/* 6923:7367 */               if (localObject5 != null) {
/* 6924:7367 */                 throw ((FFSException)localObject5);
/* 6925:     */               }
/* 6926:     */             }
/* 6927:     */             else {
/* 6928:7371 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6929:     */             }
/* 6930:     */           }
/* 6931:     */         }
/* 6932:7376 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6933:     */       }
/* 6934:     */       catch (SystemException localSystemException)
/* 6935:     */       {
/* 6936:7380 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6937:     */       }
/* 6938:     */       finally
/* 6939:     */       {
/* 6940:7384 */         if (local_Request != null) {
/* 6941:7386 */           local_Request.close();
/* 6942:     */         }
/* 6943:7388 */         if (bool1) {
/* 6944:7389 */           localLocalStack.pop(localLocalFrame);
/* 6945:     */         }
/* 6946:7390 */         localLocalStack.setArgsOnLocal(bool2);
/* 6947:     */       }
/* 6948:     */     }
/* 6949:     */   }
/* 6950:     */   
/* 6951:     */   public PayeeInfo deleteGlobalPayee(PayeeInfo paramPayeeInfo)
/* 6952:     */     throws java.rmi.RemoteException, FFSException
/* 6953:     */   {
/* 6954:7399 */     for (int i = 1;; i++)
/* 6955:     */     {
/* 6956:7401 */       _Request local_Request = null;
/* 6957:7402 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6958:7403 */       boolean bool1 = false;
/* 6959:7404 */       LocalFrame localLocalFrame = null;
/* 6960:7405 */       boolean bool2 = false;
/* 6961:     */       try
/* 6962:     */       {
/* 6963:7408 */         local_Request = __request("deleteGlobalPayee");
/* 6964:7409 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6965:7410 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6966:7411 */         localLocalStack.setArgsOnLocal(bool1);
/* 6967:7413 */         if (bool1)
/* 6968:     */         {
/* 6969:7415 */           localLocalFrame = new LocalFrame(1);
/* 6970:7416 */           localLocalStack.push(localLocalFrame);
/* 6971:     */         }
/* 6972:7418 */         if (!bool1)
/* 6973:     */         {
/* 6974:7420 */           localObject4 = local_Request.getOutputStream();
/* 6975:7421 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 6976:     */         }
/* 6977:     */         else
/* 6978:     */         {
/* 6979:7425 */           localObject4 = local_Request.getOutputStream();
/* 6980:7426 */           localLocalFrame.add(paramPayeeInfo);
/* 6981:     */         }
/* 6982:7428 */         local_Request.invoke();
/* 6983:     */         Object localObject1;
/* 6984:7429 */         if (bool1)
/* 6985:     */         {
/* 6986:7431 */           localObject4 = null;
/* 6987:7432 */           localObject5 = localLocalFrame.getResult();
/* 6988:7433 */           if (localObject5 != null) {
/* 6989:7435 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 6990:     */           }
/* 6991:7437 */           return localObject4;
/* 6992:     */         }
/* 6993:7439 */         Object localObject4 = local_Request.getInputStream();
/* 6994:     */         
/* 6995:7441 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 6996:7442 */         return localObject5;
/* 6997:     */       }
/* 6998:     */       catch (TRANSIENT localTRANSIENT)
/* 6999:     */       {
/* 7000:7446 */         if (i == 10) {
/* 7001:7448 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 7002:     */         }
/* 7003:     */       }
/* 7004:     */       catch (UserException localUserException)
/* 7005:     */       {
/* 7006:     */         Object localObject5;
/* 7007:7453 */         if (local_Request.isRMI())
/* 7008:     */         {
/* 7009:7455 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 7010:7457 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 7011:     */           }
/* 7012:     */         }
/* 7013:     */         else
/* 7014:     */         {
/* 7015:7462 */           localObject5 = null;
/* 7016:7463 */           if (bool1)
/* 7017:     */           {
/* 7018:7465 */             localObject5 = localLocalFrame.getException();
/* 7019:7466 */             if (localObject5 != null) {
/* 7020:7468 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 7021:     */             }
/* 7022:     */           }
/* 7023:7471 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 7024:     */           {
/* 7025:7473 */             if (local_Request.isRMI()) {
/* 7026:7475 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 7027:     */             }
/* 7028:7479 */             if (bool1)
/* 7029:     */             {
/* 7030:7481 */               if (localObject5 != null) {
/* 7031:7481 */                 throw ((FFSException)localObject5);
/* 7032:     */               }
/* 7033:     */             }
/* 7034:     */             else {
/* 7035:7485 */               throw FFSExceptionHelper.read(localUserException.input);
/* 7036:     */             }
/* 7037:     */           }
/* 7038:     */         }
/* 7039:7490 */         throw new java.rmi.RemoteException(localUserException.type);
/* 7040:     */       }
/* 7041:     */       catch (SystemException localSystemException)
/* 7042:     */       {
/* 7043:7494 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 7044:     */       }
/* 7045:     */       finally
/* 7046:     */       {
/* 7047:7498 */         if (local_Request != null) {
/* 7048:7500 */           local_Request.close();
/* 7049:     */         }
/* 7050:7502 */         if (bool1) {
/* 7051:7503 */           localLocalStack.pop(localLocalFrame);
/* 7052:     */         }
/* 7053:7504 */         localLocalStack.setArgsOnLocal(bool2);
/* 7054:     */       }
/* 7055:     */     }
/* 7056:     */   }
/* 7057:     */   
/* 7058:     */   public IBPWAdmin_Stub() {}
/* 7059:     */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.IBPWAdmin_Stub
 * JD-Core Version:    0.7.0.1
 */