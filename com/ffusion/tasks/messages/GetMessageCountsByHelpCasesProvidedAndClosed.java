package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMessageCountsByHelpCasesProvidedAndClosed
  extends BaseTask
  implements Task
{
  private String so = "MessageCounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    MessageCounts localMessageCounts = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 1037;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        localMessageCounts = Messages.getMessageCountsByHelpCasesProvidedAndClosed(localSecureUser, localHashMap);
        localHttpSession.setAttribute(this.so, localMessageCounts);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    this.so = "MessageCounts";
    return str;
  }
  
  public void setSessionKeyName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.so = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetMessageCountsByHelpCasesProvidedAndClosed
 * JD-Core Version:    0.7.0.1
 */