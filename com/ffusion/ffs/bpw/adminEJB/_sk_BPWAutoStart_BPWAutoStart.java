/*   1:    */ package com.ffusion.ffs.bpw.adminEJB;
/*   2:    */ 
/*   3:    */ import com.sybase.CORBA.LocalFrame;
/*   4:    */ import com.sybase.CORBA.LocalStack;
/*   5:    */ import com.sybase.CORBA._ServerRequest;
/*   6:    */ import com.sybase.CORBA.iiop.Connection;
/*   7:    */ import com.sybase.jaguar.server.Jaguar;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import org.omg.CORBA.portable.InputStream;
/*  10:    */ import org.omg.CORBA.portable.OutputStream;
/*  11:    */ 
/*  12:    */ public abstract class _sk_BPWAutoStart_BPWAutoStart
/*  13:    */ {
/*  14: 17 */   private static HashMap _methods = new HashMap(6);
/*  15:    */   private static HashMap _localMethods;
/*  16:    */   private static HashMap _localMethods2;
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20: 18 */     _methods.put("run", new Integer(0));
/*  21: 19 */     _methods.put("start", new Integer(1));
/*  22: 20 */     _methods.put("stop", new Integer(2));
/*  23:    */     
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31: 29 */     _localMethods = new HashMap(6);
/*  32: 30 */     _localMethods2 = new HashMap(6);
/*  33: 31 */     _localMethods.put("#run", new Integer(0));
/*  34: 32 */     _localMethods2.put("run", new Integer(0));
/*  35: 33 */     _localMethods.put("#start", new Integer(1));
/*  36: 34 */     _localMethods2.put("start", new Integer(1));
/*  37: 35 */     _localMethods.put("#stop", new Integer(2));
/*  38: 36 */     _localMethods2.put("stop", new Integer(2));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Object create()
/*  42:    */     throws Exception
/*  43:    */   {
/*  44: 42 */     return new BPWAutoStartService();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream)
/*  48:    */   {
/*  49: 51 */     return invoke(paramObject, paramString, paramInputStream, paramOutputStream, 0);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  53:    */   {
/*  54: 61 */     if ((paramString.startsWith("#")) || (LocalStack.getCurrent().isArgsOnLocal())) {
/*  55: 63 */       return localInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  56:    */     }
/*  57: 67 */     return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static String remoteInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  61:    */   {
/*  62: 78 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/*  63: 79 */     BPWAutoStartService localBPWAutoStartService = (BPWAutoStartService)paramObject;
/*  64: 80 */     Integer localInteger = (Integer)_methods.get(paramString);
/*  65: 81 */     if (localInteger == null) {
/*  66: 83 */       return "org.omg.CORBA.BAD_OPERATION";
/*  67:    */     }
/*  68: 85 */     switch (localInteger.intValue())
/*  69:    */     {
/*  70:    */     case 0: 
/*  71:    */       try
/*  72:    */       {
/*  73: 92 */         localBPWAutoStartService.run();
/*  74:    */       }
/*  75:    */       catch (Throwable localThrowable1)
/*  76:    */       {
/*  77: 97 */         localThrowable1.printStackTrace(Jaguar.log);
/*  78: 98 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, false);
/*  79: 99 */         return localThrowable1.getClass().getName();
/*  80:    */       }
/*  81:    */     case 1: 
/*  82:    */       try
/*  83:    */       {
/*  84:108 */         localBPWAutoStartService.start();
/*  85:    */       }
/*  86:    */       catch (Throwable localThrowable2)
/*  87:    */       {
/*  88:113 */         localThrowable2.printStackTrace(Jaguar.log);
/*  89:114 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, false);
/*  90:115 */         return localThrowable2.getClass().getName();
/*  91:    */       }
/*  92:    */     case 2: 
/*  93:    */       try
/*  94:    */       {
/*  95:124 */         localBPWAutoStartService.stop();
/*  96:    */       }
/*  97:    */       catch (Throwable localThrowable3)
/*  98:    */       {
/*  99:129 */         localThrowable3.printStackTrace(Jaguar.log);
/* 100:130 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, false);
/* 101:131 */         return localThrowable3.getClass().getName();
/* 102:    */       }
/* 103:    */     }
/* 104:136 */     return null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static String localInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/* 108:    */   {
/* 109:146 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/* 110:147 */     BPWAutoStartService localBPWAutoStartService = (BPWAutoStartService)paramObject;
/* 111:148 */     Integer localInteger = null;
/* 112:149 */     int i = 0;
/* 113:150 */     if (!paramString.startsWith("#"))
/* 114:    */     {
/* 115:152 */       localInteger = (Integer)_localMethods2.get(paramString);
/* 116:153 */       if (localInteger != null) {
/* 117:154 */         i = 1;
/* 118:    */       }
/* 119:    */     }
/* 120:    */     else
/* 121:    */     {
/* 122:158 */       localInteger = (Integer)_localMethods.get(paramString);
/* 123:    */     }
/* 124:160 */     if (localInteger == null) {
/* 125:162 */       return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/* 126:    */     }
/* 127:164 */     LocalFrame localLocalFrame = LocalStack.getCurrent().top();
/* 128:165 */     switch (localInteger.intValue())
/* 129:    */     {
/* 130:    */     case 0: 
/* 131:    */       try
/* 132:    */       {
/* 133:172 */         localBPWAutoStartService.run();
/* 134:    */       }
/* 135:    */       catch (Throwable localThrowable1)
/* 136:    */       {
/* 137:177 */         localThrowable1.printStackTrace(Jaguar.log);
/* 138:178 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, false);
/* 139:179 */         return localThrowable1.getClass().getName();
/* 140:    */       }
/* 141:    */     case 1: 
/* 142:    */       try
/* 143:    */       {
/* 144:188 */         localBPWAutoStartService.start();
/* 145:    */       }
/* 146:    */       catch (Throwable localThrowable2)
/* 147:    */       {
/* 148:193 */         localThrowable2.printStackTrace(Jaguar.log);
/* 149:194 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, false);
/* 150:195 */         return localThrowable2.getClass().getName();
/* 151:    */       }
/* 152:    */     case 2: 
/* 153:    */       try
/* 154:    */       {
/* 155:204 */         localBPWAutoStartService.stop();
/* 156:    */       }
/* 157:    */       catch (Throwable localThrowable3)
/* 158:    */       {
/* 159:209 */         localThrowable3.printStackTrace(Jaguar.log);
/* 160:210 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, false);
/* 161:211 */         return localThrowable3.getClass().getName();
/* 162:    */       }
/* 163:    */     }
/* 164:216 */     return null;
/* 165:    */   }
/* 166:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB._sk_BPWAutoStart_BPWAutoStart
 * JD-Core Version:    0.7.0.1
 */