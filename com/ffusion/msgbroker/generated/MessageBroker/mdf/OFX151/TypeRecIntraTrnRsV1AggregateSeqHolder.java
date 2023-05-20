/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class TypeRecIntraTrnRsV1AggregateSeqHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public TypeRecIntraTrnRsV1Aggregate[] value;
/* 12:   */   
/* 13:   */   public TypeRecIntraTrnRsV1AggregateSeqHolder(TypeRecIntraTrnRsV1Aggregate[] paramArrayOfTypeRecIntraTrnRsV1Aggregate)
/* 14:   */   {
/* 15:22 */     this.value = paramArrayOfTypeRecIntraTrnRsV1Aggregate;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return TypeRecIntraTrnRsV1AggregateSeqHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = TypeRecIntraTrnRsV1AggregateSeqHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     TypeRecIntraTrnRsV1AggregateSeqHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypeRecIntraTrnRsV1AggregateSeqHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1AggregateSeqHolder
 * JD-Core Version:    0.7.0.1
 */