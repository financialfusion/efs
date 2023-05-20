/*  1:   */ package com.ffusion.ffs.bpw.BPWServices;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class BPWServicesHomeHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public BPWServicesHome value;
/* 12:   */   
/* 13:   */   public BPWServicesHomeHolder(BPWServicesHome paramBPWServicesHome)
/* 14:   */   {
/* 15:22 */     this.value = paramBPWServicesHome;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return BPWServicesHomeHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = BPWServicesHomeHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     BPWServicesHomeHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public BPWServicesHomeHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.BPWServicesHomeHolder
 * JD-Core Version:    0.7.0.1
 */