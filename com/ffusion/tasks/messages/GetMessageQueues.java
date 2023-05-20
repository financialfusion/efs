package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageQueue;
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

public class GetMessageQueues
  extends BaseTask
  implements Task
{
  private String sa = "";
  private String sd = "";
  private String r9 = "";
  private boolean se = true;
  private boolean sb = true;
  private boolean r8 = true;
  private String sc = "en_US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MessageQueues localMessageQueues1 = new MessageQueues();
    MessageQueues localMessageQueues2 = null;
    MessageQueues localMessageQueues3 = null;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    MessageQueue localMessageQueue1 = new MessageQueue();
    localMessageQueue1.setBankId(getBankId());
    localMessageQueue1.setQueueType(getQueueType());
    localMessageQueue1.setSearchLanguage(getSearchLanguage());
    localMessageQueue1.setQueueID(this.r9);
    localMessageQueue1.setIsConsumer(this.sb);
    localMessageQueue1.setIsCorporate(this.r8);
    if (!this.se) {
      localHashMap.put("GetBankEmployees", "no");
    }
    try
    {
      localMessageQueues1 = MessageAdmin.getMessageQueues(localSecureUser, localMessageQueue1, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localMessageQueues2 = new MessageQueues(localSecureUser.getLocale());
      localMessageQueues3 = new MessageQueues(localSecureUser.getLocale());
      Iterator localIterator = localMessageQueues1.iterator();
      while (localIterator.hasNext())
      {
        MessageQueue localMessageQueue2 = (MessageQueue)localIterator.next();
        if (localMessageQueue2.getQueueStatus().equals("0")) {
          localMessageQueues2.add(localMessageQueue2);
        } else {
          localMessageQueues3.add(localMessageQueue2);
        }
      }
      localHttpSession.setAttribute("MessageQueues", localMessageQueues2);
      localHttpSession.setAttribute("MessageQueuesInactive", localMessageQueues3);
    }
    return str;
  }
  
  public String getBankId()
  {
    return this.sa;
  }
  
  public void setBankId(String paramString)
  {
    this.sa = paramString;
  }
  
  public String getQueueType()
  {
    return this.sd;
  }
  
  public void setQueueType(String paramString)
  {
    this.sd = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.sc;
  }
  
  public void setSearchLanguage(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.sc = null;
    } else {
      this.sc = paramString;
    }
  }
  
  public String getQueueId()
  {
    return this.r9;
  }
  
  public void setQueueId(String paramString)
  {
    this.r9 = paramString;
  }
  
  public void setGetBankEmployees(String paramString)
  {
    this.se = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setIsConsumer(String paramString)
  {
    this.sb = new Boolean(paramString).booleanValue();
  }
  
  public void setIsCorporate(String paramString)
  {
    this.r8 = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetMessageQueues
 * JD-Core Version:    0.7.0.1
 */