package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddACHOffsetAccount
  extends ModifyACHOffsetAccount
  implements Task
{
  protected String accountsCollectionType = "ACHOffsetAccounts";
  protected String accountsName = "Accounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (init(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = addOffsetAccount(localHttpSession);
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
  
  public boolean init(HttpSession paramHttpSession)
  {
    if (this.accountsCollectionType.equals("Accounts"))
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.accountsName);
      if (localAccounts == null)
      {
        this.error = 39;
        return false;
      }
      Account localAccount = localAccounts.getByID(getID());
      if (localAccount == null)
      {
        this.error = 50;
        return false;
      }
      setNumber(localAccount.getNumber());
      setNickName(localAccount.getNickName());
      if (localAccount.getTypeValue() == 16) {
        setType(4);
      } else {
        setType(localAccount.getTypeValue());
      }
      setRoutingNum(localAccount.getRoutingNum());
    }
    setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    this.initFlag = false;
    return true;
  }
  
  protected String addOffsetAccount(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    if (this.accountsCollectionType.equals("ACHOffsetAccounts"))
    {
      localObject = (ACHOffsetAccounts)paramHttpSession.getAttribute(this.offsetAccountsName);
      if (localObject == null)
      {
        this.error = 16013;
        str = this.taskErrorURL;
        return str;
      }
    }
    else if (this.accountsCollectionType.equals("Accounts"))
    {
      localObject = (Accounts)paramHttpSession.getAttribute(this.accountsName);
      if (localObject == null)
      {
        this.error = 39;
        str = this.taskErrorURL;
        return str;
      }
    }
    else
    {
      this.error = 16517;
      str = this.taskErrorURL;
      return str;
    }
    Object localObject = (ACHCompany)paramHttpSession.getAttribute(this.achCompanyName);
    if (localObject == null)
    {
      this.error = 16506;
      str = this.taskErrorURL;
      return str;
    }
    try
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      ACH.addOffsetAccount(localSecureUser, this, (ACHCompany)localObject, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute(this.offsetAccountName, this);
    }
    return str;
  }
  
  public final void setAccountsCollectionType(String paramString)
  {
    this.accountsCollectionType = paramString;
  }
  
  public final String getAccountsCollectionType()
  {
    return this.accountsCollectionType;
  }
  
  public final void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public final String getAccountsName()
  {
    return this.accountsName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddACHOffsetAccount
 * JD-Core Version:    0.7.0.1
 */