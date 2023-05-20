/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import com.sybase.CORBA.ObjectVal;
/*  4:   */ import org.omg.CORBA.Any;
/*  5:   */ import org.omg.CORBA.BAD_OPERATION;
/*  6:   */ import org.omg.CORBA.ORB;
/*  7:   */ import org.omg.CORBA.StructMember;
/*  8:   */ import org.omg.CORBA.TCKind;
/*  9:   */ import org.omg.CORBA.TypeCode;
/* 10:   */ import org.omg.CORBA.portable.InputStream;
/* 11:   */ import org.omg.CORBA.portable.OutputStream;
/* 12:   */ 
/* 13:   */ public abstract class TypeStatusV1AggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypeStatusV1Aggregate clone(TypeStatusV1Aggregate paramTypeStatusV1Aggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypeStatusV1Aggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypeStatusV1Aggregate localTypeStatusV1Aggregate = new TypeStatusV1Aggregate();
/* 23:21 */     localTypeStatusV1Aggregate.Code = paramTypeStatusV1Aggregate.Code;
/* 24:22 */     localTypeStatusV1Aggregate.Severity = ((EnumSeverityEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeStatusV1Aggregate.Severity)));
/* 25:23 */     localTypeStatusV1Aggregate.Message = paramTypeStatusV1Aggregate.Message;
/* 26:24 */     localTypeStatusV1Aggregate.MessageExists = paramTypeStatusV1Aggregate.MessageExists;
/* 27:25 */     return localTypeStatusV1Aggregate;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static TypeStatusV1Aggregate read(InputStream paramInputStream)
/* 31:   */   {
/* 32:31 */     TypeStatusV1Aggregate localTypeStatusV1Aggregate = new TypeStatusV1Aggregate();
/* 33:32 */     localTypeStatusV1Aggregate.Code = paramInputStream.read_long();
/* 34:33 */     localTypeStatusV1Aggregate.Severity = EnumSeverityEnumHelper.read(paramInputStream);
/* 35:34 */     localTypeStatusV1Aggregate.Message = paramInputStream.read_string();
/* 36:35 */     localTypeStatusV1Aggregate.MessageExists = paramInputStream.read_boolean();
/* 37:36 */     return localTypeStatusV1Aggregate;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static void write(OutputStream paramOutputStream, TypeStatusV1Aggregate paramTypeStatusV1Aggregate)
/* 41:   */   {
/* 42:43 */     if (paramTypeStatusV1Aggregate == null) {
/* 43:45 */       paramTypeStatusV1Aggregate = new TypeStatusV1Aggregate();
/* 44:   */     }
/* 45:47 */     paramOutputStream.write_long(paramTypeStatusV1Aggregate.Code);
/* 46:48 */     EnumSeverityEnumHelper.write(paramOutputStream, paramTypeStatusV1Aggregate.Severity);
/* 47:49 */     paramOutputStream.write_string(paramTypeStatusV1Aggregate.Message);
/* 48:50 */     paramOutputStream.write_boolean(paramTypeStatusV1Aggregate.MessageExists);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static String _idl()
/* 52:   */   {
/* 53:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeStatusV1Aggregate";
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static TypeCode type()
/* 57:   */   {
/* 58:62 */     if (_type == null)
/* 59:   */     {
/* 60:64 */       ORB localORB = ORB.init();
/* 61:65 */       StructMember[] arrayOfStructMember = 
/* 62:66 */         {
/* 63:67 */         new StructMember("Code", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 64:68 */         new StructMember("Severity", EnumSeverityEnumHelper.type(), null), 
/* 65:69 */         new StructMember("Message", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 66:70 */         new StructMember("MessageExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 67:   */       
/* 68:72 */       _type = localORB.create_struct_tc(id(), "TypeStatusV1Aggregate", arrayOfStructMember);
/* 69:   */     }
/* 70:74 */     return _type;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public static void insert(Any paramAny, TypeStatusV1Aggregate paramTypeStatusV1Aggregate)
/* 74:   */   {
/* 75:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 76:82 */     write(localOutputStream, paramTypeStatusV1Aggregate);
/* 77:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 78:   */   }
/* 79:   */   
/* 80:   */   public static TypeStatusV1Aggregate extract(Any paramAny)
/* 81:   */   {
/* 82:89 */     if (!paramAny.type().equal(type())) {
/* 83:91 */       throw new BAD_OPERATION();
/* 84:   */     }
/* 85:93 */     return read(paramAny.create_input_stream());
/* 86:   */   }
/* 87:   */   
/* 88:   */   public static String id()
/* 89:   */   {
/* 90:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeStatusV1Aggregate:1.0";
/* 91:   */   }
/* 92:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStatusV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */