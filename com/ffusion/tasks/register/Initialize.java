package com.ffusion.tasks.register;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Initialize
  extends BaseTask
  implements Task
{
  public boolean demoMode = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    return this.successURL;
  }
  
  public void setDemoMode(String paramString)
  {
    this.demoMode = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.Initialize
 * JD-Core Version:    0.7.0.1
 */