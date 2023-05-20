package com.ffusion.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.beans.entitlements.Limits;
import java.util.HashMap;

public abstract interface IApprovalsPlugin
{
  public abstract void initialize(HashMap paramHashMap1, HashMap paramHashMap2)
    throws Throwable;
  
  public abstract boolean isUserEntitledToApprove(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable;
  
  public abstract Limits checkApprovalLimits(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable;
  
  public abstract void rollbackApprovalLimits(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable;
  
  public abstract void processApprovedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable;
  
  public abstract void processRejectedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.IApprovalsPlugin
 * JD-Core Version:    0.7.0.1
 */