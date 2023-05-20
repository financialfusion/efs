package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
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

public class EditRegisterAccountsData
  extends ExtendedBaseTask
  implements Task
{
  protected ArrayList enabledRegisterAccountIDs = null;
  private String E3 = null;
  protected String currentAccountID;
  
  public EditRegisterAccountsData()
  {
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
    Object localObject1 = localAccounts.iterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Account)((Iterator)localObject1).next();
      if (this.enabledRegisterAccountIDs.contains(((Account)localObject2).getID())) {
        ((Account)localObject2).set("REG_ENABLED", "true");
      } else {
        ((Account)localObject2).set("REG_ENABLED", "false");
      }
    }
    if (!jdMethod_for(localAccounts, localSecureUser)) {
      return this.serviceErrorURL;
    }
    if (this.E3 != null)
    {
      localAccounts = (Accounts)localHttpSession.getAttribute(this.E3);
      if (localAccounts != null)
      {
        localObject1 = localAccounts.getFilter();
        localAccounts.setFilter("All");
        localObject2 = localAccounts.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          Account localAccount = (Account)((Iterator)localObject2).next();
          if (this.enabledRegisterAccountIDs.contains(localAccount.getID()))
          {
            localAccount.set("REG_ENABLED", "true");
          }
          else
          {
            localAccount.set("REG_DEFAULT", "false");
            localAccount.set("REG_ENABLED", "false");
          }
        }
        localAccounts.setFilter((String)localObject1);
      }
    }
    this.enabledRegisterAccountIDs.clear();
    return this.successURL;
  }
  
  private boolean jdMethod_for(Accounts paramAccounts, SecureUser paramSecureUser)
  {
    try
    {
      Register.modifyRegisterAccountsData(paramSecureUser, paramAccounts, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    return this.error == 0;
  }
  
  public void setRegisterEnabledAccountID(String paramString)
  {
    this.enabledRegisterAccountIDs.add(paramString);
  }
  
  public String getCurrentAccountID()
  {
    return this.currentAccountID;
  }
  
  public void setCurrentAccountID(String paramString)
  {
    this.currentAccountID = paramString;
  }
  
  public boolean getIsCurrentAccountRegisterEnabled()
  {
    return this.enabledRegisterAccountIDs.contains(this.currentAccountID);
  }
  
  public String getSecondAccountsCollection()
  {
    return this.E3;
  }
  
  public void setSecondAccountsCollection(String paramString)
  {
    this.E3 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.EditRegisterAccountsData
 * JD-Core Version:    0.7.0.1
 */