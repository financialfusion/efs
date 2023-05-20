/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class TypeIntraTrnRsV1UnHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public TypeIntraTrnRsV1Un value;
/* 12:   */   
/* 13:   */   public TypeIntraTrnRsV1UnHolder(TypeIntraTrnRsV1Un paramTypeIntraTrnRsV1Un)
/* 14:   */   {
/* 15:22 */     this.value = paramTypeIntraTrnRsV1Un;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return TypeIntraTrnRsV1UnHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = TypeIntraTrnRsV1UnHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     TypeIntraTrnRsV1UnHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypeIntraTrnRsV1UnHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1UnHolder
 * JD-Core Version:    0.7.0.1
 */