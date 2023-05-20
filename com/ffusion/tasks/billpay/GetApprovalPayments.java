package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.approvals.ApprovalsChainItem;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.Billpay;
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

public class GetApprovalPayments
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  protected String datetype;
  protected boolean viewAll = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Payments localPayments = null;
    this.error = 0;
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    if (localPayees == null)
    {
      this.error = 2002;
      str = this.taskErrorURL;
      return str;
    }
    if (localAccounts == null)
    {
      this.error = 2005;
      str = this.taskErrorURL;
      return str;
    }
    if (this.reload)
    {
      this.reload = false;
      localHttpSession.removeAttribute("PendingApprovalPayments");
    }
    localPayments = (Payments)localHttpSession.getAttribute("PendingApprovalPayments");
    if (localPayments == null) {
      try
      {
        localPayments = new Payments();
        localHttpSession.setAttribute("PendingApprovalPayments", localPayments);
        HashMap localHashMap1 = new HashMap();
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        HashMap localHashMap2 = new HashMap();
        if (this.viewAll == true) {
          localHashMap2.put("BusinessID", new Integer(localSecureUser.getBusinessID()));
        } else {
          localHashMap2.put("SubmittingUser", new Integer(localSecureUser.getProfileID()));
        }
        localHashMap2.put("ItemSubType", new Integer(3));
        String[] arrayOfString = { "Pending", "Rejected" };
        ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, arrayOfString, localHashMap2, localHashMap1);
        Payment localPayment;
        Object localObject3;
        if (localApprovalsStatuses != null)
        {
          localIterator = localApprovalsStatuses.iterator();
          while (localIterator.hasNext())
          {
            localObject1 = (ApprovalsStatus)localIterator.next();
            localObject2 = ((ApprovalsStatus)localObject1).getApprovalItem();
            localPayment = (Payment)((TWTransaction)((ApprovalsItem)localObject2).getItem()).getTransaction();
            localPayment.setID(String.valueOf(((ApprovalsItem)localObject2).getItemID()));
            if (((ApprovalsStatus)localObject1).getCurrentChainItem() != null) {
              if (((ApprovalsStatus)localObject1).getCurrentChainItem().isUsingUser())
              {
                localObject3 = new ArrayList();
                ((ArrayList)localObject3).add("" + ((ApprovalsStatus)localObject1).getCurrentChainItem().getGroupOrUserID());
                Object localObject4;
                if (((ApprovalsStatus)localObject1).getCurrentChainItem().getUserType() == 2)
                {
                  localObject4 = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsBankEmployeeDesc", null);
                  localPayment.setApproverName((String)((ILocalizable)localObject4).localize(localSecureUser.getLocale()));
                  localPayment.setApproverIsGroup(false);
                }
                else
                {
                  try
                  {
                    localObject4 = CustomerAdapter.getBusinessEmployeesByIds((ArrayList)localObject3);
                    if (((BusinessEmployees)localObject4).size() > 0)
                    {
                      BusinessEmployee localBusinessEmployee = (BusinessEmployee)((BusinessEmployees)localObject4).get(0);
                      localPayment.setApproverName(localBusinessEmployee.getName());
                      localPayment.setApproverIsGroup(false);
                    }
                  }
                  catch (ProfileException localProfileException) {}
                }
              }
              else if (((ApprovalsStatus)localObject1).getCurrentChainItem().getIsApprovalsGroup())
              {
                localObject3 = Approvals.getApprovalGroups(localSecureUser, localHashMap1);
                ApprovalsGroup localApprovalsGroup = ((ApprovalsGroups)localObject3).getByID(((ApprovalsStatus)localObject1).getCurrentChainItem().getGroupOrUserID());
                localPayment.setApproverName(localApprovalsGroup.getGroupName());
                localPayment.setApproverIsGroup(true);
              }
              else
              {
                localObject3 = Entitlements.getEntitlementGroup(((ApprovalsStatus)localObject1).getCurrentChainItem().getGroupOrUserID());
                localPayment.setApproverName(((EntitlementGroup)localObject3).getGroupName());
                localPayment.setApproverIsGroup(true);
              }
            }
            if ((((ApprovalsStatus)localObject1).getDecision() != null) && (((ApprovalsStatus)localObject1).getDecision().equals("Rejected")))
            {
              localObject3 = ((ApprovalsItem)localObject2).getApprovalItemProperty("RejectReason");
              if ((localObject3 != null) && (((String)localObject3).trim().length() > 0)) {
                localPayment.setRejectReason((String)localObject3);
              }
              localPayment.setStatus(15);
              localPayment.setRecPaymentID(null);
            }
            else if ((((ApprovalsStatus)localObject1).getDecision() != null) && (((ApprovalsStatus)localObject1).getDecision().equals("Hold")))
            {
              localPayment.setStatus(10);
            }
            else
            {
              localPayment.setStatus(9);
            }
            if (localAccounts != null)
            {
              localObject3 = localAccounts.getByID(localPayment.getAccountID());
              if (localObject3 != null) {
                localPayment.setAccount((Account)localObject3);
              }
            }
            localPayments.add(localPayment);
          }
        }
        Iterator localIterator = localPayments.iterator();
        Object localObject1 = (RecPayments)localHttpSession.getAttribute("RecPayments");
        if (localObject1 == null)
        {
          localObject1 = new RecPayments();
          localHttpSession.setAttribute("RecPayments", localObject1);
        }
        Object localObject2 = null;
        while (localIterator.hasNext())
        {
          localPayment = (Payment)localIterator.next();
          localObject3 = localPayment.getRecPaymentID();
          if ((localObject3 != null) && (((String)localObject3).length() != 0))
          {
            localObject2 = ((RecPayments)localObject1).getByRecID((String)localObject3);
            if (localObject2 == null)
            {
              localObject2 = Billpay.getRecPaymentByID(localSecureUser, (String)localObject3, localAccounts, localPayees, localHashMap1);
              if (localObject2 != null) {
                ((RecPayments)localObject1).add(localObject2);
              }
            }
            if (localObject2 != null)
            {
              localPayment.set("Frequency", ((RecPayment)localObject2).getFrequency());
              localPayment.set("NumberPayments", ((RecPayment)localObject2).getNumberPayments());
              localPayment.setPayee(((RecPayment)localObject2).getPayee());
            }
          }
        }
        if (this.datetype != null) {
          localPayments.setDateFormat(this.datetype);
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
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
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
 * Qualified Name:     com.ffusion.tasks.billpay.GetApprovalPayments
 * JD-Core Version:    0.7.0.1
 */