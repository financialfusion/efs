/*  1:   */ package com.ffusion.ffs.bpw.BPWServices;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class BPWServices__ListHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static BPWServices[] clone(BPWServices[] paramArrayOfBPWServices)
/* 15:   */   {
/* 16:16 */     if (paramArrayOfBPWServices == null) {
/* 17:18 */       return null;
/* 18:   */     }
/* 19:20 */     int i = paramArrayOfBPWServices.length;
/* 20:21 */     BPWServices[] arrayOfBPWServices = new BPWServices[i];
/* 21:22 */     for (int j = 0; j < i; j++) {
/* 22:24 */       arrayOfBPWServices[j] = paramArrayOfBPWServices[j];
/* 23:   */     }
/* 24:26 */     return arrayOfBPWServices;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static BPWServices[] read(InputStream paramInputStream)
/* 28:   */   {
/* 29:32 */     int i = paramInputStream.read_ulong();
/* 30:33 */     BPWServices[] arrayOfBPWServices = new BPWServices[i];
/* 31:34 */     for (int j = 0; j < i; j++) {
/* 32:36 */       arrayOfBPWServices[j] = BPWServicesHelper.read(paramInputStream);
/* 33:   */     }
/* 34:38 */     return arrayOfBPWServices;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, BPWServices[] paramArrayOfBPWServices)
/* 38:   */   {
/* 39:45 */     if (paramArrayOfBPWServices == null) {
/* 40:47 */       paramArrayOfBPWServices = new BPWServices[0];
/* 41:   */     }
/* 42:49 */     int i = paramArrayOfBPWServices.length;
/* 43:50 */     paramOutputStream.write_ulong(i);
/* 44:51 */     for (int j = 0; j < i; j++) {
/* 45:53 */       paramOutputStream.write_Object((org.omg.CORBA.Object)paramArrayOfBPWServices[j]);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:61 */     if (_type == null)
/* 52:   */     {
/* 53:63 */       ORB localORB = ORB.init();
/* 54:64 */       _type = localORB.create_sequence_tc(0, BPWServicesHelper.type());
/* 55:   */     }
/* 56:66 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, BPWServices[] paramArrayOfBPWServices)
/* 60:   */   {
/* 61:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:74 */     write(localOutputStream, paramArrayOfBPWServices);
/* 63:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static BPWServices[] extract(Any paramAny)
/* 67:   */   {
/* 68:81 */     if (!paramAny.type().equal(type())) {
/* 69:83 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:85 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:90 */     return "IDL:com/ffusion/ffs/bpw/BPWServices/BPWServices__List:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.BPWServices__ListHelper
 * JD-Core Version:    0.7.0.1
 */