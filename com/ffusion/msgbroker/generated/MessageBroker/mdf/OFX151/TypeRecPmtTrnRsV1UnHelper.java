/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/*  12:    */ public abstract class TypeRecPmtTrnRsV1UnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeRecPmtTrnRsV1Un clone(TypeRecPmtTrnRsV1Un paramTypeRecPmtTrnRsV1Un)
/*  17:    */   {
/*  18: 16 */     if (paramTypeRecPmtTrnRsV1Un == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeRecPmtTrnRsV1Un localTypeRecPmtTrnRsV1Un = new TypeRecPmtTrnRsV1Un();
/*  22: 21 */     localTypeRecPmtTrnRsV1Un.__preValueTag = paramTypeRecPmtTrnRsV1Un.__preValueTag;
/*  23: 22 */     localTypeRecPmtTrnRsV1Un.__memberName = paramTypeRecPmtTrnRsV1Un.__memberName;
/*  24: 23 */     localTypeRecPmtTrnRsV1Un.RecPmtRs = TypeRecPmtRsV1AggregateHelper.clone(paramTypeRecPmtTrnRsV1Un.RecPmtRs);
/*  25: 24 */     localTypeRecPmtTrnRsV1Un.RecPmtModRs = TypeRecPmtModRsV1AggregateHelper.clone(paramTypeRecPmtTrnRsV1Un.RecPmtModRs);
/*  26: 25 */     localTypeRecPmtTrnRsV1Un.RecPmtCancRs = TypeRecPmtCancRsV1AggregateHelper.clone(paramTypeRecPmtTrnRsV1Un.RecPmtCancRs);
/*  27: 26 */     return localTypeRecPmtTrnRsV1Un;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeRecPmtTrnRsV1Un read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeRecPmtTrnRsV1Un localTypeRecPmtTrnRsV1Un = new TypeRecPmtTrnRsV1Un();
/*  33: 33 */     localTypeRecPmtTrnRsV1Un.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeRecPmtTrnRsV1Un.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeRecPmtTrnRsV1Un.RecPmtRs = TypeRecPmtRsV1AggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypeRecPmtTrnRsV1Un.RecPmtModRs = TypeRecPmtModRsV1AggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypeRecPmtTrnRsV1Un.RecPmtCancRs = TypeRecPmtCancRsV1AggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypeRecPmtTrnRsV1Un;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtTrnRsV1Un paramTypeRecPmtTrnRsV1Un)
/*  42:    */   {
/*  43: 45 */     if (paramTypeRecPmtTrnRsV1Un == null) {
/*  44: 47 */       paramTypeRecPmtTrnRsV1Un = new TypeRecPmtTrnRsV1Un();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeRecPmtTrnRsV1Un.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeRecPmtTrnRsV1Un.__memberName);
/*  48: 51 */     TypeRecPmtRsV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRsV1Un.RecPmtRs);
/*  49: 52 */     TypeRecPmtModRsV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRsV1Un.RecPmtModRs);
/*  50: 53 */     TypeRecPmtCancRsV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRsV1Un.RecPmtCancRs);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecPmtTrnRsV1Un";
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
/*  67: 72 */         new StructMember("RecPmtRs", TypeRecPmtRsV1AggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("RecPmtModRs", TypeRecPmtModRsV1AggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("RecPmtCancRs", TypeRecPmtCancRsV1AggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtTrnRsV1Un", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeRecPmtTrnRsV1Un paramTypeRecPmtTrnRsV1Un)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeRecPmtTrnRsV1Un);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeRecPmtTrnRsV1Un extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtTrnRsV1Un:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1UnHelper
 * JD-Core Version:    0.7.0.1
 */