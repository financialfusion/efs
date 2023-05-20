/*  1:   */ package com.ffusion.ffs.db;
/*  2:   */ 
/*  3:   */ import BCD.BinaryHelper;
/*  4:   */ import com.ffusion.ffs.config.ConnPoolInfo;
/*  5:   */ import com.ffusion.ffs.util.FFSProperties;
/*  6:   */ import com.sybase.CORBA.ObjectVal;
/*  7:   */ import org.omg.CORBA.Any;
/*  8:   */ import org.omg.CORBA.BAD_OPERATION;
/*  9:   */ import org.omg.CORBA.ORB;
/* 10:   */ import org.omg.CORBA.StructMember;
/* 11:   */ import org.omg.CORBA.TypeCode;
/* 12:   */ import org.omg.CORBA.portable.InputStream;
/* 13:   */ import org.omg.CORBA.portable.OutputStream;
/* 14:   */ 
/* 15:   */ public abstract class FFSDBPropertiesHelper
/* 16:   */ {
/* 17:   */   public static TypeCode _type;
/* 18:   */   
/* 19:   */   public static FFSDBProperties clone(FFSDBProperties paramFFSDBProperties)
/* 20:   */   {
/* 21:16 */     if (paramFFSDBProperties == null) {
/* 22:18 */       return null;
/* 23:   */     }
/* 24:20 */     FFSDBProperties localFFSDBProperties = new FFSDBProperties();
/* 25:21 */     localFFSDBProperties._connInfo = ((ConnPoolInfo)ObjectVal.readObject(ObjectVal.writeObject(paramFFSDBProperties._connInfo)));
/* 26:22 */     localFFSDBProperties._pureProps = ((FFSProperties)ObjectVal.readObject(ObjectVal.writeObject(paramFFSDBProperties._pureProps)));
/* 27:23 */     localFFSDBProperties._connProps = ((FFSProperties)ObjectVal.readObject(ObjectVal.writeObject(paramFFSDBProperties._connProps)));
/* 28:24 */     return localFFSDBProperties;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static FFSDBProperties read(InputStream paramInputStream)
/* 32:   */   {
/* 33:30 */     FFSDBProperties localFFSDBProperties = new FFSDBProperties();
/* 34:31 */     localFFSDBProperties._connInfo = ((ConnPoolInfo)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 35:32 */     localFFSDBProperties._pureProps = ((FFSProperties)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 36:33 */     localFFSDBProperties._connProps = ((FFSProperties)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 37:34 */     return localFFSDBProperties;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static void write(OutputStream paramOutputStream, FFSDBProperties paramFFSDBProperties)
/* 41:   */   {
/* 42:41 */     if (paramFFSDBProperties == null) {
/* 43:43 */       paramFFSDBProperties = new FFSDBProperties();
/* 44:   */     }
/* 45:45 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramFFSDBProperties._connInfo));
/* 46:46 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramFFSDBProperties._pureProps));
/* 47:47 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramFFSDBProperties._connProps));
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static String _idl()
/* 51:   */   {
/* 52:52 */     return "com::ffusion::ffs::db::FFSDBProperties";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static TypeCode type()
/* 56:   */   {
/* 57:59 */     if (_type == null)
/* 58:   */     {
/* 59:61 */       ORB localORB = ORB.init();
/* 60:62 */       StructMember[] arrayOfStructMember = 
/* 61:63 */         {
/* 62:64 */         new StructMember("_connInfo", BinaryHelper.type(), null), 
/* 63:65 */         new StructMember("_pureProps", BinaryHelper.type(), null), 
/* 64:66 */         new StructMember("_connProps", BinaryHelper.type(), null) };
/* 65:   */       
/* 66:68 */       _type = localORB.create_struct_tc(id(), "FFSDBProperties", arrayOfStructMember);
/* 67:   */     }
/* 68:70 */     return _type;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static void insert(Any paramAny, FFSDBProperties paramFFSDBProperties)
/* 72:   */   {
/* 73:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 74:78 */     write(localOutputStream, paramFFSDBProperties);
/* 75:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 76:   */   }
/* 77:   */   
/* 78:   */   public static FFSDBProperties extract(Any paramAny)
/* 79:   */   {
/* 80:85 */     if (!paramAny.type().equal(type())) {
/* 81:87 */       throw new BAD_OPERATION();
/* 82:   */     }
/* 83:89 */     return read(paramAny.create_input_stream());
/* 84:   */   }
/* 85:   */   
/* 86:   */   public static String id()
/* 87:   */   {
/* 88:94 */     return "IDL:com/ffusion/ffs/db/FFSDBProperties:1.0";
/* 89:   */   }
/* 90:   */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.db.FFSDBPropertiesHelper
 * JD-Core Version:    0.7.0.1
 */