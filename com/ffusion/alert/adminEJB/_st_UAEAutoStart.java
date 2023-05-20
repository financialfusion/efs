/*   1:    */ package com.ffusion.alert.adminEJB;
/*   2:    */ 
/*   3:    */ import com.sybase.CORBA.ObjectRef;
/*   4:    */ import com.sybase.CORBA.UnknownException;
/*   5:    */ import com.sybase.CORBA.UserException;
/*   6:    */ import com.sybase.CORBA._Request;
/*   7:    */ import org.omg.CORBA.TRANSIENT;
/*   8:    */ import org.omg.CORBA.UNKNOWN;
/*   9:    */ import org.omg.CORBA.portable.IDLEntity;
/*  10:    */ 
/*  11:    */ class _st_UAEAutoStart
/*  12:    */   extends ObjectRef
/*  13:    */   implements UAEAutoStart, IDLEntity
/*  14:    */ {
/*  15:    */   _st_UAEAutoStart(ObjectRef paramObjectRef)
/*  16:    */   {
/*  17: 16 */     super("IDL:com/ffusion/alert/adminEJB/UAEAutoStart:1.0", paramObjectRef);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void run()
/*  21:    */   {
/*  22: 21 */     for (int i = 1;; i++)
/*  23:    */     {
/*  24: 23 */       _Request local_Request = null;
/*  25:    */       try
/*  26:    */       {
/*  27: 26 */         local_Request = __request("run");
/*  28: 27 */         local_Request.invoke();
/*  29: 28 */         return;
/*  30:    */       }
/*  31:    */       catch (TRANSIENT localTRANSIENT)
/*  32:    */       {
/*  33: 32 */         if (i == 10) {
/*  34: 34 */           throw localTRANSIENT;
/*  35:    */         }
/*  36:    */       }
/*  37:    */       catch (UserException localUserException)
/*  38:    */       {
/*  39: 39 */         if (local_Request.isRMI()) {
/*  40: 41 */           throw new UNKNOWN(localUserException.toString());
/*  41:    */         }
/*  42: 43 */         throw new UNKNOWN(localUserException.type);
/*  43:    */       }
/*  44:    */       catch (UnknownException localUnknownException)
/*  45:    */       {
/*  46: 47 */         throw localUnknownException.getUnknown();
/*  47:    */       }
/*  48:    */       finally
/*  49:    */       {
/*  50: 51 */         if (local_Request != null) {
/*  51: 53 */           local_Request.close();
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void start()
/*  58:    */   {
/*  59: 61 */     for (int i = 1;; i++)
/*  60:    */     {
/*  61: 63 */       _Request local_Request = null;
/*  62:    */       try
/*  63:    */       {
/*  64: 66 */         local_Request = __request("start");
/*  65: 67 */         local_Request.invoke();
/*  66: 68 */         return;
/*  67:    */       }
/*  68:    */       catch (TRANSIENT localTRANSIENT)
/*  69:    */       {
/*  70: 72 */         if (i == 10) {
/*  71: 74 */           throw localTRANSIENT;
/*  72:    */         }
/*  73:    */       }
/*  74:    */       catch (UserException localUserException)
/*  75:    */       {
/*  76: 79 */         if (local_Request.isRMI()) {
/*  77: 81 */           throw new UNKNOWN(localUserException.toString());
/*  78:    */         }
/*  79: 83 */         throw new UNKNOWN(localUserException.type);
/*  80:    */       }
/*  81:    */       catch (UnknownException localUnknownException)
/*  82:    */       {
/*  83: 87 */         throw localUnknownException.getUnknown();
/*  84:    */       }
/*  85:    */       finally
/*  86:    */       {
/*  87: 91 */         if (local_Request != null) {
/*  88: 93 */           local_Request.close();
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void stop()
/*  95:    */   {
/*  96:101 */     for (int i = 1;; i++)
/*  97:    */     {
/*  98:103 */       _Request local_Request = null;
/*  99:    */       try
/* 100:    */       {
/* 101:106 */         local_Request = __request("stop");
/* 102:107 */         local_Request.invoke();
/* 103:108 */         return;
/* 104:    */       }
/* 105:    */       catch (TRANSIENT localTRANSIENT)
/* 106:    */       {
/* 107:112 */         if (i == 10) {
/* 108:114 */           throw localTRANSIENT;
/* 109:    */         }
/* 110:    */       }
/* 111:    */       catch (UserException localUserException)
/* 112:    */       {
/* 113:119 */         if (local_Request.isRMI()) {
/* 114:121 */           throw new UNKNOWN(localUserException.toString());
/* 115:    */         }
/* 116:123 */         throw new UNKNOWN(localUserException.type);
/* 117:    */       }
/* 118:    */       catch (UnknownException localUnknownException)
/* 119:    */       {
/* 120:127 */         throw localUnknownException.getUnknown();
/* 121:    */       }
/* 122:    */       finally
/* 123:    */       {
/* 124:131 */         if (local_Request != null) {
/* 125:133 */           local_Request.close();
/* 126:    */         }
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB._st_UAEAutoStart
 * JD-Core Version:    0.7.0.1
 */