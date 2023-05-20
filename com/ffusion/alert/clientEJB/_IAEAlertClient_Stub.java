/*   1:    */ package com.ffusion.alert.clientEJB;
/*   2:    */ 
/*   3:    */ import com.ffusion.alert.interfaces.AEApplicationInfo;
/*   4:    */ import com.ffusion.alert.interfaces.AEException;
/*   5:    */ import com.ffusion.alert.interfaces.AEScheduleInfo;
/*   6:    */ import com.ffusion.alert.interfaces.IAEAlertDefinition;
/*   7:    */ import com.ffusion.alert.interfaces.IAEAuditInfo;
/*   8:    */ import com.ffusion.alert.interfaces.IAEChannelInfo;
/*   9:    */ import com.ffusion.alert.interfaces.IAEDeliveryInfo;
/*  10:    */ import java.io.Serializable;
/*  11:    */ import java.rmi.RemoteException;
/*  12:    */ import java.rmi.UnexpectedException;
/*  13:    */ import java.util.Date;
/*  14:    */ import javax.ejb.EJBHome;
/*  15:    */ import javax.ejb.EJBObject;
/*  16:    */ import javax.ejb.Handle;
/*  17:    */ import javax.ejb.RemoveException;
/*  18:    */ import javax.rmi.CORBA.Stub;
/*  19:    */ import javax.rmi.CORBA.Util;
/*  20:    */ import org.omg.CORBA.SystemException;
/*  21:    */ import org.omg.CORBA.portable.ApplicationException;
/*  22:    */ import org.omg.CORBA.portable.ObjectImpl;
/*  23:    */ import org.omg.CORBA.portable.RemarshalException;
/*  24:    */ import org.omg.CORBA.portable.ServantObject;
/*  25:    */ 
/*  26:    */ public class _IAEAlertClient_Stub
/*  27:    */   extends Stub
/*  28:    */   implements IAEAlertClient
/*  29:    */ {
/*  30: 32 */   private static final String[] _type_ids = {
/*  31: 33 */     "RMI:com.ffusion.alert.clientEJB.IAEAlertClient:0000000000000000", 
/*  32: 34 */     "RMI:javax.ejb.EJBObject:0000000000000000" };
/*  33:    */   
/*  34:    */   public String[] _ids()
/*  35:    */   {
/*  36: 38 */     return _type_ids;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public EJBHome getEJBHome()
/*  40:    */     throws RemoteException
/*  41:    */   {
/*  42:    */     EJBHome localEJBHome1;
/*  43:    */     Object localObject5;
/*  44: 42 */     if (!Util.isLocal(this)) {
/*  45:    */       try
/*  46:    */       {
/*  47: 44 */         org.omg.CORBA.portable.InputStream localInputStream = null;
/*  48:    */         try
/*  49:    */         {
/*  50: 46 */           org.omg.CORBA.portable.OutputStream localOutputStream = _request("_get_EJBHome", true);
/*  51: 47 */           localInputStream = _invoke(localOutputStream);
/*  52: 48 */           return (EJBHome)localInputStream.read_Object(EJBHome.class);
/*  53:    */         }
/*  54:    */         catch (ApplicationException localApplicationException)
/*  55:    */         {
/*  56: 50 */           localInputStream = localApplicationException.getInputStream();
/*  57: 51 */           localObject5 = localInputStream.read_string();
/*  58: 52 */           throw new UnexpectedException((String)localObject5);
/*  59:    */         }
/*  60:    */         catch (RemarshalException localRemarshalException)
/*  61:    */         {
/*  62: 54 */           return getEJBHome();
/*  63:    */         }
/*  64:    */         finally
/*  65:    */         {
/*  66: 56 */           _releaseReply(localInputStream);
/*  67:    */         }
/*  68:    */       }
/*  69:    */       catch (SystemException localSystemException)
/*  70:    */       {
/*  71: 59 */         throw Util.mapSystemException(localSystemException);
/*  72:    */       }
/*  73:    */     }
/*  74: 62 */     ServantObject localServantObject = _servant_preinvoke("_get_EJBHome", EJBObject.class);
/*  75: 63 */     if (localServantObject == null) {
/*  76: 64 */       return getEJBHome();
/*  77:    */     }
/*  78:    */     try
/*  79:    */     {
/*  80: 67 */       EJBHome localEJBHome2 = ((EJBObject)localServantObject.servant).getEJBHome();
/*  81: 68 */       return (EJBHome)Util.copyObject(localEJBHome2, _orb());
/*  82:    */     }
/*  83:    */     catch (Throwable localThrowable)
/*  84:    */     {
/*  85: 70 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/*  86: 71 */       throw Util.wrapException((Throwable)localObject5);
/*  87:    */     }
/*  88:    */     finally
/*  89:    */     {
/*  90: 73 */       _servant_postinvoke(localServantObject);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Object getPrimaryKey()
/*  95:    */     throws RemoteException
/*  96:    */   {
/*  97:    */     Object localObject1;
/*  98:    */     Object localObject7;
/*  99: 79 */     if (!Util.isLocal(this)) {
/* 100:    */       try
/* 101:    */       {
/* 102: 81 */         org.omg.CORBA.portable.InputStream localInputStream = null;
/* 103:    */         try
/* 104:    */         {
/* 105: 83 */           org.omg.CORBA.portable.OutputStream localOutputStream = _request("_get_primaryKey", true);
/* 106: 84 */           localInputStream = _invoke(localOutputStream);
/* 107: 85 */           return Util.readAny(localInputStream);
/* 108:    */         }
/* 109:    */         catch (ApplicationException localApplicationException)
/* 110:    */         {
/* 111: 87 */           localInputStream = localApplicationException.getInputStream();
/* 112: 88 */           localObject7 = localInputStream.read_string();
/* 113: 89 */           throw new UnexpectedException((String)localObject7);
/* 114:    */         }
/* 115:    */         catch (RemarshalException localRemarshalException)
/* 116:    */         {
/* 117: 91 */           return getPrimaryKey();
/* 118:    */         }
/* 119:    */         finally
/* 120:    */         {
/* 121: 93 */           _releaseReply(localInputStream);
/* 122:    */         }
/* 123:    */       }
/* 124:    */       catch (SystemException localSystemException)
/* 125:    */       {
/* 126: 96 */         throw Util.mapSystemException(localSystemException);
/* 127:    */       }
/* 128:    */     }
/* 129: 99 */     ServantObject localServantObject = _servant_preinvoke("_get_primaryKey", EJBObject.class);
/* 130:100 */     if (localServantObject == null) {
/* 131:101 */       return getPrimaryKey();
/* 132:    */     }
/* 133:    */     try
/* 134:    */     {
/* 135:104 */       Object localObject6 = ((EJBObject)localServantObject.servant).getPrimaryKey();
/* 136:105 */       return Util.copyObject(localObject6, _orb());
/* 137:    */     }
/* 138:    */     catch (Throwable localThrowable)
/* 139:    */     {
/* 140:107 */       localObject7 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 141:108 */       throw Util.wrapException((Throwable)localObject7);
/* 142:    */     }
/* 143:    */     finally
/* 144:    */     {
/* 145:110 */       _servant_postinvoke(localServantObject);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void remove()
/* 150:    */     throws RemoteException, RemoveException
/* 151:    */   {
/* 152:    */     Object localObject5;
/* 153:116 */     if (!Util.isLocal(this)) {
/* 154:    */       try
/* 155:    */       {
/* 156:118 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 157:    */         try
/* 158:    */         {
/* 159:120 */           org.omg.CORBA.portable.OutputStream localOutputStream = _request("remove", true);
/* 160:121 */           _invoke(localOutputStream);
/* 161:122 */           return;
/* 162:    */         }
/* 163:    */         catch (ApplicationException localApplicationException)
/* 164:    */         {
/* 165:124 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 166:125 */           localObject5 = localInputStream.read_string();
/* 167:126 */           if (((String)localObject5).equals("IDL:javax/ejb/RemoveEx:1.0")) {
/* 168:127 */             throw ((RemoveException)localInputStream.read_value(RemoveException.class));
/* 169:    */           }
/* 170:129 */           throw new UnexpectedException((String)localObject5);
/* 171:    */         }
/* 172:    */         catch (RemarshalException localRemarshalException)
/* 173:    */         {
/* 174:131 */           remove();
/* 175:132 */           return;
/* 176:    */         }
/* 177:    */         finally
/* 178:    */         {
/* 179:134 */           _releaseReply(localInputStream);
/* 180:    */         }
/* 181:    */       }
/* 182:    */       catch (SystemException localSystemException)
/* 183:    */       {
/* 184:137 */         throw Util.mapSystemException(localSystemException);
/* 185:    */       }
/* 186:    */     }
/* 187:140 */     ServantObject localServantObject = _servant_preinvoke("remove", EJBObject.class);
/* 188:141 */     if (localServantObject == null)
/* 189:    */     {
/* 190:142 */       remove();
/* 191:143 */       return;
/* 192:    */     }
/* 193:    */     try
/* 194:    */     {
/* 195:146 */       ((EJBObject)localServantObject.servant).remove();
/* 196:147 */       return;
/* 197:    */     }
/* 198:    */     catch (Throwable localThrowable)
/* 199:    */     {
/* 200:149 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 201:150 */       if ((localObject5 instanceof RemoveException)) {
/* 202:151 */         throw ((RemoveException)localObject5);
/* 203:    */       }
/* 204:153 */       throw Util.wrapException((Throwable)localObject5);
/* 205:    */     }
/* 206:    */     finally
/* 207:    */     {
/* 208:155 */       _servant_postinvoke(localServantObject);
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Handle getHandle()
/* 213:    */     throws RemoteException
/* 214:    */   {
/* 215:    */     Handle localHandle1;
/* 216:    */     Object localObject5;
/* 217:161 */     if (!Util.isLocal(this)) {
/* 218:    */       try
/* 219:    */       {
/* 220:163 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 221:    */         try
/* 222:    */         {
/* 223:165 */           org.omg.CORBA.portable.OutputStream localOutputStream = _request("_get_handle", true);
/* 224:166 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 225:167 */           return (Handle)localInputStream.read_abstract_interface(Handle.class);
/* 226:    */         }
/* 227:    */         catch (ApplicationException localApplicationException)
/* 228:    */         {
/* 229:169 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 230:170 */           localObject5 = localInputStream.read_string();
/* 231:171 */           throw new UnexpectedException((String)localObject5);
/* 232:    */         }
/* 233:    */         catch (RemarshalException localRemarshalException)
/* 234:    */         {
/* 235:173 */           return getHandle();
/* 236:    */         }
/* 237:    */         finally
/* 238:    */         {
/* 239:175 */           _releaseReply(localInputStream);
/* 240:    */         }
/* 241:    */       }
/* 242:    */       catch (SystemException localSystemException)
/* 243:    */       {
/* 244:178 */         throw Util.mapSystemException(localSystemException);
/* 245:    */       }
/* 246:    */     }
/* 247:181 */     ServantObject localServantObject = _servant_preinvoke("_get_handle", EJBObject.class);
/* 248:182 */     if (localServantObject == null) {
/* 249:183 */       return getHandle();
/* 250:    */     }
/* 251:    */     try
/* 252:    */     {
/* 253:186 */       Handle localHandle2 = ((EJBObject)localServantObject.servant).getHandle();
/* 254:187 */       return (Handle)Util.copyObject(localHandle2, _orb());
/* 255:    */     }
/* 256:    */     catch (Throwable localThrowable)
/* 257:    */     {
/* 258:189 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 259:190 */       throw Util.wrapException((Throwable)localObject5);
/* 260:    */     }
/* 261:    */     finally
/* 262:    */     {
/* 263:192 */       _servant_postinvoke(localServantObject);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isIdentical(EJBObject paramEJBObject)
/* 268:    */     throws RemoteException
/* 269:    */   {
/* 270:    */     boolean bool;
/* 271:    */     Object localObject5;
/* 272:198 */     if (!Util.isLocal(this)) {
/* 273:    */       try
/* 274:    */       {
/* 275:200 */         org.omg.CORBA.portable.InputStream localInputStream = null;
/* 276:    */         try
/* 277:    */         {
/* 278:202 */           org.omg.CORBA.portable.OutputStream localOutputStream = _request("isIdentical", true);
/* 279:203 */           Util.writeRemoteObject(localOutputStream, paramEJBObject);
/* 280:204 */           localInputStream = _invoke(localOutputStream);
/* 281:205 */           return localInputStream.read_boolean();
/* 282:    */         }
/* 283:    */         catch (ApplicationException localApplicationException)
/* 284:    */         {
/* 285:207 */           localInputStream = localApplicationException.getInputStream();
/* 286:208 */           localObject5 = localInputStream.read_string();
/* 287:209 */           throw new UnexpectedException((String)localObject5);
/* 288:    */         }
/* 289:    */         catch (RemarshalException localRemarshalException)
/* 290:    */         {
/* 291:211 */           return isIdentical(paramEJBObject);
/* 292:    */         }
/* 293:    */         finally
/* 294:    */         {
/* 295:213 */           _releaseReply(localInputStream);
/* 296:    */         }
/* 297:    */       }
/* 298:    */       catch (SystemException localSystemException)
/* 299:    */       {
/* 300:216 */         throw Util.mapSystemException(localSystemException);
/* 301:    */       }
/* 302:    */     }
/* 303:219 */     ServantObject localServantObject = _servant_preinvoke("isIdentical", EJBObject.class);
/* 304:220 */     if (localServantObject == null) {
/* 305:221 */       return isIdentical(paramEJBObject);
/* 306:    */     }
/* 307:    */     try
/* 308:    */     {
/* 309:224 */       EJBObject localEJBObject = (EJBObject)Util.copyObject(paramEJBObject, _orb());
/* 310:225 */       return ((EJBObject)localServantObject.servant).isIdentical(localEJBObject);
/* 311:    */     }
/* 312:    */     catch (Throwable localThrowable)
/* 313:    */     {
/* 314:227 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 315:228 */       throw Util.wrapException((Throwable)localObject5);
/* 316:    */     }
/* 317:    */     finally
/* 318:    */     {
/* 319:230 */       _servant_postinvoke(localServantObject);
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString1, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
/* 324:    */     throws RemoteException, AEException
/* 325:    */   {
/* 326:    */     int i;
/* 327:    */     Object localObject5;
/* 328:236 */     if (!Util.isLocal(this)) {
/* 329:    */       try
/* 330:    */       {
/* 331:238 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 332:    */         try
/* 333:    */         {
/* 334:240 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 335:241 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 336:242 */             _request("createAlert__com_ffusion_alert_interfaces_AEApplicationInfo__CORBA_WStringValue__long__long__com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__CORBA_WStringValue", true);
/* 337:243 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 338:244 */           localOutputStream.write_value(paramString1, String.class);
/* 339:245 */           localOutputStream.write_long(paramInt1);
/* 340:246 */           localOutputStream.write_long(paramInt2);
/* 341:247 */           localOutputStream.write_value(paramAEScheduleInfo, AEScheduleInfo.class);
/* 342:248 */           localOutputStream.write_value(cast_array(paramArrayOfIAEDeliveryInfo), new IAEDeliveryInfo[0].getClass());
/* 343:249 */           localOutputStream.write_value(paramString2, String.class);
/* 344:250 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 345:251 */           return localInputStream.read_long();
/* 346:    */         }
/* 347:    */         catch (ApplicationException localApplicationException)
/* 348:    */         {
/* 349:253 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 350:254 */           localObject5 = localInputStream.read_string();
/* 351:255 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 352:256 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 353:    */           }
/* 354:258 */           throw new UnexpectedException((String)localObject5);
/* 355:    */         }
/* 356:    */         catch (RemarshalException localRemarshalException)
/* 357:    */         {
/* 358:260 */           return createAlert(paramAEApplicationInfo, paramString1, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
/* 359:    */         }
/* 360:    */         finally
/* 361:    */         {
/* 362:262 */           _releaseReply(localInputStream);
/* 363:    */         }
/* 364:    */       }
/* 365:    */       catch (SystemException localSystemException)
/* 366:    */       {
/* 367:265 */         throw Util.mapSystemException(localSystemException);
/* 368:    */       }
/* 369:    */     }
/* 370:268 */     ServantObject localServantObject = _servant_preinvoke("createAlert__com_ffusion_alert_interfaces_AEApplicationInfo__CORBA_WStringValue__long__long__com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__CORBA_WStringValue", IAEAlertClient.class);
/* 371:269 */     if (localServantObject == null) {
/* 372:270 */       return createAlert(paramAEApplicationInfo, paramString1, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
/* 373:    */     }
/* 374:    */     try
/* 375:    */     {
/* 376:273 */       Object[] arrayOfObject = Util.copyObjects(new Object[] { paramAEApplicationInfo, paramString1, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2 }, _orb());
/* 377:274 */       localObject5 = (AEApplicationInfo)arrayOfObject[0];
/* 378:275 */       String str1 = (String)arrayOfObject[1];
/* 379:276 */       AEScheduleInfo localAEScheduleInfo = (AEScheduleInfo)arrayOfObject[2];
/* 380:277 */       IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = (IAEDeliveryInfo[])arrayOfObject[3];
/* 381:278 */       String str2 = (String)arrayOfObject[4];
/* 382:279 */       return ((IAEAlertClient)localServantObject.servant).createAlert((AEApplicationInfo)localObject5, str1, paramInt1, paramInt2, localAEScheduleInfo, arrayOfIAEDeliveryInfo, str2);
/* 383:    */     }
/* 384:    */     catch (Throwable localThrowable)
/* 385:    */     {
/* 386:281 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 387:282 */       if ((localObject5 instanceof AEException)) {
/* 388:283 */         throw ((AEException)localObject5);
/* 389:    */       }
/* 390:285 */       throw Util.wrapException((Throwable)localObject5);
/* 391:    */     }
/* 392:    */     finally
/* 393:    */     {
/* 394:287 */       _servant_postinvoke(localServantObject);
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   public int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
/* 399:    */     throws RemoteException, AEException
/* 400:    */   {
/* 401:    */     int i;
/* 402:    */     Object localObject5;
/* 403:293 */     if (!Util.isLocal(this)) {
/* 404:    */       try
/* 405:    */       {
/* 406:295 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 407:    */         try
/* 408:    */         {
/* 409:297 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 410:298 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 411:299 */             _request("createAlert__com_ffusion_alert_interfaces_AEApplicationInfo__CORBA_WStringValue__long__long__com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__org_omg_boxedRMI_seq1_octet", true);
/* 412:300 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 413:301 */           localOutputStream.write_value(paramString, String.class);
/* 414:302 */           localOutputStream.write_long(paramInt1);
/* 415:303 */           localOutputStream.write_long(paramInt2);
/* 416:304 */           localOutputStream.write_value(paramAEScheduleInfo, AEScheduleInfo.class);
/* 417:305 */           localOutputStream.write_value(cast_array(paramArrayOfIAEDeliveryInfo), new IAEDeliveryInfo[0].getClass());
/* 418:306 */           localOutputStream.write_value(cast_array(paramArrayOfByte), new byte[0].getClass());
/* 419:307 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 420:308 */           return localInputStream.read_long();
/* 421:    */         }
/* 422:    */         catch (ApplicationException localApplicationException)
/* 423:    */         {
/* 424:310 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 425:311 */           localObject5 = localInputStream.read_string();
/* 426:312 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 427:313 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 428:    */           }
/* 429:315 */           throw new UnexpectedException((String)localObject5);
/* 430:    */         }
/* 431:    */         catch (RemarshalException localRemarshalException)
/* 432:    */         {
/* 433:317 */           return createAlert(paramAEApplicationInfo, paramString, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
/* 434:    */         }
/* 435:    */         finally
/* 436:    */         {
/* 437:319 */           _releaseReply(localInputStream);
/* 438:    */         }
/* 439:    */       }
/* 440:    */       catch (SystemException localSystemException)
/* 441:    */       {
/* 442:322 */         throw Util.mapSystemException(localSystemException);
/* 443:    */       }
/* 444:    */     }
/* 445:325 */     ServantObject localServantObject = _servant_preinvoke("createAlert__com_ffusion_alert_interfaces_AEApplicationInfo__CORBA_WStringValue__long__long__com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__org_omg_boxedRMI_seq1_octet", IAEAlertClient.class);
/* 446:326 */     if (localServantObject == null) {
/* 447:327 */       return createAlert(paramAEApplicationInfo, paramString, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
/* 448:    */     }
/* 449:    */     try
/* 450:    */     {
/* 451:330 */       Object[] arrayOfObject = Util.copyObjects(new Object[] { paramAEApplicationInfo, paramString, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte }, _orb());
/* 452:331 */       localObject5 = (AEApplicationInfo)arrayOfObject[0];
/* 453:332 */       String str = (String)arrayOfObject[1];
/* 454:333 */       AEScheduleInfo localAEScheduleInfo = (AEScheduleInfo)arrayOfObject[2];
/* 455:334 */       IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = (IAEDeliveryInfo[])arrayOfObject[3];
/* 456:335 */       byte[] arrayOfByte = (byte[])arrayOfObject[4];
/* 457:336 */       return ((IAEAlertClient)localServantObject.servant).createAlert((AEApplicationInfo)localObject5, str, paramInt1, paramInt2, localAEScheduleInfo, arrayOfIAEDeliveryInfo, arrayOfByte);
/* 458:    */     }
/* 459:    */     catch (Throwable localThrowable)
/* 460:    */     {
/* 461:338 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 462:339 */       if ((localObject5 instanceof AEException)) {
/* 463:340 */         throw ((AEException)localObject5);
/* 464:    */       }
/* 465:342 */       throw Util.wrapException((Throwable)localObject5);
/* 466:    */     }
/* 467:    */     finally
/* 468:    */     {
/* 469:344 */       _servant_postinvoke(localServantObject);
/* 470:    */     }
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void cancelAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
/* 474:    */     throws RemoteException, AEException
/* 475:    */   {
/* 476:    */     Object localObject5;
/* 477:350 */     if (!Util.isLocal(this)) {
/* 478:    */       try
/* 479:    */       {
/* 480:352 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 481:    */         try
/* 482:    */         {
/* 483:354 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 484:355 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 485:356 */             _request("cancelAlert", true);
/* 486:357 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 487:358 */           localOutputStream.write_long(paramInt);
/* 488:359 */           _invoke(localOutputStream);
/* 489:360 */           return;
/* 490:    */         }
/* 491:    */         catch (ApplicationException localApplicationException)
/* 492:    */         {
/* 493:362 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 494:363 */           localObject5 = localInputStream.read_string();
/* 495:364 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 496:365 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 497:    */           }
/* 498:367 */           throw new UnexpectedException((String)localObject5);
/* 499:    */         }
/* 500:    */         catch (RemarshalException localRemarshalException)
/* 501:    */         {
/* 502:369 */           cancelAlert(paramAEApplicationInfo, paramInt);
/* 503:370 */           return;
/* 504:    */         }
/* 505:    */         finally
/* 506:    */         {
/* 507:372 */           _releaseReply(localInputStream);
/* 508:    */         }
/* 509:    */       }
/* 510:    */       catch (SystemException localSystemException)
/* 511:    */       {
/* 512:375 */         throw Util.mapSystemException(localSystemException);
/* 513:    */       }
/* 514:    */     }
/* 515:378 */     ServantObject localServantObject = _servant_preinvoke("cancelAlert", IAEAlertClient.class);
/* 516:379 */     if (localServantObject == null)
/* 517:    */     {
/* 518:380 */       cancelAlert(paramAEApplicationInfo, paramInt);
/* 519:381 */       return;
/* 520:    */     }
/* 521:    */     try
/* 522:    */     {
/* 523:384 */       AEApplicationInfo localAEApplicationInfo = (AEApplicationInfo)Util.copyObject(paramAEApplicationInfo, _orb());
/* 524:385 */       ((IAEAlertClient)localServantObject.servant).cancelAlert(localAEApplicationInfo, paramInt);
/* 525:386 */       return;
/* 526:    */     }
/* 527:    */     catch (Throwable localThrowable)
/* 528:    */     {
/* 529:388 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 530:389 */       if ((localObject5 instanceof AEException)) {
/* 531:390 */         throw ((AEException)localObject5);
/* 532:    */       }
/* 533:392 */       throw Util.wrapException((Throwable)localObject5);
/* 534:    */     }
/* 535:    */     finally
/* 536:    */     {
/* 537:394 */       _servant_postinvoke(localServantObject);
/* 538:    */     }
/* 539:    */   }
/* 540:    */   
/* 541:    */   public IAEAlertDefinition getAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
/* 542:    */     throws RemoteException, AEException
/* 543:    */   {
/* 544:    */     IAEAlertDefinition localIAEAlertDefinition;
/* 545:    */     Object localObject5;
/* 546:400 */     if (!Util.isLocal(this)) {
/* 547:    */       try
/* 548:    */       {
/* 549:402 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 550:    */         try
/* 551:    */         {
/* 552:404 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 553:405 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 554:406 */             _request("getAlert", true);
/* 555:407 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 556:408 */           localOutputStream.write_long(paramInt);
/* 557:409 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 558:410 */           return (IAEAlertDefinition)localInputStream.read_value(IAEAlertDefinition.class);
/* 559:    */         }
/* 560:    */         catch (ApplicationException localApplicationException)
/* 561:    */         {
/* 562:412 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 563:413 */           localObject5 = localInputStream.read_string();
/* 564:414 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 565:415 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 566:    */           }
/* 567:417 */           throw new UnexpectedException((String)localObject5);
/* 568:    */         }
/* 569:    */         catch (RemarshalException localRemarshalException)
/* 570:    */         {
/* 571:419 */           return getAlert(paramAEApplicationInfo, paramInt);
/* 572:    */         }
/* 573:    */         finally
/* 574:    */         {
/* 575:421 */           _releaseReply(localInputStream);
/* 576:    */         }
/* 577:    */       }
/* 578:    */       catch (SystemException localSystemException)
/* 579:    */       {
/* 580:424 */         throw Util.mapSystemException(localSystemException);
/* 581:    */       }
/* 582:    */     }
/* 583:427 */     ServantObject localServantObject = _servant_preinvoke("getAlert", IAEAlertClient.class);
/* 584:428 */     if (localServantObject == null) {
/* 585:429 */       return getAlert(paramAEApplicationInfo, paramInt);
/* 586:    */     }
/* 587:    */     try
/* 588:    */     {
/* 589:432 */       AEApplicationInfo localAEApplicationInfo = (AEApplicationInfo)Util.copyObject(paramAEApplicationInfo, _orb());
/* 590:433 */       localObject5 = ((IAEAlertClient)localServantObject.servant).getAlert(localAEApplicationInfo, paramInt);
/* 591:434 */       return (IAEAlertDefinition)Util.copyObject(localObject5, _orb());
/* 592:    */     }
/* 593:    */     catch (Throwable localThrowable)
/* 594:    */     {
/* 595:436 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 596:437 */       if ((localObject5 instanceof AEException)) {
/* 597:438 */         throw ((AEException)localObject5);
/* 598:    */       }
/* 599:440 */       throw Util.wrapException((Throwable)localObject5);
/* 600:    */     }
/* 601:    */     finally
/* 602:    */     {
/* 603:442 */       _servant_postinvoke(localServantObject);
/* 604:    */     }
/* 605:    */   }
/* 606:    */   
/* 607:    */   public IAEAlertDefinition[] getUserAlerts(AEApplicationInfo paramAEApplicationInfo, String paramString, boolean paramBoolean)
/* 608:    */     throws RemoteException, AEException
/* 609:    */   {
/* 610:    */     IAEAlertDefinition[] arrayOfIAEAlertDefinition1;
/* 611:    */     Object localObject5;
/* 612:448 */     if (!Util.isLocal(this)) {
/* 613:    */       try
/* 614:    */       {
/* 615:450 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 616:    */         try
/* 617:    */         {
/* 618:452 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 619:453 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 620:454 */             _request("getUserAlerts", true);
/* 621:455 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 622:456 */           localOutputStream.write_value(paramString, String.class);
/* 623:457 */           localOutputStream.write_boolean(paramBoolean);
/* 624:458 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 625:459 */           return (IAEAlertDefinition[])localInputStream.read_value(new IAEAlertDefinition[0].getClass());
/* 626:    */         }
/* 627:    */         catch (ApplicationException localApplicationException)
/* 628:    */         {
/* 629:461 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 630:462 */           localObject5 = localInputStream.read_string();
/* 631:463 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 632:464 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 633:    */           }
/* 634:466 */           throw new UnexpectedException((String)localObject5);
/* 635:    */         }
/* 636:    */         catch (RemarshalException localRemarshalException)
/* 637:    */         {
/* 638:468 */           return getUserAlerts(paramAEApplicationInfo, paramString, paramBoolean);
/* 639:    */         }
/* 640:    */         finally
/* 641:    */         {
/* 642:470 */           _releaseReply(localInputStream);
/* 643:    */         }
/* 644:    */       }
/* 645:    */       catch (SystemException localSystemException)
/* 646:    */       {
/* 647:473 */         throw Util.mapSystemException(localSystemException);
/* 648:    */       }
/* 649:    */     }
/* 650:476 */     ServantObject localServantObject = _servant_preinvoke("getUserAlerts", IAEAlertClient.class);
/* 651:477 */     if (localServantObject == null) {
/* 652:478 */       return getUserAlerts(paramAEApplicationInfo, paramString, paramBoolean);
/* 653:    */     }
/* 654:    */     try
/* 655:    */     {
/* 656:481 */       Object[] arrayOfObject = Util.copyObjects(new Object[] { paramAEApplicationInfo, paramString }, _orb());
/* 657:482 */       localObject5 = (AEApplicationInfo)arrayOfObject[0];
/* 658:483 */       String str = (String)arrayOfObject[1];
/* 659:484 */       IAEAlertDefinition[] arrayOfIAEAlertDefinition2 = ((IAEAlertClient)localServantObject.servant).getUserAlerts((AEApplicationInfo)localObject5, str, paramBoolean);
/* 660:485 */       return (IAEAlertDefinition[])Util.copyObject(arrayOfIAEAlertDefinition2, _orb());
/* 661:    */     }
/* 662:    */     catch (Throwable localThrowable)
/* 663:    */     {
/* 664:487 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 665:488 */       if ((localObject5 instanceof AEException)) {
/* 666:489 */         throw ((AEException)localObject5);
/* 667:    */       }
/* 668:491 */       throw Util.wrapException((Throwable)localObject5);
/* 669:    */     }
/* 670:    */     finally
/* 671:    */     {
/* 672:493 */       _servant_postinvoke(localServantObject);
/* 673:    */     }
/* 674:    */   }
/* 675:    */   
/* 676:    */   public IAEAlertDefinition[] getAppAlerts(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean)
/* 677:    */     throws RemoteException, AEException
/* 678:    */   {
/* 679:    */     IAEAlertDefinition[] arrayOfIAEAlertDefinition;
/* 680:    */     Object localObject5;
/* 681:499 */     if (!Util.isLocal(this)) {
/* 682:    */       try
/* 683:    */       {
/* 684:501 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 685:    */         try
/* 686:    */         {
/* 687:503 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 688:504 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 689:505 */             _request("getAppAlerts", true);
/* 690:506 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 691:507 */           localOutputStream.write_boolean(paramBoolean);
/* 692:508 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 693:509 */           return (IAEAlertDefinition[])localInputStream.read_value(new IAEAlertDefinition[0].getClass());
/* 694:    */         }
/* 695:    */         catch (ApplicationException localApplicationException)
/* 696:    */         {
/* 697:511 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 698:512 */           localObject5 = localInputStream.read_string();
/* 699:513 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 700:514 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 701:    */           }
/* 702:516 */           throw new UnexpectedException((String)localObject5);
/* 703:    */         }
/* 704:    */         catch (RemarshalException localRemarshalException)
/* 705:    */         {
/* 706:518 */           return getAppAlerts(paramAEApplicationInfo, paramBoolean);
/* 707:    */         }
/* 708:    */         finally
/* 709:    */         {
/* 710:520 */           _releaseReply(localInputStream);
/* 711:    */         }
/* 712:    */       }
/* 713:    */       catch (SystemException localSystemException)
/* 714:    */       {
/* 715:523 */         throw Util.mapSystemException(localSystemException);
/* 716:    */       }
/* 717:    */     }
/* 718:526 */     ServantObject localServantObject = _servant_preinvoke("getAppAlerts", IAEAlertClient.class);
/* 719:527 */     if (localServantObject == null) {
/* 720:528 */       return getAppAlerts(paramAEApplicationInfo, paramBoolean);
/* 721:    */     }
/* 722:    */     try
/* 723:    */     {
/* 724:531 */       AEApplicationInfo localAEApplicationInfo = (AEApplicationInfo)Util.copyObject(paramAEApplicationInfo, _orb());
/* 725:532 */       localObject5 = ((IAEAlertClient)localServantObject.servant).getAppAlerts(localAEApplicationInfo, paramBoolean);
/* 726:533 */       return (IAEAlertDefinition[])Util.copyObject(localObject5, _orb());
/* 727:    */     }
/* 728:    */     catch (Throwable localThrowable)
/* 729:    */     {
/* 730:535 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 731:536 */       if ((localObject5 instanceof AEException)) {
/* 732:537 */         throw ((AEException)localObject5);
/* 733:    */       }
/* 734:539 */       throw Util.wrapException((Throwable)localObject5);
/* 735:    */     }
/* 736:    */     finally
/* 737:    */     {
/* 738:541 */       _servant_postinvoke(localServantObject);
/* 739:    */     }
/* 740:    */   }
/* 741:    */   
/* 742:    */   public IAEAlertDefinition[] getAppAlertsForChannelPaged(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean, String paramString, int paramInt1, int paramInt2)
/* 743:    */     throws RemoteException, AEException
/* 744:    */   {
/* 745:    */     IAEAlertDefinition[] arrayOfIAEAlertDefinition1;
/* 746:    */     Object localObject5;
/* 747:547 */     if (!Util.isLocal(this)) {
/* 748:    */       try
/* 749:    */       {
/* 750:549 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 751:    */         try
/* 752:    */         {
/* 753:551 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 754:552 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 755:553 */             _request("getAppAlertsForChannelPaged", true);
/* 756:554 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 757:555 */           localOutputStream.write_boolean(paramBoolean);
/* 758:556 */           localOutputStream.write_value(paramString, String.class);
/* 759:557 */           localOutputStream.write_long(paramInt1);
/* 760:558 */           localOutputStream.write_long(paramInt2);
/* 761:559 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 762:560 */           return (IAEAlertDefinition[])localInputStream.read_value(new IAEAlertDefinition[0].getClass());
/* 763:    */         }
/* 764:    */         catch (ApplicationException localApplicationException)
/* 765:    */         {
/* 766:562 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 767:563 */           localObject5 = localInputStream.read_string();
/* 768:564 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 769:565 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 770:    */           }
/* 771:567 */           throw new UnexpectedException((String)localObject5);
/* 772:    */         }
/* 773:    */         catch (RemarshalException localRemarshalException)
/* 774:    */         {
/* 775:569 */           return getAppAlertsForChannelPaged(paramAEApplicationInfo, paramBoolean, paramString, paramInt1, paramInt2);
/* 776:    */         }
/* 777:    */         finally
/* 778:    */         {
/* 779:571 */           _releaseReply(localInputStream);
/* 780:    */         }
/* 781:    */       }
/* 782:    */       catch (SystemException localSystemException)
/* 783:    */       {
/* 784:574 */         throw Util.mapSystemException(localSystemException);
/* 785:    */       }
/* 786:    */     }
/* 787:577 */     ServantObject localServantObject = _servant_preinvoke("getAppAlertsForChannelPaged", IAEAlertClient.class);
/* 788:578 */     if (localServantObject == null) {
/* 789:579 */       return getAppAlertsForChannelPaged(paramAEApplicationInfo, paramBoolean, paramString, paramInt1, paramInt2);
/* 790:    */     }
/* 791:    */     try
/* 792:    */     {
/* 793:582 */       Object[] arrayOfObject = Util.copyObjects(new Object[] { paramAEApplicationInfo, paramString }, _orb());
/* 794:583 */       localObject5 = (AEApplicationInfo)arrayOfObject[0];
/* 795:584 */       String str = (String)arrayOfObject[1];
/* 796:585 */       IAEAlertDefinition[] arrayOfIAEAlertDefinition2 = ((IAEAlertClient)localServantObject.servant).getAppAlertsForChannelPaged((AEApplicationInfo)localObject5, paramBoolean, str, paramInt1, paramInt2);
/* 797:586 */       return (IAEAlertDefinition[])Util.copyObject(arrayOfIAEAlertDefinition2, _orb());
/* 798:    */     }
/* 799:    */     catch (Throwable localThrowable)
/* 800:    */     {
/* 801:588 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 802:589 */       if ((localObject5 instanceof AEException)) {
/* 803:590 */         throw ((AEException)localObject5);
/* 804:    */       }
/* 805:592 */       throw Util.wrapException((Throwable)localObject5);
/* 806:    */     }
/* 807:    */     finally
/* 808:    */     {
/* 809:594 */       _servant_postinvoke(localServantObject);
/* 810:    */     }
/* 811:    */   }
/* 812:    */   
/* 813:    */   public IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
/* 814:    */     throws RemoteException, AEException
/* 815:    */   {
/* 816:    */     IAEAlertDefinition localIAEAlertDefinition1;
/* 817:    */     Object localObject5;
/* 818:600 */     if (!Util.isLocal(this)) {
/* 819:    */       try
/* 820:    */       {
/* 821:602 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 822:    */         try
/* 823:    */         {
/* 824:604 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 825:605 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 826:606 */             _request("updateAlert__com_ffusion_alert_interfaces_AEApplicationInfo__long__CORBA_WStringValue__long__long__com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__CORBA_WStringValue", true);
/* 827:607 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 828:608 */           localOutputStream.write_long(paramInt1);
/* 829:609 */           localOutputStream.write_value(paramString1, String.class);
/* 830:610 */           localOutputStream.write_long(paramInt2);
/* 831:611 */           localOutputStream.write_long(paramInt3);
/* 832:612 */           localOutputStream.write_value(paramAEScheduleInfo, AEScheduleInfo.class);
/* 833:613 */           localOutputStream.write_value(cast_array(paramArrayOfIAEDeliveryInfo), new IAEDeliveryInfo[0].getClass());
/* 834:614 */           localOutputStream.write_value(paramString2, String.class);
/* 835:615 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 836:616 */           return (IAEAlertDefinition)localInputStream.read_value(IAEAlertDefinition.class);
/* 837:    */         }
/* 838:    */         catch (ApplicationException localApplicationException)
/* 839:    */         {
/* 840:618 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 841:619 */           localObject5 = localInputStream.read_string();
/* 842:620 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 843:621 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 844:    */           }
/* 845:623 */           throw new UnexpectedException((String)localObject5);
/* 846:    */         }
/* 847:    */         catch (RemarshalException localRemarshalException)
/* 848:    */         {
/* 849:625 */           return updateAlert(paramAEApplicationInfo, paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
/* 850:    */         }
/* 851:    */         finally
/* 852:    */         {
/* 853:627 */           _releaseReply(localInputStream);
/* 854:    */         }
/* 855:    */       }
/* 856:    */       catch (SystemException localSystemException)
/* 857:    */       {
/* 858:630 */         throw Util.mapSystemException(localSystemException);
/* 859:    */       }
/* 860:    */     }
/* 861:633 */     ServantObject localServantObject = _servant_preinvoke("updateAlert__com_ffusion_alert_interfaces_AEApplicationInfo__long__CORBA_WStringValue__long__long__com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__CORBA_WStringValue", IAEAlertClient.class);
/* 862:634 */     if (localServantObject == null) {
/* 863:635 */       return updateAlert(paramAEApplicationInfo, paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
/* 864:    */     }
/* 865:    */     try
/* 866:    */     {
/* 867:638 */       Object[] arrayOfObject = Util.copyObjects(new Object[] { paramAEApplicationInfo, paramString1, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2 }, _orb());
/* 868:639 */       localObject5 = (AEApplicationInfo)arrayOfObject[0];
/* 869:640 */       String str1 = (String)arrayOfObject[1];
/* 870:641 */       AEScheduleInfo localAEScheduleInfo = (AEScheduleInfo)arrayOfObject[2];
/* 871:642 */       IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = (IAEDeliveryInfo[])arrayOfObject[3];
/* 872:643 */       String str2 = (String)arrayOfObject[4];
/* 873:644 */       IAEAlertDefinition localIAEAlertDefinition2 = ((IAEAlertClient)localServantObject.servant).updateAlert((AEApplicationInfo)localObject5, paramInt1, str1, paramInt2, paramInt3, localAEScheduleInfo, arrayOfIAEDeliveryInfo, str2);
/* 874:645 */       return (IAEAlertDefinition)Util.copyObject(localIAEAlertDefinition2, _orb());
/* 875:    */     }
/* 876:    */     catch (Throwable localThrowable)
/* 877:    */     {
/* 878:647 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 879:648 */       if ((localObject5 instanceof AEException)) {
/* 880:649 */         throw ((AEException)localObject5);
/* 881:    */       }
/* 882:651 */       throw Util.wrapException((Throwable)localObject5);
/* 883:    */     }
/* 884:    */     finally
/* 885:    */     {
/* 886:653 */       _servant_postinvoke(localServantObject);
/* 887:    */     }
/* 888:    */   }
/* 889:    */   
/* 890:    */   public IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
/* 891:    */     throws RemoteException, AEException
/* 892:    */   {
/* 893:    */     IAEAlertDefinition localIAEAlertDefinition1;
/* 894:    */     Object localObject5;
/* 895:659 */     if (!Util.isLocal(this)) {
/* 896:    */       try
/* 897:    */       {
/* 898:661 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 899:    */         try
/* 900:    */         {
/* 901:663 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 902:664 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 903:665 */             _request("updateAlert__com_ffusion_alert_interfaces_AEApplicationInfo__long__CORBA_WStringValue__long__long__com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__org_omg_boxedRMI_seq1_octet", true);
/* 904:666 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 905:667 */           localOutputStream.write_long(paramInt1);
/* 906:668 */           localOutputStream.write_value(paramString, String.class);
/* 907:669 */           localOutputStream.write_long(paramInt2);
/* 908:670 */           localOutputStream.write_long(paramInt3);
/* 909:671 */           localOutputStream.write_value(paramAEScheduleInfo, AEScheduleInfo.class);
/* 910:672 */           localOutputStream.write_value(cast_array(paramArrayOfIAEDeliveryInfo), new IAEDeliveryInfo[0].getClass());
/* 911:673 */           localOutputStream.write_value(cast_array(paramArrayOfByte), new byte[0].getClass());
/* 912:674 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 913:675 */           return (IAEAlertDefinition)localInputStream.read_value(IAEAlertDefinition.class);
/* 914:    */         }
/* 915:    */         catch (ApplicationException localApplicationException)
/* 916:    */         {
/* 917:677 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 918:678 */           localObject5 = localInputStream.read_string();
/* 919:679 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 920:680 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 921:    */           }
/* 922:682 */           throw new UnexpectedException((String)localObject5);
/* 923:    */         }
/* 924:    */         catch (RemarshalException localRemarshalException)
/* 925:    */         {
/* 926:684 */           return updateAlert(paramAEApplicationInfo, paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
/* 927:    */         }
/* 928:    */         finally
/* 929:    */         {
/* 930:686 */           _releaseReply(localInputStream);
/* 931:    */         }
/* 932:    */       }
/* 933:    */       catch (SystemException localSystemException)
/* 934:    */       {
/* 935:689 */         throw Util.mapSystemException(localSystemException);
/* 936:    */       }
/* 937:    */     }
/* 938:692 */     ServantObject localServantObject = _servant_preinvoke("updateAlert__com_ffusion_alert_interfaces_AEApplicationInfo__long__CORBA_WStringValue__long__long__com_ffusion_alert_interfaces_AEScheduleInfo__org_omg_boxedRMI_com_ffusion_alert_interfaces_seq1_IAEDeliveryInfo__org_omg_boxedRMI_seq1_octet", IAEAlertClient.class);
/* 939:693 */     if (localServantObject == null) {
/* 940:694 */       return updateAlert(paramAEApplicationInfo, paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
/* 941:    */     }
/* 942:    */     try
/* 943:    */     {
/* 944:697 */       Object[] arrayOfObject = Util.copyObjects(new Object[] { paramAEApplicationInfo, paramString, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte }, _orb());
/* 945:698 */       localObject5 = (AEApplicationInfo)arrayOfObject[0];
/* 946:699 */       String str = (String)arrayOfObject[1];
/* 947:700 */       AEScheduleInfo localAEScheduleInfo = (AEScheduleInfo)arrayOfObject[2];
/* 948:701 */       IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = (IAEDeliveryInfo[])arrayOfObject[3];
/* 949:702 */       byte[] arrayOfByte = (byte[])arrayOfObject[4];
/* 950:703 */       IAEAlertDefinition localIAEAlertDefinition2 = ((IAEAlertClient)localServantObject.servant).updateAlert((AEApplicationInfo)localObject5, paramInt1, str, paramInt2, paramInt3, localAEScheduleInfo, arrayOfIAEDeliveryInfo, arrayOfByte);
/* 951:704 */       return (IAEAlertDefinition)Util.copyObject(localIAEAlertDefinition2, _orb());
/* 952:    */     }
/* 953:    */     catch (Throwable localThrowable)
/* 954:    */     {
/* 955:706 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 956:707 */       if ((localObject5 instanceof AEException)) {
/* 957:708 */         throw ((AEException)localObject5);
/* 958:    */       }
/* 959:710 */       throw Util.wrapException((Throwable)localObject5);
/* 960:    */     }
/* 961:    */     finally
/* 962:    */     {
/* 963:712 */       _servant_postinvoke(localServantObject);
/* 964:    */     }
/* 965:    */   }
/* 966:    */   
/* 967:    */   public IAEAuditInfo[] getUserAuditInfo(AEApplicationInfo paramAEApplicationInfo, String paramString, Date paramDate1, Date paramDate2)
/* 968:    */     throws RemoteException, AEException
/* 969:    */   {
/* 970:    */     IAEAuditInfo[] arrayOfIAEAuditInfo1;
/* 971:    */     Object localObject5;
/* 972:718 */     if (!Util.isLocal(this)) {
/* 973:    */       try
/* 974:    */       {
/* 975:720 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 976:    */         try
/* 977:    */         {
/* 978:722 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 979:723 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* 980:724 */             _request("getUserAuditInfo", true);
/* 981:725 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* 982:726 */           localOutputStream.write_value(paramString, String.class);
/* 983:727 */           localOutputStream.write_value(paramDate1, Date.class);
/* 984:728 */           localOutputStream.write_value(paramDate2, Date.class);
/* 985:729 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 986:730 */           return (IAEAuditInfo[])localInputStream.read_value(new IAEAuditInfo[0].getClass());
/* 987:    */         }
/* 988:    */         catch (ApplicationException localApplicationException)
/* 989:    */         {
/* 990:732 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 991:733 */           localObject5 = localInputStream.read_string();
/* 992:734 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* 993:735 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* 994:    */           }
/* 995:737 */           throw new UnexpectedException((String)localObject5);
/* 996:    */         }
/* 997:    */         catch (RemarshalException localRemarshalException)
/* 998:    */         {
/* 999:739 */           return getUserAuditInfo(paramAEApplicationInfo, paramString, paramDate1, paramDate2);
/* :00:    */         }
/* :01:    */         finally
/* :02:    */         {
/* :03:741 */           _releaseReply(localInputStream);
/* :04:    */         }
/* :05:    */       }
/* :06:    */       catch (SystemException localSystemException)
/* :07:    */       {
/* :08:744 */         throw Util.mapSystemException(localSystemException);
/* :09:    */       }
/* :10:    */     }
/* :11:747 */     ServantObject localServantObject = _servant_preinvoke("getUserAuditInfo", IAEAlertClient.class);
/* :12:748 */     if (localServantObject == null) {
/* :13:749 */       return getUserAuditInfo(paramAEApplicationInfo, paramString, paramDate1, paramDate2);
/* :14:    */     }
/* :15:    */     try
/* :16:    */     {
/* :17:752 */       Object[] arrayOfObject = Util.copyObjects(new Object[] { paramAEApplicationInfo, paramString, paramDate1, paramDate2 }, _orb());
/* :18:753 */       localObject5 = (AEApplicationInfo)arrayOfObject[0];
/* :19:754 */       String str = (String)arrayOfObject[1];
/* :20:755 */       Date localDate1 = (Date)arrayOfObject[2];
/* :21:756 */       Date localDate2 = (Date)arrayOfObject[3];
/* :22:757 */       IAEAuditInfo[] arrayOfIAEAuditInfo2 = ((IAEAlertClient)localServantObject.servant).getUserAuditInfo((AEApplicationInfo)localObject5, str, localDate1, localDate2);
/* :23:758 */       return (IAEAuditInfo[])Util.copyObject(arrayOfIAEAuditInfo2, _orb());
/* :24:    */     }
/* :25:    */     catch (Throwable localThrowable)
/* :26:    */     {
/* :27:760 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* :28:761 */       if ((localObject5 instanceof AEException)) {
/* :29:762 */         throw ((AEException)localObject5);
/* :30:    */       }
/* :31:764 */       throw Util.wrapException((Throwable)localObject5);
/* :32:    */     }
/* :33:    */     finally
/* :34:    */     {
/* :35:766 */       _servant_postinvoke(localServantObject);
/* :36:    */     }
/* :37:    */   }
/* :38:    */   
/* :39:    */   public IAEChannelInfo[] getInstalledDeliveryChannels(AEApplicationInfo paramAEApplicationInfo)
/* :40:    */     throws RemoteException, AEException
/* :41:    */   {
/* :42:    */     IAEChannelInfo[] arrayOfIAEChannelInfo;
/* :43:    */     Object localObject5;
/* :44:772 */     if (!Util.isLocal(this)) {
/* :45:    */       try
/* :46:    */       {
/* :47:774 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* :48:    */         try
/* :49:    */         {
/* :50:776 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* :51:777 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* :52:778 */             _request("getInstalledDeliveryChannels", true);
/* :53:779 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* :54:780 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* :55:781 */           return (IAEChannelInfo[])localInputStream.read_value(new IAEChannelInfo[0].getClass());
/* :56:    */         }
/* :57:    */         catch (ApplicationException localApplicationException)
/* :58:    */         {
/* :59:783 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* :60:784 */           localObject5 = localInputStream.read_string();
/* :61:785 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* :62:786 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* :63:    */           }
/* :64:788 */           throw new UnexpectedException((String)localObject5);
/* :65:    */         }
/* :66:    */         catch (RemarshalException localRemarshalException)
/* :67:    */         {
/* :68:790 */           return getInstalledDeliveryChannels(paramAEApplicationInfo);
/* :69:    */         }
/* :70:    */         finally
/* :71:    */         {
/* :72:792 */           _releaseReply(localInputStream);
/* :73:    */         }
/* :74:    */       }
/* :75:    */       catch (SystemException localSystemException)
/* :76:    */       {
/* :77:795 */         throw Util.mapSystemException(localSystemException);
/* :78:    */       }
/* :79:    */     }
/* :80:798 */     ServantObject localServantObject = _servant_preinvoke("getInstalledDeliveryChannels", IAEAlertClient.class);
/* :81:799 */     if (localServantObject == null) {
/* :82:800 */       return getInstalledDeliveryChannels(paramAEApplicationInfo);
/* :83:    */     }
/* :84:    */     try
/* :85:    */     {
/* :86:803 */       AEApplicationInfo localAEApplicationInfo = (AEApplicationInfo)Util.copyObject(paramAEApplicationInfo, _orb());
/* :87:804 */       localObject5 = ((IAEAlertClient)localServantObject.servant).getInstalledDeliveryChannels(localAEApplicationInfo);
/* :88:805 */       return (IAEChannelInfo[])Util.copyObject(localObject5, _orb());
/* :89:    */     }
/* :90:    */     catch (Throwable localThrowable)
/* :91:    */     {
/* :92:807 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* :93:808 */       if ((localObject5 instanceof AEException)) {
/* :94:809 */         throw ((AEException)localObject5);
/* :95:    */       }
/* :96:811 */       throw Util.wrapException((Throwable)localObject5);
/* :97:    */     }
/* :98:    */     finally
/* :99:    */     {
/* ;00:813 */       _servant_postinvoke(localServantObject);
/* ;01:    */     }
/* ;02:    */   }
/* ;03:    */   
/* ;04:    */   public IAEChannelInfo[] getLoadedDeliveryChannels(AEApplicationInfo paramAEApplicationInfo)
/* ;05:    */     throws RemoteException, AEException
/* ;06:    */   {
/* ;07:    */     IAEChannelInfo[] arrayOfIAEChannelInfo;
/* ;08:    */     Object localObject5;
/* ;09:819 */     if (!Util.isLocal(this)) {
/* ;10:    */       try
/* ;11:    */       {
/* ;12:821 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* ;13:    */         try
/* ;14:    */         {
/* ;15:823 */           org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* ;16:824 */             (org.omg.CORBA_2_3.portable.OutputStream)
/* ;17:825 */             _request("getLoadedDeliveryChannels", true);
/* ;18:826 */           localOutputStream.write_value(paramAEApplicationInfo, AEApplicationInfo.class);
/* ;19:827 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* ;20:828 */           return (IAEChannelInfo[])localInputStream.read_value(new IAEChannelInfo[0].getClass());
/* ;21:    */         }
/* ;22:    */         catch (ApplicationException localApplicationException)
/* ;23:    */         {
/* ;24:830 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* ;25:831 */           localObject5 = localInputStream.read_string();
/* ;26:832 */           if (((String)localObject5).equals("IDL:com/ffusion/alert/interfaces/AEEx:1.0")) {
/* ;27:833 */             throw ((AEException)localInputStream.read_value(AEException.class));
/* ;28:    */           }
/* ;29:835 */           throw new UnexpectedException((String)localObject5);
/* ;30:    */         }
/* ;31:    */         catch (RemarshalException localRemarshalException)
/* ;32:    */         {
/* ;33:837 */           return getLoadedDeliveryChannels(paramAEApplicationInfo);
/* ;34:    */         }
/* ;35:    */         finally
/* ;36:    */         {
/* ;37:839 */           _releaseReply(localInputStream);
/* ;38:    */         }
/* ;39:    */       }
/* ;40:    */       catch (SystemException localSystemException)
/* ;41:    */       {
/* ;42:842 */         throw Util.mapSystemException(localSystemException);
/* ;43:    */       }
/* ;44:    */     }
/* ;45:845 */     ServantObject localServantObject = _servant_preinvoke("getLoadedDeliveryChannels", IAEAlertClient.class);
/* ;46:846 */     if (localServantObject == null) {
/* ;47:847 */       return getLoadedDeliveryChannels(paramAEApplicationInfo);
/* ;48:    */     }
/* ;49:    */     try
/* ;50:    */     {
/* ;51:850 */       AEApplicationInfo localAEApplicationInfo = (AEApplicationInfo)Util.copyObject(paramAEApplicationInfo, _orb());
/* ;52:851 */       localObject5 = ((IAEAlertClient)localServantObject.servant).getLoadedDeliveryChannels(localAEApplicationInfo);
/* ;53:852 */       return (IAEChannelInfo[])Util.copyObject(localObject5, _orb());
/* ;54:    */     }
/* ;55:    */     catch (Throwable localThrowable)
/* ;56:    */     {
/* ;57:854 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* ;58:855 */       if ((localObject5 instanceof AEException)) {
/* ;59:856 */         throw ((AEException)localObject5);
/* ;60:    */       }
/* ;61:858 */       throw Util.wrapException((Throwable)localObject5);
/* ;62:    */     }
/* ;63:    */     finally
/* ;64:    */     {
/* ;65:860 */       _servant_postinvoke(localServantObject);
/* ;66:    */     }
/* ;67:    */   }
/* ;68:    */   
/* ;69:    */   private Serializable cast_array(Object paramObject)
/* ;70:    */   {
/* ;71:869 */     return (Serializable)paramObject;
/* ;72:    */   }
/* ;73:    */ }


/* Location:           D:\drops\jd\jars\UAEClientEJB20.jar
 * Qualified Name:     com.ffusion.alert.clientEJB._IAEAlertClient_Stub
 * JD-Core Version:    0.7.0.1
 */