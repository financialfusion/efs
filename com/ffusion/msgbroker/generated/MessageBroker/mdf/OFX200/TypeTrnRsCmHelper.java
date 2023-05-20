/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
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
/* 12:   */ public abstract class TypeTrnRsCmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeTrnRsCm clone(TypeTrnRsCm paramTypeTrnRsCm)
/* 17:   */   {
/* 18:16 */     if (paramTypeTrnRsCm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeTrnRsCm localTypeTrnRsCm = new TypeTrnRsCm();
/* 22:21 */     localTypeTrnRsCm.TrnUID = paramTypeTrnRsCm.TrnUID;
/* 23:22 */     localTypeTrnRsCm.Status = TypeStatusAggregateHelper.clone(paramTypeTrnRsCm.Status);
/* 24:23 */     localTypeTrnRsCm.CltCookie = paramTypeTrnRsCm.CltCookie;
/* 25:24 */     localTypeTrnRsCm.CltCookieExists = paramTypeTrnRsCm.CltCookieExists;
/* 26:25 */     return localTypeTrnRsCm;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeTrnRsCm read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypeTrnRsCm localTypeTrnRsCm = new TypeTrnRsCm();
/* 32:32 */     localTypeTrnRsCm.TrnUID = paramInputStream.read_string();
/* 33:33 */     localTypeTrnRsCm.Status = TypeStatusAggregateHelper.read(paramInputStream);
/* 34:34 */     localTypeTrnRsCm.CltCookie = paramInputStream.read_string();
/* 35:35 */     localTypeTrnRsCm.CltCookieExists = paramInputStream.read_boolean();
/* 36:36 */     return localTypeTrnRsCm;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypeTrnRsCm paramTypeTrnRsCm)
/* 40:   */   {
/* 41:43 */     if (paramTypeTrnRsCm == null) {
/* 42:45 */       paramTypeTrnRsCm = new TypeTrnRsCm();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypeTrnRsCm.TrnUID);
/* 45:48 */     TypeStatusAggregateHelper.write(paramOutputStream, paramTypeTrnRsCm.Status);
/* 46:49 */     paramOutputStream.write_string(paramTypeTrnRsCm.CltCookie);
/* 47:50 */     paramOutputStream.write_boolean(paramTypeTrnRsCm.CltCookieExists);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeTrnRsCm";
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
/* 63:68 */         new StructMember("Status", TypeStatusAggregateHelper.type(), null), 
/* 64:69 */         new StructMember("CltCookie", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 65:70 */         new StructMember("CltCookieExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypeTrnRsCm", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypeTrnRsCm paramTypeTrnRsCm)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypeTrnRsCm);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypeTrnRsCm extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeTrnRsCm:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCmHelper
 * JD-Core Version:    0.7.0.1
 */