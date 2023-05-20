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
/* 12:   */ public abstract class TypeRecPmtTrnRsV1AggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeRecPmtTrnRsV1Aggregate clone(TypeRecPmtTrnRsV1Aggregate paramTypeRecPmtTrnRsV1Aggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeRecPmtTrnRsV1Aggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
/* 22:21 */     localTypeRecPmtTrnRsV1Aggregate.TrnRsCm = TypeTrnRsCmHelper.clone(paramTypeRecPmtTrnRsV1Aggregate.TrnRsCm);
/* 23:22 */     localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn = TypeRecPmtTrnRsUnHelper.clone(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn);
/* 24:23 */     localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists;
/* 25:24 */     return localTypeRecPmtTrnRsV1Aggregate;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeRecPmtTrnRsV1Aggregate read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
/* 31:31 */     localTypeRecPmtTrnRsV1Aggregate.TrnRsCm = TypeTrnRsCmHelper.read(paramInputStream);
/* 32:32 */     localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn = TypeRecPmtTrnRsUnHelper.read(paramInputStream);
/* 33:33 */     localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists = paramInputStream.read_boolean();
/* 34:34 */     return localTypeRecPmtTrnRsV1Aggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeRecPmtTrnRsV1Aggregate paramTypeRecPmtTrnRsV1Aggregate)
/* 38:   */   {
/* 39:41 */     if (paramTypeRecPmtTrnRsV1Aggregate == null) {
/* 40:43 */       paramTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
/* 41:   */     }
/* 42:45 */     TypeTrnRsCmHelper.write(paramOutputStream, paramTypeRecPmtTrnRsV1Aggregate.TrnRsCm);
/* 43:46 */     TypeRecPmtTrnRsUnHelper.write(paramOutputStream, paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn);
/* 44:47 */     paramOutputStream.write_boolean(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecPmtTrnRsV1Aggregate";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("TrnRsCm", TypeTrnRsCmHelper.type(), null), 
/* 60:65 */         new StructMember("RecPmtTrnRsUn", TypeRecPmtTrnRsUnHelper.type(), null), 
/* 61:66 */         new StructMember("RecPmtTrnRsUnExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtTrnRsV1Aggregate", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeRecPmtTrnRsV1Aggregate paramTypeRecPmtTrnRsV1Aggregate)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeRecPmtTrnRsV1Aggregate);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeRecPmtTrnRsV1Aggregate extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecPmtTrnRsV1Aggregate:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */