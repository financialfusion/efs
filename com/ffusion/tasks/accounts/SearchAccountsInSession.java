package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchAccountsInSession
  extends BaseTask
  implements Task
{
  protected String accountsName = "Accounts";
  protected String nickName;
  protected String number;
  protected String type;
  protected String core;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.accountsName);
    if (localAccounts != null)
    {
      searchAccounts(localHttpSession, localAccounts);
      str = this.successURL;
    }
    else
    {
      this.error = 18006;
    }
    return str;
  }
  
  protected void searchAccounts(HttpSession paramHttpSession, Accounts paramAccounts)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    Accounts localAccounts = new Accounts(localLocale);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    localAccounts.setSecureUser(localSecureUser);
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (((this.nickName == null) || (this.nickName.length() <= 0) || (localAccount.getNickName() == null) || (checkNickName(localAccount.getNickName()))) && ((this.number == null) || (this.number.length() <= 0) || (checkNumber(localAccount.getNumber()))) && ((this.type == null) || (this.type.length() <= 0) || (checkType(localAccount.getType())))) {
        localAccounts.add(localAccount);
      }
    }
    paramHttpSession.setAttribute("FilteredAccounts", localAccounts);
  }
  
  protected boolean checkNickName(String paramString)
  {
    return paramString.toUpperCase().startsWith(this.nickName.toUpperCase());
  }
  
  protected boolean checkNumber(String paramString)
  {
    return paramString.startsWith(this.number);
  }
  
  protected boolean checkType(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(this.type, ",");
    while (localStringTokenizer.hasMoreTokens()) {
      if (paramString.equalsIgnoreCase(localStringTokenizer.nextToken())) {
        return true;
      }
    }
    return false;
  }
  
  public void setNumber(String paramString)
  {
    this.number = paramString;
  }
  
  public String getNumber()
  {
    return this.number;
  }
  
  public void setNickName(String paramString)
  {
    this.nickName = paramString;
  }
  
  public String getNickName()
  {
    return this.nickName;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setCore(String paramString)
  {
    this.core = paramString;
  }
  
  public String getCore()
  {
    return this.core;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public String getAccountsName()
  {
    return this.accountsName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.SearchAccountsInSession
 * JD-Core Version:    0.7.0.1
 */