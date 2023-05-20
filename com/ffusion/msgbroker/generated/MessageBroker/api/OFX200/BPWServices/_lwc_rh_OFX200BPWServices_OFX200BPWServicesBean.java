/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices;
/*  2:   */ 
/*  3:   */ import com.sybase.ejb.lwc.EJBHome;
/*  4:   */ import java.rmi.RemoteException;
/*  5:   */ import javax.ejb.CreateException;
/*  6:   */ 
/*  7:   */ public class _lwc_rh_OFX200BPWServices_OFX200BPWServicesBean
/*  8:   */   extends EJBHome
/*  9:   */   implements OFX200BPWServicesHome
/* 10:   */ {
/* 11:   */   public IOFX200BPWServices create()
/* 12:   */     throws RemoteException, CreateException
/* 13:   */   {
/* 14:16 */     return new _lwc_rs_OFX200BPWServices_OFX200BPWServicesBean(null);
/* 15:   */   }
/* 16:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices._lwc_rh_OFX200BPWServices_OFX200BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */