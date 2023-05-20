package com.ffusion.tasks.aggregation;

import com.ffusion.services.AccountAggregation;
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
  private String kC = "com.ffusion.services.AccountAggregation";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    if (this.className == null)
    {
      this.error = 11002;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        AccountAggregation localAccountAggregation = (AccountAggregation)localClass.newInstance();
        if ((this.error = localAccountAggregation.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute(this.kC, localAccountAggregation);
          str = this.successURL;
        }
      }
      catch (Exception localException)
      {
        this.error = 11003;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setServiceName(String paramString)
  {
    this.kC = paramString;
  }
  
  public String getServiceName()
  {
    return this.kC;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.Initialize
 * JD-Core Version:    0.7.0.1
 */