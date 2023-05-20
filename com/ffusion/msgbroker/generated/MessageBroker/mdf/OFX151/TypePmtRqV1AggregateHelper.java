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
/* 13:   */ public abstract class TypePmtRqV1AggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypePmtRqV1Aggregate clone(TypePmtRqV1Aggregate paramTypePmtRqV1Aggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypePmtRqV1Aggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypePmtRqV1Aggregate localTypePmtRqV1Aggregate = new TypePmtRqV1Aggregate();
/* 23:21 */     localTypePmtRqV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypePmtRqV1Aggregate.CurDef)));
/* 24:22 */     localTypePmtRqV1Aggregate.CurDefExists = paramTypePmtRqV1Aggregate.CurDefExists;
/* 25:23 */     localTypePmtRqV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.clone(paramTypePmtRqV1Aggregate.PmtInfo);
/* 26:24 */     return localTypePmtRqV1Aggregate;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypePmtRqV1Aggregate read(InputStream paramInputStream)
/* 30:   */   {
/* 31:30 */     TypePmtRqV1Aggregate localTypePmtRqV1Aggregate = new TypePmtRqV1Aggregate();
/* 32:31 */     localTypePmtRqV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/* 33:32 */     localTypePmtRqV1Aggregate.CurDefExists = paramInputStream.read_boolean();
/* 34:33 */     localTypePmtRqV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.read(paramInputStream);
/* 35:34 */     return localTypePmtRqV1Aggregate;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static void write(OutputStream paramOutputStream, TypePmtRqV1Aggregate paramTypePmtRqV1Aggregate)
/* 39:   */   {
/* 40:41 */     if (paramTypePmtRqV1Aggregate == null) {
/* 41:43 */       paramTypePmtRqV1Aggregate = new TypePmtRqV1Aggregate();
/* 42:   */     }
/* 43:45 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypePmtRqV1Aggregate.CurDef);
/* 44:46 */     paramOutputStream.write_boolean(paramTypePmtRqV1Aggregate.CurDefExists);
/* 45:47 */     TypePmtInfoV1AggregateHelper.write(paramOutputStream, paramTypePmtRqV1Aggregate.PmtInfo);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static String _idl()
/* 49:   */   {
/* 50:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtRqV1Aggregate";
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static TypeCode type()
/* 54:   */   {
/* 55:59 */     if (_type == null)
/* 56:   */     {
/* 57:61 */       ORB localORB = ORB.init();
/* 58:62 */       StructMember[] arrayOfStructMember = 
/* 59:63 */         {
/* 60:64 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/* 61:65 */         new StructMember("CurDefExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 62:66 */         new StructMember("PmtInfo", TypePmtInfoV1AggregateHelper.type(), null) };
/* 63:   */       
/* 64:68 */       _type = localORB.create_struct_tc(id(), "TypePmtRqV1Aggregate", arrayOfStructMember);
/* 65:   */     }
/* 66:70 */     return _type;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public static void insert(Any paramAny, TypePmtRqV1Aggregate paramTypePmtRqV1Aggregate)
/* 70:   */   {
/* 71:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 72:78 */     write(localOutputStream, paramTypePmtRqV1Aggregate);
/* 73:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static TypePmtRqV1Aggregate extract(Any paramAny)
/* 77:   */   {
/* 78:85 */     if (!paramAny.type().equal(type())) {
/* 79:87 */       throw new BAD_OPERATION();
/* 80:   */     }
/* 81:89 */     return read(paramAny.create_input_stream());
/* 82:   */   }
/* 83:   */   
/* 84:   */   public static String id()
/* 85:   */   {
/* 86:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtRqV1Aggregate:1.0";
/* 87:   */   }
/* 88:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */