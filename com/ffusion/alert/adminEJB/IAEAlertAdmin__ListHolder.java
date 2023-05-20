/*  1:   */ package com.ffusion.alert.adminEJB;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.TypeCode;
/*  4:   */ import org.omg.CORBA.portable.InputStream;
/*  5:   */ import org.omg.CORBA.portable.OutputStream;
/*  6:   */ import org.omg.CORBA.portable.Streamable;
/*  7:   */ 
/*  8:   */ public final class IAEAlertAdmin__ListHolder
/*  9:   */   implements Streamable
/* 10:   */ {
/* 11:   */   public IAEAlertAdmin[] value;
/* 12:   */   
/* 13:   */   public IAEAlertAdmin__ListHolder(IAEAlertAdmin[] paramArrayOfIAEAlertAdmin)
/* 14:   */   {
/* 15:22 */     this.value = paramArrayOfIAEAlertAdmin;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TypeCode _type()
/* 19:   */   {
/* 20:27 */     return IAEAlertAdmin__ListHelper.type();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void _read(InputStream paramInputStream)
/* 24:   */   {
/* 25:33 */     this.value = IAEAlertAdmin__ListHelper.read(paramInputStream);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void _write(OutputStream paramOutputStream)
/* 29:   */   {
/* 30:39 */     IAEAlertAdmin__ListHelper.write(paramOutputStream, this.value);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public IAEAlertAdmin__ListHolder() {}
/* 34:   */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.IAEAlertAdmin__ListHolder
 * JD-Core Version:    0.7.0.1
 */