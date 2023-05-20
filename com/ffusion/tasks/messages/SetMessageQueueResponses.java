package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueResponses;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMessageQueueResponses
  extends BaseTask
  implements Task
{
  private String ro = "MessageQueue";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    try
    {
      MessageQueue localMessageQueue = (MessageQueue)localHttpSession.getAttribute(this.ro);
      if (localMessageQueue == null)
      {
        this.error = 8021;
        str = this.taskErrorURL;
      }
      else
      {
        MessageQueueResponses localMessageQueueResponses = localMessageQueue.getQueueResponses();
        if (paramHttpServletResponse == null)
        {
          this.error = 8029;
          str = this.taskErrorURL;
        }
        else
        {
          localHttpSession.setAttribute("MessageQueueResponses", localMessageQueueResponses);
        }
      }
    }
    catch (Exception localException)
    {
      if (str == null) {
        this.error = 8499;
      }
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setMessageQueueSessionName(String paramString)
  {
    this.ro = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetMessageQueueResponses
 * JD-Core Version:    0.7.0.1
 */