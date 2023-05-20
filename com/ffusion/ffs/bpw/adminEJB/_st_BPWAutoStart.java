/*   1:    */ package com.ffusion.ffs.bpw.adminEJB;
/*   2:    */ 
/*   3:    */ import com.sybase.CORBA.LocalStack;
/*   4:    */ import com.sybase.CORBA.ObjectRef;
/*   5:    */ import com.sybase.CORBA.UnknownException;
/*   6:    */ import com.sybase.CORBA.UserException;
/*   7:    */ import com.sybase.CORBA._Request;
/*   8:    */ import org.omg.CORBA.TRANSIENT;
/*   9:    */ import org.omg.CORBA.UNKNOWN;
/*  10:    */ import org.omg.CORBA.portable.IDLEntity;
/*  11:    */ 
/*  12:    */ class _st_BPWAutoStart
/*  13:    */   extends ObjectRef
/*  14:    */   implements BPWAutoStart, IDLEntity
/*  15:    */ {
/*  16:    */   _st_BPWAutoStart(ObjectRef paramObjectRef)
/*  17:    */   {
/*  18: 16 */     super("IDL:com/ffusion/ffs/bpw/adminEJB/BPWAutoStart:1.0", paramObjectRef);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void run()
/*  22:    */   {
/*  23: 21 */     for (int i = 1;; i++)
/*  24:    */     {
/*  25: 23 */       _Request local_Request = null;
/*  26: 24 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  27: 25 */       boolean bool = false;
/*  28:    */       try
/*  29:    */       {
/*  30: 28 */         local_Request = __request("run");
/*  31: 29 */         bool = localLocalStack.isArgsOnLocal();
/*  32: 30 */         localLocalStack.setArgsOnLocal(false);
/*  33: 31 */         local_Request.invoke();
/*  34: 32 */         return;
/*  35:    */       }
/*  36:    */       catch (TRANSIENT localTRANSIENT)
/*  37:    */       {
/*  38: 36 */         if (i == 10) {
/*  39: 38 */           throw localTRANSIENT;
/*  40:    */         }
/*  41:    */       }
/*  42:    */       catch (UserException localUserException)
/*  43:    */       {
/*  44: 43 */         if (local_Request.isRMI()) {
/*  45: 45 */           throw new UNKNOWN(localUserException.toString());
/*  46:    */         }
/*  47: 47 */         throw new UNKNOWN(localUserException.type);
/*  48:    */       }
/*  49:    */       catch (UnknownException localUnknownException)
/*  50:    */       {
/*  51: 51 */         throw localUnknownException.getUnknown();
/*  52:    */       }
/*  53:    */       finally
/*  54:    */       {
/*  55: 55 */         if (local_Request != null) {
/*  56: 57 */           local_Request.close();
/*  57:    */         }
/*  58: 59 */         localLocalStack.setArgsOnLocal(bool);
/*  59:    */       }
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void start()
/*  64:    */   {
/*  65: 66 */     for (int i = 1;; i++)
/*  66:    */     {
/*  67: 68 */       _Request local_Request = null;
/*  68: 69 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  69: 70 */       boolean bool = false;
/*  70:    */       try
/*  71:    */       {
/*  72: 73 */         local_Request = __request("start");
/*  73: 74 */         bool = localLocalStack.isArgsOnLocal();
/*  74: 75 */         localLocalStack.setArgsOnLocal(false);
/*  75: 76 */         local_Request.invoke();
/*  76: 77 */         return;
/*  77:    */       }
/*  78:    */       catch (TRANSIENT localTRANSIENT)
/*  79:    */       {
/*  80: 81 */         if (i == 10) {
/*  81: 83 */           throw localTRANSIENT;
/*  82:    */         }
/*  83:    */       }
/*  84:    */       catch (UserException localUserException)
/*  85:    */       {
/*  86: 88 */         if (local_Request.isRMI()) {
/*  87: 90 */           throw new UNKNOWN(localUserException.toString());
/*  88:    */         }
/*  89: 92 */         throw new UNKNOWN(localUserException.type);
/*  90:    */       }
/*  91:    */       catch (UnknownException localUnknownException)
/*  92:    */       {
/*  93: 96 */         throw localUnknownException.getUnknown();
/*  94:    */       }
/*  95:    */       finally
/*  96:    */       {
/*  97:100 */         if (local_Request != null) {
/*  98:102 */           local_Request.close();
/*  99:    */         }
/* 100:104 */         localLocalStack.setArgsOnLocal(bool);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void stop()
/* 106:    */   {
/* 107:111 */     for (int i = 1;; i++)
/* 108:    */     {
/* 109:113 */       _Request local_Request = null;
/* 110:114 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 111:115 */       boolean bool = false;
/* 112:    */       try
/* 113:    */       {
/* 114:118 */         local_Request = __request("stop");
/* 115:119 */         bool = localLocalStack.isArgsOnLocal();
/* 116:120 */         localLocalStack.setArgsOnLocal(false);
/* 117:121 */         local_Request.invoke();
/* 118:122 */         return;
/* 119:    */       }
/* 120:    */       catch (TRANSIENT localTRANSIENT)
/* 121:    */       {
/* 122:126 */         if (i == 10) {
/* 123:128 */           throw localTRANSIENT;
/* 124:    */         }
/* 125:    */       }
/* 126:    */       catch (UserException localUserException)
/* 127:    */       {
/* 128:133 */         if (local_Request.isRMI()) {
/* 129:135 */           throw new UNKNOWN(localUserException.toString());
/* 130:    */         }
/* 131:137 */         throw new UNKNOWN(localUserException.type);
/* 132:    */       }
/* 133:    */       catch (UnknownException localUnknownException)
/* 134:    */       {
/* 135:141 */         throw localUnknownException.getUnknown();
/* 136:    */       }
/* 137:    */       finally
/* 138:    */       {
/* 139:145 */         if (local_Request != null) {
/* 140:147 */           local_Request.close();
/* 141:    */         }
/* 142:149 */         localLocalStack.setArgsOnLocal(bool);
/* 143:    */       }
/* 144:    */     }
/* 145:    */   }
/* 146:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB._st_BPWAutoStart
 * JD-Core Version:    0.7.0.1
 */