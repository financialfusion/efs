/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;
/*  2:   */ 
/*  3:   */ import com.sybase.ejb.lwc.EJBHome;
/*  4:   */ import java.rmi.RemoteException;
/*  5:   */ import javax.ejb.CreateException;
/*  6:   */ 
/*  7:   */ public class _lwc_rh_OFX151BPWServices_OFX151BPWServicesBean
/*  8:   */   extends EJBHome
/*  9:   */   implements OFX151BPWServicesHome
/* 10:   */ {
/* 11:   */   public IOFX151BPWServices create()
/* 12:   */     throws RemoteException, CreateException
/* 13:   */   {
/* 14:16 */     return new _lwc_rs_OFX151BPWServices_OFX151BPWServicesBean(null);
/* 15:   */   }
/* 16:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices._lwc_rh_OFX151BPWServices_OFX151BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */