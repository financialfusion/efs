package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModQCollectionMembersByEmployee
  extends BaseTask
  implements Task
{
  private String rO = "";
  private String rL = "ModifiedBankEmployee";
  private String rN = "MsgQueueAssignmentsActive";
  private String rM = "MsgQueueAssignmentsInActive";
  private String rP = "0";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    BankEmployee localBankEmployee1 = (BankEmployee)localHttpSession.getAttribute(this.rL);
    MessageQueues localMessageQueues1 = (MessageQueues)localHttpSession.getAttribute(this.rN);
    MessageQueues localMessageQueues2 = (MessageQueues)localHttpSession.getAttribute(this.rM);
    if ((localMessageQueues1 == null) || (localMessageQueues2 == null))
    {
      this.error = 8020;
      str = this.taskErrorURL;
    }
    else
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        MessageAdmin.modifyMessageQueueMembersByEmployee(localSecureUser, this.rO, this.rP, localMessageQueues1, localMessageQueues2, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        MessageQueues localMessageQueues3 = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
        if (this.rP.equals("0")) {
          localMessageQueues3.setFilter("QueueType=0");
        } else if (this.rP.equals("1")) {
          localMessageQueues3.setFilter("QueueType=1");
        }
        if (localMessageQueues3 != null)
        {
          Iterator localIterator1 = localMessageQueues3.iterator();
          while (localIterator1.hasNext())
          {
            MessageQueue localMessageQueue = (MessageQueue)localIterator1.next();
            int i = 0;
            Iterator localIterator2;
            Object localObject;
            BankEmployee localBankEmployee2;
            if (localMessageQueues1 != null)
            {
              localIterator2 = localMessageQueues1.iterator();
              while (localIterator2.hasNext())
              {
                localObject = (MessageQueue)localIterator2.next();
                if (localMessageQueue.getId().equals(((MessageQueue)localObject).getId()))
                {
                  i = 1;
                  break;
                }
              }
              localObject = localMessageQueue.getActiveQueueMembers();
              if (i == 0)
              {
                ((MessageQueueMembers)localObject).removeByID(this.rO);
              }
              else
              {
                if (((MessageQueueMembers)localObject).getByID(this.rO) != null) {
                  continue;
                }
                localBankEmployee2 = ((MessageQueueMembers)localObject).create();
                localBankEmployee2.set(localBankEmployee1);
                continue;
              }
            }
            i = 0;
            if (localMessageQueues2 != null)
            {
              localIterator2 = localMessageQueues2.iterator();
              while (localIterator2.hasNext())
              {
                localObject = (MessageQueue)localIterator2.next();
                if (localMessageQueue.getId().equals(((MessageQueue)localObject).getId()))
                {
                  i = 1;
                  break;
                }
              }
              localObject = localMessageQueue.getInactiveQueueMembers();
              if (i == 0)
              {
                ((MessageQueueMembers)localObject).removeByID(this.rO);
              }
              else if (((MessageQueueMembers)localObject).getByID(this.rO) == null)
              {
                localBankEmployee2 = ((MessageQueueMembers)localObject).create();
                localBankEmployee2.set(localBankEmployee1);
              }
            }
          }
        }
        else
        {
          this.error = 8020;
          str = this.taskErrorURL;
        }
        localMessageQueues3.setFilter("All");
      }
    }
    return str;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.rO = paramString;
  }
  
  public void setEmployeeSessionName(String paramString)
  {
    this.rL = paramString;
  }
  
  public void setActiveQueueCollection(String paramString)
  {
    this.rN = paramString;
  }
  
  public void setInactiveQueueCollection(String paramString)
  {
    this.rM = paramString;
  }
  
  public void setQueueType(String paramString)
  {
    this.rP = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.ModQCollectionMembersByEmployee
 * JD-Core Version:    0.7.0.1
 */