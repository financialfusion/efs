package com.ffusion.approvals.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.approvals.IApprovalsPlugin;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.handlers.ACH;
import com.ffusion.csil.handlers.HandlerUtil;
import com.ffusion.services.BillPay4;
import java.util.HashMap;

public class PaymentsPlugin
  implements IApprovalsPlugin
{
  private final String jdField_new = "INIT_URL";
  private final String jdField_int = "Billpay";
  private HashMap jdField_for = null;
  private String jdField_try = null;
  
  public void initialize(HashMap paramHashMap1, HashMap paramHashMap2)
    throws Throwable
  {
    this.jdField_for = HandlerUtil.getServiceSettings(paramHashMap1, "Billpay", "com.ffusion.approvals.plugins.BillpayPlugin.initialize", 30201);
    this.jdField_try = ((String)this.jdField_for.get("INIT_URL"));
    if (this.jdField_try == null) {
      this.jdField_try = "";
    }
  }
  
  public boolean isUserEntitledToApprove(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    return true;
  }
  
  public Limits checkApprovalLimits(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    return null;
  }
  
  public void rollbackApprovalLimits(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {}
  
  public void processApprovedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    Payment localPayment = null;
    localPayment = (Payment)paramIApprovable;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", str);
    }
    ACH.processApprovalResult(paramSecureUser, localPayment, "Approved", paramHashMap);
  }
  
  public void processRejectedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    Payment localPayment = null;
    localPayment = (Payment)paramIApprovable;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", str);
    }
    ACH.processApprovalResult(paramSecureUser, localPayment, "Rejected", paramHashMap);
  }
  
  private BillPay4 jdMethod_if()
    throws CSILException
  {
    BillPay4 localBillPay4 = (BillPay4)HandlerUtil.instantiateService(this.jdField_for, "com.ffusion.approvals.plugins.BankingPlugin.initBillpayService", 30201);
    try
    {
      localBillPay4.initialize(this.jdField_try);
    }
    catch (Throwable localThrowable)
    {
      CSILException localCSILException = null;
      if ((localThrowable instanceof CSILException)) {
        localCSILException = (CSILException)localThrowable;
      } else if ((localThrowable instanceof Exception)) {
        localCSILException = new CSILException(30201, (Exception)localThrowable);
      } else {
        localCSILException = new CSILException(30201, new Exception(localThrowable.toString()));
      }
      throw localCSILException;
    }
    return localBillPay4;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.plugins.PaymentsPlugin
 * JD-Core Version:    0.7.0.1
 */