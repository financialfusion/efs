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
/* 11:   */ public abstract class TypeRecIntraRqAggregateHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypeRecIntraRqAggregate clone(TypeRecIntraRqAggregate paramTypeRecIntraRqAggregate)
/* 16:   */   {
/* 17:16 */     if (paramTypeRecIntraRqAggregate == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypeRecIntraRqAggregate localTypeRecIntraRqAggregate = new TypeRecIntraRqAggregate();
/* 21:21 */     localTypeRecIntraRqAggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecIntraRqAggregate.RecurrInst);
/* 22:22 */     localTypeRecIntraRqAggregate.IntraRq = TypeIntraRqAggregateHelper.clone(paramTypeRecIntraRqAggregate.IntraRq);
/* 23:23 */     return localTypeRecIntraRqAggregate;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static TypeRecIntraRqAggregate read(InputStream paramInputStream)
/* 27:   */   {
/* 28:29 */     TypeRecIntraRqAggregate localTypeRecIntraRqAggregate = new TypeRecIntraRqAggregate();
/* 29:30 */     localTypeRecIntraRqAggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/* 30:31 */     localTypeRecIntraRqAggregate.IntraRq = TypeIntraRqAggregateHelper.read(paramInputStream);
/* 31:32 */     return localTypeRecIntraRqAggregate;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraRqAggregate paramTypeRecIntraRqAggregate)
/* 35:   */   {
/* 36:39 */     if (paramTypeRecIntraRqAggregate == null) {
/* 37:41 */       paramTypeRecIntraRqAggregate = new TypeRecIntraRqAggregate();
/* 38:   */     }
/* 39:43 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecIntraRqAggregate.RecurrInst);
/* 40:44 */     TypeIntraRqAggregateHelper.write(paramOutputStream, paramTypeRecIntraRqAggregate.IntraRq);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static String _idl()
/* 44:   */   {
/* 45:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecIntraRqAggregate";
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static TypeCode type()
/* 49:   */   {
/* 50:56 */     if (_type == null)
/* 51:   */     {
/* 52:58 */       ORB localORB = ORB.init();
/* 53:59 */       StructMember[] arrayOfStructMember = 
/* 54:60 */         {
/* 55:61 */         new StructMember("RecurrInst", TypeRecurrInstAggregateHelper.type(), null), 
/* 56:62 */         new StructMember("IntraRq", TypeIntraRqAggregateHelper.type(), null) };
/* 57:   */       
/* 58:64 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraRqAggregate", arrayOfStructMember);
/* 59:   */     }
/* 60:66 */     return _type;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public static void insert(Any paramAny, TypeRecIntraRqAggregate paramTypeRecIntraRqAggregate)
/* 64:   */   {
/* 65:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 66:74 */     write(localOutputStream, paramTypeRecIntraRqAggregate);
/* 67:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 68:   */   }
/* 69:   */   
/* 70:   */   public static TypeRecIntraRqAggregate extract(Any paramAny)
/* 71:   */   {
/* 72:81 */     if (!paramAny.type().equal(type())) {
/* 73:83 */       throw new BAD_OPERATION();
/* 74:   */     }
/* 75:85 */     return read(paramAny.create_input_stream());
/* 76:   */   }
/* 77:   */   
/* 78:   */   public static String id()
/* 79:   */   {
/* 80:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecIntraRqAggregate:1.0";
/* 81:   */   }
/* 82:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRqAggregateHelper
 * JD-Core Version:    0.7.0.1
 */