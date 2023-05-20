package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMessageList
  extends BaseTask
  implements Task
{
  protected String objectName = "ThreadMessage";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Object localObject = localHttpSession.getAttribute(this.objectName);
    if ((localObject == null) && ((localObject instanceof MessageThread)))
    {
      this.error = 8024;
      str = this.taskErrorURL;
    }
    else if ((localObject == null) && ((localObject instanceof Message)))
    {
      this.error = 8002;
      str = this.taskErrorURL;
    }
    else
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      com.ffusion.beans.messages.Messages localMessages = new com.ffusion.beans.messages.Messages(localLocale);
      localHashMap.put("OBJECT", localObject);
      localHashMap.put("MESSAGES", localMessages);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localMessages = com.ffusion.csil.core.Messages.getMessages(localSecureUser, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("MessageList", localMessages);
      }
    }
    return str;
  }
  
  public void setObjectName(String paramString)
  {
    this.objectName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetMessageList
 * JD-Core Version:    0.7.0.1
 */