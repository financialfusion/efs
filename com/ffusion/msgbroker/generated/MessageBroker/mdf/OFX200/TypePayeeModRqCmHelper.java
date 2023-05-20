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
/* 12:   */ public abstract class TypePayeeModRqCmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePayeeModRqCm clone(TypePayeeModRqCm paramTypePayeeModRqCm)
/* 17:   */   {
/* 18:16 */     if (paramTypePayeeModRqCm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePayeeModRqCm localTypePayeeModRqCm = new TypePayeeModRqCm();
/* 22:21 */     localTypePayeeModRqCm.Payee = TypePayeeAggregateHelper.clone(paramTypePayeeModRqCm.Payee);
/* 23:22 */     localTypePayeeModRqCm.BankAcctTo = TypeBankAcctToAggregateHelper.clone(paramTypePayeeModRqCm.BankAcctTo);
/* 24:23 */     localTypePayeeModRqCm.BankAcctToExists = paramTypePayeeModRqCm.BankAcctToExists;
/* 25:24 */     return localTypePayeeModRqCm;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypePayeeModRqCm read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypePayeeModRqCm localTypePayeeModRqCm = new TypePayeeModRqCm();
/* 31:31 */     localTypePayeeModRqCm.Payee = TypePayeeAggregateHelper.read(paramInputStream);
/* 32:32 */     localTypePayeeModRqCm.BankAcctTo = TypeBankAcctToAggregateHelper.read(paramInputStream);
/* 33:33 */     localTypePayeeModRqCm.BankAcctToExists = paramInputStream.read_boolean();
/* 34:34 */     return localTypePayeeModRqCm;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypePayeeModRqCm paramTypePayeeModRqCm)
/* 38:   */   {
/* 39:41 */     if (paramTypePayeeModRqCm == null) {
/* 40:43 */       paramTypePayeeModRqCm = new TypePayeeModRqCm();
/* 41:   */     }
/* 42:45 */     TypePayeeAggregateHelper.write(paramOutputStream, paramTypePayeeModRqCm.Payee);
/* 43:46 */     TypeBankAcctToAggregateHelper.write(paramOutputStream, paramTypePayeeModRqCm.BankAcctTo);
/* 44:47 */     paramOutputStream.write_boolean(paramTypePayeeModRqCm.BankAcctToExists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeModRqCm";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("Payee", TypePayeeAggregateHelper.type(), null), 
/* 60:65 */         new StructMember("BankAcctTo", TypeBankAcctToAggregateHelper.type(), null), 
/* 61:66 */         new StructMember("BankAcctToExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypePayeeModRqCm", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypePayeeModRqCm paramTypePayeeModRqCm)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypePayeeModRqCm);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypePayeeModRqCm extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeModRqCm:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRqCmHelper
 * JD-Core Version:    0.7.0.1
 */