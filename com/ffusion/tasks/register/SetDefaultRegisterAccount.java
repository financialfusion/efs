package com.ffusion.tasks.register;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetDefaultRegisterAccount
  extends ExtendedBaseTask
  implements Task
{
  private String Ey = null;
  private boolean Ez;
  
  public SetDefaultRegisterAccount()
  {
    this.beanSessionName = "Account";
    this.collectionSessionName = "BankingAccounts";
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
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.collectionSessionName);
    if (localAccounts == null)
    {
      this.error = 20002;
      return this.taskErrorURL;
    }
    Object localObject1 = null;
    Object localObject2 = null;
    localAccounts.setFilter("All");
    Iterator localIterator = localAccounts.iterator();
    if ((this.id == null) || (this.id.equals("")))
    {
      int i = 0;
      while (localIterator.hasNext())
      {
        localObject1 = (Account)localIterator.next();
        if ((localObject2 == null) && (RegisterUtil.isRegisterEnabled((Account)localObject1))) {
          localObject2 = localObject1;
        }
        if ((RegisterUtil.isRegisterEnabled((Account)localObject1)) && (RegisterUtil.isRegisterDefault((Account)localObject1)))
        {
          i = 1;
          DateTime localDateTime = (DateTime)((Account)localObject1).get("REG_RETRIEVAL_DATE");
          if (localDateTime == null) {
            ((Account)localObject1).setDownloadedItems(false);
          } else {
            ((Account)localObject1).setDownloadedItems(true);
          }
        }
      }
      if (i == 0)
      {
        if (localObject2 == null)
        {
          this.Ez = true;
          return this.Ey;
        }
        localObject1 = localObject2;
        ((Account)localObject1).set("REG_DEFAULT", "true");
        try
        {
          Register.modifyRegisterAccountData(localSecureUser, (Account)localObject1, null);
        }
        catch (CSILException localCSILException1)
        {
          this.error = MapError.mapError(localCSILException1);
        }
        if (this.error != 0) {
          return this.serviceErrorURL;
        }
      }
    }
    else
    {
      localObject1 = localAccounts.getByID(this.id);
      if ((localObject1 == null) || (!RegisterUtil.isRegisterEnabled((Account)localObject1)))
      {
        this.error = 20200;
        return this.taskErrorURL;
      }
      while (localIterator.hasNext())
      {
        localObject3 = (Account)localIterator.next();
        ((Account)localObject3).set("REG_DEFAULT", "false");
      }
      ((Account)localObject1).set("REG_DEFAULT", "true");
      Object localObject3 = (DateTime)((Account)localObject1).get("REG_RETRIEVAL_DATE");
      if (localObject3 == null) {
        ((Account)localObject1).setDownloadedItems(false);
      } else {
        ((Account)localObject1).setDownloadedItems(true);
      }
      try
      {
        Register.setDefaultRegisterAccount(localSecureUser, ((Account)localObject1).getID(), null);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
      }
      if (this.error != 0) {
        return this.serviceErrorURL;
      }
    }
    if ((localObject1 != null) && (((Account)localObject1).getTransactions() != null)) {
      ((Account)localObject1).getTransactions().clear();
    }
    localHttpSession.setAttribute(this.beanSessionName, localObject1);
    return this.successURL;
  }
  
  public void setNoDefaultRegisterAccountURL(String paramString)
  {
    this.Ey = paramString;
  }
  
  public String getNoRegisterEnabledAccounts()
  {
    if (this.Ez) {
      return "true";
    }
    return "false";
  }
  
  public void setNoRegisterEnabledAccounts(boolean paramBoolean)
  {
    this.Ez = paramBoolean;
  }
  
  public void setNoRegisterEnabledAccounts(String paramString)
  {
    if (new Boolean(paramString).booleanValue()) {
      this.Ez = true;
    } else {
      this.Ez = false;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetDefaultRegisterAccount
 * JD-Core Version:    0.7.0.1
 */