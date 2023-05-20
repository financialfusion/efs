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
/* 12:   */ public abstract class TypeRecIntraCanRsAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeRecIntraCanRsAggregate clone(TypeRecIntraCanRsAggregate paramTypeRecIntraCanRsAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeRecIntraCanRsAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeRecIntraCanRsAggregate localTypeRecIntraCanRsAggregate = new TypeRecIntraCanRsAggregate();
/* 22:21 */     localTypeRecIntraCanRsAggregate.RecSrvrTID = paramTypeRecIntraCanRsAggregate.RecSrvrTID;
/* 23:22 */     localTypeRecIntraCanRsAggregate.CanPending = paramTypeRecIntraCanRsAggregate.CanPending;
/* 24:23 */     return localTypeRecIntraCanRsAggregate;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypeRecIntraCanRsAggregate read(InputStream paramInputStream)
/* 28:   */   {
/* 29:29 */     TypeRecIntraCanRsAggregate localTypeRecIntraCanRsAggregate = new TypeRecIntraCanRsAggregate();
/* 30:30 */     localTypeRecIntraCanRsAggregate.RecSrvrTID = paramInputStream.read_string();
/* 31:31 */     localTypeRecIntraCanRsAggregate.CanPending = paramInputStream.read_boolean();
/* 32:32 */     return localTypeRecIntraCanRsAggregate;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraCanRsAggregate paramTypeRecIntraCanRsAggregate)
/* 36:   */   {
/* 37:39 */     if (paramTypeRecIntraCanRsAggregate == null) {
/* 38:41 */       paramTypeRecIntraCanRsAggregate = new TypeRecIntraCanRsAggregate();
/* 39:   */     }
/* 40:43 */     paramOutputStream.write_string(paramTypeRecIntraCanRsAggregate.RecSrvrTID);
/* 41:44 */     paramOutputStream.write_boolean(paramTypeRecIntraCanRsAggregate.CanPending);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static String _idl()
/* 45:   */   {
/* 46:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecIntraCanRsAggregate";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:56 */     if (_type == null)
/* 52:   */     {
/* 53:58 */       ORB localORB = ORB.init();
/* 54:59 */       StructMember[] arrayOfStructMember = 
/* 55:60 */         {
/* 56:61 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 57:62 */         new StructMember("CanPending", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 58:   */       
/* 59:64 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraCanRsAggregate", arrayOfStructMember);
/* 60:   */     }
/* 61:66 */     return _type;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static void insert(Any paramAny, TypeRecIntraCanRsAggregate paramTypeRecIntraCanRsAggregate)
/* 65:   */   {
/* 66:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 67:74 */     write(localOutputStream, paramTypeRecIntraCanRsAggregate);
/* 68:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static TypeRecIntraCanRsAggregate extract(Any paramAny)
/* 72:   */   {
/* 73:81 */     if (!paramAny.type().equal(type())) {
/* 74:83 */       throw new BAD_OPERATION();
/* 75:   */     }
/* 76:85 */     return read(paramAny.create_input_stream());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static String id()
/* 80:   */   {
/* 81:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecIntraCanRsAggregate:1.0";
/* 82:   */   }
/* 83:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraCanRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */