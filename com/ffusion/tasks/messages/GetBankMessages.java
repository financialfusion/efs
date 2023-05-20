package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.services.Messaging;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBankMessages
  extends BaseTask
  implements Task
{
  protected String accountsName;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    if (localHttpSession.getAttribute("com.ffusion.tasks.messages.BankingMessages") == null)
    {
      Accounts localAccounts = null;
      if (this.accountsName != null) {
        localAccounts = (Accounts)localHttpSession.getAttribute(this.accountsName);
      }
      if (localAccounts == null)
      {
        this.error = 8012;
        str = this.taskErrorURL;
      }
      else
      {
        com.ffusion.beans.messages.Messages localMessages = (com.ffusion.beans.messages.Messages)localHttpSession.getAttribute("Messages");
        if (localMessages == null)
        {
          localObject = (Locale)localHttpSession.getAttribute("java.util.Locale");
          localMessages = new com.ffusion.beans.messages.Messages((Locale)localObject);
          localHttpSession.setAttribute("Messages", localMessages);
        }
        localHashMap.put("OBJECT", localAccounts);
        localHashMap.put("MESSAGES", localMessages);
        localHashMap.put("SERVICE", (Messaging)localHttpSession.getAttribute("com.ffusion.services.Messaging3"));
        Object localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          localMessages = com.ffusion.csil.core.Messages.getMessages((SecureUser)localObject, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
        if (this.error == 0) {
          localHttpSession.setAttribute("com.ffusion.tasks.messages.BankingMessages", Boolean.TRUE);
        }
      }
    }
    return str;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetBankMessages
 * JD-Core Version:    0.7.0.1
 */