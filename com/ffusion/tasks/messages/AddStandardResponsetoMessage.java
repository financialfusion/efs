package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueueResponses;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddStandardResponsetoMessage
  extends BaseTask
  implements Task
{
  private String q2 = "MessageCenterReply";
  private String qZ;
  private String q1;
  private String q0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    if ((this.qZ == null) || (this.qZ.equals("-1"))) {
      return this.successURL;
    }
    try
    {
      MessageQueueResponses localMessageQueueResponses = (MessageQueueResponses)localHttpSession.getAttribute("MessageQueueResponses");
      SendReply localSendReply = (SendReply)localHttpSession.getAttribute(this.q2);
      MessageQueueResponse localMessageQueueResponse = null;
      if (localMessageQueueResponses != null) {
        localMessageQueueResponse = localMessageQueueResponses.getByID(this.qZ);
      }
      if (localMessageQueueResponses == null)
      {
        this.error = 8029;
        str = this.taskErrorURL;
      }
      else if (localSendReply == null)
      {
        this.error = 8042;
        str = this.taskErrorURL;
      }
      else if (localMessageQueueResponse == null)
      {
        this.error = 8030;
        str = this.taskErrorURL;
      }
      else
      {
        if (this.q1 != null) {
          localSendReply.setMemo(this.q1 + localMessageQueueResponse.getResponseText());
        } else {
          localSendReply.setMemo(localMessageQueueResponse.getResponseText());
        }
        if (this.q0 != null) {
          localSendReply.setStatus(this.q0);
        }
        str = this.successURL;
      }
    }
    catch (Exception localException)
    {
      this.error = 8499;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public String getMsgReplyObjectName()
  {
    return this.q2;
  }
  
  public void setMsgReplyObjectName(String paramString)
  {
    this.q2 = paramString;
  }
  
  public String getResponseID()
  {
    return this.qZ;
  }
  
  public void setResponseID(String paramString)
  {
    this.qZ = paramString;
  }
  
  public String getMemo()
  {
    return this.q1;
  }
  
  public void setMemo(String paramString)
  {
    this.q1 = paramString;
  }
  
  public String getStatus()
  {
    return this.q0;
  }
  
  public void setStatus(String paramString)
  {
    this.q0 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.AddStandardResponsetoMessage
 * JD-Core Version:    0.7.0.1
 */