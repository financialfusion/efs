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
/* 11:   */ public abstract class TypeRecPmtSyncRsV1AggregateHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypeRecPmtSyncRsV1Aggregate clone(TypeRecPmtSyncRsV1Aggregate paramTypeRecPmtSyncRsV1Aggregate)
/* 16:   */   {
/* 17:16 */     if (paramTypeRecPmtSyncRsV1Aggregate == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypeRecPmtSyncRsV1Aggregate localTypeRecPmtSyncRsV1Aggregate = new TypeRecPmtSyncRsV1Aggregate();
/* 21:21 */     localTypeRecPmtSyncRsV1Aggregate.SyncRsCm = TypeSyncRsCmHelper.clone(paramTypeRecPmtSyncRsV1Aggregate.SyncRsCm);
/* 22:22 */     localTypeRecPmtSyncRsV1Aggregate.BankAcctFrom = TypeBankAcctFromAggregateHelper.clone(paramTypeRecPmtSyncRsV1Aggregate.BankAcctFrom);
/* 23:23 */     localTypeRecPmtSyncRsV1Aggregate.RecPmtTrnRs = TypeRecPmtTrnRsV1AggregateSeqHelper.clone(paramTypeRecPmtSyncRsV1Aggregate.RecPmtTrnRs);
/* 24:24 */     return localTypeRecPmtSyncRsV1Aggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypeRecPmtSyncRsV1Aggregate read(InputStream paramInputStream)
/* 28:   */   {
/* 29:30 */     TypeRecPmtSyncRsV1Aggregate localTypeRecPmtSyncRsV1Aggregate = new TypeRecPmtSyncRsV1Aggregate();
/* 30:31 */     localTypeRecPmtSyncRsV1Aggregate.SyncRsCm = TypeSyncRsCmHelper.read(paramInputStream);
/* 31:32 */     localTypeRecPmtSyncRsV1Aggregate.BankAcctFrom = TypeBankAcctFromAggregateHelper.read(paramInputStream);
/* 32:33 */     localTypeRecPmtSyncRsV1Aggregate.RecPmtTrnRs = TypeRecPmtTrnRsV1AggregateSeqHelper.read(paramInputStream);
/* 33:34 */     return localTypeRecPmtSyncRsV1Aggregate;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void write(OutputStream paramOutputStream, TypeRecPmtSyncRsV1Aggregate paramTypeRecPmtSyncRsV1Aggregate)
/* 37:   */   {
/* 38:41 */     if (paramTypeRecPmtSyncRsV1Aggregate == null) {
/* 39:43 */       paramTypeRecPmtSyncRsV1Aggregate = new TypeRecPmtSyncRsV1Aggregate();
/* 40:   */     }
/* 41:45 */     TypeSyncRsCmHelper.write(paramOutputStream, paramTypeRecPmtSyncRsV1Aggregate.SyncRsCm);
/* 42:46 */     TypeBankAcctFromAggregateHelper.write(paramOutputStream, paramTypeRecPmtSyncRsV1Aggregate.BankAcctFrom);
/* 43:47 */     TypeRecPmtTrnRsV1AggregateSeqHelper.write(paramOutputStream, paramTypeRecPmtSyncRsV1Aggregate.RecPmtTrnRs);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static String _idl()
/* 47:   */   {
/* 48:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecPmtSyncRsV1Aggregate";
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static TypeCode type()
/* 52:   */   {
/* 53:59 */     if (_type == null)
/* 54:   */     {
/* 55:61 */       ORB localORB = ORB.init();
/* 56:62 */       StructMember[] arrayOfStructMember = 
/* 57:63 */         {
/* 58:64 */         new StructMember("SyncRsCm", TypeSyncRsCmHelper.type(), null), 
/* 59:65 */         new StructMember("BankAcctFrom", TypeBankAcctFromAggregateHelper.type(), null), 
/* 60:66 */         new StructMember("RecPmtTrnRs", TypeRecPmtTrnRsV1AggregateSeqHelper.type(), null) };
/* 61:   */       
/* 62:68 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtSyncRsV1Aggregate", arrayOfStructMember);
/* 63:   */     }
/* 64:70 */     return _type;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static void insert(Any paramAny, TypeRecPmtSyncRsV1Aggregate paramTypeRecPmtSyncRsV1Aggregate)
/* 68:   */   {
/* 69:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 70:78 */     write(localOutputStream, paramTypeRecPmtSyncRsV1Aggregate);
/* 71:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static TypeRecPmtSyncRsV1Aggregate extract(Any paramAny)
/* 75:   */   {
/* 76:85 */     if (!paramAny.type().equal(type())) {
/* 77:87 */       throw new BAD_OPERATION();
/* 78:   */     }
/* 79:89 */     return read(paramAny.create_input_stream());
/* 80:   */   }
/* 81:   */   
/* 82:   */   public static String id()
/* 83:   */   {
/* 84:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecPmtSyncRsV1Aggregate:1.0";
/* 85:   */   }
/* 86:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */