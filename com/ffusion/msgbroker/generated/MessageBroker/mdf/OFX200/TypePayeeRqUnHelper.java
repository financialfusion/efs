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
/* 12:   */ public abstract class TypePayeeRqUnHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePayeeRqUn clone(TypePayeeRqUn paramTypePayeeRqUn)
/* 17:   */   {
/* 18:16 */     if (paramTypePayeeRqUn == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePayeeRqUn localTypePayeeRqUn = new TypePayeeRqUn();
/* 22:21 */     localTypePayeeRqUn.__preValueTag = paramTypePayeeRqUn.__preValueTag;
/* 23:22 */     localTypePayeeRqUn.__memberName = paramTypePayeeRqUn.__memberName;
/* 24:23 */     localTypePayeeRqUn.PayeeID = paramTypePayeeRqUn.PayeeID;
/* 25:24 */     localTypePayeeRqUn.Payee = TypePayeeAggregateHelper.clone(paramTypePayeeRqUn.Payee);
/* 26:25 */     return localTypePayeeRqUn;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypePayeeRqUn read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypePayeeRqUn localTypePayeeRqUn = new TypePayeeRqUn();
/* 32:32 */     localTypePayeeRqUn.__preValueTag = paramInputStream.read_string();
/* 33:33 */     localTypePayeeRqUn.__memberName = paramInputStream.read_string();
/* 34:34 */     localTypePayeeRqUn.PayeeID = paramInputStream.read_string();
/* 35:35 */     localTypePayeeRqUn.Payee = TypePayeeAggregateHelper.read(paramInputStream);
/* 36:36 */     return localTypePayeeRqUn;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypePayeeRqUn paramTypePayeeRqUn)
/* 40:   */   {
/* 41:43 */     if (paramTypePayeeRqUn == null) {
/* 42:45 */       paramTypePayeeRqUn = new TypePayeeRqUn();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypePayeeRqUn.__preValueTag);
/* 45:48 */     paramOutputStream.write_string(paramTypePayeeRqUn.__memberName);
/* 46:49 */     paramOutputStream.write_string(paramTypePayeeRqUn.PayeeID);
/* 47:50 */     TypePayeeAggregateHelper.write(paramOutputStream, paramTypePayeeRqUn.Payee);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeRqUn";
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
/* 64:69 */         new StructMember("PayeeID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 65:70 */         new StructMember("Payee", TypePayeeAggregateHelper.type(), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypePayeeRqUn", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypePayeeRqUn paramTypePayeeRqUn)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypePayeeRqUn);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypePayeeRqUn extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeRqUn:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRqUnHelper
 * JD-Core Version:    0.7.0.1
 */