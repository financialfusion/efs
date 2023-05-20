/*   1:    */ package com.ffusion.alert.clientEJB;
/*   2:    */ 
/*   3:    */ import java.rmi.RemoteException;
/*   4:    */ import java.rmi.UnexpectedException;
/*   5:    */ import javax.ejb.CreateException;
/*   6:    */ import javax.ejb.EJBHome;
/*   7:    */ import javax.ejb.EJBMetaData;
/*   8:    */ import javax.ejb.Handle;
/*   9:    */ import javax.ejb.HomeHandle;
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
/*  20:    */ public class _IAEAlertClientHome_Stub
/*  21:    */   extends Stub
/*  22:    */   implements IAEAlertClientHome
/*  23:    */ {
/*  24: 24 */   private static final String[] _type_ids = {
/*  25: 25 */     "RMI:com.ffusion.alert.clientEJB.IAEAlertClientHome:0000000000000000", 
/*  26: 26 */     "RMI:javax.ejb.EJBHome:0000000000000000" };
/*  27:    */   
/*  28:    */   public String[] _ids()
/*  29:    */   {
/*  30: 30 */     return _type_ids;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void remove(Handle paramHandle)
/*  34:    */     throws RemoteException, RemoveException
/*  35:    */   {
/*  36:    */     Object localObject5;
/*  37: 34 */     if (!Util.isLocal(this)) {
/*  38:    */       try
/*  39:    */       {
/*  40: 36 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/*  41:    */         try
/*  42:    */         {
/*  43: 38 */           OutputStream localOutputStream = _request("remove__javax_ejb_Handle", true);
/*  44: 39 */           Util.writeAbstractObject(localOutputStream, paramHandle);
/*  45: 40 */           _invoke(localOutputStream);
/*  46: 41 */           return;
/*  47:    */         }
/*  48:    */         catch (ApplicationException localApplicationException)
/*  49:    */         {
/*  50: 43 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/*  51: 44 */           localObject5 = localInputStream.read_string();
/*  52: 45 */           if (((String)localObject5).equals("IDL:javax/ejb/RemoveEx:1.0")) {
/*  53: 46 */             throw ((RemoveException)localInputStream.read_value(RemoveException.class));
/*  54:    */           }
/*  55: 48 */           throw new UnexpectedException((String)localObject5);
/*  56:    */         }
/*  57:    */         catch (RemarshalException localRemarshalException)
/*  58:    */         {
/*  59: 50 */           remove(paramHandle);
/*  60: 51 */           return;
/*  61:    */         }
/*  62:    */         finally
/*  63:    */         {
/*  64: 53 */           _releaseReply(localInputStream);
/*  65:    */         }
/*  66:    */       }
/*  67:    */       catch (SystemException localSystemException)
/*  68:    */       {
/*  69: 56 */         throw Util.mapSystemException(localSystemException);
/*  70:    */       }
/*  71:    */     }
/*  72: 59 */     ServantObject localServantObject = _servant_preinvoke("remove__javax_ejb_Handle", EJBHome.class);
/*  73: 60 */     if (localServantObject == null)
/*  74:    */     {
/*  75: 61 */       remove(paramHandle);
/*  76: 62 */       return;
/*  77:    */     }
/*  78:    */     try
/*  79:    */     {
/*  80: 65 */       Handle localHandle = (Handle)Util.copyObject(paramHandle, _orb());
/*  81: 66 */       ((EJBHome)localServantObject.servant).remove(localHandle);
/*  82: 67 */       return;
/*  83:    */     }
/*  84:    */     catch (Throwable localThrowable)
/*  85:    */     {
/*  86: 69 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/*  87: 70 */       if ((localObject5 instanceof RemoveException)) {
/*  88: 71 */         throw ((RemoveException)localObject5);
/*  89:    */       }
/*  90: 73 */       throw Util.wrapException((Throwable)localObject5);
/*  91:    */     }
/*  92:    */     finally
/*  93:    */     {
/*  94: 75 */       _servant_postinvoke(localServantObject);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void remove(Object paramObject)
/*  99:    */     throws RemoteException, RemoveException
/* 100:    */   {
/* 101:    */     Object localObject6;
/* 102: 81 */     if (!Util.isLocal(this)) {
/* 103:    */       try
/* 104:    */       {
/* 105: 83 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 106:    */         try
/* 107:    */         {
/* 108: 85 */           OutputStream localOutputStream = _request("remove__java_lang_Object", true);
/* 109: 86 */           Util.writeAny(localOutputStream, paramObject);
/* 110: 87 */           _invoke(localOutputStream);
/* 111: 88 */           return;
/* 112:    */         }
/* 113:    */         catch (ApplicationException localApplicationException)
/* 114:    */         {
/* 115: 90 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 116: 91 */           localObject6 = localInputStream.read_string();
/* 117: 92 */           if (((String)localObject6).equals("IDL:javax/ejb/RemoveEx:1.0")) {
/* 118: 93 */             throw ((RemoveException)localInputStream.read_value(RemoveException.class));
/* 119:    */           }
/* 120: 95 */           throw new UnexpectedException((String)localObject6);
/* 121:    */         }
/* 122:    */         catch (RemarshalException localRemarshalException)
/* 123:    */         {
/* 124: 97 */           remove(paramObject);
/* 125: 98 */           return;
/* 126:    */         }
/* 127:    */         finally
/* 128:    */         {
/* 129:100 */           _releaseReply(localInputStream);
/* 130:    */         }
/* 131:    */       }
/* 132:    */       catch (SystemException localSystemException)
/* 133:    */       {
/* 134:103 */         throw Util.mapSystemException(localSystemException);
/* 135:    */       }
/* 136:    */     }
/* 137:106 */     ServantObject localServantObject = _servant_preinvoke("remove__java_lang_Object", EJBHome.class);
/* 138:107 */     if (localServantObject == null)
/* 139:    */     {
/* 140:108 */       remove(paramObject);
/* 141:109 */       return;
/* 142:    */     }
/* 143:    */     try
/* 144:    */     {
/* 145:112 */       Object localObject5 = Util.copyObject(paramObject, _orb());
/* 146:113 */       ((EJBHome)localServantObject.servant).remove(localObject5);
/* 147:114 */       return;
/* 148:    */     }
/* 149:    */     catch (Throwable localThrowable)
/* 150:    */     {
/* 151:116 */       localObject6 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 152:117 */       if ((localObject6 instanceof RemoveException)) {
/* 153:118 */         throw ((RemoveException)localObject6);
/* 154:    */       }
/* 155:120 */       throw Util.wrapException((Throwable)localObject6);
/* 156:    */     }
/* 157:    */     finally
/* 158:    */     {
/* 159:122 */       _servant_postinvoke(localServantObject);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public EJBMetaData getEJBMetaData()
/* 164:    */     throws RemoteException
/* 165:    */   {
/* 166:    */     EJBMetaData localEJBMetaData1;
/* 167:    */     Object localObject5;
/* 168:128 */     if (!Util.isLocal(this)) {
/* 169:    */       try
/* 170:    */       {
/* 171:130 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 172:    */         try
/* 173:    */         {
/* 174:132 */           OutputStream localOutputStream = _request("_get_EJBMetaData", true);
/* 175:133 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 176:134 */           return (EJBMetaData)localInputStream.read_value(EJBMetaData.class);
/* 177:    */         }
/* 178:    */         catch (ApplicationException localApplicationException)
/* 179:    */         {
/* 180:136 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 181:137 */           localObject5 = localInputStream.read_string();
/* 182:138 */           throw new UnexpectedException((String)localObject5);
/* 183:    */         }
/* 184:    */         catch (RemarshalException localRemarshalException)
/* 185:    */         {
/* 186:140 */           return getEJBMetaData();
/* 187:    */         }
/* 188:    */         finally
/* 189:    */         {
/* 190:142 */           _releaseReply(localInputStream);
/* 191:    */         }
/* 192:    */       }
/* 193:    */       catch (SystemException localSystemException)
/* 194:    */       {
/* 195:145 */         throw Util.mapSystemException(localSystemException);
/* 196:    */       }
/* 197:    */     }
/* 198:148 */     ServantObject localServantObject = _servant_preinvoke("_get_EJBMetaData", EJBHome.class);
/* 199:149 */     if (localServantObject == null) {
/* 200:150 */       return getEJBMetaData();
/* 201:    */     }
/* 202:    */     try
/* 203:    */     {
/* 204:153 */       EJBMetaData localEJBMetaData2 = ((EJBHome)localServantObject.servant).getEJBMetaData();
/* 205:154 */       return (EJBMetaData)Util.copyObject(localEJBMetaData2, _orb());
/* 206:    */     }
/* 207:    */     catch (Throwable localThrowable)
/* 208:    */     {
/* 209:156 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 210:157 */       throw Util.wrapException((Throwable)localObject5);
/* 211:    */     }
/* 212:    */     finally
/* 213:    */     {
/* 214:159 */       _servant_postinvoke(localServantObject);
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public HomeHandle getHomeHandle()
/* 219:    */     throws RemoteException
/* 220:    */   {
/* 221:    */     HomeHandle localHomeHandle1;
/* 222:    */     Object localObject5;
/* 223:165 */     if (!Util.isLocal(this)) {
/* 224:    */       try
/* 225:    */       {
/* 226:167 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 227:    */         try
/* 228:    */         {
/* 229:169 */           OutputStream localOutputStream = _request("_get_homeHandle", true);
/* 230:170 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 231:171 */           return (HomeHandle)localInputStream.read_abstract_interface(HomeHandle.class);
/* 232:    */         }
/* 233:    */         catch (ApplicationException localApplicationException)
/* 234:    */         {
/* 235:173 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 236:174 */           localObject5 = localInputStream.read_string();
/* 237:175 */           throw new UnexpectedException((String)localObject5);
/* 238:    */         }
/* 239:    */         catch (RemarshalException localRemarshalException)
/* 240:    */         {
/* 241:177 */           return getHomeHandle();
/* 242:    */         }
/* 243:    */         finally
/* 244:    */         {
/* 245:179 */           _releaseReply(localInputStream);
/* 246:    */         }
/* 247:    */       }
/* 248:    */       catch (SystemException localSystemException)
/* 249:    */       {
/* 250:182 */         throw Util.mapSystemException(localSystemException);
/* 251:    */       }
/* 252:    */     }
/* 253:185 */     ServantObject localServantObject = _servant_preinvoke("_get_homeHandle", EJBHome.class);
/* 254:186 */     if (localServantObject == null) {
/* 255:187 */       return getHomeHandle();
/* 256:    */     }
/* 257:    */     try
/* 258:    */     {
/* 259:190 */       HomeHandle localHomeHandle2 = ((EJBHome)localServantObject.servant).getHomeHandle();
/* 260:191 */       return (HomeHandle)Util.copyObject(localHomeHandle2, _orb());
/* 261:    */     }
/* 262:    */     catch (Throwable localThrowable)
/* 263:    */     {
/* 264:193 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 265:194 */       throw Util.wrapException((Throwable)localObject5);
/* 266:    */     }
/* 267:    */     finally
/* 268:    */     {
/* 269:196 */       _servant_postinvoke(localServantObject);
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public IAEAlertClient create()
/* 274:    */     throws CreateException, RemoteException
/* 275:    */   {
/* 276:    */     IAEAlertClient localIAEAlertClient1;
/* 277:    */     Object localObject5;
/* 278:202 */     if (!Util.isLocal(this)) {
/* 279:    */       try
/* 280:    */       {
/* 281:204 */         org.omg.CORBA_2_3.portable.InputStream localInputStream = null;
/* 282:    */         try
/* 283:    */         {
/* 284:206 */           OutputStream localOutputStream = _request("create", true);
/* 285:207 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)_invoke(localOutputStream);
/* 286:208 */           return (IAEAlertClient)localInputStream.read_Object(IAEAlertClient.class);
/* 287:    */         }
/* 288:    */         catch (ApplicationException localApplicationException)
/* 289:    */         {
/* 290:210 */           localInputStream = (org.omg.CORBA_2_3.portable.InputStream)localApplicationException.getInputStream();
/* 291:211 */           localObject5 = localInputStream.read_string();
/* 292:212 */           if (((String)localObject5).equals("IDL:javax/ejb/CreateEx:1.0")) {
/* 293:213 */             throw ((CreateException)localInputStream.read_value(CreateException.class));
/* 294:    */           }
/* 295:215 */           throw new UnexpectedException((String)localObject5);
/* 296:    */         }
/* 297:    */         catch (RemarshalException localRemarshalException)
/* 298:    */         {
/* 299:217 */           return create();
/* 300:    */         }
/* 301:    */         finally
/* 302:    */         {
/* 303:219 */           _releaseReply(localInputStream);
/* 304:    */         }
/* 305:    */       }
/* 306:    */       catch (SystemException localSystemException)
/* 307:    */       {
/* 308:222 */         throw Util.mapSystemException(localSystemException);
/* 309:    */       }
/* 310:    */     }
/* 311:225 */     ServantObject localServantObject = _servant_preinvoke("create", IAEAlertClientHome.class);
/* 312:226 */     if (localServantObject == null) {
/* 313:227 */       return create();
/* 314:    */     }
/* 315:    */     try
/* 316:    */     {
/* 317:230 */       IAEAlertClient localIAEAlertClient2 = ((IAEAlertClientHome)localServantObject.servant).create();
/* 318:231 */       return (IAEAlertClient)Util.copyObject(localIAEAlertClient2, _orb());
/* 319:    */     }
/* 320:    */     catch (Throwable localThrowable)
/* 321:    */     {
/* 322:233 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 323:234 */       if ((localObject5 instanceof CreateException)) {
/* 324:235 */         throw ((CreateException)localObject5);
/* 325:    */       }
/* 326:237 */       throw Util.wrapException((Throwable)localObject5);
/* 327:    */     }
/* 328:    */     finally
/* 329:    */     {
/* 330:239 */       _servant_postinvoke(localServantObject);
/* 331:    */     }
/* 332:    */   }
/* 333:    */ }


/* Location:           D:\drops\jd\jars\UAEClientEJB20.jar
 * Qualified Name:     com.ffusion.alert.clientEJB._IAEAlertClientHome_Stub
 * JD-Core Version:    0.7.0.1
 */