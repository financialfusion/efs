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
/* 13:   */ public abstract class TypePmtPrcStsAggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypePmtPrcStsAggregate clone(TypePmtPrcStsAggregate paramTypePmtPrcStsAggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypePmtPrcStsAggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypePmtPrcStsAggregate localTypePmtPrcStsAggregate = new TypePmtPrcStsAggregate();
/* 23:21 */     localTypePmtPrcStsAggregate.PmtPrcCode = ((EnumPmtProcessStatusEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypePmtPrcStsAggregate.PmtPrcCode)));
/* 24:22 */     localTypePmtPrcStsAggregate.DtPmtPrc = paramTypePmtPrcStsAggregate.DtPmtPrc;
/* 25:23 */     return localTypePmtPrcStsAggregate;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypePmtPrcStsAggregate read(InputStream paramInputStream)
/* 29:   */   {
/* 30:29 */     TypePmtPrcStsAggregate localTypePmtPrcStsAggregate = new TypePmtPrcStsAggregate();
/* 31:30 */     localTypePmtPrcStsAggregate.PmtPrcCode = EnumPmtProcessStatusEnumHelper.read(paramInputStream);
/* 32:31 */     localTypePmtPrcStsAggregate.DtPmtPrc = paramInputStream.read_string();
/* 33:32 */     return localTypePmtPrcStsAggregate;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void write(OutputStream paramOutputStream, TypePmtPrcStsAggregate paramTypePmtPrcStsAggregate)
/* 37:   */   {
/* 38:39 */     if (paramTypePmtPrcStsAggregate == null) {
/* 39:41 */       paramTypePmtPrcStsAggregate = new TypePmtPrcStsAggregate();
/* 40:   */     }
/* 41:43 */     EnumPmtProcessStatusEnumHelper.write(paramOutputStream, paramTypePmtPrcStsAggregate.PmtPrcCode);
/* 42:44 */     paramOutputStream.write_string(paramTypePmtPrcStsAggregate.DtPmtPrc);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static String _idl()
/* 46:   */   {
/* 47:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtPrcStsAggregate";
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static TypeCode type()
/* 51:   */   {
/* 52:56 */     if (_type == null)
/* 53:   */     {
/* 54:58 */       ORB localORB = ORB.init();
/* 55:59 */       StructMember[] arrayOfStructMember = 
/* 56:60 */         {
/* 57:61 */         new StructMember("PmtPrcCode", EnumPmtProcessStatusEnumHelper.type(), null), 
/* 58:62 */         new StructMember("DtPmtPrc", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 59:   */       
/* 60:64 */       _type = localORB.create_struct_tc(id(), "TypePmtPrcStsAggregate", arrayOfStructMember);
/* 61:   */     }
/* 62:66 */     return _type;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static void insert(Any paramAny, TypePmtPrcStsAggregate paramTypePmtPrcStsAggregate)
/* 66:   */   {
/* 67:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 68:74 */     write(localOutputStream, paramTypePmtPrcStsAggregate);
/* 69:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static TypePmtPrcStsAggregate extract(Any paramAny)
/* 73:   */   {
/* 74:81 */     if (!paramAny.type().equal(type())) {
/* 75:83 */       throw new BAD_OPERATION();
/* 76:   */     }
/* 77:85 */     return read(paramAny.create_input_stream());
/* 78:   */   }
/* 79:   */   
/* 80:   */   public static String id()
/* 81:   */   {
/* 82:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtPrcStsAggregate:1.0";
/* 83:   */   }
/* 84:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtPrcStsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */