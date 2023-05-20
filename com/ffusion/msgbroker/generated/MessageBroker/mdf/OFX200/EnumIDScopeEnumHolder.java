/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class EnumIDScopeEnumHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public EnumIDScopeEnum value;
/* 12:   */   
/* 13:   */   public EnumIDScopeEnumHolder(EnumIDScopeEnum paramEnumIDScopeEnum)
/* 14:   */   {
/* 15:22 */     this.value = paramEnumIDScopeEnum;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return EnumIDScopeEnumHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = EnumIDScopeEnumHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     EnumIDScopeEnumHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public EnumIDScopeEnumHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnumHolder
 * JD-Core Version:    0.7.0.1
 */