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
/* 12:   */ public abstract class TypeExtdPayeeAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeExtdPayeeAggregate clone(TypeExtdPayeeAggregate paramTypeExtdPayeeAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeExtdPayeeAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeExtdPayeeAggregate localTypeExtdPayeeAggregate = new TypeExtdPayeeAggregate();
/* 22:21 */     localTypeExtdPayeeAggregate.ExtdPayeeCm = TypeExtdPayeeCmHelper.clone(paramTypeExtdPayeeAggregate.ExtdPayeeCm);
/* 23:22 */     localTypeExtdPayeeAggregate.ExtdPayeeCmExists = paramTypeExtdPayeeAggregate.ExtdPayeeCmExists;
/* 24:23 */     localTypeExtdPayeeAggregate.DaysToPay = paramTypeExtdPayeeAggregate.DaysToPay;
/* 25:24 */     return localTypeExtdPayeeAggregate;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeExtdPayeeAggregate read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeExtdPayeeAggregate localTypeExtdPayeeAggregate = new TypeExtdPayeeAggregate();
/* 31:31 */     localTypeExtdPayeeAggregate.ExtdPayeeCm = TypeExtdPayeeCmHelper.read(paramInputStream);
/* 32:32 */     localTypeExtdPayeeAggregate.ExtdPayeeCmExists = paramInputStream.read_boolean();
/* 33:33 */     localTypeExtdPayeeAggregate.DaysToPay = paramInputStream.read_long();
/* 34:34 */     return localTypeExtdPayeeAggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeExtdPayeeAggregate paramTypeExtdPayeeAggregate)
/* 38:   */   {
/* 39:41 */     if (paramTypeExtdPayeeAggregate == null) {
/* 40:43 */       paramTypeExtdPayeeAggregate = new TypeExtdPayeeAggregate();
/* 41:   */     }
/* 42:45 */     TypeExtdPayeeCmHelper.write(paramOutputStream, paramTypeExtdPayeeAggregate.ExtdPayeeCm);
/* 43:46 */     paramOutputStream.write_boolean(paramTypeExtdPayeeAggregate.ExtdPayeeCmExists);
/* 44:47 */     paramOutputStream.write_long(paramTypeExtdPayeeAggregate.DaysToPay);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeExtdPayeeAggregate";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("ExtdPayeeCm", TypeExtdPayeeCmHelper.type(), null), 
/* 60:65 */         new StructMember("ExtdPayeeCmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 61:66 */         new StructMember("DaysToPay", localORB.get_primitive_tc(TCKind.tk_long), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeExtdPayeeAggregate", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeExtdPayeeAggregate paramTypeExtdPayeeAggregate)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeExtdPayeeAggregate);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeExtdPayeeAggregate extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeExtdPayeeAggregate:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeAggregateHelper
 * JD-Core Version:    0.7.0.1
 */