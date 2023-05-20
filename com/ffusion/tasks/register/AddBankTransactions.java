package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ListIterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddBankTransactions
  extends ExtendedBaseTask
  implements Task
{
  private boolean E6 = false;
  private boolean E5 = false;
  
  public AddBankTransactions()
  {
    this.beanSessionName = "Account";
    this.collectionSessionName = "RegisterTransactions";
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
    Account localAccount = (Account)localHttpSession.getAttribute(this.beanSessionName);
    if (localAccount == null)
    {
      this.error = 20001;
      return this.taskErrorURL;
    }
    if ((this.E5) || (localAccount.get("AddedToRegister") == null))
    {
      this.E5 = false;
      RegisterTransactions localRegisterTransactions = RegisterUtil.buildRegisterTransactions(localAccount.getTransactions(), localAccount.getID());
      if (this.E6) {
        localRegisterTransactions = reverseTransactionOrder(localRegisterTransactions);
      }
      try
      {
        localRegisterTransactions = Register.addBankTransactions(localSecureUser, localRegisterTransactions, null);
        localAccount.set("AddedToRegister", "true");
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
    }
    localAccount.getTransactions().clear();
    localAccount.setDownloadedItems(false);
    return this.successURL;
  }
  
  protected RegisterTransactions reverseTransactionOrder(RegisterTransactions paramRegisterTransactions)
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    ListIterator localListIterator = paramRegisterTransactions.listIterator(paramRegisterTransactions.size());
    while (localListIterator.hasPrevious()) {
      localRegisterTransactions.add(localListIterator.previous());
    }
    return localRegisterTransactions;
  }
  
  public void setReverseOrder(String paramString)
  {
    this.E6 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setReload(String paramString)
  {
    this.E5 = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.AddBankTransactions
 * JD-Core Version:    0.7.0.1
 */