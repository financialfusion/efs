/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.Any;
/*  4:   */ import org.omg.CORBA.BAD_OPERATION;
/*  5:   */ import org.omg.CORBA.ORB;
/*  6:   */ import org.omg.CORBA.StructMember;
/*  7:   */ import org.omg.CORBA.TCKind;
/*  8:   */ import org.omg.CORBA.TypeCode;
/*  9:   */ import org.omg.CORBA.portable.InputStream;
/* 10:   */ import org.omg.CORBA.portable.OutputStream;
/* 11:   */ 
/* 12:   */ public abstract class TypeTrnRsV1CmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeTrnRsV1Cm clone(TypeTrnRsV1Cm paramTypeTrnRsV1Cm)
/* 17:   */   {
/* 18:16 */     if (paramTypeTrnRsV1Cm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeTrnRsV1Cm localTypeTrnRsV1Cm = new TypeTrnRsV1Cm();
/* 22:21 */     localTypeTrnRsV1Cm.TrnUID = paramTypeTrnRsV1Cm.TrnUID;
/* 23:22 */     localTypeTrnRsV1Cm.Status = TypeStatusV1AggregateHelper.clone(paramTypeTrnRsV1Cm.Status);
/* 24:23 */     localTypeTrnRsV1Cm.CltCookie = paramTypeTrnRsV1Cm.CltCookie;
/* 25:24 */     localTypeTrnRsV1Cm.CltCookieExists = paramTypeTrnRsV1Cm.CltCookieExists;
/* 26:25 */     return localTypeTrnRsV1Cm;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeTrnRsV1Cm read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypeTrnRsV1Cm localTypeTrnRsV1Cm = new TypeTrnRsV1Cm();
/* 32:32 */     localTypeTrnRsV1Cm.TrnUID = paramInputStream.read_string();
/* 33:33 */     localTypeTrnRsV1Cm.Status = TypeStatusV1AggregateHelper.read(paramInputStream);
/* 34:34 */     localTypeTrnRsV1Cm.CltCookie = paramInputStream.read_string();
/* 35:35 */     localTypeTrnRsV1Cm.CltCookieExists = paramInputStream.read_boolean();
/* 36:36 */     return localTypeTrnRsV1Cm;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypeTrnRsV1Cm paramTypeTrnRsV1Cm)
/* 40:   */   {
/* 41:43 */     if (paramTypeTrnRsV1Cm == null) {
/* 42:45 */       paramTypeTrnRsV1Cm = new TypeTrnRsV1Cm();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypeTrnRsV1Cm.TrnUID);
/* 45:48 */     TypeStatusV1AggregateHelper.write(paramOutputStream, paramTypeTrnRsV1Cm.Status);
/* 46:49 */     paramOutputStream.write_string(paramTypeTrnRsV1Cm.CltCookie);
/* 47:50 */     paramOutputStream.write_boolean(paramTypeTrnRsV1Cm.CltCookieExists);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeTrnRsV1Cm";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static TypeCode type()
/* 56:   */   {
/* 57:62 */     if (_type == null)
/* 58:   */     {
/* 59:64 */       ORB localORB = ORB.init();
/* 60:65 */       StructMember[] arrayOfStructMember = 
/* 61:66 */         {
/* 62:67 */         new StructMember("TrnUID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 63:68 */         new StructMember("Status", TypeStatusV1AggregateHelper.type(), null), 
/* 64:69 */         new StructMember("CltCookie", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 65:70 */         new StructMember("CltCookieExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypeTrnRsV1Cm", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypeTrnRsV1Cm paramTypeTrnRsV1Cm)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypeTrnRsV1Cm);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypeTrnRsV1Cm extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeTrnRsV1Cm:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1CmHelper
 * JD-Core Version:    0.7.0.1
 */