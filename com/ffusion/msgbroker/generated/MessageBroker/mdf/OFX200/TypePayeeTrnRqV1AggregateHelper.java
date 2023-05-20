/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.StructMember;
/*  7:   */ import org.omg.CORBA.TypeCode;
/*  8:   */ import org.omg.CORBA.portable.InputStream;
/*  9:   */ import org.omg.CORBA.portable.OutputStream;
/* 10:   */ 
/* 11:   */ public abstract class TypePayeeTrnRqV1AggregateHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypePayeeTrnRqV1Aggregate clone(TypePayeeTrnRqV1Aggregate paramTypePayeeTrnRqV1Aggregate)
/* 16:   */   {
/* 17:16 */     if (paramTypePayeeTrnRqV1Aggregate == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypePayeeTrnRqV1Aggregate localTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
/* 21:21 */     localTypePayeeTrnRqV1Aggregate.TrnRqCm = TypeTrnRqCmHelper.clone(paramTypePayeeTrnRqV1Aggregate.TrnRqCm);
/* 22:22 */     localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn = TypePayeeTrnRqUnHelper.clone(paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn);
/* 23:23 */     return localTypePayeeTrnRqV1Aggregate;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static TypePayeeTrnRqV1Aggregate read(InputStream paramInputStream)
/* 27:   */   {
/* 28:29 */     TypePayeeTrnRqV1Aggregate localTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
/* 29:30 */     localTypePayeeTrnRqV1Aggregate.TrnRqCm = TypeTrnRqCmHelper.read(paramInputStream);
/* 30:31 */     localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn = TypePayeeTrnRqUnHelper.read(paramInputStream);
/* 31:32 */     return localTypePayeeTrnRqV1Aggregate;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static void write(OutputStream paramOutputStream, TypePayeeTrnRqV1Aggregate paramTypePayeeTrnRqV1Aggregate)
/* 35:   */   {
/* 36:39 */     if (paramTypePayeeTrnRqV1Aggregate == null) {
/* 37:41 */       paramTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
/* 38:   */     }
/* 39:43 */     TypeTrnRqCmHelper.write(paramOutputStream, paramTypePayeeTrnRqV1Aggregate.TrnRqCm);
/* 40:44 */     TypePayeeTrnRqUnHelper.write(paramOutputStream, paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static String _idl()
/* 44:   */   {
/* 45:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeTrnRqV1Aggregate";
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static TypeCode type()
/* 49:   */   {
/* 50:56 */     if (_type == null)
/* 51:   */     {
/* 52:58 */       ORB localORB = ORB.init();
/* 53:59 */       StructMember[] arrayOfStructMember = 
/* 54:60 */         {
/* 55:61 */         new StructMember("TrnRqCm", TypeTrnRqCmHelper.type(), null), 
/* 56:62 */         new StructMember("PayeeTrnRqUn", TypePayeeTrnRqUnHelper.type(), null) };
/* 57:   */       
/* 58:64 */       _type = localORB.create_struct_tc(id(), "TypePayeeTrnRqV1Aggregate", arrayOfStructMember);
/* 59:   */     }
/* 60:66 */     return _type;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public static void insert(Any paramAny, TypePayeeTrnRqV1Aggregate paramTypePayeeTrnRqV1Aggregate)
/* 64:   */   {
/* 65:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 66:74 */     write(localOutputStream, paramTypePayeeTrnRqV1Aggregate);
/* 67:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 68:   */   }
/* 69:   */   
/* 70:   */   public static TypePayeeTrnRqV1Aggregate extract(Any paramAny)
/* 71:   */   {
/* 72:81 */     if (!paramAny.type().equal(type())) {
/* 73:83 */       throw new BAD_OPERATION();
/* 74:   */     }
/* 75:85 */     return read(paramAny.create_input_stream());
/* 76:   */   }
/* 77:   */   
/* 78:   */   public static String id()
/* 79:   */   {
/* 80:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeTrnRqV1Aggregate:1.0";
/* 81:   */   }
/* 82:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */