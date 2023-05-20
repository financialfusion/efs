package com.ffusion.approvals.adapter;

import com.ffusion.approvals.ApprovalsException;
import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsChainItems;
import com.ffusion.beans.approvals.ApprovalsDecisions;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroupMembers;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsHistory;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsItemCounts;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import java.util.HashMap;

public abstract interface IApprovalsAdapter
{
  public abstract void initialize(HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsLevels getWorkflowLevels(int paramInt, String paramString, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsLevels getWorkflowLevels(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsLevels getWorkflowLevels(int paramInt1, int paramInt2, String paramString1, String paramString2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void addWorkflowLevel(ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void updateWorkflowLevels(ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void removeWorkflowLevels(ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsChainItems getWorkflowChainItems(ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void setWorkflowChainItems(ApprovalsLevel paramApprovalsLevel, ApprovalsChainItems paramApprovalsChainItems, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void clearWorkflowChainItems(ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsStatuses getPendingItems(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract ApprovalsStatuses getSubmittedItems(int paramInt, String[] paramArrayOfString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract int getNumberOfPendingApprovals(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract int getNumberOfPendingApprovals(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract ApprovalsItemCounts getNumberOfPendingApprovalsDetail(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems getPendingApprovals(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems getPendingApprovals(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract ApprovalsItems getPendingApprovalsForBank(int paramInt1, int paramInt2, int paramInt3, int paramInt4, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItem createApprovalItem(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, int paramInt3, IApprovable paramIApprovable, String[] paramArrayOfString, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItem createApprovalItem(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, int paramInt3, IApprovable paramIApprovable, String[] paramArrayOfString1, String[] paramArrayOfString2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItem modifyApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void removeApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems submitDecisions(int paramInt1, int paramInt2, int paramInt3, ApprovalsDecisions paramApprovalsDecisions, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void removeBusiness(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isUserInUse(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isBusinessInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems removeUser(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isGroupInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems removeGroup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void cleanup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void setItemsFailedExecution(ApprovalsItems paramApprovalsItems, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void setItemsFailedRejection(ApprovalsItems paramApprovalsItems, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsHistory getItemHistory(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract boolean isUserApprover(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void addApprovalGroup(ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems removeApprovalGroup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsGroups getApprovalGroups(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void updateApprovalGroup(ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isApprovalGroupInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsGroupMembers getApprovalGroupMembers(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void assignApprovalGroups(int paramInt, ApprovalsGroups paramApprovalsGroups, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsGroups getApprovalGroupsForUser(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isObjectInUse(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void removeObject(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems getConsumerPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.adapter.IApprovalsAdapter
 * JD-Core Version:    0.7.0.1
 */