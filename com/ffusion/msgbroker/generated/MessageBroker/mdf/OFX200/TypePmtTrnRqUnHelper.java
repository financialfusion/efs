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
/*  12:    */ public abstract class TypePmtTrnRqUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePmtTrnRqUn clone(TypePmtTrnRqUn paramTypePmtTrnRqUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypePmtTrnRqUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePmtTrnRqUn localTypePmtTrnRqUn = new TypePmtTrnRqUn();
/*  22: 21 */     localTypePmtTrnRqUn.__preValueTag = paramTypePmtTrnRqUn.__preValueTag;
/*  23: 22 */     localTypePmtTrnRqUn.__memberName = paramTypePmtTrnRqUn.__memberName;
/*  24: 23 */     localTypePmtTrnRqUn.PmtRq = TypePmtRqAggregateHelper.clone(paramTypePmtTrnRqUn.PmtRq);
/*  25: 24 */     localTypePmtTrnRqUn.PmtModRq = TypePmtModRqAggregateHelper.clone(paramTypePmtTrnRqUn.PmtModRq);
/*  26: 25 */     localTypePmtTrnRqUn.PmtCancRq = TypePmtCancRqAggregateHelper.clone(paramTypePmtTrnRqUn.PmtCancRq);
/*  27: 26 */     return localTypePmtTrnRqUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypePmtTrnRqUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypePmtTrnRqUn localTypePmtTrnRqUn = new TypePmtTrnRqUn();
/*  33: 33 */     localTypePmtTrnRqUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypePmtTrnRqUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypePmtTrnRqUn.PmtRq = TypePmtRqAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypePmtTrnRqUn.PmtModRq = TypePmtModRqAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypePmtTrnRqUn.PmtCancRq = TypePmtCancRqAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypePmtTrnRqUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypePmtTrnRqUn paramTypePmtTrnRqUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypePmtTrnRqUn == null) {
/*  44: 47 */       paramTypePmtTrnRqUn = new TypePmtTrnRqUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypePmtTrnRqUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypePmtTrnRqUn.__memberName);
/*  48: 51 */     TypePmtRqAggregateHelper.write(paramOutputStream, paramTypePmtTrnRqUn.PmtRq);
/*  49: 52 */     TypePmtModRqAggregateHelper.write(paramOutputStream, paramTypePmtTrnRqUn.PmtModRq);
/*  50: 53 */     TypePmtCancRqAggregateHelper.write(paramOutputStream, paramTypePmtTrnRqUn.PmtCancRq);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePmtTrnRqUn";
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
/*  67: 72 */         new StructMember("PmtRq", TypePmtRqAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("PmtModRq", TypePmtModRqAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("PmtCancRq", TypePmtCancRqAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypePmtTrnRqUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypePmtTrnRqUn paramTypePmtTrnRqUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypePmtTrnRqUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypePmtTrnRqUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePmtTrnRqUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqUnHelper
 * JD-Core Version:    0.7.0.1
 */