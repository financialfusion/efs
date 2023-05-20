package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMessageQueueMembers
  extends BaseTask
  implements Task
{
  private String sF = "MessageQueue";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    MessageQueue localMessageQueue = (MessageQueue)localHttpSession.getAttribute(this.sF);
    if (localMessageQueue == null)
    {
      this.error = 8021;
      str = this.taskErrorURL;
    }
    else
    {
      MessageQueueMembers localMessageQueueMembers1 = localMessageQueue.getActiveQueueMembers();
      MessageQueueMembers localMessageQueueMembers2 = localMessageQueue.getInactiveQueueMembers();
      if ((localMessageQueueMembers1 == null) || (localMessageQueueMembers2 == null))
      {
        this.error = 8028;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute("MessageQueueActiveMembers", localMessageQueueMembers1);
        localHttpSession.setAttribute("MessageQueueInactiveMembers", localMessageQueueMembers2);
        str = this.successURL;
        MessageQueueMembers localMessageQueueMembers3 = (MessageQueueMembers)localMessageQueueMembers1.clone();
        localHttpSession.setAttribute("TempMessageQueueActiveMembers", localMessageQueueMembers3);
        MessageQueueMembers localMessageQueueMembers4 = (MessageQueueMembers)localMessageQueueMembers2.clone();
        localHttpSession.setAttribute("TempMessageQueueInactiveMembers", localMessageQueueMembers4);
      }
    }
    return str;
  }
  
  public void setMessageQueueSessionName(String paramString)
  {
    this.sF = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetMessageQueueMembers
 * JD-Core Version:    0.7.0.1
 */