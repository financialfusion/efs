/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class EnumSeverityEnumHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static EnumSeverityEnum read(InputStream paramInputStream)
/* 15:   */   {
/* 16:16 */     return EnumSeverityEnum.from_int(paramInputStream.read_ulong());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void write(OutputStream paramOutputStream, EnumSeverityEnum paramEnumSeverityEnum)
/* 20:   */   {
/* 21:23 */     if (paramEnumSeverityEnum == null)
/* 22:   */     {
/* 23:25 */       paramOutputStream.write_ulong(0);
/* 24:26 */       return;
/* 25:   */     }
/* 26:28 */     paramOutputStream.write_ulong(paramEnumSeverityEnum.value());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static String _idl()
/* 30:   */   {
/* 31:33 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::EnumSeverityEnum";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static TypeCode type()
/* 35:   */   {
/* 36:40 */     if (_type == null)
/* 37:   */     {
/* 38:42 */       ORB localORB = ORB.init();
/* 39:43 */       String[] arrayOfString = 
/* 40:44 */         {
/* 41:45 */         "ERROR", 
/* 42:46 */         "INFO", 
/* 43:47 */         "WARN" };
/* 44:   */       
/* 45:49 */       _type = localORB.create_enum_tc(id(), "EnumSeverityEnum", arrayOfString);
/* 46:   */     }
/* 47:51 */     return _type;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static void insert(Any paramAny, EnumSeverityEnum paramEnumSeverityEnum)
/* 51:   */   {
/* 52:58 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 53:59 */     write(localOutputStream, paramEnumSeverityEnum);
/* 54:60 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 55:   */   }
/* 56:   */   
/* 57:   */   public static EnumSeverityEnum extract(Any paramAny)
/* 58:   */   {
/* 59:66 */     if (!paramAny.type().equal(type())) {
/* 60:68 */       throw new BAD_OPERATION();
/* 61:   */     }
/* 62:70 */     return read(paramAny.create_input_stream());
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static String id()
/* 66:   */   {
/* 67:75 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/EnumSeverityEnum:1.0";
/* 68:   */   }
/* 69:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnumHelper
 * JD-Core Version:    0.7.0.1
 */