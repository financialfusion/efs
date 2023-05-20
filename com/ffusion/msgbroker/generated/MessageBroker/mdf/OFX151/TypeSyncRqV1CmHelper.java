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
/* 12:   */ public abstract class TypeSyncRqV1CmHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static TypeSyncRqV1Cm clone(TypeSyncRqV1Cm paramTypeSyncRqV1Cm)
/* 17:   */   {
/* 18:16 */     if (paramTypeSyncRqV1Cm == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     TypeSyncRqV1Cm localTypeSyncRqV1Cm = new TypeSyncRqV1Cm();
/* 22:21 */     localTypeSyncRqV1Cm.TokenRqV1Un = TypeTokenRqV1UnHelper.clone(paramTypeSyncRqV1Cm.TokenRqV1Un);
/* 23:22 */     localTypeSyncRqV1Cm.RejectIfMissing = paramTypeSyncRqV1Cm.RejectIfMissing;
/* 24:23 */     return localTypeSyncRqV1Cm;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static TypeSyncRqV1Cm read(InputStream paramInputStream)
/* 28:   */   {
/* 29:29 */     TypeSyncRqV1Cm localTypeSyncRqV1Cm = new TypeSyncRqV1Cm();
/* 30:30 */     localTypeSyncRqV1Cm.TokenRqV1Un = TypeTokenRqV1UnHelper.read(paramInputStream);
/* 31:31 */     localTypeSyncRqV1Cm.RejectIfMissing = paramInputStream.read_boolean();
/* 32:32 */     return localTypeSyncRqV1Cm;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static void write(OutputStream paramOutputStream, TypeSyncRqV1Cm paramTypeSyncRqV1Cm)
/* 36:   */   {
/* 37:39 */     if (paramTypeSyncRqV1Cm == null) {
/* 38:41 */       paramTypeSyncRqV1Cm = new TypeSyncRqV1Cm();
/* 39:   */     }
/* 40:43 */     TypeTokenRqV1UnHelper.write(paramOutputStream, paramTypeSyncRqV1Cm.TokenRqV1Un);
/* 41:44 */     paramOutputStream.write_boolean(paramTypeSyncRqV1Cm.RejectIfMissing);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static String _idl()
/* 45:   */   {
/* 46:49 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeSyncRqV1Cm";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TypeCode type()
/* 50:   */   {
/* 51:56 */     if (_type == null)
/* 52:   */     {
/* 53:58 */       ORB localORB = ORB.init();
/* 54:59 */       StructMember[] arrayOfStructMember = 
/* 55:60 */         {
/* 56:61 */         new StructMember("TokenRqV1Un", TypeTokenRqV1UnHelper.type(), null), 
/* 57:62 */         new StructMember("RejectIfMissing", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 58:   */       
/* 59:64 */       _type = localORB.create_struct_tc(id(), "TypeSyncRqV1Cm", arrayOfStructMember);
/* 60:   */     }
/* 61:66 */     return _type;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static void insert(Any paramAny, TypeSyncRqV1Cm paramTypeSyncRqV1Cm)
/* 65:   */   {
/* 66:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 67:74 */     write(localOutputStream, paramTypeSyncRqV1Cm);
/* 68:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static TypeSyncRqV1Cm extract(Any paramAny)
/* 72:   */   {
/* 73:81 */     if (!paramAny.type().equal(type())) {
/* 74:83 */       throw new BAD_OPERATION();
/* 75:   */     }
/* 76:85 */     return read(paramAny.create_input_stream());
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static String id()
/* 80:   */   {
/* 81:90 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeSyncRqV1Cm:1.0";
/* 82:   */   }
/* 83:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRqV1CmHelper
 * JD-Core Version:    0.7.0.1
 */