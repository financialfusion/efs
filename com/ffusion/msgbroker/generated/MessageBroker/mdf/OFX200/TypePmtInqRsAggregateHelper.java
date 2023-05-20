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
/* 12:   */ public abstract class TypePmtInqRsAggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePmtInqRsAggregate clone(TypePmtInqRsAggregate paramTypePmtInqRsAggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypePmtInqRsAggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePmtInqRsAggregate localTypePmtInqRsAggregate = new TypePmtInqRsAggregate();
/* 22:21 */     localTypePmtInqRsAggregate.SrvrTID = paramTypePmtInqRsAggregate.SrvrTID;
/* 23:22 */     localTypePmtInqRsAggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.clone(paramTypePmtInqRsAggregate.PmtPrcSts);
/* 24:23 */     localTypePmtInqRsAggregate.CheckNum = paramTypePmtInqRsAggregate.CheckNum;
/* 25:24 */     localTypePmtInqRsAggregate.CheckNumExists = paramTypePmtInqRsAggregate.CheckNumExists;
/* 26:25 */     return localTypePmtInqRsAggregate;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypePmtInqRsAggregate read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypePmtInqRsAggregate localTypePmtInqRsAggregate = new TypePmtInqRsAggregate();
/* 32:32 */     localTypePmtInqRsAggregate.SrvrTID = paramInputStream.read_string();
/* 33:33 */     localTypePmtInqRsAggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.read(paramInputStream);
/* 34:34 */     localTypePmtInqRsAggregate.CheckNum = paramInputStream.read_string();
/* 35:35 */     localTypePmtInqRsAggregate.CheckNumExists = paramInputStream.read_boolean();
/* 36:36 */     return localTypePmtInqRsAggregate;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypePmtInqRsAggregate paramTypePmtInqRsAggregate)
/* 40:   */   {
/* 41:43 */     if (paramTypePmtInqRsAggregate == null) {
/* 42:45 */       paramTypePmtInqRsAggregate = new TypePmtInqRsAggregate();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypePmtInqRsAggregate.SrvrTID);
/* 45:48 */     TypePmtPrcStsAggregateHelper.write(paramOutputStream, paramTypePmtInqRsAggregate.PmtPrcSts);
/* 46:49 */     paramOutputStream.write_string(paramTypePmtInqRsAggregate.CheckNum);
/* 47:50 */     paramOutputStream.write_boolean(paramTypePmtInqRsAggregate.CheckNumExists);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePmtInqRsAggregate";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static TypeCode type()
/* 56:   */   {
/* 57:62 */     if (_type == null)
/* 58:   */     {
/* 59:64 */       ORB localORB = ORB.init();
/* 60:65 */       StructMember[] arrayOfStructMember = 
/* 61:66 */         {
/* 62:67 */         new StructMember("SrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 63:68 */         new StructMember("PmtPrcSts", TypePmtPrcStsAggregateHelper.type(), null), 
/* 64:69 */         new StructMember("CheckNum", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 65:70 */         new StructMember("CheckNumExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypePmtInqRsAggregate", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypePmtInqRsAggregate paramTypePmtInqRsAggregate)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypePmtInqRsAggregate);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypePmtInqRsAggregate extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePmtInqRsAggregate:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */