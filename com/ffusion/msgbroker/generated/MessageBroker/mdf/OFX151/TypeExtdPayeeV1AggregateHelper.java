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
/* 12:   */ public abstract class TypeExtdPayeeV1AggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeExtdPayeeV1Aggregate clone(TypeExtdPayeeV1Aggregate paramTypeExtdPayeeV1Aggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeExtdPayeeV1Aggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeExtdPayeeV1Aggregate localTypeExtdPayeeV1Aggregate = new TypeExtdPayeeV1Aggregate();
/* 22:21 */     localTypeExtdPayeeV1Aggregate.ExtdPayeeV1Cm = TypeExtdPayeeV1CmHelper.clone(paramTypeExtdPayeeV1Aggregate.ExtdPayeeV1Cm);
/* 23:22 */     localTypeExtdPayeeV1Aggregate.ExtdPayeeV1CmExists = paramTypeExtdPayeeV1Aggregate.ExtdPayeeV1CmExists;
/* 24:23 */     localTypeExtdPayeeV1Aggregate.DaysToPay = paramTypeExtdPayeeV1Aggregate.DaysToPay;
/* 25:24 */     return localTypeExtdPayeeV1Aggregate;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeExtdPayeeV1Aggregate read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeExtdPayeeV1Aggregate localTypeExtdPayeeV1Aggregate = new TypeExtdPayeeV1Aggregate();
/* 31:31 */     localTypeExtdPayeeV1Aggregate.ExtdPayeeV1Cm = TypeExtdPayeeV1CmHelper.read(paramInputStream);
/* 32:32 */     localTypeExtdPayeeV1Aggregate.ExtdPayeeV1CmExists = paramInputStream.read_boolean();
/* 33:33 */     localTypeExtdPayeeV1Aggregate.DaysToPay = paramInputStream.read_long();
/* 34:34 */     return localTypeExtdPayeeV1Aggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeExtdPayeeV1Aggregate paramTypeExtdPayeeV1Aggregate)
/* 38:   */   {
/* 39:41 */     if (paramTypeExtdPayeeV1Aggregate == null) {
/* 40:43 */       paramTypeExtdPayeeV1Aggregate = new TypeExtdPayeeV1Aggregate();
/* 41:   */     }
/* 42:45 */     TypeExtdPayeeV1CmHelper.write(paramOutputStream, paramTypeExtdPayeeV1Aggregate.ExtdPayeeV1Cm);
/* 43:46 */     paramOutputStream.write_boolean(paramTypeExtdPayeeV1Aggregate.ExtdPayeeV1CmExists);
/* 44:47 */     paramOutputStream.write_long(paramTypeExtdPayeeV1Aggregate.DaysToPay);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeExtdPayeeV1Aggregate";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("ExtdPayeeV1Cm", TypeExtdPayeeV1CmHelper.type(), null), 
/* 60:65 */         new StructMember("ExtdPayeeV1CmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 61:66 */         new StructMember("DaysToPay", localORB.get_primitive_tc(TCKind.tk_long), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeExtdPayeeV1Aggregate", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeExtdPayeeV1Aggregate paramTypeExtdPayeeV1Aggregate)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeExtdPayeeV1Aggregate);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeExtdPayeeV1Aggregate extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeExtdPayeeV1Aggregate:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */