/*  1:   */ package com.ffusion.ffs.bpw.interfaces;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class RecTransferInfoSeqHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public RecTransferInfo[] value;
/* 12:   */   
/* 13:   */   public RecTransferInfoSeqHolder(RecTransferInfo[] paramArrayOfRecTransferInfo)
/* 14:   */   {
/* 15:22 */     this.value = paramArrayOfRecTransferInfo;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return RecTransferInfoSeqHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = RecTransferInfoSeqHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     RecTransferInfoSeqHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public RecTransferInfoSeqHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RecTransferInfoSeqHolder
 * JD-Core Version:    0.7.0.1
 */