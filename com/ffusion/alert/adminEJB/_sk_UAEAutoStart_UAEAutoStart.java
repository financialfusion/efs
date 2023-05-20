/*   1:    */ package com.ffusion.alert.adminEJB;
/*   2:    */ 
/*   3:    */ import com.sybase.CORBA._ServerRequest;
/*   4:    */ import com.sybase.CORBA.iiop.Connection;
/*   5:    */ import com.sybase.jaguar.server.Jaguar;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import org.omg.CORBA.SystemException;
/*   8:    */ import org.omg.CORBA.UNKNOWN;
/*   9:    */ import org.omg.CORBA.portable.InputStream;
/*  10:    */ import org.omg.CORBA.portable.OutputStream;
/*  11:    */ 
/*  12:    */ public abstract class _sk_UAEAutoStart_UAEAutoStart
/*  13:    */ {
/*  14: 17 */   private static HashMap _methods = new HashMap(6);
/*  15:    */   
/*  16:    */   static
/*  17:    */   {
/*  18: 18 */     _methods.put("run", new Integer(0));
/*  19: 19 */     _methods.put("start", new Integer(1));
/*  20: 20 */     _methods.put("stop", new Integer(2));
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static Object create()
/*  24:    */     throws Exception
/*  25:    */   {
/*  26: 26 */     return new UAEAutoStartService();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream)
/*  30:    */   {
/*  31: 35 */     return invoke(paramObject, paramString, paramInputStream, paramOutputStream, 0);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  35:    */   {
/*  36: 45 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/*  37: 46 */     UAEAutoStartService localUAEAutoStartService = (UAEAutoStartService)paramObject;
/*  38: 47 */     Integer localInteger = (Integer)_methods.get(paramString);
/*  39: 48 */     if (localInteger == null) {
/*  40: 50 */       return "org.omg.CORBA.BAD_OPERATION";
/*  41:    */     }
/*  42:    */     String str;
/*  43: 52 */     switch (localInteger.intValue())
/*  44:    */     {
/*  45:    */     case 0: 
/*  46:    */       try
/*  47:    */       {
/*  48: 59 */         localUAEAutoStartService.run();
/*  49:    */       }
/*  50:    */       catch (Throwable localThrowable1)
/*  51:    */       {
/*  52: 64 */         localThrowable1.printStackTrace(Jaguar.log);
/*  53: 65 */         str = null;
/*  54: 66 */         if ((!(localThrowable1 instanceof SystemException)) || 
/*  55: 67 */           ((localThrowable1 instanceof UNKNOWN))) {
/*  56: 69 */           str = localThrowable1.toString();
/*  57:    */         }
/*  58: 71 */         Connection.insertExToServiceContext(paramOutputStream, str);
/*  59: 72 */         return localThrowable1.getClass().getName();
/*  60:    */       }
/*  61:    */     case 1: 
/*  62:    */       try
/*  63:    */       {
/*  64: 81 */         localUAEAutoStartService.start();
/*  65:    */       }
/*  66:    */       catch (Throwable localThrowable2)
/*  67:    */       {
/*  68: 86 */         localThrowable2.printStackTrace(Jaguar.log);
/*  69: 87 */         str = null;
/*  70: 88 */         if ((!(localThrowable2 instanceof SystemException)) || 
/*  71: 89 */           ((localThrowable2 instanceof UNKNOWN))) {
/*  72: 91 */           str = localThrowable2.toString();
/*  73:    */         }
/*  74: 93 */         Connection.insertExToServiceContext(paramOutputStream, str);
/*  75: 94 */         return localThrowable2.getClass().getName();
/*  76:    */       }
/*  77:    */     case 2: 
/*  78:    */       try
/*  79:    */       {
/*  80:103 */         localUAEAutoStartService.stop();
/*  81:    */       }
/*  82:    */       catch (Throwable localThrowable3)
/*  83:    */       {
/*  84:108 */         localThrowable3.printStackTrace(Jaguar.log);
/*  85:109 */         str = null;
/*  86:110 */         if ((!(localThrowable3 instanceof SystemException)) || 
/*  87:111 */           ((localThrowable3 instanceof UNKNOWN))) {
/*  88:113 */           str = localThrowable3.toString();
/*  89:    */         }
/*  90:115 */         Connection.insertExToServiceContext(paramOutputStream, str);
/*  91:116 */         return localThrowable3.getClass().getName();
/*  92:    */       }
/*  93:    */     }
/*  94:121 */     return null;
/*  95:    */   }
/*  96:    */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB._sk_UAEAutoStart_UAEAutoStart
 * JD-Core Version:    0.7.0.1
 */