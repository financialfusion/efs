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
/* 11:   */ public abstract class TypePmtSyncRsV1AggregateHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypePmtSyncRsV1Aggregate clone(TypePmtSyncRsV1Aggregate paramTypePmtSyncRsV1Aggregate)
/* 16:   */   {
/* 17:16 */     if (paramTypePmtSyncRsV1Aggregate == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypePmtSyncRsV1Aggregate localTypePmtSyncRsV1Aggregate = new TypePmtSyncRsV1Aggregate();
/* 21:21 */     localTypePmtSyncRsV1Aggregate.SyncRsCm = TypeSyncRsCmHelper.clone(paramTypePmtSyncRsV1Aggregate.SyncRsCm);
/* 22:22 */     localTypePmtSyncRsV1Aggregate.BankAcctFrom = TypeBankAcctFromAggregateHelper.clone(paramTypePmtSyncRsV1Aggregate.BankAcctFrom);
/* 23:23 */     localTypePmtSyncRsV1Aggregate.PmtTrnRs = TypePmtTrnRsV1AggregateSeqHelper.clone(paramTypePmtSyncRsV1Aggregate.PmtTrnRs);
/* 24:24 */     return localTypePmtSyncRsV1Aggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypePmtSyncRsV1Aggregate read(InputStream paramInputStream)
/* 28:   */   {
/* 29:30 */     TypePmtSyncRsV1Aggregate localTypePmtSyncRsV1Aggregate = new TypePmtSyncRsV1Aggregate();
/* 30:31 */     localTypePmtSyncRsV1Aggregate.SyncRsCm = TypeSyncRsCmHelper.read(paramInputStream);
/* 31:32 */     localTypePmtSyncRsV1Aggregate.BankAcctFrom = TypeBankAcctFromAggregateHelper.read(paramInputStream);
/* 32:33 */     localTypePmtSyncRsV1Aggregate.PmtTrnRs = TypePmtTrnRsV1AggregateSeqHelper.read(paramInputStream);
/* 33:34 */     return localTypePmtSyncRsV1Aggregate;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void write(OutputStream paramOutputStream, TypePmtSyncRsV1Aggregate paramTypePmtSyncRsV1Aggregate)
/* 37:   */   {
/* 38:41 */     if (paramTypePmtSyncRsV1Aggregate == null) {
/* 39:43 */       paramTypePmtSyncRsV1Aggregate = new TypePmtSyncRsV1Aggregate();
/* 40:   */     }
/* 41:45 */     TypeSyncRsCmHelper.write(paramOutputStream, paramTypePmtSyncRsV1Aggregate.SyncRsCm);
/* 42:46 */     TypeBankAcctFromAggregateHelper.write(paramOutputStream, paramTypePmtSyncRsV1Aggregate.BankAcctFrom);
/* 43:47 */     TypePmtTrnRsV1AggregateSeqHelper.write(paramOutputStream, paramTypePmtSyncRsV1Aggregate.PmtTrnRs);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static String _idl()
/* 47:   */   {
/* 48:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePmtSyncRsV1Aggregate";
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
/* 60:66 */         new StructMember("PmtTrnRs", TypePmtTrnRsV1AggregateSeqHelper.type(), null) };
/* 61:   */       
/* 62:68 */       _type = localORB.create_struct_tc(id(), "TypePmtSyncRsV1Aggregate", arrayOfStructMember);
/* 63:   */     }
/* 64:70 */     return _type;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static void insert(Any paramAny, TypePmtSyncRsV1Aggregate paramTypePmtSyncRsV1Aggregate)
/* 68:   */   {
/* 69:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 70:78 */     write(localOutputStream, paramTypePmtSyncRsV1Aggregate);
/* 71:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static TypePmtSyncRsV1Aggregate extract(Any paramAny)
/* 75:   */   {
/* 76:85 */     if (!paramAny.type().equal(type())) {
/* 77:87 */       throw new BAD_OPERATION();
/* 78:   */     }
/* 79:89 */     return read(paramAny.create_input_stream());
/* 80:   */   }
/* 81:   */   
/* 82:   */   public static String id()
/* 83:   */   {
/* 84:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePmtSyncRsV1Aggregate:1.0";
/* 85:   */   }
/* 86:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */