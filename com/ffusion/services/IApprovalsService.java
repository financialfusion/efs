package com.ffusion.services;

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
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import java.util.HashMap;

public abstract interface IApprovalsService
{
  public abstract void initialize(HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void addWorkflowLevel(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void updateWorkflowLevels(SecureUser paramSecureUser, ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void removeWorkflowLevels(SecureUser paramSecureUser, ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsChainItems getWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void setWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, ApprovalsChainItems paramApprovalsChainItems, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void clearWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsStatuses getPendingItems(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract ApprovalsStatuses getSubmittedItems(int paramInt, String[] paramArrayOfString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract int getNumberOfPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract ApprovalsItemCounts getNumberOfPendingApprovalsDetail(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems getPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract ApprovalsItems getPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItem createApprovalItem(SecureUser paramSecureUser, int paramInt, IApprovable paramIApprovable, String[] paramArrayOfString1, String[] paramArrayOfString2, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItem modifyApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems submitDecisions(SecureUser paramSecureUser, ApprovalsDecisions paramApprovalsDecisions, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void removeApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void removeBusiness(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isUserInUse(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems removeUser(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isGroupInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isBusinessInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems removeGroup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsHistory getItemHistory(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException;
  
  public abstract boolean isUserApprover(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void addApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsItems removeApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void updateApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract boolean isApprovalGroupInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsGroupMembers getApprovalGroupMembers(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void assignApprovalGroups(SecureUser paramSecureUser, int paramInt, ApprovalsGroups paramApprovalsGroups, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsGroups getApprovalGroups(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract ApprovalsGroups getApprovalGroupsForUser(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract BusinessEmployees getEntitledBusinessEmployees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract EntitlementGroups getEntitledGroups(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ApprovalsException;
  
  public abstract void cleanup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IApprovalsService
 * JD-Core Version:    0.7.0.1
 */