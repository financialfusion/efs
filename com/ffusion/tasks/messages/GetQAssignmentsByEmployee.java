package com.ffusion.tasks.messages;

import com.ffusion.beans.bankemployee.BankEmployee;
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

public class GetQAssignmentsByEmployee
  extends BaseTask
  implements Task, BankEmployeeTask
{
  private static final String rq = "message";
  private static final String rs = "application";
  private String rr;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    try
    {
      MessageQueues localMessageQueues1 = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
      if (localMessageQueues1 == null)
      {
        this.error = 8020;
        str = this.taskErrorURL;
      }
      else
      {
        localMessageQueues1.setFilter("All");
        MessageQueues localMessageQueues2 = new MessageQueues();
        MessageQueues localMessageQueues3 = new MessageQueues();
        MessageQueues localMessageQueues4 = new MessageQueues();
        MessageQueues localMessageQueues5 = new MessageQueues();
        MessageQueues localMessageQueues6 = new MessageQueues();
        MessageQueues localMessageQueues7 = new MessageQueues();
        Iterator localIterator = localMessageQueues1.iterator();
        while (localIterator.hasNext())
        {
          MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
          if (this.rr == null)
          {
            if (localMessageQueue.getQueueTypeName().equals("message")) {
              localMessageQueues2.addMessageQueue(localMessageQueue);
            } else {
              localMessageQueues5.addMessageQueue(localMessageQueue);
            }
          }
          else
          {
            MessageQueueMembers localMessageQueueMembers1 = localMessageQueue.getActiveQueueMembers();
            MessageQueueMembers localMessageQueueMembers2 = localMessageQueue.getInactiveQueueMembers();
            BankEmployee localBankEmployee1 = localMessageQueueMembers1.getByID(this.rr);
            BankEmployee localBankEmployee2 = localMessageQueueMembers2.getByID(this.rr);
            if (localBankEmployee1 != null)
            {
              if (localMessageQueue.getQueueTypeName().equals("message")) {
                localMessageQueues3.addMessageQueue(localMessageQueue);
              } else {
                localMessageQueues6.addMessageQueue(localMessageQueue);
              }
            }
            else if (localBankEmployee2 != null)
            {
              if (localMessageQueue.getQueueTypeName().equals("message")) {
                localMessageQueues4.addMessageQueue(localMessageQueue);
              } else {
                localMessageQueues7.addMessageQueue(localMessageQueue);
              }
            }
            else if (localMessageQueue.getQueueTypeName().equals("message")) {
              localMessageQueues2.addMessageQueue(localMessageQueue);
            } else {
              localMessageQueues5.addMessageQueue(localMessageQueue);
            }
          }
        }
        localHttpSession.setAttribute("MsgQueueAssignmentsAvailable", localMessageQueues2);
        localHttpSession.setAttribute("MsgQueueAssignmentsActive", localMessageQueues3);
        localHttpSession.setAttribute("MsgQueueAssignmentsInActive", localMessageQueues4);
        localHttpSession.setAttribute("AppQueueAssignmentsAvailable", localMessageQueues5);
        localHttpSession.setAttribute("AppQueueAssignmentsActive", localMessageQueues6);
        localHttpSession.setAttribute("AppQueueAssignmentsInActive", localMessageQueues7);
      }
    }
    catch (Exception localException)
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setEmployeeID(String paramString)
  {
    this.rr = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetQAssignmentsByEmployee
 * JD-Core Version:    0.7.0.1
 */