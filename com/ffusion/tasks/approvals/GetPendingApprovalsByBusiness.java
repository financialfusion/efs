package com.ffusion.tasks.approvals;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.business.PendingTransaction;
import com.ffusion.beans.business.PendingTransactions;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.Business;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPendingApprovalsByBusiness
  extends BaseTask
  implements IApprovalsTask
{
  private String aN1 = "ApprovalsItems";
  private String aN0 = null;
  private String aNZ = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PendingTransactions localPendingTransactions = null;
    if ((!ae(this.aN0)) && (!ae(this.aNZ)))
    {
      this.error = 4130;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    try
    {
      String str2 = null;
      boolean bool = ae(this.aNZ);
      if (bool == true) {
        str2 = this.aNZ;
      } else {
        str2 = this.aN0;
      }
      localPendingTransactions = Business.getPendingTransactionsByBusiness(localSecureUser, bool, str2, localHashMap);
      ApprovalsItems localApprovalsItems = new ApprovalsItems(Locale.getDefault());
      ApprovalsItem localApprovalsItem = null;
      TWTransaction localTWTransaction = null;
      FundsTransaction localFundsTransaction = null;
      for (int i = 0; i < localPendingTransactions.size(); i++)
      {
        PendingTransaction localPendingTransaction = (PendingTransaction)localPendingTransactions.get(i);
        localApprovalsItem = localPendingTransaction.getApprovalsItem();
        localTWTransaction = (TWTransaction)localApprovalsItem.getItem();
        localFundsTransaction = (FundsTransaction)localTWTransaction.getTransaction();
        Object localObject1;
        String str3;
        Object localObject2;
        Object localObject3;
        Object localObject4;
        if (((localFundsTransaction instanceof Payment)) || ((localFundsTransaction instanceof RecPayment)))
        {
          localObject1 = (Payment)localFundsTransaction;
          if ((((Payment)localObject1).getAccount().getNumber() == null) || (((Payment)localObject1).getPayeeName() == null)) {
            throw new CSILException(30211);
          }
          str3 = ((Payment)localObject1).getRecPaymentID();
          if ((str3 != null) && (str3.length() != 0))
          {
            localObject2 = null;
            localObject3 = null;
            if (localHttpSession.getAttribute("PaymentAccounts") != null) {
              localObject2 = (Accounts)localHttpSession.getAttribute("PaymentAccounts");
            }
            if (localHttpSession.getAttribute("Payees") != null) {
              localObject3 = (Payees)localHttpSession.getAttribute("Payees");
            }
            localObject4 = Billpay.getRecPaymentByID(localSecureUser, str3, (Accounts)localObject2, (Payees)localObject3, localHashMap);
            if (localObject4 != null)
            {
              ((Payment)localObject1).set("Frequency", ((RecPayment)localObject4).getFrequency());
              ((Payment)localObject1).set("NumberPayments", ((RecPayment)localObject4).getNumberPayments());
              ((Payment)localObject1).setAccount(((RecPayment)localObject4).getAccount());
              ((Payment)localObject1).setPayee(((RecPayment)localObject4).getPayee());
            }
          }
        }
        else if (((localFundsTransaction instanceof Transfer)) || ((localFundsTransaction instanceof RecTransfer)))
        {
          localObject1 = (Transfer)localFundsTransaction;
          ((Transfer)localObject1).setExternalID(((Transfer)localObject1).getID());
          str3 = ((Transfer)localObject1).getRecTransferID();
          if ((str3 != null) && (str3.length() != 0))
          {
            localObject2 = null;
            if (localHttpSession.getAttribute("TransferAccounts") != null) {
              localObject2 = (Accounts)localHttpSession.getAttribute("TransferAccounts");
            }
            localObject3 = null;
            if (((Transfer)localObject1).getTransferDestination().equals("ITOE"))
            {
              localObject4 = new Transfer();
              ((Transfer)localObject4).setTransferDestination("ITOE");
              ((Transfer)localObject4).setID(str3);
              localHashMap.put("ACCOUNTS", localObject2);
              localObject3 = Banking.getRecTransferByID(localSecureUser, (Transfer)localObject4, localHashMap);
            }
            else
            {
              localObject3 = Banking.getRecTransferByID(localSecureUser, str3, (Accounts)localObject2, localHashMap);
            }
            if (localObject3 != null)
            {
              ((Transfer)localObject1).set("Frequency", ((RecTransfer)localObject3).getFrequency());
              ((Transfer)localObject1).set("NumberTransfers", ((RecTransfer)localObject3).getNumberTransfers());
            }
          }
        }
        else if ((localFundsTransaction instanceof WireTransfer))
        {
          localObject1 = (WireTransfer)localFundsTransaction;
          str3 = ((WireTransfer)localObject1).getRecurringID();
          if ((str3 != null) && (str3.length() != 0))
          {
            localObject2 = Wire.getRecWireTransferById(localSecureUser, str3, localHashMap);
            if (localObject2 != null)
            {
              ((WireTransfer)localObject1).setFrequency(((WireTransfer)localObject2).getFrequencyValue());
              ((WireTransfer)localObject1).setNumberTransfers(((WireTransfer)localObject2).getNumberTransfers());
            }
          }
        }
        else if ((localFundsTransaction instanceof ACHBatch))
        {
          localObject1 = (ACHBatch)localFundsTransaction;
          str3 = ((ACHBatch)localObject1).getRecID();
          if ((str3 != null) && (str3.length() != 0))
          {
            localObject2 = new ACHBatch();
            ((ACHBatch)localObject2).set((ACHBatch)localObject1);
            localObject2 = ACH.getRecACHBatch(localSecureUser, (ACHBatch)localObject2, localHashMap);
            ((ACHBatch)localObject1).setNumberPayments(((ACHBatch)localObject2).getNumberPaymentsValue());
            ((ACHBatch)localObject1).setFrequency(((ACHBatch)localObject2).getFrequency());
            ((ACHBatch)localObject1).put("StartDate", ((ACHBatch)localObject2).get("StartDate"));
          }
        }
        localApprovalsItems.add(localPendingTransaction.getApprovalsItem());
      }
      localApprovalsItems.setLocale(localLocale);
      localHttpSession.setAttribute(this.aN1, localApprovalsItems);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setBusinessID(String paramString)
  {
    this.aNZ = paramString;
    this.aN0 = null;
  }
  
  public String getBusinessID()
  {
    return this.aNZ;
  }
  
  public void setBusinessName(String paramString)
  {
    this.aN0 = paramString;
    this.aNZ = null;
  }
  
  public String getBusinessName()
  {
    return this.aN0;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.aN1 = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.aN1;
  }
  
  private boolean ae(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() != 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetPendingApprovalsByBusiness
 * JD-Core Version:    0.7.0.1
 */