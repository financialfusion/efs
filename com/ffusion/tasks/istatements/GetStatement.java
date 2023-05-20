package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.beans.istatements.Statements;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatement
  extends BaseTask
  implements StatementTask
{
  private String OP;
  private String OM = "Statements";
  private String OQ = "Statement";
  private String OO = "StatementTransactions";
  private String OL = "StatementDailyBalances";
  private String OS = "StatementMessages";
  private String ON = "StatementAccount";
  private String OJ = "StatementAccounts";
  private String OK;
  private Locale OT;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    SecureUser localSecureUser = null;
    this.OP = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.OT = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.OT == null)
    {
      this.error = 41;
      return this.taskErrorURL;
    }
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (jdMethod_for(localHttpSession, localSecureUser, this.OT) == 0) {
      this.OP = this.successURL;
    } else {
      this.OP = this.serviceErrorURL;
    }
    return this.OP;
  }
  
  private int jdMethod_for(HttpSession paramHttpSession, SecureUser paramSecureUser, Locale paramLocale)
  {
    int i = 0;
    Statements localStatements = (Statements)paramHttpSession.getAttribute(this.OM);
    if (localStatements == null) {
      localStatements = new Statements(paramLocale);
    }
    try
    {
      Statement localStatement = localStatements.getByID(this.OK);
      if (localStatement == null)
      {
        localStatement = (Statement)localStatements.createNoAdd();
        localStatement.setID(this.OK);
      }
      if (localStatement.getTransactions() == null)
      {
        try
        {
          localStatement = StatementData.getStatement(paramSecureUser, localStatement);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          this.OP = this.serviceErrorURL;
        }
        if (i == 0) {
          paramHttpSession.setAttribute(this.OM, localStatements);
        }
      }
      paramHttpSession.setAttribute(this.OO, localStatement.getTransactions());
      paramHttpSession.setAttribute(this.OL, localStatement.getDailyBalanceSummaries());
      paramHttpSession.setAttribute(this.OS, localStatement.getMessages());
      paramHttpSession.setAttribute(this.OQ, localStatement);
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.OJ);
      if ((localAccounts == null) || (localAccounts.size() == 0))
      {
        this.error = 36210;
        this.OP = this.taskErrorURL;
        return this.error;
      }
      Iterator localIterator = localAccounts.iterator();
      Account localAccount = null;
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        if ((localAccount.getNumber().equals(localStatement.getAccountNumber())) && (localAccount.getBankID().equals(localStatement.getBankID()))) {
          paramHttpSession.setAttribute(this.ON, localAccount);
        }
      }
    }
    catch (Exception localException)
    {
      i = 36009;
    }
    return i;
  }
  
  public final void setStatementsInSessionName(String paramString)
  {
    this.OM = paramString;
  }
  
  public final String getStatementsInSessionName()
  {
    return this.OM;
  }
  
  public final void setStatementInSessionName(String paramString)
  {
    this.OQ = paramString;
  }
  
  public final String getStatementInSessionName()
  {
    return this.OQ;
  }
  
  public void setStatementID(String paramString)
  {
    this.OK = paramString;
  }
  
  public String getStatementID()
  {
    return this.OK;
  }
  
  public void setStatementTransactionsInSessionName(String paramString)
  {
    this.OO = paramString;
  }
  
  public void setDailyBalancesInSessionName(String paramString)
  {
    this.OL = paramString;
  }
  
  public void setMessagesInSessionName(String paramString)
  {
    this.OS = paramString;
  }
  
  public void setStatementAccountInSessionName(String paramString)
  {
    this.ON = paramString;
  }
  
  public void setStatementAccountsInSessionName(String paramString)
  {
    this.OJ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetStatement
 * JD-Core Version:    0.7.0.1
 */