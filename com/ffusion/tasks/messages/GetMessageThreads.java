package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMessageThreads
  extends BaseTask
  implements Task
{
  protected boolean allThreadFlag = false;
  protected String threadStatus = "";
  protected boolean refreshThreads = false;
  protected String bankId = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MessageThreads localMessageThreads = (MessageThreads)localHttpSession.getAttribute("MessageThreads");
    if ((this.refreshThreads) || (localMessageThreads == null))
    {
      localMessageThreads = new MessageThreads((Locale)localHttpSession.getAttribute("java.util.Locale"));
      if ((this.threadStatus != null) && (this.threadStatus.length() > 0))
      {
        localObject = localMessageThreads.create();
        ((MessageThread)localObject).setThreadStatus(this.threadStatus);
      }
      Object localObject = new HashMap();
      ((HashMap)localObject).put("MESSAGETHREADS", localMessageThreads);
      ((HashMap)localObject).put("OBJECT", new Boolean(this.allThreadFlag));
      if ((this.bankId != null) && (this.bankId.length() > 0)) {
        ((HashMap)localObject).put("BANK_ID", this.bankId);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localMessageThreads = MessageAdmin.getMessageThreads(localSecureUser, (HashMap)localObject);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        Iterator localIterator = localMessageThreads.iterator();
        MessageQueues localMessageQueues1 = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
        MessageQueues localMessageQueues2 = (MessageQueues)localHttpSession.getAttribute("MessageQueuesInactive");
        BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
        while (localIterator.hasNext())
        {
          MessageThread localMessageThread = (MessageThread)localIterator.next();
          BankEmployee localBankEmployee = null;
          MessageQueue localMessageQueue = null;
          if (localMessageThread.getEmployeeID() != null) {
            localBankEmployee = localBankEmployees.getByID(localMessageThread.getEmployeeID());
          }
          if (localBankEmployee != null) {
            localMessageThread.setEmployeeName(localBankEmployee.getFullNameWithLoginId());
          }
          String str2 = localMessageThread.getQueueID();
          if (str2 != null)
          {
            localMessageQueue = localMessageQueues1.getByID(str2);
            if (localMessageQueue == null) {
              localMessageQueue = localMessageQueues2.getByID(str2);
            }
          }
          if (localMessageQueue != null) {
            localMessageThread.setQueueName(localMessageQueue.getQueueName());
          }
        }
        localHttpSession.setAttribute("MessageThreads", localMessageThreads);
      }
      else
      {
        str1 = this.serviceErrorURL;
      }
    }
    if (str1 == null) {
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setAllThread(String paramString)
  {
    this.allThreadFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setThreadStatus(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      this.threadStatus = null;
    }
    else
    {
      paramString = paramString.trim().toUpperCase();
      if (paramString.equals("MESSAGE_ALL")) {
        this.threadStatus = "0";
      } else if (paramString.equals("MESSAGE_NEW")) {
        this.threadStatus = "1";
      } else if (paramString.equals("MESSAGE_OPEN")) {
        this.threadStatus = "2";
      } else if (paramString.equals("MESSAGE_IQUERY")) {
        this.threadStatus = "3";
      } else if (paramString.equals("MESSAGE_CQUERY")) {
        this.threadStatus = "4";
      } else if (paramString.equals("MESSAGE_CLOSED")) {
        this.threadStatus = "5";
      } else if (paramString.equals("MESSAGE_NOTES")) {
        this.threadStatus = "6";
      } else if (paramString.equals("MESSAGE_AUTOREPLY")) {
        this.threadStatus = "7";
      } else if (paramString.equals("MESSAGE_PENDING")) {
        this.threadStatus = "9";
      } else if (paramString.equals("MESSAGE_DENIED")) {
        this.threadStatus = "10";
      } else {
        this.threadStatus = paramString;
      }
    }
  }
  
  public String getThreadStatus()
  {
    if (this.threadStatus != null) {
      return this.threadStatus;
    }
    return "";
  }
  
  public void setRefresh(String paramString)
  {
    this.refreshThreads = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setBankId(String paramString)
  {
    this.bankId = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetMessageThreads
 * JD-Core Version:    0.7.0.1
 */