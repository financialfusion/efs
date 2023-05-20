/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class TypeIntraRsV1DateUnHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public TypeIntraRsV1DateUn value;
/* 12:   */   
/* 13:   */   public TypeIntraRsV1DateUnHolder(TypeIntraRsV1DateUn paramTypeIntraRsV1DateUn)
/* 14:   */   {
/* 15:22 */     this.value = paramTypeIntraRsV1DateUn;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return TypeIntraRsV1DateUnHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = TypeIntraRsV1DateUnHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     TypeIntraRsV1DateUnHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypeIntraRsV1DateUnHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1DateUnHolder
 * JD-Core Version:    0.7.0.1
 */