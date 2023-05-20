package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.approvals.ApprovalsChainItem;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApprovalTransfers
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  protected String datetype;
  protected String destination;
  protected boolean viewAll = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Transfers localTransfers = null;
    this.error = 0;
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BankingAccounts");
    if (localAccounts == null)
    {
      this.error = 1001;
      str = this.taskErrorURL;
      return str;
    }
    if (this.reload)
    {
      this.reload = false;
      localHttpSession.removeAttribute("PendingApprovalTransfers");
    }
    localTransfers = (Transfers)localHttpSession.getAttribute("PendingApprovalTransfers");
    if (localTransfers == null) {
      try
      {
        localTransfers = new Transfers();
        localHttpSession.setAttribute("PendingApprovalTransfers", localTransfers);
        HashMap localHashMap1 = new HashMap();
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        HashMap localHashMap2 = new HashMap();
        if (this.viewAll == true) {
          localHashMap2.put("BusinessID", new Integer(localSecureUser.getBusinessID()));
        } else {
          localHashMap2.put("SubmittingUser", new Integer(localSecureUser.getProfileID()));
        }
        localHashMap2.put("ItemSubType", new Integer(1));
        String[] arrayOfString = { "Pending", "Rejected" };
        ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, arrayOfString, localHashMap2, localHashMap1);
        Object localObject4;
        Object localObject5;
        if (localApprovalsStatuses != null)
        {
          localObject1 = (Accounts)localHttpSession.getAttribute("TransferAccounts");
          localObject2 = localApprovalsStatuses.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject3 = (ApprovalsStatus)((Iterator)localObject2).next();
            localObject4 = ((ApprovalsStatus)localObject3).getApprovalItem();
            localObject5 = (Transfer)((TWTransaction)((ApprovalsItem)localObject4).getItem()).getTransaction();
            ((Transfer)localObject5).setExternalID(((Transfer)localObject5).getID());
            ((Transfer)localObject5).setID(String.valueOf(((ApprovalsItem)localObject4).getItemID()));
            if (((ApprovalsStatus)localObject3).getCurrentChainItem() != null) {
              if (((ApprovalsStatus)localObject3).getCurrentChainItem().isUsingUser())
              {
                localObject6 = new ArrayList();
                ((ArrayList)localObject6).add("" + ((ApprovalsStatus)localObject3).getCurrentChainItem().getGroupOrUserID());
                Object localObject7;
                if (((ApprovalsStatus)localObject3).getCurrentChainItem().getUserType() == 2)
                {
                  localObject7 = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsBankEmployeeDesc", null);
                  ((Transfer)localObject5).setApproverName((String)((ILocalizable)localObject7).localize(localSecureUser.getLocale()));
                  ((Transfer)localObject5).setApproverIsGroup(false);
                }
                else
                {
                  try
                  {
                    localObject7 = CustomerAdapter.getBusinessEmployeesByIds((ArrayList)localObject6);
                    if (((BusinessEmployees)localObject7).size() > 0)
                    {
                      BusinessEmployee localBusinessEmployee = (BusinessEmployee)((BusinessEmployees)localObject7).get(0);
                      ((Transfer)localObject5).setApproverName(localBusinessEmployee.getName());
                      ((Transfer)localObject5).setApproverIsGroup(false);
                    }
                  }
                  catch (ProfileException localProfileException1) {}
                }
              }
              else if (((ApprovalsStatus)localObject3).getCurrentChainItem().getIsApprovalsGroup())
              {
                localObject6 = Approvals.getApprovalGroups(localSecureUser, localHashMap1);
                localObject8 = ((ApprovalsGroups)localObject6).getByID(((ApprovalsStatus)localObject3).getCurrentChainItem().getGroupOrUserID());
                ((Transfer)localObject5).setApproverName(((ApprovalsGroup)localObject8).getGroupName());
                ((Transfer)localObject5).setApproverIsGroup(true);
              }
              else
              {
                localObject6 = Entitlements.getEntitlementGroup(((ApprovalsStatus)localObject3).getCurrentChainItem().getGroupOrUserID());
                ((Transfer)localObject5).setApproverName(((EntitlementGroup)localObject6).getGroupName());
                ((Transfer)localObject5).setApproverIsGroup(true);
              }
            }
            Object localObject6 = ((Transfer)localObject5).getSubmittedBy();
            Object localObject8 = null;
            Object localObject10;
            Object localObject9;
            if ((localObject6 != null) && (((String)localObject6).length() > 0))
            {
              localObject8 = localObject6;
              try
              {
                int i = Integer.parseInt((String)localObject6);
                localObject10 = CustomerAdapter.getUserById(i);
                localObject8 = ((User)localObject10).getName();
              }
              catch (ProfileException localProfileException2) {}
            }
            else
            {
              localObject9 = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsBankEmployeeDesc", null);
              localObject8 = (String)((ILocalizable)localObject9).localize(localSecureUser.getLocale());
            }
            ((Transfer)localObject5).set("USERNAME", (String)localObject8);
            if ((((ApprovalsStatus)localObject3).getDecision() != null) && (((ApprovalsStatus)localObject3).getDecision().equals("Rejected")))
            {
              localObject9 = ((ApprovalsItem)localObject4).getApprovalItemProperty("RejectReason");
              if ((localObject9 != null) && (((String)localObject9).trim().length() > 0)) {
                ((Transfer)localObject5).setRejectReason((String)localObject9);
              }
              ((Transfer)localObject5).setType(1);
              ((Transfer)localObject5).setRecTransferID(null);
              ((Transfer)localObject5).setStatus(12);
            }
            else if ((((ApprovalsStatus)localObject3).getDecision() != null) && (((ApprovalsStatus)localObject3).getDecision().equals("Hold")))
            {
              ((Transfer)localObject5).setStatus(9);
            }
            else
            {
              ((Transfer)localObject5).setStatus(8);
            }
            if (localObject1 != null)
            {
              localObject9 = ((Accounts)localObject1).getByID(((Transfer)localObject5).getFromAccountID());
              if (localObject9 != null) {
                ((Transfer)localObject5).setFromAccount((Account)localObject9);
              }
              localObject10 = ((Accounts)localObject1).getByID(((Transfer)localObject5).getToAccountID());
              if (localObject10 != null) {
                ((Transfer)localObject5).setToAccount((Account)localObject10);
              }
            }
            localTransfers.add(localObject5);
          }
        }
        Object localObject1 = localTransfers.iterator();
        Object localObject2 = (RecTransfers)localHttpSession.getAttribute("RecTransfers");
        if (localObject2 == null)
        {
          localObject2 = new RecTransfers();
          localHttpSession.setAttribute("RecTransfers", localObject2);
        }
        Object localObject3 = null;
        while (((Iterator)localObject1).hasNext())
        {
          localObject4 = (Transfer)((Iterator)localObject1).next();
          localObject5 = ((Transfer)localObject4).getRecTransferID();
          if ((localObject5 != null) && (((String)localObject5).length() != 0))
          {
            localObject3 = (RecTransfer)((RecTransfers)localObject2).getByID((String)localObject5);
            if (localObject3 == null)
            {
              localObject3 = Banking.getRecTransferByID(localSecureUser, (String)localObject5, localAccounts, localHashMap1);
              if (localObject3 != null) {
                ((RecTransfers)localObject2).add(localObject3);
              }
            }
            if (localObject3 != null)
            {
              ((Transfer)localObject4).set("Frequency", ((RecTransfer)localObject3).getFrequency());
              ((Transfer)localObject4).set("NumberTransfers", ((RecTransfer)localObject3).getNumberTransfers());
            }
          }
        }
        if (this.datetype != null) {
          localTransfers.setDateFormat(this.datetype);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDestination(String paramString)
  {
    this.destination = paramString;
  }
  
  public void setViewAll(String paramString)
  {
    this.viewAll = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getViewAll()
  {
    return String.valueOf(this.viewAll);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetApprovalTransfers
 * JD-Core Version:    0.7.0.1
 */