/*  1:   */ package com.ibm.websphere.csi;
/*  2:   */ 
/*  3:   */ import java.rmi.RemoteException;
/*  4:   */ import java.rmi.UnexpectedException;
/*  5:   */ import javax.rmi.CORBA.Stub;
/*  6:   */ import javax.rmi.CORBA.Util;
/*  7:   */ import org.omg.CORBA.SystemException;
/*  8:   */ import org.omg.CORBA.portable.ApplicationException;
/*  9:   */ import org.omg.CORBA.portable.InputStream;
/* 10:   */ import org.omg.CORBA.portable.ObjectImpl;
/* 11:   */ import org.omg.CORBA.portable.OutputStream;
/* 12:   */ import org.omg.CORBA.portable.RemarshalException;
/* 13:   */ import org.omg.CORBA.portable.ServantObject;
/* 14:   */ 
/* 15:   */ public class _CSIServant_Stub
/* 16:   */   extends Stub
/* 17:   */   implements CSIServant
/* 18:   */ {
/* 19:19 */   private static final String[] _type_ids = {
/* 20:20 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000" };
/* 21:   */   
/* 22:   */   public String[] _ids()
/* 23:   */   {
/* 24:24 */     return _type_ids;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean wlmable()
/* 28:   */     throws RemoteException
/* 29:   */   {
/* 30:   */     boolean bool;
/* 31:   */     Object localObject5;
/* 32:28 */     if (!Util.isLocal(this)) {
/* 33:   */       try
/* 34:   */       {
/* 35:30 */         InputStream localInputStream = null;
/* 36:   */         try
/* 37:   */         {
/* 38:32 */           OutputStream localOutputStream = _request("wlmable", true);
/* 39:33 */           localInputStream = _invoke(localOutputStream);
/* 40:34 */           return localInputStream.read_boolean();
/* 41:   */         }
/* 42:   */         catch (ApplicationException localApplicationException)
/* 43:   */         {
/* 44:36 */           localInputStream = localApplicationException.getInputStream();
/* 45:37 */           localObject5 = localInputStream.read_string();
/* 46:38 */           throw new UnexpectedException((String)localObject5);
/* 47:   */         }
/* 48:   */         catch (RemarshalException localRemarshalException)
/* 49:   */         {
/* 50:40 */           return wlmable();
/* 51:   */         }
/* 52:   */         finally
/* 53:   */         {
/* 54:42 */           _releaseReply(localInputStream);
/* 55:   */         }
/* 56:   */       }
/* 57:   */       catch (SystemException localSystemException)
/* 58:   */       {
/* 59:45 */         throw Util.mapSystemException(localSystemException);
/* 60:   */       }
/* 61:   */     }
/* 62:48 */     ServantObject localServantObject = _servant_preinvoke("wlmable", CSIServant.class);
/* 63:49 */     if (localServantObject == null) {
/* 64:50 */       return wlmable();
/* 65:   */     }
/* 66:   */     try
/* 67:   */     {
/* 68:53 */       return ((CSIServant)localServantObject.servant).wlmable();
/* 69:   */     }
/* 70:   */     catch (Throwable localThrowable)
/* 71:   */     {
/* 72:55 */       localObject5 = (Throwable)Util.copyObject(localThrowable, _orb());
/* 73:56 */       throw Util.wrapException((Throwable)localObject5);
/* 74:   */     }
/* 75:   */     finally
/* 76:   */     {
/* 77:58 */       _servant_postinvoke(localServantObject);
/* 78:   */     }
/* 79:   */   }
/* 80:   */ }


/* Location:           D:\drops\jd\jars\UAEClientEJB20.jar
 * Qualified Name:     com.ibm.websphere.csi._CSIServant_Stub
 * JD-Core Version:    0.7.0.1
 */