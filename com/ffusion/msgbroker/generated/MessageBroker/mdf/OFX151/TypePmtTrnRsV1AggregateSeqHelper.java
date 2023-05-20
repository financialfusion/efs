/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class TypePmtTrnRsV1AggregateSeqHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static TypePmtTrnRsV1Aggregate[] clone(TypePmtTrnRsV1Aggregate[] paramArrayOfTypePmtTrnRsV1Aggregate)
/* 15:   */   {
/* 16:16 */     if (paramArrayOfTypePmtTrnRsV1Aggregate == null) {
/* 17:18 */       return null;
/* 18:   */     }
/* 19:20 */     int i = paramArrayOfTypePmtTrnRsV1Aggregate.length;
/* 20:21 */     TypePmtTrnRsV1Aggregate[] arrayOfTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate[i];
/* 21:22 */     for (int j = 0; j < i; j++) {
/* 22:24 */       arrayOfTypePmtTrnRsV1Aggregate[j] = TypePmtTrnRsV1AggregateHelper.clone(paramArrayOfTypePmtTrnRsV1Aggregate[j]);
/* 23:   */     }
/* 24:26 */     return arrayOfTypePmtTrnRsV1Aggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypePmtTrnRsV1Aggregate[] read(InputStream paramInputStream)
/* 28:   */   {
/* 29:32 */     int i = paramInputStream.read_ulong();
/* 30:33 */     TypePmtTrnRsV1Aggregate[] arrayOfTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate[i];
/* 31:34 */     for (int j = 0; j < i; j++) {
/* 32:36 */       arrayOfTypePmtTrnRsV1Aggregate[j] = TypePmtTrnRsV1AggregateHelper.read(paramInputStream);
/* 33:   */     }
/* 34:38 */     return arrayOfTypePmtTrnRsV1Aggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypePmtTrnRsV1Aggregate[] paramArrayOfTypePmtTrnRsV1Aggregate)
/* 38:   */   {
/* 39:45 */     if (paramArrayOfTypePmtTrnRsV1Aggregate == null) {
/* 40:47 */       paramArrayOfTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate[0];
/* 41:   */     }
/* 42:49 */     int i = paramArrayOfTypePmtTrnRsV1Aggregate.length;
/* 43:50 */     paramOutputStream.write_ulong(i);
/* 44:51 */     for (int j = 0; j < i; j++) {
/* 45:53 */       TypePmtTrnRsV1AggregateHelper.write(paramOutputStream, paramArrayOfTypePmtTrnRsV1Aggregate[j]);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:61 */     if (_type == null)
/* 52:   */     {
/* 53:63 */       ORB localORB = ORB.init();
/* 54:64 */       _type = localORB.create_sequence_tc(0, TypePmtTrnRsV1AggregateHelper.type());
/* 55:   */     }
/* 56:66 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, TypePmtTrnRsV1Aggregate[] paramArrayOfTypePmtTrnRsV1Aggregate)
/* 60:   */   {
/* 61:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:74 */     write(localOutputStream, paramArrayOfTypePmtTrnRsV1Aggregate);
/* 63:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static TypePmtTrnRsV1Aggregate[] extract(Any paramAny)
/* 67:   */   {
/* 68:81 */     if (!paramAny.type().equal(type())) {
/* 69:83 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:85 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtTrnRsV1AggregateSeq:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1AggregateSeqHelper
 * JD-Core Version:    0.7.0.1
 */