package com.ffusion.tasks.register;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessTransferTransactions
  extends ExtendedBaseTask
  implements Task
{
  private String Fd = null;
  
  public ProcessTransferTransactions()
  {
    this.collectionSessionName = "Transfers";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    Transfers localTransfers = (Transfers)localHttpSession.getAttribute(this.collectionSessionName);
    if (localTransfers != null)
    {
      Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.Fd);
      if (localAccounts == null)
      {
        this.error = 20002;
        return this.taskErrorURL;
      }
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      ArrayList localArrayList = new ArrayList();
      jdMethod_for(localTransfers, localAccounts, localRegisterTransactions, localArrayList);
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
  
  private void jdMethod_for(Transfers paramTransfers, Accounts paramAccounts, RegisterTransactions paramRegisterTransactions, ArrayList paramArrayList)
  {
    DateTime localDateTime = RegisterUtil.getLatestRetrievalDate(paramAccounts);
    if (localDateTime != null) {
      localDateTime.add(6, -1);
    }
    String str = paramTransfers.getFilter();
    paramTransfers.setFilter("All");
    Iterator localIterator = paramTransfers.iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      if ((localTransfer.getFromAccount() != null) && (localTransfer.getToAccount() != null))
      {
        int i = localTransfer.getStatus();
        if (statusRequiresDelete(i))
        {
          paramArrayList.add(localTransfer.getID());
        }
        else if (((localDateTime == null) || (localTransfer.getDateValue().after(localDateTime))) && (statusRequiresAddUpdate(i)))
        {
          Account localAccount1 = paramAccounts.getByID(localTransfer.getFromAccountID());
          Account localAccount2 = paramAccounts.getByID(localTransfer.getToAccountID());
          if (RegisterUtil.isRegisterEnabled(localAccount1)) {
            paramRegisterTransactions.add(RegisterUtil.buildRegisterTransaction(localTransfer, true));
          }
          if (RegisterUtil.isRegisterEnabled(localAccount2)) {
            paramRegisterTransactions.add(RegisterUtil.buildRegisterTransaction(localTransfer, false));
          }
        }
      }
    }
    paramTransfers.setFilter(str);
  }
  
  protected boolean statusRequiresDelete(int paramInt)
  {
    return (paramInt == 3) || (paramInt == 6) || (paramInt == 10) || (paramInt == 7);
  }
  
  protected boolean statusRequiresAddUpdate(int paramInt)
  {
    return (paramInt == 2) || (paramInt == 11) || (paramInt == 4) || (paramInt == 5);
  }
  
  public void setAccountsCollectionName(String paramString)
  {
    this.Fd = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.ProcessTransferTransactions
 * JD-Core Version:    0.7.0.1
 */