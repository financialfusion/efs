package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.csil.CSILException;
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

public class GetThreadsByNewCases
  extends BaseTask
  implements Task
{
  private String q4 = "MessageThreads";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    MessageThreads localMessageThreads = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
    MessageQueues localMessageQueues1 = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
    MessageQueues localMessageQueues2 = (MessageQueues)localHttpSession.getAttribute("MessageQueuesInactive");
    if (localSecureUser == null)
    {
      this.error = 1037;
      str1 = this.taskErrorURL;
    }
    else if (localBankEmployees == null)
    {
      this.error = 5503;
      str1 = this.taskErrorURL;
    }
    else if ((localMessageQueues1 == null) && (localMessageQueues2 == null))
    {
      this.error = 8020;
      str1 = this.taskErrorURL;
    }
    else
    {
      try
      {
        localMessageThreads = Messages.getThreadsByNewCases(localSecureUser, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    if (this.error == 0)
    {
      Iterator localIterator = localMessageThreads.iterator();
      MessageThread localMessageThread = null;
      MessageQueue localMessageQueue = null;
      BankEmployee localBankEmployee = null;
      String str2 = null;
      String str3 = null;
      while (localIterator.hasNext())
      {
        localMessageThread = (MessageThread)localIterator.next();
        str2 = localMessageThread.getQueueID();
        if (str2 != null)
        {
          localMessageQueue = null;
          if (localMessageQueues1 != null) {
            localMessageQueue = localMessageQueues1.getByID(str2);
          }
          if ((localMessageQueue == null) && (localMessageQueues2 != null)) {
            localMessageQueue = localMessageQueues2.getByID(str2);
          }
          if (localMessageQueue != null) {
            localMessageThread.setQueueName(localMessageQueue.getQueueName());
          }
        }
        str3 = localMessageThread.getEmployeeID();
        if (str3 != null)
        {
          localBankEmployee = localBankEmployees.getByID(str3);
          if (localBankEmployee != null) {
            localMessageThread.setEmployeeName(localBankEmployee.getFullNameWithLoginId());
          }
        }
      }
      localHttpSession.setAttribute(this.q4, localMessageThreads);
    }
    this.q4 = "MessageThreads";
    return str1;
  }
  
  public void setSessionKeyName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.q4 = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetThreadsByNewCases
 * JD-Core Version:    0.7.0.1
 */