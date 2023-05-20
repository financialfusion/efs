/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class TypeRecPmtTrnRsV1Holder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public TypeRecPmtTrnRsV1 value;
/* 12:   */   
/* 13:   */   public TypeRecPmtTrnRsV1Holder(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1)
/* 14:   */   {
/* 15:22 */     this.value = paramTypeRecPmtTrnRsV1;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return TypeRecPmtTrnRsV1Helper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = TypeRecPmtTrnRsV1Helper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     TypeRecPmtTrnRsV1Helper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypeRecPmtTrnRsV1Holder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Holder
 * JD-Core Version:    0.7.0.1
 */