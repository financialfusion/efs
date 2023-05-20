/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class TypeRecPmtTrnRsUnHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public TypeRecPmtTrnRsUn value;
/* 12:   */   
/* 13:   */   public TypeRecPmtTrnRsUnHolder(TypeRecPmtTrnRsUn paramTypeRecPmtTrnRsUn)
/* 14:   */   {
/* 15:22 */     this.value = paramTypeRecPmtTrnRsUn;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return TypeRecPmtTrnRsUnHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = TypeRecPmtTrnRsUnHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     TypeRecPmtTrnRsUnHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypeRecPmtTrnRsUnHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUnHolder
 * JD-Core Version:    0.7.0.1
 */