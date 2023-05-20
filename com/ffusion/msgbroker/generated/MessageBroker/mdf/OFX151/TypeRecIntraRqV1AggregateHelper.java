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
/* 11:   */ public abstract class TypeRecIntraRqV1AggregateHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypeRecIntraRqV1Aggregate clone(TypeRecIntraRqV1Aggregate paramTypeRecIntraRqV1Aggregate)
/* 16:   */   {
/* 17:16 */     if (paramTypeRecIntraRqV1Aggregate == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypeRecIntraRqV1Aggregate localTypeRecIntraRqV1Aggregate = new TypeRecIntraRqV1Aggregate();
/* 21:21 */     localTypeRecIntraRqV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecIntraRqV1Aggregate.RecurrInst);
/* 22:22 */     localTypeRecIntraRqV1Aggregate.IntraRq = TypeIntraRqV1AggregateHelper.clone(paramTypeRecIntraRqV1Aggregate.IntraRq);
/* 23:23 */     return localTypeRecIntraRqV1Aggregate;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static TypeRecIntraRqV1Aggregate read(InputStream paramInputStream)
/* 27:   */   {
/* 28:29 */     TypeRecIntraRqV1Aggregate localTypeRecIntraRqV1Aggregate = new TypeRecIntraRqV1Aggregate();
/* 29:30 */     localTypeRecIntraRqV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/* 30:31 */     localTypeRecIntraRqV1Aggregate.IntraRq = TypeIntraRqV1AggregateHelper.read(paramInputStream);
/* 31:32 */     return localTypeRecIntraRqV1Aggregate;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraRqV1Aggregate paramTypeRecIntraRqV1Aggregate)
/* 35:   */   {
/* 36:39 */     if (paramTypeRecIntraRqV1Aggregate == null) {
/* 37:41 */       paramTypeRecIntraRqV1Aggregate = new TypeRecIntraRqV1Aggregate();
/* 38:   */     }
/* 39:43 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecIntraRqV1Aggregate.RecurrInst);
/* 40:44 */     TypeIntraRqV1AggregateHelper.write(paramOutputStream, paramTypeRecIntraRqV1Aggregate.IntraRq);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static String _idl()
/* 44:   */   {
/* 45:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecIntraRqV1Aggregate";
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
/* 56:62 */         new StructMember("IntraRq", TypeIntraRqV1AggregateHelper.type(), null) };
/* 57:   */       
/* 58:64 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraRqV1Aggregate", arrayOfStructMember);
/* 59:   */     }
/* 60:66 */     return _type;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public static void insert(Any paramAny, TypeRecIntraRqV1Aggregate paramTypeRecIntraRqV1Aggregate)
/* 64:   */   {
/* 65:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 66:74 */     write(localOutputStream, paramTypeRecIntraRqV1Aggregate);
/* 67:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 68:   */   }
/* 69:   */   
/* 70:   */   public static TypeRecIntraRqV1Aggregate extract(Any paramAny)
/* 71:   */   {
/* 72:81 */     if (!paramAny.type().equal(type())) {
/* 73:83 */       throw new BAD_OPERATION();
/* 74:   */     }
/* 75:85 */     return read(paramAny.create_input_stream());
/* 76:   */   }
/* 77:   */   
/* 78:   */   public static String id()
/* 79:   */   {
/* 80:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecIntraRqV1Aggregate:1.0";
/* 81:   */   }
/* 82:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */