/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class TypeIntraSyncRsV1SeqHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public TypeIntraSyncRsV1[] value;
/* 12:   */   
/* 13:   */   public TypeIntraSyncRsV1SeqHolder(TypeIntraSyncRsV1[] paramArrayOfTypeIntraSyncRsV1)
/* 14:   */   {
/* 15:22 */     this.value = paramArrayOfTypeIntraSyncRsV1;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return TypeIntraSyncRsV1SeqHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = TypeIntraSyncRsV1SeqHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     TypeIntraSyncRsV1SeqHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypeIntraSyncRsV1SeqHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1SeqHolder
 * JD-Core Version:    0.7.0.1
 */