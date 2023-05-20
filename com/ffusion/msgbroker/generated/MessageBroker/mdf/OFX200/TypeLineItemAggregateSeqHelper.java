/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class TypeLineItemAggregateSeqHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static TypeLineItemAggregate[] clone(TypeLineItemAggregate[] paramArrayOfTypeLineItemAggregate)
/* 15:   */   {
/* 16:16 */     if (paramArrayOfTypeLineItemAggregate == null) {
/* 17:18 */       return null;
/* 18:   */     }
/* 19:20 */     int i = paramArrayOfTypeLineItemAggregate.length;
/* 20:21 */     TypeLineItemAggregate[] arrayOfTypeLineItemAggregate = new TypeLineItemAggregate[i];
/* 21:22 */     for (int j = 0; j < i; j++) {
/* 22:24 */       arrayOfTypeLineItemAggregate[j] = TypeLineItemAggregateHelper.clone(paramArrayOfTypeLineItemAggregate[j]);
/* 23:   */     }
/* 24:26 */     return arrayOfTypeLineItemAggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypeLineItemAggregate[] read(InputStream paramInputStream)
/* 28:   */   {
/* 29:32 */     int i = paramInputStream.read_ulong();
/* 30:33 */     TypeLineItemAggregate[] arrayOfTypeLineItemAggregate = new TypeLineItemAggregate[i];
/* 31:34 */     for (int j = 0; j < i; j++) {
/* 32:36 */       arrayOfTypeLineItemAggregate[j] = TypeLineItemAggregateHelper.read(paramInputStream);
/* 33:   */     }
/* 34:38 */     return arrayOfTypeLineItemAggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeLineItemAggregate[] paramArrayOfTypeLineItemAggregate)
/* 38:   */   {
/* 39:45 */     if (paramArrayOfTypeLineItemAggregate == null) {
/* 40:47 */       paramArrayOfTypeLineItemAggregate = new TypeLineItemAggregate[0];
/* 41:   */     }
/* 42:49 */     int i = paramArrayOfTypeLineItemAggregate.length;
/* 43:50 */     paramOutputStream.write_ulong(i);
/* 44:51 */     for (int j = 0; j < i; j++) {
/* 45:53 */       TypeLineItemAggregateHelper.write(paramOutputStream, paramArrayOfTypeLineItemAggregate[j]);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:61 */     if (_type == null)
/* 52:   */     {
/* 53:63 */       ORB localORB = ORB.init();
/* 54:64 */       _type = localORB.create_sequence_tc(0, TypeLineItemAggregateHelper.type());
/* 55:   */     }
/* 56:66 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, TypeLineItemAggregate[] paramArrayOfTypeLineItemAggregate)
/* 60:   */   {
/* 61:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:74 */     write(localOutputStream, paramArrayOfTypeLineItemAggregate);
/* 63:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static TypeLineItemAggregate[] extract(Any paramAny)
/* 67:   */   {
/* 68:81 */     if (!paramAny.type().equal(type())) {
/* 69:83 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:85 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeLineItemAggregateSeq:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeLineItemAggregateSeqHelper
 * JD-Core Version:    0.7.0.1
 */