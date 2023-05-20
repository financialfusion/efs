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
/* 12:   */ public abstract class TypePayeeDelRqV1AggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePayeeDelRqV1Aggregate clone(TypePayeeDelRqV1Aggregate paramTypePayeeDelRqV1Aggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypePayeeDelRqV1Aggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePayeeDelRqV1Aggregate localTypePayeeDelRqV1Aggregate = new TypePayeeDelRqV1Aggregate();
/* 22:21 */     localTypePayeeDelRqV1Aggregate.PayeeLstID = paramTypePayeeDelRqV1Aggregate.PayeeLstID;
/* 23:22 */     return localTypePayeeDelRqV1Aggregate;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static TypePayeeDelRqV1Aggregate read(InputStream paramInputStream)
/* 27:   */   {
/* 28:28 */     TypePayeeDelRqV1Aggregate localTypePayeeDelRqV1Aggregate = new TypePayeeDelRqV1Aggregate();
/* 29:29 */     localTypePayeeDelRqV1Aggregate.PayeeLstID = paramInputStream.read_string();
/* 30:30 */     return localTypePayeeDelRqV1Aggregate;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static void write(OutputStream paramOutputStream, TypePayeeDelRqV1Aggregate paramTypePayeeDelRqV1Aggregate)
/* 34:   */   {
/* 35:37 */     if (paramTypePayeeDelRqV1Aggregate == null) {
/* 36:39 */       paramTypePayeeDelRqV1Aggregate = new TypePayeeDelRqV1Aggregate();
/* 37:   */     }
/* 38:41 */     paramOutputStream.write_string(paramTypePayeeDelRqV1Aggregate.PayeeLstID);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static String _idl()
/* 42:   */   {
/* 43:46 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeDelRqV1Aggregate";
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static TypeCode type()
/* 47:   */   {
/* 48:53 */     if (_type == null)
/* 49:   */     {
/* 50:55 */       ORB localORB = ORB.init();
/* 51:56 */       StructMember[] arrayOfStructMember = 
/* 52:57 */         {
/* 53:58 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 54:   */       
/* 55:60 */       _type = localORB.create_struct_tc(id(), "TypePayeeDelRqV1Aggregate", arrayOfStructMember);
/* 56:   */     }
/* 57:62 */     return _type;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static void insert(Any paramAny, TypePayeeDelRqV1Aggregate paramTypePayeeDelRqV1Aggregate)
/* 61:   */   {
/* 62:69 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 63:70 */     write(localOutputStream, paramTypePayeeDelRqV1Aggregate);
/* 64:71 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static TypePayeeDelRqV1Aggregate extract(Any paramAny)
/* 68:   */   {
/* 69:77 */     if (!paramAny.type().equal(type())) {
/* 70:79 */       throw new BAD_OPERATION();
/* 71:   */     }
/* 72:81 */     return read(paramAny.create_input_stream());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static String id()
/* 76:   */   {
/* 77:86 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeDelRqV1Aggregate:1.0";
/* 78:   */   }
/* 79:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeDelRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */