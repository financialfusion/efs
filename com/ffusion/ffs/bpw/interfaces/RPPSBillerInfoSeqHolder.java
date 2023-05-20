/*  1:   */ package com.ffusion.ffs.bpw.interfaces;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class RPPSBillerInfoSeqHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public RPPSBillerInfo[] value;
/* 12:   */   
/* 13:   */   public RPPSBillerInfoSeqHolder(RPPSBillerInfo[] paramArrayOfRPPSBillerInfo)
/* 14:   */   {
/* 15:22 */     this.value = paramArrayOfRPPSBillerInfo;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return RPPSBillerInfoSeqHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = RPPSBillerInfoSeqHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     RPPSBillerInfoSeqHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public RPPSBillerInfoSeqHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RPPSBillerInfoSeqHolder
 * JD-Core Version:    0.7.0.1
 */