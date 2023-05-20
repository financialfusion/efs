/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
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
/* 13:   */ public abstract class TypeXferPrcStsAggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypeXferPrcStsAggregate clone(TypeXferPrcStsAggregate paramTypeXferPrcStsAggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypeXferPrcStsAggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
/* 23:21 */     localTypeXferPrcStsAggregate.XferPrcCode = ((EnumXferStatusEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeXferPrcStsAggregate.XferPrcCode)));
/* 24:22 */     localTypeXferPrcStsAggregate.DtXferPrc = paramTypeXferPrcStsAggregate.DtXferPrc;
/* 25:23 */     return localTypeXferPrcStsAggregate;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeXferPrcStsAggregate read(InputStream paramInputStream)
/* 29:   */   {
/* 30:29 */     TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
/* 31:30 */     localTypeXferPrcStsAggregate.XferPrcCode = EnumXferStatusEnumHelper.read(paramInputStream);
/* 32:31 */     localTypeXferPrcStsAggregate.DtXferPrc = paramInputStream.read_string();
/* 33:32 */     return localTypeXferPrcStsAggregate;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void write(OutputStream paramOutputStream, TypeXferPrcStsAggregate paramTypeXferPrcStsAggregate)
/* 37:   */   {
/* 38:39 */     if (paramTypeXferPrcStsAggregate == null) {
/* 39:41 */       paramTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
/* 40:   */     }
/* 41:43 */     EnumXferStatusEnumHelper.write(paramOutputStream, paramTypeXferPrcStsAggregate.XferPrcCode);
/* 42:44 */     paramOutputStream.write_string(paramTypeXferPrcStsAggregate.DtXferPrc);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static String _idl()
/* 46:   */   {
/* 47:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeXferPrcStsAggregate";
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static TypeCode type()
/* 51:   */   {
/* 52:56 */     if (_type == null)
/* 53:   */     {
/* 54:58 */       ORB localORB = ORB.init();
/* 55:59 */       StructMember[] arrayOfStructMember = 
/* 56:60 */         {
/* 57:61 */         new StructMember("XferPrcCode", EnumXferStatusEnumHelper.type(), null), 
/* 58:62 */         new StructMember("DtXferPrc", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 59:   */       
/* 60:64 */       _type = localORB.create_struct_tc(id(), "TypeXferPrcStsAggregate", arrayOfStructMember);
/* 61:   */     }
/* 62:66 */     return _type;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static void insert(Any paramAny, TypeXferPrcStsAggregate paramTypeXferPrcStsAggregate)
/* 66:   */   {
/* 67:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 68:74 */     write(localOutputStream, paramTypeXferPrcStsAggregate);
/* 69:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static TypeXferPrcStsAggregate extract(Any paramAny)
/* 73:   */   {
/* 74:81 */     if (!paramAny.type().equal(type())) {
/* 75:83 */       throw new BAD_OPERATION();
/* 76:   */     }
/* 77:85 */     return read(paramAny.create_input_stream());
/* 78:   */   }
/* 79:   */   
/* 80:   */   public static String id()
/* 81:   */   {
/* 82:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeXferPrcStsAggregate:1.0";
/* 83:   */   }
/* 84:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */