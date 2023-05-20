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
/* 12:   */ public abstract class TypeSyncRsV1CmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeSyncRsV1Cm clone(TypeSyncRsV1Cm paramTypeSyncRsV1Cm)
/* 17:   */   {
/* 18:16 */     if (paramTypeSyncRsV1Cm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeSyncRsV1Cm localTypeSyncRsV1Cm = new TypeSyncRsV1Cm();
/* 22:21 */     localTypeSyncRsV1Cm.Token = paramTypeSyncRsV1Cm.Token;
/* 23:22 */     localTypeSyncRsV1Cm.LostSync = paramTypeSyncRsV1Cm.LostSync;
/* 24:23 */     localTypeSyncRsV1Cm.LostSyncExists = paramTypeSyncRsV1Cm.LostSyncExists;
/* 25:24 */     return localTypeSyncRsV1Cm;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeSyncRsV1Cm read(InputStream paramInputStream)
/* 29:   */   {
/* 30:30 */     TypeSyncRsV1Cm localTypeSyncRsV1Cm = new TypeSyncRsV1Cm();
/* 31:31 */     localTypeSyncRsV1Cm.Token = paramInputStream.read_string();
/* 32:32 */     localTypeSyncRsV1Cm.LostSync = paramInputStream.read_boolean();
/* 33:33 */     localTypeSyncRsV1Cm.LostSyncExists = paramInputStream.read_boolean();
/* 34:34 */     return localTypeSyncRsV1Cm;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void write(OutputStream paramOutputStream, TypeSyncRsV1Cm paramTypeSyncRsV1Cm)
/* 38:   */   {
/* 39:41 */     if (paramTypeSyncRsV1Cm == null) {
/* 40:43 */       paramTypeSyncRsV1Cm = new TypeSyncRsV1Cm();
/* 41:   */     }
/* 42:45 */     paramOutputStream.write_string(paramTypeSyncRsV1Cm.Token);
/* 43:46 */     paramOutputStream.write_boolean(paramTypeSyncRsV1Cm.LostSync);
/* 44:47 */     paramOutputStream.write_boolean(paramTypeSyncRsV1Cm.LostSyncExists);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeSyncRsV1Cm";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:59 */     if (_type == null)
/* 55:   */     {
/* 56:61 */       ORB localORB = ORB.init();
/* 57:62 */       StructMember[] arrayOfStructMember = 
/* 58:63 */         {
/* 59:64 */         new StructMember("Token", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 60:65 */         new StructMember("LostSync", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 61:66 */         new StructMember("LostSyncExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 62:   */       
/* 63:68 */       _type = localORB.create_struct_tc(id(), "TypeSyncRsV1Cm", arrayOfStructMember);
/* 64:   */     }
/* 65:70 */     return _type;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void insert(Any paramAny, TypeSyncRsV1Cm paramTypeSyncRsV1Cm)
/* 69:   */   {
/* 70:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 71:78 */     write(localOutputStream, paramTypeSyncRsV1Cm);
/* 72:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static TypeSyncRsV1Cm extract(Any paramAny)
/* 76:   */   {
/* 77:85 */     if (!paramAny.type().equal(type())) {
/* 78:87 */       throw new BAD_OPERATION();
/* 79:   */     }
/* 80:89 */     return read(paramAny.create_input_stream());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static String id()
/* 84:   */   {
/* 85:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeSyncRsV1Cm:1.0";
/* 86:   */   }
/* 87:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRsV1CmHelper
 * JD-Core Version:    0.7.0.1
 */