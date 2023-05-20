/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class EnumCurrencyEnumHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public EnumCurrencyEnum value;
/* 12:   */   
/* 13:   */   public EnumCurrencyEnumHolder(EnumCurrencyEnum paramEnumCurrencyEnum)
/* 14:   */   {
/* 15:22 */     this.value = paramEnumCurrencyEnum;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return EnumCurrencyEnumHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = EnumCurrencyEnumHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     EnumCurrencyEnumHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public EnumCurrencyEnumHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnumHolder
 * JD-Core Version:    0.7.0.1
 */