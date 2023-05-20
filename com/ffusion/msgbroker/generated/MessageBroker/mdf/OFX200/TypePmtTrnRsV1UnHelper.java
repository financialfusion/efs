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
/*  12:    */ public abstract class TypePmtTrnRsV1UnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePmtTrnRsV1Un clone(TypePmtTrnRsV1Un paramTypePmtTrnRsV1Un)
/*  17:    */   {
/*  18: 16 */     if (paramTypePmtTrnRsV1Un == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePmtTrnRsV1Un localTypePmtTrnRsV1Un = new TypePmtTrnRsV1Un();
/*  22: 21 */     localTypePmtTrnRsV1Un.__preValueTag = paramTypePmtTrnRsV1Un.__preValueTag;
/*  23: 22 */     localTypePmtTrnRsV1Un.__memberName = paramTypePmtTrnRsV1Un.__memberName;
/*  24: 23 */     localTypePmtTrnRsV1Un.PmtRs = TypePmtRsAggregateHelper.clone(paramTypePmtTrnRsV1Un.PmtRs);
/*  25: 24 */     localTypePmtTrnRsV1Un.PmtModRs = TypePmtModRsAggregateHelper.clone(paramTypePmtTrnRsV1Un.PmtModRs);
/*  26: 25 */     localTypePmtTrnRsV1Un.PmtCancRs = TypePmtCancRsAggregateHelper.clone(paramTypePmtTrnRsV1Un.PmtCancRs);
/*  27: 26 */     return localTypePmtTrnRsV1Un;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypePmtTrnRsV1Un read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypePmtTrnRsV1Un localTypePmtTrnRsV1Un = new TypePmtTrnRsV1Un();
/*  33: 33 */     localTypePmtTrnRsV1Un.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypePmtTrnRsV1Un.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypePmtTrnRsV1Un.PmtRs = TypePmtRsAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypePmtTrnRsV1Un.PmtModRs = TypePmtModRsAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypePmtTrnRsV1Un.PmtCancRs = TypePmtCancRsAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypePmtTrnRsV1Un;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypePmtTrnRsV1Un paramTypePmtTrnRsV1Un)
/*  42:    */   {
/*  43: 45 */     if (paramTypePmtTrnRsV1Un == null) {
/*  44: 47 */       paramTypePmtTrnRsV1Un = new TypePmtTrnRsV1Un();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypePmtTrnRsV1Un.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypePmtTrnRsV1Un.__memberName);
/*  48: 51 */     TypePmtRsAggregateHelper.write(paramOutputStream, paramTypePmtTrnRsV1Un.PmtRs);
/*  49: 52 */     TypePmtModRsAggregateHelper.write(paramOutputStream, paramTypePmtTrnRsV1Un.PmtModRs);
/*  50: 53 */     TypePmtCancRsAggregateHelper.write(paramOutputStream, paramTypePmtTrnRsV1Un.PmtCancRs);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePmtTrnRsV1Un";
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
/*  67: 72 */         new StructMember("PmtRs", TypePmtRsAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("PmtModRs", TypePmtModRsAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("PmtCancRs", TypePmtCancRsAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypePmtTrnRsV1Un", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypePmtTrnRsV1Un paramTypePmtTrnRsV1Un)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypePmtTrnRsV1Un);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypePmtTrnRsV1Un extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePmtTrnRsV1Un:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1UnHelper
 * JD-Core Version:    0.7.0.1
 */