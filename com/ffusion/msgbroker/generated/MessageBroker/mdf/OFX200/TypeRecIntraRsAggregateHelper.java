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
/* 12:   */ public abstract class TypeRecIntraRsAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeRecIntraRsAggregate clone(TypeRecIntraRsAggregate paramTypeRecIntraRsAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypeRecIntraRsAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeRecIntraRsAggregate localTypeRecIntraRsAggregate = new TypeRecIntraRsAggregate();
/* 22:21 */     localTypeRecIntraRsAggregate.RecSrvrTID = paramTypeRecIntraRsAggregate.RecSrvrTID;
/* 23:22 */     localTypeRecIntraRsAggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecIntraRsAggregate.RecurrInst);
/* 24:23 */     localTypeRecIntraRsAggregate.IntraRs = TypeIntraRsAggregateHelper.clone(paramTypeRecIntraRsAggregate.IntraRs);
/* 25:24 */     return localTypeRecIntraRsAggregate;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeRecIntraRsAggregate read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeRecIntraRsAggregate localTypeRecIntraRsAggregate = new TypeRecIntraRsAggregate();
/* 31:31 */     localTypeRecIntraRsAggregate.RecSrvrTID = paramInputStream.read_string();
/* 32:32 */     localTypeRecIntraRsAggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/* 33:33 */     localTypeRecIntraRsAggregate.IntraRs = TypeIntraRsAggregateHelper.read(paramInputStream);
/* 34:34 */     return localTypeRecIntraRsAggregate;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeRecIntraRsAggregate paramTypeRecIntraRsAggregate)
/* 38:   */   {
/* 39:41 */     if (paramTypeRecIntraRsAggregate == null) {
/* 40:43 */       paramTypeRecIntraRsAggregate = new TypeRecIntraRsAggregate();
/* 41:   */     }
/* 42:45 */     paramOutputStream.write_string(paramTypeRecIntraRsAggregate.RecSrvrTID);
/* 43:46 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecIntraRsAggregate.RecurrInst);
/* 44:47 */     TypeIntraRsAggregateHelper.write(paramOutputStream, paramTypeRecIntraRsAggregate.IntraRs);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecIntraRsAggregate";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 60:65 */         new StructMember("RecurrInst", TypeRecurrInstAggregateHelper.type(), null), 
/* 61:66 */         new StructMember("IntraRs", TypeIntraRsAggregateHelper.type(), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeRecIntraRsAggregate", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeRecIntraRsAggregate paramTypeRecIntraRsAggregate)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeRecIntraRsAggregate);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeRecIntraRsAggregate extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecIntraRsAggregate:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */