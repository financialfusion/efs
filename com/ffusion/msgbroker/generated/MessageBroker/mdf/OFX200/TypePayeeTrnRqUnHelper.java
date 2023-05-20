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
/*  12:    */ public abstract class TypePayeeTrnRqUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePayeeTrnRqUn clone(TypePayeeTrnRqUn paramTypePayeeTrnRqUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypePayeeTrnRqUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePayeeTrnRqUn localTypePayeeTrnRqUn = new TypePayeeTrnRqUn();
/*  22: 21 */     localTypePayeeTrnRqUn.__preValueTag = paramTypePayeeTrnRqUn.__preValueTag;
/*  23: 22 */     localTypePayeeTrnRqUn.__memberName = paramTypePayeeTrnRqUn.__memberName;
/*  24: 23 */     localTypePayeeTrnRqUn.PayeeRq = TypePayeeRqAggregateHelper.clone(paramTypePayeeTrnRqUn.PayeeRq);
/*  25: 24 */     localTypePayeeTrnRqUn.PayeeModRq = TypePayeeModRqAggregateHelper.clone(paramTypePayeeTrnRqUn.PayeeModRq);
/*  26: 25 */     localTypePayeeTrnRqUn.PayeeDelRq = TypePayeeDelRqAggregateHelper.clone(paramTypePayeeTrnRqUn.PayeeDelRq);
/*  27: 26 */     return localTypePayeeTrnRqUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypePayeeTrnRqUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypePayeeTrnRqUn localTypePayeeTrnRqUn = new TypePayeeTrnRqUn();
/*  33: 33 */     localTypePayeeTrnRqUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypePayeeTrnRqUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypePayeeTrnRqUn.PayeeRq = TypePayeeRqAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypePayeeTrnRqUn.PayeeModRq = TypePayeeModRqAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypePayeeTrnRqUn.PayeeDelRq = TypePayeeDelRqAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypePayeeTrnRqUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypePayeeTrnRqUn paramTypePayeeTrnRqUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypePayeeTrnRqUn == null) {
/*  44: 47 */       paramTypePayeeTrnRqUn = new TypePayeeTrnRqUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypePayeeTrnRqUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypePayeeTrnRqUn.__memberName);
/*  48: 51 */     TypePayeeRqAggregateHelper.write(paramOutputStream, paramTypePayeeTrnRqUn.PayeeRq);
/*  49: 52 */     TypePayeeModRqAggregateHelper.write(paramOutputStream, paramTypePayeeTrnRqUn.PayeeModRq);
/*  50: 53 */     TypePayeeDelRqAggregateHelper.write(paramOutputStream, paramTypePayeeTrnRqUn.PayeeDelRq);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeTrnRqUn";
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
/*  67: 72 */         new StructMember("PayeeRq", TypePayeeRqAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("PayeeModRq", TypePayeeModRqAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("PayeeDelRq", TypePayeeDelRqAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypePayeeTrnRqUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypePayeeTrnRqUn paramTypePayeeTrnRqUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypePayeeTrnRqUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypePayeeTrnRqUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeTrnRqUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqUnHelper
 * JD-Core Version:    0.7.0.1
 */