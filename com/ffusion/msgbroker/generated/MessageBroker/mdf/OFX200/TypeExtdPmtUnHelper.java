/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.StructMember;
/*  7:   */ import org.omg.CORBA.TCKind;
/*  8:   */ import org.omg.CORBA.TypeCode;
/*  9:   */ import org.omg.CORBA.portable.InputStream;
/* 10:   */ import org.omg.CORBA.portable.OutputStream;
/* 11:   */ 
/* 12:   */ public abstract class TypeExtdPmtUnHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeExtdPmtUn clone(TypeExtdPmtUn paramTypeExtdPmtUn)
/* 17:   */   {
/* 18:16 */     if (paramTypeExtdPmtUn == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeExtdPmtUn localTypeExtdPmtUn = new TypeExtdPmtUn();
/* 22:21 */     localTypeExtdPmtUn.__preValueTag = paramTypeExtdPmtUn.__preValueTag;
/* 23:22 */     localTypeExtdPmtUn.__memberName = paramTypeExtdPmtUn.__memberName;
/* 24:23 */     localTypeExtdPmtUn.ExtdPmtCm = TypeExtdPmtCmHelper.clone(paramTypeExtdPmtUn.ExtdPmtCm);
/* 25:24 */     localTypeExtdPmtUn.ExtdPmtInv = TypeExtdPmtInvAggregateHelper.clone(paramTypeExtdPmtUn.ExtdPmtInv);
/* 26:25 */     return localTypeExtdPmtUn;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeExtdPmtUn read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypeExtdPmtUn localTypeExtdPmtUn = new TypeExtdPmtUn();
/* 32:32 */     localTypeExtdPmtUn.__preValueTag = paramInputStream.read_string();
/* 33:33 */     localTypeExtdPmtUn.__memberName = paramInputStream.read_string();
/* 34:34 */     localTypeExtdPmtUn.ExtdPmtCm = TypeExtdPmtCmHelper.read(paramInputStream);
/* 35:35 */     localTypeExtdPmtUn.ExtdPmtInv = TypeExtdPmtInvAggregateHelper.read(paramInputStream);
/* 36:36 */     return localTypeExtdPmtUn;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypeExtdPmtUn paramTypeExtdPmtUn)
/* 40:   */   {
/* 41:43 */     if (paramTypeExtdPmtUn == null) {
/* 42:45 */       paramTypeExtdPmtUn = new TypeExtdPmtUn();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypeExtdPmtUn.__preValueTag);
/* 45:48 */     paramOutputStream.write_string(paramTypeExtdPmtUn.__memberName);
/* 46:49 */     TypeExtdPmtCmHelper.write(paramOutputStream, paramTypeExtdPmtUn.ExtdPmtCm);
/* 47:50 */     TypeExtdPmtInvAggregateHelper.write(paramOutputStream, paramTypeExtdPmtUn.ExtdPmtInv);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeExtdPmtUn";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static TypeCode type()
/* 56:   */   {
/* 57:62 */     if (_type == null)
/* 58:   */     {
/* 59:64 */       ORB localORB = ORB.init();
/* 60:65 */       StructMember[] arrayOfStructMember = 
/* 61:66 */         {
/* 62:67 */         new StructMember("__preValueTag", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 63:68 */         new StructMember("__memberName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 64:69 */         new StructMember("ExtdPmtCm", TypeExtdPmtCmHelper.type(), null), 
/* 65:70 */         new StructMember("ExtdPmtInv", TypeExtdPmtInvAggregateHelper.type(), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypeExtdPmtUn", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypeExtdPmtUn paramTypeExtdPmtUn)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypeExtdPmtUn);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypeExtdPmtUn extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeExtdPmtUn:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPmtUnHelper
 * JD-Core Version:    0.7.0.1
 */