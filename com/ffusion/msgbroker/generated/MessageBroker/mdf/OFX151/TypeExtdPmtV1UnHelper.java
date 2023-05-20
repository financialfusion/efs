/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/* 12:   */ public abstract class TypeExtdPmtV1UnHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeExtdPmtV1Un clone(TypeExtdPmtV1Un paramTypeExtdPmtV1Un)
/* 17:   */   {
/* 18:16 */     if (paramTypeExtdPmtV1Un == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeExtdPmtV1Un localTypeExtdPmtV1Un = new TypeExtdPmtV1Un();
/* 22:21 */     localTypeExtdPmtV1Un.__preValueTag = paramTypeExtdPmtV1Un.__preValueTag;
/* 23:22 */     localTypeExtdPmtV1Un.__memberName = paramTypeExtdPmtV1Un.__memberName;
/* 24:23 */     localTypeExtdPmtV1Un.ExtdPmtV1Cm = TypeExtdPmtV1CmHelper.clone(paramTypeExtdPmtV1Un.ExtdPmtV1Cm);
/* 25:24 */     localTypeExtdPmtV1Un.ExtdPmtInv = TypeExtdPmtInvV1AggregateHelper.clone(paramTypeExtdPmtV1Un.ExtdPmtInv);
/* 26:25 */     return localTypeExtdPmtV1Un;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeExtdPmtV1Un read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypeExtdPmtV1Un localTypeExtdPmtV1Un = new TypeExtdPmtV1Un();
/* 32:32 */     localTypeExtdPmtV1Un.__preValueTag = paramInputStream.read_string();
/* 33:33 */     localTypeExtdPmtV1Un.__memberName = paramInputStream.read_string();
/* 34:34 */     localTypeExtdPmtV1Un.ExtdPmtV1Cm = TypeExtdPmtV1CmHelper.read(paramInputStream);
/* 35:35 */     localTypeExtdPmtV1Un.ExtdPmtInv = TypeExtdPmtInvV1AggregateHelper.read(paramInputStream);
/* 36:36 */     return localTypeExtdPmtV1Un;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypeExtdPmtV1Un paramTypeExtdPmtV1Un)
/* 40:   */   {
/* 41:43 */     if (paramTypeExtdPmtV1Un == null) {
/* 42:45 */       paramTypeExtdPmtV1Un = new TypeExtdPmtV1Un();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypeExtdPmtV1Un.__preValueTag);
/* 45:48 */     paramOutputStream.write_string(paramTypeExtdPmtV1Un.__memberName);
/* 46:49 */     TypeExtdPmtV1CmHelper.write(paramOutputStream, paramTypeExtdPmtV1Un.ExtdPmtV1Cm);
/* 47:50 */     TypeExtdPmtInvV1AggregateHelper.write(paramOutputStream, paramTypeExtdPmtV1Un.ExtdPmtInv);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeExtdPmtV1Un";
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
/* 64:69 */         new StructMember("ExtdPmtV1Cm", TypeExtdPmtV1CmHelper.type(), null), 
/* 65:70 */         new StructMember("ExtdPmtInv", TypeExtdPmtInvV1AggregateHelper.type(), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypeExtdPmtV1Un", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypeExtdPmtV1Un paramTypeExtdPmtV1Un)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypeExtdPmtV1Un);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypeExtdPmtV1Un extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeExtdPmtV1Un:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPmtV1UnHelper
 * JD-Core Version:    0.7.0.1
 */