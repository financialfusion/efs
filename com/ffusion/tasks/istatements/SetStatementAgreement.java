package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetStatementAgreement
  extends BaseTask
  implements StatementTask
{
  private String OH;
  private String OI = "StmtAccepted";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    SecureUser localSecureUser = null;
    Object localObject = null;
    this.OH = this.taskErrorURL;
    this.error = 0;
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      StatementData.setStatementAgreement(localSecureUser, "Y");
      localHttpSession.setAttribute(this.OI, "true");
      this.OH = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = 36209;
      this.OH = this.taskErrorURL;
    }
    return this.OH;
  }
  
  public void setStmtAcceptedInSessionName(String paramString)
  {
    this.OI = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.SetStatementAgreement
 * JD-Core Version:    0.7.0.1
 */