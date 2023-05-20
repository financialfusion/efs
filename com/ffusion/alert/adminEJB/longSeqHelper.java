/*  1:   */ package com.ffusion.alert.adminEJB;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TCKind;
/*  7:   */ import org.omg.CORBA.TypeCode;
/*  8:   */ import org.omg.CORBA.portable.InputStream;
/*  9:   */ import org.omg.CORBA.portable.OutputStream;
/* 10:   */ 
/* 11:   */ public abstract class longSeqHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static int[] clone(int[] paramArrayOfInt)
/* 16:   */   {
/* 17:16 */     if (paramArrayOfInt == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     int i = paramArrayOfInt.length;
/* 21:21 */     int[] arrayOfInt = new int[i];
/* 22:22 */     for (int j = 0; j < i; j++) {
/* 23:24 */       arrayOfInt[j] = paramArrayOfInt[j];
/* 24:   */     }
/* 25:26 */     return arrayOfInt;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static int[] read(InputStream paramInputStream)
/* 29:   */   {
/* 30:32 */     int i = paramInputStream.read_ulong();
/* 31:33 */     int[] arrayOfInt = new int[i];
/* 32:34 */     for (int j = 0; j < i; j++) {
/* 33:36 */       arrayOfInt[j] = paramInputStream.read_long();
/* 34:   */     }
/* 35:38 */     return arrayOfInt;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static void write(OutputStream paramOutputStream, int[] paramArrayOfInt)
/* 39:   */   {
/* 40:45 */     if (paramArrayOfInt == null) {
/* 41:47 */       paramArrayOfInt = new int[0];
/* 42:   */     }
/* 43:49 */     int i = paramArrayOfInt.length;
/* 44:50 */     paramOutputStream.write_ulong(i);
/* 45:51 */     for (int j = 0; j < i; j++) {
/* 46:53 */       paramOutputStream.write_long(paramArrayOfInt[j]);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static TypeCode type()
/* 51:   */   {
/* 52:61 */     if (_type == null)
/* 53:   */     {
/* 54:63 */       ORB localORB = ORB.init();
/* 55:64 */       _type = localORB.create_sequence_tc(0, localORB.get_primitive_tc(TCKind.tk_long));
/* 56:   */     }
/* 57:66 */     return _type;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static void insert(Any paramAny, int[] paramArrayOfInt)
/* 61:   */   {
/* 62:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 63:74 */     write(localOutputStream, paramArrayOfInt);
/* 64:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static int[] extract(Any paramAny)
/* 68:   */   {
/* 69:81 */     if (!paramAny.type().equal(type())) {
/* 70:83 */       throw new BAD_OPERATION();
/* 71:   */     }
/* 72:85 */     return read(paramAny.create_input_stream());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static String id()
/* 76:   */   {
/* 77:90 */     return "IDL:com/ffusion/alert/adminEJB/longSeq:1.0";
/* 78:   */   }
/* 79:   */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.longSeqHelper
 * JD-Core Version:    0.7.0.1
 */