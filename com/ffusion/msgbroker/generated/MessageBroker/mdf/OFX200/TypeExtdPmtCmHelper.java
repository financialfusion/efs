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
/* 12:   */ public abstract class TypeExtdPmtCmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeExtdPmtCm clone(TypeExtdPmtCm paramTypeExtdPmtCm)
/* 17:   */   {
/* 18:16 */     if (paramTypeExtdPmtCm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeExtdPmtCm localTypeExtdPmtCm = new TypeExtdPmtCm();
/* 22:21 */     localTypeExtdPmtCm.ExtdPmtDsc = paramTypeExtdPmtCm.ExtdPmtDsc;
/* 23:22 */     localTypeExtdPmtCm.ExtdPmtInv = TypeExtdPmtInvAggregateHelper.clone(paramTypeExtdPmtCm.ExtdPmtInv);
/* 24:23 */     localTypeExtdPmtCm.ExtdPmtInvExists = paramTypeExtdPmtCm.ExtdPmtInvExists;
/* 25:24 */     return localTypeExtdPmtCm;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeExtdPmtCm read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeExtdPmtCm localTypeExtdPmtCm = new TypeExtdPmtCm();
/* 31:31 */     localTypeExtdPmtCm.ExtdPmtDsc = paramInputStream.read_string();
/* 32:32 */     localTypeExtdPmtCm.ExtdPmtInv = TypeExtdPmtInvAggregateHelper.read(paramInputStream);
/* 33:33 */     localTypeExtdPmtCm.ExtdPmtInvExists = paramInputStream.read_boolean();
/* 34:34 */     return localTypeExtdPmtCm;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeExtdPmtCm paramTypeExtdPmtCm)
/* 38:   */   {
/* 39:41 */     if (paramTypeExtdPmtCm == null) {
/* 40:43 */       paramTypeExtdPmtCm = new TypeExtdPmtCm();
/* 41:   */     }
/* 42:45 */     paramOutputStream.write_string(paramTypeExtdPmtCm.ExtdPmtDsc);
/* 43:46 */     TypeExtdPmtInvAggregateHelper.write(paramOutputStream, paramTypeExtdPmtCm.ExtdPmtInv);
/* 44:47 */     paramOutputStream.write_boolean(paramTypeExtdPmtCm.ExtdPmtInvExists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeExtdPmtCm";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("ExtdPmtDsc", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 60:65 */         new StructMember("ExtdPmtInv", TypeExtdPmtInvAggregateHelper.type(), null), 
/* 61:66 */         new StructMember("ExtdPmtInvExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeExtdPmtCm", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeExtdPmtCm paramTypeExtdPmtCm)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeExtdPmtCm);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeExtdPmtCm extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeExtdPmtCm:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPmtCmHelper
 * JD-Core Version:    0.7.0.1
 */