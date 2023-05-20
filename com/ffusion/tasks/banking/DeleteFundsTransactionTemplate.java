package com.ffusion.tasks.banking;

import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteFundsTransactionTemplate
  extends BaseTask
  implements Task
{
  private FundsTransactionTemplate y6;
  protected String sessionName = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      this.y6 = ((FundsTransactionTemplate)localHttpSession.getAttribute("FundsTransactionTemplate"));
    }
    catch (Exception localException)
    {
      this.y6 = null;
    }
    if (this.y6 == null)
    {
      this.error = 1033;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = 0;
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        Banking.deleteFundsTransactionTemplate(localSecureUser, this.y6, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0)
      {
        if (this.sessionName != null)
        {
          FundsTransactionTemplates localFundsTransactionTemplates = (FundsTransactionTemplates)localHttpSession.getAttribute(this.sessionName);
          if (localFundsTransactionTemplates != null) {
            localFundsTransactionTemplates.remove(this.y6);
          }
        }
        str = this.successURL;
      }
      else
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.DeleteFundsTransactionTemplate
 * JD-Core Version:    0.7.0.1
 */