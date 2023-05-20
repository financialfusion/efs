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
/* 12:   */ public abstract class TypePayeeRqV1UnHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePayeeRqV1Un clone(TypePayeeRqV1Un paramTypePayeeRqV1Un)
/* 17:   */   {
/* 18:16 */     if (paramTypePayeeRqV1Un == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePayeeRqV1Un localTypePayeeRqV1Un = new TypePayeeRqV1Un();
/* 22:21 */     localTypePayeeRqV1Un.__preValueTag = paramTypePayeeRqV1Un.__preValueTag;
/* 23:22 */     localTypePayeeRqV1Un.__memberName = paramTypePayeeRqV1Un.__memberName;
/* 24:23 */     localTypePayeeRqV1Un.PayeeID = paramTypePayeeRqV1Un.PayeeID;
/* 25:24 */     localTypePayeeRqV1Un.PayeeRqV1Cm = TypePayeeRqV1CmHelper.clone(paramTypePayeeRqV1Un.PayeeRqV1Cm);
/* 26:25 */     return localTypePayeeRqV1Un;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypePayeeRqV1Un read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypePayeeRqV1Un localTypePayeeRqV1Un = new TypePayeeRqV1Un();
/* 32:32 */     localTypePayeeRqV1Un.__preValueTag = paramInputStream.read_string();
/* 33:33 */     localTypePayeeRqV1Un.__memberName = paramInputStream.read_string();
/* 34:34 */     localTypePayeeRqV1Un.PayeeID = paramInputStream.read_string();
/* 35:35 */     localTypePayeeRqV1Un.PayeeRqV1Cm = TypePayeeRqV1CmHelper.read(paramInputStream);
/* 36:36 */     return localTypePayeeRqV1Un;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypePayeeRqV1Un paramTypePayeeRqV1Un)
/* 40:   */   {
/* 41:43 */     if (paramTypePayeeRqV1Un == null) {
/* 42:45 */       paramTypePayeeRqV1Un = new TypePayeeRqV1Un();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypePayeeRqV1Un.__preValueTag);
/* 45:48 */     paramOutputStream.write_string(paramTypePayeeRqV1Un.__memberName);
/* 46:49 */     paramOutputStream.write_string(paramTypePayeeRqV1Un.PayeeID);
/* 47:50 */     TypePayeeRqV1CmHelper.write(paramOutputStream, paramTypePayeeRqV1Un.PayeeRqV1Cm);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeRqV1Un";
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
/* 65:70 */         new StructMember("PayeeRqV1Cm", TypePayeeRqV1CmHelper.type(), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypePayeeRqV1Un", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypePayeeRqV1Un paramTypePayeeRqV1Un)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypePayeeRqV1Un);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypePayeeRqV1Un extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeRqV1Un:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1UnHelper
 * JD-Core Version:    0.7.0.1
 */