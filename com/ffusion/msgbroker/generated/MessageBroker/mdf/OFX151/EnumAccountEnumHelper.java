/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/* 31:33 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::EnumAccountEnum";
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
/* 45:49 */         "CREDIT", 
/* 46:50 */         "CREDITLINE", 
/* 47:51 */         "FIXEDDEPOSIT", 
/* 48:52 */         "HOMEEQUITY", 
/* 49:53 */         "IRA", 
/* 50:54 */         "LOAN", 
/* 51:55 */         "MONEYMRKT", 
/* 52:56 */         "MORTGAGE", 
/* 53:57 */         "OTHER", 
/* 54:58 */         "SAVINGS", 
/* 55:59 */         "STOCKBOND" };
/* 56:   */       
/* 57:61 */       _type = localORB.create_enum_tc(id(), "EnumAccountEnum", arrayOfString);
/* 58:   */     }
/* 59:63 */     return _type;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public static void insert(Any paramAny, EnumAccountEnum paramEnumAccountEnum)
/* 63:   */   {
/* 64:70 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 65:71 */     write(localOutputStream, paramEnumAccountEnum);
/* 66:72 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 67:   */   }
/* 68:   */   
/* 69:   */   public static EnumAccountEnum extract(Any paramAny)
/* 70:   */   {
/* 71:78 */     if (!paramAny.type().equal(type())) {
/* 72:80 */       throw new BAD_OPERATION();
/* 73:   */     }
/* 74:82 */     return read(paramAny.create_input_stream());
/* 75:   */   }
/* 76:   */   
/* 77:   */   public static String id()
/* 78:   */   {
/* 79:87 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/EnumAccountEnum:1.0";
/* 80:   */   }
/* 81:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnumHelper
 * JD-Core Version:    0.7.0.1
 */