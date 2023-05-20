/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class EnumFreqEnumHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static EnumFreqEnum read(InputStream paramInputStream)
/* 15:   */   {
/* 16:16 */     return EnumFreqEnum.from_int(paramInputStream.read_ulong());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void write(OutputStream paramOutputStream, EnumFreqEnum paramEnumFreqEnum)
/* 20:   */   {
/* 21:23 */     if (paramEnumFreqEnum == null)
/* 22:   */     {
/* 23:25 */       paramOutputStream.write_ulong(0);
/* 24:26 */       return;
/* 25:   */     }
/* 26:28 */     paramOutputStream.write_ulong(paramEnumFreqEnum.value());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static String _idl()
/* 30:   */   {
/* 31:33 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::EnumFreqEnum";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static TypeCode type()
/* 35:   */   {
/* 36:40 */     if (_type == null)
/* 37:   */     {
/* 38:42 */       ORB localORB = ORB.init();
/* 39:43 */       String[] arrayOfString = 
/* 40:44 */         {
/* 41:45 */         "ANNUALLY", 
/* 42:46 */         "ANUALLY", 
/* 43:47 */         "BIMONTHLY", 
/* 44:48 */         "BIWEEKLY", 
/* 45:49 */         "FOURWEEKS", 
/* 46:50 */         "MONTHLY", 
/* 47:51 */         "QUARTERLY", 
/* 48:52 */         "SEMIANNUALLY", 
/* 49:53 */         "TRIANNUALLY", 
/* 50:54 */         "TWICEMONTHLY", 
/* 51:55 */         "WEEKLY" };
/* 52:   */       
/* 53:57 */       _type = localORB.create_enum_tc(id(), "EnumFreqEnum", arrayOfString);
/* 54:   */     }
/* 55:59 */     return _type;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static void insert(Any paramAny, EnumFreqEnum paramEnumFreqEnum)
/* 59:   */   {
/* 60:66 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 61:67 */     write(localOutputStream, paramEnumFreqEnum);
/* 62:68 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static EnumFreqEnum extract(Any paramAny)
/* 66:   */   {
/* 67:74 */     if (!paramAny.type().equal(type())) {
/* 68:76 */       throw new BAD_OPERATION();
/* 69:   */     }
/* 70:78 */     return read(paramAny.create_input_stream());
/* 71:   */   }
/* 72:   */   
/* 73:   */   public static String id()
/* 74:   */   {
/* 75:83 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/EnumFreqEnum:1.0";
/* 76:   */   }
/* 77:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumFreqEnumHelper
 * JD-Core Version:    0.7.0.1
 */