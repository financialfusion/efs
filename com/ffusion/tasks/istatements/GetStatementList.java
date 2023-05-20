package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.istatements.Statements;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatementList
  extends BaseTask
  implements StatementTask
{
  private String OE;
  private Locale OD;
  private String OF = "StatementAccounts";
  private String OG = "Statements";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    SecureUser localSecureUser = null;
    this.OE = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Statements localStatements = null;
    this.OD = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.OD == null)
    {
      this.error = 41;
      return this.taskErrorURL;
    }
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.OF);
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (localAccounts == null)
    {
      this.error = 39;
      return this.taskErrorURL;
    }
    try
    {
      localStatements = StatementData.getStatementList(localSecureUser, localAccounts);
      localHttpSession.setAttribute(this.OG, localStatements);
      this.OE = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.OE = this.serviceErrorURL;
    }
    DebugLog.log("GetStatementList Completed GSL : url = " + this.OE);
    return this.OE;
  }
  
  public void setStatementAccountsInSessionName(String paramString)
  {
    this.OF = paramString;
  }
  
  public void setStatementsInSessionName(String paramString)
  {
    this.OG = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetStatementList
 * JD-Core Version:    0.7.0.1
 */