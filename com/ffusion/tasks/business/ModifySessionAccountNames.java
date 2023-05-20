package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifySessionAccountNames
  implements BusinessTask
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error;
  protected String accountsName;
  public static final String ACCOUNTPREFIX = "Acct_";
  public static final String ACCOUNTNUMBERSEPARATOR = "-";
  public static final String ACCOUNTNICKNAMESUFFIX = "_Nick";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.accountsName);
    if (localAccounts == null) {
      return str1;
    }
    Iterator localIterator = localAccounts.iterator();
    while ((localIterator.hasNext()) && (this.error == 0))
    {
      Account localAccount = (Account)localIterator.next();
      String str2 = (String)localHttpSession.getAttribute("Acct_" + localAccount.getBankID() + "-" + localAccount.getRoutingNum() + "-" + localAccount.getNumber() + "-" + localAccount.getTypeValue() + "_Nick");
      localAccount.setNickName(str2);
    }
    return str1;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ModifySessionAccountNames
 * JD-Core Version:    0.7.0.1
 */