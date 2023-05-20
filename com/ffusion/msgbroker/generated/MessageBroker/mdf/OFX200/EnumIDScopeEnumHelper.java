/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class EnumIDScopeEnumHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static EnumIDScopeEnum read(InputStream paramInputStream)
/* 15:   */   {
/* 16:16 */     return EnumIDScopeEnum.from_int(paramInputStream.read_ulong());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void write(OutputStream paramOutputStream, EnumIDScopeEnum paramEnumIDScopeEnum)
/* 20:   */   {
/* 21:23 */     if (paramEnumIDScopeEnum == null)
/* 22:   */     {
/* 23:25 */       paramOutputStream.write_ulong(0);
/* 24:26 */       return;
/* 25:   */     }
/* 26:28 */     paramOutputStream.write_ulong(paramEnumIDScopeEnum.value());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static String _idl()
/* 30:   */   {
/* 31:33 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::EnumIDScopeEnum";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static TypeCode type()
/* 35:   */   {
/* 36:40 */     if (_type == null)
/* 37:   */     {
/* 38:42 */       ORB localORB = ORB.init();
/* 39:43 */       String[] arrayOfString = 
/* 40:44 */         {
/* 41:45 */         "GLOBAL", 
/* 42:46 */         "USER" };
/* 43:   */       
/* 44:48 */       _type = localORB.create_enum_tc(id(), "EnumIDScopeEnum", arrayOfString);
/* 45:   */     }
/* 46:50 */     return _type;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static void insert(Any paramAny, EnumIDScopeEnum paramEnumIDScopeEnum)
/* 50:   */   {
/* 51:57 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 52:58 */     write(localOutputStream, paramEnumIDScopeEnum);
/* 53:59 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static EnumIDScopeEnum extract(Any paramAny)
/* 57:   */   {
/* 58:65 */     if (!paramAny.type().equal(type())) {
/* 59:67 */       throw new BAD_OPERATION();
/* 60:   */     }
/* 61:69 */     return read(paramAny.create_input_stream());
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static String id()
/* 65:   */   {
/* 66:74 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/EnumIDScopeEnum:1.0";
/* 67:   */   }
/* 68:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnumHelper
 * JD-Core Version:    0.7.0.1
 */