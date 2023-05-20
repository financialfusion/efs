/*  1:   */ package com.ffusion.ffs.bpw.adminEJB;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class IBPWAdmin__ListHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static IBPWAdmin[] clone(IBPWAdmin[] paramArrayOfIBPWAdmin)
/* 15:   */   {
/* 16:16 */     if (paramArrayOfIBPWAdmin == null) {
/* 17:18 */       return null;
/* 18:   */     }
/* 19:20 */     int i = paramArrayOfIBPWAdmin.length;
/* 20:21 */     IBPWAdmin[] arrayOfIBPWAdmin = new IBPWAdmin[i];
/* 21:22 */     for (int j = 0; j < i; j++) {
/* 22:24 */       arrayOfIBPWAdmin[j] = paramArrayOfIBPWAdmin[j];
/* 23:   */     }
/* 24:26 */     return arrayOfIBPWAdmin;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static IBPWAdmin[] read(InputStream paramInputStream)
/* 28:   */   {
/* 29:32 */     int i = paramInputStream.read_ulong();
/* 30:33 */     IBPWAdmin[] arrayOfIBPWAdmin = new IBPWAdmin[i];
/* 31:34 */     for (int j = 0; j < i; j++) {
/* 32:36 */       arrayOfIBPWAdmin[j] = IBPWAdminHelper.read(paramInputStream);
/* 33:   */     }
/* 34:38 */     return arrayOfIBPWAdmin;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, IBPWAdmin[] paramArrayOfIBPWAdmin)
/* 38:   */   {
/* 39:45 */     if (paramArrayOfIBPWAdmin == null) {
/* 40:47 */       paramArrayOfIBPWAdmin = new IBPWAdmin[0];
/* 41:   */     }
/* 42:49 */     int i = paramArrayOfIBPWAdmin.length;
/* 43:50 */     paramOutputStream.write_ulong(i);
/* 44:51 */     for (int j = 0; j < i; j++) {
/* 45:53 */       paramOutputStream.write_Object((org.omg.CORBA.Object)paramArrayOfIBPWAdmin[j]);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:61 */     if (_type == null)
/* 52:   */     {
/* 53:63 */       ORB localORB = ORB.init();
/* 54:64 */       _type = localORB.create_sequence_tc(0, IBPWAdminHelper.type());
/* 55:   */     }
/* 56:66 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, IBPWAdmin[] paramArrayOfIBPWAdmin)
/* 60:   */   {
/* 61:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:74 */     write(localOutputStream, paramArrayOfIBPWAdmin);
/* 63:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static IBPWAdmin[] extract(Any paramAny)
/* 67:   */   {
/* 68:81 */     if (!paramAny.type().equal(type())) {
/* 69:83 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:85 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:90 */     return "IDL:com/ffusion/ffs/bpw/adminEJB/IBPWAdmin__List:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.IBPWAdmin__ListHelper
 * JD-Core Version:    0.7.0.1
 */