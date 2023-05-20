/*   1:    */ package com.ffusion.ffs.bpw.ACHServices;
/*   2:    */ 
/*   3:    */ import com.ffusion.ffs.bpw.interfaces.ACHBatchHist;
/*   4:    */ import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
/*   5:    */ import com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo;
/*   6:    */ import com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo;
/*   7:    */ import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
/*   8:    */ import com.ffusion.ffs.bpw.interfaces.ACHCompanyList;
/*   9:    */ import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList;
/*  10:    */ import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
/*  11:    */ import com.ffusion.ffs.bpw.interfaces.ACHFileHist;
/*  12:    */ import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
/*  13:    */ import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
/*  14:    */ import com.ffusion.ffs.bpw.interfaces.ACHPayeeList;
/*  15:    */ import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
/*  16:    */ import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
/*  17:    */ import com.ffusion.ffs.bpw.interfaces.PagingInfo;
/*  18:    */ import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
/*  19:    */ import com.ffusion.ffs.interfaces.FFSException;
/*  20:    */ import com.ibm.ejs.container.EJSWrapper;
/*  21:    */ import java.io.Serializable;
/*  22:    */ import java.rmi.Remote;
/*  23:    */ import javax.ejb.EJBHome;
/*  24:    */ import javax.ejb.EJBObject;
/*  25:    */ import javax.ejb.Handle;
/*  26:    */ import javax.ejb.RemoveException;
/*  27:    */ import javax.rmi.CORBA.Tie;
/*  28:    */ import javax.rmi.CORBA.Util;
/*  29:    */ import org.omg.CORBA.BAD_OPERATION;
/*  30:    */ import org.omg.CORBA.ORB;
/*  31:    */ import org.omg.CORBA.SystemException;
/*  32:    */ import org.omg.CORBA.portable.Delegate;
/*  33:    */ import org.omg.CORBA.portable.ResponseHandler;
/*  34:    */ import org.omg.CORBA.portable.UnknownException;
/*  35:    */ 
/*  36:    */ public class _EJSRemoteStatelessACHBPWServices_e0156d61_Tie
/*  37:    */   extends org.omg.CORBA_2_3.portable.ObjectImpl
/*  38:    */   implements Tie
/*  39:    */ {
/*  40: 45 */   private EJSRemoteStatelessACHBPWServices_e0156d61 target = null;
/*  41: 46 */   private ORB orb = null;
/*  42: 48 */   private static final String[] _type_ids = {
/*  43: 49 */     "RMI:com.ffusion.ffs.bpw.ACHServices.ACHBPWServices:0000000000000000", 
/*  44: 50 */     "RMI:javax.ejb.EJBObject:0000000000000000", 
/*  45: 51 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
/*  46: 52 */     "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000" };
/*  47:    */   
/*  48:    */   public void setTarget(Remote paramRemote)
/*  49:    */   {
/*  50: 56 */     this.target = ((EJSRemoteStatelessACHBPWServices_e0156d61)paramRemote);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Remote getTarget()
/*  54:    */   {
/*  55: 60 */     return this.target;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public org.omg.CORBA.Object thisObject()
/*  59:    */   {
/*  60: 64 */     return this;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void deactivate()
/*  64:    */   {
/*  65: 68 */     if (this.orb != null)
/*  66:    */     {
/*  67: 69 */       this.orb.disconnect(this);
/*  68: 70 */       _set_delegate(null);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ORB orb()
/*  73:    */   {
/*  74: 75 */     return _orb();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void orb(ORB paramORB)
/*  78:    */   {
/*  79: 79 */     paramORB.connect(this);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void _set_delegate(Delegate paramDelegate)
/*  83:    */   {
/*  84: 83 */     super._set_delegate(paramDelegate);
/*  85: 84 */     if (paramDelegate != null) {
/*  86: 85 */       this.orb = _orb();
/*  87:    */     } else {
/*  88: 87 */       this.orb = null;
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String[] _ids()
/*  93:    */   {
/*  94: 91 */     return _type_ids;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public org.omg.CORBA.portable.OutputStream _invoke(String paramString, org.omg.CORBA.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  98:    */     throws SystemException
/*  99:    */   {
/* 100:    */     try
/* 101:    */     {
/* 102: 96 */       org.omg.CORBA_2_3.portable.InputStream localInputStream = 
/* 103: 97 */         (org.omg.CORBA_2_3.portable.InputStream)paramInputStream;
/* 104: 98 */       switch (paramString.hashCode())
/* 105:    */       {
/* 106:    */       case -2091690713: 
/* 107:100 */         if (paramString.equals("getACHBatchTemplate__CORBA_WStringValue")) {
/* 108:101 */           return getACHBatchTemplate__CORBA_WStringValue(localInputStream, paramResponseHandler);
/* 109:    */         }
/* 110:    */       case -2053187032: 
/* 111:104 */         if (paramString.equals("exportACHBatch")) {
/* 112:105 */           return exportACHBatch(localInputStream, paramResponseHandler);
/* 113:    */         }
/* 114:    */       case -1919702489: 
/* 115:108 */         if (paramString.equals("canACHFIInfo")) {
/* 116:109 */           return canACHFIInfo(localInputStream, paramResponseHandler);
/* 117:    */         }
/* 118:    */       case -1865979324: 
/* 119:112 */         if (paramString.equals("canACHBatch")) {
/* 120:113 */           return canACHBatch(localInputStream, paramResponseHandler);
/* 121:    */         }
/* 122:    */       case -1853045166: 
/* 123:116 */         if (paramString.equals("canACHPayee")) {
/* 124:117 */           return canACHPayee(localInputStream, paramResponseHandler);
/* 125:    */         }
/* 126:    */       case -1792309266: 
/* 127:120 */         if (paramString.equals("getFIInfoByRTN")) {
/* 128:121 */           return getFIInfoByRTN(localInputStream, paramResponseHandler);
/* 129:    */         }
/* 130:    */       case -1774638642: 
/* 131:124 */         if (paramString.equals("getRecACHBatch")) {
/* 132:125 */           return getRecACHBatch(localInputStream, paramResponseHandler);
/* 133:    */         }
/* 134:    */       case -1743054838: 
/* 135:128 */         if (paramString.equals("activateCompany")) {
/* 136:129 */           return activateCompany(localInputStream, paramResponseHandler);
/* 137:    */         }
/* 138:    */       case -1550521068: 
/* 139:132 */         if (paramString.equals("_get_EJBHome")) {
/* 140:133 */           return _get_EJBHome(localInputStream, paramResponseHandler);
/* 141:    */         }
/* 142:    */       case -1485458410: 
/* 143:136 */         if (paramString.equals("getACHBatchTemplate__org_omg_boxedRMI_CORBA_seq1_WStringValue")) {
/* 144:137 */           return getACHBatchTemplate__org_omg_boxedRMI_CORBA_seq1_WStringValue(localInputStream, paramResponseHandler);
/* 145:    */         }
/* 146:    */       case -1357172217: 
/* 147:140 */         if (paramString.equals("getPagedACH")) {
/* 148:141 */           return getPagedACH(localInputStream, paramResponseHandler);
/* 149:    */         }
/* 150:    */       case -1308619658: 
/* 151:144 */         if (paramString.equals("addACHFIInfo")) {
/* 152:145 */           return addACHFIInfo(localInputStream, paramResponseHandler);
/* 153:    */         }
/* 154:    */       case -1279656195: 
/* 155:148 */         if (paramString.equals("addACHPayee__com_ffusion_ffs_bpw_interfaces_ACHPayeeInfo")) {
/* 156:149 */           return addACHPayee__com_ffusion_ffs_bpw_interfaces_ACHPayeeInfo(localInputStream, paramResponseHandler);
/* 157:    */         }
/* 158:    */       case -1259764295: 
/* 159:152 */         if (paramString.equals("modACHCompany")) {
/* 160:153 */           return modACHCompany(localInputStream, paramResponseHandler);
/* 161:    */         }
/* 162:    */       case -1257004973: 
/* 163:156 */         if (paramString.equals("canACHCompanyOffsetAccount")) {
/* 164:157 */           return canACHCompanyOffsetAccount(localInputStream, paramResponseHandler);
/* 165:    */         }
/* 166:    */       case -1235788974: 
/* 167:160 */         if (paramString.equals("getACHParticipantTotals")) {
/* 168:161 */           return getACHParticipantTotals(localInputStream, paramResponseHandler);
/* 169:    */         }
/* 170:    */       case -1217409374: 
/* 171:164 */         if (paramString.equals("modRecACHBatch")) {
/* 172:165 */           return modRecACHBatch(localInputStream, paramResponseHandler);
/* 173:    */         }
/* 174:    */       case -1180323487: 
/* 175:168 */         if (paramString.equals("getACHFIInfo")) {
/* 176:169 */           return getACHFIInfo(localInputStream, paramResponseHandler);
/* 177:    */         }
/* 178:    */       case -1102865052: 
/* 179:172 */         if (paramString.equals("getACHBatchTemplateByGroup")) {
/* 180:173 */           return getACHBatchTemplateByGroup(localInputStream, paramResponseHandler);
/* 181:    */         }
/* 182:    */       case -1082759444: 
/* 183:176 */         if (paramString.equals("getACHFile")) {
/* 184:177 */           return getACHFile(localInputStream, paramResponseHandler);
/* 185:    */         }
/* 186:    */       case -1011244123: 
/* 187:180 */         if (paramString.equals("_get_primaryKey")) {
/* 188:181 */           return _get_primaryKey(localInputStream, paramResponseHandler);
/* 189:    */         }
/* 190:    */       case -934610812: 
/* 191:184 */         if (paramString.equals("remove")) {
/* 192:185 */           return remove(localInputStream, paramResponseHandler);
/* 193:    */         }
/* 194:    */       case -922536697: 
/* 195:188 */         if (paramString.equals("canACHCompany")) {
/* 196:189 */           return canACHCompany(localInputStream, paramResponseHandler);
/* 197:    */         }
/* 198:    */       case -759280209: 
/* 199:192 */         if (paramString.equals("addACHBatchTemplate")) {
/* 200:193 */           return addACHBatchTemplate(localInputStream, paramResponseHandler);
/* 201:    */         }
/* 202:    */       case -743152865: 
/* 203:196 */         if (paramString.equals("getACHTotals")) {
/* 204:197 */           return getACHTotals(localInputStream, paramResponseHandler);
/* 205:    */         }
/* 206:    */       case -739596618: 
/* 207:200 */         if (paramString.equals("getDefaultACHBatchEffectiveDate")) {
/* 208:201 */           return getDefaultACHBatchEffectiveDate(localInputStream, paramResponseHandler);
/* 209:    */         }
/* 210:    */       case -575519441: 
/* 211:204 */         if (paramString.equals("setACHSameDayEffDateInfo")) {
/* 212:205 */           return setACHSameDayEffDateInfo(localInputStream, paramResponseHandler);
/* 213:    */         }
/* 214:    */       case -536082759: 
/* 215:208 */         if (paramString.equals("deleteACHBatchTemplate")) {
/* 216:209 */           return deleteACHBatchTemplate(localInputStream, paramResponseHandler);
/* 217:    */         }
/* 218:    */       case -513207389: 
/* 219:212 */         if (paramString.equals("addRecACHBatch")) {
/* 220:213 */           return addRecACHBatch(localInputStream, paramResponseHandler);
/* 221:    */         }
/* 222:    */       case -385902064: 
/* 223:216 */         if (paramString.equals("modACHBatchTemplate")) {
/* 224:217 */           return modACHBatchTemplate(localInputStream, paramResponseHandler);
/* 225:    */         }
/* 226:    */       case -377401626: 
/* 227:220 */         if (paramString.equals("getRecACHBatchHistory")) {
/* 228:221 */           return getRecACHBatchHistory(localInputStream, paramResponseHandler);
/* 229:    */         }
/* 230:    */       case -217170058: 
/* 231:224 */         if (paramString.equals("modACHBatch")) {
/* 232:225 */           return modACHBatch(localInputStream, paramResponseHandler);
/* 233:    */         }
/* 234:    */       case -211385535: 
/* 235:228 */         if (paramString.equals("addACHFile")) {
/* 236:229 */           return addACHFile(localInputStream, paramResponseHandler);
/* 237:    */         }
/* 238:    */       case -204235900: 
/* 239:232 */         if (paramString.equals("modACHPayee")) {
/* 240:233 */           return modACHPayee(localInputStream, paramResponseHandler);
/* 241:    */         }
/* 242:    */       case 228561595: 
/* 243:236 */         if (paramString.equals("getACHSameDayEffDateInfo")) {
/* 244:237 */           return getACHSameDayEffDateInfo(localInputStream, paramResponseHandler);
/* 245:    */         }
/* 246:    */       case 305363924: 
/* 247:240 */         if (paramString.equals("addACHPayee__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_ACHPayeeInfo")) {
/* 248:241 */           return addACHPayee__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_ACHPayeeInfo(localInputStream, paramResponseHandler);
/* 249:    */         }
/* 250:    */       case 415076407: 
/* 251:244 */         if (paramString.equals("getACHCompanySummaries")) {
/* 252:245 */           return getACHCompanySummaries(localInputStream, paramResponseHandler);
/* 253:    */         }
/* 254:    */       case 523375885: 
/* 255:248 */         if (paramString.equals("getACHCompany")) {
/* 256:249 */           return getACHCompany(localInputStream, paramResponseHandler);
/* 257:    */         }
/* 258:    */       case 530405532: 
/* 259:252 */         if (paramString.equals("disconnect")) {
/* 260:253 */           return disconnect(localInputStream, paramResponseHandler);
/* 261:    */         }
/* 262:    */       case 578403548: 
/* 263:256 */         if (paramString.equals("updateACHPayeePrenoteStatus")) {
/* 264:257 */           return updateACHPayeePrenoteStatus(localInputStream, paramResponseHandler);
/* 265:    */         }
/* 266:    */       case 632861704: 
/* 267:260 */         if (paramString.equals("getACHFileHistory")) {
/* 268:261 */           return getACHFileHistory(localInputStream, paramResponseHandler);
/* 269:    */         }
/* 270:    */       case 646711572: 
/* 271:264 */         if (paramString.equals("canRecACHBatch")) {
/* 272:265 */           return canRecACHBatch(localInputStream, paramResponseHandler);
/* 273:    */         }
/* 274:    */       case 673631470: 
/* 275:268 */         if (paramString.equals("getACHCompanyOffsetAccountByCompId")) {
/* 276:269 */           return getACHCompanyOffsetAccountByCompId(localInputStream, paramResponseHandler);
/* 277:    */         }
/* 278:    */       case 707100897: 
/* 279:272 */         if (paramString.equals("modACHCompanyOffsetAccount")) {
/* 280:273 */           return modACHCompanyOffsetAccount(localInputStream, paramResponseHandler);
/* 281:    */         }
/* 282:    */       case 771217714: 
/* 283:276 */         if (paramString.equals("canACHFile")) {
/* 284:277 */           return canACHFile(localInputStream, paramResponseHandler);
/* 285:    */         }
/* 286:    */       case 790270922: 
/* 287:280 */         if (paramString.equals("getACHBatch")) {
/* 288:281 */           return getACHBatch(localInputStream, paramResponseHandler);
/* 289:    */         }
/* 290:    */       case 803205080: 
/* 291:284 */         if (paramString.equals("getACHPayee")) {
/* 292:285 */           return getACHPayee(localInputStream, paramResponseHandler);
/* 293:    */         }
/* 294:    */       case 841161880: 
/* 295:288 */         if (paramString.equals("addACHCompany")) {
/* 296:289 */           return addACHCompany(localInputStream, paramResponseHandler);
/* 297:    */         }
/* 298:    */       case 1264756395: 
/* 299:292 */         if (paramString.equals("isIdentical")) {
/* 300:293 */           return isIdentical(localInputStream, paramResponseHandler);
/* 301:    */         }
/* 302:    */       case 1549297814: 
/* 303:296 */         if (paramString.equals("getACHPayeeList")) {
/* 304:297 */           return getACHPayeeList(localInputStream, paramResponseHandler);
/* 305:    */         }
/* 306:    */       case 1586491754: 
/* 307:300 */         if (paramString.equals("getACHBatchHistory")) {
/* 308:301 */           return getACHBatchHistory(localInputStream, paramResponseHandler);
/* 309:    */         }
/* 310:    */       case 1593502539: 
/* 311:304 */         if (paramString.equals("getACHCompanyList")) {
/* 312:305 */           return getACHCompanyList(localInputStream, paramResponseHandler);
/* 313:    */         }
/* 314:    */       case 1599458966: 
/* 315:308 */         if (paramString.equals("activateACHFI")) {
/* 316:309 */           return activateACHFI(localInputStream, paramResponseHandler);
/* 317:    */         }
/* 318:    */       case 1614253589: 
/* 319:312 */         if (paramString.equals("activatePayee")) {
/* 320:313 */           return activatePayee(localInputStream, paramResponseHandler);
/* 321:    */         }
/* 322:    */       case 1660665378: 
/* 323:316 */         if (paramString.equals("getACHBatchTemplateByCompany")) {
/* 324:317 */           return getACHBatchTemplateByCompany(localInputStream, paramResponseHandler);
/* 325:    */         }
/* 326:    */       case 1676285252: 
/* 327:320 */         if (paramString.equals("getACHCompanyOffsetAccountByAccountId")) {
/* 328:321 */           return getACHCompanyOffsetAccountByAccountId(localInputStream, paramResponseHandler);
/* 329:    */         }
/* 330:    */       case 1709329506: 
/* 331:324 */         if (paramString.equals("addACHCompanyOffsetAccount")) {
/* 332:325 */           return addACHCompanyOffsetAccount(localInputStream, paramResponseHandler);
/* 333:    */         }
/* 334:    */       case 1944413392: 
/* 335:328 */         if (paramString.equals("_get_handle")) {
/* 336:329 */           return _get_handle(localInputStream, paramResponseHandler);
/* 337:    */         }
/* 338:    */       case 1948744501: 
/* 339:332 */         if (paramString.equals("modACHFIInfo")) {
/* 340:333 */           return modACHFIInfo(localInputStream, paramResponseHandler);
/* 341:    */         }
/* 342:    */       case 2033058325: 
/* 343:336 */         if (paramString.equals("addACHBatch")) {
/* 344:337 */           return addACHBatch(localInputStream, paramResponseHandler);
/* 345:    */         }
/* 346:    */         break;
/* 347:    */       }
/* 348:340 */       throw new BAD_OPERATION();
/* 349:    */     }
/* 350:    */     catch (SystemException localSystemException)
/* 351:    */     {
/* 352:342 */       throw localSystemException;
/* 353:    */     }
/* 354:    */     catch (Throwable localThrowable)
/* 355:    */     {
/* 356:344 */       throw new UnknownException(localThrowable);
/* 357:    */     }
/* 358:    */   }
/* 359:    */   
/* 360:    */   private org.omg.CORBA.portable.OutputStream _get_EJBHome(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 361:    */     throws Throwable
/* 362:    */   {
/* 363:349 */     EJBHome localEJBHome = this.target.getEJBHome();
/* 364:350 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 365:351 */     Util.writeRemoteObject(localOutputStream, localEJBHome);
/* 366:352 */     return localOutputStream;
/* 367:    */   }
/* 368:    */   
/* 369:    */   private org.omg.CORBA.portable.OutputStream _get_primaryKey(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 370:    */     throws Throwable
/* 371:    */   {
/* 372:356 */     java.lang.Object localObject = this.target.getPrimaryKey();
/* 373:357 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 374:358 */     Util.writeAny(localOutputStream, localObject);
/* 375:359 */     return localOutputStream;
/* 376:    */   }
/* 377:    */   
/* 378:    */   private org.omg.CORBA.portable.OutputStream remove(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 379:    */     throws Throwable
/* 380:    */   {
/* 381:    */     try
/* 382:    */     {
/* 383:364 */       this.target.remove();
/* 384:    */     }
/* 385:    */     catch (RemoveException localRemoveException)
/* 386:    */     {
/* 387:366 */       String str = "IDL:javax/ejb/RemoveEx:1.0";
/* 388:367 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 389:368 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 390:369 */       localOutputStream1.write_string(str);
/* 391:370 */       localOutputStream1.write_value(localRemoveException, RemoveException.class);
/* 392:371 */       return localOutputStream1;
/* 393:    */     }
/* 394:373 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 395:374 */     return localOutputStream;
/* 396:    */   }
/* 397:    */   
/* 398:    */   private org.omg.CORBA.portable.OutputStream _get_handle(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 399:    */     throws Throwable
/* 400:    */   {
/* 401:378 */     Handle localHandle = this.target.getHandle();
/* 402:379 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 403:380 */     Util.writeAbstractObject(localOutputStream, localHandle);
/* 404:381 */     return localOutputStream;
/* 405:    */   }
/* 406:    */   
/* 407:    */   private org.omg.CORBA.portable.OutputStream isIdentical(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 408:    */     throws Throwable
/* 409:    */   {
/* 410:385 */     EJBObject localEJBObject = (EJBObject)paramInputStream.read_Object(EJBObject.class);
/* 411:386 */     boolean bool = this.target.isIdentical(localEJBObject);
/* 412:387 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 413:388 */     localOutputStream.write_boolean(bool);
/* 414:389 */     return localOutputStream;
/* 415:    */   }
/* 416:    */   
/* 417:    */   private org.omg.CORBA.portable.OutputStream disconnect(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 418:    */     throws Throwable
/* 419:    */   {
/* 420:393 */     this.target.disconnect();
/* 421:394 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 422:395 */     return localOutputStream;
/* 423:    */   }
/* 424:    */   
/* 425:    */   private org.omg.CORBA.portable.OutputStream addACHFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 426:    */     throws Throwable
/* 427:    */   {
/* 428:399 */     ACHFIInfo localACHFIInfo1 = (ACHFIInfo)paramInputStream.read_value(ACHFIInfo.class);
/* 429:400 */     ACHFIInfo localACHFIInfo2 = this.target.addACHFIInfo(localACHFIInfo1);
/* 430:401 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 431:402 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 432:403 */     localOutputStream.write_value(localACHFIInfo2, ACHFIInfo.class);
/* 433:404 */     return localOutputStream;
/* 434:    */   }
/* 435:    */   
/* 436:    */   private org.omg.CORBA.portable.OutputStream modACHFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 437:    */     throws Throwable
/* 438:    */   {
/* 439:408 */     ACHFIInfo localACHFIInfo1 = (ACHFIInfo)paramInputStream.read_value(ACHFIInfo.class);
/* 440:409 */     ACHFIInfo localACHFIInfo2 = this.target.modACHFIInfo(localACHFIInfo1);
/* 441:410 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 442:411 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 443:412 */     localOutputStream.write_value(localACHFIInfo2, ACHFIInfo.class);
/* 444:413 */     return localOutputStream;
/* 445:    */   }
/* 446:    */   
/* 447:    */   private org.omg.CORBA.portable.OutputStream getACHFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 448:    */     throws Throwable
/* 449:    */   {
/* 450:417 */     String str = (String)paramInputStream.read_value(String.class);
/* 451:418 */     ACHFIInfo localACHFIInfo = this.target.getACHFIInfo(str);
/* 452:419 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 453:420 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 454:421 */     localOutputStream.write_value(localACHFIInfo, ACHFIInfo.class);
/* 455:422 */     return localOutputStream;
/* 456:    */   }
/* 457:    */   
/* 458:    */   private org.omg.CORBA.portable.OutputStream getFIInfoByRTN(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 459:    */     throws Throwable
/* 460:    */   {
/* 461:426 */     String str = (String)paramInputStream.read_value(String.class);
/* 462:427 */     ACHFIInfo[] arrayOfACHFIInfo = this.target.getFIInfoByRTN(str);
/* 463:428 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 464:429 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 465:430 */     localOutputStream.write_value(cast_array(arrayOfACHFIInfo), new ACHFIInfo[0].getClass());
/* 466:431 */     return localOutputStream;
/* 467:    */   }
/* 468:    */   
/* 469:    */   private org.omg.CORBA.portable.OutputStream canACHFIInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 470:    */     throws Throwable
/* 471:    */   {
/* 472:435 */     ACHFIInfo localACHFIInfo1 = (ACHFIInfo)paramInputStream.read_value(ACHFIInfo.class);
/* 473:436 */     ACHFIInfo localACHFIInfo2 = this.target.canACHFIInfo(localACHFIInfo1);
/* 474:437 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 475:438 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 476:439 */     localOutputStream.write_value(localACHFIInfo2, ACHFIInfo.class);
/* 477:440 */     return localOutputStream;
/* 478:    */   }
/* 479:    */   
/* 480:    */   private org.omg.CORBA.portable.OutputStream activateACHFI(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 481:    */     throws Throwable
/* 482:    */   {
/* 483:444 */     String str = (String)paramInputStream.read_value(String.class);
/* 484:445 */     ACHFIInfo localACHFIInfo = this.target.activateACHFI(str);
/* 485:446 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 486:447 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 487:448 */     localOutputStream.write_value(localACHFIInfo, ACHFIInfo.class);
/* 488:449 */     return localOutputStream;
/* 489:    */   }
/* 490:    */   
/* 491:    */   private org.omg.CORBA.portable.OutputStream addACHCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 492:    */     throws Throwable
/* 493:    */   {
/* 494:453 */     ACHCompanyInfo localACHCompanyInfo1 = (ACHCompanyInfo)paramInputStream.read_value(ACHCompanyInfo.class);
/* 495:454 */     ACHCompanyInfo localACHCompanyInfo2 = this.target.addACHCompany(localACHCompanyInfo1);
/* 496:455 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 497:456 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 498:457 */     localOutputStream.write_value(localACHCompanyInfo2, ACHCompanyInfo.class);
/* 499:458 */     return localOutputStream;
/* 500:    */   }
/* 501:    */   
/* 502:    */   private org.omg.CORBA.portable.OutputStream modACHCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 503:    */     throws Throwable
/* 504:    */   {
/* 505:462 */     ACHCompanyInfo localACHCompanyInfo1 = (ACHCompanyInfo)paramInputStream.read_value(ACHCompanyInfo.class);
/* 506:463 */     ACHCompanyInfo localACHCompanyInfo2 = this.target.modACHCompany(localACHCompanyInfo1);
/* 507:464 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 508:465 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 509:466 */     localOutputStream.write_value(localACHCompanyInfo2, ACHCompanyInfo.class);
/* 510:467 */     return localOutputStream;
/* 511:    */   }
/* 512:    */   
/* 513:    */   private org.omg.CORBA.portable.OutputStream canACHCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 514:    */     throws Throwable
/* 515:    */   {
/* 516:471 */     ACHCompanyInfo localACHCompanyInfo1 = (ACHCompanyInfo)paramInputStream.read_value(ACHCompanyInfo.class);
/* 517:472 */     ACHCompanyInfo localACHCompanyInfo2 = this.target.canACHCompany(localACHCompanyInfo1);
/* 518:473 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 519:474 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 520:475 */     localOutputStream.write_value(localACHCompanyInfo2, ACHCompanyInfo.class);
/* 521:476 */     return localOutputStream;
/* 522:    */   }
/* 523:    */   
/* 524:    */   private org.omg.CORBA.portable.OutputStream activateCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 525:    */     throws Throwable
/* 526:    */   {
/* 527:480 */     String str = (String)paramInputStream.read_value(String.class);
/* 528:481 */     ACHCompanyInfo localACHCompanyInfo = this.target.activateCompany(str);
/* 529:482 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 530:483 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 531:484 */     localOutputStream.write_value(localACHCompanyInfo, ACHCompanyInfo.class);
/* 532:485 */     return localOutputStream;
/* 533:    */   }
/* 534:    */   
/* 535:    */   private org.omg.CORBA.portable.OutputStream getACHCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 536:    */     throws Throwable
/* 537:    */   {
/* 538:489 */     String str = (String)paramInputStream.read_value(String.class);
/* 539:490 */     ACHCompanyInfo localACHCompanyInfo = this.target.getACHCompany(str);
/* 540:491 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 541:492 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 542:493 */     localOutputStream.write_value(localACHCompanyInfo, ACHCompanyInfo.class);
/* 543:494 */     return localOutputStream;
/* 544:    */   }
/* 545:    */   
/* 546:    */   private org.omg.CORBA.portable.OutputStream getACHCompanyList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 547:    */     throws Throwable
/* 548:    */   {
/* 549:498 */     ACHCompanyList localACHCompanyList1 = (ACHCompanyList)paramInputStream.read_value(ACHCompanyList.class);
/* 550:499 */     ACHCompanyList localACHCompanyList2 = this.target.getACHCompanyList(localACHCompanyList1);
/* 551:500 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 552:501 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 553:502 */     localOutputStream.write_value(localACHCompanyList2, ACHCompanyList.class);
/* 554:503 */     return localOutputStream;
/* 555:    */   }
/* 556:    */   
/* 557:    */   private org.omg.CORBA.portable.OutputStream getACHCompanySummaries(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 558:    */     throws Throwable
/* 559:    */   {
/* 560:507 */     ACHCompanySummaryList localACHCompanySummaryList1 = (ACHCompanySummaryList)paramInputStream.read_value(ACHCompanySummaryList.class);
/* 561:508 */     ACHCompanySummaryList localACHCompanySummaryList2 = this.target.getACHCompanySummaries(localACHCompanySummaryList1);
/* 562:509 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 563:510 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 564:511 */     localOutputStream.write_value(localACHCompanySummaryList2, ACHCompanySummaryList.class);
/* 565:512 */     return localOutputStream;
/* 566:    */   }
/* 567:    */   
/* 568:    */   private org.omg.CORBA.portable.OutputStream addACHPayee__com_ffusion_ffs_bpw_interfaces_ACHPayeeInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 569:    */     throws Throwable
/* 570:    */   {
/* 571:516 */     ACHPayeeInfo localACHPayeeInfo1 = (ACHPayeeInfo)paramInputStream.read_value(ACHPayeeInfo.class);
/* 572:517 */     ACHPayeeInfo localACHPayeeInfo2 = this.target.addACHPayee(localACHPayeeInfo1);
/* 573:518 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 574:519 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 575:520 */     localOutputStream.write_value(localACHPayeeInfo2, ACHPayeeInfo.class);
/* 576:521 */     return localOutputStream;
/* 577:    */   }
/* 578:    */   
/* 579:    */   private org.omg.CORBA.portable.OutputStream addACHPayee__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_ACHPayeeInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 580:    */     throws Throwable
/* 581:    */   {
/* 582:525 */     ACHPayeeInfo[] arrayOfACHPayeeInfo1 = (ACHPayeeInfo[])paramInputStream.read_value(new ACHPayeeInfo[0].getClass());
/* 583:526 */     ACHPayeeInfo[] arrayOfACHPayeeInfo2 = this.target.addACHPayee(arrayOfACHPayeeInfo1);
/* 584:527 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 585:528 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 586:529 */     localOutputStream.write_value(cast_array(arrayOfACHPayeeInfo2), new ACHPayeeInfo[0].getClass());
/* 587:530 */     return localOutputStream;
/* 588:    */   }
/* 589:    */   
/* 590:    */   private org.omg.CORBA.portable.OutputStream modACHPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 591:    */     throws Throwable
/* 592:    */   {
/* 593:534 */     ACHPayeeInfo localACHPayeeInfo1 = (ACHPayeeInfo)paramInputStream.read_value(ACHPayeeInfo.class);
/* 594:535 */     ACHPayeeInfo localACHPayeeInfo2 = this.target.modACHPayee(localACHPayeeInfo1);
/* 595:536 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 596:537 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 597:538 */     localOutputStream.write_value(localACHPayeeInfo2, ACHPayeeInfo.class);
/* 598:539 */     return localOutputStream;
/* 599:    */   }
/* 600:    */   
/* 601:    */   private org.omg.CORBA.portable.OutputStream canACHPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 602:    */     throws Throwable
/* 603:    */   {
/* 604:543 */     ACHPayeeInfo localACHPayeeInfo1 = (ACHPayeeInfo)paramInputStream.read_value(ACHPayeeInfo.class);
/* 605:544 */     ACHPayeeInfo localACHPayeeInfo2 = this.target.canACHPayee(localACHPayeeInfo1);
/* 606:545 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 607:546 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 608:547 */     localOutputStream.write_value(localACHPayeeInfo2, ACHPayeeInfo.class);
/* 609:548 */     return localOutputStream;
/* 610:    */   }
/* 611:    */   
/* 612:    */   private org.omg.CORBA.portable.OutputStream activatePayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 613:    */     throws Throwable
/* 614:    */   {
/* 615:552 */     String str = (String)paramInputStream.read_value(String.class);
/* 616:553 */     ACHPayeeInfo localACHPayeeInfo = this.target.activatePayee(str);
/* 617:554 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 618:555 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 619:556 */     localOutputStream.write_value(localACHPayeeInfo, ACHPayeeInfo.class);
/* 620:557 */     return localOutputStream;
/* 621:    */   }
/* 622:    */   
/* 623:    */   private org.omg.CORBA.portable.OutputStream getACHPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 624:    */     throws Throwable
/* 625:    */   {
/* 626:561 */     String str = (String)paramInputStream.read_value(String.class);
/* 627:562 */     ACHPayeeInfo localACHPayeeInfo = this.target.getACHPayee(str);
/* 628:563 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 629:564 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 630:565 */     localOutputStream.write_value(localACHPayeeInfo, ACHPayeeInfo.class);
/* 631:566 */     return localOutputStream;
/* 632:    */   }
/* 633:    */   
/* 634:    */   private org.omg.CORBA.portable.OutputStream getACHPayeeList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 635:    */     throws Throwable
/* 636:    */   {
/* 637:570 */     ACHPayeeList localACHPayeeList1 = (ACHPayeeList)paramInputStream.read_value(ACHPayeeList.class);
/* 638:571 */     ACHPayeeList localACHPayeeList2 = this.target.getACHPayeeList(localACHPayeeList1);
/* 639:572 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 640:573 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 641:574 */     localOutputStream.write_value(localACHPayeeList2, ACHPayeeList.class);
/* 642:575 */     return localOutputStream;
/* 643:    */   }
/* 644:    */   
/* 645:    */   private org.omg.CORBA.portable.OutputStream updateACHPayeePrenoteStatus(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 646:    */     throws Throwable
/* 647:    */   {
/* 648:579 */     ACHPayeeInfo localACHPayeeInfo1 = (ACHPayeeInfo)paramInputStream.read_value(ACHPayeeInfo.class);
/* 649:580 */     ACHPayeeInfo localACHPayeeInfo2 = this.target.updateACHPayeePrenoteStatus(localACHPayeeInfo1);
/* 650:581 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 651:582 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 652:583 */     localOutputStream.write_value(localACHPayeeInfo2, ACHPayeeInfo.class);
/* 653:584 */     return localOutputStream;
/* 654:    */   }
/* 655:    */   
/* 656:    */   private org.omg.CORBA.portable.OutputStream addACHBatchTemplate(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 657:    */     throws Throwable
/* 658:    */   {
/* 659:588 */     ACHBatchTemplateInfo localACHBatchTemplateInfo1 = (ACHBatchTemplateInfo)paramInputStream.read_value(ACHBatchTemplateInfo.class);
/* 660:589 */     ACHBatchTemplateInfo localACHBatchTemplateInfo2 = this.target.addACHBatchTemplate(localACHBatchTemplateInfo1);
/* 661:590 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 662:591 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 663:592 */     localOutputStream.write_value(localACHBatchTemplateInfo2, ACHBatchTemplateInfo.class);
/* 664:593 */     return localOutputStream;
/* 665:    */   }
/* 666:    */   
/* 667:    */   private org.omg.CORBA.portable.OutputStream modACHBatchTemplate(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 668:    */     throws Throwable
/* 669:    */   {
/* 670:597 */     ACHBatchTemplateInfo localACHBatchTemplateInfo1 = (ACHBatchTemplateInfo)paramInputStream.read_value(ACHBatchTemplateInfo.class);
/* 671:598 */     ACHBatchTemplateInfo localACHBatchTemplateInfo2 = this.target.modACHBatchTemplate(localACHBatchTemplateInfo1);
/* 672:599 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 673:600 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 674:601 */     localOutputStream.write_value(localACHBatchTemplateInfo2, ACHBatchTemplateInfo.class);
/* 675:602 */     return localOutputStream;
/* 676:    */   }
/* 677:    */   
/* 678:    */   private org.omg.CORBA.portable.OutputStream deleteACHBatchTemplate(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 679:    */     throws Throwable
/* 680:    */   {
/* 681:606 */     ACHBatchTemplateInfo localACHBatchTemplateInfo1 = (ACHBatchTemplateInfo)paramInputStream.read_value(ACHBatchTemplateInfo.class);
/* 682:607 */     ACHBatchTemplateInfo localACHBatchTemplateInfo2 = this.target.deleteACHBatchTemplate(localACHBatchTemplateInfo1);
/* 683:608 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 684:609 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 685:610 */     localOutputStream.write_value(localACHBatchTemplateInfo2, ACHBatchTemplateInfo.class);
/* 686:611 */     return localOutputStream;
/* 687:    */   }
/* 688:    */   
/* 689:    */   private org.omg.CORBA.portable.OutputStream getACHBatchTemplate__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 690:    */     throws Throwable
/* 691:    */   {
/* 692:615 */     String str = (String)paramInputStream.read_value(String.class);
/* 693:616 */     ACHBatchTemplateInfo localACHBatchTemplateInfo = this.target.getACHBatchTemplate(str);
/* 694:617 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 695:618 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 696:619 */     localOutputStream.write_value(localACHBatchTemplateInfo, ACHBatchTemplateInfo.class);
/* 697:620 */     return localOutputStream;
/* 698:    */   }
/* 699:    */   
/* 700:    */   private org.omg.CORBA.portable.OutputStream getACHBatchTemplate__org_omg_boxedRMI_CORBA_seq1_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 701:    */     throws Throwable
/* 702:    */   {
/* 703:624 */     String[] arrayOfString = (String[])paramInputStream.read_value(new String[0].getClass());
/* 704:625 */     ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo = this.target.getACHBatchTemplate(arrayOfString);
/* 705:626 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 706:627 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 707:628 */     localOutputStream.write_value(cast_array(arrayOfACHBatchTemplateInfo), new ACHBatchTemplateInfo[0].getClass());
/* 708:629 */     return localOutputStream;
/* 709:    */   }
/* 710:    */   
/* 711:    */   private org.omg.CORBA.portable.OutputStream getACHBatchTemplateByCompany(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 712:    */     throws Throwable
/* 713:    */   {
/* 714:633 */     String str = (String)paramInputStream.read_value(String.class);
/* 715:634 */     ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo = this.target.getACHBatchTemplateByCompany(str);
/* 716:635 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 717:636 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 718:637 */     localOutputStream.write_value(cast_array(arrayOfACHBatchTemplateInfo), new ACHBatchTemplateInfo[0].getClass());
/* 719:638 */     return localOutputStream;
/* 720:    */   }
/* 721:    */   
/* 722:    */   private org.omg.CORBA.portable.OutputStream getACHBatchTemplateByGroup(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 723:    */     throws Throwable
/* 724:    */   {
/* 725:642 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 726:643 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 727:644 */     ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo = this.target.getACHBatchTemplateByGroup(str1, str2);
/* 728:645 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 729:646 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 730:647 */     localOutputStream.write_value(cast_array(arrayOfACHBatchTemplateInfo), new ACHBatchTemplateInfo[0].getClass());
/* 731:648 */     return localOutputStream;
/* 732:    */   }
/* 733:    */   
/* 734:    */   private org.omg.CORBA.portable.OutputStream addACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 735:    */     throws Throwable
/* 736:    */   {
/* 737:652 */     ACHBatchInfo localACHBatchInfo1 = (ACHBatchInfo)paramInputStream.read_value(ACHBatchInfo.class);
/* 738:653 */     ACHBatchInfo localACHBatchInfo2 = this.target.addACHBatch(localACHBatchInfo1);
/* 739:654 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 740:655 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 741:656 */     localOutputStream.write_value(localACHBatchInfo2, ACHBatchInfo.class);
/* 742:657 */     return localOutputStream;
/* 743:    */   }
/* 744:    */   
/* 745:    */   private org.omg.CORBA.portable.OutputStream modACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 746:    */     throws Throwable
/* 747:    */   {
/* 748:661 */     ACHBatchInfo localACHBatchInfo1 = (ACHBatchInfo)paramInputStream.read_value(ACHBatchInfo.class);
/* 749:662 */     ACHBatchInfo localACHBatchInfo2 = this.target.modACHBatch(localACHBatchInfo1);
/* 750:663 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 751:664 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 752:665 */     localOutputStream.write_value(localACHBatchInfo2, ACHBatchInfo.class);
/* 753:666 */     return localOutputStream;
/* 754:    */   }
/* 755:    */   
/* 756:    */   private org.omg.CORBA.portable.OutputStream canACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 757:    */     throws Throwable
/* 758:    */   {
/* 759:670 */     ACHBatchInfo localACHBatchInfo1 = (ACHBatchInfo)paramInputStream.read_value(ACHBatchInfo.class);
/* 760:671 */     ACHBatchInfo localACHBatchInfo2 = this.target.canACHBatch(localACHBatchInfo1);
/* 761:672 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 762:673 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 763:674 */     localOutputStream.write_value(localACHBatchInfo2, ACHBatchInfo.class);
/* 764:675 */     return localOutputStream;
/* 765:    */   }
/* 766:    */   
/* 767:    */   private org.omg.CORBA.portable.OutputStream getACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 768:    */     throws Throwable
/* 769:    */   {
/* 770:679 */     ACHBatchInfo localACHBatchInfo1 = (ACHBatchInfo)paramInputStream.read_value(ACHBatchInfo.class);
/* 771:680 */     ACHBatchInfo localACHBatchInfo2 = this.target.getACHBatch(localACHBatchInfo1);
/* 772:681 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 773:682 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 774:683 */     localOutputStream.write_value(localACHBatchInfo2, ACHBatchInfo.class);
/* 775:684 */     return localOutputStream;
/* 776:    */   }
/* 777:    */   
/* 778:    */   private org.omg.CORBA.portable.OutputStream getACHBatchHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 779:    */     throws Throwable
/* 780:    */   {
/* 781:688 */     ACHBatchHist localACHBatchHist1 = (ACHBatchHist)paramInputStream.read_value(ACHBatchHist.class);
/* 782:689 */     ACHBatchHist localACHBatchHist2 = this.target.getACHBatchHistory(localACHBatchHist1);
/* 783:690 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 784:691 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 785:692 */     localOutputStream.write_value(localACHBatchHist2, ACHBatchHist.class);
/* 786:693 */     return localOutputStream;
/* 787:    */   }
/* 788:    */   
/* 789:    */   private org.omg.CORBA.portable.OutputStream addACHFile(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 790:    */     throws Throwable
/* 791:    */   {
/* 792:697 */     ACHFileInfo localACHFileInfo1 = (ACHFileInfo)paramInputStream.read_value(ACHFileInfo.class);
/* 793:698 */     ACHFileInfo localACHFileInfo2 = this.target.addACHFile(localACHFileInfo1);
/* 794:699 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 795:700 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 796:701 */     localOutputStream.write_value(localACHFileInfo2, ACHFileInfo.class);
/* 797:702 */     return localOutputStream;
/* 798:    */   }
/* 799:    */   
/* 800:    */   private org.omg.CORBA.portable.OutputStream canACHFile(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 801:    */     throws Throwable
/* 802:    */   {
/* 803:706 */     ACHFileInfo localACHFileInfo1 = (ACHFileInfo)paramInputStream.read_value(ACHFileInfo.class);
/* 804:707 */     ACHFileInfo localACHFileInfo2 = this.target.canACHFile(localACHFileInfo1);
/* 805:708 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 806:709 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 807:710 */     localOutputStream.write_value(localACHFileInfo2, ACHFileInfo.class);
/* 808:711 */     return localOutputStream;
/* 809:    */   }
/* 810:    */   
/* 811:    */   private org.omg.CORBA.portable.OutputStream getACHFile(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 812:    */     throws Throwable
/* 813:    */   {
/* 814:715 */     ACHFileInfo localACHFileInfo1 = (ACHFileInfo)paramInputStream.read_value(ACHFileInfo.class);
/* 815:716 */     ACHFileInfo localACHFileInfo2 = this.target.getACHFile(localACHFileInfo1);
/* 816:717 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 817:718 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 818:719 */     localOutputStream.write_value(localACHFileInfo2, ACHFileInfo.class);
/* 819:720 */     return localOutputStream;
/* 820:    */   }
/* 821:    */   
/* 822:    */   private org.omg.CORBA.portable.OutputStream getACHFileHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 823:    */     throws Throwable
/* 824:    */   {
/* 825:724 */     ACHFileHist localACHFileHist1 = (ACHFileHist)paramInputStream.read_value(ACHFileHist.class);
/* 826:725 */     ACHFileHist localACHFileHist2 = this.target.getACHFileHistory(localACHFileHist1);
/* 827:726 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 828:727 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 829:728 */     localOutputStream.write_value(localACHFileHist2, ACHFileHist.class);
/* 830:729 */     return localOutputStream;
/* 831:    */   }
/* 832:    */   
/* 833:    */   private org.omg.CORBA.portable.OutputStream addRecACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 834:    */     throws Throwable
/* 835:    */   {
/* 836:733 */     RecACHBatchInfo localRecACHBatchInfo1 = (RecACHBatchInfo)paramInputStream.read_value(RecACHBatchInfo.class);
/* 837:734 */     RecACHBatchInfo localRecACHBatchInfo2 = this.target.addRecACHBatch(localRecACHBatchInfo1);
/* 838:735 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 839:736 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 840:737 */     localOutputStream.write_value(localRecACHBatchInfo2, RecACHBatchInfo.class);
/* 841:738 */     return localOutputStream;
/* 842:    */   }
/* 843:    */   
/* 844:    */   private org.omg.CORBA.portable.OutputStream modRecACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 845:    */     throws Throwable
/* 846:    */   {
/* 847:742 */     RecACHBatchInfo localRecACHBatchInfo1 = (RecACHBatchInfo)paramInputStream.read_value(RecACHBatchInfo.class);
/* 848:743 */     RecACHBatchInfo localRecACHBatchInfo2 = this.target.modRecACHBatch(localRecACHBatchInfo1);
/* 849:744 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 850:745 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 851:746 */     localOutputStream.write_value(localRecACHBatchInfo2, RecACHBatchInfo.class);
/* 852:747 */     return localOutputStream;
/* 853:    */   }
/* 854:    */   
/* 855:    */   private org.omg.CORBA.portable.OutputStream canRecACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 856:    */     throws Throwable
/* 857:    */   {
/* 858:751 */     RecACHBatchInfo localRecACHBatchInfo1 = (RecACHBatchInfo)paramInputStream.read_value(RecACHBatchInfo.class);
/* 859:752 */     RecACHBatchInfo localRecACHBatchInfo2 = this.target.canRecACHBatch(localRecACHBatchInfo1);
/* 860:753 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 861:754 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 862:755 */     localOutputStream.write_value(localRecACHBatchInfo2, RecACHBatchInfo.class);
/* 863:756 */     return localOutputStream;
/* 864:    */   }
/* 865:    */   
/* 866:    */   private org.omg.CORBA.portable.OutputStream getRecACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 867:    */     throws Throwable
/* 868:    */   {
/* 869:760 */     RecACHBatchInfo localRecACHBatchInfo1 = (RecACHBatchInfo)paramInputStream.read_value(RecACHBatchInfo.class);
/* 870:761 */     RecACHBatchInfo localRecACHBatchInfo2 = this.target.getRecACHBatch(localRecACHBatchInfo1);
/* 871:762 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 872:763 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 873:764 */     localOutputStream.write_value(localRecACHBatchInfo2, RecACHBatchInfo.class);
/* 874:765 */     return localOutputStream;
/* 875:    */   }
/* 876:    */   
/* 877:    */   private org.omg.CORBA.portable.OutputStream getRecACHBatchHistory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 878:    */     throws Throwable
/* 879:    */   {
/* 880:769 */     ACHBatchHist localACHBatchHist1 = (ACHBatchHist)paramInputStream.read_value(ACHBatchHist.class);
/* 881:770 */     ACHBatchHist localACHBatchHist2 = this.target.getRecACHBatchHistory(localACHBatchHist1);
/* 882:771 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 883:772 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 884:773 */     localOutputStream.write_value(localACHBatchHist2, ACHBatchHist.class);
/* 885:774 */     return localOutputStream;
/* 886:    */   }
/* 887:    */   
/* 888:    */   private org.omg.CORBA.portable.OutputStream addACHCompanyOffsetAccount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 889:    */     throws Throwable
/* 890:    */   {
/* 891:778 */     ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo1 = (ACHCompOffsetAcctInfo)paramInputStream.read_value(ACHCompOffsetAcctInfo.class);
/* 892:779 */     ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo2 = this.target.addACHCompanyOffsetAccount(localACHCompOffsetAcctInfo1);
/* 893:780 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 894:781 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 895:782 */     localOutputStream.write_value(localACHCompOffsetAcctInfo2, ACHCompOffsetAcctInfo.class);
/* 896:783 */     return localOutputStream;
/* 897:    */   }
/* 898:    */   
/* 899:    */   private org.omg.CORBA.portable.OutputStream modACHCompanyOffsetAccount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 900:    */     throws Throwable
/* 901:    */   {
/* 902:787 */     ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo1 = (ACHCompOffsetAcctInfo)paramInputStream.read_value(ACHCompOffsetAcctInfo.class);
/* 903:788 */     ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo2 = this.target.modACHCompanyOffsetAccount(localACHCompOffsetAcctInfo1);
/* 904:789 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 905:790 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 906:791 */     localOutputStream.write_value(localACHCompOffsetAcctInfo2, ACHCompOffsetAcctInfo.class);
/* 907:792 */     return localOutputStream;
/* 908:    */   }
/* 909:    */   
/* 910:    */   private org.omg.CORBA.portable.OutputStream canACHCompanyOffsetAccount(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 911:    */     throws Throwable
/* 912:    */   {
/* 913:796 */     ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo1 = (ACHCompOffsetAcctInfo)paramInputStream.read_value(ACHCompOffsetAcctInfo.class);
/* 914:797 */     ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo2 = this.target.canACHCompanyOffsetAccount(localACHCompOffsetAcctInfo1);
/* 915:798 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 916:799 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 917:800 */     localOutputStream.write_value(localACHCompOffsetAcctInfo2, ACHCompOffsetAcctInfo.class);
/* 918:801 */     return localOutputStream;
/* 919:    */   }
/* 920:    */   
/* 921:    */   private org.omg.CORBA.portable.OutputStream getACHCompanyOffsetAccountByAccountId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 922:    */     throws Throwable
/* 923:    */   {
/* 924:805 */     String str = (String)paramInputStream.read_value(String.class);
/* 925:806 */     ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = this.target.getACHCompanyOffsetAccountByAccountId(str);
/* 926:807 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 927:808 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 928:809 */     localOutputStream.write_value(localACHCompOffsetAcctInfo, ACHCompOffsetAcctInfo.class);
/* 929:810 */     return localOutputStream;
/* 930:    */   }
/* 931:    */   
/* 932:    */   private org.omg.CORBA.portable.OutputStream getACHCompanyOffsetAccountByCompId(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 933:    */     throws Throwable
/* 934:    */   {
/* 935:814 */     String str = (String)paramInputStream.read_value(String.class);
/* 936:815 */     ACHCompOffsetAcctInfo[] arrayOfACHCompOffsetAcctInfo = this.target.getACHCompanyOffsetAccountByCompId(str);
/* 937:816 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 938:817 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 939:818 */     localOutputStream.write_value(cast_array(arrayOfACHCompOffsetAcctInfo), new ACHCompOffsetAcctInfo[0].getClass());
/* 940:819 */     return localOutputStream;
/* 941:    */   }
/* 942:    */   
/* 943:    */   private org.omg.CORBA.portable.OutputStream exportACHBatch(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 944:    */     throws Throwable
/* 945:    */   {
/* 946:823 */     ACHBatchInfo localACHBatchInfo1 = (ACHBatchInfo)paramInputStream.read_value(ACHBatchInfo.class);
/* 947:    */     ACHBatchInfo localACHBatchInfo2;
/* 948:    */     try
/* 949:    */     {
/* 950:826 */       localACHBatchInfo2 = this.target.exportACHBatch(localACHBatchInfo1);
/* 951:    */     }
/* 952:    */     catch (FFSException localFFSException)
/* 953:    */     {
/* 954:828 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 955:829 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 956:830 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 957:831 */       localOutputStream2.write_string(str);
/* 958:832 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 959:833 */       return localOutputStream2;
/* 960:    */     }
/* 961:835 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 962:836 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 963:837 */     localOutputStream1.write_value(localACHBatchInfo2, ACHBatchInfo.class);
/* 964:838 */     return localOutputStream1;
/* 965:    */   }
/* 966:    */   
/* 967:    */   private org.omg.CORBA.portable.OutputStream getACHSameDayEffDateInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 968:    */     throws Throwable
/* 969:    */   {
/* 970:842 */     ACHSameDayEffDateInfo localACHSameDayEffDateInfo1 = (ACHSameDayEffDateInfo)paramInputStream.read_value(ACHSameDayEffDateInfo.class);
/* 971:    */     ACHSameDayEffDateInfo localACHSameDayEffDateInfo2;
/* 972:    */     try
/* 973:    */     {
/* 974:845 */       localACHSameDayEffDateInfo2 = this.target.getACHSameDayEffDateInfo(localACHSameDayEffDateInfo1);
/* 975:    */     }
/* 976:    */     catch (FFSException localFFSException)
/* 977:    */     {
/* 978:847 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 979:848 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 980:849 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 981:850 */       localOutputStream2.write_string(str);
/* 982:851 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 983:852 */       return localOutputStream2;
/* 984:    */     }
/* 985:854 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 986:855 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 987:856 */     localOutputStream1.write_value(localACHSameDayEffDateInfo2, ACHSameDayEffDateInfo.class);
/* 988:857 */     return localOutputStream1;
/* 989:    */   }
/* 990:    */   
/* 991:    */   private org.omg.CORBA.portable.OutputStream setACHSameDayEffDateInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 992:    */     throws Throwable
/* 993:    */   {
/* 994:861 */     ACHSameDayEffDateInfo localACHSameDayEffDateInfo1 = (ACHSameDayEffDateInfo)paramInputStream.read_value(ACHSameDayEffDateInfo.class);
/* 995:    */     ACHSameDayEffDateInfo localACHSameDayEffDateInfo2;
/* 996:    */     try
/* 997:    */     {
/* 998:864 */       localACHSameDayEffDateInfo2 = this.target.setACHSameDayEffDateInfo(localACHSameDayEffDateInfo1);
/* 999:    */     }
/* :00:    */     catch (FFSException localFFSException)
/* :01:    */     {
/* :02:866 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* :03:867 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* :04:868 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* :05:869 */       localOutputStream2.write_string(str);
/* :06:870 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* :07:871 */       return localOutputStream2;
/* :08:    */     }
/* :09:873 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* :10:874 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* :11:875 */     localOutputStream1.write_value(localACHSameDayEffDateInfo2, ACHSameDayEffDateInfo.class);
/* :12:876 */     return localOutputStream1;
/* :13:    */   }
/* :14:    */   
/* :15:    */   private org.omg.CORBA.portable.OutputStream getDefaultACHBatchEffectiveDate(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* :16:    */     throws Throwable
/* :17:    */   {
/* :18:880 */     String str1 = (String)paramInputStream.read_value(String.class);
/* :19:881 */     String str2 = (String)paramInputStream.read_value(String.class);
/* :20:    */     String str3;
/* :21:    */     try
/* :22:    */     {
/* :23:884 */       str3 = this.target.getDefaultACHBatchEffectiveDate(str1, str2);
/* :24:    */     }
/* :25:    */     catch (FFSException localFFSException)
/* :26:    */     {
/* :27:886 */       String str4 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* :28:887 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* :29:888 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* :30:889 */       localOutputStream2.write_string(str4);
/* :31:890 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* :32:891 */       return localOutputStream2;
/* :33:    */     }
/* :34:893 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* :35:894 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* :36:895 */     localOutputStream1.write_value(str3, String.class);
/* :37:896 */     return localOutputStream1;
/* :38:    */   }
/* :39:    */   
/* :40:    */   private org.omg.CORBA.portable.OutputStream getPagedACH(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* :41:    */     throws Throwable
/* :42:    */   {
/* :43:900 */     PagingInfo localPagingInfo1 = (PagingInfo)paramInputStream.read_value(PagingInfo.class);
/* :44:    */     PagingInfo localPagingInfo2;
/* :45:    */     try
/* :46:    */     {
/* :47:903 */       localPagingInfo2 = this.target.getPagedACH(localPagingInfo1);
/* :48:    */     }
/* :49:    */     catch (FFSException localFFSException)
/* :50:    */     {
/* :51:905 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* :52:906 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* :53:907 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* :54:908 */       localOutputStream2.write_string(str);
/* :55:909 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* :56:910 */       return localOutputStream2;
/* :57:    */     }
/* :58:912 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* :59:913 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* :60:914 */     localOutputStream1.write_value(localPagingInfo2, PagingInfo.class);
/* :61:915 */     return localOutputStream1;
/* :62:    */   }
/* :63:    */   
/* :64:    */   private org.omg.CORBA.portable.OutputStream getACHTotals(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* :65:    */     throws Throwable
/* :66:    */   {
/* :67:919 */     BPWExtdHist localBPWExtdHist1 = (BPWExtdHist)paramInputStream.read_value(BPWExtdHist.class);
/* :68:920 */     BPWExtdHist localBPWExtdHist2 = this.target.getACHTotals(localBPWExtdHist1);
/* :69:921 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* :70:922 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* :71:923 */     localOutputStream.write_value(localBPWExtdHist2, BPWExtdHist.class);
/* :72:924 */     return localOutputStream;
/* :73:    */   }
/* :74:    */   
/* :75:    */   private org.omg.CORBA.portable.OutputStream getACHParticipantTotals(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* :76:    */     throws Throwable
/* :77:    */   {
/* :78:928 */     BPWExtdHist localBPWExtdHist1 = (BPWExtdHist)paramInputStream.read_value(BPWExtdHist.class);
/* :79:929 */     BPWExtdHist localBPWExtdHist2 = this.target.getACHParticipantTotals(localBPWExtdHist1);
/* :80:930 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* :81:931 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* :82:932 */     localOutputStream.write_value(localBPWExtdHist2, BPWExtdHist.class);
/* :83:933 */     return localOutputStream;
/* :84:    */   }
/* :85:    */   
/* :86:    */   private Serializable cast_array(java.lang.Object paramObject)
/* :87:    */   {
/* :88:940 */     return (Serializable)paramObject;
/* :89:    */   }
/* :90:    */ }


/* Location:           D:\drops\jd\jars\Deployed_ACHBPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices._EJSRemoteStatelessACHBPWServices_e0156d61_Tie
 * JD-Core Version:    0.7.0.1
 */