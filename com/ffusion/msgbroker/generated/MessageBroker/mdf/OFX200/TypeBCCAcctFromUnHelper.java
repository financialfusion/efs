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
/* 12:   */ public abstract class TypeBCCAcctFromUnHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeBCCAcctFromUn clone(TypeBCCAcctFromUn paramTypeBCCAcctFromUn)
/* 17:   */   {
/* 18:16 */     if (paramTypeBCCAcctFromUn == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeBCCAcctFromUn localTypeBCCAcctFromUn = new TypeBCCAcctFromUn();
/* 22:21 */     localTypeBCCAcctFromUn.__preValueTag = paramTypeBCCAcctFromUn.__preValueTag;
/* 23:22 */     localTypeBCCAcctFromUn.__memberName = paramTypeBCCAcctFromUn.__memberName;
/* 24:23 */     localTypeBCCAcctFromUn.BankAcctFrom = TypeBankAcctFromAggregateHelper.clone(paramTypeBCCAcctFromUn.BankAcctFrom);
/* 25:24 */     localTypeBCCAcctFromUn.CCAcctFrom = TypeCCAcctFromAggregateHelper.clone(paramTypeBCCAcctFromUn.CCAcctFrom);
/* 26:25 */     return localTypeBCCAcctFromUn;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeBCCAcctFromUn read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypeBCCAcctFromUn localTypeBCCAcctFromUn = new TypeBCCAcctFromUn();
/* 32:32 */     localTypeBCCAcctFromUn.__preValueTag = paramInputStream.read_string();
/* 33:33 */     localTypeBCCAcctFromUn.__memberName = paramInputStream.read_string();
/* 34:34 */     localTypeBCCAcctFromUn.BankAcctFrom = TypeBankAcctFromAggregateHelper.read(paramInputStream);
/* 35:35 */     localTypeBCCAcctFromUn.CCAcctFrom = TypeCCAcctFromAggregateHelper.read(paramInputStream);
/* 36:36 */     return localTypeBCCAcctFromUn;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypeBCCAcctFromUn paramTypeBCCAcctFromUn)
/* 40:   */   {
/* 41:43 */     if (paramTypeBCCAcctFromUn == null) {
/* 42:45 */       paramTypeBCCAcctFromUn = new TypeBCCAcctFromUn();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypeBCCAcctFromUn.__preValueTag);
/* 45:48 */     paramOutputStream.write_string(paramTypeBCCAcctFromUn.__memberName);
/* 46:49 */     TypeBankAcctFromAggregateHelper.write(paramOutputStream, paramTypeBCCAcctFromUn.BankAcctFrom);
/* 47:50 */     TypeCCAcctFromAggregateHelper.write(paramOutputStream, paramTypeBCCAcctFromUn.CCAcctFrom);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeBCCAcctFromUn";
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
/* 64:69 */         new StructMember("BankAcctFrom", TypeBankAcctFromAggregateHelper.type(), null), 
/* 65:70 */         new StructMember("CCAcctFrom", TypeCCAcctFromAggregateHelper.type(), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypeBCCAcctFromUn", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypeBCCAcctFromUn paramTypeBCCAcctFromUn)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypeBCCAcctFromUn);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypeBCCAcctFromUn extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeBCCAcctFromUn:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUnHelper
 * JD-Core Version:    0.7.0.1
 */