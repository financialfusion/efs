/*  1:   */ package com.sybase.java.lang;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class ObjectSeqHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public Object[] value;
/* 12:   */   
/* 13:   */   public ObjectSeqHolder(Object[] paramArrayOfObject)
/* 14:   */   {
/* 15:22 */     this.value = paramArrayOfObject;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return ObjectSeqHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = ObjectSeqHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     ObjectSeqHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public ObjectSeqHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.sybase.java.lang.ObjectSeqHolder
 * JD-Core Version:    0.7.0.1
 */