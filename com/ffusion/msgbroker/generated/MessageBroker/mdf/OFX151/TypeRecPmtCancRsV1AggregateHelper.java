/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/* 12:   */ public abstract class TypeRecPmtCancRsV1AggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeRecPmtCancRsV1Aggregate clone(TypeRecPmtCancRsV1Aggregate paramTypeRecPmtCancRsV1Aggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeRecPmtCancRsV1Aggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeRecPmtCancRsV1Aggregate localTypeRecPmtCancRsV1Aggregate = new TypeRecPmtCancRsV1Aggregate();
/* 22:21 */     localTypeRecPmtCancRsV1Aggregate.RecSrvrTID = paramTypeRecPmtCancRsV1Aggregate.RecSrvrTID;
/* 23:22 */     localTypeRecPmtCancRsV1Aggregate.CanPending = paramTypeRecPmtCancRsV1Aggregate.CanPending;
/* 24:23 */     return localTypeRecPmtCancRsV1Aggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypeRecPmtCancRsV1Aggregate read(InputStream paramInputStream)
/* 28:   */   {
/* 29:29 */     TypeRecPmtCancRsV1Aggregate localTypeRecPmtCancRsV1Aggregate = new TypeRecPmtCancRsV1Aggregate();
/* 30:30 */     localTypeRecPmtCancRsV1Aggregate.RecSrvrTID = paramInputStream.read_string();
/* 31:31 */     localTypeRecPmtCancRsV1Aggregate.CanPending = paramInputStream.read_boolean();
/* 32:32 */     return localTypeRecPmtCancRsV1Aggregate;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static void write(OutputStream paramOutputStream, TypeRecPmtCancRsV1Aggregate paramTypeRecPmtCancRsV1Aggregate)
/* 36:   */   {
/* 37:39 */     if (paramTypeRecPmtCancRsV1Aggregate == null) {
/* 38:41 */       paramTypeRecPmtCancRsV1Aggregate = new TypeRecPmtCancRsV1Aggregate();
/* 39:   */     }
/* 40:43 */     paramOutputStream.write_string(paramTypeRecPmtCancRsV1Aggregate.RecSrvrTID);
/* 41:44 */     paramOutputStream.write_boolean(paramTypeRecPmtCancRsV1Aggregate.CanPending);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static String _idl()
/* 45:   */   {
/* 46:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecPmtCancRsV1Aggregate";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:56 */     if (_type == null)
/* 52:   */     {
/* 53:58 */       ORB localORB = ORB.init();
/* 54:59 */       StructMember[] arrayOfStructMember = 
/* 55:60 */         {
/* 56:61 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 57:62 */         new StructMember("CanPending", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 58:   */       
/* 59:64 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtCancRsV1Aggregate", arrayOfStructMember);
/* 60:   */     }
/* 61:66 */     return _type;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static void insert(Any paramAny, TypeRecPmtCancRsV1Aggregate paramTypeRecPmtCancRsV1Aggregate)
/* 65:   */   {
/* 66:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 67:74 */     write(localOutputStream, paramTypeRecPmtCancRsV1Aggregate);
/* 68:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static TypeRecPmtCancRsV1Aggregate extract(Any paramAny)
/* 72:   */   {
/* 73:81 */     if (!paramAny.type().equal(type())) {
/* 74:83 */       throw new BAD_OPERATION();
/* 75:   */     }
/* 76:85 */     return read(paramAny.create_input_stream());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static String id()
/* 80:   */   {
/* 81:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtCancRsV1Aggregate:1.0";
/* 82:   */   }
/* 83:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtCancRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */