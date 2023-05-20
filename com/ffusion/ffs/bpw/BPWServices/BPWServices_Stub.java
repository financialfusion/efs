/*     1:      */ package com.ffusion.ffs.bpw.BPWServices;
/*     2:      */ 
/*     3:      */ import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
/*     4:      */ import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
/*     5:      */ import com.ffusion.ffs.bpw.interfaces.BPWBankInfoSeqHelper;
/*     6:      */ import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
/*     7:      */ import com.ffusion.ffs.bpw.interfaces.BPWFIInfoSeqHelper;
/*     8:      */ import com.ffusion.ffs.bpw.interfaces.BPWHist;
/*     9:      */ import com.ffusion.ffs.bpw.interfaces.BPWHistHelper;
/*    10:      */ import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
/*    11:      */ import com.ffusion.ffs.bpw.interfaces.BankingDays;
/*    12:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
/*    13:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList;
/*    14:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
/*    15:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
/*    16:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
/*    17:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList;
/*    18:      */ import com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo;
/*    19:      */ import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
/*    20:      */ import com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList;
/*    21:      */ import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
/*    22:      */ import com.ffusion.ffs.bpw.interfaces.CCLocationInfoList;
/*    23:      */ import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
/*    24:      */ import com.ffusion.ffs.bpw.interfaces.CustomerInfoSeqHelper;
/*    25:      */ import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
/*    26:      */ import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfoSeqHelper;
/*    27:      */ import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
/*    28:      */ import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
/*    29:      */ import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
/*    30:      */ import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
/*    31:      */ import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
/*    32:      */ import com.ffusion.ffs.bpw.interfaces.LastPaymentInfo;
/*    33:      */ import com.ffusion.ffs.bpw.interfaces.NonBusinessDay;
/*    34:      */ import com.ffusion.ffs.bpw.interfaces.NonBusinessDaySeqHelper;
/*    35:      */ import com.ffusion.ffs.bpw.interfaces.PagingInfo;
/*    36:      */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*    37:      */ import com.ffusion.ffs.bpw.interfaces.PayeeInfoSeqHelper;
/*    38:      */ import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
/*    39:      */ import com.ffusion.ffs.bpw.interfaces.PmtInfo;
/*    40:      */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
/*    41:      */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRsltSeqHelper;
/*    42:      */ import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo;
/*    43:      */ import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfoSeqHelper;
/*    44:      */ import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
/*    45:      */ import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
/*    46:      */ import com.ffusion.ffs.bpw.interfaces.RecTransferInfoSeqHelper;
/*    47:      */ import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
/*    48:      */ import com.ffusion.ffs.bpw.interfaces.RecWireInfoSeqHelper;
/*    49:      */ import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
/*    50:      */ import com.ffusion.ffs.bpw.interfaces.TransferInfo;
/*    51:      */ import com.ffusion.ffs.bpw.interfaces.TransferInfoSeqHelper;
/*    52:      */ import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
/*    53:      */ import com.ffusion.ffs.bpw.interfaces.WireBatchInfoSeqHelper;
/*    54:      */ import com.ffusion.ffs.bpw.interfaces.WireInfo;
/*    55:      */ import com.ffusion.ffs.bpw.interfaces.WireInfoSeqHelper;
/*    56:      */ import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
/*    57:      */ import com.ffusion.ffs.bpw.interfaces.WirePayeeInfoSeqHelper;
/*    58:      */ import com.ffusion.ffs.bpw.interfaces.WireReleaseInfo;
/*    59:      */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*    60:      */ import com.ffusion.ffs.interfaces.FFSException;
/*    61:      */ import com.ffusion.ffs.interfaces.FFSExceptionHelper;
/*    62:      */ import com.sybase.CORBA.LocalFrame;
/*    63:      */ import com.sybase.CORBA.LocalStack;
/*    64:      */ import com.sybase.CORBA.ObjectRef;
/*    65:      */ import com.sybase.CORBA.ObjectVal;
/*    66:      */ import com.sybase.CORBA.UserException;
/*    67:      */ import com.sybase.CORBA._Request;
/*    68:      */ import com.sybase.ejb.EJBObject;
/*    69:      */ import com.sybase.ejb.cts.StringSeqHelper;
/*    70:      */ import java.util.ArrayList;
/*    71:      */ import java.util.HashMap;
/*    72:      */ import org.omg.CORBA.SystemException;
/*    73:      */ import org.omg.CORBA.TRANSIENT;
/*    74:      */ import org.omg.CORBA.portable.IDLEntity;
/*    75:      */ import org.omg.CORBA.portable.InputStream;
/*    76:      */ import org.omg.CORBA.portable.OutputStream;
/*    77:      */ 
/*    78:      */ public class BPWServices_Stub
/*    79:      */   extends EJBObject
/*    80:      */   implements BPWServices, IDLEntity
/*    81:      */ {
/*    82:      */   public BPWServices_Stub(ObjectRef paramObjectRef)
/*    83:      */   {
/*    84:   21 */     super("RMI:com.ffusion.ffs.bpw.BPWServices.BPWServices:0000000000000000", paramObjectRef);
/*    85:      */   }
/*    86:      */   
/*    87:      */   public void disconnect()
/*    88:      */     throws java.rmi.RemoteException
/*    89:      */   {
/*    90:   27 */     for (int i = 1;; i++)
/*    91:      */     {
/*    92:   29 */       _Request local_Request = null;
/*    93:   30 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*    94:   31 */       boolean bool = false;
/*    95:      */       try
/*    96:      */       {
/*    97:   34 */         local_Request = __request("disconnect");
/*    98:   35 */         bool = localLocalStack.isArgsOnLocal();
/*    99:   36 */         localLocalStack.setArgsOnLocal(false);
/*   100:   37 */         local_Request.invoke();
/*   101:   38 */         return;
/*   102:      */       }
/*   103:      */       catch (TRANSIENT localTRANSIENT)
/*   104:      */       {
/*   105:   42 */         if (i == 10) {
/*   106:   44 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   107:      */         }
/*   108:      */       }
/*   109:      */       catch (UserException localUserException)
/*   110:      */       {
/*   111:   49 */         local_Request.isRMI();
/*   112:      */         
/*   113:      */ 
/*   114:   52 */         throw new java.rmi.RemoteException(localUserException.type);
/*   115:      */       }
/*   116:      */       catch (SystemException localSystemException)
/*   117:      */       {
/*   118:   56 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   119:      */       }
/*   120:      */       finally
/*   121:      */       {
/*   122:   60 */         if (local_Request != null) {
/*   123:   62 */           local_Request.close();
/*   124:      */         }
/*   125:   64 */         localLocalStack.setArgsOnLocal(bool);
/*   126:      */       }
/*   127:      */     }
/*   128:      */   }
/*   129:      */   
/*   130:      */   public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*   131:      */     throws java.rmi.RemoteException
/*   132:      */   {
/*   133:   73 */     for (int i = 1;; i++)
/*   134:      */     {
/*   135:   75 */       _Request local_Request = null;
/*   136:   76 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   137:   77 */       boolean bool1 = false;
/*   138:   78 */       LocalFrame localLocalFrame = null;
/*   139:   79 */       boolean bool2 = false;
/*   140:      */       try
/*   141:      */       {
/*   142:   82 */         local_Request = __request("addCustomers");
/*   143:   83 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   144:   84 */         bool2 = localLocalStack.isArgsOnLocal();
/*   145:   85 */         localLocalStack.setArgsOnLocal(bool1);
/*   146:   87 */         if (bool1)
/*   147:      */         {
/*   148:   89 */           localLocalFrame = new LocalFrame(1);
/*   149:   90 */           localLocalStack.push(localLocalFrame);
/*   150:      */         }
/*   151:      */         OutputStream localOutputStream;
/*   152:   92 */         if (!bool1)
/*   153:      */         {
/*   154:   94 */           localOutputStream = local_Request.getOutputStream();
/*   155:   95 */           if (local_Request.isRMI()) {
/*   156:   95 */             local_Request.write_value(paramArrayOfCustomerInfo, new CustomerInfo[0].getClass());
/*   157:      */           } else {
/*   158:   95 */             CustomerInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerInfo);
/*   159:      */           }
/*   160:      */         }
/*   161:      */         else
/*   162:      */         {
/*   163:   99 */           localOutputStream = local_Request.getOutputStream();
/*   164:  100 */           localLocalFrame.add(paramArrayOfCustomerInfo);
/*   165:      */         }
/*   166:  102 */         local_Request.invoke();
/*   167:      */         int j;
/*   168:  103 */         if (bool1)
/*   169:      */         {
/*   170:  106 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*   171:  107 */           return k;
/*   172:      */         }
/*   173:  109 */         InputStream localInputStream = local_Request.getInputStream();
/*   174:      */         
/*   175:  111 */         int m = localInputStream.read_long();
/*   176:  112 */         return m;
/*   177:      */       }
/*   178:      */       catch (TRANSIENT localTRANSIENT)
/*   179:      */       {
/*   180:  116 */         if (i == 10) {
/*   181:  118 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   182:      */         }
/*   183:      */       }
/*   184:      */       catch (UserException localUserException)
/*   185:      */       {
/*   186:  123 */         local_Request.isRMI();
/*   187:      */         
/*   188:      */ 
/*   189:  126 */         throw new java.rmi.RemoteException(localUserException.type);
/*   190:      */       }
/*   191:      */       catch (SystemException localSystemException)
/*   192:      */       {
/*   193:  130 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   194:      */       }
/*   195:      */       finally
/*   196:      */       {
/*   197:  134 */         if (local_Request != null) {
/*   198:  136 */           local_Request.close();
/*   199:      */         }
/*   200:  138 */         if (bool1) {
/*   201:  139 */           localLocalStack.pop(localLocalFrame);
/*   202:      */         }
/*   203:  140 */         localLocalStack.setArgsOnLocal(bool2);
/*   204:      */       }
/*   205:      */     }
/*   206:      */   }
/*   207:      */   
/*   208:      */   public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*   209:      */     throws java.rmi.RemoteException
/*   210:      */   {
/*   211:  149 */     for (int i = 1;; i++)
/*   212:      */     {
/*   213:  151 */       _Request local_Request = null;
/*   214:  152 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   215:  153 */       boolean bool1 = false;
/*   216:  154 */       LocalFrame localLocalFrame = null;
/*   217:  155 */       boolean bool2 = false;
/*   218:      */       try
/*   219:      */       {
/*   220:  158 */         local_Request = __request("modifyCustomers");
/*   221:  159 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   222:  160 */         bool2 = localLocalStack.isArgsOnLocal();
/*   223:  161 */         localLocalStack.setArgsOnLocal(bool1);
/*   224:  163 */         if (bool1)
/*   225:      */         {
/*   226:  165 */           localLocalFrame = new LocalFrame(1);
/*   227:  166 */           localLocalStack.push(localLocalFrame);
/*   228:      */         }
/*   229:      */         OutputStream localOutputStream;
/*   230:  168 */         if (!bool1)
/*   231:      */         {
/*   232:  170 */           localOutputStream = local_Request.getOutputStream();
/*   233:  171 */           if (local_Request.isRMI()) {
/*   234:  171 */             local_Request.write_value(paramArrayOfCustomerInfo, new CustomerInfo[0].getClass());
/*   235:      */           } else {
/*   236:  171 */             CustomerInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerInfo);
/*   237:      */           }
/*   238:      */         }
/*   239:      */         else
/*   240:      */         {
/*   241:  175 */           localOutputStream = local_Request.getOutputStream();
/*   242:  176 */           localLocalFrame.add(paramArrayOfCustomerInfo);
/*   243:      */         }
/*   244:  178 */         local_Request.invoke();
/*   245:      */         int j;
/*   246:  179 */         if (bool1)
/*   247:      */         {
/*   248:  182 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*   249:  183 */           return k;
/*   250:      */         }
/*   251:  185 */         InputStream localInputStream = local_Request.getInputStream();
/*   252:      */         
/*   253:  187 */         int m = localInputStream.read_long();
/*   254:  188 */         return m;
/*   255:      */       }
/*   256:      */       catch (TRANSIENT localTRANSIENT)
/*   257:      */       {
/*   258:  192 */         if (i == 10) {
/*   259:  194 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   260:      */         }
/*   261:      */       }
/*   262:      */       catch (UserException localUserException)
/*   263:      */       {
/*   264:  199 */         local_Request.isRMI();
/*   265:      */         
/*   266:      */ 
/*   267:  202 */         throw new java.rmi.RemoteException(localUserException.type);
/*   268:      */       }
/*   269:      */       catch (SystemException localSystemException)
/*   270:      */       {
/*   271:  206 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   272:      */       }
/*   273:      */       finally
/*   274:      */       {
/*   275:  210 */         if (local_Request != null) {
/*   276:  212 */           local_Request.close();
/*   277:      */         }
/*   278:  214 */         if (bool1) {
/*   279:  215 */           localLocalStack.pop(localLocalFrame);
/*   280:      */         }
/*   281:  216 */         localLocalStack.setArgsOnLocal(bool2);
/*   282:      */       }
/*   283:      */     }
/*   284:      */   }
/*   285:      */   
/*   286:      */   public int deleteCustomers(String[] paramArrayOfString)
/*   287:      */     throws java.rmi.RemoteException
/*   288:      */   {
/*   289:  225 */     for (int i = 1;; i++)
/*   290:      */     {
/*   291:  227 */       _Request local_Request = null;
/*   292:  228 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   293:  229 */       boolean bool1 = false;
/*   294:  230 */       LocalFrame localLocalFrame = null;
/*   295:  231 */       boolean bool2 = false;
/*   296:      */       try
/*   297:      */       {
/*   298:  234 */         local_Request = __request("deleteCustomers__StringSeq", "deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/*   299:  235 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   300:  236 */         bool2 = localLocalStack.isArgsOnLocal();
/*   301:  237 */         localLocalStack.setArgsOnLocal(bool1);
/*   302:  239 */         if (bool1)
/*   303:      */         {
/*   304:  241 */           localLocalFrame = new LocalFrame(1);
/*   305:  242 */           localLocalStack.push(localLocalFrame);
/*   306:      */         }
/*   307:      */         OutputStream localOutputStream;
/*   308:  244 */         if (!bool1)
/*   309:      */         {
/*   310:  246 */           localOutputStream = local_Request.getOutputStream();
/*   311:  247 */           if (local_Request.isRMI()) {
/*   312:  247 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*   313:      */           } else {
/*   314:  247 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*   315:      */           }
/*   316:      */         }
/*   317:      */         else
/*   318:      */         {
/*   319:  251 */           localOutputStream = local_Request.getOutputStream();
/*   320:  252 */           localLocalFrame.add(paramArrayOfString);
/*   321:      */         }
/*   322:  254 */         local_Request.invoke();
/*   323:      */         int j;
/*   324:  255 */         if (bool1)
/*   325:      */         {
/*   326:  258 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*   327:  259 */           return k;
/*   328:      */         }
/*   329:  261 */         InputStream localInputStream = local_Request.getInputStream();
/*   330:      */         
/*   331:  263 */         int m = localInputStream.read_long();
/*   332:  264 */         return m;
/*   333:      */       }
/*   334:      */       catch (TRANSIENT localTRANSIENT)
/*   335:      */       {
/*   336:  268 */         if (i == 10) {
/*   337:  270 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   338:      */         }
/*   339:      */       }
/*   340:      */       catch (UserException localUserException)
/*   341:      */       {
/*   342:  275 */         local_Request.isRMI();
/*   343:      */         
/*   344:      */ 
/*   345:  278 */         throw new java.rmi.RemoteException(localUserException.type);
/*   346:      */       }
/*   347:      */       catch (SystemException localSystemException)
/*   348:      */       {
/*   349:  282 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   350:      */       }
/*   351:      */       finally
/*   352:      */       {
/*   353:  286 */         if (local_Request != null) {
/*   354:  288 */           local_Request.close();
/*   355:      */         }
/*   356:  290 */         if (bool1) {
/*   357:  291 */           localLocalStack.pop(localLocalFrame);
/*   358:      */         }
/*   359:  292 */         localLocalStack.setArgsOnLocal(bool2);
/*   360:      */       }
/*   361:      */     }
/*   362:      */   }
/*   363:      */   
/*   364:      */   public int deleteCustomers(String[] paramArrayOfString, int paramInt)
/*   365:      */     throws java.rmi.RemoteException
/*   366:      */   {
/*   367:  302 */     for (int i = 1;; i++)
/*   368:      */     {
/*   369:  304 */       _Request local_Request = null;
/*   370:  305 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   371:  306 */       boolean bool1 = false;
/*   372:  307 */       LocalFrame localLocalFrame = null;
/*   373:  308 */       boolean bool2 = false;
/*   374:      */       try
/*   375:      */       {
/*   376:  311 */         local_Request = __request("deleteCustomers__StringSeq__long", "deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long");
/*   377:  312 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   378:  313 */         bool2 = localLocalStack.isArgsOnLocal();
/*   379:  314 */         localLocalStack.setArgsOnLocal(bool1);
/*   380:  316 */         if (bool1)
/*   381:      */         {
/*   382:  318 */           localLocalFrame = new LocalFrame(2);
/*   383:  319 */           localLocalStack.push(localLocalFrame);
/*   384:      */         }
/*   385:      */         OutputStream localOutputStream;
/*   386:  321 */         if (!bool1)
/*   387:      */         {
/*   388:  323 */           localOutputStream = local_Request.getOutputStream();
/*   389:  324 */           if (local_Request.isRMI()) {
/*   390:  324 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*   391:      */           } else {
/*   392:  324 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*   393:      */           }
/*   394:  325 */           localOutputStream.write_long(paramInt);
/*   395:      */         }
/*   396:      */         else
/*   397:      */         {
/*   398:  329 */           localOutputStream = local_Request.getOutputStream();
/*   399:  330 */           localLocalFrame.add(paramArrayOfString);
/*   400:  331 */           localLocalFrame.add(paramInt);
/*   401:      */         }
/*   402:  333 */         local_Request.invoke();
/*   403:      */         int j;
/*   404:  334 */         if (bool1)
/*   405:      */         {
/*   406:  337 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*   407:  338 */           return k;
/*   408:      */         }
/*   409:  340 */         InputStream localInputStream = local_Request.getInputStream();
/*   410:      */         
/*   411:  342 */         int m = localInputStream.read_long();
/*   412:  343 */         return m;
/*   413:      */       }
/*   414:      */       catch (TRANSIENT localTRANSIENT)
/*   415:      */       {
/*   416:  347 */         if (i == 10) {
/*   417:  349 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   418:      */         }
/*   419:      */       }
/*   420:      */       catch (UserException localUserException)
/*   421:      */       {
/*   422:  354 */         local_Request.isRMI();
/*   423:      */         
/*   424:      */ 
/*   425:  357 */         throw new java.rmi.RemoteException(localUserException.type);
/*   426:      */       }
/*   427:      */       catch (SystemException localSystemException)
/*   428:      */       {
/*   429:  361 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   430:      */       }
/*   431:      */       finally
/*   432:      */       {
/*   433:  365 */         if (local_Request != null) {
/*   434:  367 */           local_Request.close();
/*   435:      */         }
/*   436:  369 */         if (bool1) {
/*   437:  370 */           localLocalStack.pop(localLocalFrame);
/*   438:      */         }
/*   439:  371 */         localLocalStack.setArgsOnLocal(bool2);
/*   440:      */       }
/*   441:      */     }
/*   442:      */   }
/*   443:      */   
/*   444:      */   public int deactivateCustomers(String[] paramArrayOfString)
/*   445:      */     throws java.rmi.RemoteException
/*   446:      */   {
/*   447:  380 */     for (int i = 1;; i++)
/*   448:      */     {
/*   449:  382 */       _Request local_Request = null;
/*   450:  383 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   451:  384 */       boolean bool1 = false;
/*   452:  385 */       LocalFrame localLocalFrame = null;
/*   453:  386 */       boolean bool2 = false;
/*   454:      */       try
/*   455:      */       {
/*   456:  389 */         local_Request = __request("deactivateCustomers");
/*   457:  390 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   458:  391 */         bool2 = localLocalStack.isArgsOnLocal();
/*   459:  392 */         localLocalStack.setArgsOnLocal(bool1);
/*   460:  394 */         if (bool1)
/*   461:      */         {
/*   462:  396 */           localLocalFrame = new LocalFrame(1);
/*   463:  397 */           localLocalStack.push(localLocalFrame);
/*   464:      */         }
/*   465:      */         OutputStream localOutputStream;
/*   466:  399 */         if (!bool1)
/*   467:      */         {
/*   468:  401 */           localOutputStream = local_Request.getOutputStream();
/*   469:  402 */           if (local_Request.isRMI()) {
/*   470:  402 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*   471:      */           } else {
/*   472:  402 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*   473:      */           }
/*   474:      */         }
/*   475:      */         else
/*   476:      */         {
/*   477:  406 */           localOutputStream = local_Request.getOutputStream();
/*   478:  407 */           localLocalFrame.add(paramArrayOfString);
/*   479:      */         }
/*   480:  409 */         local_Request.invoke();
/*   481:      */         int j;
/*   482:  410 */         if (bool1)
/*   483:      */         {
/*   484:  413 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*   485:  414 */           return k;
/*   486:      */         }
/*   487:  416 */         InputStream localInputStream = local_Request.getInputStream();
/*   488:      */         
/*   489:  418 */         int m = localInputStream.read_long();
/*   490:  419 */         return m;
/*   491:      */       }
/*   492:      */       catch (TRANSIENT localTRANSIENT)
/*   493:      */       {
/*   494:  423 */         if (i == 10) {
/*   495:  425 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   496:      */         }
/*   497:      */       }
/*   498:      */       catch (UserException localUserException)
/*   499:      */       {
/*   500:  430 */         local_Request.isRMI();
/*   501:      */         
/*   502:      */ 
/*   503:  433 */         throw new java.rmi.RemoteException(localUserException.type);
/*   504:      */       }
/*   505:      */       catch (SystemException localSystemException)
/*   506:      */       {
/*   507:  437 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   508:      */       }
/*   509:      */       finally
/*   510:      */       {
/*   511:  441 */         if (local_Request != null) {
/*   512:  443 */           local_Request.close();
/*   513:      */         }
/*   514:  445 */         if (bool1) {
/*   515:  446 */           localLocalStack.pop(localLocalFrame);
/*   516:      */         }
/*   517:  447 */         localLocalStack.setArgsOnLocal(bool2);
/*   518:      */       }
/*   519:      */     }
/*   520:      */   }
/*   521:      */   
/*   522:      */   public int activateCustomers(String[] paramArrayOfString)
/*   523:      */     throws java.rmi.RemoteException
/*   524:      */   {
/*   525:  456 */     for (int i = 1;; i++)
/*   526:      */     {
/*   527:  458 */       _Request local_Request = null;
/*   528:  459 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   529:  460 */       boolean bool1 = false;
/*   530:  461 */       LocalFrame localLocalFrame = null;
/*   531:  462 */       boolean bool2 = false;
/*   532:      */       try
/*   533:      */       {
/*   534:  465 */         local_Request = __request("activateCustomers");
/*   535:  466 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   536:  467 */         bool2 = localLocalStack.isArgsOnLocal();
/*   537:  468 */         localLocalStack.setArgsOnLocal(bool1);
/*   538:  470 */         if (bool1)
/*   539:      */         {
/*   540:  472 */           localLocalFrame = new LocalFrame(1);
/*   541:  473 */           localLocalStack.push(localLocalFrame);
/*   542:      */         }
/*   543:      */         OutputStream localOutputStream;
/*   544:  475 */         if (!bool1)
/*   545:      */         {
/*   546:  477 */           localOutputStream = local_Request.getOutputStream();
/*   547:  478 */           if (local_Request.isRMI()) {
/*   548:  478 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*   549:      */           } else {
/*   550:  478 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*   551:      */           }
/*   552:      */         }
/*   553:      */         else
/*   554:      */         {
/*   555:  482 */           localOutputStream = local_Request.getOutputStream();
/*   556:  483 */           localLocalFrame.add(paramArrayOfString);
/*   557:      */         }
/*   558:  485 */         local_Request.invoke();
/*   559:      */         int j;
/*   560:  486 */         if (bool1)
/*   561:      */         {
/*   562:  489 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*   563:  490 */           return k;
/*   564:      */         }
/*   565:  492 */         InputStream localInputStream = local_Request.getInputStream();
/*   566:      */         
/*   567:  494 */         int m = localInputStream.read_long();
/*   568:  495 */         return m;
/*   569:      */       }
/*   570:      */       catch (TRANSIENT localTRANSIENT)
/*   571:      */       {
/*   572:  499 */         if (i == 10) {
/*   573:  501 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   574:      */         }
/*   575:      */       }
/*   576:      */       catch (UserException localUserException)
/*   577:      */       {
/*   578:  506 */         local_Request.isRMI();
/*   579:      */         
/*   580:      */ 
/*   581:  509 */         throw new java.rmi.RemoteException(localUserException.type);
/*   582:      */       }
/*   583:      */       catch (SystemException localSystemException)
/*   584:      */       {
/*   585:  513 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   586:      */       }
/*   587:      */       finally
/*   588:      */       {
/*   589:  517 */         if (local_Request != null) {
/*   590:  519 */           local_Request.close();
/*   591:      */         }
/*   592:  521 */         if (bool1) {
/*   593:  522 */           localLocalStack.pop(localLocalFrame);
/*   594:      */         }
/*   595:  523 */         localLocalStack.setArgsOnLocal(bool2);
/*   596:      */       }
/*   597:      */     }
/*   598:      */   }
/*   599:      */   
/*   600:      */   public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
/*   601:      */     throws java.rmi.RemoteException
/*   602:      */   {
/*   603:  532 */     for (int i = 1;; i++)
/*   604:      */     {
/*   605:  534 */       _Request local_Request = null;
/*   606:  535 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   607:  536 */       boolean bool1 = false;
/*   608:  537 */       LocalFrame localLocalFrame = null;
/*   609:  538 */       boolean bool2 = false;
/*   610:      */       try
/*   611:      */       {
/*   612:  541 */         local_Request = __request("getCustomersInfo");
/*   613:  542 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   614:  543 */         bool2 = localLocalStack.isArgsOnLocal();
/*   615:  544 */         localLocalStack.setArgsOnLocal(bool1);
/*   616:  546 */         if (bool1)
/*   617:      */         {
/*   618:  548 */           localLocalFrame = new LocalFrame(1);
/*   619:  549 */           localLocalStack.push(localLocalFrame);
/*   620:      */         }
/*   621:  551 */         if (!bool1)
/*   622:      */         {
/*   623:  553 */           localObject4 = local_Request.getOutputStream();
/*   624:  554 */           if (local_Request.isRMI()) {
/*   625:  554 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*   626:      */           } else {
/*   627:  554 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/*   628:      */           }
/*   629:      */         }
/*   630:      */         else
/*   631:      */         {
/*   632:  558 */           localObject4 = local_Request.getOutputStream();
/*   633:  559 */           localLocalFrame.add(paramArrayOfString);
/*   634:      */         }
/*   635:  561 */         local_Request.invoke();
/*   636:      */         Object localObject5;
/*   637:      */         Object localObject1;
/*   638:  562 */         if (bool1)
/*   639:      */         {
/*   640:  564 */           localObject4 = null;
/*   641:  565 */           localObject5 = localLocalFrame.getResult();
/*   642:  566 */           if (localObject5 != null) {
/*   643:  568 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*   644:      */           }
/*   645:  570 */           return localObject4;
/*   646:      */         }
/*   647:  572 */         Object localObject4 = local_Request.getInputStream();
/*   648:  574 */         if (local_Request.isRMI()) {
/*   649:  574 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*   650:      */         } else {
/*   651:  574 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*   652:      */         }
/*   653:  575 */         return localObject5;
/*   654:      */       }
/*   655:      */       catch (TRANSIENT localTRANSIENT)
/*   656:      */       {
/*   657:  579 */         if (i == 10) {
/*   658:  581 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   659:      */         }
/*   660:      */       }
/*   661:      */       catch (UserException localUserException)
/*   662:      */       {
/*   663:  586 */         local_Request.isRMI();
/*   664:      */         
/*   665:      */ 
/*   666:  589 */         throw new java.rmi.RemoteException(localUserException.type);
/*   667:      */       }
/*   668:      */       catch (SystemException localSystemException)
/*   669:      */       {
/*   670:  593 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   671:      */       }
/*   672:      */       finally
/*   673:      */       {
/*   674:  597 */         if (local_Request != null) {
/*   675:  599 */           local_Request.close();
/*   676:      */         }
/*   677:  601 */         if (bool1) {
/*   678:  602 */           localLocalStack.pop(localLocalFrame);
/*   679:      */         }
/*   680:  603 */         localLocalStack.setArgsOnLocal(bool2);
/*   681:      */       }
/*   682:      */     }
/*   683:      */   }
/*   684:      */   
/*   685:      */   public CustomerInfo[] getCustomerByType(String paramString)
/*   686:      */     throws java.rmi.RemoteException
/*   687:      */   {
/*   688:  612 */     for (int i = 1;; i++)
/*   689:      */     {
/*   690:  614 */       _Request local_Request = null;
/*   691:  615 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   692:  616 */       boolean bool1 = false;
/*   693:  617 */       LocalFrame localLocalFrame = null;
/*   694:  618 */       boolean bool2 = false;
/*   695:      */       try
/*   696:      */       {
/*   697:  621 */         local_Request = __request("getCustomerByType");
/*   698:  622 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   699:  623 */         bool2 = localLocalStack.isArgsOnLocal();
/*   700:  624 */         localLocalStack.setArgsOnLocal(bool1);
/*   701:  626 */         if (bool1)
/*   702:      */         {
/*   703:  628 */           localLocalFrame = new LocalFrame(1);
/*   704:  629 */           localLocalStack.push(localLocalFrame);
/*   705:      */         }
/*   706:  631 */         if (!bool1)
/*   707:      */         {
/*   708:  633 */           localObject4 = local_Request.getOutputStream();
/*   709:  634 */           local_Request.write_string(paramString);
/*   710:      */         }
/*   711:      */         else
/*   712:      */         {
/*   713:  638 */           localObject4 = local_Request.getOutputStream();
/*   714:  639 */           localLocalFrame.add(paramString);
/*   715:      */         }
/*   716:  641 */         local_Request.invoke();
/*   717:      */         Object localObject5;
/*   718:      */         Object localObject1;
/*   719:  642 */         if (bool1)
/*   720:      */         {
/*   721:  644 */           localObject4 = null;
/*   722:  645 */           localObject5 = localLocalFrame.getResult();
/*   723:  646 */           if (localObject5 != null) {
/*   724:  648 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*   725:      */           }
/*   726:  650 */           return localObject4;
/*   727:      */         }
/*   728:  652 */         Object localObject4 = local_Request.getInputStream();
/*   729:  654 */         if (local_Request.isRMI()) {
/*   730:  654 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*   731:      */         } else {
/*   732:  654 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*   733:      */         }
/*   734:  655 */         return localObject5;
/*   735:      */       }
/*   736:      */       catch (TRANSIENT localTRANSIENT)
/*   737:      */       {
/*   738:  659 */         if (i == 10) {
/*   739:  661 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   740:      */         }
/*   741:      */       }
/*   742:      */       catch (UserException localUserException)
/*   743:      */       {
/*   744:  666 */         local_Request.isRMI();
/*   745:      */         
/*   746:      */ 
/*   747:  669 */         throw new java.rmi.RemoteException(localUserException.type);
/*   748:      */       }
/*   749:      */       catch (SystemException localSystemException)
/*   750:      */       {
/*   751:  673 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   752:      */       }
/*   753:      */       finally
/*   754:      */       {
/*   755:  677 */         if (local_Request != null) {
/*   756:  679 */           local_Request.close();
/*   757:      */         }
/*   758:  681 */         if (bool1) {
/*   759:  682 */           localLocalStack.pop(localLocalFrame);
/*   760:      */         }
/*   761:  683 */         localLocalStack.setArgsOnLocal(bool2);
/*   762:      */       }
/*   763:      */     }
/*   764:      */   }
/*   765:      */   
/*   766:      */   public CustomerInfo[] getCustomerByFI(String paramString)
/*   767:      */     throws java.rmi.RemoteException
/*   768:      */   {
/*   769:  692 */     for (int i = 1;; i++)
/*   770:      */     {
/*   771:  694 */       _Request local_Request = null;
/*   772:  695 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   773:  696 */       boolean bool1 = false;
/*   774:  697 */       LocalFrame localLocalFrame = null;
/*   775:  698 */       boolean bool2 = false;
/*   776:      */       try
/*   777:      */       {
/*   778:  701 */         local_Request = __request("getCustomerByFI");
/*   779:  702 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   780:  703 */         bool2 = localLocalStack.isArgsOnLocal();
/*   781:  704 */         localLocalStack.setArgsOnLocal(bool1);
/*   782:  706 */         if (bool1)
/*   783:      */         {
/*   784:  708 */           localLocalFrame = new LocalFrame(1);
/*   785:  709 */           localLocalStack.push(localLocalFrame);
/*   786:      */         }
/*   787:  711 */         if (!bool1)
/*   788:      */         {
/*   789:  713 */           localObject4 = local_Request.getOutputStream();
/*   790:  714 */           local_Request.write_string(paramString);
/*   791:      */         }
/*   792:      */         else
/*   793:      */         {
/*   794:  718 */           localObject4 = local_Request.getOutputStream();
/*   795:  719 */           localLocalFrame.add(paramString);
/*   796:      */         }
/*   797:  721 */         local_Request.invoke();
/*   798:      */         Object localObject5;
/*   799:      */         Object localObject1;
/*   800:  722 */         if (bool1)
/*   801:      */         {
/*   802:  724 */           localObject4 = null;
/*   803:  725 */           localObject5 = localLocalFrame.getResult();
/*   804:  726 */           if (localObject5 != null) {
/*   805:  728 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*   806:      */           }
/*   807:  730 */           return localObject4;
/*   808:      */         }
/*   809:  732 */         Object localObject4 = local_Request.getInputStream();
/*   810:  734 */         if (local_Request.isRMI()) {
/*   811:  734 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*   812:      */         } else {
/*   813:  734 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*   814:      */         }
/*   815:  735 */         return localObject5;
/*   816:      */       }
/*   817:      */       catch (TRANSIENT localTRANSIENT)
/*   818:      */       {
/*   819:  739 */         if (i == 10) {
/*   820:  741 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   821:      */         }
/*   822:      */       }
/*   823:      */       catch (UserException localUserException)
/*   824:      */       {
/*   825:  746 */         local_Request.isRMI();
/*   826:      */         
/*   827:      */ 
/*   828:  749 */         throw new java.rmi.RemoteException(localUserException.type);
/*   829:      */       }
/*   830:      */       catch (SystemException localSystemException)
/*   831:      */       {
/*   832:  753 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   833:      */       }
/*   834:      */       finally
/*   835:      */       {
/*   836:  757 */         if (local_Request != null) {
/*   837:  759 */           local_Request.close();
/*   838:      */         }
/*   839:  761 */         if (bool1) {
/*   840:  762 */           localLocalStack.pop(localLocalFrame);
/*   841:      */         }
/*   842:  763 */         localLocalStack.setArgsOnLocal(bool2);
/*   843:      */       }
/*   844:      */     }
/*   845:      */   }
/*   846:      */   
/*   847:      */   public CustomerInfo[] getCustomerByCategory(String paramString)
/*   848:      */     throws java.rmi.RemoteException
/*   849:      */   {
/*   850:  772 */     for (int i = 1;; i++)
/*   851:      */     {
/*   852:  774 */       _Request local_Request = null;
/*   853:  775 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   854:  776 */       boolean bool1 = false;
/*   855:  777 */       LocalFrame localLocalFrame = null;
/*   856:  778 */       boolean bool2 = false;
/*   857:      */       try
/*   858:      */       {
/*   859:  781 */         local_Request = __request("getCustomerByCategory");
/*   860:  782 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   861:  783 */         bool2 = localLocalStack.isArgsOnLocal();
/*   862:  784 */         localLocalStack.setArgsOnLocal(bool1);
/*   863:  786 */         if (bool1)
/*   864:      */         {
/*   865:  788 */           localLocalFrame = new LocalFrame(1);
/*   866:  789 */           localLocalStack.push(localLocalFrame);
/*   867:      */         }
/*   868:  791 */         if (!bool1)
/*   869:      */         {
/*   870:  793 */           localObject4 = local_Request.getOutputStream();
/*   871:  794 */           local_Request.write_string(paramString);
/*   872:      */         }
/*   873:      */         else
/*   874:      */         {
/*   875:  798 */           localObject4 = local_Request.getOutputStream();
/*   876:  799 */           localLocalFrame.add(paramString);
/*   877:      */         }
/*   878:  801 */         local_Request.invoke();
/*   879:      */         Object localObject5;
/*   880:      */         Object localObject1;
/*   881:  802 */         if (bool1)
/*   882:      */         {
/*   883:  804 */           localObject4 = null;
/*   884:  805 */           localObject5 = localLocalFrame.getResult();
/*   885:  806 */           if (localObject5 != null) {
/*   886:  808 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*   887:      */           }
/*   888:  810 */           return localObject4;
/*   889:      */         }
/*   890:  812 */         Object localObject4 = local_Request.getInputStream();
/*   891:  814 */         if (local_Request.isRMI()) {
/*   892:  814 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*   893:      */         } else {
/*   894:  814 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*   895:      */         }
/*   896:  815 */         return localObject5;
/*   897:      */       }
/*   898:      */       catch (TRANSIENT localTRANSIENT)
/*   899:      */       {
/*   900:  819 */         if (i == 10) {
/*   901:  821 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   902:      */         }
/*   903:      */       }
/*   904:      */       catch (UserException localUserException)
/*   905:      */       {
/*   906:  826 */         local_Request.isRMI();
/*   907:      */         
/*   908:      */ 
/*   909:  829 */         throw new java.rmi.RemoteException(localUserException.type);
/*   910:      */       }
/*   911:      */       catch (SystemException localSystemException)
/*   912:      */       {
/*   913:  833 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   914:      */       }
/*   915:      */       finally
/*   916:      */       {
/*   917:  837 */         if (local_Request != null) {
/*   918:  839 */           local_Request.close();
/*   919:      */         }
/*   920:  841 */         if (bool1) {
/*   921:  842 */           localLocalStack.pop(localLocalFrame);
/*   922:      */         }
/*   923:  843 */         localLocalStack.setArgsOnLocal(bool2);
/*   924:      */       }
/*   925:      */     }
/*   926:      */   }
/*   927:      */   
/*   928:      */   public CustomerInfo[] getCustomerByGroup(String paramString)
/*   929:      */     throws java.rmi.RemoteException
/*   930:      */   {
/*   931:  852 */     for (int i = 1;; i++)
/*   932:      */     {
/*   933:  854 */       _Request local_Request = null;
/*   934:  855 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*   935:  856 */       boolean bool1 = false;
/*   936:  857 */       LocalFrame localLocalFrame = null;
/*   937:  858 */       boolean bool2 = false;
/*   938:      */       try
/*   939:      */       {
/*   940:  861 */         local_Request = __request("getCustomerByGroup");
/*   941:  862 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*   942:  863 */         bool2 = localLocalStack.isArgsOnLocal();
/*   943:  864 */         localLocalStack.setArgsOnLocal(bool1);
/*   944:  866 */         if (bool1)
/*   945:      */         {
/*   946:  868 */           localLocalFrame = new LocalFrame(1);
/*   947:  869 */           localLocalStack.push(localLocalFrame);
/*   948:      */         }
/*   949:  871 */         if (!bool1)
/*   950:      */         {
/*   951:  873 */           localObject4 = local_Request.getOutputStream();
/*   952:  874 */           local_Request.write_string(paramString);
/*   953:      */         }
/*   954:      */         else
/*   955:      */         {
/*   956:  878 */           localObject4 = local_Request.getOutputStream();
/*   957:  879 */           localLocalFrame.add(paramString);
/*   958:      */         }
/*   959:  881 */         local_Request.invoke();
/*   960:      */         Object localObject5;
/*   961:      */         Object localObject1;
/*   962:  882 */         if (bool1)
/*   963:      */         {
/*   964:  884 */           localObject4 = null;
/*   965:  885 */           localObject5 = localLocalFrame.getResult();
/*   966:  886 */           if (localObject5 != null) {
/*   967:  888 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*   968:      */           }
/*   969:  890 */           return localObject4;
/*   970:      */         }
/*   971:  892 */         Object localObject4 = local_Request.getInputStream();
/*   972:  894 */         if (local_Request.isRMI()) {
/*   973:  894 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*   974:      */         } else {
/*   975:  894 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*   976:      */         }
/*   977:  895 */         return localObject5;
/*   978:      */       }
/*   979:      */       catch (TRANSIENT localTRANSIENT)
/*   980:      */       {
/*   981:  899 */         if (i == 10) {
/*   982:  901 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*   983:      */         }
/*   984:      */       }
/*   985:      */       catch (UserException localUserException)
/*   986:      */       {
/*   987:  906 */         local_Request.isRMI();
/*   988:      */         
/*   989:      */ 
/*   990:  909 */         throw new java.rmi.RemoteException(localUserException.type);
/*   991:      */       }
/*   992:      */       catch (SystemException localSystemException)
/*   993:      */       {
/*   994:  913 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*   995:      */       }
/*   996:      */       finally
/*   997:      */       {
/*   998:  917 */         if (local_Request != null) {
/*   999:  919 */           local_Request.close();
/*  1000:      */         }
/*  1001:  921 */         if (bool1) {
/*  1002:  922 */           localLocalStack.pop(localLocalFrame);
/*  1003:      */         }
/*  1004:  923 */         localLocalStack.setArgsOnLocal(bool2);
/*  1005:      */       }
/*  1006:      */     }
/*  1007:      */   }
/*  1008:      */   
/*  1009:      */   public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
/*  1010:      */     throws java.rmi.RemoteException
/*  1011:      */   {
/*  1012:  933 */     for (int i = 1;; i++)
/*  1013:      */     {
/*  1014:  935 */       _Request local_Request = null;
/*  1015:  936 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1016:  937 */       boolean bool1 = false;
/*  1017:  938 */       LocalFrame localLocalFrame = null;
/*  1018:  939 */       boolean bool2 = false;
/*  1019:      */       try
/*  1020:      */       {
/*  1021:  942 */         local_Request = __request("getCustomerByTypeAndFI");
/*  1022:  943 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1023:  944 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1024:  945 */         localLocalStack.setArgsOnLocal(bool1);
/*  1025:  947 */         if (bool1)
/*  1026:      */         {
/*  1027:  949 */           localLocalFrame = new LocalFrame(2);
/*  1028:  950 */           localLocalStack.push(localLocalFrame);
/*  1029:      */         }
/*  1030:  952 */         if (!bool1)
/*  1031:      */         {
/*  1032:  954 */           localObject4 = local_Request.getOutputStream();
/*  1033:  955 */           local_Request.write_string(paramString1);
/*  1034:  956 */           local_Request.write_string(paramString2);
/*  1035:      */         }
/*  1036:      */         else
/*  1037:      */         {
/*  1038:  960 */           localObject4 = local_Request.getOutputStream();
/*  1039:  961 */           localLocalFrame.add(paramString1);
/*  1040:  962 */           localLocalFrame.add(paramString2);
/*  1041:      */         }
/*  1042:  964 */         local_Request.invoke();
/*  1043:      */         Object localObject5;
/*  1044:      */         Object localObject1;
/*  1045:  965 */         if (bool1)
/*  1046:      */         {
/*  1047:  967 */           localObject4 = null;
/*  1048:  968 */           localObject5 = localLocalFrame.getResult();
/*  1049:  969 */           if (localObject5 != null) {
/*  1050:  971 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  1051:      */           }
/*  1052:  973 */           return localObject4;
/*  1053:      */         }
/*  1054:  975 */         Object localObject4 = local_Request.getInputStream();
/*  1055:  977 */         if (local_Request.isRMI()) {
/*  1056:  977 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  1057:      */         } else {
/*  1058:  977 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  1059:      */         }
/*  1060:  978 */         return localObject5;
/*  1061:      */       }
/*  1062:      */       catch (TRANSIENT localTRANSIENT)
/*  1063:      */       {
/*  1064:  982 */         if (i == 10) {
/*  1065:  984 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1066:      */         }
/*  1067:      */       }
/*  1068:      */       catch (UserException localUserException)
/*  1069:      */       {
/*  1070:  989 */         local_Request.isRMI();
/*  1071:      */         
/*  1072:      */ 
/*  1073:  992 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1074:      */       }
/*  1075:      */       catch (SystemException localSystemException)
/*  1076:      */       {
/*  1077:  996 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1078:      */       }
/*  1079:      */       finally
/*  1080:      */       {
/*  1081: 1000 */         if (local_Request != null) {
/*  1082: 1002 */           local_Request.close();
/*  1083:      */         }
/*  1084: 1004 */         if (bool1) {
/*  1085: 1005 */           localLocalStack.pop(localLocalFrame);
/*  1086:      */         }
/*  1087: 1006 */         localLocalStack.setArgsOnLocal(bool2);
/*  1088:      */       }
/*  1089:      */     }
/*  1090:      */   }
/*  1091:      */   
/*  1092:      */   public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
/*  1093:      */     throws java.rmi.RemoteException
/*  1094:      */   {
/*  1095: 1016 */     for (int i = 1;; i++)
/*  1096:      */     {
/*  1097: 1018 */       _Request local_Request = null;
/*  1098: 1019 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1099: 1020 */       boolean bool1 = false;
/*  1100: 1021 */       LocalFrame localLocalFrame = null;
/*  1101: 1022 */       boolean bool2 = false;
/*  1102:      */       try
/*  1103:      */       {
/*  1104: 1025 */         local_Request = __request("getCustomerByCategoryAndFI");
/*  1105: 1026 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1106: 1027 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1107: 1028 */         localLocalStack.setArgsOnLocal(bool1);
/*  1108: 1030 */         if (bool1)
/*  1109:      */         {
/*  1110: 1032 */           localLocalFrame = new LocalFrame(2);
/*  1111: 1033 */           localLocalStack.push(localLocalFrame);
/*  1112:      */         }
/*  1113: 1035 */         if (!bool1)
/*  1114:      */         {
/*  1115: 1037 */           localObject4 = local_Request.getOutputStream();
/*  1116: 1038 */           local_Request.write_string(paramString1);
/*  1117: 1039 */           local_Request.write_string(paramString2);
/*  1118:      */         }
/*  1119:      */         else
/*  1120:      */         {
/*  1121: 1043 */           localObject4 = local_Request.getOutputStream();
/*  1122: 1044 */           localLocalFrame.add(paramString1);
/*  1123: 1045 */           localLocalFrame.add(paramString2);
/*  1124:      */         }
/*  1125: 1047 */         local_Request.invoke();
/*  1126:      */         Object localObject5;
/*  1127:      */         Object localObject1;
/*  1128: 1048 */         if (bool1)
/*  1129:      */         {
/*  1130: 1050 */           localObject4 = null;
/*  1131: 1051 */           localObject5 = localLocalFrame.getResult();
/*  1132: 1052 */           if (localObject5 != null) {
/*  1133: 1054 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  1134:      */           }
/*  1135: 1056 */           return localObject4;
/*  1136:      */         }
/*  1137: 1058 */         Object localObject4 = local_Request.getInputStream();
/*  1138: 1060 */         if (local_Request.isRMI()) {
/*  1139: 1060 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  1140:      */         } else {
/*  1141: 1060 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  1142:      */         }
/*  1143: 1061 */         return localObject5;
/*  1144:      */       }
/*  1145:      */       catch (TRANSIENT localTRANSIENT)
/*  1146:      */       {
/*  1147: 1065 */         if (i == 10) {
/*  1148: 1067 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1149:      */         }
/*  1150:      */       }
/*  1151:      */       catch (UserException localUserException)
/*  1152:      */       {
/*  1153: 1072 */         local_Request.isRMI();
/*  1154:      */         
/*  1155:      */ 
/*  1156: 1075 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1157:      */       }
/*  1158:      */       catch (SystemException localSystemException)
/*  1159:      */       {
/*  1160: 1079 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1161:      */       }
/*  1162:      */       finally
/*  1163:      */       {
/*  1164: 1083 */         if (local_Request != null) {
/*  1165: 1085 */           local_Request.close();
/*  1166:      */         }
/*  1167: 1087 */         if (bool1) {
/*  1168: 1088 */           localLocalStack.pop(localLocalFrame);
/*  1169:      */         }
/*  1170: 1089 */         localLocalStack.setArgsOnLocal(bool2);
/*  1171:      */       }
/*  1172:      */     }
/*  1173:      */   }
/*  1174:      */   
/*  1175:      */   public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
/*  1176:      */     throws java.rmi.RemoteException
/*  1177:      */   {
/*  1178: 1099 */     for (int i = 1;; i++)
/*  1179:      */     {
/*  1180: 1101 */       _Request local_Request = null;
/*  1181: 1102 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1182: 1103 */       boolean bool1 = false;
/*  1183: 1104 */       LocalFrame localLocalFrame = null;
/*  1184: 1105 */       boolean bool2 = false;
/*  1185:      */       try
/*  1186:      */       {
/*  1187: 1108 */         local_Request = __request("getCustomerByGroupAndFI");
/*  1188: 1109 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1189: 1110 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1190: 1111 */         localLocalStack.setArgsOnLocal(bool1);
/*  1191: 1113 */         if (bool1)
/*  1192:      */         {
/*  1193: 1115 */           localLocalFrame = new LocalFrame(2);
/*  1194: 1116 */           localLocalStack.push(localLocalFrame);
/*  1195:      */         }
/*  1196: 1118 */         if (!bool1)
/*  1197:      */         {
/*  1198: 1120 */           localObject4 = local_Request.getOutputStream();
/*  1199: 1121 */           local_Request.write_string(paramString1);
/*  1200: 1122 */           local_Request.write_string(paramString2);
/*  1201:      */         }
/*  1202:      */         else
/*  1203:      */         {
/*  1204: 1126 */           localObject4 = local_Request.getOutputStream();
/*  1205: 1127 */           localLocalFrame.add(paramString1);
/*  1206: 1128 */           localLocalFrame.add(paramString2);
/*  1207:      */         }
/*  1208: 1130 */         local_Request.invoke();
/*  1209:      */         Object localObject5;
/*  1210:      */         Object localObject1;
/*  1211: 1131 */         if (bool1)
/*  1212:      */         {
/*  1213: 1133 */           localObject4 = null;
/*  1214: 1134 */           localObject5 = localLocalFrame.getResult();
/*  1215: 1135 */           if (localObject5 != null) {
/*  1216: 1137 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  1217:      */           }
/*  1218: 1139 */           return localObject4;
/*  1219:      */         }
/*  1220: 1141 */         Object localObject4 = local_Request.getInputStream();
/*  1221: 1143 */         if (local_Request.isRMI()) {
/*  1222: 1143 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  1223:      */         } else {
/*  1224: 1143 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  1225:      */         }
/*  1226: 1144 */         return localObject5;
/*  1227:      */       }
/*  1228:      */       catch (TRANSIENT localTRANSIENT)
/*  1229:      */       {
/*  1230: 1148 */         if (i == 10) {
/*  1231: 1150 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1232:      */         }
/*  1233:      */       }
/*  1234:      */       catch (UserException localUserException)
/*  1235:      */       {
/*  1236: 1155 */         local_Request.isRMI();
/*  1237:      */         
/*  1238:      */ 
/*  1239: 1158 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1240:      */       }
/*  1241:      */       catch (SystemException localSystemException)
/*  1242:      */       {
/*  1243: 1162 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1244:      */       }
/*  1245:      */       finally
/*  1246:      */       {
/*  1247: 1166 */         if (local_Request != null) {
/*  1248: 1168 */           local_Request.close();
/*  1249:      */         }
/*  1250: 1170 */         if (bool1) {
/*  1251: 1171 */           localLocalStack.pop(localLocalFrame);
/*  1252:      */         }
/*  1253: 1172 */         localLocalStack.setArgsOnLocal(bool2);
/*  1254:      */       }
/*  1255:      */     }
/*  1256:      */   }
/*  1257:      */   
/*  1258:      */   public RPPSBillerInfo[] getRPPSBillerInfoByFIRPPSId(String paramString)
/*  1259:      */     throws java.rmi.RemoteException, FFSException
/*  1260:      */   {
/*  1261: 1181 */     for (int i = 1;; i++)
/*  1262:      */     {
/*  1263: 1183 */       _Request local_Request = null;
/*  1264: 1184 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1265: 1185 */       boolean bool1 = false;
/*  1266: 1186 */       LocalFrame localLocalFrame = null;
/*  1267: 1187 */       boolean bool2 = false;
/*  1268:      */       try
/*  1269:      */       {
/*  1270: 1190 */         local_Request = __request("getRPPSBillerInfoByFIRPPSId");
/*  1271: 1191 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1272: 1192 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1273: 1193 */         localLocalStack.setArgsOnLocal(bool1);
/*  1274: 1195 */         if (bool1)
/*  1275:      */         {
/*  1276: 1197 */           localLocalFrame = new LocalFrame(1);
/*  1277: 1198 */           localLocalStack.push(localLocalFrame);
/*  1278:      */         }
/*  1279: 1200 */         if (!bool1)
/*  1280:      */         {
/*  1281: 1202 */           localObject4 = local_Request.getOutputStream();
/*  1282: 1203 */           local_Request.write_string(paramString);
/*  1283:      */         }
/*  1284:      */         else
/*  1285:      */         {
/*  1286: 1207 */           localObject4 = local_Request.getOutputStream();
/*  1287: 1208 */           localLocalFrame.add(paramString);
/*  1288:      */         }
/*  1289: 1210 */         local_Request.invoke();
/*  1290:      */         Object localObject1;
/*  1291: 1211 */         if (bool1)
/*  1292:      */         {
/*  1293: 1213 */           localObject4 = null;
/*  1294: 1214 */           localObject5 = localLocalFrame.getResult();
/*  1295: 1215 */           if (localObject5 != null) {
/*  1296: 1217 */             localObject4 = (RPPSBillerInfo[])ObjectVal.clone(localObject5);
/*  1297:      */           }
/*  1298: 1219 */           return localObject4;
/*  1299:      */         }
/*  1300: 1221 */         Object localObject4 = local_Request.getInputStream();
/*  1301: 1223 */         if (local_Request.isRMI()) {
/*  1302: 1223 */           localObject5 = (RPPSBillerInfo[])local_Request.read_value(new RPPSBillerInfo[0].getClass());
/*  1303:      */         } else {
/*  1304: 1223 */           localObject5 = RPPSBillerInfoSeqHelper.read((InputStream)localObject4);
/*  1305:      */         }
/*  1306: 1224 */         return localObject5;
/*  1307:      */       }
/*  1308:      */       catch (TRANSIENT localTRANSIENT)
/*  1309:      */       {
/*  1310: 1228 */         if (i == 10) {
/*  1311: 1230 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1312:      */         }
/*  1313:      */       }
/*  1314:      */       catch (UserException localUserException)
/*  1315:      */       {
/*  1316:      */         Object localObject5;
/*  1317: 1235 */         if (local_Request.isRMI())
/*  1318:      */         {
/*  1319: 1237 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  1320: 1239 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  1321:      */           }
/*  1322:      */         }
/*  1323:      */         else
/*  1324:      */         {
/*  1325: 1244 */           localObject5 = null;
/*  1326: 1245 */           if (bool1)
/*  1327:      */           {
/*  1328: 1247 */             localObject5 = localLocalFrame.getException();
/*  1329: 1248 */             if (localObject5 != null) {
/*  1330: 1250 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  1331:      */             }
/*  1332:      */           }
/*  1333: 1253 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  1334:      */           {
/*  1335: 1255 */             if (local_Request.isRMI()) {
/*  1336: 1257 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  1337:      */             }
/*  1338: 1261 */             if (bool1)
/*  1339:      */             {
/*  1340: 1263 */               if (localObject5 != null) {
/*  1341: 1263 */                 throw ((FFSException)localObject5);
/*  1342:      */               }
/*  1343:      */             }
/*  1344:      */             else {
/*  1345: 1267 */               throw FFSExceptionHelper.read(localUserException.input);
/*  1346:      */             }
/*  1347:      */           }
/*  1348:      */         }
/*  1349: 1272 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1350:      */       }
/*  1351:      */       catch (SystemException localSystemException)
/*  1352:      */       {
/*  1353: 1276 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1354:      */       }
/*  1355:      */       finally
/*  1356:      */       {
/*  1357: 1280 */         if (local_Request != null) {
/*  1358: 1282 */           local_Request.close();
/*  1359:      */         }
/*  1360: 1284 */         if (bool1) {
/*  1361: 1285 */           localLocalStack.pop(localLocalFrame);
/*  1362:      */         }
/*  1363: 1286 */         localLocalStack.setArgsOnLocal(bool2);
/*  1364:      */       }
/*  1365:      */     }
/*  1366:      */   }
/*  1367:      */   
/*  1368:      */   public RPPSBillerInfo getRPPSBillerInfoByFIAndBillerRPPSId(String paramString1, String paramString2)
/*  1369:      */     throws java.rmi.RemoteException, FFSException
/*  1370:      */   {
/*  1371: 1296 */     for (int i = 1;; i++)
/*  1372:      */     {
/*  1373: 1298 */       _Request local_Request = null;
/*  1374: 1299 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1375: 1300 */       boolean bool1 = false;
/*  1376: 1301 */       LocalFrame localLocalFrame = null;
/*  1377: 1302 */       boolean bool2 = false;
/*  1378:      */       try
/*  1379:      */       {
/*  1380: 1305 */         local_Request = __request("getRPPSBillerInfoByFIAndBillerRPPSId");
/*  1381: 1306 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1382: 1307 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1383: 1308 */         localLocalStack.setArgsOnLocal(bool1);
/*  1384: 1310 */         if (bool1)
/*  1385:      */         {
/*  1386: 1312 */           localLocalFrame = new LocalFrame(2);
/*  1387: 1313 */           localLocalStack.push(localLocalFrame);
/*  1388:      */         }
/*  1389: 1315 */         if (!bool1)
/*  1390:      */         {
/*  1391: 1317 */           localObject4 = local_Request.getOutputStream();
/*  1392: 1318 */           local_Request.write_string(paramString1);
/*  1393: 1319 */           local_Request.write_string(paramString2);
/*  1394:      */         }
/*  1395:      */         else
/*  1396:      */         {
/*  1397: 1323 */           localObject4 = local_Request.getOutputStream();
/*  1398: 1324 */           localLocalFrame.add(paramString1);
/*  1399: 1325 */           localLocalFrame.add(paramString2);
/*  1400:      */         }
/*  1401: 1327 */         local_Request.invoke();
/*  1402:      */         Object localObject1;
/*  1403: 1328 */         if (bool1)
/*  1404:      */         {
/*  1405: 1330 */           localObject4 = null;
/*  1406: 1331 */           localObject5 = localLocalFrame.getResult();
/*  1407: 1332 */           if (localObject5 != null) {
/*  1408: 1334 */             localObject4 = (RPPSBillerInfo)ObjectVal.clone(localObject5);
/*  1409:      */           }
/*  1410: 1336 */           return localObject4;
/*  1411:      */         }
/*  1412: 1338 */         Object localObject4 = local_Request.getInputStream();
/*  1413:      */         
/*  1414: 1340 */         localObject5 = (RPPSBillerInfo)local_Request.read_value(RPPSBillerInfo.class);
/*  1415: 1341 */         return localObject5;
/*  1416:      */       }
/*  1417:      */       catch (TRANSIENT localTRANSIENT)
/*  1418:      */       {
/*  1419: 1345 */         if (i == 10) {
/*  1420: 1347 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1421:      */         }
/*  1422:      */       }
/*  1423:      */       catch (UserException localUserException)
/*  1424:      */       {
/*  1425:      */         Object localObject5;
/*  1426: 1352 */         if (local_Request.isRMI())
/*  1427:      */         {
/*  1428: 1354 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  1429: 1356 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  1430:      */           }
/*  1431:      */         }
/*  1432:      */         else
/*  1433:      */         {
/*  1434: 1361 */           localObject5 = null;
/*  1435: 1362 */           if (bool1)
/*  1436:      */           {
/*  1437: 1364 */             localObject5 = localLocalFrame.getException();
/*  1438: 1365 */             if (localObject5 != null) {
/*  1439: 1367 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  1440:      */             }
/*  1441:      */           }
/*  1442: 1370 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  1443:      */           {
/*  1444: 1372 */             if (local_Request.isRMI()) {
/*  1445: 1374 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  1446:      */             }
/*  1447: 1378 */             if (bool1)
/*  1448:      */             {
/*  1449: 1380 */               if (localObject5 != null) {
/*  1450: 1380 */                 throw ((FFSException)localObject5);
/*  1451:      */               }
/*  1452:      */             }
/*  1453:      */             else {
/*  1454: 1384 */               throw FFSExceptionHelper.read(localUserException.input);
/*  1455:      */             }
/*  1456:      */           }
/*  1457:      */         }
/*  1458: 1389 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1459:      */       }
/*  1460:      */       catch (SystemException localSystemException)
/*  1461:      */       {
/*  1462: 1393 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1463:      */       }
/*  1464:      */       finally
/*  1465:      */       {
/*  1466: 1397 */         if (local_Request != null) {
/*  1467: 1399 */           local_Request.close();
/*  1468:      */         }
/*  1469: 1401 */         if (bool1) {
/*  1470: 1402 */           localLocalStack.pop(localLocalFrame);
/*  1471:      */         }
/*  1472: 1403 */         localLocalStack.setArgsOnLocal(bool2);
/*  1473:      */       }
/*  1474:      */     }
/*  1475:      */   }
/*  1476:      */   
/*  1477:      */   public WireInfo addWireTransfer(WireInfo paramWireInfo)
/*  1478:      */     throws java.rmi.RemoteException
/*  1479:      */   {
/*  1480: 1412 */     for (int i = 1;; i++)
/*  1481:      */     {
/*  1482: 1414 */       _Request local_Request = null;
/*  1483: 1415 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1484: 1416 */       boolean bool1 = false;
/*  1485: 1417 */       LocalFrame localLocalFrame = null;
/*  1486: 1418 */       boolean bool2 = false;
/*  1487:      */       try
/*  1488:      */       {
/*  1489: 1421 */         local_Request = __request("addWireTransfer");
/*  1490: 1422 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1491: 1423 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1492: 1424 */         localLocalStack.setArgsOnLocal(bool1);
/*  1493: 1426 */         if (bool1)
/*  1494:      */         {
/*  1495: 1428 */           localLocalFrame = new LocalFrame(1);
/*  1496: 1429 */           localLocalStack.push(localLocalFrame);
/*  1497:      */         }
/*  1498: 1431 */         if (!bool1)
/*  1499:      */         {
/*  1500: 1433 */           localObject4 = local_Request.getOutputStream();
/*  1501: 1434 */           local_Request.write_value(paramWireInfo, WireInfo.class);
/*  1502:      */         }
/*  1503:      */         else
/*  1504:      */         {
/*  1505: 1438 */           localObject4 = local_Request.getOutputStream();
/*  1506: 1439 */           localLocalFrame.add(paramWireInfo);
/*  1507:      */         }
/*  1508: 1441 */         local_Request.invoke();
/*  1509:      */         Object localObject1;
/*  1510: 1442 */         if (bool1)
/*  1511:      */         {
/*  1512: 1444 */           localObject4 = null;
/*  1513: 1445 */           localObject5 = localLocalFrame.getResult();
/*  1514: 1446 */           if (localObject5 != null) {
/*  1515: 1448 */             localObject4 = (WireInfo)ObjectVal.clone(localObject5);
/*  1516:      */           }
/*  1517: 1450 */           return localObject4;
/*  1518:      */         }
/*  1519: 1452 */         Object localObject4 = local_Request.getInputStream();
/*  1520:      */         
/*  1521: 1454 */         Object localObject5 = (WireInfo)local_Request.read_value(WireInfo.class);
/*  1522: 1455 */         return localObject5;
/*  1523:      */       }
/*  1524:      */       catch (TRANSIENT localTRANSIENT)
/*  1525:      */       {
/*  1526: 1459 */         if (i == 10) {
/*  1527: 1461 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1528:      */         }
/*  1529:      */       }
/*  1530:      */       catch (UserException localUserException)
/*  1531:      */       {
/*  1532: 1466 */         local_Request.isRMI();
/*  1533:      */         
/*  1534:      */ 
/*  1535: 1469 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1536:      */       }
/*  1537:      */       catch (SystemException localSystemException)
/*  1538:      */       {
/*  1539: 1473 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1540:      */       }
/*  1541:      */       finally
/*  1542:      */       {
/*  1543: 1477 */         if (local_Request != null) {
/*  1544: 1479 */           local_Request.close();
/*  1545:      */         }
/*  1546: 1481 */         if (bool1) {
/*  1547: 1482 */           localLocalStack.pop(localLocalFrame);
/*  1548:      */         }
/*  1549: 1483 */         localLocalStack.setArgsOnLocal(bool2);
/*  1550:      */       }
/*  1551:      */     }
/*  1552:      */   }
/*  1553:      */   
/*  1554:      */   public WireInfo modWireTransfer(WireInfo paramWireInfo)
/*  1555:      */     throws java.rmi.RemoteException
/*  1556:      */   {
/*  1557: 1492 */     for (int i = 1;; i++)
/*  1558:      */     {
/*  1559: 1494 */       _Request local_Request = null;
/*  1560: 1495 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1561: 1496 */       boolean bool1 = false;
/*  1562: 1497 */       LocalFrame localLocalFrame = null;
/*  1563: 1498 */       boolean bool2 = false;
/*  1564:      */       try
/*  1565:      */       {
/*  1566: 1501 */         local_Request = __request("modWireTransfer");
/*  1567: 1502 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1568: 1503 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1569: 1504 */         localLocalStack.setArgsOnLocal(bool1);
/*  1570: 1506 */         if (bool1)
/*  1571:      */         {
/*  1572: 1508 */           localLocalFrame = new LocalFrame(1);
/*  1573: 1509 */           localLocalStack.push(localLocalFrame);
/*  1574:      */         }
/*  1575: 1511 */         if (!bool1)
/*  1576:      */         {
/*  1577: 1513 */           localObject4 = local_Request.getOutputStream();
/*  1578: 1514 */           local_Request.write_value(paramWireInfo, WireInfo.class);
/*  1579:      */         }
/*  1580:      */         else
/*  1581:      */         {
/*  1582: 1518 */           localObject4 = local_Request.getOutputStream();
/*  1583: 1519 */           localLocalFrame.add(paramWireInfo);
/*  1584:      */         }
/*  1585: 1521 */         local_Request.invoke();
/*  1586:      */         Object localObject1;
/*  1587: 1522 */         if (bool1)
/*  1588:      */         {
/*  1589: 1524 */           localObject4 = null;
/*  1590: 1525 */           localObject5 = localLocalFrame.getResult();
/*  1591: 1526 */           if (localObject5 != null) {
/*  1592: 1528 */             localObject4 = (WireInfo)ObjectVal.clone(localObject5);
/*  1593:      */           }
/*  1594: 1530 */           return localObject4;
/*  1595:      */         }
/*  1596: 1532 */         Object localObject4 = local_Request.getInputStream();
/*  1597:      */         
/*  1598: 1534 */         Object localObject5 = (WireInfo)local_Request.read_value(WireInfo.class);
/*  1599: 1535 */         return localObject5;
/*  1600:      */       }
/*  1601:      */       catch (TRANSIENT localTRANSIENT)
/*  1602:      */       {
/*  1603: 1539 */         if (i == 10) {
/*  1604: 1541 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1605:      */         }
/*  1606:      */       }
/*  1607:      */       catch (UserException localUserException)
/*  1608:      */       {
/*  1609: 1546 */         local_Request.isRMI();
/*  1610:      */         
/*  1611:      */ 
/*  1612: 1549 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1613:      */       }
/*  1614:      */       catch (SystemException localSystemException)
/*  1615:      */       {
/*  1616: 1553 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1617:      */       }
/*  1618:      */       finally
/*  1619:      */       {
/*  1620: 1557 */         if (local_Request != null) {
/*  1621: 1559 */           local_Request.close();
/*  1622:      */         }
/*  1623: 1561 */         if (bool1) {
/*  1624: 1562 */           localLocalStack.pop(localLocalFrame);
/*  1625:      */         }
/*  1626: 1563 */         localLocalStack.setArgsOnLocal(bool2);
/*  1627:      */       }
/*  1628:      */     }
/*  1629:      */   }
/*  1630:      */   
/*  1631:      */   public WireInfo cancWireTransfer(WireInfo paramWireInfo)
/*  1632:      */     throws java.rmi.RemoteException
/*  1633:      */   {
/*  1634: 1572 */     for (int i = 1;; i++)
/*  1635:      */     {
/*  1636: 1574 */       _Request local_Request = null;
/*  1637: 1575 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1638: 1576 */       boolean bool1 = false;
/*  1639: 1577 */       LocalFrame localLocalFrame = null;
/*  1640: 1578 */       boolean bool2 = false;
/*  1641:      */       try
/*  1642:      */       {
/*  1643: 1581 */         local_Request = __request("cancWireTransfer");
/*  1644: 1582 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1645: 1583 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1646: 1584 */         localLocalStack.setArgsOnLocal(bool1);
/*  1647: 1586 */         if (bool1)
/*  1648:      */         {
/*  1649: 1588 */           localLocalFrame = new LocalFrame(1);
/*  1650: 1589 */           localLocalStack.push(localLocalFrame);
/*  1651:      */         }
/*  1652: 1591 */         if (!bool1)
/*  1653:      */         {
/*  1654: 1593 */           localObject4 = local_Request.getOutputStream();
/*  1655: 1594 */           local_Request.write_value(paramWireInfo, WireInfo.class);
/*  1656:      */         }
/*  1657:      */         else
/*  1658:      */         {
/*  1659: 1598 */           localObject4 = local_Request.getOutputStream();
/*  1660: 1599 */           localLocalFrame.add(paramWireInfo);
/*  1661:      */         }
/*  1662: 1601 */         local_Request.invoke();
/*  1663:      */         Object localObject1;
/*  1664: 1602 */         if (bool1)
/*  1665:      */         {
/*  1666: 1604 */           localObject4 = null;
/*  1667: 1605 */           localObject5 = localLocalFrame.getResult();
/*  1668: 1606 */           if (localObject5 != null) {
/*  1669: 1608 */             localObject4 = (WireInfo)ObjectVal.clone(localObject5);
/*  1670:      */           }
/*  1671: 1610 */           return localObject4;
/*  1672:      */         }
/*  1673: 1612 */         Object localObject4 = local_Request.getInputStream();
/*  1674:      */         
/*  1675: 1614 */         Object localObject5 = (WireInfo)local_Request.read_value(WireInfo.class);
/*  1676: 1615 */         return localObject5;
/*  1677:      */       }
/*  1678:      */       catch (TRANSIENT localTRANSIENT)
/*  1679:      */       {
/*  1680: 1619 */         if (i == 10) {
/*  1681: 1621 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1682:      */         }
/*  1683:      */       }
/*  1684:      */       catch (UserException localUserException)
/*  1685:      */       {
/*  1686: 1626 */         local_Request.isRMI();
/*  1687:      */         
/*  1688:      */ 
/*  1689: 1629 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1690:      */       }
/*  1691:      */       catch (SystemException localSystemException)
/*  1692:      */       {
/*  1693: 1633 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1694:      */       }
/*  1695:      */       finally
/*  1696:      */       {
/*  1697: 1637 */         if (local_Request != null) {
/*  1698: 1639 */           local_Request.close();
/*  1699:      */         }
/*  1700: 1641 */         if (bool1) {
/*  1701: 1642 */           localLocalStack.pop(localLocalFrame);
/*  1702:      */         }
/*  1703: 1643 */         localLocalStack.setArgsOnLocal(bool2);
/*  1704:      */       }
/*  1705:      */     }
/*  1706:      */   }
/*  1707:      */   
/*  1708:      */   public WireInfo getWireTransferById(String paramString)
/*  1709:      */     throws java.rmi.RemoteException
/*  1710:      */   {
/*  1711: 1652 */     for (int i = 1;; i++)
/*  1712:      */     {
/*  1713: 1654 */       _Request local_Request = null;
/*  1714: 1655 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1715: 1656 */       boolean bool1 = false;
/*  1716: 1657 */       LocalFrame localLocalFrame = null;
/*  1717: 1658 */       boolean bool2 = false;
/*  1718:      */       try
/*  1719:      */       {
/*  1720: 1661 */         local_Request = __request("getWireTransferById");
/*  1721: 1662 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1722: 1663 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1723: 1664 */         localLocalStack.setArgsOnLocal(bool1);
/*  1724: 1666 */         if (bool1)
/*  1725:      */         {
/*  1726: 1668 */           localLocalFrame = new LocalFrame(1);
/*  1727: 1669 */           localLocalStack.push(localLocalFrame);
/*  1728:      */         }
/*  1729: 1671 */         if (!bool1)
/*  1730:      */         {
/*  1731: 1673 */           localObject4 = local_Request.getOutputStream();
/*  1732: 1674 */           local_Request.write_string(paramString);
/*  1733:      */         }
/*  1734:      */         else
/*  1735:      */         {
/*  1736: 1678 */           localObject4 = local_Request.getOutputStream();
/*  1737: 1679 */           localLocalFrame.add(paramString);
/*  1738:      */         }
/*  1739: 1681 */         local_Request.invoke();
/*  1740:      */         Object localObject1;
/*  1741: 1682 */         if (bool1)
/*  1742:      */         {
/*  1743: 1684 */           localObject4 = null;
/*  1744: 1685 */           localObject5 = localLocalFrame.getResult();
/*  1745: 1686 */           if (localObject5 != null) {
/*  1746: 1688 */             localObject4 = (WireInfo)ObjectVal.clone(localObject5);
/*  1747:      */           }
/*  1748: 1690 */           return localObject4;
/*  1749:      */         }
/*  1750: 1692 */         Object localObject4 = local_Request.getInputStream();
/*  1751:      */         
/*  1752: 1694 */         Object localObject5 = (WireInfo)local_Request.read_value(WireInfo.class);
/*  1753: 1695 */         return localObject5;
/*  1754:      */       }
/*  1755:      */       catch (TRANSIENT localTRANSIENT)
/*  1756:      */       {
/*  1757: 1699 */         if (i == 10) {
/*  1758: 1701 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1759:      */         }
/*  1760:      */       }
/*  1761:      */       catch (UserException localUserException)
/*  1762:      */       {
/*  1763: 1706 */         local_Request.isRMI();
/*  1764:      */         
/*  1765:      */ 
/*  1766: 1709 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1767:      */       }
/*  1768:      */       catch (SystemException localSystemException)
/*  1769:      */       {
/*  1770: 1713 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1771:      */       }
/*  1772:      */       finally
/*  1773:      */       {
/*  1774: 1717 */         if (local_Request != null) {
/*  1775: 1719 */           local_Request.close();
/*  1776:      */         }
/*  1777: 1721 */         if (bool1) {
/*  1778: 1722 */           localLocalStack.pop(localLocalFrame);
/*  1779:      */         }
/*  1780: 1723 */         localLocalStack.setArgsOnLocal(bool2);
/*  1781:      */       }
/*  1782:      */     }
/*  1783:      */   }
/*  1784:      */   
/*  1785:      */   public WireInfo getWireTransfer(String paramString)
/*  1786:      */     throws java.rmi.RemoteException
/*  1787:      */   {
/*  1788: 1732 */     for (int i = 1;; i++)
/*  1789:      */     {
/*  1790: 1734 */       _Request local_Request = null;
/*  1791: 1735 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1792: 1736 */       boolean bool1 = false;
/*  1793: 1737 */       LocalFrame localLocalFrame = null;
/*  1794: 1738 */       boolean bool2 = false;
/*  1795:      */       try
/*  1796:      */       {
/*  1797: 1741 */         local_Request = __request("getWireTransfer");
/*  1798: 1742 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1799: 1743 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1800: 1744 */         localLocalStack.setArgsOnLocal(bool1);
/*  1801: 1746 */         if (bool1)
/*  1802:      */         {
/*  1803: 1748 */           localLocalFrame = new LocalFrame(1);
/*  1804: 1749 */           localLocalStack.push(localLocalFrame);
/*  1805:      */         }
/*  1806: 1751 */         if (!bool1)
/*  1807:      */         {
/*  1808: 1753 */           localObject4 = local_Request.getOutputStream();
/*  1809: 1754 */           local_Request.write_string(paramString);
/*  1810:      */         }
/*  1811:      */         else
/*  1812:      */         {
/*  1813: 1758 */           localObject4 = local_Request.getOutputStream();
/*  1814: 1759 */           localLocalFrame.add(paramString);
/*  1815:      */         }
/*  1816: 1761 */         local_Request.invoke();
/*  1817:      */         Object localObject1;
/*  1818: 1762 */         if (bool1)
/*  1819:      */         {
/*  1820: 1764 */           localObject4 = null;
/*  1821: 1765 */           localObject5 = localLocalFrame.getResult();
/*  1822: 1766 */           if (localObject5 != null) {
/*  1823: 1768 */             localObject4 = (WireInfo)ObjectVal.clone(localObject5);
/*  1824:      */           }
/*  1825: 1770 */           return localObject4;
/*  1826:      */         }
/*  1827: 1772 */         Object localObject4 = local_Request.getInputStream();
/*  1828:      */         
/*  1829: 1774 */         Object localObject5 = (WireInfo)local_Request.read_value(WireInfo.class);
/*  1830: 1775 */         return localObject5;
/*  1831:      */       }
/*  1832:      */       catch (TRANSIENT localTRANSIENT)
/*  1833:      */       {
/*  1834: 1779 */         if (i == 10) {
/*  1835: 1781 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1836:      */         }
/*  1837:      */       }
/*  1838:      */       catch (UserException localUserException)
/*  1839:      */       {
/*  1840: 1786 */         local_Request.isRMI();
/*  1841:      */         
/*  1842:      */ 
/*  1843: 1789 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1844:      */       }
/*  1845:      */       catch (SystemException localSystemException)
/*  1846:      */       {
/*  1847: 1793 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1848:      */       }
/*  1849:      */       finally
/*  1850:      */       {
/*  1851: 1797 */         if (local_Request != null) {
/*  1852: 1799 */           local_Request.close();
/*  1853:      */         }
/*  1854: 1801 */         if (bool1) {
/*  1855: 1802 */           localLocalStack.pop(localLocalFrame);
/*  1856:      */         }
/*  1857: 1803 */         localLocalStack.setArgsOnLocal(bool2);
/*  1858:      */       }
/*  1859:      */     }
/*  1860:      */   }
/*  1861:      */   
/*  1862:      */   public WireInfo[] getWireTransfers(String[] paramArrayOfString)
/*  1863:      */     throws java.rmi.RemoteException
/*  1864:      */   {
/*  1865: 1812 */     for (int i = 1;; i++)
/*  1866:      */     {
/*  1867: 1814 */       _Request local_Request = null;
/*  1868: 1815 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1869: 1816 */       boolean bool1 = false;
/*  1870: 1817 */       LocalFrame localLocalFrame = null;
/*  1871: 1818 */       boolean bool2 = false;
/*  1872:      */       try
/*  1873:      */       {
/*  1874: 1821 */         local_Request = __request("getWireTransfers");
/*  1875: 1822 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1876: 1823 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1877: 1824 */         localLocalStack.setArgsOnLocal(bool1);
/*  1878: 1826 */         if (bool1)
/*  1879:      */         {
/*  1880: 1828 */           localLocalFrame = new LocalFrame(1);
/*  1881: 1829 */           localLocalStack.push(localLocalFrame);
/*  1882:      */         }
/*  1883: 1831 */         if (!bool1)
/*  1884:      */         {
/*  1885: 1833 */           localObject4 = local_Request.getOutputStream();
/*  1886: 1834 */           if (local_Request.isRMI()) {
/*  1887: 1834 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  1888:      */           } else {
/*  1889: 1834 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/*  1890:      */           }
/*  1891:      */         }
/*  1892:      */         else
/*  1893:      */         {
/*  1894: 1838 */           localObject4 = local_Request.getOutputStream();
/*  1895: 1839 */           localLocalFrame.add(paramArrayOfString);
/*  1896:      */         }
/*  1897: 1841 */         local_Request.invoke();
/*  1898:      */         Object localObject5;
/*  1899:      */         Object localObject1;
/*  1900: 1842 */         if (bool1)
/*  1901:      */         {
/*  1902: 1844 */           localObject4 = null;
/*  1903: 1845 */           localObject5 = localLocalFrame.getResult();
/*  1904: 1846 */           if (localObject5 != null) {
/*  1905: 1848 */             localObject4 = (WireInfo[])ObjectVal.clone(localObject5);
/*  1906:      */           }
/*  1907: 1850 */           return localObject4;
/*  1908:      */         }
/*  1909: 1852 */         Object localObject4 = local_Request.getInputStream();
/*  1910: 1854 */         if (local_Request.isRMI()) {
/*  1911: 1854 */           localObject5 = (WireInfo[])local_Request.read_value(new WireInfo[0].getClass());
/*  1912:      */         } else {
/*  1913: 1854 */           localObject5 = WireInfoSeqHelper.read((InputStream)localObject4);
/*  1914:      */         }
/*  1915: 1855 */         return localObject5;
/*  1916:      */       }
/*  1917:      */       catch (TRANSIENT localTRANSIENT)
/*  1918:      */       {
/*  1919: 1859 */         if (i == 10) {
/*  1920: 1861 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  1921:      */         }
/*  1922:      */       }
/*  1923:      */       catch (UserException localUserException)
/*  1924:      */       {
/*  1925: 1866 */         local_Request.isRMI();
/*  1926:      */         
/*  1927:      */ 
/*  1928: 1869 */         throw new java.rmi.RemoteException(localUserException.type);
/*  1929:      */       }
/*  1930:      */       catch (SystemException localSystemException)
/*  1931:      */       {
/*  1932: 1873 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  1933:      */       }
/*  1934:      */       finally
/*  1935:      */       {
/*  1936: 1877 */         if (local_Request != null) {
/*  1937: 1879 */           local_Request.close();
/*  1938:      */         }
/*  1939: 1881 */         if (bool1) {
/*  1940: 1882 */           localLocalStack.pop(localLocalFrame);
/*  1941:      */         }
/*  1942: 1883 */         localLocalStack.setArgsOnLocal(bool2);
/*  1943:      */       }
/*  1944:      */     }
/*  1945:      */   }
/*  1946:      */   
/*  1947:      */   public BPWHist getDuplicateWires(WireInfo paramWireInfo)
/*  1948:      */     throws java.rmi.RemoteException
/*  1949:      */   {
/*  1950: 1892 */     for (int i = 1;; i++)
/*  1951:      */     {
/*  1952: 1894 */       _Request local_Request = null;
/*  1953: 1895 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  1954: 1896 */       boolean bool1 = false;
/*  1955: 1897 */       LocalFrame localLocalFrame = null;
/*  1956: 1898 */       boolean bool2 = false;
/*  1957:      */       try
/*  1958:      */       {
/*  1959: 1901 */         local_Request = __request("getDuplicateWires");
/*  1960: 1902 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  1961: 1903 */         bool2 = localLocalStack.isArgsOnLocal();
/*  1962: 1904 */         localLocalStack.setArgsOnLocal(bool1);
/*  1963: 1906 */         if (bool1)
/*  1964:      */         {
/*  1965: 1908 */           localLocalFrame = new LocalFrame(1);
/*  1966: 1909 */           localLocalStack.push(localLocalFrame);
/*  1967:      */         }
/*  1968: 1911 */         if (!bool1)
/*  1969:      */         {
/*  1970: 1913 */           localObject4 = local_Request.getOutputStream();
/*  1971: 1914 */           local_Request.write_value(paramWireInfo, WireInfo.class);
/*  1972:      */         }
/*  1973:      */         else
/*  1974:      */         {
/*  1975: 1918 */           localObject4 = local_Request.getOutputStream();
/*  1976: 1919 */           localLocalFrame.add(paramWireInfo);
/*  1977:      */         }
/*  1978: 1921 */         local_Request.invoke();
/*  1979:      */         Object localObject5;
/*  1980:      */         Object localObject1;
/*  1981: 1922 */         if (bool1)
/*  1982:      */         {
/*  1983: 1924 */           localObject4 = null;
/*  1984: 1925 */           localObject5 = localLocalFrame.getResult();
/*  1985: 1926 */           if (localObject5 != null) {
/*  1986: 1928 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/*  1987:      */           }
/*  1988: 1930 */           return localObject4;
/*  1989:      */         }
/*  1990: 1932 */         Object localObject4 = local_Request.getInputStream();
/*  1991: 1934 */         if (local_Request.isRMI()) {
/*  1992: 1934 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/*  1993:      */         } else {
/*  1994: 1934 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/*  1995:      */         }
/*  1996: 1935 */         return localObject5;
/*  1997:      */       }
/*  1998:      */       catch (TRANSIENT localTRANSIENT)
/*  1999:      */       {
/*  2000: 1939 */         if (i == 10) {
/*  2001: 1941 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2002:      */         }
/*  2003:      */       }
/*  2004:      */       catch (UserException localUserException)
/*  2005:      */       {
/*  2006: 1946 */         local_Request.isRMI();
/*  2007:      */         
/*  2008:      */ 
/*  2009: 1949 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2010:      */       }
/*  2011:      */       catch (SystemException localSystemException)
/*  2012:      */       {
/*  2013: 1953 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2014:      */       }
/*  2015:      */       finally
/*  2016:      */       {
/*  2017: 1957 */         if (local_Request != null) {
/*  2018: 1959 */           local_Request.close();
/*  2019:      */         }
/*  2020: 1961 */         if (bool1) {
/*  2021: 1962 */           localLocalStack.pop(localLocalFrame);
/*  2022:      */         }
/*  2023: 1963 */         localLocalStack.setArgsOnLocal(bool2);
/*  2024:      */       }
/*  2025:      */     }
/*  2026:      */   }
/*  2027:      */   
/*  2028:      */   public WireInfo[] getBatchWires(String paramString)
/*  2029:      */     throws java.rmi.RemoteException
/*  2030:      */   {
/*  2031: 1972 */     for (int i = 1;; i++)
/*  2032:      */     {
/*  2033: 1974 */       _Request local_Request = null;
/*  2034: 1975 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2035: 1976 */       boolean bool1 = false;
/*  2036: 1977 */       LocalFrame localLocalFrame = null;
/*  2037: 1978 */       boolean bool2 = false;
/*  2038:      */       try
/*  2039:      */       {
/*  2040: 1981 */         local_Request = __request("getBatchWires");
/*  2041: 1982 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2042: 1983 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2043: 1984 */         localLocalStack.setArgsOnLocal(bool1);
/*  2044: 1986 */         if (bool1)
/*  2045:      */         {
/*  2046: 1988 */           localLocalFrame = new LocalFrame(1);
/*  2047: 1989 */           localLocalStack.push(localLocalFrame);
/*  2048:      */         }
/*  2049: 1991 */         if (!bool1)
/*  2050:      */         {
/*  2051: 1993 */           localObject4 = local_Request.getOutputStream();
/*  2052: 1994 */           local_Request.write_string(paramString);
/*  2053:      */         }
/*  2054:      */         else
/*  2055:      */         {
/*  2056: 1998 */           localObject4 = local_Request.getOutputStream();
/*  2057: 1999 */           localLocalFrame.add(paramString);
/*  2058:      */         }
/*  2059: 2001 */         local_Request.invoke();
/*  2060:      */         Object localObject5;
/*  2061:      */         Object localObject1;
/*  2062: 2002 */         if (bool1)
/*  2063:      */         {
/*  2064: 2004 */           localObject4 = null;
/*  2065: 2005 */           localObject5 = localLocalFrame.getResult();
/*  2066: 2006 */           if (localObject5 != null) {
/*  2067: 2008 */             localObject4 = (WireInfo[])ObjectVal.clone(localObject5);
/*  2068:      */           }
/*  2069: 2010 */           return localObject4;
/*  2070:      */         }
/*  2071: 2012 */         Object localObject4 = local_Request.getInputStream();
/*  2072: 2014 */         if (local_Request.isRMI()) {
/*  2073: 2014 */           localObject5 = (WireInfo[])local_Request.read_value(new WireInfo[0].getClass());
/*  2074:      */         } else {
/*  2075: 2014 */           localObject5 = WireInfoSeqHelper.read((InputStream)localObject4);
/*  2076:      */         }
/*  2077: 2015 */         return localObject5;
/*  2078:      */       }
/*  2079:      */       catch (TRANSIENT localTRANSIENT)
/*  2080:      */       {
/*  2081: 2019 */         if (i == 10) {
/*  2082: 2021 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2083:      */         }
/*  2084:      */       }
/*  2085:      */       catch (UserException localUserException)
/*  2086:      */       {
/*  2087: 2026 */         local_Request.isRMI();
/*  2088:      */         
/*  2089:      */ 
/*  2090: 2029 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2091:      */       }
/*  2092:      */       catch (SystemException localSystemException)
/*  2093:      */       {
/*  2094: 2033 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2095:      */       }
/*  2096:      */       finally
/*  2097:      */       {
/*  2098: 2037 */         if (local_Request != null) {
/*  2099: 2039 */           local_Request.close();
/*  2100:      */         }
/*  2101: 2041 */         if (bool1) {
/*  2102: 2042 */           localLocalStack.pop(localLocalFrame);
/*  2103:      */         }
/*  2104: 2043 */         localLocalStack.setArgsOnLocal(bool2);
/*  2105:      */       }
/*  2106:      */     }
/*  2107:      */   }
/*  2108:      */   
/*  2109:      */   public BPWHist getWireHistory(BPWHist paramBPWHist)
/*  2110:      */     throws java.rmi.RemoteException
/*  2111:      */   {
/*  2112: 2052 */     for (int i = 1;; i++)
/*  2113:      */     {
/*  2114: 2054 */       _Request local_Request = null;
/*  2115: 2055 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2116: 2056 */       boolean bool1 = false;
/*  2117: 2057 */       LocalFrame localLocalFrame = null;
/*  2118: 2058 */       boolean bool2 = false;
/*  2119:      */       try
/*  2120:      */       {
/*  2121: 2061 */         local_Request = __request("getWireHistory");
/*  2122: 2062 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2123: 2063 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2124: 2064 */         localLocalStack.setArgsOnLocal(bool1);
/*  2125: 2066 */         if (bool1)
/*  2126:      */         {
/*  2127: 2068 */           localLocalFrame = new LocalFrame(1);
/*  2128: 2069 */           localLocalStack.push(localLocalFrame);
/*  2129:      */         }
/*  2130: 2071 */         if (!bool1)
/*  2131:      */         {
/*  2132: 2073 */           localObject4 = local_Request.getOutputStream();
/*  2133: 2074 */           if (local_Request.isRMI()) {
/*  2134: 2074 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/*  2135:      */           } else {
/*  2136: 2074 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/*  2137:      */           }
/*  2138:      */         }
/*  2139:      */         else
/*  2140:      */         {
/*  2141: 2078 */           localObject4 = local_Request.getOutputStream();
/*  2142: 2079 */           localLocalFrame.add(paramBPWHist);
/*  2143:      */         }
/*  2144: 2081 */         local_Request.invoke();
/*  2145:      */         Object localObject5;
/*  2146:      */         Object localObject1;
/*  2147: 2082 */         if (bool1)
/*  2148:      */         {
/*  2149: 2084 */           localObject4 = null;
/*  2150: 2085 */           localObject5 = localLocalFrame.getResult();
/*  2151: 2086 */           if (localObject5 != null) {
/*  2152: 2088 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/*  2153:      */           }
/*  2154: 2090 */           return localObject4;
/*  2155:      */         }
/*  2156: 2092 */         Object localObject4 = local_Request.getInputStream();
/*  2157: 2094 */         if (local_Request.isRMI()) {
/*  2158: 2094 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/*  2159:      */         } else {
/*  2160: 2094 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/*  2161:      */         }
/*  2162: 2095 */         return localObject5;
/*  2163:      */       }
/*  2164:      */       catch (TRANSIENT localTRANSIENT)
/*  2165:      */       {
/*  2166: 2099 */         if (i == 10) {
/*  2167: 2101 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2168:      */         }
/*  2169:      */       }
/*  2170:      */       catch (UserException localUserException)
/*  2171:      */       {
/*  2172: 2106 */         local_Request.isRMI();
/*  2173:      */         
/*  2174:      */ 
/*  2175: 2109 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2176:      */       }
/*  2177:      */       catch (SystemException localSystemException)
/*  2178:      */       {
/*  2179: 2113 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2180:      */       }
/*  2181:      */       finally
/*  2182:      */       {
/*  2183: 2117 */         if (local_Request != null) {
/*  2184: 2119 */           local_Request.close();
/*  2185:      */         }
/*  2186: 2121 */         if (bool1) {
/*  2187: 2122 */           localLocalStack.pop(localLocalFrame);
/*  2188:      */         }
/*  2189: 2123 */         localLocalStack.setArgsOnLocal(bool2);
/*  2190:      */       }
/*  2191:      */     }
/*  2192:      */   }
/*  2193:      */   
/*  2194:      */   public BPWHist getWireHistoryByCustomer(BPWHist paramBPWHist)
/*  2195:      */     throws java.rmi.RemoteException
/*  2196:      */   {
/*  2197: 2132 */     for (int i = 1;; i++)
/*  2198:      */     {
/*  2199: 2134 */       _Request local_Request = null;
/*  2200: 2135 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2201: 2136 */       boolean bool1 = false;
/*  2202: 2137 */       LocalFrame localLocalFrame = null;
/*  2203: 2138 */       boolean bool2 = false;
/*  2204:      */       try
/*  2205:      */       {
/*  2206: 2141 */         local_Request = __request("getWireHistoryByCustomer");
/*  2207: 2142 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2208: 2143 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2209: 2144 */         localLocalStack.setArgsOnLocal(bool1);
/*  2210: 2146 */         if (bool1)
/*  2211:      */         {
/*  2212: 2148 */           localLocalFrame = new LocalFrame(1);
/*  2213: 2149 */           localLocalStack.push(localLocalFrame);
/*  2214:      */         }
/*  2215: 2151 */         if (!bool1)
/*  2216:      */         {
/*  2217: 2153 */           localObject4 = local_Request.getOutputStream();
/*  2218: 2154 */           if (local_Request.isRMI()) {
/*  2219: 2154 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/*  2220:      */           } else {
/*  2221: 2154 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/*  2222:      */           }
/*  2223:      */         }
/*  2224:      */         else
/*  2225:      */         {
/*  2226: 2158 */           localObject4 = local_Request.getOutputStream();
/*  2227: 2159 */           localLocalFrame.add(paramBPWHist);
/*  2228:      */         }
/*  2229: 2161 */         local_Request.invoke();
/*  2230:      */         Object localObject5;
/*  2231:      */         Object localObject1;
/*  2232: 2162 */         if (bool1)
/*  2233:      */         {
/*  2234: 2164 */           localObject4 = null;
/*  2235: 2165 */           localObject5 = localLocalFrame.getResult();
/*  2236: 2166 */           if (localObject5 != null) {
/*  2237: 2168 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/*  2238:      */           }
/*  2239: 2170 */           return localObject4;
/*  2240:      */         }
/*  2241: 2172 */         Object localObject4 = local_Request.getInputStream();
/*  2242: 2174 */         if (local_Request.isRMI()) {
/*  2243: 2174 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/*  2244:      */         } else {
/*  2245: 2174 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/*  2246:      */         }
/*  2247: 2175 */         return localObject5;
/*  2248:      */       }
/*  2249:      */       catch (TRANSIENT localTRANSIENT)
/*  2250:      */       {
/*  2251: 2179 */         if (i == 10) {
/*  2252: 2181 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2253:      */         }
/*  2254:      */       }
/*  2255:      */       catch (UserException localUserException)
/*  2256:      */       {
/*  2257: 2186 */         local_Request.isRMI();
/*  2258:      */         
/*  2259:      */ 
/*  2260: 2189 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2261:      */       }
/*  2262:      */       catch (SystemException localSystemException)
/*  2263:      */       {
/*  2264: 2193 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2265:      */       }
/*  2266:      */       finally
/*  2267:      */       {
/*  2268: 2197 */         if (local_Request != null) {
/*  2269: 2199 */           local_Request.close();
/*  2270:      */         }
/*  2271: 2201 */         if (bool1) {
/*  2272: 2202 */           localLocalStack.pop(localLocalFrame);
/*  2273:      */         }
/*  2274: 2203 */         localLocalStack.setArgsOnLocal(bool2);
/*  2275:      */       }
/*  2276:      */     }
/*  2277:      */   }
/*  2278:      */   
/*  2279:      */   public BPWHist getCombinedWireHistory(BPWHist paramBPWHist)
/*  2280:      */     throws java.rmi.RemoteException
/*  2281:      */   {
/*  2282: 2212 */     for (int i = 1;; i++)
/*  2283:      */     {
/*  2284: 2214 */       _Request local_Request = null;
/*  2285: 2215 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2286: 2216 */       boolean bool1 = false;
/*  2287: 2217 */       LocalFrame localLocalFrame = null;
/*  2288: 2218 */       boolean bool2 = false;
/*  2289:      */       try
/*  2290:      */       {
/*  2291: 2221 */         local_Request = __request("getCombinedWireHistory");
/*  2292: 2222 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2293: 2223 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2294: 2224 */         localLocalStack.setArgsOnLocal(bool1);
/*  2295: 2226 */         if (bool1)
/*  2296:      */         {
/*  2297: 2228 */           localLocalFrame = new LocalFrame(1);
/*  2298: 2229 */           localLocalStack.push(localLocalFrame);
/*  2299:      */         }
/*  2300: 2231 */         if (!bool1)
/*  2301:      */         {
/*  2302: 2233 */           localObject4 = local_Request.getOutputStream();
/*  2303: 2234 */           if (local_Request.isRMI()) {
/*  2304: 2234 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/*  2305:      */           } else {
/*  2306: 2234 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/*  2307:      */           }
/*  2308:      */         }
/*  2309:      */         else
/*  2310:      */         {
/*  2311: 2238 */           localObject4 = local_Request.getOutputStream();
/*  2312: 2239 */           localLocalFrame.add(paramBPWHist);
/*  2313:      */         }
/*  2314: 2241 */         local_Request.invoke();
/*  2315:      */         Object localObject5;
/*  2316:      */         Object localObject1;
/*  2317: 2242 */         if (bool1)
/*  2318:      */         {
/*  2319: 2244 */           localObject4 = null;
/*  2320: 2245 */           localObject5 = localLocalFrame.getResult();
/*  2321: 2246 */           if (localObject5 != null) {
/*  2322: 2248 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/*  2323:      */           }
/*  2324: 2250 */           return localObject4;
/*  2325:      */         }
/*  2326: 2252 */         Object localObject4 = local_Request.getInputStream();
/*  2327: 2254 */         if (local_Request.isRMI()) {
/*  2328: 2254 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/*  2329:      */         } else {
/*  2330: 2254 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/*  2331:      */         }
/*  2332: 2255 */         return localObject5;
/*  2333:      */       }
/*  2334:      */       catch (TRANSIENT localTRANSIENT)
/*  2335:      */       {
/*  2336: 2259 */         if (i == 10) {
/*  2337: 2261 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2338:      */         }
/*  2339:      */       }
/*  2340:      */       catch (UserException localUserException)
/*  2341:      */       {
/*  2342: 2266 */         local_Request.isRMI();
/*  2343:      */         
/*  2344:      */ 
/*  2345: 2269 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2346:      */       }
/*  2347:      */       catch (SystemException localSystemException)
/*  2348:      */       {
/*  2349: 2273 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2350:      */       }
/*  2351:      */       finally
/*  2352:      */       {
/*  2353: 2277 */         if (local_Request != null) {
/*  2354: 2279 */           local_Request.close();
/*  2355:      */         }
/*  2356: 2281 */         if (bool1) {
/*  2357: 2282 */           localLocalStack.pop(localLocalFrame);
/*  2358:      */         }
/*  2359: 2283 */         localLocalStack.setArgsOnLocal(bool2);
/*  2360:      */       }
/*  2361:      */     }
/*  2362:      */   }
/*  2363:      */   
/*  2364:      */   public WireInfo[] getAuditWireTransfer(String paramString)
/*  2365:      */     throws java.rmi.RemoteException
/*  2366:      */   {
/*  2367: 2292 */     for (int i = 1;; i++)
/*  2368:      */     {
/*  2369: 2294 */       _Request local_Request = null;
/*  2370: 2295 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2371: 2296 */       boolean bool1 = false;
/*  2372: 2297 */       LocalFrame localLocalFrame = null;
/*  2373: 2298 */       boolean bool2 = false;
/*  2374:      */       try
/*  2375:      */       {
/*  2376: 2301 */         local_Request = __request("getAuditWireTransfer");
/*  2377: 2302 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2378: 2303 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2379: 2304 */         localLocalStack.setArgsOnLocal(bool1);
/*  2380: 2306 */         if (bool1)
/*  2381:      */         {
/*  2382: 2308 */           localLocalFrame = new LocalFrame(1);
/*  2383: 2309 */           localLocalStack.push(localLocalFrame);
/*  2384:      */         }
/*  2385: 2311 */         if (!bool1)
/*  2386:      */         {
/*  2387: 2313 */           localObject4 = local_Request.getOutputStream();
/*  2388: 2314 */           local_Request.write_string(paramString);
/*  2389:      */         }
/*  2390:      */         else
/*  2391:      */         {
/*  2392: 2318 */           localObject4 = local_Request.getOutputStream();
/*  2393: 2319 */           localLocalFrame.add(paramString);
/*  2394:      */         }
/*  2395: 2321 */         local_Request.invoke();
/*  2396:      */         Object localObject5;
/*  2397:      */         Object localObject1;
/*  2398: 2322 */         if (bool1)
/*  2399:      */         {
/*  2400: 2324 */           localObject4 = null;
/*  2401: 2325 */           localObject5 = localLocalFrame.getResult();
/*  2402: 2326 */           if (localObject5 != null) {
/*  2403: 2328 */             localObject4 = (WireInfo[])ObjectVal.clone(localObject5);
/*  2404:      */           }
/*  2405: 2330 */           return localObject4;
/*  2406:      */         }
/*  2407: 2332 */         Object localObject4 = local_Request.getInputStream();
/*  2408: 2334 */         if (local_Request.isRMI()) {
/*  2409: 2334 */           localObject5 = (WireInfo[])local_Request.read_value(new WireInfo[0].getClass());
/*  2410:      */         } else {
/*  2411: 2334 */           localObject5 = WireInfoSeqHelper.read((InputStream)localObject4);
/*  2412:      */         }
/*  2413: 2335 */         return localObject5;
/*  2414:      */       }
/*  2415:      */       catch (TRANSIENT localTRANSIENT)
/*  2416:      */       {
/*  2417: 2339 */         if (i == 10) {
/*  2418: 2341 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2419:      */         }
/*  2420:      */       }
/*  2421:      */       catch (UserException localUserException)
/*  2422:      */       {
/*  2423: 2346 */         local_Request.isRMI();
/*  2424:      */         
/*  2425:      */ 
/*  2426: 2349 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2427:      */       }
/*  2428:      */       catch (SystemException localSystemException)
/*  2429:      */       {
/*  2430: 2353 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2431:      */       }
/*  2432:      */       finally
/*  2433:      */       {
/*  2434: 2357 */         if (local_Request != null) {
/*  2435: 2359 */           local_Request.close();
/*  2436:      */         }
/*  2437: 2361 */         if (bool1) {
/*  2438: 2362 */           localLocalStack.pop(localLocalFrame);
/*  2439:      */         }
/*  2440: 2363 */         localLocalStack.setArgsOnLocal(bool2);
/*  2441:      */       }
/*  2442:      */     }
/*  2443:      */   }
/*  2444:      */   
/*  2445:      */   public WireInfo[] getAuditWireTransferByExtId(String paramString)
/*  2446:      */     throws java.rmi.RemoteException
/*  2447:      */   {
/*  2448: 2372 */     for (int i = 1;; i++)
/*  2449:      */     {
/*  2450: 2374 */       _Request local_Request = null;
/*  2451: 2375 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2452: 2376 */       boolean bool1 = false;
/*  2453: 2377 */       LocalFrame localLocalFrame = null;
/*  2454: 2378 */       boolean bool2 = false;
/*  2455:      */       try
/*  2456:      */       {
/*  2457: 2381 */         local_Request = __request("getAuditWireTransferByExtId");
/*  2458: 2382 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2459: 2383 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2460: 2384 */         localLocalStack.setArgsOnLocal(bool1);
/*  2461: 2386 */         if (bool1)
/*  2462:      */         {
/*  2463: 2388 */           localLocalFrame = new LocalFrame(1);
/*  2464: 2389 */           localLocalStack.push(localLocalFrame);
/*  2465:      */         }
/*  2466: 2391 */         if (!bool1)
/*  2467:      */         {
/*  2468: 2393 */           localObject4 = local_Request.getOutputStream();
/*  2469: 2394 */           local_Request.write_string(paramString);
/*  2470:      */         }
/*  2471:      */         else
/*  2472:      */         {
/*  2473: 2398 */           localObject4 = local_Request.getOutputStream();
/*  2474: 2399 */           localLocalFrame.add(paramString);
/*  2475:      */         }
/*  2476: 2401 */         local_Request.invoke();
/*  2477:      */         Object localObject5;
/*  2478:      */         Object localObject1;
/*  2479: 2402 */         if (bool1)
/*  2480:      */         {
/*  2481: 2404 */           localObject4 = null;
/*  2482: 2405 */           localObject5 = localLocalFrame.getResult();
/*  2483: 2406 */           if (localObject5 != null) {
/*  2484: 2408 */             localObject4 = (WireInfo[])ObjectVal.clone(localObject5);
/*  2485:      */           }
/*  2486: 2410 */           return localObject4;
/*  2487:      */         }
/*  2488: 2412 */         Object localObject4 = local_Request.getInputStream();
/*  2489: 2414 */         if (local_Request.isRMI()) {
/*  2490: 2414 */           localObject5 = (WireInfo[])local_Request.read_value(new WireInfo[0].getClass());
/*  2491:      */         } else {
/*  2492: 2414 */           localObject5 = WireInfoSeqHelper.read((InputStream)localObject4);
/*  2493:      */         }
/*  2494: 2415 */         return localObject5;
/*  2495:      */       }
/*  2496:      */       catch (TRANSIENT localTRANSIENT)
/*  2497:      */       {
/*  2498: 2419 */         if (i == 10) {
/*  2499: 2421 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2500:      */         }
/*  2501:      */       }
/*  2502:      */       catch (UserException localUserException)
/*  2503:      */       {
/*  2504: 2426 */         local_Request.isRMI();
/*  2505:      */         
/*  2506:      */ 
/*  2507: 2429 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2508:      */       }
/*  2509:      */       catch (SystemException localSystemException)
/*  2510:      */       {
/*  2511: 2433 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2512:      */       }
/*  2513:      */       finally
/*  2514:      */       {
/*  2515: 2437 */         if (local_Request != null) {
/*  2516: 2439 */           local_Request.close();
/*  2517:      */         }
/*  2518: 2441 */         if (bool1) {
/*  2519: 2442 */           localLocalStack.pop(localLocalFrame);
/*  2520:      */         }
/*  2521: 2443 */         localLocalStack.setArgsOnLocal(bool2);
/*  2522:      */       }
/*  2523:      */     }
/*  2524:      */   }
/*  2525:      */   
/*  2526:      */   public WireReleaseInfo getWireReleaseCount(WireReleaseInfo paramWireReleaseInfo)
/*  2527:      */     throws java.rmi.RemoteException
/*  2528:      */   {
/*  2529: 2452 */     for (int i = 1;; i++)
/*  2530:      */     {
/*  2531: 2454 */       _Request local_Request = null;
/*  2532: 2455 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2533: 2456 */       boolean bool1 = false;
/*  2534: 2457 */       LocalFrame localLocalFrame = null;
/*  2535: 2458 */       boolean bool2 = false;
/*  2536:      */       try
/*  2537:      */       {
/*  2538: 2461 */         local_Request = __request("getWireReleaseCount");
/*  2539: 2462 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2540: 2463 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2541: 2464 */         localLocalStack.setArgsOnLocal(bool1);
/*  2542: 2466 */         if (bool1)
/*  2543:      */         {
/*  2544: 2468 */           localLocalFrame = new LocalFrame(1);
/*  2545: 2469 */           localLocalStack.push(localLocalFrame);
/*  2546:      */         }
/*  2547: 2471 */         if (!bool1)
/*  2548:      */         {
/*  2549: 2473 */           localObject4 = local_Request.getOutputStream();
/*  2550: 2474 */           local_Request.write_value(paramWireReleaseInfo, WireReleaseInfo.class);
/*  2551:      */         }
/*  2552:      */         else
/*  2553:      */         {
/*  2554: 2478 */           localObject4 = local_Request.getOutputStream();
/*  2555: 2479 */           localLocalFrame.add(paramWireReleaseInfo);
/*  2556:      */         }
/*  2557: 2481 */         local_Request.invoke();
/*  2558:      */         Object localObject1;
/*  2559: 2482 */         if (bool1)
/*  2560:      */         {
/*  2561: 2484 */           localObject4 = null;
/*  2562: 2485 */           localObject5 = localLocalFrame.getResult();
/*  2563: 2486 */           if (localObject5 != null) {
/*  2564: 2488 */             localObject4 = (WireReleaseInfo)ObjectVal.clone(localObject5);
/*  2565:      */           }
/*  2566: 2490 */           return localObject4;
/*  2567:      */         }
/*  2568: 2492 */         Object localObject4 = local_Request.getInputStream();
/*  2569:      */         
/*  2570: 2494 */         Object localObject5 = (WireReleaseInfo)local_Request.read_value(WireReleaseInfo.class);
/*  2571: 2495 */         return localObject5;
/*  2572:      */       }
/*  2573:      */       catch (TRANSIENT localTRANSIENT)
/*  2574:      */       {
/*  2575: 2499 */         if (i == 10) {
/*  2576: 2501 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2577:      */         }
/*  2578:      */       }
/*  2579:      */       catch (UserException localUserException)
/*  2580:      */       {
/*  2581: 2506 */         local_Request.isRMI();
/*  2582:      */         
/*  2583:      */ 
/*  2584: 2509 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2585:      */       }
/*  2586:      */       catch (SystemException localSystemException)
/*  2587:      */       {
/*  2588: 2513 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2589:      */       }
/*  2590:      */       finally
/*  2591:      */       {
/*  2592: 2517 */         if (local_Request != null) {
/*  2593: 2519 */           local_Request.close();
/*  2594:      */         }
/*  2595: 2521 */         if (bool1) {
/*  2596: 2522 */           localLocalStack.pop(localLocalFrame);
/*  2597:      */         }
/*  2598: 2523 */         localLocalStack.setArgsOnLocal(bool2);
/*  2599:      */       }
/*  2600:      */     }
/*  2601:      */   }
/*  2602:      */   
/*  2603:      */   public WireInfo[] addWireTransfers(WireInfo[] paramArrayOfWireInfo)
/*  2604:      */     throws java.rmi.RemoteException
/*  2605:      */   {
/*  2606: 2532 */     for (int i = 1;; i++)
/*  2607:      */     {
/*  2608: 2534 */       _Request local_Request = null;
/*  2609: 2535 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2610: 2536 */       boolean bool1 = false;
/*  2611: 2537 */       LocalFrame localLocalFrame = null;
/*  2612: 2538 */       boolean bool2 = false;
/*  2613:      */       try
/*  2614:      */       {
/*  2615: 2541 */         local_Request = __request("addWireTransfers");
/*  2616: 2542 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2617: 2543 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2618: 2544 */         localLocalStack.setArgsOnLocal(bool1);
/*  2619: 2546 */         if (bool1)
/*  2620:      */         {
/*  2621: 2548 */           localLocalFrame = new LocalFrame(1);
/*  2622: 2549 */           localLocalStack.push(localLocalFrame);
/*  2623:      */         }
/*  2624: 2551 */         if (!bool1)
/*  2625:      */         {
/*  2626: 2553 */           localObject4 = local_Request.getOutputStream();
/*  2627: 2554 */           if (local_Request.isRMI()) {
/*  2628: 2554 */             local_Request.write_value(paramArrayOfWireInfo, new WireInfo[0].getClass());
/*  2629:      */           } else {
/*  2630: 2554 */             WireInfoSeqHelper.write((OutputStream)localObject4, paramArrayOfWireInfo);
/*  2631:      */           }
/*  2632:      */         }
/*  2633:      */         else
/*  2634:      */         {
/*  2635: 2558 */           localObject4 = local_Request.getOutputStream();
/*  2636: 2559 */           localLocalFrame.add(paramArrayOfWireInfo);
/*  2637:      */         }
/*  2638: 2561 */         local_Request.invoke();
/*  2639:      */         Object localObject5;
/*  2640:      */         Object localObject1;
/*  2641: 2562 */         if (bool1)
/*  2642:      */         {
/*  2643: 2564 */           localObject4 = null;
/*  2644: 2565 */           localObject5 = localLocalFrame.getResult();
/*  2645: 2566 */           if (localObject5 != null) {
/*  2646: 2568 */             localObject4 = (WireInfo[])ObjectVal.clone(localObject5);
/*  2647:      */           }
/*  2648: 2570 */           return localObject4;
/*  2649:      */         }
/*  2650: 2572 */         Object localObject4 = local_Request.getInputStream();
/*  2651: 2574 */         if (local_Request.isRMI()) {
/*  2652: 2574 */           localObject5 = (WireInfo[])local_Request.read_value(new WireInfo[0].getClass());
/*  2653:      */         } else {
/*  2654: 2574 */           localObject5 = WireInfoSeqHelper.read((InputStream)localObject4);
/*  2655:      */         }
/*  2656: 2575 */         return localObject5;
/*  2657:      */       }
/*  2658:      */       catch (TRANSIENT localTRANSIENT)
/*  2659:      */       {
/*  2660: 2579 */         if (i == 10) {
/*  2661: 2581 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2662:      */         }
/*  2663:      */       }
/*  2664:      */       catch (UserException localUserException)
/*  2665:      */       {
/*  2666: 2586 */         local_Request.isRMI();
/*  2667:      */         
/*  2668:      */ 
/*  2669: 2589 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2670:      */       }
/*  2671:      */       catch (SystemException localSystemException)
/*  2672:      */       {
/*  2673: 2593 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2674:      */       }
/*  2675:      */       finally
/*  2676:      */       {
/*  2677: 2597 */         if (local_Request != null) {
/*  2678: 2599 */           local_Request.close();
/*  2679:      */         }
/*  2680: 2601 */         if (bool1) {
/*  2681: 2602 */           localLocalStack.pop(localLocalFrame);
/*  2682:      */         }
/*  2683: 2603 */         localLocalStack.setArgsOnLocal(bool2);
/*  2684:      */       }
/*  2685:      */     }
/*  2686:      */   }
/*  2687:      */   
/*  2688:      */   public WireInfo[] releaseWireTransfer(WireInfo[] paramArrayOfWireInfo)
/*  2689:      */     throws java.rmi.RemoteException
/*  2690:      */   {
/*  2691: 2612 */     for (int i = 1;; i++)
/*  2692:      */     {
/*  2693: 2614 */       _Request local_Request = null;
/*  2694: 2615 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2695: 2616 */       boolean bool1 = false;
/*  2696: 2617 */       LocalFrame localLocalFrame = null;
/*  2697: 2618 */       boolean bool2 = false;
/*  2698:      */       try
/*  2699:      */       {
/*  2700: 2621 */         local_Request = __request("releaseWireTransfer");
/*  2701: 2622 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2702: 2623 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2703: 2624 */         localLocalStack.setArgsOnLocal(bool1);
/*  2704: 2626 */         if (bool1)
/*  2705:      */         {
/*  2706: 2628 */           localLocalFrame = new LocalFrame(1);
/*  2707: 2629 */           localLocalStack.push(localLocalFrame);
/*  2708:      */         }
/*  2709: 2631 */         if (!bool1)
/*  2710:      */         {
/*  2711: 2633 */           localObject4 = local_Request.getOutputStream();
/*  2712: 2634 */           if (local_Request.isRMI()) {
/*  2713: 2634 */             local_Request.write_value(paramArrayOfWireInfo, new WireInfo[0].getClass());
/*  2714:      */           } else {
/*  2715: 2634 */             WireInfoSeqHelper.write((OutputStream)localObject4, paramArrayOfWireInfo);
/*  2716:      */           }
/*  2717:      */         }
/*  2718:      */         else
/*  2719:      */         {
/*  2720: 2638 */           localObject4 = local_Request.getOutputStream();
/*  2721: 2639 */           localLocalFrame.add(paramArrayOfWireInfo);
/*  2722:      */         }
/*  2723: 2641 */         local_Request.invoke();
/*  2724:      */         Object localObject5;
/*  2725:      */         Object localObject1;
/*  2726: 2642 */         if (bool1)
/*  2727:      */         {
/*  2728: 2644 */           localObject4 = null;
/*  2729: 2645 */           localObject5 = localLocalFrame.getResult();
/*  2730: 2646 */           if (localObject5 != null) {
/*  2731: 2648 */             localObject4 = (WireInfo[])ObjectVal.clone(localObject5);
/*  2732:      */           }
/*  2733: 2650 */           return localObject4;
/*  2734:      */         }
/*  2735: 2652 */         Object localObject4 = local_Request.getInputStream();
/*  2736: 2654 */         if (local_Request.isRMI()) {
/*  2737: 2654 */           localObject5 = (WireInfo[])local_Request.read_value(new WireInfo[0].getClass());
/*  2738:      */         } else {
/*  2739: 2654 */           localObject5 = WireInfoSeqHelper.read((InputStream)localObject4);
/*  2740:      */         }
/*  2741: 2655 */         return localObject5;
/*  2742:      */       }
/*  2743:      */       catch (TRANSIENT localTRANSIENT)
/*  2744:      */       {
/*  2745: 2659 */         if (i == 10) {
/*  2746: 2661 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2747:      */         }
/*  2748:      */       }
/*  2749:      */       catch (UserException localUserException)
/*  2750:      */       {
/*  2751: 2666 */         local_Request.isRMI();
/*  2752:      */         
/*  2753:      */ 
/*  2754: 2669 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2755:      */       }
/*  2756:      */       catch (SystemException localSystemException)
/*  2757:      */       {
/*  2758: 2673 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2759:      */       }
/*  2760:      */       finally
/*  2761:      */       {
/*  2762: 2677 */         if (local_Request != null) {
/*  2763: 2679 */           local_Request.close();
/*  2764:      */         }
/*  2765: 2681 */         if (bool1) {
/*  2766: 2682 */           localLocalStack.pop(localLocalFrame);
/*  2767:      */         }
/*  2768: 2683 */         localLocalStack.setArgsOnLocal(bool2);
/*  2769:      */       }
/*  2770:      */     }
/*  2771:      */   }
/*  2772:      */   
/*  2773:      */   public RecWireInfo addRecWireTransfer(RecWireInfo paramRecWireInfo)
/*  2774:      */     throws java.rmi.RemoteException
/*  2775:      */   {
/*  2776: 2692 */     for (int i = 1;; i++)
/*  2777:      */     {
/*  2778: 2694 */       _Request local_Request = null;
/*  2779: 2695 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2780: 2696 */       boolean bool1 = false;
/*  2781: 2697 */       LocalFrame localLocalFrame = null;
/*  2782: 2698 */       boolean bool2 = false;
/*  2783:      */       try
/*  2784:      */       {
/*  2785: 2701 */         local_Request = __request("addRecWireTransfer");
/*  2786: 2702 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2787: 2703 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2788: 2704 */         localLocalStack.setArgsOnLocal(bool1);
/*  2789: 2706 */         if (bool1)
/*  2790:      */         {
/*  2791: 2708 */           localLocalFrame = new LocalFrame(1);
/*  2792: 2709 */           localLocalStack.push(localLocalFrame);
/*  2793:      */         }
/*  2794: 2711 */         if (!bool1)
/*  2795:      */         {
/*  2796: 2713 */           localObject4 = local_Request.getOutputStream();
/*  2797: 2714 */           local_Request.write_value(paramRecWireInfo, RecWireInfo.class);
/*  2798:      */         }
/*  2799:      */         else
/*  2800:      */         {
/*  2801: 2718 */           localObject4 = local_Request.getOutputStream();
/*  2802: 2719 */           localLocalFrame.add(paramRecWireInfo);
/*  2803:      */         }
/*  2804: 2721 */         local_Request.invoke();
/*  2805:      */         Object localObject1;
/*  2806: 2722 */         if (bool1)
/*  2807:      */         {
/*  2808: 2724 */           localObject4 = null;
/*  2809: 2725 */           localObject5 = localLocalFrame.getResult();
/*  2810: 2726 */           if (localObject5 != null) {
/*  2811: 2728 */             localObject4 = (RecWireInfo)ObjectVal.clone(localObject5);
/*  2812:      */           }
/*  2813: 2730 */           return localObject4;
/*  2814:      */         }
/*  2815: 2732 */         Object localObject4 = local_Request.getInputStream();
/*  2816:      */         
/*  2817: 2734 */         Object localObject5 = (RecWireInfo)local_Request.read_value(RecWireInfo.class);
/*  2818: 2735 */         return localObject5;
/*  2819:      */       }
/*  2820:      */       catch (TRANSIENT localTRANSIENT)
/*  2821:      */       {
/*  2822: 2739 */         if (i == 10) {
/*  2823: 2741 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2824:      */         }
/*  2825:      */       }
/*  2826:      */       catch (UserException localUserException)
/*  2827:      */       {
/*  2828: 2746 */         local_Request.isRMI();
/*  2829:      */         
/*  2830:      */ 
/*  2831: 2749 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2832:      */       }
/*  2833:      */       catch (SystemException localSystemException)
/*  2834:      */       {
/*  2835: 2753 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2836:      */       }
/*  2837:      */       finally
/*  2838:      */       {
/*  2839: 2757 */         if (local_Request != null) {
/*  2840: 2759 */           local_Request.close();
/*  2841:      */         }
/*  2842: 2761 */         if (bool1) {
/*  2843: 2762 */           localLocalStack.pop(localLocalFrame);
/*  2844:      */         }
/*  2845: 2763 */         localLocalStack.setArgsOnLocal(bool2);
/*  2846:      */       }
/*  2847:      */     }
/*  2848:      */   }
/*  2849:      */   
/*  2850:      */   public RecWireInfo modRecWireTransfer(RecWireInfo paramRecWireInfo)
/*  2851:      */     throws java.rmi.RemoteException
/*  2852:      */   {
/*  2853: 2772 */     for (int i = 1;; i++)
/*  2854:      */     {
/*  2855: 2774 */       _Request local_Request = null;
/*  2856: 2775 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2857: 2776 */       boolean bool1 = false;
/*  2858: 2777 */       LocalFrame localLocalFrame = null;
/*  2859: 2778 */       boolean bool2 = false;
/*  2860:      */       try
/*  2861:      */       {
/*  2862: 2781 */         local_Request = __request("modRecWireTransfer");
/*  2863: 2782 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2864: 2783 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2865: 2784 */         localLocalStack.setArgsOnLocal(bool1);
/*  2866: 2786 */         if (bool1)
/*  2867:      */         {
/*  2868: 2788 */           localLocalFrame = new LocalFrame(1);
/*  2869: 2789 */           localLocalStack.push(localLocalFrame);
/*  2870:      */         }
/*  2871: 2791 */         if (!bool1)
/*  2872:      */         {
/*  2873: 2793 */           localObject4 = local_Request.getOutputStream();
/*  2874: 2794 */           local_Request.write_value(paramRecWireInfo, RecWireInfo.class);
/*  2875:      */         }
/*  2876:      */         else
/*  2877:      */         {
/*  2878: 2798 */           localObject4 = local_Request.getOutputStream();
/*  2879: 2799 */           localLocalFrame.add(paramRecWireInfo);
/*  2880:      */         }
/*  2881: 2801 */         local_Request.invoke();
/*  2882:      */         Object localObject1;
/*  2883: 2802 */         if (bool1)
/*  2884:      */         {
/*  2885: 2804 */           localObject4 = null;
/*  2886: 2805 */           localObject5 = localLocalFrame.getResult();
/*  2887: 2806 */           if (localObject5 != null) {
/*  2888: 2808 */             localObject4 = (RecWireInfo)ObjectVal.clone(localObject5);
/*  2889:      */           }
/*  2890: 2810 */           return localObject4;
/*  2891:      */         }
/*  2892: 2812 */         Object localObject4 = local_Request.getInputStream();
/*  2893:      */         
/*  2894: 2814 */         Object localObject5 = (RecWireInfo)local_Request.read_value(RecWireInfo.class);
/*  2895: 2815 */         return localObject5;
/*  2896:      */       }
/*  2897:      */       catch (TRANSIENT localTRANSIENT)
/*  2898:      */       {
/*  2899: 2819 */         if (i == 10) {
/*  2900: 2821 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2901:      */         }
/*  2902:      */       }
/*  2903:      */       catch (UserException localUserException)
/*  2904:      */       {
/*  2905: 2826 */         local_Request.isRMI();
/*  2906:      */         
/*  2907:      */ 
/*  2908: 2829 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2909:      */       }
/*  2910:      */       catch (SystemException localSystemException)
/*  2911:      */       {
/*  2912: 2833 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2913:      */       }
/*  2914:      */       finally
/*  2915:      */       {
/*  2916: 2837 */         if (local_Request != null) {
/*  2917: 2839 */           local_Request.close();
/*  2918:      */         }
/*  2919: 2841 */         if (bool1) {
/*  2920: 2842 */           localLocalStack.pop(localLocalFrame);
/*  2921:      */         }
/*  2922: 2843 */         localLocalStack.setArgsOnLocal(bool2);
/*  2923:      */       }
/*  2924:      */     }
/*  2925:      */   }
/*  2926:      */   
/*  2927:      */   public RecWireInfo cancRecWireTransfer(RecWireInfo paramRecWireInfo)
/*  2928:      */     throws java.rmi.RemoteException
/*  2929:      */   {
/*  2930: 2852 */     for (int i = 1;; i++)
/*  2931:      */     {
/*  2932: 2854 */       _Request local_Request = null;
/*  2933: 2855 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  2934: 2856 */       boolean bool1 = false;
/*  2935: 2857 */       LocalFrame localLocalFrame = null;
/*  2936: 2858 */       boolean bool2 = false;
/*  2937:      */       try
/*  2938:      */       {
/*  2939: 2861 */         local_Request = __request("cancRecWireTransfer");
/*  2940: 2862 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  2941: 2863 */         bool2 = localLocalStack.isArgsOnLocal();
/*  2942: 2864 */         localLocalStack.setArgsOnLocal(bool1);
/*  2943: 2866 */         if (bool1)
/*  2944:      */         {
/*  2945: 2868 */           localLocalFrame = new LocalFrame(1);
/*  2946: 2869 */           localLocalStack.push(localLocalFrame);
/*  2947:      */         }
/*  2948: 2871 */         if (!bool1)
/*  2949:      */         {
/*  2950: 2873 */           localObject4 = local_Request.getOutputStream();
/*  2951: 2874 */           local_Request.write_value(paramRecWireInfo, RecWireInfo.class);
/*  2952:      */         }
/*  2953:      */         else
/*  2954:      */         {
/*  2955: 2878 */           localObject4 = local_Request.getOutputStream();
/*  2956: 2879 */           localLocalFrame.add(paramRecWireInfo);
/*  2957:      */         }
/*  2958: 2881 */         local_Request.invoke();
/*  2959:      */         Object localObject1;
/*  2960: 2882 */         if (bool1)
/*  2961:      */         {
/*  2962: 2884 */           localObject4 = null;
/*  2963: 2885 */           localObject5 = localLocalFrame.getResult();
/*  2964: 2886 */           if (localObject5 != null) {
/*  2965: 2888 */             localObject4 = (RecWireInfo)ObjectVal.clone(localObject5);
/*  2966:      */           }
/*  2967: 2890 */           return localObject4;
/*  2968:      */         }
/*  2969: 2892 */         Object localObject4 = local_Request.getInputStream();
/*  2970:      */         
/*  2971: 2894 */         Object localObject5 = (RecWireInfo)local_Request.read_value(RecWireInfo.class);
/*  2972: 2895 */         return localObject5;
/*  2973:      */       }
/*  2974:      */       catch (TRANSIENT localTRANSIENT)
/*  2975:      */       {
/*  2976: 2899 */         if (i == 10) {
/*  2977: 2901 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  2978:      */         }
/*  2979:      */       }
/*  2980:      */       catch (UserException localUserException)
/*  2981:      */       {
/*  2982: 2906 */         local_Request.isRMI();
/*  2983:      */         
/*  2984:      */ 
/*  2985: 2909 */         throw new java.rmi.RemoteException(localUserException.type);
/*  2986:      */       }
/*  2987:      */       catch (SystemException localSystemException)
/*  2988:      */       {
/*  2989: 2913 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  2990:      */       }
/*  2991:      */       finally
/*  2992:      */       {
/*  2993: 2917 */         if (local_Request != null) {
/*  2994: 2919 */           local_Request.close();
/*  2995:      */         }
/*  2996: 2921 */         if (bool1) {
/*  2997: 2922 */           localLocalStack.pop(localLocalFrame);
/*  2998:      */         }
/*  2999: 2923 */         localLocalStack.setArgsOnLocal(bool2);
/*  3000:      */       }
/*  3001:      */     }
/*  3002:      */   }
/*  3003:      */   
/*  3004:      */   public RecWireInfo getRecWireTransferById(String paramString, boolean paramBoolean)
/*  3005:      */     throws java.rmi.RemoteException
/*  3006:      */   {
/*  3007: 2933 */     for (int i = 1;; i++)
/*  3008:      */     {
/*  3009: 2935 */       _Request local_Request = null;
/*  3010: 2936 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3011: 2937 */       boolean bool1 = false;
/*  3012: 2938 */       LocalFrame localLocalFrame = null;
/*  3013: 2939 */       boolean bool2 = false;
/*  3014:      */       try
/*  3015:      */       {
/*  3016: 2942 */         local_Request = __request("getRecWireTransferById__string__boolean", "getRecWireTransferById__CORBA_WStringValue__boolean");
/*  3017: 2943 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3018: 2944 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3019: 2945 */         localLocalStack.setArgsOnLocal(bool1);
/*  3020: 2947 */         if (bool1)
/*  3021:      */         {
/*  3022: 2949 */           localLocalFrame = new LocalFrame(2);
/*  3023: 2950 */           localLocalStack.push(localLocalFrame);
/*  3024:      */         }
/*  3025: 2952 */         if (!bool1)
/*  3026:      */         {
/*  3027: 2954 */           localObject4 = local_Request.getOutputStream();
/*  3028: 2955 */           local_Request.write_string(paramString);
/*  3029: 2956 */           ((OutputStream)localObject4).write_boolean(paramBoolean);
/*  3030:      */         }
/*  3031:      */         else
/*  3032:      */         {
/*  3033: 2960 */           localObject4 = local_Request.getOutputStream();
/*  3034: 2961 */           localLocalFrame.add(paramString);
/*  3035: 2962 */           localLocalFrame.add(paramBoolean);
/*  3036:      */         }
/*  3037: 2964 */         local_Request.invoke();
/*  3038:      */         Object localObject1;
/*  3039: 2965 */         if (bool1)
/*  3040:      */         {
/*  3041: 2967 */           localObject4 = null;
/*  3042: 2968 */           localObject5 = localLocalFrame.getResult();
/*  3043: 2969 */           if (localObject5 != null) {
/*  3044: 2971 */             localObject4 = (RecWireInfo)ObjectVal.clone(localObject5);
/*  3045:      */           }
/*  3046: 2973 */           return localObject4;
/*  3047:      */         }
/*  3048: 2975 */         Object localObject4 = local_Request.getInputStream();
/*  3049:      */         
/*  3050: 2977 */         Object localObject5 = (RecWireInfo)local_Request.read_value(RecWireInfo.class);
/*  3051: 2978 */         return localObject5;
/*  3052:      */       }
/*  3053:      */       catch (TRANSIENT localTRANSIENT)
/*  3054:      */       {
/*  3055: 2982 */         if (i == 10) {
/*  3056: 2984 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3057:      */         }
/*  3058:      */       }
/*  3059:      */       catch (UserException localUserException)
/*  3060:      */       {
/*  3061: 2989 */         local_Request.isRMI();
/*  3062:      */         
/*  3063:      */ 
/*  3064: 2992 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3065:      */       }
/*  3066:      */       catch (SystemException localSystemException)
/*  3067:      */       {
/*  3068: 2996 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3069:      */       }
/*  3070:      */       finally
/*  3071:      */       {
/*  3072: 3000 */         if (local_Request != null) {
/*  3073: 3002 */           local_Request.close();
/*  3074:      */         }
/*  3075: 3004 */         if (bool1) {
/*  3076: 3005 */           localLocalStack.pop(localLocalFrame);
/*  3077:      */         }
/*  3078: 3006 */         localLocalStack.setArgsOnLocal(bool2);
/*  3079:      */       }
/*  3080:      */     }
/*  3081:      */   }
/*  3082:      */   
/*  3083:      */   public RecWireInfo getRecWireTransferById(String paramString)
/*  3084:      */     throws java.rmi.RemoteException
/*  3085:      */   {
/*  3086: 3015 */     for (int i = 1;; i++)
/*  3087:      */     {
/*  3088: 3017 */       _Request local_Request = null;
/*  3089: 3018 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3090: 3019 */       boolean bool1 = false;
/*  3091: 3020 */       LocalFrame localLocalFrame = null;
/*  3092: 3021 */       boolean bool2 = false;
/*  3093:      */       try
/*  3094:      */       {
/*  3095: 3024 */         local_Request = __request("getRecWireTransferById__string", "getRecWireTransferById__CORBA_WStringValue");
/*  3096: 3025 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3097: 3026 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3098: 3027 */         localLocalStack.setArgsOnLocal(bool1);
/*  3099: 3029 */         if (bool1)
/*  3100:      */         {
/*  3101: 3031 */           localLocalFrame = new LocalFrame(1);
/*  3102: 3032 */           localLocalStack.push(localLocalFrame);
/*  3103:      */         }
/*  3104: 3034 */         if (!bool1)
/*  3105:      */         {
/*  3106: 3036 */           localObject4 = local_Request.getOutputStream();
/*  3107: 3037 */           local_Request.write_string(paramString);
/*  3108:      */         }
/*  3109:      */         else
/*  3110:      */         {
/*  3111: 3041 */           localObject4 = local_Request.getOutputStream();
/*  3112: 3042 */           localLocalFrame.add(paramString);
/*  3113:      */         }
/*  3114: 3044 */         local_Request.invoke();
/*  3115:      */         Object localObject1;
/*  3116: 3045 */         if (bool1)
/*  3117:      */         {
/*  3118: 3047 */           localObject4 = null;
/*  3119: 3048 */           localObject5 = localLocalFrame.getResult();
/*  3120: 3049 */           if (localObject5 != null) {
/*  3121: 3051 */             localObject4 = (RecWireInfo)ObjectVal.clone(localObject5);
/*  3122:      */           }
/*  3123: 3053 */           return localObject4;
/*  3124:      */         }
/*  3125: 3055 */         Object localObject4 = local_Request.getInputStream();
/*  3126:      */         
/*  3127: 3057 */         Object localObject5 = (RecWireInfo)local_Request.read_value(RecWireInfo.class);
/*  3128: 3058 */         return localObject5;
/*  3129:      */       }
/*  3130:      */       catch (TRANSIENT localTRANSIENT)
/*  3131:      */       {
/*  3132: 3062 */         if (i == 10) {
/*  3133: 3064 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3134:      */         }
/*  3135:      */       }
/*  3136:      */       catch (UserException localUserException)
/*  3137:      */       {
/*  3138: 3069 */         local_Request.isRMI();
/*  3139:      */         
/*  3140:      */ 
/*  3141: 3072 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3142:      */       }
/*  3143:      */       catch (SystemException localSystemException)
/*  3144:      */       {
/*  3145: 3076 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3146:      */       }
/*  3147:      */       finally
/*  3148:      */       {
/*  3149: 3080 */         if (local_Request != null) {
/*  3150: 3082 */           local_Request.close();
/*  3151:      */         }
/*  3152: 3084 */         if (bool1) {
/*  3153: 3085 */           localLocalStack.pop(localLocalFrame);
/*  3154:      */         }
/*  3155: 3086 */         localLocalStack.setArgsOnLocal(bool2);
/*  3156:      */       }
/*  3157:      */     }
/*  3158:      */   }
/*  3159:      */   
/*  3160:      */   public RecWireInfo getRecWireTransfer(String paramString)
/*  3161:      */     throws java.rmi.RemoteException
/*  3162:      */   {
/*  3163: 3095 */     for (int i = 1;; i++)
/*  3164:      */     {
/*  3165: 3097 */       _Request local_Request = null;
/*  3166: 3098 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3167: 3099 */       boolean bool1 = false;
/*  3168: 3100 */       LocalFrame localLocalFrame = null;
/*  3169: 3101 */       boolean bool2 = false;
/*  3170:      */       try
/*  3171:      */       {
/*  3172: 3104 */         local_Request = __request("getRecWireTransfer");
/*  3173: 3105 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3174: 3106 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3175: 3107 */         localLocalStack.setArgsOnLocal(bool1);
/*  3176: 3109 */         if (bool1)
/*  3177:      */         {
/*  3178: 3111 */           localLocalFrame = new LocalFrame(1);
/*  3179: 3112 */           localLocalStack.push(localLocalFrame);
/*  3180:      */         }
/*  3181: 3114 */         if (!bool1)
/*  3182:      */         {
/*  3183: 3116 */           localObject4 = local_Request.getOutputStream();
/*  3184: 3117 */           local_Request.write_string(paramString);
/*  3185:      */         }
/*  3186:      */         else
/*  3187:      */         {
/*  3188: 3121 */           localObject4 = local_Request.getOutputStream();
/*  3189: 3122 */           localLocalFrame.add(paramString);
/*  3190:      */         }
/*  3191: 3124 */         local_Request.invoke();
/*  3192:      */         Object localObject1;
/*  3193: 3125 */         if (bool1)
/*  3194:      */         {
/*  3195: 3127 */           localObject4 = null;
/*  3196: 3128 */           localObject5 = localLocalFrame.getResult();
/*  3197: 3129 */           if (localObject5 != null) {
/*  3198: 3131 */             localObject4 = (RecWireInfo)ObjectVal.clone(localObject5);
/*  3199:      */           }
/*  3200: 3133 */           return localObject4;
/*  3201:      */         }
/*  3202: 3135 */         Object localObject4 = local_Request.getInputStream();
/*  3203:      */         
/*  3204: 3137 */         Object localObject5 = (RecWireInfo)local_Request.read_value(RecWireInfo.class);
/*  3205: 3138 */         return localObject5;
/*  3206:      */       }
/*  3207:      */       catch (TRANSIENT localTRANSIENT)
/*  3208:      */       {
/*  3209: 3142 */         if (i == 10) {
/*  3210: 3144 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3211:      */         }
/*  3212:      */       }
/*  3213:      */       catch (UserException localUserException)
/*  3214:      */       {
/*  3215: 3149 */         local_Request.isRMI();
/*  3216:      */         
/*  3217:      */ 
/*  3218: 3152 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3219:      */       }
/*  3220:      */       catch (SystemException localSystemException)
/*  3221:      */       {
/*  3222: 3156 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3223:      */       }
/*  3224:      */       finally
/*  3225:      */       {
/*  3226: 3160 */         if (local_Request != null) {
/*  3227: 3162 */           local_Request.close();
/*  3228:      */         }
/*  3229: 3164 */         if (bool1) {
/*  3230: 3165 */           localLocalStack.pop(localLocalFrame);
/*  3231:      */         }
/*  3232: 3166 */         localLocalStack.setArgsOnLocal(bool2);
/*  3233:      */       }
/*  3234:      */     }
/*  3235:      */   }
/*  3236:      */   
/*  3237:      */   public RecWireInfo[] getRecWireTransfers(String[] paramArrayOfString)
/*  3238:      */     throws java.rmi.RemoteException
/*  3239:      */   {
/*  3240: 3175 */     for (int i = 1;; i++)
/*  3241:      */     {
/*  3242: 3177 */       _Request local_Request = null;
/*  3243: 3178 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3244: 3179 */       boolean bool1 = false;
/*  3245: 3180 */       LocalFrame localLocalFrame = null;
/*  3246: 3181 */       boolean bool2 = false;
/*  3247:      */       try
/*  3248:      */       {
/*  3249: 3184 */         local_Request = __request("getRecWireTransfers");
/*  3250: 3185 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3251: 3186 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3252: 3187 */         localLocalStack.setArgsOnLocal(bool1);
/*  3253: 3189 */         if (bool1)
/*  3254:      */         {
/*  3255: 3191 */           localLocalFrame = new LocalFrame(1);
/*  3256: 3192 */           localLocalStack.push(localLocalFrame);
/*  3257:      */         }
/*  3258: 3194 */         if (!bool1)
/*  3259:      */         {
/*  3260: 3196 */           localObject4 = local_Request.getOutputStream();
/*  3261: 3197 */           if (local_Request.isRMI()) {
/*  3262: 3197 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  3263:      */           } else {
/*  3264: 3197 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/*  3265:      */           }
/*  3266:      */         }
/*  3267:      */         else
/*  3268:      */         {
/*  3269: 3201 */           localObject4 = local_Request.getOutputStream();
/*  3270: 3202 */           localLocalFrame.add(paramArrayOfString);
/*  3271:      */         }
/*  3272: 3204 */         local_Request.invoke();
/*  3273:      */         Object localObject5;
/*  3274:      */         Object localObject1;
/*  3275: 3205 */         if (bool1)
/*  3276:      */         {
/*  3277: 3207 */           localObject4 = null;
/*  3278: 3208 */           localObject5 = localLocalFrame.getResult();
/*  3279: 3209 */           if (localObject5 != null) {
/*  3280: 3211 */             localObject4 = (RecWireInfo[])ObjectVal.clone(localObject5);
/*  3281:      */           }
/*  3282: 3213 */           return localObject4;
/*  3283:      */         }
/*  3284: 3215 */         Object localObject4 = local_Request.getInputStream();
/*  3285: 3217 */         if (local_Request.isRMI()) {
/*  3286: 3217 */           localObject5 = (RecWireInfo[])local_Request.read_value(new RecWireInfo[0].getClass());
/*  3287:      */         } else {
/*  3288: 3217 */           localObject5 = RecWireInfoSeqHelper.read((InputStream)localObject4);
/*  3289:      */         }
/*  3290: 3218 */         return localObject5;
/*  3291:      */       }
/*  3292:      */       catch (TRANSIENT localTRANSIENT)
/*  3293:      */       {
/*  3294: 3222 */         if (i == 10) {
/*  3295: 3224 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3296:      */         }
/*  3297:      */       }
/*  3298:      */       catch (UserException localUserException)
/*  3299:      */       {
/*  3300: 3229 */         local_Request.isRMI();
/*  3301:      */         
/*  3302:      */ 
/*  3303: 3232 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3304:      */       }
/*  3305:      */       catch (SystemException localSystemException)
/*  3306:      */       {
/*  3307: 3236 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3308:      */       }
/*  3309:      */       finally
/*  3310:      */       {
/*  3311: 3240 */         if (local_Request != null) {
/*  3312: 3242 */           local_Request.close();
/*  3313:      */         }
/*  3314: 3244 */         if (bool1) {
/*  3315: 3245 */           localLocalStack.pop(localLocalFrame);
/*  3316:      */         }
/*  3317: 3246 */         localLocalStack.setArgsOnLocal(bool2);
/*  3318:      */       }
/*  3319:      */     }
/*  3320:      */   }
/*  3321:      */   
/*  3322:      */   public BPWHist getRecWireHistory(BPWHist paramBPWHist)
/*  3323:      */     throws java.rmi.RemoteException
/*  3324:      */   {
/*  3325: 3255 */     for (int i = 1;; i++)
/*  3326:      */     {
/*  3327: 3257 */       _Request local_Request = null;
/*  3328: 3258 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3329: 3259 */       boolean bool1 = false;
/*  3330: 3260 */       LocalFrame localLocalFrame = null;
/*  3331: 3261 */       boolean bool2 = false;
/*  3332:      */       try
/*  3333:      */       {
/*  3334: 3264 */         local_Request = __request("getRecWireHistory");
/*  3335: 3265 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3336: 3266 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3337: 3267 */         localLocalStack.setArgsOnLocal(bool1);
/*  3338: 3269 */         if (bool1)
/*  3339:      */         {
/*  3340: 3271 */           localLocalFrame = new LocalFrame(1);
/*  3341: 3272 */           localLocalStack.push(localLocalFrame);
/*  3342:      */         }
/*  3343: 3274 */         if (!bool1)
/*  3344:      */         {
/*  3345: 3276 */           localObject4 = local_Request.getOutputStream();
/*  3346: 3277 */           if (local_Request.isRMI()) {
/*  3347: 3277 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/*  3348:      */           } else {
/*  3349: 3277 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/*  3350:      */           }
/*  3351:      */         }
/*  3352:      */         else
/*  3353:      */         {
/*  3354: 3281 */           localObject4 = local_Request.getOutputStream();
/*  3355: 3282 */           localLocalFrame.add(paramBPWHist);
/*  3356:      */         }
/*  3357: 3284 */         local_Request.invoke();
/*  3358:      */         Object localObject5;
/*  3359:      */         Object localObject1;
/*  3360: 3285 */         if (bool1)
/*  3361:      */         {
/*  3362: 3287 */           localObject4 = null;
/*  3363: 3288 */           localObject5 = localLocalFrame.getResult();
/*  3364: 3289 */           if (localObject5 != null) {
/*  3365: 3291 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/*  3366:      */           }
/*  3367: 3293 */           return localObject4;
/*  3368:      */         }
/*  3369: 3295 */         Object localObject4 = local_Request.getInputStream();
/*  3370: 3297 */         if (local_Request.isRMI()) {
/*  3371: 3297 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/*  3372:      */         } else {
/*  3373: 3297 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/*  3374:      */         }
/*  3375: 3298 */         return localObject5;
/*  3376:      */       }
/*  3377:      */       catch (TRANSIENT localTRANSIENT)
/*  3378:      */       {
/*  3379: 3302 */         if (i == 10) {
/*  3380: 3304 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3381:      */         }
/*  3382:      */       }
/*  3383:      */       catch (UserException localUserException)
/*  3384:      */       {
/*  3385: 3309 */         local_Request.isRMI();
/*  3386:      */         
/*  3387:      */ 
/*  3388: 3312 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3389:      */       }
/*  3390:      */       catch (SystemException localSystemException)
/*  3391:      */       {
/*  3392: 3316 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3393:      */       }
/*  3394:      */       finally
/*  3395:      */       {
/*  3396: 3320 */         if (local_Request != null) {
/*  3397: 3322 */           local_Request.close();
/*  3398:      */         }
/*  3399: 3324 */         if (bool1) {
/*  3400: 3325 */           localLocalStack.pop(localLocalFrame);
/*  3401:      */         }
/*  3402: 3326 */         localLocalStack.setArgsOnLocal(bool2);
/*  3403:      */       }
/*  3404:      */     }
/*  3405:      */   }
/*  3406:      */   
/*  3407:      */   public BPWHist getRecWireHistoryByCustomer(BPWHist paramBPWHist)
/*  3408:      */     throws java.rmi.RemoteException
/*  3409:      */   {
/*  3410: 3335 */     for (int i = 1;; i++)
/*  3411:      */     {
/*  3412: 3337 */       _Request local_Request = null;
/*  3413: 3338 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3414: 3339 */       boolean bool1 = false;
/*  3415: 3340 */       LocalFrame localLocalFrame = null;
/*  3416: 3341 */       boolean bool2 = false;
/*  3417:      */       try
/*  3418:      */       {
/*  3419: 3344 */         local_Request = __request("getRecWireHistoryByCustomer");
/*  3420: 3345 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3421: 3346 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3422: 3347 */         localLocalStack.setArgsOnLocal(bool1);
/*  3423: 3349 */         if (bool1)
/*  3424:      */         {
/*  3425: 3351 */           localLocalFrame = new LocalFrame(1);
/*  3426: 3352 */           localLocalStack.push(localLocalFrame);
/*  3427:      */         }
/*  3428: 3354 */         if (!bool1)
/*  3429:      */         {
/*  3430: 3356 */           localObject4 = local_Request.getOutputStream();
/*  3431: 3357 */           if (local_Request.isRMI()) {
/*  3432: 3357 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/*  3433:      */           } else {
/*  3434: 3357 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/*  3435:      */           }
/*  3436:      */         }
/*  3437:      */         else
/*  3438:      */         {
/*  3439: 3361 */           localObject4 = local_Request.getOutputStream();
/*  3440: 3362 */           localLocalFrame.add(paramBPWHist);
/*  3441:      */         }
/*  3442: 3364 */         local_Request.invoke();
/*  3443:      */         Object localObject5;
/*  3444:      */         Object localObject1;
/*  3445: 3365 */         if (bool1)
/*  3446:      */         {
/*  3447: 3367 */           localObject4 = null;
/*  3448: 3368 */           localObject5 = localLocalFrame.getResult();
/*  3449: 3369 */           if (localObject5 != null) {
/*  3450: 3371 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/*  3451:      */           }
/*  3452: 3373 */           return localObject4;
/*  3453:      */         }
/*  3454: 3375 */         Object localObject4 = local_Request.getInputStream();
/*  3455: 3377 */         if (local_Request.isRMI()) {
/*  3456: 3377 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/*  3457:      */         } else {
/*  3458: 3377 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/*  3459:      */         }
/*  3460: 3378 */         return localObject5;
/*  3461:      */       }
/*  3462:      */       catch (TRANSIENT localTRANSIENT)
/*  3463:      */       {
/*  3464: 3382 */         if (i == 10) {
/*  3465: 3384 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3466:      */         }
/*  3467:      */       }
/*  3468:      */       catch (UserException localUserException)
/*  3469:      */       {
/*  3470: 3389 */         local_Request.isRMI();
/*  3471:      */         
/*  3472:      */ 
/*  3473: 3392 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3474:      */       }
/*  3475:      */       catch (SystemException localSystemException)
/*  3476:      */       {
/*  3477: 3396 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3478:      */       }
/*  3479:      */       finally
/*  3480:      */       {
/*  3481: 3400 */         if (local_Request != null) {
/*  3482: 3402 */           local_Request.close();
/*  3483:      */         }
/*  3484: 3404 */         if (bool1) {
/*  3485: 3405 */           localLocalStack.pop(localLocalFrame);
/*  3486:      */         }
/*  3487: 3406 */         localLocalStack.setArgsOnLocal(bool2);
/*  3488:      */       }
/*  3489:      */     }
/*  3490:      */   }
/*  3491:      */   
/*  3492:      */   public RecWireInfo[] addRecWireTransfers(RecWireInfo[] paramArrayOfRecWireInfo)
/*  3493:      */     throws java.rmi.RemoteException
/*  3494:      */   {
/*  3495: 3415 */     for (int i = 1;; i++)
/*  3496:      */     {
/*  3497: 3417 */       _Request local_Request = null;
/*  3498: 3418 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3499: 3419 */       boolean bool1 = false;
/*  3500: 3420 */       LocalFrame localLocalFrame = null;
/*  3501: 3421 */       boolean bool2 = false;
/*  3502:      */       try
/*  3503:      */       {
/*  3504: 3424 */         local_Request = __request("addRecWireTransfers");
/*  3505: 3425 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3506: 3426 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3507: 3427 */         localLocalStack.setArgsOnLocal(bool1);
/*  3508: 3429 */         if (bool1)
/*  3509:      */         {
/*  3510: 3431 */           localLocalFrame = new LocalFrame(1);
/*  3511: 3432 */           localLocalStack.push(localLocalFrame);
/*  3512:      */         }
/*  3513: 3434 */         if (!bool1)
/*  3514:      */         {
/*  3515: 3436 */           localObject4 = local_Request.getOutputStream();
/*  3516: 3437 */           if (local_Request.isRMI()) {
/*  3517: 3437 */             local_Request.write_value(paramArrayOfRecWireInfo, new RecWireInfo[0].getClass());
/*  3518:      */           } else {
/*  3519: 3437 */             RecWireInfoSeqHelper.write((OutputStream)localObject4, paramArrayOfRecWireInfo);
/*  3520:      */           }
/*  3521:      */         }
/*  3522:      */         else
/*  3523:      */         {
/*  3524: 3441 */           localObject4 = local_Request.getOutputStream();
/*  3525: 3442 */           localLocalFrame.add(paramArrayOfRecWireInfo);
/*  3526:      */         }
/*  3527: 3444 */         local_Request.invoke();
/*  3528:      */         Object localObject5;
/*  3529:      */         Object localObject1;
/*  3530: 3445 */         if (bool1)
/*  3531:      */         {
/*  3532: 3447 */           localObject4 = null;
/*  3533: 3448 */           localObject5 = localLocalFrame.getResult();
/*  3534: 3449 */           if (localObject5 != null) {
/*  3535: 3451 */             localObject4 = (RecWireInfo[])ObjectVal.clone(localObject5);
/*  3536:      */           }
/*  3537: 3453 */           return localObject4;
/*  3538:      */         }
/*  3539: 3455 */         Object localObject4 = local_Request.getInputStream();
/*  3540: 3457 */         if (local_Request.isRMI()) {
/*  3541: 3457 */           localObject5 = (RecWireInfo[])local_Request.read_value(new RecWireInfo[0].getClass());
/*  3542:      */         } else {
/*  3543: 3457 */           localObject5 = RecWireInfoSeqHelper.read((InputStream)localObject4);
/*  3544:      */         }
/*  3545: 3458 */         return localObject5;
/*  3546:      */       }
/*  3547:      */       catch (TRANSIENT localTRANSIENT)
/*  3548:      */       {
/*  3549: 3462 */         if (i == 10) {
/*  3550: 3464 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3551:      */         }
/*  3552:      */       }
/*  3553:      */       catch (UserException localUserException)
/*  3554:      */       {
/*  3555: 3469 */         local_Request.isRMI();
/*  3556:      */         
/*  3557:      */ 
/*  3558: 3472 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3559:      */       }
/*  3560:      */       catch (SystemException localSystemException)
/*  3561:      */       {
/*  3562: 3476 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3563:      */       }
/*  3564:      */       finally
/*  3565:      */       {
/*  3566: 3480 */         if (local_Request != null) {
/*  3567: 3482 */           local_Request.close();
/*  3568:      */         }
/*  3569: 3484 */         if (bool1) {
/*  3570: 3485 */           localLocalStack.pop(localLocalFrame);
/*  3571:      */         }
/*  3572: 3486 */         localLocalStack.setArgsOnLocal(bool2);
/*  3573:      */       }
/*  3574:      */     }
/*  3575:      */   }
/*  3576:      */   
/*  3577:      */   public HashMap getWiresConfiguration()
/*  3578:      */     throws java.rmi.RemoteException
/*  3579:      */   {
/*  3580: 3494 */     for (int i = 1;; i++)
/*  3581:      */     {
/*  3582: 3496 */       _Request local_Request = null;
/*  3583: 3497 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3584: 3498 */       boolean bool1 = false;
/*  3585: 3499 */       LocalFrame localLocalFrame = null;
/*  3586: 3500 */       boolean bool2 = false;
/*  3587:      */       try
/*  3588:      */       {
/*  3589: 3503 */         local_Request = __request("getWiresConfiguration", "_get_wiresConfiguration");
/*  3590: 3504 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3591: 3505 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3592: 3506 */         localLocalStack.setArgsOnLocal(bool1);
/*  3593: 3508 */         if (bool1)
/*  3594:      */         {
/*  3595: 3510 */           localLocalFrame = new LocalFrame(0);
/*  3596: 3511 */           localLocalStack.push(localLocalFrame);
/*  3597:      */         }
/*  3598: 3519 */         local_Request.invoke();
/*  3599:      */         Object localObject1;
/*  3600: 3520 */         if (bool1)
/*  3601:      */         {
/*  3602: 3522 */           localObject4 = null;
/*  3603: 3523 */           localObject5 = localLocalFrame.getResult();
/*  3604: 3524 */           if (localObject5 != null) {
/*  3605: 3526 */             localObject4 = (HashMap)ObjectVal.clone(localObject5);
/*  3606:      */           }
/*  3607: 3528 */           return localObject4;
/*  3608:      */         }
/*  3609: 3530 */         Object localObject4 = local_Request.getInputStream();
/*  3610:      */         
/*  3611: 3532 */         Object localObject5 = (HashMap)local_Request.read_value(HashMap.class);
/*  3612: 3533 */         return localObject5;
/*  3613:      */       }
/*  3614:      */       catch (TRANSIENT localTRANSIENT)
/*  3615:      */       {
/*  3616: 3537 */         if (i == 10) {
/*  3617: 3539 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3618:      */         }
/*  3619:      */       }
/*  3620:      */       catch (UserException localUserException)
/*  3621:      */       {
/*  3622: 3544 */         local_Request.isRMI();
/*  3623:      */         
/*  3624:      */ 
/*  3625: 3547 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3626:      */       }
/*  3627:      */       catch (SystemException localSystemException)
/*  3628:      */       {
/*  3629: 3551 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3630:      */       }
/*  3631:      */       finally
/*  3632:      */       {
/*  3633: 3555 */         if (local_Request != null) {
/*  3634: 3557 */           local_Request.close();
/*  3635:      */         }
/*  3636: 3559 */         if (bool1) {
/*  3637: 3560 */           localLocalStack.pop(localLocalFrame);
/*  3638:      */         }
/*  3639: 3561 */         localLocalStack.setArgsOnLocal(bool2);
/*  3640:      */       }
/*  3641:      */     }
/*  3642:      */   }
/*  3643:      */   
/*  3644:      */   public WireBatchInfo addWireTransferBatch(WireBatchInfo paramWireBatchInfo)
/*  3645:      */     throws java.rmi.RemoteException
/*  3646:      */   {
/*  3647: 3570 */     for (int i = 1;; i++)
/*  3648:      */     {
/*  3649: 3572 */       _Request local_Request = null;
/*  3650: 3573 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3651: 3574 */       boolean bool1 = false;
/*  3652: 3575 */       LocalFrame localLocalFrame = null;
/*  3653: 3576 */       boolean bool2 = false;
/*  3654:      */       try
/*  3655:      */       {
/*  3656: 3579 */         local_Request = __request("addWireTransferBatch");
/*  3657: 3580 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3658: 3581 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3659: 3582 */         localLocalStack.setArgsOnLocal(bool1);
/*  3660: 3584 */         if (bool1)
/*  3661:      */         {
/*  3662: 3586 */           localLocalFrame = new LocalFrame(1);
/*  3663: 3587 */           localLocalStack.push(localLocalFrame);
/*  3664:      */         }
/*  3665: 3589 */         if (!bool1)
/*  3666:      */         {
/*  3667: 3591 */           localObject4 = local_Request.getOutputStream();
/*  3668: 3592 */           local_Request.write_value(paramWireBatchInfo, WireBatchInfo.class);
/*  3669:      */         }
/*  3670:      */         else
/*  3671:      */         {
/*  3672: 3596 */           localObject4 = local_Request.getOutputStream();
/*  3673: 3597 */           localLocalFrame.add(paramWireBatchInfo);
/*  3674:      */         }
/*  3675: 3599 */         local_Request.invoke();
/*  3676:      */         Object localObject1;
/*  3677: 3600 */         if (bool1)
/*  3678:      */         {
/*  3679: 3602 */           localObject4 = null;
/*  3680: 3603 */           localObject5 = localLocalFrame.getResult();
/*  3681: 3604 */           if (localObject5 != null) {
/*  3682: 3606 */             localObject4 = (WireBatchInfo)ObjectVal.clone(localObject5);
/*  3683:      */           }
/*  3684: 3608 */           return localObject4;
/*  3685:      */         }
/*  3686: 3610 */         Object localObject4 = local_Request.getInputStream();
/*  3687:      */         
/*  3688: 3612 */         Object localObject5 = (WireBatchInfo)local_Request.read_value(WireBatchInfo.class);
/*  3689: 3613 */         return localObject5;
/*  3690:      */       }
/*  3691:      */       catch (TRANSIENT localTRANSIENT)
/*  3692:      */       {
/*  3693: 3617 */         if (i == 10) {
/*  3694: 3619 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3695:      */         }
/*  3696:      */       }
/*  3697:      */       catch (UserException localUserException)
/*  3698:      */       {
/*  3699: 3624 */         local_Request.isRMI();
/*  3700:      */         
/*  3701:      */ 
/*  3702: 3627 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3703:      */       }
/*  3704:      */       catch (SystemException localSystemException)
/*  3705:      */       {
/*  3706: 3631 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3707:      */       }
/*  3708:      */       finally
/*  3709:      */       {
/*  3710: 3635 */         if (local_Request != null) {
/*  3711: 3637 */           local_Request.close();
/*  3712:      */         }
/*  3713: 3639 */         if (bool1) {
/*  3714: 3640 */           localLocalStack.pop(localLocalFrame);
/*  3715:      */         }
/*  3716: 3641 */         localLocalStack.setArgsOnLocal(bool2);
/*  3717:      */       }
/*  3718:      */     }
/*  3719:      */   }
/*  3720:      */   
/*  3721:      */   public WireBatchInfo modWireTransferBatch(WireBatchInfo paramWireBatchInfo)
/*  3722:      */     throws java.rmi.RemoteException
/*  3723:      */   {
/*  3724: 3650 */     for (int i = 1;; i++)
/*  3725:      */     {
/*  3726: 3652 */       _Request local_Request = null;
/*  3727: 3653 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3728: 3654 */       boolean bool1 = false;
/*  3729: 3655 */       LocalFrame localLocalFrame = null;
/*  3730: 3656 */       boolean bool2 = false;
/*  3731:      */       try
/*  3732:      */       {
/*  3733: 3659 */         local_Request = __request("modWireTransferBatch");
/*  3734: 3660 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3735: 3661 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3736: 3662 */         localLocalStack.setArgsOnLocal(bool1);
/*  3737: 3664 */         if (bool1)
/*  3738:      */         {
/*  3739: 3666 */           localLocalFrame = new LocalFrame(1);
/*  3740: 3667 */           localLocalStack.push(localLocalFrame);
/*  3741:      */         }
/*  3742: 3669 */         if (!bool1)
/*  3743:      */         {
/*  3744: 3671 */           localObject4 = local_Request.getOutputStream();
/*  3745: 3672 */           local_Request.write_value(paramWireBatchInfo, WireBatchInfo.class);
/*  3746:      */         }
/*  3747:      */         else
/*  3748:      */         {
/*  3749: 3676 */           localObject4 = local_Request.getOutputStream();
/*  3750: 3677 */           localLocalFrame.add(paramWireBatchInfo);
/*  3751:      */         }
/*  3752: 3679 */         local_Request.invoke();
/*  3753:      */         Object localObject1;
/*  3754: 3680 */         if (bool1)
/*  3755:      */         {
/*  3756: 3682 */           localObject4 = null;
/*  3757: 3683 */           localObject5 = localLocalFrame.getResult();
/*  3758: 3684 */           if (localObject5 != null) {
/*  3759: 3686 */             localObject4 = (WireBatchInfo)ObjectVal.clone(localObject5);
/*  3760:      */           }
/*  3761: 3688 */           return localObject4;
/*  3762:      */         }
/*  3763: 3690 */         Object localObject4 = local_Request.getInputStream();
/*  3764:      */         
/*  3765: 3692 */         Object localObject5 = (WireBatchInfo)local_Request.read_value(WireBatchInfo.class);
/*  3766: 3693 */         return localObject5;
/*  3767:      */       }
/*  3768:      */       catch (TRANSIENT localTRANSIENT)
/*  3769:      */       {
/*  3770: 3697 */         if (i == 10) {
/*  3771: 3699 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3772:      */         }
/*  3773:      */       }
/*  3774:      */       catch (UserException localUserException)
/*  3775:      */       {
/*  3776: 3704 */         local_Request.isRMI();
/*  3777:      */         
/*  3778:      */ 
/*  3779: 3707 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3780:      */       }
/*  3781:      */       catch (SystemException localSystemException)
/*  3782:      */       {
/*  3783: 3711 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3784:      */       }
/*  3785:      */       finally
/*  3786:      */       {
/*  3787: 3715 */         if (local_Request != null) {
/*  3788: 3717 */           local_Request.close();
/*  3789:      */         }
/*  3790: 3719 */         if (bool1) {
/*  3791: 3720 */           localLocalStack.pop(localLocalFrame);
/*  3792:      */         }
/*  3793: 3721 */         localLocalStack.setArgsOnLocal(bool2);
/*  3794:      */       }
/*  3795:      */     }
/*  3796:      */   }
/*  3797:      */   
/*  3798:      */   public WireBatchInfo canWireTransferBatch(WireBatchInfo paramWireBatchInfo)
/*  3799:      */     throws java.rmi.RemoteException
/*  3800:      */   {
/*  3801: 3730 */     for (int i = 1;; i++)
/*  3802:      */     {
/*  3803: 3732 */       _Request local_Request = null;
/*  3804: 3733 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3805: 3734 */       boolean bool1 = false;
/*  3806: 3735 */       LocalFrame localLocalFrame = null;
/*  3807: 3736 */       boolean bool2 = false;
/*  3808:      */       try
/*  3809:      */       {
/*  3810: 3739 */         local_Request = __request("canWireTransferBatch");
/*  3811: 3740 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3812: 3741 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3813: 3742 */         localLocalStack.setArgsOnLocal(bool1);
/*  3814: 3744 */         if (bool1)
/*  3815:      */         {
/*  3816: 3746 */           localLocalFrame = new LocalFrame(1);
/*  3817: 3747 */           localLocalStack.push(localLocalFrame);
/*  3818:      */         }
/*  3819: 3749 */         if (!bool1)
/*  3820:      */         {
/*  3821: 3751 */           localObject4 = local_Request.getOutputStream();
/*  3822: 3752 */           local_Request.write_value(paramWireBatchInfo, WireBatchInfo.class);
/*  3823:      */         }
/*  3824:      */         else
/*  3825:      */         {
/*  3826: 3756 */           localObject4 = local_Request.getOutputStream();
/*  3827: 3757 */           localLocalFrame.add(paramWireBatchInfo);
/*  3828:      */         }
/*  3829: 3759 */         local_Request.invoke();
/*  3830:      */         Object localObject1;
/*  3831: 3760 */         if (bool1)
/*  3832:      */         {
/*  3833: 3762 */           localObject4 = null;
/*  3834: 3763 */           localObject5 = localLocalFrame.getResult();
/*  3835: 3764 */           if (localObject5 != null) {
/*  3836: 3766 */             localObject4 = (WireBatchInfo)ObjectVal.clone(localObject5);
/*  3837:      */           }
/*  3838: 3768 */           return localObject4;
/*  3839:      */         }
/*  3840: 3770 */         Object localObject4 = local_Request.getInputStream();
/*  3841:      */         
/*  3842: 3772 */         Object localObject5 = (WireBatchInfo)local_Request.read_value(WireBatchInfo.class);
/*  3843: 3773 */         return localObject5;
/*  3844:      */       }
/*  3845:      */       catch (TRANSIENT localTRANSIENT)
/*  3846:      */       {
/*  3847: 3777 */         if (i == 10) {
/*  3848: 3779 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3849:      */         }
/*  3850:      */       }
/*  3851:      */       catch (UserException localUserException)
/*  3852:      */       {
/*  3853: 3784 */         local_Request.isRMI();
/*  3854:      */         
/*  3855:      */ 
/*  3856: 3787 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3857:      */       }
/*  3858:      */       catch (SystemException localSystemException)
/*  3859:      */       {
/*  3860: 3791 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3861:      */       }
/*  3862:      */       finally
/*  3863:      */       {
/*  3864: 3795 */         if (local_Request != null) {
/*  3865: 3797 */           local_Request.close();
/*  3866:      */         }
/*  3867: 3799 */         if (bool1) {
/*  3868: 3800 */           localLocalStack.pop(localLocalFrame);
/*  3869:      */         }
/*  3870: 3801 */         localLocalStack.setArgsOnLocal(bool2);
/*  3871:      */       }
/*  3872:      */     }
/*  3873:      */   }
/*  3874:      */   
/*  3875:      */   public WireBatchInfo[] getWireTransferBatch(WireBatchInfo paramWireBatchInfo)
/*  3876:      */     throws java.rmi.RemoteException
/*  3877:      */   {
/*  3878: 3810 */     for (int i = 1;; i++)
/*  3879:      */     {
/*  3880: 3812 */       _Request local_Request = null;
/*  3881: 3813 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3882: 3814 */       boolean bool1 = false;
/*  3883: 3815 */       LocalFrame localLocalFrame = null;
/*  3884: 3816 */       boolean bool2 = false;
/*  3885:      */       try
/*  3886:      */       {
/*  3887: 3819 */         local_Request = __request("getWireTransferBatch");
/*  3888: 3820 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3889: 3821 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3890: 3822 */         localLocalStack.setArgsOnLocal(bool1);
/*  3891: 3824 */         if (bool1)
/*  3892:      */         {
/*  3893: 3826 */           localLocalFrame = new LocalFrame(1);
/*  3894: 3827 */           localLocalStack.push(localLocalFrame);
/*  3895:      */         }
/*  3896: 3829 */         if (!bool1)
/*  3897:      */         {
/*  3898: 3831 */           localObject4 = local_Request.getOutputStream();
/*  3899: 3832 */           local_Request.write_value(paramWireBatchInfo, WireBatchInfo.class);
/*  3900:      */         }
/*  3901:      */         else
/*  3902:      */         {
/*  3903: 3836 */           localObject4 = local_Request.getOutputStream();
/*  3904: 3837 */           localLocalFrame.add(paramWireBatchInfo);
/*  3905:      */         }
/*  3906: 3839 */         local_Request.invoke();
/*  3907:      */         Object localObject5;
/*  3908:      */         Object localObject1;
/*  3909: 3840 */         if (bool1)
/*  3910:      */         {
/*  3911: 3842 */           localObject4 = null;
/*  3912: 3843 */           localObject5 = localLocalFrame.getResult();
/*  3913: 3844 */           if (localObject5 != null) {
/*  3914: 3846 */             localObject4 = (WireBatchInfo[])ObjectVal.clone(localObject5);
/*  3915:      */           }
/*  3916: 3848 */           return localObject4;
/*  3917:      */         }
/*  3918: 3850 */         Object localObject4 = local_Request.getInputStream();
/*  3919: 3852 */         if (local_Request.isRMI()) {
/*  3920: 3852 */           localObject5 = (WireBatchInfo[])local_Request.read_value(new WireBatchInfo[0].getClass());
/*  3921:      */         } else {
/*  3922: 3852 */           localObject5 = WireBatchInfoSeqHelper.read((InputStream)localObject4);
/*  3923:      */         }
/*  3924: 3853 */         return localObject5;
/*  3925:      */       }
/*  3926:      */       catch (TRANSIENT localTRANSIENT)
/*  3927:      */       {
/*  3928: 3857 */         if (i == 10) {
/*  3929: 3859 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  3930:      */         }
/*  3931:      */       }
/*  3932:      */       catch (UserException localUserException)
/*  3933:      */       {
/*  3934: 3864 */         local_Request.isRMI();
/*  3935:      */         
/*  3936:      */ 
/*  3937: 3867 */         throw new java.rmi.RemoteException(localUserException.type);
/*  3938:      */       }
/*  3939:      */       catch (SystemException localSystemException)
/*  3940:      */       {
/*  3941: 3871 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  3942:      */       }
/*  3943:      */       finally
/*  3944:      */       {
/*  3945: 3875 */         if (local_Request != null) {
/*  3946: 3877 */           local_Request.close();
/*  3947:      */         }
/*  3948: 3879 */         if (bool1) {
/*  3949: 3880 */           localLocalStack.pop(localLocalFrame);
/*  3950:      */         }
/*  3951: 3881 */         localLocalStack.setArgsOnLocal(bool2);
/*  3952:      */       }
/*  3953:      */     }
/*  3954:      */   }
/*  3955:      */   
/*  3956:      */   public BPWHist getWireBatchHistory(BPWHist paramBPWHist)
/*  3957:      */     throws java.rmi.RemoteException
/*  3958:      */   {
/*  3959: 3890 */     for (int i = 1;; i++)
/*  3960:      */     {
/*  3961: 3892 */       _Request local_Request = null;
/*  3962: 3893 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  3963: 3894 */       boolean bool1 = false;
/*  3964: 3895 */       LocalFrame localLocalFrame = null;
/*  3965: 3896 */       boolean bool2 = false;
/*  3966:      */       try
/*  3967:      */       {
/*  3968: 3899 */         local_Request = __request("getWireBatchHistory");
/*  3969: 3900 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  3970: 3901 */         bool2 = localLocalStack.isArgsOnLocal();
/*  3971: 3902 */         localLocalStack.setArgsOnLocal(bool1);
/*  3972: 3904 */         if (bool1)
/*  3973:      */         {
/*  3974: 3906 */           localLocalFrame = new LocalFrame(1);
/*  3975: 3907 */           localLocalStack.push(localLocalFrame);
/*  3976:      */         }
/*  3977: 3909 */         if (!bool1)
/*  3978:      */         {
/*  3979: 3911 */           localObject4 = local_Request.getOutputStream();
/*  3980: 3912 */           if (local_Request.isRMI()) {
/*  3981: 3912 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/*  3982:      */           } else {
/*  3983: 3912 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/*  3984:      */           }
/*  3985:      */         }
/*  3986:      */         else
/*  3987:      */         {
/*  3988: 3916 */           localObject4 = local_Request.getOutputStream();
/*  3989: 3917 */           localLocalFrame.add(paramBPWHist);
/*  3990:      */         }
/*  3991: 3919 */         local_Request.invoke();
/*  3992:      */         Object localObject5;
/*  3993:      */         Object localObject1;
/*  3994: 3920 */         if (bool1)
/*  3995:      */         {
/*  3996: 3922 */           localObject4 = null;
/*  3997: 3923 */           localObject5 = localLocalFrame.getResult();
/*  3998: 3924 */           if (localObject5 != null) {
/*  3999: 3926 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/*  4000:      */           }
/*  4001: 3928 */           return localObject4;
/*  4002:      */         }
/*  4003: 3930 */         Object localObject4 = local_Request.getInputStream();
/*  4004: 3932 */         if (local_Request.isRMI()) {
/*  4005: 3932 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/*  4006:      */         } else {
/*  4007: 3932 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/*  4008:      */         }
/*  4009: 3933 */         return localObject5;
/*  4010:      */       }
/*  4011:      */       catch (TRANSIENT localTRANSIENT)
/*  4012:      */       {
/*  4013: 3937 */         if (i == 10) {
/*  4014: 3939 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4015:      */         }
/*  4016:      */       }
/*  4017:      */       catch (UserException localUserException)
/*  4018:      */       {
/*  4019: 3944 */         local_Request.isRMI();
/*  4020:      */         
/*  4021:      */ 
/*  4022: 3947 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4023:      */       }
/*  4024:      */       catch (SystemException localSystemException)
/*  4025:      */       {
/*  4026: 3951 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4027:      */       }
/*  4028:      */       finally
/*  4029:      */       {
/*  4030: 3955 */         if (local_Request != null) {
/*  4031: 3957 */           local_Request.close();
/*  4032:      */         }
/*  4033: 3959 */         if (bool1) {
/*  4034: 3960 */           localLocalStack.pop(localLocalFrame);
/*  4035:      */         }
/*  4036: 3961 */         localLocalStack.setArgsOnLocal(bool2);
/*  4037:      */       }
/*  4038:      */     }
/*  4039:      */   }
/*  4040:      */   
/*  4041:      */   public boolean isWireBatchDeleteable(String paramString)
/*  4042:      */     throws java.rmi.RemoteException
/*  4043:      */   {
/*  4044: 3970 */     for (int i = 1;; i++)
/*  4045:      */     {
/*  4046: 3972 */       _Request local_Request = null;
/*  4047: 3973 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4048: 3974 */       boolean bool1 = false;
/*  4049: 3975 */       LocalFrame localLocalFrame = null;
/*  4050: 3976 */       boolean bool2 = false;
/*  4051:      */       try
/*  4052:      */       {
/*  4053: 3979 */         local_Request = __request("isWireBatchDeleteable");
/*  4054: 3980 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4055: 3981 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4056: 3982 */         localLocalStack.setArgsOnLocal(bool1);
/*  4057: 3984 */         if (bool1)
/*  4058:      */         {
/*  4059: 3986 */           localLocalFrame = new LocalFrame(1);
/*  4060: 3987 */           localLocalStack.push(localLocalFrame);
/*  4061:      */         }
/*  4062:      */         OutputStream localOutputStream;
/*  4063: 3989 */         if (!bool1)
/*  4064:      */         {
/*  4065: 3991 */           localOutputStream = local_Request.getOutputStream();
/*  4066: 3992 */           local_Request.write_string(paramString);
/*  4067:      */         }
/*  4068:      */         else
/*  4069:      */         {
/*  4070: 3996 */           localOutputStream = local_Request.getOutputStream();
/*  4071: 3997 */           localLocalFrame.add(paramString);
/*  4072:      */         }
/*  4073: 3999 */         local_Request.invoke();
/*  4074:      */         boolean bool3;
/*  4075: 4000 */         if (bool1)
/*  4076:      */         {
/*  4077: 4003 */           boolean bool4 = ((Boolean)localLocalFrame.getResult()).booleanValue();
/*  4078: 4004 */           return bool4;
/*  4079:      */         }
/*  4080: 4006 */         InputStream localInputStream = local_Request.getInputStream();
/*  4081:      */         
/*  4082: 4008 */         boolean bool5 = localInputStream.read_boolean();
/*  4083: 4009 */         return bool5;
/*  4084:      */       }
/*  4085:      */       catch (TRANSIENT localTRANSIENT)
/*  4086:      */       {
/*  4087: 4013 */         if (i == 10) {
/*  4088: 4015 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4089:      */         }
/*  4090:      */       }
/*  4091:      */       catch (UserException localUserException)
/*  4092:      */       {
/*  4093: 4020 */         local_Request.isRMI();
/*  4094:      */         
/*  4095:      */ 
/*  4096: 4023 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4097:      */       }
/*  4098:      */       catch (SystemException localSystemException)
/*  4099:      */       {
/*  4100: 4027 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4101:      */       }
/*  4102:      */       finally
/*  4103:      */       {
/*  4104: 4031 */         if (local_Request != null) {
/*  4105: 4033 */           local_Request.close();
/*  4106:      */         }
/*  4107: 4035 */         if (bool1) {
/*  4108: 4036 */           localLocalStack.pop(localLocalFrame);
/*  4109:      */         }
/*  4110: 4037 */         localLocalStack.setArgsOnLocal(bool2);
/*  4111:      */       }
/*  4112:      */     }
/*  4113:      */   }
/*  4114:      */   
/*  4115:      */   public WirePayeeInfo[] addWirePayee(WirePayeeInfo[] paramArrayOfWirePayeeInfo)
/*  4116:      */     throws java.rmi.RemoteException
/*  4117:      */   {
/*  4118: 4046 */     for (int i = 1;; i++)
/*  4119:      */     {
/*  4120: 4048 */       _Request local_Request = null;
/*  4121: 4049 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4122: 4050 */       boolean bool1 = false;
/*  4123: 4051 */       LocalFrame localLocalFrame = null;
/*  4124: 4052 */       boolean bool2 = false;
/*  4125:      */       try
/*  4126:      */       {
/*  4127: 4055 */         local_Request = __request("addWirePayee__WirePayeeInfoSeq", "addWirePayee__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_WirePayeeInfo");
/*  4128: 4056 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4129: 4057 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4130: 4058 */         localLocalStack.setArgsOnLocal(bool1);
/*  4131: 4060 */         if (bool1)
/*  4132:      */         {
/*  4133: 4062 */           localLocalFrame = new LocalFrame(1);
/*  4134: 4063 */           localLocalStack.push(localLocalFrame);
/*  4135:      */         }
/*  4136: 4065 */         if (!bool1)
/*  4137:      */         {
/*  4138: 4067 */           localObject4 = local_Request.getOutputStream();
/*  4139: 4068 */           if (local_Request.isRMI()) {
/*  4140: 4068 */             local_Request.write_value(paramArrayOfWirePayeeInfo, new WirePayeeInfo[0].getClass());
/*  4141:      */           } else {
/*  4142: 4068 */             WirePayeeInfoSeqHelper.write((OutputStream)localObject4, paramArrayOfWirePayeeInfo);
/*  4143:      */           }
/*  4144:      */         }
/*  4145:      */         else
/*  4146:      */         {
/*  4147: 4072 */           localObject4 = local_Request.getOutputStream();
/*  4148: 4073 */           localLocalFrame.add(paramArrayOfWirePayeeInfo);
/*  4149:      */         }
/*  4150: 4075 */         local_Request.invoke();
/*  4151:      */         Object localObject5;
/*  4152:      */         Object localObject1;
/*  4153: 4076 */         if (bool1)
/*  4154:      */         {
/*  4155: 4078 */           localObject4 = null;
/*  4156: 4079 */           localObject5 = localLocalFrame.getResult();
/*  4157: 4080 */           if (localObject5 != null) {
/*  4158: 4082 */             localObject4 = (WirePayeeInfo[])ObjectVal.clone(localObject5);
/*  4159:      */           }
/*  4160: 4084 */           return localObject4;
/*  4161:      */         }
/*  4162: 4086 */         Object localObject4 = local_Request.getInputStream();
/*  4163: 4088 */         if (local_Request.isRMI()) {
/*  4164: 4088 */           localObject5 = (WirePayeeInfo[])local_Request.read_value(new WirePayeeInfo[0].getClass());
/*  4165:      */         } else {
/*  4166: 4088 */           localObject5 = WirePayeeInfoSeqHelper.read((InputStream)localObject4);
/*  4167:      */         }
/*  4168: 4089 */         return localObject5;
/*  4169:      */       }
/*  4170:      */       catch (TRANSIENT localTRANSIENT)
/*  4171:      */       {
/*  4172: 4093 */         if (i == 10) {
/*  4173: 4095 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4174:      */         }
/*  4175:      */       }
/*  4176:      */       catch (UserException localUserException)
/*  4177:      */       {
/*  4178: 4100 */         local_Request.isRMI();
/*  4179:      */         
/*  4180:      */ 
/*  4181: 4103 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4182:      */       }
/*  4183:      */       catch (SystemException localSystemException)
/*  4184:      */       {
/*  4185: 4107 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4186:      */       }
/*  4187:      */       finally
/*  4188:      */       {
/*  4189: 4111 */         if (local_Request != null) {
/*  4190: 4113 */           local_Request.close();
/*  4191:      */         }
/*  4192: 4115 */         if (bool1) {
/*  4193: 4116 */           localLocalStack.pop(localLocalFrame);
/*  4194:      */         }
/*  4195: 4117 */         localLocalStack.setArgsOnLocal(bool2);
/*  4196:      */       }
/*  4197:      */     }
/*  4198:      */   }
/*  4199:      */   
/*  4200:      */   public WirePayeeInfo addWirePayee(WirePayeeInfo paramWirePayeeInfo)
/*  4201:      */     throws java.rmi.RemoteException
/*  4202:      */   {
/*  4203: 4126 */     for (int i = 1;; i++)
/*  4204:      */     {
/*  4205: 4128 */       _Request local_Request = null;
/*  4206: 4129 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4207: 4130 */       boolean bool1 = false;
/*  4208: 4131 */       LocalFrame localLocalFrame = null;
/*  4209: 4132 */       boolean bool2 = false;
/*  4210:      */       try
/*  4211:      */       {
/*  4212: 4135 */         local_Request = __request("addWirePayee__WirePayeeInfo", "addWirePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_WirePayeeInfo");
/*  4213: 4136 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4214: 4137 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4215: 4138 */         localLocalStack.setArgsOnLocal(bool1);
/*  4216: 4140 */         if (bool1)
/*  4217:      */         {
/*  4218: 4142 */           localLocalFrame = new LocalFrame(1);
/*  4219: 4143 */           localLocalStack.push(localLocalFrame);
/*  4220:      */         }
/*  4221: 4145 */         if (!bool1)
/*  4222:      */         {
/*  4223: 4147 */           localObject4 = local_Request.getOutputStream();
/*  4224: 4148 */           local_Request.write_value(paramWirePayeeInfo, WirePayeeInfo.class);
/*  4225:      */         }
/*  4226:      */         else
/*  4227:      */         {
/*  4228: 4152 */           localObject4 = local_Request.getOutputStream();
/*  4229: 4153 */           localLocalFrame.add(paramWirePayeeInfo);
/*  4230:      */         }
/*  4231: 4155 */         local_Request.invoke();
/*  4232:      */         Object localObject1;
/*  4233: 4156 */         if (bool1)
/*  4234:      */         {
/*  4235: 4158 */           localObject4 = null;
/*  4236: 4159 */           localObject5 = localLocalFrame.getResult();
/*  4237: 4160 */           if (localObject5 != null) {
/*  4238: 4162 */             localObject4 = (WirePayeeInfo)ObjectVal.clone(localObject5);
/*  4239:      */           }
/*  4240: 4164 */           return localObject4;
/*  4241:      */         }
/*  4242: 4166 */         Object localObject4 = local_Request.getInputStream();
/*  4243:      */         
/*  4244: 4168 */         Object localObject5 = (WirePayeeInfo)local_Request.read_value(WirePayeeInfo.class);
/*  4245: 4169 */         return localObject5;
/*  4246:      */       }
/*  4247:      */       catch (TRANSIENT localTRANSIENT)
/*  4248:      */       {
/*  4249: 4173 */         if (i == 10) {
/*  4250: 4175 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4251:      */         }
/*  4252:      */       }
/*  4253:      */       catch (UserException localUserException)
/*  4254:      */       {
/*  4255: 4180 */         local_Request.isRMI();
/*  4256:      */         
/*  4257:      */ 
/*  4258: 4183 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4259:      */       }
/*  4260:      */       catch (SystemException localSystemException)
/*  4261:      */       {
/*  4262: 4187 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4263:      */       }
/*  4264:      */       finally
/*  4265:      */       {
/*  4266: 4191 */         if (local_Request != null) {
/*  4267: 4193 */           local_Request.close();
/*  4268:      */         }
/*  4269: 4195 */         if (bool1) {
/*  4270: 4196 */           localLocalStack.pop(localLocalFrame);
/*  4271:      */         }
/*  4272: 4197 */         localLocalStack.setArgsOnLocal(bool2);
/*  4273:      */       }
/*  4274:      */     }
/*  4275:      */   }
/*  4276:      */   
/*  4277:      */   public WirePayeeInfo modWirePayee(WirePayeeInfo paramWirePayeeInfo)
/*  4278:      */     throws java.rmi.RemoteException
/*  4279:      */   {
/*  4280: 4206 */     for (int i = 1;; i++)
/*  4281:      */     {
/*  4282: 4208 */       _Request local_Request = null;
/*  4283: 4209 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4284: 4210 */       boolean bool1 = false;
/*  4285: 4211 */       LocalFrame localLocalFrame = null;
/*  4286: 4212 */       boolean bool2 = false;
/*  4287:      */       try
/*  4288:      */       {
/*  4289: 4215 */         local_Request = __request("modWirePayee");
/*  4290: 4216 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4291: 4217 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4292: 4218 */         localLocalStack.setArgsOnLocal(bool1);
/*  4293: 4220 */         if (bool1)
/*  4294:      */         {
/*  4295: 4222 */           localLocalFrame = new LocalFrame(1);
/*  4296: 4223 */           localLocalStack.push(localLocalFrame);
/*  4297:      */         }
/*  4298: 4225 */         if (!bool1)
/*  4299:      */         {
/*  4300: 4227 */           localObject4 = local_Request.getOutputStream();
/*  4301: 4228 */           local_Request.write_value(paramWirePayeeInfo, WirePayeeInfo.class);
/*  4302:      */         }
/*  4303:      */         else
/*  4304:      */         {
/*  4305: 4232 */           localObject4 = local_Request.getOutputStream();
/*  4306: 4233 */           localLocalFrame.add(paramWirePayeeInfo);
/*  4307:      */         }
/*  4308: 4235 */         local_Request.invoke();
/*  4309:      */         Object localObject1;
/*  4310: 4236 */         if (bool1)
/*  4311:      */         {
/*  4312: 4238 */           localObject4 = null;
/*  4313: 4239 */           localObject5 = localLocalFrame.getResult();
/*  4314: 4240 */           if (localObject5 != null) {
/*  4315: 4242 */             localObject4 = (WirePayeeInfo)ObjectVal.clone(localObject5);
/*  4316:      */           }
/*  4317: 4244 */           return localObject4;
/*  4318:      */         }
/*  4319: 4246 */         Object localObject4 = local_Request.getInputStream();
/*  4320:      */         
/*  4321: 4248 */         Object localObject5 = (WirePayeeInfo)local_Request.read_value(WirePayeeInfo.class);
/*  4322: 4249 */         return localObject5;
/*  4323:      */       }
/*  4324:      */       catch (TRANSIENT localTRANSIENT)
/*  4325:      */       {
/*  4326: 4253 */         if (i == 10) {
/*  4327: 4255 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4328:      */         }
/*  4329:      */       }
/*  4330:      */       catch (UserException localUserException)
/*  4331:      */       {
/*  4332: 4260 */         local_Request.isRMI();
/*  4333:      */         
/*  4334:      */ 
/*  4335: 4263 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4336:      */       }
/*  4337:      */       catch (SystemException localSystemException)
/*  4338:      */       {
/*  4339: 4267 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4340:      */       }
/*  4341:      */       finally
/*  4342:      */       {
/*  4343: 4271 */         if (local_Request != null) {
/*  4344: 4273 */           local_Request.close();
/*  4345:      */         }
/*  4346: 4275 */         if (bool1) {
/*  4347: 4276 */           localLocalStack.pop(localLocalFrame);
/*  4348:      */         }
/*  4349: 4277 */         localLocalStack.setArgsOnLocal(bool2);
/*  4350:      */       }
/*  4351:      */     }
/*  4352:      */   }
/*  4353:      */   
/*  4354:      */   public WirePayeeInfo canWirePayee(WirePayeeInfo paramWirePayeeInfo)
/*  4355:      */     throws java.rmi.RemoteException
/*  4356:      */   {
/*  4357: 4286 */     for (int i = 1;; i++)
/*  4358:      */     {
/*  4359: 4288 */       _Request local_Request = null;
/*  4360: 4289 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4361: 4290 */       boolean bool1 = false;
/*  4362: 4291 */       LocalFrame localLocalFrame = null;
/*  4363: 4292 */       boolean bool2 = false;
/*  4364:      */       try
/*  4365:      */       {
/*  4366: 4295 */         local_Request = __request("canWirePayee");
/*  4367: 4296 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4368: 4297 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4369: 4298 */         localLocalStack.setArgsOnLocal(bool1);
/*  4370: 4300 */         if (bool1)
/*  4371:      */         {
/*  4372: 4302 */           localLocalFrame = new LocalFrame(1);
/*  4373: 4303 */           localLocalStack.push(localLocalFrame);
/*  4374:      */         }
/*  4375: 4305 */         if (!bool1)
/*  4376:      */         {
/*  4377: 4307 */           localObject4 = local_Request.getOutputStream();
/*  4378: 4308 */           local_Request.write_value(paramWirePayeeInfo, WirePayeeInfo.class);
/*  4379:      */         }
/*  4380:      */         else
/*  4381:      */         {
/*  4382: 4312 */           localObject4 = local_Request.getOutputStream();
/*  4383: 4313 */           localLocalFrame.add(paramWirePayeeInfo);
/*  4384:      */         }
/*  4385: 4315 */         local_Request.invoke();
/*  4386:      */         Object localObject1;
/*  4387: 4316 */         if (bool1)
/*  4388:      */         {
/*  4389: 4318 */           localObject4 = null;
/*  4390: 4319 */           localObject5 = localLocalFrame.getResult();
/*  4391: 4320 */           if (localObject5 != null) {
/*  4392: 4322 */             localObject4 = (WirePayeeInfo)ObjectVal.clone(localObject5);
/*  4393:      */           }
/*  4394: 4324 */           return localObject4;
/*  4395:      */         }
/*  4396: 4326 */         Object localObject4 = local_Request.getInputStream();
/*  4397:      */         
/*  4398: 4328 */         Object localObject5 = (WirePayeeInfo)local_Request.read_value(WirePayeeInfo.class);
/*  4399: 4329 */         return localObject5;
/*  4400:      */       }
/*  4401:      */       catch (TRANSIENT localTRANSIENT)
/*  4402:      */       {
/*  4403: 4333 */         if (i == 10) {
/*  4404: 4335 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4405:      */         }
/*  4406:      */       }
/*  4407:      */       catch (UserException localUserException)
/*  4408:      */       {
/*  4409: 4340 */         local_Request.isRMI();
/*  4410:      */         
/*  4411:      */ 
/*  4412: 4343 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4413:      */       }
/*  4414:      */       catch (SystemException localSystemException)
/*  4415:      */       {
/*  4416: 4347 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4417:      */       }
/*  4418:      */       finally
/*  4419:      */       {
/*  4420: 4351 */         if (local_Request != null) {
/*  4421: 4353 */           local_Request.close();
/*  4422:      */         }
/*  4423: 4355 */         if (bool1) {
/*  4424: 4356 */           localLocalStack.pop(localLocalFrame);
/*  4425:      */         }
/*  4426: 4357 */         localLocalStack.setArgsOnLocal(bool2);
/*  4427:      */       }
/*  4428:      */     }
/*  4429:      */   }
/*  4430:      */   
/*  4431:      */   public WirePayeeInfo getWirePayee(WirePayeeInfo paramWirePayeeInfo)
/*  4432:      */     throws java.rmi.RemoteException
/*  4433:      */   {
/*  4434: 4366 */     for (int i = 1;; i++)
/*  4435:      */     {
/*  4436: 4368 */       _Request local_Request = null;
/*  4437: 4369 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4438: 4370 */       boolean bool1 = false;
/*  4439: 4371 */       LocalFrame localLocalFrame = null;
/*  4440: 4372 */       boolean bool2 = false;
/*  4441:      */       try
/*  4442:      */       {
/*  4443: 4375 */         local_Request = __request("getWirePayee__WirePayeeInfo", "getWirePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_WirePayeeInfo");
/*  4444: 4376 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4445: 4377 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4446: 4378 */         localLocalStack.setArgsOnLocal(bool1);
/*  4447: 4380 */         if (bool1)
/*  4448:      */         {
/*  4449: 4382 */           localLocalFrame = new LocalFrame(1);
/*  4450: 4383 */           localLocalStack.push(localLocalFrame);
/*  4451:      */         }
/*  4452: 4385 */         if (!bool1)
/*  4453:      */         {
/*  4454: 4387 */           localObject4 = local_Request.getOutputStream();
/*  4455: 4388 */           local_Request.write_value(paramWirePayeeInfo, WirePayeeInfo.class);
/*  4456:      */         }
/*  4457:      */         else
/*  4458:      */         {
/*  4459: 4392 */           localObject4 = local_Request.getOutputStream();
/*  4460: 4393 */           localLocalFrame.add(paramWirePayeeInfo);
/*  4461:      */         }
/*  4462: 4395 */         local_Request.invoke();
/*  4463:      */         Object localObject1;
/*  4464: 4396 */         if (bool1)
/*  4465:      */         {
/*  4466: 4398 */           localObject4 = null;
/*  4467: 4399 */           localObject5 = localLocalFrame.getResult();
/*  4468: 4400 */           if (localObject5 != null) {
/*  4469: 4402 */             localObject4 = (WirePayeeInfo)ObjectVal.clone(localObject5);
/*  4470:      */           }
/*  4471: 4404 */           return localObject4;
/*  4472:      */         }
/*  4473: 4406 */         Object localObject4 = local_Request.getInputStream();
/*  4474:      */         
/*  4475: 4408 */         Object localObject5 = (WirePayeeInfo)local_Request.read_value(WirePayeeInfo.class);
/*  4476: 4409 */         return localObject5;
/*  4477:      */       }
/*  4478:      */       catch (TRANSIENT localTRANSIENT)
/*  4479:      */       {
/*  4480: 4413 */         if (i == 10) {
/*  4481: 4415 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4482:      */         }
/*  4483:      */       }
/*  4484:      */       catch (UserException localUserException)
/*  4485:      */       {
/*  4486: 4420 */         local_Request.isRMI();
/*  4487:      */         
/*  4488:      */ 
/*  4489: 4423 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4490:      */       }
/*  4491:      */       catch (SystemException localSystemException)
/*  4492:      */       {
/*  4493: 4427 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4494:      */       }
/*  4495:      */       finally
/*  4496:      */       {
/*  4497: 4431 */         if (local_Request != null) {
/*  4498: 4433 */           local_Request.close();
/*  4499:      */         }
/*  4500: 4435 */         if (bool1) {
/*  4501: 4436 */           localLocalStack.pop(localLocalFrame);
/*  4502:      */         }
/*  4503: 4437 */         localLocalStack.setArgsOnLocal(bool2);
/*  4504:      */       }
/*  4505:      */     }
/*  4506:      */   }
/*  4507:      */   
/*  4508:      */   public WirePayeeInfo getWirePayee(String paramString)
/*  4509:      */     throws java.rmi.RemoteException
/*  4510:      */   {
/*  4511: 4446 */     for (int i = 1;; i++)
/*  4512:      */     {
/*  4513: 4448 */       _Request local_Request = null;
/*  4514: 4449 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4515: 4450 */       boolean bool1 = false;
/*  4516: 4451 */       LocalFrame localLocalFrame = null;
/*  4517: 4452 */       boolean bool2 = false;
/*  4518:      */       try
/*  4519:      */       {
/*  4520: 4455 */         local_Request = __request("getWirePayee__string", "getWirePayee__CORBA_WStringValue");
/*  4521: 4456 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4522: 4457 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4523: 4458 */         localLocalStack.setArgsOnLocal(bool1);
/*  4524: 4460 */         if (bool1)
/*  4525:      */         {
/*  4526: 4462 */           localLocalFrame = new LocalFrame(1);
/*  4527: 4463 */           localLocalStack.push(localLocalFrame);
/*  4528:      */         }
/*  4529: 4465 */         if (!bool1)
/*  4530:      */         {
/*  4531: 4467 */           localObject4 = local_Request.getOutputStream();
/*  4532: 4468 */           local_Request.write_string(paramString);
/*  4533:      */         }
/*  4534:      */         else
/*  4535:      */         {
/*  4536: 4472 */           localObject4 = local_Request.getOutputStream();
/*  4537: 4473 */           localLocalFrame.add(paramString);
/*  4538:      */         }
/*  4539: 4475 */         local_Request.invoke();
/*  4540:      */         Object localObject1;
/*  4541: 4476 */         if (bool1)
/*  4542:      */         {
/*  4543: 4478 */           localObject4 = null;
/*  4544: 4479 */           localObject5 = localLocalFrame.getResult();
/*  4545: 4480 */           if (localObject5 != null) {
/*  4546: 4482 */             localObject4 = (WirePayeeInfo)ObjectVal.clone(localObject5);
/*  4547:      */           }
/*  4548: 4484 */           return localObject4;
/*  4549:      */         }
/*  4550: 4486 */         Object localObject4 = local_Request.getInputStream();
/*  4551:      */         
/*  4552: 4488 */         Object localObject5 = (WirePayeeInfo)local_Request.read_value(WirePayeeInfo.class);
/*  4553: 4489 */         return localObject5;
/*  4554:      */       }
/*  4555:      */       catch (TRANSIENT localTRANSIENT)
/*  4556:      */       {
/*  4557: 4493 */         if (i == 10) {
/*  4558: 4495 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4559:      */         }
/*  4560:      */       }
/*  4561:      */       catch (UserException localUserException)
/*  4562:      */       {
/*  4563: 4500 */         local_Request.isRMI();
/*  4564:      */         
/*  4565:      */ 
/*  4566: 4503 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4567:      */       }
/*  4568:      */       catch (SystemException localSystemException)
/*  4569:      */       {
/*  4570: 4507 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4571:      */       }
/*  4572:      */       finally
/*  4573:      */       {
/*  4574: 4511 */         if (local_Request != null) {
/*  4575: 4513 */           local_Request.close();
/*  4576:      */         }
/*  4577: 4515 */         if (bool1) {
/*  4578: 4516 */           localLocalStack.pop(localLocalFrame);
/*  4579:      */         }
/*  4580: 4517 */         localLocalStack.setArgsOnLocal(bool2);
/*  4581:      */       }
/*  4582:      */     }
/*  4583:      */   }
/*  4584:      */   
/*  4585:      */   public BPWPayeeList getWirePayeeByType(BPWPayeeList paramBPWPayeeList)
/*  4586:      */     throws java.rmi.RemoteException
/*  4587:      */   {
/*  4588: 4526 */     for (int i = 1;; i++)
/*  4589:      */     {
/*  4590: 4528 */       _Request local_Request = null;
/*  4591: 4529 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4592: 4530 */       boolean bool1 = false;
/*  4593: 4531 */       LocalFrame localLocalFrame = null;
/*  4594: 4532 */       boolean bool2 = false;
/*  4595:      */       try
/*  4596:      */       {
/*  4597: 4535 */         local_Request = __request("getWirePayeeByType");
/*  4598: 4536 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4599: 4537 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4600: 4538 */         localLocalStack.setArgsOnLocal(bool1);
/*  4601: 4540 */         if (bool1)
/*  4602:      */         {
/*  4603: 4542 */           localLocalFrame = new LocalFrame(1);
/*  4604: 4543 */           localLocalStack.push(localLocalFrame);
/*  4605:      */         }
/*  4606: 4545 */         if (!bool1)
/*  4607:      */         {
/*  4608: 4547 */           localObject4 = local_Request.getOutputStream();
/*  4609: 4548 */           local_Request.write_value(paramBPWPayeeList, BPWPayeeList.class);
/*  4610:      */         }
/*  4611:      */         else
/*  4612:      */         {
/*  4613: 4552 */           localObject4 = local_Request.getOutputStream();
/*  4614: 4553 */           localLocalFrame.add(paramBPWPayeeList);
/*  4615:      */         }
/*  4616: 4555 */         local_Request.invoke();
/*  4617:      */         Object localObject1;
/*  4618: 4556 */         if (bool1)
/*  4619:      */         {
/*  4620: 4558 */           localObject4 = null;
/*  4621: 4559 */           localObject5 = localLocalFrame.getResult();
/*  4622: 4560 */           if (localObject5 != null) {
/*  4623: 4562 */             localObject4 = (BPWPayeeList)ObjectVal.clone(localObject5);
/*  4624:      */           }
/*  4625: 4564 */           return localObject4;
/*  4626:      */         }
/*  4627: 4566 */         Object localObject4 = local_Request.getInputStream();
/*  4628:      */         
/*  4629: 4568 */         Object localObject5 = (BPWPayeeList)local_Request.read_value(BPWPayeeList.class);
/*  4630: 4569 */         return localObject5;
/*  4631:      */       }
/*  4632:      */       catch (TRANSIENT localTRANSIENT)
/*  4633:      */       {
/*  4634: 4573 */         if (i == 10) {
/*  4635: 4575 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4636:      */         }
/*  4637:      */       }
/*  4638:      */       catch (UserException localUserException)
/*  4639:      */       {
/*  4640: 4580 */         local_Request.isRMI();
/*  4641:      */         
/*  4642:      */ 
/*  4643: 4583 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4644:      */       }
/*  4645:      */       catch (SystemException localSystemException)
/*  4646:      */       {
/*  4647: 4587 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4648:      */       }
/*  4649:      */       finally
/*  4650:      */       {
/*  4651: 4591 */         if (local_Request != null) {
/*  4652: 4593 */           local_Request.close();
/*  4653:      */         }
/*  4654: 4595 */         if (bool1) {
/*  4655: 4596 */           localLocalStack.pop(localLocalFrame);
/*  4656:      */         }
/*  4657: 4597 */         localLocalStack.setArgsOnLocal(bool2);
/*  4658:      */       }
/*  4659:      */     }
/*  4660:      */   }
/*  4661:      */   
/*  4662:      */   public BPWPayeeList getWirePayeeByStatus(BPWPayeeList paramBPWPayeeList)
/*  4663:      */     throws java.rmi.RemoteException
/*  4664:      */   {
/*  4665: 4606 */     for (int i = 1;; i++)
/*  4666:      */     {
/*  4667: 4608 */       _Request local_Request = null;
/*  4668: 4609 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4669: 4610 */       boolean bool1 = false;
/*  4670: 4611 */       LocalFrame localLocalFrame = null;
/*  4671: 4612 */       boolean bool2 = false;
/*  4672:      */       try
/*  4673:      */       {
/*  4674: 4615 */         local_Request = __request("getWirePayeeByStatus");
/*  4675: 4616 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4676: 4617 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4677: 4618 */         localLocalStack.setArgsOnLocal(bool1);
/*  4678: 4620 */         if (bool1)
/*  4679:      */         {
/*  4680: 4622 */           localLocalFrame = new LocalFrame(1);
/*  4681: 4623 */           localLocalStack.push(localLocalFrame);
/*  4682:      */         }
/*  4683: 4625 */         if (!bool1)
/*  4684:      */         {
/*  4685: 4627 */           localObject4 = local_Request.getOutputStream();
/*  4686: 4628 */           local_Request.write_value(paramBPWPayeeList, BPWPayeeList.class);
/*  4687:      */         }
/*  4688:      */         else
/*  4689:      */         {
/*  4690: 4632 */           localObject4 = local_Request.getOutputStream();
/*  4691: 4633 */           localLocalFrame.add(paramBPWPayeeList);
/*  4692:      */         }
/*  4693: 4635 */         local_Request.invoke();
/*  4694:      */         Object localObject1;
/*  4695: 4636 */         if (bool1)
/*  4696:      */         {
/*  4697: 4638 */           localObject4 = null;
/*  4698: 4639 */           localObject5 = localLocalFrame.getResult();
/*  4699: 4640 */           if (localObject5 != null) {
/*  4700: 4642 */             localObject4 = (BPWPayeeList)ObjectVal.clone(localObject5);
/*  4701:      */           }
/*  4702: 4644 */           return localObject4;
/*  4703:      */         }
/*  4704: 4646 */         Object localObject4 = local_Request.getInputStream();
/*  4705:      */         
/*  4706: 4648 */         Object localObject5 = (BPWPayeeList)local_Request.read_value(BPWPayeeList.class);
/*  4707: 4649 */         return localObject5;
/*  4708:      */       }
/*  4709:      */       catch (TRANSIENT localTRANSIENT)
/*  4710:      */       {
/*  4711: 4653 */         if (i == 10) {
/*  4712: 4655 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4713:      */         }
/*  4714:      */       }
/*  4715:      */       catch (UserException localUserException)
/*  4716:      */       {
/*  4717: 4660 */         local_Request.isRMI();
/*  4718:      */         
/*  4719:      */ 
/*  4720: 4663 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4721:      */       }
/*  4722:      */       catch (SystemException localSystemException)
/*  4723:      */       {
/*  4724: 4667 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4725:      */       }
/*  4726:      */       finally
/*  4727:      */       {
/*  4728: 4671 */         if (local_Request != null) {
/*  4729: 4673 */           local_Request.close();
/*  4730:      */         }
/*  4731: 4675 */         if (bool1) {
/*  4732: 4676 */           localLocalStack.pop(localLocalFrame);
/*  4733:      */         }
/*  4734: 4677 */         localLocalStack.setArgsOnLocal(bool2);
/*  4735:      */       }
/*  4736:      */     }
/*  4737:      */   }
/*  4738:      */   
/*  4739:      */   public BPWPayeeList getWirePayeeByGroup(BPWPayeeList paramBPWPayeeList)
/*  4740:      */     throws java.rmi.RemoteException
/*  4741:      */   {
/*  4742: 4686 */     for (int i = 1;; i++)
/*  4743:      */     {
/*  4744: 4688 */       _Request local_Request = null;
/*  4745: 4689 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4746: 4690 */       boolean bool1 = false;
/*  4747: 4691 */       LocalFrame localLocalFrame = null;
/*  4748: 4692 */       boolean bool2 = false;
/*  4749:      */       try
/*  4750:      */       {
/*  4751: 4695 */         local_Request = __request("getWirePayeeByGroup");
/*  4752: 4696 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4753: 4697 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4754: 4698 */         localLocalStack.setArgsOnLocal(bool1);
/*  4755: 4700 */         if (bool1)
/*  4756:      */         {
/*  4757: 4702 */           localLocalFrame = new LocalFrame(1);
/*  4758: 4703 */           localLocalStack.push(localLocalFrame);
/*  4759:      */         }
/*  4760: 4705 */         if (!bool1)
/*  4761:      */         {
/*  4762: 4707 */           localObject4 = local_Request.getOutputStream();
/*  4763: 4708 */           local_Request.write_value(paramBPWPayeeList, BPWPayeeList.class);
/*  4764:      */         }
/*  4765:      */         else
/*  4766:      */         {
/*  4767: 4712 */           localObject4 = local_Request.getOutputStream();
/*  4768: 4713 */           localLocalFrame.add(paramBPWPayeeList);
/*  4769:      */         }
/*  4770: 4715 */         local_Request.invoke();
/*  4771:      */         Object localObject1;
/*  4772: 4716 */         if (bool1)
/*  4773:      */         {
/*  4774: 4718 */           localObject4 = null;
/*  4775: 4719 */           localObject5 = localLocalFrame.getResult();
/*  4776: 4720 */           if (localObject5 != null) {
/*  4777: 4722 */             localObject4 = (BPWPayeeList)ObjectVal.clone(localObject5);
/*  4778:      */           }
/*  4779: 4724 */           return localObject4;
/*  4780:      */         }
/*  4781: 4726 */         Object localObject4 = local_Request.getInputStream();
/*  4782:      */         
/*  4783: 4728 */         Object localObject5 = (BPWPayeeList)local_Request.read_value(BPWPayeeList.class);
/*  4784: 4729 */         return localObject5;
/*  4785:      */       }
/*  4786:      */       catch (TRANSIENT localTRANSIENT)
/*  4787:      */       {
/*  4788: 4733 */         if (i == 10) {
/*  4789: 4735 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4790:      */         }
/*  4791:      */       }
/*  4792:      */       catch (UserException localUserException)
/*  4793:      */       {
/*  4794: 4740 */         local_Request.isRMI();
/*  4795:      */         
/*  4796:      */ 
/*  4797: 4743 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4798:      */       }
/*  4799:      */       catch (SystemException localSystemException)
/*  4800:      */       {
/*  4801: 4747 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4802:      */       }
/*  4803:      */       finally
/*  4804:      */       {
/*  4805: 4751 */         if (local_Request != null) {
/*  4806: 4753 */           local_Request.close();
/*  4807:      */         }
/*  4808: 4755 */         if (bool1) {
/*  4809: 4756 */           localLocalStack.pop(localLocalFrame);
/*  4810:      */         }
/*  4811: 4757 */         localLocalStack.setArgsOnLocal(bool2);
/*  4812:      */       }
/*  4813:      */     }
/*  4814:      */   }
/*  4815:      */   
/*  4816:      */   public BPWPayeeList getWirePayeeByCustomer(BPWPayeeList paramBPWPayeeList)
/*  4817:      */     throws java.rmi.RemoteException
/*  4818:      */   {
/*  4819: 4766 */     for (int i = 1;; i++)
/*  4820:      */     {
/*  4821: 4768 */       _Request local_Request = null;
/*  4822: 4769 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4823: 4770 */       boolean bool1 = false;
/*  4824: 4771 */       LocalFrame localLocalFrame = null;
/*  4825: 4772 */       boolean bool2 = false;
/*  4826:      */       try
/*  4827:      */       {
/*  4828: 4775 */         local_Request = __request("getWirePayeeByCustomer");
/*  4829: 4776 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4830: 4777 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4831: 4778 */         localLocalStack.setArgsOnLocal(bool1);
/*  4832: 4780 */         if (bool1)
/*  4833:      */         {
/*  4834: 4782 */           localLocalFrame = new LocalFrame(1);
/*  4835: 4783 */           localLocalStack.push(localLocalFrame);
/*  4836:      */         }
/*  4837: 4785 */         if (!bool1)
/*  4838:      */         {
/*  4839: 4787 */           localObject4 = local_Request.getOutputStream();
/*  4840: 4788 */           local_Request.write_value(paramBPWPayeeList, BPWPayeeList.class);
/*  4841:      */         }
/*  4842:      */         else
/*  4843:      */         {
/*  4844: 4792 */           localObject4 = local_Request.getOutputStream();
/*  4845: 4793 */           localLocalFrame.add(paramBPWPayeeList);
/*  4846:      */         }
/*  4847: 4795 */         local_Request.invoke();
/*  4848:      */         Object localObject1;
/*  4849: 4796 */         if (bool1)
/*  4850:      */         {
/*  4851: 4798 */           localObject4 = null;
/*  4852: 4799 */           localObject5 = localLocalFrame.getResult();
/*  4853: 4800 */           if (localObject5 != null) {
/*  4854: 4802 */             localObject4 = (BPWPayeeList)ObjectVal.clone(localObject5);
/*  4855:      */           }
/*  4856: 4804 */           return localObject4;
/*  4857:      */         }
/*  4858: 4806 */         Object localObject4 = local_Request.getInputStream();
/*  4859:      */         
/*  4860: 4808 */         Object localObject5 = (BPWPayeeList)local_Request.read_value(BPWPayeeList.class);
/*  4861: 4809 */         return localObject5;
/*  4862:      */       }
/*  4863:      */       catch (TRANSIENT localTRANSIENT)
/*  4864:      */       {
/*  4865: 4813 */         if (i == 10) {
/*  4866: 4815 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4867:      */         }
/*  4868:      */       }
/*  4869:      */       catch (UserException localUserException)
/*  4870:      */       {
/*  4871: 4820 */         local_Request.isRMI();
/*  4872:      */         
/*  4873:      */ 
/*  4874: 4823 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4875:      */       }
/*  4876:      */       catch (SystemException localSystemException)
/*  4877:      */       {
/*  4878: 4827 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4879:      */       }
/*  4880:      */       finally
/*  4881:      */       {
/*  4882: 4831 */         if (local_Request != null) {
/*  4883: 4833 */           local_Request.close();
/*  4884:      */         }
/*  4885: 4835 */         if (bool1) {
/*  4886: 4836 */           localLocalStack.pop(localLocalFrame);
/*  4887:      */         }
/*  4888: 4837 */         localLocalStack.setArgsOnLocal(bool2);
/*  4889:      */       }
/*  4890:      */     }
/*  4891:      */   }
/*  4892:      */   
/*  4893:      */   public int addIntermediaryBanksToBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
/*  4894:      */     throws java.rmi.RemoteException
/*  4895:      */   {
/*  4896: 4847 */     for (int i = 1;; i++)
/*  4897:      */     {
/*  4898: 4849 */       _Request local_Request = null;
/*  4899: 4850 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4900: 4851 */       boolean bool1 = false;
/*  4901: 4852 */       LocalFrame localLocalFrame = null;
/*  4902: 4853 */       boolean bool2 = false;
/*  4903:      */       try
/*  4904:      */       {
/*  4905: 4856 */         local_Request = __request("addIntermediaryBanksToBeneficiary");
/*  4906: 4857 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4907: 4858 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4908: 4859 */         localLocalStack.setArgsOnLocal(bool1);
/*  4909: 4861 */         if (bool1)
/*  4910:      */         {
/*  4911: 4863 */           localLocalFrame = new LocalFrame(2);
/*  4912: 4864 */           localLocalStack.push(localLocalFrame);
/*  4913:      */         }
/*  4914:      */         OutputStream localOutputStream;
/*  4915: 4866 */         if (!bool1)
/*  4916:      */         {
/*  4917: 4868 */           localOutputStream = local_Request.getOutputStream();
/*  4918: 4869 */           local_Request.write_string(paramString);
/*  4919: 4870 */           if (local_Request.isRMI()) {
/*  4920: 4870 */             local_Request.write_value(paramArrayOfBPWBankInfo, new BPWBankInfo[0].getClass());
/*  4921:      */           } else {
/*  4922: 4870 */             BPWBankInfoSeqHelper.write(localOutputStream, paramArrayOfBPWBankInfo);
/*  4923:      */           }
/*  4924:      */         }
/*  4925:      */         else
/*  4926:      */         {
/*  4927: 4874 */           localOutputStream = local_Request.getOutputStream();
/*  4928: 4875 */           localLocalFrame.add(paramString);
/*  4929: 4876 */           localLocalFrame.add(paramArrayOfBPWBankInfo);
/*  4930:      */         }
/*  4931: 4878 */         local_Request.invoke();
/*  4932:      */         int j;
/*  4933: 4879 */         if (bool1)
/*  4934:      */         {
/*  4935: 4882 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  4936: 4883 */           return k;
/*  4937:      */         }
/*  4938: 4885 */         InputStream localInputStream = local_Request.getInputStream();
/*  4939:      */         
/*  4940: 4887 */         int m = localInputStream.read_long();
/*  4941: 4888 */         return m;
/*  4942:      */       }
/*  4943:      */       catch (TRANSIENT localTRANSIENT)
/*  4944:      */       {
/*  4945: 4892 */         if (i == 10) {
/*  4946: 4894 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  4947:      */         }
/*  4948:      */       }
/*  4949:      */       catch (UserException localUserException)
/*  4950:      */       {
/*  4951: 4899 */         local_Request.isRMI();
/*  4952:      */         
/*  4953:      */ 
/*  4954: 4902 */         throw new java.rmi.RemoteException(localUserException.type);
/*  4955:      */       }
/*  4956:      */       catch (SystemException localSystemException)
/*  4957:      */       {
/*  4958: 4906 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  4959:      */       }
/*  4960:      */       finally
/*  4961:      */       {
/*  4962: 4910 */         if (local_Request != null) {
/*  4963: 4912 */           local_Request.close();
/*  4964:      */         }
/*  4965: 4914 */         if (bool1) {
/*  4966: 4915 */           localLocalStack.pop(localLocalFrame);
/*  4967:      */         }
/*  4968: 4916 */         localLocalStack.setArgsOnLocal(bool2);
/*  4969:      */       }
/*  4970:      */     }
/*  4971:      */   }
/*  4972:      */   
/*  4973:      */   public int delIntermediaryBanksFromBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
/*  4974:      */     throws java.rmi.RemoteException
/*  4975:      */   {
/*  4976: 4926 */     for (int i = 1;; i++)
/*  4977:      */     {
/*  4978: 4928 */       _Request local_Request = null;
/*  4979: 4929 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  4980: 4930 */       boolean bool1 = false;
/*  4981: 4931 */       LocalFrame localLocalFrame = null;
/*  4982: 4932 */       boolean bool2 = false;
/*  4983:      */       try
/*  4984:      */       {
/*  4985: 4935 */         local_Request = __request("delIntermediaryBanksFromBeneficiary");
/*  4986: 4936 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  4987: 4937 */         bool2 = localLocalStack.isArgsOnLocal();
/*  4988: 4938 */         localLocalStack.setArgsOnLocal(bool1);
/*  4989: 4940 */         if (bool1)
/*  4990:      */         {
/*  4991: 4942 */           localLocalFrame = new LocalFrame(2);
/*  4992: 4943 */           localLocalStack.push(localLocalFrame);
/*  4993:      */         }
/*  4994:      */         OutputStream localOutputStream;
/*  4995: 4945 */         if (!bool1)
/*  4996:      */         {
/*  4997: 4947 */           localOutputStream = local_Request.getOutputStream();
/*  4998: 4948 */           local_Request.write_string(paramString);
/*  4999: 4949 */           if (local_Request.isRMI()) {
/*  5000: 4949 */             local_Request.write_value(paramArrayOfBPWBankInfo, new BPWBankInfo[0].getClass());
/*  5001:      */           } else {
/*  5002: 4949 */             BPWBankInfoSeqHelper.write(localOutputStream, paramArrayOfBPWBankInfo);
/*  5003:      */           }
/*  5004:      */         }
/*  5005:      */         else
/*  5006:      */         {
/*  5007: 4953 */           localOutputStream = local_Request.getOutputStream();
/*  5008: 4954 */           localLocalFrame.add(paramString);
/*  5009: 4955 */           localLocalFrame.add(paramArrayOfBPWBankInfo);
/*  5010:      */         }
/*  5011: 4957 */         local_Request.invoke();
/*  5012:      */         int j;
/*  5013: 4958 */         if (bool1)
/*  5014:      */         {
/*  5015: 4961 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  5016: 4962 */           return k;
/*  5017:      */         }
/*  5018: 4964 */         InputStream localInputStream = local_Request.getInputStream();
/*  5019:      */         
/*  5020: 4966 */         int m = localInputStream.read_long();
/*  5021: 4967 */         return m;
/*  5022:      */       }
/*  5023:      */       catch (TRANSIENT localTRANSIENT)
/*  5024:      */       {
/*  5025: 4971 */         if (i == 10) {
/*  5026: 4973 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5027:      */         }
/*  5028:      */       }
/*  5029:      */       catch (UserException localUserException)
/*  5030:      */       {
/*  5031: 4978 */         local_Request.isRMI();
/*  5032:      */         
/*  5033:      */ 
/*  5034: 4981 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5035:      */       }
/*  5036:      */       catch (SystemException localSystemException)
/*  5037:      */       {
/*  5038: 4985 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5039:      */       }
/*  5040:      */       finally
/*  5041:      */       {
/*  5042: 4989 */         if (local_Request != null) {
/*  5043: 4991 */           local_Request.close();
/*  5044:      */         }
/*  5045: 4993 */         if (bool1) {
/*  5046: 4994 */           localLocalStack.pop(localLocalFrame);
/*  5047:      */         }
/*  5048: 4995 */         localLocalStack.setArgsOnLocal(bool2);
/*  5049:      */       }
/*  5050:      */     }
/*  5051:      */   }
/*  5052:      */   
/*  5053:      */   public BPWBankInfo[] addWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
/*  5054:      */     throws java.rmi.RemoteException
/*  5055:      */   {
/*  5056: 5004 */     for (int i = 1;; i++)
/*  5057:      */     {
/*  5058: 5006 */       _Request local_Request = null;
/*  5059: 5007 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5060: 5008 */       boolean bool1 = false;
/*  5061: 5009 */       LocalFrame localLocalFrame = null;
/*  5062: 5010 */       boolean bool2 = false;
/*  5063:      */       try
/*  5064:      */       {
/*  5065: 5013 */         local_Request = __request("addWireBanks");
/*  5066: 5014 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5067: 5015 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5068: 5016 */         localLocalStack.setArgsOnLocal(bool1);
/*  5069: 5018 */         if (bool1)
/*  5070:      */         {
/*  5071: 5020 */           localLocalFrame = new LocalFrame(1);
/*  5072: 5021 */           localLocalStack.push(localLocalFrame);
/*  5073:      */         }
/*  5074: 5023 */         if (!bool1)
/*  5075:      */         {
/*  5076: 5025 */           localObject4 = local_Request.getOutputStream();
/*  5077: 5026 */           if (local_Request.isRMI()) {
/*  5078: 5026 */             local_Request.write_value(paramArrayOfBPWBankInfo, new BPWBankInfo[0].getClass());
/*  5079:      */           } else {
/*  5080: 5026 */             BPWBankInfoSeqHelper.write((OutputStream)localObject4, paramArrayOfBPWBankInfo);
/*  5081:      */           }
/*  5082:      */         }
/*  5083:      */         else
/*  5084:      */         {
/*  5085: 5030 */           localObject4 = local_Request.getOutputStream();
/*  5086: 5031 */           localLocalFrame.add(paramArrayOfBPWBankInfo);
/*  5087:      */         }
/*  5088: 5033 */         local_Request.invoke();
/*  5089:      */         Object localObject5;
/*  5090:      */         Object localObject1;
/*  5091: 5034 */         if (bool1)
/*  5092:      */         {
/*  5093: 5036 */           localObject4 = null;
/*  5094: 5037 */           localObject5 = localLocalFrame.getResult();
/*  5095: 5038 */           if (localObject5 != null) {
/*  5096: 5040 */             localObject4 = (BPWBankInfo[])ObjectVal.clone(localObject5);
/*  5097:      */           }
/*  5098: 5042 */           return localObject4;
/*  5099:      */         }
/*  5100: 5044 */         Object localObject4 = local_Request.getInputStream();
/*  5101: 5046 */         if (local_Request.isRMI()) {
/*  5102: 5046 */           localObject5 = (BPWBankInfo[])local_Request.read_value(new BPWBankInfo[0].getClass());
/*  5103:      */         } else {
/*  5104: 5046 */           localObject5 = BPWBankInfoSeqHelper.read((InputStream)localObject4);
/*  5105:      */         }
/*  5106: 5047 */         return localObject5;
/*  5107:      */       }
/*  5108:      */       catch (TRANSIENT localTRANSIENT)
/*  5109:      */       {
/*  5110: 5051 */         if (i == 10) {
/*  5111: 5053 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5112:      */         }
/*  5113:      */       }
/*  5114:      */       catch (UserException localUserException)
/*  5115:      */       {
/*  5116: 5058 */         local_Request.isRMI();
/*  5117:      */         
/*  5118:      */ 
/*  5119: 5061 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5120:      */       }
/*  5121:      */       catch (SystemException localSystemException)
/*  5122:      */       {
/*  5123: 5065 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5124:      */       }
/*  5125:      */       finally
/*  5126:      */       {
/*  5127: 5069 */         if (local_Request != null) {
/*  5128: 5071 */           local_Request.close();
/*  5129:      */         }
/*  5130: 5073 */         if (bool1) {
/*  5131: 5074 */           localLocalStack.pop(localLocalFrame);
/*  5132:      */         }
/*  5133: 5075 */         localLocalStack.setArgsOnLocal(bool2);
/*  5134:      */       }
/*  5135:      */     }
/*  5136:      */   }
/*  5137:      */   
/*  5138:      */   public BPWBankInfo[] modWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
/*  5139:      */     throws java.rmi.RemoteException
/*  5140:      */   {
/*  5141: 5084 */     for (int i = 1;; i++)
/*  5142:      */     {
/*  5143: 5086 */       _Request local_Request = null;
/*  5144: 5087 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5145: 5088 */       boolean bool1 = false;
/*  5146: 5089 */       LocalFrame localLocalFrame = null;
/*  5147: 5090 */       boolean bool2 = false;
/*  5148:      */       try
/*  5149:      */       {
/*  5150: 5093 */         local_Request = __request("modWireBanks");
/*  5151: 5094 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5152: 5095 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5153: 5096 */         localLocalStack.setArgsOnLocal(bool1);
/*  5154: 5098 */         if (bool1)
/*  5155:      */         {
/*  5156: 5100 */           localLocalFrame = new LocalFrame(1);
/*  5157: 5101 */           localLocalStack.push(localLocalFrame);
/*  5158:      */         }
/*  5159: 5103 */         if (!bool1)
/*  5160:      */         {
/*  5161: 5105 */           localObject4 = local_Request.getOutputStream();
/*  5162: 5106 */           if (local_Request.isRMI()) {
/*  5163: 5106 */             local_Request.write_value(paramArrayOfBPWBankInfo, new BPWBankInfo[0].getClass());
/*  5164:      */           } else {
/*  5165: 5106 */             BPWBankInfoSeqHelper.write((OutputStream)localObject4, paramArrayOfBPWBankInfo);
/*  5166:      */           }
/*  5167:      */         }
/*  5168:      */         else
/*  5169:      */         {
/*  5170: 5110 */           localObject4 = local_Request.getOutputStream();
/*  5171: 5111 */           localLocalFrame.add(paramArrayOfBPWBankInfo);
/*  5172:      */         }
/*  5173: 5113 */         local_Request.invoke();
/*  5174:      */         Object localObject5;
/*  5175:      */         Object localObject1;
/*  5176: 5114 */         if (bool1)
/*  5177:      */         {
/*  5178: 5116 */           localObject4 = null;
/*  5179: 5117 */           localObject5 = localLocalFrame.getResult();
/*  5180: 5118 */           if (localObject5 != null) {
/*  5181: 5120 */             localObject4 = (BPWBankInfo[])ObjectVal.clone(localObject5);
/*  5182:      */           }
/*  5183: 5122 */           return localObject4;
/*  5184:      */         }
/*  5185: 5124 */         Object localObject4 = local_Request.getInputStream();
/*  5186: 5126 */         if (local_Request.isRMI()) {
/*  5187: 5126 */           localObject5 = (BPWBankInfo[])local_Request.read_value(new BPWBankInfo[0].getClass());
/*  5188:      */         } else {
/*  5189: 5126 */           localObject5 = BPWBankInfoSeqHelper.read((InputStream)localObject4);
/*  5190:      */         }
/*  5191: 5127 */         return localObject5;
/*  5192:      */       }
/*  5193:      */       catch (TRANSIENT localTRANSIENT)
/*  5194:      */       {
/*  5195: 5131 */         if (i == 10) {
/*  5196: 5133 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5197:      */         }
/*  5198:      */       }
/*  5199:      */       catch (UserException localUserException)
/*  5200:      */       {
/*  5201: 5138 */         local_Request.isRMI();
/*  5202:      */         
/*  5203:      */ 
/*  5204: 5141 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5205:      */       }
/*  5206:      */       catch (SystemException localSystemException)
/*  5207:      */       {
/*  5208: 5145 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5209:      */       }
/*  5210:      */       finally
/*  5211:      */       {
/*  5212: 5149 */         if (local_Request != null) {
/*  5213: 5151 */           local_Request.close();
/*  5214:      */         }
/*  5215: 5153 */         if (bool1) {
/*  5216: 5154 */           localLocalStack.pop(localLocalFrame);
/*  5217:      */         }
/*  5218: 5155 */         localLocalStack.setArgsOnLocal(bool2);
/*  5219:      */       }
/*  5220:      */     }
/*  5221:      */   }
/*  5222:      */   
/*  5223:      */   public BPWBankInfo[] delWireBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
/*  5224:      */     throws java.rmi.RemoteException
/*  5225:      */   {
/*  5226: 5164 */     for (int i = 1;; i++)
/*  5227:      */     {
/*  5228: 5166 */       _Request local_Request = null;
/*  5229: 5167 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5230: 5168 */       boolean bool1 = false;
/*  5231: 5169 */       LocalFrame localLocalFrame = null;
/*  5232: 5170 */       boolean bool2 = false;
/*  5233:      */       try
/*  5234:      */       {
/*  5235: 5173 */         local_Request = __request("delWireBanks");
/*  5236: 5174 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5237: 5175 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5238: 5176 */         localLocalStack.setArgsOnLocal(bool1);
/*  5239: 5178 */         if (bool1)
/*  5240:      */         {
/*  5241: 5180 */           localLocalFrame = new LocalFrame(1);
/*  5242: 5181 */           localLocalStack.push(localLocalFrame);
/*  5243:      */         }
/*  5244: 5183 */         if (!bool1)
/*  5245:      */         {
/*  5246: 5185 */           localObject4 = local_Request.getOutputStream();
/*  5247: 5186 */           if (local_Request.isRMI()) {
/*  5248: 5186 */             local_Request.write_value(paramArrayOfBPWBankInfo, new BPWBankInfo[0].getClass());
/*  5249:      */           } else {
/*  5250: 5186 */             BPWBankInfoSeqHelper.write((OutputStream)localObject4, paramArrayOfBPWBankInfo);
/*  5251:      */           }
/*  5252:      */         }
/*  5253:      */         else
/*  5254:      */         {
/*  5255: 5190 */           localObject4 = local_Request.getOutputStream();
/*  5256: 5191 */           localLocalFrame.add(paramArrayOfBPWBankInfo);
/*  5257:      */         }
/*  5258: 5193 */         local_Request.invoke();
/*  5259:      */         Object localObject5;
/*  5260:      */         Object localObject1;
/*  5261: 5194 */         if (bool1)
/*  5262:      */         {
/*  5263: 5196 */           localObject4 = null;
/*  5264: 5197 */           localObject5 = localLocalFrame.getResult();
/*  5265: 5198 */           if (localObject5 != null) {
/*  5266: 5200 */             localObject4 = (BPWBankInfo[])ObjectVal.clone(localObject5);
/*  5267:      */           }
/*  5268: 5202 */           return localObject4;
/*  5269:      */         }
/*  5270: 5204 */         Object localObject4 = local_Request.getInputStream();
/*  5271: 5206 */         if (local_Request.isRMI()) {
/*  5272: 5206 */           localObject5 = (BPWBankInfo[])local_Request.read_value(new BPWBankInfo[0].getClass());
/*  5273:      */         } else {
/*  5274: 5206 */           localObject5 = BPWBankInfoSeqHelper.read((InputStream)localObject4);
/*  5275:      */         }
/*  5276: 5207 */         return localObject5;
/*  5277:      */       }
/*  5278:      */       catch (TRANSIENT localTRANSIENT)
/*  5279:      */       {
/*  5280: 5211 */         if (i == 10) {
/*  5281: 5213 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5282:      */         }
/*  5283:      */       }
/*  5284:      */       catch (UserException localUserException)
/*  5285:      */       {
/*  5286: 5218 */         local_Request.isRMI();
/*  5287:      */         
/*  5288:      */ 
/*  5289: 5221 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5290:      */       }
/*  5291:      */       catch (SystemException localSystemException)
/*  5292:      */       {
/*  5293: 5225 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5294:      */       }
/*  5295:      */       finally
/*  5296:      */       {
/*  5297: 5229 */         if (local_Request != null) {
/*  5298: 5231 */           local_Request.close();
/*  5299:      */         }
/*  5300: 5233 */         if (bool1) {
/*  5301: 5234 */           localLocalStack.pop(localLocalFrame);
/*  5302:      */         }
/*  5303: 5235 */         localLocalStack.setArgsOnLocal(bool2);
/*  5304:      */       }
/*  5305:      */     }
/*  5306:      */   }
/*  5307:      */   
/*  5308:      */   public BPWBankInfo[] getWireBanks(String paramString1, String paramString2, String paramString3, String paramString4)
/*  5309:      */     throws java.rmi.RemoteException
/*  5310:      */   {
/*  5311: 5247 */     for (int i = 1;; i++)
/*  5312:      */     {
/*  5313: 5249 */       _Request local_Request = null;
/*  5314: 5250 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5315: 5251 */       boolean bool1 = false;
/*  5316: 5252 */       LocalFrame localLocalFrame = null;
/*  5317: 5253 */       boolean bool2 = false;
/*  5318:      */       try
/*  5319:      */       {
/*  5320: 5256 */         local_Request = __request("getWireBanks");
/*  5321: 5257 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5322: 5258 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5323: 5259 */         localLocalStack.setArgsOnLocal(bool1);
/*  5324: 5261 */         if (bool1)
/*  5325:      */         {
/*  5326: 5263 */           localLocalFrame = new LocalFrame(4);
/*  5327: 5264 */           localLocalStack.push(localLocalFrame);
/*  5328:      */         }
/*  5329: 5266 */         if (!bool1)
/*  5330:      */         {
/*  5331: 5268 */           localObject4 = local_Request.getOutputStream();
/*  5332: 5269 */           local_Request.write_string(paramString1);
/*  5333: 5270 */           local_Request.write_string(paramString2);
/*  5334: 5271 */           local_Request.write_string(paramString3);
/*  5335: 5272 */           local_Request.write_string(paramString4);
/*  5336:      */         }
/*  5337:      */         else
/*  5338:      */         {
/*  5339: 5276 */           localObject4 = local_Request.getOutputStream();
/*  5340: 5277 */           localLocalFrame.add(paramString1);
/*  5341: 5278 */           localLocalFrame.add(paramString2);
/*  5342: 5279 */           localLocalFrame.add(paramString3);
/*  5343: 5280 */           localLocalFrame.add(paramString4);
/*  5344:      */         }
/*  5345: 5282 */         local_Request.invoke();
/*  5346:      */         Object localObject5;
/*  5347:      */         Object localObject1;
/*  5348: 5283 */         if (bool1)
/*  5349:      */         {
/*  5350: 5285 */           localObject4 = null;
/*  5351: 5286 */           localObject5 = localLocalFrame.getResult();
/*  5352: 5287 */           if (localObject5 != null) {
/*  5353: 5289 */             localObject4 = (BPWBankInfo[])ObjectVal.clone(localObject5);
/*  5354:      */           }
/*  5355: 5291 */           return localObject4;
/*  5356:      */         }
/*  5357: 5293 */         Object localObject4 = local_Request.getInputStream();
/*  5358: 5295 */         if (local_Request.isRMI()) {
/*  5359: 5295 */           localObject5 = (BPWBankInfo[])local_Request.read_value(new BPWBankInfo[0].getClass());
/*  5360:      */         } else {
/*  5361: 5295 */           localObject5 = BPWBankInfoSeqHelper.read((InputStream)localObject4);
/*  5362:      */         }
/*  5363: 5296 */         return localObject5;
/*  5364:      */       }
/*  5365:      */       catch (TRANSIENT localTRANSIENT)
/*  5366:      */       {
/*  5367: 5300 */         if (i == 10) {
/*  5368: 5302 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5369:      */         }
/*  5370:      */       }
/*  5371:      */       catch (UserException localUserException)
/*  5372:      */       {
/*  5373: 5307 */         local_Request.isRMI();
/*  5374:      */         
/*  5375:      */ 
/*  5376: 5310 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5377:      */       }
/*  5378:      */       catch (SystemException localSystemException)
/*  5379:      */       {
/*  5380: 5314 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5381:      */       }
/*  5382:      */       finally
/*  5383:      */       {
/*  5384: 5318 */         if (local_Request != null) {
/*  5385: 5320 */           local_Request.close();
/*  5386:      */         }
/*  5387: 5322 */         if (bool1) {
/*  5388: 5323 */           localLocalStack.pop(localLocalFrame);
/*  5389:      */         }
/*  5390: 5324 */         localLocalStack.setArgsOnLocal(bool2);
/*  5391:      */       }
/*  5392:      */     }
/*  5393:      */   }
/*  5394:      */   
/*  5395:      */   public BPWBankInfo[] getWireBanksByRTN(String paramString)
/*  5396:      */     throws java.rmi.RemoteException
/*  5397:      */   {
/*  5398: 5333 */     for (int i = 1;; i++)
/*  5399:      */     {
/*  5400: 5335 */       _Request local_Request = null;
/*  5401: 5336 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5402: 5337 */       boolean bool1 = false;
/*  5403: 5338 */       LocalFrame localLocalFrame = null;
/*  5404: 5339 */       boolean bool2 = false;
/*  5405:      */       try
/*  5406:      */       {
/*  5407: 5342 */         local_Request = __request("getWireBanksByRTN");
/*  5408: 5343 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5409: 5344 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5410: 5345 */         localLocalStack.setArgsOnLocal(bool1);
/*  5411: 5347 */         if (bool1)
/*  5412:      */         {
/*  5413: 5349 */           localLocalFrame = new LocalFrame(1);
/*  5414: 5350 */           localLocalStack.push(localLocalFrame);
/*  5415:      */         }
/*  5416: 5352 */         if (!bool1)
/*  5417:      */         {
/*  5418: 5354 */           localObject4 = local_Request.getOutputStream();
/*  5419: 5355 */           local_Request.write_string(paramString);
/*  5420:      */         }
/*  5421:      */         else
/*  5422:      */         {
/*  5423: 5359 */           localObject4 = local_Request.getOutputStream();
/*  5424: 5360 */           localLocalFrame.add(paramString);
/*  5425:      */         }
/*  5426: 5362 */         local_Request.invoke();
/*  5427:      */         Object localObject5;
/*  5428:      */         Object localObject1;
/*  5429: 5363 */         if (bool1)
/*  5430:      */         {
/*  5431: 5365 */           localObject4 = null;
/*  5432: 5366 */           localObject5 = localLocalFrame.getResult();
/*  5433: 5367 */           if (localObject5 != null) {
/*  5434: 5369 */             localObject4 = (BPWBankInfo[])ObjectVal.clone(localObject5);
/*  5435:      */           }
/*  5436: 5371 */           return localObject4;
/*  5437:      */         }
/*  5438: 5373 */         Object localObject4 = local_Request.getInputStream();
/*  5439: 5375 */         if (local_Request.isRMI()) {
/*  5440: 5375 */           localObject5 = (BPWBankInfo[])local_Request.read_value(new BPWBankInfo[0].getClass());
/*  5441:      */         } else {
/*  5442: 5375 */           localObject5 = BPWBankInfoSeqHelper.read((InputStream)localObject4);
/*  5443:      */         }
/*  5444: 5376 */         return localObject5;
/*  5445:      */       }
/*  5446:      */       catch (TRANSIENT localTRANSIENT)
/*  5447:      */       {
/*  5448: 5380 */         if (i == 10) {
/*  5449: 5382 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5450:      */         }
/*  5451:      */       }
/*  5452:      */       catch (UserException localUserException)
/*  5453:      */       {
/*  5454: 5387 */         local_Request.isRMI();
/*  5455:      */         
/*  5456:      */ 
/*  5457: 5390 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5458:      */       }
/*  5459:      */       catch (SystemException localSystemException)
/*  5460:      */       {
/*  5461: 5394 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5462:      */       }
/*  5463:      */       finally
/*  5464:      */       {
/*  5465: 5398 */         if (local_Request != null) {
/*  5466: 5400 */           local_Request.close();
/*  5467:      */         }
/*  5468: 5402 */         if (bool1) {
/*  5469: 5403 */           localLocalStack.pop(localLocalFrame);
/*  5470:      */         }
/*  5471: 5404 */         localLocalStack.setArgsOnLocal(bool2);
/*  5472:      */       }
/*  5473:      */     }
/*  5474:      */   }
/*  5475:      */   
/*  5476:      */   public BPWBankInfo getWireBanksByID(String paramString)
/*  5477:      */     throws java.rmi.RemoteException
/*  5478:      */   {
/*  5479: 5413 */     for (int i = 1;; i++)
/*  5480:      */     {
/*  5481: 5415 */       _Request local_Request = null;
/*  5482: 5416 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5483: 5417 */       boolean bool1 = false;
/*  5484: 5418 */       LocalFrame localLocalFrame = null;
/*  5485: 5419 */       boolean bool2 = false;
/*  5486:      */       try
/*  5487:      */       {
/*  5488: 5422 */         local_Request = __request("getWireBanksByID");
/*  5489: 5423 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5490: 5424 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5491: 5425 */         localLocalStack.setArgsOnLocal(bool1);
/*  5492: 5427 */         if (bool1)
/*  5493:      */         {
/*  5494: 5429 */           localLocalFrame = new LocalFrame(1);
/*  5495: 5430 */           localLocalStack.push(localLocalFrame);
/*  5496:      */         }
/*  5497: 5432 */         if (!bool1)
/*  5498:      */         {
/*  5499: 5434 */           localObject4 = local_Request.getOutputStream();
/*  5500: 5435 */           local_Request.write_string(paramString);
/*  5501:      */         }
/*  5502:      */         else
/*  5503:      */         {
/*  5504: 5439 */           localObject4 = local_Request.getOutputStream();
/*  5505: 5440 */           localLocalFrame.add(paramString);
/*  5506:      */         }
/*  5507: 5442 */         local_Request.invoke();
/*  5508:      */         Object localObject1;
/*  5509: 5443 */         if (bool1)
/*  5510:      */         {
/*  5511: 5445 */           localObject4 = null;
/*  5512: 5446 */           localObject5 = localLocalFrame.getResult();
/*  5513: 5447 */           if (localObject5 != null) {
/*  5514: 5449 */             localObject4 = (BPWBankInfo)ObjectVal.clone(localObject5);
/*  5515:      */           }
/*  5516: 5451 */           return localObject4;
/*  5517:      */         }
/*  5518: 5453 */         Object localObject4 = local_Request.getInputStream();
/*  5519:      */         
/*  5520: 5455 */         Object localObject5 = (BPWBankInfo)local_Request.read_value(BPWBankInfo.class);
/*  5521: 5456 */         return localObject5;
/*  5522:      */       }
/*  5523:      */       catch (TRANSIENT localTRANSIENT)
/*  5524:      */       {
/*  5525: 5460 */         if (i == 10) {
/*  5526: 5462 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5527:      */         }
/*  5528:      */       }
/*  5529:      */       catch (UserException localUserException)
/*  5530:      */       {
/*  5531: 5467 */         local_Request.isRMI();
/*  5532:      */         
/*  5533:      */ 
/*  5534: 5470 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5535:      */       }
/*  5536:      */       catch (SystemException localSystemException)
/*  5537:      */       {
/*  5538: 5474 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5539:      */       }
/*  5540:      */       finally
/*  5541:      */       {
/*  5542: 5478 */         if (local_Request != null) {
/*  5543: 5480 */           local_Request.close();
/*  5544:      */         }
/*  5545: 5482 */         if (bool1) {
/*  5546: 5483 */           localLocalStack.pop(localLocalFrame);
/*  5547:      */         }
/*  5548: 5484 */         localLocalStack.setArgsOnLocal(bool2);
/*  5549:      */       }
/*  5550:      */     }
/*  5551:      */   }
/*  5552:      */   
/*  5553:      */   public void processWireApprovalRslt(WireInfo[] paramArrayOfWireInfo)
/*  5554:      */     throws java.rmi.RemoteException
/*  5555:      */   {
/*  5556: 5493 */     for (int i = 1;; i++)
/*  5557:      */     {
/*  5558: 5495 */       _Request local_Request = null;
/*  5559: 5496 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5560: 5497 */       boolean bool1 = false;
/*  5561: 5498 */       LocalFrame localLocalFrame = null;
/*  5562: 5499 */       boolean bool2 = false;
/*  5563:      */       try
/*  5564:      */       {
/*  5565: 5502 */         local_Request = __request("processWireApprovalRslt");
/*  5566: 5503 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5567: 5504 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5568: 5505 */         localLocalStack.setArgsOnLocal(bool1);
/*  5569: 5507 */         if (bool1)
/*  5570:      */         {
/*  5571: 5509 */           localLocalFrame = new LocalFrame(1);
/*  5572: 5510 */           localLocalStack.push(localLocalFrame);
/*  5573:      */         }
/*  5574:      */         OutputStream localOutputStream;
/*  5575: 5512 */         if (!bool1)
/*  5576:      */         {
/*  5577: 5514 */           localOutputStream = local_Request.getOutputStream();
/*  5578: 5515 */           if (local_Request.isRMI()) {
/*  5579: 5515 */             local_Request.write_value(paramArrayOfWireInfo, new WireInfo[0].getClass());
/*  5580:      */           } else {
/*  5581: 5515 */             WireInfoSeqHelper.write(localOutputStream, paramArrayOfWireInfo);
/*  5582:      */           }
/*  5583:      */         }
/*  5584:      */         else
/*  5585:      */         {
/*  5586: 5519 */           localOutputStream = local_Request.getOutputStream();
/*  5587: 5520 */           localLocalFrame.add(paramArrayOfWireInfo);
/*  5588:      */         }
/*  5589: 5522 */         local_Request.invoke();
/*  5590: 5523 */         return;
/*  5591:      */       }
/*  5592:      */       catch (TRANSIENT localTRANSIENT)
/*  5593:      */       {
/*  5594: 5527 */         if (i == 10) {
/*  5595: 5529 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5596:      */         }
/*  5597:      */       }
/*  5598:      */       catch (UserException localUserException)
/*  5599:      */       {
/*  5600: 5534 */         local_Request.isRMI();
/*  5601:      */         
/*  5602:      */ 
/*  5603: 5537 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5604:      */       }
/*  5605:      */       catch (SystemException localSystemException)
/*  5606:      */       {
/*  5607: 5541 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5608:      */       }
/*  5609:      */       finally
/*  5610:      */       {
/*  5611: 5545 */         if (local_Request != null) {
/*  5612: 5547 */           local_Request.close();
/*  5613:      */         }
/*  5614: 5549 */         if (bool1) {
/*  5615: 5550 */           localLocalStack.pop(localLocalFrame);
/*  5616:      */         }
/*  5617: 5551 */         localLocalStack.setArgsOnLocal(bool2);
/*  5618:      */       }
/*  5619:      */     }
/*  5620:      */   }
/*  5621:      */   
/*  5622:      */   public void processWireBackendlRslt(WireInfo[] paramArrayOfWireInfo)
/*  5623:      */     throws java.rmi.RemoteException
/*  5624:      */   {
/*  5625: 5560 */     for (int i = 1;; i++)
/*  5626:      */     {
/*  5627: 5562 */       _Request local_Request = null;
/*  5628: 5563 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5629: 5564 */       boolean bool1 = false;
/*  5630: 5565 */       LocalFrame localLocalFrame = null;
/*  5631: 5566 */       boolean bool2 = false;
/*  5632:      */       try
/*  5633:      */       {
/*  5634: 5569 */         local_Request = __request("processWireBackendlRslt");
/*  5635: 5570 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5636: 5571 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5637: 5572 */         localLocalStack.setArgsOnLocal(bool1);
/*  5638: 5574 */         if (bool1)
/*  5639:      */         {
/*  5640: 5576 */           localLocalFrame = new LocalFrame(1);
/*  5641: 5577 */           localLocalStack.push(localLocalFrame);
/*  5642:      */         }
/*  5643:      */         OutputStream localOutputStream;
/*  5644: 5579 */         if (!bool1)
/*  5645:      */         {
/*  5646: 5581 */           localOutputStream = local_Request.getOutputStream();
/*  5647: 5582 */           if (local_Request.isRMI()) {
/*  5648: 5582 */             local_Request.write_value(paramArrayOfWireInfo, new WireInfo[0].getClass());
/*  5649:      */           } else {
/*  5650: 5582 */             WireInfoSeqHelper.write(localOutputStream, paramArrayOfWireInfo);
/*  5651:      */           }
/*  5652:      */         }
/*  5653:      */         else
/*  5654:      */         {
/*  5655: 5586 */           localOutputStream = local_Request.getOutputStream();
/*  5656: 5587 */           localLocalFrame.add(paramArrayOfWireInfo);
/*  5657:      */         }
/*  5658: 5589 */         local_Request.invoke();
/*  5659: 5590 */         return;
/*  5660:      */       }
/*  5661:      */       catch (TRANSIENT localTRANSIENT)
/*  5662:      */       {
/*  5663: 5594 */         if (i == 10) {
/*  5664: 5596 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5665:      */         }
/*  5666:      */       }
/*  5667:      */       catch (UserException localUserException)
/*  5668:      */       {
/*  5669: 5601 */         local_Request.isRMI();
/*  5670:      */         
/*  5671:      */ 
/*  5672: 5604 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5673:      */       }
/*  5674:      */       catch (SystemException localSystemException)
/*  5675:      */       {
/*  5676: 5608 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5677:      */       }
/*  5678:      */       finally
/*  5679:      */       {
/*  5680: 5612 */         if (local_Request != null) {
/*  5681: 5614 */           local_Request.close();
/*  5682:      */         }
/*  5683: 5616 */         if (bool1) {
/*  5684: 5617 */           localLocalStack.pop(localLocalFrame);
/*  5685:      */         }
/*  5686: 5618 */         localLocalStack.setArgsOnLocal(bool2);
/*  5687:      */       }
/*  5688:      */     }
/*  5689:      */   }
/*  5690:      */   
/*  5691:      */   public void processWireApprovalRevertRslt(WireInfo[] paramArrayOfWireInfo)
/*  5692:      */     throws java.rmi.RemoteException
/*  5693:      */   {
/*  5694: 5627 */     for (int i = 1;; i++)
/*  5695:      */     {
/*  5696: 5629 */       _Request local_Request = null;
/*  5697: 5630 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5698: 5631 */       boolean bool1 = false;
/*  5699: 5632 */       LocalFrame localLocalFrame = null;
/*  5700: 5633 */       boolean bool2 = false;
/*  5701:      */       try
/*  5702:      */       {
/*  5703: 5636 */         local_Request = __request("processWireApprovalRevertRslt");
/*  5704: 5637 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5705: 5638 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5706: 5639 */         localLocalStack.setArgsOnLocal(bool1);
/*  5707: 5641 */         if (bool1)
/*  5708:      */         {
/*  5709: 5643 */           localLocalFrame = new LocalFrame(1);
/*  5710: 5644 */           localLocalStack.push(localLocalFrame);
/*  5711:      */         }
/*  5712:      */         OutputStream localOutputStream;
/*  5713: 5646 */         if (!bool1)
/*  5714:      */         {
/*  5715: 5648 */           localOutputStream = local_Request.getOutputStream();
/*  5716: 5649 */           if (local_Request.isRMI()) {
/*  5717: 5649 */             local_Request.write_value(paramArrayOfWireInfo, new WireInfo[0].getClass());
/*  5718:      */           } else {
/*  5719: 5649 */             WireInfoSeqHelper.write(localOutputStream, paramArrayOfWireInfo);
/*  5720:      */           }
/*  5721:      */         }
/*  5722:      */         else
/*  5723:      */         {
/*  5724: 5653 */           localOutputStream = local_Request.getOutputStream();
/*  5725: 5654 */           localLocalFrame.add(paramArrayOfWireInfo);
/*  5726:      */         }
/*  5727: 5656 */         local_Request.invoke();
/*  5728: 5657 */         return;
/*  5729:      */       }
/*  5730:      */       catch (TRANSIENT localTRANSIENT)
/*  5731:      */       {
/*  5732: 5661 */         if (i == 10) {
/*  5733: 5663 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5734:      */         }
/*  5735:      */       }
/*  5736:      */       catch (UserException localUserException)
/*  5737:      */       {
/*  5738: 5668 */         local_Request.isRMI();
/*  5739:      */         
/*  5740:      */ 
/*  5741: 5671 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5742:      */       }
/*  5743:      */       catch (SystemException localSystemException)
/*  5744:      */       {
/*  5745: 5675 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5746:      */       }
/*  5747:      */       finally
/*  5748:      */       {
/*  5749: 5679 */         if (local_Request != null) {
/*  5750: 5681 */           local_Request.close();
/*  5751:      */         }
/*  5752: 5683 */         if (bool1) {
/*  5753: 5684 */           localLocalStack.pop(localLocalFrame);
/*  5754:      */         }
/*  5755: 5685 */         localLocalStack.setArgsOnLocal(bool2);
/*  5756:      */       }
/*  5757:      */     }
/*  5758:      */   }
/*  5759:      */   
/*  5760:      */   public BPWFIInfo addBPWFIInfo(BPWFIInfo paramBPWFIInfo)
/*  5761:      */     throws java.rmi.RemoteException
/*  5762:      */   {
/*  5763: 5694 */     for (int i = 1;; i++)
/*  5764:      */     {
/*  5765: 5696 */       _Request local_Request = null;
/*  5766: 5697 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5767: 5698 */       boolean bool1 = false;
/*  5768: 5699 */       LocalFrame localLocalFrame = null;
/*  5769: 5700 */       boolean bool2 = false;
/*  5770:      */       try
/*  5771:      */       {
/*  5772: 5703 */         local_Request = __request("addBPWFIInfo");
/*  5773: 5704 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5774: 5705 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5775: 5706 */         localLocalStack.setArgsOnLocal(bool1);
/*  5776: 5708 */         if (bool1)
/*  5777:      */         {
/*  5778: 5710 */           localLocalFrame = new LocalFrame(1);
/*  5779: 5711 */           localLocalStack.push(localLocalFrame);
/*  5780:      */         }
/*  5781: 5713 */         if (!bool1)
/*  5782:      */         {
/*  5783: 5715 */           localObject4 = local_Request.getOutputStream();
/*  5784: 5716 */           local_Request.write_value(paramBPWFIInfo, BPWFIInfo.class);
/*  5785:      */         }
/*  5786:      */         else
/*  5787:      */         {
/*  5788: 5720 */           localObject4 = local_Request.getOutputStream();
/*  5789: 5721 */           localLocalFrame.add(paramBPWFIInfo);
/*  5790:      */         }
/*  5791: 5723 */         local_Request.invoke();
/*  5792:      */         Object localObject1;
/*  5793: 5724 */         if (bool1)
/*  5794:      */         {
/*  5795: 5726 */           localObject4 = null;
/*  5796: 5727 */           localObject5 = localLocalFrame.getResult();
/*  5797: 5728 */           if (localObject5 != null) {
/*  5798: 5730 */             localObject4 = (BPWFIInfo)ObjectVal.clone(localObject5);
/*  5799:      */           }
/*  5800: 5732 */           return localObject4;
/*  5801:      */         }
/*  5802: 5734 */         Object localObject4 = local_Request.getInputStream();
/*  5803:      */         
/*  5804: 5736 */         Object localObject5 = (BPWFIInfo)local_Request.read_value(BPWFIInfo.class);
/*  5805: 5737 */         return localObject5;
/*  5806:      */       }
/*  5807:      */       catch (TRANSIENT localTRANSIENT)
/*  5808:      */       {
/*  5809: 5741 */         if (i == 10) {
/*  5810: 5743 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5811:      */         }
/*  5812:      */       }
/*  5813:      */       catch (UserException localUserException)
/*  5814:      */       {
/*  5815: 5748 */         local_Request.isRMI();
/*  5816:      */         
/*  5817:      */ 
/*  5818: 5751 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5819:      */       }
/*  5820:      */       catch (SystemException localSystemException)
/*  5821:      */       {
/*  5822: 5755 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5823:      */       }
/*  5824:      */       finally
/*  5825:      */       {
/*  5826: 5759 */         if (local_Request != null) {
/*  5827: 5761 */           local_Request.close();
/*  5828:      */         }
/*  5829: 5763 */         if (bool1) {
/*  5830: 5764 */           localLocalStack.pop(localLocalFrame);
/*  5831:      */         }
/*  5832: 5765 */         localLocalStack.setArgsOnLocal(bool2);
/*  5833:      */       }
/*  5834:      */     }
/*  5835:      */   }
/*  5836:      */   
/*  5837:      */   public BPWFIInfo modBPWFIInfo(BPWFIInfo paramBPWFIInfo)
/*  5838:      */     throws java.rmi.RemoteException
/*  5839:      */   {
/*  5840: 5774 */     for (int i = 1;; i++)
/*  5841:      */     {
/*  5842: 5776 */       _Request local_Request = null;
/*  5843: 5777 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5844: 5778 */       boolean bool1 = false;
/*  5845: 5779 */       LocalFrame localLocalFrame = null;
/*  5846: 5780 */       boolean bool2 = false;
/*  5847:      */       try
/*  5848:      */       {
/*  5849: 5783 */         local_Request = __request("modBPWFIInfo");
/*  5850: 5784 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5851: 5785 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5852: 5786 */         localLocalStack.setArgsOnLocal(bool1);
/*  5853: 5788 */         if (bool1)
/*  5854:      */         {
/*  5855: 5790 */           localLocalFrame = new LocalFrame(1);
/*  5856: 5791 */           localLocalStack.push(localLocalFrame);
/*  5857:      */         }
/*  5858: 5793 */         if (!bool1)
/*  5859:      */         {
/*  5860: 5795 */           localObject4 = local_Request.getOutputStream();
/*  5861: 5796 */           local_Request.write_value(paramBPWFIInfo, BPWFIInfo.class);
/*  5862:      */         }
/*  5863:      */         else
/*  5864:      */         {
/*  5865: 5800 */           localObject4 = local_Request.getOutputStream();
/*  5866: 5801 */           localLocalFrame.add(paramBPWFIInfo);
/*  5867:      */         }
/*  5868: 5803 */         local_Request.invoke();
/*  5869:      */         Object localObject1;
/*  5870: 5804 */         if (bool1)
/*  5871:      */         {
/*  5872: 5806 */           localObject4 = null;
/*  5873: 5807 */           localObject5 = localLocalFrame.getResult();
/*  5874: 5808 */           if (localObject5 != null) {
/*  5875: 5810 */             localObject4 = (BPWFIInfo)ObjectVal.clone(localObject5);
/*  5876:      */           }
/*  5877: 5812 */           return localObject4;
/*  5878:      */         }
/*  5879: 5814 */         Object localObject4 = local_Request.getInputStream();
/*  5880:      */         
/*  5881: 5816 */         Object localObject5 = (BPWFIInfo)local_Request.read_value(BPWFIInfo.class);
/*  5882: 5817 */         return localObject5;
/*  5883:      */       }
/*  5884:      */       catch (TRANSIENT localTRANSIENT)
/*  5885:      */       {
/*  5886: 5821 */         if (i == 10) {
/*  5887: 5823 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5888:      */         }
/*  5889:      */       }
/*  5890:      */       catch (UserException localUserException)
/*  5891:      */       {
/*  5892: 5828 */         local_Request.isRMI();
/*  5893:      */         
/*  5894:      */ 
/*  5895: 5831 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5896:      */       }
/*  5897:      */       catch (SystemException localSystemException)
/*  5898:      */       {
/*  5899: 5835 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5900:      */       }
/*  5901:      */       finally
/*  5902:      */       {
/*  5903: 5839 */         if (local_Request != null) {
/*  5904: 5841 */           local_Request.close();
/*  5905:      */         }
/*  5906: 5843 */         if (bool1) {
/*  5907: 5844 */           localLocalStack.pop(localLocalFrame);
/*  5908:      */         }
/*  5909: 5845 */         localLocalStack.setArgsOnLocal(bool2);
/*  5910:      */       }
/*  5911:      */     }
/*  5912:      */   }
/*  5913:      */   
/*  5914:      */   public BPWFIInfo canBPWFIInfo(BPWFIInfo paramBPWFIInfo)
/*  5915:      */     throws java.rmi.RemoteException
/*  5916:      */   {
/*  5917: 5854 */     for (int i = 1;; i++)
/*  5918:      */     {
/*  5919: 5856 */       _Request local_Request = null;
/*  5920: 5857 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5921: 5858 */       boolean bool1 = false;
/*  5922: 5859 */       LocalFrame localLocalFrame = null;
/*  5923: 5860 */       boolean bool2 = false;
/*  5924:      */       try
/*  5925:      */       {
/*  5926: 5863 */         local_Request = __request("canBPWFIInfo");
/*  5927: 5864 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  5928: 5865 */         bool2 = localLocalStack.isArgsOnLocal();
/*  5929: 5866 */         localLocalStack.setArgsOnLocal(bool1);
/*  5930: 5868 */         if (bool1)
/*  5931:      */         {
/*  5932: 5870 */           localLocalFrame = new LocalFrame(1);
/*  5933: 5871 */           localLocalStack.push(localLocalFrame);
/*  5934:      */         }
/*  5935: 5873 */         if (!bool1)
/*  5936:      */         {
/*  5937: 5875 */           localObject4 = local_Request.getOutputStream();
/*  5938: 5876 */           local_Request.write_value(paramBPWFIInfo, BPWFIInfo.class);
/*  5939:      */         }
/*  5940:      */         else
/*  5941:      */         {
/*  5942: 5880 */           localObject4 = local_Request.getOutputStream();
/*  5943: 5881 */           localLocalFrame.add(paramBPWFIInfo);
/*  5944:      */         }
/*  5945: 5883 */         local_Request.invoke();
/*  5946:      */         Object localObject1;
/*  5947: 5884 */         if (bool1)
/*  5948:      */         {
/*  5949: 5886 */           localObject4 = null;
/*  5950: 5887 */           localObject5 = localLocalFrame.getResult();
/*  5951: 5888 */           if (localObject5 != null) {
/*  5952: 5890 */             localObject4 = (BPWFIInfo)ObjectVal.clone(localObject5);
/*  5953:      */           }
/*  5954: 5892 */           return localObject4;
/*  5955:      */         }
/*  5956: 5894 */         Object localObject4 = local_Request.getInputStream();
/*  5957:      */         
/*  5958: 5896 */         Object localObject5 = (BPWFIInfo)local_Request.read_value(BPWFIInfo.class);
/*  5959: 5897 */         return localObject5;
/*  5960:      */       }
/*  5961:      */       catch (TRANSIENT localTRANSIENT)
/*  5962:      */       {
/*  5963: 5901 */         if (i == 10) {
/*  5964: 5903 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  5965:      */         }
/*  5966:      */       }
/*  5967:      */       catch (UserException localUserException)
/*  5968:      */       {
/*  5969: 5908 */         local_Request.isRMI();
/*  5970:      */         
/*  5971:      */ 
/*  5972: 5911 */         throw new java.rmi.RemoteException(localUserException.type);
/*  5973:      */       }
/*  5974:      */       catch (SystemException localSystemException)
/*  5975:      */       {
/*  5976: 5915 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  5977:      */       }
/*  5978:      */       finally
/*  5979:      */       {
/*  5980: 5919 */         if (local_Request != null) {
/*  5981: 5921 */           local_Request.close();
/*  5982:      */         }
/*  5983: 5923 */         if (bool1) {
/*  5984: 5924 */           localLocalStack.pop(localLocalFrame);
/*  5985:      */         }
/*  5986: 5925 */         localLocalStack.setArgsOnLocal(bool2);
/*  5987:      */       }
/*  5988:      */     }
/*  5989:      */   }
/*  5990:      */   
/*  5991:      */   public BPWFIInfo activateBPWFI(String paramString)
/*  5992:      */     throws java.rmi.RemoteException
/*  5993:      */   {
/*  5994: 5934 */     for (int i = 1;; i++)
/*  5995:      */     {
/*  5996: 5936 */       _Request local_Request = null;
/*  5997: 5937 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  5998: 5938 */       boolean bool1 = false;
/*  5999: 5939 */       LocalFrame localLocalFrame = null;
/*  6000: 5940 */       boolean bool2 = false;
/*  6001:      */       try
/*  6002:      */       {
/*  6003: 5943 */         local_Request = __request("activateBPWFI");
/*  6004: 5944 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6005: 5945 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6006: 5946 */         localLocalStack.setArgsOnLocal(bool1);
/*  6007: 5948 */         if (bool1)
/*  6008:      */         {
/*  6009: 5950 */           localLocalFrame = new LocalFrame(1);
/*  6010: 5951 */           localLocalStack.push(localLocalFrame);
/*  6011:      */         }
/*  6012: 5953 */         if (!bool1)
/*  6013:      */         {
/*  6014: 5955 */           localObject4 = local_Request.getOutputStream();
/*  6015: 5956 */           local_Request.write_string(paramString);
/*  6016:      */         }
/*  6017:      */         else
/*  6018:      */         {
/*  6019: 5960 */           localObject4 = local_Request.getOutputStream();
/*  6020: 5961 */           localLocalFrame.add(paramString);
/*  6021:      */         }
/*  6022: 5963 */         local_Request.invoke();
/*  6023:      */         Object localObject1;
/*  6024: 5964 */         if (bool1)
/*  6025:      */         {
/*  6026: 5966 */           localObject4 = null;
/*  6027: 5967 */           localObject5 = localLocalFrame.getResult();
/*  6028: 5968 */           if (localObject5 != null) {
/*  6029: 5970 */             localObject4 = (BPWFIInfo)ObjectVal.clone(localObject5);
/*  6030:      */           }
/*  6031: 5972 */           return localObject4;
/*  6032:      */         }
/*  6033: 5974 */         Object localObject4 = local_Request.getInputStream();
/*  6034:      */         
/*  6035: 5976 */         Object localObject5 = (BPWFIInfo)local_Request.read_value(BPWFIInfo.class);
/*  6036: 5977 */         return localObject5;
/*  6037:      */       }
/*  6038:      */       catch (TRANSIENT localTRANSIENT)
/*  6039:      */       {
/*  6040: 5981 */         if (i == 10) {
/*  6041: 5983 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6042:      */         }
/*  6043:      */       }
/*  6044:      */       catch (UserException localUserException)
/*  6045:      */       {
/*  6046: 5988 */         local_Request.isRMI();
/*  6047:      */         
/*  6048:      */ 
/*  6049: 5991 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6050:      */       }
/*  6051:      */       catch (SystemException localSystemException)
/*  6052:      */       {
/*  6053: 5995 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6054:      */       }
/*  6055:      */       finally
/*  6056:      */       {
/*  6057: 5999 */         if (local_Request != null) {
/*  6058: 6001 */           local_Request.close();
/*  6059:      */         }
/*  6060: 6003 */         if (bool1) {
/*  6061: 6004 */           localLocalStack.pop(localLocalFrame);
/*  6062:      */         }
/*  6063: 6005 */         localLocalStack.setArgsOnLocal(bool2);
/*  6064:      */       }
/*  6065:      */     }
/*  6066:      */   }
/*  6067:      */   
/*  6068:      */   public BPWFIInfo getBPWFIInfo(String paramString)
/*  6069:      */     throws java.rmi.RemoteException
/*  6070:      */   {
/*  6071: 6014 */     for (int i = 1;; i++)
/*  6072:      */     {
/*  6073: 6016 */       _Request local_Request = null;
/*  6074: 6017 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6075: 6018 */       boolean bool1 = false;
/*  6076: 6019 */       LocalFrame localLocalFrame = null;
/*  6077: 6020 */       boolean bool2 = false;
/*  6078:      */       try
/*  6079:      */       {
/*  6080: 6023 */         local_Request = __request("getBPWFIInfo__string", "getBPWFIInfo__CORBA_WStringValue");
/*  6081: 6024 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6082: 6025 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6083: 6026 */         localLocalStack.setArgsOnLocal(bool1);
/*  6084: 6028 */         if (bool1)
/*  6085:      */         {
/*  6086: 6030 */           localLocalFrame = new LocalFrame(1);
/*  6087: 6031 */           localLocalStack.push(localLocalFrame);
/*  6088:      */         }
/*  6089: 6033 */         if (!bool1)
/*  6090:      */         {
/*  6091: 6035 */           localObject4 = local_Request.getOutputStream();
/*  6092: 6036 */           local_Request.write_string(paramString);
/*  6093:      */         }
/*  6094:      */         else
/*  6095:      */         {
/*  6096: 6040 */           localObject4 = local_Request.getOutputStream();
/*  6097: 6041 */           localLocalFrame.add(paramString);
/*  6098:      */         }
/*  6099: 6043 */         local_Request.invoke();
/*  6100:      */         Object localObject1;
/*  6101: 6044 */         if (bool1)
/*  6102:      */         {
/*  6103: 6046 */           localObject4 = null;
/*  6104: 6047 */           localObject5 = localLocalFrame.getResult();
/*  6105: 6048 */           if (localObject5 != null) {
/*  6106: 6050 */             localObject4 = (BPWFIInfo)ObjectVal.clone(localObject5);
/*  6107:      */           }
/*  6108: 6052 */           return localObject4;
/*  6109:      */         }
/*  6110: 6054 */         Object localObject4 = local_Request.getInputStream();
/*  6111:      */         
/*  6112: 6056 */         Object localObject5 = (BPWFIInfo)local_Request.read_value(BPWFIInfo.class);
/*  6113: 6057 */         return localObject5;
/*  6114:      */       }
/*  6115:      */       catch (TRANSIENT localTRANSIENT)
/*  6116:      */       {
/*  6117: 6061 */         if (i == 10) {
/*  6118: 6063 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6119:      */         }
/*  6120:      */       }
/*  6121:      */       catch (UserException localUserException)
/*  6122:      */       {
/*  6123: 6068 */         local_Request.isRMI();
/*  6124:      */         
/*  6125:      */ 
/*  6126: 6071 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6127:      */       }
/*  6128:      */       catch (SystemException localSystemException)
/*  6129:      */       {
/*  6130: 6075 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6131:      */       }
/*  6132:      */       finally
/*  6133:      */       {
/*  6134: 6079 */         if (local_Request != null) {
/*  6135: 6081 */           local_Request.close();
/*  6136:      */         }
/*  6137: 6083 */         if (bool1) {
/*  6138: 6084 */           localLocalStack.pop(localLocalFrame);
/*  6139:      */         }
/*  6140: 6085 */         localLocalStack.setArgsOnLocal(bool2);
/*  6141:      */       }
/*  6142:      */     }
/*  6143:      */   }
/*  6144:      */   
/*  6145:      */   public BPWFIInfo[] getBPWFIInfo(String[] paramArrayOfString)
/*  6146:      */     throws java.rmi.RemoteException
/*  6147:      */   {
/*  6148: 6094 */     for (int i = 1;; i++)
/*  6149:      */     {
/*  6150: 6096 */       _Request local_Request = null;
/*  6151: 6097 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6152: 6098 */       boolean bool1 = false;
/*  6153: 6099 */       LocalFrame localLocalFrame = null;
/*  6154: 6100 */       boolean bool2 = false;
/*  6155:      */       try
/*  6156:      */       {
/*  6157: 6103 */         local_Request = __request("getBPWFIInfo__StringSeq", "getBPWFIInfo__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/*  6158: 6104 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6159: 6105 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6160: 6106 */         localLocalStack.setArgsOnLocal(bool1);
/*  6161: 6108 */         if (bool1)
/*  6162:      */         {
/*  6163: 6110 */           localLocalFrame = new LocalFrame(1);
/*  6164: 6111 */           localLocalStack.push(localLocalFrame);
/*  6165:      */         }
/*  6166: 6113 */         if (!bool1)
/*  6167:      */         {
/*  6168: 6115 */           localObject4 = local_Request.getOutputStream();
/*  6169: 6116 */           if (local_Request.isRMI()) {
/*  6170: 6116 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  6171:      */           } else {
/*  6172: 6116 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/*  6173:      */           }
/*  6174:      */         }
/*  6175:      */         else
/*  6176:      */         {
/*  6177: 6120 */           localObject4 = local_Request.getOutputStream();
/*  6178: 6121 */           localLocalFrame.add(paramArrayOfString);
/*  6179:      */         }
/*  6180: 6123 */         local_Request.invoke();
/*  6181:      */         Object localObject5;
/*  6182:      */         Object localObject1;
/*  6183: 6124 */         if (bool1)
/*  6184:      */         {
/*  6185: 6126 */           localObject4 = null;
/*  6186: 6127 */           localObject5 = localLocalFrame.getResult();
/*  6187: 6128 */           if (localObject5 != null) {
/*  6188: 6130 */             localObject4 = (BPWFIInfo[])ObjectVal.clone(localObject5);
/*  6189:      */           }
/*  6190: 6132 */           return localObject4;
/*  6191:      */         }
/*  6192: 6134 */         Object localObject4 = local_Request.getInputStream();
/*  6193: 6136 */         if (local_Request.isRMI()) {
/*  6194: 6136 */           localObject5 = (BPWFIInfo[])local_Request.read_value(new BPWFIInfo[0].getClass());
/*  6195:      */         } else {
/*  6196: 6136 */           localObject5 = BPWFIInfoSeqHelper.read((InputStream)localObject4);
/*  6197:      */         }
/*  6198: 6137 */         return localObject5;
/*  6199:      */       }
/*  6200:      */       catch (TRANSIENT localTRANSIENT)
/*  6201:      */       {
/*  6202: 6141 */         if (i == 10) {
/*  6203: 6143 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6204:      */         }
/*  6205:      */       }
/*  6206:      */       catch (UserException localUserException)
/*  6207:      */       {
/*  6208: 6148 */         local_Request.isRMI();
/*  6209:      */         
/*  6210:      */ 
/*  6211: 6151 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6212:      */       }
/*  6213:      */       catch (SystemException localSystemException)
/*  6214:      */       {
/*  6215: 6155 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6216:      */       }
/*  6217:      */       finally
/*  6218:      */       {
/*  6219: 6159 */         if (local_Request != null) {
/*  6220: 6161 */           local_Request.close();
/*  6221:      */         }
/*  6222: 6163 */         if (bool1) {
/*  6223: 6164 */           localLocalStack.pop(localLocalFrame);
/*  6224:      */         }
/*  6225: 6165 */         localLocalStack.setArgsOnLocal(bool2);
/*  6226:      */       }
/*  6227:      */     }
/*  6228:      */   }
/*  6229:      */   
/*  6230:      */   public BPWFIInfo[] getBPWFIInfoByType(String paramString)
/*  6231:      */     throws java.rmi.RemoteException
/*  6232:      */   {
/*  6233: 6174 */     for (int i = 1;; i++)
/*  6234:      */     {
/*  6235: 6176 */       _Request local_Request = null;
/*  6236: 6177 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6237: 6178 */       boolean bool1 = false;
/*  6238: 6179 */       LocalFrame localLocalFrame = null;
/*  6239: 6180 */       boolean bool2 = false;
/*  6240:      */       try
/*  6241:      */       {
/*  6242: 6183 */         local_Request = __request("getBPWFIInfoByType");
/*  6243: 6184 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6244: 6185 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6245: 6186 */         localLocalStack.setArgsOnLocal(bool1);
/*  6246: 6188 */         if (bool1)
/*  6247:      */         {
/*  6248: 6190 */           localLocalFrame = new LocalFrame(1);
/*  6249: 6191 */           localLocalStack.push(localLocalFrame);
/*  6250:      */         }
/*  6251: 6193 */         if (!bool1)
/*  6252:      */         {
/*  6253: 6195 */           localObject4 = local_Request.getOutputStream();
/*  6254: 6196 */           local_Request.write_string(paramString);
/*  6255:      */         }
/*  6256:      */         else
/*  6257:      */         {
/*  6258: 6200 */           localObject4 = local_Request.getOutputStream();
/*  6259: 6201 */           localLocalFrame.add(paramString);
/*  6260:      */         }
/*  6261: 6203 */         local_Request.invoke();
/*  6262:      */         Object localObject5;
/*  6263:      */         Object localObject1;
/*  6264: 6204 */         if (bool1)
/*  6265:      */         {
/*  6266: 6206 */           localObject4 = null;
/*  6267: 6207 */           localObject5 = localLocalFrame.getResult();
/*  6268: 6208 */           if (localObject5 != null) {
/*  6269: 6210 */             localObject4 = (BPWFIInfo[])ObjectVal.clone(localObject5);
/*  6270:      */           }
/*  6271: 6212 */           return localObject4;
/*  6272:      */         }
/*  6273: 6214 */         Object localObject4 = local_Request.getInputStream();
/*  6274: 6216 */         if (local_Request.isRMI()) {
/*  6275: 6216 */           localObject5 = (BPWFIInfo[])local_Request.read_value(new BPWFIInfo[0].getClass());
/*  6276:      */         } else {
/*  6277: 6216 */           localObject5 = BPWFIInfoSeqHelper.read((InputStream)localObject4);
/*  6278:      */         }
/*  6279: 6217 */         return localObject5;
/*  6280:      */       }
/*  6281:      */       catch (TRANSIENT localTRANSIENT)
/*  6282:      */       {
/*  6283: 6221 */         if (i == 10) {
/*  6284: 6223 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6285:      */         }
/*  6286:      */       }
/*  6287:      */       catch (UserException localUserException)
/*  6288:      */       {
/*  6289: 6228 */         local_Request.isRMI();
/*  6290:      */         
/*  6291:      */ 
/*  6292: 6231 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6293:      */       }
/*  6294:      */       catch (SystemException localSystemException)
/*  6295:      */       {
/*  6296: 6235 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6297:      */       }
/*  6298:      */       finally
/*  6299:      */       {
/*  6300: 6239 */         if (local_Request != null) {
/*  6301: 6241 */           local_Request.close();
/*  6302:      */         }
/*  6303: 6243 */         if (bool1) {
/*  6304: 6244 */           localLocalStack.pop(localLocalFrame);
/*  6305:      */         }
/*  6306: 6245 */         localLocalStack.setArgsOnLocal(bool2);
/*  6307:      */       }
/*  6308:      */     }
/*  6309:      */   }
/*  6310:      */   
/*  6311:      */   public BPWFIInfo[] getBPWFIInfoByStatus(String paramString)
/*  6312:      */     throws java.rmi.RemoteException
/*  6313:      */   {
/*  6314: 6254 */     for (int i = 1;; i++)
/*  6315:      */     {
/*  6316: 6256 */       _Request local_Request = null;
/*  6317: 6257 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6318: 6258 */       boolean bool1 = false;
/*  6319: 6259 */       LocalFrame localLocalFrame = null;
/*  6320: 6260 */       boolean bool2 = false;
/*  6321:      */       try
/*  6322:      */       {
/*  6323: 6263 */         local_Request = __request("getBPWFIInfoByStatus");
/*  6324: 6264 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6325: 6265 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6326: 6266 */         localLocalStack.setArgsOnLocal(bool1);
/*  6327: 6268 */         if (bool1)
/*  6328:      */         {
/*  6329: 6270 */           localLocalFrame = new LocalFrame(1);
/*  6330: 6271 */           localLocalStack.push(localLocalFrame);
/*  6331:      */         }
/*  6332: 6273 */         if (!bool1)
/*  6333:      */         {
/*  6334: 6275 */           localObject4 = local_Request.getOutputStream();
/*  6335: 6276 */           local_Request.write_string(paramString);
/*  6336:      */         }
/*  6337:      */         else
/*  6338:      */         {
/*  6339: 6280 */           localObject4 = local_Request.getOutputStream();
/*  6340: 6281 */           localLocalFrame.add(paramString);
/*  6341:      */         }
/*  6342: 6283 */         local_Request.invoke();
/*  6343:      */         Object localObject5;
/*  6344:      */         Object localObject1;
/*  6345: 6284 */         if (bool1)
/*  6346:      */         {
/*  6347: 6286 */           localObject4 = null;
/*  6348: 6287 */           localObject5 = localLocalFrame.getResult();
/*  6349: 6288 */           if (localObject5 != null) {
/*  6350: 6290 */             localObject4 = (BPWFIInfo[])ObjectVal.clone(localObject5);
/*  6351:      */           }
/*  6352: 6292 */           return localObject4;
/*  6353:      */         }
/*  6354: 6294 */         Object localObject4 = local_Request.getInputStream();
/*  6355: 6296 */         if (local_Request.isRMI()) {
/*  6356: 6296 */           localObject5 = (BPWFIInfo[])local_Request.read_value(new BPWFIInfo[0].getClass());
/*  6357:      */         } else {
/*  6358: 6296 */           localObject5 = BPWFIInfoSeqHelper.read((InputStream)localObject4);
/*  6359:      */         }
/*  6360: 6297 */         return localObject5;
/*  6361:      */       }
/*  6362:      */       catch (TRANSIENT localTRANSIENT)
/*  6363:      */       {
/*  6364: 6301 */         if (i == 10) {
/*  6365: 6303 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6366:      */         }
/*  6367:      */       }
/*  6368:      */       catch (UserException localUserException)
/*  6369:      */       {
/*  6370: 6308 */         local_Request.isRMI();
/*  6371:      */         
/*  6372:      */ 
/*  6373: 6311 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6374:      */       }
/*  6375:      */       catch (SystemException localSystemException)
/*  6376:      */       {
/*  6377: 6315 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6378:      */       }
/*  6379:      */       finally
/*  6380:      */       {
/*  6381: 6319 */         if (local_Request != null) {
/*  6382: 6321 */           local_Request.close();
/*  6383:      */         }
/*  6384: 6323 */         if (bool1) {
/*  6385: 6324 */           localLocalStack.pop(localLocalFrame);
/*  6386:      */         }
/*  6387: 6325 */         localLocalStack.setArgsOnLocal(bool2);
/*  6388:      */       }
/*  6389:      */     }
/*  6390:      */   }
/*  6391:      */   
/*  6392:      */   public BPWFIInfo[] getBPWFIInfoByGroup(String paramString)
/*  6393:      */     throws java.rmi.RemoteException
/*  6394:      */   {
/*  6395: 6334 */     for (int i = 1;; i++)
/*  6396:      */     {
/*  6397: 6336 */       _Request local_Request = null;
/*  6398: 6337 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6399: 6338 */       boolean bool1 = false;
/*  6400: 6339 */       LocalFrame localLocalFrame = null;
/*  6401: 6340 */       boolean bool2 = false;
/*  6402:      */       try
/*  6403:      */       {
/*  6404: 6343 */         local_Request = __request("getBPWFIInfoByGroup");
/*  6405: 6344 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6406: 6345 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6407: 6346 */         localLocalStack.setArgsOnLocal(bool1);
/*  6408: 6348 */         if (bool1)
/*  6409:      */         {
/*  6410: 6350 */           localLocalFrame = new LocalFrame(1);
/*  6411: 6351 */           localLocalStack.push(localLocalFrame);
/*  6412:      */         }
/*  6413: 6353 */         if (!bool1)
/*  6414:      */         {
/*  6415: 6355 */           localObject4 = local_Request.getOutputStream();
/*  6416: 6356 */           local_Request.write_string(paramString);
/*  6417:      */         }
/*  6418:      */         else
/*  6419:      */         {
/*  6420: 6360 */           localObject4 = local_Request.getOutputStream();
/*  6421: 6361 */           localLocalFrame.add(paramString);
/*  6422:      */         }
/*  6423: 6363 */         local_Request.invoke();
/*  6424:      */         Object localObject5;
/*  6425:      */         Object localObject1;
/*  6426: 6364 */         if (bool1)
/*  6427:      */         {
/*  6428: 6366 */           localObject4 = null;
/*  6429: 6367 */           localObject5 = localLocalFrame.getResult();
/*  6430: 6368 */           if (localObject5 != null) {
/*  6431: 6370 */             localObject4 = (BPWFIInfo[])ObjectVal.clone(localObject5);
/*  6432:      */           }
/*  6433: 6372 */           return localObject4;
/*  6434:      */         }
/*  6435: 6374 */         Object localObject4 = local_Request.getInputStream();
/*  6436: 6376 */         if (local_Request.isRMI()) {
/*  6437: 6376 */           localObject5 = (BPWFIInfo[])local_Request.read_value(new BPWFIInfo[0].getClass());
/*  6438:      */         } else {
/*  6439: 6376 */           localObject5 = BPWFIInfoSeqHelper.read((InputStream)localObject4);
/*  6440:      */         }
/*  6441: 6377 */         return localObject5;
/*  6442:      */       }
/*  6443:      */       catch (TRANSIENT localTRANSIENT)
/*  6444:      */       {
/*  6445: 6381 */         if (i == 10) {
/*  6446: 6383 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6447:      */         }
/*  6448:      */       }
/*  6449:      */       catch (UserException localUserException)
/*  6450:      */       {
/*  6451: 6388 */         local_Request.isRMI();
/*  6452:      */         
/*  6453:      */ 
/*  6454: 6391 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6455:      */       }
/*  6456:      */       catch (SystemException localSystemException)
/*  6457:      */       {
/*  6458: 6395 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6459:      */       }
/*  6460:      */       finally
/*  6461:      */       {
/*  6462: 6399 */         if (local_Request != null) {
/*  6463: 6401 */           local_Request.close();
/*  6464:      */         }
/*  6465: 6403 */         if (bool1) {
/*  6466: 6404 */           localLocalStack.pop(localLocalFrame);
/*  6467:      */         }
/*  6468: 6405 */         localLocalStack.setArgsOnLocal(bool2);
/*  6469:      */       }
/*  6470:      */     }
/*  6471:      */   }
/*  6472:      */   
/*  6473:      */   public BPWFIInfo getBPWFIInfoByACHId(String paramString)
/*  6474:      */     throws java.rmi.RemoteException
/*  6475:      */   {
/*  6476: 6414 */     for (int i = 1;; i++)
/*  6477:      */     {
/*  6478: 6416 */       _Request local_Request = null;
/*  6479: 6417 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6480: 6418 */       boolean bool1 = false;
/*  6481: 6419 */       LocalFrame localLocalFrame = null;
/*  6482: 6420 */       boolean bool2 = false;
/*  6483:      */       try
/*  6484:      */       {
/*  6485: 6423 */         local_Request = __request("getBPWFIInfoByACHId");
/*  6486: 6424 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6487: 6425 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6488: 6426 */         localLocalStack.setArgsOnLocal(bool1);
/*  6489: 6428 */         if (bool1)
/*  6490:      */         {
/*  6491: 6430 */           localLocalFrame = new LocalFrame(1);
/*  6492: 6431 */           localLocalStack.push(localLocalFrame);
/*  6493:      */         }
/*  6494: 6433 */         if (!bool1)
/*  6495:      */         {
/*  6496: 6435 */           localObject4 = local_Request.getOutputStream();
/*  6497: 6436 */           local_Request.write_string(paramString);
/*  6498:      */         }
/*  6499:      */         else
/*  6500:      */         {
/*  6501: 6440 */           localObject4 = local_Request.getOutputStream();
/*  6502: 6441 */           localLocalFrame.add(paramString);
/*  6503:      */         }
/*  6504: 6443 */         local_Request.invoke();
/*  6505:      */         Object localObject1;
/*  6506: 6444 */         if (bool1)
/*  6507:      */         {
/*  6508: 6446 */           localObject4 = null;
/*  6509: 6447 */           localObject5 = localLocalFrame.getResult();
/*  6510: 6448 */           if (localObject5 != null) {
/*  6511: 6450 */             localObject4 = (BPWFIInfo)ObjectVal.clone(localObject5);
/*  6512:      */           }
/*  6513: 6452 */           return localObject4;
/*  6514:      */         }
/*  6515: 6454 */         Object localObject4 = local_Request.getInputStream();
/*  6516:      */         
/*  6517: 6456 */         Object localObject5 = (BPWFIInfo)local_Request.read_value(BPWFIInfo.class);
/*  6518: 6457 */         return localObject5;
/*  6519:      */       }
/*  6520:      */       catch (TRANSIENT localTRANSIENT)
/*  6521:      */       {
/*  6522: 6461 */         if (i == 10) {
/*  6523: 6463 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6524:      */         }
/*  6525:      */       }
/*  6526:      */       catch (UserException localUserException)
/*  6527:      */       {
/*  6528: 6468 */         local_Request.isRMI();
/*  6529:      */         
/*  6530:      */ 
/*  6531: 6471 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6532:      */       }
/*  6533:      */       catch (SystemException localSystemException)
/*  6534:      */       {
/*  6535: 6475 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6536:      */       }
/*  6537:      */       finally
/*  6538:      */       {
/*  6539: 6479 */         if (local_Request != null) {
/*  6540: 6481 */           local_Request.close();
/*  6541:      */         }
/*  6542: 6483 */         if (bool1) {
/*  6543: 6484 */           localLocalStack.pop(localLocalFrame);
/*  6544:      */         }
/*  6545: 6485 */         localLocalStack.setArgsOnLocal(bool2);
/*  6546:      */       }
/*  6547:      */     }
/*  6548:      */   }
/*  6549:      */   
/*  6550:      */   public BPWFIInfo getBPWFIInfoByRTN(String paramString)
/*  6551:      */     throws java.rmi.RemoteException
/*  6552:      */   {
/*  6553: 6494 */     for (int i = 1;; i++)
/*  6554:      */     {
/*  6555: 6496 */       _Request local_Request = null;
/*  6556: 6497 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6557: 6498 */       boolean bool1 = false;
/*  6558: 6499 */       LocalFrame localLocalFrame = null;
/*  6559: 6500 */       boolean bool2 = false;
/*  6560:      */       try
/*  6561:      */       {
/*  6562: 6503 */         local_Request = __request("getBPWFIInfoByRTN");
/*  6563: 6504 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6564: 6505 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6565: 6506 */         localLocalStack.setArgsOnLocal(bool1);
/*  6566: 6508 */         if (bool1)
/*  6567:      */         {
/*  6568: 6510 */           localLocalFrame = new LocalFrame(1);
/*  6569: 6511 */           localLocalStack.push(localLocalFrame);
/*  6570:      */         }
/*  6571: 6513 */         if (!bool1)
/*  6572:      */         {
/*  6573: 6515 */           localObject4 = local_Request.getOutputStream();
/*  6574: 6516 */           local_Request.write_string(paramString);
/*  6575:      */         }
/*  6576:      */         else
/*  6577:      */         {
/*  6578: 6520 */           localObject4 = local_Request.getOutputStream();
/*  6579: 6521 */           localLocalFrame.add(paramString);
/*  6580:      */         }
/*  6581: 6523 */         local_Request.invoke();
/*  6582:      */         Object localObject1;
/*  6583: 6524 */         if (bool1)
/*  6584:      */         {
/*  6585: 6526 */           localObject4 = null;
/*  6586: 6527 */           localObject5 = localLocalFrame.getResult();
/*  6587: 6528 */           if (localObject5 != null) {
/*  6588: 6530 */             localObject4 = (BPWFIInfo)ObjectVal.clone(localObject5);
/*  6589:      */           }
/*  6590: 6532 */           return localObject4;
/*  6591:      */         }
/*  6592: 6534 */         Object localObject4 = local_Request.getInputStream();
/*  6593:      */         
/*  6594: 6536 */         Object localObject5 = (BPWFIInfo)local_Request.read_value(BPWFIInfo.class);
/*  6595: 6537 */         return localObject5;
/*  6596:      */       }
/*  6597:      */       catch (TRANSIENT localTRANSIENT)
/*  6598:      */       {
/*  6599: 6541 */         if (i == 10) {
/*  6600: 6543 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6601:      */         }
/*  6602:      */       }
/*  6603:      */       catch (UserException localUserException)
/*  6604:      */       {
/*  6605: 6548 */         local_Request.isRMI();
/*  6606:      */         
/*  6607:      */ 
/*  6608: 6551 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6609:      */       }
/*  6610:      */       catch (SystemException localSystemException)
/*  6611:      */       {
/*  6612: 6555 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6613:      */       }
/*  6614:      */       finally
/*  6615:      */       {
/*  6616: 6559 */         if (local_Request != null) {
/*  6617: 6561 */           local_Request.close();
/*  6618:      */         }
/*  6619: 6563 */         if (bool1) {
/*  6620: 6564 */           localLocalStack.pop(localLocalFrame);
/*  6621:      */         }
/*  6622: 6565 */         localLocalStack.setArgsOnLocal(bool2);
/*  6623:      */       }
/*  6624:      */     }
/*  6625:      */   }
/*  6626:      */   
/*  6627:      */   public RPPSFIInfo addRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
/*  6628:      */     throws java.rmi.RemoteException, FFSException
/*  6629:      */   {
/*  6630: 6574 */     for (int i = 1;; i++)
/*  6631:      */     {
/*  6632: 6576 */       _Request local_Request = null;
/*  6633: 6577 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6634: 6578 */       boolean bool1 = false;
/*  6635: 6579 */       LocalFrame localLocalFrame = null;
/*  6636: 6580 */       boolean bool2 = false;
/*  6637:      */       try
/*  6638:      */       {
/*  6639: 6583 */         local_Request = __request("addRPPSFIInfo");
/*  6640: 6584 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6641: 6585 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6642: 6586 */         localLocalStack.setArgsOnLocal(bool1);
/*  6643: 6588 */         if (bool1)
/*  6644:      */         {
/*  6645: 6590 */           localLocalFrame = new LocalFrame(1);
/*  6646: 6591 */           localLocalStack.push(localLocalFrame);
/*  6647:      */         }
/*  6648: 6593 */         if (!bool1)
/*  6649:      */         {
/*  6650: 6595 */           localObject4 = local_Request.getOutputStream();
/*  6651: 6596 */           local_Request.write_value(paramRPPSFIInfo, RPPSFIInfo.class);
/*  6652:      */         }
/*  6653:      */         else
/*  6654:      */         {
/*  6655: 6600 */           localObject4 = local_Request.getOutputStream();
/*  6656: 6601 */           localLocalFrame.add(paramRPPSFIInfo);
/*  6657:      */         }
/*  6658: 6603 */         local_Request.invoke();
/*  6659:      */         Object localObject1;
/*  6660: 6604 */         if (bool1)
/*  6661:      */         {
/*  6662: 6606 */           localObject4 = null;
/*  6663: 6607 */           localObject5 = localLocalFrame.getResult();
/*  6664: 6608 */           if (localObject5 != null) {
/*  6665: 6610 */             localObject4 = (RPPSFIInfo)ObjectVal.clone(localObject5);
/*  6666:      */           }
/*  6667: 6612 */           return localObject4;
/*  6668:      */         }
/*  6669: 6614 */         Object localObject4 = local_Request.getInputStream();
/*  6670:      */         
/*  6671: 6616 */         localObject5 = (RPPSFIInfo)local_Request.read_value(RPPSFIInfo.class);
/*  6672: 6617 */         return localObject5;
/*  6673:      */       }
/*  6674:      */       catch (TRANSIENT localTRANSIENT)
/*  6675:      */       {
/*  6676: 6621 */         if (i == 10) {
/*  6677: 6623 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6678:      */         }
/*  6679:      */       }
/*  6680:      */       catch (UserException localUserException)
/*  6681:      */       {
/*  6682:      */         Object localObject5;
/*  6683: 6628 */         if (local_Request.isRMI())
/*  6684:      */         {
/*  6685: 6630 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  6686: 6632 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  6687:      */           }
/*  6688:      */         }
/*  6689:      */         else
/*  6690:      */         {
/*  6691: 6637 */           localObject5 = null;
/*  6692: 6638 */           if (bool1)
/*  6693:      */           {
/*  6694: 6640 */             localObject5 = localLocalFrame.getException();
/*  6695: 6641 */             if (localObject5 != null) {
/*  6696: 6643 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  6697:      */             }
/*  6698:      */           }
/*  6699: 6646 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  6700:      */           {
/*  6701: 6648 */             if (local_Request.isRMI()) {
/*  6702: 6650 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  6703:      */             }
/*  6704: 6654 */             if (bool1)
/*  6705:      */             {
/*  6706: 6656 */               if (localObject5 != null) {
/*  6707: 6656 */                 throw ((FFSException)localObject5);
/*  6708:      */               }
/*  6709:      */             }
/*  6710:      */             else {
/*  6711: 6660 */               throw FFSExceptionHelper.read(localUserException.input);
/*  6712:      */             }
/*  6713:      */           }
/*  6714:      */         }
/*  6715: 6665 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6716:      */       }
/*  6717:      */       catch (SystemException localSystemException)
/*  6718:      */       {
/*  6719: 6669 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6720:      */       }
/*  6721:      */       finally
/*  6722:      */       {
/*  6723: 6673 */         if (local_Request != null) {
/*  6724: 6675 */           local_Request.close();
/*  6725:      */         }
/*  6726: 6677 */         if (bool1) {
/*  6727: 6678 */           localLocalStack.pop(localLocalFrame);
/*  6728:      */         }
/*  6729: 6679 */         localLocalStack.setArgsOnLocal(bool2);
/*  6730:      */       }
/*  6731:      */     }
/*  6732:      */   }
/*  6733:      */   
/*  6734:      */   public RPPSFIInfo getRPPSFIInfoByFIId(String paramString)
/*  6735:      */     throws java.rmi.RemoteException, FFSException
/*  6736:      */   {
/*  6737: 6688 */     for (int i = 1;; i++)
/*  6738:      */     {
/*  6739: 6690 */       _Request local_Request = null;
/*  6740: 6691 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6741: 6692 */       boolean bool1 = false;
/*  6742: 6693 */       LocalFrame localLocalFrame = null;
/*  6743: 6694 */       boolean bool2 = false;
/*  6744:      */       try
/*  6745:      */       {
/*  6746: 6697 */         local_Request = __request("getRPPSFIInfoByFIId");
/*  6747: 6698 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6748: 6699 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6749: 6700 */         localLocalStack.setArgsOnLocal(bool1);
/*  6750: 6702 */         if (bool1)
/*  6751:      */         {
/*  6752: 6704 */           localLocalFrame = new LocalFrame(1);
/*  6753: 6705 */           localLocalStack.push(localLocalFrame);
/*  6754:      */         }
/*  6755: 6707 */         if (!bool1)
/*  6756:      */         {
/*  6757: 6709 */           localObject4 = local_Request.getOutputStream();
/*  6758: 6710 */           local_Request.write_string(paramString);
/*  6759:      */         }
/*  6760:      */         else
/*  6761:      */         {
/*  6762: 6714 */           localObject4 = local_Request.getOutputStream();
/*  6763: 6715 */           localLocalFrame.add(paramString);
/*  6764:      */         }
/*  6765: 6717 */         local_Request.invoke();
/*  6766:      */         Object localObject1;
/*  6767: 6718 */         if (bool1)
/*  6768:      */         {
/*  6769: 6720 */           localObject4 = null;
/*  6770: 6721 */           localObject5 = localLocalFrame.getResult();
/*  6771: 6722 */           if (localObject5 != null) {
/*  6772: 6724 */             localObject4 = (RPPSFIInfo)ObjectVal.clone(localObject5);
/*  6773:      */           }
/*  6774: 6726 */           return localObject4;
/*  6775:      */         }
/*  6776: 6728 */         Object localObject4 = local_Request.getInputStream();
/*  6777:      */         
/*  6778: 6730 */         localObject5 = (RPPSFIInfo)local_Request.read_value(RPPSFIInfo.class);
/*  6779: 6731 */         return localObject5;
/*  6780:      */       }
/*  6781:      */       catch (TRANSIENT localTRANSIENT)
/*  6782:      */       {
/*  6783: 6735 */         if (i == 10) {
/*  6784: 6737 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6785:      */         }
/*  6786:      */       }
/*  6787:      */       catch (UserException localUserException)
/*  6788:      */       {
/*  6789:      */         Object localObject5;
/*  6790: 6742 */         if (local_Request.isRMI())
/*  6791:      */         {
/*  6792: 6744 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  6793: 6746 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  6794:      */           }
/*  6795:      */         }
/*  6796:      */         else
/*  6797:      */         {
/*  6798: 6751 */           localObject5 = null;
/*  6799: 6752 */           if (bool1)
/*  6800:      */           {
/*  6801: 6754 */             localObject5 = localLocalFrame.getException();
/*  6802: 6755 */             if (localObject5 != null) {
/*  6803: 6757 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  6804:      */             }
/*  6805:      */           }
/*  6806: 6760 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  6807:      */           {
/*  6808: 6762 */             if (local_Request.isRMI()) {
/*  6809: 6764 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  6810:      */             }
/*  6811: 6768 */             if (bool1)
/*  6812:      */             {
/*  6813: 6770 */               if (localObject5 != null) {
/*  6814: 6770 */                 throw ((FFSException)localObject5);
/*  6815:      */               }
/*  6816:      */             }
/*  6817:      */             else {
/*  6818: 6774 */               throw FFSExceptionHelper.read(localUserException.input);
/*  6819:      */             }
/*  6820:      */           }
/*  6821:      */         }
/*  6822: 6779 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6823:      */       }
/*  6824:      */       catch (SystemException localSystemException)
/*  6825:      */       {
/*  6826: 6783 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6827:      */       }
/*  6828:      */       finally
/*  6829:      */       {
/*  6830: 6787 */         if (local_Request != null) {
/*  6831: 6789 */           local_Request.close();
/*  6832:      */         }
/*  6833: 6791 */         if (bool1) {
/*  6834: 6792 */           localLocalStack.pop(localLocalFrame);
/*  6835:      */         }
/*  6836: 6793 */         localLocalStack.setArgsOnLocal(bool2);
/*  6837:      */       }
/*  6838:      */     }
/*  6839:      */   }
/*  6840:      */   
/*  6841:      */   public RPPSFIInfo getRPPSFIInfoByFIRPPSId(String paramString)
/*  6842:      */     throws java.rmi.RemoteException, FFSException
/*  6843:      */   {
/*  6844: 6802 */     for (int i = 1;; i++)
/*  6845:      */     {
/*  6846: 6804 */       _Request local_Request = null;
/*  6847: 6805 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6848: 6806 */       boolean bool1 = false;
/*  6849: 6807 */       LocalFrame localLocalFrame = null;
/*  6850: 6808 */       boolean bool2 = false;
/*  6851:      */       try
/*  6852:      */       {
/*  6853: 6811 */         local_Request = __request("getRPPSFIInfoByFIRPPSId");
/*  6854: 6812 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6855: 6813 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6856: 6814 */         localLocalStack.setArgsOnLocal(bool1);
/*  6857: 6816 */         if (bool1)
/*  6858:      */         {
/*  6859: 6818 */           localLocalFrame = new LocalFrame(1);
/*  6860: 6819 */           localLocalStack.push(localLocalFrame);
/*  6861:      */         }
/*  6862: 6821 */         if (!bool1)
/*  6863:      */         {
/*  6864: 6823 */           localObject4 = local_Request.getOutputStream();
/*  6865: 6824 */           local_Request.write_string(paramString);
/*  6866:      */         }
/*  6867:      */         else
/*  6868:      */         {
/*  6869: 6828 */           localObject4 = local_Request.getOutputStream();
/*  6870: 6829 */           localLocalFrame.add(paramString);
/*  6871:      */         }
/*  6872: 6831 */         local_Request.invoke();
/*  6873:      */         Object localObject1;
/*  6874: 6832 */         if (bool1)
/*  6875:      */         {
/*  6876: 6834 */           localObject4 = null;
/*  6877: 6835 */           localObject5 = localLocalFrame.getResult();
/*  6878: 6836 */           if (localObject5 != null) {
/*  6879: 6838 */             localObject4 = (RPPSFIInfo)ObjectVal.clone(localObject5);
/*  6880:      */           }
/*  6881: 6840 */           return localObject4;
/*  6882:      */         }
/*  6883: 6842 */         Object localObject4 = local_Request.getInputStream();
/*  6884:      */         
/*  6885: 6844 */         localObject5 = (RPPSFIInfo)local_Request.read_value(RPPSFIInfo.class);
/*  6886: 6845 */         return localObject5;
/*  6887:      */       }
/*  6888:      */       catch (TRANSIENT localTRANSIENT)
/*  6889:      */       {
/*  6890: 6849 */         if (i == 10) {
/*  6891: 6851 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6892:      */         }
/*  6893:      */       }
/*  6894:      */       catch (UserException localUserException)
/*  6895:      */       {
/*  6896:      */         Object localObject5;
/*  6897: 6856 */         if (local_Request.isRMI())
/*  6898:      */         {
/*  6899: 6858 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  6900: 6860 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  6901:      */           }
/*  6902:      */         }
/*  6903:      */         else
/*  6904:      */         {
/*  6905: 6865 */           localObject5 = null;
/*  6906: 6866 */           if (bool1)
/*  6907:      */           {
/*  6908: 6868 */             localObject5 = localLocalFrame.getException();
/*  6909: 6869 */             if (localObject5 != null) {
/*  6910: 6871 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  6911:      */             }
/*  6912:      */           }
/*  6913: 6874 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  6914:      */           {
/*  6915: 6876 */             if (local_Request.isRMI()) {
/*  6916: 6878 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  6917:      */             }
/*  6918: 6882 */             if (bool1)
/*  6919:      */             {
/*  6920: 6884 */               if (localObject5 != null) {
/*  6921: 6884 */                 throw ((FFSException)localObject5);
/*  6922:      */               }
/*  6923:      */             }
/*  6924:      */             else {
/*  6925: 6888 */               throw FFSExceptionHelper.read(localUserException.input);
/*  6926:      */             }
/*  6927:      */           }
/*  6928:      */         }
/*  6929: 6893 */         throw new java.rmi.RemoteException(localUserException.type);
/*  6930:      */       }
/*  6931:      */       catch (SystemException localSystemException)
/*  6932:      */       {
/*  6933: 6897 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  6934:      */       }
/*  6935:      */       finally
/*  6936:      */       {
/*  6937: 6901 */         if (local_Request != null) {
/*  6938: 6903 */           local_Request.close();
/*  6939:      */         }
/*  6940: 6905 */         if (bool1) {
/*  6941: 6906 */           localLocalStack.pop(localLocalFrame);
/*  6942:      */         }
/*  6943: 6907 */         localLocalStack.setArgsOnLocal(bool2);
/*  6944:      */       }
/*  6945:      */     }
/*  6946:      */   }
/*  6947:      */   
/*  6948:      */   public RPPSFIInfo canRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
/*  6949:      */     throws java.rmi.RemoteException, FFSException
/*  6950:      */   {
/*  6951: 6916 */     for (int i = 1;; i++)
/*  6952:      */     {
/*  6953: 6918 */       _Request local_Request = null;
/*  6954: 6919 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  6955: 6920 */       boolean bool1 = false;
/*  6956: 6921 */       LocalFrame localLocalFrame = null;
/*  6957: 6922 */       boolean bool2 = false;
/*  6958:      */       try
/*  6959:      */       {
/*  6960: 6925 */         local_Request = __request("canRPPSFIInfo");
/*  6961: 6926 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  6962: 6927 */         bool2 = localLocalStack.isArgsOnLocal();
/*  6963: 6928 */         localLocalStack.setArgsOnLocal(bool1);
/*  6964: 6930 */         if (bool1)
/*  6965:      */         {
/*  6966: 6932 */           localLocalFrame = new LocalFrame(1);
/*  6967: 6933 */           localLocalStack.push(localLocalFrame);
/*  6968:      */         }
/*  6969: 6935 */         if (!bool1)
/*  6970:      */         {
/*  6971: 6937 */           localObject4 = local_Request.getOutputStream();
/*  6972: 6938 */           local_Request.write_value(paramRPPSFIInfo, RPPSFIInfo.class);
/*  6973:      */         }
/*  6974:      */         else
/*  6975:      */         {
/*  6976: 6942 */           localObject4 = local_Request.getOutputStream();
/*  6977: 6943 */           localLocalFrame.add(paramRPPSFIInfo);
/*  6978:      */         }
/*  6979: 6945 */         local_Request.invoke();
/*  6980:      */         Object localObject1;
/*  6981: 6946 */         if (bool1)
/*  6982:      */         {
/*  6983: 6948 */           localObject4 = null;
/*  6984: 6949 */           localObject5 = localLocalFrame.getResult();
/*  6985: 6950 */           if (localObject5 != null) {
/*  6986: 6952 */             localObject4 = (RPPSFIInfo)ObjectVal.clone(localObject5);
/*  6987:      */           }
/*  6988: 6954 */           return localObject4;
/*  6989:      */         }
/*  6990: 6956 */         Object localObject4 = local_Request.getInputStream();
/*  6991:      */         
/*  6992: 6958 */         localObject5 = (RPPSFIInfo)local_Request.read_value(RPPSFIInfo.class);
/*  6993: 6959 */         return localObject5;
/*  6994:      */       }
/*  6995:      */       catch (TRANSIENT localTRANSIENT)
/*  6996:      */       {
/*  6997: 6963 */         if (i == 10) {
/*  6998: 6965 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  6999:      */         }
/*  7000:      */       }
/*  7001:      */       catch (UserException localUserException)
/*  7002:      */       {
/*  7003:      */         Object localObject5;
/*  7004: 6970 */         if (local_Request.isRMI())
/*  7005:      */         {
/*  7006: 6972 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7007: 6974 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7008:      */           }
/*  7009:      */         }
/*  7010:      */         else
/*  7011:      */         {
/*  7012: 6979 */           localObject5 = null;
/*  7013: 6980 */           if (bool1)
/*  7014:      */           {
/*  7015: 6982 */             localObject5 = localLocalFrame.getException();
/*  7016: 6983 */             if (localObject5 != null) {
/*  7017: 6985 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  7018:      */             }
/*  7019:      */           }
/*  7020: 6988 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7021:      */           {
/*  7022: 6990 */             if (local_Request.isRMI()) {
/*  7023: 6992 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7024:      */             }
/*  7025: 6996 */             if (bool1)
/*  7026:      */             {
/*  7027: 6998 */               if (localObject5 != null) {
/*  7028: 6998 */                 throw ((FFSException)localObject5);
/*  7029:      */               }
/*  7030:      */             }
/*  7031:      */             else {
/*  7032: 7002 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7033:      */             }
/*  7034:      */           }
/*  7035:      */         }
/*  7036: 7007 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7037:      */       }
/*  7038:      */       catch (SystemException localSystemException)
/*  7039:      */       {
/*  7040: 7011 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7041:      */       }
/*  7042:      */       finally
/*  7043:      */       {
/*  7044: 7015 */         if (local_Request != null) {
/*  7045: 7017 */           local_Request.close();
/*  7046:      */         }
/*  7047: 7019 */         if (bool1) {
/*  7048: 7020 */           localLocalStack.pop(localLocalFrame);
/*  7049:      */         }
/*  7050: 7021 */         localLocalStack.setArgsOnLocal(bool2);
/*  7051:      */       }
/*  7052:      */     }
/*  7053:      */   }
/*  7054:      */   
/*  7055:      */   public RPPSFIInfo activateRPPSFI(RPPSFIInfo paramRPPSFIInfo)
/*  7056:      */     throws java.rmi.RemoteException, FFSException
/*  7057:      */   {
/*  7058: 7030 */     for (int i = 1;; i++)
/*  7059:      */     {
/*  7060: 7032 */       _Request local_Request = null;
/*  7061: 7033 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7062: 7034 */       boolean bool1 = false;
/*  7063: 7035 */       LocalFrame localLocalFrame = null;
/*  7064: 7036 */       boolean bool2 = false;
/*  7065:      */       try
/*  7066:      */       {
/*  7067: 7039 */         local_Request = __request("activateRPPSFI");
/*  7068: 7040 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7069: 7041 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7070: 7042 */         localLocalStack.setArgsOnLocal(bool1);
/*  7071: 7044 */         if (bool1)
/*  7072:      */         {
/*  7073: 7046 */           localLocalFrame = new LocalFrame(1);
/*  7074: 7047 */           localLocalStack.push(localLocalFrame);
/*  7075:      */         }
/*  7076: 7049 */         if (!bool1)
/*  7077:      */         {
/*  7078: 7051 */           localObject4 = local_Request.getOutputStream();
/*  7079: 7052 */           local_Request.write_value(paramRPPSFIInfo, RPPSFIInfo.class);
/*  7080:      */         }
/*  7081:      */         else
/*  7082:      */         {
/*  7083: 7056 */           localObject4 = local_Request.getOutputStream();
/*  7084: 7057 */           localLocalFrame.add(paramRPPSFIInfo);
/*  7085:      */         }
/*  7086: 7059 */         local_Request.invoke();
/*  7087:      */         Object localObject1;
/*  7088: 7060 */         if (bool1)
/*  7089:      */         {
/*  7090: 7062 */           localObject4 = null;
/*  7091: 7063 */           localObject5 = localLocalFrame.getResult();
/*  7092: 7064 */           if (localObject5 != null) {
/*  7093: 7066 */             localObject4 = (RPPSFIInfo)ObjectVal.clone(localObject5);
/*  7094:      */           }
/*  7095: 7068 */           return localObject4;
/*  7096:      */         }
/*  7097: 7070 */         Object localObject4 = local_Request.getInputStream();
/*  7098:      */         
/*  7099: 7072 */         localObject5 = (RPPSFIInfo)local_Request.read_value(RPPSFIInfo.class);
/*  7100: 7073 */         return localObject5;
/*  7101:      */       }
/*  7102:      */       catch (TRANSIENT localTRANSIENT)
/*  7103:      */       {
/*  7104: 7077 */         if (i == 10) {
/*  7105: 7079 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7106:      */         }
/*  7107:      */       }
/*  7108:      */       catch (UserException localUserException)
/*  7109:      */       {
/*  7110:      */         Object localObject5;
/*  7111: 7084 */         if (local_Request.isRMI())
/*  7112:      */         {
/*  7113: 7086 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7114: 7088 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7115:      */           }
/*  7116:      */         }
/*  7117:      */         else
/*  7118:      */         {
/*  7119: 7093 */           localObject5 = null;
/*  7120: 7094 */           if (bool1)
/*  7121:      */           {
/*  7122: 7096 */             localObject5 = localLocalFrame.getException();
/*  7123: 7097 */             if (localObject5 != null) {
/*  7124: 7099 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  7125:      */             }
/*  7126:      */           }
/*  7127: 7102 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7128:      */           {
/*  7129: 7104 */             if (local_Request.isRMI()) {
/*  7130: 7106 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7131:      */             }
/*  7132: 7110 */             if (bool1)
/*  7133:      */             {
/*  7134: 7112 */               if (localObject5 != null) {
/*  7135: 7112 */                 throw ((FFSException)localObject5);
/*  7136:      */               }
/*  7137:      */             }
/*  7138:      */             else {
/*  7139: 7116 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7140:      */             }
/*  7141:      */           }
/*  7142:      */         }
/*  7143: 7121 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7144:      */       }
/*  7145:      */       catch (SystemException localSystemException)
/*  7146:      */       {
/*  7147: 7125 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7148:      */       }
/*  7149:      */       finally
/*  7150:      */       {
/*  7151: 7129 */         if (local_Request != null) {
/*  7152: 7131 */           local_Request.close();
/*  7153:      */         }
/*  7154: 7133 */         if (bool1) {
/*  7155: 7134 */           localLocalStack.pop(localLocalFrame);
/*  7156:      */         }
/*  7157: 7135 */         localLocalStack.setArgsOnLocal(bool2);
/*  7158:      */       }
/*  7159:      */     }
/*  7160:      */   }
/*  7161:      */   
/*  7162:      */   public RPPSFIInfo modRPPSFIInfo(RPPSFIInfo paramRPPSFIInfo)
/*  7163:      */     throws java.rmi.RemoteException, FFSException
/*  7164:      */   {
/*  7165: 7144 */     for (int i = 1;; i++)
/*  7166:      */     {
/*  7167: 7146 */       _Request local_Request = null;
/*  7168: 7147 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7169: 7148 */       boolean bool1 = false;
/*  7170: 7149 */       LocalFrame localLocalFrame = null;
/*  7171: 7150 */       boolean bool2 = false;
/*  7172:      */       try
/*  7173:      */       {
/*  7174: 7153 */         local_Request = __request("modRPPSFIInfo");
/*  7175: 7154 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7176: 7155 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7177: 7156 */         localLocalStack.setArgsOnLocal(bool1);
/*  7178: 7158 */         if (bool1)
/*  7179:      */         {
/*  7180: 7160 */           localLocalFrame = new LocalFrame(1);
/*  7181: 7161 */           localLocalStack.push(localLocalFrame);
/*  7182:      */         }
/*  7183: 7163 */         if (!bool1)
/*  7184:      */         {
/*  7185: 7165 */           localObject4 = local_Request.getOutputStream();
/*  7186: 7166 */           local_Request.write_value(paramRPPSFIInfo, RPPSFIInfo.class);
/*  7187:      */         }
/*  7188:      */         else
/*  7189:      */         {
/*  7190: 7170 */           localObject4 = local_Request.getOutputStream();
/*  7191: 7171 */           localLocalFrame.add(paramRPPSFIInfo);
/*  7192:      */         }
/*  7193: 7173 */         local_Request.invoke();
/*  7194:      */         Object localObject1;
/*  7195: 7174 */         if (bool1)
/*  7196:      */         {
/*  7197: 7176 */           localObject4 = null;
/*  7198: 7177 */           localObject5 = localLocalFrame.getResult();
/*  7199: 7178 */           if (localObject5 != null) {
/*  7200: 7180 */             localObject4 = (RPPSFIInfo)ObjectVal.clone(localObject5);
/*  7201:      */           }
/*  7202: 7182 */           return localObject4;
/*  7203:      */         }
/*  7204: 7184 */         Object localObject4 = local_Request.getInputStream();
/*  7205:      */         
/*  7206: 7186 */         localObject5 = (RPPSFIInfo)local_Request.read_value(RPPSFIInfo.class);
/*  7207: 7187 */         return localObject5;
/*  7208:      */       }
/*  7209:      */       catch (TRANSIENT localTRANSIENT)
/*  7210:      */       {
/*  7211: 7191 */         if (i == 10) {
/*  7212: 7193 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7213:      */         }
/*  7214:      */       }
/*  7215:      */       catch (UserException localUserException)
/*  7216:      */       {
/*  7217:      */         Object localObject5;
/*  7218: 7198 */         if (local_Request.isRMI())
/*  7219:      */         {
/*  7220: 7200 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7221: 7202 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7222:      */           }
/*  7223:      */         }
/*  7224:      */         else
/*  7225:      */         {
/*  7226: 7207 */           localObject5 = null;
/*  7227: 7208 */           if (bool1)
/*  7228:      */           {
/*  7229: 7210 */             localObject5 = localLocalFrame.getException();
/*  7230: 7211 */             if (localObject5 != null) {
/*  7231: 7213 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  7232:      */             }
/*  7233:      */           }
/*  7234: 7216 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7235:      */           {
/*  7236: 7218 */             if (local_Request.isRMI()) {
/*  7237: 7220 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7238:      */             }
/*  7239: 7224 */             if (bool1)
/*  7240:      */             {
/*  7241: 7226 */               if (localObject5 != null) {
/*  7242: 7226 */                 throw ((FFSException)localObject5);
/*  7243:      */               }
/*  7244:      */             }
/*  7245:      */             else {
/*  7246: 7230 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7247:      */             }
/*  7248:      */           }
/*  7249:      */         }
/*  7250: 7235 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7251:      */       }
/*  7252:      */       catch (SystemException localSystemException)
/*  7253:      */       {
/*  7254: 7239 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7255:      */       }
/*  7256:      */       finally
/*  7257:      */       {
/*  7258: 7243 */         if (local_Request != null) {
/*  7259: 7245 */           local_Request.close();
/*  7260:      */         }
/*  7261: 7247 */         if (bool1) {
/*  7262: 7248 */           localLocalStack.pop(localLocalFrame);
/*  7263:      */         }
/*  7264: 7249 */         localLocalStack.setArgsOnLocal(bool2);
/*  7265:      */       }
/*  7266:      */     }
/*  7267:      */   }
/*  7268:      */   
/*  7269:      */   public int getSmartDate(String paramString, int paramInt)
/*  7270:      */     throws java.rmi.RemoteException
/*  7271:      */   {
/*  7272: 7259 */     for (int i = 1;; i++)
/*  7273:      */     {
/*  7274: 7261 */       _Request local_Request = null;
/*  7275: 7262 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7276: 7263 */       boolean bool1 = false;
/*  7277: 7264 */       LocalFrame localLocalFrame = null;
/*  7278: 7265 */       boolean bool2 = false;
/*  7279:      */       try
/*  7280:      */       {
/*  7281: 7268 */         local_Request = __request("getSmartDate");
/*  7282: 7269 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7283: 7270 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7284: 7271 */         localLocalStack.setArgsOnLocal(bool1);
/*  7285: 7273 */         if (bool1)
/*  7286:      */         {
/*  7287: 7275 */           localLocalFrame = new LocalFrame(2);
/*  7288: 7276 */           localLocalStack.push(localLocalFrame);
/*  7289:      */         }
/*  7290:      */         OutputStream localOutputStream;
/*  7291: 7278 */         if (!bool1)
/*  7292:      */         {
/*  7293: 7280 */           localOutputStream = local_Request.getOutputStream();
/*  7294: 7281 */           local_Request.write_string(paramString);
/*  7295: 7282 */           localOutputStream.write_long(paramInt);
/*  7296:      */         }
/*  7297:      */         else
/*  7298:      */         {
/*  7299: 7286 */           localOutputStream = local_Request.getOutputStream();
/*  7300: 7287 */           localLocalFrame.add(paramString);
/*  7301: 7288 */           localLocalFrame.add(paramInt);
/*  7302:      */         }
/*  7303: 7290 */         local_Request.invoke();
/*  7304:      */         int j;
/*  7305: 7291 */         if (bool1)
/*  7306:      */         {
/*  7307: 7294 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  7308: 7295 */           return k;
/*  7309:      */         }
/*  7310: 7297 */         InputStream localInputStream = local_Request.getInputStream();
/*  7311:      */         
/*  7312: 7299 */         int m = localInputStream.read_long();
/*  7313: 7300 */         return m;
/*  7314:      */       }
/*  7315:      */       catch (TRANSIENT localTRANSIENT)
/*  7316:      */       {
/*  7317: 7304 */         if (i == 10) {
/*  7318: 7306 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7319:      */         }
/*  7320:      */       }
/*  7321:      */       catch (UserException localUserException)
/*  7322:      */       {
/*  7323: 7311 */         local_Request.isRMI();
/*  7324:      */         
/*  7325:      */ 
/*  7326: 7314 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7327:      */       }
/*  7328:      */       catch (SystemException localSystemException)
/*  7329:      */       {
/*  7330: 7318 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7331:      */       }
/*  7332:      */       finally
/*  7333:      */       {
/*  7334: 7322 */         if (local_Request != null) {
/*  7335: 7324 */           local_Request.close();
/*  7336:      */         }
/*  7337: 7326 */         if (bool1) {
/*  7338: 7327 */           localLocalStack.pop(localLocalFrame);
/*  7339:      */         }
/*  7340: 7328 */         localLocalStack.setArgsOnLocal(bool2);
/*  7341:      */       }
/*  7342:      */     }
/*  7343:      */   }
/*  7344:      */   
/*  7345:      */   public void processApprovalResult(String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
/*  7346:      */     throws java.rmi.RemoteException, FFSException
/*  7347:      */   {
/*  7348: 7340 */     for (int i = 1;; i++)
/*  7349:      */     {
/*  7350: 7342 */       _Request local_Request = null;
/*  7351: 7343 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7352: 7344 */       boolean bool1 = false;
/*  7353: 7345 */       LocalFrame localLocalFrame = null;
/*  7354: 7346 */       boolean bool2 = false;
/*  7355:      */       try
/*  7356:      */       {
/*  7357: 7349 */         local_Request = __request("processApprovalResult");
/*  7358: 7350 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7359: 7351 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7360: 7352 */         localLocalStack.setArgsOnLocal(bool1);
/*  7361: 7354 */         if (bool1)
/*  7362:      */         {
/*  7363: 7356 */           localLocalFrame = new LocalFrame(4);
/*  7364: 7357 */           localLocalStack.push(localLocalFrame);
/*  7365:      */         }
/*  7366:      */         OutputStream localOutputStream;
/*  7367: 7359 */         if (!bool1)
/*  7368:      */         {
/*  7369: 7361 */           localOutputStream = local_Request.getOutputStream();
/*  7370: 7362 */           local_Request.write_string(paramString1);
/*  7371: 7363 */           local_Request.write_string(paramString2);
/*  7372: 7364 */           local_Request.write_string(paramString3);
/*  7373: 7365 */           local_Request.write_value(paramHashMap, HashMap.class);
/*  7374:      */         }
/*  7375:      */         else
/*  7376:      */         {
/*  7377: 7369 */           localOutputStream = local_Request.getOutputStream();
/*  7378: 7370 */           localLocalFrame.add(paramString1);
/*  7379: 7371 */           localLocalFrame.add(paramString2);
/*  7380: 7372 */           localLocalFrame.add(paramString3);
/*  7381: 7373 */           localLocalFrame.add(paramHashMap);
/*  7382:      */         }
/*  7383: 7375 */         local_Request.invoke();
/*  7384: 7376 */         return;
/*  7385:      */       }
/*  7386:      */       catch (TRANSIENT localTRANSIENT)
/*  7387:      */       {
/*  7388: 7380 */         if (i == 10) {
/*  7389: 7382 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7390:      */         }
/*  7391:      */       }
/*  7392:      */       catch (UserException localUserException)
/*  7393:      */       {
/*  7394: 7387 */         if (local_Request.isRMI())
/*  7395:      */         {
/*  7396: 7389 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7397: 7391 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7398:      */           }
/*  7399:      */         }
/*  7400:      */         else
/*  7401:      */         {
/*  7402: 7396 */           Throwable localThrowable = null;
/*  7403: 7397 */           if (bool1)
/*  7404:      */           {
/*  7405: 7399 */             localThrowable = localLocalFrame.getException();
/*  7406: 7400 */             if (localThrowable != null) {
/*  7407: 7402 */               localThrowable = (Throwable)ObjectVal.clone(localThrowable);
/*  7408:      */             }
/*  7409:      */           }
/*  7410: 7405 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7411:      */           {
/*  7412: 7407 */             if (local_Request.isRMI()) {
/*  7413: 7409 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7414:      */             }
/*  7415: 7413 */             if (bool1)
/*  7416:      */             {
/*  7417: 7415 */               if (localThrowable != null) {
/*  7418: 7415 */                 throw ((FFSException)localThrowable);
/*  7419:      */               }
/*  7420:      */             }
/*  7421:      */             else {
/*  7422: 7419 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7423:      */             }
/*  7424:      */           }
/*  7425:      */         }
/*  7426: 7424 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7427:      */       }
/*  7428:      */       catch (SystemException localSystemException)
/*  7429:      */       {
/*  7430: 7428 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7431:      */       }
/*  7432:      */       finally
/*  7433:      */       {
/*  7434: 7432 */         if (local_Request != null) {
/*  7435: 7434 */           local_Request.close();
/*  7436:      */         }
/*  7437: 7436 */         if (bool1) {
/*  7438: 7437 */           localLocalStack.pop(localLocalFrame);
/*  7439:      */         }
/*  7440: 7438 */         localLocalStack.setArgsOnLocal(bool2);
/*  7441:      */       }
/*  7442:      */     }
/*  7443:      */   }
/*  7444:      */   
/*  7445:      */   public CCCompanyInfo addCCCompany(CCCompanyInfo paramCCCompanyInfo)
/*  7446:      */     throws java.rmi.RemoteException, FFSException
/*  7447:      */   {
/*  7448: 7447 */     for (int i = 1;; i++)
/*  7449:      */     {
/*  7450: 7449 */       _Request local_Request = null;
/*  7451: 7450 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7452: 7451 */       boolean bool1 = false;
/*  7453: 7452 */       LocalFrame localLocalFrame = null;
/*  7454: 7453 */       boolean bool2 = false;
/*  7455:      */       try
/*  7456:      */       {
/*  7457: 7456 */         local_Request = __request("addCCCompany");
/*  7458: 7457 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7459: 7458 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7460: 7459 */         localLocalStack.setArgsOnLocal(bool1);
/*  7461: 7461 */         if (bool1)
/*  7462:      */         {
/*  7463: 7463 */           localLocalFrame = new LocalFrame(1);
/*  7464: 7464 */           localLocalStack.push(localLocalFrame);
/*  7465:      */         }
/*  7466: 7466 */         if (!bool1)
/*  7467:      */         {
/*  7468: 7468 */           localObject4 = local_Request.getOutputStream();
/*  7469: 7469 */           local_Request.write_value(paramCCCompanyInfo, CCCompanyInfo.class);
/*  7470:      */         }
/*  7471:      */         else
/*  7472:      */         {
/*  7473: 7473 */           localObject4 = local_Request.getOutputStream();
/*  7474: 7474 */           localLocalFrame.add(paramCCCompanyInfo);
/*  7475:      */         }
/*  7476: 7476 */         local_Request.invoke();
/*  7477:      */         Object localObject1;
/*  7478: 7477 */         if (bool1)
/*  7479:      */         {
/*  7480: 7479 */           localObject4 = null;
/*  7481: 7480 */           localObject5 = localLocalFrame.getResult();
/*  7482: 7481 */           if (localObject5 != null) {
/*  7483: 7483 */             localObject4 = (CCCompanyInfo)ObjectVal.clone(localObject5);
/*  7484:      */           }
/*  7485: 7485 */           return localObject4;
/*  7486:      */         }
/*  7487: 7487 */         Object localObject4 = local_Request.getInputStream();
/*  7488:      */         
/*  7489: 7489 */         localObject5 = (CCCompanyInfo)local_Request.read_value(CCCompanyInfo.class);
/*  7490: 7490 */         return localObject5;
/*  7491:      */       }
/*  7492:      */       catch (TRANSIENT localTRANSIENT)
/*  7493:      */       {
/*  7494: 7494 */         if (i == 10) {
/*  7495: 7496 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7496:      */         }
/*  7497:      */       }
/*  7498:      */       catch (UserException localUserException)
/*  7499:      */       {
/*  7500:      */         Object localObject5;
/*  7501: 7501 */         if (local_Request.isRMI())
/*  7502:      */         {
/*  7503: 7503 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7504: 7505 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7505:      */           }
/*  7506:      */         }
/*  7507:      */         else
/*  7508:      */         {
/*  7509: 7510 */           localObject5 = null;
/*  7510: 7511 */           if (bool1)
/*  7511:      */           {
/*  7512: 7513 */             localObject5 = localLocalFrame.getException();
/*  7513: 7514 */             if (localObject5 != null) {
/*  7514: 7516 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  7515:      */             }
/*  7516:      */           }
/*  7517: 7519 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7518:      */           {
/*  7519: 7521 */             if (local_Request.isRMI()) {
/*  7520: 7523 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7521:      */             }
/*  7522: 7527 */             if (bool1)
/*  7523:      */             {
/*  7524: 7529 */               if (localObject5 != null) {
/*  7525: 7529 */                 throw ((FFSException)localObject5);
/*  7526:      */               }
/*  7527:      */             }
/*  7528:      */             else {
/*  7529: 7533 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7530:      */             }
/*  7531:      */           }
/*  7532:      */         }
/*  7533: 7538 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7534:      */       }
/*  7535:      */       catch (SystemException localSystemException)
/*  7536:      */       {
/*  7537: 7542 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7538:      */       }
/*  7539:      */       finally
/*  7540:      */       {
/*  7541: 7546 */         if (local_Request != null) {
/*  7542: 7548 */           local_Request.close();
/*  7543:      */         }
/*  7544: 7550 */         if (bool1) {
/*  7545: 7551 */           localLocalStack.pop(localLocalFrame);
/*  7546:      */         }
/*  7547: 7552 */         localLocalStack.setArgsOnLocal(bool2);
/*  7548:      */       }
/*  7549:      */     }
/*  7550:      */   }
/*  7551:      */   
/*  7552:      */   public CCCompanyInfo cancelCCCompany(CCCompanyInfo paramCCCompanyInfo)
/*  7553:      */     throws java.rmi.RemoteException, FFSException
/*  7554:      */   {
/*  7555: 7561 */     for (int i = 1;; i++)
/*  7556:      */     {
/*  7557: 7563 */       _Request local_Request = null;
/*  7558: 7564 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7559: 7565 */       boolean bool1 = false;
/*  7560: 7566 */       LocalFrame localLocalFrame = null;
/*  7561: 7567 */       boolean bool2 = false;
/*  7562:      */       try
/*  7563:      */       {
/*  7564: 7570 */         local_Request = __request("cancelCCCompany");
/*  7565: 7571 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7566: 7572 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7567: 7573 */         localLocalStack.setArgsOnLocal(bool1);
/*  7568: 7575 */         if (bool1)
/*  7569:      */         {
/*  7570: 7577 */           localLocalFrame = new LocalFrame(1);
/*  7571: 7578 */           localLocalStack.push(localLocalFrame);
/*  7572:      */         }
/*  7573: 7580 */         if (!bool1)
/*  7574:      */         {
/*  7575: 7582 */           localObject4 = local_Request.getOutputStream();
/*  7576: 7583 */           local_Request.write_value(paramCCCompanyInfo, CCCompanyInfo.class);
/*  7577:      */         }
/*  7578:      */         else
/*  7579:      */         {
/*  7580: 7587 */           localObject4 = local_Request.getOutputStream();
/*  7581: 7588 */           localLocalFrame.add(paramCCCompanyInfo);
/*  7582:      */         }
/*  7583: 7590 */         local_Request.invoke();
/*  7584:      */         Object localObject1;
/*  7585: 7591 */         if (bool1)
/*  7586:      */         {
/*  7587: 7593 */           localObject4 = null;
/*  7588: 7594 */           localObject5 = localLocalFrame.getResult();
/*  7589: 7595 */           if (localObject5 != null) {
/*  7590: 7597 */             localObject4 = (CCCompanyInfo)ObjectVal.clone(localObject5);
/*  7591:      */           }
/*  7592: 7599 */           return localObject4;
/*  7593:      */         }
/*  7594: 7601 */         Object localObject4 = local_Request.getInputStream();
/*  7595:      */         
/*  7596: 7603 */         localObject5 = (CCCompanyInfo)local_Request.read_value(CCCompanyInfo.class);
/*  7597: 7604 */         return localObject5;
/*  7598:      */       }
/*  7599:      */       catch (TRANSIENT localTRANSIENT)
/*  7600:      */       {
/*  7601: 7608 */         if (i == 10) {
/*  7602: 7610 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7603:      */         }
/*  7604:      */       }
/*  7605:      */       catch (UserException localUserException)
/*  7606:      */       {
/*  7607:      */         Object localObject5;
/*  7608: 7615 */         if (local_Request.isRMI())
/*  7609:      */         {
/*  7610: 7617 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7611: 7619 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7612:      */           }
/*  7613:      */         }
/*  7614:      */         else
/*  7615:      */         {
/*  7616: 7624 */           localObject5 = null;
/*  7617: 7625 */           if (bool1)
/*  7618:      */           {
/*  7619: 7627 */             localObject5 = localLocalFrame.getException();
/*  7620: 7628 */             if (localObject5 != null) {
/*  7621: 7630 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  7622:      */             }
/*  7623:      */           }
/*  7624: 7633 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7625:      */           {
/*  7626: 7635 */             if (local_Request.isRMI()) {
/*  7627: 7637 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7628:      */             }
/*  7629: 7641 */             if (bool1)
/*  7630:      */             {
/*  7631: 7643 */               if (localObject5 != null) {
/*  7632: 7643 */                 throw ((FFSException)localObject5);
/*  7633:      */               }
/*  7634:      */             }
/*  7635:      */             else {
/*  7636: 7647 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7637:      */             }
/*  7638:      */           }
/*  7639:      */         }
/*  7640: 7652 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7641:      */       }
/*  7642:      */       catch (SystemException localSystemException)
/*  7643:      */       {
/*  7644: 7656 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7645:      */       }
/*  7646:      */       finally
/*  7647:      */       {
/*  7648: 7660 */         if (local_Request != null) {
/*  7649: 7662 */           local_Request.close();
/*  7650:      */         }
/*  7651: 7664 */         if (bool1) {
/*  7652: 7665 */           localLocalStack.pop(localLocalFrame);
/*  7653:      */         }
/*  7654: 7666 */         localLocalStack.setArgsOnLocal(bool2);
/*  7655:      */       }
/*  7656:      */     }
/*  7657:      */   }
/*  7658:      */   
/*  7659:      */   public CCCompanyInfo modCCCompany(CCCompanyInfo paramCCCompanyInfo)
/*  7660:      */     throws java.rmi.RemoteException, FFSException
/*  7661:      */   {
/*  7662: 7675 */     for (int i = 1;; i++)
/*  7663:      */     {
/*  7664: 7677 */       _Request local_Request = null;
/*  7665: 7678 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7666: 7679 */       boolean bool1 = false;
/*  7667: 7680 */       LocalFrame localLocalFrame = null;
/*  7668: 7681 */       boolean bool2 = false;
/*  7669:      */       try
/*  7670:      */       {
/*  7671: 7684 */         local_Request = __request("modCCCompany");
/*  7672: 7685 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7673: 7686 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7674: 7687 */         localLocalStack.setArgsOnLocal(bool1);
/*  7675: 7689 */         if (bool1)
/*  7676:      */         {
/*  7677: 7691 */           localLocalFrame = new LocalFrame(1);
/*  7678: 7692 */           localLocalStack.push(localLocalFrame);
/*  7679:      */         }
/*  7680: 7694 */         if (!bool1)
/*  7681:      */         {
/*  7682: 7696 */           localObject4 = local_Request.getOutputStream();
/*  7683: 7697 */           local_Request.write_value(paramCCCompanyInfo, CCCompanyInfo.class);
/*  7684:      */         }
/*  7685:      */         else
/*  7686:      */         {
/*  7687: 7701 */           localObject4 = local_Request.getOutputStream();
/*  7688: 7702 */           localLocalFrame.add(paramCCCompanyInfo);
/*  7689:      */         }
/*  7690: 7704 */         local_Request.invoke();
/*  7691:      */         Object localObject1;
/*  7692: 7705 */         if (bool1)
/*  7693:      */         {
/*  7694: 7707 */           localObject4 = null;
/*  7695: 7708 */           localObject5 = localLocalFrame.getResult();
/*  7696: 7709 */           if (localObject5 != null) {
/*  7697: 7711 */             localObject4 = (CCCompanyInfo)ObjectVal.clone(localObject5);
/*  7698:      */           }
/*  7699: 7713 */           return localObject4;
/*  7700:      */         }
/*  7701: 7715 */         Object localObject4 = local_Request.getInputStream();
/*  7702:      */         
/*  7703: 7717 */         localObject5 = (CCCompanyInfo)local_Request.read_value(CCCompanyInfo.class);
/*  7704: 7718 */         return localObject5;
/*  7705:      */       }
/*  7706:      */       catch (TRANSIENT localTRANSIENT)
/*  7707:      */       {
/*  7708: 7722 */         if (i == 10) {
/*  7709: 7724 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7710:      */         }
/*  7711:      */       }
/*  7712:      */       catch (UserException localUserException)
/*  7713:      */       {
/*  7714:      */         Object localObject5;
/*  7715: 7729 */         if (local_Request.isRMI())
/*  7716:      */         {
/*  7717: 7731 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7718: 7733 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7719:      */           }
/*  7720:      */         }
/*  7721:      */         else
/*  7722:      */         {
/*  7723: 7738 */           localObject5 = null;
/*  7724: 7739 */           if (bool1)
/*  7725:      */           {
/*  7726: 7741 */             localObject5 = localLocalFrame.getException();
/*  7727: 7742 */             if (localObject5 != null) {
/*  7728: 7744 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  7729:      */             }
/*  7730:      */           }
/*  7731: 7747 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7732:      */           {
/*  7733: 7749 */             if (local_Request.isRMI()) {
/*  7734: 7751 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7735:      */             }
/*  7736: 7755 */             if (bool1)
/*  7737:      */             {
/*  7738: 7757 */               if (localObject5 != null) {
/*  7739: 7757 */                 throw ((FFSException)localObject5);
/*  7740:      */               }
/*  7741:      */             }
/*  7742:      */             else {
/*  7743: 7761 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7744:      */             }
/*  7745:      */           }
/*  7746:      */         }
/*  7747: 7766 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7748:      */       }
/*  7749:      */       catch (SystemException localSystemException)
/*  7750:      */       {
/*  7751: 7770 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7752:      */       }
/*  7753:      */       finally
/*  7754:      */       {
/*  7755: 7774 */         if (local_Request != null) {
/*  7756: 7776 */           local_Request.close();
/*  7757:      */         }
/*  7758: 7778 */         if (bool1) {
/*  7759: 7779 */           localLocalStack.pop(localLocalFrame);
/*  7760:      */         }
/*  7761: 7780 */         localLocalStack.setArgsOnLocal(bool2);
/*  7762:      */       }
/*  7763:      */     }
/*  7764:      */   }
/*  7765:      */   
/*  7766:      */   public CCCompanyInfo getCCCompany(CCCompanyInfo paramCCCompanyInfo)
/*  7767:      */     throws java.rmi.RemoteException, FFSException
/*  7768:      */   {
/*  7769: 7789 */     for (int i = 1;; i++)
/*  7770:      */     {
/*  7771: 7791 */       _Request local_Request = null;
/*  7772: 7792 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7773: 7793 */       boolean bool1 = false;
/*  7774: 7794 */       LocalFrame localLocalFrame = null;
/*  7775: 7795 */       boolean bool2 = false;
/*  7776:      */       try
/*  7777:      */       {
/*  7778: 7798 */         local_Request = __request("getCCCompany");
/*  7779: 7799 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7780: 7800 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7781: 7801 */         localLocalStack.setArgsOnLocal(bool1);
/*  7782: 7803 */         if (bool1)
/*  7783:      */         {
/*  7784: 7805 */           localLocalFrame = new LocalFrame(1);
/*  7785: 7806 */           localLocalStack.push(localLocalFrame);
/*  7786:      */         }
/*  7787: 7808 */         if (!bool1)
/*  7788:      */         {
/*  7789: 7810 */           localObject4 = local_Request.getOutputStream();
/*  7790: 7811 */           local_Request.write_value(paramCCCompanyInfo, CCCompanyInfo.class);
/*  7791:      */         }
/*  7792:      */         else
/*  7793:      */         {
/*  7794: 7815 */           localObject4 = local_Request.getOutputStream();
/*  7795: 7816 */           localLocalFrame.add(paramCCCompanyInfo);
/*  7796:      */         }
/*  7797: 7818 */         local_Request.invoke();
/*  7798:      */         Object localObject1;
/*  7799: 7819 */         if (bool1)
/*  7800:      */         {
/*  7801: 7821 */           localObject4 = null;
/*  7802: 7822 */           localObject5 = localLocalFrame.getResult();
/*  7803: 7823 */           if (localObject5 != null) {
/*  7804: 7825 */             localObject4 = (CCCompanyInfo)ObjectVal.clone(localObject5);
/*  7805:      */           }
/*  7806: 7827 */           return localObject4;
/*  7807:      */         }
/*  7808: 7829 */         Object localObject4 = local_Request.getInputStream();
/*  7809:      */         
/*  7810: 7831 */         localObject5 = (CCCompanyInfo)local_Request.read_value(CCCompanyInfo.class);
/*  7811: 7832 */         return localObject5;
/*  7812:      */       }
/*  7813:      */       catch (TRANSIENT localTRANSIENT)
/*  7814:      */       {
/*  7815: 7836 */         if (i == 10) {
/*  7816: 7838 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7817:      */         }
/*  7818:      */       }
/*  7819:      */       catch (UserException localUserException)
/*  7820:      */       {
/*  7821:      */         Object localObject5;
/*  7822: 7843 */         if (local_Request.isRMI())
/*  7823:      */         {
/*  7824: 7845 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7825: 7847 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7826:      */           }
/*  7827:      */         }
/*  7828:      */         else
/*  7829:      */         {
/*  7830: 7852 */           localObject5 = null;
/*  7831: 7853 */           if (bool1)
/*  7832:      */           {
/*  7833: 7855 */             localObject5 = localLocalFrame.getException();
/*  7834: 7856 */             if (localObject5 != null) {
/*  7835: 7858 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  7836:      */             }
/*  7837:      */           }
/*  7838: 7861 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7839:      */           {
/*  7840: 7863 */             if (local_Request.isRMI()) {
/*  7841: 7865 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7842:      */             }
/*  7843: 7869 */             if (bool1)
/*  7844:      */             {
/*  7845: 7871 */               if (localObject5 != null) {
/*  7846: 7871 */                 throw ((FFSException)localObject5);
/*  7847:      */               }
/*  7848:      */             }
/*  7849:      */             else {
/*  7850: 7875 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7851:      */             }
/*  7852:      */           }
/*  7853:      */         }
/*  7854: 7880 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7855:      */       }
/*  7856:      */       catch (SystemException localSystemException)
/*  7857:      */       {
/*  7858: 7884 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7859:      */       }
/*  7860:      */       finally
/*  7861:      */       {
/*  7862: 7888 */         if (local_Request != null) {
/*  7863: 7890 */           local_Request.close();
/*  7864:      */         }
/*  7865: 7892 */         if (bool1) {
/*  7866: 7893 */           localLocalStack.pop(localLocalFrame);
/*  7867:      */         }
/*  7868: 7894 */         localLocalStack.setArgsOnLocal(bool2);
/*  7869:      */       }
/*  7870:      */     }
/*  7871:      */   }
/*  7872:      */   
/*  7873:      */   public CCCompanyInfoList getCCCompanyList(CCCompanyInfoList paramCCCompanyInfoList)
/*  7874:      */     throws java.rmi.RemoteException, FFSException
/*  7875:      */   {
/*  7876: 7903 */     for (int i = 1;; i++)
/*  7877:      */     {
/*  7878: 7905 */       _Request local_Request = null;
/*  7879: 7906 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7880: 7907 */       boolean bool1 = false;
/*  7881: 7908 */       LocalFrame localLocalFrame = null;
/*  7882: 7909 */       boolean bool2 = false;
/*  7883:      */       try
/*  7884:      */       {
/*  7885: 7912 */         local_Request = __request("getCCCompanyList");
/*  7886: 7913 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7887: 7914 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7888: 7915 */         localLocalStack.setArgsOnLocal(bool1);
/*  7889: 7917 */         if (bool1)
/*  7890:      */         {
/*  7891: 7919 */           localLocalFrame = new LocalFrame(1);
/*  7892: 7920 */           localLocalStack.push(localLocalFrame);
/*  7893:      */         }
/*  7894: 7922 */         if (!bool1)
/*  7895:      */         {
/*  7896: 7924 */           localObject4 = local_Request.getOutputStream();
/*  7897: 7925 */           local_Request.write_value(paramCCCompanyInfoList, CCCompanyInfoList.class);
/*  7898:      */         }
/*  7899:      */         else
/*  7900:      */         {
/*  7901: 7929 */           localObject4 = local_Request.getOutputStream();
/*  7902: 7930 */           localLocalFrame.add(paramCCCompanyInfoList);
/*  7903:      */         }
/*  7904: 7932 */         local_Request.invoke();
/*  7905:      */         Object localObject1;
/*  7906: 7933 */         if (bool1)
/*  7907:      */         {
/*  7908: 7935 */           localObject4 = null;
/*  7909: 7936 */           localObject5 = localLocalFrame.getResult();
/*  7910: 7937 */           if (localObject5 != null) {
/*  7911: 7939 */             localObject4 = (CCCompanyInfoList)ObjectVal.clone(localObject5);
/*  7912:      */           }
/*  7913: 7941 */           return localObject4;
/*  7914:      */         }
/*  7915: 7943 */         Object localObject4 = local_Request.getInputStream();
/*  7916:      */         
/*  7917: 7945 */         localObject5 = (CCCompanyInfoList)local_Request.read_value(CCCompanyInfoList.class);
/*  7918: 7946 */         return localObject5;
/*  7919:      */       }
/*  7920:      */       catch (TRANSIENT localTRANSIENT)
/*  7921:      */       {
/*  7922: 7950 */         if (i == 10) {
/*  7923: 7952 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  7924:      */         }
/*  7925:      */       }
/*  7926:      */       catch (UserException localUserException)
/*  7927:      */       {
/*  7928:      */         Object localObject5;
/*  7929: 7957 */         if (local_Request.isRMI())
/*  7930:      */         {
/*  7931: 7959 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  7932: 7961 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7933:      */           }
/*  7934:      */         }
/*  7935:      */         else
/*  7936:      */         {
/*  7937: 7966 */           localObject5 = null;
/*  7938: 7967 */           if (bool1)
/*  7939:      */           {
/*  7940: 7969 */             localObject5 = localLocalFrame.getException();
/*  7941: 7970 */             if (localObject5 != null) {
/*  7942: 7972 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  7943:      */             }
/*  7944:      */           }
/*  7945: 7975 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  7946:      */           {
/*  7947: 7977 */             if (local_Request.isRMI()) {
/*  7948: 7979 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  7949:      */             }
/*  7950: 7983 */             if (bool1)
/*  7951:      */             {
/*  7952: 7985 */               if (localObject5 != null) {
/*  7953: 7985 */                 throw ((FFSException)localObject5);
/*  7954:      */               }
/*  7955:      */             }
/*  7956:      */             else {
/*  7957: 7989 */               throw FFSExceptionHelper.read(localUserException.input);
/*  7958:      */             }
/*  7959:      */           }
/*  7960:      */         }
/*  7961: 7994 */         throw new java.rmi.RemoteException(localUserException.type);
/*  7962:      */       }
/*  7963:      */       catch (SystemException localSystemException)
/*  7964:      */       {
/*  7965: 7998 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  7966:      */       }
/*  7967:      */       finally
/*  7968:      */       {
/*  7969: 8002 */         if (local_Request != null) {
/*  7970: 8004 */           local_Request.close();
/*  7971:      */         }
/*  7972: 8006 */         if (bool1) {
/*  7973: 8007 */           localLocalStack.pop(localLocalFrame);
/*  7974:      */         }
/*  7975: 8008 */         localLocalStack.setArgsOnLocal(bool2);
/*  7976:      */       }
/*  7977:      */     }
/*  7978:      */   }
/*  7979:      */   
/*  7980:      */   public CutOffInfo getNextCashConCutOff(String paramString1, String paramString2)
/*  7981:      */     throws java.rmi.RemoteException, FFSException
/*  7982:      */   {
/*  7983: 8018 */     for (int i = 1;; i++)
/*  7984:      */     {
/*  7985: 8020 */       _Request local_Request = null;
/*  7986: 8021 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  7987: 8022 */       boolean bool1 = false;
/*  7988: 8023 */       LocalFrame localLocalFrame = null;
/*  7989: 8024 */       boolean bool2 = false;
/*  7990:      */       try
/*  7991:      */       {
/*  7992: 8027 */         local_Request = __request("getNextCashConCutOff");
/*  7993: 8028 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  7994: 8029 */         bool2 = localLocalStack.isArgsOnLocal();
/*  7995: 8030 */         localLocalStack.setArgsOnLocal(bool1);
/*  7996: 8032 */         if (bool1)
/*  7997:      */         {
/*  7998: 8034 */           localLocalFrame = new LocalFrame(2);
/*  7999: 8035 */           localLocalStack.push(localLocalFrame);
/*  8000:      */         }
/*  8001: 8037 */         if (!bool1)
/*  8002:      */         {
/*  8003: 8039 */           localObject4 = local_Request.getOutputStream();
/*  8004: 8040 */           local_Request.write_string(paramString1);
/*  8005: 8041 */           local_Request.write_string(paramString2);
/*  8006:      */         }
/*  8007:      */         else
/*  8008:      */         {
/*  8009: 8045 */           localObject4 = local_Request.getOutputStream();
/*  8010: 8046 */           localLocalFrame.add(paramString1);
/*  8011: 8047 */           localLocalFrame.add(paramString2);
/*  8012:      */         }
/*  8013: 8049 */         local_Request.invoke();
/*  8014:      */         Object localObject1;
/*  8015: 8050 */         if (bool1)
/*  8016:      */         {
/*  8017: 8052 */           localObject4 = null;
/*  8018: 8053 */           localObject5 = localLocalFrame.getResult();
/*  8019: 8054 */           if (localObject5 != null) {
/*  8020: 8056 */             localObject4 = (CutOffInfo)ObjectVal.clone(localObject5);
/*  8021:      */           }
/*  8022: 8058 */           return localObject4;
/*  8023:      */         }
/*  8024: 8060 */         Object localObject4 = local_Request.getInputStream();
/*  8025:      */         
/*  8026: 8062 */         localObject5 = (CutOffInfo)local_Request.read_value(CutOffInfo.class);
/*  8027: 8063 */         return localObject5;
/*  8028:      */       }
/*  8029:      */       catch (TRANSIENT localTRANSIENT)
/*  8030:      */       {
/*  8031: 8067 */         if (i == 10) {
/*  8032: 8069 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8033:      */         }
/*  8034:      */       }
/*  8035:      */       catch (UserException localUserException)
/*  8036:      */       {
/*  8037:      */         Object localObject5;
/*  8038: 8074 */         if (local_Request.isRMI())
/*  8039:      */         {
/*  8040: 8076 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8041: 8078 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8042:      */           }
/*  8043:      */         }
/*  8044:      */         else
/*  8045:      */         {
/*  8046: 8083 */           localObject5 = null;
/*  8047: 8084 */           if (bool1)
/*  8048:      */           {
/*  8049: 8086 */             localObject5 = localLocalFrame.getException();
/*  8050: 8087 */             if (localObject5 != null) {
/*  8051: 8089 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8052:      */             }
/*  8053:      */           }
/*  8054: 8092 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8055:      */           {
/*  8056: 8094 */             if (local_Request.isRMI()) {
/*  8057: 8096 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8058:      */             }
/*  8059: 8100 */             if (bool1)
/*  8060:      */             {
/*  8061: 8102 */               if (localObject5 != null) {
/*  8062: 8102 */                 throw ((FFSException)localObject5);
/*  8063:      */               }
/*  8064:      */             }
/*  8065:      */             else {
/*  8066: 8106 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8067:      */             }
/*  8068:      */           }
/*  8069:      */         }
/*  8070: 8111 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8071:      */       }
/*  8072:      */       catch (SystemException localSystemException)
/*  8073:      */       {
/*  8074: 8115 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8075:      */       }
/*  8076:      */       finally
/*  8077:      */       {
/*  8078: 8119 */         if (local_Request != null) {
/*  8079: 8121 */           local_Request.close();
/*  8080:      */         }
/*  8081: 8123 */         if (bool1) {
/*  8082: 8124 */           localLocalStack.pop(localLocalFrame);
/*  8083:      */         }
/*  8084: 8125 */         localLocalStack.setArgsOnLocal(bool2);
/*  8085:      */       }
/*  8086:      */     }
/*  8087:      */   }
/*  8088:      */   
/*  8089:      */   public CCCompanyAcctInfo addCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
/*  8090:      */     throws java.rmi.RemoteException, FFSException
/*  8091:      */   {
/*  8092: 8134 */     for (int i = 1;; i++)
/*  8093:      */     {
/*  8094: 8136 */       _Request local_Request = null;
/*  8095: 8137 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8096: 8138 */       boolean bool1 = false;
/*  8097: 8139 */       LocalFrame localLocalFrame = null;
/*  8098: 8140 */       boolean bool2 = false;
/*  8099:      */       try
/*  8100:      */       {
/*  8101: 8143 */         local_Request = __request("addCCCompanyAcct");
/*  8102: 8144 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8103: 8145 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8104: 8146 */         localLocalStack.setArgsOnLocal(bool1);
/*  8105: 8148 */         if (bool1)
/*  8106:      */         {
/*  8107: 8150 */           localLocalFrame = new LocalFrame(1);
/*  8108: 8151 */           localLocalStack.push(localLocalFrame);
/*  8109:      */         }
/*  8110: 8153 */         if (!bool1)
/*  8111:      */         {
/*  8112: 8155 */           localObject4 = local_Request.getOutputStream();
/*  8113: 8156 */           local_Request.write_value(paramCCCompanyAcctInfo, CCCompanyAcctInfo.class);
/*  8114:      */         }
/*  8115:      */         else
/*  8116:      */         {
/*  8117: 8160 */           localObject4 = local_Request.getOutputStream();
/*  8118: 8161 */           localLocalFrame.add(paramCCCompanyAcctInfo);
/*  8119:      */         }
/*  8120: 8163 */         local_Request.invoke();
/*  8121:      */         Object localObject1;
/*  8122: 8164 */         if (bool1)
/*  8123:      */         {
/*  8124: 8166 */           localObject4 = null;
/*  8125: 8167 */           localObject5 = localLocalFrame.getResult();
/*  8126: 8168 */           if (localObject5 != null) {
/*  8127: 8170 */             localObject4 = (CCCompanyAcctInfo)ObjectVal.clone(localObject5);
/*  8128:      */           }
/*  8129: 8172 */           return localObject4;
/*  8130:      */         }
/*  8131: 8174 */         Object localObject4 = local_Request.getInputStream();
/*  8132:      */         
/*  8133: 8176 */         localObject5 = (CCCompanyAcctInfo)local_Request.read_value(CCCompanyAcctInfo.class);
/*  8134: 8177 */         return localObject5;
/*  8135:      */       }
/*  8136:      */       catch (TRANSIENT localTRANSIENT)
/*  8137:      */       {
/*  8138: 8181 */         if (i == 10) {
/*  8139: 8183 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8140:      */         }
/*  8141:      */       }
/*  8142:      */       catch (UserException localUserException)
/*  8143:      */       {
/*  8144:      */         Object localObject5;
/*  8145: 8188 */         if (local_Request.isRMI())
/*  8146:      */         {
/*  8147: 8190 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8148: 8192 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8149:      */           }
/*  8150:      */         }
/*  8151:      */         else
/*  8152:      */         {
/*  8153: 8197 */           localObject5 = null;
/*  8154: 8198 */           if (bool1)
/*  8155:      */           {
/*  8156: 8200 */             localObject5 = localLocalFrame.getException();
/*  8157: 8201 */             if (localObject5 != null) {
/*  8158: 8203 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8159:      */             }
/*  8160:      */           }
/*  8161: 8206 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8162:      */           {
/*  8163: 8208 */             if (local_Request.isRMI()) {
/*  8164: 8210 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8165:      */             }
/*  8166: 8214 */             if (bool1)
/*  8167:      */             {
/*  8168: 8216 */               if (localObject5 != null) {
/*  8169: 8216 */                 throw ((FFSException)localObject5);
/*  8170:      */               }
/*  8171:      */             }
/*  8172:      */             else {
/*  8173: 8220 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8174:      */             }
/*  8175:      */           }
/*  8176:      */         }
/*  8177: 8225 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8178:      */       }
/*  8179:      */       catch (SystemException localSystemException)
/*  8180:      */       {
/*  8181: 8229 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8182:      */       }
/*  8183:      */       finally
/*  8184:      */       {
/*  8185: 8233 */         if (local_Request != null) {
/*  8186: 8235 */           local_Request.close();
/*  8187:      */         }
/*  8188: 8237 */         if (bool1) {
/*  8189: 8238 */           localLocalStack.pop(localLocalFrame);
/*  8190:      */         }
/*  8191: 8239 */         localLocalStack.setArgsOnLocal(bool2);
/*  8192:      */       }
/*  8193:      */     }
/*  8194:      */   }
/*  8195:      */   
/*  8196:      */   public CCCompanyAcctInfo cancelCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
/*  8197:      */     throws java.rmi.RemoteException, FFSException
/*  8198:      */   {
/*  8199: 8248 */     for (int i = 1;; i++)
/*  8200:      */     {
/*  8201: 8250 */       _Request local_Request = null;
/*  8202: 8251 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8203: 8252 */       boolean bool1 = false;
/*  8204: 8253 */       LocalFrame localLocalFrame = null;
/*  8205: 8254 */       boolean bool2 = false;
/*  8206:      */       try
/*  8207:      */       {
/*  8208: 8257 */         local_Request = __request("cancelCCCompanyAcct");
/*  8209: 8258 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8210: 8259 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8211: 8260 */         localLocalStack.setArgsOnLocal(bool1);
/*  8212: 8262 */         if (bool1)
/*  8213:      */         {
/*  8214: 8264 */           localLocalFrame = new LocalFrame(1);
/*  8215: 8265 */           localLocalStack.push(localLocalFrame);
/*  8216:      */         }
/*  8217: 8267 */         if (!bool1)
/*  8218:      */         {
/*  8219: 8269 */           localObject4 = local_Request.getOutputStream();
/*  8220: 8270 */           local_Request.write_value(paramCCCompanyAcctInfo, CCCompanyAcctInfo.class);
/*  8221:      */         }
/*  8222:      */         else
/*  8223:      */         {
/*  8224: 8274 */           localObject4 = local_Request.getOutputStream();
/*  8225: 8275 */           localLocalFrame.add(paramCCCompanyAcctInfo);
/*  8226:      */         }
/*  8227: 8277 */         local_Request.invoke();
/*  8228:      */         Object localObject1;
/*  8229: 8278 */         if (bool1)
/*  8230:      */         {
/*  8231: 8280 */           localObject4 = null;
/*  8232: 8281 */           localObject5 = localLocalFrame.getResult();
/*  8233: 8282 */           if (localObject5 != null) {
/*  8234: 8284 */             localObject4 = (CCCompanyAcctInfo)ObjectVal.clone(localObject5);
/*  8235:      */           }
/*  8236: 8286 */           return localObject4;
/*  8237:      */         }
/*  8238: 8288 */         Object localObject4 = local_Request.getInputStream();
/*  8239:      */         
/*  8240: 8290 */         localObject5 = (CCCompanyAcctInfo)local_Request.read_value(CCCompanyAcctInfo.class);
/*  8241: 8291 */         return localObject5;
/*  8242:      */       }
/*  8243:      */       catch (TRANSIENT localTRANSIENT)
/*  8244:      */       {
/*  8245: 8295 */         if (i == 10) {
/*  8246: 8297 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8247:      */         }
/*  8248:      */       }
/*  8249:      */       catch (UserException localUserException)
/*  8250:      */       {
/*  8251:      */         Object localObject5;
/*  8252: 8302 */         if (local_Request.isRMI())
/*  8253:      */         {
/*  8254: 8304 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8255: 8306 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8256:      */           }
/*  8257:      */         }
/*  8258:      */         else
/*  8259:      */         {
/*  8260: 8311 */           localObject5 = null;
/*  8261: 8312 */           if (bool1)
/*  8262:      */           {
/*  8263: 8314 */             localObject5 = localLocalFrame.getException();
/*  8264: 8315 */             if (localObject5 != null) {
/*  8265: 8317 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8266:      */             }
/*  8267:      */           }
/*  8268: 8320 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8269:      */           {
/*  8270: 8322 */             if (local_Request.isRMI()) {
/*  8271: 8324 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8272:      */             }
/*  8273: 8328 */             if (bool1)
/*  8274:      */             {
/*  8275: 8330 */               if (localObject5 != null) {
/*  8276: 8330 */                 throw ((FFSException)localObject5);
/*  8277:      */               }
/*  8278:      */             }
/*  8279:      */             else {
/*  8280: 8334 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8281:      */             }
/*  8282:      */           }
/*  8283:      */         }
/*  8284: 8339 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8285:      */       }
/*  8286:      */       catch (SystemException localSystemException)
/*  8287:      */       {
/*  8288: 8343 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8289:      */       }
/*  8290:      */       finally
/*  8291:      */       {
/*  8292: 8347 */         if (local_Request != null) {
/*  8293: 8349 */           local_Request.close();
/*  8294:      */         }
/*  8295: 8351 */         if (bool1) {
/*  8296: 8352 */           localLocalStack.pop(localLocalFrame);
/*  8297:      */         }
/*  8298: 8353 */         localLocalStack.setArgsOnLocal(bool2);
/*  8299:      */       }
/*  8300:      */     }
/*  8301:      */   }
/*  8302:      */   
/*  8303:      */   public CCCompanyAcctInfo modCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
/*  8304:      */     throws java.rmi.RemoteException, FFSException
/*  8305:      */   {
/*  8306: 8362 */     for (int i = 1;; i++)
/*  8307:      */     {
/*  8308: 8364 */       _Request local_Request = null;
/*  8309: 8365 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8310: 8366 */       boolean bool1 = false;
/*  8311: 8367 */       LocalFrame localLocalFrame = null;
/*  8312: 8368 */       boolean bool2 = false;
/*  8313:      */       try
/*  8314:      */       {
/*  8315: 8371 */         local_Request = __request("modCCCompanyAcct");
/*  8316: 8372 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8317: 8373 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8318: 8374 */         localLocalStack.setArgsOnLocal(bool1);
/*  8319: 8376 */         if (bool1)
/*  8320:      */         {
/*  8321: 8378 */           localLocalFrame = new LocalFrame(1);
/*  8322: 8379 */           localLocalStack.push(localLocalFrame);
/*  8323:      */         }
/*  8324: 8381 */         if (!bool1)
/*  8325:      */         {
/*  8326: 8383 */           localObject4 = local_Request.getOutputStream();
/*  8327: 8384 */           local_Request.write_value(paramCCCompanyAcctInfo, CCCompanyAcctInfo.class);
/*  8328:      */         }
/*  8329:      */         else
/*  8330:      */         {
/*  8331: 8388 */           localObject4 = local_Request.getOutputStream();
/*  8332: 8389 */           localLocalFrame.add(paramCCCompanyAcctInfo);
/*  8333:      */         }
/*  8334: 8391 */         local_Request.invoke();
/*  8335:      */         Object localObject1;
/*  8336: 8392 */         if (bool1)
/*  8337:      */         {
/*  8338: 8394 */           localObject4 = null;
/*  8339: 8395 */           localObject5 = localLocalFrame.getResult();
/*  8340: 8396 */           if (localObject5 != null) {
/*  8341: 8398 */             localObject4 = (CCCompanyAcctInfo)ObjectVal.clone(localObject5);
/*  8342:      */           }
/*  8343: 8400 */           return localObject4;
/*  8344:      */         }
/*  8345: 8402 */         Object localObject4 = local_Request.getInputStream();
/*  8346:      */         
/*  8347: 8404 */         localObject5 = (CCCompanyAcctInfo)local_Request.read_value(CCCompanyAcctInfo.class);
/*  8348: 8405 */         return localObject5;
/*  8349:      */       }
/*  8350:      */       catch (TRANSIENT localTRANSIENT)
/*  8351:      */       {
/*  8352: 8409 */         if (i == 10) {
/*  8353: 8411 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8354:      */         }
/*  8355:      */       }
/*  8356:      */       catch (UserException localUserException)
/*  8357:      */       {
/*  8358:      */         Object localObject5;
/*  8359: 8416 */         if (local_Request.isRMI())
/*  8360:      */         {
/*  8361: 8418 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8362: 8420 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8363:      */           }
/*  8364:      */         }
/*  8365:      */         else
/*  8366:      */         {
/*  8367: 8425 */           localObject5 = null;
/*  8368: 8426 */           if (bool1)
/*  8369:      */           {
/*  8370: 8428 */             localObject5 = localLocalFrame.getException();
/*  8371: 8429 */             if (localObject5 != null) {
/*  8372: 8431 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8373:      */             }
/*  8374:      */           }
/*  8375: 8434 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8376:      */           {
/*  8377: 8436 */             if (local_Request.isRMI()) {
/*  8378: 8438 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8379:      */             }
/*  8380: 8442 */             if (bool1)
/*  8381:      */             {
/*  8382: 8444 */               if (localObject5 != null) {
/*  8383: 8444 */                 throw ((FFSException)localObject5);
/*  8384:      */               }
/*  8385:      */             }
/*  8386:      */             else {
/*  8387: 8448 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8388:      */             }
/*  8389:      */           }
/*  8390:      */         }
/*  8391: 8453 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8392:      */       }
/*  8393:      */       catch (SystemException localSystemException)
/*  8394:      */       {
/*  8395: 8457 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8396:      */       }
/*  8397:      */       finally
/*  8398:      */       {
/*  8399: 8461 */         if (local_Request != null) {
/*  8400: 8463 */           local_Request.close();
/*  8401:      */         }
/*  8402: 8465 */         if (bool1) {
/*  8403: 8466 */           localLocalStack.pop(localLocalFrame);
/*  8404:      */         }
/*  8405: 8467 */         localLocalStack.setArgsOnLocal(bool2);
/*  8406:      */       }
/*  8407:      */     }
/*  8408:      */   }
/*  8409:      */   
/*  8410:      */   public CCCompanyAcctInfo getCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
/*  8411:      */     throws java.rmi.RemoteException, FFSException
/*  8412:      */   {
/*  8413: 8476 */     for (int i = 1;; i++)
/*  8414:      */     {
/*  8415: 8478 */       _Request local_Request = null;
/*  8416: 8479 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8417: 8480 */       boolean bool1 = false;
/*  8418: 8481 */       LocalFrame localLocalFrame = null;
/*  8419: 8482 */       boolean bool2 = false;
/*  8420:      */       try
/*  8421:      */       {
/*  8422: 8485 */         local_Request = __request("getCCCompanyAcct");
/*  8423: 8486 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8424: 8487 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8425: 8488 */         localLocalStack.setArgsOnLocal(bool1);
/*  8426: 8490 */         if (bool1)
/*  8427:      */         {
/*  8428: 8492 */           localLocalFrame = new LocalFrame(1);
/*  8429: 8493 */           localLocalStack.push(localLocalFrame);
/*  8430:      */         }
/*  8431: 8495 */         if (!bool1)
/*  8432:      */         {
/*  8433: 8497 */           localObject4 = local_Request.getOutputStream();
/*  8434: 8498 */           local_Request.write_value(paramCCCompanyAcctInfo, CCCompanyAcctInfo.class);
/*  8435:      */         }
/*  8436:      */         else
/*  8437:      */         {
/*  8438: 8502 */           localObject4 = local_Request.getOutputStream();
/*  8439: 8503 */           localLocalFrame.add(paramCCCompanyAcctInfo);
/*  8440:      */         }
/*  8441: 8505 */         local_Request.invoke();
/*  8442:      */         Object localObject1;
/*  8443: 8506 */         if (bool1)
/*  8444:      */         {
/*  8445: 8508 */           localObject4 = null;
/*  8446: 8509 */           localObject5 = localLocalFrame.getResult();
/*  8447: 8510 */           if (localObject5 != null) {
/*  8448: 8512 */             localObject4 = (CCCompanyAcctInfo)ObjectVal.clone(localObject5);
/*  8449:      */           }
/*  8450: 8514 */           return localObject4;
/*  8451:      */         }
/*  8452: 8516 */         Object localObject4 = local_Request.getInputStream();
/*  8453:      */         
/*  8454: 8518 */         localObject5 = (CCCompanyAcctInfo)local_Request.read_value(CCCompanyAcctInfo.class);
/*  8455: 8519 */         return localObject5;
/*  8456:      */       }
/*  8457:      */       catch (TRANSIENT localTRANSIENT)
/*  8458:      */       {
/*  8459: 8523 */         if (i == 10) {
/*  8460: 8525 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8461:      */         }
/*  8462:      */       }
/*  8463:      */       catch (UserException localUserException)
/*  8464:      */       {
/*  8465:      */         Object localObject5;
/*  8466: 8530 */         if (local_Request.isRMI())
/*  8467:      */         {
/*  8468: 8532 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8469: 8534 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8470:      */           }
/*  8471:      */         }
/*  8472:      */         else
/*  8473:      */         {
/*  8474: 8539 */           localObject5 = null;
/*  8475: 8540 */           if (bool1)
/*  8476:      */           {
/*  8477: 8542 */             localObject5 = localLocalFrame.getException();
/*  8478: 8543 */             if (localObject5 != null) {
/*  8479: 8545 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8480:      */             }
/*  8481:      */           }
/*  8482: 8548 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8483:      */           {
/*  8484: 8550 */             if (local_Request.isRMI()) {
/*  8485: 8552 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8486:      */             }
/*  8487: 8556 */             if (bool1)
/*  8488:      */             {
/*  8489: 8558 */               if (localObject5 != null) {
/*  8490: 8558 */                 throw ((FFSException)localObject5);
/*  8491:      */               }
/*  8492:      */             }
/*  8493:      */             else {
/*  8494: 8562 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8495:      */             }
/*  8496:      */           }
/*  8497:      */         }
/*  8498: 8567 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8499:      */       }
/*  8500:      */       catch (SystemException localSystemException)
/*  8501:      */       {
/*  8502: 8571 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8503:      */       }
/*  8504:      */       finally
/*  8505:      */       {
/*  8506: 8575 */         if (local_Request != null) {
/*  8507: 8577 */           local_Request.close();
/*  8508:      */         }
/*  8509: 8579 */         if (bool1) {
/*  8510: 8580 */           localLocalStack.pop(localLocalFrame);
/*  8511:      */         }
/*  8512: 8581 */         localLocalStack.setArgsOnLocal(bool2);
/*  8513:      */       }
/*  8514:      */     }
/*  8515:      */   }
/*  8516:      */   
/*  8517:      */   public CCCompanyAcctInfoList getCCCompanyAcctList(CCCompanyAcctInfoList paramCCCompanyAcctInfoList)
/*  8518:      */     throws java.rmi.RemoteException, FFSException
/*  8519:      */   {
/*  8520: 8590 */     for (int i = 1;; i++)
/*  8521:      */     {
/*  8522: 8592 */       _Request local_Request = null;
/*  8523: 8593 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8524: 8594 */       boolean bool1 = false;
/*  8525: 8595 */       LocalFrame localLocalFrame = null;
/*  8526: 8596 */       boolean bool2 = false;
/*  8527:      */       try
/*  8528:      */       {
/*  8529: 8599 */         local_Request = __request("getCCCompanyAcctList");
/*  8530: 8600 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8531: 8601 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8532: 8602 */         localLocalStack.setArgsOnLocal(bool1);
/*  8533: 8604 */         if (bool1)
/*  8534:      */         {
/*  8535: 8606 */           localLocalFrame = new LocalFrame(1);
/*  8536: 8607 */           localLocalStack.push(localLocalFrame);
/*  8537:      */         }
/*  8538: 8609 */         if (!bool1)
/*  8539:      */         {
/*  8540: 8611 */           localObject4 = local_Request.getOutputStream();
/*  8541: 8612 */           local_Request.write_value(paramCCCompanyAcctInfoList, CCCompanyAcctInfoList.class);
/*  8542:      */         }
/*  8543:      */         else
/*  8544:      */         {
/*  8545: 8616 */           localObject4 = local_Request.getOutputStream();
/*  8546: 8617 */           localLocalFrame.add(paramCCCompanyAcctInfoList);
/*  8547:      */         }
/*  8548: 8619 */         local_Request.invoke();
/*  8549:      */         Object localObject1;
/*  8550: 8620 */         if (bool1)
/*  8551:      */         {
/*  8552: 8622 */           localObject4 = null;
/*  8553: 8623 */           localObject5 = localLocalFrame.getResult();
/*  8554: 8624 */           if (localObject5 != null) {
/*  8555: 8626 */             localObject4 = (CCCompanyAcctInfoList)ObjectVal.clone(localObject5);
/*  8556:      */           }
/*  8557: 8628 */           return localObject4;
/*  8558:      */         }
/*  8559: 8630 */         Object localObject4 = local_Request.getInputStream();
/*  8560:      */         
/*  8561: 8632 */         localObject5 = (CCCompanyAcctInfoList)local_Request.read_value(CCCompanyAcctInfoList.class);
/*  8562: 8633 */         return localObject5;
/*  8563:      */       }
/*  8564:      */       catch (TRANSIENT localTRANSIENT)
/*  8565:      */       {
/*  8566: 8637 */         if (i == 10) {
/*  8567: 8639 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8568:      */         }
/*  8569:      */       }
/*  8570:      */       catch (UserException localUserException)
/*  8571:      */       {
/*  8572:      */         Object localObject5;
/*  8573: 8644 */         if (local_Request.isRMI())
/*  8574:      */         {
/*  8575: 8646 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8576: 8648 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8577:      */           }
/*  8578:      */         }
/*  8579:      */         else
/*  8580:      */         {
/*  8581: 8653 */           localObject5 = null;
/*  8582: 8654 */           if (bool1)
/*  8583:      */           {
/*  8584: 8656 */             localObject5 = localLocalFrame.getException();
/*  8585: 8657 */             if (localObject5 != null) {
/*  8586: 8659 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8587:      */             }
/*  8588:      */           }
/*  8589: 8662 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8590:      */           {
/*  8591: 8664 */             if (local_Request.isRMI()) {
/*  8592: 8666 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8593:      */             }
/*  8594: 8670 */             if (bool1)
/*  8595:      */             {
/*  8596: 8672 */               if (localObject5 != null) {
/*  8597: 8672 */                 throw ((FFSException)localObject5);
/*  8598:      */               }
/*  8599:      */             }
/*  8600:      */             else {
/*  8601: 8676 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8602:      */             }
/*  8603:      */           }
/*  8604:      */         }
/*  8605: 8681 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8606:      */       }
/*  8607:      */       catch (SystemException localSystemException)
/*  8608:      */       {
/*  8609: 8685 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8610:      */       }
/*  8611:      */       finally
/*  8612:      */       {
/*  8613: 8689 */         if (local_Request != null) {
/*  8614: 8691 */           local_Request.close();
/*  8615:      */         }
/*  8616: 8693 */         if (bool1) {
/*  8617: 8694 */           localLocalStack.pop(localLocalFrame);
/*  8618:      */         }
/*  8619: 8695 */         localLocalStack.setArgsOnLocal(bool2);
/*  8620:      */       }
/*  8621:      */     }
/*  8622:      */   }
/*  8623:      */   
/*  8624:      */   public CCCompanyCutOffInfo addCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
/*  8625:      */     throws java.rmi.RemoteException, FFSException
/*  8626:      */   {
/*  8627: 8704 */     for (int i = 1;; i++)
/*  8628:      */     {
/*  8629: 8706 */       _Request local_Request = null;
/*  8630: 8707 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8631: 8708 */       boolean bool1 = false;
/*  8632: 8709 */       LocalFrame localLocalFrame = null;
/*  8633: 8710 */       boolean bool2 = false;
/*  8634:      */       try
/*  8635:      */       {
/*  8636: 8713 */         local_Request = __request("addCCCompanyCutOff");
/*  8637: 8714 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8638: 8715 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8639: 8716 */         localLocalStack.setArgsOnLocal(bool1);
/*  8640: 8718 */         if (bool1)
/*  8641:      */         {
/*  8642: 8720 */           localLocalFrame = new LocalFrame(1);
/*  8643: 8721 */           localLocalStack.push(localLocalFrame);
/*  8644:      */         }
/*  8645: 8723 */         if (!bool1)
/*  8646:      */         {
/*  8647: 8725 */           localObject4 = local_Request.getOutputStream();
/*  8648: 8726 */           local_Request.write_value(paramCCCompanyCutOffInfo, CCCompanyCutOffInfo.class);
/*  8649:      */         }
/*  8650:      */         else
/*  8651:      */         {
/*  8652: 8730 */           localObject4 = local_Request.getOutputStream();
/*  8653: 8731 */           localLocalFrame.add(paramCCCompanyCutOffInfo);
/*  8654:      */         }
/*  8655: 8733 */         local_Request.invoke();
/*  8656:      */         Object localObject1;
/*  8657: 8734 */         if (bool1)
/*  8658:      */         {
/*  8659: 8736 */           localObject4 = null;
/*  8660: 8737 */           localObject5 = localLocalFrame.getResult();
/*  8661: 8738 */           if (localObject5 != null) {
/*  8662: 8740 */             localObject4 = (CCCompanyCutOffInfo)ObjectVal.clone(localObject5);
/*  8663:      */           }
/*  8664: 8742 */           return localObject4;
/*  8665:      */         }
/*  8666: 8744 */         Object localObject4 = local_Request.getInputStream();
/*  8667:      */         
/*  8668: 8746 */         localObject5 = (CCCompanyCutOffInfo)local_Request.read_value(CCCompanyCutOffInfo.class);
/*  8669: 8747 */         return localObject5;
/*  8670:      */       }
/*  8671:      */       catch (TRANSIENT localTRANSIENT)
/*  8672:      */       {
/*  8673: 8751 */         if (i == 10) {
/*  8674: 8753 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8675:      */         }
/*  8676:      */       }
/*  8677:      */       catch (UserException localUserException)
/*  8678:      */       {
/*  8679:      */         Object localObject5;
/*  8680: 8758 */         if (local_Request.isRMI())
/*  8681:      */         {
/*  8682: 8760 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8683: 8762 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8684:      */           }
/*  8685:      */         }
/*  8686:      */         else
/*  8687:      */         {
/*  8688: 8767 */           localObject5 = null;
/*  8689: 8768 */           if (bool1)
/*  8690:      */           {
/*  8691: 8770 */             localObject5 = localLocalFrame.getException();
/*  8692: 8771 */             if (localObject5 != null) {
/*  8693: 8773 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8694:      */             }
/*  8695:      */           }
/*  8696: 8776 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8697:      */           {
/*  8698: 8778 */             if (local_Request.isRMI()) {
/*  8699: 8780 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8700:      */             }
/*  8701: 8784 */             if (bool1)
/*  8702:      */             {
/*  8703: 8786 */               if (localObject5 != null) {
/*  8704: 8786 */                 throw ((FFSException)localObject5);
/*  8705:      */               }
/*  8706:      */             }
/*  8707:      */             else {
/*  8708: 8790 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8709:      */             }
/*  8710:      */           }
/*  8711:      */         }
/*  8712: 8795 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8713:      */       }
/*  8714:      */       catch (SystemException localSystemException)
/*  8715:      */       {
/*  8716: 8799 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8717:      */       }
/*  8718:      */       finally
/*  8719:      */       {
/*  8720: 8803 */         if (local_Request != null) {
/*  8721: 8805 */           local_Request.close();
/*  8722:      */         }
/*  8723: 8807 */         if (bool1) {
/*  8724: 8808 */           localLocalStack.pop(localLocalFrame);
/*  8725:      */         }
/*  8726: 8809 */         localLocalStack.setArgsOnLocal(bool2);
/*  8727:      */       }
/*  8728:      */     }
/*  8729:      */   }
/*  8730:      */   
/*  8731:      */   public CCCompanyCutOffInfo cancelCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
/*  8732:      */     throws java.rmi.RemoteException, FFSException
/*  8733:      */   {
/*  8734: 8818 */     for (int i = 1;; i++)
/*  8735:      */     {
/*  8736: 8820 */       _Request local_Request = null;
/*  8737: 8821 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8738: 8822 */       boolean bool1 = false;
/*  8739: 8823 */       LocalFrame localLocalFrame = null;
/*  8740: 8824 */       boolean bool2 = false;
/*  8741:      */       try
/*  8742:      */       {
/*  8743: 8827 */         local_Request = __request("cancelCCCompanyCutOff");
/*  8744: 8828 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8745: 8829 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8746: 8830 */         localLocalStack.setArgsOnLocal(bool1);
/*  8747: 8832 */         if (bool1)
/*  8748:      */         {
/*  8749: 8834 */           localLocalFrame = new LocalFrame(1);
/*  8750: 8835 */           localLocalStack.push(localLocalFrame);
/*  8751:      */         }
/*  8752: 8837 */         if (!bool1)
/*  8753:      */         {
/*  8754: 8839 */           localObject4 = local_Request.getOutputStream();
/*  8755: 8840 */           local_Request.write_value(paramCCCompanyCutOffInfo, CCCompanyCutOffInfo.class);
/*  8756:      */         }
/*  8757:      */         else
/*  8758:      */         {
/*  8759: 8844 */           localObject4 = local_Request.getOutputStream();
/*  8760: 8845 */           localLocalFrame.add(paramCCCompanyCutOffInfo);
/*  8761:      */         }
/*  8762: 8847 */         local_Request.invoke();
/*  8763:      */         Object localObject1;
/*  8764: 8848 */         if (bool1)
/*  8765:      */         {
/*  8766: 8850 */           localObject4 = null;
/*  8767: 8851 */           localObject5 = localLocalFrame.getResult();
/*  8768: 8852 */           if (localObject5 != null) {
/*  8769: 8854 */             localObject4 = (CCCompanyCutOffInfo)ObjectVal.clone(localObject5);
/*  8770:      */           }
/*  8771: 8856 */           return localObject4;
/*  8772:      */         }
/*  8773: 8858 */         Object localObject4 = local_Request.getInputStream();
/*  8774:      */         
/*  8775: 8860 */         localObject5 = (CCCompanyCutOffInfo)local_Request.read_value(CCCompanyCutOffInfo.class);
/*  8776: 8861 */         return localObject5;
/*  8777:      */       }
/*  8778:      */       catch (TRANSIENT localTRANSIENT)
/*  8779:      */       {
/*  8780: 8865 */         if (i == 10) {
/*  8781: 8867 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8782:      */         }
/*  8783:      */       }
/*  8784:      */       catch (UserException localUserException)
/*  8785:      */       {
/*  8786:      */         Object localObject5;
/*  8787: 8872 */         if (local_Request.isRMI())
/*  8788:      */         {
/*  8789: 8874 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8790: 8876 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8791:      */           }
/*  8792:      */         }
/*  8793:      */         else
/*  8794:      */         {
/*  8795: 8881 */           localObject5 = null;
/*  8796: 8882 */           if (bool1)
/*  8797:      */           {
/*  8798: 8884 */             localObject5 = localLocalFrame.getException();
/*  8799: 8885 */             if (localObject5 != null) {
/*  8800: 8887 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8801:      */             }
/*  8802:      */           }
/*  8803: 8890 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8804:      */           {
/*  8805: 8892 */             if (local_Request.isRMI()) {
/*  8806: 8894 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8807:      */             }
/*  8808: 8898 */             if (bool1)
/*  8809:      */             {
/*  8810: 8900 */               if (localObject5 != null) {
/*  8811: 8900 */                 throw ((FFSException)localObject5);
/*  8812:      */               }
/*  8813:      */             }
/*  8814:      */             else {
/*  8815: 8904 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8816:      */             }
/*  8817:      */           }
/*  8818:      */         }
/*  8819: 8909 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8820:      */       }
/*  8821:      */       catch (SystemException localSystemException)
/*  8822:      */       {
/*  8823: 8913 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8824:      */       }
/*  8825:      */       finally
/*  8826:      */       {
/*  8827: 8917 */         if (local_Request != null) {
/*  8828: 8919 */           local_Request.close();
/*  8829:      */         }
/*  8830: 8921 */         if (bool1) {
/*  8831: 8922 */           localLocalStack.pop(localLocalFrame);
/*  8832:      */         }
/*  8833: 8923 */         localLocalStack.setArgsOnLocal(bool2);
/*  8834:      */       }
/*  8835:      */     }
/*  8836:      */   }
/*  8837:      */   
/*  8838:      */   public CCCompanyCutOffInfo getCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
/*  8839:      */     throws java.rmi.RemoteException, FFSException
/*  8840:      */   {
/*  8841: 8932 */     for (int i = 1;; i++)
/*  8842:      */     {
/*  8843: 8934 */       _Request local_Request = null;
/*  8844: 8935 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8845: 8936 */       boolean bool1 = false;
/*  8846: 8937 */       LocalFrame localLocalFrame = null;
/*  8847: 8938 */       boolean bool2 = false;
/*  8848:      */       try
/*  8849:      */       {
/*  8850: 8941 */         local_Request = __request("getCCCompanyCutOff");
/*  8851: 8942 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8852: 8943 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8853: 8944 */         localLocalStack.setArgsOnLocal(bool1);
/*  8854: 8946 */         if (bool1)
/*  8855:      */         {
/*  8856: 8948 */           localLocalFrame = new LocalFrame(1);
/*  8857: 8949 */           localLocalStack.push(localLocalFrame);
/*  8858:      */         }
/*  8859: 8951 */         if (!bool1)
/*  8860:      */         {
/*  8861: 8953 */           localObject4 = local_Request.getOutputStream();
/*  8862: 8954 */           local_Request.write_value(paramCCCompanyCutOffInfo, CCCompanyCutOffInfo.class);
/*  8863:      */         }
/*  8864:      */         else
/*  8865:      */         {
/*  8866: 8958 */           localObject4 = local_Request.getOutputStream();
/*  8867: 8959 */           localLocalFrame.add(paramCCCompanyCutOffInfo);
/*  8868:      */         }
/*  8869: 8961 */         local_Request.invoke();
/*  8870:      */         Object localObject1;
/*  8871: 8962 */         if (bool1)
/*  8872:      */         {
/*  8873: 8964 */           localObject4 = null;
/*  8874: 8965 */           localObject5 = localLocalFrame.getResult();
/*  8875: 8966 */           if (localObject5 != null) {
/*  8876: 8968 */             localObject4 = (CCCompanyCutOffInfo)ObjectVal.clone(localObject5);
/*  8877:      */           }
/*  8878: 8970 */           return localObject4;
/*  8879:      */         }
/*  8880: 8972 */         Object localObject4 = local_Request.getInputStream();
/*  8881:      */         
/*  8882: 8974 */         localObject5 = (CCCompanyCutOffInfo)local_Request.read_value(CCCompanyCutOffInfo.class);
/*  8883: 8975 */         return localObject5;
/*  8884:      */       }
/*  8885:      */       catch (TRANSIENT localTRANSIENT)
/*  8886:      */       {
/*  8887: 8979 */         if (i == 10) {
/*  8888: 8981 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8889:      */         }
/*  8890:      */       }
/*  8891:      */       catch (UserException localUserException)
/*  8892:      */       {
/*  8893:      */         Object localObject5;
/*  8894: 8986 */         if (local_Request.isRMI())
/*  8895:      */         {
/*  8896: 8988 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  8897: 8990 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8898:      */           }
/*  8899:      */         }
/*  8900:      */         else
/*  8901:      */         {
/*  8902: 8995 */           localObject5 = null;
/*  8903: 8996 */           if (bool1)
/*  8904:      */           {
/*  8905: 8998 */             localObject5 = localLocalFrame.getException();
/*  8906: 8999 */             if (localObject5 != null) {
/*  8907: 9001 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  8908:      */             }
/*  8909:      */           }
/*  8910: 9004 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  8911:      */           {
/*  8912: 9006 */             if (local_Request.isRMI()) {
/*  8913: 9008 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  8914:      */             }
/*  8915: 9012 */             if (bool1)
/*  8916:      */             {
/*  8917: 9014 */               if (localObject5 != null) {
/*  8918: 9014 */                 throw ((FFSException)localObject5);
/*  8919:      */               }
/*  8920:      */             }
/*  8921:      */             else {
/*  8922: 9018 */               throw FFSExceptionHelper.read(localUserException.input);
/*  8923:      */             }
/*  8924:      */           }
/*  8925:      */         }
/*  8926: 9023 */         throw new java.rmi.RemoteException(localUserException.type);
/*  8927:      */       }
/*  8928:      */       catch (SystemException localSystemException)
/*  8929:      */       {
/*  8930: 9027 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  8931:      */       }
/*  8932:      */       finally
/*  8933:      */       {
/*  8934: 9031 */         if (local_Request != null) {
/*  8935: 9033 */           local_Request.close();
/*  8936:      */         }
/*  8937: 9035 */         if (bool1) {
/*  8938: 9036 */           localLocalStack.pop(localLocalFrame);
/*  8939:      */         }
/*  8940: 9037 */         localLocalStack.setArgsOnLocal(bool2);
/*  8941:      */       }
/*  8942:      */     }
/*  8943:      */   }
/*  8944:      */   
/*  8945:      */   public CCCompanyCutOffInfoList getCCCompanyCutOffList(CCCompanyCutOffInfoList paramCCCompanyCutOffInfoList)
/*  8946:      */     throws java.rmi.RemoteException, FFSException
/*  8947:      */   {
/*  8948: 9046 */     for (int i = 1;; i++)
/*  8949:      */     {
/*  8950: 9048 */       _Request local_Request = null;
/*  8951: 9049 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  8952: 9050 */       boolean bool1 = false;
/*  8953: 9051 */       LocalFrame localLocalFrame = null;
/*  8954: 9052 */       boolean bool2 = false;
/*  8955:      */       try
/*  8956:      */       {
/*  8957: 9055 */         local_Request = __request("getCCCompanyCutOffList");
/*  8958: 9056 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  8959: 9057 */         bool2 = localLocalStack.isArgsOnLocal();
/*  8960: 9058 */         localLocalStack.setArgsOnLocal(bool1);
/*  8961: 9060 */         if (bool1)
/*  8962:      */         {
/*  8963: 9062 */           localLocalFrame = new LocalFrame(1);
/*  8964: 9063 */           localLocalStack.push(localLocalFrame);
/*  8965:      */         }
/*  8966: 9065 */         if (!bool1)
/*  8967:      */         {
/*  8968: 9067 */           localObject4 = local_Request.getOutputStream();
/*  8969: 9068 */           local_Request.write_value(paramCCCompanyCutOffInfoList, CCCompanyCutOffInfoList.class);
/*  8970:      */         }
/*  8971:      */         else
/*  8972:      */         {
/*  8973: 9072 */           localObject4 = local_Request.getOutputStream();
/*  8974: 9073 */           localLocalFrame.add(paramCCCompanyCutOffInfoList);
/*  8975:      */         }
/*  8976: 9075 */         local_Request.invoke();
/*  8977:      */         Object localObject1;
/*  8978: 9076 */         if (bool1)
/*  8979:      */         {
/*  8980: 9078 */           localObject4 = null;
/*  8981: 9079 */           localObject5 = localLocalFrame.getResult();
/*  8982: 9080 */           if (localObject5 != null) {
/*  8983: 9082 */             localObject4 = (CCCompanyCutOffInfoList)ObjectVal.clone(localObject5);
/*  8984:      */           }
/*  8985: 9084 */           return localObject4;
/*  8986:      */         }
/*  8987: 9086 */         Object localObject4 = local_Request.getInputStream();
/*  8988:      */         
/*  8989: 9088 */         localObject5 = (CCCompanyCutOffInfoList)local_Request.read_value(CCCompanyCutOffInfoList.class);
/*  8990: 9089 */         return localObject5;
/*  8991:      */       }
/*  8992:      */       catch (TRANSIENT localTRANSIENT)
/*  8993:      */       {
/*  8994: 9093 */         if (i == 10) {
/*  8995: 9095 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  8996:      */         }
/*  8997:      */       }
/*  8998:      */       catch (UserException localUserException)
/*  8999:      */       {
/*  9000:      */         Object localObject5;
/*  9001: 9100 */         if (local_Request.isRMI())
/*  9002:      */         {
/*  9003: 9102 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9004: 9104 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9005:      */           }
/*  9006:      */         }
/*  9007:      */         else
/*  9008:      */         {
/*  9009: 9109 */           localObject5 = null;
/*  9010: 9110 */           if (bool1)
/*  9011:      */           {
/*  9012: 9112 */             localObject5 = localLocalFrame.getException();
/*  9013: 9113 */             if (localObject5 != null) {
/*  9014: 9115 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9015:      */             }
/*  9016:      */           }
/*  9017: 9118 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9018:      */           {
/*  9019: 9120 */             if (local_Request.isRMI()) {
/*  9020: 9122 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9021:      */             }
/*  9022: 9126 */             if (bool1)
/*  9023:      */             {
/*  9024: 9128 */               if (localObject5 != null) {
/*  9025: 9128 */                 throw ((FFSException)localObject5);
/*  9026:      */               }
/*  9027:      */             }
/*  9028:      */             else {
/*  9029: 9132 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9030:      */             }
/*  9031:      */           }
/*  9032:      */         }
/*  9033: 9137 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9034:      */       }
/*  9035:      */       catch (SystemException localSystemException)
/*  9036:      */       {
/*  9037: 9141 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9038:      */       }
/*  9039:      */       finally
/*  9040:      */       {
/*  9041: 9145 */         if (local_Request != null) {
/*  9042: 9147 */           local_Request.close();
/*  9043:      */         }
/*  9044: 9149 */         if (bool1) {
/*  9045: 9150 */           localLocalStack.pop(localLocalFrame);
/*  9046:      */         }
/*  9047: 9151 */         localLocalStack.setArgsOnLocal(bool2);
/*  9048:      */       }
/*  9049:      */     }
/*  9050:      */   }
/*  9051:      */   
/*  9052:      */   public CCLocationInfo addCCLocation(CCLocationInfo paramCCLocationInfo)
/*  9053:      */     throws java.rmi.RemoteException, FFSException
/*  9054:      */   {
/*  9055: 9160 */     for (int i = 1;; i++)
/*  9056:      */     {
/*  9057: 9162 */       _Request local_Request = null;
/*  9058: 9163 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9059: 9164 */       boolean bool1 = false;
/*  9060: 9165 */       LocalFrame localLocalFrame = null;
/*  9061: 9166 */       boolean bool2 = false;
/*  9062:      */       try
/*  9063:      */       {
/*  9064: 9169 */         local_Request = __request("addCCLocation");
/*  9065: 9170 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9066: 9171 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9067: 9172 */         localLocalStack.setArgsOnLocal(bool1);
/*  9068: 9174 */         if (bool1)
/*  9069:      */         {
/*  9070: 9176 */           localLocalFrame = new LocalFrame(1);
/*  9071: 9177 */           localLocalStack.push(localLocalFrame);
/*  9072:      */         }
/*  9073: 9179 */         if (!bool1)
/*  9074:      */         {
/*  9075: 9181 */           localObject4 = local_Request.getOutputStream();
/*  9076: 9182 */           local_Request.write_value(paramCCLocationInfo, CCLocationInfo.class);
/*  9077:      */         }
/*  9078:      */         else
/*  9079:      */         {
/*  9080: 9186 */           localObject4 = local_Request.getOutputStream();
/*  9081: 9187 */           localLocalFrame.add(paramCCLocationInfo);
/*  9082:      */         }
/*  9083: 9189 */         local_Request.invoke();
/*  9084:      */         Object localObject1;
/*  9085: 9190 */         if (bool1)
/*  9086:      */         {
/*  9087: 9192 */           localObject4 = null;
/*  9088: 9193 */           localObject5 = localLocalFrame.getResult();
/*  9089: 9194 */           if (localObject5 != null) {
/*  9090: 9196 */             localObject4 = (CCLocationInfo)ObjectVal.clone(localObject5);
/*  9091:      */           }
/*  9092: 9198 */           return localObject4;
/*  9093:      */         }
/*  9094: 9200 */         Object localObject4 = local_Request.getInputStream();
/*  9095:      */         
/*  9096: 9202 */         localObject5 = (CCLocationInfo)local_Request.read_value(CCLocationInfo.class);
/*  9097: 9203 */         return localObject5;
/*  9098:      */       }
/*  9099:      */       catch (TRANSIENT localTRANSIENT)
/*  9100:      */       {
/*  9101: 9207 */         if (i == 10) {
/*  9102: 9209 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9103:      */         }
/*  9104:      */       }
/*  9105:      */       catch (UserException localUserException)
/*  9106:      */       {
/*  9107:      */         Object localObject5;
/*  9108: 9214 */         if (local_Request.isRMI())
/*  9109:      */         {
/*  9110: 9216 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9111: 9218 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9112:      */           }
/*  9113:      */         }
/*  9114:      */         else
/*  9115:      */         {
/*  9116: 9223 */           localObject5 = null;
/*  9117: 9224 */           if (bool1)
/*  9118:      */           {
/*  9119: 9226 */             localObject5 = localLocalFrame.getException();
/*  9120: 9227 */             if (localObject5 != null) {
/*  9121: 9229 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9122:      */             }
/*  9123:      */           }
/*  9124: 9232 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9125:      */           {
/*  9126: 9234 */             if (local_Request.isRMI()) {
/*  9127: 9236 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9128:      */             }
/*  9129: 9240 */             if (bool1)
/*  9130:      */             {
/*  9131: 9242 */               if (localObject5 != null) {
/*  9132: 9242 */                 throw ((FFSException)localObject5);
/*  9133:      */               }
/*  9134:      */             }
/*  9135:      */             else {
/*  9136: 9246 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9137:      */             }
/*  9138:      */           }
/*  9139:      */         }
/*  9140: 9251 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9141:      */       }
/*  9142:      */       catch (SystemException localSystemException)
/*  9143:      */       {
/*  9144: 9255 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9145:      */       }
/*  9146:      */       finally
/*  9147:      */       {
/*  9148: 9259 */         if (local_Request != null) {
/*  9149: 9261 */           local_Request.close();
/*  9150:      */         }
/*  9151: 9263 */         if (bool1) {
/*  9152: 9264 */           localLocalStack.pop(localLocalFrame);
/*  9153:      */         }
/*  9154: 9265 */         localLocalStack.setArgsOnLocal(bool2);
/*  9155:      */       }
/*  9156:      */     }
/*  9157:      */   }
/*  9158:      */   
/*  9159:      */   public CCLocationInfo cancelCCLocation(CCLocationInfo paramCCLocationInfo)
/*  9160:      */     throws java.rmi.RemoteException, FFSException
/*  9161:      */   {
/*  9162: 9274 */     for (int i = 1;; i++)
/*  9163:      */     {
/*  9164: 9276 */       _Request local_Request = null;
/*  9165: 9277 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9166: 9278 */       boolean bool1 = false;
/*  9167: 9279 */       LocalFrame localLocalFrame = null;
/*  9168: 9280 */       boolean bool2 = false;
/*  9169:      */       try
/*  9170:      */       {
/*  9171: 9283 */         local_Request = __request("cancelCCLocation");
/*  9172: 9284 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9173: 9285 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9174: 9286 */         localLocalStack.setArgsOnLocal(bool1);
/*  9175: 9288 */         if (bool1)
/*  9176:      */         {
/*  9177: 9290 */           localLocalFrame = new LocalFrame(1);
/*  9178: 9291 */           localLocalStack.push(localLocalFrame);
/*  9179:      */         }
/*  9180: 9293 */         if (!bool1)
/*  9181:      */         {
/*  9182: 9295 */           localObject4 = local_Request.getOutputStream();
/*  9183: 9296 */           local_Request.write_value(paramCCLocationInfo, CCLocationInfo.class);
/*  9184:      */         }
/*  9185:      */         else
/*  9186:      */         {
/*  9187: 9300 */           localObject4 = local_Request.getOutputStream();
/*  9188: 9301 */           localLocalFrame.add(paramCCLocationInfo);
/*  9189:      */         }
/*  9190: 9303 */         local_Request.invoke();
/*  9191:      */         Object localObject1;
/*  9192: 9304 */         if (bool1)
/*  9193:      */         {
/*  9194: 9306 */           localObject4 = null;
/*  9195: 9307 */           localObject5 = localLocalFrame.getResult();
/*  9196: 9308 */           if (localObject5 != null) {
/*  9197: 9310 */             localObject4 = (CCLocationInfo)ObjectVal.clone(localObject5);
/*  9198:      */           }
/*  9199: 9312 */           return localObject4;
/*  9200:      */         }
/*  9201: 9314 */         Object localObject4 = local_Request.getInputStream();
/*  9202:      */         
/*  9203: 9316 */         localObject5 = (CCLocationInfo)local_Request.read_value(CCLocationInfo.class);
/*  9204: 9317 */         return localObject5;
/*  9205:      */       }
/*  9206:      */       catch (TRANSIENT localTRANSIENT)
/*  9207:      */       {
/*  9208: 9321 */         if (i == 10) {
/*  9209: 9323 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9210:      */         }
/*  9211:      */       }
/*  9212:      */       catch (UserException localUserException)
/*  9213:      */       {
/*  9214:      */         Object localObject5;
/*  9215: 9328 */         if (local_Request.isRMI())
/*  9216:      */         {
/*  9217: 9330 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9218: 9332 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9219:      */           }
/*  9220:      */         }
/*  9221:      */         else
/*  9222:      */         {
/*  9223: 9337 */           localObject5 = null;
/*  9224: 9338 */           if (bool1)
/*  9225:      */           {
/*  9226: 9340 */             localObject5 = localLocalFrame.getException();
/*  9227: 9341 */             if (localObject5 != null) {
/*  9228: 9343 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9229:      */             }
/*  9230:      */           }
/*  9231: 9346 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9232:      */           {
/*  9233: 9348 */             if (local_Request.isRMI()) {
/*  9234: 9350 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9235:      */             }
/*  9236: 9354 */             if (bool1)
/*  9237:      */             {
/*  9238: 9356 */               if (localObject5 != null) {
/*  9239: 9356 */                 throw ((FFSException)localObject5);
/*  9240:      */               }
/*  9241:      */             }
/*  9242:      */             else {
/*  9243: 9360 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9244:      */             }
/*  9245:      */           }
/*  9246:      */         }
/*  9247: 9365 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9248:      */       }
/*  9249:      */       catch (SystemException localSystemException)
/*  9250:      */       {
/*  9251: 9369 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9252:      */       }
/*  9253:      */       finally
/*  9254:      */       {
/*  9255: 9373 */         if (local_Request != null) {
/*  9256: 9375 */           local_Request.close();
/*  9257:      */         }
/*  9258: 9377 */         if (bool1) {
/*  9259: 9378 */           localLocalStack.pop(localLocalFrame);
/*  9260:      */         }
/*  9261: 9379 */         localLocalStack.setArgsOnLocal(bool2);
/*  9262:      */       }
/*  9263:      */     }
/*  9264:      */   }
/*  9265:      */   
/*  9266:      */   public CCLocationInfo modCCLocation(CCLocationInfo paramCCLocationInfo)
/*  9267:      */     throws java.rmi.RemoteException, FFSException
/*  9268:      */   {
/*  9269: 9388 */     for (int i = 1;; i++)
/*  9270:      */     {
/*  9271: 9390 */       _Request local_Request = null;
/*  9272: 9391 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9273: 9392 */       boolean bool1 = false;
/*  9274: 9393 */       LocalFrame localLocalFrame = null;
/*  9275: 9394 */       boolean bool2 = false;
/*  9276:      */       try
/*  9277:      */       {
/*  9278: 9397 */         local_Request = __request("modCCLocation");
/*  9279: 9398 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9280: 9399 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9281: 9400 */         localLocalStack.setArgsOnLocal(bool1);
/*  9282: 9402 */         if (bool1)
/*  9283:      */         {
/*  9284: 9404 */           localLocalFrame = new LocalFrame(1);
/*  9285: 9405 */           localLocalStack.push(localLocalFrame);
/*  9286:      */         }
/*  9287: 9407 */         if (!bool1)
/*  9288:      */         {
/*  9289: 9409 */           localObject4 = local_Request.getOutputStream();
/*  9290: 9410 */           local_Request.write_value(paramCCLocationInfo, CCLocationInfo.class);
/*  9291:      */         }
/*  9292:      */         else
/*  9293:      */         {
/*  9294: 9414 */           localObject4 = local_Request.getOutputStream();
/*  9295: 9415 */           localLocalFrame.add(paramCCLocationInfo);
/*  9296:      */         }
/*  9297: 9417 */         local_Request.invoke();
/*  9298:      */         Object localObject1;
/*  9299: 9418 */         if (bool1)
/*  9300:      */         {
/*  9301: 9420 */           localObject4 = null;
/*  9302: 9421 */           localObject5 = localLocalFrame.getResult();
/*  9303: 9422 */           if (localObject5 != null) {
/*  9304: 9424 */             localObject4 = (CCLocationInfo)ObjectVal.clone(localObject5);
/*  9305:      */           }
/*  9306: 9426 */           return localObject4;
/*  9307:      */         }
/*  9308: 9428 */         Object localObject4 = local_Request.getInputStream();
/*  9309:      */         
/*  9310: 9430 */         localObject5 = (CCLocationInfo)local_Request.read_value(CCLocationInfo.class);
/*  9311: 9431 */         return localObject5;
/*  9312:      */       }
/*  9313:      */       catch (TRANSIENT localTRANSIENT)
/*  9314:      */       {
/*  9315: 9435 */         if (i == 10) {
/*  9316: 9437 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9317:      */         }
/*  9318:      */       }
/*  9319:      */       catch (UserException localUserException)
/*  9320:      */       {
/*  9321:      */         Object localObject5;
/*  9322: 9442 */         if (local_Request.isRMI())
/*  9323:      */         {
/*  9324: 9444 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9325: 9446 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9326:      */           }
/*  9327:      */         }
/*  9328:      */         else
/*  9329:      */         {
/*  9330: 9451 */           localObject5 = null;
/*  9331: 9452 */           if (bool1)
/*  9332:      */           {
/*  9333: 9454 */             localObject5 = localLocalFrame.getException();
/*  9334: 9455 */             if (localObject5 != null) {
/*  9335: 9457 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9336:      */             }
/*  9337:      */           }
/*  9338: 9460 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9339:      */           {
/*  9340: 9462 */             if (local_Request.isRMI()) {
/*  9341: 9464 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9342:      */             }
/*  9343: 9468 */             if (bool1)
/*  9344:      */             {
/*  9345: 9470 */               if (localObject5 != null) {
/*  9346: 9470 */                 throw ((FFSException)localObject5);
/*  9347:      */               }
/*  9348:      */             }
/*  9349:      */             else {
/*  9350: 9474 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9351:      */             }
/*  9352:      */           }
/*  9353:      */         }
/*  9354: 9479 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9355:      */       }
/*  9356:      */       catch (SystemException localSystemException)
/*  9357:      */       {
/*  9358: 9483 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9359:      */       }
/*  9360:      */       finally
/*  9361:      */       {
/*  9362: 9487 */         if (local_Request != null) {
/*  9363: 9489 */           local_Request.close();
/*  9364:      */         }
/*  9365: 9491 */         if (bool1) {
/*  9366: 9492 */           localLocalStack.pop(localLocalFrame);
/*  9367:      */         }
/*  9368: 9493 */         localLocalStack.setArgsOnLocal(bool2);
/*  9369:      */       }
/*  9370:      */     }
/*  9371:      */   }
/*  9372:      */   
/*  9373:      */   public CCLocationInfo getCCLocation(CCLocationInfo paramCCLocationInfo)
/*  9374:      */     throws java.rmi.RemoteException, FFSException
/*  9375:      */   {
/*  9376: 9502 */     for (int i = 1;; i++)
/*  9377:      */     {
/*  9378: 9504 */       _Request local_Request = null;
/*  9379: 9505 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9380: 9506 */       boolean bool1 = false;
/*  9381: 9507 */       LocalFrame localLocalFrame = null;
/*  9382: 9508 */       boolean bool2 = false;
/*  9383:      */       try
/*  9384:      */       {
/*  9385: 9511 */         local_Request = __request("getCCLocation");
/*  9386: 9512 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9387: 9513 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9388: 9514 */         localLocalStack.setArgsOnLocal(bool1);
/*  9389: 9516 */         if (bool1)
/*  9390:      */         {
/*  9391: 9518 */           localLocalFrame = new LocalFrame(1);
/*  9392: 9519 */           localLocalStack.push(localLocalFrame);
/*  9393:      */         }
/*  9394: 9521 */         if (!bool1)
/*  9395:      */         {
/*  9396: 9523 */           localObject4 = local_Request.getOutputStream();
/*  9397: 9524 */           local_Request.write_value(paramCCLocationInfo, CCLocationInfo.class);
/*  9398:      */         }
/*  9399:      */         else
/*  9400:      */         {
/*  9401: 9528 */           localObject4 = local_Request.getOutputStream();
/*  9402: 9529 */           localLocalFrame.add(paramCCLocationInfo);
/*  9403:      */         }
/*  9404: 9531 */         local_Request.invoke();
/*  9405:      */         Object localObject1;
/*  9406: 9532 */         if (bool1)
/*  9407:      */         {
/*  9408: 9534 */           localObject4 = null;
/*  9409: 9535 */           localObject5 = localLocalFrame.getResult();
/*  9410: 9536 */           if (localObject5 != null) {
/*  9411: 9538 */             localObject4 = (CCLocationInfo)ObjectVal.clone(localObject5);
/*  9412:      */           }
/*  9413: 9540 */           return localObject4;
/*  9414:      */         }
/*  9415: 9542 */         Object localObject4 = local_Request.getInputStream();
/*  9416:      */         
/*  9417: 9544 */         localObject5 = (CCLocationInfo)local_Request.read_value(CCLocationInfo.class);
/*  9418: 9545 */         return localObject5;
/*  9419:      */       }
/*  9420:      */       catch (TRANSIENT localTRANSIENT)
/*  9421:      */       {
/*  9422: 9549 */         if (i == 10) {
/*  9423: 9551 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9424:      */         }
/*  9425:      */       }
/*  9426:      */       catch (UserException localUserException)
/*  9427:      */       {
/*  9428:      */         Object localObject5;
/*  9429: 9556 */         if (local_Request.isRMI())
/*  9430:      */         {
/*  9431: 9558 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9432: 9560 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9433:      */           }
/*  9434:      */         }
/*  9435:      */         else
/*  9436:      */         {
/*  9437: 9565 */           localObject5 = null;
/*  9438: 9566 */           if (bool1)
/*  9439:      */           {
/*  9440: 9568 */             localObject5 = localLocalFrame.getException();
/*  9441: 9569 */             if (localObject5 != null) {
/*  9442: 9571 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9443:      */             }
/*  9444:      */           }
/*  9445: 9574 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9446:      */           {
/*  9447: 9576 */             if (local_Request.isRMI()) {
/*  9448: 9578 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9449:      */             }
/*  9450: 9582 */             if (bool1)
/*  9451:      */             {
/*  9452: 9584 */               if (localObject5 != null) {
/*  9453: 9584 */                 throw ((FFSException)localObject5);
/*  9454:      */               }
/*  9455:      */             }
/*  9456:      */             else {
/*  9457: 9588 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9458:      */             }
/*  9459:      */           }
/*  9460:      */         }
/*  9461: 9593 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9462:      */       }
/*  9463:      */       catch (SystemException localSystemException)
/*  9464:      */       {
/*  9465: 9597 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9466:      */       }
/*  9467:      */       finally
/*  9468:      */       {
/*  9469: 9601 */         if (local_Request != null) {
/*  9470: 9603 */           local_Request.close();
/*  9471:      */         }
/*  9472: 9605 */         if (bool1) {
/*  9473: 9606 */           localLocalStack.pop(localLocalFrame);
/*  9474:      */         }
/*  9475: 9607 */         localLocalStack.setArgsOnLocal(bool2);
/*  9476:      */       }
/*  9477:      */     }
/*  9478:      */   }
/*  9479:      */   
/*  9480:      */   public CCLocationInfoList getCCLocationList(CCLocationInfoList paramCCLocationInfoList)
/*  9481:      */     throws java.rmi.RemoteException, FFSException
/*  9482:      */   {
/*  9483: 9616 */     for (int i = 1;; i++)
/*  9484:      */     {
/*  9485: 9618 */       _Request local_Request = null;
/*  9486: 9619 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9487: 9620 */       boolean bool1 = false;
/*  9488: 9621 */       LocalFrame localLocalFrame = null;
/*  9489: 9622 */       boolean bool2 = false;
/*  9490:      */       try
/*  9491:      */       {
/*  9492: 9625 */         local_Request = __request("getCCLocationList");
/*  9493: 9626 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9494: 9627 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9495: 9628 */         localLocalStack.setArgsOnLocal(bool1);
/*  9496: 9630 */         if (bool1)
/*  9497:      */         {
/*  9498: 9632 */           localLocalFrame = new LocalFrame(1);
/*  9499: 9633 */           localLocalStack.push(localLocalFrame);
/*  9500:      */         }
/*  9501: 9635 */         if (!bool1)
/*  9502:      */         {
/*  9503: 9637 */           localObject4 = local_Request.getOutputStream();
/*  9504: 9638 */           local_Request.write_value(paramCCLocationInfoList, CCLocationInfoList.class);
/*  9505:      */         }
/*  9506:      */         else
/*  9507:      */         {
/*  9508: 9642 */           localObject4 = local_Request.getOutputStream();
/*  9509: 9643 */           localLocalFrame.add(paramCCLocationInfoList);
/*  9510:      */         }
/*  9511: 9645 */         local_Request.invoke();
/*  9512:      */         Object localObject1;
/*  9513: 9646 */         if (bool1)
/*  9514:      */         {
/*  9515: 9648 */           localObject4 = null;
/*  9516: 9649 */           localObject5 = localLocalFrame.getResult();
/*  9517: 9650 */           if (localObject5 != null) {
/*  9518: 9652 */             localObject4 = (CCLocationInfoList)ObjectVal.clone(localObject5);
/*  9519:      */           }
/*  9520: 9654 */           return localObject4;
/*  9521:      */         }
/*  9522: 9656 */         Object localObject4 = local_Request.getInputStream();
/*  9523:      */         
/*  9524: 9658 */         localObject5 = (CCLocationInfoList)local_Request.read_value(CCLocationInfoList.class);
/*  9525: 9659 */         return localObject5;
/*  9526:      */       }
/*  9527:      */       catch (TRANSIENT localTRANSIENT)
/*  9528:      */       {
/*  9529: 9663 */         if (i == 10) {
/*  9530: 9665 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9531:      */         }
/*  9532:      */       }
/*  9533:      */       catch (UserException localUserException)
/*  9534:      */       {
/*  9535:      */         Object localObject5;
/*  9536: 9670 */         if (local_Request.isRMI())
/*  9537:      */         {
/*  9538: 9672 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9539: 9674 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9540:      */           }
/*  9541:      */         }
/*  9542:      */         else
/*  9543:      */         {
/*  9544: 9679 */           localObject5 = null;
/*  9545: 9680 */           if (bool1)
/*  9546:      */           {
/*  9547: 9682 */             localObject5 = localLocalFrame.getException();
/*  9548: 9683 */             if (localObject5 != null) {
/*  9549: 9685 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9550:      */             }
/*  9551:      */           }
/*  9552: 9688 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9553:      */           {
/*  9554: 9690 */             if (local_Request.isRMI()) {
/*  9555: 9692 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9556:      */             }
/*  9557: 9696 */             if (bool1)
/*  9558:      */             {
/*  9559: 9698 */               if (localObject5 != null) {
/*  9560: 9698 */                 throw ((FFSException)localObject5);
/*  9561:      */               }
/*  9562:      */             }
/*  9563:      */             else {
/*  9564: 9702 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9565:      */             }
/*  9566:      */           }
/*  9567:      */         }
/*  9568: 9707 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9569:      */       }
/*  9570:      */       catch (SystemException localSystemException)
/*  9571:      */       {
/*  9572: 9711 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9573:      */       }
/*  9574:      */       finally
/*  9575:      */       {
/*  9576: 9715 */         if (local_Request != null) {
/*  9577: 9717 */           local_Request.close();
/*  9578:      */         }
/*  9579: 9719 */         if (bool1) {
/*  9580: 9720 */           localLocalStack.pop(localLocalFrame);
/*  9581:      */         }
/*  9582: 9721 */         localLocalStack.setArgsOnLocal(bool2);
/*  9583:      */       }
/*  9584:      */     }
/*  9585:      */   }
/*  9586:      */   
/*  9587:      */   public CCEntryInfo addCCEntry(CCEntryInfo paramCCEntryInfo)
/*  9588:      */     throws java.rmi.RemoteException, FFSException
/*  9589:      */   {
/*  9590: 9730 */     for (int i = 1;; i++)
/*  9591:      */     {
/*  9592: 9732 */       _Request local_Request = null;
/*  9593: 9733 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9594: 9734 */       boolean bool1 = false;
/*  9595: 9735 */       LocalFrame localLocalFrame = null;
/*  9596: 9736 */       boolean bool2 = false;
/*  9597:      */       try
/*  9598:      */       {
/*  9599: 9739 */         local_Request = __request("addCCEntry");
/*  9600: 9740 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9601: 9741 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9602: 9742 */         localLocalStack.setArgsOnLocal(bool1);
/*  9603: 9744 */         if (bool1)
/*  9604:      */         {
/*  9605: 9746 */           localLocalFrame = new LocalFrame(1);
/*  9606: 9747 */           localLocalStack.push(localLocalFrame);
/*  9607:      */         }
/*  9608: 9749 */         if (!bool1)
/*  9609:      */         {
/*  9610: 9751 */           localObject4 = local_Request.getOutputStream();
/*  9611: 9752 */           local_Request.write_value(paramCCEntryInfo, CCEntryInfo.class);
/*  9612:      */         }
/*  9613:      */         else
/*  9614:      */         {
/*  9615: 9756 */           localObject4 = local_Request.getOutputStream();
/*  9616: 9757 */           localLocalFrame.add(paramCCEntryInfo);
/*  9617:      */         }
/*  9618: 9759 */         local_Request.invoke();
/*  9619:      */         Object localObject1;
/*  9620: 9760 */         if (bool1)
/*  9621:      */         {
/*  9622: 9762 */           localObject4 = null;
/*  9623: 9763 */           localObject5 = localLocalFrame.getResult();
/*  9624: 9764 */           if (localObject5 != null) {
/*  9625: 9766 */             localObject4 = (CCEntryInfo)ObjectVal.clone(localObject5);
/*  9626:      */           }
/*  9627: 9768 */           return localObject4;
/*  9628:      */         }
/*  9629: 9770 */         Object localObject4 = local_Request.getInputStream();
/*  9630:      */         
/*  9631: 9772 */         localObject5 = (CCEntryInfo)local_Request.read_value(CCEntryInfo.class);
/*  9632: 9773 */         return localObject5;
/*  9633:      */       }
/*  9634:      */       catch (TRANSIENT localTRANSIENT)
/*  9635:      */       {
/*  9636: 9777 */         if (i == 10) {
/*  9637: 9779 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9638:      */         }
/*  9639:      */       }
/*  9640:      */       catch (UserException localUserException)
/*  9641:      */       {
/*  9642:      */         Object localObject5;
/*  9643: 9784 */         if (local_Request.isRMI())
/*  9644:      */         {
/*  9645: 9786 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9646: 9788 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9647:      */           }
/*  9648:      */         }
/*  9649:      */         else
/*  9650:      */         {
/*  9651: 9793 */           localObject5 = null;
/*  9652: 9794 */           if (bool1)
/*  9653:      */           {
/*  9654: 9796 */             localObject5 = localLocalFrame.getException();
/*  9655: 9797 */             if (localObject5 != null) {
/*  9656: 9799 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9657:      */             }
/*  9658:      */           }
/*  9659: 9802 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9660:      */           {
/*  9661: 9804 */             if (local_Request.isRMI()) {
/*  9662: 9806 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9663:      */             }
/*  9664: 9810 */             if (bool1)
/*  9665:      */             {
/*  9666: 9812 */               if (localObject5 != null) {
/*  9667: 9812 */                 throw ((FFSException)localObject5);
/*  9668:      */               }
/*  9669:      */             }
/*  9670:      */             else {
/*  9671: 9816 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9672:      */             }
/*  9673:      */           }
/*  9674:      */         }
/*  9675: 9821 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9676:      */       }
/*  9677:      */       catch (SystemException localSystemException)
/*  9678:      */       {
/*  9679: 9825 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9680:      */       }
/*  9681:      */       finally
/*  9682:      */       {
/*  9683: 9829 */         if (local_Request != null) {
/*  9684: 9831 */           local_Request.close();
/*  9685:      */         }
/*  9686: 9833 */         if (bool1) {
/*  9687: 9834 */           localLocalStack.pop(localLocalFrame);
/*  9688:      */         }
/*  9689: 9835 */         localLocalStack.setArgsOnLocal(bool2);
/*  9690:      */       }
/*  9691:      */     }
/*  9692:      */   }
/*  9693:      */   
/*  9694:      */   public CCEntryInfo cancelCCEntry(CCEntryInfo paramCCEntryInfo)
/*  9695:      */     throws java.rmi.RemoteException, FFSException
/*  9696:      */   {
/*  9697: 9844 */     for (int i = 1;; i++)
/*  9698:      */     {
/*  9699: 9846 */       _Request local_Request = null;
/*  9700: 9847 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9701: 9848 */       boolean bool1 = false;
/*  9702: 9849 */       LocalFrame localLocalFrame = null;
/*  9703: 9850 */       boolean bool2 = false;
/*  9704:      */       try
/*  9705:      */       {
/*  9706: 9853 */         local_Request = __request("cancelCCEntry");
/*  9707: 9854 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9708: 9855 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9709: 9856 */         localLocalStack.setArgsOnLocal(bool1);
/*  9710: 9858 */         if (bool1)
/*  9711:      */         {
/*  9712: 9860 */           localLocalFrame = new LocalFrame(1);
/*  9713: 9861 */           localLocalStack.push(localLocalFrame);
/*  9714:      */         }
/*  9715: 9863 */         if (!bool1)
/*  9716:      */         {
/*  9717: 9865 */           localObject4 = local_Request.getOutputStream();
/*  9718: 9866 */           local_Request.write_value(paramCCEntryInfo, CCEntryInfo.class);
/*  9719:      */         }
/*  9720:      */         else
/*  9721:      */         {
/*  9722: 9870 */           localObject4 = local_Request.getOutputStream();
/*  9723: 9871 */           localLocalFrame.add(paramCCEntryInfo);
/*  9724:      */         }
/*  9725: 9873 */         local_Request.invoke();
/*  9726:      */         Object localObject1;
/*  9727: 9874 */         if (bool1)
/*  9728:      */         {
/*  9729: 9876 */           localObject4 = null;
/*  9730: 9877 */           localObject5 = localLocalFrame.getResult();
/*  9731: 9878 */           if (localObject5 != null) {
/*  9732: 9880 */             localObject4 = (CCEntryInfo)ObjectVal.clone(localObject5);
/*  9733:      */           }
/*  9734: 9882 */           return localObject4;
/*  9735:      */         }
/*  9736: 9884 */         Object localObject4 = local_Request.getInputStream();
/*  9737:      */         
/*  9738: 9886 */         localObject5 = (CCEntryInfo)local_Request.read_value(CCEntryInfo.class);
/*  9739: 9887 */         return localObject5;
/*  9740:      */       }
/*  9741:      */       catch (TRANSIENT localTRANSIENT)
/*  9742:      */       {
/*  9743: 9891 */         if (i == 10) {
/*  9744: 9893 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9745:      */         }
/*  9746:      */       }
/*  9747:      */       catch (UserException localUserException)
/*  9748:      */       {
/*  9749:      */         Object localObject5;
/*  9750: 9898 */         if (local_Request.isRMI())
/*  9751:      */         {
/*  9752: 9900 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9753: 9902 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9754:      */           }
/*  9755:      */         }
/*  9756:      */         else
/*  9757:      */         {
/*  9758: 9907 */           localObject5 = null;
/*  9759: 9908 */           if (bool1)
/*  9760:      */           {
/*  9761: 9910 */             localObject5 = localLocalFrame.getException();
/*  9762: 9911 */             if (localObject5 != null) {
/*  9763: 9913 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9764:      */             }
/*  9765:      */           }
/*  9766: 9916 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9767:      */           {
/*  9768: 9918 */             if (local_Request.isRMI()) {
/*  9769: 9920 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9770:      */             }
/*  9771: 9924 */             if (bool1)
/*  9772:      */             {
/*  9773: 9926 */               if (localObject5 != null) {
/*  9774: 9926 */                 throw ((FFSException)localObject5);
/*  9775:      */               }
/*  9776:      */             }
/*  9777:      */             else {
/*  9778: 9930 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9779:      */             }
/*  9780:      */           }
/*  9781:      */         }
/*  9782: 9935 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9783:      */       }
/*  9784:      */       catch (SystemException localSystemException)
/*  9785:      */       {
/*  9786: 9939 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9787:      */       }
/*  9788:      */       finally
/*  9789:      */       {
/*  9790: 9943 */         if (local_Request != null) {
/*  9791: 9945 */           local_Request.close();
/*  9792:      */         }
/*  9793: 9947 */         if (bool1) {
/*  9794: 9948 */           localLocalStack.pop(localLocalFrame);
/*  9795:      */         }
/*  9796: 9949 */         localLocalStack.setArgsOnLocal(bool2);
/*  9797:      */       }
/*  9798:      */     }
/*  9799:      */   }
/*  9800:      */   
/*  9801:      */   public CCEntryInfo modCCEntry(CCEntryInfo paramCCEntryInfo)
/*  9802:      */     throws java.rmi.RemoteException, FFSException
/*  9803:      */   {
/*  9804: 9958 */     for (int i = 1;; i++)
/*  9805:      */     {
/*  9806: 9960 */       _Request local_Request = null;
/*  9807: 9961 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9808: 9962 */       boolean bool1 = false;
/*  9809: 9963 */       LocalFrame localLocalFrame = null;
/*  9810: 9964 */       boolean bool2 = false;
/*  9811:      */       try
/*  9812:      */       {
/*  9813: 9967 */         local_Request = __request("modCCEntry");
/*  9814: 9968 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9815: 9969 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9816: 9970 */         localLocalStack.setArgsOnLocal(bool1);
/*  9817: 9972 */         if (bool1)
/*  9818:      */         {
/*  9819: 9974 */           localLocalFrame = new LocalFrame(1);
/*  9820: 9975 */           localLocalStack.push(localLocalFrame);
/*  9821:      */         }
/*  9822: 9977 */         if (!bool1)
/*  9823:      */         {
/*  9824: 9979 */           localObject4 = local_Request.getOutputStream();
/*  9825: 9980 */           local_Request.write_value(paramCCEntryInfo, CCEntryInfo.class);
/*  9826:      */         }
/*  9827:      */         else
/*  9828:      */         {
/*  9829: 9984 */           localObject4 = local_Request.getOutputStream();
/*  9830: 9985 */           localLocalFrame.add(paramCCEntryInfo);
/*  9831:      */         }
/*  9832: 9987 */         local_Request.invoke();
/*  9833:      */         Object localObject1;
/*  9834: 9988 */         if (bool1)
/*  9835:      */         {
/*  9836: 9990 */           localObject4 = null;
/*  9837: 9991 */           localObject5 = localLocalFrame.getResult();
/*  9838: 9992 */           if (localObject5 != null) {
/*  9839: 9994 */             localObject4 = (CCEntryInfo)ObjectVal.clone(localObject5);
/*  9840:      */           }
/*  9841: 9996 */           return localObject4;
/*  9842:      */         }
/*  9843: 9998 */         Object localObject4 = local_Request.getInputStream();
/*  9844:      */         
/*  9845:10000 */         localObject5 = (CCEntryInfo)local_Request.read_value(CCEntryInfo.class);
/*  9846:10001 */         return localObject5;
/*  9847:      */       }
/*  9848:      */       catch (TRANSIENT localTRANSIENT)
/*  9849:      */       {
/*  9850:10005 */         if (i == 10) {
/*  9851:10007 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9852:      */         }
/*  9853:      */       }
/*  9854:      */       catch (UserException localUserException)
/*  9855:      */       {
/*  9856:      */         Object localObject5;
/*  9857:10012 */         if (local_Request.isRMI())
/*  9858:      */         {
/*  9859:10014 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9860:10016 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9861:      */           }
/*  9862:      */         }
/*  9863:      */         else
/*  9864:      */         {
/*  9865:10021 */           localObject5 = null;
/*  9866:10022 */           if (bool1)
/*  9867:      */           {
/*  9868:10024 */             localObject5 = localLocalFrame.getException();
/*  9869:10025 */             if (localObject5 != null) {
/*  9870:10027 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9871:      */             }
/*  9872:      */           }
/*  9873:10030 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9874:      */           {
/*  9875:10032 */             if (local_Request.isRMI()) {
/*  9876:10034 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9877:      */             }
/*  9878:10038 */             if (bool1)
/*  9879:      */             {
/*  9880:10040 */               if (localObject5 != null) {
/*  9881:10040 */                 throw ((FFSException)localObject5);
/*  9882:      */               }
/*  9883:      */             }
/*  9884:      */             else {
/*  9885:10044 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9886:      */             }
/*  9887:      */           }
/*  9888:      */         }
/*  9889:10049 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9890:      */       }
/*  9891:      */       catch (SystemException localSystemException)
/*  9892:      */       {
/*  9893:10053 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  9894:      */       }
/*  9895:      */       finally
/*  9896:      */       {
/*  9897:10057 */         if (local_Request != null) {
/*  9898:10059 */           local_Request.close();
/*  9899:      */         }
/*  9900:10061 */         if (bool1) {
/*  9901:10062 */           localLocalStack.pop(localLocalFrame);
/*  9902:      */         }
/*  9903:10063 */         localLocalStack.setArgsOnLocal(bool2);
/*  9904:      */       }
/*  9905:      */     }
/*  9906:      */   }
/*  9907:      */   
/*  9908:      */   public CCEntryInfo getCCEntry(CCEntryInfo paramCCEntryInfo)
/*  9909:      */     throws java.rmi.RemoteException, FFSException
/*  9910:      */   {
/*  9911:10072 */     for (int i = 1;; i++)
/*  9912:      */     {
/*  9913:10074 */       _Request local_Request = null;
/*  9914:10075 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  9915:10076 */       boolean bool1 = false;
/*  9916:10077 */       LocalFrame localLocalFrame = null;
/*  9917:10078 */       boolean bool2 = false;
/*  9918:      */       try
/*  9919:      */       {
/*  9920:10081 */         local_Request = __request("getCCEntry");
/*  9921:10082 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  9922:10083 */         bool2 = localLocalStack.isArgsOnLocal();
/*  9923:10084 */         localLocalStack.setArgsOnLocal(bool1);
/*  9924:10086 */         if (bool1)
/*  9925:      */         {
/*  9926:10088 */           localLocalFrame = new LocalFrame(1);
/*  9927:10089 */           localLocalStack.push(localLocalFrame);
/*  9928:      */         }
/*  9929:10091 */         if (!bool1)
/*  9930:      */         {
/*  9931:10093 */           localObject4 = local_Request.getOutputStream();
/*  9932:10094 */           local_Request.write_value(paramCCEntryInfo, CCEntryInfo.class);
/*  9933:      */         }
/*  9934:      */         else
/*  9935:      */         {
/*  9936:10098 */           localObject4 = local_Request.getOutputStream();
/*  9937:10099 */           localLocalFrame.add(paramCCEntryInfo);
/*  9938:      */         }
/*  9939:10101 */         local_Request.invoke();
/*  9940:      */         Object localObject1;
/*  9941:10102 */         if (bool1)
/*  9942:      */         {
/*  9943:10104 */           localObject4 = null;
/*  9944:10105 */           localObject5 = localLocalFrame.getResult();
/*  9945:10106 */           if (localObject5 != null) {
/*  9946:10108 */             localObject4 = (CCEntryInfo)ObjectVal.clone(localObject5);
/*  9947:      */           }
/*  9948:10110 */           return localObject4;
/*  9949:      */         }
/*  9950:10112 */         Object localObject4 = local_Request.getInputStream();
/*  9951:      */         
/*  9952:10114 */         localObject5 = (CCEntryInfo)local_Request.read_value(CCEntryInfo.class);
/*  9953:10115 */         return localObject5;
/*  9954:      */       }
/*  9955:      */       catch (TRANSIENT localTRANSIENT)
/*  9956:      */       {
/*  9957:10119 */         if (i == 10) {
/*  9958:10121 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  9959:      */         }
/*  9960:      */       }
/*  9961:      */       catch (UserException localUserException)
/*  9962:      */       {
/*  9963:      */         Object localObject5;
/*  9964:10126 */         if (local_Request.isRMI())
/*  9965:      */         {
/*  9966:10128 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/*  9967:10130 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9968:      */           }
/*  9969:      */         }
/*  9970:      */         else
/*  9971:      */         {
/*  9972:10135 */           localObject5 = null;
/*  9973:10136 */           if (bool1)
/*  9974:      */           {
/*  9975:10138 */             localObject5 = localLocalFrame.getException();
/*  9976:10139 */             if (localObject5 != null) {
/*  9977:10141 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/*  9978:      */             }
/*  9979:      */           }
/*  9980:10144 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/*  9981:      */           {
/*  9982:10146 */             if (local_Request.isRMI()) {
/*  9983:10148 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/*  9984:      */             }
/*  9985:10152 */             if (bool1)
/*  9986:      */             {
/*  9987:10154 */               if (localObject5 != null) {
/*  9988:10154 */                 throw ((FFSException)localObject5);
/*  9989:      */               }
/*  9990:      */             }
/*  9991:      */             else {
/*  9992:10158 */               throw FFSExceptionHelper.read(localUserException.input);
/*  9993:      */             }
/*  9994:      */           }
/*  9995:      */         }
/*  9996:10163 */         throw new java.rmi.RemoteException(localUserException.type);
/*  9997:      */       }
/*  9998:      */       catch (SystemException localSystemException)
/*  9999:      */       {
/* 10000:10167 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10001:      */       }
/* 10002:      */       finally
/* 10003:      */       {
/* 10004:10171 */         if (local_Request != null) {
/* 10005:10173 */           local_Request.close();
/* 10006:      */         }
/* 10007:10175 */         if (bool1) {
/* 10008:10176 */           localLocalStack.pop(localLocalFrame);
/* 10009:      */         }
/* 10010:10177 */         localLocalStack.setArgsOnLocal(bool2);
/* 10011:      */       }
/* 10012:      */     }
/* 10013:      */   }
/* 10014:      */   
/* 10015:      */   public CCEntryHistInfo getCCEntryHist(CCEntryHistInfo paramCCEntryHistInfo)
/* 10016:      */     throws java.rmi.RemoteException, FFSException
/* 10017:      */   {
/* 10018:10186 */     for (int i = 1;; i++)
/* 10019:      */     {
/* 10020:10188 */       _Request local_Request = null;
/* 10021:10189 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10022:10190 */       boolean bool1 = false;
/* 10023:10191 */       LocalFrame localLocalFrame = null;
/* 10024:10192 */       boolean bool2 = false;
/* 10025:      */       try
/* 10026:      */       {
/* 10027:10195 */         local_Request = __request("getCCEntryHist");
/* 10028:10196 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10029:10197 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10030:10198 */         localLocalStack.setArgsOnLocal(bool1);
/* 10031:10200 */         if (bool1)
/* 10032:      */         {
/* 10033:10202 */           localLocalFrame = new LocalFrame(1);
/* 10034:10203 */           localLocalStack.push(localLocalFrame);
/* 10035:      */         }
/* 10036:10205 */         if (!bool1)
/* 10037:      */         {
/* 10038:10207 */           localObject4 = local_Request.getOutputStream();
/* 10039:10208 */           local_Request.write_value(paramCCEntryHistInfo, CCEntryHistInfo.class);
/* 10040:      */         }
/* 10041:      */         else
/* 10042:      */         {
/* 10043:10212 */           localObject4 = local_Request.getOutputStream();
/* 10044:10213 */           localLocalFrame.add(paramCCEntryHistInfo);
/* 10045:      */         }
/* 10046:10215 */         local_Request.invoke();
/* 10047:      */         Object localObject1;
/* 10048:10216 */         if (bool1)
/* 10049:      */         {
/* 10050:10218 */           localObject4 = null;
/* 10051:10219 */           localObject5 = localLocalFrame.getResult();
/* 10052:10220 */           if (localObject5 != null) {
/* 10053:10222 */             localObject4 = (CCEntryHistInfo)ObjectVal.clone(localObject5);
/* 10054:      */           }
/* 10055:10224 */           return localObject4;
/* 10056:      */         }
/* 10057:10226 */         Object localObject4 = local_Request.getInputStream();
/* 10058:      */         
/* 10059:10228 */         localObject5 = (CCEntryHistInfo)local_Request.read_value(CCEntryHistInfo.class);
/* 10060:10229 */         return localObject5;
/* 10061:      */       }
/* 10062:      */       catch (TRANSIENT localTRANSIENT)
/* 10063:      */       {
/* 10064:10233 */         if (i == 10) {
/* 10065:10235 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10066:      */         }
/* 10067:      */       }
/* 10068:      */       catch (UserException localUserException)
/* 10069:      */       {
/* 10070:      */         Object localObject5;
/* 10071:10240 */         if (local_Request.isRMI())
/* 10072:      */         {
/* 10073:10242 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 10074:10244 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 10075:      */           }
/* 10076:      */         }
/* 10077:      */         else
/* 10078:      */         {
/* 10079:10249 */           localObject5 = null;
/* 10080:10250 */           if (bool1)
/* 10081:      */           {
/* 10082:10252 */             localObject5 = localLocalFrame.getException();
/* 10083:10253 */             if (localObject5 != null) {
/* 10084:10255 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 10085:      */             }
/* 10086:      */           }
/* 10087:10258 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 10088:      */           {
/* 10089:10260 */             if (local_Request.isRMI()) {
/* 10090:10262 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 10091:      */             }
/* 10092:10266 */             if (bool1)
/* 10093:      */             {
/* 10094:10268 */               if (localObject5 != null) {
/* 10095:10268 */                 throw ((FFSException)localObject5);
/* 10096:      */               }
/* 10097:      */             }
/* 10098:      */             else {
/* 10099:10272 */               throw FFSExceptionHelper.read(localUserException.input);
/* 10100:      */             }
/* 10101:      */           }
/* 10102:      */         }
/* 10103:10277 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10104:      */       }
/* 10105:      */       catch (SystemException localSystemException)
/* 10106:      */       {
/* 10107:10281 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10108:      */       }
/* 10109:      */       finally
/* 10110:      */       {
/* 10111:10285 */         if (local_Request != null) {
/* 10112:10287 */           local_Request.close();
/* 10113:      */         }
/* 10114:10289 */         if (bool1) {
/* 10115:10290 */           localLocalStack.pop(localLocalFrame);
/* 10116:      */         }
/* 10117:10291 */         localLocalStack.setArgsOnLocal(bool2);
/* 10118:      */       }
/* 10119:      */     }
/* 10120:      */   }
/* 10121:      */   
/* 10122:      */   public CCEntrySummaryInfoList getCCEntrySummaryList(CCEntrySummaryInfoList paramCCEntrySummaryInfoList)
/* 10123:      */     throws java.rmi.RemoteException, FFSException
/* 10124:      */   {
/* 10125:10300 */     for (int i = 1;; i++)
/* 10126:      */     {
/* 10127:10302 */       _Request local_Request = null;
/* 10128:10303 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10129:10304 */       boolean bool1 = false;
/* 10130:10305 */       LocalFrame localLocalFrame = null;
/* 10131:10306 */       boolean bool2 = false;
/* 10132:      */       try
/* 10133:      */       {
/* 10134:10309 */         local_Request = __request("getCCEntrySummaryList");
/* 10135:10310 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10136:10311 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10137:10312 */         localLocalStack.setArgsOnLocal(bool1);
/* 10138:10314 */         if (bool1)
/* 10139:      */         {
/* 10140:10316 */           localLocalFrame = new LocalFrame(1);
/* 10141:10317 */           localLocalStack.push(localLocalFrame);
/* 10142:      */         }
/* 10143:10319 */         if (!bool1)
/* 10144:      */         {
/* 10145:10321 */           localObject4 = local_Request.getOutputStream();
/* 10146:10322 */           local_Request.write_value(paramCCEntrySummaryInfoList, CCEntrySummaryInfoList.class);
/* 10147:      */         }
/* 10148:      */         else
/* 10149:      */         {
/* 10150:10326 */           localObject4 = local_Request.getOutputStream();
/* 10151:10327 */           localLocalFrame.add(paramCCEntrySummaryInfoList);
/* 10152:      */         }
/* 10153:10329 */         local_Request.invoke();
/* 10154:      */         Object localObject1;
/* 10155:10330 */         if (bool1)
/* 10156:      */         {
/* 10157:10332 */           localObject4 = null;
/* 10158:10333 */           localObject5 = localLocalFrame.getResult();
/* 10159:10334 */           if (localObject5 != null) {
/* 10160:10336 */             localObject4 = (CCEntrySummaryInfoList)ObjectVal.clone(localObject5);
/* 10161:      */           }
/* 10162:10338 */           return localObject4;
/* 10163:      */         }
/* 10164:10340 */         Object localObject4 = local_Request.getInputStream();
/* 10165:      */         
/* 10166:10342 */         localObject5 = (CCEntrySummaryInfoList)local_Request.read_value(CCEntrySummaryInfoList.class);
/* 10167:10343 */         return localObject5;
/* 10168:      */       }
/* 10169:      */       catch (TRANSIENT localTRANSIENT)
/* 10170:      */       {
/* 10171:10347 */         if (i == 10) {
/* 10172:10349 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10173:      */         }
/* 10174:      */       }
/* 10175:      */       catch (UserException localUserException)
/* 10176:      */       {
/* 10177:      */         Object localObject5;
/* 10178:10354 */         if (local_Request.isRMI())
/* 10179:      */         {
/* 10180:10356 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 10181:10358 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 10182:      */           }
/* 10183:      */         }
/* 10184:      */         else
/* 10185:      */         {
/* 10186:10363 */           localObject5 = null;
/* 10187:10364 */           if (bool1)
/* 10188:      */           {
/* 10189:10366 */             localObject5 = localLocalFrame.getException();
/* 10190:10367 */             if (localObject5 != null) {
/* 10191:10369 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 10192:      */             }
/* 10193:      */           }
/* 10194:10372 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 10195:      */           {
/* 10196:10374 */             if (local_Request.isRMI()) {
/* 10197:10376 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 10198:      */             }
/* 10199:10380 */             if (bool1)
/* 10200:      */             {
/* 10201:10382 */               if (localObject5 != null) {
/* 10202:10382 */                 throw ((FFSException)localObject5);
/* 10203:      */               }
/* 10204:      */             }
/* 10205:      */             else {
/* 10206:10386 */               throw FFSExceptionHelper.read(localUserException.input);
/* 10207:      */             }
/* 10208:      */           }
/* 10209:      */         }
/* 10210:10391 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10211:      */       }
/* 10212:      */       catch (SystemException localSystemException)
/* 10213:      */       {
/* 10214:10395 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10215:      */       }
/* 10216:      */       finally
/* 10217:      */       {
/* 10218:10399 */         if (local_Request != null) {
/* 10219:10401 */           local_Request.close();
/* 10220:      */         }
/* 10221:10403 */         if (bool1) {
/* 10222:10404 */           localLocalStack.pop(localLocalFrame);
/* 10223:      */         }
/* 10224:10405 */         localLocalStack.setArgsOnLocal(bool2);
/* 10225:      */       }
/* 10226:      */     }
/* 10227:      */   }
/* 10228:      */   
/* 10229:      */   public TransferInfo addTransfer(TransferInfo paramTransferInfo)
/* 10230:      */     throws java.rmi.RemoteException
/* 10231:      */   {
/* 10232:10414 */     for (int i = 1;; i++)
/* 10233:      */     {
/* 10234:10416 */       _Request local_Request = null;
/* 10235:10417 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10236:10418 */       boolean bool1 = false;
/* 10237:10419 */       LocalFrame localLocalFrame = null;
/* 10238:10420 */       boolean bool2 = false;
/* 10239:      */       try
/* 10240:      */       {
/* 10241:10423 */         local_Request = __request("addTransfer");
/* 10242:10424 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10243:10425 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10244:10426 */         localLocalStack.setArgsOnLocal(bool1);
/* 10245:10428 */         if (bool1)
/* 10246:      */         {
/* 10247:10430 */           localLocalFrame = new LocalFrame(1);
/* 10248:10431 */           localLocalStack.push(localLocalFrame);
/* 10249:      */         }
/* 10250:10433 */         if (!bool1)
/* 10251:      */         {
/* 10252:10435 */           localObject4 = local_Request.getOutputStream();
/* 10253:10436 */           local_Request.write_value(paramTransferInfo, TransferInfo.class);
/* 10254:      */         }
/* 10255:      */         else
/* 10256:      */         {
/* 10257:10440 */           localObject4 = local_Request.getOutputStream();
/* 10258:10441 */           localLocalFrame.add(paramTransferInfo);
/* 10259:      */         }
/* 10260:10443 */         local_Request.invoke();
/* 10261:      */         Object localObject1;
/* 10262:10444 */         if (bool1)
/* 10263:      */         {
/* 10264:10446 */           localObject4 = null;
/* 10265:10447 */           localObject5 = localLocalFrame.getResult();
/* 10266:10448 */           if (localObject5 != null) {
/* 10267:10450 */             localObject4 = (TransferInfo)ObjectVal.clone(localObject5);
/* 10268:      */           }
/* 10269:10452 */           return localObject4;
/* 10270:      */         }
/* 10271:10454 */         Object localObject4 = local_Request.getInputStream();
/* 10272:      */         
/* 10273:10456 */         Object localObject5 = (TransferInfo)local_Request.read_value(TransferInfo.class);
/* 10274:10457 */         return localObject5;
/* 10275:      */       }
/* 10276:      */       catch (TRANSIENT localTRANSIENT)
/* 10277:      */       {
/* 10278:10461 */         if (i == 10) {
/* 10279:10463 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10280:      */         }
/* 10281:      */       }
/* 10282:      */       catch (UserException localUserException)
/* 10283:      */       {
/* 10284:10468 */         local_Request.isRMI();
/* 10285:      */         
/* 10286:      */ 
/* 10287:10471 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10288:      */       }
/* 10289:      */       catch (SystemException localSystemException)
/* 10290:      */       {
/* 10291:10475 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10292:      */       }
/* 10293:      */       finally
/* 10294:      */       {
/* 10295:10479 */         if (local_Request != null) {
/* 10296:10481 */           local_Request.close();
/* 10297:      */         }
/* 10298:10483 */         if (bool1) {
/* 10299:10484 */           localLocalStack.pop(localLocalFrame);
/* 10300:      */         }
/* 10301:10485 */         localLocalStack.setArgsOnLocal(bool2);
/* 10302:      */       }
/* 10303:      */     }
/* 10304:      */   }
/* 10305:      */   
/* 10306:      */   public TransferInfo modTransfer(TransferInfo paramTransferInfo)
/* 10307:      */     throws java.rmi.RemoteException
/* 10308:      */   {
/* 10309:10494 */     for (int i = 1;; i++)
/* 10310:      */     {
/* 10311:10496 */       _Request local_Request = null;
/* 10312:10497 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10313:10498 */       boolean bool1 = false;
/* 10314:10499 */       LocalFrame localLocalFrame = null;
/* 10315:10500 */       boolean bool2 = false;
/* 10316:      */       try
/* 10317:      */       {
/* 10318:10503 */         local_Request = __request("modTransfer");
/* 10319:10504 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10320:10505 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10321:10506 */         localLocalStack.setArgsOnLocal(bool1);
/* 10322:10508 */         if (bool1)
/* 10323:      */         {
/* 10324:10510 */           localLocalFrame = new LocalFrame(1);
/* 10325:10511 */           localLocalStack.push(localLocalFrame);
/* 10326:      */         }
/* 10327:10513 */         if (!bool1)
/* 10328:      */         {
/* 10329:10515 */           localObject4 = local_Request.getOutputStream();
/* 10330:10516 */           local_Request.write_value(paramTransferInfo, TransferInfo.class);
/* 10331:      */         }
/* 10332:      */         else
/* 10333:      */         {
/* 10334:10520 */           localObject4 = local_Request.getOutputStream();
/* 10335:10521 */           localLocalFrame.add(paramTransferInfo);
/* 10336:      */         }
/* 10337:10523 */         local_Request.invoke();
/* 10338:      */         Object localObject1;
/* 10339:10524 */         if (bool1)
/* 10340:      */         {
/* 10341:10526 */           localObject4 = null;
/* 10342:10527 */           localObject5 = localLocalFrame.getResult();
/* 10343:10528 */           if (localObject5 != null) {
/* 10344:10530 */             localObject4 = (TransferInfo)ObjectVal.clone(localObject5);
/* 10345:      */           }
/* 10346:10532 */           return localObject4;
/* 10347:      */         }
/* 10348:10534 */         Object localObject4 = local_Request.getInputStream();
/* 10349:      */         
/* 10350:10536 */         Object localObject5 = (TransferInfo)local_Request.read_value(TransferInfo.class);
/* 10351:10537 */         return localObject5;
/* 10352:      */       }
/* 10353:      */       catch (TRANSIENT localTRANSIENT)
/* 10354:      */       {
/* 10355:10541 */         if (i == 10) {
/* 10356:10543 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10357:      */         }
/* 10358:      */       }
/* 10359:      */       catch (UserException localUserException)
/* 10360:      */       {
/* 10361:10548 */         local_Request.isRMI();
/* 10362:      */         
/* 10363:      */ 
/* 10364:10551 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10365:      */       }
/* 10366:      */       catch (SystemException localSystemException)
/* 10367:      */       {
/* 10368:10555 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10369:      */       }
/* 10370:      */       finally
/* 10371:      */       {
/* 10372:10559 */         if (local_Request != null) {
/* 10373:10561 */           local_Request.close();
/* 10374:      */         }
/* 10375:10563 */         if (bool1) {
/* 10376:10564 */           localLocalStack.pop(localLocalFrame);
/* 10377:      */         }
/* 10378:10565 */         localLocalStack.setArgsOnLocal(bool2);
/* 10379:      */       }
/* 10380:      */     }
/* 10381:      */   }
/* 10382:      */   
/* 10383:      */   public TransferInfo cancTransfer(TransferInfo paramTransferInfo)
/* 10384:      */     throws java.rmi.RemoteException
/* 10385:      */   {
/* 10386:10574 */     for (int i = 1;; i++)
/* 10387:      */     {
/* 10388:10576 */       _Request local_Request = null;
/* 10389:10577 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10390:10578 */       boolean bool1 = false;
/* 10391:10579 */       LocalFrame localLocalFrame = null;
/* 10392:10580 */       boolean bool2 = false;
/* 10393:      */       try
/* 10394:      */       {
/* 10395:10583 */         local_Request = __request("cancTransfer");
/* 10396:10584 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10397:10585 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10398:10586 */         localLocalStack.setArgsOnLocal(bool1);
/* 10399:10588 */         if (bool1)
/* 10400:      */         {
/* 10401:10590 */           localLocalFrame = new LocalFrame(1);
/* 10402:10591 */           localLocalStack.push(localLocalFrame);
/* 10403:      */         }
/* 10404:10593 */         if (!bool1)
/* 10405:      */         {
/* 10406:10595 */           localObject4 = local_Request.getOutputStream();
/* 10407:10596 */           local_Request.write_value(paramTransferInfo, TransferInfo.class);
/* 10408:      */         }
/* 10409:      */         else
/* 10410:      */         {
/* 10411:10600 */           localObject4 = local_Request.getOutputStream();
/* 10412:10601 */           localLocalFrame.add(paramTransferInfo);
/* 10413:      */         }
/* 10414:10603 */         local_Request.invoke();
/* 10415:      */         Object localObject1;
/* 10416:10604 */         if (bool1)
/* 10417:      */         {
/* 10418:10606 */           localObject4 = null;
/* 10419:10607 */           localObject5 = localLocalFrame.getResult();
/* 10420:10608 */           if (localObject5 != null) {
/* 10421:10610 */             localObject4 = (TransferInfo)ObjectVal.clone(localObject5);
/* 10422:      */           }
/* 10423:10612 */           return localObject4;
/* 10424:      */         }
/* 10425:10614 */         Object localObject4 = local_Request.getInputStream();
/* 10426:      */         
/* 10427:10616 */         Object localObject5 = (TransferInfo)local_Request.read_value(TransferInfo.class);
/* 10428:10617 */         return localObject5;
/* 10429:      */       }
/* 10430:      */       catch (TRANSIENT localTRANSIENT)
/* 10431:      */       {
/* 10432:10621 */         if (i == 10) {
/* 10433:10623 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10434:      */         }
/* 10435:      */       }
/* 10436:      */       catch (UserException localUserException)
/* 10437:      */       {
/* 10438:10628 */         local_Request.isRMI();
/* 10439:      */         
/* 10440:      */ 
/* 10441:10631 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10442:      */       }
/* 10443:      */       catch (SystemException localSystemException)
/* 10444:      */       {
/* 10445:10635 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10446:      */       }
/* 10447:      */       finally
/* 10448:      */       {
/* 10449:10639 */         if (local_Request != null) {
/* 10450:10641 */           local_Request.close();
/* 10451:      */         }
/* 10452:10643 */         if (bool1) {
/* 10453:10644 */           localLocalStack.pop(localLocalFrame);
/* 10454:      */         }
/* 10455:10645 */         localLocalStack.setArgsOnLocal(bool2);
/* 10456:      */       }
/* 10457:      */     }
/* 10458:      */   }
/* 10459:      */   
/* 10460:      */   public TransferInfo getTransferBySrvrTId(String paramString1, String paramString2)
/* 10461:      */     throws java.rmi.RemoteException
/* 10462:      */   {
/* 10463:10655 */     for (int i = 1;; i++)
/* 10464:      */     {
/* 10465:10657 */       _Request local_Request = null;
/* 10466:10658 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10467:10659 */       boolean bool1 = false;
/* 10468:10660 */       LocalFrame localLocalFrame = null;
/* 10469:10661 */       boolean bool2 = false;
/* 10470:      */       try
/* 10471:      */       {
/* 10472:10664 */         local_Request = __request("getTransferBySrvrTId__string__string", "getTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue");
/* 10473:10665 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10474:10666 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10475:10667 */         localLocalStack.setArgsOnLocal(bool1);
/* 10476:10669 */         if (bool1)
/* 10477:      */         {
/* 10478:10671 */           localLocalFrame = new LocalFrame(2);
/* 10479:10672 */           localLocalStack.push(localLocalFrame);
/* 10480:      */         }
/* 10481:10674 */         if (!bool1)
/* 10482:      */         {
/* 10483:10676 */           localObject4 = local_Request.getOutputStream();
/* 10484:10677 */           local_Request.write_string(paramString1);
/* 10485:10678 */           local_Request.write_string(paramString2);
/* 10486:      */         }
/* 10487:      */         else
/* 10488:      */         {
/* 10489:10682 */           localObject4 = local_Request.getOutputStream();
/* 10490:10683 */           localLocalFrame.add(paramString1);
/* 10491:10684 */           localLocalFrame.add(paramString2);
/* 10492:      */         }
/* 10493:10686 */         local_Request.invoke();
/* 10494:      */         Object localObject1;
/* 10495:10687 */         if (bool1)
/* 10496:      */         {
/* 10497:10689 */           localObject4 = null;
/* 10498:10690 */           localObject5 = localLocalFrame.getResult();
/* 10499:10691 */           if (localObject5 != null) {
/* 10500:10693 */             localObject4 = (TransferInfo)ObjectVal.clone(localObject5);
/* 10501:      */           }
/* 10502:10695 */           return localObject4;
/* 10503:      */         }
/* 10504:10697 */         Object localObject4 = local_Request.getInputStream();
/* 10505:      */         
/* 10506:10699 */         Object localObject5 = (TransferInfo)local_Request.read_value(TransferInfo.class);
/* 10507:10700 */         return localObject5;
/* 10508:      */       }
/* 10509:      */       catch (TRANSIENT localTRANSIENT)
/* 10510:      */       {
/* 10511:10704 */         if (i == 10) {
/* 10512:10706 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10513:      */         }
/* 10514:      */       }
/* 10515:      */       catch (UserException localUserException)
/* 10516:      */       {
/* 10517:10711 */         local_Request.isRMI();
/* 10518:      */         
/* 10519:      */ 
/* 10520:10714 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10521:      */       }
/* 10522:      */       catch (SystemException localSystemException)
/* 10523:      */       {
/* 10524:10718 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10525:      */       }
/* 10526:      */       finally
/* 10527:      */       {
/* 10528:10722 */         if (local_Request != null) {
/* 10529:10724 */           local_Request.close();
/* 10530:      */         }
/* 10531:10726 */         if (bool1) {
/* 10532:10727 */           localLocalStack.pop(localLocalFrame);
/* 10533:      */         }
/* 10534:10728 */         localLocalStack.setArgsOnLocal(bool2);
/* 10535:      */       }
/* 10536:      */     }
/* 10537:      */   }
/* 10538:      */   
/* 10539:      */   public TransferInfo getTransferBySrvrTId(String paramString)
/* 10540:      */     throws java.rmi.RemoteException
/* 10541:      */   {
/* 10542:10737 */     for (int i = 1;; i++)
/* 10543:      */     {
/* 10544:10739 */       _Request local_Request = null;
/* 10545:10740 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10546:10741 */       boolean bool1 = false;
/* 10547:10742 */       LocalFrame localLocalFrame = null;
/* 10548:10743 */       boolean bool2 = false;
/* 10549:      */       try
/* 10550:      */       {
/* 10551:10746 */         local_Request = __request("getTransferBySrvrTId__string", "getTransferBySrvrTId__CORBA_WStringValue");
/* 10552:10747 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10553:10748 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10554:10749 */         localLocalStack.setArgsOnLocal(bool1);
/* 10555:10751 */         if (bool1)
/* 10556:      */         {
/* 10557:10753 */           localLocalFrame = new LocalFrame(1);
/* 10558:10754 */           localLocalStack.push(localLocalFrame);
/* 10559:      */         }
/* 10560:10756 */         if (!bool1)
/* 10561:      */         {
/* 10562:10758 */           localObject4 = local_Request.getOutputStream();
/* 10563:10759 */           local_Request.write_string(paramString);
/* 10564:      */         }
/* 10565:      */         else
/* 10566:      */         {
/* 10567:10763 */           localObject4 = local_Request.getOutputStream();
/* 10568:10764 */           localLocalFrame.add(paramString);
/* 10569:      */         }
/* 10570:10766 */         local_Request.invoke();
/* 10571:      */         Object localObject1;
/* 10572:10767 */         if (bool1)
/* 10573:      */         {
/* 10574:10769 */           localObject4 = null;
/* 10575:10770 */           localObject5 = localLocalFrame.getResult();
/* 10576:10771 */           if (localObject5 != null) {
/* 10577:10773 */             localObject4 = (TransferInfo)ObjectVal.clone(localObject5);
/* 10578:      */           }
/* 10579:10775 */           return localObject4;
/* 10580:      */         }
/* 10581:10777 */         Object localObject4 = local_Request.getInputStream();
/* 10582:      */         
/* 10583:10779 */         Object localObject5 = (TransferInfo)local_Request.read_value(TransferInfo.class);
/* 10584:10780 */         return localObject5;
/* 10585:      */       }
/* 10586:      */       catch (TRANSIENT localTRANSIENT)
/* 10587:      */       {
/* 10588:10784 */         if (i == 10) {
/* 10589:10786 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10590:      */         }
/* 10591:      */       }
/* 10592:      */       catch (UserException localUserException)
/* 10593:      */       {
/* 10594:10791 */         local_Request.isRMI();
/* 10595:      */         
/* 10596:      */ 
/* 10597:10794 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10598:      */       }
/* 10599:      */       catch (SystemException localSystemException)
/* 10600:      */       {
/* 10601:10798 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10602:      */       }
/* 10603:      */       finally
/* 10604:      */       {
/* 10605:10802 */         if (local_Request != null) {
/* 10606:10804 */           local_Request.close();
/* 10607:      */         }
/* 10608:10806 */         if (bool1) {
/* 10609:10807 */           localLocalStack.pop(localLocalFrame);
/* 10610:      */         }
/* 10611:10808 */         localLocalStack.setArgsOnLocal(bool2);
/* 10612:      */       }
/* 10613:      */     }
/* 10614:      */   }
/* 10615:      */   
/* 10616:      */   public TransferInfo getTransferByTrackingId(String paramString)
/* 10617:      */     throws java.rmi.RemoteException
/* 10618:      */   {
/* 10619:10817 */     for (int i = 1;; i++)
/* 10620:      */     {
/* 10621:10819 */       _Request local_Request = null;
/* 10622:10820 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10623:10821 */       boolean bool1 = false;
/* 10624:10822 */       LocalFrame localLocalFrame = null;
/* 10625:10823 */       boolean bool2 = false;
/* 10626:      */       try
/* 10627:      */       {
/* 10628:10826 */         local_Request = __request("getTransferByTrackingId");
/* 10629:10827 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10630:10828 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10631:10829 */         localLocalStack.setArgsOnLocal(bool1);
/* 10632:10831 */         if (bool1)
/* 10633:      */         {
/* 10634:10833 */           localLocalFrame = new LocalFrame(1);
/* 10635:10834 */           localLocalStack.push(localLocalFrame);
/* 10636:      */         }
/* 10637:10836 */         if (!bool1)
/* 10638:      */         {
/* 10639:10838 */           localObject4 = local_Request.getOutputStream();
/* 10640:10839 */           local_Request.write_string(paramString);
/* 10641:      */         }
/* 10642:      */         else
/* 10643:      */         {
/* 10644:10843 */           localObject4 = local_Request.getOutputStream();
/* 10645:10844 */           localLocalFrame.add(paramString);
/* 10646:      */         }
/* 10647:10846 */         local_Request.invoke();
/* 10648:      */         Object localObject1;
/* 10649:10847 */         if (bool1)
/* 10650:      */         {
/* 10651:10849 */           localObject4 = null;
/* 10652:10850 */           localObject5 = localLocalFrame.getResult();
/* 10653:10851 */           if (localObject5 != null) {
/* 10654:10853 */             localObject4 = (TransferInfo)ObjectVal.clone(localObject5);
/* 10655:      */           }
/* 10656:10855 */           return localObject4;
/* 10657:      */         }
/* 10658:10857 */         Object localObject4 = local_Request.getInputStream();
/* 10659:      */         
/* 10660:10859 */         Object localObject5 = (TransferInfo)local_Request.read_value(TransferInfo.class);
/* 10661:10860 */         return localObject5;
/* 10662:      */       }
/* 10663:      */       catch (TRANSIENT localTRANSIENT)
/* 10664:      */       {
/* 10665:10864 */         if (i == 10) {
/* 10666:10866 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10667:      */         }
/* 10668:      */       }
/* 10669:      */       catch (UserException localUserException)
/* 10670:      */       {
/* 10671:10871 */         local_Request.isRMI();
/* 10672:      */         
/* 10673:      */ 
/* 10674:10874 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10675:      */       }
/* 10676:      */       catch (SystemException localSystemException)
/* 10677:      */       {
/* 10678:10878 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10679:      */       }
/* 10680:      */       finally
/* 10681:      */       {
/* 10682:10882 */         if (local_Request != null) {
/* 10683:10884 */           local_Request.close();
/* 10684:      */         }
/* 10685:10886 */         if (bool1) {
/* 10686:10887 */           localLocalStack.pop(localLocalFrame);
/* 10687:      */         }
/* 10688:10888 */         localLocalStack.setArgsOnLocal(bool2);
/* 10689:      */       }
/* 10690:      */     }
/* 10691:      */   }
/* 10692:      */   
/* 10693:      */   public TransferInfo[] getTransfersBySrvrTId(String[] paramArrayOfString)
/* 10694:      */     throws java.rmi.RemoteException
/* 10695:      */   {
/* 10696:10897 */     for (int i = 1;; i++)
/* 10697:      */     {
/* 10698:10899 */       _Request local_Request = null;
/* 10699:10900 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10700:10901 */       boolean bool1 = false;
/* 10701:10902 */       LocalFrame localLocalFrame = null;
/* 10702:10903 */       boolean bool2 = false;
/* 10703:      */       try
/* 10704:      */       {
/* 10705:10906 */         local_Request = __request("getTransfersBySrvrTId");
/* 10706:10907 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10707:10908 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10708:10909 */         localLocalStack.setArgsOnLocal(bool1);
/* 10709:10911 */         if (bool1)
/* 10710:      */         {
/* 10711:10913 */           localLocalFrame = new LocalFrame(1);
/* 10712:10914 */           localLocalStack.push(localLocalFrame);
/* 10713:      */         }
/* 10714:10916 */         if (!bool1)
/* 10715:      */         {
/* 10716:10918 */           localObject4 = local_Request.getOutputStream();
/* 10717:10919 */           if (local_Request.isRMI()) {
/* 10718:10919 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 10719:      */           } else {
/* 10720:10919 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 10721:      */           }
/* 10722:      */         }
/* 10723:      */         else
/* 10724:      */         {
/* 10725:10923 */           localObject4 = local_Request.getOutputStream();
/* 10726:10924 */           localLocalFrame.add(paramArrayOfString);
/* 10727:      */         }
/* 10728:10926 */         local_Request.invoke();
/* 10729:      */         Object localObject5;
/* 10730:      */         Object localObject1;
/* 10731:10927 */         if (bool1)
/* 10732:      */         {
/* 10733:10929 */           localObject4 = null;
/* 10734:10930 */           localObject5 = localLocalFrame.getResult();
/* 10735:10931 */           if (localObject5 != null) {
/* 10736:10933 */             localObject4 = (TransferInfo[])ObjectVal.clone(localObject5);
/* 10737:      */           }
/* 10738:10935 */           return localObject4;
/* 10739:      */         }
/* 10740:10937 */         Object localObject4 = local_Request.getInputStream();
/* 10741:10939 */         if (local_Request.isRMI()) {
/* 10742:10939 */           localObject5 = (TransferInfo[])local_Request.read_value(new TransferInfo[0].getClass());
/* 10743:      */         } else {
/* 10744:10939 */           localObject5 = TransferInfoSeqHelper.read((InputStream)localObject4);
/* 10745:      */         }
/* 10746:10940 */         return localObject5;
/* 10747:      */       }
/* 10748:      */       catch (TRANSIENT localTRANSIENT)
/* 10749:      */       {
/* 10750:10944 */         if (i == 10) {
/* 10751:10946 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10752:      */         }
/* 10753:      */       }
/* 10754:      */       catch (UserException localUserException)
/* 10755:      */       {
/* 10756:10951 */         local_Request.isRMI();
/* 10757:      */         
/* 10758:      */ 
/* 10759:10954 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10760:      */       }
/* 10761:      */       catch (SystemException localSystemException)
/* 10762:      */       {
/* 10763:10958 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10764:      */       }
/* 10765:      */       finally
/* 10766:      */       {
/* 10767:10962 */         if (local_Request != null) {
/* 10768:10964 */           local_Request.close();
/* 10769:      */         }
/* 10770:10966 */         if (bool1) {
/* 10771:10967 */           localLocalStack.pop(localLocalFrame);
/* 10772:      */         }
/* 10773:10968 */         localLocalStack.setArgsOnLocal(bool2);
/* 10774:      */       }
/* 10775:      */     }
/* 10776:      */   }
/* 10777:      */   
/* 10778:      */   public TransferInfo[] getTransfersByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
/* 10779:      */     throws java.rmi.RemoteException
/* 10780:      */   {
/* 10781:10978 */     for (int i = 1;; i++)
/* 10782:      */     {
/* 10783:10980 */       _Request local_Request = null;
/* 10784:10981 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10785:10982 */       boolean bool1 = false;
/* 10786:10983 */       LocalFrame localLocalFrame = null;
/* 10787:10984 */       boolean bool2 = false;
/* 10788:      */       try
/* 10789:      */       {
/* 10790:10987 */         local_Request = __request("getTransfersByRecSrvrTId__StringSeq__boolean", "getTransfersByRecSrvrTId__org_omg_boxedRMI_CORBA_seq1_WStringValue__boolean");
/* 10791:10988 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10792:10989 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10793:10990 */         localLocalStack.setArgsOnLocal(bool1);
/* 10794:10992 */         if (bool1)
/* 10795:      */         {
/* 10796:10994 */           localLocalFrame = new LocalFrame(2);
/* 10797:10995 */           localLocalStack.push(localLocalFrame);
/* 10798:      */         }
/* 10799:10997 */         if (!bool1)
/* 10800:      */         {
/* 10801:10999 */           localObject4 = local_Request.getOutputStream();
/* 10802:11000 */           if (local_Request.isRMI()) {
/* 10803:11000 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 10804:      */           } else {
/* 10805:11000 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 10806:      */           }
/* 10807:11001 */           ((OutputStream)localObject4).write_boolean(paramBoolean);
/* 10808:      */         }
/* 10809:      */         else
/* 10810:      */         {
/* 10811:11005 */           localObject4 = local_Request.getOutputStream();
/* 10812:11006 */           localLocalFrame.add(paramArrayOfString);
/* 10813:11007 */           localLocalFrame.add(paramBoolean);
/* 10814:      */         }
/* 10815:11009 */         local_Request.invoke();
/* 10816:      */         Object localObject5;
/* 10817:      */         Object localObject1;
/* 10818:11010 */         if (bool1)
/* 10819:      */         {
/* 10820:11012 */           localObject4 = null;
/* 10821:11013 */           localObject5 = localLocalFrame.getResult();
/* 10822:11014 */           if (localObject5 != null) {
/* 10823:11016 */             localObject4 = (TransferInfo[])ObjectVal.clone(localObject5);
/* 10824:      */           }
/* 10825:11018 */           return localObject4;
/* 10826:      */         }
/* 10827:11020 */         Object localObject4 = local_Request.getInputStream();
/* 10828:11022 */         if (local_Request.isRMI()) {
/* 10829:11022 */           localObject5 = (TransferInfo[])local_Request.read_value(new TransferInfo[0].getClass());
/* 10830:      */         } else {
/* 10831:11022 */           localObject5 = TransferInfoSeqHelper.read((InputStream)localObject4);
/* 10832:      */         }
/* 10833:11023 */         return localObject5;
/* 10834:      */       }
/* 10835:      */       catch (TRANSIENT localTRANSIENT)
/* 10836:      */       {
/* 10837:11027 */         if (i == 10) {
/* 10838:11029 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10839:      */         }
/* 10840:      */       }
/* 10841:      */       catch (UserException localUserException)
/* 10842:      */       {
/* 10843:11034 */         local_Request.isRMI();
/* 10844:      */         
/* 10845:      */ 
/* 10846:11037 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10847:      */       }
/* 10848:      */       catch (SystemException localSystemException)
/* 10849:      */       {
/* 10850:11041 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10851:      */       }
/* 10852:      */       finally
/* 10853:      */       {
/* 10854:11045 */         if (local_Request != null) {
/* 10855:11047 */           local_Request.close();
/* 10856:      */         }
/* 10857:11049 */         if (bool1) {
/* 10858:11050 */           localLocalStack.pop(localLocalFrame);
/* 10859:      */         }
/* 10860:11051 */         localLocalStack.setArgsOnLocal(bool2);
/* 10861:      */       }
/* 10862:      */     }
/* 10863:      */   }
/* 10864:      */   
/* 10865:      */   public TransferInfo[] getTransfersByRecSrvrTId(String paramString, boolean paramBoolean)
/* 10866:      */     throws java.rmi.RemoteException
/* 10867:      */   {
/* 10868:11061 */     for (int i = 1;; i++)
/* 10869:      */     {
/* 10870:11063 */       _Request local_Request = null;
/* 10871:11064 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10872:11065 */       boolean bool1 = false;
/* 10873:11066 */       LocalFrame localLocalFrame = null;
/* 10874:11067 */       boolean bool2 = false;
/* 10875:      */       try
/* 10876:      */       {
/* 10877:11070 */         local_Request = __request("getTransfersByRecSrvrTId__string__boolean", "getTransfersByRecSrvrTId__CORBA_WStringValue__boolean");
/* 10878:11071 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10879:11072 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10880:11073 */         localLocalStack.setArgsOnLocal(bool1);
/* 10881:11075 */         if (bool1)
/* 10882:      */         {
/* 10883:11077 */           localLocalFrame = new LocalFrame(2);
/* 10884:11078 */           localLocalStack.push(localLocalFrame);
/* 10885:      */         }
/* 10886:11080 */         if (!bool1)
/* 10887:      */         {
/* 10888:11082 */           localObject4 = local_Request.getOutputStream();
/* 10889:11083 */           local_Request.write_string(paramString);
/* 10890:11084 */           ((OutputStream)localObject4).write_boolean(paramBoolean);
/* 10891:      */         }
/* 10892:      */         else
/* 10893:      */         {
/* 10894:11088 */           localObject4 = local_Request.getOutputStream();
/* 10895:11089 */           localLocalFrame.add(paramString);
/* 10896:11090 */           localLocalFrame.add(paramBoolean);
/* 10897:      */         }
/* 10898:11092 */         local_Request.invoke();
/* 10899:      */         Object localObject5;
/* 10900:      */         Object localObject1;
/* 10901:11093 */         if (bool1)
/* 10902:      */         {
/* 10903:11095 */           localObject4 = null;
/* 10904:11096 */           localObject5 = localLocalFrame.getResult();
/* 10905:11097 */           if (localObject5 != null) {
/* 10906:11099 */             localObject4 = (TransferInfo[])ObjectVal.clone(localObject5);
/* 10907:      */           }
/* 10908:11101 */           return localObject4;
/* 10909:      */         }
/* 10910:11103 */         Object localObject4 = local_Request.getInputStream();
/* 10911:11105 */         if (local_Request.isRMI()) {
/* 10912:11105 */           localObject5 = (TransferInfo[])local_Request.read_value(new TransferInfo[0].getClass());
/* 10913:      */         } else {
/* 10914:11105 */           localObject5 = TransferInfoSeqHelper.read((InputStream)localObject4);
/* 10915:      */         }
/* 10916:11106 */         return localObject5;
/* 10917:      */       }
/* 10918:      */       catch (TRANSIENT localTRANSIENT)
/* 10919:      */       {
/* 10920:11110 */         if (i == 10) {
/* 10921:11112 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 10922:      */         }
/* 10923:      */       }
/* 10924:      */       catch (UserException localUserException)
/* 10925:      */       {
/* 10926:11117 */         local_Request.isRMI();
/* 10927:      */         
/* 10928:      */ 
/* 10929:11120 */         throw new java.rmi.RemoteException(localUserException.type);
/* 10930:      */       }
/* 10931:      */       catch (SystemException localSystemException)
/* 10932:      */       {
/* 10933:11124 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 10934:      */       }
/* 10935:      */       finally
/* 10936:      */       {
/* 10937:11128 */         if (local_Request != null) {
/* 10938:11130 */           local_Request.close();
/* 10939:      */         }
/* 10940:11132 */         if (bool1) {
/* 10941:11133 */           localLocalStack.pop(localLocalFrame);
/* 10942:      */         }
/* 10943:11134 */         localLocalStack.setArgsOnLocal(bool2);
/* 10944:      */       }
/* 10945:      */     }
/* 10946:      */   }
/* 10947:      */   
/* 10948:      */   public TransferInfo[] getTransfersByTrackingId(String[] paramArrayOfString)
/* 10949:      */     throws java.rmi.RemoteException
/* 10950:      */   {
/* 10951:11143 */     for (int i = 1;; i++)
/* 10952:      */     {
/* 10953:11145 */       _Request local_Request = null;
/* 10954:11146 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 10955:11147 */       boolean bool1 = false;
/* 10956:11148 */       LocalFrame localLocalFrame = null;
/* 10957:11149 */       boolean bool2 = false;
/* 10958:      */       try
/* 10959:      */       {
/* 10960:11152 */         local_Request = __request("getTransfersByTrackingId");
/* 10961:11153 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 10962:11154 */         bool2 = localLocalStack.isArgsOnLocal();
/* 10963:11155 */         localLocalStack.setArgsOnLocal(bool1);
/* 10964:11157 */         if (bool1)
/* 10965:      */         {
/* 10966:11159 */           localLocalFrame = new LocalFrame(1);
/* 10967:11160 */           localLocalStack.push(localLocalFrame);
/* 10968:      */         }
/* 10969:11162 */         if (!bool1)
/* 10970:      */         {
/* 10971:11164 */           localObject4 = local_Request.getOutputStream();
/* 10972:11165 */           if (local_Request.isRMI()) {
/* 10973:11165 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 10974:      */           } else {
/* 10975:11165 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 10976:      */           }
/* 10977:      */         }
/* 10978:      */         else
/* 10979:      */         {
/* 10980:11169 */           localObject4 = local_Request.getOutputStream();
/* 10981:11170 */           localLocalFrame.add(paramArrayOfString);
/* 10982:      */         }
/* 10983:11172 */         local_Request.invoke();
/* 10984:      */         Object localObject5;
/* 10985:      */         Object localObject1;
/* 10986:11173 */         if (bool1)
/* 10987:      */         {
/* 10988:11175 */           localObject4 = null;
/* 10989:11176 */           localObject5 = localLocalFrame.getResult();
/* 10990:11177 */           if (localObject5 != null) {
/* 10991:11179 */             localObject4 = (TransferInfo[])ObjectVal.clone(localObject5);
/* 10992:      */           }
/* 10993:11181 */           return localObject4;
/* 10994:      */         }
/* 10995:11183 */         Object localObject4 = local_Request.getInputStream();
/* 10996:11185 */         if (local_Request.isRMI()) {
/* 10997:11185 */           localObject5 = (TransferInfo[])local_Request.read_value(new TransferInfo[0].getClass());
/* 10998:      */         } else {
/* 10999:11185 */           localObject5 = TransferInfoSeqHelper.read((InputStream)localObject4);
/* 11000:      */         }
/* 11001:11186 */         return localObject5;
/* 11002:      */       }
/* 11003:      */       catch (TRANSIENT localTRANSIENT)
/* 11004:      */       {
/* 11005:11190 */         if (i == 10) {
/* 11006:11192 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11007:      */         }
/* 11008:      */       }
/* 11009:      */       catch (UserException localUserException)
/* 11010:      */       {
/* 11011:11197 */         local_Request.isRMI();
/* 11012:      */         
/* 11013:      */ 
/* 11014:11200 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11015:      */       }
/* 11016:      */       catch (SystemException localSystemException)
/* 11017:      */       {
/* 11018:11204 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11019:      */       }
/* 11020:      */       finally
/* 11021:      */       {
/* 11022:11208 */         if (local_Request != null) {
/* 11023:11210 */           local_Request.close();
/* 11024:      */         }
/* 11025:11212 */         if (bool1) {
/* 11026:11213 */           localLocalStack.pop(localLocalFrame);
/* 11027:      */         }
/* 11028:11214 */         localLocalStack.setArgsOnLocal(bool2);
/* 11029:      */       }
/* 11030:      */     }
/* 11031:      */   }
/* 11032:      */   
/* 11033:      */   public BPWHist getTransferHistory(BPWHist paramBPWHist)
/* 11034:      */     throws java.rmi.RemoteException
/* 11035:      */   {
/* 11036:11223 */     for (int i = 1;; i++)
/* 11037:      */     {
/* 11038:11225 */       _Request local_Request = null;
/* 11039:11226 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11040:11227 */       boolean bool1 = false;
/* 11041:11228 */       LocalFrame localLocalFrame = null;
/* 11042:11229 */       boolean bool2 = false;
/* 11043:      */       try
/* 11044:      */       {
/* 11045:11232 */         local_Request = __request("getTransferHistory");
/* 11046:11233 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11047:11234 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11048:11235 */         localLocalStack.setArgsOnLocal(bool1);
/* 11049:11237 */         if (bool1)
/* 11050:      */         {
/* 11051:11239 */           localLocalFrame = new LocalFrame(1);
/* 11052:11240 */           localLocalStack.push(localLocalFrame);
/* 11053:      */         }
/* 11054:11242 */         if (!bool1)
/* 11055:      */         {
/* 11056:11244 */           localObject4 = local_Request.getOutputStream();
/* 11057:11245 */           if (local_Request.isRMI()) {
/* 11058:11245 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/* 11059:      */           } else {
/* 11060:11245 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/* 11061:      */           }
/* 11062:      */         }
/* 11063:      */         else
/* 11064:      */         {
/* 11065:11249 */           localObject4 = local_Request.getOutputStream();
/* 11066:11250 */           localLocalFrame.add(paramBPWHist);
/* 11067:      */         }
/* 11068:11252 */         local_Request.invoke();
/* 11069:      */         Object localObject5;
/* 11070:      */         Object localObject1;
/* 11071:11253 */         if (bool1)
/* 11072:      */         {
/* 11073:11255 */           localObject4 = null;
/* 11074:11256 */           localObject5 = localLocalFrame.getResult();
/* 11075:11257 */           if (localObject5 != null) {
/* 11076:11259 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/* 11077:      */           }
/* 11078:11261 */           return localObject4;
/* 11079:      */         }
/* 11080:11263 */         Object localObject4 = local_Request.getInputStream();
/* 11081:11265 */         if (local_Request.isRMI()) {
/* 11082:11265 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/* 11083:      */         } else {
/* 11084:11265 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/* 11085:      */         }
/* 11086:11266 */         return localObject5;
/* 11087:      */       }
/* 11088:      */       catch (TRANSIENT localTRANSIENT)
/* 11089:      */       {
/* 11090:11270 */         if (i == 10) {
/* 11091:11272 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11092:      */         }
/* 11093:      */       }
/* 11094:      */       catch (UserException localUserException)
/* 11095:      */       {
/* 11096:11277 */         local_Request.isRMI();
/* 11097:      */         
/* 11098:      */ 
/* 11099:11280 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11100:      */       }
/* 11101:      */       catch (SystemException localSystemException)
/* 11102:      */       {
/* 11103:11284 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11104:      */       }
/* 11105:      */       finally
/* 11106:      */       {
/* 11107:11288 */         if (local_Request != null) {
/* 11108:11290 */           local_Request.close();
/* 11109:      */         }
/* 11110:11292 */         if (bool1) {
/* 11111:11293 */           localLocalStack.pop(localLocalFrame);
/* 11112:      */         }
/* 11113:11294 */         localLocalStack.setArgsOnLocal(bool2);
/* 11114:      */       }
/* 11115:      */     }
/* 11116:      */   }
/* 11117:      */   
/* 11118:      */   public TransferInfo[] addTransfers(TransferInfo[] paramArrayOfTransferInfo)
/* 11119:      */     throws java.rmi.RemoteException
/* 11120:      */   {
/* 11121:11303 */     for (int i = 1;; i++)
/* 11122:      */     {
/* 11123:11305 */       _Request local_Request = null;
/* 11124:11306 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11125:11307 */       boolean bool1 = false;
/* 11126:11308 */       LocalFrame localLocalFrame = null;
/* 11127:11309 */       boolean bool2 = false;
/* 11128:      */       try
/* 11129:      */       {
/* 11130:11312 */         local_Request = __request("addTransfers");
/* 11131:11313 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11132:11314 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11133:11315 */         localLocalStack.setArgsOnLocal(bool1);
/* 11134:11317 */         if (bool1)
/* 11135:      */         {
/* 11136:11319 */           localLocalFrame = new LocalFrame(1);
/* 11137:11320 */           localLocalStack.push(localLocalFrame);
/* 11138:      */         }
/* 11139:11322 */         if (!bool1)
/* 11140:      */         {
/* 11141:11324 */           localObject4 = local_Request.getOutputStream();
/* 11142:11325 */           if (local_Request.isRMI()) {
/* 11143:11325 */             local_Request.write_value(paramArrayOfTransferInfo, new TransferInfo[0].getClass());
/* 11144:      */           } else {
/* 11145:11325 */             TransferInfoSeqHelper.write((OutputStream)localObject4, paramArrayOfTransferInfo);
/* 11146:      */           }
/* 11147:      */         }
/* 11148:      */         else
/* 11149:      */         {
/* 11150:11329 */           localObject4 = local_Request.getOutputStream();
/* 11151:11330 */           localLocalFrame.add(paramArrayOfTransferInfo);
/* 11152:      */         }
/* 11153:11332 */         local_Request.invoke();
/* 11154:      */         Object localObject5;
/* 11155:      */         Object localObject1;
/* 11156:11333 */         if (bool1)
/* 11157:      */         {
/* 11158:11335 */           localObject4 = null;
/* 11159:11336 */           localObject5 = localLocalFrame.getResult();
/* 11160:11337 */           if (localObject5 != null) {
/* 11161:11339 */             localObject4 = (TransferInfo[])ObjectVal.clone(localObject5);
/* 11162:      */           }
/* 11163:11341 */           return localObject4;
/* 11164:      */         }
/* 11165:11343 */         Object localObject4 = local_Request.getInputStream();
/* 11166:11345 */         if (local_Request.isRMI()) {
/* 11167:11345 */           localObject5 = (TransferInfo[])local_Request.read_value(new TransferInfo[0].getClass());
/* 11168:      */         } else {
/* 11169:11345 */           localObject5 = TransferInfoSeqHelper.read((InputStream)localObject4);
/* 11170:      */         }
/* 11171:11346 */         return localObject5;
/* 11172:      */       }
/* 11173:      */       catch (TRANSIENT localTRANSIENT)
/* 11174:      */       {
/* 11175:11350 */         if (i == 10) {
/* 11176:11352 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11177:      */         }
/* 11178:      */       }
/* 11179:      */       catch (UserException localUserException)
/* 11180:      */       {
/* 11181:11357 */         local_Request.isRMI();
/* 11182:      */         
/* 11183:      */ 
/* 11184:11360 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11185:      */       }
/* 11186:      */       catch (SystemException localSystemException)
/* 11187:      */       {
/* 11188:11364 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11189:      */       }
/* 11190:      */       finally
/* 11191:      */       {
/* 11192:11368 */         if (local_Request != null) {
/* 11193:11370 */           local_Request.close();
/* 11194:      */         }
/* 11195:11372 */         if (bool1) {
/* 11196:11373 */           localLocalStack.pop(localLocalFrame);
/* 11197:      */         }
/* 11198:11374 */         localLocalStack.setArgsOnLocal(bool2);
/* 11199:      */       }
/* 11200:      */     }
/* 11201:      */   }
/* 11202:      */   
/* 11203:      */   public RecTransferInfo addRecTransfer(RecTransferInfo paramRecTransferInfo)
/* 11204:      */     throws java.rmi.RemoteException
/* 11205:      */   {
/* 11206:11383 */     for (int i = 1;; i++)
/* 11207:      */     {
/* 11208:11385 */       _Request local_Request = null;
/* 11209:11386 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11210:11387 */       boolean bool1 = false;
/* 11211:11388 */       LocalFrame localLocalFrame = null;
/* 11212:11389 */       boolean bool2 = false;
/* 11213:      */       try
/* 11214:      */       {
/* 11215:11392 */         local_Request = __request("addRecTransfer");
/* 11216:11393 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11217:11394 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11218:11395 */         localLocalStack.setArgsOnLocal(bool1);
/* 11219:11397 */         if (bool1)
/* 11220:      */         {
/* 11221:11399 */           localLocalFrame = new LocalFrame(1);
/* 11222:11400 */           localLocalStack.push(localLocalFrame);
/* 11223:      */         }
/* 11224:11402 */         if (!bool1)
/* 11225:      */         {
/* 11226:11404 */           localObject4 = local_Request.getOutputStream();
/* 11227:11405 */           local_Request.write_value(paramRecTransferInfo, RecTransferInfo.class);
/* 11228:      */         }
/* 11229:      */         else
/* 11230:      */         {
/* 11231:11409 */           localObject4 = local_Request.getOutputStream();
/* 11232:11410 */           localLocalFrame.add(paramRecTransferInfo);
/* 11233:      */         }
/* 11234:11412 */         local_Request.invoke();
/* 11235:      */         Object localObject1;
/* 11236:11413 */         if (bool1)
/* 11237:      */         {
/* 11238:11415 */           localObject4 = null;
/* 11239:11416 */           localObject5 = localLocalFrame.getResult();
/* 11240:11417 */           if (localObject5 != null) {
/* 11241:11419 */             localObject4 = (RecTransferInfo)ObjectVal.clone(localObject5);
/* 11242:      */           }
/* 11243:11421 */           return localObject4;
/* 11244:      */         }
/* 11245:11423 */         Object localObject4 = local_Request.getInputStream();
/* 11246:      */         
/* 11247:11425 */         Object localObject5 = (RecTransferInfo)local_Request.read_value(RecTransferInfo.class);
/* 11248:11426 */         return localObject5;
/* 11249:      */       }
/* 11250:      */       catch (TRANSIENT localTRANSIENT)
/* 11251:      */       {
/* 11252:11430 */         if (i == 10) {
/* 11253:11432 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11254:      */         }
/* 11255:      */       }
/* 11256:      */       catch (UserException localUserException)
/* 11257:      */       {
/* 11258:11437 */         local_Request.isRMI();
/* 11259:      */         
/* 11260:      */ 
/* 11261:11440 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11262:      */       }
/* 11263:      */       catch (SystemException localSystemException)
/* 11264:      */       {
/* 11265:11444 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11266:      */       }
/* 11267:      */       finally
/* 11268:      */       {
/* 11269:11448 */         if (local_Request != null) {
/* 11270:11450 */           local_Request.close();
/* 11271:      */         }
/* 11272:11452 */         if (bool1) {
/* 11273:11453 */           localLocalStack.pop(localLocalFrame);
/* 11274:      */         }
/* 11275:11454 */         localLocalStack.setArgsOnLocal(bool2);
/* 11276:      */       }
/* 11277:      */     }
/* 11278:      */   }
/* 11279:      */   
/* 11280:      */   public RecTransferInfo modRecTransfer(RecTransferInfo paramRecTransferInfo)
/* 11281:      */     throws java.rmi.RemoteException
/* 11282:      */   {
/* 11283:11463 */     for (int i = 1;; i++)
/* 11284:      */     {
/* 11285:11465 */       _Request local_Request = null;
/* 11286:11466 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11287:11467 */       boolean bool1 = false;
/* 11288:11468 */       LocalFrame localLocalFrame = null;
/* 11289:11469 */       boolean bool2 = false;
/* 11290:      */       try
/* 11291:      */       {
/* 11292:11472 */         local_Request = __request("modRecTransfer");
/* 11293:11473 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11294:11474 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11295:11475 */         localLocalStack.setArgsOnLocal(bool1);
/* 11296:11477 */         if (bool1)
/* 11297:      */         {
/* 11298:11479 */           localLocalFrame = new LocalFrame(1);
/* 11299:11480 */           localLocalStack.push(localLocalFrame);
/* 11300:      */         }
/* 11301:11482 */         if (!bool1)
/* 11302:      */         {
/* 11303:11484 */           localObject4 = local_Request.getOutputStream();
/* 11304:11485 */           local_Request.write_value(paramRecTransferInfo, RecTransferInfo.class);
/* 11305:      */         }
/* 11306:      */         else
/* 11307:      */         {
/* 11308:11489 */           localObject4 = local_Request.getOutputStream();
/* 11309:11490 */           localLocalFrame.add(paramRecTransferInfo);
/* 11310:      */         }
/* 11311:11492 */         local_Request.invoke();
/* 11312:      */         Object localObject1;
/* 11313:11493 */         if (bool1)
/* 11314:      */         {
/* 11315:11495 */           localObject4 = null;
/* 11316:11496 */           localObject5 = localLocalFrame.getResult();
/* 11317:11497 */           if (localObject5 != null) {
/* 11318:11499 */             localObject4 = (RecTransferInfo)ObjectVal.clone(localObject5);
/* 11319:      */           }
/* 11320:11501 */           return localObject4;
/* 11321:      */         }
/* 11322:11503 */         Object localObject4 = local_Request.getInputStream();
/* 11323:      */         
/* 11324:11505 */         Object localObject5 = (RecTransferInfo)local_Request.read_value(RecTransferInfo.class);
/* 11325:11506 */         return localObject5;
/* 11326:      */       }
/* 11327:      */       catch (TRANSIENT localTRANSIENT)
/* 11328:      */       {
/* 11329:11510 */         if (i == 10) {
/* 11330:11512 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11331:      */         }
/* 11332:      */       }
/* 11333:      */       catch (UserException localUserException)
/* 11334:      */       {
/* 11335:11517 */         local_Request.isRMI();
/* 11336:      */         
/* 11337:      */ 
/* 11338:11520 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11339:      */       }
/* 11340:      */       catch (SystemException localSystemException)
/* 11341:      */       {
/* 11342:11524 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11343:      */       }
/* 11344:      */       finally
/* 11345:      */       {
/* 11346:11528 */         if (local_Request != null) {
/* 11347:11530 */           local_Request.close();
/* 11348:      */         }
/* 11349:11532 */         if (bool1) {
/* 11350:11533 */           localLocalStack.pop(localLocalFrame);
/* 11351:      */         }
/* 11352:11534 */         localLocalStack.setArgsOnLocal(bool2);
/* 11353:      */       }
/* 11354:      */     }
/* 11355:      */   }
/* 11356:      */   
/* 11357:      */   public RecTransferInfo cancRecTransfer(RecTransferInfo paramRecTransferInfo)
/* 11358:      */     throws java.rmi.RemoteException
/* 11359:      */   {
/* 11360:11543 */     for (int i = 1;; i++)
/* 11361:      */     {
/* 11362:11545 */       _Request local_Request = null;
/* 11363:11546 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11364:11547 */       boolean bool1 = false;
/* 11365:11548 */       LocalFrame localLocalFrame = null;
/* 11366:11549 */       boolean bool2 = false;
/* 11367:      */       try
/* 11368:      */       {
/* 11369:11552 */         local_Request = __request("cancRecTransfer");
/* 11370:11553 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11371:11554 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11372:11555 */         localLocalStack.setArgsOnLocal(bool1);
/* 11373:11557 */         if (bool1)
/* 11374:      */         {
/* 11375:11559 */           localLocalFrame = new LocalFrame(1);
/* 11376:11560 */           localLocalStack.push(localLocalFrame);
/* 11377:      */         }
/* 11378:11562 */         if (!bool1)
/* 11379:      */         {
/* 11380:11564 */           localObject4 = local_Request.getOutputStream();
/* 11381:11565 */           local_Request.write_value(paramRecTransferInfo, RecTransferInfo.class);
/* 11382:      */         }
/* 11383:      */         else
/* 11384:      */         {
/* 11385:11569 */           localObject4 = local_Request.getOutputStream();
/* 11386:11570 */           localLocalFrame.add(paramRecTransferInfo);
/* 11387:      */         }
/* 11388:11572 */         local_Request.invoke();
/* 11389:      */         Object localObject1;
/* 11390:11573 */         if (bool1)
/* 11391:      */         {
/* 11392:11575 */           localObject4 = null;
/* 11393:11576 */           localObject5 = localLocalFrame.getResult();
/* 11394:11577 */           if (localObject5 != null) {
/* 11395:11579 */             localObject4 = (RecTransferInfo)ObjectVal.clone(localObject5);
/* 11396:      */           }
/* 11397:11581 */           return localObject4;
/* 11398:      */         }
/* 11399:11583 */         Object localObject4 = local_Request.getInputStream();
/* 11400:      */         
/* 11401:11585 */         Object localObject5 = (RecTransferInfo)local_Request.read_value(RecTransferInfo.class);
/* 11402:11586 */         return localObject5;
/* 11403:      */       }
/* 11404:      */       catch (TRANSIENT localTRANSIENT)
/* 11405:      */       {
/* 11406:11590 */         if (i == 10) {
/* 11407:11592 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11408:      */         }
/* 11409:      */       }
/* 11410:      */       catch (UserException localUserException)
/* 11411:      */       {
/* 11412:11597 */         local_Request.isRMI();
/* 11413:      */         
/* 11414:      */ 
/* 11415:11600 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11416:      */       }
/* 11417:      */       catch (SystemException localSystemException)
/* 11418:      */       {
/* 11419:11604 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11420:      */       }
/* 11421:      */       finally
/* 11422:      */       {
/* 11423:11608 */         if (local_Request != null) {
/* 11424:11610 */           local_Request.close();
/* 11425:      */         }
/* 11426:11612 */         if (bool1) {
/* 11427:11613 */           localLocalStack.pop(localLocalFrame);
/* 11428:      */         }
/* 11429:11614 */         localLocalStack.setArgsOnLocal(bool2);
/* 11430:      */       }
/* 11431:      */     }
/* 11432:      */   }
/* 11433:      */   
/* 11434:      */   public RecTransferInfo getRecTransferBySrvrTId(String paramString1, String paramString2)
/* 11435:      */     throws java.rmi.RemoteException
/* 11436:      */   {
/* 11437:11624 */     for (int i = 1;; i++)
/* 11438:      */     {
/* 11439:11626 */       _Request local_Request = null;
/* 11440:11627 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11441:11628 */       boolean bool1 = false;
/* 11442:11629 */       LocalFrame localLocalFrame = null;
/* 11443:11630 */       boolean bool2 = false;
/* 11444:      */       try
/* 11445:      */       {
/* 11446:11633 */         local_Request = __request("getRecTransferBySrvrTId__string__string", "getRecTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue");
/* 11447:11634 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11448:11635 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11449:11636 */         localLocalStack.setArgsOnLocal(bool1);
/* 11450:11638 */         if (bool1)
/* 11451:      */         {
/* 11452:11640 */           localLocalFrame = new LocalFrame(2);
/* 11453:11641 */           localLocalStack.push(localLocalFrame);
/* 11454:      */         }
/* 11455:11643 */         if (!bool1)
/* 11456:      */         {
/* 11457:11645 */           localObject4 = local_Request.getOutputStream();
/* 11458:11646 */           local_Request.write_string(paramString1);
/* 11459:11647 */           local_Request.write_string(paramString2);
/* 11460:      */         }
/* 11461:      */         else
/* 11462:      */         {
/* 11463:11651 */           localObject4 = local_Request.getOutputStream();
/* 11464:11652 */           localLocalFrame.add(paramString1);
/* 11465:11653 */           localLocalFrame.add(paramString2);
/* 11466:      */         }
/* 11467:11655 */         local_Request.invoke();
/* 11468:      */         Object localObject1;
/* 11469:11656 */         if (bool1)
/* 11470:      */         {
/* 11471:11658 */           localObject4 = null;
/* 11472:11659 */           localObject5 = localLocalFrame.getResult();
/* 11473:11660 */           if (localObject5 != null) {
/* 11474:11662 */             localObject4 = (RecTransferInfo)ObjectVal.clone(localObject5);
/* 11475:      */           }
/* 11476:11664 */           return localObject4;
/* 11477:      */         }
/* 11478:11666 */         Object localObject4 = local_Request.getInputStream();
/* 11479:      */         
/* 11480:11668 */         Object localObject5 = (RecTransferInfo)local_Request.read_value(RecTransferInfo.class);
/* 11481:11669 */         return localObject5;
/* 11482:      */       }
/* 11483:      */       catch (TRANSIENT localTRANSIENT)
/* 11484:      */       {
/* 11485:11673 */         if (i == 10) {
/* 11486:11675 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11487:      */         }
/* 11488:      */       }
/* 11489:      */       catch (UserException localUserException)
/* 11490:      */       {
/* 11491:11680 */         local_Request.isRMI();
/* 11492:      */         
/* 11493:      */ 
/* 11494:11683 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11495:      */       }
/* 11496:      */       catch (SystemException localSystemException)
/* 11497:      */       {
/* 11498:11687 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11499:      */       }
/* 11500:      */       finally
/* 11501:      */       {
/* 11502:11691 */         if (local_Request != null) {
/* 11503:11693 */           local_Request.close();
/* 11504:      */         }
/* 11505:11695 */         if (bool1) {
/* 11506:11696 */           localLocalStack.pop(localLocalFrame);
/* 11507:      */         }
/* 11508:11697 */         localLocalStack.setArgsOnLocal(bool2);
/* 11509:      */       }
/* 11510:      */     }
/* 11511:      */   }
/* 11512:      */   
/* 11513:      */   public RecTransferInfo getRecTransferBySrvrTId(String paramString)
/* 11514:      */     throws java.rmi.RemoteException
/* 11515:      */   {
/* 11516:11706 */     for (int i = 1;; i++)
/* 11517:      */     {
/* 11518:11708 */       _Request local_Request = null;
/* 11519:11709 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11520:11710 */       boolean bool1 = false;
/* 11521:11711 */       LocalFrame localLocalFrame = null;
/* 11522:11712 */       boolean bool2 = false;
/* 11523:      */       try
/* 11524:      */       {
/* 11525:11715 */         local_Request = __request("getRecTransferBySrvrTId__string", "getRecTransferBySrvrTId__CORBA_WStringValue");
/* 11526:11716 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11527:11717 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11528:11718 */         localLocalStack.setArgsOnLocal(bool1);
/* 11529:11720 */         if (bool1)
/* 11530:      */         {
/* 11531:11722 */           localLocalFrame = new LocalFrame(1);
/* 11532:11723 */           localLocalStack.push(localLocalFrame);
/* 11533:      */         }
/* 11534:11725 */         if (!bool1)
/* 11535:      */         {
/* 11536:11727 */           localObject4 = local_Request.getOutputStream();
/* 11537:11728 */           local_Request.write_string(paramString);
/* 11538:      */         }
/* 11539:      */         else
/* 11540:      */         {
/* 11541:11732 */           localObject4 = local_Request.getOutputStream();
/* 11542:11733 */           localLocalFrame.add(paramString);
/* 11543:      */         }
/* 11544:11735 */         local_Request.invoke();
/* 11545:      */         Object localObject1;
/* 11546:11736 */         if (bool1)
/* 11547:      */         {
/* 11548:11738 */           localObject4 = null;
/* 11549:11739 */           localObject5 = localLocalFrame.getResult();
/* 11550:11740 */           if (localObject5 != null) {
/* 11551:11742 */             localObject4 = (RecTransferInfo)ObjectVal.clone(localObject5);
/* 11552:      */           }
/* 11553:11744 */           return localObject4;
/* 11554:      */         }
/* 11555:11746 */         Object localObject4 = local_Request.getInputStream();
/* 11556:      */         
/* 11557:11748 */         Object localObject5 = (RecTransferInfo)local_Request.read_value(RecTransferInfo.class);
/* 11558:11749 */         return localObject5;
/* 11559:      */       }
/* 11560:      */       catch (TRANSIENT localTRANSIENT)
/* 11561:      */       {
/* 11562:11753 */         if (i == 10) {
/* 11563:11755 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11564:      */         }
/* 11565:      */       }
/* 11566:      */       catch (UserException localUserException)
/* 11567:      */       {
/* 11568:11760 */         local_Request.isRMI();
/* 11569:      */         
/* 11570:      */ 
/* 11571:11763 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11572:      */       }
/* 11573:      */       catch (SystemException localSystemException)
/* 11574:      */       {
/* 11575:11767 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11576:      */       }
/* 11577:      */       finally
/* 11578:      */       {
/* 11579:11771 */         if (local_Request != null) {
/* 11580:11773 */           local_Request.close();
/* 11581:      */         }
/* 11582:11775 */         if (bool1) {
/* 11583:11776 */           localLocalStack.pop(localLocalFrame);
/* 11584:      */         }
/* 11585:11777 */         localLocalStack.setArgsOnLocal(bool2);
/* 11586:      */       }
/* 11587:      */     }
/* 11588:      */   }
/* 11589:      */   
/* 11590:      */   public RecTransferInfo getRecTransferByTrackingId(String paramString)
/* 11591:      */     throws java.rmi.RemoteException
/* 11592:      */   {
/* 11593:11786 */     for (int i = 1;; i++)
/* 11594:      */     {
/* 11595:11788 */       _Request local_Request = null;
/* 11596:11789 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11597:11790 */       boolean bool1 = false;
/* 11598:11791 */       LocalFrame localLocalFrame = null;
/* 11599:11792 */       boolean bool2 = false;
/* 11600:      */       try
/* 11601:      */       {
/* 11602:11795 */         local_Request = __request("getRecTransferByTrackingId");
/* 11603:11796 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11604:11797 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11605:11798 */         localLocalStack.setArgsOnLocal(bool1);
/* 11606:11800 */         if (bool1)
/* 11607:      */         {
/* 11608:11802 */           localLocalFrame = new LocalFrame(1);
/* 11609:11803 */           localLocalStack.push(localLocalFrame);
/* 11610:      */         }
/* 11611:11805 */         if (!bool1)
/* 11612:      */         {
/* 11613:11807 */           localObject4 = local_Request.getOutputStream();
/* 11614:11808 */           local_Request.write_string(paramString);
/* 11615:      */         }
/* 11616:      */         else
/* 11617:      */         {
/* 11618:11812 */           localObject4 = local_Request.getOutputStream();
/* 11619:11813 */           localLocalFrame.add(paramString);
/* 11620:      */         }
/* 11621:11815 */         local_Request.invoke();
/* 11622:      */         Object localObject1;
/* 11623:11816 */         if (bool1)
/* 11624:      */         {
/* 11625:11818 */           localObject4 = null;
/* 11626:11819 */           localObject5 = localLocalFrame.getResult();
/* 11627:11820 */           if (localObject5 != null) {
/* 11628:11822 */             localObject4 = (RecTransferInfo)ObjectVal.clone(localObject5);
/* 11629:      */           }
/* 11630:11824 */           return localObject4;
/* 11631:      */         }
/* 11632:11826 */         Object localObject4 = local_Request.getInputStream();
/* 11633:      */         
/* 11634:11828 */         Object localObject5 = (RecTransferInfo)local_Request.read_value(RecTransferInfo.class);
/* 11635:11829 */         return localObject5;
/* 11636:      */       }
/* 11637:      */       catch (TRANSIENT localTRANSIENT)
/* 11638:      */       {
/* 11639:11833 */         if (i == 10) {
/* 11640:11835 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11641:      */         }
/* 11642:      */       }
/* 11643:      */       catch (UserException localUserException)
/* 11644:      */       {
/* 11645:11840 */         local_Request.isRMI();
/* 11646:      */         
/* 11647:      */ 
/* 11648:11843 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11649:      */       }
/* 11650:      */       catch (SystemException localSystemException)
/* 11651:      */       {
/* 11652:11847 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11653:      */       }
/* 11654:      */       finally
/* 11655:      */       {
/* 11656:11851 */         if (local_Request != null) {
/* 11657:11853 */           local_Request.close();
/* 11658:      */         }
/* 11659:11855 */         if (bool1) {
/* 11660:11856 */           localLocalStack.pop(localLocalFrame);
/* 11661:      */         }
/* 11662:11857 */         localLocalStack.setArgsOnLocal(bool2);
/* 11663:      */       }
/* 11664:      */     }
/* 11665:      */   }
/* 11666:      */   
/* 11667:      */   public RecTransferInfo[] getRecTransfersBySrvrTId(String[] paramArrayOfString)
/* 11668:      */     throws java.rmi.RemoteException
/* 11669:      */   {
/* 11670:11866 */     for (int i = 1;; i++)
/* 11671:      */     {
/* 11672:11868 */       _Request local_Request = null;
/* 11673:11869 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11674:11870 */       boolean bool1 = false;
/* 11675:11871 */       LocalFrame localLocalFrame = null;
/* 11676:11872 */       boolean bool2 = false;
/* 11677:      */       try
/* 11678:      */       {
/* 11679:11875 */         local_Request = __request("getRecTransfersBySrvrTId");
/* 11680:11876 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11681:11877 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11682:11878 */         localLocalStack.setArgsOnLocal(bool1);
/* 11683:11880 */         if (bool1)
/* 11684:      */         {
/* 11685:11882 */           localLocalFrame = new LocalFrame(1);
/* 11686:11883 */           localLocalStack.push(localLocalFrame);
/* 11687:      */         }
/* 11688:11885 */         if (!bool1)
/* 11689:      */         {
/* 11690:11887 */           localObject4 = local_Request.getOutputStream();
/* 11691:11888 */           if (local_Request.isRMI()) {
/* 11692:11888 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 11693:      */           } else {
/* 11694:11888 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 11695:      */           }
/* 11696:      */         }
/* 11697:      */         else
/* 11698:      */         {
/* 11699:11892 */           localObject4 = local_Request.getOutputStream();
/* 11700:11893 */           localLocalFrame.add(paramArrayOfString);
/* 11701:      */         }
/* 11702:11895 */         local_Request.invoke();
/* 11703:      */         Object localObject5;
/* 11704:      */         Object localObject1;
/* 11705:11896 */         if (bool1)
/* 11706:      */         {
/* 11707:11898 */           localObject4 = null;
/* 11708:11899 */           localObject5 = localLocalFrame.getResult();
/* 11709:11900 */           if (localObject5 != null) {
/* 11710:11902 */             localObject4 = (RecTransferInfo[])ObjectVal.clone(localObject5);
/* 11711:      */           }
/* 11712:11904 */           return localObject4;
/* 11713:      */         }
/* 11714:11906 */         Object localObject4 = local_Request.getInputStream();
/* 11715:11908 */         if (local_Request.isRMI()) {
/* 11716:11908 */           localObject5 = (RecTransferInfo[])local_Request.read_value(new RecTransferInfo[0].getClass());
/* 11717:      */         } else {
/* 11718:11908 */           localObject5 = RecTransferInfoSeqHelper.read((InputStream)localObject4);
/* 11719:      */         }
/* 11720:11909 */         return localObject5;
/* 11721:      */       }
/* 11722:      */       catch (TRANSIENT localTRANSIENT)
/* 11723:      */       {
/* 11724:11913 */         if (i == 10) {
/* 11725:11915 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11726:      */         }
/* 11727:      */       }
/* 11728:      */       catch (UserException localUserException)
/* 11729:      */       {
/* 11730:11920 */         local_Request.isRMI();
/* 11731:      */         
/* 11732:      */ 
/* 11733:11923 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11734:      */       }
/* 11735:      */       catch (SystemException localSystemException)
/* 11736:      */       {
/* 11737:11927 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11738:      */       }
/* 11739:      */       finally
/* 11740:      */       {
/* 11741:11931 */         if (local_Request != null) {
/* 11742:11933 */           local_Request.close();
/* 11743:      */         }
/* 11744:11935 */         if (bool1) {
/* 11745:11936 */           localLocalStack.pop(localLocalFrame);
/* 11746:      */         }
/* 11747:11937 */         localLocalStack.setArgsOnLocal(bool2);
/* 11748:      */       }
/* 11749:      */     }
/* 11750:      */   }
/* 11751:      */   
/* 11752:      */   public BPWHist getRecTransfers(BPWHist paramBPWHist)
/* 11753:      */     throws java.rmi.RemoteException
/* 11754:      */   {
/* 11755:11946 */     for (int i = 1;; i++)
/* 11756:      */     {
/* 11757:11948 */       _Request local_Request = null;
/* 11758:11949 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11759:11950 */       boolean bool1 = false;
/* 11760:11951 */       LocalFrame localLocalFrame = null;
/* 11761:11952 */       boolean bool2 = false;
/* 11762:      */       try
/* 11763:      */       {
/* 11764:11955 */         local_Request = __request("getRecTransfers");
/* 11765:11956 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11766:11957 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11767:11958 */         localLocalStack.setArgsOnLocal(bool1);
/* 11768:11960 */         if (bool1)
/* 11769:      */         {
/* 11770:11962 */           localLocalFrame = new LocalFrame(1);
/* 11771:11963 */           localLocalStack.push(localLocalFrame);
/* 11772:      */         }
/* 11773:11965 */         if (!bool1)
/* 11774:      */         {
/* 11775:11967 */           localObject4 = local_Request.getOutputStream();
/* 11776:11968 */           if (local_Request.isRMI()) {
/* 11777:11968 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/* 11778:      */           } else {
/* 11779:11968 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/* 11780:      */           }
/* 11781:      */         }
/* 11782:      */         else
/* 11783:      */         {
/* 11784:11972 */           localObject4 = local_Request.getOutputStream();
/* 11785:11973 */           localLocalFrame.add(paramBPWHist);
/* 11786:      */         }
/* 11787:11975 */         local_Request.invoke();
/* 11788:      */         Object localObject5;
/* 11789:      */         Object localObject1;
/* 11790:11976 */         if (bool1)
/* 11791:      */         {
/* 11792:11978 */           localObject4 = null;
/* 11793:11979 */           localObject5 = localLocalFrame.getResult();
/* 11794:11980 */           if (localObject5 != null) {
/* 11795:11982 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/* 11796:      */           }
/* 11797:11984 */           return localObject4;
/* 11798:      */         }
/* 11799:11986 */         Object localObject4 = local_Request.getInputStream();
/* 11800:11988 */         if (local_Request.isRMI()) {
/* 11801:11988 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/* 11802:      */         } else {
/* 11803:11988 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/* 11804:      */         }
/* 11805:11989 */         return localObject5;
/* 11806:      */       }
/* 11807:      */       catch (TRANSIENT localTRANSIENT)
/* 11808:      */       {
/* 11809:11993 */         if (i == 10) {
/* 11810:11995 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11811:      */         }
/* 11812:      */       }
/* 11813:      */       catch (UserException localUserException)
/* 11814:      */       {
/* 11815:12000 */         local_Request.isRMI();
/* 11816:      */         
/* 11817:      */ 
/* 11818:12003 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11819:      */       }
/* 11820:      */       catch (SystemException localSystemException)
/* 11821:      */       {
/* 11822:12007 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11823:      */       }
/* 11824:      */       finally
/* 11825:      */       {
/* 11826:12011 */         if (local_Request != null) {
/* 11827:12013 */           local_Request.close();
/* 11828:      */         }
/* 11829:12015 */         if (bool1) {
/* 11830:12016 */           localLocalStack.pop(localLocalFrame);
/* 11831:      */         }
/* 11832:12017 */         localLocalStack.setArgsOnLocal(bool2);
/* 11833:      */       }
/* 11834:      */     }
/* 11835:      */   }
/* 11836:      */   
/* 11837:      */   public RecTransferInfo[] getRecTransfersByTrackingId(String[] paramArrayOfString)
/* 11838:      */     throws java.rmi.RemoteException
/* 11839:      */   {
/* 11840:12026 */     for (int i = 1;; i++)
/* 11841:      */     {
/* 11842:12028 */       _Request local_Request = null;
/* 11843:12029 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11844:12030 */       boolean bool1 = false;
/* 11845:12031 */       LocalFrame localLocalFrame = null;
/* 11846:12032 */       boolean bool2 = false;
/* 11847:      */       try
/* 11848:      */       {
/* 11849:12035 */         local_Request = __request("getRecTransfersByTrackingId");
/* 11850:12036 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11851:12037 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11852:12038 */         localLocalStack.setArgsOnLocal(bool1);
/* 11853:12040 */         if (bool1)
/* 11854:      */         {
/* 11855:12042 */           localLocalFrame = new LocalFrame(1);
/* 11856:12043 */           localLocalStack.push(localLocalFrame);
/* 11857:      */         }
/* 11858:12045 */         if (!bool1)
/* 11859:      */         {
/* 11860:12047 */           localObject4 = local_Request.getOutputStream();
/* 11861:12048 */           if (local_Request.isRMI()) {
/* 11862:12048 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 11863:      */           } else {
/* 11864:12048 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 11865:      */           }
/* 11866:      */         }
/* 11867:      */         else
/* 11868:      */         {
/* 11869:12052 */           localObject4 = local_Request.getOutputStream();
/* 11870:12053 */           localLocalFrame.add(paramArrayOfString);
/* 11871:      */         }
/* 11872:12055 */         local_Request.invoke();
/* 11873:      */         Object localObject5;
/* 11874:      */         Object localObject1;
/* 11875:12056 */         if (bool1)
/* 11876:      */         {
/* 11877:12058 */           localObject4 = null;
/* 11878:12059 */           localObject5 = localLocalFrame.getResult();
/* 11879:12060 */           if (localObject5 != null) {
/* 11880:12062 */             localObject4 = (RecTransferInfo[])ObjectVal.clone(localObject5);
/* 11881:      */           }
/* 11882:12064 */           return localObject4;
/* 11883:      */         }
/* 11884:12066 */         Object localObject4 = local_Request.getInputStream();
/* 11885:12068 */         if (local_Request.isRMI()) {
/* 11886:12068 */           localObject5 = (RecTransferInfo[])local_Request.read_value(new RecTransferInfo[0].getClass());
/* 11887:      */         } else {
/* 11888:12068 */           localObject5 = RecTransferInfoSeqHelper.read((InputStream)localObject4);
/* 11889:      */         }
/* 11890:12069 */         return localObject5;
/* 11891:      */       }
/* 11892:      */       catch (TRANSIENT localTRANSIENT)
/* 11893:      */       {
/* 11894:12073 */         if (i == 10) {
/* 11895:12075 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11896:      */         }
/* 11897:      */       }
/* 11898:      */       catch (UserException localUserException)
/* 11899:      */       {
/* 11900:12080 */         local_Request.isRMI();
/* 11901:      */         
/* 11902:      */ 
/* 11903:12083 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11904:      */       }
/* 11905:      */       catch (SystemException localSystemException)
/* 11906:      */       {
/* 11907:12087 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11908:      */       }
/* 11909:      */       finally
/* 11910:      */       {
/* 11911:12091 */         if (local_Request != null) {
/* 11912:12093 */           local_Request.close();
/* 11913:      */         }
/* 11914:12095 */         if (bool1) {
/* 11915:12096 */           localLocalStack.pop(localLocalFrame);
/* 11916:      */         }
/* 11917:12097 */         localLocalStack.setArgsOnLocal(bool2);
/* 11918:      */       }
/* 11919:      */     }
/* 11920:      */   }
/* 11921:      */   
/* 11922:      */   public BPWHist getRecTransferHistory(BPWHist paramBPWHist)
/* 11923:      */     throws java.rmi.RemoteException
/* 11924:      */   {
/* 11925:12106 */     for (int i = 1;; i++)
/* 11926:      */     {
/* 11927:12108 */       _Request local_Request = null;
/* 11928:12109 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 11929:12110 */       boolean bool1 = false;
/* 11930:12111 */       LocalFrame localLocalFrame = null;
/* 11931:12112 */       boolean bool2 = false;
/* 11932:      */       try
/* 11933:      */       {
/* 11934:12115 */         local_Request = __request("getRecTransferHistory");
/* 11935:12116 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 11936:12117 */         bool2 = localLocalStack.isArgsOnLocal();
/* 11937:12118 */         localLocalStack.setArgsOnLocal(bool1);
/* 11938:12120 */         if (bool1)
/* 11939:      */         {
/* 11940:12122 */           localLocalFrame = new LocalFrame(1);
/* 11941:12123 */           localLocalStack.push(localLocalFrame);
/* 11942:      */         }
/* 11943:12125 */         if (!bool1)
/* 11944:      */         {
/* 11945:12127 */           localObject4 = local_Request.getOutputStream();
/* 11946:12128 */           if (local_Request.isRMI()) {
/* 11947:12128 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/* 11948:      */           } else {
/* 11949:12128 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/* 11950:      */           }
/* 11951:      */         }
/* 11952:      */         else
/* 11953:      */         {
/* 11954:12132 */           localObject4 = local_Request.getOutputStream();
/* 11955:12133 */           localLocalFrame.add(paramBPWHist);
/* 11956:      */         }
/* 11957:12135 */         local_Request.invoke();
/* 11958:      */         Object localObject5;
/* 11959:      */         Object localObject1;
/* 11960:12136 */         if (bool1)
/* 11961:      */         {
/* 11962:12138 */           localObject4 = null;
/* 11963:12139 */           localObject5 = localLocalFrame.getResult();
/* 11964:12140 */           if (localObject5 != null) {
/* 11965:12142 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/* 11966:      */           }
/* 11967:12144 */           return localObject4;
/* 11968:      */         }
/* 11969:12146 */         Object localObject4 = local_Request.getInputStream();
/* 11970:12148 */         if (local_Request.isRMI()) {
/* 11971:12148 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/* 11972:      */         } else {
/* 11973:12148 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/* 11974:      */         }
/* 11975:12149 */         return localObject5;
/* 11976:      */       }
/* 11977:      */       catch (TRANSIENT localTRANSIENT)
/* 11978:      */       {
/* 11979:12153 */         if (i == 10) {
/* 11980:12155 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 11981:      */         }
/* 11982:      */       }
/* 11983:      */       catch (UserException localUserException)
/* 11984:      */       {
/* 11985:12160 */         local_Request.isRMI();
/* 11986:      */         
/* 11987:      */ 
/* 11988:12163 */         throw new java.rmi.RemoteException(localUserException.type);
/* 11989:      */       }
/* 11990:      */       catch (SystemException localSystemException)
/* 11991:      */       {
/* 11992:12167 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 11993:      */       }
/* 11994:      */       finally
/* 11995:      */       {
/* 11996:12171 */         if (local_Request != null) {
/* 11997:12173 */           local_Request.close();
/* 11998:      */         }
/* 11999:12175 */         if (bool1) {
/* 12000:12176 */           localLocalStack.pop(localLocalFrame);
/* 12001:      */         }
/* 12002:12177 */         localLocalStack.setArgsOnLocal(bool2);
/* 12003:      */       }
/* 12004:      */     }
/* 12005:      */   }
/* 12006:      */   
/* 12007:      */   public ExtTransferCompanyInfo addExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
/* 12008:      */     throws java.rmi.RemoteException
/* 12009:      */   {
/* 12010:12186 */     for (int i = 1;; i++)
/* 12011:      */     {
/* 12012:12188 */       _Request local_Request = null;
/* 12013:12189 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12014:12190 */       boolean bool1 = false;
/* 12015:12191 */       LocalFrame localLocalFrame = null;
/* 12016:12192 */       boolean bool2 = false;
/* 12017:      */       try
/* 12018:      */       {
/* 12019:12195 */         local_Request = __request("addExtTransferCompany");
/* 12020:12196 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12021:12197 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12022:12198 */         localLocalStack.setArgsOnLocal(bool1);
/* 12023:12200 */         if (bool1)
/* 12024:      */         {
/* 12025:12202 */           localLocalFrame = new LocalFrame(1);
/* 12026:12203 */           localLocalStack.push(localLocalFrame);
/* 12027:      */         }
/* 12028:12205 */         if (!bool1)
/* 12029:      */         {
/* 12030:12207 */           localObject4 = local_Request.getOutputStream();
/* 12031:12208 */           local_Request.write_value(paramExtTransferCompanyInfo, ExtTransferCompanyInfo.class);
/* 12032:      */         }
/* 12033:      */         else
/* 12034:      */         {
/* 12035:12212 */           localObject4 = local_Request.getOutputStream();
/* 12036:12213 */           localLocalFrame.add(paramExtTransferCompanyInfo);
/* 12037:      */         }
/* 12038:12215 */         local_Request.invoke();
/* 12039:      */         Object localObject1;
/* 12040:12216 */         if (bool1)
/* 12041:      */         {
/* 12042:12218 */           localObject4 = null;
/* 12043:12219 */           localObject5 = localLocalFrame.getResult();
/* 12044:12220 */           if (localObject5 != null) {
/* 12045:12222 */             localObject4 = (ExtTransferCompanyInfo)ObjectVal.clone(localObject5);
/* 12046:      */           }
/* 12047:12224 */           return localObject4;
/* 12048:      */         }
/* 12049:12226 */         Object localObject4 = local_Request.getInputStream();
/* 12050:      */         
/* 12051:12228 */         Object localObject5 = (ExtTransferCompanyInfo)local_Request.read_value(ExtTransferCompanyInfo.class);
/* 12052:12229 */         return localObject5;
/* 12053:      */       }
/* 12054:      */       catch (TRANSIENT localTRANSIENT)
/* 12055:      */       {
/* 12056:12233 */         if (i == 10) {
/* 12057:12235 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12058:      */         }
/* 12059:      */       }
/* 12060:      */       catch (UserException localUserException)
/* 12061:      */       {
/* 12062:12240 */         local_Request.isRMI();
/* 12063:      */         
/* 12064:      */ 
/* 12065:12243 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12066:      */       }
/* 12067:      */       catch (SystemException localSystemException)
/* 12068:      */       {
/* 12069:12247 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12070:      */       }
/* 12071:      */       finally
/* 12072:      */       {
/* 12073:12251 */         if (local_Request != null) {
/* 12074:12253 */           local_Request.close();
/* 12075:      */         }
/* 12076:12255 */         if (bool1) {
/* 12077:12256 */           localLocalStack.pop(localLocalFrame);
/* 12078:      */         }
/* 12079:12257 */         localLocalStack.setArgsOnLocal(bool2);
/* 12080:      */       }
/* 12081:      */     }
/* 12082:      */   }
/* 12083:      */   
/* 12084:      */   public ExtTransferCompanyInfo canExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
/* 12085:      */     throws java.rmi.RemoteException
/* 12086:      */   {
/* 12087:12266 */     for (int i = 1;; i++)
/* 12088:      */     {
/* 12089:12268 */       _Request local_Request = null;
/* 12090:12269 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12091:12270 */       boolean bool1 = false;
/* 12092:12271 */       LocalFrame localLocalFrame = null;
/* 12093:12272 */       boolean bool2 = false;
/* 12094:      */       try
/* 12095:      */       {
/* 12096:12275 */         local_Request = __request("canExtTransferCompany");
/* 12097:12276 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12098:12277 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12099:12278 */         localLocalStack.setArgsOnLocal(bool1);
/* 12100:12280 */         if (bool1)
/* 12101:      */         {
/* 12102:12282 */           localLocalFrame = new LocalFrame(1);
/* 12103:12283 */           localLocalStack.push(localLocalFrame);
/* 12104:      */         }
/* 12105:12285 */         if (!bool1)
/* 12106:      */         {
/* 12107:12287 */           localObject4 = local_Request.getOutputStream();
/* 12108:12288 */           local_Request.write_value(paramExtTransferCompanyInfo, ExtTransferCompanyInfo.class);
/* 12109:      */         }
/* 12110:      */         else
/* 12111:      */         {
/* 12112:12292 */           localObject4 = local_Request.getOutputStream();
/* 12113:12293 */           localLocalFrame.add(paramExtTransferCompanyInfo);
/* 12114:      */         }
/* 12115:12295 */         local_Request.invoke();
/* 12116:      */         Object localObject1;
/* 12117:12296 */         if (bool1)
/* 12118:      */         {
/* 12119:12298 */           localObject4 = null;
/* 12120:12299 */           localObject5 = localLocalFrame.getResult();
/* 12121:12300 */           if (localObject5 != null) {
/* 12122:12302 */             localObject4 = (ExtTransferCompanyInfo)ObjectVal.clone(localObject5);
/* 12123:      */           }
/* 12124:12304 */           return localObject4;
/* 12125:      */         }
/* 12126:12306 */         Object localObject4 = local_Request.getInputStream();
/* 12127:      */         
/* 12128:12308 */         Object localObject5 = (ExtTransferCompanyInfo)local_Request.read_value(ExtTransferCompanyInfo.class);
/* 12129:12309 */         return localObject5;
/* 12130:      */       }
/* 12131:      */       catch (TRANSIENT localTRANSIENT)
/* 12132:      */       {
/* 12133:12313 */         if (i == 10) {
/* 12134:12315 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12135:      */         }
/* 12136:      */       }
/* 12137:      */       catch (UserException localUserException)
/* 12138:      */       {
/* 12139:12320 */         local_Request.isRMI();
/* 12140:      */         
/* 12141:      */ 
/* 12142:12323 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12143:      */       }
/* 12144:      */       catch (SystemException localSystemException)
/* 12145:      */       {
/* 12146:12327 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12147:      */       }
/* 12148:      */       finally
/* 12149:      */       {
/* 12150:12331 */         if (local_Request != null) {
/* 12151:12333 */           local_Request.close();
/* 12152:      */         }
/* 12153:12335 */         if (bool1) {
/* 12154:12336 */           localLocalStack.pop(localLocalFrame);
/* 12155:      */         }
/* 12156:12337 */         localLocalStack.setArgsOnLocal(bool2);
/* 12157:      */       }
/* 12158:      */     }
/* 12159:      */   }
/* 12160:      */   
/* 12161:      */   public ExtTransferCompanyInfo modExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
/* 12162:      */     throws java.rmi.RemoteException
/* 12163:      */   {
/* 12164:12346 */     for (int i = 1;; i++)
/* 12165:      */     {
/* 12166:12348 */       _Request local_Request = null;
/* 12167:12349 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12168:12350 */       boolean bool1 = false;
/* 12169:12351 */       LocalFrame localLocalFrame = null;
/* 12170:12352 */       boolean bool2 = false;
/* 12171:      */       try
/* 12172:      */       {
/* 12173:12355 */         local_Request = __request("modExtTransferCompany");
/* 12174:12356 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12175:12357 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12176:12358 */         localLocalStack.setArgsOnLocal(bool1);
/* 12177:12360 */         if (bool1)
/* 12178:      */         {
/* 12179:12362 */           localLocalFrame = new LocalFrame(1);
/* 12180:12363 */           localLocalStack.push(localLocalFrame);
/* 12181:      */         }
/* 12182:12365 */         if (!bool1)
/* 12183:      */         {
/* 12184:12367 */           localObject4 = local_Request.getOutputStream();
/* 12185:12368 */           local_Request.write_value(paramExtTransferCompanyInfo, ExtTransferCompanyInfo.class);
/* 12186:      */         }
/* 12187:      */         else
/* 12188:      */         {
/* 12189:12372 */           localObject4 = local_Request.getOutputStream();
/* 12190:12373 */           localLocalFrame.add(paramExtTransferCompanyInfo);
/* 12191:      */         }
/* 12192:12375 */         local_Request.invoke();
/* 12193:      */         Object localObject1;
/* 12194:12376 */         if (bool1)
/* 12195:      */         {
/* 12196:12378 */           localObject4 = null;
/* 12197:12379 */           localObject5 = localLocalFrame.getResult();
/* 12198:12380 */           if (localObject5 != null) {
/* 12199:12382 */             localObject4 = (ExtTransferCompanyInfo)ObjectVal.clone(localObject5);
/* 12200:      */           }
/* 12201:12384 */           return localObject4;
/* 12202:      */         }
/* 12203:12386 */         Object localObject4 = local_Request.getInputStream();
/* 12204:      */         
/* 12205:12388 */         Object localObject5 = (ExtTransferCompanyInfo)local_Request.read_value(ExtTransferCompanyInfo.class);
/* 12206:12389 */         return localObject5;
/* 12207:      */       }
/* 12208:      */       catch (TRANSIENT localTRANSIENT)
/* 12209:      */       {
/* 12210:12393 */         if (i == 10) {
/* 12211:12395 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12212:      */         }
/* 12213:      */       }
/* 12214:      */       catch (UserException localUserException)
/* 12215:      */       {
/* 12216:12400 */         local_Request.isRMI();
/* 12217:      */         
/* 12218:      */ 
/* 12219:12403 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12220:      */       }
/* 12221:      */       catch (SystemException localSystemException)
/* 12222:      */       {
/* 12223:12407 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12224:      */       }
/* 12225:      */       finally
/* 12226:      */       {
/* 12227:12411 */         if (local_Request != null) {
/* 12228:12413 */           local_Request.close();
/* 12229:      */         }
/* 12230:12415 */         if (bool1) {
/* 12231:12416 */           localLocalStack.pop(localLocalFrame);
/* 12232:      */         }
/* 12233:12417 */         localLocalStack.setArgsOnLocal(bool2);
/* 12234:      */       }
/* 12235:      */     }
/* 12236:      */   }
/* 12237:      */   
/* 12238:      */   public ExtTransferCompanyInfo getExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
/* 12239:      */     throws java.rmi.RemoteException
/* 12240:      */   {
/* 12241:12426 */     for (int i = 1;; i++)
/* 12242:      */     {
/* 12243:12428 */       _Request local_Request = null;
/* 12244:12429 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12245:12430 */       boolean bool1 = false;
/* 12246:12431 */       LocalFrame localLocalFrame = null;
/* 12247:12432 */       boolean bool2 = false;
/* 12248:      */       try
/* 12249:      */       {
/* 12250:12435 */         local_Request = __request("getExtTransferCompany");
/* 12251:12436 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12252:12437 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12253:12438 */         localLocalStack.setArgsOnLocal(bool1);
/* 12254:12440 */         if (bool1)
/* 12255:      */         {
/* 12256:12442 */           localLocalFrame = new LocalFrame(1);
/* 12257:12443 */           localLocalStack.push(localLocalFrame);
/* 12258:      */         }
/* 12259:12445 */         if (!bool1)
/* 12260:      */         {
/* 12261:12447 */           localObject4 = local_Request.getOutputStream();
/* 12262:12448 */           local_Request.write_value(paramExtTransferCompanyInfo, ExtTransferCompanyInfo.class);
/* 12263:      */         }
/* 12264:      */         else
/* 12265:      */         {
/* 12266:12452 */           localObject4 = local_Request.getOutputStream();
/* 12267:12453 */           localLocalFrame.add(paramExtTransferCompanyInfo);
/* 12268:      */         }
/* 12269:12455 */         local_Request.invoke();
/* 12270:      */         Object localObject1;
/* 12271:12456 */         if (bool1)
/* 12272:      */         {
/* 12273:12458 */           localObject4 = null;
/* 12274:12459 */           localObject5 = localLocalFrame.getResult();
/* 12275:12460 */           if (localObject5 != null) {
/* 12276:12462 */             localObject4 = (ExtTransferCompanyInfo)ObjectVal.clone(localObject5);
/* 12277:      */           }
/* 12278:12464 */           return localObject4;
/* 12279:      */         }
/* 12280:12466 */         Object localObject4 = local_Request.getInputStream();
/* 12281:      */         
/* 12282:12468 */         Object localObject5 = (ExtTransferCompanyInfo)local_Request.read_value(ExtTransferCompanyInfo.class);
/* 12283:12469 */         return localObject5;
/* 12284:      */       }
/* 12285:      */       catch (TRANSIENT localTRANSIENT)
/* 12286:      */       {
/* 12287:12473 */         if (i == 10) {
/* 12288:12475 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12289:      */         }
/* 12290:      */       }
/* 12291:      */       catch (UserException localUserException)
/* 12292:      */       {
/* 12293:12480 */         local_Request.isRMI();
/* 12294:      */         
/* 12295:      */ 
/* 12296:12483 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12297:      */       }
/* 12298:      */       catch (SystemException localSystemException)
/* 12299:      */       {
/* 12300:12487 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12301:      */       }
/* 12302:      */       finally
/* 12303:      */       {
/* 12304:12491 */         if (local_Request != null) {
/* 12305:12493 */           local_Request.close();
/* 12306:      */         }
/* 12307:12495 */         if (bool1) {
/* 12308:12496 */           localLocalStack.pop(localLocalFrame);
/* 12309:      */         }
/* 12310:12497 */         localLocalStack.setArgsOnLocal(bool2);
/* 12311:      */       }
/* 12312:      */     }
/* 12313:      */   }
/* 12314:      */   
/* 12315:      */   public ExtTransferCompanyList getExtTransferCompanyList(ExtTransferCompanyList paramExtTransferCompanyList)
/* 12316:      */     throws java.rmi.RemoteException
/* 12317:      */   {
/* 12318:12506 */     for (int i = 1;; i++)
/* 12319:      */     {
/* 12320:12508 */       _Request local_Request = null;
/* 12321:12509 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12322:12510 */       boolean bool1 = false;
/* 12323:12511 */       LocalFrame localLocalFrame = null;
/* 12324:12512 */       boolean bool2 = false;
/* 12325:      */       try
/* 12326:      */       {
/* 12327:12515 */         local_Request = __request("getExtTransferCompanyList");
/* 12328:12516 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12329:12517 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12330:12518 */         localLocalStack.setArgsOnLocal(bool1);
/* 12331:12520 */         if (bool1)
/* 12332:      */         {
/* 12333:12522 */           localLocalFrame = new LocalFrame(1);
/* 12334:12523 */           localLocalStack.push(localLocalFrame);
/* 12335:      */         }
/* 12336:12525 */         if (!bool1)
/* 12337:      */         {
/* 12338:12527 */           localObject4 = local_Request.getOutputStream();
/* 12339:12528 */           local_Request.write_value(paramExtTransferCompanyList, ExtTransferCompanyList.class);
/* 12340:      */         }
/* 12341:      */         else
/* 12342:      */         {
/* 12343:12532 */           localObject4 = local_Request.getOutputStream();
/* 12344:12533 */           localLocalFrame.add(paramExtTransferCompanyList);
/* 12345:      */         }
/* 12346:12535 */         local_Request.invoke();
/* 12347:      */         Object localObject1;
/* 12348:12536 */         if (bool1)
/* 12349:      */         {
/* 12350:12538 */           localObject4 = null;
/* 12351:12539 */           localObject5 = localLocalFrame.getResult();
/* 12352:12540 */           if (localObject5 != null) {
/* 12353:12542 */             localObject4 = (ExtTransferCompanyList)ObjectVal.clone(localObject5);
/* 12354:      */           }
/* 12355:12544 */           return localObject4;
/* 12356:      */         }
/* 12357:12546 */         Object localObject4 = local_Request.getInputStream();
/* 12358:      */         
/* 12359:12548 */         Object localObject5 = (ExtTransferCompanyList)local_Request.read_value(ExtTransferCompanyList.class);
/* 12360:12549 */         return localObject5;
/* 12361:      */       }
/* 12362:      */       catch (TRANSIENT localTRANSIENT)
/* 12363:      */       {
/* 12364:12553 */         if (i == 10) {
/* 12365:12555 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12366:      */         }
/* 12367:      */       }
/* 12368:      */       catch (UserException localUserException)
/* 12369:      */       {
/* 12370:12560 */         local_Request.isRMI();
/* 12371:      */         
/* 12372:      */ 
/* 12373:12563 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12374:      */       }
/* 12375:      */       catch (SystemException localSystemException)
/* 12376:      */       {
/* 12377:12567 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12378:      */       }
/* 12379:      */       finally
/* 12380:      */       {
/* 12381:12571 */         if (local_Request != null) {
/* 12382:12573 */           local_Request.close();
/* 12383:      */         }
/* 12384:12575 */         if (bool1) {
/* 12385:12576 */           localLocalStack.pop(localLocalFrame);
/* 12386:      */         }
/* 12387:12577 */         localLocalStack.setArgsOnLocal(bool2);
/* 12388:      */       }
/* 12389:      */     }
/* 12390:      */   }
/* 12391:      */   
/* 12392:      */   public ExtTransferAcctInfo addExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 12393:      */     throws java.rmi.RemoteException
/* 12394:      */   {
/* 12395:12586 */     for (int i = 1;; i++)
/* 12396:      */     {
/* 12397:12588 */       _Request local_Request = null;
/* 12398:12589 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12399:12590 */       boolean bool1 = false;
/* 12400:12591 */       LocalFrame localLocalFrame = null;
/* 12401:12592 */       boolean bool2 = false;
/* 12402:      */       try
/* 12403:      */       {
/* 12404:12595 */         local_Request = __request("addExtTransferAccount");
/* 12405:12596 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12406:12597 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12407:12598 */         localLocalStack.setArgsOnLocal(bool1);
/* 12408:12600 */         if (bool1)
/* 12409:      */         {
/* 12410:12602 */           localLocalFrame = new LocalFrame(1);
/* 12411:12603 */           localLocalStack.push(localLocalFrame);
/* 12412:      */         }
/* 12413:12605 */         if (!bool1)
/* 12414:      */         {
/* 12415:12607 */           localObject4 = local_Request.getOutputStream();
/* 12416:12608 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 12417:      */         }
/* 12418:      */         else
/* 12419:      */         {
/* 12420:12612 */           localObject4 = local_Request.getOutputStream();
/* 12421:12613 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 12422:      */         }
/* 12423:12615 */         local_Request.invoke();
/* 12424:      */         Object localObject1;
/* 12425:12616 */         if (bool1)
/* 12426:      */         {
/* 12427:12618 */           localObject4 = null;
/* 12428:12619 */           localObject5 = localLocalFrame.getResult();
/* 12429:12620 */           if (localObject5 != null) {
/* 12430:12622 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 12431:      */           }
/* 12432:12624 */           return localObject4;
/* 12433:      */         }
/* 12434:12626 */         Object localObject4 = local_Request.getInputStream();
/* 12435:      */         
/* 12436:12628 */         Object localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 12437:12629 */         return localObject5;
/* 12438:      */       }
/* 12439:      */       catch (TRANSIENT localTRANSIENT)
/* 12440:      */       {
/* 12441:12633 */         if (i == 10) {
/* 12442:12635 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12443:      */         }
/* 12444:      */       }
/* 12445:      */       catch (UserException localUserException)
/* 12446:      */       {
/* 12447:12640 */         local_Request.isRMI();
/* 12448:      */         
/* 12449:      */ 
/* 12450:12643 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12451:      */       }
/* 12452:      */       catch (SystemException localSystemException)
/* 12453:      */       {
/* 12454:12647 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12455:      */       }
/* 12456:      */       finally
/* 12457:      */       {
/* 12458:12651 */         if (local_Request != null) {
/* 12459:12653 */           local_Request.close();
/* 12460:      */         }
/* 12461:12655 */         if (bool1) {
/* 12462:12656 */           localLocalStack.pop(localLocalFrame);
/* 12463:      */         }
/* 12464:12657 */         localLocalStack.setArgsOnLocal(bool2);
/* 12465:      */       }
/* 12466:      */     }
/* 12467:      */   }
/* 12468:      */   
/* 12469:      */   public ExtTransferAcctInfo canExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 12470:      */     throws java.rmi.RemoteException
/* 12471:      */   {
/* 12472:12666 */     for (int i = 1;; i++)
/* 12473:      */     {
/* 12474:12668 */       _Request local_Request = null;
/* 12475:12669 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12476:12670 */       boolean bool1 = false;
/* 12477:12671 */       LocalFrame localLocalFrame = null;
/* 12478:12672 */       boolean bool2 = false;
/* 12479:      */       try
/* 12480:      */       {
/* 12481:12675 */         local_Request = __request("canExtTransferAccount");
/* 12482:12676 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12483:12677 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12484:12678 */         localLocalStack.setArgsOnLocal(bool1);
/* 12485:12680 */         if (bool1)
/* 12486:      */         {
/* 12487:12682 */           localLocalFrame = new LocalFrame(1);
/* 12488:12683 */           localLocalStack.push(localLocalFrame);
/* 12489:      */         }
/* 12490:12685 */         if (!bool1)
/* 12491:      */         {
/* 12492:12687 */           localObject4 = local_Request.getOutputStream();
/* 12493:12688 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 12494:      */         }
/* 12495:      */         else
/* 12496:      */         {
/* 12497:12692 */           localObject4 = local_Request.getOutputStream();
/* 12498:12693 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 12499:      */         }
/* 12500:12695 */         local_Request.invoke();
/* 12501:      */         Object localObject1;
/* 12502:12696 */         if (bool1)
/* 12503:      */         {
/* 12504:12698 */           localObject4 = null;
/* 12505:12699 */           localObject5 = localLocalFrame.getResult();
/* 12506:12700 */           if (localObject5 != null) {
/* 12507:12702 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 12508:      */           }
/* 12509:12704 */           return localObject4;
/* 12510:      */         }
/* 12511:12706 */         Object localObject4 = local_Request.getInputStream();
/* 12512:      */         
/* 12513:12708 */         Object localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 12514:12709 */         return localObject5;
/* 12515:      */       }
/* 12516:      */       catch (TRANSIENT localTRANSIENT)
/* 12517:      */       {
/* 12518:12713 */         if (i == 10) {
/* 12519:12715 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12520:      */         }
/* 12521:      */       }
/* 12522:      */       catch (UserException localUserException)
/* 12523:      */       {
/* 12524:12720 */         local_Request.isRMI();
/* 12525:      */         
/* 12526:      */ 
/* 12527:12723 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12528:      */       }
/* 12529:      */       catch (SystemException localSystemException)
/* 12530:      */       {
/* 12531:12727 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12532:      */       }
/* 12533:      */       finally
/* 12534:      */       {
/* 12535:12731 */         if (local_Request != null) {
/* 12536:12733 */           local_Request.close();
/* 12537:      */         }
/* 12538:12735 */         if (bool1) {
/* 12539:12736 */           localLocalStack.pop(localLocalFrame);
/* 12540:      */         }
/* 12541:12737 */         localLocalStack.setArgsOnLocal(bool2);
/* 12542:      */       }
/* 12543:      */     }
/* 12544:      */   }
/* 12545:      */   
/* 12546:      */   public ExtTransferAcctInfo modExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 12547:      */     throws java.rmi.RemoteException
/* 12548:      */   {
/* 12549:12746 */     for (int i = 1;; i++)
/* 12550:      */     {
/* 12551:12748 */       _Request local_Request = null;
/* 12552:12749 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12553:12750 */       boolean bool1 = false;
/* 12554:12751 */       LocalFrame localLocalFrame = null;
/* 12555:12752 */       boolean bool2 = false;
/* 12556:      */       try
/* 12557:      */       {
/* 12558:12755 */         local_Request = __request("modExtTransferAccount");
/* 12559:12756 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12560:12757 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12561:12758 */         localLocalStack.setArgsOnLocal(bool1);
/* 12562:12760 */         if (bool1)
/* 12563:      */         {
/* 12564:12762 */           localLocalFrame = new LocalFrame(1);
/* 12565:12763 */           localLocalStack.push(localLocalFrame);
/* 12566:      */         }
/* 12567:12765 */         if (!bool1)
/* 12568:      */         {
/* 12569:12767 */           localObject4 = local_Request.getOutputStream();
/* 12570:12768 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 12571:      */         }
/* 12572:      */         else
/* 12573:      */         {
/* 12574:12772 */           localObject4 = local_Request.getOutputStream();
/* 12575:12773 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 12576:      */         }
/* 12577:12775 */         local_Request.invoke();
/* 12578:      */         Object localObject1;
/* 12579:12776 */         if (bool1)
/* 12580:      */         {
/* 12581:12778 */           localObject4 = null;
/* 12582:12779 */           localObject5 = localLocalFrame.getResult();
/* 12583:12780 */           if (localObject5 != null) {
/* 12584:12782 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 12585:      */           }
/* 12586:12784 */           return localObject4;
/* 12587:      */         }
/* 12588:12786 */         Object localObject4 = local_Request.getInputStream();
/* 12589:      */         
/* 12590:12788 */         Object localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 12591:12789 */         return localObject5;
/* 12592:      */       }
/* 12593:      */       catch (TRANSIENT localTRANSIENT)
/* 12594:      */       {
/* 12595:12793 */         if (i == 10) {
/* 12596:12795 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12597:      */         }
/* 12598:      */       }
/* 12599:      */       catch (UserException localUserException)
/* 12600:      */       {
/* 12601:12800 */         local_Request.isRMI();
/* 12602:      */         
/* 12603:      */ 
/* 12604:12803 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12605:      */       }
/* 12606:      */       catch (SystemException localSystemException)
/* 12607:      */       {
/* 12608:12807 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12609:      */       }
/* 12610:      */       finally
/* 12611:      */       {
/* 12612:12811 */         if (local_Request != null) {
/* 12613:12813 */           local_Request.close();
/* 12614:      */         }
/* 12615:12815 */         if (bool1) {
/* 12616:12816 */           localLocalStack.pop(localLocalFrame);
/* 12617:      */         }
/* 12618:12817 */         localLocalStack.setArgsOnLocal(bool2);
/* 12619:      */       }
/* 12620:      */     }
/* 12621:      */   }
/* 12622:      */   
/* 12623:      */   public ExtTransferAcctInfo getExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 12624:      */     throws java.rmi.RemoteException
/* 12625:      */   {
/* 12626:12826 */     for (int i = 1;; i++)
/* 12627:      */     {
/* 12628:12828 */       _Request local_Request = null;
/* 12629:12829 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12630:12830 */       boolean bool1 = false;
/* 12631:12831 */       LocalFrame localLocalFrame = null;
/* 12632:12832 */       boolean bool2 = false;
/* 12633:      */       try
/* 12634:      */       {
/* 12635:12835 */         local_Request = __request("getExtTransferAccount");
/* 12636:12836 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12637:12837 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12638:12838 */         localLocalStack.setArgsOnLocal(bool1);
/* 12639:12840 */         if (bool1)
/* 12640:      */         {
/* 12641:12842 */           localLocalFrame = new LocalFrame(1);
/* 12642:12843 */           localLocalStack.push(localLocalFrame);
/* 12643:      */         }
/* 12644:12845 */         if (!bool1)
/* 12645:      */         {
/* 12646:12847 */           localObject4 = local_Request.getOutputStream();
/* 12647:12848 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 12648:      */         }
/* 12649:      */         else
/* 12650:      */         {
/* 12651:12852 */           localObject4 = local_Request.getOutputStream();
/* 12652:12853 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 12653:      */         }
/* 12654:12855 */         local_Request.invoke();
/* 12655:      */         Object localObject1;
/* 12656:12856 */         if (bool1)
/* 12657:      */         {
/* 12658:12858 */           localObject4 = null;
/* 12659:12859 */           localObject5 = localLocalFrame.getResult();
/* 12660:12860 */           if (localObject5 != null) {
/* 12661:12862 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 12662:      */           }
/* 12663:12864 */           return localObject4;
/* 12664:      */         }
/* 12665:12866 */         Object localObject4 = local_Request.getInputStream();
/* 12666:      */         
/* 12667:12868 */         Object localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 12668:12869 */         return localObject5;
/* 12669:      */       }
/* 12670:      */       catch (TRANSIENT localTRANSIENT)
/* 12671:      */       {
/* 12672:12873 */         if (i == 10) {
/* 12673:12875 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12674:      */         }
/* 12675:      */       }
/* 12676:      */       catch (UserException localUserException)
/* 12677:      */       {
/* 12678:12880 */         local_Request.isRMI();
/* 12679:      */         
/* 12680:      */ 
/* 12681:12883 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12682:      */       }
/* 12683:      */       catch (SystemException localSystemException)
/* 12684:      */       {
/* 12685:12887 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12686:      */       }
/* 12687:      */       finally
/* 12688:      */       {
/* 12689:12891 */         if (local_Request != null) {
/* 12690:12893 */           local_Request.close();
/* 12691:      */         }
/* 12692:12895 */         if (bool1) {
/* 12693:12896 */           localLocalStack.pop(localLocalFrame);
/* 12694:      */         }
/* 12695:12897 */         localLocalStack.setArgsOnLocal(bool2);
/* 12696:      */       }
/* 12697:      */     }
/* 12698:      */   }
/* 12699:      */   
/* 12700:      */   public ExtTransferAcctInfo verifyExtTransferAccount(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo, int[] paramArrayOfInt)
/* 12701:      */     throws java.rmi.RemoteException, FFSException
/* 12702:      */   {
/* 12703:12908 */     for (int i = 1;; i++)
/* 12704:      */     {
/* 12705:12910 */       _Request local_Request = null;
/* 12706:12911 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12707:12912 */       boolean bool1 = false;
/* 12708:12913 */       LocalFrame localLocalFrame = null;
/* 12709:12914 */       boolean bool2 = false;
/* 12710:      */       try
/* 12711:      */       {
/* 12712:12917 */         local_Request = __request("verifyExtTransferAccount");
/* 12713:12918 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12714:12919 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12715:12920 */         localLocalStack.setArgsOnLocal(bool1);
/* 12716:12922 */         if (bool1)
/* 12717:      */         {
/* 12718:12924 */           localLocalFrame = new LocalFrame(3);
/* 12719:12925 */           localLocalStack.push(localLocalFrame);
/* 12720:      */         }
/* 12721:12927 */         if (!bool1)
/* 12722:      */         {
/* 12723:12929 */           localObject4 = local_Request.getOutputStream();
/* 12724:12930 */           local_Request.write_string(paramString);
/* 12725:12931 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 12726:12932 */           if (local_Request.isRMI()) {
/* 12727:12932 */             local_Request.write_value(paramArrayOfInt, new int[0].getClass());
/* 12728:      */           } else {
/* 12729:12932 */             longSeqHelper.write((OutputStream)localObject4, paramArrayOfInt);
/* 12730:      */           }
/* 12731:      */         }
/* 12732:      */         else
/* 12733:      */         {
/* 12734:12936 */           localObject4 = local_Request.getOutputStream();
/* 12735:12937 */           localLocalFrame.add(paramString);
/* 12736:12938 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 12737:12939 */           localLocalFrame.add(paramArrayOfInt);
/* 12738:      */         }
/* 12739:12941 */         local_Request.invoke();
/* 12740:      */         Object localObject1;
/* 12741:12942 */         if (bool1)
/* 12742:      */         {
/* 12743:12944 */           localObject4 = null;
/* 12744:12945 */           localObject5 = localLocalFrame.getResult();
/* 12745:12946 */           if (localObject5 != null) {
/* 12746:12948 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 12747:      */           }
/* 12748:12950 */           return localObject4;
/* 12749:      */         }
/* 12750:12952 */         Object localObject4 = local_Request.getInputStream();
/* 12751:      */         
/* 12752:12954 */         localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 12753:12955 */         return localObject5;
/* 12754:      */       }
/* 12755:      */       catch (TRANSIENT localTRANSIENT)
/* 12756:      */       {
/* 12757:12959 */         if (i == 10) {
/* 12758:12961 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12759:      */         }
/* 12760:      */       }
/* 12761:      */       catch (UserException localUserException)
/* 12762:      */       {
/* 12763:      */         Object localObject5;
/* 12764:12966 */         if (local_Request.isRMI())
/* 12765:      */         {
/* 12766:12968 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 12767:12970 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 12768:      */           }
/* 12769:      */         }
/* 12770:      */         else
/* 12771:      */         {
/* 12772:12975 */           localObject5 = null;
/* 12773:12976 */           if (bool1)
/* 12774:      */           {
/* 12775:12978 */             localObject5 = localLocalFrame.getException();
/* 12776:12979 */             if (localObject5 != null) {
/* 12777:12981 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 12778:      */             }
/* 12779:      */           }
/* 12780:12984 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 12781:      */           {
/* 12782:12986 */             if (local_Request.isRMI()) {
/* 12783:12988 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 12784:      */             }
/* 12785:12992 */             if (bool1)
/* 12786:      */             {
/* 12787:12994 */               if (localObject5 != null) {
/* 12788:12994 */                 throw ((FFSException)localObject5);
/* 12789:      */               }
/* 12790:      */             }
/* 12791:      */             else {
/* 12792:12998 */               throw FFSExceptionHelper.read(localUserException.input);
/* 12793:      */             }
/* 12794:      */           }
/* 12795:      */         }
/* 12796:13003 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12797:      */       }
/* 12798:      */       catch (SystemException localSystemException)
/* 12799:      */       {
/* 12800:13007 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12801:      */       }
/* 12802:      */       finally
/* 12803:      */       {
/* 12804:13011 */         if (local_Request != null) {
/* 12805:13013 */           local_Request.close();
/* 12806:      */         }
/* 12807:13015 */         if (bool1) {
/* 12808:13016 */           localLocalStack.pop(localLocalFrame);
/* 12809:      */         }
/* 12810:13017 */         localLocalStack.setArgsOnLocal(bool2);
/* 12811:      */       }
/* 12812:      */     }
/* 12813:      */   }
/* 12814:      */   
/* 12815:      */   public ExtTransferAcctInfo depositAmountsForVerify(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 12816:      */     throws java.rmi.RemoteException, FFSException
/* 12817:      */   {
/* 12818:13027 */     for (int i = 1;; i++)
/* 12819:      */     {
/* 12820:13029 */       _Request local_Request = null;
/* 12821:13030 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12822:13031 */       boolean bool1 = false;
/* 12823:13032 */       LocalFrame localLocalFrame = null;
/* 12824:13033 */       boolean bool2 = false;
/* 12825:      */       try
/* 12826:      */       {
/* 12827:13036 */         local_Request = __request("depositAmountsForVerify");
/* 12828:13037 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12829:13038 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12830:13039 */         localLocalStack.setArgsOnLocal(bool1);
/* 12831:13041 */         if (bool1)
/* 12832:      */         {
/* 12833:13043 */           localLocalFrame = new LocalFrame(2);
/* 12834:13044 */           localLocalStack.push(localLocalFrame);
/* 12835:      */         }
/* 12836:13046 */         if (!bool1)
/* 12837:      */         {
/* 12838:13048 */           localObject4 = local_Request.getOutputStream();
/* 12839:13049 */           local_Request.write_string(paramString);
/* 12840:13050 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 12841:      */         }
/* 12842:      */         else
/* 12843:      */         {
/* 12844:13054 */           localObject4 = local_Request.getOutputStream();
/* 12845:13055 */           localLocalFrame.add(paramString);
/* 12846:13056 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 12847:      */         }
/* 12848:13058 */         local_Request.invoke();
/* 12849:      */         Object localObject1;
/* 12850:13059 */         if (bool1)
/* 12851:      */         {
/* 12852:13061 */           localObject4 = null;
/* 12853:13062 */           localObject5 = localLocalFrame.getResult();
/* 12854:13063 */           if (localObject5 != null) {
/* 12855:13065 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 12856:      */           }
/* 12857:13067 */           return localObject4;
/* 12858:      */         }
/* 12859:13069 */         Object localObject4 = local_Request.getInputStream();
/* 12860:      */         
/* 12861:13071 */         localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 12862:13072 */         return localObject5;
/* 12863:      */       }
/* 12864:      */       catch (TRANSIENT localTRANSIENT)
/* 12865:      */       {
/* 12866:13076 */         if (i == 10) {
/* 12867:13078 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12868:      */         }
/* 12869:      */       }
/* 12870:      */       catch (UserException localUserException)
/* 12871:      */       {
/* 12872:      */         Object localObject5;
/* 12873:13083 */         if (local_Request.isRMI())
/* 12874:      */         {
/* 12875:13085 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 12876:13087 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 12877:      */           }
/* 12878:      */         }
/* 12879:      */         else
/* 12880:      */         {
/* 12881:13092 */           localObject5 = null;
/* 12882:13093 */           if (bool1)
/* 12883:      */           {
/* 12884:13095 */             localObject5 = localLocalFrame.getException();
/* 12885:13096 */             if (localObject5 != null) {
/* 12886:13098 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 12887:      */             }
/* 12888:      */           }
/* 12889:13101 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 12890:      */           {
/* 12891:13103 */             if (local_Request.isRMI()) {
/* 12892:13105 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 12893:      */             }
/* 12894:13109 */             if (bool1)
/* 12895:      */             {
/* 12896:13111 */               if (localObject5 != null) {
/* 12897:13111 */                 throw ((FFSException)localObject5);
/* 12898:      */               }
/* 12899:      */             }
/* 12900:      */             else {
/* 12901:13115 */               throw FFSExceptionHelper.read(localUserException.input);
/* 12902:      */             }
/* 12903:      */           }
/* 12904:      */         }
/* 12905:13120 */         throw new java.rmi.RemoteException(localUserException.type);
/* 12906:      */       }
/* 12907:      */       catch (SystemException localSystemException)
/* 12908:      */       {
/* 12909:13124 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 12910:      */       }
/* 12911:      */       finally
/* 12912:      */       {
/* 12913:13128 */         if (local_Request != null) {
/* 12914:13130 */           local_Request.close();
/* 12915:      */         }
/* 12916:13132 */         if (bool1) {
/* 12917:13133 */           localLocalStack.pop(localLocalFrame);
/* 12918:      */         }
/* 12919:13134 */         localLocalStack.setArgsOnLocal(bool2);
/* 12920:      */       }
/* 12921:      */     }
/* 12922:      */   }
/* 12923:      */   
/* 12924:      */   public ExtTransferAcctInfo activateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 12925:      */     throws java.rmi.RemoteException, FFSException
/* 12926:      */   {
/* 12927:13143 */     for (int i = 1;; i++)
/* 12928:      */     {
/* 12929:13145 */       _Request local_Request = null;
/* 12930:13146 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 12931:13147 */       boolean bool1 = false;
/* 12932:13148 */       LocalFrame localLocalFrame = null;
/* 12933:13149 */       boolean bool2 = false;
/* 12934:      */       try
/* 12935:      */       {
/* 12936:13152 */         local_Request = __request("activateExtTransferAcct");
/* 12937:13153 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 12938:13154 */         bool2 = localLocalStack.isArgsOnLocal();
/* 12939:13155 */         localLocalStack.setArgsOnLocal(bool1);
/* 12940:13157 */         if (bool1)
/* 12941:      */         {
/* 12942:13159 */           localLocalFrame = new LocalFrame(1);
/* 12943:13160 */           localLocalStack.push(localLocalFrame);
/* 12944:      */         }
/* 12945:13162 */         if (!bool1)
/* 12946:      */         {
/* 12947:13164 */           localObject4 = local_Request.getOutputStream();
/* 12948:13165 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 12949:      */         }
/* 12950:      */         else
/* 12951:      */         {
/* 12952:13169 */           localObject4 = local_Request.getOutputStream();
/* 12953:13170 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 12954:      */         }
/* 12955:13172 */         local_Request.invoke();
/* 12956:      */         Object localObject1;
/* 12957:13173 */         if (bool1)
/* 12958:      */         {
/* 12959:13175 */           localObject4 = null;
/* 12960:13176 */           localObject5 = localLocalFrame.getResult();
/* 12961:13177 */           if (localObject5 != null) {
/* 12962:13179 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 12963:      */           }
/* 12964:13181 */           return localObject4;
/* 12965:      */         }
/* 12966:13183 */         Object localObject4 = local_Request.getInputStream();
/* 12967:      */         
/* 12968:13185 */         localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 12969:13186 */         return localObject5;
/* 12970:      */       }
/* 12971:      */       catch (TRANSIENT localTRANSIENT)
/* 12972:      */       {
/* 12973:13190 */         if (i == 10) {
/* 12974:13192 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 12975:      */         }
/* 12976:      */       }
/* 12977:      */       catch (UserException localUserException)
/* 12978:      */       {
/* 12979:      */         Object localObject5;
/* 12980:13197 */         if (local_Request.isRMI())
/* 12981:      */         {
/* 12982:13199 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 12983:13201 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 12984:      */           }
/* 12985:      */         }
/* 12986:      */         else
/* 12987:      */         {
/* 12988:13206 */           localObject5 = null;
/* 12989:13207 */           if (bool1)
/* 12990:      */           {
/* 12991:13209 */             localObject5 = localLocalFrame.getException();
/* 12992:13210 */             if (localObject5 != null) {
/* 12993:13212 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 12994:      */             }
/* 12995:      */           }
/* 12996:13215 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 12997:      */           {
/* 12998:13217 */             if (local_Request.isRMI()) {
/* 12999:13219 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13000:      */             }
/* 13001:13223 */             if (bool1)
/* 13002:      */             {
/* 13003:13225 */               if (localObject5 != null) {
/* 13004:13225 */                 throw ((FFSException)localObject5);
/* 13005:      */               }
/* 13006:      */             }
/* 13007:      */             else {
/* 13008:13229 */               throw FFSExceptionHelper.read(localUserException.input);
/* 13009:      */             }
/* 13010:      */           }
/* 13011:      */         }
/* 13012:13234 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13013:      */       }
/* 13014:      */       catch (SystemException localSystemException)
/* 13015:      */       {
/* 13016:13238 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13017:      */       }
/* 13018:      */       finally
/* 13019:      */       {
/* 13020:13242 */         if (local_Request != null) {
/* 13021:13244 */           local_Request.close();
/* 13022:      */         }
/* 13023:13246 */         if (bool1) {
/* 13024:13247 */           localLocalStack.pop(localLocalFrame);
/* 13025:      */         }
/* 13026:13248 */         localLocalStack.setArgsOnLocal(bool2);
/* 13027:      */       }
/* 13028:      */     }
/* 13029:      */   }
/* 13030:      */   
/* 13031:      */   public ExtTransferAcctInfo inactivateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 13032:      */     throws java.rmi.RemoteException, FFSException
/* 13033:      */   {
/* 13034:13257 */     for (int i = 1;; i++)
/* 13035:      */     {
/* 13036:13259 */       _Request local_Request = null;
/* 13037:13260 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13038:13261 */       boolean bool1 = false;
/* 13039:13262 */       LocalFrame localLocalFrame = null;
/* 13040:13263 */       boolean bool2 = false;
/* 13041:      */       try
/* 13042:      */       {
/* 13043:13266 */         local_Request = __request("inactivateExtTransferAcct");
/* 13044:13267 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13045:13268 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13046:13269 */         localLocalStack.setArgsOnLocal(bool1);
/* 13047:13271 */         if (bool1)
/* 13048:      */         {
/* 13049:13273 */           localLocalFrame = new LocalFrame(1);
/* 13050:13274 */           localLocalStack.push(localLocalFrame);
/* 13051:      */         }
/* 13052:13276 */         if (!bool1)
/* 13053:      */         {
/* 13054:13278 */           localObject4 = local_Request.getOutputStream();
/* 13055:13279 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 13056:      */         }
/* 13057:      */         else
/* 13058:      */         {
/* 13059:13283 */           localObject4 = local_Request.getOutputStream();
/* 13060:13284 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 13061:      */         }
/* 13062:13286 */         local_Request.invoke();
/* 13063:      */         Object localObject1;
/* 13064:13287 */         if (bool1)
/* 13065:      */         {
/* 13066:13289 */           localObject4 = null;
/* 13067:13290 */           localObject5 = localLocalFrame.getResult();
/* 13068:13291 */           if (localObject5 != null) {
/* 13069:13293 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 13070:      */           }
/* 13071:13295 */           return localObject4;
/* 13072:      */         }
/* 13073:13297 */         Object localObject4 = local_Request.getInputStream();
/* 13074:      */         
/* 13075:13299 */         localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 13076:13300 */         return localObject5;
/* 13077:      */       }
/* 13078:      */       catch (TRANSIENT localTRANSIENT)
/* 13079:      */       {
/* 13080:13304 */         if (i == 10) {
/* 13081:13306 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13082:      */         }
/* 13083:      */       }
/* 13084:      */       catch (UserException localUserException)
/* 13085:      */       {
/* 13086:      */         Object localObject5;
/* 13087:13311 */         if (local_Request.isRMI())
/* 13088:      */         {
/* 13089:13313 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 13090:13315 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13091:      */           }
/* 13092:      */         }
/* 13093:      */         else
/* 13094:      */         {
/* 13095:13320 */           localObject5 = null;
/* 13096:13321 */           if (bool1)
/* 13097:      */           {
/* 13098:13323 */             localObject5 = localLocalFrame.getException();
/* 13099:13324 */             if (localObject5 != null) {
/* 13100:13326 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 13101:      */             }
/* 13102:      */           }
/* 13103:13329 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 13104:      */           {
/* 13105:13331 */             if (local_Request.isRMI()) {
/* 13106:13333 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13107:      */             }
/* 13108:13337 */             if (bool1)
/* 13109:      */             {
/* 13110:13339 */               if (localObject5 != null) {
/* 13111:13339 */                 throw ((FFSException)localObject5);
/* 13112:      */               }
/* 13113:      */             }
/* 13114:      */             else {
/* 13115:13343 */               throw FFSExceptionHelper.read(localUserException.input);
/* 13116:      */             }
/* 13117:      */           }
/* 13118:      */         }
/* 13119:13348 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13120:      */       }
/* 13121:      */       catch (SystemException localSystemException)
/* 13122:      */       {
/* 13123:13352 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13124:      */       }
/* 13125:      */       finally
/* 13126:      */       {
/* 13127:13356 */         if (local_Request != null) {
/* 13128:13358 */           local_Request.close();
/* 13129:      */         }
/* 13130:13360 */         if (bool1) {
/* 13131:13361 */           localLocalStack.pop(localLocalFrame);
/* 13132:      */         }
/* 13133:13362 */         localLocalStack.setArgsOnLocal(bool2);
/* 13134:      */       }
/* 13135:      */     }
/* 13136:      */   }
/* 13137:      */   
/* 13138:      */   public ExtTransferAcctList getExtTransferAcctList(ExtTransferAcctList paramExtTransferAcctList)
/* 13139:      */     throws java.rmi.RemoteException
/* 13140:      */   {
/* 13141:13371 */     for (int i = 1;; i++)
/* 13142:      */     {
/* 13143:13373 */       _Request local_Request = null;
/* 13144:13374 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13145:13375 */       boolean bool1 = false;
/* 13146:13376 */       LocalFrame localLocalFrame = null;
/* 13147:13377 */       boolean bool2 = false;
/* 13148:      */       try
/* 13149:      */       {
/* 13150:13380 */         local_Request = __request("getExtTransferAcctList");
/* 13151:13381 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13152:13382 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13153:13383 */         localLocalStack.setArgsOnLocal(bool1);
/* 13154:13385 */         if (bool1)
/* 13155:      */         {
/* 13156:13387 */           localLocalFrame = new LocalFrame(1);
/* 13157:13388 */           localLocalStack.push(localLocalFrame);
/* 13158:      */         }
/* 13159:13390 */         if (!bool1)
/* 13160:      */         {
/* 13161:13392 */           localObject4 = local_Request.getOutputStream();
/* 13162:13393 */           local_Request.write_value(paramExtTransferAcctList, ExtTransferAcctList.class);
/* 13163:      */         }
/* 13164:      */         else
/* 13165:      */         {
/* 13166:13397 */           localObject4 = local_Request.getOutputStream();
/* 13167:13398 */           localLocalFrame.add(paramExtTransferAcctList);
/* 13168:      */         }
/* 13169:13400 */         local_Request.invoke();
/* 13170:      */         Object localObject1;
/* 13171:13401 */         if (bool1)
/* 13172:      */         {
/* 13173:13403 */           localObject4 = null;
/* 13174:13404 */           localObject5 = localLocalFrame.getResult();
/* 13175:13405 */           if (localObject5 != null) {
/* 13176:13407 */             localObject4 = (ExtTransferAcctList)ObjectVal.clone(localObject5);
/* 13177:      */           }
/* 13178:13409 */           return localObject4;
/* 13179:      */         }
/* 13180:13411 */         Object localObject4 = local_Request.getInputStream();
/* 13181:      */         
/* 13182:13413 */         Object localObject5 = (ExtTransferAcctList)local_Request.read_value(ExtTransferAcctList.class);
/* 13183:13414 */         return localObject5;
/* 13184:      */       }
/* 13185:      */       catch (TRANSIENT localTRANSIENT)
/* 13186:      */       {
/* 13187:13418 */         if (i == 10) {
/* 13188:13420 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13189:      */         }
/* 13190:      */       }
/* 13191:      */       catch (UserException localUserException)
/* 13192:      */       {
/* 13193:13425 */         local_Request.isRMI();
/* 13194:      */         
/* 13195:      */ 
/* 13196:13428 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13197:      */       }
/* 13198:      */       catch (SystemException localSystemException)
/* 13199:      */       {
/* 13200:13432 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13201:      */       }
/* 13202:      */       finally
/* 13203:      */       {
/* 13204:13436 */         if (local_Request != null) {
/* 13205:13438 */           local_Request.close();
/* 13206:      */         }
/* 13207:13440 */         if (bool1) {
/* 13208:13441 */           localLocalStack.pop(localLocalFrame);
/* 13209:      */         }
/* 13210:13442 */         localLocalStack.setArgsOnLocal(bool2);
/* 13211:      */       }
/* 13212:      */     }
/* 13213:      */   }
/* 13214:      */   
/* 13215:      */   public NonBusinessDay[] getNonBusinessDays(String paramString)
/* 13216:      */     throws java.rmi.RemoteException, FFSException
/* 13217:      */   {
/* 13218:13451 */     for (int i = 1;; i++)
/* 13219:      */     {
/* 13220:13453 */       _Request local_Request = null;
/* 13221:13454 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13222:13455 */       boolean bool1 = false;
/* 13223:13456 */       LocalFrame localLocalFrame = null;
/* 13224:13457 */       boolean bool2 = false;
/* 13225:      */       try
/* 13226:      */       {
/* 13227:13460 */         local_Request = __request("getNonBusinessDays");
/* 13228:13461 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13229:13462 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13230:13463 */         localLocalStack.setArgsOnLocal(bool1);
/* 13231:13465 */         if (bool1)
/* 13232:      */         {
/* 13233:13467 */           localLocalFrame = new LocalFrame(1);
/* 13234:13468 */           localLocalStack.push(localLocalFrame);
/* 13235:      */         }
/* 13236:13470 */         if (!bool1)
/* 13237:      */         {
/* 13238:13472 */           localObject4 = local_Request.getOutputStream();
/* 13239:13473 */           local_Request.write_string(paramString);
/* 13240:      */         }
/* 13241:      */         else
/* 13242:      */         {
/* 13243:13477 */           localObject4 = local_Request.getOutputStream();
/* 13244:13478 */           localLocalFrame.add(paramString);
/* 13245:      */         }
/* 13246:13480 */         local_Request.invoke();
/* 13247:      */         Object localObject1;
/* 13248:13481 */         if (bool1)
/* 13249:      */         {
/* 13250:13483 */           localObject4 = null;
/* 13251:13484 */           localObject5 = localLocalFrame.getResult();
/* 13252:13485 */           if (localObject5 != null) {
/* 13253:13487 */             localObject4 = (NonBusinessDay[])ObjectVal.clone(localObject5);
/* 13254:      */           }
/* 13255:13489 */           return localObject4;
/* 13256:      */         }
/* 13257:13491 */         Object localObject4 = local_Request.getInputStream();
/* 13258:13493 */         if (local_Request.isRMI()) {
/* 13259:13493 */           localObject5 = (NonBusinessDay[])local_Request.read_value(new NonBusinessDay[0].getClass());
/* 13260:      */         } else {
/* 13261:13493 */           localObject5 = NonBusinessDaySeqHelper.read((InputStream)localObject4);
/* 13262:      */         }
/* 13263:13494 */         return localObject5;
/* 13264:      */       }
/* 13265:      */       catch (TRANSIENT localTRANSIENT)
/* 13266:      */       {
/* 13267:13498 */         if (i == 10) {
/* 13268:13500 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13269:      */         }
/* 13270:      */       }
/* 13271:      */       catch (UserException localUserException)
/* 13272:      */       {
/* 13273:      */         Object localObject5;
/* 13274:13505 */         if (local_Request.isRMI())
/* 13275:      */         {
/* 13276:13507 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 13277:13509 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13278:      */           }
/* 13279:      */         }
/* 13280:      */         else
/* 13281:      */         {
/* 13282:13514 */           localObject5 = null;
/* 13283:13515 */           if (bool1)
/* 13284:      */           {
/* 13285:13517 */             localObject5 = localLocalFrame.getException();
/* 13286:13518 */             if (localObject5 != null) {
/* 13287:13520 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 13288:      */             }
/* 13289:      */           }
/* 13290:13523 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 13291:      */           {
/* 13292:13525 */             if (local_Request.isRMI()) {
/* 13293:13527 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13294:      */             }
/* 13295:13531 */             if (bool1)
/* 13296:      */             {
/* 13297:13533 */               if (localObject5 != null) {
/* 13298:13533 */                 throw ((FFSException)localObject5);
/* 13299:      */               }
/* 13300:      */             }
/* 13301:      */             else {
/* 13302:13537 */               throw FFSExceptionHelper.read(localUserException.input);
/* 13303:      */             }
/* 13304:      */           }
/* 13305:      */         }
/* 13306:13542 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13307:      */       }
/* 13308:      */       catch (SystemException localSystemException)
/* 13309:      */       {
/* 13310:13546 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13311:      */       }
/* 13312:      */       finally
/* 13313:      */       {
/* 13314:13550 */         if (local_Request != null) {
/* 13315:13552 */           local_Request.close();
/* 13316:      */         }
/* 13317:13554 */         if (bool1) {
/* 13318:13555 */           localLocalStack.pop(localLocalFrame);
/* 13319:      */         }
/* 13320:13556 */         localLocalStack.setArgsOnLocal(bool2);
/* 13321:      */       }
/* 13322:      */     }
/* 13323:      */   }
/* 13324:      */   
/* 13325:      */   public NonBusinessDay[] getNonBusinessDaysFromFile(String paramString)
/* 13326:      */     throws java.rmi.RemoteException, FFSException
/* 13327:      */   {
/* 13328:13565 */     for (int i = 1;; i++)
/* 13329:      */     {
/* 13330:13567 */       _Request local_Request = null;
/* 13331:13568 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13332:13569 */       boolean bool1 = false;
/* 13333:13570 */       LocalFrame localLocalFrame = null;
/* 13334:13571 */       boolean bool2 = false;
/* 13335:      */       try
/* 13336:      */       {
/* 13337:13574 */         local_Request = __request("getNonBusinessDaysFromFile");
/* 13338:13575 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13339:13576 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13340:13577 */         localLocalStack.setArgsOnLocal(bool1);
/* 13341:13579 */         if (bool1)
/* 13342:      */         {
/* 13343:13581 */           localLocalFrame = new LocalFrame(1);
/* 13344:13582 */           localLocalStack.push(localLocalFrame);
/* 13345:      */         }
/* 13346:13584 */         if (!bool1)
/* 13347:      */         {
/* 13348:13586 */           localObject4 = local_Request.getOutputStream();
/* 13349:13587 */           local_Request.write_string(paramString);
/* 13350:      */         }
/* 13351:      */         else
/* 13352:      */         {
/* 13353:13591 */           localObject4 = local_Request.getOutputStream();
/* 13354:13592 */           localLocalFrame.add(paramString);
/* 13355:      */         }
/* 13356:13594 */         local_Request.invoke();
/* 13357:      */         Object localObject1;
/* 13358:13595 */         if (bool1)
/* 13359:      */         {
/* 13360:13597 */           localObject4 = null;
/* 13361:13598 */           localObject5 = localLocalFrame.getResult();
/* 13362:13599 */           if (localObject5 != null) {
/* 13363:13601 */             localObject4 = (NonBusinessDay[])ObjectVal.clone(localObject5);
/* 13364:      */           }
/* 13365:13603 */           return localObject4;
/* 13366:      */         }
/* 13367:13605 */         Object localObject4 = local_Request.getInputStream();
/* 13368:13607 */         if (local_Request.isRMI()) {
/* 13369:13607 */           localObject5 = (NonBusinessDay[])local_Request.read_value(new NonBusinessDay[0].getClass());
/* 13370:      */         } else {
/* 13371:13607 */           localObject5 = NonBusinessDaySeqHelper.read((InputStream)localObject4);
/* 13372:      */         }
/* 13373:13608 */         return localObject5;
/* 13374:      */       }
/* 13375:      */       catch (TRANSIENT localTRANSIENT)
/* 13376:      */       {
/* 13377:13612 */         if (i == 10) {
/* 13378:13614 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13379:      */         }
/* 13380:      */       }
/* 13381:      */       catch (UserException localUserException)
/* 13382:      */       {
/* 13383:      */         Object localObject5;
/* 13384:13619 */         if (local_Request.isRMI())
/* 13385:      */         {
/* 13386:13621 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 13387:13623 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13388:      */           }
/* 13389:      */         }
/* 13390:      */         else
/* 13391:      */         {
/* 13392:13628 */           localObject5 = null;
/* 13393:13629 */           if (bool1)
/* 13394:      */           {
/* 13395:13631 */             localObject5 = localLocalFrame.getException();
/* 13396:13632 */             if (localObject5 != null) {
/* 13397:13634 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 13398:      */             }
/* 13399:      */           }
/* 13400:13637 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 13401:      */           {
/* 13402:13639 */             if (local_Request.isRMI()) {
/* 13403:13641 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13404:      */             }
/* 13405:13645 */             if (bool1)
/* 13406:      */             {
/* 13407:13647 */               if (localObject5 != null) {
/* 13408:13647 */                 throw ((FFSException)localObject5);
/* 13409:      */               }
/* 13410:      */             }
/* 13411:      */             else {
/* 13412:13651 */               throw FFSExceptionHelper.read(localUserException.input);
/* 13413:      */             }
/* 13414:      */           }
/* 13415:      */         }
/* 13416:13656 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13417:      */       }
/* 13418:      */       catch (SystemException localSystemException)
/* 13419:      */       {
/* 13420:13660 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13421:      */       }
/* 13422:      */       finally
/* 13423:      */       {
/* 13424:13664 */         if (local_Request != null) {
/* 13425:13666 */           local_Request.close();
/* 13426:      */         }
/* 13427:13668 */         if (bool1) {
/* 13428:13669 */           localLocalStack.pop(localLocalFrame);
/* 13429:      */         }
/* 13430:13670 */         localLocalStack.setArgsOnLocal(bool2);
/* 13431:      */       }
/* 13432:      */     }
/* 13433:      */   }
/* 13434:      */   
/* 13435:      */   public PagingInfo getPagedWire(PagingInfo paramPagingInfo)
/* 13436:      */     throws java.rmi.RemoteException, FFSException
/* 13437:      */   {
/* 13438:13679 */     for (int i = 1;; i++)
/* 13439:      */     {
/* 13440:13681 */       _Request local_Request = null;
/* 13441:13682 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13442:13683 */       boolean bool1 = false;
/* 13443:13684 */       LocalFrame localLocalFrame = null;
/* 13444:13685 */       boolean bool2 = false;
/* 13445:      */       try
/* 13446:      */       {
/* 13447:13688 */         local_Request = __request("getPagedWire");
/* 13448:13689 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13449:13690 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13450:13691 */         localLocalStack.setArgsOnLocal(bool1);
/* 13451:13693 */         if (bool1)
/* 13452:      */         {
/* 13453:13695 */           localLocalFrame = new LocalFrame(1);
/* 13454:13696 */           localLocalStack.push(localLocalFrame);
/* 13455:      */         }
/* 13456:13698 */         if (!bool1)
/* 13457:      */         {
/* 13458:13700 */           localObject4 = local_Request.getOutputStream();
/* 13459:13701 */           local_Request.write_value(paramPagingInfo, PagingInfo.class);
/* 13460:      */         }
/* 13461:      */         else
/* 13462:      */         {
/* 13463:13705 */           localObject4 = local_Request.getOutputStream();
/* 13464:13706 */           localLocalFrame.add(paramPagingInfo);
/* 13465:      */         }
/* 13466:13708 */         local_Request.invoke();
/* 13467:      */         Object localObject1;
/* 13468:13709 */         if (bool1)
/* 13469:      */         {
/* 13470:13711 */           localObject4 = null;
/* 13471:13712 */           localObject5 = localLocalFrame.getResult();
/* 13472:13713 */           if (localObject5 != null) {
/* 13473:13715 */             localObject4 = (PagingInfo)ObjectVal.clone(localObject5);
/* 13474:      */           }
/* 13475:13717 */           return localObject4;
/* 13476:      */         }
/* 13477:13719 */         Object localObject4 = local_Request.getInputStream();
/* 13478:      */         
/* 13479:13721 */         localObject5 = (PagingInfo)local_Request.read_value(PagingInfo.class);
/* 13480:13722 */         return localObject5;
/* 13481:      */       }
/* 13482:      */       catch (TRANSIENT localTRANSIENT)
/* 13483:      */       {
/* 13484:13726 */         if (i == 10) {
/* 13485:13728 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13486:      */         }
/* 13487:      */       }
/* 13488:      */       catch (UserException localUserException)
/* 13489:      */       {
/* 13490:      */         Object localObject5;
/* 13491:13733 */         if (local_Request.isRMI())
/* 13492:      */         {
/* 13493:13735 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 13494:13737 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13495:      */           }
/* 13496:      */         }
/* 13497:      */         else
/* 13498:      */         {
/* 13499:13742 */           localObject5 = null;
/* 13500:13743 */           if (bool1)
/* 13501:      */           {
/* 13502:13745 */             localObject5 = localLocalFrame.getException();
/* 13503:13746 */             if (localObject5 != null) {
/* 13504:13748 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 13505:      */             }
/* 13506:      */           }
/* 13507:13751 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 13508:      */           {
/* 13509:13753 */             if (local_Request.isRMI()) {
/* 13510:13755 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13511:      */             }
/* 13512:13759 */             if (bool1)
/* 13513:      */             {
/* 13514:13761 */               if (localObject5 != null) {
/* 13515:13761 */                 throw ((FFSException)localObject5);
/* 13516:      */               }
/* 13517:      */             }
/* 13518:      */             else {
/* 13519:13765 */               throw FFSExceptionHelper.read(localUserException.input);
/* 13520:      */             }
/* 13521:      */           }
/* 13522:      */         }
/* 13523:13770 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13524:      */       }
/* 13525:      */       catch (SystemException localSystemException)
/* 13526:      */       {
/* 13527:13774 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13528:      */       }
/* 13529:      */       finally
/* 13530:      */       {
/* 13531:13778 */         if (local_Request != null) {
/* 13532:13780 */           local_Request.close();
/* 13533:      */         }
/* 13534:13782 */         if (bool1) {
/* 13535:13783 */           localLocalStack.pop(localLocalFrame);
/* 13536:      */         }
/* 13537:13784 */         localLocalStack.setArgsOnLocal(bool2);
/* 13538:      */       }
/* 13539:      */     }
/* 13540:      */   }
/* 13541:      */   
/* 13542:      */   public PagingInfo getPagedTransfer(PagingInfo paramPagingInfo)
/* 13543:      */     throws java.rmi.RemoteException, FFSException
/* 13544:      */   {
/* 13545:13793 */     for (int i = 1;; i++)
/* 13546:      */     {
/* 13547:13795 */       _Request local_Request = null;
/* 13548:13796 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13549:13797 */       boolean bool1 = false;
/* 13550:13798 */       LocalFrame localLocalFrame = null;
/* 13551:13799 */       boolean bool2 = false;
/* 13552:      */       try
/* 13553:      */       {
/* 13554:13802 */         local_Request = __request("getPagedTransfer");
/* 13555:13803 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13556:13804 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13557:13805 */         localLocalStack.setArgsOnLocal(bool1);
/* 13558:13807 */         if (bool1)
/* 13559:      */         {
/* 13560:13809 */           localLocalFrame = new LocalFrame(1);
/* 13561:13810 */           localLocalStack.push(localLocalFrame);
/* 13562:      */         }
/* 13563:13812 */         if (!bool1)
/* 13564:      */         {
/* 13565:13814 */           localObject4 = local_Request.getOutputStream();
/* 13566:13815 */           local_Request.write_value(paramPagingInfo, PagingInfo.class);
/* 13567:      */         }
/* 13568:      */         else
/* 13569:      */         {
/* 13570:13819 */           localObject4 = local_Request.getOutputStream();
/* 13571:13820 */           localLocalFrame.add(paramPagingInfo);
/* 13572:      */         }
/* 13573:13822 */         local_Request.invoke();
/* 13574:      */         Object localObject1;
/* 13575:13823 */         if (bool1)
/* 13576:      */         {
/* 13577:13825 */           localObject4 = null;
/* 13578:13826 */           localObject5 = localLocalFrame.getResult();
/* 13579:13827 */           if (localObject5 != null) {
/* 13580:13829 */             localObject4 = (PagingInfo)ObjectVal.clone(localObject5);
/* 13581:      */           }
/* 13582:13831 */           return localObject4;
/* 13583:      */         }
/* 13584:13833 */         Object localObject4 = local_Request.getInputStream();
/* 13585:      */         
/* 13586:13835 */         localObject5 = (PagingInfo)local_Request.read_value(PagingInfo.class);
/* 13587:13836 */         return localObject5;
/* 13588:      */       }
/* 13589:      */       catch (TRANSIENT localTRANSIENT)
/* 13590:      */       {
/* 13591:13840 */         if (i == 10) {
/* 13592:13842 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13593:      */         }
/* 13594:      */       }
/* 13595:      */       catch (UserException localUserException)
/* 13596:      */       {
/* 13597:      */         Object localObject5;
/* 13598:13847 */         if (local_Request.isRMI())
/* 13599:      */         {
/* 13600:13849 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 13601:13851 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13602:      */           }
/* 13603:      */         }
/* 13604:      */         else
/* 13605:      */         {
/* 13606:13856 */           localObject5 = null;
/* 13607:13857 */           if (bool1)
/* 13608:      */           {
/* 13609:13859 */             localObject5 = localLocalFrame.getException();
/* 13610:13860 */             if (localObject5 != null) {
/* 13611:13862 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 13612:      */             }
/* 13613:      */           }
/* 13614:13865 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 13615:      */           {
/* 13616:13867 */             if (local_Request.isRMI()) {
/* 13617:13869 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13618:      */             }
/* 13619:13873 */             if (bool1)
/* 13620:      */             {
/* 13621:13875 */               if (localObject5 != null) {
/* 13622:13875 */                 throw ((FFSException)localObject5);
/* 13623:      */               }
/* 13624:      */             }
/* 13625:      */             else {
/* 13626:13879 */               throw FFSExceptionHelper.read(localUserException.input);
/* 13627:      */             }
/* 13628:      */           }
/* 13629:      */         }
/* 13630:13884 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13631:      */       }
/* 13632:      */       catch (SystemException localSystemException)
/* 13633:      */       {
/* 13634:13888 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13635:      */       }
/* 13636:      */       finally
/* 13637:      */       {
/* 13638:13892 */         if (local_Request != null) {
/* 13639:13894 */           local_Request.close();
/* 13640:      */         }
/* 13641:13896 */         if (bool1) {
/* 13642:13897 */           localLocalStack.pop(localLocalFrame);
/* 13643:      */         }
/* 13644:13898 */         localLocalStack.setArgsOnLocal(bool2);
/* 13645:      */       }
/* 13646:      */     }
/* 13647:      */   }
/* 13648:      */   
/* 13649:      */   public PagingInfo getPagedBillPayments(PagingInfo paramPagingInfo)
/* 13650:      */     throws java.rmi.RemoteException, FFSException
/* 13651:      */   {
/* 13652:13907 */     for (int i = 1;; i++)
/* 13653:      */     {
/* 13654:13909 */       _Request local_Request = null;
/* 13655:13910 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13656:13911 */       boolean bool1 = false;
/* 13657:13912 */       LocalFrame localLocalFrame = null;
/* 13658:13913 */       boolean bool2 = false;
/* 13659:      */       try
/* 13660:      */       {
/* 13661:13916 */         local_Request = __request("getPagedBillPayments");
/* 13662:13917 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13663:13918 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13664:13919 */         localLocalStack.setArgsOnLocal(bool1);
/* 13665:13921 */         if (bool1)
/* 13666:      */         {
/* 13667:13923 */           localLocalFrame = new LocalFrame(1);
/* 13668:13924 */           localLocalStack.push(localLocalFrame);
/* 13669:      */         }
/* 13670:13926 */         if (!bool1)
/* 13671:      */         {
/* 13672:13928 */           localObject4 = local_Request.getOutputStream();
/* 13673:13929 */           local_Request.write_value(paramPagingInfo, PagingInfo.class);
/* 13674:      */         }
/* 13675:      */         else
/* 13676:      */         {
/* 13677:13933 */           localObject4 = local_Request.getOutputStream();
/* 13678:13934 */           localLocalFrame.add(paramPagingInfo);
/* 13679:      */         }
/* 13680:13936 */         local_Request.invoke();
/* 13681:      */         Object localObject1;
/* 13682:13937 */         if (bool1)
/* 13683:      */         {
/* 13684:13939 */           localObject4 = null;
/* 13685:13940 */           localObject5 = localLocalFrame.getResult();
/* 13686:13941 */           if (localObject5 != null) {
/* 13687:13943 */             localObject4 = (PagingInfo)ObjectVal.clone(localObject5);
/* 13688:      */           }
/* 13689:13945 */           return localObject4;
/* 13690:      */         }
/* 13691:13947 */         Object localObject4 = local_Request.getInputStream();
/* 13692:      */         
/* 13693:13949 */         localObject5 = (PagingInfo)local_Request.read_value(PagingInfo.class);
/* 13694:13950 */         return localObject5;
/* 13695:      */       }
/* 13696:      */       catch (TRANSIENT localTRANSIENT)
/* 13697:      */       {
/* 13698:13954 */         if (i == 10) {
/* 13699:13956 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13700:      */         }
/* 13701:      */       }
/* 13702:      */       catch (UserException localUserException)
/* 13703:      */       {
/* 13704:      */         Object localObject5;
/* 13705:13961 */         if (local_Request.isRMI())
/* 13706:      */         {
/* 13707:13963 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 13708:13965 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13709:      */           }
/* 13710:      */         }
/* 13711:      */         else
/* 13712:      */         {
/* 13713:13970 */           localObject5 = null;
/* 13714:13971 */           if (bool1)
/* 13715:      */           {
/* 13716:13973 */             localObject5 = localLocalFrame.getException();
/* 13717:13974 */             if (localObject5 != null) {
/* 13718:13976 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 13719:      */             }
/* 13720:      */           }
/* 13721:13979 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 13722:      */           {
/* 13723:13981 */             if (local_Request.isRMI()) {
/* 13724:13983 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13725:      */             }
/* 13726:13987 */             if (bool1)
/* 13727:      */             {
/* 13728:13989 */               if (localObject5 != null) {
/* 13729:13989 */                 throw ((FFSException)localObject5);
/* 13730:      */               }
/* 13731:      */             }
/* 13732:      */             else {
/* 13733:13993 */               throw FFSExceptionHelper.read(localUserException.input);
/* 13734:      */             }
/* 13735:      */           }
/* 13736:      */         }
/* 13737:13998 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13738:      */       }
/* 13739:      */       catch (SystemException localSystemException)
/* 13740:      */       {
/* 13741:14002 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13742:      */       }
/* 13743:      */       finally
/* 13744:      */       {
/* 13745:14006 */         if (local_Request != null) {
/* 13746:14008 */           local_Request.close();
/* 13747:      */         }
/* 13748:14010 */         if (bool1) {
/* 13749:14011 */           localLocalStack.pop(localLocalFrame);
/* 13750:      */         }
/* 13751:14012 */         localLocalStack.setArgsOnLocal(bool2);
/* 13752:      */       }
/* 13753:      */     }
/* 13754:      */   }
/* 13755:      */   
/* 13756:      */   public int getValidTransferDateDue(TransferInfo paramTransferInfo)
/* 13757:      */     throws java.rmi.RemoteException
/* 13758:      */   {
/* 13759:14021 */     for (int i = 1;; i++)
/* 13760:      */     {
/* 13761:14023 */       _Request local_Request = null;
/* 13762:14024 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13763:14025 */       boolean bool1 = false;
/* 13764:14026 */       LocalFrame localLocalFrame = null;
/* 13765:14027 */       boolean bool2 = false;
/* 13766:      */       try
/* 13767:      */       {
/* 13768:14030 */         local_Request = __request("getValidTransferDateDue");
/* 13769:14031 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13770:14032 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13771:14033 */         localLocalStack.setArgsOnLocal(bool1);
/* 13772:14035 */         if (bool1)
/* 13773:      */         {
/* 13774:14037 */           localLocalFrame = new LocalFrame(1);
/* 13775:14038 */           localLocalStack.push(localLocalFrame);
/* 13776:      */         }
/* 13777:      */         OutputStream localOutputStream;
/* 13778:14040 */         if (!bool1)
/* 13779:      */         {
/* 13780:14042 */           localOutputStream = local_Request.getOutputStream();
/* 13781:14043 */           local_Request.write_value(paramTransferInfo, TransferInfo.class);
/* 13782:      */         }
/* 13783:      */         else
/* 13784:      */         {
/* 13785:14047 */           localOutputStream = local_Request.getOutputStream();
/* 13786:14048 */           localLocalFrame.add(paramTransferInfo);
/* 13787:      */         }
/* 13788:14050 */         local_Request.invoke();
/* 13789:      */         int j;
/* 13790:14051 */         if (bool1)
/* 13791:      */         {
/* 13792:14054 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 13793:14055 */           return k;
/* 13794:      */         }
/* 13795:14057 */         InputStream localInputStream = local_Request.getInputStream();
/* 13796:      */         
/* 13797:14059 */         int m = localInputStream.read_long();
/* 13798:14060 */         return m;
/* 13799:      */       }
/* 13800:      */       catch (TRANSIENT localTRANSIENT)
/* 13801:      */       {
/* 13802:14064 */         if (i == 10) {
/* 13803:14066 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13804:      */         }
/* 13805:      */       }
/* 13806:      */       catch (UserException localUserException)
/* 13807:      */       {
/* 13808:14071 */         local_Request.isRMI();
/* 13809:      */         
/* 13810:      */ 
/* 13811:14074 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13812:      */       }
/* 13813:      */       catch (SystemException localSystemException)
/* 13814:      */       {
/* 13815:14078 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13816:      */       }
/* 13817:      */       finally
/* 13818:      */       {
/* 13819:14082 */         if (local_Request != null) {
/* 13820:14084 */           local_Request.close();
/* 13821:      */         }
/* 13822:14086 */         if (bool1) {
/* 13823:14087 */           localLocalStack.pop(localLocalFrame);
/* 13824:      */         }
/* 13825:14088 */         localLocalStack.setArgsOnLocal(bool2);
/* 13826:      */       }
/* 13827:      */     }
/* 13828:      */   }
/* 13829:      */   
/* 13830:      */   public PagingInfo getPagedCashCon(PagingInfo paramPagingInfo)
/* 13831:      */     throws java.rmi.RemoteException, FFSException
/* 13832:      */   {
/* 13833:14097 */     for (int i = 1;; i++)
/* 13834:      */     {
/* 13835:14099 */       _Request local_Request = null;
/* 13836:14100 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13837:14101 */       boolean bool1 = false;
/* 13838:14102 */       LocalFrame localLocalFrame = null;
/* 13839:14103 */       boolean bool2 = false;
/* 13840:      */       try
/* 13841:      */       {
/* 13842:14106 */         local_Request = __request("getPagedCashCon");
/* 13843:14107 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13844:14108 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13845:14109 */         localLocalStack.setArgsOnLocal(bool1);
/* 13846:14111 */         if (bool1)
/* 13847:      */         {
/* 13848:14113 */           localLocalFrame = new LocalFrame(1);
/* 13849:14114 */           localLocalStack.push(localLocalFrame);
/* 13850:      */         }
/* 13851:14116 */         if (!bool1)
/* 13852:      */         {
/* 13853:14118 */           localObject4 = local_Request.getOutputStream();
/* 13854:14119 */           local_Request.write_value(paramPagingInfo, PagingInfo.class);
/* 13855:      */         }
/* 13856:      */         else
/* 13857:      */         {
/* 13858:14123 */           localObject4 = local_Request.getOutputStream();
/* 13859:14124 */           localLocalFrame.add(paramPagingInfo);
/* 13860:      */         }
/* 13861:14126 */         local_Request.invoke();
/* 13862:      */         Object localObject1;
/* 13863:14127 */         if (bool1)
/* 13864:      */         {
/* 13865:14129 */           localObject4 = null;
/* 13866:14130 */           localObject5 = localLocalFrame.getResult();
/* 13867:14131 */           if (localObject5 != null) {
/* 13868:14133 */             localObject4 = (PagingInfo)ObjectVal.clone(localObject5);
/* 13869:      */           }
/* 13870:14135 */           return localObject4;
/* 13871:      */         }
/* 13872:14137 */         Object localObject4 = local_Request.getInputStream();
/* 13873:      */         
/* 13874:14139 */         localObject5 = (PagingInfo)local_Request.read_value(PagingInfo.class);
/* 13875:14140 */         return localObject5;
/* 13876:      */       }
/* 13877:      */       catch (TRANSIENT localTRANSIENT)
/* 13878:      */       {
/* 13879:14144 */         if (i == 10) {
/* 13880:14146 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13881:      */         }
/* 13882:      */       }
/* 13883:      */       catch (UserException localUserException)
/* 13884:      */       {
/* 13885:      */         Object localObject5;
/* 13886:14151 */         if (local_Request.isRMI())
/* 13887:      */         {
/* 13888:14153 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 13889:14155 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13890:      */           }
/* 13891:      */         }
/* 13892:      */         else
/* 13893:      */         {
/* 13894:14160 */           localObject5 = null;
/* 13895:14161 */           if (bool1)
/* 13896:      */           {
/* 13897:14163 */             localObject5 = localLocalFrame.getException();
/* 13898:14164 */             if (localObject5 != null) {
/* 13899:14166 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 13900:      */             }
/* 13901:      */           }
/* 13902:14169 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 13903:      */           {
/* 13904:14171 */             if (local_Request.isRMI()) {
/* 13905:14173 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13906:      */             }
/* 13907:14177 */             if (bool1)
/* 13908:      */             {
/* 13909:14179 */               if (localObject5 != null) {
/* 13910:14179 */                 throw ((FFSException)localObject5);
/* 13911:      */               }
/* 13912:      */             }
/* 13913:      */             else {
/* 13914:14183 */               throw FFSExceptionHelper.read(localUserException.input);
/* 13915:      */             }
/* 13916:      */           }
/* 13917:      */         }
/* 13918:14188 */         throw new java.rmi.RemoteException(localUserException.type);
/* 13919:      */       }
/* 13920:      */       catch (SystemException localSystemException)
/* 13921:      */       {
/* 13922:14192 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 13923:      */       }
/* 13924:      */       finally
/* 13925:      */       {
/* 13926:14196 */         if (local_Request != null) {
/* 13927:14198 */           local_Request.close();
/* 13928:      */         }
/* 13929:14200 */         if (bool1) {
/* 13930:14201 */           localLocalStack.pop(localLocalFrame);
/* 13931:      */         }
/* 13932:14202 */         localLocalStack.setArgsOnLocal(bool2);
/* 13933:      */       }
/* 13934:      */     }
/* 13935:      */   }
/* 13936:      */   
/* 13937:      */   public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
/* 13938:      */     throws java.rmi.RemoteException, FFSException
/* 13939:      */   {
/* 13940:14212 */     for (int i = 1;; i++)
/* 13941:      */     {
/* 13942:14214 */       _Request local_Request = null;
/* 13943:14215 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 13944:14216 */       boolean bool1 = false;
/* 13945:14217 */       LocalFrame localLocalFrame = null;
/* 13946:14218 */       boolean bool2 = false;
/* 13947:      */       try
/* 13948:      */       {
/* 13949:14221 */         local_Request = __request("getPayeeByListId");
/* 13950:14222 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 13951:14223 */         bool2 = localLocalStack.isArgsOnLocal();
/* 13952:14224 */         localLocalStack.setArgsOnLocal(bool1);
/* 13953:14226 */         if (bool1)
/* 13954:      */         {
/* 13955:14228 */           localLocalFrame = new LocalFrame(2);
/* 13956:14229 */           localLocalStack.push(localLocalFrame);
/* 13957:      */         }
/* 13958:14231 */         if (!bool1)
/* 13959:      */         {
/* 13960:14233 */           localObject4 = local_Request.getOutputStream();
/* 13961:14234 */           local_Request.write_string(paramString1);
/* 13962:14235 */           local_Request.write_string(paramString2);
/* 13963:      */         }
/* 13964:      */         else
/* 13965:      */         {
/* 13966:14239 */           localObject4 = local_Request.getOutputStream();
/* 13967:14240 */           localLocalFrame.add(paramString1);
/* 13968:14241 */           localLocalFrame.add(paramString2);
/* 13969:      */         }
/* 13970:14243 */         local_Request.invoke();
/* 13971:      */         Object localObject1;
/* 13972:14244 */         if (bool1)
/* 13973:      */         {
/* 13974:14246 */           localObject4 = null;
/* 13975:14247 */           localObject5 = localLocalFrame.getResult();
/* 13976:14248 */           if (localObject5 != null) {
/* 13977:14250 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 13978:      */           }
/* 13979:14252 */           return localObject4;
/* 13980:      */         }
/* 13981:14254 */         Object localObject4 = local_Request.getInputStream();
/* 13982:      */         
/* 13983:14256 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 13984:14257 */         return localObject5;
/* 13985:      */       }
/* 13986:      */       catch (TRANSIENT localTRANSIENT)
/* 13987:      */       {
/* 13988:14261 */         if (i == 10) {
/* 13989:14263 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 13990:      */         }
/* 13991:      */       }
/* 13992:      */       catch (UserException localUserException)
/* 13993:      */       {
/* 13994:      */         Object localObject5;
/* 13995:14268 */         if (local_Request.isRMI())
/* 13996:      */         {
/* 13997:14270 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 13998:14272 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 13999:      */           }
/* 14000:      */         }
/* 14001:      */         else
/* 14002:      */         {
/* 14003:14277 */           localObject5 = null;
/* 14004:14278 */           if (bool1)
/* 14005:      */           {
/* 14006:14280 */             localObject5 = localLocalFrame.getException();
/* 14007:14281 */             if (localObject5 != null) {
/* 14008:14283 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14009:      */             }
/* 14010:      */           }
/* 14011:14286 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14012:      */           {
/* 14013:14288 */             if (local_Request.isRMI()) {
/* 14014:14290 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14015:      */             }
/* 14016:14294 */             if (bool1)
/* 14017:      */             {
/* 14018:14296 */               if (localObject5 != null) {
/* 14019:14296 */                 throw ((FFSException)localObject5);
/* 14020:      */               }
/* 14021:      */             }
/* 14022:      */             else {
/* 14023:14300 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14024:      */             }
/* 14025:      */           }
/* 14026:      */         }
/* 14027:14305 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14028:      */       }
/* 14029:      */       catch (SystemException localSystemException)
/* 14030:      */       {
/* 14031:14309 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14032:      */       }
/* 14033:      */       finally
/* 14034:      */       {
/* 14035:14313 */         if (local_Request != null) {
/* 14036:14315 */           local_Request.close();
/* 14037:      */         }
/* 14038:14317 */         if (bool1) {
/* 14039:14318 */           localLocalStack.pop(localLocalFrame);
/* 14040:      */         }
/* 14041:14319 */         localLocalStack.setArgsOnLocal(bool2);
/* 14042:      */       }
/* 14043:      */     }
/* 14044:      */   }
/* 14045:      */   
/* 14046:      */   public AccountTypesMap getAccountTypesMap()
/* 14047:      */     throws java.rmi.RemoteException, FFSException
/* 14048:      */   {
/* 14049:14327 */     for (int i = 1;; i++)
/* 14050:      */     {
/* 14051:14329 */       _Request local_Request = null;
/* 14052:14330 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14053:14331 */       boolean bool1 = false;
/* 14054:14332 */       LocalFrame localLocalFrame = null;
/* 14055:14333 */       boolean bool2 = false;
/* 14056:      */       try
/* 14057:      */       {
/* 14058:14336 */         local_Request = __request("getAccountTypesMap");
/* 14059:14337 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14060:14338 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14061:14339 */         localLocalStack.setArgsOnLocal(bool1);
/* 14062:14341 */         if (bool1)
/* 14063:      */         {
/* 14064:14343 */           localLocalFrame = new LocalFrame(0);
/* 14065:14344 */           localLocalStack.push(localLocalFrame);
/* 14066:      */         }
/* 14067:14352 */         local_Request.invoke();
/* 14068:      */         Object localObject1;
/* 14069:14353 */         if (bool1)
/* 14070:      */         {
/* 14071:14355 */           localObject4 = null;
/* 14072:14356 */           localObject5 = localLocalFrame.getResult();
/* 14073:14357 */           if (localObject5 != null) {
/* 14074:14359 */             localObject4 = (AccountTypesMap)ObjectVal.clone(localObject5);
/* 14075:      */           }
/* 14076:14361 */           return localObject4;
/* 14077:      */         }
/* 14078:14363 */         Object localObject4 = local_Request.getInputStream();
/* 14079:      */         
/* 14080:14365 */         localObject5 = (AccountTypesMap)local_Request.read_value(AccountTypesMap.class);
/* 14081:14366 */         return localObject5;
/* 14082:      */       }
/* 14083:      */       catch (TRANSIENT localTRANSIENT)
/* 14084:      */       {
/* 14085:14370 */         if (i == 10) {
/* 14086:14372 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14087:      */         }
/* 14088:      */       }
/* 14089:      */       catch (UserException localUserException)
/* 14090:      */       {
/* 14091:      */         Object localObject5;
/* 14092:14377 */         if (local_Request.isRMI())
/* 14093:      */         {
/* 14094:14379 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 14095:14381 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14096:      */           }
/* 14097:      */         }
/* 14098:      */         else
/* 14099:      */         {
/* 14100:14386 */           localObject5 = null;
/* 14101:14387 */           if (bool1)
/* 14102:      */           {
/* 14103:14389 */             localObject5 = localLocalFrame.getException();
/* 14104:14390 */             if (localObject5 != null) {
/* 14105:14392 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14106:      */             }
/* 14107:      */           }
/* 14108:14395 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14109:      */           {
/* 14110:14397 */             if (local_Request.isRMI()) {
/* 14111:14399 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14112:      */             }
/* 14113:14403 */             if (bool1)
/* 14114:      */             {
/* 14115:14405 */               if (localObject5 != null) {
/* 14116:14405 */                 throw ((FFSException)localObject5);
/* 14117:      */               }
/* 14118:      */             }
/* 14119:      */             else {
/* 14120:14409 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14121:      */             }
/* 14122:      */           }
/* 14123:      */         }
/* 14124:14414 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14125:      */       }
/* 14126:      */       catch (SystemException localSystemException)
/* 14127:      */       {
/* 14128:14418 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14129:      */       }
/* 14130:      */       finally
/* 14131:      */       {
/* 14132:14422 */         if (local_Request != null) {
/* 14133:14424 */           local_Request.close();
/* 14134:      */         }
/* 14135:14426 */         if (bool1) {
/* 14136:14427 */           localLocalStack.pop(localLocalFrame);
/* 14137:      */         }
/* 14138:14428 */         localLocalStack.setArgsOnLocal(bool2);
/* 14139:      */       }
/* 14140:      */     }
/* 14141:      */   }
/* 14142:      */   
/* 14143:      */   public ExtTransferAcctInfo modExtTransferAccountPrenoteStatus(ExtTransferAcctInfo paramExtTransferAcctInfo)
/* 14144:      */     throws java.rmi.RemoteException
/* 14145:      */   {
/* 14146:14437 */     for (int i = 1;; i++)
/* 14147:      */     {
/* 14148:14439 */       _Request local_Request = null;
/* 14149:14440 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14150:14441 */       boolean bool1 = false;
/* 14151:14442 */       LocalFrame localLocalFrame = null;
/* 14152:14443 */       boolean bool2 = false;
/* 14153:      */       try
/* 14154:      */       {
/* 14155:14446 */         local_Request = __request("modExtTransferAccountPrenoteStatus");
/* 14156:14447 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14157:14448 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14158:14449 */         localLocalStack.setArgsOnLocal(bool1);
/* 14159:14451 */         if (bool1)
/* 14160:      */         {
/* 14161:14453 */           localLocalFrame = new LocalFrame(1);
/* 14162:14454 */           localLocalStack.push(localLocalFrame);
/* 14163:      */         }
/* 14164:14456 */         if (!bool1)
/* 14165:      */         {
/* 14166:14458 */           localObject4 = local_Request.getOutputStream();
/* 14167:14459 */           local_Request.write_value(paramExtTransferAcctInfo, ExtTransferAcctInfo.class);
/* 14168:      */         }
/* 14169:      */         else
/* 14170:      */         {
/* 14171:14463 */           localObject4 = local_Request.getOutputStream();
/* 14172:14464 */           localLocalFrame.add(paramExtTransferAcctInfo);
/* 14173:      */         }
/* 14174:14466 */         local_Request.invoke();
/* 14175:      */         Object localObject1;
/* 14176:14467 */         if (bool1)
/* 14177:      */         {
/* 14178:14469 */           localObject4 = null;
/* 14179:14470 */           localObject5 = localLocalFrame.getResult();
/* 14180:14471 */           if (localObject5 != null) {
/* 14181:14473 */             localObject4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject5);
/* 14182:      */           }
/* 14183:14475 */           return localObject4;
/* 14184:      */         }
/* 14185:14477 */         Object localObject4 = local_Request.getInputStream();
/* 14186:      */         
/* 14187:14479 */         Object localObject5 = (ExtTransferAcctInfo)local_Request.read_value(ExtTransferAcctInfo.class);
/* 14188:14480 */         return localObject5;
/* 14189:      */       }
/* 14190:      */       catch (TRANSIENT localTRANSIENT)
/* 14191:      */       {
/* 14192:14484 */         if (i == 10) {
/* 14193:14486 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14194:      */         }
/* 14195:      */       }
/* 14196:      */       catch (UserException localUserException)
/* 14197:      */       {
/* 14198:14491 */         local_Request.isRMI();
/* 14199:      */         
/* 14200:      */ 
/* 14201:14494 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14202:      */       }
/* 14203:      */       catch (SystemException localSystemException)
/* 14204:      */       {
/* 14205:14498 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14206:      */       }
/* 14207:      */       finally
/* 14208:      */       {
/* 14209:14502 */         if (local_Request != null) {
/* 14210:14504 */           local_Request.close();
/* 14211:      */         }
/* 14212:14506 */         if (bool1) {
/* 14213:14507 */           localLocalStack.pop(localLocalFrame);
/* 14214:      */         }
/* 14215:14508 */         localLocalStack.setArgsOnLocal(bool2);
/* 14216:      */       }
/* 14217:      */     }
/* 14218:      */   }
/* 14219:      */   
/* 14220:      */   public String getBPWProperty(String paramString1, String paramString2)
/* 14221:      */     throws java.rmi.RemoteException, FFSException
/* 14222:      */   {
/* 14223:14518 */     for (int i = 1;; i++)
/* 14224:      */     {
/* 14225:14520 */       _Request local_Request = null;
/* 14226:14521 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14227:14522 */       boolean bool1 = false;
/* 14228:14523 */       LocalFrame localLocalFrame = null;
/* 14229:14524 */       boolean bool2 = false;
/* 14230:      */       try
/* 14231:      */       {
/* 14232:14527 */         local_Request = __request("getBPWProperty");
/* 14233:14528 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14234:14529 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14235:14530 */         localLocalStack.setArgsOnLocal(bool1);
/* 14236:14532 */         if (bool1)
/* 14237:      */         {
/* 14238:14534 */           localLocalFrame = new LocalFrame(2);
/* 14239:14535 */           localLocalStack.push(localLocalFrame);
/* 14240:      */         }
/* 14241:14537 */         if (!bool1)
/* 14242:      */         {
/* 14243:14539 */           localObject4 = local_Request.getOutputStream();
/* 14244:14540 */           local_Request.write_string(paramString1);
/* 14245:14541 */           local_Request.write_string(paramString2);
/* 14246:      */         }
/* 14247:      */         else
/* 14248:      */         {
/* 14249:14545 */           localObject4 = local_Request.getOutputStream();
/* 14250:14546 */           localLocalFrame.add(paramString1);
/* 14251:14547 */           localLocalFrame.add(paramString2);
/* 14252:      */         }
/* 14253:14549 */         local_Request.invoke();
/* 14254:      */         Object localObject1;
/* 14255:14550 */         if (bool1)
/* 14256:      */         {
/* 14257:14552 */           localObject4 = null;
/* 14258:14553 */           localObject4 = (String)localLocalFrame.getResult();
/* 14259:14554 */           return localObject4;
/* 14260:      */         }
/* 14261:14556 */         Object localObject4 = local_Request.getInputStream();
/* 14262:      */         
/* 14263:14558 */         localObject5 = local_Request.read_string();
/* 14264:14559 */         return localObject5;
/* 14265:      */       }
/* 14266:      */       catch (TRANSIENT localTRANSIENT)
/* 14267:      */       {
/* 14268:14563 */         if (i == 10) {
/* 14269:14565 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14270:      */         }
/* 14271:      */       }
/* 14272:      */       catch (UserException localUserException)
/* 14273:      */       {
/* 14274:      */         Object localObject5;
/* 14275:14570 */         if (local_Request.isRMI())
/* 14276:      */         {
/* 14277:14572 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 14278:14574 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14279:      */           }
/* 14280:      */         }
/* 14281:      */         else
/* 14282:      */         {
/* 14283:14579 */           localObject5 = null;
/* 14284:14580 */           if (bool1)
/* 14285:      */           {
/* 14286:14582 */             localObject5 = localLocalFrame.getException();
/* 14287:14583 */             if (localObject5 != null) {
/* 14288:14585 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14289:      */             }
/* 14290:      */           }
/* 14291:14588 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14292:      */           {
/* 14293:14590 */             if (local_Request.isRMI()) {
/* 14294:14592 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14295:      */             }
/* 14296:14596 */             if (bool1)
/* 14297:      */             {
/* 14298:14598 */               if (localObject5 != null) {
/* 14299:14598 */                 throw ((FFSException)localObject5);
/* 14300:      */               }
/* 14301:      */             }
/* 14302:      */             else {
/* 14303:14602 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14304:      */             }
/* 14305:      */           }
/* 14306:      */         }
/* 14307:14607 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14308:      */       }
/* 14309:      */       catch (SystemException localSystemException)
/* 14310:      */       {
/* 14311:14611 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14312:      */       }
/* 14313:      */       finally
/* 14314:      */       {
/* 14315:14615 */         if (local_Request != null) {
/* 14316:14617 */           local_Request.close();
/* 14317:      */         }
/* 14318:14619 */         if (bool1) {
/* 14319:14620 */           localLocalStack.pop(localLocalFrame);
/* 14320:      */         }
/* 14321:14621 */         localLocalStack.setArgsOnLocal(bool2);
/* 14322:      */       }
/* 14323:      */     }
/* 14324:      */   }
/* 14325:      */   
/* 14326:      */   public TransferBatchInfo addTransferBatch(TransferBatchInfo paramTransferBatchInfo)
/* 14327:      */     throws java.rmi.RemoteException, FFSException
/* 14328:      */   {
/* 14329:14630 */     for (int i = 1;; i++)
/* 14330:      */     {
/* 14331:14632 */       _Request local_Request = null;
/* 14332:14633 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14333:14634 */       boolean bool1 = false;
/* 14334:14635 */       LocalFrame localLocalFrame = null;
/* 14335:14636 */       boolean bool2 = false;
/* 14336:      */       try
/* 14337:      */       {
/* 14338:14639 */         local_Request = __request("addTransferBatch");
/* 14339:14640 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14340:14641 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14341:14642 */         localLocalStack.setArgsOnLocal(bool1);
/* 14342:14644 */         if (bool1)
/* 14343:      */         {
/* 14344:14646 */           localLocalFrame = new LocalFrame(1);
/* 14345:14647 */           localLocalStack.push(localLocalFrame);
/* 14346:      */         }
/* 14347:14649 */         if (!bool1)
/* 14348:      */         {
/* 14349:14651 */           localObject4 = local_Request.getOutputStream();
/* 14350:14652 */           local_Request.write_value(paramTransferBatchInfo, TransferBatchInfo.class);
/* 14351:      */         }
/* 14352:      */         else
/* 14353:      */         {
/* 14354:14656 */           localObject4 = local_Request.getOutputStream();
/* 14355:14657 */           localLocalFrame.add(paramTransferBatchInfo);
/* 14356:      */         }
/* 14357:14659 */         local_Request.invoke();
/* 14358:      */         Object localObject1;
/* 14359:14660 */         if (bool1)
/* 14360:      */         {
/* 14361:14662 */           localObject4 = null;
/* 14362:14663 */           localObject5 = localLocalFrame.getResult();
/* 14363:14664 */           if (localObject5 != null) {
/* 14364:14666 */             localObject4 = (TransferBatchInfo)ObjectVal.clone(localObject5);
/* 14365:      */           }
/* 14366:14668 */           return localObject4;
/* 14367:      */         }
/* 14368:14670 */         Object localObject4 = local_Request.getInputStream();
/* 14369:      */         
/* 14370:14672 */         localObject5 = (TransferBatchInfo)local_Request.read_value(TransferBatchInfo.class);
/* 14371:14673 */         return localObject5;
/* 14372:      */       }
/* 14373:      */       catch (TRANSIENT localTRANSIENT)
/* 14374:      */       {
/* 14375:14677 */         if (i == 10) {
/* 14376:14679 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14377:      */         }
/* 14378:      */       }
/* 14379:      */       catch (UserException localUserException)
/* 14380:      */       {
/* 14381:      */         Object localObject5;
/* 14382:14684 */         if (local_Request.isRMI())
/* 14383:      */         {
/* 14384:14686 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 14385:14688 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14386:      */           }
/* 14387:      */         }
/* 14388:      */         else
/* 14389:      */         {
/* 14390:14693 */           localObject5 = null;
/* 14391:14694 */           if (bool1)
/* 14392:      */           {
/* 14393:14696 */             localObject5 = localLocalFrame.getException();
/* 14394:14697 */             if (localObject5 != null) {
/* 14395:14699 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14396:      */             }
/* 14397:      */           }
/* 14398:14702 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14399:      */           {
/* 14400:14704 */             if (local_Request.isRMI()) {
/* 14401:14706 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14402:      */             }
/* 14403:14710 */             if (bool1)
/* 14404:      */             {
/* 14405:14712 */               if (localObject5 != null) {
/* 14406:14712 */                 throw ((FFSException)localObject5);
/* 14407:      */               }
/* 14408:      */             }
/* 14409:      */             else {
/* 14410:14716 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14411:      */             }
/* 14412:      */           }
/* 14413:      */         }
/* 14414:14721 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14415:      */       }
/* 14416:      */       catch (SystemException localSystemException)
/* 14417:      */       {
/* 14418:14725 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14419:      */       }
/* 14420:      */       finally
/* 14421:      */       {
/* 14422:14729 */         if (local_Request != null) {
/* 14423:14731 */           local_Request.close();
/* 14424:      */         }
/* 14425:14733 */         if (bool1) {
/* 14426:14734 */           localLocalStack.pop(localLocalFrame);
/* 14427:      */         }
/* 14428:14735 */         localLocalStack.setArgsOnLocal(bool2);
/* 14429:      */       }
/* 14430:      */     }
/* 14431:      */   }
/* 14432:      */   
/* 14433:      */   public TransferBatchInfo modifyTransferBatch(TransferBatchInfo paramTransferBatchInfo)
/* 14434:      */     throws java.rmi.RemoteException, FFSException
/* 14435:      */   {
/* 14436:14744 */     for (int i = 1;; i++)
/* 14437:      */     {
/* 14438:14746 */       _Request local_Request = null;
/* 14439:14747 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14440:14748 */       boolean bool1 = false;
/* 14441:14749 */       LocalFrame localLocalFrame = null;
/* 14442:14750 */       boolean bool2 = false;
/* 14443:      */       try
/* 14444:      */       {
/* 14445:14753 */         local_Request = __request("modifyTransferBatch");
/* 14446:14754 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14447:14755 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14448:14756 */         localLocalStack.setArgsOnLocal(bool1);
/* 14449:14758 */         if (bool1)
/* 14450:      */         {
/* 14451:14760 */           localLocalFrame = new LocalFrame(1);
/* 14452:14761 */           localLocalStack.push(localLocalFrame);
/* 14453:      */         }
/* 14454:14763 */         if (!bool1)
/* 14455:      */         {
/* 14456:14765 */           localObject4 = local_Request.getOutputStream();
/* 14457:14766 */           local_Request.write_value(paramTransferBatchInfo, TransferBatchInfo.class);
/* 14458:      */         }
/* 14459:      */         else
/* 14460:      */         {
/* 14461:14770 */           localObject4 = local_Request.getOutputStream();
/* 14462:14771 */           localLocalFrame.add(paramTransferBatchInfo);
/* 14463:      */         }
/* 14464:14773 */         local_Request.invoke();
/* 14465:      */         Object localObject1;
/* 14466:14774 */         if (bool1)
/* 14467:      */         {
/* 14468:14776 */           localObject4 = null;
/* 14469:14777 */           localObject5 = localLocalFrame.getResult();
/* 14470:14778 */           if (localObject5 != null) {
/* 14471:14780 */             localObject4 = (TransferBatchInfo)ObjectVal.clone(localObject5);
/* 14472:      */           }
/* 14473:14782 */           return localObject4;
/* 14474:      */         }
/* 14475:14784 */         Object localObject4 = local_Request.getInputStream();
/* 14476:      */         
/* 14477:14786 */         localObject5 = (TransferBatchInfo)local_Request.read_value(TransferBatchInfo.class);
/* 14478:14787 */         return localObject5;
/* 14479:      */       }
/* 14480:      */       catch (TRANSIENT localTRANSIENT)
/* 14481:      */       {
/* 14482:14791 */         if (i == 10) {
/* 14483:14793 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14484:      */         }
/* 14485:      */       }
/* 14486:      */       catch (UserException localUserException)
/* 14487:      */       {
/* 14488:      */         Object localObject5;
/* 14489:14798 */         if (local_Request.isRMI())
/* 14490:      */         {
/* 14491:14800 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 14492:14802 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14493:      */           }
/* 14494:      */         }
/* 14495:      */         else
/* 14496:      */         {
/* 14497:14807 */           localObject5 = null;
/* 14498:14808 */           if (bool1)
/* 14499:      */           {
/* 14500:14810 */             localObject5 = localLocalFrame.getException();
/* 14501:14811 */             if (localObject5 != null) {
/* 14502:14813 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14503:      */             }
/* 14504:      */           }
/* 14505:14816 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14506:      */           {
/* 14507:14818 */             if (local_Request.isRMI()) {
/* 14508:14820 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14509:      */             }
/* 14510:14824 */             if (bool1)
/* 14511:      */             {
/* 14512:14826 */               if (localObject5 != null) {
/* 14513:14826 */                 throw ((FFSException)localObject5);
/* 14514:      */               }
/* 14515:      */             }
/* 14516:      */             else {
/* 14517:14830 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14518:      */             }
/* 14519:      */           }
/* 14520:      */         }
/* 14521:14835 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14522:      */       }
/* 14523:      */       catch (SystemException localSystemException)
/* 14524:      */       {
/* 14525:14839 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14526:      */       }
/* 14527:      */       finally
/* 14528:      */       {
/* 14529:14843 */         if (local_Request != null) {
/* 14530:14845 */           local_Request.close();
/* 14531:      */         }
/* 14532:14847 */         if (bool1) {
/* 14533:14848 */           localLocalStack.pop(localLocalFrame);
/* 14534:      */         }
/* 14535:14849 */         localLocalStack.setArgsOnLocal(bool2);
/* 14536:      */       }
/* 14537:      */     }
/* 14538:      */   }
/* 14539:      */   
/* 14540:      */   public TransferBatchInfo cancelTransferBatch(TransferBatchInfo paramTransferBatchInfo)
/* 14541:      */     throws java.rmi.RemoteException, FFSException
/* 14542:      */   {
/* 14543:14858 */     for (int i = 1;; i++)
/* 14544:      */     {
/* 14545:14860 */       _Request local_Request = null;
/* 14546:14861 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14547:14862 */       boolean bool1 = false;
/* 14548:14863 */       LocalFrame localLocalFrame = null;
/* 14549:14864 */       boolean bool2 = false;
/* 14550:      */       try
/* 14551:      */       {
/* 14552:14867 */         local_Request = __request("cancelTransferBatch");
/* 14553:14868 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14554:14869 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14555:14870 */         localLocalStack.setArgsOnLocal(bool1);
/* 14556:14872 */         if (bool1)
/* 14557:      */         {
/* 14558:14874 */           localLocalFrame = new LocalFrame(1);
/* 14559:14875 */           localLocalStack.push(localLocalFrame);
/* 14560:      */         }
/* 14561:14877 */         if (!bool1)
/* 14562:      */         {
/* 14563:14879 */           localObject4 = local_Request.getOutputStream();
/* 14564:14880 */           local_Request.write_value(paramTransferBatchInfo, TransferBatchInfo.class);
/* 14565:      */         }
/* 14566:      */         else
/* 14567:      */         {
/* 14568:14884 */           localObject4 = local_Request.getOutputStream();
/* 14569:14885 */           localLocalFrame.add(paramTransferBatchInfo);
/* 14570:      */         }
/* 14571:14887 */         local_Request.invoke();
/* 14572:      */         Object localObject1;
/* 14573:14888 */         if (bool1)
/* 14574:      */         {
/* 14575:14890 */           localObject4 = null;
/* 14576:14891 */           localObject5 = localLocalFrame.getResult();
/* 14577:14892 */           if (localObject5 != null) {
/* 14578:14894 */             localObject4 = (TransferBatchInfo)ObjectVal.clone(localObject5);
/* 14579:      */           }
/* 14580:14896 */           return localObject4;
/* 14581:      */         }
/* 14582:14898 */         Object localObject4 = local_Request.getInputStream();
/* 14583:      */         
/* 14584:14900 */         localObject5 = (TransferBatchInfo)local_Request.read_value(TransferBatchInfo.class);
/* 14585:14901 */         return localObject5;
/* 14586:      */       }
/* 14587:      */       catch (TRANSIENT localTRANSIENT)
/* 14588:      */       {
/* 14589:14905 */         if (i == 10) {
/* 14590:14907 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14591:      */         }
/* 14592:      */       }
/* 14593:      */       catch (UserException localUserException)
/* 14594:      */       {
/* 14595:      */         Object localObject5;
/* 14596:14912 */         if (local_Request.isRMI())
/* 14597:      */         {
/* 14598:14914 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 14599:14916 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14600:      */           }
/* 14601:      */         }
/* 14602:      */         else
/* 14603:      */         {
/* 14604:14921 */           localObject5 = null;
/* 14605:14922 */           if (bool1)
/* 14606:      */           {
/* 14607:14924 */             localObject5 = localLocalFrame.getException();
/* 14608:14925 */             if (localObject5 != null) {
/* 14609:14927 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14610:      */             }
/* 14611:      */           }
/* 14612:14930 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14613:      */           {
/* 14614:14932 */             if (local_Request.isRMI()) {
/* 14615:14934 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14616:      */             }
/* 14617:14938 */             if (bool1)
/* 14618:      */             {
/* 14619:14940 */               if (localObject5 != null) {
/* 14620:14940 */                 throw ((FFSException)localObject5);
/* 14621:      */               }
/* 14622:      */             }
/* 14623:      */             else {
/* 14624:14944 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14625:      */             }
/* 14626:      */           }
/* 14627:      */         }
/* 14628:14949 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14629:      */       }
/* 14630:      */       catch (SystemException localSystemException)
/* 14631:      */       {
/* 14632:14953 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14633:      */       }
/* 14634:      */       finally
/* 14635:      */       {
/* 14636:14957 */         if (local_Request != null) {
/* 14637:14959 */           local_Request.close();
/* 14638:      */         }
/* 14639:14961 */         if (bool1) {
/* 14640:14962 */           localLocalStack.pop(localLocalFrame);
/* 14641:      */         }
/* 14642:14963 */         localLocalStack.setArgsOnLocal(bool2);
/* 14643:      */       }
/* 14644:      */     }
/* 14645:      */   }
/* 14646:      */   
/* 14647:      */   public TransferBatchInfo getTransferBatchById(String paramString)
/* 14648:      */     throws java.rmi.RemoteException, FFSException
/* 14649:      */   {
/* 14650:14972 */     for (int i = 1;; i++)
/* 14651:      */     {
/* 14652:14974 */       _Request local_Request = null;
/* 14653:14975 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14654:14976 */       boolean bool1 = false;
/* 14655:14977 */       LocalFrame localLocalFrame = null;
/* 14656:14978 */       boolean bool2 = false;
/* 14657:      */       try
/* 14658:      */       {
/* 14659:14981 */         local_Request = __request("getTransferBatchById");
/* 14660:14982 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14661:14983 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14662:14984 */         localLocalStack.setArgsOnLocal(bool1);
/* 14663:14986 */         if (bool1)
/* 14664:      */         {
/* 14665:14988 */           localLocalFrame = new LocalFrame(1);
/* 14666:14989 */           localLocalStack.push(localLocalFrame);
/* 14667:      */         }
/* 14668:14991 */         if (!bool1)
/* 14669:      */         {
/* 14670:14993 */           localObject4 = local_Request.getOutputStream();
/* 14671:14994 */           local_Request.write_string(paramString);
/* 14672:      */         }
/* 14673:      */         else
/* 14674:      */         {
/* 14675:14998 */           localObject4 = local_Request.getOutputStream();
/* 14676:14999 */           localLocalFrame.add(paramString);
/* 14677:      */         }
/* 14678:15001 */         local_Request.invoke();
/* 14679:      */         Object localObject1;
/* 14680:15002 */         if (bool1)
/* 14681:      */         {
/* 14682:15004 */           localObject4 = null;
/* 14683:15005 */           localObject5 = localLocalFrame.getResult();
/* 14684:15006 */           if (localObject5 != null) {
/* 14685:15008 */             localObject4 = (TransferBatchInfo)ObjectVal.clone(localObject5);
/* 14686:      */           }
/* 14687:15010 */           return localObject4;
/* 14688:      */         }
/* 14689:15012 */         Object localObject4 = local_Request.getInputStream();
/* 14690:      */         
/* 14691:15014 */         localObject5 = (TransferBatchInfo)local_Request.read_value(TransferBatchInfo.class);
/* 14692:15015 */         return localObject5;
/* 14693:      */       }
/* 14694:      */       catch (TRANSIENT localTRANSIENT)
/* 14695:      */       {
/* 14696:15019 */         if (i == 10) {
/* 14697:15021 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14698:      */         }
/* 14699:      */       }
/* 14700:      */       catch (UserException localUserException)
/* 14701:      */       {
/* 14702:      */         Object localObject5;
/* 14703:15026 */         if (local_Request.isRMI())
/* 14704:      */         {
/* 14705:15028 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 14706:15030 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14707:      */           }
/* 14708:      */         }
/* 14709:      */         else
/* 14710:      */         {
/* 14711:15035 */           localObject5 = null;
/* 14712:15036 */           if (bool1)
/* 14713:      */           {
/* 14714:15038 */             localObject5 = localLocalFrame.getException();
/* 14715:15039 */             if (localObject5 != null) {
/* 14716:15041 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14717:      */             }
/* 14718:      */           }
/* 14719:15044 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14720:      */           {
/* 14721:15046 */             if (local_Request.isRMI()) {
/* 14722:15048 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14723:      */             }
/* 14724:15052 */             if (bool1)
/* 14725:      */             {
/* 14726:15054 */               if (localObject5 != null) {
/* 14727:15054 */                 throw ((FFSException)localObject5);
/* 14728:      */               }
/* 14729:      */             }
/* 14730:      */             else {
/* 14731:15058 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14732:      */             }
/* 14733:      */           }
/* 14734:      */         }
/* 14735:15063 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14736:      */       }
/* 14737:      */       catch (SystemException localSystemException)
/* 14738:      */       {
/* 14739:15067 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14740:      */       }
/* 14741:      */       finally
/* 14742:      */       {
/* 14743:15071 */         if (local_Request != null) {
/* 14744:15073 */           local_Request.close();
/* 14745:      */         }
/* 14746:15075 */         if (bool1) {
/* 14747:15076 */           localLocalStack.pop(localLocalFrame);
/* 14748:      */         }
/* 14749:15077 */         localLocalStack.setArgsOnLocal(bool2);
/* 14750:      */       }
/* 14751:      */     }
/* 14752:      */   }
/* 14753:      */   
/* 14754:      */   public AccountTransactions accountHasPendingTransfers(AccountTransactions paramAccountTransactions)
/* 14755:      */     throws java.rmi.RemoteException, FFSException
/* 14756:      */   {
/* 14757:15086 */     for (int i = 1;; i++)
/* 14758:      */     {
/* 14759:15088 */       _Request local_Request = null;
/* 14760:15089 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14761:15090 */       boolean bool1 = false;
/* 14762:15091 */       LocalFrame localLocalFrame = null;
/* 14763:15092 */       boolean bool2 = false;
/* 14764:      */       try
/* 14765:      */       {
/* 14766:15095 */         local_Request = __request("accountHasPendingTransfers");
/* 14767:15096 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14768:15097 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14769:15098 */         localLocalStack.setArgsOnLocal(bool1);
/* 14770:15100 */         if (bool1)
/* 14771:      */         {
/* 14772:15102 */           localLocalFrame = new LocalFrame(1);
/* 14773:15103 */           localLocalStack.push(localLocalFrame);
/* 14774:      */         }
/* 14775:15105 */         if (!bool1)
/* 14776:      */         {
/* 14777:15107 */           localObject4 = local_Request.getOutputStream();
/* 14778:15108 */           local_Request.write_value(paramAccountTransactions, AccountTransactions.class);
/* 14779:      */         }
/* 14780:      */         else
/* 14781:      */         {
/* 14782:15112 */           localObject4 = local_Request.getOutputStream();
/* 14783:15113 */           localLocalFrame.add(paramAccountTransactions);
/* 14784:      */         }
/* 14785:15115 */         local_Request.invoke();
/* 14786:      */         Object localObject1;
/* 14787:15116 */         if (bool1)
/* 14788:      */         {
/* 14789:15118 */           localObject4 = null;
/* 14790:15119 */           localObject5 = localLocalFrame.getResult();
/* 14791:15120 */           if (localObject5 != null) {
/* 14792:15122 */             localObject4 = (AccountTransactions)ObjectVal.clone(localObject5);
/* 14793:      */           }
/* 14794:15124 */           return localObject4;
/* 14795:      */         }
/* 14796:15126 */         Object localObject4 = local_Request.getInputStream();
/* 14797:      */         
/* 14798:15128 */         localObject5 = (AccountTransactions)local_Request.read_value(AccountTransactions.class);
/* 14799:15129 */         return localObject5;
/* 14800:      */       }
/* 14801:      */       catch (TRANSIENT localTRANSIENT)
/* 14802:      */       {
/* 14803:15133 */         if (i == 10) {
/* 14804:15135 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14805:      */         }
/* 14806:      */       }
/* 14807:      */       catch (UserException localUserException)
/* 14808:      */       {
/* 14809:      */         Object localObject5;
/* 14810:15140 */         if (local_Request.isRMI())
/* 14811:      */         {
/* 14812:15142 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 14813:15144 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14814:      */           }
/* 14815:      */         }
/* 14816:      */         else
/* 14817:      */         {
/* 14818:15149 */           localObject5 = null;
/* 14819:15150 */           if (bool1)
/* 14820:      */           {
/* 14821:15152 */             localObject5 = localLocalFrame.getException();
/* 14822:15153 */             if (localObject5 != null) {
/* 14823:15155 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14824:      */             }
/* 14825:      */           }
/* 14826:15158 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14827:      */           {
/* 14828:15160 */             if (local_Request.isRMI()) {
/* 14829:15162 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14830:      */             }
/* 14831:15166 */             if (bool1)
/* 14832:      */             {
/* 14833:15168 */               if (localObject5 != null) {
/* 14834:15168 */                 throw ((FFSException)localObject5);
/* 14835:      */               }
/* 14836:      */             }
/* 14837:      */             else {
/* 14838:15172 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14839:      */             }
/* 14840:      */           }
/* 14841:      */         }
/* 14842:15177 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14843:      */       }
/* 14844:      */       catch (SystemException localSystemException)
/* 14845:      */       {
/* 14846:15181 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14847:      */       }
/* 14848:      */       finally
/* 14849:      */       {
/* 14850:15185 */         if (local_Request != null) {
/* 14851:15187 */           local_Request.close();
/* 14852:      */         }
/* 14853:15189 */         if (bool1) {
/* 14854:15190 */           localLocalStack.pop(localLocalFrame);
/* 14855:      */         }
/* 14856:15191 */         localLocalStack.setArgsOnLocal(bool2);
/* 14857:      */       }
/* 14858:      */     }
/* 14859:      */   }
/* 14860:      */   
/* 14861:      */   public PmtInfo addBillPayment(PmtInfo paramPmtInfo)
/* 14862:      */     throws java.rmi.RemoteException, FFSException
/* 14863:      */   {
/* 14864:15200 */     for (int i = 1;; i++)
/* 14865:      */     {
/* 14866:15202 */       _Request local_Request = null;
/* 14867:15203 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14868:15204 */       boolean bool1 = false;
/* 14869:15205 */       LocalFrame localLocalFrame = null;
/* 14870:15206 */       boolean bool2 = false;
/* 14871:      */       try
/* 14872:      */       {
/* 14873:15209 */         local_Request = __request("addBillPayment");
/* 14874:15210 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14875:15211 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14876:15212 */         localLocalStack.setArgsOnLocal(bool1);
/* 14877:15214 */         if (bool1)
/* 14878:      */         {
/* 14879:15216 */           localLocalFrame = new LocalFrame(1);
/* 14880:15217 */           localLocalStack.push(localLocalFrame);
/* 14881:      */         }
/* 14882:15219 */         if (!bool1)
/* 14883:      */         {
/* 14884:15221 */           localObject4 = local_Request.getOutputStream();
/* 14885:15222 */           local_Request.write_value(paramPmtInfo, PmtInfo.class);
/* 14886:      */         }
/* 14887:      */         else
/* 14888:      */         {
/* 14889:15226 */           localObject4 = local_Request.getOutputStream();
/* 14890:15227 */           localLocalFrame.add(paramPmtInfo);
/* 14891:      */         }
/* 14892:15229 */         local_Request.invoke();
/* 14893:      */         Object localObject1;
/* 14894:15230 */         if (bool1)
/* 14895:      */         {
/* 14896:15232 */           localObject4 = null;
/* 14897:15233 */           localObject5 = localLocalFrame.getResult();
/* 14898:15234 */           if (localObject5 != null) {
/* 14899:15236 */             localObject4 = (PmtInfo)ObjectVal.clone(localObject5);
/* 14900:      */           }
/* 14901:15238 */           return localObject4;
/* 14902:      */         }
/* 14903:15240 */         Object localObject4 = local_Request.getInputStream();
/* 14904:      */         
/* 14905:15242 */         localObject5 = (PmtInfo)local_Request.read_value(PmtInfo.class);
/* 14906:15243 */         return localObject5;
/* 14907:      */       }
/* 14908:      */       catch (TRANSIENT localTRANSIENT)
/* 14909:      */       {
/* 14910:15247 */         if (i == 10) {
/* 14911:15249 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 14912:      */         }
/* 14913:      */       }
/* 14914:      */       catch (UserException localUserException)
/* 14915:      */       {
/* 14916:      */         Object localObject5;
/* 14917:15254 */         if (local_Request.isRMI())
/* 14918:      */         {
/* 14919:15256 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 14920:15258 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14921:      */           }
/* 14922:      */         }
/* 14923:      */         else
/* 14924:      */         {
/* 14925:15263 */           localObject5 = null;
/* 14926:15264 */           if (bool1)
/* 14927:      */           {
/* 14928:15266 */             localObject5 = localLocalFrame.getException();
/* 14929:15267 */             if (localObject5 != null) {
/* 14930:15269 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 14931:      */             }
/* 14932:      */           }
/* 14933:15272 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 14934:      */           {
/* 14935:15274 */             if (local_Request.isRMI()) {
/* 14936:15276 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 14937:      */             }
/* 14938:15280 */             if (bool1)
/* 14939:      */             {
/* 14940:15282 */               if (localObject5 != null) {
/* 14941:15282 */                 throw ((FFSException)localObject5);
/* 14942:      */               }
/* 14943:      */             }
/* 14944:      */             else {
/* 14945:15286 */               throw FFSExceptionHelper.read(localUserException.input);
/* 14946:      */             }
/* 14947:      */           }
/* 14948:      */         }
/* 14949:15291 */         throw new java.rmi.RemoteException(localUserException.type);
/* 14950:      */       }
/* 14951:      */       catch (SystemException localSystemException)
/* 14952:      */       {
/* 14953:15295 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 14954:      */       }
/* 14955:      */       finally
/* 14956:      */       {
/* 14957:15299 */         if (local_Request != null) {
/* 14958:15301 */           local_Request.close();
/* 14959:      */         }
/* 14960:15303 */         if (bool1) {
/* 14961:15304 */           localLocalStack.pop(localLocalFrame);
/* 14962:      */         }
/* 14963:15305 */         localLocalStack.setArgsOnLocal(bool2);
/* 14964:      */       }
/* 14965:      */     }
/* 14966:      */   }
/* 14967:      */   
/* 14968:      */   public PmtInfo modifyBillPayment(PmtInfo paramPmtInfo)
/* 14969:      */     throws java.rmi.RemoteException, FFSException
/* 14970:      */   {
/* 14971:15314 */     for (int i = 1;; i++)
/* 14972:      */     {
/* 14973:15316 */       _Request local_Request = null;
/* 14974:15317 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 14975:15318 */       boolean bool1 = false;
/* 14976:15319 */       LocalFrame localLocalFrame = null;
/* 14977:15320 */       boolean bool2 = false;
/* 14978:      */       try
/* 14979:      */       {
/* 14980:15323 */         local_Request = __request("modifyBillPayment");
/* 14981:15324 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 14982:15325 */         bool2 = localLocalStack.isArgsOnLocal();
/* 14983:15326 */         localLocalStack.setArgsOnLocal(bool1);
/* 14984:15328 */         if (bool1)
/* 14985:      */         {
/* 14986:15330 */           localLocalFrame = new LocalFrame(1);
/* 14987:15331 */           localLocalStack.push(localLocalFrame);
/* 14988:      */         }
/* 14989:15333 */         if (!bool1)
/* 14990:      */         {
/* 14991:15335 */           localObject4 = local_Request.getOutputStream();
/* 14992:15336 */           local_Request.write_value(paramPmtInfo, PmtInfo.class);
/* 14993:      */         }
/* 14994:      */         else
/* 14995:      */         {
/* 14996:15340 */           localObject4 = local_Request.getOutputStream();
/* 14997:15341 */           localLocalFrame.add(paramPmtInfo);
/* 14998:      */         }
/* 14999:15343 */         local_Request.invoke();
/* 15000:      */         Object localObject1;
/* 15001:15344 */         if (bool1)
/* 15002:      */         {
/* 15003:15346 */           localObject4 = null;
/* 15004:15347 */           localObject5 = localLocalFrame.getResult();
/* 15005:15348 */           if (localObject5 != null) {
/* 15006:15350 */             localObject4 = (PmtInfo)ObjectVal.clone(localObject5);
/* 15007:      */           }
/* 15008:15352 */           return localObject4;
/* 15009:      */         }
/* 15010:15354 */         Object localObject4 = local_Request.getInputStream();
/* 15011:      */         
/* 15012:15356 */         localObject5 = (PmtInfo)local_Request.read_value(PmtInfo.class);
/* 15013:15357 */         return localObject5;
/* 15014:      */       }
/* 15015:      */       catch (TRANSIENT localTRANSIENT)
/* 15016:      */       {
/* 15017:15361 */         if (i == 10) {
/* 15018:15363 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15019:      */         }
/* 15020:      */       }
/* 15021:      */       catch (UserException localUserException)
/* 15022:      */       {
/* 15023:      */         Object localObject5;
/* 15024:15368 */         if (local_Request.isRMI())
/* 15025:      */         {
/* 15026:15370 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15027:15372 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15028:      */           }
/* 15029:      */         }
/* 15030:      */         else
/* 15031:      */         {
/* 15032:15377 */           localObject5 = null;
/* 15033:15378 */           if (bool1)
/* 15034:      */           {
/* 15035:15380 */             localObject5 = localLocalFrame.getException();
/* 15036:15381 */             if (localObject5 != null) {
/* 15037:15383 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15038:      */             }
/* 15039:      */           }
/* 15040:15386 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15041:      */           {
/* 15042:15388 */             if (local_Request.isRMI()) {
/* 15043:15390 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15044:      */             }
/* 15045:15394 */             if (bool1)
/* 15046:      */             {
/* 15047:15396 */               if (localObject5 != null) {
/* 15048:15396 */                 throw ((FFSException)localObject5);
/* 15049:      */               }
/* 15050:      */             }
/* 15051:      */             else {
/* 15052:15400 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15053:      */             }
/* 15054:      */           }
/* 15055:      */         }
/* 15056:15405 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15057:      */       }
/* 15058:      */       catch (SystemException localSystemException)
/* 15059:      */       {
/* 15060:15409 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15061:      */       }
/* 15062:      */       finally
/* 15063:      */       {
/* 15064:15413 */         if (local_Request != null) {
/* 15065:15415 */           local_Request.close();
/* 15066:      */         }
/* 15067:15417 */         if (bool1) {
/* 15068:15418 */           localLocalStack.pop(localLocalFrame);
/* 15069:      */         }
/* 15070:15419 */         localLocalStack.setArgsOnLocal(bool2);
/* 15071:      */       }
/* 15072:      */     }
/* 15073:      */   }
/* 15074:      */   
/* 15075:      */   public PmtInfo deleteBillPayment(PmtInfo paramPmtInfo)
/* 15076:      */     throws java.rmi.RemoteException, FFSException
/* 15077:      */   {
/* 15078:15428 */     for (int i = 1;; i++)
/* 15079:      */     {
/* 15080:15430 */       _Request local_Request = null;
/* 15081:15431 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15082:15432 */       boolean bool1 = false;
/* 15083:15433 */       LocalFrame localLocalFrame = null;
/* 15084:15434 */       boolean bool2 = false;
/* 15085:      */       try
/* 15086:      */       {
/* 15087:15437 */         local_Request = __request("deleteBillPayment");
/* 15088:15438 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15089:15439 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15090:15440 */         localLocalStack.setArgsOnLocal(bool1);
/* 15091:15442 */         if (bool1)
/* 15092:      */         {
/* 15093:15444 */           localLocalFrame = new LocalFrame(1);
/* 15094:15445 */           localLocalStack.push(localLocalFrame);
/* 15095:      */         }
/* 15096:15447 */         if (!bool1)
/* 15097:      */         {
/* 15098:15449 */           localObject4 = local_Request.getOutputStream();
/* 15099:15450 */           local_Request.write_value(paramPmtInfo, PmtInfo.class);
/* 15100:      */         }
/* 15101:      */         else
/* 15102:      */         {
/* 15103:15454 */           localObject4 = local_Request.getOutputStream();
/* 15104:15455 */           localLocalFrame.add(paramPmtInfo);
/* 15105:      */         }
/* 15106:15457 */         local_Request.invoke();
/* 15107:      */         Object localObject1;
/* 15108:15458 */         if (bool1)
/* 15109:      */         {
/* 15110:15460 */           localObject4 = null;
/* 15111:15461 */           localObject5 = localLocalFrame.getResult();
/* 15112:15462 */           if (localObject5 != null) {
/* 15113:15464 */             localObject4 = (PmtInfo)ObjectVal.clone(localObject5);
/* 15114:      */           }
/* 15115:15466 */           return localObject4;
/* 15116:      */         }
/* 15117:15468 */         Object localObject4 = local_Request.getInputStream();
/* 15118:      */         
/* 15119:15470 */         localObject5 = (PmtInfo)local_Request.read_value(PmtInfo.class);
/* 15120:15471 */         return localObject5;
/* 15121:      */       }
/* 15122:      */       catch (TRANSIENT localTRANSIENT)
/* 15123:      */       {
/* 15124:15475 */         if (i == 10) {
/* 15125:15477 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15126:      */         }
/* 15127:      */       }
/* 15128:      */       catch (UserException localUserException)
/* 15129:      */       {
/* 15130:      */         Object localObject5;
/* 15131:15482 */         if (local_Request.isRMI())
/* 15132:      */         {
/* 15133:15484 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15134:15486 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15135:      */           }
/* 15136:      */         }
/* 15137:      */         else
/* 15138:      */         {
/* 15139:15491 */           localObject5 = null;
/* 15140:15492 */           if (bool1)
/* 15141:      */           {
/* 15142:15494 */             localObject5 = localLocalFrame.getException();
/* 15143:15495 */             if (localObject5 != null) {
/* 15144:15497 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15145:      */             }
/* 15146:      */           }
/* 15147:15500 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15148:      */           {
/* 15149:15502 */             if (local_Request.isRMI()) {
/* 15150:15504 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15151:      */             }
/* 15152:15508 */             if (bool1)
/* 15153:      */             {
/* 15154:15510 */               if (localObject5 != null) {
/* 15155:15510 */                 throw ((FFSException)localObject5);
/* 15156:      */               }
/* 15157:      */             }
/* 15158:      */             else {
/* 15159:15514 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15160:      */             }
/* 15161:      */           }
/* 15162:      */         }
/* 15163:15519 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15164:      */       }
/* 15165:      */       catch (SystemException localSystemException)
/* 15166:      */       {
/* 15167:15523 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15168:      */       }
/* 15169:      */       finally
/* 15170:      */       {
/* 15171:15527 */         if (local_Request != null) {
/* 15172:15529 */           local_Request.close();
/* 15173:      */         }
/* 15174:15531 */         if (bool1) {
/* 15175:15532 */           localLocalStack.pop(localLocalFrame);
/* 15176:      */         }
/* 15177:15533 */         localLocalStack.setArgsOnLocal(bool2);
/* 15178:      */       }
/* 15179:      */     }
/* 15180:      */   }
/* 15181:      */   
/* 15182:      */   public PmtInfo getBillPaymentById(String paramString1, String paramString2)
/* 15183:      */     throws java.rmi.RemoteException, FFSException
/* 15184:      */   {
/* 15185:15543 */     for (int i = 1;; i++)
/* 15186:      */     {
/* 15187:15545 */       _Request local_Request = null;
/* 15188:15546 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15189:15547 */       boolean bool1 = false;
/* 15190:15548 */       LocalFrame localLocalFrame = null;
/* 15191:15549 */       boolean bool2 = false;
/* 15192:      */       try
/* 15193:      */       {
/* 15194:15552 */         local_Request = __request("getBillPaymentById");
/* 15195:15553 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15196:15554 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15197:15555 */         localLocalStack.setArgsOnLocal(bool1);
/* 15198:15557 */         if (bool1)
/* 15199:      */         {
/* 15200:15559 */           localLocalFrame = new LocalFrame(2);
/* 15201:15560 */           localLocalStack.push(localLocalFrame);
/* 15202:      */         }
/* 15203:15562 */         if (!bool1)
/* 15204:      */         {
/* 15205:15564 */           localObject4 = local_Request.getOutputStream();
/* 15206:15565 */           local_Request.write_string(paramString1);
/* 15207:15566 */           local_Request.write_string(paramString2);
/* 15208:      */         }
/* 15209:      */         else
/* 15210:      */         {
/* 15211:15570 */           localObject4 = local_Request.getOutputStream();
/* 15212:15571 */           localLocalFrame.add(paramString1);
/* 15213:15572 */           localLocalFrame.add(paramString2);
/* 15214:      */         }
/* 15215:15574 */         local_Request.invoke();
/* 15216:      */         Object localObject1;
/* 15217:15575 */         if (bool1)
/* 15218:      */         {
/* 15219:15577 */           localObject4 = null;
/* 15220:15578 */           localObject5 = localLocalFrame.getResult();
/* 15221:15579 */           if (localObject5 != null) {
/* 15222:15581 */             localObject4 = (PmtInfo)ObjectVal.clone(localObject5);
/* 15223:      */           }
/* 15224:15583 */           return localObject4;
/* 15225:      */         }
/* 15226:15585 */         Object localObject4 = local_Request.getInputStream();
/* 15227:      */         
/* 15228:15587 */         localObject5 = (PmtInfo)local_Request.read_value(PmtInfo.class);
/* 15229:15588 */         return localObject5;
/* 15230:      */       }
/* 15231:      */       catch (TRANSIENT localTRANSIENT)
/* 15232:      */       {
/* 15233:15592 */         if (i == 10) {
/* 15234:15594 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15235:      */         }
/* 15236:      */       }
/* 15237:      */       catch (UserException localUserException)
/* 15238:      */       {
/* 15239:      */         Object localObject5;
/* 15240:15599 */         if (local_Request.isRMI())
/* 15241:      */         {
/* 15242:15601 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15243:15603 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15244:      */           }
/* 15245:      */         }
/* 15246:      */         else
/* 15247:      */         {
/* 15248:15608 */           localObject5 = null;
/* 15249:15609 */           if (bool1)
/* 15250:      */           {
/* 15251:15611 */             localObject5 = localLocalFrame.getException();
/* 15252:15612 */             if (localObject5 != null) {
/* 15253:15614 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15254:      */             }
/* 15255:      */           }
/* 15256:15617 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15257:      */           {
/* 15258:15619 */             if (local_Request.isRMI()) {
/* 15259:15621 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15260:      */             }
/* 15261:15625 */             if (bool1)
/* 15262:      */             {
/* 15263:15627 */               if (localObject5 != null) {
/* 15264:15627 */                 throw ((FFSException)localObject5);
/* 15265:      */               }
/* 15266:      */             }
/* 15267:      */             else {
/* 15268:15631 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15269:      */             }
/* 15270:      */           }
/* 15271:      */         }
/* 15272:15636 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15273:      */       }
/* 15274:      */       catch (SystemException localSystemException)
/* 15275:      */       {
/* 15276:15640 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15277:      */       }
/* 15278:      */       finally
/* 15279:      */       {
/* 15280:15644 */         if (local_Request != null) {
/* 15281:15646 */           local_Request.close();
/* 15282:      */         }
/* 15283:15648 */         if (bool1) {
/* 15284:15649 */           localLocalStack.pop(localLocalFrame);
/* 15285:      */         }
/* 15286:15650 */         localLocalStack.setArgsOnLocal(bool2);
/* 15287:      */       }
/* 15288:      */     }
/* 15289:      */   }
/* 15290:      */   
/* 15291:      */   public PaymentBatchInfo addPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
/* 15292:      */     throws java.rmi.RemoteException, FFSException
/* 15293:      */   {
/* 15294:15659 */     for (int i = 1;; i++)
/* 15295:      */     {
/* 15296:15661 */       _Request local_Request = null;
/* 15297:15662 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15298:15663 */       boolean bool1 = false;
/* 15299:15664 */       LocalFrame localLocalFrame = null;
/* 15300:15665 */       boolean bool2 = false;
/* 15301:      */       try
/* 15302:      */       {
/* 15303:15668 */         local_Request = __request("addPaymentBatch");
/* 15304:15669 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15305:15670 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15306:15671 */         localLocalStack.setArgsOnLocal(bool1);
/* 15307:15673 */         if (bool1)
/* 15308:      */         {
/* 15309:15675 */           localLocalFrame = new LocalFrame(1);
/* 15310:15676 */           localLocalStack.push(localLocalFrame);
/* 15311:      */         }
/* 15312:15678 */         if (!bool1)
/* 15313:      */         {
/* 15314:15680 */           localObject4 = local_Request.getOutputStream();
/* 15315:15681 */           local_Request.write_value(paramPaymentBatchInfo, PaymentBatchInfo.class);
/* 15316:      */         }
/* 15317:      */         else
/* 15318:      */         {
/* 15319:15685 */           localObject4 = local_Request.getOutputStream();
/* 15320:15686 */           localLocalFrame.add(paramPaymentBatchInfo);
/* 15321:      */         }
/* 15322:15688 */         local_Request.invoke();
/* 15323:      */         Object localObject1;
/* 15324:15689 */         if (bool1)
/* 15325:      */         {
/* 15326:15691 */           localObject4 = null;
/* 15327:15692 */           localObject5 = localLocalFrame.getResult();
/* 15328:15693 */           if (localObject5 != null) {
/* 15329:15695 */             localObject4 = (PaymentBatchInfo)ObjectVal.clone(localObject5);
/* 15330:      */           }
/* 15331:15697 */           return localObject4;
/* 15332:      */         }
/* 15333:15699 */         Object localObject4 = local_Request.getInputStream();
/* 15334:      */         
/* 15335:15701 */         localObject5 = (PaymentBatchInfo)local_Request.read_value(PaymentBatchInfo.class);
/* 15336:15702 */         return localObject5;
/* 15337:      */       }
/* 15338:      */       catch (TRANSIENT localTRANSIENT)
/* 15339:      */       {
/* 15340:15706 */         if (i == 10) {
/* 15341:15708 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15342:      */         }
/* 15343:      */       }
/* 15344:      */       catch (UserException localUserException)
/* 15345:      */       {
/* 15346:      */         Object localObject5;
/* 15347:15713 */         if (local_Request.isRMI())
/* 15348:      */         {
/* 15349:15715 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15350:15717 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15351:      */           }
/* 15352:      */         }
/* 15353:      */         else
/* 15354:      */         {
/* 15355:15722 */           localObject5 = null;
/* 15356:15723 */           if (bool1)
/* 15357:      */           {
/* 15358:15725 */             localObject5 = localLocalFrame.getException();
/* 15359:15726 */             if (localObject5 != null) {
/* 15360:15728 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15361:      */             }
/* 15362:      */           }
/* 15363:15731 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15364:      */           {
/* 15365:15733 */             if (local_Request.isRMI()) {
/* 15366:15735 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15367:      */             }
/* 15368:15739 */             if (bool1)
/* 15369:      */             {
/* 15370:15741 */               if (localObject5 != null) {
/* 15371:15741 */                 throw ((FFSException)localObject5);
/* 15372:      */               }
/* 15373:      */             }
/* 15374:      */             else {
/* 15375:15745 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15376:      */             }
/* 15377:      */           }
/* 15378:      */         }
/* 15379:15750 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15380:      */       }
/* 15381:      */       catch (SystemException localSystemException)
/* 15382:      */       {
/* 15383:15754 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15384:      */       }
/* 15385:      */       finally
/* 15386:      */       {
/* 15387:15758 */         if (local_Request != null) {
/* 15388:15760 */           local_Request.close();
/* 15389:      */         }
/* 15390:15762 */         if (bool1) {
/* 15391:15763 */           localLocalStack.pop(localLocalFrame);
/* 15392:      */         }
/* 15393:15764 */         localLocalStack.setArgsOnLocal(bool2);
/* 15394:      */       }
/* 15395:      */     }
/* 15396:      */   }
/* 15397:      */   
/* 15398:      */   public PaymentBatchInfo modifyPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
/* 15399:      */     throws java.rmi.RemoteException, FFSException
/* 15400:      */   {
/* 15401:15773 */     for (int i = 1;; i++)
/* 15402:      */     {
/* 15403:15775 */       _Request local_Request = null;
/* 15404:15776 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15405:15777 */       boolean bool1 = false;
/* 15406:15778 */       LocalFrame localLocalFrame = null;
/* 15407:15779 */       boolean bool2 = false;
/* 15408:      */       try
/* 15409:      */       {
/* 15410:15782 */         local_Request = __request("modifyPaymentBatch");
/* 15411:15783 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15412:15784 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15413:15785 */         localLocalStack.setArgsOnLocal(bool1);
/* 15414:15787 */         if (bool1)
/* 15415:      */         {
/* 15416:15789 */           localLocalFrame = new LocalFrame(1);
/* 15417:15790 */           localLocalStack.push(localLocalFrame);
/* 15418:      */         }
/* 15419:15792 */         if (!bool1)
/* 15420:      */         {
/* 15421:15794 */           localObject4 = local_Request.getOutputStream();
/* 15422:15795 */           local_Request.write_value(paramPaymentBatchInfo, PaymentBatchInfo.class);
/* 15423:      */         }
/* 15424:      */         else
/* 15425:      */         {
/* 15426:15799 */           localObject4 = local_Request.getOutputStream();
/* 15427:15800 */           localLocalFrame.add(paramPaymentBatchInfo);
/* 15428:      */         }
/* 15429:15802 */         local_Request.invoke();
/* 15430:      */         Object localObject1;
/* 15431:15803 */         if (bool1)
/* 15432:      */         {
/* 15433:15805 */           localObject4 = null;
/* 15434:15806 */           localObject5 = localLocalFrame.getResult();
/* 15435:15807 */           if (localObject5 != null) {
/* 15436:15809 */             localObject4 = (PaymentBatchInfo)ObjectVal.clone(localObject5);
/* 15437:      */           }
/* 15438:15811 */           return localObject4;
/* 15439:      */         }
/* 15440:15813 */         Object localObject4 = local_Request.getInputStream();
/* 15441:      */         
/* 15442:15815 */         localObject5 = (PaymentBatchInfo)local_Request.read_value(PaymentBatchInfo.class);
/* 15443:15816 */         return localObject5;
/* 15444:      */       }
/* 15445:      */       catch (TRANSIENT localTRANSIENT)
/* 15446:      */       {
/* 15447:15820 */         if (i == 10) {
/* 15448:15822 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15449:      */         }
/* 15450:      */       }
/* 15451:      */       catch (UserException localUserException)
/* 15452:      */       {
/* 15453:      */         Object localObject5;
/* 15454:15827 */         if (local_Request.isRMI())
/* 15455:      */         {
/* 15456:15829 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15457:15831 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15458:      */           }
/* 15459:      */         }
/* 15460:      */         else
/* 15461:      */         {
/* 15462:15836 */           localObject5 = null;
/* 15463:15837 */           if (bool1)
/* 15464:      */           {
/* 15465:15839 */             localObject5 = localLocalFrame.getException();
/* 15466:15840 */             if (localObject5 != null) {
/* 15467:15842 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15468:      */             }
/* 15469:      */           }
/* 15470:15845 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15471:      */           {
/* 15472:15847 */             if (local_Request.isRMI()) {
/* 15473:15849 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15474:      */             }
/* 15475:15853 */             if (bool1)
/* 15476:      */             {
/* 15477:15855 */               if (localObject5 != null) {
/* 15478:15855 */                 throw ((FFSException)localObject5);
/* 15479:      */               }
/* 15480:      */             }
/* 15481:      */             else {
/* 15482:15859 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15483:      */             }
/* 15484:      */           }
/* 15485:      */         }
/* 15486:15864 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15487:      */       }
/* 15488:      */       catch (SystemException localSystemException)
/* 15489:      */       {
/* 15490:15868 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15491:      */       }
/* 15492:      */       finally
/* 15493:      */       {
/* 15494:15872 */         if (local_Request != null) {
/* 15495:15874 */           local_Request.close();
/* 15496:      */         }
/* 15497:15876 */         if (bool1) {
/* 15498:15877 */           localLocalStack.pop(localLocalFrame);
/* 15499:      */         }
/* 15500:15878 */         localLocalStack.setArgsOnLocal(bool2);
/* 15501:      */       }
/* 15502:      */     }
/* 15503:      */   }
/* 15504:      */   
/* 15505:      */   public PaymentBatchInfo deletePaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
/* 15506:      */     throws java.rmi.RemoteException, FFSException
/* 15507:      */   {
/* 15508:15887 */     for (int i = 1;; i++)
/* 15509:      */     {
/* 15510:15889 */       _Request local_Request = null;
/* 15511:15890 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15512:15891 */       boolean bool1 = false;
/* 15513:15892 */       LocalFrame localLocalFrame = null;
/* 15514:15893 */       boolean bool2 = false;
/* 15515:      */       try
/* 15516:      */       {
/* 15517:15896 */         local_Request = __request("deletePaymentBatch");
/* 15518:15897 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15519:15898 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15520:15899 */         localLocalStack.setArgsOnLocal(bool1);
/* 15521:15901 */         if (bool1)
/* 15522:      */         {
/* 15523:15903 */           localLocalFrame = new LocalFrame(1);
/* 15524:15904 */           localLocalStack.push(localLocalFrame);
/* 15525:      */         }
/* 15526:15906 */         if (!bool1)
/* 15527:      */         {
/* 15528:15908 */           localObject4 = local_Request.getOutputStream();
/* 15529:15909 */           local_Request.write_value(paramPaymentBatchInfo, PaymentBatchInfo.class);
/* 15530:      */         }
/* 15531:      */         else
/* 15532:      */         {
/* 15533:15913 */           localObject4 = local_Request.getOutputStream();
/* 15534:15914 */           localLocalFrame.add(paramPaymentBatchInfo);
/* 15535:      */         }
/* 15536:15916 */         local_Request.invoke();
/* 15537:      */         Object localObject1;
/* 15538:15917 */         if (bool1)
/* 15539:      */         {
/* 15540:15919 */           localObject4 = null;
/* 15541:15920 */           localObject5 = localLocalFrame.getResult();
/* 15542:15921 */           if (localObject5 != null) {
/* 15543:15923 */             localObject4 = (PaymentBatchInfo)ObjectVal.clone(localObject5);
/* 15544:      */           }
/* 15545:15925 */           return localObject4;
/* 15546:      */         }
/* 15547:15927 */         Object localObject4 = local_Request.getInputStream();
/* 15548:      */         
/* 15549:15929 */         localObject5 = (PaymentBatchInfo)local_Request.read_value(PaymentBatchInfo.class);
/* 15550:15930 */         return localObject5;
/* 15551:      */       }
/* 15552:      */       catch (TRANSIENT localTRANSIENT)
/* 15553:      */       {
/* 15554:15934 */         if (i == 10) {
/* 15555:15936 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15556:      */         }
/* 15557:      */       }
/* 15558:      */       catch (UserException localUserException)
/* 15559:      */       {
/* 15560:      */         Object localObject5;
/* 15561:15941 */         if (local_Request.isRMI())
/* 15562:      */         {
/* 15563:15943 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15564:15945 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15565:      */           }
/* 15566:      */         }
/* 15567:      */         else
/* 15568:      */         {
/* 15569:15950 */           localObject5 = null;
/* 15570:15951 */           if (bool1)
/* 15571:      */           {
/* 15572:15953 */             localObject5 = localLocalFrame.getException();
/* 15573:15954 */             if (localObject5 != null) {
/* 15574:15956 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15575:      */             }
/* 15576:      */           }
/* 15577:15959 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15578:      */           {
/* 15579:15961 */             if (local_Request.isRMI()) {
/* 15580:15963 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15581:      */             }
/* 15582:15967 */             if (bool1)
/* 15583:      */             {
/* 15584:15969 */               if (localObject5 != null) {
/* 15585:15969 */                 throw ((FFSException)localObject5);
/* 15586:      */               }
/* 15587:      */             }
/* 15588:      */             else {
/* 15589:15973 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15590:      */             }
/* 15591:      */           }
/* 15592:      */         }
/* 15593:15978 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15594:      */       }
/* 15595:      */       catch (SystemException localSystemException)
/* 15596:      */       {
/* 15597:15982 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15598:      */       }
/* 15599:      */       finally
/* 15600:      */       {
/* 15601:15986 */         if (local_Request != null) {
/* 15602:15988 */           local_Request.close();
/* 15603:      */         }
/* 15604:15990 */         if (bool1) {
/* 15605:15991 */           localLocalStack.pop(localLocalFrame);
/* 15606:      */         }
/* 15607:15992 */         localLocalStack.setArgsOnLocal(bool2);
/* 15608:      */       }
/* 15609:      */     }
/* 15610:      */   }
/* 15611:      */   
/* 15612:      */   public PaymentBatchInfo getPaymentBatchById(String paramString)
/* 15613:      */     throws java.rmi.RemoteException, FFSException
/* 15614:      */   {
/* 15615:16001 */     for (int i = 1;; i++)
/* 15616:      */     {
/* 15617:16003 */       _Request local_Request = null;
/* 15618:16004 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15619:16005 */       boolean bool1 = false;
/* 15620:16006 */       LocalFrame localLocalFrame = null;
/* 15621:16007 */       boolean bool2 = false;
/* 15622:      */       try
/* 15623:      */       {
/* 15624:16010 */         local_Request = __request("getPaymentBatchById");
/* 15625:16011 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15626:16012 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15627:16013 */         localLocalStack.setArgsOnLocal(bool1);
/* 15628:16015 */         if (bool1)
/* 15629:      */         {
/* 15630:16017 */           localLocalFrame = new LocalFrame(1);
/* 15631:16018 */           localLocalStack.push(localLocalFrame);
/* 15632:      */         }
/* 15633:16020 */         if (!bool1)
/* 15634:      */         {
/* 15635:16022 */           localObject4 = local_Request.getOutputStream();
/* 15636:16023 */           local_Request.write_string(paramString);
/* 15637:      */         }
/* 15638:      */         else
/* 15639:      */         {
/* 15640:16027 */           localObject4 = local_Request.getOutputStream();
/* 15641:16028 */           localLocalFrame.add(paramString);
/* 15642:      */         }
/* 15643:16030 */         local_Request.invoke();
/* 15644:      */         Object localObject1;
/* 15645:16031 */         if (bool1)
/* 15646:      */         {
/* 15647:16033 */           localObject4 = null;
/* 15648:16034 */           localObject5 = localLocalFrame.getResult();
/* 15649:16035 */           if (localObject5 != null) {
/* 15650:16037 */             localObject4 = (PaymentBatchInfo)ObjectVal.clone(localObject5);
/* 15651:      */           }
/* 15652:16039 */           return localObject4;
/* 15653:      */         }
/* 15654:16041 */         Object localObject4 = local_Request.getInputStream();
/* 15655:      */         
/* 15656:16043 */         localObject5 = (PaymentBatchInfo)local_Request.read_value(PaymentBatchInfo.class);
/* 15657:16044 */         return localObject5;
/* 15658:      */       }
/* 15659:      */       catch (TRANSIENT localTRANSIENT)
/* 15660:      */       {
/* 15661:16048 */         if (i == 10) {
/* 15662:16050 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15663:      */         }
/* 15664:      */       }
/* 15665:      */       catch (UserException localUserException)
/* 15666:      */       {
/* 15667:      */         Object localObject5;
/* 15668:16055 */         if (local_Request.isRMI())
/* 15669:      */         {
/* 15670:16057 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15671:16059 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15672:      */           }
/* 15673:      */         }
/* 15674:      */         else
/* 15675:      */         {
/* 15676:16064 */           localObject5 = null;
/* 15677:16065 */           if (bool1)
/* 15678:      */           {
/* 15679:16067 */             localObject5 = localLocalFrame.getException();
/* 15680:16068 */             if (localObject5 != null) {
/* 15681:16070 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15682:      */             }
/* 15683:      */           }
/* 15684:16073 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15685:      */           {
/* 15686:16075 */             if (local_Request.isRMI()) {
/* 15687:16077 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15688:      */             }
/* 15689:16081 */             if (bool1)
/* 15690:      */             {
/* 15691:16083 */               if (localObject5 != null) {
/* 15692:16083 */                 throw ((FFSException)localObject5);
/* 15693:      */               }
/* 15694:      */             }
/* 15695:      */             else {
/* 15696:16087 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15697:      */             }
/* 15698:      */           }
/* 15699:      */         }
/* 15700:16092 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15701:      */       }
/* 15702:      */       catch (SystemException localSystemException)
/* 15703:      */       {
/* 15704:16096 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15705:      */       }
/* 15706:      */       finally
/* 15707:      */       {
/* 15708:16100 */         if (local_Request != null) {
/* 15709:16102 */           local_Request.close();
/* 15710:      */         }
/* 15711:16104 */         if (bool1) {
/* 15712:16105 */           localLocalStack.pop(localLocalFrame);
/* 15713:      */         }
/* 15714:16106 */         localLocalStack.setArgsOnLocal(bool2);
/* 15715:      */       }
/* 15716:      */     }
/* 15717:      */   }
/* 15718:      */   
/* 15719:      */   public LastPaymentInfo getLastPayments(LastPaymentInfo paramLastPaymentInfo)
/* 15720:      */     throws java.rmi.RemoteException, FFSException
/* 15721:      */   {
/* 15722:16115 */     for (int i = 1;; i++)
/* 15723:      */     {
/* 15724:16117 */       _Request local_Request = null;
/* 15725:16118 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15726:16119 */       boolean bool1 = false;
/* 15727:16120 */       LocalFrame localLocalFrame = null;
/* 15728:16121 */       boolean bool2 = false;
/* 15729:      */       try
/* 15730:      */       {
/* 15731:16124 */         local_Request = __request("getLastPayments");
/* 15732:16125 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15733:16126 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15734:16127 */         localLocalStack.setArgsOnLocal(bool1);
/* 15735:16129 */         if (bool1)
/* 15736:      */         {
/* 15737:16131 */           localLocalFrame = new LocalFrame(1);
/* 15738:16132 */           localLocalStack.push(localLocalFrame);
/* 15739:      */         }
/* 15740:16134 */         if (!bool1)
/* 15741:      */         {
/* 15742:16136 */           localObject4 = local_Request.getOutputStream();
/* 15743:16137 */           local_Request.write_value(paramLastPaymentInfo, LastPaymentInfo.class);
/* 15744:      */         }
/* 15745:      */         else
/* 15746:      */         {
/* 15747:16141 */           localObject4 = local_Request.getOutputStream();
/* 15748:16142 */           localLocalFrame.add(paramLastPaymentInfo);
/* 15749:      */         }
/* 15750:16144 */         local_Request.invoke();
/* 15751:      */         Object localObject1;
/* 15752:16145 */         if (bool1)
/* 15753:      */         {
/* 15754:16147 */           localObject4 = null;
/* 15755:16148 */           localObject5 = localLocalFrame.getResult();
/* 15756:16149 */           if (localObject5 != null) {
/* 15757:16151 */             localObject4 = (LastPaymentInfo)ObjectVal.clone(localObject5);
/* 15758:      */           }
/* 15759:16153 */           return localObject4;
/* 15760:      */         }
/* 15761:16155 */         Object localObject4 = local_Request.getInputStream();
/* 15762:      */         
/* 15763:16157 */         localObject5 = (LastPaymentInfo)local_Request.read_value(LastPaymentInfo.class);
/* 15764:16158 */         return localObject5;
/* 15765:      */       }
/* 15766:      */       catch (TRANSIENT localTRANSIENT)
/* 15767:      */       {
/* 15768:16162 */         if (i == 10) {
/* 15769:16164 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15770:      */         }
/* 15771:      */       }
/* 15772:      */       catch (UserException localUserException)
/* 15773:      */       {
/* 15774:      */         Object localObject5;
/* 15775:16169 */         if (local_Request.isRMI())
/* 15776:      */         {
/* 15777:16171 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15778:16173 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15779:      */           }
/* 15780:      */         }
/* 15781:      */         else
/* 15782:      */         {
/* 15783:16178 */           localObject5 = null;
/* 15784:16179 */           if (bool1)
/* 15785:      */           {
/* 15786:16181 */             localObject5 = localLocalFrame.getException();
/* 15787:16182 */             if (localObject5 != null) {
/* 15788:16184 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15789:      */             }
/* 15790:      */           }
/* 15791:16187 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15792:      */           {
/* 15793:16189 */             if (local_Request.isRMI()) {
/* 15794:16191 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15795:      */             }
/* 15796:16195 */             if (bool1)
/* 15797:      */             {
/* 15798:16197 */               if (localObject5 != null) {
/* 15799:16197 */                 throw ((FFSException)localObject5);
/* 15800:      */               }
/* 15801:      */             }
/* 15802:      */             else {
/* 15803:16201 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15804:      */             }
/* 15805:      */           }
/* 15806:      */         }
/* 15807:16206 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15808:      */       }
/* 15809:      */       catch (SystemException localSystemException)
/* 15810:      */       {
/* 15811:16210 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15812:      */       }
/* 15813:      */       finally
/* 15814:      */       {
/* 15815:16214 */         if (local_Request != null) {
/* 15816:16216 */           local_Request.close();
/* 15817:      */         }
/* 15818:16218 */         if (bool1) {
/* 15819:16219 */           localLocalStack.pop(localLocalFrame);
/* 15820:      */         }
/* 15821:16220 */         localLocalStack.setArgsOnLocal(bool2);
/* 15822:      */       }
/* 15823:      */     }
/* 15824:      */   }
/* 15825:      */   
/* 15826:      */   public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
/* 15827:      */     throws java.rmi.RemoteException, FFSException
/* 15828:      */   {
/* 15829:16230 */     for (int i = 1;; i++)
/* 15830:      */     {
/* 15831:16232 */       _Request local_Request = null;
/* 15832:16233 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15833:16234 */       boolean bool1 = false;
/* 15834:16235 */       LocalFrame localLocalFrame = null;
/* 15835:16236 */       boolean bool2 = false;
/* 15836:      */       try
/* 15837:      */       {
/* 15838:16239 */         local_Request = __request("getBankingDaysInRange");
/* 15839:16240 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15840:16241 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15841:16242 */         localLocalStack.setArgsOnLocal(bool1);
/* 15842:16244 */         if (bool1)
/* 15843:      */         {
/* 15844:16246 */           localLocalFrame = new LocalFrame(2);
/* 15845:16247 */           localLocalStack.push(localLocalFrame);
/* 15846:      */         }
/* 15847:16249 */         if (!bool1)
/* 15848:      */         {
/* 15849:16251 */           localObject4 = local_Request.getOutputStream();
/* 15850:16252 */           local_Request.write_value(paramBankingDays, BankingDays.class);
/* 15851:16253 */           local_Request.write_value(paramHashMap, HashMap.class);
/* 15852:      */         }
/* 15853:      */         else
/* 15854:      */         {
/* 15855:16257 */           localObject4 = local_Request.getOutputStream();
/* 15856:16258 */           localLocalFrame.add(paramBankingDays);
/* 15857:16259 */           localLocalFrame.add(paramHashMap);
/* 15858:      */         }
/* 15859:16261 */         local_Request.invoke();
/* 15860:      */         Object localObject1;
/* 15861:16262 */         if (bool1)
/* 15862:      */         {
/* 15863:16264 */           localObject4 = null;
/* 15864:16265 */           localObject5 = localLocalFrame.getResult();
/* 15865:16266 */           if (localObject5 != null) {
/* 15866:16268 */             localObject4 = (BankingDays)ObjectVal.clone(localObject5);
/* 15867:      */           }
/* 15868:16270 */           return localObject4;
/* 15869:      */         }
/* 15870:16272 */         Object localObject4 = local_Request.getInputStream();
/* 15871:      */         
/* 15872:16274 */         localObject5 = (BankingDays)local_Request.read_value(BankingDays.class);
/* 15873:16275 */         return localObject5;
/* 15874:      */       }
/* 15875:      */       catch (TRANSIENT localTRANSIENT)
/* 15876:      */       {
/* 15877:16279 */         if (i == 10) {
/* 15878:16281 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15879:      */         }
/* 15880:      */       }
/* 15881:      */       catch (UserException localUserException)
/* 15882:      */       {
/* 15883:      */         Object localObject5;
/* 15884:16286 */         if (local_Request.isRMI())
/* 15885:      */         {
/* 15886:16288 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15887:16290 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15888:      */           }
/* 15889:      */         }
/* 15890:      */         else
/* 15891:      */         {
/* 15892:16295 */           localObject5 = null;
/* 15893:16296 */           if (bool1)
/* 15894:      */           {
/* 15895:16298 */             localObject5 = localLocalFrame.getException();
/* 15896:16299 */             if (localObject5 != null) {
/* 15897:16301 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 15898:      */             }
/* 15899:      */           }
/* 15900:16304 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 15901:      */           {
/* 15902:16306 */             if (local_Request.isRMI()) {
/* 15903:16308 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15904:      */             }
/* 15905:16312 */             if (bool1)
/* 15906:      */             {
/* 15907:16314 */               if (localObject5 != null) {
/* 15908:16314 */                 throw ((FFSException)localObject5);
/* 15909:      */               }
/* 15910:      */             }
/* 15911:      */             else {
/* 15912:16318 */               throw FFSExceptionHelper.read(localUserException.input);
/* 15913:      */             }
/* 15914:      */           }
/* 15915:      */         }
/* 15916:16323 */         throw new java.rmi.RemoteException(localUserException.type);
/* 15917:      */       }
/* 15918:      */       catch (SystemException localSystemException)
/* 15919:      */       {
/* 15920:16327 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 15921:      */       }
/* 15922:      */       finally
/* 15923:      */       {
/* 15924:16331 */         if (local_Request != null) {
/* 15925:16333 */           local_Request.close();
/* 15926:      */         }
/* 15927:16335 */         if (bool1) {
/* 15928:16336 */           localLocalStack.pop(localLocalFrame);
/* 15929:      */         }
/* 15930:16337 */         localLocalStack.setArgsOnLocal(bool2);
/* 15931:      */       }
/* 15932:      */     }
/* 15933:      */   }
/* 15934:      */   
/* 15935:      */   public CustomerPayeeInfo addCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
/* 15936:      */     throws java.rmi.RemoteException, FFSException
/* 15937:      */   {
/* 15938:16346 */     for (int i = 1;; i++)
/* 15939:      */     {
/* 15940:16348 */       _Request local_Request = null;
/* 15941:16349 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 15942:16350 */       boolean bool1 = false;
/* 15943:16351 */       LocalFrame localLocalFrame = null;
/* 15944:16352 */       boolean bool2 = false;
/* 15945:      */       try
/* 15946:      */       {
/* 15947:16355 */         local_Request = __request("addCustomerPayee");
/* 15948:16356 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 15949:16357 */         bool2 = localLocalStack.isArgsOnLocal();
/* 15950:16358 */         localLocalStack.setArgsOnLocal(bool1);
/* 15951:16360 */         if (bool1)
/* 15952:      */         {
/* 15953:16362 */           localLocalFrame = new LocalFrame(1);
/* 15954:16363 */           localLocalStack.push(localLocalFrame);
/* 15955:      */         }
/* 15956:16365 */         if (!bool1)
/* 15957:      */         {
/* 15958:16367 */           localObject4 = local_Request.getOutputStream();
/* 15959:16368 */           local_Request.write_value(paramCustomerPayeeInfo, CustomerPayeeInfo.class);
/* 15960:      */         }
/* 15961:      */         else
/* 15962:      */         {
/* 15963:16372 */           localObject4 = local_Request.getOutputStream();
/* 15964:16373 */           localLocalFrame.add(paramCustomerPayeeInfo);
/* 15965:      */         }
/* 15966:16375 */         local_Request.invoke();
/* 15967:      */         Object localObject1;
/* 15968:16376 */         if (bool1)
/* 15969:      */         {
/* 15970:16378 */           localObject4 = null;
/* 15971:16379 */           localObject5 = localLocalFrame.getResult();
/* 15972:16380 */           if (localObject5 != null) {
/* 15973:16382 */             localObject4 = (CustomerPayeeInfo)ObjectVal.clone(localObject5);
/* 15974:      */           }
/* 15975:16384 */           return localObject4;
/* 15976:      */         }
/* 15977:16386 */         Object localObject4 = local_Request.getInputStream();
/* 15978:      */         
/* 15979:16388 */         localObject5 = (CustomerPayeeInfo)local_Request.read_value(CustomerPayeeInfo.class);
/* 15980:16389 */         return localObject5;
/* 15981:      */       }
/* 15982:      */       catch (TRANSIENT localTRANSIENT)
/* 15983:      */       {
/* 15984:16393 */         if (i == 10) {
/* 15985:16395 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 15986:      */         }
/* 15987:      */       }
/* 15988:      */       catch (UserException localUserException)
/* 15989:      */       {
/* 15990:      */         Object localObject5;
/* 15991:16400 */         if (local_Request.isRMI())
/* 15992:      */         {
/* 15993:16402 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 15994:16404 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 15995:      */           }
/* 15996:      */         }
/* 15997:      */         else
/* 15998:      */         {
/* 15999:16409 */           localObject5 = null;
/* 16000:16410 */           if (bool1)
/* 16001:      */           {
/* 16002:16412 */             localObject5 = localLocalFrame.getException();
/* 16003:16413 */             if (localObject5 != null) {
/* 16004:16415 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 16005:      */             }
/* 16006:      */           }
/* 16007:16418 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 16008:      */           {
/* 16009:16420 */             if (local_Request.isRMI()) {
/* 16010:16422 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16011:      */             }
/* 16012:16426 */             if (bool1)
/* 16013:      */             {
/* 16014:16428 */               if (localObject5 != null) {
/* 16015:16428 */                 throw ((FFSException)localObject5);
/* 16016:      */               }
/* 16017:      */             }
/* 16018:      */             else {
/* 16019:16432 */               throw FFSExceptionHelper.read(localUserException.input);
/* 16020:      */             }
/* 16021:      */           }
/* 16022:      */         }
/* 16023:16437 */         throw new java.rmi.RemoteException(localUserException.type);
/* 16024:      */       }
/* 16025:      */       catch (SystemException localSystemException)
/* 16026:      */       {
/* 16027:16441 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 16028:      */       }
/* 16029:      */       finally
/* 16030:      */       {
/* 16031:16445 */         if (local_Request != null) {
/* 16032:16447 */           local_Request.close();
/* 16033:      */         }
/* 16034:16449 */         if (bool1) {
/* 16035:16450 */           localLocalStack.pop(localLocalFrame);
/* 16036:      */         }
/* 16037:16451 */         localLocalStack.setArgsOnLocal(bool2);
/* 16038:      */       }
/* 16039:      */     }
/* 16040:      */   }
/* 16041:      */   
/* 16042:      */   public CustomerPayeeInfo deleteCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
/* 16043:      */     throws java.rmi.RemoteException, FFSException
/* 16044:      */   {
/* 16045:16460 */     for (int i = 1;; i++)
/* 16046:      */     {
/* 16047:16462 */       _Request local_Request = null;
/* 16048:16463 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 16049:16464 */       boolean bool1 = false;
/* 16050:16465 */       LocalFrame localLocalFrame = null;
/* 16051:16466 */       boolean bool2 = false;
/* 16052:      */       try
/* 16053:      */       {
/* 16054:16469 */         local_Request = __request("deleteCustomerPayee");
/* 16055:16470 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 16056:16471 */         bool2 = localLocalStack.isArgsOnLocal();
/* 16057:16472 */         localLocalStack.setArgsOnLocal(bool1);
/* 16058:16474 */         if (bool1)
/* 16059:      */         {
/* 16060:16476 */           localLocalFrame = new LocalFrame(1);
/* 16061:16477 */           localLocalStack.push(localLocalFrame);
/* 16062:      */         }
/* 16063:16479 */         if (!bool1)
/* 16064:      */         {
/* 16065:16481 */           localObject4 = local_Request.getOutputStream();
/* 16066:16482 */           local_Request.write_value(paramCustomerPayeeInfo, CustomerPayeeInfo.class);
/* 16067:      */         }
/* 16068:      */         else
/* 16069:      */         {
/* 16070:16486 */           localObject4 = local_Request.getOutputStream();
/* 16071:16487 */           localLocalFrame.add(paramCustomerPayeeInfo);
/* 16072:      */         }
/* 16073:16489 */         local_Request.invoke();
/* 16074:      */         Object localObject1;
/* 16075:16490 */         if (bool1)
/* 16076:      */         {
/* 16077:16492 */           localObject4 = null;
/* 16078:16493 */           localObject5 = localLocalFrame.getResult();
/* 16079:16494 */           if (localObject5 != null) {
/* 16080:16496 */             localObject4 = (CustomerPayeeInfo)ObjectVal.clone(localObject5);
/* 16081:      */           }
/* 16082:16498 */           return localObject4;
/* 16083:      */         }
/* 16084:16500 */         Object localObject4 = local_Request.getInputStream();
/* 16085:      */         
/* 16086:16502 */         localObject5 = (CustomerPayeeInfo)local_Request.read_value(CustomerPayeeInfo.class);
/* 16087:16503 */         return localObject5;
/* 16088:      */       }
/* 16089:      */       catch (TRANSIENT localTRANSIENT)
/* 16090:      */       {
/* 16091:16507 */         if (i == 10) {
/* 16092:16509 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 16093:      */         }
/* 16094:      */       }
/* 16095:      */       catch (UserException localUserException)
/* 16096:      */       {
/* 16097:      */         Object localObject5;
/* 16098:16514 */         if (local_Request.isRMI())
/* 16099:      */         {
/* 16100:16516 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 16101:16518 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16102:      */           }
/* 16103:      */         }
/* 16104:      */         else
/* 16105:      */         {
/* 16106:16523 */           localObject5 = null;
/* 16107:16524 */           if (bool1)
/* 16108:      */           {
/* 16109:16526 */             localObject5 = localLocalFrame.getException();
/* 16110:16527 */             if (localObject5 != null) {
/* 16111:16529 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 16112:      */             }
/* 16113:      */           }
/* 16114:16532 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 16115:      */           {
/* 16116:16534 */             if (local_Request.isRMI()) {
/* 16117:16536 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16118:      */             }
/* 16119:16540 */             if (bool1)
/* 16120:      */             {
/* 16121:16542 */               if (localObject5 != null) {
/* 16122:16542 */                 throw ((FFSException)localObject5);
/* 16123:      */               }
/* 16124:      */             }
/* 16125:      */             else {
/* 16126:16546 */               throw FFSExceptionHelper.read(localUserException.input);
/* 16127:      */             }
/* 16128:      */           }
/* 16129:      */         }
/* 16130:16551 */         throw new java.rmi.RemoteException(localUserException.type);
/* 16131:      */       }
/* 16132:      */       catch (SystemException localSystemException)
/* 16133:      */       {
/* 16134:16555 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 16135:      */       }
/* 16136:      */       finally
/* 16137:      */       {
/* 16138:16559 */         if (local_Request != null) {
/* 16139:16561 */           local_Request.close();
/* 16140:      */         }
/* 16141:16563 */         if (bool1) {
/* 16142:16564 */           localLocalStack.pop(localLocalFrame);
/* 16143:      */         }
/* 16144:16565 */         localLocalStack.setArgsOnLocal(bool2);
/* 16145:      */       }
/* 16146:      */     }
/* 16147:      */   }
/* 16148:      */   
/* 16149:      */   public CustomerPayeeInfo updateCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
/* 16150:      */     throws java.rmi.RemoteException, FFSException
/* 16151:      */   {
/* 16152:16574 */     for (int i = 1;; i++)
/* 16153:      */     {
/* 16154:16576 */       _Request local_Request = null;
/* 16155:16577 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 16156:16578 */       boolean bool1 = false;
/* 16157:16579 */       LocalFrame localLocalFrame = null;
/* 16158:16580 */       boolean bool2 = false;
/* 16159:      */       try
/* 16160:      */       {
/* 16161:16583 */         local_Request = __request("updateCustomerPayee");
/* 16162:16584 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 16163:16585 */         bool2 = localLocalStack.isArgsOnLocal();
/* 16164:16586 */         localLocalStack.setArgsOnLocal(bool1);
/* 16165:16588 */         if (bool1)
/* 16166:      */         {
/* 16167:16590 */           localLocalFrame = new LocalFrame(1);
/* 16168:16591 */           localLocalStack.push(localLocalFrame);
/* 16169:      */         }
/* 16170:16593 */         if (!bool1)
/* 16171:      */         {
/* 16172:16595 */           localObject4 = local_Request.getOutputStream();
/* 16173:16596 */           local_Request.write_value(paramCustomerPayeeInfo, CustomerPayeeInfo.class);
/* 16174:      */         }
/* 16175:      */         else
/* 16176:      */         {
/* 16177:16600 */           localObject4 = local_Request.getOutputStream();
/* 16178:16601 */           localLocalFrame.add(paramCustomerPayeeInfo);
/* 16179:      */         }
/* 16180:16603 */         local_Request.invoke();
/* 16181:      */         Object localObject1;
/* 16182:16604 */         if (bool1)
/* 16183:      */         {
/* 16184:16606 */           localObject4 = null;
/* 16185:16607 */           localObject5 = localLocalFrame.getResult();
/* 16186:16608 */           if (localObject5 != null) {
/* 16187:16610 */             localObject4 = (CustomerPayeeInfo)ObjectVal.clone(localObject5);
/* 16188:      */           }
/* 16189:16612 */           return localObject4;
/* 16190:      */         }
/* 16191:16614 */         Object localObject4 = local_Request.getInputStream();
/* 16192:      */         
/* 16193:16616 */         localObject5 = (CustomerPayeeInfo)local_Request.read_value(CustomerPayeeInfo.class);
/* 16194:16617 */         return localObject5;
/* 16195:      */       }
/* 16196:      */       catch (TRANSIENT localTRANSIENT)
/* 16197:      */       {
/* 16198:16621 */         if (i == 10) {
/* 16199:16623 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 16200:      */         }
/* 16201:      */       }
/* 16202:      */       catch (UserException localUserException)
/* 16203:      */       {
/* 16204:      */         Object localObject5;
/* 16205:16628 */         if (local_Request.isRMI())
/* 16206:      */         {
/* 16207:16630 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 16208:16632 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16209:      */           }
/* 16210:      */         }
/* 16211:      */         else
/* 16212:      */         {
/* 16213:16637 */           localObject5 = null;
/* 16214:16638 */           if (bool1)
/* 16215:      */           {
/* 16216:16640 */             localObject5 = localLocalFrame.getException();
/* 16217:16641 */             if (localObject5 != null) {
/* 16218:16643 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 16219:      */             }
/* 16220:      */           }
/* 16221:16646 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 16222:      */           {
/* 16223:16648 */             if (local_Request.isRMI()) {
/* 16224:16650 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16225:      */             }
/* 16226:16654 */             if (bool1)
/* 16227:      */             {
/* 16228:16656 */               if (localObject5 != null) {
/* 16229:16656 */                 throw ((FFSException)localObject5);
/* 16230:      */               }
/* 16231:      */             }
/* 16232:      */             else {
/* 16233:16660 */               throw FFSExceptionHelper.read(localUserException.input);
/* 16234:      */             }
/* 16235:      */           }
/* 16236:      */         }
/* 16237:16665 */         throw new java.rmi.RemoteException(localUserException.type);
/* 16238:      */       }
/* 16239:      */       catch (SystemException localSystemException)
/* 16240:      */       {
/* 16241:16669 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 16242:      */       }
/* 16243:      */       finally
/* 16244:      */       {
/* 16245:16673 */         if (local_Request != null) {
/* 16246:16675 */           local_Request.close();
/* 16247:      */         }
/* 16248:16677 */         if (bool1) {
/* 16249:16678 */           localLocalStack.pop(localLocalFrame);
/* 16250:      */         }
/* 16251:16679 */         localLocalStack.setArgsOnLocal(bool2);
/* 16252:      */       }
/* 16253:      */     }
/* 16254:      */   }
/* 16255:      */   
/* 16256:      */   public CustomerPayeeInfo[] getCustomerPayees(CustomerPayeeInfo paramCustomerPayeeInfo)
/* 16257:      */     throws java.rmi.RemoteException, FFSException
/* 16258:      */   {
/* 16259:16688 */     for (int i = 1;; i++)
/* 16260:      */     {
/* 16261:16690 */       _Request local_Request = null;
/* 16262:16691 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 16263:16692 */       boolean bool1 = false;
/* 16264:16693 */       LocalFrame localLocalFrame = null;
/* 16265:16694 */       boolean bool2 = false;
/* 16266:      */       try
/* 16267:      */       {
/* 16268:16697 */         local_Request = __request("getCustomerPayees");
/* 16269:16698 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 16270:16699 */         bool2 = localLocalStack.isArgsOnLocal();
/* 16271:16700 */         localLocalStack.setArgsOnLocal(bool1);
/* 16272:16702 */         if (bool1)
/* 16273:      */         {
/* 16274:16704 */           localLocalFrame = new LocalFrame(1);
/* 16275:16705 */           localLocalStack.push(localLocalFrame);
/* 16276:      */         }
/* 16277:16707 */         if (!bool1)
/* 16278:      */         {
/* 16279:16709 */           localObject4 = local_Request.getOutputStream();
/* 16280:16710 */           local_Request.write_value(paramCustomerPayeeInfo, CustomerPayeeInfo.class);
/* 16281:      */         }
/* 16282:      */         else
/* 16283:      */         {
/* 16284:16714 */           localObject4 = local_Request.getOutputStream();
/* 16285:16715 */           localLocalFrame.add(paramCustomerPayeeInfo);
/* 16286:      */         }
/* 16287:16717 */         local_Request.invoke();
/* 16288:      */         Object localObject1;
/* 16289:16718 */         if (bool1)
/* 16290:      */         {
/* 16291:16720 */           localObject4 = null;
/* 16292:16721 */           localObject5 = localLocalFrame.getResult();
/* 16293:16722 */           if (localObject5 != null) {
/* 16294:16724 */             localObject4 = (CustomerPayeeInfo[])ObjectVal.clone(localObject5);
/* 16295:      */           }
/* 16296:16726 */           return localObject4;
/* 16297:      */         }
/* 16298:16728 */         Object localObject4 = local_Request.getInputStream();
/* 16299:16730 */         if (local_Request.isRMI()) {
/* 16300:16730 */           localObject5 = (CustomerPayeeInfo[])local_Request.read_value(new CustomerPayeeInfo[0].getClass());
/* 16301:      */         } else {
/* 16302:16730 */           localObject5 = CustomerPayeeInfoSeqHelper.read((InputStream)localObject4);
/* 16303:      */         }
/* 16304:16731 */         return localObject5;
/* 16305:      */       }
/* 16306:      */       catch (TRANSIENT localTRANSIENT)
/* 16307:      */       {
/* 16308:16735 */         if (i == 10) {
/* 16309:16737 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 16310:      */         }
/* 16311:      */       }
/* 16312:      */       catch (UserException localUserException)
/* 16313:      */       {
/* 16314:      */         Object localObject5;
/* 16315:16742 */         if (local_Request.isRMI())
/* 16316:      */         {
/* 16317:16744 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 16318:16746 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16319:      */           }
/* 16320:      */         }
/* 16321:      */         else
/* 16322:      */         {
/* 16323:16751 */           localObject5 = null;
/* 16324:16752 */           if (bool1)
/* 16325:      */           {
/* 16326:16754 */             localObject5 = localLocalFrame.getException();
/* 16327:16755 */             if (localObject5 != null) {
/* 16328:16757 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 16329:      */             }
/* 16330:      */           }
/* 16331:16760 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 16332:      */           {
/* 16333:16762 */             if (local_Request.isRMI()) {
/* 16334:16764 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16335:      */             }
/* 16336:16768 */             if (bool1)
/* 16337:      */             {
/* 16338:16770 */               if (localObject5 != null) {
/* 16339:16770 */                 throw ((FFSException)localObject5);
/* 16340:      */               }
/* 16341:      */             }
/* 16342:      */             else {
/* 16343:16774 */               throw FFSExceptionHelper.read(localUserException.input);
/* 16344:      */             }
/* 16345:      */           }
/* 16346:      */         }
/* 16347:16779 */         throw new java.rmi.RemoteException(localUserException.type);
/* 16348:      */       }
/* 16349:      */       catch (SystemException localSystemException)
/* 16350:      */       {
/* 16351:16783 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 16352:      */       }
/* 16353:      */       finally
/* 16354:      */       {
/* 16355:16787 */         if (local_Request != null) {
/* 16356:16789 */           local_Request.close();
/* 16357:      */         }
/* 16358:16791 */         if (bool1) {
/* 16359:16792 */           localLocalStack.pop(localLocalFrame);
/* 16360:      */         }
/* 16361:16793 */         localLocalStack.setArgsOnLocal(bool2);
/* 16362:      */       }
/* 16363:      */     }
/* 16364:      */   }
/* 16365:      */   
/* 16366:      */   public PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
/* 16367:      */     throws java.rmi.RemoteException, FFSException
/* 16368:      */   {
/* 16369:16803 */     for (int i = 1;; i++)
/* 16370:      */     {
/* 16371:16805 */       _Request local_Request = null;
/* 16372:16806 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 16373:16807 */       boolean bool1 = false;
/* 16374:16808 */       LocalFrame localLocalFrame = null;
/* 16375:16809 */       boolean bool2 = false;
/* 16376:      */       try
/* 16377:      */       {
/* 16378:16812 */         local_Request = __request("searchGlobalPayees");
/* 16379:16813 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 16380:16814 */         bool2 = localLocalStack.isArgsOnLocal();
/* 16381:16815 */         localLocalStack.setArgsOnLocal(bool1);
/* 16382:16817 */         if (bool1)
/* 16383:      */         {
/* 16384:16819 */           localLocalFrame = new LocalFrame(2);
/* 16385:16820 */           localLocalStack.push(localLocalFrame);
/* 16386:      */         }
/* 16387:16822 */         if (!bool1)
/* 16388:      */         {
/* 16389:16824 */           localObject4 = local_Request.getOutputStream();
/* 16390:16825 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 16391:16826 */           ((OutputStream)localObject4).write_long(paramInt);
/* 16392:      */         }
/* 16393:      */         else
/* 16394:      */         {
/* 16395:16830 */           localObject4 = local_Request.getOutputStream();
/* 16396:16831 */           localLocalFrame.add(paramPayeeInfo);
/* 16397:16832 */           localLocalFrame.add(paramInt);
/* 16398:      */         }
/* 16399:16834 */         local_Request.invoke();
/* 16400:      */         Object localObject1;
/* 16401:16835 */         if (bool1)
/* 16402:      */         {
/* 16403:16837 */           localObject4 = null;
/* 16404:16838 */           localObject5 = localLocalFrame.getResult();
/* 16405:16839 */           if (localObject5 != null) {
/* 16406:16841 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 16407:      */           }
/* 16408:16843 */           return localObject4;
/* 16409:      */         }
/* 16410:16845 */         Object localObject4 = local_Request.getInputStream();
/* 16411:16847 */         if (local_Request.isRMI()) {
/* 16412:16847 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 16413:      */         } else {
/* 16414:16847 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 16415:      */         }
/* 16416:16848 */         return localObject5;
/* 16417:      */       }
/* 16418:      */       catch (TRANSIENT localTRANSIENT)
/* 16419:      */       {
/* 16420:16852 */         if (i == 10) {
/* 16421:16854 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 16422:      */         }
/* 16423:      */       }
/* 16424:      */       catch (UserException localUserException)
/* 16425:      */       {
/* 16426:      */         Object localObject5;
/* 16427:16859 */         if (local_Request.isRMI())
/* 16428:      */         {
/* 16429:16861 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 16430:16863 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16431:      */           }
/* 16432:      */         }
/* 16433:      */         else
/* 16434:      */         {
/* 16435:16868 */           localObject5 = null;
/* 16436:16869 */           if (bool1)
/* 16437:      */           {
/* 16438:16871 */             localObject5 = localLocalFrame.getException();
/* 16439:16872 */             if (localObject5 != null) {
/* 16440:16874 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 16441:      */             }
/* 16442:      */           }
/* 16443:16877 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 16444:      */           {
/* 16445:16879 */             if (local_Request.isRMI()) {
/* 16446:16881 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16447:      */             }
/* 16448:16885 */             if (bool1)
/* 16449:      */             {
/* 16450:16887 */               if (localObject5 != null) {
/* 16451:16887 */                 throw ((FFSException)localObject5);
/* 16452:      */               }
/* 16453:      */             }
/* 16454:      */             else {
/* 16455:16891 */               throw FFSExceptionHelper.read(localUserException.input);
/* 16456:      */             }
/* 16457:      */           }
/* 16458:      */         }
/* 16459:16896 */         throw new java.rmi.RemoteException(localUserException.type);
/* 16460:      */       }
/* 16461:      */       catch (SystemException localSystemException)
/* 16462:      */       {
/* 16463:16900 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 16464:      */       }
/* 16465:      */       finally
/* 16466:      */       {
/* 16467:16904 */         if (local_Request != null) {
/* 16468:16906 */           local_Request.close();
/* 16469:      */         }
/* 16470:16908 */         if (bool1) {
/* 16471:16909 */           localLocalStack.pop(localLocalFrame);
/* 16472:      */         }
/* 16473:16910 */         localLocalStack.setArgsOnLocal(bool2);
/* 16474:      */       }
/* 16475:      */     }
/* 16476:      */   }
/* 16477:      */   
/* 16478:      */   public PayeeInfo getGlobalPayee(String paramString)
/* 16479:      */     throws java.rmi.RemoteException, FFSException
/* 16480:      */   {
/* 16481:16919 */     for (int i = 1;; i++)
/* 16482:      */     {
/* 16483:16921 */       _Request local_Request = null;
/* 16484:16922 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 16485:16923 */       boolean bool1 = false;
/* 16486:16924 */       LocalFrame localLocalFrame = null;
/* 16487:16925 */       boolean bool2 = false;
/* 16488:      */       try
/* 16489:      */       {
/* 16490:16928 */         local_Request = __request("getGlobalPayee");
/* 16491:16929 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 16492:16930 */         bool2 = localLocalStack.isArgsOnLocal();
/* 16493:16931 */         localLocalStack.setArgsOnLocal(bool1);
/* 16494:16933 */         if (bool1)
/* 16495:      */         {
/* 16496:16935 */           localLocalFrame = new LocalFrame(1);
/* 16497:16936 */           localLocalStack.push(localLocalFrame);
/* 16498:      */         }
/* 16499:16938 */         if (!bool1)
/* 16500:      */         {
/* 16501:16940 */           localObject4 = local_Request.getOutputStream();
/* 16502:16941 */           local_Request.write_string(paramString);
/* 16503:      */         }
/* 16504:      */         else
/* 16505:      */         {
/* 16506:16945 */           localObject4 = local_Request.getOutputStream();
/* 16507:16946 */           localLocalFrame.add(paramString);
/* 16508:      */         }
/* 16509:16948 */         local_Request.invoke();
/* 16510:      */         Object localObject1;
/* 16511:16949 */         if (bool1)
/* 16512:      */         {
/* 16513:16951 */           localObject4 = null;
/* 16514:16952 */           localObject5 = localLocalFrame.getResult();
/* 16515:16953 */           if (localObject5 != null) {
/* 16516:16955 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 16517:      */           }
/* 16518:16957 */           return localObject4;
/* 16519:      */         }
/* 16520:16959 */         Object localObject4 = local_Request.getInputStream();
/* 16521:      */         
/* 16522:16961 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 16523:16962 */         return localObject5;
/* 16524:      */       }
/* 16525:      */       catch (TRANSIENT localTRANSIENT)
/* 16526:      */       {
/* 16527:16966 */         if (i == 10) {
/* 16528:16968 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 16529:      */         }
/* 16530:      */       }
/* 16531:      */       catch (UserException localUserException)
/* 16532:      */       {
/* 16533:      */         Object localObject5;
/* 16534:16973 */         if (local_Request.isRMI())
/* 16535:      */         {
/* 16536:16975 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 16537:16977 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16538:      */           }
/* 16539:      */         }
/* 16540:      */         else
/* 16541:      */         {
/* 16542:16982 */           localObject5 = null;
/* 16543:16983 */           if (bool1)
/* 16544:      */           {
/* 16545:16985 */             localObject5 = localLocalFrame.getException();
/* 16546:16986 */             if (localObject5 != null) {
/* 16547:16988 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 16548:      */             }
/* 16549:      */           }
/* 16550:16991 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 16551:      */           {
/* 16552:16993 */             if (local_Request.isRMI()) {
/* 16553:16995 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 16554:      */             }
/* 16555:16999 */             if (bool1)
/* 16556:      */             {
/* 16557:17001 */               if (localObject5 != null) {
/* 16558:17001 */                 throw ((FFSException)localObject5);
/* 16559:      */               }
/* 16560:      */             }
/* 16561:      */             else {
/* 16562:17005 */               throw FFSExceptionHelper.read(localUserException.input);
/* 16563:      */             }
/* 16564:      */           }
/* 16565:      */         }
/* 16566:17010 */         throw new java.rmi.RemoteException(localUserException.type);
/* 16567:      */       }
/* 16568:      */       catch (SystemException localSystemException)
/* 16569:      */       {
/* 16570:17014 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 16571:      */       }
/* 16572:      */       finally
/* 16573:      */       {
/* 16574:17018 */         if (local_Request != null) {
/* 16575:17020 */           local_Request.close();
/* 16576:      */         }
/* 16577:17022 */         if (bool1) {
/* 16578:17023 */           localLocalStack.pop(localLocalFrame);
/* 16579:      */         }
/* 16580:17024 */         localLocalStack.setArgsOnLocal(bool2);
/* 16581:      */       }
/* 16582:      */     }
/* 16583:      */   }
/* 16584:      */   
/* 16585:      */   public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
/* 16586:      */     throws java.rmi.RemoteException
/* 16587:      */   {
/* 16588:17033 */     for (int i = 1;; i++)
/* 16589:      */     {
/* 16590:17035 */       _Request local_Request = null;
/* 16591:17036 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 16592:17037 */       boolean bool1 = false;
/* 16593:17038 */       LocalFrame localLocalFrame = null;
/* 16594:17039 */       boolean bool2 = false;
/* 16595:      */       try
/* 16596:      */       {
/* 16597:17042 */         local_Request = __request("processPmtTrnRslt");
/* 16598:17043 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 16599:17044 */         bool2 = localLocalStack.isArgsOnLocal();
/* 16600:17045 */         localLocalStack.setArgsOnLocal(bool1);
/* 16601:17047 */         if (bool1)
/* 16602:      */         {
/* 16603:17049 */           localLocalFrame = new LocalFrame(1);
/* 16604:17050 */           localLocalStack.push(localLocalFrame);
/* 16605:      */         }
/* 16606:      */         OutputStream localOutputStream;
/* 16607:17052 */         if (!bool1)
/* 16608:      */         {
/* 16609:17054 */           localOutputStream = local_Request.getOutputStream();
/* 16610:17055 */           if (local_Request.isRMI()) {
/* 16611:17055 */             local_Request.write_value(paramArrayOfPmtTrnRslt, new PmtTrnRslt[0].getClass());
/* 16612:      */           } else {
/* 16613:17055 */             PmtTrnRsltSeqHelper.write(localOutputStream, paramArrayOfPmtTrnRslt);
/* 16614:      */           }
/* 16615:      */         }
/* 16616:      */         else
/* 16617:      */         {
/* 16618:17059 */           localOutputStream = local_Request.getOutputStream();
/* 16619:17060 */           localLocalFrame.add(paramArrayOfPmtTrnRslt);
/* 16620:      */         }
/* 16621:17062 */         local_Request.invoke();
/* 16622:17063 */         return;
/* 16623:      */       }
/* 16624:      */       catch (TRANSIENT localTRANSIENT)
/* 16625:      */       {
/* 16626:17067 */         if (i == 10) {
/* 16627:17069 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 16628:      */         }
/* 16629:      */       }
/* 16630:      */       catch (UserException localUserException)
/* 16631:      */       {
/* 16632:17074 */         local_Request.isRMI();
/* 16633:      */         
/* 16634:      */ 
/* 16635:17077 */         throw new java.rmi.RemoteException(localUserException.type);
/* 16636:      */       }
/* 16637:      */       catch (SystemException localSystemException)
/* 16638:      */       {
/* 16639:17081 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 16640:      */       }
/* 16641:      */       finally
/* 16642:      */       {
/* 16643:17085 */         if (local_Request != null) {
/* 16644:17087 */           local_Request.close();
/* 16645:      */         }
/* 16646:17089 */         if (bool1) {
/* 16647:17090 */           localLocalStack.pop(localLocalFrame);
/* 16648:      */         }
/* 16649:17091 */         localLocalStack.setArgsOnLocal(bool2);
/* 16650:      */       }
/* 16651:      */     }
/* 16652:      */   }
/* 16653:      */   
/* 16654:      */   public BPWServices_Stub() {}
/* 16655:      */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.BPWServices_Stub
 * JD-Core Version:    0.7.0.1
 */