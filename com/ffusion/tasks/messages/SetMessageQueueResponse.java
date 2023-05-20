package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueueResponses;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMessageQueueResponse
  extends BaseTask
  implements Task
{
  private String rW;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    MessageQueueResponses localMessageQueueResponses = (MessageQueueResponses)localHttpSession.getAttribute("MessageQueueResponses");
    if (localMessageQueueResponses == null)
    {
      this.error = 8029;
      str = this.taskErrorURL;
    }
    else
    {
      MessageQueueResponse localMessageQueueResponse = localMessageQueueResponses.getByID(this.rW);
      if (localMessageQueueResponse == null)
      {
        this.error = 8030;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute("MessageQueueResponse", localMessageQueueResponse);
      }
    }
    return str;
  }
  
  public void setResponseID(String paramString)
  {
    this.rW = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetMessageQueueResponse
 * JD-Core Version:    0.7.0.1
 */