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
/* 11:   */ public abstract class TypeRecPmtTrnRqV1Helper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypeRecPmtTrnRqV1 clone(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1)
/* 16:   */   {
/* 17:16 */     if (paramTypeRecPmtTrnRqV1 == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1 = new TypeRecPmtTrnRqV1();
/* 21:21 */     localTypeRecPmtTrnRqV1.RecPmtTrnRq = TypeRecPmtTrnRqV1AggregateHelper.clone(paramTypeRecPmtTrnRqV1.RecPmtTrnRq);
/* 22:22 */     return localTypeRecPmtTrnRqV1;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static TypeRecPmtTrnRqV1 read(InputStream paramInputStream)
/* 26:   */   {
/* 27:28 */     TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1 = new TypeRecPmtTrnRqV1();
/* 28:29 */     localTypeRecPmtTrnRqV1.RecPmtTrnRq = TypeRecPmtTrnRqV1AggregateHelper.read(paramInputStream);
/* 29:30 */     return localTypeRecPmtTrnRqV1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void write(OutputStream paramOutputStream, TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1)
/* 33:   */   {
/* 34:37 */     if (paramTypeRecPmtTrnRqV1 == null) {
/* 35:39 */       paramTypeRecPmtTrnRqV1 = new TypeRecPmtTrnRqV1();
/* 36:   */     }
/* 37:41 */     TypeRecPmtTrnRqV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRqV1.RecPmtTrnRq);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static String _idl()
/* 41:   */   {
/* 42:46 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecPmtTrnRqV1";
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static TypeCode type()
/* 46:   */   {
/* 47:53 */     if (_type == null)
/* 48:   */     {
/* 49:55 */       ORB localORB = ORB.init();
/* 50:56 */       StructMember[] arrayOfStructMember = 
/* 51:57 */         {
/* 52:58 */         new StructMember("RecPmtTrnRq", TypeRecPmtTrnRqV1AggregateHelper.type(), null) };
/* 53:   */       
/* 54:60 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtTrnRqV1", arrayOfStructMember);
/* 55:   */     }
/* 56:62 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1)
/* 60:   */   {
/* 61:69 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:70 */     write(localOutputStream, paramTypeRecPmtTrnRqV1);
/* 63:71 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static TypeRecPmtTrnRqV1 extract(Any paramAny)
/* 67:   */   {
/* 68:77 */     if (!paramAny.type().equal(type())) {
/* 69:79 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:81 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:86 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecPmtTrnRqV1:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1Helper
 * JD-Core Version:    0.7.0.1
 */