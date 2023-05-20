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
/* 12:   */ public abstract class TypePayeeUnHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePayeeUn clone(TypePayeeUn paramTypePayeeUn)
/* 17:   */   {
/* 18:16 */     if (paramTypePayeeUn == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePayeeUn localTypePayeeUn = new TypePayeeUn();
/* 22:21 */     localTypePayeeUn.__preValueTag = paramTypePayeeUn.__preValueTag;
/* 23:22 */     localTypePayeeUn.__memberName = paramTypePayeeUn.__memberName;
/* 24:23 */     localTypePayeeUn.PayeeID = paramTypePayeeUn.PayeeID;
/* 25:24 */     localTypePayeeUn.Payee = TypePayeeV1AggregateHelper.clone(paramTypePayeeUn.Payee);
/* 26:25 */     return localTypePayeeUn;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypePayeeUn read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypePayeeUn localTypePayeeUn = new TypePayeeUn();
/* 32:32 */     localTypePayeeUn.__preValueTag = paramInputStream.read_string();
/* 33:33 */     localTypePayeeUn.__memberName = paramInputStream.read_string();
/* 34:34 */     localTypePayeeUn.PayeeID = paramInputStream.read_string();
/* 35:35 */     localTypePayeeUn.Payee = TypePayeeV1AggregateHelper.read(paramInputStream);
/* 36:36 */     return localTypePayeeUn;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypePayeeUn paramTypePayeeUn)
/* 40:   */   {
/* 41:43 */     if (paramTypePayeeUn == null) {
/* 42:45 */       paramTypePayeeUn = new TypePayeeUn();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypePayeeUn.__preValueTag);
/* 45:48 */     paramOutputStream.write_string(paramTypePayeeUn.__memberName);
/* 46:49 */     paramOutputStream.write_string(paramTypePayeeUn.PayeeID);
/* 47:50 */     TypePayeeV1AggregateHelper.write(paramOutputStream, paramTypePayeeUn.Payee);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeUn";
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
/* 65:70 */         new StructMember("Payee", TypePayeeV1AggregateHelper.type(), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypePayeeUn", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypePayeeUn paramTypePayeeUn)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypePayeeUn);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypePayeeUn extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeUn:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeUnHelper
 * JD-Core Version:    0.7.0.1
 */