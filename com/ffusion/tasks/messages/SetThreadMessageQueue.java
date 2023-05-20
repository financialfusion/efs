package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetThreadMessageQueue
  extends BaseTask
  implements Task
{
  private String sG = "ThreadMessage";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    MessageThread localMessageThread = (MessageThread)localHttpSession.getAttribute(this.sG);
    MessageQueues localMessageQueues1 = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
    MessageQueues localMessageQueues2 = (MessageQueues)localHttpSession.getAttribute("MessageQueuesInactive");
    if ((localMessageQueues1 == null) || (localMessageQueues2 == null))
    {
      this.error = 8020;
      str1 = this.taskErrorURL;
    }
    else if (localMessageThread == null)
    {
      this.error = 8022;
      str1 = this.taskErrorURL;
    }
    else
    {
      MessageQueue localMessageQueue = null;
      String str2 = localMessageThread.getQueueID();
      if ((str2 == null) || (str2.length() == 0))
      {
        this.error = 8033;
        str1 = this.taskErrorURL;
      }
      else
      {
        if (str2.equals("0")) {
          localMessageQueue = new MessageQueue();
        } else {
          localMessageQueue = (MessageQueue)localMessageQueues1.getElementByID(str2);
        }
        if (localMessageQueue == null) {
          localMessageQueue = (MessageQueue)localMessageQueues2.getElementByID(str2);
        }
        if (localMessageQueue == null)
        {
          this.error = 8043;
          str1 = this.taskErrorURL;
        }
        else
        {
          localHttpSession.setAttribute("MessageQueue", localMessageQueue);
        }
      }
    }
    return str1;
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.sG = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.sG;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetThreadMessageQueue
 * JD-Core Version:    0.7.0.1
 */