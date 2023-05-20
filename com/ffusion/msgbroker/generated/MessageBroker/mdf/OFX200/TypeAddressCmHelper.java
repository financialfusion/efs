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
/* 12:   */ public abstract class TypeAddressCmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeAddressCm clone(TypeAddressCm paramTypeAddressCm)
/* 17:   */   {
/* 18:16 */     if (paramTypeAddressCm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeAddressCm localTypeAddressCm = new TypeAddressCm();
/* 22:21 */     localTypeAddressCm.Addr1 = paramTypeAddressCm.Addr1;
/* 23:22 */     localTypeAddressCm.SubAddressCm = TypeSubAddressCmHelper.clone(paramTypeAddressCm.SubAddressCm);
/* 24:23 */     localTypeAddressCm.SubAddressCmExists = paramTypeAddressCm.SubAddressCmExists;
/* 25:24 */     return localTypeAddressCm;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeAddressCm read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeAddressCm localTypeAddressCm = new TypeAddressCm();
/* 31:31 */     localTypeAddressCm.Addr1 = paramInputStream.read_string();
/* 32:32 */     localTypeAddressCm.SubAddressCm = TypeSubAddressCmHelper.read(paramInputStream);
/* 33:33 */     localTypeAddressCm.SubAddressCmExists = paramInputStream.read_boolean();
/* 34:34 */     return localTypeAddressCm;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeAddressCm paramTypeAddressCm)
/* 38:   */   {
/* 39:41 */     if (paramTypeAddressCm == null) {
/* 40:43 */       paramTypeAddressCm = new TypeAddressCm();
/* 41:   */     }
/* 42:45 */     paramOutputStream.write_string(paramTypeAddressCm.Addr1);
/* 43:46 */     TypeSubAddressCmHelper.write(paramOutputStream, paramTypeAddressCm.SubAddressCm);
/* 44:47 */     paramOutputStream.write_boolean(paramTypeAddressCm.SubAddressCmExists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeAddressCm";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("Addr1", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 60:65 */         new StructMember("SubAddressCm", TypeSubAddressCmHelper.type(), null), 
/* 61:66 */         new StructMember("SubAddressCmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeAddressCm", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeAddressCm paramTypeAddressCm)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeAddressCm);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeAddressCm extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeAddressCm:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAddressCmHelper
 * JD-Core Version:    0.7.0.1
 */