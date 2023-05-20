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
/* 12:   */ public abstract class TypePayeeRqV1CmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePayeeRqV1Cm clone(TypePayeeRqV1Cm paramTypePayeeRqV1Cm)
/* 17:   */   {
/* 18:16 */     if (paramTypePayeeRqV1Cm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePayeeRqV1Cm localTypePayeeRqV1Cm = new TypePayeeRqV1Cm();
/* 22:21 */     localTypePayeeRqV1Cm.Payee = TypePayeeV1AggregateHelper.clone(paramTypePayeeRqV1Cm.Payee);
/* 23:22 */     localTypePayeeRqV1Cm.BankAcctTo = TypeBankAcctToV1AggregateHelper.clone(paramTypePayeeRqV1Cm.BankAcctTo);
/* 24:23 */     localTypePayeeRqV1Cm.BankAcctToExists = paramTypePayeeRqV1Cm.BankAcctToExists;
/* 25:24 */     return localTypePayeeRqV1Cm;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypePayeeRqV1Cm read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypePayeeRqV1Cm localTypePayeeRqV1Cm = new TypePayeeRqV1Cm();
/* 31:31 */     localTypePayeeRqV1Cm.Payee = TypePayeeV1AggregateHelper.read(paramInputStream);
/* 32:32 */     localTypePayeeRqV1Cm.BankAcctTo = TypeBankAcctToV1AggregateHelper.read(paramInputStream);
/* 33:33 */     localTypePayeeRqV1Cm.BankAcctToExists = paramInputStream.read_boolean();
/* 34:34 */     return localTypePayeeRqV1Cm;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypePayeeRqV1Cm paramTypePayeeRqV1Cm)
/* 38:   */   {
/* 39:41 */     if (paramTypePayeeRqV1Cm == null) {
/* 40:43 */       paramTypePayeeRqV1Cm = new TypePayeeRqV1Cm();
/* 41:   */     }
/* 42:45 */     TypePayeeV1AggregateHelper.write(paramOutputStream, paramTypePayeeRqV1Cm.Payee);
/* 43:46 */     TypeBankAcctToV1AggregateHelper.write(paramOutputStream, paramTypePayeeRqV1Cm.BankAcctTo);
/* 44:47 */     paramOutputStream.write_boolean(paramTypePayeeRqV1Cm.BankAcctToExists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeRqV1Cm";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("Payee", TypePayeeV1AggregateHelper.type(), null), 
/* 60:65 */         new StructMember("BankAcctTo", TypeBankAcctToV1AggregateHelper.type(), null), 
/* 61:66 */         new StructMember("BankAcctToExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypePayeeRqV1Cm", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypePayeeRqV1Cm paramTypePayeeRqV1Cm)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypePayeeRqV1Cm);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypePayeeRqV1Cm extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeRqV1Cm:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1CmHelper
 * JD-Core Version:    0.7.0.1
 */