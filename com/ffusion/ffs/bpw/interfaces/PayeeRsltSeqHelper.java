/*  1:   */ package com.ffusion.ffs.bpw.interfaces;
/*  2:   */ 
/*  3:   */ import BCD.BinaryHelper;
/*  4:   */ import com.sybase.CORBA.ObjectVal;
/*  5:   */ import org.omg.CORBA.Any;
/*  6:   */ import org.omg.CORBA.BAD_OPERATION;
/*  7:   */ import org.omg.CORBA.ORB;
/*  8:   */ import org.omg.CORBA.TypeCode;
/*  9:   */ import org.omg.CORBA.portable.InputStream;
/* 10:   */ import org.omg.CORBA.portable.OutputStream;
/* 11:   */ 
/* 12:   */ public abstract class PayeeRsltSeqHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static PayeeRslt[] clone(PayeeRslt[] paramArrayOfPayeeRslt)
/* 17:   */   {
/* 18:16 */     if (paramArrayOfPayeeRslt == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     int i = paramArrayOfPayeeRslt.length;
/* 22:21 */     PayeeRslt[] arrayOfPayeeRslt = new PayeeRslt[i];
/* 23:22 */     for (int j = 0; j < i; j++) {
/* 24:24 */       arrayOfPayeeRslt[j] = ((PayeeRslt)ObjectVal.readObject(ObjectVal.writeObject(paramArrayOfPayeeRslt[j])));
/* 25:   */     }
/* 26:26 */     return arrayOfPayeeRslt;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static PayeeRslt[] read(InputStream paramInputStream)
/* 30:   */   {
/* 31:32 */     int i = paramInputStream.read_ulong();
/* 32:33 */     PayeeRslt[] arrayOfPayeeRslt = new PayeeRslt[i];
/* 33:34 */     for (int j = 0; j < i; j++) {
/* 34:36 */       arrayOfPayeeRslt[j] = ((PayeeRslt)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 35:   */     }
/* 36:38 */     return arrayOfPayeeRslt;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, PayeeRslt[] paramArrayOfPayeeRslt)
/* 40:   */   {
/* 41:45 */     if (paramArrayOfPayeeRslt == null) {
/* 42:47 */       paramArrayOfPayeeRslt = new PayeeRslt[0];
/* 43:   */     }
/* 44:49 */     int i = paramArrayOfPayeeRslt.length;
/* 45:50 */     paramOutputStream.write_ulong(i);
/* 46:51 */     for (int j = 0; j < i; j++) {
/* 47:53 */       BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramArrayOfPayeeRslt[j]));
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static TypeCode type()
/* 52:   */   {
/* 53:61 */     if (_type == null)
/* 54:   */     {
/* 55:63 */       ORB localORB = ORB.init();
/* 56:64 */       _type = localORB.create_sequence_tc(0, BinaryHelper.type());
/* 57:   */     }
/* 58:66 */     return _type;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public static void insert(Any paramAny, PayeeRslt[] paramArrayOfPayeeRslt)
/* 62:   */   {
/* 63:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 64:74 */     write(localOutputStream, paramArrayOfPayeeRslt);
/* 65:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static PayeeRslt[] extract(Any paramAny)
/* 69:   */   {
/* 70:81 */     if (!paramAny.type().equal(type())) {
/* 71:83 */       throw new BAD_OPERATION();
/* 72:   */     }
/* 73:85 */     return read(paramAny.create_input_stream());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static String id()
/* 77:   */   {
/* 78:90 */     return "IDL:com/ffusion/ffs/bpw/interfaces/PayeeRsltSeq:1.0";
/* 79:   */   }
/* 80:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PayeeRsltSeqHelper
 * JD-Core Version:    0.7.0.1
 */