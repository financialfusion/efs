package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchACHAcctsByNameNumType
  extends BaseTask
  implements Task
{
  protected String accountsName = "ACHOffsetAccounts";
  protected String nickName;
  protected String number;
  protected String type;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHOffsetAccounts localACHOffsetAccounts = (ACHOffsetAccounts)localHttpSession.getAttribute(this.accountsName);
    if (localACHOffsetAccounts != null)
    {
      searchACHOffsetAccounts(localHttpSession, localACHOffsetAccounts);
      str = this.successURL;
    }
    else
    {
      this.error = 16013;
    }
    return str;
  }
  
  protected void searchACHOffsetAccounts(HttpSession paramHttpSession, ACHOffsetAccounts paramACHOffsetAccounts)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    ACHOffsetAccounts localACHOffsetAccounts = new ACHOffsetAccounts(localLocale);
    Iterator localIterator = paramACHOffsetAccounts.iterator();
    while (localIterator.hasNext())
    {
      ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)localIterator.next();
      if (((this.nickName == null) || (this.nickName.length() <= 0) || (localACHOffsetAccount.getNickName() == null) || (localACHOffsetAccount.getNickName().length() >= 40) || (checkNickName(localACHOffsetAccount.getNickName()))) && ((this.number == null) || (this.number.length() <= 0) || (localACHOffsetAccount.getNumber() == null) || (localACHOffsetAccount.getNumber().length() >= 17) || (checkNumber(localACHOffsetAccount.getNumber()))) && ((this.type == null) || (this.type.length() <= 0) || (localACHOffsetAccount.getType() == null) || (checkType(localACHOffsetAccount.getType())))) {
        localACHOffsetAccounts.add(localACHOffsetAccount);
      }
    }
    paramHttpSession.setAttribute("FilteredACHOffsetAccounts", localACHOffsetAccounts);
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
    return paramString.equalsIgnoreCase(this.type);
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
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SearchACHAcctsByNameNumType
 * JD-Core Version:    0.7.0.1
 */