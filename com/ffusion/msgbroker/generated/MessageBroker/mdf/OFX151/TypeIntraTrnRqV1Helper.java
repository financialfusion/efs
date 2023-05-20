/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.StructMember;
/*  7:   */ import org.omg.CORBA.TypeCode;
/*  8:   */ import org.omg.CORBA.portable.InputStream;
/*  9:   */ import org.omg.CORBA.portable.OutputStream;
/* 10:   */ 
/* 11:   */ public abstract class TypeIntraTrnRqV1Helper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static TypeIntraTrnRqV1 clone(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1)
/* 16:   */   {
/* 17:16 */     if (paramTypeIntraTrnRqV1 == null) {
/* 18:18 */       return null;
/* 19:   */     }
/* 20:20 */     TypeIntraTrnRqV1 localTypeIntraTrnRqV1 = new TypeIntraTrnRqV1();
/* 21:21 */     localTypeIntraTrnRqV1.IntraTrnRq = TypeIntraTrnRqV1AggregateHelper.clone(paramTypeIntraTrnRqV1.IntraTrnRq);
/* 22:22 */     return localTypeIntraTrnRqV1;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static TypeIntraTrnRqV1 read(InputStream paramInputStream)
/* 26:   */   {
/* 27:28 */     TypeIntraTrnRqV1 localTypeIntraTrnRqV1 = new TypeIntraTrnRqV1();
/* 28:29 */     localTypeIntraTrnRqV1.IntraTrnRq = TypeIntraTrnRqV1AggregateHelper.read(paramInputStream);
/* 29:30 */     return localTypeIntraTrnRqV1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void write(OutputStream paramOutputStream, TypeIntraTrnRqV1 paramTypeIntraTrnRqV1)
/* 33:   */   {
/* 34:37 */     if (paramTypeIntraTrnRqV1 == null) {
/* 35:39 */       paramTypeIntraTrnRqV1 = new TypeIntraTrnRqV1();
/* 36:   */     }
/* 37:41 */     TypeIntraTrnRqV1AggregateHelper.write(paramOutputStream, paramTypeIntraTrnRqV1.IntraTrnRq);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static String _idl()
/* 41:   */   {
/* 42:46 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeIntraTrnRqV1";
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static TypeCode type()
/* 46:   */   {
/* 47:53 */     if (_type == null)
/* 48:   */     {
/* 49:55 */       ORB localORB = ORB.init();
/* 50:56 */       StructMember[] arrayOfStructMember = 
/* 51:57 */         {
/* 52:58 */         new StructMember("IntraTrnRq", TypeIntraTrnRqV1AggregateHelper.type(), null) };
/* 53:   */       
/* 54:60 */       _type = localORB.create_struct_tc(id(), "TypeIntraTrnRqV1", arrayOfStructMember);
/* 55:   */     }
/* 56:62 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, TypeIntraTrnRqV1 paramTypeIntraTrnRqV1)
/* 60:   */   {
/* 61:69 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:70 */     write(localOutputStream, paramTypeIntraTrnRqV1);
/* 63:71 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static TypeIntraTrnRqV1 extract(Any paramAny)
/* 67:   */   {
/* 68:77 */     if (!paramAny.type().equal(type())) {
/* 69:79 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:81 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:86 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraTrnRqV1:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Helper
 * JD-Core Version:    0.7.0.1
 */