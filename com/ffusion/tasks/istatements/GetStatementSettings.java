package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.core.StatementData;
import com.ffusion.services.AccountService3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatementSettings
  extends BaseTask
  implements StatementTask
{
  private String Ot;
  private static final String Ox = "STMT_FLAG";
  private String Ou = "Accounts";
  private String Ov = "StatementAccounts";
  private String Oy = "AvailableAccounts";
  private SecureUser Ow = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.Ot = this.taskErrorURL;
    this.error = 0;
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.Ou);
      if ((localAccounts == null) || (localAccounts.size() == 0))
      {
        this.Ow = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
        if (this.Ow == null)
        {
          this.error = 38;
          return this.taskErrorURL;
        }
        localObject1 = (AccountService3)localHttpSession.getAttribute("com.ffusion.service.AccountService");
        if (localObject1 == null)
        {
          this.error = 36211;
          return this.taskErrorURL;
        }
        localObject2 = (Locale)localHttpSession.getAttribute("Locale");
        if (localObject2 == null) {
          localObject2 = Locale.getDefault();
        }
        localObject3 = new HashMap();
        ((HashMap)localObject3).put("Locale", localObject2);
        ((HashMap)localObject3).put("SERVICE", localObject1);
        localAccounts = StatementData.getAllAccounts(this.Ow, (HashMap)localObject3);
        if ((localAccounts == null) || (localAccounts.size() == 0))
        {
          this.error = 36212;
          return this.taskErrorURL;
        }
        localHttpSession.setAttribute(this.Ou, localAccounts);
      }
      DebugLog.log("GetStatementSettings Total No. of Accounts : " + localAccounts.size());
      Object localObject1 = (Accounts)localHttpSession.getAttribute(this.Ov);
      if ((localObject1 == null) || (((Accounts)localObject1).size() == 0)) {
        DebugLog.log("GetStatementSettings No Accounts are enabled for istatement");
      }
      Object localObject2 = new Accounts();
      Object localObject3 = localAccounts.iterator();
      Iterator localIterator = null;
      Account localAccount1 = null;
      Account localAccount2 = null;
      while (((Iterator)localObject3).hasNext())
      {
        localAccount1 = (Account)((Iterator)localObject3).next();
        int i = 0;
        String str = (String)localAccount1.get("STMT_FLAG");
        if ((str != null) && (str.equalsIgnoreCase("Y")))
        {
          if ((localObject1 != null) && (((Accounts)localObject1).size() != 0))
          {
            localIterator = ((Accounts)localObject1).iterator();
            while (localIterator.hasNext())
            {
              localAccount2 = (Account)localIterator.next();
              if ((localAccount2.getBankID().equals(localAccount1.getBankID())) && (localAccount2.getNumber().equals(localAccount1.getNumber()))) {
                i = 1;
              }
            }
          }
          if (i == 0) {
            ((Accounts)localObject2).add(localAccount1);
          }
        }
      }
      localHttpSession.setAttribute(this.Oy, localObject2);
      this.Ot = this.successURL;
    }
    catch (Exception localException)
    {
      this.error = 36209;
      this.Ot = this.serviceErrorURL;
    }
    return this.Ot;
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.Ou = paramString;
  }
  
  public void setStatementAccountsInSessionName(String paramString)
  {
    this.Ov = paramString;
  }
  
  public void setAvailableAccountsInSessionName(String paramString)
  {
    this.Oy = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetStatementSettings
 * JD-Core Version:    0.7.0.1
 */