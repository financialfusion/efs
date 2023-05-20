package com.ffusion.tasks.billpay;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeletePayee
  extends BaseTask
  implements Task
{
  protected String payeeID;
  protected String validate = null;
  protected boolean processFlag = false;
  protected boolean checkForOutstandingPayments = true;
  protected boolean checkForSavedPayments = true;
  
  public void setCheckForOutstandingPayments(String paramString)
  {
    if ("false".equalsIgnoreCase(paramString)) {
      this.checkForOutstandingPayments = false;
    } else {
      this.checkForOutstandingPayments = true;
    }
  }
  
  public void setCheckForSavedPayments(String paramString)
  {
    if ("true".equalsIgnoreCase(paramString)) {
      this.checkForSavedPayments = true;
    } else {
      this.checkForSavedPayments = false;
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    Payments localPayments = (Payments)localHttpSession.getAttribute("Payments");
    RecPayments localRecPayments = (RecPayments)localHttpSession.getAttribute("RecPayments");
    String str = null;
    if (localPayees == null)
    {
      this.error = 2002;
      return this.taskErrorURL;
    }
    Payee localPayee = localPayees.getByID(this.payeeID);
    this.error = validateInput(localHttpSession, localPayee, localPayments, localRecPayments);
    if (this.error == 0)
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        this.error = doProcess(localHttpSession, localPayee);
        if (this.error == 0) {
          str = this.successURL;
        } else {
          str = this.serviceErrorURL;
        }
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected int validateInput(HttpSession paramHttpSession, Payee paramPayee, Payments paramPayments, RecPayments paramRecPayments)
  {
    int i = 0;
    if (this.validate != null)
    {
      Object localObject1;
      Object localObject2;
      if (paramPayments == null)
      {
        i = 2003;
      }
      else if (paramRecPayments == null)
      {
        i = 2004;
      }
      else if (paramPayee == null)
      {
        i = 2013;
      }
      else if (this.checkForOutstandingPayments == true)
      {
        localObject1 = paramPayments.getScheduledPaymentsToPayee(paramPayee);
        localObject2 = paramRecPayments.getPaymentsToPayee(paramPayee);
        if ((((Payments)localObject1).size() != 0) || (((RecPayments)localObject2).size() != 0)) {
          i = 2014;
        }
      }
      if ((i == 0) && (this.checkForSavedPayments))
      {
        localObject1 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        localObject2 = (Payees)paramHttpSession.getAttribute("Payees");
        Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
        Object localObject3 = (ArrayList)paramHttpSession.getAttribute("PaymentTemplates");
        if (localObject3 != null)
        {
          if ((localObject3 instanceof Payments)) {
            localObject3 = (Payments)localObject3;
          } else if ((localObject3 instanceof FundsTransactions)) {
            localObject3 = (FundsTransactions)localObject3;
          }
          Iterator localIterator = ((ArrayList)localObject3).iterator();
          while (localIterator.hasNext())
          {
            FundsTransaction localFundsTransaction = (FundsTransaction)localIterator.next();
            Object localObject4;
            if ((localFundsTransaction instanceof Payment))
            {
              localObject4 = ((Payment)localFundsTransaction).getPayee();
              if ((localObject4 != null) && (((Payee)localObject4).getID() == paramPayee.getID()))
              {
                i = 2040;
                break;
              }
            }
            else if ((localFundsTransaction instanceof PaymentBatch))
            {
              localObject4 = new HashMap();
              ((HashMap)localObject4).put("PAYEES", localObject2);
              ((HashMap)localObject4).put("ACCOUNTS", localAccounts);
              PaymentBatch localPaymentBatch = new PaymentBatch();
              localPaymentBatch.setID(((PaymentBatch)localFundsTransaction).getID());
              try
              {
                localPaymentBatch = Billpay.getPaymentBatchByID((SecureUser)localObject1, localPaymentBatch, (HashMap)localObject4);
              }
              catch (CSILException localCSILException) {}
              Payments localPayments = localPaymentBatch.getPayments();
              if (localPayments != null) {
                for (int j = 0; j < localPayments.size(); j++)
                {
                  Payee localPayee = ((Payment)localPayments.get(j)).getPayee();
                  if ((localPayee != null) && (localPayee.getID() == paramPayee.getID()))
                  {
                    i = 2040;
                    break;
                  }
                }
              }
              if (i != 0) {
                break;
              }
            }
          }
        }
      }
      this.validate = null;
    }
    return i;
  }
  
  public int doProcess(HttpSession paramHttpSession, Payee paramPayee)
  {
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      paramPayee = Billpay.deletePayee(localSecureUser, paramPayee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
      paramPayee = localPayees.getByID(paramPayee.getID());
      localPayees.remove(paramPayee);
      paramHttpSession.removeAttribute("Payee");
    }
    return this.error;
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeID = paramString;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!"".equals(paramString)) {
      this.validate = paramString.toUpperCase();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.DeletePayee
 * JD-Core Version:    0.7.0.1
 */