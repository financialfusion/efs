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
/*  12:    */ public abstract class TypePayeeTrnRsUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePayeeTrnRsUn clone(TypePayeeTrnRsUn paramTypePayeeTrnRsUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypePayeeTrnRsUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePayeeTrnRsUn localTypePayeeTrnRsUn = new TypePayeeTrnRsUn();
/*  22: 21 */     localTypePayeeTrnRsUn.__preValueTag = paramTypePayeeTrnRsUn.__preValueTag;
/*  23: 22 */     localTypePayeeTrnRsUn.__memberName = paramTypePayeeTrnRsUn.__memberName;
/*  24: 23 */     localTypePayeeTrnRsUn.PayeeRs = TypePayeeRsAggregateHelper.clone(paramTypePayeeTrnRsUn.PayeeRs);
/*  25: 24 */     localTypePayeeTrnRsUn.PayeeModRs = TypePayeeModRsAggregateHelper.clone(paramTypePayeeTrnRsUn.PayeeModRs);
/*  26: 25 */     localTypePayeeTrnRsUn.PayeeDelRs = TypePayeeDelRsAggregateHelper.clone(paramTypePayeeTrnRsUn.PayeeDelRs);
/*  27: 26 */     return localTypePayeeTrnRsUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypePayeeTrnRsUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypePayeeTrnRsUn localTypePayeeTrnRsUn = new TypePayeeTrnRsUn();
/*  33: 33 */     localTypePayeeTrnRsUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypePayeeTrnRsUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypePayeeTrnRsUn.PayeeRs = TypePayeeRsAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypePayeeTrnRsUn.PayeeModRs = TypePayeeModRsAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypePayeeTrnRsUn.PayeeDelRs = TypePayeeDelRsAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypePayeeTrnRsUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypePayeeTrnRsUn paramTypePayeeTrnRsUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypePayeeTrnRsUn == null) {
/*  44: 47 */       paramTypePayeeTrnRsUn = new TypePayeeTrnRsUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypePayeeTrnRsUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypePayeeTrnRsUn.__memberName);
/*  48: 51 */     TypePayeeRsAggregateHelper.write(paramOutputStream, paramTypePayeeTrnRsUn.PayeeRs);
/*  49: 52 */     TypePayeeModRsAggregateHelper.write(paramOutputStream, paramTypePayeeTrnRsUn.PayeeModRs);
/*  50: 53 */     TypePayeeDelRsAggregateHelper.write(paramOutputStream, paramTypePayeeTrnRsUn.PayeeDelRs);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeTrnRsUn";
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
/*  67: 72 */         new StructMember("PayeeRs", TypePayeeRsAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("PayeeModRs", TypePayeeModRsAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("PayeeDelRs", TypePayeeDelRsAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypePayeeTrnRsUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypePayeeTrnRsUn paramTypePayeeTrnRsUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypePayeeTrnRsUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypePayeeTrnRsUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeTrnRsUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsUnHelper
 * JD-Core Version:    0.7.0.1
 */