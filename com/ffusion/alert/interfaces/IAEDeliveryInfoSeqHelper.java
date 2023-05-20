/*  1:   */ package com.ffusion.alert.interfaces;
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
/* 12:   */ public abstract class IAEDeliveryInfoSeqHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static IAEDeliveryInfo[] clone(IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo)
/* 17:   */   {
/* 18:16 */     if (paramArrayOfIAEDeliveryInfo == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     int i = paramArrayOfIAEDeliveryInfo.length;
/* 22:21 */     IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = new IAEDeliveryInfo[i];
/* 23:22 */     for (int j = 0; j < i; j++) {
/* 24:24 */       arrayOfIAEDeliveryInfo[j] = ((IAEDeliveryInfo)ObjectVal.readObject(ObjectVal.writeObject(paramArrayOfIAEDeliveryInfo[j])));
/* 25:   */     }
/* 26:26 */     return arrayOfIAEDeliveryInfo;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static IAEDeliveryInfo[] read(InputStream paramInputStream)
/* 30:   */   {
/* 31:32 */     int i = paramInputStream.read_ulong();
/* 32:33 */     IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = new IAEDeliveryInfo[i];
/* 33:34 */     for (int j = 0; j < i; j++) {
/* 34:36 */       arrayOfIAEDeliveryInfo[j] = ((IAEDeliveryInfo)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 35:   */     }
/* 36:38 */     return arrayOfIAEDeliveryInfo;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo)
/* 40:   */   {
/* 41:45 */     if (paramArrayOfIAEDeliveryInfo == null) {
/* 42:47 */       paramArrayOfIAEDeliveryInfo = new IAEDeliveryInfo[0];
/* 43:   */     }
/* 44:49 */     int i = paramArrayOfIAEDeliveryInfo.length;
/* 45:50 */     paramOutputStream.write_ulong(i);
/* 46:51 */     for (int j = 0; j < i; j++) {
/* 47:53 */       BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramArrayOfIAEDeliveryInfo[j]));
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
/* 61:   */   public static void insert(Any paramAny, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo)
/* 62:   */   {
/* 63:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 64:74 */     write(localOutputStream, paramArrayOfIAEDeliveryInfo);
/* 65:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static IAEDeliveryInfo[] extract(Any paramAny)
/* 69:   */   {
/* 70:81 */     if (!paramAny.type().equal(type())) {
/* 71:83 */       throw new BAD_OPERATION();
/* 72:   */     }
/* 73:85 */     return read(paramAny.create_input_stream());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static String id()
/* 77:   */   {
/* 78:90 */     return "IDL:com/ffusion/alert/interfaces/IAEDeliveryInfoSeq:1.0";
/* 79:   */   }
/* 80:   */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEDeliveryInfoSeqHelper
 * JD-Core Version:    0.7.0.1
 */