package com.ffusion.tasks;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOff
  implements Task
{
  protected String nextURL;
  protected String taskErrorURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    removeObjects(localHttpSession);
    return this.nextURL;
  }
  
  protected void removeObjects(HttpSession paramHttpSession) {}
  
  public void setNextURL(String paramString)
  {
    this.nextURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.SignOff
 * JD-Core Version:    0.7.0.1
 */