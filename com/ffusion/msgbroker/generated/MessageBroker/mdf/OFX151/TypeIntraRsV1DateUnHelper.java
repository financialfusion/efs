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
/* 12:   */ public abstract class TypeIntraRsV1DateUnHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeIntraRsV1DateUn clone(TypeIntraRsV1DateUn paramTypeIntraRsV1DateUn)
/* 17:   */   {
/* 18:16 */     if (paramTypeIntraRsV1DateUn == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeIntraRsV1DateUn localTypeIntraRsV1DateUn = new TypeIntraRsV1DateUn();
/* 22:21 */     localTypeIntraRsV1DateUn.__preValueTag = paramTypeIntraRsV1DateUn.__preValueTag;
/* 23:22 */     localTypeIntraRsV1DateUn.__memberName = paramTypeIntraRsV1DateUn.__memberName;
/* 24:23 */     localTypeIntraRsV1DateUn.DtXferPrj = paramTypeIntraRsV1DateUn.DtXferPrj;
/* 25:24 */     localTypeIntraRsV1DateUn.DtPosted = paramTypeIntraRsV1DateUn.DtPosted;
/* 26:25 */     return localTypeIntraRsV1DateUn;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeIntraRsV1DateUn read(InputStream paramInputStream)
/* 30:   */   {
/* 31:31 */     TypeIntraRsV1DateUn localTypeIntraRsV1DateUn = new TypeIntraRsV1DateUn();
/* 32:32 */     localTypeIntraRsV1DateUn.__preValueTag = paramInputStream.read_string();
/* 33:33 */     localTypeIntraRsV1DateUn.__memberName = paramInputStream.read_string();
/* 34:34 */     localTypeIntraRsV1DateUn.DtXferPrj = paramInputStream.read_string();
/* 35:35 */     localTypeIntraRsV1DateUn.DtPosted = paramInputStream.read_string();
/* 36:36 */     return localTypeIntraRsV1DateUn;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, TypeIntraRsV1DateUn paramTypeIntraRsV1DateUn)
/* 40:   */   {
/* 41:43 */     if (paramTypeIntraRsV1DateUn == null) {
/* 42:45 */       paramTypeIntraRsV1DateUn = new TypeIntraRsV1DateUn();
/* 43:   */     }
/* 44:47 */     paramOutputStream.write_string(paramTypeIntraRsV1DateUn.__preValueTag);
/* 45:48 */     paramOutputStream.write_string(paramTypeIntraRsV1DateUn.__memberName);
/* 46:49 */     paramOutputStream.write_string(paramTypeIntraRsV1DateUn.DtXferPrj);
/* 47:50 */     paramOutputStream.write_string(paramTypeIntraRsV1DateUn.DtPosted);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeIntraRsV1DateUn";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static TypeCode type()
/* 56:   */   {
/* 57:62 */     if (_type == null)
/* 58:   */     {
/* 59:64 */       ORB localORB = ORB.init();
/* 60:65 */       StructMember[] arrayOfStructMember = 
/* 61:66 */         {
/* 62:67 */         new StructMember("__preValueTag", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 63:68 */         new StructMember("__memberName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 64:69 */         new StructMember("DtXferPrj", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 65:70 */         new StructMember("DtPosted", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 66:   */       
/* 67:72 */       _type = localORB.create_struct_tc(id(), "TypeIntraRsV1DateUn", arrayOfStructMember);
/* 68:   */     }
/* 69:74 */     return _type;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void insert(Any paramAny, TypeIntraRsV1DateUn paramTypeIntraRsV1DateUn)
/* 73:   */   {
/* 74:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 75:82 */     write(localOutputStream, paramTypeIntraRsV1DateUn);
/* 76:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static TypeIntraRsV1DateUn extract(Any paramAny)
/* 80:   */   {
/* 81:89 */     if (!paramAny.type().equal(type())) {
/* 82:91 */       throw new BAD_OPERATION();
/* 83:   */     }
/* 84:93 */     return read(paramAny.create_input_stream());
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static String id()
/* 88:   */   {
/* 89:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraRsV1DateUn:1.0";
/* 90:   */   }
/* 91:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1DateUnHelper
 * JD-Core Version:    0.7.0.1
 */