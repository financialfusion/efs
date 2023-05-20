package com.ffusion.approvals.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.approvals.IApprovalsPlugin;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.handlers.ACH;
import com.ffusion.csil.handlers.HandlerUtil;
import com.ffusion.services.Banking4;
import java.util.HashMap;

public class TransfersPlugin
  implements IApprovalsPlugin
{
  private final String jdField_char = "INIT_URL";
  private final String jdField_byte = "Banking";
  private HashMap jdField_else = null;
  private String jdField_case = null;
  
  public void initialize(HashMap paramHashMap1, HashMap paramHashMap2)
    throws Throwable
  {
    this.jdField_else = HandlerUtil.getServiceSettings(paramHashMap1, "Banking", "com.ffusion.approvals.plugins.BankingPlugin.initialize", 30202);
    this.jdField_case = ((String)this.jdField_else.get("INIT_URL"));
    if (this.jdField_case == null) {
      this.jdField_case = "";
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
    Transfer localTransfer = null;
    localTransfer = (Transfer)paramIApprovable;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", str);
    }
    ACH.processApprovalResult(paramSecureUser, localTransfer, "Approved", paramHashMap);
  }
  
  public void processRejectedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    Transfer localTransfer = null;
    localTransfer = (Transfer)paramIApprovable;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", str);
    }
    ACH.processApprovalResult(paramSecureUser, localTransfer, "Rejected", paramHashMap);
  }
  
  private Banking4 jdMethod_do()
    throws CSILException
  {
    Banking4 localBanking4 = (Banking4)HandlerUtil.instantiateService(this.jdField_else, "com.ffusion.approvals.plugins.BankingPlugin.initBankingService", 30202);
    try
    {
      localBanking4.initialize(this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      CSILException localCSILException = null;
      if ((localThrowable instanceof CSILException)) {
        localCSILException = (CSILException)localThrowable;
      } else if ((localThrowable instanceof Exception)) {
        localCSILException = new CSILException(30202, (Exception)localThrowable);
      } else {
        localCSILException = new CSILException(30202, new Exception(localThrowable.toString()));
      }
      throw localCSILException;
    }
    return localBanking4;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.plugins.TransfersPlugin
 * JD-Core Version:    0.7.0.1
 */