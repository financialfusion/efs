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
/*  12:    */ public abstract class TypeRecIntraRsUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeRecIntraRsUn clone(TypeRecIntraRsUn paramTypeRecIntraRsUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypeRecIntraRsUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeRecIntraRsUn localTypeRecIntraRsUn = new TypeRecIntraRsUn();
/*  22: 21 */     localTypeRecIntraRsUn.__preValueTag = paramTypeRecIntraRsUn.__preValueTag;
/*  23: 22 */     localTypeRecIntraRsUn.__memberName = paramTypeRecIntraRsUn.__memberName;
/*  24: 23 */     localTypeRecIntraRsUn.RecIntraRs = TypeRecIntraRsAggregateHelper.clone(paramTypeRecIntraRsUn.RecIntraRs);
/*  25: 24 */     localTypeRecIntraRsUn.RecIntraModRs = TypeRecIntraModRsAggregateHelper.clone(paramTypeRecIntraRsUn.RecIntraModRs);
/*  26: 25 */     localTypeRecIntraRsUn.RecIntraCanRs = TypeRecIntraCanRsAggregateHelper.clone(paramTypeRecIntraRsUn.RecIntraCanRs);
/*  27: 26 */     return localTypeRecIntraRsUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeRecIntraRsUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeRecIntraRsUn localTypeRecIntraRsUn = new TypeRecIntraRsUn();
/*  33: 33 */     localTypeRecIntraRsUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeRecIntraRsUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeRecIntraRsUn.RecIntraRs = TypeRecIntraRsAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypeRecIntraRsUn.RecIntraModRs = TypeRecIntraModRsAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypeRecIntraRsUn.RecIntraCanRs = TypeRecIntraCanRsAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypeRecIntraRsUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeRecIntraRsUn paramTypeRecIntraRsUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypeRecIntraRsUn == null) {
/*  44: 47 */       paramTypeRecIntraRsUn = new TypeRecIntraRsUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeRecIntraRsUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeRecIntraRsUn.__memberName);
/*  48: 51 */     TypeRecIntraRsAggregateHelper.write(paramOutputStream, paramTypeRecIntraRsUn.RecIntraRs);
/*  49: 52 */     TypeRecIntraModRsAggregateHelper.write(paramOutputStream, paramTypeRecIntraRsUn.RecIntraModRs);
/*  50: 53 */     TypeRecIntraCanRsAggregateHelper.write(paramOutputStream, paramTypeRecIntraRsUn.RecIntraCanRs);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecIntraRsUn";
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
/*  67: 72 */         new StructMember("RecIntraRs", TypeRecIntraRsAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("RecIntraModRs", TypeRecIntraModRsAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("RecIntraCanRs", TypeRecIntraCanRsAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraRsUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeRecIntraRsUn paramTypeRecIntraRsUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeRecIntraRsUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeRecIntraRsUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecIntraRsUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsUnHelper
 * JD-Core Version:    0.7.0.1
 */