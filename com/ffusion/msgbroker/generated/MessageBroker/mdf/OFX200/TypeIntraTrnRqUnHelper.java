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
/*  12:    */ public abstract class TypeIntraTrnRqUnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeIntraTrnRqUn clone(TypeIntraTrnRqUn paramTypeIntraTrnRqUn)
/*  17:    */   {
/*  18: 16 */     if (paramTypeIntraTrnRqUn == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeIntraTrnRqUn localTypeIntraTrnRqUn = new TypeIntraTrnRqUn();
/*  22: 21 */     localTypeIntraTrnRqUn.__preValueTag = paramTypeIntraTrnRqUn.__preValueTag;
/*  23: 22 */     localTypeIntraTrnRqUn.__memberName = paramTypeIntraTrnRqUn.__memberName;
/*  24: 23 */     localTypeIntraTrnRqUn.IntraRq = TypeIntraRqAggregateHelper.clone(paramTypeIntraTrnRqUn.IntraRq);
/*  25: 24 */     localTypeIntraTrnRqUn.IntraModRq = TypeIntraModRqAggregateHelper.clone(paramTypeIntraTrnRqUn.IntraModRq);
/*  26: 25 */     localTypeIntraTrnRqUn.IntraCanRq = TypeIntraCanRqAggregateHelper.clone(paramTypeIntraTrnRqUn.IntraCanRq);
/*  27: 26 */     return localTypeIntraTrnRqUn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeIntraTrnRqUn read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeIntraTrnRqUn localTypeIntraTrnRqUn = new TypeIntraTrnRqUn();
/*  33: 33 */     localTypeIntraTrnRqUn.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeIntraTrnRqUn.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeIntraTrnRqUn.IntraRq = TypeIntraRqAggregateHelper.read(paramInputStream);
/*  36: 36 */     localTypeIntraTrnRqUn.IntraModRq = TypeIntraModRqAggregateHelper.read(paramInputStream);
/*  37: 37 */     localTypeIntraTrnRqUn.IntraCanRq = TypeIntraCanRqAggregateHelper.read(paramInputStream);
/*  38: 38 */     return localTypeIntraTrnRqUn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeIntraTrnRqUn paramTypeIntraTrnRqUn)
/*  42:    */   {
/*  43: 45 */     if (paramTypeIntraTrnRqUn == null) {
/*  44: 47 */       paramTypeIntraTrnRqUn = new TypeIntraTrnRqUn();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeIntraTrnRqUn.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeIntraTrnRqUn.__memberName);
/*  48: 51 */     TypeIntraRqAggregateHelper.write(paramOutputStream, paramTypeIntraTrnRqUn.IntraRq);
/*  49: 52 */     TypeIntraModRqAggregateHelper.write(paramOutputStream, paramTypeIntraTrnRqUn.IntraModRq);
/*  50: 53 */     TypeIntraCanRqAggregateHelper.write(paramOutputStream, paramTypeIntraTrnRqUn.IntraCanRq);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeIntraTrnRqUn";
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
/*  67: 72 */         new StructMember("IntraRq", TypeIntraRqAggregateHelper.type(), null), 
/*  68: 73 */         new StructMember("IntraModRq", TypeIntraModRqAggregateHelper.type(), null), 
/*  69: 74 */         new StructMember("IntraCanRq", TypeIntraCanRqAggregateHelper.type(), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeIntraTrnRqUn", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeIntraTrnRqUn paramTypeIntraTrnRqUn)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeIntraTrnRqUn);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeIntraTrnRqUn extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeIntraTrnRqUn:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqUnHelper
 * JD-Core Version:    0.7.0.1
 */