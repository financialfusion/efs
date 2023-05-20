/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.TypeCode;
/*  7:   */ import org.omg.CORBA.portable.InputStream;
/*  8:   */ import org.omg.CORBA.portable.OutputStream;
/*  9:   */ 
/* 10:   */ public abstract class EnumXferStatusEnumHelper
/* 11:   */ {
/* 12:   */   public static TypeCode _type;
/* 13:   */   
/* 14:   */   public static EnumXferStatusEnum read(InputStream paramInputStream)
/* 15:   */   {
/* 16:16 */     return EnumXferStatusEnum.from_int(paramInputStream.read_ulong());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void write(OutputStream paramOutputStream, EnumXferStatusEnum paramEnumXferStatusEnum)
/* 20:   */   {
/* 21:23 */     if (paramEnumXferStatusEnum == null)
/* 22:   */     {
/* 23:25 */       paramOutputStream.write_ulong(0);
/* 24:26 */       return;
/* 25:   */     }
/* 26:28 */     paramOutputStream.write_ulong(paramEnumXferStatusEnum.value());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static String _idl()
/* 30:   */   {
/* 31:33 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::EnumXferStatusEnum";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static TypeCode type()
/* 35:   */   {
/* 36:40 */     if (_type == null)
/* 37:   */     {
/* 38:42 */       ORB localORB = ORB.init();
/* 39:43 */       String[] arrayOfString = 
/* 40:44 */         {
/* 41:45 */         "CANCELEDON", 
/* 42:46 */         "FAILEDON", 
/* 43:47 */         "NOFUNDSON", 
/* 44:48 */         "POSTEDON", 
/* 45:49 */         "WILLPROCESSON" };
/* 46:   */       
/* 47:51 */       _type = localORB.create_enum_tc(id(), "EnumXferStatusEnum", arrayOfString);
/* 48:   */     }
/* 49:53 */     return _type;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static void insert(Any paramAny, EnumXferStatusEnum paramEnumXferStatusEnum)
/* 53:   */   {
/* 54:60 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 55:61 */     write(localOutputStream, paramEnumXferStatusEnum);
/* 56:62 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static EnumXferStatusEnum extract(Any paramAny)
/* 60:   */   {
/* 61:68 */     if (!paramAny.type().equal(type())) {
/* 62:70 */       throw new BAD_OPERATION();
/* 63:   */     }
/* 64:72 */     return read(paramAny.create_input_stream());
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static String id()
/* 68:   */   {
/* 69:77 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/EnumXferStatusEnum:1.0";
/* 70:   */   }
/* 71:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnumHelper
 * JD-Core Version:    0.7.0.1
 */