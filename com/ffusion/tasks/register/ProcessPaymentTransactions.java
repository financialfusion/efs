package com.ffusion.tasks.register;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessPaymentTransactions
  extends ExtendedBaseTask
  implements Task
{
  String E4 = null;
  
  public ProcessPaymentTransactions()
  {
    this.collectionSessionName = "Payments";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    Payments localPayments = (Payments)localHttpSession.getAttribute(this.collectionSessionName);
    if (localPayments != null)
    {
      Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.E4);
      if (localAccounts == null)
      {
        this.error = 20002;
        return this.taskErrorURL;
      }
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      ArrayList localArrayList = new ArrayList();
      jdMethod_for(localPayments, localAccounts, localRegisterTransactions, localArrayList);
      try
      {
        if (localRegisterTransactions.size() > 0) {
          Register.addUpdateRegisterTransactions(localSecureUser, localRegisterTransactions, null);
        }
        if (localArrayList.size() > 0) {
          Register.deleteRegisterTransactionsByServerTID(localSecureUser, localArrayList, null);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
    }
    return this.successURL;
  }
  
  private void jdMethod_for(Payments paramPayments, Accounts paramAccounts, RegisterTransactions paramRegisterTransactions, ArrayList paramArrayList)
  {
    DateTime localDateTime = RegisterUtil.getLatestRetrievalDate(paramAccounts);
    if (localDateTime != null) {
      localDateTime.add(6, -1);
    }
    String str = paramPayments.getFilter();
    paramPayments.setFilter("All");
    ListIterator localListIterator = paramPayments.listIterator();
    while (localListIterator.hasNext())
    {
      Payment localPayment = (Payment)localListIterator.next();
      if (localPayment.getAccount() != null)
      {
        int i = localPayment.getStatus();
        if (statusRequiresDelete(i)) {
          paramArrayList.add(localPayment.getID());
        } else if (((localDateTime == null) || (localPayment.getPayDateValue().after(localDateTime))) && (statusRequiresAddUpdate(i))) {
          paramRegisterTransactions.add(RegisterUtil.buildRegisterTransaction(localPayment));
        }
      }
    }
    paramPayments.setFilter(str);
  }
  
  protected boolean statusRequiresDelete(int paramInt)
  {
    return (paramInt == 3) || (paramInt == 6) || (paramInt == 11) || (paramInt == 7) || (paramInt == 14);
  }
  
  protected boolean statusRequiresAddUpdate(int paramInt)
  {
    return (paramInt == 2) || (paramInt == 13) || (paramInt == 12) || (paramInt == 4) || (paramInt == 5);
  }
  
  public void setAccountsCollectionName(String paramString)
  {
    this.E4 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.ProcessPaymentTransactions
 * JD-Core Version:    0.7.0.1
 */