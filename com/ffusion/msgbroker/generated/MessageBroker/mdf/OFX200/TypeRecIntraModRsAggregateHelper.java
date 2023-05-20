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
/* 12:   */ public abstract class TypeRecIntraModRsAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeRecIntraModRsAggregate clone(TypeRecIntraModRsAggregate paramTypeRecIntraModRsAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeRecIntraModRsAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeRecIntraModRsAggregate localTypeRecIntraModRsAggregate = new TypeRecIntraModRsAggregate();
/* 22:21 */     localTypeRecIntraModRsAggregate.RecSrvrTID = paramTypeRecIntraModRsAggregate.RecSrvrTID;
/* 23:22 */     localTypeRecIntraModRsAggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecIntraModRsAggregate.RecurrInst);
/* 24:23 */     localTypeRecIntraModRsAggregate.IntraRs = TypeIntraRsAggregateHelper.clone(paramTypeRecIntraModRsAggregate.IntraRs);
/* 25:24 */     localTypeRecIntraModRsAggregate.ModPending = paramTypeRecIntraModRsAggregate.ModPending;
/* 26:25 */     return localTypeRecIntraModRsAggregate;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeRecIntraModRsAggregate read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypeRecIntraModRsAggregate localTypeRecIntraModRsAggregate = new TypeRecIntraModRsAggregate();
/* 32:32 */     localTypeRecIntraModRsAggregate.RecSrvrTID = paramInputStream.read_string();
/* 33:33 */     localTypeRecIntraModRsAggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/* 34:34 */     localTypeRecIntraModRsAggregate.IntraRs = TypeIntraRsAggregateHelper.read(paramInputStream);
/* 35:35 */     localTypeRecIntraModRsAggregate.ModPending = paramInputStream.read_boolean();
/* 36:36 */     return localTypeRecIntraModRsAggregate;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraModRsAggregate paramTypeRecIntraModRsAggregate)
/* 40:   */   {
/* 41:43 */     if (paramTypeRecIntraModRsAggregate == null) {
/* 42:45 */       paramTypeRecIntraModRsAggregate = new TypeRecIntraModRsAggregate();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypeRecIntraModRsAggregate.RecSrvrTID);
/* 45:48 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecIntraModRsAggregate.RecurrInst);
/* 46:49 */     TypeIntraRsAggregateHelper.write(paramOutputStream, paramTypeRecIntraModRsAggregate.IntraRs);
/* 47:50 */     paramOutputStream.write_boolean(paramTypeRecIntraModRsAggregate.ModPending);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecIntraModRsAggregate";
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
/* 64:69 */         new StructMember("IntraRs", TypeIntraRsAggregateHelper.type(), null), 
/* 65:70 */         new StructMember("ModPending", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraModRsAggregate", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypeRecIntraModRsAggregate paramTypeRecIntraModRsAggregate)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypeRecIntraModRsAggregate);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypeRecIntraModRsAggregate extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecIntraModRsAggregate:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */