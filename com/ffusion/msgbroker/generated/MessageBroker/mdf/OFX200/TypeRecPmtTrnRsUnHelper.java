/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*   2:    */ 
/*   3:    */ import org.omg.CORBA.Any;
/*   4:    */ import org.omg.CORBA.BAD_OPERATION;
/*   5:    */ import org.omg.CORBA.ORB;
/*   6:    */ import org.omg.CORBA.StructMember;
/*   7:    */ import org.omg.CORBA.TCKind;
/*   8:    */ import org.omg.CORBA.TypeCode;
/*   9:    */ import org.omg.CORBA.portable.InputStream;
/*  10:    */ import org.omg.CORBA.portable.OutputStream;
/*  11:    */ 
/*  12:    */ public abstract class TypeRecPmtTrnRsUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeRecPmtTrnRsUn clone(TypeRecPmtTrnRsUn paramTypeRecPmtTrnRsUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypeRecPmtTrnRsUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeRecPmtTrnRsUn localTypeRecPmtTrnRsUn = new TypeRecPmtTrnRsUn();
/*  22: 21 */     localTypeRecPmtTrnRsUn.__preValueTag = paramTypeRecPmtTrnRsUn.__preValueTag;
/*  23: 22 */     localTypeRecPmtTrnRsUn.__memberName = paramTypeRecPmtTrnRsUn.__memberName;
/*  24: 23 */     localTypeRecPmtTrnRsUn.RecPmtRs = TypeRecPmtRsAggregateHelper.clone(paramTypeRecPmtTrnRsUn.RecPmtRs);
/*  25: 24 */     localTypeRecPmtTrnRsUn.RecPmtModRs = TypeRecPmtModRsAggregateHelper.clone(paramTypeRecPmtTrnRsUn.RecPmtModRs);
/*  26: 25 */     localTypeRecPmtTrnRsUn.RecPmtCancRs = TypeRecPmtCancRsAggregateHelper.clone(paramTypeRecPmtTrnRsUn.RecPmtCancRs);
/*  27: 26 */     return localTypeRecPmtTrnRsUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeRecPmtTrnRsUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeRecPmtTrnRsUn localTypeRecPmtTrnRsUn = new TypeRecPmtTrnRsUn();
/*  33: 33 */     localTypeRecPmtTrnRsUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeRecPmtTrnRsUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeRecPmtTrnRsUn.RecPmtRs = TypeRecPmtRsAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypeRecPmtTrnRsUn.RecPmtModRs = TypeRecPmtModRsAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypeRecPmtTrnRsUn.RecPmtCancRs = TypeRecPmtCancRsAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypeRecPmtTrnRsUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtTrnRsUn paramTypeRecPmtTrnRsUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypeRecPmtTrnRsUn == null) {
/*  44: 47 */       paramTypeRecPmtTrnRsUn = new TypeRecPmtTrnRsUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeRecPmtTrnRsUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeRecPmtTrnRsUn.__memberName);
/*  48: 51 */     TypeRecPmtRsAggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRsUn.RecPmtRs);
/*  49: 52 */     TypeRecPmtModRsAggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRsUn.RecPmtModRs);
/*  50: 53 */     TypeRecPmtCancRsAggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRsUn.RecPmtCancRs);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecPmtTrnRsUn";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static TypeCode type()
/*  59:    */   {
/*  60: 65 */     if (_type == null)
/*  61:    */     {
/*  62: 67 */       ORB localORB = ORB.init();
/*  63: 68 */       StructMember[] arrayOfStructMember = 
/*  64: 69 */         {
/*  65: 70 */         new StructMember("__preValueTag", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  66: 71 */         new StructMember("__memberName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  67: 72 */         new StructMember("RecPmtRs", TypeRecPmtRsAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("RecPmtModRs", TypeRecPmtModRsAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("RecPmtCancRs", TypeRecPmtCancRsAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtTrnRsUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeRecPmtTrnRsUn paramTypeRecPmtTrnRsUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeRecPmtTrnRsUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeRecPmtTrnRsUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecPmtTrnRsUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUnHelper
 * JD-Core Version:    0.7.0.1
 */