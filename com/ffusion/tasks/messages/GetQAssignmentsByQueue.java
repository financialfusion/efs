package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.bankemployee.BankEmployeeTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetQAssignmentsByQueue
  extends BaseTask
  implements Task, BankEmployeeTask
{
  private String qN = "";
  private String qO = "message";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    try
    {
      if (this.qN.length() == 0)
      {
        localHttpSession.setAttribute("QueueMembersActive", new MessageQueueMembers());
        localHttpSession.setAttribute("QueueMembersInActive", new MessageQueueMembers());
        localHttpSession.setAttribute("TempMessageQueueActiveMembers", new MessageQueueMembers());
        localHttpSession.setAttribute("TempMessageQueueInactiveMembers", new MessageQueueMembers());
        return str;
      }
      MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
      if (localMessageQueues == null)
      {
        this.error = 8020;
        str = this.taskErrorURL;
      }
      else
      {
        localMessageQueues.setFilter("All");
        Iterator localIterator = localMessageQueues.iterator();
        while (localIterator.hasNext())
        {
          MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
          if (localMessageQueue.getId().equals(this.qN))
          {
            MessageQueueMembers localMessageQueueMembers1 = localMessageQueue.getActiveQueueMembers();
            MessageQueueMembers localMessageQueueMembers2 = localMessageQueue.getInactiveQueueMembers();
            localHttpSession.setAttribute("QueueMembersActive", localMessageQueueMembers1);
            localHttpSession.setAttribute("QueueMembersInActive", localMessageQueueMembers2);
            localHttpSession.setAttribute("TempMessageQueueActiveMembers", localMessageQueueMembers1.clone());
            localHttpSession.setAttribute("TempMessageQueueInactiveMembers", localMessageQueueMembers2.clone());
            break;
          }
        }
      }
    }
    catch (Exception localException)
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setQueueId(String paramString)
  {
    this.qN = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetQAssignmentsByQueue
 * JD-Core Version:    0.7.0.1
 */