package com.ffusion.tasks.accounts;

import com.ffusion.services.AccountService;
import com.ffusion.services.AccountService3;
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
  protected String serviceName = "com.ffusion.service.AccountService";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    try
    {
      if (this.className == null)
      {
        this.error = 18001;
        str = this.taskErrorURL;
      }
      else
      {
        Class localClass = Class.forName(this.className);
        AccountService localAccountService = null;
        try
        {
          localAccountService = (AccountService)localClass.newInstance();
          this.error = localAccountService.initialize(this.initURL);
          if (this.error == 0)
          {
            HttpSession localHttpSession1 = paramHttpServletRequest.getSession();
            localHttpSession1.setAttribute("com.ffusion.service.AccountService", localAccountService);
          }
          else
          {
            str = this.serviceErrorURL;
          }
        }
        catch (ClassCastException localClassCastException)
        {
          Object localObject = null;
          try
          {
            localObject.initialize();
            HttpSession localHttpSession2 = paramHttpServletRequest.getSession();
            localHttpSession2.setAttribute("com.ffusion.service.AccountService", localAccountService);
          }
          catch (Exception localException2)
          {
            str = this.serviceErrorURL;
          }
        }
      }
    }
    catch (Exception localException1)
    {
      this.error = 18002;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected AccountService createService()
  {
    AccountService localAccountService = null;
    try
    {
      Class localClass = Class.forName(this.className);
      localAccountService = (AccountService)localClass.newInstance();
    }
    catch (Throwable localThrowable) {}
    return localAccountService;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.Initialize
 * JD-Core Version:    0.7.0.1
 */