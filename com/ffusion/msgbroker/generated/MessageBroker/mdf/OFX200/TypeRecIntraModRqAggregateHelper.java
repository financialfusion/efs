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
/* 12:   */ public abstract class TypeRecIntraModRqAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeRecIntraModRqAggregate clone(TypeRecIntraModRqAggregate paramTypeRecIntraModRqAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeRecIntraModRqAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeRecIntraModRqAggregate localTypeRecIntraModRqAggregate = new TypeRecIntraModRqAggregate();
/* 22:21 */     localTypeRecIntraModRqAggregate.RecSrvrTID = paramTypeRecIntraModRqAggregate.RecSrvrTID;
/* 23:22 */     localTypeRecIntraModRqAggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecIntraModRqAggregate.RecurrInst);
/* 24:23 */     localTypeRecIntraModRqAggregate.IntraRq = TypeIntraRqAggregateHelper.clone(paramTypeRecIntraModRqAggregate.IntraRq);
/* 25:24 */     localTypeRecIntraModRqAggregate.ModPending = paramTypeRecIntraModRqAggregate.ModPending;
/* 26:25 */     return localTypeRecIntraModRqAggregate;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeRecIntraModRqAggregate read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypeRecIntraModRqAggregate localTypeRecIntraModRqAggregate = new TypeRecIntraModRqAggregate();
/* 32:32 */     localTypeRecIntraModRqAggregate.RecSrvrTID = paramInputStream.read_string();
/* 33:33 */     localTypeRecIntraModRqAggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/* 34:34 */     localTypeRecIntraModRqAggregate.IntraRq = TypeIntraRqAggregateHelper.read(paramInputStream);
/* 35:35 */     localTypeRecIntraModRqAggregate.ModPending = paramInputStream.read_boolean();
/* 36:36 */     return localTypeRecIntraModRqAggregate;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraModRqAggregate paramTypeRecIntraModRqAggregate)
/* 40:   */   {
/* 41:43 */     if (paramTypeRecIntraModRqAggregate == null) {
/* 42:45 */       paramTypeRecIntraModRqAggregate = new TypeRecIntraModRqAggregate();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypeRecIntraModRqAggregate.RecSrvrTID);
/* 45:48 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecIntraModRqAggregate.RecurrInst);
/* 46:49 */     TypeIntraRqAggregateHelper.write(paramOutputStream, paramTypeRecIntraModRqAggregate.IntraRq);
/* 47:50 */     paramOutputStream.write_boolean(paramTypeRecIntraModRqAggregate.ModPending);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecIntraModRqAggregate";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static TypeCode type()
/* 56:   */   {
/* 57:62 */     if (_type == null)
/* 58:   */     {
/* 59:64 */       ORB localORB = ORB.init();
/* 60:65 */       StructMember[] arrayOfStructMember = 
/* 61:66 */         {
/* 62:67 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 63:68 */         new StructMember("RecurrInst", TypeRecurrInstAggregateHelper.type(), null), 
/* 64:69 */         new StructMember("IntraRq", TypeIntraRqAggregateHelper.type(), null), 
/* 65:70 */         new StructMember("ModPending", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraModRqAggregate", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypeRecIntraModRqAggregate paramTypeRecIntraModRqAggregate)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypeRecIntraModRqAggregate);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypeRecIntraModRqAggregate extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecIntraModRqAggregate:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRqAggregateHelper
 * JD-Core Version:    0.7.0.1
 */