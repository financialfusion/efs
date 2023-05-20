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
/* 11:   */ public abstract class TypeRecIntraSyncRsV1AggregateHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypeRecIntraSyncRsV1Aggregate clone(TypeRecIntraSyncRsV1Aggregate paramTypeRecIntraSyncRsV1Aggregate)
/* 16:   */   {
/* 17:16 */     if (paramTypeRecIntraSyncRsV1Aggregate == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypeRecIntraSyncRsV1Aggregate localTypeRecIntraSyncRsV1Aggregate = new TypeRecIntraSyncRsV1Aggregate();
/* 21:21 */     localTypeRecIntraSyncRsV1Aggregate.SyncRsV1Cm = TypeSyncRsV1CmHelper.clone(paramTypeRecIntraSyncRsV1Aggregate.SyncRsV1Cm);
/* 22:22 */     localTypeRecIntraSyncRsV1Aggregate.BankAcctFrom = TypeBankAcctFromV1AggregateHelper.clone(paramTypeRecIntraSyncRsV1Aggregate.BankAcctFrom);
/* 23:23 */     localTypeRecIntraSyncRsV1Aggregate.RecIntraTrnRs = TypeRecIntraTrnRsV1AggregateSeqHelper.clone(paramTypeRecIntraSyncRsV1Aggregate.RecIntraTrnRs);
/* 24:24 */     return localTypeRecIntraSyncRsV1Aggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypeRecIntraSyncRsV1Aggregate read(InputStream paramInputStream)
/* 28:   */   {
/* 29:30 */     TypeRecIntraSyncRsV1Aggregate localTypeRecIntraSyncRsV1Aggregate = new TypeRecIntraSyncRsV1Aggregate();
/* 30:31 */     localTypeRecIntraSyncRsV1Aggregate.SyncRsV1Cm = TypeSyncRsV1CmHelper.read(paramInputStream);
/* 31:32 */     localTypeRecIntraSyncRsV1Aggregate.BankAcctFrom = TypeBankAcctFromV1AggregateHelper.read(paramInputStream);
/* 32:33 */     localTypeRecIntraSyncRsV1Aggregate.RecIntraTrnRs = TypeRecIntraTrnRsV1AggregateSeqHelper.read(paramInputStream);
/* 33:34 */     return localTypeRecIntraSyncRsV1Aggregate;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraSyncRsV1Aggregate paramTypeRecIntraSyncRsV1Aggregate)
/* 37:   */   {
/* 38:41 */     if (paramTypeRecIntraSyncRsV1Aggregate == null) {
/* 39:43 */       paramTypeRecIntraSyncRsV1Aggregate = new TypeRecIntraSyncRsV1Aggregate();
/* 40:   */     }
/* 41:45 */     TypeSyncRsV1CmHelper.write(paramOutputStream, paramTypeRecIntraSyncRsV1Aggregate.SyncRsV1Cm);
/* 42:46 */     TypeBankAcctFromV1AggregateHelper.write(paramOutputStream, paramTypeRecIntraSyncRsV1Aggregate.BankAcctFrom);
/* 43:47 */     TypeRecIntraTrnRsV1AggregateSeqHelper.write(paramOutputStream, paramTypeRecIntraSyncRsV1Aggregate.RecIntraTrnRs);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static String _idl()
/* 47:   */   {
/* 48:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecIntraSyncRsV1Aggregate";
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static TypeCode type()
/* 52:   */   {
/* 53:59 */     if (_type == null)
/* 54:   */     {
/* 55:61 */       ORB localORB = ORB.init();
/* 56:62 */       StructMember[] arrayOfStructMember = 
/* 57:63 */         {
/* 58:64 */         new StructMember("SyncRsV1Cm", TypeSyncRsV1CmHelper.type(), null), 
/* 59:65 */         new StructMember("BankAcctFrom", TypeBankAcctFromV1AggregateHelper.type(), null), 
/* 60:66 */         new StructMember("RecIntraTrnRs", TypeRecIntraTrnRsV1AggregateSeqHelper.type(), null) };
/* 61:   */       
/* 62:68 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraSyncRsV1Aggregate", arrayOfStructMember);
/* 63:   */     }
/* 64:70 */     return _type;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static void insert(Any paramAny, TypeRecIntraSyncRsV1Aggregate paramTypeRecIntraSyncRsV1Aggregate)
/* 68:   */   {
/* 69:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 70:78 */     write(localOutputStream, paramTypeRecIntraSyncRsV1Aggregate);
/* 71:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static TypeRecIntraSyncRsV1Aggregate extract(Any paramAny)
/* 75:   */   {
/* 76:85 */     if (!paramAny.type().equal(type())) {
/* 77:87 */       throw new BAD_OPERATION();
/* 78:   */     }
/* 79:89 */     return read(paramAny.create_input_stream());
/* 80:   */   }
/* 81:   */   
/* 82:   */   public static String id()
/* 83:   */   {
/* 84:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecIntraSyncRsV1Aggregate:1.0";
/* 85:   */   }
/* 86:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */