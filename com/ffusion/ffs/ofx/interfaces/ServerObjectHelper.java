/*  1:   */ package com.ffusion.ffs.ofx.interfaces;
/*  2:   */ 
/*  3:   */ import BCD.BinaryHelper;
/*  4:   */ import com.sybase.CORBA.ObjectVal;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import org.omg.CORBA.Any;
/*  7:   */ import org.omg.CORBA.BAD_OPERATION;
/*  8:   */ import org.omg.CORBA.ORB;
/*  9:   */ import org.omg.CORBA.StructMember;
/* 10:   */ import org.omg.CORBA.TCKind;
/* 11:   */ import org.omg.CORBA.TypeCode;
/* 12:   */ import org.omg.CORBA.portable.InputStream;
/* 13:   */ import org.omg.CORBA.portable.OutputStream;
/* 14:   */ 
/* 15:   */ public abstract class ServerObjectHelper
/* 16:   */ {
/* 17:   */   public static TypeCode _type;
/* 18:   */   
/* 19:   */   public static ServerObject clone(ServerObject paramServerObject)
/* 20:   */   {
/* 21:16 */     if (paramServerObject == null) {
/* 22:18 */       return null;
/* 23:   */     }
/* 24:20 */     ServerObject localServerObject = new ServerObject();
/* 25:21 */     localServerObject._anonymousSon = paramServerObject._anonymousSon;
/* 26:22 */     localServerObject._msgSets = ((ArrayList)ObjectVal.readObject(ObjectVal.writeObject(paramServerObject._msgSets)));
/* 27:23 */     return localServerObject;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static ServerObject read(InputStream paramInputStream)
/* 31:   */   {
/* 32:29 */     ServerObject localServerObject = new ServerObject();
/* 33:30 */     localServerObject._anonymousSon = paramInputStream.read_boolean();
/* 34:31 */     localServerObject._msgSets = ((ArrayList)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 35:32 */     return localServerObject;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static void write(OutputStream paramOutputStream, ServerObject paramServerObject)
/* 39:   */   {
/* 40:39 */     if (paramServerObject == null) {
/* 41:41 */       paramServerObject = new ServerObject();
/* 42:   */     }
/* 43:43 */     paramOutputStream.write_boolean(paramServerObject._anonymousSon);
/* 44:44 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramServerObject._msgSets));
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String _idl()
/* 48:   */   {
/* 49:49 */     return "com::ffusion::ffs::ofx::interfaces::ServerObject";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static TypeCode type()
/* 53:   */   {
/* 54:56 */     if (_type == null)
/* 55:   */     {
/* 56:58 */       ORB localORB = ORB.init();
/* 57:59 */       StructMember[] arrayOfStructMember = 
/* 58:60 */         {
/* 59:61 */         new StructMember("_anonymousSon", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 60:62 */         new StructMember("_msgSets", BinaryHelper.type(), null) };
/* 61:   */       
/* 62:64 */       _type = localORB.create_struct_tc(id(), "ServerObject", arrayOfStructMember);
/* 63:   */     }
/* 64:66 */     return _type;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static void insert(Any paramAny, ServerObject paramServerObject)
/* 68:   */   {
/* 69:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 70:74 */     write(localOutputStream, paramServerObject);
/* 71:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static ServerObject extract(Any paramAny)
/* 75:   */   {
/* 76:81 */     if (!paramAny.type().equal(type())) {
/* 77:83 */       throw new BAD_OPERATION();
/* 78:   */     }
/* 79:85 */     return read(paramAny.create_input_stream());
/* 80:   */   }
/* 81:   */   
/* 82:   */   public static String id()
/* 83:   */   {
/* 84:90 */     return "IDL:com/ffusion/ffs/ofx/interfaces/ServerObject:1.0";
/* 85:   */   }
/* 86:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.ofx.interfaces.ServerObjectHelper
 * JD-Core Version:    0.7.0.1
 */