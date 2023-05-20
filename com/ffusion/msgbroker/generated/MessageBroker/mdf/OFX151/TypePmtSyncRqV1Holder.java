/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class TypePmtSyncRqV1Holder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public TypePmtSyncRqV1 value;
/* 12:   */   
/* 13:   */   public TypePmtSyncRqV1Holder(TypePmtSyncRqV1 paramTypePmtSyncRqV1)
/* 14:   */   {
/* 15:22 */     this.value = paramTypePmtSyncRqV1;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return TypePmtSyncRqV1Helper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = TypePmtSyncRqV1Helper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     TypePmtSyncRqV1Helper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypePmtSyncRqV1Holder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1Holder
 * JD-Core Version:    0.7.0.1
 */