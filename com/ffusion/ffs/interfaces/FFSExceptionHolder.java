/*  1:   */ package com.ffusion.ffs.interfaces;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class FFSExceptionHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public FFSException value;
/* 12:   */   
/* 13:   */   public FFSExceptionHolder(FFSException paramFFSException)
/* 14:   */   {
/* 15:22 */     this.value = paramFFSException;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return FFSExceptionHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = FFSExceptionHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     FFSExceptionHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public FFSExceptionHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.interfaces.FFSExceptionHolder
 * JD-Core Version:    0.7.0.1
 */