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
/*  12:    */ public abstract class TypeRecIntraTrnRqUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeRecIntraTrnRqUn clone(TypeRecIntraTrnRqUn paramTypeRecIntraTrnRqUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypeRecIntraTrnRqUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeRecIntraTrnRqUn localTypeRecIntraTrnRqUn = new TypeRecIntraTrnRqUn();
/*  22: 21 */     localTypeRecIntraTrnRqUn.__preValueTag = paramTypeRecIntraTrnRqUn.__preValueTag;
/*  23: 22 */     localTypeRecIntraTrnRqUn.__memberName = paramTypeRecIntraTrnRqUn.__memberName;
/*  24: 23 */     localTypeRecIntraTrnRqUn.RecIntraRq = TypeRecIntraRqAggregateHelper.clone(paramTypeRecIntraTrnRqUn.RecIntraRq);
/*  25: 24 */     localTypeRecIntraTrnRqUn.RecIntraModRq = TypeRecIntraModRqAggregateHelper.clone(paramTypeRecIntraTrnRqUn.RecIntraModRq);
/*  26: 25 */     localTypeRecIntraTrnRqUn.RecIntraCanRq = TypeRecIntraCanRqAggregateHelper.clone(paramTypeRecIntraTrnRqUn.RecIntraCanRq);
/*  27: 26 */     return localTypeRecIntraTrnRqUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeRecIntraTrnRqUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeRecIntraTrnRqUn localTypeRecIntraTrnRqUn = new TypeRecIntraTrnRqUn();
/*  33: 33 */     localTypeRecIntraTrnRqUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeRecIntraTrnRqUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeRecIntraTrnRqUn.RecIntraRq = TypeRecIntraRqAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypeRecIntraTrnRqUn.RecIntraModRq = TypeRecIntraModRqAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypeRecIntraTrnRqUn.RecIntraCanRq = TypeRecIntraCanRqAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypeRecIntraTrnRqUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeRecIntraTrnRqUn paramTypeRecIntraTrnRqUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypeRecIntraTrnRqUn == null) {
/*  44: 47 */       paramTypeRecIntraTrnRqUn = new TypeRecIntraTrnRqUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeRecIntraTrnRqUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeRecIntraTrnRqUn.__memberName);
/*  48: 51 */     TypeRecIntraRqAggregateHelper.write(paramOutputStream, paramTypeRecIntraTrnRqUn.RecIntraRq);
/*  49: 52 */     TypeRecIntraModRqAggregateHelper.write(paramOutputStream, paramTypeRecIntraTrnRqUn.RecIntraModRq);
/*  50: 53 */     TypeRecIntraCanRqAggregateHelper.write(paramOutputStream, paramTypeRecIntraTrnRqUn.RecIntraCanRq);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecIntraTrnRqUn";
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
/*  67: 72 */         new StructMember("RecIntraRq", TypeRecIntraRqAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("RecIntraModRq", TypeRecIntraModRqAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("RecIntraCanRq", TypeRecIntraCanRqAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraTrnRqUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeRecIntraTrnRqUn paramTypeRecIntraTrnRqUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeRecIntraTrnRqUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeRecIntraTrnRqUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecIntraTrnRqUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqUnHelper
 * JD-Core Version:    0.7.0.1
 */