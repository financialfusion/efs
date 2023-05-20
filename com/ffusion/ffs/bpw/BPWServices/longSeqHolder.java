/*  1:   */ package com.ffusion.ffs.bpw.BPWServices;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class longSeqHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public int[] value;
/* 12:   */   
/* 13:   */   public longSeqHolder(int[] paramArrayOfInt)
/* 14:   */   {
/* 15:22 */     this.value = paramArrayOfInt;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return longSeqHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = longSeqHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     longSeqHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public longSeqHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.longSeqHolder
 * JD-Core Version:    0.7.0.1
 */