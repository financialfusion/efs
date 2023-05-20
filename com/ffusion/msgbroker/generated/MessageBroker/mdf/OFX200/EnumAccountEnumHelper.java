/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class EnumAccountEnumHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static EnumAccountEnum read(InputStream paramInputStream)
/* 15:   */   {
/* 16:16 */     return EnumAccountEnum.from_int(paramInputStream.read_ulong());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void write(OutputStream paramOutputStream, EnumAccountEnum paramEnumAccountEnum)
/* 20:   */   {
/* 21:23 */     if (paramEnumAccountEnum == null)
/* 22:   */     {
/* 23:25 */       paramOutputStream.write_ulong(0);
/* 24:26 */       return;
/* 25:   */     }
/* 26:28 */     paramOutputStream.write_ulong(paramEnumAccountEnum.value());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static String _idl()
/* 30:   */   {
/* 31:33 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::EnumAccountEnum";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static TypeCode type()
/* 35:   */   {
/* 36:40 */     if (_type == null)
/* 37:   */     {
/* 38:42 */       ORB localORB = ORB.init();
/* 39:43 */       String[] arrayOfString = 
/* 40:44 */         {
/* 41:45 */         "BROKERAGE", 
/* 42:46 */         "BUSINESSLOAN", 
/* 43:47 */         "CD", 
/* 44:48 */         "CHECKING", 
/* 45:49 */         "CMA", 
/* 46:50 */         "CREDIT", 
/* 47:51 */         "CREDITLINE", 
/* 48:52 */         "DDA", 
/* 49:53 */         "FIXEDDEPOSIT", 
/* 50:54 */         "HOMEEQUITY", 
/* 51:55 */         "IRA", 
/* 52:56 */         "LOAN", 
/* 53:57 */         "MMA", 
/* 54:58 */         "MONEYMRKT", 
/* 55:59 */         "MORTGAGE", 
/* 56:60 */         "OTHER", 
/* 57:61 */         "SAVINGS", 
/* 58:62 */         "SDA", 
/* 59:63 */         "STOCKBOND" };
/* 60:   */       
/* 61:65 */       _type = localORB.create_enum_tc(id(), "EnumAccountEnum", arrayOfString);
/* 62:   */     }
/* 63:67 */     return _type;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static void insert(Any paramAny, EnumAccountEnum paramEnumAccountEnum)
/* 67:   */   {
/* 68:74 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 69:75 */     write(localOutputStream, paramEnumAccountEnum);
/* 70:76 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 71:   */   }
/* 72:   */   
/* 73:   */   public static EnumAccountEnum extract(Any paramAny)
/* 74:   */   {
/* 75:82 */     if (!paramAny.type().equal(type())) {
/* 76:84 */       throw new BAD_OPERATION();
/* 77:   */     }
/* 78:86 */     return read(paramAny.create_input_stream());
/* 79:   */   }
/* 80:   */   
/* 81:   */   public static String id()
/* 82:   */   {
/* 83:91 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/EnumAccountEnum:1.0";
/* 84:   */   }
/* 85:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnumHelper
 * JD-Core Version:    0.7.0.1
 */