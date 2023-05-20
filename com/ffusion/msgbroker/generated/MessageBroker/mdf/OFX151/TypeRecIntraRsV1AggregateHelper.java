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
/* 12:   */ public abstract class TypeRecIntraRsV1AggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeRecIntraRsV1Aggregate clone(TypeRecIntraRsV1Aggregate paramTypeRecIntraRsV1Aggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeRecIntraRsV1Aggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeRecIntraRsV1Aggregate localTypeRecIntraRsV1Aggregate = new TypeRecIntraRsV1Aggregate();
/* 22:21 */     localTypeRecIntraRsV1Aggregate.RecSrvrTID = paramTypeRecIntraRsV1Aggregate.RecSrvrTID;
/* 23:22 */     localTypeRecIntraRsV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecIntraRsV1Aggregate.RecurrInst);
/* 24:23 */     localTypeRecIntraRsV1Aggregate.IntraRs = TypeIntraRsV1AggregateHelper.clone(paramTypeRecIntraRsV1Aggregate.IntraRs);
/* 25:24 */     return localTypeRecIntraRsV1Aggregate;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeRecIntraRsV1Aggregate read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeRecIntraRsV1Aggregate localTypeRecIntraRsV1Aggregate = new TypeRecIntraRsV1Aggregate();
/* 31:31 */     localTypeRecIntraRsV1Aggregate.RecSrvrTID = paramInputStream.read_string();
/* 32:32 */     localTypeRecIntraRsV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/* 33:33 */     localTypeRecIntraRsV1Aggregate.IntraRs = TypeIntraRsV1AggregateHelper.read(paramInputStream);
/* 34:34 */     return localTypeRecIntraRsV1Aggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraRsV1Aggregate paramTypeRecIntraRsV1Aggregate)
/* 38:   */   {
/* 39:41 */     if (paramTypeRecIntraRsV1Aggregate == null) {
/* 40:43 */       paramTypeRecIntraRsV1Aggregate = new TypeRecIntraRsV1Aggregate();
/* 41:   */     }
/* 42:45 */     paramOutputStream.write_string(paramTypeRecIntraRsV1Aggregate.RecSrvrTID);
/* 43:46 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecIntraRsV1Aggregate.RecurrInst);
/* 44:47 */     TypeIntraRsV1AggregateHelper.write(paramOutputStream, paramTypeRecIntraRsV1Aggregate.IntraRs);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecIntraRsV1Aggregate";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 60:65 */         new StructMember("RecurrInst", TypeRecurrInstAggregateHelper.type(), null), 
/* 61:66 */         new StructMember("IntraRs", TypeIntraRsV1AggregateHelper.type(), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraRsV1Aggregate", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeRecIntraRsV1Aggregate paramTypeRecIntraRsV1Aggregate)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeRecIntraRsV1Aggregate);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeRecIntraRsV1Aggregate extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecIntraRsV1Aggregate:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */