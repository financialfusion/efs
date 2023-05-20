/*  1:   */ package com.ffusion.ffs.bpw.interfaces;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class ConsumerCrossRefInfoSeqHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static ConsumerCrossRefInfo[] clone(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 15:   */   {
/* 16:16 */     if (paramArrayOfConsumerCrossRefInfo == null) {
/* 17:18 */       return null;
/* 18:   */     }
/* 19:20 */     int i = paramArrayOfConsumerCrossRefInfo.length;
/* 20:21 */     ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = new ConsumerCrossRefInfo[i];
/* 21:22 */     for (int j = 0; j < i; j++) {
/* 22:24 */       arrayOfConsumerCrossRefInfo[j] = ConsumerCrossRefInfoHelper.clone(paramArrayOfConsumerCrossRefInfo[j]);
/* 23:   */     }
/* 24:26 */     return arrayOfConsumerCrossRefInfo;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static ConsumerCrossRefInfo[] read(InputStream paramInputStream)
/* 28:   */   {
/* 29:32 */     int i = paramInputStream.read_ulong();
/* 30:33 */     ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo = new ConsumerCrossRefInfo[i];
/* 31:34 */     for (int j = 0; j < i; j++) {
/* 32:36 */       arrayOfConsumerCrossRefInfo[j] = ConsumerCrossRefInfoHelper.read(paramInputStream);
/* 33:   */     }
/* 34:38 */     return arrayOfConsumerCrossRefInfo;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 38:   */   {
/* 39:45 */     if (paramArrayOfConsumerCrossRefInfo == null) {
/* 40:47 */       paramArrayOfConsumerCrossRefInfo = new ConsumerCrossRefInfo[0];
/* 41:   */     }
/* 42:49 */     int i = paramArrayOfConsumerCrossRefInfo.length;
/* 43:50 */     paramOutputStream.write_ulong(i);
/* 44:51 */     for (int j = 0; j < i; j++) {
/* 45:53 */       ConsumerCrossRefInfoHelper.write(paramOutputStream, paramArrayOfConsumerCrossRefInfo[j]);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:61 */     if (_type == null)
/* 52:   */     {
/* 53:63 */       ORB localORB = ORB.init();
/* 54:64 */       _type = localORB.create_sequence_tc(0, ConsumerCrossRefInfoHelper.type());
/* 55:   */     }
/* 56:66 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 60:   */   {
/* 61:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:74 */     write(localOutputStream, paramArrayOfConsumerCrossRefInfo);
/* 63:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static ConsumerCrossRefInfo[] extract(Any paramAny)
/* 67:   */   {
/* 68:81 */     if (!paramAny.type().equal(type())) {
/* 69:83 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:85 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:90 */     return "IDL:com/ffusion/ffs/bpw/interfaces/ConsumerCrossRefInfoSeq:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfoSeqHelper
 * JD-Core Version:    0.7.0.1
 */