/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;
/*  2:   */ 
/*  3:   */ import com.sybase.CORBA.ObjectRef;
/*  4:   */ import org.omg.CORBA.Any;
/*  5:   */ import org.omg.CORBA.BAD_OPERATION;
/*  6:   */ import org.omg.CORBA.ORB;
/*  7:   */ import org.omg.CORBA.TypeCode;
/*  8:   */ import org.omg.CORBA.portable.InputStream;
/*  9:   */ import org.omg.CORBA.portable.OutputStream;
/* 10:   */ 
/* 11:   */ public abstract class OFX151BPWServicesHomeHelper
/* 12:   */ {
/* 13:   */   public static TypeCode _type;
/* 14:   */   
/* 15:   */   public static OFX151BPWServicesHome read(InputStream paramInputStream)
/* 16:   */   {
/* 17:16 */     return unchecked_narrow(paramInputStream.read_Object());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static void write(OutputStream paramOutputStream, OFX151BPWServicesHome paramOFX151BPWServicesHome)
/* 21:   */   {
/* 22:23 */     paramOutputStream.write_Object((org.omg.CORBA.Object)paramOFX151BPWServicesHome);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static OFX151BPWServicesHome narrow(org.omg.CORBA.Object paramObject)
/* 26:   */   {
/* 27:29 */     if (paramObject == null) {
/* 28:31 */       return null;
/* 29:   */     }
/* 30:33 */     if ((paramObject._is_a("IDL:com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesHome:1.0")) || (paramObject._is_a("RMI:com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.OFX151BPWServicesHome:0000000000000000"))) {
/* 31:35 */       return new OFX151BPWServicesHome_Stub((ObjectRef)paramObject);
/* 32:   */     }
/* 33:39 */     return null;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static OFX151BPWServicesHome unchecked_narrow(org.omg.CORBA.Object paramObject)
/* 37:   */   {
/* 38:46 */     if (paramObject == null) {
/* 39:48 */       return null;
/* 40:   */     }
/* 41:52 */     return new OFX151BPWServicesHome_Stub((ObjectRef)paramObject);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static String _idl()
/* 45:   */   {
/* 46:58 */     return "com::ffusion::msgbroker::generated::MessageBroker::api::OFX151::BPWServices::OFX151BPWServicesHome";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:65 */     if (_type == null)
/* 52:   */     {
/* 53:67 */       ORB localORB = ORB.init();
/* 54:68 */       _type = localORB.create_interface_tc(id(), "OFX151BPWServicesHome");
/* 55:   */     }
/* 56:70 */     return _type;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void insert(Any paramAny, OFX151BPWServicesHome paramOFX151BPWServicesHome)
/* 60:   */   {
/* 61:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 62:78 */     write(localOutputStream, paramOFX151BPWServicesHome);
/* 63:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static OFX151BPWServicesHome extract(Any paramAny)
/* 67:   */   {
/* 68:85 */     if (!paramAny.type().equal(type())) {
/* 69:87 */       throw new BAD_OPERATION();
/* 70:   */     }
/* 71:89 */     return read(paramAny.create_input_stream());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static String id()
/* 75:   */   {
/* 76:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/api/OFX151/BPWServices/OFX151BPWServicesHome:1.0";
/* 77:   */   }
/* 78:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.OFX151BPWServicesHomeHelper
 * JD-Core Version:    0.7.0.1
 */