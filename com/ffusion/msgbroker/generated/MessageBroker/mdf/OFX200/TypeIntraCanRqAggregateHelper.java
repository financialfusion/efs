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
/* 12:   */ public abstract class TypeIntraCanRqAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeIntraCanRqAggregate clone(TypeIntraCanRqAggregate paramTypeIntraCanRqAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeIntraCanRqAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeIntraCanRqAggregate localTypeIntraCanRqAggregate = new TypeIntraCanRqAggregate();
/* 22:21 */     localTypeIntraCanRqAggregate.SrvrTID = paramTypeIntraCanRqAggregate.SrvrTID;
/* 23:22 */     return localTypeIntraCanRqAggregate;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static TypeIntraCanRqAggregate read(InputStream paramInputStream)
/* 27:   */   {
/* 28:28 */     TypeIntraCanRqAggregate localTypeIntraCanRqAggregate = new TypeIntraCanRqAggregate();
/* 29:29 */     localTypeIntraCanRqAggregate.SrvrTID = paramInputStream.read_string();
/* 30:30 */     return localTypeIntraCanRqAggregate;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static void write(OutputStream paramOutputStream, TypeIntraCanRqAggregate paramTypeIntraCanRqAggregate)
/* 34:   */   {
/* 35:37 */     if (paramTypeIntraCanRqAggregate == null) {
/* 36:39 */       paramTypeIntraCanRqAggregate = new TypeIntraCanRqAggregate();
/* 37:   */     }
/* 38:41 */     paramOutputStream.write_string(paramTypeIntraCanRqAggregate.SrvrTID);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static String _idl()
/* 42:   */   {
/* 43:46 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeIntraCanRqAggregate";
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static TypeCode type()
/* 47:   */   {
/* 48:53 */     if (_type == null)
/* 49:   */     {
/* 50:55 */       ORB localORB = ORB.init();
/* 51:56 */       StructMember[] arrayOfStructMember = 
/* 52:57 */         {
/* 53:58 */         new StructMember("SrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 54:   */       
/* 55:60 */       _type = localORB.create_struct_tc(id(), "TypeIntraCanRqAggregate", arrayOfStructMember);
/* 56:   */     }
/* 57:62 */     return _type;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static void insert(Any paramAny, TypeIntraCanRqAggregate paramTypeIntraCanRqAggregate)
/* 61:   */   {
/* 62:69 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 63:70 */     write(localOutputStream, paramTypeIntraCanRqAggregate);
/* 64:71 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static TypeIntraCanRqAggregate extract(Any paramAny)
/* 68:   */   {
/* 69:77 */     if (!paramAny.type().equal(type())) {
/* 70:79 */       throw new BAD_OPERATION();
/* 71:   */     }
/* 72:81 */     return read(paramAny.create_input_stream());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static String id()
/* 76:   */   {
/* 77:86 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeIntraCanRqAggregate:1.0";
/* 78:   */   }
/* 79:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraCanRqAggregateHelper
 * JD-Core Version:    0.7.0.1
 */