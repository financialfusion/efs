package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMessageQueue
  extends BaseTask
  implements Task
{
  private String q6;
  private String q8;
  private String q7;
  private String q5 = "MessageQueues";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute(this.q5);
    if (localMessageQueues == null)
    {
      this.error = 8020;
      str = this.taskErrorURL;
    }
    else
    {
      MessageQueue localMessageQueue = null;
      localMessageQueues.setFilter("All");
      if (this.q6 != null)
      {
        localMessageQueue = (MessageQueue)localMessageQueues.getElementByID(this.q6);
      }
      else if ((this.q8 != null) && (this.q7 != null))
      {
        localMessageQueue = localMessageQueues.getByStatProdID(this.q8, this.q7);
      }
      else
      {
        this.error = 8033;
        str = this.taskErrorURL;
      }
      if (localMessageQueue == null)
      {
        this.error = 8021;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute("MessageQueue", localMessageQueue);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setQueueID(String paramString)
  {
    this.q6 = paramString;
  }
  
  public void setStatusID(String paramString)
  {
    this.q8 = paramString;
  }
  
  public void setProductID(String paramString)
  {
    this.q7 = paramString;
  }
  
  public void setMessageQueuesSessionName(String paramString)
  {
    this.q5 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetMessageQueue
 * JD-Core Version:    0.7.0.1
 */