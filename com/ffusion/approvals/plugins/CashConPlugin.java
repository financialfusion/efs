package com.ffusion.approvals.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.approvals.IApprovalsPlugin;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashCon;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.handlers.ACH;
import java.util.HashMap;

public class CashConPlugin
  implements IApprovalsPlugin
{
  public void initialize(HashMap paramHashMap1, HashMap paramHashMap2)
    throws Throwable
  {}
  
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
    CashCon localCashCon = null;
    localCashCon = (CashCon)paramIApprovable;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", str);
    }
    ACH.processApprovalResult(paramSecureUser, localCashCon, "Approved", paramHashMap);
  }
  
  public void processRejectedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    CashCon localCashCon = null;
    localCashCon = (CashCon)paramIApprovable;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", str);
    }
    ACH.processApprovalResult(paramSecureUser, localCashCon, "Rejected", paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.plugins.CashConPlugin
 * JD-Core Version:    0.7.0.1
 */