package com.ffusion.tasks.approvals;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
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
import com.ffusion.beans.cashcon.CashCon;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.Wire;
import com.ffusion.csil.core.WireUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPendingApprovals
  extends BaseTask
{
  String aOj = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap1 = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap2 = (HashMap)localHttpSession.getAttribute("SearchCriteria");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    try
    {
      localHttpSession.removeAttribute("ApprovalsItems");
      ApprovalsItems localApprovalsItems = null;
      if (localHashMap2 == null) {
        localApprovalsItems = Approvals.getPendingApprovals(localSecureUser, localHashMap1);
      } else {
        localApprovalsItems = Approvals.getPendingApprovals(localSecureUser, localHashMap2, localHashMap1);
      }
      if (localApprovalsItems != null)
      {
        int i = localApprovalsItems.size();
        ApprovalsItem localApprovalsItem = null;
        TWTransaction localTWTransaction = null;
        DateTime localDateTime = null;
        FundsTransaction localFundsTransaction = null;
        for (int j = 0; j < i; j++)
        {
          localApprovalsItem = (ApprovalsItem)localApprovalsItems.get(j);
          if (localApprovalsItem.getSubmittingUserName() == null) {
            throw new CSILException(30211);
          }
          localTWTransaction = (TWTransaction)localApprovalsItem.getItem();
          localDateTime = localTWTransaction.getSubmissionDateTime();
          if (localDateTime != null)
          {
            if (this.aOj != null) {
              localDateTime.setFormat(this.aOj);
            }
          }
          else {
            throw new CSILException(30211);
          }
          if (localTWTransaction.getTypeAsString() == null) {
            throw new CSILException(30211);
          }
          if (localTWTransaction.getAmount().getCurrencyStringNoSymbol() == null) {
            throw new CSILException(30211);
          }
          localFundsTransaction = (FundsTransaction)localTWTransaction.getTransaction();
          if (localFundsTransaction != null)
          {
            Object localObject1;
            String str2;
            Object localObject2;
            if ((localFundsTransaction instanceof ACHBatch))
            {
              localObject1 = (ACHBatch)localFundsTransaction;
              str2 = ((ACHBatch)localObject1).getRecID();
              if ((str2 != null) && (str2.length() != 0))
              {
                localObject2 = new ACHBatch();
                ((ACHBatch)localObject2).set((ACHBatch)localObject1);
                localObject2 = ACH.getRecACHBatch(localSecureUser, (ACHBatch)localObject2, localHashMap1);
                ((ACHBatch)localObject1).setNumberPayments(((ACHBatch)localObject2).getNumberPaymentsValue());
                ((ACHBatch)localObject1).setFrequency(((ACHBatch)localObject2).getFrequency());
                ((ACHBatch)localObject1).put("StartDate", ((ACHBatch)localObject2).get("StartDate"));
              }
            }
            else
            {
              Object localObject3;
              Object localObject4;
              if (((localFundsTransaction instanceof Transfer)) || ((localFundsTransaction instanceof RecTransfer)))
              {
                localObject1 = (Transfer)localFundsTransaction;
                if ((((Transfer)localObject1).getFromAccount().getNumber() == null) || (((Transfer)localObject1).getToAccount().getNumber() == null)) {
                  throw new CSILException(30211);
                }
                ((Transfer)localObject1).setExternalID(((Transfer)localObject1).getID());
                str2 = ((Transfer)localObject1).getRecTransferID();
                if ((str2 != null) && (str2.length() != 0))
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
                    ((Transfer)localObject4).setID(str2);
                    localHashMap1.put("ACCOUNTS", localObject2);
                    localObject3 = Banking.getRecTransferByID(localSecureUser, (Transfer)localObject4, localHashMap1);
                  }
                  else
                  {
                    localObject3 = Banking.getRecTransferByID(localSecureUser, str2, (Accounts)localObject2, localHashMap1);
                  }
                  if (localObject3 != null)
                  {
                    ((Transfer)localObject1).set("Frequency", ((RecTransfer)localObject3).getFrequency());
                    ((Transfer)localObject1).set("NumberTransfers", ((RecTransfer)localObject3).getNumberTransfers());
                  }
                }
              }
              else if (((localFundsTransaction instanceof Payment)) || ((localFundsTransaction instanceof RecPayment)))
              {
                localObject1 = (Payment)localFundsTransaction;
                if ((((Payment)localObject1).getAccount().getNumber() == null) || (((Payment)localObject1).getPayeeName() == null)) {
                  throw new CSILException(30211);
                }
                str2 = ((Payment)localObject1).getRecPaymentID();
                if ((str2 != null) && (str2.length() != 0))
                {
                  localObject2 = null;
                  localObject3 = null;
                  if (localHttpSession.getAttribute("PaymentAccounts") != null) {
                    localObject2 = (Accounts)localHttpSession.getAttribute("PaymentAccounts");
                  }
                  if (localHttpSession.getAttribute("Payees") != null) {
                    localObject3 = (Payees)localHttpSession.getAttribute("Payees");
                  }
                  localObject4 = Billpay.getRecPaymentByID(localSecureUser, str2, (Accounts)localObject2, (Payees)localObject3, localHashMap1);
                  if (localObject4 != null)
                  {
                    ((Payment)localObject1).set("Frequency", ((RecPayment)localObject4).getFrequency());
                    ((Payment)localObject1).set("NumberPayments", ((RecPayment)localObject4).getNumberPayments());
                    ((Payment)localObject1).setAccount(((RecPayment)localObject4).getAccount());
                    ((Payment)localObject1).setPayee(((RecPayment)localObject4).getPayee());
                  }
                }
              }
              else if ((localFundsTransaction instanceof WireTransfer))
              {
                localObject1 = (WireTransfer)localFundsTransaction;
                str2 = ((WireTransfer)localObject1).getRecurringID();
                if ((str2 != null) && (str2.length() != 0))
                {
                  localObject2 = Wire.getRecWireTransferById(localSecureUser, str2, localHashMap1);
                  if (localObject2 != null)
                  {
                    ((WireTransfer)localObject1).setFrequency(((WireTransfer)localObject2).getFrequencyValue());
                    ((WireTransfer)localObject1).setNumberTransfers(((WireTransfer)localObject2).getNumberTransfers());
                  }
                }
                try
                {
                  WireUtil.setWirePayee(localSecureUser, (WireTransfer)localObject1, localHashMap1);
                }
                catch (Throwable localThrowable)
                {
                  ((WireTransfer)localObject1).setWirePayee(null);
                }
              }
              else if ((localFundsTransaction instanceof WireBatch))
              {
                localObject1 = (WireBatch)localFundsTransaction;
                if ((((WireBatch)localObject1).getTotalDebit() == null) && (((WireBatch)localObject1).getTotalCredit() == null)) {
                  throw new CSILException(30211);
                }
              }
              else if (!(localFundsTransaction instanceof CashCon)) {}
            }
          }
        }
        localApprovalsItems.setLocale(localLocale);
        localHttpSession.setAttribute("ApprovalsItems", localApprovalsItems);
      }
    }
    catch (CSILException localCSILException)
    {
      str1 = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str1;
  }
  
  public void setDateFormat(String paramString)
  {
    this.aOj = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetPendingApprovals
 * JD-Core Version:    0.7.0.1
 */