/*   1:    */ package com.ibm.ejs.container;
/*   2:    */ 
/*   3:    */ import com.ibm.websphere.csi.CSIServant;
/*   4:    */ import com.ibm.websphere.csi.TransactionalObject;
/*   5:    */ import java.rmi.RemoteException;
/*   6:    */ import java.rmi.UnexpectedException;
/*   7:    */ import javax.ejb.EJBHome;
/*   8:    */ import javax.ejb.EJBObject;
/*   9:    */ import javax.ejb.Handle;
/*  10:    */ import javax.ejb.RemoveException;
/*  11:    */ import javax.rmi.CORBA.Stub;
/*  12:    */ import javax.rmi.CORBA.Util;
/*  13:    */ import org.omg.CORBA.SystemException;
/*  14:    */ import org.omg.CORBA.portable.ApplicationException;
/*  15:    */ import org.omg.CORBA.portable.ObjectImpl;
/*  16:    */ import org.omg.CORBA.portable.OutputStream;
/*  17:    */ import org.omg.CORBA.portable.RemarshalException;
/*  18:    */ import org.omg.CORBA.portable.ServantObject;
/*  19:    */ 
/*  20:    */ public class _EJSWrapper_Stub
/*  21:    */   extends Stub
/*  22:    */   implements CSIServant, TransactionalObject, EJBObject
/*  23:    */ {
/*  24: 27 */   private static final String[] _type_ids = {
/*  25: 28 */     "RMI:com.ibm.ejs.container.EJSWrapper:0000000000000000", 
/*  26: 29 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
/*  27: 30 */     "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000", 
/*  28: 31 */     "RMI:javax.ejb.EJBObject:0000000000000000" };
/*  29:    */   
/*  30:    */   public String[] _ids()
/*  31:    */   {
/*  32: 35 */     return _type_ids;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean wlmable()
/*  36:    */     throws RemoteException
/*  37:    */   {
/*  38:    */     boolean bool;
/*  39:    */     Object localObject5;
/*  40: 39 */     if (!Util.isLocal(this)) {
/*  41:    */       try
/*  42:    */       {
/*  43: 41 */         org.omg.CORBA.portable.InputStream localInputStream = null;
/*  44:    */         try
/*  45:    */         {
/*  46: 43 */           OutputStream localOutputStream = _request("wlmable", true);
/*  47: 44 */           localInputStream = _invoke(localOutputStream);
/*  48: 45 */           return localInputStream.read_boolean();
/*  49:    */         }
/*  50:    */         catch (ApplicationException localApplicationException)
/*  51:    */         {
/*  52: 47 */           localInputStream = localApplicationException.getInputStream();
/*  53: 48 */           localObject5 = localInputStream.read_string();
/*  54: 49 */           throw new UnexpectedException((String)localObject5);
/*  55:    */         }
/*  56:    */         catch (RemarshalException localRemarshalException)
/*  57:    */         {
/*  58: 51 */           return wlmable();
/*  59:    */         }
/*  60:    */         finally
/*  61:    */         {
/*  62: 53 */           _releaseReply(localInputStream);
/*  63:    */         }
/*  64:    */       }
/*  65:    */       catch (SystemException localSystemException)
/*  66:    */       {
/*  67: 56 */         throw Util.mapSystemException(localSystemException);
/*  68:    */       }
/*  69:    */     }
/*  70: 59 */     ServantObject localServantObject = _servant_preinvoke("wlmable", CSIServant.class);
/*  71: 60 */     if (localServantObject == null) {
/*  72: 61 */       return wlmable();
/*  73:    */     }
/*  74:    */     try
/*  75:    */     {
/*  76: 64 */       return ((CSIServant)localServantObject.servant).wlmable();
/*  77:    */     }
/*  78:    */     catch (Throwable localThrowable)
/*  79:    */     {
/*  80: 66 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/*  81: 67 */       throw Util.wrapException((Throwable)localObject5);
/*  82:    */     }
/*  83:    */     finally
/*  84:    */     {
/*  85: 69 */       _servant_postinvoke(localServantObject);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public EJBHome getEJBHome()
/*  90:    */     throws RemoteException
/*  91:    */   {
/*  92:    */     EJBHome localEJBHome1;
/*  93:    */     Object localObject5;
/*  94: 75 */     if (!Util.isLocal(this)) {
/*  95:    */       try
/*  96:    */       {
/*  97: 77 */         org.omg.CORBA.portable.InputStream localInputStream = null;
/*  98:    */         try
/*  99:    */         {
/* 100: 79 */           OutputStream localOutputStream = _request("_get_EJBHome", true);
/* 101: 80 */           localInputStream = _invoke(localOutputStream);
/* 102: 81 */           return (EJBHome)localInputStream.read_Object(EJBHome.class);
/* 103:    */         }
/* 104:    */         catch (ApplicationException localApplicationException)
/* 105:    */         {
/* 106: 83 */           localInputStream = localApplicationException.getInputStream();
/* 107: 84 */           localObject5 = localInputStream.read_string();
/* 108: 85 */           throw new UnexpectedException((String)localObject5);
/* 109:    */         }
/* 110:    */         catch (RemarshalException localRemarshalException)
/* 111:    */         {
/* 112: 87 */           return getEJBHome();
/* 113:    */         }
/* 114:    */         finally
/* 115:    */         {
/* 116: 89 */           _releaseReply(localInputStream);
/* 117:    */         }
/* 118:    */       }
/* 119:    */       catch (SystemException localSystemException)
/* 120:    */       {
/* 121: 92 */         throw Util.mapSystemException(localSystemException);
/* 122:    */       }
/* 123:    */     }
/* 124: 95 */     ServantObject localServantObject = _servant_preinvoke("_get_EJBHome", EJBObject.class);
/* 125: 96 */     if (localServantObject == null) {
/* 126: 97 */       return getEJBHome();
/* 127:    */     }
/* 128:    */     try
/* 129:    */     {
/* 130:100 */       EJBHome localEJBHome2 = ((EJBObject)localServantObject.servant).getEJBHome();
/* 131:101 */       return (EJBHome)Util.copyObject(localEJBHome2, _orb());
/* 132:    */     }
/* 133:    */     catch (Throwable localThrowable)
/* 134:    */     {
/* 135:103 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 136:104 */       throw Util.wrapException((Throwable)localObject5);
/* 137:    */     }
/* 138:    */     finally
/* 139:    */     {
/* 140:106 */       _servant_postinvoke(localServantObject);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Object getPrimaryKey()
/* 145:    */     throws RemoteException
/* 146:    */   {
/* 147:    */     Object localObject1;
/* 148:    */     Object localObject7;
/* 149:112 */     if (!Util.isLocal(this)) {
/* 150:    */       try
/* 151:    */       {
/* 152:114 */         org.omg.CORBA.portable.InputStream localInputStream = null;
/* 153:    */         try
/* 154:    */         {
/* 155:116 */           OutputStream localOutputStream = _request("_get_primaryKey", true);
/* 156:117 */           localInputStream = _invoke(localOutputStream);
/* 157:118 */           return Util.readAny(localInputStream);
/* 158:    */         }
/* 159:    */         catch (ApplicationException localApplicationException)
/* 160:    */         {
/* 161:120 */           localInputStream = localApplicationException.getInputStream();
/* 162:121 */           localObject7 = localInputStream.read_string();
/* 163:122 */           throw new UnexpectedException((String)localObject7);
/* 164:    */         }
/* 165:    */         catch (RemarshalException localRemarshalException)
/* 166:    */         {
/* 167:124 */           return getPrimaryKey();
/* 168:    */         }
/* 169:    */         finally
/* 170:    */         {
/* 171:126 */           _releaseReply(localInputStream);
/* 172:    */         }
/* 173:    */       }
/* 174:    */       catch (SystemException localSystemException)
/* 175:    */       {
/* 176:129 */         throw Util.mapSystemException(localSystemException);
/* 177:    */       }
/* 178:    */     }
/* 179:132 */     ServantObject localServantObject = _servant_preinvoke("_get_primaryKey", EJBObject.class);
/* 180:133 */     if (localServantObject == null) {
/* 181:134 */       return getPrimaryKey();
/* 182:    */     }
/* 183:    */     try
/* 184:    */     {
/* 185:137 */       Object localObject6 = ((EJBObject)localServantObject.servant).getPrimaryKey();
/* 186:138 */       return Util.copyObject(localObject6, _orb());
/* 187:    */     }
/* 188:    */     catch (Throwable localThrowable)
/* 189:    */     {
/* 190:140 */       localObject7 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 191:141 */       throw Util.wrapException((Throwable)localObject7);
/* 192:    */     }
/* 193:    */     finally
/* 194:    */     {
/* 195:143 */       _servant_postinvoke(localServantObject);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void remove()
/* 200:    */     throws RemoteException, RemoveException
/* 201:    */   {
/* 202:    */     Object localObject5;
/* 203:149 */     if (!Util.isLocal(this)) {
/* 204:    */       try
/* 205:    */       {
/* 206:151 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 207:    */         try
/* 208:    */         {
/* 209:153 */           OutputStream localOutputStream = _request("remove", true);
/* 210:154 */           _invoke(localOutputStream);
/* 211:155 */           return;
/* 212:    */         }
/* 213:    */         catch (ApplicationException localApplicationException)
/* 214:    */         {
/* 215:157 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 216:158 */           localObject5 = localInputStream.read_string();
/* 217:159 */           if (((String)localObject5).equals("IDL:javax/ejb/RemoveEx:1.0")) {
/* 218:160 */             throw ((RemoveException)localInputStream.read_value(RemoveException.class));
/* 219:    */           }
/* 220:162 */           throw new UnexpectedException((String)localObject5);
/* 221:    */         }
/* 222:    */         catch (RemarshalException localRemarshalException)
/* 223:    */         {
/* 224:164 */           remove();
/* 225:165 */           return;
/* 226:    */         }
/* 227:    */         finally
/* 228:    */         {
/* 229:167 */           _releaseReply(localInputStream);
/* 230:    */         }
/* 231:    */       }
/* 232:    */       catch (SystemException localSystemException)
/* 233:    */       {
/* 234:170 */         throw Util.mapSystemException(localSystemException);
/* 235:    */       }
/* 236:    */     }
/* 237:173 */     ServantObject localServantObject = _servant_preinvoke("remove", EJBObject.class);
/* 238:174 */     if (localServantObject == null)
/* 239:    */     {
/* 240:175 */       remove();
/* 241:176 */       return;
/* 242:    */     }
/* 243:    */     try
/* 244:    */     {
/* 245:179 */       ((EJBObject)localServantObject.servant).remove();
/* 246:180 */       return;
/* 247:    */     }
/* 248:    */     catch (Throwable localThrowable)
/* 249:    */     {
/* 250:182 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 251:183 */       if ((localObject5 instanceof RemoveException)) {
/* 252:184 */         throw ((RemoveException)localObject5);
/* 253:    */       }
/* 254:186 */       throw Util.wrapException((Throwable)localObject5);
/* 255:    */     }
/* 256:    */     finally
/* 257:    */     {
/* 258:188 */       _servant_postinvoke(localServantObject);
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   public Handle getHandle()
/* 263:    */     throws RemoteException
/* 264:    */   {
/* 265:    */     Handle localHandle1;
/* 266:    */     Object localObject5;
/* 267:194 */     if (!Util.isLocal(this)) {
/* 268:    */       try
/* 269:    */       {
/* 270:196 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 271:    */         try
/* 272:    */         {
/* 273:198 */           OutputStream localOutputStream = _request("_get_handle", true);
/* 274:199 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 275:200 */           return (Handle)localInputStream.read_abstract_interface(Handle.class);
/* 276:    */         }
/* 277:    */         catch (ApplicationException localApplicationException)
/* 278:    */         {
/* 279:202 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 280:203 */           localObject5 = localInputStream.read_string();
/* 281:204 */           throw new UnexpectedException((String)localObject5);
/* 282:    */         }
/* 283:    */         catch (RemarshalException localRemarshalException)
/* 284:    */         {
/* 285:206 */           return getHandle();
/* 286:    */         }
/* 287:    */         finally
/* 288:    */         {
/* 289:208 */           _releaseReply(localInputStream);
/* 290:    */         }
/* 291:    */       }
/* 292:    */       catch (SystemException localSystemException)
/* 293:    */       {
/* 294:211 */         throw Util.mapSystemException(localSystemException);
/* 295:    */       }
/* 296:    */     }
/* 297:214 */     ServantObject localServantObject = _servant_preinvoke("_get_handle", EJBObject.class);
/* 298:215 */     if (localServantObject == null) {
/* 299:216 */       return getHandle();
/* 300:    */     }
/* 301:    */     try
/* 302:    */     {
/* 303:219 */       Handle localHandle2 = ((EJBObject)localServantObject.servant).getHandle();
/* 304:220 */       return (Handle)Util.copyObject(localHandle2, _orb());
/* 305:    */     }
/* 306:    */     catch (Throwable localThrowable)
/* 307:    */     {
/* 308:222 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 309:223 */       throw Util.wrapException((Throwable)localObject5);
/* 310:    */     }
/* 311:    */     finally
/* 312:    */     {
/* 313:225 */       _servant_postinvoke(localServantObject);
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   public boolean isIdentical(EJBObject paramEJBObject)
/* 318:    */     throws RemoteException
/* 319:    */   {
/* 320:    */     boolean bool;
/* 321:    */     Object localObject5;
/* 322:231 */     if (!Util.isLocal(this)) {
/* 323:    */       try
/* 324:    */       {
/* 325:233 */         org.omg.CORBA.portable.InputStream localInputStream = null;
/* 326:    */         try
/* 327:    */         {
/* 328:235 */           OutputStream localOutputStream = _request("isIdentical", true);
/* 329:236 */           Util.writeRemoteObject(localOutputStream, paramEJBObject);
/* 330:237 */           localInputStream = _invoke(localOutputStream);
/* 331:238 */           return localInputStream.read_boolean();
/* 332:    */         }
/* 333:    */         catch (ApplicationException localApplicationException)
/* 334:    */         {
/* 335:240 */           localInputStream = localApplicationException.getInputStream();
/* 336:241 */           localObject5 = localInputStream.read_string();
/* 337:242 */           throw new UnexpectedException((String)localObject5);
/* 338:    */         }
/* 339:    */         catch (RemarshalException localRemarshalException)
/* 340:    */         {
/* 341:244 */           return isIdentical(paramEJBObject);
/* 342:    */         }
/* 343:    */         finally
/* 344:    */         {
/* 345:246 */           _releaseReply(localInputStream);
/* 346:    */         }
/* 347:    */       }
/* 348:    */       catch (SystemException localSystemException)
/* 349:    */       {
/* 350:249 */         throw Util.mapSystemException(localSystemException);
/* 351:    */       }
/* 352:    */     }
/* 353:252 */     ServantObject localServantObject = _servant_preinvoke("isIdentical", EJBObject.class);
/* 354:253 */     if (localServantObject == null) {
/* 355:254 */       return isIdentical(paramEJBObject);
/* 356:    */     }
/* 357:    */     try
/* 358:    */     {
/* 359:257 */       EJBObject localEJBObject = (EJBObject)Util.copyObject(paramEJBObject, _orb());
/* 360:258 */       return ((EJBObject)localServantObject.servant).isIdentical(localEJBObject);
/* 361:    */     }
/* 362:    */     catch (Throwable localThrowable)
/* 363:    */     {
/* 364:260 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 365:261 */       throw Util.wrapException((Throwable)localObject5);
/* 366:    */     }
/* 367:    */     finally
/* 368:    */     {
/* 369:263 */       _servant_postinvoke(localServantObject);
/* 370:    */     }
/* 371:    */   }
/* 372:    */ }


/* Location:           D:\drops\jd\jars\UAEClientEJB20.jar
 * Qualified Name:     com.ibm.ejs.container._EJSWrapper_Stub
 * JD-Core Version:    0.7.0.1
 */