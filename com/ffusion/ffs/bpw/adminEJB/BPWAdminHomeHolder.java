/*  1:   */ package com.ffusion.ffs.bpw.adminEJB;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class BPWAdminHomeHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public BPWAdminHome value;
/* 12:   */   
/* 13:   */   public BPWAdminHomeHolder(BPWAdminHome paramBPWAdminHome)
/* 14:   */   {
/* 15:22 */     this.value = paramBPWAdminHome;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return BPWAdminHomeHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = BPWAdminHomeHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     BPWAdminHomeHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public BPWAdminHomeHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.BPWAdminHomeHolder
 * JD-Core Version:    0.7.0.1
 */