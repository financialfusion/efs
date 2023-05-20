package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatementAgreement
  extends BaseTask
  implements StatementTask
{
  private String Of;
  private String Og = "StmtAccepted";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    SecureUser localSecureUser = null;
    String str = null;
    this.Of = this.taskErrorURL;
    this.error = 0;
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      str = (String)localHttpSession.getAttribute(this.Og);
      if (str == null)
      {
        localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        if (localSecureUser == null)
        {
          this.error = 38;
          return this.taskErrorURL;
        }
        str = StatementData.getStatementAgreement(localSecureUser);
        if ((str == null) || (str.equalsIgnoreCase("N"))) {
          localHttpSession.setAttribute(this.Og, "false");
        } else {
          localHttpSession.setAttribute(this.Og, "true");
        }
      }
      this.Of = this.successURL;
    }
    catch (Exception localException)
    {
      this.error = 36209;
      this.Of = this.taskErrorURL;
    }
    return this.Of;
  }
  
  public void setStmtAcceptedInSessionName(String paramString)
  {
    this.Og = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetStatementAgreement
 * JD-Core Version:    0.7.0.1
 */