package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatementEnabledAccts
  extends BaseTask
  implements StatementTask
{
  private String Op;
  private String Or = "StatementAccounts";
  private String Oq = "Accounts";
  private String Os;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    SecureUser localSecureUser = null;
    this.Op = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.Oq);
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    try
    {
      Accounts localAccounts2 = null;
      if ((this.Os == null) || (this.Os.trim().equals(""))) {
        localAccounts2 = StatementData.getAccountsForIStatement(localSecureUser);
      } else {
        localAccounts2 = StatementData.getAccountsForIStatement(localSecureUser, this.Os, new HashMap());
      }
      Accounts localAccounts3 = new Accounts(localLocale);
      Iterator localIterator1 = localAccounts2.iterator();
      Iterator localIterator2 = null;
      Account localAccount1 = null;
      Account localAccount2 = null;
      for (;;)
      {
        if (!localIterator1.hasNext()) {
          break label258;
        }
        localAccount1 = (Account)localIterator1.next();
        localIterator2 = localAccounts1.iterator();
        if (localIterator2.hasNext())
        {
          localAccount2 = (Account)localIterator2.next();
          if ((!localAccount1.getBankID().equals(localAccount2.getBankID())) || (!localAccount1.getNumber().equals(localAccount2.getNumber()))) {
            break;
          }
          localAccounts3.add(localAccount2);
        }
      }
      label258:
      localHttpSession.setAttribute(this.Or, localAccounts3);
      this.Op = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.Op = this.serviceErrorURL;
    }
    DebugLog.log("GetStatementEnabledAccts Completed GSEA : url = " + this.Op);
    return this.Op;
  }
  
  public void setStatementAccountsInSessionName(String paramString)
  {
    this.Or = paramString;
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.Oq = paramString;
  }
  
  public void setUserID(String paramString)
  {
    this.Os = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetStatementEnabledAccts
 * JD-Core Version:    0.7.0.1
 */