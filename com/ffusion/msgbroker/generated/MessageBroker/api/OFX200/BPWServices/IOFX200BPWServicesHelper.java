/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices;
/*  2:   */ 
/*  3:   */ import com.sybase.CORBA.ObjectRef;
/*  4:   */ import org.omg.CORBA.Any;
/*  5:   */ import org.omg.CORBA.BAD_OPERATION;
/*  6:   */ import org.omg.CORBA.ORB;
/*  7:   */ import org.omg.CORBA.TypeCode;
/*  8:   */ import org.omg.CORBA.portable.InputStream;
/*  9:   */ import org.omg.CORBA.portable.OutputStream;
/* 10:   */ 
/* 11:   */ public abstract class IOFX200BPWServicesHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static IOFX200BPWServices read(InputStream paramInputStream)
/* 16:   */   {
/* 17:16 */     return unchecked_narrow(paramInputStream.read_Object());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static void write(OutputStream paramOutputStream, IOFX200BPWServices paramIOFX200BPWServices)
/* 21:   */   {
/* 22:23 */     paramOutputStream.write_Object((org.omg.CORBA.Object)paramIOFX200BPWServices);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static IOFX200BPWServices narrow(org.omg.CORBA.Object paramObject)
/* 26:   */   {
/* 27:29 */     if (paramObject == null) {
/* 28:31 */       return null;
/* 29:   */     }
/* 30:33 */     if ((paramObject._is_a("IDL:com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/IOFX200BPWServices:1.0")) || (paramObject._is_a("RMI:com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices:0000000000000000"))) {
/* 31:35 */       return new IOFX200BPWServices_Stub((ObjectRef)paramObject);
/* 32:   */     }
/* 33:39 */     return null;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static IOFX200BPWServices unchecked_narrow(org.omg.CORBA.Object paramObject)
/* 37:   */   {
/* 38:46 */     if (paramObject == null) {
/* 39:48 */       return null;
/* 40:   */     }
/* 41:52 */     return new IOFX200BPWServices_Stub((ObjectRef)paramObject);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static String _idl()
/* 45:   */   {
/* 46:58 */     return "com::ffusion::msgbroker::generated::MessageBroker::api::OFX200::BPWServices::IOFX200BPWServices";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:65 */     if (_type == null)
/* 52:   */     {
/* 53:67 */       ORB localORB = ORB.init();
/* 54:68 */       _type = localORB.create_interface_tc(id(), "IOFX200BPWServices");
/* 55:   */     }
/* 56:70 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, IOFX200BPWServices paramIOFX200BPWServices)
/* 60:   */   {
/* 61:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:78 */     write(localOutputStream, paramIOFX200BPWServices);
/* 63:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static IOFX200BPWServices extract(Any paramAny)
/* 67:   */   {
/* 68:85 */     if (!paramAny.type().equal(type())) {
/* 69:87 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:89 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/api/OFX200/BPWServices/IOFX200BPWServices:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServicesHelper
 * JD-Core Version:    0.7.0.1
 */