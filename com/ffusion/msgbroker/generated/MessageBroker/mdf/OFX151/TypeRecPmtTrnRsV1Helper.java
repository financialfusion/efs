/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.StructMember;
/*  7:   */ import org.omg.CORBA.TypeCode;
/*  8:   */ import org.omg.CORBA.portable.InputStream;
/*  9:   */ import org.omg.CORBA.portable.OutputStream;
/* 10:   */ 
/* 11:   */ public abstract class TypeRecPmtTrnRsV1Helper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypeRecPmtTrnRsV1 clone(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1)
/* 16:   */   {
/* 17:16 */     if (paramTypeRecPmtTrnRsV1 == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = new TypeRecPmtTrnRsV1();
/* 21:21 */     localTypeRecPmtTrnRsV1.RecPmtTrnRs = TypeRecPmtTrnRsV1AggregateHelper.clone(paramTypeRecPmtTrnRsV1.RecPmtTrnRs);
/* 22:22 */     return localTypeRecPmtTrnRsV1;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static TypeRecPmtTrnRsV1 read(InputStream paramInputStream)
/* 26:   */   {
/* 27:28 */     TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = new TypeRecPmtTrnRsV1();
/* 28:29 */     localTypeRecPmtTrnRsV1.RecPmtTrnRs = TypeRecPmtTrnRsV1AggregateHelper.read(paramInputStream);
/* 29:30 */     return localTypeRecPmtTrnRsV1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void write(OutputStream paramOutputStream, TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1)
/* 33:   */   {
/* 34:37 */     if (paramTypeRecPmtTrnRsV1 == null) {
/* 35:39 */       paramTypeRecPmtTrnRsV1 = new TypeRecPmtTrnRsV1();
/* 36:   */     }
/* 37:41 */     TypeRecPmtTrnRsV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRsV1.RecPmtTrnRs);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static String _idl()
/* 41:   */   {
/* 42:46 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecPmtTrnRsV1";
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static TypeCode type()
/* 46:   */   {
/* 47:53 */     if (_type == null)
/* 48:   */     {
/* 49:55 */       ORB localORB = ORB.init();
/* 50:56 */       StructMember[] arrayOfStructMember = 
/* 51:57 */         {
/* 52:58 */         new StructMember("RecPmtTrnRs", TypeRecPmtTrnRsV1AggregateHelper.type(), null) };
/* 53:   */       
/* 54:60 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtTrnRsV1", arrayOfStructMember);
/* 55:   */     }
/* 56:62 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1)
/* 60:   */   {
/* 61:69 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:70 */     write(localOutputStream, paramTypeRecPmtTrnRsV1);
/* 63:71 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static TypeRecPmtTrnRsV1 extract(Any paramAny)
/* 67:   */   {
/* 68:77 */     if (!paramAny.type().equal(type())) {
/* 69:79 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:81 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:86 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtTrnRsV1:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Helper
 * JD-Core Version:    0.7.0.1
 */