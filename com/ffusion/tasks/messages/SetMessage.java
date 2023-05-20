package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMessage
  extends BaseTask
  implements Task
{
  private String rV;
  protected String collectionSessionName = "Messages";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      if ((this.rV == null) || (this.rV.trim().length() == 0))
      {
        this.error = 8052;
        return this.taskErrorURL;
      }
      Messages localMessages = (Messages)localHttpSession.getAttribute(this.collectionSessionName);
      if (localMessages == null)
      {
        this.error = 8001;
        return this.taskErrorURL;
      }
      Message localMessage = localMessages.getByID(this.rV);
      if (localMessage == null)
      {
        this.error = 8002;
        return this.taskErrorURL;
      }
      localHttpSession.setAttribute("Message", localMessage);
    }
    catch (Exception localException)
    {
      this.error = 8499;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setMessageID(String paramString)
  {
    this.rV = paramString;
  }
  
  public String getMessageID()
  {
    return this.rV;
  }
  
  public void setCollectionSessionName(String paramString)
  {
    this.collectionSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetMessage
 * JD-Core Version:    0.7.0.1
 */