/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class TypeRecIntraTrnRsV1AggregateHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public TypeRecIntraTrnRsV1Aggregate value;
/* 12:   */   
/* 13:   */   public TypeRecIntraTrnRsV1AggregateHolder(TypeRecIntraTrnRsV1Aggregate paramTypeRecIntraTrnRsV1Aggregate)
/* 14:   */   {
/* 15:22 */     this.value = paramTypeRecIntraTrnRsV1Aggregate;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return TypeRecIntraTrnRsV1AggregateHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = TypeRecIntraTrnRsV1AggregateHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     TypeRecIntraTrnRsV1AggregateHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypeRecIntraTrnRsV1AggregateHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1AggregateHolder
 * JD-Core Version:    0.7.0.1
 */