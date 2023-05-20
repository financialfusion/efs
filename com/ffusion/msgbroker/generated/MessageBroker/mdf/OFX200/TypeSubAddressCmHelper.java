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
/* 12:   */ public abstract class TypeSubAddressCmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeSubAddressCm clone(TypeSubAddressCm paramTypeSubAddressCm)
/* 17:   */   {
/* 18:16 */     if (paramTypeSubAddressCm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeSubAddressCm localTypeSubAddressCm = new TypeSubAddressCm();
/* 22:21 */     localTypeSubAddressCm.Addr2 = paramTypeSubAddressCm.Addr2;
/* 23:22 */     localTypeSubAddressCm.Addr3 = paramTypeSubAddressCm.Addr3;
/* 24:23 */     localTypeSubAddressCm.Addr3Exists = paramTypeSubAddressCm.Addr3Exists;
/* 25:24 */     return localTypeSubAddressCm;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeSubAddressCm read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeSubAddressCm localTypeSubAddressCm = new TypeSubAddressCm();
/* 31:31 */     localTypeSubAddressCm.Addr2 = paramInputStream.read_string();
/* 32:32 */     localTypeSubAddressCm.Addr3 = paramInputStream.read_string();
/* 33:33 */     localTypeSubAddressCm.Addr3Exists = paramInputStream.read_boolean();
/* 34:34 */     return localTypeSubAddressCm;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeSubAddressCm paramTypeSubAddressCm)
/* 38:   */   {
/* 39:41 */     if (paramTypeSubAddressCm == null) {
/* 40:43 */       paramTypeSubAddressCm = new TypeSubAddressCm();
/* 41:   */     }
/* 42:45 */     paramOutputStream.write_string(paramTypeSubAddressCm.Addr2);
/* 43:46 */     paramOutputStream.write_string(paramTypeSubAddressCm.Addr3);
/* 44:47 */     paramOutputStream.write_boolean(paramTypeSubAddressCm.Addr3Exists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeSubAddressCm";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("Addr2", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 60:65 */         new StructMember("Addr3", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 61:66 */         new StructMember("Addr3Exists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeSubAddressCm", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeSubAddressCm paramTypeSubAddressCm)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeSubAddressCm);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeSubAddressCm extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeSubAddressCm:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSubAddressCmHelper
 * JD-Core Version:    0.7.0.1
 */