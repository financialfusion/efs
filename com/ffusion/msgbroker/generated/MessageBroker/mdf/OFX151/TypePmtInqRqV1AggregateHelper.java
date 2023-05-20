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
/* 12:   */ public abstract class TypePmtInqRqV1AggregateHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypePmtInqRqV1Aggregate clone(TypePmtInqRqV1Aggregate paramTypePmtInqRqV1Aggregate)
/* 17:   */   {
/* 18:16 */     if (paramTypePmtInqRqV1Aggregate == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypePmtInqRqV1Aggregate localTypePmtInqRqV1Aggregate = new TypePmtInqRqV1Aggregate();
/* 22:21 */     localTypePmtInqRqV1Aggregate.SrvrTID = paramTypePmtInqRqV1Aggregate.SrvrTID;
/* 23:22 */     return localTypePmtInqRqV1Aggregate;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static TypePmtInqRqV1Aggregate read(InputStream paramInputStream)
/* 27:   */   {
/* 28:28 */     TypePmtInqRqV1Aggregate localTypePmtInqRqV1Aggregate = new TypePmtInqRqV1Aggregate();
/* 29:29 */     localTypePmtInqRqV1Aggregate.SrvrTID = paramInputStream.read_string();
/* 30:30 */     return localTypePmtInqRqV1Aggregate;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static void write(OutputStream paramOutputStream, TypePmtInqRqV1Aggregate paramTypePmtInqRqV1Aggregate)
/* 34:   */   {
/* 35:37 */     if (paramTypePmtInqRqV1Aggregate == null) {
/* 36:39 */       paramTypePmtInqRqV1Aggregate = new TypePmtInqRqV1Aggregate();
/* 37:   */     }
/* 38:41 */     paramOutputStream.write_string(paramTypePmtInqRqV1Aggregate.SrvrTID);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static String _idl()
/* 42:   */   {
/* 43:46 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtInqRqV1Aggregate";
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
/* 55:60 */       _type = localORB.create_struct_tc(id(), "TypePmtInqRqV1Aggregate", arrayOfStructMember);
/* 56:   */     }
/* 57:62 */     return _type;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static void insert(Any paramAny, TypePmtInqRqV1Aggregate paramTypePmtInqRqV1Aggregate)
/* 61:   */   {
/* 62:69 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 63:70 */     write(localOutputStream, paramTypePmtInqRqV1Aggregate);
/* 64:71 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static TypePmtInqRqV1Aggregate extract(Any paramAny)
/* 68:   */   {
/* 69:77 */     if (!paramAny.type().equal(type())) {
/* 70:79 */       throw new BAD_OPERATION();
/* 71:   */     }
/* 72:81 */     return read(paramAny.create_input_stream());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static String id()
/* 76:   */   {
/* 77:86 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtInqRqV1Aggregate:1.0";
/* 78:   */   }
/* 79:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */