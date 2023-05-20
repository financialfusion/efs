package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMessageThread
  extends BaseTask
  implements Task
{
  private String fy;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      if ((this.fy == null) || (this.fy.trim().length() == 0))
      {
        this.error = 8027;
        return this.taskErrorURL;
      }
      MessageThreads localMessageThreads = (MessageThreads)localHttpSession.getAttribute("MessageThreads");
      if (localMessageThreads == null)
      {
        this.error = 8025;
        return this.taskErrorURL;
      }
      MessageThread localMessageThread = localMessageThreads.getByID(this.fy);
      if (localMessageThread == null)
      {
        this.error = 8024;
        return this.taskErrorURL;
      }
      localHttpSession.setAttribute("MessageThread", localMessageThread);
    }
    catch (Exception localException)
    {
      this.error = 8499;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setThreadID(String paramString)
  {
    this.fy = paramString;
  }
  
  public String getThreadID()
  {
    return this.fy;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetMessageThread
 * JD-Core Version:    0.7.0.1
 */