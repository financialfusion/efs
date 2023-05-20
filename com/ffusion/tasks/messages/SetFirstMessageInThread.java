package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;
import com.ffusion.tasks.BaseTask;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetFirstMessageInThread
  extends BaseTask
  implements Task
{
  private String sx = "MessageList";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      Messages localMessages = (Messages)localHttpSession.getAttribute(this.sx);
      if (localMessages == null)
      {
        this.error = 8001;
        return this.taskErrorURL;
      }
      Message localMessage = null;
      Iterator localIterator = localMessages.iterator();
      while (localIterator.hasNext())
      {
        localMessage = (Message)localIterator.next();
        if (localMessage.getTypeValue() != 7) {
          break;
        }
      }
      if (localMessage == null)
      {
        this.error = 8002;
        return this.taskErrorURL;
      }
      localHttpSession.setAttribute("Message", localMessage);
      str = this.successURL;
    }
    catch (Exception localException)
    {
      this.error = 8499;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setMessagesSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.sx = paramString;
    }
  }
  
  public String getMessagesSessionKey()
  {
    return this.sx;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetFirstMessageInThread
 * JD-Core Version:    0.7.0.1
 */