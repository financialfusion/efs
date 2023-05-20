/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import com.sybase.ejb.cts.StringSeqHelper;
/*  4:   */ import org.omg.CORBA.Any;
/*  5:   */ import org.omg.CORBA.BAD_OPERATION;
/*  6:   */ import org.omg.CORBA.ORB;
/*  7:   */ import org.omg.CORBA.StructMember;
/*  8:   */ import org.omg.CORBA.TCKind;
/*  9:   */ import org.omg.CORBA.TypeCode;
/* 10:   */ import org.omg.CORBA.portable.InputStream;
/* 11:   */ import org.omg.CORBA.portable.OutputStream;
/* 12:   */ 
/* 13:   */ public abstract class TypePayeeRqV1AggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypePayeeRqV1Aggregate clone(TypePayeeRqV1Aggregate paramTypePayeeRqV1Aggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypePayeeRqV1Aggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypePayeeRqV1Aggregate localTypePayeeRqV1Aggregate = new TypePayeeRqV1Aggregate();
/* 23:21 */     localTypePayeeRqV1Aggregate.PayeeRqV1Un = TypePayeeRqV1UnHelper.clone(paramTypePayeeRqV1Aggregate.PayeeRqV1Un);
/* 24:22 */     localTypePayeeRqV1Aggregate.PayAcct = StringSeqHelper.clone(paramTypePayeeRqV1Aggregate.PayAcct);
/* 25:23 */     localTypePayeeRqV1Aggregate.NameOnAcct = StringSeqHelper.clone(paramTypePayeeRqV1Aggregate.NameOnAcct);
/* 26:24 */     localTypePayeeRqV1Aggregate.NameOnAcctExists = paramTypePayeeRqV1Aggregate.NameOnAcctExists;
/* 27:25 */     return localTypePayeeRqV1Aggregate;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static TypePayeeRqV1Aggregate read(InputStream paramInputStream)
/* 31:   */   {
/* 32:31 */     TypePayeeRqV1Aggregate localTypePayeeRqV1Aggregate = new TypePayeeRqV1Aggregate();
/* 33:32 */     localTypePayeeRqV1Aggregate.PayeeRqV1Un = TypePayeeRqV1UnHelper.read(paramInputStream);
/* 34:33 */     localTypePayeeRqV1Aggregate.PayAcct = StringSeqHelper.read(paramInputStream);
/* 35:34 */     localTypePayeeRqV1Aggregate.NameOnAcct = StringSeqHelper.read(paramInputStream);
/* 36:35 */     localTypePayeeRqV1Aggregate.NameOnAcctExists = paramInputStream.read_boolean();
/* 37:36 */     return localTypePayeeRqV1Aggregate;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static void write(OutputStream paramOutputStream, TypePayeeRqV1Aggregate paramTypePayeeRqV1Aggregate)
/* 41:   */   {
/* 42:43 */     if (paramTypePayeeRqV1Aggregate == null) {
/* 43:45 */       paramTypePayeeRqV1Aggregate = new TypePayeeRqV1Aggregate();
/* 44:   */     }
/* 45:47 */     TypePayeeRqV1UnHelper.write(paramOutputStream, paramTypePayeeRqV1Aggregate.PayeeRqV1Un);
/* 46:48 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeRqV1Aggregate.PayAcct);
/* 47:49 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeRqV1Aggregate.NameOnAcct);
/* 48:50 */     paramOutputStream.write_boolean(paramTypePayeeRqV1Aggregate.NameOnAcctExists);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static String _idl()
/* 52:   */   {
/* 53:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeRqV1Aggregate";
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static TypeCode type()
/* 57:   */   {
/* 58:62 */     if (_type == null)
/* 59:   */     {
/* 60:64 */       ORB localORB = ORB.init();
/* 61:65 */       StructMember[] arrayOfStructMember = 
/* 62:66 */         {
/* 63:67 */         new StructMember("PayeeRqV1Un", TypePayeeRqV1UnHelper.type(), null), 
/* 64:68 */         new StructMember("PayAcct", StringSeqHelper.type(), null), 
/* 65:69 */         new StructMember("NameOnAcct", StringSeqHelper.type(), null), 
/* 66:70 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 67:   */       
/* 68:72 */       _type = localORB.create_struct_tc(id(), "TypePayeeRqV1Aggregate", arrayOfStructMember);
/* 69:   */     }
/* 70:74 */     return _type;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public static void insert(Any paramAny, TypePayeeRqV1Aggregate paramTypePayeeRqV1Aggregate)
/* 74:   */   {
/* 75:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 76:82 */     write(localOutputStream, paramTypePayeeRqV1Aggregate);
/* 77:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 78:   */   }
/* 79:   */   
/* 80:   */   public static TypePayeeRqV1Aggregate extract(Any paramAny)
/* 81:   */   {
/* 82:89 */     if (!paramAny.type().equal(type())) {
/* 83:91 */       throw new BAD_OPERATION();
/* 84:   */     }
/* 85:93 */     return read(paramAny.create_input_stream());
/* 86:   */   }
/* 87:   */   
/* 88:   */   public static String id()
/* 89:   */   {
/* 90:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeRqV1Aggregate:1.0";
/* 91:   */   }
/* 92:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */