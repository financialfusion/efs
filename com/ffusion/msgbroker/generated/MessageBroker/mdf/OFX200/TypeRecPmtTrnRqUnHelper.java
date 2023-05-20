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
/*  12:    */ public abstract class TypeRecPmtTrnRqUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeRecPmtTrnRqUn clone(TypeRecPmtTrnRqUn paramTypeRecPmtTrnRqUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypeRecPmtTrnRqUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeRecPmtTrnRqUn localTypeRecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
/*  22: 21 */     localTypeRecPmtTrnRqUn.__preValueTag = paramTypeRecPmtTrnRqUn.__preValueTag;
/*  23: 22 */     localTypeRecPmtTrnRqUn.__memberName = paramTypeRecPmtTrnRqUn.__memberName;
/*  24: 23 */     localTypeRecPmtTrnRqUn.RecPmtRq = TypeRecPmtRqAggregateHelper.clone(paramTypeRecPmtTrnRqUn.RecPmtRq);
/*  25: 24 */     localTypeRecPmtTrnRqUn.RecPmtModRq = TypeRecPmtModRqAggregateHelper.clone(paramTypeRecPmtTrnRqUn.RecPmtModRq);
/*  26: 25 */     localTypeRecPmtTrnRqUn.RecPmtCancRq = TypeRecPmtCancRqAggregateHelper.clone(paramTypeRecPmtTrnRqUn.RecPmtCancRq);
/*  27: 26 */     return localTypeRecPmtTrnRqUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeRecPmtTrnRqUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeRecPmtTrnRqUn localTypeRecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
/*  33: 33 */     localTypeRecPmtTrnRqUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeRecPmtTrnRqUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeRecPmtTrnRqUn.RecPmtRq = TypeRecPmtRqAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypeRecPmtTrnRqUn.RecPmtModRq = TypeRecPmtModRqAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypeRecPmtTrnRqUn.RecPmtCancRq = TypeRecPmtCancRqAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypeRecPmtTrnRqUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtTrnRqUn paramTypeRecPmtTrnRqUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypeRecPmtTrnRqUn == null) {
/*  44: 47 */       paramTypeRecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeRecPmtTrnRqUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeRecPmtTrnRqUn.__memberName);
/*  48: 51 */     TypeRecPmtRqAggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRqUn.RecPmtRq);
/*  49: 52 */     TypeRecPmtModRqAggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRqUn.RecPmtModRq);
/*  50: 53 */     TypeRecPmtCancRqAggregateHelper.write(paramOutputStream, paramTypeRecPmtTrnRqUn.RecPmtCancRq);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecPmtTrnRqUn";
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
/*  67: 72 */         new StructMember("RecPmtRq", TypeRecPmtRqAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("RecPmtModRq", TypeRecPmtModRqAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("RecPmtCancRq", TypeRecPmtCancRqAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtTrnRqUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeRecPmtTrnRqUn paramTypeRecPmtTrnRqUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeRecPmtTrnRqUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeRecPmtTrnRqUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecPmtTrnRqUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqUnHelper
 * JD-Core Version:    0.7.0.1
 */