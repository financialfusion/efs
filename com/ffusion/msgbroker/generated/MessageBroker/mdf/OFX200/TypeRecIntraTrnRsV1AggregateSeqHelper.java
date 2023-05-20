/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class TypeRecIntraTrnRsV1AggregateSeqHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static TypeRecIntraTrnRsV1Aggregate[] clone(TypeRecIntraTrnRsV1Aggregate[] paramArrayOfTypeRecIntraTrnRsV1Aggregate)
/* 15:   */   {
/* 16:16 */     if (paramArrayOfTypeRecIntraTrnRsV1Aggregate == null) {
/* 17:18 */       return null;
/* 18:   */     }
/* 19:20 */     int i = paramArrayOfTypeRecIntraTrnRsV1Aggregate.length;
/* 20:21 */     TypeRecIntraTrnRsV1Aggregate[] arrayOfTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate[i];
/* 21:22 */     for (int j = 0; j < i; j++) {
/* 22:24 */       arrayOfTypeRecIntraTrnRsV1Aggregate[j] = TypeRecIntraTrnRsV1AggregateHelper.clone(paramArrayOfTypeRecIntraTrnRsV1Aggregate[j]);
/* 23:   */     }
/* 24:26 */     return arrayOfTypeRecIntraTrnRsV1Aggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypeRecIntraTrnRsV1Aggregate[] read(InputStream paramInputStream)
/* 28:   */   {
/* 29:32 */     int i = paramInputStream.read_ulong();
/* 30:33 */     TypeRecIntraTrnRsV1Aggregate[] arrayOfTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate[i];
/* 31:34 */     for (int j = 0; j < i; j++) {
/* 32:36 */       arrayOfTypeRecIntraTrnRsV1Aggregate[j] = TypeRecIntraTrnRsV1AggregateHelper.read(paramInputStream);
/* 33:   */     }
/* 34:38 */     return arrayOfTypeRecIntraTrnRsV1Aggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraTrnRsV1Aggregate[] paramArrayOfTypeRecIntraTrnRsV1Aggregate)
/* 38:   */   {
/* 39:45 */     if (paramArrayOfTypeRecIntraTrnRsV1Aggregate == null) {
/* 40:47 */       paramArrayOfTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate[0];
/* 41:   */     }
/* 42:49 */     int i = paramArrayOfTypeRecIntraTrnRsV1Aggregate.length;
/* 43:50 */     paramOutputStream.write_ulong(i);
/* 44:51 */     for (int j = 0; j < i; j++) {
/* 45:53 */       TypeRecIntraTrnRsV1AggregateHelper.write(paramOutputStream, paramArrayOfTypeRecIntraTrnRsV1Aggregate[j]);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:61 */     if (_type == null)
/* 52:   */     {
/* 53:63 */       ORB localORB = ORB.init();
/* 54:64 */       _type = localORB.create_sequence_tc(0, TypeRecIntraTrnRsV1AggregateHelper.type());
/* 55:   */     }
/* 56:66 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, TypeRecIntraTrnRsV1Aggregate[] paramArrayOfTypeRecIntraTrnRsV1Aggregate)
/* 60:   */   {
/* 61:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:74 */     write(localOutputStream, paramArrayOfTypeRecIntraTrnRsV1Aggregate);
/* 63:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static TypeRecIntraTrnRsV1Aggregate[] extract(Any paramAny)
/* 67:   */   {
/* 68:81 */     if (!paramAny.type().equal(type())) {
/* 69:83 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:85 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecIntraTrnRsV1AggregateSeq:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1AggregateSeqHelper
 * JD-Core Version:    0.7.0.1
 */