package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;
import com.ffusion.tasks.BaseTask;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetLastMessageInThread
  extends BaseTask
  implements Task
{
  private String sn = "Message";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      Messages localMessages = (Messages)localHttpSession.getAttribute(this.sn);
      if (localMessages == null)
      {
        this.error = 8001;
        return this.taskErrorURL;
      }
      Message localMessage = null;
      for (int i = localMessages.size() - 1; i >= 0; i--)
      {
        localMessage = (Message)localMessages.get(i);
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
      this.sn = paramString;
    }
  }
  
  public String getMessagesSessionKey()
  {
    return this.sn;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetLastMessageInThread
 * JD-Core Version:    0.7.0.1
 */