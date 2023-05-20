/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices;
/*  2:   */ 
/*  3:   */ import com.sybase.CORBA.LocalStack;
/*  4:   */ import com.sybase.CORBA.ObjectRef;
/*  5:   */ import com.sybase.CORBA.UserException;
/*  6:   */ import com.sybase.CORBA._Request;
/*  7:   */ import com.sybase.ejb.EJBHome;
/*  8:   */ import javax.ejb.CreateException;
/*  9:   */ import javax.ejb.DuplicateKeyException;
/* 10:   */ import org.omg.CORBA.SystemException;
/* 11:   */ import org.omg.CORBA.TRANSIENT;
/* 12:   */ import org.omg.CORBA.portable.IDLEntity;
/* 13:   */ import org.omg.CORBA.portable.InputStream;
/* 14:   */ 
/* 15:   */ public class OFX200BPWServicesHome_Stub
/* 16:   */   extends EJBHome
/* 17:   */   implements OFX200BPWServicesHome, IDLEntity
/* 18:   */ {
/* 19:   */   public OFX200BPWServicesHome_Stub() {}
/* 20:   */   
/* 21:   */   public OFX200BPWServicesHome_Stub(ObjectRef paramObjectRef)
/* 22:   */   {
/* 23:21 */     super("RMI:com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.OFX200BPWServicesHome:0000000000000000", paramObjectRef);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public IOFX200BPWServices create()
/* 27:   */     throws java.rmi.RemoteException, CreateException
/* 28:   */   {
/* 29:27 */     for (int i = 1;; i++)
/* 30:   */     {
/* 31:29 */       _Request local_Request = null;
/* 32:30 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 33:31 */       boolean bool = false;
/* 34:   */       try
/* 35:   */       {
/* 36:34 */         local_Request = __request("create");
/* 37:35 */         bool = localLocalStack.isArgsOnLocal();
/* 38:36 */         localLocalStack.setArgsOnLocal(false);
/* 39:37 */         local_Request.invoke();
/* 40:38 */         InputStream localInputStream = local_Request.getInputStream();
/* 41:   */         
/* 42:40 */         localObject4 = IOFX200BPWServicesHelper.read(localInputStream);
/* 43:41 */         return localObject4;
/* 44:   */       }
/* 45:   */       catch (TRANSIENT localTRANSIENT)
/* 46:   */       {
/* 47:45 */         if (i == 10) {
/* 48:47 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 49:   */         }
/* 50:   */       }
/* 51:   */       catch (UserException localUserException)
/* 52:   */       {
/* 53:   */         Object localObject4;
/* 54:52 */         if (local_Request.isRMI())
/* 55:   */         {
/* 56:54 */           if (localUserException.type.equals("IDL:javax/ejb/CreateEx:1.0")) {
/* 57:56 */             throw ((CreateException)local_Request.read_value(CreateException.class));
/* 58:   */           }
/* 59:   */         }
/* 60:61 */         else if (localUserException.type.equals("IDL:CtsComponents/CreateException:1.0"))
/* 61:   */         {
/* 62:63 */           localObject4 = localUserException.input.read_string();
/* 63:64 */           if (((String)localObject4).equals("DUPLICATE_KEY")) {
/* 64:66 */             throw new DuplicateKeyException((String)localObject4);
/* 65:   */           }
/* 66:70 */           throw new CreateException((String)localObject4);
/* 67:   */         }
/* 68:74 */         throw new java.rmi.RemoteException(localUserException.type);
/* 69:   */       }
/* 70:   */       catch (SystemException localSystemException)
/* 71:   */       {
/* 72:78 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 73:   */       }
/* 74:   */       finally
/* 75:   */       {
/* 76:82 */         if (local_Request != null) {
/* 77:84 */           local_Request.close();
/* 78:   */         }
/* 79:86 */         localLocalStack.setArgsOnLocal(bool);
/* 80:   */       }
/* 81:   */     }
/* 82:   */   }
/* 83:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.OFX200BPWServicesHome_Stub
 * JD-Core Version:    0.7.0.1
 */