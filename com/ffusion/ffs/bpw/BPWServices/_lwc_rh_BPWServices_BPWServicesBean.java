/*  1:   */ package com.ffusion.ffs.bpw.BPWServices;
/*  2:   */ 
/*  3:   */ import com.sybase.ejb.lwc.EJBHome;
/*  4:   */ import java.rmi.RemoteException;
/*  5:   */ import javax.ejb.CreateException;
/*  6:   */ 
/*  7:   */ public class _lwc_rh_BPWServices_BPWServicesBean
/*  8:   */   extends EJBHome
/*  9:   */   implements BPWServicesHome
/* 10:   */ {
/* 11:   */   public BPWServices create()
/* 12:   */     throws RemoteException, CreateException
/* 13:   */   {
/* 14:16 */     return new _lwc_rs_BPWServices_BPWServicesBean(null);
/* 15:   */   }
/* 16:   */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices._lwc_rh_BPWServices_BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */