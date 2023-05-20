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
/*  12:    */ public abstract class TypeIntraTrnRsUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeIntraTrnRsUn clone(TypeIntraTrnRsUn paramTypeIntraTrnRsUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypeIntraTrnRsUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeIntraTrnRsUn localTypeIntraTrnRsUn = new TypeIntraTrnRsUn();
/*  22: 21 */     localTypeIntraTrnRsUn.__preValueTag = paramTypeIntraTrnRsUn.__preValueTag;
/*  23: 22 */     localTypeIntraTrnRsUn.__memberName = paramTypeIntraTrnRsUn.__memberName;
/*  24: 23 */     localTypeIntraTrnRsUn.IntraRs = TypeIntraRsAggregateHelper.clone(paramTypeIntraTrnRsUn.IntraRs);
/*  25: 24 */     localTypeIntraTrnRsUn.IntraModRs = TypeIntraModRsAggregateHelper.clone(paramTypeIntraTrnRsUn.IntraModRs);
/*  26: 25 */     localTypeIntraTrnRsUn.IntraCanRs = TypeIntraCanRsAggregateHelper.clone(paramTypeIntraTrnRsUn.IntraCanRs);
/*  27: 26 */     return localTypeIntraTrnRsUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeIntraTrnRsUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeIntraTrnRsUn localTypeIntraTrnRsUn = new TypeIntraTrnRsUn();
/*  33: 33 */     localTypeIntraTrnRsUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeIntraTrnRsUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeIntraTrnRsUn.IntraRs = TypeIntraRsAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypeIntraTrnRsUn.IntraModRs = TypeIntraModRsAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypeIntraTrnRsUn.IntraCanRs = TypeIntraCanRsAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypeIntraTrnRsUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeIntraTrnRsUn paramTypeIntraTrnRsUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypeIntraTrnRsUn == null) {
/*  44: 47 */       paramTypeIntraTrnRsUn = new TypeIntraTrnRsUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeIntraTrnRsUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeIntraTrnRsUn.__memberName);
/*  48: 51 */     TypeIntraRsAggregateHelper.write(paramOutputStream, paramTypeIntraTrnRsUn.IntraRs);
/*  49: 52 */     TypeIntraModRsAggregateHelper.write(paramOutputStream, paramTypeIntraTrnRsUn.IntraModRs);
/*  50: 53 */     TypeIntraCanRsAggregateHelper.write(paramOutputStream, paramTypeIntraTrnRsUn.IntraCanRs);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeIntraTrnRsUn";
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
/*  67: 72 */         new StructMember("IntraRs", TypeIntraRsAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("IntraModRs", TypeIntraModRsAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("IntraCanRs", TypeIntraCanRsAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeIntraTrnRsUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeIntraTrnRsUn paramTypeIntraTrnRsUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeIntraTrnRsUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeIntraTrnRsUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeIntraTrnRsUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUnHelper
 * JD-Core Version:    0.7.0.1
 */