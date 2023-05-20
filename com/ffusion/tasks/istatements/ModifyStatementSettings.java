package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyStatementSettings
  extends BaseTask
  implements StatementTask
{
  private String Oh;
  private ArrayList Oi = new ArrayList();
  private ArrayList Ok = new ArrayList();
  private String Oj = "StatementAccounts";
  private String Ol = "AvailableAccounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    SecureUser localSecureUser = null;
    this.Oh = this.taskErrorURL;
    this.error = 0;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.Oj);
    Accounts localAccounts2 = (Accounts)localHttpSession.getAttribute(this.Ol);
    Accounts localAccounts3 = new Accounts();
    Accounts localAccounts4 = new Accounts();
    Account localAccount = null;
    Iterator localIterator1 = this.Oi.iterator();
    Iterator localIterator2 = null;
    for (;;)
    {
      if (!localIterator1.hasNext()) {
        break label321;
      }
      str3 = (String)localIterator1.next();
      if (str3.indexOf(',') < 0)
      {
        this.error = 36200;
        return this.taskErrorURL;
      }
      str1 = str3.substring(0, str3.indexOf(','));
      str2 = str3.substring(str3.indexOf(',') + 1, str3.length());
      if ((str1 == null) || (str1.length() == 0))
      {
        this.error = 36201;
        return this.taskErrorURL;
      }
      if ((str2 == null) || (str2.length() == 0))
      {
        this.error = 36202;
        return this.taskErrorURL;
      }
      localIterator2 = localAccounts2.iterator();
      if (localIterator2.hasNext())
      {
        localAccount = (Account)localIterator2.next();
        if ((!localAccount.getBankID().equals(str1)) || (!localAccount.getNumber().equals(str2))) {
          break;
        }
        localAccounts3.add(localAccount);
      }
    }
    try
    {
      label321:
      if ((localAccounts3 != null) && (localAccounts3.size() != 0)) {
        StatementData.addAccountsForIStatement(localSecureUser, localAccounts3);
      }
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      this.Oh = this.serviceErrorURL;
    }
    localIterator1 = this.Ok.iterator();
    for (;;)
    {
      if (!localIterator1.hasNext()) {
        break label565;
      }
      str3 = (String)localIterator1.next();
      if (str3.indexOf(',') < 0)
      {
        this.error = 36200;
        return this.taskErrorURL;
      }
      str1 = str3.substring(0, str3.indexOf(','));
      str2 = str3.substring(str3.indexOf(',') + 1, str3.length());
      if ((str1 == null) || (str1.length() == 0))
      {
        this.error = 36203;
        return this.taskErrorURL;
      }
      if ((str2 == null) || (str2.length() == 0))
      {
        this.error = 36204;
        return this.taskErrorURL;
      }
      localIterator2 = localAccounts1.iterator();
      if (localIterator2.hasNext())
      {
        localAccount = (Account)localIterator2.next();
        if ((!localAccount.getBankID().equals(str1)) || (!localAccount.getNumber().equals(str2))) {
          break;
        }
        localAccounts4.add(localAccount);
      }
    }
    try
    {
      label565:
      if ((localAccounts4 != null) && (localAccounts4.size() != 0)) {
        StatementData.removeAccountsForIStatement(localSecureUser, localAccounts4);
      }
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      this.Oh = this.serviceErrorURL;
    }
    localIterator1 = localAccounts3.iterator();
    while (localIterator1.hasNext())
    {
      localAccount = (Account)localIterator1.next();
      localAccounts2.removeByID(localAccount.getID());
      localAccounts1.add(localAccount);
    }
    localIterator2 = localAccounts4.iterator();
    while (localIterator2.hasNext())
    {
      localAccount = (Account)localIterator2.next();
      localAccounts1.removeByID(localAccount.getID());
      localAccounts2.add(localAccount);
    }
    localHttpSession.setAttribute(this.Oj, localAccounts1);
    localHttpSession.setAttribute(this.Ol, localAccounts2);
    return this.successURL;
  }
  
  public void setAcctToEnable(String paramString)
  {
    this.Oi.add(paramString);
  }
  
  public void setAcctToDisble(String paramString)
  {
    this.Ok.add(paramString);
  }
  
  public void setStatementAccountsInSessionName(String paramString)
  {
    this.Oj = paramString;
  }
  
  public void setAvailableAccountsInSessionName(String paramString)
  {
    this.Ol = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.ModifyStatementSettings
 * JD-Core Version:    0.7.0.1
 */