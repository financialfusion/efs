package com.ffusion.tasks.banking;

import com.ffusion.services.Banking2;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Initialize
  extends InitService
  implements Task
{
  private String ln = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    if (this.className == null)
    {
      this.error = 1030;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        Banking2 localBanking2 = (Banking2)localClass.newInstance();
        if ((this.error = localBanking2.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute(this.ln, localBanking2);
          str = this.successURL;
        }
      }
      catch (Exception localException)
      {
        this.error = 1031;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setServiceName(String paramString)
  {
    this.ln = paramString;
  }
  
  public String getServiceName()
  {
    return this.ln;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.Initialize
 * JD-Core Version:    0.7.0.1
 */