/*  1:   */ package com.ffusion.alert.interfaces;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class IAEDeliveryInfoSeqHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public IAEDeliveryInfo[] value;
/* 12:   */   
/* 13:   */   public IAEDeliveryInfoSeqHolder(IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo)
/* 14:   */   {
/* 15:22 */     this.value = paramArrayOfIAEDeliveryInfo;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return IAEDeliveryInfoSeqHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = IAEDeliveryInfoSeqHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     IAEDeliveryInfoSeqHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public IAEDeliveryInfoSeqHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEDeliveryInfoSeqHolder
 * JD-Core Version:    0.7.0.1
 */