package com.ffusion.tasks.accounts;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddAccountWithoutSaveBC
  extends BaseTask
  implements Task
{
  public static String SELECT_ACCOUNT = "acct_";
  public static String SELECTED = "on";
  private String mE = "BackendAccounts";
  private String mD = "Accounts";
  private String mC = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.mE);
    Accounts localAccounts2 = (Accounts)localHttpSession.getAttribute(this.mD);
    if ((localAccounts1 == null) || (localAccounts2 == null))
    {
      this.error = 18006;
      return this.taskErrorURL;
    }
    Iterator localIterator = localAccounts1.iterator();
    Account localAccount = null;
    int i = 0;
    ArrayList localArrayList = new ArrayList();
    while (localIterator.hasNext())
    {
      localObject = paramHttpServletRequest.getParameter(SELECT_ACCOUNT + i);
      localAccount = (Account)localIterator.next();
      if ((localObject != null) && (((String)localObject).equals(SELECTED))) {
        localArrayList.add(localAccount);
      }
      i++;
    }
    Object localObject = localArrayList.iterator();
    while (((Iterator)localObject).hasNext())
    {
      localAccount = (Account)((Iterator)localObject).next();
      localAccounts1.removeByID(localAccount.getID());
      localAccounts2.add(localAccount);
    }
    return str;
  }
  
  public void setBackendAccountKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.mE = paramString;
    }
  }
  
  public String getBackendAccountKey()
  {
    return this.mE;
  }
  
  public void setProfileAccountKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.mD = paramString;
    }
  }
  
  public String getProfileAccountKey()
  {
    return this.mD;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.AddAccountWithoutSaveBC
 * JD-Core Version:    0.7.0.1
 */