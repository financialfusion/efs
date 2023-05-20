/*  1:   */ package com.ffusion.alert.adminEJB;
/*  2:   */ 
/*  3:   */ import com.sybase.ejb.lwc.EJBHome;
/*  4:   */ import java.rmi.RemoteException;
/*  5:   */ import javax.ejb.CreateException;
/*  6:   */ 
/*  7:   */ public class _lwc_rh_UAEAdminEJB20_UAEAdminBean
/*  8:   */   extends EJBHome
/*  9:   */   implements IAEAlertAdminHome
/* 10:   */ {
/* 11:   */   public IAEAlertAdmin create()
/* 12:   */     throws RemoteException, CreateException
/* 13:   */   {
/* 14:16 */     return new _lwc_rs_UAEAdminEJB20_UAEAdminBean(null);
/* 15:   */   }
/* 16:   */ }


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB._lwc_rh_UAEAdminEJB20_UAEAdminBean
 * JD-Core Version:    0.7.0.1
 */