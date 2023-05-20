/*  1:   */ package com.ffusion.alert.adminEJB;
/*  2:   */ 
/*  3:   */ import com.sybase.CORBA.ObjectRef;
/*  4:   */ import com.sybase.CORBA.UserException;
/*  5:   */ import com.sybase.CORBA._Request;
/*  6:   */ import com.sybase.ejb.EJBHome;
/*  7:   */ import javax.ejb.CreateException;
/*  8:   */ import javax.ejb.DuplicateKeyException;
/*  9:   */ import org.omg.CORBA.SystemException;
/* 10:   */ import org.omg.CORBA.TRANSIENT;
/* 11:   */ import org.omg.CORBA.portable.IDLEntity;
/* 12:   */ import org.omg.CORBA.portable.InputStream;
/* 13:   */ 
/* 14:   */ public class IAEAlertAdminHome_Stub
/* 15:   */   extends EJBHome
/* 16:   */   implements IAEAlertAdminHome, IDLEntity
/* 17:   */ {
/* 18:   */   public IAEAlertAdminHome_Stub() {}
/* 19:   */   
/* 20:   */   public IAEAlertAdminHome_Stub(ObjectRef paramObjectRef)
/* 21:   */   {
/* 22:21 */     super("RMI:com.ffusion.alert.adminEJB.IAEAlertAdminHome:0000000000000000", paramObjectRef);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public IAEAlertAdmin create()
/* 26:   */     throws java.rmi.RemoteException, CreateException
/* 27:   */   {
/* 28:27 */     for (int i = 1;; i++)
/* 29:   */     {
/* 30:29 */       _Request local_Request = null;
/* 31:   */       try
/* 32:   */       {
/* 33:32 */         local_Request = __request("create");
/* 34:33 */         local_Request.invoke();
/* 35:34 */         InputStream localInputStream = local_Request.getInputStream();
/* 36:   */         
/* 37:36 */         localObject4 = IAEAlertAdminHelper.read(localInputStream);
/* 38:37 */         return localObject4;
/* 39:   */       }
/* 40:   */       catch (TRANSIENT localTRANSIENT)
/* 41:   */       {
/* 42:41 */         if (i == 10) {
/* 43:43 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 44:   */         }
/* 45:   */       }
/* 46:   */       catch (UserException localUserException)
/* 47:   */       {
/* 48:   */         Object localObject4;
/* 49:48 */         if (local_Request.isRMI())
/* 50:   */         {
/* 51:50 */           if (localUserException.type.equals("IDL:javax/ejb/CreateEx:1.0")) {
/* 52:52 */             throw ((CreateException)local_Request.read_value(CreateException.class));
/* 53:   */           }
/* 54:   */         }
/* 55:57 */         else if (localUserException.type.equals("IDL:CtsComponents/CreateException:1.0"))
/* 56:   */         {
/* 57:59 */           localObject4 = localUserException.input.read_string();
/* 58:60 */           if (((String)localObject4).equals("DUPLICATE_KEY")) {
/* 59:62 */             throw new DuplicateKeyException((String)localObject4);
/* 60:   */           }
/* 61:66 */           throw new CreateException((String)localObject4);
/* 62:   */         }
/* 63:70 */         throw new java.rmi.RemoteException(localUserException.type);
/* 64:   */       }
/* 65:   */       catch (SystemException localSystemException)
/* 66:   */       {
/* 67:74 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 68:   */       }
/* 69:   */       finally
/* 70:   */       {
/* 71:78 */         if (local_Request != null) {
/* 72:80 */           local_Request.close();
/* 73:   */         }
/* 74:   */       }
/* 75:   */     }
/* 76:   */   }
/* 77:   */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.IAEAlertAdminHome_Stub
 * JD-Core Version:    0.7.0.1
 */