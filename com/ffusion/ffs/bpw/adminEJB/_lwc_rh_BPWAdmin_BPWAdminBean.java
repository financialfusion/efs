/*  1:   */ package com.ffusion.ffs.bpw.adminEJB;
/*  2:   */ 
/*  3:   */ import com.sybase.ejb.lwc.EJBHome;
/*  4:   */ import java.rmi.RemoteException;
/*  5:   */ import javax.ejb.CreateException;
/*  6:   */ 
/*  7:   */ public class _lwc_rh_BPWAdmin_BPWAdminBean
/*  8:   */   extends EJBHome
/*  9:   */   implements BPWAdminHome
/* 10:   */ {
/* 11:   */   public IBPWAdmin create()
/* 12:   */     throws RemoteException, CreateException
/* 13:   */   {
/* 14:16 */     return new _lwc_rs_BPWAdmin_BPWAdminBean(null);
/* 15:   */   }
/* 16:   */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB._lwc_rh_BPWAdmin_BPWAdminBean
 * JD-Core Version:    0.7.0.1
 */