package com.ffusion.services;

import com.ffusion.approvals.ApprovalsException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.beans.approvals.ApprovalsLevels;
import java.util.HashMap;

public abstract interface IApprovalsService2
  extends IApprovalsService
{
  public abstract ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt1, int paramInt2, String paramString1, String paramString2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems getConsumerPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void removeObject(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isObjectInUse(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IApprovalsService2
 * JD-Core Version:    0.7.0.1
 */