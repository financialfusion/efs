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
/* 12:   */ public abstract class TypePmtInqRsV1AggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePmtInqRsV1Aggregate clone(TypePmtInqRsV1Aggregate paramTypePmtInqRsV1Aggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypePmtInqRsV1Aggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePmtInqRsV1Aggregate localTypePmtInqRsV1Aggregate = new TypePmtInqRsV1Aggregate();
/* 22:21 */     localTypePmtInqRsV1Aggregate.SrvrTID = paramTypePmtInqRsV1Aggregate.SrvrTID;
/* 23:22 */     localTypePmtInqRsV1Aggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.clone(paramTypePmtInqRsV1Aggregate.PmtPrcSts);
/* 24:23 */     localTypePmtInqRsV1Aggregate.CheckNum = paramTypePmtInqRsV1Aggregate.CheckNum;
/* 25:24 */     localTypePmtInqRsV1Aggregate.CheckNumExists = paramTypePmtInqRsV1Aggregate.CheckNumExists;
/* 26:25 */     return localTypePmtInqRsV1Aggregate;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypePmtInqRsV1Aggregate read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypePmtInqRsV1Aggregate localTypePmtInqRsV1Aggregate = new TypePmtInqRsV1Aggregate();
/* 32:32 */     localTypePmtInqRsV1Aggregate.SrvrTID = paramInputStream.read_string();
/* 33:33 */     localTypePmtInqRsV1Aggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.read(paramInputStream);
/* 34:34 */     localTypePmtInqRsV1Aggregate.CheckNum = paramInputStream.read_string();
/* 35:35 */     localTypePmtInqRsV1Aggregate.CheckNumExists = paramInputStream.read_boolean();
/* 36:36 */     return localTypePmtInqRsV1Aggregate;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypePmtInqRsV1Aggregate paramTypePmtInqRsV1Aggregate)
/* 40:   */   {
/* 41:43 */     if (paramTypePmtInqRsV1Aggregate == null) {
/* 42:45 */       paramTypePmtInqRsV1Aggregate = new TypePmtInqRsV1Aggregate();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypePmtInqRsV1Aggregate.SrvrTID);
/* 45:48 */     TypePmtPrcStsAggregateHelper.write(paramOutputStream, paramTypePmtInqRsV1Aggregate.PmtPrcSts);
/* 46:49 */     paramOutputStream.write_string(paramTypePmtInqRsV1Aggregate.CheckNum);
/* 47:50 */     paramOutputStream.write_boolean(paramTypePmtInqRsV1Aggregate.CheckNumExists);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtInqRsV1Aggregate";
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
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypePmtInqRsV1Aggregate", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypePmtInqRsV1Aggregate paramTypePmtInqRsV1Aggregate)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypePmtInqRsV1Aggregate);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypePmtInqRsV1Aggregate extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtInqRsV1Aggregate:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */