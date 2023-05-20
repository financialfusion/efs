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
/* 12:   */ public abstract class TypeExtdPmtV1CmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeExtdPmtV1Cm clone(TypeExtdPmtV1Cm paramTypeExtdPmtV1Cm)
/* 17:   */   {
/* 18:16 */     if (paramTypeExtdPmtV1Cm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeExtdPmtV1Cm localTypeExtdPmtV1Cm = new TypeExtdPmtV1Cm();
/* 22:21 */     localTypeExtdPmtV1Cm.ExtdPmtDsc = paramTypeExtdPmtV1Cm.ExtdPmtDsc;
/* 23:22 */     localTypeExtdPmtV1Cm.ExtdPmtInv = TypeExtdPmtInvV1AggregateHelper.clone(paramTypeExtdPmtV1Cm.ExtdPmtInv);
/* 24:23 */     localTypeExtdPmtV1Cm.ExtdPmtInvExists = paramTypeExtdPmtV1Cm.ExtdPmtInvExists;
/* 25:24 */     return localTypeExtdPmtV1Cm;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeExtdPmtV1Cm read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeExtdPmtV1Cm localTypeExtdPmtV1Cm = new TypeExtdPmtV1Cm();
/* 31:31 */     localTypeExtdPmtV1Cm.ExtdPmtDsc = paramInputStream.read_string();
/* 32:32 */     localTypeExtdPmtV1Cm.ExtdPmtInv = TypeExtdPmtInvV1AggregateHelper.read(paramInputStream);
/* 33:33 */     localTypeExtdPmtV1Cm.ExtdPmtInvExists = paramInputStream.read_boolean();
/* 34:34 */     return localTypeExtdPmtV1Cm;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeExtdPmtV1Cm paramTypeExtdPmtV1Cm)
/* 38:   */   {
/* 39:41 */     if (paramTypeExtdPmtV1Cm == null) {
/* 40:43 */       paramTypeExtdPmtV1Cm = new TypeExtdPmtV1Cm();
/* 41:   */     }
/* 42:45 */     paramOutputStream.write_string(paramTypeExtdPmtV1Cm.ExtdPmtDsc);
/* 43:46 */     TypeExtdPmtInvV1AggregateHelper.write(paramOutputStream, paramTypeExtdPmtV1Cm.ExtdPmtInv);
/* 44:47 */     paramOutputStream.write_boolean(paramTypeExtdPmtV1Cm.ExtdPmtInvExists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeExtdPmtV1Cm";
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
/* 60:65 */         new StructMember("ExtdPmtInv", TypeExtdPmtInvV1AggregateHelper.type(), null), 
/* 61:66 */         new StructMember("ExtdPmtInvExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeExtdPmtV1Cm", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeExtdPmtV1Cm paramTypeExtdPmtV1Cm)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeExtdPmtV1Cm);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeExtdPmtV1Cm extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeExtdPmtV1Cm:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPmtV1CmHelper
 * JD-Core Version:    0.7.0.1
 */