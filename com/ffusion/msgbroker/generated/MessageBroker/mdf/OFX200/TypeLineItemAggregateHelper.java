/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.StructMember;
/*  7:   */ import org.omg.CORBA.TCKind;
/*  8:   */ import org.omg.CORBA.TypeCode;
/*  9:   */ import org.omg.CORBA.portable.InputStream;
/* 10:   */ import org.omg.CORBA.portable.OutputStream;
/* 11:   */ 
/* 12:   */ public abstract class TypeLineItemAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeLineItemAggregate clone(TypeLineItemAggregate paramTypeLineItemAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeLineItemAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeLineItemAggregate localTypeLineItemAggregate = new TypeLineItemAggregate();
/* 22:21 */     localTypeLineItemAggregate.LItmAmt = paramTypeLineItemAggregate.LItmAmt;
/* 23:22 */     localTypeLineItemAggregate.LItmDesc = paramTypeLineItemAggregate.LItmDesc;
/* 24:23 */     return localTypeLineItemAggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypeLineItemAggregate read(InputStream paramInputStream)
/* 28:   */   {
/* 29:29 */     TypeLineItemAggregate localTypeLineItemAggregate = new TypeLineItemAggregate();
/* 30:30 */     localTypeLineItemAggregate.LItmAmt = paramInputStream.read_float();
/* 31:31 */     localTypeLineItemAggregate.LItmDesc = paramInputStream.read_string();
/* 32:32 */     return localTypeLineItemAggregate;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static void write(OutputStream paramOutputStream, TypeLineItemAggregate paramTypeLineItemAggregate)
/* 36:   */   {
/* 37:39 */     if (paramTypeLineItemAggregate == null) {
/* 38:41 */       paramTypeLineItemAggregate = new TypeLineItemAggregate();
/* 39:   */     }
/* 40:43 */     paramOutputStream.write_float(paramTypeLineItemAggregate.LItmAmt);
/* 41:44 */     paramOutputStream.write_string(paramTypeLineItemAggregate.LItmDesc);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static String _idl()
/* 45:   */   {
/* 46:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeLineItemAggregate";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:56 */     if (_type == null)
/* 52:   */     {
/* 53:58 */       ORB localORB = ORB.init();
/* 54:59 */       StructMember[] arrayOfStructMember = 
/* 55:60 */         {
/* 56:61 */         new StructMember("LItmAmt", localORB.get_primitive_tc(TCKind.tk_float), null), 
/* 57:62 */         new StructMember("LItmDesc", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 58:   */       
/* 59:64 */       _type = localORB.create_struct_tc(id(), "TypeLineItemAggregate", arrayOfStructMember);
/* 60:   */     }
/* 61:66 */     return _type;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static void insert(Any paramAny, TypeLineItemAggregate paramTypeLineItemAggregate)
/* 65:   */   {
/* 66:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 67:74 */     write(localOutputStream, paramTypeLineItemAggregate);
/* 68:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static TypeLineItemAggregate extract(Any paramAny)
/* 72:   */   {
/* 73:81 */     if (!paramAny.type().equal(type())) {
/* 74:83 */       throw new BAD_OPERATION();
/* 75:   */     }
/* 76:85 */     return read(paramAny.create_input_stream());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static String id()
/* 80:   */   {
/* 81:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeLineItemAggregate:1.0";
/* 82:   */   }
/* 83:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeLineItemAggregateHelper
 * JD-Core Version:    0.7.0.1
 */