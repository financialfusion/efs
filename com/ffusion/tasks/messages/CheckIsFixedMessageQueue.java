package com.ffusion.tasks.messages;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckIsFixedMessageQueue
  extends BaseTask
  implements Task
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected int error = 0;
  protected int queueID = 0;
  protected String isFixedCaseType = "false";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (this.queueID < 1000) {
      this.isFixedCaseType = "true";
    } else {
      this.isFixedCaseType = "false";
    }
    return str;
  }
  
  public void setQueueID(String paramString)
  {
    this.queueID = 0;
    try
    {
      this.queueID = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getIsFixedCaseType()
  {
    return this.isFixedCaseType;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.CheckIsFixedMessageQueue
 * JD-Core Version:    0.7.0.1
 */