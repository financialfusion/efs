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
/* 12:   */ public abstract class TypeCCAcctFromAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeCCAcctFromAggregate clone(TypeCCAcctFromAggregate paramTypeCCAcctFromAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeCCAcctFromAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeCCAcctFromAggregate localTypeCCAcctFromAggregate = new TypeCCAcctFromAggregate();
/* 22:21 */     localTypeCCAcctFromAggregate.AcctID = paramTypeCCAcctFromAggregate.AcctID;
/* 23:22 */     localTypeCCAcctFromAggregate.AcctKey = paramTypeCCAcctFromAggregate.AcctKey;
/* 24:23 */     localTypeCCAcctFromAggregate.AcctKeyExists = paramTypeCCAcctFromAggregate.AcctKeyExists;
/* 25:24 */     return localTypeCCAcctFromAggregate;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeCCAcctFromAggregate read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeCCAcctFromAggregate localTypeCCAcctFromAggregate = new TypeCCAcctFromAggregate();
/* 31:31 */     localTypeCCAcctFromAggregate.AcctID = paramInputStream.read_string();
/* 32:32 */     localTypeCCAcctFromAggregate.AcctKey = paramInputStream.read_string();
/* 33:33 */     localTypeCCAcctFromAggregate.AcctKeyExists = paramInputStream.read_boolean();
/* 34:34 */     return localTypeCCAcctFromAggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeCCAcctFromAggregate paramTypeCCAcctFromAggregate)
/* 38:   */   {
/* 39:41 */     if (paramTypeCCAcctFromAggregate == null) {
/* 40:43 */       paramTypeCCAcctFromAggregate = new TypeCCAcctFromAggregate();
/* 41:   */     }
/* 42:45 */     paramOutputStream.write_string(paramTypeCCAcctFromAggregate.AcctID);
/* 43:46 */     paramOutputStream.write_string(paramTypeCCAcctFromAggregate.AcctKey);
/* 44:47 */     paramOutputStream.write_boolean(paramTypeCCAcctFromAggregate.AcctKeyExists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeCCAcctFromAggregate";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("AcctID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 60:65 */         new StructMember("AcctKey", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 61:66 */         new StructMember("AcctKeyExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeCCAcctFromAggregate", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeCCAcctFromAggregate paramTypeCCAcctFromAggregate)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeCCAcctFromAggregate);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeCCAcctFromAggregate extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeCCAcctFromAggregate:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctFromAggregateHelper
 * JD-Core Version:    0.7.0.1
 */