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
/* 11:   */ public abstract class TypePayeeTrnRqV1Helper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypePayeeTrnRqV1 clone(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1)
/* 16:   */   {
/* 17:16 */     if (paramTypePayeeTrnRqV1 == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypePayeeTrnRqV1 localTypePayeeTrnRqV1 = new TypePayeeTrnRqV1();
/* 21:21 */     localTypePayeeTrnRqV1.PayeeTrnRq = TypePayeeTrnRqV1AggregateHelper.clone(paramTypePayeeTrnRqV1.PayeeTrnRq);
/* 22:22 */     return localTypePayeeTrnRqV1;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static TypePayeeTrnRqV1 read(InputStream paramInputStream)
/* 26:   */   {
/* 27:28 */     TypePayeeTrnRqV1 localTypePayeeTrnRqV1 = new TypePayeeTrnRqV1();
/* 28:29 */     localTypePayeeTrnRqV1.PayeeTrnRq = TypePayeeTrnRqV1AggregateHelper.read(paramInputStream);
/* 29:30 */     return localTypePayeeTrnRqV1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void write(OutputStream paramOutputStream, TypePayeeTrnRqV1 paramTypePayeeTrnRqV1)
/* 33:   */   {
/* 34:37 */     if (paramTypePayeeTrnRqV1 == null) {
/* 35:39 */       paramTypePayeeTrnRqV1 = new TypePayeeTrnRqV1();
/* 36:   */     }
/* 37:41 */     TypePayeeTrnRqV1AggregateHelper.write(paramOutputStream, paramTypePayeeTrnRqV1.PayeeTrnRq);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static String _idl()
/* 41:   */   {
/* 42:46 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeTrnRqV1";
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static TypeCode type()
/* 46:   */   {
/* 47:53 */     if (_type == null)
/* 48:   */     {
/* 49:55 */       ORB localORB = ORB.init();
/* 50:56 */       StructMember[] arrayOfStructMember = 
/* 51:57 */         {
/* 52:58 */         new StructMember("PayeeTrnRq", TypePayeeTrnRqV1AggregateHelper.type(), null) };
/* 53:   */       
/* 54:60 */       _type = localORB.create_struct_tc(id(), "TypePayeeTrnRqV1", arrayOfStructMember);
/* 55:   */     }
/* 56:62 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, TypePayeeTrnRqV1 paramTypePayeeTrnRqV1)
/* 60:   */   {
/* 61:69 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:70 */     write(localOutputStream, paramTypePayeeTrnRqV1);
/* 63:71 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static TypePayeeTrnRqV1 extract(Any paramAny)
/* 67:   */   {
/* 68:77 */     if (!paramAny.type().equal(type())) {
/* 69:79 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:81 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:86 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeTrnRqV1:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1Helper
 * JD-Core Version:    0.7.0.1
 */