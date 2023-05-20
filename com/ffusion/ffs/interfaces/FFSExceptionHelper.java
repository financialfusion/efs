/*  1:   */ package com.ffusion.ffs.interfaces;
/*  2:   */ 
/*  3:   */ import BCD.BinaryHelper;
/*  4:   */ import com.sybase.CORBA.ObjectVal;
/*  5:   */ import org.omg.CORBA.Any;
/*  6:   */ import org.omg.CORBA.BAD_OPERATION;
/*  7:   */ import org.omg.CORBA.ORB;
/*  8:   */ import org.omg.CORBA.StructMember;
/*  9:   */ import org.omg.CORBA.TypeCode;
/* 10:   */ import org.omg.CORBA.portable.InputStream;
/* 11:   */ import org.omg.CORBA.portable.OutputStream;
/* 12:   */ 
/* 13:   */ public abstract class FFSExceptionHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static FFSException read(InputStream paramInputStream)
/* 18:   */   {
/* 19:16 */     FFSException localFFSException = (FFSException)ObjectVal.readObject(BinaryHelper.read(paramInputStream));
/* 20:17 */     return localFFSException;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static void write(OutputStream paramOutputStream, FFSException paramFFSException)
/* 24:   */   {
/* 25:24 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramFFSException));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static TypeCode type()
/* 29:   */   {
/* 30:31 */     if (_type == null)
/* 31:   */     {
/* 32:33 */       ORB localORB = ORB.init();
/* 33:34 */       StructMember[] arrayOfStructMember = 
/* 34:35 */         {
/* 35:36 */         new StructMember("value", BinaryHelper.type(), null) };
/* 36:   */       
/* 37:38 */       _type = localORB.create_exception_tc(id(), "FFSException", arrayOfStructMember);
/* 38:   */     }
/* 39:40 */     return _type;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static void insert(Any paramAny, FFSException paramFFSException)
/* 43:   */   {
/* 44:47 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 45:48 */     write(localOutputStream, paramFFSException);
/* 46:49 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static FFSException extract(Any paramAny)
/* 50:   */   {
/* 51:55 */     if (!paramAny.type().equal(type())) {
/* 52:57 */       throw new BAD_OPERATION();
/* 53:   */     }
/* 54:59 */     return read(paramAny.create_input_stream());
/* 55:   */   }
/* 56:   */   
/* 57:   */   public static String id()
/* 58:   */   {
/* 59:64 */     return "IDL:com/ffusion/ffs/interfaces/FFSException:1.0";
/* 60:   */   }
/* 61:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.interfaces.FFSExceptionHelper
 * JD-Core Version:    0.7.0.1
 */