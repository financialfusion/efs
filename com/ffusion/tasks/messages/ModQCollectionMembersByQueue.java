package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.messages.MessageCount;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModQCollectionMembersByQueue
  extends BaseTask
  implements Task
{
  protected static String QUEUEMEMBERS = "QUEUEMEMBERS";
  protected static String ASSIGNEDMESSAGES = "ASSIGNEDMESSAGES";
  private String sw = "TempMessageQueueActiveMembers";
  private String su = "QueueMembersActive";
  private String st = "TempMessageQueueInactiveMembers";
  private String ss = "QueueMembersInActive";
  private String sv = "MessageQueue";
  protected String validate;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (validateInput(localHttpSession))
    {
      if (modifyQueueMembers(localHttpSession) == true) {
        str = this.successURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate != null)
    {
      Object localObject1;
      Object localObject2;
      if (this.validate.indexOf(QUEUEMEMBERS) != -1)
      {
        localObject1 = (MessageQueueMembers)paramHttpSession.getAttribute(this.sw);
        localObject2 = (MessageQueueMembers)paramHttpSession.getAttribute(this.st);
        if ((localObject1 != null) && (localObject2 != null))
        {
          int i = ((MessageQueueMembers)localObject1).size() + ((MessageQueueMembers)localObject2).size();
          if (i == 0) {
            this.error = 8028;
          }
        }
      }
      if (this.validate.indexOf(ASSIGNEDMESSAGES) != -1)
      {
        localObject1 = null;
        localObject2 = null;
        try
        {
          SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          localObject2 = (MessageQueue)paramHttpSession.getAttribute(this.sv);
          localObject1 = Messages.getAssignedMessageCountsByQueue(localSecureUser, ((MessageQueue)localObject2).getQueueID(), new HashMap());
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
        }
        MessageQueueMembers localMessageQueueMembers1 = ((MessageQueue)localObject2).getActiveQueueMembers();
        MessageQueueMembers localMessageQueueMembers2 = (MessageQueueMembers)paramHttpSession.getAttribute(this.sw);
        Iterator localIterator = ((MessageCounts)localObject1).iterator();
        while (localIterator.hasNext())
        {
          MessageCount localMessageCount = (MessageCount)localIterator.next();
          if ((localMessageQueueMembers1.getByID(localMessageCount.getName()) != null) && (localMessageQueueMembers2.getByID(localMessageCount.getName()) == null))
          {
            this.error = 8069;
            break;
          }
        }
      }
      this.validate = null;
    }
    return this.error == 0;
  }
  
  protected boolean modifyQueueMembers(HttpSession paramHttpSession)
    throws IOException
  {
    this.error = 0;
    HashMap localHashMap = new HashMap();
    MessageQueueMembers localMessageQueueMembers1 = (MessageQueueMembers)paramHttpSession.getAttribute(this.su);
    MessageQueueMembers localMessageQueueMembers2 = (MessageQueueMembers)paramHttpSession.getAttribute(this.sw);
    MessageQueueMembers localMessageQueueMembers3 = (MessageQueueMembers)paramHttpSession.getAttribute(this.ss);
    MessageQueueMembers localMessageQueueMembers4 = (MessageQueueMembers)paramHttpSession.getAttribute(this.st);
    MessageQueue localMessageQueue = (MessageQueue)paramHttpSession.getAttribute(this.sv);
    if ((localMessageQueueMembers1 != null) && (localMessageQueueMembers2 != null) && (localMessageQueueMembers3 != null) && (localMessageQueueMembers4 != null))
    {
      localMessageQueueMembers1.clear();
      localMessageQueueMembers3.clear();
      Iterator localIterator = localMessageQueueMembers2.iterator();
      while (localIterator.hasNext())
      {
        localObject = (BankEmployee)localIterator.next();
        localMessageQueueMembers1.add(localObject);
      }
      localIterator = localMessageQueueMembers4.iterator();
      while (localIterator.hasNext())
      {
        localObject = (BankEmployee)localIterator.next();
        localMessageQueueMembers3.add(localObject);
      }
      localMessageQueue.setActiveQueueMembers((MessageQueueMembers)localMessageQueueMembers1.clone());
      localMessageQueue.setInactiveQueueMembers((MessageQueueMembers)localMessageQueueMembers3.clone());
      Object localObject = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        localMessageQueue = MessageAdmin.modifyMessageQueueMembersByQueue((SecureUser)localObject, localMessageQueue, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return false;
      }
    }
    return true;
  }
  
  public void setTempActiveMembersCollection(String paramString)
  {
    this.sw = paramString;
  }
  
  public void setActiveMembersCollection(String paramString)
  {
    this.su = paramString;
  }
  
  public void setTempInactiveMembersCollection(String paramString)
  {
    this.st = paramString;
  }
  
  public void setInactiveMembersCollection(String paramString)
  {
    this.ss = paramString;
  }
  
  public void setMessageQueueSessionName(String paramString)
  {
    this.sv = paramString;
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.ModQCollectionMembersByQueue
 * JD-Core Version:    0.7.0.1
 */