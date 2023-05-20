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
/*  12:    */ public abstract class TypeRecIntraTrnRqV1UnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeRecIntraTrnRqV1Un clone(TypeRecIntraTrnRqV1Un paramTypeRecIntraTrnRqV1Un)
/*  17:    */   {
/*  18: 16 */     if (paramTypeRecIntraTrnRqV1Un == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeRecIntraTrnRqV1Un localTypeRecIntraTrnRqV1Un = new TypeRecIntraTrnRqV1Un();
/*  22: 21 */     localTypeRecIntraTrnRqV1Un.__preValueTag = paramTypeRecIntraTrnRqV1Un.__preValueTag;
/*  23: 22 */     localTypeRecIntraTrnRqV1Un.__memberName = paramTypeRecIntraTrnRqV1Un.__memberName;
/*  24: 23 */     localTypeRecIntraTrnRqV1Un.RecIntraRq = TypeRecIntraRqV1AggregateHelper.clone(paramTypeRecIntraTrnRqV1Un.RecIntraRq);
/*  25: 24 */     localTypeRecIntraTrnRqV1Un.RecIntraModRq = TypeRecIntraModRqV1AggregateHelper.clone(paramTypeRecIntraTrnRqV1Un.RecIntraModRq);
/*  26: 25 */     localTypeRecIntraTrnRqV1Un.RecIntraCanRq = TypeRecIntraCanRqV1AggregateHelper.clone(paramTypeRecIntraTrnRqV1Un.RecIntraCanRq);
/*  27: 26 */     return localTypeRecIntraTrnRqV1Un;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeRecIntraTrnRqV1Un read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeRecIntraTrnRqV1Un localTypeRecIntraTrnRqV1Un = new TypeRecIntraTrnRqV1Un();
/*  33: 33 */     localTypeRecIntraTrnRqV1Un.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeRecIntraTrnRqV1Un.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeRecIntraTrnRqV1Un.RecIntraRq = TypeRecIntraRqV1AggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypeRecIntraTrnRqV1Un.RecIntraModRq = TypeRecIntraModRqV1AggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypeRecIntraTrnRqV1Un.RecIntraCanRq = TypeRecIntraCanRqV1AggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypeRecIntraTrnRqV1Un;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeRecIntraTrnRqV1Un paramTypeRecIntraTrnRqV1Un)
/*  42:    */   {
/*  43: 45 */     if (paramTypeRecIntraTrnRqV1Un == null) {
/*  44: 47 */       paramTypeRecIntraTrnRqV1Un = new TypeRecIntraTrnRqV1Un();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeRecIntraTrnRqV1Un.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeRecIntraTrnRqV1Un.__memberName);
/*  48: 51 */     TypeRecIntraRqV1AggregateHelper.write(paramOutputStream, paramTypeRecIntraTrnRqV1Un.RecIntraRq);
/*  49: 52 */     TypeRecIntraModRqV1AggregateHelper.write(paramOutputStream, paramTypeRecIntraTrnRqV1Un.RecIntraModRq);
/*  50: 53 */     TypeRecIntraCanRqV1AggregateHelper.write(paramOutputStream, paramTypeRecIntraTrnRqV1Un.RecIntraCanRq);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecIntraTrnRqV1Un";
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
/*  67: 72 */         new StructMember("RecIntraRq", TypeRecIntraRqV1AggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("RecIntraModRq", TypeRecIntraModRqV1AggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("RecIntraCanRq", TypeRecIntraCanRqV1AggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraTrnRqV1Un", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeRecIntraTrnRqV1Un paramTypeRecIntraTrnRqV1Un)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeRecIntraTrnRqV1Un);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeRecIntraTrnRqV1Un extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecIntraTrnRqV1Un:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1UnHelper
 * JD-Core Version:    0.7.0.1
 */