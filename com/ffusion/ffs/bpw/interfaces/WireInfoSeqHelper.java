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
/* 12:   */ public abstract class WireInfoSeqHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static WireInfo[] clone(WireInfo[] paramArrayOfWireInfo)
/* 17:   */   {
/* 18:16 */     if (paramArrayOfWireInfo == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     int i = paramArrayOfWireInfo.length;
/* 22:21 */     WireInfo[] arrayOfWireInfo = new WireInfo[i];
/* 23:22 */     for (int j = 0; j < i; j++) {
/* 24:24 */       arrayOfWireInfo[j] = ((WireInfo)ObjectVal.readObject(ObjectVal.writeObject(paramArrayOfWireInfo[j])));
/* 25:   */     }
/* 26:26 */     return arrayOfWireInfo;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static WireInfo[] read(InputStream paramInputStream)
/* 30:   */   {
/* 31:32 */     int i = paramInputStream.read_ulong();
/* 32:33 */     WireInfo[] arrayOfWireInfo = new WireInfo[i];
/* 33:34 */     for (int j = 0; j < i; j++) {
/* 34:36 */       arrayOfWireInfo[j] = ((WireInfo)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 35:   */     }
/* 36:38 */     return arrayOfWireInfo;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, WireInfo[] paramArrayOfWireInfo)
/* 40:   */   {
/* 41:45 */     if (paramArrayOfWireInfo == null) {
/* 42:47 */       paramArrayOfWireInfo = new WireInfo[0];
/* 43:   */     }
/* 44:49 */     int i = paramArrayOfWireInfo.length;
/* 45:50 */     paramOutputStream.write_ulong(i);
/* 46:51 */     for (int j = 0; j < i; j++) {
/* 47:53 */       BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramArrayOfWireInfo[j]));
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
/* 61:   */   public static void insert(Any paramAny, WireInfo[] paramArrayOfWireInfo)
/* 62:   */   {
/* 63:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 64:74 */     write(localOutputStream, paramArrayOfWireInfo);
/* 65:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static WireInfo[] extract(Any paramAny)
/* 69:   */   {
/* 70:81 */     if (!paramAny.type().equal(type())) {
/* 71:83 */       throw new BAD_OPERATION();
/* 72:   */     }
/* 73:85 */     return read(paramAny.create_input_stream());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static String id()
/* 77:   */   {
/* 78:90 */     return "IDL:com/ffusion/ffs/bpw/interfaces/WireInfoSeq:1.0";
/* 79:   */   }
/* 80:   */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.WireInfoSeqHelper
 * JD-Core Version:    0.7.0.1
 */