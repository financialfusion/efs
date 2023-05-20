package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsChainItem;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.PagingBaseTask;
import com.ffusion.util.FilteredList;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GetApprovalCashCons
  extends PagingBaseTask
  implements Task
{
  public GetApprovalCashCons()
  {
    this.sortedBy = "SUBMITDATE";
    this.collectionName = "PendingApprovalCashCons";
  }
  
  public FilteredList getPaged(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = com.ffusion.csil.core.CashCon.getPagedApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    updateApprovalInfo(paramSecureUser, localCashCons);
    return localCashCons;
  }
  
  public FilteredList getNext(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = com.ffusion.csil.core.CashCon.getNextApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    updateApprovalInfo(paramSecureUser, localCashCons);
    return localCashCons;
  }
  
  public FilteredList getPrevious(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = com.ffusion.csil.core.CashCon.getPreviousApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    updateApprovalInfo(paramSecureUser, localCashCons);
    return localCashCons;
  }
  
  public void updateApprovalInfo(SecureUser paramSecureUser, CashCons paramCashCons)
  {
    try
    {
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      int i = 11;
      if (!this.viewAll) {
        localHashMap2.put("SubmittingUser", new Integer(paramSecureUser.getProfileID()));
      }
      localHashMap2.put("ItemSubType", new Integer(i));
      String[] arrayOfString = { "Pending", "Rejected" };
      ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, arrayOfString, localHashMap2, localHashMap1);
      if (localApprovalsStatuses != null)
      {
        Iterator localIterator = localApprovalsStatuses.iterator();
        while (localIterator.hasNext())
        {
          ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)localIterator.next();
          ApprovalsItem localApprovalsItem = localApprovalsStatus.getApprovalItem();
          com.ffusion.beans.cashcon.CashCon localCashCon = (com.ffusion.beans.cashcon.CashCon)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
          localCashCon.setID(String.valueOf(localApprovalsItem.getItemID()));
          Object localObject1;
          if (localApprovalsStatus.getCurrentChainItem() != null) {
            if (localApprovalsStatus.getCurrentChainItem().isUsingUser())
            {
              localObject1 = new ArrayList();
              ((ArrayList)localObject1).add("" + localApprovalsStatus.getCurrentChainItem().getGroupOrUserID());
              Object localObject2;
              if (localApprovalsStatus.getCurrentChainItem().getUserType() == 2)
              {
                localObject2 = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsBankEmployeeDesc", null);
                localCashCon.setApproverName((String)((ILocalizable)localObject2).localize(paramSecureUser.getLocale()));
                localCashCon.setApproverIsGroup(false);
              }
              else
              {
                try
                {
                  localObject2 = CustomerAdapter.getBusinessEmployeesByIds((ArrayList)localObject1);
                  if (((BusinessEmployees)localObject2).size() > 0)
                  {
                    BusinessEmployee localBusinessEmployee = (BusinessEmployee)((BusinessEmployees)localObject2).get(0);
                    localCashCon.setApproverName(localBusinessEmployee.getName());
                    localCashCon.setApproverIsGroup(false);
                  }
                }
                catch (ProfileException localProfileException) {}
              }
            }
            else
            {
              localObject1 = Entitlements.getEntitlementGroup(localApprovalsStatus.getCurrentChainItem().getGroupOrUserID());
              localCashCon.setApproverName(((EntitlementGroup)localObject1).getGroupName());
              localCashCon.setApproverIsGroup(true);
            }
          }
          if ((localApprovalsStatus.getDecision() != null) && (localApprovalsStatus.getDecision().equals("Rejected")))
          {
            localObject1 = localApprovalsItem.getApprovalItemProperty("RejectReason");
            if ((localObject1 != null) && (((String)localObject1).trim().length() > 0)) {
              localCashCon.setRejectReason((String)localObject1);
            }
            localCashCon.setStatus(4);
            localCashCon.setCanDelete("false");
          }
          else if ((localApprovalsStatus.getDecision() != null) && (localApprovalsStatus.getDecision().equals("Hold")))
          {
            localCashCon.setStatus(3);
          }
          else
          {
            localCashCon.setStatus(1);
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetApprovalCashCons
 * JD-Core Version:    0.7.0.1
 */