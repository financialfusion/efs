/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import com.sybase.CORBA.ObjectVal;
/*  4:   */ import org.omg.CORBA.Any;
/*  5:   */ import org.omg.CORBA.BAD_OPERATION;
/*  6:   */ import org.omg.CORBA.ORB;
/*  7:   */ import org.omg.CORBA.StructMember;
/*  8:   */ import org.omg.CORBA.TCKind;
/*  9:   */ import org.omg.CORBA.TypeCode;
/* 10:   */ import org.omg.CORBA.portable.InputStream;
/* 11:   */ import org.omg.CORBA.portable.OutputStream;
/* 12:   */ 
/* 13:   */ public abstract class TypePmtModRqV1AggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypePmtModRqV1Aggregate clone(TypePmtModRqV1Aggregate paramTypePmtModRqV1Aggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypePmtModRqV1Aggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypePmtModRqV1Aggregate localTypePmtModRqV1Aggregate = new TypePmtModRqV1Aggregate();
/* 23:21 */     localTypePmtModRqV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypePmtModRqV1Aggregate.CurDef)));
/* 24:22 */     localTypePmtModRqV1Aggregate.CurDefExists = paramTypePmtModRqV1Aggregate.CurDefExists;
/* 25:23 */     localTypePmtModRqV1Aggregate.SrvrTID = paramTypePmtModRqV1Aggregate.SrvrTID;
/* 26:24 */     localTypePmtModRqV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.clone(paramTypePmtModRqV1Aggregate.PmtInfo);
/* 27:25 */     return localTypePmtModRqV1Aggregate;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static TypePmtModRqV1Aggregate read(InputStream paramInputStream)
/* 31:   */   {
/* 32:31 */     TypePmtModRqV1Aggregate localTypePmtModRqV1Aggregate = new TypePmtModRqV1Aggregate();
/* 33:32 */     localTypePmtModRqV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/* 34:33 */     localTypePmtModRqV1Aggregate.CurDefExists = paramInputStream.read_boolean();
/* 35:34 */     localTypePmtModRqV1Aggregate.SrvrTID = paramInputStream.read_string();
/* 36:35 */     localTypePmtModRqV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.read(paramInputStream);
/* 37:36 */     return localTypePmtModRqV1Aggregate;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static void write(OutputStream paramOutputStream, TypePmtModRqV1Aggregate paramTypePmtModRqV1Aggregate)
/* 41:   */   {
/* 42:43 */     if (paramTypePmtModRqV1Aggregate == null) {
/* 43:45 */       paramTypePmtModRqV1Aggregate = new TypePmtModRqV1Aggregate();
/* 44:   */     }
/* 45:47 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypePmtModRqV1Aggregate.CurDef);
/* 46:48 */     paramOutputStream.write_boolean(paramTypePmtModRqV1Aggregate.CurDefExists);
/* 47:49 */     paramOutputStream.write_string(paramTypePmtModRqV1Aggregate.SrvrTID);
/* 48:50 */     TypePmtInfoV1AggregateHelper.write(paramOutputStream, paramTypePmtModRqV1Aggregate.PmtInfo);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static String _idl()
/* 52:   */   {
/* 53:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtModRqV1Aggregate";
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static TypeCode type()
/* 57:   */   {
/* 58:62 */     if (_type == null)
/* 59:   */     {
/* 60:64 */       ORB localORB = ORB.init();
/* 61:65 */       StructMember[] arrayOfStructMember = 
/* 62:66 */         {
/* 63:67 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/* 64:68 */         new StructMember("CurDefExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 65:69 */         new StructMember("SrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 66:70 */         new StructMember("PmtInfo", TypePmtInfoV1AggregateHelper.type(), null) };
/* 67:   */       
/* 68:72 */       _type = localORB.create_struct_tc(id(), "TypePmtModRqV1Aggregate", arrayOfStructMember);
/* 69:   */     }
/* 70:74 */     return _type;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public static void insert(Any paramAny, TypePmtModRqV1Aggregate paramTypePmtModRqV1Aggregate)
/* 74:   */   {
/* 75:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 76:82 */     write(localOutputStream, paramTypePmtModRqV1Aggregate);
/* 77:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 78:   */   }
/* 79:   */   
/* 80:   */   public static TypePmtModRqV1Aggregate extract(Any paramAny)
/* 81:   */   {
/* 82:89 */     if (!paramAny.type().equal(type())) {
/* 83:91 */       throw new BAD_OPERATION();
/* 84:   */     }
/* 85:93 */     return read(paramAny.create_input_stream());
/* 86:   */   }
/* 87:   */   
/* 88:   */   public static String id()
/* 89:   */   {
/* 90:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtModRqV1Aggregate:1.0";
/* 91:   */   }
/* 92:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtModRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */