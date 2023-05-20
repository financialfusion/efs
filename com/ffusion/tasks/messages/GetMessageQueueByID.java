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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMessageQueueByID
  extends BaseTask
  implements Task
{
  private String sB = "";
  private String sC = "";
  private String sD = "";
  private String sz = "en_US";
  private boolean sA = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    MessageQueue localMessageQueue1 = null;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MessageQueues localMessageQueues = new MessageQueues();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    MessageQueue localMessageQueue2 = new MessageQueue();
    localMessageQueue2.setBankId(this.sB);
    localMessageQueue2.setQueueType(this.sC);
    localMessageQueue2.setSearchLanguage(this.sz);
    this.error = 0;
    if (this.sA)
    {
      localMessageQueue2.setQueueID(this.sD);
    }
    else
    {
      this.error = 8033;
      str = this.taskErrorURL;
    }
    try
    {
      if (this.error == 0) {
        localMessageQueues = MessageAdmin.getMessageQueues(localSecureUser, localMessageQueue2, localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      localMessageQueues = null;
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if ((this.error == 0) && (localMessageQueues != null) && (localMessageQueues.size() == 1))
    {
      localMessageQueue1 = (MessageQueue)localMessageQueues.get(0);
      localHttpSession.setAttribute("Queue", localMessageQueue1);
    }
    else
    {
      this.error = 8021;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public String getBankId()
  {
    return this.sB;
  }
  
  public void setBankId(String paramString)
  {
    this.sB = paramString;
  }
  
  public String getQueueId()
  {
    return this.sD;
  }
  
  public void setQueueId(String paramString)
  {
    this.sD = paramString;
    if ((paramString != null) && (paramString.length() > 0)) {
      this.sA = true;
    } else {
      this.sA = false;
    }
  }
  
  public String getQueueType()
  {
    return this.sC;
  }
  
  public void setQueueType(String paramString)
  {
    this.sC = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.sz;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.sz = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetMessageQueueByID
 * JD-Core Version:    0.7.0.1
 */