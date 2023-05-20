package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendInternalQuery
  extends SendMessage
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    return str;
  }
  
  protected String sendMessage(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    this.processFlag = false;
    HashMap localHashMap = null;
    MessageThread localMessageThread = (MessageThread)paramHttpSession.getAttribute("ThreadMessage");
    if (localMessageThread == null)
    {
      this.error = 8024;
      str = this.taskErrorURL;
    }
    else
    {
      localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        MessageAdmin.sendInternalQuery(localSecureUser, this, localMessageThread, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localMessageThread.setThreadStatus("MESSAGE_IQUERY");
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendInternalQuery
 * JD-Core Version:    0.7.0.1
 */