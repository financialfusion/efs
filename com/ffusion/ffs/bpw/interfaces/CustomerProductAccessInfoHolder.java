/*  1:   */ package com.ffusion.ffs.bpw.interfaces;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class CustomerProductAccessInfoHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public CustomerProductAccessInfo value;
/* 12:   */   
/* 13:   */   public CustomerProductAccessInfoHolder(CustomerProductAccessInfo paramCustomerProductAccessInfo)
/* 14:   */   {
/* 15:22 */     this.value = paramCustomerProductAccessInfo;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return CustomerProductAccessInfoHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = CustomerProductAccessInfoHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     CustomerProductAccessInfoHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public CustomerProductAccessInfoHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfoHolder
 * JD-Core Version:    0.7.0.1
 */