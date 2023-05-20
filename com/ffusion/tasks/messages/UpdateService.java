package com.ffusion.tasks.messages;

import com.ffusion.services.Messaging;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateService
  implements Task
{
  private String tl;
  private String tk;
  protected int error = 0;
  protected String taskErrorURL;
  protected String successURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Messaging localMessaging = (Messaging)localHttpSession.getAttribute("com.ffusion.services.Messaging3");
    String str = this.successURL;
    if (localMessaging != null)
    {
      if (this.tl != null) {
        localMessaging.setUserName(this.tl);
      }
      if (this.tk != null) {
        localMessaging.setPassword(this.tk);
      }
      str = this.successURL;
    }
    else
    {
      this.error = 8000;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setUserName(String paramString)
  {
    this.tl = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.tk = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.UpdateService
 * JD-Core Version:    0.7.0.1
 */