package com.ffusion.tasks;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetectDemoMode
  implements Task
{
  private String aQQ;
  private String aQO;
  private String aQP;
  protected String liveURL;
  protected String demoURL;
  protected String taskErrorURL;
  private int aQN;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    if ((this.aQP != null) && (this.aQP.equalsIgnoreCase(this.aQQ)))
    {
      this.aQP = this.aQP.toLowerCase();
      this.aQQ = this.aQQ.toLowerCase();
      if (this.demoURL == null)
      {
        this.aQN = 10;
        str = this.taskErrorURL;
      }
      else
      {
        str = this.demoURL;
      }
    }
    else if (this.liveURL == null)
    {
      this.aQN = 11;
      str = this.taskErrorURL;
    }
    else
    {
      str = this.liveURL;
    }
    return str;
  }
  
  public void setUserName(String paramString)
  {
    this.aQQ = paramString;
  }
  
  public String getUserName()
  {
    return this.aQQ;
  }
  
  public void setPassword(String paramString)
  {
    this.aQO = paramString;
  }
  
  public String getPassword()
  {
    return this.aQO;
  }
  
  public void setDemoName(String paramString)
  {
    this.aQP = paramString;
  }
  
  public String getDemoName()
  {
    return this.aQP;
  }
  
  public void setLiveURL(String paramString)
  {
    this.liveURL = paramString;
  }
  
  public void setDemoURL(String paramString)
  {
    this.demoURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.aQN);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.DetectDemoMode
 * JD-Core Version:    0.7.0.1
 */