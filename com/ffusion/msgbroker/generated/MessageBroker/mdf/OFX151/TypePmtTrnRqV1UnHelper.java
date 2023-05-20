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
/*  12:    */ public abstract class TypePmtTrnRqV1UnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePmtTrnRqV1Un clone(TypePmtTrnRqV1Un paramTypePmtTrnRqV1Un)
/*  17:    */   {
/*  18: 16 */     if (paramTypePmtTrnRqV1Un == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePmtTrnRqV1Un localTypePmtTrnRqV1Un = new TypePmtTrnRqV1Un();
/*  22: 21 */     localTypePmtTrnRqV1Un.__preValueTag = paramTypePmtTrnRqV1Un.__preValueTag;
/*  23: 22 */     localTypePmtTrnRqV1Un.__memberName = paramTypePmtTrnRqV1Un.__memberName;
/*  24: 23 */     localTypePmtTrnRqV1Un.PmtRq = TypePmtRqV1AggregateHelper.clone(paramTypePmtTrnRqV1Un.PmtRq);
/*  25: 24 */     localTypePmtTrnRqV1Un.PmtModRq = TypePmtModRqV1AggregateHelper.clone(paramTypePmtTrnRqV1Un.PmtModRq);
/*  26: 25 */     localTypePmtTrnRqV1Un.PmtCancRq = TypePmtCancRqV1AggregateHelper.clone(paramTypePmtTrnRqV1Un.PmtCancRq);
/*  27: 26 */     return localTypePmtTrnRqV1Un;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypePmtTrnRqV1Un read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypePmtTrnRqV1Un localTypePmtTrnRqV1Un = new TypePmtTrnRqV1Un();
/*  33: 33 */     localTypePmtTrnRqV1Un.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypePmtTrnRqV1Un.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypePmtTrnRqV1Un.PmtRq = TypePmtRqV1AggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypePmtTrnRqV1Un.PmtModRq = TypePmtModRqV1AggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypePmtTrnRqV1Un.PmtCancRq = TypePmtCancRqV1AggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypePmtTrnRqV1Un;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypePmtTrnRqV1Un paramTypePmtTrnRqV1Un)
/*  42:    */   {
/*  43: 45 */     if (paramTypePmtTrnRqV1Un == null) {
/*  44: 47 */       paramTypePmtTrnRqV1Un = new TypePmtTrnRqV1Un();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypePmtTrnRqV1Un.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypePmtTrnRqV1Un.__memberName);
/*  48: 51 */     TypePmtRqV1AggregateHelper.write(paramOutputStream, paramTypePmtTrnRqV1Un.PmtRq);
/*  49: 52 */     TypePmtModRqV1AggregateHelper.write(paramOutputStream, paramTypePmtTrnRqV1Un.PmtModRq);
/*  50: 53 */     TypePmtCancRqV1AggregateHelper.write(paramOutputStream, paramTypePmtTrnRqV1Un.PmtCancRq);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtTrnRqV1Un";
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
/*  67: 72 */         new StructMember("PmtRq", TypePmtRqV1AggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("PmtModRq", TypePmtModRqV1AggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("PmtCancRq", TypePmtCancRqV1AggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypePmtTrnRqV1Un", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypePmtTrnRqV1Un paramTypePmtTrnRqV1Un)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypePmtTrnRqV1Un);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypePmtTrnRqV1Un extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtTrnRqV1Un:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1UnHelper
 * JD-Core Version:    0.7.0.1
 */